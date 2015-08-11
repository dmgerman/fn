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
name|logic
operator|.
name|l10n
operator|.
name|Localization
import|;
end_import

begin_class
DECL|class|UndoableModifyGroup
class|class
name|UndoableModifyGroup
extends|extends
name|AbstractUndoableEdit
block|{
DECL|field|m_groupSelector
specifier|private
specifier|final
name|GroupSelector
name|m_groupSelector
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
name|int
index|[]
name|m_pathToNode
decl_stmt|;
comment|/**      * @param node      *            The node which still contains the old group.      * @param newGroup      *            The new group to replace the one currently stored in<b>node      *</b>.      */
DECL|method|UndoableModifyGroup (GroupSelector gs, GroupTreeNode groupsRoot, GroupTreeNode node, AbstractGroup newGroup)
specifier|public
name|UndoableModifyGroup
parameter_list|(
name|GroupSelector
name|gs
parameter_list|,
name|GroupTreeNode
name|groupsRoot
parameter_list|,
name|GroupTreeNode
name|node
parameter_list|,
name|AbstractGroup
name|newGroup
parameter_list|)
block|{
name|m_groupSelector
operator|=
name|gs
expr_stmt|;
name|m_oldGroupBackup
operator|=
name|node
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
name|getIndexedPath
argument_list|()
expr_stmt|;
name|m_groupsRootHandle
operator|=
name|groupsRoot
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
literal|"modify group"
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
name|m_groupsRootHandle
operator|.
name|getDescendant
argument_list|(
name|m_pathToNode
argument_list|)
operator|.
name|setGroup
argument_list|(
name|m_oldGroupBackup
operator|.
name|deepCopy
argument_list|()
argument_list|)
expr_stmt|;
name|m_groupSelector
operator|.
name|revalidateGroups
argument_list|()
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
name|setGroup
argument_list|(
name|m_newGroupBackup
operator|.
name|deepCopy
argument_list|()
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

