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
name|net
operator|.
name|URISyntaxException
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
name|Path
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
name|ArrayList
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
name|preferences
operator|.
name|JabRefPreferences
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
import|import
name|org
operator|.
name|junit
operator|.
name|runner
operator|.
name|RunWith
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|runners
operator|.
name|Parameterized
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
name|assertEquals
import|;
end_import

begin_class
annotation|@
name|RunWith
argument_list|(
name|Parameterized
operator|.
name|class
argument_list|)
DECL|class|ImportFormatReaderIntegrationTest
specifier|public
class|class
name|ImportFormatReaderIntegrationTest
block|{
DECL|field|reader
specifier|private
name|ImportFormatReader
name|reader
decl_stmt|;
DECL|field|count
specifier|private
specifier|final
name|int
name|count
decl_stmt|;
DECL|field|format
specifier|public
specifier|final
name|String
name|format
decl_stmt|;
DECL|field|file
specifier|private
specifier|final
name|Path
name|file
decl_stmt|;
DECL|method|ImportFormatReaderIntegrationTest (String resource, String format, int count)
specifier|public
name|ImportFormatReaderIntegrationTest
parameter_list|(
name|String
name|resource
parameter_list|,
name|String
name|format
parameter_list|,
name|int
name|count
parameter_list|)
throws|throws
name|URISyntaxException
block|{
name|this
operator|.
name|format
operator|=
name|format
expr_stmt|;
name|this
operator|.
name|count
operator|=
name|count
expr_stmt|;
name|this
operator|.
name|file
operator|=
name|Paths
operator|.
name|get
argument_list|(
name|ImportFormatReaderIntegrationTest
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|resource
argument_list|)
operator|.
name|toURI
argument_list|()
argument_list|)
expr_stmt|;
block|}
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
DECL|method|testImportUnknownFormat ()
specifier|public
name|void
name|testImportUnknownFormat
parameter_list|()
block|{
name|ImportFormatReader
operator|.
name|UnknownFormatImport
name|unknownFormat
init|=
name|reader
operator|.
name|importUnknownFormat
argument_list|(
name|file
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|count
argument_list|,
name|unknownFormat
operator|.
name|parserResult
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntryCount
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testImportFormatFromFile ()
specifier|public
name|void
name|testImportFormatFromFile
parameter_list|()
throws|throws
name|IOException
block|{
name|assertEquals
argument_list|(
name|count
argument_list|,
name|reader
operator|.
name|importFromFile
argument_list|(
name|format
argument_list|,
name|file
argument_list|)
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Parameterized
operator|.
name|Parameters
argument_list|(
name|name
operator|=
literal|"{index}: {1}"
argument_list|)
DECL|method|importFormats ()
specifier|public
specifier|static
name|Collection
argument_list|<
name|Object
index|[]
argument_list|>
name|importFormats
parameter_list|()
block|{
name|Collection
argument_list|<
name|Object
index|[]
argument_list|>
name|result
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|result
operator|.
name|add
argument_list|(
operator|new
name|Object
index|[]
block|{
literal|"fileformat/RisImporterTest1.ris"
block|,
literal|"ris"
block|,
literal|1
block|}
argument_list|)
expr_stmt|;
name|result
operator|.
name|add
argument_list|(
operator|new
name|Object
index|[]
block|{
literal|"fileformat/IsiImporterTest1.isi"
block|,
literal|"isi"
block|,
literal|1
block|}
argument_list|)
expr_stmt|;
name|result
operator|.
name|add
argument_list|(
operator|new
name|Object
index|[]
block|{
literal|"fileformat/SilverPlatterImporterTest1.txt"
block|,
literal|"silverplatter"
block|,
literal|1
block|}
argument_list|)
expr_stmt|;
name|result
operator|.
name|add
argument_list|(
operator|new
name|Object
index|[]
block|{
literal|"fileformat/RepecNepImporterTest2.txt"
block|,
literal|"repecnep"
block|,
literal|1
block|}
argument_list|)
expr_stmt|;
name|result
operator|.
name|add
argument_list|(
operator|new
name|Object
index|[]
block|{
literal|"fileformat/OvidImporterTest3.txt"
block|,
literal|"ovid"
block|,
literal|1
block|}
argument_list|)
expr_stmt|;
name|result
operator|.
name|add
argument_list|(
operator|new
name|Object
index|[]
block|{
literal|"fileformat/Endnote.entries.enw"
block|,
literal|"refer"
block|,
literal|5
block|}
argument_list|)
expr_stmt|;
name|result
operator|.
name|add
argument_list|(
operator|new
name|Object
index|[]
block|{
literal|"fileformat/MsBibImporterTest4.xml"
block|,
literal|"msbib"
block|,
literal|1
block|}
argument_list|)
expr_stmt|;
name|result
operator|.
name|add
argument_list|(
operator|new
name|Object
index|[]
block|{
literal|"fileformat/MsBibImporterTest4.bib"
block|,
literal|"bibtex"
block|,
literal|1
block|}
argument_list|)
expr_stmt|;
return|return
name|result
return|;
block|}
block|}
end_class

end_unit

