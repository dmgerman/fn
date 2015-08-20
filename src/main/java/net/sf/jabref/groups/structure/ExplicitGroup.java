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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|model
operator|.
name|database
operator|.
name|BibtexDatabase
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
name|BibtexEntry
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
name|groups
operator|.
name|UndoableChangeAssignment
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
name|logic
operator|.
name|util
operator|.
name|strings
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
name|java
operator|.
name|util
operator|.
name|Collections
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashSet
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Set
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|TreeSet
import|;
end_import

begin_comment
comment|/**  * Select explicit bibtex entries. It is also known as static group.  *  * @author jzieren  */
end_comment

begin_class
DECL|class|ExplicitGroup
specifier|public
class|class
name|ExplicitGroup
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
literal|"ExplicitGroup:"
decl_stmt|;
DECL|field|entries
specifier|private
specifier|final
name|Set
argument_list|<
name|BibtexEntry
argument_list|>
name|entries
init|=
operator|new
name|HashSet
argument_list|<
name|BibtexEntry
argument_list|>
argument_list|()
decl_stmt|;
DECL|method|ExplicitGroup (String name, GroupHierarchyType context)
specifier|public
name|ExplicitGroup
parameter_list|(
name|String
name|name
parameter_list|,
name|GroupHierarchyType
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
name|ExplicitGroup
operator|.
name|ID
argument_list|)
condition|)
block|{
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
name|ExplicitGroup
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
name|GroupHierarchyType
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
name|GroupHierarchyType
operator|.
name|getByNumber
argument_list|(
name|context
argument_list|)
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
comment|/**      * Called only when created fromString      */
DECL|method|addEntries (QuotedStringTokenizer tok, BibtexDatabase db)
specifier|private
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
name|StringUtil
operator|.
name|unquote
argument_list|(
name|tok
operator|.
name|nextToken
argument_list|()
argument_list|,
name|AbstractGroup
operator|.
name|QUOTE_CHAR
argument_list|)
argument_list|)
expr_stmt|;
name|Collections
operator|.
name|addAll
argument_list|(
name|this
operator|.
name|entries
argument_list|,
name|entries
argument_list|)
expr_stmt|;
block|}
block|}
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
annotation|@
name|Override
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
annotation|@
name|Override
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
name|entries
operator|.
name|length
operator|==
literal|0
condition|)
block|{
return|return
literal|null
return|;
comment|// nothing to do
block|}
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
name|this
operator|.
name|entries
argument_list|)
decl_stmt|;
name|Collections
operator|.
name|addAll
argument_list|(
name|this
operator|.
name|entries
argument_list|,
name|entries
argument_list|)
expr_stmt|;
return|return
operator|new
name|UndoableChangeAssignment
argument_list|(
name|entriesBeforeEdit
argument_list|,
name|this
operator|.
name|entries
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
name|entries
operator|.
name|add
argument_list|(
name|entry
argument_list|)
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
name|entries
operator|.
name|length
operator|==
literal|0
condition|)
block|{
return|return
literal|null
return|;
comment|// nothing to do
block|}
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
name|this
operator|.
name|entries
argument_list|)
decl_stmt|;
for|for
control|(
name|BibtexEntry
name|entry
range|:
name|entries
control|)
block|{
name|this
operator|.
name|entries
operator|.
name|remove
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
return|return
operator|new
name|UndoableChangeAssignment
argument_list|(
name|entriesBeforeEdit
argument_list|,
name|this
operator|.
name|entries
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
name|entries
operator|.
name|remove
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
return|return
name|entries
operator|.
name|contains
argument_list|(
name|entry
argument_list|)
return|;
block|}
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
name|name
argument_list|,
name|context
argument_list|)
decl_stmt|;
name|copy
operator|.
name|entries
operator|.
name|addAll
argument_list|(
name|entries
argument_list|)
expr_stmt|;
return|return
name|copy
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
name|ExplicitGroup
operator|)
condition|)
block|{
return|return
literal|false
return|;
block|}
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
name|entries
operator|.
name|size
argument_list|()
operator|!=
name|other
operator|.
name|entries
operator|.
name|size
argument_list|()
condition|)
block|{
return|return
literal|false
return|;
comment|// add/remove
block|}
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
name|entries
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
block|{
name|keys
operator|.
name|add
argument_list|(
name|key
argument_list|)
expr_stmt|;
block|}
block|}
for|for
control|(
name|BibtexEntry
name|m_entry
range|:
name|other
operator|.
name|entries
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
block|{
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
block|{
return|return
literal|false
return|;
block|}
block|}
block|}
if|if
condition|(
operator|!
name|keys
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
literal|false
return|;
block|}
return|return
name|other
operator|.
name|name
operator|.
name|equals
argument_list|(
name|name
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
annotation|@
name|Override
DECL|method|toString ()
specifier|public
name|String
name|toString
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
name|ExplicitGroup
operator|.
name|ID
argument_list|)
operator|.
name|append
argument_list|(
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
argument_list|)
operator|.
name|append
argument_list|(
name|AbstractGroup
operator|.
name|SEPARATOR
argument_list|)
operator|.
name|append
argument_list|(
name|context
operator|.
name|ordinal
argument_list|()
argument_list|)
operator|.
name|append
argument_list|(
name|AbstractGroup
operator|.
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
name|entries
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
name|isEmpty
argument_list|()
condition|)
block|{
name|sortedKeys
operator|.
name|add
argument_list|(
name|s
argument_list|)
expr_stmt|;
block|}
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
name|StringUtil
operator|.
name|quote
argument_list|(
name|sortedKey
argument_list|,
name|AbstractGroup
operator|.
name|SEPARATOR
argument_list|,
name|AbstractGroup
operator|.
name|QUOTE_CHAR
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
name|AbstractGroup
operator|.
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
comment|/**      * Remove all assignments, resulting in an empty group.      */
DECL|method|clearAssignments ()
specifier|public
name|void
name|clearAssignments
parameter_list|()
block|{
name|entries
operator|.
name|clear
argument_list|()
expr_stmt|;
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
literal|false
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
name|ExplicitGroup
operator|.
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
name|Localization
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
name|Localization
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
name|Localization
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
name|Localization
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
annotation|@
name|Override
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
name|entries
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
comment|/*if (sameEntry == null) {                 System.out.println("Error: could not find entry '"+entry.getCiteKey()+"'");             } else {                 System.out.println("'"+entry.getCiteKey()+"' ok");             }*/
name|newSet
operator|.
name|add
argument_list|(
name|sameEntry
argument_list|)
expr_stmt|;
block|}
name|entries
operator|.
name|clear
argument_list|()
expr_stmt|;
name|entries
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
name|entries
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
name|ExplicitGroup
operator|.
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
name|entries
operator|.
name|size
argument_list|()
return|;
block|}
block|}
end_class

end_unit

