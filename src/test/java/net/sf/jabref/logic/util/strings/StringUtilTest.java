begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.util.strings
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|util
operator|.
name|strings
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
name|entry
operator|.
name|FileField
import|;
end_import

begin_class
DECL|class|StringUtilTest
specifier|public
class|class
name|StringUtilTest
block|{
annotation|@
name|BeforeClass
DECL|method|loadPreferences ()
specifier|public
specifier|static
name|void
name|loadPreferences
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
block|}
annotation|@
name|Test
DECL|method|testUnifyLineBreaks ()
specifier|public
name|void
name|testUnifyLineBreaks
parameter_list|()
throws|throws
name|Exception
block|{
comment|// Mac< v9
name|String
name|result
init|=
name|StringUtil
operator|.
name|unifyLineBreaksToConfiguredLineBreaks
argument_list|(
literal|"\r"
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Globals
operator|.
name|NEWLINE
argument_list|,
name|result
argument_list|)
expr_stmt|;
comment|// Windows
name|result
operator|=
name|StringUtil
operator|.
name|unifyLineBreaksToConfiguredLineBreaks
argument_list|(
literal|"\r\n"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Globals
operator|.
name|NEWLINE
argument_list|,
name|result
argument_list|)
expr_stmt|;
comment|// Unix
name|result
operator|=
name|StringUtil
operator|.
name|unifyLineBreaksToConfiguredLineBreaks
argument_list|(
literal|"\n"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Globals
operator|.
name|NEWLINE
argument_list|,
name|result
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetCorrectFileName ()
specifier|public
name|void
name|testGetCorrectFileName
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"aa.bib"
argument_list|,
name|StringUtil
operator|.
name|getCorrectFileName
argument_list|(
literal|"aa"
argument_list|,
literal|"bib"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|".login.bib"
argument_list|,
name|StringUtil
operator|.
name|getCorrectFileName
argument_list|(
literal|".login"
argument_list|,
literal|"bib"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"a.bib"
argument_list|,
name|StringUtil
operator|.
name|getCorrectFileName
argument_list|(
literal|"a.bib"
argument_list|,
literal|"bib"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"a.bib"
argument_list|,
name|StringUtil
operator|.
name|getCorrectFileName
argument_list|(
literal|"a.bib"
argument_list|,
literal|"BIB"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"a.bib"
argument_list|,
name|StringUtil
operator|.
name|getCorrectFileName
argument_list|(
literal|"a"
argument_list|,
literal|"bib"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"a.bb"
argument_list|,
name|StringUtil
operator|.
name|getCorrectFileName
argument_list|(
literal|"a.bb"
argument_list|,
literal|"bib"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|StringUtil
operator|.
name|getCorrectFileName
argument_list|(
literal|null
argument_list|,
literal|"bib"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testQuoteForHTML ()
specifier|public
name|void
name|testQuoteForHTML
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"&#33;"
argument_list|,
name|StringUtil
operator|.
name|quoteForHTML
argument_list|(
literal|"!"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"&#33;&#33;&#33;"
argument_list|,
name|StringUtil
operator|.
name|quoteForHTML
argument_list|(
literal|"!!!"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testRemoveBracesAroundCapitals ()
specifier|public
name|void
name|testRemoveBracesAroundCapitals
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"ABC"
argument_list|,
name|StringUtil
operator|.
name|removeBracesAroundCapitals
argument_list|(
literal|"{ABC}"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"ABC"
argument_list|,
name|StringUtil
operator|.
name|removeBracesAroundCapitals
argument_list|(
literal|"{{ABC}}"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"{abc}"
argument_list|,
name|StringUtil
operator|.
name|removeBracesAroundCapitals
argument_list|(
literal|"{abc}"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"ABCDEF"
argument_list|,
name|StringUtil
operator|.
name|removeBracesAroundCapitals
argument_list|(
literal|"{ABC}{DEF}"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testPutBracesAroundCapitals ()
specifier|public
name|void
name|testPutBracesAroundCapitals
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"{ABC}"
argument_list|,
name|StringUtil
operator|.
name|putBracesAroundCapitals
argument_list|(
literal|"ABC"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"{ABC}"
argument_list|,
name|StringUtil
operator|.
name|putBracesAroundCapitals
argument_list|(
literal|"{ABC}"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"abc"
argument_list|,
name|StringUtil
operator|.
name|putBracesAroundCapitals
argument_list|(
literal|"abc"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"#ABC#"
argument_list|,
name|StringUtil
operator|.
name|putBracesAroundCapitals
argument_list|(
literal|"#ABC#"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"{ABC} def {EFG}"
argument_list|,
name|StringUtil
operator|.
name|putBracesAroundCapitals
argument_list|(
literal|"ABC def EFG"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testShaveString ()
specifier|public
name|void
name|testShaveString
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|StringUtil
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
name|StringUtil
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
name|StringUtil
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
name|StringUtil
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
name|StringUtil
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
name|StringUtil
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
name|StringUtil
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
name|StringUtil
operator|.
name|shaveString
argument_list|(
literal|"  \"{a\"}    "
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
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
name|StringUtil
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
name|StringUtil
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
name|StringUtil
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
name|StringUtil
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
name|StringUtil
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
annotation|@
name|Test
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
name|StringUtil
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
name|StringUtil
operator|.
name|stripBrackets
argument_list|(
literal|"[[foo]]"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|StringUtil
operator|.
name|stripBrackets
argument_list|(
literal|""
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"[foo"
argument_list|,
name|StringUtil
operator|.
name|stripBrackets
argument_list|(
literal|"[foo"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"]"
argument_list|,
name|StringUtil
operator|.
name|stripBrackets
argument_list|(
literal|"]"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|StringUtil
operator|.
name|stripBrackets
argument_list|(
literal|"[]"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"f[]f"
argument_list|,
name|StringUtil
operator|.
name|stripBrackets
argument_list|(
literal|"f[]f"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|null
argument_list|,
name|StringUtil
operator|.
name|stripBrackets
argument_list|(
literal|null
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetPart ()
specifier|public
name|void
name|testGetPart
parameter_list|()
block|{
comment|// Should be added
block|}
annotation|@
name|Test
DECL|method|testFindEncodingsForString ()
specifier|public
name|void
name|testFindEncodingsForString
parameter_list|()
block|{
comment|// Unused in JabRef, but should be added in case it finds some use
block|}
annotation|@
name|Test
DECL|method|testWrap ()
specifier|public
name|void
name|testWrap
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"aaaaa"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"\tbbbbb"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"\tccccc"
argument_list|,
name|StringUtil
operator|.
name|wrap
argument_list|(
literal|"aaaaa bbbbb ccccc"
argument_list|,
literal|5
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"aaaaa bbbbb"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"\tccccc"
argument_list|,
name|StringUtil
operator|.
name|wrap
argument_list|(
literal|"aaaaa bbbbb ccccc"
argument_list|,
literal|8
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"aaaaa bbbbb"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"\tccccc"
argument_list|,
name|StringUtil
operator|.
name|wrap
argument_list|(
literal|"aaaaa bbbbb ccccc"
argument_list|,
literal|11
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"aaaaa bbbbb ccccc"
argument_list|,
name|StringUtil
operator|.
name|wrap
argument_list|(
literal|"aaaaa bbbbb ccccc"
argument_list|,
literal|12
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"aaaaa"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"\t"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"\tbbbbb"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"\t"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"\tccccc"
argument_list|,
name|StringUtil
operator|.
name|wrap
argument_list|(
literal|"aaaaa\nbbbbb\nccccc"
argument_list|,
literal|12
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"aaaaa"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"\t"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"\t"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"\tbbbbb"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"\t"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"\tccccc"
argument_list|,
name|StringUtil
operator|.
name|wrap
argument_list|(
literal|"aaaaa\n\nbbbbb\nccccc"
argument_list|,
literal|12
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"aaaaa"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"\t"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"\tbbbbb"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"\t"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"\tccccc"
argument_list|,
name|StringUtil
operator|.
name|wrap
argument_list|(
literal|"aaaaa\r\nbbbbb\r\nccccc"
argument_list|,
literal|12
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testUnquote ()
specifier|public
name|void
name|testUnquote
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"a:"
argument_list|,
name|StringUtil
operator|.
name|unquote
argument_list|(
literal|"a::"
argument_list|,
literal|':'
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"a:;"
argument_list|,
name|StringUtil
operator|.
name|unquote
argument_list|(
literal|"a:::;"
argument_list|,
literal|':'
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"a:b%c;"
argument_list|,
name|StringUtil
operator|.
name|unquote
argument_list|(
literal|"a::b:%c:;"
argument_list|,
literal|':'
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|field|stringArray1
specifier|private
specifier|static
specifier|final
name|String
index|[]
index|[]
name|stringArray1
init|=
block|{
block|{
literal|"a"
block|,
literal|"b"
block|}
block|,
block|{
literal|"c"
block|,
literal|"d"
block|}
block|}
decl_stmt|;
DECL|field|encStringArray1
specifier|private
specifier|static
specifier|final
name|String
name|encStringArray1
init|=
literal|"a:b;c:d"
decl_stmt|;
DECL|field|stringArray2null
specifier|private
specifier|static
specifier|final
name|String
index|[]
index|[]
name|stringArray2null
init|=
block|{
block|{
literal|"a"
block|,
literal|null
block|}
block|,
block|{
literal|"c"
block|,
literal|"d"
block|}
block|}
decl_stmt|;
DECL|field|encStringArray2
specifier|private
specifier|static
specifier|final
name|String
name|encStringArray2
init|=
literal|"a:;c:d"
decl_stmt|;
DECL|field|stringArray2
specifier|private
specifier|static
specifier|final
name|String
index|[]
index|[]
name|stringArray2
init|=
block|{
block|{
literal|"a"
block|,
literal|""
block|}
block|,
block|{
literal|"c"
block|,
literal|"d"
block|}
block|}
decl_stmt|;
DECL|field|encStringArray2null
specifier|private
specifier|static
specifier|final
name|String
name|encStringArray2null
init|=
literal|"a:"
operator|+
literal|null
operator|+
literal|";c:d"
decl_stmt|;
DECL|field|stringArray3
specifier|private
specifier|static
specifier|final
name|String
index|[]
index|[]
name|stringArray3
init|=
block|{
block|{
literal|"a"
block|,
literal|":b"
block|}
block|,
block|{
literal|"c;"
block|,
literal|"d"
block|}
block|}
decl_stmt|;
DECL|field|encStringArray3
specifier|private
specifier|static
specifier|final
name|String
name|encStringArray3
init|=
literal|"a:\\:b;c\\;:d"
decl_stmt|;
annotation|@
name|Test
DECL|method|testEncodeStringArray ()
specifier|public
name|void
name|testEncodeStringArray
parameter_list|()
block|{
name|assertEquals
argument_list|(
name|encStringArray1
argument_list|,
name|FileField
operator|.
name|encodeStringArray
argument_list|(
name|stringArray1
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|encStringArray2
argument_list|,
name|FileField
operator|.
name|encodeStringArray
argument_list|(
name|stringArray2
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|encStringArray2null
argument_list|,
name|FileField
operator|.
name|encodeStringArray
argument_list|(
name|stringArray2null
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|encStringArray3
argument_list|,
name|FileField
operator|.
name|encodeStringArray
argument_list|(
name|stringArray3
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testDecodeStringDoubleArray ()
specifier|public
name|void
name|testDecodeStringDoubleArray
parameter_list|()
block|{
name|assertArrayEquals
argument_list|(
name|stringArray1
argument_list|,
name|StringUtil
operator|.
name|decodeStringDoubleArray
argument_list|(
name|encStringArray1
argument_list|)
argument_list|)
expr_stmt|;
name|assertArrayEquals
argument_list|(
name|stringArray2
argument_list|,
name|StringUtil
operator|.
name|decodeStringDoubleArray
argument_list|(
name|encStringArray2
argument_list|)
argument_list|)
expr_stmt|;
comment|// arrays first differed at element [0][1]; expected: null<null> but was: java.lang.String<null>
comment|// assertArrayEquals(stringArray2res, StringUtil.decodeStringDoubleArray(encStringArray2));
name|assertArrayEquals
argument_list|(
name|stringArray3
argument_list|,
name|StringUtil
operator|.
name|decodeStringDoubleArray
argument_list|(
name|encStringArray3
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testBooleanToBinaryString ()
specifier|public
name|void
name|testBooleanToBinaryString
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"0"
argument_list|,
name|StringUtil
operator|.
name|booleanToBinaryString
argument_list|(
literal|false
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"1"
argument_list|,
name|StringUtil
operator|.
name|booleanToBinaryString
argument_list|(
literal|true
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testIsInCurlyBrackets ()
specifier|public
name|void
name|testIsInCurlyBrackets
parameter_list|()
block|{
name|assertFalse
argument_list|(
name|StringUtil
operator|.
name|isInCurlyBrackets
argument_list|(
literal|""
argument_list|)
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|StringUtil
operator|.
name|isInCurlyBrackets
argument_list|(
literal|null
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|StringUtil
operator|.
name|isInCurlyBrackets
argument_list|(
literal|"{}"
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|StringUtil
operator|.
name|isInCurlyBrackets
argument_list|(
literal|"{a}"
argument_list|)
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|StringUtil
operator|.
name|isInCurlyBrackets
argument_list|(
literal|"{"
argument_list|)
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|StringUtil
operator|.
name|isInCurlyBrackets
argument_list|(
literal|"}"
argument_list|)
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|StringUtil
operator|.
name|isInCurlyBrackets
argument_list|(
literal|"a{}a"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testIsInSquareBrackets ()
specifier|public
name|void
name|testIsInSquareBrackets
parameter_list|()
block|{
name|assertFalse
argument_list|(
name|StringUtil
operator|.
name|isInSquareBrackets
argument_list|(
literal|""
argument_list|)
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|StringUtil
operator|.
name|isInSquareBrackets
argument_list|(
literal|null
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|StringUtil
operator|.
name|isInSquareBrackets
argument_list|(
literal|"[]"
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|StringUtil
operator|.
name|isInSquareBrackets
argument_list|(
literal|"[a]"
argument_list|)
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|StringUtil
operator|.
name|isInSquareBrackets
argument_list|(
literal|"["
argument_list|)
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|StringUtil
operator|.
name|isInSquareBrackets
argument_list|(
literal|"]"
argument_list|)
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|StringUtil
operator|.
name|isInSquareBrackets
argument_list|(
literal|"a[]a"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testIsInCitationMarks ()
specifier|public
name|void
name|testIsInCitationMarks
parameter_list|()
block|{
name|assertFalse
argument_list|(
name|StringUtil
operator|.
name|isInCitationMarks
argument_list|(
literal|""
argument_list|)
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|StringUtil
operator|.
name|isInCitationMarks
argument_list|(
literal|null
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|StringUtil
operator|.
name|isInCitationMarks
argument_list|(
literal|"\"\""
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|StringUtil
operator|.
name|isInCitationMarks
argument_list|(
literal|"\"a\""
argument_list|)
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|StringUtil
operator|.
name|isInCitationMarks
argument_list|(
literal|"\""
argument_list|)
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|StringUtil
operator|.
name|isInCitationMarks
argument_list|(
literal|"a\"\"a"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testIntValueOfSingleDigit ()
specifier|public
name|void
name|testIntValueOfSingleDigit
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|StringUtil
operator|.
name|intValueOf
argument_list|(
literal|"1"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|2
argument_list|,
name|StringUtil
operator|.
name|intValueOf
argument_list|(
literal|"2"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|8
argument_list|,
name|StringUtil
operator|.
name|intValueOf
argument_list|(
literal|"8"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testIntValueOfLongString ()
specifier|public
name|void
name|testIntValueOfLongString
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|1234567890
argument_list|,
name|StringUtil
operator|.
name|intValueOf
argument_list|(
literal|"1234567890"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testIntValueOfStartWithZeros ()
specifier|public
name|void
name|testIntValueOfStartWithZeros
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|1234
argument_list|,
name|StringUtil
operator|.
name|intValueOf
argument_list|(
literal|"001234"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
argument_list|(
name|expected
operator|=
name|NumberFormatException
operator|.
name|class
argument_list|)
DECL|method|testIntValueOfExceptionIfStringContainsLetter ()
specifier|public
name|void
name|testIntValueOfExceptionIfStringContainsLetter
parameter_list|()
block|{
name|StringUtil
operator|.
name|intValueOf
argument_list|(
literal|"12A2"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
argument_list|(
name|expected
operator|=
name|NumberFormatException
operator|.
name|class
argument_list|)
DECL|method|testIntValueOfExceptionIfStringNull ()
specifier|public
name|void
name|testIntValueOfExceptionIfStringNull
parameter_list|()
block|{
name|StringUtil
operator|.
name|intValueOf
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
argument_list|(
name|expected
operator|=
name|NumberFormatException
operator|.
name|class
argument_list|)
DECL|method|testIntValueOfExceptionfIfStringEmpty ()
specifier|public
name|void
name|testIntValueOfExceptionfIfStringEmpty
parameter_list|()
block|{
name|StringUtil
operator|.
name|intValueOf
argument_list|(
literal|""
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testQuoteSimple ()
specifier|public
name|void
name|testQuoteSimple
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"a::"
argument_list|,
name|StringUtil
operator|.
name|quote
argument_list|(
literal|"a:"
argument_list|,
literal|""
argument_list|,
literal|':'
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testQuoteNullQuotation ()
specifier|public
name|void
name|testQuoteNullQuotation
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"a::"
argument_list|,
name|StringUtil
operator|.
name|quote
argument_list|(
literal|"a:"
argument_list|,
literal|null
argument_list|,
literal|':'
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testQuoteNullString ()
specifier|public
name|void
name|testQuoteNullString
parameter_list|()
block|{
name|assertNull
argument_list|(
name|StringUtil
operator|.
name|quote
argument_list|(
literal|null
argument_list|,
literal|";"
argument_list|,
literal|':'
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testQuoteQuotationCharacter ()
specifier|public
name|void
name|testQuoteQuotationCharacter
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"a:::;"
argument_list|,
name|StringUtil
operator|.
name|quote
argument_list|(
literal|"a:;"
argument_list|,
literal|";"
argument_list|,
literal|':'
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testQuoteMoreComplicated ()
specifier|public
name|void
name|testQuoteMoreComplicated
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"a::b:%c:;"
argument_list|,
name|StringUtil
operator|.
name|quote
argument_list|(
literal|"a:b%c;"
argument_list|,
literal|"%;"
argument_list|,
literal|':'
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

