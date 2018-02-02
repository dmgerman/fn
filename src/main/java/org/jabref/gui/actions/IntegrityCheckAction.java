begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.actions
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|actions
package|;
end_package

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
name|HashMap
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
name|Map
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
name|JButton
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JCheckBoxMenuItem
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
name|JFrame
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
name|JPopupMenu
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JProgressBar
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
name|ListSelectionModel
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|RowFilter
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|SwingWorker
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
name|AbstractTableModel
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
name|javax
operator|.
name|swing
operator|.
name|table
operator|.
name|TableRowSorter
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|Globals
import|;
end_import

begin_import
import|import
name|org
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
name|org
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
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|integrity
operator|.
name|IntegrityCheck
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|integrity
operator|.
name|IntegrityMessage
import|;
end_import

begin_import
import|import
name|org
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
name|org
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
name|com
operator|.
name|jgoodies
operator|.
name|forms
operator|.
name|builder
operator|.
name|FormBuilder
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
name|layout
operator|.
name|FormLayout
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|Logger
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|LoggerFactory
import|;
end_import

begin_class
DECL|class|IntegrityCheckAction
specifier|public
class|class
name|IntegrityCheckAction
extends|extends
name|MnemonicAwareAction
block|{
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Logger
name|LOGGER
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|IntegrityCheckAction
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|ELLIPSES
specifier|private
specifier|static
specifier|final
name|String
name|ELLIPSES
init|=
literal|"..."
decl_stmt|;
DECL|field|frame
specifier|private
specifier|final
name|JabRefFrame
name|frame
decl_stmt|;
DECL|method|IntegrityCheckAction (JabRefFrame frame)
specifier|public
name|IntegrityCheckAction
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|)
block|{
name|this
operator|.
name|frame
operator|=
name|frame
expr_stmt|;
name|putValue
argument_list|(
name|Action
operator|.
name|NAME
argument_list|,
name|Localization
operator|.
name|menuTitle
argument_list|(
literal|"Check integrity"
argument_list|)
operator|+
name|ELLIPSES
argument_list|)
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
name|KeyBinding
operator|.
name|CHECK_INTEGRITY
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
name|IntegrityCheck
name|check
init|=
operator|new
name|IntegrityCheck
argument_list|(
name|frame
operator|.
name|getCurrentBasePanel
argument_list|()
operator|.
name|getBibDatabaseContext
argument_list|()
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getFileDirectoryPreferences
argument_list|()
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getBibtexKeyPatternPreferences
argument_list|()
argument_list|,
name|Globals
operator|.
name|journalAbbreviationLoader
operator|.
name|getRepository
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getJournalAbbreviationPreferences
argument_list|()
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
name|ENFORCE_LEGAL_BIBTEX_KEY
argument_list|)
argument_list|)
decl_stmt|;
specifier|final
name|JDialog
name|integrityDialog
init|=
operator|new
name|JDialog
argument_list|(
operator|(
name|JFrame
operator|)
literal|null
argument_list|,
literal|true
argument_list|)
decl_stmt|;
name|integrityDialog
operator|.
name|setUndecorated
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|JProgressBar
name|integrityProgressBar
init|=
operator|new
name|JProgressBar
argument_list|()
decl_stmt|;
name|integrityProgressBar
operator|.
name|setIndeterminate
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|integrityProgressBar
operator|.
name|setStringPainted
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|integrityProgressBar
operator|.
name|setString
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Checking integrity..."
argument_list|)
argument_list|)
expr_stmt|;
name|integrityDialog
operator|.
name|add
argument_list|(
name|integrityProgressBar
argument_list|)
expr_stmt|;
name|integrityDialog
operator|.
name|pack
argument_list|()
expr_stmt|;
name|SwingWorker
argument_list|<
name|List
argument_list|<
name|IntegrityMessage
argument_list|>
argument_list|,
name|Void
argument_list|>
name|worker
init|=
operator|new
name|SwingWorker
argument_list|<
name|List
argument_list|<
name|IntegrityMessage
argument_list|>
argument_list|,
name|Void
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|protected
name|List
argument_list|<
name|IntegrityMessage
argument_list|>
name|doInBackground
parameter_list|()
block|{
name|List
argument_list|<
name|IntegrityMessage
argument_list|>
name|messages
init|=
name|check
operator|.
name|checkBibtexDatabase
argument_list|()
decl_stmt|;
return|return
name|messages
return|;
block|}
annotation|@
name|Override
specifier|protected
name|void
name|done
parameter_list|()
block|{
name|integrityDialog
operator|.
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
decl_stmt|;
name|worker
operator|.
name|execute
argument_list|()
expr_stmt|;
name|integrityDialog
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|IntegrityMessage
argument_list|>
name|messages
init|=
literal|null
decl_stmt|;
try|try
block|{
name|messages
operator|=
name|worker
operator|.
name|get
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|ex
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Integrity check failed."
argument_list|,
name|ex
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|messages
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
literal|null
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"No problems found."
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|Boolean
argument_list|>
name|showMessage
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
comment|// prepare data model
name|Object
index|[]
index|[]
name|model
init|=
operator|new
name|Object
index|[
name|messages
operator|.
name|size
argument_list|()
index|]
index|[
literal|4
index|]
decl_stmt|;
name|int
name|i
init|=
literal|0
decl_stmt|;
for|for
control|(
name|IntegrityMessage
name|message
range|:
name|messages
control|)
block|{
name|model
index|[
name|i
index|]
index|[
literal|0
index|]
operator|=
name|message
operator|.
name|getEntry
argument_list|()
operator|.
name|getId
argument_list|()
expr_stmt|;
name|model
index|[
name|i
index|]
index|[
literal|1
index|]
operator|=
name|message
operator|.
name|getEntry
argument_list|()
operator|.
name|getCiteKeyOptional
argument_list|()
operator|.
name|orElse
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|model
index|[
name|i
index|]
index|[
literal|2
index|]
operator|=
name|message
operator|.
name|getFieldName
argument_list|()
expr_stmt|;
name|model
index|[
name|i
index|]
index|[
literal|3
index|]
operator|=
name|message
operator|.
name|getMessage
argument_list|()
expr_stmt|;
name|showMessage
operator|.
name|put
argument_list|(
name|message
operator|.
name|getMessage
argument_list|()
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|i
operator|++
expr_stmt|;
block|}
comment|// construct view
name|JTable
name|table
init|=
operator|new
name|JTable
argument_list|(
name|model
argument_list|,
operator|new
name|Object
index|[]
block|{
literal|"ID"
block|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"BibTeX key"
argument_list|)
block|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Field"
argument_list|)
block|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Message"
argument_list|)
block|}
argument_list|)
decl_stmt|;
comment|// hide IDs
name|TableColumnModel
name|columnModel
init|=
name|table
operator|.
name|getColumnModel
argument_list|()
decl_stmt|;
name|columnModel
operator|.
name|removeColumn
argument_list|(
name|columnModel
operator|.
name|getColumn
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|RowFilter
argument_list|<
name|Object
argument_list|,
name|Object
argument_list|>
name|filter
init|=
operator|new
name|RowFilter
argument_list|<
name|Object
argument_list|,
name|Object
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|boolean
name|include
parameter_list|(
name|Entry
argument_list|<
name|?
argument_list|,
name|?
argument_list|>
name|entry
parameter_list|)
block|{
return|return
name|showMessage
operator|.
name|get
argument_list|(
name|entry
operator|.
name|getStringValue
argument_list|(
literal|3
argument_list|)
argument_list|)
return|;
block|}
block|}
decl_stmt|;
name|TableRowSorter
argument_list|<
name|TableModel
argument_list|>
name|sorter
init|=
operator|new
name|TableRowSorter
argument_list|<>
argument_list|(
name|table
operator|.
name|getModel
argument_list|()
argument_list|)
decl_stmt|;
name|sorter
operator|.
name|setRowFilter
argument_list|(
name|filter
argument_list|)
expr_stmt|;
name|table
operator|.
name|setRowSorter
argument_list|(
name|sorter
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
name|table
operator|.
name|setDefaultEditor
argument_list|(
name|Object
operator|.
name|class
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|ListSelectionModel
name|selectionModel
init|=
name|table
operator|.
name|getSelectionModel
argument_list|()
decl_stmt|;
name|selectionModel
operator|.
name|addListSelectionListener
argument_list|(
name|event
lambda|->
block|{
if|if
condition|(
operator|!
name|event
operator|.
name|getValueIsAdjusting
argument_list|()
condition|)
block|{
try|try
block|{
name|String
name|entryId
init|=
operator|(
name|String
operator|)
name|model
index|[
name|table
operator|.
name|convertRowIndexToModel
argument_list|(
name|table
operator|.
name|getSelectedRow
argument_list|()
argument_list|)
index|]
index|[
literal|0
index|]
decl_stmt|;
name|String
name|fieldName
init|=
operator|(
name|String
operator|)
name|model
index|[
name|table
operator|.
name|convertRowIndexToModel
argument_list|(
name|table
operator|.
name|getSelectedRow
argument_list|()
argument_list|)
index|]
index|[
literal|2
index|]
decl_stmt|;
name|frame
operator|.
name|getCurrentBasePanel
argument_list|()
operator|.
name|editEntryByIdAndFocusField
argument_list|(
name|entryId
argument_list|,
name|fieldName
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ArrayIndexOutOfBoundsException
name|exception
parameter_list|)
block|{
comment|// Ignore -- most likely caused by filtering out the earlier selected row
block|}
block|}
block|}
argument_list|)
expr_stmt|;
comment|// BibTeX key
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
literal|100
argument_list|)
expr_stmt|;
comment|// field name
name|table
operator|.
name|getColumnModel
argument_list|()
operator|.
name|getColumn
argument_list|(
literal|1
argument_list|)
operator|.
name|setPreferredWidth
argument_list|(
literal|60
argument_list|)
expr_stmt|;
comment|// message
name|table
operator|.
name|getColumnModel
argument_list|()
operator|.
name|getColumn
argument_list|(
literal|2
argument_list|)
operator|.
name|setPreferredWidth
argument_list|(
literal|400
argument_list|)
expr_stmt|;
name|table
operator|.
name|setAutoResizeMode
argument_list|(
name|JTable
operator|.
name|AUTO_RESIZE_LAST_COLUMN
argument_list|)
expr_stmt|;
name|JScrollPane
name|scrollPane
init|=
operator|new
name|JScrollPane
argument_list|(
name|table
argument_list|)
decl_stmt|;
name|String
name|title
init|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"%0 problem(s) found"
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|messages
operator|.
name|size
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
name|JDialog
name|dialog
init|=
operator|new
name|JDialog
argument_list|(
operator|(
name|JFrame
operator|)
literal|null
argument_list|,
name|title
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|JPopupMenu
name|menu
init|=
operator|new
name|JPopupMenu
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|messageString
range|:
name|showMessage
operator|.
name|keySet
argument_list|()
control|)
block|{
name|JCheckBoxMenuItem
name|menuItem
init|=
operator|new
name|JCheckBoxMenuItem
argument_list|(
name|messageString
argument_list|,
literal|true
argument_list|)
decl_stmt|;
name|menuItem
operator|.
name|addActionListener
argument_list|(
name|event
lambda|->
block|{
name|showMessage
operator|.
name|put
argument_list|(
name|messageString
argument_list|,
name|menuItem
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
operator|(
operator|(
name|AbstractTableModel
operator|)
name|table
operator|.
name|getModel
argument_list|()
operator|)
operator|.
name|fireTableDataChanged
argument_list|()
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
name|menu
operator|.
name|add
argument_list|(
name|menuItem
argument_list|)
expr_stmt|;
block|}
name|JButton
name|menuButton
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Filter"
argument_list|)
argument_list|)
decl_stmt|;
name|menuButton
operator|.
name|addActionListener
argument_list|(
name|entry
lambda|->
name|menu
operator|.
name|show
argument_list|(
name|menuButton
argument_list|,
literal|0
argument_list|,
name|menuButton
operator|.
name|getHeight
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|FormBuilder
name|builder
init|=
name|FormBuilder
operator|.
name|create
argument_list|()
operator|.
name|layout
argument_list|(
operator|new
name|FormLayout
argument_list|(
literal|"fill:pref:grow"
argument_list|,
literal|"fill:pref:grow, 2dlu, pref"
argument_list|)
argument_list|)
decl_stmt|;
name|JButton
name|filterNoneButton
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Filter None"
argument_list|)
argument_list|)
decl_stmt|;
name|filterNoneButton
operator|.
name|addActionListener
argument_list|(
name|event
lambda|->
block|{
for|for
control|(
name|Component
name|component
range|:
name|menu
operator|.
name|getComponents
argument_list|()
control|)
block|{
if|if
condition|(
name|component
operator|instanceof
name|JCheckBoxMenuItem
condition|)
block|{
name|JCheckBoxMenuItem
name|checkBox
init|=
operator|(
name|JCheckBoxMenuItem
operator|)
name|component
decl_stmt|;
if|if
condition|(
name|checkBox
operator|.
name|isSelected
argument_list|()
condition|)
block|{
name|checkBox
operator|.
name|setSelected
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|showMessage
operator|.
name|put
argument_list|(
name|checkBox
operator|.
name|getText
argument_list|()
argument_list|,
name|checkBox
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
operator|(
operator|(
name|AbstractTableModel
operator|)
name|table
operator|.
name|getModel
argument_list|()
operator|)
operator|.
name|fireTableDataChanged
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|JButton
name|filterAllButton
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Filter All"
argument_list|)
argument_list|)
decl_stmt|;
name|filterAllButton
operator|.
name|addActionListener
argument_list|(
name|event
lambda|->
block|{
for|for
control|(
name|Component
name|component
range|:
name|menu
operator|.
name|getComponents
argument_list|()
control|)
block|{
if|if
condition|(
name|component
operator|instanceof
name|JCheckBoxMenuItem
condition|)
block|{
name|JCheckBoxMenuItem
name|checkBox
init|=
operator|(
name|JCheckBoxMenuItem
operator|)
name|component
decl_stmt|;
if|if
condition|(
operator|!
name|checkBox
operator|.
name|isSelected
argument_list|()
condition|)
block|{
name|checkBox
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|showMessage
operator|.
name|put
argument_list|(
name|checkBox
operator|.
name|getText
argument_list|()
argument_list|,
name|checkBox
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
operator|(
operator|(
name|AbstractTableModel
operator|)
name|table
operator|.
name|getModel
argument_list|()
operator|)
operator|.
name|fireTableDataChanged
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|filterNoneButton
argument_list|)
operator|.
name|xy
argument_list|(
literal|1
argument_list|,
literal|3
argument_list|,
literal|"left, b"
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|filterAllButton
argument_list|)
operator|.
name|xy
argument_list|(
literal|1
argument_list|,
literal|3
argument_list|,
literal|"right, b"
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|scrollPane
argument_list|)
operator|.
name|xy
argument_list|(
literal|1
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|menuButton
argument_list|)
operator|.
name|xy
argument_list|(
literal|1
argument_list|,
literal|3
argument_list|,
literal|"c, b"
argument_list|)
expr_stmt|;
name|dialog
operator|.
name|add
argument_list|(
name|builder
operator|.
name|getPanel
argument_list|()
argument_list|)
expr_stmt|;
name|dialog
operator|.
name|setSize
argument_list|(
literal|600
argument_list|,
literal|600
argument_list|)
expr_stmt|;
comment|// show view
name|dialog
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

