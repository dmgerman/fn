begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
package|;
end_package

begin_import
import|import
name|ca
operator|.
name|odell
operator|.
name|glazedlists
operator|.
name|BasicEventList
import|;
end_import

begin_import
import|import
name|ca
operator|.
name|odell
operator|.
name|glazedlists
operator|.
name|EventList
import|;
end_import

begin_import
import|import
name|ca
operator|.
name|odell
operator|.
name|glazedlists
operator|.
name|SortedList
import|;
end_import

begin_import
import|import
name|ca
operator|.
name|odell
operator|.
name|glazedlists
operator|.
name|event
operator|.
name|ListEvent
import|;
end_import

begin_import
import|import
name|ca
operator|.
name|odell
operator|.
name|glazedlists
operator|.
name|event
operator|.
name|ListEventListener
import|;
end_import

begin_import
import|import
name|ca
operator|.
name|odell
operator|.
name|glazedlists
operator|.
name|gui
operator|.
name|AdvancedTableFormat
import|;
end_import

begin_import
import|import
name|ca
operator|.
name|odell
operator|.
name|glazedlists
operator|.
name|swing
operator|.
name|EventSelectionModel
import|;
end_import

begin_import
import|import
name|ca
operator|.
name|odell
operator|.
name|glazedlists
operator|.
name|swing
operator|.
name|EventTableModel
import|;
end_import

begin_import
import|import
name|ca
operator|.
name|odell
operator|.
name|glazedlists
operator|.
name|swing
operator|.
name|TableComparatorChooser
import|;
end_import

begin_import
import|import
name|com
operator|.
name|jgoodies
operator|.
name|uif_lite
operator|.
name|component
operator|.
name|UIFSplitPane
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
name|external
operator|.
name|ExternalFileMenuItem
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
name|table
operator|.
name|TableColumnModel
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
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
name|Comparator
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_comment
comment|/**  * Dialog to display search results, potentially from more than one BasePanel, with  * possibility to preview and to locate each entry in the main window.  *  * TODO: should be possible to save or export the list.  */
end_comment

