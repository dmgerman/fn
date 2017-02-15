begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.model.strings
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|model
operator|.
name|strings
package|;
end_package

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
name|model
operator|.
name|entry
operator|.
name|FileField
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
name|assertArrayEquals
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
DECL|class|StringUtilTest
specifier|public
class|class
name|StringUtilTest
block|{
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
name|assertEquals
argument_list|(
literal|""
argument_list|,
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
DECL|field|STRING_ARRAY_1
specifier|private
specifier|static
specifier|final
name|String
index|[]
index|[]
name|STRING_ARRAY_1
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
DECL|field|ENCODED_STRING_ARRAY_1
specifier|private
specifier|static
specifier|final
name|String
name|ENCODED_STRING_ARRAY_1
init|=
literal|"a:b;c:d"
decl_stmt|;
DECL|field|STRING_ARRAY_2_WITH_NULL
specifier|private
specifier|static
specifier|final
name|String
index|[]
index|[]
name|STRING_ARRAY_2_WITH_NULL
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
DECL|field|ENCODED_STRING_ARRAY_2_WITH_NULL
specifier|private
specifier|static
specifier|final
name|String
name|ENCODED_STRING_ARRAY_2_WITH_NULL
init|=
literal|"a:"
operator|+
literal|null
operator|+
literal|";c:d"
decl_stmt|;
DECL|field|STRING_ARRAY_2
specifier|private
specifier|static
specifier|final
name|String
index|[]
index|[]
name|STRING_ARRAY_2
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
DECL|field|ENCODED_STRING_ARRAY_2
specifier|private
specifier|static
specifier|final
name|String
name|ENCODED_STRING_ARRAY_2
init|=
literal|"a:;c:d"
decl_stmt|;
DECL|field|STRING_ARRAY_3
specifier|private
specifier|static
specifier|final
name|String
index|[]
index|[]
name|STRING_ARRAY_3
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
DECL|field|ENCODED_STRING_ARRAY_3
specifier|private
specifier|static
specifier|final
name|String
name|ENCODED_STRING_ARRAY_3
init|=
literal|"a:\\:b;c\\;:d"
decl_stmt|;
annotation|@
name|Test
DECL|method|testUnifyLineBreaks ()
specifier|public
name|void
name|testUnifyLineBreaks
parameter_list|()
block|{
comment|// Mac< v9
name|String
name|result
init|=
name|StringUtil
operator|.
name|unifyLineBreaks
argument_list|(
literal|"\r"
argument_list|,
literal|"newline"
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|"newline"
argument_list|,
name|result
argument_list|)
expr_stmt|;
comment|// Windows
name|result
operator|=
name|StringUtil
operator|.
name|unifyLineBreaks
argument_list|(
literal|"\r\n"
argument_list|,
literal|"newline"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"newline"
argument_list|,
name|result
argument_list|)
expr_stmt|;
comment|// Unix
name|result
operator|=
name|StringUtil
operator|.
name|unifyLineBreaks
argument_list|(
literal|"\n"
argument_list|,
literal|"newline"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"newline"
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
name|String
name|newline
init|=
literal|"newline"
decl_stmt|;
name|assertEquals
argument_list|(
literal|"aaaaa"
operator|+
name|newline
operator|+
literal|"\tbbbbb"
operator|+
name|newline
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
argument_list|,
name|newline
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"aaaaa bbbbb"
operator|+
name|newline
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
argument_list|,
name|newline
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"aaaaa bbbbb"
operator|+
name|newline
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
argument_list|,
name|newline
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
argument_list|,
name|newline
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"aaaaa"
operator|+
name|newline
operator|+
literal|"\t"
operator|+
name|newline
operator|+
literal|"\tbbbbb"
operator|+
name|newline
operator|+
literal|"\t"
operator|+
name|newline
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
argument_list|,
name|newline
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"aaaaa"
operator|+
name|newline
operator|+
literal|"\t"
operator|+
name|newline
operator|+
literal|"\t"
operator|+
name|newline
operator|+
literal|"\tbbbbb"
operator|+
name|newline
operator|+
literal|"\t"
operator|+
name|newline
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
argument_list|,
name|newline
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"aaaaa"
operator|+
name|newline
operator|+
literal|"\t"
operator|+
name|newline
operator|+
literal|"\tbbbbb"
operator|+
name|newline
operator|+
literal|"\t"
operator|+
name|newline
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
argument_list|,
name|newline
argument_list|)
argument_list|)
expr_stmt|;
block|}
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
name|ENCODED_STRING_ARRAY_1
argument_list|,
name|FileField
operator|.
name|encodeStringArray
argument_list|(
name|STRING_ARRAY_1
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|ENCODED_STRING_ARRAY_2
argument_list|,
name|FileField
operator|.
name|encodeStringArray
argument_list|(
name|STRING_ARRAY_2
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|ENCODED_STRING_ARRAY_2_WITH_NULL
argument_list|,
name|FileField
operator|.
name|encodeStringArray
argument_list|(
name|STRING_ARRAY_2_WITH_NULL
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|ENCODED_STRING_ARRAY_3
argument_list|,
name|FileField
operator|.
name|encodeStringArray
argument_list|(
name|STRING_ARRAY_3
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
name|STRING_ARRAY_1
argument_list|,
name|StringUtil
operator|.
name|decodeStringDoubleArray
argument_list|(
name|ENCODED_STRING_ARRAY_1
argument_list|)
argument_list|)
expr_stmt|;
name|assertArrayEquals
argument_list|(
name|STRING_ARRAY_2
argument_list|,
name|StringUtil
operator|.
name|decodeStringDoubleArray
argument_list|(
name|ENCODED_STRING_ARRAY_2
argument_list|)
argument_list|)
expr_stmt|;
comment|// arrays first differed at element [0][1]; expected: null<null> but was: java.lang.String<null>
comment|// assertArrayEquals(stringArray2res, StringUtil.decodeStringDoubleArray(encStringArray2));
name|assertArrayEquals
argument_list|(
name|STRING_ARRAY_3
argument_list|,
name|StringUtil
operator|.
name|decodeStringDoubleArray
argument_list|(
name|ENCODED_STRING_ARRAY_3
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
name|assertTrue
argument_list|(
name|StringUtil
operator|.
name|isInCurlyBrackets
argument_list|(
literal|"{a{a}}"
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|StringUtil
operator|.
name|isInCurlyBrackets
argument_list|(
literal|"{{\\AA}sa {\\AA}Stor{\\aa}}"
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
name|assertFalse
argument_list|(
name|StringUtil
operator|.
name|isInCurlyBrackets
argument_list|(
literal|"{\\AA}sa {\\AA}Stor{\\aa}"
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
DECL|method|testIntValueOfWithNullSingleDigit ()
specifier|public
name|void
name|testIntValueOfWithNullSingleDigit
parameter_list|()
block|{
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Integer
operator|.
name|valueOf
argument_list|(
literal|1
argument_list|)
argument_list|)
argument_list|,
name|StringUtil
operator|.
name|intValueOfOptional
argument_list|(
literal|"1"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Integer
operator|.
name|valueOf
argument_list|(
literal|2
argument_list|)
argument_list|)
argument_list|,
name|StringUtil
operator|.
name|intValueOfOptional
argument_list|(
literal|"2"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Integer
operator|.
name|valueOf
argument_list|(
literal|8
argument_list|)
argument_list|)
argument_list|,
name|StringUtil
operator|.
name|intValueOfOptional
argument_list|(
literal|"8"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testIntValueOfWithNullLongString ()
specifier|public
name|void
name|testIntValueOfWithNullLongString
parameter_list|()
block|{
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Integer
operator|.
name|valueOf
argument_list|(
literal|1234567890
argument_list|)
argument_list|)
argument_list|,
name|StringUtil
operator|.
name|intValueOfOptional
argument_list|(
literal|"1234567890"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testIntValueOfWithNullStartWithZeros ()
specifier|public
name|void
name|testIntValueOfWithNullStartWithZeros
parameter_list|()
block|{
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Integer
operator|.
name|valueOf
argument_list|(
literal|1234
argument_list|)
argument_list|)
argument_list|,
name|StringUtil
operator|.
name|intValueOfOptional
argument_list|(
literal|"001234"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testIntValueOfWithNullExceptionIfStringContainsLetter ()
specifier|public
name|void
name|testIntValueOfWithNullExceptionIfStringContainsLetter
parameter_list|()
block|{
name|assertEquals
argument_list|(
name|Optional
operator|.
name|empty
argument_list|()
argument_list|,
name|StringUtil
operator|.
name|intValueOfOptional
argument_list|(
literal|"12A2"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testIntValueOfWithNullExceptionIfStringNull ()
specifier|public
name|void
name|testIntValueOfWithNullExceptionIfStringNull
parameter_list|()
block|{
name|assertEquals
argument_list|(
name|Optional
operator|.
name|empty
argument_list|()
argument_list|,
name|StringUtil
operator|.
name|intValueOfOptional
argument_list|(
literal|null
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testIntValueOfWithNullExceptionfIfStringEmpty ()
specifier|public
name|void
name|testIntValueOfWithNullExceptionfIfStringEmpty
parameter_list|()
block|{
name|assertEquals
argument_list|(
name|Optional
operator|.
name|empty
argument_list|()
argument_list|,
name|StringUtil
operator|.
name|intValueOfOptional
argument_list|(
literal|""
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testLimitStringLengthShort ()
specifier|public
name|void
name|testLimitStringLengthShort
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"Test"
argument_list|,
name|StringUtil
operator|.
name|limitStringLength
argument_list|(
literal|"Test"
argument_list|,
literal|20
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testLimitStringLengthLimiting ()
specifier|public
name|void
name|testLimitStringLengthLimiting
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"TestTes..."
argument_list|,
name|StringUtil
operator|.
name|limitStringLength
argument_list|(
literal|"TestTestTestTestTest"
argument_list|,
literal|10
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|10
argument_list|,
name|StringUtil
operator|.
name|limitStringLength
argument_list|(
literal|"TestTestTestTestTest"
argument_list|,
literal|10
argument_list|)
operator|.
name|length
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testLimitStringLengthNullInput ()
specifier|public
name|void
name|testLimitStringLengthNullInput
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|StringUtil
operator|.
name|limitStringLength
argument_list|(
literal|null
argument_list|,
literal|10
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testReplaceSpecialCharacters ()
specifier|public
name|void
name|testReplaceSpecialCharacters
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"Hallo Arger"
argument_list|,
name|StringUtil
operator|.
name|replaceSpecialCharacters
argument_list|(
literal|"Hallo Arger"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"aaAeoeeee"
argument_list|,
name|StringUtil
operator|.
name|replaceSpecialCharacters
argument_list|(
literal|"Ã¥ÃÃ¶Ã©Ã¨Ã«"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testRepeatSpaces ()
specifier|public
name|void
name|testRepeatSpaces
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|StringUtil
operator|.
name|repeatSpaces
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|" "
argument_list|,
name|StringUtil
operator|.
name|repeatSpaces
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"       "
argument_list|,
name|StringUtil
operator|.
name|repeatSpaces
argument_list|(
literal|7
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testRepeat ()
specifier|public
name|void
name|testRepeat
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|StringUtil
operator|.
name|repeat
argument_list|(
literal|0
argument_list|,
literal|'a'
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"a"
argument_list|,
name|StringUtil
operator|.
name|repeat
argument_list|(
literal|1
argument_list|,
literal|'a'
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"aaaaaaa"
argument_list|,
name|StringUtil
operator|.
name|repeat
argument_list|(
literal|7
argument_list|,
literal|'a'
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testBoldHTML ()
specifier|public
name|void
name|testBoldHTML
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"<b>AA</b>"
argument_list|,
name|StringUtil
operator|.
name|boldHTML
argument_list|(
literal|"AA"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testBoldHTMLReturnsOriginalTextIfNonNull ()
specifier|public
name|void
name|testBoldHTMLReturnsOriginalTextIfNonNull
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"<b>AA</b>"
argument_list|,
name|StringUtil
operator|.
name|boldHTML
argument_list|(
literal|"AA"
argument_list|,
literal|"BB"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testBoldHTMLReturnsAlternativeTextIfNull ()
specifier|public
name|void
name|testBoldHTMLReturnsAlternativeTextIfNull
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"<b>BB</b>"
argument_list|,
name|StringUtil
operator|.
name|boldHTML
argument_list|(
literal|null
argument_list|,
literal|"BB"
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
annotation|@
name|Test
DECL|method|testCapitalizeFirst ()
specifier|public
name|void
name|testCapitalizeFirst
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|StringUtil
operator|.
name|capitalizeFirst
argument_list|(
literal|""
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Hello world"
argument_list|,
name|StringUtil
operator|.
name|capitalizeFirst
argument_list|(
literal|"Hello World"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"A"
argument_list|,
name|StringUtil
operator|.
name|capitalizeFirst
argument_list|(
literal|"a"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Aa"
argument_list|,
name|StringUtil
operator|.
name|capitalizeFirst
argument_list|(
literal|"AA"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit
