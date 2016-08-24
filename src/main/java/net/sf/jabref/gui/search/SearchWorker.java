begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.search
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|search
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|LinkedList
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
name|concurrent
operator|.
name|ExecutionException
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
name|javax
operator|.
name|swing
operator|.
name|SwingWorker
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
name|BasePanel
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
name|maintable
operator|.
name|MainTableDataModel
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
name|logic
operator|.
name|search
operator|.
name|SearchQuery
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
name|database
operator|.
name|BibDatabase
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
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|Log
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|LogFactory
import|;
end_import

begin_comment
comment|/**  * Not reusable. Always create a new instance for each search!  */
end_comment

begin_class
DECL|class|SearchWorker
class|class
name|SearchWorker
extends|extends
name|SwingWorker
argument_list|<
name|List
argument_list|<
name|BibEntry
argument_list|>
argument_list|,
name|Void
argument_list|>
block|{
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Log
name|LOGGER
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|SearchWorker
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|basePanel
specifier|private
specifier|final
name|BasePanel
name|basePanel
decl_stmt|;
DECL|field|database
specifier|private
specifier|final
name|BibDatabase
name|database
decl_stmt|;
DECL|field|searchQuery
specifier|private
specifier|final
name|SearchQuery
name|searchQuery
decl_stmt|;
DECL|field|mode
specifier|private
specifier|final
name|SearchMode
name|mode
decl_stmt|;
DECL|method|SearchWorker (BasePanel basePanel, SearchQuery searchQuery, SearchMode mode)
name|SearchWorker
parameter_list|(
name|BasePanel
name|basePanel
parameter_list|,
name|SearchQuery
name|searchQuery
parameter_list|,
name|SearchMode
name|mode
parameter_list|)
block|{
name|this
operator|.
name|basePanel
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|basePanel
argument_list|)
expr_stmt|;
name|this
operator|.
name|database
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|basePanel
operator|.
name|getDatabase
argument_list|()
argument_list|)
expr_stmt|;
name|this
operator|.
name|searchQuery
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|searchQuery
argument_list|)
expr_stmt|;
name|this
operator|.
name|mode
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|mode
argument_list|)
expr_stmt|;
name|LOGGER
operator|.
name|debug
argument_list|(
literal|"Search ("
operator|+
name|this
operator|.
name|mode
operator|.
name|getDisplayName
argument_list|()
operator|+
literal|"): "
operator|+
name|this
operator|.
name|searchQuery
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|doInBackground ()
specifier|protected
name|List
argument_list|<
name|BibEntry
argument_list|>
name|doInBackground
parameter_list|()
throws|throws
name|Exception
block|{
comment|// Search the current database
name|List
argument_list|<
name|BibEntry
argument_list|>
name|matchedEntries
init|=
operator|new
name|LinkedList
argument_list|<>
argument_list|()
decl_stmt|;
name|matchedEntries
operator|.
name|addAll
argument_list|(
name|database
operator|.
name|getEntries
argument_list|()
operator|.
name|stream
argument_list|()
operator|.
name|filter
argument_list|(
name|searchQuery
operator|::
name|isMatch
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
return|return
name|matchedEntries
return|;
block|}
annotation|@
name|Override
DECL|method|done ()
specifier|protected
name|void
name|done
parameter_list|()
block|{
if|if
condition|(
name|isCancelled
argument_list|()
condition|)
block|{
return|return;
block|}
try|try
block|{
name|updateUIWithSearchResult
argument_list|(
name|get
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|InterruptedException
decl||
name|ExecutionException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"something went wrong during the search"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|updateUIWithSearchResult (List<BibEntry> matchedEntries)
specifier|private
name|void
name|updateUIWithSearchResult
parameter_list|(
name|List
argument_list|<
name|BibEntry
argument_list|>
name|matchedEntries
parameter_list|)
block|{
comment|// check if still the current query
if|if
condition|(
operator|!
name|basePanel
operator|.
name|getSearchBar
argument_list|()
operator|.
name|isStillValidQuery
argument_list|(
name|searchQuery
argument_list|)
condition|)
block|{
comment|// do not update - another search was already issued
return|return;
block|}
comment|// clear
for|for
control|(
name|BibEntry
name|entry
range|:
name|basePanel
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
control|)
block|{
name|entry
operator|.
name|setSearchHit
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|BibEntry
name|entry
range|:
name|matchedEntries
control|)
block|{
name|entry
operator|.
name|setSearchHit
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
name|basePanel
operator|.
name|getMainTable
argument_list|()
operator|.
name|getTableModel
argument_list|()
operator|.
name|updateSearchState
argument_list|(
name|MainTableDataModel
operator|.
name|DisplayOption
operator|.
name|DISABLED
argument_list|)
expr_stmt|;
comment|// Show the result in the chosen way:
switch|switch
condition|(
name|mode
condition|)
block|{
case|case
name|FLOAT
case|:
name|basePanel
operator|.
name|getMainTable
argument_list|()
operator|.
name|getTableModel
argument_list|()
operator|.
name|updateSearchState
argument_list|(
name|MainTableDataModel
operator|.
name|DisplayOption
operator|.
name|FLOAT
argument_list|)
expr_stmt|;
break|break;
case|case
name|FILTER
case|:
name|basePanel
operator|.
name|getMainTable
argument_list|()
operator|.
name|getTableModel
argument_list|()
operator|.
name|updateSearchState
argument_list|(
name|MainTableDataModel
operator|.
name|DisplayOption
operator|.
name|FILTER
argument_list|)
expr_stmt|;
break|break;
default|default:
break|break;
block|}
comment|// select first match (i.e., row) if there is any
name|int
name|hits
init|=
name|matchedEntries
operator|.
name|size
argument_list|()
decl_stmt|;
if|if
condition|(
operator|(
name|hits
operator|>
literal|0
operator|)
operator|&&
operator|(
name|basePanel
operator|.
name|getMainTable
argument_list|()
operator|.
name|getRowCount
argument_list|()
operator|>
literal|0
operator|)
condition|)
block|{
name|basePanel
operator|.
name|getMainTable
argument_list|()
operator|.
name|setSelected
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
name|basePanel
operator|.
name|getSearchBar
argument_list|()
operator|.
name|updateResults
argument_list|(
name|hits
argument_list|,
name|searchQuery
operator|.
name|getDescription
argument_list|()
argument_list|,
name|searchQuery
operator|.
name|isGrammarBasedSearch
argument_list|()
argument_list|)
expr_stmt|;
name|basePanel
operator|.
name|getSearchBar
argument_list|()
operator|.
name|getSearchQueryHighlightObservable
argument_list|()
operator|.
name|fireSearchlistenerEvent
argument_list|(
name|searchQuery
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

