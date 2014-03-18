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
name|java
operator|.
name|io
operator|.
name|File
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|MalformedURLException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URISyntaxException
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
name|java
operator|.
name|util
operator|.
name|Collection
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|LinkedList
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
name|java
operator|.
name|util
operator|.
name|logging
operator|.
name|Level
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|logging
operator|.
name|Logger
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
name|util
operator|.
name|Util
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
name|ObjectFactory
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

begin_import
import|import
name|org
operator|.
name|java
operator|.
name|plugin
operator|.
name|PluginManager
operator|.
name|PluginLocation
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
name|boot
operator|.
name|DefaultPluginsCollector
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
name|PluginDescriptor
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
name|standard
operator|.
name|StandardPluginLocation
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
name|util
operator|.
name|ExtendedProperties
import|;
end_import

begin_comment
comment|/**  * Helper class for the plug-in system. Helps to retrieve the singleton instance  * of the PluginManager, which then can be used to access all the plug-ins  * registered.  *   * For an example how this is done see  * {@link net.sf.jabref.export.layout.LayoutEntry#getLayoutFormatterFromPlugins(String)}  *   * The PluginCore relies on the generated class  * {@link net.sf.jabref.plugin.core.JabRefPlugin} in the sub-package "core" for  * finding the plugins and their extension.  *   * @author Christopher Oezbek  */
end_comment

