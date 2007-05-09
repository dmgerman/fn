begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.imports
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|imports
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
name|Globals
import|;
end_import

begin_comment
comment|/**  * This class provides the reformatting needed when reading BibTeX fields formatted  * in JabRef style. The reformatting must undo all formatting done by JabRef when  * writing the same fields.  */
end_comment

begin_class
DECL|class|FieldContentParser
specifier|public
class|class
name|FieldContentParser
block|{
comment|/**      * Performs the reformatting      * @param content StringBuffer containing the field to format.      * @return The formatted field content. NOTE: the StringBuffer returned may      * or may not be the same as the argument given.      */
DECL|method|format (StringBuffer content)
specifier|public
name|StringBuffer
name|format
parameter_list|(
name|StringBuffer
name|content
parameter_list|)
block|{
comment|//boolean rep = false;
name|int
name|i
init|=
literal|0
decl_stmt|;
comment|// Remove windows newlines and insert unix ones:
comment|// TODO: 2005.12.3: Added replace from \r to \n, to work around a reported problem of words stiched together.
comment|// But: we need to find out why these lone \r characters appear in his file.
name|content
operator|=
operator|new
name|StringBuffer
argument_list|(
name|content
operator|.
name|toString
argument_list|()
operator|.
name|replaceAll
argument_list|(
literal|"\r\n"
argument_list|,
literal|"\n"
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"\r"
argument_list|,
literal|"\n"
argument_list|)
argument_list|)
expr_stmt|;
comment|//if (rep) System.out.println(content.toString());
comment|/*while (i<content.length()) {             if (content.charAt(i) == '\r')                 content.deleteCharAt(i);             else i++;         }          i=0;*/
while|while
condition|(
name|i
operator|<
name|content
operator|.
name|length
argument_list|()
condition|)
block|{
name|int
name|c
init|=
name|content
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
name|c
operator|==
literal|'\n'
condition|)
block|{
if|if
condition|(
operator|(
name|content
operator|.
name|length
argument_list|()
operator|>
name|i
operator|+
literal|2
operator|)
operator|&&
operator|(
name|content
operator|.
name|charAt
argument_list|(
name|i
operator|+
literal|1
argument_list|)
operator|==
literal|'\t'
operator|)
operator|&&
operator|!
name|Character
operator|.
name|isWhitespace
argument_list|(
name|content
operator|.
name|charAt
argument_list|(
name|i
operator|+
literal|2
argument_list|)
argument_list|)
condition|)
block|{
comment|// We have \n\t followed by non-whitespace, which indicates
comment|// a wrap made by JabRef. Remove and insert space if necessary.
name|content
operator|.
name|deleteCharAt
argument_list|(
name|i
argument_list|)
expr_stmt|;
comment|// \n
name|content
operator|.
name|deleteCharAt
argument_list|(
name|i
argument_list|)
expr_stmt|;
comment|// \t
comment|// Add space only if necessary:
if|if
condition|(
operator|(
name|i
operator|>
literal|0
operator|)
operator|&&
operator|!
name|Character
operator|.
name|isWhitespace
argument_list|(
name|content
operator|.
name|charAt
argument_list|(
name|i
operator|-
literal|1
argument_list|)
argument_list|)
condition|)
block|{
name|content
operator|.
name|insert
argument_list|(
name|i
argument_list|,
literal|' '
argument_list|)
expr_stmt|;
comment|// Increment i because of the inserted character:
name|i
operator|++
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
operator|(
name|content
operator|.
name|length
argument_list|()
operator|>
name|i
operator|+
literal|3
operator|)
operator|&&
operator|(
name|content
operator|.
name|charAt
argument_list|(
name|i
operator|+
literal|1
argument_list|)
operator|==
literal|'\t'
operator|)
operator|&&
operator|(
name|content
operator|.
name|charAt
argument_list|(
name|i
operator|+
literal|2
argument_list|)
operator|==
literal|' '
operator|)
operator|&&
operator|!
name|Character
operator|.
name|isWhitespace
argument_list|(
name|content
operator|.
name|charAt
argument_list|(
name|i
operator|+
literal|3
argument_list|)
argument_list|)
condition|)
block|{
comment|// We have \n\t followed by ' ' followed by non-whitespace, which indicates
comment|// a wrap made by JabRef<= 1.7.1. Remove:
name|content
operator|.
name|deleteCharAt
argument_list|(
name|i
argument_list|)
expr_stmt|;
comment|// \n
name|content
operator|.
name|deleteCharAt
argument_list|(
name|i
argument_list|)
expr_stmt|;
comment|// \t
comment|// Remove space only if necessary:
if|if
condition|(
operator|(
name|i
operator|>
literal|0
operator|)
operator|&&
name|Character
operator|.
name|isWhitespace
argument_list|(
name|content
operator|.
name|charAt
argument_list|(
name|i
operator|-
literal|1
argument_list|)
argument_list|)
condition|)
block|{
name|content
operator|.
name|deleteCharAt
argument_list|(
name|i
argument_list|)
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
operator|(
name|content
operator|.
name|length
argument_list|()
operator|>
name|i
operator|+
literal|3
operator|)
operator|&&
operator|(
name|content
operator|.
name|charAt
argument_list|(
name|i
operator|+
literal|1
argument_list|)
operator|==
literal|'\t'
operator|)
operator|&&
operator|(
name|content
operator|.
name|charAt
argument_list|(
name|i
operator|+
literal|2
argument_list|)
operator|==
literal|'\n'
operator|)
operator|&&
operator|(
name|content
operator|.
name|charAt
argument_list|(
name|i
operator|+
literal|3
argument_list|)
operator|==
literal|'\t'
operator|)
condition|)
block|{
comment|// We have \n\t\n\t, which looks like a JabRef-formatted empty line.
comment|// Remove the tabs and keep one of the line breaks:
name|content
operator|.
name|deleteCharAt
argument_list|(
name|i
operator|+
literal|1
argument_list|)
expr_stmt|;
comment|// \t
name|content
operator|.
name|deleteCharAt
argument_list|(
name|i
operator|+
literal|1
argument_list|)
expr_stmt|;
comment|// \n
name|content
operator|.
name|deleteCharAt
argument_list|(
name|i
operator|+
literal|1
argument_list|)
expr_stmt|;
comment|// \t
comment|// Skip past the line breaks:
name|i
operator|++
expr_stmt|;
comment|// Now, if more \n\t pairs are following, keep each line break. This
comment|// preserves several line breaks properly. Repeat until done:
while|while
condition|(
operator|(
name|content
operator|.
name|length
argument_list|()
operator|>
name|i
operator|+
literal|1
operator|)
operator|&&
operator|(
name|content
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
operator|==
literal|'\n'
operator|)
operator|&&
operator|(
name|content
operator|.
name|charAt
argument_list|(
name|i
operator|+
literal|1
argument_list|)
operator|==
literal|'\t'
operator|)
condition|)
block|{
name|content
operator|.
name|deleteCharAt
argument_list|(
name|i
operator|+
literal|1
argument_list|)
expr_stmt|;
name|i
operator|++
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
operator|(
name|content
operator|.
name|length
argument_list|()
operator|>
name|i
operator|+
literal|1
operator|)
operator|&&
operator|(
name|content
operator|.
name|charAt
argument_list|(
name|i
operator|+
literal|1
argument_list|)
operator|!=
literal|'\n'
operator|)
condition|)
block|{
comment|// We have a line break not followed by another line break. This is probably a normal
comment|// line break made by whatever other editor, so we will remove the line break.
name|content
operator|.
name|deleteCharAt
argument_list|(
name|i
argument_list|)
expr_stmt|;
comment|// If the line break is not accompanied by other whitespace we must add a space:
if|if
condition|(
operator|!
name|Character
operator|.
name|isWhitespace
argument_list|(
name|content
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
argument_list|)
operator|&&
comment|// No whitespace after?
operator|(
name|i
operator|>
literal|0
operator|)
operator|&&
operator|!
name|Character
operator|.
name|isWhitespace
argument_list|(
name|content
operator|.
name|charAt
argument_list|(
name|i
operator|-
literal|1
argument_list|)
argument_list|)
condition|)
comment|// No whitespace before?
name|content
operator|.
name|insert
argument_list|(
name|i
argument_list|,
literal|' '
argument_list|)
expr_stmt|;
block|}
comment|//else if ((content.length()>i+1)&& (content.charAt(i+1)=='\n'))
else|else
name|i
operator|++
expr_stmt|;
comment|//content.deleteCharAt(i);
block|}
elseif|else
if|if
condition|(
name|c
operator|==
literal|' '
condition|)
block|{
comment|//if ((content.length()>i+2)&& (content.charAt(i+1)==' ')) {
if|if
condition|(
operator|(
name|i
operator|>
literal|0
operator|)
operator|&&
operator|(
name|content
operator|.
name|charAt
argument_list|(
name|i
operator|-
literal|1
argument_list|)
operator|==
literal|' '
operator|)
condition|)
block|{
comment|// We have two spaces in a row. Don't include this one.
name|content
operator|.
name|deleteCharAt
argument_list|(
name|i
argument_list|)
expr_stmt|;
block|}
else|else
name|i
operator|++
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|c
operator|==
literal|'\t'
condition|)
comment|// Remove all tab characters that aren't associated with a line break.
name|content
operator|.
name|deleteCharAt
argument_list|(
name|i
argument_list|)
expr_stmt|;
else|else
name|i
operator|++
expr_stmt|;
block|}
return|return
name|content
return|;
block|}
comment|/**      * Formats field contents for output. Must be "symmetric" with the parse method above,      * so stored and reloaded fields are not mangled.      * @param in      * @param wrapAmount      * @return the wrapped String.      */
DECL|method|wrap (String in, int wrapAmount)
specifier|public
specifier|static
name|String
name|wrap
parameter_list|(
name|String
name|in
parameter_list|,
name|int
name|wrapAmount
parameter_list|)
block|{
name|String
index|[]
name|lines
init|=
name|in
operator|.
name|split
argument_list|(
literal|"\n"
argument_list|)
decl_stmt|;
name|StringBuffer
name|res
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|addWrappedLine
argument_list|(
name|res
argument_list|,
name|lines
index|[
literal|0
index|]
argument_list|,
name|wrapAmount
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|1
init|;
name|i
operator|<
name|lines
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
name|lines
index|[
name|i
index|]
operator|.
name|trim
argument_list|()
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
block|{
name|res
operator|.
name|append
argument_list|(
name|Globals
operator|.
name|NEWLINE
argument_list|)
expr_stmt|;
name|res
operator|.
name|append
argument_list|(
literal|'\t'
argument_list|)
expr_stmt|;
name|res
operator|.
name|append
argument_list|(
name|Globals
operator|.
name|NEWLINE
argument_list|)
expr_stmt|;
name|res
operator|.
name|append
argument_list|(
literal|'\t'
argument_list|)
expr_stmt|;
name|addWrappedLine
argument_list|(
name|res
argument_list|,
name|lines
index|[
name|i
index|]
argument_list|,
name|wrapAmount
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|res
operator|.
name|append
argument_list|(
name|Globals
operator|.
name|NEWLINE
argument_list|)
expr_stmt|;
name|res
operator|.
name|append
argument_list|(
literal|'\t'
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|res
operator|.
name|toString
argument_list|()
return|;
block|}
DECL|method|addWrappedLine (StringBuffer res, String line, int wrapAmount)
specifier|private
specifier|static
name|void
name|addWrappedLine
parameter_list|(
name|StringBuffer
name|res
parameter_list|,
name|String
name|line
parameter_list|,
name|int
name|wrapAmount
parameter_list|)
block|{
comment|// Set our pointer to the beginning of the new line in the StringBuffer:
name|int
name|p
init|=
name|res
operator|.
name|length
argument_list|()
decl_stmt|;
comment|// Add the line, unmodified:
name|res
operator|.
name|append
argument_list|(
name|line
argument_list|)
expr_stmt|;
while|while
condition|(
name|p
operator|<
name|res
operator|.
name|length
argument_list|()
condition|)
block|{
name|int
name|q
init|=
name|res
operator|.
name|indexOf
argument_list|(
literal|" "
argument_list|,
name|p
operator|+
name|wrapAmount
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|q
operator|<
literal|0
operator|)
operator|||
operator|(
name|q
operator|>=
name|res
operator|.
name|length
argument_list|()
operator|)
condition|)
break|break;
name|res
operator|.
name|deleteCharAt
argument_list|(
name|q
argument_list|)
expr_stmt|;
name|res
operator|.
name|insert
argument_list|(
name|q
argument_list|,
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"\t"
argument_list|)
expr_stmt|;
name|p
operator|=
name|q
operator|+
name|Globals
operator|.
name|NEWLINE_LENGTH
expr_stmt|;
block|}
block|}
DECL|class|Indents
specifier|static
class|class
name|Indents
block|{
comment|//int hyp
block|}
block|}
end_class

end_unit

