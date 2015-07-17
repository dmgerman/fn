begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.plugin
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|plugin
package|;
end_package

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|JabRefFrame
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|SidePaneComponent
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|SidePaneManager
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|*
import|;
end_import

begin_comment
comment|/**  * Created by IntelliJ IDEA.  * User: alver  * Date: Nov 26, 2007  * Time: 5:44:16 PM  * To change this template use File | Settings | File Templates.  */
end_comment

begin_interface
DECL|interface|SidePanePlugin
specifier|public
interface|interface
name|SidePanePlugin
block|{
DECL|method|init (JabRefFrame frame, SidePaneManager manager)
name|void
name|init
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|SidePaneManager
name|manager
parameter_list|)
function_decl|;
DECL|method|getSidePaneComponent ()
name|SidePaneComponent
name|getSidePaneComponent
parameter_list|()
function_decl|;
DECL|method|getMenuItem ()
name|JMenuItem
name|getMenuItem
parameter_list|()
function_decl|;
DECL|method|getShortcutKey ()
name|String
name|getShortcutKey
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

