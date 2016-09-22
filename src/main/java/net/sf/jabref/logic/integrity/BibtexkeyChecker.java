begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.integrity
package|package
name|net
operator|.
name|sf
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
name|Optional
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
name|integrity
operator|.
name|IntegrityCheck
operator|.
name|Checker
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
name|model
operator|.
name|entry
operator|.
name|BibEntry
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
name|entry
operator|.
name|FieldName
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
name|strings
operator|.
name|StringUtil
import|;
end_import

begin_class
DECL|class|BibtexkeyChecker
specifier|public
class|class
name|BibtexkeyChecker
implements|implements
name|Checker
block|{
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
name|Optional
argument_list|<
name|String
argument_list|>
name|valueauthor
init|=
name|entry
operator|.
name|getField
argument_list|(
name|FieldName
operator|.
name|AUTHOR
argument_list|)
decl_stmt|;
name|Optional
argument_list|<
name|String
argument_list|>
name|valuetitle
init|=
name|entry
operator|.
name|getField
argument_list|(
name|FieldName
operator|.
name|TITLE
argument_list|)
decl_stmt|;
name|Optional
argument_list|<
name|String
argument_list|>
name|valueyear
init|=
name|entry
operator|.
name|getField
argument_list|(
name|FieldName
operator|.
name|YEAR
argument_list|)
decl_stmt|;
name|String
name|authortitleyear
init|=
name|entry
operator|.
name|getAuthorTitleYear
argument_list|(
literal|100
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|valueauthor
operator|.
name|isPresent
argument_list|()
operator|||
operator|!
name|valuetitle
operator|.
name|isPresent
argument_list|()
operator|||
operator|!
name|valueyear
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
if|if
condition|(
name|StringUtil
operator|.
name|isBlank
argument_list|(
name|valuekey
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
literal|"empty bibtexkey"
argument_list|)
operator|+
literal|": "
operator|+
name|authortitleyear
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

