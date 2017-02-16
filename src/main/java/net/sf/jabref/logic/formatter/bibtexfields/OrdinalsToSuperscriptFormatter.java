begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.formatter.bibtexfields
package|package
name|net
operator|.
name|sf
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
name|net
operator|.
name|sf
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
name|model
operator|.
name|cleanup
operator|.
name|Formatter
import|;
end_import

begin_comment
comment|/**  * This class transforms ordinal numbers into LaTex superscripts.  */
end_comment

begin_class
DECL|class|OrdinalsToSuperscriptFormatter
specifier|public
class|class
name|OrdinalsToSuperscriptFormatter
implements|implements
name|Formatter
block|{
comment|// find possible superscripts on word boundaries
DECL|field|SUPERSCRIPT_DETECT_PATTERN
specifier|private
specifier|static
specifier|final
name|Pattern
name|SUPERSCRIPT_DETECT_PATTERN
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"\\b(\\d+)(st|nd|rd|th)\\b"
argument_list|,
name|Pattern
operator|.
name|CASE_INSENSITIVE
operator||
name|Pattern
operator|.
name|MULTILINE
argument_list|)
decl_stmt|;
DECL|field|SUPERSCRIPT_REPLACE_PATTERN
specifier|private
specifier|static
specifier|final
name|String
name|SUPERSCRIPT_REPLACE_PATTERN
init|=
literal|"$1\\\\textsuperscript{$2}"
decl_stmt|;
annotation|@
name|Override
DECL|method|getName ()
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"Ordinals to LaTeX superscript"
argument_list|)
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
literal|"ordinals_to_superscript"
return|;
block|}
comment|/**      * Converts ordinal numbers to superscripts, e.g. 1st, 2nd or 3rd.      * Will replace ordinal numbers even if they are semantically wrong, e.g. 21rd      *      *<example>      * 1st Conf. Cloud Computing -> 1\textsuperscript{st} Conf. Cloud Computing      *</example>      */
annotation|@
name|Override
DECL|method|format (String value)
specifier|public
name|String
name|format
parameter_list|(
name|String
name|value
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|value
argument_list|)
expr_stmt|;
if|if
condition|(
name|value
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
comment|// nothing to do
return|return
name|value
return|;
block|}
name|Matcher
name|matcher
init|=
name|SUPERSCRIPT_DETECT_PATTERN
operator|.
name|matcher
argument_list|(
name|value
argument_list|)
decl_stmt|;
comment|// replace globally
comment|// adds superscript tag
return|return
name|matcher
operator|.
name|replaceAll
argument_list|(
name|SUPERSCRIPT_REPLACE_PATTERN
argument_list|)
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
literal|"Converts ordinals to LaTeX superscripts."
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
literal|"11th"
return|;
block|}
block|}
end_class

end_unit

