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
name|time
operator|.
name|LocalDate
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
name|HashMap
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
name|Map
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
name|layout
operator|.
name|LayoutFormatterPreferences
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
name|xmp
operator|.
name|XmpPreferences
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
name|entry
operator|.
name|BibtexEntryTypes
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
name|FieldName
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
name|api
operator|.
name|extension
operator|.
name|ExtendWith
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junitpioneer
operator|.
name|jupiter
operator|.
name|TempDirectory
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
name|hamcrest
operator|.
name|MatcherAssert
operator|.
name|assertThat
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
name|ExtendWith
argument_list|(
name|TempDirectory
operator|.
name|class
argument_list|)
DECL|class|DocBook5ExporterTest
specifier|public
class|class
name|DocBook5ExporterTest
block|{
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
DECL|field|entries
specifier|public
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
decl_stmt|;
DECL|field|xmlFile
specifier|private
name|Path
name|xmlFile
decl_stmt|;
DECL|field|exportFormat
specifier|private
name|Exporter
name|exportFormat
decl_stmt|;
annotation|@
name|BeforeEach
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
throws|throws
name|URISyntaxException
block|{
name|xmlFile
operator|=
name|Paths
operator|.
name|get
argument_list|(
name|DocBook5ExporterTest
operator|.
name|class
operator|.
name|getResource
argument_list|(
literal|"Docbook5ExportFormat.xml"
argument_list|)
operator|.
name|toURI
argument_list|()
argument_list|)
expr_stmt|;
name|Map
argument_list|<
name|String
argument_list|,
name|TemplateExporter
argument_list|>
name|customFormats
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
name|LayoutFormatterPreferences
name|layoutPreferences
init|=
name|mock
argument_list|(
name|LayoutFormatterPreferences
operator|.
name|class
argument_list|,
name|Answers
operator|.
name|RETURNS_DEEP_STUBS
argument_list|)
decl_stmt|;
name|SavePreferences
name|savePreferences
init|=
name|mock
argument_list|(
name|SavePreferences
operator|.
name|class
argument_list|)
decl_stmt|;
name|XmpPreferences
name|xmpPreferences
init|=
name|mock
argument_list|(
name|XmpPreferences
operator|.
name|class
argument_list|)
decl_stmt|;
name|ExporterFactory
name|exporterFactory
init|=
name|ExporterFactory
operator|.
name|create
argument_list|(
name|customFormats
argument_list|,
name|layoutPreferences
argument_list|,
name|savePreferences
argument_list|,
name|xmpPreferences
argument_list|)
decl_stmt|;
name|exportFormat
operator|=
name|exporterFactory
operator|.
name|getExporterByName
argument_list|(
literal|"docbook5"
argument_list|)
operator|.
name|get
argument_list|()
expr_stmt|;
name|LocalDate
name|myDate
init|=
name|LocalDate
operator|.
name|of
argument_list|(
literal|2018
argument_list|,
literal|1
argument_list|,
literal|1
argument_list|)
decl_stmt|;
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
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|(
name|BibtexEntryTypes
operator|.
name|BOOK
argument_list|)
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|TITLE
argument_list|,
literal|"my paper title"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|AUTHOR
argument_list|,
literal|"Stefan Kolb and Tobias Diez"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|ISBN
argument_list|,
literal|"1-2-34"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setCiteKey
argument_list|(
literal|"mykey"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setDate
argument_list|(
operator|new
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|Date
argument_list|(
name|myDate
argument_list|)
argument_list|)
expr_stmt|;
name|entries
operator|=
name|Arrays
operator|.
name|asList
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testPerformExportForSingleEntry (@empDirectory.TempDir Path testFolder)
specifier|public
name|void
name|testPerformExportForSingleEntry
parameter_list|(
annotation|@
name|TempDirectory
operator|.
name|TempDir
name|Path
name|testFolder
parameter_list|)
throws|throws
name|Exception
block|{
name|Path
name|path
init|=
name|testFolder
operator|.
name|resolve
argument_list|(
literal|"ThisIsARandomlyNamedFile"
argument_list|)
decl_stmt|;
name|exportFormat
operator|.
name|export
argument_list|(
name|databaseContext
argument_list|,
name|path
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
name|path
argument_list|)
argument_list|)
decl_stmt|;
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

