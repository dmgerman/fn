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
comment|/**  * OptionModifiable.java  *  * Version:  *    $Id$  */
end_comment

begin_comment
comment|/**  * Implementors of this interface are capable of being registered and  * processed. When an option is specified, its modify method is invoked.  *  *<hr>  *  *<pre>  * Copyright (C) Damian Ryan Eads, 2001. All Rights Reserved.  *  * ritopt is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.   * ritopt is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *  * You should have received a copy of the GNU General Public License  * along with ritopt; if not, write to the Free Software  * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA  *</pre>  *  * @author Damian Eads  */
end_comment

begin_interface
DECL|interface|OptionModifiable
specifier|public
interface|interface
name|OptionModifiable
block|{
comment|/**     * Modify an option based on a string representation.     *     * @param     value String representation of the object.     * @exception OptionModificationException Thrown if an error occurs     *                                  during modification of an option.     */
DECL|method|modify ( String value )
name|void
name|modify
parameter_list|(
name|String
name|value
parameter_list|)
throws|throws
name|OptionModificationException
function_decl|;
block|}
end_interface

begin_comment
comment|/** OptionModifiable **/
end_comment

end_unit

