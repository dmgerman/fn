begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.l10n
package|package
name|org
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
name|LocalizationKey
name|localizationKey
init|=
operator|new
name|LocalizationKey
argument_list|(
literal|"#test! : ="
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|"\\#test\\!\\ \\:\\ \\="
argument_list|,
name|localizationKey
operator|.
name|getPropertiesKey
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"#test! : ="
argument_list|,
name|localizationKey
operator|.
name|getPropertiesKeyUnescaped
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"#test! : ="
argument_list|,
name|localizationKey
operator|.
name|getTranslationValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|underscoreIsPreserved ()
specifier|public
name|void
name|underscoreIsPreserved
parameter_list|()
block|{
name|LocalizationKey
name|localizationKey
init|=
operator|new
name|LocalizationKey
argument_list|(
literal|"test_with_underscore"
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|"test_with_underscore"
argument_list|,
name|localizationKey
operator|.
name|getPropertiesKey
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

