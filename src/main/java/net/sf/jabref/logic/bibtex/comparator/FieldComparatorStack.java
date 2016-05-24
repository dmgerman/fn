begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.logic.bibtex.comparator
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|bibtex
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
name|List
import|;
end_import

begin_comment
comment|/**  * This class represents a list of comparators. The first Comparator takes precedence,  * and each time a Comparator returns 0, the next one is attempted. If all comparators  * return 0 the final result will be 0.  */
end_comment

begin_class
DECL|class|FieldComparatorStack
specifier|public
class|class
name|FieldComparatorStack
parameter_list|<
name|T
parameter_list|>
implements|implements
name|Comparator
argument_list|<
name|T
argument_list|>
block|{
DECL|field|comparators
specifier|private
specifier|final
name|List
argument_list|<
name|?
extends|extends
name|Comparator
argument_list|<
name|?
super|super
name|T
argument_list|>
argument_list|>
name|comparators
decl_stmt|;
DECL|method|FieldComparatorStack (List<? extends Comparator<? super T>> comparators)
specifier|public
name|FieldComparatorStack
parameter_list|(
name|List
argument_list|<
name|?
extends|extends
name|Comparator
argument_list|<
name|?
super|super
name|T
argument_list|>
argument_list|>
name|comparators
parameter_list|)
block|{
name|this
operator|.
name|comparators
operator|=
name|comparators
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|compare (T o1, T o2)
specifier|public
name|int
name|compare
parameter_list|(
name|T
name|o1
parameter_list|,
name|T
name|o2
parameter_list|)
block|{
for|for
control|(
name|Comparator
argument_list|<
name|?
super|super
name|T
argument_list|>
name|comp
range|:
name|comparators
control|)
block|{
name|int
name|res
init|=
name|comp
operator|.
name|compare
argument_list|(
name|o1
argument_list|,
name|o2
argument_list|)
decl_stmt|;
if|if
condition|(
name|res
operator|!=
literal|0
condition|)
block|{
return|return
name|res
return|;
block|}
block|}
return|return
literal|0
return|;
block|}
block|}
end_class

end_unit
