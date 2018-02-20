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
name|scene
operator|.
name|Node
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
name|Label
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|layout
operator|.
name|BorderPane
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|layout
operator|.
name|HBox
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|layout
operator|.
name|Priority
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
name|Action
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
name|SimpleCommand
import|;
end_import

begin_class
DECL|class|SidePaneComponent
specifier|public
specifier|abstract
class|class
name|SidePaneComponent
block|{
DECL|field|manager
specifier|protected
specifier|final
name|SidePaneManager
name|manager
decl_stmt|;
DECL|field|toggleCommand
specifier|protected
specifier|final
name|ToggleCommand
name|toggleCommand
decl_stmt|;
DECL|field|icon
specifier|private
specifier|final
name|JabRefIcon
name|icon
decl_stmt|;
DECL|field|title
specifier|private
specifier|final
name|String
name|title
decl_stmt|;
DECL|field|contentNode
specifier|private
name|Node
name|contentNode
decl_stmt|;
DECL|method|SidePaneComponent (SidePaneManager manager, JabRefIcon icon, String title)
specifier|public
name|SidePaneComponent
parameter_list|(
name|SidePaneManager
name|manager
parameter_list|,
name|JabRefIcon
name|icon
parameter_list|,
name|String
name|title
parameter_list|)
block|{
name|this
operator|.
name|manager
operator|=
name|manager
expr_stmt|;
name|this
operator|.
name|icon
operator|=
name|icon
expr_stmt|;
name|this
operator|.
name|title
operator|=
name|title
expr_stmt|;
name|this
operator|.
name|toggleCommand
operator|=
operator|new
name|ToggleCommand
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
DECL|method|hide ()
specifier|protected
name|void
name|hide
parameter_list|()
block|{
name|manager
operator|.
name|hide
argument_list|(
name|this
operator|.
name|getType
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|show ()
specifier|protected
name|void
name|show
parameter_list|()
block|{
name|manager
operator|.
name|show
argument_list|(
name|this
operator|.
name|getType
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|moveUp ()
specifier|private
name|void
name|moveUp
parameter_list|()
block|{
name|manager
operator|.
name|moveUp
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
DECL|method|moveDown ()
specifier|private
name|void
name|moveDown
parameter_list|()
block|{
name|manager
operator|.
name|moveDown
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
comment|/**      * Override this method if the component needs to make any changes before it can close.      */
DECL|method|beforeClosing ()
specifier|public
name|void
name|beforeClosing
parameter_list|()
block|{
comment|// Nothing to do by default
block|}
comment|/**      * Override this method if the component needs to do any actions after it is shown.      */
DECL|method|afterOpening ()
specifier|public
name|void
name|afterOpening
parameter_list|()
block|{
comment|// Nothing to do by default
block|}
comment|/**      * Specifies how to this side pane component behaves if there is additional vertical space.      */
DECL|method|getResizePolicy ()
specifier|public
specifier|abstract
name|Priority
name|getResizePolicy
parameter_list|()
function_decl|;
comment|/**      * @return the command which toggles this {@link SidePaneComponent}      */
DECL|method|getToggleCommand ()
specifier|public
name|ToggleCommand
name|getToggleCommand
parameter_list|()
block|{
return|return
name|toggleCommand
return|;
block|}
comment|/**      * @return the action to toggle this {@link SidePaneComponent}      */
DECL|method|getToggleAction ()
specifier|public
specifier|abstract
name|Action
name|getToggleAction
parameter_list|()
function_decl|;
comment|/**      * @return the content of this component      */
DECL|method|getContentPane ()
specifier|public
specifier|final
name|Node
name|getContentPane
parameter_list|()
block|{
if|if
condition|(
name|contentNode
operator|==
literal|null
condition|)
block|{
name|contentNode
operator|=
name|createContentPane
argument_list|()
expr_stmt|;
block|}
return|return
name|contentNode
return|;
block|}
comment|/**      * @return the header pane for this component      */
DECL|method|getHeader ()
specifier|public
specifier|final
name|Node
name|getHeader
parameter_list|()
block|{
name|Button
name|close
init|=
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|CLOSE
operator|.
name|asButton
argument_list|()
decl_stmt|;
name|close
operator|.
name|setOnAction
argument_list|(
name|event
lambda|->
name|hide
argument_list|()
argument_list|)
expr_stmt|;
name|Button
name|up
init|=
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|UP
operator|.
name|asButton
argument_list|()
decl_stmt|;
name|up
operator|.
name|setOnAction
argument_list|(
name|event
lambda|->
name|moveUp
argument_list|()
argument_list|)
expr_stmt|;
name|Button
name|down
init|=
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|DOWN
operator|.
name|asButton
argument_list|()
decl_stmt|;
name|down
operator|.
name|setOnAction
argument_list|(
name|event
lambda|->
name|moveDown
argument_list|()
argument_list|)
expr_stmt|;
name|HBox
name|buttonContainer
init|=
operator|new
name|HBox
argument_list|()
decl_stmt|;
name|buttonContainer
operator|.
name|getChildren
argument_list|()
operator|.
name|addAll
argument_list|(
name|up
argument_list|,
name|down
argument_list|,
name|close
argument_list|)
expr_stmt|;
name|BorderPane
name|graphic
init|=
operator|new
name|BorderPane
argument_list|()
decl_stmt|;
name|graphic
operator|.
name|setCenter
argument_list|(
name|icon
operator|.
name|getGraphicNode
argument_list|()
argument_list|)
expr_stmt|;
name|BorderPane
name|container
init|=
operator|new
name|BorderPane
argument_list|()
decl_stmt|;
name|container
operator|.
name|setLeft
argument_list|(
name|graphic
argument_list|)
expr_stmt|;
name|container
operator|.
name|setCenter
argument_list|(
operator|new
name|Label
argument_list|(
name|title
argument_list|)
argument_list|)
expr_stmt|;
name|container
operator|.
name|setRight
argument_list|(
name|buttonContainer
argument_list|)
expr_stmt|;
name|container
operator|.
name|getStyleClass
argument_list|()
operator|.
name|add
argument_list|(
literal|"sidePaneComponentHeader"
argument_list|)
expr_stmt|;
return|return
name|container
return|;
block|}
comment|/**      * Create the content of this component      *      * @implNote The {@link SidePaneManager} always creates an instance of every side component (e.g., to get the toggle action)      * but we only want to create the content view if the component is shown to save resources.      * This is the reason for the lazy loading.      */
DECL|method|createContentPane ()
specifier|protected
specifier|abstract
name|Node
name|createContentPane
parameter_list|()
function_decl|;
comment|/**      * @return the type of this component      */
DECL|method|getType ()
specifier|public
specifier|abstract
name|SidePaneType
name|getType
parameter_list|()
function_decl|;
DECL|class|ToggleCommand
specifier|public
class|class
name|ToggleCommand
extends|extends
name|SimpleCommand
block|{
DECL|field|component
specifier|private
specifier|final
name|SidePaneComponent
name|component
decl_stmt|;
DECL|method|ToggleCommand (SidePaneComponent component)
specifier|public
name|ToggleCommand
parameter_list|(
name|SidePaneComponent
name|component
parameter_list|)
block|{
name|this
operator|.
name|component
operator|=
name|component
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|execute ()
specifier|public
name|void
name|execute
parameter_list|()
block|{
name|manager
operator|.
name|toggle
argument_list|(
name|component
operator|.
name|getType
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

