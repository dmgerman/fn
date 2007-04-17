begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* Copyright (C) 2003 David Weitzman, Morten O. Alver  All programs in this directory and subdirectories are published under the GNU General Public License as described below.  This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.  You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA  Further information about the GNU GPL is available at: http://www.gnu.org/copyleft/gpl.ja.html  Note: Modified for use in JabRef  */
end_comment

begin_comment
comment|// created by : ?
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|// modified : r.nagel 23.08.2004
end_comment

begin_comment
comment|//                - insert getEntryByKey() methode needed by AuxSubGenerator
end_comment

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
name|java
operator|.
name|beans
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
name|*
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JOptionPane
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
name|groups
operator|.
name|GroupSelector
import|;
end_import

begin_class
DECL|class|BibtexDatabase
specifier|public
class|class
name|BibtexDatabase
block|{
DECL|field|_entries
name|Map
name|_entries
init|=
operator|new
name|Hashtable
argument_list|()
decl_stmt|;
DECL|field|_preamble
name|String
name|_preamble
init|=
literal|null
decl_stmt|;
DECL|field|_strings
name|HashMap
name|_strings
init|=
operator|new
name|HashMap
argument_list|()
decl_stmt|;
DECL|field|_strings_
name|Vector
name|_strings_
init|=
operator|new
name|Vector
argument_list|()
decl_stmt|;
DECL|field|changeListeners
name|Set
name|changeListeners
init|=
operator|new
name|HashSet
argument_list|()
decl_stmt|;
DECL|field|ths
specifier|private
name|BibtexDatabase
name|ths
init|=
name|this
decl_stmt|;
DECL|field|allKeys
specifier|private
name|HashMap
name|allKeys
init|=
operator|new
name|HashMap
argument_list|()
decl_stmt|;
comment|// use a map instead of a set since i need to know how many of each key is inthere
comment|/* Entries are stored in a HashMap with the ID as key.      * What happens if someone changes a BibtexEntry's ID      * after it has been added to this BibtexDatabase?      * The key of that entry would be the old ID, not the new one.      * Use a PropertyChangeListener to identify an ID change      * and update the Map.      */
DECL|field|listener
specifier|private
specifier|final
name|VetoableChangeListener
name|listener
init|=
operator|new
name|VetoableChangeListener
argument_list|()
block|{
specifier|public
name|void
name|vetoableChange
parameter_list|(
name|PropertyChangeEvent
name|pce
parameter_list|)
throws|throws
name|PropertyVetoException
block|{
if|if
condition|(
name|pce
operator|.
name|getPropertyName
argument_list|()
operator|==
literal|null
condition|)
name|fireDatabaseChanged
argument_list|(
operator|new
name|DatabaseChangeEvent
argument_list|(
name|ths
argument_list|,
name|DatabaseChangeEvent
operator|.
name|CHANGING_ENTRY
argument_list|,
operator|(
name|BibtexEntry
operator|)
name|pce
operator|.
name|getSource
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
literal|"id"
operator|.
name|equals
argument_list|(
name|pce
operator|.
name|getPropertyName
argument_list|()
argument_list|)
condition|)
block|{
comment|// locate the entry under its old key
name|Object
name|oldEntry
init|=
name|_entries
operator|.
name|remove
argument_list|(
operator|(
name|String
operator|)
name|pce
operator|.
name|getOldValue
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|oldEntry
operator|!=
name|pce
operator|.
name|getSource
argument_list|()
condition|)
block|{
comment|// Something is very wrong!
comment|// The entry under the old key isn't
comment|// the one that sent this event.
comment|// Restore the old state.
name|_entries
operator|.
name|put
argument_list|(
name|pce
operator|.
name|getOldValue
argument_list|()
argument_list|,
name|oldEntry
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|PropertyVetoException
argument_list|(
literal|"Wrong old ID"
argument_list|,
name|pce
argument_list|)
throw|;
block|}
if|if
condition|(
name|_entries
operator|.
name|get
argument_list|(
name|pce
operator|.
name|getNewValue
argument_list|()
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|_entries
operator|.
name|put
argument_list|(
name|pce
operator|.
name|getOldValue
argument_list|()
argument_list|,
name|oldEntry
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|PropertyVetoException
argument_list|(
literal|"New ID already in use, please choose another"
argument_list|,
name|pce
argument_list|)
throw|;
block|}
comment|// and re-file this entry
name|_entries
operator|.
name|put
argument_list|(
operator|(
name|String
operator|)
name|pce
operator|.
name|getNewValue
argument_list|()
argument_list|,
operator|(
name|BibtexEntry
operator|)
name|pce
operator|.
name|getSource
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|fireDatabaseChanged
argument_list|(
operator|new
name|DatabaseChangeEvent
argument_list|(
name|ths
argument_list|,
name|DatabaseChangeEvent
operator|.
name|CHANGED_ENTRY
argument_list|,
operator|(
name|BibtexEntry
operator|)
name|pce
operator|.
name|getSource
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
comment|//Util.pr(pce.getSource().toString()+"\n"+pce.getPropertyName()
comment|//    +"\n"+pce.getNewValue());
block|}
block|}
block|}
decl_stmt|;
comment|/**      * Returns the number of entries.      */
DECL|method|getEntryCount ()
specifier|public
specifier|synchronized
name|int
name|getEntryCount
parameter_list|()
block|{
return|return
name|_entries
operator|.
name|size
argument_list|()
return|;
block|}
comment|/**      * Returns a Set containing the keys to all entries.      * Use getKeySet().iterator() to iterate over all entries.      */
DECL|method|getKeySet ()
specifier|public
specifier|synchronized
name|Set
name|getKeySet
parameter_list|()
block|{
return|return
name|_entries
operator|.
name|keySet
argument_list|()
return|;
block|}
comment|/**      * Returns an EntrySorter with the sorted entries from this base,      * sorted by the given Comparator.      */
DECL|method|getSorter (java.util.Comparator comp)
specifier|public
specifier|synchronized
name|EntrySorter
name|getSorter
parameter_list|(
name|java
operator|.
name|util
operator|.
name|Comparator
name|comp
parameter_list|)
block|{
name|EntrySorter
name|sorter
init|=
operator|new
name|EntrySorter
argument_list|(
name|_entries
argument_list|,
name|comp
argument_list|)
decl_stmt|;
name|addDatabaseChangeListener
argument_list|(
name|sorter
argument_list|)
expr_stmt|;
return|return
name|sorter
return|;
block|}
comment|/**      * Just temporary, for testing purposes....      * @return      */
DECL|method|getEntryMap ()
specifier|public
name|Map
name|getEntryMap
parameter_list|()
block|{
return|return
name|_entries
return|;
block|}
comment|/**      * Returns the entry with the given ID (-> entry_type + hashcode).      */
DECL|method|getEntryById (String id)
specifier|public
specifier|synchronized
name|BibtexEntry
name|getEntryById
parameter_list|(
name|String
name|id
parameter_list|)
block|{
return|return
operator|(
name|BibtexEntry
operator|)
name|_entries
operator|.
name|get
argument_list|(
name|id
argument_list|)
return|;
block|}
DECL|method|getEntries ()
specifier|public
specifier|synchronized
name|Collection
name|getEntries
parameter_list|()
block|{
return|return
name|_entries
operator|.
name|values
argument_list|()
return|;
block|}
comment|/**      * Returns the entry with the given bibtex key.      */
DECL|method|getEntryByKey (String key)
specifier|public
specifier|synchronized
name|BibtexEntry
name|getEntryByKey
parameter_list|(
name|String
name|key
parameter_list|)
block|{
name|BibtexEntry
name|back
init|=
literal|null
decl_stmt|;
name|int
name|keyHash
init|=
name|key
operator|.
name|hashCode
argument_list|()
decl_stmt|;
comment|// key hash for better performance
name|Set
name|keySet
init|=
name|_entries
operator|.
name|keySet
argument_list|()
decl_stmt|;
if|if
condition|(
name|keySet
operator|!=
literal|null
condition|)
block|{
name|Iterator
name|it
init|=
name|keySet
operator|.
name|iterator
argument_list|()
decl_stmt|;
name|boolean
name|loop
init|=
name|it
operator|.
name|hasNext
argument_list|()
decl_stmt|;
while|while
condition|(
name|loop
condition|)
block|{
name|String
name|entrieID
init|=
operator|(
name|String
operator|)
name|it
operator|.
name|next
argument_list|()
decl_stmt|;
name|BibtexEntry
name|entry
init|=
name|getEntryById
argument_list|(
name|entrieID
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|entry
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|entry
operator|.
name|getCiteKey
argument_list|()
operator|!=
literal|null
operator|)
condition|)
block|{
name|String
name|citeKey
init|=
name|entry
operator|.
name|getCiteKey
argument_list|()
decl_stmt|;
if|if
condition|(
name|citeKey
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|keyHash
operator|==
name|citeKey
operator|.
name|hashCode
argument_list|()
condition|)
block|{
name|loop
operator|=
literal|false
expr_stmt|;
name|back
operator|=
name|entry
expr_stmt|;
block|}
else|else
name|loop
operator|=
name|it
operator|.
name|hasNext
argument_list|()
expr_stmt|;
block|}
else|else
name|loop
operator|=
name|it
operator|.
name|hasNext
argument_list|()
expr_stmt|;
block|}
block|}
block|}
return|return
name|back
return|;
block|}
DECL|method|getEntriesByKey (String key)
specifier|public
specifier|synchronized
name|BibtexEntry
index|[]
name|getEntriesByKey
parameter_list|(
name|String
name|key
parameter_list|)
block|{
name|Vector
name|entries
init|=
operator|new
name|Vector
argument_list|()
decl_stmt|;
name|BibtexEntry
name|entry
decl_stmt|;
for|for
control|(
name|Iterator
name|it
init|=
name|_entries
operator|.
name|entrySet
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|it
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|entry
operator|=
call|(
name|BibtexEntry
call|)
argument_list|(
operator|(
name|Map
operator|.
name|Entry
operator|)
name|it
operator|.
name|next
argument_list|()
argument_list|)
operator|.
name|getValue
argument_list|()
expr_stmt|;
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
name|entries
operator|.
name|add
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
name|BibtexEntry
index|[]
name|entryArray
init|=
operator|new
name|BibtexEntry
index|[
name|entries
operator|.
name|size
argument_list|()
index|]
decl_stmt|;
return|return
operator|(
name|BibtexEntry
index|[]
operator|)
name|entries
operator|.
name|toArray
argument_list|(
name|entryArray
argument_list|)
return|;
block|}
comment|/**      * Inserts the entry, given that its ID is not already in use.      * use Util.createId(...) to make up a unique ID for an entry.      */
DECL|method|insertEntry (BibtexEntry entry)
specifier|public
specifier|synchronized
name|boolean
name|insertEntry
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|)
throws|throws
name|KeyCollisionException
block|{
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
name|getEntryById
argument_list|(
name|id
argument_list|)
operator|!=
literal|null
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
name|entry
operator|.
name|addPropertyChangeListener
argument_list|(
name|listener
argument_list|)
expr_stmt|;
name|_entries
operator|.
name|put
argument_list|(
name|id
argument_list|,
name|entry
argument_list|)
expr_stmt|;
name|fireDatabaseChanged
argument_list|(
operator|new
name|DatabaseChangeEvent
argument_list|(
name|this
argument_list|,
name|DatabaseChangeEvent
operator|.
name|ADDED_ENTRY
argument_list|,
name|entry
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|checkForDuplicateKeyAndAdd
argument_list|(
literal|null
argument_list|,
name|entry
operator|.
name|getCiteKey
argument_list|()
argument_list|,
literal|false
argument_list|)
return|;
block|}
comment|/**      * Removes the entry with the given string.      */
DECL|method|removeEntry (String id)
specifier|public
specifier|synchronized
name|BibtexEntry
name|removeEntry
parameter_list|(
name|String
name|id
parameter_list|)
block|{
name|BibtexEntry
name|oldValue
init|=
operator|(
name|BibtexEntry
operator|)
name|_entries
operator|.
name|remove
argument_list|(
name|id
argument_list|)
decl_stmt|;
name|removeKeyFromSet
argument_list|(
name|oldValue
operator|.
name|getCiteKey
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|oldValue
operator|!=
literal|null
condition|)
block|{
name|oldValue
operator|.
name|removePropertyChangeListener
argument_list|(
name|listener
argument_list|)
expr_stmt|;
block|}
name|fireDatabaseChanged
argument_list|(
operator|new
name|DatabaseChangeEvent
argument_list|(
name|this
argument_list|,
name|DatabaseChangeEvent
operator|.
name|REMOVED_ENTRY
argument_list|,
name|oldValue
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|oldValue
return|;
block|}
DECL|method|setCiteKeyForEntry (String id, String key)
specifier|public
specifier|synchronized
name|boolean
name|setCiteKeyForEntry
parameter_list|(
name|String
name|id
parameter_list|,
name|String
name|key
parameter_list|)
block|{
if|if
condition|(
operator|!
name|_entries
operator|.
name|containsKey
argument_list|(
name|id
argument_list|)
condition|)
return|return
literal|false
return|;
comment|// Entry doesn't exist!
name|BibtexEntry
name|entry
init|=
name|getEntryById
argument_list|(
name|id
argument_list|)
decl_stmt|;
name|String
name|oldKey
init|=
name|entry
operator|.
name|getCiteKey
argument_list|()
decl_stmt|;
if|if
condition|(
name|key
operator|!=
literal|null
condition|)
name|entry
operator|.
name|setField
argument_list|(
name|BibtexFields
operator|.
name|KEY_FIELD
argument_list|,
name|key
argument_list|)
expr_stmt|;
else|else
name|entry
operator|.
name|clearField
argument_list|(
name|BibtexFields
operator|.
name|KEY_FIELD
argument_list|)
expr_stmt|;
return|return
name|checkForDuplicateKeyAndAdd
argument_list|(
name|oldKey
argument_list|,
name|entry
operator|.
name|getCiteKey
argument_list|()
argument_list|,
literal|false
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
name|_preamble
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
name|_preamble
return|;
block|}
comment|/**      * Inserts a Bibtex String at the given index.      */
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
for|for
control|(
name|java
operator|.
name|util
operator|.
name|Iterator
name|i
init|=
name|_strings
operator|.
name|keySet
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
if|if
condition|(
operator|(
operator|(
name|BibtexString
operator|)
name|_strings
operator|.
name|get
argument_list|(
name|i
operator|.
name|next
argument_list|()
argument_list|)
operator|)
operator|.
name|getName
argument_list|()
operator|.
name|equals
argument_list|(
name|string
operator|.
name|getName
argument_list|()
argument_list|)
condition|)
throw|throw
operator|new
name|KeyCollisionException
argument_list|(
literal|"A string with this label already exists,"
argument_list|)
throw|;
block|}
if|if
condition|(
name|_strings
operator|.
name|containsKey
argument_list|(
name|string
operator|.
name|getId
argument_list|()
argument_list|)
condition|)
throw|throw
operator|new
name|KeyCollisionException
argument_list|(
literal|"Duplicate BibtexString id."
argument_list|)
throw|;
name|_strings
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
comment|/**      * Removes the string at the given index.      */
DECL|method|removeString (String id)
specifier|public
specifier|synchronized
name|void
name|removeString
parameter_list|(
name|String
name|id
parameter_list|)
block|{
name|_strings
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
name|getStringKeySet
parameter_list|()
block|{
return|return
name|_strings
operator|.
name|keySet
argument_list|()
return|;
block|}
comment|/**      * Returns the string at the given index.      */
DECL|method|getString (Object o)
specifier|public
specifier|synchronized
name|BibtexString
name|getString
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
return|return
call|(
name|BibtexString
call|)
argument_list|(
name|_strings
operator|.
name|get
argument_list|(
name|o
argument_list|)
argument_list|)
return|;
block|}
comment|/**      * Returns the number of strings.      */
DECL|method|getStringCount ()
specifier|public
specifier|synchronized
name|int
name|getStringCount
parameter_list|()
block|{
return|return
name|_strings
operator|.
name|size
argument_list|()
return|;
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
name|java
operator|.
name|util
operator|.
name|Iterator
name|i
init|=
name|_strings
operator|.
name|keySet
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
if|if
condition|(
operator|(
operator|(
name|BibtexString
operator|)
name|_strings
operator|.
name|get
argument_list|(
name|i
operator|.
name|next
argument_list|()
argument_list|)
operator|)
operator|.
name|getName
argument_list|()
operator|.
name|equals
argument_list|(
name|label
argument_list|)
condition|)
return|return
literal|true
return|;
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
return|return
name|resolveContent
argument_list|(
name|content
argument_list|,
operator|new
name|HashSet
argument_list|()
argument_list|)
return|;
block|}
comment|/**     * If the label represents a string contained in this database, returns     * that string's content. Resolves references to other strings, taking     * care not to follow a circular reference pattern.     * If the string is undefined, returns the label itself.     */
DECL|method|resolveString (String label, HashSet usedIds)
specifier|private
name|String
name|resolveString
parameter_list|(
name|String
name|label
parameter_list|,
name|HashSet
name|usedIds
parameter_list|)
block|{
for|for
control|(
name|java
operator|.
name|util
operator|.
name|Iterator
name|i
init|=
name|_strings
operator|.
name|keySet
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|BibtexString
name|string
init|=
operator|(
name|BibtexString
operator|)
name|_strings
operator|.
name|get
argument_list|(
name|i
operator|.
name|next
argument_list|()
argument_list|)
decl_stmt|;
comment|//Util.pr(label+" : "+string.getName());
if|if
condition|(
name|string
operator|.
name|getName
argument_list|()
operator|.
name|toLowerCase
argument_list|()
operator|.
name|equals
argument_list|(
name|label
operator|.
name|toLowerCase
argument_list|()
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
name|Util
operator|.
name|pr
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
name|res
init|=
name|string
operator|.
name|getContent
argument_list|()
decl_stmt|;
name|res
operator|=
name|resolveContent
argument_list|(
name|res
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
name|res
return|;
block|}
block|}
comment|// If we get to this point, the string has obviously not been defined locally.
comment|// Check if one of the standard BibTeX month strings has been used:
name|Object
name|o
decl_stmt|;
if|if
condition|(
operator|(
name|o
operator|=
name|Globals
operator|.
name|MONTH_STRINGS
operator|.
name|get
argument_list|(
name|label
operator|.
name|toLowerCase
argument_list|()
argument_list|)
operator|)
operator|!=
literal|null
condition|)
block|{
return|return
operator|(
name|String
operator|)
name|o
return|;
block|}
return|return
name|label
return|;
block|}
DECL|method|resolveContent (String res, HashSet usedIds)
specifier|private
name|String
name|resolveContent
parameter_list|(
name|String
name|res
parameter_list|,
name|HashSet
name|usedIds
parameter_list|)
block|{
comment|//if (res.matches(".*#[-\\^\\:\\w]+#.*")) {
if|if
condition|(
name|res
operator|.
name|matches
argument_list|(
literal|".*#[^#]+#.*"
argument_list|)
condition|)
block|{
name|StringBuffer
name|newRes
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|int
name|piv
init|=
literal|0
decl_stmt|,
name|next
init|=
literal|0
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
literal|"#"
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
name|int
name|stringEnd
init|=
name|res
operator|.
name|indexOf
argument_list|(
literal|"#"
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
name|refLabel
operator|.
name|equals
argument_list|(
name|resolved
argument_list|)
condition|)
block|{
comment|// We got just the label in return, so this may not have
comment|// been intended as a string label, or it may be a label for
comment|// an undefined string. Therefore we prefer to display the #
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
comment|// The string was resolved, so we display its meaning only,
comment|// stripping the # characters signifying the string label:
name|newRes
operator|.
name|append
argument_list|(
name|resolved
argument_list|)
expr_stmt|;
name|piv
operator|=
name|stringEnd
operator|+
literal|1
expr_stmt|;
block|}
else|else
block|{
comment|// We didn't find the boundaries of the string ref. This
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
name|res
operator|.
name|length
argument_list|()
operator|-
literal|1
condition|)
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
comment|//##########################################
comment|//  usage:
comment|//  isDuplicate=checkForDuplicateKeyAndAdd( null, b.getKey() , issueDuplicateWarning);
comment|//############################################
comment|// if the newkey already exists and is not the same as oldkey it will give a warning
comment|// else it will add the newkey to the to set and remove the oldkey
DECL|method|checkForDuplicateKeyAndAdd (String oldKey, String newKey, boolean issueWarning)
specifier|public
name|boolean
name|checkForDuplicateKeyAndAdd
parameter_list|(
name|String
name|oldKey
parameter_list|,
name|String
name|newKey
parameter_list|,
name|boolean
name|issueWarning
parameter_list|)
block|{
comment|// Globals.logger(" checkForDuplicateKeyAndAdd [oldKey = " + oldKey + "] [newKey = " + newKey + "]");
name|boolean
name|duplicate
init|=
literal|false
decl_stmt|;
if|if
condition|(
name|oldKey
operator|==
literal|null
condition|)
block|{
comment|// this is a new entry so don't bother removing oldKey
name|duplicate
operator|=
name|addKeyToSet
argument_list|(
name|newKey
argument_list|)
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|oldKey
operator|.
name|equals
argument_list|(
name|newKey
argument_list|)
condition|)
block|{
comment|// were OK because the user did not change keys
name|duplicate
operator|=
literal|false
expr_stmt|;
block|}
else|else
block|{
comment|// user changed the key
comment|// removed the oldkey
comment|// But what if more than two have the same key?
comment|// this means that user can add another key and would not get a warning!
comment|// consider this: i add a key xxx, then i add another key xxx . I get a warning. I delete the key xxx. JBM
comment|// removes this key from the allKey. then I add another key xxx. I don't get a warning!
comment|// i need a way to count the number of keys of each type
comment|// hashmap=>int (increment each time)
name|removeKeyFromSet
argument_list|(
name|oldKey
argument_list|)
expr_stmt|;
name|duplicate
operator|=
name|addKeyToSet
argument_list|(
name|newKey
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|duplicate
operator|==
literal|true
operator|&&
name|issueWarning
operator|==
literal|true
condition|)
block|{
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
literal|null
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Warning there is a duplicate key"
argument_list|)
operator|+
literal|":"
operator|+
name|newKey
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Duplicate Key Warning"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|WARNING_MESSAGE
argument_list|)
expr_stmt|;
comment|//, options);
block|}
return|return
name|duplicate
return|;
block|}
comment|/**      * Returns the number of occurences of the given key in this database.      */
DECL|method|getNumberOfKeyOccurences (String key)
specifier|public
name|int
name|getNumberOfKeyOccurences
parameter_list|(
name|String
name|key
parameter_list|)
block|{
name|Object
name|o
init|=
name|allKeys
operator|.
name|get
argument_list|(
name|key
argument_list|)
decl_stmt|;
if|if
condition|(
name|o
operator|==
literal|null
condition|)
return|return
literal|0
return|;
else|else
return|return
operator|(
operator|(
name|Integer
operator|)
name|o
operator|)
operator|.
name|intValue
argument_list|()
return|;
block|}
comment|//========================================================
comment|// keep track of all the keys to warn if there are duplicates
comment|//========================================================
DECL|method|addKeyToSet (String key)
specifier|private
name|boolean
name|addKeyToSet
parameter_list|(
name|String
name|key
parameter_list|)
block|{
name|boolean
name|exists
init|=
literal|false
decl_stmt|;
if|if
condition|(
operator|(
name|key
operator|==
literal|null
operator|)
operator|||
name|key
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
return|return
literal|false
return|;
comment|//don't put empty key
if|if
condition|(
name|allKeys
operator|.
name|containsKey
argument_list|(
name|key
argument_list|)
condition|)
block|{
comment|// warning
name|exists
operator|=
literal|true
expr_stmt|;
name|allKeys
operator|.
name|put
argument_list|(
name|key
argument_list|,
operator|new
name|Integer
argument_list|(
operator|(
operator|(
name|Integer
operator|)
name|allKeys
operator|.
name|get
argument_list|(
name|key
argument_list|)
operator|)
operator|.
name|intValue
argument_list|()
operator|+
literal|1
argument_list|)
argument_list|)
expr_stmt|;
comment|// incrementInteger( allKeys.get(key)));
block|}
else|else
name|allKeys
operator|.
name|put
argument_list|(
name|key
argument_list|,
operator|new
name|Integer
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|exists
return|;
block|}
comment|//========================================================
comment|// reduce the number of keys by 1. if this number goes to zero then remove from the set
comment|// note: there is a good reason why we should not use a hashset but use hashmap instead
comment|//========================================================
DECL|method|removeKeyFromSet (String key)
specifier|private
name|void
name|removeKeyFromSet
parameter_list|(
name|String
name|key
parameter_list|)
block|{
if|if
condition|(
operator|(
name|key
operator|==
literal|null
operator|)
operator|||
name|key
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
return|return;
if|if
condition|(
name|allKeys
operator|.
name|containsKey
argument_list|(
name|key
argument_list|)
condition|)
block|{
name|Integer
name|tI
init|=
operator|(
name|Integer
operator|)
name|allKeys
operator|.
name|get
argument_list|(
name|key
argument_list|)
decl_stmt|;
comment|// if(allKeys.get(key) instanceof Integer)
if|if
condition|(
name|tI
operator|.
name|intValue
argument_list|()
operator|==
literal|1
condition|)
name|allKeys
operator|.
name|remove
argument_list|(
name|key
argument_list|)
expr_stmt|;
else|else
name|allKeys
operator|.
name|put
argument_list|(
name|key
argument_list|,
operator|new
name|Integer
argument_list|(
operator|(
operator|(
name|Integer
operator|)
name|tI
operator|)
operator|.
name|intValue
argument_list|()
operator|-
literal|1
argument_list|)
argument_list|)
expr_stmt|;
comment|//decrementInteger( tI ));
block|}
block|}
DECL|method|fireDatabaseChanged (DatabaseChangeEvent e)
specifier|public
name|void
name|fireDatabaseChanged
parameter_list|(
name|DatabaseChangeEvent
name|e
parameter_list|)
block|{
for|for
control|(
name|Iterator
name|i
init|=
name|changeListeners
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
operator|(
operator|(
name|DatabaseChangeListener
operator|)
name|i
operator|.
name|next
argument_list|()
operator|)
operator|.
name|databaseChanged
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|addDatabaseChangeListener (DatabaseChangeListener l)
specifier|public
name|void
name|addDatabaseChangeListener
parameter_list|(
name|DatabaseChangeListener
name|l
parameter_list|)
block|{
name|changeListeners
operator|.
name|add
argument_list|(
name|l
argument_list|)
expr_stmt|;
block|}
DECL|method|removeDatabaseChangeListener (DatabaseChangeListener l)
specifier|public
name|void
name|removeDatabaseChangeListener
parameter_list|(
name|DatabaseChangeListener
name|l
parameter_list|)
block|{
name|changeListeners
operator|.
name|remove
argument_list|(
name|l
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

