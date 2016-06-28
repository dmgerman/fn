begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.  This program is free software; you can redistribute it and/or modify  it under the terms of the GNU General Public License as published by  the Free Software Foundation; either version 2 of the License, or  (at your option) any later version.   This program is distributed in the hope that it will be useful,  but WITHOUT ANY WARRANTY; without even the implied warranty of  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  GNU General Public License for more details.   You should have received a copy of the GNU General Public License along  with this program; if not, write to the Free Software Foundation, Inc.,  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
end_comment

begin_package
DECL|package|net.sf.jabref.logic.groups
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
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
name|Collections
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
name|model
operator|.
name|FieldChange
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

begin_class
DECL|class|EntriesGroupChange
specifier|public
class|class
name|EntriesGroupChange
block|{
DECL|field|oldEntries
specifier|private
name|Set
argument_list|<
name|BibEntry
argument_list|>
name|oldEntries
decl_stmt|;
DECL|field|newEntries
specifier|private
name|Set
argument_list|<
name|BibEntry
argument_list|>
name|newEntries
decl_stmt|;
DECL|field|entryChanges
specifier|private
name|List
argument_list|<
name|FieldChange
argument_list|>
name|entryChanges
decl_stmt|;
DECL|method|EntriesGroupChange (Set<BibEntry> oldEntries, Set<BibEntry> newEntries)
specifier|public
name|EntriesGroupChange
parameter_list|(
name|Set
argument_list|<
name|BibEntry
argument_list|>
name|oldEntries
parameter_list|,
name|Set
argument_list|<
name|BibEntry
argument_list|>
name|newEntries
parameter_list|)
block|{
name|this
argument_list|(
name|oldEntries
argument_list|,
name|newEntries
argument_list|,
name|Collections
operator|.
name|emptyList
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|EntriesGroupChange (List<FieldChange> entryChanges)
specifier|public
name|EntriesGroupChange
parameter_list|(
name|List
argument_list|<
name|FieldChange
argument_list|>
name|entryChanges
parameter_list|)
block|{
name|this
argument_list|(
name|Collections
operator|.
name|emptySet
argument_list|()
argument_list|,
name|Collections
operator|.
name|emptySet
argument_list|()
argument_list|,
name|entryChanges
argument_list|)
expr_stmt|;
block|}
DECL|method|EntriesGroupChange (Set<BibEntry> oldEntries, Set<BibEntry> newEntries, List<FieldChange> entryChanges)
specifier|public
name|EntriesGroupChange
parameter_list|(
name|Set
argument_list|<
name|BibEntry
argument_list|>
name|oldEntries
parameter_list|,
name|Set
argument_list|<
name|BibEntry
argument_list|>
name|newEntries
parameter_list|,
name|List
argument_list|<
name|FieldChange
argument_list|>
name|entryChanges
parameter_list|)
block|{
name|this
operator|.
name|oldEntries
operator|=
name|oldEntries
expr_stmt|;
name|this
operator|.
name|newEntries
operator|=
name|newEntries
expr_stmt|;
name|this
operator|.
name|entryChanges
operator|=
name|entryChanges
expr_stmt|;
block|}
DECL|method|getOldEntries ()
specifier|public
name|Set
argument_list|<
name|BibEntry
argument_list|>
name|getOldEntries
parameter_list|()
block|{
return|return
name|oldEntries
return|;
block|}
DECL|method|getNewEntries ()
specifier|public
name|Set
argument_list|<
name|BibEntry
argument_list|>
name|getNewEntries
parameter_list|()
block|{
return|return
name|newEntries
return|;
block|}
DECL|method|getEntryChanges ()
specifier|public
name|Iterable
argument_list|<
name|FieldChange
argument_list|>
name|getEntryChanges
parameter_list|()
block|{
return|return
name|entryChanges
return|;
block|}
block|}
end_class

end_unit

