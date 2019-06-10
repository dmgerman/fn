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
DECL|class|DefaultTexParserTest
specifier|public
class|class
name|DefaultTexParserTest
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
DECL|method|testMatchCite (String key, String citeString)
specifier|private
name|void
name|testMatchCite
parameter_list|(
name|String
name|key
parameter_list|,
name|String
name|citeString
parameter_list|)
block|{
name|TexParserResult
name|texParserResult
init|=
operator|new
name|DefaultTexParser
argument_list|()
operator|.
name|parse
argument_list|(
name|citeString
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
name|addKey
argument_list|(
name|key
argument_list|,
name|Paths
operator|.
name|get
argument_list|(
literal|"foo/bar"
argument_list|)
argument_list|,
literal|1
argument_list|,
literal|0
argument_list|,
name|citeString
operator|.
name|length
argument_list|()
argument_list|,
name|citeString
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|expectedParserResult
argument_list|,
name|texParserResult
argument_list|)
expr_stmt|;
block|}
DECL|method|testNonMatchCite (String citeString)
specifier|private
name|void
name|testNonMatchCite
parameter_list|(
name|String
name|citeString
parameter_list|)
block|{
name|TexParserResult
name|texParserResult
init|=
operator|new
name|DefaultTexParser
argument_list|()
operator|.
name|parse
argument_list|(
name|citeString
argument_list|)
decl_stmt|;
name|TexParserResult
name|expectedParserResult
init|=
operator|new
name|TexParserResult
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
name|expectedParserResult
argument_list|,
name|texParserResult
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testCiteCommands ()
specifier|public
name|void
name|testCiteCommands
parameter_list|()
block|{
name|testMatchCite
argument_list|(
name|UNRESOLVED
argument_list|,
literal|"\\cite[pre][post]{UnresolvedKey}"
argument_list|)
expr_stmt|;
name|testMatchCite
argument_list|(
name|UNRESOLVED
argument_list|,
literal|"\\cite*{UnresolvedKey}"
argument_list|)
expr_stmt|;
name|testMatchCite
argument_list|(
name|UNRESOLVED
argument_list|,
literal|"\\parencite[post]{UnresolvedKey}"
argument_list|)
expr_stmt|;
name|testMatchCite
argument_list|(
name|UNRESOLVED
argument_list|,
literal|"\\cite[pre][post]{UnresolvedKey}"
argument_list|)
expr_stmt|;
name|testMatchCite
argument_list|(
name|EINSTEIN_C
argument_list|,
literal|"\\citep{Einstein1920c}"
argument_list|)
expr_stmt|;
name|testNonMatchCite
argument_list|(
literal|"\\citet21312{123U123n123resolvedKey}"
argument_list|)
expr_stmt|;
name|testNonMatchCite
argument_list|(
literal|"\\1cite[pr234e][post]{UnresolvedKey}"
argument_list|)
expr_stmt|;
name|testNonMatchCite
argument_list|(
literal|"\\citep55{5}UnresolvedKey}"
argument_list|)
expr_stmt|;
name|testNonMatchCite
argument_list|(
literal|"\\cit2et{UnresolvedKey}"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testSingleFile ()
specifier|public
name|void
name|testSingleFile
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
name|DefaultTexParserTest
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
name|assertEquals
argument_list|(
name|expectedParserResult
argument_list|,
name|parserResult
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testTwoFiles ()
specifier|public
name|void
name|testTwoFiles
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
name|DefaultTexParserTest
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
name|DefaultTexParserTest
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
name|assertEquals
argument_list|(
name|expectedParserResult
argument_list|,
name|parserResult
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testDuplicateFiles ()
specifier|public
name|void
name|testDuplicateFiles
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
name|DefaultTexParserTest
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
name|Arrays
operator|.
name|asList
argument_list|(
name|texFile
argument_list|,
name|texFile
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
name|texFile
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
name|assertEquals
argument_list|(
name|expectedParserResult
argument_list|,
name|parserResult
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testUnknownKey ()
specifier|public
name|void
name|testUnknownKey
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
name|DefaultTexParserTest
operator|.
name|class
operator|.
name|getResource
argument_list|(
literal|"unknown_key.tex"
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
name|DARWIN
argument_list|,
name|texFile
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
name|texFile
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
name|UNKNOWN
argument_list|,
name|texFile
argument_list|,
literal|6
argument_list|,
literal|48
argument_list|,
literal|65
argument_list|,
literal|"This is some content trying to cite a bib file: \\cite{UnknownKey}"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|expectedParserResult
argument_list|,
name|parserResult
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testFileNotFound ()
specifier|public
name|void
name|testFileNotFound
parameter_list|()
block|{
name|Path
name|texFile
init|=
name|Paths
operator|.
name|get
argument_list|(
literal|"file_not_found.tex"
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
name|assertEquals
argument_list|(
name|expectedParserResult
argument_list|,
name|parserResult
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testNestedFiles ()
specifier|public
name|void
name|testNestedFiles
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
name|DefaultTexParserTest
operator|.
name|class
operator|.
name|getResource
argument_list|(
literal|"nested.tex"
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
name|DefaultTexParserTest
operator|.
name|class
operator|.
name|getResource
argument_list|(
literal|"nested2.tex"
argument_list|)
operator|.
name|toURI
argument_list|()
argument_list|)
decl_stmt|;
name|Path
name|texFile3
init|=
name|Paths
operator|.
name|get
argument_list|(
name|DefaultTexParserTest
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
name|getNestedFiles
argument_list|()
operator|.
name|addAll
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|texFile2
argument_list|,
name|texFile3
argument_list|)
argument_list|)
expr_stmt|;
name|expectedParserResult
operator|.
name|addKey
argument_list|(
name|EINSTEIN
argument_list|,
name|texFile3
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
name|texFile3
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
name|texFile3
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
name|texFile3
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
name|assertEquals
argument_list|(
name|expectedParserResult
argument_list|,
name|parserResult
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

