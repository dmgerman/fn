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
name|io
operator|.
name|IOException
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
DECL|class|MsBibExportFormatTest
specifier|public
class|class
name|MsBibExportFormatTest
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
DECL|field|tempFile
specifier|public
name|File
name|tempFile
decl_stmt|;
DECL|field|msBibExportFormat
specifier|public
name|MSBibExportFormat
name|msBibExportFormat
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
name|Charsets
operator|.
name|UTF_8
expr_stmt|;
name|msBibExportFormat
operator|=
operator|new
name|MSBibExportFormat
argument_list|()
expr_stmt|;
name|tempFile
operator|=
name|testFolder
operator|.
name|newFile
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testPerformExportWithNoEntry ()
specifier|public
specifier|final
name|void
name|testPerformExportWithNoEntry
parameter_list|()
throws|throws
name|IOException
throws|,
name|SaveException
block|{
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
init|=
name|Collections
operator|.
name|emptyList
argument_list|()
decl_stmt|;
name|String
name|tempFileName
init|=
name|tempFile
operator|.
name|getCanonicalPath
argument_list|()
decl_stmt|;
name|msBibExportFormat
operator|.
name|performExport
argument_list|(
name|databaseContext
argument_list|,
name|tempFileName
argument_list|,
name|charset
argument_list|,
name|entries
argument_list|)
expr_stmt|;
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
name|tempFile
operator|.
name|toPath
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

