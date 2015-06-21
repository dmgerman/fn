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
name|UndoableFieldChange
import|;
end_import

begin_class
DECL|class|EntryChange
specifier|public
class|class
name|EntryChange
extends|extends
name|Change
block|{
DECL|field|memEntry
DECL|field|tmpEntry
DECL|field|diskEntry
name|BibtexEntry
name|memEntry
decl_stmt|,
name|tmpEntry
decl_stmt|,
name|diskEntry
decl_stmt|;
DECL|field|isModifiedLocally
DECL|field|modificationsAgree
name|boolean
name|isModifiedLocally
decl_stmt|,
name|modificationsAgree
decl_stmt|;
DECL|method|EntryChange (BibtexEntry memEntry, BibtexEntry tmpEntry, BibtexEntry diskEntry)
specifier|public
name|EntryChange
parameter_list|(
name|BibtexEntry
name|memEntry
parameter_list|,
name|BibtexEntry
name|tmpEntry
parameter_list|,
name|BibtexEntry
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
name|name
operator|=
literal|"Modified entry"
expr_stmt|;
else|else
name|name
operator|=
literal|"Modified entry: '"
operator|+
name|key
operator|+
literal|"'"
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
name|this
operator|.
name|diskEntry
operator|=
name|diskEntry
expr_stmt|;
comment|// We know that tmpEntry is not equal to diskEntry. Check if it has been modified
comment|// locally as well, since last tempfile was saved.
name|isModifiedLocally
operator|=
operator|!
operator|(
name|DuplicateCheck
operator|.
name|compareEntriesStrictly
argument_list|(
name|memEntry
argument_list|,
name|tmpEntry
argument_list|)
operator|>
literal|1
operator|)
expr_stmt|;
comment|// Another (unlikely?) possibility is that both disk and mem version has been modified
comment|// in the same way. Check for this, too.
name|modificationsAgree
operator|=
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
expr_stmt|;
comment|//Util.pr("Modified entry: "+memEntry.getCiteKey()+"\n Modified locally: "+isModifiedLocally
comment|//        +" Modifications agree: "+modificationsAgree);
name|TreeSet
argument_list|<
name|String
argument_list|>
name|allFields
init|=
operator|new
name|TreeSet
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|allFields
operator|.
name|addAll
argument_list|(
name|memEntry
operator|.
name|getAllFields
argument_list|()
argument_list|)
expr_stmt|;
name|allFields
operator|.
name|addAll
argument_list|(
name|tmpEntry
operator|.
name|getAllFields
argument_list|()
argument_list|)
expr_stmt|;
name|allFields
operator|.
name|addAll
argument_list|(
name|diskEntry
operator|.
name|getAllFields
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
decl_stmt|,
name|tmp
init|=
name|tmpEntry
operator|.
name|getField
argument_list|(
name|field
argument_list|)
decl_stmt|,
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
name|equals
argument_list|(
literal|""
argument_list|)
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
elseif|else
if|if
condition|(
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
name|equals
argument_list|(
literal|""
argument_list|)
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
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
block|{
comment|// Deleted externally and not locally.
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
comment|//Util.pr("Field: "+fld.next());
block|}
block|}
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
name|boolean
name|allAccepted
init|=
literal|true
decl_stmt|;
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
name|Enumeration
argument_list|<
name|Change
argument_list|>
name|e
init|=
operator|(
name|Enumeration
argument_list|<
name|Change
argument_list|>
operator|)
name|children
argument_list|()
decl_stmt|;
for|for
control|(
init|;
name|e
operator|.
name|hasMoreElements
argument_list|()
condition|;
control|)
block|{
name|Change
name|c
init|=
name|e
operator|.
name|nextElement
argument_list|()
decl_stmt|;
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
else|else
name|allAccepted
operator|=
literal|false
expr_stmt|;
block|}
comment|/*panel.database().removeEntry(memEntry.getId());         try {           diskEntry.setId(Util.createNeutralId());         } catch (KeyCollisionException ex) {}         panel.database().removeEntry(memEntry.getId());*/
return|return
name|allAccepted
return|;
block|}
DECL|method|description ()
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
class|class
name|FieldChange
extends|extends
name|Change
block|{
DECL|field|entry
DECL|field|tmpEntry
name|BibtexEntry
name|entry
decl_stmt|,
name|tmpEntry
decl_stmt|;
DECL|field|field
DECL|field|inMem
DECL|field|onTmp
DECL|field|onDisk
name|String
name|field
decl_stmt|,
name|inMem
decl_stmt|,
name|onTmp
decl_stmt|,
name|onDisk
decl_stmt|;
DECL|field|tp
name|InfoPane
name|tp
init|=
operator|new
name|InfoPane
argument_list|()
decl_stmt|;
DECL|field|sp
name|JScrollPane
name|sp
init|=
operator|new
name|JScrollPane
argument_list|(
name|tp
argument_list|)
decl_stmt|;
DECL|method|FieldChange (String field, BibtexEntry memEntry, BibtexEntry tmpEntry, String inMem, String onTmp, String onDisk)
specifier|public
name|FieldChange
parameter_list|(
name|String
name|field
parameter_list|,
name|BibtexEntry
name|memEntry
parameter_list|,
name|BibtexEntry
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
name|name
operator|=
name|field
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
name|onTmp
operator|=
name|onTmp
expr_stmt|;
name|this
operator|.
name|onDisk
operator|=
name|onDisk
expr_stmt|;
name|StringBuffer
name|text
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|text
operator|.
name|append
argument_list|(
literal|"<FONT SIZE=10>"
argument_list|)
expr_stmt|;
name|text
operator|.
name|append
argument_list|(
literal|"<H2>"
argument_list|)
operator|.
name|append
argument_list|(
name|Globals
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
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
name|text
operator|.
name|append
argument_list|(
literal|"<H3>"
argument_list|)
operator|.
name|append
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Value set externally"
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|":</H3>"
operator|+
literal|" "
argument_list|)
operator|.
name|append
argument_list|(
name|onDisk
argument_list|)
expr_stmt|;
else|else
name|text
operator|.
name|append
argument_list|(
literal|"<H3>"
argument_list|)
operator|.
name|append
argument_list|(
name|Globals
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
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
name|text
operator|.
name|append
argument_list|(
literal|"<H3>"
argument_list|)
operator|.
name|append
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Current value"
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|":</H3>"
operator|+
literal|" "
argument_list|)
operator|.
name|append
argument_list|(
name|inMem
argument_list|)
expr_stmt|;
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
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
name|text
operator|.
name|append
argument_list|(
literal|"<H3>"
argument_list|)
operator|.
name|append
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Current tmp value"
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|":</H3>"
operator|+
literal|" "
argument_list|)
operator|.
name|append
argument_list|(
name|onTmp
argument_list|)
expr_stmt|;
else|else
block|{
comment|// No value in memory.
comment|/*if ((onTmp != null)&& !onTmp.equals(inMem))                   text.append("<H2>"+Globals.lang("You have cleared this field. Original value")+":</H2>"                               +" "+onTmp);*/
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
comment|//System.out.println(field+" "+onDisk);
name|entry
operator|.
name|setField
argument_list|(
name|field
argument_list|,
name|onDisk
argument_list|)
expr_stmt|;
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
name|tmpEntry
operator|.
name|setField
argument_list|(
name|field
argument_list|,
name|onDisk
argument_list|)
expr_stmt|;
return|return
literal|true
return|;
block|}
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
block|}
end_class

end_unit

