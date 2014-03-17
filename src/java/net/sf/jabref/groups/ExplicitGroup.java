begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
comment|/**  * @author jzieren  *  */
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
argument_list|<
name|BibtexEntry
argument_list|>
name|m_entries
decl_stmt|;
DECL|method|ExplicitGroup (String name, int context)
specifier|public
name|ExplicitGroup
parameter_list|(
name|String
name|name
parameter_list|,
name|int
name|context
parameter_list|)
block|{
name|super
argument_list|(
name|name
argument_list|,
name|context
argument_list|)
expr_stmt|;
name|m_entries
operator|=
operator|new
name|HashSet
argument_list|<
name|BibtexEntry
argument_list|>
argument_list|()
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
case|case
literal|1
case|:
case|case
literal|2
case|:
block|{
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
name|AbstractGroup
operator|.
name|INDEPENDENT
argument_list|)
decl_stmt|;
name|newGroup
operator|.
name|addEntries
argument_list|(
name|tok
argument_list|,
name|db
argument_list|)
expr_stmt|;
return|return
name|newGroup
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
name|ExplicitGroup
name|newGroup
init|=
operator|new
name|ExplicitGroup
argument_list|(
name|name
argument_list|,
name|context
argument_list|)
decl_stmt|;
name|newGroup
operator|.
name|addEntries
argument_list|(
name|tok
argument_list|,
name|db
argument_list|)
expr_stmt|;
return|return
name|newGroup
return|;
block|}
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
comment|/** Called only when created fromString */
DECL|method|addEntries (QuotedStringTokenizer tok, BibtexDatabase db)
specifier|protected
name|void
name|addEntries
parameter_list|(
name|QuotedStringTokenizer
name|tok
parameter_list|,
name|BibtexDatabase
name|db
parameter_list|)
block|{
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
name|BibtexEntry
name|entry
range|:
name|entries
control|)
name|m_entries
operator|.
name|add
argument_list|(
name|entry
argument_list|)
expr_stmt|;
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
argument_list|<
name|BibtexEntry
argument_list|>
name|entriesBeforeEdit
init|=
operator|new
name|HashSet
argument_list|<
name|BibtexEntry
argument_list|>
argument_list|(
name|m_entries
argument_list|)
decl_stmt|;
for|for
control|(
name|BibtexEntry
name|entry
range|:
name|entries
control|)
name|m_entries
operator|.
name|add
argument_list|(
name|entry
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
argument_list|<
name|BibtexEntry
argument_list|>
name|entriesBeforeEdit
init|=
operator|new
name|HashSet
argument_list|<
name|BibtexEntry
argument_list|>
argument_list|(
name|m_entries
argument_list|)
decl_stmt|;
for|for
control|(
name|BibtexEntry
name|entry
range|:
name|entries
control|)
name|m_entries
operator|.
name|remove
argument_list|(
name|entry
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
DECL|method|contains (Map<String, String> searchOptions, BibtexEntry entry)
specifier|public
name|boolean
name|contains
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
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
DECL|method|applyRule (Map<String, String> searchStrings, BibtexEntry bibtexEntry)
specifier|public
name|int
name|applyRule
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
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
DECL|method|validateSearchStrings (Map<String, String> searchStrings)
specifier|public
name|boolean
name|validateSearchStrings
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|searchStrings
parameter_list|)
block|{
return|return
literal|true
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
name|m_context
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
comment|// compare entries assigned to both groups
if|if
condition|(
name|m_entries
operator|.
name|size
argument_list|()
operator|!=
name|other
operator|.
name|m_entries
operator|.
name|size
argument_list|()
condition|)
return|return
literal|false
return|;
comment|// add/remove
name|HashSet
argument_list|<
name|String
argument_list|>
name|keys
init|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|BibtexEntry
name|entry
decl_stmt|;
name|String
name|key
decl_stmt|;
comment|// compare bibtex keys for all entries that have one
for|for
control|(
name|BibtexEntry
name|m_entry1
range|:
name|m_entries
control|)
block|{
name|entry
operator|=
name|m_entry1
expr_stmt|;
name|key
operator|=
name|entry
operator|.
name|getCiteKey
argument_list|()
expr_stmt|;
if|if
condition|(
name|key
operator|!=
literal|null
condition|)
name|keys
operator|.
name|add
argument_list|(
name|key
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|BibtexEntry
name|m_entry
range|:
name|other
operator|.
name|m_entries
control|)
block|{
name|entry
operator|=
name|m_entry
expr_stmt|;
name|key
operator|=
name|entry
operator|.
name|getCiteKey
argument_list|()
expr_stmt|;
if|if
condition|(
name|key
operator|!=
literal|null
condition|)
if|if
condition|(
operator|!
name|keys
operator|.
name|remove
argument_list|(
name|key
argument_list|)
condition|)
return|return
literal|false
return|;
block|}
if|if
condition|(
operator|!
name|keys
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
literal|false
return|;
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
name|getHierarchicalContext
argument_list|()
operator|==
name|getHierarchicalContext
argument_list|()
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
argument_list|)
operator|.
name|append
argument_list|(
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
argument_list|)
operator|.
name|append
argument_list|(
name|SEPARATOR
argument_list|)
operator|.
name|append
argument_list|(
name|m_context
argument_list|)
operator|.
name|append
argument_list|(
name|SEPARATOR
argument_list|)
expr_stmt|;
name|String
name|s
decl_stmt|;
comment|// write entries in well-defined order for CVS compatibility
name|Set
argument_list|<
name|String
argument_list|>
name|sortedKeys
init|=
operator|new
name|TreeSet
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|BibtexEntry
name|m_entry
range|:
name|m_entries
control|)
block|{
name|s
operator|=
name|m_entry
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
comment|// entries without a key are lost
name|sortedKeys
operator|.
name|add
argument_list|(
name|s
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|String
name|sortedKey
range|:
name|sortedKeys
control|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|Util
operator|.
name|quote
argument_list|(
name|sortedKey
argument_list|,
name|SEPARATOR
argument_list|,
name|QUOTE_CHAR
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
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
DECL|method|isDynamic ()
specifier|public
name|boolean
name|isDynamic
parameter_list|()
block|{
return|return
literal|false
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
argument_list|()
return|;
block|}
DECL|method|getDescriptionForPreview ()
specifier|public
specifier|static
name|String
name|getDescriptionForPreview
parameter_list|()
block|{
return|return
name|Globals
operator|.
name|lang
argument_list|(
literal|"This group contains entries based on manual assignment. "
operator|+
literal|"Entries can be assigned to this group by selecting them "
operator|+
literal|"then using either drag and drop or the context menu. "
operator|+
literal|"Entries can be removed from this group by selecting them "
operator|+
literal|"then using the context menu. Every entry assigned to this group "
operator|+
literal|"must have a unique key. The key may be changed at any time "
operator|+
literal|"as long as it remains unique."
argument_list|)
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
operator|.
name|append
argument_list|(
name|getName
argument_list|()
argument_list|)
operator|.
name|append
argument_list|(
literal|"</b> -"
argument_list|)
operator|.
name|append
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"static group"
argument_list|)
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
name|AbstractGroup
operator|.
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
comment|/**      * Update the group to handle the situation where the group      * is applied to a different BibtexDatabase than it was created for.      * This group type contains a Set of BibtexEntry objects, and these will not      * be the same objects as in the new database. We must reset the entire Set with      * matching entries from the new database.      *      * @param db The database to refresh for.      */
DECL|method|refreshForNewDatabase (BibtexDatabase db)
specifier|public
name|void
name|refreshForNewDatabase
parameter_list|(
name|BibtexDatabase
name|db
parameter_list|)
block|{
name|Set
argument_list|<
name|BibtexEntry
argument_list|>
name|newSet
init|=
operator|new
name|HashSet
argument_list|<
name|BibtexEntry
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|BibtexEntry
name|entry
range|:
name|m_entries
control|)
block|{
name|BibtexEntry
name|sameEntry
init|=
name|db
operator|.
name|getEntryByKey
argument_list|(
name|entry
operator|.
name|getCiteKey
argument_list|()
argument_list|)
decl_stmt|;
comment|/*if (sameEntry == null) {                     System.out.println("Error: could not find entry '"+entry.getCiteKey()+"'");                 } else {                     System.out.println("'"+entry.getCiteKey()+"' ok");                 }*/
name|newSet
operator|.
name|add
argument_list|(
name|sameEntry
argument_list|)
expr_stmt|;
block|}
name|m_entries
operator|.
name|clear
argument_list|()
expr_stmt|;
name|m_entries
operator|.
name|addAll
argument_list|(
name|newSet
argument_list|)
expr_stmt|;
block|}
DECL|method|getEntries ()
specifier|public
name|Set
argument_list|<
name|BibtexEntry
argument_list|>
name|getEntries
parameter_list|()
block|{
return|return
name|m_entries
return|;
block|}
DECL|method|getTypeId ()
specifier|public
name|String
name|getTypeId
parameter_list|()
block|{
return|return
name|ID
return|;
block|}
DECL|method|getNumEntries ()
specifier|public
name|int
name|getNumEntries
parameter_list|()
block|{
return|return
name|m_entries
operator|.
name|size
argument_list|()
return|;
block|}
block|}
end_class

end_unit

