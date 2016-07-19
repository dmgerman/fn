begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.logic.bibtex
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|bibtex
package|;
end_package

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
name|List
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

begin_import
import|import
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
name|model
operator|.
name|entry
operator|.
name|InternalBibtexFields
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
name|preferences
operator|.
name|JabRefPreferences
import|;
end_import

begin_comment
comment|/**  * Currently the only implementation of net.sf.jabref.exporter.FieldFormatter  *<p>  * Obeys following settings:  * * JabRefPreferences.RESOLVE_STRINGS_ALL_FIELDS  * * JabRefPreferences.DO_NOT_RESOLVE_STRINGS_FOR  * * JabRefPreferences.WRITEFIELD_WRAPFIELD  */
end_comment

begin_class
DECL|class|LatexFieldFormatter
specifier|public
class|class
name|LatexFieldFormatter
block|{
comment|// "Fieldname" to indicate that a field should be treated as a bibtex string. Used when writing database to file.
DECL|field|BIBTEX_STRING
specifier|public
specifier|static
specifier|final
name|String
name|BIBTEX_STRING
init|=
literal|"__string"
decl_stmt|;
DECL|field|stringBuilder
specifier|private
name|StringBuilder
name|stringBuilder
decl_stmt|;
DECL|field|neverFailOnHashes
specifier|private
specifier|final
name|boolean
name|neverFailOnHashes
decl_stmt|;
DECL|field|resolveStringsAllFields
specifier|private
specifier|final
name|boolean
name|resolveStringsAllFields
decl_stmt|;
DECL|field|valueDelimiterStartOfValue
specifier|private
specifier|final
name|char
name|valueDelimiterStartOfValue
decl_stmt|;
DECL|field|valueDelimiterEndOfValue
specifier|private
specifier|final
name|char
name|valueDelimiterEndOfValue
decl_stmt|;
DECL|field|doNotResolveStringsFors
specifier|private
specifier|final
name|List
argument_list|<
name|String
argument_list|>
name|doNotResolveStringsFors
decl_stmt|;
DECL|field|lineLength
specifier|private
specifier|final
name|int
name|lineLength
decl_stmt|;
DECL|field|parser
specifier|private
specifier|final
name|FieldContentParser
name|parser
decl_stmt|;
DECL|method|LatexFieldFormatter ()
specifier|public
name|LatexFieldFormatter
parameter_list|()
block|{
name|this
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
DECL|method|LatexFieldFormatter (boolean neverFailOnHashes)
specifier|private
name|LatexFieldFormatter
parameter_list|(
name|boolean
name|neverFailOnHashes
parameter_list|)
block|{
name|this
operator|.
name|neverFailOnHashes
operator|=
name|neverFailOnHashes
expr_stmt|;
name|this
operator|.
name|resolveStringsAllFields
operator|=
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|RESOLVE_STRINGS_ALL_FIELDS
argument_list|)
expr_stmt|;
name|valueDelimiterStartOfValue
operator|=
name|Globals
operator|.
name|prefs
operator|.
name|getValueDelimiters
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|valueDelimiterEndOfValue
operator|=
name|Globals
operator|.
name|prefs
operator|.
name|getValueDelimiters
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|doNotResolveStringsFors
operator|=
name|Globals
operator|.
name|prefs
operator|.
name|getStringList
argument_list|(
name|JabRefPreferences
operator|.
name|DO_NOT_RESOLVE_STRINGS_FOR
argument_list|)
expr_stmt|;
name|lineLength
operator|=
name|Globals
operator|.
name|prefs
operator|.
name|getInt
argument_list|(
name|JabRefPreferences
operator|.
name|LINE_LENGTH
argument_list|)
expr_stmt|;
name|parser
operator|=
operator|new
name|FieldContentParser
argument_list|()
expr_stmt|;
block|}
DECL|method|buildIgnoreHashes ()
specifier|public
specifier|static
name|LatexFieldFormatter
name|buildIgnoreHashes
parameter_list|()
block|{
return|return
operator|new
name|LatexFieldFormatter
argument_list|(
literal|true
argument_list|)
return|;
block|}
comment|/**      * Formats the content of a field.      *      * @param content   the content of the field      * @param fieldName the name of the field - used to trigger different serializations, e.g., turning off resolution for some strings      * @return a formatted string suitable for output      * @throws IllegalArgumentException if s is not a correct bibtex string, e.g., because of improperly balanced braces or using # not paired      */
DECL|method|format (String content, String fieldName)
specifier|public
name|String
name|format
parameter_list|(
name|String
name|content
parameter_list|,
name|String
name|fieldName
parameter_list|)
throws|throws
name|IllegalArgumentException
block|{
if|if
condition|(
name|content
operator|==
literal|null
condition|)
block|{
return|return
name|valueDelimiterStartOfValue
operator|+
name|String
operator|.
name|valueOf
argument_list|(
name|valueDelimiterEndOfValue
argument_list|)
return|;
block|}
name|String
name|result
init|=
name|content
decl_stmt|;
comment|// normalize newlines
name|boolean
name|shouldNormalizeNewlines
init|=
operator|!
name|result
operator|.
name|contains
argument_list|(
name|Globals
operator|.
name|NEWLINE
argument_list|)
operator|&&
name|result
operator|.
name|contains
argument_list|(
literal|"\n"
argument_list|)
decl_stmt|;
if|if
condition|(
name|shouldNormalizeNewlines
condition|)
block|{
comment|// if we don't have real new lines, but pseudo newlines, we replace them
comment|// On Win 8.1, this is always true for multiline fields
name|result
operator|=
name|result
operator|.
name|replace
argument_list|(
literal|"\n"
argument_list|,
name|Globals
operator|.
name|NEWLINE
argument_list|)
expr_stmt|;
block|}
comment|// If the field is non-standard, we will just append braces,
comment|// wrap and write.
name|boolean
name|resolveStrings
init|=
name|shouldResolveStrings
argument_list|(
name|fieldName
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|resolveStrings
condition|)
block|{
return|return
name|formatWithoutResolvingStrings
argument_list|(
name|result
argument_list|,
name|fieldName
argument_list|)
return|;
block|}
comment|// Trim whitespace
name|result
operator|=
name|result
operator|.
name|trim
argument_list|()
expr_stmt|;
return|return
name|formatAndResolveStrings
argument_list|(
name|result
argument_list|,
name|fieldName
argument_list|)
return|;
block|}
DECL|method|formatAndResolveStrings (String content, String fieldName)
specifier|private
name|String
name|formatAndResolveStrings
parameter_list|(
name|String
name|content
parameter_list|,
name|String
name|fieldName
parameter_list|)
block|{
name|stringBuilder
operator|=
operator|new
name|StringBuilder
argument_list|()
expr_stmt|;
name|int
name|pivot
init|=
literal|0
decl_stmt|;
name|int
name|pos1
decl_stmt|;
name|int
name|pos2
decl_stmt|;
comment|// Here we assume that the user encloses any bibtex strings in #, e.g.:
comment|// #jan# - #feb#
comment|// ...which will be written to the file like this:
comment|// jan # { - } # feb
name|checkBraces
argument_list|(
name|content
argument_list|)
expr_stmt|;
while|while
condition|(
name|pivot
operator|<
name|content
operator|.
name|length
argument_list|()
condition|)
block|{
name|int
name|goFrom
init|=
name|pivot
decl_stmt|;
name|pos1
operator|=
name|pivot
expr_stmt|;
while|while
condition|(
name|goFrom
operator|==
name|pos1
condition|)
block|{
name|pos1
operator|=
name|content
operator|.
name|indexOf
argument_list|(
literal|'#'
argument_list|,
name|goFrom
argument_list|)
expr_stmt|;
if|if
condition|(
operator|(
name|pos1
operator|>
literal|0
operator|)
operator|&&
operator|(
name|content
operator|.
name|charAt
argument_list|(
name|pos1
operator|-
literal|1
argument_list|)
operator|==
literal|'\\'
operator|)
condition|)
block|{
name|goFrom
operator|=
name|pos1
operator|+
literal|1
expr_stmt|;
name|pos1
operator|++
expr_stmt|;
block|}
else|else
block|{
name|goFrom
operator|=
name|pos1
operator|-
literal|1
expr_stmt|;
comment|// Ends the loop.
block|}
block|}
if|if
condition|(
name|pos1
operator|==
operator|-
literal|1
condition|)
block|{
name|pos1
operator|=
name|content
operator|.
name|length
argument_list|()
expr_stmt|;
comment|// No more occurrences found.
name|pos2
operator|=
operator|-
literal|1
expr_stmt|;
block|}
else|else
block|{
name|pos2
operator|=
name|content
operator|.
name|indexOf
argument_list|(
literal|'#'
argument_list|,
name|pos1
operator|+
literal|1
argument_list|)
expr_stmt|;
if|if
condition|(
name|pos2
operator|==
operator|-
literal|1
condition|)
block|{
if|if
condition|(
name|neverFailOnHashes
condition|)
block|{
name|pos1
operator|=
name|content
operator|.
name|length
argument_list|()
expr_stmt|;
comment|// just write out the rest of the text, and throw no exception
block|}
else|else
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The # character is not allowed in BibTeX strings unless escaped as in '\\#'.\n"
operator|+
literal|"In JabRef, use pairs of # characters to indicate a string.\n"
operator|+
literal|"Note that the entry causing the problem has been selected."
argument_list|)
throw|;
block|}
block|}
block|}
if|if
condition|(
name|pos1
operator|>
name|pivot
condition|)
block|{
name|writeText
argument_list|(
name|content
argument_list|,
name|pivot
argument_list|,
name|pos1
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|(
name|pos1
operator|<
name|content
operator|.
name|length
argument_list|()
operator|)
operator|&&
operator|(
operator|(
name|pos2
operator|-
literal|1
operator|)
operator|>
name|pos1
operator|)
condition|)
block|{
comment|// We check that the string label is not empty. That means
comment|// an occurrence of ## will simply be ignored. Should it instead
comment|// cause an error message?
name|writeStringLabel
argument_list|(
name|content
argument_list|,
name|pos1
operator|+
literal|1
argument_list|,
name|pos2
argument_list|,
name|pos1
operator|==
name|pivot
argument_list|,
operator|(
name|pos2
operator|+
literal|1
operator|)
operator|==
name|content
operator|.
name|length
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|pos2
operator|>
operator|-
literal|1
condition|)
block|{
name|pivot
operator|=
name|pos2
operator|+
literal|1
expr_stmt|;
block|}
else|else
block|{
name|pivot
operator|=
name|pos1
operator|+
literal|1
expr_stmt|;
comment|//if (tell++> 10) System.exit(0);
block|}
block|}
return|return
name|parser
operator|.
name|format
argument_list|(
name|stringBuilder
argument_list|,
name|fieldName
argument_list|)
return|;
block|}
DECL|method|shouldResolveStrings (String fieldName)
specifier|private
name|boolean
name|shouldResolveStrings
parameter_list|(
name|String
name|fieldName
parameter_list|)
block|{
name|boolean
name|resolveStrings
init|=
literal|true
decl_stmt|;
if|if
condition|(
name|resolveStringsAllFields
condition|)
block|{
comment|// Resolve strings for all fields except some:
for|for
control|(
name|String
name|exception
range|:
name|doNotResolveStringsFors
control|)
block|{
if|if
condition|(
name|exception
operator|.
name|equals
argument_list|(
name|fieldName
argument_list|)
condition|)
block|{
name|resolveStrings
operator|=
literal|false
expr_stmt|;
break|break;
block|}
block|}
block|}
else|else
block|{
comment|// Default operation - we only resolve strings for standard fields:
name|resolveStrings
operator|=
name|InternalBibtexFields
operator|.
name|isStandardField
argument_list|(
name|fieldName
argument_list|)
operator|||
name|BIBTEX_STRING
operator|.
name|equals
argument_list|(
name|fieldName
argument_list|)
expr_stmt|;
block|}
return|return
name|resolveStrings
return|;
block|}
DECL|method|formatWithoutResolvingStrings (String content, String fieldName)
specifier|private
name|String
name|formatWithoutResolvingStrings
parameter_list|(
name|String
name|content
parameter_list|,
name|String
name|fieldName
parameter_list|)
block|{
name|checkBraces
argument_list|(
name|content
argument_list|)
expr_stmt|;
name|stringBuilder
operator|=
operator|new
name|StringBuilder
argument_list|(
name|String
operator|.
name|valueOf
argument_list|(
name|valueDelimiterStartOfValue
argument_list|)
argument_list|)
expr_stmt|;
name|stringBuilder
operator|.
name|append
argument_list|(
name|parser
operator|.
name|format
argument_list|(
name|content
argument_list|,
name|fieldName
argument_list|)
argument_list|)
expr_stmt|;
name|stringBuilder
operator|.
name|append
argument_list|(
name|valueDelimiterEndOfValue
argument_list|)
expr_stmt|;
return|return
name|stringBuilder
operator|.
name|toString
argument_list|()
return|;
block|}
DECL|method|writeText (String text, int startPos, int endPos)
specifier|private
name|void
name|writeText
parameter_list|(
name|String
name|text
parameter_list|,
name|int
name|startPos
parameter_list|,
name|int
name|endPos
parameter_list|)
block|{
name|stringBuilder
operator|.
name|append
argument_list|(
name|valueDelimiterStartOfValue
argument_list|)
expr_stmt|;
name|boolean
name|escape
init|=
literal|false
decl_stmt|;
name|boolean
name|inCommandName
init|=
literal|false
decl_stmt|;
name|boolean
name|inCommand
init|=
literal|false
decl_stmt|;
name|boolean
name|inCommandOption
init|=
literal|false
decl_stmt|;
name|int
name|nestedEnvironments
init|=
literal|0
decl_stmt|;
name|StringBuilder
name|commandName
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|char
name|c
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
name|startPos
init|;
name|i
operator|<
name|endPos
condition|;
name|i
operator|++
control|)
block|{
name|c
operator|=
name|text
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
expr_stmt|;
comment|// Track whether we are in a LaTeX command of some sort.
if|if
condition|(
name|Character
operator|.
name|isLetter
argument_list|(
name|c
argument_list|)
operator|&&
operator|(
name|escape
operator|||
name|inCommandName
operator|)
condition|)
block|{
name|inCommandName
operator|=
literal|true
expr_stmt|;
if|if
condition|(
operator|!
name|inCommandOption
condition|)
block|{
name|commandName
operator|.
name|append
argument_list|(
name|c
argument_list|)
expr_stmt|;
block|}
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
operator|&&
operator|(
name|inCommand
operator|||
name|inCommandOption
operator|)
condition|)
block|{
comment|// Whitespace
block|}
elseif|else
if|if
condition|(
name|inCommandName
condition|)
block|{
comment|// This means the command name is ended.
comment|// Perhaps the beginning of an argument:
if|if
condition|(
name|c
operator|==
literal|'['
condition|)
block|{
name|inCommandOption
operator|=
literal|true
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|inCommandOption
operator|&&
operator|(
name|c
operator|==
literal|']'
operator|)
condition|)
block|{
comment|// Or the end of an argument:
name|inCommandOption
operator|=
literal|false
expr_stmt|;
block|}
elseif|else
if|if
condition|(
operator|!
name|inCommandOption
operator|&&
operator|(
name|c
operator|==
literal|'{'
operator|)
condition|)
block|{
name|inCommandName
operator|=
literal|false
expr_stmt|;
name|inCommand
operator|=
literal|true
expr_stmt|;
block|}
else|else
block|{
comment|// Or simply the end of this command altogether:
name|commandName
operator|.
name|delete
argument_list|(
literal|0
argument_list|,
name|commandName
operator|.
name|length
argument_list|()
argument_list|)
expr_stmt|;
name|inCommandName
operator|=
literal|false
expr_stmt|;
block|}
block|}
comment|// If we are in a command body, see if it has ended:
if|if
condition|(
name|inCommand
operator|&&
operator|(
name|c
operator|==
literal|'}'
operator|)
condition|)
block|{
if|if
condition|(
literal|"begin"
operator|.
name|equals
argument_list|(
name|commandName
operator|.
name|toString
argument_list|()
argument_list|)
condition|)
block|{
name|nestedEnvironments
operator|++
expr_stmt|;
block|}
if|if
condition|(
operator|(
name|nestedEnvironments
operator|>
literal|0
operator|)
operator|&&
literal|"end"
operator|.
name|equals
argument_list|(
name|commandName
operator|.
name|toString
argument_list|()
argument_list|)
condition|)
block|{
name|nestedEnvironments
operator|--
expr_stmt|;
block|}
name|commandName
operator|.
name|delete
argument_list|(
literal|0
argument_list|,
name|commandName
operator|.
name|length
argument_list|()
argument_list|)
expr_stmt|;
name|inCommand
operator|=
literal|false
expr_stmt|;
block|}
comment|// We add a backslash before any ampersand characters, with one exception: if
comment|// we are inside an \\url{...} command, we should write it as it is. Maybe.
if|if
condition|(
operator|(
name|c
operator|==
literal|'&'
operator|)
operator|&&
operator|!
name|escape
operator|&&
operator|!
operator|(
name|inCommand
operator|&&
literal|"url"
operator|.
name|equals
argument_list|(
name|commandName
operator|.
name|toString
argument_list|()
argument_list|)
operator|)
operator|&&
operator|(
name|nestedEnvironments
operator|==
literal|0
operator|)
condition|)
block|{
name|stringBuilder
operator|.
name|append
argument_list|(
literal|"\\&"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|stringBuilder
operator|.
name|append
argument_list|(
name|c
argument_list|)
expr_stmt|;
block|}
name|escape
operator|=
name|c
operator|==
literal|'\\'
expr_stmt|;
block|}
name|stringBuilder
operator|.
name|append
argument_list|(
name|valueDelimiterEndOfValue
argument_list|)
expr_stmt|;
block|}
DECL|method|writeStringLabel (String text, int startPos, int endPos, boolean first, boolean last)
specifier|private
name|void
name|writeStringLabel
parameter_list|(
name|String
name|text
parameter_list|,
name|int
name|startPos
parameter_list|,
name|int
name|endPos
parameter_list|,
name|boolean
name|first
parameter_list|,
name|boolean
name|last
parameter_list|)
block|{
name|putIn
argument_list|(
operator|(
name|first
condition|?
literal|""
else|:
literal|" # "
operator|)
operator|+
name|text
operator|.
name|substring
argument_list|(
name|startPos
argument_list|,
name|endPos
argument_list|)
operator|+
operator|(
name|last
condition|?
literal|""
else|:
literal|" # "
operator|)
argument_list|)
expr_stmt|;
block|}
DECL|method|putIn (String s)
specifier|private
name|void
name|putIn
parameter_list|(
name|String
name|s
parameter_list|)
block|{
name|stringBuilder
operator|.
name|append
argument_list|(
name|StringUtil
operator|.
name|wrap
argument_list|(
name|s
argument_list|,
name|lineLength
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|checkBraces (String text)
specifier|private
specifier|static
name|void
name|checkBraces
parameter_list|(
name|String
name|text
parameter_list|)
throws|throws
name|IllegalArgumentException
block|{
name|List
argument_list|<
name|Integer
argument_list|>
name|left
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|(
literal|5
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|Integer
argument_list|>
name|right
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|(
literal|5
argument_list|)
decl_stmt|;
name|int
name|current
init|=
operator|-
literal|1
decl_stmt|;
comment|// First we collect all occurrences:
while|while
condition|(
operator|(
name|current
operator|=
name|text
operator|.
name|indexOf
argument_list|(
literal|'{'
argument_list|,
name|current
operator|+
literal|1
argument_list|)
operator|)
operator|!=
operator|-
literal|1
condition|)
block|{
name|left
operator|.
name|add
argument_list|(
name|current
argument_list|)
expr_stmt|;
block|}
while|while
condition|(
operator|(
name|current
operator|=
name|text
operator|.
name|indexOf
argument_list|(
literal|'}'
argument_list|,
name|current
operator|+
literal|1
argument_list|)
operator|)
operator|!=
operator|-
literal|1
condition|)
block|{
name|right
operator|.
name|add
argument_list|(
name|current
argument_list|)
expr_stmt|;
block|}
comment|// Then we throw an exception if the error criteria are met.
if|if
condition|(
operator|!
name|right
operator|.
name|isEmpty
argument_list|()
operator|&&
name|left
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"'}' character ends string prematurely."
argument_list|)
throw|;
block|}
if|if
condition|(
operator|!
name|right
operator|.
name|isEmpty
argument_list|()
operator|&&
operator|(
name|right
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|<
name|left
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|)
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"'}' character ends string prematurely."
argument_list|)
throw|;
block|}
if|if
condition|(
name|left
operator|.
name|size
argument_list|()
operator|!=
name|right
operator|.
name|size
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Braces don't match."
argument_list|)
throw|;
block|}
block|}
block|}
end_class

end_unit

