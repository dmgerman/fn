begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.exporter
package|package
name|net
operator|.
name|sf
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
name|io
operator|.
name|IOException
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
name|nio
operator|.
name|charset
operator|.
name|StandardCharsets
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|file
operator|.
name|Paths
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
name|Objects
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|transform
operator|.
name|OutputKeys
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|transform
operator|.
name|Transformer
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|transform
operator|.
name|TransformerException
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|transform
operator|.
name|TransformerFactory
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|transform
operator|.
name|TransformerFactoryConfigurationError
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|transform
operator|.
name|dom
operator|.
name|DOMSource
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|transform
operator|.
name|stream
operator|.
name|StreamResult
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
name|BibDatabaseContext
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
name|msbib
operator|.
name|MSBibDatabase
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

begin_comment
comment|/**  * ExportFormat for exporting in MSBIB XML format.  */
end_comment

begin_class
DECL|class|MSBibExportFormat
class|class
name|MSBibExportFormat
extends|extends
name|ExportFormat
block|{
DECL|method|MSBibExportFormat ()
specifier|public
name|MSBibExportFormat
parameter_list|()
block|{
name|super
argument_list|(
literal|"MS Office 2007"
argument_list|,
literal|"MSBib"
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|,
literal|".xml"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|performExport (final BibDatabaseContext databaseContext, final String file, final Charset encoding, List<BibEntry> entries)
specifier|public
name|void
name|performExport
parameter_list|(
specifier|final
name|BibDatabaseContext
name|databaseContext
parameter_list|,
specifier|final
name|String
name|file
parameter_list|,
specifier|final
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
name|SaveException
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|databaseContext
argument_list|)
expr_stmt|;
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|entries
argument_list|)
expr_stmt|;
if|if
condition|(
name|entries
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return;
block|}
comment|// forcing to use UTF8 output format for some problems with xml export in other encodings
name|SaveSession
name|session
init|=
operator|new
name|FileSaveSession
argument_list|(
name|StandardCharsets
operator|.
name|UTF_8
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|MSBibDatabase
name|msBibDatabase
init|=
operator|new
name|MSBibDatabase
argument_list|(
name|databaseContext
operator|.
name|getDatabase
argument_list|()
argument_list|,
name|entries
argument_list|)
decl_stmt|;
try|try
init|(
name|VerifyingWriter
name|ps
init|=
name|session
operator|.
name|getWriter
argument_list|()
init|)
block|{
try|try
block|{
name|DOMSource
name|source
init|=
operator|new
name|DOMSource
argument_list|(
name|msBibDatabase
operator|.
name|getDOM
argument_list|()
argument_list|)
decl_stmt|;
name|StreamResult
name|result
init|=
operator|new
name|StreamResult
argument_list|(
name|ps
argument_list|)
decl_stmt|;
name|Transformer
name|trans
init|=
name|TransformerFactory
operator|.
name|newInstance
argument_list|()
operator|.
name|newTransformer
argument_list|()
decl_stmt|;
name|trans
operator|.
name|setOutputProperty
argument_list|(
name|OutputKeys
operator|.
name|INDENT
argument_list|,
literal|"yes"
argument_list|)
expr_stmt|;
name|trans
operator|.
name|transform
argument_list|(
name|source
argument_list|,
name|result
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|TransformerException
decl||
name|IllegalArgumentException
decl||
name|TransformerFactoryConfigurationError
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|Error
argument_list|(
name|e
argument_list|)
throw|;
block|}
name|finalizeSaveSession
argument_list|(
name|session
argument_list|,
name|Paths
operator|.
name|get
argument_list|(
name|file
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{
throw|throw
operator|new
name|SaveException
argument_list|(
name|ex
argument_list|)
throw|;
block|}
block|}
block|}
end_class

end_unit

