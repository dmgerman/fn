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
comment|/**  * If an option supports array registration and processing then it  * should implement this interface.<p>  *  * For example, let's say we are implementing an integer array option class.  *  *<pre>  * public class MyIntArray extends ArrayOption implements OptionArrayable,  *                                                        OptionModifiable {  *    int values[];  *  *    // rest of implementation goes here.  *  *    public void modify(java.lang.String[] value) {  *       try {  *          int newValues[] = new int[ value.length ];  *          for int( n = 0; n< value.length; n++ ) {  *             newValues[ n ] = Integer.parseInt( value[ n ] );  *          }  *          values[ n ] = newValues;  *       }  *       catch ( NumberFormatException e ) {  *          throw new OptionModificationException( "Not a number." );  *       }  *    }  * }  *</pre>  *  *<hr>  *  *<pre>  * Copyright (C) Damian Ryan Eads, 2001. All Rights Reserved.  *  * ritopt is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.   * ritopt is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *  * You should have received a copy of the GNU General Public License  * along with ritopt; if not, write to the Free Software  * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA  *</pre>  *  * @author Damian Eads  */
end_comment

begin_interface
DECL|interface|OptionArrayable
specifier|public
interface|interface
name|OptionArrayable
extends|extends
name|OptionModifiable
block|{
comment|/**      * Modify an option based on several string representations.      *      * @param     value String representation of the object.      * @exception OptionModificationException Thrown if an error occurs      *                                  during modification of an option.      */
DECL|method|modify ( String value[] )
name|void
name|modify
parameter_list|(
name|String
name|value
index|[]
parameter_list|)
throws|throws
name|OptionModificationException
function_decl|;
block|}
end_interface

end_unit

