begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.importer
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|importer
package|;
end_package

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|ActionEvent
import|;
end_import

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
name|file
operator|.
name|attribute
operator|.
name|FileTime
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collections
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Iterator
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Optional
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|Action
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
name|BibDatabaseContext
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
name|Defaults
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
name|MetaData
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
name|exporter
operator|.
name|AutoSaveManager
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
name|IconTheme
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
name|ParserResultWarningDialog
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
name|actions
operator|.
name|MnemonicAwareAction
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
name|keyboard
operator|.
name|KeyBinding
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
name|undo
operator|.
name|NamedCompound
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
name|importer
operator|.
name|fileformat
operator|.
name|BibtexImporter
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
name|strings
operator|.
name|StringUtil
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
name|migrations
operator|.
name|FileLinksUpgradeWarning
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
name|model
operator|.
name|database
operator|.
name|BibDatabase
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
name|model
operator|.
name|database
operator|.
name|BibDatabaseMode
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
name|model
operator|.
name|entry
operator|.
name|BibEntry
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
name|specialfields
operator|.
name|SpecialFieldsUtils
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
comment|// The action concerned with opening an existing database.
end_comment

begin_class
DECL|class|OpenDatabaseAction
specifier|public
class|class
name|OpenDatabaseAction
extends|extends
name|MnemonicAwareAction
block|{
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
name|OpenDatabaseAction
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|showDialog
specifier|private
specifier|final
name|boolean
name|showDialog
decl_stmt|;
DECL|field|frame
specifier|private
specifier|final
name|JabRefFrame
name|frame
decl_stmt|;
comment|// List of actions that may need to be called after opening the file. Such as
comment|// upgrade actions etc. that may depend on the JabRef version that wrote the file:
DECL|field|POST_OPEN_ACTIONS
specifier|private
specifier|static
specifier|final
name|List
argument_list|<
name|PostOpenAction
argument_list|>
name|POST_OPEN_ACTIONS
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
static|static
block|{
comment|// Add the action for checking for new custom entry types loaded from the bib file:
name|POST_OPEN_ACTIONS
operator|.
name|add
argument_list|(
operator|new
name|CheckForNewEntryTypesAction
argument_list|()
argument_list|)
expr_stmt|;
comment|// Add the action for converting legacy entries in ExplicitGroup
name|POST_OPEN_ACTIONS
operator|.
name|add
argument_list|(
operator|new
name|ConvertLegacyExplicitGroups
argument_list|()
argument_list|)
expr_stmt|;
comment|// Add the action for the new external file handling system in version 2.3:
name|POST_OPEN_ACTIONS
operator|.
name|add
argument_list|(
operator|new
name|FileLinksUpgradeWarning
argument_list|()
argument_list|)
expr_stmt|;
comment|// Add the action for warning about and handling duplicate BibTeX keys:
name|POST_OPEN_ACTIONS
operator|.
name|add
argument_list|(
operator|new
name|HandleDuplicateWarnings
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|OpenDatabaseAction (JabRefFrame frame, boolean showDialog)
specifier|public
name|OpenDatabaseAction
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|boolean
name|showDialog
parameter_list|)
block|{
name|super
argument_list|(
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|OPEN
operator|.
name|getIcon
argument_list|()
argument_list|)
expr_stmt|;
name|this
operator|.
name|frame
operator|=
name|frame
expr_stmt|;
name|this
operator|.
name|showDialog
operator|=
name|showDialog
expr_stmt|;
name|putValue
argument_list|(
name|Action
operator|.
name|NAME
argument_list|,
name|Localization
operator|.
name|menuTitle
argument_list|(
literal|"Open database"
argument_list|)
argument_list|)
expr_stmt|;
name|putValue
argument_list|(
name|Action
operator|.
name|ACCELERATOR_KEY
argument_list|,
name|Globals
operator|.
name|getKeyPrefs
argument_list|()
operator|.
name|getKey
argument_list|(
name|KeyBinding
operator|.
name|OPEN_DATABASE
argument_list|)
argument_list|)
expr_stmt|;
name|putValue
argument_list|(
name|Action
operator|.
name|SHORT_DESCRIPTION
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Open BibTeX database"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|actionPerformed (ActionEvent e)
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|List
argument_list|<
name|File
argument_list|>
name|filesToOpen
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
if|if
condition|(
name|showDialog
condition|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|chosenStrings
init|=
name|FileDialogs
operator|.
name|getMultipleFiles
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
name|Collections
operator|.
name|singletonList
argument_list|(
literal|".bib"
argument_list|)
argument_list|,
literal|true
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|chosen
range|:
name|chosenStrings
control|)
block|{
if|if
condition|(
name|chosen
operator|!=
literal|null
condition|)
block|{
name|filesToOpen
operator|.
name|add
argument_list|(
operator|new
name|File
argument_list|(
name|chosen
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
else|else
block|{
name|LOGGER
operator|.
name|info
argument_list|(
name|Action
operator|.
name|NAME
operator|+
literal|" "
operator|+
name|e
operator|.
name|getActionCommand
argument_list|()
argument_list|)
expr_stmt|;
name|filesToOpen
operator|.
name|add
argument_list|(
operator|new
name|File
argument_list|(
name|StringUtil
operator|.
name|getCorrectFileName
argument_list|(
name|e
operator|.
name|getActionCommand
argument_list|()
argument_list|,
literal|"bib"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|openFiles
argument_list|(
name|filesToOpen
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
comment|/**      * Opens the given file. If null or 404, nothing happens      *      * @param file the file, may be null or not existing      */
DECL|method|openFile (File file, boolean raisePanel)
specifier|public
name|void
name|openFile
parameter_list|(
name|File
name|file
parameter_list|,
name|boolean
name|raisePanel
parameter_list|)
block|{
name|List
argument_list|<
name|File
argument_list|>
name|filesToOpen
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|filesToOpen
operator|.
name|add
argument_list|(
name|file
argument_list|)
expr_stmt|;
name|openFiles
argument_list|(
name|filesToOpen
argument_list|,
name|raisePanel
argument_list|)
expr_stmt|;
block|}
DECL|method|openFilesAsStringList (List<String> fileNamesToOpen, boolean raisePanel)
specifier|public
name|void
name|openFilesAsStringList
parameter_list|(
name|List
argument_list|<
name|String
argument_list|>
name|fileNamesToOpen
parameter_list|,
name|boolean
name|raisePanel
parameter_list|)
block|{
name|List
argument_list|<
name|File
argument_list|>
name|filesToOpen
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|fileName
range|:
name|fileNamesToOpen
control|)
block|{
name|filesToOpen
operator|.
name|add
argument_list|(
operator|new
name|File
argument_list|(
name|fileName
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|openFiles
argument_list|(
name|filesToOpen
argument_list|,
name|raisePanel
argument_list|)
expr_stmt|;
block|}
comment|/**      * Opens the given files. If one of it is null or 404, nothing happens      *      * @param filesToOpen the filesToOpen, may be null or not existing      */
DECL|method|openFiles (List<File> filesToOpen, boolean raisePanel)
specifier|public
name|void
name|openFiles
parameter_list|(
name|List
argument_list|<
name|File
argument_list|>
name|filesToOpen
parameter_list|,
name|boolean
name|raisePanel
parameter_list|)
block|{
name|BasePanel
name|toRaise
init|=
literal|null
decl_stmt|;
name|int
name|initialCount
init|=
name|filesToOpen
operator|.
name|size
argument_list|()
decl_stmt|;
name|int
name|removed
init|=
literal|0
decl_stmt|;
comment|// Check if any of the files are already open:
for|for
control|(
name|Iterator
argument_list|<
name|File
argument_list|>
name|iterator
init|=
name|filesToOpen
operator|.
name|iterator
argument_list|()
init|;
name|iterator
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|File
name|file
init|=
name|iterator
operator|.
name|next
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|frame
operator|.
name|getTabbedPane
argument_list|()
operator|.
name|getTabCount
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|BasePanel
name|basePanel
init|=
name|frame
operator|.
name|getBasePanelAt
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|basePanel
operator|.
name|getBibDatabaseContext
argument_list|()
operator|.
name|getDatabaseFile
argument_list|()
operator|!=
literal|null
operator|)
operator|&&
name|basePanel
operator|.
name|getBibDatabaseContext
argument_list|()
operator|.
name|getDatabaseFile
argument_list|()
operator|.
name|equals
argument_list|(
name|file
argument_list|)
condition|)
block|{
name|iterator
operator|.
name|remove
argument_list|()
expr_stmt|;
name|removed
operator|++
expr_stmt|;
comment|// See if we removed the final one. If so, we must perhaps
comment|// raise the BasePanel in question:
if|if
condition|(
name|removed
operator|==
name|initialCount
condition|)
block|{
name|toRaise
operator|=
name|basePanel
expr_stmt|;
block|}
comment|// no more bps to check, we found a matching one
break|break;
block|}
block|}
block|}
comment|// Run the actual open in a thread to prevent the program
comment|// locking until the file is loaded.
if|if
condition|(
operator|!
name|filesToOpen
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
specifier|final
name|List
argument_list|<
name|File
argument_list|>
name|theFiles
init|=
name|Collections
operator|.
name|unmodifiableList
argument_list|(
name|filesToOpen
argument_list|)
decl_stmt|;
name|JabRefExecutorService
operator|.
name|INSTANCE
operator|.
name|execute
argument_list|(
parameter_list|()
lambda|->
block|{
for|for
control|(
name|File
name|theFile
range|:
name|theFiles
control|)
block|{
name|openTheFile
argument_list|(
name|theFile
argument_list|,
name|raisePanel
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
for|for
control|(
name|File
name|theFile
range|:
name|theFiles
control|)
block|{
name|frame
operator|.
name|getFileHistory
argument_list|()
operator|.
name|newFile
argument_list|(
name|theFile
operator|.
name|getPath
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
comment|// If no files are remaining to open, this could mean that a file was
comment|// already open. If so, we may have to raise the correct tab:
elseif|else
if|if
condition|(
name|toRaise
operator|!=
literal|null
condition|)
block|{
name|frame
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"File '%0' is already open."
argument_list|,
name|toRaise
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
argument_list|)
expr_stmt|;
name|frame
operator|.
name|getTabbedPane
argument_list|()
operator|.
name|setSelectedComponent
argument_list|(
name|toRaise
argument_list|)
expr_stmt|;
block|}
name|frame
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Files opened"
argument_list|)
operator|+
literal|": "
operator|+
operator|(
name|filesToOpen
operator|.
name|size
argument_list|()
operator|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * @param file the file, may be null or not existing      */
DECL|method|openTheFile (File file, boolean raisePanel)
specifier|private
name|void
name|openTheFile
parameter_list|(
name|File
name|file
parameter_list|,
name|boolean
name|raisePanel
parameter_list|)
block|{
if|if
condition|(
operator|(
name|file
operator|!=
literal|null
operator|)
operator|&&
name|file
operator|.
name|exists
argument_list|()
condition|)
block|{
name|File
name|fileToLoad
init|=
name|file
decl_stmt|;
name|frame
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Opening"
argument_list|)
operator|+
literal|": '"
operator|+
name|file
operator|.
name|getPath
argument_list|()
operator|+
literal|"'"
argument_list|)
expr_stmt|;
name|boolean
name|tryingAutosave
init|=
literal|false
decl_stmt|;
name|boolean
name|autoSaveFound
init|=
name|AutoSaveManager
operator|.
name|newerAutoSaveExists
argument_list|(
name|file
argument_list|)
decl_stmt|;
if|if
condition|(
name|autoSaveFound
operator|&&
operator|!
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|PROMPT_BEFORE_USING_AUTOSAVE
argument_list|)
condition|)
block|{
comment|// We have found a newer autosave, and the preferences say we should load
comment|// it without prompting, so we replace the fileToLoad:
name|fileToLoad
operator|=
name|AutoSaveManager
operator|.
name|getAutoSaveFile
argument_list|(
name|file
argument_list|)
expr_stmt|;
name|tryingAutosave
operator|=
literal|true
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|autoSaveFound
condition|)
block|{
comment|// We have found a newer autosave, but we are not allowed to use it without
comment|// prompting.
name|int
name|answer
init|=
name|JOptionPane
operator|.
name|showConfirmDialog
argument_list|(
literal|null
argument_list|,
literal|"<html>"
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"An autosave file was found for this database. This could indicate "
operator|+
literal|"that JabRef didn't shut down cleanly last time the file was used."
argument_list|)
operator|+
literal|"<br>"
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"Do you want to recover the database from the autosave file?"
argument_list|)
operator|+
literal|"</html>"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Recover from autosave"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|YES_NO_OPTION
argument_list|)
decl_stmt|;
if|if
condition|(
name|answer
operator|==
name|JOptionPane
operator|.
name|YES_OPTION
condition|)
block|{
name|fileToLoad
operator|=
name|AutoSaveManager
operator|.
name|getAutoSaveFile
argument_list|(
name|file
argument_list|)
expr_stmt|;
name|tryingAutosave
operator|=
literal|true
expr_stmt|;
block|}
block|}
name|boolean
name|done
init|=
literal|false
decl_stmt|;
while|while
condition|(
operator|!
name|done
condition|)
block|{
name|String
name|fileName
init|=
name|file
operator|.
name|getPath
argument_list|()
decl_stmt|;
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
name|file
operator|.
name|getPath
argument_list|()
argument_list|)
expr_stmt|;
comment|// Should this be done _after_ we know it was successfully opened?
if|if
condition|(
name|FileBasedLock
operator|.
name|hasLockFile
argument_list|(
name|file
operator|.
name|toPath
argument_list|()
argument_list|)
condition|)
block|{
name|Optional
argument_list|<
name|FileTime
argument_list|>
name|modificationTime
init|=
name|FileBasedLock
operator|.
name|getLockFileTimeStamp
argument_list|(
name|file
operator|.
name|toPath
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|modificationTime
operator|.
name|isPresent
argument_list|()
operator|)
operator|&&
operator|(
operator|(
name|System
operator|.
name|currentTimeMillis
argument_list|()
operator|-
name|modificationTime
operator|.
name|get
argument_list|()
operator|.
name|toMillis
argument_list|()
operator|)
operator|>
name|FileBasedLock
operator|.
name|LOCKFILE_CRITICAL_AGE
operator|)
condition|)
block|{
comment|// The lock file is fairly old, so we can offer to "steal" the file:
name|int
name|answer
init|=
name|JOptionPane
operator|.
name|showConfirmDialog
argument_list|(
literal|null
argument_list|,
literal|"<html>"
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"Error opening file"
argument_list|)
operator|+
literal|" '"
operator|+
name|fileName
operator|+
literal|"'. "
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"File is locked by another JabRef instance."
argument_list|)
operator|+
literal|"<p>"
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"Do you want to override the file lock?"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"File locked"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|YES_NO_OPTION
argument_list|)
decl_stmt|;
if|if
condition|(
name|answer
operator|==
name|JOptionPane
operator|.
name|YES_OPTION
condition|)
block|{
name|FileBasedLock
operator|.
name|deleteLockFile
argument_list|(
name|file
operator|.
name|toPath
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
return|return;
block|}
block|}
elseif|else
if|if
condition|(
operator|!
name|FileBasedLock
operator|.
name|waitForFileLock
argument_list|(
name|file
operator|.
name|toPath
argument_list|()
argument_list|,
literal|10
argument_list|)
condition|)
block|{
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
literal|null
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Error opening file"
argument_list|)
operator|+
literal|" '"
operator|+
name|fileName
operator|+
literal|"'. "
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"File is locked by another JabRef instance."
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Error"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
return|return;
block|}
block|}
name|Charset
name|encoding
init|=
name|Globals
operator|.
name|prefs
operator|.
name|getDefaultEncoding
argument_list|()
decl_stmt|;
name|ParserResult
name|result
decl_stmt|;
name|String
name|errorMessage
init|=
literal|null
decl_stmt|;
try|try
block|{
name|result
operator|=
name|OpenDatabaseAction
operator|.
name|loadDatabase
argument_list|(
name|fileToLoad
argument_list|,
name|encoding
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
literal|"Error loading database "
operator|+
name|fileToLoad
argument_list|,
name|ex
argument_list|)
expr_stmt|;
name|result
operator|=
name|ParserResult
operator|.
name|getNullResult
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|result
operator|.
name|isNullResult
argument_list|()
condition|)
block|{
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
literal|null
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Error opening file"
argument_list|)
operator|+
literal|" '"
operator|+
name|fileName
operator|+
literal|"'"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Error"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
name|String
name|message
init|=
literal|"<html>"
operator|+
name|errorMessage
operator|+
literal|"<p>"
operator|+
operator|(
name|tryingAutosave
condition|?
name|Localization
operator|.
name|lang
argument_list|(
literal|"Error opening autosave of '%0'. Trying to load '%0' instead."
argument_list|,
name|file
operator|.
name|getName
argument_list|()
argument_list|)
else|:
literal|""
comment|/*Globals.lang("Error opening file '%0'.", file.getName())*/
operator|)
operator|+
literal|"</html>"
decl_stmt|;
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
literal|null
argument_list|,
name|message
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Error opening file"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
if|if
condition|(
name|tryingAutosave
condition|)
block|{
name|tryingAutosave
operator|=
literal|false
expr_stmt|;
name|fileToLoad
operator|=
name|file
expr_stmt|;
block|}
else|else
block|{
name|done
operator|=
literal|true
expr_stmt|;
block|}
continue|continue;
block|}
else|else
block|{
name|done
operator|=
literal|true
expr_stmt|;
block|}
specifier|final
name|BasePanel
name|panel
init|=
name|addNewDatabase
argument_list|(
name|result
argument_list|,
name|file
argument_list|,
name|raisePanel
argument_list|)
decl_stmt|;
if|if
condition|(
name|tryingAutosave
condition|)
block|{
name|panel
operator|.
name|markNonUndoableBaseChanged
argument_list|()
expr_stmt|;
block|}
comment|// After adding the database, go through our list and see if
comment|// any post open actions need to be done. For instance, checking
comment|// if we found new entry types that can be imported, or checking
comment|// if the database contents should be modified due to new features
comment|// in this version of JabRef:
specifier|final
name|ParserResult
name|finalReferenceToResult
init|=
name|result
decl_stmt|;
name|SwingUtilities
operator|.
name|invokeLater
argument_list|(
parameter_list|()
lambda|->
name|OpenDatabaseAction
operator|.
name|performPostOpenActions
argument_list|(
name|panel
argument_list|,
name|finalReferenceToResult
argument_list|,
literal|true
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|/**      * Go through the list of post open actions, and perform those that need to be performed.      *      * @param panel  The BasePanel where the database is shown.      * @param result The result of the bib file parse operation.      */
DECL|method|performPostOpenActions (BasePanel panel, ParserResult result, boolean mustRaisePanel)
specifier|public
specifier|static
name|void
name|performPostOpenActions
parameter_list|(
name|BasePanel
name|panel
parameter_list|,
name|ParserResult
name|result
parameter_list|,
name|boolean
name|mustRaisePanel
parameter_list|)
block|{
for|for
control|(
name|PostOpenAction
name|action
range|:
name|OpenDatabaseAction
operator|.
name|POST_OPEN_ACTIONS
control|)
block|{
if|if
condition|(
name|action
operator|.
name|isActionNecessary
argument_list|(
name|result
argument_list|)
condition|)
block|{
if|if
condition|(
name|mustRaisePanel
condition|)
block|{
name|panel
operator|.
name|frame
argument_list|()
operator|.
name|getTabbedPane
argument_list|()
operator|.
name|setSelectedComponent
argument_list|(
name|panel
argument_list|)
expr_stmt|;
block|}
name|action
operator|.
name|performAction
argument_list|(
name|panel
argument_list|,
name|result
argument_list|)
expr_stmt|;
block|}
block|}
block|}
DECL|method|addNewDatabase (ParserResult result, final File file, boolean raisePanel)
specifier|private
name|BasePanel
name|addNewDatabase
parameter_list|(
name|ParserResult
name|result
parameter_list|,
specifier|final
name|File
name|file
parameter_list|,
name|boolean
name|raisePanel
parameter_list|)
block|{
name|String
name|fileName
init|=
name|file
operator|.
name|getPath
argument_list|()
decl_stmt|;
name|BibDatabase
name|database
init|=
name|result
operator|.
name|getDatabase
argument_list|()
decl_stmt|;
name|MetaData
name|meta
init|=
name|result
operator|.
name|getMetaData
argument_list|()
decl_stmt|;
if|if
condition|(
name|result
operator|.
name|hasWarnings
argument_list|()
condition|)
block|{
name|JabRefExecutorService
operator|.
name|INSTANCE
operator|.
name|execute
argument_list|(
parameter_list|()
lambda|->
name|ParserResultWarningDialog
operator|.
name|showParserResultWarningDialog
argument_list|(
name|result
argument_list|,
name|frame
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|Defaults
name|defaults
init|=
operator|new
name|Defaults
argument_list|(
name|BibDatabaseMode
operator|.
name|fromPreference
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|BIBLATEX_DEFAULT_MODE
argument_list|)
argument_list|)
argument_list|)
decl_stmt|;
name|BasePanel
name|basePanel
init|=
operator|new
name|BasePanel
argument_list|(
name|frame
argument_list|,
operator|new
name|BibDatabaseContext
argument_list|(
name|database
argument_list|,
name|meta
argument_list|,
name|file
argument_list|,
name|defaults
argument_list|)
argument_list|)
decl_stmt|;
comment|// file is set to null inside the EventDispatcherThread
name|SwingUtilities
operator|.
name|invokeLater
argument_list|(
parameter_list|()
lambda|->
name|frame
operator|.
name|addTab
argument_list|(
name|basePanel
argument_list|,
name|raisePanel
argument_list|)
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
literal|"Opened database"
argument_list|)
operator|+
literal|" '"
operator|+
name|fileName
operator|+
literal|"' "
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"with"
argument_list|)
operator|+
literal|" "
operator|+
name|database
operator|.
name|getEntryCount
argument_list|()
operator|+
literal|" "
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"entries"
argument_list|)
operator|+
literal|"."
argument_list|)
expr_stmt|;
return|return
name|basePanel
return|;
block|}
comment|/**      * Opens a new database.      */
DECL|method|loadDatabase (File fileToOpen, Charset defaultEncoding)
specifier|public
specifier|static
name|ParserResult
name|loadDatabase
parameter_list|(
name|File
name|fileToOpen
parameter_list|,
name|Charset
name|defaultEncoding
parameter_list|)
throws|throws
name|IOException
block|{
comment|// Open and parse file
name|ParserResult
name|result
init|=
operator|new
name|BibtexImporter
argument_list|()
operator|.
name|importDatabase
argument_list|(
name|fileToOpen
operator|.
name|toPath
argument_list|()
argument_list|,
name|defaultEncoding
argument_list|)
decl_stmt|;
if|if
condition|(
name|SpecialFieldsUtils
operator|.
name|keywordSyncEnabled
argument_list|()
condition|)
block|{
name|NamedCompound
name|compound
init|=
operator|new
name|NamedCompound
argument_list|(
literal|"SpecialFieldSync"
argument_list|)
decl_stmt|;
for|for
control|(
name|BibEntry
name|entry
range|:
name|result
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
control|)
block|{
name|SpecialFieldsUtils
operator|.
name|syncSpecialFieldsFromKeywords
argument_list|(
name|entry
argument_list|,
name|compound
argument_list|)
expr_stmt|;
block|}
name|LOGGER
operator|.
name|debug
argument_list|(
literal|"Synchronized special fields based on keywords"
argument_list|)
expr_stmt|;
block|}
return|return
name|result
return|;
block|}
comment|/**      * Load database (bib-file) or, if there exists, a newer autosave version, unless the flag is set to ignore the autosave      *      * @param name Name of the bib-file to open      * @param ignoreAutosave true if autosave version of the file should be ignored      * @return ParserResult which never is null      */
DECL|method|loadDatabaseOrAutoSave (String name, boolean ignoreAutosave)
specifier|public
specifier|static
name|ParserResult
name|loadDatabaseOrAutoSave
parameter_list|(
name|String
name|name
parameter_list|,
name|boolean
name|ignoreAutosave
parameter_list|)
block|{
comment|// String in OpenDatabaseAction.java
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Opening: "
operator|+
name|name
argument_list|)
expr_stmt|;
name|File
name|file
init|=
operator|new
name|File
argument_list|(
name|name
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|file
operator|.
name|exists
argument_list|()
condition|)
block|{
name|ParserResult
name|pr
init|=
operator|new
name|ParserResult
argument_list|(
literal|null
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|)
decl_stmt|;
name|pr
operator|.
name|setFile
argument_list|(
name|file
argument_list|)
expr_stmt|;
name|pr
operator|.
name|setInvalid
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|LOGGER
operator|.
name|error
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Error"
argument_list|)
operator|+
literal|": "
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"File not found"
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|pr
return|;
block|}
try|try
block|{
if|if
condition|(
operator|!
name|ignoreAutosave
condition|)
block|{
name|boolean
name|autoSaveFound
init|=
name|AutoSaveManager
operator|.
name|newerAutoSaveExists
argument_list|(
name|file
argument_list|)
decl_stmt|;
if|if
condition|(
name|autoSaveFound
condition|)
block|{
comment|// We have found a newer autosave. Make a note of this, so it can be
comment|// handled after startup:
name|ParserResult
name|postp
init|=
operator|new
name|ParserResult
argument_list|(
literal|null
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|)
decl_stmt|;
name|postp
operator|.
name|setPostponedAutosaveFound
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|postp
operator|.
name|setFile
argument_list|(
name|file
argument_list|)
expr_stmt|;
return|return
name|postp
return|;
block|}
block|}
if|if
condition|(
operator|!
name|FileBasedLock
operator|.
name|waitForFileLock
argument_list|(
name|file
operator|.
name|toPath
argument_list|()
argument_list|,
literal|10
argument_list|)
condition|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Error opening file"
argument_list|)
operator|+
literal|" '"
operator|+
name|name
operator|+
literal|"'. "
operator|+
literal|"File is locked by another JabRef instance."
argument_list|)
expr_stmt|;
return|return
name|ParserResult
operator|.
name|getNullResult
argument_list|()
return|;
block|}
name|Charset
name|encoding
init|=
name|Globals
operator|.
name|prefs
operator|.
name|getDefaultEncoding
argument_list|()
decl_stmt|;
name|ParserResult
name|pr
init|=
name|OpenDatabaseAction
operator|.
name|loadDatabase
argument_list|(
name|file
argument_list|,
name|encoding
argument_list|)
decl_stmt|;
name|pr
operator|.
name|setFile
argument_list|(
name|file
argument_list|)
expr_stmt|;
if|if
condition|(
name|pr
operator|.
name|hasWarnings
argument_list|()
condition|)
block|{
for|for
control|(
name|String
name|aWarn
range|:
name|pr
operator|.
name|warnings
argument_list|()
control|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
name|aWarn
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|pr
return|;
block|}
catch|catch
parameter_list|(
name|Throwable
name|ex
parameter_list|)
block|{
name|ParserResult
name|pr
init|=
operator|new
name|ParserResult
argument_list|(
literal|null
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|)
decl_stmt|;
name|pr
operator|.
name|setFile
argument_list|(
name|file
argument_list|)
expr_stmt|;
name|pr
operator|.
name|setInvalid
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|pr
operator|.
name|setErrorMessage
argument_list|(
name|ex
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Problem opening .bib-file"
argument_list|,
name|ex
argument_list|)
expr_stmt|;
return|return
name|pr
return|;
block|}
block|}
block|}
end_class

end_unit

