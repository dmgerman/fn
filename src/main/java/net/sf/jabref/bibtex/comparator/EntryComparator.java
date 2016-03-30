begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.bibtex.comparator
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|bibtex
operator|.
name|comparator
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
name|bibtex
operator|.
name|InternalBibtexFields
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
name|AuthorList
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
name|Objects
import|;
end_import

begin_comment
comment|/**  * This implementation of Comparator takes care of most of the details of sorting BibTeX entries in JabRef. It is  * structured as a node in a linked list of comparators, where each node can contain a link to a new comparator that  * decides the ordering (by recursion) if this one can't find a difference. The next node, if any, is given at  * construction time, and an arbitrary number of nodes can be included. If the entries are equal by this comparator, and  * there is no next entry, the entries' unique IDs will decide the ordering. Consequently, this comparator can never  * return 0 unless the entries are the same object.  */
end_comment

begin_class
DECL|class|EntryComparator
specifier|public
class|class
name|EntryComparator
implements|implements
name|Comparator
argument_list|<
name|BibEntry
argument_list|>
block|{
DECL|field|sortField
specifier|private
specifier|final
name|String
name|sortField
decl_stmt|;
DECL|field|descending
specifier|private
specifier|final
name|boolean
name|descending
decl_stmt|;
DECL|field|binary
specifier|private
specifier|final
name|boolean
name|binary
decl_stmt|;
DECL|field|numeric
specifier|private
specifier|final
name|boolean
name|numeric
decl_stmt|;
DECL|field|next
specifier|private
specifier|final
name|Comparator
argument_list|<
name|BibEntry
argument_list|>
name|next
decl_stmt|;
DECL|method|EntryComparator (boolean binary, boolean desc, String field, Comparator<BibEntry> next)
specifier|public
name|EntryComparator
parameter_list|(
name|boolean
name|binary
parameter_list|,
name|boolean
name|desc
parameter_list|,
name|String
name|field
parameter_list|,
name|Comparator
argument_list|<
name|BibEntry
argument_list|>
name|next
parameter_list|)
block|{
name|this
operator|.
name|binary
operator|=
name|binary
expr_stmt|;
name|this
operator|.
name|sortField
operator|=
name|field
expr_stmt|;
name|this
operator|.
name|descending
operator|=
name|desc
expr_stmt|;
name|this
operator|.
name|next
operator|=
name|next
expr_stmt|;
name|this
operator|.
name|numeric
operator|=
name|InternalBibtexFields
operator|.
name|isNumeric
argument_list|(
name|sortField
argument_list|)
expr_stmt|;
block|}
DECL|method|EntryComparator (boolean binary, boolean desc, String field)
specifier|public
name|EntryComparator
parameter_list|(
name|boolean
name|binary
parameter_list|,
name|boolean
name|desc
parameter_list|,
name|String
name|field
parameter_list|)
block|{
name|this
operator|.
name|binary
operator|=
name|binary
expr_stmt|;
name|this
operator|.
name|sortField
operator|=
name|field
expr_stmt|;
name|this
operator|.
name|descending
operator|=
name|desc
expr_stmt|;
name|this
operator|.
name|next
operator|=
literal|null
expr_stmt|;
name|this
operator|.
name|numeric
operator|=
name|InternalBibtexFields
operator|.
name|isNumeric
argument_list|(
name|sortField
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|compare (BibEntry e1, BibEntry e2)
specifier|public
name|int
name|compare
parameter_list|(
name|BibEntry
name|e1
parameter_list|,
name|BibEntry
name|e2
parameter_list|)
block|{
if|if
condition|(
name|Objects
operator|.
name|equals
argument_list|(
name|e1
argument_list|,
name|e2
argument_list|)
condition|)
block|{
return|return
literal|0
return|;
block|}
name|Object
name|f1
init|=
name|e1
operator|.
name|getField
argument_list|(
name|sortField
argument_list|)
decl_stmt|;
name|Object
name|f2
init|=
name|e2
operator|.
name|getField
argument_list|(
name|sortField
argument_list|)
decl_stmt|;
if|if
condition|(
name|binary
condition|)
block|{
comment|// We just separate on set and unset fields:
if|if
condition|(
name|f1
operator|==
literal|null
condition|)
block|{
return|return
name|f2
operator|==
literal|null
condition|?
operator|(
name|next
operator|==
literal|null
condition|?
name|idCompare
argument_list|(
name|e1
argument_list|,
name|e2
argument_list|)
else|:
name|next
operator|.
name|compare
argument_list|(
name|e1
argument_list|,
name|e2
argument_list|)
operator|)
else|:
literal|1
return|;
block|}
else|else
block|{
return|return
name|f2
operator|==
literal|null
condition|?
operator|-
literal|1
else|:
operator|(
name|next
operator|==
literal|null
condition|?
name|idCompare
argument_list|(
name|e1
argument_list|,
name|e2
argument_list|)
else|:
name|next
operator|.
name|compare
argument_list|(
name|e1
argument_list|,
name|e2
argument_list|)
operator|)
return|;
block|}
block|}
comment|// If the field is author or editor, we rearrange names so they are
comment|// sorted according to last name.
if|if
condition|(
literal|"author"
operator|.
name|equals
argument_list|(
name|sortField
argument_list|)
operator|||
literal|"editor"
operator|.
name|equals
argument_list|(
name|sortField
argument_list|)
condition|)
block|{
if|if
condition|(
name|f1
operator|!=
literal|null
condition|)
block|{
name|f1
operator|=
name|AuthorList
operator|.
name|fixAuthorForAlphabetization
argument_list|(
operator|(
name|String
operator|)
name|f1
argument_list|)
operator|.
name|toLowerCase
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|f2
operator|!=
literal|null
condition|)
block|{
name|f2
operator|=
name|AuthorList
operator|.
name|fixAuthorForAlphabetization
argument_list|(
operator|(
name|String
operator|)
name|f2
argument_list|)
operator|.
name|toLowerCase
argument_list|()
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|sortField
operator|.
name|equals
argument_list|(
name|BibEntry
operator|.
name|TYPE_HEADER
argument_list|)
condition|)
block|{
comment|// Sort by type.
name|f1
operator|=
name|e1
operator|.
name|getType
argument_list|()
expr_stmt|;
name|f2
operator|=
name|e2
operator|.
name|getType
argument_list|()
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|numeric
condition|)
block|{
try|try
block|{
name|Integer
name|i1
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
operator|(
name|String
operator|)
name|f1
argument_list|)
decl_stmt|;
name|Integer
name|i2
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
operator|(
name|String
operator|)
name|f2
argument_list|)
decl_stmt|;
comment|// Ok, parsing was successful. Update f1 and f2:
name|f1
operator|=
name|i1
expr_stmt|;
name|f2
operator|=
name|i2
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|ex
parameter_list|)
block|{
comment|// Parsing failed. Give up treating these as numbers.
comment|// TODO: should we check which of them failed, and sort based on that?
block|}
block|}
if|if
condition|(
operator|(
name|f1
operator|==
literal|null
operator|)
operator|&&
operator|(
name|f2
operator|==
literal|null
operator|)
condition|)
block|{
return|return
name|next
operator|==
literal|null
condition|?
name|idCompare
argument_list|(
name|e1
argument_list|,
name|e2
argument_list|)
else|:
name|next
operator|.
name|compare
argument_list|(
name|e1
argument_list|,
name|e2
argument_list|)
return|;
block|}
if|if
condition|(
operator|(
name|f1
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|f2
operator|==
literal|null
operator|)
condition|)
block|{
return|return
operator|-
literal|1
return|;
block|}
if|if
condition|(
name|f1
operator|==
literal|null
condition|)
block|{
comment|// f2 != null here automatically
return|return
literal|1
return|;
block|}
name|int
name|result
decl_stmt|;
if|if
condition|(
operator|(
name|f1
operator|instanceof
name|Integer
operator|)
operator|&&
operator|(
name|f2
operator|instanceof
name|Integer
operator|)
condition|)
block|{
name|result
operator|=
operator|-
operator|(
operator|(
name|Integer
operator|)
name|f1
operator|)
operator|.
name|compareTo
argument_list|(
operator|(
name|Integer
operator|)
name|f2
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|f2
operator|instanceof
name|Integer
condition|)
block|{
name|Integer
name|f1AsInteger
init|=
name|Integer
operator|.
name|valueOf
argument_list|(
name|f1
operator|.
name|toString
argument_list|()
argument_list|)
decl_stmt|;
name|result
operator|=
operator|-
name|f1AsInteger
operator|.
name|compareTo
argument_list|(
operator|(
name|Integer
operator|)
name|f2
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|f1
operator|instanceof
name|Integer
condition|)
block|{
name|Integer
name|f2AsInteger
init|=
name|Integer
operator|.
name|valueOf
argument_list|(
name|f2
operator|.
name|toString
argument_list|()
argument_list|)
decl_stmt|;
name|result
operator|=
operator|-
operator|(
operator|(
name|Integer
operator|)
name|f1
operator|)
operator|.
name|compareTo
argument_list|(
name|f2AsInteger
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|String
name|ours
init|=
operator|(
operator|(
name|String
operator|)
name|f1
operator|)
operator|.
name|toLowerCase
argument_list|()
decl_stmt|;
name|String
name|theirs
init|=
operator|(
operator|(
name|String
operator|)
name|f2
operator|)
operator|.
name|toLowerCase
argument_list|()
decl_stmt|;
name|int
name|comp
init|=
name|ours
operator|.
name|compareTo
argument_list|(
name|theirs
argument_list|)
decl_stmt|;
name|result
operator|=
operator|-
name|comp
expr_stmt|;
block|}
if|if
condition|(
name|result
operator|!=
literal|0
condition|)
block|{
return|return
name|descending
condition|?
name|result
else|:
operator|-
name|result
return|;
comment|// Primary sort.
block|}
if|if
condition|(
name|next
operator|==
literal|null
condition|)
block|{
return|return
name|idCompare
argument_list|(
name|e1
argument_list|,
name|e2
argument_list|)
return|;
comment|// If still equal, we use the unique IDs.
block|}
else|else
block|{
return|return
name|next
operator|.
name|compare
argument_list|(
name|e1
argument_list|,
name|e2
argument_list|)
return|;
comment|// Secondary sort if existent.
block|}
block|}
DECL|method|idCompare (BibEntry b1, BibEntry b2)
specifier|private
specifier|static
name|int
name|idCompare
parameter_list|(
name|BibEntry
name|b1
parameter_list|,
name|BibEntry
name|b2
parameter_list|)
block|{
return|return
name|b1
operator|.
name|getId
argument_list|()
operator|.
name|compareTo
argument_list|(
name|b2
operator|.
name|getId
argument_list|()
argument_list|)
return|;
block|}
block|}
end_class

end_unit

