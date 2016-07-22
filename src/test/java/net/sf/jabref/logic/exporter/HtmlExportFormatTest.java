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
name|file
operator|.
name|Files
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
name|MetaData
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
name|journals
operator|.
name|JournalAbbreviationLoader
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
name|database
operator|.
name|BibDatabase
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
name|com
operator|.
name|google
operator|.
name|common
operator|.
name|base
operator|.
name|Charsets
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|After
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
DECL|class|HtmlExportFormatTest
specifier|public
class|class
name|HtmlExportFormatTest
block|{
DECL|field|exportFormat
specifier|private
name|IExportFormat
name|exportFormat
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
DECL|field|entries
specifier|public
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
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
name|ExportFormats
operator|.
name|initAllExports
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|customExports
operator|.
name|getCustomExportFormats
argument_list|(
name|Globals
operator|.
name|prefs
argument_list|)
argument_list|)
expr_stmt|;
name|exportFormat
operator|=
name|ExportFormats
operator|.
name|getExportFormat
argument_list|(
literal|"html"
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|journalAbbreviationLoader
operator|=
operator|new
name|JournalAbbreviationLoader
argument_list|()
expr_stmt|;
name|databaseContext
operator|=
operator|new
name|BibDatabaseContext
argument_list|(
operator|new
name|BibDatabase
argument_list|()
argument_list|,
operator|new
name|MetaData
argument_list|()
argument_list|)
expr_stmt|;
name|charset
operator|=
name|Charsets
operator|.
name|UTF_8
expr_stmt|;
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
literal|"title"
argument_list|,
literal|"my paper title"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
literal|"Stefan Kolb"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setCiteKey
argument_list|(
literal|"mykey"
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
name|After
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
DECL|method|emitWellFormedHtml ()
specifier|public
name|void
name|emitWellFormedHtml
parameter_list|()
throws|throws
name|Exception
block|{
name|File
name|tmpFile
init|=
name|testFolder
operator|.
name|newFile
argument_list|()
decl_stmt|;
name|String
name|filename
init|=
name|tmpFile
operator|.
name|getCanonicalPath
argument_list|()
decl_stmt|;
name|exportFormat
operator|.
name|performExport
argument_list|(
name|databaseContext
argument_list|,
name|filename
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
literal|"</html>"
argument_list|,
name|lines
operator|.
name|get
argument_list|(
name|lines
operator|.
name|size
argument_list|()
operator|-
literal|1
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

