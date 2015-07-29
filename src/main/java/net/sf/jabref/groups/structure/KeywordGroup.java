begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.groups.structure
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|groups
operator|.
name|structure
package|;
end_package

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
name|javax
operator|.
name|swing
operator|.
name|undo
operator|.
name|AbstractUndoableEdit
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

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|search
operator|.
name|SearchRule
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
name|undo
operator|.
name|NamedCompound
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
name|undo
operator|.
name|UndoableFieldChange
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
name|QuotedStringTokenizer
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
name|StringUtil
import|;
end_import

begin_comment
comment|/**  * @author jzieren  */
end_comment

begin_class
DECL|class|KeywordGroup
specifier|public
class|class
name|KeywordGroup
extends|extends
name|AbstractGroup
block|{
DECL|field|ID
specifier|public
specifier|static
specifier|final
name|String
name|ID
init|=
literal|"KeywordGroup:"
decl_stmt|;
DECL|field|searchField
specifier|private
specifier|final
name|String
name|searchField
decl_stmt|;
DECL|field|searchExpression
specifier|private
specifier|final
name|String
name|searchExpression
decl_stmt|;
DECL|field|caseSensitive
specifier|private
specifier|final
name|boolean
name|caseSensitive
decl_stmt|;
DECL|field|regExp
specifier|private
specifier|final
name|boolean
name|regExp
decl_stmt|;
DECL|field|pattern
specifier|private
name|Pattern
name|pattern
init|=
literal|null
decl_stmt|;
comment|/**      * Creates a KeywordGroup with the specified properties.      */
DECL|method|KeywordGroup (String name, String searchField, String searchExpression, boolean caseSensitive, boolean regExp, GroupHierarchyType context)
specifier|public
name|KeywordGroup
parameter_list|(
name|String
name|name
parameter_list|,
name|String
name|searchField
parameter_list|,
name|String
name|searchExpression
parameter_list|,
name|boolean
name|caseSensitive
parameter_list|,
name|boolean
name|regExp
parameter_list|,
name|GroupHierarchyType
name|context
parameter_list|)
throws|throws
name|IllegalArgumentException
block|{
name|super
argument_list|(
name|name
argument_list|,
name|context
argument_list|)
expr_stmt|;
name|this
operator|.
name|searchField
operator|=
name|searchField
expr_stmt|;
name|this
operator|.
name|searchExpression
operator|=
name|searchExpression
expr_stmt|;
name|this
operator|.
name|caseSensitive
operator|=
name|caseSensitive
expr_stmt|;
name|this
operator|.
name|regExp
operator|=
name|regExp
expr_stmt|;
if|if
condition|(
name|this
operator|.
name|regExp
condition|)
block|{
name|compilePattern
argument_list|()
expr_stmt|;
block|}
block|}
DECL|method|compilePattern ()
specifier|private
name|void
name|compilePattern
parameter_list|()
throws|throws
name|IllegalArgumentException
block|{
name|pattern
operator|=
name|caseSensitive
condition|?
name|Pattern
operator|.
name|compile
argument_list|(
literal|"\\b"
operator|+
name|searchExpression
operator|+
literal|"\\b"
argument_list|)
else|:
name|Pattern
operator|.
name|compile
argument_list|(
literal|"\\b"
operator|+
name|searchExpression
operator|+
literal|"\\b"
argument_list|,
name|Pattern
operator|.
name|CASE_INSENSITIVE
argument_list|)
expr_stmt|;
block|}
comment|/**      * Parses s and recreates the KeywordGroup from it.      *      * @param s The String representation obtained from      *          KeywordGroup.toString()      */
DECL|method|fromString (String s, BibtexDatabase db, int version)
specifier|public
specifier|static
name|AbstractGroup
name|fromString
parameter_list|(
name|String
name|s
parameter_list|,
name|BibtexDatabase
name|db
parameter_list|,
name|int
name|version
parameter_list|)
throws|throws
name|Exception
block|{
if|if
condition|(
operator|!
name|s
operator|.
name|startsWith
argument_list|(
name|KeywordGroup
operator|.
name|ID
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Internal error: KeywordGroup cannot be created from \""
operator|+
name|s
operator|+
literal|"\". "
operator|+
literal|"Please report this on www.sf.net/projects/jabref"
argument_list|)
throw|;
block|}
name|QuotedStringTokenizer
name|tok
init|=
operator|new
name|QuotedStringTokenizer
argument_list|(
name|s
operator|.
name|substring
argument_list|(
name|KeywordGroup
operator|.
name|ID
operator|.
name|length
argument_list|()
argument_list|)
argument_list|,
name|AbstractGroup
operator|.
name|SEPARATOR
argument_list|,
name|AbstractGroup
operator|.
name|QUOTE_CHAR
argument_list|)
decl_stmt|;
switch|switch
condition|(
name|version
condition|)
block|{
case|case
literal|0
case|:
block|{
name|String
name|name
init|=
name|tok
operator|.
name|nextToken
argument_list|()
decl_stmt|;
name|String
name|field
init|=
name|tok
operator|.
name|nextToken
argument_list|()
decl_stmt|;
name|String
name|expression
init|=
name|tok
operator|.
name|nextToken
argument_list|()
decl_stmt|;
comment|// assume caseSensitive=false and regExp=true for old groups
return|return
operator|new
name|KeywordGroup
argument_list|(
name|StringUtil
operator|.
name|unquote
argument_list|(
name|name
argument_list|,
name|AbstractGroup
operator|.
name|QUOTE_CHAR
argument_list|)
argument_list|,
name|StringUtil
operator|.
name|unquote
argument_list|(
name|field
argument_list|,
name|AbstractGroup
operator|.
name|QUOTE_CHAR
argument_list|)
argument_list|,
name|StringUtil
operator|.
name|unquote
argument_list|(
name|expression
argument_list|,
name|AbstractGroup
operator|.
name|QUOTE_CHAR
argument_list|)
argument_list|,
literal|false
argument_list|,
literal|true
argument_list|,
name|GroupHierarchyType
operator|.
name|INDEPENDENT
argument_list|)
return|;
block|}
case|case
literal|1
case|:
case|case
literal|2
case|:
block|{
name|String
name|name
init|=
name|tok
operator|.
name|nextToken
argument_list|()
decl_stmt|;
name|String
name|field
init|=
name|tok
operator|.
name|nextToken
argument_list|()
decl_stmt|;
name|String
name|expression
init|=
name|tok
operator|.
name|nextToken
argument_list|()
decl_stmt|;
name|boolean
name|caseSensitive
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|tok
operator|.
name|nextToken
argument_list|()
argument_list|)
operator|==
literal|1
decl_stmt|;
name|boolean
name|regExp
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|tok
operator|.
name|nextToken
argument_list|()
argument_list|)
operator|==
literal|1
decl_stmt|;
return|return
operator|new
name|KeywordGroup
argument_list|(
name|StringUtil
operator|.
name|unquote
argument_list|(
name|name
argument_list|,
name|AbstractGroup
operator|.
name|QUOTE_CHAR
argument_list|)
argument_list|,
name|StringUtil
operator|.
name|unquote
argument_list|(
name|field
argument_list|,
name|AbstractGroup
operator|.
name|QUOTE_CHAR
argument_list|)
argument_list|,
name|StringUtil
operator|.
name|unquote
argument_list|(
name|expression
argument_list|,
name|AbstractGroup
operator|.
name|QUOTE_CHAR
argument_list|)
argument_list|,
name|caseSensitive
argument_list|,
name|regExp
argument_list|,
name|GroupHierarchyType
operator|.
name|INDEPENDENT
argument_list|)
return|;
block|}
case|case
literal|3
case|:
block|{
name|String
name|name
init|=
name|tok
operator|.
name|nextToken
argument_list|()
decl_stmt|;
name|int
name|context
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|tok
operator|.
name|nextToken
argument_list|()
argument_list|)
decl_stmt|;
name|String
name|field
init|=
name|tok
operator|.
name|nextToken
argument_list|()
decl_stmt|;
name|String
name|expression
init|=
name|tok
operator|.
name|nextToken
argument_list|()
decl_stmt|;
name|boolean
name|caseSensitive
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|tok
operator|.
name|nextToken
argument_list|()
argument_list|)
operator|==
literal|1
decl_stmt|;
name|boolean
name|regExp
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|tok
operator|.
name|nextToken
argument_list|()
argument_list|)
operator|==
literal|1
decl_stmt|;
return|return
operator|new
name|KeywordGroup
argument_list|(
name|StringUtil
operator|.
name|unquote
argument_list|(
name|name
argument_list|,
name|AbstractGroup
operator|.
name|QUOTE_CHAR
argument_list|)
argument_list|,
name|StringUtil
operator|.
name|unquote
argument_list|(
name|field
argument_list|,
name|AbstractGroup
operator|.
name|QUOTE_CHAR
argument_list|)
argument_list|,
name|StringUtil
operator|.
name|unquote
argument_list|(
name|expression
argument_list|,
name|AbstractGroup
operator|.
name|QUOTE_CHAR
argument_list|)
argument_list|,
name|caseSensitive
argument_list|,
name|regExp
argument_list|,
name|GroupHierarchyType
operator|.
name|getByNumber
argument_list|(
name|context
argument_list|)
argument_list|)
return|;
block|}
default|default:
throw|throw
operator|new
name|UnsupportedVersionException
argument_list|(
literal|"KeywordGroup"
argument_list|,
name|version
argument_list|)
throw|;
block|}
block|}
comment|/**      * @see AbstractGroup#getSearchRule()      */
annotation|@
name|Override
DECL|method|getSearchRule ()
specifier|public
name|SearchRule
name|getSearchRule
parameter_list|()
block|{
return|return
operator|new
name|SearchRule
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|boolean
name|applyRule
parameter_list|(
name|String
name|query
parameter_list|,
name|BibtexEntry
name|bibtexEntry
parameter_list|)
block|{
return|return
name|contains
argument_list|(
name|query
argument_list|,
name|bibtexEntry
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|validateSearchStrings
parameter_list|(
name|String
name|query
parameter_list|)
block|{
return|return
literal|true
return|;
block|}
block|}
return|;
block|}
comment|/**      * Returns a String representation of this object that can be used to      * reconstruct it.      */
annotation|@
name|Override
DECL|method|toString ()
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|KeywordGroup
operator|.
name|ID
operator|+
name|StringUtil
operator|.
name|quote
argument_list|(
name|name
argument_list|,
name|AbstractGroup
operator|.
name|SEPARATOR
argument_list|,
name|AbstractGroup
operator|.
name|QUOTE_CHAR
argument_list|)
operator|+
name|AbstractGroup
operator|.
name|SEPARATOR
operator|+
name|context
operator|.
name|ordinal
argument_list|()
operator|+
name|AbstractGroup
operator|.
name|SEPARATOR
operator|+
name|StringUtil
operator|.
name|quote
argument_list|(
name|searchField
argument_list|,
name|AbstractGroup
operator|.
name|SEPARATOR
argument_list|,
name|AbstractGroup
operator|.
name|QUOTE_CHAR
argument_list|)
operator|+
name|AbstractGroup
operator|.
name|SEPARATOR
operator|+
name|StringUtil
operator|.
name|quote
argument_list|(
name|searchExpression
argument_list|,
name|AbstractGroup
operator|.
name|SEPARATOR
argument_list|,
name|AbstractGroup
operator|.
name|QUOTE_CHAR
argument_list|)
operator|+
name|AbstractGroup
operator|.
name|SEPARATOR
operator|+
name|StringUtil
operator|.
name|booleanToBinaryString
argument_list|(
name|caseSensitive
argument_list|)
operator|+
name|AbstractGroup
operator|.
name|SEPARATOR
operator|+
name|StringUtil
operator|.
name|booleanToBinaryString
argument_list|(
name|regExp
argument_list|)
operator|+
name|AbstractGroup
operator|.
name|SEPARATOR
return|;
block|}
annotation|@
name|Override
DECL|method|supportsAdd ()
specifier|public
name|boolean
name|supportsAdd
parameter_list|()
block|{
return|return
operator|!
name|regExp
return|;
block|}
annotation|@
name|Override
DECL|method|supportsRemove ()
specifier|public
name|boolean
name|supportsRemove
parameter_list|()
block|{
return|return
operator|!
name|regExp
return|;
block|}
annotation|@
name|Override
DECL|method|add (BibtexEntry[] entries)
specifier|public
name|AbstractUndoableEdit
name|add
parameter_list|(
name|BibtexEntry
index|[]
name|entries
parameter_list|)
block|{
if|if
condition|(
operator|!
name|supportsAdd
argument_list|()
condition|)
block|{
return|return
literal|null
return|;
block|}
if|if
condition|(
name|entries
operator|!=
literal|null
operator|&&
name|entries
operator|.
name|length
operator|>
literal|0
condition|)
block|{
name|NamedCompound
name|ce
init|=
operator|new
name|NamedCompound
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"add entries to group"
argument_list|)
argument_list|)
decl_stmt|;
name|boolean
name|modified
init|=
literal|false
decl_stmt|;
for|for
control|(
name|BibtexEntry
name|entry
range|:
name|entries
control|)
block|{
if|if
condition|(
operator|!
name|getSearchRule
argument_list|()
operator|.
name|applyRule
argument_list|(
name|SearchRule
operator|.
name|NULL_QUERY
argument_list|,
name|entry
argument_list|)
condition|)
block|{
name|String
name|oldContent
init|=
name|entry
operator|.
name|getField
argument_list|(
name|searchField
argument_list|)
decl_stmt|,
name|pre
init|=
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|GROUP_KEYWORD_SEPARATOR
argument_list|)
decl_stmt|;
name|String
name|newContent
init|=
operator|(
name|oldContent
operator|==
literal|null
condition|?
literal|""
else|:
name|oldContent
operator|+
name|pre
operator|)
operator|+
name|searchExpression
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|searchField
argument_list|,
name|newContent
argument_list|)
expr_stmt|;
comment|// Store undo information.
name|ce
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableFieldChange
argument_list|(
name|entry
argument_list|,
name|searchField
argument_list|,
name|oldContent
argument_list|,
name|newContent
argument_list|)
argument_list|)
expr_stmt|;
name|modified
operator|=
literal|true
expr_stmt|;
block|}
block|}
if|if
condition|(
name|modified
condition|)
block|{
name|ce
operator|.
name|end
argument_list|()
expr_stmt|;
block|}
return|return
name|modified
condition|?
name|ce
else|:
literal|null
return|;
block|}
return|return
literal|null
return|;
block|}
annotation|@
name|Override
DECL|method|remove (BibtexEntry[] entries)
specifier|public
name|AbstractUndoableEdit
name|remove
parameter_list|(
name|BibtexEntry
index|[]
name|entries
parameter_list|)
block|{
if|if
condition|(
operator|!
name|supportsRemove
argument_list|()
condition|)
block|{
return|return
literal|null
return|;
block|}
if|if
condition|(
name|entries
operator|!=
literal|null
operator|&&
name|entries
operator|.
name|length
operator|>
literal|0
condition|)
block|{
name|NamedCompound
name|ce
init|=
operator|new
name|NamedCompound
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"remove from group"
argument_list|)
argument_list|)
decl_stmt|;
name|boolean
name|modified
init|=
literal|false
decl_stmt|;
for|for
control|(
name|BibtexEntry
name|entry
range|:
name|entries
control|)
block|{
if|if
condition|(
name|getSearchRule
argument_list|()
operator|.
name|applyRule
argument_list|(
name|SearchRule
operator|.
name|NULL_QUERY
argument_list|,
name|entry
argument_list|)
condition|)
block|{
name|String
name|oldContent
init|=
name|entry
operator|.
name|getField
argument_list|(
name|searchField
argument_list|)
decl_stmt|;
name|removeMatches
argument_list|(
name|entry
argument_list|)
expr_stmt|;
comment|// Store undo information.
name|ce
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableFieldChange
argument_list|(
name|entry
argument_list|,
name|searchField
argument_list|,
name|oldContent
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
name|searchField
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|modified
operator|=
literal|true
expr_stmt|;
block|}
block|}
if|if
condition|(
name|modified
condition|)
block|{
name|ce
operator|.
name|end
argument_list|()
expr_stmt|;
block|}
return|return
name|modified
condition|?
name|ce
else|:
literal|null
return|;
block|}
return|return
literal|null
return|;
block|}
annotation|@
name|Override
DECL|method|equals (Object o)
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
if|if
condition|(
operator|!
operator|(
name|o
operator|instanceof
name|KeywordGroup
operator|)
condition|)
block|{
return|return
literal|false
return|;
block|}
name|KeywordGroup
name|other
init|=
operator|(
name|KeywordGroup
operator|)
name|o
decl_stmt|;
return|return
name|name
operator|.
name|equals
argument_list|(
name|other
operator|.
name|name
argument_list|)
operator|&&
name|searchField
operator|.
name|equals
argument_list|(
name|other
operator|.
name|searchField
argument_list|)
operator|&&
name|searchExpression
operator|.
name|equals
argument_list|(
name|other
operator|.
name|searchExpression
argument_list|)
operator|&&
name|caseSensitive
operator|==
name|other
operator|.
name|caseSensitive
operator|&&
name|regExp
operator|==
name|other
operator|.
name|regExp
operator|&&
name|getHierarchicalContext
argument_list|()
operator|==
name|other
operator|.
name|getHierarchicalContext
argument_list|()
return|;
block|}
comment|/*      * (non-Javadoc)      *       * @see net.sf.jabref.groups.structure.AbstractGroup#contains(java.util.Map,      *      net.sf.jabref.BibtexEntry)      */
annotation|@
name|Override
DECL|method|contains (String query, BibtexEntry entry)
specifier|public
name|boolean
name|contains
parameter_list|(
name|String
name|query
parameter_list|,
name|BibtexEntry
name|entry
parameter_list|)
block|{
return|return
name|contains
argument_list|(
name|entry
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|contains (BibtexEntry entry)
specifier|public
name|boolean
name|contains
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|)
block|{
name|String
name|content
init|=
name|entry
operator|.
name|getField
argument_list|(
name|searchField
argument_list|)
decl_stmt|;
if|if
condition|(
name|content
operator|==
literal|null
condition|)
block|{
return|return
literal|false
return|;
block|}
if|if
condition|(
name|regExp
condition|)
block|{
return|return
name|pattern
operator|.
name|matcher
argument_list|(
name|content
argument_list|)
operator|.
name|find
argument_list|()
return|;
block|}
if|if
condition|(
name|caseSensitive
condition|)
block|{
return|return
name|KeywordGroup
operator|.
name|containsWord
argument_list|(
name|searchExpression
argument_list|,
name|content
argument_list|)
return|;
block|}
return|return
name|KeywordGroup
operator|.
name|containsWord
argument_list|(
name|searchExpression
operator|.
name|toLowerCase
argument_list|()
argument_list|,
name|content
operator|.
name|toLowerCase
argument_list|()
argument_list|)
return|;
block|}
comment|/**      * Look for the given non-regexp string in another string, but check whether a      * match concerns a complete word, not part of a word.      *      * @param word The word to look for.      * @param text The string to look in.      * @return true if the word was found, false otherwise.      */
DECL|method|containsWord (String word, String text)
specifier|private
specifier|static
name|boolean
name|containsWord
parameter_list|(
name|String
name|word
parameter_list|,
name|String
name|text
parameter_list|)
block|{
name|int
name|piv
init|=
literal|0
decl_stmt|;
while|while
condition|(
name|piv
operator|<
name|text
operator|.
name|length
argument_list|()
condition|)
block|{
name|int
name|index
init|=
name|text
operator|.
name|indexOf
argument_list|(
name|word
argument_list|,
name|piv
argument_list|)
decl_stmt|;
if|if
condition|(
name|index
operator|<
literal|0
condition|)
block|{
return|return
literal|false
return|;
block|}
comment|// Found a match. See if it is a complete word:
if|if
condition|(
operator|(
name|index
operator|==
literal|0
operator|||
operator|!
name|Character
operator|.
name|isLetterOrDigit
argument_list|(
name|text
operator|.
name|charAt
argument_list|(
name|index
operator|-
literal|1
argument_list|)
argument_list|)
operator|)
operator|&&
operator|(
name|index
operator|+
name|word
operator|.
name|length
argument_list|()
operator|==
name|text
operator|.
name|length
argument_list|()
operator|||
operator|!
name|Character
operator|.
name|isLetterOrDigit
argument_list|(
name|text
operator|.
name|charAt
argument_list|(
name|index
operator|+
name|word
operator|.
name|length
argument_list|()
argument_list|)
argument_list|)
operator|)
condition|)
block|{
return|return
literal|true
return|;
block|}
else|else
block|{
name|piv
operator|=
name|index
operator|+
literal|1
expr_stmt|;
block|}
block|}
return|return
literal|false
return|;
block|}
comment|/**      * Removes matches of searchString in the entry's field. This is only      * possible if the search expression is not a regExp.      */
DECL|method|removeMatches (BibtexEntry entry)
specifier|private
name|void
name|removeMatches
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|)
block|{
name|String
name|content
init|=
name|entry
operator|.
name|getField
argument_list|(
name|searchField
argument_list|)
decl_stmt|;
if|if
condition|(
name|content
operator|==
literal|null
condition|)
block|{
return|return;
comment|// nothing to modify
block|}
name|StringBuffer
name|sbOrig
init|=
operator|new
name|StringBuffer
argument_list|(
name|content
argument_list|)
decl_stmt|;
name|StringBuffer
name|sbLower
init|=
operator|new
name|StringBuffer
argument_list|(
name|content
operator|.
name|toLowerCase
argument_list|()
argument_list|)
decl_stmt|;
name|StringBuffer
name|haystack
init|=
name|caseSensitive
condition|?
name|sbOrig
else|:
name|sbLower
decl_stmt|;
name|String
name|needle
init|=
name|caseSensitive
condition|?
name|searchExpression
else|:
name|searchExpression
operator|.
name|toLowerCase
argument_list|()
decl_stmt|;
name|int
name|i
decl_stmt|,
name|j
decl_stmt|,
name|k
decl_stmt|;
specifier|final
name|String
name|separator
init|=
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|GROUP_KEYWORD_SEPARATOR
argument_list|)
decl_stmt|;
while|while
condition|(
operator|(
name|i
operator|=
name|haystack
operator|.
name|indexOf
argument_list|(
name|needle
argument_list|)
operator|)
operator|>=
literal|0
condition|)
block|{
name|sbOrig
operator|.
name|replace
argument_list|(
name|i
argument_list|,
name|i
operator|+
name|needle
operator|.
name|length
argument_list|()
argument_list|,
literal|""
argument_list|)
expr_stmt|;
name|sbLower
operator|.
name|replace
argument_list|(
name|i
argument_list|,
name|i
operator|+
name|needle
operator|.
name|length
argument_list|()
argument_list|,
literal|""
argument_list|)
expr_stmt|;
comment|// reduce spaces at i to 1
name|j
operator|=
name|i
expr_stmt|;
name|k
operator|=
name|i
expr_stmt|;
while|while
condition|(
name|j
operator|-
literal|1
operator|>=
literal|0
operator|&&
name|separator
operator|.
name|indexOf
argument_list|(
name|haystack
operator|.
name|charAt
argument_list|(
name|j
operator|-
literal|1
argument_list|)
argument_list|)
operator|>=
literal|0
condition|)
block|{
operator|--
name|j
expr_stmt|;
block|}
while|while
condition|(
name|k
operator|<
name|haystack
operator|.
name|length
argument_list|()
operator|&&
name|separator
operator|.
name|indexOf
argument_list|(
name|haystack
operator|.
name|charAt
argument_list|(
name|k
argument_list|)
argument_list|)
operator|>=
literal|0
condition|)
block|{
operator|++
name|k
expr_stmt|;
block|}
name|sbOrig
operator|.
name|replace
argument_list|(
name|j
argument_list|,
name|k
argument_list|,
name|j
operator|>=
literal|0
operator|&&
name|k
operator|<
name|sbOrig
operator|.
name|length
argument_list|()
condition|?
name|separator
else|:
literal|""
argument_list|)
expr_stmt|;
name|sbLower
operator|.
name|replace
argument_list|(
name|j
argument_list|,
name|k
argument_list|,
name|j
operator|>=
literal|0
operator|&&
name|k
operator|<
name|sbOrig
operator|.
name|length
argument_list|()
condition|?
name|separator
else|:
literal|""
argument_list|)
expr_stmt|;
block|}
name|String
name|result
init|=
name|sbOrig
operator|.
name|toString
argument_list|()
operator|.
name|trim
argument_list|()
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|searchField
argument_list|,
operator|!
name|result
operator|.
name|isEmpty
argument_list|()
condition|?
name|result
else|:
literal|null
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|deepCopy ()
specifier|public
name|AbstractGroup
name|deepCopy
parameter_list|()
block|{
try|try
block|{
return|return
operator|new
name|KeywordGroup
argument_list|(
name|name
argument_list|,
name|searchField
argument_list|,
name|searchExpression
argument_list|,
name|caseSensitive
argument_list|,
name|regExp
argument_list|,
name|context
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|Throwable
name|t
parameter_list|)
block|{
comment|// this should never happen, because the constructor obviously
comment|// succeeded in creating _this_ instance!
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"Internal error: Exception "
operator|+
name|t
operator|+
literal|" in KeywordGroup.deepCopy(). "
operator|+
literal|"Please report this on www.sf.net/projects/jabref"
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
DECL|method|isCaseSensitive ()
specifier|public
name|boolean
name|isCaseSensitive
parameter_list|()
block|{
return|return
name|caseSensitive
return|;
block|}
DECL|method|isRegExp ()
specifier|public
name|boolean
name|isRegExp
parameter_list|()
block|{
return|return
name|regExp
return|;
block|}
DECL|method|getSearchExpression ()
specifier|public
name|String
name|getSearchExpression
parameter_list|()
block|{
return|return
name|searchExpression
return|;
block|}
DECL|method|getSearchField ()
specifier|public
name|String
name|getSearchField
parameter_list|()
block|{
return|return
name|searchField
return|;
block|}
annotation|@
name|Override
DECL|method|isDynamic ()
specifier|public
name|boolean
name|isDynamic
parameter_list|()
block|{
return|return
literal|true
return|;
block|}
annotation|@
name|Override
DECL|method|getDescription ()
specifier|public
name|String
name|getDescription
parameter_list|()
block|{
return|return
name|KeywordGroup
operator|.
name|getDescriptionForPreview
argument_list|(
name|searchField
argument_list|,
name|searchExpression
argument_list|,
name|caseSensitive
argument_list|,
name|regExp
argument_list|)
return|;
block|}
DECL|method|getDescriptionForPreview (String field, String expr, boolean caseSensitive, boolean regExp)
specifier|public
specifier|static
name|String
name|getDescriptionForPreview
parameter_list|(
name|String
name|field
parameter_list|,
name|String
name|expr
parameter_list|,
name|boolean
name|caseSensitive
parameter_list|,
name|boolean
name|regExp
parameter_list|)
block|{
name|String
name|header
init|=
name|regExp
condition|?
name|Globals
operator|.
name|lang
argument_list|(
literal|"This group contains entries whose<b>%0</b> field contains the regular expression<b>%1</b>"
argument_list|,
name|field
argument_list|,
name|StringUtil
operator|.
name|quoteForHTML
argument_list|(
name|expr
argument_list|)
argument_list|)
else|:
name|Globals
operator|.
name|lang
argument_list|(
literal|"This group contains entries whose<b>%0</b> field contains the keyword<b>%1</b>"
argument_list|,
name|field
argument_list|,
name|StringUtil
operator|.
name|quoteForHTML
argument_list|(
name|expr
argument_list|)
argument_list|)
decl_stmt|;
name|String
name|caseSensitiveText
init|=
name|caseSensitive
condition|?
name|Globals
operator|.
name|lang
argument_list|(
literal|"case sensitive"
argument_list|)
else|:
name|Globals
operator|.
name|lang
argument_list|(
literal|"case insensitive"
argument_list|)
decl_stmt|;
name|String
name|footer
init|=
name|regExp
condition|?
name|Globals
operator|.
name|lang
argument_list|(
literal|"Entries cannot be manually assigned to or removed from this group."
argument_list|)
else|:
name|Globals
operator|.
name|lang
argument_list|(
literal|"Additionally, entries whose<b>%0</b> field does not contain "
operator|+
literal|"<b>%1</b> can be assigned manually to this group by selecting them "
operator|+
literal|"then using either drag and drop or the context menu. "
operator|+
literal|"This process adds the term<b>%1</b> to "
operator|+
literal|"each entry's<b>%0</b> field. "
operator|+
literal|"Entries can be removed manually from this group by selecting them "
operator|+
literal|"then using the context menu. "
operator|+
literal|"This process removes the term<b>%1</b> from "
operator|+
literal|"each entry's<b>%0</b> field."
argument_list|,
name|field
argument_list|,
name|StringUtil
operator|.
name|quoteForHTML
argument_list|(
name|expr
argument_list|)
argument_list|)
decl_stmt|;
return|return
name|String
operator|.
name|format
argument_list|(
literal|"%s (%s). %s"
argument_list|,
name|header
argument_list|,
name|caseSensitiveText
argument_list|,
name|footer
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|getShortDescription ()
specifier|public
name|String
name|getShortDescription
parameter_list|()
block|{
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"<b>"
argument_list|)
expr_stmt|;
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|GROUP_SHOW_DYNAMIC
argument_list|)
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|"<i>"
argument_list|)
operator|.
name|append
argument_list|(
name|StringUtil
operator|.
name|quoteForHTML
argument_list|(
name|getName
argument_list|()
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|"</i>"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|sb
operator|.
name|append
argument_list|(
name|StringUtil
operator|.
name|quoteForHTML
argument_list|(
name|getName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|sb
operator|.
name|append
argument_list|(
literal|"</b> - "
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"dynamic group"
argument_list|)
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"<b>"
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|searchField
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"</b>"
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"contains"
argument_list|)
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"<b>"
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|StringUtil
operator|.
name|quoteForHTML
argument_list|(
name|searchExpression
argument_list|)
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"</b>)"
argument_list|)
expr_stmt|;
switch|switch
condition|(
name|getHierarchicalContext
argument_list|()
condition|)
block|{
case|case
name|INCLUDING
case|:
name|sb
operator|.
name|append
argument_list|(
literal|", "
argument_list|)
operator|.
name|append
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"includes subgroups"
argument_list|)
argument_list|)
expr_stmt|;
break|break;
case|case
name|REFINING
case|:
name|sb
operator|.
name|append
argument_list|(
literal|", "
argument_list|)
operator|.
name|append
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"refines supergroup"
argument_list|)
argument_list|)
expr_stmt|;
break|break;
default|default:
break|break;
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
annotation|@
name|Override
DECL|method|getTypeId ()
specifier|public
name|String
name|getTypeId
parameter_list|()
block|{
return|return
name|KeywordGroup
operator|.
name|ID
return|;
block|}
block|}
end_class

end_unit

