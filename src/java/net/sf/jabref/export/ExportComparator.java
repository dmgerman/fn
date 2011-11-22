begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.export
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|export
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

begin_class
DECL|class|ExportComparator
specifier|public
class|class
name|ExportComparator
implements|implements
name|Comparator
argument_list|<
name|String
index|[]
argument_list|>
block|{
DECL|method|compare (String[] s1, String[] s2)
specifier|public
name|int
name|compare
parameter_list|(
name|String
index|[]
name|s1
parameter_list|,
name|String
index|[]
name|s2
parameter_list|)
block|{
return|return
name|s1
index|[
literal|0
index|]
operator|.
name|compareTo
argument_list|(
name|s2
index|[
literal|0
index|]
argument_list|)
return|;
block|}
block|}
end_class

end_unit

