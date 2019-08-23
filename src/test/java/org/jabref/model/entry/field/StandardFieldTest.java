begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.model.entry.field
package|package
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|field
package|;
end_package

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|jupiter
operator|.
name|api
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
name|jupiter
operator|.
name|api
operator|.
name|Assertions
operator|.
name|assertEquals
import|;
end_import

begin_class
DECL|class|StandardFieldTest
class|class
name|StandardFieldTest
block|{
annotation|@
name|Test
DECL|method|fieldsConsideredEqualIfSame ()
name|void
name|fieldsConsideredEqualIfSame
parameter_list|()
block|{
name|assertEquals
argument_list|(
name|StandardField
operator|.
name|TITLE
argument_list|,
name|StandardField
operator|.
name|TITLE
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

