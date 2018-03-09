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
name|javafx
operator|.
name|fxml
operator|.
name|FXMLLoader
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
name|Dialog
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
name|scene
operator|.
name|image
operator|.
name|Image
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|stage
operator|.
name|Modality
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|stage
operator|.
name|Stage
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|Globals
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
name|keyboard
operator|.
name|KeyBinding
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
name|keyboard
operator|.
name|KeyBindingRepository
import|;
end_import

begin_comment
comment|/**  * This class provides a super class for all dialogs implemented in JavaFX.  *  * To create a custom JavaFX dialog one should create an instance of this class and set a dialog  * pane through the inherited {@link Dialog#setDialogPane(DialogPane)} method.  * The dialog can be shown via {@link Dialog#show()} or {@link Dialog#showAndWait()}.  *  * The layout of the pane should be defined in an external fxml file and loaded it via the  * {@link FXMLLoader}.  */
end_comment

begin_class
DECL|class|FXDialog
specifier|public
class|class
name|FXDialog
extends|extends
name|Alert
block|{
DECL|method|FXDialog (AlertType type, String title, Image image, boolean isModal)
specifier|public
name|FXDialog
parameter_list|(
name|AlertType
name|type
parameter_list|,
name|String
name|title
parameter_list|,
name|Image
name|image
parameter_list|,
name|boolean
name|isModal
parameter_list|)
block|{
name|this
argument_list|(
name|type
argument_list|,
name|title
argument_list|,
name|isModal
argument_list|)
expr_stmt|;
name|setDialogIcon
argument_list|(
name|image
argument_list|)
expr_stmt|;
block|}
DECL|method|FXDialog (AlertType type, String title, Image image)
specifier|public
name|FXDialog
parameter_list|(
name|AlertType
name|type
parameter_list|,
name|String
name|title
parameter_list|,
name|Image
name|image
parameter_list|)
block|{
name|this
argument_list|(
name|type
argument_list|,
name|title
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|setDialogIcon
argument_list|(
name|image
argument_list|)
expr_stmt|;
block|}
DECL|method|FXDialog (AlertType type, String title, boolean isModal)
specifier|public
name|FXDialog
parameter_list|(
name|AlertType
name|type
parameter_list|,
name|String
name|title
parameter_list|,
name|boolean
name|isModal
parameter_list|)
block|{
name|this
argument_list|(
name|type
argument_list|,
name|isModal
argument_list|)
expr_stmt|;
name|setTitle
argument_list|(
name|title
argument_list|)
expr_stmt|;
block|}
DECL|method|FXDialog (AlertType type, String title)
specifier|public
name|FXDialog
parameter_list|(
name|AlertType
name|type
parameter_list|,
name|String
name|title
parameter_list|)
block|{
name|this
argument_list|(
name|type
argument_list|)
expr_stmt|;
name|setTitle
argument_list|(
name|title
argument_list|)
expr_stmt|;
block|}
DECL|method|FXDialog (AlertType type, boolean isModal)
specifier|public
name|FXDialog
parameter_list|(
name|AlertType
name|type
parameter_list|,
name|boolean
name|isModal
parameter_list|)
block|{
name|super
argument_list|(
name|type
argument_list|)
expr_stmt|;
name|setDialogIcon
argument_list|(
name|IconTheme
operator|.
name|getJabRefImageFX
argument_list|()
argument_list|)
expr_stmt|;
name|Stage
name|dialogWindow
init|=
name|getDialogWindow
argument_list|()
decl_stmt|;
name|dialogWindow
operator|.
name|setOnCloseRequest
argument_list|(
name|evt
lambda|->
name|this
operator|.
name|close
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|isModal
condition|)
block|{
name|initModality
argument_list|(
name|Modality
operator|.
name|APPLICATION_MODAL
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|initModality
argument_list|(
name|Modality
operator|.
name|NONE
argument_list|)
expr_stmt|;
block|}
name|dialogWindow
operator|.
name|getScene
argument_list|()
operator|.
name|setOnKeyPressed
argument_list|(
name|event
lambda|->
block|{
name|KeyBindingRepository
name|keyBindingRepository
init|=
name|Globals
operator|.
name|getKeyPrefs
argument_list|()
decl_stmt|;
if|if
condition|(
name|keyBindingRepository
operator|.
name|checkKeyCombinationEquality
argument_list|(
name|KeyBinding
operator|.
name|CLOSE_DIALOG
argument_list|,
name|event
argument_list|)
condition|)
block|{
name|dialogWindow
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
DECL|method|FXDialog (AlertType type)
specifier|public
name|FXDialog
parameter_list|(
name|AlertType
name|type
parameter_list|)
block|{
name|this
argument_list|(
name|type
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
DECL|method|setDialogIcon (Image image)
specifier|private
name|void
name|setDialogIcon
parameter_list|(
name|Image
name|image
parameter_list|)
block|{
name|Stage
name|fxDialogWindow
init|=
name|getDialogWindow
argument_list|()
decl_stmt|;
name|fxDialogWindow
operator|.
name|getIcons
argument_list|()
operator|.
name|add
argument_list|(
name|image
argument_list|)
expr_stmt|;
block|}
DECL|method|getDialogWindow ()
specifier|private
name|Stage
name|getDialogWindow
parameter_list|()
block|{
return|return
operator|(
name|Stage
operator|)
name|getDialogPane
argument_list|()
operator|.
name|getScene
argument_list|()
operator|.
name|getWindow
argument_list|()
return|;
block|}
block|}
end_class

end_unit

