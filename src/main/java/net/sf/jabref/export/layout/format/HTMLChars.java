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
name|StringUtil
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

begin_comment
comment|/**  * This formatter escapes characters so they are suitable for HTML.  *   * @version $Revision$ ($Date$)  */
end_comment

begin_class
DECL|class|HTMLChars
specifier|public
class|class
name|HTMLChars
implements|implements
name|LayoutFormatter
block|{
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
name|int
name|i
decl_stmt|;
name|field
operator|=
name|field
operator|.
name|replaceAll
argument_list|(
literal|"&|\\\\&"
argument_list|,
literal|"&amp;"
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"[\\n]{2,}"
argument_list|,
literal|"<p>"
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"\\n"
argument_list|,
literal|"<br>"
argument_list|)
expr_stmt|;
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|StringBuffer
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
decl_stmt|,
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
name|Object
name|result
init|=
name|Globals
operator|.
name|HTMLCHARS
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
operator|(
name|String
operator|)
name|result
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|sb
operator|.
name|append
argument_list|(
name|command
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
operator|(
name|Globals
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
operator|(
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
operator|)
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
comment|// System.out.println("next: "+(char)c);
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
name|combody
operator|=
name|part
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
comment|// System.out.println("... "+combody);
block|}
name|Object
name|result
init|=
name|Globals
operator|.
name|HTMLCHARS
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
operator|(
name|String
operator|)
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
name|Object
name|result
init|=
name|Globals
operator|.
name|HTMLCHARS
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
operator|!=
literal|null
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
operator|(
name|String
operator|)
name|result
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|sb
operator|.
name|append
argument_list|(
name|command
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
block|}
else|else
block|{
name|String
name|argument
decl_stmt|;
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
comment|// Then test if we are dealing with a italics or bold
comment|// command.
comment|// If so, handle.
if|if
condition|(
name|command
operator|.
name|equals
argument_list|(
literal|"em"
argument_list|)
operator|||
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
literal|"<em>"
argument_list|)
operator|.
name|append
argument_list|(
name|part
argument_list|)
operator|.
name|append
argument_list|(
literal|"</em>"
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
literal|"<b>"
argument_list|)
operator|.
name|append
argument_list|(
name|part
argument_list|)
operator|.
name|append
argument_list|(
literal|"</b>"
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
name|argument
operator|=
name|part
expr_stmt|;
if|if
condition|(
name|argument
operator|!=
literal|null
condition|)
block|{
comment|// handle common case of general latex command
name|Object
name|result
init|=
name|Globals
operator|.
name|HTMLCHARS
operator|.
name|get
argument_list|(
name|command
operator|+
name|argument
argument_list|)
decl_stmt|;
comment|// System.out.print("command: "+command+", arg: "+argument);
comment|// System.out.print(", result: ");
comment|// If found, then use translated version. If not, then keep
comment|// the
comment|// text of the parameter intact.
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
operator|(
name|String
operator|)
name|result
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
name|Object
name|result
init|=
name|Globals
operator|.
name|HTMLCHARS
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
operator|(
name|String
operator|)
name|result
argument_list|)
expr_stmt|;
block|}
else|else
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
block|}
else|else
block|{
name|Object
name|result
init|=
name|Globals
operator|.
name|HTMLCHARS
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
operator|(
name|String
operator|)
name|result
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|sb
operator|.
name|append
argument_list|(
name|command
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
comment|/* else if (c == '}') {                     System.out.printf("com term by }: '%s'\n", currentCommand.toString());                      argument = "";                  }*/
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
return|;
block|}
block|}
end_class

end_unit

