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
DECL|class|ReplaceTest
specifier|public
class|class
name|ReplaceTest
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
name|Replace
argument_list|()
decl_stmt|;
name|a
operator|.
name|setArgument
argument_list|(
literal|"Bob,Ben"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Ben Bruce"
argument_list|,
name|a
operator|.
name|format
argument_list|(
literal|"Bob Bruce"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testSimpleTextNoHit ()
specifier|public
name|void
name|testSimpleTextNoHit
parameter_list|()
block|{
name|ParamLayoutFormatter
name|a
init|=
operator|new
name|Replace
argument_list|()
decl_stmt|;
name|a
operator|.
name|setArgument
argument_list|(
literal|"Bob,Ben"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Jolly Jumper"
argument_list|,
name|a
operator|.
name|format
argument_list|(
literal|"Jolly Jumper"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testFormatNull ()
specifier|public
name|void
name|testFormatNull
parameter_list|()
block|{
name|ParamLayoutFormatter
name|a
init|=
operator|new
name|Replace
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
DECL|method|testFormatEmpty ()
specifier|public
name|void
name|testFormatEmpty
parameter_list|()
block|{
name|ParamLayoutFormatter
name|a
init|=
operator|new
name|Replace
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
DECL|method|testNoArgumentSet ()
specifier|public
name|void
name|testNoArgumentSet
parameter_list|()
block|{
name|ParamLayoutFormatter
name|a
init|=
operator|new
name|Replace
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
DECL|method|testNoProperArgument ()
specifier|public
name|void
name|testNoProperArgument
parameter_list|()
block|{
name|ParamLayoutFormatter
name|a
init|=
operator|new
name|Replace
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
