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
name|ArrayList
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
name|Enumeration
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
import|;
end_import

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
name|MissingResourceException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Objects
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
name|java
operator|.
name|util
operator|.
name|ResourceBundle
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Set
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
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|Log
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|LogFactory
import|;
end_import

begin_comment
comment|/**  * Provides handling for messages and menu entries in the preferred language of the user.  *<p>  * Notes: All messages and menu-entries in JabRef are stored in escaped form like "This_is_a_message". This message  * serves as key inside the {@link l10n} properties files that hold the translation for many languages. When a message  * is accessed, it needs to be unescaped and possible parameters that can appear in a message need to be filled with  * values.  *<p>  * This implementation loads the appropriate language by importing all keys/values from the correct bundle and stores  * them in unescaped form inside a {@link LocalizationBundle} which provides fast access because it caches the key-value  * pairs.  *<p>  * The access to this is given by the functions {@link Localization#lang(String, String...)} and {@link  * Localization#menuTitle(String, String...)} that developers should use whenever they use strings for the e.g. GUI that  * need to be translatable.  */
end_comment

begin_class
DECL|class|Localization
specifier|public
class|class
name|Localization
block|{
DECL|field|BIBTEX
specifier|public
specifier|static
specifier|final
name|String
name|BIBTEX
init|=
literal|"BibTeX"
decl_stmt|;
DECL|field|RESOURCE_PREFIX
specifier|static
specifier|final
name|String
name|RESOURCE_PREFIX
init|=
literal|"l10n/JabRef"
decl_stmt|;
DECL|field|MENU_RESOURCE_PREFIX
specifier|static
specifier|final
name|String
name|MENU_RESOURCE_PREFIX
init|=
literal|"l10n/Menu"
decl_stmt|;
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Log
name|LOGGER
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|Localization
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|locale
specifier|private
specifier|static
name|Locale
name|locale
decl_stmt|;
DECL|field|localizedMessages
specifier|private
specifier|static
name|LocalizationBundle
name|localizedMessages
decl_stmt|;
DECL|field|localizedMenuTitles
specifier|private
specifier|static
name|LocalizationBundle
name|localizedMenuTitles
decl_stmt|;
DECL|method|Localization ()
specifier|private
name|Localization
parameter_list|()
block|{     }
comment|/**      * Public access to all messages that are not menu-entries      *      * @param key    The key of the message in unescaped form like "All fields"      * @param params Replacement strings for parameters %0, %1, etc.      * @return The message with replaced parameters      */
DECL|method|lang (String key, String... params)
specifier|public
specifier|static
name|String
name|lang
parameter_list|(
name|String
name|key
parameter_list|,
name|String
modifier|...
name|params
parameter_list|)
block|{
if|if
condition|(
name|localizedMessages
operator|==
literal|null
condition|)
block|{
comment|// I'm logging this because it should never happen
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Messages are not initialized before accessing "
operator|+
name|key
argument_list|)
expr_stmt|;
name|setLanguage
argument_list|(
literal|"en"
argument_list|)
expr_stmt|;
block|}
return|return
name|lookup
argument_list|(
name|localizedMessages
argument_list|,
literal|"message"
argument_list|,
name|key
argument_list|,
name|params
argument_list|)
return|;
block|}
comment|/**      * Public access to menu entry messages      *      * @param key    The key of the message in unescaped form like "Save all"      * @param params Replacement strings for parameters %0, %1, etc.      * @return The message with replaced parameters      */
DECL|method|menuTitle (String key, String... params)
specifier|public
specifier|static
name|String
name|menuTitle
parameter_list|(
name|String
name|key
parameter_list|,
name|String
modifier|...
name|params
parameter_list|)
block|{
if|if
condition|(
name|localizedMenuTitles
operator|==
literal|null
condition|)
block|{
comment|// I'm logging this because it should never happen
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Menu entries are not initialized"
argument_list|)
expr_stmt|;
name|setLanguage
argument_list|(
literal|"en"
argument_list|)
expr_stmt|;
block|}
return|return
name|lookup
argument_list|(
name|localizedMenuTitles
argument_list|,
literal|"menu item"
argument_list|,
name|key
argument_list|,
name|params
argument_list|)
return|;
block|}
comment|/**      * Sets the language and loads the appropriate translations. Note, that this function should be called before any      * other function of this class.      *      * @param language Language identifier like "en", "de", etc.      */
DECL|method|setLanguage (String language)
specifier|public
specifier|static
name|void
name|setLanguage
parameter_list|(
name|String
name|language
parameter_list|)
block|{
name|Optional
argument_list|<
name|Locale
argument_list|>
name|knownLanguage
init|=
name|Languages
operator|.
name|convertToSupportedLocale
argument_list|(
name|language
argument_list|)
decl_stmt|;
specifier|final
name|Locale
name|defaultLocale
init|=
name|Locale
operator|.
name|getDefault
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|knownLanguage
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Language "
operator|+
name|language
operator|+
literal|" is not supported by JabRef (Default:"
operator|+
name|defaultLocale
operator|+
literal|")"
argument_list|)
expr_stmt|;
name|setLanguage
argument_list|(
literal|"en"
argument_list|)
expr_stmt|;
return|return;
block|}
comment|// avoid reinitialization of the language bundles
specifier|final
name|Locale
name|langLocale
init|=
name|knownLanguage
operator|.
name|get
argument_list|()
decl_stmt|;
if|if
condition|(
operator|(
name|locale
operator|!=
literal|null
operator|)
operator|&&
name|locale
operator|.
name|equals
argument_list|(
name|langLocale
argument_list|)
operator|&&
name|locale
operator|.
name|equals
argument_list|(
name|defaultLocale
argument_list|)
condition|)
block|{
return|return;
block|}
name|locale
operator|=
name|langLocale
expr_stmt|;
name|Locale
operator|.
name|setDefault
argument_list|(
name|locale
argument_list|)
expr_stmt|;
name|javax
operator|.
name|swing
operator|.
name|JComponent
operator|.
name|setDefaultLocale
argument_list|(
name|locale
argument_list|)
expr_stmt|;
try|try
block|{
name|createResourceBundles
argument_list|(
name|locale
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|MissingResourceException
name|ex
parameter_list|)
block|{
comment|// should not happen as we have scripts to enforce this
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Could not find bundles for language "
operator|+
name|locale
operator|+
literal|", switching to full english language"
argument_list|,
name|ex
argument_list|)
expr_stmt|;
name|setLanguage
argument_list|(
literal|"en"
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Public access to the messages bundle for classes like AbstractView.      *      * @return The internally cashed bundle.      */
DECL|method|getMessages ()
specifier|public
specifier|static
name|LocalizationBundle
name|getMessages
parameter_list|()
block|{
comment|// avoid situations where this function is called before any language was set
if|if
condition|(
name|locale
operator|==
literal|null
condition|)
block|{
name|setLanguage
argument_list|(
literal|"en"
argument_list|)
expr_stmt|;
block|}
return|return
name|localizedMessages
return|;
block|}
comment|/**      * Creates and caches the language bundles used in JabRef for a particular language. This function first loads      * correct version of the "escaped" bundles that are given in {@link l10n}. After that, it stores the unescaped      * version in a cached {@link LocalizationBundle} for fast access.      *      * @param locale Localization to use.      */
DECL|method|createResourceBundles (Locale locale)
specifier|private
specifier|static
name|void
name|createResourceBundles
parameter_list|(
name|Locale
name|locale
parameter_list|)
block|{
name|ResourceBundle
name|messages
init|=
name|ResourceBundle
operator|.
name|getBundle
argument_list|(
name|RESOURCE_PREFIX
argument_list|,
name|locale
argument_list|,
operator|new
name|EncodingControl
argument_list|(
name|StandardCharsets
operator|.
name|UTF_8
argument_list|)
argument_list|)
decl_stmt|;
name|ResourceBundle
name|menuTitles
init|=
name|ResourceBundle
operator|.
name|getBundle
argument_list|(
name|MENU_RESOURCE_PREFIX
argument_list|,
name|locale
argument_list|,
operator|new
name|EncodingControl
argument_list|(
name|StandardCharsets
operator|.
name|UTF_8
argument_list|)
argument_list|)
decl_stmt|;
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|messages
argument_list|,
literal|"Could not load "
operator|+
name|RESOURCE_PREFIX
operator|+
literal|" resource."
argument_list|)
expr_stmt|;
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|menuTitles
argument_list|,
literal|"Could not load "
operator|+
name|MENU_RESOURCE_PREFIX
operator|+
literal|" resource."
argument_list|)
expr_stmt|;
name|localizedMenuTitles
operator|=
operator|new
name|LocalizationBundle
argument_list|(
name|createLookupMap
argument_list|(
name|menuTitles
argument_list|)
argument_list|)
expr_stmt|;
name|localizedMessages
operator|=
operator|new
name|LocalizationBundle
argument_list|(
name|createLookupMap
argument_list|(
name|messages
argument_list|,
name|localizedMenuTitles
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * Helper function to create a HashMap from the key/value pairs of a bundle.      *      * @param baseBundle JabRef language bundle with keys and values for translations.      * @return Lookup map for the baseBundle.      */
DECL|method|createLookupMap (ResourceBundle baseBundle)
specifier|private
specifier|static
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|createLookupMap
parameter_list|(
name|ResourceBundle
name|baseBundle
parameter_list|)
block|{
specifier|final
name|ArrayList
argument_list|<
name|String
argument_list|>
name|baseKeys
init|=
name|Collections
operator|.
name|list
argument_list|(
name|baseBundle
operator|.
name|getKeys
argument_list|()
argument_list|)
decl_stmt|;
return|return
operator|new
name|HashMap
argument_list|<>
argument_list|(
name|baseKeys
operator|.
name|stream
argument_list|()
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toMap
argument_list|(
name|key
lambda|->
operator|new
name|LocalizationKey
argument_list|(
name|key
argument_list|)
operator|.
name|getTranslationValue
argument_list|()
argument_list|,
name|key
lambda|->
operator|new
name|LocalizationKey
argument_list|(
name|baseBundle
operator|.
name|getString
argument_list|(
name|key
argument_list|)
argument_list|)
operator|.
name|getTranslationValue
argument_list|()
argument_list|)
argument_list|)
argument_list|)
return|;
block|}
comment|/**      * Helper function to create a HashMap from the key/value pairs of a bundle and existing localized menu titles.      * Currently, JabRef has two translations for the same string: One for the menu and one for other parts of the      * application. The menu might contain an ampersand (&), which causes issues when used outside the menu.      * With this fix, the ampersand is removed      */
DECL|method|createLookupMap (ResourceBundle baseBundle, LocalizationBundle localizedMenuTitles)
specifier|private
specifier|static
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|createLookupMap
parameter_list|(
name|ResourceBundle
name|baseBundle
parameter_list|,
name|LocalizationBundle
name|localizedMenuTitles
parameter_list|)
block|{
specifier|final
name|ArrayList
argument_list|<
name|String
argument_list|>
name|baseKeys
init|=
name|Collections
operator|.
name|list
argument_list|(
name|baseBundle
operator|.
name|getKeys
argument_list|()
argument_list|)
decl_stmt|;
return|return
operator|new
name|HashMap
argument_list|<>
argument_list|(
name|baseKeys
operator|.
name|stream
argument_list|()
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toMap
argument_list|(
name|key
lambda|->
operator|new
name|LocalizationKey
argument_list|(
name|key
argument_list|)
operator|.
name|getTranslationValue
argument_list|()
argument_list|,
name|key
lambda|->
block|{
name|String
name|menuTranslationValue
operator|=
name|localizedMenuTitles
operator|.
name|lookup
operator|.
name|get
argument_list|(
name|key
argument_list|)
argument_list|;
name|String
name|plainTranslationValue
operator|=
operator|new
name|LocalizationKey
argument_list|(
name|baseBundle
operator|.
name|getString
argument_list|(
name|key
argument_list|)
argument_list|)
operator|.
name|getTranslationValue
argument_list|()
argument_list|;
name|String
name|translationValue
argument_list|;                             if
operator|(
name|plainTranslationValue
operator|.
name|contains
argument_list|(
literal|"&"
argument_list|)
operator|&&
name|plainTranslationValue
operator|.
name|equals
argument_list|(
name|menuTranslationValue
argument_list|)
operator|)
block|{
name|translationValue
operator|=
name|plainTranslationValue
operator|.
name|replace
argument_list|(
literal|"&"
argument_list|,
literal|""
argument_list|)
block|;                             }
else|else
block|{
name|translationValue
operator|=
name|plainTranslationValue
expr_stmt|;
block|}
argument_list|return
name|translationValue
argument_list|;
block|}
block|)
end_class

begin_empty_stmt
unit|))
empty_stmt|;
end_empty_stmt

begin_comment
unit|}
comment|/**      * This looks up a key in the bundle and replaces parameters %0, ..., %9 with the respective params given. Note that      * the keys are the "unescaped" strings from the bundle property files.      *      * @param bundle            The {@link LocalizationBundle} which means either {@link Localization#localizedMenuTitles}      *                          or {@link Localization#localizedMessages}.      * @param idForErrorMessage Identifier-string when the translation is not found.      * @param key               The lookup key.      * @param params            The parameters that should be inserted into the message      * @return The final message with replaced parameters.      */
end_comment

begin_function
DECL|method|lookup (LocalizationBundle bundle, String idForErrorMessage, String key, String... params)
unit|private
specifier|static
name|String
name|lookup
parameter_list|(
name|LocalizationBundle
name|bundle
parameter_list|,
name|String
name|idForErrorMessage
parameter_list|,
name|String
name|key
parameter_list|,
name|String
modifier|...
name|params
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|key
argument_list|)
expr_stmt|;
name|String
name|translation
init|=
name|bundle
operator|.
name|containsKey
argument_list|(
name|key
argument_list|)
condition|?
name|bundle
operator|.
name|getString
argument_list|(
name|key
argument_list|)
else|:
literal|""
decl_stmt|;
if|if
condition|(
name|translation
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Warning: could not get "
operator|+
name|idForErrorMessage
operator|+
literal|" translation for \""
operator|+
name|key
operator|+
literal|"\" for locale "
operator|+
name|Locale
operator|.
name|getDefault
argument_list|()
argument_list|)
expr_stmt|;
name|translation
operator|=
name|key
expr_stmt|;
block|}
return|return
operator|new
name|LocalizationKeyParams
argument_list|(
name|translation
argument_list|,
name|params
argument_list|)
operator|.
name|replacePlaceholders
argument_list|()
return|;
block|}
end_function