begin_class
DECL|class|SearchResultsDialog
specifier|public
class|class
name|SearchResultsDialog
block|{
DECL|field|frame
specifier|private
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|title
specifier|private
name|String
name|title
decl_stmt|;
DECL|field|diag
specifier|private
name|JDialog
name|diag
decl_stmt|;
DECL|field|fields
specifier|private
name|String
index|[]
name|fields
init|=
operator|new
name|String
index|[]
block|{
literal|"author"
block|,
literal|"title"
block|,
literal|"year"
block|,
literal|"journal"
block|}
decl_stmt|;
DECL|field|FILE_COL
DECL|field|URL_COL
specifier|protected
specifier|final
name|int
name|FILE_COL
init|=
literal|0
decl_stmt|,
name|URL_COL
init|=
literal|1
decl_stmt|,
DECL|field|PAD
name|PAD
init|=
literal|2
decl_stmt|;
DECL|field|fileLabel
specifier|private
name|JLabel
name|fileLabel
init|=
operator|new
name|JLabel
argument_list|(
name|GUIGlobals
operator|.
name|getImage
argument_list|(
literal|"psSmall"
argument_list|)
argument_list|)
decl_stmt|,
DECL|field|urlLabel
name|urlLabel
init|=
operator|new
name|JLabel
argument_list|(
name|GUIGlobals
operator|.
name|getImage
argument_list|(
literal|"wwwSmall"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|toRect
specifier|protected
name|Rectangle
name|toRect
init|=
operator|new
name|Rectangle
argument_list|(
literal|0
argument_list|,
literal|0
argument_list|,
literal|1
argument_list|,
literal|1
argument_list|)
decl_stmt|;
DECL|field|model
specifier|private
name|EventTableModel
name|model
decl_stmt|;
DECL|field|entries
specifier|private
name|EventList
argument_list|<
name|BibtexEntry
argument_list|>
name|entries
init|=
operator|new
name|BasicEventList
argument_list|<
name|BibtexEntry
argument_list|>
argument_list|()
decl_stmt|;
DECL|field|sortedEntries
specifier|private
name|SortedList
argument_list|<
name|BibtexEntry
argument_list|>
name|sortedEntries
decl_stmt|;
DECL|field|entryHome
specifier|private
name|HashMap
argument_list|<
name|BibtexEntry
argument_list|,
name|BasePanel
argument_list|>
name|entryHome
init|=
operator|new
name|HashMap
argument_list|<
name|BibtexEntry
argument_list|,
name|BasePanel
argument_list|>
argument_list|()
decl_stmt|;
DECL|field|entryTable
specifier|private
name|JTable
name|entryTable
decl_stmt|;
DECL|field|contentPane
specifier|protected
name|UIFSplitPane
name|contentPane
init|=
operator|new
name|UIFSplitPane
argument_list|(
name|UIFSplitPane
operator|.
name|VERTICAL_SPLIT
argument_list|)
decl_stmt|;
DECL|field|preview
name|PreviewPanel
name|preview
decl_stmt|;
DECL|method|SearchResultsDialog (JabRefFrame frame, String title)
specifier|public
name|SearchResultsDialog
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|String
name|title
parameter_list|)
block|{
name|this
operator|.
name|frame
operator|=
name|frame
expr_stmt|;
name|init
argument_list|(
name|title
argument_list|)
expr_stmt|;
block|}
DECL|method|init (String title)
specifier|private
name|void
name|init
parameter_list|(
name|String
name|title
parameter_list|)
block|{
name|diag
operator|=
operator|new
name|JDialog
argument_list|(
name|frame
argument_list|,
name|title
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|preview
operator|=
operator|new
name|PreviewPanel
argument_list|(
literal|null
argument_list|,
operator|new
name|MetaData
argument_list|()
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
literal|"preview1"
argument_list|)
argument_list|)
expr_stmt|;
name|sortedEntries
operator|=
operator|new
name|SortedList
argument_list|<
name|BibtexEntry
argument_list|>
argument_list|(
name|entries
argument_list|,
operator|new
name|EntryComparator
argument_list|(
literal|false
argument_list|,
literal|true
argument_list|,
literal|"author"
argument_list|)
argument_list|)
expr_stmt|;
name|model
operator|=
operator|new
name|EventTableModel
argument_list|(
name|sortedEntries
argument_list|,
operator|new
name|EntryTableFormat
argument_list|()
argument_list|)
expr_stmt|;
name|entryTable
operator|=
operator|new
name|JTable
argument_list|(
name|model
argument_list|)
expr_stmt|;
name|GeneralRenderer
name|renderer
init|=
operator|new
name|GeneralRenderer
argument_list|(
name|Color
operator|.
name|white
argument_list|)
decl_stmt|;
name|entryTable
operator|.
name|setDefaultRenderer
argument_list|(
name|JLabel
operator|.
name|class
argument_list|,
name|renderer
argument_list|)
expr_stmt|;
name|entryTable
operator|.
name|setDefaultRenderer
argument_list|(
name|String
operator|.
name|class
argument_list|,
name|renderer
argument_list|)
expr_stmt|;
name|setWidths
argument_list|()
expr_stmt|;
name|TableComparatorChooser
argument_list|<
name|BibtexEntry
argument_list|>
name|tableSorter
init|=
operator|new
name|TableComparatorChooser
argument_list|<
name|BibtexEntry
argument_list|>
argument_list|(
name|entryTable
argument_list|,
name|sortedEntries
argument_list|,
name|TableComparatorChooser
operator|.
name|MULTIPLE_COLUMN_KEYBOARD
argument_list|)
decl_stmt|;
name|setupComparatorChooser
argument_list|(
name|tableSorter
argument_list|)
expr_stmt|;
name|JScrollPane
name|sp
init|=
operator|new
name|JScrollPane
argument_list|(
name|entryTable
argument_list|)
decl_stmt|;
name|EventSelectionModel
argument_list|<
name|BibtexEntry
argument_list|>
name|selectionModel
init|=
operator|new
name|EventSelectionModel
argument_list|<
name|BibtexEntry
argument_list|>
argument_list|(
name|sortedEntries
argument_list|)
decl_stmt|;
name|entryTable
operator|.
name|setSelectionModel
argument_list|(
name|selectionModel
argument_list|)
expr_stmt|;
name|selectionModel
operator|.
name|getSelected
argument_list|()
operator|.
name|addListEventListener
argument_list|(
operator|new
name|EntrySelectionListener
argument_list|()
argument_list|)
expr_stmt|;
name|entryTable
operator|.
name|addMouseListener
argument_list|(
operator|new
name|TableClickListener
argument_list|()
argument_list|)
expr_stmt|;
name|contentPane
operator|.
name|setTopComponent
argument_list|(
name|sp
argument_list|)
expr_stmt|;
name|contentPane
operator|.
name|setBottomComponent
argument_list|(
name|preview
argument_list|)
expr_stmt|;
comment|// Key bindings:
name|AbstractAction
name|closeAction
init|=
operator|new
name|AbstractAction
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
name|diag
operator|.
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
decl_stmt|;
name|ActionMap
name|am
init|=
name|contentPane
operator|.
name|getActionMap
argument_list|()
decl_stmt|;
name|InputMap
name|im
init|=
name|contentPane
operator|.
name|getInputMap
argument_list|(
name|JComponent
operator|.
name|WHEN_IN_FOCUSED_WINDOW
argument_list|)
decl_stmt|;
name|im
operator|.
name|put
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getKey
argument_list|(
literal|"Close dialog"
argument_list|)
argument_list|,
literal|"close"
argument_list|)
expr_stmt|;
name|am
operator|.
name|put
argument_list|(
literal|"close"
argument_list|,
name|closeAction
argument_list|)
expr_stmt|;
name|diag
operator|.
name|addWindowListener
argument_list|(
operator|new
name|WindowAdapter
argument_list|()
block|{
specifier|public
name|void
name|windowOpened
parameter_list|(
name|WindowEvent
name|e
parameter_list|)
block|{
name|contentPane
operator|.
name|setDividerLocation
argument_list|(
literal|0.5f
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|windowClosing
parameter_list|(
name|WindowEvent
name|event
parameter_list|)
block|{
name|Globals
operator|.
name|prefs
operator|.
name|putInt
argument_list|(
literal|"searchDialogWidth"
argument_list|,
name|diag
operator|.
name|getSize
argument_list|()
operator|.
name|width
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|prefs
operator|.
name|putInt
argument_list|(
literal|"searchDialogHeight"
argument_list|,
name|diag
operator|.
name|getSize
argument_list|()
operator|.
name|height
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|diag
operator|.
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|contentPane
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
comment|// Remember and default to last size:
name|diag
operator|.
name|setSize
argument_list|(
operator|new
name|Dimension
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getInt
argument_list|(
literal|"searchDialogWidth"
argument_list|)
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getInt
argument_list|(
literal|"searchDialogHeight"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|diag
operator|.
name|setLocationRelativeTo
argument_list|(
name|frame
argument_list|)
expr_stmt|;
block|}
DECL|method|setVisible (boolean visible)
specifier|public
name|void
name|setVisible
parameter_list|(
name|boolean
name|visible
parameter_list|)
block|{
name|diag
operator|.
name|setVisible
argument_list|(
name|visible
argument_list|)
expr_stmt|;
block|}
comment|/**      * Remove all entries from the table.      */
DECL|method|clear ()
specifier|public
specifier|synchronized
name|void
name|clear
parameter_list|()
block|{
name|entries
operator|.
name|clear
argument_list|()
expr_stmt|;
name|entryHome
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
DECL|method|setupComparatorChooser (TableComparatorChooser<BibtexEntry> comparatorChooser)
specifier|protected
name|void
name|setupComparatorChooser
parameter_list|(
name|TableComparatorChooser
argument_list|<
name|BibtexEntry
argument_list|>
name|comparatorChooser
parameter_list|)
block|{
comment|// First column:
name|java
operator|.
name|util
operator|.
name|List
argument_list|<
name|Comparator
argument_list|<
name|BibtexEntry
argument_list|>
argument_list|>
name|comparators
init|=
name|comparatorChooser
operator|.
name|getComparatorsForColumn
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|comparators
operator|.
name|clear
argument_list|()
expr_stmt|;
name|comparators
operator|=
name|comparatorChooser
operator|.
name|getComparatorsForColumn
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|comparators
operator|.
name|clear
argument_list|()
expr_stmt|;
comment|// Icon columns:
for|for
control|(
name|int
name|i
init|=
literal|2
init|;
name|i
operator|<
name|PAD
condition|;
name|i
operator|++
control|)
block|{
name|comparators
operator|=
name|comparatorChooser
operator|.
name|getComparatorsForColumn
argument_list|(
name|i
argument_list|)
expr_stmt|;
name|comparators
operator|.
name|clear
argument_list|()
expr_stmt|;
if|if
condition|(
name|i
operator|==
name|FILE_COL
condition|)
name|comparators
operator|.
name|add
argument_list|(
operator|new
name|IconComparator
argument_list|(
operator|new
name|String
index|[]
block|{
name|GUIGlobals
operator|.
name|FILE_FIELD
block|}
argument_list|)
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
name|i
operator|==
name|URL_COL
condition|)
name|comparators
operator|.
name|add
argument_list|(
operator|new
name|IconComparator
argument_list|(
operator|new
name|String
index|[]
block|{
literal|"url"
block|}
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|// Remaining columns:
for|for
control|(
name|int
name|i
init|=
name|PAD
init|;
name|i
operator|<
name|PAD
operator|+
name|fields
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|comparators
operator|=
name|comparatorChooser
operator|.
name|getComparatorsForColumn
argument_list|(
name|i
argument_list|)
expr_stmt|;
name|comparators
operator|.
name|clear
argument_list|()
expr_stmt|;
name|comparators
operator|.
name|add
argument_list|(
operator|new
name|FieldComparator
argument_list|(
name|fields
index|[
name|i
operator|-
name|PAD
index|]
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|sortedEntries
operator|.
name|getReadWriteLock
argument_list|()
operator|.
name|writeLock
argument_list|()
operator|.
name|lock
argument_list|()
expr_stmt|;
name|comparatorChooser
operator|.
name|appendComparator
argument_list|(
name|PAD
argument_list|,
literal|0
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|sortedEntries
operator|.
name|getReadWriteLock
argument_list|()
operator|.
name|writeLock
argument_list|()
operator|.
name|unlock
argument_list|()
expr_stmt|;
block|}
DECL|method|setWidths ()
specifier|protected
name|void
name|setWidths
parameter_list|()
block|{
name|TableColumnModel
name|cm
init|=
name|entryTable
operator|.
name|getColumnModel
argument_list|()
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
name|PAD
condition|;
name|i
operator|++
control|)
block|{
comment|// Lock the width of icon columns.
name|cm
operator|.
name|getColumn
argument_list|(
name|i
argument_list|)
operator|.
name|setPreferredWidth
argument_list|(
name|GUIGlobals
operator|.
name|WIDTH_ICON_COL
argument_list|)
expr_stmt|;
name|cm
operator|.
name|getColumn
argument_list|(
name|i
argument_list|)
operator|.
name|setMinWidth
argument_list|(
name|GUIGlobals
operator|.
name|WIDTH_ICON_COL
argument_list|)
expr_stmt|;
name|cm
operator|.
name|getColumn
argument_list|(
name|i
argument_list|)
operator|.
name|setMaxWidth
argument_list|(
name|GUIGlobals
operator|.
name|WIDTH_ICON_COL
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|fields
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|int
name|width
init|=
name|BibtexFields
operator|.
name|getFieldLength
argument_list|(
name|fields
index|[
name|i
index|]
argument_list|)
decl_stmt|;
name|cm
operator|.
name|getColumn
argument_list|(
name|i
operator|+
name|PAD
argument_list|)
operator|.
name|setPreferredWidth
argument_list|(
name|width
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Add a list of entries to the table.      * @param newEntries The list of entries.      * @param panel A reference to the BasePanel where the entries belong.      */
DECL|method|addEntries (java.util.List<BibtexEntry> newEntries, BasePanel panel)
specifier|public
specifier|synchronized
name|void
name|addEntries
parameter_list|(
name|java
operator|.
name|util
operator|.
name|List
argument_list|<
name|BibtexEntry
argument_list|>
name|newEntries
parameter_list|,
name|BasePanel
name|panel
parameter_list|)
block|{
for|for
control|(
name|BibtexEntry
name|entry
range|:
name|newEntries
control|)
block|{
name|entries
operator|.
name|add
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|entryHome
operator|.
name|put
argument_list|(
name|entry
argument_list|,
name|panel
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Add a single entry to the table.      * @param entry The entry to add.      * @param panel A reference to the BasePanel where the entry belongs.      */
DECL|method|addEntry (BibtexEntry entry, BasePanel panel)
specifier|public
specifier|synchronized
name|void
name|addEntry
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|,
name|BasePanel
name|panel
parameter_list|)
block|{
name|entries
operator|.
name|add
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|entryHome
operator|.
name|put
argument_list|(
name|entry
argument_list|,
name|panel
argument_list|)
expr_stmt|;
block|}
comment|/**      * Mouse listener for the entry table.      */
DECL|class|TableClickListener
class|class
name|TableClickListener
extends|extends
name|MouseAdapter
block|{
DECL|method|mouseReleased (MouseEvent e)
specifier|public
name|void
name|mouseReleased
parameter_list|(
name|MouseEvent
name|e
parameter_list|)
block|{
if|if
condition|(
name|e
operator|.
name|isPopupTrigger
argument_list|()
condition|)
block|{
name|processPopupTrigger
argument_list|(
name|e
argument_list|)
expr_stmt|;
return|return;
block|}
block|}
DECL|method|mousePressed (MouseEvent e)
specifier|public
name|void
name|mousePressed
parameter_list|(
name|MouseEvent
name|e
parameter_list|)
block|{
if|if
condition|(
name|e
operator|.
name|isPopupTrigger
argument_list|()
condition|)
block|{
name|processPopupTrigger
argument_list|(
name|e
argument_list|)
expr_stmt|;
return|return;
block|}
comment|// First find the row on which the user has clicked.
specifier|final
name|int
name|row
init|=
name|entryTable
operator|.
name|rowAtPoint
argument_list|(
name|e
operator|.
name|getPoint
argument_list|()
argument_list|)
decl_stmt|;
comment|// A double click on an entry should highlight the entry in its BasePanel:
if|if
condition|(
name|e
operator|.
name|getClickCount
argument_list|()
operator|==
literal|2
condition|)
block|{
comment|// Get the selected entry:
name|BibtexEntry
name|toShow
init|=
operator|(
name|BibtexEntry
operator|)
name|model
operator|.
name|getElementAt
argument_list|(
name|row
argument_list|)
decl_stmt|;
comment|// Look up which BasePanel it belongs to:
name|BasePanel
name|p
init|=
name|entryHome
operator|.
name|get
argument_list|(
name|toShow
argument_list|)
decl_stmt|;
comment|// Show the correct tab in the main window:
name|frame
operator|.
name|showBasePanel
argument_list|(
name|p
argument_list|)
expr_stmt|;
comment|// Highlight the entry:
name|p
operator|.
name|highlightEntry
argument_list|(
name|toShow
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|mouseClicked (MouseEvent e)
specifier|public
name|void
name|mouseClicked
parameter_list|(
name|MouseEvent
name|e
parameter_list|)
block|{
if|if
condition|(
name|e
operator|.
name|isPopupTrigger
argument_list|()
condition|)
block|{
name|processPopupTrigger
argument_list|(
name|e
argument_list|)
expr_stmt|;
return|return;
block|}
comment|//if (e.)
specifier|final
name|int
name|col
init|=
name|entryTable
operator|.
name|columnAtPoint
argument_list|(
name|e
operator|.
name|getPoint
argument_list|()
argument_list|)
decl_stmt|,
name|row
init|=
name|entryTable
operator|.
name|rowAtPoint
argument_list|(
name|e
operator|.
name|getPoint
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|col
operator|<
name|PAD
condition|)
block|{
name|BibtexEntry
name|entry
init|=
name|sortedEntries
operator|.
name|get
argument_list|(
name|row
argument_list|)
decl_stmt|;
name|BasePanel
name|p
init|=
name|entryHome
operator|.
name|get
argument_list|(
name|entry
argument_list|)
decl_stmt|;
switch|switch
condition|(
name|col
condition|)
block|{
case|case
name|FILE_COL
case|:
name|Object
name|o
init|=
name|entry
operator|.
name|getField
argument_list|(
name|GUIGlobals
operator|.
name|FILE_FIELD
argument_list|)
decl_stmt|;
if|if
condition|(
name|o
operator|!=
literal|null
condition|)
block|{
name|FileListTableModel
name|tableModel
init|=
operator|new
name|FileListTableModel
argument_list|()
decl_stmt|;
name|tableModel
operator|.
name|setContent
argument_list|(
operator|(
name|String
operator|)
name|o
argument_list|)
expr_stmt|;
if|if
condition|(
name|tableModel
operator|.
name|getRowCount
argument_list|()
operator|==
literal|0
condition|)
return|return;
name|FileListEntry
name|fl
init|=
name|tableModel
operator|.
name|getEntry
argument_list|(
literal|0
argument_list|)
decl_stmt|;
operator|(
operator|new
name|ExternalFileMenuItem
argument_list|(
name|frame
argument_list|,
name|entry
argument_list|,
literal|""
argument_list|,
name|fl
operator|.
name|getLink
argument_list|()
argument_list|,
literal|null
argument_list|,
name|p
operator|.
name|metaData
argument_list|()
argument_list|,
name|fl
operator|.
name|getType
argument_list|()
argument_list|)
operator|)
operator|.
name|actionPerformed
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
break|break;
case|case
name|URL_COL
case|:
name|Object
name|link
init|=
name|entry
operator|.
name|getField
argument_list|(
literal|"url"
argument_list|)
decl_stmt|;
try|try
block|{
if|if
condition|(
name|link
operator|!=
literal|null
condition|)
name|Util
operator|.
name|openExternalViewer
argument_list|(
name|p
operator|.
name|metaData
argument_list|()
argument_list|,
operator|(
name|String
operator|)
name|link
argument_list|,
literal|"url"
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{
name|ex
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
break|break;
block|}
block|}
block|}
DECL|method|processPopupTrigger (MouseEvent e)
specifier|public
name|void
name|processPopupTrigger
parameter_list|(
name|MouseEvent
name|e
parameter_list|)
block|{
name|BibtexEntry
name|entry
init|=
name|sortedEntries
operator|.
name|get
argument_list|(
name|entryTable
operator|.
name|rowAtPoint
argument_list|(
name|e
operator|.
name|getPoint
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
name|BasePanel
name|p
init|=
name|entryHome
operator|.
name|get
argument_list|(
name|entry
argument_list|)
decl_stmt|;
name|int
name|col
init|=
name|entryTable
operator|.
name|columnAtPoint
argument_list|(
name|e
operator|.
name|getPoint
argument_list|()
argument_list|)
decl_stmt|;
name|JPopupMenu
name|menu
init|=
operator|new
name|JPopupMenu
argument_list|()
decl_stmt|;
name|int
name|count
init|=
literal|0
decl_stmt|;
if|if
condition|(
name|col
operator|==
name|FILE_COL
condition|)
block|{
comment|// We use a FileListTableModel to parse the field content:
name|Object
name|o
init|=
name|entry
operator|.
name|getField
argument_list|(
name|GUIGlobals
operator|.
name|FILE_FIELD
argument_list|)
decl_stmt|;
name|FileListTableModel
name|fileList
init|=
operator|new
name|FileListTableModel
argument_list|()
decl_stmt|;
name|fileList
operator|.
name|setContent
argument_list|(
operator|(
name|String
operator|)
name|o
argument_list|)
expr_stmt|;
comment|// If there are one or more links, open the first one:
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|fileList
operator|.
name|getRowCount
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|FileListEntry
name|flEntry
init|=
name|fileList
operator|.
name|getEntry
argument_list|(
name|i
argument_list|)
decl_stmt|;
name|String
name|description
init|=
name|flEntry
operator|.
name|getDescription
argument_list|()
decl_stmt|;
if|if
condition|(
operator|(
name|description
operator|==
literal|null
operator|)
operator|||
operator|(
name|description
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
operator|)
condition|)
name|description
operator|=
name|flEntry
operator|.
name|getLink
argument_list|()
expr_stmt|;
name|menu
operator|.
name|add
argument_list|(
operator|new
name|ExternalFileMenuItem
argument_list|(
name|p
operator|.
name|frame
argument_list|()
argument_list|,
name|entry
argument_list|,
name|description
argument_list|,
name|flEntry
operator|.
name|getLink
argument_list|()
argument_list|,
name|flEntry
operator|.
name|getType
argument_list|()
operator|.
name|getIcon
argument_list|()
argument_list|,
name|p
operator|.
name|metaData
argument_list|()
argument_list|,
name|flEntry
operator|.
name|getType
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|count
operator|++
expr_stmt|;
block|}
block|}
if|if
condition|(
name|count
operator|>
literal|0
condition|)
name|menu
operator|.
name|show
argument_list|(
name|entryTable
argument_list|,
name|e
operator|.
name|getX
argument_list|()
argument_list|,
name|e
operator|.
name|getY
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
DECL|class|EntrySelectionListener
class|class
name|EntrySelectionListener
implements|implements
name|ListEventListener
argument_list|<
name|BibtexEntry
argument_list|>
block|{
DECL|method|listChanged (ListEvent<BibtexEntry> listEvent)
specifier|public
name|void
name|listChanged
parameter_list|(
name|ListEvent
argument_list|<
name|BibtexEntry
argument_list|>
name|listEvent
parameter_list|)
block|{
if|if
condition|(
name|listEvent
operator|.
name|getSourceList
argument_list|()
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
block|{
name|BibtexEntry
name|entry
init|=
name|listEvent
operator|.
name|getSourceList
argument_list|()
operator|.
name|get
argument_list|(
literal|0
argument_list|)
decl_stmt|;
comment|// Find out which BasePanel the selected entry belongs to:
name|BasePanel
name|p
init|=
name|entryHome
operator|.
name|get
argument_list|(
name|entry
argument_list|)
decl_stmt|;
comment|// Update the preview's metadata reference:
name|preview
operator|.
name|setMetaData
argument_list|(
name|p
operator|.
name|metaData
argument_list|()
argument_list|)
expr_stmt|;
comment|// Update the preview's entry:
name|preview
operator|.
name|setEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|contentPane
operator|.
name|setDividerLocation
argument_list|(
literal|0.5f
argument_list|)
expr_stmt|;
name|SwingUtilities
operator|.
name|invokeLater
argument_list|(
operator|new
name|Runnable
argument_list|()
block|{
specifier|public
name|void
name|run
parameter_list|()
block|{
name|preview
operator|.
name|scrollRectToVisible
argument_list|(
name|toRect
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|/**      * TableFormat for the table shown in the dialog.      */
DECL|class|EntryTableFormat
specifier|public
class|class
name|EntryTableFormat
implements|implements
name|AdvancedTableFormat
block|{
DECL|method|getColumnCount ()
specifier|public
name|int
name|getColumnCount
parameter_list|()
block|{
return|return
name|PAD
operator|+
name|fields
operator|.
name|length
return|;
block|}
DECL|method|getColumnName (int column)
specifier|public
name|String
name|getColumnName
parameter_list|(
name|int
name|column
parameter_list|)
block|{
if|if
condition|(
name|column
operator|>=
name|PAD
condition|)
return|return
name|Util
operator|.
name|nCase
argument_list|(
name|fields
index|[
name|column
operator|-
name|PAD
index|]
argument_list|)
return|;
else|else
return|return
literal|""
return|;
block|}
DECL|method|getColumnValue (Object ob, int column)
specifier|public
name|Object
name|getColumnValue
parameter_list|(
name|Object
name|ob
parameter_list|,
name|int
name|column
parameter_list|)
block|{
name|BibtexEntry
name|entry
init|=
operator|(
name|BibtexEntry
operator|)
name|ob
decl_stmt|;
if|if
condition|(
name|column
operator|<
name|PAD
condition|)
block|{
name|Object
name|o
decl_stmt|;
switch|switch
condition|(
name|column
condition|)
block|{
case|case
name|FILE_COL
case|:
name|o
operator|=
name|entry
operator|.
name|getField
argument_list|(
name|GUIGlobals
operator|.
name|FILE_FIELD
argument_list|)
expr_stmt|;
if|if
condition|(
name|o
operator|!=
literal|null
condition|)
block|{
name|FileListTableModel
name|model
init|=
operator|new
name|FileListTableModel
argument_list|()
decl_stmt|;
name|model
operator|.
name|setContent
argument_list|(
operator|(
name|String
operator|)
name|o
argument_list|)
expr_stmt|;
name|fileLabel
operator|.
name|setToolTipText
argument_list|(
name|model
operator|.
name|getToolTipHTMLRepresentation
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|model
operator|.
name|getRowCount
argument_list|()
operator|>
literal|0
condition|)
name|fileLabel
operator|.
name|setIcon
argument_list|(
name|model
operator|.
name|getEntry
argument_list|(
literal|0
argument_list|)
operator|.
name|getType
argument_list|()
operator|.
name|getIcon
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|fileLabel
return|;
block|}
else|else
return|return
literal|null
return|;
case|case
name|URL_COL
case|:
name|o
operator|=
name|entry
operator|.
name|getField
argument_list|(
literal|"url"
argument_list|)
expr_stmt|;
if|if
condition|(
name|o
operator|!=
literal|null
condition|)
block|{
name|urlLabel
operator|.
name|setToolTipText
argument_list|(
operator|(
name|String
operator|)
name|o
argument_list|)
expr_stmt|;
return|return
name|urlLabel
return|;
block|}
else|else
return|return
literal|null
return|;
default|default:
return|return
literal|null
return|;
block|}
block|}
else|else
return|return
name|entry
operator|.
name|getField
argument_list|(
name|fields
index|[
name|column
operator|-
name|PAD
index|]
argument_list|)
return|;
block|}
DECL|method|getColumnClass (int i)
specifier|public
name|Class
name|getColumnClass
parameter_list|(
name|int
name|i
parameter_list|)
block|{
if|if
condition|(
name|i
operator|<
name|PAD
condition|)
return|return
name|JLabel
operator|.
name|class
return|;
else|else
return|return
name|String
operator|.
name|class
return|;
block|}
DECL|method|getColumnComparator (int i)
specifier|public
name|Comparator
name|getColumnComparator
parameter_list|(
name|int
name|i
parameter_list|)
block|{
return|return
literal|null
return|;
block|}
block|}
block|}
end_class

end_unit

