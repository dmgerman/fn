begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* Copyright (C) 2003 Nizar N. Batada, Morten O. Alver  All programs in this directory and subdirectories are published under the GNU General Public License as described below.  This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.  You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA  Further information about the GNU GPL is available at: http://www.gnu.org/copyleft/gpl.ja.html  */
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
name|event
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
name|*
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
name|*
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
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
name|undo
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
name|undo
operator|.
name|CompoundEdit
import|;
end_import

begin_class
DECL|class|StringDialog
specifier|public
class|class
name|StringDialog
extends|extends
name|JDialog
block|{
comment|// A reference to the entry this object works on.
DECL|field|base
name|BibtexDatabase
name|base
decl_stmt|;
DECL|field|frame
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|panel
name|BasePanel
name|panel
decl_stmt|;
DECL|field|prefs
name|JabRefPreferences
name|prefs
decl_stmt|;
comment|// Layout objects.
DECL|field|gbl
name|GridBagLayout
name|gbl
init|=
operator|new
name|GridBagLayout
argument_list|()
decl_stmt|;
DECL|field|con
name|GridBagConstraints
name|con
init|=
operator|new
name|GridBagConstraints
argument_list|()
decl_stmt|;
DECL|field|lab
name|JLabel
name|lab
decl_stmt|;
DECL|field|conPane
name|Container
name|conPane
init|=
name|getContentPane
argument_list|()
decl_stmt|;
DECL|field|tlb
name|JToolBar
name|tlb
init|=
operator|new
name|JToolBar
argument_list|()
decl_stmt|;
DECL|field|pan
name|JPanel
name|pan
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
DECL|field|table
name|StringTable
name|table
decl_stmt|;
DECL|field|helpAction
name|HelpAction
name|helpAction
decl_stmt|;
DECL|method|StringDialog (JabRefFrame frame, BasePanel panel, BibtexDatabase base, JabRefPreferences prefs)
specifier|public
name|StringDialog
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|BasePanel
name|panel
parameter_list|,
name|BibtexDatabase
name|base
parameter_list|,
name|JabRefPreferences
name|prefs
parameter_list|)
block|{
name|super
argument_list|(
name|frame
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
name|panel
operator|=
name|panel
expr_stmt|;
name|this
operator|.
name|base
operator|=
name|base
expr_stmt|;
name|this
operator|.
name|prefs
operator|=
name|prefs
expr_stmt|;
name|helpAction
operator|=
operator|new
name|HelpAction
argument_list|(
name|frame
operator|.
name|helpDiag
argument_list|,
name|GUIGlobals
operator|.
name|stringEditorHelp
argument_list|,
literal|"Help"
argument_list|)
expr_stmt|;
name|addWindowListener
argument_list|(
operator|new
name|WindowAdapter
argument_list|()
block|{
specifier|public
name|void
name|windowClosing
parameter_list|(
name|WindowEvent
name|e
parameter_list|)
block|{
name|closeAction
operator|.
name|actionPerformed
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
comment|// We replace the default FocusTraversalPolicy with a subclass
comment|// that only allows the StringTable to gain keyboard focus.
name|setFocusTraversalPolicy
argument_list|(
operator|new
name|LayoutFocusTraversalPolicy
argument_list|()
block|{
specifier|protected
name|boolean
name|accept
parameter_list|(
name|Component
name|c
parameter_list|)
block|{
return|return
operator|(
name|super
operator|.
name|accept
argument_list|(
name|c
argument_list|)
operator|&&
operator|(
name|c
operator|instanceof
name|StringTable
operator|)
operator|)
return|;
block|}
block|}
argument_list|)
expr_stmt|;
name|setLocation
argument_list|(
name|prefs
operator|.
name|getInt
argument_list|(
literal|"stringsPosX"
argument_list|)
argument_list|,
name|prefs
operator|.
name|getInt
argument_list|(
literal|"stringsPosY"
argument_list|)
argument_list|)
expr_stmt|;
name|setSize
argument_list|(
name|prefs
operator|.
name|getInt
argument_list|(
literal|"stringsSizeX"
argument_list|)
argument_list|,
name|prefs
operator|.
name|getInt
argument_list|(
literal|"stringsSizeY"
argument_list|)
argument_list|)
expr_stmt|;
name|pan
operator|.
name|setLayout
argument_list|(
name|gbl
argument_list|)
expr_stmt|;
name|con
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|BOTH
expr_stmt|;
name|con
operator|.
name|weighty
operator|=
literal|1
expr_stmt|;
name|con
operator|.
name|weightx
operator|=
literal|1
expr_stmt|;
name|table
operator|=
operator|new
name|StringTable
argument_list|(
name|this
argument_list|,
name|base
argument_list|)
expr_stmt|;
if|if
condition|(
name|base
operator|.
name|getStringCount
argument_list|()
operator|>
literal|0
condition|)
name|table
operator|.
name|setRowSelectionInterval
argument_list|(
literal|0
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|table
operator|.
name|getPane
argument_list|()
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|pan
operator|.
name|add
argument_list|(
name|table
operator|.
name|getPane
argument_list|()
argument_list|)
expr_stmt|;
name|InputMap
name|im
init|=
name|tlb
operator|.
name|getInputMap
argument_list|(
name|JComponent
operator|.
name|WHEN_IN_FOCUSED_WINDOW
argument_list|)
decl_stmt|;
name|ActionMap
name|am
init|=
name|tlb
operator|.
name|getActionMap
argument_list|()
decl_stmt|;
name|im
operator|.
name|put
argument_list|(
name|KeyStroke
operator|.
name|getKeyStroke
argument_list|(
name|GUIGlobals
operator|.
name|addKey
argument_list|)
argument_list|,
literal|"add"
argument_list|)
expr_stmt|;
name|am
operator|.
name|put
argument_list|(
literal|"add"
argument_list|,
name|newStringAction
argument_list|)
expr_stmt|;
name|im
operator|.
name|put
argument_list|(
name|KeyStroke
operator|.
name|getKeyStroke
argument_list|(
name|GUIGlobals
operator|.
name|removeKey
argument_list|)
argument_list|,
literal|"remove"
argument_list|)
expr_stmt|;
name|am
operator|.
name|put
argument_list|(
literal|"remove"
argument_list|,
name|removeStringAction
argument_list|)
expr_stmt|;
name|im
operator|.
name|put
argument_list|(
name|KeyStroke
operator|.
name|getKeyStroke
argument_list|(
name|GUIGlobals
operator|.
name|upKey
argument_list|)
argument_list|,
literal|"up"
argument_list|)
expr_stmt|;
name|am
operator|.
name|put
argument_list|(
literal|"up"
argument_list|,
name|stringUpAction
argument_list|)
expr_stmt|;
name|im
operator|.
name|put
argument_list|(
name|KeyStroke
operator|.
name|getKeyStroke
argument_list|(
name|GUIGlobals
operator|.
name|downKey
argument_list|)
argument_list|,
literal|"down"
argument_list|)
expr_stmt|;
name|am
operator|.
name|put
argument_list|(
literal|"down"
argument_list|,
name|stringDownAction
argument_list|)
expr_stmt|;
name|im
operator|.
name|put
argument_list|(
name|GUIGlobals
operator|.
name|exitDialog
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
name|im
operator|.
name|put
argument_list|(
name|GUIGlobals
operator|.
name|helpKeyStroke
argument_list|,
literal|"help"
argument_list|)
expr_stmt|;
name|am
operator|.
name|put
argument_list|(
literal|"help"
argument_list|,
name|helpAction
argument_list|)
expr_stmt|;
name|im
operator|.
name|put
argument_list|(
name|GUIGlobals
operator|.
name|undoStroke
argument_list|,
literal|"undo"
argument_list|)
expr_stmt|;
name|am
operator|.
name|put
argument_list|(
literal|"undo"
argument_list|,
name|undoAction
argument_list|)
expr_stmt|;
name|im
operator|.
name|put
argument_list|(
name|GUIGlobals
operator|.
name|redoStroke
argument_list|,
literal|"redo"
argument_list|)
expr_stmt|;
name|am
operator|.
name|put
argument_list|(
literal|"redo"
argument_list|,
name|redoAction
argument_list|)
expr_stmt|;
comment|//tlb.add(closeAction);
comment|//tlb.addSeparator();
name|tlb
operator|.
name|add
argument_list|(
name|newStringAction
argument_list|)
expr_stmt|;
name|tlb
operator|.
name|add
argument_list|(
name|removeStringAction
argument_list|)
expr_stmt|;
name|tlb
operator|.
name|addSeparator
argument_list|()
expr_stmt|;
name|tlb
operator|.
name|add
argument_list|(
name|stringUpAction
argument_list|)
expr_stmt|;
name|tlb
operator|.
name|add
argument_list|(
name|stringDownAction
argument_list|)
expr_stmt|;
name|tlb
operator|.
name|addSeparator
argument_list|()
expr_stmt|;
name|tlb
operator|.
name|add
argument_list|(
name|helpAction
argument_list|)
expr_stmt|;
name|conPane
operator|.
name|add
argument_list|(
name|tlb
argument_list|,
name|BorderLayout
operator|.
name|NORTH
argument_list|)
expr_stmt|;
name|conPane
operator|.
name|add
argument_list|(
name|pan
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
if|if
condition|(
name|panel
operator|.
name|file
operator|!=
literal|null
condition|)
name|setTitle
argument_list|(
name|GUIGlobals
operator|.
name|stringsTitle
operator|+
name|panel
operator|.
name|file
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
else|else
name|setTitle
argument_list|(
name|GUIGlobals
operator|.
name|untitledStringsTitle
argument_list|)
expr_stmt|;
block|}
DECL|method|refreshTable ()
specifier|public
name|void
name|refreshTable
parameter_list|()
block|{
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
block|}
DECL|class|StringTable
class|class
name|StringTable
extends|extends
name|JTable
block|{
DECL|field|sp
name|JScrollPane
name|sp
init|=
operator|new
name|JScrollPane
argument_list|(
operator|(
name|JTable
operator|)
name|this
argument_list|)
decl_stmt|;
DECL|method|StringTable (StringDialog parent, BibtexDatabase base)
specifier|public
name|StringTable
parameter_list|(
name|StringDialog
name|parent
parameter_list|,
name|BibtexDatabase
name|base
parameter_list|)
block|{
name|super
argument_list|(
operator|new
name|StringTableModel
argument_list|(
name|parent
argument_list|,
name|base
argument_list|)
argument_list|)
expr_stmt|;
name|setShowVerticalLines
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|setShowHorizontalLines
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|setColumnSelectionAllowed
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|DefaultCellEditor
name|dce
init|=
operator|new
name|DefaultCellEditor
argument_list|(
operator|new
name|JTextField
argument_list|()
argument_list|)
decl_stmt|;
name|dce
operator|.
name|setClickCountToStart
argument_list|(
literal|2
argument_list|)
expr_stmt|;
name|setDefaultEditor
argument_list|(
name|String
operator|.
name|class
argument_list|,
name|dce
argument_list|)
expr_stmt|;
name|TableColumnModel
name|cm
init|=
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
literal|800
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
literal|2000
argument_list|)
expr_stmt|;
name|sp
operator|.
name|getViewport
argument_list|()
operator|.
name|setBackground
argument_list|(
name|GUIGlobals
operator|.
name|tableBackground
argument_list|)
expr_stmt|;
comment|// getInputMap().remove(GUIGlobals.exitDialog);
name|getInputMap
argument_list|()
operator|.
name|put
argument_list|(
name|GUIGlobals
operator|.
name|exitDialog
argument_list|,
literal|"close"
argument_list|)
expr_stmt|;
name|getActionMap
argument_list|()
operator|.
name|put
argument_list|(
literal|"close"
argument_list|,
name|closeAction
argument_list|)
expr_stmt|;
name|getInputMap
argument_list|()
operator|.
name|put
argument_list|(
name|GUIGlobals
operator|.
name|helpKeyStroke
argument_list|,
literal|"help"
argument_list|)
expr_stmt|;
name|getActionMap
argument_list|()
operator|.
name|put
argument_list|(
literal|"help"
argument_list|,
name|helpAction
argument_list|)
expr_stmt|;
block|}
DECL|method|getPane ()
specifier|public
name|JComponent
name|getPane
parameter_list|()
block|{
return|return
name|sp
return|;
block|}
block|}
DECL|class|StringTableModel
class|class
name|StringTableModel
extends|extends
name|AbstractTableModel
block|{
DECL|field|base
name|BibtexDatabase
name|base
decl_stmt|;
DECL|field|parent
name|StringDialog
name|parent
decl_stmt|;
DECL|method|StringTableModel (StringDialog parent, BibtexDatabase base)
specifier|public
name|StringTableModel
parameter_list|(
name|StringDialog
name|parent
parameter_list|,
name|BibtexDatabase
name|base
parameter_list|)
block|{
name|this
operator|.
name|parent
operator|=
name|parent
expr_stmt|;
name|this
operator|.
name|base
operator|=
name|base
expr_stmt|;
block|}
DECL|method|getValueAt (int row, int col)
specifier|public
name|Object
name|getValueAt
parameter_list|(
name|int
name|row
parameter_list|,
name|int
name|col
parameter_list|)
block|{
return|return
operator|(
operator|(
name|col
operator|==
literal|0
operator|)
condition|?
name|base
operator|.
name|getString
argument_list|(
name|row
argument_list|)
operator|.
name|getName
argument_list|()
else|:
name|base
operator|.
name|getString
argument_list|(
name|row
argument_list|)
operator|.
name|getContent
argument_list|()
operator|)
return|;
block|}
DECL|method|setValueAt (Object value, int row, int col)
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
name|col
parameter_list|)
block|{
comment|//	    if (row>= base.getStringCount())
comment|//	return; // After a Remove operation the program somehow
comment|// thinks the user is still editing an entry,
comment|// which might now be outside
if|if
condition|(
name|col
operator|==
literal|0
condition|)
block|{
comment|// Change name of string.
if|if
condition|(
operator|!
operator|(
operator|(
name|String
operator|)
name|value
operator|)
operator|.
name|equals
argument_list|(
name|base
operator|.
name|getString
argument_list|(
name|row
argument_list|)
operator|.
name|getName
argument_list|()
argument_list|)
condition|)
block|{
if|if
condition|(
name|base
operator|.
name|hasStringLabel
argument_list|(
operator|(
name|String
operator|)
name|value
argument_list|)
condition|)
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|parent
argument_list|,
literal|"A string with that label "
operator|+
literal|"already exists"
argument_list|,
literal|"Label"
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
operator|(
name|value
operator|!=
literal|null
operator|)
operator|&&
name|isNumber
argument_list|(
operator|(
name|String
operator|)
name|value
argument_list|)
condition|)
block|{
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|parent
argument_list|,
literal|"The label of the string can not be a number."
argument_list|,
literal|"Label"
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// Store undo information.
name|panel
operator|.
name|undoManager
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableStringChange
argument_list|(
name|panel
argument_list|,
name|base
operator|.
name|getString
argument_list|(
name|row
argument_list|)
argument_list|,
literal|true
argument_list|,
name|base
operator|.
name|getString
argument_list|(
name|row
argument_list|)
operator|.
name|getName
argument_list|()
argument_list|,
operator|(
name|String
operator|)
name|value
argument_list|)
argument_list|)
expr_stmt|;
name|base
operator|.
name|getString
argument_list|(
name|row
argument_list|)
operator|.
name|setName
argument_list|(
operator|(
name|String
operator|)
name|value
argument_list|)
expr_stmt|;
name|panel
operator|.
name|markBaseChanged
argument_list|()
expr_stmt|;
block|}
block|}
block|}
else|else
block|{
comment|// Change content of string.
if|if
condition|(
operator|!
operator|(
operator|(
name|String
operator|)
name|value
operator|)
operator|.
name|equals
argument_list|(
name|base
operator|.
name|getString
argument_list|(
name|row
argument_list|)
operator|.
name|getContent
argument_list|()
argument_list|)
condition|)
block|{
comment|// Store undo information.
name|panel
operator|.
name|undoManager
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableStringChange
argument_list|(
name|panel
argument_list|,
name|base
operator|.
name|getString
argument_list|(
name|row
argument_list|)
argument_list|,
literal|false
argument_list|,
name|base
operator|.
name|getString
argument_list|(
name|row
argument_list|)
operator|.
name|getContent
argument_list|()
argument_list|,
operator|(
name|String
operator|)
name|value
argument_list|)
argument_list|)
expr_stmt|;
name|base
operator|.
name|getString
argument_list|(
name|row
argument_list|)
operator|.
name|setContent
argument_list|(
operator|(
name|String
operator|)
name|value
argument_list|)
expr_stmt|;
name|panel
operator|.
name|markBaseChanged
argument_list|()
expr_stmt|;
block|}
block|}
block|}
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
DECL|method|getRowCount ()
specifier|public
name|int
name|getRowCount
parameter_list|()
block|{
return|return
name|base
operator|.
name|getStringCount
argument_list|()
return|;
block|}
DECL|method|getColumnName (int col)
specifier|public
name|String
name|getColumnName
parameter_list|(
name|int
name|col
parameter_list|)
block|{
return|return
operator|(
operator|(
name|col
operator|==
literal|0
operator|)
condition|?
literal|"Name"
else|:
literal|"Content"
operator|)
return|;
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
literal|true
return|;
block|}
block|}
DECL|method|isNumber (String name)
specifier|protected
name|boolean
name|isNumber
parameter_list|(
name|String
name|name
parameter_list|)
block|{
comment|// A pure integer number can not be used as a string label,
comment|// since Bibtex will read it as a number.
try|try
block|{
name|Integer
operator|.
name|parseInt
argument_list|(
name|name
argument_list|)
expr_stmt|;
return|return
literal|true
return|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|ex
parameter_list|)
block|{
return|return
literal|false
return|;
block|}
block|}
DECL|method|assureNotEditing ()
specifier|protected
name|void
name|assureNotEditing
parameter_list|()
block|{
if|if
condition|(
name|table
operator|.
name|isEditing
argument_list|()
condition|)
block|{
name|int
name|col
init|=
name|table
operator|.
name|getEditingColumn
argument_list|()
decl_stmt|,
name|row
init|=
name|table
operator|.
name|getEditingRow
argument_list|()
decl_stmt|;
name|table
operator|.
name|getCellEditor
argument_list|(
name|row
argument_list|,
name|col
argument_list|)
operator|.
name|stopCellEditing
argument_list|()
expr_stmt|;
block|}
block|}
comment|// The action concerned with closing the window.
DECL|field|closeAction
name|CloseAction
name|closeAction
init|=
operator|new
name|CloseAction
argument_list|(
name|this
argument_list|)
decl_stmt|;
DECL|class|CloseAction
class|class
name|CloseAction
extends|extends
name|AbstractAction
block|{
DECL|field|parent
name|StringDialog
name|parent
decl_stmt|;
DECL|method|CloseAction (StringDialog parent)
specifier|public
name|CloseAction
parameter_list|(
name|StringDialog
name|parent
parameter_list|)
block|{
name|super
argument_list|(
literal|"Close window"
argument_list|)
expr_stmt|;
comment|//, new ImageIcon(GUIGlobals.closeIconFile));
name|putValue
argument_list|(
name|SHORT_DESCRIPTION
argument_list|,
literal|"Close window (Ctrl-Q)"
argument_list|)
expr_stmt|;
name|this
operator|.
name|parent
operator|=
name|parent
expr_stmt|;
block|}
DECL|method|actionPerformed (ActionEvent e)
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|panel
operator|.
name|stringsClosing
argument_list|()
expr_stmt|;
name|dispose
argument_list|()
expr_stmt|;
name|Point
name|p
init|=
name|getLocation
argument_list|()
decl_stmt|;
name|Dimension
name|d
init|=
name|getSize
argument_list|()
decl_stmt|;
name|prefs
operator|.
name|putInt
argument_list|(
literal|"stringsPosX"
argument_list|,
name|p
operator|.
name|x
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|putInt
argument_list|(
literal|"stringsPosY"
argument_list|,
name|p
operator|.
name|y
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|putInt
argument_list|(
literal|"stringsSizeX"
argument_list|,
name|d
operator|.
name|width
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|putInt
argument_list|(
literal|"stringsSizeY"
argument_list|,
name|d
operator|.
name|height
argument_list|)
expr_stmt|;
block|}
block|}
DECL|field|newStringAction
name|NewStringAction
name|newStringAction
init|=
operator|new
name|NewStringAction
argument_list|(
name|this
argument_list|)
decl_stmt|;
DECL|class|NewStringAction
class|class
name|NewStringAction
extends|extends
name|AbstractAction
block|{
DECL|field|parent
name|StringDialog
name|parent
decl_stmt|;
DECL|method|NewStringAction (StringDialog parent)
specifier|public
name|NewStringAction
parameter_list|(
name|StringDialog
name|parent
parameter_list|)
block|{
name|super
argument_list|(
literal|"New string"
argument_list|,
operator|new
name|ImageIcon
argument_list|(
name|GUIGlobals
operator|.
name|addIconFile
argument_list|)
argument_list|)
expr_stmt|;
name|putValue
argument_list|(
name|SHORT_DESCRIPTION
argument_list|,
literal|"New string (Ctrl-N)"
argument_list|)
expr_stmt|;
name|this
operator|.
name|parent
operator|=
name|parent
expr_stmt|;
block|}
DECL|method|actionPerformed (ActionEvent e)
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|String
name|name
init|=
name|JOptionPane
operator|.
name|showInputDialog
argument_list|(
name|parent
argument_list|,
literal|"Please enter the string's label"
argument_list|)
decl_stmt|;
if|if
condition|(
name|name
operator|==
literal|null
condition|)
return|return;
if|if
condition|(
name|isNumber
argument_list|(
name|name
argument_list|)
condition|)
block|{
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|parent
argument_list|,
literal|"The label of the string can not be a number."
argument_list|,
literal|"Label"
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
return|return;
block|}
try|try
block|{
name|BibtexString
name|bs
init|=
operator|new
name|BibtexString
argument_list|(
name|name
argument_list|,
literal|""
argument_list|)
decl_stmt|;
comment|// Store undo information:
name|panel
operator|.
name|undoManager
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableInsertString
argument_list|(
name|base
argument_list|,
name|bs
argument_list|,
name|base
operator|.
name|getStringCount
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|base
operator|.
name|addString
argument_list|(
name|bs
argument_list|,
name|base
operator|.
name|getStringCount
argument_list|()
argument_list|)
expr_stmt|;
name|table
operator|.
name|revalidate
argument_list|()
expr_stmt|;
name|panel
operator|.
name|markBaseChanged
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|KeyCollisionException
name|ex
parameter_list|)
block|{
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|parent
argument_list|,
literal|"A string with that label "
operator|+
literal|"already exists"
argument_list|,
literal|"Label"
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
block|}
block|}
block|}
DECL|field|storeContentAction
name|StoreContentAction
name|storeContentAction
init|=
operator|new
name|StoreContentAction
argument_list|(
name|this
argument_list|)
decl_stmt|;
DECL|class|StoreContentAction
class|class
name|StoreContentAction
extends|extends
name|AbstractAction
block|{
DECL|field|parent
name|StringDialog
name|parent
decl_stmt|;
DECL|method|StoreContentAction (StringDialog parent)
specifier|public
name|StoreContentAction
parameter_list|(
name|StringDialog
name|parent
parameter_list|)
block|{
name|super
argument_list|(
literal|"Store string"
argument_list|,
operator|new
name|ImageIcon
argument_list|(
name|GUIGlobals
operator|.
name|addIconFile
argument_list|)
argument_list|)
expr_stmt|;
name|putValue
argument_list|(
name|SHORT_DESCRIPTION
argument_list|,
literal|"Store string (Ctrl-S)"
argument_list|)
expr_stmt|;
name|this
operator|.
name|parent
operator|=
name|parent
expr_stmt|;
block|}
DECL|method|actionPerformed (ActionEvent e)
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{ 	}
block|}
DECL|field|removeStringAction
name|RemoveStringAction
name|removeStringAction
init|=
operator|new
name|RemoveStringAction
argument_list|(
name|this
argument_list|)
decl_stmt|;
DECL|class|RemoveStringAction
class|class
name|RemoveStringAction
extends|extends
name|AbstractAction
block|{
DECL|field|parent
name|StringDialog
name|parent
decl_stmt|;
DECL|method|RemoveStringAction (StringDialog parent)
specifier|public
name|RemoveStringAction
parameter_list|(
name|StringDialog
name|parent
parameter_list|)
block|{
name|super
argument_list|(
literal|"Remove selected strings"
argument_list|,
operator|new
name|ImageIcon
argument_list|(
name|GUIGlobals
operator|.
name|removeIconFile
argument_list|)
argument_list|)
expr_stmt|;
name|putValue
argument_list|(
name|SHORT_DESCRIPTION
argument_list|,
literal|"Remove selected strings (Shift-DEL)"
argument_list|)
expr_stmt|;
name|this
operator|.
name|parent
operator|=
name|parent
expr_stmt|;
block|}
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
name|sel
init|=
name|table
operator|.
name|getSelectedRows
argument_list|()
decl_stmt|;
if|if
condition|(
name|sel
operator|.
name|length
operator|>
literal|0
condition|)
block|{
comment|// Make sure no cell is being edited, as caused by the
comment|// keystroke. This makes the content hang on the screen.
name|assureNotEditing
argument_list|()
expr_stmt|;
name|String
name|msg
init|=
literal|"Really delete the selected "
operator|+
operator|(
operator|(
name|sel
operator|.
name|length
operator|>
literal|1
operator|)
condition|?
name|sel
operator|.
name|length
operator|+
literal|" entries?"
else|:
literal|"entry?"
operator|)
decl_stmt|;
name|int
name|answer
init|=
name|JOptionPane
operator|.
name|showConfirmDialog
argument_list|(
name|parent
argument_list|,
name|msg
argument_list|,
literal|"Delete strings"
argument_list|,
name|JOptionPane
operator|.
name|YES_NO_OPTION
argument_list|,
name|JOptionPane
operator|.
name|QUESTION_MESSAGE
argument_list|)
decl_stmt|;
if|if
condition|(
name|answer
operator|==
name|JOptionPane
operator|.
name|YES_OPTION
condition|)
block|{
name|CompoundEdit
name|ce
init|=
operator|new
name|CompoundEdit
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
name|sel
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
comment|// Delete the strings backwards to avoid moving indexes.
comment|// Store undo information:
name|ce
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableRemoveString
argument_list|(
name|panel
argument_list|,
name|base
argument_list|,
name|base
operator|.
name|getString
argument_list|(
name|sel
index|[
name|i
index|]
argument_list|)
argument_list|,
name|sel
index|[
name|i
index|]
argument_list|)
argument_list|)
expr_stmt|;
name|base
operator|.
name|removeString
argument_list|(
name|sel
index|[
name|i
index|]
argument_list|)
expr_stmt|;
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
name|table
operator|.
name|revalidate
argument_list|()
expr_stmt|;
if|if
condition|(
name|base
operator|.
name|getStringCount
argument_list|()
operator|>
literal|0
condition|)
name|table
operator|.
name|setRowSelectionInterval
argument_list|(
literal|0
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|table
operator|.
name|repaint
argument_list|()
expr_stmt|;
name|panel
operator|.
name|markBaseChanged
argument_list|()
expr_stmt|;
block|}
block|}
block|}
block|}
DECL|field|stringUpAction
name|StringUpAction
name|stringUpAction
init|=
operator|new
name|StringUpAction
argument_list|()
decl_stmt|;
DECL|class|StringUpAction
class|class
name|StringUpAction
extends|extends
name|AbstractAction
block|{
DECL|method|StringUpAction ()
specifier|public
name|StringUpAction
parameter_list|()
block|{
name|super
argument_list|(
literal|"Move string upwards"
argument_list|,
operator|new
name|ImageIcon
argument_list|(
name|GUIGlobals
operator|.
name|upIconFile
argument_list|)
argument_list|)
expr_stmt|;
name|putValue
argument_list|(
name|SHORT_DESCRIPTION
argument_list|,
literal|"Move string upwards (Ctrl-Up)"
argument_list|)
expr_stmt|;
block|}
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
name|sel
init|=
name|table
operator|.
name|getSelectedRows
argument_list|()
decl_stmt|;
if|if
condition|(
operator|(
name|sel
operator|.
name|length
operator|==
literal|1
operator|)
operator|&&
operator|(
name|sel
index|[
literal|0
index|]
operator|>
literal|0
operator|)
condition|)
block|{
comment|// Make sure no cell is being edited, as caused by the
comment|// keystroke. This makes the content hang on the screen.
name|assureNotEditing
argument_list|()
expr_stmt|;
comment|/*		int col = table.getEditingColumn(), 		    row = table.getEditingRow(); 		    table.getCellEditor(row, col).stopCellEditing();*/
comment|// Store undo information:
name|panel
operator|.
name|undoManager
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableMoveString
argument_list|(
name|panel
argument_list|,
name|base
argument_list|,
name|sel
index|[
literal|0
index|]
argument_list|,
literal|true
argument_list|)
argument_list|)
expr_stmt|;
name|BibtexString
name|bs
init|=
name|base
operator|.
name|getString
argument_list|(
name|sel
index|[
literal|0
index|]
argument_list|)
decl_stmt|;
name|base
operator|.
name|removeString
argument_list|(
name|sel
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
try|try
block|{
name|base
operator|.
name|addString
argument_list|(
name|bs
argument_list|,
name|sel
index|[
literal|0
index|]
operator|-
literal|1
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|KeyCollisionException
name|ex
parameter_list|)
block|{}
name|table
operator|.
name|revalidate
argument_list|()
expr_stmt|;
name|table
operator|.
name|setRowSelectionInterval
argument_list|(
name|sel
index|[
literal|0
index|]
operator|-
literal|1
argument_list|,
name|sel
index|[
literal|0
index|]
operator|-
literal|1
argument_list|)
expr_stmt|;
name|table
operator|.
name|repaint
argument_list|()
expr_stmt|;
name|panel
operator|.
name|markBaseChanged
argument_list|()
expr_stmt|;
block|}
block|}
block|}
DECL|field|stringDownAction
name|StringDownAction
name|stringDownAction
init|=
operator|new
name|StringDownAction
argument_list|()
decl_stmt|;
DECL|class|StringDownAction
class|class
name|StringDownAction
extends|extends
name|AbstractAction
block|{
DECL|method|StringDownAction ()
specifier|public
name|StringDownAction
parameter_list|()
block|{
name|super
argument_list|(
literal|"Move string downwards"
argument_list|,
operator|new
name|ImageIcon
argument_list|(
name|GUIGlobals
operator|.
name|downIconFile
argument_list|)
argument_list|)
expr_stmt|;
name|putValue
argument_list|(
name|SHORT_DESCRIPTION
argument_list|,
literal|"Move string downwards (Ctrl-Down)"
argument_list|)
expr_stmt|;
block|}
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
name|sel
init|=
name|table
operator|.
name|getSelectedRows
argument_list|()
decl_stmt|;
if|if
condition|(
operator|(
name|sel
operator|.
name|length
operator|==
literal|1
operator|)
operator|&&
operator|(
name|sel
index|[
literal|0
index|]
operator|+
literal|1
operator|<
name|base
operator|.
name|getStringCount
argument_list|()
operator|)
condition|)
block|{
comment|// Make sure no cell is being edited, as caused by the
comment|// keystroke. This makes the content hang on the screen.
name|assureNotEditing
argument_list|()
expr_stmt|;
comment|/*int col = table.getEditingColumn(), 		    row = table.getEditingRow(); 		    table.getCellEditor(row, col).stopCellEditing();*/
comment|// Store undo information:
name|panel
operator|.
name|undoManager
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableMoveString
argument_list|(
name|panel
argument_list|,
name|base
argument_list|,
name|sel
index|[
literal|0
index|]
argument_list|,
literal|false
argument_list|)
argument_list|)
expr_stmt|;
name|BibtexString
name|bs
init|=
name|base
operator|.
name|getString
argument_list|(
name|sel
index|[
literal|0
index|]
argument_list|)
decl_stmt|;
name|base
operator|.
name|removeString
argument_list|(
name|sel
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
try|try
block|{
name|base
operator|.
name|addString
argument_list|(
name|bs
argument_list|,
name|sel
index|[
literal|0
index|]
operator|+
literal|1
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|KeyCollisionException
name|ex
parameter_list|)
block|{}
name|table
operator|.
name|revalidate
argument_list|()
expr_stmt|;
name|table
operator|.
name|setRowSelectionInterval
argument_list|(
name|sel
index|[
literal|0
index|]
operator|+
literal|1
argument_list|,
name|sel
index|[
literal|0
index|]
operator|+
literal|1
argument_list|)
expr_stmt|;
name|table
operator|.
name|repaint
argument_list|()
expr_stmt|;
name|panel
operator|.
name|markBaseChanged
argument_list|()
expr_stmt|;
block|}
block|}
block|}
DECL|field|undoAction
name|UndoAction
name|undoAction
init|=
operator|new
name|UndoAction
argument_list|()
decl_stmt|;
DECL|class|UndoAction
class|class
name|UndoAction
extends|extends
name|AbstractAction
block|{
DECL|method|UndoAction ()
specifier|public
name|UndoAction
parameter_list|()
block|{
name|super
argument_list|(
literal|"Undo"
argument_list|,
operator|new
name|ImageIcon
argument_list|(
name|GUIGlobals
operator|.
name|undoIconFile
argument_list|)
argument_list|)
expr_stmt|;
name|putValue
argument_list|(
name|SHORT_DESCRIPTION
argument_list|,
literal|"Undo"
argument_list|)
expr_stmt|;
block|}
DECL|method|actionPerformed (ActionEvent e)
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|panel
operator|.
name|runCommand
argument_list|(
literal|"undo"
argument_list|)
expr_stmt|;
block|}
block|}
DECL|field|redoAction
name|RedoAction
name|redoAction
init|=
operator|new
name|RedoAction
argument_list|()
decl_stmt|;
DECL|class|RedoAction
class|class
name|RedoAction
extends|extends
name|AbstractAction
block|{
DECL|method|RedoAction ()
specifier|public
name|RedoAction
parameter_list|()
block|{
name|super
argument_list|(
literal|"Undo"
argument_list|,
operator|new
name|ImageIcon
argument_list|(
name|GUIGlobals
operator|.
name|redoIconFile
argument_list|)
argument_list|)
expr_stmt|;
name|putValue
argument_list|(
name|SHORT_DESCRIPTION
argument_list|,
literal|"Redo"
argument_list|)
expr_stmt|;
block|}
DECL|method|actionPerformed (ActionEvent e)
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|panel
operator|.
name|runCommand
argument_list|(
literal|"redo"
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

