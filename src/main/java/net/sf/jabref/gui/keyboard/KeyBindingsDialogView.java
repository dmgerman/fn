begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.keyboard
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|keyboard
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
name|Alert
operator|.
name|AlertType
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
name|Stage
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
name|gui
operator|.
name|FXAlert
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
name|logic
operator|.
name|l10n
operator|.
name|Localization
import|;
end_import

begin_import
import|import
name|com
operator|.
name|airhacks
operator|.
name|afterburner
operator|.
name|views
operator|.
name|FXMLView
import|;
end_import

begin_class
DECL|class|KeyBindingsDialogView
specifier|public
class|class
name|KeyBindingsDialogView
extends|extends
name|FXMLView
block|{
DECL|method|KeyBindingsDialogView ()
specifier|public
name|KeyBindingsDialogView
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
name|bundle
operator|=
name|Localization
operator|.
name|getMessages
argument_list|()
expr_stmt|;
block|}
DECL|method|show (KeyBindingPreferences keyBindingPreferences)
specifier|public
name|void
name|show
parameter_list|(
name|KeyBindingPreferences
name|keyBindingPreferences
parameter_list|)
block|{
name|FXAlert
name|keyBindingsDialog
init|=
operator|new
name|FXAlert
argument_list|(
name|AlertType
operator|.
name|INFORMATION
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Key bindings"
argument_list|)
argument_list|)
decl_stmt|;
name|keyBindingsDialog
operator|.
name|setDialogPane
argument_list|(
operator|(
name|DialogPane
operator|)
name|this
operator|.
name|getView
argument_list|()
argument_list|)
expr_stmt|;
name|KeyBindingsDialogViewModel
name|controller
init|=
operator|(
name|KeyBindingsDialogViewModel
operator|)
name|fxmlLoader
operator|.
name|getController
argument_list|()
decl_stmt|;
name|controller
operator|.
name|setKeyBindingPreferences
argument_list|(
name|keyBindingPreferences
argument_list|)
expr_stmt|;
name|controller
operator|.
name|initializeView
argument_list|()
expr_stmt|;
name|keyBindingsDialog
operator|.
name|setResizable
argument_list|(
literal|true
argument_list|)
expr_stmt|;
operator|(
operator|(
name|Stage
operator|)
name|keyBindingsDialog
operator|.
name|getDialogPane
argument_list|()
operator|.
name|getScene
argument_list|()
operator|.
name|getWindow
argument_list|()
operator|)
operator|.
name|setMinHeight
argument_list|(
literal|475
argument_list|)
expr_stmt|;
operator|(
operator|(
name|Stage
operator|)
name|keyBindingsDialog
operator|.
name|getDialogPane
argument_list|()
operator|.
name|getScene
argument_list|()
operator|.
name|getWindow
argument_list|()
operator|)
operator|.
name|setMinWidth
argument_list|(
literal|375
argument_list|)
expr_stmt|;
name|keyBindingsDialog
operator|.
name|show
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

