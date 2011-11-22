begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.plugin.core.generated
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
operator|.
name|generated
package|;
end_package

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URL
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
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
name|plugin
operator|.
name|util
operator|.
name|RuntimeExtension
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
name|Plugin
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
name|registry
operator|.
name|Extension
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
name|registry
operator|.
name|ExtensionPoint
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|Log
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|LogFactory
import|;
end_import

begin_comment
comment|/**  * Do not modify this file, as it was auto generated and will be overwritten!  * User modifications should go in net.sf.jabref.plugin.core.JabRefPlugin.  */
end_comment

begin_class
DECL|class|_JabRefPlugin
specifier|public
specifier|abstract
class|class
name|_JabRefPlugin
extends|extends
name|Plugin
block|{
DECL|method|getId ()
specifier|public
specifier|static
name|String
name|getId
parameter_list|()
block|{
return|return
literal|"net.sf.jabref.core"
return|;
block|}
DECL|field|log
specifier|static
name|Log
name|log
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|_JabRefPlugin
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|method|getExportFormatTemplateExtensions ()
specifier|public
name|List
argument_list|<
name|ExportFormatTemplateExtension
argument_list|>
name|getExportFormatTemplateExtensions
parameter_list|()
block|{
name|ExtensionPoint
name|extPoint
init|=
name|getManager
argument_list|()
operator|.
name|getRegistry
argument_list|()
operator|.
name|getExtensionPoint
argument_list|(
name|getId
argument_list|()
argument_list|,
literal|"ExportFormatTemplate"
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|ExportFormatTemplateExtension
argument_list|>
name|result
init|=
operator|new
name|ArrayList
argument_list|<
name|ExportFormatTemplateExtension
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Extension
name|ext
range|:
name|extPoint
operator|.
name|getConnectedExtensions
argument_list|()
control|)
block|{
try|try
block|{
name|result
operator|.
name|add
argument_list|(
operator|new
name|ExportFormatTemplateExtension
argument_list|(
name|getManager
argument_list|()
operator|.
name|getPlugin
argument_list|(
name|ext
operator|.
name|getDeclaringPluginDescriptor
argument_list|()
operator|.
name|getId
argument_list|()
argument_list|)
argument_list|,
name|ext
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|PluginLifecycleException
name|e
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"Failed to activate plug-in "
operator|+
name|ext
operator|.
name|getDeclaringPluginDescriptor
argument_list|()
operator|.
name|getId
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|result
return|;
block|}
DECL|class|ExportFormatTemplateExtension
specifier|public
specifier|static
class|class
name|ExportFormatTemplateExtension
extends|extends
name|RuntimeExtension
block|{
DECL|method|ExportFormatTemplateExtension (Plugin declaringPlugin, Extension wrapped)
specifier|public
name|ExportFormatTemplateExtension
parameter_list|(
name|Plugin
name|declaringPlugin
parameter_list|,
name|Extension
name|wrapped
parameter_list|)
block|{
name|super
argument_list|(
name|declaringPlugin
argument_list|,
name|wrapped
argument_list|)
expr_stmt|;
block|}
DECL|method|getDisplayName ()
specifier|public
name|String
name|getDisplayName
parameter_list|()
block|{
return|return
name|getStringParameter
argument_list|(
literal|"displayName"
argument_list|)
return|;
block|}
DECL|method|getConsoleName ()
specifier|public
name|String
name|getConsoleName
parameter_list|()
block|{
return|return
name|getStringParameter
argument_list|(
literal|"consoleName"
argument_list|)
return|;
block|}
DECL|method|getLayoutFilename ()
specifier|public
name|String
name|getLayoutFilename
parameter_list|()
block|{
return|return
name|getStringParameter
argument_list|(
literal|"layoutFilename"
argument_list|)
return|;
block|}
DECL|method|getDirAsUrl ()
specifier|public
name|URL
name|getDirAsUrl
parameter_list|()
block|{
return|return
name|getResourceParameter
argument_list|(
literal|"dir"
argument_list|)
return|;
block|}
DECL|method|getDirAsUrl (String relativePath)
specifier|public
name|URL
name|getDirAsUrl
parameter_list|(
name|String
name|relativePath
parameter_list|)
block|{
return|return
name|getResourceParameter
argument_list|(
literal|"dir"
argument_list|,
name|relativePath
argument_list|)
return|;
block|}
DECL|method|getExtension ()
specifier|public
name|String
name|getExtension
parameter_list|()
block|{
return|return
name|getStringParameter
argument_list|(
literal|"extension"
argument_list|)
return|;
block|}
DECL|method|getEncoding ()
specifier|public
name|String
name|getEncoding
parameter_list|()
block|{
return|return
name|getStringParameter
argument_list|(
literal|"encoding"
argument_list|)
return|;
block|}
block|}
DECL|method|getExportFormatExtensions ()
specifier|public
name|List
argument_list|<
name|ExportFormatExtension
argument_list|>
name|getExportFormatExtensions
parameter_list|()
block|{
name|ExtensionPoint
name|extPoint
init|=
name|getManager
argument_list|()
operator|.
name|getRegistry
argument_list|()
operator|.
name|getExtensionPoint
argument_list|(
name|getId
argument_list|()
argument_list|,
literal|"ExportFormat"
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|ExportFormatExtension
argument_list|>
name|result
init|=
operator|new
name|ArrayList
argument_list|<
name|ExportFormatExtension
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Extension
name|ext
range|:
name|extPoint
operator|.
name|getConnectedExtensions
argument_list|()
control|)
block|{
try|try
block|{
name|result
operator|.
name|add
argument_list|(
operator|new
name|ExportFormatExtension
argument_list|(
name|getManager
argument_list|()
operator|.
name|getPlugin
argument_list|(
name|ext
operator|.
name|getDeclaringPluginDescriptor
argument_list|()
operator|.
name|getId
argument_list|()
argument_list|)
argument_list|,
name|ext
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|PluginLifecycleException
name|e
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"Failed to activate plug-in "
operator|+
name|ext
operator|.
name|getDeclaringPluginDescriptor
argument_list|()
operator|.
name|getId
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|result
return|;
block|}
DECL|class|ExportFormatExtension
specifier|public
specifier|static
class|class
name|ExportFormatExtension
extends|extends
name|RuntimeExtension
block|{
DECL|method|ExportFormatExtension (Plugin declaringPlugin, Extension wrapped)
specifier|public
name|ExportFormatExtension
parameter_list|(
name|Plugin
name|declaringPlugin
parameter_list|,
name|Extension
name|wrapped
parameter_list|)
block|{
name|super
argument_list|(
name|declaringPlugin
argument_list|,
name|wrapped
argument_list|)
expr_stmt|;
block|}
comment|/**          * @return A singleton instance of the class parameter or null if the class could not be found!          */
DECL|method|getExportFormat ()
specifier|public
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|export
operator|.
name|IExportFormat
name|getExportFormat
parameter_list|()
block|{
return|return
operator|(
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|export
operator|.
name|IExportFormat
operator|)
name|getClassParameter
argument_list|(
literal|"exportFormat"
argument_list|)
return|;
block|}
DECL|method|getDisplayName ()
specifier|public
name|String
name|getDisplayName
parameter_list|()
block|{
return|return
name|getStringParameter
argument_list|(
literal|"displayName"
argument_list|)
return|;
block|}
DECL|method|getConsoleName ()
specifier|public
name|String
name|getConsoleName
parameter_list|()
block|{
return|return
name|getStringParameter
argument_list|(
literal|"consoleName"
argument_list|)
return|;
block|}
DECL|method|getExtension ()
specifier|public
name|String
name|getExtension
parameter_list|()
block|{
return|return
name|getStringParameter
argument_list|(
literal|"extension"
argument_list|)
return|;
block|}
block|}
DECL|method|getSidePanePluginExtensions ()
specifier|public
name|List
argument_list|<
name|SidePanePluginExtension
argument_list|>
name|getSidePanePluginExtensions
parameter_list|()
block|{
name|ExtensionPoint
name|extPoint
init|=
name|getManager
argument_list|()
operator|.
name|getRegistry
argument_list|()
operator|.
name|getExtensionPoint
argument_list|(
name|getId
argument_list|()
argument_list|,
literal|"SidePanePlugin"
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|SidePanePluginExtension
argument_list|>
name|result
init|=
operator|new
name|ArrayList
argument_list|<
name|SidePanePluginExtension
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Extension
name|ext
range|:
name|extPoint
operator|.
name|getConnectedExtensions
argument_list|()
control|)
block|{
try|try
block|{
name|result
operator|.
name|add
argument_list|(
operator|new
name|SidePanePluginExtension
argument_list|(
name|getManager
argument_list|()
operator|.
name|getPlugin
argument_list|(
name|ext
operator|.
name|getDeclaringPluginDescriptor
argument_list|()
operator|.
name|getId
argument_list|()
argument_list|)
argument_list|,
name|ext
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|PluginLifecycleException
name|e
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"Failed to activate plug-in "
operator|+
name|ext
operator|.
name|getDeclaringPluginDescriptor
argument_list|()
operator|.
name|getId
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|result
return|;
block|}
DECL|class|SidePanePluginExtension
specifier|public
specifier|static
class|class
name|SidePanePluginExtension
extends|extends
name|RuntimeExtension
block|{
DECL|method|SidePanePluginExtension (Plugin declaringPlugin, Extension wrapped)
specifier|public
name|SidePanePluginExtension
parameter_list|(
name|Plugin
name|declaringPlugin
parameter_list|,
name|Extension
name|wrapped
parameter_list|)
block|{
name|super
argument_list|(
name|declaringPlugin
argument_list|,
name|wrapped
argument_list|)
expr_stmt|;
block|}
comment|/**          * @return A singleton instance of the class parameter or null if the class could not be found!          */
DECL|method|getSidePanePlugin ()
specifier|public
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|plugin
operator|.
name|SidePanePlugin
name|getSidePanePlugin
parameter_list|()
block|{
return|return
operator|(
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|plugin
operator|.
name|SidePanePlugin
operator|)
name|getClassParameter
argument_list|(
literal|"sidePanePlugin"
argument_list|)
return|;
block|}
DECL|method|getName ()
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|getStringParameter
argument_list|(
literal|"name"
argument_list|)
return|;
block|}
DECL|method|getDescription ()
specifier|public
name|String
name|getDescription
parameter_list|()
block|{
return|return
name|getStringParameter
argument_list|(
literal|"description"
argument_list|)
return|;
block|}
block|}
DECL|method|getEntryFetcherExtensions ()
specifier|public
name|List
argument_list|<
name|EntryFetcherExtension
argument_list|>
name|getEntryFetcherExtensions
parameter_list|()
block|{
name|ExtensionPoint
name|extPoint
init|=
name|getManager
argument_list|()
operator|.
name|getRegistry
argument_list|()
operator|.
name|getExtensionPoint
argument_list|(
name|getId
argument_list|()
argument_list|,
literal|"EntryFetcher"
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|EntryFetcherExtension
argument_list|>
name|result
init|=
operator|new
name|ArrayList
argument_list|<
name|EntryFetcherExtension
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Extension
name|ext
range|:
name|extPoint
operator|.
name|getConnectedExtensions
argument_list|()
control|)
block|{
try|try
block|{
name|result
operator|.
name|add
argument_list|(
operator|new
name|EntryFetcherExtension
argument_list|(
name|getManager
argument_list|()
operator|.
name|getPlugin
argument_list|(
name|ext
operator|.
name|getDeclaringPluginDescriptor
argument_list|()
operator|.
name|getId
argument_list|()
argument_list|)
argument_list|,
name|ext
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|PluginLifecycleException
name|e
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"Failed to activate plug-in "
operator|+
name|ext
operator|.
name|getDeclaringPluginDescriptor
argument_list|()
operator|.
name|getId
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|result
return|;
block|}
DECL|class|EntryFetcherExtension
specifier|public
specifier|static
class|class
name|EntryFetcherExtension
extends|extends
name|RuntimeExtension
block|{
DECL|method|EntryFetcherExtension (Plugin declaringPlugin, Extension wrapped)
specifier|public
name|EntryFetcherExtension
parameter_list|(
name|Plugin
name|declaringPlugin
parameter_list|,
name|Extension
name|wrapped
parameter_list|)
block|{
name|super
argument_list|(
name|declaringPlugin
argument_list|,
name|wrapped
argument_list|)
expr_stmt|;
block|}
comment|/**          * @return A singleton instance of the class parameter or null if the class could not be found!          */
DECL|method|getEntryFetcher ()
specifier|public
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|imports
operator|.
name|EntryFetcher
name|getEntryFetcher
parameter_list|()
block|{
return|return
operator|(
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|imports
operator|.
name|EntryFetcher
operator|)
name|getClassParameter
argument_list|(
literal|"entryFetcher"
argument_list|)
return|;
block|}
DECL|method|getName ()
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|getStringParameter
argument_list|(
literal|"name"
argument_list|)
return|;
block|}
DECL|method|getDescription ()
specifier|public
name|String
name|getDescription
parameter_list|()
block|{
return|return
name|getStringParameter
argument_list|(
literal|"description"
argument_list|)
return|;
block|}
block|}
DECL|method|getExportFormatProviderExtensions ()
specifier|public
name|List
argument_list|<
name|ExportFormatProviderExtension
argument_list|>
name|getExportFormatProviderExtensions
parameter_list|()
block|{
name|ExtensionPoint
name|extPoint
init|=
name|getManager
argument_list|()
operator|.
name|getRegistry
argument_list|()
operator|.
name|getExtensionPoint
argument_list|(
name|getId
argument_list|()
argument_list|,
literal|"ExportFormatProvider"
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|ExportFormatProviderExtension
argument_list|>
name|result
init|=
operator|new
name|ArrayList
argument_list|<
name|ExportFormatProviderExtension
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Extension
name|ext
range|:
name|extPoint
operator|.
name|getConnectedExtensions
argument_list|()
control|)
block|{
try|try
block|{
name|result
operator|.
name|add
argument_list|(
operator|new
name|ExportFormatProviderExtension
argument_list|(
name|getManager
argument_list|()
operator|.
name|getPlugin
argument_list|(
name|ext
operator|.
name|getDeclaringPluginDescriptor
argument_list|()
operator|.
name|getId
argument_list|()
argument_list|)
argument_list|,
name|ext
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|PluginLifecycleException
name|e
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"Failed to activate plug-in "
operator|+
name|ext
operator|.
name|getDeclaringPluginDescriptor
argument_list|()
operator|.
name|getId
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|result
return|;
block|}
DECL|class|ExportFormatProviderExtension
specifier|public
specifier|static
class|class
name|ExportFormatProviderExtension
extends|extends
name|RuntimeExtension
block|{
DECL|method|ExportFormatProviderExtension (Plugin declaringPlugin, Extension wrapped)
specifier|public
name|ExportFormatProviderExtension
parameter_list|(
name|Plugin
name|declaringPlugin
parameter_list|,
name|Extension
name|wrapped
parameter_list|)
block|{
name|super
argument_list|(
name|declaringPlugin
argument_list|,
name|wrapped
argument_list|)
expr_stmt|;
block|}
comment|/**          * @return A singleton instance of the class parameter or null if the class could not be found!          */
DECL|method|getFormatProvider ()
specifier|public
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|export
operator|.
name|IExportFormatProvider
name|getFormatProvider
parameter_list|()
block|{
return|return
operator|(
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|export
operator|.
name|IExportFormatProvider
operator|)
name|getClassParameter
argument_list|(
literal|"formatProvider"
argument_list|)
return|;
block|}
DECL|method|getName ()
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|getStringParameter
argument_list|(
literal|"name"
argument_list|)
return|;
block|}
DECL|method|getDescription ()
specifier|public
name|String
name|getDescription
parameter_list|()
block|{
return|return
name|getStringParameter
argument_list|(
literal|"description"
argument_list|)
return|;
block|}
block|}
DECL|method|getPushToApplicationExtensions ()
specifier|public
name|List
argument_list|<
name|PushToApplicationExtension
argument_list|>
name|getPushToApplicationExtensions
parameter_list|()
block|{
name|ExtensionPoint
name|extPoint
init|=
name|getManager
argument_list|()
operator|.
name|getRegistry
argument_list|()
operator|.
name|getExtensionPoint
argument_list|(
name|getId
argument_list|()
argument_list|,
literal|"PushToApplication"
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|PushToApplicationExtension
argument_list|>
name|result
init|=
operator|new
name|ArrayList
argument_list|<
name|PushToApplicationExtension
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Extension
name|ext
range|:
name|extPoint
operator|.
name|getConnectedExtensions
argument_list|()
control|)
block|{
try|try
block|{
name|result
operator|.
name|add
argument_list|(
operator|new
name|PushToApplicationExtension
argument_list|(
name|getManager
argument_list|()
operator|.
name|getPlugin
argument_list|(
name|ext
operator|.
name|getDeclaringPluginDescriptor
argument_list|()
operator|.
name|getId
argument_list|()
argument_list|)
argument_list|,
name|ext
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|PluginLifecycleException
name|e
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"Failed to activate plug-in "
operator|+
name|ext
operator|.
name|getDeclaringPluginDescriptor
argument_list|()
operator|.
name|getId
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|result
return|;
block|}
DECL|class|PushToApplicationExtension
specifier|public
specifier|static
class|class
name|PushToApplicationExtension
extends|extends
name|RuntimeExtension
block|{
DECL|method|PushToApplicationExtension (Plugin declaringPlugin, Extension wrapped)
specifier|public
name|PushToApplicationExtension
parameter_list|(
name|Plugin
name|declaringPlugin
parameter_list|,
name|Extension
name|wrapped
parameter_list|)
block|{
name|super
argument_list|(
name|declaringPlugin
argument_list|,
name|wrapped
argument_list|)
expr_stmt|;
block|}
comment|/**          * @return A singleton instance of the class parameter or null if the class could not be found!          */
DECL|method|getPushToApp ()
specifier|public
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|external
operator|.
name|PushToApplication
name|getPushToApp
parameter_list|()
block|{
return|return
operator|(
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|external
operator|.
name|PushToApplication
operator|)
name|getClassParameter
argument_list|(
literal|"pushToApp"
argument_list|)
return|;
block|}
DECL|method|getName ()
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|getStringParameter
argument_list|(
literal|"name"
argument_list|)
return|;
block|}
DECL|method|getDescription ()
specifier|public
name|String
name|getDescription
parameter_list|()
block|{
return|return
name|getStringParameter
argument_list|(
literal|"description"
argument_list|)
return|;
block|}
block|}
DECL|method|getLayoutFormatterExtensions ()
specifier|public
name|List
argument_list|<
name|LayoutFormatterExtension
argument_list|>
name|getLayoutFormatterExtensions
parameter_list|()
block|{
name|ExtensionPoint
name|extPoint
init|=
name|getManager
argument_list|()
operator|.
name|getRegistry
argument_list|()
operator|.
name|getExtensionPoint
argument_list|(
name|getId
argument_list|()
argument_list|,
literal|"LayoutFormatter"
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|LayoutFormatterExtension
argument_list|>
name|result
init|=
operator|new
name|ArrayList
argument_list|<
name|LayoutFormatterExtension
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Extension
name|ext
range|:
name|extPoint
operator|.
name|getConnectedExtensions
argument_list|()
control|)
block|{
try|try
block|{
name|result
operator|.
name|add
argument_list|(
operator|new
name|LayoutFormatterExtension
argument_list|(
name|getManager
argument_list|()
operator|.
name|getPlugin
argument_list|(
name|ext
operator|.
name|getDeclaringPluginDescriptor
argument_list|()
operator|.
name|getId
argument_list|()
argument_list|)
argument_list|,
name|ext
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|PluginLifecycleException
name|e
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"Failed to activate plug-in "
operator|+
name|ext
operator|.
name|getDeclaringPluginDescriptor
argument_list|()
operator|.
name|getId
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|result
return|;
block|}
DECL|class|LayoutFormatterExtension
specifier|public
specifier|static
class|class
name|LayoutFormatterExtension
extends|extends
name|RuntimeExtension
block|{
DECL|method|LayoutFormatterExtension (Plugin declaringPlugin, Extension wrapped)
specifier|public
name|LayoutFormatterExtension
parameter_list|(
name|Plugin
name|declaringPlugin
parameter_list|,
name|Extension
name|wrapped
parameter_list|)
block|{
name|super
argument_list|(
name|declaringPlugin
argument_list|,
name|wrapped
argument_list|)
expr_stmt|;
block|}
comment|/**          * @return A singleton instance of the class parameter or null if the class could not be found!          */
DECL|method|getLayoutFormatter ()
specifier|public
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|export
operator|.
name|layout
operator|.
name|LayoutFormatter
name|getLayoutFormatter
parameter_list|()
block|{
return|return
operator|(
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|export
operator|.
name|layout
operator|.
name|LayoutFormatter
operator|)
name|getClassParameter
argument_list|(
literal|"layoutFormatter"
argument_list|)
return|;
block|}
DECL|method|getDescription ()
specifier|public
name|String
name|getDescription
parameter_list|()
block|{
return|return
name|getStringParameter
argument_list|(
literal|"description"
argument_list|)
return|;
block|}
DECL|method|getName ()
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|getStringParameter
argument_list|(
literal|"name"
argument_list|)
return|;
block|}
block|}
DECL|method|getImportFormatExtensions ()
specifier|public
name|List
argument_list|<
name|ImportFormatExtension
argument_list|>
name|getImportFormatExtensions
parameter_list|()
block|{
name|ExtensionPoint
name|extPoint
init|=
name|getManager
argument_list|()
operator|.
name|getRegistry
argument_list|()
operator|.
name|getExtensionPoint
argument_list|(
name|getId
argument_list|()
argument_list|,
literal|"ImportFormat"
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|ImportFormatExtension
argument_list|>
name|result
init|=
operator|new
name|ArrayList
argument_list|<
name|ImportFormatExtension
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Extension
name|ext
range|:
name|extPoint
operator|.
name|getConnectedExtensions
argument_list|()
control|)
block|{
try|try
block|{
name|result
operator|.
name|add
argument_list|(
operator|new
name|ImportFormatExtension
argument_list|(
name|getManager
argument_list|()
operator|.
name|getPlugin
argument_list|(
name|ext
operator|.
name|getDeclaringPluginDescriptor
argument_list|()
operator|.
name|getId
argument_list|()
argument_list|)
argument_list|,
name|ext
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|PluginLifecycleException
name|e
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"Failed to activate plug-in "
operator|+
name|ext
operator|.
name|getDeclaringPluginDescriptor
argument_list|()
operator|.
name|getId
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|result
return|;
block|}
DECL|class|ImportFormatExtension
specifier|public
specifier|static
class|class
name|ImportFormatExtension
extends|extends
name|RuntimeExtension
block|{
DECL|method|ImportFormatExtension (Plugin declaringPlugin, Extension wrapped)
specifier|public
name|ImportFormatExtension
parameter_list|(
name|Plugin
name|declaringPlugin
parameter_list|,
name|Extension
name|wrapped
parameter_list|)
block|{
name|super
argument_list|(
name|declaringPlugin
argument_list|,
name|wrapped
argument_list|)
expr_stmt|;
block|}
comment|/**          * @return A singleton instance of the class parameter or null if the class could not be found!          */
DECL|method|getImportFormat ()
specifier|public
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|imports
operator|.
name|ImportFormat
name|getImportFormat
parameter_list|()
block|{
return|return
operator|(
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|imports
operator|.
name|ImportFormat
operator|)
name|getClassParameter
argument_list|(
literal|"importFormat"
argument_list|)
return|;
block|}
DECL|method|getName ()
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|getStringParameter
argument_list|(
literal|"name"
argument_list|)
return|;
block|}
DECL|method|getDescription ()
specifier|public
name|String
name|getDescription
parameter_list|()
block|{
return|return
name|getStringParameter
argument_list|(
literal|"description"
argument_list|)
return|;
block|}
block|}
block|}
end_class

end_unit

