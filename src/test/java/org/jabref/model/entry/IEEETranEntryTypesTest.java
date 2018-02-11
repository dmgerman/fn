begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.model.entry
package|package
name|org
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
name|java
operator|.
name|util
operator|.
name|Collection
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

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
name|assertTrue
import|;
end_import

begin_class
DECL|class|IEEETranEntryTypesTest
specifier|public
class|class
name|IEEETranEntryTypesTest
block|{
annotation|@
name|Test
DECL|method|ctlTypeContainsYesNoFields ()
specifier|public
name|void
name|ctlTypeContainsYesNoFields
parameter_list|()
block|{
name|Collection
argument_list|<
name|String
argument_list|>
name|ctlFields
init|=
name|IEEETranEntryTypes
operator|.
name|IEEETRANBSTCTL
operator|.
name|getAllFields
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|ynFields
init|=
name|InternalBibtexFields
operator|.
name|getIEEETranBSTctlYesNoFields
argument_list|()
decl_stmt|;
name|assertTrue
argument_list|(
name|ctlFields
operator|.
name|containsAll
argument_list|(
name|ynFields
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

