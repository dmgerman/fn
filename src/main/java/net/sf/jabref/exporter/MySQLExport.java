begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.exporter
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|exporter
package|;
end_package

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|charset
operator|.
name|Charset
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
name|Set
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
name|model
operator|.
name|database
operator|.
name|BibDatabase
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
name|MetaData
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
name|logic
operator|.
name|l10n
operator|.
name|Localization
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
name|model
operator|.
name|entry
operator|.
name|BibEntry
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
name|sql
operator|.
name|DBExporterAndImporterFactory
import|;
end_import

begin_comment
comment|/**  * MySQLExport contributed by Lee Patton.  */
end_comment

begin_class
DECL|class|MySQLExport
specifier|public
class|class
name|MySQLExport
extends|extends
name|ExportFormat
block|{
DECL|method|MySQLExport ()
specifier|public
name|MySQLExport
parameter_list|()
block|{
name|super
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"MySQL database"
argument_list|)
argument_list|,
literal|"mysql"
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|,
literal|".sql"
argument_list|)
expr_stmt|;
block|}
comment|/**      * First method called when user starts the export.      *      * @param database The bibtex database from which to export.      * @param file The filename to which the export should be writtten.      * @param encodingToUse The encoding to use.      * @param entries The entries to export.      * @throws java.lang.Exception If something goes wrong, feel free to throw an exception. The error message is shown      *             to the user.      */
annotation|@
name|Override
DECL|method|performExport (final BibDatabase database, final MetaData metaData, final String file, final Charset encodingToUse, List<BibEntry> entries)
specifier|public
name|void
name|performExport
parameter_list|(
specifier|final
name|BibDatabase
name|database
parameter_list|,
specifier|final
name|MetaData
name|metaData
parameter_list|,
specifier|final
name|String
name|file
parameter_list|,
specifier|final
name|Charset
name|encodingToUse
parameter_list|,
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
parameter_list|)
throws|throws
name|Exception
block|{
operator|new
name|DBExporterAndImporterFactory
argument_list|()
operator|.
name|getExporter
argument_list|(
literal|"MYSQL"
argument_list|)
operator|.
name|exportDatabaseAsFile
argument_list|(
name|database
argument_list|,
name|metaData
argument_list|,
name|entries
argument_list|,
name|file
argument_list|,
name|encodingToUse
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

