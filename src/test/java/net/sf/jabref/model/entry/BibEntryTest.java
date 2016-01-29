begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.model.entry
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
package|;
end_package

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
DECL|class|BibEntryTest
specifier|public
class|class
name|BibEntryTest
block|{
DECL|field|entry
specifier|private
name|BibEntry
name|entry
decl_stmt|;
annotation|@
name|Before
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
throws|throws
name|Exception
block|{
name|entry
operator|=
operator|new
name|BibEntry
argument_list|()
expr_stmt|;
block|}
annotation|@
name|After
DECL|method|tearDown ()
specifier|public
name|void
name|tearDown
parameter_list|()
throws|throws
name|Exception
block|{
name|entry
operator|=
literal|null
expr_stmt|;
block|}
annotation|@
name|Test
argument_list|(
name|expected
operator|=
name|IllegalArgumentException
operator|.
name|class
argument_list|)
DECL|method|notOverrideReservedFields ()
specifier|public
name|void
name|notOverrideReservedFields
parameter_list|()
throws|throws
name|Exception
block|{
name|entry
operator|.
name|setField
argument_list|(
name|BibEntry
operator|.
name|ID_FIELD
argument_list|,
literal|"somevalue"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
argument_list|(
name|expected
operator|=
name|IllegalArgumentException
operator|.
name|class
argument_list|)
DECL|method|notClearReservedFields ()
specifier|public
name|void
name|notClearReservedFields
parameter_list|()
throws|throws
name|Exception
block|{
name|entry
operator|.
name|clearField
argument_list|(
name|BibEntry
operator|.
name|ID_FIELD
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

