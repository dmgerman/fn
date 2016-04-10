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
name|Test
import|;
end_import

begin_comment
comment|/**  * Tests in addition to the general tests from {@link net.sf.jabref.logic.formatter.FormatterTest}  */
end_comment

begin_class
DECL|class|UnitsToLatexFormatterTest
specifier|public
class|class
name|UnitsToLatexFormatterTest
block|{
DECL|field|formatter
specifier|private
specifier|final
name|UnitsToLatexFormatter
name|formatter
init|=
operator|new
name|UnitsToLatexFormatter
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
name|assertEquals
argument_list|(
literal|"1~{A}"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"1 A"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"1\\mbox{-}{mA}"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"1-mA"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

