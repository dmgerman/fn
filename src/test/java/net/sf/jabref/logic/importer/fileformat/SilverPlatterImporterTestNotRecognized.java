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
name|net
operator|.
name|URL
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

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|Assert
operator|.
name|assertNotNull
import|;
end_import

begin_class
DECL|class|SilverPlatterImporterTestNotRecognized
specifier|public
class|class
name|SilverPlatterImporterTestNotRecognized
block|{
DECL|field|testImporter
specifier|public
name|SilverPlatterImporter
name|testImporter
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
name|testImporter
operator|=
operator|new
name|SilverPlatterImporter
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testIsNotRecognizedFormat ()
specifier|public
specifier|final
name|void
name|testIsNotRecognizedFormat
parameter_list|()
throws|throws
name|Exception
block|{
name|List
argument_list|<
name|String
argument_list|>
name|notAccept
init|=
name|Arrays
operator|.
name|asList
argument_list|(
literal|"emptyFile.xml"
argument_list|,
literal|"IsiImporterTest1.isi"
argument_list|,
literal|"RisImporterTest1.ris"
argument_list|,
literal|"InspecImportTest2.txt"
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|s
range|:
name|notAccept
control|)
block|{
name|URL
name|resource
init|=
name|SilverPlatterImporter
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|s
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
literal|"resource "
operator|+
name|s
operator|+
literal|" must be available"
argument_list|,
name|resource
argument_list|)
expr_stmt|;
name|Path
name|file
init|=
name|Paths
operator|.
name|get
argument_list|(
name|resource
operator|.
name|toURI
argument_list|()
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
name|testImporter
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
block|}
end_class

end_unit

