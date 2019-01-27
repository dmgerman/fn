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
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|layout
operator|.
name|LayoutFormatter
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

begin_class
DECL|class|AuthorAndsReplacerTest
specifier|public
class|class
name|AuthorAndsReplacerTest
block|{
comment|/**      * Test method for {@link org.jabref.logic.layout.format.AuthorAndsReplacer#format(java.lang.String)}.      */
annotation|@
name|Test
DECL|method|testFormat ()
specifier|public
name|void
name|testFormat
parameter_list|()
block|{
name|LayoutFormatter
name|a
init|=
operator|new
name|AuthorAndsReplacer
argument_list|()
decl_stmt|;
comment|// Empty case
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|a
operator|.
name|format
argument_list|(
literal|""
argument_list|)
argument_list|)
expr_stmt|;
comment|// Single Names don't change
name|assertEquals
argument_list|(
literal|"Someone, Van Something"
argument_list|,
name|a
operator|.
name|format
argument_list|(
literal|"Someone, Van Something"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Two names just an&
name|assertEquals
argument_list|(
literal|"John Smith& Black Brown, Peter"
argument_list|,
name|a
operator|.
name|format
argument_list|(
literal|"John Smith and Black Brown, Peter"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Three names put a comma:
name|assertEquals
argument_list|(
literal|"von Neumann, John; Smith, John& Black Brown, Peter"
argument_list|,
name|a
operator|.
name|format
argument_list|(
literal|"von Neumann, John and Smith, John and Black Brown, Peter"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"John von Neumann; John Smith& Peter Black Brown"
argument_list|,
name|a
operator|.
name|format
argument_list|(
literal|"John von Neumann and John Smith and Peter Black Brown"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

