begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui
package|package
name|net
operator|.
name|sf
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

begin_comment
comment|/**  * This interface provides methods to create dialogs and show them to the user.  */
end_comment

begin_interface
DECL|interface|DialogService
specifier|public
interface|interface
name|DialogService
block|{
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
comment|/**      * This will create and display a new confirmation dialog.      * It will include a blue question icon on the left and      * a OK and Cancel Button. To create a confirmation dialog with custom      * buttons see also {@link #showCustomButtonDialogAndWait(Alert.AlertType, String, String, ButtonType...)}      *      * @return Optional with the pressed Button as ButtonType      */
DECL|method|showConfirmationDialogAndWait (String title, String content)
name|Optional
argument_list|<
name|ButtonType
argument_list|>
name|showConfirmationDialogAndWait
parameter_list|(
name|String
name|title
parameter_list|,
name|String
name|content
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
comment|/**      * Notify the user in an non-blocking way (i.e., update status message instead of showing a dialog).      * @param message the message to show.      */
DECL|method|notify (String message)
name|void
name|notify
parameter_list|(
name|String
name|message
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

