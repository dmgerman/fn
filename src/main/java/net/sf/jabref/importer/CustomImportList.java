begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2005-2015 Andreas Rudert, Oscar Gustafsson based on CustomExportList by ??   All programs in this directory and  subdirectories are published under the GNU General Public License as  described below.   This program is free software; you can redistribute it and/or modify  it under the terms of the GNU General Public License as published by  the Free Software Foundation; either version 2 of the License, or (at  your option) any later version.   This program is distributed in the hope that it will be useful, but  WITHOUT ANY WARRANTY; without even the implied warranty of  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU  General Public License for more details.   You should have received a copy of the GNU General Public License  along with this program; if not, write to the Free Software  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA   Further information about the GNU GPL is available at:  http://www.gnu.org/copyleft/gpl.ja.html   Copyright (C) 2005-2014 JabRef contributors.  */
end_comment

begin_package
DECL|package|net.sf.jabref.importer
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|importer
package|;
end_package

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

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|importer
operator|.
name|fileformat
operator|.
name|ImportFormat
import|;
end_import

begin_comment
comment|/**  * Collection of user defined custom import formats.  *  *<p>The collection can be stored and retrieved from Preferences. It is sorted by the default  * order of {@link ImportFormat}.</p>  */
end_comment

begin_class
DECL|class|CustomImportList
specifier|public
class|class
name|CustomImportList
extends|extends
name|TreeSet
argument_list|<
name|CustomImporter
argument_list|>
block|{
DECL|field|prefs
specifier|private
specifier|final
name|JabRefPreferences
name|prefs
decl_stmt|;
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Log
name|LOGGER
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|CustomImportList
operator|.
name|class
argument_list|)
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
name|JabRefPreferences
operator|.
name|CUSTOM_IMPORT_FORMAT
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
name|CustomImporter
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
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Could not load "
operator|+
name|s
index|[
literal|0
index|]
operator|+
literal|" from preferences. Will ignore."
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
name|i
operator|++
expr_stmt|;
block|}
block|}
DECL|method|addImporter (CustomImporter customImporter)
specifier|private
name|void
name|addImporter
parameter_list|(
name|CustomImporter
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
comment|/**      * Adds an importer.      *      *<p>If an old one equal to the new one was contained, the old      * one is replaced.</p>      *      * @param customImporter new (version of an) importer      * @return  if the importer was contained      */
DECL|method|replaceImporter (CustomImporter customImporter)
specifier|public
name|boolean
name|replaceImporter
parameter_list|(
name|CustomImporter
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
name|CustomImporter
index|[]
name|importers
init|=
name|this
operator|.
name|toArray
argument_list|(
operator|new
name|CustomImporter
index|[
name|this
operator|.
name|size
argument_list|()
index|]
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
name|JabRefPreferences
operator|.
name|CUSTOM_IMPORT_FORMAT
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
operator|!
operator|(
name|Globals
operator|.
name|prefs
operator|.
name|getStringList
argument_list|(
name|JabRefPreferences
operator|.
name|CUSTOM_IMPORT_FORMAT
operator|+
name|i
argument_list|)
operator|.
name|isEmpty
argument_list|()
operator|)
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
name|JabRefPreferences
operator|.
name|CUSTOM_IMPORT_FORMAT
operator|+
name|i
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

