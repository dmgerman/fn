begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.exporter
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|exporter
package|;
end_package

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
name|nio
operator|.
name|charset
operator|.
name|Charset
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|bibtex
operator|.
name|BibEntryWriter
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|bibtex
operator|.
name|InvalidFieldValueException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|bibtex
operator|.
name|LatexFieldFormatter
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|util
operator|.
name|OS
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|database
operator|.
name|BibDatabaseContext
import|;
end_import

begin_import
import|import
name|org
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
name|org
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
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|BibEntryType
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|BibEntryTypesManager
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|BibtexString
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|field
operator|.
name|InternalField
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|metadata
operator|.
name|MetaData
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|strings
operator|.
name|StringUtil
import|;
end_import

begin_class
DECL|class|BibtexDatabaseWriter
specifier|public
class|class
name|BibtexDatabaseWriter
extends|extends
name|BibDatabaseWriter
block|{
DECL|field|DATABASE_ID_PREFIX
specifier|public
specifier|static
specifier|final
name|String
name|DATABASE_ID_PREFIX
init|=
literal|"DBID:"
decl_stmt|;
DECL|field|STRING_PREFIX
specifier|private
specifier|static
specifier|final
name|String
name|STRING_PREFIX
init|=
literal|"@String"
decl_stmt|;
DECL|field|COMMENT_PREFIX
specifier|private
specifier|static
specifier|final
name|String
name|COMMENT_PREFIX
init|=
literal|"@Comment"
decl_stmt|;
DECL|field|PREAMBLE_PREFIX
specifier|private
specifier|static
specifier|final
name|String
name|PREAMBLE_PREFIX
init|=
literal|"@Preamble"
decl_stmt|;
DECL|method|BibtexDatabaseWriter (Writer writer, SavePreferences preferences, BibEntryTypesManager entryTypesManager)
specifier|public
name|BibtexDatabaseWriter
parameter_list|(
name|Writer
name|writer
parameter_list|,
name|SavePreferences
name|preferences
parameter_list|,
name|BibEntryTypesManager
name|entryTypesManager
parameter_list|)
block|{
name|super
argument_list|(
name|writer
argument_list|,
name|preferences
argument_list|,
name|entryTypesManager
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|writeEpilogue (String epilogue)
specifier|protected
name|void
name|writeEpilogue
parameter_list|(
name|String
name|epilogue
parameter_list|)
throws|throws
name|IOException
block|{
if|if
condition|(
operator|!
name|StringUtil
operator|.
name|isNullOrEmpty
argument_list|(
name|epilogue
argument_list|)
condition|)
block|{
name|writer
operator|.
name|write
argument_list|(
name|OS
operator|.
name|NEWLINE
argument_list|)
expr_stmt|;
name|writer
operator|.
name|write
argument_list|(
name|epilogue
argument_list|)
expr_stmt|;
name|writer
operator|.
name|write
argument_list|(
name|OS
operator|.
name|NEWLINE
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
DECL|method|writeMetaDataItem (Map.Entry<String, String> metaItem)
specifier|protected
name|void
name|writeMetaDataItem
parameter_list|(
name|Map
operator|.
name|Entry
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|metaItem
parameter_list|)
throws|throws
name|IOException
block|{
name|writer
operator|.
name|write
argument_list|(
name|OS
operator|.
name|NEWLINE
argument_list|)
expr_stmt|;
name|writer
operator|.
name|write
argument_list|(
name|COMMENT_PREFIX
operator|+
literal|"{"
argument_list|)
expr_stmt|;
name|writer
operator|.
name|write
argument_list|(
name|MetaData
operator|.
name|META_FLAG
argument_list|)
expr_stmt|;
name|writer
operator|.
name|write
argument_list|(
name|metaItem
operator|.
name|getKey
argument_list|()
argument_list|)
expr_stmt|;
name|writer
operator|.
name|write
argument_list|(
literal|":"
argument_list|)
expr_stmt|;
name|writer
operator|.
name|write
argument_list|(
name|metaItem
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
name|writer
operator|.
name|write
argument_list|(
literal|"}"
argument_list|)
expr_stmt|;
name|writer
operator|.
name|write
argument_list|(
name|OS
operator|.
name|NEWLINE
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|writePreamble (String preamble)
specifier|protected
name|void
name|writePreamble
parameter_list|(
name|String
name|preamble
parameter_list|)
throws|throws
name|IOException
block|{
if|if
condition|(
operator|!
name|StringUtil
operator|.
name|isNullOrEmpty
argument_list|(
name|preamble
argument_list|)
condition|)
block|{
name|writer
operator|.
name|write
argument_list|(
name|OS
operator|.
name|NEWLINE
argument_list|)
expr_stmt|;
name|writer
operator|.
name|write
argument_list|(
name|PREAMBLE_PREFIX
operator|+
literal|"{"
argument_list|)
expr_stmt|;
name|writer
operator|.
name|write
argument_list|(
name|preamble
argument_list|)
expr_stmt|;
name|writer
operator|.
name|write
argument_list|(
literal|'}'
operator|+
name|OS
operator|.
name|NEWLINE
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
DECL|method|writeString (BibtexString bibtexString, boolean isFirstString, int maxKeyLength)
specifier|protected
name|void
name|writeString
parameter_list|(
name|BibtexString
name|bibtexString
parameter_list|,
name|boolean
name|isFirstString
parameter_list|,
name|int
name|maxKeyLength
parameter_list|)
throws|throws
name|IOException
block|{
comment|// If the string has not been modified, write it back as it was
if|if
condition|(
operator|!
name|preferences
operator|.
name|isReformatFile
argument_list|()
operator|&&
operator|!
name|bibtexString
operator|.
name|hasChanged
argument_list|()
condition|)
block|{
name|writer
operator|.
name|write
argument_list|(
name|bibtexString
operator|.
name|getParsedSerialization
argument_list|()
argument_list|)
expr_stmt|;
return|return;
block|}
comment|// Write user comments
name|String
name|userComments
init|=
name|bibtexString
operator|.
name|getUserComments
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|userComments
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|writer
operator|.
name|write
argument_list|(
name|userComments
operator|+
name|OS
operator|.
name|NEWLINE
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|isFirstString
condition|)
block|{
name|writer
operator|.
name|write
argument_list|(
name|OS
operator|.
name|NEWLINE
argument_list|)
expr_stmt|;
block|}
name|writer
operator|.
name|write
argument_list|(
name|STRING_PREFIX
operator|+
literal|"{"
operator|+
name|bibtexString
operator|.
name|getName
argument_list|()
operator|+
name|StringUtil
operator|.
name|repeatSpaces
argument_list|(
name|maxKeyLength
operator|-
name|bibtexString
operator|.
name|getName
argument_list|()
operator|.
name|length
argument_list|()
argument_list|)
operator|+
literal|" = "
argument_list|)
expr_stmt|;
if|if
condition|(
name|bibtexString
operator|.
name|getContent
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|writer
operator|.
name|write
argument_list|(
literal|"{}"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
try|try
block|{
name|String
name|formatted
init|=
operator|new
name|LatexFieldFormatter
argument_list|(
name|preferences
operator|.
name|getLatexFieldFormatterPreferences
argument_list|()
argument_list|)
operator|.
name|format
argument_list|(
name|bibtexString
operator|.
name|getContent
argument_list|()
argument_list|,
name|InternalField
operator|.
name|BIBTEX_STRING
argument_list|)
decl_stmt|;
name|writer
operator|.
name|write
argument_list|(
name|formatted
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|InvalidFieldValueException
name|ex
parameter_list|)
block|{
throw|throw
operator|new
name|IOException
argument_list|(
name|ex
argument_list|)
throw|;
block|}
block|}
name|writer
operator|.
name|write
argument_list|(
literal|"}"
operator|+
name|OS
operator|.
name|NEWLINE
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|writeEntryTypeDefinition (BibEntryType customType)
specifier|protected
name|void
name|writeEntryTypeDefinition
parameter_list|(
name|BibEntryType
name|customType
parameter_list|)
throws|throws
name|IOException
block|{
name|writer
operator|.
name|write
argument_list|(
name|OS
operator|.
name|NEWLINE
argument_list|)
expr_stmt|;
name|writer
operator|.
name|write
argument_list|(
name|COMMENT_PREFIX
operator|+
literal|"{"
argument_list|)
expr_stmt|;
name|writer
operator|.
name|write
argument_list|(
name|BibEntryTypesManager
operator|.
name|serialize
argument_list|(
name|customType
argument_list|)
argument_list|)
expr_stmt|;
name|writer
operator|.
name|write
argument_list|(
literal|"}"
argument_list|)
expr_stmt|;
name|writer
operator|.
name|write
argument_list|(
name|OS
operator|.
name|NEWLINE
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|writePrelogue (BibDatabaseContext bibDatabaseContext, Charset encoding)
specifier|protected
name|void
name|writePrelogue
parameter_list|(
name|BibDatabaseContext
name|bibDatabaseContext
parameter_list|,
name|Charset
name|encoding
parameter_list|)
throws|throws
name|IOException
block|{
if|if
condition|(
name|encoding
operator|==
literal|null
condition|)
block|{
return|return;
block|}
comment|// Writes the file encoding information.
name|writer
operator|.
name|write
argument_list|(
literal|"% "
argument_list|)
expr_stmt|;
name|writer
operator|.
name|write
argument_list|(
name|SavePreferences
operator|.
name|ENCODING_PREFIX
operator|+
name|encoding
argument_list|)
expr_stmt|;
name|writer
operator|.
name|write
argument_list|(
name|OS
operator|.
name|NEWLINE
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|writeDatabaseID (String sharedDatabaseID)
specifier|protected
name|void
name|writeDatabaseID
parameter_list|(
name|String
name|sharedDatabaseID
parameter_list|)
throws|throws
name|IOException
block|{
name|writer
operator|.
name|write
argument_list|(
literal|"% "
operator|+
name|DATABASE_ID_PREFIX
operator|+
literal|" "
operator|+
name|sharedDatabaseID
operator|+
name|OS
operator|.
name|NEWLINE
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|writeEntry (BibEntry entry, BibDatabaseMode mode)
specifier|protected
name|void
name|writeEntry
parameter_list|(
name|BibEntry
name|entry
parameter_list|,
name|BibDatabaseMode
name|mode
parameter_list|)
throws|throws
name|IOException
block|{
name|BibEntryWriter
name|bibtexEntryWriter
init|=
operator|new
name|BibEntryWriter
argument_list|(
operator|new
name|LatexFieldFormatter
argument_list|(
name|preferences
operator|.
name|getLatexFieldFormatterPreferences
argument_list|()
argument_list|)
argument_list|,
name|entryTypesManager
argument_list|)
decl_stmt|;
name|bibtexEntryWriter
operator|.
name|write
argument_list|(
name|entry
argument_list|,
name|writer
argument_list|,
name|mode
argument_list|,
name|preferences
operator|.
name|isReformatFile
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

