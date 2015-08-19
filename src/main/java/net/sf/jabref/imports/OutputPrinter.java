begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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

begin_interface
DECL|interface|OutputPrinter
specifier|public
interface|interface
name|OutputPrinter
block|{
DECL|method|setStatus (String s)
name|void
name|setStatus
parameter_list|(
name|String
name|s
parameter_list|)
function_decl|;
DECL|method|showMessage (Object message, String title, int msgType)
name|void
name|showMessage
parameter_list|(
name|Object
name|message
parameter_list|,
name|String
name|title
parameter_list|,
name|int
name|msgType
parameter_list|)
function_decl|;
DECL|method|showMessage (String string)
name|void
name|showMessage
parameter_list|(
name|String
name|string
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

