begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* Copyright (C) 2003 Nizar N. Batada, Morten O. Alver  All programs in this directory and subdirectories are published under the GNU General Public License as described below.  This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.  You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA  Further information about the GNU GPL is available at: http://www.gnu.org/copyleft/gpl.ja.html  */
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
name|BibtexEntry
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
name|BibtexEntryType
import|;
end_import

begin_comment
comment|/**  * This class represents the change of type for an entry.  */
end_comment

begin_class
DECL|class|UndoableChangeType
specifier|public
class|class
name|UndoableChangeType
extends|extends
name|AbstractUndoableEdit
block|{
DECL|field|oldType
DECL|field|newType
name|BibtexEntryType
name|oldType
decl_stmt|,
name|newType
decl_stmt|;
DECL|field|be
name|BibtexEntry
name|be
decl_stmt|;
DECL|method|UndoableChangeType (BibtexEntry be, BibtexEntryType oldType, BibtexEntryType newType)
specifier|public
name|UndoableChangeType
parameter_list|(
name|BibtexEntry
name|be
parameter_list|,
name|BibtexEntryType
name|oldType
parameter_list|,
name|BibtexEntryType
name|newType
parameter_list|)
block|{
name|this
operator|.
name|oldType
operator|=
name|oldType
expr_stmt|;
name|this
operator|.
name|newType
operator|=
name|newType
expr_stmt|;
name|this
operator|.
name|be
operator|=
name|be
expr_stmt|;
block|}
DECL|method|getUndoPresentationName ()
specifier|public
name|String
name|getUndoPresentationName
parameter_list|()
block|{
return|return
literal|"Undo: change type"
return|;
block|}
DECL|method|getRedoPresentationName ()
specifier|public
name|String
name|getRedoPresentationName
parameter_list|()
block|{
return|return
literal|"Redo: change type"
return|;
block|}
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
name|be
operator|.
name|setType
argument_list|(
name|oldType
argument_list|)
expr_stmt|;
block|}
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
name|be
operator|.
name|setType
argument_list|(
name|newType
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

