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
name|ImportFormatPreferences
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
name|mockito
operator|.
name|Mockito
operator|.
name|mock
import|;
end_import

begin_class
DECL|class|ModsImporterTestFiles
specifier|public
class|class
name|ModsImporterTestFiles
block|{
DECL|field|FILE_ENDING
specifier|private
specifier|static
specifier|final
name|String
name|FILE_ENDING
init|=
literal|".xml"
decl_stmt|;
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
literal|"MODS"
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
annotation|@
name|ParameterizedTest
annotation|@
name|MethodSource
argument_list|(
literal|"fileNames"
argument_list|)
DECL|method|testIsRecognizedFormat (String fileName)
specifier|public
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
operator|new
name|ModsImporter
argument_list|(
name|mock
argument_list|(
name|ImportFormatPreferences
operator|.
name|class
argument_list|,
name|Answers
operator|.
name|RETURNS_DEEP_STUBS
argument_list|)
argument_list|)
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
specifier|public
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
operator|new
name|ModsImporter
argument_list|(
name|mock
argument_list|(
name|ImportFormatPreferences
operator|.
name|class
argument_list|,
name|Answers
operator|.
name|RETURNS_DEEP_STUBS
argument_list|)
argument_list|)
argument_list|,
name|fileName
argument_list|,
name|FILE_ENDING
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

