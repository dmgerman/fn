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
name|Enumeration
import|;
end_import

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
name|AbstractAction
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
name|Globals
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
name|AbstractGroup
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
name|util
operator|.
name|Util
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
name|undo
operator|.
name|NamedCompound
import|;
end_import

begin_class
DECL|class|AddToGroupAction
specifier|public
class|class
name|AddToGroupAction
extends|extends
name|AbstractAction
block|{
DECL|field|m_node
specifier|private
name|GroupTreeNode
name|m_node
decl_stmt|;
DECL|field|m_move
specifier|private
specifier|final
name|boolean
name|m_move
decl_stmt|;
DECL|field|m_panel
specifier|private
name|BasePanel
name|m_panel
decl_stmt|;
comment|/**      * @param move If true, remove node from all other groups.      */
DECL|method|AddToGroupAction (GroupTreeNode node, boolean move, BasePanel panel)
specifier|public
name|AddToGroupAction
parameter_list|(
name|GroupTreeNode
name|node
parameter_list|,
name|boolean
name|move
parameter_list|,
name|BasePanel
name|panel
parameter_list|)
block|{
name|super
argument_list|(
name|node
operator|.
name|getGroup
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|m_node
operator|=
name|node
expr_stmt|;
name|m_move
operator|=
name|move
expr_stmt|;
name|m_panel
operator|=
name|panel
expr_stmt|;
block|}
DECL|method|AddToGroupAction (boolean move)
specifier|public
name|AddToGroupAction
parameter_list|(
name|boolean
name|move
parameter_list|)
block|{
name|super
argument_list|(
name|move
condition|?
name|Globals
operator|.
name|lang
argument_list|(
literal|"Assign entry selection exclusively to this group"
argument_list|)
else|:
name|Globals
operator|.
name|lang
argument_list|(
literal|"Add entry selection to this group"
argument_list|)
argument_list|)
expr_stmt|;
name|m_move
operator|=
name|move
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
name|m_panel
operator|=
name|panel
expr_stmt|;
block|}
DECL|method|setNode (GroupTreeNode node)
specifier|public
name|void
name|setNode
parameter_list|(
name|GroupTreeNode
name|node
parameter_list|)
block|{
name|m_node
operator|=
name|node
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
specifier|final
name|BibtexEntry
index|[]
name|entries
init|=
name|m_panel
operator|.
name|getSelectedEntries
argument_list|()
decl_stmt|;
specifier|final
name|Vector
argument_list|<
name|GroupTreeNode
argument_list|>
name|removeGroupsNodes
init|=
operator|new
name|Vector
argument_list|<>
argument_list|()
decl_stmt|;
comment|// used only when moving
if|if
condition|(
name|m_move
condition|)
block|{
comment|// collect warnings for removal
name|Enumeration
argument_list|<
name|GroupTreeNode
argument_list|>
name|e
init|=
operator|(
operator|(
name|GroupTreeNode
operator|)
name|m_node
operator|.
name|getRoot
argument_list|()
operator|)
operator|.
name|preorderEnumeration
argument_list|()
decl_stmt|;
name|GroupTreeNode
name|node
decl_stmt|;
while|while
condition|(
name|e
operator|.
name|hasMoreElements
argument_list|()
condition|)
block|{
name|node
operator|=
name|e
operator|.
name|nextElement
argument_list|()
expr_stmt|;
if|if
condition|(
operator|!
name|node
operator|.
name|getGroup
argument_list|()
operator|.
name|supportsRemove
argument_list|()
condition|)
block|{
continue|continue;
block|}
for|for
control|(
name|BibtexEntry
name|entry
range|:
name|entries
control|)
block|{
if|if
condition|(
name|node
operator|.
name|getGroup
argument_list|()
operator|.
name|contains
argument_list|(
name|entry
argument_list|)
condition|)
block|{
name|removeGroupsNodes
operator|.
name|add
argument_list|(
name|node
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|// warning for all groups from which the entries are removed, and
comment|// for the one to which they are added! hence the magical +1
name|AbstractGroup
index|[]
name|groups
init|=
operator|new
name|AbstractGroup
index|[
name|removeGroupsNodes
operator|.
name|size
argument_list|()
operator|+
literal|1
index|]
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
name|removeGroupsNodes
operator|.
name|size
argument_list|()
condition|;
operator|++
name|i
control|)
block|{
name|groups
index|[
name|i
index|]
operator|=
name|removeGroupsNodes
operator|.
name|elementAt
argument_list|(
name|i
argument_list|)
operator|.
name|getGroup
argument_list|()
expr_stmt|;
block|}
name|groups
index|[
name|groups
operator|.
name|length
operator|-
literal|1
index|]
operator|=
name|m_node
operator|.
name|getGroup
argument_list|()
expr_stmt|;
if|if
condition|(
operator|!
name|Util
operator|.
name|warnAssignmentSideEffects
argument_list|(
name|groups
argument_list|,
name|entries
argument_list|,
name|m_panel
operator|.
name|getDatabase
argument_list|()
argument_list|,
name|m_panel
operator|.
name|frame
argument_list|()
argument_list|)
condition|)
block|{
return|return;
comment|// user aborted operation
block|}
block|}
else|else
block|{
comment|// warn if assignment has undesired side effects (modifies a field != keywords)
if|if
condition|(
operator|!
name|Util
operator|.
name|warnAssignmentSideEffects
argument_list|(
operator|new
name|AbstractGroup
index|[]
block|{
name|m_node
operator|.
name|getGroup
argument_list|()
block|}
operator|,
name|entries
operator|,
name|m_panel
operator|.
name|getDatabase
argument_list|()
operator|,
name|m_panel
operator|.
name|frame
argument_list|()
block|)
block|)
block|{
return|return;
comment|// user aborted operation
block|}
block|}
end_class

begin_comment
comment|// if an editor is showing, its fields must be updated
end_comment

begin_comment
comment|// after the assignment, and before that, the current
end_comment

begin_comment
comment|// edit has to be stored:
end_comment

begin_expr_stmt
name|m_panel
operator|.
name|storeCurrentEdit
argument_list|()
expr_stmt|;
end_expr_stmt

begin_decl_stmt
name|NamedCompound
name|undoAll
init|=
operator|new
name|NamedCompound
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"change assignment of entries"
argument_list|)
argument_list|)
decl_stmt|;
end_decl_stmt

begin_if
if|if
condition|(
name|m_move
condition|)
block|{
comment|// first remove
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|removeGroupsNodes
operator|.
name|size
argument_list|()
condition|;
operator|++
name|i
control|)
block|{
name|GroupTreeNode
name|node
init|=
name|removeGroupsNodes
operator|.
name|elementAt
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
name|node
operator|.
name|getGroup
argument_list|()
operator|.
name|containsAny
argument_list|(
name|entries
argument_list|)
condition|)
block|{
name|undoAll
operator|.
name|addEdit
argument_list|(
name|node
operator|.
name|removeFromGroup
argument_list|(
name|entries
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
comment|// then add
name|AbstractUndoableEdit
name|undoAdd
init|=
name|m_node
operator|.
name|addToGroup
argument_list|(
name|entries
argument_list|)
decl_stmt|;
if|if
condition|(
name|undoAdd
operator|!=
literal|null
condition|)
block|{
name|undoAll
operator|.
name|addEdit
argument_list|(
name|undoAdd
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|AbstractUndoableEdit
name|undoAdd
init|=
name|m_node
operator|.
name|addToGroup
argument_list|(
name|entries
argument_list|)
decl_stmt|;
if|if
condition|(
name|undoAdd
operator|==
literal|null
condition|)
block|{
return|return;
comment|// no changed made
block|}
name|undoAll
operator|.
name|addEdit
argument_list|(
name|undoAdd
argument_list|)
expr_stmt|;
block|}
end_if

begin_expr_stmt
name|undoAll
operator|.
name|end
argument_list|()
expr_stmt|;
end_expr_stmt

begin_expr_stmt
name|m_panel
operator|.
name|undoManager
operator|.
name|addEdit
argument_list|(
name|undoAll
argument_list|)
expr_stmt|;
end_expr_stmt

begin_expr_stmt
name|m_panel
operator|.
name|markBaseChanged
argument_list|()
expr_stmt|;
end_expr_stmt

begin_expr_stmt
name|m_panel
operator|.
name|updateEntryEditorIfShowing
argument_list|()
expr_stmt|;
end_expr_stmt

begin_expr_stmt
name|m_panel
operator|.
name|getGroupSelector
argument_list|()
operator|.
name|valueChanged
argument_list|(
literal|null
argument_list|)
expr_stmt|;
end_expr_stmt

unit|} }
end_unit

