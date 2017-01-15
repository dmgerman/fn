begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.model.strings
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|model
operator|.
name|strings
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
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Pattern
import|;
end_import

begin_class
DECL|class|LatexToUnicode
specifier|public
class|class
name|LatexToUnicode
block|{
DECL|field|CHARS
specifier|private
specifier|static
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|CHARS
init|=
name|HTMLUnicodeConversionMaps
operator|.
name|LATEX_UNICODE_CONVERSION_MAP
decl_stmt|;
DECL|field|ACCENTS
specifier|private
specifier|static
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|ACCENTS
init|=
name|HTMLUnicodeConversionMaps
operator|.
name|UNICODE_ESCAPED_ACCENTS
decl_stmt|;
DECL|field|AMP_LATEX
specifier|private
specifier|static
specifier|final
name|Pattern
name|AMP_LATEX
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"&|\\\\&"
argument_list|)
decl_stmt|;
DECL|field|P_LATEX
specifier|private
specifier|static
specifier|final
name|Pattern
name|P_LATEX
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"[\\n]{1,}"
argument_list|)
decl_stmt|;
DECL|field|DOLLAR_LATEX
specifier|private
specifier|static
specifier|final
name|Pattern
name|DOLLAR_LATEX
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"\\\\\\$"
argument_list|)
decl_stmt|;
DECL|field|DOLLARS_LATEX
specifier|private
specifier|static
specifier|final
name|Pattern
name|DOLLARS_LATEX
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"\\$([^\\$]*)\\$"
argument_list|)
decl_stmt|;
DECL|field|AMP
specifier|private
specifier|static
specifier|final
name|Pattern
name|AMP
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"\\&amp;"
argument_list|)
decl_stmt|;
DECL|field|P
specifier|private
specifier|static
specifier|final
name|Pattern
name|P
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"<p>"
argument_list|)
decl_stmt|;
DECL|field|DOLLAR
specifier|private
specifier|static
specifier|final
name|Pattern
name|DOLLAR
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"\\&dollar;"
argument_list|)
decl_stmt|;
DECL|field|TILDE
specifier|private
specifier|static
specifier|final
name|Pattern
name|TILDE
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"~"
argument_list|)
decl_stmt|;
DECL|method|format (String inField)
specifier|public
name|String
name|format
parameter_list|(
name|String
name|inField
parameter_list|)
block|{
if|if
condition|(
name|inField
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
literal|""
return|;
block|}
name|int
name|i
decl_stmt|;
comment|// TODO: document what does this do
name|String
name|field
init|=
name|AMP_LATEX
operator|.
name|matcher
argument_list|(
name|inField
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"&amp;"
argument_list|)
decl_stmt|;
name|field
operator|=
name|P_LATEX
operator|.
name|matcher
argument_list|(
name|field
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"<p>"
argument_list|)
expr_stmt|;
name|field
operator|=
name|DOLLAR_LATEX
operator|.
name|matcher
argument_list|(
name|field
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"&dollar;"
argument_list|)
expr_stmt|;
name|field
operator|=
name|DOLLARS_LATEX
operator|.
name|matcher
argument_list|(
name|field
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"\\{$1\\}"
argument_list|)
expr_stmt|;
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
name|CHARS
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
comment|// a single ' can also be a command
if|if
condition|(
literal|'\''
operator|==
name|c
condition|)
block|{
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
operator|&&
operator|!
operator|(
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
operator|)
condition|)
block|{
comment|// This indicates that we are in a command of the type
comment|// \^o or \~{n}
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
name|fixCollidingCommand
argument_list|(
name|CHARS
operator|.
name|get
argument_list|(
name|command
operator|+
name|commandBody
argument_list|)
argument_list|,
name|c
argument_list|)
decl_stmt|;
if|if
condition|(
name|result
operator|==
literal|null
condition|)
block|{
comment|// Use combining accents if argument is single character or empty
if|if
condition|(
name|commandBody
operator|.
name|length
argument_list|()
operator|<=
literal|1
condition|)
block|{
name|String
name|accent
init|=
name|ACCENTS
operator|.
name|get
argument_list|(
name|command
argument_list|)
decl_stmt|;
if|if
condition|(
name|accent
operator|==
literal|null
condition|)
block|{
comment|// Shouldn't happen
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
name|commandBody
argument_list|)
operator|.
name|append
argument_list|(
name|accent
argument_list|)
expr_stmt|;
block|}
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
comment|//  Are we already at the end of the string?
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
name|CHARS
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
comment|// First test if we are already at the end of the string.
comment|// if (i>= field.length()-1)
comment|// break testContent;
name|String
name|command
init|=
name|currentCommand
operator|.
name|toString
argument_list|()
decl_stmt|;
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
name|CHARS
operator|.
name|get
argument_list|(
name|command
operator|+
name|argument
argument_list|)
decl_stmt|;
comment|// If found, then use translated version. If not, then keep
comment|// the
comment|// text of the parameter intact.
if|if
condition|(
name|result
operator|==
literal|null
condition|)
block|{
comment|// Use combining accents if argument is single character or empty
if|if
condition|(
name|argument
operator|.
name|length
argument_list|()
operator|<=
literal|1
condition|)
block|{
name|String
name|accent
init|=
name|ACCENTS
operator|.
name|get
argument_list|(
name|command
argument_list|)
decl_stmt|;
if|if
condition|(
name|accent
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
comment|// Empty argument, may be used as separator as in \LaTeX{}, so keep the command
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
name|argument
argument_list|)
operator|.
name|append
argument_list|(
name|accent
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
name|CHARS
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
name|CHARS
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
name|String
name|result
init|=
name|AMP
operator|.
name|matcher
argument_list|(
name|sb
operator|.
name|toString
argument_list|()
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"&"
argument_list|)
decl_stmt|;
name|result
operator|=
name|P
operator|.
name|matcher
argument_list|(
name|result
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"\n"
argument_list|)
expr_stmt|;
name|result
operator|=
name|DOLLAR
operator|.
name|matcher
argument_list|(
name|result
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"\\$"
argument_list|)
expr_stmt|;
name|result
operator|=
name|TILDE
operator|.
name|matcher
argument_list|(
name|result
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"\u00A0"
argument_list|)
expr_stmt|;
return|return
name|result
return|;
block|}
DECL|method|fixCollidingCommand (String currentChar, Character bracket)
specifier|private
name|String
name|fixCollidingCommand
parameter_list|(
name|String
name|currentChar
parameter_list|,
name|Character
name|bracket
parameter_list|)
block|{
comment|// when stripping Latex, there is a collision between unicode characters 324 and 329. Hence, this needs to be checked
if|if
condition|(
operator|!
operator|(
literal|"Å"
operator|.
name|equals
argument_list|(
name|currentChar
argument_list|)
operator|&&
literal|'{'
operator|==
name|bracket
operator|)
condition|)
block|{
return|return
name|currentChar
return|;
block|}
else|else
block|{
return|return
literal|"Å"
return|;
block|}
block|}
block|}
end_class

end_unit

