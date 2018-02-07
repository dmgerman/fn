begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.formatter.minifier
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|formatter
operator|.
name|minifier
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
DECL|class|MinifyNameListFormatterTest
specifier|public
class|class
name|MinifyNameListFormatterTest
block|{
DECL|field|formatter
specifier|private
name|MinifyNameListFormatter
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
name|MinifyNameListFormatter
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|minifyAuthorNames ()
specifier|public
name|void
name|minifyAuthorNames
parameter_list|()
block|{
name|expectCorrect
argument_list|(
literal|"Simon Harrer"
argument_list|,
literal|"Simon Harrer"
argument_list|)
expr_stmt|;
name|expectCorrect
argument_list|(
literal|"Simon Harrer and others"
argument_list|,
literal|"Simon Harrer and others"
argument_list|)
expr_stmt|;
name|expectCorrect
argument_list|(
literal|"Simon Harrer and JÃ¶rg Lenhard"
argument_list|,
literal|"Simon Harrer and JÃ¶rg Lenhard"
argument_list|)
expr_stmt|;
name|expectCorrect
argument_list|(
literal|"Simon Harrer and JÃ¶rg Lenhard and Guido Wirtz"
argument_list|,
literal|"Simon Harrer and others"
argument_list|)
expr_stmt|;
name|expectCorrect
argument_list|(
literal|"Simon Harrer and JÃ¶rg Lenhard and Guido Wirtz and others"
argument_list|,
literal|"Simon Harrer and others"
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
name|expectCorrect
argument_list|(
name|formatter
operator|.
name|getExampleInput
argument_list|()
argument_list|,
literal|"Stefan Kolb and others"
argument_list|)
expr_stmt|;
block|}
DECL|method|expectCorrect (String input, String expected)
specifier|private
name|void
name|expectCorrect
parameter_list|(
name|String
name|input
parameter_list|,
name|String
name|expected
parameter_list|)
block|{
name|assertEquals
argument_list|(
name|expected
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
name|input
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

