begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

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
name|util
operator|.
name|Util
import|;
end_import

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
name|KeyAdapter
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
name|KeyEvent
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
name|WindowAdapter
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
name|WindowEvent
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
name|Iterator
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
operator|.
name|Entry
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Set
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|TreeMap
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|Box
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|BoxLayout
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
name|JDialog
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
name|JTextField
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

begin_comment
comment|/**  * Dialog to customize key bindings  */
end_comment

begin_class
annotation|@
name|SuppressWarnings
argument_list|(
literal|"serial"
argument_list|)
DECL|class|KeyBindingsDialog
class|class
name|KeyBindingsDialog
extends|extends
name|JDialog
block|{
DECL|field|table
specifier|private
name|KeystrokeTable
name|table
decl_stmt|;
comment|//JList list = new JList();
comment|// displays the key binding of the currently selected entry
comment|// currently not displayed as it does not get updated
DECL|field|keyTF
specifier|private
specifier|final
name|JTextField
name|keyTF
init|=
operator|new
name|JTextField
argument_list|()
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
literal|"Ok"
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
DECL|field|defB
specifier|private
specifier|final
name|JButton
name|defB
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
DECL|field|grabB
specifier|private
specifier|final
name|JButton
name|grabB
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Grab"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|buttonBox
specifier|private
specifier|final
name|Box
name|buttonBox
init|=
operator|new
name|Box
argument_list|(
name|BoxLayout
operator|.
name|X_AXIS
argument_list|)
decl_stmt|;
comment|// stores the user-selected key bindings
DECL|field|bindHM
specifier|private
specifier|final
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|bindHM
decl_stmt|;
comment|// stores default key bindings
DECL|field|defBinds
specifier|private
specifier|final
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|defBinds
decl_stmt|;
DECL|field|clickedSave
specifier|private
name|boolean
name|clickedSave
decl_stmt|;
comment|/**      * Checked by the caller whether user has confirmed the change      * @return true if the user wants the keybindings to be stored      */
DECL|method|getAction ()
name|boolean
name|getAction
parameter_list|()
block|{
return|return
name|clickedSave
return|;
block|}
comment|/**      * Used by the caller to retrieve the keybindings      */
DECL|method|getNewKeyBindings ()
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|getNewKeyBindings
parameter_list|()
block|{
return|return
name|bindHM
return|;
block|}
DECL|method|KeyBindingsDialog (HashMap<String, String> name2binding, HashMap<String, String> defBinds)
specifier|public
name|KeyBindingsDialog
parameter_list|(
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|name2binding
parameter_list|,
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|defBinds
parameter_list|)
block|{
name|super
argument_list|()
expr_stmt|;
name|this
operator|.
name|defBinds
operator|=
name|defBinds
expr_stmt|;
name|setTitle
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Key bindings"
argument_list|)
argument_list|)
expr_stmt|;
name|setModal
argument_list|(
literal|true
argument_list|)
expr_stmt|;
comment|//this needs to be modal so that client knows when ok or cancel was clicked
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
name|bindHM
operator|=
name|name2binding
expr_stmt|;
name|setupTable
argument_list|()
expr_stmt|;
name|setList
argument_list|()
expr_stmt|;
comment|//JScrollPane listScroller = new JScrollPane(list);
name|JScrollPane
name|listScroller
init|=
operator|new
name|JScrollPane
argument_list|(
name|table
argument_list|)
decl_stmt|;
name|listScroller
operator|.
name|setPreferredSize
argument_list|(
operator|new
name|Dimension
argument_list|(
literal|250
argument_list|,
literal|400
argument_list|)
argument_list|)
expr_stmt|;
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|listScroller
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|grabB
operator|.
name|addKeyListener
argument_list|(
operator|new
name|JBM_CustomKeyBindingsListener
argument_list|()
argument_list|)
expr_stmt|;
name|buttonBox
operator|.
name|add
argument_list|(
name|grabB
argument_list|)
expr_stmt|;
name|buttonBox
operator|.
name|add
argument_list|(
name|defB
argument_list|)
expr_stmt|;
name|buttonBox
operator|.
name|add
argument_list|(
name|ok
argument_list|)
expr_stmt|;
name|buttonBox
operator|.
name|add
argument_list|(
name|cancel
argument_list|)
expr_stmt|;
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|buttonBox
argument_list|,
name|BorderLayout
operator|.
name|SOUTH
argument_list|)
expr_stmt|;
comment|//setTop();
name|setButtons
argument_list|()
expr_stmt|;
name|keyTF
operator|.
name|setEditable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|Util
operator|.
name|bindCloseDialogKeyToCancelAction
argument_list|(
name|getRootPane
argument_list|()
argument_list|,
name|cancel
operator|.
name|getAction
argument_list|()
argument_list|)
expr_stmt|;
name|addWindowListener
argument_list|(
operator|new
name|WindowAdapter
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|windowClosing
parameter_list|(
name|WindowEvent
name|e
parameter_list|)
block|{
name|clickedSave
operator|=
literal|false
expr_stmt|;
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
DECL|method|setupTable ()
specifier|private
name|void
name|setupTable
parameter_list|()
block|{
name|table
operator|=
operator|new
name|KeystrokeTable
argument_list|()
expr_stmt|;
comment|//table.setCellSelectionEnabled(false);
name|table
operator|.
name|setRowSelectionAllowed
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|table
operator|.
name|setColumnSelectionAllowed
argument_list|(
literal|false
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
name|setAutoCreateRowSorter
argument_list|(
literal|true
argument_list|)
expr_stmt|;
comment|// TODO: setup so that clicking on list will display the current binding
block|}
comment|/**      * respond to grabKey and display the key binding      */
DECL|class|JBM_CustomKeyBindingsListener
specifier|private
class|class
name|JBM_CustomKeyBindingsListener
extends|extends
name|KeyAdapter
block|{
annotation|@
name|Override
DECL|method|keyPressed (KeyEvent evt)
specifier|public
name|void
name|keyPressed
parameter_list|(
name|KeyEvent
name|evt
parameter_list|)
block|{
comment|// first check if anything is selected if not the return
name|int
name|selRow
init|=
name|table
operator|.
name|getSelectedRow
argument_list|()
decl_stmt|;
if|if
condition|(
name|selRow
operator|<
literal|0
condition|)
block|{
return|return;
block|}
name|String
name|code
init|=
name|KeyEvent
operator|.
name|getKeyText
argument_list|(
name|evt
operator|.
name|getKeyCode
argument_list|()
argument_list|)
decl_stmt|;
name|String
name|mod
init|=
name|KeyEvent
operator|.
name|getKeyModifiersText
argument_list|(
name|evt
operator|.
name|getModifiers
argument_list|()
argument_list|)
decl_stmt|;
comment|// all key bindings must have a modifier: ctrl alt etc
if|if
condition|(
literal|""
operator|.
name|equals
argument_list|(
name|mod
argument_list|)
condition|)
block|{
name|int
name|kc
init|=
name|evt
operator|.
name|getKeyCode
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
operator|(
operator|(
operator|(
name|kc
operator|>=
name|KeyEvent
operator|.
name|VK_F1
operator|)
operator|&&
operator|(
name|kc
operator|<=
name|KeyEvent
operator|.
name|VK_F12
operator|)
operator|)
operator|||
operator|(
name|kc
operator|==
name|KeyEvent
operator|.
name|VK_ESCAPE
operator|)
operator|||
operator|(
name|kc
operator|==
name|KeyEvent
operator|.
name|VK_DELETE
operator|)
operator|)
condition|)
block|{
return|return;
comment|// need a modifier except for function keys
block|}
block|}
comment|// second key cannot be a modifiers
if|if
condition|(
comment|//code.equals("Escape")
literal|"Tab"
operator|.
name|equals
argument_list|(
name|code
argument_list|)
operator|||
literal|"Backspace"
operator|.
name|equals
argument_list|(
name|code
argument_list|)
operator|||
literal|"Enter"
operator|.
name|equals
argument_list|(
name|code
argument_list|)
comment|//|| code.equals("Delete")
operator|||
literal|"Space"
operator|.
name|equals
argument_list|(
name|code
argument_list|)
operator|||
literal|"Ctrl"
operator|.
name|equals
argument_list|(
name|code
argument_list|)
operator|||
literal|"Shift"
operator|.
name|equals
argument_list|(
name|code
argument_list|)
operator|||
literal|"Alt"
operator|.
name|equals
argument_list|(
name|code
argument_list|)
condition|)
block|{
return|return;
block|}
name|String
name|newKey
decl_stmt|;
if|if
condition|(
operator|!
literal|""
operator|.
name|equals
argument_list|(
name|mod
argument_list|)
condition|)
block|{
name|newKey
operator|=
name|mod
operator|.
name|toLowerCase
argument_list|()
operator|.
name|replaceAll
argument_list|(
literal|"\\+"
argument_list|,
literal|" "
argument_list|)
operator|+
literal|" "
operator|+
name|code
expr_stmt|;
block|}
else|else
block|{
name|newKey
operator|=
name|code
expr_stmt|;
block|}
name|keyTF
operator|.
name|setText
argument_list|(
name|newKey
argument_list|)
expr_stmt|;
comment|//find which key is selected and set its value in the bindHM
name|String
name|selectedFunction
init|=
name|table
operator|.
name|getOriginalName
argument_list|(
name|selRow
argument_list|)
decl_stmt|;
name|table
operator|.
name|setValueAt
argument_list|(
name|newKey
argument_list|,
name|selRow
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|table
operator|.
name|revalidate
argument_list|()
expr_stmt|;
name|table
operator|.
name|repaint
argument_list|()
expr_stmt|;
name|bindHM
operator|.
name|put
argument_list|(
name|selectedFunction
argument_list|,
name|newKey
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Puts the content of bindHM into the table      */
DECL|method|setList ()
specifier|private
name|void
name|setList
parameter_list|()
block|{
name|Iterator
argument_list|<
name|String
argument_list|>
name|it
init|=
name|bindHM
operator|.
name|keySet
argument_list|()
operator|.
name|iterator
argument_list|()
decl_stmt|;
name|String
index|[]
index|[]
name|tableData
init|=
operator|new
name|String
index|[
name|bindHM
operator|.
name|size
argument_list|()
index|]
index|[
literal|3
index|]
decl_stmt|;
name|int
name|i
init|=
literal|0
decl_stmt|;
while|while
condition|(
name|it
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|String
name|s
init|=
name|it
operator|.
name|next
argument_list|()
decl_stmt|;
name|tableData
index|[
name|i
index|]
index|[
literal|2
index|]
operator|=
name|s
expr_stmt|;
name|tableData
index|[
name|i
index|]
index|[
literal|1
index|]
operator|=
name|bindHM
operator|.
name|get
argument_list|(
name|s
argument_list|)
expr_stmt|;
name|tableData
index|[
name|i
index|]
index|[
literal|0
index|]
operator|=
name|Localization
operator|.
name|lang
argument_list|(
name|s
argument_list|)
expr_stmt|;
name|i
operator|++
expr_stmt|;
comment|//listModel.addElement(s + " (" + bindHM.get(s) + ")");
block|}
name|TreeMap
argument_list|<
name|String
argument_list|,
name|String
index|[]
argument_list|>
name|sorted
init|=
operator|new
name|TreeMap
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
name|i
operator|=
literal|0
init|;
name|i
operator|<
name|tableData
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|sorted
operator|.
name|put
argument_list|(
name|tableData
index|[
name|i
index|]
index|[
literal|0
index|]
argument_list|,
name|tableData
index|[
name|i
index|]
argument_list|)
expr_stmt|;
block|}
name|KeystrokeTableModel
name|tableModel
init|=
operator|new
name|KeystrokeTableModel
argument_list|(
name|sorted
argument_list|)
decl_stmt|;
name|table
operator|.
name|setModel
argument_list|(
name|tableModel
argument_list|)
expr_stmt|;
comment|// has to be done each time as the columnModel is dependent on the tableModel
name|TableColumnModel
name|cm
init|=
name|table
operator|.
name|getColumnModel
argument_list|()
decl_stmt|;
name|cm
operator|.
name|getColumn
argument_list|(
literal|0
argument_list|)
operator|.
name|setPreferredWidth
argument_list|(
name|GUIGlobals
operator|.
name|KEYBIND_COL_0
argument_list|)
expr_stmt|;
name|cm
operator|.
name|getColumn
argument_list|(
literal|1
argument_list|)
operator|.
name|setPreferredWidth
argument_list|(
name|GUIGlobals
operator|.
name|KEYBIND_COL_1
argument_list|)
expr_stmt|;
block|}
annotation|@
name|SuppressWarnings
argument_list|(
literal|"serial"
argument_list|)
DECL|class|KeystrokeTable
specifier|private
specifier|static
class|class
name|KeystrokeTable
extends|extends
name|JTable
block|{
DECL|method|KeystrokeTable ()
specifier|public
name|KeystrokeTable
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
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
literal|false
return|;
block|}
DECL|method|getOriginalName (int row)
specifier|public
name|String
name|getOriginalName
parameter_list|(
name|int
name|row
parameter_list|)
block|{
return|return
operator|(
operator|(
name|KeystrokeTableModel
operator|)
name|getModel
argument_list|()
operator|)
operator|.
name|data
index|[
name|row
index|]
index|[
literal|2
index|]
return|;
block|}
block|}
annotation|@
name|SuppressWarnings
argument_list|(
literal|"serial"
argument_list|)
DECL|class|KeystrokeTableModel
specifier|private
specifier|static
class|class
name|KeystrokeTableModel
extends|extends
name|AbstractTableModel
block|{
DECL|field|data
specifier|final
name|String
index|[]
index|[]
name|data
decl_stmt|;
DECL|method|KeystrokeTableModel (TreeMap<String, String[]> sorted)
specifier|public
name|KeystrokeTableModel
parameter_list|(
name|TreeMap
argument_list|<
name|String
argument_list|,
name|String
index|[]
argument_list|>
name|sorted
parameter_list|)
block|{
name|data
operator|=
operator|new
name|String
index|[
name|sorted
operator|.
name|size
argument_list|()
index|]
index|[
literal|3
index|]
expr_stmt|;
name|Iterator
argument_list|<
name|String
argument_list|>
name|i
init|=
name|sorted
operator|.
name|keySet
argument_list|()
operator|.
name|iterator
argument_list|()
decl_stmt|;
name|int
name|row
init|=
literal|0
decl_stmt|;
while|while
condition|(
name|i
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|data
index|[
name|row
operator|++
index|]
operator|=
name|sorted
operator|.
name|get
argument_list|(
name|i
operator|.
name|next
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
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
literal|false
return|;
block|}
annotation|@
name|Override
DECL|method|getColumnName (int col)
specifier|public
name|String
name|getColumnName
parameter_list|(
name|int
name|col
parameter_list|)
block|{
comment|// @formatter:off
return|return
name|col
operator|==
literal|0
condition|?
name|Localization
operator|.
name|lang
argument_list|(
literal|"Action"
argument_list|)
else|:
name|Localization
operator|.
name|lang
argument_list|(
literal|"Shortcut"
argument_list|)
return|;
comment|// @formatter:on
block|}
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
DECL|method|getRowCount ()
specifier|public
name|int
name|getRowCount
parameter_list|()
block|{
return|return
name|data
operator|.
name|length
return|;
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
return|return
name|data
index|[
name|rowIndex
index|]
index|[
name|columnIndex
index|]
return|;
block|}
annotation|@
name|Override
DECL|method|setValueAt (Object o, int row, int col)
specifier|public
name|void
name|setValueAt
parameter_list|(
name|Object
name|o
parameter_list|,
name|int
name|row
parameter_list|,
name|int
name|col
parameter_list|)
block|{
name|data
index|[
name|row
index|]
index|[
name|col
index|]
operator|=
operator|(
name|String
operator|)
name|o
expr_stmt|;
block|}
block|}
comment|// listeners
DECL|method|setButtons ()
specifier|private
name|void
name|setButtons
parameter_list|()
block|{
name|ok
operator|.
name|addActionListener
argument_list|(
operator|new
name|ActionListener
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
comment|// save all the key bindings
name|dispose
argument_list|()
expr_stmt|;
name|clickedSave
operator|=
literal|true
expr_stmt|;
comment|// also displays message: key bindings will take into effect next time you start JBM
block|}
block|}
argument_list|)
expr_stmt|;
name|cancel
operator|.
name|addActionListener
argument_list|(
operator|new
name|ActionListener
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
name|clickedSave
operator|=
literal|false
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|defB
operator|.
name|addActionListener
argument_list|(
operator|new
name|ActionListener
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
name|int
index|[]
name|selected
init|=
name|table
operator|.
name|getSelectedRows
argument_list|()
decl_stmt|;
if|if
condition|(
name|selected
operator|.
name|length
operator|==
literal|0
condition|)
block|{
name|int
name|answer
decl_stmt|;
comment|// @formatter:off
name|answer
operator|=
name|JOptionPane
operator|.
name|showOptionDialog
argument_list|(
name|KeyBindingsDialog
operator|.
name|this
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"All key bindings will be reset to their defaults."
argument_list|)
operator|+
literal|" "
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"Continue?"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Resetting all key bindings"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|YES_NO_OPTION
argument_list|,
name|JOptionPane
operator|.
name|QUESTION_MESSAGE
argument_list|,
literal|null
argument_list|,
operator|new
name|String
index|[]
block|{
name|Localization
operator|.
name|lang
argument_list|(
literal|"Ok"
argument_list|)
block|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Cancel"
argument_list|)
block|}
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Ok"
argument_list|)
argument_list|)
expr_stmt|;
comment|// @formatter:on
if|if
condition|(
name|answer
operator|==
name|JOptionPane
operator|.
name|YES_OPTION
condition|)
block|{
name|bindHM
operator|.
name|clear
argument_list|()
expr_stmt|;
name|Set
argument_list|<
name|Entry
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|>
name|entrySet
init|=
name|defBinds
operator|.
name|entrySet
argument_list|()
decl_stmt|;
for|for
control|(
name|Entry
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|entry
range|:
name|entrySet
control|)
block|{
name|bindHM
operator|.
name|put
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|,
name|entry
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|setList
argument_list|()
expr_stmt|;
block|}
block|}
else|else
block|{
for|for
control|(
name|int
name|row
range|:
name|selected
control|)
block|{
name|String
name|name
init|=
operator|(
name|String
operator|)
name|table
operator|.
name|getValueAt
argument_list|(
name|row
argument_list|,
literal|0
argument_list|)
decl_stmt|;
name|String
name|newKey
init|=
name|setToDefault
argument_list|(
name|name
argument_list|)
decl_stmt|;
name|keyTF
operator|.
name|setText
argument_list|(
name|newKey
argument_list|)
expr_stmt|;
name|table
operator|.
name|setValueAt
argument_list|(
name|newKey
argument_list|,
name|row
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|table
operator|.
name|repaint
argument_list|()
expr_stmt|;
block|}
block|}
block|}
block|}
argument_list|)
expr_stmt|;
block|}
comment|/**      * Resets a single accelerator key      * @param name the action name      * @return the default accelerator key      */
DECL|method|setToDefault (String name)
specifier|private
name|String
name|setToDefault
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|String
name|defKey
init|=
name|defBinds
operator|.
name|get
argument_list|(
name|name
argument_list|)
decl_stmt|;
name|bindHM
operator|.
name|put
argument_list|(
name|name
argument_list|,
name|defKey
argument_list|)
expr_stmt|;
return|return
name|defKey
return|;
block|}
comment|/*          public static void main(String args[])          {       HashMap h=new HashMap();       h.put("new-bibtex","ctrl N");       h.put("edit-bibtex","ctrl E");       h.put("exit-bibtex","ctrl Q");       KeyBindingsDialog d= new KeyBindingsDialog(h);       d.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);       d.setSize(200,300);       d.setVisible(true);        }*/
block|}
end_class

end_unit

