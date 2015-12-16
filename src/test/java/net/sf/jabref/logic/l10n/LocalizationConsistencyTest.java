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
name|Assert
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
name|*
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
DECL|class|LocalizationConsistencyTest
specifier|public
class|class
name|LocalizationConsistencyTest
block|{
annotation|@
name|Test
DECL|method|allFilesMustHaveSameKeys ()
specifier|public
name|void
name|allFilesMustHaveSameKeys
parameter_list|()
block|{
for|for
control|(
name|String
name|bundle
range|:
name|Arrays
operator|.
name|asList
argument_list|(
literal|"JabRef"
argument_list|,
literal|"Menu"
argument_list|)
control|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|englishKeys
init|=
name|LocalizationParser
operator|.
name|getKeysInPropertiesFile
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"/l10n/%s_%s.properties"
argument_list|,
name|bundle
argument_list|,
literal|"en"
argument_list|)
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|nonEnglishLanguages
init|=
name|Languages
operator|.
name|LANGUAGES
operator|.
name|values
argument_list|()
operator|.
name|stream
argument_list|()
operator|.
name|filter
argument_list|(
name|l
lambda|->
operator|!
literal|"en"
operator|.
name|equals
argument_list|(
name|l
argument_list|)
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|lang
range|:
name|nonEnglishLanguages
control|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|nonEnglishKeys
init|=
name|LocalizationParser
operator|.
name|getKeysInPropertiesFile
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"/l10n/%s_%s.properties"
argument_list|,
name|bundle
argument_list|,
name|lang
argument_list|)
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|missing
init|=
operator|new
name|LinkedList
argument_list|<>
argument_list|(
name|englishKeys
argument_list|)
decl_stmt|;
name|missing
operator|.
name|removeAll
argument_list|(
name|nonEnglishKeys
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Missing keys of "
operator|+
name|lang
argument_list|,
name|Collections
operator|.
name|emptyList
argument_list|()
argument_list|,
name|missing
argument_list|)
expr_stmt|;
block|}
block|}
block|}
annotation|@
name|Test
DECL|method|keyValueShouldBeEqualForEnglishPropertiesMenu ()
specifier|public
name|void
name|keyValueShouldBeEqualForEnglishPropertiesMenu
parameter_list|()
block|{
name|Properties
name|englishKeys
init|=
name|LocalizationParser
operator|.
name|getProperties
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"/l10n/%s_%s.properties"
argument_list|,
literal|"Menu"
argument_list|,
literal|"en"
argument_list|)
argument_list|)
decl_stmt|;
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|Object
argument_list|,
name|Object
argument_list|>
name|entry
range|:
name|englishKeys
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|String
name|expectedKeyEqualsKey
init|=
name|String
operator|.
name|format
argument_list|(
literal|"%s=%s"
argument_list|,
name|entry
operator|.
name|getKey
argument_list|()
argument_list|,
name|entry
operator|.
name|getKey
argument_list|()
argument_list|)
decl_stmt|;
name|String
name|actualKeyEqualsValue
init|=
name|String
operator|.
name|format
argument_list|(
literal|"%s=%s"
argument_list|,
name|entry
operator|.
name|getKey
argument_list|()
argument_list|,
name|entry
operator|.
name|getValue
argument_list|()
operator|.
name|toString
argument_list|()
operator|.
name|replace
argument_list|(
literal|"&"
argument_list|,
literal|""
argument_list|)
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|expectedKeyEqualsKey
argument_list|,
name|actualKeyEqualsValue
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Test
DECL|method|keyValueShouldBeEqualForEnglishPropertiesMessages ()
specifier|public
name|void
name|keyValueShouldBeEqualForEnglishPropertiesMessages
parameter_list|()
block|{
name|Properties
name|englishKeys
init|=
name|LocalizationParser
operator|.
name|getProperties
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"/l10n/%s_%s.properties"
argument_list|,
literal|"JabRef"
argument_list|,
literal|"en"
argument_list|)
argument_list|)
decl_stmt|;
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|Object
argument_list|,
name|Object
argument_list|>
name|entry
range|:
name|englishKeys
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|String
name|expectedKeyEqualsKey
init|=
name|String
operator|.
name|format
argument_list|(
literal|"%s=%s"
argument_list|,
name|entry
operator|.
name|getKey
argument_list|()
argument_list|,
name|entry
operator|.
name|getKey
argument_list|()
argument_list|)
decl_stmt|;
name|String
name|actualKeyEqualsValue
init|=
name|String
operator|.
name|format
argument_list|(
literal|"%s=%s"
argument_list|,
name|entry
operator|.
name|getKey
argument_list|()
argument_list|,
name|entry
operator|.
name|getValue
argument_list|()
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|expectedKeyEqualsKey
argument_list|,
name|actualKeyEqualsValue
argument_list|)
expr_stmt|;
block|}
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
block|}
end_class

end_unit

