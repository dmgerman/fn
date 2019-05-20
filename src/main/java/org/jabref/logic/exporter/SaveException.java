begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.exporter
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|exporter
package|;
end_package

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|l10n
operator|.
name|Localization
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
comment|/**  * Exception thrown if saving goes wrong. If caused by a specific  * entry, keeps track of which entry caused the problem.  */
end_comment

begin_class
DECL|class|SaveException
specifier|public
class|class
name|SaveException
extends|extends
name|Exception
block|{
DECL|field|FILE_LOCKED
specifier|public
specifier|static
specifier|final
name|SaveException
name|FILE_LOCKED
init|=
operator|new
name|SaveException
argument_list|(
literal|"Could not save, file locked by another JabRef instance."
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Could not save, file locked by another JabRef instance."
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|BACKUP_CREATION
specifier|public
specifier|static
specifier|final
name|SaveException
name|BACKUP_CREATION
init|=
operator|new
name|SaveException
argument_list|(
literal|"Unable to create backup"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Unable to create backup"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|entry
specifier|private
name|BibEntry
name|entry
decl_stmt|;
DECL|field|status
specifier|private
name|int
name|status
decl_stmt|;
DECL|field|localizedMessage
specifier|private
name|String
name|localizedMessage
decl_stmt|;
DECL|method|SaveException (String message)
specifier|public
name|SaveException
parameter_list|(
name|String
name|message
parameter_list|)
block|{
name|super
argument_list|(
name|message
argument_list|)
expr_stmt|;
name|entry
operator|=
literal|null
expr_stmt|;
block|}
DECL|method|SaveException (String message, Throwable exception)
specifier|public
name|SaveException
parameter_list|(
name|String
name|message
parameter_list|,
name|Throwable
name|exception
parameter_list|)
block|{
name|super
argument_list|(
name|message
argument_list|,
name|exception
argument_list|)
expr_stmt|;
name|entry
operator|=
literal|null
expr_stmt|;
block|}
DECL|method|SaveException (String message, String localizedMessage)
specifier|public
name|SaveException
parameter_list|(
name|String
name|message
parameter_list|,
name|String
name|localizedMessage
parameter_list|)
block|{
name|super
argument_list|(
name|message
argument_list|)
expr_stmt|;
name|this
operator|.
name|localizedMessage
operator|=
name|localizedMessage
expr_stmt|;
name|entry
operator|=
literal|null
expr_stmt|;
block|}
DECL|method|SaveException (String message, int status)
specifier|public
name|SaveException
parameter_list|(
name|String
name|message
parameter_list|,
name|int
name|status
parameter_list|)
block|{
name|super
argument_list|(
name|message
argument_list|)
expr_stmt|;
name|entry
operator|=
literal|null
expr_stmt|;
name|this
operator|.
name|status
operator|=
name|status
expr_stmt|;
block|}
DECL|method|SaveException (String message, BibEntry entry)
specifier|public
name|SaveException
parameter_list|(
name|String
name|message
parameter_list|,
name|BibEntry
name|entry
parameter_list|)
block|{
name|super
argument_list|(
name|message
argument_list|)
expr_stmt|;
name|this
operator|.
name|entry
operator|=
name|entry
expr_stmt|;
block|}
DECL|method|SaveException (String message, String localizedMessage, BibEntry entry, Throwable base)
specifier|public
name|SaveException
parameter_list|(
name|String
name|message
parameter_list|,
name|String
name|localizedMessage
parameter_list|,
name|BibEntry
name|entry
parameter_list|,
name|Throwable
name|base
parameter_list|)
block|{
name|super
argument_list|(
name|message
argument_list|,
name|base
argument_list|)
expr_stmt|;
name|this
operator|.
name|localizedMessage
operator|=
name|localizedMessage
expr_stmt|;
name|this
operator|.
name|entry
operator|=
name|entry
expr_stmt|;
block|}
DECL|method|SaveException (Throwable base)
specifier|public
name|SaveException
parameter_list|(
name|Throwable
name|base
parameter_list|)
block|{
name|super
argument_list|(
name|base
operator|.
name|getMessage
argument_list|()
argument_list|,
name|base
argument_list|)
expr_stmt|;
block|}
DECL|method|SaveException (Throwable base, BibEntry entry)
specifier|public
name|SaveException
parameter_list|(
name|Throwable
name|base
parameter_list|,
name|BibEntry
name|entry
parameter_list|)
block|{
name|this
argument_list|(
name|base
operator|.
name|getMessage
argument_list|()
argument_list|,
name|base
operator|.
name|getLocalizedMessage
argument_list|()
argument_list|,
name|entry
argument_list|,
name|base
argument_list|)
expr_stmt|;
block|}
DECL|method|getStatus ()
specifier|public
name|int
name|getStatus
parameter_list|()
block|{
return|return
name|status
return|;
block|}
DECL|method|getEntry ()
specifier|public
name|BibEntry
name|getEntry
parameter_list|()
block|{
return|return
name|entry
return|;
block|}
DECL|method|specificEntry ()
specifier|public
name|boolean
name|specificEntry
parameter_list|()
block|{
return|return
name|entry
operator|!=
literal|null
return|;
block|}
annotation|@
name|Override
DECL|method|getLocalizedMessage ()
specifier|public
name|String
name|getLocalizedMessage
parameter_list|()
block|{
if|if
condition|(
name|localizedMessage
operator|==
literal|null
condition|)
block|{
return|return
name|getMessage
argument_list|()
return|;
block|}
else|else
block|{
return|return
name|localizedMessage
return|;
block|}
block|}
block|}
end_class

end_unit

