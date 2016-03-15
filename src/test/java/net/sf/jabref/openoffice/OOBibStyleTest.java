begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.openoffice
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|openoffice
package|;
end_package

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|Assert
operator|.
name|*
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
name|Arrays
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Set
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
name|JabRef
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
name|importer
operator|.
name|ImportFormatReader
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
name|logic
operator|.
name|journals
operator|.
name|JournalAbbreviationRepository
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
name|layout
operator|.
name|Layout
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
DECL|class|OOBibStyleTest
specifier|public
class|class
name|OOBibStyleTest
block|{
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
name|After
DECL|method|tearDown ()
specifier|public
name|void
name|tearDown
parameter_list|()
throws|throws
name|Exception
block|{
name|Globals
operator|.
name|prefs
operator|=
literal|null
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testAuthorYear ()
specifier|public
name|void
name|testAuthorYear
parameter_list|()
block|{
try|try
block|{
name|URL
name|defPath
init|=
name|JabRef
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|OpenOfficePanel
operator|.
name|DEFAULT_AUTHORYEAR_STYLE_PATH
argument_list|)
decl_stmt|;
name|Reader
name|r
init|=
operator|new
name|InputStreamReader
argument_list|(
name|defPath
operator|.
name|openStream
argument_list|()
argument_list|)
decl_stmt|;
name|OOBibStyle
name|style
init|=
operator|new
name|OOBibStyle
argument_list|(
name|r
argument_list|,
name|mock
argument_list|(
name|JournalAbbreviationRepository
operator|.
name|class
argument_list|)
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|style
operator|.
name|isValid
argument_list|()
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|style
operator|.
name|isBibtexKeyCiteMarkers
argument_list|()
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|style
operator|.
name|isBoldCitations
argument_list|()
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|style
operator|.
name|isFormatCitations
argument_list|()
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|style
operator|.
name|isItalicCitations
argument_list|()
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|style
operator|.
name|isNumberEntries
argument_list|()
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|style
operator|.
name|isSortByPosition
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|fail
argument_list|()
expr_stmt|;
block|}
block|}
annotation|@
name|Test
DECL|method|testAuthorYearAsFile ()
specifier|public
name|void
name|testAuthorYearAsFile
parameter_list|()
block|{
try|try
block|{
name|URL
name|defPath
init|=
name|JabRef
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|OpenOfficePanel
operator|.
name|DEFAULT_AUTHORYEAR_STYLE_PATH
argument_list|)
decl_stmt|;
name|OOBibStyle
name|style
init|=
operator|new
name|OOBibStyle
argument_list|(
operator|new
name|File
argument_list|(
name|defPath
operator|.
name|getFile
argument_list|()
argument_list|)
argument_list|,
name|mock
argument_list|(
name|JournalAbbreviationRepository
operator|.
name|class
argument_list|)
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getDefaultEncoding
argument_list|()
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|style
operator|.
name|isValid
argument_list|()
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|style
operator|.
name|isBibtexKeyCiteMarkers
argument_list|()
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|style
operator|.
name|isBoldCitations
argument_list|()
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|style
operator|.
name|isFormatCitations
argument_list|()
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|style
operator|.
name|isItalicCitations
argument_list|()
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|style
operator|.
name|isNumberEntries
argument_list|()
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|style
operator|.
name|isSortByPosition
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|fail
argument_list|()
expr_stmt|;
block|}
block|}
annotation|@
name|Test
DECL|method|testNumerical ()
specifier|public
name|void
name|testNumerical
parameter_list|()
block|{
try|try
block|{
name|URL
name|defPath
init|=
name|JabRef
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|OpenOfficePanel
operator|.
name|DEFAULT_NUMERICAL_STYLE_PATH
argument_list|)
decl_stmt|;
name|Reader
name|r
init|=
operator|new
name|InputStreamReader
argument_list|(
name|defPath
operator|.
name|openStream
argument_list|()
argument_list|)
decl_stmt|;
name|OOBibStyle
name|style
init|=
operator|new
name|OOBibStyle
argument_list|(
name|r
argument_list|,
name|mock
argument_list|(
name|JournalAbbreviationRepository
operator|.
name|class
argument_list|)
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|style
operator|.
name|isValid
argument_list|()
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|style
operator|.
name|isBibtexKeyCiteMarkers
argument_list|()
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|style
operator|.
name|isBoldCitations
argument_list|()
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|style
operator|.
name|isFormatCitations
argument_list|()
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|style
operator|.
name|isItalicCitations
argument_list|()
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|style
operator|.
name|isNumberEntries
argument_list|()
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|style
operator|.
name|isSortByPosition
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|fail
argument_list|()
expr_stmt|;
block|}
block|}
annotation|@
name|Test
DECL|method|testGetNumCitationMarker ()
specifier|public
name|void
name|testGetNumCitationMarker
parameter_list|()
throws|throws
name|IOException
block|{
name|URL
name|defPath
init|=
name|JabRef
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|OpenOfficePanel
operator|.
name|DEFAULT_NUMERICAL_STYLE_PATH
argument_list|)
decl_stmt|;
name|Reader
name|r
init|=
operator|new
name|InputStreamReader
argument_list|(
name|defPath
operator|.
name|openStream
argument_list|()
argument_list|)
decl_stmt|;
name|OOBibStyle
name|style
init|=
operator|new
name|OOBibStyle
argument_list|(
name|r
argument_list|,
name|mock
argument_list|(
name|JournalAbbreviationRepository
operator|.
name|class
argument_list|)
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|"[1] "
argument_list|,
name|style
operator|.
name|getNumCitationMarker
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
literal|1
argument_list|)
argument_list|,
operator|-
literal|1
argument_list|,
literal|true
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"[1]"
argument_list|,
name|style
operator|.
name|getNumCitationMarker
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
literal|1
argument_list|)
argument_list|,
operator|-
literal|1
argument_list|,
literal|false
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"[1] "
argument_list|,
name|style
operator|.
name|getNumCitationMarker
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
literal|1
argument_list|)
argument_list|,
literal|0
argument_list|,
literal|true
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"[1-3] "
argument_list|,
name|style
operator|.
name|getNumCitationMarker
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
literal|1
argument_list|,
literal|2
argument_list|,
literal|3
argument_list|)
argument_list|,
literal|1
argument_list|,
literal|true
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"[1; 2; 3] "
argument_list|,
name|style
operator|.
name|getNumCitationMarker
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
literal|1
argument_list|,
literal|2
argument_list|,
literal|3
argument_list|)
argument_list|,
literal|5
argument_list|,
literal|true
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"[1; 2; 3] "
argument_list|,
name|style
operator|.
name|getNumCitationMarker
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
literal|1
argument_list|,
literal|2
argument_list|,
literal|3
argument_list|)
argument_list|,
operator|-
literal|1
argument_list|,
literal|true
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"[1; 3; 12] "
argument_list|,
name|style
operator|.
name|getNumCitationMarker
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
literal|1
argument_list|,
literal|12
argument_list|,
literal|3
argument_list|)
argument_list|,
literal|1
argument_list|,
literal|true
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"[3-5; 7; 10-12] "
argument_list|,
name|style
operator|.
name|getNumCitationMarker
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
literal|12
argument_list|,
literal|7
argument_list|,
literal|3
argument_list|,
literal|4
argument_list|,
literal|11
argument_list|,
literal|10
argument_list|,
literal|5
argument_list|)
argument_list|,
literal|1
argument_list|,
literal|true
argument_list|)
argument_list|)
expr_stmt|;
name|String
name|citation
init|=
name|style
operator|.
name|getNumCitationMarker
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
literal|1
argument_list|)
argument_list|,
operator|-
literal|1
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|"[1; pp. 55-56]"
argument_list|,
name|style
operator|.
name|insertPageInfo
argument_list|(
name|citation
argument_list|,
literal|"pp. 55-56"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetCitProperty ()
specifier|public
name|void
name|testGetCitProperty
parameter_list|()
throws|throws
name|IOException
block|{
name|URL
name|defPath
init|=
name|JabRef
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|OpenOfficePanel
operator|.
name|DEFAULT_NUMERICAL_STYLE_PATH
argument_list|)
decl_stmt|;
name|Reader
name|r
init|=
operator|new
name|InputStreamReader
argument_list|(
name|defPath
operator|.
name|openStream
argument_list|()
argument_list|)
decl_stmt|;
name|OOBibStyle
name|style
init|=
operator|new
name|OOBibStyle
argument_list|(
name|r
argument_list|,
name|mock
argument_list|(
name|JournalAbbreviationRepository
operator|.
name|class
argument_list|)
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|", "
argument_list|,
name|style
operator|.
name|getStringCitProperty
argument_list|(
literal|"AuthorSeparator"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|3
argument_list|,
name|style
operator|.
name|getIntCitProperty
argument_list|(
literal|"MaxAuthors"
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|style
operator|.
name|getBooleanCitProperty
argument_list|(
name|OOBibStyle
operator|.
name|MULTI_CITE_CHRONOLOGICAL
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Default"
argument_list|,
name|style
operator|.
name|getCitationCharacterFormat
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Example style file for JabRef-OO connection."
argument_list|,
name|style
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|Set
argument_list|<
name|String
argument_list|>
name|journals
init|=
name|style
operator|.
name|getJournals
argument_list|()
decl_stmt|;
name|assertTrue
argument_list|(
name|journals
operator|.
name|contains
argument_list|(
literal|"Journal name 1"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetCitationMarker ()
specifier|public
name|void
name|testGetCitationMarker
parameter_list|()
throws|throws
name|IOException
block|{
name|File
name|testBibtexFile
init|=
operator|new
name|File
argument_list|(
literal|"src/test/resources/testbib/complex.bib"
argument_list|)
decl_stmt|;
name|Charset
name|encoding
init|=
name|StandardCharsets
operator|.
name|UTF_8
decl_stmt|;
name|ParserResult
name|result
init|=
name|BibtexParser
operator|.
name|parse
argument_list|(
name|ImportFormatReader
operator|.
name|getReader
argument_list|(
name|testBibtexFile
argument_list|,
name|encoding
argument_list|)
argument_list|)
decl_stmt|;
name|URL
name|defPath
init|=
name|JabRef
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|OpenOfficePanel
operator|.
name|DEFAULT_NUMERICAL_STYLE_PATH
argument_list|)
decl_stmt|;
name|Reader
name|r
init|=
operator|new
name|InputStreamReader
argument_list|(
name|defPath
operator|.
name|openStream
argument_list|()
argument_list|)
decl_stmt|;
name|OOBibStyle
name|style
init|=
operator|new
name|OOBibStyle
argument_list|(
name|r
argument_list|,
name|mock
argument_list|(
name|JournalAbbreviationRepository
operator|.
name|class
argument_list|)
argument_list|)
decl_stmt|;
name|Map
argument_list|<
name|BibEntry
argument_list|,
name|BibDatabase
argument_list|>
name|entryDBMap
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
name|BibDatabase
name|db
init|=
name|result
operator|.
name|getDatabase
argument_list|()
decl_stmt|;
for|for
control|(
name|BibEntry
name|entry
range|:
name|db
operator|.
name|getEntries
argument_list|()
control|)
block|{
name|entryDBMap
operator|.
name|put
argument_list|(
name|entry
argument_list|,
name|db
argument_list|)
expr_stmt|;
block|}
name|BibEntry
name|entry
init|=
name|db
operator|.
name|getEntryByKey
argument_list|(
literal|"1137631"
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|"[BostrÃ¶m et al., 2006]"
argument_list|,
name|style
operator|.
name|getCitationMarker
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|entry
argument_list|)
argument_list|,
name|entryDBMap
argument_list|,
literal|true
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"BostrÃ¶m et al. [2006]"
argument_list|,
name|style
operator|.
name|getCitationMarker
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|entry
argument_list|)
argument_list|,
name|entryDBMap
argument_list|,
literal|false
argument_list|,
literal|null
argument_list|,
operator|new
name|int
index|[]
block|{
literal|3
block|}
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"[BostrÃ¶m, WÃ¤yrynen, BodÃ©n, Beznosov& Kruchten, 2006]"
argument_list|,
name|style
operator|.
name|getCitationMarker
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|entry
argument_list|)
argument_list|,
name|entryDBMap
argument_list|,
literal|true
argument_list|,
literal|null
argument_list|,
operator|new
name|int
index|[]
block|{
literal|5
block|}
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testLayout ()
specifier|public
name|void
name|testLayout
parameter_list|()
throws|throws
name|IOException
block|{
name|File
name|testBibtexFile
init|=
operator|new
name|File
argument_list|(
literal|"src/test/resources/testbib/complex.bib"
argument_list|)
decl_stmt|;
name|Charset
name|encoding
init|=
name|StandardCharsets
operator|.
name|UTF_8
decl_stmt|;
name|ParserResult
name|result
init|=
name|BibtexParser
operator|.
name|parse
argument_list|(
name|ImportFormatReader
operator|.
name|getReader
argument_list|(
name|testBibtexFile
argument_list|,
name|encoding
argument_list|)
argument_list|)
decl_stmt|;
name|URL
name|defPath
init|=
name|JabRef
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|OpenOfficePanel
operator|.
name|DEFAULT_NUMERICAL_STYLE_PATH
argument_list|)
decl_stmt|;
name|Reader
name|r
init|=
operator|new
name|InputStreamReader
argument_list|(
name|defPath
operator|.
name|openStream
argument_list|()
argument_list|)
decl_stmt|;
name|OOBibStyle
name|style
init|=
operator|new
name|OOBibStyle
argument_list|(
name|r
argument_list|,
name|mock
argument_list|(
name|JournalAbbreviationRepository
operator|.
name|class
argument_list|)
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
name|Layout
name|l
init|=
name|style
operator|.
name|getReferenceFormat
argument_list|(
literal|"default"
argument_list|)
decl_stmt|;
name|l
operator|.
name|setPostFormatter
argument_list|(
operator|new
name|OOPreFormatter
argument_list|()
argument_list|)
expr_stmt|;
name|BibEntry
name|entry
init|=
name|db
operator|.
name|getEntryByKey
argument_list|(
literal|"1137631"
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|"BostrÃ¶m, G.; WÃ¤yrynen, J.; BodÃ©n, M.; Beznosov, K. and Kruchten, P. (<b>2006</b>).<i>Extending XP practices to support security requirements engineering</i>,   : 11-18."
argument_list|,
name|l
operator|.
name|doLayout
argument_list|(
name|entry
argument_list|,
name|db
argument_list|)
argument_list|)
expr_stmt|;
name|l
operator|=
name|style
operator|.
name|getReferenceFormat
argument_list|(
literal|"incollection"
argument_list|)
expr_stmt|;
name|l
operator|.
name|setPostFormatter
argument_list|(
operator|new
name|OOPreFormatter
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"BostrÃ¶m, G.; WÃ¤yrynen, J.; BodÃ©n, M.; Beznosov, K. and Kruchten, P. (<b>2006</b>).<i>Extending XP practices to support security requirements engineering</i>. In:  (Ed.),<i>SESS '06: Proceedings of the 2006 international workshop on Software engineering for secure systems</i>, ACM."
argument_list|,
name|l
operator|.
name|doLayout
argument_list|(
name|entry
argument_list|,
name|db
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

