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
name|List
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
DECL|class|LocalizationParserTest
specifier|public
class|class
name|LocalizationParserTest
block|{
annotation|@
name|Test
DECL|method|testParsingCode ()
specifier|public
name|void
name|testParsingCode
parameter_list|()
block|{
name|String
name|code
init|=
literal|""
operator|+
literal|"Localization.lang(\"one per line\")"
operator|+
literal|"Localization.lang(\n"
operator|+
literal|"            \"Copy \\\\cite{BibTeX key}\")"
operator|+
literal|"Localization.lang(\"two per line\") Localization.lang(\"two per line\")"
operator|+
literal|"Localization.lang(\"multi \" + \n"
operator|+
literal|"\"line\")"
operator|+
literal|"Localization.lang(\"one per line with var\", var)"
operator|+
literal|"Localization.lang(\"Search %0\", \"Springer\")"
operator|+
literal|"Localization.lang(\"Reset preferences (key1,key2,... or 'all')\")"
operator|+
literal|"Localization.lang(\"Multiple entries selected. Do you want to change the type of all these to '%0'?\")"
operator|+
literal|"Localization.lang(\"Run Fetcher, e.g. \\\"--fetch=Medline:cancer\\\"\");"
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|expectedLanguageKeys
init|=
name|Arrays
operator|.
name|asList
argument_list|(
literal|"one_per_line"
argument_list|,
literal|"Copy_\\cite{BibTeX_key}"
argument_list|,
literal|"two_per_line"
argument_list|,
literal|"two_per_line"
argument_list|,
literal|"multi_line"
argument_list|,
literal|"one_per_line_with_var"
argument_list|,
literal|"Search_%0"
argument_list|,
literal|"Reset_preferences_(key1,key2,..._or_'all')"
argument_list|,
literal|"Multiple_entries_selected._Do_you_want_to_change_"
operator|+
literal|"the_type_of_all_these_to_'%0'?"
argument_list|,
literal|"Run_Fetcher,_e.g._\"--fetch\\=Medline\\:cancer\""
argument_list|)
decl_stmt|;
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
name|LocalizationBundle
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
block|}
end_class

end_unit

