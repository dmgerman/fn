begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Copyright (C) 2003 Morten O. Alver, Nizar N. Batada  *  * All programs in this directory and subdirectories are published under the GNU  * General Public License as described below.  *  * This program is free software; you can redistribute it and/or modify it under  * the terms of the GNU General Public License as published by the Free Software  * Foundation; either version 2 of the License, or (at your option) any later  * version.  *  * This program is distributed in the hope that it will be useful, but WITHOUT  * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS  * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more  * details.  *  * You should have received a copy of the GNU General Public License along with  * this program; if not, write to the Free Software Foundation, Inc., 59 Temple  * Place, Suite 330, Boston, MA 02111-1307 USA  *  * Further information about the GNU GPL is available at:  * http://www.gnu.org/copyleft/gpl.ja.html  *  */
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
name|text
operator|.
name|JTextComponent
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
name|java
operator|.
name|awt
operator|.
name|GridBagLayout
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|GridBagConstraints
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
name|Insets
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|AWTKeyStroke
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|KeyboardFocusManager
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

begin_class
DECL|class|EntryEditorTab
specifier|public
class|class
name|EntryEditorTab
block|{
DECL|field|panel
specifier|private
name|JPanel
name|panel
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
DECL|field|fields
specifier|private
name|String
index|[]
name|fields
decl_stmt|;
DECL|field|ARRAY
specifier|private
specifier|final
specifier|static
name|Object
index|[]
name|ARRAY
init|=
operator|new
name|String
index|[
literal|0
index|]
decl_stmt|;
DECL|field|parent
specifier|private
name|EntryEditor
name|parent
decl_stmt|;
DECL|field|editors
specifier|private
name|HashMap
name|editors
init|=
operator|new
name|HashMap
argument_list|()
decl_stmt|;
DECL|field|activeField
specifier|private
name|FieldEditor
name|activeField
init|=
literal|null
decl_stmt|;
DECL|field|sp
specifier|private
name|JScrollPane
name|sp
init|=
operator|new
name|JScrollPane
argument_list|(
name|panel
argument_list|,
name|JScrollPane
operator|.
name|VERTICAL_SCROLLBAR_AS_NEEDED
argument_list|,
name|JScrollPane
operator|.
name|HORIZONTAL_SCROLLBAR_NEVER
argument_list|)
decl_stmt|;
comment|//    private BibtexEntry entry;
DECL|method|EntryEditorTab (List fields, EntryEditor parent, boolean addKeyField)
specifier|public
name|EntryEditorTab
parameter_list|(
name|List
name|fields
parameter_list|,
name|EntryEditor
name|parent
parameter_list|,
name|boolean
name|addKeyField
parameter_list|)
block|{
if|if
condition|(
name|fields
operator|!=
literal|null
condition|)
name|this
operator|.
name|fields
operator|=
operator|(
name|String
index|[]
operator|)
name|fields
operator|.
name|toArray
argument_list|(
name|ARRAY
argument_list|)
expr_stmt|;
else|else
name|this
operator|.
name|fields
operator|=
operator|new
name|String
index|[]
block|{}
expr_stmt|;
name|this
operator|.
name|parent
operator|=
name|parent
expr_stmt|;
name|setupPanel
argument_list|(
name|addKeyField
argument_list|)
expr_stmt|;
comment|// The following line makes sure focus cycles inside tab instead of being lost
comment|// to other parts of the frame:
name|panel
operator|.
name|setFocusCycleRoot
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
DECL|method|setupPanel (boolean addKeyField)
specifier|private
specifier|final
name|void
name|setupPanel
parameter_list|(
name|boolean
name|addKeyField
parameter_list|)
block|{
name|GridBagLayout
name|gbl
init|=
operator|new
name|GridBagLayout
argument_list|()
decl_stmt|;
name|GridBagConstraints
name|con
init|=
operator|new
name|GridBagConstraints
argument_list|()
decl_stmt|;
name|panel
operator|.
name|setLayout
argument_list|(
name|gbl
argument_list|)
expr_stmt|;
name|double
name|totalWeight
init|=
literal|0
decl_stmt|;
comment|//panel.setOpaque(true);
comment|//panel.setBackground(java.awt.Color.white);
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
comment|// Create the text area:
name|FieldTextArea
name|ta
init|=
operator|new
name|FieldTextArea
argument_list|(
name|fields
index|[
name|i
index|]
argument_list|,
literal|null
argument_list|)
decl_stmt|;
comment|//stringContent);
name|JComponent
name|ex
init|=
name|parent
operator|.
name|getExtra
argument_list|(
name|fields
index|[
name|i
index|]
argument_list|,
name|ta
argument_list|)
decl_stmt|;
comment|// Attach listeners and key bindings:
name|setupJTextComponent
argument_list|(
name|ta
argument_list|)
expr_stmt|;
name|ta
operator|.
name|addFocusListener
argument_list|(
operator|new
name|FieldListener
argument_list|(
name|ta
argument_list|)
argument_list|)
expr_stmt|;
comment|// Store the editor for later reference:
name|editors
operator|.
name|put
argument_list|(
name|fields
index|[
name|i
index|]
argument_list|,
name|ta
argument_list|)
expr_stmt|;
if|if
condition|(
name|i
operator|==
literal|0
condition|)
name|activeField
operator|=
name|ta
expr_stmt|;
comment|// The label for this field:
name|con
operator|.
name|insets
operator|=
operator|new
name|Insets
argument_list|(
literal|5
argument_list|,
literal|5
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|con
operator|.
name|anchor
operator|=
name|GridBagConstraints
operator|.
name|WEST
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
name|gridwidth
operator|=
literal|1
expr_stmt|;
name|con
operator|.
name|weightx
operator|=
literal|0
expr_stmt|;
name|con
operator|.
name|weighty
operator|=
literal|0
expr_stmt|;
name|con
operator|.
name|anchor
operator|=
name|GridBagConstraints
operator|.
name|NORTH
expr_stmt|;
name|con
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|BOTH
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|ta
operator|.
name|getLabel
argument_list|()
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|panel
operator|.
name|add
argument_list|(
name|ta
operator|.
name|getLabel
argument_list|()
argument_list|)
expr_stmt|;
comment|// The field itself:
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
name|weightx
operator|=
literal|1
expr_stmt|;
name|con
operator|.
name|weighty
operator|=
name|GUIGlobals
operator|.
name|getFieldWeight
argument_list|(
name|fields
index|[
name|i
index|]
argument_list|)
expr_stmt|;
name|totalWeight
operator|+=
name|con
operator|.
name|weighty
expr_stmt|;
comment|// The gridwidth depends on whether we will add an extra component to the right:
if|if
condition|(
name|ex
operator|!=
literal|null
condition|)
name|con
operator|.
name|gridwidth
operator|=
literal|1
expr_stmt|;
else|else
name|con
operator|.
name|gridwidth
operator|=
name|GridBagConstraints
operator|.
name|REMAINDER
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|ta
operator|.
name|getPane
argument_list|()
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|panel
operator|.
name|add
argument_list|(
name|ta
operator|.
name|getPane
argument_list|()
argument_list|)
expr_stmt|;
comment|// Possibly an extra component:
if|if
condition|(
name|ex
operator|!=
literal|null
condition|)
block|{
name|con
operator|.
name|gridwidth
operator|=
name|GridBagConstraints
operator|.
name|REMAINDER
expr_stmt|;
name|con
operator|.
name|anchor
operator|=
name|GridBagConstraints
operator|.
name|NORTH
expr_stmt|;
name|con
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|HORIZONTAL
expr_stmt|;
name|con
operator|.
name|weightx
operator|=
literal|0
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|ex
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|panel
operator|.
name|add
argument_list|(
name|ex
argument_list|)
expr_stmt|;
block|}
block|}
comment|// Add the edit field for Bibtex-key.
if|if
condition|(
name|addKeyField
condition|)
block|{
name|con
operator|.
name|insets
operator|.
name|top
operator|+=
literal|25
expr_stmt|;
name|con
operator|.
name|insets
operator|.
name|bottom
operator|=
literal|10
expr_stmt|;
name|con
operator|.
name|gridwidth
operator|=
literal|1
expr_stmt|;
name|con
operator|.
name|weighty
operator|=
literal|0
expr_stmt|;
name|con
operator|.
name|weightx
operator|=
literal|0
expr_stmt|;
name|con
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|HORIZONTAL
expr_stmt|;
name|con
operator|.
name|anchor
operator|=
name|GridBagConstraints
operator|.
name|SOUTHWEST
expr_stmt|;
name|FieldTextField
name|tf
init|=
operator|new
name|FieldTextField
argument_list|(
name|Globals
operator|.
name|KEY_FIELD
argument_list|,
operator|(
name|String
operator|)
name|parent
operator|.
name|entry
operator|.
name|getField
argument_list|(
name|Globals
operator|.
name|KEY_FIELD
argument_list|)
argument_list|)
decl_stmt|;
comment|//(String) entry.getField(Globals.KEY_FIELD));
name|editors
operator|.
name|put
argument_list|(
literal|"bibtexkey"
argument_list|,
name|tf
argument_list|)
expr_stmt|;
comment|// If the key field is the only field, we should have only one editor, and this one should be set
comment|// as active initially:
if|if
condition|(
name|editors
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
name|activeField
operator|=
name|tf
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|tf
operator|.
name|getLabel
argument_list|()
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|panel
operator|.
name|add
argument_list|(
name|tf
operator|.
name|getLabel
argument_list|()
argument_list|)
expr_stmt|;
name|con
operator|.
name|gridwidth
operator|=
name|GridBagConstraints
operator|.
name|REMAINDER
expr_stmt|;
name|con
operator|.
name|weightx
operator|=
literal|1
expr_stmt|;
name|setupJTextComponent
argument_list|(
name|tf
argument_list|)
expr_stmt|;
name|tf
operator|.
name|addFocusListener
argument_list|(
operator|new
name|FieldListener
argument_list|(
name|tf
argument_list|)
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|tf
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|panel
operator|.
name|add
argument_list|(
name|tf
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|setActive (FieldEditor c)
specifier|public
name|void
name|setActive
parameter_list|(
name|FieldEditor
name|c
parameter_list|)
block|{
name|activeField
operator|=
name|c
expr_stmt|;
comment|//System.out.println(c.toString());
block|}
DECL|method|getActive ()
specifier|public
name|FieldEditor
name|getActive
parameter_list|()
block|{
return|return
name|activeField
return|;
block|}
DECL|method|getFields ()
specifier|public
name|List
name|getFields
parameter_list|()
block|{
return|return
name|java
operator|.
name|util
operator|.
name|Arrays
operator|.
name|asList
argument_list|(
name|fields
argument_list|)
return|;
block|}
DECL|method|activate ()
specifier|public
name|void
name|activate
parameter_list|()
block|{
if|if
condition|(
name|activeField
operator|!=
literal|null
condition|)
name|activeField
operator|.
name|requestFocus
argument_list|()
expr_stmt|;
comment|//System.out.println("Activate, hurra");
block|}
DECL|method|setEntry (BibtexEntry entry)
specifier|public
name|void
name|setEntry
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|)
block|{
for|for
control|(
name|Iterator
name|i
init|=
name|editors
operator|.
name|keySet
argument_list|()
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
name|String
name|field
init|=
operator|(
name|String
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|FieldEditor
name|ed
init|=
operator|(
name|FieldEditor
operator|)
name|editors
operator|.
name|get
argument_list|(
name|field
argument_list|)
decl_stmt|;
name|Object
name|content
init|=
name|entry
operator|.
name|getField
argument_list|(
name|ed
operator|.
name|getFieldName
argument_list|()
argument_list|)
decl_stmt|;
name|ed
operator|.
name|setText
argument_list|(
operator|(
name|content
operator|==
literal|null
operator|)
condition|?
literal|""
else|:
name|content
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|updateField (String field, String content)
specifier|public
name|boolean
name|updateField
parameter_list|(
name|String
name|field
parameter_list|,
name|String
name|content
parameter_list|)
block|{
if|if
condition|(
operator|!
name|editors
operator|.
name|containsKey
argument_list|(
name|field
argument_list|)
condition|)
return|return
literal|false
return|;
name|FieldEditor
name|ed
init|=
operator|(
name|FieldEditor
operator|)
name|editors
operator|.
name|get
argument_list|(
name|field
argument_list|)
decl_stmt|;
name|ed
operator|.
name|setText
argument_list|(
name|content
argument_list|)
expr_stmt|;
return|return
literal|true
return|;
block|}
DECL|method|validateAllFields ()
specifier|public
name|void
name|validateAllFields
parameter_list|()
block|{
for|for
control|(
name|Iterator
name|i
init|=
name|editors
operator|.
name|keySet
argument_list|()
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
name|String
name|field
init|=
operator|(
name|String
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|FieldEditor
name|ed
init|=
operator|(
name|FieldEditor
operator|)
name|editors
operator|.
name|get
argument_list|(
name|field
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
operator|(
name|Component
operator|)
name|ed
operator|)
operator|.
name|hasFocus
argument_list|()
condition|)
name|ed
operator|.
name|setBackground
argument_list|(
name|GUIGlobals
operator|.
name|activeEditor
argument_list|)
expr_stmt|;
else|else
name|ed
operator|.
name|setBackground
argument_list|(
name|GUIGlobals
operator|.
name|validFieldBackground
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|setEnabled (boolean enabled)
specifier|public
name|void
name|setEnabled
parameter_list|(
name|boolean
name|enabled
parameter_list|)
block|{
for|for
control|(
name|Iterator
name|i
init|=
name|editors
operator|.
name|keySet
argument_list|()
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
name|String
name|field
init|=
operator|(
name|String
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|FieldEditor
name|ed
init|=
operator|(
name|FieldEditor
operator|)
name|editors
operator|.
name|get
argument_list|(
name|field
argument_list|)
decl_stmt|;
name|ed
operator|.
name|setEnabled
argument_list|(
name|enabled
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|getPane ()
specifier|public
name|Component
name|getPane
parameter_list|()
block|{
return|return
name|panel
return|;
block|}
DECL|method|setupJTextComponent (JTextComponent ta)
specifier|public
name|void
name|setupJTextComponent
parameter_list|(
name|JTextComponent
name|ta
parameter_list|)
block|{
comment|// Activate autocompletion if it should be used for this field.
comment|// Set up key bindings and focus listener for the FieldEditor.
name|InputMap
name|im
init|=
name|ta
operator|.
name|getInputMap
argument_list|(
name|JComponent
operator|.
name|WHEN_FOCUSED
argument_list|)
decl_stmt|;
name|ActionMap
name|am
init|=
name|ta
operator|.
name|getActionMap
argument_list|()
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
literal|"Entry editor, previous entry"
argument_list|)
argument_list|,
literal|"prev"
argument_list|)
expr_stmt|;
name|am
operator|.
name|put
argument_list|(
literal|"prev"
argument_list|,
name|parent
operator|.
name|prevEntryAction
argument_list|)
expr_stmt|;
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
literal|"Entry editor, next entry"
argument_list|)
argument_list|,
literal|"next"
argument_list|)
expr_stmt|;
name|am
operator|.
name|put
argument_list|(
literal|"next"
argument_list|,
name|parent
operator|.
name|nextEntryAction
argument_list|)
expr_stmt|;
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
literal|"Entry editor, store field"
argument_list|)
argument_list|,
literal|"store"
argument_list|)
expr_stmt|;
name|am
operator|.
name|put
argument_list|(
literal|"store"
argument_list|,
name|parent
operator|.
name|storeFieldAction
argument_list|)
expr_stmt|;
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
literal|"Entry editor, next panel"
argument_list|)
argument_list|,
literal|"right"
argument_list|)
expr_stmt|;
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
literal|"Entry editor, next panel 2"
argument_list|)
argument_list|,
literal|"right"
argument_list|)
expr_stmt|;
name|am
operator|.
name|put
argument_list|(
literal|"left"
argument_list|,
name|parent
operator|.
name|switchLeftAction
argument_list|)
expr_stmt|;
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
literal|"Entry editor, previous panel"
argument_list|)
argument_list|,
literal|"left"
argument_list|)
expr_stmt|;
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
literal|"Entry editor, previous panel 2"
argument_list|)
argument_list|,
literal|"left"
argument_list|)
expr_stmt|;
name|am
operator|.
name|put
argument_list|(
literal|"right"
argument_list|,
name|parent
operator|.
name|switchRightAction
argument_list|)
expr_stmt|;
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
literal|"Help"
argument_list|)
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
name|parent
operator|.
name|helpAction
argument_list|)
expr_stmt|;
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
literal|"Save database"
argument_list|)
argument_list|,
literal|"save"
argument_list|)
expr_stmt|;
name|am
operator|.
name|put
argument_list|(
literal|"save"
argument_list|,
name|parent
operator|.
name|saveDatabaseAction
argument_list|)
expr_stmt|;
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
literal|"Next tab"
argument_list|)
argument_list|,
literal|"nexttab"
argument_list|)
expr_stmt|;
name|am
operator|.
name|put
argument_list|(
literal|"nexttab"
argument_list|,
name|parent
operator|.
name|frame
operator|.
name|nextTab
argument_list|)
expr_stmt|;
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
literal|"Previous tab"
argument_list|)
argument_list|,
literal|"prevtab"
argument_list|)
expr_stmt|;
name|am
operator|.
name|put
argument_list|(
literal|"prevtab"
argument_list|,
name|parent
operator|.
name|frame
operator|.
name|prevTab
argument_list|)
expr_stmt|;
try|try
block|{
name|HashSet
name|keys
init|=
operator|new
name|HashSet
argument_list|(
name|ta
operator|.
name|getFocusTraversalKeys
argument_list|(
name|KeyboardFocusManager
operator|.
name|FORWARD_TRAVERSAL_KEYS
argument_list|)
argument_list|)
decl_stmt|;
name|keys
operator|.
name|clear
argument_list|()
expr_stmt|;
name|keys
operator|.
name|add
argument_list|(
name|AWTKeyStroke
operator|.
name|getAWTKeyStroke
argument_list|(
literal|"pressed TAB"
argument_list|)
argument_list|)
expr_stmt|;
name|ta
operator|.
name|setFocusTraversalKeys
argument_list|(
name|KeyboardFocusManager
operator|.
name|FORWARD_TRAVERSAL_KEYS
argument_list|,
name|keys
argument_list|)
expr_stmt|;
name|keys
operator|=
operator|new
name|HashSet
argument_list|(
name|ta
operator|.
name|getFocusTraversalKeys
argument_list|(
name|KeyboardFocusManager
operator|.
name|BACKWARD_TRAVERSAL_KEYS
argument_list|)
argument_list|)
expr_stmt|;
name|keys
operator|.
name|clear
argument_list|()
expr_stmt|;
name|keys
operator|.
name|add
argument_list|(
name|KeyStroke
operator|.
name|getKeyStroke
argument_list|(
literal|"shift pressed TAB"
argument_list|)
argument_list|)
expr_stmt|;
name|ta
operator|.
name|setFocusTraversalKeys
argument_list|(
name|KeyboardFocusManager
operator|.
name|BACKWARD_TRAVERSAL_KEYS
argument_list|,
name|keys
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Throwable
name|t
parameter_list|)
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
name|t
argument_list|)
expr_stmt|;
block|}
block|}
comment|/*      * Focus listener that fires the storeFieldAction when a FieldTextArea loses      * focus.      */
DECL|class|FieldListener
class|class
name|FieldListener
extends|extends
name|FocusAdapter
block|{
DECL|field|fe
name|FieldEditor
name|fe
decl_stmt|;
DECL|method|FieldListener (FieldEditor fe)
specifier|public
name|FieldListener
parameter_list|(
name|FieldEditor
name|fe
parameter_list|)
block|{
name|this
operator|.
name|fe
operator|=
name|fe
expr_stmt|;
block|}
DECL|method|focusGained (FocusEvent e)
specifier|public
name|void
name|focusGained
parameter_list|(
name|FocusEvent
name|e
parameter_list|)
block|{
name|setActive
argument_list|(
name|fe
argument_list|)
expr_stmt|;
block|}
DECL|method|focusLost (FocusEvent e)
specifier|public
name|void
name|focusLost
parameter_list|(
name|FocusEvent
name|e
parameter_list|)
block|{
if|if
condition|(
operator|!
name|e
operator|.
name|isTemporary
argument_list|()
condition|)
name|parent
operator|.
name|updateField
argument_list|(
name|fe
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

