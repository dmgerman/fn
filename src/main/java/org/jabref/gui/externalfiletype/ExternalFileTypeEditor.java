begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.externalfiletype
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|externalfiletype
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
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|MouseAdapter
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
name|MouseEvent
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
name|Collection
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
name|ImageIcon
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
name|JFrame
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
name|TableCellRenderer
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
name|IconTheme
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
name|JabRefDialog
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
name|JabRefIcon
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
name|actions
operator|.
name|MnemonicAwareAction
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
name|l10n
operator|.
name|Localization
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
comment|/**  * Editor for external file types.  */
end_comment

begin_class
DECL|class|ExternalFileTypeEditor
specifier|public
class|class
name|ExternalFileTypeEditor
extends|extends
name|JabRefDialog
block|{
DECL|field|frame
specifier|private
name|JFrame
name|frame
decl_stmt|;
DECL|field|dialog
specifier|private
name|JDialog
name|dialog
decl_stmt|;
DECL|field|fileTypes
specifier|private
name|List
argument_list|<
name|ExternalFileType
argument_list|>
name|fileTypes
decl_stmt|;
DECL|field|table
specifier|private
name|JTable
name|table
decl_stmt|;
DECL|field|entryEditor
specifier|private
name|ExternalFileTypeEntryEditor
name|entryEditor
decl_stmt|;
DECL|field|tableModel
specifier|private
name|FileTypeTableModel
name|tableModel
decl_stmt|;
DECL|field|ok
specifier|private
specifier|final
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
DECL|field|cancel
specifier|private
specifier|final
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
DECL|field|add
specifier|private
specifier|final
name|JButton
name|add
init|=
operator|new
name|JButton
argument_list|(
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|ADD_NOBOX
operator|.
name|getIcon
argument_list|()
argument_list|)
decl_stmt|;
DECL|field|remove
specifier|private
specifier|final
name|JButton
name|remove
init|=
operator|new
name|JButton
argument_list|(
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|REMOVE_NOBOX
operator|.
name|getIcon
argument_list|()
argument_list|)
decl_stmt|;
DECL|field|edit
specifier|private
specifier|final
name|JButton
name|edit
init|=
operator|new
name|JButton
argument_list|(
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|EDIT
operator|.
name|getIcon
argument_list|()
argument_list|)
decl_stmt|;
DECL|field|toDefaults
specifier|private
specifier|final
name|JButton
name|toDefaults
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Default"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|editListener
specifier|private
specifier|final
name|EditListener
name|editListener
init|=
operator|new
name|EditListener
argument_list|()
decl_stmt|;
DECL|method|ExternalFileTypeEditor (JFrame frame)
specifier|private
name|ExternalFileTypeEditor
parameter_list|(
name|JFrame
name|frame
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
literal|"Manage external file types"
argument_list|)
argument_list|,
literal|true
argument_list|,
name|ExternalFileTypeEditor
operator|.
name|class
argument_list|)
expr_stmt|;
name|this
operator|.
name|frame
operator|=
name|frame
expr_stmt|;
name|init
argument_list|()
expr_stmt|;
block|}
DECL|method|ExternalFileTypeEditor (JDialog dialog)
specifier|private
name|ExternalFileTypeEditor
parameter_list|(
name|JDialog
name|dialog
parameter_list|)
block|{
name|super
argument_list|(
name|dialog
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Manage external file types"
argument_list|)
argument_list|,
literal|true
argument_list|,
name|ExternalFileTypeEditor
operator|.
name|class
argument_list|)
expr_stmt|;
name|this
operator|.
name|dialog
operator|=
name|dialog
expr_stmt|;
name|init
argument_list|()
expr_stmt|;
block|}
comment|/**      * Update the editor to show the current settings in Preferences.      */
DECL|method|setValues ()
specifier|private
name|void
name|setValues
parameter_list|()
block|{
name|fileTypes
operator|.
name|clear
argument_list|()
expr_stmt|;
name|Collection
argument_list|<
name|ExternalFileType
argument_list|>
name|types
init|=
name|ExternalFileTypes
operator|.
name|getInstance
argument_list|()
operator|.
name|getExternalFileTypeSelection
argument_list|()
decl_stmt|;
for|for
control|(
name|ExternalFileType
name|type
range|:
name|types
control|)
block|{
name|fileTypes
operator|.
name|add
argument_list|(
name|type
operator|.
name|copy
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|Collections
operator|.
name|sort
argument_list|(
name|fileTypes
argument_list|)
expr_stmt|;
block|}
comment|/**      * Store the list of external entry types to Preferences.      */
DECL|method|storeSettings ()
specifier|private
name|void
name|storeSettings
parameter_list|()
block|{
name|ExternalFileTypes
operator|.
name|getInstance
argument_list|()
operator|.
name|setExternalFileTypes
argument_list|(
name|fileTypes
argument_list|)
expr_stmt|;
block|}
DECL|method|init ()
specifier|private
name|void
name|init
parameter_list|()
block|{
name|ok
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
block|{
name|storeSettings
argument_list|()
expr_stmt|;
name|dispose
argument_list|()
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
name|Action
name|cancelAction
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
name|cancel
operator|.
name|addActionListener
argument_list|(
name|cancelAction
argument_list|)
expr_stmt|;
comment|// The toDefaults resets the entire list to its default values.
name|toDefaults
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
block|{
comment|/*int reply = JOptionPane.showConfirmDialog(ExternalFileTypeEditor.this,                     Globals.lang("All custom file types will be lost. Proceed?"),                     Globals.lang("Reset file type definitions"), JOptionPane.YES_NO_OPTION,                     JOptionPane.QUESTION_MESSAGE);*/
comment|//if (reply == JOptionPane.YES_OPTION) {
name|List
argument_list|<
name|ExternalFileType
argument_list|>
name|list
init|=
name|ExternalFileTypes
operator|.
name|getDefaultExternalFileTypes
argument_list|()
decl_stmt|;
name|fileTypes
operator|.
name|clear
argument_list|()
expr_stmt|;
name|fileTypes
operator|.
name|addAll
argument_list|(
name|list
argument_list|)
expr_stmt|;
name|Collections
operator|.
name|sort
argument_list|(
name|fileTypes
argument_list|)
expr_stmt|;
comment|//Globals.prefs.resetExternalFileTypesToDefault();
comment|//setValues();
name|tableModel
operator|.
name|fireTableDataChanged
argument_list|()
expr_stmt|;
comment|//}
block|}
argument_list|)
expr_stmt|;
name|add
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
block|{
comment|// Generate a new file type:
name|ExternalFileType
name|type
init|=
operator|new
name|ExternalFileType
argument_list|(
literal|""
argument_list|,
literal|""
argument_list|,
literal|""
argument_list|,
literal|""
argument_list|,
literal|"new"
argument_list|,
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|FILE
argument_list|)
decl_stmt|;
comment|// Show the file type editor:
name|getEditor
argument_list|(
name|type
argument_list|)
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
if|if
condition|(
name|entryEditor
operator|.
name|okPressed
argument_list|()
condition|)
block|{
comment|// Ok was pressed. Add the new file type and update the table:
name|fileTypes
operator|.
name|add
argument_list|(
name|type
argument_list|)
expr_stmt|;
name|tableModel
operator|.
name|fireTableDataChanged
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|remove
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
block|{
name|int
index|[]
name|rows
init|=
name|table
operator|.
name|getSelectedRows
argument_list|()
decl_stmt|;
if|if
condition|(
name|rows
operator|.
name|length
operator|==
literal|0
condition|)
block|{
return|return;
block|}
for|for
control|(
name|int
name|i
init|=
name|rows
operator|.
name|length
operator|-
literal|1
init|;
name|i
operator|>=
literal|0
condition|;
name|i
operator|--
control|)
block|{
name|fileTypes
operator|.
name|remove
argument_list|(
name|rows
index|[
name|i
index|]
argument_list|)
expr_stmt|;
block|}
name|tableModel
operator|.
name|fireTableDataChanged
argument_list|()
expr_stmt|;
if|if
condition|(
operator|!
name|fileTypes
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|int
name|row
init|=
name|Math
operator|.
name|min
argument_list|(
name|rows
index|[
literal|0
index|]
argument_list|,
name|fileTypes
operator|.
name|size
argument_list|()
operator|-
literal|1
argument_list|)
decl_stmt|;
name|table
operator|.
name|setRowSelectionInterval
argument_list|(
name|row
argument_list|,
name|row
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|edit
operator|.
name|addActionListener
argument_list|(
name|editListener
argument_list|)
expr_stmt|;
name|fileTypes
operator|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
expr_stmt|;
name|setValues
argument_list|()
expr_stmt|;
name|tableModel
operator|=
operator|new
name|FileTypeTableModel
argument_list|()
expr_stmt|;
name|table
operator|=
operator|new
name|JTable
argument_list|(
name|tableModel
argument_list|)
expr_stmt|;
name|table
operator|.
name|setDefaultRenderer
argument_list|(
name|ImageIcon
operator|.
name|class
argument_list|,
operator|new
name|IconRenderer
argument_list|()
argument_list|)
expr_stmt|;
name|table
operator|.
name|addMouseListener
argument_list|(
operator|new
name|TableClickListener
argument_list|()
argument_list|)
expr_stmt|;
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
name|setMaxWidth
argument_list|(
literal|24
argument_list|)
expr_stmt|;
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
name|setMinWidth
argument_list|(
literal|24
argument_list|)
expr_stmt|;
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
name|setMinWidth
argument_list|(
literal|170
argument_list|)
expr_stmt|;
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
name|setMinWidth
argument_list|(
literal|60
argument_list|)
expr_stmt|;
name|table
operator|.
name|getColumnModel
argument_list|()
operator|.
name|getColumn
argument_list|(
literal|3
argument_list|)
operator|.
name|setMinWidth
argument_list|(
literal|100
argument_list|)
expr_stmt|;
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
name|setResizable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|JScrollPane
name|sp
init|=
operator|new
name|JScrollPane
argument_list|(
name|table
argument_list|)
decl_stmt|;
name|JPanel
name|upper
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|upper
operator|.
name|setLayout
argument_list|(
operator|new
name|BorderLayout
argument_list|()
argument_list|)
expr_stmt|;
name|upper
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
name|upper
operator|.
name|add
argument_list|(
name|sp
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
name|upper
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|ButtonStackBuilder
name|bs
init|=
operator|new
name|ButtonStackBuilder
argument_list|()
decl_stmt|;
name|bs
operator|.
name|addButton
argument_list|(
name|add
argument_list|)
expr_stmt|;
name|bs
operator|.
name|addButton
argument_list|(
name|remove
argument_list|)
expr_stmt|;
name|bs
operator|.
name|addButton
argument_list|(
name|edit
argument_list|)
expr_stmt|;
name|bs
operator|.
name|addRelatedGap
argument_list|()
expr_stmt|;
name|bs
operator|.
name|addButton
argument_list|(
name|toDefaults
argument_list|)
expr_stmt|;
name|upper
operator|.
name|add
argument_list|(
name|bs
operator|.
name|getPanel
argument_list|()
argument_list|,
name|BorderLayout
operator|.
name|EAST
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
name|pack
argument_list|()
expr_stmt|;
comment|// Key bindings:
name|ActionMap
name|am
init|=
name|upper
operator|.
name|getActionMap
argument_list|()
decl_stmt|;
name|InputMap
name|im
init|=
name|upper
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
name|cancelAction
argument_list|)
expr_stmt|;
name|am
operator|=
name|bb
operator|.
name|getPanel
argument_list|()
operator|.
name|getActionMap
argument_list|()
expr_stmt|;
name|im
operator|=
name|bb
operator|.
name|getPanel
argument_list|()
operator|.
name|getInputMap
argument_list|(
name|JComponent
operator|.
name|WHEN_IN_FOCUSED_WINDOW
argument_list|)
expr_stmt|;
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
name|cancelAction
argument_list|)
expr_stmt|;
if|if
condition|(
name|frame
operator|==
literal|null
condition|)
block|{
name|setLocationRelativeTo
argument_list|(
name|dialog
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|setLocationRelativeTo
argument_list|(
name|frame
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|getEditor (ExternalFileType type)
specifier|private
name|ExternalFileTypeEntryEditor
name|getEditor
parameter_list|(
name|ExternalFileType
name|type
parameter_list|)
block|{
if|if
condition|(
name|entryEditor
operator|==
literal|null
condition|)
block|{
name|entryEditor
operator|=
operator|new
name|ExternalFileTypeEntryEditor
argument_list|(
name|ExternalFileTypeEditor
operator|.
name|this
argument_list|,
name|type
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|entryEditor
operator|.
name|setEntry
argument_list|(
name|type
argument_list|)
expr_stmt|;
block|}
return|return
name|entryEditor
return|;
block|}
comment|/**      * Get an AbstractAction for opening the external file types editor.      * @param frame The JFrame used as parent window for the dialog.      * @return An Action for opening the editor.      */
DECL|method|getAction (JabRefFrame frame)
specifier|public
specifier|static
name|AbstractAction
name|getAction
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|)
block|{
return|return
operator|new
name|EditExternalFileTypesAction
argument_list|(
name|frame
argument_list|)
return|;
block|}
comment|/**      * Get an AbstractAction for opening the external file types editor.      * @param dialog The JDialog used as parent window for the dialog.      * @return An Action for opening the editor.      */
DECL|method|getAction (JDialog dialog)
specifier|public
specifier|static
name|AbstractAction
name|getAction
parameter_list|(
name|JDialog
name|dialog
parameter_list|)
block|{
return|return
operator|new
name|EditExternalFileTypesAction
argument_list|(
name|dialog
argument_list|)
return|;
block|}
DECL|class|EditListener
class|class
name|EditListener
implements|implements
name|ActionListener
block|{
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
name|int
index|[]
name|rows
init|=
name|table
operator|.
name|getSelectedRows
argument_list|()
decl_stmt|;
if|if
condition|(
name|rows
operator|.
name|length
operator|!=
literal|1
condition|)
block|{
return|return;
block|}
name|getEditor
argument_list|(
name|fileTypes
operator|.
name|get
argument_list|(
name|rows
index|[
literal|0
index|]
argument_list|)
argument_list|)
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
if|if
condition|(
name|entryEditor
operator|.
name|okPressed
argument_list|()
condition|)
block|{
name|tableModel
operator|.
name|fireTableDataChanged
argument_list|()
expr_stmt|;
block|}
block|}
block|}
DECL|class|IconRenderer
specifier|static
class|class
name|IconRenderer
implements|implements
name|TableCellRenderer
block|{
DECL|field|lab
specifier|private
specifier|final
name|JLabel
name|lab
init|=
operator|new
name|JLabel
argument_list|()
decl_stmt|;
annotation|@
name|Override
DECL|method|getTableCellRendererComponent (JTable tab, Object value, boolean isSelected, boolean hasFocus, int row, int column)
specifier|public
name|Component
name|getTableCellRendererComponent
parameter_list|(
name|JTable
name|tab
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
name|lab
operator|.
name|setText
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|lab
operator|.
name|setIcon
argument_list|(
operator|(
operator|(
name|JabRefIcon
operator|)
name|value
operator|)
operator|.
name|getIcon
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|lab
return|;
block|}
block|}
DECL|class|FileTypeTableModel
specifier|private
class|class
name|FileTypeTableModel
extends|extends
name|AbstractTableModel
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
literal|5
return|;
block|}
annotation|@
name|Override
DECL|method|getRowCount ()
specifier|public
name|int
name|getRowCount
parameter_list|()
block|{
return|return
name|fileTypes
operator|.
name|size
argument_list|()
return|;
block|}
annotation|@
name|Override
DECL|method|getColumnName (int column)
specifier|public
name|String
name|getColumnName
parameter_list|(
name|int
name|column
parameter_list|)
block|{
switch|switch
condition|(
name|column
condition|)
block|{
case|case
literal|0
case|:
return|return
literal|" "
return|;
case|case
literal|1
case|:
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"Name"
argument_list|)
return|;
case|case
literal|2
case|:
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"Extension"
argument_list|)
return|;
case|case
literal|3
case|:
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"MIME type"
argument_list|)
return|;
default|default:
comment|// Five columns
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"Application"
argument_list|)
return|;
block|}
block|}
annotation|@
name|Override
DECL|method|getColumnClass (int columnIndex)
specifier|public
name|Class
argument_list|<
name|?
argument_list|>
name|getColumnClass
parameter_list|(
name|int
name|columnIndex
parameter_list|)
block|{
if|if
condition|(
name|columnIndex
operator|==
literal|0
condition|)
block|{
return|return
name|ImageIcon
operator|.
name|class
return|;
block|}
else|else
block|{
return|return
name|String
operator|.
name|class
return|;
block|}
block|}
annotation|@
name|Override
DECL|method|getValueAt (int rowIndex, int columnIndex)
specifier|public
name|Object
name|getValueAt
parameter_list|(
name|int
name|rowIndex
parameter_list|,
name|int
name|columnIndex
parameter_list|)
block|{
name|ExternalFileType
name|type
init|=
name|fileTypes
operator|.
name|get
argument_list|(
name|rowIndex
argument_list|)
decl_stmt|;
switch|switch
condition|(
name|columnIndex
condition|)
block|{
case|case
literal|0
case|:
return|return
name|type
operator|.
name|getIcon
argument_list|()
return|;
case|case
literal|1
case|:
return|return
name|type
operator|.
name|getName
argument_list|()
return|;
case|case
literal|2
case|:
return|return
name|type
operator|.
name|getExtension
argument_list|()
return|;
case|case
literal|3
case|:
return|return
name|type
operator|.
name|getMimeType
argument_list|()
return|;
default|default:
return|return
name|type
operator|.
name|getOpenWithApplication
argument_list|()
return|;
block|}
block|}
block|}
DECL|class|TableClickListener
class|class
name|TableClickListener
extends|extends
name|MouseAdapter
block|{
DECL|method|handleClick (MouseEvent e)
specifier|private
name|void
name|handleClick
parameter_list|(
name|MouseEvent
name|e
parameter_list|)
block|{
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
name|editListener
operator|.
name|actionPerformed
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
DECL|method|mouseClicked (MouseEvent e)
specifier|public
name|void
name|mouseClicked
parameter_list|(
name|MouseEvent
name|e
parameter_list|)
block|{
name|handleClick
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|mousePressed (MouseEvent e)
specifier|public
name|void
name|mousePressed
parameter_list|(
name|MouseEvent
name|e
parameter_list|)
block|{
name|handleClick
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|mouseReleased (MouseEvent e)
specifier|public
name|void
name|mouseReleased
parameter_list|(
name|MouseEvent
name|e
parameter_list|)
block|{
name|handleClick
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
block|}
DECL|class|EditExternalFileTypesAction
specifier|public
specifier|static
class|class
name|EditExternalFileTypesAction
extends|extends
name|MnemonicAwareAction
block|{
DECL|field|frame
specifier|private
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|dialog
specifier|private
name|JDialog
name|dialog
decl_stmt|;
DECL|field|editor
specifier|private
name|ExternalFileTypeEditor
name|editor
decl_stmt|;
DECL|method|EditExternalFileTypesAction (JabRefFrame frame)
specifier|public
name|EditExternalFileTypesAction
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|)
block|{
name|super
argument_list|()
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
literal|"Manage external file types"
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|frame
operator|=
name|frame
expr_stmt|;
block|}
DECL|method|EditExternalFileTypesAction (JDialog dialog)
specifier|public
name|EditExternalFileTypesAction
parameter_list|(
name|JDialog
name|dialog
parameter_list|)
block|{
name|super
argument_list|()
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
literal|"Manage external file types"
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|dialog
operator|=
name|dialog
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
if|if
condition|(
name|editor
operator|==
literal|null
condition|)
block|{
if|if
condition|(
name|frame
operator|==
literal|null
condition|)
block|{
name|editor
operator|=
operator|new
name|ExternalFileTypeEditor
argument_list|(
name|dialog
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|editor
operator|=
operator|new
name|ExternalFileTypeEditor
argument_list|(
name|frame
argument_list|)
expr_stmt|;
block|}
block|}
name|editor
operator|.
name|setValues
argument_list|()
expr_stmt|;
name|editor
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

