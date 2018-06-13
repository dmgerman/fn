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
name|Component
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
name|JMenuItem
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
name|JPopupMenu
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
DECL|field|toApp
specifier|private
name|PushToApplication
name|toApp
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
comment|// Set the last used external application
name|toApp
operator|=
name|getLastUsedApplication
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
block|}
end_class

end_unit

