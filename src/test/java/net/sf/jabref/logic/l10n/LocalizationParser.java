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
name|com
operator|.
name|google
operator|.
name|common
operator|.
name|base
operator|.
name|Charsets
import|;
end_import

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
name|io
operator|.
name|InputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|file
operator|.
name|Files
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|file
operator|.
name|Path
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|file
operator|.
name|Paths
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
name|regex
operator|.
name|Matcher
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Pattern
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

begin_class
DECL|class|LocalizationParser
specifier|public
class|class
name|LocalizationParser
block|{
DECL|method|find (LocalizationBundle type)
specifier|public
specifier|static
name|List
argument_list|<
name|LocalizationEntry
argument_list|>
name|find
parameter_list|(
name|LocalizationBundle
name|type
parameter_list|)
throws|throws
name|IOException
block|{
name|List
argument_list|<
name|LocalizationEntry
argument_list|>
name|entries
init|=
name|findLocalizationEntriesInJavaFiles
argument_list|(
name|type
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|keysInJavaFiles
init|=
name|entries
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|LocalizationEntry
operator|::
name|getKey
argument_list|)
operator|.
name|distinct
argument_list|()
operator|.
name|sorted
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
name|List
argument_list|<
name|String
argument_list|>
name|englishKeys
decl_stmt|;
if|if
condition|(
name|type
operator|==
name|LocalizationBundle
operator|.
name|LANG
condition|)
block|{
name|englishKeys
operator|=
name|getKeysInPropertiesFile
argument_list|(
literal|"/l10n/JabRef_en.properties"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|englishKeys
operator|=
name|getKeysInPropertiesFile
argument_list|(
literal|"/l10n/Menu_en.properties"
argument_list|)
expr_stmt|;
block|}
name|List
argument_list|<
name|String
argument_list|>
name|missingKeys
init|=
operator|new
name|LinkedList
argument_list|<>
argument_list|(
name|keysInJavaFiles
argument_list|)
decl_stmt|;
name|missingKeys
operator|.
name|removeAll
argument_list|(
name|englishKeys
argument_list|)
expr_stmt|;
return|return
name|entries
operator|.
name|stream
argument_list|()
operator|.
name|filter
argument_list|(
name|e
lambda|->
name|missingKeys
operator|.
name|contains
argument_list|(
name|e
operator|.
name|getKey
argument_list|()
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
return|;
block|}
DECL|method|findLocalizationEntriesInJavaFiles (LocalizationBundle type)
specifier|private
specifier|static
name|List
argument_list|<
name|LocalizationEntry
argument_list|>
name|findLocalizationEntriesInJavaFiles
parameter_list|(
name|LocalizationBundle
name|type
parameter_list|)
throws|throws
name|IOException
block|{
return|return
name|Files
operator|.
name|walk
argument_list|(
name|Paths
operator|.
name|get
argument_list|(
literal|"src/main"
argument_list|)
argument_list|)
operator|.
name|filter
argument_list|(
name|LocalizationParser
operator|::
name|isJavaFile
argument_list|)
operator|.
name|flatMap
argument_list|(
name|p
lambda|->
name|getLanguageKeysInJavaFile
argument_list|(
name|p
argument_list|,
name|type
argument_list|)
operator|.
name|stream
argument_list|()
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
argument_list|)
return|;
block|}
DECL|method|getKeysInPropertiesFile (String path)
specifier|public
specifier|static
name|List
argument_list|<
name|String
argument_list|>
name|getKeysInPropertiesFile
parameter_list|(
name|String
name|path
parameter_list|)
block|{
name|Properties
name|properties
init|=
name|getProperties
argument_list|(
name|path
argument_list|)
decl_stmt|;
return|return
name|properties
operator|.
name|keySet
argument_list|()
operator|.
name|stream
argument_list|()
operator|.
name|sorted
argument_list|()
operator|.
name|map
argument_list|(
name|Object
operator|::
name|toString
argument_list|)
operator|.
name|map
argument_list|(
name|String
operator|::
name|trim
argument_list|)
operator|.
name|map
argument_list|(
name|e
lambda|->
operator|new
name|Localization
operator|.
name|LocalizationKey
argument_list|(
name|e
argument_list|)
operator|.
name|getPropertiesKey
argument_list|()
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
argument_list|)
return|;
block|}
DECL|method|getProperties (String path)
specifier|private
specifier|static
name|Properties
name|getProperties
parameter_list|(
name|String
name|path
parameter_list|)
block|{
name|Properties
name|properties
init|=
operator|new
name|Properties
argument_list|()
decl_stmt|;
try|try
init|(
name|InputStream
name|is
init|=
name|LocalizationTest
operator|.
name|class
operator|.
name|getResourceAsStream
argument_list|(
name|path
argument_list|)
init|)
block|{
name|properties
operator|.
name|load
argument_list|(
name|is
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
return|return
name|properties
return|;
block|}
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
name|Assert
operator|.
name|assertEquals
argument_list|(
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
argument_list|,
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
name|Assert
operator|.
name|assertEquals
argument_list|(
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
argument_list|,
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
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|isJavaFile (Path path)
specifier|private
specifier|static
name|boolean
name|isJavaFile
parameter_list|(
name|Path
name|path
parameter_list|)
block|{
return|return
name|path
operator|.
name|toString
argument_list|()
operator|.
name|endsWith
argument_list|(
literal|".java"
argument_list|)
return|;
block|}
DECL|method|getLanguageKeysInJavaFile (Path path, LocalizationBundle type)
specifier|public
specifier|static
name|List
argument_list|<
name|LocalizationEntry
argument_list|>
name|getLanguageKeysInJavaFile
parameter_list|(
name|Path
name|path
parameter_list|,
name|LocalizationBundle
name|type
parameter_list|)
block|{
name|List
argument_list|<
name|LocalizationEntry
argument_list|>
name|result
init|=
operator|new
name|LinkedList
argument_list|<>
argument_list|()
decl_stmt|;
try|try
block|{
name|List
argument_list|<
name|String
argument_list|>
name|lines
init|=
name|Files
operator|.
name|readAllLines
argument_list|(
name|path
argument_list|,
name|Charsets
operator|.
name|UTF_8
argument_list|)
decl_stmt|;
name|String
name|content
init|=
name|String
operator|.
name|join
argument_list|(
literal|"\n"
argument_list|,
name|lines
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|keys
init|=
name|JavaLocalizationEntryParser
operator|.
name|getLanguageKeysInString
argument_list|(
name|content
argument_list|,
name|type
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|key
range|:
name|keys
control|)
block|{
name|result
operator|.
name|add
argument_list|(
operator|new
name|LocalizationEntry
argument_list|(
name|path
argument_list|,
name|key
argument_list|,
name|type
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|IOException
name|ignore
parameter_list|)
block|{
name|ignore
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
return|return
name|result
return|;
block|}
DECL|class|JavaLocalizationEntryParser
specifier|public
specifier|static
class|class
name|JavaLocalizationEntryParser
block|{
DECL|field|INFINITE_WHITESPACE
specifier|private
specifier|static
specifier|final
name|String
name|INFINITE_WHITESPACE
init|=
literal|"\\s*"
decl_stmt|;
DECL|field|DOT
specifier|private
specifier|static
specifier|final
name|String
name|DOT
init|=
literal|"\\."
decl_stmt|;
DECL|field|LOCALIZATION_START_PATTERN
specifier|private
specifier|static
specifier|final
name|Pattern
name|LOCALIZATION_START_PATTERN
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"Localization"
operator|+
name|INFINITE_WHITESPACE
operator|+
name|DOT
operator|+
name|INFINITE_WHITESPACE
operator|+
literal|"lang"
operator|+
name|INFINITE_WHITESPACE
operator|+
literal|"\\("
argument_list|)
decl_stmt|;
DECL|field|LOCALIZATION_MENU_START_PATTERN
specifier|private
specifier|static
specifier|final
name|Pattern
name|LOCALIZATION_MENU_START_PATTERN
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"Localization"
operator|+
name|INFINITE_WHITESPACE
operator|+
name|DOT
operator|+
name|INFINITE_WHITESPACE
operator|+
literal|"menuTitle"
operator|+
name|INFINITE_WHITESPACE
operator|+
literal|"\\("
argument_list|)
decl_stmt|;
DECL|field|ESCAPED_QUOTATION_SYMBOL
specifier|private
specifier|static
specifier|final
name|Pattern
name|ESCAPED_QUOTATION_SYMBOL
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"\\\\\""
argument_list|)
decl_stmt|;
DECL|field|QUOTATION_SYMBOL
specifier|private
specifier|static
specifier|final
name|Pattern
name|QUOTATION_SYMBOL
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"QUOTATIONPLACEHOLDER"
argument_list|)
decl_stmt|;
DECL|method|getLanguageKeysInString (String content, LocalizationBundle type)
specifier|public
specifier|static
name|List
argument_list|<
name|String
argument_list|>
name|getLanguageKeysInString
parameter_list|(
name|String
name|content
parameter_list|,
name|LocalizationBundle
name|type
parameter_list|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|result
init|=
operator|new
name|LinkedList
argument_list|<>
argument_list|()
decl_stmt|;
name|Matcher
name|matcher
decl_stmt|;
if|if
condition|(
name|type
operator|==
name|LocalizationBundle
operator|.
name|LANG
condition|)
block|{
name|matcher
operator|=
name|LOCALIZATION_START_PATTERN
operator|.
name|matcher
argument_list|(
name|content
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|matcher
operator|=
name|LOCALIZATION_MENU_START_PATTERN
operator|.
name|matcher
argument_list|(
name|content
argument_list|)
expr_stmt|;
block|}
while|while
condition|(
name|matcher
operator|.
name|find
argument_list|()
condition|)
block|{
comment|// find contents between the brackets, covering multi-line strings as well
name|int
name|index
init|=
name|matcher
operator|.
name|end
argument_list|()
decl_stmt|;
name|int
name|brackets
init|=
literal|1
decl_stmt|;
name|StringBuilder
name|buffer
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
while|while
condition|(
name|brackets
operator|!=
literal|0
condition|)
block|{
name|char
name|c
init|=
name|content
operator|.
name|charAt
argument_list|(
name|index
argument_list|)
decl_stmt|;
if|if
condition|(
name|c
operator|==
literal|'('
condition|)
block|{
name|brackets
operator|++
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|c
operator|==
literal|')'
condition|)
block|{
name|brackets
operator|--
expr_stmt|;
block|}
name|buffer
operator|.
name|append
argument_list|(
name|c
argument_list|)
expr_stmt|;
name|index
operator|++
expr_stmt|;
block|}
name|String
name|parsedContentsOfLangMethod
init|=
name|ESCAPED_QUOTATION_SYMBOL
operator|.
name|matcher
argument_list|(
name|buffer
operator|.
name|toString
argument_list|()
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"QUOTATIONPLACEHOLDER"
argument_list|)
decl_stmt|;
comment|// only retain what is within quotation
name|StringBuilder
name|b
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|int
name|quotations
init|=
literal|0
decl_stmt|;
for|for
control|(
name|char
name|c
range|:
name|parsedContentsOfLangMethod
operator|.
name|toCharArray
argument_list|()
control|)
block|{
if|if
condition|(
name|c
operator|==
literal|'"'
operator|&&
name|quotations
operator|>
literal|0
condition|)
block|{
name|quotations
operator|--
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|c
operator|==
literal|'"'
condition|)
block|{
name|quotations
operator|++
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|quotations
operator|!=
literal|0
condition|)
block|{
name|b
operator|.
name|append
argument_list|(
name|c
argument_list|)
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|c
operator|==
literal|','
condition|)
block|{
break|break;
block|}
block|}
block|}
block|}
name|String
name|languageKey
init|=
name|QUOTATION_SYMBOL
operator|.
name|matcher
argument_list|(
name|b
operator|.
name|toString
argument_list|()
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"\\\""
argument_list|)
decl_stmt|;
comment|// escape chars which are not allowed in property file keys
name|String
name|languagePropertyKey
init|=
operator|new
name|Localization
operator|.
name|LocalizationKey
argument_list|(
name|languageKey
argument_list|)
operator|.
name|getPropertiesKey
argument_list|()
decl_stmt|;
if|if
condition|(
name|languagePropertyKey
operator|.
name|endsWith
argument_list|(
literal|"_"
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|RuntimeException
argument_list|(
name|languageKey
operator|+
literal|" ends with a space. As this is a localization key, this is illegal!"
argument_list|)
throw|;
block|}
if|if
condition|(
name|languagePropertyKey
operator|.
name|contains
argument_list|(
literal|"\\n"
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|RuntimeException
argument_list|(
name|languageKey
operator|+
literal|" contains a new line character. As this is a localization key, this is illegal!"
argument_list|)
throw|;
block|}
if|if
condition|(
operator|!
name|languagePropertyKey
operator|.
name|trim
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|result
operator|.
name|add
argument_list|(
name|languagePropertyKey
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|result
return|;
block|}
block|}
block|}
end_class

end_unit

