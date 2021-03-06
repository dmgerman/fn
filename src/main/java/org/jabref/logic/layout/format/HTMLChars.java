begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.layout.format
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|layout
operator|.
name|format
package|;
end_package

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
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|layout
operator|.
name|LayoutFormatter
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
name|util
operator|.
name|strings
operator|.
name|HTMLUnicodeConversionMaps
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
name|strings
operator|.
name|StringUtil
import|;
end_import

begin_comment
comment|/**  * This formatter escapes characters so they are suitable for HTML.  */
end_comment

begin_class
DECL|class|HTMLChars
specifier|public
class|class
name|HTMLChars
implements|implements
name|LayoutFormatter
block|{
DECL|field|HTML_CHARS
specifier|private
specifier|static
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|HTML_CHARS
init|=
name|HTMLUnicodeConversionMaps
operator|.
name|LATEX_HTML_CONVERSION_MAP
decl_stmt|;
annotation|@
name|Override
DECL|method|format (String inField)
specifier|public
name|String
name|format
parameter_list|(
name|String
name|inField
parameter_list|)
block|{
name|int
name|i
decl_stmt|;
name|String
name|field
init|=
name|inField
operator|.
name|replaceAll
argument_list|(
literal|"&|\\\\&"
argument_list|,
literal|"&amp;"
argument_list|)
comment|// Replace& and \& with&amp;
operator|.
name|replaceAll
argument_list|(
literal|"[\\n]{2,}"
argument_list|,
literal|"<p>"
argument_list|)
comment|// Replace double line breaks with<p>
operator|.
name|replace
argument_list|(
literal|"\n"
argument_list|,
literal|"<br>"
argument_list|)
comment|// Replace single line breaks with<br>
operator|.
name|replace
argument_list|(
literal|"\\$"
argument_list|,
literal|"&dollar;"
argument_list|)
comment|// Replace \$ with&dollar;
operator|.
name|replaceAll
argument_list|(
literal|"\\$([^\\$]*)\\$"
argument_list|,
literal|"\\{$1\\}"
argument_list|)
decl_stmt|;
comment|// Replace $...$ with {...} to simplify conversion
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|StringBuilder
name|currentCommand
init|=
literal|null
decl_stmt|;
name|char
name|c
decl_stmt|;
name|boolean
name|escaped
init|=
literal|false
decl_stmt|;
name|boolean
name|incommand
init|=
literal|false
decl_stmt|;
for|for
control|(
name|i
operator|=
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
name|c
operator|=
name|field
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
expr_stmt|;
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
if|if
condition|(
name|incommand
condition|)
block|{
comment|/* Close Command */
name|String
name|command
init|=
name|currentCommand
operator|.
name|toString
argument_list|()
decl_stmt|;
name|String
name|result
init|=
name|HTML_CHARS
operator|.
name|get
argument_list|(
name|command
argument_list|)
decl_stmt|;
if|if
condition|(
name|result
operator|==
literal|null
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|command
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|sb
operator|.
name|append
argument_list|(
name|result
argument_list|)
expr_stmt|;
block|}
block|}
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
name|StringBuilder
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
operator|(
name|c
operator|==
literal|'{'
operator|)
operator|||
operator|(
name|c
operator|==
literal|'}'
operator|)
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
name|c
argument_list|)
operator|||
operator|(
name|c
operator|==
literal|'%'
operator|)
operator|||
name|StringUtil
operator|.
name|SPECIAL_COMMAND_CHARS
operator|.
name|contains
argument_list|(
name|String
operator|.
name|valueOf
argument_list|(
name|c
argument_list|)
argument_list|)
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
name|c
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|currentCommand
operator|.
name|append
argument_list|(
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
name|StringUtil
operator|.
name|SPECIAL_COMMAND_CHARS
operator|.
name|contains
argument_list|(
name|currentCommand
operator|.
name|toString
argument_list|()
argument_list|)
condition|)
block|{
comment|// This indicates that we are in a command of the type
comment|// \^o or \~{n}
if|if
condition|(
name|i
operator|>=
operator|(
name|field
operator|.
name|length
argument_list|()
operator|-
literal|1
operator|)
condition|)
block|{
break|break
name|testCharCom
break|;
block|}
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
name|commandBody
decl_stmt|;
if|if
condition|(
name|c
operator|==
literal|'{'
condition|)
block|{
name|String
name|part
init|=
name|StringUtil
operator|.
name|getPart
argument_list|(
name|field
argument_list|,
name|i
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|i
operator|+=
name|part
operator|.
name|length
argument_list|()
expr_stmt|;
name|commandBody
operator|=
name|part
expr_stmt|;
block|}
else|else
block|{
name|commandBody
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
name|HTML_CHARS
operator|.
name|get
argument_list|(
name|command
operator|+
name|commandBody
argument_list|)
decl_stmt|;
if|if
condition|(
name|result
operator|==
literal|null
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|commandBody
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|sb
operator|.
name|append
argument_list|(
name|result
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
else|else
block|{
comment|//	Are we already at the end of the string?
if|if
condition|(
operator|(
name|i
operator|+
literal|1
operator|)
operator|==
name|field
operator|.
name|length
argument_list|()
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
name|String
name|result
init|=
name|HTML_CHARS
operator|.
name|get
argument_list|(
name|command
argument_list|)
decl_stmt|;
comment|/* If found, then use translated version. If not,                              * then keep                              * the text of the parameter intact.                              */
if|if
condition|(
name|result
operator|==
literal|null
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|command
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|sb
operator|.
name|append
argument_list|(
name|result
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
block|}
else|else
block|{
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
name|c
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|Character
operator|.
name|isWhitespace
argument_list|(
name|c
argument_list|)
operator|||
operator|(
name|c
operator|==
literal|'{'
operator|)
operator|||
operator|(
name|c
operator|==
literal|'}'
operator|)
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
comment|// Test if we are dealing with a formatting
comment|// command.
comment|// If so, handle.
name|String
name|tag
init|=
name|getHTMLTag
argument_list|(
name|command
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|tag
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|String
name|part
init|=
name|StringUtil
operator|.
name|getPart
argument_list|(
name|field
argument_list|,
name|i
argument_list|,
literal|true
argument_list|)
decl_stmt|;
name|i
operator|+=
name|part
operator|.
name|length
argument_list|()
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|'<'
argument_list|)
operator|.
name|append
argument_list|(
name|tag
argument_list|)
operator|.
name|append
argument_list|(
literal|'>'
argument_list|)
operator|.
name|append
argument_list|(
name|part
argument_list|)
operator|.
name|append
argument_list|(
literal|"</"
argument_list|)
operator|.
name|append
argument_list|(
name|tag
argument_list|)
operator|.
name|append
argument_list|(
literal|'>'
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|c
operator|==
literal|'{'
condition|)
block|{
name|String
name|argument
init|=
name|StringUtil
operator|.
name|getPart
argument_list|(
name|field
argument_list|,
name|i
argument_list|,
literal|true
argument_list|)
decl_stmt|;
name|i
operator|+=
name|argument
operator|.
name|length
argument_list|()
expr_stmt|;
comment|// handle common case of general latex command
name|String
name|result
init|=
name|HTML_CHARS
operator|.
name|get
argument_list|(
name|command
operator|+
name|argument
argument_list|)
decl_stmt|;
comment|// If found, then use translated version. If not, then keep
comment|// the text of the parameter intact.
if|if
condition|(
name|result
operator|==
literal|null
condition|)
block|{
if|if
condition|(
name|argument
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
comment|// Maybe a separator, such as in \LaTeX{}, so use command
name|sb
operator|.
name|append
argument_list|(
name|command
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// Otherwise, use argument
name|sb
operator|.
name|append
argument_list|(
name|argument
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|sb
operator|.
name|append
argument_list|(
name|result
argument_list|)
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|c
operator|==
literal|'}'
condition|)
block|{
comment|// This end brace terminates a command. This can be the case in
comment|// constructs like {\aa}. The correct behaviour should be to
comment|// substitute the evaluated command and swallow the brace:
name|String
name|result
init|=
name|HTML_CHARS
operator|.
name|get
argument_list|(
name|command
argument_list|)
decl_stmt|;
if|if
condition|(
name|result
operator|==
literal|null
condition|)
block|{
comment|// If the command is unknown, just print it:
name|sb
operator|.
name|append
argument_list|(
name|command
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|sb
operator|.
name|append
argument_list|(
name|result
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|String
name|result
init|=
name|HTML_CHARS
operator|.
name|get
argument_list|(
name|command
argument_list|)
decl_stmt|;
if|if
condition|(
name|result
operator|==
literal|null
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|command
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|sb
operator|.
name|append
argument_list|(
name|result
argument_list|)
expr_stmt|;
block|}
name|sb
operator|.
name|append
argument_list|(
literal|' '
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
comment|/*                      * TODO: this point is reached, apparently, if a command is                      * terminated in a strange way, such as with "$\omega$".                      * Also, the command "\&" causes us to get here. The former                      * issue is maybe a little difficult to address, since it                      * involves the LaTeX math mode. We don't have a complete                      * LaTeX parser, so maybe it's better to ignore these                      * commands?                      */
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
return|return
name|sb
operator|.
name|toString
argument_list|()
operator|.
name|replace
argument_list|(
literal|"~"
argument_list|,
literal|"&nbsp;"
argument_list|)
return|;
comment|// Replace any remaining ~ with&nbsp; (non-breaking spaces)
block|}
DECL|method|getHTMLTag (String latexCommand)
specifier|private
name|String
name|getHTMLTag
parameter_list|(
name|String
name|latexCommand
parameter_list|)
block|{
name|String
name|result
init|=
literal|""
decl_stmt|;
switch|switch
condition|(
name|latexCommand
condition|)
block|{
comment|// Italic
case|case
literal|"textit"
case|:
case|case
literal|"it"
case|:
name|result
operator|=
literal|"i"
expr_stmt|;
break|break;
comment|// Emphasize
case|case
literal|"emph"
case|:
case|case
literal|"em"
case|:
name|result
operator|=
literal|"em"
expr_stmt|;
break|break;
comment|// Bold font
case|case
literal|"textbf"
case|:
case|case
literal|"bf"
case|:
name|result
operator|=
literal|"b"
expr_stmt|;
break|break;
comment|// Underline
case|case
literal|"underline"
case|:
name|result
operator|=
literal|"u"
expr_stmt|;
break|break;
comment|// Strikeout, sout is the "standard" command, although it is actually based on the package ulem
case|case
literal|"sout"
case|:
name|result
operator|=
literal|"s"
expr_stmt|;
break|break;
comment|// Monospace font
case|case
literal|"texttt"
case|:
name|result
operator|=
literal|"tt"
expr_stmt|;
break|break;
comment|// Superscript
case|case
literal|"textsuperscript"
case|:
name|result
operator|=
literal|"sup"
expr_stmt|;
break|break;
comment|// Subscript
case|case
literal|"textsubscript"
case|:
name|result
operator|=
literal|"sub"
expr_stmt|;
break|break;
default|default:
break|break;
block|}
return|return
name|result
return|;
block|}
block|}
end_class

end_unit

