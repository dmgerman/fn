begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.gui.entryeditor
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|entryeditor
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
name|gui
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
name|gui
operator|.
name|autocompleter
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
name|fieldeditors
operator|.
name|FieldEditor
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
name|fieldeditors
operator|.
name|TextArea
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
name|fieldeditors
operator|.
name|TextField
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
name|autocompleter
operator|.
name|AutoCompleter
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
name|fieldeditors
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

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|BibtexEntry
import|;
end_import

begin_comment
comment|/**  * A single tab displayed in the EntryEditor holding several FieldEditors.  */
end_comment

begin_class
DECL|class|EntryEditorTab
class|class
name|EntryEditorTab
block|{
DECL|field|panel
specifier|private
specifier|final
name|JPanel
name|panel
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
DECL|field|scrollPane
specifier|private
specifier|final
name|JScrollPane
name|scrollPane
init|=
operator|new
name|JScrollPane
argument_list|(
name|panel
argument_list|,
name|ScrollPaneConstants
operator|.
name|VERTICAL_SCROLLBAR_AS_NEEDED
argument_list|,
name|ScrollPaneConstants
operator|.
name|HORIZONTAL_SCROLLBAR_NEVER
argument_list|)
decl_stmt|;
DECL|field|fields
specifier|private
specifier|final
name|String
index|[]
name|fields
decl_stmt|;
DECL|field|parent
specifier|private
specifier|final
name|EntryEditor
name|parent
decl_stmt|;
DECL|field|editors
specifier|private
specifier|final
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
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|activeField
specifier|private
name|FieldEditor
name|activeField
decl_stmt|;
comment|// UGLY HACK to have a pointer to the fileListEditor to call autoSetLinks()
DECL|field|fileListEditor
specifier|public
name|FileListEditor
name|fileListEditor
decl_stmt|;
DECL|field|entry
specifier|private
name|BibtexEntry
name|entry
decl_stmt|;
DECL|field|fieldListener
specifier|private
specifier|final
name|FocusListener
name|fieldListener
init|=
operator|new
name|EntryEditorTabFocusListener
argument_list|(
name|this
argument_list|)
decl_stmt|;
DECL|method|EntryEditorTab (JabRefFrame frame, BasePanel panel, List<String> fields, EntryEditor parent, boolean addKeyField, boolean compressed, String name)
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
name|boolean
name|compressed
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
block|{
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
name|fields
operator|.
name|size
argument_list|()
index|]
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|this
operator|.
name|fields
operator|=
operator|new
name|String
index|[]
block|{}
expr_stmt|;
block|}
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
name|compressed
argument_list|,
name|name
argument_list|)
expr_stmt|;
comment|/*          * The following line makes sure focus cycles inside tab instead of          * being lost to other parts of the frame:          */
name|scrollPane
operator|.
name|setFocusCycleRoot
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
DECL|method|setupPanel (JabRefFrame frame, BasePanel bPanel, boolean addKeyField, boolean compressed, String title)
specifier|private
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
name|boolean
name|compressed
parameter_list|,
name|String
name|title
parameter_list|)
block|{
name|InputMap
name|inputMap
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
name|actionMap
init|=
name|panel
operator|.
name|getActionMap
argument_list|()
decl_stmt|;
name|inputMap
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
name|actionMap
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
name|inputMap
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
name|actionMap
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
name|inputMap
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
name|actionMap
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
name|inputMap
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
name|inputMap
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
name|actionMap
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
name|inputMap
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
name|inputMap
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
name|actionMap
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
name|inputMap
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
name|actionMap
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
name|inputMap
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
name|actionMap
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
name|inputMap
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
name|actionMap
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
name|inputMap
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
name|actionMap
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
name|int
name|fieldsPerRow
init|=
name|compressed
condition|?
literal|2
else|:
literal|1
decl_stmt|;
name|String
name|colSpec
init|=
name|compressed
condition|?
literal|"fill:pref, 1dlu, fill:10dlu:grow, 1dlu, fill:pref, "
operator|+
literal|"8dlu, fill:pref, 1dlu, fill:10dlu:grow, 1dlu, fill:pref"
else|:
literal|"fill:pref, 1dlu, fill:pref:grow, 1dlu, fill:pref"
decl_stmt|;
name|StringBuilder
name|stringBuilder
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|int
name|rows
init|=
operator|(
name|int
operator|)
name|Math
operator|.
name|ceil
argument_list|(
operator|(
name|double
operator|)
name|fields
operator|.
name|length
operator|/
name|fieldsPerRow
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
name|rows
condition|;
name|i
operator|++
control|)
block|{
name|stringBuilder
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
block|{
name|stringBuilder
operator|.
name|append
argument_list|(
literal|"4dlu, fill:pref"
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|stringBuilder
operator|.
name|length
argument_list|()
operator|>=
literal|2
condition|)
block|{
name|stringBuilder
operator|.
name|delete
argument_list|(
name|stringBuilder
operator|.
name|length
argument_list|()
operator|-
literal|2
argument_list|,
name|stringBuilder
operator|.
name|length
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|String
name|rowSpec
init|=
name|stringBuilder
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
comment|// BibTex edit fields are defined here
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
name|fieldEditor
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
name|fieldEditor
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
name|fileListEditor
operator|=
operator|(
name|FileListEditor
operator|)
name|fieldEditor
expr_stmt|;
name|defaultHeight
operator|=
literal|0
expr_stmt|;
block|}
else|else
block|{
name|fieldEditor
operator|=
operator|new
name|TextArea
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
name|getSearchBar
argument_list|()
operator|.
name|addSearchListener
argument_list|(
operator|(
name|TextArea
operator|)
name|fieldEditor
argument_list|)
expr_stmt|;
name|defaultHeight
operator|=
name|fieldEditor
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
name|JComponent
name|extra
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
name|fieldEditor
argument_list|)
decl_stmt|;
comment|// Add autocompleter listener, if required for this field:
name|AutoCompleter
name|autoCompleter
init|=
name|bPanel
operator|.
name|getAutoCompleters
argument_list|()
operator|.
name|get
argument_list|(
name|fields
index|[
name|i
index|]
argument_list|)
decl_stmt|;
name|AutoCompleteListener
name|autoCompleteListener
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|autoCompleter
operator|!=
literal|null
condition|)
block|{
name|autoCompleteListener
operator|=
operator|new
name|AutoCompleteListener
argument_list|(
name|autoCompleter
argument_list|)
expr_stmt|;
block|}
name|setupJTextComponent
argument_list|(
name|fieldEditor
operator|.
name|getTextComponent
argument_list|()
argument_list|,
name|autoCompleteListener
argument_list|)
expr_stmt|;
name|fieldEditor
operator|.
name|setAutoCompleteListener
argument_list|(
name|autoCompleteListener
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
name|fieldEditor
argument_list|)
expr_stmt|;
if|if
condition|(
name|i
operator|==
literal|0
condition|)
block|{
name|activeField
operator|=
name|fieldEditor
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|compressed
condition|)
block|{
name|fieldEditor
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
block|}
name|builder
operator|.
name|append
argument_list|(
name|fieldEditor
operator|.
name|getLabel
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|extra
operator|==
literal|null
condition|)
block|{
name|builder
operator|.
name|append
argument_list|(
name|fieldEditor
operator|.
name|getPane
argument_list|()
argument_list|,
literal|3
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|builder
operator|.
name|append
argument_list|(
name|fieldEditor
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
name|extra
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
if|if
condition|(
operator|(
name|i
operator|+
literal|1
operator|)
operator|%
name|fieldsPerRow
operator|==
literal|0
condition|)
block|{
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
block|}
block|}
comment|// Add the edit field for Bibtex-key.
if|if
condition|(
name|addKeyField
condition|)
block|{
specifier|final
name|TextField
name|textField
init|=
operator|new
name|TextField
argument_list|(
name|BibtexEntry
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
name|BibtexEntry
operator|.
name|KEY_FIELD
argument_list|)
argument_list|,
literal|true
argument_list|)
decl_stmt|;
name|setupJTextComponent
argument_list|(
name|textField
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
name|textField
argument_list|)
expr_stmt|;
comment|/*              * If the key field is the only field, we should have only one              * editor, and this one should be set as active initially:              */
if|if
condition|(
name|editors
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
block|{
name|activeField
operator|=
name|textField
expr_stmt|;
block|}
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|textField
operator|.
name|getLabel
argument_list|()
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|textField
argument_list|,
literal|3
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|getEntry ()
specifier|private
name|BibtexEntry
name|getEntry
parameter_list|()
block|{
return|return
name|entry
return|;
block|}
DECL|method|isFieldModified (FieldEditor fieldEditor)
specifier|private
name|boolean
name|isFieldModified
parameter_list|(
name|FieldEditor
name|fieldEditor
parameter_list|)
block|{
name|String
name|text
init|=
name|fieldEditor
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
name|isEmpty
argument_list|()
condition|)
block|{
return|return
name|getEntry
argument_list|()
operator|.
name|getField
argument_list|(
name|fieldEditor
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
name|fieldEditor
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
DECL|method|markIfModified (FieldEditor fieldEditor)
specifier|public
name|void
name|markIfModified
parameter_list|(
name|FieldEditor
name|fieldEditor
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
name|fieldEditor
argument_list|)
condition|)
block|{
name|markBaseChanged
argument_list|()
expr_stmt|;
block|}
block|}
DECL|method|markBaseChanged ()
specifier|private
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
comment|/**      * Only sets the activeField variable but does not focus it.      *<p>      * Call activate afterwards.      *      * @param fieldEditor      */
DECL|method|setActive (FieldEditor fieldEditor)
specifier|public
name|void
name|setActive
parameter_list|(
name|FieldEditor
name|fieldEditor
parameter_list|)
block|{
name|activeField
operator|=
name|fieldEditor
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
comment|/**              * Corrected to fix [ 1594169 ] Entry editor: navigation between panels              */
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
comment|/**      * Reset all fields from the data in the BibtexEntry.      */
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
specifier|private
name|boolean
name|updating
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
for|for
control|(
name|FieldEditor
name|editor
range|:
name|editors
operator|.
name|values
argument_list|()
control|)
block|{
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
name|content
operator|==
literal|null
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
block|{
name|editor
operator|.
name|setText
argument_list|(
name|toSet
argument_list|)
expr_stmt|;
block|}
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
block|{
return|return
literal|false
return|;
block|}
name|FieldEditor
name|fieldEditor
init|=
name|editors
operator|.
name|get
argument_list|(
name|field
argument_list|)
decl_stmt|;
comment|// trying to preserve current edit position (fixes SF bug #1285)
if|if
condition|(
name|fieldEditor
operator|.
name|getTextComponent
argument_list|()
operator|instanceof
name|JTextComponent
condition|)
block|{
name|int
name|initialCaretPosition
init|=
operator|(
operator|(
name|JTextComponent
operator|)
name|fieldEditor
operator|)
operator|.
name|getCaretPosition
argument_list|()
decl_stmt|;
name|fieldEditor
operator|.
name|setText
argument_list|(
name|content
argument_list|)
expr_stmt|;
operator|(
operator|(
name|JTextComponent
operator|)
name|fieldEditor
operator|)
operator|.
name|setCaretPosition
argument_list|(
name|initialCaretPosition
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|fieldEditor
operator|.
name|setText
argument_list|(
name|content
argument_list|)
expr_stmt|;
block|}
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
name|Map
operator|.
name|Entry
argument_list|<
name|String
argument_list|,
name|FieldEditor
argument_list|>
name|stringFieldEditorEntry
range|:
name|editors
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|FieldEditor
name|ed
init|=
name|stringFieldEditorEntry
operator|.
name|getValue
argument_list|()
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
block|{
name|ed
operator|.
name|setActiveBackgroundColor
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|ed
operator|.
name|setValidBackgroundColor
argument_list|()
expr_stmt|;
block|}
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
name|FieldEditor
name|editor
range|:
name|editors
operator|.
name|values
argument_list|()
control|)
block|{
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
DECL|method|getParent ()
specifier|public
name|EntryEditor
name|getParent
parameter_list|()
block|{
return|return
name|parent
return|;
block|}
comment|/**      * Set up key bindings and focus listener for the FieldEditor.      *      * @param component      */
DECL|method|setupJTextComponent (final JComponent component, final AutoCompleteListener autoCompleteListener)
specifier|private
name|void
name|setupJTextComponent
parameter_list|(
specifier|final
name|JComponent
name|component
parameter_list|,
specifier|final
name|AutoCompleteListener
name|autoCompleteListener
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
name|autoCompleteListener
operator|!=
literal|null
condition|)
block|{
name|component
operator|.
name|addKeyListener
argument_list|(
name|autoCompleteListener
argument_list|)
expr_stmt|;
name|component
operator|.
name|addFocusListener
argument_list|(
name|autoCompleteListener
argument_list|)
expr_stmt|;
name|autoCompleteListener
operator|.
name|setNextFocusListener
argument_list|(
name|fieldListener
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|component
operator|.
name|addFocusListener
argument_list|(
name|fieldListener
argument_list|)
expr_stmt|;
block|}
name|InputMap
name|inputMap
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
name|actionMap
init|=
name|component
operator|.
name|getActionMap
argument_list|()
decl_stmt|;
name|inputMap
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
name|actionMap
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
name|inputMap
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
name|actionMap
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
name|inputMap
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
name|actionMap
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
name|inputMap
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
name|inputMap
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
name|actionMap
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
name|inputMap
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
name|inputMap
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
name|actionMap
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
name|inputMap
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
name|actionMap
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
name|inputMap
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
name|actionMap
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
name|inputMap
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
name|actionMap
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
name|inputMap
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
name|actionMap
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
name|HashSet
argument_list|<
name|AWTKeyStroke
argument_list|>
name|keys
init|=
operator|new
name|HashSet
argument_list|<>
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
argument_list|<>
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
block|}
end_class

end_unit

