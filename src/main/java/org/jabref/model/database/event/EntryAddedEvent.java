begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.model.database.event
package|package
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|database
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
name|database
operator|.
name|BibDatabase
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
name|event
operator|.
name|EntryEvent
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
name|event
operator|.
name|EntryEventSource
import|;
end_import

begin_comment
comment|/**  * {@link EntryAddedEvent} is fired when a new {@link BibEntry} was added to the {@link BibDatabase}.  */
end_comment

begin_class
DECL|class|EntryAddedEvent
specifier|public
class|class
name|EntryAddedEvent
extends|extends
name|EntryEvent
block|{
comment|/**      * @param bibEntry the entry which has been added      */
DECL|method|EntryAddedEvent (BibEntry bibEntry)
specifier|public
name|EntryAddedEvent
parameter_list|(
name|BibEntry
name|bibEntry
parameter_list|)
block|{
name|super
argument_list|(
name|bibEntry
argument_list|)
expr_stmt|;
block|}
comment|/**      * @param bibEntry<code>BibEntry</code> object which has been added.      * @param location Location affected by this event      */
DECL|method|EntryAddedEvent (BibEntry bibEntry, EntryEventSource location)
specifier|public
name|EntryAddedEvent
parameter_list|(
name|BibEntry
name|bibEntry
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
block|}
block|}
end_class

end_unit

