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
comment|/**  * Implementors are capable of registering options and storing them in a  * repository. A parent object may pass its child a reference to an  * OptionRegistrar to preserve abstraction and restrict access to  * registration. This may be preferred so that children may only  * register their options without performing any administrating the  * repository.  *<p>  *  * The Options and OptionModule classes implement this interface. It is not  * necessary to refer to instances as an OptionRegistrar.<p>  *  *<hr>  *  *<pre>  * Copyright (C) Damian Ryan Eads, 2001. All Rights Reserved.  *  * ritopt is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.   * ritopt is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *  * You should have received a copy of the GNU General Public License  * along with ritopt; if not, write to the Free Software  * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA  *</pre>  *  * @author Damian Eads  */
end_comment

begin_interface
DECL|interface|OptionRegistrar
specifier|public
interface|interface
name|OptionRegistrar
block|{
comment|/**      * Register an option into the repository as a long option.      *      * @param longName   The long option name.      * @param option     The option to register.      */
DECL|method|register ( String longName, Option option )
specifier|public
name|void
name|register
parameter_list|(
name|String
name|longName
parameter_list|,
name|Option
name|option
parameter_list|)
function_decl|;
comment|/**      * Register an option into the repository as a short option.      *      * @param shortName  The short option name.      * @param option     The option to register.      */
DECL|method|register ( char shortName, Option option )
specifier|public
name|void
name|register
parameter_list|(
name|char
name|shortName
parameter_list|,
name|Option
name|option
parameter_list|)
function_decl|;
comment|/**      * Register an option into the repository both as a short and long option.      *      * @param longOption  The long option name.      * @param shortOption The short option name.      * @param option      The option to register.      */
DECL|method|register ( String longOption, char shortOption, Option option )
specifier|public
name|void
name|register
parameter_list|(
name|String
name|longOption
parameter_list|,
name|char
name|shortOption
parameter_list|,
name|Option
name|option
parameter_list|)
function_decl|;
comment|/**      * Register an option into the repository both as a short and long option.      * Initialize its description with the description passed.      *      * @param longOption  The long option name.      * @param shortOption The short option name.      * @param description The description of the option.      * @param option      The option to register.      */
DECL|method|register ( String longOption, char shortOption, String description, Option option )
specifier|public
name|void
name|register
parameter_list|(
name|String
name|longOption
parameter_list|,
name|char
name|shortOption
parameter_list|,
name|String
name|description
parameter_list|,
name|Option
name|option
parameter_list|)
function_decl|;
comment|/**      * Register an option into the repository both as a short and long option.      * Initialize its description with the description passed.      *      * @param longOption  The long option name.      * @param shortOption The short option name.      * @param description The description of the option.      * @param option      The option to register.      * @param deprecated  A boolean indicating whether an option should      *                    be deprecated.      */
DECL|method|register ( String longOption, char shortOption, String description, Option option, boolean deprecated )
specifier|public
name|void
name|register
parameter_list|(
name|String
name|longOption
parameter_list|,
name|char
name|shortOption
parameter_list|,
name|String
name|description
parameter_list|,
name|Option
name|option
parameter_list|,
name|boolean
name|deprecated
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

