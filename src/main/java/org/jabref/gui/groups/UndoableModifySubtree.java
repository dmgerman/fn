begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.groups
package|package
name|org
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
name|util
operator|.
name|ArrayList
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
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|undo
operator|.
name|AbstractUndoableJabRefEdit
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
name|groups
operator|.
name|GroupTreeNode
import|;
end_import

begin_class
DECL|class|UndoableModifySubtree
specifier|public
class|class
name|UndoableModifySubtree
extends|extends
name|AbstractUndoableJabRefEdit
block|{
comment|/** A backup of the groups before the modification */
DECL|field|m_groupRoot
specifier|private
specifier|final
name|GroupTreeNode
name|m_groupRoot
decl_stmt|;
DECL|field|m_subtreeBackup
specifier|private
specifier|final
name|GroupTreeNode
name|m_subtreeBackup
decl_stmt|;
comment|/** The path to the global groups root node */
DECL|field|m_subtreeRootPath
specifier|private
specifier|final
name|List
argument_list|<
name|Integer
argument_list|>
name|m_subtreeRootPath
decl_stmt|;
comment|/** This holds the new subtree (the root's modified children) to allow redo. */
DECL|field|m_modifiedSubtree
specifier|private
specifier|final
name|List
argument_list|<
name|GroupTreeNode
argument_list|>
name|m_modifiedSubtree
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|m_name
specifier|private
specifier|final
name|String
name|m_name
decl_stmt|;
comment|/**      *      * @param subtree      *            The root node of the subtree that was modified (this node may      *            not be modified, it is just used as a convenience handle).      */
DECL|method|UndoableModifySubtree (GroupTreeNodeViewModel groupRoot, GroupTreeNodeViewModel subtree, String name)
specifier|public
name|UndoableModifySubtree
parameter_list|(
name|GroupTreeNodeViewModel
name|groupRoot
parameter_list|,
name|GroupTreeNodeViewModel
name|subtree
parameter_list|,
name|String
name|name
parameter_list|)
block|{
name|m_subtreeBackup
operator|=
name|subtree
operator|.
name|getNode
argument_list|()
operator|.
name|copySubtree
argument_list|()
expr_stmt|;
name|m_groupRoot
operator|=
name|groupRoot
operator|.
name|getNode
argument_list|()
expr_stmt|;
name|m_subtreeRootPath
operator|=
name|subtree
operator|.
name|getNode
argument_list|()
operator|.
name|getIndexedPathFromRoot
argument_list|()
expr_stmt|;
name|m_name
operator|=
name|name
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|getPresentationName ()
specifier|public
name|String
name|getPresentationName
parameter_list|()
block|{
return|return
name|m_name
return|;
block|}
annotation|@
name|Override
DECL|method|undo ()
specifier|public
name|void
name|undo
parameter_list|()
block|{
name|super
operator|.
name|undo
argument_list|()
expr_stmt|;
comment|// remember modified children for redo
name|m_modifiedSubtree
operator|.
name|clear
argument_list|()
expr_stmt|;
comment|// get node to edit
specifier|final
name|GroupTreeNode
name|subtreeRoot
init|=
name|m_groupRoot
operator|.
name|getDescendant
argument_list|(
name|m_subtreeRootPath
argument_list|)
operator|.
name|get
argument_list|()
decl_stmt|;
comment|//TODO: NULL
for|for
control|(
name|GroupTreeNode
name|child
range|:
name|subtreeRoot
operator|.
name|getChildren
argument_list|()
control|)
block|{
name|m_modifiedSubtree
operator|.
name|add
argument_list|(
name|child
argument_list|)
expr_stmt|;
block|}
comment|// keep subtree handle, but restore everything else from backup
name|subtreeRoot
operator|.
name|removeAllChildren
argument_list|()
expr_stmt|;
for|for
control|(
name|GroupTreeNode
name|child
range|:
name|m_subtreeBackup
operator|.
name|getChildren
argument_list|()
control|)
block|{
name|child
operator|.
name|copySubtree
argument_list|()
operator|.
name|moveTo
argument_list|(
name|subtreeRoot
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
DECL|method|redo ()
specifier|public
name|void
name|redo
parameter_list|()
block|{
name|super
operator|.
name|redo
argument_list|()
expr_stmt|;
specifier|final
name|GroupTreeNode
name|subtreeRoot
init|=
name|m_groupRoot
operator|.
name|getDescendant
argument_list|(
name|m_subtreeRootPath
argument_list|)
operator|.
name|get
argument_list|()
decl_stmt|;
comment|//TODO: NULL
name|subtreeRoot
operator|.
name|removeAllChildren
argument_list|()
expr_stmt|;
for|for
control|(
name|GroupTreeNode
name|modifiedNode
range|:
name|m_modifiedSubtree
control|)
block|{
name|modifiedNode
operator|.
name|moveTo
argument_list|(
name|subtreeRoot
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

