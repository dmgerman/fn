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
name|io
operator|.
name|IOException
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
import|import
name|java
operator|.
name|util
operator|.
name|StringJoiner
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|stream
operator|.
name|Collectors
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
DECL|class|LocalizationTest
specifier|public
class|class
name|LocalizationTest
block|{
annotation|@
name|Test
DECL|method|testLocalizationKey ()
specifier|public
name|void
name|testLocalizationKey
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"test_\\:_\\="
argument_list|,
operator|new
name|Localization
operator|.
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
annotation|@
name|Test
DECL|method|testTranslation ()
specifier|public
name|void
name|testTranslation
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"What \n : %e %c a b"
argument_list|,
operator|new
name|Localization
operator|.
name|LocalizationKeyParams
argument_list|(
literal|"What \n : %e %c_%0 %1"
argument_list|,
literal|"a"
argument_list|,
literal|"b"
argument_list|)
operator|.
name|translate
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testParamsReplacement ()
specifier|public
name|void
name|testParamsReplacement
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"BibLaTeX mode"
argument_list|,
operator|new
name|Localization
operator|.
name|LocalizationKeyParams
argument_list|(
literal|"%0 mode"
argument_list|,
literal|"BibLaTeX"
argument_list|)
operator|.
name|translate
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|findMissingLocalizationKeys ()
specifier|public
name|void
name|findMissingLocalizationKeys
parameter_list|()
throws|throws
name|IOException
block|{
name|List
argument_list|<
name|LocalizationEntry
argument_list|>
name|missingKeys
init|=
name|LocalizationParser
operator|.
name|find
argument_list|(
name|LocalizationBundle
operator|.
name|LANG
argument_list|)
operator|.
name|stream
argument_list|()
operator|.
name|sorted
argument_list|()
operator|.
name|distinct
argument_list|()
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
argument_list|)
decl_stmt|;
name|printInfos
argument_list|(
name|missingKeys
argument_list|)
expr_stmt|;
name|String
name|resultString
init|=
name|missingKeys
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|Object
operator|::
name|toString
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|joining
argument_list|(
literal|"\n"
argument_list|)
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|"source code contains language keys for the messages which are not in the corresponding properties file"
argument_list|,
literal|""
argument_list|,
name|resultString
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|findMissingMenuLocalizationKeys ()
specifier|public
name|void
name|findMissingMenuLocalizationKeys
parameter_list|()
throws|throws
name|IOException
block|{
name|List
argument_list|<
name|LocalizationEntry
argument_list|>
name|missingKeys
init|=
name|LocalizationParser
operator|.
name|find
argument_list|(
name|LocalizationBundle
operator|.
name|MENU
argument_list|)
operator|.
name|stream
argument_list|()
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
argument_list|)
decl_stmt|;
name|printInfos
argument_list|(
name|missingKeys
argument_list|)
expr_stmt|;
name|String
name|resultString
init|=
name|missingKeys
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|Object
operator|::
name|toString
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|joining
argument_list|(
literal|"\n"
argument_list|)
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|"source code contains language keys for the menu which are not in the corresponding properties file"
argument_list|,
literal|""
argument_list|,
name|resultString
argument_list|)
expr_stmt|;
block|}
DECL|method|printInfos (List<LocalizationEntry> missingKeys)
specifier|private
name|void
name|printInfos
parameter_list|(
name|List
argument_list|<
name|LocalizationEntry
argument_list|>
name|missingKeys
parameter_list|)
block|{
if|if
condition|(
operator|!
name|missingKeys
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|convertToEnglishPropertiesFile
argument_list|(
name|missingKeys
argument_list|)
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|()
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|()
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|convertPropertiesFile
argument_list|(
name|missingKeys
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|convertToEnglishPropertiesFile (List<LocalizationEntry> missingKeys)
specifier|private
name|String
name|convertToEnglishPropertiesFile
parameter_list|(
name|List
argument_list|<
name|LocalizationEntry
argument_list|>
name|missingKeys
parameter_list|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"PASTE THIS INTO THE ENGLISH LANGUAGE FILE"
argument_list|)
expr_stmt|;
name|StringJoiner
name|result
init|=
operator|new
name|StringJoiner
argument_list|(
literal|"\n"
argument_list|)
decl_stmt|;
for|for
control|(
name|LocalizationEntry
name|key
range|:
name|missingKeys
control|)
block|{
name|result
operator|.
name|add
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"%s=%s"
argument_list|,
name|key
operator|.
name|getKey
argument_list|()
argument_list|,
name|key
operator|.
name|getKey
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|result
operator|.
name|toString
argument_list|()
return|;
block|}
DECL|method|convertPropertiesFile (List<LocalizationEntry> missingKeys)
specifier|private
name|String
name|convertPropertiesFile
parameter_list|(
name|List
argument_list|<
name|LocalizationEntry
argument_list|>
name|missingKeys
parameter_list|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"PASTE THIS INTO THE NON-ENGLISH LANGUAGE FILES"
argument_list|)
expr_stmt|;
name|StringJoiner
name|result
init|=
operator|new
name|StringJoiner
argument_list|(
literal|"\n"
argument_list|)
decl_stmt|;
for|for
control|(
name|LocalizationEntry
name|key
range|:
name|missingKeys
control|)
block|{
name|result
operator|.
name|add
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"%s="
argument_list|,
name|key
operator|.
name|getKey
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|result
operator|.
name|toString
argument_list|()
return|;
block|}
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

