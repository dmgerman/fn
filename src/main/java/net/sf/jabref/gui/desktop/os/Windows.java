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

begin_class
DECL|class|Windows
specifier|public
class|class
name|Windows
implements|implements
name|NativeDesktop
block|{
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
name|progFiles
operator|+
literal|"\\"
operator|+
name|directoryName
operator|+
literal|"\\"
operator|+
name|programName
operator|+
literal|".exe"
return|;
block|}
return|return
name|progFiles
operator|+
literal|"\\"
operator|+
name|programName
operator|+
literal|".exe"
return|;
block|}
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
comment|// escape& and spaces
name|Runtime
operator|.
name|getRuntime
argument_list|()
operator|.
name|exec
argument_list|(
literal|"cmd.exe /c start "
operator|+
name|filePath
operator|.
name|replace
argument_list|(
literal|"&"
argument_list|,
literal|"\"&\""
argument_list|)
operator|.
name|replace
argument_list|(
literal|" "
argument_list|,
literal|"\" \""
argument_list|)
argument_list|)
expr_stmt|;
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
name|Runtime
operator|.
name|getRuntime
argument_list|()
operator|.
name|exec
argument_list|(
name|Paths
operator|.
name|get
argument_list|(
name|application
argument_list|)
operator|+
literal|" "
operator|+
name|Paths
operator|.
name|get
argument_list|(
name|filePath
argument_list|)
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
name|String
name|escapedLink
init|=
name|filePath
operator|.
name|replace
argument_list|(
literal|"&"
argument_list|,
literal|"\"&\""
argument_list|)
decl_stmt|;
name|String
name|cmd
init|=
literal|"explorer.exe /select,\""
operator|+
name|escapedLink
operator|+
literal|"\""
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
literal|"cmd.exe /c start"
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
block|}
end_class

end_unit

