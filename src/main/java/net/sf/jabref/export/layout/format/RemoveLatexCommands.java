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

begin_class
DECL|class|RemoveLatexCommands
specifier|public
class|class
name|RemoveLatexCommands
implements|implements
name|LayoutFormatter
block|{
DECL|field|i
name|int
name|i
decl_stmt|;
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
operator|(
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
name|sb
operator|.
name|append
argument_list|(
name|c
argument_list|)
expr_stmt|;
comment|// Else we are in a command, and should not keep the letter.
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
comment|// This indicates that we are in a command of the type \^o or \~{n}
comment|/*            if (i>= field.length()-1)                                       break testCharCom;                                      String command = currentCommand.toString();                                     i++;                                     c = field.charAt(i);                                     //System.out.println("next: "+(char)c);                                     String combody;                                     if (c == '{') {                                       IntAndString part = getPart(field, i);                                       i += part.i;                                       combody = part.s;                                     }                                     else {                                       combody = field.substring(i,i+1);                                       //System.out.println("... "+combody);                                     }                                     Object result = Globals.HTMLCHARS.get(command+combody);                                     if (result != null)                                       sb.append((String)result);                         */
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
elseif|else
if|if
condition|(
name|Character
operator|.
name|isLetter
argument_list|(
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
name|sb
operator|.
name|append
argument_list|(
name|c
argument_list|)
expr_stmt|;
comment|// Else we are in a command, and should not keep the letter.
else|else
name|currentCommand
operator|.
name|append
argument_list|(
name|c
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|//if (!incommand || ((c!='{')&& !Character.isWhitespace(c)))
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
name|c
argument_list|)
expr_stmt|;
else|else
block|{
if|if
condition|(
name|c
operator|!=
literal|'{'
condition|)
name|sb
operator|.
name|append
argument_list|(
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

