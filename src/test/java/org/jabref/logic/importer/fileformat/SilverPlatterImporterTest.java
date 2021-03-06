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
name|util
operator|.
name|function
operator|.
name|Predicate
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
name|importer
operator|.
name|Importer
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
import|import
name|org
operator|.
name|junit
operator|.
name|jupiter
operator|.
name|params
operator|.
name|ParameterizedTest
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
name|params
operator|.
name|provider
operator|.
name|MethodSource
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

begin_class
DECL|class|SilverPlatterImporterTest
class|class
name|SilverPlatterImporterTest
block|{
DECL|field|FILE_ENDING
specifier|private
specifier|static
specifier|final
name|String
name|FILE_ENDING
init|=
literal|".txt"
decl_stmt|;
DECL|field|testImporter
specifier|private
name|Importer
name|testImporter
decl_stmt|;
annotation|@
name|BeforeEach
DECL|method|setUp ()
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
DECL|method|fileNames ()
specifier|private
specifier|static
name|Stream
argument_list|<
name|String
argument_list|>
name|fileNames
parameter_list|()
throws|throws
name|IOException
block|{
name|Predicate
argument_list|<
name|String
argument_list|>
name|fileName
init|=
name|name
lambda|->
name|name
operator|.
name|startsWith
argument_list|(
literal|"SilverPlatterImporterTest"
argument_list|)
operator|&&
name|name
operator|.
name|endsWith
argument_list|(
name|FILE_ENDING
argument_list|)
decl_stmt|;
return|return
name|ImporterTestEngine
operator|.
name|getTestFiles
argument_list|(
name|fileName
argument_list|)
operator|.
name|stream
argument_list|()
return|;
block|}
DECL|method|invalidFileNames ()
specifier|private
specifier|static
name|Stream
argument_list|<
name|String
argument_list|>
name|invalidFileNames
parameter_list|()
throws|throws
name|IOException
block|{
name|Predicate
argument_list|<
name|String
argument_list|>
name|fileName
init|=
name|name
lambda|->
operator|!
name|name
operator|.
name|startsWith
argument_list|(
literal|"SilverPlatterImporterTest"
argument_list|)
decl_stmt|;
return|return
name|ImporterTestEngine
operator|.
name|getTestFiles
argument_list|(
name|fileName
argument_list|)
operator|.
name|stream
argument_list|()
return|;
block|}
annotation|@
name|ParameterizedTest
annotation|@
name|MethodSource
argument_list|(
literal|"fileNames"
argument_list|)
DECL|method|testIsRecognizedFormat (String fileName)
name|void
name|testIsRecognizedFormat
parameter_list|(
name|String
name|fileName
parameter_list|)
throws|throws
name|IOException
block|{
name|ImporterTestEngine
operator|.
name|testIsRecognizedFormat
argument_list|(
name|testImporter
argument_list|,
name|fileName
argument_list|)
expr_stmt|;
block|}
annotation|@
name|ParameterizedTest
annotation|@
name|MethodSource
argument_list|(
literal|"invalidFileNames"
argument_list|)
DECL|method|testIsNotRecognizedFormat (String fileName)
name|void
name|testIsNotRecognizedFormat
parameter_list|(
name|String
name|fileName
parameter_list|)
throws|throws
name|IOException
block|{
name|ImporterTestEngine
operator|.
name|testIsNotRecognizedFormat
argument_list|(
name|testImporter
argument_list|,
name|fileName
argument_list|)
expr_stmt|;
block|}
annotation|@
name|ParameterizedTest
annotation|@
name|MethodSource
argument_list|(
literal|"fileNames"
argument_list|)
DECL|method|testImportEntries (String fileName)
name|void
name|testImportEntries
parameter_list|(
name|String
name|fileName
parameter_list|)
throws|throws
name|Exception
block|{
name|ImporterTestEngine
operator|.
name|testImportEntries
argument_list|(
name|testImporter
argument_list|,
name|fileName
argument_list|,
name|FILE_ENDING
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testsGetExtensions ()
name|void
name|testsGetExtensions
parameter_list|()
block|{
name|assertEquals
argument_list|(
name|StandardFileType
operator|.
name|SILVER_PLATTER
argument_list|,
name|testImporter
operator|.
name|getFileType
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetDescription ()
name|void
name|testGetDescription
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"Imports a SilverPlatter exported file."
argument_list|,
name|testImporter
operator|.
name|getDescription
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

