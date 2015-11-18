begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) -2015 JabRef contributors.     Copyright (C) 2015 Oliver Kopp      This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.bibtex
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|bibtex
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
name|gui
operator|.
name|BibtexFields
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
name|JabRefPreferences
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
name|exporter
operator|.
name|LatexFieldFormatter
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
name|util
operator|.
name|strings
operator|.
name|StringUtil
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

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|Writer
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|*
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|common
operator|.
name|base
operator|.
name|Strings
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
name|EntryType
import|;
end_import

begin_class
DECL|class|BibtexEntryWriter
specifier|public
class|class
name|BibtexEntryWriter
block|{
comment|/**      * Display name map for entry field names.      */
DECL|field|tagDisplayNameMap
specifier|private
specifier|static
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|tagDisplayNameMap
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
static|static
block|{
comment|// The field name display map.
name|BibtexEntryWriter
operator|.
name|tagDisplayNameMap
operator|.
name|put
argument_list|(
literal|"bibtexkey"
argument_list|,
literal|"BibTeXKey"
argument_list|)
expr_stmt|;
name|BibtexEntryWriter
operator|.
name|tagDisplayNameMap
operator|.
name|put
argument_list|(
literal|"doi"
argument_list|,
literal|"DOI"
argument_list|)
expr_stmt|;
name|BibtexEntryWriter
operator|.
name|tagDisplayNameMap
operator|.
name|put
argument_list|(
literal|"ee"
argument_list|,
literal|"EE"
argument_list|)
expr_stmt|;
name|BibtexEntryWriter
operator|.
name|tagDisplayNameMap
operator|.
name|put
argument_list|(
literal|"howpublished"
argument_list|,
literal|"HowPublished"
argument_list|)
expr_stmt|;
name|BibtexEntryWriter
operator|.
name|tagDisplayNameMap
operator|.
name|put
argument_list|(
literal|"lastchecked"
argument_list|,
literal|"LastChecked"
argument_list|)
expr_stmt|;
name|BibtexEntryWriter
operator|.
name|tagDisplayNameMap
operator|.
name|put
argument_list|(
literal|"isbn"
argument_list|,
literal|"ISBN"
argument_list|)
expr_stmt|;
name|BibtexEntryWriter
operator|.
name|tagDisplayNameMap
operator|.
name|put
argument_list|(
literal|"issn"
argument_list|,
literal|"ISSN"
argument_list|)
expr_stmt|;
name|BibtexEntryWriter
operator|.
name|tagDisplayNameMap
operator|.
name|put
argument_list|(
literal|"UNKNOWN"
argument_list|,
literal|"UNKNOWN"
argument_list|)
expr_stmt|;
name|BibtexEntryWriter
operator|.
name|tagDisplayNameMap
operator|.
name|put
argument_list|(
literal|"url"
argument_list|,
literal|"URL"
argument_list|)
expr_stmt|;
block|}
DECL|field|requiredFieldsSorted
specifier|private
specifier|static
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|String
argument_list|>
argument_list|>
name|requiredFieldsSorted
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|optionalFieldsSorted
specifier|private
specifier|static
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|String
argument_list|>
argument_list|>
name|optionalFieldsSorted
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
comment|/**      * The maximum length of a field name to properly make the alignment of the      * equal sign.      */
DECL|field|maxFieldLength
specifier|private
specifier|static
specifier|final
name|int
name|maxFieldLength
decl_stmt|;
static|static
block|{
comment|// Looking for the longest field name.
comment|// XXX JK: Look for all used field names not only defined once, since
comment|//         there may be some unofficial field name used.
name|int
name|max
init|=
literal|0
decl_stmt|;
for|for
control|(
name|EntryType
name|type
range|:
name|EntryTypes
operator|.
name|getAllValues
argument_list|()
control|)
block|{
for|for
control|(
name|String
name|field
range|:
name|type
operator|.
name|getRequiredFieldsFlat
argument_list|()
control|)
block|{
name|max
operator|=
name|Math
operator|.
name|max
argument_list|(
name|max
argument_list|,
name|field
operator|.
name|length
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|type
operator|.
name|getOptionalFields
argument_list|()
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|String
name|field
range|:
name|type
operator|.
name|getOptionalFields
argument_list|()
control|)
block|{
name|max
operator|=
name|Math
operator|.
name|max
argument_list|(
name|max
argument_list|,
name|field
operator|.
name|length
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
name|maxFieldLength
operator|=
name|max
expr_stmt|;
block|}
DECL|field|fieldFormatter
specifier|private
specifier|final
name|LatexFieldFormatter
name|fieldFormatter
decl_stmt|;
DECL|field|write
specifier|private
specifier|final
name|boolean
name|write
decl_stmt|;
DECL|field|writeFieldCameCaseName
specifier|private
specifier|final
name|boolean
name|writeFieldCameCaseName
init|=
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|WRITEFIELD_CAMELCASENAME
argument_list|)
decl_stmt|;
DECL|field|writeFieldAddSpaces
specifier|private
specifier|final
name|boolean
name|writeFieldAddSpaces
init|=
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|WRITEFIELD_ADDSPACES
argument_list|)
decl_stmt|;
DECL|field|includeEmptyFields
specifier|private
specifier|final
name|boolean
name|includeEmptyFields
init|=
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|INCLUDE_EMPTY_FIELDS
argument_list|)
decl_stmt|;
DECL|field|writeFieldSortStyle
specifier|private
specifier|final
name|int
name|writeFieldSortStyle
init|=
name|Globals
operator|.
name|prefs
operator|.
name|getInt
argument_list|(
name|JabRefPreferences
operator|.
name|WRITEFIELD_SORTSTYLE
argument_list|)
decl_stmt|;
DECL|method|BibtexEntryWriter (LatexFieldFormatter fieldFormatter, boolean write)
specifier|public
name|BibtexEntryWriter
parameter_list|(
name|LatexFieldFormatter
name|fieldFormatter
parameter_list|,
name|boolean
name|write
parameter_list|)
block|{
name|this
operator|.
name|fieldFormatter
operator|=
name|fieldFormatter
expr_stmt|;
name|this
operator|.
name|write
operator|=
name|write
expr_stmt|;
block|}
DECL|method|write (BibtexEntry entry, Writer out)
specifier|public
name|void
name|write
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|,
name|Writer
name|out
parameter_list|)
throws|throws
name|IOException
block|{
switch|switch
condition|(
name|writeFieldSortStyle
condition|)
block|{
case|case
literal|0
case|:
name|writeRequiredFieldsFirstOptionalFieldsSecondRemainingFieldsThird
argument_list|(
name|entry
argument_list|,
name|out
argument_list|)
expr_stmt|;
break|break;
case|case
literal|1
case|:
name|writeRequiredFieldsFirstRemainingFieldsSecond
argument_list|(
name|entry
argument_list|,
name|out
argument_list|)
expr_stmt|;
break|break;
case|case
literal|2
case|:
name|writeUserDefinedOrder
argument_list|(
name|entry
argument_list|,
name|out
argument_list|)
expr_stmt|;
break|break;
block|}
block|}
comment|/**      * new style ver>=2.10, sort the field for requiredFields, optionalFields and other fields separately      *      * @param entry      * @param out      * @throws IOException      */
DECL|method|writeRequiredFieldsFirstOptionalFieldsSecondRemainingFieldsThird (BibtexEntry entry, Writer out)
specifier|private
name|void
name|writeRequiredFieldsFirstOptionalFieldsSecondRemainingFieldsThird
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|,
name|Writer
name|out
parameter_list|)
throws|throws
name|IOException
block|{
comment|// Write header with type and bibtex-key.
name|out
operator|.
name|write
argument_list|(
literal|'@'
operator|+
name|entry
operator|.
name|getType
argument_list|()
operator|.
name|getName
argument_list|()
operator|+
literal|'{'
argument_list|)
expr_stmt|;
name|HashSet
argument_list|<
name|String
argument_list|>
name|writtenFields
init|=
operator|new
name|HashSet
argument_list|<>
argument_list|()
decl_stmt|;
name|writeKeyField
argument_list|(
name|entry
argument_list|,
name|out
argument_list|)
expr_stmt|;
name|writtenFields
operator|.
name|add
argument_list|(
name|BibtexEntry
operator|.
name|KEY_FIELD
argument_list|)
expr_stmt|;
comment|// Write required fields first.
comment|// Thereby, write the title field first.
name|boolean
name|hasWritten
init|=
name|writeField
argument_list|(
name|entry
argument_list|,
name|out
argument_list|,
literal|"title"
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|writtenFields
operator|.
name|add
argument_list|(
literal|"title"
argument_list|)
expr_stmt|;
if|if
condition|(
name|entry
operator|.
name|getRequiredFields
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|requiredFields
init|=
name|getRequiredFieldsSorted
argument_list|(
name|entry
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|value
range|:
name|requiredFields
control|)
block|{
if|if
condition|(
operator|!
name|writtenFields
operator|.
name|contains
argument_list|(
name|value
argument_list|)
condition|)
block|{
comment|// If field appears both in req. and opt. don't repeat.
name|hasWritten
operator|=
name|hasWritten
operator||
name|writeField
argument_list|(
name|entry
argument_list|,
name|out
argument_list|,
name|value
argument_list|,
name|hasWritten
argument_list|)
expr_stmt|;
name|writtenFields
operator|.
name|add
argument_list|(
name|value
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|// Then optional fields
if|if
condition|(
name|entry
operator|.
name|getOptionalFields
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|optionalFields
init|=
name|getOptionalFieldsSorted
argument_list|(
name|entry
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|value
range|:
name|optionalFields
control|)
block|{
if|if
condition|(
operator|!
name|writtenFields
operator|.
name|contains
argument_list|(
name|value
argument_list|)
condition|)
block|{
comment|// If field appears both in req. and opt. don't repeat.
name|hasWritten
operator|=
name|hasWritten
operator||
name|writeField
argument_list|(
name|entry
argument_list|,
name|out
argument_list|,
name|value
argument_list|,
name|hasWritten
argument_list|)
expr_stmt|;
name|writtenFields
operator|.
name|add
argument_list|(
name|value
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|// Then write remaining fields in alphabetic order.
name|TreeSet
argument_list|<
name|String
argument_list|>
name|remainingFields
init|=
operator|new
name|TreeSet
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|key
range|:
name|entry
operator|.
name|getFieldNames
argument_list|()
control|)
block|{
name|boolean
name|writeIt
init|=
name|write
condition|?
name|BibtexFields
operator|.
name|isWriteableField
argument_list|(
name|key
argument_list|)
else|:
name|BibtexFields
operator|.
name|isDisplayableField
argument_list|(
name|key
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|writtenFields
operator|.
name|contains
argument_list|(
name|key
argument_list|)
operator|&&
name|writeIt
condition|)
block|{
name|remainingFields
operator|.
name|add
argument_list|(
name|key
argument_list|)
expr_stmt|;
block|}
block|}
for|for
control|(
name|String
name|field
range|:
name|remainingFields
control|)
block|{
name|hasWritten
operator|=
name|hasWritten
operator||
name|writeField
argument_list|(
name|entry
argument_list|,
name|out
argument_list|,
name|field
argument_list|,
name|hasWritten
argument_list|)
expr_stmt|;
block|}
comment|// Finally, end the entry.
name|out
operator|.
name|write
argument_list|(
operator|(
name|hasWritten
condition|?
name|Globals
operator|.
name|NEWLINE
else|:
literal|""
operator|)
operator|+
literal|'}'
operator|+
name|Globals
operator|.
name|NEWLINE
argument_list|)
expr_stmt|;
block|}
DECL|method|getRequiredFieldsSorted (BibtexEntry entry)
specifier|private
name|List
argument_list|<
name|String
argument_list|>
name|getRequiredFieldsSorted
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|)
block|{
name|String
name|entryTypeName
init|=
name|entry
operator|.
name|getType
argument_list|()
operator|.
name|getName
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|sortedFields
init|=
name|requiredFieldsSorted
operator|.
name|get
argument_list|(
name|entryTypeName
argument_list|)
decl_stmt|;
comment|// put into chache if necessary
if|if
condition|(
name|sortedFields
operator|==
literal|null
condition|)
block|{
name|sortedFields
operator|=
operator|new
name|ArrayList
argument_list|<>
argument_list|(
name|entry
operator|.
name|getRequiredFields
argument_list|()
argument_list|)
expr_stmt|;
name|Collections
operator|.
name|sort
argument_list|(
name|sortedFields
argument_list|)
expr_stmt|;
name|requiredFieldsSorted
operator|.
name|put
argument_list|(
name|entryTypeName
argument_list|,
name|sortedFields
argument_list|)
expr_stmt|;
block|}
return|return
name|sortedFields
return|;
block|}
DECL|method|getOptionalFieldsSorted (BibtexEntry entry)
specifier|private
name|List
argument_list|<
name|String
argument_list|>
name|getOptionalFieldsSorted
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|)
block|{
name|String
name|entryTypeName
init|=
name|entry
operator|.
name|getType
argument_list|()
operator|.
name|getName
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|sortedFields
init|=
name|optionalFieldsSorted
operator|.
name|get
argument_list|(
name|entryTypeName
argument_list|)
decl_stmt|;
comment|// put into chache if necessary
if|if
condition|(
name|sortedFields
operator|==
literal|null
condition|)
block|{
name|sortedFields
operator|=
operator|new
name|ArrayList
argument_list|<>
argument_list|(
name|entry
operator|.
name|getOptionalFields
argument_list|()
argument_list|)
expr_stmt|;
name|Collections
operator|.
name|sort
argument_list|(
name|sortedFields
argument_list|)
expr_stmt|;
name|optionalFieldsSorted
operator|.
name|put
argument_list|(
name|entryTypeName
argument_list|,
name|sortedFields
argument_list|)
expr_stmt|;
block|}
return|return
name|sortedFields
return|;
block|}
comment|/**      * old style ver<=2.9.2, write fields in the order of requiredFields, optionalFields and other fields, but does not sort the fields.      *      * @param entry      * @param out      * @throws IOException      */
DECL|method|writeRequiredFieldsFirstRemainingFieldsSecond (BibtexEntry entry, Writer out)
specifier|private
name|void
name|writeRequiredFieldsFirstRemainingFieldsSecond
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|,
name|Writer
name|out
parameter_list|)
throws|throws
name|IOException
block|{
comment|// Write header with type and bibtex-key.
name|out
operator|.
name|write
argument_list|(
literal|'@'
operator|+
name|entry
operator|.
name|getType
argument_list|()
operator|.
name|getName
argument_list|()
operator|.
name|toUpperCase
argument_list|(
name|Locale
operator|.
name|US
argument_list|)
operator|+
literal|'{'
argument_list|)
expr_stmt|;
name|writeKeyField
argument_list|(
name|entry
argument_list|,
name|out
argument_list|)
expr_stmt|;
name|HashSet
argument_list|<
name|String
argument_list|>
name|written
init|=
operator|new
name|HashSet
argument_list|<>
argument_list|()
decl_stmt|;
name|written
operator|.
name|add
argument_list|(
name|BibtexEntry
operator|.
name|KEY_FIELD
argument_list|)
expr_stmt|;
name|boolean
name|hasWritten
init|=
literal|false
decl_stmt|;
comment|// Write required fields first.
name|List
argument_list|<
name|String
argument_list|>
name|fields
init|=
name|entry
operator|.
name|getRequiredFields
argument_list|()
decl_stmt|;
if|if
condition|(
name|fields
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|String
name|value
range|:
name|fields
control|)
block|{
name|hasWritten
operator|=
name|hasWritten
operator||
name|writeField
argument_list|(
name|entry
argument_list|,
name|out
argument_list|,
name|value
argument_list|,
name|hasWritten
argument_list|)
expr_stmt|;
name|written
operator|.
name|add
argument_list|(
name|value
argument_list|)
expr_stmt|;
block|}
block|}
comment|// Then optional fields.
name|fields
operator|=
name|entry
operator|.
name|getOptionalFields
argument_list|()
expr_stmt|;
if|if
condition|(
name|fields
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|String
name|value
range|:
name|fields
control|)
block|{
if|if
condition|(
operator|!
name|written
operator|.
name|contains
argument_list|(
name|value
argument_list|)
condition|)
block|{
comment|// If field appears both in req. and opt. don't repeat.
name|hasWritten
operator|=
name|hasWritten
operator||
name|writeField
argument_list|(
name|entry
argument_list|,
name|out
argument_list|,
name|value
argument_list|,
name|hasWritten
argument_list|)
expr_stmt|;
name|written
operator|.
name|add
argument_list|(
name|value
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|// Then write remaining fields in alphabetic order.
name|TreeSet
argument_list|<
name|String
argument_list|>
name|remainingFields
init|=
operator|new
name|TreeSet
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|key
range|:
name|entry
operator|.
name|getFieldNames
argument_list|()
control|)
block|{
name|boolean
name|writeIt
init|=
name|write
condition|?
name|BibtexFields
operator|.
name|isWriteableField
argument_list|(
name|key
argument_list|)
else|:
name|BibtexFields
operator|.
name|isDisplayableField
argument_list|(
name|key
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|written
operator|.
name|contains
argument_list|(
name|key
argument_list|)
operator|&&
name|writeIt
condition|)
block|{
name|remainingFields
operator|.
name|add
argument_list|(
name|key
argument_list|)
expr_stmt|;
block|}
block|}
for|for
control|(
name|String
name|field
range|:
name|remainingFields
control|)
block|{
name|hasWritten
operator|=
name|hasWritten
operator||
name|writeField
argument_list|(
name|entry
argument_list|,
name|out
argument_list|,
name|field
argument_list|,
name|hasWritten
argument_list|)
expr_stmt|;
block|}
comment|// Finally, end the entry.
name|out
operator|.
name|write
argument_list|(
operator|(
name|hasWritten
condition|?
name|Globals
operator|.
name|NEWLINE
else|:
literal|""
operator|)
operator|+
literal|'}'
operator|+
name|Globals
operator|.
name|NEWLINE
argument_list|)
expr_stmt|;
block|}
DECL|method|writeUserDefinedOrder (BibtexEntry entry, Writer out)
specifier|private
name|void
name|writeUserDefinedOrder
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|,
name|Writer
name|out
parameter_list|)
throws|throws
name|IOException
block|{
comment|// Write header with type and bibtex-key.
name|out
operator|.
name|write
argument_list|(
literal|'@'
operator|+
name|entry
operator|.
name|getType
argument_list|()
operator|.
name|getName
argument_list|()
operator|+
literal|'{'
argument_list|)
expr_stmt|;
name|writeKeyField
argument_list|(
name|entry
argument_list|,
name|out
argument_list|)
expr_stmt|;
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|written
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
name|written
operator|.
name|put
argument_list|(
name|BibtexEntry
operator|.
name|KEY_FIELD
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|boolean
name|hasWritten
init|=
literal|false
decl_stmt|;
comment|// Write user defined fields first.
name|String
index|[]
name|fields
init|=
name|Globals
operator|.
name|prefs
operator|.
name|getStringArray
argument_list|(
name|JabRefPreferences
operator|.
name|WRITEFIELD_USERDEFINEDORDER
argument_list|)
decl_stmt|;
if|if
condition|(
name|fields
operator|!=
literal|null
condition|)
block|{
comment|//do not sort, write as it is.
for|for
control|(
name|String
name|value
range|:
name|fields
control|)
block|{
if|if
condition|(
operator|!
name|written
operator|.
name|containsKey
argument_list|(
name|value
argument_list|)
condition|)
block|{
comment|// If field appears both in req. and opt. don't repeat.
name|hasWritten
operator|=
name|hasWritten
operator||
name|writeField
argument_list|(
name|entry
argument_list|,
name|out
argument_list|,
name|value
argument_list|,
name|hasWritten
argument_list|)
expr_stmt|;
name|written
operator|.
name|put
argument_list|(
name|value
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|// Then write remaining fields in alphabetic order.
comment|//STA get remaining fields
name|TreeSet
argument_list|<
name|String
argument_list|>
name|remainingFields
init|=
operator|new
name|TreeSet
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|key
range|:
name|entry
operator|.
name|getFieldNames
argument_list|()
control|)
block|{
comment|//iterate through all fields
name|boolean
name|writeIt
init|=
name|write
condition|?
name|BibtexFields
operator|.
name|isWriteableField
argument_list|(
name|key
argument_list|)
else|:
name|BibtexFields
operator|.
name|isDisplayableField
argument_list|(
name|key
argument_list|)
decl_stmt|;
comment|//find the ones has not been written.
if|if
condition|(
operator|!
name|written
operator|.
name|containsKey
argument_list|(
name|key
argument_list|)
operator|&&
name|writeIt
condition|)
block|{
name|remainingFields
operator|.
name|add
argument_list|(
name|key
argument_list|)
expr_stmt|;
block|}
block|}
comment|//END get remaining fields
for|for
control|(
name|String
name|field
range|:
name|remainingFields
control|)
block|{
name|hasWritten
operator|=
name|hasWritten
operator||
name|writeField
argument_list|(
name|entry
argument_list|,
name|out
argument_list|,
name|field
argument_list|,
name|hasWritten
argument_list|)
expr_stmt|;
block|}
comment|// Finally, end the entry.
name|out
operator|.
name|write
argument_list|(
operator|(
name|hasWritten
condition|?
name|Globals
operator|.
name|NEWLINE
else|:
literal|""
operator|)
operator|+
literal|'}'
operator|+
name|Globals
operator|.
name|NEWLINE
argument_list|)
expr_stmt|;
block|}
DECL|method|writeKeyField (BibtexEntry entry, Writer out)
specifier|private
name|void
name|writeKeyField
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|,
name|Writer
name|out
parameter_list|)
throws|throws
name|IOException
block|{
name|String
name|keyField
init|=
name|StringUtil
operator|.
name|shaveString
argument_list|(
name|entry
operator|.
name|getCiteKey
argument_list|()
argument_list|)
decl_stmt|;
name|out
operator|.
name|write
argument_list|(
operator|(
name|keyField
operator|==
literal|null
condition|?
literal|""
else|:
name|keyField
operator|)
operator|+
literal|','
operator|+
name|Globals
operator|.
name|NEWLINE
argument_list|)
expr_stmt|;
block|}
comment|/**      * Write a single field, if it has any content.      *      * @param entry             the entry to write      * @param out               the target of the write      * @param name              The field name      * @param prependWhiteSpace Indicates whether this is the first field written for      *                          this entry - if not, start by writing a comma and newline   @return true if this field was written, false if it was skipped because      *                          it was not set      * @throws IOException In case of an IO error      */
DECL|method|writeField (BibtexEntry entry, Writer out, String name, boolean prependWhiteSpace)
specifier|private
name|boolean
name|writeField
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|,
name|Writer
name|out
parameter_list|,
name|String
name|name
parameter_list|,
name|boolean
name|prependWhiteSpace
parameter_list|)
throws|throws
name|IOException
block|{
name|String
name|field
init|=
name|entry
operator|.
name|getField
argument_list|(
name|name
argument_list|)
decl_stmt|;
comment|// only write field if is is not empty or if empty fields should be included
comment|// the first condition mirrors mirror behavior of com.jgoodies.common.base.Strings.isNotBlank(str)
if|if
condition|(
operator|!
name|Strings
operator|.
name|nullToEmpty
argument_list|(
name|field
argument_list|)
operator|.
name|trim
argument_list|()
operator|.
name|isEmpty
argument_list|()
operator|||
name|includeEmptyFields
condition|)
block|{
if|if
condition|(
name|prependWhiteSpace
condition|)
block|{
name|out
operator|.
name|write
argument_list|(
literal|','
operator|+
name|Globals
operator|.
name|NEWLINE
argument_list|)
expr_stmt|;
block|}
name|out
operator|.
name|write
argument_list|(
literal|"  "
operator|+
name|getFieldDisplayName
argument_list|(
name|name
argument_list|)
operator|+
literal|" = "
argument_list|)
expr_stmt|;
try|try
block|{
name|out
operator|.
name|write
argument_list|(
name|fieldFormatter
operator|.
name|format
argument_list|(
name|field
argument_list|,
name|name
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{
throw|throw
operator|new
name|IOException
argument_list|(
literal|"Error in field '"
operator|+
name|name
operator|+
literal|"': "
operator|+
name|ex
operator|.
name|getMessage
argument_list|()
argument_list|)
throw|;
block|}
return|return
literal|true
return|;
block|}
else|else
block|{
return|return
literal|false
return|;
block|}
block|}
comment|/**      * Get display version of a entry field.      *<p>      * BibTeX is case-insensitive therefore there is no difference between:      * howpublished, HOWPUBLISHED, HowPublished, etc. Since the camel case      * version is the most easy to read this should be the one written in the      * *.bib file. Since there is no way how do detect multi-word strings by      * default the first character will be made uppercase. In other characters      * case needs to be changed the {@link #tagDisplayNameMap} will be used.      *      * @param field The name of the field.      * @return The display version of the field name.      */
DECL|method|getFieldDisplayName (String field)
specifier|private
name|String
name|getFieldDisplayName
parameter_list|(
name|String
name|field
parameter_list|)
block|{
if|if
condition|(
name|field
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
comment|// hard coded "UNKNOWN" is assigned to a field without any name
name|field
operator|=
literal|"UNKNOWN"
expr_stmt|;
block|}
name|String
name|suffix
init|=
literal|""
decl_stmt|;
if|if
condition|(
name|writeFieldAddSpaces
condition|)
block|{
for|for
control|(
name|int
name|i
init|=
name|BibtexEntryWriter
operator|.
name|maxFieldLength
operator|-
name|field
operator|.
name|length
argument_list|()
init|;
name|i
operator|>
literal|0
condition|;
name|i
operator|--
control|)
block|{
name|suffix
operator|+=
literal|" "
expr_stmt|;
block|}
block|}
name|String
name|result
decl_stmt|;
if|if
condition|(
name|writeFieldCameCaseName
condition|)
block|{
if|if
condition|(
name|BibtexEntryWriter
operator|.
name|tagDisplayNameMap
operator|.
name|containsKey
argument_list|(
name|field
operator|.
name|toLowerCase
argument_list|()
argument_list|)
condition|)
block|{
name|result
operator|=
name|BibtexEntryWriter
operator|.
name|tagDisplayNameMap
operator|.
name|get
argument_list|(
name|field
operator|.
name|toLowerCase
argument_list|()
argument_list|)
operator|+
name|suffix
expr_stmt|;
block|}
else|else
block|{
name|result
operator|=
operator|(
name|field
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
operator|+
literal|""
operator|)
operator|.
name|toUpperCase
argument_list|()
operator|+
name|field
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
operator|+
name|suffix
expr_stmt|;
block|}
block|}
else|else
block|{
name|result
operator|=
name|field
operator|+
name|suffix
expr_stmt|;
block|}
return|return
name|result
return|;
block|}
block|}
end_class

end_unit

