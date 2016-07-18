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

begin_class
DECL|class|DateFormatterTest
specifier|public
class|class
name|DateFormatterTest
block|{
DECL|field|formatter
specifier|private
name|ParamLayoutFormatter
name|formatter
decl_stmt|;
annotation|@
name|Before
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
block|{
name|formatter
operator|=
operator|new
name|DateFormatter
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testDefaultFormat ()
specifier|public
name|void
name|testDefaultFormat
parameter_list|()
block|{
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"2016-07-15"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"2016-07-15"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testRequestedFormat ()
specifier|public
name|void
name|testRequestedFormat
parameter_list|()
block|{
name|formatter
operator|.
name|setArgument
argument_list|(
literal|"MM/yyyy"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"07/2016"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"2016-07-15"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit
