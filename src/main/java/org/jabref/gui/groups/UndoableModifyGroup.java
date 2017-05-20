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
name|groups
operator|.
name|AbstractGroup
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
DECL|class|UndoableModifyGroup
class|class
name|UndoableModifyGroup
extends|extends
name|AbstractUndoableJabRefEdit
block|{
DECL|field|groupSidePane
specifier|private
specifier|final
name|GroupSidePane
name|groupSidePane
decl_stmt|;
DECL|field|m_oldGroupBackup
specifier|private
specifier|final
name|AbstractGroup
name|m_oldGroupBackup
decl_stmt|;
DECL|field|m_newGroupBackup
specifier|private
specifier|final
name|AbstractGroup
name|m_newGroupBackup
decl_stmt|;
DECL|field|m_groupsRootHandle
specifier|private
specifier|final
name|GroupTreeNode
name|m_groupsRootHandle
decl_stmt|;
DECL|field|m_pathToNode
specifier|private
specifier|final
name|List
argument_list|<
name|Integer
argument_list|>
name|m_pathToNode
decl_stmt|;
comment|/**      * @param node      *            The node which still contains the old group.      * @param newGroup      *            The new group to replace the one currently stored in<b>node      *</b>.      */
DECL|method|UndoableModifyGroup (GroupSidePane gs, GroupTreeNodeViewModel groupsRoot, GroupTreeNodeViewModel node, AbstractGroup newGroup)
specifier|public
name|UndoableModifyGroup
parameter_list|(
name|GroupSidePane
name|gs
parameter_list|,
name|GroupTreeNodeViewModel
name|groupsRoot
parameter_list|,
name|GroupTreeNodeViewModel
name|node
parameter_list|,
name|AbstractGroup
name|newGroup
parameter_list|)
block|{
name|groupSidePane
operator|=
name|gs
expr_stmt|;
name|m_oldGroupBackup
operator|=
name|node
operator|.
name|getNode
argument_list|()
operator|.
name|getGroup
argument_list|()
operator|.
name|deepCopy
argument_list|()
expr_stmt|;
name|m_newGroupBackup
operator|=
name|newGroup
operator|.
name|deepCopy
argument_list|()
expr_stmt|;
name|m_pathToNode
operator|=
name|node
operator|.
name|getNode
argument_list|()
operator|.
name|getIndexedPathFromRoot
argument_list|()
expr_stmt|;
name|m_groupsRootHandle
operator|=
name|groupsRoot
operator|.
name|getNode
argument_list|()
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
name|Localization
operator|.
name|lang
argument_list|(
literal|"modify group"
argument_list|)
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
comment|//TODO: NULL
name|m_groupsRootHandle
operator|.
name|getDescendant
argument_list|(
name|m_pathToNode
argument_list|)
operator|.
name|get
argument_list|()
operator|.
name|setGroup
argument_list|(
name|m_oldGroupBackup
operator|.
name|deepCopy
argument_list|()
argument_list|)
expr_stmt|;
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
name|m_groupsRootHandle
operator|.
name|getDescendant
argument_list|(
name|m_pathToNode
argument_list|)
operator|.
name|get
argument_list|()
operator|.
name|setGroup
argument_list|(
name|m_newGroupBackup
operator|.
name|deepCopy
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

