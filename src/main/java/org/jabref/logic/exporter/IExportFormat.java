begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.exporter
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
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
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|database
operator|.
name|BibDatabaseContext
import|;
end_import

begin_import
import|import
name|org
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
DECL|method|getExtension ()
name|String
name|getExtension
parameter_list|()
function_decl|;
comment|/**      * Perform the export.      *      * @param databaseContext the database to export from.      * @param file      *            The filename to write to.      * @param encoding      *            The encoding to use.      * @param entries      *             A list containing all entries that      *            should be exported. The list of entries must be non null      * @throws Exception      */
DECL|method|performExport (BibDatabaseContext databaseContext, String file, Charset encoding, List<BibEntry> entries)
name|void
name|performExport
parameter_list|(
name|BibDatabaseContext
name|databaseContext
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