begin_comment
comment|/**      * A bundle for caching localized strings. Needed to support JavaFX inline binding.      */
end_comment

begin_class
DECL|class|LocalizationBundle
specifier|private
specifier|static
class|class
name|LocalizationBundle
extends|extends
name|ResourceBundle
block|{
DECL|field|lookup
specifier|private
specifier|final
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|lookup
decl_stmt|;
DECL|method|LocalizationBundle (HashMap<String, String> lookupMap)
name|LocalizationBundle
parameter_list|(
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|lookupMap
parameter_list|)
block|{
name|lookup
operator|=
name|lookupMap
expr_stmt|;
block|}
DECL|method|handleGetObject (String key)
specifier|public
specifier|final
name|Object
name|handleGetObject
parameter_list|(
name|String
name|key
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|key
argument_list|)
expr_stmt|;
return|return
name|lookup
operator|.
name|get
argument_list|(
name|key
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|getKeys ()
specifier|public
name|Enumeration
argument_list|<
name|String
argument_list|>
name|getKeys
parameter_list|()
block|{
return|return
name|Collections
operator|.
name|enumeration
argument_list|(
name|lookup
operator|.
name|keySet
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|handleKeySet ()
specifier|protected
name|Set
argument_list|<
name|String
argument_list|>
name|handleKeySet
parameter_list|()
block|{
return|return
name|lookup
operator|.
name|keySet
argument_list|()
return|;
block|}
annotation|@
name|Override
DECL|method|containsKey (String key)
specifier|public
name|boolean
name|containsKey
parameter_list|(
name|String
name|key
parameter_list|)
block|{
return|return
operator|(
name|key
operator|!=
literal|null
operator|)
operator|&&
name|lookup
operator|.
name|containsKey
argument_list|(
name|key
argument_list|)
return|;
block|}
block|}
end_class

unit|}
end_unit

