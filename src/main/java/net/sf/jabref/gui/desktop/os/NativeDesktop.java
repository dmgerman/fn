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
name|IOException
import|;
end_import

begin_interface
DECL|interface|NativeDesktop
specifier|public
interface|interface
name|NativeDesktop
block|{
DECL|method|openFile (String filePath, String fileType)
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
function_decl|;
comment|/**      * Opens a file on an Operating System, using the given application.      *      * @param filePath The filename.      * @param application Link to the app that opens the file.      * @throws IOException      */
DECL|method|openFileWithApplication (String filePath, String application)
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
function_decl|;
DECL|method|openFolderAndSelectFile (String filePath)
name|void
name|openFolderAndSelectFile
parameter_list|(
name|String
name|filePath
parameter_list|)
throws|throws
name|IOException
function_decl|;
DECL|method|openConsole (String absolutePath)
name|void
name|openConsole
parameter_list|(
name|String
name|absolutePath
parameter_list|)
throws|throws
name|IOException
function_decl|;
block|}
end_interface

end_unit

