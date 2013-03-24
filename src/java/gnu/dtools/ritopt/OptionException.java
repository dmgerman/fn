begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|gnu.dtools.ritopt
package|package
name|gnu
operator|.
name|dtools
operator|.
name|ritopt
package|;
end_package

begin_comment
comment|/**  * OptionException.java  *  * Version:  *    $Id$  */
end_comment

begin_comment
comment|/**  * An OptionException is thrown when an error occurs during option processing,  * modification, or registration. This class is the base class for all  * run-time based option processing exceptions. A class must throw an instance  * of one of the derived classes as this implementation is abstract.  *  *<pre>  * Copyright (C) Damian Ryan Eads, 2001. All Rights Reserved.  *  * ritopt is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.   * ritopt is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *  * You should have received a copy of the GNU General Public License  * along with ritopt; if not, write to the Free Software  * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA  *</pre>  *  * @author Damian Eads  */
end_comment

begin_class
DECL|class|OptionException
specifier|public
specifier|abstract
class|class
name|OptionException
extends|extends
name|RuntimeException
block|{
comment|/**      * Constructs an OptionException.      *      * @param msg The exception message.      */
DECL|method|OptionException ( String msg )
name|OptionException
parameter_list|(
name|String
name|msg
parameter_list|)
block|{
name|super
argument_list|(
name|msg
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