begin_class
DECL|class|PluginCore
specifier|public
class|class
name|PluginCore
block|{
DECL|field|singleton
specifier|static
name|PluginManager
name|singleton
decl_stmt|;
DECL|field|userPluginDir
specifier|static
name|File
name|userPluginDir
init|=
operator|new
name|File
argument_list|(
name|System
operator|.
name|getProperty
argument_list|(
literal|"user.home"
argument_list|)
operator|+
literal|"/.jabref/plugins"
argument_list|)
decl_stmt|;
DECL|method|getLocationInsideJar (String context, String manifest)
specifier|static
name|PluginLocation
name|getLocationInsideJar
parameter_list|(
name|String
name|context
parameter_list|,
name|String
name|manifest
parameter_list|)
block|{
name|URL
name|jar
init|=
name|PluginCore
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|Util
operator|.
name|joinPath
argument_list|(
name|context
argument_list|,
name|manifest
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|jar
operator|==
literal|null
condition|)
block|{
return|return
literal|null
return|;
block|}
name|String
name|protocol
init|=
name|jar
operator|.
name|getProtocol
argument_list|()
operator|.
name|toLowerCase
argument_list|()
decl_stmt|;
try|try
block|{
if|if
condition|(
name|protocol
operator|.
name|startsWith
argument_list|(
literal|"jar"
argument_list|)
condition|)
block|{
return|return
operator|new
name|StandardPluginLocation
argument_list|(
operator|new
name|URL
argument_list|(
name|jar
operator|.
name|toExternalForm
argument_list|()
operator|.
name|replaceFirst
argument_list|(
literal|"!(.*?)$"
argument_list|,
name|Util
operator|.
name|joinPath
argument_list|(
literal|"!"
argument_list|,
name|context
argument_list|)
argument_list|)
argument_list|)
argument_list|,
name|jar
argument_list|)
return|;
block|}
elseif|else
if|if
condition|(
name|protocol
operator|.
name|startsWith
argument_list|(
literal|"file"
argument_list|)
condition|)
block|{
name|File
name|f
init|=
operator|new
name|File
argument_list|(
name|jar
operator|.
name|toURI
argument_list|()
argument_list|)
decl_stmt|;
return|return
operator|new
name|StandardPluginLocation
argument_list|(
name|f
operator|.
name|getParentFile
argument_list|()
argument_list|,
name|manifest
argument_list|)
return|;
block|}
block|}
catch|catch
parameter_list|(
name|URISyntaxException
name|e
parameter_list|)
block|{
return|return
literal|null
return|;
block|}
catch|catch
parameter_list|(
name|MalformedURLException
name|e
parameter_list|)
block|{
return|return
literal|null
return|;
block|}
return|return
literal|null
return|;
block|}
DECL|method|initialize ()
specifier|static
name|PluginManager
name|initialize
parameter_list|()
block|{
comment|// We do not want info messages from JPF.
name|Logger
operator|.
name|getLogger
argument_list|(
literal|"org.java.plugin"
argument_list|)
operator|.
name|setLevel
argument_list|(
name|Level
operator|.
name|WARNING
argument_list|)
expr_stmt|;
name|Logger
name|log
init|=
name|Logger
operator|.
name|getLogger
argument_list|(
name|PluginCore
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
name|ObjectFactory
name|objectFactory
init|=
name|ObjectFactory
operator|.
name|newInstance
argument_list|()
decl_stmt|;
name|PluginManager
name|result
init|=
name|objectFactory
operator|.
name|createManager
argument_list|()
decl_stmt|;
comment|/*          * Now find plug-ins! Check directories and jar.          */
try|try
block|{
name|DefaultPluginsCollector
name|collector
init|=
operator|new
name|DefaultPluginsCollector
argument_list|()
decl_stmt|;
name|ExtendedProperties
name|ep
init|=
operator|new
name|ExtendedProperties
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|File
argument_list|>
name|directoriesToSearch
init|=
operator|new
name|LinkedList
argument_list|<
name|File
argument_list|>
argument_list|()
decl_stmt|;
name|directoriesToSearch
operator|.
name|add
argument_list|(
operator|new
name|File
argument_list|(
literal|"./src/resources/plugins"
argument_list|)
argument_list|)
expr_stmt|;
name|directoriesToSearch
operator|.
name|add
argument_list|(
operator|new
name|File
argument_list|(
literal|"./plugins"
argument_list|)
argument_list|)
expr_stmt|;
name|directoriesToSearch
operator|.
name|add
argument_list|(
name|userPluginDir
argument_list|)
expr_stmt|;
try|try
block|{
name|File
name|parent
init|=
operator|new
name|File
argument_list|(
name|PluginCore
operator|.
name|class
operator|.
name|getProtectionDomain
argument_list|()
operator|.
name|getCodeSource
argument_list|()
operator|.
name|getLocation
argument_list|()
operator|.
name|toURI
argument_list|()
argument_list|)
operator|.
name|getParentFile
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|parent
operator|.
name|getCanonicalFile
argument_list|()
operator|.
name|equals
argument_list|(
operator|new
name|File
argument_list|(
literal|"."
argument_list|)
operator|.
name|getCanonicalFile
argument_list|()
argument_list|)
condition|)
block|{
name|directoriesToSearch
operator|.
name|add
argument_list|(
operator|new
name|File
argument_list|(
name|parent
argument_list|,
literal|"/src/resources/plugins"
argument_list|)
argument_list|)
expr_stmt|;
name|directoriesToSearch
operator|.
name|add
argument_list|(
operator|new
name|File
argument_list|(
name|parent
argument_list|,
literal|"/plugins"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
comment|// no problem, we just use paths relative to current dir.
block|}
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
for|for
control|(
name|File
name|directory
range|:
name|directoriesToSearch
control|)
block|{
comment|// We don't want warnings if the default plug-in paths don't
comment|// exist, we do that below
if|if
condition|(
name|directory
operator|.
name|exists
argument_list|()
condition|)
block|{
if|if
condition|(
name|sb
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|sb
operator|.
name|append
argument_list|(
literal|','
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|directory
operator|.
name|getPath
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
name|ep
operator|.
name|setProperty
argument_list|(
literal|"org.java.plugin.boot.pluginsRepositories"
argument_list|,
name|sb
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|collector
operator|.
name|configure
argument_list|(
name|ep
argument_list|)
expr_stmt|;
name|Collection
argument_list|<
name|PluginLocation
argument_list|>
name|plugins
init|=
name|collector
operator|.
name|collectPluginLocations
argument_list|()
decl_stmt|;
comment|/**              * I know the following is really, really ugly, but I have found no              * way to automatically discover multiple plugin.xmls in JARs              */
name|String
index|[]
name|jarLocationsToSearch
init|=
operator|new
name|String
index|[]
block|{
literal|"/plugins/net.sf.jabref.core/"
block|,
literal|"/plugins/net.sf.jabref.export.misq/"
block|}
decl_stmt|;
comment|// Collection locations
for|for
control|(
name|String
name|jarLocation
range|:
name|jarLocationsToSearch
control|)
block|{
name|PluginLocation
name|location
init|=
name|getLocationInsideJar
argument_list|(
name|jarLocation
argument_list|,
literal|"plugin.xml"
argument_list|)
decl_stmt|;
if|if
condition|(
name|location
operator|!=
literal|null
condition|)
name|plugins
operator|.
name|add
argument_list|(
name|location
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|plugins
operator|.
name|size
argument_list|()
operator|<=
literal|0
condition|)
block|{
name|log
operator|.
name|warning
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"No plugins were found in the following folders:"
argument_list|)
operator|+
literal|"\n  "
operator|+
name|Util
operator|.
name|join
argument_list|(
name|directoriesToSearch
operator|.
name|toArray
argument_list|(
operator|new
name|String
index|[
name|directoriesToSearch
operator|.
name|size
argument_list|()
index|]
argument_list|)
argument_list|,
literal|"\n  "
argument_list|,
literal|0
argument_list|,
name|directoriesToSearch
operator|.
name|size
argument_list|()
argument_list|)
operator|+
literal|"\n"
operator|+
name|Globals
operator|.
name|lang
argument_list|(
literal|"and inside the JabRef-jar:"
argument_list|)
operator|+
literal|"\n  "
operator|+
name|Util
operator|.
name|join
argument_list|(
name|jarLocationsToSearch
argument_list|,
literal|"\n  "
argument_list|,
literal|0
argument_list|,
name|jarLocationsToSearch
operator|.
name|length
argument_list|)
operator|+
literal|"\n"
operator|+
name|Globals
operator|.
name|lang
argument_list|(
literal|"At least the plug-in 'net.sf.jabref.core' should be there."
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|result
operator|.
name|publishPlugins
argument_list|(
name|plugins
operator|.
name|toArray
argument_list|(
operator|new
name|PluginLocation
index|[
name|plugins
operator|.
name|size
argument_list|()
index|]
argument_list|)
argument_list|)
expr_stmt|;
name|Collection
argument_list|<
name|PluginDescriptor
argument_list|>
name|descs
init|=
name|result
operator|.
name|getRegistry
argument_list|()
operator|.
name|getPluginDescriptors
argument_list|()
decl_stmt|;
name|sb
operator|=
operator|new
name|StringBuilder
argument_list|()
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Found %0 plugin(s)"
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|descs
operator|.
name|size
argument_list|()
argument_list|)
argument_list|)
operator|+
literal|":\n"
argument_list|)
expr_stmt|;
for|for
control|(
name|PluginDescriptor
name|p
range|:
name|result
operator|.
name|getRegistry
argument_list|()
operator|.
name|getPluginDescriptors
argument_list|()
control|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|"  - "
argument_list|)
operator|.
name|append
argument_list|(
name|p
operator|.
name|getId
argument_list|()
argument_list|)
operator|.
name|append
argument_list|(
literal|" ("
argument_list|)
operator|.
name|append
argument_list|(
name|p
operator|.
name|getLocation
argument_list|()
argument_list|)
operator|.
name|append
argument_list|(
literal|")\n"
argument_list|)
expr_stmt|;
block|}
name|log
operator|.
name|info
argument_list|(
name|sb
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|log
operator|.
name|severe
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Error in starting plug-in system. Starting without, but some functionality may be missing."
argument_list|)
operator|+
literal|"\n"
operator|+
name|e
operator|.
name|getLocalizedMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|result
return|;
block|}
DECL|method|getManager ()
specifier|public
specifier|static
name|PluginManager
name|getManager
parameter_list|()
block|{
if|if
condition|(
name|singleton
operator|==
literal|null
condition|)
block|{
name|singleton
operator|=
name|PluginCore
operator|.
name|initialize
argument_list|()
expr_stmt|;
block|}
return|return
name|singleton
return|;
block|}
block|}
end_class

end_unit

