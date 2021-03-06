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
DECL|class|SentenceCaseFormatterTest
specifier|public
class|class
name|SentenceCaseFormatterTest
block|{
DECL|field|formatter
specifier|private
name|SentenceCaseFormatter
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
name|SentenceCaseFormatter
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|test ()
specifier|public
name|void
name|test
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"Upper first"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"upper First"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Upper first"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"uPPER FIRST"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Upper {NOT} first"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"upper {NOT} FIRST"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Upper {N}ot first"
argument_list|,
name|formatter
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
DECL|method|formatExample ()
specifier|public
name|void
name|formatExample
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"I have {Aa} dream"
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

