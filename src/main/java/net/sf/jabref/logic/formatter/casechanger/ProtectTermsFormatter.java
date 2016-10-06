begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.formatter.casechanger
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
name|casechanger
package|;
end_package

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
name|Objects
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|protectedterms
operator|.
name|ProtectedTermsLoader
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
name|util
operator|.
name|strings
operator|.
name|StringLengthComparator
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
name|model
operator|.
name|cleanup
operator|.
name|Formatter
import|;
end_import

begin_class
DECL|class|ProtectTermsFormatter
specifier|public
class|class
name|ProtectTermsFormatter
implements|implements
name|Formatter
block|{
DECL|field|protectedTermsLoader
specifier|private
specifier|static
name|ProtectedTermsLoader
name|protectedTermsLoader
decl_stmt|;
comment|/**      * @deprecated use ProtectTermsFormatter(ProtectedTermsLoader) instead      */
annotation|@
name|Deprecated
DECL|method|ProtectTermsFormatter ()
specifier|public
name|ProtectTermsFormatter
parameter_list|()
block|{     }
DECL|method|ProtectTermsFormatter (ProtectedTermsLoader protectedTermsLoader)
specifier|public
name|ProtectTermsFormatter
parameter_list|(
name|ProtectedTermsLoader
name|protectedTermsLoader
parameter_list|)
block|{
name|ProtectTermsFormatter
operator|.
name|protectedTermsLoader
operator|=
name|protectedTermsLoader
expr_stmt|;
block|}
comment|/**      * This must be called from JabRefMain      *      * @deprecated use ProtectTermsFormatter(ProtectedTermsLoader) instead      */
annotation|@
name|Deprecated
DECL|method|setProtectedTermsLoader (ProtectedTermsLoader loader)
specifier|public
specifier|static
name|void
name|setProtectedTermsLoader
parameter_list|(
name|ProtectedTermsLoader
name|loader
parameter_list|)
block|{
name|protectedTermsLoader
operator|=
name|loader
expr_stmt|;
block|}
DECL|method|format (String text, List<String> listOfWords)
specifier|private
name|String
name|format
parameter_list|(
name|String
name|text
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|listOfWords
parameter_list|)
block|{
name|String
name|result
init|=
name|text
decl_stmt|;
name|listOfWords
operator|.
name|sort
argument_list|(
operator|new
name|StringLengthComparator
argument_list|()
argument_list|)
expr_stmt|;
comment|// For each word in the list
for|for
control|(
name|String
name|listOfWord
range|:
name|listOfWords
control|)
block|{
comment|// Add {} if the character before is a space, -, /, (, [, ", or } or if it is at the start of the string but not if it is followed by a }
name|result
operator|=
name|result
operator|.
name|replaceAll
argument_list|(
literal|"(^|[- /\\[(}\"])"
operator|+
name|listOfWord
operator|+
literal|"($|[^a-zA-Z}])"
argument_list|,
literal|"$1\\{"
operator|+
name|listOfWord
operator|+
literal|"\\}$2"
argument_list|)
expr_stmt|;
block|}
return|return
name|result
return|;
block|}
annotation|@
name|Override
DECL|method|format (String text)
specifier|public
name|String
name|format
parameter_list|(
name|String
name|text
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|text
argument_list|)
expr_stmt|;
if|if
condition|(
name|text
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
name|text
return|;
block|}
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|ProtectTermsFormatter
operator|.
name|protectedTermsLoader
argument_list|)
expr_stmt|;
return|return
name|this
operator|.
name|format
argument_list|(
name|text
argument_list|,
name|ProtectTermsFormatter
operator|.
name|protectedTermsLoader
operator|.
name|getProtectedTerms
argument_list|()
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
literal|"Adds {} brackets around acronyms, month names and countries to preserve their case."
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
literal|"In CDMA"
return|;
block|}
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
literal|"Protect terms"
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
literal|"protect_terms"
return|;
block|}
block|}
end_class

end_unit

