begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
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

begin_class
DECL|class|FileBasedTestHelper
specifier|public
class|class
name|FileBasedTestHelper
block|{
comment|/**      * Creates a temp directory in the System temp directory.      *<p/>      * Taken from      * http://forum.java.sun.com/thread.jspa?threadID=470197&messageID=2169110      *<p/>      * Author: jfbriere      *      * @return returns null if directory could not created.      */
DECL|method|createTempDir (String prefix)
specifier|public
specifier|static
name|File
name|createTempDir
parameter_list|(
name|String
name|prefix
parameter_list|)
block|{
return|return
name|createTempDir
argument_list|(
name|prefix
argument_list|,
literal|null
argument_list|)
return|;
block|}
comment|/**      * Creates a temp directory in a given directory.      *<p/>      * Taken from      * http://forum.java.sun.com/thread.jspa?threadID=470197&messageID=2169110      *<p/>      * Author: jfbriere      *      * @param directory MayBeNull - null indicates that the system tmp directory      *                  should be used.      * @return returns null if directory could not created.      */
DECL|method|createTempDir (String prefix, File directory)
specifier|public
specifier|static
name|File
name|createTempDir
parameter_list|(
name|String
name|prefix
parameter_list|,
name|File
name|directory
parameter_list|)
block|{
try|try
block|{
name|File
name|tempFile
init|=
name|File
operator|.
name|createTempFile
argument_list|(
name|prefix
argument_list|,
literal|""
argument_list|,
name|directory
argument_list|)
decl_stmt|;
if|if
condition|(
name|tempFile
operator|==
literal|null
condition|)
block|{
return|return
literal|null
return|;
block|}
if|if
condition|(
operator|!
name|tempFile
operator|.
name|delete
argument_list|()
condition|)
block|{
return|return
literal|null
return|;
block|}
if|if
condition|(
operator|!
name|tempFile
operator|.
name|mkdir
argument_list|()
condition|)
block|{
return|return
literal|null
return|;
block|}
return|return
name|tempFile
return|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
return|return
literal|null
return|;
block|}
block|}
comment|/**      * Deletes a directory or file      *<p/>      * Taken from      * http://forum.java.sun.com/thread.jspa?threadID=470197&messageID=2169110      *<p/>      * Author: jfbriere      *      * @param file      */
DECL|method|deleteRecursive (File file)
specifier|public
specifier|static
name|void
name|deleteRecursive
parameter_list|(
name|File
name|file
parameter_list|)
block|{
if|if
condition|(
name|file
operator|.
name|isDirectory
argument_list|()
condition|)
block|{
name|File
index|[]
name|fileArray
init|=
name|file
operator|.
name|listFiles
argument_list|()
decl_stmt|;
if|if
condition|(
name|fileArray
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|File
name|aFileArray
range|:
name|fileArray
control|)
block|{
name|deleteRecursive
argument_list|(
name|aFileArray
argument_list|)
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
operator|!
name|file
operator|.
name|delete
argument_list|()
condition|)
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"Cannot delete file"
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

