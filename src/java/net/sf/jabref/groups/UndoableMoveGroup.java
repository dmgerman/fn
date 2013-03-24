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

begin_comment
comment|/**  * @author jzieren  *   * TODO To change the template for this generated type comment go to Window -  * Preferences - Java - Code Style - Code Templates  */
end_comment

begin_class
DECL|class|UndoableMoveGroup
specifier|public
class|class
name|UndoableMoveGroup
extends|extends
name|AbstractUndoableEdit
block|{
DECL|field|m_groupSelector
specifier|private
specifier|final
name|GroupSelector
name|m_groupSelector
decl_stmt|;
DECL|field|m_groupsRootHandle
specifier|private
specifier|final
name|GroupTreeNode
name|m_groupsRootHandle
decl_stmt|;
DECL|field|m_pathToNewParent
specifier|private
specifier|final
name|int
index|[]
name|m_pathToNewParent
decl_stmt|;
DECL|field|m_newChildIndex
specifier|private
specifier|final
name|int
name|m_newChildIndex
decl_stmt|;
DECL|field|m_pathToOldParent
specifier|private
specifier|final
name|int
index|[]
name|m_pathToOldParent
decl_stmt|;
DECL|field|m_oldChildIndex
specifier|private
specifier|final
name|int
name|m_oldChildIndex
decl_stmt|;
comment|/**      * @param moveNode      *            The node which is being moved. At the time of construction of      *            this object, it must not have moved yet.      * @param newParent      *            The new parent node to which<b>moveNode</b> will be moved.      * @param newChildIndex      *            The child index at<b>newParent</b> to which<b>moveNode</b>      *            will be moved.      */
DECL|method|UndoableMoveGroup (GroupSelector gs, GroupTreeNode groupsRoot, GroupTreeNode moveNode, GroupTreeNode newParent, int newChildIndex)
specifier|public
name|UndoableMoveGroup
parameter_list|(
name|GroupSelector
name|gs
parameter_list|,
name|GroupTreeNode
name|groupsRoot
parameter_list|,
name|GroupTreeNode
name|moveNode
parameter_list|,
name|GroupTreeNode
name|newParent
parameter_list|,
name|int
name|newChildIndex
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
name|m_pathToNewParent
operator|=
name|newParent
operator|.
name|getIndexedPath
argument_list|()
expr_stmt|;
name|m_newChildIndex
operator|=
name|newChildIndex
expr_stmt|;
name|m_pathToOldParent
operator|=
operator|(
operator|(
name|GroupTreeNode
operator|)
name|moveNode
operator|.
name|getParent
argument_list|()
operator|)
operator|.
name|getIndexedPath
argument_list|()
expr_stmt|;
name|m_oldChildIndex
operator|=
name|moveNode
operator|.
name|getParent
argument_list|()
operator|.
name|getIndex
argument_list|(
name|moveNode
argument_list|)
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
name|Globals
operator|.
name|lang
argument_list|(
literal|"move group"
argument_list|)
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
name|Globals
operator|.
name|lang
argument_list|(
literal|"move group"
argument_list|)
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
name|GroupTreeNode
name|cursor
init|=
name|m_groupsRootHandle
operator|.
name|getDescendant
argument_list|(
name|m_pathToNewParent
argument_list|)
decl_stmt|;
name|cursor
operator|=
operator|(
name|GroupTreeNode
operator|)
name|cursor
operator|.
name|getChildAt
argument_list|(
name|m_newChildIndex
argument_list|)
expr_stmt|;
name|m_groupsRootHandle
operator|.
name|getDescendant
argument_list|(
name|m_pathToOldParent
argument_list|)
operator|.
name|insert
argument_list|(
name|cursor
argument_list|,
name|m_oldChildIndex
argument_list|)
expr_stmt|;
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
name|GroupTreeNode
name|cursor
init|=
name|m_groupsRootHandle
operator|.
name|getDescendant
argument_list|(
name|m_pathToOldParent
argument_list|)
decl_stmt|;
name|cursor
operator|=
operator|(
name|GroupTreeNode
operator|)
name|cursor
operator|.
name|getChildAt
argument_list|(
name|m_oldChildIndex
argument_list|)
expr_stmt|;
name|m_groupsRootHandle
operator|.
name|getDescendant
argument_list|(
name|m_pathToNewParent
argument_list|)
operator|.
name|insert
argument_list|(
name|cursor
argument_list|,
name|m_newChildIndex
argument_list|)
expr_stmt|;
name|m_groupSelector
operator|.
name|revalidateGroups
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

