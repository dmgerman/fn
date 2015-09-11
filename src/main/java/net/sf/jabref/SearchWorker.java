begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
package|;
end_package

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
name|JabRefFrame
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
name|SearchResultsDialog
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
name|worker
operator|.
name|AbstractWorker
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
name|BibtexEntry
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
name|l10n
operator|.
name|Localization
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
name|SearchRule
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
name|matchers
operator|.
name|SearchMatcher
import|;
end_import

begin_class
DECL|class|SearchWorker
class|class
name|SearchWorker
extends|extends
name|AbstractWorker
block|{
DECL|field|frame
specifier|private
specifier|final
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|rule
specifier|private
name|SearchRule
name|rule
decl_stmt|;
DECL|field|query
specifier|private
name|String
name|query
init|=
literal|""
decl_stmt|;
DECL|field|mode
specifier|private
name|SearchMode
name|mode
init|=
name|SearchMode
operator|.
name|Incremental
decl_stmt|;
DECL|field|hits
specifier|private
name|int
name|hits
init|=
literal|0
decl_stmt|;
DECL|field|searchDialog
specifier|private
name|SearchResultsDialog
name|searchDialog
init|=
literal|null
decl_stmt|;
comment|/**      * To keep track of where we are in an incremental search. -1 means that the search is inactive.      */
DECL|field|incSearchPos
specifier|private
name|int
name|incSearchPos
init|=
operator|-
literal|1
decl_stmt|;
DECL|method|SearchWorker (JabRefFrame frame)
specifier|public
name|SearchWorker
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|)
block|{
name|this
operator|.
name|frame
operator|=
name|frame
expr_stmt|;
block|}
comment|/**      * Resets the information and display of the previous search.      * DONE      */
DECL|method|restart ()
specifier|public
name|void
name|restart
parameter_list|()
block|{
name|incSearchPos
operator|=
operator|-
literal|1
expr_stmt|;
if|if
condition|(
name|frame
operator|.
name|basePanel
argument_list|()
operator|==
literal|null
condition|)
block|{
return|return;
block|}
if|if
condition|(
name|frame
operator|.
name|basePanel
argument_list|()
operator|.
name|isShowingFloatSearch
argument_list|()
condition|)
block|{
name|frame
operator|.
name|basePanel
argument_list|()
operator|.
name|mainTable
operator|.
name|stopShowingFloatSearch
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|frame
operator|.
name|basePanel
argument_list|()
operator|.
name|isShowingFilterSearch
argument_list|()
condition|)
block|{
name|frame
operator|.
name|basePanel
argument_list|()
operator|.
name|stopShowingSearchResults
argument_list|()
expr_stmt|;
block|}
block|}
comment|/**      * DONE      * Initializes a new search.      */
DECL|method|initSearch (SearchRule rule, String query, SearchMode mode)
specifier|public
name|void
name|initSearch
parameter_list|(
name|SearchRule
name|rule
parameter_list|,
name|String
name|query
parameter_list|,
name|SearchMode
name|mode
parameter_list|)
block|{
name|this
operator|.
name|rule
operator|=
name|rule
expr_stmt|;
if|if
condition|(
name|this
operator|.
name|query
operator|.
name|equals
argument_list|(
name|query
argument_list|)
operator|&&
name|this
operator|.
name|mode
operator|==
name|SearchMode
operator|.
name|Incremental
condition|)
block|{
comment|// The query stayed the same and we are in incremental mode
comment|// So we do not want to start the search at the next item
name|incSearchPos
operator|++
expr_stmt|;
block|}
name|this
operator|.
name|query
operator|=
name|query
expr_stmt|;
if|if
condition|(
name|this
operator|.
name|mode
operator|!=
name|mode
condition|)
block|{
name|this
operator|.
name|mode
operator|=
name|mode
expr_stmt|;
comment|// We changed search mode so reset information
name|restart
argument_list|()
expr_stmt|;
block|}
name|LogFactory
operator|.
name|getLog
argument_list|(
name|SearchWorker
operator|.
name|class
argument_list|)
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
name|query
operator|+
literal|" at "
operator|+
name|incSearchPos
argument_list|)
expr_stmt|;
block|}
comment|/* (non-Javadoc)      * @see net.sf.jabref.Worker#run()      * DONE      */
annotation|@
name|Override
DECL|method|run ()
specifier|public
name|void
name|run
parameter_list|()
block|{
switch|switch
condition|(
name|mode
condition|)
block|{
case|case
name|Incremental
case|:
name|runIncremental
argument_list|()
expr_stmt|;
break|break;
case|case
name|Float
case|:
case|case
name|Filter
case|:
case|case
name|LiveFilter
case|:
case|case
name|ResultsInDialog
case|:
name|runNormal
argument_list|()
expr_stmt|;
break|break;
case|case
name|Global
case|:
name|runGlobal
argument_list|()
expr_stmt|;
break|break;
block|}
block|}
comment|/**      * Searches for matches in all open databases. Saves the number of matches in hits. DONE      */
DECL|method|runGlobal ()
specifier|private
name|void
name|runGlobal
parameter_list|()
block|{
comment|// Search all databases
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|frame
operator|.
name|getTabbedPane
argument_list|()
operator|.
name|getTabCount
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|BasePanel
name|p
init|=
name|frame
operator|.
name|baseAt
argument_list|(
name|i
argument_list|)
decl_stmt|;
for|for
control|(
name|BibtexEntry
name|entry
range|:
name|p
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
control|)
block|{
name|boolean
name|hit
init|=
name|rule
operator|.
name|applyRule
argument_list|(
name|query
argument_list|,
name|entry
argument_list|)
decl_stmt|;
name|entry
operator|.
name|setSearchHit
argument_list|(
name|hit
argument_list|)
expr_stmt|;
if|if
condition|(
name|hit
condition|)
block|{
name|hits
operator|++
expr_stmt|;
block|}
block|}
block|}
block|}
comment|/**      * Searches for matches in the current database. Saves the number of matches in hits. DONE      */
DECL|method|runNormal ()
specifier|private
name|void
name|runNormal
parameter_list|()
block|{
comment|// Search the current database
for|for
control|(
name|BibtexEntry
name|entry
range|:
name|frame
operator|.
name|basePanel
argument_list|()
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
control|)
block|{
name|boolean
name|hit
init|=
name|rule
operator|.
name|applyRule
argument_list|(
name|query
argument_list|,
name|entry
argument_list|)
decl_stmt|;
name|entry
operator|.
name|setSearchHit
argument_list|(
name|hit
argument_list|)
expr_stmt|;
if|if
condition|(
name|hit
condition|)
block|{
name|hits
operator|++
expr_stmt|;
block|}
block|}
block|}
comment|/**      * DONE Searches for the next match, beginning at incSearchPos. The index of the first match is then saved in      * incSearchPos. Sets it to -1 if no further match was found.      */
DECL|method|runIncremental ()
specifier|private
name|void
name|runIncremental
parameter_list|()
block|{
name|int
name|entryCount
init|=
name|frame
operator|.
name|basePanel
argument_list|()
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntryCount
argument_list|()
decl_stmt|;
if|if
condition|(
name|incSearchPos
operator|<
literal|0
condition|)
block|{
name|incSearchPos
operator|=
literal|0
expr_stmt|;
block|}
if|if
condition|(
name|incSearchPos
operator|>=
name|entryCount
condition|)
block|{
name|incSearchPos
operator|=
operator|-
literal|1
expr_stmt|;
return|return;
block|}
for|for
control|(
name|int
name|i
init|=
name|incSearchPos
init|;
name|i
operator|<
name|entryCount
condition|;
name|i
operator|++
control|)
block|{
name|BibtexEntry
name|entry
init|=
name|frame
operator|.
name|basePanel
argument_list|()
operator|.
name|mainTable
operator|.
name|getEntryAt
argument_list|(
name|i
argument_list|)
decl_stmt|;
name|boolean
name|hit
init|=
name|rule
operator|.
name|applyRule
argument_list|(
name|query
argument_list|,
name|entry
argument_list|)
decl_stmt|;
name|entry
operator|.
name|setSearchHit
argument_list|(
name|hit
argument_list|)
expr_stmt|;
if|if
condition|(
name|hit
condition|)
block|{
name|incSearchPos
operator|=
name|i
expr_stmt|;
return|return;
block|}
block|}
name|incSearchPos
operator|=
operator|-
literal|1
expr_stmt|;
return|return;
block|}
comment|/**      * DONE Selects the next match in the entry table based on the position saved in incSearchPos.      */
DECL|method|updateIncremental ()
specifier|private
name|void
name|updateIncremental
parameter_list|()
block|{
name|int
name|entryCount
init|=
name|frame
operator|.
name|basePanel
argument_list|()
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntryCount
argument_list|()
decl_stmt|;
if|if
condition|(
operator|(
name|incSearchPos
operator|>=
name|entryCount
operator|)
operator|||
operator|(
name|incSearchPos
operator|<
literal|0
operator|)
condition|)
block|{
name|frame
operator|.
name|basePanel
argument_list|()
operator|.
name|output
argument_list|(
literal|'\''
operator|+
name|query
operator|+
literal|"' : "
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"Incremental search failed. Repeat to search from top."
argument_list|)
operator|+
literal|'.'
argument_list|)
expr_stmt|;
return|return;
block|}
name|frame
operator|.
name|basePanel
argument_list|()
operator|.
name|selectSingleEntry
argument_list|(
name|incSearchPos
argument_list|)
expr_stmt|;
name|frame
operator|.
name|basePanel
argument_list|()
operator|.
name|output
argument_list|(
literal|'\''
operator|+
name|query
operator|+
literal|"' "
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"found"
argument_list|)
operator|+
literal|'.'
argument_list|)
expr_stmt|;
block|}
comment|/* (non-Javadoc)      * @see net.sf.jabref.AbstractWorker#update()      * DONE      */
annotation|@
name|Override
DECL|method|update ()
specifier|public
name|void
name|update
parameter_list|()
block|{
comment|// Show the result in the chosen way:
switch|switch
condition|(
name|mode
condition|)
block|{
case|case
name|Incremental
case|:
name|updateIncremental
argument_list|()
expr_stmt|;
break|break;
case|case
name|Float
case|:
name|updateFloat
argument_list|()
expr_stmt|;
break|break;
case|case
name|Filter
case|:
case|case
name|LiveFilter
case|:
name|updateFilter
argument_list|()
expr_stmt|;
break|break;
case|case
name|ResultsInDialog
case|:
name|updateResultsInDialog
argument_list|()
expr_stmt|;
break|break;
case|case
name|Global
case|:
name|updateGlobal
argument_list|()
expr_stmt|;
break|break;
block|}
if|if
condition|(
name|mode
operator|!=
name|SearchMode
operator|.
name|Incremental
condition|)
block|{
name|frame
operator|.
name|basePanel
argument_list|()
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Searched database. Number of hits"
argument_list|)
operator|+
literal|": "
operator|+
name|hits
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Floats matches to the top of the entry table. DONE      */
DECL|method|updateFloat ()
specifier|private
name|void
name|updateFloat
parameter_list|()
block|{
comment|// TODO: Rename these things in mainTable, they are not search specific
name|frame
operator|.
name|basePanel
argument_list|()
operator|.
name|mainTable
operator|.
name|showFloatSearch
argument_list|(
operator|new
name|SearchMatcher
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|hits
operator|>
literal|0
condition|)
block|{
name|frame
operator|.
name|basePanel
argument_list|()
operator|.
name|mainTable
operator|.
name|setSelected
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Shows only matches in the entry table by removing non-hits. DONE      */
DECL|method|updateFilter ()
specifier|private
name|void
name|updateFilter
parameter_list|()
block|{
comment|// TODO: Rename these things in basePanel, they are not search specific
name|frame
operator|.
name|basePanel
argument_list|()
operator|.
name|setSearchMatcher
argument_list|(
operator|new
name|SearchMatcher
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|hits
operator|>
literal|0
condition|)
block|{
name|frame
operator|.
name|basePanel
argument_list|()
operator|.
name|mainTable
operator|.
name|setSelected
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Displays search results in a dialog window. DONE      */
DECL|method|updateResultsInDialog ()
specifier|private
name|void
name|updateResultsInDialog
parameter_list|()
block|{
comment|// Make sure the search dialog is instantiated and cleared:
name|initSearchDialog
argument_list|()
expr_stmt|;
name|searchDialog
operator|.
name|clear
argument_list|()
expr_stmt|;
for|for
control|(
name|BibtexEntry
name|entry
range|:
name|frame
operator|.
name|basePanel
argument_list|()
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
control|)
block|{
if|if
condition|(
name|entry
operator|.
name|isSearchHit
argument_list|()
condition|)
block|{
name|searchDialog
operator|.
name|addEntry
argument_list|(
name|entry
argument_list|,
name|frame
operator|.
name|basePanel
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
name|searchDialog
operator|.
name|selectFirstEntry
argument_list|()
expr_stmt|;
name|searchDialog
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
comment|/**      * Displays search results in a dialog window. DONE      */
DECL|method|updateGlobal ()
specifier|private
name|void
name|updateGlobal
parameter_list|()
block|{
comment|// Make sure the search dialog is instantiated and cleared:
name|initSearchDialog
argument_list|()
expr_stmt|;
name|searchDialog
operator|.
name|clear
argument_list|()
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|frame
operator|.
name|getTabbedPane
argument_list|()
operator|.
name|getTabCount
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|BasePanel
name|p
init|=
name|frame
operator|.
name|baseAt
argument_list|(
name|i
argument_list|)
decl_stmt|;
for|for
control|(
name|BibtexEntry
name|entry
range|:
name|p
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
control|)
block|{
if|if
condition|(
name|entry
operator|.
name|isSearchHit
argument_list|()
condition|)
block|{
name|searchDialog
operator|.
name|addEntry
argument_list|(
name|entry
argument_list|,
name|p
argument_list|)
expr_stmt|;
block|}
block|}
block|}
name|searchDialog
operator|.
name|selectFirstEntry
argument_list|()
expr_stmt|;
name|searchDialog
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
comment|/**      * Initializes the search dialog, unless it has already been instantiated. DONE       */
DECL|method|initSearchDialog ()
specifier|private
name|void
name|initSearchDialog
parameter_list|()
block|{
comment|// TODO: Move search dialog to main table and make it non-search specific (similar to filter/float by SearchMatcher
if|if
condition|(
name|searchDialog
operator|==
literal|null
condition|)
block|{
name|searchDialog
operator|=
operator|new
name|SearchResultsDialog
argument_list|(
name|frame
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Search results"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

