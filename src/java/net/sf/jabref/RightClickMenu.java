begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|ActionEvent
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
name|ActionListener
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
name|PopupMenuEvent
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
name|PopupMenuListener
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
name|specialfields
operator|.
name|Priority
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
name|specialfields
operator|.
name|Quality
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
name|specialfields
operator|.
name|Rank
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
name|specialfields
operator|.
name|Relevance
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
name|specialfields
operator|.
name|SpecialField
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
name|specialfields
operator|.
name|SpecialFieldValue
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
name|specialfields
operator|.
name|SpecialFieldsUtils
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
name|Globals
operator|.
name|lang
argument_list|(
literal|"Assign exclusively to group"
argument_list|)
argument_list|)
decl_stmt|,
comment|// JZTODO lyrics
DECL|field|rankingMenu
name|rankingMenu
init|=
operator|new
name|JMenu
argument_list|()
decl_stmt|,
DECL|field|priorityMenu
name|priorityMenu
init|=
operator|new
name|JMenu
argument_list|()
decl_stmt|,
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
name|JCheckBoxMenuItem
DECL|field|floatMarked
name|floatMarked
init|=
operator|new
name|JCheckBoxMenuItem
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Float marked entries"
argument_list|)
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"floatMarkedEntries"
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
name|mainTable
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
name|mainTable
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
name|mainTable
operator|.
name|getSelected
argument_list|()
operator|.
name|get
argument_list|(
literal|0
argument_list|)
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
name|GUIGlobals
operator|.
name|getImage
argument_list|(
literal|"copy"
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
name|GUIGlobals
operator|.
name|getImage
argument_list|(
literal|"paste"
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
name|GUIGlobals
operator|.
name|getImage
argument_list|(
literal|"cut"
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
name|GUIGlobals
operator|.
name|getImage
argument_list|(
literal|"delete"
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
comment|/*SwingUtilities.invokeLater(new Runnable () {              public void run() {*/
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
comment|/*}                }); */
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
name|add
argument_list|(
operator|new
name|AbstractAction
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Send as email"
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
literal|"sendAsEmail"
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
name|JMenu
name|markSpecific
init|=
name|JabRefFrame
operator|.
name|subMenu
argument_list|(
literal|"Mark specific color"
argument_list|)
decl_stmt|;
name|JabRefFrame
name|frame
init|=
name|panel
operator|.
name|frame
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
name|Util
operator|.
name|MAX_MARKING_LEVEL
condition|;
name|i
operator|++
control|)
name|markSpecific
operator|.
name|add
argument_list|(
operator|new
name|MarkEntriesAction
argument_list|(
name|frame
argument_list|,
name|i
argument_list|)
operator|.
name|getMenuItem
argument_list|()
argument_list|)
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
name|GUIGlobals
operator|.
name|getImage
argument_list|(
literal|"markEntries"
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
name|markSpecific
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
name|GUIGlobals
operator|.
name|getImage
argument_list|(
literal|"unmarkEntries"
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
name|BibtexFields
operator|.
name|MARKED
argument_list|)
operator|==
literal|null
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
literal|"Mark entry"
argument_list|)
argument_list|,
name|GUIGlobals
operator|.
name|getImage
argument_list|(
literal|"markEntries"
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
name|markSpecific
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|add
argument_list|(
name|markSpecific
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
literal|"Unmark entry"
argument_list|)
argument_list|,
name|GUIGlobals
operator|.
name|getImage
argument_list|(
literal|"unmarkEntries"
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
block|}
name|addSeparator
argument_list|()
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
name|SpecialFieldsUtils
operator|.
name|PREF_SPECIALFIELDSENABLED
argument_list|)
condition|)
block|{
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|SpecialFieldsUtils
operator|.
name|PREF_SHOWCOLUMN_RANKING
argument_list|)
condition|)
block|{
name|populateSpecialFieldMenu
argument_list|(
name|this
operator|.
name|rankingMenu
argument_list|,
name|Rank
operator|.
name|getInstance
argument_list|()
argument_list|,
name|panel
operator|.
name|frame
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|this
operator|.
name|rankingMenu
argument_list|)
expr_stmt|;
block|}
comment|// TODO: multiple handling for relevance and quality-assurance
comment|// if multiple values are selected ("if (multiple)"), two options (set / clear) should be offered
comment|// if one value is selected either set or clear should be offered
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|SpecialFieldsUtils
operator|.
name|PREF_SHOWCOLUMN_RELEVANCE
argument_list|)
condition|)
block|{
name|add
argument_list|(
name|Relevance
operator|.
name|getInstance
argument_list|()
operator|.
name|getValues
argument_list|()
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|getMenuAction
argument_list|(
name|panel
operator|.
name|frame
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
name|SpecialFieldsUtils
operator|.
name|PREF_SHOWCOLUMN_QUALITY
argument_list|)
condition|)
block|{
name|add
argument_list|(
name|Quality
operator|.
name|getInstance
argument_list|()
operator|.
name|getValues
argument_list|()
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|getMenuAction
argument_list|(
name|panel
operator|.
name|frame
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
name|SpecialFieldsUtils
operator|.
name|PREF_SHOWCOLUMN_PRIORITY
argument_list|)
condition|)
block|{
name|populateSpecialFieldMenu
argument_list|(
name|this
operator|.
name|priorityMenu
argument_list|,
name|Priority
operator|.
name|getInstance
argument_list|()
argument_list|,
name|panel
operator|.
name|frame
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|this
operator|.
name|priorityMenu
argument_list|)
expr_stmt|;
block|}
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
literal|"Open file"
argument_list|)
argument_list|,
name|GUIGlobals
operator|.
name|getImage
argument_list|(
literal|"openExternalFile"
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
literal|"openExternalFile"
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
literal|"Open PDF or PS"
argument_list|)
argument_list|,
name|GUIGlobals
operator|.
name|getImage
argument_list|(
literal|"openFile"
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
name|GUIGlobals
operator|.
name|getImage
argument_list|(
literal|"www"
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
literal|"Copy"
argument_list|)
operator|+
literal|" \\cite{"
operator|+
name|Globals
operator|.
name|lang
argument_list|(
literal|"BibTeX key"
argument_list|)
operator|+
literal|"}"
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
name|floatMarked
operator|.
name|addActionListener
argument_list|(
operator|new
name|ActionListener
argument_list|()
block|{
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|Globals
operator|.
name|prefs
operator|.
name|putBoolean
argument_list|(
literal|"floatMarkedEntries"
argument_list|,
name|floatMarked
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|panel
operator|.
name|mainTable
operator|.
name|refreshSorting
argument_list|()
expr_stmt|;
comment|// Bad remote access
block|}
block|}
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
name|String
name|key
range|:
name|BibtexEntryType
operator|.
name|ALL_TYPES
operator|.
name|keySet
argument_list|()
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
name|key
argument_list|)
argument_list|,
name|panel
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Remove all types from the menu.       * Then cycle through all available values, and add them.      */
DECL|method|populateSpecialFieldMenu (JMenu menu, SpecialField field, JabRefFrame frame)
specifier|public
specifier|static
name|void
name|populateSpecialFieldMenu
parameter_list|(
name|JMenu
name|menu
parameter_list|,
name|SpecialField
name|field
parameter_list|,
name|JabRefFrame
name|frame
parameter_list|)
block|{
comment|//menu.removeAll();
name|menu
operator|.
name|setText
argument_list|(
name|field
operator|.
name|getMenuString
argument_list|()
argument_list|)
expr_stmt|;
name|menu
operator|.
name|setIcon
argument_list|(
name|field
operator|.
name|getRepresentingIcon
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|SpecialFieldValue
name|val
range|:
name|field
operator|.
name|getValues
argument_list|()
control|)
block|{
name|menu
operator|.
name|add
argument_list|(
name|val
operator|.
name|getMenuAction
argument_list|(
name|frame
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
name|addSeparator
argument_list|()
expr_stmt|;
name|floatMarked
operator|.
name|setSelected
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"floatMarkedEntries"
argument_list|)
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|floatMarked
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
name|getImage
argument_list|(
literal|"groupIncluding"
argument_list|)
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
name|getImage
argument_list|(
literal|"groupRefining"
argument_list|)
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
name|getImage
argument_list|(
literal|"groupRegular"
argument_list|)
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
argument_list|,
name|panel
argument_list|)
else|:
operator|(
name|AbstractAction
operator|)
operator|new
name|RemoveFromGroupAction
argument_list|(
name|node
argument_list|,
name|panel
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
block|}
end_class

end_unit

