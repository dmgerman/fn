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
name|JabRefPreferences
import|;
end_import

begin_class
DECL|class|HTMLConverterTest
specifier|public
class|class
name|HTMLConverterTest
block|{
DECL|field|conv
specifier|private
specifier|final
name|HTMLToLatexFormatter
name|conv
init|=
operator|new
name|HTMLToLatexFormatter
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
name|conv
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
DECL|method|testHTMLEmpty ()
specifier|public
name|void
name|testHTMLEmpty
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|conv
operator|.
name|format
argument_list|(
literal|""
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
name|conv
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
name|conv
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
name|conv
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
name|conv
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
name|conv
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
name|conv
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
name|conv
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
name|conv
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
name|conv
operator|.
name|format
argument_list|(
literal|"a&#x308;b"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

