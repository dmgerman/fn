begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.exporter.layout
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|exporter
operator|.
name|layout
package|;
end_package

begin_comment
comment|/**  * This interface extends LayoutFormatter, adding the capability of taking  * and additional parameter. Such a parameter is specified in the layout file  * by the following construct: \format[MyFormatter(argument){\field}  * If and only if MyFormatter is a class that implements ParamLayoutFormatter,  * it will be set up with the argument given in the parenthesis by way of the  * method setArgument(String). If no argument is given, the formatter will be  * invoked without the setArgument() method being called first.  */
end_comment

begin_interface
DECL|interface|ParamLayoutFormatter
specifier|public
interface|interface
name|ParamLayoutFormatter
extends|extends
name|LayoutFormatter
block|{
comment|/**      * Method for setting the argument of this formatter.      * @param arg A String argument.      */
DECL|method|setArgument (String arg)
name|void
name|setArgument
parameter_list|(
name|String
name|arg
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

