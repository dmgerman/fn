begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.integrity
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|integrity
package|;
end_package

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
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|bibtexkeypattern
operator|.
name|BibtexKeyGenerator
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
name|model
operator|.
name|strings
operator|.
name|StringUtil
import|;
end_import

begin_comment
comment|/**  * Makes sure the key is legal  */
end_comment

begin_class
DECL|class|ValidBibtexKeyChecker
specifier|public
class|class
name|ValidBibtexKeyChecker
implements|implements
name|ValueChecker
block|{
DECL|field|enforceLegalKey
specifier|private
specifier|final
name|boolean
name|enforceLegalKey
decl_stmt|;
DECL|method|ValidBibtexKeyChecker (boolean enforceLegalKey)
specifier|public
name|ValidBibtexKeyChecker
parameter_list|(
name|boolean
name|enforceLegalKey
parameter_list|)
block|{
name|this
operator|.
name|enforceLegalKey
operator|=
name|enforceLegalKey
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|checkValue (String value)
specifier|public
name|Optional
argument_list|<
name|String
argument_list|>
name|checkValue
parameter_list|(
name|String
name|value
parameter_list|)
block|{
if|if
condition|(
name|StringUtil
operator|.
name|isNullOrEmpty
argument_list|(
name|value
argument_list|)
condition|)
block|{
return|return
name|Optional
operator|.
name|of
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"empty BibTeX key"
argument_list|)
argument_list|)
return|;
block|}
name|String
name|cleaned
init|=
name|BibtexKeyGenerator
operator|.
name|cleanKey
argument_list|(
name|value
argument_list|,
name|enforceLegalKey
argument_list|)
decl_stmt|;
if|if
condition|(
name|cleaned
operator|.
name|equals
argument_list|(
name|value
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
else|else
block|{
return|return
name|Optional
operator|.
name|of
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Invalid BibTeX key"
argument_list|)
argument_list|)
return|;
block|}
block|}
block|}
end_class

end_unit

