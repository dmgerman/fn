begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.export.layout.format
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|export
operator|.
name|layout
operator|.
name|format
package|;
end_package

begin_import
import|import
name|junit
operator|.
name|framework
operator|.
name|TestCase
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|export
operator|.
name|layout
operator|.
name|LayoutFormatter
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|export
operator|.
name|layout
operator|.
name|format
operator|.
name|NoSpaceBetweenAbbreviations
import|;
end_import

begin_class
DECL|class|NoSpaceBetweenAbbreviationsTest
specifier|public
class|class
name|NoSpaceBetweenAbbreviationsTest
extends|extends
name|TestCase
block|{
DECL|method|setUp ()
specifier|protected
name|void
name|setUp
parameter_list|()
throws|throws
name|Exception
block|{
name|super
operator|.
name|setUp
argument_list|()
expr_stmt|;
block|}
DECL|method|tearDown ()
specifier|protected
name|void
name|tearDown
parameter_list|()
throws|throws
name|Exception
block|{
name|super
operator|.
name|tearDown
argument_list|()
expr_stmt|;
block|}
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

