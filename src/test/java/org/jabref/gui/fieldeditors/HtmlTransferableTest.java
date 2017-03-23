begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.fieldeditors
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|fieldeditors
package|;
end_package

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|datatransfer
operator|.
name|DataFlavor
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|util
operator|.
name|OS
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
name|assertEquals
import|;
end_import

begin_class
DECL|class|HtmlTransferableTest
specifier|public
class|class
name|HtmlTransferableTest
block|{
annotation|@
name|Test
DECL|method|testSimpleDivConstruct ()
specifier|public
name|void
name|testSimpleDivConstruct
parameter_list|()
throws|throws
name|Exception
block|{
name|String
name|html
init|=
literal|"<div>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<div>Text</div>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"</div>"
operator|+
name|OS
operator|.
name|NEWLINE
decl_stmt|;
name|HtmlTransferable
name|htmlTransferable
init|=
operator|new
name|HtmlTransferable
argument_list|(
name|html
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|"Text"
argument_list|,
name|htmlTransferable
operator|.
name|getTransferData
argument_list|(
name|DataFlavor
operator|.
name|stringFlavor
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testAdvancedDivConstruct ()
specifier|public
name|void
name|testAdvancedDivConstruct
parameter_list|()
throws|throws
name|Exception
block|{
name|String
name|html
init|=
literal|"<!DOCTYPE html>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<html>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<head>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<meta charset=\"utf-8\">"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"</head>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<body>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<div class=\"csl-entry\">"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<div class=\"csl-left-margin\">Content 1</div>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"</div>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<br>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<div class=\"csl-entry\">"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<div class=\"csl-left-margin\">Content 2</div>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"</div>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"</body>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"</html>"
operator|+
name|OS
operator|.
name|NEWLINE
decl_stmt|;
name|String
name|expected
init|=
literal|"Content 1"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"Content 2"
decl_stmt|;
name|HtmlTransferable
name|htmlTransferable
init|=
operator|new
name|HtmlTransferable
argument_list|(
name|html
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|expected
argument_list|,
name|htmlTransferable
operator|.
name|getTransferData
argument_list|(
name|DataFlavor
operator|.
name|stringFlavor
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGenerateMagicSpaces ()
specifier|public
name|void
name|testGenerateMagicSpaces
parameter_list|()
throws|throws
name|Exception
block|{
name|String
name|html
init|=
literal|"<!DOCTYPE html>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<html>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<head>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<meta charset=\"utf-8\">"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"</head>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<body>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<div class=\"csl-entry\">"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<div>number1</div><div class=\"csl-left-margin\">Content 1</div>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"</div>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<br>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<div class=\"csl-entry\">"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<div>number2</div><div class=\"csl-left-margin\">Content 2</div>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"</div>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"</body>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"</html>"
operator|+
name|OS
operator|.
name|NEWLINE
decl_stmt|;
name|String
name|expected
init|=
literal|"number1 Content 1"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"number2 Content 2"
decl_stmt|;
name|HtmlTransferable
name|htmlTransferable
init|=
operator|new
name|HtmlTransferable
argument_list|(
name|html
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|expected
argument_list|,
name|htmlTransferable
operator|.
name|getTransferData
argument_list|(
name|DataFlavor
operator|.
name|stringFlavor
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testAmpersandConversion ()
specifier|public
name|void
name|testAmpersandConversion
parameter_list|()
throws|throws
name|Exception
block|{
name|String
name|html
init|=
literal|"<!DOCTYPE html>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<html>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<head>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<meta charset=\"utf-8\">"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"</head>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<body>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<div>Let's rock&amp; have fun"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"</div>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"</body>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"</html>"
operator|+
name|OS
operator|.
name|NEWLINE
decl_stmt|;
name|String
name|expected
init|=
literal|"Let's rock& have fun"
decl_stmt|;
name|HtmlTransferable
name|htmlTransferable
init|=
operator|new
name|HtmlTransferable
argument_list|(
name|html
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|expected
argument_list|,
name|htmlTransferable
operator|.
name|getTransferData
argument_list|(
name|DataFlavor
operator|.
name|stringFlavor
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

