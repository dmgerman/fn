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
name|java
operator|.
name|awt
operator|.
name|Font
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
name|*
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
name|*
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
DECL|field|groupAddMenu
name|JMenu
name|groupAddMenu
init|=
operator|new
name|JMenu
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Add to group"
argument_list|)
argument_list|)
decl_stmt|,
DECL|field|groupRemoveMenu
name|groupRemoveMenu
init|=
operator|new
name|JMenu
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Remove from group"
argument_list|)
argument_list|)
decl_stmt|,
DECL|field|groupMoveMenu
name|groupMoveMenu
init|=
operator|new
name|JMenu
argument_list|(
literal|"Assign exclusively to group"
argument_list|)
decl_stmt|,
comment|// JZTODO lyrics
DECL|field|typeMenu
name|typeMenu
init|=
operator|new
name|JMenu
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Change entry type"
argument_list|)
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
comment|// Are multiple entries selected?
name|boolean
name|multiple
init|=
operator|(
name|panel
operator|.
name|entryTable
operator|.
name|getSelectedRowCount
argument_list|()
operator|>
literal|1
operator|)
decl_stmt|;
comment|// If only one entry is selected, get a reference to it for adapting the menu.
name|BibtexEntry
name|be
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|panel
operator|.
name|entryTable
operator|.
name|getSelectedRowCount
argument_list|()
operator|==
literal|1
condition|)
name|be
operator|=
name|panel
operator|.
name|entryTable
operator|.
name|getSelectedEntries
argument_list|()
index|[
literal|0
index|]
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
name|Globals
operator|.
name|lang
argument_list|(
literal|"Copy"
argument_list|)
argument_list|,
operator|new
name|ImageIcon
argument_list|(
name|GUIGlobals
operator|.
name|copyIconFile
argument_list|)
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
try|try
block|{
name|panel
operator|.
name|runCommand
argument_list|(
literal|"copy"
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Throwable
name|ex
parameter_list|)
block|{}
block|}
block|}
argument_list|)
expr_stmt|;
name|add
argument_list|(
operator|new
name|AbstractAction
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Paste"
argument_list|)
argument_list|,
operator|new
name|ImageIcon
argument_list|(
name|GUIGlobals
operator|.
name|pasteIconFile
argument_list|)
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
try|try
block|{
name|panel
operator|.
name|runCommand
argument_list|(
literal|"paste"
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Throwable
name|ex
parameter_list|)
block|{}
block|}
block|}
argument_list|)
expr_stmt|;
name|add
argument_list|(
operator|new
name|AbstractAction
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Cut"
argument_list|)
argument_list|,
operator|new
name|ImageIcon
argument_list|(
name|GUIGlobals
operator|.
name|cutIconFile
argument_list|)
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
try|try
block|{
name|panel
operator|.
name|runCommand
argument_list|(
literal|"cut"
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Throwable
name|ex
parameter_list|)
block|{}
block|}
block|}
argument_list|)
expr_stmt|;
name|add
argument_list|(
operator|new
name|AbstractAction
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Delete"
argument_list|)
argument_list|,
operator|new
name|ImageIcon
argument_list|(
name|GUIGlobals
operator|.
name|removeIconFile
argument_list|)
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
try|try
block|{
name|panel
operator|.
name|runCommand
argument_list|(
literal|"delete"
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Throwable
name|ex
parameter_list|)
block|{}
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
name|Globals
operator|.
name|lang
argument_list|(
literal|"Export to clipboard"
argument_list|)
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
try|try
block|{
name|panel
operator|.
name|runCommand
argument_list|(
literal|"exportToClipboard"
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Throwable
name|ex
parameter_list|)
block|{}
block|}
block|}
argument_list|)
expr_stmt|;
name|addSeparator
argument_list|()
expr_stmt|;
if|if
condition|(
name|multiple
condition|)
block|{
name|add
argument_list|(
operator|new
name|AbstractAction
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Mark entries"
argument_list|)
argument_list|,
operator|new
name|ImageIcon
argument_list|(
name|GUIGlobals
operator|.
name|markIcon
argument_list|)
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
try|try
block|{
name|panel
operator|.
name|runCommand
argument_list|(
literal|"markEntries"
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Throwable
name|ex
parameter_list|)
block|{}
block|}
block|}
argument_list|)
expr_stmt|;
name|add
argument_list|(
operator|new
name|AbstractAction
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Unmark entries"
argument_list|)
argument_list|,
operator|new
name|ImageIcon
argument_list|(
name|GUIGlobals
operator|.
name|unmarkIcon
argument_list|)
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
try|try
block|{
name|panel
operator|.
name|runCommand
argument_list|(
literal|"unmarkEntries"
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Throwable
name|ex
parameter_list|)
block|{}
block|}
block|}
argument_list|)
expr_stmt|;
name|addSeparator
argument_list|()
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|be
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|be
operator|.
name|getField
argument_list|(
name|Globals
operator|.
name|MARKED
argument_list|)
operator|==
literal|null
condition|)
name|add
argument_list|(
operator|new
name|AbstractAction
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Mark entry"
argument_list|)
argument_list|,
operator|new
name|ImageIcon
argument_list|(
name|GUIGlobals
operator|.
name|markIcon
argument_list|)
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
try|try
block|{
name|panel
operator|.
name|runCommand
argument_list|(
literal|"markEntries"
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Throwable
name|ex
parameter_list|)
block|{}
block|}
block|}
argument_list|)
expr_stmt|;
else|else
name|add
argument_list|(
operator|new
name|AbstractAction
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Unmark entry"
argument_list|)
argument_list|,
operator|new
name|ImageIcon
argument_list|(
name|GUIGlobals
operator|.
name|unmarkIcon
argument_list|)
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
try|try
block|{
name|panel
operator|.
name|runCommand
argument_list|(
literal|"unmarkEntries"
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Throwable
name|ex
parameter_list|)
block|{}
block|}
block|}
argument_list|)
expr_stmt|;
name|addSeparator
argument_list|()
expr_stmt|;
block|}
name|add
argument_list|(
operator|new
name|AbstractAction
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Open PDF or PS"
argument_list|)
argument_list|,
operator|new
name|ImageIcon
argument_list|(
name|GUIGlobals
operator|.
name|pdfIcon
argument_list|)
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
try|try
block|{
name|panel
operator|.
name|runCommand
argument_list|(
literal|"openFile"
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Throwable
name|ex
parameter_list|)
block|{}
block|}
block|}
argument_list|)
expr_stmt|;
name|add
argument_list|(
operator|new
name|AbstractAction
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Open URL or DOI"
argument_list|)
argument_list|,
operator|new
name|ImageIcon
argument_list|(
name|GUIGlobals
operator|.
name|wwwIcon
argument_list|)
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
try|try
block|{
name|panel
operator|.
name|runCommand
argument_list|(
literal|"openUrl"
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Throwable
name|ex
parameter_list|)
block|{}
block|}
block|}
argument_list|)
expr_stmt|;
name|add
argument_list|(
operator|new
name|AbstractAction
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Copy BibTeX key"
argument_list|)
argument_list|,
operator|new
name|ImageIcon
argument_list|(
name|GUIGlobals
operator|.
name|copyKeyIconFile
argument_list|)
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
try|try
block|{
name|panel
operator|.
name|runCommand
argument_list|(
literal|"copyKey"
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Throwable
name|ex
parameter_list|)
block|{}
block|}
block|}
argument_list|)
expr_stmt|;
name|add
argument_list|(
operator|new
name|AbstractAction
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Copy \\cite{BibTeX key}"
argument_list|)
argument_list|,
operator|new
name|ImageIcon
argument_list|(
name|GUIGlobals
operator|.
name|copyKeyIconFile
argument_list|)
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
try|try
block|{
name|panel
operator|.
name|runCommand
argument_list|(
literal|"copyCiteKey"
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Throwable
name|ex
parameter_list|)
block|{}
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
name|add
argument_list|(
operator|new
name|AbstractAction
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Plain text import"
argument_list|)
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
try|try
block|{
name|panel
operator|.
name|runCommand
argument_list|(
literal|"importPlainText"
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Throwable
name|ex
parameter_list|)
block|{}
block|}
block|}
argument_list|)
expr_stmt|;
name|addSeparator
argument_list|()
expr_stmt|;
comment|// for "add/move/remove to/from group" entries (appended here)
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
argument_list|,
name|panel
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
name|BibtexEntry
index|[]
name|bes
init|=
name|panel
operator|.
name|entryTable
operator|.
name|getSelectedEntries
argument_list|()
decl_stmt|;
name|panel
operator|.
name|storeCurrentEdit
argument_list|()
expr_stmt|;
name|GroupTreeNode
name|groups
init|=
name|metaData
operator|.
name|getGroups
argument_list|()
decl_stmt|;
if|if
condition|(
name|groups
operator|==
literal|null
condition|)
block|{
name|groupAddMenu
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|groupMoveMenu
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
name|groupAddMenu
operator|.
name|setEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|groupMoveMenu
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
name|groupAddMenu
operator|.
name|removeAll
argument_list|()
expr_stmt|;
name|groupMoveMenu
operator|.
name|removeAll
argument_list|()
expr_stmt|;
name|groupRemoveMenu
operator|.
name|removeAll
argument_list|()
expr_stmt|;
if|if
condition|(
name|bes
operator|==
literal|null
condition|)
return|return;
name|add
argument_list|(
name|groupAddMenu
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|groupMoveMenu
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|groupRemoveMenu
argument_list|)
expr_stmt|;
name|groupAddMenu
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|groupMoveMenu
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
name|insertNodes
argument_list|(
name|groupAddMenu
argument_list|,
name|metaData
operator|.
name|getGroups
argument_list|()
argument_list|,
name|bes
argument_list|,
literal|true
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|insertNodes
argument_list|(
name|groupMoveMenu
argument_list|,
name|metaData
operator|.
name|getGroups
argument_list|()
argument_list|,
name|bes
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|insertNodes
argument_list|(
name|groupRemoveMenu
argument_list|,
name|metaData
operator|.
name|getGroups
argument_list|()
argument_list|,
name|bes
argument_list|,
literal|false
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
comment|/**      * @param move For add: if true, remove from previous groups      */
DECL|method|insertNodes (JMenu menu, GroupTreeNode node, BibtexEntry[] selection, boolean add, boolean move)
specifier|public
name|void
name|insertNodes
parameter_list|(
name|JMenu
name|menu
parameter_list|,
name|GroupTreeNode
name|node
parameter_list|,
name|BibtexEntry
index|[]
name|selection
parameter_list|,
name|boolean
name|add
parameter_list|,
name|boolean
name|move
parameter_list|)
block|{
specifier|final
name|AbstractAction
name|action
init|=
name|getAction
argument_list|(
name|node
argument_list|,
name|selection
argument_list|,
name|add
argument_list|,
name|move
argument_list|)
decl_stmt|;
if|if
condition|(
name|node
operator|.
name|getChildCount
argument_list|()
operator|==
literal|0
condition|)
block|{
name|JMenuItem
name|menuItem
init|=
operator|new
name|JMenuItem
argument_list|(
name|action
argument_list|)
decl_stmt|;
name|setGroupFontAndIcon
argument_list|(
name|menuItem
argument_list|,
name|node
operator|.
name|getGroup
argument_list|()
argument_list|)
expr_stmt|;
name|menu
operator|.
name|add
argument_list|(
name|menuItem
argument_list|)
expr_stmt|;
if|if
condition|(
name|action
operator|.
name|isEnabled
argument_list|()
condition|)
name|menu
operator|.
name|setEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
return|return;
block|}
name|JMenu
name|submenu
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|node
operator|.
name|getGroup
argument_list|()
operator|instanceof
name|AllEntriesGroup
condition|)
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|node
operator|.
name|getChildCount
argument_list|()
condition|;
operator|++
name|i
control|)
block|{
name|insertNodes
argument_list|(
name|menu
argument_list|,
operator|(
name|GroupTreeNode
operator|)
name|node
operator|.
name|getChildAt
argument_list|(
name|i
argument_list|)
argument_list|,
name|selection
argument_list|,
name|add
argument_list|,
name|move
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|submenu
operator|=
operator|new
name|JMenu
argument_list|(
literal|"["
operator|+
name|node
operator|.
name|getGroup
argument_list|()
operator|.
name|getName
argument_list|()
operator|+
literal|"]"
argument_list|)
expr_stmt|;
name|setGroupFontAndIcon
argument_list|(
name|submenu
argument_list|,
name|node
operator|.
name|getGroup
argument_list|()
argument_list|)
expr_stmt|;
comment|// setEnabled(true) is done above/below if at least one menu
comment|// entry (item or submenu) is enabled
name|submenu
operator|.
name|setEnabled
argument_list|(
name|action
operator|.
name|isEnabled
argument_list|()
argument_list|)
expr_stmt|;
name|JMenuItem
name|menuItem
init|=
operator|new
name|JMenuItem
argument_list|(
name|action
argument_list|)
decl_stmt|;
name|setGroupFontAndIcon
argument_list|(
name|menuItem
argument_list|,
name|node
operator|.
name|getGroup
argument_list|()
argument_list|)
expr_stmt|;
name|submenu
operator|.
name|add
argument_list|(
name|menuItem
argument_list|)
expr_stmt|;
name|submenu
operator|.
name|add
argument_list|(
operator|new
name|Separator
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
name|node
operator|.
name|getChildCount
argument_list|()
condition|;
operator|++
name|i
control|)
name|insertNodes
argument_list|(
name|submenu
argument_list|,
operator|(
name|GroupTreeNode
operator|)
name|node
operator|.
name|getChildAt
argument_list|(
name|i
argument_list|)
argument_list|,
name|selection
argument_list|,
name|add
argument_list|,
name|move
argument_list|)
expr_stmt|;
name|menu
operator|.
name|add
argument_list|(
name|submenu
argument_list|)
expr_stmt|;
if|if
condition|(
name|submenu
operator|.
name|isEnabled
argument_list|()
condition|)
name|menu
operator|.
name|setEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
comment|/** Sets the font and icon to be used, depending on the group */
DECL|method|setGroupFontAndIcon (JMenuItem menuItem, AbstractGroup group)
specifier|private
name|void
name|setGroupFontAndIcon
parameter_list|(
name|JMenuItem
name|menuItem
parameter_list|,
name|AbstractGroup
name|group
parameter_list|)
block|{
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"groupShowDynamic"
argument_list|)
condition|)
block|{
name|menuItem
operator|.
name|setFont
argument_list|(
name|menuItem
operator|.
name|getFont
argument_list|()
operator|.
name|deriveFont
argument_list|(
name|group
operator|.
name|isDynamic
argument_list|()
condition|?
name|Font
operator|.
name|ITALIC
else|:
name|Font
operator|.
name|PLAIN
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"groupShowIcons"
argument_list|)
condition|)
block|{
switch|switch
condition|(
name|group
operator|.
name|getHierarchicalContext
argument_list|()
condition|)
block|{
case|case
name|AbstractGroup
operator|.
name|INCLUDING
case|:
name|menuItem
operator|.
name|setIcon
argument_list|(
name|GUIGlobals
operator|.
name|groupIncludingIcon
argument_list|)
expr_stmt|;
break|break;
case|case
name|AbstractGroup
operator|.
name|REFINING
case|:
name|menuItem
operator|.
name|setIcon
argument_list|(
name|GUIGlobals
operator|.
name|groupRefiningIcon
argument_list|)
expr_stmt|;
break|break;
default|default:
name|menuItem
operator|.
name|setIcon
argument_list|(
name|GUIGlobals
operator|.
name|groupRegularIcon
argument_list|)
expr_stmt|;
break|break;
block|}
block|}
block|}
comment|/**      * @param move For add: if true, remove from all previous groups      */
DECL|method|getAction (GroupTreeNode node, BibtexEntry[] selection, boolean add, boolean move)
specifier|private
name|AbstractAction
name|getAction
parameter_list|(
name|GroupTreeNode
name|node
parameter_list|,
name|BibtexEntry
index|[]
name|selection
parameter_list|,
name|boolean
name|add
parameter_list|,
name|boolean
name|move
parameter_list|)
block|{
name|AbstractAction
name|action
init|=
name|add
condition|?
operator|(
name|AbstractAction
operator|)
operator|new
name|AddToGroupAction
argument_list|(
name|node
argument_list|,
name|move
argument_list|)
else|:
operator|(
name|AbstractAction
operator|)
operator|new
name|RemoveFromGroupAction
argument_list|(
name|node
argument_list|)
decl_stmt|;
name|AbstractGroup
name|group
init|=
name|node
operator|.
name|getGroup
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|move
condition|)
block|{
name|action
operator|.
name|setEnabled
argument_list|(
name|add
condition|?
name|group
operator|.
name|supportsAdd
argument_list|()
operator|&&
operator|!
name|group
operator|.
name|containsAll
argument_list|(
name|selection
argument_list|)
else|:
name|group
operator|.
name|supportsRemove
argument_list|()
operator|&&
name|group
operator|.
name|containsAny
argument_list|(
name|selection
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|action
operator|.
name|setEnabled
argument_list|(
name|group
operator|.
name|supportsAdd
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|action
return|;
block|}
DECL|method|popupMenuWillBecomeInvisible (PopupMenuEvent e)
specifier|public
name|void
name|popupMenuWillBecomeInvisible
parameter_list|(
name|PopupMenuEvent
name|e
parameter_list|)
block|{
name|remove
argument_list|(
name|groupAddMenu
argument_list|)
expr_stmt|;
name|remove
argument_list|(
name|groupMoveMenu
argument_list|)
expr_stmt|;
name|remove
argument_list|(
name|groupRemoveMenu
argument_list|)
expr_stmt|;
block|}
DECL|method|popupMenuCanceled (PopupMenuEvent e)
specifier|public
name|void
name|popupMenuCanceled
parameter_list|(
name|PopupMenuEvent
name|e
parameter_list|)
block|{
comment|// nothing to do
block|}
DECL|class|AddToGroupAction
class|class
name|AddToGroupAction
extends|extends
name|AbstractAction
block|{
DECL|field|m_node
specifier|final
name|GroupTreeNode
name|m_node
decl_stmt|;
DECL|field|m_move
specifier|final
name|boolean
name|m_move
decl_stmt|;
comment|/**          * @param move If true, remove node from all other groups.          */
DECL|method|AddToGroupAction (GroupTreeNode node, boolean move)
specifier|public
name|AddToGroupAction
parameter_list|(
name|GroupTreeNode
name|node
parameter_list|,
name|boolean
name|move
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
specifier|final
name|BibtexEntry
index|[]
name|entries
init|=
name|panel
operator|.
name|getSelectedEntries
argument_list|()
decl_stmt|;
specifier|final
name|Vector
name|removeGroupsNodes
init|=
operator|new
name|Vector
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
operator|(
name|GroupTreeNode
operator|)
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
continue|continue;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|entries
operator|.
name|length
condition|;
operator|++
name|i
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
name|entries
index|[
name|i
index|]
argument_list|)
condition|)
name|removeGroupsNodes
operator|.
name|add
argument_list|(
name|node
argument_list|)
expr_stmt|;
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
name|groups
index|[
name|i
index|]
operator|=
operator|(
operator|(
name|GroupTreeNode
operator|)
name|removeGroupsNodes
operator|.
name|elementAt
argument_list|(
name|i
argument_list|)
operator|)
operator|.
name|getGroup
argument_list|()
expr_stmt|;
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
name|panel
operator|.
name|getDatabase
argument_list|()
argument_list|,
name|panel
operator|.
name|frame
argument_list|)
condition|)
return|return;
comment|// user aborted operation
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
name|panel
operator|.
name|getDatabase
argument_list|()
operator|,
name|panel
operator|.
name|frame
block|)
block|)
function|return;
comment|// user aborted operation
block|}
comment|// if an editor is showing, its fields must be updated
comment|// after the assignment, and before that, the current
comment|// edit has to be stored:
name|panel
operator|.
name|storeCurrentEdit
parameter_list|()
constructor_decl|;
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
operator|(
name|GroupTreeNode
operator|)
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
name|undoAll
operator|.
name|addEdit
argument_list|(
name|undoAdd
argument_list|)
expr_stmt|;
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
return|return;
comment|// no changed made
name|undoAll
operator|.
name|addEdit
argument_list|(
name|undoAdd
argument_list|)
expr_stmt|;
block|}
name|undoAll
operator|.
name|end
parameter_list|()
constructor_decl|;
name|panel
operator|.
name|undoManager
operator|.
name|addEdit
parameter_list|(
name|undoAll
parameter_list|)
constructor_decl|;
name|panel
operator|.
name|refreshTable
parameter_list|()
constructor_decl|;
name|panel
operator|.
name|markBaseChanged
parameter_list|()
constructor_decl|;
name|panel
operator|.
name|updateEntryEditorIfShowing
parameter_list|()
constructor_decl|;
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
name|panel
operator|.
name|updateViewToSelected
parameter_list|()
constructor_decl|;
block|}
end_class

