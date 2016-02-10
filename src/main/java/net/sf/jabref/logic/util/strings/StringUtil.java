begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
end_comment

begin_package
DECL|package|net.sf.jabref.logic.util.strings
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|util
operator|.
name|strings
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
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
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
name|Matcher
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

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|common
operator|.
name|base
operator|.
name|CharMatcher
import|;
end_import

begin_class
DECL|class|StringUtil
specifier|public
class|class
name|StringUtil
block|{
comment|// contains all possible line breaks, not omitting any break such as "\\n"
DECL|field|LINE_BREAKS
specifier|private
specifier|static
specifier|final
name|Pattern
name|LINE_BREAKS
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"\\r\\n|\\r|\\n"
argument_list|)
decl_stmt|;
DECL|field|BRACED_TITLE_CAPITAL_PATTERN
specifier|private
specifier|static
specifier|final
name|Pattern
name|BRACED_TITLE_CAPITAL_PATTERN
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"\\{[A-Z]+\\}"
argument_list|)
decl_stmt|;
comment|/**      * Returns the string, after shaving off whitespace at the beginning and end,      * and removing (at most) one pair of braces or " surrounding it.      *      * @param toShave      * @return      */
DECL|method|shaveString (String toShave)
specifier|public
specifier|static
name|String
name|shaveString
parameter_list|(
name|String
name|toShave
parameter_list|)
block|{
if|if
condition|(
operator|(
name|toShave
operator|==
literal|null
operator|)
operator|||
operator|(
name|toShave
operator|.
name|isEmpty
argument_list|()
operator|)
condition|)
block|{
return|return
literal|""
return|;
block|}
name|toShave
operator|=
name|toShave
operator|.
name|trim
argument_list|()
expr_stmt|;
if|if
condition|(
name|isInCurlyBrackets
argument_list|(
name|toShave
argument_list|)
operator|||
name|isInCitationMarks
argument_list|(
name|toShave
argument_list|)
condition|)
block|{
return|return
name|toShave
operator|.
name|substring
argument_list|(
literal|1
argument_list|,
name|toShave
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
return|;
block|}
return|return
name|toShave
return|;
block|}
comment|/**      * Concatenate all strings in the array from index 'from' to 'to' (excluding      * to) with the given separator.      *<p>      * Example:      *<p>      * String[] s = "ab/cd/ed".split("/"); join(s, "\\", 0, s.length) ->      * "ab\\cd\\ed"      *      * @param strings      * @param separator      * @param from      * @param to        Excluding strings[to]      * @return      */
DECL|method|join (String[] strings, String separator, int from, int to)
specifier|public
specifier|static
name|String
name|join
parameter_list|(
name|String
index|[]
name|strings
parameter_list|,
name|String
name|separator
parameter_list|,
name|int
name|from
parameter_list|,
name|int
name|to
parameter_list|)
block|{
if|if
condition|(
operator|(
name|strings
operator|.
name|length
operator|==
literal|0
operator|)
operator|||
operator|(
name|from
operator|>=
name|to
operator|)
condition|)
block|{
return|return
literal|""
return|;
block|}
name|from
operator|=
name|Math
operator|.
name|max
argument_list|(
name|from
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|to
operator|=
name|Math
operator|.
name|min
argument_list|(
name|strings
operator|.
name|length
argument_list|,
name|to
argument_list|)
expr_stmt|;
name|StringBuilder
name|stringBuilder
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
name|from
init|;
name|i
operator|<
operator|(
name|to
operator|-
literal|1
operator|)
condition|;
name|i
operator|++
control|)
block|{
name|stringBuilder
operator|.
name|append
argument_list|(
name|strings
index|[
name|i
index|]
argument_list|)
operator|.
name|append
argument_list|(
name|separator
argument_list|)
expr_stmt|;
block|}
return|return
name|stringBuilder
operator|.
name|append
argument_list|(
name|strings
index|[
name|to
operator|-
literal|1
index|]
argument_list|)
operator|.
name|toString
argument_list|()
return|;
block|}
DECL|method|join (Collection<String> strings, String separator)
specifier|public
specifier|static
name|String
name|join
parameter_list|(
name|Collection
argument_list|<
name|String
argument_list|>
name|strings
parameter_list|,
name|String
name|separator
parameter_list|)
block|{
name|String
index|[]
name|arr
init|=
name|strings
operator|.
name|toArray
argument_list|(
operator|new
name|String
index|[
name|strings
operator|.
name|size
argument_list|()
index|]
argument_list|)
decl_stmt|;
return|return
name|join
argument_list|(
name|arr
argument_list|,
name|separator
argument_list|,
literal|0
argument_list|,
name|arr
operator|.
name|length
argument_list|)
return|;
block|}
DECL|method|join (String[] strings, String separator)
specifier|public
specifier|static
name|String
name|join
parameter_list|(
name|String
index|[]
name|strings
parameter_list|,
name|String
name|separator
parameter_list|)
block|{
return|return
name|join
argument_list|(
name|strings
argument_list|,
name|separator
argument_list|,
literal|0
argument_list|,
name|strings
operator|.
name|length
argument_list|)
return|;
block|}
comment|/**      * Removes optional square brackets from the string s      *      * @param toStrip      * @return      */
DECL|method|stripBrackets (String toStrip)
specifier|public
specifier|static
name|String
name|stripBrackets
parameter_list|(
name|String
name|toStrip
parameter_list|)
block|{
if|if
condition|(
name|isInSquareBrackets
argument_list|(
name|toStrip
argument_list|)
condition|)
block|{
return|return
name|toStrip
operator|.
name|substring
argument_list|(
literal|1
argument_list|,
name|toStrip
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
return|;
block|}
return|return
name|toStrip
return|;
block|}
comment|/**      * extends the filename with a default Extension, if no Extension '.x' could      * be found      */
DECL|method|getCorrectFileName (String orgName, String defaultExtension)
specifier|public
specifier|static
name|String
name|getCorrectFileName
parameter_list|(
name|String
name|orgName
parameter_list|,
name|String
name|defaultExtension
parameter_list|)
block|{
if|if
condition|(
name|orgName
operator|==
literal|null
condition|)
block|{
return|return
literal|""
return|;
block|}
if|if
condition|(
name|orgName
operator|.
name|toLowerCase
argument_list|()
operator|.
name|endsWith
argument_list|(
literal|"."
operator|+
name|defaultExtension
operator|.
name|toLowerCase
argument_list|()
argument_list|)
condition|)
block|{
return|return
name|orgName
return|;
block|}
name|int
name|hiddenChar
init|=
name|orgName
operator|.
name|indexOf
argument_list|(
literal|'.'
argument_list|,
literal|1
argument_list|)
decl_stmt|;
comment|// hidden files Linux/Unix (?)
if|if
condition|(
name|hiddenChar
operator|<
literal|1
condition|)
block|{
name|orgName
operator|=
name|orgName
operator|+
literal|"."
operator|+
name|defaultExtension
expr_stmt|;
block|}
return|return
name|orgName
return|;
block|}
comment|/**      * Creates a substring from a text      *      * @param text      * @param index      * @param terminateOnEndBraceOnly      * @return      */
DECL|method|getPart (String text, int index, boolean terminateOnEndBraceOnly)
specifier|public
specifier|static
name|String
name|getPart
parameter_list|(
name|String
name|text
parameter_list|,
name|int
name|index
parameter_list|,
name|boolean
name|terminateOnEndBraceOnly
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
comment|// advance to first char and skip whitespace
name|index
operator|++
expr_stmt|;
while|while
condition|(
operator|(
name|index
operator|<
name|text
operator|.
name|length
argument_list|()
operator|)
operator|&&
name|Character
operator|.
name|isWhitespace
argument_list|(
name|text
operator|.
name|charAt
argument_list|(
name|index
argument_list|)
argument_list|)
condition|)
block|{
name|index
operator|++
expr_stmt|;
block|}
comment|// then grab whatever is the first token (counting braces)
while|while
condition|(
name|index
operator|<
name|text
operator|.
name|length
argument_list|()
condition|)
block|{
name|c
operator|=
name|text
operator|.
name|charAt
argument_list|(
name|index
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|terminateOnEndBraceOnly
operator|&&
operator|(
name|count
operator|==
literal|0
operator|)
operator|&&
name|Character
operator|.
name|isWhitespace
argument_list|(
name|c
argument_list|)
condition|)
block|{
comment|// end argument and leave whitespace for further processing
break|break;
block|}
if|if
condition|(
operator|(
name|c
operator|==
literal|'}'
operator|)
operator|&&
operator|(
operator|--
name|count
operator|<
literal|0
operator|)
condition|)
block|{
break|break;
block|}
elseif|else
if|if
condition|(
name|c
operator|==
literal|'{'
condition|)
block|{
name|count
operator|++
expr_stmt|;
block|}
name|part
operator|.
name|append
argument_list|(
name|c
argument_list|)
expr_stmt|;
name|index
operator|++
expr_stmt|;
block|}
return|return
name|part
operator|.
name|toString
argument_list|()
return|;
block|}
comment|/**      * Formats field contents for output. Must be "symmetric" with the parse method above,      * so stored and reloaded fields are not mangled.      *      * @param in      * @param wrapAmount      * @return the wrapped String.      */
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
name|StringBuilder
name|result
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
comment|// remove all whitespace at the end of the string, this especially includes \r created when the field content has \r\n as line separator
name|addWrappedLine
argument_list|(
name|result
argument_list|,
name|CharMatcher
operator|.
name|WHITESPACE
operator|.
name|trimTrailingFrom
argument_list|(
name|lines
index|[
literal|0
index|]
argument_list|)
argument_list|,
name|wrapAmount
argument_list|)
expr_stmt|;
comment|// See
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
name|lines
index|[
name|i
index|]
operator|.
name|trim
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|result
operator|.
name|append
argument_list|(
name|Globals
operator|.
name|NEWLINE
argument_list|)
expr_stmt|;
name|result
operator|.
name|append
argument_list|(
literal|'\t'
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|result
operator|.
name|append
argument_list|(
name|Globals
operator|.
name|NEWLINE
argument_list|)
expr_stmt|;
name|result
operator|.
name|append
argument_list|(
literal|'\t'
argument_list|)
expr_stmt|;
name|result
operator|.
name|append
argument_list|(
name|Globals
operator|.
name|NEWLINE
argument_list|)
expr_stmt|;
name|result
operator|.
name|append
argument_list|(
literal|'\t'
argument_list|)
expr_stmt|;
comment|// remove all whitespace at the end of the string, this especially includes \r created when the field content has \r\n as line separator
name|String
name|line
init|=
name|CharMatcher
operator|.
name|WHITESPACE
operator|.
name|trimTrailingFrom
argument_list|(
name|lines
index|[
name|i
index|]
argument_list|)
decl_stmt|;
name|addWrappedLine
argument_list|(
name|result
argument_list|,
name|line
argument_list|,
name|wrapAmount
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|result
operator|.
name|toString
argument_list|()
return|;
block|}
DECL|method|addWrappedLine (StringBuilder result, String line, int wrapAmount)
specifier|private
specifier|static
name|void
name|addWrappedLine
parameter_list|(
name|StringBuilder
name|result
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
name|length
init|=
name|result
operator|.
name|length
argument_list|()
decl_stmt|;
comment|// Add the line, unmodified:
name|result
operator|.
name|append
argument_list|(
name|line
argument_list|)
expr_stmt|;
while|while
condition|(
name|length
operator|<
name|result
operator|.
name|length
argument_list|()
condition|)
block|{
name|int
name|current
init|=
name|result
operator|.
name|indexOf
argument_list|(
literal|" "
argument_list|,
name|length
operator|+
name|wrapAmount
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|current
operator|<
literal|0
operator|)
operator|||
operator|(
name|current
operator|>=
name|result
operator|.
name|length
argument_list|()
operator|)
condition|)
block|{
break|break;
block|}
name|result
operator|.
name|deleteCharAt
argument_list|(
name|current
argument_list|)
expr_stmt|;
name|result
operator|.
name|insert
argument_list|(
name|current
argument_list|,
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"\t"
argument_list|)
expr_stmt|;
name|length
operator|=
name|current
operator|+
name|Globals
operator|.
name|NEWLINE
operator|.
name|length
argument_list|()
expr_stmt|;
block|}
block|}
comment|/**      * Quotes each and every character, e.g. '!' as&#33;. Used for verbatim      * display of arbitrary strings that may contain HTML entities.      */
DECL|method|quoteForHTML (String toQuote)
specifier|public
specifier|static
name|String
name|quoteForHTML
parameter_list|(
name|String
name|toQuote
parameter_list|)
block|{
name|StringBuilder
name|result
init|=
operator|new
name|StringBuilder
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
name|toQuote
operator|.
name|length
argument_list|()
condition|;
operator|++
name|i
control|)
block|{
name|result
operator|.
name|append
argument_list|(
literal|"&#"
argument_list|)
operator|.
name|append
argument_list|(
operator|(
name|int
operator|)
name|toQuote
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|';'
argument_list|)
expr_stmt|;
block|}
return|return
name|result
operator|.
name|toString
argument_list|()
return|;
block|}
comment|/**      * Quote special characters.      *      * @param toQuote         The String which may contain special characters.      * @param specials  A String containing all special characters except the quoting      *                  character itself, which is automatically quoted.      * @param quoteChar The quoting character.      * @return A String with every special character (including the quoting      * character itself) quoted.      */
DECL|method|quote (String toQuote, String specials, char quoteChar)
specifier|public
specifier|static
name|String
name|quote
parameter_list|(
name|String
name|toQuote
parameter_list|,
name|String
name|specials
parameter_list|,
name|char
name|quoteChar
parameter_list|)
block|{
name|StringBuilder
name|result
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|char
name|c
decl_stmt|;
name|boolean
name|isSpecial
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
name|toQuote
operator|.
name|length
argument_list|()
condition|;
operator|++
name|i
control|)
block|{
name|c
operator|=
name|toQuote
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
expr_stmt|;
name|isSpecial
operator|=
operator|(
name|c
operator|==
name|quoteChar
operator|)
expr_stmt|;
comment|// If non-null specials performs logic-or with specials.indexOf(c)>= 0
name|isSpecial
operator||=
operator|(
operator|(
name|specials
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|specials
operator|.
name|indexOf
argument_list|(
name|c
argument_list|)
operator|>=
literal|0
operator|)
operator|)
expr_stmt|;
if|if
condition|(
name|isSpecial
condition|)
block|{
name|result
operator|.
name|append
argument_list|(
name|quoteChar
argument_list|)
expr_stmt|;
block|}
name|result
operator|.
name|append
argument_list|(
name|c
argument_list|)
expr_stmt|;
block|}
return|return
name|result
operator|.
name|toString
argument_list|()
return|;
block|}
comment|/**      * Unquote special characters.      *      * @param toUnquote         The String which may contain quoted special characters.      * @param quoteChar The quoting character.      * @return A String with all quoted characters unquoted.      */
DECL|method|unquote (String toUnquote, char quoteChar)
specifier|public
specifier|static
name|String
name|unquote
parameter_list|(
name|String
name|toUnquote
parameter_list|,
name|char
name|quoteChar
parameter_list|)
block|{
name|StringBuilder
name|result
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|char
name|c
decl_stmt|;
name|boolean
name|quoted
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
name|toUnquote
operator|.
name|length
argument_list|()
condition|;
operator|++
name|i
control|)
block|{
name|c
operator|=
name|toUnquote
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
expr_stmt|;
if|if
condition|(
name|quoted
condition|)
block|{
comment|// append literally...
if|if
condition|(
name|c
operator|!=
literal|'\n'
condition|)
block|{
name|result
operator|.
name|append
argument_list|(
name|c
argument_list|)
expr_stmt|;
block|}
name|quoted
operator|=
literal|false
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|c
operator|==
name|quoteChar
condition|)
block|{
comment|// quote char
name|quoted
operator|=
literal|true
expr_stmt|;
block|}
else|else
block|{
name|result
operator|.
name|append
argument_list|(
name|c
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|result
operator|.
name|toString
argument_list|()
return|;
block|}
DECL|method|booleanToBinaryString (boolean expression)
specifier|public
specifier|static
name|String
name|booleanToBinaryString
parameter_list|(
name|boolean
name|expression
parameter_list|)
block|{
return|return
name|expression
condition|?
literal|"1"
else|:
literal|"0"
return|;
block|}
comment|/**      * Decodes an encoded double String array back into array form. The array      * is assumed to be square, and delimited by the characters ';' (first dim) and      * ':' (second dim).      * @param value The encoded String to be decoded.      * @return The decoded String array.      */
DECL|method|decodeStringDoubleArray (String value)
specifier|public
specifier|static
name|String
index|[]
index|[]
name|decodeStringDoubleArray
parameter_list|(
name|String
name|value
parameter_list|)
block|{
name|ArrayList
argument_list|<
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|>
name|newList
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|ArrayList
argument_list|<
name|String
argument_list|>
name|thisEntry
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|boolean
name|escaped
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
name|value
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
name|value
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|escaped
operator|&&
operator|(
name|c
operator|==
literal|'\\'
operator|)
condition|)
block|{
name|escaped
operator|=
literal|true
expr_stmt|;
continue|continue;
block|}
elseif|else
if|if
condition|(
operator|!
name|escaped
operator|&&
operator|(
name|c
operator|==
literal|':'
operator|)
condition|)
block|{
name|thisEntry
operator|.
name|add
argument_list|(
name|sb
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|sb
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
name|escaped
operator|&&
operator|(
name|c
operator|==
literal|';'
operator|)
condition|)
block|{
name|thisEntry
operator|.
name|add
argument_list|(
name|sb
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|sb
operator|=
operator|new
name|StringBuilder
argument_list|()
expr_stmt|;
name|newList
operator|.
name|add
argument_list|(
name|thisEntry
argument_list|)
expr_stmt|;
name|thisEntry
operator|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
expr_stmt|;
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
name|escaped
operator|=
literal|false
expr_stmt|;
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
name|thisEntry
operator|.
name|add
argument_list|(
name|sb
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|thisEntry
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|newList
operator|.
name|add
argument_list|(
name|thisEntry
argument_list|)
expr_stmt|;
block|}
comment|// Convert to String[][]:
name|String
index|[]
index|[]
name|res
init|=
operator|new
name|String
index|[
name|newList
operator|.
name|size
argument_list|()
index|]
index|[]
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
name|res
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|res
index|[
name|i
index|]
operator|=
operator|new
name|String
index|[
name|newList
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|.
name|size
argument_list|()
index|]
expr_stmt|;
for|for
control|(
name|int
name|j
init|=
literal|0
init|;
name|j
operator|<
name|res
index|[
name|i
index|]
operator|.
name|length
condition|;
name|j
operator|++
control|)
block|{
name|res
index|[
name|i
index|]
index|[
name|j
index|]
operator|=
name|newList
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|.
name|get
argument_list|(
name|j
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|res
return|;
block|}
comment|/**      * Wrap all uppercase letters, or sequences of uppercase letters, in curly      * braces. Ignore letters within a pair of # character, as these are part of      * a string label that should not be modified.      *      * @param s      *            The string to modify.      * @return The resulting string after wrapping capitals.      */
DECL|method|putBracesAroundCapitals (String s)
specifier|public
specifier|static
name|String
name|putBracesAroundCapitals
parameter_list|(
name|String
name|s
parameter_list|)
block|{
name|boolean
name|inString
init|=
literal|false
decl_stmt|;
name|boolean
name|isBracing
init|=
literal|false
decl_stmt|;
name|boolean
name|escaped
init|=
literal|false
decl_stmt|;
name|int
name|inBrace
init|=
literal|0
decl_stmt|;
name|StringBuilder
name|buf
init|=
operator|new
name|StringBuilder
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
name|s
operator|.
name|length
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
comment|// Update variables based on special characters:
name|int
name|c
init|=
name|s
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
literal|'{'
condition|)
block|{
name|inBrace
operator|++
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
name|inBrace
operator|--
expr_stmt|;
block|}
elseif|else
if|if
condition|(
operator|!
name|escaped
operator|&&
operator|(
name|c
operator|==
literal|'#'
operator|)
condition|)
block|{
name|inString
operator|=
operator|!
name|inString
expr_stmt|;
block|}
comment|// See if we should start bracing:
if|if
condition|(
operator|(
name|inBrace
operator|==
literal|0
operator|)
operator|&&
operator|!
name|isBracing
operator|&&
operator|!
name|inString
operator|&&
name|Character
operator|.
name|isLetter
argument_list|(
operator|(
name|char
operator|)
name|c
argument_list|)
operator|&&
name|Character
operator|.
name|isUpperCase
argument_list|(
operator|(
name|char
operator|)
name|c
argument_list|)
condition|)
block|{
name|buf
operator|.
name|append
argument_list|(
literal|'{'
argument_list|)
expr_stmt|;
name|isBracing
operator|=
literal|true
expr_stmt|;
block|}
comment|// See if we should close a brace set:
if|if
condition|(
name|isBracing
operator|&&
operator|!
operator|(
name|Character
operator|.
name|isLetter
argument_list|(
operator|(
name|char
operator|)
name|c
argument_list|)
operator|&&
name|Character
operator|.
name|isUpperCase
argument_list|(
operator|(
name|char
operator|)
name|c
argument_list|)
operator|)
condition|)
block|{
name|buf
operator|.
name|append
argument_list|(
literal|'}'
argument_list|)
expr_stmt|;
name|isBracing
operator|=
literal|false
expr_stmt|;
block|}
comment|// Add the current character:
name|buf
operator|.
name|append
argument_list|(
operator|(
name|char
operator|)
name|c
argument_list|)
expr_stmt|;
comment|// Check if we are entering an escape sequence:
name|escaped
operator|=
operator|(
name|c
operator|==
literal|'\\'
operator|)
operator|&&
operator|!
name|escaped
expr_stmt|;
block|}
comment|// Check if we have an unclosed brace:
if|if
condition|(
name|isBracing
condition|)
block|{
name|buf
operator|.
name|append
argument_list|(
literal|'}'
argument_list|)
expr_stmt|;
block|}
return|return
name|buf
operator|.
name|toString
argument_list|()
return|;
comment|/*          * if (s.isEmpty()) return s; // Protect against ArrayIndexOutOf....          * StringBuffer buf = new StringBuffer();          *          * Matcher mcr = titleCapitalPattern.matcher(s.substring(1)); while          * (mcr.find()) { String replaceStr = mcr.group();          * mcr.appendReplacement(buf, "{" + replaceStr + "}"); }          * mcr.appendTail(buf); return s.substring(0, 1) + buf.toString();          */
block|}
comment|/**      * This method looks for occurrences of capital letters enclosed in an      * arbitrary number of pairs of braces, e.g. "{AB}" or "{{T}}". All of these      * pairs of braces are removed.      *      * @param s      *            The String to analyze.      * @return A new String with braces removed.      */
DECL|method|removeBracesAroundCapitals (String s)
specifier|public
specifier|static
name|String
name|removeBracesAroundCapitals
parameter_list|(
name|String
name|s
parameter_list|)
block|{
name|String
name|previous
init|=
name|s
decl_stmt|;
while|while
condition|(
operator|(
name|s
operator|=
name|removeSingleBracesAroundCapitals
argument_list|(
name|s
argument_list|)
operator|)
operator|.
name|length
argument_list|()
operator|<
name|previous
operator|.
name|length
argument_list|()
condition|)
block|{
name|previous
operator|=
name|s
expr_stmt|;
block|}
return|return
name|s
return|;
block|}
comment|/**      * This method looks for occurrences of capital letters enclosed in one pair      * of braces, e.g. "{AB}". All these are replaced by only the capitals in      * between the braces.      *      * @param s      *            The String to analyze.      * @return A new String with braces removed.      */
DECL|method|removeSingleBracesAroundCapitals (String s)
specifier|private
specifier|static
name|String
name|removeSingleBracesAroundCapitals
parameter_list|(
name|String
name|s
parameter_list|)
block|{
name|Matcher
name|mcr
init|=
name|BRACED_TITLE_CAPITAL_PATTERN
operator|.
name|matcher
argument_list|(
name|s
argument_list|)
decl_stmt|;
name|StringBuffer
name|buf
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
while|while
condition|(
name|mcr
operator|.
name|find
argument_list|()
condition|)
block|{
name|String
name|replaceStr
init|=
name|mcr
operator|.
name|group
argument_list|()
decl_stmt|;
name|mcr
operator|.
name|appendReplacement
argument_list|(
name|buf
argument_list|,
name|replaceStr
operator|.
name|substring
argument_list|(
literal|1
argument_list|,
name|replaceStr
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|mcr
operator|.
name|appendTail
argument_list|(
name|buf
argument_list|)
expr_stmt|;
return|return
name|buf
operator|.
name|toString
argument_list|()
return|;
block|}
comment|/**      * Replaces all platform-dependent line breaks by Globals.NEWLINE line breaks.      *      * We do NOT use UNIX line breaks as the user explicitly configures its linebreaks and this method is used in bibtex field writing      *      *<example>      * Legacy Macintosh \r -> Globals.NEWLINE      * Windows \r\n -> Globals.NEWLINE      *</example>      *      * @return a String with only Globals.NEWLINE as line breaks      */
DECL|method|unifyLineBreaksToConfiguredLineBreaks (String s)
specifier|public
specifier|static
name|String
name|unifyLineBreaksToConfiguredLineBreaks
parameter_list|(
name|String
name|s
parameter_list|)
block|{
return|return
name|LINE_BREAKS
operator|.
name|matcher
argument_list|(
name|s
argument_list|)
operator|.
name|replaceAll
argument_list|(
name|Globals
operator|.
name|NEWLINE
argument_list|)
return|;
block|}
DECL|method|isInCurlyBrackets (String toCheck)
specifier|public
specifier|static
name|boolean
name|isInCurlyBrackets
parameter_list|(
name|String
name|toCheck
parameter_list|)
block|{
if|if
condition|(
operator|(
name|toCheck
operator|==
literal|null
operator|)
operator|||
name|toCheck
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
literal|false
return|;
comment|// In case of null or empty string
block|}
else|else
block|{
return|return
operator|(
name|toCheck
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
operator|==
literal|'{'
operator|)
operator|&&
operator|(
name|toCheck
operator|.
name|charAt
argument_list|(
name|toCheck
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
operator|==
literal|'}'
operator|)
return|;
block|}
block|}
DECL|method|isInSquareBrackets (String toCheck)
specifier|public
specifier|static
name|boolean
name|isInSquareBrackets
parameter_list|(
name|String
name|toCheck
parameter_list|)
block|{
if|if
condition|(
operator|(
name|toCheck
operator|==
literal|null
operator|)
operator|||
name|toCheck
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
literal|false
return|;
comment|// In case of null or empty string
block|}
else|else
block|{
return|return
operator|(
name|toCheck
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
operator|==
literal|'['
operator|)
operator|&&
operator|(
name|toCheck
operator|.
name|charAt
argument_list|(
name|toCheck
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
operator|==
literal|']'
operator|)
return|;
block|}
block|}
DECL|method|isInCitationMarks (String toCheck)
specifier|public
specifier|static
name|boolean
name|isInCitationMarks
parameter_list|(
name|String
name|toCheck
parameter_list|)
block|{
if|if
condition|(
operator|(
name|toCheck
operator|==
literal|null
operator|)
operator|||
operator|(
name|toCheck
operator|.
name|length
argument_list|()
operator|<=
literal|1
operator|)
condition|)
block|{
return|return
literal|false
return|;
comment|// In case of null, empty string, or a single citation mark
block|}
else|else
block|{
return|return
operator|(
name|toCheck
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
operator|==
literal|'"'
operator|)
operator|&&
operator|(
name|toCheck
operator|.
name|charAt
argument_list|(
name|toCheck
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
operator|==
literal|'"'
operator|)
return|;
block|}
block|}
comment|/**      * Optimized method for converting a String into an Integer      *      * From http://stackoverflow.com/questions/1030479/most-efficient-way-of-converting-string-to-integer-in-java      *      * @param str the String holding an Integer value      * @throws NumberFormatException if str cannot be parsed to an int      * @return the int value of str      */
DECL|method|intValueOf (String str)
specifier|public
specifier|static
name|int
name|intValueOf
parameter_list|(
name|String
name|str
parameter_list|)
block|{
name|int
name|idx
init|=
literal|0
decl_stmt|;
name|int
name|end
decl_stmt|;
name|boolean
name|sign
init|=
literal|false
decl_stmt|;
name|char
name|ch
decl_stmt|;
if|if
condition|(
operator|(
name|str
operator|==
literal|null
operator|)
operator|||
operator|(
operator|(
name|end
operator|=
name|str
operator|.
name|length
argument_list|()
operator|)
operator|==
literal|0
operator|)
operator|||
operator|(
operator|(
operator|(
operator|(
name|ch
operator|=
name|str
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
operator|)
operator|<
literal|'0'
operator|)
operator|||
operator|(
name|ch
operator|>
literal|'9'
operator|)
operator|)
operator|&&
operator|(
operator|!
operator|(
name|sign
operator|=
name|ch
operator|==
literal|'-'
operator|)
operator|||
operator|(
operator|++
name|idx
operator|==
name|end
operator|)
operator|||
operator|(
operator|(
name|ch
operator|=
name|str
operator|.
name|charAt
argument_list|(
name|idx
argument_list|)
operator|)
operator|<
literal|'0'
operator|)
operator|||
operator|(
name|ch
operator|>
literal|'9'
operator|)
operator|)
operator|)
condition|)
block|{
throw|throw
operator|new
name|NumberFormatException
argument_list|(
name|str
argument_list|)
throw|;
block|}
name|int
name|ival
init|=
literal|0
decl_stmt|;
for|for
control|(
init|;
condition|;
name|ival
operator|*=
literal|10
control|)
block|{
name|ival
operator|+=
literal|'0'
operator|-
name|ch
expr_stmt|;
if|if
condition|(
operator|++
name|idx
operator|==
name|end
condition|)
block|{
return|return
name|sign
condition|?
name|ival
else|:
operator|-
name|ival
return|;
block|}
if|if
condition|(
operator|(
operator|(
name|ch
operator|=
name|str
operator|.
name|charAt
argument_list|(
name|idx
argument_list|)
operator|)
operator|<
literal|'0'
operator|)
operator|||
operator|(
name|ch
operator|>
literal|'9'
operator|)
condition|)
block|{
throw|throw
operator|new
name|NumberFormatException
argument_list|(
name|str
argument_list|)
throw|;
block|}
block|}
block|}
block|}
end_class

end_unit

