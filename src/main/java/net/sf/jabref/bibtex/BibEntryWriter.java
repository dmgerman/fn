begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
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
name|EntryTypes
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
name|BibDatabaseMode
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
name|java
operator|.
name|util
operator|.
name|function
operator|.
name|Predicate
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
name|logic
operator|.
name|TypedBibEntry
import|;
end_import

begin_class
DECL|class|BibEntryWriter
specifier|public
class|class
name|BibEntryWriter
block|{
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
DECL|method|BibEntryWriter (LatexFieldFormatter fieldFormatter, boolean write)
specifier|public
name|BibEntryWriter
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
DECL|method|write (BibEntry entry, Writer out, BibDatabaseMode bibDatabaseMode)
specifier|public
name|void
name|write
parameter_list|(
name|BibEntry
name|entry
parameter_list|,
name|Writer
name|out
parameter_list|,
name|BibDatabaseMode
name|bibDatabaseMode
parameter_list|)
throws|throws
name|IOException
block|{
name|write
argument_list|(
name|entry
argument_list|,
name|out
argument_list|,
name|bibDatabaseMode
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
comment|/**      * Writes the given BibEntry using the given writer      *      * @param entry The entry to write      * @param out The writer to use      * @param bibDatabaseMode The database mode (bibtex or biblatex)      * @param reformat Should the entry be in any case, even if no change occurred?      */
DECL|method|write (BibEntry entry, Writer out, BibDatabaseMode bibDatabaseMode, Boolean reformat)
specifier|public
name|void
name|write
parameter_list|(
name|BibEntry
name|entry
parameter_list|,
name|Writer
name|out
parameter_list|,
name|BibDatabaseMode
name|bibDatabaseMode
parameter_list|,
name|Boolean
name|reformat
parameter_list|)
throws|throws
name|IOException
block|{
comment|// if the entry has not been modified, write it as it was
if|if
condition|(
operator|!
name|reformat
operator|&&
operator|!
name|entry
operator|.
name|hasChanged
argument_list|()
condition|)
block|{
name|out
operator|.
name|write
argument_list|(
name|entry
operator|.
name|getParsedSerialization
argument_list|()
argument_list|)
expr_stmt|;
return|return;
block|}
name|out
operator|.
name|write
argument_list|(
name|Globals
operator|.
name|NEWLINE
argument_list|)
expr_stmt|;
name|writeRequiredFieldsFirstRemainingFieldsSecond
argument_list|(
name|entry
argument_list|,
name|out
argument_list|,
name|bibDatabaseMode
argument_list|)
expr_stmt|;
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
DECL|method|writeWithoutPrependedNewlines (BibEntry entry, Writer out, BibDatabaseMode bibDatabaseMode)
specifier|public
name|void
name|writeWithoutPrependedNewlines
parameter_list|(
name|BibEntry
name|entry
parameter_list|,
name|Writer
name|out
parameter_list|,
name|BibDatabaseMode
name|bibDatabaseMode
parameter_list|)
throws|throws
name|IOException
block|{
comment|// if the entry has not been modified, write it as it was
if|if
condition|(
operator|!
name|entry
operator|.
name|hasChanged
argument_list|()
condition|)
block|{
name|out
operator|.
name|write
argument_list|(
name|entry
operator|.
name|getParsedSerialization
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
return|return;
block|}
name|writeRequiredFieldsFirstRemainingFieldsSecond
argument_list|(
name|entry
argument_list|,
name|out
argument_list|,
name|bibDatabaseMode
argument_list|)
expr_stmt|;
block|}
comment|/**      * Write fields in the order of requiredFields, optionalFields and other fields, but does not sort the fields.      *      * @param entry      * @param out      * @throws IOException      */
DECL|method|writeRequiredFieldsFirstRemainingFieldsSecond (BibEntry entry, Writer out, BibDatabaseMode bibDatabaseMode)
specifier|private
name|void
name|writeRequiredFieldsFirstRemainingFieldsSecond
parameter_list|(
name|BibEntry
name|entry
parameter_list|,
name|Writer
name|out
parameter_list|,
name|BibDatabaseMode
name|bibDatabaseMode
parameter_list|)
throws|throws
name|IOException
block|{
comment|// Write header with type and bibtex-key.
name|TypedBibEntry
name|typedEntry
init|=
operator|new
name|TypedBibEntry
argument_list|(
name|entry
argument_list|,
name|Optional
operator|.
name|empty
argument_list|()
argument_list|,
name|bibDatabaseMode
argument_list|)
decl_stmt|;
name|out
operator|.
name|write
argument_list|(
literal|'@'
operator|+
name|typedEntry
operator|.
name|getTypeForDisplay
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
name|Set
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
name|BibEntry
operator|.
name|KEY_FIELD
argument_list|)
expr_stmt|;
name|int
name|indentation
init|=
name|getLengthOfLongestFieldName
argument_list|(
name|entry
argument_list|)
decl_stmt|;
name|EntryType
name|type
init|=
name|EntryTypes
operator|.
name|getTypeOrDefault
argument_list|(
name|entry
operator|.
name|getType
argument_list|()
argument_list|,
name|bibDatabaseMode
argument_list|)
decl_stmt|;
comment|// Write required fields first.
name|List
argument_list|<
name|String
argument_list|>
name|fields
init|=
name|type
operator|.
name|getRequiredFieldsFlat
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
name|writeField
argument_list|(
name|entry
argument_list|,
name|out
argument_list|,
name|value
argument_list|,
name|indentation
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
name|type
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
name|writeField
argument_list|(
name|entry
argument_list|,
name|out
argument_list|,
name|value
argument_list|,
name|indentation
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
name|Set
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
name|InternalBibtexFields
operator|.
name|isWriteableField
argument_list|(
name|key
argument_list|)
else|:
name|InternalBibtexFields
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
name|writeField
argument_list|(
name|entry
argument_list|,
name|out
argument_list|,
name|field
argument_list|,
name|indentation
argument_list|)
expr_stmt|;
block|}
comment|// Finally, end the entry.
name|out
operator|.
name|write
argument_list|(
literal|'}'
argument_list|)
expr_stmt|;
block|}
DECL|method|writeKeyField (BibEntry entry, Writer out)
specifier|private
name|void
name|writeKeyField
parameter_list|(
name|BibEntry
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
name|keyField
operator|+
literal|','
operator|+
name|Globals
operator|.
name|NEWLINE
argument_list|)
expr_stmt|;
block|}
comment|/**      * Write a single field, if it has any content.      *      * @param entry             the entry to write      * @param out               the target of the write      * @param name              The field name      * @throws IOException In case of an IO error      */
DECL|method|writeField (BibEntry entry, Writer out, String name, int indentation)
specifier|private
name|void
name|writeField
parameter_list|(
name|BibEntry
name|entry
parameter_list|,
name|Writer
name|out
parameter_list|,
name|String
name|name
parameter_list|,
name|int
name|indentation
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
condition|)
block|{
return|return;
block|}
else|else
block|{
name|out
operator|.
name|write
argument_list|(
literal|"  "
operator|+
name|getFieldDisplayName
argument_list|(
name|name
argument_list|,
name|indentation
argument_list|)
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
block|}
block|}
DECL|method|getLengthOfLongestFieldName (BibEntry entry)
specifier|private
name|int
name|getLengthOfLongestFieldName
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
name|Predicate
argument_list|<
name|String
argument_list|>
name|isNotBibtexKey
init|=
name|field
lambda|->
operator|!
literal|"bibtexkey"
operator|.
name|equals
argument_list|(
name|field
argument_list|)
decl_stmt|;
return|return
name|entry
operator|.
name|getFieldNames
argument_list|()
operator|.
name|stream
argument_list|()
operator|.
name|filter
argument_list|(
name|isNotBibtexKey
argument_list|)
operator|.
name|mapToInt
argument_list|(
name|field
lambda|->
name|field
operator|.
name|length
argument_list|()
argument_list|)
operator|.
name|max
argument_list|()
operator|.
name|orElse
argument_list|(
literal|0
argument_list|)
return|;
block|}
comment|/**      * Get display version of a entry field.      *<p>      * BibTeX is case-insensitive therefore there is no difference between:      * howpublished, HOWPUBLISHED, HowPublished, etc.      *<p>      * The was a long discussion about how JabRef should write the fields.      * See https://github.com/JabRef/jabref/issues/116      *<p>      * The team decided to do the biblatex way and use lower case for the field names.      *      * @param field The name of the field.      * @return The display version of the field name.      */
DECL|method|getFieldDisplayName (String field, int intendation)
specifier|private
name|String
name|getFieldDisplayName
parameter_list|(
name|String
name|field
parameter_list|,
name|int
name|intendation
parameter_list|)
block|{
name|String
name|actualField
init|=
name|field
decl_stmt|;
if|if
condition|(
name|actualField
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
comment|// hard coded "UNKNOWN" is assigned to a field without any name
name|actualField
operator|=
literal|"UNKNOWN"
expr_stmt|;
block|}
return|return
name|actualField
operator|.
name|toLowerCase
argument_list|()
operator|+
name|StringUtil
operator|.
name|repeatSpaces
argument_list|(
name|intendation
operator|-
name|actualField
operator|.
name|length
argument_list|()
argument_list|)
operator|+
literal|" = "
return|;
block|}
block|}
end_class

end_unit

