begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.gui.menus
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|menus
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
name|bibtex
operator|.
name|InternalBibtexFields
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
name|EntryMarker
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
name|FileListTableModel
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
name|IconTheme
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
name|JabRefFrame
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
name|actions
operator|.
name|Actions
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
name|worker
operator|.
name|MarkEntriesAction
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
name|Printed
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
name|ReadStatus
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

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|Log
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|LogFactory
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
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Log
name|LOGGER
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|RightClickMenu
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|panel
specifier|private
specifier|final
name|BasePanel
name|panel
decl_stmt|;
DECL|field|groupAdd
specifier|private
specifier|final
name|JMenuItem
name|groupAdd
decl_stmt|;
DECL|field|groupRemove
specifier|private
specifier|final
name|JMenuItem
name|groupRemove
decl_stmt|;
DECL|field|groupMoveTo
specifier|private
specifier|final
name|JMenuItem
name|groupMoveTo
decl_stmt|;
DECL|field|floatMarked
specifier|private
specifier|final
name|JCheckBoxMenuItem
name|floatMarked
init|=
operator|new
name|JCheckBoxMenuItem
argument_list|(
name|Localization
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
name|JabRefPreferences
operator|.
name|FLOAT_MARKED_ENTRIES
argument_list|)
argument_list|)
decl_stmt|;
DECL|method|RightClickMenu (JabRefFrame frame, BasePanel panel)
specifier|public
name|RightClickMenu
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
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
name|JMenu
name|typeMenu
init|=
operator|new
name|ChangeEntryTypeMenu
argument_list|()
operator|.
name|getChangeEntryTypeMenu
argument_list|(
name|panel
argument_list|)
decl_stmt|;
comment|// Are multiple entries selected?
name|boolean
name|multiple
init|=
name|panel
operator|.
name|mainTable
operator|.
name|getSelectedRowCount
argument_list|()
operator|>
literal|1
decl_stmt|;
comment|// If only one entry is selected, get a reference to it for adapting the menu.
name|BibEntry
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
block|{
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
block|}
name|addPopupMenuListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|JMenu
name|copySpecialMenu
init|=
operator|new
name|JMenu
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Copy special"
argument_list|)
argument_list|)
decl_stmt|;
name|copySpecialMenu
operator|.
name|add
argument_list|(
operator|new
name|GeneralAction
argument_list|(
name|Actions
operator|.
name|COPY_KEY
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Copy BibTeX key"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|copySpecialMenu
operator|.
name|add
argument_list|(
operator|new
name|GeneralAction
argument_list|(
name|Actions
operator|.
name|COPY_CITE_KEY
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Copy \\cite{BibTeX key}"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|copySpecialMenu
operator|.
name|add
argument_list|(
operator|new
name|GeneralAction
argument_list|(
name|Actions
operator|.
name|COPY_KEY_AND_TITLE
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Copy BibTeX key and title"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|copySpecialMenu
operator|.
name|add
argument_list|(
operator|new
name|GeneralAction
argument_list|(
name|Actions
operator|.
name|EXPORT_TO_CLIPBOARD
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Export to clipboard"
argument_list|)
argument_list|,
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|EXPORT_TO_CLIPBOARD
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|add
argument_list|(
operator|new
name|GeneralAction
argument_list|(
name|Actions
operator|.
name|COPY
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Copy"
argument_list|)
argument_list|,
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|COPY
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|copySpecialMenu
argument_list|)
expr_stmt|;
name|add
argument_list|(
operator|new
name|GeneralAction
argument_list|(
name|Actions
operator|.
name|PASTE
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Paste"
argument_list|)
argument_list|,
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|PASTE
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|add
argument_list|(
operator|new
name|GeneralAction
argument_list|(
name|Actions
operator|.
name|CUT
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Cut"
argument_list|)
argument_list|,
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|CUT
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|add
argument_list|(
operator|new
name|GeneralAction
argument_list|(
name|Actions
operator|.
name|DELETE
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Delete"
argument_list|)
argument_list|,
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|DELETE_ENTRY
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|addSeparator
argument_list|()
expr_stmt|;
name|add
argument_list|(
operator|new
name|GeneralAction
argument_list|(
name|Actions
operator|.
name|SEND_AS_EMAIL
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Send as email"
argument_list|)
argument_list|,
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|EMAIL
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
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
name|Localization
operator|.
name|menuTitle
argument_list|(
literal|"Mark specific color"
argument_list|)
argument_list|)
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
name|EntryMarker
operator|.
name|MAX_MARKING_LEVEL
condition|;
name|i
operator|++
control|)
block|{
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
block|}
if|if
condition|(
name|multiple
condition|)
block|{
name|add
argument_list|(
operator|new
name|GeneralAction
argument_list|(
name|Actions
operator|.
name|MARK_ENTRIES
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Mark entries"
argument_list|)
argument_list|,
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|MARK_ENTRIES
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
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
name|GeneralAction
argument_list|(
name|Actions
operator|.
name|UNMARK_ENTRIES
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Unmark entries"
argument_list|)
argument_list|,
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|UNMARK_ENTRIES
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
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
name|String
name|marked
init|=
name|be
operator|.
name|getField
argument_list|(
name|InternalBibtexFields
operator|.
name|MARKED
argument_list|)
decl_stmt|;
comment|// We have to check for "" too as the marked field may be empty
if|if
condition|(
operator|(
name|marked
operator|==
literal|null
operator|)
operator|||
name|marked
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|add
argument_list|(
operator|new
name|GeneralAction
argument_list|(
name|Actions
operator|.
name|MARK_ENTRIES
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Mark entry"
argument_list|)
argument_list|,
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|MARK_ENTRIES
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
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
name|GeneralAction
argument_list|(
name|Actions
operator|.
name|UNMARK_ENTRIES
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Unmark entry"
argument_list|)
argument_list|,
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|UNMARK_ENTRIES
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
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
name|JMenu
name|rankingMenu
init|=
operator|new
name|JMenu
argument_list|()
decl_stmt|;
name|RightClickMenu
operator|.
name|populateSpecialFieldMenu
argument_list|(
name|rankingMenu
argument_list|,
name|Rank
operator|.
name|getInstance
argument_list|()
argument_list|,
name|frame
argument_list|)
expr_stmt|;
name|add
argument_list|(
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
name|PREF_SHOWCOLUMN_PRINTED
argument_list|)
condition|)
block|{
name|add
argument_list|(
name|Printed
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
name|JMenu
name|priorityMenu
init|=
operator|new
name|JMenu
argument_list|()
decl_stmt|;
name|RightClickMenu
operator|.
name|populateSpecialFieldMenu
argument_list|(
name|priorityMenu
argument_list|,
name|Priority
operator|.
name|getInstance
argument_list|()
argument_list|,
name|frame
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|priorityMenu
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
name|PREF_SHOWCOLUMN_READ
argument_list|)
condition|)
block|{
name|JMenu
name|readStatusMenu
init|=
operator|new
name|JMenu
argument_list|()
decl_stmt|;
name|RightClickMenu
operator|.
name|populateSpecialFieldMenu
argument_list|(
name|readStatusMenu
argument_list|,
name|ReadStatus
operator|.
name|getInstance
argument_list|()
argument_list|,
name|frame
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|readStatusMenu
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
name|GeneralAction
argument_list|(
name|Actions
operator|.
name|OPEN_FOLDER
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Open folder"
argument_list|)
argument_list|)
block|{
block|{
if|if
condition|(
operator|!
name|isFieldSetForSelectedEntry
argument_list|(
name|Globals
operator|.
name|FILE_FIELD
argument_list|)
condition|)
block|{
name|this
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
block|}
block|}
argument_list|)
expr_stmt|;
name|add
argument_list|(
operator|new
name|GeneralAction
argument_list|(
name|Actions
operator|.
name|OPEN_EXTERNAL_FILE
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Open file"
argument_list|)
argument_list|,
name|getFileIconForSelectedEntry
argument_list|()
argument_list|)
block|{
block|{
if|if
condition|(
operator|!
name|isFieldSetForSelectedEntry
argument_list|(
name|Globals
operator|.
name|FILE_FIELD
argument_list|)
condition|)
block|{
name|this
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
block|}
block|}
argument_list|)
expr_stmt|;
name|add
argument_list|(
operator|new
name|GeneralAction
argument_list|(
name|Actions
operator|.
name|ADD_FILE_LINK
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Attach file"
argument_list|)
argument_list|,
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|ATTACH_FILE
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|add
argument_list|(
operator|new
name|GeneralAction
argument_list|(
name|Actions
operator|.
name|OPEN_URL
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Open URL or DOI"
argument_list|)
argument_list|,
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|WWW
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
block|{
block|{
if|if
condition|(
operator|!
operator|(
name|isFieldSetForSelectedEntry
argument_list|(
literal|"url"
argument_list|)
operator|||
name|isFieldSetForSelectedEntry
argument_list|(
literal|"doi"
argument_list|)
operator|)
condition|)
block|{
name|this
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
block|}
block|}
argument_list|)
expr_stmt|;
name|addSeparator
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
name|GeneralAction
argument_list|(
name|Actions
operator|.
name|PLAIN_TEXT_IMPORT
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Plain text import"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|frame
operator|.
name|getMassSetField
argument_list|()
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|frame
operator|.
name|getManageKeywords
argument_list|()
argument_list|)
expr_stmt|;
name|addSeparator
argument_list|()
expr_stmt|;
comment|// for "add/move/remove to/from group" entries (appended here)
name|groupAdd
operator|=
operator|new
name|JMenuItem
argument_list|(
operator|new
name|GeneralAction
argument_list|(
name|Actions
operator|.
name|ADD_TO_GROUP
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Add to group"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|groupAdd
argument_list|)
expr_stmt|;
name|groupRemove
operator|=
operator|new
name|JMenuItem
argument_list|(
operator|new
name|GeneralAction
argument_list|(
name|Actions
operator|.
name|REMOVE_FROM_GROUP
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Remove from group"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|groupRemove
argument_list|)
expr_stmt|;
name|groupMoveTo
operator|=
name|add
argument_list|(
operator|new
name|GeneralAction
argument_list|(
name|Actions
operator|.
name|MOVE_TO_GROUP
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Move to group"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|groupMoveTo
argument_list|)
expr_stmt|;
name|floatMarked
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
block|{
name|Globals
operator|.
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|FLOAT_MARKED_ENTRIES
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
argument_list|)
expr_stmt|;
comment|// create disabledIcons for all menu entries
name|frame
operator|.
name|createDisabledIconsForMenuEntries
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
comment|/**      * Remove all types from the menu.      * Then cycle through all available values, and add them.      */
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
operator|(
operator|(
name|IconTheme
operator|.
name|FontBasedIcon
operator|)
name|field
operator|.
name|getRepresentingIcon
argument_list|()
operator|)
operator|.
name|createSmallIcon
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
annotation|@
name|Override
DECL|method|popupMenuWillBecomeVisible (PopupMenuEvent e)
specifier|public
name|void
name|popupMenuWillBecomeVisible
parameter_list|(
name|PopupMenuEvent
name|e
parameter_list|)
block|{
name|panel
operator|.
name|storeCurrentEdit
argument_list|()
expr_stmt|;
name|GroupTreeNode
name|groups
init|=
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
operator|.
name|getMetaData
argument_list|()
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
name|groupAdd
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|groupRemove
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|groupMoveTo
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|groupAdd
operator|.
name|setEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|groupRemove
operator|.
name|setEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|groupMoveTo
operator|.
name|setEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
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
name|JabRefPreferences
operator|.
name|FLOAT_MARKED_ENTRIES
argument_list|)
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|floatMarked
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|popupMenuWillBecomeInvisible (PopupMenuEvent e)
specifier|public
name|void
name|popupMenuWillBecomeInvisible
parameter_list|(
name|PopupMenuEvent
name|e
parameter_list|)
block|{
comment|// Nothing to do
block|}
annotation|@
name|Override
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
DECL|method|isFieldSetForSelectedEntry (String fieldname)
specifier|private
name|boolean
name|isFieldSetForSelectedEntry
parameter_list|(
name|String
name|fieldname
parameter_list|)
block|{
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
block|{
name|BibEntry
name|entry
init|=
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
decl_stmt|;
return|return
name|entry
operator|.
name|getFieldNames
argument_list|()
operator|.
name|contains
argument_list|(
name|fieldname
argument_list|)
return|;
block|}
else|else
block|{
return|return
literal|false
return|;
block|}
block|}
DECL|method|getFileIconForSelectedEntry ()
specifier|private
name|Icon
name|getFileIconForSelectedEntry
parameter_list|()
block|{
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
block|{
name|BibEntry
name|entry
init|=
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
decl_stmt|;
if|if
condition|(
name|entry
operator|.
name|hasField
argument_list|(
name|Globals
operator|.
name|FILE_FIELD
argument_list|)
condition|)
block|{
name|JLabel
name|label
init|=
name|FileListTableModel
operator|.
name|getFirstLabel
argument_list|(
name|entry
operator|.
name|getField
argument_list|(
name|Globals
operator|.
name|FILE_FIELD
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|label
operator|!=
literal|null
condition|)
block|{
return|return
name|label
operator|.
name|getIcon
argument_list|()
return|;
block|}
block|}
block|}
return|return
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|FILE
operator|.
name|getSmallIcon
argument_list|()
return|;
block|}
DECL|class|GeneralAction
class|class
name|GeneralAction
extends|extends
name|AbstractAction
block|{
DECL|field|command
specifier|private
specifier|final
name|String
name|command
decl_stmt|;
DECL|method|GeneralAction (String command, String name)
specifier|public
name|GeneralAction
parameter_list|(
name|String
name|command
parameter_list|,
name|String
name|name
parameter_list|)
block|{
name|super
argument_list|(
name|name
argument_list|)
expr_stmt|;
name|this
operator|.
name|command
operator|=
name|command
expr_stmt|;
block|}
DECL|method|GeneralAction (String command, String name, Icon icon)
specifier|public
name|GeneralAction
parameter_list|(
name|String
name|command
parameter_list|,
name|String
name|name
parameter_list|,
name|Icon
name|icon
parameter_list|)
block|{
name|super
argument_list|(
name|name
argument_list|,
name|icon
argument_list|)
expr_stmt|;
name|this
operator|.
name|command
operator|=
name|command
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|actionPerformed (ActionEvent e)
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
name|command
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Throwable
name|ex
parameter_list|)
block|{
name|LOGGER
operator|.
name|debug
argument_list|(
literal|"Cannot execute command "
operator|+
name|command
operator|+
literal|"."
argument_list|,
name|ex
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
end_class

end_unit

