begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.importer
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|importer
package|;
end_package

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
name|model
operator|.
name|database
operator|.
name|BibDatabase
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
name|BeforeClass
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
name|java
operator|.
name|io
operator|.
name|File
import|;
end_import

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
name|Collection
import|;
end_import

begin_class
DECL|class|OpenDatabaseActionTest
specifier|public
class|class
name|OpenDatabaseActionTest
block|{
DECL|field|defaultEncoding
specifier|private
specifier|final
name|Charset
name|defaultEncoding
init|=
name|StandardCharsets
operator|.
name|UTF_8
decl_stmt|;
DECL|field|bibNoHeader
specifier|private
specifier|final
name|File
name|bibNoHeader
init|=
operator|new
name|File
argument_list|(
name|OpenDatabaseActionTest
operator|.
name|class
operator|.
name|getResource
argument_list|(
literal|"headerless.bib"
argument_list|)
operator|.
name|getFile
argument_list|()
argument_list|)
decl_stmt|;
DECL|field|bibWrongHeader
specifier|private
specifier|final
name|File
name|bibWrongHeader
init|=
operator|new
name|File
argument_list|(
name|OpenDatabaseActionTest
operator|.
name|class
operator|.
name|getResource
argument_list|(
literal|"wrong-header.bib"
argument_list|)
operator|.
name|getFile
argument_list|()
argument_list|)
decl_stmt|;
DECL|field|bibHeader
specifier|private
specifier|final
name|File
name|bibHeader
init|=
operator|new
name|File
argument_list|(
name|OpenDatabaseActionTest
operator|.
name|class
operator|.
name|getResource
argument_list|(
literal|"encoding-header.bib"
argument_list|)
operator|.
name|getFile
argument_list|()
argument_list|)
decl_stmt|;
DECL|field|bibHeaderAndSignature
specifier|private
specifier|final
name|File
name|bibHeaderAndSignature
init|=
operator|new
name|File
argument_list|(
name|OpenDatabaseActionTest
operator|.
name|class
operator|.
name|getResource
argument_list|(
literal|"jabref-header.bib"
argument_list|)
operator|.
name|getFile
argument_list|()
argument_list|)
decl_stmt|;
DECL|field|bibEncodingWithoutNewline
specifier|private
specifier|final
name|File
name|bibEncodingWithoutNewline
init|=
operator|new
name|File
argument_list|(
name|OpenDatabaseActionTest
operator|.
name|class
operator|.
name|getResource
argument_list|(
literal|"encodingWithoutNewline.bib"
argument_list|)
operator|.
name|getFile
argument_list|()
argument_list|)
decl_stmt|;
annotation|@
name|BeforeClass
DECL|method|setUpGlobalsPrefs ()
specifier|public
specifier|static
name|void
name|setUpGlobalsPrefs
parameter_list|()
block|{
comment|// otherwise FieldContentParser (called by BibtexParser) crashes
name|Globals
operator|.
name|prefs
operator|=
name|JabRefPreferences
operator|.
name|getInstance
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|useFallbackEncodingIfNoHeader ()
specifier|public
name|void
name|useFallbackEncodingIfNoHeader
parameter_list|()
throws|throws
name|IOException
block|{
name|ParserResult
name|result
init|=
name|OpenDatabaseAction
operator|.
name|loadDatabase
argument_list|(
name|bibNoHeader
argument_list|,
name|defaultEncoding
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|defaultEncoding
argument_list|,
name|result
operator|.
name|getEncoding
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|useFallbackEncodingIfUnknownHeader ()
specifier|public
name|void
name|useFallbackEncodingIfUnknownHeader
parameter_list|()
throws|throws
name|IOException
block|{
name|ParserResult
name|result
init|=
name|OpenDatabaseAction
operator|.
name|loadDatabase
argument_list|(
name|bibWrongHeader
argument_list|,
name|defaultEncoding
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|defaultEncoding
argument_list|,
name|result
operator|.
name|getEncoding
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|useSpecifiedEncoding ()
specifier|public
name|void
name|useSpecifiedEncoding
parameter_list|()
throws|throws
name|IOException
block|{
name|ParserResult
name|result
init|=
name|OpenDatabaseAction
operator|.
name|loadDatabase
argument_list|(
name|bibHeader
argument_list|,
name|StandardCharsets
operator|.
name|US_ASCII
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|StandardCharsets
operator|.
name|UTF_8
argument_list|,
name|result
operator|.
name|getEncoding
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|useSpecifiedEncodingWithSignature ()
specifier|public
name|void
name|useSpecifiedEncodingWithSignature
parameter_list|()
throws|throws
name|IOException
block|{
name|ParserResult
name|result
init|=
name|OpenDatabaseAction
operator|.
name|loadDatabase
argument_list|(
name|bibHeaderAndSignature
argument_list|,
name|StandardCharsets
operator|.
name|US_ASCII
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|StandardCharsets
operator|.
name|UTF_8
argument_list|,
name|result
operator|.
name|getEncoding
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|entriesAreParsedNoHeader ()
specifier|public
name|void
name|entriesAreParsedNoHeader
parameter_list|()
throws|throws
name|IOException
block|{
name|ParserResult
name|result
init|=
name|OpenDatabaseAction
operator|.
name|loadDatabase
argument_list|(
name|bibNoHeader
argument_list|,
name|defaultEncoding
argument_list|)
decl_stmt|;
name|BibDatabase
name|db
init|=
name|result
operator|.
name|getDatabase
argument_list|()
decl_stmt|;
comment|// Entry
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|db
operator|.
name|getEntryCount
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"2014"
argument_list|,
name|db
operator|.
name|getEntryByKey
argument_list|(
literal|"1"
argument_list|)
operator|.
name|getField
argument_list|(
literal|"year"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|entriesAreParsedHeader ()
specifier|public
name|void
name|entriesAreParsedHeader
parameter_list|()
throws|throws
name|IOException
block|{
name|ParserResult
name|result
init|=
name|OpenDatabaseAction
operator|.
name|loadDatabase
argument_list|(
name|bibHeader
argument_list|,
name|defaultEncoding
argument_list|)
decl_stmt|;
name|BibDatabase
name|db
init|=
name|result
operator|.
name|getDatabase
argument_list|()
decl_stmt|;
comment|// Entry
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|db
operator|.
name|getEntryCount
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"2014"
argument_list|,
name|db
operator|.
name|getEntryByKey
argument_list|(
literal|"1"
argument_list|)
operator|.
name|getField
argument_list|(
literal|"year"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|entriesAreParsedHeaderAndSignature ()
specifier|public
name|void
name|entriesAreParsedHeaderAndSignature
parameter_list|()
throws|throws
name|IOException
block|{
name|ParserResult
name|result
init|=
name|OpenDatabaseAction
operator|.
name|loadDatabase
argument_list|(
name|bibHeaderAndSignature
argument_list|,
name|defaultEncoding
argument_list|)
decl_stmt|;
name|BibDatabase
name|db
init|=
name|result
operator|.
name|getDatabase
argument_list|()
decl_stmt|;
comment|// Entry
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|db
operator|.
name|getEntryCount
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"2014"
argument_list|,
name|db
operator|.
name|getEntryByKey
argument_list|(
literal|"1"
argument_list|)
operator|.
name|getField
argument_list|(
literal|"year"
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * Test for #669      */
annotation|@
name|Test
DECL|method|correctlyParseEncodingWithoutNewline ()
specifier|public
name|void
name|correctlyParseEncodingWithoutNewline
parameter_list|()
throws|throws
name|IOException
block|{
name|ParserResult
name|result
init|=
name|OpenDatabaseAction
operator|.
name|loadDatabase
argument_list|(
name|bibEncodingWithoutNewline
argument_list|,
name|defaultEncoding
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|StandardCharsets
operator|.
name|US_ASCII
argument_list|,
name|result
operator|.
name|getEncoding
argument_list|()
argument_list|)
expr_stmt|;
name|BibDatabase
name|db
init|=
name|result
operator|.
name|getDatabase
argument_list|()
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"testPreamble"
argument_list|,
name|db
operator|.
name|getPreamble
argument_list|()
argument_list|)
expr_stmt|;
name|Collection
argument_list|<
name|BibEntry
argument_list|>
name|entries
init|=
name|db
operator|.
name|getEntries
argument_list|()
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|entries
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|BibEntry
name|entry
init|=
name|entries
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"testArticle"
argument_list|,
name|entry
operator|.
name|getCiteKey
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

