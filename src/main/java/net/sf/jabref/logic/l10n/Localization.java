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
name|*
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
DECL|field|defaultLocale
specifier|private
specifier|static
specifier|final
name|Locale
name|defaultLocale
init|=
name|Locale
operator|.
name|getDefault
argument_list|()
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
name|Locale
name|locale
init|=
operator|new
name|Locale
argument_list|(
name|language
argument_list|)
decl_stmt|;
try|try
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
comment|// silent fallback to system locale when bundle is not found
if|if
condition|(
operator|!
name|messages
operator|.
name|getLocale
argument_list|()
operator|.
name|equals
argument_list|(
name|locale
argument_list|)
condition|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Bundle for locale<"
operator|+
name|locale
operator|+
literal|"> not found. Falling back to system locale<"
operator|+
name|defaultLocale
operator|+
literal|">"
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|MissingResourceException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Bundle for locale<"
operator|+
name|locale
operator|+
literal|"> not found. Fallback to system locale<"
operator|+
name|defaultLocale
operator|+
literal|"> failed, using locale<en> instead"
argument_list|,
name|e
argument_list|)
expr_stmt|;
name|locale
operator|=
operator|new
name|Locale
argument_list|(
literal|"en"
argument_list|)
expr_stmt|;
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
finally|finally
block|{
comment|// Set consistent VM locales
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
block|}
block|}
comment|/**      * In the translation, %c is translated to ":", %e is translated to "=", %<anythingelse> to<anythingelse>, %0, ...      * %9 to the respective params given      *      * @param resBundle the ResourceBundle to use      * @param idForErrorMessage output when translation is not found Ã¶ * @param key the key to lookup in resBundle      * @param params a list of Strings to replace %0, %1, ...      * @return      */
DECL|method|translate (ResourceBundle resBundle, String idForErrorMessage, String key, String... params)
specifier|private
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
name|String
name|translation
init|=
literal|null
decl_stmt|;
try|try
block|{
if|if
condition|(
name|resBundle
operator|!=
literal|null
condition|)
block|{
name|translation
operator|=
name|resBundle
operator|.
name|getString
argument_list|(
name|key
operator|.
name|replaceAll
argument_list|(
literal|" "
argument_list|,
literal|"_"
argument_list|)
argument_list|)
expr_stmt|;
block|}
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
name|translation
operator|==
literal|null
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
comment|// replace %0, %1, ...
if|if
condition|(
operator|(
name|translation
operator|!=
literal|null
operator|)
operator|&&
operator|!
name|translation
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
comment|// also done if no params are given
comment|//  Then, %c is translated to ":", %e is translated to "=", ...
operator|new
name|Translation
argument_list|(
name|translation
argument_list|,
name|params
argument_list|)
operator|.
name|translate
argument_list|()
expr_stmt|;
block|}
return|return
name|key
return|;
block|}
DECL|class|Translation
specifier|private
specifier|static
class|class
name|Translation
block|{
DECL|field|key
specifier|private
specifier|final
name|String
name|key
decl_stmt|;
DECL|field|params
specifier|private
specifier|final
name|List
argument_list|<
name|String
argument_list|>
name|params
decl_stmt|;
DECL|method|Translation (String key, String... params)
specifier|public
name|Translation
parameter_list|(
name|String
name|key
parameter_list|,
name|String
modifier|...
name|params
parameter_list|)
block|{
name|this
operator|.
name|key
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|key
argument_list|)
expr_stmt|;
name|this
operator|.
name|params
operator|=
name|Arrays
operator|.
name|asList
argument_list|(
name|params
argument_list|)
expr_stmt|;
if|if
condition|(
name|this
operator|.
name|params
operator|.
name|size
argument_list|()
operator|>
literal|10
condition|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Translations can only have at most 10 parameters"
argument_list|)
throw|;
block|}
block|}
DECL|method|translate ()
specifier|public
name|String
name|translate
parameter_list|()
block|{
name|String
name|translation
init|=
name|key
operator|.
name|replaceAll
argument_list|(
literal|"_"
argument_list|,
literal|" "
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"%e"
argument_list|,
literal|"="
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"%c"
argument_list|,
literal|":"
argument_list|)
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|params
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|String
name|param
init|=
name|params
operator|.
name|get
argument_list|(
name|i
argument_list|)
decl_stmt|;
name|translation
operator|=
name|translation
operator|.
name|replaceAll
argument_list|(
literal|"%"
operator|+
name|i
argument_list|,
name|param
argument_list|)
expr_stmt|;
block|}
return|return
name|translation
return|;
block|}
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
DECL|method|lang (String key)
specifier|public
specifier|static
name|String
name|lang
parameter_list|(
name|String
name|key
parameter_list|)
block|{
return|return
name|lang
argument_list|(
name|key
argument_list|,
operator|(
name|String
index|[]
operator|)
literal|null
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
DECL|method|getMenuTitleKeys ()
specifier|public
specifier|static
name|List
argument_list|<
name|String
argument_list|>
name|getMenuTitleKeys
parameter_list|()
block|{
return|return
operator|new
name|LinkedList
argument_list|<>
argument_list|(
name|menuTitles
operator|.
name|keySet
argument_list|()
argument_list|)
return|;
block|}
block|}
end_class

end_unit

