begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.push
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|push
package|;
end_package

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
name|JabRefFrame
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
name|icon
operator|.
name|JabRefIcon
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
name|logic
operator|.
name|l10n
operator|.
name|Localization
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
name|util
operator|.
name|OS
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|preferences
operator|.
name|JabRefPreferences
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|*
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|*
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
name|ActionListener
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
name|MouseAdapter
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
name|MouseEvent
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
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
name|Map
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

begin_comment
comment|/**  * Customized UI component for pushing to external applications. Has a selection popup menu to change the selected  * external application. This class implements the ActionListener interface. When actionPerformed() is invoked, the  * currently selected PushToApplication is activated. The actionPerformed() method can be called with a null argument.  */
end_comment

begin_class
DECL|class|PushToApplicationButton
specifier|public
class|class
name|PushToApplicationButton
extends|extends
name|SimpleCommand
implements|implements
name|ActionListener
block|{
DECL|field|ARROW_ICON
specifier|private
specifier|static
specifier|final
name|Icon
name|ARROW_ICON
init|=
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|DOWN
operator|.
name|getSmallIcon
argument_list|()
decl_stmt|;
DECL|field|frame
specifier|private
specifier|final
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|pushActions
specifier|private
specifier|final
name|List
argument_list|<
name|PushToApplication
argument_list|>
name|pushActions
decl_stmt|;
DECL|field|comp
specifier|private
name|JPanel
name|comp
decl_stmt|;
DECL|field|pushButton
specifier|private
name|JButton
name|pushButton
decl_stmt|;
DECL|field|toApp
specifier|private
name|PushToApplication
name|toApp
decl_stmt|;
DECL|field|popup
specifier|private
name|JPopupMenu
name|popup
decl_stmt|;
DECL|field|actions
specifier|private
specifier|final
name|Map
argument_list|<
name|PushToApplication
argument_list|,
name|PushToApplicationAction
argument_list|>
name|actions
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|buttonDim
specifier|private
specifier|final
name|Dimension
name|buttonDim
init|=
operator|new
name|Dimension
argument_list|(
literal|23
argument_list|,
literal|23
argument_list|)
decl_stmt|;
DECL|field|optPopup
specifier|private
specifier|final
name|JPopupMenu
name|optPopup
init|=
operator|new
name|JPopupMenu
argument_list|()
decl_stmt|;
DECL|field|settings
specifier|private
specifier|final
name|JMenuItem
name|settings
init|=
operator|new
name|JMenuItem
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Settings"
argument_list|)
argument_list|)
decl_stmt|;
DECL|method|PushToApplicationButton (JabRefFrame frame, List<PushToApplication> pushActions)
specifier|public
name|PushToApplicationButton
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|List
argument_list|<
name|PushToApplication
argument_list|>
name|pushActions
parameter_list|)
block|{
name|this
operator|.
name|frame
operator|=
name|frame
expr_stmt|;
name|this
operator|.
name|pushActions
operator|=
name|pushActions
expr_stmt|;
name|init
argument_list|()
expr_stmt|;
block|}
DECL|method|init ()
specifier|private
name|void
name|init
parameter_list|()
block|{
name|comp
operator|=
operator|new
name|JPanel
argument_list|()
expr_stmt|;
name|comp
operator|.
name|setLayout
argument_list|(
operator|new
name|BorderLayout
argument_list|()
argument_list|)
expr_stmt|;
name|JButton
name|menuButton
init|=
operator|new
name|JButton
argument_list|(
name|PushToApplicationButton
operator|.
name|ARROW_ICON
argument_list|)
decl_stmt|;
name|menuButton
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
name|menuButton
operator|.
name|setPreferredSize
argument_list|(
operator|new
name|Dimension
argument_list|(
name|menuButton
operator|.
name|getIcon
argument_list|()
operator|.
name|getIconWidth
argument_list|()
argument_list|,
name|menuButton
operator|.
name|getIcon
argument_list|()
operator|.
name|getIconHeight
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|menuButton
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
block|{
if|if
condition|(
name|popup
operator|==
literal|null
condition|)
block|{
name|buildPopupMenu
argument_list|()
expr_stmt|;
block|}
name|popup
operator|.
name|show
argument_list|(
name|comp
argument_list|,
literal|0
argument_list|,
name|menuButton
operator|.
name|getHeight
argument_list|()
argument_list|)
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
name|menuButton
operator|.
name|setToolTipText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Select external application"
argument_list|)
argument_list|)
expr_stmt|;
name|pushButton
operator|=
operator|new
name|JButton
argument_list|()
expr_stmt|;
if|if
condition|(
name|OS
operator|.
name|OS_X
condition|)
block|{
name|menuButton
operator|.
name|putClientProperty
argument_list|(
literal|"JButton.buttonType"
argument_list|,
literal|"toolbar"
argument_list|)
expr_stmt|;
name|pushButton
operator|.
name|putClientProperty
argument_list|(
literal|"JButton.buttonType"
argument_list|,
literal|"toolbar"
argument_list|)
expr_stmt|;
block|}
comment|// Set the last used external application
name|toApp
operator|=
name|getLastUsedApplication
argument_list|()
expr_stmt|;
name|setSelected
argument_list|()
expr_stmt|;
name|pushButton
operator|.
name|addActionListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|pushButton
operator|.
name|addMouseListener
argument_list|(
operator|new
name|PushButtonMouseListener
argument_list|()
argument_list|)
expr_stmt|;
name|pushButton
operator|.
name|setOpaque
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|menuButton
operator|.
name|setOpaque
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|comp
operator|.
name|setOpaque
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|comp
operator|.
name|add
argument_list|(
name|pushButton
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|comp
operator|.
name|add
argument_list|(
name|menuButton
argument_list|,
name|BorderLayout
operator|.
name|EAST
argument_list|)
expr_stmt|;
name|comp
operator|.
name|setMaximumSize
argument_list|(
name|comp
operator|.
name|getPreferredSize
argument_list|()
argument_list|)
expr_stmt|;
name|optPopup
operator|.
name|add
argument_list|(
name|settings
argument_list|)
expr_stmt|;
name|settings
operator|.
name|addActionListener
argument_list|(
name|event
lambda|->
block|{
name|JPanel
name|options
init|=
name|toApp
operator|.
name|getSettingsPanel
argument_list|()
decl_stmt|;
if|if
condition|(
name|options
operator|!=
literal|null
condition|)
block|{
name|PushToApplicationSettingsDialog
operator|.
name|showSettingsDialog
argument_list|(
literal|null
argument_list|,
name|toApp
argument_list|,
name|options
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|buildPopupMenu
argument_list|()
expr_stmt|;
block|}
DECL|method|getLastUsedApplication ()
specifier|private
name|PushToApplication
name|getLastUsedApplication
parameter_list|()
block|{
name|String
name|appSelected
init|=
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|PUSH_TO_APPLICATION
argument_list|)
decl_stmt|;
for|for
control|(
name|PushToApplication
name|application
range|:
name|pushActions
control|)
block|{
if|if
condition|(
name|application
operator|.
name|getApplicationName
argument_list|()
operator|.
name|equals
argument_list|(
name|appSelected
argument_list|)
condition|)
block|{
return|return
name|application
return|;
block|}
block|}
comment|// Nothing found, pick first
return|return
name|pushActions
operator|.
name|get
argument_list|(
literal|0
argument_list|)
return|;
block|}
comment|/**      * Create a selection menu for the available "Push" options.      */
DECL|method|buildPopupMenu ()
specifier|private
name|void
name|buildPopupMenu
parameter_list|()
block|{
name|popup
operator|=
operator|new
name|JPopupMenu
argument_list|()
expr_stmt|;
for|for
control|(
name|PushToApplication
name|application
range|:
name|pushActions
control|)
block|{
name|JMenuItem
name|item
init|=
operator|new
name|JMenuItem
argument_list|(
name|application
operator|.
name|getApplicationName
argument_list|()
argument_list|,
name|application
operator|.
name|getIcon
argument_list|()
operator|.
name|getIcon
argument_list|()
argument_list|)
decl_stmt|;
name|item
operator|.
name|setToolTipText
argument_list|(
name|application
operator|.
name|getTooltip
argument_list|()
argument_list|)
expr_stmt|;
name|item
operator|.
name|addActionListener
argument_list|(
operator|new
name|PopupItemActionListener
argument_list|(
name|application
argument_list|)
argument_list|)
expr_stmt|;
name|popup
operator|.
name|add
argument_list|(
name|item
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Update the PushButton to default to the given application.      *      * @param newApplication the application to default to      */
DECL|method|setSelected (PushToApplication newApplication)
specifier|private
name|void
name|setSelected
parameter_list|(
name|PushToApplication
name|newApplication
parameter_list|)
block|{
name|toApp
operator|=
name|newApplication
expr_stmt|;
name|setSelected
argument_list|()
expr_stmt|;
block|}
DECL|method|setSelected ()
specifier|private
name|void
name|setSelected
parameter_list|()
block|{
name|pushButton
operator|.
name|setIcon
argument_list|(
name|toApp
operator|.
name|getIcon
argument_list|()
operator|.
name|getIcon
argument_list|()
argument_list|)
expr_stmt|;
name|pushButton
operator|.
name|setToolTipText
argument_list|(
name|toApp
operator|.
name|getTooltip
argument_list|()
argument_list|)
expr_stmt|;
name|pushButton
operator|.
name|setPreferredSize
argument_list|(
name|buttonDim
argument_list|)
expr_stmt|;
comment|// Store the last used application
name|Globals
operator|.
name|prefs
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|PUSH_TO_APPLICATION
argument_list|,
name|toApp
operator|.
name|getApplicationName
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * Get the toolbar component for the push button.      *      * @return The component.      */
DECL|method|getComponent ()
specifier|public
name|Component
name|getComponent
parameter_list|()
block|{
return|return
name|comp
return|;
block|}
DECL|method|getMenuAction ()
specifier|public
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|actions
operator|.
name|Action
name|getMenuAction
parameter_list|()
block|{
name|PushToApplication
name|application
init|=
name|getLastUsedApplication
argument_list|()
decl_stmt|;
return|return
operator|new
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|actions
operator|.
name|Action
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|Optional
argument_list|<
name|JabRefIcon
argument_list|>
name|getIcon
parameter_list|()
block|{
return|return
name|Optional
operator|.
name|of
argument_list|(
name|application
operator|.
name|getIcon
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Optional
argument_list|<
name|KeyBinding
argument_list|>
name|getKeyBinding
parameter_list|()
block|{
return|return
name|Optional
operator|.
name|of
argument_list|(
name|KeyBinding
operator|.
name|PUSH_TO_APPLICATION
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getText
parameter_list|()
block|{
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"Push entries to external application (%0)"
argument_list|,
name|application
operator|.
name|getApplicationName
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getDescription
parameter_list|()
block|{
return|return
literal|""
return|;
block|}
block|}
return|;
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
name|execute
argument_list|()
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
comment|// Lazy initialization of the push action:
name|PushToApplicationAction
name|action
init|=
name|actions
operator|.
name|get
argument_list|(
name|toApp
argument_list|)
decl_stmt|;
if|if
condition|(
name|action
operator|==
literal|null
condition|)
block|{
name|action
operator|=
operator|new
name|PushToApplicationAction
argument_list|(
name|frame
argument_list|,
name|toApp
argument_list|)
expr_stmt|;
name|actions
operator|.
name|put
argument_list|(
name|toApp
argument_list|,
name|action
argument_list|)
expr_stmt|;
block|}
name|action
operator|.
name|actionPerformed
argument_list|(
operator|new
name|ActionEvent
argument_list|(
name|toApp
argument_list|,
literal|0
argument_list|,
literal|"push"
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|class|PopupItemActionListener
class|class
name|PopupItemActionListener
implements|implements
name|ActionListener
block|{
DECL|field|application
specifier|private
specifier|final
name|PushToApplication
name|application
decl_stmt|;
DECL|method|PopupItemActionListener (PushToApplication application)
specifier|public
name|PopupItemActionListener
parameter_list|(
name|PushToApplication
name|application
parameter_list|)
block|{
name|this
operator|.
name|application
operator|=
name|application
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
comment|// Change the selection:
name|setSelected
argument_list|(
name|application
argument_list|)
expr_stmt|;
comment|// Invoke the selected operation (is that expected behaviour?):
comment|//PushToApplicationButton.this.actionPerformed(null);
comment|// It makes sense to transfer focus to the push button after the
comment|// menu closes:
name|pushButton
operator|.
name|requestFocus
argument_list|()
expr_stmt|;
block|}
block|}
DECL|class|PushButtonMouseListener
class|class
name|PushButtonMouseListener
extends|extends
name|MouseAdapter
block|{
annotation|@
name|Override
DECL|method|mousePressed (MouseEvent event)
specifier|public
name|void
name|mousePressed
parameter_list|(
name|MouseEvent
name|event
parameter_list|)
block|{
if|if
condition|(
name|event
operator|.
name|isPopupTrigger
argument_list|()
condition|)
block|{
name|processPopupTrigger
argument_list|(
name|event
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
DECL|method|mouseClicked (MouseEvent event)
specifier|public
name|void
name|mouseClicked
parameter_list|(
name|MouseEvent
name|event
parameter_list|)
block|{
if|if
condition|(
name|event
operator|.
name|isPopupTrigger
argument_list|()
condition|)
block|{
name|processPopupTrigger
argument_list|(
name|event
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
DECL|method|mouseReleased (MouseEvent event)
specifier|public
name|void
name|mouseReleased
parameter_list|(
name|MouseEvent
name|event
parameter_list|)
block|{
if|if
condition|(
name|event
operator|.
name|isPopupTrigger
argument_list|()
condition|)
block|{
name|processPopupTrigger
argument_list|(
name|event
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|processPopupTrigger (MouseEvent e)
specifier|private
name|void
name|processPopupTrigger
parameter_list|(
name|MouseEvent
name|e
parameter_list|)
block|{
comment|// We only want to show the popup if a settings panel exists for the selected
comment|// item:
if|if
condition|(
name|toApp
operator|.
name|getSettingsPanel
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|optPopup
operator|.
name|show
argument_list|(
name|pushButton
argument_list|,
name|e
operator|.
name|getX
argument_list|()
argument_list|,
name|e
operator|.
name|getY
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
end_class

end_unit

