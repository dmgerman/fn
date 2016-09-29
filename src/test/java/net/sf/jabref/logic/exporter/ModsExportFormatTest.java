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
operator|.
name|BibtexImporter
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

begin_class
DECL|class|ModsExportFormatTest
specifier|public
class|class
name|ModsExportFormatTest
block|{
DECL|field|charset
specifier|public
name|Charset
name|charset
decl_stmt|;
DECL|field|modsExportFormat
specifier|private
name|ModsExportFormat
name|modsExportFormat
decl_stmt|;
DECL|field|databaseContext
specifier|private
name|BibDatabaseContext
name|databaseContext
decl_stmt|;
DECL|field|bibtexImporter
specifier|private
name|BibtexImporter
name|bibtexImporter
decl_stmt|;
DECL|field|tempFile
specifier|private
name|File
name|tempFile
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
DECL|field|importFile
specifier|private
name|Path
name|importFile
decl_stmt|;
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
name|charset
operator|=
name|StandardCharsets
operator|.
name|UTF_8
expr_stmt|;
name|modsExportFormat
operator|=
operator|new
name|ModsExportFormat
argument_list|()
expr_stmt|;
name|bibtexImporter
operator|=
operator|new
name|BibtexImporter
argument_list|(
name|JabRefPreferences
operator|.
name|getInstance
argument_list|()
operator|.
name|getImportFormatPreferences
argument_list|()
argument_list|)
expr_stmt|;
name|tempFile
operator|=
name|testFolder
operator|.
name|newFile
argument_list|()
expr_stmt|;
name|importFile
operator|=
name|Paths
operator|.
name|get
argument_list|(
name|ModsExportFormatTest
operator|.
name|class
operator|.
name|getResource
argument_list|(
literal|"ModsExportFormatTestAllFields.bib"
argument_list|)
operator|.
name|toURI
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
argument_list|(
name|expected
operator|=
name|SaveException
operator|.
name|class
argument_list|)
DECL|method|testPerformExportTrowsSaveException ()
specifier|public
specifier|final
name|void
name|testPerformExportTrowsSaveException
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
name|performExport
argument_list|(
name|databaseContext
argument_list|,
literal|""
argument_list|,
name|charset
argument_list|,
name|entries
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testPerformExportEmptyEntry ()
specifier|public
specifier|final
name|void
name|testPerformExportEmptyEntry
parameter_list|()
throws|throws
name|Exception
block|{
name|String
name|canonicalPath
init|=
name|tempFile
operator|.
name|getCanonicalPath
argument_list|()
decl_stmt|;
name|modsExportFormat
operator|.
name|performExport
argument_list|(
name|databaseContext
argument_list|,
name|canonicalPath
argument_list|,
name|charset
argument_list|,
name|Collections
operator|.
name|emptyList
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|Collections
operator|.
name|emptyList
argument_list|()
argument_list|,
name|Files
operator|.
name|readAllLines
argument_list|(
name|Paths
operator|.
name|get
argument_list|(
name|canonicalPath
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

