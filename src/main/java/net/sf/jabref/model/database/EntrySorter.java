begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.model.database
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|model
operator|.
name|database
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
name|Comparator
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
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
name|BibEntry
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|Log
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|LogFactory
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
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Log
name|LOGGER
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|EntrySorter
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|set
specifier|private
specifier|final
name|List
argument_list|<
name|BibEntry
argument_list|>
name|set
decl_stmt|;
DECL|field|comp
specifier|private
specifier|final
name|Comparator
argument_list|<
name|BibEntry
argument_list|>
name|comp
decl_stmt|;
DECL|field|entryArray
specifier|private
name|BibEntry
index|[]
name|entryArray
decl_stmt|;
DECL|field|changed
specifier|private
name|boolean
name|changed
decl_stmt|;
DECL|method|EntrySorter (List<BibEntry> entries, Comparator<BibEntry> comp)
specifier|public
name|EntrySorter
parameter_list|(
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
parameter_list|,
name|Comparator
argument_list|<
name|BibEntry
argument_list|>
name|comp
parameter_list|)
block|{
name|set
operator|=
name|entries
expr_stmt|;
name|this
operator|.
name|comp
operator|=
name|comp
expr_stmt|;
name|changed
operator|=
literal|true
expr_stmt|;
name|index
argument_list|()
expr_stmt|;
block|}
DECL|method|index ()
specifier|private
name|void
name|index
parameter_list|()
block|{
comment|/*  Old version, from when set was a TreeSet.          // The boolean "changing" is true in the situation that an entry is about to change,         // and has temporarily been removed from the entry set in this sorter. So, if we index         // now, we will cause exceptions other places because one entry has been left out of         // the indexed array. Simply waiting foth this to change can lead to deadlocks,         // so we have no other choice than to return without indexing.         if (changing)             return;         */
synchronized|synchronized
init|(
name|set
init|)
block|{
comment|// Resort if necessary:
if|if
condition|(
name|changed
condition|)
block|{
name|Collections
operator|.
name|sort
argument_list|(
name|set
argument_list|,
name|comp
argument_list|)
expr_stmt|;
name|changed
operator|=
literal|false
expr_stmt|;
block|}
comment|// Create an array of IDs for quick access, since getIdAt() is called by
comment|// getValueAt() in EntryTableModel, which *has* to be efficient.
name|int
name|count
init|=
name|set
operator|.
name|size
argument_list|()
decl_stmt|;
name|entryArray
operator|=
operator|new
name|BibEntry
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
name|BibEntry
name|entry
range|:
name|set
control|)
block|{
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
block|}
DECL|method|getEntryAt (int pos)
specifier|public
name|BibEntry
name|getEntryAt
parameter_list|(
name|int
name|pos
parameter_list|)
block|{
synchronized|synchronized
init|(
name|set
init|)
block|{
return|return
name|entryArray
index|[
name|pos
index|]
return|;
block|}
block|}
DECL|method|getEntryCount ()
specifier|public
name|int
name|getEntryCount
parameter_list|()
block|{
synchronized|synchronized
init|(
name|set
init|)
block|{
if|if
condition|(
name|entryArray
operator|!=
literal|null
condition|)
block|{
return|return
name|entryArray
operator|.
name|length
return|;
block|}
else|else
block|{
return|return
literal|0
return|;
block|}
block|}
block|}
annotation|@
name|Override
DECL|method|databaseChanged (DatabaseChangeEvent e)
specifier|public
name|void
name|databaseChanged
parameter_list|(
name|DatabaseChangeEvent
name|e
parameter_list|)
block|{
synchronized|synchronized
init|(
name|set
init|)
block|{
name|int
name|pos
decl_stmt|;
switch|switch
condition|(
name|e
operator|.
name|getType
argument_list|()
condition|)
block|{
case|case
name|ADDED_ENTRY
case|:
name|pos
operator|=
operator|-
name|Collections
operator|.
name|binarySearch
argument_list|(
name|set
argument_list|,
name|e
operator|.
name|getEntry
argument_list|()
argument_list|,
name|comp
argument_list|)
operator|-
literal|1
expr_stmt|;
name|LOGGER
operator|.
name|debug
argument_list|(
literal|"Insert position = "
operator|+
name|pos
argument_list|)
expr_stmt|;
if|if
condition|(
name|pos
operator|>=
literal|0
condition|)
block|{
name|set
operator|.
name|add
argument_list|(
name|pos
argument_list|,
name|e
operator|.
name|getEntry
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|set
operator|.
name|add
argument_list|(
literal|0
argument_list|,
name|e
operator|.
name|getEntry
argument_list|()
argument_list|)
expr_stmt|;
block|}
break|break;
case|case
name|REMOVED_ENTRY
case|:
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
name|changed
operator|=
literal|true
expr_stmt|;
break|break;
case|case
name|CHANGED_ENTRY
case|:
name|pos
operator|=
name|Collections
operator|.
name|binarySearch
argument_list|(
name|set
argument_list|,
name|e
operator|.
name|getEntry
argument_list|()
argument_list|,
name|comp
argument_list|)
expr_stmt|;
name|int
name|posOld
init|=
name|set
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
name|pos
operator|<
literal|0
condition|)
block|{
name|set
operator|.
name|remove
argument_list|(
name|posOld
argument_list|)
expr_stmt|;
name|set
operator|.
name|add
argument_list|(
operator|-
name|posOld
operator|-
literal|1
argument_list|,
name|e
operator|.
name|getEntry
argument_list|()
argument_list|)
expr_stmt|;
block|}
break|break;
default|default:
break|break;
block|}
block|}
block|}
block|}
end_class

end_unit

