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
name|preferences
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
DECL|class|UnicodeConverterTest
specifier|public
class|class
name|UnicodeConverterTest
block|{
DECL|field|formatter
specifier|private
name|UnicodeToLatexFormatter
name|formatter
decl_stmt|;
annotation|@
name|BeforeClass
DECL|method|setUpBeforeClass ()
specifier|public
specifier|static
name|void
name|setUpBeforeClass
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
name|UnicodeToLatexFormatter
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
DECL|method|testUnicodeCombiningAccents ()
specifier|public
name|void
name|testUnicodeCombiningAccents
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
literal|"a\u0308"
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
literal|"a\u0308b"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testUnicode ()
specifier|public
name|void
name|testUnicode
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
literal|"Ã¤"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"{{$\\Epsilon$}}"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\u0395"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testUnicodeSingle ()
specifier|public
name|void
name|testUnicodeSingle
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"a"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"a"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

