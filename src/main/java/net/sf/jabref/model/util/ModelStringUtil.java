begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.model.util
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|model
operator|.
name|util
package|;
end_package

begin_class
DECL|class|ModelStringUtil
specifier|public
class|class
name|ModelStringUtil
block|{
comment|// Non-letters which are used to denote accents in LaTeX-commands, e.g., in {\"{a}}
DECL|field|SPECIAL_COMMAND_CHARS
specifier|public
specifier|static
specifier|final
name|String
name|SPECIAL_COMMAND_CHARS
init|=
literal|"\"`^~'=.|"
decl_stmt|;
DECL|method|booleanToBinaryString (boolean expression)
specifier|public
specifier|static
name|String
name|booleanToBinaryString
parameter_list|(
name|boolean
name|expression
parameter_list|)
block|{
return|return
name|expression
condition|?
literal|"1"
else|:
literal|"0"
return|;
block|}
comment|/**      * Quote special characters.      *      * @param toQuote         The String which may contain special characters.      * @param specials  A String containing all special characters except the quoting      *                  character itself, which is automatically quoted.      * @param quoteChar The quoting character.      * @return A String with every special character (including the quoting      * character itself) quoted.      */
DECL|method|quote (String toQuote, String specials, char quoteChar)
specifier|public
specifier|static
name|String
name|quote
parameter_list|(
name|String
name|toQuote
parameter_list|,
name|String
name|specials
parameter_list|,
name|char
name|quoteChar
parameter_list|)
block|{
if|if
condition|(
name|toQuote
operator|==
literal|null
condition|)
block|{
return|return
literal|""
return|;
block|}
name|StringBuilder
name|result
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|char
name|c
decl_stmt|;
name|boolean
name|isSpecial
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
name|toQuote
operator|.
name|length
argument_list|()
condition|;
operator|++
name|i
control|)
block|{
name|c
operator|=
name|toQuote
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
expr_stmt|;
name|isSpecial
operator|=
operator|(
name|c
operator|==
name|quoteChar
operator|)
expr_stmt|;
comment|// If non-null specials performs logic-or with specials.indexOf(c)>= 0
name|isSpecial
operator||=
operator|(
operator|(
name|specials
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|specials
operator|.
name|indexOf
argument_list|(
name|c
argument_list|)
operator|>=
literal|0
operator|)
operator|)
expr_stmt|;
if|if
condition|(
name|isSpecial
condition|)
block|{
name|result
operator|.
name|append
argument_list|(
name|quoteChar
argument_list|)
expr_stmt|;
block|}
name|result
operator|.
name|append
argument_list|(
name|c
argument_list|)
expr_stmt|;
block|}
return|return
name|result
operator|.
name|toString
argument_list|()
return|;
block|}
comment|/**      * Creates a substring from a text      *      * @param text      * @param startIndex      * @param terminateOnEndBraceOnly      * @return      */
DECL|method|getPart (String text, int startIndex, boolean terminateOnEndBraceOnly)
specifier|public
specifier|static
name|String
name|getPart
parameter_list|(
name|String
name|text
parameter_list|,
name|int
name|startIndex
parameter_list|,
name|boolean
name|terminateOnEndBraceOnly
parameter_list|)
block|{
name|char
name|c
decl_stmt|;
name|int
name|count
init|=
literal|0
decl_stmt|;
name|StringBuilder
name|part
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
comment|// advance to first char and skip whitespace
name|int
name|index
init|=
name|startIndex
operator|+
literal|1
decl_stmt|;
while|while
condition|(
operator|(
name|index
operator|<
name|text
operator|.
name|length
argument_list|()
operator|)
operator|&&
name|Character
operator|.
name|isWhitespace
argument_list|(
name|text
operator|.
name|charAt
argument_list|(
name|index
argument_list|)
argument_list|)
condition|)
block|{
name|index
operator|++
expr_stmt|;
block|}
comment|// then grab whatever is the first token (counting braces)
while|while
condition|(
name|index
operator|<
name|text
operator|.
name|length
argument_list|()
condition|)
block|{
name|c
operator|=
name|text
operator|.
name|charAt
argument_list|(
name|index
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|terminateOnEndBraceOnly
operator|&&
operator|(
name|count
operator|==
literal|0
operator|)
operator|&&
name|Character
operator|.
name|isWhitespace
argument_list|(
name|c
argument_list|)
condition|)
block|{
comment|// end argument and leave whitespace for further processing
break|break;
block|}
if|if
condition|(
operator|(
name|c
operator|==
literal|'}'
operator|)
operator|&&
operator|(
operator|--
name|count
operator|<
literal|0
operator|)
condition|)
block|{
break|break;
block|}
elseif|else
if|if
condition|(
name|c
operator|==
literal|'{'
condition|)
block|{
name|count
operator|++
expr_stmt|;
block|}
name|part
operator|.
name|append
argument_list|(
name|c
argument_list|)
expr_stmt|;
name|index
operator|++
expr_stmt|;
block|}
return|return
name|part
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
end_class

end_unit

