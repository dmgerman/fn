begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

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
name|Globals
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
name|LayoutFormatter
import|;
end_import

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
name|Log
import|;
end_import

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

begin_comment
comment|/**  * Transform a LaTeX-String to RTF.  *   * This method will:  *   *   1.) Remove LaTeX-Command sequences.  *     *   2.) Replace LaTeX-Special chars with RTF aquivalents.  *     *   3.) Replace emph and textit and textbf with their RTF replacements.  *     *   4.) Take special care to save all unicode characters correctly.  *  *   5.) Replace --- by \emdash and -- by \endash.  */
end_comment

begin_class
DECL|class|RTFChars
specifier|public
class|class
name|RTFChars
implements|implements
name|LayoutFormatter
block|{
comment|// Instantiate logger:
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Log
name|LOGGER
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|LayoutFormatter
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|RTF_CHARS
specifier|private
specifier|static
specifier|final
name|RtfCharMap
name|RTF_CHARS
init|=
operator|new
name|RtfCharMap
argument_list|()
decl_stmt|;
annotation|@
name|Override
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
decl_stmt|;
name|boolean
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
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"incommand="
operator|+
name|incommand
operator|+
literal|". escaped="
operator|+
name|escaped
operator|+
literal|". currentCommand='"
operator|+
operator|(
name|currentCommand
operator|!=
literal|null
condition|?
name|currentCommand
operator|.
name|toString
argument_list|()
else|:
literal|""
operator|)
operator|+
literal|'\''
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"sb: '"
operator|+
name|sb
operator|+
literal|'\''
argument_list|)
expr_stmt|;
comment|/**/
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
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Char: '"
operator|+
name|c
operator|+
literal|'\''
argument_list|)
expr_stmt|;
if|if
condition|(
name|escaped
operator|&&
name|c
operator|==
literal|'\\'
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
name|c
argument_list|)
operator|||
name|Globals
operator|.
name|SPECIAL_COMMAND_CHARS
operator|.
name|contains
argument_list|(
literal|""
operator|+
name|c
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
comment|// Else we are in a command, and should not keep the letter.
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
name|currentCommand
operator|.
name|length
argument_list|()
operator|==
literal|1
operator|&&
name|Globals
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
name|field
operator|.
name|length
argument_list|()
operator|-
literal|1
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
argument_list|,
literal|true
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
name|RTF_CHARS
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
operator|!
name|Character
operator|.
name|isWhitespace
argument_list|(
name|c
argument_list|)
operator|&&
name|c
operator|!=
literal|'{'
operator|&&
name|c
operator|!=
literal|'}'
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
assert|assert
name|incommand
assert|;
comment|// First test for braces that may be part of a LaTeX command:
if|if
condition|(
name|c
operator|==
literal|'{'
operator|&&
name|currentCommand
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
block|{
comment|// We have seen something like \{, which is probably the start
comment|// of a command like \{aa}. Swallow the brace.
continue|continue;
block|}
elseif|else
if|if
condition|(
name|c
operator|==
literal|'}'
operator|&&
name|currentCommand
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
comment|// Seems to be the end of a command like \{aa}. Look it up:
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
name|RTF_CHARS
operator|.
name|get
argument_list|(
name|command
argument_list|)
decl_stmt|;
if|if
condition|(
name|result
operator|!=
literal|null
condition|)
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
continue|continue;
block|}
comment|// Then look for italics etc.,
comment|// but first check if we are already at the end of the string.
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
block|{
break|break
name|testContent
break|;
block|}
if|if
condition|(
operator|(
name|c
operator|==
literal|'{'
operator|||
name|c
operator|==
literal|' '
operator|)
operator|&&
name|currentCommand
operator|.
name|length
argument_list|()
operator|>
literal|0
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
switch|switch
condition|(
name|command
condition|)
block|{
case|case
literal|"em"
case|:
case|case
literal|"emph"
case|:
case|case
literal|"textit"
case|:
block|{
name|IntAndString
name|part
init|=
name|getPart
argument_list|(
name|field
argument_list|,
name|i
argument_list|,
name|c
operator|==
literal|'{'
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
literal|"{\\i "
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
literal|'}'
argument_list|)
expr_stmt|;
break|break;
block|}
case|case
literal|"textbf"
case|:
block|{
name|IntAndString
name|part
init|=
name|getPart
argument_list|(
name|field
argument_list|,
name|i
argument_list|,
name|c
operator|==
literal|'{'
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
literal|"{\\b "
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
literal|'}'
argument_list|)
expr_stmt|;
break|break;
block|}
default|default:
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Unknown command "
operator|+
name|command
argument_list|)
expr_stmt|;
break|break;
block|}
if|if
condition|(
name|c
operator|==
literal|' '
condition|)
block|{
comment|// command was separated with the content by ' '
comment|// We have to add the space a
block|}
block|}
else|else
block|{
name|sb
operator|.
name|append
argument_list|(
name|c
argument_list|)
expr_stmt|;
block|}
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
name|char
name|c
range|:
name|chars
control|)
block|{
if|if
condition|(
name|c
operator|<
literal|128
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
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
operator|.
name|replaceAll
argument_list|(
literal|"---"
argument_list|,
literal|"{\\\\emdash}"
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"--"
argument_list|,
literal|"{\\\\endash}"
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"``"
argument_list|,
literal|"{\\\\ldblquote}"
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"''"
argument_list|,
literal|"{\\\\rdblquote}"
argument_list|)
return|;
block|}
comment|/**      * @param text the text to extract the part from      * @param i the position to start      * @param commandNestedInBraces true if the command is nested in braces (\emph{xy}), false if spaces are sued (\emph xy)       * @return a tuple of number of added characters and the extracted part      */
DECL|method|getPart (String text, int i, boolean commandNestedInBraces)
specifier|private
name|IntAndString
name|getPart
parameter_list|(
name|String
name|text
parameter_list|,
name|int
name|i
parameter_list|,
name|boolean
name|commandNestedInBraces
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
name|loop
label|:
while|while
condition|(
name|count
operator|>=
literal|0
operator|&&
name|i
operator|<
name|text
operator|.
name|length
argument_list|()
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
switch|switch
condition|(
name|c
condition|)
block|{
case|case
literal|'}'
case|:
name|count
operator|--
expr_stmt|;
break|break;
case|case
literal|'{'
case|:
name|count
operator|++
expr_stmt|;
break|break;
case|case
literal|' '
case|:
if|if
condition|(
name|commandNestedInBraces
condition|)
block|{
comment|// in any case, a space terminates the loop
break|break
name|loop
break|;
block|}
break|break;
block|}
name|part
operator|.
name|append
argument_list|(
name|c
argument_list|)
expr_stmt|;
block|}
name|String
name|res
init|=
name|part
operator|.
name|toString
argument_list|()
decl_stmt|;
comment|// the wrong "}" at the end is removed by "format(res)"
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
name|res
argument_list|)
argument_list|)
return|;
block|}
DECL|class|IntAndString
specifier|private
specifier|static
class|class
name|IntAndString
block|{
DECL|field|i
specifier|public
specifier|final
name|int
name|i
decl_stmt|;
DECL|field|s
specifier|final
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

