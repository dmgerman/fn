begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.importer.fileformat
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
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
name|io
operator|.
name|InputStream
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
name|DirectoryStream
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
name|stream
operator|.
name|Collectors
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
name|JabRefPreferences
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
name|bibtex
operator|.
name|BibEntryAssert
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
import|import
name|org
operator|.
name|junit
operator|.
name|runner
operator|.
name|RunWith
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|runners
operator|.
name|Parameterized
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|runners
operator|.
name|Parameterized
operator|.
name|Parameter
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|runners
operator|.
name|Parameterized
operator|.
name|Parameters
import|;
end_import

begin_class
annotation|@
name|RunWith
argument_list|(
name|Parameterized
operator|.
name|class
argument_list|)
DECL|class|CopacImporterTestFiles
specifier|public
class|class
name|CopacImporterTestFiles
block|{
DECL|field|copacImporter
specifier|private
name|CopacImporter
name|copacImporter
decl_stmt|;
DECL|field|FILEFORMAT_PATH
specifier|private
specifier|final
specifier|static
name|String
name|FILEFORMAT_PATH
init|=
literal|"src/test/resources/net/sf/jabref/importer/fileformat"
decl_stmt|;
annotation|@
name|Parameter
DECL|field|fileName
specifier|public
name|String
name|fileName
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
name|copacImporter
operator|=
operator|new
name|CopacImporter
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Parameters
argument_list|(
name|name
operator|=
literal|"{0}"
argument_list|)
DECL|method|fileNames ()
specifier|public
specifier|static
name|Collection
argument_list|<
name|String
argument_list|>
name|fileNames
parameter_list|()
throws|throws
name|IOException
block|{
name|List
argument_list|<
name|String
argument_list|>
name|files
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
try|try
init|(
name|DirectoryStream
argument_list|<
name|Path
argument_list|>
name|stream
init|=
name|Files
operator|.
name|newDirectoryStream
argument_list|(
name|Paths
operator|.
name|get
argument_list|(
name|FILEFORMAT_PATH
argument_list|)
argument_list|)
init|)
block|{
name|stream
operator|.
name|forEach
argument_list|(
name|n
lambda|->
name|files
operator|.
name|add
argument_list|(
name|n
operator|.
name|getFileName
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|files
operator|.
name|stream
argument_list|()
operator|.
name|filter
argument_list|(
name|n
lambda|->
name|n
operator|.
name|startsWith
argument_list|(
literal|"CopacImporterTest"
argument_list|)
argument_list|)
operator|.
name|filter
argument_list|(
name|n
lambda|->
name|n
operator|.
name|endsWith
argument_list|(
literal|".txt"
argument_list|)
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
annotation|@
name|Test
DECL|method|testIsRecognizedFormat ()
specifier|public
name|void
name|testIsRecognizedFormat
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
name|CopacImporterTest
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
decl_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|copacImporter
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
annotation|@
name|Test
DECL|method|testImportEntries ()
specifier|public
name|void
name|testImportEntries
parameter_list|()
throws|throws
name|IOException
throws|,
name|URISyntaxException
block|{
name|String
name|bibFileName
init|=
name|fileName
operator|.
name|replace
argument_list|(
literal|".txt"
argument_list|,
literal|".bib"
argument_list|)
decl_stmt|;
try|try
init|(
name|InputStream
name|bibStream
init|=
name|BibtexImporterTest
operator|.
name|class
operator|.
name|getResourceAsStream
argument_list|(
name|bibFileName
argument_list|)
init|)
block|{
name|BibEntryAssert
operator|.
name|assertEquals
argument_list|(
name|bibStream
argument_list|,
name|CopacImporterTest
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|fileName
argument_list|)
argument_list|,
name|copacImporter
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

