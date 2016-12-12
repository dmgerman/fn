begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.groups
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|groups
package|;
end_package

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|ActionEvent
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|AbstractAction
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
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
name|net
operator|.
name|sf
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|model
operator|.
name|FieldChange
import|;
end_import

begin_class
DECL|class|RemoveFromGroupAction
specifier|public
class|class
name|RemoveFromGroupAction
extends|extends
name|AbstractAction
block|{
DECL|field|mNode
specifier|private
name|GroupTreeNodeViewModel
name|mNode
decl_stmt|;
DECL|field|mPanel
specifier|private
name|BasePanel
name|mPanel
decl_stmt|;
DECL|method|RemoveFromGroupAction (GroupTreeNodeViewModel node, BasePanel panel)
specifier|public
name|RemoveFromGroupAction
parameter_list|(
name|GroupTreeNodeViewModel
name|node
parameter_list|,
name|BasePanel
name|panel
parameter_list|)
block|{
name|super
argument_list|(
name|node
operator|.
name|getNode
argument_list|()
operator|.
name|getGroup
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|mNode
operator|=
name|node
expr_stmt|;
name|mPanel
operator|=
name|panel
expr_stmt|;
block|}
DECL|method|RemoveFromGroupAction ()
specifier|public
name|RemoveFromGroupAction
parameter_list|()
block|{
name|super
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Remove entry selection from this group"
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|setNode (GroupTreeNodeViewModel node)
specifier|public
name|void
name|setNode
parameter_list|(
name|GroupTreeNodeViewModel
name|node
parameter_list|)
block|{
name|mNode
operator|=
name|node
expr_stmt|;
block|}
DECL|method|setBasePanel (BasePanel panel)
specifier|public
name|void
name|setBasePanel
parameter_list|(
name|BasePanel
name|panel
parameter_list|)
block|{
name|mPanel
operator|=
name|panel
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|actionPerformed (ActionEvent evt)
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|evt
parameter_list|)
block|{
comment|// warn if assignment has undesired side effects (modifies a field != keywords)
if|if
condition|(
operator|!
name|WarnAssignmentSideEffects
operator|.
name|warnAssignmentSideEffects
argument_list|(
name|mNode
operator|.
name|getNode
argument_list|()
operator|.
name|getGroup
argument_list|()
argument_list|,
name|mPanel
operator|.
name|frame
argument_list|()
argument_list|)
condition|)
block|{
return|return;
comment|// user aborted operation
block|}
name|List
argument_list|<
name|FieldChange
argument_list|>
name|undo
init|=
name|mNode
operator|.
name|removeEntriesFromGroup
argument_list|(
name|mPanel
operator|.
name|getSelectedEntries
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|undo
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return;
comment|// no changed made
block|}
name|mPanel
operator|.
name|getUndoManager
argument_list|()
operator|.
name|addEdit
argument_list|(
name|UndoableChangeEntriesOfGroup
operator|.
name|getUndoableEdit
argument_list|(
name|mNode
argument_list|,
name|undo
argument_list|)
argument_list|)
expr_stmt|;
name|mPanel
operator|.
name|markBaseChanged
argument_list|()
expr_stmt|;
name|mPanel
operator|.
name|updateEntryEditorIfShowing
argument_list|()
expr_stmt|;
name|mPanel
operator|.
name|getGroupSelector
argument_list|()
operator|.
name|valueChanged
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

