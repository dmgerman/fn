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
name|io
operator|.
name|BufferedReader
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
name|InputStreamReader
import|;
end_import

begin_class
DECL|class|Linux
specifier|public
class|class
name|Linux
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
comment|// TODO: use "xdg-open" instead?
name|String
name|viewer
init|=
name|type
operator|==
literal|null
condition|?
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|PSVIEWER
argument_list|)
else|:
name|type
operator|.
name|getOpenWith
argument_list|()
decl_stmt|;
name|String
index|[]
name|cmdArray
init|=
operator|new
name|String
index|[
literal|2
index|]
decl_stmt|;
name|cmdArray
index|[
literal|0
index|]
operator|=
name|viewer
expr_stmt|;
name|cmdArray
index|[
literal|1
index|]
operator|=
name|filePath
expr_stmt|;
name|Runtime
operator|.
name|getRuntime
argument_list|()
operator|.
name|exec
argument_list|(
name|cmdArray
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
comment|// Use the given app if specified, and the universal "xdg-open" otherwise:
name|String
index|[]
name|openWith
decl_stmt|;
if|if
condition|(
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
condition|)
block|{
name|openWith
operator|=
name|application
operator|.
name|split
argument_list|(
literal|" "
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|openWith
operator|=
operator|new
name|String
index|[]
block|{
literal|"xdg-open"
block|}
expr_stmt|;
block|}
name|String
index|[]
name|cmdArray
init|=
operator|new
name|String
index|[
name|openWith
operator|.
name|length
operator|+
literal|1
index|]
decl_stmt|;
name|System
operator|.
name|arraycopy
argument_list|(
name|openWith
argument_list|,
literal|0
argument_list|,
name|cmdArray
argument_list|,
literal|0
argument_list|,
name|openWith
operator|.
name|length
argument_list|)
expr_stmt|;
name|cmdArray
index|[
name|cmdArray
operator|.
name|length
operator|-
literal|1
index|]
operator|=
name|filePath
expr_stmt|;
name|Runtime
operator|.
name|getRuntime
argument_list|()
operator|.
name|exec
argument_list|(
name|cmdArray
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
name|desktopSession
init|=
name|System
operator|.
name|getenv
argument_list|(
literal|"DESKTOP_SESSION"
argument_list|)
operator|.
name|toLowerCase
argument_list|()
decl_stmt|;
name|String
name|cmd
decl_stmt|;
if|if
condition|(
name|desktopSession
operator|.
name|contains
argument_list|(
literal|"gnome"
argument_list|)
condition|)
block|{
name|cmd
operator|=
literal|"nautilus "
operator|+
name|filePath
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|desktopSession
operator|.
name|contains
argument_list|(
literal|"kde"
argument_list|)
condition|)
block|{
name|cmd
operator|=
literal|"dolphin --select "
operator|+
name|filePath
expr_stmt|;
block|}
else|else
block|{
name|cmd
operator|=
literal|"xdg-open "
operator|+
name|filePath
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|filePath
operator|.
name|lastIndexOf
argument_list|(
name|File
operator|.
name|separator
argument_list|)
argument_list|)
expr_stmt|;
block|}
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
name|Process
name|p
init|=
name|runtime
operator|.
name|exec
argument_list|(
literal|"readlink /etc/alternatives/x-terminal-emulator"
argument_list|)
decl_stmt|;
name|BufferedReader
name|reader
init|=
operator|new
name|BufferedReader
argument_list|(
operator|new
name|InputStreamReader
argument_list|(
name|p
operator|.
name|getInputStream
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
name|String
name|emulatorName
init|=
name|reader
operator|.
name|readLine
argument_list|()
decl_stmt|;
if|if
condition|(
name|emulatorName
operator|!=
literal|null
condition|)
block|{
name|emulatorName
operator|=
name|emulatorName
operator|.
name|substring
argument_list|(
name|emulatorName
operator|.
name|lastIndexOf
argument_list|(
name|File
operator|.
name|separator
argument_list|)
operator|+
literal|1
argument_list|)
expr_stmt|;
if|if
condition|(
name|emulatorName
operator|.
name|contains
argument_list|(
literal|"gnome"
argument_list|)
condition|)
block|{
name|runtime
operator|.
name|exec
argument_list|(
literal|"gnome-terminal --working-directory="
operator|+
name|absolutePath
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|emulatorName
operator|.
name|contains
argument_list|(
literal|"xfce4"
argument_list|)
condition|)
block|{
name|runtime
operator|.
name|exec
argument_list|(
literal|"xfce4-terminal --working-directory="
operator|+
name|absolutePath
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|emulatorName
operator|.
name|contains
argument_list|(
literal|"konsole"
argument_list|)
condition|)
block|{
name|runtime
operator|.
name|exec
argument_list|(
literal|"konsole --workdir="
operator|+
name|absolutePath
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|runtime
operator|.
name|exec
argument_list|(
name|emulatorName
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

