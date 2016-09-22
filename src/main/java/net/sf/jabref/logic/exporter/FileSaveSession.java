begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.exporter
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
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
name|Files
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
name|Path
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
comment|/**  * Class used to handle safe storage to disk.  *<p>  * Usage: create a SaveSession giving the file to save to, the encoding, and whether to make a backup. The SaveSession  * will provide a Writer to store to, which actually goes to a temporary file. The Writer keeps track of whether all  * characters could be saved, and if not, which characters were not encodable.  *<p>  * After saving is finished, the client should close the Writer. If the save should be put into effect, call commit(),  * otherwise call cancel(). When canceling, the temporary file is simply deleted and the target file remains unchanged.  * When committing, the temporary file is copied to the target file after making a backup if requested and if the target  * file already existed, and finally the temporary file is deleted.  *<p>  * If committing fails, the temporary file will not be deleted.  */
end_comment

begin_class
DECL|class|FileSaveSession
specifier|public
class|class
name|FileSaveSession
extends|extends
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
name|FileSaveSession
operator|.
name|class
argument_list|)
decl_stmt|;
comment|// Filenames.
DECL|field|BACKUP_EXTENSION
specifier|private
specifier|static
specifier|final
name|String
name|BACKUP_EXTENSION
init|=
literal|".bak"
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
DECL|field|temporaryFile
specifier|private
specifier|final
name|Path
name|temporaryFile
decl_stmt|;
DECL|method|FileSaveSession (Charset encoding, boolean backup)
specifier|public
name|FileSaveSession
parameter_list|(
name|Charset
name|encoding
parameter_list|,
name|boolean
name|backup
parameter_list|)
throws|throws
name|SaveException
block|{
name|this
argument_list|(
name|encoding
argument_list|,
name|backup
argument_list|,
name|createTemporaryFile
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|FileSaveSession (Charset encoding, boolean backup, Path temporaryFile)
specifier|public
name|FileSaveSession
parameter_list|(
name|Charset
name|encoding
parameter_list|,
name|boolean
name|backup
parameter_list|,
name|Path
name|temporaryFile
parameter_list|)
throws|throws
name|SaveException
block|{
name|super
argument_list|(
name|encoding
argument_list|,
name|backup
argument_list|,
name|getWriterForFile
argument_list|(
name|encoding
argument_list|,
name|temporaryFile
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|temporaryFile
operator|=
name|temporaryFile
expr_stmt|;
block|}
DECL|method|getWriterForFile (Charset encoding, Path file)
specifier|private
specifier|static
name|VerifyingWriter
name|getWriterForFile
parameter_list|(
name|Charset
name|encoding
parameter_list|,
name|Path
name|file
parameter_list|)
throws|throws
name|SaveException
block|{
try|try
block|{
return|return
operator|new
name|VerifyingWriter
argument_list|(
name|Files
operator|.
name|newOutputStream
argument_list|(
name|file
argument_list|)
argument_list|,
name|encoding
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|SaveException
argument_list|(
name|e
argument_list|)
throw|;
block|}
block|}
DECL|method|createTemporaryFile ()
specifier|private
specifier|static
name|Path
name|createTemporaryFile
parameter_list|()
throws|throws
name|SaveException
block|{
try|try
block|{
return|return
name|Files
operator|.
name|createTempFile
argument_list|(
name|FileSaveSession
operator|.
name|TEMP_PREFIX
argument_list|,
name|FileSaveSession
operator|.
name|TEMP_SUFFIX
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|SaveException
argument_list|(
name|e
argument_list|)
throw|;
block|}
block|}
annotation|@
name|Override
DECL|method|commit (Path file)
specifier|public
name|void
name|commit
parameter_list|(
name|Path
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
name|backup
operator|&&
name|Files
operator|.
name|exists
argument_list|(
name|file
argument_list|)
condition|)
block|{
name|Path
name|fileName
init|=
name|file
operator|.
name|getFileName
argument_list|()
decl_stmt|;
name|Path
name|backupFile
init|=
name|file
operator|.
name|resolveSibling
argument_list|(
name|fileName
operator|+
name|BACKUP_EXTENSION
argument_list|)
decl_stmt|;
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
try|try
block|{
comment|// Always use a lock file
try|try
block|{
if|if
condition|(
name|FileBasedLock
operator|.
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
name|FileUtil
operator|.
name|copyFile
argument_list|(
name|temporaryFile
argument_list|,
name|file
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
finally|finally
block|{
name|FileBasedLock
operator|.
name|deleteLockFile
argument_list|(
name|file
argument_list|)
expr_stmt|;
block|}
try|try
block|{
name|Files
operator|.
name|deleteIfExists
argument_list|(
name|temporaryFile
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Cannot delete temporary file"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
DECL|method|cancel ()
specifier|public
name|void
name|cancel
parameter_list|()
block|{
try|try
block|{
name|Files
operator|.
name|deleteIfExists
argument_list|(
name|temporaryFile
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Cannot delete temporary file"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

