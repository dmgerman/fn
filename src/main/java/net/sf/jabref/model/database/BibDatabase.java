begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* Copyright (C) 2003-2016 JabRef contributors Copyright (C) 2003 David Weitzman, Morten O. Alver  All programs in this directory and subdirectories are published under the GNU General Public License as described below.  This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.  You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA  Further information about the GNU GPL is available at: http://www.gnu.org/copyleft/gpl.ja.html  Note: Modified for use in JabRef   */
end_comment

begin_package
DECL|package|net.sf.jabref.model.database
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|model
operator|.
name|database
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
name|Collection
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
name|HashSet
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
name|java
operator|.
name|util
operator|.
name|concurrent
operator|.
name|ConcurrentHashMap
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|event
operator|.
name|source
operator|.
name|EntryEventSource
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
name|InternalBibtexFields
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
name|MonthUtil
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
name|event
operator|.
name|EntryAddedEvent
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
name|event
operator|.
name|EntryChangedEvent
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
name|event
operator|.
name|EntryRemovedEvent
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
name|event
operator|.
name|FieldChangedEvent
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
name|eventbus
operator|.
name|EventBus
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
name|eventbus
operator|.
name|Subscribe
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

begin_comment
comment|/**  * A bibliography database.  */
end_comment

begin_class
DECL|class|BibDatabase
specifier|public
class|class
name|BibDatabase
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
name|BibDatabase
operator|.
name|class
argument_list|)
decl_stmt|;
comment|/**      * State attributes      */
DECL|field|entries
specifier|private
specifier|final
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
init|=
name|Collections
operator|.
name|synchronizedList
argument_list|(
operator|new
name|ArrayList
argument_list|<>
argument_list|()
argument_list|)
decl_stmt|;
DECL|field|preamble
specifier|private
name|String
name|preamble
decl_stmt|;
comment|// All file contents below the last entry in the file
DECL|field|epilog
specifier|private
name|String
name|epilog
init|=
literal|""
decl_stmt|;
DECL|field|bibtexStrings
specifier|private
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|BibtexString
argument_list|>
name|bibtexStrings
init|=
operator|new
name|ConcurrentHashMap
argument_list|<>
argument_list|()
decl_stmt|;
comment|/**      * this is kept in sync with the database (upon adding/removing an entry, it is updated as well)      */
DECL|field|duplicationChecker
specifier|private
specifier|final
name|DuplicationChecker
name|duplicationChecker
init|=
operator|new
name|DuplicationChecker
argument_list|()
decl_stmt|;
comment|/**      * contains all entry.getID() of the current database      */
DECL|field|internalIDs
specifier|private
specifier|final
name|Set
argument_list|<
name|String
argument_list|>
name|internalIDs
init|=
operator|new
name|HashSet
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|eventBus
specifier|private
specifier|final
name|EventBus
name|eventBus
init|=
operator|new
name|EventBus
argument_list|()
decl_stmt|;
comment|/**      * Returns the number of entries.      */
DECL|method|getEntryCount ()
specifier|public
name|int
name|getEntryCount
parameter_list|()
block|{
return|return
name|entries
operator|.
name|size
argument_list|()
return|;
block|}
comment|/**      * Checks if the database contains entries.      */
DECL|method|hasEntries ()
specifier|public
name|boolean
name|hasEntries
parameter_list|()
block|{
return|return
operator|!
name|entries
operator|.
name|isEmpty
argument_list|()
return|;
block|}
comment|/**      * Returns an EntrySorter with the sorted entries from this base,      * sorted by the given Comparator.      */
DECL|method|getSorter (Comparator<BibEntry> comp)
specifier|public
specifier|synchronized
name|EntrySorter
name|getSorter
parameter_list|(
name|Comparator
argument_list|<
name|BibEntry
argument_list|>
name|comp
parameter_list|)
block|{
return|return
operator|new
name|EntrySorter
argument_list|(
operator|new
name|ArrayList
argument_list|<>
argument_list|(
name|getEntries
argument_list|()
argument_list|)
argument_list|,
name|comp
argument_list|)
return|;
block|}
comment|/**      * Returns whether an entry with the given ID exists (-> entry_type + hashcode).      */
DECL|method|containsEntryWithId (String id)
specifier|public
name|boolean
name|containsEntryWithId
parameter_list|(
name|String
name|id
parameter_list|)
block|{
return|return
name|internalIDs
operator|.
name|contains
argument_list|(
name|id
argument_list|)
return|;
block|}
DECL|method|getEntries ()
specifier|public
name|List
argument_list|<
name|BibEntry
argument_list|>
name|getEntries
parameter_list|()
block|{
return|return
name|Collections
operator|.
name|unmodifiableList
argument_list|(
name|entries
argument_list|)
return|;
block|}
DECL|method|getAllVisibleFields ()
specifier|public
name|Set
argument_list|<
name|String
argument_list|>
name|getAllVisibleFields
parameter_list|()
block|{
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
for|for
control|(
name|BibEntry
name|e
range|:
name|getEntries
argument_list|()
control|)
block|{
name|allFields
operator|.
name|addAll
argument_list|(
name|e
operator|.
name|getFieldNames
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|Set
argument_list|<
name|String
argument_list|>
name|toberemoved
init|=
operator|new
name|TreeSet
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|field
range|:
name|allFields
control|)
block|{
if|if
condition|(
name|InternalBibtexFields
operator|.
name|isInternalField
argument_list|(
name|field
argument_list|)
condition|)
block|{
name|toberemoved
operator|.
name|add
argument_list|(
name|field
argument_list|)
expr_stmt|;
block|}
block|}
for|for
control|(
name|String
name|field
range|:
name|toberemoved
control|)
block|{
name|allFields
operator|.
name|remove
argument_list|(
name|field
argument_list|)
expr_stmt|;
block|}
return|return
name|allFields
return|;
block|}
comment|/**      * Returns the entry with the given bibtex key.      */
DECL|method|getEntryByKey (String key)
specifier|public
specifier|synchronized
name|Optional
argument_list|<
name|BibEntry
argument_list|>
name|getEntryByKey
parameter_list|(
name|String
name|key
parameter_list|)
block|{
for|for
control|(
name|BibEntry
name|entry
range|:
name|entries
control|)
block|{
if|if
condition|(
name|key
operator|.
name|equals
argument_list|(
name|entry
operator|.
name|getCiteKey
argument_list|()
argument_list|)
condition|)
block|{
return|return
name|Optional
operator|.
name|of
argument_list|(
name|entry
argument_list|)
return|;
block|}
block|}
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
DECL|method|getEntriesByKey (String key)
specifier|public
specifier|synchronized
name|List
argument_list|<
name|BibEntry
argument_list|>
name|getEntriesByKey
parameter_list|(
name|String
name|key
parameter_list|)
block|{
name|List
argument_list|<
name|BibEntry
argument_list|>
name|result
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
name|BibEntry
name|entry
range|:
name|entries
control|)
block|{
if|if
condition|(
name|key
operator|.
name|equals
argument_list|(
name|entry
operator|.
name|getCiteKey
argument_list|()
argument_list|)
condition|)
block|{
name|result
operator|.
name|add
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|result
return|;
block|}
comment|/**      * Inserts the entry, given that its ID is not already in use.      * use Util.createId(...) to make up a unique ID for an entry.      *      * @param entry BibEntry to insert into the database      * @return false if the insert was done without a duplicate warning      * @throws KeyCollisionException thrown if the entry id ({@link BibEntry#getId()}) is already  present in the database      */
DECL|method|insertEntry (BibEntry entry)
specifier|public
specifier|synchronized
name|boolean
name|insertEntry
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
throws|throws
name|KeyCollisionException
block|{
return|return
name|insertEntry
argument_list|(
name|entry
argument_list|,
name|EntryEventSource
operator|.
name|LOCAL
argument_list|)
return|;
block|}
comment|/**      * Inserts the entry, given that its ID is not already in use.      * use Util.createId(...) to make up a unique ID for an entry.      *      * @param entry BibEntry to insert      * @param eventSource Source the event is sent from      * @return false if the insert was done without a duplicate warning      */
DECL|method|insertEntry (BibEntry entry, EntryEventSource eventSource)
specifier|public
specifier|synchronized
name|boolean
name|insertEntry
parameter_list|(
name|BibEntry
name|entry
parameter_list|,
name|EntryEventSource
name|eventSource
parameter_list|)
throws|throws
name|KeyCollisionException
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|String
name|id
init|=
name|entry
operator|.
name|getId
argument_list|()
decl_stmt|;
if|if
condition|(
name|containsEntryWithId
argument_list|(
name|id
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|KeyCollisionException
argument_list|(
literal|"ID is already in use, please choose another"
argument_list|)
throw|;
block|}
name|internalIDs
operator|.
name|add
argument_list|(
name|id
argument_list|)
expr_stmt|;
name|entries
operator|.
name|add
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|entry
operator|.
name|registerListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|eventBus
operator|.
name|post
argument_list|(
operator|new
name|EntryAddedEvent
argument_list|(
name|entry
argument_list|,
name|eventSource
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|duplicationChecker
operator|.
name|checkForDuplicateKeyAndAdd
argument_list|(
literal|null
argument_list|,
name|entry
operator|.
name|getCiteKeyOptional
argument_list|()
operator|.
name|orElse
argument_list|(
literal|null
argument_list|)
argument_list|)
return|;
block|}
comment|/**      * Removes the given entry.      * The Entry is removed based on the id {@link BibEntry#id}      * @param toBeDeleted Entry to delete      */
DECL|method|removeEntry (BibEntry toBeDeleted)
specifier|public
specifier|synchronized
name|void
name|removeEntry
parameter_list|(
name|BibEntry
name|toBeDeleted
parameter_list|)
block|{
name|removeEntry
argument_list|(
name|toBeDeleted
argument_list|,
name|EntryEventSource
operator|.
name|LOCAL
argument_list|)
expr_stmt|;
block|}
comment|/**      * Removes the given entry.      * The Entry is removed based on the id {@link BibEntry#id}      * @param toBeDeleted Entry to delete      * @param eventSource Source the event is sent from      */
DECL|method|removeEntry (BibEntry toBeDeleted, EntryEventSource eventSource)
specifier|public
specifier|synchronized
name|void
name|removeEntry
parameter_list|(
name|BibEntry
name|toBeDeleted
parameter_list|,
name|EntryEventSource
name|eventSource
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|toBeDeleted
argument_list|)
expr_stmt|;
name|boolean
name|anyRemoved
init|=
name|entries
operator|.
name|removeIf
argument_list|(
name|entry
lambda|->
name|entry
operator|.
name|getId
argument_list|()
operator|.
name|equals
argument_list|(
name|toBeDeleted
operator|.
name|getId
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|anyRemoved
condition|)
block|{
name|internalIDs
operator|.
name|remove
argument_list|(
name|toBeDeleted
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
name|toBeDeleted
operator|.
name|getCiteKeyOptional
argument_list|()
operator|.
name|ifPresent
argument_list|(
name|duplicationChecker
operator|::
name|removeKeyFromSet
argument_list|)
expr_stmt|;
name|eventBus
operator|.
name|post
argument_list|(
operator|new
name|EntryRemovedEvent
argument_list|(
name|toBeDeleted
argument_list|,
name|eventSource
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|getNumberOfKeyOccurrences (String key)
specifier|public
name|int
name|getNumberOfKeyOccurrences
parameter_list|(
name|String
name|key
parameter_list|)
block|{
return|return
name|duplicationChecker
operator|.
name|getNumberOfKeyOccurrences
argument_list|(
name|key
argument_list|)
return|;
block|}
DECL|method|setCiteKeyForEntry (BibEntry entry, String key)
specifier|public
specifier|synchronized
name|boolean
name|setCiteKeyForEntry
parameter_list|(
name|BibEntry
name|entry
parameter_list|,
name|String
name|key
parameter_list|)
block|{
name|String
name|oldKey
init|=
name|entry
operator|.
name|getCiteKeyOptional
argument_list|()
operator|.
name|orElse
argument_list|(
literal|null
argument_list|)
decl_stmt|;
if|if
condition|(
name|key
operator|==
literal|null
condition|)
block|{
name|entry
operator|.
name|clearField
argument_list|(
name|BibEntry
operator|.
name|KEY_FIELD
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|entry
operator|.
name|setCiteKey
argument_list|(
name|key
argument_list|)
expr_stmt|;
block|}
return|return
name|duplicationChecker
operator|.
name|checkForDuplicateKeyAndAdd
argument_list|(
name|oldKey
argument_list|,
name|key
argument_list|)
return|;
block|}
comment|/**      * Sets the database's preamble.      */
DECL|method|setPreamble (String preamble)
specifier|public
specifier|synchronized
name|void
name|setPreamble
parameter_list|(
name|String
name|preamble
parameter_list|)
block|{
name|this
operator|.
name|preamble
operator|=
name|preamble
expr_stmt|;
block|}
comment|/**      * Returns the database's preamble.      */
DECL|method|getPreamble ()
specifier|public
specifier|synchronized
name|String
name|getPreamble
parameter_list|()
block|{
return|return
name|preamble
return|;
block|}
comment|/**      * Inserts a Bibtex String.      */
DECL|method|addString (BibtexString string)
specifier|public
specifier|synchronized
name|void
name|addString
parameter_list|(
name|BibtexString
name|string
parameter_list|)
throws|throws
name|KeyCollisionException
block|{
if|if
condition|(
name|hasStringLabel
argument_list|(
name|string
operator|.
name|getName
argument_list|()
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|KeyCollisionException
argument_list|(
literal|"A string with that label already exists"
argument_list|)
throw|;
block|}
if|if
condition|(
name|bibtexStrings
operator|.
name|containsKey
argument_list|(
name|string
operator|.
name|getId
argument_list|()
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|KeyCollisionException
argument_list|(
literal|"Duplicate BibTeXString id."
argument_list|)
throw|;
block|}
name|bibtexStrings
operator|.
name|put
argument_list|(
name|string
operator|.
name|getId
argument_list|()
argument_list|,
name|string
argument_list|)
expr_stmt|;
block|}
comment|/**      * Removes the string with the given id.      */
DECL|method|removeString (String id)
specifier|public
name|void
name|removeString
parameter_list|(
name|String
name|id
parameter_list|)
block|{
name|bibtexStrings
operator|.
name|remove
argument_list|(
name|id
argument_list|)
expr_stmt|;
block|}
comment|/**      * Returns a Set of keys to all BibtexString objects in the database.      * These are in no sorted order.      */
DECL|method|getStringKeySet ()
specifier|public
name|Set
argument_list|<
name|String
argument_list|>
name|getStringKeySet
parameter_list|()
block|{
return|return
name|bibtexStrings
operator|.
name|keySet
argument_list|()
return|;
block|}
comment|/**      * Returns a Collection of all BibtexString objects in the database.      * These are in no particular order.      */
DECL|method|getStringValues ()
specifier|public
name|Collection
argument_list|<
name|BibtexString
argument_list|>
name|getStringValues
parameter_list|()
block|{
return|return
name|bibtexStrings
operator|.
name|values
argument_list|()
return|;
block|}
comment|/**      * Returns the string with the given id.      */
DECL|method|getString (String id)
specifier|public
name|BibtexString
name|getString
parameter_list|(
name|String
name|id
parameter_list|)
block|{
return|return
name|bibtexStrings
operator|.
name|get
argument_list|(
name|id
argument_list|)
return|;
block|}
comment|/**      * Returns the number of strings.      */
DECL|method|getStringCount ()
specifier|public
name|int
name|getStringCount
parameter_list|()
block|{
return|return
name|bibtexStrings
operator|.
name|size
argument_list|()
return|;
block|}
comment|/**      * Check if there are strings.      */
DECL|method|hasNoStrings ()
specifier|public
name|boolean
name|hasNoStrings
parameter_list|()
block|{
return|return
name|bibtexStrings
operator|.
name|isEmpty
argument_list|()
return|;
block|}
comment|/**      * Copies the preamble of another BibDatabase.      *      * @param database another BibDatabase      */
DECL|method|copyPreamble (BibDatabase database)
specifier|public
name|void
name|copyPreamble
parameter_list|(
name|BibDatabase
name|database
parameter_list|)
block|{
name|setPreamble
argument_list|(
name|database
operator|.
name|getPreamble
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * Copies all Strings from another BibDatabase.      *      * @param database another BibDatabase      */
DECL|method|copyStrings (BibDatabase database)
specifier|public
name|void
name|copyStrings
parameter_list|(
name|BibDatabase
name|database
parameter_list|)
block|{
for|for
control|(
name|String
name|key
range|:
name|database
operator|.
name|getStringKeySet
argument_list|()
control|)
block|{
name|BibtexString
name|string
init|=
name|database
operator|.
name|getString
argument_list|(
name|key
argument_list|)
decl_stmt|;
name|addString
argument_list|(
name|string
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Returns true if a string with the given label already exists.      */
DECL|method|hasStringLabel (String label)
specifier|public
specifier|synchronized
name|boolean
name|hasStringLabel
parameter_list|(
name|String
name|label
parameter_list|)
block|{
for|for
control|(
name|BibtexString
name|value
range|:
name|bibtexStrings
operator|.
name|values
argument_list|()
control|)
block|{
if|if
condition|(
name|value
operator|.
name|getName
argument_list|()
operator|.
name|equals
argument_list|(
name|label
argument_list|)
condition|)
block|{
return|return
literal|true
return|;
block|}
block|}
return|return
literal|false
return|;
block|}
comment|/**      * Resolves any references to strings contained in this field content,      * if possible.      */
DECL|method|resolveForStrings (String content)
specifier|public
name|String
name|resolveForStrings
parameter_list|(
name|String
name|content
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|content
argument_list|,
literal|"Content for resolveForStrings must not be null."
argument_list|)
expr_stmt|;
return|return
name|resolveContent
argument_list|(
name|content
argument_list|,
operator|new
name|HashSet
argument_list|<>
argument_list|()
argument_list|)
return|;
block|}
comment|/**      * Take the given collection of BibEntry and resolve any string      * references.      *      * @param entries A collection of BibtexEntries in which all strings of the form      *                #xxx# will be resolved against the hash map of string      *                references stored in the database.      * @param inPlace If inPlace is true then the given BibtexEntries will be modified, if false then copies of the BibtexEntries are made before resolving the strings.      * @return a list of bibtexentries, with all strings resolved. It is dependent on the value of inPlace whether copies are made or the given BibtexEntries are modified.      */
DECL|method|resolveForStrings (Collection<BibEntry> entries, boolean inPlace)
specifier|public
name|List
argument_list|<
name|BibEntry
argument_list|>
name|resolveForStrings
parameter_list|(
name|Collection
argument_list|<
name|BibEntry
argument_list|>
name|entries
parameter_list|,
name|boolean
name|inPlace
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|entries
argument_list|,
literal|"entries must not be null."
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|BibEntry
argument_list|>
name|results
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|(
name|entries
operator|.
name|size
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|BibEntry
name|entry
range|:
name|entries
control|)
block|{
name|results
operator|.
name|add
argument_list|(
name|this
operator|.
name|resolveForStrings
argument_list|(
name|entry
argument_list|,
name|inPlace
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|results
return|;
block|}
comment|/**      * Take the given BibEntry and resolve any string references.      *      * @param entry   A BibEntry in which all strings of the form #xxx# will be      *                resolved against the hash map of string references stored in      *                the database.      * @param inPlace If inPlace is true then the given BibEntry will be      *                modified, if false then a copy is made using close made before      *                resolving the strings.      * @return a BibEntry with all string references resolved. It is      * dependent on the value of inPlace whether a copy is made or the      * given BibtexEntries is modified.      */
DECL|method|resolveForStrings (BibEntry entry, boolean inPlace)
specifier|public
name|BibEntry
name|resolveForStrings
parameter_list|(
name|BibEntry
name|entry
parameter_list|,
name|boolean
name|inPlace
parameter_list|)
block|{
name|BibEntry
name|resultingEntry
decl_stmt|;
if|if
condition|(
name|inPlace
condition|)
block|{
name|resultingEntry
operator|=
name|entry
expr_stmt|;
block|}
else|else
block|{
name|resultingEntry
operator|=
operator|(
name|BibEntry
operator|)
name|entry
operator|.
name|clone
argument_list|()
expr_stmt|;
block|}
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
name|field
range|:
name|resultingEntry
operator|.
name|getFieldMap
argument_list|()
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|resultingEntry
operator|.
name|setField
argument_list|(
name|field
operator|.
name|getKey
argument_list|()
argument_list|,
name|this
operator|.
name|resolveForStrings
argument_list|(
name|field
operator|.
name|getValue
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|resultingEntry
return|;
block|}
comment|/**      * If the label represents a string contained in this database, returns      * that string's content. Resolves references to other strings, taking      * care not to follow a circular reference pattern.      * If the string is undefined, returns null.      */
DECL|method|resolveString (String label, Set<String> usedIds)
specifier|private
name|String
name|resolveString
parameter_list|(
name|String
name|label
parameter_list|,
name|Set
argument_list|<
name|String
argument_list|>
name|usedIds
parameter_list|)
block|{
for|for
control|(
name|BibtexString
name|string
range|:
name|bibtexStrings
operator|.
name|values
argument_list|()
control|)
block|{
if|if
condition|(
name|string
operator|.
name|getName
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
name|label
argument_list|)
condition|)
block|{
comment|// First check if this string label has been resolved
comment|// earlier in this recursion. If so, we have a
comment|// circular reference, and have to stop to avoid
comment|// infinite recursion.
if|if
condition|(
name|usedIds
operator|.
name|contains
argument_list|(
name|string
operator|.
name|getId
argument_list|()
argument_list|)
condition|)
block|{
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Stopped due to circular reference in strings: "
operator|+
name|label
argument_list|)
expr_stmt|;
return|return
name|label
return|;
block|}
comment|// If not, log this string's ID now.
name|usedIds
operator|.
name|add
argument_list|(
name|string
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
comment|// Ok, we found the string. Now we must make sure we
comment|// resolve any references to other strings in this one.
name|String
name|result
init|=
name|string
operator|.
name|getContent
argument_list|()
decl_stmt|;
name|result
operator|=
name|resolveContent
argument_list|(
name|result
argument_list|,
name|usedIds
argument_list|)
expr_stmt|;
comment|// Finished with recursing this branch, so we remove our
comment|// ID again:
name|usedIds
operator|.
name|remove
argument_list|(
name|string
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|result
return|;
block|}
block|}
comment|// If we get to this point, the string has obviously not been defined locally.
comment|// Check if one of the standard BibTeX month strings has been used:
name|MonthUtil
operator|.
name|Month
name|month
init|=
name|MonthUtil
operator|.
name|getMonthByShortName
argument_list|(
name|label
argument_list|)
decl_stmt|;
if|if
condition|(
name|month
operator|.
name|isValid
argument_list|()
condition|)
block|{
return|return
name|month
operator|.
name|fullName
return|;
block|}
else|else
block|{
return|return
literal|null
return|;
block|}
block|}
DECL|field|RESOLVE_CONTENT_PATTERN
specifier|private
specifier|static
specifier|final
name|Pattern
name|RESOLVE_CONTENT_PATTERN
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|".*#[^#]+#.*"
argument_list|)
decl_stmt|;
DECL|method|resolveContent (String result, Set<String> usedIds)
specifier|private
name|String
name|resolveContent
parameter_list|(
name|String
name|result
parameter_list|,
name|Set
argument_list|<
name|String
argument_list|>
name|usedIds
parameter_list|)
block|{
name|String
name|res
init|=
name|result
decl_stmt|;
if|if
condition|(
name|RESOLVE_CONTENT_PATTERN
operator|.
name|matcher
argument_list|(
name|res
argument_list|)
operator|.
name|matches
argument_list|()
condition|)
block|{
name|StringBuilder
name|newRes
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|int
name|piv
init|=
literal|0
decl_stmt|;
name|int
name|next
decl_stmt|;
while|while
condition|(
operator|(
name|next
operator|=
name|res
operator|.
name|indexOf
argument_list|(
literal|'#'
argument_list|,
name|piv
argument_list|)
operator|)
operator|>=
literal|0
condition|)
block|{
comment|// We found the next string ref. Append the text
comment|// up to it.
if|if
condition|(
name|next
operator|>
literal|0
condition|)
block|{
name|newRes
operator|.
name|append
argument_list|(
name|res
operator|.
name|substring
argument_list|(
name|piv
argument_list|,
name|next
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|int
name|stringEnd
init|=
name|res
operator|.
name|indexOf
argument_list|(
literal|'#'
argument_list|,
name|next
operator|+
literal|1
argument_list|)
decl_stmt|;
if|if
condition|(
name|stringEnd
operator|>=
literal|0
condition|)
block|{
comment|// We found the boundaries of the string ref,
comment|// now resolve that one.
name|String
name|refLabel
init|=
name|res
operator|.
name|substring
argument_list|(
name|next
operator|+
literal|1
argument_list|,
name|stringEnd
argument_list|)
decl_stmt|;
name|String
name|resolved
init|=
name|resolveString
argument_list|(
name|refLabel
argument_list|,
name|usedIds
argument_list|)
decl_stmt|;
if|if
condition|(
name|resolved
operator|==
literal|null
condition|)
block|{
comment|// Could not resolve string. Display the #
comment|// characters rather than removing them:
name|newRes
operator|.
name|append
argument_list|(
name|res
operator|.
name|substring
argument_list|(
name|next
argument_list|,
name|stringEnd
operator|+
literal|1
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// The string was resolved, so we display its meaning only,
comment|// stripping the # characters signifying the string label:
name|newRes
operator|.
name|append
argument_list|(
name|resolved
argument_list|)
expr_stmt|;
block|}
name|piv
operator|=
name|stringEnd
operator|+
literal|1
expr_stmt|;
block|}
else|else
block|{
comment|// We did not find the boundaries of the string ref. This
comment|// makes it impossible to interpret it as a string label.
comment|// So we should just append the rest of the text and finish.
name|newRes
operator|.
name|append
argument_list|(
name|res
operator|.
name|substring
argument_list|(
name|next
argument_list|)
argument_list|)
expr_stmt|;
name|piv
operator|=
name|res
operator|.
name|length
argument_list|()
expr_stmt|;
break|break;
block|}
block|}
if|if
condition|(
name|piv
operator|<
operator|(
name|res
operator|.
name|length
argument_list|()
operator|-
literal|1
operator|)
condition|)
block|{
name|newRes
operator|.
name|append
argument_list|(
name|res
operator|.
name|substring
argument_list|(
name|piv
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|res
operator|=
name|newRes
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
return|return
name|res
return|;
block|}
comment|/**      * Returns the text stored in the given field of the given bibtex entry      * which belongs to the given database.      *<p>      * If a database is given, this function will try to resolve any string      * references in the field-value.      * Also, if a database is given, this function will try to find values for      * unset fields in the entry linked by the "crossref" field, if any.      *      * @param field    The field to return the value of.      * @param entry    The bibtex entry which contains the field.      * @param database maybenull      *                 The database of the bibtex entry.      * @return The resolved field value or null if not found.      */
DECL|method|getResolvedField (String field, BibEntry entry, BibDatabase database)
specifier|public
specifier|static
name|Optional
argument_list|<
name|String
argument_list|>
name|getResolvedField
parameter_list|(
name|String
name|field
parameter_list|,
name|BibEntry
name|entry
parameter_list|,
name|BibDatabase
name|database
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|entry
argument_list|,
literal|"entry cannot be null"
argument_list|)
expr_stmt|;
if|if
condition|(
literal|"bibtextype"
operator|.
name|equals
argument_list|(
name|field
argument_list|)
condition|)
block|{
return|return
name|Optional
operator|.
name|of
argument_list|(
name|EntryUtil
operator|.
name|capitalizeFirst
argument_list|(
name|entry
operator|.
name|getType
argument_list|()
argument_list|)
argument_list|)
return|;
block|}
comment|// TODO: Changed this to also consider alias fields, which is the expected
comment|// behavior for the preview layout and for the check whatever all fields are present.
comment|// But there might be unwanted side-effects?!
name|Optional
argument_list|<
name|String
argument_list|>
name|result
init|=
name|entry
operator|.
name|getFieldOrAlias
argument_list|(
name|field
argument_list|)
decl_stmt|;
comment|// If this field is not set, and the entry has a crossref, try to look up the
comment|// field in the referred entry: Do not do this for the bibtex key.
if|if
condition|(
operator|!
name|result
operator|.
name|isPresent
argument_list|()
operator|&&
operator|(
name|database
operator|!=
literal|null
operator|)
operator|&&
operator|!
name|field
operator|.
name|equals
argument_list|(
name|BibEntry
operator|.
name|KEY_FIELD
argument_list|)
condition|)
block|{
name|Optional
argument_list|<
name|String
argument_list|>
name|crossrefKey
init|=
name|entry
operator|.
name|getFieldOptional
argument_list|(
name|FieldName
operator|.
name|CROSSREF
argument_list|)
decl_stmt|;
if|if
condition|(
name|crossrefKey
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|Optional
argument_list|<
name|BibEntry
argument_list|>
name|referred
init|=
name|database
operator|.
name|getEntryByKey
argument_list|(
name|crossrefKey
operator|.
name|get
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|referred
operator|.
name|isPresent
argument_list|()
condition|)
block|{
comment|// Ok, we found the referred entry. Get the field value from that
comment|// entry. If it is unset there, too, stop looking:
name|result
operator|=
name|referred
operator|.
name|get
argument_list|()
operator|.
name|getFieldOptional
argument_list|(
name|field
argument_list|)
expr_stmt|;
block|}
block|}
block|}
return|return
name|result
operator|.
name|map
argument_list|(
name|resultText
lambda|->
name|BibDatabase
operator|.
name|getText
argument_list|(
name|resultText
argument_list|,
name|database
argument_list|)
argument_list|)
return|;
block|}
comment|/**      * Returns a text with references resolved according to an optionally given database.      *      * @param toResolve maybenull The text to resolve.      * @param database  maybenull The database to use for resolving the text.      * @return The resolved text or the original text if either the text or the database are null      */
DECL|method|getText (String toResolve, BibDatabase database)
specifier|public
specifier|static
name|String
name|getText
parameter_list|(
name|String
name|toResolve
parameter_list|,
name|BibDatabase
name|database
parameter_list|)
block|{
if|if
condition|(
operator|(
name|toResolve
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|database
operator|!=
literal|null
operator|)
condition|)
block|{
return|return
name|database
operator|.
name|resolveForStrings
argument_list|(
name|toResolve
argument_list|)
return|;
block|}
return|return
name|toResolve
return|;
block|}
DECL|method|setEpilog (String epilog)
specifier|public
name|void
name|setEpilog
parameter_list|(
name|String
name|epilog
parameter_list|)
block|{
name|this
operator|.
name|epilog
operator|=
name|epilog
expr_stmt|;
block|}
DECL|method|getEpilog ()
specifier|public
name|String
name|getEpilog
parameter_list|()
block|{
return|return
name|epilog
return|;
block|}
comment|/**      * Registers an listener object (subscriber) to the internal event bus.      * The following events are posted:      *      *   - {@link EntryAddedEvent}      *   - {@link EntryChangedEvent}      *   - {@link EntryRemovedEvent}      *      * @param listener listener (subscriber) to add      */
DECL|method|registerListener (Object listener)
specifier|public
name|void
name|registerListener
parameter_list|(
name|Object
name|listener
parameter_list|)
block|{
name|this
operator|.
name|eventBus
operator|.
name|register
argument_list|(
name|listener
argument_list|)
expr_stmt|;
block|}
comment|/**      * Unregisters an listener object.      * @param listener listener (subscriber) to remove      */
DECL|method|unregisterListener (Object listener)
specifier|public
name|void
name|unregisterListener
parameter_list|(
name|Object
name|listener
parameter_list|)
block|{
name|this
operator|.
name|eventBus
operator|.
name|unregister
argument_list|(
name|listener
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Subscribe
DECL|method|relayEntryChangeEvent (FieldChangedEvent event)
specifier|private
name|void
name|relayEntryChangeEvent
parameter_list|(
name|FieldChangedEvent
name|event
parameter_list|)
block|{
name|eventBus
operator|.
name|post
argument_list|(
name|event
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

