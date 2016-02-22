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
DECL|class|DefaultTest
specifier|public
class|class
name|DefaultTest
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
name|Default
argument_list|()
decl_stmt|;
name|a
operator|.
name|setArgument
argument_list|(
literal|"DEFAULT TEXT"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Bob Bruce"
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
DECL|method|testFormatNullExpectReplace ()
specifier|public
name|void
name|testFormatNullExpectReplace
parameter_list|()
block|{
name|ParamLayoutFormatter
name|a
init|=
operator|new
name|Default
argument_list|()
decl_stmt|;
name|a
operator|.
name|setArgument
argument_list|(
literal|"DEFAULT TEXT"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"DEFAULT TEXT"
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
name|Default
argument_list|()
decl_stmt|;
name|a
operator|.
name|setArgument
argument_list|(
literal|"DEFAULT TEXT"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"DEFAULT TEXT"
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
name|Default
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
DECL|method|testNoArgumentSetNullInput ()
specifier|public
name|void
name|testNoArgumentSetNullInput
parameter_list|()
block|{
name|ParamLayoutFormatter
name|a
init|=
operator|new
name|Default
argument_list|()
decl_stmt|;
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
literal|null
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testNoArgumentSetEmptyInput ()
specifier|public
name|void
name|testNoArgumentSetEmptyInput
parameter_list|()
block|{
name|ParamLayoutFormatter
name|a
init|=
operator|new
name|Default
argument_list|()
decl_stmt|;
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
block|}
end_class

end_unit

