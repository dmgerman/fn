begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|java
operator|.
name|util
operator|.
name|Collections
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Enumeration
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Set
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|TreeSet
import|;
end_import

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
name|JLabel
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
name|UndoableFieldChange
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

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|Log
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|LogFactory
import|;
end_import

begin_class
DECL|class|EntryChange
class|class
name|EntryChange
extends|extends
name|Change
block|{
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Log
name|LOGGER
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|EntryChange
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|method|EntryChange (BibEntry memEntry, BibEntry tmpEntry, BibEntry diskEntry)
specifier|public
name|EntryChange
parameter_list|(
name|BibEntry
name|memEntry
parameter_list|,
name|BibEntry
name|tmpEntry
parameter_list|,
name|BibEntry
name|diskEntry
parameter_list|)
block|{
name|super
argument_list|()
expr_stmt|;
name|String
name|key
init|=
name|tmpEntry
operator|.
name|getCiteKey
argument_list|()
decl_stmt|;
if|if
condition|(
name|key
operator|==
literal|null
condition|)
block|{
name|name
operator|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"Modified entry"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|name
operator|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"Modified entry"
argument_list|)
operator|+
literal|": '"
operator|+
name|key
operator|+
literal|'\''
expr_stmt|;
block|}
comment|// We know that tmpEntry is not equal to diskEntry. Check if it has been modified
comment|// locally as well, since last tempfile was saved.
name|boolean
name|isModifiedLocally
init|=
operator|(
name|DuplicateCheck
operator|.
name|compareEntriesStrictly
argument_list|(
name|memEntry
argument_list|,
name|tmpEntry
argument_list|)
operator|<=
literal|1
operator|)
decl_stmt|;
comment|// Another (unlikely?) possibility is that both disk and mem version has been modified
comment|// in the same way. Check for this, too.
name|boolean
name|modificationsAgree
init|=
operator|(
name|DuplicateCheck
operator|.
name|compareEntriesStrictly
argument_list|(
name|memEntry
argument_list|,
name|diskEntry
argument_list|)
operator|>
literal|1
operator|)
decl_stmt|;
name|LOGGER
operator|.
name|debug
argument_list|(
literal|"Modified entry: "
operator|+
name|memEntry
operator|.
name|getCiteKey
argument_list|()
operator|+
literal|"\n Modified locally: "
operator|+
name|isModifiedLocally
operator|+
literal|" Modifications agree: "
operator|+
name|modificationsAgree
argument_list|)
expr_stmt|;
name|Set
argument_list|<
name|String
argument_list|>
name|allFields
init|=
operator|new
name|TreeSet
argument_list|<>
argument_list|()
decl_stmt|;
name|allFields
operator|.
name|addAll
argument_list|(
name|memEntry
operator|.
name|getFieldNames
argument_list|()
argument_list|)
expr_stmt|;
name|allFields
operator|.
name|addAll
argument_list|(
name|tmpEntry
operator|.
name|getFieldNames
argument_list|()
argument_list|)
expr_stmt|;
name|allFields
operator|.
name|addAll
argument_list|(
name|diskEntry
operator|.
name|getFieldNames
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|String
name|field
range|:
name|allFields
control|)
block|{
name|String
name|mem
init|=
name|memEntry
operator|.
name|getField
argument_list|(
name|field
argument_list|)
decl_stmt|;
name|String
name|tmp
init|=
name|tmpEntry
operator|.
name|getField
argument_list|(
name|field
argument_list|)
decl_stmt|;
name|String
name|disk
init|=
name|diskEntry
operator|.
name|getField
argument_list|(
name|field
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|tmp
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|disk
operator|!=
literal|null
operator|)
condition|)
block|{
if|if
condition|(
operator|!
name|tmp
operator|.
name|equals
argument_list|(
name|disk
argument_list|)
condition|)
block|{
comment|// Modified externally.
name|add
argument_list|(
operator|new
name|FieldChange
argument_list|(
name|field
argument_list|,
name|memEntry
argument_list|,
name|tmpEntry
argument_list|,
name|mem
argument_list|,
name|tmp
argument_list|,
name|disk
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
operator|(
operator|(
name|tmp
operator|==
literal|null
operator|)
operator|&&
operator|(
name|disk
operator|!=
literal|null
operator|)
operator|&&
operator|!
name|disk
operator|.
name|isEmpty
argument_list|()
operator|)
operator|||
operator|(
operator|(
name|disk
operator|==
literal|null
operator|)
operator|&&
operator|(
name|tmp
operator|!=
literal|null
operator|)
operator|&&
operator|!
name|tmp
operator|.
name|isEmpty
argument_list|()
operator|&&
operator|(
name|mem
operator|!=
literal|null
operator|)
operator|&&
operator|!
name|mem
operator|.
name|isEmpty
argument_list|()
operator|)
condition|)
block|{
comment|// Added externally.
name|add
argument_list|(
operator|new
name|FieldChange
argument_list|(
name|field
argument_list|,
name|memEntry
argument_list|,
name|tmpEntry
argument_list|,
name|mem
argument_list|,
name|tmp
argument_list|,
name|disk
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
annotation|@
name|Override
DECL|method|makeChange (BasePanel panel, BibDatabase secondary, NamedCompound undoEdit)
specifier|public
name|boolean
name|makeChange
parameter_list|(
name|BasePanel
name|panel
parameter_list|,
name|BibDatabase
name|secondary
parameter_list|,
name|NamedCompound
name|undoEdit
parameter_list|)
block|{
name|boolean
name|allAccepted
init|=
literal|true
decl_stmt|;
name|Enumeration
argument_list|<
name|Change
argument_list|>
name|e
init|=
name|children
argument_list|()
decl_stmt|;
for|for
control|(
name|Change
name|c
range|:
name|Collections
operator|.
name|list
argument_list|(
name|e
argument_list|)
control|)
block|{
if|if
condition|(
name|c
operator|.
name|isAcceptable
argument_list|()
operator|&&
name|c
operator|.
name|isAccepted
argument_list|()
condition|)
block|{
name|c
operator|.
name|makeChange
argument_list|(
name|panel
argument_list|,
name|secondary
argument_list|,
name|undoEdit
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|allAccepted
operator|=
literal|false
expr_stmt|;
block|}
block|}
comment|/*panel.database().removeEntry(memEntry.getId());         try {           diskEntry.setId(Util.next());         } catch (KeyCollisionException ex) {}         panel.database().removeEntry(memEntry.getId());*/
return|return
name|allAccepted
return|;
block|}
annotation|@
name|Override
DECL|method|description ()
specifier|public
name|JComponent
name|description
parameter_list|()
block|{
return|return
operator|new
name|JLabel
argument_list|(
name|name
argument_list|)
return|;
block|}
DECL|class|FieldChange
specifier|static
class|class
name|FieldChange
extends|extends
name|Change
block|{
DECL|field|entry
specifier|private
specifier|final
name|BibEntry
name|entry
decl_stmt|;
DECL|field|tmpEntry
specifier|private
specifier|final
name|BibEntry
name|tmpEntry
decl_stmt|;
DECL|field|field
specifier|private
specifier|final
name|String
name|field
decl_stmt|;
DECL|field|inMem
specifier|private
specifier|final
name|String
name|inMem
decl_stmt|;
DECL|field|onDisk
specifier|private
specifier|final
name|String
name|onDisk
decl_stmt|;
DECL|field|tp
specifier|private
specifier|final
name|InfoPane
name|tp
init|=
operator|new
name|InfoPane
argument_list|()
decl_stmt|;
DECL|field|sp
specifier|private
specifier|final
name|JScrollPane
name|sp
init|=
operator|new
name|JScrollPane
argument_list|(
name|tp
argument_list|)
decl_stmt|;
DECL|method|FieldChange (String field, BibEntry memEntry, BibEntry tmpEntry, String inMem, String onTmp, String onDisk)
specifier|public
name|FieldChange
parameter_list|(
name|String
name|field
parameter_list|,
name|BibEntry
name|memEntry
parameter_list|,
name|BibEntry
name|tmpEntry
parameter_list|,
name|String
name|inMem
parameter_list|,
name|String
name|onTmp
parameter_list|,
name|String
name|onDisk
parameter_list|)
block|{
name|super
argument_list|(
name|field
argument_list|)
expr_stmt|;
name|entry
operator|=
name|memEntry
expr_stmt|;
name|this
operator|.
name|tmpEntry
operator|=
name|tmpEntry
expr_stmt|;
name|this
operator|.
name|field
operator|=
name|field
expr_stmt|;
name|this
operator|.
name|inMem
operator|=
name|inMem
expr_stmt|;
name|this
operator|.
name|onDisk
operator|=
name|onDisk
expr_stmt|;
name|StringBuilder
name|text
init|=
operator|new
name|StringBuilder
argument_list|(
literal|36
argument_list|)
decl_stmt|;
name|text
operator|.
name|append
argument_list|(
literal|"<FONT SIZE=10><H2>"
argument_list|)
operator|.
name|append
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Modification of field"
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|"<I>"
argument_list|)
operator|.
name|append
argument_list|(
name|field
argument_list|)
operator|.
name|append
argument_list|(
literal|"</I></H2>"
argument_list|)
expr_stmt|;
if|if
condition|(
operator|(
name|onDisk
operator|!=
literal|null
operator|)
operator|&&
operator|!
name|onDisk
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|text
operator|.
name|append
argument_list|(
literal|"<H3>"
argument_list|)
operator|.
name|append
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Value set externally"
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|":</H3> "
argument_list|)
operator|.
name|append
argument_list|(
name|onDisk
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|text
operator|.
name|append
argument_list|(
literal|"<H3>"
argument_list|)
operator|.
name|append
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Value cleared externally"
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|"</H3>"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|(
name|inMem
operator|!=
literal|null
operator|)
operator|&&
operator|!
name|inMem
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|text
operator|.
name|append
argument_list|(
literal|"<H3>"
argument_list|)
operator|.
name|append
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Current value"
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|":</H3> "
argument_list|)
operator|.
name|append
argument_list|(
name|inMem
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|(
name|onTmp
operator|!=
literal|null
operator|)
operator|&&
operator|!
name|onTmp
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|text
operator|.
name|append
argument_list|(
literal|"<H3>"
argument_list|)
operator|.
name|append
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Current tmp value"
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|":</H3> "
argument_list|)
operator|.
name|append
argument_list|(
name|onTmp
argument_list|)
expr_stmt|;
block|}
name|tp
operator|.
name|setContentType
argument_list|(
literal|"text/html"
argument_list|)
expr_stmt|;
name|tp
operator|.
name|setText
argument_list|(
name|text
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|makeChange (BasePanel panel, BibDatabase secondary, NamedCompound undoEdit)
specifier|public
name|boolean
name|makeChange
parameter_list|(
name|BasePanel
name|panel
parameter_list|,
name|BibDatabase
name|secondary
parameter_list|,
name|NamedCompound
name|undoEdit
parameter_list|)
block|{
if|if
condition|(
name|onDisk
operator|==
literal|null
condition|)
block|{
name|entry
operator|.
name|clearField
argument_list|(
name|field
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|entry
operator|.
name|setField
argument_list|(
name|field
argument_list|,
name|onDisk
argument_list|)
expr_stmt|;
block|}
name|undoEdit
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableFieldChange
argument_list|(
name|entry
argument_list|,
name|field
argument_list|,
name|inMem
argument_list|,
name|onDisk
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|onDisk
operator|==
literal|null
condition|)
block|{
name|tmpEntry
operator|.
name|clearField
argument_list|(
name|field
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|tmpEntry
operator|.
name|setField
argument_list|(
name|field
argument_list|,
name|onDisk
argument_list|)
expr_stmt|;
block|}
return|return
literal|true
return|;
block|}
annotation|@
name|Override
DECL|method|description ()
specifier|public
name|JComponent
name|description
parameter_list|()
block|{
return|return
name|sp
return|;
block|}
block|}
block|}
end_class

end_unit

