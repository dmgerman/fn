begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.maintable
package|package
name|net
operator|.
name|sf
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
name|Arrays
import|;
end_import

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
name|stream
operator|.
name|Collectors
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|BibDatabaseContext
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|groups
operator|.
name|GroupMatcher
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|search
operator|.
name|HitOrMissComparator
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|search
operator|.
name|matchers
operator|.
name|EverythingMatcher
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|search
operator|.
name|matchers
operator|.
name|SearchMatcher
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|util
operator|.
name|comparator
operator|.
name|IsMarkedComparator
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
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
name|ca
operator|.
name|odell
operator|.
name|glazedlists
operator|.
name|BasicEventList
import|;
end_import

begin_import
import|import
name|ca
operator|.
name|odell
operator|.
name|glazedlists
operator|.
name|EventList
import|;
end_import

begin_import
import|import
name|ca
operator|.
name|odell
operator|.
name|glazedlists
operator|.
name|FilterList
import|;
end_import

begin_import
import|import
name|ca
operator|.
name|odell
operator|.
name|glazedlists
operator|.
name|SortedList
import|;
end_import

begin_import
import|import
name|ca
operator|.
name|odell
operator|.
name|glazedlists
operator|.
name|matchers
operator|.
name|Matcher
import|;
end_import

