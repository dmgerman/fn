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
name|PropertyChangeEvent
import|;
end_import

begin_import
import|import
name|java
operator|.
name|beans
operator|.
name|PropertyVetoException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|beans
operator|.
name|VetoableChangeListener
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
name|HashMap
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
DECL|field|_autoCompleters
name|Hashtable
name|_autoCompleters
init|=
literal|null
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
return|return
operator|new
name|EntrySorter
argument_list|(
name|_entries
argument_list|,
name|comp
argument_list|)
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
comment|// Possibly add a FieldChangeListener, which is there to add
comment|// new words to the autocompleter's dictionary. In case the
comment|// entry is non-empty (pasted), update completers.
comment|/*if (_autoCompleters != null) {             entry.addPropertyChangeListener(new FieldChangeListener                                             (_autoCompleters, entry));             Util.updateCompletersForEntry(_autoCompleters,                                           entry);         }         */
name|_entries
operator|.
name|put
argument_list|(
name|id
argument_list|,
name|entry
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
name|Globals
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
name|Globals
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
else|else
comment|// ignore, as there is no such key
empty_stmt|;
block|}
comment|/*     public void setCompleters(Hashtable autoCompleters) {         _autoCompleters = autoCompleters;          for (Iterator i=getKeySet().iterator(); i.hasNext();) {             BibtexEntry be = getEntryById((String)(i.next()));             be.addPropertyChangeListener(new FieldChangeListener                                          (autoCompleters, be));              Util.updateCompletersForEntry(autoCompleters, be);         }         }*/
block|}
end_class

end_unit

