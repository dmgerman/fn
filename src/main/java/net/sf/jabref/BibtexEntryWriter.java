begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
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
name|export
operator|.
name|FieldFormatter
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
name|util
operator|.
name|StringUtil
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
block|}
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
name|BibtexEntryType
name|t
range|:
name|BibtexEntryType
operator|.
name|ALL_TYPES
operator|.
name|values
argument_list|()
control|)
block|{
if|if
condition|(
name|t
operator|.
name|getRequiredFields
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
name|t
operator|.
name|getRequiredFields
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
if|if
condition|(
name|t
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
name|t
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
name|FieldFormatter
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
DECL|field|writeFieldSortStype
specifier|private
specifier|final
name|int
name|writeFieldSortStype
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
DECL|method|BibtexEntryWriter (FieldFormatter fieldFormatter, boolean write)
specifier|public
name|BibtexEntryWriter
parameter_list|(
name|FieldFormatter
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
name|writeFieldSortStype
condition|)
block|{
case|case
literal|0
case|:
name|writeSorted
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
name|writeUnsorted
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
DECL|method|writeSorted (BibtexEntry entry, Writer out)
specifier|private
name|void
name|writeSorted
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
name|String
name|str
init|=
name|StringUtil
operator|.
name|shaveString
argument_list|(
name|entry
operator|.
name|getField
argument_list|(
name|BibtexFields
operator|.
name|KEY_FIELD
argument_list|)
argument_list|)
decl_stmt|;
name|out
operator|.
name|write
argument_list|(
operator|(
name|str
operator|==
literal|null
condition|?
literal|""
else|:
name|str
operator|)
operator|+
literal|','
operator|+
name|Globals
operator|.
name|NEWLINE
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
name|BibtexFields
operator|.
name|KEY_FIELD
argument_list|,
literal|null
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
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|written
operator|.
name|put
argument_list|(
literal|"title"
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|String
index|[]
name|s
init|=
name|entry
operator|.
name|getRequiredFields
argument_list|()
decl_stmt|;
if|if
condition|(
name|s
operator|!=
literal|null
condition|)
block|{
name|Arrays
operator|.
name|sort
argument_list|(
name|s
argument_list|)
expr_stmt|;
comment|// Sorting in alphabetic order.
for|for
control|(
name|String
name|value
range|:
name|s
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
argument_list|,
literal|false
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
comment|// Then optional fields.
name|s
operator|=
name|entry
operator|.
name|getOptionalFields
argument_list|()
expr_stmt|;
name|boolean
name|first
init|=
literal|true
decl_stmt|;
name|boolean
name|previous
decl_stmt|;
name|previous
operator|=
literal|false
expr_stmt|;
if|if
condition|(
name|s
operator|!=
literal|null
condition|)
block|{
name|Arrays
operator|.
name|sort
argument_list|(
name|s
argument_list|)
expr_stmt|;
comment|// Sorting in alphabetic order.
for|for
control|(
name|String
name|value
range|:
name|s
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
comment|//writeField(s[i], out, fieldFormatter);
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
argument_list|,
name|hasWritten
operator|&&
name|first
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
name|first
operator|=
literal|false
expr_stmt|;
name|previous
operator|=
literal|true
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
name|getAllFields
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
name|first
operator|=
name|previous
expr_stmt|;
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
argument_list|,
name|hasWritten
operator|&&
name|first
argument_list|)
expr_stmt|;
name|first
operator|=
literal|false
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
comment|/**      * old style ver<=2.9.2, write fields in the order of requiredFields, optionalFields and other fields, but does not sort the fields.      *      * @param entry      * @param out      * @throws IOException      */
DECL|method|writeUnsorted (BibtexEntry entry, Writer out)
specifier|private
name|void
name|writeUnsorted
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
name|String
name|str
init|=
name|StringUtil
operator|.
name|shaveString
argument_list|(
name|entry
operator|.
name|getField
argument_list|(
name|BibtexFields
operator|.
name|KEY_FIELD
argument_list|)
argument_list|)
decl_stmt|;
name|out
operator|.
name|write
argument_list|(
operator|(
name|str
operator|==
literal|null
condition|?
literal|""
else|:
name|str
operator|)
operator|+
literal|','
operator|+
name|Globals
operator|.
name|NEWLINE
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
name|BibtexFields
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
comment|// Write required fields first.
name|String
index|[]
name|s
init|=
name|entry
operator|.
name|getRequiredFields
argument_list|()
decl_stmt|;
if|if
condition|(
name|s
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|String
name|value
range|:
name|s
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
argument_list|,
literal|false
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
comment|// Then optional fields.
name|s
operator|=
name|entry
operator|.
name|getOptionalFields
argument_list|()
expr_stmt|;
if|if
condition|(
name|s
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|String
name|value
range|:
name|s
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
comment|//writeField(s[i], out, fieldFormatter);
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
argument_list|,
literal|false
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
name|getAllFields
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
argument_list|,
literal|false
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
name|String
name|str
init|=
name|StringUtil
operator|.
name|shaveString
argument_list|(
name|entry
operator|.
name|getField
argument_list|(
name|BibtexFields
operator|.
name|KEY_FIELD
argument_list|)
argument_list|)
decl_stmt|;
name|out
operator|.
name|write
argument_list|(
operator|(
name|str
operator|==
literal|null
condition|?
literal|""
else|:
name|str
operator|)
operator|+
literal|','
operator|+
name|Globals
operator|.
name|NEWLINE
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
name|BibtexFields
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
name|s
init|=
name|entry
operator|.
name|getUserDefinedFields
argument_list|()
decl_stmt|;
if|if
condition|(
name|s
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
name|s
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
argument_list|,
literal|false
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
name|boolean
name|first
decl_stmt|;
name|boolean
name|previous
decl_stmt|;
name|previous
operator|=
literal|false
expr_stmt|;
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
name|getAllFields
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
name|first
operator|=
name|previous
expr_stmt|;
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
argument_list|,
name|hasWritten
operator|&&
name|first
argument_list|)
expr_stmt|;
name|first
operator|=
literal|false
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
comment|/**      * Write a single field, if it has any content.      *      * @param entry      the entry to write      * @param out        the target of the write      * @param name       The field name      * @param isNotFirst Indicates whether this is the first field written for      *                   this entry - if not, start by writing a comma and newline   @return true if this field was written, false if it was skipped because      *                   it was not set      * @throws IOException In case of an IO error      */
DECL|method|writeField (BibtexEntry entry, Writer out, String name, boolean isNotFirst, boolean isNextGroup)
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
name|isNotFirst
parameter_list|,
name|boolean
name|isNextGroup
parameter_list|)
throws|throws
name|IOException
block|{
name|String
name|o
init|=
name|entry
operator|.
name|getField
argument_list|(
name|name
argument_list|)
decl_stmt|;
if|if
condition|(
name|o
operator|!=
literal|null
operator|||
name|includeEmptyFields
condition|)
block|{
if|if
condition|(
name|isNotFirst
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
if|if
condition|(
name|isNextGroup
condition|)
block|{
name|out
operator|.
name|write
argument_list|(
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
name|o
argument_list|,
name|name
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Throwable
name|ex
parameter_list|)
block|{
throw|throw
operator|new
name|IOException
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Error in field"
argument_list|)
operator|+
literal|" '"
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
comment|/**      * Get display version of a entry field.      *<p/>      * BibTeX is case-insensitive therefore there is no difference between:      * howpublished, HOWPUBLISHED, HowPublished, etc. Since the camel case      * version is the most easy to read this should be the one written in the      * *.bib file. Since there is no way how do detect multi-word strings by      * default the first character will be made uppercase. In other characters      * case needs to be changed the {@link #tagDisplayNameMap} will be used.      *      * @param field The name of the field.      * @return The display version of the field name.      */
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
name|res
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
name|res
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
name|res
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
name|res
operator|=
name|field
operator|+
name|suffix
expr_stmt|;
block|}
return|return
name|res
return|;
block|}
block|}
end_class

end_unit

