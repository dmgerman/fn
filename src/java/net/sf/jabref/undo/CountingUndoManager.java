begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.undo
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|undo
package|;
end_package

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

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|undo
operator|.
name|UndoableEdit
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|BasePanel
import|;
end_import

begin_class
DECL|class|CountingUndoManager
specifier|public
class|class
name|CountingUndoManager
extends|extends
name|UndoManager
block|{
DECL|field|unchangedPoint
specifier|private
name|int
name|unchangedPoint
init|=
literal|0
decl_stmt|,
DECL|field|current
name|current
init|=
literal|0
decl_stmt|;
DECL|field|panel
specifier|private
name|BasePanel
name|panel
init|=
literal|null
decl_stmt|;
DECL|method|CountingUndoManager (BasePanel basePanel)
specifier|public
name|CountingUndoManager
parameter_list|(
name|BasePanel
name|basePanel
parameter_list|)
block|{
name|super
argument_list|()
expr_stmt|;
name|panel
operator|=
name|basePanel
expr_stmt|;
block|}
DECL|method|addEdit (UndoableEdit edit)
specifier|public
specifier|synchronized
name|boolean
name|addEdit
parameter_list|(
name|UndoableEdit
name|edit
parameter_list|)
block|{
name|current
operator|++
expr_stmt|;
return|return
name|super
operator|.
name|addEdit
argument_list|(
name|edit
argument_list|)
return|;
block|}
DECL|method|undo ()
specifier|public
specifier|synchronized
name|void
name|undo
parameter_list|()
throws|throws
name|CannotUndoException
block|{
name|super
operator|.
name|undo
argument_list|()
expr_stmt|;
name|current
operator|--
expr_stmt|;
name|panel
operator|.
name|updateEntryEditorIfShowing
argument_list|()
expr_stmt|;
block|}
DECL|method|redo ()
specifier|public
specifier|synchronized
name|void
name|redo
parameter_list|()
throws|throws
name|CannotUndoException
block|{
name|super
operator|.
name|redo
argument_list|()
expr_stmt|;
name|current
operator|++
expr_stmt|;
name|panel
operator|.
name|updateEntryEditorIfShowing
argument_list|()
expr_stmt|;
block|}
DECL|method|markUnchanged ()
specifier|public
specifier|synchronized
name|void
name|markUnchanged
parameter_list|()
block|{
name|unchangedPoint
operator|=
name|current
expr_stmt|;
block|}
DECL|method|hasChanged ()
specifier|public
name|boolean
name|hasChanged
parameter_list|()
block|{
return|return
operator|!
operator|(
name|current
operator|==
name|unchangedPoint
operator|)
return|;
block|}
block|}
end_class

end_unit

