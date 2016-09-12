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
name|Map
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
name|TreeMap
import|;
end_import

begin_class
DECL|class|Languages
specifier|public
class|class
name|Languages
block|{
DECL|field|LANGUAGES
specifier|public
specifier|static
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|LANGUAGES
decl_stmt|;
static|static
block|{
name|LANGUAGES
operator|=
operator|new
name|TreeMap
argument_list|<>
argument_list|()
expr_stmt|;
comment|// LANGUAGES contains mappings for supported languages.
name|LANGUAGES
operator|.
name|put
argument_list|(
literal|"Dansk"
argument_list|,
literal|"da"
argument_list|)
expr_stmt|;
name|LANGUAGES
operator|.
name|put
argument_list|(
literal|"Deutsch"
argument_list|,
literal|"de"
argument_list|)
expr_stmt|;
name|LANGUAGES
operator|.
name|put
argument_list|(
literal|"English"
argument_list|,
literal|"en"
argument_list|)
expr_stmt|;
name|LANGUAGES
operator|.
name|put
argument_list|(
literal|"EspaÃ±ol"
argument_list|,
literal|"es"
argument_list|)
expr_stmt|;
name|LANGUAGES
operator|.
name|put
argument_list|(
literal|"Persian (ÙØ§Ø±Ø³Û)"
argument_list|,
literal|"fa"
argument_list|)
expr_stmt|;
name|LANGUAGES
operator|.
name|put
argument_list|(
literal|"FranÃ§ais"
argument_list|,
literal|"fr"
argument_list|)
expr_stmt|;
name|LANGUAGES
operator|.
name|put
argument_list|(
literal|"Bahasa Indonesia"
argument_list|,
literal|"in"
argument_list|)
expr_stmt|;
name|LANGUAGES
operator|.
name|put
argument_list|(
literal|"Italiano"
argument_list|,
literal|"it"
argument_list|)
expr_stmt|;
name|LANGUAGES
operator|.
name|put
argument_list|(
literal|"Japanese"
argument_list|,
literal|"ja"
argument_list|)
expr_stmt|;
name|LANGUAGES
operator|.
name|put
argument_list|(
literal|"Nederlands"
argument_list|,
literal|"nl"
argument_list|)
expr_stmt|;
name|LANGUAGES
operator|.
name|put
argument_list|(
literal|"Norsk"
argument_list|,
literal|"no"
argument_list|)
expr_stmt|;
name|LANGUAGES
operator|.
name|put
argument_list|(
literal|"Brazilian Portuguese"
argument_list|,
literal|"pt_BR"
argument_list|)
expr_stmt|;
name|LANGUAGES
operator|.
name|put
argument_list|(
literal|"Russian"
argument_list|,
literal|"ru"
argument_list|)
expr_stmt|;
name|LANGUAGES
operator|.
name|put
argument_list|(
literal|"Svenska"
argument_list|,
literal|"sv"
argument_list|)
expr_stmt|;
name|LANGUAGES
operator|.
name|put
argument_list|(
literal|"Turkish"
argument_list|,
literal|"tr"
argument_list|)
expr_stmt|;
name|LANGUAGES
operator|.
name|put
argument_list|(
literal|"Vietnamese"
argument_list|,
literal|"vi"
argument_list|)
expr_stmt|;
name|LANGUAGES
operator|.
name|put
argument_list|(
literal|"Simplified Chinese"
argument_list|,
literal|"zh"
argument_list|)
expr_stmt|;
block|}
DECL|method|convertToSupportedLocale (String language)
specifier|public
specifier|static
name|Optional
argument_list|<
name|Locale
argument_list|>
name|convertToSupportedLocale
parameter_list|(
name|String
name|language
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|language
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|LANGUAGES
operator|.
name|values
argument_list|()
operator|.
name|contains
argument_list|(
name|language
argument_list|)
condition|)
block|{
if|if
condition|(
operator|!
name|language
operator|.
name|contains
argument_list|(
literal|"_"
argument_list|)
condition|)
block|{
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
name|String
name|lang
init|=
name|language
operator|.
name|split
argument_list|(
literal|"_"
argument_list|)
index|[
literal|0
index|]
decl_stmt|;
if|if
condition|(
operator|!
name|LANGUAGES
operator|.
name|values
argument_list|()
operator|.
name|contains
argument_list|(
name|lang
argument_list|)
condition|)
block|{
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
return|return
name|Optional
operator|.
name|of
argument_list|(
operator|new
name|Locale
argument_list|(
name|lang
argument_list|)
argument_list|)
return|;
block|}
return|return
name|Optional
operator|.
name|of
argument_list|(
operator|new
name|Locale
argument_list|(
name|language
argument_list|)
argument_list|)
return|;
block|}
block|}
end_class

end_unit

