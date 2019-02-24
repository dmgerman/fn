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
name|Comparator
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
name|Objects
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
name|java
operator|.
name|util
operator|.
name|function
operator|.
name|Predicate
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|stream
operator|.
name|Collectors
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|beans
operator|.
name|binding
operator|.
name|Bindings
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|beans
operator|.
name|property
operator|.
name|ListProperty
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|beans
operator|.
name|property
operator|.
name|ObjectProperty
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|beans
operator|.
name|property
operator|.
name|SimpleListProperty
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|beans
operator|.
name|property
operator|.
name|SimpleObjectProperty
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|beans
operator|.
name|property
operator|.
name|SimpleStringProperty
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|beans
operator|.
name|property
operator|.
name|StringProperty
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|collections
operator|.
name|FXCollections
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|collections
operator|.
name|ObservableList
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
name|AbstractViewModel
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
name|DialogService
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
name|StateManager
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
name|util
operator|.
name|CustomLocalDragboard
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
name|util
operator|.
name|TaskExecutor
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
name|database
operator|.
name|BibDatabaseContext
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
name|entry
operator|.
name|BibEntry
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
name|AbstractGroup
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
name|ExplicitGroup
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
name|metadata
operator|.
name|MetaData
import|;
end_import

begin_import
import|import
name|org
operator|.
name|fxmisc
operator|.
name|easybind
operator|.
name|EasyBind
import|;
end_import

