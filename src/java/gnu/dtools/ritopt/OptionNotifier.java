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
comment|/**  * If an object is able notify and maintain a repository of listeners, it  * should implement this interface even though it is not required. This  * interface expects listener registration and event configuration behavior.  *  *<hr>  *  *<pre>  * Copyright (C) Damian Ryan Eads, 2001. All Rights Reserved.  *  * ritopt is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.   * ritopt is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *  * You should have received a copy of the GNU General Public License  * along with ritopt; if not, write to the Free Software  * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA  *</pre>  *  * @author Damian Eads  */
end_comment

begin_interface
DECL|interface|OptionNotifier
specifier|public
interface|interface
name|OptionNotifier
block|{
comment|/**      * Adds an OptionListener to the notification list.      *      * @param listener The OptionListener to add.      */
DECL|method|addOptionListener ( OptionListener listener )
specifier|public
name|void
name|addOptionListener
parameter_list|(
name|OptionListener
name|listener
parameter_list|)
function_decl|;
comment|/**      * Removes an OptionListener from the notification list.      *      * @param listener The OptionListener to remove.      */
DECL|method|removeOptionListener ( OptionListener listener )
specifier|public
name|void
name|removeOptionListener
parameter_list|(
name|OptionListener
name|listener
parameter_list|)
function_decl|;
comment|/**      * Sets the command sent when an option is invoked.      *      * @param command  The command to send.      */
DECL|method|setOptionCommand ( String command )
specifier|public
name|void
name|setOptionCommand
parameter_list|(
name|String
name|command
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

