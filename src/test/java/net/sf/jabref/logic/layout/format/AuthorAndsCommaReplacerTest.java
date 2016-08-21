begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.layout.format
package|package
name|net
operator|.
name|sf
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
name|net
operator|.
name|sf
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

begin_class
DECL|class|AuthorAndsCommaReplacerTest
specifier|public
class|class
name|AuthorAndsCommaReplacerTest
block|{
comment|/**      * Test method for      * {@link net.sf.jabref.logic.layout.format.AuthorAndsCommaReplacer#format(java.lang.String)}.      */
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
name|AuthorAndsCommaReplacer
argument_list|()
decl_stmt|;
comment|// Empty case
name|Assert
operator|.
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
name|Assert
operator|.
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
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"John von Neumann& Peter Black Brown"
argument_list|,
name|a
operator|.
name|format
argument_list|(
literal|"John von Neumann and Peter Black Brown"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Three names put a comma:
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"von Neumann, John, Smith, John& Black Brown, Peter"
argument_list|,
name|a
operator|.
name|format
argument_list|(
literal|"von Neumann, John and Smith, John and Black Brown, Peter"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

