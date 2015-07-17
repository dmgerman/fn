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
name|AbstractUndoableEdit
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

begin_comment
comment|/**  * This class represents the removal of an entry. The constructor needs  * references to the database, the entry, and the map of open entry editors.  * The latter to be able to close the entry's editor if it is opened before  * the insert is undone.  */
end_comment

begin_class
DECL|class|UndoableInsertEntry
specifier|public
class|class
name|UndoableInsertEntry
extends|extends
name|AbstractUndoableEdit
block|{
DECL|field|base
specifier|private
specifier|final
name|BibtexDatabase
name|base
decl_stmt|;
DECL|field|entry
specifier|private
specifier|final
name|BibtexEntry
name|entry
decl_stmt|;
DECL|field|panel
specifier|private
specifier|final
name|BasePanel
name|panel
decl_stmt|;
DECL|method|UndoableInsertEntry (BibtexDatabase base, BibtexEntry entry, BasePanel panel)
specifier|public
name|UndoableInsertEntry
parameter_list|(
name|BibtexDatabase
name|base
parameter_list|,
name|BibtexEntry
name|entry
parameter_list|,
name|BasePanel
name|panel
parameter_list|)
block|{
name|this
operator|.
name|base
operator|=
name|base
expr_stmt|;
name|this
operator|.
name|entry
operator|=
name|entry
expr_stmt|;
name|this
operator|.
name|panel
operator|=
name|panel
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|getUndoPresentationName ()
specifier|public
name|String
name|getUndoPresentationName
parameter_list|()
block|{
return|return
literal|"Undo: insert entry"
return|;
block|}
annotation|@
name|Override
DECL|method|getRedoPresentationName ()
specifier|public
name|String
name|getRedoPresentationName
parameter_list|()
block|{
return|return
literal|"Redo: insert entry"
return|;
block|}
annotation|@
name|Override
DECL|method|undo ()
specifier|public
name|void
name|undo
parameter_list|()
block|{
name|super
operator|.
name|undo
argument_list|()
expr_stmt|;
comment|// Revert the change.
try|try
block|{
name|base
operator|.
name|removeEntry
argument_list|(
name|entry
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
comment|// If the entry has an editor currently open, we must close it.
name|panel
operator|.
name|ensureNotShowing
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Throwable
name|ex
parameter_list|)
block|{
name|ex
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
annotation|@
name|Override
DECL|method|redo ()
specifier|public
name|void
name|redo
parameter_list|()
block|{
name|super
operator|.
name|redo
argument_list|()
expr_stmt|;
comment|// Redo the change.
try|try
block|{
name|String
name|id
init|=
name|IdGenerator
operator|.
name|next
argument_list|()
decl_stmt|;
name|entry
operator|.
name|setId
argument_list|(
name|id
argument_list|)
expr_stmt|;
name|base
operator|.
name|insertEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Throwable
name|ex
parameter_list|)
block|{
name|ex
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

