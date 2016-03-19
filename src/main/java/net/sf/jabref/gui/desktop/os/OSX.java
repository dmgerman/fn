begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.desktop.os
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|desktop
operator|.
name|os
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
name|external
operator|.
name|ExternalFileType
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
name|external
operator|.
name|ExternalFileTypes
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|*
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

begin_class
DECL|class|OSX
specifier|public
class|class
name|OSX
implements|implements
name|NativeDesktop
block|{
annotation|@
name|Override
DECL|method|openFile (String filePath, String fileType)
specifier|public
name|void
name|openFile
parameter_list|(
name|String
name|filePath
parameter_list|,
name|String
name|fileType
parameter_list|)
throws|throws
name|IOException
block|{
name|ExternalFileType
name|type
init|=
name|ExternalFileTypes
operator|.
name|getInstance
argument_list|()
operator|.
name|getExternalFileTypeByExt
argument_list|(
name|fileType
argument_list|)
decl_stmt|;
if|if
condition|(
name|type
operator|==
literal|null
operator|&&
operator|!
name|type
operator|.
name|getOpenWithApplication
argument_list|()
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|openFileWithApplication
argument_list|(
name|filePath
argument_list|,
name|type
operator|.
name|getOpenWithApplication
argument_list|()
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|String
index|[]
name|cmd
init|=
block|{
literal|"/usr/bin/open"
block|,
name|filePath
block|}
decl_stmt|;
name|Runtime
operator|.
name|getRuntime
argument_list|()
operator|.
name|exec
argument_list|(
name|cmd
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
DECL|method|openFileWithApplication (String filePath, String application)
specifier|public
name|void
name|openFileWithApplication
parameter_list|(
name|String
name|filePath
parameter_list|,
name|String
name|application
parameter_list|)
throws|throws
name|IOException
block|{
comment|// Use "-a<application>" if the app is specified, and just "open<filename>" otherwise:
name|String
index|[]
name|cmd
init|=
operator|(
name|application
operator|!=
literal|null
operator|)
operator|&&
operator|!
name|application
operator|.
name|isEmpty
argument_list|()
condition|?
operator|new
name|String
index|[]
block|{
literal|"/usr/bin/open"
block|,
literal|"-a"
block|,
name|application
block|,
name|filePath
block|}
else|:
operator|new
name|String
index|[]
block|{
literal|"/usr/bin/open"
block|,
name|filePath
block|}
decl_stmt|;
name|Runtime
operator|.
name|getRuntime
argument_list|()
operator|.
name|exec
argument_list|(
name|cmd
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|openFolderAndSelectFile (String filePath)
specifier|public
name|void
name|openFolderAndSelectFile
parameter_list|(
name|String
name|filePath
parameter_list|)
throws|throws
name|IOException
block|{
name|File
name|file
init|=
operator|new
name|File
argument_list|(
name|filePath
argument_list|)
decl_stmt|;
name|Desktop
operator|.
name|getDesktop
argument_list|()
operator|.
name|open
argument_list|(
name|file
operator|.
name|getParentFile
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|openConsole (String absolutePath)
specifier|public
name|void
name|openConsole
parameter_list|(
name|String
name|absolutePath
parameter_list|)
throws|throws
name|IOException
block|{
name|Runtime
name|runtime
init|=
name|Runtime
operator|.
name|getRuntime
argument_list|()
decl_stmt|;
name|runtime
operator|.
name|exec
argument_list|(
literal|"open -a Terminal "
operator|+
name|absolutePath
argument_list|,
literal|null
argument_list|,
operator|new
name|File
argument_list|(
name|absolutePath
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|detectProgramPath (String programName, String directoryName)
specifier|public
name|String
name|detectProgramPath
parameter_list|(
name|String
name|programName
parameter_list|,
name|String
name|directoryName
parameter_list|)
block|{
return|return
name|programName
return|;
block|}
block|}
end_class

end_unit

