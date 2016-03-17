begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.  This program is free software; you can redistribute it and/or modify  it under the terms of the GNU General Public License as published by  the Free Software Foundation; either version 2 of the License, or  (at your option) any later version.   This program is distributed in the hope that it will be useful,  but WITHOUT ANY WARRANTY; without even the implied warranty of  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  GNU General Public License for more details.   You should have received a copy of the GNU General Public License along  with this program; if not, write to the Free Software Foundation, Inc.,  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
end_comment

begin_package
DECL|package|net.sf.jabref.exporter
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
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
name|*
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
name|logic
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
name|entry
operator|.
name|*
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
name|bibtex
operator|.
name|BibEntryWriter
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
name|logic
operator|.
name|id
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
name|model
operator|.
name|database
operator|.
name|BibDatabase
import|;
end_import

begin_class
DECL|class|BibDatabaseWriter
specifier|public
class|class
name|BibDatabaseWriter
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
name|BibDatabaseWriter
operator|.
name|class
argument_list|)
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
DECL|field|exceptionCause
specifier|private
name|BibEntry
name|exceptionCause
decl_stmt|;
DECL|field|isFirstStringInType
specifier|private
name|boolean
name|isFirstStringInType
decl_stmt|;
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
comment|/*      * We have begun to use getSortedEntries() for both database save operations      * and non-database save operations.  In a non-database save operation      * (such as the exportDatabase call), we do not wish to use the      * global preference of saving in standard order.      */
DECL|method|getSortedEntries (BibDatabaseContext bibDatabaseContext, Set<String> keySet, SavePreferences preferences)
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
name|Set
argument_list|<
name|String
argument_list|>
name|keySet
parameter_list|,
name|SavePreferences
name|preferences
parameter_list|)
block|{
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
name|bibDatabaseContext
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
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
if|if
condition|(
name|keySet
operator|==
literal|null
condition|)
block|{
name|keySet
operator|=
name|bibDatabaseContext
operator|.
name|getDatabase
argument_list|()
operator|.
name|getKeySet
argument_list|()
expr_stmt|;
block|}
for|for
control|(
name|String
name|id
range|:
name|keySet
control|)
block|{
name|sorted
operator|.
name|add
argument_list|(
name|bibDatabaseContext
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntryById
argument_list|(
name|id
argument_list|)
argument_list|)
expr_stmt|;
block|}
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
comment|/**      * Saves the database to file. Two boolean values indicate whether only      * entries which are marked as search / group hit should be saved. This can be used to      * let the user save only the results of a search. False and false means all      * entries are saved.      */
DECL|method|saveDatabase (BibDatabaseContext bibDatabaseContext, SavePreferences preferences)
specifier|public
name|SaveSession
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
DECL|method|savePartOfDatabase (BibDatabaseContext bibDatabaseContext, Collection<BibEntry> entries, SavePreferences preferences)
specifier|public
name|SaveSession
name|savePartOfDatabase
parameter_list|(
name|BibDatabaseContext
name|bibDatabaseContext
parameter_list|,
name|Collection
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
name|SaveSession
name|session
decl_stmt|;
try|try
block|{
name|session
operator|=
operator|new
name|SaveSession
argument_list|(
name|preferences
operator|.
name|getEncoding
argument_list|()
argument_list|,
name|preferences
operator|.
name|getMakeBackup
argument_list|()
argument_list|)
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
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
operator|.
name|getLocalizedMessage
argument_list|()
argument_list|)
throw|;
block|}
name|exceptionCause
operator|=
literal|null
expr_stmt|;
comment|// Get our data stream. This stream writes only to a temporary file until committed.
try|try
init|(
name|VerifyingWriter
name|writer
init|=
name|session
operator|.
name|getWriter
argument_list|()
init|)
block|{
name|List
argument_list|<
name|FieldChange
argument_list|>
name|saveActionChanges
init|=
name|writePartOfDatabase
argument_list|(
name|writer
argument_list|,
name|bibDatabaseContext
argument_list|,
name|entries
argument_list|,
name|preferences
argument_list|)
decl_stmt|;
name|session
operator|.
name|addFieldChanges
argument_list|(
name|saveActionChanges
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Could not write file"
argument_list|,
name|ex
argument_list|)
expr_stmt|;
name|session
operator|.
name|cancel
argument_list|()
expr_stmt|;
throw|throw
operator|new
name|SaveException
argument_list|(
name|ex
operator|.
name|getMessage
argument_list|()
argument_list|,
name|ex
operator|.
name|getLocalizedMessage
argument_list|()
argument_list|,
name|exceptionCause
argument_list|)
throw|;
block|}
return|return
name|session
return|;
block|}
DECL|method|writePartOfDatabase (Writer writer, BibDatabaseContext bibDatabaseContext, Collection<BibEntry> entries, SavePreferences preferences)
specifier|public
name|List
argument_list|<
name|FieldChange
argument_list|>
name|writePartOfDatabase
parameter_list|(
name|Writer
name|writer
parameter_list|,
name|BibDatabaseContext
name|bibDatabaseContext
parameter_list|,
name|Collection
argument_list|<
name|BibEntry
argument_list|>
name|entries
parameter_list|,
name|SavePreferences
name|preferences
parameter_list|)
throws|throws
name|IOException
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|writer
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
comment|// Write signature.
name|writeBibFileHeader
argument_list|(
name|writer
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
name|writer
argument_list|,
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
name|writer
argument_list|,
name|bibDatabaseContext
operator|.
name|getDatabase
argument_list|()
argument_list|,
name|preferences
operator|.
name|isReformatFile
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
name|BibDatabaseWriter
operator|.
name|getSortedEntries
argument_list|(
name|bibDatabaseContext
argument_list|,
name|entries
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|BibEntry
operator|::
name|getId
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toSet
argument_list|()
argument_list|)
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
name|BibDatabaseWriter
operator|.
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
name|BibEntryWriter
name|bibtexEntryWriter
init|=
operator|new
name|BibEntryWriter
argument_list|(
operator|new
name|LatexFieldFormatter
argument_list|()
argument_list|,
literal|true
argument_list|)
decl_stmt|;
for|for
control|(
name|BibEntry
name|entry
range|:
name|sortedEntries
control|)
block|{
name|exceptionCause
operator|=
name|entry
expr_stmt|;
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
name|bibtexEntryWriter
operator|.
name|write
argument_list|(
name|entry
argument_list|,
name|writer
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
name|writer
argument_list|,
name|bibDatabaseContext
operator|.
name|getMetaData
argument_list|()
argument_list|)
expr_stmt|;
comment|// Write type definitions, if any:
name|writeTypeDefinitions
argument_list|(
name|writer
argument_list|,
name|typesToWrite
argument_list|)
expr_stmt|;
block|}
comment|//finally write whatever remains of the file, but at least a concluding newline
name|writeEpilogue
argument_list|(
name|writer
argument_list|,
name|bibDatabaseContext
operator|.
name|getDatabase
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|saveActionChanges
return|;
block|}
comment|/**      * Saves the database to file, including only the entries included in the      * supplied input array bes.      */
DECL|method|savePartOfDatabase (BibDatabaseContext bibDatabaseContext, SavePreferences preferences, Collection<BibEntry> entries)
specifier|public
name|SaveSession
name|savePartOfDatabase
parameter_list|(
name|BibDatabaseContext
name|bibDatabaseContext
parameter_list|,
name|SavePreferences
name|preferences
parameter_list|,
name|Collection
argument_list|<
name|BibEntry
argument_list|>
name|entries
parameter_list|)
throws|throws
name|SaveException
block|{
return|return
name|savePartOfDatabase
argument_list|(
name|bibDatabaseContext
argument_list|,
name|entries
argument_list|,
name|preferences
argument_list|)
return|;
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
if|if
condition|(
name|metaData
operator|.
name|getData
argument_list|(
name|MetaData
operator|.
name|SAVE_ACTIONS
argument_list|)
operator|!=
literal|null
condition|)
block|{
comment|// save actions defined -> apply for every entry
name|FieldFormatterCleanups
name|saveActions
init|=
name|metaData
operator|.
name|getSaveActions
argument_list|()
decl_stmt|;
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
name|saveActions
operator|.
name|applySaveActions
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|changes
return|;
block|}
comment|/**      * Writes the file encoding information.      *      * @param encoding String the name of the encoding, which is part of the file header.      */
DECL|method|writeBibFileHeader (Writer out, Charset encoding)
specifier|private
name|void
name|writeBibFileHeader
parameter_list|(
name|Writer
name|out
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
name|out
operator|.
name|write
argument_list|(
literal|"% "
argument_list|)
expr_stmt|;
name|out
operator|.
name|write
argument_list|(
name|Globals
operator|.
name|encPrefix
operator|+
name|encoding
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
DECL|method|writeEpilogue (Writer writer, BibDatabase database)
specifier|private
name|void
name|writeEpilogue
parameter_list|(
name|Writer
name|writer
parameter_list|,
name|BibDatabase
name|database
parameter_list|)
throws|throws
name|IOException
block|{
if|if
condition|(
operator|(
name|database
operator|.
name|getEpilog
argument_list|()
operator|!=
literal|null
operator|)
operator|&&
operator|!
operator|(
name|database
operator|.
name|getEpilog
argument_list|()
operator|.
name|isEmpty
argument_list|()
operator|)
condition|)
block|{
name|writer
operator|.
name|write
argument_list|(
name|Globals
operator|.
name|NEWLINE
argument_list|)
expr_stmt|;
name|writer
operator|.
name|write
argument_list|(
name|database
operator|.
name|getEpilog
argument_list|()
argument_list|)
expr_stmt|;
name|writer
operator|.
name|write
argument_list|(
name|Globals
operator|.
name|NEWLINE
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Writes all data to the specified writer, using each object's toString() method.      */
DECL|method|writeMetaData (Writer out, MetaData metaData)
specifier|private
name|void
name|writeMetaData
parameter_list|(
name|Writer
name|out
parameter_list|,
name|MetaData
name|metaData
parameter_list|)
throws|throws
name|IOException
block|{
if|if
condition|(
name|metaData
operator|==
literal|null
condition|)
block|{
return|return;
block|}
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
name|serialize
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
name|StringBuilder
name|stringBuilder
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|stringBuilder
operator|.
name|append
argument_list|(
name|Globals
operator|.
name|NEWLINE
argument_list|)
expr_stmt|;
name|stringBuilder
operator|.
name|append
argument_list|(
name|COMMENT_PREFIX
operator|+
literal|"{"
argument_list|)
operator|.
name|append
argument_list|(
name|MetaData
operator|.
name|META_FLAG
argument_list|)
operator|.
name|append
argument_list|(
name|metaItem
operator|.
name|getKey
argument_list|()
argument_list|)
operator|.
name|append
argument_list|(
literal|":"
argument_list|)
expr_stmt|;
name|stringBuilder
operator|.
name|append
argument_list|(
name|metaItem
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
name|stringBuilder
operator|.
name|append
argument_list|(
literal|"}"
argument_list|)
expr_stmt|;
name|stringBuilder
operator|.
name|append
argument_list|(
name|Globals
operator|.
name|NEWLINE
argument_list|)
expr_stmt|;
name|out
operator|.
name|write
argument_list|(
name|stringBuilder
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|writePreamble (Writer fw, String preamble)
specifier|private
name|void
name|writePreamble
parameter_list|(
name|Writer
name|fw
parameter_list|,
name|String
name|preamble
parameter_list|)
throws|throws
name|IOException
block|{
if|if
condition|(
name|preamble
operator|!=
literal|null
condition|)
block|{
name|fw
operator|.
name|write
argument_list|(
name|Globals
operator|.
name|NEWLINE
argument_list|)
expr_stmt|;
name|fw
operator|.
name|write
argument_list|(
name|PREAMBLE_PREFIX
operator|+
literal|"{"
argument_list|)
expr_stmt|;
name|fw
operator|.
name|write
argument_list|(
name|preamble
argument_list|)
expr_stmt|;
name|fw
operator|.
name|write
argument_list|(
literal|'}'
operator|+
name|Globals
operator|.
name|NEWLINE
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|writeString (Writer fw, BibtexString bs, Map<String, BibtexString> remaining, int maxKeyLength, Boolean reformatFile)
specifier|private
name|void
name|writeString
parameter_list|(
name|Writer
name|fw
parameter_list|,
name|BibtexString
name|bs
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
parameter_list|)
throws|throws
name|IOException
block|{
comment|// First remove this from the "remaining" list so it can't cause problem with circular refs:
name|remaining
operator|.
name|remove
argument_list|(
name|bs
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
comment|//if the string has not been modified, write it back as it was
if|if
condition|(
operator|!
name|reformatFile
operator|&&
operator|!
name|bs
operator|.
name|hasChanged
argument_list|()
condition|)
block|{
name|fw
operator|.
name|write
argument_list|(
name|bs
operator|.
name|getParsedSerialization
argument_list|()
argument_list|)
expr_stmt|;
return|return;
block|}
if|if
condition|(
name|isFirstStringInType
condition|)
block|{
name|fw
operator|.
name|write
argument_list|(
name|Globals
operator|.
name|NEWLINE
argument_list|)
expr_stmt|;
block|}
comment|// Then we go through the string looking for references to other strings. If we find references
comment|// to strings that we will write, but still haven't, we write those before proceeding. This ensures
comment|// that the string order will be acceptable for BibTeX.
name|String
name|content
init|=
name|bs
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
name|BibDatabaseWriter
operator|.
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
name|Object
name|referred
init|=
name|remaining
operator|.
name|get
argument_list|(
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
argument_list|)
decl_stmt|;
comment|// If the label we found exists as a key in the "remaining" Map, we go on and write it now:
if|if
condition|(
name|referred
operator|!=
literal|null
condition|)
block|{
name|writeString
argument_list|(
name|fw
argument_list|,
operator|(
name|BibtexString
operator|)
name|referred
argument_list|,
name|remaining
argument_list|,
name|maxKeyLength
argument_list|,
name|reformatFile
argument_list|)
expr_stmt|;
block|}
block|}
name|StringBuilder
name|suffixSB
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
name|maxKeyLength
operator|-
name|bs
operator|.
name|getName
argument_list|()
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
name|suffixSB
operator|.
name|append
argument_list|(
literal|' '
argument_list|)
expr_stmt|;
block|}
name|String
name|suffix
init|=
name|suffixSB
operator|.
name|toString
argument_list|()
decl_stmt|;
name|fw
operator|.
name|write
argument_list|(
name|STRING_PREFIX
operator|+
literal|"{"
operator|+
name|bs
operator|.
name|getName
argument_list|()
operator|+
name|suffix
operator|+
literal|" = "
argument_list|)
expr_stmt|;
if|if
condition|(
name|bs
operator|.
name|getContent
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|fw
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
argument_list|()
operator|.
name|format
argument_list|(
name|bs
operator|.
name|getContent
argument_list|()
argument_list|,
name|LatexFieldFormatter
operator|.
name|BIBTEX_STRING
argument_list|)
decl_stmt|;
name|fw
operator|.
name|write
argument_list|(
name|formatted
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IllegalArgumentException
name|ex
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The # character is not allowed in BibTeX strings unless escaped as in '\\#'.\n"
operator|+
literal|"Before saving, please edit any strings containing the # character."
argument_list|)
throw|;
block|}
block|}
name|fw
operator|.
name|write
argument_list|(
literal|"}"
operator|+
name|Globals
operator|.
name|NEWLINE
argument_list|)
expr_stmt|;
block|}
comment|/**      * Write all strings in alphabetical order, modified to produce a safe (for      * BibTeX) order of the strings if they reference each other.      *      * @param fw       The Writer to send the output to.      * @param database The database whose strings we should write.      * @param reformatFile      * @throws IOException If anything goes wrong in writing.      */
DECL|method|writeStrings (Writer fw, BibDatabase database, Boolean reformatFile)
specifier|private
name|void
name|writeStrings
parameter_list|(
name|Writer
name|fw
parameter_list|,
name|BibDatabase
name|database
parameter_list|,
name|Boolean
name|reformatFile
parameter_list|)
throws|throws
name|IOException
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
name|HashMap
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
name|isFirstStringInType
operator|=
literal|true
expr_stmt|;
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
name|fw
argument_list|,
name|bs
argument_list|,
name|remaining
argument_list|,
name|maxKeyLength
argument_list|,
name|reformatFile
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
DECL|method|writeTypeDefinitions (Writer writer, Map<String, EntryType> types)
specifier|private
name|void
name|writeTypeDefinitions
parameter_list|(
name|Writer
name|writer
parameter_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|EntryType
argument_list|>
name|types
parameter_list|)
throws|throws
name|IOException
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
name|CustomEntryType
name|customType
init|=
operator|(
name|CustomEntryType
operator|)
name|type
decl_stmt|;
name|writer
operator|.
name|write
argument_list|(
name|Globals
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
name|CustomEntryType
operator|.
name|ENTRYTYPE_FLAG
argument_list|)
expr_stmt|;
name|writer
operator|.
name|write
argument_list|(
name|customType
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|writer
operator|.
name|write
argument_list|(
literal|": req["
argument_list|)
expr_stmt|;
name|writer
operator|.
name|write
argument_list|(
name|customType
operator|.
name|getRequiredFieldsString
argument_list|()
argument_list|)
expr_stmt|;
name|writer
operator|.
name|write
argument_list|(
literal|"] opt["
argument_list|)
expr_stmt|;
name|writer
operator|.
name|write
argument_list|(
name|String
operator|.
name|join
argument_list|(
literal|";"
argument_list|,
name|customType
operator|.
name|getOptionalFields
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|writer
operator|.
name|write
argument_list|(
literal|"]}"
argument_list|)
expr_stmt|;
name|writer
operator|.
name|write
argument_list|(
name|Globals
operator|.
name|NEWLINE
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
end_class

end_unit

