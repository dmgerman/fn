begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.shared
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|shared
package|;
end_package

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|JabRefFrame
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|actions
operator|.
name|SimpleCommand
import|;
end_import

begin_comment
comment|/**  * Opens a shared database.  */
end_comment

begin_class
DECL|class|ConnectToSharedDatabaseCommand
specifier|public
class|class
name|ConnectToSharedDatabaseCommand
extends|extends
name|SimpleCommand
block|{
DECL|field|jabRefFrame
specifier|private
specifier|final
name|JabRefFrame
name|jabRefFrame
decl_stmt|;
DECL|method|ConnectToSharedDatabaseCommand (JabRefFrame jabRefFrame)
specifier|public
name|ConnectToSharedDatabaseCommand
parameter_list|(
name|JabRefFrame
name|jabRefFrame
parameter_list|)
block|{
name|this
operator|.
name|jabRefFrame
operator|=
name|jabRefFrame
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|execute ()
specifier|public
name|void
name|execute
parameter_list|()
block|{
operator|new
name|SharedDatabaseLoginDialogView
argument_list|(
name|jabRefFrame
argument_list|)
operator|.
name|showAndWait
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

