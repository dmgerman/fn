begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.contentselector
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|contentselector
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
name|StateManager
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
import|import static
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|actions
operator|.
name|ActionHelper
operator|.
name|needsDatabase
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
DECL|method|ManageContentSelectorAction (JabRefFrame jabRefFrame, StateManager stateManager)
specifier|public
name|ManageContentSelectorAction
parameter_list|(
name|JabRefFrame
name|jabRefFrame
parameter_list|,
name|StateManager
name|stateManager
parameter_list|)
block|{
name|this
operator|.
name|jabRefFrame
operator|=
name|jabRefFrame
expr_stmt|;
name|this
operator|.
name|executable
operator|.
name|bind
argument_list|(
name|needsDatabase
argument_list|(
name|stateManager
argument_list|)
argument_list|)
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
block|}
end_class

end_unit
