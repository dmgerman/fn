begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* Copyright (C) 2003 Morten O. Alver  All programs in this directory and subdirectories are published under the GNU General Public License as described below.  This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.  You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA  Further information about the GNU GPL is available at: http://www.gnu.org/copyleft/gpl.ja.html  */
end_comment

begin_package
DECL|package|net.sf.jabref
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
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

begin_class
DECL|class|EntrySorter
specifier|public
class|class
name|EntrySorter
implements|implements
name|DatabaseChangeListener
block|{
DECL|field|set
name|TreeSet
name|set
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
comment|//static BibtexEntry[] DUMMY = new BibtexEntry[0];
DECL|field|outdated
specifier|private
name|boolean
name|outdated
init|=
literal|false
decl_stmt|;
DECL|method|EntrySorter (Map entries, Comparator comp)
specifier|public
name|EntrySorter
parameter_list|(
name|Map
name|entries
parameter_list|,
name|Comparator
name|comp
parameter_list|)
block|{
name|set
operator|=
operator|new
name|TreeSet
argument_list|(
name|comp
argument_list|)
expr_stmt|;
name|Set
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
name|Iterator
name|i
init|=
name|keySet
operator|.
name|iterator
argument_list|()
decl_stmt|;
while|while
condition|(
name|i
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|set
operator|.
name|add
argument_list|(
name|entries
operator|.
name|get
argument_list|(
name|i
operator|.
name|next
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|index
argument_list|()
expr_stmt|;
block|}
block|}
DECL|method|index ()
specifier|public
name|void
name|index
parameter_list|()
block|{
comment|// Create aan array of IDs for quick access, since getIdAt() is called by
comment|// getValueAt() in EntryTableModel, which *has* to be efficient.
name|int
name|count
init|=
name|set
operator|.
name|size
argument_list|()
decl_stmt|;
name|idArray
operator|=
operator|new
name|String
index|[
name|count
index|]
expr_stmt|;
name|entryArray
operator|=
operator|new
name|BibtexEntry
index|[
name|count
index|]
expr_stmt|;
name|int
name|piv
init|=
literal|0
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|set
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
comment|//        for (int i=0; i<idArray.length; i++) {
name|BibtexEntry
name|entry
init|=
operator|(
name|BibtexEntry
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|idArray
index|[
name|piv
index|]
operator|=
name|entry
operator|.
name|getId
argument_list|()
expr_stmt|;
name|entryArray
index|[
name|piv
index|]
operator|=
name|entry
expr_stmt|;
name|piv
operator|++
expr_stmt|;
block|}
block|}
DECL|method|isOutdated ()
specifier|public
name|boolean
name|isOutdated
parameter_list|()
block|{
return|return
name|outdated
return|;
block|}
DECL|method|getIdAt (int pos)
specifier|public
name|String
name|getIdAt
parameter_list|(
name|int
name|pos
parameter_list|)
block|{
return|return
name|idArray
index|[
name|pos
index|]
return|;
comment|//return ((BibtexEntry)(entryArray[pos])).getId();
block|}
DECL|method|getEntryAt (int pos)
specifier|public
name|BibtexEntry
name|getEntryAt
parameter_list|(
name|int
name|pos
parameter_list|)
block|{
return|return
name|entryArray
index|[
name|pos
index|]
return|;
block|}
DECL|method|getEntryCount ()
specifier|public
name|int
name|getEntryCount
parameter_list|()
block|{
if|if
condition|(
name|entryArray
operator|!=
literal|null
condition|)
return|return
name|entryArray
operator|.
name|length
return|;
else|else
return|return
literal|0
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
if|if
condition|(
name|e
operator|.
name|getType
argument_list|()
operator|==
name|DatabaseChangeEvent
operator|.
name|ADDED_ENTRY
condition|)
block|{
name|set
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
name|REMOVED_ENTRY
condition|)
block|{
name|set
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
name|CHANGING_ENTRY
condition|)
block|{
name|set
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
name|CHANGED_ENTRY
condition|)
block|{
comment|// Remove and re-add the entry, to make sure it is in the
comment|// correct sort position.
name|set
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
block|}
block|}
end_class

end_unit

