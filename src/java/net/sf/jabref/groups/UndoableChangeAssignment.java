begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  All programs in this directory and subdirectories are published under the   GNU General Public License as described below.   This program is free software; you can redistribute it and/or modify it   under the terms of the GNU General Public License as published by the Free   Software Foundation; either version 2 of the License, or (at your option)   any later version.   This program is distributed in the hope that it will be useful, but WITHOUT   ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or   FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for   more details.   You should have received a copy of the GNU General Public License along   with this program; if not, write to the Free Software Foundation, Inc., 59   Temple Place, Suite 330, Boston, MA 02111-1307 USA   Further information about the GNU GPL is available at:  http://www.gnu.org/copyleft/gpl.ja.html  */
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
name|*
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
name|BibtexEntry
import|;
end_import

begin_comment
comment|/**  * @author jzieren  *   */
end_comment

begin_class
DECL|class|UndoableChangeAssignment
specifier|public
class|class
name|UndoableChangeAssignment
extends|extends
name|AbstractUndoableEdit
block|{
DECL|field|m_previousAssignmentBackup
specifier|private
specifier|final
name|Set
name|m_previousAssignmentBackup
decl_stmt|;
DECL|field|m_newAssignmentBackup
specifier|private
specifier|final
name|Set
name|m_newAssignmentBackup
decl_stmt|;
comment|/** The path to the edited node */
DECL|field|m_pathToNode
specifier|private
name|int
index|[]
name|m_pathToNode
init|=
literal|null
decl_stmt|;
comment|/** The root of the global groups tree */
DECL|field|m_groupsRootHandle
specifier|private
name|GroupTreeNode
name|m_groupsRootHandle
init|=
literal|null
decl_stmt|;
DECL|method|UndoableChangeAssignment (Set previousAssignment, Set currentAssignment)
specifier|public
name|UndoableChangeAssignment
parameter_list|(
name|Set
name|previousAssignment
parameter_list|,
name|Set
name|currentAssignment
parameter_list|)
block|{
name|m_previousAssignmentBackup
operator|=
operator|new
name|HashSet
argument_list|(
name|previousAssignment
argument_list|)
expr_stmt|;
name|m_newAssignmentBackup
operator|=
operator|new
name|HashSet
argument_list|(
name|currentAssignment
argument_list|)
expr_stmt|;
block|}
comment|/**      * Sets the node of the group that was edited. This method has to be called      * before this instance may be used.      *       * @param node      *            The node whose assignments were edited.      */
DECL|method|setEditedNode (GroupTreeNode node)
specifier|public
name|void
name|setEditedNode
parameter_list|(
name|GroupTreeNode
name|node
parameter_list|)
block|{
name|m_groupsRootHandle
operator|=
operator|(
name|GroupTreeNode
operator|)
name|node
operator|.
name|getRoot
argument_list|()
expr_stmt|;
name|m_pathToNode
operator|=
name|node
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
literal|"Undo: (de)assign entries"
return|;
block|}
DECL|method|getRedoPresentationName ()
specifier|public
name|String
name|getRedoPresentationName
parameter_list|()
block|{
return|return
literal|"Redo: (de)assign entries"
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
name|ExplicitGroup
name|group
init|=
operator|(
name|ExplicitGroup
operator|)
name|m_groupsRootHandle
operator|.
name|getChildAt
argument_list|(
name|m_pathToNode
argument_list|)
operator|.
name|getGroup
argument_list|()
decl_stmt|;
name|group
operator|.
name|clearAssignments
argument_list|()
expr_stmt|;
for|for
control|(
name|Iterator
name|it
init|=
name|m_previousAssignmentBackup
operator|.
name|iterator
argument_list|()
init|;
name|it
operator|.
name|hasNext
argument_list|()
condition|;
control|)
name|group
operator|.
name|addEntry
argument_list|(
operator|(
name|BibtexEntry
operator|)
name|it
operator|.
name|next
argument_list|()
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
name|ExplicitGroup
name|group
init|=
operator|(
name|ExplicitGroup
operator|)
name|m_groupsRootHandle
operator|.
name|getChildAt
argument_list|(
name|m_pathToNode
argument_list|)
operator|.
name|getGroup
argument_list|()
decl_stmt|;
name|group
operator|.
name|clearAssignments
argument_list|()
expr_stmt|;
for|for
control|(
name|Iterator
name|it
init|=
name|m_newAssignmentBackup
operator|.
name|iterator
argument_list|()
init|;
name|it
operator|.
name|hasNext
argument_list|()
condition|;
control|)
name|group
operator|.
name|addEntry
argument_list|(
operator|(
name|BibtexEntry
operator|)
name|it
operator|.
name|next
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

