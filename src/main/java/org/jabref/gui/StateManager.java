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
name|ReadOnlyListProperty
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
name|ReadOnlyListWrapper
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
name|javafx
operator|.
name|collections
operator|.
name|ObservableMap
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
name|OptionalObjectProperty
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
name|search
operator|.
name|SearchQuery
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
name|util
operator|.
name|OptionalUtil
import|;
end_import

begin_comment
comment|/**  * This class manages the GUI-state of JabRef, including:  * - currently selected database  * - currently selected group  * Coming soon:  * - open databases  * - active search  */
end_comment

begin_class
DECL|class|StateManager
specifier|public
class|class
name|StateManager
block|{
DECL|field|activeDatabase
specifier|private
specifier|final
name|OptionalObjectProperty
argument_list|<
name|BibDatabaseContext
argument_list|>
name|activeDatabase
init|=
name|OptionalObjectProperty
operator|.
name|empty
argument_list|()
decl_stmt|;
DECL|field|activeGroups
specifier|private
specifier|final
name|ReadOnlyListWrapper
argument_list|<
name|GroupTreeNode
argument_list|>
name|activeGroups
init|=
operator|new
name|ReadOnlyListWrapper
argument_list|<>
argument_list|(
name|FXCollections
operator|.
name|observableArrayList
argument_list|()
argument_list|)
decl_stmt|;
DECL|field|selectedEntries
specifier|private
specifier|final
name|ObservableList
argument_list|<
name|BibEntry
argument_list|>
name|selectedEntries
init|=
name|FXCollections
operator|.
name|observableArrayList
argument_list|()
decl_stmt|;
DECL|field|selectedGroups
specifier|private
specifier|final
name|ObservableMap
argument_list|<
name|BibDatabaseContext
argument_list|,
name|ObservableList
argument_list|<
name|GroupTreeNode
argument_list|>
argument_list|>
name|selectedGroups
init|=
name|FXCollections
operator|.
name|observableHashMap
argument_list|()
decl_stmt|;
DECL|field|activeSearchQuery
specifier|private
specifier|final
name|OptionalObjectProperty
argument_list|<
name|SearchQuery
argument_list|>
name|activeSearchQuery
init|=
name|OptionalObjectProperty
operator|.
name|empty
argument_list|()
decl_stmt|;
DECL|method|StateManager ()
specifier|public
name|StateManager
parameter_list|()
block|{
name|activeGroups
operator|.
name|bind
argument_list|(
name|Bindings
operator|.
name|valueAt
argument_list|(
name|selectedGroups
argument_list|,
name|activeDatabase
operator|.
name|orElse
argument_list|(
literal|null
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|activeDatabaseProperty ()
specifier|public
name|OptionalObjectProperty
argument_list|<
name|BibDatabaseContext
argument_list|>
name|activeDatabaseProperty
parameter_list|()
block|{
return|return
name|activeDatabase
return|;
block|}
DECL|method|activeSearchQueryProperty ()
specifier|public
name|OptionalObjectProperty
argument_list|<
name|SearchQuery
argument_list|>
name|activeSearchQueryProperty
parameter_list|()
block|{
return|return
name|activeSearchQuery
return|;
block|}
DECL|method|activeGroupProperty ()
specifier|public
name|ReadOnlyListProperty
argument_list|<
name|GroupTreeNode
argument_list|>
name|activeGroupProperty
parameter_list|()
block|{
return|return
name|activeGroups
operator|.
name|getReadOnlyProperty
argument_list|()
return|;
block|}
DECL|method|getSelectedEntries ()
specifier|public
name|ObservableList
argument_list|<
name|BibEntry
argument_list|>
name|getSelectedEntries
parameter_list|()
block|{
return|return
name|selectedEntries
return|;
block|}
DECL|method|setSelectedEntries (List<BibEntry> newSelectedEntries)
specifier|public
name|void
name|setSelectedEntries
parameter_list|(
name|List
argument_list|<
name|BibEntry
argument_list|>
name|newSelectedEntries
parameter_list|)
block|{
name|selectedEntries
operator|.
name|setAll
argument_list|(
name|newSelectedEntries
argument_list|)
expr_stmt|;
block|}
DECL|method|setSelectedGroups (BibDatabaseContext database, List<GroupTreeNode> newSelectedGroups)
specifier|public
name|void
name|setSelectedGroups
parameter_list|(
name|BibDatabaseContext
name|database
parameter_list|,
name|List
argument_list|<
name|GroupTreeNode
argument_list|>
name|newSelectedGroups
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|newSelectedGroups
argument_list|)
expr_stmt|;
name|selectedGroups
operator|.
name|put
argument_list|(
name|database
argument_list|,
name|FXCollections
operator|.
name|observableArrayList
argument_list|(
name|newSelectedGroups
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|getSelectedGroup (BibDatabaseContext database)
specifier|public
name|ObservableList
argument_list|<
name|GroupTreeNode
argument_list|>
name|getSelectedGroup
parameter_list|(
name|BibDatabaseContext
name|database
parameter_list|)
block|{
name|ObservableList
argument_list|<
name|GroupTreeNode
argument_list|>
name|selectedGroupsForDatabase
init|=
name|selectedGroups
operator|.
name|get
argument_list|(
name|database
argument_list|)
decl_stmt|;
return|return
name|selectedGroupsForDatabase
operator|!=
literal|null
condition|?
name|selectedGroupsForDatabase
else|:
name|FXCollections
operator|.
name|observableArrayList
argument_list|()
return|;
block|}
DECL|method|clearSelectedGroups (BibDatabaseContext database)
specifier|public
name|void
name|clearSelectedGroups
parameter_list|(
name|BibDatabaseContext
name|database
parameter_list|)
block|{
name|selectedGroups
operator|.
name|remove
argument_list|(
name|database
argument_list|)
expr_stmt|;
block|}
DECL|method|getActiveDatabase ()
specifier|public
name|Optional
argument_list|<
name|BibDatabaseContext
argument_list|>
name|getActiveDatabase
parameter_list|()
block|{
return|return
name|activeDatabase
operator|.
name|get
argument_list|()
return|;
block|}
DECL|method|getEntriesInCurrentDatabase ()
specifier|public
name|List
argument_list|<
name|BibEntry
argument_list|>
name|getEntriesInCurrentDatabase
parameter_list|()
block|{
return|return
name|OptionalUtil
operator|.
name|flatMap
argument_list|(
name|activeDatabase
operator|.
name|get
argument_list|()
argument_list|,
name|BibDatabaseContext
operator|::
name|getEntries
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
argument_list|)
return|;
block|}
DECL|method|clearSearchQuery ()
specifier|public
name|void
name|clearSearchQuery
parameter_list|()
block|{
name|activeSearchQuery
operator|.
name|setValue
argument_list|(
name|Optional
operator|.
name|empty
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|setSearchQuery (SearchQuery searchQuery)
specifier|public
name|void
name|setSearchQuery
parameter_list|(
name|SearchQuery
name|searchQuery
parameter_list|)
block|{
name|activeSearchQuery
operator|.
name|setValue
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|searchQuery
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

