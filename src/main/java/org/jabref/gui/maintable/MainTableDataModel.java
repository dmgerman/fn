begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.maintable
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|maintable
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
name|Optional
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
name|IntegerProperty
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
name|SimpleIntegerProperty
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
name|transformation
operator|.
name|FilteredList
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|collections
operator|.
name|transformation
operator|.
name|SortedList
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
name|groups
operator|.
name|GroupViewMode
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
name|BindingsHelper
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

begin_class
DECL|class|MainTableDataModel
specifier|public
class|class
name|MainTableDataModel
block|{
DECL|field|entriesFiltered
specifier|private
specifier|final
name|FilteredList
argument_list|<
name|BibEntryTableViewModel
argument_list|>
name|entriesFiltered
decl_stmt|;
DECL|field|entriesSorted
specifier|private
specifier|final
name|SortedList
argument_list|<
name|BibEntryTableViewModel
argument_list|>
name|entriesSorted
decl_stmt|;
DECL|method|MainTableDataModel (BibDatabaseContext context)
specifier|public
name|MainTableDataModel
parameter_list|(
name|BibDatabaseContext
name|context
parameter_list|)
block|{
name|ObservableList
argument_list|<
name|BibEntry
argument_list|>
name|allEntries
init|=
name|BindingsHelper
operator|.
name|forUI
argument_list|(
name|context
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
argument_list|)
decl_stmt|;
name|ObservableList
argument_list|<
name|BibEntryTableViewModel
argument_list|>
name|entriesViewModel
init|=
name|BindingsHelper
operator|.
name|mapBacked
argument_list|(
name|allEntries
argument_list|,
name|BibEntryTableViewModel
operator|::
operator|new
argument_list|)
decl_stmt|;
name|entriesFiltered
operator|=
operator|new
name|FilteredList
argument_list|<>
argument_list|(
name|entriesViewModel
argument_list|)
expr_stmt|;
name|entriesFiltered
operator|.
name|predicateProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|Bindings
operator|.
name|createObjectBinding
argument_list|(
parameter_list|()
lambda|->
name|this
operator|::
name|isMatched
argument_list|,
name|Globals
operator|.
name|stateManager
operator|.
name|activeGroupProperty
argument_list|()
argument_list|,
name|Globals
operator|.
name|stateManager
operator|.
name|activeSearchQueryProperty
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|IntegerProperty
name|resultSize
init|=
operator|new
name|SimpleIntegerProperty
argument_list|()
decl_stmt|;
name|resultSize
operator|.
name|bind
argument_list|(
name|Bindings
operator|.
name|size
argument_list|(
name|entriesFiltered
argument_list|)
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|stateManager
operator|.
name|setActiveSearchResultSize
argument_list|(
name|context
argument_list|,
name|resultSize
argument_list|)
expr_stmt|;
comment|// We need to wrap the list since otherwise sorting in the table does not work
name|entriesSorted
operator|=
operator|new
name|SortedList
argument_list|<>
argument_list|(
name|entriesFiltered
argument_list|)
expr_stmt|;
block|}
DECL|method|isMatched (BibEntryTableViewModel entry)
specifier|private
name|boolean
name|isMatched
parameter_list|(
name|BibEntryTableViewModel
name|entry
parameter_list|)
block|{
return|return
name|isMatchedByGroup
argument_list|(
name|entry
argument_list|)
operator|&&
name|isMatchedBySearch
argument_list|(
name|entry
argument_list|)
return|;
block|}
DECL|method|isMatchedBySearch (BibEntryTableViewModel entry)
specifier|private
name|boolean
name|isMatchedBySearch
parameter_list|(
name|BibEntryTableViewModel
name|entry
parameter_list|)
block|{
return|return
name|Globals
operator|.
name|stateManager
operator|.
name|activeSearchQueryProperty
argument_list|()
operator|.
name|getValue
argument_list|()
operator|.
name|map
argument_list|(
name|matcher
lambda|->
name|matcher
operator|.
name|isMatch
argument_list|(
name|entry
operator|.
name|getEntry
argument_list|()
argument_list|)
argument_list|)
operator|.
name|orElse
argument_list|(
literal|true
argument_list|)
return|;
block|}
DECL|method|isMatchedByGroup (BibEntryTableViewModel entry)
specifier|private
name|boolean
name|isMatchedByGroup
parameter_list|(
name|BibEntryTableViewModel
name|entry
parameter_list|)
block|{
return|return
name|createGroupMatcher
argument_list|(
name|Globals
operator|.
name|stateManager
operator|.
name|activeGroupProperty
argument_list|()
operator|.
name|getValue
argument_list|()
argument_list|)
operator|.
name|map
argument_list|(
name|matcher
lambda|->
name|matcher
operator|.
name|isMatch
argument_list|(
name|entry
operator|.
name|getEntry
argument_list|()
argument_list|)
argument_list|)
operator|.
name|orElse
argument_list|(
literal|true
argument_list|)
return|;
block|}
DECL|method|createGroupMatcher (List<GroupTreeNode> selectedGroups)
specifier|private
name|Optional
argument_list|<
name|MatcherSet
argument_list|>
name|createGroupMatcher
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
operator|(
name|selectedGroups
operator|==
literal|null
operator|)
operator|||
name|selectedGroups
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
comment|// No selected group, show all entries
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
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
name|getGroupViewMode
argument_list|()
operator|==
name|GroupViewMode
operator|.
name|INTERSECTION
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
return|return
name|Optional
operator|.
name|of
argument_list|(
name|searchRules
argument_list|)
return|;
block|}
DECL|method|getEntriesFilteredAndSorted ()
specifier|public
name|SortedList
argument_list|<
name|BibEntryTableViewModel
argument_list|>
name|getEntriesFilteredAndSorted
parameter_list|()
block|{
return|return
name|entriesSorted
return|;
block|}
block|}
end_class

end_unit

