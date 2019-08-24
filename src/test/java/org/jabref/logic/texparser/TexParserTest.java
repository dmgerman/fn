begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.texparser
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|texparser
package|;
end_package

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
name|org
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
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|field
operator|.
name|StandardField
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
name|types
operator|.
name|StandardEntryType
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
name|texparser
operator|.
name|TexBibEntriesResolverResult
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
name|texparser
operator|.
name|TexParserResult
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
DECL|class|TexParserTest
specifier|public
class|class
name|TexParserTest
block|{
DECL|field|DARWIN
specifier|private
specifier|final
specifier|static
name|String
name|DARWIN
init|=
literal|"Darwin1888"
decl_stmt|;
DECL|field|EINSTEIN
specifier|private
specifier|final
specifier|static
name|String
name|EINSTEIN
init|=
literal|"Einstein1920"
decl_stmt|;
DECL|field|NEWTON
specifier|private
specifier|final
specifier|static
name|String
name|NEWTON
init|=
literal|"Newton1999"
decl_stmt|;
DECL|field|EINSTEIN_A
specifier|private
specifier|final
specifier|static
name|String
name|EINSTEIN_A
init|=
literal|"Einstein1920a"
decl_stmt|;
DECL|field|EINSTEIN_B
specifier|private
specifier|final
specifier|static
name|String
name|EINSTEIN_B
init|=
literal|"Einstein1920b"
decl_stmt|;
DECL|field|EINSTEIN_C
specifier|private
specifier|final
specifier|static
name|String
name|EINSTEIN_C
init|=
literal|"Einstein1920c"
decl_stmt|;
DECL|field|EINSTEIN_21
specifier|private
specifier|final
specifier|static
name|String
name|EINSTEIN_21
init|=
literal|"Einstein1921"
decl_stmt|;
DECL|field|UNRESOLVED
specifier|private
specifier|final
specifier|static
name|String
name|UNRESOLVED
init|=
literal|"UnresolvedKey"
decl_stmt|;
DECL|field|UNKNOWN
specifier|private
specifier|final
specifier|static
name|String
name|UNKNOWN
init|=
literal|"UnknownKey"
decl_stmt|;
DECL|field|database
specifier|private
specifier|static
name|BibDatabase
name|database
decl_stmt|;
DECL|field|database2
specifier|private
specifier|static
name|BibDatabase
name|database2
decl_stmt|;
annotation|@
name|BeforeEach
DECL|method|setUp ()
specifier|private
name|void
name|setUp
parameter_list|()
block|{
name|database
operator|=
operator|new
name|BibDatabase
argument_list|()
expr_stmt|;
name|database2
operator|=
operator|new
name|BibDatabase
argument_list|()
expr_stmt|;
name|BibEntry
name|darwin
init|=
operator|new
name|BibEntry
argument_list|(
name|StandardEntryType
operator|.
name|Book
argument_list|)
operator|.
name|withCiteKey
argument_list|(
name|DARWIN
argument_list|)
operator|.
name|withField
argument_list|(
name|StandardField
operator|.
name|TITLE
argument_list|,
literal|"The descent of man, and selection in relation to sex"
argument_list|)
operator|.
name|withField
argument_list|(
name|StandardField
operator|.
name|PUBLISHER
argument_list|,
literal|"J. Murray"
argument_list|)
operator|.
name|withField
argument_list|(
name|StandardField
operator|.
name|YEAR
argument_list|,
literal|"1888"
argument_list|)
operator|.
name|withField
argument_list|(
name|StandardField
operator|.
name|AUTHOR
argument_list|,
literal|"Darwin, Charles"
argument_list|)
decl_stmt|;
name|database
operator|.
name|insertEntry
argument_list|(
name|darwin
argument_list|)
expr_stmt|;
name|BibEntry
name|einstein
init|=
operator|new
name|BibEntry
argument_list|(
name|StandardEntryType
operator|.
name|Book
argument_list|)
operator|.
name|withCiteKey
argument_list|(
name|EINSTEIN
argument_list|)
operator|.
name|withField
argument_list|(
name|StandardField
operator|.
name|TITLE
argument_list|,
literal|"Relativity: The special and general theory"
argument_list|)
operator|.
name|withField
argument_list|(
name|StandardField
operator|.
name|PUBLISHER
argument_list|,
literal|"Penguin"
argument_list|)
operator|.
name|withField
argument_list|(
name|StandardField
operator|.
name|YEAR
argument_list|,
literal|"1920"
argument_list|)
operator|.
name|withField
argument_list|(
name|StandardField
operator|.
name|AUTHOR
argument_list|,
literal|"Einstein, Albert"
argument_list|)
decl_stmt|;
name|database
operator|.
name|insertEntry
argument_list|(
name|einstein
argument_list|)
expr_stmt|;
name|database2
operator|.
name|insertEntry
argument_list|(
name|einstein
argument_list|)
expr_stmt|;
name|BibEntry
name|newton
init|=
operator|new
name|BibEntry
argument_list|(
name|StandardEntryType
operator|.
name|Book
argument_list|)
operator|.
name|withCiteKey
argument_list|(
name|NEWTON
argument_list|)
operator|.
name|withField
argument_list|(
name|StandardField
operator|.
name|TITLE
argument_list|,
literal|"The Principia: mathematical principles of natural philosophy"
argument_list|)
operator|.
name|withField
argument_list|(
name|StandardField
operator|.
name|PUBLISHER
argument_list|,
literal|"Univ of California Press"
argument_list|)
operator|.
name|withField
argument_list|(
name|StandardField
operator|.
name|YEAR
argument_list|,
literal|"1999"
argument_list|)
operator|.
name|withField
argument_list|(
name|StandardField
operator|.
name|AUTHOR
argument_list|,
literal|"Newton, Isaac"
argument_list|)
decl_stmt|;
name|database
operator|.
name|insertEntry
argument_list|(
name|newton
argument_list|)
expr_stmt|;
name|BibEntry
name|einsteinA
init|=
operator|new
name|BibEntry
argument_list|(
name|StandardEntryType
operator|.
name|Book
argument_list|)
operator|.
name|withCiteKey
argument_list|(
name|EINSTEIN_A
argument_list|)
operator|.
name|withField
argument_list|(
name|StandardField
operator|.
name|CROSSREF
argument_list|,
literal|"Einstein1920"
argument_list|)
operator|.
name|withField
argument_list|(
name|StandardField
operator|.
name|PAGES
argument_list|,
literal|"22--23"
argument_list|)
decl_stmt|;
name|database
operator|.
name|insertEntry
argument_list|(
name|einsteinA
argument_list|)
expr_stmt|;
name|BibEntry
name|einsteinB
init|=
operator|new
name|BibEntry
argument_list|(
name|StandardEntryType
operator|.
name|Book
argument_list|)
operator|.
name|withCiteKey
argument_list|(
name|EINSTEIN_B
argument_list|)
operator|.
name|withField
argument_list|(
name|StandardField
operator|.
name|CROSSREF
argument_list|,
literal|"Einstein1921"
argument_list|)
operator|.
name|withField
argument_list|(
name|StandardField
operator|.
name|PAGES
argument_list|,
literal|"22--23"
argument_list|)
decl_stmt|;
name|database
operator|.
name|insertEntry
argument_list|(
name|einsteinB
argument_list|)
expr_stmt|;
name|BibEntry
name|einsteinC
init|=
operator|new
name|BibEntry
argument_list|(
name|StandardEntryType
operator|.
name|Book
argument_list|)
operator|.
name|withCiteKey
argument_list|(
name|EINSTEIN_C
argument_list|)
operator|.
name|withField
argument_list|(
name|StandardField
operator|.
name|CROSSREF
argument_list|,
literal|"Einstein1920"
argument_list|)
operator|.
name|withField
argument_list|(
name|StandardField
operator|.
name|PAGES
argument_list|,
literal|"25--33"
argument_list|)
decl_stmt|;
name|database
operator|.
name|insertEntry
argument_list|(
name|einsteinC
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testSameFileDifferentDatabases ()
specifier|public
name|void
name|testSameFileDifferentDatabases
parameter_list|()
throws|throws
name|URISyntaxException
block|{
name|Path
name|texFile
init|=
name|Paths
operator|.
name|get
argument_list|(
name|TexParserTest
operator|.
name|class
operator|.
name|getResource
argument_list|(
literal|"paper.tex"
argument_list|)
operator|.
name|toURI
argument_list|()
argument_list|)
decl_stmt|;
name|TexParserResult
name|parserResult
init|=
operator|new
name|DefaultTexParser
argument_list|()
operator|.
name|parse
argument_list|(
name|texFile
argument_list|)
decl_stmt|;
name|TexParserResult
name|expectedParserResult
init|=
operator|new
name|TexParserResult
argument_list|()
decl_stmt|;
name|expectedParserResult
operator|.
name|getFileList
argument_list|()
operator|.
name|add
argument_list|(
name|texFile
argument_list|)
expr_stmt|;
name|expectedParserResult
operator|.
name|addKey
argument_list|(
name|EINSTEIN
argument_list|,
name|texFile
argument_list|,
literal|4
argument_list|,
literal|0
argument_list|,
literal|19
argument_list|,
literal|"\\cite{Einstein1920}"
argument_list|)
expr_stmt|;
name|expectedParserResult
operator|.
name|addKey
argument_list|(
name|DARWIN
argument_list|,
name|texFile
argument_list|,
literal|5
argument_list|,
literal|0
argument_list|,
literal|17
argument_list|,
literal|"\\cite{Darwin1888}."
argument_list|)
expr_stmt|;
name|expectedParserResult
operator|.
name|addKey
argument_list|(
name|EINSTEIN
argument_list|,
name|texFile
argument_list|,
literal|6
argument_list|,
literal|14
argument_list|,
literal|33
argument_list|,
literal|"Einstein said \\cite{Einstein1920} that lorem impsum, consectetur adipiscing elit."
argument_list|)
expr_stmt|;
name|expectedParserResult
operator|.
name|addKey
argument_list|(
name|DARWIN
argument_list|,
name|texFile
argument_list|,
literal|7
argument_list|,
literal|67
argument_list|,
literal|84
argument_list|,
literal|"Nunc ultricies leo nec libero rhoncus, eu vehicula enim efficitur. \\cite{Darwin1888}"
argument_list|)
expr_stmt|;
name|TexBibEntriesResolverResult
name|crossingResult
init|=
operator|new
name|TexBibEntriesResolver
argument_list|(
name|database
argument_list|)
operator|.
name|resolveKeys
argument_list|(
name|parserResult
argument_list|)
decl_stmt|;
name|TexBibEntriesResolverResult
name|expectedCrossingResult
init|=
operator|new
name|TexBibEntriesResolverResult
argument_list|(
name|expectedParserResult
argument_list|)
decl_stmt|;
name|expectedCrossingResult
operator|.
name|insertEntry
argument_list|(
name|database
argument_list|,
name|DARWIN
argument_list|)
expr_stmt|;
name|expectedCrossingResult
operator|.
name|insertEntry
argument_list|(
name|database
argument_list|,
name|EINSTEIN
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|expectedCrossingResult
argument_list|,
name|crossingResult
argument_list|)
expr_stmt|;
name|TexBibEntriesResolverResult
name|crossingResult2
init|=
operator|new
name|TexBibEntriesResolver
argument_list|(
name|database2
argument_list|)
operator|.
name|resolveKeys
argument_list|(
name|parserResult
argument_list|)
decl_stmt|;
name|TexBibEntriesResolverResult
name|expectedCrossingResult2
init|=
operator|new
name|TexBibEntriesResolverResult
argument_list|(
name|expectedParserResult
argument_list|)
decl_stmt|;
name|expectedCrossingResult2
operator|.
name|insertEntry
argument_list|(
name|database2
argument_list|,
name|EINSTEIN
argument_list|)
expr_stmt|;
name|expectedCrossingResult2
operator|.
name|addUnresolvedKey
argument_list|(
name|DARWIN
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|expectedCrossingResult2
argument_list|,
name|crossingResult2
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testTwoFilesDifferentDatabases ()
specifier|public
name|void
name|testTwoFilesDifferentDatabases
parameter_list|()
throws|throws
name|URISyntaxException
block|{
name|Path
name|texFile
init|=
name|Paths
operator|.
name|get
argument_list|(
name|TexParserTest
operator|.
name|class
operator|.
name|getResource
argument_list|(
literal|"paper.tex"
argument_list|)
operator|.
name|toURI
argument_list|()
argument_list|)
decl_stmt|;
name|Path
name|texFile2
init|=
name|Paths
operator|.
name|get
argument_list|(
name|TexParserTest
operator|.
name|class
operator|.
name|getResource
argument_list|(
literal|"paper2.tex"
argument_list|)
operator|.
name|toURI
argument_list|()
argument_list|)
decl_stmt|;
name|TexParserResult
name|parserResult
init|=
operator|new
name|DefaultTexParser
argument_list|()
operator|.
name|parse
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|texFile
argument_list|,
name|texFile2
argument_list|)
argument_list|)
decl_stmt|;
name|TexParserResult
name|expectedParserResult
init|=
operator|new
name|TexParserResult
argument_list|()
decl_stmt|;
name|expectedParserResult
operator|.
name|getFileList
argument_list|()
operator|.
name|addAll
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|texFile
argument_list|,
name|texFile2
argument_list|)
argument_list|)
expr_stmt|;
name|expectedParserResult
operator|.
name|addKey
argument_list|(
name|EINSTEIN
argument_list|,
name|texFile
argument_list|,
literal|4
argument_list|,
literal|0
argument_list|,
literal|19
argument_list|,
literal|"\\cite{Einstein1920}"
argument_list|)
expr_stmt|;
name|expectedParserResult
operator|.
name|addKey
argument_list|(
name|DARWIN
argument_list|,
name|texFile
argument_list|,
literal|5
argument_list|,
literal|0
argument_list|,
literal|17
argument_list|,
literal|"\\cite{Darwin1888}."
argument_list|)
expr_stmt|;
name|expectedParserResult
operator|.
name|addKey
argument_list|(
name|EINSTEIN
argument_list|,
name|texFile
argument_list|,
literal|6
argument_list|,
literal|14
argument_list|,
literal|33
argument_list|,
literal|"Einstein said \\cite{Einstein1920} that lorem impsum, consectetur adipiscing elit."
argument_list|)
expr_stmt|;
name|expectedParserResult
operator|.
name|addKey
argument_list|(
name|DARWIN
argument_list|,
name|texFile
argument_list|,
literal|7
argument_list|,
literal|67
argument_list|,
literal|84
argument_list|,
literal|"Nunc ultricies leo nec libero rhoncus, eu vehicula enim efficitur. \\cite{Darwin1888}"
argument_list|)
expr_stmt|;
name|expectedParserResult
operator|.
name|addKey
argument_list|(
name|DARWIN
argument_list|,
name|texFile2
argument_list|,
literal|4
argument_list|,
literal|48
argument_list|,
literal|65
argument_list|,
literal|"This is some content trying to cite a bib file: \\cite{Darwin1888}"
argument_list|)
expr_stmt|;
name|expectedParserResult
operator|.
name|addKey
argument_list|(
name|EINSTEIN
argument_list|,
name|texFile2
argument_list|,
literal|5
argument_list|,
literal|48
argument_list|,
literal|67
argument_list|,
literal|"This is some content trying to cite a bib file: \\cite{Einstein1920}"
argument_list|)
expr_stmt|;
name|expectedParserResult
operator|.
name|addKey
argument_list|(
name|NEWTON
argument_list|,
name|texFile2
argument_list|,
literal|6
argument_list|,
literal|48
argument_list|,
literal|65
argument_list|,
literal|"This is some content trying to cite a bib file: \\cite{Newton1999}"
argument_list|)
expr_stmt|;
name|TexBibEntriesResolverResult
name|crossingResult
init|=
operator|new
name|TexBibEntriesResolver
argument_list|(
name|database
argument_list|)
operator|.
name|resolveKeys
argument_list|(
name|parserResult
argument_list|)
decl_stmt|;
name|TexBibEntriesResolverResult
name|expectedCrossingResult
init|=
operator|new
name|TexBibEntriesResolverResult
argument_list|(
name|expectedParserResult
argument_list|)
decl_stmt|;
name|expectedCrossingResult
operator|.
name|insertEntry
argument_list|(
name|database
argument_list|,
name|DARWIN
argument_list|)
expr_stmt|;
name|expectedCrossingResult
operator|.
name|insertEntry
argument_list|(
name|database
argument_list|,
name|EINSTEIN
argument_list|)
expr_stmt|;
name|expectedCrossingResult
operator|.
name|insertEntry
argument_list|(
name|database
argument_list|,
name|NEWTON
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|expectedCrossingResult
argument_list|,
name|crossingResult
argument_list|)
expr_stmt|;
name|TexBibEntriesResolverResult
name|crossingResult2
init|=
operator|new
name|TexBibEntriesResolver
argument_list|(
name|database2
argument_list|)
operator|.
name|resolveKeys
argument_list|(
name|parserResult
argument_list|)
decl_stmt|;
name|TexBibEntriesResolverResult
name|expectedCrossingResult2
init|=
operator|new
name|TexBibEntriesResolverResult
argument_list|(
name|expectedParserResult
argument_list|)
decl_stmt|;
name|expectedCrossingResult2
operator|.
name|insertEntry
argument_list|(
name|database2
argument_list|,
name|EINSTEIN
argument_list|)
expr_stmt|;
name|expectedCrossingResult2
operator|.
name|addUnresolvedKey
argument_list|(
name|DARWIN
argument_list|)
expr_stmt|;
name|expectedCrossingResult2
operator|.
name|addUnresolvedKey
argument_list|(
name|NEWTON
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|expectedCrossingResult2
argument_list|,
name|crossingResult2
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

