begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* Copyright (C) 2003 Nizar N. Batada, Morten O. Alver  All programs in this directory and subdirectories are published under the GNU General Public License as described below.  This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.  You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA  Further information about the GNU GPL is available at: http://www.gnu.org/copyleft/gpl.ja.html  */
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
name|java
operator|.
name|util
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
name|*
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
DECL|field|sb
name|StringBuffer
name|sb
decl_stmt|;
DECL|field|col
name|int
name|col
decl_stmt|;
comment|// First line usually starts about so much further to the right.
DECL|field|STARTCOL
specifier|final
name|int
name|STARTCOL
init|=
literal|4
decl_stmt|;
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
comment|// If the field is non-standard, we will just append braces,
comment|// wrap and write.
name|boolean
name|resolveStrings
init|=
literal|true
decl_stmt|;
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"resolveStringsAllFields"
argument_list|)
condition|)
block|{
comment|// Resolve strings for all fields except some:
name|String
index|[]
name|exceptions
init|=
name|Globals
operator|.
name|prefs
operator|.
name|getStringArray
argument_list|(
literal|"doNotResolveStringsFor"
argument_list|)
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
name|exceptions
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|exceptions
index|[
name|i
index|]
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
name|brc
operator|++
expr_stmt|;
if|if
condition|(
name|c
operator|==
literal|'}'
condition|)
name|brc
operator|--
expr_stmt|;
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
name|ok
operator|=
literal|false
expr_stmt|;
if|if
condition|(
operator|!
name|ok
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Curly braces { and } must be balanced."
argument_list|)
throw|;
name|sb
operator|=
operator|new
name|StringBuffer
argument_list|(
name|Globals
operator|.
name|getOpeningBrace
argument_list|()
argument_list|)
expr_stmt|;
comment|// No formatting at all for these fields, to allow custom formatting?
comment|//if (Globals.prefs.getBoolean("preserveFieldFormatting"))
comment|//  sb.append(text);
comment|//else
if|if
condition|(
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
name|sb
operator|.
name|append
argument_list|(
name|Util
operator|.
name|wrap2
argument_list|(
name|text
argument_list|,
name|GUIGlobals
operator|.
name|LINE_LENGTH
argument_list|)
argument_list|)
expr_stmt|;
else|else
name|sb
operator|.
name|append
argument_list|(
name|text
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|Globals
operator|.
name|getClosingBrace
argument_list|()
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
decl_stmt|,
name|pos1
decl_stmt|,
name|pos2
decl_stmt|;
name|col
operator|=
name|STARTCOL
expr_stmt|;
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
operator|(
name|pos1
operator|>
literal|0
operator|)
operator|&&
operator|(
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
name|goFrom
operator|=
name|pos1
operator|-
literal|1
expr_stmt|;
comment|// Ends the loop.
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
comment|// No more occurences found.
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
comment|//System.out.println("pos2:"+pos2);
if|if
condition|(
name|pos2
operator|==
operator|-
literal|1
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"The # character is not allowed in BibTeX fields"
argument_list|)
operator|+
literal|".\n"
operator|+
name|Globals
operator|.
name|lang
argument_list|(
literal|"In JabRef, use pairs of # characters to indicate "
operator|+
literal|"a string."
argument_list|)
operator|+
literal|"\n"
operator|+
name|Globals
operator|.
name|lang
argument_list|(
literal|"Note that the entry causing the problem has been selected."
argument_list|)
argument_list|)
throw|;
block|}
block|}
if|if
condition|(
name|pos1
operator|>
name|pivot
condition|)
name|writeText
argument_list|(
name|text
argument_list|,
name|pivot
argument_list|,
name|pos1
argument_list|)
expr_stmt|;
if|if
condition|(
operator|(
name|pos1
operator|<
name|text
operator|.
name|length
argument_list|()
operator|)
operator|&&
operator|(
name|pos2
operator|-
literal|1
operator|>
name|pos1
operator|)
condition|)
comment|// We check that the string label is not empty. That means
comment|// an occurence of ## will simply be ignored. Should it instead
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
operator|(
name|pos1
operator|==
name|pivot
operator|)
argument_list|,
operator|(
name|pos2
operator|+
literal|1
operator|==
name|text
operator|.
name|length
argument_list|()
operator|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|pos2
operator|>
operator|-
literal|1
condition|)
name|pivot
operator|=
name|pos2
operator|+
literal|1
expr_stmt|;
else|else
name|pivot
operator|=
name|pos1
operator|+
literal|1
expr_stmt|;
comment|//if (tell++> 10) System.exit(0);
block|}
if|if
condition|(
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
return|return
name|Util
operator|.
name|wrap2
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
else|else
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
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
comment|/*sb.append("{");      sb.append(text.substring(start_pos, end_pos));      sb.append("}");*/
name|sb
operator|.
name|append
argument_list|(
name|Globals
operator|.
name|getOpeningBrace
argument_list|()
argument_list|)
expr_stmt|;
name|boolean
name|escape
init|=
literal|false
decl_stmt|,
name|inCommandName
init|=
literal|false
decl_stmt|,
name|inCommand
init|=
literal|false
decl_stmt|,
name|inCommandOption
init|=
literal|false
decl_stmt|;
name|int
name|nestedEnvironments
init|=
literal|0
decl_stmt|;
name|StringBuffer
name|commandName
init|=
operator|new
name|StringBuffer
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
name|commandName
operator|.
name|append
argument_list|(
operator|(
name|char
operator|)
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
operator|(
name|c
operator|==
literal|']'
operator|)
condition|)
name|inCommandOption
operator|=
literal|false
expr_stmt|;
comment|// Or the beginning of the command body:
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
operator|(
name|c
operator|==
literal|'}'
operator|)
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
operator|(
name|nestedEnvironments
operator|==
literal|0
operator|)
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
name|sb
operator|.
name|append
argument_list|(
name|c
argument_list|)
expr_stmt|;
name|escape
operator|=
operator|(
name|c
operator|==
literal|'\\'
operator|)
expr_stmt|;
block|}
name|sb
operator|.
name|append
argument_list|(
name|Globals
operator|.
name|getClosingBrace
argument_list|()
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
comment|//sb.append(Util.wrap2((first ? "" : " # ") + text.substring(start_pos, end_pos)
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
name|Util
operator|.
name|wrap2
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
name|left
init|=
operator|new
name|Vector
argument_list|(
literal|5
argument_list|,
literal|3
argument_list|)
decl_stmt|,
name|right
init|=
operator|new
name|Vector
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
name|left
operator|.
name|add
argument_list|(
operator|new
name|Integer
argument_list|(
name|current
argument_list|)
argument_list|)
expr_stmt|;
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
name|right
operator|.
name|add
argument_list|(
operator|new
name|Integer
argument_list|(
name|current
argument_list|)
argument_list|)
expr_stmt|;
comment|// Then we throw an exception if the error criteria are met.
if|if
condition|(
operator|(
name|right
operator|.
name|size
argument_list|()
operator|>
literal|0
operator|)
operator|&&
operator|(
name|left
operator|.
name|size
argument_list|()
operator|==
literal|0
operator|)
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"'}' character ends string prematurely."
argument_list|)
throw|;
if|if
condition|(
operator|(
name|right
operator|.
name|size
argument_list|()
operator|>
literal|0
operator|)
operator|&&
operator|(
operator|(
operator|(
name|Integer
operator|)
name|right
operator|.
name|elementAt
argument_list|(
literal|0
argument_list|)
operator|)
operator|.
name|intValue
argument_list|()
operator|<
operator|(
operator|(
name|Integer
operator|)
name|left
operator|.
name|elementAt
argument_list|(
literal|0
argument_list|)
operator|)
operator|.
name|intValue
argument_list|()
operator|)
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"'}' character ends string prematurely."
argument_list|)
throw|;
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
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Braces don't match."
argument_list|)
throw|;
block|}
block|}
end_class

end_unit

