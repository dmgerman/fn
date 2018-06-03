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
name|java
operator|.
name|util
operator|.
name|function
operator|.
name|Consumer
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JComponent
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|embed
operator|.
name|swing
operator|.
name|SwingNode
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|event
operator|.
name|ActionEvent
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|event
operator|.
name|Event
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
name|Button
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

begin_class
DECL|class|ControlHelper
specifier|public
class|class
name|ControlHelper
block|{
DECL|method|setAction (ButtonType buttonType, DialogPane dialogPane, Consumer<Event> consumer)
specifier|public
specifier|static
name|void
name|setAction
parameter_list|(
name|ButtonType
name|buttonType
parameter_list|,
name|DialogPane
name|dialogPane
parameter_list|,
name|Consumer
argument_list|<
name|Event
argument_list|>
name|consumer
parameter_list|)
block|{
name|Button
name|button
init|=
operator|(
name|Button
operator|)
name|dialogPane
operator|.
name|lookupButton
argument_list|(
name|buttonType
argument_list|)
decl_stmt|;
name|button
operator|.
name|addEventFilter
argument_list|(
name|ActionEvent
operator|.
name|ACTION
argument_list|,
operator|(
name|event
lambda|->
block|{
name|consumer
operator|.
name|accept
argument_list|(
name|event
argument_list|)
expr_stmt|;
name|event
operator|.
name|consume
argument_list|()
expr_stmt|;
block|}
operator|)
argument_list|)
expr_stmt|;
block|}
DECL|method|setSwingContent (DialogPane dialogPane, JComponent content)
specifier|public
specifier|static
name|void
name|setSwingContent
parameter_list|(
name|DialogPane
name|dialogPane
parameter_list|,
name|JComponent
name|content
parameter_list|)
block|{
name|SwingNode
name|node
init|=
operator|new
name|SwingNode
argument_list|()
decl_stmt|;
name|node
operator|.
name|setContent
argument_list|(
name|content
argument_list|)
expr_stmt|;
name|node
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|dialogPane
operator|.
name|setContent
argument_list|(
name|node
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

