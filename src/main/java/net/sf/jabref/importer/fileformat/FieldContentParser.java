begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.importer.fileformat
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|importer
operator|.
name|fileformat
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
name|gui
operator|.
name|GUIGlobals
import|;
end_import

begin_comment
comment|/**  * This class provides the reformatting needed when reading BibTeX fields formatted  * in JabRef style. The reformatting must undo all formatting done by JabRef when  * writing the same fields.  */
end_comment

begin_class
DECL|class|FieldContentParser
class|class
name|FieldContentParser
block|{
comment|/**      * Performs the reformatting      * @param content StringBuffer containing the field to format. key contains field name according to field      *  was edited by Kuehn/Havalevich      * @return The formatted field content. NOTE: the StringBuffer returned may      * or may not be the same as the argument given.      */
DECL|method|format (StringBuffer content, String key)
specifier|public
name|StringBuffer
name|format
parameter_list|(
name|StringBuffer
name|content
parameter_list|,
name|String
name|key
parameter_list|)
block|{
name|int
name|i
init|=
literal|0
decl_stmt|;
comment|// Remove windows newlines and insert unix ones:
comment|// TODO: 2005.12.3: Added replace from \r to \n, to work around a reported problem of words stiched together.
comment|// But: we need to find out why these lone \r characters appear in his file.
name|content
operator|=
operator|new
name|StringBuffer
argument_list|(
name|content
operator|.
name|toString
argument_list|()
operator|.
name|replaceAll
argument_list|(
literal|"\r\n"
argument_list|,
literal|"\n"
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"\r"
argument_list|,
literal|"\n"
argument_list|)
argument_list|)
expr_stmt|;
while|while
condition|(
name|i
operator|<
name|content
operator|.
name|length
argument_list|()
condition|)
block|{
name|int
name|c
init|=
name|content
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
literal|'\n'
condition|)
block|{
comment|// @formatter:off
if|if
condition|(
name|content
operator|.
name|length
argument_list|()
operator|>
name|i
operator|+
literal|1
operator|&&
name|content
operator|.
name|charAt
argument_list|(
name|i
operator|+
literal|1
argument_list|)
operator|==
literal|'\t'
operator|&&
operator|(
name|content
operator|.
name|length
argument_list|()
operator|==
name|i
operator|+
literal|2
operator|||
operator|!
name|Character
operator|.
name|isWhitespace
argument_list|(
name|content
operator|.
name|charAt
argument_list|(
name|i
operator|+
literal|2
argument_list|)
argument_list|)
operator|)
condition|)
block|{
comment|// We have either \n\t followed by non-whitespace, or \n\t at the
comment|// end. Both cases indicate a wrap made by JabRef. Remove and insert space if necessary.
comment|// @formatter:on
name|content
operator|.
name|deleteCharAt
argument_list|(
name|i
argument_list|)
expr_stmt|;
comment|// \n
name|content
operator|.
name|deleteCharAt
argument_list|(
name|i
argument_list|)
expr_stmt|;
comment|// \t
comment|// Add space only if necessary:
comment|// Note 2007-05-26, mortenalver: the following line was modified. It previously
comment|// didn't add a space if the line break was at i==0. This caused some occurences of
comment|// "string1 # { and } # string2" constructs lose the space in front of the "and" because
comment|// the line wrap caused a JabRef line break at the start of a value containing the " and ".
comment|// The bug was caused by a protective check for i>0 to avoid intexing char -1 in content.
if|if
condition|(
name|i
operator|==
literal|0
operator|||
operator|!
name|Character
operator|.
name|isWhitespace
argument_list|(
name|content
operator|.
name|charAt
argument_list|(
name|i
operator|-
literal|1
argument_list|)
argument_list|)
condition|)
block|{
name|content
operator|.
name|insert
argument_list|(
name|i
argument_list|,
literal|' '
argument_list|)
expr_stmt|;
comment|// Increment i because of the inserted character:
name|i
operator|++
expr_stmt|;
block|}
comment|// @formatter:off
block|}
elseif|else
if|if
condition|(
name|content
operator|.
name|length
argument_list|()
operator|>
name|i
operator|+
literal|3
operator|&&
name|content
operator|.
name|charAt
argument_list|(
name|i
operator|+
literal|1
argument_list|)
operator|==
literal|'\t'
operator|&&
name|content
operator|.
name|charAt
argument_list|(
name|i
operator|+
literal|2
argument_list|)
operator|==
literal|' '
operator|&&
operator|!
name|Character
operator|.
name|isWhitespace
argument_list|(
name|content
operator|.
name|charAt
argument_list|(
name|i
operator|+
literal|3
argument_list|)
argument_list|)
condition|)
block|{
comment|// We have \n\t followed by ' ' followed by non-whitespace, which indicates
comment|// a wrap made by JabRef<= 1.7.1. Remove:
comment|// @formatter:on
name|content
operator|.
name|deleteCharAt
argument_list|(
name|i
argument_list|)
expr_stmt|;
comment|// \n
name|content
operator|.
name|deleteCharAt
argument_list|(
name|i
argument_list|)
expr_stmt|;
comment|// \t
comment|// Remove space only if necessary:
if|if
condition|(
name|i
operator|>
literal|0
operator|&&
name|Character
operator|.
name|isWhitespace
argument_list|(
name|content
operator|.
name|charAt
argument_list|(
name|i
operator|-
literal|1
argument_list|)
argument_list|)
condition|)
block|{
name|content
operator|.
name|deleteCharAt
argument_list|(
name|i
argument_list|)
expr_stmt|;
block|}
comment|// @formatter:off
block|}
elseif|else
if|if
condition|(
name|content
operator|.
name|length
argument_list|()
operator|>
name|i
operator|+
literal|3
operator|&&
name|content
operator|.
name|charAt
argument_list|(
name|i
operator|+
literal|1
argument_list|)
operator|==
literal|'\t'
operator|&&
name|content
operator|.
name|charAt
argument_list|(
name|i
operator|+
literal|2
argument_list|)
operator|==
literal|'\n'
operator|&&
name|content
operator|.
name|charAt
argument_list|(
name|i
operator|+
literal|3
argument_list|)
operator|==
literal|'\t'
condition|)
block|{
comment|// We have \n\t\n\t, which looks like a JabRef-formatted empty line.
comment|// Remove the tabs and keep one of the line breaks:
comment|// @formatter:on
name|content
operator|.
name|deleteCharAt
argument_list|(
name|i
operator|+
literal|1
argument_list|)
expr_stmt|;
comment|// \t
name|content
operator|.
name|deleteCharAt
argument_list|(
name|i
operator|+
literal|1
argument_list|)
expr_stmt|;
comment|// \n
name|content
operator|.
name|deleteCharAt
argument_list|(
name|i
operator|+
literal|1
argument_list|)
expr_stmt|;
comment|// \t
comment|// Skip past the line breaks:
name|i
operator|++
expr_stmt|;
comment|// Now, if more \n\t pairs are following, keep each line break. This
comment|// preserves several line breaks properly. Repeat until done:
while|while
condition|(
name|content
operator|.
name|length
argument_list|()
operator|>
name|i
operator|+
literal|1
operator|&&
name|content
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
operator|==
literal|'\n'
operator|&&
name|content
operator|.
name|charAt
argument_list|(
name|i
operator|+
literal|1
argument_list|)
operator|==
literal|'\t'
condition|)
block|{
name|content
operator|.
name|deleteCharAt
argument_list|(
name|i
operator|+
literal|1
argument_list|)
expr_stmt|;
name|i
operator|++
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|content
operator|.
name|length
argument_list|()
operator|>
name|i
operator|+
literal|1
operator|&&
name|content
operator|.
name|charAt
argument_list|(
name|i
operator|+
literal|1
argument_list|)
operator|!=
literal|'\n'
condition|)
block|{
comment|// We have a line break not followed by another line break.
comment|// Interpretation before JabRef 2.10:
comment|//   line break made by whatever other editor, so we will remove the line break.
comment|// Current interpretation:
comment|//   keep line break
name|i
operator|++
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|content
operator|.
name|length
argument_list|()
operator|>
name|i
operator|+
literal|1
operator|&&
name|content
operator|.
name|charAt
argument_list|(
name|i
operator|+
literal|1
argument_list|)
operator|==
literal|'\n'
condition|)
block|{
comment|// we have a line break followed by another line break.
comment|// This is a linebreak was manually input by the user
comment|// Handling before JabRef 2.10:
comment|//   just delete the additional linebreak
comment|//   content.deleteCharAt(i+1);
comment|// Current interpretation:
comment|//   keep line break
name|i
operator|++
expr_stmt|;
comment|// do not handle \n again
name|i
operator|++
expr_stmt|;
block|}
else|else
block|{
name|i
operator|++
expr_stmt|;
comment|//content.deleteCharAt(i);
block|}
block|}
elseif|else
if|if
condition|(
name|c
operator|==
literal|' '
condition|)
block|{
comment|//if ((content.length()>i+2)&& (content.charAt(i+1)==' ')) {
if|if
condition|(
name|i
operator|>
literal|0
operator|&&
name|content
operator|.
name|charAt
argument_list|(
name|i
operator|-
literal|1
argument_list|)
operator|==
literal|' '
condition|)
block|{
comment|// We have two spaces in a row. Don't include this one.
comment|// Yes, of course we have, but in Filenames it is necessary to have all spaces. :-)
comment|// This is the reason why the next lines are required
comment|// FIXME: just don't edit some fields rather than hacking every exception?
if|if
condition|(
name|key
operator|!=
literal|null
operator|&&
name|key
operator|.
name|equals
argument_list|(
name|GUIGlobals
operator|.
name|FILE_FIELD
argument_list|)
condition|)
block|{
name|i
operator|++
expr_stmt|;
block|}
else|else
block|{
name|content
operator|.
name|deleteCharAt
argument_list|(
name|i
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|i
operator|++
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|c
operator|==
literal|'\t'
condition|)
block|{
comment|// Remove all tab characters that aren't associated with a line break.
name|content
operator|.
name|deleteCharAt
argument_list|(
name|i
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|i
operator|++
expr_stmt|;
block|}
block|}
return|return
name|content
return|;
block|}
comment|/**      * Performs the reformatting      * @param content StringBuffer containing the field to format.      * @return The formatted field content. NOTE: the StringBuffer returned may      * or may not be the same as the argument given.      */
DECL|method|format (StringBuffer content)
specifier|public
name|StringBuffer
name|format
parameter_list|(
name|StringBuffer
name|content
parameter_list|)
block|{
return|return
name|format
argument_list|(
name|content
argument_list|,
literal|null
argument_list|)
return|;
block|}
block|}
end_class

end_unit

