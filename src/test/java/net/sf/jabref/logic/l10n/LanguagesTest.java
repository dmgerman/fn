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
name|assertEquals
import|;
end_import

begin_class
DECL|class|LanguagesTest
specifier|public
class|class
name|LanguagesTest
block|{
annotation|@
name|Test
DECL|method|convertKnownLanguageOnly ()
specifier|public
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
name|Languages
operator|.
name|convertToSupportedLocale
argument_list|(
literal|"en"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|convertUnknownLanguage ()
specifier|public
name|void
name|convertUnknownLanguage
parameter_list|()
block|{
name|assertEquals
argument_list|(
name|Optional
operator|.
name|empty
argument_list|()
argument_list|,
name|Languages
operator|.
name|convertToSupportedLocale
argument_list|(
literal|"This is not a locale"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|convertKnownLanguageAndCountryOnly ()
specifier|public
name|void
name|convertKnownLanguageAndCountryOnly
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
name|Languages
operator|.
name|convertToSupportedLocale
argument_list|(
literal|"en_US"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|convertKnownLanguageAndUnknownCountry ()
specifier|public
name|void
name|convertKnownLanguageAndUnknownCountry
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
name|Languages
operator|.
name|convertToSupportedLocale
argument_list|(
literal|"en_GB_unknownvariant"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|convertUnknownKnownLanguageAndUnknownCountry ()
specifier|public
name|void
name|convertUnknownKnownLanguageAndUnknownCountry
parameter_list|()
block|{
name|assertEquals
argument_list|(
name|Optional
operator|.
name|empty
argument_list|()
argument_list|,
name|Languages
operator|.
name|convertToSupportedLocale
argument_list|(
literal|"language_country_variant"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
argument_list|(
name|expected
operator|=
name|NullPointerException
operator|.
name|class
argument_list|)
DECL|method|convertToKnownLocaleNull ()
specifier|public
name|void
name|convertToKnownLocaleNull
parameter_list|()
block|{
name|Languages
operator|.
name|convertToSupportedLocale
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

