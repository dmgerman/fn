begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.importer.fileformat
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|importer
operator|.
name|fileformat
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|BufferedReader
import|;
end_import

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
name|io
operator|.
name|StringReader
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
name|Arrays
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
name|Optional
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
name|importer
operator|.
name|ImportFormatPreferences
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
name|util
operator|.
name|FileExtensions
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

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|Assert
operator|.
name|assertFalse
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
name|assertTrue
import|;
end_import

begin_class
DECL|class|EndnoteImporterTest
specifier|public
class|class
name|EndnoteImporterTest
block|{
DECL|field|importer
specifier|private
name|EndnoteImporter
name|importer
decl_stmt|;
annotation|@
name|Before
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
block|{
name|importer
operator|=
operator|new
name|EndnoteImporter
argument_list|(
name|ImportFormatPreferences
operator|.
name|fromPreferences
argument_list|(
name|JabRefPreferences
operator|.
name|getInstance
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetFormatName ()
specifier|public
name|void
name|testGetFormatName
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"Refer/Endnote"
argument_list|,
name|importer
operator|.
name|getFormatName
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetCLIId ()
specifier|public
name|void
name|testGetCLIId
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"refer"
argument_list|,
name|importer
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testsGetExtensions ()
specifier|public
name|void
name|testsGetExtensions
parameter_list|()
block|{
name|assertEquals
argument_list|(
name|FileExtensions
operator|.
name|ENDNOTE
argument_list|,
name|importer
operator|.
name|getExtensions
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetDescription ()
specifier|public
name|void
name|testGetDescription
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"Importer for the Refer/Endnote format."
operator|+
literal|" Modified to use article number for pages if pages are missing."
argument_list|,
name|importer
operator|.
name|getDescription
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testIsRecognizedFormat ()
specifier|public
name|void
name|testIsRecognizedFormat
parameter_list|()
throws|throws
name|IOException
throws|,
name|URISyntaxException
block|{
name|List
argument_list|<
name|String
argument_list|>
name|list
init|=
name|Arrays
operator|.
name|asList
argument_list|(
literal|"Endnote.pattern.A.enw"
argument_list|,
literal|"Endnote.pattern.E.enw"
argument_list|,
literal|"Endnote.book.example.enw"
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|str
range|:
name|list
control|)
block|{
name|Path
name|file
init|=
name|Paths
operator|.
name|get
argument_list|(
name|EndnoteImporterTest
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|str
argument_list|)
operator|.
name|toURI
argument_list|()
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|importer
operator|.
name|isRecognizedFormat
argument_list|(
name|file
argument_list|,
name|StandardCharsets
operator|.
name|UTF_8
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Test
DECL|method|testIsRecognizedFormatReject ()
specifier|public
name|void
name|testIsRecognizedFormatReject
parameter_list|()
throws|throws
name|IOException
throws|,
name|URISyntaxException
block|{
name|List
argument_list|<
name|String
argument_list|>
name|list
init|=
name|Arrays
operator|.
name|asList
argument_list|(
literal|"IEEEImport1.txt"
argument_list|,
literal|"IsiImporterTest1.isi"
argument_list|,
literal|"IsiImporterTestInspec.isi"
argument_list|,
literal|"IsiImporterTestWOS.isi"
argument_list|,
literal|"IsiImporterTestMedline.isi"
argument_list|,
literal|"RisImporterTest1.ris"
argument_list|,
literal|"Endnote.pattern.no_enw"
argument_list|,
literal|"empty.pdf"
argument_list|,
literal|"annotated.pdf"
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|str
range|:
name|list
control|)
block|{
name|Path
name|file
init|=
name|Paths
operator|.
name|get
argument_list|(
name|EndnoteImporterTest
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|str
argument_list|)
operator|.
name|toURI
argument_list|()
argument_list|)
decl_stmt|;
name|assertFalse
argument_list|(
name|importer
operator|.
name|isRecognizedFormat
argument_list|(
name|file
argument_list|,
name|Charset
operator|.
name|defaultCharset
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Test
DECL|method|testImportEntries0 ()
specifier|public
name|void
name|testImportEntries0
parameter_list|()
throws|throws
name|IOException
throws|,
name|URISyntaxException
block|{
name|Path
name|file
init|=
name|Paths
operator|.
name|get
argument_list|(
name|EndnoteImporterTest
operator|.
name|class
operator|.
name|getResource
argument_list|(
literal|"Endnote.entries.enw"
argument_list|)
operator|.
name|toURI
argument_list|()
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|BibEntry
argument_list|>
name|bibEntries
init|=
name|importer
operator|.
name|importDatabase
argument_list|(
name|file
argument_list|,
name|StandardCharsets
operator|.
name|UTF_8
argument_list|)
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
literal|5
argument_list|,
name|bibEntries
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|BibEntry
name|be0
init|=
name|bibEntries
operator|.
name|get
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|"misc"
argument_list|,
name|be0
operator|.
name|getType
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"testA0 and testA1"
argument_list|)
argument_list|,
name|be0
operator|.
name|getFieldOptional
argument_list|(
literal|"author"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"testE0 and testE1"
argument_list|)
argument_list|,
name|be0
operator|.
name|getFieldOptional
argument_list|(
literal|"editor"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"testT"
argument_list|)
argument_list|,
name|be0
operator|.
name|getFieldOptional
argument_list|(
literal|"title"
argument_list|)
argument_list|)
expr_stmt|;
name|BibEntry
name|be1
init|=
name|bibEntries
operator|.
name|get
argument_list|(
literal|1
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|"misc"
argument_list|,
name|be1
operator|.
name|getType
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"testC"
argument_list|)
argument_list|,
name|be1
operator|.
name|getFieldOptional
argument_list|(
literal|"address"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"testB2"
argument_list|)
argument_list|,
name|be1
operator|.
name|getFieldOptional
argument_list|(
literal|"booktitle"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"test8"
argument_list|)
argument_list|,
name|be1
operator|.
name|getFieldOptional
argument_list|(
literal|"date"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"test7"
argument_list|)
argument_list|,
name|be1
operator|.
name|getFieldOptional
argument_list|(
literal|"edition"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"testJ"
argument_list|)
argument_list|,
name|be1
operator|.
name|getFieldOptional
argument_list|(
literal|"journal"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"testD"
argument_list|)
argument_list|,
name|be1
operator|.
name|getFieldOptional
argument_list|(
literal|"year"
argument_list|)
argument_list|)
expr_stmt|;
name|BibEntry
name|be2
init|=
name|bibEntries
operator|.
name|get
argument_list|(
literal|2
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|"article"
argument_list|,
name|be2
operator|.
name|getType
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"testB0"
argument_list|)
argument_list|,
name|be2
operator|.
name|getFieldOptional
argument_list|(
literal|"journal"
argument_list|)
argument_list|)
expr_stmt|;
name|BibEntry
name|be3
init|=
name|bibEntries
operator|.
name|get
argument_list|(
literal|3
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|"book"
argument_list|,
name|be3
operator|.
name|getType
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"testI0"
argument_list|)
argument_list|,
name|be3
operator|.
name|getFieldOptional
argument_list|(
literal|"publisher"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"testB1"
argument_list|)
argument_list|,
name|be3
operator|.
name|getFieldOptional
argument_list|(
literal|"series"
argument_list|)
argument_list|)
expr_stmt|;
name|BibEntry
name|be4
init|=
name|bibEntries
operator|.
name|get
argument_list|(
literal|4
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|"mastersthesis"
argument_list|,
name|be4
operator|.
name|getType
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"testX"
argument_list|)
argument_list|,
name|be4
operator|.
name|getFieldOptional
argument_list|(
literal|"abstract"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"testF"
argument_list|)
argument_list|,
name|be4
operator|.
name|getFieldOptional
argument_list|(
literal|"bibtexkey"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"testR"
argument_list|)
argument_list|,
name|be4
operator|.
name|getFieldOptional
argument_list|(
literal|"doi"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"testK"
argument_list|)
argument_list|,
name|be4
operator|.
name|getFieldOptional
argument_list|(
literal|"keywords"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"testO1"
argument_list|)
argument_list|,
name|be4
operator|.
name|getFieldOptional
argument_list|(
literal|"note"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"testN"
argument_list|)
argument_list|,
name|be4
operator|.
name|getFieldOptional
argument_list|(
literal|"number"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"testP"
argument_list|)
argument_list|,
name|be4
operator|.
name|getFieldOptional
argument_list|(
literal|"pages"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"testI1"
argument_list|)
argument_list|,
name|be4
operator|.
name|getFieldOptional
argument_list|(
literal|"school"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"testU"
argument_list|)
argument_list|,
name|be4
operator|.
name|getFieldOptional
argument_list|(
literal|"url"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"testV"
argument_list|)
argument_list|,
name|be4
operator|.
name|getFieldOptional
argument_list|(
literal|"volume"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testImportEntries1 ()
specifier|public
name|void
name|testImportEntries1
parameter_list|()
throws|throws
name|IOException
block|{
name|String
name|s
init|=
literal|"%O Artn\\\\s testO\n%A testA,\n%E testE0, testE1"
decl_stmt|;
name|List
argument_list|<
name|BibEntry
argument_list|>
name|bibEntries
init|=
name|importer
operator|.
name|importDatabase
argument_list|(
operator|new
name|BufferedReader
argument_list|(
operator|new
name|StringReader
argument_list|(
name|s
argument_list|)
argument_list|)
argument_list|)
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|bibEntries
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|BibEntry
name|be
init|=
name|bibEntries
operator|.
name|get
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|"misc"
argument_list|,
name|be
operator|.
name|getType
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"testA"
argument_list|)
argument_list|,
name|be
operator|.
name|getFieldOptional
argument_list|(
literal|"author"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"testE0, testE1"
argument_list|)
argument_list|,
name|be
operator|.
name|getFieldOptional
argument_list|(
literal|"editor"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"testO"
argument_list|)
argument_list|,
name|be
operator|.
name|getFieldOptional
argument_list|(
literal|"pages"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testImportEntriesBookExample ()
specifier|public
name|void
name|testImportEntriesBookExample
parameter_list|()
throws|throws
name|IOException
throws|,
name|URISyntaxException
block|{
name|Path
name|file
init|=
name|Paths
operator|.
name|get
argument_list|(
name|EndnoteImporterTest
operator|.
name|class
operator|.
name|getResource
argument_list|(
literal|"Endnote.book.example.enw"
argument_list|)
operator|.
name|toURI
argument_list|()
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|BibEntry
argument_list|>
name|bibEntries
init|=
name|importer
operator|.
name|importDatabase
argument_list|(
name|file
argument_list|,
name|StandardCharsets
operator|.
name|UTF_8
argument_list|)
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|bibEntries
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|BibEntry
name|be
init|=
name|bibEntries
operator|.
name|get
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|"book"
argument_list|,
name|be
operator|.
name|getType
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"Heidelberg"
argument_list|)
argument_list|,
name|be
operator|.
name|getFieldOptional
argument_list|(
literal|"address"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"PreiÃel, RenÃ© and Stachmann, BjÃ¸rn"
argument_list|)
argument_list|,
name|be
operator|.
name|getFieldOptional
argument_list|(
literal|"author"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"3., aktualisierte und erweiterte Auflage"
argument_list|)
argument_list|,
name|be
operator|.
name|getFieldOptional
argument_list|(
literal|"edition"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"Versionsverwaltung"
argument_list|)
argument_list|,
name|be
operator|.
name|getFieldOptional
argument_list|(
literal|"keywords"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"XX, 327"
argument_list|)
argument_list|,
name|be
operator|.
name|getFieldOptional
argument_list|(
literal|"pages"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"dpunkt.verlag"
argument_list|)
argument_list|,
name|be
operator|.
name|getFieldOptional
argument_list|(
literal|"publisher"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"Git : dezentrale Versionsverwaltung im Team : Grundlagen und Workflows"
argument_list|)
argument_list|,
name|be
operator|.
name|getFieldOptional
argument_list|(
literal|"title"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"http://d-nb.info/107601965X"
argument_list|)
argument_list|,
name|be
operator|.
name|getFieldOptional
argument_list|(
literal|"url"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"2016"
argument_list|)
argument_list|,
name|be
operator|.
name|getFieldOptional
argument_list|(
literal|"year"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

