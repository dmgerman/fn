begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.util
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|util
package|;
end_package

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
name|icon
operator|.
name|IconTheme
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

begin_class
DECL|class|BaseDialog
specifier|public
class|class
name|BaseDialog
parameter_list|<
name|T
parameter_list|>
extends|extends
name|Dialog
argument_list|<
name|T
argument_list|>
block|{
DECL|method|BaseDialog ()
specifier|protected
name|BaseDialog
parameter_list|()
block|{
name|getDialogPane
argument_list|()
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
name|close
argument_list|()
expr_stmt|;
block|}
block|}
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
name|Globals
operator|.
name|getThemeLoader
argument_list|()
operator|.
name|installBaseCss
argument_list|(
name|getDialogPane
argument_list|()
operator|.
name|getScene
argument_list|()
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
name|dialogWindow
init|=
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
decl_stmt|;
name|dialogWindow
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
block|}
end_class

end_unit

