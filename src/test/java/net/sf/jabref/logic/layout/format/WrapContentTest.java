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
name|ParamLayoutFormatter
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
DECL|class|WrapContentTest
specifier|public
class|class
name|WrapContentTest
block|{
annotation|@
name|Test
DECL|method|testSimpleText ()
specifier|public
name|void
name|testSimpleText
parameter_list|()
block|{
name|ParamLayoutFormatter
name|a
init|=
operator|new
name|WrapContent
argument_list|()
decl_stmt|;
name|a
operator|.
name|setArgument
argument_list|(
literal|"<,>"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"<Bob>"
argument_list|,
name|a
operator|.
name|format
argument_list|(
literal|"Bob"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testEmptyStart ()
specifier|public
name|void
name|testEmptyStart
parameter_list|()
block|{
name|ParamLayoutFormatter
name|a
init|=
operator|new
name|WrapContent
argument_list|()
decl_stmt|;
name|a
operator|.
name|setArgument
argument_list|(
literal|",:"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Bob:"
argument_list|,
name|a
operator|.
name|format
argument_list|(
literal|"Bob"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testEmptyEnd ()
specifier|public
name|void
name|testEmptyEnd
parameter_list|()
block|{
name|ParamLayoutFormatter
name|a
init|=
operator|new
name|WrapContent
argument_list|()
decl_stmt|;
name|a
operator|.
name|setArgument
argument_list|(
literal|"Content: ,"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Content: Bob"
argument_list|,
name|a
operator|.
name|format
argument_list|(
literal|"Bob"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testEscaping ()
specifier|public
name|void
name|testEscaping
parameter_list|()
block|{
name|ParamLayoutFormatter
name|a
init|=
operator|new
name|WrapContent
argument_list|()
decl_stmt|;
name|a
operator|.
name|setArgument
argument_list|(
literal|"Name\\,Field\\,,\\,Author"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Name,Field,Bob,Author"
argument_list|,
name|a
operator|.
name|format
argument_list|(
literal|"Bob"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testFormatNullExpectNothingAdded ()
specifier|public
name|void
name|testFormatNullExpectNothingAdded
parameter_list|()
block|{
name|ParamLayoutFormatter
name|a
init|=
operator|new
name|WrapContent
argument_list|()
decl_stmt|;
name|a
operator|.
name|setArgument
argument_list|(
literal|"Eds.,Ed."
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|null
argument_list|,
name|a
operator|.
name|format
argument_list|(
literal|null
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testFormatEmptyExpectNothingAdded ()
specifier|public
name|void
name|testFormatEmptyExpectNothingAdded
parameter_list|()
block|{
name|ParamLayoutFormatter
name|a
init|=
operator|new
name|WrapContent
argument_list|()
decl_stmt|;
name|a
operator|.
name|setArgument
argument_list|(
literal|"Eds.,Ed."
argument_list|)
expr_stmt|;
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
block|}
annotation|@
name|Test
DECL|method|testNoArgumentSetExpectNothingAdded ()
specifier|public
name|void
name|testNoArgumentSetExpectNothingAdded
parameter_list|()
block|{
name|ParamLayoutFormatter
name|a
init|=
operator|new
name|WrapContent
argument_list|()
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Bob Bruce and Jolly Jumper"
argument_list|,
name|a
operator|.
name|format
argument_list|(
literal|"Bob Bruce and Jolly Jumper"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testNoProperArgumentExpectNothingAdded ()
specifier|public
name|void
name|testNoProperArgumentExpectNothingAdded
parameter_list|()
block|{
name|ParamLayoutFormatter
name|a
init|=
operator|new
name|WrapContent
argument_list|()
decl_stmt|;
name|a
operator|.
name|setArgument
argument_list|(
literal|"Eds."
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Bob Bruce and Jolly Jumper"
argument_list|,
name|a
operator|.
name|format
argument_list|(
literal|"Bob Bruce and Jolly Jumper"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

