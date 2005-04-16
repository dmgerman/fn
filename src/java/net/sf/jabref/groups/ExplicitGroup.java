begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* All programs in this directory and subdirectories are published under the  GNU General Public License as described below.  This program is free software; you can redistribute it and/or modify it  under the terms of the GNU General Public License as published by the Free  Software Foundation; either version 2 of the License, or (at your option)  any later version.  This program is distributed in the hope that it will be useful, but WITHOUT  ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or  FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for  more details.  You should have received a copy of the GNU General Public License along  with this program; if not, write to the Free Software Foundation, Inc., 59  Temple Place, Suite 330, Boston, MA 02111-1307 USA  Further information about the GNU GPL is available at: http://www.gnu.org/copyleft/gpl.ja.html */
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
name|*
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
name|util
operator|.
name|QuotedStringTokenizer
import|;
end_import

begin_comment
comment|/**  * @author jzieren  *   */
end_comment

begin_class
DECL|class|ExplicitGroup
specifier|public
class|class
name|ExplicitGroup
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
literal|"ExplicitGroup:"
decl_stmt|;
DECL|field|m_entries
specifier|private
specifier|final
name|Set
name|m_entries
decl_stmt|;
DECL|field|m_database
specifier|private
specifier|final
name|BibtexDatabase
name|m_database
decl_stmt|;
DECL|method|ExplicitGroup (String name, BibtexDatabase db)
specifier|public
name|ExplicitGroup
parameter_list|(
name|String
name|name
parameter_list|,
name|BibtexDatabase
name|db
parameter_list|)
block|{
name|super
argument_list|(
name|name
argument_list|)
expr_stmt|;
name|m_entries
operator|=
operator|new
name|HashSet
argument_list|()
expr_stmt|;
name|m_database
operator|=
name|db
expr_stmt|;
block|}
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
literal|"Internal error: ExplicitGroup cannot be created from \""
operator|+
name|s
operator|+
literal|"\""
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
case|case
literal|1
case|:
name|ExplicitGroup
name|newGroup
init|=
operator|new
name|ExplicitGroup
argument_list|(
name|tok
operator|.
name|nextToken
argument_list|()
argument_list|,
name|db
argument_list|)
decl_stmt|;
name|BibtexEntry
index|[]
name|entries
decl_stmt|;
while|while
condition|(
name|tok
operator|.
name|hasMoreTokens
argument_list|()
condition|)
block|{
name|entries
operator|=
name|db
operator|.
name|getEntriesByKey
argument_list|(
name|Util
operator|.
name|unquote
argument_list|(
name|tok
operator|.
name|nextToken
argument_list|()
argument_list|,
name|QUOTE_CHAR
argument_list|)
argument_list|)
expr_stmt|;
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
name|newGroup
operator|.
name|m_entries
operator|.
name|add
argument_list|(
name|entries
index|[
name|i
index|]
argument_list|)
expr_stmt|;
block|}
return|return
name|newGroup
return|;
default|default:
throw|throw
operator|new
name|UnsupportedVersionException
argument_list|(
literal|"ExplicitGroup"
argument_list|,
name|version
argument_list|)
throw|;
block|}
block|}
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
DECL|method|supportsAdd ()
specifier|public
name|boolean
name|supportsAdd
parameter_list|()
block|{
return|return
literal|true
return|;
block|}
DECL|method|supportsRemove ()
specifier|public
name|boolean
name|supportsRemove
parameter_list|()
block|{
return|return
literal|true
return|;
block|}
DECL|method|addSelection (BibtexEntry[] entries)
specifier|public
name|AbstractUndoableEdit
name|addSelection
parameter_list|(
name|BibtexEntry
index|[]
name|entries
parameter_list|)
block|{
if|if
condition|(
name|entries
operator|.
name|length
operator|==
literal|0
condition|)
return|return
literal|null
return|;
comment|// nothing to do
name|HashSet
name|entriesBeforeEdit
init|=
operator|new
name|HashSet
argument_list|(
name|m_entries
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
name|entries
operator|.
name|length
condition|;
operator|++
name|i
control|)
name|m_entries
operator|.
name|add
argument_list|(
name|entries
index|[
name|i
index|]
argument_list|)
expr_stmt|;
return|return
operator|new
name|UndoableChangeAssignment
argument_list|(
name|entriesBeforeEdit
argument_list|,
name|m_entries
argument_list|)
return|;
block|}
DECL|method|addSelection (BasePanel basePanel)
specifier|public
name|AbstractUndoableEdit
name|addSelection
parameter_list|(
name|BasePanel
name|basePanel
parameter_list|)
block|{
return|return
name|addSelection
argument_list|(
name|basePanel
operator|.
name|getSelectedEntries
argument_list|()
argument_list|)
return|;
block|}
DECL|method|addEntry (BibtexEntry entry)
specifier|public
name|boolean
name|addEntry
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|)
block|{
return|return
name|m_entries
operator|.
name|add
argument_list|(
name|entry
argument_list|)
return|;
block|}
DECL|method|removeSelection (BibtexEntry[] entries)
specifier|public
name|AbstractUndoableEdit
name|removeSelection
parameter_list|(
name|BibtexEntry
index|[]
name|entries
parameter_list|)
block|{
if|if
condition|(
name|entries
operator|.
name|length
operator|==
literal|0
condition|)
return|return
literal|null
return|;
comment|// nothing to do
name|HashSet
name|entriesBeforeEdit
init|=
operator|new
name|HashSet
argument_list|(
name|m_entries
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
name|entries
operator|.
name|length
condition|;
operator|++
name|i
control|)
name|m_entries
operator|.
name|remove
argument_list|(
name|entries
index|[
name|i
index|]
argument_list|)
expr_stmt|;
return|return
operator|new
name|UndoableChangeAssignment
argument_list|(
name|entriesBeforeEdit
argument_list|,
name|m_entries
argument_list|)
return|;
block|}
DECL|method|removeSelection (BasePanel basePanel)
specifier|public
name|AbstractUndoableEdit
name|removeSelection
parameter_list|(
name|BasePanel
name|basePanel
parameter_list|)
block|{
return|return
name|removeSelection
argument_list|(
name|basePanel
operator|.
name|getSelectedEntries
argument_list|()
argument_list|)
return|;
block|}
DECL|method|removeEntry (BibtexEntry entry)
specifier|public
name|boolean
name|removeEntry
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|)
block|{
return|return
name|m_entries
operator|.
name|remove
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
return|return
name|m_entries
operator|.
name|contains
argument_list|(
name|entry
argument_list|)
return|;
block|}
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
DECL|method|applyRule (Map searchStrings, BibtexEntry bibtexEntry)
specifier|public
name|int
name|applyRule
parameter_list|(
name|Map
name|searchStrings
parameter_list|,
name|BibtexEntry
name|bibtexEntry
parameter_list|)
block|{
return|return
name|contains
argument_list|(
name|searchStrings
argument_list|,
name|bibtexEntry
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
name|ExplicitGroup
name|copy
init|=
operator|new
name|ExplicitGroup
argument_list|(
name|m_name
argument_list|,
name|m_database
argument_list|)
decl_stmt|;
name|copy
operator|.
name|m_entries
operator|.
name|addAll
argument_list|(
name|m_entries
argument_list|)
expr_stmt|;
return|return
name|copy
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
name|ExplicitGroup
operator|)
condition|)
return|return
literal|false
return|;
name|ExplicitGroup
name|other
init|=
operator|(
name|ExplicitGroup
operator|)
name|o
decl_stmt|;
return|return
name|other
operator|.
name|m_name
operator|.
name|equals
argument_list|(
name|m_name
argument_list|)
operator|&&
name|other
operator|.
name|m_entries
operator|.
name|equals
argument_list|(
name|m_entries
argument_list|)
operator|&&
name|other
operator|.
name|m_database
operator|==
name|m_database
return|;
block|}
comment|/**      * Returns a String representation of this group and its entries. Entries      * are referenced by their Bibtexkey. Entries that do not have a Bibtexkey      * are not included in the representation and will thus not be available      * upon recreation.      */
DECL|method|toString ()
specifier|public
name|String
name|toString
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
argument_list|)
expr_stmt|;
name|String
name|s
decl_stmt|;
for|for
control|(
name|Iterator
name|it
init|=
name|m_entries
operator|.
name|iterator
argument_list|()
init|;
name|it
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|s
operator|=
operator|(
operator|(
name|BibtexEntry
operator|)
name|it
operator|.
name|next
argument_list|()
operator|)
operator|.
name|getCiteKey
argument_list|()
expr_stmt|;
if|if
condition|(
name|s
operator|!=
literal|null
operator|&&
operator|!
name|s
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
name|sb
operator|.
name|append
argument_list|(
name|Util
operator|.
name|quote
argument_list|(
name|s
argument_list|,
name|SEPARATOR
argument_list|,
name|QUOTE_CHAR
argument_list|)
operator|+
name|SEPARATOR
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
comment|/** Remove all assignments, resulting in an empty group. */
DECL|method|clearAssignments ()
specifier|public
name|void
name|clearAssignments
parameter_list|()
block|{
name|m_entries
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

