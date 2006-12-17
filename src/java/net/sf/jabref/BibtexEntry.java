begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* Copyright (C) 2003 David Weitzman, Morten O. Alver  All programs in this directory and subdirectories are published under the GNU General Public License as described below.  This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.  You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA  Further information about the GNU GPL is available at: http://www.gnu.org/copyleft/gpl.ja.html  Note: Modified for use in JabRef.  */
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|export
operator|.
name|FieldFormatter
import|;
end_import

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
name|beans
operator|.
name|VetoableChangeSupport
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
name|io
operator|.
name|*
import|;
end_import

begin_class
DECL|class|BibtexEntry
specifier|public
class|class
name|BibtexEntry
block|{
DECL|field|ID_FIELD
specifier|public
specifier|final
specifier|static
name|String
name|ID_FIELD
init|=
literal|"id"
decl_stmt|;
DECL|field|_id
specifier|private
name|String
name|_id
decl_stmt|;
DECL|field|_type
specifier|private
name|BibtexEntryType
name|_type
decl_stmt|;
DECL|field|_fields
specifier|private
name|Map
name|_fields
init|=
operator|new
name|HashMap
argument_list|()
decl_stmt|;
DECL|field|_changeSupport
name|VetoableChangeSupport
name|_changeSupport
init|=
operator|new
name|VetoableChangeSupport
argument_list|(
name|this
argument_list|)
decl_stmt|;
comment|// Search and grouping status is stored in boolean fields for quick reference:
DECL|field|searchHit
DECL|field|groupHit
specifier|private
name|boolean
name|searchHit
decl_stmt|,
name|groupHit
decl_stmt|;
DECL|method|BibtexEntry ()
specifier|public
name|BibtexEntry
parameter_list|()
block|{
name|this
argument_list|(
name|Util
operator|.
name|createNeutralId
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|BibtexEntry (String id)
specifier|public
name|BibtexEntry
parameter_list|(
name|String
name|id
parameter_list|)
block|{
name|this
argument_list|(
name|id
argument_list|,
name|BibtexEntryType
operator|.
name|OTHER
argument_list|)
expr_stmt|;
block|}
DECL|method|BibtexEntry (String id, BibtexEntryType type)
specifier|public
name|BibtexEntry
parameter_list|(
name|String
name|id
parameter_list|,
name|BibtexEntryType
name|type
parameter_list|)
block|{
if|if
condition|(
name|id
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|NullPointerException
argument_list|(
literal|"Every BibtexEntry must have an ID"
argument_list|)
throw|;
block|}
name|_id
operator|=
name|id
expr_stmt|;
name|setType
argument_list|(
name|type
argument_list|)
expr_stmt|;
block|}
comment|/**      * Returns an array describing the optional fields for this entry.      */
DECL|method|getOptionalFields ()
specifier|public
name|String
index|[]
name|getOptionalFields
parameter_list|()
block|{
return|return
name|_type
operator|.
name|getOptionalFields
argument_list|()
return|;
block|}
comment|/**      * Returns an array describing the required fields for this entry.      */
DECL|method|getRequiredFields ()
specifier|public
name|String
index|[]
name|getRequiredFields
parameter_list|()
block|{
return|return
name|_type
operator|.
name|getRequiredFields
argument_list|()
return|;
block|}
comment|/**      * Returns an array describing general fields.      */
DECL|method|getGeneralFields ()
specifier|public
name|String
index|[]
name|getGeneralFields
parameter_list|()
block|{
return|return
name|_type
operator|.
name|getGeneralFields
argument_list|()
return|;
block|}
comment|/**      * Returns an array containing the names of all fields that are      * set for this particular entry.      */
DECL|method|getAllFields ()
specifier|public
name|Object
index|[]
name|getAllFields
parameter_list|()
block|{
return|return
name|_fields
operator|.
name|keySet
argument_list|()
operator|.
name|toArray
argument_list|()
return|;
block|}
comment|/**      * Returns a string describing the required fields for this entry.      */
DECL|method|describeRequiredFields ()
specifier|public
name|String
name|describeRequiredFields
parameter_list|()
block|{
return|return
name|_type
operator|.
name|describeRequiredFields
argument_list|()
return|;
block|}
comment|/**      * Returns true if this entry contains the fields it needs to be      * complete.      */
DECL|method|hasAllRequiredFields ()
specifier|public
name|boolean
name|hasAllRequiredFields
parameter_list|()
block|{
return|return
name|_type
operator|.
name|hasAllRequiredFields
argument_list|(
name|this
argument_list|)
return|;
block|}
comment|/**      * Returns this entry's type.      */
DECL|method|getType ()
specifier|public
name|BibtexEntryType
name|getType
parameter_list|()
block|{
return|return
name|_type
return|;
block|}
comment|/**      * Sets this entry's type.      */
DECL|method|setType (BibtexEntryType type)
specifier|public
name|void
name|setType
parameter_list|(
name|BibtexEntryType
name|type
parameter_list|)
block|{
if|if
condition|(
name|type
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|NullPointerException
argument_list|(
literal|"Every BibtexEntry must have a type.  Instead of null, use type OTHER"
argument_list|)
throw|;
block|}
name|_type
operator|=
name|type
expr_stmt|;
block|}
comment|/**      * Prompts the entry to call BibtexEntryType.getType(String) with      * its current type name as argument, and sets its type according      * to what is returned. This method is called when a user changes      * the type customization, to make sure all entries are set with      * current types.      * @return true if the entry could find a type, false if not (in      * this case the type will have been set to      * BibtexEntryType.TYPELESS).      */
DECL|method|updateType ()
specifier|public
name|boolean
name|updateType
parameter_list|()
block|{
name|BibtexEntryType
name|newType
init|=
name|BibtexEntryType
operator|.
name|getType
argument_list|(
name|_type
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|newType
operator|!=
literal|null
condition|)
block|{
name|_type
operator|=
name|newType
expr_stmt|;
return|return
literal|true
return|;
block|}
name|_type
operator|=
name|BibtexEntryType
operator|.
name|TYPELESS
expr_stmt|;
return|return
literal|false
return|;
block|}
comment|/**      * Sets this entry's ID, provided the database containing it      * doesn't veto the change.      */
DECL|method|setId (String id)
specifier|public
name|void
name|setId
parameter_list|(
name|String
name|id
parameter_list|)
throws|throws
name|KeyCollisionException
block|{
if|if
condition|(
name|id
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|NullPointerException
argument_list|(
literal|"Every BibtexEntry must have an ID"
argument_list|)
throw|;
block|}
try|try
block|{
name|firePropertyChangedEvent
argument_list|(
name|ID_FIELD
argument_list|,
name|_id
argument_list|,
name|id
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|PropertyVetoException
name|pv
parameter_list|)
block|{
throw|throw
operator|new
name|KeyCollisionException
argument_list|(
literal|"Couldn't change ID: "
operator|+
name|pv
argument_list|)
throw|;
block|}
name|_id
operator|=
name|id
expr_stmt|;
block|}
comment|/**      * Returns this entry's ID.      */
DECL|method|getId ()
specifier|public
name|String
name|getId
parameter_list|()
block|{
return|return
name|_id
return|;
block|}
comment|/**      * Returns the contents of the given field, or null if it is not set.      */
DECL|method|getField (String name)
specifier|public
name|Object
name|getField
parameter_list|(
name|String
name|name
parameter_list|)
block|{
return|return
name|_fields
operator|.
name|get
argument_list|(
name|name
argument_list|)
return|;
block|}
DECL|method|getCiteKey ()
specifier|public
name|String
name|getCiteKey
parameter_list|()
block|{
return|return
operator|(
name|_fields
operator|.
name|containsKey
argument_list|(
name|BibtexFields
operator|.
name|KEY_FIELD
argument_list|)
condition|?
operator|(
name|String
operator|)
name|_fields
operator|.
name|get
argument_list|(
name|BibtexFields
operator|.
name|KEY_FIELD
argument_list|)
else|:
literal|null
operator|)
return|;
block|}
comment|/**      * Sets a number of fields simultaneously. The given HashMap contains field      * names as keys, each mapped to the value to set.      * WARNING: this method does not notify change listeners, so it should *NOT*      * be used for entries that are being displayed in the GUI. Furthermore, it      * does not check values for content, so e.g. empty strings will be set as such.      */
DECL|method|setField (Map fields)
specifier|public
name|void
name|setField
parameter_list|(
name|Map
name|fields
parameter_list|)
block|{
name|_fields
operator|.
name|putAll
argument_list|(
name|fields
argument_list|)
expr_stmt|;
block|}
comment|/**      * Set a field, and notify listeners about the change.      *      * @param name The field to set.      * @param value The value to set.      */
DECL|method|setField (String name, Object value)
specifier|public
name|void
name|setField
parameter_list|(
name|String
name|name
parameter_list|,
name|Object
name|value
parameter_list|)
block|{
if|if
condition|(
name|ID_FIELD
operator|.
name|equals
argument_list|(
name|name
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The field name '"
operator|+
name|name
operator|+
literal|"' is reserved"
argument_list|)
throw|;
block|}
comment|// This mechanism is probably not really necessary.
comment|//Object normalValue = FieldTypes.normalize(name, value);
name|Object
name|oldValue
init|=
name|_fields
operator|.
name|get
argument_list|(
name|name
argument_list|)
decl_stmt|;
try|try
block|{
comment|// We set the field before throwing the changeEvent, to enable
comment|// the change listener to access the new value if the change
comment|// sets off a change in database sorting etc.
name|_fields
operator|.
name|put
argument_list|(
name|name
argument_list|,
name|value
argument_list|)
expr_stmt|;
name|firePropertyChangedEvent
argument_list|(
name|name
argument_list|,
name|oldValue
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|PropertyVetoException
name|pve
parameter_list|)
block|{
comment|// Since we have already made the change, we must undo it since
comment|// the change was rejected:
name|_fields
operator|.
name|put
argument_list|(
name|name
argument_list|,
name|oldValue
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Change rejected: "
operator|+
name|pve
argument_list|)
throw|;
block|}
block|}
comment|/**      * Remove the mapping for the field name, and notify listeners about      * the change.      *      * @param name The field to clear.      */
DECL|method|clearField (String name)
specifier|public
name|void
name|clearField
parameter_list|(
name|String
name|name
parameter_list|)
block|{
if|if
condition|(
name|ID_FIELD
operator|.
name|equals
argument_list|(
name|name
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The field name '"
operator|+
name|name
operator|+
literal|"' is reserved"
argument_list|)
throw|;
block|}
name|Object
name|oldValue
init|=
name|_fields
operator|.
name|get
argument_list|(
name|name
argument_list|)
decl_stmt|;
name|_fields
operator|.
name|remove
argument_list|(
name|name
argument_list|)
expr_stmt|;
try|try
block|{
name|firePropertyChangedEvent
argument_list|(
name|name
argument_list|,
name|oldValue
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|PropertyVetoException
name|pve
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Change rejected: "
operator|+
name|pve
argument_list|)
throw|;
block|}
block|}
DECL|method|allFieldsPresent (String[] fields)
specifier|protected
name|boolean
name|allFieldsPresent
parameter_list|(
name|String
index|[]
name|fields
parameter_list|)
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|fields
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|getField
argument_list|(
name|fields
index|[
name|i
index|]
argument_list|)
operator|==
literal|null
condition|)
block|{
return|return
literal|false
return|;
block|}
block|}
return|return
literal|true
return|;
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
throws|throws
name|PropertyVetoException
block|{
name|_changeSupport
operator|.
name|fireVetoableChange
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
comment|/**      * Adds a VetoableChangeListener, which is notified of field      * changes. This is useful for an object that needs to update      * itself each time a field changes.      */
DECL|method|addPropertyChangeListener (VetoableChangeListener listener)
specifier|public
name|void
name|addPropertyChangeListener
parameter_list|(
name|VetoableChangeListener
name|listener
parameter_list|)
block|{
name|_changeSupport
operator|.
name|addVetoableChangeListener
argument_list|(
name|listener
argument_list|)
expr_stmt|;
block|}
comment|/**      * Removes a property listener.      */
DECL|method|removePropertyChangeListener (VetoableChangeListener listener)
specifier|public
name|void
name|removePropertyChangeListener
parameter_list|(
name|VetoableChangeListener
name|listener
parameter_list|)
block|{
name|_changeSupport
operator|.
name|removeVetoableChangeListener
argument_list|(
name|listener
argument_list|)
expr_stmt|;
block|}
comment|/**      * Write this entry to the given Writer, with the given FieldFormatter.      * @param write True if this is a write, false if it is a display. The write will      * not include non-writeable fields if it is a write, otherwise non-displayable fields      * will be ignored. Refer to GUIGlobals for isWriteableField(String) and      * isDisplayableField(String).      */
DECL|method|write (Writer out, FieldFormatter ff, boolean write)
specifier|public
name|void
name|write
parameter_list|(
name|Writer
name|out
parameter_list|,
name|FieldFormatter
name|ff
parameter_list|,
name|boolean
name|write
parameter_list|)
throws|throws
name|IOException
block|{
comment|// Write header with type and bibtex-key.
name|out
operator|.
name|write
argument_list|(
literal|"@"
operator|+
name|_type
operator|.
name|getName
argument_list|()
operator|.
name|toUpperCase
argument_list|()
operator|+
literal|"{"
argument_list|)
expr_stmt|;
name|String
name|str
init|=
name|Util
operator|.
name|shaveString
argument_list|(
operator|(
name|String
operator|)
name|getField
argument_list|(
name|BibtexFields
operator|.
name|KEY_FIELD
argument_list|)
argument_list|)
decl_stmt|;
name|out
operator|.
name|write
argument_list|(
operator|(
operator|(
name|str
operator|==
literal|null
operator|)
condition|?
literal|""
else|:
name|str
operator|)
operator|+
literal|","
operator|+
name|Globals
operator|.
name|NEWLINE
argument_list|)
expr_stmt|;
name|HashMap
name|written
init|=
operator|new
name|HashMap
argument_list|()
decl_stmt|;
name|written
operator|.
name|put
argument_list|(
name|BibtexFields
operator|.
name|KEY_FIELD
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|boolean
name|hasWritten
init|=
literal|false
decl_stmt|;
comment|// Write required fields first.
name|String
index|[]
name|s
init|=
name|getRequiredFields
argument_list|()
decl_stmt|;
if|if
condition|(
name|s
operator|!=
literal|null
condition|)
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|s
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|hasWritten
operator|=
name|hasWritten
operator||
name|writeField
argument_list|(
name|s
index|[
name|i
index|]
argument_list|,
name|out
argument_list|,
name|ff
argument_list|,
name|hasWritten
argument_list|)
expr_stmt|;
name|written
operator|.
name|put
argument_list|(
name|s
index|[
name|i
index|]
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
comment|// Then optional fields.
name|s
operator|=
name|getOptionalFields
argument_list|()
expr_stmt|;
if|if
condition|(
name|s
operator|!=
literal|null
condition|)
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|s
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
operator|!
name|written
operator|.
name|containsKey
argument_list|(
name|s
index|[
name|i
index|]
argument_list|)
condition|)
block|{
comment|// If field appears both in req. and opt. don't repeat.
comment|//writeField(s[i], out, ff);
name|hasWritten
operator|=
name|hasWritten
operator||
name|writeField
argument_list|(
name|s
index|[
name|i
index|]
argument_list|,
name|out
argument_list|,
name|ff
argument_list|,
name|hasWritten
argument_list|)
expr_stmt|;
name|written
operator|.
name|put
argument_list|(
name|s
index|[
name|i
index|]
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
block|}
comment|// Then write remaining fields in alphabetic order.
name|TreeSet
name|remainingFields
init|=
operator|new
name|TreeSet
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|_fields
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
name|String
name|key
init|=
operator|(
name|String
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|boolean
name|writeIt
init|=
operator|(
name|write
condition|?
name|BibtexFields
operator|.
name|isWriteableField
argument_list|(
name|key
argument_list|)
else|:
name|BibtexFields
operator|.
name|isDisplayableField
argument_list|(
name|key
argument_list|)
operator|)
decl_stmt|;
if|if
condition|(
operator|!
name|written
operator|.
name|containsKey
argument_list|(
name|key
argument_list|)
operator|&&
name|writeIt
condition|)
name|remainingFields
operator|.
name|add
argument_list|(
name|key
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|Iterator
name|i
init|=
name|remainingFields
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
name|hasWritten
operator|=
name|hasWritten
operator||
name|writeField
argument_list|(
operator|(
name|String
operator|)
name|i
operator|.
name|next
argument_list|()
argument_list|,
name|out
argument_list|,
name|ff
argument_list|,
name|hasWritten
argument_list|)
expr_stmt|;
comment|//writeField((String)i.next(),out,ff);
comment|// Finally, end the entry.
name|out
operator|.
name|write
argument_list|(
operator|(
name|hasWritten
condition|?
name|Globals
operator|.
name|NEWLINE
else|:
literal|""
operator|)
operator|+
literal|"}"
operator|+
name|Globals
operator|.
name|NEWLINE
argument_list|)
expr_stmt|;
block|}
comment|/**      * Write a single field, if it has any content.      * @param name The field name      * @param out The Writer to send it to      * @param ff A formatter to filter field contents before writing      * @param isFirst Indicates whether this is the first field written for      *    this entry - if not, start by writing a comma and newline      * @return true if this field was written, false if it was skipped because      *    it was not set      * @throws IOException In case of an IO error      */
DECL|method|writeField (String name, Writer out, FieldFormatter ff, boolean isFirst)
specifier|private
name|boolean
name|writeField
parameter_list|(
name|String
name|name
parameter_list|,
name|Writer
name|out
parameter_list|,
name|FieldFormatter
name|ff
parameter_list|,
name|boolean
name|isFirst
parameter_list|)
throws|throws
name|IOException
block|{
name|Object
name|o
init|=
name|getField
argument_list|(
name|name
argument_list|)
decl_stmt|;
if|if
condition|(
name|o
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|isFirst
condition|)
name|out
operator|.
name|write
argument_list|(
literal|","
operator|+
name|Globals
operator|.
name|NEWLINE
argument_list|)
expr_stmt|;
name|out
operator|.
name|write
argument_list|(
literal|"  "
operator|+
name|name
operator|+
literal|" = "
argument_list|)
expr_stmt|;
try|try
block|{
name|out
operator|.
name|write
argument_list|(
name|ff
operator|.
name|format
argument_list|(
name|o
operator|.
name|toString
argument_list|()
argument_list|,
name|name
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Throwable
name|ex
parameter_list|)
block|{
throw|throw
operator|new
name|IOException
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Error in field"
argument_list|)
operator|+
literal|" '"
operator|+
name|name
operator|+
literal|"': "
operator|+
name|ex
operator|.
name|getMessage
argument_list|()
argument_list|)
throw|;
block|}
return|return
literal|true
return|;
comment|//Util.writeField(name, o, out);
comment|//out.write(","+Globals.NEWLINE);
block|}
else|else
return|return
literal|false
return|;
block|}
comment|/**      * Returns a clone of this entry. Useful for copying.      */
DECL|method|clone ()
specifier|public
name|Object
name|clone
parameter_list|()
block|{
name|BibtexEntry
name|clone
init|=
operator|new
name|BibtexEntry
argument_list|(
name|_id
argument_list|,
name|_type
argument_list|)
decl_stmt|;
name|clone
operator|.
name|_fields
operator|=
call|(
name|Map
call|)
argument_list|(
operator|(
name|HashMap
operator|)
name|_fields
argument_list|)
operator|.
name|clone
argument_list|()
expr_stmt|;
return|return
name|clone
return|;
block|}
DECL|method|toString ()
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|getType
argument_list|()
operator|.
name|getName
argument_list|()
operator|+
literal|":"
operator|+
name|getField
argument_list|(
name|BibtexFields
operator|.
name|KEY_FIELD
argument_list|)
return|;
block|}
DECL|method|isSearchHit ()
specifier|public
name|boolean
name|isSearchHit
parameter_list|()
block|{
return|return
name|searchHit
return|;
block|}
DECL|method|setSearchHit (boolean searchHit)
specifier|public
name|void
name|setSearchHit
parameter_list|(
name|boolean
name|searchHit
parameter_list|)
block|{
name|this
operator|.
name|searchHit
operator|=
name|searchHit
expr_stmt|;
block|}
DECL|method|isGroupHit ()
specifier|public
name|boolean
name|isGroupHit
parameter_list|()
block|{
return|return
name|groupHit
return|;
block|}
DECL|method|setGroupHit (boolean groupHit)
specifier|public
name|void
name|setGroupHit
parameter_list|(
name|boolean
name|groupHit
parameter_list|)
block|{
name|this
operator|.
name|groupHit
operator|=
name|groupHit
expr_stmt|;
block|}
comment|/**      * @param maxCharacters The maximum number of characters (additional      * characters are replaced with "..."). Set to 0 to disable truncation.      * @return A short textual description of the entry in the format:      * Author1, Author2: Title (Year)      */
DECL|method|getAuthorTitleYear (int maxCharacters)
specifier|public
name|String
name|getAuthorTitleYear
parameter_list|(
name|int
name|maxCharacters
parameter_list|)
block|{
name|String
index|[]
name|s
init|=
operator|new
name|String
index|[]
block|{
operator|(
name|String
operator|)
name|getField
argument_list|(
literal|"author"
argument_list|)
block|,
operator|(
name|String
operator|)
name|getField
argument_list|(
literal|"title"
argument_list|)
block|,
operator|(
name|String
operator|)
name|getField
argument_list|(
literal|"year"
argument_list|)
block|}
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
name|s
operator|.
name|length
condition|;
operator|++
name|i
control|)
if|if
condition|(
name|s
index|[
name|i
index|]
operator|==
literal|null
condition|)
name|s
index|[
name|i
index|]
operator|=
literal|"N/A"
expr_stmt|;
name|String
name|text
init|=
name|s
index|[
literal|0
index|]
operator|+
literal|": \""
operator|+
name|s
index|[
literal|1
index|]
operator|+
literal|"\" ("
operator|+
name|s
index|[
literal|2
index|]
operator|+
literal|")"
decl_stmt|;
if|if
condition|(
name|maxCharacters
operator|<=
literal|0
operator|||
name|text
operator|.
name|length
argument_list|()
operator|<=
name|maxCharacters
condition|)
return|return
name|text
return|;
return|return
name|text
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|maxCharacters
operator|+
literal|1
argument_list|)
operator|+
literal|"..."
return|;
block|}
block|}
end_class

end_unit

