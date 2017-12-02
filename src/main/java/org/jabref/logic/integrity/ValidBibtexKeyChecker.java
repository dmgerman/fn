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
name|Globals
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
name|BibtexKeyPatternUtil
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
name|preferences
operator|.
name|JabRefPreferences
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
name|String
name|cleaned
init|=
name|BibtexKeyPatternUtil
operator|.
name|checkLegalKey
argument_list|(
name|value
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|ENFORCE_LEGAL_BIBTEX_KEY
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|cleaned
operator|==
literal|null
operator|)
operator|||
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

