begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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

begin_comment
comment|/**  * Compares Bibtex entries based on their 'crossref' fields. Entries including  * this field are deemed smaller than entries without this field. This serves  * the purpose of always placing referenced entries after referring entries in  * the .bib file. After this criterion comes comparisons of individual fields.  */
end_comment

begin_class
DECL|class|CrossRefEntryComparator
specifier|public
class|class
name|CrossRefEntryComparator
implements|implements
name|Comparator
argument_list|<
name|BibEntry
argument_list|>
block|{
DECL|field|CROSS_REF_FIELD
specifier|private
specifier|static
specifier|final
name|String
name|CROSS_REF_FIELD
init|=
literal|"crossref"
decl_stmt|;
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
throws|throws
name|ClassCastException
block|{
name|Object
name|f1
init|=
name|e1
operator|.
name|getField
argument_list|(
name|CrossRefEntryComparator
operator|.
name|CROSS_REF_FIELD
argument_list|)
decl_stmt|;
name|Object
name|f2
init|=
name|e2
operator|.
name|getField
argument_list|(
name|CrossRefEntryComparator
operator|.
name|CROSS_REF_FIELD
argument_list|)
decl_stmt|;
if|if
condition|(
name|f1
operator|==
literal|null
operator|&&
name|f2
operator|==
literal|null
condition|)
block|{
return|return
literal|0
return|;
comment|// secComparator.compare(e1, e2);
block|}
if|if
condition|(
name|f1
operator|!=
literal|null
operator|&&
name|f2
operator|!=
literal|null
condition|)
block|{
return|return
literal|0
return|;
comment|// secComparator.compare(e1, e2);
block|}
if|if
condition|(
name|f1
operator|!=
literal|null
condition|)
block|{
return|return
operator|-
literal|1
return|;
block|}
else|else
block|{
return|return
literal|1
return|;
block|}
block|}
block|}
end_class

end_unit

