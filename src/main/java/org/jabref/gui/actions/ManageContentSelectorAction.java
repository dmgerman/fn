begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.actions
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|actions
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
name|BasePanel
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
name|contentselector
operator|.
name|ContentSelectorDialogView
import|;
end_import

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

begin_class
DECL|class|ManageContentSelectorAction
specifier|public
class|class
name|ManageContentSelectorAction
extends|extends
name|SimpleCommand
block|{
DECL|field|jabRefFrame
specifier|private
specifier|final
name|JabRefFrame
name|jabRefFrame
decl_stmt|;
DECL|method|ManageContentSelectorAction (JabRefFrame jabRefFrame)
specifier|public
name|ManageContentSelectorAction
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
name|BasePanel
name|basePanel
init|=
name|jabRefFrame
operator|.
name|getCurrentBasePanel
argument_list|()
decl_stmt|;
if|if
condition|(
name|noActiveConnectionExists
argument_list|(
name|basePanel
argument_list|)
condition|)
block|{
name|jabRefFrame
operator|.
name|getDialogService
argument_list|()
operator|.
name|showErrorDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Active database connection do not exists!"
argument_list|)
argument_list|)
expr_stmt|;
return|return;
block|}
operator|new
name|ContentSelectorDialogView
argument_list|(
name|basePanel
argument_list|)
operator|.
name|showAndWait
argument_list|()
expr_stmt|;
block|}
DECL|method|noActiveConnectionExists (BasePanel basePanel)
specifier|private
name|boolean
name|noActiveConnectionExists
parameter_list|(
name|BasePanel
name|basePanel
parameter_list|)
block|{
return|return
name|basePanel
operator|==
literal|null
operator|||
name|basePanel
operator|.
name|getBibDatabaseContext
argument_list|()
operator|==
literal|null
return|;
block|}
block|}
end_class

end_unit
