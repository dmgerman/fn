begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.formatter.casechanger
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|formatter
operator|.
name|casechanger
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
DECL|class|TitleCaseFormatterTest
specifier|public
class|class
name|TitleCaseFormatterTest
block|{
DECL|field|formatter
specifier|private
name|TitleCaseFormatter
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
name|TitleCaseFormatter
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|eachFirstLetterIsUppercased ()
specifier|public
name|void
name|eachFirstLetterIsUppercased
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"Upper Each First"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"upper each first"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|eachFirstLetterIsUppercasedAndOthersLowercased ()
specifier|public
name|void
name|eachFirstLetterIsUppercasedAndOthersLowercased
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"Upper Each First"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"upper eACH first"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|eachFirstLetterIsUppercasedAndATralingAndIsAlsoUppercased ()
specifier|public
name|void
name|eachFirstLetterIsUppercasedAndATralingAndIsAlsoUppercased
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"An Upper Each First And"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"an upper each first and"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|eachFirstLetterIsUppercasedAndATralingAndIsAlsoCorrectlyCased ()
specifier|public
name|void
name|eachFirstLetterIsUppercasedAndATralingAndIsAlsoCorrectlyCased
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"An Upper Each First And"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"an upper each first AND"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|eachFirstLetterIsUppercasedButIntermediateAndsAreKeptLowercase ()
specifier|public
name|void
name|eachFirstLetterIsUppercasedButIntermediateAndsAreKeptLowercase
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"An Upper Each of the and First And"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"an upper each of the and first and"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|eachFirstLetterIsUppercasedButIntermediateAndsArePutLowercase ()
specifier|public
name|void
name|eachFirstLetterIsUppercasedButIntermediateAndsArePutLowercase
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"An Upper Each of the and First And"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"an upper each of the AND first and"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|theAfterColonGetsCapitalized ()
specifier|public
name|void
name|theAfterColonGetsCapitalized
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"An Upper Each of: The and First And"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"an upper each of: the and first and"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|completeWordsInCurlyBracketsIsLeftUnchanged ()
specifier|public
name|void
name|completeWordsInCurlyBracketsIsLeftUnchanged
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"An Upper First with and without {CURLY} {brackets}"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"AN UPPER FIRST WITH AND WITHOUT {CURLY} {brackets}"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|lettersInCurlyBracketsIsLeftUnchanged ()
specifier|public
name|void
name|lettersInCurlyBracketsIsLeftUnchanged
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"An Upper First with {A}nd without {C}urly {b}rackets"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"AN UPPER FIRST WITH {A}ND WITHOUT {C}URLY {b}rackets"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|intraWordLettersInCurlyBracketsIsLeftUnchanged ()
specifier|public
name|void
name|intraWordLettersInCurlyBracketsIsLeftUnchanged
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"{b}rackets {b}rac{K}ets Brack{E}ts"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"{b}RaCKeTS {b}RaC{K}eTS bRaCK{E}ts"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testTwoExperiencesTitle ()
specifier|public
name|void
name|testTwoExperiencesTitle
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"Two Experiences Designing for Effective Security"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"Two experiences designing for effective security"
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
literal|"{BPMN} Conformance in Open Source Engines"
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

