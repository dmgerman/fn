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

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_comment
comment|/**  * ArrayOption.java  *  * Version:  *    $Id$  */
end_comment

begin_comment
comment|/**  * The principal base class used to register option variables that represent  * arrays or Collections. Array options are useful for options which represent  * path lists or file specifications.  *<p>  *   * The preferred derived sub-class implementation is to provide a constructor  * with a single array parameter to allow for simple registration. For example,  * an ArrayOption derived class for int arrays should implement the following  * constructor and accessor.  *   *<pre>  *   MyIntArrayOption( int array[] );  *   int[] getValue();  *</pre>  *   * Although this has no affect on option processing, following this philosophy  * for the public interfaces make it easier for the programmer to use your  * source code.  *   *<hr>  *   *<pre>  * Copyright (C) Damian Ryan Eads, 2001. All Rights Reserved.  *   * ritopt is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.  *   * ritopt is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License  * along with ritopt; if not, write to the Free Software  * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA  *</pre>  *   * @author Damian Ryan Eads  */
end_comment

begin_class
DECL|class|ArrayOption
specifier|public
specifier|abstract
class|class
name|ArrayOption
extends|extends
name|Option
implements|implements
name|OptionArrayable
block|{
comment|/** 	 * Builds and initializes ArrayOption class members, and invokes the Option 	 * constructor. 	 */
DECL|method|ArrayOption ()
specifier|public
name|ArrayOption
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Get an ArrayOption in array form. If the option value is an array of 	 * primitive values, references to wrapper objects are returned. 	 *  	 * @return An array of objects representing the option's value. 	 */
DECL|method|getObjectArray ()
specifier|public
specifier|abstract
name|Object
index|[]
name|getObjectArray
parameter_list|()
function_decl|;
comment|/** 	 * Get a list of objects representing the elements of this array option. 	 *  	 * @return A list of objects representing the option's value. 	 */
DECL|method|getObjectList ()
specifier|public
name|List
argument_list|<
name|Object
argument_list|>
name|getObjectList
parameter_list|()
block|{
return|return
name|java
operator|.
name|util
operator|.
name|Arrays
operator|.
name|asList
argument_list|(
name|getObjectArray
argument_list|()
argument_list|)
return|;
block|}
block|}
end_class

end_unit

