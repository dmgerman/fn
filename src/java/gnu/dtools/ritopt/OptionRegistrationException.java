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
comment|/**  * OptionRegistrationException.java  *  * Version:  *   $Id$  */
end_comment

begin_comment
comment|/**  * This exception indicates that an error has occurred during registration  * of an option, registrar, or module.  *  *<pre>  * Copyright (C) Damian Ryan Eads, 2001. All Rights Reserved.  *  * ritopt is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.   * ritopt is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *  * You should have received a copy of the GNU General Public License  * along with ritopt; if not, write to the Free Software  * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA  *</pre>  *  * @author Damian Eads  */
end_comment

begin_class
DECL|class|OptionRegistrationException
specifier|public
class|class
name|OptionRegistrationException
extends|extends
name|OptionException
block|{
comment|/**      * The target option associated with the registration failure.      */
DECL|field|target
specifier|private
name|Option
name|target
decl_stmt|;
comment|/**      * Construct an OptionRegistrationException.      *      * @param msg The exception message.      */
DECL|method|OptionRegistrationException ( String msg )
specifier|public
name|OptionRegistrationException
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
comment|/**      * Construct an OptionRegisrationException and initialize its members      * with the message and target passed.      *      * @param msg     An exception message.      * @param target  The target option that caused the registration failure.      */
DECL|method|OptionRegistrationException ( String msg, Option target )
specifier|public
name|OptionRegistrationException
parameter_list|(
name|String
name|msg
parameter_list|,
name|Option
name|target
parameter_list|)
block|{
name|super
argument_list|(
name|msg
argument_list|)
expr_stmt|;
name|this
operator|.
name|target
operator|=
name|target
expr_stmt|;
block|}
comment|/**      * Returns the target option associated with the registration failure.      *      * @return The target option.      */
DECL|method|getTarget ()
specifier|public
name|Option
name|getTarget
parameter_list|()
block|{
return|return
name|target
return|;
block|}
block|}
end_class

end_unit

