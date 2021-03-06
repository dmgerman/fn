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
name|java
operator|.
name|util
operator|.
name|Locale
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Optional
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
name|assertEquals
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
name|assertThrows
import|;
end_import

begin_class
DECL|class|LanguageTest
class|class
name|LanguageTest
block|{
annotation|@
name|Test
DECL|method|convertKnownLanguageOnly ()
name|void
name|convertKnownLanguageOnly
parameter_list|()
block|{
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
operator|new
name|Locale
argument_list|(
literal|"en"
argument_list|)
argument_list|)
argument_list|,
name|Language
operator|.
name|convertToSupportedLocale
argument_list|(
name|Language
operator|.
name|ENGLISH
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|convertKnownLanguageAndCountryCorrect ()
name|void
name|convertKnownLanguageAndCountryCorrect
parameter_list|()
block|{
comment|//Language and country code have to be separated see: https://stackoverflow.com/a/3318598
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
operator|new
name|Locale
argument_list|(
literal|"pt"
argument_list|,
literal|"BR"
argument_list|)
argument_list|)
argument_list|,
name|Language
operator|.
name|convertToSupportedLocale
argument_list|(
name|Language
operator|.
name|BRAZILIAN_PORTUGUESE
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|convertToKnownLocaleNull ()
name|void
name|convertToKnownLocaleNull
parameter_list|()
block|{
name|assertThrows
argument_list|(
name|NullPointerException
operator|.
name|class
argument_list|,
parameter_list|()
lambda|->
name|Language
operator|.
name|convertToSupportedLocale
argument_list|(
literal|null
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

