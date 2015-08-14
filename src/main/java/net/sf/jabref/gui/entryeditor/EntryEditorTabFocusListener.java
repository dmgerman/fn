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

begin_comment
comment|/*  * Focus listener that fires the storeFieldAction when a FieldTextArea loses  * focus.  */
end_comment

begin_class
DECL|class|EntryEditorTabFocusListener
class|class
name|EntryEditorTabFocusListener
implements|implements
name|FocusListener
block|{
DECL|field|textComponent
specifier|private
name|JTextComponent
name|textComponent
decl_stmt|;
DECL|field|documentListener
specifier|private
name|DocumentListener
name|documentListener
decl_stmt|;
DECL|field|entryEditorTab
specifier|private
specifier|final
name|EntryEditorTab
name|entryEditorTab
decl_stmt|;
DECL|method|EntryEditorTabFocusListener (final EntryEditorTab entryEditorTab)
specifier|public
name|EntryEditorTabFocusListener
parameter_list|(
specifier|final
name|EntryEditorTab
name|entryEditorTab
parameter_list|)
block|{
name|this
operator|.
name|entryEditorTab
operator|=
name|entryEditorTab
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|focusGained (FocusEvent event)
specifier|public
name|void
name|focusGained
parameter_list|(
name|FocusEvent
name|event
parameter_list|)
block|{
synchronized|synchronized
init|(
name|this
init|)
block|{
if|if
condition|(
name|textComponent
operator|!=
literal|null
condition|)
block|{
name|textComponent
operator|.
name|getDocument
argument_list|()
operator|.
name|removeDocumentListener
argument_list|(
name|documentListener
argument_list|)
expr_stmt|;
name|textComponent
operator|=
literal|null
expr_stmt|;
name|documentListener
operator|=
literal|null
expr_stmt|;
block|}
if|if
condition|(
name|event
operator|.
name|getSource
argument_list|()
operator|instanceof
name|JTextComponent
condition|)
block|{
name|textComponent
operator|=
operator|(
name|JTextComponent
operator|)
name|event
operator|.
name|getSource
argument_list|()
expr_stmt|;
comment|/**                  * [ 1553552 ] Not properly detecting changes to flag as                  * changed                  */
name|documentListener
operator|=
operator|new
name|DocumentListener
argument_list|()
block|{
name|void
name|fire
parameter_list|()
block|{
if|if
condition|(
name|textComponent
operator|.
name|isFocusOwner
argument_list|()
condition|)
block|{
name|entryEditorTab
operator|.
name|markIfModified
argument_list|(
operator|(
name|FieldEditor
operator|)
name|textComponent
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|changedUpdate
parameter_list|(
name|DocumentEvent
name|e
parameter_list|)
block|{
name|fire
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|insertUpdate
parameter_list|(
name|DocumentEvent
name|e
parameter_list|)
block|{
name|fire
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|removeUpdate
parameter_list|(
name|DocumentEvent
name|e
parameter_list|)
block|{
name|fire
argument_list|()
expr_stmt|;
block|}
block|}
expr_stmt|;
name|textComponent
operator|.
name|getDocument
argument_list|()
operator|.
name|addDocumentListener
argument_list|(
name|documentListener
argument_list|)
expr_stmt|;
comment|/**                  * Makes the vertical scroll panel view follow the focus                  */
name|Component
name|scrollPane
init|=
name|textComponent
operator|.
name|getParent
argument_list|()
operator|.
name|getParent
argument_list|()
decl_stmt|;
if|if
condition|(
name|scrollPane
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
name|scrollPane
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
name|entryEditorTab
operator|.
name|setActive
argument_list|(
operator|(
name|FieldEditor
operator|)
name|event
operator|.
name|getSource
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|focusLost (FocusEvent event)
specifier|public
name|void
name|focusLost
parameter_list|(
name|FocusEvent
name|event
parameter_list|)
block|{
synchronized|synchronized
init|(
name|this
init|)
block|{
if|if
condition|(
name|textComponent
operator|!=
literal|null
condition|)
block|{
name|textComponent
operator|.
name|getDocument
argument_list|()
operator|.
name|removeDocumentListener
argument_list|(
name|documentListener
argument_list|)
expr_stmt|;
name|textComponent
operator|=
literal|null
expr_stmt|;
name|documentListener
operator|=
literal|null
expr_stmt|;
block|}
block|}
if|if
condition|(
operator|!
name|event
operator|.
name|isTemporary
argument_list|()
condition|)
block|{
name|entryEditorTab
operator|.
name|getParent
argument_list|()
operator|.
name|updateField
argument_list|(
name|event
operator|.
name|getSource
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

