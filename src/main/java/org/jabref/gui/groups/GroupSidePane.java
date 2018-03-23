begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.groups
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|groups
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
name|SidePaneComponent
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
name|SidePaneManager
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
name|SidePaneType
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
name|StandardActions
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
name|preferences
operator|.
name|JabRefPreferences
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
name|ViewLoader
import|;
end_import

begin_comment
comment|/**  * The groups side pane.  */
end_comment

begin_class
DECL|class|GroupSidePane
specifier|public
class|class
name|GroupSidePane
extends|extends
name|SidePaneComponent
block|{
DECL|field|preferences
specifier|private
specifier|final
name|JabRefPreferences
name|preferences
decl_stmt|;
DECL|method|GroupSidePane (SidePaneManager manager, JabRefPreferences preferences)
specifier|public
name|GroupSidePane
parameter_list|(
name|SidePaneManager
name|manager
parameter_list|,
name|JabRefPreferences
name|preferences
parameter_list|)
block|{
name|super
argument_list|(
name|manager
argument_list|,
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|TOGGLE_GROUPS
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Groups"
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|preferences
operator|=
name|preferences
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|afterOpening ()
specifier|public
name|void
name|afterOpening
parameter_list|()
block|{
name|preferences
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|GROUP_SIDEPANE_VISIBLE
argument_list|,
name|Boolean
operator|.
name|TRUE
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|getResizePolicy ()
specifier|public
name|Priority
name|getResizePolicy
parameter_list|()
block|{
return|return
name|Priority
operator|.
name|ALWAYS
return|;
block|}
annotation|@
name|Override
DECL|method|beforeClosing ()
specifier|public
name|void
name|beforeClosing
parameter_list|()
block|{
name|preferences
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|GROUP_SIDEPANE_VISIBLE
argument_list|,
name|Boolean
operator|.
name|FALSE
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|getToggleAction ()
specifier|public
name|Action
name|getToggleAction
parameter_list|()
block|{
return|return
name|StandardActions
operator|.
name|TOGGLE_GROUPS
return|;
block|}
annotation|@
name|Override
DECL|method|createContentPane ()
specifier|protected
name|Node
name|createContentPane
parameter_list|()
block|{
return|return
name|ViewLoader
operator|.
name|view
argument_list|(
name|GroupTreeView
operator|.
name|class
argument_list|)
operator|.
name|load
argument_list|()
operator|.
name|getView
argument_list|()
return|;
block|}
annotation|@
name|Override
DECL|method|getType ()
specifier|public
name|SidePaneType
name|getType
parameter_list|()
block|{
return|return
name|SidePaneType
operator|.
name|GROUPS
return|;
block|}
block|}
end_class

end_unit

