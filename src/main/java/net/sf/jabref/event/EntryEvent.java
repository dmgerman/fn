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
comment|/**  * This abstract class pretends a minimal set of attributes and methods  * which an entry event should have.  */
end_comment

begin_class
DECL|class|EntryEvent
specifier|public
specifier|abstract
class|class
name|EntryEvent
block|{
DECL|field|bibEntry
specifier|private
specifier|final
name|BibEntry
name|bibEntry
decl_stmt|;
comment|/**      * @param bibEntry BibEntry object which is involved in this event       */
DECL|method|EntryEvent (BibEntry bibEntry)
specifier|public
name|EntryEvent
parameter_list|(
name|BibEntry
name|bibEntry
parameter_list|)
block|{
name|this
operator|.
name|bibEntry
operator|=
name|bibEntry
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

