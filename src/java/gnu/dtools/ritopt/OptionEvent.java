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
comment|/**  * OptionEvent.java  *  * Version:  *    $Id$  */
end_comment

begin_comment
comment|/**  * An event indicating that an option has been invoked.  * When an OptionListener is notified by a NotifyOption, it passes  * an OptionEvent object to all registered listeners. This includes  * the target NotifyOption, a command (NotifyOption passes the long  * option by default), and the option value.<p>  *  *<hr>  *  *<pre>  * Copyright (C) Damian Ryan Eads, 2001. All Rights Reserved.  *  * ritopt is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.   * ritopt is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *  * You should have received a copy of the GNU General Public License  * along with ritopt; if not, write to the Free Software  * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA  *</pre>  *  * @author Damian Eads  */
end_comment

begin_class
DECL|class|OptionEvent
specifier|public
class|class
name|OptionEvent
block|{
comment|/**      * The command string associated with this option processing event.      */
DECL|field|command
specifier|private
name|String
name|command
decl_stmt|;
comment|/**      * The value of the option processing event.      */
DECL|field|value
specifier|private
name|String
name|value
decl_stmt|;
comment|/**      * The target Option in which the option processing event occurred.      */
DECL|field|target
specifier|private
name|Option
name|target
decl_stmt|;
comment|/**      * Constructs an option event with the command set to "Default", the      * value set to the empty string, and the target set to null.      */
DECL|method|OptionEvent ()
specifier|public
name|OptionEvent
parameter_list|()
block|{
name|this
argument_list|(
literal|"Default"
argument_list|,
literal|""
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
comment|/**      * Constructs an option event with the command set to the value passed,      * the value set to the empty string, and the target set to null.      *      * @param command The value to set the command string.      */
DECL|method|OptionEvent ( String command )
specifier|public
name|OptionEvent
parameter_list|(
name|String
name|command
parameter_list|)
block|{
name|this
argument_list|(
name|command
argument_list|,
literal|""
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
comment|/**      * Constructs an option event with the command and value set to the      * values passed, and the target set to null.      *      * @param command The value to set the command string.      * @param value   The value to set the option value.      */
DECL|method|OptionEvent ( String command, String value )
specifier|public
name|OptionEvent
parameter_list|(
name|String
name|command
parameter_list|,
name|String
name|value
parameter_list|)
block|{
name|this
argument_list|(
name|command
argument_list|,
name|value
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
comment|/**      * Constructs an option event with the command set to the long or short      * option (whichever exists), the value set to the current value of      * the option, and the target option set to the option passed. If      * neither the short or long option exist, a value of "Default" is      * assigned.      *      * @param option The option to initialize this OptionEvent.      */
DECL|method|OptionEvent ( Option option )
specifier|public
name|OptionEvent
parameter_list|(
name|Option
name|option
parameter_list|)
block|{
name|this
operator|.
name|target
operator|=
name|option
expr_stmt|;
name|this
operator|.
name|value
operator|=
operator|new
name|String
argument_list|(
name|option
operator|.
name|getStringValue
argument_list|()
argument_list|)
expr_stmt|;
name|String
name|longOption
init|=
name|option
operator|.
name|getLongOption
argument_list|()
decl_stmt|;
name|char
name|shortOption
init|=
name|option
operator|.
name|getShortOption
argument_list|()
decl_stmt|;
if|if
condition|(
name|longOption
operator|!=
literal|null
condition|)
block|{
name|command
operator|=
operator|new
name|String
argument_list|(
name|longOption
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|shortOption
operator|!=
literal|'\0'
condition|)
block|{
name|command
operator|=
operator|new
name|Character
argument_list|(
name|shortOption
argument_list|)
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|command
operator|=
literal|"Default"
expr_stmt|;
block|}
block|}
comment|/**      * Constructs an option event with the command, value, and target      * set to the values passed.      *      * @param command The value to set the command string.      * @param value   The value to set the option value.      * @param target  The target option in which the option processing      *                event occurred.      */
DECL|method|OptionEvent ( String command, String value, Option target )
specifier|public
name|OptionEvent
parameter_list|(
name|String
name|command
parameter_list|,
name|String
name|value
parameter_list|,
name|Option
name|target
parameter_list|)
block|{
name|this
operator|.
name|command
operator|=
name|command
expr_stmt|;
name|this
operator|.
name|value
operator|=
name|value
expr_stmt|;
name|this
operator|.
name|target
operator|=
name|target
expr_stmt|;
block|}
comment|/**      * Returns the command string associated with the option.      *      * @return The command string associated with the option.      */
DECL|method|getCommand ()
specifier|public
name|String
name|getCommand
parameter_list|()
block|{
return|return
name|command
return|;
block|}
comment|/**      * Returns the value associated with the target option.      *      * @return The value associated with the target option.      */
DECL|method|getValue ()
specifier|public
name|String
name|getValue
parameter_list|()
block|{
return|return
name|value
return|;
block|}
comment|/**      * Returns the target option of the option processing event.      *      * @return The target option.      */
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
comment|/**      * Sets the command string to the value passed.      *      * @param command The value to set the command string.      */
DECL|method|setCommand ( String command )
specifier|public
name|void
name|setCommand
parameter_list|(
name|String
name|command
parameter_list|)
block|{
name|this
operator|.
name|command
operator|=
name|command
expr_stmt|;
block|}
comment|/**      * Sets the value of this option event. This value generally should be      * equal to the value of the target option.      *      * @param value   The value of the option event.      */
DECL|method|setValue ( String value )
specifier|public
name|void
name|setValue
parameter_list|(
name|String
name|value
parameter_list|)
block|{
name|this
operator|.
name|value
operator|=
name|value
expr_stmt|;
block|}
comment|/**      * Sets the target option of the option processing event.      *      * @param target   The target option.      */
DECL|method|setTarget ( Option target )
specifier|public
name|void
name|setTarget
parameter_list|(
name|Option
name|target
parameter_list|)
block|{
name|this
operator|.
name|target
operator|=
name|target
expr_stmt|;
block|}
block|}
end_class

end_unit

