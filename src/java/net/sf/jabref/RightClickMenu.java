begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* Copyright (C) 2003 Morten O. Alver, Nizar N. Batada  All programs in this directory and subdirectories are published under the GNU General Public License as described below.  This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.  You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA  Further information about the GNU GPL is available at: http://www.gnu.org/copyleft/gpl.ja.html  */
end_comment

begin_package
DECL|package|net.sf.jabref
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
package|;
end_package

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
name|GroupSelector
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
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
name|event
operator|.
name|*
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|*
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
name|java
operator|.
name|util
operator|.
name|Iterator
import|;
end_import

begin_class
DECL|class|RightClickMenu
specifier|public
class|class
name|RightClickMenu
extends|extends
name|JPopupMenu
implements|implements
name|PopupMenuListener
block|{
DECL|field|panel
name|BasePanel
name|panel
decl_stmt|;
DECL|field|metaData
name|MetaData
name|metaData
decl_stmt|;
DECL|field|groupMenu
name|JMenu
name|groupMenu
init|=
operator|new
name|JMenu
argument_list|(
literal|"Add to group"
argument_list|)
decl_stmt|,
DECL|field|groupRemoveMenu
name|groupRemoveMenu
init|=
operator|new
name|JMenu
argument_list|(
literal|"Remove from group"
argument_list|)
decl_stmt|,
DECL|field|typeMenu
name|typeMenu
init|=
operator|new
name|JMenu
argument_list|(
literal|"Change entry type"
argument_list|)
decl_stmt|;
DECL|method|RightClickMenu (BasePanel panel_, MetaData metaData_)
specifier|public
name|RightClickMenu
parameter_list|(
name|BasePanel
name|panel_
parameter_list|,
name|MetaData
name|metaData_
parameter_list|)
block|{
name|panel
operator|=
name|panel_
expr_stmt|;
name|metaData
operator|=
name|metaData_
expr_stmt|;
name|addPopupMenuListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|add
argument_list|(
operator|new
name|AbstractAction
argument_list|(
literal|"Copy"
argument_list|)
block|{
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|panel
operator|.
name|runCommand
argument_list|(
literal|"copy"
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|add
argument_list|(
operator|new
name|AbstractAction
argument_list|(
literal|"Paste"
argument_list|)
block|{
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|panel
operator|.
name|runCommand
argument_list|(
literal|"paste"
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|add
argument_list|(
operator|new
name|AbstractAction
argument_list|(
literal|"Cut"
argument_list|)
block|{
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|panel
operator|.
name|runCommand
argument_list|(
literal|"cut"
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|addSeparator
argument_list|()
expr_stmt|;
name|add
argument_list|(
operator|new
name|AbstractAction
argument_list|(
literal|"Copy BibTeX key"
argument_list|)
block|{
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|panel
operator|.
name|runCommand
argument_list|(
literal|"copyKey"
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|add
argument_list|(
operator|new
name|AbstractAction
argument_list|(
literal|"Copy \\cite{BibTeX key}"
argument_list|)
block|{
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|panel
operator|.
name|runCommand
argument_list|(
literal|"copyCiteKey"
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|addSeparator
argument_list|()
expr_stmt|;
name|populateTypeMenu
argument_list|()
expr_stmt|;
name|add
argument_list|(
name|typeMenu
argument_list|)
expr_stmt|;
name|addSeparator
argument_list|()
expr_stmt|;
name|add
argument_list|(
name|groupMenu
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|groupRemoveMenu
argument_list|)
expr_stmt|;
block|}
comment|/**      * Remove all types from the menu. Then cycle through all available      * types, and add them.      */
DECL|method|populateTypeMenu ()
specifier|public
name|void
name|populateTypeMenu
parameter_list|()
block|{
name|typeMenu
operator|.
name|removeAll
argument_list|()
expr_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|BibtexEntryType
operator|.
name|ALL_TYPES
operator|.
name|keySet
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|typeMenu
operator|.
name|add
argument_list|(
operator|new
name|ChangeTypeAction
argument_list|(
name|BibtexEntryType
operator|.
name|getType
argument_list|(
operator|(
name|String
operator|)
name|i
operator|.
name|next
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Set the dynamic contents of "Add to group ..." submenu.      */
DECL|method|popupMenuWillBecomeVisible (PopupMenuEvent e)
specifier|public
name|void
name|popupMenuWillBecomeVisible
parameter_list|(
name|PopupMenuEvent
name|e
parameter_list|)
block|{
name|Vector
name|groups
init|=
name|metaData
operator|.
name|getData
argument_list|(
literal|"groups"
argument_list|)
decl_stmt|;
if|if
condition|(
name|groups
operator|==
literal|null
condition|)
block|{
name|groupMenu
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|groupRemoveMenu
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
return|return;
block|}
name|groupMenu
operator|.
name|setEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|groupRemoveMenu
operator|.
name|setEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|groupMenu
operator|.
name|removeAll
argument_list|()
expr_stmt|;
name|groupRemoveMenu
operator|.
name|removeAll
argument_list|()
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
name|GroupSelector
operator|.
name|OFFSET
init|;
name|i
operator|<
name|groups
operator|.
name|size
argument_list|()
operator|-
literal|2
condition|;
name|i
operator|+=
name|GroupSelector
operator|.
name|DIM
control|)
block|{
name|String
name|name
init|=
operator|(
name|String
operator|)
name|groups
operator|.
name|elementAt
argument_list|(
name|i
operator|+
literal|1
argument_list|)
decl_stmt|,
name|regexp
init|=
operator|(
name|String
operator|)
name|groups
operator|.
name|elementAt
argument_list|(
name|i
operator|+
literal|2
argument_list|)
decl_stmt|,
name|field
init|=
operator|(
name|String
operator|)
name|groups
operator|.
name|elementAt
argument_list|(
name|i
argument_list|)
decl_stmt|;
name|groupMenu
operator|.
name|add
argument_list|(
operator|new
name|AddToGroupAction
argument_list|(
name|name
argument_list|,
name|regexp
argument_list|,
name|field
argument_list|)
argument_list|)
expr_stmt|;
name|groupRemoveMenu
operator|.
name|add
argument_list|(
operator|new
name|RemoveFromGroupAction
argument_list|(
name|name
argument_list|,
name|regexp
argument_list|,
name|field
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|popupMenuWillBecomeInvisible (PopupMenuEvent e)
specifier|public
name|void
name|popupMenuWillBecomeInvisible
parameter_list|(
name|PopupMenuEvent
name|e
parameter_list|)
block|{      }
DECL|method|popupMenuCanceled (PopupMenuEvent e)
specifier|public
name|void
name|popupMenuCanceled
parameter_list|(
name|PopupMenuEvent
name|e
parameter_list|)
block|{      }
DECL|class|AddToGroupAction
class|class
name|AddToGroupAction
extends|extends
name|AbstractAction
block|{
DECL|field|grp
DECL|field|regexp
DECL|field|field
name|String
name|grp
decl_stmt|,
name|regexp
decl_stmt|,
name|field
decl_stmt|;
DECL|method|AddToGroupAction (String grp, String regexp, String field)
specifier|public
name|AddToGroupAction
parameter_list|(
name|String
name|grp
parameter_list|,
name|String
name|regexp
parameter_list|,
name|String
name|field
parameter_list|)
block|{
name|super
argument_list|(
name|grp
argument_list|)
expr_stmt|;
name|this
operator|.
name|grp
operator|=
name|grp
expr_stmt|;
name|this
operator|.
name|regexp
operator|=
name|regexp
expr_stmt|;
name|this
operator|.
name|field
operator|=
name|field
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
name|panel
operator|.
name|addToGroup
argument_list|(
name|grp
argument_list|,
name|regexp
argument_list|,
name|field
argument_list|)
expr_stmt|;
block|}
block|}
DECL|class|RemoveFromGroupAction
class|class
name|RemoveFromGroupAction
extends|extends
name|AbstractAction
block|{
DECL|field|grp
DECL|field|regexp
DECL|field|field
name|String
name|grp
decl_stmt|,
name|regexp
decl_stmt|,
name|field
decl_stmt|;
DECL|method|RemoveFromGroupAction (String grp, String regexp, String field)
specifier|public
name|RemoveFromGroupAction
parameter_list|(
name|String
name|grp
parameter_list|,
name|String
name|regexp
parameter_list|,
name|String
name|field
parameter_list|)
block|{
name|super
argument_list|(
name|grp
argument_list|)
expr_stmt|;
name|this
operator|.
name|grp
operator|=
name|grp
expr_stmt|;
name|this
operator|.
name|regexp
operator|=
name|regexp
expr_stmt|;
name|this
operator|.
name|field
operator|=
name|field
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
name|panel
operator|.
name|removeFromGroup
argument_list|(
name|grp
argument_list|,
name|regexp
argument_list|,
name|field
argument_list|)
expr_stmt|;
block|}
block|}
DECL|class|ChangeTypeAction
class|class
name|ChangeTypeAction
extends|extends
name|AbstractAction
block|{
DECL|field|type
name|BibtexEntryType
name|type
decl_stmt|;
DECL|method|ChangeTypeAction (BibtexEntryType type)
specifier|public
name|ChangeTypeAction
parameter_list|(
name|BibtexEntryType
name|type
parameter_list|)
block|{
name|super
argument_list|(
name|type
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|this
operator|.
name|type
operator|=
name|type
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
name|panel
operator|.
name|changeType
argument_list|(
name|type
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

