begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.layout.format
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|layout
operator|.
name|format
package|;
end_package

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
name|*
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

begin_class
DECL|class|AuthorFirstFirstCommasTest
specifier|public
class|class
name|AuthorFirstFirstCommasTest
block|{
comment|/**      * Test method for      * {@link org.jabref.logic.layout.format.AuthorFirstFirstCommas#format(java.lang.String)}.      */
annotation|@
name|Test
DECL|method|testFormat ()
specifier|public
name|void
name|testFormat
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"John von Neumann, John Smith and Peter Black Brown, Jr"
argument_list|,
operator|new
name|AuthorFirstFirstCommas
argument_list|()
operator|.
name|format
argument_list|(
literal|"von Neumann,,John and John Smith and Black Brown, Jr, Peter"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

