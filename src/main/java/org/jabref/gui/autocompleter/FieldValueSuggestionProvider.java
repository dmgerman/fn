begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.autocompleter
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|autocompleter
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

begin_comment
comment|/**  * Stores the full content of one field.  */
end_comment

begin_class
DECL|class|FieldValueSuggestionProvider
class|class
name|FieldValueSuggestionProvider
extends|extends
name|StringSuggestionProvider
implements|implements
name|AutoCompleteSuggestionProvider
argument_list|<
name|String
argument_list|>
block|{
DECL|field|fieldName
specifier|private
specifier|final
name|String
name|fieldName
decl_stmt|;
DECL|method|FieldValueSuggestionProvider (String fieldName)
name|FieldValueSuggestionProvider
parameter_list|(
name|String
name|fieldName
parameter_list|)
block|{
name|this
operator|.
name|fieldName
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|fieldName
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|indexEntry (BibEntry entry)
specifier|public
name|void
name|indexEntry
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
if|if
condition|(
name|entry
operator|==
literal|null
condition|)
block|{
return|return;
block|}
name|entry
operator|.
name|getField
argument_list|(
name|fieldName
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|fieldValue
lambda|->
name|addPossibleSuggestions
argument_list|(
name|fieldValue
operator|.
name|trim
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit
