begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* Copyright (C) 2003 Nizar N. Batada nbatada@stanford.edu Morten O. Alver alver@boblefisk.org  All programs in this directory and subdirectories are published under the GNU General Public License as described below.  This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.  You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA  Further information about the GNU GPL is available at: http://www.gnu.org/copyleft/gpl.ja.html  */
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
name|HashMap
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

begin_class
DECL|class|BibDatabase
specifier|public
class|class
name|BibDatabase
block|{
DECL|field|_entries
specifier|private
name|HashMap
name|_entries
init|=
operator|new
name|HashMap
argument_list|()
decl_stmt|;
DECL|field|_changeSupport
name|PropertyChangeSupport
name|_changeSupport
init|=
operator|new
name|PropertyChangeSupport
argument_list|(
name|this
argument_list|)
decl_stmt|;
comment|/**      * Insert an entry.      */
DECL|method|addEntry (String id, BibEntry entry)
specifier|public
name|void
name|addEntry
parameter_list|(
name|String
name|id
parameter_list|,
name|BibEntry
name|entry
parameter_list|)
throws|throws
name|KeyCollisionException
block|{
comment|// Inserts entry into _entries, but only if there is no entry
comment|// there already with the same id.
if|if
condition|(
name|_entries
operator|.
name|containsKey
argument_list|(
name|id
argument_list|)
condition|)
throw|throw
operator|new
name|KeyCollisionException
argument_list|(
literal|"ID already used in database"
argument_list|)
throw|;
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
comment|/**      * Get an entry by its ID. Returns null if id doesn't exist.      */
DECL|method|getEntry (String id)
specifier|public
name|BibEntry
name|getEntry
parameter_list|(
name|String
name|id
parameter_list|)
block|{
return|return
operator|(
name|BibEntry
operator|)
name|_entries
operator|.
name|get
argument_list|(
name|id
argument_list|)
return|;
block|}
comment|/**      * Remove an entry by its ID.      */
DECL|method|removeEntry (String id)
specifier|public
name|void
name|removeEntry
parameter_list|(
name|String
name|id
parameter_list|)
block|{
name|_entries
operator|.
name|remove
argument_list|(
name|id
argument_list|)
expr_stmt|;
block|}
comment|/**      * Remove an entry.      */
comment|/*    public void removeEntry(BibEntry entry) {      }*/
comment|/**      * To get keys for all entries.      */
DECL|method|getKeySet ()
specifier|public
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
comment|/**      * Add a property listener, that gets notification any time a string      * or and entry is added or removed, or the preamble is changed.      */
DECL|method|addPropertyChangeListener (PropertyChangeListener listener)
specifier|public
name|void
name|addPropertyChangeListener
parameter_list|(
name|PropertyChangeListener
name|listener
parameter_list|)
block|{
name|_changeSupport
operator|.
name|addPropertyChangeListener
argument_list|(
name|listener
argument_list|)
expr_stmt|;
block|}
comment|/**      * Remove a property listener.      */
DECL|method|removePropertyChangeListener (PropertyChangeListener listener)
specifier|public
name|void
name|removePropertyChangeListener
parameter_list|(
name|PropertyChangeListener
name|listener
parameter_list|)
block|{
name|_changeSupport
operator|.
name|removePropertyChangeListener
argument_list|(
name|listener
argument_list|)
expr_stmt|;
block|}
DECL|method|firePropertyChangedEvent (String fieldName, Object oldValue, Object newValue)
specifier|private
name|void
name|firePropertyChangedEvent
parameter_list|(
name|String
name|fieldName
parameter_list|,
name|Object
name|oldValue
parameter_list|,
name|Object
name|newValue
parameter_list|)
block|{
name|_changeSupport
operator|.
name|firePropertyChange
argument_list|(
operator|new
name|PropertyChangeEvent
argument_list|(
name|this
argument_list|,
name|fieldName
argument_list|,
name|oldValue
argument_list|,
name|newValue
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

