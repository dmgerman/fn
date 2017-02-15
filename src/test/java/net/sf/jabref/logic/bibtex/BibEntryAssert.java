begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.bibtex
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|bibtex
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
name|io
operator|.
name|InputStreamReader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|Reader
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
name|Importer
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
name|ParserResult
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
name|BibtexParser
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

begin_class
DECL|class|BibEntryAssert
specifier|public
class|class
name|BibEntryAssert
block|{
comment|/**      * Reads a single entry from the resource using `getResourceAsStream` from the given class. The resource has to      * contain a single entry      *      * @param clazz the class where to call `getResourceAsStream`      * @param resourceName the resource to read      * @param entry the entry to compare with      */
DECL|method|assertEquals (Class<?> clazz, String resourceName, BibEntry entry)
specifier|public
specifier|static
name|void
name|assertEquals
parameter_list|(
name|Class
argument_list|<
name|?
argument_list|>
name|clazz
parameter_list|,
name|String
name|resourceName
parameter_list|,
name|BibEntry
name|entry
parameter_list|)
throws|throws
name|IOException
block|{
name|Assert
operator|.
name|assertNotNull
argument_list|(
name|clazz
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertNotNull
argument_list|(
name|resourceName
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertNotNull
argument_list|(
name|entry
argument_list|)
expr_stmt|;
try|try
init|(
name|InputStream
name|shouldBeIs
init|=
name|clazz
operator|.
name|getResourceAsStream
argument_list|(
name|resourceName
argument_list|)
init|)
block|{
name|BibEntryAssert
operator|.
name|assertEquals
argument_list|(
name|shouldBeIs
argument_list|,
name|entry
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Reads a single entry from the resource using `getResourceAsStream` from the given class. The resource has to      * contain a single entry      *      * @param clazz the class where to call `getResourceAsStream`      * @param resourceName the resource to read      * @param asIsEntries a list containing a single entry to compare with      */
DECL|method|assertEquals (Class<?> clazz, String resourceName, List<BibEntry> asIsEntries)
specifier|public
specifier|static
name|void
name|assertEquals
parameter_list|(
name|Class
argument_list|<
name|?
argument_list|>
name|clazz
parameter_list|,
name|String
name|resourceName
parameter_list|,
name|List
argument_list|<
name|BibEntry
argument_list|>
name|asIsEntries
parameter_list|)
throws|throws
name|IOException
block|{
name|Assert
operator|.
name|assertNotNull
argument_list|(
name|clazz
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertNotNull
argument_list|(
name|resourceName
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertNotNull
argument_list|(
name|asIsEntries
argument_list|)
expr_stmt|;
try|try
init|(
name|InputStream
name|shouldBeIs
init|=
name|clazz
operator|.
name|getResourceAsStream
argument_list|(
name|resourceName
argument_list|)
init|)
block|{
name|BibEntryAssert
operator|.
name|assertEquals
argument_list|(
name|shouldBeIs
argument_list|,
name|asIsEntries
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|getListFromInputStream (InputStream is)
specifier|private
specifier|static
name|List
argument_list|<
name|BibEntry
argument_list|>
name|getListFromInputStream
parameter_list|(
name|InputStream
name|is
parameter_list|)
throws|throws
name|IOException
block|{
name|ParserResult
name|result
decl_stmt|;
try|try
init|(
name|Reader
name|reader
init|=
operator|new
name|InputStreamReader
argument_list|(
name|is
argument_list|,
name|StandardCharsets
operator|.
name|UTF_8
argument_list|)
init|)
block|{
name|BibtexParser
name|parser
init|=
operator|new
name|BibtexParser
argument_list|(
name|JabRefPreferences
operator|.
name|getInstance
argument_list|()
operator|.
name|getImportFormatPreferences
argument_list|()
argument_list|)
decl_stmt|;
name|result
operator|=
name|parser
operator|.
name|parse
argument_list|(
name|reader
argument_list|)
expr_stmt|;
block|}
name|Assert
operator|.
name|assertNotNull
argument_list|(
name|result
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
name|result
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|result
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
return|;
block|}
comment|/**      * Reads a bibtex database from the given InputStream. The list is compared with the given list.      *      * @param expectedInputStream the inputStream reading the entry from      * @param actualEntries a list containing a single entry to compare with      */
DECL|method|assertEquals (InputStream expectedInputStream, List<BibEntry> actualEntries)
specifier|public
specifier|static
name|void
name|assertEquals
parameter_list|(
name|InputStream
name|expectedInputStream
parameter_list|,
name|List
argument_list|<
name|BibEntry
argument_list|>
name|actualEntries
parameter_list|)
throws|throws
name|IOException
block|{
name|Assert
operator|.
name|assertNotNull
argument_list|(
name|expectedInputStream
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertNotNull
argument_list|(
name|actualEntries
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|getListFromInputStream
argument_list|(
name|expectedInputStream
argument_list|)
argument_list|,
name|actualEntries
argument_list|)
expr_stmt|;
block|}
DECL|method|assertEquals (List<BibEntry> expectedEntries, InputStream actualInputStream)
specifier|public
specifier|static
name|void
name|assertEquals
parameter_list|(
name|List
argument_list|<
name|BibEntry
argument_list|>
name|expectedEntries
parameter_list|,
name|InputStream
name|actualInputStream
parameter_list|)
throws|throws
name|IOException
block|{
name|Assert
operator|.
name|assertNotNull
argument_list|(
name|actualInputStream
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertNotNull
argument_list|(
name|expectedEntries
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|expectedEntries
argument_list|,
name|getListFromInputStream
argument_list|(
name|actualInputStream
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * Reads a bibtex database from the given InputStream. The result has to contain a single BibEntry. This entry is      * compared to the given entry      *      * @param expected the inputStream reading the entry from      * @param actual the entry to compare with      */
DECL|method|assertEquals (InputStream expected, BibEntry actual)
specifier|public
specifier|static
name|void
name|assertEquals
parameter_list|(
name|InputStream
name|expected
parameter_list|,
name|BibEntry
name|actual
parameter_list|)
throws|throws
name|IOException
block|{
name|assertEquals
argument_list|(
name|expected
argument_list|,
name|Collections
operator|.
name|singletonList
argument_list|(
name|actual
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * Compares two InputStreams. For each InputStream a list will be created. expectedIs is read directly, actualIs is filtered through importer to convert to a list of BibEntries.      * @param expectedIs A BibtexImporter InputStream.      * @param fileToImport The path to the file to be imported.      * @param importer The fileformat you want to use to read the passed file to get the list of expected BibEntries      * @throws IOException      */
DECL|method|assertEquals (InputStream expectedIs, Path fileToImport, Importer importer)
specifier|public
specifier|static
name|void
name|assertEquals
parameter_list|(
name|InputStream
name|expectedIs
parameter_list|,
name|Path
name|fileToImport
parameter_list|,
name|Importer
name|importer
parameter_list|)
throws|throws
name|IOException
block|{
name|assertEquals
argument_list|(
name|getListFromInputStream
argument_list|(
name|expectedIs
argument_list|)
argument_list|,
name|fileToImport
argument_list|,
name|importer
argument_list|)
expr_stmt|;
block|}
DECL|method|assertEquals (InputStream expectedIs, URL fileToImport, Importer importer)
specifier|public
specifier|static
name|void
name|assertEquals
parameter_list|(
name|InputStream
name|expectedIs
parameter_list|,
name|URL
name|fileToImport
parameter_list|,
name|Importer
name|importer
parameter_list|)
throws|throws
name|URISyntaxException
throws|,
name|IOException
block|{
name|assertEquals
argument_list|(
name|expectedIs
argument_list|,
name|Paths
operator|.
name|get
argument_list|(
name|fileToImport
operator|.
name|toURI
argument_list|()
argument_list|)
argument_list|,
name|importer
argument_list|)
expr_stmt|;
block|}
comment|/**      * Compares a list of BibEntries to an InputStream. actualIs is filtered through importerForActualIs to convert to a list of BibEntries.      * @param expected A BibtexImporter InputStream.      * @param fileToImport The path to the file to be imported.      * @param importer The fileformat you want to use to read the passed file to get the list of expected BibEntries      * @throws IOException      */
DECL|method|assertEquals (List<BibEntry> expected, Path fileToImport, Importer importer)
specifier|public
specifier|static
name|void
name|assertEquals
parameter_list|(
name|List
argument_list|<
name|BibEntry
argument_list|>
name|expected
parameter_list|,
name|Path
name|fileToImport
parameter_list|,
name|Importer
name|importer
parameter_list|)
throws|throws
name|IOException
block|{
name|List
argument_list|<
name|BibEntry
argument_list|>
name|actualEntries
init|=
name|importer
operator|.
name|importDatabase
argument_list|(
name|fileToImport
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
name|Assert
operator|.
name|assertEquals
argument_list|(
name|expected
argument_list|,
name|actualEntries
argument_list|)
expr_stmt|;
block|}
DECL|method|assertEquals (List<BibEntry> expected, URL fileToImport, Importer importer)
specifier|public
specifier|static
name|void
name|assertEquals
parameter_list|(
name|List
argument_list|<
name|BibEntry
argument_list|>
name|expected
parameter_list|,
name|URL
name|fileToImport
parameter_list|,
name|Importer
name|importer
parameter_list|)
throws|throws
name|URISyntaxException
throws|,
name|IOException
block|{
name|assertEquals
argument_list|(
name|expected
argument_list|,
name|Paths
operator|.
name|get
argument_list|(
name|fileToImport
operator|.
name|toURI
argument_list|()
argument_list|)
argument_list|,
name|importer
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

