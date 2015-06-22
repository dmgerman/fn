begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|java
operator|.
name|awt
operator|.
name|Color
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
name|UndoableEditEvent
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
name|UndoableEditListener
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
name|Document
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
name|CannotRedoException
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
name|CannotUndoException
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
name|UndoManager
import|;
end_import

begin_class
DECL|class|FieldTextField
specifier|public
class|class
name|FieldTextField
extends|extends
name|JTextField
implements|implements
name|FieldEditor
block|{
DECL|field|fieldName
specifier|protected
specifier|final
name|String
name|fieldName
decl_stmt|;
DECL|field|label
specifier|protected
specifier|final
name|JLabel
name|label
decl_stmt|;
DECL|field|undo
specifier|protected
name|UndoManager
name|undo
decl_stmt|;
DECL|field|autoCompleteListener
specifier|private
name|AutoCompleteListener
name|autoCompleteListener
init|=
literal|null
decl_stmt|;
comment|//protected UndoManager undo = new UndoManager();
DECL|method|FieldTextField (String fieldName_, String content, boolean changeColorOnFocus)
specifier|public
name|FieldTextField
parameter_list|(
name|String
name|fieldName_
parameter_list|,
name|String
name|content
parameter_list|,
name|boolean
name|changeColorOnFocus
parameter_list|)
block|{
name|super
argument_list|(
name|content
argument_list|)
expr_stmt|;
comment|// Listen for undo and redo events
comment|/*getDocument().addUndoableEditListener(new UndoableEditListener() {             public void undoableEditHappened(UndoableEditEvent evt) {                 undo.addEdit(evt.getEdit());             }         });*/
name|setupUndoRedo
argument_list|()
expr_stmt|;
name|updateFont
argument_list|()
expr_stmt|;
comment|// Add the global focus listener, so a menu item can see if this field
comment|// was focused when
comment|// an action was called.
name|addFocusListener
argument_list|(
name|Globals
operator|.
name|focusListener
argument_list|)
expr_stmt|;
if|if
condition|(
name|changeColorOnFocus
condition|)
name|addFocusListener
argument_list|(
operator|new
name|FieldEditorFocusListener
argument_list|()
argument_list|)
expr_stmt|;
name|fieldName
operator|=
name|fieldName_
expr_stmt|;
name|label
operator|=
operator|new
name|FieldNameLabel
argument_list|(
literal|" "
operator|+
name|Util
operator|.
name|nCase
argument_list|(
name|fieldName
argument_list|)
operator|+
literal|" "
argument_list|)
expr_stmt|;
comment|// label = new JLabel(" "+Util.nCase(fieldName)+" ", JLabel.CENTER);
comment|// label.setBorder(BorderFactory.createEtchedBorder());
name|setBackground
argument_list|(
name|GUIGlobals
operator|.
name|validFieldBackgroundColor
argument_list|)
expr_stmt|;
name|setForeground
argument_list|(
name|GUIGlobals
operator|.
name|editorTextColor
argument_list|)
expr_stmt|;
comment|// label.setOpaque(true);
comment|// if ((content != null)&& (content.length()> 0))
comment|// label.setForeground(GUIGlobals.entryEditorLabelColor);
comment|// At construction time, the field can never have an invalid value.
comment|// else label.setForeground(GUIGlobals.nullFieldColor);
name|FieldTextMenu
name|popMenu
init|=
operator|new
name|FieldTextMenu
argument_list|(
name|this
argument_list|)
decl_stmt|;
name|this
operator|.
name|addMouseListener
argument_list|(
name|popMenu
argument_list|)
expr_stmt|;
name|label
operator|.
name|addMouseListener
argument_list|(
name|popMenu
argument_list|)
expr_stmt|;
block|}
DECL|method|setupUndoRedo ()
specifier|protected
name|void
name|setupUndoRedo
parameter_list|()
block|{
name|undo
operator|=
operator|new
name|UndoManager
argument_list|()
expr_stmt|;
name|Document
name|doc
init|=
name|getDocument
argument_list|()
decl_stmt|;
comment|// Listen for undo and redo events
name|doc
operator|.
name|addUndoableEditListener
argument_list|(
operator|new
name|UndoableEditListener
argument_list|()
block|{
specifier|public
name|void
name|undoableEditHappened
parameter_list|(
name|UndoableEditEvent
name|evt
parameter_list|)
block|{
name|undo
operator|.
name|addEdit
argument_list|(
name|evt
operator|.
name|getEdit
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
comment|// Create an undo action and add it to the text component
name|getActionMap
argument_list|()
operator|.
name|put
argument_list|(
literal|"Undo"
argument_list|,
operator|new
name|AbstractAction
argument_list|(
literal|"Undo"
argument_list|)
block|{
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|evt
parameter_list|)
block|{
try|try
block|{
if|if
condition|(
name|undo
operator|.
name|canUndo
argument_list|()
condition|)
block|{
name|undo
operator|.
name|undo
argument_list|()
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|CannotUndoException
name|ignored
parameter_list|)
block|{                         }
block|}
block|}
argument_list|)
expr_stmt|;
comment|// Bind the undo action to ctl-Z
name|getInputMap
argument_list|()
operator|.
name|put
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getKey
argument_list|(
literal|"Undo"
argument_list|)
argument_list|,
literal|"Undo"
argument_list|)
expr_stmt|;
comment|// Create a redo action and add it to the text component
name|getActionMap
argument_list|()
operator|.
name|put
argument_list|(
literal|"Redo"
argument_list|,
operator|new
name|AbstractAction
argument_list|(
literal|"Redo"
argument_list|)
block|{
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|evt
parameter_list|)
block|{
try|try
block|{
if|if
condition|(
name|undo
operator|.
name|canRedo
argument_list|()
condition|)
block|{
name|undo
operator|.
name|redo
argument_list|()
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|CannotRedoException
name|ignored
parameter_list|)
block|{                         }
block|}
block|}
argument_list|)
expr_stmt|;
comment|// Bind the redo action to ctl-Y
name|getInputMap
argument_list|()
operator|.
name|put
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getKey
argument_list|(
literal|"Redo"
argument_list|)
argument_list|,
literal|"Redo"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|setText (String t)
specifier|public
name|void
name|setText
parameter_list|(
name|String
name|t
parameter_list|)
block|{
name|super
operator|.
name|setText
argument_list|(
name|t
argument_list|)
expr_stmt|;
if|if
condition|(
name|undo
operator|!=
literal|null
condition|)
name|undo
operator|.
name|discardAllEdits
argument_list|()
expr_stmt|;
block|}
DECL|method|append (String text)
specifier|public
name|void
name|append
parameter_list|(
name|String
name|text
parameter_list|)
block|{
name|setText
argument_list|(
name|getText
argument_list|()
operator|+
name|text
argument_list|)
expr_stmt|;
block|}
DECL|method|getFieldName ()
specifier|public
name|String
name|getFieldName
parameter_list|()
block|{
return|return
name|fieldName
return|;
block|}
DECL|method|getLabel ()
specifier|public
name|JLabel
name|getLabel
parameter_list|()
block|{
return|return
name|label
return|;
block|}
DECL|method|setLabelColor (Color c)
specifier|public
name|void
name|setLabelColor
parameter_list|(
name|Color
name|c
parameter_list|)
block|{
name|label
operator|.
name|setForeground
argument_list|(
name|c
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|NullPointerException
argument_list|(
literal|"ok"
argument_list|)
throw|;
block|}
DECL|method|getPane ()
specifier|public
name|JComponent
name|getPane
parameter_list|()
block|{
return|return
name|this
return|;
block|}
DECL|method|getTextComponent ()
specifier|public
name|JComponent
name|getTextComponent
parameter_list|()
block|{
return|return
name|this
return|;
block|}
DECL|method|setActiveBackgroundColor ()
specifier|public
name|void
name|setActiveBackgroundColor
parameter_list|()
block|{
name|setBackground
argument_list|(
name|GUIGlobals
operator|.
name|activeBackground
argument_list|)
expr_stmt|;
block|}
DECL|method|setValidBackgroundColor ()
specifier|public
name|void
name|setValidBackgroundColor
parameter_list|()
block|{
name|setBackground
argument_list|(
name|GUIGlobals
operator|.
name|validFieldBackgroundColor
argument_list|)
expr_stmt|;
block|}
DECL|method|setInvalidBackgroundColor ()
specifier|public
name|void
name|setInvalidBackgroundColor
parameter_list|()
block|{
name|setBackground
argument_list|(
name|GUIGlobals
operator|.
name|invalidFieldBackgroundColor
argument_list|)
expr_stmt|;
block|}
DECL|method|updateFontColor ()
specifier|public
name|void
name|updateFontColor
parameter_list|()
block|{
name|setForeground
argument_list|(
name|GUIGlobals
operator|.
name|editorTextColor
argument_list|)
expr_stmt|;
block|}
DECL|method|updateFont ()
specifier|public
name|void
name|updateFont
parameter_list|()
block|{
name|setFont
argument_list|(
name|GUIGlobals
operator|.
name|CURRENTFONT
argument_list|)
expr_stmt|;
block|}
comment|/*public void paint(Graphics g) {     	Graphics2D g2 = (Graphics2D) g;     	if (antialias)     		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);     	super.paint(g2);     }*/
DECL|method|paste (String textToInsert)
specifier|public
name|void
name|paste
parameter_list|(
name|String
name|textToInsert
parameter_list|)
block|{
name|int
name|sel
init|=
name|getSelectionEnd
argument_list|()
operator|-
name|getSelectionStart
argument_list|()
decl_stmt|;
if|if
condition|(
name|sel
operator|<
literal|1
condition|)
block|{
name|int
name|cPos
init|=
name|getCaretPosition
argument_list|()
decl_stmt|;
name|select
argument_list|(
name|cPos
argument_list|,
name|cPos
argument_list|)
expr_stmt|;
block|}
name|replaceSelection
argument_list|(
name|textToInsert
argument_list|)
expr_stmt|;
block|}
DECL|method|hasUndoInformation ()
specifier|public
name|boolean
name|hasUndoInformation
parameter_list|()
block|{
return|return
literal|false
return|;
comment|//undo.canUndo();
block|}
DECL|method|undo ()
specifier|public
name|void
name|undo
parameter_list|()
block|{
comment|/*try {             if (undo.canUndo()) {                 undo.undo();             }         } catch (CannotUndoException e) {         }*/
block|}
DECL|method|hasRedoInformation ()
specifier|public
name|boolean
name|hasRedoInformation
parameter_list|()
block|{
return|return
literal|false
return|;
comment|//undo.canRedo();
block|}
DECL|method|redo ()
specifier|public
name|void
name|redo
parameter_list|()
block|{
comment|/*try {             if (undo.canRedo()) {                 undo.redo();             }         } catch (CannotUndoException e) {         }*/
block|}
DECL|method|addUndoableEditListener (UndoableEditListener listener)
specifier|public
name|void
name|addUndoableEditListener
parameter_list|(
name|UndoableEditListener
name|listener
parameter_list|)
block|{
name|getDocument
argument_list|()
operator|.
name|addUndoableEditListener
argument_list|(
name|listener
argument_list|)
expr_stmt|;
block|}
DECL|method|setAutoCompleteListener (AutoCompleteListener listener)
specifier|public
name|void
name|setAutoCompleteListener
parameter_list|(
name|AutoCompleteListener
name|listener
parameter_list|)
block|{
name|autoCompleteListener
operator|=
name|listener
expr_stmt|;
block|}
DECL|method|clearAutoCompleteSuggestion ()
specifier|public
name|void
name|clearAutoCompleteSuggestion
parameter_list|()
block|{
if|if
condition|(
name|autoCompleteListener
operator|!=
literal|null
condition|)
name|autoCompleteListener
operator|.
name|clearCurrentSuggestion
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

