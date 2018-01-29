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
name|File
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
name|Collection
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
name|logic
operator|.
name|importer
operator|.
name|fileformat
operator|.
name|ModsImporter
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
name|Assert
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
name|Rule
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
name|rules
operator|.
name|TemporaryFolder
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
import|import
name|org
operator|.
name|junit
operator|.
name|runners
operator|.
name|Parameterized
operator|.
name|Parameter
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
operator|.
name|Parameters
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
import|import
name|org
operator|.
name|xmlunit
operator|.
name|builder
operator|.
name|Input
import|;
end_import

begin_import
import|import
name|org
operator|.
name|xmlunit
operator|.
name|builder
operator|.
name|Input
operator|.
name|Builder
import|;
end_import

begin_import
import|import
name|org
operator|.
name|xmlunit
operator|.
name|diff
operator|.
name|DefaultNodeMatcher
import|;
end_import

begin_import
import|import
name|org
operator|.
name|xmlunit
operator|.
name|diff
operator|.
name|ElementSelectors
import|;
end_import

begin_import
import|import
name|org
operator|.
name|xmlunit
operator|.
name|matchers
operator|.
name|CompareMatcher
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
annotation|@
name|RunWith
argument_list|(
name|Parameterized
operator|.
name|class
argument_list|)
DECL|class|ModsExportFormatTestFiles
specifier|public
class|class
name|ModsExportFormatTestFiles
block|{
DECL|field|charset
specifier|public
name|Charset
name|charset
decl_stmt|;
DECL|field|databaseContext
specifier|private
name|BibDatabaseContext
name|databaseContext
decl_stmt|;
DECL|field|tempFile
specifier|private
name|File
name|tempFile
decl_stmt|;
DECL|field|modsExportFormat
specifier|private
name|ModsExporter
name|modsExportFormat
decl_stmt|;
DECL|field|bibtexImporter
specifier|private
name|BibtexImporter
name|bibtexImporter
decl_stmt|;
DECL|field|modsImporter
specifier|private
name|ModsImporter
name|modsImporter
decl_stmt|;
DECL|field|importFile
specifier|private
name|Path
name|importFile
decl_stmt|;
annotation|@
name|Parameter
DECL|field|filename
specifier|public
name|String
name|filename
decl_stmt|;
annotation|@
name|Rule
DECL|field|testFolder
specifier|public
name|TemporaryFolder
name|testFolder
init|=
operator|new
name|TemporaryFolder
argument_list|()
decl_stmt|;
annotation|@
name|Parameters
argument_list|(
name|name
operator|=
literal|"{0}"
argument_list|)
DECL|method|fileNames ()
specifier|public
specifier|static
name|Collection
argument_list|<
name|String
argument_list|>
name|fileNames
parameter_list|()
throws|throws
name|Exception
block|{
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
name|Paths
operator|.
name|get
argument_list|(
name|ModsExportFormatTestFiles
operator|.
name|class
operator|.
name|getResource
argument_list|(
literal|""
argument_list|)
operator|.
name|toURI
argument_list|()
argument_list|)
argument_list|)
init|)
block|{
comment|//            stream.forEach(n -> System.out.println(n));
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
literal|"Mods"
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
return|;
block|}
block|}
annotation|@
name|Before
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
throws|throws
name|Exception
block|{
name|databaseContext
operator|=
operator|new
name|BibDatabaseContext
argument_list|()
expr_stmt|;
name|importFile
operator|=
name|Paths
operator|.
name|get
argument_list|(
name|ModsExportFormatTestFiles
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|filename
argument_list|)
operator|.
name|toURI
argument_list|()
argument_list|)
expr_stmt|;
name|charset
operator|=
name|StandardCharsets
operator|.
name|UTF_8
expr_stmt|;
name|modsExportFormat
operator|=
operator|new
name|ModsExporter
argument_list|()
expr_stmt|;
name|tempFile
operator|=
name|testFolder
operator|.
name|newFile
argument_list|()
expr_stmt|;
name|bibtexImporter
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
name|modsImporter
operator|=
operator|new
name|ModsImporter
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testPerformExport ()
specifier|public
specifier|final
name|void
name|testPerformExport
parameter_list|()
throws|throws
name|Exception
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
name|String
name|tempFilename
init|=
name|tempFile
operator|.
name|getCanonicalPath
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
init|=
name|bibtexImporter
operator|.
name|importDatabase
argument_list|(
name|importFile
argument_list|,
name|charset
argument_list|)
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
decl_stmt|;
name|Path
name|xmlFile
init|=
name|Paths
operator|.
name|get
argument_list|(
name|ModsExportFormatTestFiles
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|xmlFileName
argument_list|)
operator|.
name|toURI
argument_list|()
argument_list|)
decl_stmt|;
name|modsExportFormat
operator|.
name|export
argument_list|(
name|databaseContext
argument_list|,
name|tempFile
operator|.
name|toPath
argument_list|()
argument_list|,
name|charset
argument_list|,
name|entries
argument_list|)
expr_stmt|;
name|Builder
name|control
init|=
name|Input
operator|.
name|from
argument_list|(
name|Files
operator|.
name|newInputStream
argument_list|(
name|xmlFile
argument_list|)
argument_list|)
decl_stmt|;
name|Builder
name|test
init|=
name|Input
operator|.
name|from
argument_list|(
name|Files
operator|.
name|newInputStream
argument_list|(
name|Paths
operator|.
name|get
argument_list|(
name|tempFilename
argument_list|)
argument_list|)
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertThat
argument_list|(
name|test
argument_list|,
name|CompareMatcher
operator|.
name|isSimilarTo
argument_list|(
name|control
argument_list|)
operator|.
name|withNodeMatcher
argument_list|(
operator|new
name|DefaultNodeMatcher
argument_list|(
name|ElementSelectors
operator|.
name|byNameAndText
argument_list|)
argument_list|)
operator|.
name|throwComparisonFailure
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testExportAsModsAndThenImportAsMods ()
specifier|public
specifier|final
name|void
name|testExportAsModsAndThenImportAsMods
parameter_list|()
throws|throws
name|Exception
block|{
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
init|=
name|bibtexImporter
operator|.
name|importDatabase
argument_list|(
name|importFile
argument_list|,
name|charset
argument_list|)
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
decl_stmt|;
name|modsExportFormat
operator|.
name|export
argument_list|(
name|databaseContext
argument_list|,
name|tempFile
operator|.
name|toPath
argument_list|()
argument_list|,
name|charset
argument_list|,
name|entries
argument_list|)
expr_stmt|;
name|BibEntryAssert
operator|.
name|assertEquals
argument_list|(
name|entries
argument_list|,
name|Paths
operator|.
name|get
argument_list|(
name|tempFile
operator|.
name|getPath
argument_list|()
argument_list|)
argument_list|,
name|modsImporter
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testImportAsModsAndExportAsMods ()
specifier|public
specifier|final
name|void
name|testImportAsModsAndExportAsMods
parameter_list|()
throws|throws
name|Exception
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
name|String
name|tempFilename
init|=
name|tempFile
operator|.
name|getCanonicalPath
argument_list|()
decl_stmt|;
name|Path
name|xmlFile
init|=
name|Paths
operator|.
name|get
argument_list|(
name|ModsExportFormatTestFiles
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|xmlFileName
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
name|entries
init|=
name|modsImporter
operator|.
name|importDatabase
argument_list|(
name|xmlFile
argument_list|,
name|charset
argument_list|)
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
decl_stmt|;
name|modsExportFormat
operator|.
name|export
argument_list|(
name|databaseContext
argument_list|,
name|tempFile
operator|.
name|toPath
argument_list|()
argument_list|,
name|charset
argument_list|,
name|entries
argument_list|)
expr_stmt|;
name|Builder
name|control
init|=
name|Input
operator|.
name|from
argument_list|(
name|Files
operator|.
name|newInputStream
argument_list|(
name|xmlFile
argument_list|)
argument_list|)
decl_stmt|;
name|Builder
name|test
init|=
name|Input
operator|.
name|from
argument_list|(
name|Files
operator|.
name|newInputStream
argument_list|(
name|Paths
operator|.
name|get
argument_list|(
name|tempFilename
argument_list|)
argument_list|)
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertThat
argument_list|(
name|test
argument_list|,
name|CompareMatcher
operator|.
name|isSimilarTo
argument_list|(
name|control
argument_list|)
operator|.
name|withNodeMatcher
argument_list|(
operator|new
name|DefaultNodeMatcher
argument_list|(
name|ElementSelectors
operator|.
name|byNameAndText
argument_list|)
argument_list|)
operator|.
name|throwComparisonFailure
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

