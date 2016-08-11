begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2012 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.gui.util.comparator
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|util
operator|.
name|comparator
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

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Optional
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
name|SpecialFields
import|;
end_import

begin_comment
comment|/**  * Comparator that handles the ranking icon column  *  * Based on IconComparator  * Only comparing ranking field  * inverse comparison of ranking as rank5 is higher than rank1  */
end_comment

begin_class
DECL|class|RankingFieldComparator
specifier|public
class|class
name|RankingFieldComparator
implements|implements
name|Comparator
argument_list|<
name|BibEntry
argument_list|>
block|{
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
name|Optional
argument_list|<
name|String
argument_list|>
name|val1
init|=
name|e1
operator|.
name|getFieldOptional
argument_list|(
name|SpecialFields
operator|.
name|FIELDNAME_RANKING
argument_list|)
decl_stmt|;
name|Optional
argument_list|<
name|String
argument_list|>
name|val2
init|=
name|e2
operator|.
name|getFieldOptional
argument_list|(
name|SpecialFields
operator|.
name|FIELDNAME_RANKING
argument_list|)
decl_stmt|;
if|if
condition|(
name|val1
operator|.
name|isPresent
argument_list|()
condition|)
block|{
if|if
condition|(
name|val2
operator|.
name|isPresent
argument_list|()
condition|)
block|{
comment|// val1 is not null AND val2 is not null
name|int
name|compareToRes
init|=
name|val1
operator|.
name|get
argument_list|()
operator|.
name|compareTo
argument_list|(
name|val2
operator|.
name|get
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|compareToRes
operator|==
literal|0
condition|)
block|{
return|return
literal|0
return|;
block|}
else|else
block|{
return|return
name|compareToRes
operator|*
operator|-
literal|1
return|;
block|}
block|}
else|else
block|{
return|return
operator|-
literal|1
return|;
block|}
block|}
else|else
block|{
if|if
condition|(
name|val2
operator|.
name|isPresent
argument_list|()
condition|)
block|{
return|return
literal|1
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
block|}
end_class

end_unit

