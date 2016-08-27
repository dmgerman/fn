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
name|logic
operator|.
name|util
operator|.
name|ISSN
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

begin_class
DECL|class|ISSNChecker
specifier|public
class|class
name|ISSNChecker
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
if|if
condition|(
operator|!
name|entry
operator|.
name|hasField
argument_list|(
name|FieldName
operator|.
name|ISSN
argument_list|)
condition|)
block|{
return|return
name|Collections
operator|.
name|emptyList
argument_list|()
return|;
block|}
comment|// Check that the ISSN is on the correct form
name|String
name|issnString
init|=
name|entry
operator|.
name|getField
argument_list|(
name|FieldName
operator|.
name|ISSN
argument_list|)
operator|.
name|get
argument_list|()
operator|.
name|trim
argument_list|()
decl_stmt|;
name|ISSN
name|issn
init|=
operator|new
name|ISSN
argument_list|(
name|issnString
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|issn
operator|.
name|isValidFormat
argument_list|()
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
literal|"incorrect format"
argument_list|)
argument_list|,
name|entry
argument_list|,
name|FieldName
operator|.
name|ISSN
argument_list|)
argument_list|)
return|;
block|}
if|if
condition|(
name|issn
operator|.
name|isValidChecksum
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
else|else
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
literal|"incorrect control digit"
argument_list|)
argument_list|,
name|entry
argument_list|,
name|FieldName
operator|.
name|ISSN
argument_list|)
argument_list|)
return|;
block|}
block|}
block|}
end_class

end_unit

