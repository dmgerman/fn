begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.gui
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
package|;
end_package

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
name|Map
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
name|net
operator|.
name|sf
operator|.
name|jabref
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
name|DatabaseChangeEvent
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
name|DatabaseChangeListener
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
name|IdComparator
import|;
end_import

begin_import
import|import
name|ca
operator|.
name|odell
operator|.
name|glazedlists
operator|.
name|BasicEventList
import|;
end_import

begin_import
import|import
name|ca
operator|.
name|odell
operator|.
name|glazedlists
operator|.
name|EventList
import|;
end_import

begin_class
DECL|class|GlazedEntrySorter
specifier|public
class|class
name|GlazedEntrySorter
implements|implements
name|DatabaseChangeListener
block|{
DECL|field|list
name|EventList
argument_list|<
name|BibtexEntry
argument_list|>
name|list
decl_stmt|;
DECL|field|idArray
name|String
index|[]
name|idArray
decl_stmt|;
DECL|field|entryArray
name|BibtexEntry
index|[]
name|entryArray
decl_stmt|;
DECL|method|GlazedEntrySorter (Map<String, BibtexEntry> entries)
specifier|public
name|GlazedEntrySorter
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|BibtexEntry
argument_list|>
name|entries
parameter_list|)
block|{
name|list
operator|=
operator|new
name|BasicEventList
argument_list|<
name|BibtexEntry
argument_list|>
argument_list|()
expr_stmt|;
name|list
operator|.
name|getReadWriteLock
argument_list|()
operator|.
name|writeLock
argument_list|()
operator|.
name|lock
argument_list|()
expr_stmt|;
name|Set
argument_list|<
name|String
argument_list|>
name|keySet
init|=
name|entries
operator|.
name|keySet
argument_list|()
decl_stmt|;
if|if
condition|(
name|keySet
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|String
name|aKeySet
range|:
name|keySet
control|)
block|{
name|list
operator|.
name|add
argument_list|(
name|entries
operator|.
name|get
argument_list|(
name|aKeySet
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
comment|// Sort the list so it is ordered according to creation (or read) order
comment|// when the table is unsorted.
name|Collections
operator|.
name|sort
argument_list|(
name|list
argument_list|,
operator|new
name|IdComparator
argument_list|()
argument_list|)
expr_stmt|;
name|list
operator|.
name|getReadWriteLock
argument_list|()
operator|.
name|writeLock
argument_list|()
operator|.
name|unlock
argument_list|()
expr_stmt|;
block|}
DECL|method|getTheList ()
specifier|public
name|EventList
argument_list|<
name|BibtexEntry
argument_list|>
name|getTheList
parameter_list|()
block|{
return|return
name|list
return|;
block|}
DECL|method|databaseChanged (DatabaseChangeEvent e)
specifier|public
name|void
name|databaseChanged
parameter_list|(
name|DatabaseChangeEvent
name|e
parameter_list|)
block|{
name|list
operator|.
name|getReadWriteLock
argument_list|()
operator|.
name|writeLock
argument_list|()
operator|.
name|lock
argument_list|()
expr_stmt|;
if|if
condition|(
name|e
operator|.
name|getType
argument_list|()
operator|==
name|DatabaseChangeEvent
operator|.
name|ChangeType
operator|.
name|ADDED_ENTRY
condition|)
block|{
name|list
operator|.
name|add
argument_list|(
name|e
operator|.
name|getEntry
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|e
operator|.
name|getType
argument_list|()
operator|==
name|DatabaseChangeEvent
operator|.
name|ChangeType
operator|.
name|REMOVED_ENTRY
condition|)
block|{
name|list
operator|.
name|remove
argument_list|(
name|e
operator|.
name|getEntry
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|e
operator|.
name|getType
argument_list|()
operator|==
name|DatabaseChangeEvent
operator|.
name|ChangeType
operator|.
name|CHANGED_ENTRY
condition|)
block|{
name|int
name|index
init|=
name|list
operator|.
name|indexOf
argument_list|(
name|e
operator|.
name|getEntry
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|index
operator|!=
operator|-
literal|1
condition|)
block|{
comment|// SpecialFieldUtils.syncSpecialFieldsFromKeywords update an entry during
comment|// DatabaseChangeEvent.ADDED_ENTRY
comment|// thus,
name|list
operator|.
name|set
argument_list|(
name|index
argument_list|,
name|e
operator|.
name|getEntry
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
name|list
operator|.
name|getReadWriteLock
argument_list|()
operator|.
name|writeLock
argument_list|()
operator|.
name|unlock
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

