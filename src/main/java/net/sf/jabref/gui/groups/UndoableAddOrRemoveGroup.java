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
name|util
operator|.
name|List
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
name|undo
operator|.
name|AbstractUndoableJabRefEdit
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
DECL|class|UndoableAddOrRemoveGroup
class|class
name|UndoableAddOrRemoveGroup
extends|extends
name|AbstractUndoableJabRefEdit
block|{
comment|/** The root of the global groups tree */
DECL|field|m_groupsRootHandle
specifier|private
specifier|final
name|GroupTreeNodeViewModel
name|m_groupsRootHandle
decl_stmt|;
comment|/** The subtree that was added or removed */
DECL|field|m_subtreeBackup
specifier|private
specifier|final
name|GroupTreeNode
name|m_subtreeBackup
decl_stmt|;
comment|/**      * In case of removing a node but keeping all of its children, the number of      * children has to be stored.      */
DECL|field|m_subtreeRootChildCount
specifier|private
specifier|final
name|int
name|m_subtreeRootChildCount
decl_stmt|;
comment|/** The path to the edited subtree's root node */
DECL|field|m_pathToNode
specifier|private
specifier|final
name|List
argument_list|<
name|Integer
argument_list|>
name|m_pathToNode
decl_stmt|;
comment|/**      * The type of the editing (ADD_NODE, REMOVE_NODE_KEEP_CHILDREN,      * REMOVE_NODE_AND_CHILDREN)      */
DECL|field|m_editType
specifier|private
specifier|final
name|int
name|m_editType
decl_stmt|;
comment|/** Adding of a single node (group). */
DECL|field|ADD_NODE
specifier|public
specifier|static
specifier|final
name|int
name|ADD_NODE
init|=
literal|0
decl_stmt|;
comment|/** Removal of a single node. Children, if any, are kept. */
DECL|field|REMOVE_NODE_KEEP_CHILDREN
specifier|public
specifier|static
specifier|final
name|int
name|REMOVE_NODE_KEEP_CHILDREN
init|=
literal|1
decl_stmt|;
comment|/** Removal of a node and all of its children. */
DECL|field|REMOVE_NODE_AND_CHILDREN
specifier|public
specifier|static
specifier|final
name|int
name|REMOVE_NODE_AND_CHILDREN
init|=
literal|2
decl_stmt|;
comment|/**      * Creates an object that can undo/redo an edit event.      *      * @param groupsRoot      *            The global groups root.      * @param editType      *            The type of editing (ADD_NODE, REMOVE_NODE_KEEP_CHILDREN,      *            REMOVE_NODE_AND_CHILDREN)      * @param editedNode      *            The edited node (which was added or will be removed). The node      *            must be a descendant of node<b>groupsRoot</b>! This means      *            that, in case of adding, you first have to add it to the tree,      *            then call this constructor. When removing, you first have to      *            call this constructor, then remove the node.      */
DECL|method|UndoableAddOrRemoveGroup (GroupTreeNodeViewModel groupsRoot, GroupTreeNodeViewModel editedNode, int editType)
specifier|public
name|UndoableAddOrRemoveGroup
parameter_list|(
name|GroupTreeNodeViewModel
name|groupsRoot
parameter_list|,
name|GroupTreeNodeViewModel
name|editedNode
parameter_list|,
name|int
name|editType
parameter_list|)
block|{
name|m_groupsRootHandle
operator|=
name|groupsRoot
expr_stmt|;
name|m_editType
operator|=
name|editType
expr_stmt|;
name|m_subtreeRootChildCount
operator|=
name|editedNode
operator|.
name|getChildCount
argument_list|()
expr_stmt|;
comment|// storing a backup of the whole subtree is not required when children
comment|// are kept
name|m_subtreeBackup
operator|=
name|editType
operator|!=
name|UndoableAddOrRemoveGroup
operator|.
name|REMOVE_NODE_KEEP_CHILDREN
condition|?
name|editedNode
operator|.
name|getNode
argument_list|()
operator|.
name|copySubtree
argument_list|()
else|:
name|GroupTreeNode
operator|.
name|fromGroup
argument_list|(
name|editedNode
operator|.
name|getNode
argument_list|()
operator|.
name|getGroup
argument_list|()
operator|.
name|deepCopy
argument_list|()
argument_list|)
expr_stmt|;
comment|// remember path to edited node. this cannot be stored as a reference,
comment|// because the reference itself might change. the method below is more
comment|// robust.
name|m_pathToNode
operator|=
name|editedNode
operator|.
name|getNode
argument_list|()
operator|.
name|getIndexedPathFromRoot
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
switch|switch
condition|(
name|m_editType
condition|)
block|{
case|case
name|ADD_NODE
case|:
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"add group"
argument_list|)
return|;
case|case
name|REMOVE_NODE_KEEP_CHILDREN
case|:
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"remove group (keep subgroups)"
argument_list|)
return|;
case|case
name|REMOVE_NODE_AND_CHILDREN
case|:
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"remove group and subgroups"
argument_list|)
return|;
default|default:
break|break;
block|}
return|return
literal|"? ("
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"unknown edit"
argument_list|)
operator|+
literal|")"
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
name|doOperation
argument_list|(
literal|true
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
name|doOperation
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
DECL|method|doOperation (boolean undo)
specifier|private
name|void
name|doOperation
parameter_list|(
name|boolean
name|undo
parameter_list|)
block|{
name|GroupTreeNode
name|cursor
init|=
name|m_groupsRootHandle
operator|.
name|getNode
argument_list|()
decl_stmt|;
specifier|final
name|int
name|childIndex
init|=
name|m_pathToNode
operator|.
name|get
argument_list|(
name|m_pathToNode
operator|.
name|size
argument_list|()
operator|-
literal|1
argument_list|)
decl_stmt|;
comment|// traverse path up to but last element
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
operator|(
name|m_pathToNode
operator|.
name|size
argument_list|()
operator|-
literal|1
operator|)
condition|;
operator|++
name|i
control|)
block|{
name|cursor
operator|=
name|cursor
operator|.
name|getChildAt
argument_list|(
name|m_pathToNode
operator|.
name|get
argument_list|(
name|i
argument_list|)
argument_list|)
operator|.
name|get
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|undo
condition|)
block|{
switch|switch
condition|(
name|m_editType
condition|)
block|{
case|case
name|ADD_NODE
case|:
name|cursor
operator|.
name|removeChild
argument_list|(
name|childIndex
argument_list|)
expr_stmt|;
break|break;
case|case
name|REMOVE_NODE_KEEP_CHILDREN
case|:
comment|// move all children to newNode, then add newNode
name|GroupTreeNode
name|newNode
init|=
name|m_subtreeBackup
operator|.
name|copySubtree
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
name|childIndex
init|;
name|i
operator|<
operator|(
name|childIndex
operator|+
name|m_subtreeRootChildCount
operator|)
condition|;
operator|++
name|i
control|)
block|{
name|cursor
operator|.
name|getChildAt
argument_list|(
name|childIndex
argument_list|)
operator|.
name|get
argument_list|()
operator|.
name|moveTo
argument_list|(
name|newNode
argument_list|)
expr_stmt|;
block|}
name|newNode
operator|.
name|moveTo
argument_list|(
name|cursor
argument_list|,
name|childIndex
argument_list|)
expr_stmt|;
break|break;
case|case
name|REMOVE_NODE_AND_CHILDREN
case|:
name|m_subtreeBackup
operator|.
name|copySubtree
argument_list|()
operator|.
name|moveTo
argument_list|(
name|cursor
argument_list|,
name|childIndex
argument_list|)
expr_stmt|;
break|break;
default|default:
break|break;
block|}
block|}
else|else
block|{
comment|// redo
switch|switch
condition|(
name|m_editType
condition|)
block|{
case|case
name|ADD_NODE
case|:
name|m_subtreeBackup
operator|.
name|copySubtree
argument_list|()
operator|.
name|moveTo
argument_list|(
name|cursor
argument_list|,
name|childIndex
argument_list|)
expr_stmt|;
break|break;
case|case
name|REMOVE_NODE_KEEP_CHILDREN
case|:
comment|// remove node, then insert all children
name|GroupTreeNode
name|removedNode
init|=
name|cursor
operator|.
name|getChildAt
argument_list|(
name|childIndex
argument_list|)
operator|.
name|get
argument_list|()
decl_stmt|;
name|cursor
operator|.
name|removeChild
argument_list|(
name|childIndex
argument_list|)
expr_stmt|;
while|while
condition|(
name|removedNode
operator|.
name|getNumberOfChildren
argument_list|()
operator|>
literal|0
condition|)
block|{
name|removedNode
operator|.
name|getFirstChild
argument_list|()
operator|.
name|get
argument_list|()
operator|.
name|moveTo
argument_list|(
name|cursor
argument_list|,
name|childIndex
argument_list|)
expr_stmt|;
block|}
break|break;
case|case
name|REMOVE_NODE_AND_CHILDREN
case|:
name|cursor
operator|.
name|removeChild
argument_list|(
name|childIndex
argument_list|)
expr_stmt|;
break|break;
default|default:
break|break;
block|}
block|}
block|}
block|}
end_class

end_unit

