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
name|CompoundEdit
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|Globals
import|;
end_import

begin_class
DECL|class|NamedCompound
specifier|public
class|class
name|NamedCompound
extends|extends
name|CompoundEdit
block|{
DECL|field|name
name|String
name|name
decl_stmt|;
DECL|field|hasEdits
name|boolean
name|hasEdits
init|=
literal|false
decl_stmt|;
DECL|method|NamedCompound (String name)
specifier|public
name|NamedCompound
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|super
argument_list|()
expr_stmt|;
name|this
operator|.
name|name
operator|=
name|name
expr_stmt|;
block|}
DECL|method|addEdit (UndoableEdit undoableEdit)
specifier|public
name|boolean
name|addEdit
parameter_list|(
name|UndoableEdit
name|undoableEdit
parameter_list|)
block|{
name|hasEdits
operator|=
literal|true
expr_stmt|;
return|return
name|super
operator|.
name|addEdit
argument_list|(
name|undoableEdit
argument_list|)
return|;
block|}
DECL|method|hasEdits ()
specifier|public
name|boolean
name|hasEdits
parameter_list|()
block|{
return|return
name|hasEdits
return|;
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
name|name
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
name|name
return|;
block|}
comment|/**      * Returns the name of this compound, without the Undo or Redo prefix.      */
DECL|method|getNameOnly ()
specifier|public
name|String
name|getNameOnly
parameter_list|()
block|{
return|return
name|name
return|;
block|}
block|}
end_class

end_unit

