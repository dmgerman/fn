begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* Copyright (C) 2003 David Weitzman, Morten O. Alver  All programs in this directory and subdirectories are published under the GNU General Public License as described below.  This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.  You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA  Further information about the GNU GPL is available at: http://www.gnu.org/copyleft/gpl.ja.html  Note: Modified for use in JabRef  */
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
name|Vector
name|_strings
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
comment|/**      * Returns the entry with the given ID.      */
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
comment|/**      * Inserts the entry, given that its ID is not already in use.      * use Util.createId(...) to make up a unique ID for an entry.      */
DECL|method|insertEntry (BibtexEntry entry)
specifier|public
specifier|synchronized
name|void
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
comment|/*if (_autoCompleters != null) { 	    entry.addPropertyChangeListener(new FieldChangeListener 					    (_autoCompleters, entry)); 	    Util.updateCompletersForEntry(_autoCompleters, 					  entry); 	} 	*/
name|_entries
operator|.
name|put
argument_list|(
name|id
argument_list|,
name|entry
argument_list|)
expr_stmt|;
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
DECL|method|addString (BibtexString string, int index)
specifier|public
specifier|synchronized
name|void
name|addString
parameter_list|(
name|BibtexString
name|string
parameter_list|,
name|int
name|index
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
name|i
operator|.
name|next
argument_list|()
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
name|_strings
operator|.
name|insertElementAt
argument_list|(
name|string
argument_list|,
name|index
argument_list|)
expr_stmt|;
block|}
comment|/**      * Removes the string at the given index.      */
DECL|method|removeString (int index)
specifier|public
specifier|synchronized
name|void
name|removeString
parameter_list|(
name|int
name|index
parameter_list|)
block|{
name|_strings
operator|.
name|removeElementAt
argument_list|(
name|index
argument_list|)
expr_stmt|;
block|}
comment|/**      * Returns the string at the given index.      */
DECL|method|getString (int index)
specifier|public
specifier|synchronized
name|BibtexString
name|getString
parameter_list|(
name|int
name|index
parameter_list|)
block|{
return|return
call|(
name|BibtexString
call|)
argument_list|(
name|_strings
operator|.
name|elementAt
argument_list|(
name|index
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
name|i
operator|.
name|next
argument_list|()
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
comment|/*     public void setCompleters(Hashtable autoCompleters) { 	_autoCompleters = autoCompleters;         	for (Iterator i=getKeySet().iterator(); i.hasNext();) { 	    BibtexEntry be = getEntryById((String)(i.next())); 	    be.addPropertyChangeListener(new FieldChangeListener 					 (autoCompleters, be));  	    Util.updateCompletersForEntry(autoCompleters, be); 	} 	}*/
block|}
end_class

end_unit

