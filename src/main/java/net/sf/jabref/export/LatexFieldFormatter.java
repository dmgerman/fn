begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.export
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|export
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
name|*
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
name|gui
operator|.
name|GUIGlobals
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
name|l10n
operator|.
name|Localization
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
name|util
operator|.
name|Util
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Vector
import|;
end_import

begin_class
DECL|class|LatexFieldFormatter
specifier|public
class|class
name|LatexFieldFormatter
implements|implements
name|FieldFormatter
block|{
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
DECL|field|sb
specifier|private
name|StringBuffer
name|sb
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
DECL|field|valueDelimitersZero
specifier|private
specifier|final
name|char
name|valueDelimitersZero
decl_stmt|;
DECL|field|valueDelimitersOne
specifier|private
specifier|final
name|char
name|valueDelimitersOne
decl_stmt|;
DECL|field|writefieldWrapfield
specifier|private
specifier|final
name|boolean
name|writefieldWrapfield
decl_stmt|;
DECL|field|doNotResolveStringsFors
specifier|private
specifier|final
name|String
index|[]
name|doNotResolveStringsFors
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
name|valueDelimitersZero
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
name|valueDelimitersOne
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
name|getStringArray
argument_list|(
name|JabRefPreferences
operator|.
name|DO_NOT_RESOLVE_STRINGS_FOR
argument_list|)
expr_stmt|;
name|writefieldWrapfield
operator|=
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|WRITEFIELD_WRAPFIELD
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|format (String text, String fieldName)
specifier|public
name|String
name|format
parameter_list|(
name|String
name|text
parameter_list|,
name|String
name|fieldName
parameter_list|)
throws|throws
name|IllegalArgumentException
block|{
if|if
condition|(
name|text
operator|==
literal|null
condition|)
block|{
return|return
name|valueDelimitersZero
operator|+
literal|""
operator|+
name|valueDelimitersOne
return|;
block|}
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|putBracesAroundCapitals
argument_list|(
name|fieldName
argument_list|)
operator|&&
operator|!
name|Globals
operator|.
name|BIBTEX_STRING
operator|.
name|equals
argument_list|(
name|fieldName
argument_list|)
condition|)
block|{
name|text
operator|=
name|Util
operator|.
name|putBracesAroundCapitals
argument_list|(
name|text
argument_list|)
expr_stmt|;
block|}
comment|// normalize newlines
if|if
condition|(
operator|!
name|text
operator|.
name|contains
argument_list|(
name|Globals
operator|.
name|NEWLINE
argument_list|)
operator|&&
name|text
operator|.
name|contains
argument_list|(
literal|"\n"
argument_list|)
condition|)
block|{
comment|// if we don't have real new lines, but pseudo newlines, we replace them
comment|// On Win 8.1, this is always true for multiline fields
name|text
operator|=
name|text
operator|.
name|replaceAll
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
literal|true
decl_stmt|;
if|if
condition|(
name|resolveStringsAllFields
condition|)
block|{
comment|// Resolve strings for all fields except some:
name|String
index|[]
name|exceptions
init|=
name|doNotResolveStringsFors
decl_stmt|;
for|for
control|(
name|String
name|exception
range|:
name|exceptions
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
name|BibtexFields
operator|.
name|isStandardField
argument_list|(
name|fieldName
argument_list|)
operator|||
name|Globals
operator|.
name|BIBTEX_STRING
operator|.
name|equals
argument_list|(
name|fieldName
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|resolveStrings
condition|)
block|{
name|int
name|brc
init|=
literal|0
decl_stmt|;
name|boolean
name|ok
init|=
literal|true
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
name|text
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
name|text
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
decl_stmt|;
comment|//Util.pr(""+c);
if|if
condition|(
name|c
operator|==
literal|'{'
condition|)
block|{
name|brc
operator|++
expr_stmt|;
block|}
if|if
condition|(
name|c
operator|==
literal|'}'
condition|)
block|{
name|brc
operator|--
expr_stmt|;
block|}
if|if
condition|(
name|brc
operator|<
literal|0
condition|)
block|{
name|ok
operator|=
literal|false
expr_stmt|;
break|break;
block|}
block|}
if|if
condition|(
name|brc
operator|>
literal|0
condition|)
block|{
name|ok
operator|=
literal|false
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|ok
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Curly braces { and } must be balanced."
argument_list|)
throw|;
block|}
name|sb
operator|=
operator|new
name|StringBuffer
argument_list|(
name|valueDelimitersZero
operator|+
literal|""
argument_list|)
expr_stmt|;
comment|// No formatting at all for these fields, to allow custom formatting?
comment|//            if (Globals.prefs.getBoolean("preserveFieldFormatting"))
comment|//              sb.append(text);
comment|//            else
comment|//             currently, we do not do any more wrapping
if|if
condition|(
name|writefieldWrapfield
operator|&&
operator|!
name|Globals
operator|.
name|prefs
operator|.
name|isNonWrappableField
argument_list|(
name|fieldName
argument_list|)
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|StringUtil
operator|.
name|wrap
argument_list|(
name|text
argument_list|,
name|GUIGlobals
operator|.
name|LINE_LENGTH
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|sb
operator|.
name|append
argument_list|(
name|text
argument_list|)
expr_stmt|;
block|}
name|sb
operator|.
name|append
argument_list|(
name|valueDelimitersOne
argument_list|)
expr_stmt|;
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
name|sb
operator|=
operator|new
name|StringBuffer
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
name|text
argument_list|)
expr_stmt|;
while|while
condition|(
name|pivot
operator|<
name|text
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
name|text
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
name|pos1
operator|>
literal|0
operator|&&
name|text
operator|.
name|charAt
argument_list|(
name|pos1
operator|-
literal|1
argument_list|)
operator|==
literal|'\\'
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
name|text
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
name|text
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
operator|!
name|neverFailOnHashes
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"The # character is not allowed in BibTeX strings unless escaped as in '\\#'."
argument_list|)
operator|+
literal|'\n'
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"In JabRef, use pairs of # characters to indicate a string."
argument_list|)
operator|+
literal|'\n'
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"Note that the entry causing the problem has been selected."
argument_list|)
argument_list|)
throw|;
block|}
else|else
block|{
name|pos1
operator|=
name|text
operator|.
name|length
argument_list|()
expr_stmt|;
comment|// just write out the rest of the text, and throw no exception
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
name|text
argument_list|,
name|pivot
argument_list|,
name|pos1
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|pos1
argument_list|<
name|text
operator|.
name|length
operator|(
operator|)
operator|&&
name|pos2
operator|-
literal|1
argument_list|>
name|pos1
condition|)
block|{
comment|// We check that the string label is not empty. That means
comment|// an occurrence of ## will simply be ignored. Should it instead
comment|// cause an error message?
name|writeStringLabel
argument_list|(
name|text
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
name|pos2
operator|+
literal|1
operator|==
name|text
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
comment|// currently, we do not add newlines and new formatting
if|if
condition|(
name|writefieldWrapfield
operator|&&
operator|!
name|Globals
operator|.
name|prefs
operator|.
name|isNonWrappableField
argument_list|(
name|fieldName
argument_list|)
condition|)
block|{
comment|//             introduce a line break to be read at the parser
return|return
name|StringUtil
operator|.
name|wrap
argument_list|(
name|sb
operator|.
name|toString
argument_list|()
argument_list|,
name|GUIGlobals
operator|.
name|LINE_LENGTH
argument_list|)
return|;
comment|//, but that lead to ugly .tex
block|}
else|else
block|{
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
DECL|method|writeText (String text, int start_pos, int end_pos)
specifier|private
name|void
name|writeText
parameter_list|(
name|String
name|text
parameter_list|,
name|int
name|start_pos
parameter_list|,
name|int
name|end_pos
parameter_list|)
block|{
comment|/*sb.append("{");         sb.append(text.substring(start_pos, end_pos));         sb.append("}");*/
name|sb
operator|.
name|append
argument_list|(
name|valueDelimitersZero
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
name|start_pos
init|;
name|i
operator|<
name|end_pos
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
comment|//System.out.println("whitespace here");
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
comment|// Or the end of an argument:
elseif|else
if|if
condition|(
name|inCommandOption
operator|&&
name|c
operator|==
literal|']'
condition|)
block|{
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
name|c
operator|==
literal|'{'
condition|)
block|{
comment|//System.out.println("Read command: '"+commandName.toString()+"'");
name|inCommandName
operator|=
literal|false
expr_stmt|;
name|inCommand
operator|=
literal|true
expr_stmt|;
block|}
comment|// Or simply the end of this command altogether:
else|else
block|{
comment|//System.out.println("I think I read command: '"+commandName.toString()+"'");
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
name|c
operator|==
literal|'}'
condition|)
block|{
comment|//System.out.println("nestedEnvironments = " + nestedEnvironments);
comment|//System.out.println("Done with command: '"+commandName.toString()+"'");
if|if
condition|(
name|commandName
operator|.
name|toString
argument_list|()
operator|.
name|equals
argument_list|(
literal|"begin"
argument_list|)
condition|)
block|{
name|nestedEnvironments
operator|++
expr_stmt|;
block|}
if|if
condition|(
name|nestedEnvironments
operator|>
literal|0
operator|&&
name|commandName
operator|.
name|toString
argument_list|()
operator|.
name|equals
argument_list|(
literal|"end"
argument_list|)
condition|)
block|{
name|nestedEnvironments
operator|--
expr_stmt|;
block|}
comment|//System.out.println("nestedEnvironments = " + nestedEnvironments);
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
name|c
operator|==
literal|'&'
operator|&&
operator|!
name|escape
operator|&&
operator|!
operator|(
name|inCommand
operator|&&
name|commandName
operator|.
name|toString
argument_list|()
operator|.
name|equals
argument_list|(
literal|"url"
argument_list|)
operator|)
operator|&&
name|nestedEnvironments
operator|==
literal|0
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|"\\&"
argument_list|)
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
name|escape
operator|=
name|c
operator|==
literal|'\\'
expr_stmt|;
block|}
name|sb
operator|.
name|append
argument_list|(
name|valueDelimitersOne
argument_list|)
expr_stmt|;
block|}
DECL|method|writeStringLabel (String text, int start_pos, int end_pos, boolean first, boolean last)
specifier|private
name|void
name|writeStringLabel
parameter_list|(
name|String
name|text
parameter_list|,
name|int
name|start_pos
parameter_list|,
name|int
name|end_pos
parameter_list|,
name|boolean
name|first
parameter_list|,
name|boolean
name|last
parameter_list|)
block|{
comment|//sb.append(Util.wrap((first ? "" : " # ") + text.substring(start_pos, end_pos)
comment|//		     + (last ? "" : " # "), GUIGlobals.LINE_LENGTH));
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
name|start_pos
argument_list|,
name|end_pos
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
name|sb
operator|.
name|append
argument_list|(
name|StringUtil
operator|.
name|wrap
argument_list|(
name|s
argument_list|,
name|GUIGlobals
operator|.
name|LINE_LENGTH
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|checkBraces (String text)
specifier|private
name|void
name|checkBraces
parameter_list|(
name|String
name|text
parameter_list|)
throws|throws
name|IllegalArgumentException
block|{
name|Vector
argument_list|<
name|Integer
argument_list|>
name|left
init|=
operator|new
name|Vector
argument_list|<
name|Integer
argument_list|>
argument_list|(
literal|5
argument_list|,
literal|3
argument_list|)
decl_stmt|;
name|Vector
argument_list|<
name|Integer
argument_list|>
name|right
init|=
operator|new
name|Vector
argument_list|<
name|Integer
argument_list|>
argument_list|(
literal|5
argument_list|,
literal|3
argument_list|)
decl_stmt|;
name|int
name|current
init|=
operator|-
literal|1
decl_stmt|;
comment|// First we collect all occurences:
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
name|right
operator|.
name|elementAt
argument_list|(
literal|0
argument_list|)
operator|<
name|left
operator|.
name|elementAt
argument_list|(
literal|0
argument_list|)
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

