begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.bst
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|bst
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Arrays
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|stream
operator|.
name|Collectors
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
name|model
operator|.
name|entry
operator|.
name|AuthorList
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
name|model
operator|.
name|entry
operator|.
name|AuthorList
operator|.
name|Author
import|;
end_import

begin_comment
comment|/**  * From Bibtex:  *  * "The |built_in| function {\.{format.name\$}} pops the  * top three literals (they are a string, an integer, and a string  * literal, in that order). The last string literal represents a  * name list (each name corresponding to a person), the integer  * literal specifies which name to pick from this list, and the  * first string literal specifies how to format this name, as  * described in the \BibTeX\ documentation. Finally, this function  * pushes the formatted name. If any of the types is incorrect, it  * complains and pushes the null string."  *  * Sounds easy - is a nightmare... X-(  *  */
end_comment

begin_class
DECL|class|BibtexNameFormatter
specifier|public
class|class
name|BibtexNameFormatter
block|{
DECL|method|formatName (String authorsNameList, int whichName, String formatString, Warn warn)
specifier|public
specifier|static
name|String
name|formatName
parameter_list|(
name|String
name|authorsNameList
parameter_list|,
name|int
name|whichName
parameter_list|,
name|String
name|formatString
parameter_list|,
name|Warn
name|warn
parameter_list|)
block|{
name|AuthorList
name|al
init|=
name|AuthorList
operator|.
name|getAuthorList
argument_list|(
name|authorsNameList
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|whichName
operator|<
literal|1
operator|)
operator|&&
operator|(
name|whichName
operator|>
name|al
operator|.
name|size
argument_list|()
operator|)
condition|)
block|{
name|warn
operator|.
name|warn
argument_list|(
literal|"AuthorList "
operator|+
name|authorsNameList
operator|+
literal|" does not contain an author with number "
operator|+
name|whichName
argument_list|)
expr_stmt|;
return|return
literal|""
return|;
block|}
return|return
name|BibtexNameFormatter
operator|.
name|formatName
argument_list|(
name|al
operator|.
name|getAuthor
argument_list|(
name|whichName
operator|-
literal|1
argument_list|)
argument_list|,
name|formatString
argument_list|,
name|warn
argument_list|)
return|;
block|}
comment|/**      *      * @param author      * @param format      * @param warn may-be-null      * @return      */
DECL|method|formatName (Author author, String format, Warn warn)
specifier|public
specifier|static
name|String
name|formatName
parameter_list|(
name|Author
name|author
parameter_list|,
name|String
name|format
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
name|c
init|=
name|format
operator|.
name|toCharArray
argument_list|()
decl_stmt|;
name|int
name|n
init|=
name|c
operator|.
name|length
decl_stmt|;
name|int
name|braceLevel
init|=
literal|0
decl_stmt|;
name|int
name|group
init|=
literal|0
decl_stmt|;
name|int
name|i
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
if|if
condition|(
name|c
index|[
name|i
index|]
operator|==
literal|'{'
condition|)
block|{
name|group
operator|++
expr_stmt|;
name|i
operator|++
expr_stmt|;
name|braceLevel
operator|++
expr_stmt|;
name|StringBuilder
name|level1Chars
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|StringBuilder
name|wholeChar
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
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
name|wholeChar
operator|.
name|append
argument_list|(
name|c
index|[
name|i
index|]
argument_list|)
expr_stmt|;
if|if
condition|(
name|c
index|[
name|i
index|]
operator|==
literal|'{'
condition|)
block|{
name|braceLevel
operator|++
expr_stmt|;
name|i
operator|++
expr_stmt|;
continue|continue;
block|}
if|if
condition|(
name|c
index|[
name|i
index|]
operator|==
literal|'}'
condition|)
block|{
name|braceLevel
operator|--
expr_stmt|;
name|i
operator|++
expr_stmt|;
continue|continue;
block|}
if|if
condition|(
operator|(
name|braceLevel
operator|==
literal|1
operator|)
operator|&&
name|Character
operator|.
name|isLetter
argument_list|(
name|c
index|[
name|i
index|]
argument_list|)
condition|)
block|{
if|if
condition|(
literal|"fvlj"
operator|.
name|indexOf
argument_list|(
name|c
index|[
name|i
index|]
argument_list|)
operator|==
operator|-
literal|1
condition|)
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
literal|"Format String in format.name$ may only contain fvlj on brace level 1 in group "
operator|+
name|group
operator|+
literal|": "
operator|+
name|format
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|level1Chars
operator|.
name|append
argument_list|(
name|c
index|[
name|i
index|]
argument_list|)
expr_stmt|;
block|}
block|}
name|i
operator|++
expr_stmt|;
block|}
name|i
operator|--
expr_stmt|;
comment|// unskip last brace (for last i++ at the end)
name|String
name|control
init|=
name|level1Chars
operator|.
name|toString
argument_list|()
operator|.
name|toLowerCase
argument_list|()
decl_stmt|;
if|if
condition|(
name|control
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
continue|continue;
block|}
if|if
condition|(
operator|(
name|control
operator|.
name|length
argument_list|()
operator|>
literal|2
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
literal|"Format String in format.name$ may only be one or two character long on brace level 1 in group "
operator|+
name|group
operator|+
literal|": "
operator|+
name|format
argument_list|)
expr_stmt|;
block|}
name|char
name|type
init|=
name|control
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|String
name|tokenS
decl_stmt|;
switch|switch
condition|(
name|type
condition|)
block|{
case|case
literal|'f'
case|:
name|tokenS
operator|=
name|author
operator|.
name|getFirst
argument_list|()
expr_stmt|;
break|break;
case|case
literal|'v'
case|:
name|tokenS
operator|=
name|author
operator|.
name|getVon
argument_list|()
expr_stmt|;
break|break;
case|case
literal|'l'
case|:
name|tokenS
operator|=
name|author
operator|.
name|getLast
argument_list|()
expr_stmt|;
break|break;
case|case
literal|'j'
case|:
name|tokenS
operator|=
name|author
operator|.
name|getJr
argument_list|()
expr_stmt|;
break|break;
default|default:
throw|throw
operator|new
name|VMException
argument_list|(
literal|"Internal error"
argument_list|)
throw|;
block|}
if|if
condition|(
name|tokenS
operator|==
literal|null
condition|)
block|{
name|i
operator|++
expr_stmt|;
continue|continue;
block|}
name|String
index|[]
name|tokens
init|=
name|tokenS
operator|.
name|split
argument_list|(
literal|" "
argument_list|)
decl_stmt|;
name|boolean
name|abbreviateThatIsSingleLetter
init|=
literal|true
decl_stmt|;
if|if
condition|(
name|control
operator|.
name|length
argument_list|()
operator|==
literal|2
condition|)
block|{
if|if
condition|(
name|control
operator|.
name|charAt
argument_list|(
literal|1
argument_list|)
operator|==
name|control
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
condition|)
block|{
name|abbreviateThatIsSingleLetter
operator|=
literal|false
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
literal|"Format String in format.name$ may only contain one type of vlfj on brace level 1 in group "
operator|+
name|group
operator|+
literal|": "
operator|+
name|format
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|// Now we know what to do
if|if
condition|(
operator|(
name|braceLevel
operator|==
literal|0
operator|)
operator|&&
operator|(
name|wholeChar
operator|.
name|charAt
argument_list|(
name|wholeChar
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
operator|==
literal|'}'
operator|)
condition|)
block|{
name|wholeChar
operator|.
name|deleteCharAt
argument_list|(
name|wholeChar
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
expr_stmt|;
block|}
name|char
index|[]
name|d
init|=
name|wholeChar
operator|.
name|toString
argument_list|()
operator|.
name|toCharArray
argument_list|()
decl_stmt|;
name|int
name|bLevel
init|=
literal|1
decl_stmt|;
name|String
name|interToken
init|=
literal|null
decl_stmt|;
name|int
name|groupStart
init|=
name|sb
operator|.
name|length
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|j
init|=
literal|0
init|;
name|j
operator|<
name|d
operator|.
name|length
condition|;
name|j
operator|++
control|)
block|{
if|if
condition|(
name|Character
operator|.
name|isLetter
argument_list|(
name|d
index|[
name|j
index|]
argument_list|)
operator|&&
operator|(
name|bLevel
operator|==
literal|1
operator|)
condition|)
block|{
name|groupStart
operator|=
name|sb
operator|.
name|length
argument_list|()
expr_stmt|;
if|if
condition|(
operator|!
name|abbreviateThatIsSingleLetter
condition|)
block|{
name|j
operator|++
expr_stmt|;
block|}
if|if
condition|(
operator|(
operator|(
name|j
operator|+
literal|1
operator|)
operator|<
name|d
operator|.
name|length
operator|)
operator|&&
operator|(
name|d
index|[
name|j
operator|+
literal|1
index|]
operator|==
literal|'{'
operator|)
condition|)
block|{
name|StringBuilder
name|interTokenSb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|j
operator|=
name|BibtexNameFormatter
operator|.
name|consumeToMatchingBrace
argument_list|(
name|interTokenSb
argument_list|,
name|d
argument_list|,
name|j
operator|+
literal|1
argument_list|)
expr_stmt|;
name|interToken
operator|=
name|interTokenSb
operator|.
name|substring
argument_list|(
literal|1
argument_list|,
name|interTokenSb
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|int
name|k
init|=
literal|0
init|;
name|k
operator|<
name|tokens
operator|.
name|length
condition|;
name|k
operator|++
control|)
block|{
name|String
name|token
init|=
name|tokens
index|[
name|k
index|]
decl_stmt|;
if|if
condition|(
name|abbreviateThatIsSingleLetter
condition|)
block|{
name|String
index|[]
name|dashes
init|=
name|token
operator|.
name|split
argument_list|(
literal|"-"
argument_list|)
decl_stmt|;
name|token
operator|=
name|Arrays
operator|.
name|asList
argument_list|(
name|dashes
argument_list|)
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|BibtexNameFormatter
operator|::
name|getFirstCharOfString
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|joining
argument_list|(
literal|".-"
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|// Output token
name|sb
operator|.
name|append
argument_list|(
name|token
argument_list|)
expr_stmt|;
if|if
condition|(
name|k
operator|<
operator|(
name|tokens
operator|.
name|length
operator|-
literal|1
operator|)
condition|)
block|{
comment|// Output Intertoken String
if|if
condition|(
name|interToken
operator|==
literal|null
condition|)
block|{
if|if
condition|(
name|abbreviateThatIsSingleLetter
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|'.'
argument_list|)
expr_stmt|;
block|}
comment|// No clue what this means (What the hell are tokens anyway???
comment|// if (lex_class[name_sep_char[cur_token]] = sep_char) then
comment|//    append_ex_buf_char_and_check (name_sep_char[cur_token])
if|if
condition|(
operator|(
name|k
operator|==
operator|(
name|tokens
operator|.
name|length
operator|-
literal|2
operator|)
operator|)
operator|||
operator|(
name|BibtexNameFormatter
operator|.
name|numberOfChars
argument_list|(
name|sb
operator|.
name|substring
argument_list|(
name|groupStart
argument_list|,
name|sb
operator|.
name|length
argument_list|()
argument_list|)
argument_list|,
literal|3
argument_list|)
operator|<
literal|3
operator|)
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|'~'
argument_list|)
expr_stmt|;
block|}
else|else
block|{
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
name|sb
operator|.
name|append
argument_list|(
name|interToken
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
elseif|else
if|if
condition|(
name|d
index|[
name|j
index|]
operator|==
literal|'}'
condition|)
block|{
name|bLevel
operator|--
expr_stmt|;
if|if
condition|(
name|bLevel
operator|>
literal|0
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|'}'
argument_list|)
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|d
index|[
name|j
index|]
operator|==
literal|'{'
condition|)
block|{
name|bLevel
operator|++
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|'{'
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|sb
operator|.
name|append
argument_list|(
name|d
index|[
name|j
index|]
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|sb
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|boolean
name|noDisTie
init|=
literal|false
decl_stmt|;
if|if
condition|(
operator|(
name|sb
operator|.
name|charAt
argument_list|(
name|sb
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
operator|==
literal|'~'
operator|)
operator|&&
operator|(
operator|(
name|BibtexNameFormatter
operator|.
name|numberOfChars
argument_list|(
name|sb
operator|.
name|substring
argument_list|(
name|groupStart
argument_list|,
name|sb
operator|.
name|length
argument_list|()
argument_list|)
argument_list|,
literal|4
argument_list|)
operator|>=
literal|4
operator|)
operator|||
operator|(
operator|(
name|sb
operator|.
name|length
argument_list|()
operator|>
literal|1
operator|)
operator|&&
operator|(
name|noDisTie
operator|=
name|sb
operator|.
name|charAt
argument_list|(
name|sb
operator|.
name|length
argument_list|()
operator|-
literal|2
argument_list|)
operator|==
literal|'~'
operator|)
operator|)
operator|)
condition|)
block|{
name|sb
operator|.
name|deleteCharAt
argument_list|(
name|sb
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|noDisTie
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
block|}
block|}
block|}
elseif|else
if|if
condition|(
name|c
index|[
name|i
index|]
operator|==
literal|'}'
condition|)
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
literal|"Unmatched brace in format string: "
operator|+
name|format
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
name|c
index|[
name|i
index|]
argument_list|)
expr_stmt|;
comment|// verbatim
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
literal|"Unbalanced brace in format string for nameFormat: "
operator|+
name|format
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
comment|/**      * Including the matching brace.      *      * @param interTokenSb      * @param c      * @param pos      * @return      */
DECL|method|consumeToMatchingBrace (StringBuilder interTokenSb, char[] c, int pos)
specifier|public
specifier|static
name|int
name|consumeToMatchingBrace
parameter_list|(
name|StringBuilder
name|interTokenSb
parameter_list|,
name|char
index|[]
name|c
parameter_list|,
name|int
name|pos
parameter_list|)
block|{
name|int
name|braceLevel
init|=
literal|0
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
name|pos
init|;
name|i
operator|<
name|c
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|c
index|[
name|i
index|]
operator|==
literal|'}'
condition|)
block|{
name|braceLevel
operator|--
expr_stmt|;
if|if
condition|(
name|braceLevel
operator|==
literal|0
condition|)
block|{
name|interTokenSb
operator|.
name|append
argument_list|(
literal|'}'
argument_list|)
expr_stmt|;
return|return
name|i
return|;
block|}
block|}
elseif|else
if|if
condition|(
name|c
index|[
name|i
index|]
operator|==
literal|'{'
condition|)
block|{
name|braceLevel
operator|++
expr_stmt|;
block|}
name|interTokenSb
operator|.
name|append
argument_list|(
name|c
index|[
name|i
index|]
argument_list|)
expr_stmt|;
block|}
return|return
name|c
operator|.
name|length
return|;
block|}
comment|/**      * Takes care of special characters too      *      * @param s      * @return      */
DECL|method|getFirstCharOfString (String s)
specifier|public
specifier|static
name|String
name|getFirstCharOfString
parameter_list|(
name|String
name|s
parameter_list|)
block|{
name|char
index|[]
name|c
init|=
name|s
operator|.
name|toCharArray
argument_list|()
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
name|c
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|Character
operator|.
name|isLetter
argument_list|(
name|c
index|[
name|i
index|]
argument_list|)
condition|)
block|{
return|return
name|String
operator|.
name|valueOf
argument_list|(
name|c
index|[
name|i
index|]
argument_list|)
return|;
block|}
if|if
condition|(
operator|(
name|c
index|[
name|i
index|]
operator|==
literal|'{'
operator|)
operator|&&
operator|(
operator|(
name|i
operator|+
literal|1
operator|)
operator|<
name|c
operator|.
name|length
operator|)
operator|&&
operator|(
name|c
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
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|BibtexNameFormatter
operator|.
name|consumeToMatchingBrace
argument_list|(
name|sb
argument_list|,
name|c
argument_list|,
name|i
argument_list|)
expr_stmt|;
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
return|return
literal|""
return|;
block|}
DECL|method|numberOfChars (String token, int inStop)
specifier|public
specifier|static
name|int
name|numberOfChars
parameter_list|(
name|String
name|token
parameter_list|,
name|int
name|inStop
parameter_list|)
block|{
name|int
name|stop
init|=
name|inStop
decl_stmt|;
if|if
condition|(
name|stop
operator|<
literal|0
condition|)
block|{
name|stop
operator|=
name|Integer
operator|.
name|MAX_VALUE
expr_stmt|;
block|}
name|int
name|result
init|=
literal|0
decl_stmt|;
name|int
name|i
init|=
literal|0
decl_stmt|;
name|char
index|[]
name|c
init|=
name|token
operator|.
name|toCharArray
argument_list|()
decl_stmt|;
name|int
name|n
init|=
name|c
operator|.
name|length
decl_stmt|;
name|int
name|braceLevel
init|=
literal|0
decl_stmt|;
while|while
condition|(
operator|(
name|i
operator|<
name|n
operator|)
operator|&&
operator|(
name|result
operator|<
name|stop
operator|)
condition|)
block|{
name|i
operator|++
expr_stmt|;
if|if
condition|(
name|c
index|[
name|i
operator|-
literal|1
index|]
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
name|i
operator|<
name|n
operator|)
operator|&&
operator|(
name|c
index|[
name|i
index|]
operator|==
literal|'\\'
operator|)
condition|)
block|{
name|i
operator|++
expr_stmt|;
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
if|if
condition|(
name|c
index|[
name|i
index|]
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
index|[
name|i
index|]
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
block|}
elseif|else
if|if
condition|(
name|c
index|[
name|i
operator|-
literal|1
index|]
operator|==
literal|'}'
condition|)
block|{
name|braceLevel
operator|--
expr_stmt|;
block|}
name|result
operator|++
expr_stmt|;
block|}
return|return
name|result
return|;
block|}
block|}
end_class

end_unit

