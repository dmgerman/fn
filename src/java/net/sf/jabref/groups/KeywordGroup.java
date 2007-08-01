begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  All programs in this directory and subdirectories are published under the   GNU General Public License as described below.   This program is free software; you can redistribute it and/or modify it   under the terms of the GNU General Public License as published by the Free   Software Foundation; either version 2 of the License, or (at your option)   any later version.   This program is distributed in the hope that it will be useful, but WITHOUT   ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or   FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for   more details.   You should have received a copy of the GNU General Public License along   with this program; if not, write to the Free Software Foundation, Inc., 59   Temple Place, Suite 330, Boston, MA 02111-1307 USA   Further information about the GNU GPL is available at:  http://www.gnu.org/copyleft/gpl.ja.html  */
end_comment

begin_package
DECL|package|net.sf.jabref.groups
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|groups
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
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
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|PatternSyntaxException
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
implements|implements
name|SearchRule
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
DECL|field|m_searchField
specifier|private
specifier|final
name|String
name|m_searchField
decl_stmt|;
DECL|field|m_searchExpression
specifier|private
specifier|final
name|String
name|m_searchExpression
decl_stmt|;
DECL|field|m_caseSensitive
specifier|private
specifier|final
name|boolean
name|m_caseSensitive
decl_stmt|;
DECL|field|m_regExp
specifier|private
specifier|final
name|boolean
name|m_regExp
decl_stmt|;
DECL|field|m_pattern
specifier|private
name|Pattern
name|m_pattern
init|=
literal|null
decl_stmt|;
comment|/** 	 * Creates a KeywordGroup with the specified properties. 	 */
DECL|method|KeywordGroup (String name, String searchField, String searchExpression, boolean caseSensitive, boolean regExp, int context)
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
name|int
name|context
parameter_list|)
throws|throws
name|IllegalArgumentException
throws|,
name|PatternSyntaxException
block|{
name|super
argument_list|(
name|name
argument_list|,
name|context
argument_list|)
expr_stmt|;
name|m_searchField
operator|=
name|searchField
expr_stmt|;
name|m_searchExpression
operator|=
name|searchExpression
expr_stmt|;
name|m_caseSensitive
operator|=
name|caseSensitive
expr_stmt|;
name|m_regExp
operator|=
name|regExp
expr_stmt|;
if|if
condition|(
name|m_regExp
condition|)
name|compilePattern
argument_list|()
expr_stmt|;
block|}
DECL|method|compilePattern ()
specifier|protected
name|void
name|compilePattern
parameter_list|()
throws|throws
name|IllegalArgumentException
throws|,
name|PatternSyntaxException
block|{
name|m_pattern
operator|=
name|m_caseSensitive
condition|?
name|Pattern
operator|.
name|compile
argument_list|(
name|m_searchExpression
argument_list|)
else|:
name|Pattern
operator|.
name|compile
argument_list|(
name|m_searchExpression
argument_list|,
name|Pattern
operator|.
name|CASE_INSENSITIVE
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * Parses s and recreates the KeywordGroup from it. 	 *  	 * @param s 	 *            The String representation obtained from 	 *            KeywordGroup.toString() 	 */
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
name|ID
argument_list|)
condition|)
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
name|ID
operator|.
name|length
argument_list|()
argument_list|)
argument_list|,
name|SEPARATOR
argument_list|,
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
name|Util
operator|.
name|unquote
argument_list|(
name|name
argument_list|,
name|QUOTE_CHAR
argument_list|)
argument_list|,
name|Util
operator|.
name|unquote
argument_list|(
name|field
argument_list|,
name|QUOTE_CHAR
argument_list|)
argument_list|,
name|Util
operator|.
name|unquote
argument_list|(
name|expression
argument_list|,
name|QUOTE_CHAR
argument_list|)
argument_list|,
literal|false
argument_list|,
literal|true
argument_list|,
name|AbstractGroup
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
name|Util
operator|.
name|unquote
argument_list|(
name|name
argument_list|,
name|QUOTE_CHAR
argument_list|)
argument_list|,
name|Util
operator|.
name|unquote
argument_list|(
name|field
argument_list|,
name|QUOTE_CHAR
argument_list|)
argument_list|,
name|Util
operator|.
name|unquote
argument_list|(
name|expression
argument_list|,
name|QUOTE_CHAR
argument_list|)
argument_list|,
name|caseSensitive
argument_list|,
name|regExp
argument_list|,
name|AbstractGroup
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
name|Util
operator|.
name|unquote
argument_list|(
name|name
argument_list|,
name|QUOTE_CHAR
argument_list|)
argument_list|,
name|Util
operator|.
name|unquote
argument_list|(
name|field
argument_list|,
name|QUOTE_CHAR
argument_list|)
argument_list|,
name|Util
operator|.
name|unquote
argument_list|(
name|expression
argument_list|,
name|QUOTE_CHAR
argument_list|)
argument_list|,
name|caseSensitive
argument_list|,
name|regExp
argument_list|,
name|context
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
comment|/** 	 * @see net.sf.jabref.groups.AbstractGroup#getSearchRule() 	 */
DECL|method|getSearchRule ()
specifier|public
name|SearchRule
name|getSearchRule
parameter_list|()
block|{
return|return
name|this
return|;
block|}
comment|/** 	 * Returns a String representation of this object that can be used to 	 * reconstruct it. 	 */
DECL|method|toString ()
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|ID
operator|+
name|Util
operator|.
name|quote
argument_list|(
name|m_name
argument_list|,
name|SEPARATOR
argument_list|,
name|QUOTE_CHAR
argument_list|)
operator|+
name|SEPARATOR
operator|+
name|m_context
operator|+
name|SEPARATOR
operator|+
name|Util
operator|.
name|quote
argument_list|(
name|m_searchField
argument_list|,
name|SEPARATOR
argument_list|,
name|QUOTE_CHAR
argument_list|)
operator|+
name|SEPARATOR
operator|+
name|Util
operator|.
name|quote
argument_list|(
name|m_searchExpression
argument_list|,
name|SEPARATOR
argument_list|,
name|QUOTE_CHAR
argument_list|)
operator|+
name|SEPARATOR
operator|+
operator|(
name|m_caseSensitive
condition|?
literal|"1"
else|:
literal|"0"
operator|)
operator|+
name|SEPARATOR
operator|+
operator|(
name|m_regExp
condition|?
literal|"1"
else|:
literal|"0"
operator|)
operator|+
name|SEPARATOR
return|;
block|}
DECL|method|supportsAdd ()
specifier|public
name|boolean
name|supportsAdd
parameter_list|()
block|{
return|return
operator|!
name|m_regExp
return|;
block|}
DECL|method|supportsRemove ()
specifier|public
name|boolean
name|supportsRemove
parameter_list|()
block|{
return|return
operator|!
name|m_regExp
return|;
block|}
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
return|return
literal|null
return|;
if|if
condition|(
operator|(
name|entries
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|entries
operator|.
name|length
operator|>
literal|0
operator|)
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
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|entries
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|applyRule
argument_list|(
literal|null
argument_list|,
name|entries
index|[
name|i
index|]
argument_list|)
operator|==
literal|0
condition|)
block|{
name|String
name|oldContent
init|=
operator|(
name|String
operator|)
name|entries
index|[
name|i
index|]
operator|.
name|getField
argument_list|(
name|m_searchField
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
literal|"groupKeywordSeparator"
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
name|m_searchExpression
decl_stmt|;
name|entries
index|[
name|i
index|]
operator|.
name|setField
argument_list|(
name|m_searchField
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
name|entries
index|[
name|i
index|]
argument_list|,
name|m_searchField
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
name|ce
operator|.
name|end
argument_list|()
expr_stmt|;
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
return|return
literal|null
return|;
if|if
condition|(
operator|(
name|entries
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|entries
operator|.
name|length
operator|>
literal|0
operator|)
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
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|entries
operator|.
name|length
condition|;
operator|++
name|i
control|)
block|{
if|if
condition|(
name|applyRule
argument_list|(
literal|null
argument_list|,
name|entries
index|[
name|i
index|]
argument_list|)
operator|>
literal|0
condition|)
block|{
name|String
name|oldContent
init|=
operator|(
name|String
operator|)
name|entries
index|[
name|i
index|]
operator|.
name|getField
argument_list|(
name|m_searchField
argument_list|)
decl_stmt|;
name|removeMatches
argument_list|(
name|entries
index|[
name|i
index|]
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
name|entries
index|[
name|i
index|]
argument_list|,
name|m_searchField
argument_list|,
name|oldContent
argument_list|,
name|entries
index|[
name|i
index|]
operator|.
name|getField
argument_list|(
name|m_searchField
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
name|ce
operator|.
name|end
argument_list|()
expr_stmt|;
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
return|return
literal|false
return|;
name|KeywordGroup
name|other
init|=
operator|(
name|KeywordGroup
operator|)
name|o
decl_stmt|;
return|return
name|m_name
operator|.
name|equals
argument_list|(
name|other
operator|.
name|m_name
argument_list|)
operator|&&
name|m_searchField
operator|.
name|equals
argument_list|(
name|other
operator|.
name|m_searchField
argument_list|)
operator|&&
name|m_searchExpression
operator|.
name|equals
argument_list|(
name|other
operator|.
name|m_searchExpression
argument_list|)
operator|&&
name|m_caseSensitive
operator|==
name|other
operator|.
name|m_caseSensitive
operator|&&
name|m_regExp
operator|==
name|other
operator|.
name|m_regExp
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
comment|/* 	 * (non-Javadoc) 	 *  	 * @see net.sf.jabref.groups.AbstractGroup#contains(java.util.Map, 	 *      net.sf.jabref.BibtexEntry) 	 */
DECL|method|contains (Map searchOptions, BibtexEntry entry)
specifier|public
name|boolean
name|contains
parameter_list|(
name|Map
name|searchOptions
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
operator|(
name|String
operator|)
name|entry
operator|.
name|getField
argument_list|(
name|m_searchField
argument_list|)
decl_stmt|;
if|if
condition|(
name|content
operator|==
literal|null
condition|)
return|return
literal|false
return|;
if|if
condition|(
name|m_regExp
condition|)
return|return
name|m_pattern
operator|.
name|matcher
argument_list|(
name|content
argument_list|)
operator|.
name|find
argument_list|()
return|;
if|if
condition|(
name|m_caseSensitive
condition|)
return|return
name|content
operator|.
name|indexOf
argument_list|(
name|m_searchExpression
argument_list|)
operator|>=
literal|0
return|;
name|content
operator|=
name|content
operator|.
name|toLowerCase
argument_list|()
expr_stmt|;
return|return
name|content
operator|.
name|indexOf
argument_list|(
name|m_searchExpression
operator|.
name|toLowerCase
argument_list|()
argument_list|)
operator|>=
literal|0
return|;
block|}
comment|/** 	 * Removes matches of searchString in the entry's field. This is only 	 * possible if the search expression is not a regExp. 	 */
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
operator|(
name|String
operator|)
name|entry
operator|.
name|getField
argument_list|(
name|m_searchField
argument_list|)
decl_stmt|;
if|if
condition|(
name|content
operator|==
literal|null
condition|)
return|return;
comment|// nothing to modify
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
name|m_caseSensitive
condition|?
name|sbOrig
else|:
name|sbLower
decl_stmt|;
name|String
name|needle
init|=
name|m_caseSensitive
condition|?
name|m_searchExpression
else|:
name|m_searchExpression
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
literal|"groupKeywordSeparator"
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
operator|--
name|j
expr_stmt|;
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
operator|++
name|k
expr_stmt|;
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
name|m_searchField
argument_list|,
operator|(
name|result
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|?
name|result
else|:
literal|null
operator|)
argument_list|)
expr_stmt|;
block|}
DECL|method|applyRule (Map searchOptions, BibtexEntry entry)
specifier|public
name|int
name|applyRule
parameter_list|(
name|Map
name|searchOptions
parameter_list|,
name|BibtexEntry
name|entry
parameter_list|)
block|{
return|return
name|contains
argument_list|(
name|searchOptions
argument_list|,
name|entry
argument_list|)
condition|?
literal|1
else|:
literal|0
return|;
block|}
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
name|m_name
argument_list|,
name|m_searchField
argument_list|,
name|m_searchExpression
argument_list|,
name|m_caseSensitive
argument_list|,
name|m_regExp
argument_list|,
name|m_context
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
name|m_caseSensitive
return|;
block|}
DECL|method|isRegExp ()
specifier|public
name|boolean
name|isRegExp
parameter_list|()
block|{
return|return
name|m_regExp
return|;
block|}
DECL|method|getSearchExpression ()
specifier|public
name|String
name|getSearchExpression
parameter_list|()
block|{
return|return
name|m_searchExpression
return|;
block|}
DECL|method|getSearchField ()
specifier|public
name|String
name|getSearchField
parameter_list|()
block|{
return|return
name|m_searchField
return|;
block|}
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
DECL|method|getDescription ()
specifier|public
name|String
name|getDescription
parameter_list|()
block|{
return|return
name|getDescriptionForPreview
argument_list|(
name|m_searchField
argument_list|,
name|m_searchExpression
argument_list|,
name|m_caseSensitive
argument_list|,
name|m_regExp
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
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
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
name|Util
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
name|Util
operator|.
name|quoteForHTML
argument_list|(
name|expr
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|" ("
argument_list|)
operator|.
name|append
argument_list|(
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
argument_list|)
operator|.
name|append
argument_list|(
literal|"). "
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
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
name|Util
operator|.
name|quoteForHTML
argument_list|(
name|expr
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
DECL|method|getShortDescription ()
specifier|public
name|String
name|getShortDescription
parameter_list|()
block|{
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
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
literal|"groupShowDynamic"
argument_list|)
condition|)
name|sb
operator|.
name|append
argument_list|(
literal|"<i>"
argument_list|)
operator|.
name|append
argument_list|(
name|Util
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
else|else
name|sb
operator|.
name|append
argument_list|(
name|Util
operator|.
name|quoteForHTML
argument_list|(
name|getName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"</b> - dynamic group (<b>"
argument_list|)
operator|.
name|append
argument_list|(
name|m_searchField
argument_list|)
operator|.
name|append
argument_list|(
literal|"</b> contains<b>"
argument_list|)
operator|.
name|append
argument_list|(
name|Util
operator|.
name|quoteForHTML
argument_list|(
name|m_searchExpression
argument_list|)
argument_list|)
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
name|AbstractGroup
operator|.
name|INCLUDING
case|:
name|sb
operator|.
name|append
argument_list|(
literal|", includes subgroups"
argument_list|)
expr_stmt|;
break|break;
case|case
name|AbstractGroup
operator|.
name|REFINING
case|:
name|sb
operator|.
name|append
argument_list|(
literal|", refines supergroup"
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
block|}
end_class

end_unit

