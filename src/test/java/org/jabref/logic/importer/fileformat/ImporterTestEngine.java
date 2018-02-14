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
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
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
name|Collectors
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
name|bibtex
operator|.
name|BibEntryAssert
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
name|Assertions
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
DECL|class|ImporterTestEngine
specifier|public
class|class
name|ImporterTestEngine
block|{
DECL|field|TEST_RESOURCES
specifier|private
specifier|static
specifier|final
name|String
name|TEST_RESOURCES
init|=
literal|"src/test/resources/org/jabref/logic/importer/fileformat"
decl_stmt|;
comment|/**      * @param fileNamePredicate A predicate that describes the files which contain tests      * @return A collection with the names of files in the test folder      * @throws IOException if there is a problem when trying to read the files in the file system      */
DECL|method|getTestFiles (Predicate<String> fileNamePredicate)
specifier|public
specifier|static
name|Collection
argument_list|<
name|String
argument_list|>
name|getTestFiles
parameter_list|(
name|Predicate
argument_list|<
name|String
argument_list|>
name|fileNamePredicate
parameter_list|)
throws|throws
name|IOException
block|{
try|try
init|(
name|Stream
argument_list|<
name|Path
argument_list|>
name|stream
init|=
name|Files
operator|.
name|list
argument_list|(
name|Paths
operator|.
name|get
argument_list|(
name|TEST_RESOURCES
argument_list|)
argument_list|)
init|)
block|{
return|return
name|stream
operator|.
name|map
argument_list|(
name|path
lambda|->
name|path
operator|.
name|getFileName
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
operator|.
name|filter
argument_list|(
name|fileNamePredicate
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
argument_list|)
return|;
block|}
block|}
DECL|method|testIsRecognizedFormat (Importer importer, String fileName)
specifier|public
specifier|static
name|void
name|testIsRecognizedFormat
parameter_list|(
name|Importer
name|importer
parameter_list|,
name|String
name|fileName
parameter_list|)
throws|throws
name|IOException
block|{
name|Assertions
operator|.
name|assertTrue
argument_list|(
name|importer
operator|.
name|isRecognizedFormat
argument_list|(
name|getPath
argument_list|(
name|fileName
argument_list|)
argument_list|,
name|StandardCharsets
operator|.
name|UTF_8
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|testIsNotRecognizedFormat (Importer importer, String fileName)
specifier|public
specifier|static
name|void
name|testIsNotRecognizedFormat
parameter_list|(
name|Importer
name|importer
parameter_list|,
name|String
name|fileName
parameter_list|)
throws|throws
name|IOException
block|{
name|Assertions
operator|.
name|assertFalse
argument_list|(
name|importer
operator|.
name|isRecognizedFormat
argument_list|(
name|getPath
argument_list|(
name|fileName
argument_list|)
argument_list|,
name|StandardCharsets
operator|.
name|UTF_8
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|testImportEntries (Importer importer, String fileName, String fileType)
specifier|public
specifier|static
name|void
name|testImportEntries
parameter_list|(
name|Importer
name|importer
parameter_list|,
name|String
name|fileName
parameter_list|,
name|String
name|fileType
parameter_list|)
throws|throws
name|IOException
block|{
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
init|=
name|importer
operator|.
name|importDatabase
argument_list|(
name|getPath
argument_list|(
name|fileName
argument_list|)
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
decl_stmt|;
name|BibEntryAssert
operator|.
name|assertEquals
argument_list|(
name|ImporterTestEngine
operator|.
name|class
argument_list|,
name|fileName
operator|.
name|replaceAll
argument_list|(
name|fileType
argument_list|,
literal|".bib"
argument_list|)
argument_list|,
name|entries
argument_list|)
expr_stmt|;
block|}
DECL|method|getPath (String fileName)
specifier|private
specifier|static
name|Path
name|getPath
parameter_list|(
name|String
name|fileName
parameter_list|)
throws|throws
name|IOException
block|{
try|try
block|{
return|return
name|Paths
operator|.
name|get
argument_list|(
name|ImporterTestEngine
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|fileName
argument_list|)
operator|.
name|toURI
argument_list|()
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|URISyntaxException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IOException
argument_list|(
name|e
argument_list|)
throw|;
block|}
block|}
DECL|method|testImportMalformedFiles (Importer importer, String fileName)
specifier|public
specifier|static
name|void
name|testImportMalformedFiles
parameter_list|(
name|Importer
name|importer
parameter_list|,
name|String
name|fileName
parameter_list|)
throws|throws
name|IOException
block|{
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
init|=
name|importer
operator|.
name|importDatabase
argument_list|(
name|getPath
argument_list|(
name|fileName
argument_list|)
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
decl_stmt|;
name|assertEquals
argument_list|(
name|entries
argument_list|,
operator|new
name|ArrayList
argument_list|<
name|BibEntry
argument_list|>
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit
