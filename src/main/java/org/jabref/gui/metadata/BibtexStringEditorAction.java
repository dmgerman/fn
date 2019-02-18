begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.metadata
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|metadata
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
name|BibDatabase
import|;
end_import

begin_class
DECL|class|BibtexStringEditorAction
specifier|public
class|class
name|BibtexStringEditorAction
extends|extends
name|SimpleCommand
block|{
DECL|field|frame
specifier|private
specifier|final
name|JabRefFrame
name|frame
decl_stmt|;
DECL|method|BibtexStringEditorAction (JabRefFrame jabRefFrame)
specifier|public
name|BibtexStringEditorAction
parameter_list|(
name|JabRefFrame
name|jabRefFrame
parameter_list|)
block|{
name|this
operator|.
name|frame
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
name|BibDatabase
name|database
init|=
name|frame
operator|.
name|getCurrentBasePanel
argument_list|()
operator|.
name|getDatabase
argument_list|()
decl_stmt|;
operator|new
name|BibtexStringEditorDialogView
argument_list|(
name|database
argument_list|)
operator|.
name|showAndWait
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

