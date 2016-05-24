begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.formatter.bibtexfields
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|formatter
operator|.
name|bibtexfields
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

begin_comment
comment|/**  * Tests in addition to the general tests from {@link net.sf.jabref.logic.formatter.FormatterTest}  */
end_comment

begin_class
DECL|class|HtmlToLatexFormatterTest
specifier|public
class|class
name|HtmlToLatexFormatterTest
block|{
DECL|field|formatter
specifier|private
specifier|final
name|HtmlToLatexFormatter
name|formatter
init|=
operator|new
name|HtmlToLatexFormatter
argument_list|()
decl_stmt|;
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
name|Test
DECL|method|formatWithoutHtmlCharactersReturnsSameString ()
specifier|public
name|void
name|formatWithoutHtmlCharactersReturnsSameString
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"abc"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"abc"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|formatMultipleHtmlCharacters ()
specifier|public
name|void
name|formatMultipleHtmlCharacters
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"{{\\aa}}{\\\"{a}}{\\\"{o}}"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"&aring;&auml;&ouml;"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|formatCombinedAccent ()
specifier|public
name|void
name|formatCombinedAccent
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"{\\'{\\i}}"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"i&#x301;"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testBasic ()
specifier|public
name|void
name|testBasic
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"aaa"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"aaa"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testHTML ()
specifier|public
name|void
name|testHTML
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"{\\\"{a}}"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"&auml;"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"{\\\"{a}}"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"&#228;"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"{\\\"{a}}"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"&#xe4;"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"{$\\Epsilon$}"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"&Epsilon;"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testHTMLRemoveTags ()
specifier|public
name|void
name|testHTMLRemoveTags
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"aaa"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"<b>aaa</b>"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testHTMLCombiningAccents ()
specifier|public
name|void
name|testHTMLCombiningAccents
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"{\\\"{a}}"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"a&#776;"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"{\\\"{a}}"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"a&#x308;"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"{\\\"{a}}b"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"a&#776;b"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"{\\\"{a}}b"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"a&#x308;b"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|formatExample ()
specifier|public
name|void
name|formatExample
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"JabRef"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
name|formatter
operator|.
name|getExampleInput
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

