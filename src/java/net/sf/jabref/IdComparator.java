begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
comment|/**  * Comparator for sorting BibtexEntry objects based on their ID. This  * can be used to sort entries back into the order they were created,  * provided the IDs given to entries are lexically monotonically increasing.  */
end_comment

begin_class
DECL|class|IdComparator
specifier|public
class|class
name|IdComparator
implements|implements
name|Comparator
argument_list|<
name|BibtexEntry
argument_list|>
block|{
DECL|method|compare (BibtexEntry one, BibtexEntry two)
specifier|public
name|int
name|compare
parameter_list|(
name|BibtexEntry
name|one
parameter_list|,
name|BibtexEntry
name|two
parameter_list|)
block|{
return|return
name|one
operator|.
name|getId
argument_list|()
operator|.
name|compareTo
argument_list|(
name|two
operator|.
name|getId
argument_list|()
argument_list|)
return|;
block|}
block|}
end_class

end_unit

