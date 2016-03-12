begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.bibtex
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
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
name|io
operator|.
name|UnsupportedEncodingException
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
name|net
operator|.
name|sf
operator|.
name|jabref
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
name|model
operator|.
name|entry
operator|.
name|CanonicalBibtexEntry
import|;
end_import

begin_class
DECL|class|BibtexEntryAssert
specifier|public
class|class
name|BibtexEntryAssert
block|{
comment|/**      * Reads a single entry from the resource using `getResourceAsStream` from the given class. The resource has to      * contain a single entry      *      * @param clazz the class where to call `getResourceAsStream`      * @param resourceName the resource to read      * @param entry the entry to compare with      */
DECL|method|assertEquals (Class<? extends Object> clazz, String resourceName, BibEntry entry)
specifier|public
specifier|static
name|void
name|assertEquals
parameter_list|(
name|Class
argument_list|<
name|?
extends|extends
name|Object
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
name|BibtexEntryAssert
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
comment|/**      * Reads a bibtex database from the given InputStream. The result has to contain a single BibEntry. This entry is      * compared to the given entry      *      * @param shouldBeIs the inputStream reading the entry from      * @param entry the entry to compare with      */
DECL|method|assertEquals (InputStream shouldBeIs, BibEntry entry)
specifier|public
specifier|static
name|void
name|assertEquals
parameter_list|(
name|InputStream
name|shouldBeIs
parameter_list|,
name|BibEntry
name|entry
parameter_list|)
throws|throws
name|UnsupportedEncodingException
throws|,
name|IOException
block|{
name|Assert
operator|.
name|assertNotNull
argument_list|(
name|shouldBeIs
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertNotNull
argument_list|(
name|entry
argument_list|)
expr_stmt|;
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
name|shouldBeIs
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
name|reader
argument_list|)
decl_stmt|;
name|result
operator|=
name|parser
operator|.
name|parse
argument_list|()
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
name|assertNotEquals
argument_list|(
name|ParserResult
operator|.
name|INVALID_FORMAT
argument_list|,
name|result
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|result
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntryCount
argument_list|()
argument_list|)
expr_stmt|;
name|BibEntry
name|shouldBeEntry
init|=
name|result
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
name|shouldBeEntry
argument_list|,
name|entry
argument_list|)
expr_stmt|;
block|}
comment|/**      * Compares to lists of bibtex entries      *      * @param shouldBeIs the list with the expected entries      * @param actualEntries the list with the actual entries      */
DECL|method|assertEquals (List<BibEntry> shouldBeIs, List<BibEntry> actualEntries)
specifier|public
specifier|static
name|void
name|assertEquals
parameter_list|(
name|List
argument_list|<
name|BibEntry
argument_list|>
name|shouldBeIs
parameter_list|,
name|List
argument_list|<
name|BibEntry
argument_list|>
name|actualEntries
parameter_list|)
block|{
name|Assert
operator|.
name|assertNotNull
argument_list|(
name|shouldBeIs
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
name|shouldBeIs
operator|.
name|size
argument_list|()
argument_list|,
name|actualEntries
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|actualEntries
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|assertEquals
argument_list|(
name|shouldBeIs
operator|.
name|get
argument_list|(
name|i
argument_list|)
argument_list|,
name|actualEntries
operator|.
name|get
argument_list|(
name|i
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Compares to BibTeX entries using their canonical representation      */
DECL|method|assertEquals (BibEntry shouldBeEntry, BibEntry entry)
specifier|public
specifier|static
name|void
name|assertEquals
parameter_list|(
name|BibEntry
name|shouldBeEntry
parameter_list|,
name|BibEntry
name|entry
parameter_list|)
block|{
comment|// use the canonical string representation to compare the entries
name|String
name|shouldBeEntryRepresentation
init|=
name|CanonicalBibtexEntry
operator|.
name|getCanonicalRepresentation
argument_list|(
name|shouldBeEntry
argument_list|)
decl_stmt|;
name|String
name|entryRepresentation
init|=
name|CanonicalBibtexEntry
operator|.
name|getCanonicalRepresentation
argument_list|(
name|entry
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|shouldBeEntryRepresentation
argument_list|,
name|entryRepresentation
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

