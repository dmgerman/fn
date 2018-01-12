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

begin_comment
comment|/**  * A new {@link ConnectionLostEvent} is fired, when the connection to the shared database gets lost.  */
end_comment

begin_class
DECL|class|ConnectionLostEvent
specifier|public
class|class
name|ConnectionLostEvent
block|{
DECL|field|bibDatabaseContext
specifier|private
specifier|final
name|BibDatabaseContext
name|bibDatabaseContext
decl_stmt|;
comment|/**      * @param bibDatabaseContext Affected {@link BibDatabaseContext}      */
DECL|method|ConnectionLostEvent (BibDatabaseContext bibDatabaseContext)
specifier|public
name|ConnectionLostEvent
parameter_list|(
name|BibDatabaseContext
name|bibDatabaseContext
parameter_list|)
block|{
name|this
operator|.
name|bibDatabaseContext
operator|=
name|bibDatabaseContext
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
block|}
end_class

end_unit

