begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.util
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|util
package|;
end_package

begin_import
import|import
name|java
operator|.
name|time
operator|.
name|LocalDateTime
import|;
end_import

begin_import
import|import
name|java
operator|.
name|time
operator|.
name|format
operator|.
name|DateTimeFormatter
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
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
name|FieldName
import|;
end_import

begin_class
DECL|class|UpdateField
specifier|public
class|class
name|UpdateField
block|{
comment|/**      * Updating a field will result in the entry being reformatted on save      *      * @param be         BibEntry      * @param field      Field name      * @param newValue   New field value      */
DECL|method|updateField (BibEntry be, String field, String newValue)
specifier|public
specifier|static
name|Optional
argument_list|<
name|FieldChange
argument_list|>
name|updateField
parameter_list|(
name|BibEntry
name|be
parameter_list|,
name|String
name|field
parameter_list|,
name|String
name|newValue
parameter_list|)
block|{
return|return
name|updateField
argument_list|(
name|be
argument_list|,
name|field
argument_list|,
name|newValue
argument_list|,
literal|false
argument_list|)
return|;
block|}
comment|/**      * Updating a non-displayable field does not result in the entry being reformatted on save      *      * @param be         BibEntry      * @param field      Field name      * @param newValue   New field value      */
DECL|method|updateNonDisplayableField (BibEntry be, String field, String newValue)
specifier|public
specifier|static
name|Optional
argument_list|<
name|FieldChange
argument_list|>
name|updateNonDisplayableField
parameter_list|(
name|BibEntry
name|be
parameter_list|,
name|String
name|field
parameter_list|,
name|String
name|newValue
parameter_list|)
block|{
name|boolean
name|changed
init|=
name|be
operator|.
name|hasChanged
argument_list|()
decl_stmt|;
name|Optional
argument_list|<
name|FieldChange
argument_list|>
name|fieldChange
init|=
name|updateField
argument_list|(
name|be
argument_list|,
name|field
argument_list|,
name|newValue
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|be
operator|.
name|setChanged
argument_list|(
name|changed
argument_list|)
expr_stmt|;
return|return
name|fieldChange
return|;
block|}
comment|/**      * Undoable change of field value      *      * @param be                          BibEntry      * @param field                       Field name      * @param newValue                    New field value      * @param nullFieldIfValueIsTheSame   If true the field value is removed when the current value is equals to newValue      */
DECL|method|updateField (BibEntry be, String field, String newValue, Boolean nullFieldIfValueIsTheSame)
specifier|public
specifier|static
name|Optional
argument_list|<
name|FieldChange
argument_list|>
name|updateField
parameter_list|(
name|BibEntry
name|be
parameter_list|,
name|String
name|field
parameter_list|,
name|String
name|newValue
parameter_list|,
name|Boolean
name|nullFieldIfValueIsTheSame
parameter_list|)
block|{
name|String
name|writtenValue
init|=
literal|null
decl_stmt|;
name|String
name|oldValue
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|be
operator|.
name|hasField
argument_list|(
name|field
argument_list|)
condition|)
block|{
name|oldValue
operator|=
name|be
operator|.
name|getField
argument_list|(
name|field
argument_list|)
operator|.
name|get
argument_list|()
expr_stmt|;
if|if
condition|(
operator|(
name|newValue
operator|==
literal|null
operator|)
operator|||
operator|(
name|oldValue
operator|.
name|equals
argument_list|(
name|newValue
argument_list|)
operator|&&
name|nullFieldIfValueIsTheSame
operator|)
condition|)
block|{
comment|// If the new field value is null or the old and the new value are the same and flag is set
comment|// Clear the field
name|be
operator|.
name|clearField
argument_list|(
name|field
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
operator|!
name|oldValue
operator|.
name|equals
argument_list|(
name|newValue
argument_list|)
condition|)
block|{
comment|// Update
name|writtenValue
operator|=
name|newValue
expr_stmt|;
name|be
operator|.
name|setField
argument_list|(
name|field
argument_list|,
name|newValue
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// Values are the same, do nothing
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
block|}
else|else
block|{
comment|// old field value not set
if|if
condition|(
name|newValue
operator|==
literal|null
condition|)
block|{
comment|// Do nothing
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
else|else
block|{
comment|// Set new value
name|writtenValue
operator|=
name|newValue
expr_stmt|;
name|be
operator|.
name|setField
argument_list|(
name|field
argument_list|,
name|newValue
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|Optional
operator|.
name|of
argument_list|(
operator|new
name|FieldChange
argument_list|(
name|be
argument_list|,
name|field
argument_list|,
name|oldValue
argument_list|,
name|writtenValue
argument_list|)
argument_list|)
return|;
block|}
comment|/**      * Sets empty or non-existing owner fields of a bibtex entry to a specified default value. Timestamp field is also      * set. Preferences are checked to see if these options are enabled.      *      * @param entry              The entry to set fields for.      * @param overwriteOwner     Indicates whether owner should be set if it is already set.      * @param overwriteTimestamp Indicates whether timestamp should be set if it is already set.      */
DECL|method|setAutomaticFields (BibEntry entry, boolean overwriteOwner, boolean overwriteTimestamp, UpdateFieldPreferences prefs)
specifier|public
specifier|static
name|void
name|setAutomaticFields
parameter_list|(
name|BibEntry
name|entry
parameter_list|,
name|boolean
name|overwriteOwner
parameter_list|,
name|boolean
name|overwriteTimestamp
parameter_list|,
name|UpdateFieldPreferences
name|prefs
parameter_list|)
block|{
name|String
name|defaultOwner
init|=
name|prefs
operator|.
name|getDefaultOwner
argument_list|()
decl_stmt|;
name|String
name|timestamp
init|=
name|DateTimeFormatter
operator|.
name|ofPattern
argument_list|(
name|prefs
operator|.
name|getTimeStampFormat
argument_list|()
argument_list|)
operator|.
name|format
argument_list|(
name|LocalDateTime
operator|.
name|now
argument_list|()
argument_list|)
decl_stmt|;
name|String
name|timeStampField
init|=
name|prefs
operator|.
name|getTimeStampField
argument_list|()
decl_stmt|;
name|boolean
name|setOwner
init|=
name|prefs
operator|.
name|isUseOwner
argument_list|()
operator|&&
operator|(
name|overwriteOwner
operator|||
operator|(
operator|!
name|entry
operator|.
name|hasField
argument_list|(
name|FieldName
operator|.
name|OWNER
argument_list|)
operator|)
operator|)
decl_stmt|;
name|boolean
name|setTimeStamp
init|=
name|prefs
operator|.
name|isUseTimeStamp
argument_list|()
operator|&&
operator|(
name|overwriteTimestamp
operator|||
operator|(
operator|!
name|entry
operator|.
name|hasField
argument_list|(
name|timeStampField
argument_list|)
operator|)
operator|)
decl_stmt|;
name|setAutomaticFields
argument_list|(
name|entry
argument_list|,
name|setOwner
argument_list|,
name|defaultOwner
argument_list|,
name|setTimeStamp
argument_list|,
name|timeStampField
argument_list|,
name|timestamp
argument_list|)
expr_stmt|;
block|}
DECL|method|setAutomaticFields (BibEntry entry, UpdateFieldPreferences prefs)
specifier|public
specifier|static
name|void
name|setAutomaticFields
parameter_list|(
name|BibEntry
name|entry
parameter_list|,
name|UpdateFieldPreferences
name|prefs
parameter_list|)
block|{
name|UpdateField
operator|.
name|setAutomaticFields
argument_list|(
name|entry
argument_list|,
name|prefs
operator|.
name|isOverwriteOwner
argument_list|()
argument_list|,
name|prefs
operator|.
name|isOverwriteTimeStamp
argument_list|()
argument_list|,
name|prefs
argument_list|)
expr_stmt|;
block|}
DECL|method|setAutomaticFields (BibEntry entry, boolean setOwner, String owner, boolean setTimeStamp, String timeStampField, String timeStamp)
specifier|private
specifier|static
name|void
name|setAutomaticFields
parameter_list|(
name|BibEntry
name|entry
parameter_list|,
name|boolean
name|setOwner
parameter_list|,
name|String
name|owner
parameter_list|,
name|boolean
name|setTimeStamp
parameter_list|,
name|String
name|timeStampField
parameter_list|,
name|String
name|timeStamp
parameter_list|)
block|{
comment|// Set owner field if this option is enabled:
if|if
condition|(
name|setOwner
condition|)
block|{
comment|// Set owner field to default value
name|entry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|OWNER
argument_list|,
name|owner
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|setTimeStamp
condition|)
block|{
name|entry
operator|.
name|setField
argument_list|(
name|timeStampField
argument_list|,
name|timeStamp
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Sets empty or non-existing owner fields of bibtex entries inside a List to a specified default value. Timestamp      * field is also set. Preferences are checked to see if these options are enabled.      *      * @param bibs List of bibtex entries      */
DECL|method|setAutomaticFields (Collection<BibEntry> bibs, boolean overwriteOwner, boolean overwriteTimestamp, UpdateFieldPreferences prefs)
specifier|public
specifier|static
name|void
name|setAutomaticFields
parameter_list|(
name|Collection
argument_list|<
name|BibEntry
argument_list|>
name|bibs
parameter_list|,
name|boolean
name|overwriteOwner
parameter_list|,
name|boolean
name|overwriteTimestamp
parameter_list|,
name|UpdateFieldPreferences
name|prefs
parameter_list|)
block|{
name|boolean
name|globalSetOwner
init|=
name|prefs
operator|.
name|isUseOwner
argument_list|()
decl_stmt|;
name|boolean
name|globalSetTimeStamp
init|=
name|prefs
operator|.
name|isUseTimeStamp
argument_list|()
decl_stmt|;
comment|// Do not need to do anything if all options are disabled
if|if
condition|(
operator|!
operator|(
name|globalSetOwner
operator|||
name|globalSetTimeStamp
operator|)
condition|)
block|{
return|return;
block|}
name|String
name|timeStampField
init|=
name|prefs
operator|.
name|getTimeStampField
argument_list|()
decl_stmt|;
name|String
name|defaultOwner
init|=
name|prefs
operator|.
name|getDefaultOwner
argument_list|()
decl_stmt|;
name|String
name|timestamp
init|=
name|DateTimeFormatter
operator|.
name|ofPattern
argument_list|(
name|prefs
operator|.
name|getTimeStampFormat
argument_list|()
argument_list|)
operator|.
name|format
argument_list|(
name|LocalDateTime
operator|.
name|now
argument_list|()
argument_list|)
decl_stmt|;
comment|// Iterate through all entries
for|for
control|(
name|BibEntry
name|curEntry
range|:
name|bibs
control|)
block|{
name|boolean
name|setOwner
init|=
name|globalSetOwner
operator|&&
operator|(
name|overwriteOwner
operator|||
operator|(
operator|!
name|curEntry
operator|.
name|hasField
argument_list|(
name|FieldName
operator|.
name|OWNER
argument_list|)
operator|)
operator|)
decl_stmt|;
name|boolean
name|setTimeStamp
init|=
name|globalSetTimeStamp
operator|&&
operator|(
name|overwriteTimestamp
operator|||
operator|(
operator|!
name|curEntry
operator|.
name|hasField
argument_list|(
name|timeStampField
argument_list|)
operator|)
operator|)
decl_stmt|;
name|setAutomaticFields
argument_list|(
name|curEntry
argument_list|,
name|setOwner
argument_list|,
name|defaultOwner
argument_list|,
name|setTimeStamp
argument_list|,
name|timeStampField
argument_list|,
name|timestamp
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|setAutomaticFields (Collection<BibEntry> bibs, UpdateFieldPreferences prefs)
specifier|public
specifier|static
name|void
name|setAutomaticFields
parameter_list|(
name|Collection
argument_list|<
name|BibEntry
argument_list|>
name|bibs
parameter_list|,
name|UpdateFieldPreferences
name|prefs
parameter_list|)
block|{
name|UpdateField
operator|.
name|setAutomaticFields
argument_list|(
name|bibs
argument_list|,
name|prefs
operator|.
name|isOverwriteOwner
argument_list|()
argument_list|,
name|prefs
operator|.
name|isOverwriteTimeStamp
argument_list|()
argument_list|,
name|prefs
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

