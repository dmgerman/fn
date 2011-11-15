begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003 Morten O. Alver, Nizar N. Batada   All programs in this directory and  subdirectories are published under the GNU General Public License as  described below.   This program is free software; you can redistribute it and/or modify  it under the terms of the GNU General Public License as published by  the Free Software Foundation; either version 2 of the License, or (at  your option) any later version.   This program is distributed in the hope that it will be useful, but  WITHOUT ANY WARRANTY; without even the implied warranty of  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU  General Public License for more details.   You should have received a copy of the GNU General Public License  along with this program; if not, write to the Free Software  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA   Further information about the GNU GPL is available at:  http://www.gnu.org/copyleft/gpl.ja.html   */
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
name|java
operator|.
name|util
operator|.
name|Vector
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|tree
operator|.
name|TreeNode
import|;
end_import

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
DECL|class|UndoableModifySubtree
specifier|public
class|class
name|UndoableModifySubtree
extends|extends
name|AbstractUndoableEdit
block|{
comment|/** A backup of the groups before the modification */
DECL|field|m_groupRoot
DECL|field|m_subtreeBackup
specifier|private
specifier|final
name|GroupTreeNode
name|m_groupRoot
decl_stmt|,
name|m_subtreeBackup
decl_stmt|;
comment|/** The path to the global groups root node */
DECL|field|m_subtreeRootPath
specifier|private
specifier|final
name|int
index|[]
name|m_subtreeRootPath
decl_stmt|;
DECL|field|m_groupSelector
specifier|private
specifier|final
name|GroupSelector
name|m_groupSelector
decl_stmt|;
comment|/** This holds the new subtree (the root's modified children) to allow redo. */
DECL|field|m_modifiedSubtree
specifier|private
name|Vector
argument_list|<
name|TreeNode
argument_list|>
name|m_modifiedSubtree
init|=
operator|new
name|Vector
argument_list|<
name|TreeNode
argument_list|>
argument_list|()
decl_stmt|;
DECL|field|m_revalidate
specifier|private
name|boolean
name|m_revalidate
init|=
literal|true
decl_stmt|;
DECL|field|m_name
specifier|private
specifier|final
name|String
name|m_name
decl_stmt|;
comment|/**      *       * @param subtree      *            The root node of the subtree that was modified (this node may      *            not be modified, it is just used as a convenience handle).      */
DECL|method|UndoableModifySubtree (GroupSelector groupSelector, GroupTreeNode groupRoot, GroupTreeNode subtree, String name)
specifier|public
name|UndoableModifySubtree
parameter_list|(
name|GroupSelector
name|groupSelector
parameter_list|,
name|GroupTreeNode
name|groupRoot
parameter_list|,
name|GroupTreeNode
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
name|deepCopy
argument_list|()
expr_stmt|;
name|m_groupRoot
operator|=
name|groupRoot
expr_stmt|;
name|m_subtreeRootPath
operator|=
name|subtree
operator|.
name|getIndexedPath
argument_list|()
expr_stmt|;
name|m_groupSelector
operator|=
name|groupSelector
expr_stmt|;
name|m_name
operator|=
name|name
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
name|m_name
return|;
comment|// JZTODO lyrics
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
name|m_name
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
name|getNode
argument_list|(
name|m_subtreeRootPath
argument_list|)
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|subtreeRoot
operator|.
name|getChildCount
argument_list|()
condition|;
operator|++
name|i
control|)
name|m_modifiedSubtree
operator|.
name|add
argument_list|(
name|subtreeRoot
operator|.
name|getChildAt
argument_list|(
name|i
argument_list|)
argument_list|)
expr_stmt|;
comment|// keep subtree handle, but restore everything else from backup
name|subtreeRoot
operator|.
name|removeAllChildren
argument_list|()
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|m_subtreeBackup
operator|.
name|getChildCount
argument_list|()
condition|;
operator|++
name|i
control|)
name|subtreeRoot
operator|.
name|add
argument_list|(
operator|(
operator|(
name|GroupTreeNode
operator|)
name|m_subtreeBackup
operator|.
name|getChildAt
argument_list|(
name|i
argument_list|)
operator|)
operator|.
name|deepCopy
argument_list|()
argument_list|)
expr_stmt|;
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
name|getNode
argument_list|(
name|m_subtreeRootPath
argument_list|)
decl_stmt|;
name|subtreeRoot
operator|.
name|removeAllChildren
argument_list|()
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|m_modifiedSubtree
operator|.
name|size
argument_list|()
condition|;
operator|++
name|i
control|)
name|subtreeRoot
operator|.
name|add
argument_list|(
operator|(
name|GroupTreeNode
operator|)
name|m_modifiedSubtree
operator|.
name|elementAt
argument_list|(
name|i
argument_list|)
argument_list|)
expr_stmt|;
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
comment|/**      * Call this method to decide if the group list should be immediately      * revalidated by this operation. Default is true.      */
DECL|method|setRevalidate (boolean revalidate)
specifier|public
name|void
name|setRevalidate
parameter_list|(
name|boolean
name|revalidate
parameter_list|)
block|{
name|m_revalidate
operator|=
name|revalidate
expr_stmt|;
block|}
block|}
end_class

end_unit

