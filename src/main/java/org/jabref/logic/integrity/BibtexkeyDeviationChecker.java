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
name|Collections
import|;
end_import

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
name|bibtexkeypattern
operator|.
name|BibtexKeyPatternPreferences
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
name|integrity
operator|.
name|IntegrityCheck
operator|.
name|Checker
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
name|database
operator|.
name|BibDatabaseContext
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
name|entry
operator|.
name|BibEntry
import|;
end_import

begin_class
DECL|class|BibtexkeyDeviationChecker
specifier|public
class|class
name|BibtexkeyDeviationChecker
implements|implements
name|Checker
block|{
DECL|field|bibDatabaseContext
specifier|private
specifier|final
name|BibDatabaseContext
name|bibDatabaseContext
decl_stmt|;
DECL|field|bibtexKeyPatternPreferences
specifier|private
specifier|final
name|BibtexKeyPatternPreferences
name|bibtexKeyPatternPreferences
decl_stmt|;
DECL|method|BibtexkeyDeviationChecker (BibDatabaseContext bibDatabaseContext, BibtexKeyPatternPreferences bibtexKeyPatternPreferences)
specifier|public
name|BibtexkeyDeviationChecker
parameter_list|(
name|BibDatabaseContext
name|bibDatabaseContext
parameter_list|,
name|BibtexKeyPatternPreferences
name|bibtexKeyPatternPreferences
parameter_list|)
block|{
name|this
operator|.
name|bibDatabaseContext
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|bibDatabaseContext
argument_list|)
expr_stmt|;
name|this
operator|.
name|bibtexKeyPatternPreferences
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|bibtexKeyPatternPreferences
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|check (BibEntry entry)
specifier|public
name|List
argument_list|<
name|IntegrityMessage
argument_list|>
name|check
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
name|Optional
argument_list|<
name|String
argument_list|>
name|valuekey
init|=
name|entry
operator|.
name|getCiteKeyOptional
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|valuekey
operator|.
name|isPresent
argument_list|()
condition|)
block|{
return|return
name|Collections
operator|.
name|emptyList
argument_list|()
return|;
block|}
name|String
name|key
init|=
name|valuekey
operator|.
name|get
argument_list|()
decl_stmt|;
comment|// generate new key
name|String
name|generatedKey
init|=
operator|new
name|BibtexKeyGenerator
argument_list|(
name|bibDatabaseContext
argument_list|,
name|bibtexKeyPatternPreferences
argument_list|)
operator|.
name|generateKey
argument_list|(
name|entry
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|Objects
operator|.
name|equals
argument_list|(
name|key
argument_list|,
name|generatedKey
argument_list|)
condition|)
block|{
return|return
name|Collections
operator|.
name|singletonList
argument_list|(
operator|new
name|IntegrityMessage
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"BibTeX key deviates from generated key"
argument_list|)
argument_list|,
name|entry
argument_list|,
name|BibEntry
operator|.
name|KEY_FIELD
argument_list|)
argument_list|)
return|;
block|}
return|return
name|Collections
operator|.
name|emptyList
argument_list|()
return|;
block|}
block|}
end_class

end_unit

