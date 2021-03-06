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
name|database
operator|.
name|BibDatabaseContext
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

begin_comment
comment|/**  * A new {@link UpdateRefusedEvent} is fired, when the user tries to push changes of an obsolete  * {@link BibEntry} to the server.  */
end_comment

begin_class
DECL|class|UpdateRefusedEvent
specifier|public
class|class
name|UpdateRefusedEvent
block|{
DECL|field|bibDatabaseContext
specifier|private
specifier|final
name|BibDatabaseContext
name|bibDatabaseContext
decl_stmt|;
DECL|field|localBibEntry
specifier|private
specifier|final
name|BibEntry
name|localBibEntry
decl_stmt|;
DECL|field|sharedBibEntry
specifier|private
specifier|final
name|BibEntry
name|sharedBibEntry
decl_stmt|;
comment|/**      * @param bibDatabaseContext Affected {@link BibDatabaseContext}      * @param localBibEntry Affected {@link BibEntry}      */
DECL|method|UpdateRefusedEvent (BibDatabaseContext bibDatabaseContext, BibEntry localBibEntry, BibEntry sharedBibEntry)
specifier|public
name|UpdateRefusedEvent
parameter_list|(
name|BibDatabaseContext
name|bibDatabaseContext
parameter_list|,
name|BibEntry
name|localBibEntry
parameter_list|,
name|BibEntry
name|sharedBibEntry
parameter_list|)
block|{
name|this
operator|.
name|bibDatabaseContext
operator|=
name|bibDatabaseContext
expr_stmt|;
name|this
operator|.
name|localBibEntry
operator|=
name|localBibEntry
expr_stmt|;
name|this
operator|.
name|sharedBibEntry
operator|=
name|sharedBibEntry
expr_stmt|;
block|}
DECL|method|getBibDatabaseContext ()
specifier|public
name|BibDatabaseContext
name|getBibDatabaseContext
parameter_list|()
block|{
return|return
name|this
operator|.
name|bibDatabaseContext
return|;
block|}
DECL|method|getLocalBibEntry ()
specifier|public
name|BibEntry
name|getLocalBibEntry
parameter_list|()
block|{
return|return
name|localBibEntry
return|;
block|}
DECL|method|getSharedBibEntry ()
specifier|public
name|BibEntry
name|getSharedBibEntry
parameter_list|()
block|{
return|return
name|sharedBibEntry
return|;
block|}
block|}
end_class

end_unit

