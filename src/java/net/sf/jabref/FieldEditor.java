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
DECL|method|getPane ()
specifier|public
name|javax
operator|.
name|swing
operator|.
name|JComponent
name|getPane
parameter_list|()
function_decl|;
comment|// Returns the component to be added to a container. Might
comment|// be a JScrollPane or the component itself.
DECL|method|getTextComponent ()
specifier|public
name|javax
operator|.
name|swing
operator|.
name|JComponent
name|getTextComponent
parameter_list|()
function_decl|;
comment|// Returns the text component itself.
DECL|method|getLabel ()
specifier|public
name|javax
operator|.
name|swing
operator|.
name|JLabel
name|getLabel
parameter_list|()
function_decl|;
DECL|method|setLabelColor (java.awt.Color c)
specifier|public
name|void
name|setLabelColor
parameter_list|(
name|java
operator|.
name|awt
operator|.
name|Color
name|c
parameter_list|)
function_decl|;
DECL|method|setBackground (java.awt.Color c)
specifier|public
name|void
name|setBackground
parameter_list|(
name|java
operator|.
name|awt
operator|.
name|Color
name|c
parameter_list|)
function_decl|;
DECL|method|getText ()
specifier|public
name|String
name|getText
parameter_list|()
function_decl|;
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
name|java
operator|.
name|awt
operator|.
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
comment|// paste text into component, it should also take some selected text into account
DECL|method|paste (String textToInsert)
specifier|public
name|void
name|paste
parameter_list|(
name|String
name|textToInsert
parameter_list|)
function_decl|;
comment|// normally implemented in JTextArea and JTextField
DECL|method|getSelectedText ()
specifier|public
name|String
name|getSelectedText
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

