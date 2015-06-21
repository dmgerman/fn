begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 M. Spiegel     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.imports
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|imports
package|;
end_package

begin_class
DECL|class|BooleanAssign
specifier|public
class|class
name|BooleanAssign
block|{
DECL|field|value
name|boolean
name|value
decl_stmt|;
comment|/**      * @param b      */
DECL|method|BooleanAssign (boolean b)
specifier|public
name|BooleanAssign
parameter_list|(
name|boolean
name|b
parameter_list|)
block|{
name|setValue
argument_list|(
name|b
argument_list|)
expr_stmt|;
block|}
DECL|method|setValue (boolean value)
specifier|public
name|void
name|setValue
parameter_list|(
name|boolean
name|value
parameter_list|)
block|{
name|this
operator|.
name|value
operator|=
name|value
expr_stmt|;
block|}
DECL|method|getValue ()
specifier|public
name|boolean
name|getValue
parameter_list|()
block|{
return|return
operator|(
name|value
operator|)
return|;
block|}
block|}
end_class

end_unit

