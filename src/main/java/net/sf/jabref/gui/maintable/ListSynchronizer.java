begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.maintable
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|maintable
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
name|ca
operator|.
name|odell
operator|.
name|glazedlists
operator|.
name|EventList
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
DECL|class|ListSynchronizer
specifier|public
class|class
name|ListSynchronizer
block|{
DECL|field|list
specifier|private
specifier|final
name|EventList
argument_list|<
name|BibEntry
argument_list|>
name|list
decl_stmt|;
DECL|method|ListSynchronizer (EventList<BibEntry> list)
specifier|public
name|ListSynchronizer
parameter_list|(
name|EventList
argument_list|<
name|BibEntry
argument_list|>
name|list
parameter_list|)
block|{
name|this
operator|.
name|list
operator|=
name|list
expr_stmt|;
block|}
annotation|@
name|Subscribe
DECL|method|listen (EntryAddedEvent entryAddedEvent)
specifier|public
name|void
name|listen
parameter_list|(
name|EntryAddedEvent
name|entryAddedEvent
parameter_list|)
block|{
name|lock
argument_list|()
expr_stmt|;
try|try
block|{
name|list
operator|.
name|add
argument_list|(
name|entryAddedEvent
operator|.
name|getBibEntry
argument_list|()
argument_list|)
expr_stmt|;
block|}
finally|finally
block|{
name|unlock
argument_list|()
expr_stmt|;
block|}
block|}
annotation|@
name|Subscribe
DECL|method|listen (EntryRemovedEvent entryRemovedEvent)
specifier|public
name|void
name|listen
parameter_list|(
name|EntryRemovedEvent
name|entryRemovedEvent
parameter_list|)
block|{
name|lock
argument_list|()
expr_stmt|;
try|try
block|{
name|list
operator|.
name|remove
argument_list|(
name|entryRemovedEvent
operator|.
name|getBibEntry
argument_list|()
argument_list|)
expr_stmt|;
block|}
finally|finally
block|{
name|unlock
argument_list|()
expr_stmt|;
block|}
block|}
annotation|@
name|Subscribe
DECL|method|listen (EntryChangedEvent entryChangedEvent)
specifier|public
name|void
name|listen
parameter_list|(
name|EntryChangedEvent
name|entryChangedEvent
parameter_list|)
block|{
name|lock
argument_list|()
expr_stmt|;
try|try
block|{
comment|// cannot use list#indexOf b/c it won't distinguish between duplicates
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|list
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|list
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|==
name|entryChangedEvent
operator|.
name|getBibEntry
argument_list|()
condition|)
block|{
name|list
operator|.
name|set
argument_list|(
name|i
argument_list|,
name|entryChangedEvent
operator|.
name|getBibEntry
argument_list|()
argument_list|)
expr_stmt|;
break|break;
block|}
block|}
block|}
finally|finally
block|{
name|unlock
argument_list|()
expr_stmt|;
block|}
block|}
DECL|method|lock ()
specifier|private
name|void
name|lock
parameter_list|()
block|{
name|list
operator|.
name|getReadWriteLock
argument_list|()
operator|.
name|writeLock
argument_list|()
operator|.
name|lock
argument_list|()
expr_stmt|;
block|}
DECL|method|unlock ()
specifier|private
name|void
name|unlock
parameter_list|()
block|{
name|list
operator|.
name|getReadWriteLock
argument_list|()
operator|.
name|writeLock
argument_list|()
operator|.
name|unlock
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

