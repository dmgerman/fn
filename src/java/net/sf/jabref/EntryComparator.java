begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* Copyright (C) 2003 Nizar N. Batada, Morten O. Alver  All programs in this directory and subdirectories are published under the GNU General Public License as described below.  This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.  You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA  Further information about the GNU GPL is available at: http://www.gnu.org/copyleft/gpl.ja.html  */
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
name|Comparator
import|;
end_import

begin_comment
comment|/**  * This implementation of Comparator takes care of most of the details of sorting BibTeX entries in JabRef.  * It is structured as a node in a linked list of comparators, where each node can contain a link to a  * new comparator that decides the ordering (by recursion) if this one can't find a difference. The next  * node, if any, is given at construction time, and an arbitrary number of nodes can be included.  * If the entries are equal by this comparator, and there is no next entry, the entries' unique IDs will  * decide the ordering. Consequently, this comparator can never return 0 unless the entries are the same  * object.  */
end_comment

begin_class
DECL|class|EntryComparator
specifier|public
class|class
name|EntryComparator
implements|implements
name|Comparator
block|{
DECL|field|sortField
name|String
name|sortField
decl_stmt|;
DECL|field|descending
DECL|field|binary
name|boolean
name|descending
decl_stmt|,
name|binary
init|=
literal|false
decl_stmt|;
DECL|field|next
name|Comparator
name|next
decl_stmt|;
DECL|method|EntryComparator (boolean binary, boolean desc, String field, Comparator next)
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
block|}
DECL|method|compare (Object o1, Object o2)
specifier|public
name|int
name|compare
parameter_list|(
name|Object
name|o1
parameter_list|,
name|Object
name|o2
parameter_list|)
throws|throws
name|ClassCastException
block|{
comment|//if (o1 == null) Util.pr("o1 == null");
comment|//if (o2 == null) Util.pr("o2 == null");
comment|/*  The explicit instanceof test is unnecessary, since the          explicit casts below will throw ClassCastException anyway          if there is trouble.       if (!(o1 instanceof BibtexEntry) || !(o2 instanceof BibtexEntry))        throw new ClassCastException("Trouble comparing objects: "+o1.toString()+"\n\n"+o2.toString());*/
name|BibtexEntry
name|e1
init|=
operator|(
name|BibtexEntry
operator|)
name|o1
decl_stmt|,
name|e2
init|=
operator|(
name|BibtexEntry
operator|)
name|o2
decl_stmt|;
if|if
condition|(
name|e1
operator|==
name|e2
condition|)
return|return
literal|0
return|;
comment|//Util.pr("EntryComparator: "+e1+" : "+e2);
name|Object
name|f1
init|=
name|e1
operator|.
name|getField
argument_list|(
name|sortField
argument_list|)
decl_stmt|,
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
operator|!=
literal|null
condition|)
return|return
operator|(
name|f2
operator|==
literal|null
operator|)
condition|?
operator|-
literal|1
else|:
operator|(
name|next
operator|!=
literal|null
condition|?
name|next
operator|.
name|compare
argument_list|(
name|o1
argument_list|,
name|o2
argument_list|)
else|:
name|idCompare
argument_list|(
name|e1
argument_list|,
name|e2
argument_list|)
operator|)
return|;
else|else
return|return
operator|(
name|f2
operator|==
literal|null
operator|)
condition|?
operator|(
name|next
operator|!=
literal|null
condition|?
name|next
operator|.
name|compare
argument_list|(
name|o1
argument_list|,
name|o2
argument_list|)
else|:
name|idCompare
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
comment|// If the field is author or editor, we rearrange names so they are
comment|// sorted according to last name.
if|if
condition|(
name|sortField
operator|.
name|equals
argument_list|(
literal|"author"
argument_list|)
operator|||
name|sortField
operator|.
name|equals
argument_list|(
literal|"editor"
argument_list|)
condition|)
block|{
if|if
condition|(
name|f1
operator|!=
literal|null
condition|)
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
comment|//ImportFormatReader.fixAuthor_lastNameFirst((String)f1);
if|if
condition|(
name|f2
operator|!=
literal|null
condition|)
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
comment|//ImportFormatReader.fixAuthor_lastNameFirst((String)f2);
block|}
elseif|else
if|if
condition|(
name|sortField
operator|.
name|equals
argument_list|(
name|GUIGlobals
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
operator|.
name|getName
argument_list|()
expr_stmt|;
name|f2
operator|=
name|e2
operator|.
name|getType
argument_list|()
operator|.
name|getName
argument_list|()
expr_stmt|;
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
return|return
operator|(
name|next
operator|!=
literal|null
condition|?
name|next
operator|.
name|compare
argument_list|(
name|o1
argument_list|,
name|o2
argument_list|)
else|:
name|idCompare
argument_list|(
name|e1
argument_list|,
name|e2
argument_list|)
operator|)
return|;
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
return|return
operator|-
literal|1
return|;
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
operator|!=
literal|null
operator|)
condition|)
return|return
literal|1
return|;
name|int
name|result
init|=
literal|0
decl_stmt|;
comment|//String ours = ((String)e1.getField(sortField)).toLowerCase(),
comment|//    theirs = ((String)e2.getField(sortField)).toLowerCase();
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
operator|)
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
operator|new
name|Integer
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
operator|(
operator|(
name|f1AsInteger
operator|)
operator|.
name|compareTo
argument_list|(
operator|(
name|Integer
operator|)
name|f2
argument_list|)
operator|)
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
operator|new
name|Integer
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
operator|)
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
decl_stmt|,
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
return|return
operator|(
name|descending
condition|?
name|result
else|:
operator|-
name|result
operator|)
return|;
comment|// Primary sort.
if|if
condition|(
name|next
operator|!=
literal|null
condition|)
return|return
name|next
operator|.
name|compare
argument_list|(
name|o1
argument_list|,
name|o2
argument_list|)
return|;
comment|// Secondary sort if existent.
else|else
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
block|}
DECL|method|idCompare (BibtexEntry b1, BibtexEntry b2)
specifier|private
name|int
name|idCompare
parameter_list|(
name|BibtexEntry
name|b1
parameter_list|,
name|BibtexEntry
name|b2
parameter_list|)
block|{
return|return
operator|(
call|(
name|String
call|)
argument_list|(
name|b1
operator|.
name|getId
argument_list|()
argument_list|)
operator|)
operator|.
name|compareTo
argument_list|(
call|(
name|String
call|)
argument_list|(
name|b2
operator|.
name|getId
argument_list|()
argument_list|)
argument_list|)
return|;
block|}
block|}
end_class

end_unit

