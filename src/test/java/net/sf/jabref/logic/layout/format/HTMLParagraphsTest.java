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
DECL|class|HTMLParagraphsTest
specifier|public
class|class
name|HTMLParagraphsTest
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
name|HTMLParagraphs
argument_list|()
decl_stmt|;
name|Assert
operator|.
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
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"<p>\nHello\n</p>"
argument_list|,
name|f
operator|.
name|format
argument_list|(
literal|"Hello"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"<p>\nHello\nWorld\n</p>"
argument_list|,
name|f
operator|.
name|format
argument_list|(
literal|"Hello\nWorld"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"<p>\nHello World\n</p>\n<p>\nWhat a lovely day\n</p>"
argument_list|,
name|f
operator|.
name|format
argument_list|(
literal|"Hello World\n   \nWhat a lovely day\n"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"<p>\nHello World\n</p>\n<p>\nCould not be any better\n</p>\n<p>\nWhat a lovely day\n</p>"
argument_list|,
name|f
operator|.
name|format
argument_list|(
literal|"Hello World\n \n\nCould not be any better\n\nWhat a lovely day\n"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

