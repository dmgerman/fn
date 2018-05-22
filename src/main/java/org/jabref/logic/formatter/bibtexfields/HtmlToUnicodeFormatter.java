begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.formatter.bibtexfields
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|formatter
operator|.
name|bibtexfields
package|;
end_package

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|architecture
operator|.
name|ApacheCommonsLang3Allowed
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|l10n
operator|.
name|Localization
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|layout
operator|.
name|LayoutFormatter
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|cleanup
operator|.
name|Formatter
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
name|lang3
operator|.
name|StringEscapeUtils
import|;
end_import

begin_class
annotation|@
name|ApacheCommonsLang3Allowed
argument_list|(
literal|"There is no equivalent in Google's Guava"
argument_list|)
DECL|class|HtmlToUnicodeFormatter
specifier|public
class|class
name|HtmlToUnicodeFormatter
extends|extends
name|Formatter
implements|implements
name|LayoutFormatter
block|{
annotation|@
name|Override
DECL|method|getName ()
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"HTML to Unicode"
return|;
block|}
annotation|@
name|Override
DECL|method|getKey ()
specifier|public
name|String
name|getKey
parameter_list|()
block|{
return|return
literal|"html_to_unicode"
return|;
block|}
annotation|@
name|Override
DECL|method|getDescription ()
specifier|public
name|String
name|getDescription
parameter_list|()
block|{
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"Converts HTML code to Unicode."
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|getExampleInput ()
specifier|public
name|String
name|getExampleInput
parameter_list|()
block|{
return|return
literal|"<b>bread</b>&amp; butter"
return|;
block|}
annotation|@
name|Override
DECL|method|format (String fieldText)
specifier|public
name|String
name|format
parameter_list|(
name|String
name|fieldText
parameter_list|)
block|{
comment|// StringEscapeUtils converts characters and regex kills tags
return|return
name|StringEscapeUtils
operator|.
name|unescapeHtml4
argument_list|(
name|fieldText
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"\\<[^>]*>"
argument_list|,
literal|""
argument_list|)
return|;
block|}
block|}
end_class

end_unit

