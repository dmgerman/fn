begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.help
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|help
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
name|Action
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|Icon
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
name|actions
operator|.
name|MnemonicAwareAction
import|;
end_import

begin_class
DECL|class|AboutAction
specifier|public
class|class
name|AboutAction
extends|extends
name|MnemonicAwareAction
block|{
DECL|method|AboutAction (String title, String tooltip, Icon iconFile)
specifier|public
name|AboutAction
parameter_list|(
name|String
name|title
parameter_list|,
name|String
name|tooltip
parameter_list|,
name|Icon
name|iconFile
parameter_list|)
block|{
name|super
argument_list|(
name|iconFile
argument_list|)
expr_stmt|;
name|putValue
argument_list|(
name|Action
operator|.
name|NAME
argument_list|,
name|title
argument_list|)
expr_stmt|;
name|putValue
argument_list|(
name|Action
operator|.
name|SHORT_DESCRIPTION
argument_list|,
name|tooltip
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
operator|new
name|AboutDialogView
argument_list|()
operator|.
name|show
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

