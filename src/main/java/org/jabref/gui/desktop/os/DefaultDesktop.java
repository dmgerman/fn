begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.desktop.os
package|package
name|org
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
name|awt
operator|.
name|Desktop
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
name|file
operator|.
name|Path
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
name|slf4j
operator|.
name|Logger
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|LoggerFactory
import|;
end_import

begin_class
DECL|class|DefaultDesktop
specifier|public
class|class
name|DefaultDesktop
implements|implements
name|NativeDesktop
block|{
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Logger
name|LOGGER
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|NativeDesktop
operator|.
name|class
argument_list|)
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
name|Desktop
operator|.
name|getDesktop
argument_list|()
operator|.
name|open
argument_list|(
operator|new
name|File
argument_list|(
name|filePath
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
name|Desktop
operator|.
name|getDesktop
argument_list|()
operator|.
name|open
argument_list|(
operator|new
name|File
argument_list|(
name|filePath
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|openFolderAndSelectFile (Path filePath)
specifier|public
name|void
name|openFolderAndSelectFile
parameter_list|(
name|Path
name|filePath
parameter_list|)
throws|throws
name|IOException
block|{
name|File
name|file
init|=
name|filePath
operator|.
name|toAbsolutePath
argument_list|()
operator|.
name|getParent
argument_list|()
operator|.
name|toFile
argument_list|()
decl_stmt|;
name|Desktop
operator|.
name|getDesktop
argument_list|()
operator|.
name|open
argument_list|(
name|file
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
name|LOGGER
operator|.
name|error
argument_list|(
literal|"This feature is not supported by your Operating System."
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|openPdfWithParameters (String filePath, List<String> parameters)
specifier|public
name|void
name|openPdfWithParameters
parameter_list|(
name|String
name|filePath
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|parameters
parameter_list|)
throws|throws
name|IOException
block|{
comment|//TODO imlement default
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
annotation|@
name|Override
DECL|method|getApplicationDirectory ()
specifier|public
name|Path
name|getApplicationDirectory
parameter_list|()
block|{
return|return
name|getUserDirectory
argument_list|()
return|;
block|}
block|}
end_class

end_unit

