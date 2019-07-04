begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.util.strings
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|util
operator|.
name|strings
package|;
end_package

begin_comment
comment|/**  * A String tokenizer that works just like StringTokenizer, but considers quoted  * characters (which do not act as delimiters).  */
end_comment

begin_class
DECL|class|QuotedStringTokenizer
specifier|public
class|class
name|QuotedStringTokenizer
block|{
DECL|field|content
specifier|private
specifier|final
name|String
name|content
decl_stmt|;
DECL|field|contentLength
specifier|private
specifier|final
name|int
name|contentLength
decl_stmt|;
DECL|field|delimiters
specifier|private
specifier|final
name|String
name|delimiters
decl_stmt|;
DECL|field|quoteChar
specifier|private
specifier|final
name|char
name|quoteChar
decl_stmt|;
DECL|field|index
specifier|private
name|int
name|index
decl_stmt|;
comment|/**      * @param content      *            The String to be tokenized.      * @param delimiters      *            The delimiter characters.      * @param quoteCharacter      *            The quoting character. Every character (including, but not      *            limited to, delimiters) that is preceded by this character is      *            not treated as a delimiter, but as a token component.      */
DECL|method|QuotedStringTokenizer (String content, String delimiters, char quoteCharacter)
specifier|public
name|QuotedStringTokenizer
parameter_list|(
name|String
name|content
parameter_list|,
name|String
name|delimiters
parameter_list|,
name|char
name|quoteCharacter
parameter_list|)
block|{
name|this
operator|.
name|content
operator|=
name|content
expr_stmt|;
name|this
operator|.
name|delimiters
operator|=
name|delimiters
expr_stmt|;
name|quoteChar
operator|=
name|quoteCharacter
expr_stmt|;
name|contentLength
operator|=
name|this
operator|.
name|content
operator|.
name|length
argument_list|()
expr_stmt|;
comment|// skip leading delimiters
while|while
condition|(
name|isDelimiter
argument_list|(
name|this
operator|.
name|content
operator|.
name|charAt
argument_list|(
name|index
argument_list|)
argument_list|)
operator|&&
name|index
operator|<
name|contentLength
condition|)
block|{
operator|++
name|index
expr_stmt|;
block|}
block|}
comment|/**      * @return the next token from the content string, ending at the next      * unquoted delimiter. Does not unquote the string itself.      */
DECL|method|nextToken ()
specifier|public
name|String
name|nextToken
parameter_list|()
block|{
name|char
name|c
decl_stmt|;
name|StringBuilder
name|stringBuilder
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
while|while
condition|(
name|index
operator|<
name|contentLength
condition|)
block|{
name|c
operator|=
name|content
operator|.
name|charAt
argument_list|(
name|index
argument_list|)
expr_stmt|;
if|if
condition|(
name|c
operator|==
name|quoteChar
condition|)
block|{
comment|// next is quoted
operator|++
name|index
expr_stmt|;
name|stringBuilder
operator|.
name|append
argument_list|(
name|c
argument_list|)
expr_stmt|;
if|if
condition|(
name|index
operator|<
name|contentLength
condition|)
block|{
name|stringBuilder
operator|.
name|append
argument_list|(
name|content
operator|.
name|charAt
argument_list|(
name|index
argument_list|)
argument_list|)
expr_stmt|;
comment|// ignore for delimiter search!
block|}
block|}
elseif|else
if|if
condition|(
name|isDelimiter
argument_list|(
name|c
argument_list|)
condition|)
block|{
comment|// unit finished
comment|// advance index until next token or end
operator|++
name|index
expr_stmt|;
return|return
name|stringBuilder
operator|.
name|toString
argument_list|()
return|;
block|}
else|else
block|{
name|stringBuilder
operator|.
name|append
argument_list|(
name|c
argument_list|)
expr_stmt|;
block|}
operator|++
name|index
expr_stmt|;
block|}
return|return
name|stringBuilder
operator|.
name|toString
argument_list|()
return|;
block|}
DECL|method|isDelimiter (char c)
specifier|private
name|boolean
name|isDelimiter
parameter_list|(
name|char
name|c
parameter_list|)
block|{
return|return
name|delimiters
operator|.
name|indexOf
argument_list|(
name|c
argument_list|)
operator|>=
literal|0
return|;
block|}
DECL|method|hasMoreTokens ()
specifier|public
name|boolean
name|hasMoreTokens
parameter_list|()
block|{
return|return
name|index
operator|<
name|contentLength
return|;
block|}
block|}
end_class

end_unit

