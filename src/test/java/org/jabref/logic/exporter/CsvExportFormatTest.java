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
name|junit
operator|.
name|jupiter
operator|.
name|api
operator|.
name|AfterEach
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
name|io
operator|.
name|TempDir
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
DECL|class|CsvExportFormatTest
specifier|public
class|class
name|CsvExportFormatTest
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
block|{
name|List
argument_list|<
name|TemplateExporter
argument_list|>
name|customFormats
init|=
operator|new
name|ArrayList
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
literal|"oocsv"
argument_list|)
operator|.
name|get
argument_list|()
expr_stmt|;
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
block|}
annotation|@
name|AfterEach
DECL|method|tearDown ()
specifier|public
name|void
name|tearDown
parameter_list|()
block|{
name|exportFormat
operator|=
literal|null
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testPerformExportForSingleAuthor (@empDir Path testFolder)
specifier|public
name|void
name|testPerformExportForSingleAuthor
parameter_list|(
annotation|@
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
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
literal|"Someone, Van Something"
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
init|=
name|Arrays
operator|.
name|asList
argument_list|(
name|entry
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
name|List
argument_list|<
name|String
argument_list|>
name|lines
init|=
name|Files
operator|.
name|readAllLines
argument_list|(
name|path
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|2
argument_list|,
name|lines
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"10,\"\",\"\",\"Someone, Van Something\",\"\",\"\",,,\"\",\"\",,\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\""
argument_list|,
name|lines
operator|.
name|get
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testPerformExportForMultipleAuthors (@empDir Path testFolder)
specifier|public
name|void
name|testPerformExportForMultipleAuthors
parameter_list|(
annotation|@
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
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
literal|"von Neumann, John and Smith, John and Black Brown, Peter"
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
init|=
name|Arrays
operator|.
name|asList
argument_list|(
name|entry
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
name|List
argument_list|<
name|String
argument_list|>
name|lines
init|=
name|Files
operator|.
name|readAllLines
argument_list|(
name|path
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|2
argument_list|,
name|lines
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"10,\"\",\"\",\"von Neumann, John; Smith, John; Black Brown, Peter\",\"\",\"\",,,\"\",\"\",,\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\""
argument_list|,
name|lines
operator|.
name|get
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testPerformExportForSingleEditor (@empDir Path testFolder)
specifier|public
name|void
name|testPerformExportForSingleEditor
parameter_list|(
annotation|@
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
name|File
name|tmpFile
init|=
name|path
operator|.
name|toFile
argument_list|()
decl_stmt|;
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"editor"
argument_list|,
literal|"Someone, Van Something"
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
init|=
name|Arrays
operator|.
name|asList
argument_list|(
name|entry
argument_list|)
decl_stmt|;
name|exportFormat
operator|.
name|export
argument_list|(
name|databaseContext
argument_list|,
name|tmpFile
operator|.
name|toPath
argument_list|()
argument_list|,
name|charset
argument_list|,
name|entries
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|lines
init|=
name|Files
operator|.
name|readAllLines
argument_list|(
name|tmpFile
operator|.
name|toPath
argument_list|()
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|2
argument_list|,
name|lines
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"10,\"\",\"\",\"\",\"\",\"\",,,\"\",\"\",,\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"Someone, Van Something\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\""
argument_list|,
name|lines
operator|.
name|get
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testPerformExportForMultipleEditors (@empDir Path testFolder)
specifier|public
name|void
name|testPerformExportForMultipleEditors
parameter_list|(
annotation|@
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
name|File
name|tmpFile
init|=
name|path
operator|.
name|toFile
argument_list|()
decl_stmt|;
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"editor"
argument_list|,
literal|"von Neumann, John and Smith, John and Black Brown, Peter"
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
init|=
name|Arrays
operator|.
name|asList
argument_list|(
name|entry
argument_list|)
decl_stmt|;
name|exportFormat
operator|.
name|export
argument_list|(
name|databaseContext
argument_list|,
name|tmpFile
operator|.
name|toPath
argument_list|()
argument_list|,
name|charset
argument_list|,
name|entries
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|lines
init|=
name|Files
operator|.
name|readAllLines
argument_list|(
name|tmpFile
operator|.
name|toPath
argument_list|()
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|2
argument_list|,
name|lines
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"10,\"\",\"\",\"\",\"\",\"\",,,\"\",\"\",,\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"von Neumann, John; Smith, John; Black Brown, Peter\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\""
argument_list|,
name|lines
operator|.
name|get
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