begin_class
DECL|class|GroupTreeViewModel
specifier|public
class|class
name|GroupTreeViewModel
extends|extends
name|AbstractViewModel
block|{
DECL|field|rootGroup
specifier|private
specifier|final
name|ObjectProperty
argument_list|<
name|GroupNodeViewModel
argument_list|>
name|rootGroup
init|=
operator|new
name|SimpleObjectProperty
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|selectedGroups
specifier|private
specifier|final
name|ListProperty
argument_list|<
name|GroupNodeViewModel
argument_list|>
name|selectedGroups
init|=
operator|new
name|SimpleListProperty
argument_list|<>
argument_list|(
name|FXCollections
operator|.
name|observableArrayList
argument_list|()
argument_list|)
decl_stmt|;
DECL|field|stateManager
specifier|private
specifier|final
name|StateManager
name|stateManager
decl_stmt|;
DECL|field|dialogService
specifier|private
specifier|final
name|DialogService
name|dialogService
decl_stmt|;
DECL|field|taskExecutor
specifier|private
specifier|final
name|TaskExecutor
name|taskExecutor
decl_stmt|;
DECL|field|localDragboard
specifier|private
specifier|final
name|CustomLocalDragboard
name|localDragboard
decl_stmt|;
DECL|field|filterPredicate
specifier|private
specifier|final
name|ObjectProperty
argument_list|<
name|Predicate
argument_list|<
name|GroupNodeViewModel
argument_list|>
argument_list|>
name|filterPredicate
init|=
operator|new
name|SimpleObjectProperty
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|filterText
specifier|private
specifier|final
name|StringProperty
name|filterText
init|=
operator|new
name|SimpleStringProperty
argument_list|()
decl_stmt|;
DECL|field|compAlphabetIgnoreCase
specifier|private
specifier|final
name|Comparator
argument_list|<
name|GroupTreeNode
argument_list|>
name|compAlphabetIgnoreCase
init|=
parameter_list|(
name|GroupTreeNode
name|v1
parameter_list|,
name|GroupTreeNode
name|v2
parameter_list|)
lambda|->
name|v1
operator|.
name|getName
argument_list|()
operator|.
name|compareToIgnoreCase
argument_list|(
name|v2
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
DECL|field|currentDatabase
specifier|private
name|Optional
argument_list|<
name|BibDatabaseContext
argument_list|>
name|currentDatabase
decl_stmt|;
DECL|method|GroupTreeViewModel (StateManager stateManager, DialogService dialogService, TaskExecutor taskExecutor, CustomLocalDragboard localDragboard)
specifier|public
name|GroupTreeViewModel
parameter_list|(
name|StateManager
name|stateManager
parameter_list|,
name|DialogService
name|dialogService
parameter_list|,
name|TaskExecutor
name|taskExecutor
parameter_list|,
name|CustomLocalDragboard
name|localDragboard
parameter_list|)
block|{
name|this
operator|.
name|stateManager
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|stateManager
argument_list|)
expr_stmt|;
name|this
operator|.
name|dialogService
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|dialogService
argument_list|)
expr_stmt|;
name|this
operator|.
name|taskExecutor
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|taskExecutor
argument_list|)
expr_stmt|;
name|this
operator|.
name|localDragboard
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|localDragboard
argument_list|)
expr_stmt|;
comment|// Register listener
name|EasyBind
operator|.
name|subscribe
argument_list|(
name|stateManager
operator|.
name|activeDatabaseProperty
argument_list|()
argument_list|,
name|this
operator|::
name|onActiveDatabaseChanged
argument_list|)
expr_stmt|;
name|EasyBind
operator|.
name|subscribe
argument_list|(
name|selectedGroups
argument_list|,
name|this
operator|::
name|onSelectedGroupChanged
argument_list|)
expr_stmt|;
comment|// Set-up bindings
name|filterPredicate
operator|.
name|bind
argument_list|(
name|Bindings
operator|.
name|createObjectBinding
argument_list|(
parameter_list|()
lambda|->
name|group
lambda|->
name|group
operator|.
name|isMatchedBy
argument_list|(
name|filterText
operator|.
name|get
argument_list|()
argument_list|)
argument_list|,
name|filterText
argument_list|)
argument_list|)
expr_stmt|;
comment|// Init
name|refresh
argument_list|()
expr_stmt|;
block|}
DECL|method|refresh ()
specifier|private
name|void
name|refresh
parameter_list|()
block|{
name|onActiveDatabaseChanged
argument_list|(
name|stateManager
operator|.
name|activeDatabaseProperty
argument_list|()
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|rootGroupProperty ()
specifier|public
name|ObjectProperty
argument_list|<
name|GroupNodeViewModel
argument_list|>
name|rootGroupProperty
parameter_list|()
block|{
return|return
name|rootGroup
return|;
block|}
DECL|method|selectedGroupsProperty ()
specifier|public
name|ListProperty
argument_list|<
name|GroupNodeViewModel
argument_list|>
name|selectedGroupsProperty
parameter_list|()
block|{
return|return
name|selectedGroups
return|;
block|}
DECL|method|filterPredicateProperty ()
specifier|public
name|ObjectProperty
argument_list|<
name|Predicate
argument_list|<
name|GroupNodeViewModel
argument_list|>
argument_list|>
name|filterPredicateProperty
parameter_list|()
block|{
return|return
name|filterPredicate
return|;
block|}
DECL|method|filterTextProperty ()
specifier|public
name|StringProperty
name|filterTextProperty
parameter_list|()
block|{
return|return
name|filterText
return|;
block|}
comment|/**      * Gets invoked if the user selects a different group.      * We need to notify the {@link StateManager} about this change so that the main table gets updated.      */
DECL|method|onSelectedGroupChanged (ObservableList<GroupNodeViewModel> newValue)
specifier|private
name|void
name|onSelectedGroupChanged
parameter_list|(
name|ObservableList
argument_list|<
name|GroupNodeViewModel
argument_list|>
name|newValue
parameter_list|)
block|{
if|if
condition|(
operator|!
name|currentDatabase
operator|.
name|equals
argument_list|(
name|stateManager
operator|.
name|activeDatabaseProperty
argument_list|()
operator|.
name|getValue
argument_list|()
argument_list|)
condition|)
block|{
comment|// Switch of database occurred -> do nothing
return|return;
block|}
name|currentDatabase
operator|.
name|ifPresent
argument_list|(
name|database
lambda|->
block|{
if|if
condition|(
operator|(
name|newValue
operator|==
literal|null
operator|)
operator|||
name|newValue
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|stateManager
operator|.
name|clearSelectedGroups
argument_list|(
name|database
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|stateManager
operator|.
name|setSelectedGroups
argument_list|(
name|database
argument_list|,
name|newValue
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|GroupNodeViewModel
operator|::
name|getGroupNode
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
comment|/**      * Opens "New Group Dialog" and add the resulting group to the root      */
DECL|method|addNewGroupToRoot ()
specifier|public
name|void
name|addNewGroupToRoot
parameter_list|()
block|{
if|if
condition|(
name|currentDatabase
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|addNewSubgroup
argument_list|(
name|rootGroup
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|dialogService
operator|.
name|showWarningDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Cannot create group"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Cannot create group. Please create a library first."
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Gets invoked if the user changes the active database.      * We need to get the new group tree and update the view      */
DECL|method|onActiveDatabaseChanged (Optional<BibDatabaseContext> newDatabase)
specifier|private
name|void
name|onActiveDatabaseChanged
parameter_list|(
name|Optional
argument_list|<
name|BibDatabaseContext
argument_list|>
name|newDatabase
parameter_list|)
block|{
if|if
condition|(
name|newDatabase
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|GroupNodeViewModel
name|newRoot
init|=
name|newDatabase
operator|.
name|map
argument_list|(
name|BibDatabaseContext
operator|::
name|getMetaData
argument_list|)
operator|.
name|flatMap
argument_list|(
name|MetaData
operator|::
name|getGroups
argument_list|)
operator|.
name|map
argument_list|(
name|root
lambda|->
operator|new
name|GroupNodeViewModel
argument_list|(
name|newDatabase
operator|.
name|get
argument_list|()
argument_list|,
name|stateManager
argument_list|,
name|taskExecutor
argument_list|,
name|root
argument_list|,
name|localDragboard
argument_list|)
argument_list|)
operator|.
name|orElse
argument_list|(
name|GroupNodeViewModel
operator|.
name|getAllEntriesGroup
argument_list|(
name|newDatabase
operator|.
name|get
argument_list|()
argument_list|,
name|stateManager
argument_list|,
name|taskExecutor
argument_list|,
name|localDragboard
argument_list|)
argument_list|)
decl_stmt|;
name|rootGroup
operator|.
name|setValue
argument_list|(
name|newRoot
argument_list|)
expr_stmt|;
name|this
operator|.
name|selectedGroups
operator|.
name|setAll
argument_list|(
name|stateManager
operator|.
name|getSelectedGroup
argument_list|(
name|newDatabase
operator|.
name|get
argument_list|()
argument_list|)
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|selectedGroup
lambda|->
operator|new
name|GroupNodeViewModel
argument_list|(
name|newDatabase
operator|.
name|get
argument_list|()
argument_list|,
name|stateManager
argument_list|,
name|taskExecutor
argument_list|,
name|selectedGroup
argument_list|,
name|localDragboard
argument_list|)
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|rootGroup
operator|.
name|setValue
argument_list|(
name|GroupNodeViewModel
operator|.
name|getAllEntriesGroup
argument_list|(
operator|new
name|BibDatabaseContext
argument_list|()
argument_list|,
name|stateManager
argument_list|,
name|taskExecutor
argument_list|,
name|localDragboard
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|currentDatabase
operator|=
name|newDatabase
expr_stmt|;
block|}
comment|/**      * Opens "New Group Dialog" and add the resulting group to the specified group      */
DECL|method|addNewSubgroup (GroupNodeViewModel parent)
specifier|public
name|void
name|addNewSubgroup
parameter_list|(
name|GroupNodeViewModel
name|parent
parameter_list|)
block|{
name|Optional
argument_list|<
name|AbstractGroup
argument_list|>
name|newGroup
init|=
name|dialogService
operator|.
name|showCustomDialogAndWait
argument_list|(
operator|new
name|GroupDialog
argument_list|()
argument_list|)
decl_stmt|;
name|newGroup
operator|.
name|ifPresent
argument_list|(
name|group
lambda|->
block|{
name|parent
operator|.
name|addSubgroup
argument_list|(
name|group
argument_list|)
expr_stmt|;
comment|// TODO: Add undo
comment|//UndoableAddOrRemoveGroup undo = new UndoableAddOrRemoveGroup(parent, new GroupTreeNodeViewModel(newGroupNode), UndoableAddOrRemoveGroup.ADD_NODE);
comment|//panel.getUndoManager().addEdit(undo);
comment|// TODO: Expand parent to make new group visible
comment|//parent.expand();
name|dialogService
operator|.
name|notify
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Added group \"%0\"."
argument_list|,
name|group
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|writeGroupChangesToMetaData
argument_list|()
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
block|}
DECL|method|writeGroupChangesToMetaData ()
specifier|private
name|void
name|writeGroupChangesToMetaData
parameter_list|()
block|{
name|currentDatabase
operator|.
name|get
argument_list|()
operator|.
name|getMetaData
argument_list|()
operator|.
name|setGroups
argument_list|(
name|rootGroup
operator|.
name|get
argument_list|()
operator|.
name|getGroupNode
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * Opens "Edit Group Dialog" and changes the given group to the edited one.      */
DECL|method|editGroup (GroupNodeViewModel oldGroup)
specifier|public
name|void
name|editGroup
parameter_list|(
name|GroupNodeViewModel
name|oldGroup
parameter_list|)
block|{
name|Optional
argument_list|<
name|AbstractGroup
argument_list|>
name|newGroup
init|=
name|dialogService
operator|.
name|showCustomDialogAndWait
argument_list|(
operator|new
name|GroupDialog
argument_list|(
name|oldGroup
operator|.
name|getGroupNode
argument_list|()
operator|.
name|getGroup
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
name|newGroup
operator|.
name|ifPresent
argument_list|(
name|group
lambda|->
block|{
comment|// TODO: Keep assignments
name|boolean
name|keepPreviousAssignments
init|=
name|dialogService
operator|.
name|showConfirmationDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Change of Grouping Method"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Assign the original group's entries to this group?"
argument_list|)
argument_list|)
decl_stmt|;
comment|//        WarnAssignmentSideEffects.warnAssignmentSideEffects(newGroup, panel.frame());
name|boolean
name|removePreviousAssignments
init|=
operator|(
name|oldGroup
operator|.
name|getGroupNode
argument_list|()
operator|.
name|getGroup
argument_list|()
operator|instanceof
name|ExplicitGroup
operator|)
operator|&&
operator|(
name|group
operator|instanceof
name|ExplicitGroup
operator|)
decl_stmt|;
name|oldGroup
operator|.
name|getGroupNode
argument_list|()
operator|.
name|setGroup
argument_list|(
name|group
argument_list|,
name|keepPreviousAssignments
argument_list|,
name|removePreviousAssignments
argument_list|,
name|stateManager
operator|.
name|getEntriesInCurrentDatabase
argument_list|()
argument_list|)
expr_stmt|;
comment|// TODO: Add undo
comment|// Store undo information.
comment|// AbstractUndoableEdit undoAddPreviousEntries = null;
comment|// UndoableModifyGroup undo = new UndoableModifyGroup(GroupSelector.this, groupsRoot, node, newGroup);
comment|// if (undoAddPreviousEntries == null) {
comment|//    panel.getUndoManager().addEdit(undo);
comment|//} else {
comment|//    NamedCompound nc = new NamedCompound("Modify Group");
comment|//    nc.addEdit(undo);
comment|//    nc.addEdit(undoAddPreviousEntries);
comment|//    nc.end();/
comment|//      panel.getUndoManager().addEdit(nc);
comment|//}
comment|//if (!addChange.isEmpty()) {
comment|//    undoAddPreviousEntries = UndoableChangeEntriesOfGroup.getUndoableEdit(null, addChange);
comment|//}
name|dialogService
operator|.
name|notify
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Modified group \"%0\"."
argument_list|,
name|group
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|writeGroupChangesToMetaData
argument_list|()
expr_stmt|;
comment|// This is ugly but we have no proper update mechanism in place to propagate the changes, so redraw everything
name|refresh
argument_list|()
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
block|}
DECL|method|removeSubgroups (GroupNodeViewModel group)
specifier|public
name|void
name|removeSubgroups
parameter_list|(
name|GroupNodeViewModel
name|group
parameter_list|)
block|{
name|boolean
name|confirmation
init|=
name|dialogService
operator|.
name|showConfirmationDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Remove subgroups"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Remove all subgroups of \"%0\"?"
argument_list|,
name|group
operator|.
name|getDisplayName
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|confirmation
condition|)
block|{
comment|/// TODO: Add undo
comment|//final UndoableModifySubtree undo = new UndoableModifySubtree(getGroupTreeRoot(), node, "Remove subgroups");
comment|//panel.getUndoManager().addEdit(undo);
name|group
operator|.
name|getGroupNode
argument_list|()
operator|.
name|removeAllChildren
argument_list|()
expr_stmt|;
name|dialogService
operator|.
name|notify
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Removed all subgroups of group \"%0\"."
argument_list|,
name|group
operator|.
name|getDisplayName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|writeGroupChangesToMetaData
argument_list|()
expr_stmt|;
block|}
block|}
DECL|method|removeGroupKeepSubgroups (GroupNodeViewModel group)
specifier|public
name|void
name|removeGroupKeepSubgroups
parameter_list|(
name|GroupNodeViewModel
name|group
parameter_list|)
block|{
name|boolean
name|confirmation
init|=
name|dialogService
operator|.
name|showConfirmationDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Remove group"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Remove group \"%0\"?"
argument_list|,
name|group
operator|.
name|getDisplayName
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|confirmation
condition|)
block|{
comment|// TODO: Add undo
comment|//final UndoableAddOrRemoveGroup undo = new UndoableAddOrRemoveGroup(groupsRoot, node, UndoableAddOrRemoveGroup.REMOVE_NODE_KEEP_CHILDREN);
comment|//panel.getUndoManager().addEdit(undo);
name|GroupTreeNode
name|groupNode
init|=
name|group
operator|.
name|getGroupNode
argument_list|()
decl_stmt|;
name|groupNode
operator|.
name|getParent
argument_list|()
operator|.
name|ifPresent
argument_list|(
name|parent
lambda|->
name|groupNode
operator|.
name|moveAllChildrenTo
argument_list|(
name|parent
argument_list|,
name|parent
operator|.
name|getIndexOfChild
argument_list|(
name|groupNode
argument_list|)
operator|.
name|get
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|groupNode
operator|.
name|removeFromParent
argument_list|()
expr_stmt|;
name|dialogService
operator|.
name|notify
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Removed group \"%0\"."
argument_list|,
name|group
operator|.
name|getDisplayName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|writeGroupChangesToMetaData
argument_list|()
expr_stmt|;
block|}
block|}
comment|/**      * Removes the specified group and its subgroups (after asking for confirmation).      */
DECL|method|removeGroupAndSubgroups (GroupNodeViewModel group)
specifier|public
name|void
name|removeGroupAndSubgroups
parameter_list|(
name|GroupNodeViewModel
name|group
parameter_list|)
block|{
name|boolean
name|confirmed
init|=
name|dialogService
operator|.
name|showConfirmationDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Remove group and subgroups"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Remove group \"%0\" and its subgroups?"
argument_list|,
name|group
operator|.
name|getDisplayName
argument_list|()
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Remove"
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|confirmed
condition|)
block|{
comment|// TODO: Add undo
comment|//final UndoableAddOrRemoveGroup undo = new UndoableAddOrRemoveGroup(groupsRoot, node, UndoableAddOrRemoveGroup.REMOVE_NODE_AND_CHILDREN);
comment|//panel.getUndoManager().addEdit(undo);
name|removeGroupsAndSubGroupsFromEntries
argument_list|(
name|group
argument_list|)
expr_stmt|;
name|group
operator|.
name|getGroupNode
argument_list|()
operator|.
name|removeFromParent
argument_list|()
expr_stmt|;
name|dialogService
operator|.
name|notify
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Removed group \"%0\" and its subgroups."
argument_list|,
name|group
operator|.
name|getDisplayName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|writeGroupChangesToMetaData
argument_list|()
expr_stmt|;
block|}
block|}
DECL|method|removeGroupsAndSubGroupsFromEntries (GroupNodeViewModel group)
name|void
name|removeGroupsAndSubGroupsFromEntries
parameter_list|(
name|GroupNodeViewModel
name|group
parameter_list|)
block|{
for|for
control|(
name|GroupNodeViewModel
name|child
range|:
name|group
operator|.
name|getChildren
argument_list|()
control|)
block|{
name|removeGroupsAndSubGroupsFromEntries
argument_list|(
name|child
argument_list|)
expr_stmt|;
block|}
comment|// only remove explicit groups from the entries, keyword groups should not be deleted
if|if
condition|(
name|group
operator|.
name|getGroupNode
argument_list|()
operator|.
name|getGroup
argument_list|()
operator|instanceof
name|ExplicitGroup
condition|)
block|{
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entriesInGroup
init|=
name|group
operator|.
name|getGroupNode
argument_list|()
operator|.
name|getEntriesInGroup
argument_list|(
name|this
operator|.
name|currentDatabase
operator|.
name|get
argument_list|()
operator|.
name|getEntries
argument_list|()
argument_list|)
decl_stmt|;
name|group
operator|.
name|getGroupNode
argument_list|()
operator|.
name|removeEntriesFromGroup
argument_list|(
name|entriesInGroup
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|addSelectedEntries (GroupNodeViewModel group)
specifier|public
name|void
name|addSelectedEntries
parameter_list|(
name|GroupNodeViewModel
name|group
parameter_list|)
block|{
comment|// TODO: Warn
comment|// if (!WarnAssignmentSideEffects.warnAssignmentSideEffects(node.getNode().getGroup(), panel.frame())) {
comment|//    return; // user aborted operation
name|group
operator|.
name|getGroupNode
argument_list|()
operator|.
name|addEntriesToGroup
argument_list|(
name|stateManager
operator|.
name|getSelectedEntries
argument_list|()
argument_list|)
expr_stmt|;
comment|// TODO: Add undo
comment|// NamedCompound undoAll = new NamedCompound(Localization.lang("change assignment of entries"));
comment|// if (!undoAdd.isEmpty()) { undo.addEdit(UndoableChangeEntriesOfGroup.getUndoableEdit(node, undoAdd)); }
comment|// panel.getUndoManager().addEdit(undoAll);
comment|// TODO Display massages
comment|//if (undo == null) {
comment|//    frame.output(Localization.lang("The group \"%0\" already contains the selection.",
comment|//            node.getGroup().getName()));
comment|//    return;
comment|//}
comment|// panel.getUndoManager().addEdit(undo);
comment|// final String groupName = node.getGroup().getName();
comment|// if (assignedEntries == 1) {
comment|//    frame.output(Localization.lang("Assigned 1 entry to group \"%0\".", groupName));
comment|// } else {
comment|//    frame.output(Localization.lang("Assigned %0 entries to group \"%1\".", String.valueOf(assignedEntries),
comment|//            groupName));
comment|//}
block|}
DECL|method|removeSelectedEntries (GroupNodeViewModel group)
specifier|public
name|void
name|removeSelectedEntries
parameter_list|(
name|GroupNodeViewModel
name|group
parameter_list|)
block|{
comment|// TODO: warn if assignment has undesired side effects (modifies a field != keywords)
comment|// if (!WarnAssignmentSideEffects.warnAssignmentSideEffects(mNode.getNode().getGroup(), mPanel.frame())) {
comment|//    return; // user aborted operation
name|group
operator|.
name|getGroupNode
argument_list|()
operator|.
name|removeEntriesFromGroup
argument_list|(
name|stateManager
operator|.
name|getSelectedEntries
argument_list|()
argument_list|)
expr_stmt|;
comment|// TODO: Add undo
comment|// if (!undo.isEmpty()) {
comment|//    mPanel.getUndoManager().addEdit(UndoableChangeEntriesOfGroup.getUndoableEdit(mNode, undo));
block|}
DECL|method|sortAlphabeticallyRecursive (GroupNodeViewModel group)
specifier|public
name|void
name|sortAlphabeticallyRecursive
parameter_list|(
name|GroupNodeViewModel
name|group
parameter_list|)
block|{
name|group
operator|.
name|getGroupNode
argument_list|()
operator|.
name|sortChildren
argument_list|(
name|compAlphabetIgnoreCase
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

