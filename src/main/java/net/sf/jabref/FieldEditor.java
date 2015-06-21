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
name|Container
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
name|JLabel
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
comment|/**  * FieldEditors is a common interface between the FieldTextField and FieldTextArea.  */
end_comment

begin_interface
DECL|interface|FieldEditor
specifier|public
interface|interface
name|FieldEditor
block|{
DECL|method|getFieldName ()
specifier|public
name|String
name|getFieldName
parameter_list|()
function_decl|;
comment|/*      * Returns the component to be added to a container. Might be a JScrollPane      * or the component itself.      */
DECL|method|getPane ()
specifier|public
name|JComponent
name|getPane
parameter_list|()
function_decl|;
comment|/*      * Returns the text component itself.      */
DECL|method|getTextComponent ()
specifier|public
name|JComponent
name|getTextComponent
parameter_list|()
function_decl|;
DECL|method|getLabel ()
specifier|public
name|JLabel
name|getLabel
parameter_list|()
function_decl|;
DECL|method|setActiveBackgroundColor ()
specifier|public
name|void
name|setActiveBackgroundColor
parameter_list|()
function_decl|;
DECL|method|setValidBackgroundColor ()
specifier|public
name|void
name|setValidBackgroundColor
parameter_list|()
function_decl|;
DECL|method|setInvalidBackgroundColor ()
specifier|public
name|void
name|setInvalidBackgroundColor
parameter_list|()
function_decl|;
DECL|method|setLabelColor (Color c)
specifier|public
name|void
name|setLabelColor
parameter_list|(
name|Color
name|c
parameter_list|)
function_decl|;
DECL|method|setBackground (Color c)
specifier|public
name|void
name|setBackground
parameter_list|(
name|Color
name|c
parameter_list|)
function_decl|;
DECL|method|updateFontColor ()
specifier|public
name|void
name|updateFontColor
parameter_list|()
function_decl|;
DECL|method|getText ()
specifier|public
name|String
name|getText
parameter_list|()
function_decl|;
comment|/**      * Sets the given text on the current field editor and marks this text      * editor as modified.      *       * @param newText      */
DECL|method|setText (String newText)
specifier|public
name|void
name|setText
parameter_list|(
name|String
name|newText
parameter_list|)
function_decl|;
DECL|method|append (String text)
specifier|public
name|void
name|append
parameter_list|(
name|String
name|text
parameter_list|)
function_decl|;
DECL|method|getParent ()
specifier|public
name|Container
name|getParent
parameter_list|()
function_decl|;
DECL|method|requestFocus ()
specifier|public
name|void
name|requestFocus
parameter_list|()
function_decl|;
DECL|method|setEnabled (boolean enabled)
specifier|public
name|void
name|setEnabled
parameter_list|(
name|boolean
name|enabled
parameter_list|)
function_decl|;
DECL|method|updateFont ()
specifier|public
name|void
name|updateFont
parameter_list|()
function_decl|;
comment|/**      * paste text into component, it should also take some selected text into      * account      */
DECL|method|paste (String textToInsert)
specifier|public
name|void
name|paste
parameter_list|(
name|String
name|textToInsert
parameter_list|)
function_decl|;
comment|/**      * normally implemented in JTextArea and JTextField      *       * @return      */
DECL|method|getSelectedText ()
specifier|public
name|String
name|getSelectedText
parameter_list|()
function_decl|;
DECL|method|hasUndoInformation ()
specifier|public
name|boolean
name|hasUndoInformation
parameter_list|()
function_decl|;
DECL|method|undo ()
specifier|public
name|void
name|undo
parameter_list|()
function_decl|;
DECL|method|hasRedoInformation ()
specifier|public
name|boolean
name|hasRedoInformation
parameter_list|()
function_decl|;
DECL|method|redo ()
specifier|public
name|void
name|redo
parameter_list|()
function_decl|;
DECL|method|addUndoableEditListener (UndoableEditListener listener)
specifier|public
name|void
name|addUndoableEditListener
parameter_list|(
name|UndoableEditListener
name|listener
parameter_list|)
function_decl|;
DECL|method|setAutoCompleteListener (AutoCompleteListener listener)
specifier|public
name|void
name|setAutoCompleteListener
parameter_list|(
name|AutoCompleteListener
name|listener
parameter_list|)
function_decl|;
DECL|method|clearAutoCompleteSuggestion ()
specifier|public
name|void
name|clearAutoCompleteSuggestion
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

