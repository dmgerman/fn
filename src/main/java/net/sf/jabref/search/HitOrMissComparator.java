begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.search
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|search
package|;
end_package

begin_import
import|import
name|ca
operator|.
name|odell
operator|.
name|glazedlists
operator|.
name|matchers
operator|.
name|Matcher
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
comment|/**  * This Comparator compares two objects based on whether none, one of them, or both  * match a given Matcher. It is used to "float" group and search hits in the main table.  */
end_comment

begin_class
DECL|class|HitOrMissComparator
specifier|public
class|class
name|HitOrMissComparator
implements|implements
name|Comparator
argument_list|<
name|BibtexEntry
argument_list|>
block|{
DECL|field|hitOrMiss
specifier|private
specifier|final
name|Matcher
argument_list|<
name|BibtexEntry
argument_list|>
name|hitOrMiss
decl_stmt|;
DECL|method|HitOrMissComparator (Matcher<BibtexEntry> hitOrMiss)
specifier|public
name|HitOrMissComparator
parameter_list|(
name|Matcher
argument_list|<
name|BibtexEntry
argument_list|>
name|hitOrMiss
parameter_list|)
block|{
name|this
operator|.
name|hitOrMiss
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|hitOrMiss
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|compare (BibtexEntry o1, BibtexEntry o2)
specifier|public
name|int
name|compare
parameter_list|(
name|BibtexEntry
name|o1
parameter_list|,
name|BibtexEntry
name|o2
parameter_list|)
block|{
if|if
condition|(
name|hitOrMiss
operator|==
literal|null
condition|)
block|{
return|return
literal|0
return|;
block|}
name|boolean
name|hit1
init|=
name|hitOrMiss
operator|.
name|matches
argument_list|(
name|o1
argument_list|)
decl_stmt|;
name|boolean
name|hit2
init|=
name|hitOrMiss
operator|.
name|matches
argument_list|(
name|o2
argument_list|)
decl_stmt|;
comment|// TODO use Boolean.compareTo when converting to Java8
if|if
condition|(
name|hit1
operator|==
name|hit2
condition|)
block|{
return|return
literal|0
return|;
block|}
else|else
block|{
return|return
name|hit1
condition|?
operator|-
literal|1
else|:
literal|1
return|;
block|}
block|}
block|}
end_class

end_unit

