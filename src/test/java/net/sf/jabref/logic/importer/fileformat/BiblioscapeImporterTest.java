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
name|Collections
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
name|Test
import|;
end_import

begin_class
DECL|class|BiblioscapeImporterTest
specifier|public
class|class
name|BiblioscapeImporterTest
block|{
DECL|field|importer
specifier|private
name|BiblioscapeImporter
name|importer
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
name|importer
operator|=
operator|new
name|BiblioscapeImporter
argument_list|()
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
name|Assert
operator|.
name|assertEquals
argument_list|(
name|importer
operator|.
name|getFormatName
argument_list|()
argument_list|,
literal|"Biblioscape"
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
name|Assert
operator|.
name|assertEquals
argument_list|(
name|FileExtensions
operator|.
name|BILBIOSCAPE
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
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Imports a Biblioscape Tag File.\n"
operator|+
literal|"Several Biblioscape field types are ignored. Others are only included in the BibTeX field \"comment\"."
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
DECL|method|testGetCLIID ()
specifier|public
name|void
name|testGetCLIID
parameter_list|()
block|{
name|Assert
operator|.
name|assertEquals
argument_list|(
name|importer
operator|.
name|getId
argument_list|()
argument_list|,
literal|"biblioscape"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testImportEntriesAbortion ()
specifier|public
name|void
name|testImportEntriesAbortion
parameter_list|()
throws|throws
name|Throwable
block|{
name|Path
name|file
init|=
name|Paths
operator|.
name|get
argument_list|(
name|BiblioscapeImporter
operator|.
name|class
operator|.
name|getResource
argument_list|(
literal|"BiblioscapeImporterTestCorrupt.txt"
argument_list|)
operator|.
name|toURI
argument_list|()
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|Collections
operator|.
name|emptyList
argument_list|()
argument_list|,
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
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

