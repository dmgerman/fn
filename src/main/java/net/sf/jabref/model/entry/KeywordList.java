begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.model.entry
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
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
name|Collection
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
name|StringTokenizer
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
name|java
operator|.
name|util
operator|.
name|stream
operator|.
name|Stream
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
name|strings
operator|.
name|StringUtil
import|;
end_import

begin_comment
comment|/**  * Represents a list of keyword chains.  * For example, "Type> A, Type> B, Something else".  */
end_comment

begin_class
DECL|class|KeywordList
specifier|public
class|class
name|KeywordList
implements|implements
name|Iterable
argument_list|<
name|Keyword
argument_list|>
block|{
DECL|field|keywords
specifier|private
specifier|final
name|List
argument_list|<
name|Keyword
argument_list|>
name|keywords
decl_stmt|;
DECL|method|KeywordList ()
specifier|public
name|KeywordList
parameter_list|()
block|{
name|keywords
operator|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
expr_stmt|;
block|}
DECL|method|KeywordList (Collection<Keyword> keywords)
specifier|public
name|KeywordList
parameter_list|(
name|Collection
argument_list|<
name|Keyword
argument_list|>
name|keywords
parameter_list|)
block|{
name|this
operator|.
name|keywords
operator|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
expr_stmt|;
name|keywords
operator|.
name|forEach
argument_list|(
name|this
operator|::
name|add
argument_list|)
expr_stmt|;
block|}
DECL|method|KeywordList (List<String> keywords)
specifier|public
name|KeywordList
parameter_list|(
name|List
argument_list|<
name|String
argument_list|>
name|keywords
parameter_list|)
block|{
name|this
argument_list|(
name|keywords
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|Keyword
operator|::
operator|new
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
DECL|method|KeywordList (String... keywords)
specifier|public
name|KeywordList
parameter_list|(
name|String
modifier|...
name|keywords
parameter_list|)
block|{
name|this
argument_list|(
name|Arrays
operator|.
name|stream
argument_list|(
name|keywords
argument_list|)
operator|.
name|map
argument_list|(
name|Keyword
operator|::
operator|new
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
comment|/**      * @param keywordString a String of keywords      * @return an parsed list containing the keywords      */
DECL|method|parse (String keywordString, Character delimiter)
specifier|public
specifier|static
name|KeywordList
name|parse
parameter_list|(
name|String
name|keywordString
parameter_list|,
name|Character
name|delimiter
parameter_list|)
block|{
if|if
condition|(
name|StringUtil
operator|.
name|isBlank
argument_list|(
name|keywordString
argument_list|)
condition|)
block|{
return|return
operator|new
name|KeywordList
argument_list|()
return|;
block|}
name|List
argument_list|<
name|String
argument_list|>
name|keywords
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|StringTokenizer
name|tok
init|=
operator|new
name|StringTokenizer
argument_list|(
name|keywordString
argument_list|,
name|delimiter
operator|.
name|toString
argument_list|()
argument_list|)
decl_stmt|;
while|while
condition|(
name|tok
operator|.
name|hasMoreTokens
argument_list|()
condition|)
block|{
name|String
name|word
init|=
name|tok
operator|.
name|nextToken
argument_list|()
operator|.
name|trim
argument_list|()
decl_stmt|;
name|keywords
operator|.
name|add
argument_list|(
name|word
argument_list|)
expr_stmt|;
block|}
return|return
operator|new
name|KeywordList
argument_list|(
name|keywords
argument_list|)
return|;
block|}
DECL|method|createClone ()
specifier|public
name|KeywordList
name|createClone
parameter_list|()
block|{
return|return
operator|new
name|KeywordList
argument_list|(
name|this
operator|.
name|keywords
argument_list|)
return|;
block|}
DECL|method|replaceAll (KeywordList keywordsToReplace, Keyword newValue)
specifier|public
name|void
name|replaceAll
parameter_list|(
name|KeywordList
name|keywordsToReplace
parameter_list|,
name|Keyword
name|newValue
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|newValue
argument_list|)
expr_stmt|;
comment|// Remove keywords which should be replaced
name|int
name|foundPosition
init|=
operator|-
literal|1
decl_stmt|;
comment|// remember position of the last found keyword
for|for
control|(
name|Keyword
name|specialFieldKeyword
range|:
name|keywordsToReplace
control|)
block|{
name|int
name|pos
init|=
name|keywords
operator|.
name|indexOf
argument_list|(
name|specialFieldKeyword
argument_list|)
decl_stmt|;
if|if
condition|(
name|pos
operator|>=
literal|0
condition|)
block|{
name|foundPosition
operator|=
name|pos
expr_stmt|;
name|keywords
operator|.
name|remove
argument_list|(
name|pos
argument_list|)
expr_stmt|;
block|}
block|}
comment|// Add new keyword at right position
if|if
condition|(
name|foundPosition
operator|==
operator|-
literal|1
condition|)
block|{
name|add
argument_list|(
name|newValue
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|keywords
operator|.
name|add
argument_list|(
name|foundPosition
argument_list|,
name|newValue
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|removeAll (KeywordList keywordsToRemove)
specifier|public
name|void
name|removeAll
parameter_list|(
name|KeywordList
name|keywordsToRemove
parameter_list|)
block|{
name|keywords
operator|.
name|removeAll
argument_list|(
name|keywordsToRemove
operator|.
name|keywords
argument_list|)
expr_stmt|;
block|}
comment|/**      * @deprecated use {@link #replaceAll(KeywordList, Keyword)} or {@link #removeAll(KeywordList)}      */
annotation|@
name|Deprecated
DECL|method|replaceKeywords (KeywordList keywordsToReplace, Optional<Keyword> newValue)
specifier|public
name|void
name|replaceKeywords
parameter_list|(
name|KeywordList
name|keywordsToReplace
parameter_list|,
name|Optional
argument_list|<
name|Keyword
argument_list|>
name|newValue
parameter_list|)
block|{
if|if
condition|(
name|newValue
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|replaceAll
argument_list|(
name|keywordsToReplace
argument_list|,
name|newValue
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|removeAll
argument_list|(
name|keywordsToReplace
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|add (Keyword keyword)
specifier|public
name|boolean
name|add
parameter_list|(
name|Keyword
name|keyword
parameter_list|)
block|{
if|if
condition|(
name|contains
argument_list|(
name|keyword
argument_list|)
condition|)
block|{
return|return
literal|false
return|;
comment|// Don't add duplicate keywords
block|}
return|return
name|keywords
operator|.
name|add
argument_list|(
name|keyword
argument_list|)
return|;
block|}
comment|/**      * Keywords are separated by the given delimiter and an additional space, i.e. "one, two".      */
DECL|method|getAsString (Character delimiter)
specifier|public
name|String
name|getAsString
parameter_list|(
name|Character
name|delimiter
parameter_list|)
block|{
return|return
name|keywords
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|Keyword
operator|::
name|toString
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|joining
argument_list|(
name|delimiter
operator|+
literal|" "
argument_list|)
argument_list|)
return|;
block|}
DECL|method|add (String keywordsString)
specifier|public
name|void
name|add
parameter_list|(
name|String
name|keywordsString
parameter_list|)
block|{
name|add
argument_list|(
operator|new
name|Keyword
argument_list|(
name|keywordsString
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|iterator ()
specifier|public
name|Iterator
argument_list|<
name|Keyword
argument_list|>
name|iterator
parameter_list|()
block|{
return|return
name|keywords
operator|.
name|iterator
argument_list|()
return|;
block|}
DECL|method|size ()
specifier|public
name|int
name|size
parameter_list|()
block|{
return|return
name|keywords
operator|.
name|size
argument_list|()
return|;
block|}
DECL|method|isEmpty ()
specifier|public
name|boolean
name|isEmpty
parameter_list|()
block|{
return|return
name|keywords
operator|.
name|isEmpty
argument_list|()
return|;
block|}
DECL|method|contains (Object o)
specifier|public
name|boolean
name|contains
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
return|return
name|keywords
operator|.
name|contains
argument_list|(
name|o
argument_list|)
return|;
block|}
DECL|method|remove (Object o)
specifier|public
name|boolean
name|remove
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
return|return
name|keywords
operator|.
name|remove
argument_list|(
name|o
argument_list|)
return|;
block|}
DECL|method|addAll (KeywordList keywordsToAdd)
specifier|public
name|void
name|addAll
parameter_list|(
name|KeywordList
name|keywordsToAdd
parameter_list|)
block|{
name|keywords
operator|.
name|addAll
argument_list|(
name|keywordsToAdd
operator|.
name|keywords
argument_list|)
expr_stmt|;
block|}
DECL|method|retainAll (KeywordList keywordToRetain)
specifier|public
name|void
name|retainAll
parameter_list|(
name|KeywordList
name|keywordToRetain
parameter_list|)
block|{
name|keywords
operator|.
name|retainAll
argument_list|(
name|keywordToRetain
operator|.
name|keywords
argument_list|)
expr_stmt|;
block|}
DECL|method|clear ()
specifier|public
name|void
name|clear
parameter_list|()
block|{
name|keywords
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
DECL|method|get (int index)
specifier|public
name|Keyword
name|get
parameter_list|(
name|int
name|index
parameter_list|)
block|{
return|return
name|keywords
operator|.
name|get
argument_list|(
name|index
argument_list|)
return|;
block|}
DECL|method|stream ()
specifier|public
name|Stream
argument_list|<
name|Keyword
argument_list|>
name|stream
parameter_list|()
block|{
return|return
name|keywords
operator|.
name|stream
argument_list|()
return|;
block|}
annotation|@
name|Override
DECL|method|toString ()
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|getAsString
argument_list|(
literal|','
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|equals (Object o)
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
if|if
condition|(
name|this
operator|==
name|o
condition|)
block|{
return|return
literal|true
return|;
block|}
if|if
condition|(
operator|(
name|o
operator|==
literal|null
operator|)
operator|||
operator|(
name|getClass
argument_list|()
operator|!=
name|o
operator|.
name|getClass
argument_list|()
operator|)
condition|)
block|{
return|return
literal|false
return|;
block|}
name|KeywordList
name|keywords1
init|=
operator|(
name|KeywordList
operator|)
name|o
decl_stmt|;
return|return
name|Objects
operator|.
name|equals
argument_list|(
name|keywords
argument_list|,
name|keywords1
operator|.
name|keywords
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|hashCode ()
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|Objects
operator|.
name|hash
argument_list|(
name|keywords
argument_list|)
return|;
block|}
block|}
end_class

end_unit

