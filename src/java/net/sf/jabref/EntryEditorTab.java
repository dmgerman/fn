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
name|KeyboardFocusManager
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Rectangle
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
name|FocusEvent
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
name|FocusListener
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
name|HashSet
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
name|List
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
name|InputMap
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
name|KeyStroke
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
name|DocumentEvent
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
name|DocumentListener
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|autocompleter
operator|.
name|AbstractAutoCompleter
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
name|AutoCompleteListener
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
name|FileListEditor
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
name|DefaultFormBuilder
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

begin_comment
comment|/**  * A single tab displayed in the EntryEditor holding several FieldEditors.  *   * @author $Author$  * @version $Revision$ ($Date$)  *   */
end_comment

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
DECL|field|scrollPane
specifier|private
name|JScrollPane
name|scrollPane
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
DECL|field|fields
specifier|private
name|String
index|[]
name|fields
decl_stmt|;
DECL|field|parent
specifier|private
name|EntryEditor
name|parent
decl_stmt|;
DECL|field|editors
specifier|private
name|HashMap
argument_list|<
name|String
argument_list|,
name|FieldEditor
argument_list|>
name|editors
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|FieldEditor
argument_list|>
argument_list|()
decl_stmt|;
DECL|field|activeField
specifier|private
name|FieldEditor
name|activeField
init|=
literal|null
decl_stmt|;
DECL|method|EntryEditorTab ()
specifier|protected
name|EntryEditorTab
parameter_list|()
block|{      }
DECL|method|EntryEditorTab (JabRefFrame frame, BasePanel panel, List<String> fields, EntryEditor parent, boolean addKeyField, String name)
specifier|public
name|EntryEditorTab
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|BasePanel
name|panel
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|fields
parameter_list|,
name|EntryEditor
name|parent
parameter_list|,
name|boolean
name|addKeyField
parameter_list|,
name|String
name|name
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
name|fields
operator|.
name|toArray
argument_list|(
operator|new
name|String
index|[
literal|0
index|]
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
name|frame
argument_list|,
name|panel
argument_list|,
name|addKeyField
argument_list|,
name|name
argument_list|)
expr_stmt|;
comment|/* 		 * The following line makes sure focus cycles inside tab instead of 		 * being lost to other parts of the frame: 		 */
name|scrollPane
operator|.
name|setFocusCycleRoot
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
DECL|method|setupPanel (JabRefFrame frame, BasePanel bPanel, boolean addKeyField, String title)
name|void
name|setupPanel
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|BasePanel
name|bPanel
parameter_list|,
name|boolean
name|addKeyField
parameter_list|,
name|String
name|title
parameter_list|)
block|{
name|InputMap
name|im
init|=
name|panel
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
name|panel
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
name|panel
operator|.
name|setName
argument_list|(
name|title
argument_list|)
expr_stmt|;
comment|//String rowSpec = "left:pref, 4dlu, fill:pref:grow, 4dlu, fill:pref";
name|String
name|colSpec
init|=
literal|"fill:pref, 1dlu, fill:pref:grow, 1dlu, fill:pref"
decl_stmt|;
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
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
name|fields
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|"fill:pref:grow, "
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|addKeyField
condition|)
name|sb
operator|.
name|append
argument_list|(
literal|"4dlu, fill:pref"
argument_list|)
expr_stmt|;
else|else
name|sb
operator|.
name|delete
argument_list|(
name|sb
operator|.
name|length
argument_list|()
operator|-
literal|2
argument_list|,
name|sb
operator|.
name|length
argument_list|()
argument_list|)
expr_stmt|;
name|String
name|rowSpec
init|=
name|sb
operator|.
name|toString
argument_list|()
decl_stmt|;
name|DefaultFormBuilder
name|builder
init|=
operator|new
name|DefaultFormBuilder
argument_list|(
operator|new
name|FormLayout
argument_list|(
name|colSpec
argument_list|,
name|rowSpec
argument_list|)
argument_list|,
name|panel
argument_list|)
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
name|fields
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
comment|// Create the text area:
name|int
name|editorType
init|=
name|BibtexFields
operator|.
name|getEditorType
argument_list|(
name|fields
index|[
name|i
index|]
argument_list|)
decl_stmt|;
specifier|final
name|FieldEditor
name|ta
decl_stmt|;
name|int
name|defaultHeight
decl_stmt|;
name|int
name|wHeight
init|=
call|(
name|int
call|)
argument_list|(
literal|50.0
operator|*
name|BibtexFields
operator|.
name|getFieldWeight
argument_list|(
name|fields
index|[
name|i
index|]
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|editorType
operator|==
name|GUIGlobals
operator|.
name|FILE_LIST_EDITOR
condition|)
block|{
name|ta
operator|=
operator|new
name|FileListEditor
argument_list|(
name|frame
argument_list|,
name|bPanel
operator|.
name|metaData
argument_list|()
argument_list|,
name|fields
index|[
name|i
index|]
argument_list|,
literal|null
argument_list|,
name|parent
argument_list|)
expr_stmt|;
name|defaultHeight
operator|=
literal|0
expr_stmt|;
block|}
else|else
block|{
name|ta
operator|=
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
expr_stmt|;
name|frame
operator|.
name|getSearchManager
argument_list|()
operator|.
name|addSearchListener
argument_list|(
operator|(
name|FieldTextArea
operator|)
name|ta
argument_list|)
expr_stmt|;
name|defaultHeight
operator|=
name|ta
operator|.
name|getPane
argument_list|()
operator|.
name|getPreferredSize
argument_list|()
operator|.
name|height
expr_stmt|;
block|}
comment|//ta.addUndoableEditListener(bPanel.undoListener);
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
comment|// Add autocompleter listener, if required for this field:
name|AbstractAutoCompleter
name|autoComp
init|=
name|bPanel
operator|.
name|getAutoCompleter
argument_list|(
name|fields
index|[
name|i
index|]
argument_list|)
decl_stmt|;
name|AutoCompleteListener
name|acl
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|autoComp
operator|!=
literal|null
condition|)
block|{
name|acl
operator|=
operator|new
name|AutoCompleteListener
argument_list|(
name|autoComp
argument_list|)
expr_stmt|;
block|}
name|setupJTextComponent
argument_list|(
name|ta
operator|.
name|getTextComponent
argument_list|()
argument_list|,
name|acl
argument_list|)
expr_stmt|;
name|ta
operator|.
name|setAutoCompleteListener
argument_list|(
name|acl
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
comment|//System.out.println(fields[i]+": "+BibtexFields.getFieldWeight(fields[i]));
name|ta
operator|.
name|getPane
argument_list|()
operator|.
name|setPreferredSize
argument_list|(
operator|new
name|Dimension
argument_list|(
literal|100
argument_list|,
name|Math
operator|.
name|max
argument_list|(
name|defaultHeight
argument_list|,
name|wHeight
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|ta
operator|.
name|getLabel
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|ex
operator|==
literal|null
condition|)
name|builder
operator|.
name|append
argument_list|(
name|ta
operator|.
name|getPane
argument_list|()
argument_list|,
literal|3
argument_list|)
expr_stmt|;
else|else
block|{
name|builder
operator|.
name|append
argument_list|(
name|ta
operator|.
name|getPane
argument_list|()
argument_list|)
expr_stmt|;
name|JPanel
name|pan
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|pan
operator|.
name|setLayout
argument_list|(
operator|new
name|BorderLayout
argument_list|()
argument_list|)
expr_stmt|;
name|pan
operator|.
name|add
argument_list|(
name|ex
argument_list|,
name|BorderLayout
operator|.
name|NORTH
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|pan
argument_list|)
expr_stmt|;
block|}
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
block|}
comment|// Add the edit field for Bibtex-key.
if|if
condition|(
name|addKeyField
condition|)
block|{
specifier|final
name|FieldTextField
name|tf
init|=
operator|new
name|FieldTextField
argument_list|(
name|BibtexFields
operator|.
name|KEY_FIELD
argument_list|,
name|parent
operator|.
name|getEntry
argument_list|()
operator|.
name|getField
argument_list|(
name|BibtexFields
operator|.
name|KEY_FIELD
argument_list|)
argument_list|,
literal|true
argument_list|)
decl_stmt|;
comment|//tf.addUndoableEditListener(bPanel.undoListener);
name|setupJTextComponent
argument_list|(
name|tf
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|editors
operator|.
name|put
argument_list|(
literal|"bibtexkey"
argument_list|,
name|tf
argument_list|)
expr_stmt|;
comment|/* 			 * If the key field is the only field, we should have only one 			 * editor, and this one should be set as active initially: 			 */
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
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|tf
operator|.
name|getLabel
argument_list|()
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|tf
argument_list|,
literal|3
argument_list|)
expr_stmt|;
block|}
block|}
DECL|field|entry
name|BibtexEntry
name|entry
decl_stmt|;
DECL|method|getEntry ()
specifier|public
name|BibtexEntry
name|getEntry
parameter_list|()
block|{
return|return
name|entry
return|;
block|}
DECL|method|isFieldModified (FieldEditor f)
name|boolean
name|isFieldModified
parameter_list|(
name|FieldEditor
name|f
parameter_list|)
block|{
name|String
name|text
init|=
name|f
operator|.
name|getText
argument_list|()
operator|.
name|trim
argument_list|()
decl_stmt|;
if|if
condition|(
name|text
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
block|{
return|return
name|getEntry
argument_list|()
operator|.
name|getField
argument_list|(
name|f
operator|.
name|getFieldName
argument_list|()
argument_list|)
operator|!=
literal|null
return|;
block|}
else|else
block|{
name|Object
name|entryValue
init|=
name|getEntry
argument_list|()
operator|.
name|getField
argument_list|(
name|f
operator|.
name|getFieldName
argument_list|()
argument_list|)
decl_stmt|;
return|return
name|entryValue
operator|==
literal|null
operator|||
operator|!
name|entryValue
operator|.
name|toString
argument_list|()
operator|.
name|equals
argument_list|(
name|text
argument_list|)
return|;
block|}
block|}
DECL|method|markIfModified (FieldEditor f)
specifier|public
name|void
name|markIfModified
parameter_list|(
name|FieldEditor
name|f
parameter_list|)
block|{
comment|// Only mark as changed if not already is and the field was indeed
comment|// modified
if|if
condition|(
operator|!
name|updating
operator|&&
operator|!
name|parent
operator|.
name|panel
operator|.
name|isBaseChanged
argument_list|()
operator|&&
name|isFieldModified
argument_list|(
name|f
argument_list|)
condition|)
block|{
name|markBaseChanged
argument_list|()
expr_stmt|;
block|}
block|}
DECL|method|markBaseChanged ()
name|void
name|markBaseChanged
parameter_list|()
block|{
name|parent
operator|.
name|panel
operator|.
name|markBaseChanged
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Only sets the activeField variable but does not focus it. 	 *  	 * Call activate afterwards. 	 *  	 * @param c 	 */
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
argument_list|<
name|String
argument_list|>
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
block|{
comment|/** 			 * Corrected to fix [ 1594169 ] Entry editor: navigation between panels 			 */
operator|new
name|FocusRequester
argument_list|(
name|activeField
operator|.
name|getTextComponent
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
comment|/** 	 * Reset all fields from the data in the BibtexEntry. 	 *  	 */
DECL|method|updateAll ()
specifier|public
name|void
name|updateAll
parameter_list|()
block|{
name|setEntry
argument_list|(
name|getEntry
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|field|updating
specifier|protected
name|boolean
name|updating
init|=
literal|false
decl_stmt|;
DECL|method|setEntry (BibtexEntry entry)
specifier|public
name|void
name|setEntry
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|)
block|{
try|try
block|{
name|updating
operator|=
literal|true
expr_stmt|;
name|Iterator
argument_list|<
name|FieldEditor
argument_list|>
name|i
init|=
name|editors
operator|.
name|values
argument_list|()
operator|.
name|iterator
argument_list|()
decl_stmt|;
while|while
condition|(
name|i
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|FieldEditor
name|editor
init|=
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|Object
name|content
init|=
name|entry
operator|.
name|getField
argument_list|(
name|editor
operator|.
name|getFieldName
argument_list|()
argument_list|)
decl_stmt|;
name|String
name|toSet
init|=
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
decl_stmt|;
if|if
condition|(
operator|!
name|toSet
operator|.
name|equals
argument_list|(
name|editor
operator|.
name|getText
argument_list|()
argument_list|)
condition|)
name|editor
operator|.
name|setText
argument_list|(
name|toSet
argument_list|)
expr_stmt|;
block|}
name|this
operator|.
name|entry
operator|=
name|entry
expr_stmt|;
block|}
finally|finally
block|{
name|updating
operator|=
literal|false
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
argument_list|<
name|String
argument_list|>
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
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|FieldEditor
name|ed
init|=
name|editors
operator|.
name|get
argument_list|(
name|field
argument_list|)
decl_stmt|;
name|ed
operator|.
name|updateFontColor
argument_list|()
expr_stmt|;
name|ed
operator|.
name|setEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
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
name|setActiveBackgroundColor
argument_list|()
expr_stmt|;
else|else
name|ed
operator|.
name|setValidBackgroundColor
argument_list|()
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
name|Iterator
argument_list|<
name|FieldEditor
argument_list|>
name|i
init|=
name|editors
operator|.
name|values
argument_list|()
operator|.
name|iterator
argument_list|()
decl_stmt|;
while|while
condition|(
name|i
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|FieldEditor
name|editor
init|=
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|editor
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
name|scrollPane
return|;
block|}
comment|/** 	 * Set up key bindings and focus listener for the FieldEditor. 	 *  	 * @param component 	 */
DECL|method|setupJTextComponent (final JComponent component, final AutoCompleteListener acl)
specifier|public
name|void
name|setupJTextComponent
parameter_list|(
specifier|final
name|JComponent
name|component
parameter_list|,
specifier|final
name|AutoCompleteListener
name|acl
parameter_list|)
block|{
comment|// Here we add focus listeners to the component. The funny code is because we need
comment|// to guarantee that the AutoCompleteListener - if used - is called before fieldListener
comment|// on a focus lost event. The AutoCompleteListener is responsible for removing any
comment|// current suggestion when focus is lost, and this must be done before fieldListener
comment|// stores the current edit. Swing doesn't guarantee the order of execution of event
comment|// listeners, so we handle this by only adding the AutoCompleteListener and telling
comment|// it to call fieldListener afterwards. If no AutoCompleteListener is used, we
comment|// add the fieldListener normally.
if|if
condition|(
name|acl
operator|!=
literal|null
condition|)
block|{
name|component
operator|.
name|addKeyListener
argument_list|(
name|acl
argument_list|)
expr_stmt|;
name|component
operator|.
name|addFocusListener
argument_list|(
name|acl
argument_list|)
expr_stmt|;
name|acl
operator|.
name|setNextFocusListener
argument_list|(
name|fieldListener
argument_list|)
expr_stmt|;
block|}
else|else
name|component
operator|.
name|addFocusListener
argument_list|(
name|fieldListener
argument_list|)
expr_stmt|;
name|InputMap
name|im
init|=
name|component
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
name|component
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
argument_list|<
name|AWTKeyStroke
argument_list|>
name|keys
init|=
operator|new
name|HashSet
argument_list|<
name|AWTKeyStroke
argument_list|>
argument_list|(
name|component
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
name|component
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
argument_list|<
name|AWTKeyStroke
argument_list|>
argument_list|(
name|component
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
name|component
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
comment|/* 	 * Focus listener that fires the storeFieldAction when a FieldTextArea loses 	 * focus, and makes the vertical scroll view follow up. 	 *  	 * TODO: It would be nice to test this thoroughly. 	 */
DECL|field|fieldListener
name|FocusListener
name|fieldListener
init|=
operator|new
name|FocusListener
argument_list|()
block|{
name|JTextComponent
name|c
decl_stmt|;
name|DocumentListener
name|d
decl_stmt|;
specifier|public
name|void
name|focusGained
parameter_list|(
name|FocusEvent
name|e
parameter_list|)
block|{
synchronized|synchronized
init|(
name|this
init|)
block|{
if|if
condition|(
name|c
operator|!=
literal|null
condition|)
block|{
name|c
operator|.
name|getDocument
argument_list|()
operator|.
name|removeDocumentListener
argument_list|(
name|d
argument_list|)
expr_stmt|;
name|c
operator|=
literal|null
expr_stmt|;
name|d
operator|=
literal|null
expr_stmt|;
block|}
if|if
condition|(
name|e
operator|.
name|getSource
argument_list|()
operator|instanceof
name|JTextComponent
condition|)
block|{
name|c
operator|=
operator|(
name|JTextComponent
operator|)
name|e
operator|.
name|getSource
argument_list|()
expr_stmt|;
comment|/** 					 * [ 1553552 ] Not properly detecting changes to flag as 					 * changed 					 */
name|d
operator|=
operator|new
name|DocumentListener
argument_list|()
block|{
name|void
name|fire
parameter_list|(
name|DocumentEvent
name|e
parameter_list|)
block|{
if|if
condition|(
name|c
operator|.
name|isFocusOwner
argument_list|()
condition|)
block|{
name|markIfModified
argument_list|(
operator|(
name|FieldEditor
operator|)
name|c
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|void
name|changedUpdate
parameter_list|(
name|DocumentEvent
name|e
parameter_list|)
block|{
name|fire
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|insertUpdate
parameter_list|(
name|DocumentEvent
name|e
parameter_list|)
block|{
name|fire
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|removeUpdate
parameter_list|(
name|DocumentEvent
name|e
parameter_list|)
block|{
name|fire
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
block|}
expr_stmt|;
name|c
operator|.
name|getDocument
argument_list|()
operator|.
name|addDocumentListener
argument_list|(
name|d
argument_list|)
expr_stmt|;
comment|/** 					 * Makes the vertical scroll panel view follow the focus 					 */
name|Component
name|cScrollPane
init|=
name|c
operator|.
name|getParent
argument_list|()
operator|.
name|getParent
argument_list|()
decl_stmt|;
if|if
condition|(
name|cScrollPane
operator|instanceof
name|JScrollPane
condition|)
block|{
name|JScrollPane
name|componentPane
init|=
operator|(
name|JScrollPane
operator|)
name|cScrollPane
decl_stmt|;
name|Component
name|cPane
init|=
name|componentPane
operator|.
name|getParent
argument_list|()
decl_stmt|;
if|if
condition|(
name|cPane
operator|instanceof
name|JPanel
condition|)
block|{
name|JPanel
name|panel
init|=
operator|(
name|JPanel
operator|)
name|cPane
decl_stmt|;
name|Rectangle
name|bounds
init|=
name|componentPane
operator|.
name|getBounds
argument_list|()
decl_stmt|;
name|panel
operator|.
name|scrollRectToVisible
argument_list|(
name|bounds
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
name|setActive
argument_list|(
operator|(
name|FieldEditor
operator|)
name|e
operator|.
name|getSource
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|focusLost
parameter_list|(
name|FocusEvent
name|e
parameter_list|)
block|{
synchronized|synchronized
init|(
name|this
init|)
block|{
if|if
condition|(
name|c
operator|!=
literal|null
condition|)
block|{
name|c
operator|.
name|getDocument
argument_list|()
operator|.
name|removeDocumentListener
argument_list|(
name|d
argument_list|)
expr_stmt|;
name|c
operator|=
literal|null
expr_stmt|;
name|d
operator|=
literal|null
expr_stmt|;
block|}
block|}
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
name|e
operator|.
name|getSource
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
decl_stmt|;
block|}
end_class

end_unit

