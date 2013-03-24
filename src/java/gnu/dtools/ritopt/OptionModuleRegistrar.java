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
comment|/**  * OptionModuleRegistrar.java  *  * Version:  *   $Id$  */
end_comment

begin_comment
comment|/**  * Implementors are capable of registering option modules and storing them in a  * repository. A parent object may pass its child a reference to an  * OptionModuleRegistrar to preserve abstraction and constrain access to  * registration. This may be preferred so that children may only  * register their OptionModules without performing any administrating the  * repository.<p>  *  * The Options class implements this interface. It is not necessary to refer  * to instances as an OptionModuleRegistrar.<p>  *  *<hr>  *  *<pre>  * Copyright (C) Damian Ryan Eads, 2001. All Rights Reserved.  *  * ritopt is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.   * ritopt is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *  * You should have received a copy of the GNU General Public License  * along with ritopt; if not, write to the Free Software  * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA  *</pre>  *  * @author Damian Eads  */
end_comment

begin_interface
DECL|interface|OptionModuleRegistrar
specifier|public
interface|interface
name|OptionModuleRegistrar
block|{
comment|/**      * Register an option module based on its name.      *      * @param module The option module to register.      */
DECL|method|register ( OptionModule module )
specifier|public
name|void
name|register
parameter_list|(
name|OptionModule
name|module
parameter_list|)
function_decl|;
comment|/**      * Register an option module and associate it with the name passed.      *      * @param name   The name associated with the option module.      * @param module The option module to register.      */
DECL|method|register ( String name, OptionModule module )
specifier|public
name|void
name|register
parameter_list|(
name|String
name|name
parameter_list|,
name|OptionModule
name|module
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

