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
name|Files
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
name|List
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
name|Collectors
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
name|importer
operator|.
name|ImportFormatPreferences
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
name|importer
operator|.
name|fileformat
operator|.
name|BibtexImporter
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

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|util
operator|.
name|DummyFileUpdateMonitor
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
name|io
operator|.
name|TempDir
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
import|import
name|org
operator|.
name|mockito
operator|.
name|Answers
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

begin_import
import|import static
name|org
operator|.
name|mockito
operator|.
name|Mockito
operator|.
name|mock
import|;
end_import

begin_class
DECL|class|MSBibExportFormatTestFiles
specifier|public
class|class
name|MSBibExportFormatTestFiles
block|{
DECL|field|resourceDir
specifier|private
specifier|static
name|Path
name|resourceDir
decl_stmt|;
DECL|field|databaseContext
specifier|public
name|BibDatabaseContext
name|databaseContext
decl_stmt|;
DECL|field|charset
specifier|public
name|Charset
name|charset
decl_stmt|;
DECL|field|exportedFile
specifier|private
name|Path
name|exportedFile
decl_stmt|;
DECL|field|msBibExportFormat
specifier|private
name|MSBibExporter
name|msBibExportFormat
decl_stmt|;
DECL|field|testImporter
specifier|private
name|BibtexImporter
name|testImporter
decl_stmt|;
DECL|method|fileNames ()
specifier|static
name|Stream
argument_list|<
name|String
argument_list|>
name|fileNames
parameter_list|()
throws|throws
name|IOException
throws|,
name|URISyntaxException
block|{
comment|//we have to point it to one existing file, otherwise it will return the default class path
name|resourceDir
operator|=
name|Paths
operator|.
name|get
argument_list|(
name|MSBibExportFormatTestFiles
operator|.
name|class
operator|.
name|getResource
argument_list|(
literal|"MsBibExportFormatTest1.bib"
argument_list|)
operator|.
name|toURI
argument_list|()
argument_list|)
operator|.
name|getParent
argument_list|()
expr_stmt|;
try|try
init|(
name|Stream
argument_list|<
name|Path
argument_list|>
name|stream
init|=
name|Files
operator|.
name|list
argument_list|(
name|resourceDir
argument_list|)
init|)
block|{
return|return
name|stream
operator|.
name|map
argument_list|(
name|n
lambda|->
name|n
operator|.
name|getFileName
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
operator|.
name|filter
argument_list|(
name|n
lambda|->
name|n
operator|.
name|endsWith
argument_list|(
literal|".bib"
argument_list|)
argument_list|)
operator|.
name|filter
argument_list|(
name|n
lambda|->
name|n
operator|.
name|startsWith
argument_list|(
literal|"MsBib"
argument_list|)
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
argument_list|)
operator|.
name|stream
argument_list|()
return|;
block|}
block|}
annotation|@
name|BeforeEach
DECL|method|setUp (@empDir Path testFolder)
name|void
name|setUp
parameter_list|(
annotation|@
name|TempDir
name|Path
name|testFolder
parameter_list|)
throws|throws
name|Exception
block|{
name|databaseContext
operator|=
operator|new
name|BibDatabaseContext
argument_list|()
expr_stmt|;
name|charset
operator|=
name|StandardCharsets
operator|.
name|UTF_8
expr_stmt|;
name|msBibExportFormat
operator|=
operator|new
name|MSBibExporter
argument_list|()
expr_stmt|;
name|Path
name|path
init|=
name|testFolder
operator|.
name|resolve
argument_list|(
literal|"ARandomlyNamedFile.tmp"
argument_list|)
decl_stmt|;
name|exportedFile
operator|=
name|Files
operator|.
name|createFile
argument_list|(
name|path
argument_list|)
expr_stmt|;
name|testImporter
operator|=
operator|new
name|BibtexImporter
argument_list|(
name|mock
argument_list|(
name|ImportFormatPreferences
operator|.
name|class
argument_list|,
name|Answers
operator|.
name|RETURNS_DEEP_STUBS
argument_list|)
argument_list|,
operator|new
name|DummyFileUpdateMonitor
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|ParameterizedTest
annotation|@
name|MethodSource
argument_list|(
literal|"fileNames"
argument_list|)
DECL|method|testPerformExport (String filename)
name|void
name|testPerformExport
parameter_list|(
name|String
name|filename
parameter_list|)
throws|throws
name|IOException
throws|,
name|SaveException
block|{
name|String
name|xmlFileName
init|=
name|filename
operator|.
name|replace
argument_list|(
literal|".bib"
argument_list|,
literal|".xml"
argument_list|)
decl_stmt|;
name|Path
name|expectedFile
init|=
name|resourceDir
operator|.
name|resolve
argument_list|(
name|xmlFileName
argument_list|)
decl_stmt|;
name|Path
name|importFile
init|=
name|resourceDir
operator|.
name|resolve
argument_list|(
name|filename
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
init|=
name|testImporter
operator|.
name|importDatabase
argument_list|(
name|importFile
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
name|msBibExportFormat
operator|.
name|export
argument_list|(
name|databaseContext
argument_list|,
name|exportedFile
argument_list|,
name|charset
argument_list|,
name|entries
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Files
operator|.
name|readAllLines
argument_list|(
name|expectedFile
argument_list|)
argument_list|,
name|Files
operator|.
name|readAllLines
argument_list|(
name|exportedFile
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

