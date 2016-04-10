begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.formatter
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
package|;
end_package

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
name|Test
import|;
end_import

begin_comment
comment|/**  * Tests in addition to the general tests from {@link net.sf.jabref.logic.formatter.FormatterTest}  */
end_comment

begin_class
DECL|class|CaseChangersTest
specifier|public
class|class
name|CaseChangersTest
block|{
annotation|@
name|Test
DECL|method|testChangeCaseLower ()
specifier|public
name|void
name|testChangeCaseLower
parameter_list|()
block|{
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"lower"
argument_list|,
name|CaseChangers
operator|.
name|TO_LOWER_CASE
operator|.
name|format
argument_list|(
literal|"LOWER"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"lower {UPPER}"
argument_list|,
name|CaseChangers
operator|.
name|TO_LOWER_CASE
operator|.
name|format
argument_list|(
literal|"LOWER {UPPER}"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"lower {U}pper"
argument_list|,
name|CaseChangers
operator|.
name|TO_LOWER_CASE
operator|.
name|format
argument_list|(
literal|"LOWER {U}PPER"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testChangeCaseUpper ()
specifier|public
name|void
name|testChangeCaseUpper
parameter_list|()
block|{
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"LOWER"
argument_list|,
name|CaseChangers
operator|.
name|TO_UPPER_CASE
operator|.
name|format
argument_list|(
literal|"LOWER"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"UPPER"
argument_list|,
name|CaseChangers
operator|.
name|TO_UPPER_CASE
operator|.
name|format
argument_list|(
literal|"upper"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"UPPER"
argument_list|,
name|CaseChangers
operator|.
name|TO_UPPER_CASE
operator|.
name|format
argument_list|(
literal|"UPPER"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"UPPER {lower}"
argument_list|,
name|CaseChangers
operator|.
name|TO_UPPER_CASE
operator|.
name|format
argument_list|(
literal|"upper {lower}"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"UPPER {l}OWER"
argument_list|,
name|CaseChangers
operator|.
name|TO_UPPER_CASE
operator|.
name|format
argument_list|(
literal|"upper {l}ower"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testChangeCaseUpperFirst ()
specifier|public
name|void
name|testChangeCaseUpperFirst
parameter_list|()
block|{
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Upper first"
argument_list|,
name|CaseChangers
operator|.
name|TO_SENTENCE_CASE
operator|.
name|format
argument_list|(
literal|"upper First"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Upper first"
argument_list|,
name|CaseChangers
operator|.
name|TO_SENTENCE_CASE
operator|.
name|format
argument_list|(
literal|"uPPER FIRST"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Upper {NOT} first"
argument_list|,
name|CaseChangers
operator|.
name|TO_SENTENCE_CASE
operator|.
name|format
argument_list|(
literal|"upper {NOT} FIRST"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Upper {N}ot first"
argument_list|,
name|CaseChangers
operator|.
name|TO_SENTENCE_CASE
operator|.
name|format
argument_list|(
literal|"upper {N}OT FIRST"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testChangeCaseUpperEachFirst ()
specifier|public
name|void
name|testChangeCaseUpperEachFirst
parameter_list|()
block|{
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Upper Each First"
argument_list|,
name|CaseChangers
operator|.
name|CAPITALIZE
operator|.
name|format
argument_list|(
literal|"upper each First"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Upper Each First {NOT} {this}"
argument_list|,
name|CaseChangers
operator|.
name|CAPITALIZE
operator|.
name|format
argument_list|(
literal|"upper each first {NOT} {this}"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Upper Each First {N}ot {t}his"
argument_list|,
name|CaseChangers
operator|.
name|CAPITALIZE
operator|.
name|format
argument_list|(
literal|"upper each first {N}OT {t}his"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testChangeCaseTitle ()
specifier|public
name|void
name|testChangeCaseTitle
parameter_list|()
block|{
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Upper Each First"
argument_list|,
name|CaseChangers
operator|.
name|TO_TITLE_CASE
operator|.
name|format
argument_list|(
literal|"upper each first"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"An Upper Each First And"
argument_list|,
name|CaseChangers
operator|.
name|TO_TITLE_CASE
operator|.
name|format
argument_list|(
literal|"an upper each first and"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"An Upper Each of the and First And"
argument_list|,
name|CaseChangers
operator|.
name|TO_TITLE_CASE
operator|.
name|format
argument_list|(
literal|"an upper each of the and first and"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"An Upper Each of: The and First And"
argument_list|,
name|CaseChangers
operator|.
name|TO_TITLE_CASE
operator|.
name|format
argument_list|(
literal|"an upper each of: the and first and"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"An Upper First with and without {CURLY} {brackets}"
argument_list|,
name|CaseChangers
operator|.
name|TO_TITLE_CASE
operator|.
name|format
argument_list|(
literal|"AN UPPER FIRST WITH AND WITHOUT {CURLY} {brackets}"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"An Upper First with {A}nd without {C}urly {b}rackets"
argument_list|,
name|CaseChangers
operator|.
name|TO_TITLE_CASE
operator|.
name|format
argument_list|(
literal|"AN UPPER FIRST WITH {A}ND WITHOUT {C}URLY {b}rackets"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

