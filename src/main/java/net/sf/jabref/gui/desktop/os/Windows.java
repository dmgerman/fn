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
name|file
operator|.
name|Paths
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

begin_class
DECL|class|Windows
specifier|public
class|class
name|Windows
implements|implements
name|NativeDesktop
block|{
DECL|field|DEFAULT_EXECUTABLE_EXTENSION
specifier|private
specifier|static
name|String
name|DEFAULT_EXECUTABLE_EXTENSION
init|=
literal|".exe"
decl_stmt|;
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
name|Optional
argument_list|<
name|ExternalFileType
argument_list|>
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
operator|.
name|isPresent
argument_list|()
operator|&&
operator|!
name|type
operator|.
name|get
argument_list|()
operator|.
name|getOpenWithApplication
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|openFileWithApplication
argument_list|(
name|filePath
argument_list|,
name|type
operator|.
name|get
argument_list|()
operator|.
name|getOpenWithApplication
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// quote String so explorer handles URL query strings correctly
name|String
name|quotePath
init|=
literal|"\""
operator|+
name|filePath
operator|+
literal|"\""
decl_stmt|;
operator|new
name|ProcessBuilder
argument_list|(
literal|"explorer.exe"
argument_list|,
name|quotePath
argument_list|)
operator|.
name|start
argument_list|()
expr_stmt|;
block|}
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
name|String
name|progFiles
init|=
name|System
operator|.
name|getenv
argument_list|(
literal|"ProgramFiles(x86)"
argument_list|)
decl_stmt|;
if|if
condition|(
name|progFiles
operator|==
literal|null
condition|)
block|{
name|progFiles
operator|=
name|System
operator|.
name|getenv
argument_list|(
literal|"ProgramFiles"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|(
name|directoryName
operator|!=
literal|null
operator|)
operator|&&
operator|!
name|directoryName
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
name|Paths
operator|.
name|get
argument_list|(
name|progFiles
argument_list|,
name|directoryName
argument_list|,
name|programName
operator|+
name|DEFAULT_EXECUTABLE_EXTENSION
argument_list|)
operator|.
name|toString
argument_list|()
return|;
block|}
return|return
name|Paths
operator|.
name|get
argument_list|(
name|progFiles
argument_list|,
name|programName
operator|+
name|DEFAULT_EXECUTABLE_EXTENSION
argument_list|)
operator|.
name|toString
argument_list|()
return|;
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
operator|new
name|ProcessBuilder
argument_list|(
name|Paths
operator|.
name|get
argument_list|(
name|application
argument_list|)
operator|.
name|toString
argument_list|()
argument_list|,
name|Paths
operator|.
name|get
argument_list|(
name|filePath
argument_list|)
operator|.
name|toString
argument_list|()
argument_list|)
operator|.
name|start
argument_list|()
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
operator|new
name|ProcessBuilder
argument_list|(
literal|"explorer.exe"
argument_list|,
literal|"/select,"
argument_list|,
name|filePath
argument_list|)
operator|.
name|start
argument_list|()
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
name|ProcessBuilder
name|process
init|=
operator|new
name|ProcessBuilder
argument_list|(
literal|"cmd.exe"
argument_list|,
literal|"/c"
argument_list|,
literal|"start"
argument_list|)
decl_stmt|;
name|process
operator|.
name|directory
argument_list|(
operator|new
name|File
argument_list|(
name|absolutePath
argument_list|)
argument_list|)
expr_stmt|;
name|process
operator|.
name|start
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

