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

begin_class
DECL|class|BibtexCaseChanger
specifier|public
class|class
name|BibtexCaseChanger
block|{
comment|// stores whether the char before the current char was a colon
DECL|field|prevColon
specifier|private
name|boolean
name|prevColon
init|=
literal|true
decl_stmt|;
comment|// global variable to store the current brace level
DECL|field|braceLevel
specifier|private
name|int
name|braceLevel
decl_stmt|;
DECL|enum|FORMAT_MODE
specifier|public
enum|enum
name|FORMAT_MODE
block|{
comment|// First character and character after a ":" as upper case - everything else in lower case. Obey {}.
DECL|enumConstant|TITLE_LOWERS
name|TITLE_LOWERS
argument_list|(
literal|'t'
argument_list|)
block|,
comment|// All characters lower case - Obey {}
DECL|enumConstant|ALL_LOWERS
name|ALL_LOWERS
argument_list|(
literal|'l'
argument_list|)
block|,
comment|// all characters upper case - Obey {}
DECL|enumConstant|ALL_UPPERS
name|ALL_UPPERS
argument_list|(
literal|'u'
argument_list|)
block|;
comment|// the following would have to be done if the functionality of CaseChangers would be included here
comment|// However, we decided against it and will probably do the other way round: https://github.com/JabRef/jabref/pull/215#issuecomment-146981624
comment|// Each word should start with a capital letter
comment|//EACH_FIRST_UPPERS('f'),
comment|// Converts all words to upper case, but converts articles, prepositions, and conjunctions to lower case
comment|// Capitalizes first and last word
comment|// Does not change words starting with "{"
comment|// DIFFERENCE to old CaseChangers.TITLE: last word is NOT capitalized in all cases
comment|//TITLE_UPPERS('T');
DECL|method|asChar ()
specifier|public
name|char
name|asChar
parameter_list|()
block|{
return|return
name|asChar
return|;
block|}
DECL|field|asChar
specifier|private
specifier|final
name|char
name|asChar
decl_stmt|;
DECL|method|FORMAT_MODE (char asChar)
specifier|private
name|FORMAT_MODE
parameter_list|(
name|char
name|asChar
parameter_list|)
block|{
name|this
operator|.
name|asChar
operator|=
name|asChar
expr_stmt|;
block|}
comment|/**          * Convert bstFormat char into ENUM          *          * @throws IllegalArgumentException if char is not 't', 'l', 'u'          */
DECL|method|getFormatModeForBSTFormat (final char bstFormat)
specifier|public
specifier|static
name|FORMAT_MODE
name|getFormatModeForBSTFormat
parameter_list|(
specifier|final
name|char
name|bstFormat
parameter_list|)
block|{
for|for
control|(
name|FORMAT_MODE
name|mode
range|:
name|FORMAT_MODE
operator|.
name|values
argument_list|()
control|)
block|{
if|if
condition|(
name|mode
operator|.
name|asChar
operator|==
name|bstFormat
condition|)
block|{
return|return
name|mode
return|;
block|}
block|}
throw|throw
operator|new
name|IllegalArgumentException
argument_list|()
throw|;
block|}
block|}
DECL|method|BibtexCaseChanger ()
specifier|private
name|BibtexCaseChanger
parameter_list|()
block|{     }
comment|/**      * Changes case of the given string s      *      * @param s the string to handle      * @param format the format      * @return      */
DECL|method|changeCase (String s, FORMAT_MODE format)
specifier|public
specifier|static
name|String
name|changeCase
parameter_list|(
name|String
name|s
parameter_list|,
name|FORMAT_MODE
name|format
parameter_list|)
block|{
return|return
operator|(
operator|new
name|BibtexCaseChanger
argument_list|()
operator|)
operator|.
name|doChangeCase
argument_list|(
name|s
argument_list|,
name|format
argument_list|)
return|;
block|}
DECL|method|doChangeCase (String s, FORMAT_MODE format)
specifier|private
name|String
name|doChangeCase
parameter_list|(
name|String
name|s
parameter_list|,
name|FORMAT_MODE
name|format
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
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|int
name|i
init|=
literal|0
decl_stmt|;
name|int
name|n
init|=
name|s
operator|.
name|length
argument_list|()
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
name|braceLevel
operator|++
expr_stmt|;
if|if
condition|(
operator|(
name|braceLevel
operator|!=
literal|1
operator|)
operator|||
operator|(
operator|(
name|i
operator|+
literal|4
operator|)
operator|>
name|n
operator|)
operator|||
operator|(
name|c
index|[
name|i
operator|+
literal|1
index|]
operator|!=
literal|'\\'
operator|)
condition|)
block|{
name|prevColon
operator|=
literal|false
expr_stmt|;
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
name|i
operator|++
expr_stmt|;
continue|continue;
block|}
if|if
condition|(
operator|(
name|format
operator|==
name|FORMAT_MODE
operator|.
name|TITLE_LOWERS
operator|)
operator|&&
operator|(
operator|(
name|i
operator|==
literal|0
operator|)
operator|||
operator|(
name|prevColon
operator|&&
name|Character
operator|.
name|isWhitespace
argument_list|(
name|c
index|[
name|i
operator|-
literal|1
index|]
argument_list|)
operator|)
operator|)
condition|)
block|{
assert|assert
operator|(
name|c
index|[
name|i
index|]
operator|==
literal|'{'
operator|)
assert|;
name|sb
operator|.
name|append
argument_list|(
literal|'{'
argument_list|)
expr_stmt|;
name|i
operator|++
expr_stmt|;
name|prevColon
operator|=
literal|false
expr_stmt|;
continue|continue;
block|}
name|i
operator|=
name|convertSpecialChar
argument_list|(
name|sb
argument_list|,
name|c
argument_list|,
name|i
argument_list|,
name|format
argument_list|)
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
name|i
operator|++
expr_stmt|;
name|braceLevel
operator|=
name|decrBraceLevel
argument_list|(
name|s
argument_list|,
name|braceLevel
argument_list|)
expr_stmt|;
name|prevColon
operator|=
literal|false
expr_stmt|;
continue|continue;
block|}
if|if
condition|(
name|braceLevel
operator|==
literal|0
condition|)
block|{
name|i
operator|=
name|convertCharIfBraceLevelIsZero
argument_list|(
name|c
argument_list|,
name|i
argument_list|,
name|sb
argument_list|,
name|format
argument_list|)
expr_stmt|;
continue|continue;
block|}
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
name|i
operator|++
expr_stmt|;
block|}
name|BibtexCaseChanger
operator|.
name|checkBrace
argument_list|(
name|s
argument_list|,
name|braceLevel
argument_list|)
expr_stmt|;
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
DECL|method|decrBraceLevel (String string, int braceLevel)
specifier|private
name|int
name|decrBraceLevel
parameter_list|(
name|String
name|string
parameter_list|,
name|int
name|braceLevel
parameter_list|)
block|{
if|if
condition|(
name|braceLevel
operator|==
literal|0
condition|)
block|{
name|BibtexCaseChanger
operator|.
name|complain
argument_list|(
name|string
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|braceLevel
operator|--
expr_stmt|;
block|}
return|return
name|braceLevel
return|;
block|}
DECL|method|complain (String s)
specifier|static
name|void
name|complain
parameter_list|(
name|String
name|s
parameter_list|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Warning -- String is not brace-balanced: "
operator|+
name|s
argument_list|)
expr_stmt|;
block|}
DECL|method|checkBrace (String s, int braceLevel)
specifier|static
name|void
name|checkBrace
parameter_list|(
name|String
name|s
parameter_list|,
name|int
name|braceLevel
parameter_list|)
block|{
if|if
condition|(
name|braceLevel
operator|>
literal|0
condition|)
block|{
name|BibtexCaseChanger
operator|.
name|complain
argument_list|(
name|s
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * We're dealing with a special character (usually either an undotted `\i'      * or `\j', or an accent like one in Table~3.1 of the \LaTeX\ manual, or a      * foreign character like one in Table~3.2) if the first character after the      * |left_brace| is a |backslash|; the special character ends with the      * matching |right_brace|. How we handle what's in between depends on the      * special character. In general, this code will do reasonably well if there      * is other stuff, too, between braces, but it doesn't try to do anything      * special with |colon|s.      *       * @param c      * @param i the current position. It points to the opening brace      * @param format      * @return      */
DECL|method|convertSpecialChar (StringBuffer sb, char[] c, int i, FORMAT_MODE format)
specifier|private
name|int
name|convertSpecialChar
parameter_list|(
name|StringBuffer
name|sb
parameter_list|,
name|char
index|[]
name|c
parameter_list|,
name|int
name|i
parameter_list|,
name|FORMAT_MODE
name|format
parameter_list|)
block|{
assert|assert
operator|(
name|c
index|[
name|i
index|]
operator|==
literal|'{'
operator|)
assert|;
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
name|i
operator|++
expr_stmt|;
comment|// skip over open brace
while|while
condition|(
operator|(
name|i
operator|<
name|c
operator|.
name|length
operator|)
operator|&&
operator|(
name|braceLevel
operator|>
literal|0
operator|)
condition|)
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
name|i
operator|++
expr_stmt|;
comment|// skip over the |backslash|
name|String
name|s
init|=
name|BibtexCaseChanger
operator|.
name|findSpecialChar
argument_list|(
name|c
argument_list|,
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
name|s
operator|!=
literal|null
condition|)
block|{
name|i
operator|=
name|convertAccented
argument_list|(
name|c
argument_list|,
name|i
argument_list|,
name|s
argument_list|,
name|sb
argument_list|,
name|format
argument_list|)
expr_stmt|;
block|}
while|while
condition|(
operator|(
name|i
operator|<
name|c
operator|.
name|length
operator|)
operator|&&
operator|(
name|braceLevel
operator|>
literal|0
operator|)
operator|&&
operator|(
name|c
index|[
name|i
index|]
operator|!=
literal|'\\'
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
operator|=
name|convertNonControl
argument_list|(
name|c
argument_list|,
name|i
argument_list|,
name|sb
argument_list|,
name|format
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|i
return|;
block|}
comment|/**      * Convert the given string according to the format character (title, lower,      * up) and append the result to the stringBuffer, return the updated      * position.      *       * @param c      * @param pos      * @param s      * @param sb      * @param format      * @return the new position      */
DECL|method|convertAccented (char[] c, int pos, String s, StringBuffer sb, FORMAT_MODE format)
specifier|private
name|int
name|convertAccented
parameter_list|(
name|char
index|[]
name|c
parameter_list|,
name|int
name|pos
parameter_list|,
name|String
name|s
parameter_list|,
name|StringBuffer
name|sb
parameter_list|,
name|FORMAT_MODE
name|format
parameter_list|)
block|{
name|pos
operator|+=
name|s
operator|.
name|length
argument_list|()
expr_stmt|;
switch|switch
condition|(
name|format
condition|)
block|{
case|case
name|TITLE_LOWERS
case|:
case|case
name|ALL_LOWERS
case|:
if|if
condition|(
literal|"L O OE AE AA"
operator|.
name|contains
argument_list|(
name|s
argument_list|)
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|s
operator|.
name|toLowerCase
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|sb
operator|.
name|append
argument_list|(
name|s
argument_list|)
expr_stmt|;
block|}
break|break;
case|case
name|ALL_UPPERS
case|:
if|if
condition|(
literal|"l o oe ae aa"
operator|.
name|contains
argument_list|(
name|s
argument_list|)
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|s
operator|.
name|toUpperCase
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"i j ss"
operator|.
name|contains
argument_list|(
name|s
argument_list|)
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
comment|// Kill backslash
name|sb
operator|.
name|append
argument_list|(
name|s
operator|.
name|toUpperCase
argument_list|()
argument_list|)
expr_stmt|;
while|while
condition|(
operator|(
name|pos
operator|<
name|c
operator|.
name|length
operator|)
operator|&&
name|Character
operator|.
name|isWhitespace
argument_list|(
name|c
index|[
name|pos
index|]
argument_list|)
condition|)
block|{
name|pos
operator|++
expr_stmt|;
block|}
block|}
else|else
block|{
name|sb
operator|.
name|append
argument_list|(
name|s
argument_list|)
expr_stmt|;
block|}
break|break;
block|}
return|return
name|pos
return|;
block|}
DECL|method|convertNonControl (char[] c, int pos, StringBuffer sb, FORMAT_MODE format)
specifier|private
name|int
name|convertNonControl
parameter_list|(
name|char
index|[]
name|c
parameter_list|,
name|int
name|pos
parameter_list|,
name|StringBuffer
name|sb
parameter_list|,
name|FORMAT_MODE
name|format
parameter_list|)
block|{
switch|switch
condition|(
name|format
condition|)
block|{
case|case
name|TITLE_LOWERS
case|:
case|case
name|ALL_LOWERS
case|:
name|sb
operator|.
name|append
argument_list|(
name|Character
operator|.
name|toLowerCase
argument_list|(
name|c
index|[
name|pos
index|]
argument_list|)
argument_list|)
expr_stmt|;
name|pos
operator|++
expr_stmt|;
break|break;
case|case
name|ALL_UPPERS
case|:
name|sb
operator|.
name|append
argument_list|(
name|Character
operator|.
name|toUpperCase
argument_list|(
name|c
index|[
name|pos
index|]
argument_list|)
argument_list|)
expr_stmt|;
name|pos
operator|++
expr_stmt|;
break|break;
block|}
return|return
name|pos
return|;
block|}
DECL|method|convertCharIfBraceLevelIsZero (char[] c, int i, StringBuffer sb, FORMAT_MODE format)
specifier|private
name|int
name|convertCharIfBraceLevelIsZero
parameter_list|(
name|char
index|[]
name|c
parameter_list|,
name|int
name|i
parameter_list|,
name|StringBuffer
name|sb
parameter_list|,
name|FORMAT_MODE
name|format
parameter_list|)
block|{
switch|switch
condition|(
name|format
condition|)
block|{
case|case
name|TITLE_LOWERS
case|:
if|if
condition|(
name|i
operator|==
literal|0
condition|)
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
block|}
elseif|else
if|if
condition|(
name|prevColon
operator|&&
name|Character
operator|.
name|isWhitespace
argument_list|(
name|c
index|[
name|i
operator|-
literal|1
index|]
argument_list|)
condition|)
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
block|}
else|else
block|{
name|sb
operator|.
name|append
argument_list|(
name|Character
operator|.
name|toLowerCase
argument_list|(
name|c
index|[
name|i
index|]
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|c
index|[
name|i
index|]
operator|==
literal|':'
condition|)
block|{
name|prevColon
operator|=
literal|true
expr_stmt|;
block|}
elseif|else
if|if
condition|(
operator|!
name|Character
operator|.
name|isWhitespace
argument_list|(
name|c
index|[
name|i
index|]
argument_list|)
condition|)
block|{
name|prevColon
operator|=
literal|false
expr_stmt|;
block|}
break|break;
case|case
name|ALL_LOWERS
case|:
name|sb
operator|.
name|append
argument_list|(
name|Character
operator|.
name|toLowerCase
argument_list|(
name|c
index|[
name|i
index|]
argument_list|)
argument_list|)
expr_stmt|;
break|break;
case|case
name|ALL_UPPERS
case|:
name|sb
operator|.
name|append
argument_list|(
name|Character
operator|.
name|toUpperCase
argument_list|(
name|c
index|[
name|i
index|]
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|i
operator|++
expr_stmt|;
return|return
name|i
return|;
block|}
comment|/**      * Determine whether there starts a special char at pos (e.g., oe, AE). Return it as string.      * If nothing found, return null      *      * Also used by BibtexPurify      *      * @param c the current "String"      * @param pos the position      * @return the special LaTeX character or null      */
DECL|method|findSpecialChar (char[] c, int pos)
specifier|static
name|String
name|findSpecialChar
parameter_list|(
name|char
index|[]
name|c
parameter_list|,
name|int
name|pos
parameter_list|)
block|{
if|if
condition|(
operator|(
name|pos
operator|+
literal|1
operator|)
operator|<
name|c
operator|.
name|length
condition|)
block|{
if|if
condition|(
operator|(
name|c
index|[
name|pos
index|]
operator|==
literal|'o'
operator|)
operator|&&
operator|(
name|c
index|[
name|pos
operator|+
literal|1
index|]
operator|==
literal|'e'
operator|)
condition|)
block|{
return|return
literal|"oe"
return|;
block|}
if|if
condition|(
operator|(
name|c
index|[
name|pos
index|]
operator|==
literal|'O'
operator|)
operator|&&
operator|(
name|c
index|[
name|pos
operator|+
literal|1
index|]
operator|==
literal|'E'
operator|)
condition|)
block|{
return|return
literal|"OE"
return|;
block|}
if|if
condition|(
operator|(
name|c
index|[
name|pos
index|]
operator|==
literal|'a'
operator|)
operator|&&
operator|(
name|c
index|[
name|pos
operator|+
literal|1
index|]
operator|==
literal|'e'
operator|)
condition|)
block|{
return|return
literal|"ae"
return|;
block|}
if|if
condition|(
operator|(
name|c
index|[
name|pos
index|]
operator|==
literal|'A'
operator|)
operator|&&
operator|(
name|c
index|[
name|pos
operator|+
literal|1
index|]
operator|==
literal|'E'
operator|)
condition|)
block|{
return|return
literal|"AE"
return|;
block|}
if|if
condition|(
operator|(
name|c
index|[
name|pos
index|]
operator|==
literal|'s'
operator|)
operator|&&
operator|(
name|c
index|[
name|pos
operator|+
literal|1
index|]
operator|==
literal|'s'
operator|)
condition|)
block|{
return|return
literal|"ss"
return|;
block|}
if|if
condition|(
operator|(
name|c
index|[
name|pos
index|]
operator|==
literal|'A'
operator|)
operator|&&
operator|(
name|c
index|[
name|pos
operator|+
literal|1
index|]
operator|==
literal|'A'
operator|)
condition|)
block|{
return|return
literal|"AA"
return|;
block|}
if|if
condition|(
operator|(
name|c
index|[
name|pos
index|]
operator|==
literal|'a'
operator|)
operator|&&
operator|(
name|c
index|[
name|pos
operator|+
literal|1
index|]
operator|==
literal|'a'
operator|)
condition|)
block|{
return|return
literal|"aa"
return|;
block|}
block|}
if|if
condition|(
name|c
index|[
name|pos
index|]
operator|==
literal|'i'
condition|)
block|{
return|return
name|String
operator|.
name|valueOf
argument_list|(
name|c
index|[
name|pos
index|]
argument_list|)
return|;
block|}
if|if
condition|(
name|c
index|[
name|pos
index|]
operator|==
literal|'j'
condition|)
block|{
return|return
name|String
operator|.
name|valueOf
argument_list|(
name|c
index|[
name|pos
index|]
argument_list|)
return|;
block|}
if|if
condition|(
name|c
index|[
name|pos
index|]
operator|==
literal|'o'
condition|)
block|{
return|return
name|String
operator|.
name|valueOf
argument_list|(
name|c
index|[
name|pos
index|]
argument_list|)
return|;
block|}
if|if
condition|(
name|c
index|[
name|pos
index|]
operator|==
literal|'O'
condition|)
block|{
return|return
name|String
operator|.
name|valueOf
argument_list|(
name|c
index|[
name|pos
index|]
argument_list|)
return|;
block|}
if|if
condition|(
name|c
index|[
name|pos
index|]
operator|==
literal|'l'
condition|)
block|{
return|return
name|String
operator|.
name|valueOf
argument_list|(
name|c
index|[
name|pos
index|]
argument_list|)
return|;
block|}
if|if
condition|(
name|c
index|[
name|pos
index|]
operator|==
literal|'L'
condition|)
block|{
return|return
name|String
operator|.
name|valueOf
argument_list|(
name|c
index|[
name|pos
index|]
argument_list|)
return|;
block|}
return|return
literal|null
return|;
block|}
block|}
end_class

end_unit

