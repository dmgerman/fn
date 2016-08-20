begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.importer
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|importer
package|;
end_package

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|BorderLayout
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Component
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Dimension
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
name|util
operator|.
name|LinkedHashMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
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
name|ActionMap
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|BorderFactory
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|InputMap
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JButton
import|;
end_import

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
name|JDialog
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
name|JOptionPane
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JPanel
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JScrollPane
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JTable
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
name|TableCellRenderer
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
name|TableModel
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
name|logic
operator|.
name|importer
operator|.
name|OutputPrinter
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
name|gui
operator|.
name|TableFormat
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
name|DefaultEventSelectionModel
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
name|DefaultEventTableModel
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
name|GlazedListsSwing
import|;
end_import

begin_import
import|import
name|com
operator|.
name|jgoodies
operator|.
name|forms
operator|.
name|builder
operator|.
name|ButtonBarBuilder
import|;
end_import

begin_import
import|import
name|com
operator|.
name|jgoodies
operator|.
name|forms
operator|.
name|builder
operator|.
name|ButtonStackBuilder
import|;
end_import

begin_comment
comment|/**  *  */
end_comment

begin_class
DECL|class|FetcherPreviewDialog
specifier|public
class|class
name|FetcherPreviewDialog
extends|extends
name|JDialog
implements|implements
name|OutputPrinter
block|{
DECL|field|entries
specifier|private
specifier|final
name|EventList
argument_list|<
name|TableEntry
argument_list|>
name|entries
init|=
operator|new
name|BasicEventList
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|glTable
specifier|private
specifier|final
name|JTable
name|glTable
decl_stmt|;
DECL|field|okPressed
specifier|private
name|boolean
name|okPressed
decl_stmt|;
DECL|field|frame
specifier|private
specifier|final
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|warningLimit
specifier|private
specifier|final
name|int
name|warningLimit
decl_stmt|;
DECL|method|FetcherPreviewDialog (JabRefFrame frame, int warningLimit, int tableRowHeight)
specifier|public
name|FetcherPreviewDialog
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|int
name|warningLimit
parameter_list|,
name|int
name|tableRowHeight
parameter_list|)
block|{
name|super
argument_list|(
name|frame
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Title"
argument_list|)
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|this
operator|.
name|frame
operator|=
name|frame
expr_stmt|;
name|this
operator|.
name|warningLimit
operator|=
name|warningLimit
expr_stmt|;
name|JButton
name|ok
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"OK"
argument_list|)
argument_list|)
decl_stmt|;
name|ok
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
block|{
if|if
condition|(
name|verifySelection
argument_list|()
condition|)
block|{
name|okPressed
operator|=
literal|true
expr_stmt|;
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|JButton
name|cancel
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Cancel"
argument_list|)
argument_list|)
decl_stmt|;
name|cancel
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
block|{
name|okPressed
operator|=
literal|false
expr_stmt|;
name|dispose
argument_list|()
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
name|JButton
name|selectAll
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Select all"
argument_list|)
argument_list|)
decl_stmt|;
name|selectAll
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
name|setSelectionAll
argument_list|(
literal|true
argument_list|)
argument_list|)
expr_stmt|;
name|JButton
name|deselectAll
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Deselect all"
argument_list|)
argument_list|)
decl_stmt|;
name|deselectAll
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
name|setSelectionAll
argument_list|(
literal|false
argument_list|)
argument_list|)
expr_stmt|;
name|DefaultEventTableModel
argument_list|<
name|TableEntry
argument_list|>
name|tableModelGl
init|=
operator|(
name|DefaultEventTableModel
argument_list|<
name|TableEntry
argument_list|>
operator|)
name|GlazedListsSwing
operator|.
name|eventTableModelWithThreadProxyList
argument_list|(
name|entries
argument_list|,
operator|new
name|EntryTableFormat
argument_list|()
argument_list|)
decl_stmt|;
name|glTable
operator|=
operator|new
name|EntryTable
argument_list|(
name|tableModelGl
argument_list|)
expr_stmt|;
name|glTable
operator|.
name|setRowHeight
argument_list|(
name|tableRowHeight
argument_list|)
expr_stmt|;
name|glTable
operator|.
name|getColumnModel
argument_list|()
operator|.
name|getColumn
argument_list|(
literal|0
argument_list|)
operator|.
name|setMaxWidth
argument_list|(
literal|45
argument_list|)
expr_stmt|;
name|glTable
operator|.
name|setPreferredScrollableViewportSize
argument_list|(
operator|new
name|Dimension
argument_list|(
literal|1100
argument_list|,
literal|600
argument_list|)
argument_list|)
expr_stmt|;
name|DefaultEventSelectionModel
argument_list|<
name|TableEntry
argument_list|>
name|selectionModel
init|=
operator|(
name|DefaultEventSelectionModel
argument_list|<
name|TableEntry
argument_list|>
operator|)
name|GlazedListsSwing
operator|.
name|eventSelectionModelWithThreadProxyList
argument_list|(
name|entries
argument_list|)
decl_stmt|;
name|glTable
operator|.
name|setSelectionModel
argument_list|(
name|selectionModel
argument_list|)
expr_stmt|;
name|ButtonStackBuilder
name|builder
init|=
operator|new
name|ButtonStackBuilder
argument_list|()
decl_stmt|;
name|builder
operator|.
name|addButton
argument_list|(
name|selectAll
argument_list|)
expr_stmt|;
name|builder
operator|.
name|addButton
argument_list|(
name|deselectAll
argument_list|)
expr_stmt|;
name|builder
operator|.
name|getPanel
argument_list|()
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createEmptyBorder
argument_list|(
literal|5
argument_list|,
literal|5
argument_list|,
literal|5
argument_list|,
literal|5
argument_list|)
argument_list|)
expr_stmt|;
name|ButtonBarBuilder
name|bb
init|=
operator|new
name|ButtonBarBuilder
argument_list|()
decl_stmt|;
name|bb
operator|.
name|addGlue
argument_list|()
expr_stmt|;
name|bb
operator|.
name|addButton
argument_list|(
name|ok
argument_list|)
expr_stmt|;
name|bb
operator|.
name|addButton
argument_list|(
name|cancel
argument_list|)
expr_stmt|;
name|bb
operator|.
name|addGlue
argument_list|()
expr_stmt|;
name|bb
operator|.
name|getPanel
argument_list|()
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createEmptyBorder
argument_list|(
literal|5
argument_list|,
literal|5
argument_list|,
literal|5
argument_list|,
literal|5
argument_list|)
argument_list|)
expr_stmt|;
name|JPanel
name|centerPan
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|centerPan
operator|.
name|setLayout
argument_list|(
operator|new
name|BorderLayout
argument_list|()
argument_list|)
expr_stmt|;
name|centerPan
operator|.
name|add
argument_list|(
operator|new
name|JScrollPane
argument_list|(
name|glTable
argument_list|)
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|centerPan
operator|.
name|add
argument_list|(
name|builder
operator|.
name|getPanel
argument_list|()
argument_list|,
name|BorderLayout
operator|.
name|WEST
argument_list|)
expr_stmt|;
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|centerPan
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|bb
operator|.
name|getPanel
argument_list|()
argument_list|,
name|BorderLayout
operator|.
name|SOUTH
argument_list|)
expr_stmt|;
comment|// Key bindings:
name|Action
name|closeAction
init|=
operator|new
name|AbstractAction
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
decl_stmt|;
name|ActionMap
name|am
init|=
name|centerPan
operator|.
name|getActionMap
argument_list|()
decl_stmt|;
name|InputMap
name|im
init|=
name|centerPan
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
name|getKeyPrefs
argument_list|()
operator|.
name|getKey
argument_list|(
name|KeyBinding
operator|.
name|CLOSE_DIALOG
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
name|pack
argument_list|()
expr_stmt|;
block|}
comment|/**      * Check whether a large number of entries are selected, and if so, ask the user whether      * to go on.      * @return true if we should go on      */
DECL|method|verifySelection ()
specifier|private
name|boolean
name|verifySelection
parameter_list|()
block|{
name|int
name|selected
init|=
literal|0
decl_stmt|;
for|for
control|(
name|TableEntry
name|entry
range|:
name|entries
control|)
block|{
if|if
condition|(
name|entry
operator|.
name|isWanted
argument_list|()
condition|)
block|{
name|selected
operator|++
expr_stmt|;
block|}
block|}
if|if
condition|(
name|selected
operator|>
name|warningLimit
condition|)
block|{
name|int
name|result
init|=
name|JOptionPane
operator|.
name|showConfirmDialog
argument_list|(
name|this
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"You have selected more than %0 entries for download. Some web sites "
operator|+
literal|"might block you if you make too many rapid downloads. Do you want to continue?"
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|warningLimit
argument_list|)
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Confirm selection"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|YES_NO_OPTION
argument_list|,
name|JOptionPane
operator|.
name|WARNING_MESSAGE
argument_list|)
decl_stmt|;
return|return
name|result
operator|==
name|JOptionPane
operator|.
name|YES_OPTION
return|;
block|}
else|else
block|{
return|return
literal|true
return|;
block|}
block|}
DECL|method|getSelection ()
specifier|public
name|Map
argument_list|<
name|String
argument_list|,
name|Boolean
argument_list|>
name|getSelection
parameter_list|()
block|{
name|LinkedHashMap
argument_list|<
name|String
argument_list|,
name|Boolean
argument_list|>
name|selection
init|=
operator|new
name|LinkedHashMap
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
name|TableEntry
name|e
range|:
name|entries
control|)
block|{
name|selection
operator|.
name|put
argument_list|(
name|e
operator|.
name|id
argument_list|,
name|e
operator|.
name|isWanted
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|selection
return|;
block|}
comment|/* (non-Javadoc)     * @see net.sf.jabref.gui.ImportInspection#addEntry(net.sf.jabref.BibEntry)     */
DECL|method|addEntry (String entryId, JLabel preview)
specifier|public
name|void
name|addEntry
parameter_list|(
name|String
name|entryId
parameter_list|,
name|JLabel
name|preview
parameter_list|)
block|{
name|TableEntry
name|entry
init|=
operator|new
name|TableEntry
argument_list|(
name|entryId
argument_list|,
name|preview
argument_list|)
decl_stmt|;
name|this
operator|.
name|entries
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
name|this
operator|.
name|entries
operator|.
name|add
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|this
operator|.
name|entries
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
name|glTable
operator|.
name|repaint
argument_list|()
expr_stmt|;
block|}
DECL|method|setSelectionAll (boolean select)
specifier|private
name|void
name|setSelectionAll
parameter_list|(
name|boolean
name|select
parameter_list|)
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
name|glTable
operator|.
name|getRowCount
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|glTable
operator|.
name|setValueAt
argument_list|(
name|select
argument_list|,
name|i
argument_list|,
literal|0
argument_list|)
expr_stmt|;
block|}
name|glTable
operator|.
name|repaint
argument_list|()
expr_stmt|;
block|}
DECL|class|TableEntry
specifier|static
class|class
name|TableEntry
block|{
DECL|field|id
specifier|private
specifier|final
name|String
name|id
decl_stmt|;
DECL|field|preview
specifier|private
specifier|final
name|JLabel
name|preview
decl_stmt|;
DECL|field|wanted
specifier|private
name|boolean
name|wanted
decl_stmt|;
DECL|method|TableEntry (String id, JLabel preview)
specifier|public
name|TableEntry
parameter_list|(
name|String
name|id
parameter_list|,
name|JLabel
name|preview
parameter_list|)
block|{
name|this
operator|.
name|id
operator|=
name|id
expr_stmt|;
name|this
operator|.
name|preview
operator|=
name|preview
expr_stmt|;
block|}
DECL|method|isWanted ()
specifier|public
name|boolean
name|isWanted
parameter_list|()
block|{
return|return
name|wanted
return|;
block|}
DECL|method|setWanted (boolean wanted)
specifier|public
name|void
name|setWanted
parameter_list|(
name|boolean
name|wanted
parameter_list|)
block|{
name|this
operator|.
name|wanted
operator|=
name|wanted
expr_stmt|;
block|}
DECL|method|getPreview ()
specifier|public
name|JLabel
name|getPreview
parameter_list|()
block|{
return|return
name|preview
return|;
block|}
block|}
DECL|class|PreviewRenderer
specifier|static
class|class
name|PreviewRenderer
implements|implements
name|TableCellRenderer
block|{
DECL|field|label
specifier|private
specifier|final
name|JLabel
name|label
init|=
operator|new
name|JLabel
argument_list|()
decl_stmt|;
annotation|@
name|Override
DECL|method|getTableCellRendererComponent (JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
specifier|public
name|Component
name|getTableCellRendererComponent
parameter_list|(
name|JTable
name|table
parameter_list|,
name|Object
name|value
parameter_list|,
name|boolean
name|isSelected
parameter_list|,
name|boolean
name|hasFocus
parameter_list|,
name|int
name|row
parameter_list|,
name|int
name|column
parameter_list|)
block|{
name|JLabel
name|valueLabel
init|=
operator|(
name|JLabel
operator|)
name|value
decl_stmt|;
name|label
operator|.
name|setText
argument_list|(
name|valueLabel
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|label
return|;
block|}
block|}
DECL|class|EntryTable
class|class
name|EntryTable
extends|extends
name|JTable
block|{
DECL|field|renderer
specifier|private
specifier|final
name|PreviewRenderer
name|renderer
init|=
operator|new
name|PreviewRenderer
argument_list|()
decl_stmt|;
DECL|method|EntryTable (TableModel model)
specifier|public
name|EntryTable
parameter_list|(
name|TableModel
name|model
parameter_list|)
block|{
name|super
argument_list|(
name|model
argument_list|)
expr_stmt|;
name|getTableHeader
argument_list|()
operator|.
name|setReorderingAllowed
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|getCellRenderer (int row, int column)
specifier|public
name|TableCellRenderer
name|getCellRenderer
parameter_list|(
name|int
name|row
parameter_list|,
name|int
name|column
parameter_list|)
block|{
return|return
name|column
operator|==
literal|0
condition|?
name|getDefaultRenderer
argument_list|(
name|Boolean
operator|.
name|class
argument_list|)
else|:
name|renderer
return|;
block|}
comment|/*          * public TableCellEditor getCellEditor() { return          * getDefaultEditor(Boolean.class); }          */
annotation|@
name|Override
DECL|method|getColumnClass (int col)
specifier|public
name|Class
argument_list|<
name|?
argument_list|>
name|getColumnClass
parameter_list|(
name|int
name|col
parameter_list|)
block|{
if|if
condition|(
name|col
operator|==
literal|0
condition|)
block|{
return|return
name|Boolean
operator|.
name|class
return|;
block|}
else|else
block|{
return|return
name|JLabel
operator|.
name|class
return|;
block|}
block|}
annotation|@
name|Override
DECL|method|isCellEditable (int row, int column)
specifier|public
name|boolean
name|isCellEditable
parameter_list|(
name|int
name|row
parameter_list|,
name|int
name|column
parameter_list|)
block|{
return|return
name|column
operator|==
literal|0
return|;
block|}
annotation|@
name|Override
DECL|method|setValueAt (Object value, int row, int column)
specifier|public
name|void
name|setValueAt
parameter_list|(
name|Object
name|value
parameter_list|,
name|int
name|row
parameter_list|,
name|int
name|column
parameter_list|)
block|{
comment|// Only column 0, which is controlled by BibEntry.searchHit, is
comment|// editable:
name|entries
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
name|TableEntry
name|entry
init|=
name|entries
operator|.
name|get
argument_list|(
name|row
argument_list|)
decl_stmt|;
name|entry
operator|.
name|setWanted
argument_list|(
operator|(
name|Boolean
operator|)
name|value
argument_list|)
expr_stmt|;
name|entries
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
block|}
DECL|class|EntryTableFormat
specifier|private
specifier|static
class|class
name|EntryTableFormat
implements|implements
name|TableFormat
argument_list|<
name|TableEntry
argument_list|>
block|{
annotation|@
name|Override
DECL|method|getColumnCount ()
specifier|public
name|int
name|getColumnCount
parameter_list|()
block|{
return|return
literal|2
return|;
block|}
annotation|@
name|Override
DECL|method|getColumnName (int i)
specifier|public
name|String
name|getColumnName
parameter_list|(
name|int
name|i
parameter_list|)
block|{
if|if
condition|(
name|i
operator|==
literal|0
condition|)
block|{
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"Keep"
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"Preview"
argument_list|)
return|;
block|}
block|}
annotation|@
name|Override
DECL|method|getColumnValue (TableEntry entry, int i)
specifier|public
name|Object
name|getColumnValue
parameter_list|(
name|TableEntry
name|entry
parameter_list|,
name|int
name|i
parameter_list|)
block|{
if|if
condition|(
name|i
operator|==
literal|0
condition|)
block|{
return|return
name|entry
operator|.
name|isWanted
argument_list|()
condition|?
name|Boolean
operator|.
name|TRUE
else|:
name|Boolean
operator|.
name|FALSE
return|;
block|}
else|else
block|{
return|return
name|entry
operator|.
name|getPreview
argument_list|()
return|;
block|}
block|}
block|}
DECL|method|isOkPressed ()
specifier|public
name|boolean
name|isOkPressed
parameter_list|()
block|{
return|return
name|okPressed
return|;
block|}
annotation|@
name|Override
DECL|method|setStatus (String s)
specifier|public
name|void
name|setStatus
parameter_list|(
name|String
name|s
parameter_list|)
block|{
name|frame
operator|.
name|setStatus
argument_list|(
name|s
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|showMessage (String message, String title, int msgType)
specifier|public
name|void
name|showMessage
parameter_list|(
name|String
name|message
parameter_list|,
name|String
name|title
parameter_list|,
name|int
name|msgType
parameter_list|)
block|{
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|this
argument_list|,
name|message
argument_list|,
name|title
argument_list|,
name|msgType
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|showMessage (String message)
specifier|public
name|void
name|showMessage
parameter_list|(
name|String
name|message
parameter_list|)
block|{
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|this
argument_list|,
name|message
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

