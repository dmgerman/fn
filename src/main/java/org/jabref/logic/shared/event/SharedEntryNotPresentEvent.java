begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.shared.event
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|shared
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
name|entry
operator|.
name|BibEntry
import|;
end_import

begin_comment
comment|/**  * A new {@link SharedEntryNotPresentEvent} is fired, when the user tries to push changes of an obsolete  * {@link BibEntry} to the server.  */
end_comment

begin_class
DECL|class|SharedEntryNotPresentEvent
specifier|public
class|class
name|SharedEntryNotPresentEvent
block|{
DECL|field|bibEntry
specifier|private
specifier|final
name|BibEntry
name|bibEntry
decl_stmt|;
comment|/**      * @param bibEntry Affected {@link BibEntry}      */
DECL|method|SharedEntryNotPresentEvent (BibEntry bibEntry)
specifier|public
name|SharedEntryNotPresentEvent
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

