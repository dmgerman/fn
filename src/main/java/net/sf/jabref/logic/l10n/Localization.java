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

begin_class
DECL|class|Localization
specifier|public
class|class
name|Localization
block|{
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
DECL|field|RESOURCE_PREFIX
specifier|public
specifier|static
specifier|final
name|String
name|RESOURCE_PREFIX
init|=
literal|"l10n/JabRef"
decl_stmt|;
DECL|field|MENU_RESOURCE_PREFIX
specifier|public
specifier|static
specifier|final
name|String
name|MENU_RESOURCE_PREFIX
init|=
literal|"l10n/Menu"
decl_stmt|;
DECL|field|messages
specifier|private
specifier|static
name|ResourceBundle
name|messages
decl_stmt|;
DECL|field|menuTitles
specifier|private
specifier|static
name|ResourceBundle
name|menuTitles
decl_stmt|;
DECL|method|getMessages ()
specifier|public
specifier|static
name|LocalizationBundle
name|getMessages
parameter_list|()
block|{
return|return
operator|new
name|LocalizationBundle
argument_list|(
name|messages
argument_list|)
return|;
block|}
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
name|String
argument_list|>
name|knownLanguage
init|=
name|Languages
operator|.
name|convertToKnownLocale
argument_list|(
name|language
argument_list|)
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
name|Locale
operator|.
name|getDefault
argument_list|()
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
name|String
index|[]
name|languageParts
init|=
name|knownLanguage
operator|.
name|get
argument_list|()
operator|.
name|split
argument_list|(
literal|"_"
argument_list|)
decl_stmt|;
name|Locale
name|locale
decl_stmt|;
if|if
condition|(
name|languageParts
operator|.
name|length
operator|==
literal|1
condition|)
block|{
name|locale
operator|=
operator|new
name|Locale
argument_list|(
name|languageParts
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|languageParts
operator|.
name|length
operator|==
literal|2
condition|)
block|{
name|locale
operator|=
operator|new
name|Locale
argument_list|(
name|languageParts
index|[
literal|0
index|]
argument_list|,
name|languageParts
index|[
literal|1
index|]
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|locale
operator|=
name|Locale
operator|.
name|ENGLISH
expr_stmt|;
block|}
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
name|e
parameter_list|)
block|{
comment|// SHOULD NOT HAPPEN AS WE HAVE SCRIPTS TO COVER FOR THIS
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
name|e
argument_list|)
expr_stmt|;
name|setLanguage
argument_list|(
literal|"en"
argument_list|)
expr_stmt|;
block|}
block|}
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
name|messages
operator|=
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
expr_stmt|;
name|menuTitles
operator|=
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
expr_stmt|;
block|}
comment|/**      * In the translation, %0, ..., %9 is replaced by the respective params given      *      * @param resBundle         the ResourceBundle to use      * @param idForErrorMessage output when translation is not found      * @param key               the key to lookup in resBundle      * @param params            a list of Strings to replace %0, %1, ...      * @return      */
DECL|method|translate (ResourceBundle resBundle, String idForErrorMessage, String key, String... params)
specifier|protected
specifier|static
name|String
name|translate
parameter_list|(
name|ResourceBundle
name|resBundle
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
name|resBundle
argument_list|)
expr_stmt|;
name|String
name|translation
init|=
literal|null
decl_stmt|;
try|try
block|{
name|String
name|propertiesKey
init|=
operator|new
name|LocalizationKey
argument_list|(
name|key
argument_list|)
operator|.
name|getPropertiesKeyUnescaped
argument_list|()
decl_stmt|;
name|translation
operator|=
name|resBundle
operator|.
name|getString
argument_list|(
name|propertiesKey
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|MissingResourceException
name|ex
parameter_list|)
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
block|}
if|if
condition|(
operator|(
name|translation
operator|==
literal|null
operator|)
operator|||
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
literal|"Warning: no "
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
name|messages
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
name|translate
argument_list|(
name|messages
argument_list|,
literal|"message"
argument_list|,
name|key
argument_list|,
name|params
argument_list|)
return|;
block|}
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
name|menuTitles
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
name|translate
argument_list|(
name|menuTitles
argument_list|,
literal|"menu item"
argument_list|,
name|key
argument_list|,
name|params
argument_list|)
return|;
block|}
block|}
end_class

end_unit

