begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
package|;
end_package

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
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Objects
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Optional
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
name|BibDatabaseContext
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
name|FieldChange
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
name|EntryUtil
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
name|FieldName
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
name|FileField
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
name|ParsedFileField
import|;
end_import

begin_class
DECL|class|TypedBibEntry
specifier|public
class|class
name|TypedBibEntry
block|{
DECL|field|entry
specifier|private
specifier|final
name|BibEntry
name|entry
decl_stmt|;
DECL|field|database
specifier|private
specifier|final
name|Optional
argument_list|<
name|BibDatabase
argument_list|>
name|database
decl_stmt|;
DECL|field|mode
specifier|private
specifier|final
name|BibDatabaseMode
name|mode
decl_stmt|;
DECL|method|TypedBibEntry (BibEntry entry, BibDatabaseMode mode)
specifier|public
name|TypedBibEntry
parameter_list|(
name|BibEntry
name|entry
parameter_list|,
name|BibDatabaseMode
name|mode
parameter_list|)
block|{
name|this
argument_list|(
name|entry
argument_list|,
name|Optional
operator|.
name|empty
argument_list|()
argument_list|,
name|mode
argument_list|)
expr_stmt|;
block|}
DECL|method|TypedBibEntry (BibEntry entry, Optional<BibDatabase> database, BibDatabaseMode mode)
specifier|private
name|TypedBibEntry
parameter_list|(
name|BibEntry
name|entry
parameter_list|,
name|Optional
argument_list|<
name|BibDatabase
argument_list|>
name|database
parameter_list|,
name|BibDatabaseMode
name|mode
parameter_list|)
block|{
name|this
operator|.
name|entry
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|this
operator|.
name|database
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|database
argument_list|)
expr_stmt|;
name|this
operator|.
name|mode
operator|=
name|mode
expr_stmt|;
block|}
DECL|method|TypedBibEntry (BibEntry entry, BibDatabaseContext databaseContext)
specifier|public
name|TypedBibEntry
parameter_list|(
name|BibEntry
name|entry
parameter_list|,
name|BibDatabaseContext
name|databaseContext
parameter_list|)
block|{
name|this
argument_list|(
name|entry
argument_list|,
name|Optional
operator|.
name|of
argument_list|(
name|databaseContext
operator|.
name|getDatabase
argument_list|()
argument_list|)
argument_list|,
name|databaseContext
operator|.
name|getMode
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * Returns true if this entry contains the fields it needs to be      * complete.      */
DECL|method|hasAllRequiredFields ()
specifier|public
name|boolean
name|hasAllRequiredFields
parameter_list|()
block|{
name|Optional
argument_list|<
name|EntryType
argument_list|>
name|type
init|=
name|EntryTypes
operator|.
name|getType
argument_list|(
name|entry
operator|.
name|getType
argument_list|()
argument_list|,
name|this
operator|.
name|mode
argument_list|)
decl_stmt|;
if|if
condition|(
name|type
operator|.
name|isPresent
argument_list|()
condition|)
block|{
return|return
name|entry
operator|.
name|allFieldsPresent
argument_list|(
name|type
operator|.
name|get
argument_list|()
operator|.
name|getRequiredFields
argument_list|()
argument_list|,
name|database
operator|.
name|orElse
argument_list|(
literal|null
argument_list|)
argument_list|)
return|;
block|}
else|else
block|{
return|return
literal|true
return|;
block|}
block|}
comment|/**      * Gets the display name for the type of the entry.      */
DECL|method|getTypeForDisplay ()
specifier|public
name|String
name|getTypeForDisplay
parameter_list|()
block|{
name|Optional
argument_list|<
name|EntryType
argument_list|>
name|entryType
init|=
name|EntryTypes
operator|.
name|getType
argument_list|(
name|entry
operator|.
name|getType
argument_list|()
argument_list|,
name|mode
argument_list|)
decl_stmt|;
if|if
condition|(
name|entryType
operator|.
name|isPresent
argument_list|()
condition|)
block|{
return|return
name|entryType
operator|.
name|get
argument_list|()
operator|.
name|getName
argument_list|()
return|;
block|}
else|else
block|{
return|return
name|EntryUtil
operator|.
name|capitalizeFirst
argument_list|(
name|entry
operator|.
name|getType
argument_list|()
argument_list|)
return|;
block|}
block|}
comment|/**      * Gets a list of linked files.      *      * @return the list of linked files, is never null but can be empty      */
DECL|method|getFiles ()
specifier|public
name|List
argument_list|<
name|ParsedFileField
argument_list|>
name|getFiles
parameter_list|()
block|{
comment|//Extract the path
name|Optional
argument_list|<
name|String
argument_list|>
name|oldValue
init|=
name|entry
operator|.
name|getFieldOptional
argument_list|(
name|FieldName
operator|.
name|FILE
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|oldValue
operator|.
name|isPresent
argument_list|()
condition|)
block|{
return|return
operator|new
name|ArrayList
argument_list|<>
argument_list|()
return|;
block|}
return|return
name|FileField
operator|.
name|parse
argument_list|(
name|oldValue
operator|.
name|get
argument_list|()
argument_list|)
return|;
block|}
DECL|method|setFiles (List<ParsedFileField> files)
specifier|public
name|Optional
argument_list|<
name|FieldChange
argument_list|>
name|setFiles
parameter_list|(
name|List
argument_list|<
name|ParsedFileField
argument_list|>
name|files
parameter_list|)
block|{
name|Optional
argument_list|<
name|String
argument_list|>
name|oldValue
init|=
name|entry
operator|.
name|getFieldOptional
argument_list|(
name|FieldName
operator|.
name|FILE
argument_list|)
decl_stmt|;
name|String
name|newValue
init|=
name|FileField
operator|.
name|getStringRepresentation
argument_list|(
name|files
argument_list|)
decl_stmt|;
if|if
condition|(
name|oldValue
operator|.
name|isPresent
argument_list|()
operator|&&
name|oldValue
operator|.
name|get
argument_list|()
operator|.
name|equals
argument_list|(
name|newValue
argument_list|)
condition|)
block|{
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
name|entry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|FILE
argument_list|,
name|newValue
argument_list|)
expr_stmt|;
return|return
name|Optional
operator|.
name|of
argument_list|(
operator|new
name|FieldChange
argument_list|(
name|entry
argument_list|,
name|FieldName
operator|.
name|FILE
argument_list|,
name|oldValue
operator|.
name|orElse
argument_list|(
literal|""
argument_list|)
argument_list|,
name|newValue
argument_list|)
argument_list|)
return|;
block|}
block|}
end_class

end_unit

