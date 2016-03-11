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
DECL|package|net.sf.jabref.logic.layout
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|layout
package|;
end_package

begin_comment
comment|/**  * String and integer value.  *  * @author     wegnerj  */
end_comment

begin_class
DECL|class|StringInt
specifier|public
class|class
name|StringInt
implements|implements
name|java
operator|.
name|io
operator|.
name|Serializable
block|{
comment|/**      *  Description of the Field      */
DECL|field|s
specifier|public
name|String
name|s
decl_stmt|;
comment|/**      *  Description of the Field      */
DECL|field|i
specifier|public
specifier|final
name|int
name|i
decl_stmt|;
comment|/**      *  Constructor for the StringInt object      *      * @param  _s  Description of the Parameter      * @param  _i  Description of the Parameter      */
DECL|method|StringInt (final String s, final int i)
specifier|public
name|StringInt
parameter_list|(
specifier|final
name|String
name|s
parameter_list|,
specifier|final
name|int
name|i
parameter_list|)
block|{
name|this
operator|.
name|s
operator|=
name|s
expr_stmt|;
name|this
operator|.
name|i
operator|=
name|i
expr_stmt|;
block|}
block|}
end_class

end_unit
