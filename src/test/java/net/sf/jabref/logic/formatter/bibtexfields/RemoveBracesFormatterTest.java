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
name|*
import|;
end_import

begin_comment
comment|/**  * Tests in addition to the general tests from {@link net.sf.jabref.logic.formatter.FormatterTest}  */
end_comment

begin_class
DECL|class|RemoveBracesFormatterTest
specifier|public
class|class
name|RemoveBracesFormatterTest
block|{
DECL|field|formatter
specifier|private
specifier|final
name|RemoveBracesFormatter
name|formatter
init|=
operator|new
name|RemoveBracesFormatter
argument_list|()
decl_stmt|;
annotation|@
name|Test
DECL|method|formatRemovesSingleEnclosingBraces ()
specifier|public
name|void
name|formatRemovesSingleEnclosingBraces
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"test"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"{test}"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|formatKeepsUnmatchedBracesAtBeginning ()
specifier|public
name|void
name|formatKeepsUnmatchedBracesAtBeginning
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"{test"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"{test"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|formatKeepsUnmatchedBracesAtEnd ()
specifier|public
name|void
name|formatKeepsUnmatchedBracesAtEnd
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"test}"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"test}"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|formatKeepsShortString ()
specifier|public
name|void
name|formatKeepsShortString
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"t"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"t"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|formatKeepsEmptyString ()
specifier|public
name|void
name|formatKeepsEmptyString
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|""
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|formatRemovesDoubleEnclosingBraces ()
specifier|public
name|void
name|formatRemovesDoubleEnclosingBraces
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"test"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"{{test}}"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|formatRemovesTripleEnclosingBraces ()
specifier|public
name|void
name|formatRemovesTripleEnclosingBraces
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"test"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"{{{test}}}"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|formatKeepsNonMatchingBraces ()
specifier|public
name|void
name|formatKeepsNonMatchingBraces
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"{A} and {B}"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"{A} and {B}"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|formatRemovesOnlyMatchingBraces ()
specifier|public
name|void
name|formatRemovesOnlyMatchingBraces
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"{A} and {B}"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"{{A} and {B}}"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|formatDoesNotRemoveBracesInBrokenString ()
specifier|public
name|void
name|formatDoesNotRemoveBracesInBrokenString
parameter_list|()
block|{
comment|// We opt here for a conservative approach although one could argue that "A} and {B}" is also a valid return
name|assertEquals
argument_list|(
literal|"{A} and {B}}"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"{A} and {B}}"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

