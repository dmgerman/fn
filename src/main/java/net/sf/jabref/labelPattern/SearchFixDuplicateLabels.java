begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.labelPattern
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|labelPattern
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
name|undo
operator|.
name|UndoableKeyChange
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
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_comment
comment|/**  * Function for resolving duplicate BibTeX keys.  */
end_comment

begin_class
DECL|class|SearchFixDuplicateLabels
specifier|public
class|class
name|SearchFixDuplicateLabels
extends|extends
name|AbstractWorker
block|{
DECL|field|panel
specifier|private
specifier|final
name|BasePanel
name|panel
decl_stmt|;
DECL|field|dupes
name|HashMap
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|BibtexEntry
argument_list|>
argument_list|>
name|dupes
init|=
literal|null
decl_stmt|;
DECL|method|SearchFixDuplicateLabels (BasePanel panel)
specifier|public
name|SearchFixDuplicateLabels
parameter_list|(
name|BasePanel
name|panel
parameter_list|)
block|{
name|this
operator|.
name|panel
operator|=
name|panel
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
comment|// Find all multiple occurences of BibTeX keys.
name|dupes
operator|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|BibtexEntry
argument_list|>
argument_list|>
argument_list|()
expr_stmt|;
name|HashMap
argument_list|<
name|String
argument_list|,
name|BibtexEntry
argument_list|>
name|foundKeys
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|BibtexEntry
argument_list|>
argument_list|()
decl_stmt|;
name|BibtexDatabase
name|db
init|=
name|panel
operator|.
name|database
argument_list|()
decl_stmt|;
for|for
control|(
name|BibtexEntry
name|entry
range|:
name|db
operator|.
name|getEntries
argument_list|()
control|)
block|{
name|String
name|key
init|=
name|entry
operator|.
name|getCiteKey
argument_list|()
decl_stmt|;
comment|// Only handle keys that are actually set:
if|if
condition|(
operator|(
name|key
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|key
operator|.
name|length
argument_list|()
operator|>
literal|0
operator|)
condition|)
block|{
comment|// See whether this entry's key is already known:
if|if
condition|(
operator|!
name|foundKeys
operator|.
name|containsKey
argument_list|(
name|key
argument_list|)
condition|)
block|{
comment|// Not already known. Add key and entry to map:
name|foundKeys
operator|.
name|put
argument_list|(
name|key
argument_list|,
name|entry
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// Already known, so we have found a dupe. See if it was already found as a dupe:
if|if
condition|(
name|dupes
operator|.
name|containsKey
argument_list|(
name|key
argument_list|)
condition|)
block|{
comment|// Already in the dupe map. Add this entry as well:
name|dupes
operator|.
name|get
argument_list|(
name|key
argument_list|)
operator|.
name|add
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// Construct a list of entries for this key:
name|ArrayList
argument_list|<
name|BibtexEntry
argument_list|>
name|al
init|=
operator|new
name|ArrayList
argument_list|<
name|BibtexEntry
argument_list|>
argument_list|()
decl_stmt|;
comment|// Add both the first one we found, and the one we found just now:
name|al
operator|.
name|add
argument_list|(
name|foundKeys
operator|.
name|get
argument_list|(
name|key
argument_list|)
argument_list|)
expr_stmt|;
name|al
operator|.
name|add
argument_list|(
name|entry
argument_list|)
expr_stmt|;
comment|// Add the list to the dupe map:
name|dupes
operator|.
name|put
argument_list|(
name|key
argument_list|,
name|al
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
block|}
annotation|@
name|Override
DECL|method|init ()
specifier|public
name|void
name|init
parameter_list|()
throws|throws
name|Throwable
block|{
name|panel
operator|.
name|output
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Resolving duplicate BibTeX keys..."
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|update ()
specifier|public
name|void
name|update
parameter_list|()
block|{
name|List
argument_list|<
name|BibtexEntry
argument_list|>
name|toGenerateFor
init|=
operator|new
name|ArrayList
argument_list|<
name|BibtexEntry
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|key
range|:
name|dupes
operator|.
name|keySet
argument_list|()
control|)
block|{
name|ResolveDuplicateLabelDialog
name|rdld
init|=
operator|new
name|ResolveDuplicateLabelDialog
argument_list|(
name|panel
argument_list|,
name|key
argument_list|,
name|dupes
operator|.
name|get
argument_list|(
name|key
argument_list|)
argument_list|)
decl_stmt|;
name|rdld
operator|.
name|show
argument_list|()
expr_stmt|;
if|if
condition|(
name|rdld
operator|.
name|isOkPressed
argument_list|()
condition|)
block|{
name|List
argument_list|<
name|JCheckBox
argument_list|>
name|cbs
init|=
name|rdld
operator|.
name|getCheckBoxes
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|cbs
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|cbs
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|.
name|isSelected
argument_list|()
condition|)
block|{
comment|// The checkbox for entry i has been selected, so we should generate a new key for it:
name|toGenerateFor
operator|.
name|add
argument_list|(
name|dupes
operator|.
name|get
argument_list|(
name|key
argument_list|)
operator|.
name|get
argument_list|(
name|i
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
comment|// Do the actual generation:
if|if
condition|(
name|toGenerateFor
operator|.
name|size
argument_list|()
operator|>
literal|0
condition|)
block|{
name|NamedCompound
name|ce
init|=
operator|new
name|NamedCompound
argument_list|(
literal|"resolve duplicate keys"
argument_list|)
decl_stmt|;
for|for
control|(
name|BibtexEntry
name|entry
range|:
name|toGenerateFor
control|)
block|{
name|String
name|oldKey
init|=
name|entry
operator|.
name|getCiteKey
argument_list|()
decl_stmt|;
name|entry
operator|=
name|LabelPatternUtil
operator|.
name|makeLabel
argument_list|(
name|panel
operator|.
name|metaData
argument_list|()
argument_list|,
name|panel
operator|.
name|database
argument_list|()
argument_list|,
name|entry
argument_list|)
expr_stmt|;
name|ce
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableKeyChange
argument_list|(
name|panel
operator|.
name|database
argument_list|()
argument_list|,
name|entry
operator|.
name|getId
argument_list|()
argument_list|,
name|oldKey
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
name|BibtexFields
operator|.
name|KEY_FIELD
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
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
block|}
name|panel
operator|.
name|output
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Finished resolving duplicate BibTeX keys. %0 entries modified."
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|toGenerateFor
operator|.
name|size
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

