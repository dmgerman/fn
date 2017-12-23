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
name|Arrays
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collections
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
DECL|class|LocalizationParserTest
specifier|public
class|class
name|LocalizationParserTest
block|{
annotation|@
name|Test
DECL|method|testKeyParsingCode ()
specifier|public
name|void
name|testKeyParsingCode
parameter_list|()
block|{
name|assertLocalizationKeyParsing
argument_list|(
literal|"Localization.lang(\"one per line\")"
argument_list|,
literal|"one\\ per\\ line"
argument_list|)
expr_stmt|;
name|assertLocalizationKeyParsing
argument_list|(
literal|"Localization.lang(\n            \"Copy \\\\cite{BibTeX key}\")"
argument_list|,
literal|"Copy\\ \\cite{BibTeX\\ key}"
argument_list|)
expr_stmt|;
name|assertLocalizationKeyParsing
argument_list|(
literal|"Localization.lang(\"two per line\") Localization.lang(\"two per line\")"
argument_list|,
name|Arrays
operator|.
name|asList
argument_list|(
literal|"two\\ per\\ line"
argument_list|,
literal|"two\\ per\\ line"
argument_list|)
argument_list|)
expr_stmt|;
name|assertLocalizationKeyParsing
argument_list|(
literal|"Localization.lang(\"multi \" + \n\"line\")"
argument_list|,
literal|"multi\\ line"
argument_list|)
expr_stmt|;
name|assertLocalizationKeyParsing
argument_list|(
literal|"Localization.lang(\"one per line with var\", var)"
argument_list|,
literal|"one\\ per\\ line\\ with\\ var"
argument_list|)
expr_stmt|;
name|assertLocalizationKeyParsing
argument_list|(
literal|"Localization.lang(\"Search %0\", \"Springer\")"
argument_list|,
literal|"Search\\ %0"
argument_list|)
expr_stmt|;
name|assertLocalizationKeyParsing
argument_list|(
literal|"Localization.lang(\"Reset preferences (key1,key2,... or 'all')\")"
argument_list|,
literal|"Reset\\ preferences\\ (key1,key2,...\\ or\\ 'all')"
argument_list|)
expr_stmt|;
name|assertLocalizationKeyParsing
argument_list|(
literal|"Localization.lang(\"Multiple entries selected. Do you want to change the type of all these to '%0'?\")"
argument_list|,
literal|"Multiple\\ entries\\ selected.\\ Do\\ you\\ want\\ to\\ change\\ the\\ type\\ of\\ all\\ these\\ to\\ '%0'?"
argument_list|)
expr_stmt|;
name|assertLocalizationKeyParsing
argument_list|(
literal|"Localization.lang(\"Run fetcher, e.g. \\\"--fetch=Medline:cancer\\\"\");"
argument_list|,
literal|"Run\\ fetcher,\\ e.g.\\ \"--fetch\\=Medline\\:cancer\""
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testParameterParsingCode ()
specifier|public
name|void
name|testParameterParsingCode
parameter_list|()
block|{
name|assertLocalizationParameterParsing
argument_list|(
literal|"Localization.lang(\"one per line\")"
argument_list|,
literal|"\"one per line\""
argument_list|)
expr_stmt|;
name|assertLocalizationParameterParsing
argument_list|(
literal|"Localization.lang(\"one per line\" + var)"
argument_list|,
literal|"\"one per line\" + var"
argument_list|)
expr_stmt|;
name|assertLocalizationParameterParsing
argument_list|(
literal|"Localization.lang(var + \"one per line\")"
argument_list|,
literal|"var + \"one per line\""
argument_list|)
expr_stmt|;
name|assertLocalizationParameterParsing
argument_list|(
literal|"Localization.lang(\"Search %0\", \"Springer\")"
argument_list|,
literal|"\"Search %0\", \"Springer\""
argument_list|)
expr_stmt|;
block|}
DECL|method|assertLocalizationKeyParsing (String code, String expectedLanguageKeys)
specifier|private
name|void
name|assertLocalizationKeyParsing
parameter_list|(
name|String
name|code
parameter_list|,
name|String
name|expectedLanguageKeys
parameter_list|)
block|{
name|assertLocalizationKeyParsing
argument_list|(
name|code
argument_list|,
name|Collections
operator|.
name|singletonList
argument_list|(
name|expectedLanguageKeys
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|assertLocalizationKeyParsing (String code, List<String> expectedLanguageKeys)
specifier|private
name|void
name|assertLocalizationKeyParsing
parameter_list|(
name|String
name|code
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|expectedLanguageKeys
parameter_list|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|languageKeysInString
init|=
name|LocalizationParser
operator|.
name|JavaLocalizationEntryParser
operator|.
name|getLanguageKeysInString
argument_list|(
name|code
argument_list|,
name|LocalizationBundleForTest
operator|.
name|LANG
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|expectedLanguageKeys
argument_list|,
name|languageKeysInString
argument_list|)
expr_stmt|;
block|}
DECL|method|assertLocalizationParameterParsing (String code, List<String> expectedParameter)
specifier|private
name|void
name|assertLocalizationParameterParsing
parameter_list|(
name|String
name|code
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|expectedParameter
parameter_list|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|languageKeysInString
init|=
name|LocalizationParser
operator|.
name|JavaLocalizationEntryParser
operator|.
name|getLocalizationParameter
argument_list|(
name|code
argument_list|,
name|LocalizationBundleForTest
operator|.
name|LANG
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|expectedParameter
argument_list|,
name|languageKeysInString
argument_list|)
expr_stmt|;
block|}
DECL|method|assertLocalizationParameterParsing (String code, String expectedParameter)
specifier|private
name|void
name|assertLocalizationParameterParsing
parameter_list|(
name|String
name|code
parameter_list|,
name|String
name|expectedParameter
parameter_list|)
block|{
name|assertLocalizationParameterParsing
argument_list|(
name|code
argument_list|,
name|Collections
operator|.
name|singletonList
argument_list|(
name|expectedParameter
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

