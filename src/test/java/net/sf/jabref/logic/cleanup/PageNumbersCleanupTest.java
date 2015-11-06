begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.cleanup
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|cleanup
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
name|model
operator|.
name|entry
operator|.
name|BibtexEntry
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|After
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
DECL|class|PageNumbersCleanupTest
specifier|public
class|class
name|PageNumbersCleanupTest
block|{
DECL|field|entry
specifier|private
name|BibtexEntry
name|entry
decl_stmt|;
annotation|@
name|Before
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
block|{
name|entry
operator|=
operator|new
name|BibtexEntry
argument_list|()
expr_stmt|;
block|}
annotation|@
name|After
DECL|method|teardown ()
specifier|public
name|void
name|teardown
parameter_list|()
block|{
name|entry
operator|=
literal|null
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|formatPageNumbers ()
specifier|public
name|void
name|formatPageNumbers
parameter_list|()
block|{
name|entry
operator|.
name|setField
argument_list|(
literal|"pages"
argument_list|,
literal|"1-2"
argument_list|)
expr_stmt|;
operator|new
name|PageNumbersCleanup
argument_list|(
name|entry
argument_list|)
operator|.
name|cleanup
argument_list|()
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"1--2"
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"pages"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|onlyFormatPageNumbersField ()
specifier|public
name|void
name|onlyFormatPageNumbersField
parameter_list|()
block|{
name|entry
operator|.
name|setField
argument_list|(
literal|"otherfield"
argument_list|,
literal|"1-2"
argument_list|)
expr_stmt|;
operator|new
name|PageNumbersCleanup
argument_list|(
name|entry
argument_list|)
operator|.
name|cleanup
argument_list|()
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"1-2"
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"otherfield"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

