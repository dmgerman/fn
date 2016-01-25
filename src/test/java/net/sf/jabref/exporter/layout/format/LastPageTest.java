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
DECL|class|LastPageTest
specifier|public
class|class
name|LastPageTest
block|{
annotation|@
name|Test
DECL|method|testFormatEmpty ()
specifier|public
name|void
name|testFormatEmpty
parameter_list|()
block|{
name|LayoutFormatter
name|a
init|=
operator|new
name|LastPage
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
annotation|@
name|Test
DECL|method|testFormatNull ()
specifier|public
name|void
name|testFormatNull
parameter_list|()
block|{
name|LayoutFormatter
name|a
init|=
operator|new
name|LastPage
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
DECL|method|testFormatSinglePage ()
specifier|public
name|void
name|testFormatSinglePage
parameter_list|()
block|{
name|LayoutFormatter
name|a
init|=
operator|new
name|LastPage
argument_list|()
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"345"
argument_list|,
name|a
operator|.
name|format
argument_list|(
literal|"345"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testFormatSingleDash ()
specifier|public
name|void
name|testFormatSingleDash
parameter_list|()
block|{
name|LayoutFormatter
name|a
init|=
operator|new
name|LastPage
argument_list|()
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"350"
argument_list|,
name|a
operator|.
name|format
argument_list|(
literal|"345-350"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testFormatDoubleDash ()
specifier|public
name|void
name|testFormatDoubleDash
parameter_list|()
block|{
name|LayoutFormatter
name|a
init|=
operator|new
name|LastPage
argument_list|()
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"350"
argument_list|,
name|a
operator|.
name|format
argument_list|(
literal|"345--350"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testFinalCoverageCase ()
specifier|public
name|void
name|testFinalCoverageCase
parameter_list|()
block|{
name|LayoutFormatter
name|a
init|=
operator|new
name|LastPage
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
literal|"--"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

