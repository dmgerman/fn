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
DECL|class|RegexFormatterTest
class|class
name|RegexFormatterTest
block|{
DECL|field|formatter
specifier|private
name|RegexFormatter
name|formatter
decl_stmt|;
annotation|@
name|Test
DECL|method|spacesReplacedCorrectly ()
name|void
name|spacesReplacedCorrectly
parameter_list|()
block|{
name|formatter
operator|=
operator|new
name|RegexFormatter
argument_list|(
literal|"(\" \",\"-\")"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"replace-all-spaces"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"replace all spaces"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|protectedSpacesNotReplacedInSingleProtectedBlock ()
name|void
name|protectedSpacesNotReplacedInSingleProtectedBlock
parameter_list|()
block|{
name|formatter
operator|=
operator|new
name|RegexFormatter
argument_list|(
literal|"(\" \",\"-\")"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"replace-spaces-{not these ones}"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"replace spaces {not these ones}"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|protectedSpacesNotReplacedInTwoProtectedBlocks ()
name|void
name|protectedSpacesNotReplacedInTwoProtectedBlocks
parameter_list|()
block|{
name|formatter
operator|=
operator|new
name|RegexFormatter
argument_list|(
literal|"(\" \",\"-\")"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"replace-spaces-{not these ones}-{or these ones}-but-these-ones"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"replace spaces {not these ones} {or these ones} but these ones"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|escapedBracesAreNotReplaced ()
name|void
name|escapedBracesAreNotReplaced
parameter_list|()
block|{
name|formatter
operator|=
operator|new
name|RegexFormatter
argument_list|(
literal|"(\" \",\"-\")"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"replace-spaces-\\{-these-ones\\}-and-these-ones"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"replace spaces \\{ these ones\\} and these ones"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|escapedBracesAreNotReplacedInTwoCases ()
name|void
name|escapedBracesAreNotReplacedInTwoCases
parameter_list|()
block|{
name|formatter
operator|=
operator|new
name|RegexFormatter
argument_list|(
literal|"(\" \",\"-\")"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"replace-spaces-\\{-these-ones\\},-these-ones,-and-\\{-these-ones\\}"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"replace spaces \\{ these ones\\}, these ones, and \\{ these ones\\}"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|escapedBracesAreNotReplacedAndProtectionStillWorks ()
name|void
name|escapedBracesAreNotReplacedAndProtectionStillWorks
parameter_list|()
block|{
name|formatter
operator|=
operator|new
name|RegexFormatter
argument_list|(
literal|"(\" \",\"-\")"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"replace-spaces-{not these ones},-these-ones,-and-\\{-these-ones\\}"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"replace spaces {not these ones}, these ones, and \\{ these ones\\}"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|formatExample ()
name|void
name|formatExample
parameter_list|()
block|{
name|formatter
operator|=
operator|new
name|RegexFormatter
argument_list|(
literal|"(\" \",\"-\")"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Please-replace-the-spaces"
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

