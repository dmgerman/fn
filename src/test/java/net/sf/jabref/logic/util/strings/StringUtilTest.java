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
name|Assert
operator|.
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
name|Assert
operator|.
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
name|Assert
operator|.
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
name|Assert
operator|.
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
name|Assert
operator|.
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
name|Assert
operator|.
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
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|null
argument_list|,
name|StringUtil
operator|.
name|shaveString
argument_list|(
literal|null
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
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
name|Assert
operator|.
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
name|Assert
operator|.
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
name|Assert
operator|.
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
name|Assert
operator|.
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
name|Assert
operator|.
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
name|Assert
operator|.
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
name|Assert
operator|.
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
name|Assert
operator|.
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
name|Assert
operator|.
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
name|Assert
operator|.
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
name|Assert
operator|.
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
name|Assert
operator|.
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
name|Assert
operator|.
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
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"foo"
argument_list|,
name|StringUtil
operator|.
name|stripBrackets
argument_list|(
literal|"foo]"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"foo"
argument_list|,
name|StringUtil
operator|.
name|stripBrackets
argument_list|(
literal|"[foo"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
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
name|Assert
operator|.
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
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|StringUtil
operator|.
name|stripBrackets
argument_list|(
literal|"["
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|StringUtil
operator|.
name|stripBrackets
argument_list|(
literal|"]"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
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
try|try
block|{
name|StringUtil
operator|.
name|stripBrackets
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|fail
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NullPointerException
name|ignored
parameter_list|)
block|{
comment|// Ignored
block|}
block|}
block|}
end_class

end_unit

