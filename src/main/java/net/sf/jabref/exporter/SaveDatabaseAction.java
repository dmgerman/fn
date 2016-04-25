begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.      This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.exporter
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|exporter
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|File
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|charset
operator|.
name|Charset
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|charset
operator|.
name|UnsupportedCharsetException
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JFileChooser
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JOptionPane
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JTextArea
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|SwingUtilities
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|Globals
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|JabRefExecutorService
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|JabRefPreferences
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|collab
operator|.
name|ChangeScanner
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|BasePanel
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|FileDialogs
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|JabRefFrame
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|worker
operator|.
name|AbstractWorker
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|worker
operator|.
name|CallBack
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|worker
operator|.
name|Worker
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|l10n
operator|.
name|Encodings
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|l10n
operator|.
name|Localization
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|util
operator|.
name|io
operator|.
name|FileBasedLock
import|;
end_import

begin_import
import|import
name|com
operator|.
name|jgoodies
operator|.
name|forms
operator|.
name|builder
operator|.
name|FormBuilder
import|;
end_import

begin_import
import|import
name|com
operator|.
name|jgoodies
operator|.
name|forms
operator|.
name|layout
operator|.
name|FormLayout
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|Log
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|LogFactory
import|;
end_import

begin_comment
comment|/**  * Action for the "Save" and "Save as" operations called from BasePanel. This class is also used for  * save operations when closing a database or quitting the applications.  *  * The operations run synchronously, but offload the save operation from the event thread using Spin.  * Callers can query whether the operation was canceled, or whether it was successful.  */
end_comment

