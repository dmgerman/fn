begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2016 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
end_comment

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
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Optional
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|stream
operator|.
name|Collectors
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
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
name|gui
operator|.
name|undo
operator|.
name|NamedCompound
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
name|groups
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
name|logic
operator|.
name|groups
operator|.
name|EntriesGroupChange
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
name|groups
operator|.
name|GroupTreeNode
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
name|BibEntry
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
DECL|field|move
specifier|private
specifier|final
name|boolean
name|move
decl_stmt|;
DECL|field|node
specifier|private
name|GroupTreeNodeViewModel
name|node
decl_stmt|;
DECL|field|panel
specifier|private
name|BasePanel
name|panel
decl_stmt|;
comment|/**      * @param move If true, remove entries from all other groups.      */
DECL|method|AddToGroupAction (GroupTreeNodeViewModel node, boolean move, BasePanel panel)
specifier|public
name|AddToGroupAction
parameter_list|(
name|GroupTreeNodeViewModel
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
name|getNode
argument_list|()
operator|.
name|getGroup
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|this
operator|.
name|node
operator|=
name|node
expr_stmt|;
name|this
operator|.
name|move
operator|=
name|move
expr_stmt|;
name|this
operator|.
name|panel
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
name|Localization
operator|.
name|lang
argument_list|(
literal|"Assign entry selection exclusively to this group"
argument_list|)
else|:
name|Localization
operator|.
name|lang
argument_list|(
literal|"Add entry selection to this group"
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|move
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
name|this
operator|.
name|panel
operator|=
name|panel
expr_stmt|;
block|}
DECL|method|setNode (GroupTreeNodeViewModel node)
specifier|public
name|void
name|setNode
parameter_list|(
name|GroupTreeNodeViewModel
name|node
parameter_list|)
block|{
name|this
operator|.
name|node
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
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
init|=
name|panel
operator|.
name|getSelectedEntries
argument_list|()
decl_stmt|;
comment|// if an editor is showing, its fields must be updated after the assignment,
comment|// and before that, the current edit has to be stored:
name|panel
operator|.
name|storeCurrentEdit
argument_list|()
expr_stmt|;
name|NamedCompound
name|undoAll
init|=
operator|new
name|NamedCompound
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"change assignment of entries"
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|move
condition|)
block|{
name|moveToGroup
argument_list|(
name|entries
argument_list|,
name|undoAll
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|addToGroup
argument_list|(
name|entries
argument_list|,
name|undoAll
argument_list|)
expr_stmt|;
block|}
name|undoAll
operator|.
name|end
argument_list|()
expr_stmt|;
name|panel
operator|.
name|getUndoManager
argument_list|()
operator|.
name|addEdit
argument_list|(
name|undoAll
argument_list|)
expr_stmt|;
name|panel
operator|.
name|markBaseChanged
argument_list|()
expr_stmt|;
name|panel
operator|.
name|updateEntryEditorIfShowing
argument_list|()
expr_stmt|;
name|panel
operator|.
name|getGroupSelector
argument_list|()
operator|.
name|valueChanged
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
DECL|method|moveToGroup (List<BibEntry> entries, NamedCompound undoAll)
specifier|public
name|void
name|moveToGroup
parameter_list|(
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
parameter_list|,
name|NamedCompound
name|undoAll
parameter_list|)
block|{
name|List
argument_list|<
name|GroupTreeNode
argument_list|>
name|groupsContainingEntries
init|=
name|node
operator|.
name|getNode
argument_list|()
operator|.
name|getRoot
argument_list|()
operator|.
name|getContainingGroups
argument_list|(
name|entries
argument_list|,
literal|false
argument_list|)
operator|.
name|stream
argument_list|()
operator|.
name|filter
argument_list|(
name|node
lambda|->
name|node
operator|.
name|getGroup
argument_list|()
operator|.
name|supportsRemove
argument_list|()
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|AbstractGroup
argument_list|>
name|affectedGroups
init|=
name|groupsContainingEntries
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|GroupTreeNode
operator|::
name|getGroup
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
argument_list|)
decl_stmt|;
name|affectedGroups
operator|.
name|add
argument_list|(
name|node
operator|.
name|getNode
argument_list|()
operator|.
name|getGroup
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|WarnAssignmentSideEffects
operator|.
name|warnAssignmentSideEffects
argument_list|(
name|affectedGroups
argument_list|,
name|panel
operator|.
name|frame
argument_list|()
argument_list|)
condition|)
block|{
return|return;
comment|// user aborted operation
block|}
comment|// first remove
for|for
control|(
name|GroupTreeNode
name|group
range|:
name|groupsContainingEntries
control|)
block|{
name|Optional
argument_list|<
name|EntriesGroupChange
argument_list|>
name|undoRemove
init|=
name|group
operator|.
name|getGroup
argument_list|()
operator|.
name|remove
argument_list|(
name|entries
argument_list|)
decl_stmt|;
if|if
condition|(
name|undoRemove
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|undoAll
operator|.
name|addEdit
argument_list|(
name|UndoableChangeEntriesOfGroup
operator|.
name|getUndoableEdit
argument_list|(
name|node
argument_list|,
name|undoRemove
operator|.
name|get
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
comment|// then add
name|Optional
argument_list|<
name|EntriesGroupChange
argument_list|>
name|undoAdd
init|=
name|node
operator|.
name|addEntriesToGroup
argument_list|(
name|entries
argument_list|)
decl_stmt|;
if|if
condition|(
name|undoAdd
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|undoAll
operator|.
name|addEdit
argument_list|(
name|UndoableChangeEntriesOfGroup
operator|.
name|getUndoableEdit
argument_list|(
name|node
argument_list|,
name|undoAdd
operator|.
name|get
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|addToGroup (List<BibEntry> entries, NamedCompound undo)
specifier|public
name|void
name|addToGroup
parameter_list|(
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
parameter_list|,
name|NamedCompound
name|undo
parameter_list|)
block|{
if|if
condition|(
operator|!
name|WarnAssignmentSideEffects
operator|.
name|warnAssignmentSideEffects
argument_list|(
name|node
operator|.
name|getNode
argument_list|()
operator|.
name|getGroup
argument_list|()
argument_list|,
name|panel
operator|.
name|frame
argument_list|()
argument_list|)
condition|)
block|{
return|return;
comment|// user aborted operation
block|}
name|Optional
argument_list|<
name|EntriesGroupChange
argument_list|>
name|undoAdd
init|=
name|node
operator|.
name|addEntriesToGroup
argument_list|(
name|entries
argument_list|)
decl_stmt|;
if|if
condition|(
name|undoAdd
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|undo
operator|.
name|addEdit
argument_list|(
name|UndoableChangeEntriesOfGroup
operator|.
name|getUndoableEdit
argument_list|(
name|node
argument_list|,
name|undoAdd
operator|.
name|get
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

