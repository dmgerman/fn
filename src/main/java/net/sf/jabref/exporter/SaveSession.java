begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|gui
operator|.
name|GUIGlobals
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
name|FieldChange
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
name|io
operator|.
name|FileUtil
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
name|io
operator|.
name|FileOutputStream
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
name|List
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
comment|/**  * Class used to handle safe storage to disk.  *<p>  * Usage: create a SaveSession giving the file to save to, the encoding, and whether to make a backup. The SaveSession  * will provide a Writer to store to, which actually goes to a temporary file. The Writer keeps track of whether all  * characters could be saved, and if not, which characters were not encodable.  *<p>  * After saving is finished, the client should close the Writer. If the save should be put into effect, call commit(),  * otherwise call cancel(). When cancelling, the temporary file is simply deleted and the target file remains unchanged.  * When committing, the temporary file is copied to the target file after making a backup if requested and if the target  * file already existed, and finally the temporary file is deleted.  *<p>  * If committing fails, the temporary file will not be deleted.  */
end_comment

begin_class
DECL|class|SaveSession
specifier|public
class|class
name|SaveSession
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
name|SaveSession
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|LOCKFILE_SUFFIX
specifier|public
specifier|static
specifier|final
name|String
name|LOCKFILE_SUFFIX
init|=
literal|".lock"
decl_stmt|;
comment|// The age in ms of a lockfile before JabRef will offer to "steal" the locked file:
DECL|field|LOCKFILE_CRITICAL_AGE
specifier|public
specifier|static
specifier|final
name|long
name|LOCKFILE_CRITICAL_AGE
init|=
literal|60000
decl_stmt|;
DECL|field|TEMP_PREFIX
specifier|private
specifier|static
specifier|final
name|String
name|TEMP_PREFIX
init|=
literal|"jabref"
decl_stmt|;
DECL|field|TEMP_SUFFIX
specifier|private
specifier|static
specifier|final
name|String
name|TEMP_SUFFIX
init|=
literal|"save.bib"
decl_stmt|;
DECL|field|tmp
specifier|private
specifier|final
name|File
name|tmp
decl_stmt|;
DECL|field|encoding
specifier|private
specifier|final
name|Charset
name|encoding
decl_stmt|;
DECL|field|backup
specifier|private
name|boolean
name|backup
decl_stmt|;
DECL|field|useLockFile
specifier|private
specifier|final
name|boolean
name|useLockFile
decl_stmt|;
DECL|field|writer
specifier|private
specifier|final
name|VerifyingWriter
name|writer
decl_stmt|;
DECL|field|undoableFieldChanges
specifier|private
specifier|final
name|List
argument_list|<
name|FieldChange
argument_list|>
name|undoableFieldChanges
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
DECL|method|SaveSession (Charset encoding, boolean backup)
specifier|public
name|SaveSession
parameter_list|(
name|Charset
name|encoding
parameter_list|,
name|boolean
name|backup
parameter_list|)
throws|throws
name|IOException
block|{
name|tmp
operator|=
name|File
operator|.
name|createTempFile
argument_list|(
name|SaveSession
operator|.
name|TEMP_PREFIX
argument_list|,
name|SaveSession
operator|.
name|TEMP_SUFFIX
argument_list|)
expr_stmt|;
name|useLockFile
operator|=
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|USE_LOCK_FILES
argument_list|)
expr_stmt|;
name|this
operator|.
name|backup
operator|=
name|backup
expr_stmt|;
name|this
operator|.
name|encoding
operator|=
name|encoding
expr_stmt|;
comment|/* Using 	   try (FileOutputStream fos = new FileOutputStream(tmp)) { 	       writer = new VerifyingWriter(fos, encoding); 	   } 	   doesn't work since fos is closed after assigning write, 	   leading to that fos may never be closed at all 	 */
name|writer
operator|=
operator|new
name|VerifyingWriter
argument_list|(
operator|new
name|FileOutputStream
argument_list|(
name|tmp
argument_list|)
argument_list|,
name|encoding
argument_list|)
expr_stmt|;
block|}
DECL|method|getWriter ()
specifier|public
name|VerifyingWriter
name|getWriter
parameter_list|()
block|{
return|return
name|writer
return|;
block|}
DECL|method|getEncoding ()
specifier|public
name|Charset
name|getEncoding
parameter_list|()
block|{
return|return
name|encoding
return|;
block|}
DECL|method|setUseBackup (boolean useBackup)
specifier|public
name|void
name|setUseBackup
parameter_list|(
name|boolean
name|useBackup
parameter_list|)
block|{
name|this
operator|.
name|backup
operator|=
name|useBackup
expr_stmt|;
block|}
DECL|method|commit (File file)
specifier|public
name|void
name|commit
parameter_list|(
name|File
name|file
parameter_list|)
throws|throws
name|SaveException
block|{
if|if
condition|(
name|file
operator|==
literal|null
condition|)
block|{
return|return;
block|}
if|if
condition|(
name|file
operator|.
name|exists
argument_list|()
operator|&&
name|backup
condition|)
block|{
name|String
name|name
init|=
name|file
operator|.
name|getName
argument_list|()
decl_stmt|;
name|String
name|path
init|=
name|file
operator|.
name|getParent
argument_list|()
decl_stmt|;
name|File
name|backupFile
init|=
operator|new
name|File
argument_list|(
name|path
argument_list|,
name|name
operator|+
name|GUIGlobals
operator|.
name|backupExt
argument_list|)
decl_stmt|;
try|try
block|{
name|FileUtil
operator|.
name|copyFile
argument_list|(
name|file
argument_list|,
name|backupFile
argument_list|,
literal|true
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
literal|"Problem copying file"
argument_list|,
name|ex
argument_list|)
expr_stmt|;
throw|throw
name|SaveException
operator|.
name|BACKUP_CREATION
throw|;
block|}
block|}
try|try
block|{
if|if
condition|(
name|useLockFile
condition|)
block|{
try|try
block|{
if|if
condition|(
name|createLockFile
argument_list|(
name|file
argument_list|)
condition|)
block|{
comment|// Oops, the lock file already existed. Try to wait it out:
if|if
condition|(
operator|!
name|FileBasedLock
operator|.
name|waitForFileLock
argument_list|(
name|file
argument_list|,
literal|10
argument_list|)
condition|)
block|{
throw|throw
name|SaveException
operator|.
name|FILE_LOCKED
throw|;
block|}
block|}
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
literal|"Error when creating lock file."
argument_list|,
name|ex
argument_list|)
expr_stmt|;
block|}
block|}
name|FileUtil
operator|.
name|copyFile
argument_list|(
name|tmp
argument_list|,
name|file
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|ex2
parameter_list|)
block|{
comment|// If something happens here, what can we do to correct the problem? The file is corrupted, but we still
comment|// have a clean copy in tmp. However, we just failed to copy tmp to file, so it's not likely that
comment|// repeating the action will have a different result.
comment|// On the other hand, our temporary file should still be clean, and won't be deleted.
throw|throw
operator|new
name|SaveException
argument_list|(
literal|"Save failed while committing changes: "
operator|+
name|ex2
operator|.
name|getMessage
argument_list|()
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Save failed while committing changes: %0"
argument_list|,
name|ex2
operator|.
name|getMessage
argument_list|()
argument_list|)
argument_list|)
throw|;
block|}
finally|finally
block|{
if|if
condition|(
name|useLockFile
condition|)
block|{
name|deleteLockFile
argument_list|(
name|file
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
operator|!
name|tmp
operator|.
name|delete
argument_list|()
condition|)
block|{
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Cannot delete temporary file"
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|cancel ()
specifier|public
name|void
name|cancel
parameter_list|()
block|{
if|if
condition|(
operator|!
name|tmp
operator|.
name|delete
argument_list|()
condition|)
block|{
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Cannot delete temporary file"
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Check if a lock file exists, and create it if it doesn't.      *      * @return true if the lock file already existed      * @throws IOException if something happens during creation.      */
DECL|method|createLockFile (File file)
specifier|private
name|boolean
name|createLockFile
parameter_list|(
name|File
name|file
parameter_list|)
throws|throws
name|IOException
block|{
name|File
name|lock
init|=
operator|new
name|File
argument_list|(
name|file
operator|.
name|getPath
argument_list|()
operator|+
name|SaveSession
operator|.
name|LOCKFILE_SUFFIX
argument_list|)
decl_stmt|;
if|if
condition|(
name|lock
operator|.
name|exists
argument_list|()
condition|)
block|{
return|return
literal|true
return|;
block|}
try|try
init|(
name|FileOutputStream
name|out
init|=
operator|new
name|FileOutputStream
argument_list|(
name|lock
argument_list|)
init|)
block|{
name|out
operator|.
name|write
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|out
operator|.
name|close
argument_list|()
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
literal|"Error when creating lock file."
argument_list|,
name|ex
argument_list|)
expr_stmt|;
block|}
name|lock
operator|.
name|deleteOnExit
argument_list|()
expr_stmt|;
return|return
literal|false
return|;
block|}
comment|/**      * Check if a lock file exists, and delete it if it does.      *      * @return true if the lock file existed, false otherwise.      * @throws IOException if something goes wrong.      */
DECL|method|deleteLockFile (File file)
specifier|private
name|boolean
name|deleteLockFile
parameter_list|(
name|File
name|file
parameter_list|)
block|{
name|File
name|lock
init|=
operator|new
name|File
argument_list|(
name|file
operator|.
name|getPath
argument_list|()
operator|+
name|SaveSession
operator|.
name|LOCKFILE_SUFFIX
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|lock
operator|.
name|exists
argument_list|()
condition|)
block|{
return|return
literal|false
return|;
block|}
if|if
condition|(
operator|!
name|lock
operator|.
name|delete
argument_list|()
condition|)
block|{
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Cannot delete lock file"
argument_list|)
expr_stmt|;
block|}
return|return
literal|true
return|;
block|}
DECL|method|getTemporaryFile ()
specifier|public
name|File
name|getTemporaryFile
parameter_list|()
block|{
return|return
name|tmp
return|;
block|}
DECL|method|getUndoableFieldChanges ()
specifier|public
name|List
argument_list|<
name|FieldChange
argument_list|>
name|getUndoableFieldChanges
parameter_list|()
block|{
return|return
name|undoableFieldChanges
return|;
block|}
DECL|method|addUndoableFieldChanges (List<FieldChange> undoableFieldChanges)
specifier|public
name|void
name|addUndoableFieldChanges
parameter_list|(
name|List
argument_list|<
name|FieldChange
argument_list|>
name|undoableFieldChanges
parameter_list|)
block|{
name|this
operator|.
name|undoableFieldChanges
operator|.
name|addAll
argument_list|(
name|undoableFieldChanges
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

