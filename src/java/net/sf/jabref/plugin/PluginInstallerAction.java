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
name|MnemonicAwareAction
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
name|Globals
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
name|GUIGlobals
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|ActionEvent
import|;
end_import

begin_comment
comment|/**  * Created by IntelliJ IDEA.  * User: alver  * Date: Mar 27, 2009  * Time: 11:33:56 PM  * To change this template use File | Settings | File Templates.  */
end_comment

begin_class
DECL|class|PluginInstallerAction
specifier|public
class|class
name|PluginInstallerAction
extends|extends
name|MnemonicAwareAction
block|{
DECL|field|frame
specifier|private
name|JabRefFrame
name|frame
decl_stmt|;
DECL|method|PluginInstallerAction (JabRefFrame frame)
specifier|public
name|PluginInstallerAction
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|)
block|{
name|super
argument_list|(
name|GUIGlobals
operator|.
name|getImage
argument_list|(
literal|"plugin"
argument_list|)
argument_list|)
expr_stmt|;
comment|//System.out.println();
name|this
operator|.
name|frame
operator|=
name|frame
expr_stmt|;
name|putValue
argument_list|(
name|NAME
argument_list|,
name|Globals
operator|.
name|menuTitle
argument_list|(
literal|"Manage plugins"
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|actionPerformed (ActionEvent actionEvent)
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|actionEvent
parameter_list|)
block|{
name|ManagePluginsDialog
name|mpd
init|=
operator|new
name|ManagePluginsDialog
argument_list|(
name|frame
argument_list|)
decl_stmt|;
name|mpd
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

