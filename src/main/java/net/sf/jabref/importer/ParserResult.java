begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
end_comment

begin_package
DECL|package|net.sf.jabref.importer
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|importer
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|File
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
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
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
name|BibDatabases
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
name|MetaData
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
DECL|class|ParserResult
specifier|public
class|class
name|ParserResult
block|{
DECL|field|INVALID_FORMAT
specifier|public
specifier|static
specifier|final
name|ParserResult
name|INVALID_FORMAT
init|=
operator|new
name|ParserResult
argument_list|(
literal|null
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|)
decl_stmt|;
DECL|field|FILE_LOCKED
specifier|public
specifier|static
specifier|final
name|ParserResult
name|FILE_LOCKED
init|=
operator|new
name|ParserResult
argument_list|(
literal|null
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|)
decl_stmt|;
DECL|field|base
specifier|private
specifier|final
name|BibDatabase
name|base
decl_stmt|;
DECL|field|metaData
specifier|private
name|MetaData
name|metaData
decl_stmt|;
DECL|field|entryTypes
specifier|private
specifier|final
name|HashMap
argument_list|<
name|String
argument_list|,
name|EntryType
argument_list|>
name|entryTypes
decl_stmt|;
DECL|field|file
specifier|private
name|File
name|file
decl_stmt|;
DECL|field|warnings
specifier|private
specifier|final
name|ArrayList
argument_list|<
name|String
argument_list|>
name|warnings
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|duplicateKeys
specifier|private
specifier|final
name|ArrayList
argument_list|<
name|String
argument_list|>
name|duplicateKeys
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|errorMessage
specifier|private
name|String
name|errorMessage
decl_stmt|;
comment|// Which encoding was used?
DECL|field|encoding
specifier|private
name|Charset
name|encoding
decl_stmt|;
DECL|field|postponedAutosaveFound
specifier|private
name|boolean
name|postponedAutosaveFound
decl_stmt|;
DECL|field|invalid
specifier|private
name|boolean
name|invalid
decl_stmt|;
DECL|field|toOpenTab
specifier|private
name|boolean
name|toOpenTab
decl_stmt|;
DECL|method|ParserResult (Collection<BibEntry> entries)
specifier|public
name|ParserResult
parameter_list|(
name|Collection
argument_list|<
name|BibEntry
argument_list|>
name|entries
parameter_list|)
block|{
name|this
argument_list|(
name|BibDatabases
operator|.
name|createDatabase
argument_list|(
name|BibDatabases
operator|.
name|purgeEmptyEntries
argument_list|(
name|entries
argument_list|)
argument_list|)
argument_list|,
literal|null
argument_list|,
operator|new
name|HashMap
argument_list|<>
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|ParserResult (BibDatabase base, MetaData metaData, HashMap<String, EntryType> entryTypes)
specifier|public
name|ParserResult
parameter_list|(
name|BibDatabase
name|base
parameter_list|,
name|MetaData
name|metaData
parameter_list|,
name|HashMap
argument_list|<
name|String
argument_list|,
name|EntryType
argument_list|>
name|entryTypes
parameter_list|)
block|{
name|this
operator|.
name|base
operator|=
name|base
expr_stmt|;
name|this
operator|.
name|metaData
operator|=
name|metaData
expr_stmt|;
name|this
operator|.
name|entryTypes
operator|=
name|entryTypes
expr_stmt|;
block|}
comment|/**      * Check if this base is marked to be added to the currently open tab. Default is false.      *      * @return      */
DECL|method|toOpenTab ()
specifier|public
name|boolean
name|toOpenTab
parameter_list|()
block|{
return|return
name|toOpenTab
return|;
block|}
DECL|method|setToOpenTab (boolean toOpenTab)
specifier|public
name|void
name|setToOpenTab
parameter_list|(
name|boolean
name|toOpenTab
parameter_list|)
block|{
name|this
operator|.
name|toOpenTab
operator|=
name|toOpenTab
expr_stmt|;
block|}
DECL|method|getDatabase ()
specifier|public
name|BibDatabase
name|getDatabase
parameter_list|()
block|{
return|return
name|base
return|;
block|}
DECL|method|getMetaData ()
specifier|public
name|MetaData
name|getMetaData
parameter_list|()
block|{
return|return
name|metaData
return|;
block|}
DECL|method|setMetaData (MetaData md)
specifier|public
name|void
name|setMetaData
parameter_list|(
name|MetaData
name|md
parameter_list|)
block|{
name|this
operator|.
name|metaData
operator|=
name|md
expr_stmt|;
block|}
DECL|method|getEntryTypes ()
specifier|public
name|HashMap
argument_list|<
name|String
argument_list|,
name|EntryType
argument_list|>
name|getEntryTypes
parameter_list|()
block|{
return|return
name|entryTypes
return|;
block|}
DECL|method|getFile ()
specifier|public
name|File
name|getFile
parameter_list|()
block|{
return|return
name|file
return|;
block|}
DECL|method|setFile (File f)
specifier|public
name|void
name|setFile
parameter_list|(
name|File
name|f
parameter_list|)
block|{
name|file
operator|=
name|f
expr_stmt|;
block|}
comment|/**      * Sets the variable indicating which encoding was used during parsing.      *      * @param enc the encoding.      */
DECL|method|setEncoding (Charset enc)
specifier|public
name|void
name|setEncoding
parameter_list|(
name|Charset
name|enc
parameter_list|)
block|{
name|encoding
operator|=
name|enc
expr_stmt|;
block|}
comment|/**      * Returns the encoding used during parsing, or null if not specified (indicates that      * prefs.get(JabRefPreferences.DEFAULT_ENCODING) was used).      */
DECL|method|getEncoding ()
specifier|public
name|Charset
name|getEncoding
parameter_list|()
block|{
return|return
name|encoding
return|;
block|}
comment|/**      * Add a parser warning.      *      * @param s String Warning text. Must be pretranslated. Only added if there isn't already a dupe.      */
DECL|method|addWarning (String s)
specifier|public
name|void
name|addWarning
parameter_list|(
name|String
name|s
parameter_list|)
block|{
if|if
condition|(
operator|!
name|warnings
operator|.
name|contains
argument_list|(
name|s
argument_list|)
condition|)
block|{
name|warnings
operator|.
name|add
argument_list|(
name|s
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|hasWarnings ()
specifier|public
name|boolean
name|hasWarnings
parameter_list|()
block|{
return|return
operator|!
name|warnings
operator|.
name|isEmpty
argument_list|()
return|;
block|}
DECL|method|warnings ()
specifier|public
name|String
index|[]
name|warnings
parameter_list|()
block|{
name|String
index|[]
name|s
init|=
operator|new
name|String
index|[
name|warnings
operator|.
name|size
argument_list|()
index|]
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
name|warnings
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|s
index|[
name|i
index|]
operator|=
name|warnings
operator|.
name|get
argument_list|(
name|i
argument_list|)
expr_stmt|;
block|}
return|return
name|s
return|;
block|}
comment|/**      * Add a key to the list of duplicated BibTeX keys found in the database.      *      * @param key The duplicated key      */
DECL|method|addDuplicateKey (String key)
specifier|public
name|void
name|addDuplicateKey
parameter_list|(
name|String
name|key
parameter_list|)
block|{
if|if
condition|(
operator|!
name|duplicateKeys
operator|.
name|contains
argument_list|(
name|key
argument_list|)
condition|)
block|{
name|duplicateKeys
operator|.
name|add
argument_list|(
name|key
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Query whether any duplicated BibTeX keys have been found in the database.      *      * @return true if there is at least one duplicate key.      */
DECL|method|hasDuplicateKeys ()
specifier|public
name|boolean
name|hasDuplicateKeys
parameter_list|()
block|{
return|return
operator|!
name|duplicateKeys
operator|.
name|isEmpty
argument_list|()
return|;
block|}
comment|/**      * Get all duplicated keys found in the database.      *      * @return An array containing the duplicated keys.      */
DECL|method|getDuplicateKeys ()
specifier|public
name|String
index|[]
name|getDuplicateKeys
parameter_list|()
block|{
return|return
name|duplicateKeys
operator|.
name|toArray
argument_list|(
operator|new
name|String
index|[
name|duplicateKeys
operator|.
name|size
argument_list|()
index|]
argument_list|)
return|;
block|}
DECL|method|isPostponedAutosaveFound ()
specifier|public
name|boolean
name|isPostponedAutosaveFound
parameter_list|()
block|{
return|return
name|postponedAutosaveFound
return|;
block|}
DECL|method|setPostponedAutosaveFound (boolean postponedAutosaveFound)
specifier|public
name|void
name|setPostponedAutosaveFound
parameter_list|(
name|boolean
name|postponedAutosaveFound
parameter_list|)
block|{
name|this
operator|.
name|postponedAutosaveFound
operator|=
name|postponedAutosaveFound
expr_stmt|;
block|}
DECL|method|isInvalid ()
specifier|public
name|boolean
name|isInvalid
parameter_list|()
block|{
return|return
name|invalid
return|;
block|}
DECL|method|setInvalid (boolean invalid)
specifier|public
name|void
name|setInvalid
parameter_list|(
name|boolean
name|invalid
parameter_list|)
block|{
name|this
operator|.
name|invalid
operator|=
name|invalid
expr_stmt|;
block|}
DECL|method|getErrorMessage ()
specifier|public
name|String
name|getErrorMessage
parameter_list|()
block|{
return|return
name|errorMessage
return|;
block|}
DECL|method|setErrorMessage (String errorMessage)
specifier|public
name|void
name|setErrorMessage
parameter_list|(
name|String
name|errorMessage
parameter_list|)
block|{
name|this
operator|.
name|errorMessage
operator|=
name|errorMessage
expr_stmt|;
block|}
block|}
end_class

end_unit

