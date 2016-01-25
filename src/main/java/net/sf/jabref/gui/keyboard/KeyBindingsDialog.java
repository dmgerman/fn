begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.gui.keyboard
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|keyboard
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
name|GUIGlobals
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
specifier|public
class|class
name|KeyBindingsDialog
extends|extends
name|JDialog
block|{
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
DECL|field|resetToDefaultKeyBindings
specifier|private
specifier|final
name|JButton
name|resetToDefaultKeyBindings
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
DECL|field|keyBindingRepository
specifier|private
specifier|final
name|KeyBindingRepository
name|keyBindingRepository
decl_stmt|;
DECL|field|table
specifier|private
specifier|final
name|KeyBindingTable
name|table
decl_stmt|;
DECL|method|KeyBindingsDialog (KeyBindingRepository keyBindingRepository)
specifier|public
name|KeyBindingsDialog
parameter_list|(
name|KeyBindingRepository
name|keyBindingRepository
parameter_list|)
block|{
name|super
argument_list|()
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
name|this
operator|.
name|keyBindingRepository
operator|=
name|keyBindingRepository
expr_stmt|;
name|this
operator|.
name|table
operator|=
name|setupTable
argument_list|()
expr_stmt|;
name|updateTableData
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
name|KeyBindingsListener
argument_list|(
name|table
argument_list|)
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
name|resetToDefaultKeyBindings
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
name|activateListeners
argument_list|()
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
name|KeyBindingTable
name|setupTable
parameter_list|()
block|{
name|KeyBindingTable
name|table
init|=
operator|new
name|KeyBindingTable
argument_list|()
decl_stmt|;
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
return|return
name|table
return|;
block|}
DECL|method|updateTableData ()
specifier|private
name|void
name|updateTableData
parameter_list|()
block|{
name|KeyBindingTableModel
name|tableModel
init|=
operator|new
name|KeyBindingTableModel
argument_list|(
name|keyBindingRepository
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
DECL|method|activateListeners ()
specifier|private
name|void
name|activateListeners
parameter_list|()
block|{
name|ok
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
block|{
comment|// save all the key bindings
name|Globals
operator|.
name|getKeyPrefs
argument_list|()
operator|.
name|setNewKeyBindings
argument_list|(
name|keyBindingRepository
operator|.
name|getKeyBindings
argument_list|()
argument_list|)
expr_stmt|;
comment|// show message
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|KeyBindingsDialog
operator|.
name|this
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Your new key bindings have been stored."
argument_list|)
operator|+
literal|'\n'
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"You must restart JabRef for the new key "
operator|+
literal|"bindings to work properly."
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Key bindings changed"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|INFORMATION_MESSAGE
argument_list|)
expr_stmt|;
name|dispose
argument_list|()
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
name|cancel
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
name|dispose
argument_list|()
argument_list|)
expr_stmt|;
name|resetToDefaultKeyBindings
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
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
name|boolean
name|hasNothingSelected
init|=
name|selected
operator|.
name|length
operator|==
literal|0
decl_stmt|;
if|if
condition|(
name|hasNothingSelected
condition|)
block|{
name|int
name|answer
init|=
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
literal|"OK"
argument_list|)
operator|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Cancel"
argument_list|)
block|}
operator|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"OK"
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|answer
operator|==
name|JOptionPane
operator|.
name|YES_OPTION
condition|)
block|{
name|keyBindingRepository
operator|.
name|resetToDefault
argument_list|()
expr_stmt|;
name|updateTableData
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
name|String
operator|.
name|valueOf
argument_list|(
name|table
operator|.
name|getValueAt
argument_list|(
name|row
argument_list|,
literal|0
argument_list|)
argument_list|)
decl_stmt|;
name|keyBindingRepository
operator|.
name|resetToDefault
argument_list|(
name|name
argument_list|)
expr_stmt|;
name|String
name|newKey
init|=
name|keyBindingRepository
operator|.
name|get
argument_list|(
name|name
argument_list|)
decl_stmt|;
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
end_class

begin_empty_stmt
unit|)
empty_stmt|;
end_empty_stmt

unit|} }
end_unit

