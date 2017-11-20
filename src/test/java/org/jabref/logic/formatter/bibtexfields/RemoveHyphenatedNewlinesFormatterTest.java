begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.formatter.bibtexfields
package|package
name|org
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
DECL|class|RemoveHyphenatedNewlinesFormatterTest
specifier|public
class|class
name|RemoveHyphenatedNewlinesFormatterTest
block|{
DECL|field|formatter
specifier|private
name|RemoveHyphenatedNewlinesFormatter
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
name|RemoveHyphenatedNewlinesFormatter
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|removeHyphensBeforeNewlines ()
specifier|public
name|void
name|removeHyphensBeforeNewlines
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"water"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"wa-\nter"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"water"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"wa-\r\nter"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"water"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"wa-\rter"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

