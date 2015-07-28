begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.oo
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|oo
package|;
end_package

begin_comment
comment|/**  * Exception used to indicate that the plugin attempted to set a character format that is  * not defined in the current OpenOffice document.  */
end_comment

begin_class
DECL|class|UndefinedCharacterFormatException
class|class
name|UndefinedCharacterFormatException
extends|extends
name|Exception
block|{
DECL|field|formatName
specifier|private
specifier|final
name|String
name|formatName
decl_stmt|;
DECL|method|UndefinedCharacterFormatException (String formatName)
specifier|public
name|UndefinedCharacterFormatException
parameter_list|(
name|String
name|formatName
parameter_list|)
block|{
name|super
argument_list|()
expr_stmt|;
name|this
operator|.
name|formatName
operator|=
name|formatName
expr_stmt|;
block|}
DECL|method|getFormatName ()
specifier|public
name|String
name|getFormatName
parameter_list|()
block|{
return|return
name|formatName
return|;
block|}
block|}
end_class

end_unit

