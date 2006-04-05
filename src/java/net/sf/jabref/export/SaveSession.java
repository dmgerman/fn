begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.export
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|export
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
name|Util
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
name|GUIGlobals
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
name|UnsupportedCharsetException
import|;
end_import

begin_comment
comment|/**  * Class used to handle safe storage to disk. Usage: create a SaveSession giving the file to save to, the  * encoding, and whether to make a backup. The SaveSession will provide a Writer to store to, which actually  * goes to a temporary file. The Writer keeps track of whether all characters could be saved, and if not,  * which characters were not encodable.  * After saving is finished, the client should close the Writer. If the save should be put into effect, call  * commit(), otherwise call cancel(). When cancelling, the temporary file is simply deleted and the target  * file remains unchanged. When committing, the temporary file is copied to the target file after making  * a backup if requested and if the target file already existed, and finally the temporary file is deleted.  * If committing fails, the temporary file will not be deleted.  */
end_comment

begin_class
DECL|class|SaveSession
specifier|public
class|class
name|SaveSession
block|{
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
DECL|field|file
DECL|field|tmp
DECL|field|backupFile
name|File
name|file
decl_stmt|,
name|tmp
decl_stmt|,
name|backupFile
decl_stmt|;
DECL|field|encoding
name|String
name|encoding
decl_stmt|;
DECL|field|backup
name|boolean
name|backup
decl_stmt|;
DECL|field|writer
name|VerifyingWriter
name|writer
decl_stmt|;
DECL|method|SaveSession (File file, String encoding, boolean backup)
specifier|public
name|SaveSession
parameter_list|(
name|File
name|file
parameter_list|,
name|String
name|encoding
parameter_list|,
name|boolean
name|backup
parameter_list|)
throws|throws
name|IOException
throws|,
name|UnsupportedCharsetException
block|{
name|this
operator|.
name|file
operator|=
name|file
expr_stmt|;
name|tmp
operator|=
name|File
operator|.
name|createTempFile
argument_list|(
name|TEMP_PREFIX
argument_list|,
name|TEMP_SUFFIX
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
name|String
name|getEncoding
parameter_list|()
block|{
return|return
name|encoding
return|;
block|}
DECL|method|commit ()
specifier|public
name|void
name|commit
parameter_list|()
throws|throws
name|SaveException
block|{
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
name|Util
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
throw|throw
operator|new
name|SaveException
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Save failed during backup creation"
argument_list|)
operator|+
literal|": "
operator|+
name|ex
operator|.
name|getMessage
argument_list|()
argument_list|)
throw|;
block|}
block|}
try|try
block|{
name|Util
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
name|Globals
operator|.
name|lang
argument_list|(
literal|"Save failed while committing changes"
argument_list|)
operator|+
literal|": "
operator|+
name|ex2
operator|.
name|getMessage
argument_list|()
argument_list|)
throw|;
block|}
name|tmp
operator|.
name|delete
argument_list|()
expr_stmt|;
block|}
DECL|method|cancel ()
specifier|public
name|void
name|cancel
parameter_list|()
throws|throws
name|IOException
block|{
name|tmp
operator|.
name|delete
argument_list|()
expr_stmt|;
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
block|}
end_class

end_unit

