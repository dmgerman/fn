begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.model.event
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|model
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
name|database
operator|.
name|event
operator|.
name|EntryAddedEvent
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
name|database
operator|.
name|event
operator|.
name|EntryRemovedEvent
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
name|event
operator|.
name|EntryChangedEvent
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|common
operator|.
name|eventbus
operator|.
name|Subscribe
import|;
end_import

begin_class
DECL|class|TestEventListener
specifier|public
class|class
name|TestEventListener
block|{
DECL|field|bibEntry
specifier|private
name|BibEntry
name|bibEntry
decl_stmt|;
annotation|@
name|Subscribe
DECL|method|listen (EntryAddedEvent event)
specifier|public
name|void
name|listen
parameter_list|(
name|EntryAddedEvent
name|event
parameter_list|)
block|{
name|this
operator|.
name|bibEntry
operator|=
name|event
operator|.
name|getBibEntry
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Subscribe
DECL|method|listen (EntryRemovedEvent event)
specifier|public
name|void
name|listen
parameter_list|(
name|EntryRemovedEvent
name|event
parameter_list|)
block|{
name|this
operator|.
name|bibEntry
operator|=
name|event
operator|.
name|getBibEntry
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Subscribe
DECL|method|listen (EntryChangedEvent event)
specifier|public
name|void
name|listen
parameter_list|(
name|EntryChangedEvent
name|event
parameter_list|)
block|{
name|this
operator|.
name|bibEntry
operator|=
name|event
operator|.
name|getBibEntry
argument_list|()
expr_stmt|;
block|}
DECL|method|getBibEntry ()
specifier|public
name|BibEntry
name|getBibEntry
parameter_list|()
block|{
return|return
name|this
operator|.
name|bibEntry
return|;
block|}
block|}
end_class

end_unit

