begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.auxparser
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|auxparser
package|;
end_package

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
name|Paths
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Optional
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
name|ImportFormatPreferences
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
name|After
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

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|Assert
operator|.
name|assertTrue
import|;
end_import

begin_class
DECL|class|AuxParserTest
specifier|public
class|class
name|AuxParserTest
block|{
DECL|field|importFormatPreferences
specifier|private
name|ImportFormatPreferences
name|importFormatPreferences
decl_stmt|;
annotation|@
name|Before
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
block|{
name|importFormatPreferences
operator|=
name|JabRefPreferences
operator|.
name|getInstance
argument_list|()
operator|.
name|getImportFormatPreferences
argument_list|()
expr_stmt|;
block|}
annotation|@
name|After
DECL|method|tearDown ()
specifier|public
name|void
name|tearDown
parameter_list|()
block|{
name|importFormatPreferences
operator|=
literal|null
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testNormal ()
specifier|public
name|void
name|testNormal
parameter_list|()
throws|throws
name|URISyntaxException
throws|,
name|IOException
block|{
name|InputStream
name|originalStream
init|=
name|AuxParserTest
operator|.
name|class
operator|.
name|getResourceAsStream
argument_list|(
literal|"origin.bib"
argument_list|)
decl_stmt|;
name|File
name|auxFile
init|=
name|Paths
operator|.
name|get
argument_list|(
name|AuxParserTest
operator|.
name|class
operator|.
name|getResource
argument_list|(
literal|"paper.aux"
argument_list|)
operator|.
name|toURI
argument_list|()
argument_list|)
operator|.
name|toFile
argument_list|()
decl_stmt|;
try|try
init|(
name|InputStreamReader
name|originalReader
init|=
operator|new
name|InputStreamReader
argument_list|(
name|originalStream
argument_list|,
name|StandardCharsets
operator|.
name|UTF_8
argument_list|)
init|)
block|{
name|ParserResult
name|result
init|=
name|BibtexParser
operator|.
name|parse
argument_list|(
name|originalReader
argument_list|,
name|importFormatPreferences
argument_list|)
decl_stmt|;
name|AuxParser
name|auxParser
init|=
operator|new
name|AuxParser
argument_list|(
name|auxFile
operator|.
name|getAbsolutePath
argument_list|()
argument_list|,
name|result
operator|.
name|getDatabase
argument_list|()
argument_list|)
decl_stmt|;
name|AuxParserResult
name|auxResult
init|=
name|auxParser
operator|.
name|parse
argument_list|()
decl_stmt|;
name|assertTrue
argument_list|(
name|auxResult
operator|.
name|getGeneratedBibDatabase
argument_list|()
operator|.
name|hasEntries
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|0
argument_list|,
name|auxResult
operator|.
name|getUnresolvedKeysCount
argument_list|()
argument_list|)
expr_stmt|;
name|BibDatabase
name|newDB
init|=
name|auxResult
operator|.
name|getGeneratedBibDatabase
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
literal|2
argument_list|,
name|newDB
operator|.
name|getEntries
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|2
argument_list|,
name|auxResult
operator|.
name|getResolvedKeysCount
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|2
argument_list|,
name|auxResult
operator|.
name|getFoundKeysInAux
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|auxResult
operator|.
name|getFoundKeysInAux
argument_list|()
operator|+
name|auxResult
operator|.
name|getCrossRefEntriesCount
argument_list|()
argument_list|,
name|auxResult
operator|.
name|getResolvedKeysCount
argument_list|()
operator|+
name|auxResult
operator|.
name|getUnresolvedKeysCount
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|0
argument_list|,
name|auxResult
operator|.
name|getCrossRefEntriesCount
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Test
DECL|method|testNotAllFound ()
specifier|public
name|void
name|testNotAllFound
parameter_list|()
throws|throws
name|URISyntaxException
throws|,
name|IOException
block|{
name|InputStream
name|originalStream
init|=
name|AuxParserTest
operator|.
name|class
operator|.
name|getResourceAsStream
argument_list|(
literal|"origin.bib"
argument_list|)
decl_stmt|;
name|File
name|auxFile
init|=
name|Paths
operator|.
name|get
argument_list|(
name|AuxParserTest
operator|.
name|class
operator|.
name|getResource
argument_list|(
literal|"badpaper.aux"
argument_list|)
operator|.
name|toURI
argument_list|()
argument_list|)
operator|.
name|toFile
argument_list|()
decl_stmt|;
try|try
init|(
name|InputStreamReader
name|originalReader
init|=
operator|new
name|InputStreamReader
argument_list|(
name|originalStream
argument_list|,
name|StandardCharsets
operator|.
name|UTF_8
argument_list|)
init|)
block|{
name|ParserResult
name|result
init|=
name|BibtexParser
operator|.
name|parse
argument_list|(
name|originalReader
argument_list|,
name|importFormatPreferences
argument_list|)
decl_stmt|;
name|AuxParser
name|auxParser
init|=
operator|new
name|AuxParser
argument_list|(
name|auxFile
operator|.
name|getAbsolutePath
argument_list|()
argument_list|,
name|result
operator|.
name|getDatabase
argument_list|()
argument_list|)
decl_stmt|;
name|AuxParserResult
name|auxResult
init|=
name|auxParser
operator|.
name|parse
argument_list|()
decl_stmt|;
name|assertTrue
argument_list|(
name|auxResult
operator|.
name|getGeneratedBibDatabase
argument_list|()
operator|.
name|hasEntries
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|auxResult
operator|.
name|getUnresolvedKeysCount
argument_list|()
argument_list|)
expr_stmt|;
name|BibDatabase
name|newDB
init|=
name|auxResult
operator|.
name|getGeneratedBibDatabase
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
literal|2
argument_list|,
name|newDB
operator|.
name|getEntries
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|2
argument_list|,
name|auxResult
operator|.
name|getResolvedKeysCount
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|3
argument_list|,
name|auxResult
operator|.
name|getFoundKeysInAux
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|auxResult
operator|.
name|getFoundKeysInAux
argument_list|()
operator|+
name|auxResult
operator|.
name|getCrossRefEntriesCount
argument_list|()
argument_list|,
name|auxResult
operator|.
name|getResolvedKeysCount
argument_list|()
operator|+
name|auxResult
operator|.
name|getUnresolvedKeysCount
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|0
argument_list|,
name|auxResult
operator|.
name|getCrossRefEntriesCount
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Test
DECL|method|duplicateBibDatabaseConfiguration ()
specifier|public
name|void
name|duplicateBibDatabaseConfiguration
parameter_list|()
throws|throws
name|URISyntaxException
throws|,
name|IOException
block|{
name|InputStream
name|originalStream
init|=
name|AuxParserTest
operator|.
name|class
operator|.
name|getResourceAsStream
argument_list|(
literal|"config.bib"
argument_list|)
decl_stmt|;
name|File
name|auxFile
init|=
name|Paths
operator|.
name|get
argument_list|(
name|AuxParserTest
operator|.
name|class
operator|.
name|getResource
argument_list|(
literal|"paper.aux"
argument_list|)
operator|.
name|toURI
argument_list|()
argument_list|)
operator|.
name|toFile
argument_list|()
decl_stmt|;
try|try
init|(
name|InputStreamReader
name|originalReader
init|=
operator|new
name|InputStreamReader
argument_list|(
name|originalStream
argument_list|,
name|StandardCharsets
operator|.
name|UTF_8
argument_list|)
init|)
block|{
name|ParserResult
name|result
init|=
name|BibtexParser
operator|.
name|parse
argument_list|(
name|originalReader
argument_list|,
name|importFormatPreferences
argument_list|)
decl_stmt|;
name|AuxParser
name|auxParser
init|=
operator|new
name|AuxParser
argument_list|(
name|auxFile
operator|.
name|getAbsolutePath
argument_list|()
argument_list|,
name|result
operator|.
name|getDatabase
argument_list|()
argument_list|)
decl_stmt|;
name|AuxParserResult
name|auxResult
init|=
name|auxParser
operator|.
name|parse
argument_list|()
decl_stmt|;
name|BibDatabase
name|db
init|=
name|auxResult
operator|.
name|getGeneratedBibDatabase
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"\"Maintained by \" # maintainer"
argument_list|)
argument_list|,
name|db
operator|.
name|getPreamble
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|db
operator|.
name|getStringCount
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Test
DECL|method|testNestedAux ()
specifier|public
name|void
name|testNestedAux
parameter_list|()
throws|throws
name|URISyntaxException
throws|,
name|IOException
block|{
name|InputStream
name|originalStream
init|=
name|AuxParserTest
operator|.
name|class
operator|.
name|getResourceAsStream
argument_list|(
literal|"origin.bib"
argument_list|)
decl_stmt|;
name|File
name|auxFile
init|=
name|Paths
operator|.
name|get
argument_list|(
name|AuxParserTest
operator|.
name|class
operator|.
name|getResource
argument_list|(
literal|"nested.aux"
argument_list|)
operator|.
name|toURI
argument_list|()
argument_list|)
operator|.
name|toFile
argument_list|()
decl_stmt|;
try|try
init|(
name|InputStreamReader
name|originalReader
init|=
operator|new
name|InputStreamReader
argument_list|(
name|originalStream
argument_list|,
name|StandardCharsets
operator|.
name|UTF_8
argument_list|)
init|)
block|{
name|ParserResult
name|result
init|=
name|BibtexParser
operator|.
name|parse
argument_list|(
name|originalReader
argument_list|,
name|importFormatPreferences
argument_list|)
decl_stmt|;
name|AuxParser
name|auxParser
init|=
operator|new
name|AuxParser
argument_list|(
name|auxFile
operator|.
name|getAbsolutePath
argument_list|()
argument_list|,
name|result
operator|.
name|getDatabase
argument_list|()
argument_list|)
decl_stmt|;
name|AuxParserResult
name|auxResult
init|=
name|auxParser
operator|.
name|parse
argument_list|()
decl_stmt|;
name|assertTrue
argument_list|(
name|auxResult
operator|.
name|getGeneratedBibDatabase
argument_list|()
operator|.
name|hasEntries
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|0
argument_list|,
name|auxResult
operator|.
name|getUnresolvedKeysCount
argument_list|()
argument_list|)
expr_stmt|;
name|BibDatabase
name|newDB
init|=
name|auxResult
operator|.
name|getGeneratedBibDatabase
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
literal|2
argument_list|,
name|newDB
operator|.
name|getEntries
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|2
argument_list|,
name|auxResult
operator|.
name|getResolvedKeysCount
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|2
argument_list|,
name|auxResult
operator|.
name|getFoundKeysInAux
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|auxResult
operator|.
name|getFoundKeysInAux
argument_list|()
operator|+
name|auxResult
operator|.
name|getCrossRefEntriesCount
argument_list|()
argument_list|,
name|auxResult
operator|.
name|getResolvedKeysCount
argument_list|()
operator|+
name|auxResult
operator|.
name|getUnresolvedKeysCount
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|0
argument_list|,
name|auxResult
operator|.
name|getCrossRefEntriesCount
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Test
DECL|method|testCrossRef ()
specifier|public
name|void
name|testCrossRef
parameter_list|()
throws|throws
name|URISyntaxException
throws|,
name|IOException
block|{
name|InputStream
name|originalStream
init|=
name|AuxParserTest
operator|.
name|class
operator|.
name|getResourceAsStream
argument_list|(
literal|"origin.bib"
argument_list|)
decl_stmt|;
name|File
name|auxFile
init|=
name|Paths
operator|.
name|get
argument_list|(
name|AuxParserTest
operator|.
name|class
operator|.
name|getResource
argument_list|(
literal|"crossref.aux"
argument_list|)
operator|.
name|toURI
argument_list|()
argument_list|)
operator|.
name|toFile
argument_list|()
decl_stmt|;
try|try
init|(
name|InputStreamReader
name|originalReader
init|=
operator|new
name|InputStreamReader
argument_list|(
name|originalStream
argument_list|,
name|StandardCharsets
operator|.
name|UTF_8
argument_list|)
init|)
block|{
name|ParserResult
name|result
init|=
name|BibtexParser
operator|.
name|parse
argument_list|(
name|originalReader
argument_list|,
name|importFormatPreferences
argument_list|)
decl_stmt|;
name|AuxParser
name|auxParser
init|=
operator|new
name|AuxParser
argument_list|(
name|auxFile
operator|.
name|getAbsolutePath
argument_list|()
argument_list|,
name|result
operator|.
name|getDatabase
argument_list|()
argument_list|)
decl_stmt|;
name|AuxParserResult
name|auxResult
init|=
name|auxParser
operator|.
name|parse
argument_list|()
decl_stmt|;
name|assertTrue
argument_list|(
name|auxResult
operator|.
name|getGeneratedBibDatabase
argument_list|()
operator|.
name|hasEntries
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|2
argument_list|,
name|auxResult
operator|.
name|getUnresolvedKeysCount
argument_list|()
argument_list|)
expr_stmt|;
name|BibDatabase
name|newDB
init|=
name|auxResult
operator|.
name|getGeneratedBibDatabase
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
literal|4
argument_list|,
name|newDB
operator|.
name|getEntries
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|3
argument_list|,
name|auxResult
operator|.
name|getResolvedKeysCount
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|4
argument_list|,
name|auxResult
operator|.
name|getFoundKeysInAux
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|auxResult
operator|.
name|getFoundKeysInAux
argument_list|()
operator|+
name|auxResult
operator|.
name|getCrossRefEntriesCount
argument_list|()
argument_list|,
name|auxResult
operator|.
name|getResolvedKeysCount
argument_list|()
operator|+
name|auxResult
operator|.
name|getUnresolvedKeysCount
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|auxResult
operator|.
name|getCrossRefEntriesCount
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Test
DECL|method|testFileNotFound ()
specifier|public
name|void
name|testFileNotFound
parameter_list|()
throws|throws
name|URISyntaxException
throws|,
name|IOException
block|{
name|AuxParser
name|auxParser
init|=
operator|new
name|AuxParser
argument_list|(
literal|"unknownfile.bib"
argument_list|,
operator|new
name|BibDatabase
argument_list|()
argument_list|)
decl_stmt|;
name|AuxParserResult
name|auxResult
init|=
name|auxParser
operator|.
name|parse
argument_list|()
decl_stmt|;
name|assertFalse
argument_list|(
name|auxResult
operator|.
name|getGeneratedBibDatabase
argument_list|()
operator|.
name|hasEntries
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|0
argument_list|,
name|auxResult
operator|.
name|getUnresolvedKeysCount
argument_list|()
argument_list|)
expr_stmt|;
name|BibDatabase
name|newDB
init|=
name|auxResult
operator|.
name|getGeneratedBibDatabase
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
literal|0
argument_list|,
name|newDB
operator|.
name|getEntries
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|0
argument_list|,
name|auxResult
operator|.
name|getResolvedKeysCount
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|0
argument_list|,
name|auxResult
operator|.
name|getFoundKeysInAux
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|auxResult
operator|.
name|getFoundKeysInAux
argument_list|()
operator|+
name|auxResult
operator|.
name|getCrossRefEntriesCount
argument_list|()
argument_list|,
name|auxResult
operator|.
name|getResolvedKeysCount
argument_list|()
operator|+
name|auxResult
operator|.
name|getUnresolvedKeysCount
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|0
argument_list|,
name|auxResult
operator|.
name|getCrossRefEntriesCount
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

