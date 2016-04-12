begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.formatter.casechanger
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
name|casechanger
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
DECL|class|TitleCaseFormatterTest
specifier|public
class|class
name|TitleCaseFormatterTest
block|{
DECL|field|formatter
specifier|private
specifier|final
name|TitleCaseFormatter
name|formatter
init|=
operator|new
name|TitleCaseFormatter
argument_list|()
decl_stmt|;
annotation|@
name|Test
DECL|method|test ()
specifier|public
name|void
name|test
parameter_list|()
block|{
name|Assert
operator|.
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
name|Assert
operator|.
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
name|Assert
operator|.
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
name|Assert
operator|.
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
name|Assert
operator|.
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
name|Assert
operator|.
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
DECL|method|formatExample ()
specifier|public
name|void
name|formatExample
parameter_list|()
block|{
name|Assert
operator|.
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

