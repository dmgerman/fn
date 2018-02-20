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

begin_class
DECL|class|RemoveBracketsAddCommaTest
specifier|public
class|class
name|RemoveBracketsAddCommaTest
block|{
DECL|field|formatter
specifier|private
name|LayoutFormatter
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
name|RemoveBracketsAddComma
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testFormat ()
specifier|public
name|void
name|testFormat
parameter_list|()
throws|throws
name|Exception
block|{
name|assertEquals
argument_list|(
literal|"some text,"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"{some text}"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"some text"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"{some text"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"some text,"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"some text}"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

