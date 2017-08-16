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

begin_class
DECL|class|FieldAddedOrRemovedEvent
specifier|public
class|class
name|FieldAddedOrRemovedEvent
extends|extends
name|FieldChangedEvent
block|{
DECL|method|FieldAddedOrRemovedEvent (FieldChange fieldChange, EntryEventSource location)
specifier|public
name|FieldAddedOrRemovedEvent
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
argument_list|,
name|location
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

