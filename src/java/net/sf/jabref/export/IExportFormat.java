begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|BibtexDatabase
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
name|java
operator|.
name|io
operator|.
name|Writer
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
name|javax
operator|.
name|swing
operator|.
name|filechooser
operator|.
name|FileFilter
import|;
end_import

begin_interface
DECL|interface|IExportFormat
specifier|public
interface|interface
name|IExportFormat
block|{
comment|/** 	 * Name to call this format in the console. 	 */
DECL|method|getConsoleName ()
name|String
name|getConsoleName
parameter_list|()
function_decl|;
comment|/** 	 * Name to display to the user (for instance in the Save file format drop 	 * down box. 	 */
DECL|method|getDisplayName ()
name|String
name|getDisplayName
parameter_list|()
function_decl|;
comment|/** 	 * A file filter that accepts filetypes that this exporter would create. 	 */
DECL|method|getFileFilter ()
name|FileFilter
name|getFileFilter
parameter_list|()
function_decl|;
comment|/** 	 * Perform the export. 	 *  	 * @param database 	 *            The database to export from.      * @param metaData      *            The database's metadata. 	 * @param file 	 *            The filename to write to. 	 * @param encoding 	 *            The encoding to use. 	 * @param entryIds 	 *            (may be null) A Set containing the IDs of all entries that 	 *            should be exported. If null, all entries will be exported. 	 * @throws Exception 	 * @see #performExport(BibtexDatabase, Set, Writer) 	 */
DECL|method|performExport (BibtexDatabase database, MetaData metaData, String file, String encoding, Set<String> entryIds)
name|void
name|performExport
parameter_list|(
name|BibtexDatabase
name|database
parameter_list|,
name|MetaData
name|metaData
parameter_list|,
name|String
name|file
parameter_list|,
name|String
name|encoding
parameter_list|,
name|Set
argument_list|<
name|String
argument_list|>
name|entryIds
parameter_list|)
throws|throws
name|Exception
function_decl|;
block|}
end_interface

end_unit

