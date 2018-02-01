begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.keyboard
package|package
name|org
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
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|ActionEvent
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|AbstractAction
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|Action
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|application
operator|.
name|Platform
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
name|IconTheme
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

begin_class
DECL|class|KeyBindingAction
specifier|public
class|class
name|KeyBindingAction
extends|extends
name|AbstractAction
block|{
DECL|method|KeyBindingAction ()
specifier|public
name|KeyBindingAction
parameter_list|()
block|{
name|super
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Customize key bindings"
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|putValue
argument_list|(
name|Action
operator|.
name|SMALL_ICON
argument_list|,
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|KEY_BINDINGS
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|actionPerformed (ActionEvent e)
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|Platform
operator|.
name|runLater
argument_list|(
parameter_list|()
lambda|->
block|{
name|KeyBindingsDialogView
name|view
init|=
operator|new
name|KeyBindingsDialogView
argument_list|()
decl_stmt|;
name|view
operator|.
name|show
argument_list|()
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

