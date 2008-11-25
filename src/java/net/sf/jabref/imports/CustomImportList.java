begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2005 Andreas Rudert, based on CustomExportList by ??   All programs in this directory and  subdirectories are published under the GNU General Public License as  described below.   This program is free software; you can redistribute it and/or modify  it under the terms of the GNU General Public License as published by  the Free Software Foundation; either version 2 of the License, or (at  your option) any later version.   This program is distributed in the hope that it will be useful, but  WITHOUT ANY WARRANTY; without even the implied warranty of  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU  General Public License for more details.   You should have received a copy of the GNU General Public License  along with this program; if not, write to the Free Software  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA   Further information about the GNU GPL is available at:  http://www.gnu.org/copyleft/gpl.ja.html */
end_comment

begin_package
DECL|package|net.sf.jabref.imports
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|imports
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
name|URL
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URLClassLoader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|TreeSet
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
name|JabRefPreferences
import|;
end_import

begin_comment
comment|/**  * Collection of user defined custom import formats.   *   *<p>The collection can be stored and retrieved from Preferences. It is sorted by the default  * order of {@link ImportFormat}.</p>  */
end_comment

begin_class
DECL|class|CustomImportList
specifier|public
class|class
name|CustomImportList
extends|extends
name|TreeSet
argument_list|<
name|CustomImportList
operator|.
name|Importer
argument_list|>
block|{
comment|/**    * Object with data for a custom importer.    *     *<p>Is also responsible for instantiating the class loader.</p>    */
DECL|class|Importer
specifier|public
class|class
name|Importer
implements|implements
name|Comparable
argument_list|<
name|Importer
argument_list|>
block|{
DECL|field|name
specifier|private
name|String
name|name
decl_stmt|;
DECL|field|cliId
specifier|private
name|String
name|cliId
decl_stmt|;
DECL|field|className
specifier|private
name|String
name|className
decl_stmt|;
DECL|field|basePath
specifier|private
name|String
name|basePath
decl_stmt|;
DECL|method|Importer ()
specifier|public
name|Importer
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
DECL|method|Importer (String[] data)
specifier|public
name|Importer
parameter_list|(
name|String
index|[]
name|data
parameter_list|)
block|{
name|super
argument_list|()
expr_stmt|;
name|this
operator|.
name|name
operator|=
name|data
index|[
literal|0
index|]
expr_stmt|;
name|this
operator|.
name|cliId
operator|=
name|data
index|[
literal|1
index|]
expr_stmt|;
name|this
operator|.
name|className
operator|=
name|data
index|[
literal|2
index|]
expr_stmt|;
name|this
operator|.
name|basePath
operator|=
name|data
index|[
literal|3
index|]
expr_stmt|;
block|}
DECL|method|getName ()
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|this
operator|.
name|name
return|;
block|}
DECL|method|setName (String name)
specifier|public
name|void
name|setName
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|this
operator|.
name|name
operator|=
name|name
expr_stmt|;
block|}
DECL|method|getClidId ()
specifier|public
name|String
name|getClidId
parameter_list|()
block|{
return|return
name|this
operator|.
name|cliId
return|;
block|}
DECL|method|setCliId (String cliId)
specifier|public
name|void
name|setCliId
parameter_list|(
name|String
name|cliId
parameter_list|)
block|{
name|this
operator|.
name|cliId
operator|=
name|cliId
expr_stmt|;
block|}
DECL|method|getClassName ()
specifier|public
name|String
name|getClassName
parameter_list|()
block|{
return|return
name|this
operator|.
name|className
return|;
block|}
DECL|method|setClassName (String className)
specifier|public
name|void
name|setClassName
parameter_list|(
name|String
name|className
parameter_list|)
block|{
name|this
operator|.
name|className
operator|=
name|className
expr_stmt|;
block|}
DECL|method|setBasePath (String basePath)
specifier|public
name|void
name|setBasePath
parameter_list|(
name|String
name|basePath
parameter_list|)
block|{
name|this
operator|.
name|basePath
operator|=
name|basePath
expr_stmt|;
block|}
DECL|method|getBasePath ()
specifier|public
name|File
name|getBasePath
parameter_list|()
block|{
return|return
operator|new
name|File
argument_list|(
name|basePath
argument_list|)
return|;
block|}
DECL|method|getBasePathUrl ()
specifier|public
name|URL
name|getBasePathUrl
parameter_list|()
throws|throws
name|MalformedURLException
block|{
return|return
name|getBasePath
argument_list|()
operator|.
name|toURI
argument_list|()
operator|.
name|toURL
argument_list|()
return|;
block|}
DECL|method|getAsStringArray ()
specifier|public
name|String
index|[]
name|getAsStringArray
parameter_list|()
block|{
return|return
operator|new
name|String
index|[]
block|{
name|name
block|,
name|cliId
block|,
name|className
block|,
name|basePath
block|}
return|;
block|}
DECL|method|equals (Object o)
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
return|return
name|o
operator|!=
literal|null
operator|&&
name|o
operator|instanceof
name|Importer
operator|&&
name|this
operator|.
name|getName
argument_list|()
operator|.
name|equals
argument_list|(
operator|(
operator|(
name|Importer
operator|)
name|o
operator|)
operator|.
name|getName
argument_list|()
argument_list|)
return|;
block|}
DECL|method|hashCode ()
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|name
operator|.
name|hashCode
argument_list|()
return|;
block|}
DECL|method|compareTo (Importer o)
specifier|public
name|int
name|compareTo
parameter_list|(
name|Importer
name|o
parameter_list|)
block|{
return|return
name|this
operator|.
name|getName
argument_list|()
operator|.
name|compareTo
argument_list|(
name|o
operator|.
name|getName
argument_list|()
argument_list|)
return|;
block|}
DECL|method|toString ()
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|this
operator|.
name|name
return|;
block|}
DECL|method|getInstance ()
specifier|public
name|ImportFormat
name|getInstance
parameter_list|()
throws|throws
name|MalformedURLException
throws|,
name|ClassNotFoundException
throws|,
name|InstantiationException
throws|,
name|IllegalAccessException
block|{
name|URLClassLoader
name|cl
init|=
operator|new
name|URLClassLoader
argument_list|(
operator|new
name|URL
index|[]
block|{
name|getBasePathUrl
argument_list|()
block|}
argument_list|)
decl_stmt|;
name|Class
argument_list|<
name|?
argument_list|>
name|clazz
init|=
name|Class
operator|.
name|forName
argument_list|(
name|className
argument_list|,
literal|true
argument_list|,
name|cl
argument_list|)
decl_stmt|;
name|ImportFormat
name|importFormat
init|=
operator|(
name|ImportFormat
operator|)
name|clazz
operator|.
name|newInstance
argument_list|()
decl_stmt|;
name|importFormat
operator|.
name|setIsCustomImporter
argument_list|(
literal|true
argument_list|)
expr_stmt|;
return|return
name|importFormat
return|;
block|}
block|}
DECL|field|prefs
specifier|private
name|JabRefPreferences
name|prefs
decl_stmt|;
DECL|method|CustomImportList (JabRefPreferences prefs)
specifier|public
name|CustomImportList
parameter_list|(
name|JabRefPreferences
name|prefs
parameter_list|)
block|{
name|super
argument_list|()
expr_stmt|;
name|this
operator|.
name|prefs
operator|=
name|prefs
expr_stmt|;
name|readPrefs
argument_list|()
expr_stmt|;
block|}
DECL|method|readPrefs ()
specifier|private
name|void
name|readPrefs
parameter_list|()
block|{
name|int
name|i
init|=
literal|0
decl_stmt|;
name|String
index|[]
name|s
init|=
literal|null
decl_stmt|;
while|while
condition|(
operator|(
name|s
operator|=
name|prefs
operator|.
name|getStringArray
argument_list|(
literal|"customImportFormat"
operator|+
name|i
argument_list|)
operator|)
operator|!=
literal|null
condition|)
block|{
try|try
block|{
name|super
operator|.
name|add
argument_list|(
operator|new
name|Importer
argument_list|(
name|s
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"Warning! Could not load "
operator|+
name|s
index|[
literal|0
index|]
operator|+
literal|" from preferences. Will ignore."
argument_list|)
expr_stmt|;
comment|// Globals.prefs.remove("customImportFormat"+i);
block|}
name|i
operator|++
expr_stmt|;
block|}
block|}
DECL|method|addImporter (Importer customImporter)
specifier|public
name|void
name|addImporter
parameter_list|(
name|Importer
name|customImporter
parameter_list|)
block|{
name|super
operator|.
name|add
argument_list|(
name|customImporter
argument_list|)
expr_stmt|;
block|}
comment|/**    * Adds an importer.    *     *<p>If an old one equal to the new one was contained, the old    * one is replaced.</p>    *     * @param customImporter new (version of an) importer    * @return  if the importer was contained    */
DECL|method|replaceImporter (Importer customImporter)
specifier|public
name|boolean
name|replaceImporter
parameter_list|(
name|Importer
name|customImporter
parameter_list|)
block|{
name|boolean
name|wasContained
init|=
name|this
operator|.
name|remove
argument_list|(
name|customImporter
argument_list|)
decl_stmt|;
name|this
operator|.
name|addImporter
argument_list|(
name|customImporter
argument_list|)
expr_stmt|;
return|return
name|wasContained
return|;
block|}
DECL|method|store ()
specifier|public
name|void
name|store
parameter_list|()
block|{
name|purgeAll
argument_list|()
expr_stmt|;
name|Importer
index|[]
name|importers
init|=
name|this
operator|.
name|toArray
argument_list|(
operator|new
name|Importer
index|[]
block|{}
argument_list|)
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|importers
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|Globals
operator|.
name|prefs
operator|.
name|putStringArray
argument_list|(
literal|"customImportFormat"
operator|+
name|i
argument_list|,
name|importers
index|[
name|i
index|]
operator|.
name|getAsStringArray
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|purgeAll ()
specifier|private
name|void
name|purgeAll
parameter_list|()
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|Globals
operator|.
name|prefs
operator|.
name|getStringArray
argument_list|(
literal|"customImportFormat"
operator|+
name|i
argument_list|)
operator|!=
literal|null
condition|;
name|i
operator|++
control|)
block|{
name|Globals
operator|.
name|prefs
operator|.
name|remove
argument_list|(
literal|"customImportFormat"
operator|+
name|i
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

