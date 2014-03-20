begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.plugin.core
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|plugin
operator|.
name|core
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
name|plugin
operator|.
name|core
operator|.
name|generated
operator|.
name|_JabRefPlugin
import|;
end_import

begin_import
import|import
name|org
operator|.
name|java
operator|.
name|plugin
operator|.
name|PluginLifecycleException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|java
operator|.
name|plugin
operator|.
name|PluginManager
import|;
end_import

begin_comment
comment|/**  * Plug-in class for plug-in net.sf.jabref.core.  *   * Feel free to modify this file, since only the class _JabRefPlugin   * (in the subpackage net.sf.jabref.plugin.core.generated)  * will be overwritten, when you re-run the code generator.  */
end_comment

begin_class
DECL|class|JabRefPlugin
specifier|public
class|class
name|JabRefPlugin
extends|extends
name|_JabRefPlugin
block|{
DECL|method|doStart ()
specifier|public
name|void
name|doStart
parameter_list|()
block|{
comment|// TODO: Will be called when plug-in is started.
block|}
DECL|method|doStop ()
specifier|public
name|void
name|doStop
parameter_list|()
block|{
comment|// TODO: Will be called when plug-in is stopped.
block|}
comment|/** 	 * Retrieve the Plug-in instance from the given manager. 	 *  	 * @param manager 	 *            The manager from which to retrieve the plug-in instance 	 *  	 * @return The requested plug-in or null if not found. 	 */
DECL|method|getInstance (PluginManager manager)
specifier|public
specifier|static
name|JabRefPlugin
name|getInstance
parameter_list|(
name|PluginManager
name|manager
parameter_list|)
block|{
try|try
block|{
return|return
operator|(
name|JabRefPlugin
operator|)
name|manager
operator|.
name|getPlugin
argument_list|(
name|JabRefPlugin
operator|.
name|getId
argument_list|()
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|PluginLifecycleException
name|e
parameter_list|)
block|{
return|return
literal|null
return|;
block|}
catch|catch
parameter_list|(
name|IllegalArgumentException
name|e
parameter_list|)
block|{
return|return
literal|null
return|;
block|}
block|}
block|}
end_class

end_unit

