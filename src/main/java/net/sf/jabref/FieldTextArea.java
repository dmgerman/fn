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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|util
operator|.
name|StringUtil
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
name|util
operator|.
name|regex
operator|.
name|Pattern
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
name|UndoableEditListener
import|;
end_import

begin_comment
comment|/**  * An implementation of the FieldEditor backed by a JTextArea. Used for  * multi-line input.  */
end_comment

begin_class
DECL|class|FieldTextArea
specifier|public
class|class
name|FieldTextArea
extends|extends
name|JTextAreaWithHighlighting
implements|implements
name|FieldEditor
block|{
DECL|field|PREFERRED_SIZE
name|Dimension
name|PREFERRED_SIZE
decl_stmt|;
DECL|field|sp
specifier|private
specifier|final
name|JScrollPane
name|sp
decl_stmt|;
DECL|field|label
specifier|private
specifier|final
name|FieldNameLabel
name|label
decl_stmt|;
DECL|field|fieldName
specifier|private
name|String
name|fieldName
decl_stmt|;
DECL|field|bull
specifier|static
specifier|final
name|Pattern
name|bull
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"\\s*[-\\*]+.*"
argument_list|)
decl_stmt|;
DECL|field|indent
specifier|static
specifier|final
name|Pattern
name|indent
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"\\s+.*"
argument_list|)
decl_stmt|;
DECL|field|autoCompleteListener
specifier|private
name|AutoCompleteListener
name|autoCompleteListener
init|=
literal|null
decl_stmt|;
comment|// protected UndoManager undo = new UndoManager();
DECL|method|FieldTextArea (String fieldName_, String content)
specifier|public
name|FieldTextArea
parameter_list|(
name|String
name|fieldName_
parameter_list|,
name|String
name|content
parameter_list|)
block|{
name|super
argument_list|(
name|content
argument_list|)
expr_stmt|;
comment|// Listen for undo and redo events
comment|/*          * getDocument().addUndoableEditListener(new UndoableEditListener() {          * public void undoableEditHappened(UndoableEditEvent evt) {          * undo.addEdit(evt.getEdit()); } });          */
name|updateFont
argument_list|()
expr_stmt|;
comment|// Add the global focus listener, so a menu item can see if this field
comment|// was focused when an action was called.
name|addFocusListener
argument_list|(
name|Globals
operator|.
name|focusListener
argument_list|)
expr_stmt|;
name|addFocusListener
argument_list|(
operator|new
name|FieldEditorFocusListener
argument_list|()
argument_list|)
expr_stmt|;
name|sp
operator|=
operator|new
name|JScrollPane
argument_list|(
name|this
argument_list|,
name|ScrollPaneConstants
operator|.
name|VERTICAL_SCROLLBAR_AS_NEEDED
argument_list|,
name|ScrollPaneConstants
operator|.
name|HORIZONTAL_SCROLLBAR_NEVER
argument_list|)
expr_stmt|;
name|sp
operator|.
name|setMinimumSize
argument_list|(
operator|new
name|Dimension
argument_list|(
literal|200
argument_list|,
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|setLineWrap
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|setWrapStyleWord
argument_list|(
literal|true
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
literal|' '
operator|+
name|StringUtil
operator|.
name|nCase
argument_list|(
name|fieldName
argument_list|)
operator|+
literal|' '
argument_list|)
expr_stmt|;
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
comment|// setFont(new Font("Times", Font.PLAIN, 10));
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
annotation|@
name|Override
DECL|method|getPreferredScrollableViewportSize ()
specifier|public
name|Dimension
name|getPreferredScrollableViewportSize
parameter_list|()
block|{
return|return
name|getPreferredSize
argument_list|()
return|;
block|}
comment|/*      * public void paint(Graphics g) { Graphics2D g2 = (Graphics2D) g; if      * (antialias) g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,      * RenderingHints.VALUE_ANTIALIAS_ON); super.paint(g2); }      */
annotation|@
name|Override
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
DECL|method|setFieldName (String newName)
specifier|public
name|void
name|setFieldName
parameter_list|(
name|String
name|newName
parameter_list|)
block|{
name|fieldName
operator|=
name|newName
expr_stmt|;
block|}
annotation|@
name|Override
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
annotation|@
name|Override
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
block|}
annotation|@
name|Override
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
annotation|@
name|Override
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
annotation|@
name|Override
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
annotation|@
name|Override
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
annotation|@
name|Override
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
annotation|@
name|Override
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
annotation|@
name|Override
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
annotation|@
name|Override
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
operator|>
literal|0
condition|)
block|{
name|replaceSelection
argument_list|(
name|textToInsert
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|int
name|cPos
init|=
name|this
operator|.
name|getCaretPosition
argument_list|()
decl_stmt|;
name|this
operator|.
name|insert
argument_list|(
name|textToInsert
argument_list|,
name|cPos
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
DECL|method|hasUndoInformation ()
specifier|public
name|boolean
name|hasUndoInformation
parameter_list|()
block|{
return|return
literal|false
return|;
comment|// undo.canUndo();
block|}
annotation|@
name|Override
DECL|method|undo ()
specifier|public
name|void
name|undo
parameter_list|()
block|{
comment|/*          * try { if (undo.canUndo()) { undo.undo(); } } catch          * (CannotUndoException e) { }          */
block|}
annotation|@
name|Override
DECL|method|hasRedoInformation ()
specifier|public
name|boolean
name|hasRedoInformation
parameter_list|()
block|{
return|return
literal|false
return|;
comment|// undo.canRedo();
block|}
annotation|@
name|Override
DECL|method|redo ()
specifier|public
name|void
name|redo
parameter_list|()
block|{
comment|/*          * try { if (undo.canRedo()) { undo.redo(); } } catch          * (CannotUndoException e) { }          */
block|}
annotation|@
name|Override
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
annotation|@
name|Override
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
annotation|@
name|Override
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
block|{
name|autoCompleteListener
operator|.
name|clearCurrentSuggestion
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