begin_expr_stmt
unit|}          class
DECL|class|RemoveFromGroupAction
name|RemoveFromGroupAction
expr|extends
name|AbstractAction
block|{
DECL|field|m_node
name|GroupTreeNode
name|m_node
block|;
DECL|method|RemoveFromGroupAction (GroupTreeNode node)
specifier|public
name|RemoveFromGroupAction
argument_list|(
name|GroupTreeNode
name|node
argument_list|)
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
block|;
name|m_node
operator|=
name|node
block|;         }
DECL|method|actionPerformed (ActionEvent evt)
specifier|public
name|void
name|actionPerformed
argument_list|(
name|ActionEvent
name|evt
argument_list|)
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
block|,
name|panel
operator|.
name|getSelectedEntries
argument_list|()
block|,
name|panel
operator|.
name|getDatabase
argument_list|()
block|,
name|panel
operator|.
name|frame
block|))
end_expr_stmt

begin_return
return|return;
end_return

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
name|panel
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
name|panel
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
name|panel
operator|.
name|refreshTable
argument_list|()
expr_stmt|;
end_expr_stmt

begin_expr_stmt
name|panel
operator|.
name|markBaseChanged
argument_list|()
expr_stmt|;
end_expr_stmt

begin_expr_stmt
name|panel
operator|.
name|updateEntryEditorIfShowing
argument_list|()
expr_stmt|;
end_expr_stmt

begin_expr_stmt
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
end_expr_stmt

begin_expr_stmt
name|panel
operator|.
name|updateViewToSelected
argument_list|()
expr_stmt|;
end_expr_stmt

begin_class
unit|}     }
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
DECL|field|panel
name|BasePanel
name|panel
decl_stmt|;
DECL|method|ChangeTypeAction (BibtexEntryType type, BasePanel bp)
specifier|public
name|ChangeTypeAction
parameter_list|(
name|BibtexEntryType
name|type
parameter_list|,
name|BasePanel
name|bp
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
name|panel
operator|=
name|bp
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
end_class

unit|}
end_unit

