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
DECL|field|selectedGroup
specifier|private
specifier|final
name|ObjectProperty
argument_list|<
name|GroupNodeViewModel
argument_list|>
name|selectedGroup
init|=
operator|new
name|SimpleObjectProperty
argument_list|<>
argument_list|()
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
DECL|field|currentDatabase
specifier|private
name|Optional
argument_list|<
name|BibDatabaseContext
argument_list|>
name|currentDatabase
decl_stmt|;
DECL|method|GroupTreeViewModel (StateManager stateManager, DialogService dialogService)
specifier|public
name|GroupTreeViewModel
parameter_list|(
name|StateManager
name|stateManager
parameter_list|,
name|DialogService
name|dialogService
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
comment|// Register listener
name|stateManager
operator|.
name|activeDatabaseProperty
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
name|onActiveDatabaseChanged
argument_list|(
name|newValue
argument_list|)
argument_list|)
expr_stmt|;
name|selectedGroup
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
name|onSelectedGroupChanged
argument_list|(
name|newValue
argument_list|)
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
DECL|method|selectedGroupProperty ()
specifier|public
name|ObjectProperty
argument_list|<
name|GroupNodeViewModel
argument_list|>
name|selectedGroupProperty
parameter_list|()
block|{
return|return
name|selectedGroup
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
DECL|method|onSelectedGroupChanged (GroupNodeViewModel newValue)
specifier|private
name|void
name|onSelectedGroupChanged
parameter_list|(
name|GroupNodeViewModel
name|newValue
parameter_list|)
block|{
name|currentDatabase
operator|.
name|ifPresent
argument_list|(
name|database
lambda|->
block|{
if|if
condition|(
name|newValue
operator|==
literal|null
condition|)
block|{
name|stateManager
operator|.
name|clearSelectedGroup
argument_list|(
name|database
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|stateManager
operator|.
name|setSelectedGroup
argument_list|(
name|database
argument_list|,
name|newValue
operator|.
name|getGroupNode
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
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
name|currentDatabase
operator|=
name|newDatabase
expr_stmt|;
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
name|root
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
name|ifPresent
argument_list|(
name|selectedGroup
lambda|->
name|this
operator|.
name|selectedGroup
operator|.
name|setValue
argument_list|(
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
name|selectedGroup
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Opens "New Group Dialog" and add the resulting group to the root      */
DECL|method|addNewGroupToRoot ()
specifier|public
name|void
name|addNewGroupToRoot
parameter_list|()
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
name|GroupTreeNode
name|newGroupNode
init|=
name|parent
operator|.
name|addSubgroup
argument_list|(
name|group
argument_list|)
decl_stmt|;
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
block|}
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