begin_class
DECL|class|MainTableDataModel
specifier|public
class|class
name|MainTableDataModel
block|{
DECL|field|listSynchronizer
specifier|private
specifier|final
name|ListSynchronizer
name|listSynchronizer
decl_stmt|;
DECL|field|sortedForUserDefinedTableColumnSorting
specifier|private
specifier|final
name|SortedList
argument_list|<
name|BibEntry
argument_list|>
name|sortedForUserDefinedTableColumnSorting
decl_stmt|;
DECL|field|sortedForMarkingSearchGrouping
specifier|private
specifier|final
name|SortedList
argument_list|<
name|BibEntry
argument_list|>
name|sortedForMarkingSearchGrouping
decl_stmt|;
DECL|field|filterSearchToggle
specifier|private
specifier|final
name|StartStopListFilterAction
name|filterSearchToggle
decl_stmt|;
DECL|field|filterGroupToggle
specifier|private
specifier|final
name|StartStopListFilterAction
name|filterGroupToggle
decl_stmt|;
DECL|field|finalList
specifier|private
specifier|final
name|EventList
argument_list|<
name|BibEntry
argument_list|>
name|finalList
decl_stmt|;
DECL|field|filterAndSortingState
specifier|private
specifier|final
name|FilterAndSortingState
name|filterAndSortingState
init|=
operator|new
name|FilterAndSortingState
argument_list|()
decl_stmt|;
DECL|method|MainTableDataModel (BibDatabaseContext context)
specifier|public
name|MainTableDataModel
parameter_list|(
name|BibDatabaseContext
name|context
parameter_list|)
block|{
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
init|=
name|context
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
decl_stmt|;
name|EventList
argument_list|<
name|BibEntry
argument_list|>
name|initialEventList
init|=
operator|new
name|BasicEventList
argument_list|<>
argument_list|()
decl_stmt|;
name|initialEventList
operator|.
name|addAll
argument_list|(
name|entries
argument_list|)
expr_stmt|;
name|listSynchronizer
operator|=
operator|new
name|ListSynchronizer
argument_list|(
name|initialEventList
argument_list|)
expr_stmt|;
comment|// This SortedList has a Comparator controlled by the TableComparatorChooser
comment|// we are going to install, which responds to user sorting selections:
name|sortedForUserDefinedTableColumnSorting
operator|=
operator|new
name|SortedList
argument_list|<>
argument_list|(
name|initialEventList
argument_list|,
literal|null
argument_list|)
expr_stmt|;
comment|// This SortedList applies afterwards, and floats marked entries:
name|sortedForMarkingSearchGrouping
operator|=
operator|new
name|SortedList
argument_list|<>
argument_list|(
name|sortedForUserDefinedTableColumnSorting
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|FilterList
argument_list|<
name|BibEntry
argument_list|>
name|groupFilterList
init|=
operator|new
name|FilterList
argument_list|<>
argument_list|(
name|sortedForMarkingSearchGrouping
argument_list|,
name|EverythingMatcher
operator|.
name|INSTANCE
argument_list|)
decl_stmt|;
name|filterGroupToggle
operator|=
operator|new
name|StartStopListFilterAction
argument_list|(
name|groupFilterList
argument_list|,
name|GroupMatcher
operator|.
name|INSTANCE
argument_list|,
name|EverythingMatcher
operator|.
name|INSTANCE
argument_list|)
expr_stmt|;
name|FilterList
argument_list|<
name|BibEntry
argument_list|>
name|searchFilterList
init|=
operator|new
name|FilterList
argument_list|<>
argument_list|(
name|groupFilterList
argument_list|,
name|EverythingMatcher
operator|.
name|INSTANCE
argument_list|)
decl_stmt|;
name|filterSearchToggle
operator|=
operator|new
name|StartStopListFilterAction
argument_list|(
name|searchFilterList
argument_list|,
name|SearchMatcher
operator|.
name|INSTANCE
argument_list|,
name|EverythingMatcher
operator|.
name|INSTANCE
argument_list|)
expr_stmt|;
name|finalList
operator|=
name|searchFilterList
expr_stmt|;
block|}
DECL|method|updateSortOrder ()
specifier|public
name|void
name|updateSortOrder
parameter_list|()
block|{
name|Comparator
argument_list|<
name|BibEntry
argument_list|>
name|markingComparator
init|=
name|filterAndSortingState
operator|.
name|markingState
condition|?
name|IsMarkedComparator
operator|.
name|INSTANCE
else|:
literal|null
decl_stmt|;
name|Comparator
argument_list|<
name|BibEntry
argument_list|>
name|searchComparator
init|=
name|getSearchState
argument_list|()
operator|==
name|DisplayOption
operator|.
name|FLOAT
condition|?
operator|new
name|HitOrMissComparator
argument_list|(
name|SearchMatcher
operator|.
name|INSTANCE
argument_list|)
else|:
literal|null
decl_stmt|;
name|Comparator
argument_list|<
name|BibEntry
argument_list|>
name|groupingComparator
init|=
name|getGroupingState
argument_list|()
operator|==
name|DisplayOption
operator|.
name|FLOAT
condition|?
operator|new
name|HitOrMissComparator
argument_list|(
name|GroupMatcher
operator|.
name|INSTANCE
argument_list|)
else|:
literal|null
decl_stmt|;
name|GenericCompositeComparator
name|comparator
init|=
operator|new
name|GenericCompositeComparator
argument_list|(
name|markingComparator
argument_list|,
name|searchComparator
argument_list|,
name|groupingComparator
argument_list|)
decl_stmt|;
name|sortedForMarkingSearchGrouping
operator|.
name|getReadWriteLock
argument_list|()
operator|.
name|writeLock
argument_list|()
operator|.
name|lock
argument_list|()
expr_stmt|;
try|try
block|{
if|if
condition|(
name|sortedForMarkingSearchGrouping
operator|.
name|getComparator
argument_list|()
operator|!=
name|comparator
condition|)
block|{
name|sortedForMarkingSearchGrouping
operator|.
name|setComparator
argument_list|(
name|comparator
argument_list|)
expr_stmt|;
block|}
block|}
finally|finally
block|{
name|sortedForMarkingSearchGrouping
operator|.
name|getReadWriteLock
argument_list|()
operator|.
name|writeLock
argument_list|()
operator|.
name|unlock
argument_list|()
expr_stmt|;
block|}
block|}
DECL|method|updateSearchState (DisplayOption searchState)
specifier|public
name|void
name|updateSearchState
parameter_list|(
name|DisplayOption
name|searchState
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|searchState
argument_list|)
expr_stmt|;
comment|// fail fast
if|if
condition|(
name|filterAndSortingState
operator|.
name|searchState
operator|==
name|searchState
condition|)
block|{
return|return;
block|}
name|boolean
name|updateSortOrder
init|=
literal|false
decl_stmt|;
if|if
condition|(
name|filterAndSortingState
operator|.
name|searchState
operator|==
name|DisplayOption
operator|.
name|FLOAT
condition|)
block|{
name|updateSortOrder
operator|=
literal|true
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|filterAndSortingState
operator|.
name|searchState
operator|==
name|DisplayOption
operator|.
name|FILTER
condition|)
block|{
name|filterSearchToggle
operator|.
name|stop
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|searchState
operator|==
name|DisplayOption
operator|.
name|FLOAT
condition|)
block|{
name|updateSortOrder
operator|=
literal|true
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|searchState
operator|==
name|DisplayOption
operator|.
name|FILTER
condition|)
block|{
name|filterSearchToggle
operator|.
name|start
argument_list|()
expr_stmt|;
block|}
name|filterAndSortingState
operator|.
name|searchState
operator|=
name|searchState
expr_stmt|;
if|if
condition|(
name|updateSortOrder
condition|)
block|{
name|updateSortOrder
argument_list|()
expr_stmt|;
block|}
block|}
DECL|method|updateGroupingState (DisplayOption groupingState)
specifier|public
name|void
name|updateGroupingState
parameter_list|(
name|DisplayOption
name|groupingState
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|groupingState
argument_list|)
expr_stmt|;
comment|// fail fast
if|if
condition|(
name|filterAndSortingState
operator|.
name|groupingState
operator|==
name|groupingState
condition|)
block|{
return|return;
block|}
name|boolean
name|updateSortOrder
init|=
literal|false
decl_stmt|;
if|if
condition|(
name|filterAndSortingState
operator|.
name|groupingState
operator|==
name|DisplayOption
operator|.
name|FLOAT
condition|)
block|{
name|updateSortOrder
operator|=
literal|true
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|filterAndSortingState
operator|.
name|groupingState
operator|==
name|DisplayOption
operator|.
name|FILTER
condition|)
block|{
name|filterGroupToggle
operator|.
name|stop
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|groupingState
operator|==
name|DisplayOption
operator|.
name|FLOAT
condition|)
block|{
name|updateSortOrder
operator|=
literal|true
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|groupingState
operator|==
name|DisplayOption
operator|.
name|FILTER
condition|)
block|{
name|filterGroupToggle
operator|.
name|start
argument_list|()
expr_stmt|;
block|}
name|filterAndSortingState
operator|.
name|groupingState
operator|=
name|groupingState
expr_stmt|;
if|if
condition|(
name|updateSortOrder
condition|)
block|{
name|updateSortOrder
argument_list|()
expr_stmt|;
block|}
block|}
DECL|method|getSearchState ()
specifier|public
name|DisplayOption
name|getSearchState
parameter_list|()
block|{
return|return
name|filterAndSortingState
operator|.
name|searchState
return|;
block|}
DECL|method|getGroupingState ()
name|DisplayOption
name|getGroupingState
parameter_list|()
block|{
return|return
name|filterAndSortingState
operator|.
name|groupingState
return|;
block|}
DECL|method|getListSynchronizer ()
specifier|public
name|ListSynchronizer
name|getListSynchronizer
parameter_list|()
block|{
return|return
name|this
operator|.
name|listSynchronizer
return|;
block|}
DECL|method|updateMarkingState (boolean floatMarkedEntries)
specifier|public
name|void
name|updateMarkingState
parameter_list|(
name|boolean
name|floatMarkedEntries
parameter_list|)
block|{
comment|// fail fast
if|if
condition|(
name|filterAndSortingState
operator|.
name|markingState
operator|==
name|floatMarkedEntries
condition|)
block|{
return|return;
block|}
if|if
condition|(
name|floatMarkedEntries
condition|)
block|{
name|filterAndSortingState
operator|.
name|markingState
operator|=
literal|true
expr_stmt|;
block|}
else|else
block|{
name|filterAndSortingState
operator|.
name|markingState
operator|=
literal|false
expr_stmt|;
block|}
name|updateSortOrder
argument_list|()
expr_stmt|;
block|}
DECL|method|getTableRows ()
name|EventList
argument_list|<
name|BibEntry
argument_list|>
name|getTableRows
parameter_list|()
block|{
return|return
name|finalList
return|;
block|}
comment|/**      * Returns the List of entries sorted by a user-selected term. This is the      * sorting before marking, search etc. applies.      *<p>      * Note: The returned List must not be modified from the outside      *      * @return The sorted list of entries.      */
DECL|method|getSortedForUserDefinedTableColumnSorting ()
name|SortedList
argument_list|<
name|BibEntry
argument_list|>
name|getSortedForUserDefinedTableColumnSorting
parameter_list|()
block|{
return|return
name|sortedForUserDefinedTableColumnSorting
return|;
block|}
DECL|method|updateGroupFilter ()
specifier|public
name|void
name|updateGroupFilter
parameter_list|()
block|{
if|if
condition|(
name|getGroupingState
argument_list|()
operator|==
name|DisplayOption
operator|.
name|FILTER
condition|)
block|{
name|filterGroupToggle
operator|.
name|start
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|filterGroupToggle
operator|.
name|stop
argument_list|()
expr_stmt|;
block|}
block|}
DECL|enum|DisplayOption
specifier|public
enum|enum
name|DisplayOption
block|{
DECL|enumConstant|FLOAT
DECL|enumConstant|FILTER
DECL|enumConstant|DISABLED
name|FLOAT
block|,
name|FILTER
block|,
name|DISABLED
block|}
DECL|class|FilterAndSortingState
specifier|static
class|class
name|FilterAndSortingState
block|{
comment|// at the beginning, everything is disabled
DECL|field|searchState
specifier|private
name|DisplayOption
name|searchState
init|=
name|DisplayOption
operator|.
name|DISABLED
decl_stmt|;
DECL|field|groupingState
specifier|private
name|DisplayOption
name|groupingState
init|=
name|DisplayOption
operator|.
name|DISABLED
decl_stmt|;
DECL|field|markingState
specifier|private
name|boolean
name|markingState
init|=
literal|false
decl_stmt|;
block|}
DECL|class|GenericCompositeComparator
specifier|private
specifier|static
class|class
name|GenericCompositeComparator
implements|implements
name|Comparator
argument_list|<
name|BibEntry
argument_list|>
block|{
DECL|field|comparators
specifier|private
specifier|final
name|List
argument_list|<
name|Comparator
argument_list|<
name|BibEntry
argument_list|>
argument_list|>
name|comparators
decl_stmt|;
annotation|@
name|SafeVarargs
DECL|method|GenericCompositeComparator (Comparator<BibEntry>.... comparators)
specifier|public
name|GenericCompositeComparator
parameter_list|(
name|Comparator
argument_list|<
name|BibEntry
argument_list|>
modifier|...
name|comparators
parameter_list|)
block|{
name|this
operator|.
name|comparators
operator|=
name|Arrays
operator|.
name|asList
argument_list|(
name|comparators
argument_list|)
operator|.
name|stream
argument_list|()
operator|.
name|filter
argument_list|(
name|Objects
operator|::
name|nonNull
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|compare (BibEntry lhs, BibEntry rhs)
specifier|public
name|int
name|compare
parameter_list|(
name|BibEntry
name|lhs
parameter_list|,
name|BibEntry
name|rhs
parameter_list|)
block|{
for|for
control|(
name|Comparator
argument_list|<
name|BibEntry
argument_list|>
name|comp
range|:
name|comparators
control|)
block|{
name|int
name|result
init|=
name|comp
operator|.
name|compare
argument_list|(
name|lhs
argument_list|,
name|rhs
argument_list|)
decl_stmt|;
if|if
condition|(
name|result
operator|!=
literal|0
condition|)
block|{
return|return
name|result
return|;
block|}
block|}
return|return
literal|0
return|;
block|}
block|}
DECL|class|StartStopListFilterAction
specifier|private
specifier|static
class|class
name|StartStopListFilterAction
block|{
DECL|field|active
specifier|private
specifier|final
name|Matcher
argument_list|<
name|BibEntry
argument_list|>
name|active
decl_stmt|;
DECL|field|inactive
specifier|private
specifier|final
name|Matcher
argument_list|<
name|BibEntry
argument_list|>
name|inactive
decl_stmt|;
DECL|field|list
specifier|private
specifier|final
name|FilterList
argument_list|<
name|BibEntry
argument_list|>
name|list
decl_stmt|;
DECL|method|StartStopListFilterAction (FilterList<BibEntry> list, Matcher<BibEntry> active, Matcher<BibEntry> inactive)
specifier|private
name|StartStopListFilterAction
parameter_list|(
name|FilterList
argument_list|<
name|BibEntry
argument_list|>
name|list
parameter_list|,
name|Matcher
argument_list|<
name|BibEntry
argument_list|>
name|active
parameter_list|,
name|Matcher
argument_list|<
name|BibEntry
argument_list|>
name|inactive
parameter_list|)
block|{
name|this
operator|.
name|list
operator|=
name|list
expr_stmt|;
name|this
operator|.
name|active
operator|=
name|active
expr_stmt|;
name|this
operator|.
name|inactive
operator|=
name|inactive
expr_stmt|;
name|list
operator|.
name|setMatcher
argument_list|(
name|inactive
argument_list|)
expr_stmt|;
block|}
DECL|method|start ()
specifier|public
name|void
name|start
parameter_list|()
block|{
name|update
argument_list|(
name|active
argument_list|)
expr_stmt|;
block|}
DECL|method|stop ()
specifier|public
name|void
name|stop
parameter_list|()
block|{
name|update
argument_list|(
name|inactive
argument_list|)
expr_stmt|;
block|}
DECL|method|update (Matcher<BibEntry> comparator)
specifier|private
name|void
name|update
parameter_list|(
name|Matcher
argument_list|<
name|BibEntry
argument_list|>
name|comparator
parameter_list|)
block|{
name|list
operator|.
name|getReadWriteLock
argument_list|()
operator|.
name|writeLock
argument_list|()
operator|.
name|lock
argument_list|()
expr_stmt|;
try|try
block|{
name|list
operator|.
name|setMatcher
argument_list|(
name|comparator
argument_list|)
expr_stmt|;
block|}
finally|finally
block|{
name|list
operator|.
name|getReadWriteLock
argument_list|()
operator|.
name|writeLock
argument_list|()
operator|.
name|unlock
argument_list|()
expr_stmt|;
block|}
block|}
block|}
block|}
end_class

end_unit

