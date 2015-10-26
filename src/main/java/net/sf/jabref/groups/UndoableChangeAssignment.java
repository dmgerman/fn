begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|HashSet
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Set
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
name|model
operator|.
name|entry
operator|.
name|BibtexEntry
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
name|groups
operator|.
name|structure
operator|.
name|ExplicitGroup
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

begin_comment
comment|/**  * @author jzieren  *  */
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
argument_list|<
name|BibtexEntry
argument_list|>
name|m_previousAssignmentBackup
decl_stmt|;
DECL|field|m_newAssignmentBackup
specifier|private
specifier|final
name|Set
argument_list|<
name|BibtexEntry
argument_list|>
name|m_newAssignmentBackup
decl_stmt|;
comment|/** The path to the edited node */
DECL|field|m_pathToNode
specifier|private
name|int
index|[]
name|m_pathToNode
decl_stmt|;
comment|/** The root of the global groups tree */
DECL|field|m_groupsRootHandle
specifier|private
name|GroupTreeNode
name|m_groupsRootHandle
decl_stmt|;
comment|/**      * Constructor for use in a group itself, where the enclosing node is      * unknown. The node must be set using setEditedNode() before this instance      * may be used.      *      * @param previousAssignment      * @param currentAssignment      */
DECL|method|UndoableChangeAssignment (Set<BibtexEntry> previousAssignment, Set<BibtexEntry> currentAssignment)
specifier|public
name|UndoableChangeAssignment
parameter_list|(
name|Set
argument_list|<
name|BibtexEntry
argument_list|>
name|previousAssignment
parameter_list|,
name|Set
argument_list|<
name|BibtexEntry
argument_list|>
name|currentAssignment
parameter_list|)
block|{
name|m_previousAssignmentBackup
operator|=
operator|new
name|HashSet
argument_list|<>
argument_list|(
name|previousAssignment
argument_list|)
expr_stmt|;
name|m_newAssignmentBackup
operator|=
operator|new
name|HashSet
argument_list|<>
argument_list|(
name|currentAssignment
argument_list|)
expr_stmt|;
block|}
DECL|method|UndoableChangeAssignment (Set<BibtexEntry> previousAssignment, Set<BibtexEntry> currentAssignment, GroupTreeNode node)
specifier|public
name|UndoableChangeAssignment
parameter_list|(
name|Set
argument_list|<
name|BibtexEntry
argument_list|>
name|previousAssignment
parameter_list|,
name|Set
argument_list|<
name|BibtexEntry
argument_list|>
name|currentAssignment
parameter_list|,
name|GroupTreeNode
name|node
parameter_list|)
block|{
name|this
argument_list|(
name|previousAssignment
argument_list|,
name|currentAssignment
argument_list|)
expr_stmt|;
name|setEditedNode
argument_list|(
name|node
argument_list|)
expr_stmt|;
block|}
comment|/**      * Sets the node of the group that was edited. If this node was not      * specified at construction time, this method has to be called before this      * instance may be used.      *      * @param node      *            The node whose assignments were edited.      */
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
annotation|@
name|Override
DECL|method|getUndoPresentationName ()
specifier|public
name|String
name|getUndoPresentationName
parameter_list|()
block|{
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"Undo"
argument_list|)
operator|+
literal|": "
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"change assignment of entries"
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|getRedoPresentationName ()
specifier|public
name|String
name|getRedoPresentationName
parameter_list|()
block|{
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"Redo"
argument_list|)
operator|+
literal|": "
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"change assignment of entries"
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
name|BibtexEntry
name|aM_previousAssignmentBackup
range|:
name|m_previousAssignmentBackup
control|)
block|{
name|group
operator|.
name|addEntry
argument_list|(
name|aM_previousAssignmentBackup
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
name|BibtexEntry
name|aM_newAssignmentBackup
range|:
name|m_newAssignmentBackup
control|)
block|{
name|group
operator|.
name|addEntry
argument_list|(
name|aM_newAssignmentBackup
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