begin_class
DECL|class|SaveDatabaseAction
specifier|public
class|class
name|SaveDatabaseAction
extends|extends
name|AbstractWorker
block|{
DECL|field|panel
specifier|private
specifier|final
name|BasePanel
name|panel
decl_stmt|;
DECL|field|frame
specifier|private
specifier|final
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|success
specifier|private
name|boolean
name|success
decl_stmt|;
DECL|field|canceled
specifier|private
name|boolean
name|canceled
decl_stmt|;
DECL|field|fileLockedError
specifier|private
name|boolean
name|fileLockedError
decl_stmt|;
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Log
name|LOGGER
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|SaveDatabaseAction
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|method|SaveDatabaseAction (BasePanel panel)
specifier|public
name|SaveDatabaseAction
parameter_list|(
name|BasePanel
name|panel
parameter_list|)
block|{
name|this
operator|.
name|panel
operator|=
name|panel
expr_stmt|;
name|this
operator|.
name|frame
operator|=
name|panel
operator|.
name|frame
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|init ()
specifier|public
name|void
name|init
parameter_list|()
throws|throws
name|Throwable
block|{
name|success
operator|=
literal|false
expr_stmt|;
name|canceled
operator|=
literal|false
expr_stmt|;
name|fileLockedError
operator|=
literal|false
expr_stmt|;
if|if
condition|(
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
operator|.
name|getDatabaseFile
argument_list|()
operator|==
literal|null
condition|)
block|{
name|saveAs
argument_list|()
expr_stmt|;
block|}
else|else
block|{
comment|// Check for external modifications: if true, save not performed so do not tell the user a save is underway but return instead.
if|if
condition|(
name|checkExternalModification
argument_list|()
condition|)
block|{
return|return;
block|}
name|panel
operator|.
name|frame
argument_list|()
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Saving database"
argument_list|)
operator|+
literal|"..."
argument_list|)
expr_stmt|;
name|panel
operator|.
name|setSaving
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
DECL|method|update ()
specifier|public
name|void
name|update
parameter_list|()
block|{
if|if
condition|(
name|success
condition|)
block|{
comment|// Reset title of tab
name|frame
operator|.
name|setTabTitle
argument_list|(
name|panel
argument_list|,
name|panel
operator|.
name|getTabTitle
argument_list|()
argument_list|,
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
operator|.
name|getDatabaseFile
argument_list|()
operator|.
name|getAbsolutePath
argument_list|()
argument_list|)
expr_stmt|;
name|frame
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Saved database"
argument_list|)
operator|+
literal|" '"
operator|+
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
operator|.
name|getDatabaseFile
argument_list|()
operator|.
name|getPath
argument_list|()
operator|+
literal|"'."
argument_list|)
expr_stmt|;
name|frame
operator|.
name|setWindowTitle
argument_list|()
expr_stmt|;
name|frame
operator|.
name|updateAllTabTitles
argument_list|()
expr_stmt|;
block|}
elseif|else
if|if
condition|(
operator|!
name|canceled
condition|)
block|{
if|if
condition|(
name|fileLockedError
condition|)
block|{
comment|// TODO: user should have the option to override the lock file.
name|frame
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Could not save, file locked by another JabRef instance."
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|frame
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Save failed"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
annotation|@
name|Override
DECL|method|run ()
specifier|public
name|void
name|run
parameter_list|()
block|{
if|if
condition|(
name|canceled
operator|||
operator|(
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
operator|.
name|getDatabaseFile
argument_list|()
operator|==
literal|null
operator|)
condition|)
block|{
return|return;
block|}
try|try
block|{
comment|// Make sure the current edit is stored:
name|panel
operator|.
name|storeCurrentEdit
argument_list|()
expr_stmt|;
comment|// If the option is set, autogenerate keys for all entries that are
comment|// lacking keys, before saving:
name|panel
operator|.
name|autoGenerateKeysBeforeSaving
argument_list|()
expr_stmt|;
if|if
condition|(
name|FileBasedLock
operator|.
name|waitForFileLock
argument_list|(
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
operator|.
name|getDatabaseFile
argument_list|()
argument_list|,
literal|10
argument_list|)
condition|)
block|{
comment|// Check for external modifications to alleviate multiuser concurrency issue when near
comment|// simultaneous saves occur to a shared database file: if true, do not perform the save
comment|// rather return instead.
if|if
condition|(
name|checkExternalModification
argument_list|()
condition|)
block|{
return|return;
block|}
comment|// Save the database:
name|success
operator|=
name|saveDatabase
argument_list|(
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
operator|.
name|getDatabaseFile
argument_list|()
argument_list|,
literal|false
argument_list|,
name|panel
operator|.
name|getEncoding
argument_list|()
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|fileUpdateMonitor
operator|.
name|updateTimeStamp
argument_list|(
name|panel
operator|.
name|getFileMonitorHandle
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// No file lock
name|success
operator|=
literal|false
expr_stmt|;
name|fileLockedError
operator|=
literal|true
expr_stmt|;
block|}
name|panel
operator|.
name|setSaving
argument_list|(
literal|false
argument_list|)
expr_stmt|;
if|if
condition|(
name|success
condition|)
block|{
name|panel
operator|.
name|undoManager
operator|.
name|markUnchanged
argument_list|()
expr_stmt|;
if|if
condition|(
operator|!
name|AutoSaveManager
operator|.
name|deleteAutoSaveFile
argument_list|(
name|panel
argument_list|)
condition|)
block|{
comment|//System.out.println("Deletion of autosave file failed");
block|}
comment|/* else                      System.out.println("Deleted autosave file (if it existed)");*/
comment|// (Only) after a successful save the following
comment|// statement marks that the base is unchanged
comment|// since last save:
name|panel
operator|.
name|setNonUndoableChange
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|panel
operator|.
name|setBaseChanged
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|panel
operator|.
name|setUpdatedExternally
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|SaveException
name|ex2
parameter_list|)
block|{
if|if
condition|(
name|ex2
operator|==
name|SaveException
operator|.
name|FILE_LOCKED
condition|)
block|{
name|success
operator|=
literal|false
expr_stmt|;
name|fileLockedError
operator|=
literal|true
expr_stmt|;
return|return;
block|}
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Problem saving file"
argument_list|,
name|ex2
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|saveDatabase (File file, boolean selectedOnly, Charset encoding)
specifier|private
name|boolean
name|saveDatabase
parameter_list|(
name|File
name|file
parameter_list|,
name|boolean
name|selectedOnly
parameter_list|,
name|Charset
name|encoding
parameter_list|)
throws|throws
name|SaveException
block|{
name|SaveSession
name|session
decl_stmt|;
name|frame
operator|.
name|block
argument_list|()
expr_stmt|;
try|try
block|{
name|SavePreferences
name|prefs
init|=
name|SavePreferences
operator|.
name|loadForSaveFromPreferences
argument_list|(
name|Globals
operator|.
name|prefs
argument_list|)
operator|.
name|withEncoding
argument_list|(
name|encoding
argument_list|)
decl_stmt|;
name|BibDatabaseWriter
name|databaseWriter
init|=
operator|new
name|BibDatabaseWriter
argument_list|()
decl_stmt|;
if|if
condition|(
name|selectedOnly
condition|)
block|{
name|session
operator|=
name|databaseWriter
operator|.
name|savePartOfDatabase
argument_list|(
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
argument_list|,
name|prefs
argument_list|,
name|panel
operator|.
name|getSelectedEntries
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|session
operator|=
name|databaseWriter
operator|.
name|saveDatabase
argument_list|(
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
argument_list|,
name|prefs
argument_list|)
expr_stmt|;
block|}
name|panel
operator|.
name|registerUndoableChanges
argument_list|(
name|session
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|UnsupportedCharsetException
name|ex2
parameter_list|)
block|{
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|frame
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Could not save file."
argument_list|)
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"Character encoding '%0' is not supported."
argument_list|,
name|encoding
operator|.
name|displayName
argument_list|()
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Save database"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|SaveException
argument_list|(
literal|"rt"
argument_list|)
throw|;
block|}
catch|catch
parameter_list|(
name|SaveException
name|ex
parameter_list|)
block|{
if|if
condition|(
name|ex
operator|==
name|SaveException
operator|.
name|FILE_LOCKED
condition|)
block|{
throw|throw
name|ex
throw|;
block|}
if|if
condition|(
name|ex
operator|.
name|specificEntry
argument_list|()
condition|)
block|{
comment|// Error occured during processing of
comment|// be. Highlight it:
name|int
name|row
init|=
name|panel
operator|.
name|mainTable
operator|.
name|findEntry
argument_list|(
name|ex
operator|.
name|getEntry
argument_list|()
argument_list|)
decl_stmt|;
name|int
name|topShow
init|=
name|Math
operator|.
name|max
argument_list|(
literal|0
argument_list|,
name|row
operator|-
literal|3
argument_list|)
decl_stmt|;
name|panel
operator|.
name|mainTable
operator|.
name|setRowSelectionInterval
argument_list|(
name|row
argument_list|,
name|row
argument_list|)
expr_stmt|;
name|panel
operator|.
name|mainTable
operator|.
name|scrollTo
argument_list|(
name|topShow
argument_list|)
expr_stmt|;
name|panel
operator|.
name|showEntry
argument_list|(
name|ex
operator|.
name|getEntry
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Problem saving file"
argument_list|,
name|ex
argument_list|)
expr_stmt|;
block|}
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|frame
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Could not save file."
argument_list|)
operator|+
literal|".\n"
operator|+
name|ex
operator|.
name|getMessage
argument_list|()
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Save database"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|SaveException
argument_list|(
literal|"rt"
argument_list|)
throw|;
block|}
finally|finally
block|{
name|frame
operator|.
name|unblock
argument_list|()
expr_stmt|;
block|}
name|boolean
name|commit
init|=
literal|true
decl_stmt|;
if|if
condition|(
operator|!
name|session
operator|.
name|getWriter
argument_list|()
operator|.
name|couldEncodeAll
argument_list|()
condition|)
block|{
name|FormBuilder
name|builder
init|=
name|FormBuilder
operator|.
name|create
argument_list|()
operator|.
name|layout
argument_list|(
operator|new
name|FormLayout
argument_list|(
literal|"left:pref, 4dlu, fill:pref"
argument_list|,
literal|"pref, 4dlu, pref"
argument_list|)
argument_list|)
decl_stmt|;
name|JTextArea
name|ta
init|=
operator|new
name|JTextArea
argument_list|(
name|session
operator|.
name|getWriter
argument_list|()
operator|.
name|getProblemCharacters
argument_list|()
argument_list|)
decl_stmt|;
name|ta
operator|.
name|setEditable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"The chosen encoding '%0' could not encode the following characters:"
argument_list|,
name|session
operator|.
name|getEncoding
argument_list|()
operator|.
name|displayName
argument_list|()
argument_list|)
argument_list|)
operator|.
name|xy
argument_list|(
literal|1
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|ta
argument_list|)
operator|.
name|xy
argument_list|(
literal|3
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"What do you want to do?"
argument_list|)
argument_list|)
operator|.
name|xy
argument_list|(
literal|1
argument_list|,
literal|3
argument_list|)
expr_stmt|;
name|String
name|tryDiff
init|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"Try different encoding"
argument_list|)
decl_stmt|;
name|int
name|answer
init|=
name|JOptionPane
operator|.
name|showOptionDialog
argument_list|(
name|frame
argument_list|,
name|builder
operator|.
name|getPanel
argument_list|()
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Save database"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|YES_NO_CANCEL_OPTION
argument_list|,
name|JOptionPane
operator|.
name|WARNING_MESSAGE
argument_list|,
literal|null
argument_list|,
operator|new
name|String
index|[]
block|{
name|Localization
operator|.
name|lang
argument_list|(
literal|"Save"
argument_list|)
block|,
name|tryDiff
block|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Cancel"
argument_list|)
block|}
argument_list|,
name|tryDiff
argument_list|)
decl_stmt|;
if|if
condition|(
name|answer
operator|==
name|JOptionPane
operator|.
name|NO_OPTION
condition|)
block|{
comment|// The user wants to use another encoding.
name|Object
name|choice
init|=
name|JOptionPane
operator|.
name|showInputDialog
argument_list|(
name|frame
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Select encoding"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Save database"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|QUESTION_MESSAGE
argument_list|,
literal|null
argument_list|,
name|Encodings
operator|.
name|ENCODINGS_DISPLAYNAMES
argument_list|,
name|encoding
argument_list|)
decl_stmt|;
if|if
condition|(
name|choice
operator|==
literal|null
condition|)
block|{
name|commit
operator|=
literal|false
expr_stmt|;
block|}
else|else
block|{
name|Charset
name|newEncoding
init|=
name|Charset
operator|.
name|forName
argument_list|(
operator|(
name|String
operator|)
name|choice
argument_list|)
decl_stmt|;
return|return
name|saveDatabase
argument_list|(
name|file
argument_list|,
name|selectedOnly
argument_list|,
name|newEncoding
argument_list|)
return|;
block|}
block|}
elseif|else
if|if
condition|(
name|answer
operator|==
name|JOptionPane
operator|.
name|CANCEL_OPTION
condition|)
block|{
name|commit
operator|=
literal|false
expr_stmt|;
block|}
block|}
try|try
block|{
if|if
condition|(
name|commit
condition|)
block|{
name|session
operator|.
name|commit
argument_list|(
name|file
argument_list|)
expr_stmt|;
name|panel
operator|.
name|setEncoding
argument_list|(
name|encoding
argument_list|)
expr_stmt|;
comment|// Make sure to remember which encoding we used.
block|}
else|else
block|{
name|session
operator|.
name|cancel
argument_list|()
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|SaveException
name|e
parameter_list|)
block|{
name|int
name|ans
init|=
name|JOptionPane
operator|.
name|showConfirmDialog
argument_list|(
literal|null
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Save failed during backup creation"
argument_list|)
operator|+
literal|". "
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"Save without backup?"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Unable to create backup"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|YES_NO_OPTION
argument_list|)
decl_stmt|;
if|if
condition|(
name|ans
operator|==
name|JOptionPane
operator|.
name|YES_OPTION
condition|)
block|{
name|session
operator|.
name|setUseBackup
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|session
operator|.
name|commit
argument_list|(
name|file
argument_list|)
expr_stmt|;
name|panel
operator|.
name|setEncoding
argument_list|(
name|encoding
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|commit
operator|=
literal|false
expr_stmt|;
block|}
block|}
return|return
name|commit
return|;
block|}
comment|/**      * Run the "Save" operation. This method offloads the actual save operation to a background thread, but      * still runs synchronously using Spin (the method returns only after completing the operation).      */
DECL|method|runCommand ()
specifier|public
name|void
name|runCommand
parameter_list|()
throws|throws
name|Throwable
block|{
comment|// This part uses Spin's features:
name|Worker
name|wrk
init|=
name|getWorker
argument_list|()
decl_stmt|;
comment|// The Worker returned by getWorker() has been wrapped
comment|// by Spin.off(), which makes its methods be run in
comment|// a different thread from the EDT.
name|CallBack
name|clb
init|=
name|getCallBack
argument_list|()
decl_stmt|;
name|init
argument_list|()
expr_stmt|;
comment|// This method runs in this same thread, the EDT.
comment|// Useful for initial GUI actions, like printing a message.
comment|// The CallBack returned by getCallBack() has been wrapped
comment|// by Spin.over(), which makes its methods be run on
comment|// the EDT.
name|wrk
operator|.
name|run
argument_list|()
expr_stmt|;
comment|// Runs the potentially time-consuming action
comment|// without freezing the GUI. The magic is that THIS line
comment|// of execution will not continue until run() is finished.
name|clb
operator|.
name|update
argument_list|()
expr_stmt|;
comment|// Runs the update() method on the EDT.
block|}
DECL|method|save ()
specifier|public
name|void
name|save
parameter_list|()
throws|throws
name|Throwable
block|{
name|runCommand
argument_list|()
expr_stmt|;
name|frame
operator|.
name|updateEnabledState
argument_list|()
expr_stmt|;
block|}
comment|/**      * Run the "Save as" operation. This method offloads the actual save operation to a background thread, but      * still runs synchronously using Spin (the method returns only after completing the operation).      */
DECL|method|saveAs ()
specifier|public
name|void
name|saveAs
parameter_list|()
throws|throws
name|Throwable
block|{
name|String
name|chosenFile
decl_stmt|;
name|File
name|f
init|=
literal|null
decl_stmt|;
while|while
condition|(
name|f
operator|==
literal|null
condition|)
block|{
name|chosenFile
operator|=
name|FileDialogs
operator|.
name|getNewFile
argument_list|(
name|frame
argument_list|,
operator|new
name|File
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|WORKING_DIRECTORY
argument_list|)
argument_list|)
argument_list|,
literal|".bib"
argument_list|,
name|JFileChooser
operator|.
name|SAVE_DIALOG
argument_list|,
literal|false
argument_list|,
literal|null
argument_list|)
expr_stmt|;
if|if
condition|(
name|chosenFile
operator|==
literal|null
condition|)
block|{
name|canceled
operator|=
literal|true
expr_stmt|;
return|return;
comment|// canceled
block|}
name|f
operator|=
operator|new
name|File
argument_list|(
name|chosenFile
argument_list|)
expr_stmt|;
comment|// Check if the file already exists:
if|if
condition|(
name|f
operator|.
name|exists
argument_list|()
operator|&&
operator|(
name|JOptionPane
operator|.
name|showConfirmDialog
argument_list|(
name|frame
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"'%0' exists. Overwrite file?"
argument_list|,
name|f
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Save database"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|OK_CANCEL_OPTION
argument_list|)
operator|!=
name|JOptionPane
operator|.
name|OK_OPTION
operator|)
condition|)
block|{
name|f
operator|=
literal|null
expr_stmt|;
block|}
block|}
if|if
condition|(
name|f
operator|!=
literal|null
condition|)
block|{
name|File
name|oldFile
init|=
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
operator|.
name|getDatabaseFile
argument_list|()
decl_stmt|;
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
operator|.
name|setDatabaseFile
argument_list|(
name|f
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|prefs
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|WORKING_DIRECTORY
argument_list|,
name|f
operator|.
name|getParent
argument_list|()
argument_list|)
expr_stmt|;
name|runCommand
argument_list|()
expr_stmt|;
comment|// If the operation failed, revert the file field and return:
if|if
condition|(
operator|!
name|success
condition|)
block|{
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
operator|.
name|setDatabaseFile
argument_list|(
name|oldFile
argument_list|)
expr_stmt|;
return|return;
block|}
comment|// Register so we get notifications about outside changes to the file.
try|try
block|{
name|panel
operator|.
name|setFileMonitorHandle
argument_list|(
name|Globals
operator|.
name|fileUpdateMonitor
operator|.
name|addUpdateListener
argument_list|(
name|panel
argument_list|,
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
operator|.
name|getDatabaseFile
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Problem registering file change notifications"
argument_list|,
name|ex
argument_list|)
expr_stmt|;
block|}
name|frame
operator|.
name|getFileHistory
argument_list|()
operator|.
name|newFile
argument_list|(
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
operator|.
name|getDatabaseFile
argument_list|()
operator|.
name|getPath
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|frame
operator|.
name|updateEnabledState
argument_list|()
expr_stmt|;
block|}
comment|/**      * Query whether the last operation was successful.      *      * @return true if the last Save/SaveAs operation completed successfully, false otherwise.      */
DECL|method|isSuccess ()
specifier|public
name|boolean
name|isSuccess
parameter_list|()
block|{
return|return
name|success
return|;
block|}
comment|/**      * Query whether the last operation was canceled.      *      * @return true if the last Save/SaveAs operation was canceled from the file dialog or from another      * query dialog, false otherwise.      */
DECL|method|isCanceled ()
specifier|public
name|boolean
name|isCanceled
parameter_list|()
block|{
return|return
name|canceled
return|;
block|}
comment|/**      * Check whether or not the external database has been modified. If so need to alert the user to accept external updates prior to      * saving the database. This is necessary to avoid overwriting other users work when using a multiuser database file.      *      * @return true if the external database file has been modified and the user must choose to accept the changes and false if no modifications      * were found or there is no requested protection for the database file.      */
DECL|method|checkExternalModification ()
specifier|private
name|boolean
name|checkExternalModification
parameter_list|()
block|{
comment|// Check for external modifications:
if|if
condition|(
name|panel
operator|.
name|isUpdatedExternally
argument_list|()
operator|||
name|Globals
operator|.
name|fileUpdateMonitor
operator|.
name|hasBeenModified
argument_list|(
name|panel
operator|.
name|getFileMonitorHandle
argument_list|()
argument_list|)
condition|)
block|{
name|String
index|[]
name|opts
init|=
operator|new
name|String
index|[]
block|{
name|Localization
operator|.
name|lang
argument_list|(
literal|"Review changes"
argument_list|)
block|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Save"
argument_list|)
block|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Cancel"
argument_list|)
block|}
decl_stmt|;
name|int
name|answer
init|=
name|JOptionPane
operator|.
name|showOptionDialog
argument_list|(
name|panel
operator|.
name|frame
argument_list|()
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"File has been updated externally. "
operator|+
literal|"What do you want to do?"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"File updated externally"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|YES_NO_CANCEL_OPTION
argument_list|,
name|JOptionPane
operator|.
name|QUESTION_MESSAGE
argument_list|,
literal|null
argument_list|,
name|opts
argument_list|,
name|opts
index|[
literal|0
index|]
argument_list|)
decl_stmt|;
if|if
condition|(
name|answer
operator|==
name|JOptionPane
operator|.
name|CANCEL_OPTION
condition|)
block|{
name|canceled
operator|=
literal|true
expr_stmt|;
return|return
literal|true
return|;
block|}
elseif|else
if|if
condition|(
name|answer
operator|==
name|JOptionPane
operator|.
name|YES_OPTION
condition|)
block|{
name|canceled
operator|=
literal|true
expr_stmt|;
name|JabRefExecutorService
operator|.
name|INSTANCE
operator|.
name|execute
argument_list|(
call|(
name|Runnable
call|)
argument_list|()
operator|->
block|{
if|if
condition|(
operator|!
name|FileBasedLock
operator|.
name|waitForFileLock
argument_list|(
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
operator|.
name|getDatabaseFile
argument_list|()
argument_list|,
literal|10
argument_list|)
condition|)
block|{
comment|// TODO: GUI handling of the situation when the externally modified file keeps being locked.
name|LOGGER
operator|.
name|error
argument_list|(
literal|"File locked, this will be trouble."
argument_list|)
expr_stmt|;
block|}
name|ChangeScanner
name|scanner
operator|=
operator|new
name|ChangeScanner
argument_list|(
name|panel
operator|.
name|frame
argument_list|()
argument_list|,
name|panel
argument_list|,
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
operator|.
name|getDatabaseFile
argument_list|()
argument_list|)
argument_list|;
name|JabRefExecutorService
operator|.
name|INSTANCE
operator|.
name|executeWithLowPriorityInOwnThreadAndWait
argument_list|(
name|scanner
argument_list|)
expr_stmt|;
if|if
condition|(
name|scanner
operator|.
name|changesFound
argument_list|()
condition|)
block|{
name|scanner
operator|.
name|displayResult
argument_list|(
operator|(
name|ChangeScanner
operator|.
name|DisplayResultCallback
operator|)
name|resolved
lambda|->
block|{
if|if
condition|(
name|resolved
condition|)
block|{
name|panel
operator|.
name|setUpdatedExternally
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|SwingUtilities
operator|.
name|invokeLater
argument_list|(
call|(
name|Runnable
call|)
argument_list|()
operator|->
name|panel
operator|.
name|getSidePaneManager
argument_list|()
operator|.
name|hide
argument_list|(
literal|"fileUpdate"
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|canceled
operator|=
literal|true
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
block|}
block|)
empty_stmt|;
return|return
literal|true
return|;
block|}
else|else
block|{
comment|// User indicated to store anyway.
if|if
condition|(
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
operator|.
name|getMetaData
argument_list|()
operator|.
name|isProtected
argument_list|()
condition|)
block|{
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|frame
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Database is protected. Cannot save until external changes have been reviewed."
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Protected database"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
name|canceled
operator|=
literal|true
expr_stmt|;
block|}
else|else
block|{
name|panel
operator|.
name|setUpdatedExternally
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|panel
operator|.
name|getSidePaneManager
argument_list|()
operator|.
name|hide
argument_list|(
literal|"fileUpdate"
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

begin_comment
comment|// Return false as either no external database file modifications have been found or overwrite is requested any way
end_comment

begin_return
return|return
literal|false
return|;
end_return

unit|} }
end_unit

