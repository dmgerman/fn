begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2016 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.event
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|event
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
name|model
operator|.
name|database
operator|.
name|BibDatabase
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
name|model
operator|.
name|entry
operator|.
name|BibEntry
import|;
end_import

begin_comment
comment|/**  * {@link EntryAddedEvent} is fired when a new {@link BibEntry} was added to the {@link BibDatabase}.  */
end_comment

begin_class
DECL|class|EntryAddedEvent
specifier|public
class|class
name|EntryAddedEvent
extends|extends
name|EntryEvent
block|{
comment|/**      * flag if the addition is the undo of a deletion/cut      */
DECL|field|isUndo
specifier|private
name|boolean
name|isUndo
decl_stmt|;
comment|/**      * @param bibEntry the entry which has been added      */
DECL|method|EntryAddedEvent (BibEntry bibEntry)
specifier|public
name|EntryAddedEvent
parameter_list|(
name|BibEntry
name|bibEntry
parameter_list|)
block|{
name|super
argument_list|(
name|bibEntry
argument_list|)
expr_stmt|;
name|this
operator|.
name|isUndo
operator|=
literal|false
expr_stmt|;
block|}
comment|/**      * @param bibEntry the entry which has been added      * @param isUndo   flag if the addition is the undo of a deletion/cut      */
DECL|method|EntryAddedEvent (BibEntry bibEntry, boolean isUndo)
specifier|public
name|EntryAddedEvent
parameter_list|(
name|BibEntry
name|bibEntry
parameter_list|,
name|boolean
name|isUndo
parameter_list|)
block|{
name|super
argument_list|(
name|bibEntry
argument_list|)
expr_stmt|;
name|this
operator|.
name|isUndo
operator|=
name|isUndo
expr_stmt|;
block|}
DECL|method|isUndo ()
specifier|public
name|boolean
name|isUndo
parameter_list|()
block|{
return|return
name|isUndo
return|;
block|}
block|}
end_class

end_unit

