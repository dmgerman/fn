begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.l10n
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|l10n
package|;
end_package

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
DECL|class|LocalizationKeyTest
specifier|public
class|class
name|LocalizationKeyTest
block|{
annotation|@
name|Test
DECL|method|testConversionToPropertiesKey ()
specifier|public
name|void
name|testConversionToPropertiesKey
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"test_\\:_\\="
argument_list|,
operator|new
name|LocalizationKey
argument_list|(
literal|"test : ="
argument_list|)
operator|.
name|getPropertiesKey
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

