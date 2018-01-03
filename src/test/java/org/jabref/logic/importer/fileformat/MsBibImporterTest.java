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
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|util
operator|.
name|FileType
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
name|assertEquals
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
name|assertFalse
import|;
end_import

begin_class
DECL|class|MsBibImporterTest
specifier|public
class|class
name|MsBibImporterTest
block|{
annotation|@
name|Test
DECL|method|testsGetExtensions ()
specifier|public
name|void
name|testsGetExtensions
parameter_list|()
block|{
name|MsBibImporter
name|importer
init|=
operator|new
name|MsBibImporter
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
name|FileType
operator|.
name|MSBIB
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
name|MsBibImporter
name|importer
init|=
operator|new
name|MsBibImporter
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
literal|"Importer for the MS Office 2007 XML bibliography format."
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
DECL|method|testIsNotRecognizedFormat ()
specifier|public
specifier|final
name|void
name|testIsNotRecognizedFormat
parameter_list|()
throws|throws
name|Exception
block|{
name|MsBibImporter
name|testImporter
init|=
operator|new
name|MsBibImporter
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|notAccepted
init|=
name|Arrays
operator|.
name|asList
argument_list|(
literal|"CopacImporterTest1.txt"
argument_list|,
literal|"IsiImporterTest1.isi"
argument_list|,
literal|"IsiImporterTestInspec.isi"
argument_list|,
literal|"emptyFile.xml"
argument_list|,
literal|"IsiImporterTestWOS.isi"
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|s
range|:
name|notAccepted
control|)
block|{
name|Path
name|file
init|=
name|Paths
operator|.
name|get
argument_list|(
name|MsBibImporter
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|s
argument_list|)
operator|.
name|toURI
argument_list|()
argument_list|)
decl_stmt|;
name|assertFalse
argument_list|(
name|testImporter
operator|.
name|isRecognizedFormat
argument_list|(
name|file
argument_list|,
name|Charset
operator|.
name|defaultCharset
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Test
DECL|method|testImportEntriesEmpty ()
specifier|public
specifier|final
name|void
name|testImportEntriesEmpty
parameter_list|()
throws|throws
name|IOException
throws|,
name|URISyntaxException
block|{
name|MsBibImporter
name|testImporter
init|=
operator|new
name|MsBibImporter
argument_list|()
decl_stmt|;
name|Path
name|file
init|=
name|Paths
operator|.
name|get
argument_list|(
name|MsBibImporter
operator|.
name|class
operator|.
name|getResource
argument_list|(
literal|"EmptyMsBib_Test.xml"
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
name|testImporter
operator|.
name|importDatabase
argument_list|(
name|file
argument_list|,
name|Charset
operator|.
name|defaultCharset
argument_list|()
argument_list|)
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
name|Collections
operator|.
name|emptyList
argument_list|()
argument_list|,
name|entries
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testImportEntriesNotRecognizedFormat ()
specifier|public
specifier|final
name|void
name|testImportEntriesNotRecognizedFormat
parameter_list|()
throws|throws
name|IOException
throws|,
name|URISyntaxException
block|{
name|MsBibImporter
name|testImporter
init|=
operator|new
name|MsBibImporter
argument_list|()
decl_stmt|;
name|Path
name|file
init|=
name|Paths
operator|.
name|get
argument_list|(
name|MsBibImporter
operator|.
name|class
operator|.
name|getResource
argument_list|(
literal|"CopacImporterTest1.txt"
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
name|testImporter
operator|.
name|importDatabase
argument_list|(
name|file
argument_list|,
name|Charset
operator|.
name|defaultCharset
argument_list|()
argument_list|)
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
literal|0
argument_list|,
name|entries
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetFormatName ()
specifier|public
specifier|final
name|void
name|testGetFormatName
parameter_list|()
block|{
name|MsBibImporter
name|testImporter
init|=
operator|new
name|MsBibImporter
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
literal|"MSBib"
argument_list|,
name|testImporter
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetCommandLineId ()
specifier|public
specifier|final
name|void
name|testGetCommandLineId
parameter_list|()
block|{
name|MsBibImporter
name|testImporter
init|=
operator|new
name|MsBibImporter
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
literal|"msbib"
argument_list|,
name|testImporter
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

