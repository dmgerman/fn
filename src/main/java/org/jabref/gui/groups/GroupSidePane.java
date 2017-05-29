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
name|java
operator|.
name|util
operator|.
name|List
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
name|javafx
operator|.
name|embed
operator|.
name|swing
operator|.
name|JFXPanel
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|Scene
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
name|StackPane
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
name|BasePanel
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
name|maintable
operator|.
name|MainTableDataModel
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
name|model
operator|.
name|groups
operator|.
name|GroupTreeNode
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|search
operator|.
name|matchers
operator|.
name|MatcherSet
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|search
operator|.
name|matchers
operator|.
name|MatcherSets
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
comment|/**  * The groups side pane.  * This class is just a Swing wrapper around the JavaFX implementation {@link GroupTreeView}.  */
end_comment

begin_class
DECL|class|GroupSidePane
specifier|public
class|class
name|GroupSidePane
extends|extends
name|SidePaneComponent
block|{
DECL|field|frame
specifier|protected
specifier|final
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|toggleAction
specifier|private
specifier|final
name|ToggleAction
name|toggleAction
decl_stmt|;
comment|/**      * The first element for each group defines which field to use for the quicksearch. The next two define the name and      * regexp for the group.      */
DECL|method|GroupSidePane (JabRefFrame frame, SidePaneManager manager)
specifier|public
name|GroupSidePane
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|SidePaneManager
name|manager
parameter_list|)
block|{
name|super
argument_list|(
name|manager
argument_list|,
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|TOGGLE_GROUPS
operator|.
name|getIcon
argument_list|()
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Groups"
argument_list|)
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|stateManager
operator|.
name|activeGroupProperty
argument_list|()
operator|.
name|addListener
argument_list|(
parameter_list|(
name|observable
parameter_list|,
name|oldValue
parameter_list|,
name|newValue
parameter_list|)
lambda|->
name|updateShownEntriesAccordingToSelectedGroups
argument_list|(
name|newValue
argument_list|)
argument_list|)
expr_stmt|;
name|toggleAction
operator|=
operator|new
name|ToggleAction
argument_list|(
name|Localization
operator|.
name|menuTitle
argument_list|(
literal|"Toggle groups interface"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Toggle groups interface"
argument_list|)
argument_list|,
name|Globals
operator|.
name|getKeyPrefs
argument_list|()
operator|.
name|getKey
argument_list|(
name|KeyBinding
operator|.
name|TOGGLE_GROUPS_INTERFACE
argument_list|)
argument_list|,
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|TOGGLE_GROUPS
argument_list|)
expr_stmt|;
name|this
operator|.
name|frame
operator|=
name|frame
expr_stmt|;
name|this
operator|.
name|setTitle
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Groups"
argument_list|)
argument_list|)
expr_stmt|;
name|JFXPanel
name|groupsPane
init|=
operator|new
name|JFXPanel
argument_list|()
decl_stmt|;
name|add
argument_list|(
name|groupsPane
argument_list|)
expr_stmt|;
comment|// Execute on JavaFX Application Thread
name|Platform
operator|.
name|runLater
argument_list|(
parameter_list|()
lambda|->
block|{
name|StackPane
name|root
init|=
operator|new
name|StackPane
argument_list|()
decl_stmt|;
name|root
operator|.
name|getChildren
argument_list|()
operator|.
name|addAll
argument_list|(
operator|new
name|GroupTreeView
argument_list|()
operator|.
name|getView
argument_list|()
argument_list|)
expr_stmt|;
name|Scene
name|scene
init|=
operator|new
name|Scene
argument_list|(
name|root
argument_list|)
decl_stmt|;
name|groupsPane
operator|.
name|setScene
argument_list|(
name|scene
argument_list|)
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
block|}
DECL|method|updateShownEntriesAccordingToSelectedGroups (List<GroupTreeNode> selectedGroups)
specifier|private
name|void
name|updateShownEntriesAccordingToSelectedGroups
parameter_list|(
name|List
argument_list|<
name|GroupTreeNode
argument_list|>
name|selectedGroups
parameter_list|)
block|{
if|if
condition|(
name|selectedGroups
operator|==
literal|null
operator|||
name|selectedGroups
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
comment|// No selected group, nothing to do
return|return;
block|}
specifier|final
name|MatcherSet
name|searchRules
init|=
name|MatcherSets
operator|.
name|build
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|GROUP_INTERSECT_SELECTIONS
argument_list|)
condition|?
name|MatcherSets
operator|.
name|MatcherType
operator|.
name|AND
else|:
name|MatcherSets
operator|.
name|MatcherType
operator|.
name|OR
argument_list|)
decl_stmt|;
for|for
control|(
name|GroupTreeNode
name|node
range|:
name|selectedGroups
control|)
block|{
name|searchRules
operator|.
name|addRule
argument_list|(
name|node
operator|.
name|getSearchMatcher
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|GroupingWorker
name|worker
init|=
operator|new
name|GroupingWorker
argument_list|(
name|frame
argument_list|,
name|panel
argument_list|)
decl_stmt|;
name|worker
operator|.
name|run
argument_list|(
name|searchRules
argument_list|)
expr_stmt|;
name|worker
operator|.
name|update
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|componentOpening ()
specifier|public
name|void
name|componentOpening
parameter_list|()
block|{
name|Globals
operator|.
name|prefs
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
DECL|method|getRescalingWeight ()
specifier|public
name|int
name|getRescalingWeight
parameter_list|()
block|{
return|return
literal|1
return|;
block|}
annotation|@
name|Override
DECL|method|componentClosing ()
specifier|public
name|void
name|componentClosing
parameter_list|()
block|{
if|if
condition|(
name|panel
operator|!=
literal|null
condition|)
block|{
comment|// panel may be null if no file is open any more
name|panel
operator|.
name|getMainTable
argument_list|()
operator|.
name|getTableModel
argument_list|()
operator|.
name|updateGroupingState
argument_list|(
name|MainTableDataModel
operator|.
name|DisplayOption
operator|.
name|DISABLED
argument_list|)
expr_stmt|;
block|}
name|getToggleAction
argument_list|()
operator|.
name|setSelected
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|prefs
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
DECL|method|setActiveBasePanel (BasePanel panel)
specifier|public
name|void
name|setActiveBasePanel
parameter_list|(
name|BasePanel
name|panel
parameter_list|)
block|{
name|super
operator|.
name|setActiveBasePanel
argument_list|(
name|panel
argument_list|)
expr_stmt|;
if|if
condition|(
name|panel
operator|==
literal|null
condition|)
block|{
comment|// hide groups
name|frame
operator|.
name|getSidePaneManager
argument_list|()
operator|.
name|hide
argument_list|(
name|GroupSidePane
operator|.
name|class
argument_list|)
expr_stmt|;
return|return;
block|}
synchronized|synchronized
init|(
name|getTreeLock
argument_list|()
init|)
block|{
name|validateTree
argument_list|()
expr_stmt|;
block|}
block|}
annotation|@
name|Override
DECL|method|grabFocus ()
specifier|public
name|void
name|grabFocus
parameter_list|()
block|{      }
annotation|@
name|Override
DECL|method|getToggleAction ()
specifier|public
name|ToggleAction
name|getToggleAction
parameter_list|()
block|{
return|return
name|toggleAction
return|;
block|}
block|}
end_class

end_unit
