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

begin_class
DECL|class|NoSpaceBetweenAbbreviationsTest
specifier|public
class|class
name|NoSpaceBetweenAbbreviationsTest
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
name|f
init|=
operator|new
name|NoSpaceBetweenAbbreviations
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|f
operator|.
name|format
argument_list|(
literal|""
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"John Meier"
argument_list|,
name|f
operator|.
name|format
argument_list|(
literal|"John Meier"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"J.F. Kennedy"
argument_list|,
name|f
operator|.
name|format
argument_list|(
literal|"J. F. Kennedy"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"J.R.R. Tolkien"
argument_list|,
name|f
operator|.
name|format
argument_list|(
literal|"J. R. R. Tolkien"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"J.R.R. Tolkien and J.F. Kennedy"
argument_list|,
name|f
operator|.
name|format
argument_list|(
literal|"J. R. R. Tolkien and J. F. Kennedy"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

