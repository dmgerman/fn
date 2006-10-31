begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.export.layout.format
package|package
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
name|export
operator|.
name|layout
operator|.
name|*
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
name|Globals
import|;
end_import

begin_comment
comment|/**  * Transform a LaTeX-String to RTF.  *   * This method will:  *   *   1.) Remove LaTeX-Command sequences.  *     *   2.) Replace LaTeX-Special chars with RTF aquivalents.  *     *   3.) Replace emph and textit and textbf with their RTF replacements.  *     *   4.) Take special care to save all unicode characters correctly.   *   * @author $Author$  * @version $Revision$ ($Date$)  *  */
end_comment

begin_class
DECL|class|RTFChars
specifier|public
class|class
name|RTFChars
implements|implements
name|LayoutFormatter
block|{
DECL|method|format (String field)
specifier|public
name|String
name|format
parameter_list|(
name|String
name|field
parameter_list|)
block|{
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|(
literal|""
argument_list|)
decl_stmt|;
name|StringBuffer
name|currentCommand
init|=
literal|null
decl_stmt|;
name|boolean
name|escaped
init|=
literal|false
decl_stmt|,
name|incommand
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
name|field
operator|.
name|length
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|char
name|c
init|=
name|field
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
name|escaped
operator|&&
operator|(
name|c
operator|==
literal|'\\'
operator|)
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|'\\'
argument_list|)
expr_stmt|;
name|escaped
operator|=
literal|false
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|c
operator|==
literal|'\\'
condition|)
block|{
name|escaped
operator|=
literal|true
expr_stmt|;
name|incommand
operator|=
literal|true
expr_stmt|;
name|currentCommand
operator|=
operator|new
name|StringBuffer
argument_list|()
expr_stmt|;
block|}
elseif|else
if|if
condition|(
operator|!
name|incommand
operator|&&
operator|(
name|c
operator|==
literal|'{'
operator|||
name|c
operator|==
literal|'}'
operator|)
condition|)
block|{
comment|// Swallow the brace.
block|}
elseif|else
if|if
condition|(
name|Character
operator|.
name|isLetter
argument_list|(
operator|(
name|char
operator|)
name|c
argument_list|)
operator|||
operator|(
name|Globals
operator|.
name|SPECIAL_COMMAND_CHARS
operator|.
name|indexOf
argument_list|(
literal|""
operator|+
operator|(
name|char
operator|)
name|c
argument_list|)
operator|>=
literal|0
operator|)
condition|)
block|{
name|escaped
operator|=
literal|false
expr_stmt|;
if|if
condition|(
operator|!
name|incommand
condition|)
block|{
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
else|else
block|{
comment|// Else we are in a command, and should not keep the letter.
name|currentCommand
operator|.
name|append
argument_list|(
operator|(
name|char
operator|)
name|c
argument_list|)
expr_stmt|;
name|testCharCom
label|:
if|if
condition|(
operator|(
name|currentCommand
operator|.
name|length
argument_list|()
operator|==
literal|1
operator|)
operator|&&
operator|(
name|Globals
operator|.
name|SPECIAL_COMMAND_CHARS
operator|.
name|indexOf
argument_list|(
name|currentCommand
operator|.
name|toString
argument_list|()
argument_list|)
operator|>=
literal|0
operator|)
condition|)
block|{
comment|// This indicates that we are in a command of the type
comment|// \^o or \~{n}
if|if
condition|(
name|i
operator|>=
name|field
operator|.
name|length
argument_list|()
operator|-
literal|1
condition|)
break|break
name|testCharCom
break|;
name|String
name|command
init|=
name|currentCommand
operator|.
name|toString
argument_list|()
decl_stmt|;
name|i
operator|++
expr_stmt|;
name|c
operator|=
name|field
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
expr_stmt|;
name|String
name|combody
decl_stmt|;
if|if
condition|(
name|c
operator|==
literal|'{'
condition|)
block|{
name|IntAndString
name|part
init|=
name|getPart
argument_list|(
name|field
argument_list|,
name|i
argument_list|)
decl_stmt|;
name|i
operator|+=
name|part
operator|.
name|i
expr_stmt|;
name|combody
operator|=
name|part
operator|.
name|s
expr_stmt|;
block|}
else|else
block|{
name|combody
operator|=
name|field
operator|.
name|substring
argument_list|(
name|i
argument_list|,
name|i
operator|+
literal|1
argument_list|)
expr_stmt|;
block|}
name|String
name|result
init|=
operator|(
name|String
operator|)
name|Globals
operator|.
name|RTFCHARS
operator|.
name|get
argument_list|(
name|command
operator|+
name|combody
argument_list|)
decl_stmt|;
if|if
condition|(
name|result
operator|!=
literal|null
condition|)
name|sb
operator|.
name|append
argument_list|(
name|result
argument_list|)
expr_stmt|;
name|incommand
operator|=
literal|false
expr_stmt|;
name|escaped
operator|=
literal|false
expr_stmt|;
block|}
block|}
block|}
else|else
block|{
comment|// if (!incommand || ((c!='{')&& !Character.isWhitespace(c)))
name|testContent
label|:
if|if
condition|(
operator|!
name|incommand
operator|||
operator|(
operator|!
name|Character
operator|.
name|isWhitespace
argument_list|(
name|c
argument_list|)
operator|&&
operator|(
name|c
operator|!=
literal|'{'
operator|)
operator|)
condition|)
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
else|else
block|{
comment|// First test if we are already at the end of the string.
if|if
condition|(
name|i
operator|>=
name|field
operator|.
name|length
argument_list|()
operator|-
literal|1
condition|)
break|break
name|testContent
break|;
if|if
condition|(
name|c
operator|==
literal|'{'
condition|)
block|{
name|String
name|command
init|=
name|currentCommand
operator|.
name|toString
argument_list|()
decl_stmt|;
comment|// Then test if we are dealing with a italics or bold
comment|// command. If so, handle.
if|if
condition|(
name|command
operator|.
name|equals
argument_list|(
literal|"emph"
argument_list|)
operator|||
name|command
operator|.
name|equals
argument_list|(
literal|"textit"
argument_list|)
condition|)
block|{
name|IntAndString
name|part
init|=
name|getPart
argument_list|(
name|field
argument_list|,
name|i
argument_list|)
decl_stmt|;
name|i
operator|+=
name|part
operator|.
name|i
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"}{\\i "
argument_list|)
operator|.
name|append
argument_list|(
name|part
operator|.
name|s
argument_list|)
operator|.
name|append
argument_list|(
literal|"}{"
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|command
operator|.
name|equals
argument_list|(
literal|"textbf"
argument_list|)
condition|)
block|{
name|IntAndString
name|part
init|=
name|getPart
argument_list|(
name|field
argument_list|,
name|i
argument_list|)
decl_stmt|;
name|i
operator|+=
name|part
operator|.
name|i
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"}{\\b "
argument_list|)
operator|.
name|append
argument_list|(
name|part
operator|.
name|s
argument_list|)
operator|.
name|append
argument_list|(
literal|"}{"
argument_list|)
expr_stmt|;
block|}
block|}
else|else
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
name|incommand
operator|=
literal|false
expr_stmt|;
name|escaped
operator|=
literal|false
expr_stmt|;
block|}
block|}
name|char
index|[]
name|chars
init|=
name|sb
operator|.
name|toString
argument_list|()
operator|.
name|toCharArray
argument_list|()
decl_stmt|;
name|sb
operator|=
operator|new
name|StringBuffer
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
name|chars
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|char
name|c
init|=
name|chars
index|[
name|i
index|]
decl_stmt|;
if|if
condition|(
name|c
operator|<
literal|128
condition|)
name|sb
operator|.
name|append
argument_list|(
name|c
argument_list|)
expr_stmt|;
else|else
name|sb
operator|.
name|append
argument_list|(
literal|"\\u"
argument_list|)
operator|.
name|append
argument_list|(
operator|(
name|long
operator|)
name|c
argument_list|)
operator|.
name|append
argument_list|(
literal|'?'
argument_list|)
expr_stmt|;
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
DECL|method|getPart (String text, int i)
specifier|private
name|IntAndString
name|getPart
parameter_list|(
name|String
name|text
parameter_list|,
name|int
name|i
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
comment|// , i=index;
name|StringBuffer
name|part
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
while|while
condition|(
operator|(
name|count
operator|>=
literal|0
operator|)
operator|&&
operator|(
name|i
operator|<
name|text
operator|.
name|length
argument_list|()
operator|)
condition|)
block|{
name|i
operator|++
expr_stmt|;
name|c
operator|=
name|text
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
expr_stmt|;
if|if
condition|(
name|c
operator|==
literal|'}'
condition|)
name|count
operator|--
expr_stmt|;
elseif|else
if|if
condition|(
name|c
operator|==
literal|'{'
condition|)
name|count
operator|++
expr_stmt|;
name|part
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
comment|// System.out.println("part: "+part.toString()+"\nformatted:
comment|// "+format(part.toString()));
return|return
operator|new
name|IntAndString
argument_list|(
name|part
operator|.
name|length
argument_list|()
argument_list|,
name|format
argument_list|(
name|part
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
return|;
block|}
DECL|class|IntAndString
specifier|private
class|class
name|IntAndString
block|{
DECL|field|i
specifier|public
name|int
name|i
decl_stmt|;
DECL|field|s
name|String
name|s
decl_stmt|;
DECL|method|IntAndString (int i, String s)
specifier|public
name|IntAndString
parameter_list|(
name|int
name|i
parameter_list|,
name|String
name|s
parameter_list|)
block|{
name|this
operator|.
name|i
operator|=
name|i
expr_stmt|;
name|this
operator|.
name|s
operator|=
name|s
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

