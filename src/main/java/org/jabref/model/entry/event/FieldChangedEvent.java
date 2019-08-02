begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.model.entry.event
package|package
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|event
package|;
end_package

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
name|field
operator|.
name|Field
import|;
end_import

begin_comment
comment|/**  *<code>FieldChangedEvent</code> is fired when a field of<code>BibEntry</code> has been modified, removed or added.  */
end_comment

begin_class
DECL|class|FieldChangedEvent
specifier|public
class|class
name|FieldChangedEvent
extends|extends
name|EntryChangedEvent
block|{
DECL|field|field
specifier|private
specifier|final
name|Field
name|field
decl_stmt|;
DECL|field|newValue
specifier|private
specifier|final
name|String
name|newValue
decl_stmt|;
DECL|field|oldValue
specifier|private
specifier|final
name|String
name|oldValue
decl_stmt|;
DECL|field|delta
specifier|private
name|int
name|delta
init|=
literal|0
decl_stmt|;
comment|/**      * @param bibEntry  Affected BibEntry object      * @param field Name of field which has been changed      * @param newValue  new field value      * @param newValue  old field value      * @param location  location Location affected by this event      */
DECL|method|FieldChangedEvent (BibEntry bibEntry, Field field, String newValue, String oldValue, EntryEventSource location)
specifier|public
name|FieldChangedEvent
parameter_list|(
name|BibEntry
name|bibEntry
parameter_list|,
name|Field
name|field
parameter_list|,
name|String
name|newValue
parameter_list|,
name|String
name|oldValue
parameter_list|,
name|EntryEventSource
name|location
parameter_list|)
block|{
name|super
argument_list|(
name|bibEntry
argument_list|,
name|location
argument_list|)
expr_stmt|;
name|this
operator|.
name|field
operator|=
name|field
expr_stmt|;
name|this
operator|.
name|newValue
operator|=
name|newValue
expr_stmt|;
name|this
operator|.
name|oldValue
operator|=
name|oldValue
expr_stmt|;
name|delta
operator|=
name|computeDelta
argument_list|(
name|oldValue
argument_list|,
name|newValue
argument_list|)
expr_stmt|;
block|}
comment|/**      * @param bibEntry  Affected BibEntry object      * @param field Name of field which has been changed      * @param newValue  new field value      */
DECL|method|FieldChangedEvent (BibEntry bibEntry, Field field, String newValue, String oldValue)
specifier|public
name|FieldChangedEvent
parameter_list|(
name|BibEntry
name|bibEntry
parameter_list|,
name|Field
name|field
parameter_list|,
name|String
name|newValue
parameter_list|,
name|String
name|oldValue
parameter_list|)
block|{
name|super
argument_list|(
name|bibEntry
argument_list|)
expr_stmt|;
name|this
operator|.
name|field
operator|=
name|field
expr_stmt|;
name|this
operator|.
name|newValue
operator|=
name|newValue
expr_stmt|;
name|this
operator|.
name|oldValue
operator|=
name|oldValue
expr_stmt|;
name|delta
operator|=
name|computeDelta
argument_list|(
name|oldValue
argument_list|,
name|newValue
argument_list|)
expr_stmt|;
block|}
comment|/**      * @param bibEntry  Affected BibEntry object      * @param fieldName Name of field which has been changed      * @param newValue  new field value      * @param location  location Location affected by this event      */
DECL|method|FieldChangedEvent (FieldChange fieldChange, EntryEventSource location)
specifier|public
name|FieldChangedEvent
parameter_list|(
name|FieldChange
name|fieldChange
parameter_list|,
name|EntryEventSource
name|location
parameter_list|)
block|{
name|super
argument_list|(
name|fieldChange
operator|.
name|getEntry
argument_list|()
argument_list|,
name|location
argument_list|)
expr_stmt|;
name|this
operator|.
name|field
operator|=
name|fieldChange
operator|.
name|getField
argument_list|()
expr_stmt|;
name|this
operator|.
name|newValue
operator|=
name|fieldChange
operator|.
name|getNewValue
argument_list|()
expr_stmt|;
name|this
operator|.
name|oldValue
operator|=
name|fieldChange
operator|.
name|getOldValue
argument_list|()
expr_stmt|;
name|delta
operator|=
name|computeDelta
argument_list|(
name|oldValue
argument_list|,
name|newValue
argument_list|)
expr_stmt|;
block|}
DECL|method|FieldChangedEvent (FieldChange fieldChange)
specifier|public
name|FieldChangedEvent
parameter_list|(
name|FieldChange
name|fieldChange
parameter_list|)
block|{
name|this
argument_list|(
name|fieldChange
argument_list|,
name|EntryEventSource
operator|.
name|LOCAL
argument_list|)
expr_stmt|;
block|}
DECL|method|computeDelta (String oldValue, String newValue)
specifier|private
name|int
name|computeDelta
parameter_list|(
name|String
name|oldValue
parameter_list|,
name|String
name|newValue
parameter_list|)
block|{
if|if
condition|(
name|oldValue
operator|==
name|newValue
condition|)
block|{
return|return
literal|0
return|;
block|}
elseif|else
if|if
condition|(
name|oldValue
operator|==
literal|null
operator|&&
name|newValue
operator|!=
literal|null
condition|)
block|{
return|return
name|newValue
operator|.
name|length
argument_list|()
return|;
block|}
elseif|else
if|if
condition|(
name|newValue
operator|==
literal|null
operator|&&
name|oldValue
operator|!=
literal|null
condition|)
block|{
return|return
name|oldValue
operator|.
name|length
argument_list|()
return|;
block|}
else|else
block|{
return|return
name|Math
operator|.
name|abs
argument_list|(
name|newValue
operator|.
name|length
argument_list|()
operator|-
name|oldValue
operator|.
name|length
argument_list|()
argument_list|)
return|;
block|}
block|}
DECL|method|getField ()
specifier|public
name|Field
name|getField
parameter_list|()
block|{
return|return
name|field
return|;
block|}
DECL|method|getNewValue ()
specifier|public
name|String
name|getNewValue
parameter_list|()
block|{
return|return
name|newValue
return|;
block|}
DECL|method|getOldValue ()
specifier|public
name|String
name|getOldValue
parameter_list|()
block|{
return|return
name|oldValue
return|;
block|}
DECL|method|getDelta ()
specifier|public
name|int
name|getDelta
parameter_list|()
block|{
return|return
name|delta
return|;
block|}
block|}
end_class

end_unit

