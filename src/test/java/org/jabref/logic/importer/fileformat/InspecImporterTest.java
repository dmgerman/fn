begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.importer.fileformat
package|package
name|org
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
name|util
operator|.
name|Collections
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
name|function
operator|.
name|Predicate
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|stream
operator|.
name|Stream
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|bibtex
operator|.
name|BibEntryAssert
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|util
operator|.
name|StandardFileType
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
name|field
operator|.
name|StandardField
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
name|types
operator|.
name|StandardEntryType
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|jupiter
operator|.
name|api
operator|.
name|BeforeEach
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|jupiter
operator|.
name|api
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
name|jupiter
operator|.
name|params
operator|.
name|ParameterizedTest
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|jupiter
operator|.
name|params
operator|.
name|provider
operator|.
name|MethodSource
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|jupiter
operator|.
name|api
operator|.
name|Assertions
operator|.
name|assertEquals
import|;
end_import

begin_class
DECL|class|InspecImporterTest
specifier|public
class|class
name|InspecImporterTest
block|{
DECL|field|FILE_ENDING
specifier|private
specifier|static
specifier|final
name|String
name|FILE_ENDING
init|=
literal|".txt"
decl_stmt|;
DECL|field|importer
specifier|private
name|InspecImporter
name|importer
decl_stmt|;
DECL|method|fileNames ()
specifier|private
specifier|static
name|Stream
argument_list|<
name|String
argument_list|>
name|fileNames
parameter_list|()
throws|throws
name|IOException
block|{
name|Predicate
argument_list|<
name|String
argument_list|>
name|fileName
init|=
name|name
lambda|->
name|name
operator|.
name|startsWith
argument_list|(
literal|"InspecImportTest"
argument_list|)
operator|&&
operator|!
name|name
operator|.
name|contains
argument_list|(
literal|"False"
argument_list|)
operator|&&
name|name
operator|.
name|endsWith
argument_list|(
name|FILE_ENDING
argument_list|)
decl_stmt|;
return|return
name|ImporterTestEngine
operator|.
name|getTestFiles
argument_list|(
name|fileName
argument_list|)
operator|.
name|stream
argument_list|()
return|;
block|}
DECL|method|nonInspecfileNames ()
specifier|private
specifier|static
name|Stream
argument_list|<
name|String
argument_list|>
name|nonInspecfileNames
parameter_list|()
throws|throws
name|IOException
block|{
name|Predicate
argument_list|<
name|String
argument_list|>
name|fileName
init|=
name|name
lambda|->
operator|!
name|name
operator|.
name|startsWith
argument_list|(
literal|"InspecImportTest"
argument_list|)
decl_stmt|;
return|return
name|ImporterTestEngine
operator|.
name|getTestFiles
argument_list|(
name|fileName
argument_list|)
operator|.
name|stream
argument_list|()
return|;
block|}
annotation|@
name|BeforeEach
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
throws|throws
name|Exception
block|{
name|this
operator|.
name|importer
operator|=
operator|new
name|InspecImporter
argument_list|()
expr_stmt|;
block|}
annotation|@
name|ParameterizedTest
annotation|@
name|MethodSource
argument_list|(
literal|"fileNames"
argument_list|)
DECL|method|testIsRecognizedFormatAccept (String fileName)
specifier|public
name|void
name|testIsRecognizedFormatAccept
parameter_list|(
name|String
name|fileName
parameter_list|)
throws|throws
name|IOException
block|{
name|ImporterTestEngine
operator|.
name|testIsRecognizedFormat
argument_list|(
name|importer
argument_list|,
name|fileName
argument_list|)
expr_stmt|;
block|}
annotation|@
name|ParameterizedTest
annotation|@
name|MethodSource
argument_list|(
literal|"nonInspecfileNames"
argument_list|)
DECL|method|testIsRecognizedFormatReject (String fileName)
specifier|public
name|void
name|testIsRecognizedFormatReject
parameter_list|(
name|String
name|fileName
parameter_list|)
throws|throws
name|IOException
block|{
name|ImporterTestEngine
operator|.
name|testIsNotRecognizedFormat
argument_list|(
name|importer
argument_list|,
name|fileName
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testCompleteBibtexEntryOnJournalPaperImport ()
specifier|public
name|void
name|testCompleteBibtexEntryOnJournalPaperImport
parameter_list|()
throws|throws
name|IOException
throws|,
name|URISyntaxException
block|{
name|BibEntry
name|expectedEntry
init|=
operator|new
name|BibEntry
argument_list|(
name|StandardEntryType
operator|.
name|Article
argument_list|)
decl_stmt|;
name|expectedEntry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|TITLE
argument_list|,
literal|"The SIS project : software reuse with a natural language approach"
argument_list|)
expr_stmt|;
name|expectedEntry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|AUTHOR
argument_list|,
literal|"Prechelt, Lutz"
argument_list|)
expr_stmt|;
name|expectedEntry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|YEAR
argument_list|,
literal|"1992"
argument_list|)
expr_stmt|;
name|expectedEntry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|ABSTRACT
argument_list|,
literal|"Abstrakt"
argument_list|)
expr_stmt|;
name|expectedEntry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|KEYWORDS
argument_list|,
literal|"key"
argument_list|)
expr_stmt|;
name|expectedEntry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|JOURNAL
argument_list|,
literal|"10000"
argument_list|)
expr_stmt|;
name|expectedEntry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|PAGES
argument_list|,
literal|"20"
argument_list|)
expr_stmt|;
name|expectedEntry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|VOLUME
argument_list|,
literal|"19"
argument_list|)
expr_stmt|;
name|BibEntryAssert
operator|.
name|assertEquals
argument_list|(
name|Collections
operator|.
name|singletonList
argument_list|(
name|expectedEntry
argument_list|)
argument_list|,
name|InspecImporterTest
operator|.
name|class
operator|.
name|getResource
argument_list|(
literal|"InspecImportTest2.txt"
argument_list|)
argument_list|,
name|importer
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|importConferencePaperGivesInproceedings ()
specifier|public
name|void
name|importConferencePaperGivesInproceedings
parameter_list|()
throws|throws
name|IOException
block|{
name|String
name|testInput
init|=
literal|"Record.*INSPEC.*\n"
operator|+
literal|"\n"
operator|+
literal|"RT ~ Conference-Paper\n"
operator|+
literal|"AU ~ Prechelt, Lutz"
decl_stmt|;
name|BibEntry
name|expectedEntry
init|=
operator|new
name|BibEntry
argument_list|(
name|StandardEntryType
operator|.
name|InProceedings
argument_list|)
decl_stmt|;
name|expectedEntry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|AUTHOR
argument_list|,
literal|"Prechelt, Lutz"
argument_list|)
expr_stmt|;
try|try
init|(
name|BufferedReader
name|reader
init|=
operator|new
name|BufferedReader
argument_list|(
operator|new
name|StringReader
argument_list|(
name|testInput
argument_list|)
argument_list|)
init|)
block|{
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
init|=
name|importer
operator|.
name|importDatabase
argument_list|(
name|reader
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
name|Collections
operator|.
name|singletonList
argument_list|(
name|expectedEntry
argument_list|)
argument_list|,
name|entries
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Test
DECL|method|importMiscGivesMisc ()
specifier|public
name|void
name|importMiscGivesMisc
parameter_list|()
throws|throws
name|IOException
block|{
name|String
name|testInput
init|=
literal|"Record.*INSPEC.*\n"
operator|+
literal|"\n"
operator|+
literal|"AU ~ Prechelt, Lutz \n"
operator|+
literal|"RT ~ Misc"
decl_stmt|;
name|BibEntry
name|expectedEntry
init|=
operator|new
name|BibEntry
argument_list|(
name|StandardEntryType
operator|.
name|Misc
argument_list|)
decl_stmt|;
name|expectedEntry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|AUTHOR
argument_list|,
literal|"Prechelt, Lutz"
argument_list|)
expr_stmt|;
try|try
init|(
name|BufferedReader
name|reader
init|=
operator|new
name|BufferedReader
argument_list|(
operator|new
name|StringReader
argument_list|(
name|testInput
argument_list|)
argument_list|)
init|)
block|{
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
init|=
name|importer
operator|.
name|importDatabase
argument_list|(
name|reader
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
name|entries
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|BibEntry
name|entry
init|=
name|entries
operator|.
name|get
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|expectedEntry
argument_list|,
name|entry
argument_list|)
expr_stmt|;
block|}
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
literal|"INSPEC"
argument_list|,
name|importer
operator|.
name|getName
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
literal|"inspec"
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
name|StandardFileType
operator|.
name|TXT
argument_list|,
name|importer
operator|.
name|getFileType
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
literal|"INSPEC format importer."
argument_list|,
name|importer
operator|.
name|getDescription
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

