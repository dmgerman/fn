begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
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
name|io
operator|.
name|IOException
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
name|model
operator|.
name|entry
operator|.
name|BibEntry
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Before
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Test
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|Assert
operator|.
name|assertNull
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|Assert
operator|.
name|fail
import|;
end_import

begin_class
DECL|class|ImportFormatReaderTestParameterless
specifier|public
class|class
name|ImportFormatReaderTestParameterless
block|{
DECL|field|reader
specifier|private
name|ImportFormatReader
name|reader
decl_stmt|;
DECL|field|unknownFormat
specifier|private
name|ImportFormatReader
operator|.
name|UnknownFormatImport
name|unknownFormat
decl_stmt|;
DECL|field|result
specifier|public
name|List
argument_list|<
name|BibEntry
argument_list|>
name|result
decl_stmt|;
annotation|@
name|Before
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
block|{
name|Globals
operator|.
name|prefs
operator|=
name|JabRefPreferences
operator|.
name|getInstance
argument_list|()
expr_stmt|;
name|reader
operator|=
operator|new
name|ImportFormatReader
argument_list|()
expr_stmt|;
name|reader
operator|.
name|resetImportFormats
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testImportUnknownFormatNotWorking ()
specifier|public
name|void
name|testImportUnknownFormatNotWorking
parameter_list|()
block|{
name|String
name|fileName
init|=
name|ImportFormatReaderTestParameterless
operator|.
name|class
operator|.
name|getResource
argument_list|(
literal|"fileformat/emptyFile.xml"
argument_list|)
operator|.
name|getFile
argument_list|()
decl_stmt|;
name|unknownFormat
operator|=
name|reader
operator|.
name|importUnknownFormat
argument_list|(
name|fileName
argument_list|)
expr_stmt|;
name|assertNull
argument_list|(
name|unknownFormat
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
argument_list|(
name|expected
operator|=
name|NullPointerException
operator|.
name|class
argument_list|)
DECL|method|testNullImportUnknownFormat ()
specifier|public
name|void
name|testNullImportUnknownFormat
parameter_list|()
block|{
name|unknownFormat
operator|=
name|reader
operator|.
name|importUnknownFormat
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|fail
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Test
argument_list|(
name|expected
operator|=
name|IllegalArgumentException
operator|.
name|class
argument_list|)
DECL|method|testImportFromFileUnknownFormat ()
specifier|public
name|void
name|testImportFromFileUnknownFormat
parameter_list|()
throws|throws
name|IOException
block|{
name|result
operator|=
name|reader
operator|.
name|importFromFile
argument_list|(
literal|"someunknownformat"
argument_list|,
literal|"doesn't matter"
argument_list|,
operator|new
name|OutputPrinterToNull
argument_list|()
argument_list|)
expr_stmt|;
name|fail
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

