begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.cleanup
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|cleanup
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
name|FieldChange
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
DECL|class|ISSNCleanup
specifier|public
class|class
name|ISSNCleanup
implements|implements
name|CleanupJob
block|{
annotation|@
name|Override
DECL|method|cleanup (BibEntry entry)
specifier|public
name|List
argument_list|<
name|FieldChange
argument_list|>
name|cleanup
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
name|Optional
argument_list|<
name|String
argument_list|>
name|issnString
init|=
name|entry
operator|.
name|getFieldOptional
argument_list|(
name|FieldName
operator|.
name|ISSN
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|issnString
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
name|ISSN
name|issn
init|=
operator|new
name|ISSN
argument_list|(
name|issnString
operator|.
name|get
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|issn
operator|.
name|isCanBeCleaned
argument_list|()
condition|)
block|{
name|String
name|newValue
init|=
name|issn
operator|.
name|getCleanedISSN
argument_list|()
decl_stmt|;
name|FieldChange
name|change
init|=
operator|new
name|FieldChange
argument_list|(
name|entry
argument_list|,
name|FieldName
operator|.
name|ISSN
argument_list|,
name|issnString
operator|.
name|get
argument_list|()
argument_list|,
name|newValue
argument_list|)
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|ISSN
argument_list|,
name|newValue
argument_list|)
expr_stmt|;
return|return
name|Collections
operator|.
name|singletonList
argument_list|(
name|change
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

