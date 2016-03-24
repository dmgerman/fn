begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|model
operator|.
name|entry
operator|.
name|BibEntry
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|filechooser
operator|.
name|FileFilter
import|;
end_import

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

begin_interface
DECL|interface|IExportFormat
specifier|public
interface|interface
name|IExportFormat
block|{
comment|/**      * Name to call this format in the console.      */
DECL|method|getConsoleName ()
name|String
name|getConsoleName
parameter_list|()
function_decl|;
comment|/**      * Name to display to the user (for instance in the Save file format drop      * down box.      */
DECL|method|getDisplayName ()
name|String
name|getDisplayName
parameter_list|()
function_decl|;
comment|/**      * A file filter that accepts filetypes that this exporter would create.      */
DECL|method|getFileFilter ()
name|FileFilter
name|getFileFilter
parameter_list|()
function_decl|;
comment|/**      * Perform the export.      *      * @param database      *            The database to export from.      * @param metaData      *            The database's metadata.      * @param file      *            The filename to write to.      * @param encoding      *            The encoding to use.      * @param entries      *            (may be null) A list containing all entries that      *            should be exported. If null, all entries will be exported.      * @throws Exception      */
DECL|method|performExport (BibDatabase database, MetaData metaData, String file, Charset encoding, List<BibEntry> entries)
name|void
name|performExport
parameter_list|(
name|BibDatabase
name|database
parameter_list|,
name|MetaData
name|metaData
parameter_list|,
name|String
name|file
parameter_list|,
name|Charset
name|encoding
parameter_list|,
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
parameter_list|)
throws|throws
name|Exception
function_decl|;
block|}
end_interface

end_unit

