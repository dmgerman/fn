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
comment|/**  * Implementors are capable of being stopped. This interface is used  * by the StreamPrinters in the SimpleProcess class so that it if an  * error has occurred.  *  *<hr>  *  *<pre>  * Copyright (C) Damian Ryan Eads, 2001. All Rights Reserved.  *  * ritopt is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.   * ritopt is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *  * You should have received a copy of the GNU General Public License  * along with ritopt; if not, write to the Free Software  * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA  *</pre>  *  * @author Damian Eads  */
end_comment

begin_interface
DECL|interface|Stoppable
specifier|public
interface|interface
name|Stoppable
block|{
comment|/**      * Stop the implemetor from performing some sort of processing.      */
DECL|method|stop ()
specifier|public
name|void
name|stop
parameter_list|()
function_decl|;
comment|/**      * Returns whether the implementor has stopped.      *      * @return A boolean value.      */
DECL|method|isStopped ()
specifier|public
name|boolean
name|isStopped
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

