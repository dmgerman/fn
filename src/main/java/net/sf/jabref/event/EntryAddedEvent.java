begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.event
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
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
comment|/**  *<code>EntryAddedEvent</code> is fired when a new<code>BibEntry</code> was added  * to the database.  */
end_comment

begin_class
DECL|class|EntryAddedEvent
specifier|public
class|class
name|EntryAddedEvent
extends|extends
name|EntryEvent
block|{
comment|/**      * @param bibEntry<code>BibEntry</code> object which has been added.      */
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
block|}
end_class

end_unit

