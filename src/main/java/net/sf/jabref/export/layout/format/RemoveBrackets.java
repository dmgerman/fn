begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_comment
comment|///////////////////////////////////////////////////////////////////////////////
end_comment

begin_comment
comment|//  Filename: $RCSfile$
end_comment

begin_comment
comment|//  Purpose:  Atom representation.
end_comment

begin_comment
comment|//  Language: Java
end_comment

begin_comment
comment|//  Compiler: JDK 1.4
end_comment

begin_comment
comment|//  Authors:  Joerg K. Wegner
end_comment

begin_comment
comment|//  Version:  $Revision$
end_comment

begin_comment
comment|//            $Date$
end_comment

begin_comment
comment|//            $Author$
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//  Copyright (c) Dept. Computer Architecture, University of Tuebingen, Germany
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//  This program is free software; you can redistribute it and/or modify
end_comment

begin_comment
comment|//  it under the terms of the GNU General Public License as published by
end_comment

begin_comment
comment|//  the Free Software Foundation version 2 of the License.
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//  This program is distributed in the hope that it will be useful,
end_comment

begin_comment
comment|//  but WITHOUT ANY WARRANTY; without even the implied warranty of
end_comment

begin_comment
comment|//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
end_comment

begin_comment
comment|//  GNU General Public License for more details.
end_comment

begin_comment
comment|///////////////////////////////////////////////////////////////////////////////
end_comment

begin_package
DECL|package|net.sf.jabref.export.layout.format
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|export
operator|.
name|layout
operator|.
name|format
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
name|export
operator|.
name|layout
operator|.
name|LayoutFormatter
import|;
end_import

begin_comment
comment|/**  * Remove brackets formatter.  */
end_comment

begin_class
DECL|class|RemoveBrackets
specifier|public
class|class
name|RemoveBrackets
implements|implements
name|LayoutFormatter
block|{
annotation|@
name|Override
DECL|method|format (String fieldText)
specifier|public
name|String
name|format
parameter_list|(
name|String
name|fieldText
parameter_list|)
block|{
name|String
name|fieldEntry
init|=
name|fieldText
decl_stmt|;
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|(
name|fieldEntry
operator|.
name|length
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|fieldEntry
operator|.
name|length
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
comment|//System.out.print(fieldEntry.charAt(i));
if|if
condition|(
name|fieldEntry
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
operator|!=
literal|'{'
operator|&&
name|fieldEntry
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
operator|!=
literal|'}'
condition|)
block|{
comment|//System.out.print(fieldEntry.charAt(i));
name|sb
operator|.
name|append
argument_list|(
name|fieldEntry
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
name|fieldEntry
operator|=
name|sb
operator|.
name|toString
argument_list|()
expr_stmt|;
return|return
name|fieldEntry
return|;
block|}
block|}
end_class

end_unit

