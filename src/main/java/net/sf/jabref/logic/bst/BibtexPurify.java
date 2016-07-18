begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_comment
comment|// $Id$
end_comment

begin_package
DECL|package|net.sf.jabref.logic.bst
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|bst
package|;
end_package

begin_comment
comment|/**  *  * The |built_in| function {\.{purify\$}} pops the top (string) literal, removes  * nonalphanumeric characters except for |white_space| and |sep_char| characters  * (these get converted to a |space|) and removes certain alphabetic characters  * contained in the control sequences associated with a special character, and  * pushes the resulting string. If the literal isn't a string, it complains and  * pushes the null string.  *  */
end_comment

begin_class
DECL|class|BibtexPurify
specifier|public
class|class
name|BibtexPurify
block|{
comment|/**      *      * @param toPurify      * @param warn      *            may-be-null      * @return      */
DECL|method|purify (String toPurify, Warn warn)
specifier|public
specifier|static
name|String
name|purify
parameter_list|(
name|String
name|toPurify
parameter_list|,
name|Warn
name|warn
parameter_list|)
block|{
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|char
index|[]
name|cs
init|=
name|toPurify
operator|.
name|toCharArray
argument_list|()
decl_stmt|;
name|int
name|n
init|=
name|cs
operator|.
name|length
decl_stmt|;
name|int
name|i
init|=
literal|0
decl_stmt|;
name|int
name|braceLevel
init|=
literal|0
decl_stmt|;
while|while
condition|(
name|i
operator|<
name|n
condition|)
block|{
name|char
name|c
init|=
name|cs
index|[
name|i
index|]
decl_stmt|;
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
literal|'-'
operator|)
operator|||
operator|(
name|c
operator|==
literal|'~'
operator|)
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|' '
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|Character
operator|.
name|isLetterOrDigit
argument_list|(
name|c
argument_list|)
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
name|c
operator|==
literal|'{'
condition|)
block|{
name|braceLevel
operator|++
expr_stmt|;
if|if
condition|(
operator|(
name|braceLevel
operator|==
literal|1
operator|)
operator|&&
operator|(
operator|(
name|i
operator|+
literal|1
operator|)
operator|<
name|n
operator|)
operator|&&
operator|(
name|cs
index|[
name|i
operator|+
literal|1
index|]
operator|==
literal|'\\'
operator|)
condition|)
block|{
name|i
operator|++
expr_stmt|;
comment|// skip brace
while|while
condition|(
operator|(
name|i
operator|<
name|n
operator|)
operator|&&
operator|(
name|braceLevel
operator|>
literal|0
operator|)
condition|)
block|{
name|i
operator|++
expr_stmt|;
comment|// skip backslash
name|BibtexCaseChanger
operator|.
name|findSpecialChar
argument_list|(
name|cs
argument_list|,
name|i
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|sb
operator|::
name|append
argument_list|)
expr_stmt|;
while|while
condition|(
operator|(
name|i
operator|<
name|n
operator|)
operator|&&
name|Character
operator|.
name|isLetter
argument_list|(
name|cs
index|[
name|i
index|]
argument_list|)
condition|)
block|{
name|i
operator|++
expr_stmt|;
block|}
while|while
condition|(
operator|(
name|i
operator|<
name|n
operator|)
operator|&&
operator|(
name|braceLevel
operator|>
literal|0
operator|)
operator|&&
operator|(
operator|(
name|c
operator|=
name|cs
index|[
name|i
index|]
operator|)
operator|!=
literal|'\\'
operator|)
condition|)
block|{
if|if
condition|(
name|Character
operator|.
name|isLetterOrDigit
argument_list|(
name|c
argument_list|)
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
name|c
operator|==
literal|'}'
condition|)
block|{
name|braceLevel
operator|--
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
name|braceLevel
operator|++
expr_stmt|;
block|}
name|i
operator|++
expr_stmt|;
block|}
block|}
continue|continue;
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
if|if
condition|(
name|braceLevel
operator|>
literal|0
condition|)
block|{
name|braceLevel
operator|--
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|warn
operator|!=
literal|null
condition|)
block|{
name|warn
operator|.
name|warn
argument_list|(
literal|"Unbalanced brace in string for purify$: "
operator|+
name|toPurify
argument_list|)
expr_stmt|;
block|}
block|}
block|}
name|i
operator|++
expr_stmt|;
block|}
if|if
condition|(
operator|(
name|braceLevel
operator|!=
literal|0
operator|)
operator|&&
operator|(
name|warn
operator|!=
literal|null
operator|)
condition|)
block|{
name|warn
operator|.
name|warn
argument_list|(
literal|"Unbalanced brace in string for purify$: "
operator|+
name|toPurify
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
block|}
end_class

end_unit
