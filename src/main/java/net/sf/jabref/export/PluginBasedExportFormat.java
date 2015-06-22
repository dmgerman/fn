begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.export
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|export
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|*
import|;
end_import

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
name|plugin
operator|.
name|core
operator|.
name|generated
operator|.
name|_JabRefPlugin
operator|.
name|ExportFormatTemplateExtension
import|;
end_import

begin_comment
comment|/**  * Class for export formats defined in plugins.  *   * Needed since resources might be loaded from a plugin-jar.  *   */
end_comment

begin_class
DECL|class|PluginBasedExportFormat
specifier|public
class|class
name|PluginBasedExportFormat
extends|extends
name|ExportFormat
block|{
DECL|field|extension
specifier|public
specifier|final
name|ExportFormatTemplateExtension
name|extension
decl_stmt|;
comment|/**      * Load the plugin from the given extension. Might be null if extension      * could not be loaded.      *       * @param extension      * @return      */
DECL|method|getFormat ( ExportFormatTemplateExtension extension)
specifier|public
specifier|static
name|PluginBasedExportFormat
name|getFormat
parameter_list|(
name|ExportFormatTemplateExtension
name|extension
parameter_list|)
block|{
name|String
name|consoleName
init|=
name|extension
operator|.
name|getConsoleName
argument_list|()
decl_stmt|;
name|String
name|displayName
init|=
name|extension
operator|.
name|getDisplayName
argument_list|()
decl_stmt|;
name|String
name|layoutFilename
init|=
name|extension
operator|.
name|getLayoutFilename
argument_list|()
decl_stmt|;
name|String
name|fileExtension
init|=
name|extension
operator|.
name|getExtension
argument_list|()
decl_stmt|;
name|String
name|encoding
init|=
name|extension
operator|.
name|getEncoding
argument_list|()
decl_stmt|;
if|if
condition|(
literal|""
operator|.
name|equals
argument_list|(
name|fileExtension
argument_list|)
operator|||
literal|""
operator|.
name|equals
argument_list|(
name|displayName
argument_list|)
operator|||
literal|""
operator|.
name|equals
argument_list|(
name|consoleName
argument_list|)
operator|||
literal|""
operator|.
name|equals
argument_list|(
name|layoutFilename
argument_list|)
condition|)
block|{
name|Globals
operator|.
name|logger
argument_list|(
literal|"Could not load extension "
operator|+
name|extension
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
return|return
operator|new
name|PluginBasedExportFormat
argument_list|(
name|displayName
argument_list|,
name|consoleName
argument_list|,
name|layoutFilename
argument_list|,
name|fileExtension
argument_list|,
name|encoding
argument_list|,
name|extension
argument_list|)
return|;
block|}
DECL|method|PluginBasedExportFormat (String displayName, String consoleName, String layoutFileName, String fileExtension, String encoding, ExportFormatTemplateExtension extension)
specifier|public
name|PluginBasedExportFormat
parameter_list|(
name|String
name|displayName
parameter_list|,
name|String
name|consoleName
parameter_list|,
name|String
name|layoutFileName
parameter_list|,
name|String
name|fileExtension
parameter_list|,
name|String
name|encoding
parameter_list|,
name|ExportFormatTemplateExtension
name|extension
parameter_list|)
block|{
name|super
argument_list|(
name|displayName
argument_list|,
name|consoleName
argument_list|,
name|layoutFileName
argument_list|,
literal|null
argument_list|,
name|fileExtension
argument_list|)
expr_stmt|;
comment|// Set the overriding encoding, if the plugin supplied one:
if|if
condition|(
name|encoding
operator|!=
literal|null
condition|)
name|setEncoding
argument_list|(
name|encoding
argument_list|)
expr_stmt|;
name|this
operator|.
name|extension
operator|=
name|extension
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|getReader (String filename)
specifier|public
name|Reader
name|getReader
parameter_list|(
name|String
name|filename
parameter_list|)
throws|throws
name|IOException
block|{
name|URL
name|reso
init|=
name|extension
operator|.
name|getDirAsUrl
argument_list|(
name|filename
argument_list|)
decl_stmt|;
if|if
condition|(
name|reso
operator|!=
literal|null
condition|)
block|{
try|try
block|{
return|return
operator|new
name|InputStreamReader
argument_list|(
name|reso
operator|.
name|openStream
argument_list|()
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|FileNotFoundException
name|ex
parameter_list|)
block|{
comment|// If that didn't work, try below
block|}
block|}
try|try
block|{
return|return
operator|new
name|FileReader
argument_list|(
operator|new
name|File
argument_list|(
name|filename
argument_list|)
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|FileNotFoundException
name|ex
parameter_list|)
block|{
comment|// If that did not work, throw the IOException below
block|}
throw|throw
operator|new
name|IOException
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Could not find layout file"
argument_list|)
operator|+
literal|": '"
operator|+
name|filename
operator|+
literal|"'."
argument_list|)
throw|;
block|}
block|}
end_class

end_unit

