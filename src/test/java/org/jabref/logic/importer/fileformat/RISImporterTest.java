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
name|junit
operator|.
name|jupiter
operator|.
name|api
operator|.
name|Assertions
operator|.
name|assertFalse
import|;
end_import

begin_class
DECL|class|RISImporterTest
specifier|public
class|class
name|RISImporterTest
block|{
DECL|field|importer
specifier|private
name|RisImporter
name|importer
decl_stmt|;
annotation|@
name|BeforeEach
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
block|{
name|importer
operator|=
operator|new
name|RisImporter
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
name|assertEquals
argument_list|(
literal|"RIS"
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
literal|"ris"
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
name|RIS
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
literal|"Imports a Biblioscape Tag File."
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
DECL|method|testIfNotRecognizedFormat ()
specifier|public
name|void
name|testIfNotRecognizedFormat
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
name|RISImporterTest
operator|.
name|class
operator|.
name|getResource
argument_list|(
literal|"RisImporterCorrupted.ris"
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
name|StandardCharsets
operator|.
name|UTF_8
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

