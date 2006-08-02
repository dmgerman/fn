begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|tests.net.sf.jabref
package|package
name|tests
operator|.
name|net
operator|.
name|sf
operator|.
name|jabref
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|ByteArrayOutputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|PrintStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|StringReader
import|;
end_import

begin_import
import|import
name|junit
operator|.
name|framework
operator|.
name|TestCase
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
name|BibtexDatabase
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
name|BibtexEntry
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
name|Util
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
name|imports
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
name|imports
operator|.
name|ParserResult
import|;
end_import

begin_class
DECL|class|UtilTest
specifier|public
class|class
name|UtilTest
extends|extends
name|TestCase
block|{
DECL|method|testBool ()
specifier|public
name|void
name|testBool
parameter_list|()
block|{
comment|// cannot be tested
block|}
DECL|method|testPr ()
specifier|public
name|void
name|testPr
parameter_list|()
block|{
comment|// cannot be tested
block|}
DECL|method|testPr_ ()
specifier|public
name|void
name|testPr_
parameter_list|()
block|{
comment|// cannot be tested
block|}
DECL|method|testNCase ()
specifier|public
name|void
name|testNCase
parameter_list|()
block|{
name|fail
argument_list|(
literal|"Not yet implemented"
argument_list|)
expr_stmt|;
block|}
DECL|method|testCheckName ()
specifier|public
name|void
name|testCheckName
parameter_list|()
block|{
name|fail
argument_list|(
literal|"Not yet implemented"
argument_list|)
expr_stmt|;
block|}
DECL|method|testCreateNeutralId ()
specifier|public
name|void
name|testCreateNeutralId
parameter_list|()
block|{
name|fail
argument_list|(
literal|"Not yet implemented"
argument_list|)
expr_stmt|;
block|}
DECL|method|testPlaceDialog ()
specifier|public
name|void
name|testPlaceDialog
parameter_list|()
block|{
name|fail
argument_list|(
literal|"Not yet implemented"
argument_list|)
expr_stmt|;
block|}
DECL|method|testParseField ()
specifier|public
name|void
name|testParseField
parameter_list|()
block|{
name|fail
argument_list|(
literal|"Not yet implemented"
argument_list|)
expr_stmt|;
block|}
DECL|method|testShaveString ()
specifier|public
name|void
name|testShaveString
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|null
argument_list|,
name|Util
operator|.
name|shaveString
argument_list|(
literal|null
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|Util
operator|.
name|shaveString
argument_list|(
literal|""
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"aaa"
argument_list|,
name|Util
operator|.
name|shaveString
argument_list|(
literal|"   aaa\t\t\n\r"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"a"
argument_list|,
name|Util
operator|.
name|shaveString
argument_list|(
literal|"  {a}    "
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"a"
argument_list|,
name|Util
operator|.
name|shaveString
argument_list|(
literal|"  \"a\"    "
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"{a}"
argument_list|,
name|Util
operator|.
name|shaveString
argument_list|(
literal|"  {{a}}    "
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"{a}"
argument_list|,
name|Util
operator|.
name|shaveString
argument_list|(
literal|"  \"{a}\"    "
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"\"{a\"}"
argument_list|,
name|Util
operator|.
name|shaveString
argument_list|(
literal|"  \"{a\"}    "
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|testCheckLegalKey ()
specifier|public
name|void
name|testCheckLegalKey
parameter_list|()
block|{
name|fail
argument_list|(
literal|"Not yet implemented"
argument_list|)
expr_stmt|;
block|}
DECL|method|testReplaceSpecialCharacters ()
specifier|public
name|void
name|testReplaceSpecialCharacters
parameter_list|()
block|{
name|fail
argument_list|(
literal|"Not yet implemented"
argument_list|)
expr_stmt|;
block|}
DECL|method|test_wrap2 ()
specifier|public
name|void
name|test_wrap2
parameter_list|()
block|{
name|fail
argument_list|(
literal|"Not yet implemented"
argument_list|)
expr_stmt|;
block|}
DECL|method|testWrap2 ()
specifier|public
name|void
name|testWrap2
parameter_list|()
block|{
name|fail
argument_list|(
literal|"Not yet implemented"
argument_list|)
expr_stmt|;
block|}
DECL|method|test__wrap2 ()
specifier|public
name|void
name|test__wrap2
parameter_list|()
block|{
name|fail
argument_list|(
literal|"Not yet implemented"
argument_list|)
expr_stmt|;
block|}
DECL|method|testFindDeliminatedWordsInField ()
specifier|public
name|void
name|testFindDeliminatedWordsInField
parameter_list|()
block|{
name|fail
argument_list|(
literal|"Not yet implemented"
argument_list|)
expr_stmt|;
block|}
DECL|method|testFindAllWordsInField ()
specifier|public
name|void
name|testFindAllWordsInField
parameter_list|()
block|{
name|fail
argument_list|(
literal|"Not yet implemented"
argument_list|)
expr_stmt|;
block|}
DECL|method|testStringArrayToDelimited ()
specifier|public
name|void
name|testStringArrayToDelimited
parameter_list|()
block|{
name|fail
argument_list|(
literal|"Not yet implemented"
argument_list|)
expr_stmt|;
block|}
DECL|method|testDelimToStringArray ()
specifier|public
name|void
name|testDelimToStringArray
parameter_list|()
block|{
name|fail
argument_list|(
literal|"Not yet implemented"
argument_list|)
expr_stmt|;
block|}
DECL|method|testOpenExternalViewer ()
specifier|public
name|void
name|testOpenExternalViewer
parameter_list|()
block|{
name|fail
argument_list|(
literal|"Not yet implemented"
argument_list|)
expr_stmt|;
block|}
DECL|method|testOpenFileOnWindows ()
specifier|public
name|void
name|testOpenFileOnWindows
parameter_list|()
block|{
name|fail
argument_list|(
literal|"Not yet implemented"
argument_list|)
expr_stmt|;
block|}
DECL|method|testOpenExternalFileAnyFormat ()
specifier|public
name|void
name|testOpenExternalFileAnyFormat
parameter_list|()
block|{
name|fail
argument_list|(
literal|"Not yet implemented"
argument_list|)
expr_stmt|;
block|}
DECL|method|testFindPdf ()
specifier|public
name|void
name|testFindPdf
parameter_list|()
block|{
name|fail
argument_list|(
literal|"Not yet implemented"
argument_list|)
expr_stmt|;
block|}
DECL|method|testFindFile ()
specifier|public
name|void
name|testFindFile
parameter_list|()
block|{
comment|// -> Tested in UtilFindFileTest
block|}
DECL|method|testJoin ()
specifier|public
name|void
name|testJoin
parameter_list|()
block|{
name|String
index|[]
name|s
init|=
literal|"ab/cd/ed"
operator|.
name|split
argument_list|(
literal|"/"
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|"ab\\cd\\ed"
argument_list|,
name|Util
operator|.
name|join
argument_list|(
name|s
argument_list|,
literal|"\\"
argument_list|,
literal|0
argument_list|,
name|s
operator|.
name|length
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"cd\\ed"
argument_list|,
name|Util
operator|.
name|join
argument_list|(
name|s
argument_list|,
literal|"\\"
argument_list|,
literal|1
argument_list|,
name|s
operator|.
name|length
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"ed"
argument_list|,
name|Util
operator|.
name|join
argument_list|(
name|s
argument_list|,
literal|"\\"
argument_list|,
literal|2
argument_list|,
name|s
operator|.
name|length
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|Util
operator|.
name|join
argument_list|(
name|s
argument_list|,
literal|"\\"
argument_list|,
literal|3
argument_list|,
name|s
operator|.
name|length
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|Util
operator|.
name|join
argument_list|(
operator|new
name|String
index|[]
block|{}
argument_list|,
literal|"\\"
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|testStripBrackets ()
specifier|public
name|void
name|testStripBrackets
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"foo"
argument_list|,
name|Util
operator|.
name|stripBrackets
argument_list|(
literal|"[foo]"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"[foo]"
argument_list|,
name|Util
operator|.
name|stripBrackets
argument_list|(
literal|"[[foo]]"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"foo"
argument_list|,
name|Util
operator|.
name|stripBrackets
argument_list|(
literal|"foo]"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"foo"
argument_list|,
name|Util
operator|.
name|stripBrackets
argument_list|(
literal|"[foo"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|Util
operator|.
name|stripBrackets
argument_list|(
literal|""
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|Util
operator|.
name|stripBrackets
argument_list|(
literal|"[]"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|Util
operator|.
name|stripBrackets
argument_list|(
literal|"["
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|Util
operator|.
name|stripBrackets
argument_list|(
literal|"]"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"f[]f"
argument_list|,
name|Util
operator|.
name|stripBrackets
argument_list|(
literal|"f[]f"
argument_list|)
argument_list|)
expr_stmt|;
try|try
block|{
name|Util
operator|.
name|stripBrackets
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|fail
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NullPointerException
name|npe
parameter_list|)
block|{ 			 		}
block|}
DECL|field|database
name|BibtexDatabase
name|database
decl_stmt|;
DECL|field|entry
name|BibtexEntry
name|entry
decl_stmt|;
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
block|{
name|StringReader
name|reader
init|=
operator|new
name|StringReader
argument_list|(
literal|"@ARTICLE{HipKro03,"
operator|+
literal|"\n"
operator|+
literal|"  author = {Eric von Hippel and Georg von Krogh},"
operator|+
literal|"\n"
operator|+
literal|"  title = {Open Source Software and the \"Private-Collective\" Innovation Model: Issues for Organization Science},"
operator|+
literal|"\n"
operator|+
literal|"  journal = {Organization Science},"
operator|+
literal|"\n"
operator|+
literal|"  year = {2003},"
operator|+
literal|"\n"
operator|+
literal|"  volume = {14},"
operator|+
literal|"\n"
operator|+
literal|"  pages = {209--223},"
operator|+
literal|"\n"
operator|+
literal|"  number = {2},"
operator|+
literal|"\n"
operator|+
literal|"  address = {Institute for Operations Research and the Management Sciences (INFORMS), Linthicum, Maryland, USA},"
operator|+
literal|"\n"
operator|+
literal|"  doi = {http://dx.doi.org/10.1287/orsc.14.2.209.14992},"
operator|+
literal|"\n"
operator|+
literal|"  issn = {1526-5455},"
operator|+
literal|"\n"
operator|+
literal|"  publisher = {INFORMS}"
operator|+
literal|"\n"
operator|+
literal|"}"
argument_list|)
decl_stmt|;
name|BibtexParser
name|parser
init|=
operator|new
name|BibtexParser
argument_list|(
name|reader
argument_list|)
decl_stmt|;
name|ParserResult
name|result
init|=
literal|null
decl_stmt|;
try|try
block|{
name|result
operator|=
name|parser
operator|.
name|parse
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|fail
argument_list|()
expr_stmt|;
block|}
name|database
operator|=
name|result
operator|.
name|getDatabase
argument_list|()
expr_stmt|;
name|entry
operator|=
name|database
operator|.
name|getEntriesByKey
argument_list|(
literal|"HipKro03"
argument_list|)
index|[
literal|0
index|]
expr_stmt|;
name|assertNotNull
argument_list|(
name|database
argument_list|)
expr_stmt|;
name|assertNotNull
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
DECL|method|testFieldAndFormat ()
specifier|public
name|void
name|testFieldAndFormat
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"Eric von Hippel and Georg von Krogh"
argument_list|,
name|Util
operator|.
name|getFieldAndFormat
argument_list|(
literal|"[author]"
argument_list|,
name|entry
argument_list|,
name|database
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Eric von Hippel and Georg von Krogh"
argument_list|,
name|Util
operator|.
name|getFieldAndFormat
argument_list|(
literal|"author"
argument_list|,
name|entry
argument_list|,
name|database
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|null
argument_list|,
name|Util
operator|.
name|getFieldAndFormat
argument_list|(
literal|"[unknownkey]"
argument_list|,
name|entry
argument_list|,
name|database
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"HipKro03"
argument_list|,
name|Util
operator|.
name|getFieldAndFormat
argument_list|(
literal|"[bibtexkey]"
argument_list|,
name|entry
argument_list|,
name|database
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|testExpandBrackets ()
specifier|public
name|void
name|testExpandBrackets
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|Util
operator|.
name|expandBrackets
argument_list|(
literal|""
argument_list|,
name|entry
argument_list|,
name|database
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"dropped"
argument_list|,
name|Util
operator|.
name|expandBrackets
argument_list|(
literal|"drop[unknownkey]ped"
argument_list|,
name|entry
argument_list|,
name|database
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Eric von Hippel and Georg von Krogh"
argument_list|,
name|Util
operator|.
name|expandBrackets
argument_list|(
literal|"[author]"
argument_list|,
name|entry
argument_list|,
name|database
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Eric von Hippel and Georg von Krogh are two famous authors."
argument_list|,
name|Util
operator|.
name|expandBrackets
argument_list|(
literal|"[author] are two famous authors."
argument_list|,
name|entry
argument_list|,
name|database
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Eric von Hippel and Georg von Krogh are two famous authors."
argument_list|,
name|Util
operator|.
name|expandBrackets
argument_list|(
literal|"[author] are two famous authors."
argument_list|,
name|entry
argument_list|,
name|database
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Eric von Hippel and Georg von Krogh have published Open Source Software and the \"Private-Collective\" Innovation Model: Issues for Organization Science in Organization Science."
argument_list|,
name|Util
operator|.
name|expandBrackets
argument_list|(
literal|"[author] have published [title] in [journal]."
argument_list|,
name|entry
argument_list|,
name|database
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|testExpandFilename ()
specifier|public
name|void
name|testExpandFilename
parameter_list|()
block|{
name|fail
argument_list|(
literal|"Not yet implemented"
argument_list|)
expr_stmt|;
block|}
DECL|method|testIsDuplicate ()
specifier|public
name|void
name|testIsDuplicate
parameter_list|()
block|{
name|fail
argument_list|(
literal|"Not yet implemented"
argument_list|)
expr_stmt|;
block|}
DECL|method|testContainsDuplicate ()
specifier|public
name|void
name|testContainsDuplicate
parameter_list|()
block|{
name|fail
argument_list|(
literal|"Not yet implemented"
argument_list|)
expr_stmt|;
block|}
DECL|method|testCompareEntriesStrictly ()
specifier|public
name|void
name|testCompareEntriesStrictly
parameter_list|()
block|{
name|fail
argument_list|(
literal|"Not yet implemented"
argument_list|)
expr_stmt|;
block|}
DECL|method|testSetAutomaticFieldsList ()
specifier|public
name|void
name|testSetAutomaticFieldsList
parameter_list|()
block|{
name|fail
argument_list|(
literal|"Not yet implemented"
argument_list|)
expr_stmt|;
block|}
DECL|method|testSetAutomaticFieldsBibtexEntry ()
specifier|public
name|void
name|testSetAutomaticFieldsBibtexEntry
parameter_list|()
block|{
name|fail
argument_list|(
literal|"Not yet implemented"
argument_list|)
expr_stmt|;
block|}
DECL|method|testCopyFile ()
specifier|public
name|void
name|testCopyFile
parameter_list|()
block|{
name|fail
argument_list|(
literal|"Not yet implemented"
argument_list|)
expr_stmt|;
block|}
DECL|method|testPerformCompatibilityUpdate ()
specifier|public
name|void
name|testPerformCompatibilityUpdate
parameter_list|()
block|{
name|fail
argument_list|(
literal|"Not yet implemented"
argument_list|)
expr_stmt|;
block|}
DECL|method|testGetCorrectFileName ()
specifier|public
name|void
name|testGetCorrectFileName
parameter_list|()
block|{
name|fail
argument_list|(
literal|"Not yet implemented"
argument_list|)
expr_stmt|;
block|}
DECL|method|testQuoteForHTML ()
specifier|public
name|void
name|testQuoteForHTML
parameter_list|()
block|{
name|fail
argument_list|(
literal|"Not yet implemented"
argument_list|)
expr_stmt|;
block|}
DECL|method|testQuoteStringStringChar ()
specifier|public
name|void
name|testQuoteStringStringChar
parameter_list|()
block|{
name|fail
argument_list|(
literal|"Not yet implemented"
argument_list|)
expr_stmt|;
block|}
DECL|method|testQuoteStringStringCharInt ()
specifier|public
name|void
name|testQuoteStringStringCharInt
parameter_list|()
block|{
name|fail
argument_list|(
literal|"Not yet implemented"
argument_list|)
expr_stmt|;
block|}
DECL|method|testUnquote ()
specifier|public
name|void
name|testUnquote
parameter_list|()
block|{
name|fail
argument_list|(
literal|"Not yet implemented"
argument_list|)
expr_stmt|;
block|}
DECL|method|testQuoteMeta ()
specifier|public
name|void
name|testQuoteMeta
parameter_list|()
block|{
name|fail
argument_list|(
literal|"Not yet implemented"
argument_list|)
expr_stmt|;
block|}
DECL|method|testSortWordsAndRemoveDuplicates ()
specifier|public
name|void
name|testSortWordsAndRemoveDuplicates
parameter_list|()
block|{
name|fail
argument_list|(
literal|"Not yet implemented"
argument_list|)
expr_stmt|;
block|}
DECL|method|testWarnAssignmentSideEffects ()
specifier|public
name|void
name|testWarnAssignmentSideEffects
parameter_list|()
block|{
name|fail
argument_list|(
literal|"Not yet implemented"
argument_list|)
expr_stmt|;
block|}
DECL|method|testPutBracesAroundCapitals ()
specifier|public
name|void
name|testPutBracesAroundCapitals
parameter_list|()
block|{
name|fail
argument_list|(
literal|"Not yet implemented"
argument_list|)
expr_stmt|;
block|}
DECL|method|testRemoveBracesAroundCapitals ()
specifier|public
name|void
name|testRemoveBracesAroundCapitals
parameter_list|()
block|{
name|fail
argument_list|(
literal|"Not yet implemented"
argument_list|)
expr_stmt|;
block|}
DECL|method|testRemoveSingleBracesAroundCapitals ()
specifier|public
name|void
name|testRemoveSingleBracesAroundCapitals
parameter_list|()
block|{
name|fail
argument_list|(
literal|"Not yet implemented"
argument_list|)
expr_stmt|;
block|}
DECL|method|testGetFileFilterForField ()
specifier|public
name|void
name|testGetFileFilterForField
parameter_list|()
block|{
name|fail
argument_list|(
literal|"Not yet implemented"
argument_list|)
expr_stmt|;
block|}
DECL|method|testShowQuickErrorDialog ()
specifier|public
name|void
name|testShowQuickErrorDialog
parameter_list|()
block|{
name|fail
argument_list|(
literal|"Not yet implemented"
argument_list|)
expr_stmt|;
block|}
DECL|method|testWrapHTML ()
specifier|public
name|void
name|testWrapHTML
parameter_list|()
block|{
name|fail
argument_list|(
literal|"Not yet implemented"
argument_list|)
expr_stmt|;
block|}
DECL|method|testEasyDateFormat ()
specifier|public
name|void
name|testEasyDateFormat
parameter_list|()
block|{
name|fail
argument_list|(
literal|"Not yet implemented"
argument_list|)
expr_stmt|;
block|}
DECL|method|testEasyDateFormatDate ()
specifier|public
name|void
name|testEasyDateFormatDate
parameter_list|()
block|{
name|fail
argument_list|(
literal|"Not yet implemented"
argument_list|)
expr_stmt|;
block|}
DECL|method|testMarkEntry ()
specifier|public
name|void
name|testMarkEntry
parameter_list|()
block|{
name|fail
argument_list|(
literal|"Not yet implemented"
argument_list|)
expr_stmt|;
block|}
DECL|method|testUnmarkEntry ()
specifier|public
name|void
name|testUnmarkEntry
parameter_list|()
block|{
name|fail
argument_list|(
literal|"Not yet implemented"
argument_list|)
expr_stmt|;
block|}
DECL|method|testIsMarked ()
specifier|public
name|void
name|testIsMarked
parameter_list|()
block|{
name|fail
argument_list|(
literal|"Not yet implemented"
argument_list|)
expr_stmt|;
block|}
DECL|method|testFindEncodingsForString ()
specifier|public
name|void
name|testFindEncodingsForString
parameter_list|()
block|{
name|fail
argument_list|(
literal|"Not yet implemented"
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

