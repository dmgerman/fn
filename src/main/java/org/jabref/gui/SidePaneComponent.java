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
name|awt
operator|.
name|BorderLayout
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Color
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Dimension
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Insets
import|;
end_import

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
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|InputEvent
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
name|BorderFactory
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
name|javax
operator|.
name|swing
operator|.
name|JButton
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JLabel
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JPanel
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JToolBar
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|KeyStroke
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
DECL|class|SidePaneComponent
specifier|public
specifier|abstract
class|class
name|SidePaneComponent
extends|extends
name|JPanel
block|{
DECL|field|close
specifier|protected
specifier|final
name|JButton
name|close
init|=
operator|new
name|JButton
argument_list|(
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|CLOSE
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
decl_stmt|;
DECL|field|manager
specifier|protected
specifier|final
name|SidePaneManager
name|manager
decl_stmt|;
DECL|field|panel
specifier|protected
name|BasePanel
name|panel
decl_stmt|;
DECL|method|SidePaneComponent (SidePaneManager manager, Icon icon, String title)
specifier|public
name|SidePaneComponent
parameter_list|(
name|SidePaneManager
name|manager
parameter_list|,
name|Icon
name|icon
parameter_list|,
name|String
name|title
parameter_list|)
block|{
name|super
argument_list|(
operator|new
name|BorderLayout
argument_list|()
argument_list|)
expr_stmt|;
name|this
operator|.
name|manager
operator|=
name|manager
expr_stmt|;
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createEmptyBorder
argument_list|()
argument_list|)
expr_stmt|;
name|close
operator|.
name|setMargin
argument_list|(
operator|new
name|Insets
argument_list|(
literal|0
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|close
operator|.
name|setBorder
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|close
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
name|hideAway
argument_list|()
argument_list|)
expr_stmt|;
name|JButton
name|up
init|=
operator|new
name|JButton
argument_list|(
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|UP
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
decl_stmt|;
name|up
operator|.
name|setMargin
argument_list|(
operator|new
name|Insets
argument_list|(
literal|0
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|up
operator|.
name|setBorder
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|up
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
name|moveUp
argument_list|()
argument_list|)
expr_stmt|;
name|JButton
name|down
init|=
operator|new
name|JButton
argument_list|(
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|DOWN
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
decl_stmt|;
name|down
operator|.
name|setMargin
argument_list|(
operator|new
name|Insets
argument_list|(
literal|0
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|down
operator|.
name|setBorder
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|down
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
name|moveDown
argument_list|()
argument_list|)
expr_stmt|;
name|JPanel
name|titlePanel
init|=
operator|new
name|JPanel
argument_list|(
operator|new
name|BorderLayout
argument_list|()
argument_list|)
decl_stmt|;
name|titlePanel
operator|.
name|add
argument_list|(
operator|new
name|JLabel
argument_list|(
name|icon
argument_list|)
argument_list|,
name|BorderLayout
operator|.
name|WEST
argument_list|)
expr_stmt|;
name|JLabel
name|titleLabel
init|=
operator|new
name|JLabel
argument_list|(
name|title
argument_list|)
decl_stmt|;
name|titleLabel
operator|.
name|setOpaque
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|titleLabel
operator|.
name|setForeground
argument_list|(
operator|new
name|Color
argument_list|(
literal|79
argument_list|,
literal|95
argument_list|,
literal|143
argument_list|)
argument_list|)
expr_stmt|;
name|titlePanel
operator|.
name|add
argument_list|(
name|titleLabel
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|JToolBar
name|toolbar
init|=
operator|new
name|OSXCompatibleToolbar
argument_list|()
decl_stmt|;
name|toolbar
operator|.
name|add
argument_list|(
name|up
argument_list|)
expr_stmt|;
name|toolbar
operator|.
name|add
argument_list|(
name|down
argument_list|)
expr_stmt|;
name|toolbar
operator|.
name|add
argument_list|(
name|close
argument_list|)
expr_stmt|;
name|toolbar
operator|.
name|setOpaque
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|toolbar
operator|.
name|setFloatable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|titlePanel
operator|.
name|add
argument_list|(
name|toolbar
argument_list|,
name|BorderLayout
operator|.
name|EAST
argument_list|)
expr_stmt|;
name|this
operator|.
name|add
argument_list|(
name|titlePanel
argument_list|,
name|BorderLayout
operator|.
name|NORTH
argument_list|)
expr_stmt|;
block|}
DECL|method|setContentContainer (JPanel panel)
specifier|public
name|void
name|setContentContainer
parameter_list|(
name|JPanel
name|panel
parameter_list|)
block|{
name|this
operator|.
name|add
argument_list|(
name|panel
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
block|}
DECL|method|hideAway ()
specifier|private
name|void
name|hideAway
parameter_list|()
block|{
name|manager
operator|.
name|hideComponent
argument_list|(
name|this
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
DECL|method|setActiveBasePanel (BasePanel panel)
specifier|public
name|void
name|setActiveBasePanel
parameter_list|(
name|BasePanel
name|panel
parameter_list|)
block|{
name|this
operator|.
name|panel
operator|=
name|panel
expr_stmt|;
block|}
DECL|method|getActiveBasePanel ()
specifier|public
name|BasePanel
name|getActiveBasePanel
parameter_list|()
block|{
return|return
name|panel
return|;
block|}
comment|/**      * Override this method if the component needs to make any changes before it can close.      */
DECL|method|componentClosing ()
specifier|public
name|void
name|componentClosing
parameter_list|()
block|{
comment|// Nothing right now
block|}
comment|/**      * Override this method if the component needs to do any actions when opening.      */
DECL|method|componentOpening ()
specifier|public
name|void
name|componentOpening
parameter_list|()
block|{
comment|// Nothing right now
block|}
annotation|@
name|Override
DECL|method|getMinimumSize ()
specifier|public
name|Dimension
name|getMinimumSize
parameter_list|()
block|{
return|return
name|getPreferredSize
argument_list|()
return|;
block|}
comment|/**      * Specifies how to distribute extra vertical space between side pane components.      * 0: fixed height, 1: fill the remaining space      */
DECL|method|getRescalingWeight ()
specifier|public
specifier|abstract
name|int
name|getRescalingWeight
parameter_list|()
function_decl|;
comment|/**      * @return the action which toggles this {@link SidePaneComponent}      */
DECL|method|getToggleAction ()
specifier|public
specifier|abstract
name|ToggleAction
name|getToggleAction
parameter_list|()
function_decl|;
DECL|class|ToggleAction
specifier|public
class|class
name|ToggleAction
extends|extends
name|MnemonicAwareAction
block|{
DECL|method|ToggleAction (String text, String description, KeyStroke key, IconTheme.JabRefIcon icon)
specifier|public
name|ToggleAction
parameter_list|(
name|String
name|text
parameter_list|,
name|String
name|description
parameter_list|,
name|KeyStroke
name|key
parameter_list|,
name|IconTheme
operator|.
name|JabRefIcon
name|icon
parameter_list|)
block|{
name|super
argument_list|(
name|icon
operator|.
name|getIcon
argument_list|()
argument_list|)
expr_stmt|;
name|putValue
argument_list|(
name|Action
operator|.
name|NAME
argument_list|,
name|text
argument_list|)
expr_stmt|;
name|putValue
argument_list|(
name|Action
operator|.
name|ACCELERATOR_KEY
argument_list|,
name|key
argument_list|)
expr_stmt|;
name|putValue
argument_list|(
name|Action
operator|.
name|SHORT_DESCRIPTION
argument_list|,
name|description
argument_list|)
expr_stmt|;
block|}
DECL|method|ToggleAction (String text, String description, KeyStroke key, Icon icon)
specifier|public
name|ToggleAction
parameter_list|(
name|String
name|text
parameter_list|,
name|String
name|description
parameter_list|,
name|KeyStroke
name|key
parameter_list|,
name|Icon
name|icon
parameter_list|)
block|{
name|super
argument_list|(
name|icon
argument_list|)
expr_stmt|;
name|putValue
argument_list|(
name|Action
operator|.
name|NAME
argument_list|,
name|text
argument_list|)
expr_stmt|;
name|putValue
argument_list|(
name|Action
operator|.
name|ACCELERATOR_KEY
argument_list|,
name|key
argument_list|)
expr_stmt|;
name|putValue
argument_list|(
name|Action
operator|.
name|SHORT_DESCRIPTION
argument_list|,
name|description
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
if|if
condition|(
operator|!
name|manager
operator|.
name|hasComponent
argument_list|(
name|SidePaneComponent
operator|.
name|this
operator|.
name|getClass
argument_list|()
argument_list|)
condition|)
block|{
name|manager
operator|.
name|register
argument_list|(
name|SidePaneComponent
operator|.
name|this
argument_list|)
expr_stmt|;
block|}
comment|// if clicked by mouse just toggle
if|if
condition|(
operator|(
name|e
operator|.
name|getModifiers
argument_list|()
operator|&
name|InputEvent
operator|.
name|BUTTON1_MASK
operator|)
operator|!=
literal|0
condition|)
block|{
name|manager
operator|.
name|toggle
argument_list|(
name|SidePaneComponent
operator|.
name|this
operator|.
name|getClass
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|manager
operator|.
name|toggleThreeWay
argument_list|(
name|SidePaneComponent
operator|.
name|this
operator|.
name|getClass
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|putValue
argument_list|(
name|Action
operator|.
name|SELECTED_KEY
argument_list|,
name|manager
operator|.
name|isComponentVisible
argument_list|(
name|SidePaneComponent
operator|.
name|this
operator|.
name|getClass
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|setSelected (boolean selected)
specifier|public
name|void
name|setSelected
parameter_list|(
name|boolean
name|selected
parameter_list|)
block|{
name|putValue
argument_list|(
name|Action
operator|.
name|SELECTED_KEY
argument_list|,
name|selected
argument_list|)
expr_stmt|;
block|}
DECL|method|isSelected ()
specifier|public
name|boolean
name|isSelected
parameter_list|()
block|{
return|return
name|Boolean
operator|.
name|TRUE
operator|.
name|equals
argument_list|(
name|getValue
argument_list|(
name|Action
operator|.
name|SELECTED_KEY
argument_list|)
argument_list|)
return|;
block|}
block|}
block|}
end_class

end_unit

