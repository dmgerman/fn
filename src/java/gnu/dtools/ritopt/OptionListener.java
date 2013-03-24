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
comment|/**  * OptionListener.java  *  * Version:  *   $Id$  */
end_comment

begin_comment
comment|/**  * This interface is used to receive notification of option processing  * events. Implementors are registered by being passed to an OptionNotifier's  * addOptionListener method.  *  * Option processing events occur when an option is invoked at the command  * line, menu, or is present in an options file.  *  *<hr>  *  *<pre>  * Copyright (C) Damian Ryan Eads, 2001. All Rights Reserved.  *  * ritopt is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.   * ritopt is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *  * You should have received a copy of the GNU General Public License  * along with ritopt; if not, write to the Free Software  * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA  *</pre>  *  * @author Damian Eads  */
end_comment

begin_interface
DECL|interface|OptionListener
specifier|public
interface|interface
name|OptionListener
block|{
comment|/**      * Invoked when an option processing event occurs. Option processing      * events occur when an option is invoked at the command line, menu,      * or is present in an options file.      *      * @param e   An object containing information about the option processing      *            and invocation event.      */
DECL|method|optionInvoked ( OptionEvent e )
specifier|public
name|void
name|optionInvoked
parameter_list|(
name|OptionEvent
name|e
parameter_list|)
function_decl|;
block|}
end_interface

begin_comment
comment|/** OptionListener **/
end_comment

end_unit

