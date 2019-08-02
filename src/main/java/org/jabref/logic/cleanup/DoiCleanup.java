begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.cleanup
package|package
name|org
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
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Arrays
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
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|formatter
operator|.
name|bibtexfields
operator|.
name|ClearFormatter
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
name|FieldChange
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
name|CleanupJob
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
name|FieldFormatterCleanup
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
name|field
operator|.
name|Field
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
name|field
operator|.
name|StandardField
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
name|field
operator|.
name|UnknownField
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
name|identifier
operator|.
name|DOI
import|;
end_import

begin_comment
comment|/**  * Formats the DOI (e.g. removes http part) and also moves DOIs from note, url or ee field to the doi field.  */
end_comment

begin_class
DECL|class|DoiCleanup
specifier|public
class|class
name|DoiCleanup
implements|implements
name|CleanupJob
block|{
comment|/**      * Fields to check for DOIs.      */
DECL|field|FIELDS
specifier|private
specifier|static
specifier|final
name|List
argument_list|<
name|Field
argument_list|>
name|FIELDS
init|=
name|Arrays
operator|.
name|asList
argument_list|(
name|StandardField
operator|.
name|NOTE
argument_list|,
name|StandardField
operator|.
name|URL
argument_list|,
operator|new
name|UnknownField
argument_list|(
literal|"ee"
argument_list|)
argument_list|)
decl_stmt|;
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
name|List
argument_list|<
name|FieldChange
argument_list|>
name|changes
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
comment|// First check if the Doi Field is empty
if|if
condition|(
name|entry
operator|.
name|hasField
argument_list|(
name|StandardField
operator|.
name|DOI
argument_list|)
condition|)
block|{
name|String
name|doiFieldValue
init|=
name|entry
operator|.
name|getField
argument_list|(
name|StandardField
operator|.
name|DOI
argument_list|)
operator|.
name|orElse
argument_list|(
literal|null
argument_list|)
decl_stmt|;
name|Optional
argument_list|<
name|DOI
argument_list|>
name|doi
init|=
name|DOI
operator|.
name|parse
argument_list|(
name|doiFieldValue
argument_list|)
decl_stmt|;
if|if
condition|(
name|doi
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|String
name|newValue
init|=
name|doi
operator|.
name|get
argument_list|()
operator|.
name|getDOI
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|doiFieldValue
operator|.
name|equals
argument_list|(
name|newValue
argument_list|)
condition|)
block|{
name|entry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|DOI
argument_list|,
name|newValue
argument_list|)
expr_stmt|;
name|FieldChange
name|change
init|=
operator|new
name|FieldChange
argument_list|(
name|entry
argument_list|,
name|StandardField
operator|.
name|DOI
argument_list|,
name|doiFieldValue
argument_list|,
name|newValue
argument_list|)
decl_stmt|;
name|changes
operator|.
name|add
argument_list|(
name|change
argument_list|)
expr_stmt|;
block|}
comment|// Doi field seems to contain Doi -> cleanup note, url, ee field
for|for
control|(
name|Field
name|field
range|:
name|FIELDS
control|)
block|{
name|entry
operator|.
name|getField
argument_list|(
name|field
argument_list|)
operator|.
name|flatMap
argument_list|(
name|DOI
operator|::
name|parse
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|unused
lambda|->
name|removeFieldValue
argument_list|(
name|entry
argument_list|,
name|field
argument_list|,
name|changes
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
else|else
block|{
comment|// As the Doi field is empty we now check if note, url, or ee field contains a Doi
for|for
control|(
name|Field
name|field
range|:
name|FIELDS
control|)
block|{
name|Optional
argument_list|<
name|DOI
argument_list|>
name|doi
init|=
name|entry
operator|.
name|getField
argument_list|(
name|field
argument_list|)
operator|.
name|flatMap
argument_list|(
name|DOI
operator|::
name|parse
argument_list|)
decl_stmt|;
if|if
condition|(
name|doi
operator|.
name|isPresent
argument_list|()
condition|)
block|{
comment|// Update Doi
name|Optional
argument_list|<
name|FieldChange
argument_list|>
name|change
init|=
name|entry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|DOI
argument_list|,
name|doi
operator|.
name|get
argument_list|()
operator|.
name|getDOI
argument_list|()
argument_list|)
decl_stmt|;
name|change
operator|.
name|ifPresent
argument_list|(
name|changes
operator|::
name|add
argument_list|)
expr_stmt|;
name|removeFieldValue
argument_list|(
name|entry
argument_list|,
name|field
argument_list|,
name|changes
argument_list|)
expr_stmt|;
block|}
block|}
block|}
return|return
name|changes
return|;
block|}
DECL|method|removeFieldValue (BibEntry entry, Field field, List<FieldChange> changes)
specifier|private
name|void
name|removeFieldValue
parameter_list|(
name|BibEntry
name|entry
parameter_list|,
name|Field
name|field
parameter_list|,
name|List
argument_list|<
name|FieldChange
argument_list|>
name|changes
parameter_list|)
block|{
name|CleanupJob
name|eraser
init|=
operator|new
name|FieldFormatterCleanup
argument_list|(
name|field
argument_list|,
operator|new
name|ClearFormatter
argument_list|()
argument_list|)
decl_stmt|;
name|changes
operator|.
name|addAll
argument_list|(
name|eraser
operator|.
name|cleanup
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

