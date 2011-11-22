begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.groups
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|groups
package|;
end_package

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|undo
operator|.
name|AbstractUndoableEdit
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
name|Globals
import|;
end_import

begin_class
DECL|class|UndoableAddOrRemoveGroup
class|class
name|UndoableAddOrRemoveGroup
extends|extends
name|AbstractUndoableEdit
block|{
comment|/** The root of the global groups tree */
DECL|field|m_groupsRootHandle
specifier|private
specifier|final
name|GroupTreeNode
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
name|int
index|[]
name|m_pathToNode
decl_stmt|;
comment|/**      * The type of the editing (ADD_NODE, REMOVE_NODE_KEEP_CHILDREN,      * REMOVE_NODE_AND_CHILDREN)      */
DECL|field|m_editType
specifier|private
specifier|final
name|int
name|m_editType
decl_stmt|;
DECL|field|m_groupSelector
specifier|private
specifier|final
name|GroupSelector
name|m_groupSelector
decl_stmt|;
DECL|field|m_revalidate
specifier|private
name|boolean
name|m_revalidate
init|=
literal|true
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
comment|/**      * Creates an object that can undo/redo an edit event.      *       * @param groupsRoot      *            The global groups root.      * @param editType      *            The type of editing (ADD_NODE, REMOVE_NODE_KEEP_CHILDREN,      *            REMOVE_NODE_AND_CHILDREN)      * @param editedNode      *            The edited node (which was added or will be removed). The node      *            must be a descendant of node<b>groupsRoot</b>! This means      *            that, in case of adding, you first have to add it to the tree,      *            then call this constructor. When removing, you first have to      *            call this constructor, then remove the node.      */
DECL|method|UndoableAddOrRemoveGroup (GroupSelector gs, GroupTreeNode groupsRoot, GroupTreeNode editedNode, int editType)
specifier|public
name|UndoableAddOrRemoveGroup
parameter_list|(
name|GroupSelector
name|gs
parameter_list|,
name|GroupTreeNode
name|groupsRoot
parameter_list|,
name|GroupTreeNode
name|editedNode
parameter_list|,
name|int
name|editType
parameter_list|)
block|{
name|m_groupSelector
operator|=
name|gs
expr_stmt|;
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
name|REMOVE_NODE_KEEP_CHILDREN
condition|?
name|editedNode
operator|.
name|deepCopy
argument_list|()
else|:
operator|new
name|GroupTreeNode
argument_list|(
name|editedNode
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
name|getIndexedPath
argument_list|()
expr_stmt|;
block|}
DECL|method|getUndoPresentationName ()
specifier|public
name|String
name|getUndoPresentationName
parameter_list|()
block|{
return|return
name|Globals
operator|.
name|lang
argument_list|(
literal|"Undo"
argument_list|)
operator|+
literal|": "
operator|+
name|getName
argument_list|()
return|;
block|}
DECL|method|getName ()
specifier|public
name|String
name|getName
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
name|Globals
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
name|Globals
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
name|Globals
operator|.
name|lang
argument_list|(
literal|"remove group and subgroups"
argument_list|)
return|;
block|}
return|return
literal|"? ("
operator|+
name|Globals
operator|.
name|lang
argument_list|(
literal|"unknown edit"
argument_list|)
operator|+
literal|")"
return|;
block|}
DECL|method|getRedoPresentationName ()
specifier|public
name|String
name|getRedoPresentationName
parameter_list|()
block|{
return|return
name|Globals
operator|.
name|lang
argument_list|(
literal|"Redo"
argument_list|)
operator|+
literal|": "
operator|+
name|getName
argument_list|()
return|;
block|}
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
decl_stmt|;
specifier|final
name|int
name|childIndex
init|=
name|m_pathToNode
index|[
name|m_pathToNode
operator|.
name|length
operator|-
literal|1
index|]
decl_stmt|;
comment|// traverse path up to butlast element
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|m_pathToNode
operator|.
name|length
operator|-
literal|1
condition|;
operator|++
name|i
control|)
name|cursor
operator|=
operator|(
name|GroupTreeNode
operator|)
name|cursor
operator|.
name|getChildAt
argument_list|(
name|m_pathToNode
index|[
name|i
index|]
argument_list|)
expr_stmt|;
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
name|remove
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
name|deepCopy
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
name|childIndex
operator|+
name|m_subtreeRootChildCount
condition|;
operator|++
name|i
control|)
block|{
name|newNode
operator|.
name|add
argument_list|(
operator|(
name|GroupTreeNode
operator|)
name|cursor
operator|.
name|getChildAt
argument_list|(
name|childIndex
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|cursor
operator|.
name|insert
argument_list|(
name|newNode
argument_list|,
name|childIndex
argument_list|)
expr_stmt|;
break|break;
case|case
name|REMOVE_NODE_AND_CHILDREN
case|:
name|cursor
operator|.
name|insert
argument_list|(
name|m_subtreeBackup
operator|.
name|deepCopy
argument_list|()
argument_list|,
name|childIndex
argument_list|)
expr_stmt|;
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
name|cursor
operator|.
name|insert
argument_list|(
name|m_subtreeBackup
operator|.
name|deepCopy
argument_list|()
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
operator|(
name|GroupTreeNode
operator|)
name|cursor
operator|.
name|getChildAt
argument_list|(
name|childIndex
argument_list|)
decl_stmt|;
name|cursor
operator|.
name|remove
argument_list|(
name|childIndex
argument_list|)
expr_stmt|;
while|while
condition|(
name|removedNode
operator|.
name|getChildCount
argument_list|()
operator|>
literal|0
condition|)
name|cursor
operator|.
name|insert
argument_list|(
operator|(
name|GroupTreeNode
operator|)
name|removedNode
operator|.
name|getFirstChild
argument_list|()
argument_list|,
name|childIndex
argument_list|)
expr_stmt|;
break|break;
case|case
name|REMOVE_NODE_AND_CHILDREN
case|:
name|cursor
operator|.
name|remove
argument_list|(
name|childIndex
argument_list|)
expr_stmt|;
break|break;
block|}
block|}
if|if
condition|(
name|m_revalidate
condition|)
name|m_groupSelector
operator|.
name|revalidateGroups
argument_list|()
expr_stmt|;
block|}
comment|/**      * Call this method to decide if the group list should be immediately      * revalidated by this operation. Default is true.      *       * @param val      *            a<code>boolean</code> value      */
DECL|method|setRevalidate (boolean val)
specifier|public
name|void
name|setRevalidate
parameter_list|(
name|boolean
name|val
parameter_list|)
block|{
name|m_revalidate
operator|=
name|val
expr_stmt|;
block|}
block|}
end_class

end_unit

