begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.search
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
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
name|ArrayList
import|;
end_import

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
name|StringJoiner
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Pattern
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
name|rules
operator|.
name|SentenceAnalyzer
import|;
end_import

begin_class
DECL|class|SearchQueryHighlightObservable
specifier|public
class|class
name|SearchQueryHighlightObservable
block|{
DECL|field|listeners
specifier|private
specifier|final
name|List
argument_list|<
name|SearchQueryHighlightListener
argument_list|>
name|listeners
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|pattern
specifier|private
name|Optional
argument_list|<
name|Pattern
argument_list|>
name|pattern
init|=
name|Optional
operator|.
name|empty
argument_list|()
decl_stmt|;
comment|/**      * Adds a SearchQueryHighlightListener to the search bar. The added listener is immediately informed about the current search.      * Subscribers will be notified about searches.      *      * @param l SearchQueryHighlightListener to be added      */
DECL|method|addSearchListener (SearchQueryHighlightListener l)
specifier|public
name|void
name|addSearchListener
parameter_list|(
name|SearchQueryHighlightListener
name|l
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|l
argument_list|)
expr_stmt|;
if|if
condition|(
name|listeners
operator|.
name|contains
argument_list|(
name|l
argument_list|)
condition|)
block|{
return|return;
block|}
else|else
block|{
name|listeners
operator|.
name|add
argument_list|(
name|l
argument_list|)
expr_stmt|;
block|}
comment|// fire event for the new subscriber
name|l
operator|.
name|highlightPattern
argument_list|(
name|pattern
argument_list|)
expr_stmt|;
block|}
DECL|method|getListenerCount ()
specifier|public
name|int
name|getListenerCount
parameter_list|()
block|{
return|return
name|listeners
operator|.
name|size
argument_list|()
return|;
block|}
comment|/**      * Remove a SearchQueryHighlightListener      *      * @param l SearchQueryHighlightListener to be removed      */
DECL|method|removeSearchListener (SearchQueryHighlightListener l)
specifier|public
name|void
name|removeSearchListener
parameter_list|(
name|SearchQueryHighlightListener
name|l
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|l
argument_list|)
expr_stmt|;
name|listeners
operator|.
name|remove
argument_list|(
name|l
argument_list|)
expr_stmt|;
block|}
comment|/**      * Parses the search query for valid words and returns a list these words. For example, "The great Vikinger" will      * give ["The","great","Vikinger"]      *      * @param searchText the search query      * @return list of words found in the search query      */
DECL|method|getSearchwords (String searchText)
specifier|private
name|List
argument_list|<
name|String
argument_list|>
name|getSearchwords
parameter_list|(
name|String
name|searchText
parameter_list|)
block|{
return|return
operator|(
operator|new
name|SentenceAnalyzer
argument_list|(
name|searchText
argument_list|)
operator|)
operator|.
name|getWords
argument_list|()
return|;
block|}
comment|/**      * Fires an event if a search was started (or cleared)      *      * @param searchQuery the search query      */
DECL|method|fireSearchlistenerEvent (SearchQuery searchQuery)
specifier|public
name|void
name|fireSearchlistenerEvent
parameter_list|(
name|SearchQuery
name|searchQuery
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|searchQuery
argument_list|)
expr_stmt|;
comment|// Parse the search string to words
if|if
condition|(
name|searchQuery
operator|.
name|isGrammarBasedSearch
argument_list|()
condition|)
block|{
name|pattern
operator|=
name|Optional
operator|.
name|empty
argument_list|()
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|searchQuery
operator|.
name|isRegularExpression
argument_list|()
condition|)
block|{
name|pattern
operator|=
name|getPatternForWords
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|searchQuery
operator|.
name|getQuery
argument_list|()
argument_list|)
argument_list|,
literal|true
argument_list|,
name|searchQuery
operator|.
name|isCaseSensitive
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|pattern
operator|=
name|getPatternForWords
argument_list|(
name|getSearchwords
argument_list|(
name|searchQuery
operator|.
name|getQuery
argument_list|()
argument_list|)
argument_list|,
name|searchQuery
operator|.
name|isRegularExpression
argument_list|()
argument_list|,
name|searchQuery
operator|.
name|isCaseSensitive
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|update
argument_list|()
expr_stmt|;
block|}
DECL|method|reset ()
specifier|public
name|void
name|reset
parameter_list|()
block|{
name|pattern
operator|=
name|Optional
operator|.
name|empty
argument_list|()
expr_stmt|;
name|update
argument_list|()
expr_stmt|;
block|}
DECL|method|update ()
specifier|private
name|void
name|update
parameter_list|()
block|{
comment|// Fire an event for every listener
for|for
control|(
name|SearchQueryHighlightListener
name|s
range|:
name|listeners
control|)
block|{
name|s
operator|.
name|highlightPattern
argument_list|(
name|pattern
argument_list|)
expr_stmt|;
block|}
block|}
comment|// Returns a regular expression pattern in the form (w1)|(w2)| ... wi are escaped if no regular expression search is enabled
DECL|method|getPatternForWords (List<String> words, boolean useRegex, boolean isCaseSensitive)
specifier|public
specifier|static
name|Optional
argument_list|<
name|Pattern
argument_list|>
name|getPatternForWords
parameter_list|(
name|List
argument_list|<
name|String
argument_list|>
name|words
parameter_list|,
name|boolean
name|useRegex
parameter_list|,
name|boolean
name|isCaseSensitive
parameter_list|)
block|{
if|if
condition|(
operator|(
name|words
operator|==
literal|null
operator|)
operator|||
name|words
operator|.
name|isEmpty
argument_list|()
operator|||
name|words
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
comment|// compile the words to a regular expression in the form (w1)|(w2)|(w3)
name|StringJoiner
name|joiner
init|=
operator|new
name|StringJoiner
argument_list|(
literal|")|("
argument_list|,
literal|"("
argument_list|,
literal|")"
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|word
range|:
name|words
control|)
block|{
name|joiner
operator|.
name|add
argument_list|(
name|useRegex
condition|?
name|word
else|:
name|Pattern
operator|.
name|quote
argument_list|(
name|word
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|String
name|searchPattern
init|=
name|joiner
operator|.
name|toString
argument_list|()
decl_stmt|;
if|if
condition|(
name|isCaseSensitive
condition|)
block|{
return|return
name|Optional
operator|.
name|of
argument_list|(
name|Pattern
operator|.
name|compile
argument_list|(
name|searchPattern
argument_list|)
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|Optional
operator|.
name|of
argument_list|(
name|Pattern
operator|.
name|compile
argument_list|(
name|searchPattern
argument_list|,
name|Pattern
operator|.
name|CASE_INSENSITIVE
argument_list|)
argument_list|)
return|;
block|}
block|}
block|}
end_class

end_unit

