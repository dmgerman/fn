begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.shared.exception
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|shared
operator|.
name|exception
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
comment|/**  * This exception is thrown if a BibEntry with smaller version number is going to be used for an update on the shared side.  * The principle of optimistic offline lock forbids updating with obsolete objects.  */
end_comment

begin_class
DECL|class|OfflineLockException
specifier|public
class|class
name|OfflineLockException
extends|extends
name|Exception
block|{
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
DECL|method|OfflineLockException (BibEntry localBibEntry, BibEntry sharedBibEntry)
specifier|public
name|OfflineLockException
parameter_list|(
name|BibEntry
name|localBibEntry
parameter_list|,
name|BibEntry
name|sharedBibEntry
parameter_list|)
block|{
name|super
argument_list|(
literal|"Local BibEntry data is not up-to-date."
argument_list|)
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

