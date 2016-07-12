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

begin_class
DECL|class|HtmlToUnicodeFormatterTest
specifier|public
class|class
name|HtmlToUnicodeFormatterTest
block|{
DECL|field|formatter
specifier|private
name|HtmlToUnicodeFormatter
name|formatter
decl_stmt|;
annotation|@
name|Before
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
block|{
name|formatter
operator|=
operator|new
name|HtmlToUnicodeFormatter
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
literal|"Ã¥Ã¤Ã¶"
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
literal|"iÌ"
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
DECL|method|testUmlauts ()
specifier|public
name|void
name|testUmlauts
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"Ã¤"
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
literal|"Ã¤"
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
literal|"Ã¤"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"&#xe4;"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGreekLetter ()
specifier|public
name|void
name|testGreekLetter
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"Î"
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
literal|"<p>aaa</p>"
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
literal|"bread& butter"
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

