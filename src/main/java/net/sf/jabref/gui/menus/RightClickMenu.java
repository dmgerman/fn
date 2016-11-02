begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
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
name|java
operator|.
name|util
operator|.
name|Arrays
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collections
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
name|Action
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|Icon
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
name|javax
operator|.
name|swing
operator|.
name|JMenu
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JMenuItem
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JPopupMenu
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
name|filelist
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
name|keyboard
operator|.
name|KeyBinding
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
name|mergeentries
operator|.
name|FetchAndMergeEntry
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
name|specialfields
operator|.
name|SpecialFieldMenuAction
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
name|specialfields
operator|.
name|SpecialFieldValueViewModel
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
name|specialfields
operator|.
name|SpecialFieldViewModel
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
name|model
operator|.
name|entry
operator|.
name|FieldName
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
name|model
operator|.
name|entry
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
name|preferences
operator|.
name|JabRefPreferences
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
name|areMultipleEntriesSelected
argument_list|()
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
name|getMainTable
argument_list|()
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
name|getMainTable
argument_list|()
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
literal|"Copy"
argument_list|)
operator|+
literal|"..."
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
argument_list|,
name|KeyBinding
operator|.
name|COPY_BIBTEX_KEY
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
argument_list|,
name|KeyBinding
operator|.
name|COPY_CITE_BIBTEX_KEY
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
argument_list|,
name|KeyBinding
operator|.
name|COPY_BIBTEX_KEY_AND_TITLE
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
name|COPY_KEY_AND_LINK
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Copy BibTeX key and link"
argument_list|)
argument_list|,
name|KeyBinding
operator|.
name|COPY_BIBTEX_KEY_AND_LINK
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
argument_list|,
name|KeyBinding
operator|.
name|COPY
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
argument_list|,
name|KeyBinding
operator|.
name|PASTE
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
argument_list|,
name|KeyBinding
operator|.
name|CUT
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
argument_list|,
name|KeyBinding
operator|.
name|DELETE_ENTRY
argument_list|)
argument_list|)
expr_stmt|;
name|GeneralAction
name|printPreviewAction
init|=
operator|new
name|GeneralAction
argument_list|(
name|Actions
operator|.
name|PRINT_PREVIEW
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Print entry preview"
argument_list|)
argument_list|,
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|PRINTED
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
decl_stmt|;
name|printPreviewAction
operator|.
name|setEnabled
argument_list|(
operator|!
name|multiple
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|printPreviewAction
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
name|markSpecific
operator|.
name|setIcon
argument_list|(
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|MARK_ENTRIES
operator|.
name|getSmallIcon
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
argument_list|,
name|KeyBinding
operator|.
name|MARK_ENTRIES
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
argument_list|,
name|KeyBinding
operator|.
name|UNMARK_ENTRIES
argument_list|)
argument_list|)
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
name|Optional
argument_list|<
name|String
argument_list|>
name|marked
init|=
name|be
operator|.
name|getField
argument_list|(
name|FieldName
operator|.
name|MARKED_INTERNAL
argument_list|)
decl_stmt|;
comment|// We have to check for "" too as the marked field may be empty
if|if
condition|(
operator|(
operator|!
name|marked
operator|.
name|isPresent
argument_list|()
operator|)
operator|||
name|marked
operator|.
name|get
argument_list|()
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
argument_list|,
name|KeyBinding
operator|.
name|MARK_ENTRIES
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
argument_list|,
name|KeyBinding
operator|.
name|UNMARK_ENTRIES
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|SPECIALFIELDSENABLED
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
name|JabRefPreferences
operator|.
name|SHOWCOLUMN_RANKING
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
name|SpecialField
operator|.
name|RANKING
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
name|JabRefPreferences
operator|.
name|SHOWCOLUMN_RELEVANCE
argument_list|)
condition|)
block|{
name|add
argument_list|(
operator|new
name|SpecialFieldMenuAction
argument_list|(
operator|new
name|SpecialFieldValueViewModel
argument_list|(
name|SpecialField
operator|.
name|RELEVANCE
operator|.
name|getValues
argument_list|()
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
argument_list|,
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
name|JabRefPreferences
operator|.
name|SHOWCOLUMN_QUALITY
argument_list|)
condition|)
block|{
name|add
argument_list|(
operator|new
name|SpecialFieldMenuAction
argument_list|(
operator|new
name|SpecialFieldValueViewModel
argument_list|(
name|SpecialField
operator|.
name|QUALITY
operator|.
name|getValues
argument_list|()
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
argument_list|,
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
name|JabRefPreferences
operator|.
name|SHOWCOLUMN_PRINTED
argument_list|)
condition|)
block|{
name|add
argument_list|(
operator|new
name|SpecialFieldMenuAction
argument_list|(
operator|new
name|SpecialFieldValueViewModel
argument_list|(
name|SpecialField
operator|.
name|PRINTED
operator|.
name|getValues
argument_list|()
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
argument_list|,
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
name|JabRefPreferences
operator|.
name|SHOWCOLUMN_PRIORITY
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
name|SpecialField
operator|.
name|PRIORITY
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
name|JabRefPreferences
operator|.
name|SHOWCOLUMN_READ
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
name|SpecialField
operator|.
name|READ_STATUS
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
block|}
name|addSeparator
argument_list|()
expr_stmt|;
name|GeneralAction
name|openFolderAction
init|=
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
argument_list|,
name|KeyBinding
operator|.
name|OPEN_FOLDER
argument_list|)
decl_stmt|;
name|openFolderAction
operator|.
name|setEnabled
argument_list|(
name|isFieldSetForSelectedEntry
argument_list|(
name|FieldName
operator|.
name|FILE
argument_list|)
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|openFolderAction
argument_list|)
expr_stmt|;
name|GeneralAction
name|openFileAction
init|=
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
argument_list|,
name|KeyBinding
operator|.
name|OPEN_FILE
argument_list|)
decl_stmt|;
name|openFileAction
operator|.
name|setEnabled
argument_list|(
name|isFieldSetForSelectedEntry
argument_list|(
name|FieldName
operator|.
name|FILE
argument_list|)
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|openFileAction
argument_list|)
expr_stmt|;
name|GeneralAction
name|openUrlAction
init|=
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
argument_list|,
name|KeyBinding
operator|.
name|OPEN_URL_OR_DOI
argument_list|)
decl_stmt|;
name|openUrlAction
operator|.
name|setEnabled
argument_list|(
name|isFieldSetForSelectedEntry
argument_list|(
name|FieldName
operator|.
name|URL
argument_list|)
operator|||
name|isFieldSetForSelectedEntry
argument_list|(
name|FieldName
operator|.
name|DOI
argument_list|)
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|openUrlAction
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
name|GeneralAction
name|mergeFetchedEntryAction
init|=
operator|new
name|GeneralAction
argument_list|(
name|Actions
operator|.
name|MERGE_WITH_FETCHED_ENTRY
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Get BibTeX data from %0"
argument_list|,
name|FetchAndMergeEntry
operator|.
name|getDisplayNameOfSupportedFields
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
name|mergeFetchedEntryAction
operator|.
name|setEnabled
argument_list|(
name|isAnyFieldSetForSelectedEntry
argument_list|(
name|FetchAndMergeEntry
operator|.
name|SUPPORTED_FIELDS
argument_list|)
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|mergeFetchedEntryAction
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
name|GeneralAction
name|attachFileAction
init|=
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
decl_stmt|;
name|attachFileAction
operator|.
name|setEnabled
argument_list|(
operator|!
name|multiple
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|attachFileAction
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
name|GeneralAction
name|mergeEntriesAction
init|=
operator|new
name|GeneralAction
argument_list|(
name|Actions
operator|.
name|MERGE_ENTRIES
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Merge entries"
argument_list|)
operator|+
literal|"..."
argument_list|,
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|MERGE_ENTRIES
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
decl_stmt|;
name|mergeEntriesAction
operator|.
name|setEnabled
argument_list|(
name|areExactlyTwoEntriesSelected
argument_list|()
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|mergeEntriesAction
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
comment|// create disabledIcons for all menu entries
name|frame
operator|.
name|createDisabledIconsForMenuEntries
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
DECL|method|areMultipleEntriesSelected ()
specifier|private
name|boolean
name|areMultipleEntriesSelected
parameter_list|()
block|{
return|return
name|panel
operator|.
name|getMainTable
argument_list|()
operator|.
name|getSelectedRowCount
argument_list|()
operator|>
literal|1
return|;
block|}
DECL|method|areExactlyTwoEntriesSelected ()
specifier|private
name|boolean
name|areExactlyTwoEntriesSelected
parameter_list|()
block|{
return|return
name|panel
operator|.
name|getMainTable
argument_list|()
operator|.
name|getSelectedRowCount
argument_list|()
operator|==
literal|2
return|;
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
name|SpecialFieldViewModel
name|viewModel
init|=
operator|new
name|SpecialFieldViewModel
argument_list|(
name|field
argument_list|)
decl_stmt|;
name|menu
operator|.
name|setText
argument_list|(
name|viewModel
operator|.
name|getLocalization
argument_list|()
argument_list|)
expr_stmt|;
name|menu
operator|.
name|setIcon
argument_list|(
name|viewModel
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
operator|new
name|SpecialFieldMenuAction
argument_list|(
operator|new
name|SpecialFieldValueViewModel
argument_list|(
name|val
argument_list|)
argument_list|,
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
name|boolean
name|groupsPresent
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
operator|.
name|isPresent
argument_list|()
decl_stmt|;
name|groupAdd
operator|.
name|setEnabled
argument_list|(
name|groupsPresent
argument_list|)
expr_stmt|;
name|groupRemove
operator|.
name|setEnabled
argument_list|(
name|groupsPresent
argument_list|)
expr_stmt|;
name|groupMoveTo
operator|.
name|setEnabled
argument_list|(
name|groupsPresent
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
return|return
name|isAnyFieldSetForSelectedEntry
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|fieldname
argument_list|)
argument_list|)
return|;
block|}
DECL|method|isAnyFieldSetForSelectedEntry (List<String> fieldnames)
specifier|private
name|boolean
name|isAnyFieldSetForSelectedEntry
parameter_list|(
name|List
argument_list|<
name|String
argument_list|>
name|fieldnames
parameter_list|)
block|{
if|if
condition|(
name|panel
operator|.
name|getMainTable
argument_list|()
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
name|getMainTable
argument_list|()
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
operator|!
name|Collections
operator|.
name|disjoint
argument_list|(
name|fieldnames
argument_list|,
name|entry
operator|.
name|getFieldNames
argument_list|()
argument_list|)
return|;
block|}
return|return
literal|false
return|;
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
name|getMainTable
argument_list|()
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
name|getMainTable
argument_list|()
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
name|FieldName
operator|.
name|FILE
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
name|FieldName
operator|.
name|FILE
argument_list|)
operator|.
name|get
argument_list|()
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
DECL|method|GeneralAction (String command, String name, KeyBinding key)
specifier|public
name|GeneralAction
parameter_list|(
name|String
name|command
parameter_list|,
name|String
name|name
parameter_list|,
name|KeyBinding
name|key
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
name|putValue
argument_list|(
name|Action
operator|.
name|ACCELERATOR_KEY
argument_list|,
name|Globals
operator|.
name|getKeyPrefs
argument_list|()
operator|.
name|getKey
argument_list|(
name|key
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|GeneralAction (String command, String name, Icon icon, KeyBinding key)
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
parameter_list|,
name|KeyBinding
name|key
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
name|putValue
argument_list|(
name|Action
operator|.
name|ACCELERATOR_KEY
argument_list|,
name|Globals
operator|.
name|getKeyPrefs
argument_list|()
operator|.
name|getKey
argument_list|(
name|key
argument_list|)
argument_list|)
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

