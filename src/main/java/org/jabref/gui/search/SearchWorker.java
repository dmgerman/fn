begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.search
package|package
name|org
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
name|org
operator|.
name|jabref
operator|.
name|JabRefGUI
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
name|BasePanelMode
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
name|gui
operator|.
name|search
operator|.
name|rules
operator|.
name|describer
operator|.
name|SearchDescribers
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
name|BibDatabase
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
name|slf4j
operator|.
name|Logger
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|LoggerFactory
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
name|Logger
name|LOGGER
init|=
name|LoggerFactory
operator|.
name|getLogger
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
DECL|field|searchDisplayMode
specifier|private
specifier|final
name|SearchDisplayMode
name|searchDisplayMode
decl_stmt|;
DECL|method|SearchWorker (BasePanel basePanel, SearchQuery searchQuery, SearchDisplayMode searchDisplayMode)
specifier|public
name|SearchWorker
parameter_list|(
name|BasePanel
name|basePanel
parameter_list|,
name|SearchQuery
name|searchQuery
parameter_list|,
name|SearchDisplayMode
name|searchDisplayMode
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
name|searchDisplayMode
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|searchDisplayMode
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
name|searchDisplayMode
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
return|return
name|database
operator|.
name|getEntries
argument_list|()
operator|.
name|parallelStream
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
name|GlobalSearchBar
name|globalSearchBar
init|=
name|JabRefGUI
operator|.
name|getMainFrame
argument_list|()
operator|.
name|getGlobalSearchBar
argument_list|()
decl_stmt|;
comment|// check if still the current query
if|if
condition|(
operator|!
name|globalSearchBar
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
comment|// and mark
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
name|searchDisplayMode
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
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Following searchDisplayMode was not defined: "
operator|+
name|searchDisplayMode
argument_list|)
expr_stmt|;
break|break;
block|}
comment|// only selects the first match if the selected entries are no hits or no entry is selected
comment|// and no editor is open (to avoid jumping around when editing an entry)
if|if
condition|(
name|basePanel
operator|.
name|getMode
argument_list|()
operator|!=
name|BasePanelMode
operator|.
name|SHOWING_EDITOR
operator|&&
name|basePanel
operator|.
name|getMode
argument_list|()
operator|!=
name|BasePanelMode
operator|.
name|WILL_SHOW_EDITOR
condition|)
block|{
name|List
argument_list|<
name|BibEntry
argument_list|>
name|selectedEntries
init|=
name|basePanel
operator|.
name|getSelectedEntries
argument_list|()
decl_stmt|;
name|boolean
name|isHitSelected
init|=
name|selectedEntries
operator|.
name|stream
argument_list|()
operator|.
name|anyMatch
argument_list|(
name|BibEntry
operator|::
name|isSearchHit
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|isHitSelected
operator|&&
operator|!
name|matchedEntries
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|basePanel
operator|.
name|getMainTable
argument_list|()
operator|.
name|getRowCount
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|BibEntry
name|entry
init|=
name|basePanel
operator|.
name|getMainTable
argument_list|()
operator|.
name|getEntryAt
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
name|entry
operator|.
name|isSearchHit
argument_list|()
condition|)
block|{
name|basePanel
operator|.
name|getMainTable
argument_list|()
operator|.
name|setSelected
argument_list|(
name|i
argument_list|)
expr_stmt|;
break|break;
block|}
block|}
block|}
block|}
name|globalSearchBar
operator|.
name|updateResults
argument_list|(
name|matchedEntries
operator|.
name|size
argument_list|()
argument_list|,
name|SearchDescribers
operator|.
name|getSearchDescriberFor
argument_list|(
name|searchQuery
argument_list|)
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
name|globalSearchBar
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

