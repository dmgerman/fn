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
name|jupiter
operator|.
name|api
operator|.
name|BeforeEach
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

begin_comment
comment|/**  * Tests in addition to the general tests from {@link org.jabref.logic.formatter.FormatterTest}  */
end_comment

begin_class
DECL|class|NormalizePagesFormatterTest
specifier|public
class|class
name|NormalizePagesFormatterTest
block|{
DECL|field|formatter
specifier|private
name|NormalizePagesFormatter
name|formatter
decl_stmt|;
annotation|@
name|BeforeEach
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
block|{
name|formatter
operator|=
operator|new
name|NormalizePagesFormatter
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|formatSinglePageResultsInNoChange ()
specifier|public
name|void
name|formatSinglePageResultsInNoChange
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"1"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"1"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|formatPageNumbers ()
specifier|public
name|void
name|formatPageNumbers
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"1--2"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"1-2"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|formatPageNumbersCommaSeparated ()
specifier|public
name|void
name|formatPageNumbersCommaSeparated
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"1,2,3"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"1,2,3"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|formatPageNumbersPlusRange ()
specifier|public
name|void
name|formatPageNumbersPlusRange
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"43+"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"43+"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|ignoreWhitespaceInPageNumbers ()
specifier|public
name|void
name|ignoreWhitespaceInPageNumbers
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"1--2"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"   1  - 2 "
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|removeWhitespaceSinglePage ()
specifier|public
name|void
name|removeWhitespaceSinglePage
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"1"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"   1  "
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|removeWhitespacePageRange ()
specifier|public
name|void
name|removeWhitespacePageRange
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"1--2"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"   1 -- 2  "
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|ignoreWhitespaceInPageNumbersWithDoubleDash ()
specifier|public
name|void
name|ignoreWhitespaceInPageNumbersWithDoubleDash
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"43--103"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"43 -- 103"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|keepCorrectlyFormattedPageNumbers ()
specifier|public
name|void
name|keepCorrectlyFormattedPageNumbers
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"1--2"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"1--2"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|formatPageNumbersRemoveUnexpectedLiterals ()
specifier|public
name|void
name|formatPageNumbersRemoveUnexpectedLiterals
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"1--2"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"{1}-{2}"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|formatPageNumbersRegexNotMatching ()
specifier|public
name|void
name|formatPageNumbersRegexNotMatching
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"12"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"12"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|doNotRemoveLetters ()
specifier|public
name|void
name|doNotRemoveLetters
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"R1-R50"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"R1-R50"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|replaceLongDashWithDoubleDash ()
specifier|public
name|void
name|replaceLongDashWithDoubleDash
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"1--50"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"1 \u2014 50"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|removePagePrefix ()
specifier|public
name|void
name|removePagePrefix
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"50"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"p.50"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|removePagesPrefix ()
specifier|public
name|void
name|removePagesPrefix
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"50"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"pp.50"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|formatACMPages ()
specifier|public
name|void
name|formatACMPages
parameter_list|()
block|{
comment|// This appears in https://doi.org/10.1145/1658373.1658375
name|assertEquals
argument_list|(
literal|"2:1--2:33"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"2:1-2:33"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|keepFormattedACMPages ()
specifier|public
name|void
name|keepFormattedACMPages
parameter_list|()
block|{
comment|// This appears in https://doi.org/10.1145/1658373.1658375
name|assertEquals
argument_list|(
literal|"2:1--2:33"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"2:1--2:33"
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
literal|"1--2"
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

