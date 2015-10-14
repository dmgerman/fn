begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.collab
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|collab
package|;
end_package

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
name|JScrollPane
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
name|gui
operator|.
name|PreviewPanel
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
name|gui
operator|.
name|undo
operator|.
name|NamedCompound
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
name|gui
operator|.
name|undo
operator|.
name|UndoableRemoveEntry
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
name|bibtex
operator|.
name|DuplicateCheck
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
name|database
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
name|model
operator|.
name|entry
operator|.
name|BibtexEntry
import|;
end_import

begin_class
DECL|class|EntryDeleteChange
class|class
name|EntryDeleteChange
extends|extends
name|Change
block|{
DECL|field|memEntry
specifier|private
specifier|final
name|BibtexEntry
name|memEntry
decl_stmt|;
DECL|field|tmpEntry
specifier|private
specifier|final
name|BibtexEntry
name|tmpEntry
decl_stmt|;
DECL|field|diskEntry
name|BibtexEntry
name|diskEntry
decl_stmt|;
DECL|field|sp
specifier|private
specifier|final
name|JScrollPane
name|sp
decl_stmt|;
DECL|method|EntryDeleteChange (BibtexEntry memEntry, BibtexEntry tmpEntry)
specifier|public
name|EntryDeleteChange
parameter_list|(
name|BibtexEntry
name|memEntry
parameter_list|,
name|BibtexEntry
name|tmpEntry
parameter_list|)
block|{
name|super
argument_list|(
literal|"Deleted entry"
argument_list|)
expr_stmt|;
name|this
operator|.
name|memEntry
operator|=
name|memEntry
expr_stmt|;
name|this
operator|.
name|tmpEntry
operator|=
name|tmpEntry
expr_stmt|;
comment|// Compare the deleted entry in memory with the one in the tmpfile. The
comment|// entry could have been removed in memory.
name|double
name|matchWithTmp
init|=
name|DuplicateCheck
operator|.
name|compareEntriesStrictly
argument_list|(
name|memEntry
argument_list|,
name|tmpEntry
argument_list|)
decl_stmt|;
comment|// Check if it has been modified locally, since last tempfile was saved.
name|boolean
name|isModifiedLocally
init|=
operator|!
operator|(
name|matchWithTmp
operator|>
literal|1
operator|)
decl_stmt|;
comment|//Util.pr("Modified entry: "+memEntry.getCiteKey()+"\n Modified locally: "+isModifiedLocally
comment|//        +" Modifications agree: "+modificationsAgree);
name|PreviewPanel
name|pp
init|=
operator|new
name|PreviewPanel
argument_list|(
literal|null
argument_list|,
name|memEntry
argument_list|,
literal|null
argument_list|,
operator|new
name|MetaData
argument_list|()
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|PREVIEW_0
argument_list|)
argument_list|)
decl_stmt|;
name|sp
operator|=
operator|new
name|JScrollPane
argument_list|(
name|pp
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|makeChange (BasePanel panel, BibtexDatabase secondary, NamedCompound undoEdit)
specifier|public
name|boolean
name|makeChange
parameter_list|(
name|BasePanel
name|panel
parameter_list|,
name|BibtexDatabase
name|secondary
parameter_list|,
name|NamedCompound
name|undoEdit
parameter_list|)
block|{
name|panel
operator|.
name|database
argument_list|()
operator|.
name|removeEntry
argument_list|(
name|memEntry
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
name|undoEdit
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableRemoveEntry
argument_list|(
name|panel
operator|.
name|database
argument_list|()
argument_list|,
name|memEntry
argument_list|,
name|panel
argument_list|)
argument_list|)
expr_stmt|;
name|secondary
operator|.
name|removeEntry
argument_list|(
name|tmpEntry
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
return|return
literal|true
return|;
block|}
annotation|@
name|Override
DECL|method|description ()
name|JComponent
name|description
parameter_list|()
block|{
return|return
name|sp
return|;
block|}
block|}
end_class

end_unit

