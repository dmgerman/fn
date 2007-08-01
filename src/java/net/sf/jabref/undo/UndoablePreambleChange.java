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
name|BasePanel
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
name|BibtexDatabase
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
name|Globals
import|;
end_import

begin_comment
comment|/**  * This class represents a change in any field value. The relevant  * information is the BibtexEntry, the field name, the old and the  * new value. Old/new values can be null.  */
end_comment

begin_class
DECL|class|UndoablePreambleChange
specifier|public
class|class
name|UndoablePreambleChange
extends|extends
name|AbstractUndoableEdit
block|{
DECL|field|base
specifier|private
name|BibtexDatabase
name|base
decl_stmt|;
DECL|field|oldValue
DECL|field|newValue
specifier|private
name|String
name|oldValue
decl_stmt|,
name|newValue
decl_stmt|;
DECL|field|panel
specifier|private
name|BasePanel
name|panel
decl_stmt|;
DECL|method|UndoablePreambleChange (BibtexDatabase base, BasePanel panel, String oldValue, String newValue)
specifier|public
name|UndoablePreambleChange
parameter_list|(
name|BibtexDatabase
name|base
parameter_list|,
name|BasePanel
name|panel
parameter_list|,
name|String
name|oldValue
parameter_list|,
name|String
name|newValue
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
name|oldValue
operator|=
name|oldValue
expr_stmt|;
name|this
operator|.
name|newValue
operator|=
name|newValue
expr_stmt|;
name|this
operator|.
name|panel
operator|=
name|panel
expr_stmt|;
block|}
DECL|method|getUndoPresentationName ()
specifier|public
name|String
name|getUndoPresentationName
parameter_list|()
block|{
return|return
name|Globals
operator|.
name|lang
argument_list|(
literal|"Undo"
argument_list|)
operator|+
literal|": "
operator|+
name|Globals
operator|.
name|lang
argument_list|(
literal|"change preamble"
argument_list|)
return|;
block|}
DECL|method|getRedoPresentationName ()
specifier|public
name|String
name|getRedoPresentationName
parameter_list|()
block|{
return|return
name|Globals
operator|.
name|lang
argument_list|(
literal|"Redo"
argument_list|)
operator|+
literal|": "
operator|+
name|Globals
operator|.
name|lang
argument_list|(
literal|"change preamble"
argument_list|)
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
comment|// Revert the change.
name|base
operator|.
name|setPreamble
argument_list|(
name|oldValue
argument_list|)
expr_stmt|;
comment|// If the preamble editor is open, update it.
name|panel
operator|.
name|updatePreamble
argument_list|()
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
comment|// Redo the change.
name|base
operator|.
name|setPreamble
argument_list|(
name|newValue
argument_list|)
expr_stmt|;
comment|// If the preamble editor is open, update it.
name|panel
operator|.
name|updatePreamble
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

