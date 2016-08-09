begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Copyright (C) 2003-2016 JabRef contributors.  * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.  *  * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *  * You should have received a copy of the GNU General Public License along  * with this program; if not, write to the Free Software Foundation, Inc.,  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
end_comment

begin_package
DECL|package|net.sf.jabref.logic.exporter
package|package
name|net
operator|.
name|sf
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
name|Arrays
import|;
end_import

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
name|Comparator
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
name|LinkedList
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
name|Map
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
name|java
operator|.
name|util
operator|.
name|TreeMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Matcher
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Pattern
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|stream
operator|.
name|Collectors
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
name|logic
operator|.
name|bibtex
operator|.
name|LatexFieldFormatterPreferences
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
name|bibtex
operator|.
name|comparator
operator|.
name|BibtexStringComparator
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
name|bibtex
operator|.
name|comparator
operator|.
name|CrossRefEntryComparator
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
name|bibtex
operator|.
name|comparator
operator|.
name|FieldComparator
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
name|bibtex
operator|.
name|comparator
operator|.
name|FieldComparatorStack
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
name|bibtex
operator|.
name|comparator
operator|.
name|IdComparator
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
name|config
operator|.
name|SaveOrderConfig
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
name|BibtexString
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
name|CustomEntryType
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
DECL|class|BibDatabaseWriter
specifier|public
specifier|abstract
class|class
name|BibDatabaseWriter
parameter_list|<
name|E
extends|extends
name|SaveSession
parameter_list|>
block|{
DECL|field|REFERENCE_PATTERN
specifier|private
specifier|static
specifier|final
name|Pattern
name|REFERENCE_PATTERN
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"(#[A-Za-z]+#)"
argument_list|)
decl_stmt|;
comment|// Used to detect string references in strings
DECL|field|saveSessionFactory
specifier|private
specifier|final
name|SaveSessionFactory
argument_list|<
name|E
argument_list|>
name|saveSessionFactory
decl_stmt|;
DECL|field|session
specifier|private
name|E
name|session
decl_stmt|;
DECL|method|BibDatabaseWriter (SaveSessionFactory<E> saveSessionFactory)
specifier|public
name|BibDatabaseWriter
parameter_list|(
name|SaveSessionFactory
argument_list|<
name|E
argument_list|>
name|saveSessionFactory
parameter_list|)
block|{
name|this
operator|.
name|saveSessionFactory
operator|=
name|saveSessionFactory
expr_stmt|;
block|}
DECL|interface|SaveSessionFactory
specifier|public
interface|interface
name|SaveSessionFactory
parameter_list|<
name|E
extends|extends
name|SaveSession
parameter_list|>
block|{
DECL|method|createSaveSession (Charset encoding, Boolean makeBackup)
name|E
name|createSaveSession
parameter_list|(
name|Charset
name|encoding
parameter_list|,
name|Boolean
name|makeBackup
parameter_list|)
throws|throws
name|SaveException
function_decl|;
block|}
DECL|method|applySaveActions (List<BibEntry> toChange, MetaData metaData)
specifier|private
specifier|static
name|List
argument_list|<
name|FieldChange
argument_list|>
name|applySaveActions
parameter_list|(
name|List
argument_list|<
name|BibEntry
argument_list|>
name|toChange
parameter_list|,
name|MetaData
name|metaData
parameter_list|)
block|{
name|List
argument_list|<
name|FieldChange
argument_list|>
name|changes
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|Optional
argument_list|<
name|FieldFormatterCleanups
argument_list|>
name|saveActions
init|=
name|metaData
operator|.
name|getSaveActions
argument_list|()
decl_stmt|;
name|saveActions
operator|.
name|ifPresent
argument_list|(
name|actions
lambda|->
block|{
comment|// save actions defined -> apply for every entry
for|for
control|(
name|BibEntry
name|entry
range|:
name|toChange
control|)
block|{
name|changes
operator|.
name|addAll
argument_list|(
name|actions
operator|.
name|applySaveActions
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
return|return
name|changes
return|;
block|}
DECL|method|applySaveActions (BibEntry entry, MetaData metaData)
specifier|public
specifier|static
name|List
argument_list|<
name|FieldChange
argument_list|>
name|applySaveActions
parameter_list|(
name|BibEntry
name|entry
parameter_list|,
name|MetaData
name|metaData
parameter_list|)
block|{
return|return
name|applySaveActions
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|entry
argument_list|)
argument_list|,
name|metaData
argument_list|)
return|;
block|}
DECL|method|getSaveComparators (SavePreferences preferences, MetaData metaData)
specifier|private
specifier|static
name|List
argument_list|<
name|Comparator
argument_list|<
name|BibEntry
argument_list|>
argument_list|>
name|getSaveComparators
parameter_list|(
name|SavePreferences
name|preferences
parameter_list|,
name|MetaData
name|metaData
parameter_list|)
block|{
name|List
argument_list|<
name|Comparator
argument_list|<
name|BibEntry
argument_list|>
argument_list|>
name|comparators
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|Optional
argument_list|<
name|SaveOrderConfig
argument_list|>
name|saveOrder
init|=
name|getSaveOrder
argument_list|(
name|preferences
argument_list|,
name|metaData
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|saveOrder
operator|.
name|isPresent
argument_list|()
condition|)
block|{
comment|// Take care, using CrossRefEntry-Comparator, that referred entries occur after referring
comment|// ones. Apart from crossref requirements, entries will be sorted based on their creation order,
comment|// utilizing the fact that IDs used for entries are increasing, sortable numbers.
name|comparators
operator|.
name|add
argument_list|(
operator|new
name|CrossRefEntryComparator
argument_list|()
argument_list|)
expr_stmt|;
name|comparators
operator|.
name|add
argument_list|(
operator|new
name|IdComparator
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|comparators
operator|.
name|add
argument_list|(
operator|new
name|CrossRefEntryComparator
argument_list|()
argument_list|)
expr_stmt|;
name|comparators
operator|.
name|add
argument_list|(
operator|new
name|FieldComparator
argument_list|(
name|saveOrder
operator|.
name|get
argument_list|()
operator|.
name|sortCriteria
index|[
literal|0
index|]
argument_list|)
argument_list|)
expr_stmt|;
name|comparators
operator|.
name|add
argument_list|(
operator|new
name|FieldComparator
argument_list|(
name|saveOrder
operator|.
name|get
argument_list|()
operator|.
name|sortCriteria
index|[
literal|1
index|]
argument_list|)
argument_list|)
expr_stmt|;
name|comparators
operator|.
name|add
argument_list|(
operator|new
name|FieldComparator
argument_list|(
name|saveOrder
operator|.
name|get
argument_list|()
operator|.
name|sortCriteria
index|[
literal|2
index|]
argument_list|)
argument_list|)
expr_stmt|;
name|comparators
operator|.
name|add
argument_list|(
operator|new
name|FieldComparator
argument_list|(
name|BibEntry
operator|.
name|KEY_FIELD
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|comparators
return|;
block|}
comment|/*      * We have begun to use getSortedEntries() for both database save operations      * and non-database save operations.  In a non-database save operation      * (such as the exportDatabase call), we do not wish to use the      * global preference of saving in standard order.     */
DECL|method|getSortedEntries (BibDatabaseContext bibDatabaseContext, List<BibEntry> entriesToSort, SavePreferences preferences)
specifier|public
specifier|static
name|List
argument_list|<
name|BibEntry
argument_list|>
name|getSortedEntries
parameter_list|(
name|BibDatabaseContext
name|bibDatabaseContext
parameter_list|,
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entriesToSort
parameter_list|,
name|SavePreferences
name|preferences
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|bibDatabaseContext
argument_list|)
expr_stmt|;
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|entriesToSort
argument_list|)
expr_stmt|;
comment|//if no meta data are present, simply return in original order
if|if
condition|(
name|bibDatabaseContext
operator|.
name|getMetaData
argument_list|()
operator|==
literal|null
condition|)
block|{
name|List
argument_list|<
name|BibEntry
argument_list|>
name|result
init|=
operator|new
name|LinkedList
argument_list|<>
argument_list|()
decl_stmt|;
name|result
operator|.
name|addAll
argument_list|(
name|entriesToSort
argument_list|)
expr_stmt|;
return|return
name|result
return|;
block|}
name|List
argument_list|<
name|Comparator
argument_list|<
name|BibEntry
argument_list|>
argument_list|>
name|comparators
init|=
name|BibDatabaseWriter
operator|.
name|getSaveComparators
argument_list|(
name|preferences
argument_list|,
name|bibDatabaseContext
operator|.
name|getMetaData
argument_list|()
argument_list|)
decl_stmt|;
name|FieldComparatorStack
argument_list|<
name|BibEntry
argument_list|>
name|comparatorStack
init|=
operator|new
name|FieldComparatorStack
argument_list|<>
argument_list|(
name|comparators
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|BibEntry
argument_list|>
name|sorted
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|sorted
operator|.
name|addAll
argument_list|(
name|entriesToSort
argument_list|)
expr_stmt|;
name|Collections
operator|.
name|sort
argument_list|(
name|sorted
argument_list|,
name|comparatorStack
argument_list|)
expr_stmt|;
return|return
name|sorted
return|;
block|}
DECL|method|getSaveOrder (SavePreferences preferences, MetaData metaData)
specifier|private
specifier|static
name|Optional
argument_list|<
name|SaveOrderConfig
argument_list|>
name|getSaveOrder
parameter_list|(
name|SavePreferences
name|preferences
parameter_list|,
name|MetaData
name|metaData
parameter_list|)
block|{
comment|/* three options:          * 1. original order          * 2. order specified in metaData          * 3. order specified in preferences          */
if|if
condition|(
name|preferences
operator|.
name|isSaveInOriginalOrder
argument_list|()
condition|)
block|{
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
if|if
condition|(
name|preferences
operator|.
name|getTakeMetadataSaveOrderInAccount
argument_list|()
condition|)
block|{
return|return
name|metaData
operator|.
name|getSaveOrderConfig
argument_list|()
return|;
block|}
return|return
name|Optional
operator|.
name|ofNullable
argument_list|(
name|preferences
operator|.
name|getSaveOrder
argument_list|()
argument_list|)
return|;
block|}
comment|/**      * Saves the complete database.      */
DECL|method|saveDatabase (BibDatabaseContext bibDatabaseContext, SavePreferences preferences)
specifier|public
name|E
name|saveDatabase
parameter_list|(
name|BibDatabaseContext
name|bibDatabaseContext
parameter_list|,
name|SavePreferences
name|preferences
parameter_list|)
throws|throws
name|SaveException
block|{
return|return
name|savePartOfDatabase
argument_list|(
name|bibDatabaseContext
argument_list|,
name|bibDatabaseContext
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
argument_list|,
name|preferences
argument_list|)
return|;
block|}
comment|/**      * Saves the database, including only the specified entries.      */
DECL|method|savePartOfDatabase (BibDatabaseContext bibDatabaseContext, List<BibEntry> entries, SavePreferences preferences)
specifier|public
name|E
name|savePartOfDatabase
parameter_list|(
name|BibDatabaseContext
name|bibDatabaseContext
parameter_list|,
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
parameter_list|,
name|SavePreferences
name|preferences
parameter_list|)
throws|throws
name|SaveException
block|{
name|session
operator|=
name|saveSessionFactory
operator|.
name|createSaveSession
argument_list|(
name|preferences
operator|.
name|getEncodingOrDefault
argument_list|()
argument_list|,
name|preferences
operator|.
name|getMakeBackup
argument_list|()
argument_list|)
expr_stmt|;
comment|// Map to collect entry type definitions that we must save along with entries using them.
name|Map
argument_list|<
name|String
argument_list|,
name|EntryType
argument_list|>
name|typesToWrite
init|=
operator|new
name|TreeMap
argument_list|<>
argument_list|()
decl_stmt|;
comment|// Some file formats write something at the start of the file (like the encoding)
if|if
condition|(
name|preferences
operator|.
name|getSaveType
argument_list|()
operator|!=
name|SavePreferences
operator|.
name|DatabaseSaveType
operator|.
name|PLAIN_BIBTEX
condition|)
block|{
name|writePrelogue
argument_list|(
name|bibDatabaseContext
argument_list|,
name|preferences
operator|.
name|getEncoding
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|// Write preamble if there is one.
name|writePreamble
argument_list|(
name|bibDatabaseContext
operator|.
name|getDatabase
argument_list|()
operator|.
name|getPreamble
argument_list|()
argument_list|)
expr_stmt|;
comment|// Write strings if there are any.
name|writeStrings
argument_list|(
name|bibDatabaseContext
operator|.
name|getDatabase
argument_list|()
argument_list|,
name|preferences
operator|.
name|isReformatFile
argument_list|()
argument_list|,
name|preferences
operator|.
name|getLatexFieldFormatterPreferences
argument_list|()
argument_list|)
expr_stmt|;
comment|// Write database entries.
name|List
argument_list|<
name|BibEntry
argument_list|>
name|sortedEntries
init|=
name|getSortedEntries
argument_list|(
name|bibDatabaseContext
argument_list|,
name|entries
argument_list|,
name|preferences
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|FieldChange
argument_list|>
name|saveActionChanges
init|=
name|applySaveActions
argument_list|(
name|sortedEntries
argument_list|,
name|bibDatabaseContext
operator|.
name|getMetaData
argument_list|()
argument_list|)
decl_stmt|;
name|session
operator|.
name|addFieldChanges
argument_list|(
name|saveActionChanges
argument_list|)
expr_stmt|;
for|for
control|(
name|BibEntry
name|entry
range|:
name|sortedEntries
control|)
block|{
comment|// Check if we must write the type definition for this
comment|// entry, as well. Our criterion is that all non-standard
comment|// types (*not* all customized standard types) must be written.
if|if
condition|(
operator|!
name|EntryTypes
operator|.
name|getStandardType
argument_list|(
name|entry
operator|.
name|getType
argument_list|()
argument_list|,
name|bibDatabaseContext
operator|.
name|getMode
argument_list|()
argument_list|)
operator|.
name|isPresent
argument_list|()
condition|)
block|{
comment|// If user-defined entry type, then add it
comment|// Otherwise (getType returns empty optional) it is a completely unknown entry type, so ignore it
name|EntryTypes
operator|.
name|getType
argument_list|(
name|entry
operator|.
name|getType
argument_list|()
argument_list|,
name|bibDatabaseContext
operator|.
name|getMode
argument_list|()
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|entryType
lambda|->
name|typesToWrite
operator|.
name|put
argument_list|(
name|entryType
operator|.
name|getName
argument_list|()
argument_list|,
name|entryType
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|writeEntry
argument_list|(
name|entry
argument_list|,
name|bibDatabaseContext
operator|.
name|getMode
argument_list|()
argument_list|,
name|preferences
operator|.
name|isReformatFile
argument_list|()
argument_list|,
name|preferences
operator|.
name|getLatexFieldFormatterPreferences
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|preferences
operator|.
name|getSaveType
argument_list|()
operator|!=
name|SavePreferences
operator|.
name|DatabaseSaveType
operator|.
name|PLAIN_BIBTEX
condition|)
block|{
comment|// Write meta data.
name|writeMetaData
argument_list|(
name|bibDatabaseContext
operator|.
name|getMetaData
argument_list|()
argument_list|)
expr_stmt|;
comment|// Write type definitions, if any:
name|writeEntryTypeDefinitions
argument_list|(
name|typesToWrite
argument_list|)
expr_stmt|;
block|}
comment|//finally write whatever remains of the file, but at least a concluding newline
name|writeEpilogue
argument_list|(
name|bibDatabaseContext
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEpilog
argument_list|()
argument_list|)
expr_stmt|;
try|try
block|{
name|session
operator|.
name|getWriter
argument_list|()
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|SaveException
argument_list|(
name|e
argument_list|)
throw|;
block|}
return|return
name|session
return|;
block|}
DECL|method|writePrelogue (BibDatabaseContext bibDatabaseContext, Charset encoding)
specifier|protected
specifier|abstract
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
name|SaveException
function_decl|;
DECL|method|writeEntry (BibEntry entry, BibDatabaseMode mode, Boolean isReformatFile, LatexFieldFormatterPreferences latexFieldFormatterPreferences)
specifier|protected
specifier|abstract
name|void
name|writeEntry
parameter_list|(
name|BibEntry
name|entry
parameter_list|,
name|BibDatabaseMode
name|mode
parameter_list|,
name|Boolean
name|isReformatFile
parameter_list|,
name|LatexFieldFormatterPreferences
name|latexFieldFormatterPreferences
parameter_list|)
throws|throws
name|SaveException
function_decl|;
DECL|method|writeEpilogue (String epilogue)
specifier|protected
specifier|abstract
name|void
name|writeEpilogue
parameter_list|(
name|String
name|epilogue
parameter_list|)
throws|throws
name|SaveException
function_decl|;
comment|/**      * Writes all data to the specified writer, using each object's toString() method.      */
DECL|method|writeMetaData (MetaData metaData)
specifier|protected
name|void
name|writeMetaData
parameter_list|(
name|MetaData
name|metaData
parameter_list|)
throws|throws
name|SaveException
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|metaData
argument_list|)
expr_stmt|;
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|serializedMetaData
init|=
name|metaData
operator|.
name|getAsStringMap
argument_list|()
decl_stmt|;
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|metaItem
range|:
name|serializedMetaData
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|writeMetaDataItem
argument_list|(
name|metaItem
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|writeMetaDataItem (Map.Entry<String, String> metaItem)
specifier|protected
specifier|abstract
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
name|SaveException
function_decl|;
DECL|method|writePreamble (String preamble)
specifier|protected
specifier|abstract
name|void
name|writePreamble
parameter_list|(
name|String
name|preamble
parameter_list|)
throws|throws
name|SaveException
function_decl|;
comment|/**      * Write all strings in alphabetical order, modified to produce a safe (for      * BibTeX) order of the strings if they reference each other.      *      * @param database The database whose strings we should write.      */
DECL|method|writeStrings (BibDatabase database, Boolean reformatFile, LatexFieldFormatterPreferences latexFieldFormatterPreferences)
specifier|private
name|void
name|writeStrings
parameter_list|(
name|BibDatabase
name|database
parameter_list|,
name|Boolean
name|reformatFile
parameter_list|,
name|LatexFieldFormatterPreferences
name|latexFieldFormatterPreferences
parameter_list|)
throws|throws
name|SaveException
block|{
name|List
argument_list|<
name|BibtexString
argument_list|>
name|strings
init|=
name|database
operator|.
name|getStringKeySet
argument_list|()
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|database
operator|::
name|getString
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
argument_list|)
decl_stmt|;
name|strings
operator|.
name|sort
argument_list|(
operator|new
name|BibtexStringComparator
argument_list|(
literal|true
argument_list|)
argument_list|)
expr_stmt|;
comment|// First, make a Map of all entries:
name|Map
argument_list|<
name|String
argument_list|,
name|BibtexString
argument_list|>
name|remaining
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
name|int
name|maxKeyLength
init|=
literal|0
decl_stmt|;
for|for
control|(
name|BibtexString
name|string
range|:
name|strings
control|)
block|{
name|remaining
operator|.
name|put
argument_list|(
name|string
operator|.
name|getName
argument_list|()
argument_list|,
name|string
argument_list|)
expr_stmt|;
name|maxKeyLength
operator|=
name|Math
operator|.
name|max
argument_list|(
name|maxKeyLength
argument_list|,
name|string
operator|.
name|getName
argument_list|()
operator|.
name|length
argument_list|()
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|BibtexString
operator|.
name|Type
name|t
range|:
name|BibtexString
operator|.
name|Type
operator|.
name|values
argument_list|()
control|)
block|{
name|boolean
name|isFirstStringInType
init|=
literal|true
decl_stmt|;
for|for
control|(
name|BibtexString
name|bs
range|:
name|strings
control|)
block|{
if|if
condition|(
name|remaining
operator|.
name|containsKey
argument_list|(
name|bs
operator|.
name|getName
argument_list|()
argument_list|)
operator|&&
operator|(
name|bs
operator|.
name|getType
argument_list|()
operator|==
name|t
operator|)
condition|)
block|{
name|writeString
argument_list|(
name|bs
argument_list|,
name|isFirstStringInType
argument_list|,
name|remaining
argument_list|,
name|maxKeyLength
argument_list|,
name|reformatFile
argument_list|,
name|latexFieldFormatterPreferences
argument_list|)
expr_stmt|;
name|isFirstStringInType
operator|=
literal|false
expr_stmt|;
block|}
block|}
block|}
block|}
DECL|method|writeString (BibtexString bibtexString, boolean isFirstString, Map<String, BibtexString> remaining, int maxKeyLength, Boolean reformatFile, LatexFieldFormatterPreferences latexFieldFormatterPreferences)
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
name|Map
argument_list|<
name|String
argument_list|,
name|BibtexString
argument_list|>
name|remaining
parameter_list|,
name|int
name|maxKeyLength
parameter_list|,
name|Boolean
name|reformatFile
parameter_list|,
name|LatexFieldFormatterPreferences
name|latexFieldFormatterPreferences
parameter_list|)
throws|throws
name|SaveException
block|{
comment|// First remove this from the "remaining" list so it can't cause problem with circular refs:
name|remaining
operator|.
name|remove
argument_list|(
name|bibtexString
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
comment|// Then we go through the string looking for references to other strings. If we find references
comment|// to strings that we will write, but still haven't, we write those before proceeding. This ensures
comment|// that the string order will be acceptable for BibTeX.
name|String
name|content
init|=
name|bibtexString
operator|.
name|getContent
argument_list|()
decl_stmt|;
name|Matcher
name|m
decl_stmt|;
while|while
condition|(
operator|(
name|m
operator|=
name|REFERENCE_PATTERN
operator|.
name|matcher
argument_list|(
name|content
argument_list|)
operator|)
operator|.
name|find
argument_list|()
condition|)
block|{
name|String
name|foundLabel
init|=
name|m
operator|.
name|group
argument_list|(
literal|1
argument_list|)
decl_stmt|;
name|int
name|restIndex
init|=
name|content
operator|.
name|indexOf
argument_list|(
name|foundLabel
argument_list|)
operator|+
name|foundLabel
operator|.
name|length
argument_list|()
decl_stmt|;
name|content
operator|=
name|content
operator|.
name|substring
argument_list|(
name|restIndex
argument_list|)
expr_stmt|;
name|String
name|label
init|=
name|foundLabel
operator|.
name|substring
argument_list|(
literal|1
argument_list|,
name|foundLabel
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
decl_stmt|;
comment|// If the label we found exists as a key in the "remaining" Map, we go on and write it now:
if|if
condition|(
name|remaining
operator|.
name|containsKey
argument_list|(
name|label
argument_list|)
condition|)
block|{
name|BibtexString
name|referred
init|=
name|remaining
operator|.
name|get
argument_list|(
name|label
argument_list|)
decl_stmt|;
name|writeString
argument_list|(
name|referred
argument_list|,
name|isFirstString
argument_list|,
name|remaining
argument_list|,
name|maxKeyLength
argument_list|,
name|reformatFile
argument_list|,
name|latexFieldFormatterPreferences
argument_list|)
expr_stmt|;
block|}
block|}
name|writeString
argument_list|(
name|bibtexString
argument_list|,
name|isFirstString
argument_list|,
name|maxKeyLength
argument_list|,
name|reformatFile
argument_list|,
name|latexFieldFormatterPreferences
argument_list|)
expr_stmt|;
block|}
DECL|method|writeString (BibtexString bibtexString, boolean isFirstString, int maxKeyLength, Boolean reformatFile, LatexFieldFormatterPreferences latexFieldFormatterPreferences)
specifier|protected
specifier|abstract
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
parameter_list|,
name|Boolean
name|reformatFile
parameter_list|,
name|LatexFieldFormatterPreferences
name|latexFieldFormatterPreferences
parameter_list|)
throws|throws
name|SaveException
function_decl|;
DECL|method|writeEntryTypeDefinitions (Map<String, EntryType> types)
specifier|protected
name|void
name|writeEntryTypeDefinitions
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|EntryType
argument_list|>
name|types
parameter_list|)
throws|throws
name|SaveException
block|{
for|for
control|(
name|EntryType
name|type
range|:
name|types
operator|.
name|values
argument_list|()
control|)
block|{
if|if
condition|(
name|type
operator|instanceof
name|CustomEntryType
condition|)
block|{
name|writeEntryTypeDefinition
argument_list|(
operator|(
name|CustomEntryType
operator|)
name|type
argument_list|)
expr_stmt|;
block|}
block|}
block|}
DECL|method|writeEntryTypeDefinition (CustomEntryType customType)
specifier|protected
specifier|abstract
name|void
name|writeEntryTypeDefinition
parameter_list|(
name|CustomEntryType
name|customType
parameter_list|)
throws|throws
name|SaveException
function_decl|;
DECL|method|getActiveSession ()
specifier|protected
name|SaveSession
name|getActiveSession
parameter_list|()
block|{
return|return
name|session
return|;
block|}
block|}
end_class

end_unit

