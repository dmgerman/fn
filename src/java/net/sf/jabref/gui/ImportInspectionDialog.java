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
name|labelPattern
operator|.
name|LabelPatternUtil
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
name|UndoableInsertEntry
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
name|imports
operator|.
name|ImportFormatReader
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
name|ListSelectionListener
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
name|ListSelectionEvent
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
name|DefaultTableModel
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
name|Iterator
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
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
name|ActionListener
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

begin_comment
comment|/**  * Created by IntelliJ IDEA.  * User: alver  * Date: 20.mar.2005  * Time: 22:02:35  * To change this template use File | Settings | File Templates.  */
end_comment

begin_class
DECL|class|ImportInspectionDialog
specifier|public
class|class
name|ImportInspectionDialog
extends|extends
name|JDialog
block|{
DECL|field|panel
specifier|private
name|BasePanel
name|panel
decl_stmt|;
DECL|field|frame
specifier|private
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|contentPane
specifier|private
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
DECL|field|tableModel
specifier|private
name|DefaultTableModel
name|tableModel
init|=
operator|new
name|MyTableModel
argument_list|()
decl_stmt|;
DECL|field|table
specifier|private
name|JTable
name|table
init|=
operator|new
name|MyTable
argument_list|(
name|tableModel
argument_list|)
decl_stmt|;
DECL|field|fields
specifier|private
name|String
index|[]
name|fields
decl_stmt|;
DECL|field|progressBar
specifier|private
name|JProgressBar
name|progressBar
init|=
operator|new
name|JProgressBar
argument_list|(
name|JProgressBar
operator|.
name|HORIZONTAL
argument_list|)
decl_stmt|;
DECL|field|ok
specifier|private
name|JButton
name|ok
init|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Ok"
argument_list|)
argument_list|)
decl_stmt|,
DECL|field|cancel
name|cancel
init|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Cancel"
argument_list|)
argument_list|)
decl_stmt|,
DECL|field|generate
name|generate
init|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Generate keys"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|entries
specifier|private
name|List
name|entries
init|=
operator|new
name|ArrayList
argument_list|()
decl_stmt|;
DECL|field|undoName
specifier|private
name|String
name|undoName
decl_stmt|;
DECL|field|callBacks
specifier|private
name|ArrayList
name|callBacks
init|=
operator|new
name|ArrayList
argument_list|()
decl_stmt|;
DECL|field|newDatabase
specifier|private
name|boolean
name|newDatabase
decl_stmt|;
DECL|field|selectAll
specifier|private
name|JButton
name|selectAll
init|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Select all"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|deselectAll
specifier|private
name|JButton
name|deselectAll
init|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Deselect all"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|stop
specifier|private
name|JButton
name|stop
init|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Stop"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|preview
specifier|private
name|PreviewPanel
name|preview
init|=
operator|new
name|PreviewPanel
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
literal|"preview1"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|previewListener
specifier|private
name|ListSelectionListener
name|previewListener
init|=
literal|null
decl_stmt|;
DECL|field|generatedKeys
specifier|private
name|boolean
name|generatedKeys
init|=
literal|false
decl_stmt|;
DECL|field|toRect
specifier|private
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
comment|/**      * Creates a dialog that displays the given set of fields in the table.      * The dialog allows another process to add entries dynamically while the dialog      * is shown.      * @param frame      * @param panel      * @param fields      */
DECL|method|ImportInspectionDialog (JabRefFrame frame, BasePanel panel, String[] fields, String undoName, boolean newDatabase)
specifier|public
name|ImportInspectionDialog
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|BasePanel
name|panel
parameter_list|,
name|String
index|[]
name|fields
parameter_list|,
name|String
name|undoName
parameter_list|,
name|boolean
name|newDatabase
parameter_list|)
block|{
name|this
operator|.
name|frame
operator|=
name|frame
expr_stmt|;
name|this
operator|.
name|panel
operator|=
name|panel
expr_stmt|;
name|this
operator|.
name|fields
operator|=
name|fields
expr_stmt|;
name|this
operator|.
name|undoName
operator|=
name|undoName
expr_stmt|;
name|this
operator|.
name|newDatabase
operator|=
name|newDatabase
expr_stmt|;
name|tableModel
operator|.
name|addColumn
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Keep"
argument_list|)
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
name|fields
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|tableModel
operator|.
name|addColumn
argument_list|(
name|Util
operator|.
name|nCase
argument_list|(
name|fields
index|[
name|i
index|]
argument_list|)
argument_list|)
expr_stmt|;
name|Object
name|o
init|=
name|GUIGlobals
operator|.
name|fieldLength
operator|.
name|get
argument_list|(
name|fields
index|[
name|i
index|]
argument_list|)
decl_stmt|;
name|int
name|width
init|=
name|o
operator|==
literal|null
condition|?
name|GUIGlobals
operator|.
name|DEFAULT_FIELD_LENGTH
else|:
operator|(
operator|(
name|Integer
operator|)
name|o
operator|)
operator|.
name|intValue
argument_list|()
decl_stmt|;
name|table
operator|.
name|getColumnModel
argument_list|()
operator|.
name|getColumn
argument_list|(
name|i
operator|+
literal|1
argument_list|)
operator|.
name|setPreferredWidth
argument_list|(
name|width
argument_list|)
expr_stmt|;
block|}
name|table
operator|.
name|getColumnModel
argument_list|()
operator|.
name|getColumn
argument_list|(
literal|0
argument_list|)
operator|.
name|setPreferredWidth
argument_list|(
literal|25
argument_list|)
expr_stmt|;
name|table
operator|.
name|setRowSelectionAllowed
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|table
operator|.
name|setSelectionMode
argument_list|(
name|ListSelectionModel
operator|.
name|SINGLE_SELECTION
argument_list|)
expr_stmt|;
comment|//table.setCellSelectionEnabled(false);
name|previewListener
operator|=
operator|new
name|TableSelectionListener
argument_list|()
expr_stmt|;
name|table
operator|.
name|getSelectionModel
argument_list|()
operator|.
name|addListSelectionListener
argument_list|(
name|previewListener
argument_list|)
expr_stmt|;
name|getContentPane
argument_list|()
operator|.
name|setLayout
argument_list|(
operator|new
name|BorderLayout
argument_list|()
argument_list|)
expr_stmt|;
name|progressBar
operator|.
name|setIndeterminate
argument_list|(
literal|true
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
name|contentPane
operator|.
name|setTopComponent
argument_list|(
operator|new
name|JScrollPane
argument_list|(
name|table
argument_list|)
argument_list|)
expr_stmt|;
name|contentPane
operator|.
name|setBottomComponent
argument_list|(
operator|new
name|JScrollPane
argument_list|(
name|preview
argument_list|)
argument_list|)
expr_stmt|;
name|centerPan
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
name|centerPan
operator|.
name|add
argument_list|(
name|progressBar
argument_list|,
name|BorderLayout
operator|.
name|SOUTH
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
name|addGridded
argument_list|(
name|ok
argument_list|)
expr_stmt|;
name|bb
operator|.
name|addGridded
argument_list|(
name|stop
argument_list|)
expr_stmt|;
name|bb
operator|.
name|addGridded
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
literal|2
argument_list|,
literal|2
argument_list|,
literal|2
argument_list|,
literal|2
argument_list|)
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
name|addGridded
argument_list|(
name|selectAll
argument_list|)
expr_stmt|;
name|builder
operator|.
name|addGridded
argument_list|(
name|deselectAll
argument_list|)
expr_stmt|;
name|builder
operator|.
name|addRelatedGap
argument_list|()
expr_stmt|;
name|builder
operator|.
name|addGridded
argument_list|(
name|generate
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
literal|2
argument_list|,
literal|2
argument_list|,
literal|2
argument_list|,
literal|2
argument_list|)
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
name|ok
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|generate
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|ok
operator|.
name|addActionListener
argument_list|(
operator|new
name|OkListener
argument_list|()
argument_list|)
expr_stmt|;
name|cancel
operator|.
name|addActionListener
argument_list|(
operator|new
name|CancelListener
argument_list|()
argument_list|)
expr_stmt|;
name|generate
operator|.
name|addActionListener
argument_list|(
operator|new
name|GenerateListener
argument_list|()
argument_list|)
expr_stmt|;
name|stop
operator|.
name|addActionListener
argument_list|(
operator|new
name|StopListener
argument_list|()
argument_list|)
expr_stmt|;
name|selectAll
operator|.
name|addActionListener
argument_list|(
operator|new
name|SelectionButton
argument_list|(
literal|true
argument_list|)
argument_list|)
expr_stmt|;
name|deselectAll
operator|.
name|addActionListener
argument_list|(
operator|new
name|SelectionButton
argument_list|(
literal|false
argument_list|)
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
name|setSize
argument_list|(
operator|new
name|Dimension
argument_list|(
literal|650
argument_list|,
literal|650
argument_list|)
argument_list|)
expr_stmt|;
comment|//contentPane.setDividerLocation(0.6f);
block|}
DECL|method|setProgress (int current, int max)
specifier|public
name|void
name|setProgress
parameter_list|(
name|int
name|current
parameter_list|,
name|int
name|max
parameter_list|)
block|{
name|progressBar
operator|.
name|setIndeterminate
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|progressBar
operator|.
name|setMinimum
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|progressBar
operator|.
name|setMaximum
argument_list|(
name|max
argument_list|)
expr_stmt|;
name|progressBar
operator|.
name|setValue
argument_list|(
name|current
argument_list|)
expr_stmt|;
block|}
comment|/**      * Add a List of entries to the table view. The table will update to show the      * added entries.      * @param entries      */
DECL|method|addEntries (List entries)
specifier|public
name|void
name|addEntries
parameter_list|(
name|List
name|entries
parameter_list|)
block|{
for|for
control|(
name|Iterator
name|i
init|=
name|entries
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
name|BibtexEntry
name|entry
init|=
operator|(
name|BibtexEntry
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|this
operator|.
name|entries
operator|.
name|add
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|Object
index|[]
name|values
init|=
operator|new
name|Object
index|[
name|tableModel
operator|.
name|getColumnCount
argument_list|()
index|]
decl_stmt|;
name|values
index|[
literal|0
index|]
operator|=
name|Boolean
operator|.
name|TRUE
expr_stmt|;
for|for
control|(
name|int
name|j
init|=
literal|0
init|;
name|j
operator|<
name|fields
operator|.
name|length
condition|;
name|j
operator|++
control|)
name|values
index|[
literal|1
operator|+
name|j
index|]
operator|=
name|entry
operator|.
name|getField
argument_list|(
name|fields
index|[
name|j
index|]
argument_list|)
expr_stmt|;
name|tableModel
operator|.
name|addRow
argument_list|(
name|values
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * When this method is called, the dialog will visually change to indicate      * that all entries are in place.      */
DECL|method|entryListComplete ()
specifier|public
name|void
name|entryListComplete
parameter_list|()
block|{
name|progressBar
operator|.
name|setIndeterminate
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|progressBar
operator|.
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|ok
operator|.
name|setEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|generatedKeys
condition|)
name|generate
operator|.
name|setEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
comment|/**      * This method returns a List containing all entries that are selected      * (checkbox checked).      * @return      */
DECL|method|getSelectedEntries ()
specifier|public
name|List
name|getSelectedEntries
parameter_list|()
block|{
name|List
name|selected
init|=
operator|new
name|ArrayList
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
name|table
operator|.
name|getRowCount
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|Boolean
name|sel
init|=
operator|(
name|Boolean
operator|)
name|table
operator|.
name|getValueAt
argument_list|(
name|i
argument_list|,
literal|0
argument_list|)
decl_stmt|;
if|if
condition|(
name|sel
operator|.
name|booleanValue
argument_list|()
condition|)
block|{
name|selected
operator|.
name|add
argument_list|(
name|entries
operator|.
name|get
argument_list|(
name|i
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|selected
return|;
block|}
comment|/**      * Generate keys for all entries. All keys will be unique with respect to one another,      * and, if they are destined for an existing database, with respect to existing keys in      * the database.      */
DECL|method|generateKeys ()
specifier|public
name|void
name|generateKeys
parameter_list|()
block|{
name|BibtexDatabase
name|database
init|=
literal|null
decl_stmt|;
comment|// Relate to the existing database, if any:
if|if
condition|(
name|panel
operator|!=
literal|null
condition|)
name|database
operator|=
name|panel
operator|.
name|database
argument_list|()
expr_stmt|;
comment|// ... or create a temporary one:
else|else
name|database
operator|=
operator|new
name|BibtexDatabase
argument_list|()
expr_stmt|;
name|List
name|keys
init|=
operator|new
name|ArrayList
argument_list|(
name|entries
operator|.
name|size
argument_list|()
argument_list|)
decl_stmt|;
comment|// Iterate over the entries, add them to the database we are working with,
comment|// and generate unique keys:
for|for
control|(
name|Iterator
name|i
init|=
name|entries
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
name|BibtexEntry
name|entry
init|=
operator|(
name|BibtexEntry
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
comment|//if (newDatabase) {
try|try
block|{
name|entry
operator|.
name|setId
argument_list|(
name|Util
operator|.
name|createNeutralId
argument_list|()
argument_list|)
expr_stmt|;
name|database
operator|.
name|insertEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|KeyCollisionException
name|ex
parameter_list|)
block|{
name|ex
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
comment|//}
name|LabelPatternUtil
operator|.
name|makeLabel
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getKeyPattern
argument_list|()
argument_list|,
name|database
argument_list|,
name|entry
argument_list|)
expr_stmt|;
comment|// Add the generated key to our list:
name|keys
operator|.
name|add
argument_list|(
name|entry
operator|.
name|getCiteKey
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|// Remove the entries from the database again, since they are not supposed to
comment|// added yet. They only needed to be in it while we generated the keys, to keep
comment|// control over key uniqueness.
for|for
control|(
name|Iterator
name|i
init|=
name|entries
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
name|BibtexEntry
name|entry
init|=
operator|(
name|BibtexEntry
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|database
operator|.
name|removeEntry
argument_list|(
name|entry
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|// Add a column to the table for displaying the generated keys:
name|tableModel
operator|.
name|addColumn
argument_list|(
literal|"Bibtexkey"
argument_list|,
name|keys
operator|.
name|toArray
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|addCallBack (CallBack cb)
specifier|public
name|void
name|addCallBack
parameter_list|(
name|CallBack
name|cb
parameter_list|)
block|{
name|callBacks
operator|.
name|add
argument_list|(
name|cb
argument_list|)
expr_stmt|;
block|}
DECL|class|OkListener
class|class
name|OkListener
implements|implements
name|ActionListener
block|{
DECL|method|actionPerformed (ActionEvent event)
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|event
parameter_list|)
block|{
specifier|final
name|List
name|selected
init|=
name|getSelectedEntries
argument_list|()
decl_stmt|;
if|if
condition|(
name|selected
operator|.
name|size
argument_list|()
operator|==
literal|0
condition|)
block|{
name|dispose
argument_list|()
expr_stmt|;
return|return;
block|}
name|NamedCompound
name|ce
init|=
operator|new
name|NamedCompound
argument_list|(
name|undoName
argument_list|)
decl_stmt|;
if|if
condition|(
name|newDatabase
condition|)
block|{
comment|// Create a new BasePanel for the entries:
name|BibtexDatabase
name|base
init|=
operator|new
name|BibtexDatabase
argument_list|()
decl_stmt|;
name|panel
operator|=
operator|new
name|BasePanel
argument_list|(
name|frame
argument_list|,
name|base
argument_list|,
literal|null
argument_list|,
operator|new
name|HashMap
argument_list|()
argument_list|,
name|Globals
operator|.
name|prefs
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|Iterator
name|i
init|=
name|selected
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
name|BibtexEntry
name|entry
init|=
operator|(
name|BibtexEntry
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|entry
operator|.
name|clone
argument_list|()
expr_stmt|;
try|try
block|{
name|entry
operator|.
name|setId
argument_list|(
name|Util
operator|.
name|createId
argument_list|(
name|entry
operator|.
name|getType
argument_list|()
argument_list|,
name|panel
operator|.
name|database
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|panel
operator|.
name|database
argument_list|()
operator|.
name|insertEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|ce
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableInsertEntry
argument_list|(
name|panel
operator|.
name|database
argument_list|()
argument_list|,
name|entry
argument_list|,
name|panel
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|KeyCollisionException
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
name|ce
operator|.
name|end
argument_list|()
expr_stmt|;
name|panel
operator|.
name|undoManager
operator|.
name|addEdit
argument_list|(
name|ce
argument_list|)
expr_stmt|;
name|dispose
argument_list|()
expr_stmt|;
name|SwingUtilities
operator|.
name|invokeLater
argument_list|(
operator|new
name|Thread
argument_list|()
block|{
specifier|public
name|void
name|run
parameter_list|()
block|{
if|if
condition|(
name|newDatabase
condition|)
block|{
name|frame
operator|.
name|addTab
argument_list|(
name|panel
argument_list|,
literal|null
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
name|panel
operator|.
name|markBaseChanged
argument_list|()
expr_stmt|;
name|panel
operator|.
name|refreshTable
argument_list|()
expr_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|callBacks
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
operator|(
operator|(
name|CallBack
operator|)
name|i
operator|.
name|next
argument_list|()
operator|)
operator|.
name|done
argument_list|(
name|selected
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|signalStopFetching ()
specifier|private
name|void
name|signalStopFetching
parameter_list|()
block|{
for|for
control|(
name|Iterator
name|i
init|=
name|callBacks
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
operator|(
operator|(
name|CallBack
operator|)
name|i
operator|.
name|next
argument_list|()
operator|)
operator|.
name|stopFetching
argument_list|()
expr_stmt|;
block|}
block|}
DECL|class|StopListener
class|class
name|StopListener
implements|implements
name|ActionListener
block|{
DECL|method|actionPerformed (ActionEvent event)
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|event
parameter_list|)
block|{
name|signalStopFetching
argument_list|()
expr_stmt|;
name|entryListComplete
argument_list|()
expr_stmt|;
block|}
block|}
DECL|class|CancelListener
class|class
name|CancelListener
implements|implements
name|ActionListener
block|{
DECL|method|actionPerformed (ActionEvent event)
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|event
parameter_list|)
block|{
name|signalStopFetching
argument_list|()
expr_stmt|;
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
DECL|class|GenerateListener
class|class
name|GenerateListener
implements|implements
name|ActionListener
block|{
DECL|method|actionPerformed (ActionEvent event)
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|event
parameter_list|)
block|{
name|generate
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|generatedKeys
operator|=
literal|true
expr_stmt|;
comment|// To prevent the button from getting enabled again.
name|generateKeys
argument_list|()
expr_stmt|;
comment|// Generate the keys.
block|}
block|}
DECL|class|MyTable
class|class
name|MyTable
extends|extends
name|JTable
block|{
DECL|method|MyTable (TableModel model)
specifier|public
name|MyTable
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
comment|//setDefaultRenderer(Boolean.class, );
block|}
DECL|method|isCellEditable (int row, int col)
specifier|public
name|boolean
name|isCellEditable
parameter_list|(
name|int
name|row
parameter_list|,
name|int
name|col
parameter_list|)
block|{
return|return
name|col
operator|==
literal|0
return|;
block|}
block|}
DECL|class|MyTableModel
class|class
name|MyTableModel
extends|extends
name|DefaultTableModel
block|{
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
operator|==
literal|0
condition|)
return|return
name|Boolean
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
block|}
DECL|class|SelectionButton
class|class
name|SelectionButton
implements|implements
name|ActionListener
block|{
DECL|field|enable
specifier|private
name|Boolean
name|enable
decl_stmt|;
DECL|method|SelectionButton (boolean enable)
specifier|public
name|SelectionButton
parameter_list|(
name|boolean
name|enable
parameter_list|)
block|{
name|this
operator|.
name|enable
operator|=
operator|new
name|Boolean
argument_list|(
name|enable
argument_list|)
expr_stmt|;
block|}
DECL|method|actionPerformed (ActionEvent event)
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|event
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
name|table
operator|.
name|getRowCount
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|table
operator|.
name|setValueAt
argument_list|(
name|enable
argument_list|,
name|i
argument_list|,
literal|0
argument_list|)
expr_stmt|;
block|}
block|}
block|}
DECL|class|TableSelectionListener
class|class
name|TableSelectionListener
implements|implements
name|ListSelectionListener
block|{
DECL|method|valueChanged (ListSelectionEvent event)
specifier|public
name|void
name|valueChanged
parameter_list|(
name|ListSelectionEvent
name|event
parameter_list|)
block|{
if|if
condition|(
name|event
operator|.
name|getValueIsAdjusting
argument_list|()
condition|)
return|return;
name|int
name|row
init|=
name|table
operator|.
name|getSelectedRow
argument_list|()
decl_stmt|;
if|if
condition|(
name|row
operator|<
literal|0
condition|)
return|return;
name|preview
operator|.
name|setEntry
argument_list|(
operator|(
name|BibtexEntry
operator|)
name|entries
operator|.
name|get
argument_list|(
name|row
argument_list|)
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
DECL|interface|CallBack
specifier|public
specifier|static
interface|interface
name|CallBack
block|{
comment|// This method is called by the dialog when the user has selected the
comment|// wanted entries, and clicked Ok. The callback object can update status
comment|// line etc.
DECL|method|done (int entriesImported)
specifier|public
name|void
name|done
parameter_list|(
name|int
name|entriesImported
parameter_list|)
function_decl|;
comment|// This method is called by the dialog when the user has cancelled or
comment|// signalled a stop. It is expected that any long-running fetch operations
comment|// will stop after this method is called.
DECL|method|stopFetching ()
specifier|public
name|void
name|stopFetching
parameter_list|()
function_decl|;
block|}
block|}
end_class

end_unit

