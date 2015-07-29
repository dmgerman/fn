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
comment|/**  * Replace a non-command tilde ~ by a space.  *   * Usefull for formatting Latex code.  */
end_comment

begin_class
DECL|class|RemoveTilde
specifier|public
class|class
name|RemoveTilde
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
name|StringBuilder
name|result
init|=
operator|new
name|StringBuilder
argument_list|(
name|fieldText
operator|.
name|length
argument_list|()
argument_list|)
decl_stmt|;
name|char
index|[]
name|c
init|=
name|fieldText
operator|.
name|toCharArray
argument_list|()
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
name|c
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|c
index|[
name|i
index|]
operator|!=
literal|'~'
condition|)
block|{
name|result
operator|.
name|append
argument_list|(
name|c
index|[
name|i
index|]
argument_list|)
expr_stmt|;
comment|// Skip the next character if the current one is a backslash
if|if
condition|(
name|c
index|[
name|i
index|]
operator|==
literal|'\\'
operator|&&
name|i
operator|+
literal|1
operator|<
name|c
operator|.
name|length
condition|)
block|{
name|i
operator|++
expr_stmt|;
name|result
operator|.
name|append
argument_list|(
name|c
index|[
name|i
index|]
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|result
operator|.
name|append
argument_list|(
literal|' '
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|result
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
end_class

end_unit

