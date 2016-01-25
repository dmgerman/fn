begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.exporter.layout.format
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|exporter
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
name|exporter
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
name|exporter
operator|.
name|layout
operator|.
name|format
operator|.
name|RemoveBrackets
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Before
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

begin_class
DECL|class|RemoveBracketsTest
specifier|public
class|class
name|RemoveBracketsTest
block|{
DECL|field|formatter
specifier|private
name|LayoutFormatter
name|formatter
decl_stmt|;
annotation|@
name|Before
DECL|method|setup ()
specifier|public
name|void
name|setup
parameter_list|()
block|{
name|formatter
operator|=
operator|new
name|RemoveBrackets
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
literal|"some text"
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
literal|"some text"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"some text}"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"\\some text\\"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\{some text\\}"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

