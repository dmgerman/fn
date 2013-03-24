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
name|Util
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
DECL|field|m_node
specifier|protected
name|GroupTreeNode
name|m_node
decl_stmt|;
DECL|field|m_panel
specifier|protected
name|BasePanel
name|m_panel
decl_stmt|;
DECL|method|RemoveFromGroupAction (GroupTreeNode node, BasePanel panel)
specifier|public
name|RemoveFromGroupAction
parameter_list|(
name|GroupTreeNode
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
name|m_panel
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
name|Globals
operator|.
name|lang
argument_list|(
literal|"Remove entry selection from this group"
argument_list|)
argument_list|)
expr_stmt|;
comment|// JZTODO lyrics
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
name|m_panel
operator|.
name|getSelectedEntries
argument_list|()
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
return|return;
end_class

begin_comment
comment|// user aborted operation
end_comment

begin_decl_stmt
name|AbstractUndoableEdit
name|undo
init|=
name|m_node
operator|.
name|removeFromGroup
argument_list|(
name|m_panel
operator|.
name|getSelectedEntries
argument_list|()
argument_list|)
decl_stmt|;
end_decl_stmt

begin_if
if|if
condition|(
name|undo
operator|==
literal|null
condition|)
return|return;
end_if

begin_comment
comment|// no changed made
end_comment

begin_expr_stmt
name|m_panel
operator|.
name|undoManager
operator|.
name|addEdit
argument_list|(
name|undo
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

