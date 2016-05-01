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
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|InputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|InputStreamReader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|charset
operator|.
name|StandardCharsets
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
name|Collections
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|LinkedList
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
name|Map
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Properties
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

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|Assert
operator|.
name|fail
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
name|List
argument_list|<
name|String
argument_list|>
name|obsolete
init|=
operator|new
name|LinkedList
argument_list|<>
argument_list|(
name|nonEnglishKeys
argument_list|)
decl_stmt|;
name|obsolete
operator|.
name|removeAll
argument_list|(
name|englishKeys
argument_list|)
expr_stmt|;
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
name|assertEquals
argument_list|(
literal|"Obsolete keys of "
operator|+
name|lang
argument_list|,
name|Collections
operator|.
name|emptyList
argument_list|()
argument_list|,
name|obsolete
argument_list|)
expr_stmt|;
block|}
block|}
block|}
DECL|class|DuplicationDetectionProperties
specifier|private
specifier|static
class|class
name|DuplicationDetectionProperties
extends|extends
name|Properties
block|{
DECL|field|serialVersionUID
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
DECL|field|duplicates
specifier|private
specifier|final
name|List
argument_list|<
name|String
argument_list|>
name|duplicates
init|=
operator|new
name|LinkedList
argument_list|<>
argument_list|()
decl_stmt|;
DECL|method|DuplicationDetectionProperties ()
specifier|public
name|DuplicationDetectionProperties
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
comment|/**          * Overriding the HashTable put() so we can check for duplicates          */
annotation|@
name|Override
DECL|method|put (Object key, Object value)
specifier|public
specifier|synchronized
name|Object
name|put
parameter_list|(
name|Object
name|key
parameter_list|,
name|Object
name|value
parameter_list|)
block|{
comment|// Have we seen this key before?
if|if
condition|(
name|containsKey
argument_list|(
name|key
argument_list|)
condition|)
block|{
name|duplicates
operator|.
name|add
argument_list|(
name|String
operator|.
name|valueOf
argument_list|(
name|key
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|super
operator|.
name|put
argument_list|(
name|key
argument_list|,
name|value
argument_list|)
return|;
block|}
DECL|method|getDuplicates ()
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getDuplicates
parameter_list|()
block|{
return|return
name|duplicates
return|;
block|}
block|}
annotation|@
name|Test
DECL|method|ensureNoDuplicates ()
specifier|public
name|void
name|ensureNoDuplicates
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
for|for
control|(
name|String
name|lang
range|:
name|Languages
operator|.
name|LANGUAGES
operator|.
name|values
argument_list|()
control|)
block|{
name|String
name|propertyFilePath
init|=
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
decl_stmt|;
comment|// read in
name|DuplicationDetectionProperties
name|properties
init|=
operator|new
name|DuplicationDetectionProperties
argument_list|()
decl_stmt|;
try|try
init|(
name|InputStream
name|is
init|=
name|LocalizationConsistencyTest
operator|.
name|class
operator|.
name|getResourceAsStream
argument_list|(
name|propertyFilePath
argument_list|)
init|;
name|InputStreamReader
name|reader
operator|=
operator|new
name|InputStreamReader
argument_list|(
name|is
argument_list|,
name|StandardCharsets
operator|.
name|UTF_8
argument_list|)
init|)
block|{
name|properties
operator|.
name|load
argument_list|(
name|reader
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|RuntimeException
argument_list|(
name|e
argument_list|)
throw|;
block|}
name|List
argument_list|<
name|String
argument_list|>
name|duplicates
init|=
name|properties
operator|.
name|getDuplicates
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
literal|"Duplicate keys of "
operator|+
name|lang
argument_list|,
name|Collections
operator|.
name|emptyList
argument_list|()
argument_list|,
name|duplicates
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
annotation|@
name|Test
DECL|method|findObsoleteLocalizationKeys ()
specifier|public
name|void
name|findObsoleteLocalizationKeys
parameter_list|()
throws|throws
name|IOException
block|{
name|List
argument_list|<
name|String
argument_list|>
name|obsoleteKeys
init|=
name|LocalizationParser
operator|.
name|findObsolete
argument_list|(
name|LocalizationBundle
operator|.
name|LANG
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|obsoleteKeys
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
argument_list|()
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Obsolete keys found:"
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|obsoleteKeys
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
argument_list|(
literal|"1. REMOVE THESE FROM THE ENGLISH LANGUAGE FILE"
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"2. EXECUTE gradlew -b localization.gradle generateMissingTranslationKeys TO"
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"REMOVE THESE FROM THE NON-ENGLISH LANGUAGE FILES"
argument_list|)
expr_stmt|;
name|fail
argument_list|(
literal|"Obsolete keys "
operator|+
name|obsoleteKeys
operator|+
literal|" found in properties file which should be removed"
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Test
DECL|method|findObsoleteMenuLocalizationKeys ()
specifier|public
name|void
name|findObsoleteMenuLocalizationKeys
parameter_list|()
throws|throws
name|IOException
block|{
name|List
argument_list|<
name|String
argument_list|>
name|obsoleteKeys
init|=
name|LocalizationParser
operator|.
name|findObsolete
argument_list|(
name|LocalizationBundle
operator|.
name|MENU
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|obsoleteKeys
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
argument_list|()
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Obsolete menu keys found:"
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|obsoleteKeys
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
argument_list|(
literal|"1. REMOVE THESE FROM THE ENGLISH LANGUAGE FILE"
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"2. EXECUTE gradlew -b localization.gradle generateMissingTranslationKeys"
operator|+
literal|" TO"
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"REMOVE THESE FROM THE NON-ENGLISH LANGUAGE FILES"
argument_list|)
expr_stmt|;
name|fail
argument_list|(
literal|"Obsolete keys "
operator|+
name|obsoleteKeys
operator|+
literal|" found in menu properties file which should be removed"
argument_list|)
expr_stmt|;
block|}
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
literal|"EXECUTE gradlew -b localization.gradle generateMissingTranslationKeys TO"
argument_list|)
expr_stmt|;
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

