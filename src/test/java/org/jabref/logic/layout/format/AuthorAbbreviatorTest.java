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

begin_comment
comment|/**  * Is the save as the AuthorLastFirstAbbreviator.  */
end_comment

begin_class
DECL|class|AuthorAbbreviatorTest
specifier|public
class|class
name|AuthorAbbreviatorTest
block|{
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
name|AuthorLastFirstAbbreviator
argument_list|()
decl_stmt|;
name|LayoutFormatter
name|b
init|=
operator|new
name|AuthorAbbreviator
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
name|b
operator|.
name|format
argument_list|(
literal|""
argument_list|)
argument_list|,
name|a
operator|.
name|format
argument_list|(
literal|""
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|b
operator|.
name|format
argument_list|(
literal|"Someone, Van Something"
argument_list|)
argument_list|,
name|a
operator|.
name|format
argument_list|(
literal|"Someone, Van Something"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|b
operator|.
name|format
argument_list|(
literal|"Smith, John"
argument_list|)
argument_list|,
name|a
operator|.
name|format
argument_list|(
literal|"Smith, John"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|b
operator|.
name|format
argument_list|(
literal|"von Neumann, John and Smith, John and Black Brown, Peter"
argument_list|)
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

