begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.collab
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|collab
package|;
end_package

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JComponent
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JLabel
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
name|BibtexDatabase
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
name|AllEntriesGroup
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
name|groups
operator|.
name|UndoableModifySubtree
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
DECL|class|GroupChange
class|class
name|GroupChange
extends|extends
name|Change
block|{
DECL|field|changedGroups
specifier|private
specifier|final
name|GroupTreeNode
name|changedGroups
decl_stmt|;
DECL|field|tmpGroupRoot
specifier|private
specifier|final
name|GroupTreeNode
name|tmpGroupRoot
decl_stmt|;
DECL|method|GroupChange (GroupTreeNode changedGroups, GroupTreeNode tmpGroupRoot)
specifier|public
name|GroupChange
parameter_list|(
name|GroupTreeNode
name|changedGroups
parameter_list|,
name|GroupTreeNode
name|tmpGroupRoot
parameter_list|)
block|{
name|super
argument_list|(
name|changedGroups
operator|!=
literal|null
condition|?
literal|"Modified groups tree"
else|:
literal|"Removed all groups"
argument_list|)
expr_stmt|;
comment|// JZTODO lyrics
name|this
operator|.
name|changedGroups
operator|=
name|changedGroups
expr_stmt|;
name|this
operator|.
name|tmpGroupRoot
operator|=
name|tmpGroupRoot
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|makeChange (BasePanel panel, BibtexDatabase secondary, NamedCompound undoEdit)
specifier|public
name|boolean
name|makeChange
parameter_list|(
name|BasePanel
name|panel
parameter_list|,
name|BibtexDatabase
name|secondary
parameter_list|,
name|NamedCompound
name|undoEdit
parameter_list|)
block|{
specifier|final
name|GroupTreeNode
name|root
init|=
name|panel
operator|.
name|metaData
argument_list|()
operator|.
name|getGroups
argument_list|()
decl_stmt|;
specifier|final
name|UndoableModifySubtree
name|undo
init|=
operator|new
name|UndoableModifySubtree
argument_list|(
name|panel
operator|.
name|getGroupSelector
argument_list|()
argument_list|,
name|panel
operator|.
name|metaData
argument_list|()
operator|.
name|getGroups
argument_list|()
argument_list|,
name|root
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Modified groups"
argument_list|)
argument_list|)
decl_stmt|;
name|root
operator|.
name|removeAllChildren
argument_list|()
expr_stmt|;
if|if
condition|(
name|changedGroups
operator|==
literal|null
condition|)
block|{
comment|// I think setting root to null is not possible
name|root
operator|.
name|setGroup
argument_list|(
operator|new
name|AllEntriesGroup
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// change root group, even though it'll be AllEntries anyway
name|root
operator|.
name|setGroup
argument_list|(
name|changedGroups
operator|.
name|getGroup
argument_list|()
argument_list|)
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
name|changedGroups
operator|.
name|getChildCount
argument_list|()
condition|;
operator|++
name|i
control|)
block|{
name|root
operator|.
name|add
argument_list|(
operator|(
operator|(
name|GroupTreeNode
operator|)
name|changedGroups
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
block|}
comment|// the group tree is now appled to a different BibtexDatabase than it was created
comment|// for, which affects groups such as ExplicitGroup (which links to BibtexEntry objects).
comment|// We must traverse the tree and refresh all groups:
name|root
operator|.
name|refreshGroupsForNewDatabase
argument_list|(
name|panel
operator|.
name|database
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|panel
operator|.
name|getGroupSelector
argument_list|()
operator|.
name|getGroupTreeRoot
argument_list|()
operator|==
name|root
condition|)
block|{
name|panel
operator|.
name|getGroupSelector
argument_list|()
operator|.
name|revalidateGroups
argument_list|()
expr_stmt|;
block|}
name|undoEdit
operator|.
name|addEdit
argument_list|(
name|undo
argument_list|)
expr_stmt|;
comment|// Update tmp database:
name|GroupTreeNode
name|copied
init|=
name|changedGroups
operator|.
name|deepCopy
argument_list|()
decl_stmt|;
name|tmpGroupRoot
operator|.
name|removeAllChildren
argument_list|()
expr_stmt|;
name|tmpGroupRoot
operator|.
name|setGroup
argument_list|(
name|copied
operator|.
name|getGroup
argument_list|()
argument_list|)
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
name|copied
operator|.
name|getChildCount
argument_list|()
condition|;
operator|++
name|i
control|)
block|{
name|tmpGroupRoot
operator|.
name|add
argument_list|(
operator|(
operator|(
name|GroupTreeNode
operator|)
name|copied
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
block|}
name|tmpGroupRoot
operator|.
name|refreshGroupsForNewDatabase
argument_list|(
name|secondary
argument_list|)
expr_stmt|;
return|return
literal|true
return|;
block|}
annotation|@
name|Override
DECL|method|description ()
name|JComponent
name|description
parameter_list|()
block|{
return|return
operator|new
name|JLabel
argument_list|(
literal|"<html>"
operator|+
name|name
operator|+
literal|'.'
operator|+
operator|(
name|changedGroups
operator|!=
literal|null
condition|?
literal|' '
operator|+
literal|"Accepting the change replaces the complete "
operator|+
literal|"groups tree with the externally modified groups tree."
else|:
literal|""
operator|)
operator|+
literal|"</html>"
argument_list|)
return|;
comment|// JZTODO lyrics
block|}
block|}
end_class

end_unit

