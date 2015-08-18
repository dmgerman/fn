begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2014 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.gui.journals
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|journals
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
name|Globals
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
name|gui
operator|.
name|worker
operator|.
name|AbstractWorker
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
name|logic
operator|.
name|l10n
operator|.
name|Localization
import|;
end_import

begin_comment
comment|/**  * Converts journal full names to either iso or medline abbreviations for all selected entries.  */
end_comment

begin_class
DECL|class|AbbreviateAction
specifier|public
class|class
name|AbbreviateAction
extends|extends
name|AbstractWorker
block|{
DECL|field|panel
specifier|private
specifier|final
name|BasePanel
name|panel
decl_stmt|;
DECL|field|message
specifier|private
name|String
name|message
init|=
literal|""
decl_stmt|;
DECL|field|iso
specifier|private
specifier|final
name|boolean
name|iso
decl_stmt|;
DECL|method|AbbreviateAction (BasePanel panel, boolean iso)
specifier|public
name|AbbreviateAction
parameter_list|(
name|BasePanel
name|panel
parameter_list|,
name|boolean
name|iso
parameter_list|)
block|{
name|this
operator|.
name|panel
operator|=
name|panel
expr_stmt|;
name|this
operator|.
name|iso
operator|=
name|iso
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|init ()
specifier|public
name|void
name|init
parameter_list|()
block|{
name|panel
operator|.
name|output
argument_list|(
literal|"Abbreviating..."
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|run ()
specifier|public
name|void
name|run
parameter_list|()
block|{
name|BibtexEntry
index|[]
name|entries
init|=
name|panel
operator|.
name|getSelectedEntries
argument_list|()
decl_stmt|;
if|if
condition|(
name|entries
operator|==
literal|null
condition|)
block|{
return|return;
block|}
name|UndoableAbbreviator
name|undoableAbbreviator
init|=
operator|new
name|UndoableAbbreviator
argument_list|(
name|Globals
operator|.
name|journalAbbrev
argument_list|,
name|iso
argument_list|)
decl_stmt|;
name|NamedCompound
name|ce
init|=
operator|new
name|NamedCompound
argument_list|(
literal|"Abbreviate journal names"
argument_list|)
decl_stmt|;
name|int
name|count
init|=
literal|0
decl_stmt|;
for|for
control|(
name|BibtexEntry
name|entry
range|:
name|entries
control|)
block|{
if|if
condition|(
name|undoableAbbreviator
operator|.
name|abbreviate
argument_list|(
name|panel
operator|.
name|database
argument_list|()
argument_list|,
name|entry
argument_list|,
literal|"journal"
argument_list|,
name|ce
argument_list|)
condition|)
block|{
name|count
operator|++
expr_stmt|;
block|}
if|if
condition|(
name|undoableAbbreviator
operator|.
name|abbreviate
argument_list|(
name|panel
operator|.
name|database
argument_list|()
argument_list|,
name|entry
argument_list|,
literal|"journaltitle"
argument_list|,
name|ce
argument_list|)
condition|)
block|{
name|count
operator|++
expr_stmt|;
block|}
block|}
if|if
condition|(
name|count
operator|>
literal|0
condition|)
block|{
name|ce
operator|.
name|end
argument_list|()
expr_stmt|;
name|panel
operator|.
name|undoManager
operator|.
name|addEdit
argument_list|(
name|ce
argument_list|)
expr_stmt|;
name|panel
operator|.
name|markBaseChanged
argument_list|()
expr_stmt|;
name|message
operator|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"Abbreviated %0 journal names."
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|count
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|message
operator|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"No journal names could be abbreviated."
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
DECL|method|update ()
specifier|public
name|void
name|update
parameter_list|()
block|{
name|panel
operator|.
name|output
argument_list|(
name|message
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

