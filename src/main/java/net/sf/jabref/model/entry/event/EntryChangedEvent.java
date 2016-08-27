begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.model.entry.event
package|package
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
name|event
package|;
end_package

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

begin_comment
comment|/**  *<code>EntryChangedEvent</code> is fired when a<code>BibEntry</code> has been changed.  */
end_comment

begin_class
DECL|class|EntryChangedEvent
specifier|public
class|class
name|EntryChangedEvent
extends|extends
name|EntryEvent
block|{
comment|/**      * @param bibEntry<code>BibEntry</code> object the changes were applied on.      */
DECL|method|EntryChangedEvent (BibEntry bibEntry)
specifier|public
name|EntryChangedEvent
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
comment|/**      * @param bibEntry<code>BibEntry</code> object the changes were applied on.      * @param location Location affected by this event      */
DECL|method|EntryChangedEvent (BibEntry bibEntry, EntryEventSource location)
specifier|public
name|EntryChangedEvent
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

