begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.search
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|search
package|;
end_package

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
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
name|export
operator|.
name|layout
operator|.
name|format
operator|.
name|RemoveBrackets
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
import|;
end_import

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
name|Iterator
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
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
name|Matcher
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

begin_comment
comment|/**  * Search rule for simple search.  */
end_comment

begin_class
DECL|class|BasicSearch
specifier|public
class|class
name|BasicSearch
implements|implements
name|SearchRule
block|{
DECL|field|caseSensitive
specifier|private
name|boolean
name|caseSensitive
decl_stmt|;
DECL|field|regExp
specifier|private
name|boolean
name|regExp
decl_stmt|;
DECL|field|pattern
name|Pattern
index|[]
name|pattern
decl_stmt|;
DECL|field|removeBrackets
specifier|static
name|RemoveBrackets
name|removeBrackets
init|=
operator|new
name|RemoveBrackets
argument_list|()
decl_stmt|;
DECL|method|BasicSearch (boolean caseSensitive, boolean regExp)
specifier|public
name|BasicSearch
parameter_list|(
name|boolean
name|caseSensitive
parameter_list|,
name|boolean
name|regExp
parameter_list|)
block|{
name|this
operator|.
name|caseSensitive
operator|=
name|caseSensitive
expr_stmt|;
name|this
operator|.
name|regExp
operator|=
name|regExp
expr_stmt|;
block|}
DECL|method|print (ArrayList list)
specifier|private
specifier|static
name|void
name|print
parameter_list|(
name|ArrayList
name|list
parameter_list|)
block|{
for|for
control|(
name|Iterator
name|i
init|=
name|list
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|String
name|s
init|=
operator|(
name|String
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|System
operator|.
name|out
operator|.
name|print
argument_list|(
literal|"'"
operator|+
name|s
operator|+
literal|"' "
argument_list|)
expr_stmt|;
block|}
name|System
operator|.
name|out
operator|.
name|println
argument_list|()
expr_stmt|;
block|}
DECL|method|applyRule (String query, BibtexEntry bibtexEntry)
specifier|public
name|int
name|applyRule
parameter_list|(
name|String
name|query
parameter_list|,
name|BibtexEntry
name|bibtexEntry
parameter_list|)
block|{
name|HashMap
name|map
init|=
operator|new
name|HashMap
argument_list|()
decl_stmt|;
name|map
operator|.
name|put
argument_list|(
literal|"1"
argument_list|,
name|query
argument_list|)
expr_stmt|;
return|return
name|applyRule
argument_list|(
name|map
argument_list|,
name|bibtexEntry
argument_list|)
return|;
block|}
DECL|method|applyRule (Map searchStrings, BibtexEntry bibtexEntry)
specifier|public
name|int
name|applyRule
parameter_list|(
name|Map
name|searchStrings
parameter_list|,
name|BibtexEntry
name|bibtexEntry
parameter_list|)
block|{
name|int
name|flags
init|=
literal|0
decl_stmt|;
name|String
name|searchString
init|=
operator|(
name|String
operator|)
name|searchStrings
operator|.
name|values
argument_list|()
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|caseSensitive
condition|)
block|{
name|searchString
operator|=
name|searchString
operator|.
name|toLowerCase
argument_list|()
expr_stmt|;
name|flags
operator|=
name|Pattern
operator|.
name|CASE_INSENSITIVE
expr_stmt|;
block|}
name|ArrayList
name|words
init|=
name|parseQuery
argument_list|(
name|searchString
argument_list|)
decl_stmt|;
if|if
condition|(
name|regExp
condition|)
block|{
name|pattern
operator|=
operator|new
name|Pattern
index|[
name|words
operator|.
name|size
argument_list|()
index|]
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
name|pattern
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|pattern
index|[
name|i
index|]
operator|=
name|Pattern
operator|.
name|compile
argument_list|(
operator|(
name|String
operator|)
name|words
operator|.
name|get
argument_list|(
name|i
argument_list|)
argument_list|,
name|flags
argument_list|)
expr_stmt|;
block|}
block|}
comment|//print(words);
comment|// We need match for all words:
name|boolean
index|[]
name|matchFound
init|=
operator|new
name|boolean
index|[
name|words
operator|.
name|size
argument_list|()
index|]
decl_stmt|;
name|Object
name|fieldContentAsObject
decl_stmt|;
name|String
name|fieldContent
decl_stmt|;
name|Object
index|[]
name|fields
init|=
name|bibtexEntry
operator|.
name|getAllFields
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|fields
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|fieldContentAsObject
operator|=
name|bibtexEntry
operator|.
name|getField
argument_list|(
name|fields
index|[
name|i
index|]
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|fieldContentAsObject
operator|!=
literal|null
condition|)
block|{
name|fieldContent
operator|=
name|removeBrackets
operator|.
name|format
argument_list|(
name|fieldContentAsObject
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|caseSensitive
condition|)
name|fieldContent
operator|=
name|fieldContent
operator|.
name|toLowerCase
argument_list|()
expr_stmt|;
name|int
name|index
init|=
literal|0
decl_stmt|;
comment|// Check if we have a match for each of the query words, ignoring
comment|// those words for which we already have a match:
for|for
control|(
name|int
name|j
init|=
literal|0
init|;
name|j
operator|<
name|words
operator|.
name|size
argument_list|()
condition|;
name|j
operator|++
control|)
block|{
if|if
condition|(
operator|!
name|regExp
condition|)
block|{
name|String
name|s
init|=
operator|(
name|String
operator|)
name|words
operator|.
name|get
argument_list|(
name|j
argument_list|)
decl_stmt|;
name|matchFound
index|[
name|index
index|]
operator|=
name|matchFound
index|[
name|index
index|]
operator|||
operator|(
name|fieldContent
operator|.
name|indexOf
argument_list|(
name|s
argument_list|)
operator|>=
literal|0
operator|)
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|fieldContent
operator|!=
literal|null
condition|)
block|{
name|Matcher
name|m
init|=
name|pattern
index|[
name|j
index|]
operator|.
name|matcher
argument_list|(
name|removeBrackets
operator|.
name|format
argument_list|(
name|fieldContent
argument_list|)
argument_list|)
decl_stmt|;
name|matchFound
index|[
name|index
index|]
operator|=
name|matchFound
index|[
name|index
index|]
operator|||
name|m
operator|.
name|find
argument_list|()
expr_stmt|;
block|}
block|}
name|index
operator|++
expr_stmt|;
block|}
block|}
block|}
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|matchFound
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
operator|!
name|matchFound
index|[
name|i
index|]
condition|)
return|return
literal|0
return|;
comment|// Didn't match all words.
block|}
return|return
literal|1
return|;
comment|// Matched all words.
block|}
DECL|method|parseQuery (String query)
specifier|private
name|ArrayList
name|parseQuery
parameter_list|(
name|String
name|query
parameter_list|)
block|{
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|ArrayList
name|result
init|=
operator|new
name|ArrayList
argument_list|()
decl_stmt|;
name|int
name|c
decl_stmt|;
name|boolean
name|escaped
init|=
literal|false
decl_stmt|,
name|quoted
init|=
literal|false
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|query
operator|.
name|length
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|c
operator|=
name|query
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
expr_stmt|;
comment|// Check if we are entering an escape sequence:
if|if
condition|(
operator|!
name|escaped
operator|&&
operator|(
name|c
operator|==
literal|'\\'
operator|)
condition|)
name|escaped
operator|=
literal|true
expr_stmt|;
else|else
block|{
comment|// See if we have reached the end of a word:
if|if
condition|(
operator|!
name|escaped
operator|&&
operator|!
name|quoted
operator|&&
name|Character
operator|.
name|isWhitespace
argument_list|(
operator|(
name|char
operator|)
name|c
argument_list|)
condition|)
block|{
if|if
condition|(
name|sb
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|result
operator|.
name|add
argument_list|(
name|sb
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|sb
operator|=
operator|new
name|StringBuffer
argument_list|()
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|c
operator|==
literal|'"'
condition|)
block|{
comment|// Whether it is a start or end quote, store the current
comment|// word if any:
if|if
condition|(
name|sb
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|result
operator|.
name|add
argument_list|(
name|sb
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|sb
operator|=
operator|new
name|StringBuffer
argument_list|()
expr_stmt|;
block|}
name|quoted
operator|=
operator|!
name|quoted
expr_stmt|;
block|}
else|else
block|{
comment|// All other possibilities exhausted, we add the char to
comment|// the current word:
name|sb
operator|.
name|append
argument_list|(
operator|(
name|char
operator|)
name|c
argument_list|)
expr_stmt|;
block|}
name|escaped
operator|=
literal|false
expr_stmt|;
block|}
block|}
comment|// Finished with the loop. If we have a current word, add it:
if|if
condition|(
name|sb
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|result
operator|.
name|add
argument_list|(
name|sb
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|result
return|;
block|}
block|}
end_class

end_unit

