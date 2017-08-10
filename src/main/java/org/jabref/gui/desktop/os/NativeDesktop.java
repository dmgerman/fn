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
name|List
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
DECL|method|openFolderAndSelectFile (Path file)
name|void
name|openFolderAndSelectFile
parameter_list|(
name|Path
name|file
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
comment|/**      * This method opens a pdf using the giving the parameters to the executing pdf reader      * @param filePath absolute path to the pdf file to be opened      * @param parameters console parameters depending on the pdf reader      * @throws IOException      */
DECL|method|openPdfWithParameters (String filePath, List<String> parameters)
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
function_decl|;
DECL|method|detectProgramPath (String programName, String directoryName)
name|String
name|detectProgramPath
parameter_list|(
name|String
name|programName
parameter_list|,
name|String
name|directoryName
parameter_list|)
function_decl|;
comment|/**      * Returns the path to the system's applications folder.      *      * @return the path to the applications folder.      */
DECL|method|getApplicationDirectory ()
name|Path
name|getApplicationDirectory
parameter_list|()
function_decl|;
comment|/**      * Returns the path to the system's user directory.      *      * @return the path to the user directory.      */
DECL|method|getUserDirectory ()
specifier|default
name|Path
name|getUserDirectory
parameter_list|()
block|{
return|return
name|Paths
operator|.
name|get
argument_list|(
name|System
operator|.
name|getProperty
argument_list|(
literal|"user.home"
argument_list|)
argument_list|)
return|;
block|}
block|}
end_interface

end_unit

