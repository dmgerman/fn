begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
package|;
end_package

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
name|java
operator|.
name|util
operator|.
name|Optional
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|Alert
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|ButtonType
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|DialogPane
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|stage
operator|.
name|FileChooser
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|util
operator|.
name|DirectoryDialogConfiguration
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|util
operator|.
name|FileDialogConfiguration
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|l10n
operator|.
name|Localization
import|;
end_import

begin_comment
comment|/**  * This interface provides methods to create dialogs and show them to the user.  */
end_comment

begin_interface
DECL|interface|DialogService
specifier|public
interface|interface
name|DialogService
block|{
DECL|method|showInputDialogAndWait (String title, String content)
name|Optional
argument_list|<
name|String
argument_list|>
name|showInputDialogAndWait
parameter_list|(
name|String
name|title
parameter_list|,
name|String
name|content
parameter_list|)
function_decl|;
comment|/**      * This will create and display a new information dialog.      * It will include a blue information icon on the left and      * a single OK Button. To create an information dialog with custom      * buttons see also {@link #showCustomButtonDialogAndWait(Alert.AlertType, String, String, ButtonType...)}      */
DECL|method|showInformationDialogAndWait (String title, String content)
name|void
name|showInformationDialogAndWait
parameter_list|(
name|String
name|title
parameter_list|,
name|String
name|content
parameter_list|)
function_decl|;
comment|/**      * This will create and display a new information dialog.      * It will include a yellow warning icon on the left and      * a single OK Button. To create a warning dialog with custom      * buttons see also {@link #showCustomButtonDialogAndWait(Alert.AlertType, String, String, ButtonType...)}      */
DECL|method|showWarningDialogAndWait (String title, String content)
name|void
name|showWarningDialogAndWait
parameter_list|(
name|String
name|title
parameter_list|,
name|String
name|content
parameter_list|)
function_decl|;
comment|/**      * This will create and display a new error dialog.      * It will include a red error icon on the left and      * a single OK Button. To create a error dialog with custom      * buttons see also {@link #showCustomButtonDialogAndWait(Alert.AlertType, String, String, ButtonType...)}      */
DECL|method|showErrorDialogAndWait (String title, String content)
name|void
name|showErrorDialogAndWait
parameter_list|(
name|String
name|title
parameter_list|,
name|String
name|content
parameter_list|)
function_decl|;
comment|/**      * Create and display error dialog displaying the given exception.      * @param message the error message      * @param exception the exception causing the error      */
DECL|method|showErrorDialogAndWait (String message, Throwable exception)
name|void
name|showErrorDialogAndWait
parameter_list|(
name|String
name|message
parameter_list|,
name|Throwable
name|exception
parameter_list|)
function_decl|;
comment|/**      * Create and display error dialog displaying the given exception.      * @param exception the exception causing the error      */
DECL|method|showErrorDialogAndWait (Exception exception)
specifier|default
name|void
name|showErrorDialogAndWait
parameter_list|(
name|Exception
name|exception
parameter_list|)
block|{
name|showErrorDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Unhandled exception occurred."
argument_list|)
argument_list|,
name|exception
argument_list|)
expr_stmt|;
block|}
comment|/**      * Create and display error dialog displaying the given message.      * @param message the error message      */
DECL|method|showErrorDialogAndWait (String message)
name|void
name|showErrorDialogAndWait
parameter_list|(
name|String
name|message
parameter_list|)
function_decl|;
comment|/**      * This will create and display a new confirmation dialog.      * It will include a blue question icon on the left and      * a OK and Cancel button. To create a confirmation dialog with custom      * buttons see also {@link #showCustomButtonDialogAndWait(Alert.AlertType, String, String, ButtonType...)}      *      * @return true if the use clicked "OK" otherwise false      */
DECL|method|showConfirmationDialogAndWait (String title, String content)
name|boolean
name|showConfirmationDialogAndWait
parameter_list|(
name|String
name|title
parameter_list|,
name|String
name|content
parameter_list|)
function_decl|;
comment|/**      * Create and display a new confirmation dialog.      * It will include a blue question icon on the left and      * a OK (with given label) and Cancel button. To create a confirmation dialog with custom      * buttons see also {@link #showCustomButtonDialogAndWait(Alert.AlertType, String, String, ButtonType...)}      *      * @return true if the use clicked "OK" otherwise false      */
DECL|method|showConfirmationDialogAndWait (String title, String content, String okButtonLabel)
name|boolean
name|showConfirmationDialogAndWait
parameter_list|(
name|String
name|title
parameter_list|,
name|String
name|content
parameter_list|,
name|String
name|okButtonLabel
parameter_list|)
function_decl|;
comment|/**      * Create and display a new confirmation dialog.      * It will include a blue question icon on the left and      * a OK (with given label) and Cancel (also with given label) button. To create a confirmation dialog with custom      * buttons see also {@link #showCustomButtonDialogAndWait(Alert.AlertType, String, String, ButtonType...)}      *      * @return true if the use clicked "OK" otherwise false      */
DECL|method|showConfirmationDialogAndWait (String title, String content, String okButtonLabel, String cancelButtonLabel)
name|boolean
name|showConfirmationDialogAndWait
parameter_list|(
name|String
name|title
parameter_list|,
name|String
name|content
parameter_list|,
name|String
name|okButtonLabel
parameter_list|,
name|String
name|cancelButtonLabel
parameter_list|)
function_decl|;
comment|/**      * This will create and display a new dialog of the specified      * {@link Alert.AlertType} but with user defined buttons as optional      * {@link ButtonType}s.      *      * @return Optional with the pressed Button as ButtonType      */
DECL|method|showCustomButtonDialogAndWait (Alert.AlertType type, String title, String content, ButtonType... buttonTypes)
name|Optional
argument_list|<
name|ButtonType
argument_list|>
name|showCustomButtonDialogAndWait
parameter_list|(
name|Alert
operator|.
name|AlertType
name|type
parameter_list|,
name|String
name|title
parameter_list|,
name|String
name|content
parameter_list|,
name|ButtonType
modifier|...
name|buttonTypes
parameter_list|)
function_decl|;
comment|/**      * This will create and display a new dialog showing a custom {@link DialogPane}      * and using custom {@link ButtonType}s.      *      * @return Optional with the pressed Button as ButtonType      */
DECL|method|showCustomDialogAndWait (String title, DialogPane contentPane, ButtonType... buttonTypes)
name|Optional
argument_list|<
name|ButtonType
argument_list|>
name|showCustomDialogAndWait
parameter_list|(
name|String
name|title
parameter_list|,
name|DialogPane
name|contentPane
parameter_list|,
name|ButtonType
modifier|...
name|buttonTypes
parameter_list|)
function_decl|;
comment|/**      * Shows a custom dialog and returns the result.      *      * @param dialog dialog to show      * @param<R>    type of result      */
DECL|method|showCustomDialogAndWait (Dialog<R> dialog)
parameter_list|<
name|R
parameter_list|>
name|Optional
argument_list|<
name|R
argument_list|>
name|showCustomDialogAndWait
parameter_list|(
name|Dialog
argument_list|<
name|R
argument_list|>
name|dialog
parameter_list|)
function_decl|;
comment|/**      * Notify the user in an non-blocking way (i.e., update status message instead of showing a dialog).      * @param message the message to show.      */
DECL|method|notify (String message)
name|void
name|notify
parameter_list|(
name|String
name|message
parameter_list|)
function_decl|;
comment|/**      * Shows a new file save dialog. The method doesn't return until the      * displayed file save dialog is dismissed. The return value specifies the      * file chosen by the user or an empty {@link Optional} if no selection has been made.      *      * @return the selected file or an empty {@link Optional} if no file has been selected      */
DECL|method|showFileSaveDialog (FileDialogConfiguration fileDialogConfiguration)
name|Optional
argument_list|<
name|Path
argument_list|>
name|showFileSaveDialog
parameter_list|(
name|FileDialogConfiguration
name|fileDialogConfiguration
parameter_list|)
function_decl|;
comment|/**      * Shows a new file open dialog. The method doesn't return until the      * displayed open dialog is dismissed. The return value specifies      * the file chosen by the user or an empty {@link Optional} if no selection has been      * made.      *      * @return the selected file or an empty {@link Optional} if no file has been selected      */
DECL|method|showFileOpenDialog (FileDialogConfiguration fileDialogConfiguration)
name|Optional
argument_list|<
name|Path
argument_list|>
name|showFileOpenDialog
parameter_list|(
name|FileDialogConfiguration
name|fileDialogConfiguration
parameter_list|)
function_decl|;
comment|/**      * Shows a new file open dialog. The method doesn't return until the      * displayed open dialog is dismissed. The return value specifies      * the files chosen by the user or an empty {@link List} if no selection has been      * made.      *      * @return the selected files or an empty {@link List} if no file has been selected      */
DECL|method|showFileOpenDialogAndGetMultipleFiles (FileDialogConfiguration fileDialogConfiguration)
name|List
argument_list|<
name|Path
argument_list|>
name|showFileOpenDialogAndGetMultipleFiles
parameter_list|(
name|FileDialogConfiguration
name|fileDialogConfiguration
parameter_list|)
function_decl|;
comment|/**      * Shows a new directory selection dialog. The method doesn't return until the      * displayed open dialog is dismissed. The return value specifies      * the file chosen by the user or an empty {@link Optional} if no selection has been      * made.      *      * @return the selected directory or an empty {@link Optional} if no directory has been selected      */
DECL|method|showDirectorySelectionDialog (DirectoryDialogConfiguration directoryDialogConfiguration)
name|Optional
argument_list|<
name|Path
argument_list|>
name|showDirectorySelectionDialog
parameter_list|(
name|DirectoryDialogConfiguration
name|directoryDialogConfiguration
parameter_list|)
function_decl|;
comment|/**      * Gets the configured {@link FileChooser}, should only be necessary in rare use cases.      * For normal usage use the show-Methods which directly return the selected file(s)      * @param fileDialogConfiguration      * @return A configured instance of the {@link FileChooser}      */
DECL|method|getConfiguredFileChooser (FileDialogConfiguration fileDialogConfiguration)
name|FileChooser
name|getConfiguredFileChooser
parameter_list|(
name|FileDialogConfiguration
name|fileDialogConfiguration
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

