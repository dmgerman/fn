begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.bibtexextractor
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|bibtexextractor
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
DECL|class|ExtractBibtexAction
specifier|public
class|class
name|ExtractBibtexAction
extends|extends
name|SimpleCommand
block|{
DECL|method|ExtractBibtexAction (StateManager stateManager)
specifier|public
name|ExtractBibtexAction
parameter_list|(
name|StateManager
name|stateManager
parameter_list|)
block|{
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
name|ExtractBibtexDialog
name|dlg
init|=
operator|new
name|ExtractBibtexDialog
argument_list|()
decl_stmt|;
name|dlg
operator|.
name|showAndWait
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

