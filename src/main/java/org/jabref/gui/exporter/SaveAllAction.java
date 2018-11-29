begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.exporter
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
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
name|DialogService
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
name|actions
operator|.
name|Actions
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
DECL|class|SaveAllAction
specifier|public
class|class
name|SaveAllAction
extends|extends
name|SimpleCommand
block|{
DECL|field|frame
specifier|private
specifier|final
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|dialogService
specifier|private
specifier|final
name|DialogService
name|dialogService
decl_stmt|;
DECL|method|SaveAllAction (JabRefFrame frame)
specifier|public
name|SaveAllAction
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|)
block|{
name|this
operator|.
name|frame
operator|=
name|frame
expr_stmt|;
name|this
operator|.
name|dialogService
operator|=
name|frame
operator|.
name|getDialogService
argument_list|()
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
name|dialogService
operator|.
name|notify
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Saving all libraries..."
argument_list|)
argument_list|)
expr_stmt|;
for|for
control|(
name|BasePanel
name|panel
range|:
name|frame
operator|.
name|getBasePanelList
argument_list|()
control|)
block|{
if|if
condition|(
operator|!
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
operator|.
name|getDatabasePath
argument_list|()
operator|.
name|isPresent
argument_list|()
condition|)
block|{
comment|//It will ask a path before saving.
name|panel
operator|.
name|runCommand
argument_list|(
name|Actions
operator|.
name|SAVE_AS
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|panel
operator|.
name|runCommand
argument_list|(
name|Actions
operator|.
name|SAVE
argument_list|)
expr_stmt|;
comment|// TODO: can we find out whether the save was actually done or not?
block|}
block|}
name|dialogService
operator|.
name|notify
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Save all finished."
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

