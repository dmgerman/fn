begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* Copyright (C) 2003 Morten O.Alver& Nizar N. Batada  All programs in this directory and subdirectories are published under the GNU General Public License as described below.  This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.  You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA  Further information about the GNU GPL is available at: http://www.gnu.org/copyleft/gpl.ja.html  */
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
name|util
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
name|javax
operator|.
name|swing
operator|.
name|event
operator|.
name|*
import|;
end_import

begin_comment
comment|//
end_comment

begin_class
DECL|class|KeyBindingsDialog
class|class
name|KeyBindingsDialog
extends|extends
name|JDialog
block|{
DECL|field|list
name|JList
name|list
init|=
operator|new
name|JList
argument_list|()
decl_stmt|;
DECL|field|keyTF
name|JTextField
name|keyTF
init|=
operator|new
name|JTextField
argument_list|()
decl_stmt|;
DECL|field|ok
DECL|field|cancel
DECL|field|grabB
DECL|field|defB
name|JButton
name|ok
decl_stmt|,
name|cancel
decl_stmt|,
name|grabB
decl_stmt|,
name|defB
decl_stmt|;
DECL|field|bindHM
DECL|field|defBinds
name|HashMap
name|bindHM
decl_stmt|,
name|defBinds
decl_stmt|;
DECL|field|clickedSave
name|boolean
name|clickedSave
init|=
literal|false
decl_stmt|;
DECL|method|getAction ()
name|boolean
name|getAction
parameter_list|()
block|{
return|return
name|clickedSave
return|;
block|}
DECL|method|getNewKeyBindings ()
name|HashMap
name|getNewKeyBindings
parameter_list|()
block|{
return|return
name|bindHM
return|;
block|}
DECL|method|KeyBindingsDialog (HashMap name2binding, HashMap defBinds)
specifier|public
name|KeyBindingsDialog
parameter_list|(
name|HashMap
name|name2binding
parameter_list|,
name|HashMap
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
name|Globals
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
name|JScrollPane
name|listScroller
init|=
operator|new
name|JScrollPane
argument_list|(
name|list
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
name|ok
operator|=
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
expr_stmt|;
name|cancel
operator|=
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
expr_stmt|;
name|grabB
operator|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Grab"
argument_list|)
argument_list|)
expr_stmt|;
name|defB
operator|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Default"
argument_list|)
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
name|setTop
argument_list|()
expr_stmt|;
name|setButtons
argument_list|()
expr_stmt|;
name|setList
argument_list|()
expr_stmt|;
name|keyTF
operator|.
name|setEditable
argument_list|(
literal|false
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
DECL|method|setTop ()
name|void
name|setTop
parameter_list|()
block|{
name|Box
name|topBox
init|=
operator|new
name|Box
argument_list|(
name|BoxLayout
operator|.
name|X_AXIS
argument_list|)
decl_stmt|;
name|topBox
operator|.
name|add
argument_list|(
operator|new
name|JLabel
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Binding"
argument_list|)
operator|+
literal|":"
argument_list|,
name|JLabel
operator|.
name|RIGHT
argument_list|)
argument_list|)
expr_stmt|;
name|topBox
operator|.
name|add
argument_list|(
name|keyTF
argument_list|)
expr_stmt|;
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|topBox
argument_list|,
name|BorderLayout
operator|.
name|NORTH
argument_list|)
expr_stmt|;
block|}
comment|//##################################################
comment|// respond to grabKey and display the key binding
comment|//##################################################
DECL|class|JBM_CustomKeyBindingsListener
specifier|public
class|class
name|JBM_CustomKeyBindingsListener
extends|extends
name|KeyAdapter
block|{
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
name|Object
index|[]
name|selected
init|=
name|list
operator|.
name|getSelectedValues
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
return|return;
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
name|mod
operator|.
name|equals
argument_list|(
literal|""
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
operator|(
name|kc
operator|<
name|KeyEvent
operator|.
name|VK_F1
operator|)
operator|&&
operator|(
name|kc
operator|>
name|KeyEvent
operator|.
name|VK_F12
operator|)
operator|&&
operator|(
name|kc
operator|!=
name|KeyEvent
operator|.
name|VK_ESCAPE
operator|)
operator|&&
operator|(
name|kc
operator|!=
name|KeyEvent
operator|.
name|VK_DELETE
operator|)
condition|)
return|return;
comment|// need a modifier except for function keys
block|}
comment|// second key cannot be a modifiers
comment|//if ( evt.isActionKey()) {
comment|//Util.pr(code);
if|if
condition|(
comment|//code.equals("Escape")
name|code
operator|.
name|equals
argument_list|(
literal|"Tab"
argument_list|)
operator|||
name|code
operator|.
name|equals
argument_list|(
literal|"Backspace"
argument_list|)
operator|||
name|code
operator|.
name|equals
argument_list|(
literal|"Enter"
argument_list|)
comment|//|| code.equals("Delete")
operator|||
name|code
operator|.
name|equals
argument_list|(
literal|"Space"
argument_list|)
operator|||
name|code
operator|.
name|equals
argument_list|(
literal|"Ctrl"
argument_list|)
operator|||
name|code
operator|.
name|equals
argument_list|(
literal|"Shift"
argument_list|)
operator|||
name|code
operator|.
name|equals
argument_list|(
literal|"Alt"
argument_list|)
condition|)
return|return;
comment|//}
name|String
name|newKey
decl_stmt|;
if|if
condition|(
operator|!
name|mod
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
name|newKey
operator|=
name|mod
operator|.
name|toLowerCase
argument_list|()
operator|+
literal|" "
operator|+
name|code
expr_stmt|;
else|else
name|newKey
operator|=
name|code
expr_stmt|;
name|keyTF
operator|.
name|setText
argument_list|(
name|newKey
argument_list|)
expr_stmt|;
comment|//find which key is selected and set its value int the bindHM
name|String
name|selectedFunction
init|=
operator|(
name|String
operator|)
name|list
operator|.
name|getSelectedValue
argument_list|()
decl_stmt|;
comment|// log print
comment|// System.out.println("selectedfunction " + selectedFunction + " new key: " + newKey);
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
comment|//##################################################
comment|// put the corresponding key binding into keyTF
comment|//##################################################
DECL|class|MyListSelectionListener
class|class
name|MyListSelectionListener
implements|implements
name|ListSelectionListener
block|{
comment|// This method is called each time the user changes the set of selected items
DECL|method|valueChanged (ListSelectionEvent evt)
specifier|public
name|void
name|valueChanged
parameter_list|(
name|ListSelectionEvent
name|evt
parameter_list|)
block|{
comment|// When the user release the mouse button and completes the selection,
comment|// getValueIsAdjusting() becomes false
if|if
condition|(
operator|!
name|evt
operator|.
name|getValueIsAdjusting
argument_list|()
condition|)
block|{
name|JList
name|list
init|=
operator|(
name|JList
operator|)
name|evt
operator|.
name|getSource
argument_list|()
decl_stmt|;
comment|// Get all selected items
name|Object
index|[]
name|selected
init|=
name|list
operator|.
name|getSelectedValues
argument_list|()
decl_stmt|;
comment|// Iterate all selected items
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|selected
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|Object
name|sel
init|=
name|selected
index|[
name|i
index|]
decl_stmt|;
name|keyTF
operator|.
name|setText
argument_list|(
operator|(
name|String
operator|)
name|bindHM
operator|.
name|get
argument_list|(
name|sel
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
DECL|class|MyListDataListener
class|class
name|MyListDataListener
implements|implements
name|ListDataListener
block|{
comment|// This method is called when new items have been added to the list
DECL|method|intervalAdded (ListDataEvent evt)
specifier|public
name|void
name|intervalAdded
parameter_list|(
name|ListDataEvent
name|evt
parameter_list|)
block|{
name|DefaultListModel
name|model
init|=
operator|(
name|DefaultListModel
operator|)
name|evt
operator|.
name|getSource
argument_list|()
decl_stmt|;
comment|// Get range of new  items
name|int
name|start
init|=
name|evt
operator|.
name|getIndex0
argument_list|()
decl_stmt|;
name|int
name|end
init|=
name|evt
operator|.
name|getIndex1
argument_list|()
decl_stmt|;
name|int
name|count
init|=
name|end
operator|-
name|start
operator|+
literal|1
decl_stmt|;
comment|// Get new items
for|for
control|(
name|int
name|i
init|=
name|start
init|;
name|i
operator|<=
name|end
condition|;
name|i
operator|++
control|)
block|{
name|Object
name|item
init|=
name|model
operator|.
name|getElementAt
argument_list|(
name|i
argument_list|)
decl_stmt|;
block|}
block|}
comment|// This method is called when items have been removed from the list
DECL|method|intervalRemoved (ListDataEvent evt)
specifier|public
name|void
name|intervalRemoved
parameter_list|(
name|ListDataEvent
name|evt
parameter_list|)
block|{
comment|// Get range of removed items
name|int
name|start
init|=
name|evt
operator|.
name|getIndex0
argument_list|()
decl_stmt|;
name|int
name|end
init|=
name|evt
operator|.
name|getIndex1
argument_list|()
decl_stmt|;
name|int
name|count
init|=
name|end
operator|-
name|start
operator|+
literal|1
decl_stmt|;
comment|// The removed items are not available
block|}
comment|// This method is called when items in the list are replaced
DECL|method|contentsChanged (ListDataEvent evt)
specifier|public
name|void
name|contentsChanged
parameter_list|(
name|ListDataEvent
name|evt
parameter_list|)
block|{
name|DefaultListModel
name|model
init|=
operator|(
name|DefaultListModel
operator|)
name|evt
operator|.
name|getSource
argument_list|()
decl_stmt|;
comment|// Get range of changed items
name|int
name|start
init|=
name|evt
operator|.
name|getIndex0
argument_list|()
decl_stmt|;
name|int
name|end
init|=
name|evt
operator|.
name|getIndex1
argument_list|()
decl_stmt|;
name|int
name|count
init|=
name|end
operator|-
name|start
operator|+
literal|1
decl_stmt|;
comment|// Get changed items
for|for
control|(
name|int
name|i
init|=
name|start
init|;
name|i
operator|<=
name|end
condition|;
name|i
operator|++
control|)
block|{
name|Object
name|item
init|=
name|model
operator|.
name|getElementAt
argument_list|(
name|i
argument_list|)
decl_stmt|;
block|}
block|}
block|}
comment|//setup so that clicking on list will display the current binding
DECL|method|setList ()
name|void
name|setList
parameter_list|()
block|{
name|list
operator|.
name|setSelectionMode
argument_list|(
name|ListSelectionModel
operator|.
name|SINGLE_SELECTION
argument_list|)
expr_stmt|;
name|list
operator|.
name|getModel
argument_list|()
operator|.
name|addListDataListener
argument_list|(
operator|new
name|MyListDataListener
argument_list|()
argument_list|)
expr_stmt|;
comment|// This method is called each time the user changes the set of selected items
name|list
operator|.
name|addListSelectionListener
argument_list|(
operator|new
name|MyListSelectionListener
argument_list|()
argument_list|)
expr_stmt|;
name|DefaultListModel
name|listModel
init|=
operator|new
name|DefaultListModel
argument_list|()
decl_stmt|;
name|TreeMap
name|sorted
init|=
operator|new
name|TreeMap
argument_list|(
name|bindHM
argument_list|)
decl_stmt|;
name|Iterator
name|it
init|=
name|sorted
operator|.
name|keySet
argument_list|()
operator|.
name|iterator
argument_list|()
decl_stmt|;
while|while
condition|(
name|it
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|listModel
operator|.
name|addElement
argument_list|(
name|it
operator|.
name|next
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|list
operator|.
name|setModel
argument_list|(
name|listModel
argument_list|)
expr_stmt|;
name|list
operator|.
name|setSelectionInterval
argument_list|(
literal|0
argument_list|,
literal|0
argument_list|)
expr_stmt|;
comment|//select the first entry
block|}
comment|// listners
DECL|method|setButtons ()
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
comment|// message: key bindings will take into effect next time you start JBM
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
comment|//System.exit(-1);//get rid of this
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
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|Object
index|[]
name|selected
init|=
name|list
operator|.
name|getSelectedValues
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
return|return;
name|keyTF
operator|.
name|setText
argument_list|(
name|setToDefault
argument_list|(
operator|(
name|String
operator|)
name|list
operator|.
name|getSelectedValue
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
DECL|method|setToDefault (String name)
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
operator|(
name|String
operator|)
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
comment|/*     public static void main(String args[])     { 	HashMap h=new HashMap(); 	h.put("new-bibtex","ctrl N"); 	h.put("edit-bibtex","ctrl E"); 	h.put("exit-bibtex","ctrl Q"); 	KeyBindingsDialog d= new KeyBindingsDialog(h); 	d.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 	d.setSize(200,300); 	d.setVisible(true);  	}*/
block|}
end_class

end_unit

