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

begin_comment
comment|/**  * A class implementing this interface can provided as a receiver for error messages originating  * in a thread that can't return any value or throw any exceptions. E.g. net.sf.jabref.DatabaseSearch.  *  * The point is that the worker thread doesn't need to know what interface it is working against,  * since the ErrorMessageDisplay implementer will be responsible for displaying the error message.  */
end_comment

begin_interface
DECL|interface|ErrorMessageDisplay
specifier|public
interface|interface
name|ErrorMessageDisplay
block|{
comment|/**      * An error has occured.      * @param errorMessage Error message.      */
DECL|method|reportError (String errorMessage)
specifier|public
name|void
name|reportError
parameter_list|(
name|String
name|errorMessage
parameter_list|)
function_decl|;
comment|/**      * An error has occured.      * @param errorMessage Error message.      * @param exception Exception representing the error condition.      */
DECL|method|reportError (String errorMessage, Exception exception)
specifier|public
name|void
name|reportError
parameter_list|(
name|String
name|errorMessage
parameter_list|,
name|Exception
name|exception
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

