begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* Copyright (C) 2003 Morten O. Alver, Nizar N. Batada  All programs in this directory and subdirectories are published under the GNU General Public License as described below.  This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.  You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA  Further information about the GNU GPL is available at: http://www.gnu.org/copyleft/gpl.ja.html  */
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
name|io
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
name|*
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Point
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Dimension
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|prefs
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

begin_class
DECL|class|JabRefPreferences
specifier|public
class|class
name|JabRefPreferences
block|{
DECL|field|prefs
name|Preferences
name|prefs
decl_stmt|;
DECL|field|defaults
name|HashMap
name|defaults
init|=
operator|new
name|HashMap
argument_list|()
decl_stmt|,
DECL|field|keyBinds
name|keyBinds
init|=
operator|new
name|HashMap
argument_list|()
decl_stmt|,
DECL|field|defKeyBinds
name|defKeyBinds
init|=
operator|new
name|HashMap
argument_list|()
decl_stmt|;
DECL|method|JabRefPreferences ()
specifier|public
name|JabRefPreferences
parameter_list|()
block|{
name|prefs
operator|=
name|Preferences
operator|.
name|userNodeForPackage
argument_list|(
name|JabRef
operator|.
name|class
argument_list|)
expr_stmt|;
comment|//Util.pr(prefs.toString());
name|defaults
operator|.
name|put
argument_list|(
literal|"posX"
argument_list|,
operator|new
name|Integer
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|defaults
operator|.
name|put
argument_list|(
literal|"posY"
argument_list|,
operator|new
name|Integer
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|defaults
operator|.
name|put
argument_list|(
literal|"sizeX"
argument_list|,
operator|new
name|Integer
argument_list|(
literal|840
argument_list|)
argument_list|)
expr_stmt|;
name|defaults
operator|.
name|put
argument_list|(
literal|"sizeY"
argument_list|,
operator|new
name|Integer
argument_list|(
literal|680
argument_list|)
argument_list|)
expr_stmt|;
name|defaults
operator|.
name|put
argument_list|(
literal|"autoResizeMode"
argument_list|,
operator|new
name|Integer
argument_list|(
name|JTable
operator|.
name|AUTO_RESIZE_OFF
argument_list|)
argument_list|)
expr_stmt|;
name|defaults
operator|.
name|put
argument_list|(
literal|"tableColorCodesOn"
argument_list|,
operator|new
name|Boolean
argument_list|(
literal|true
argument_list|)
argument_list|)
expr_stmt|;
name|defaults
operator|.
name|put
argument_list|(
literal|"language"
argument_list|,
literal|"en"
argument_list|)
expr_stmt|;
name|defaults
operator|.
name|put
argument_list|(
literal|"priSort"
argument_list|,
literal|"author"
argument_list|)
expr_stmt|;
name|defaults
operator|.
name|put
argument_list|(
literal|"priDescending"
argument_list|,
operator|new
name|Boolean
argument_list|(
literal|false
argument_list|)
argument_list|)
expr_stmt|;
name|defaults
operator|.
name|put
argument_list|(
literal|"secSort"
argument_list|,
literal|"year"
argument_list|)
expr_stmt|;
name|defaults
operator|.
name|put
argument_list|(
literal|"secDescending"
argument_list|,
operator|new
name|Boolean
argument_list|(
literal|true
argument_list|)
argument_list|)
expr_stmt|;
name|defaults
operator|.
name|put
argument_list|(
literal|"terSort"
argument_list|,
literal|"author"
argument_list|)
expr_stmt|;
name|defaults
operator|.
name|put
argument_list|(
literal|"terDescending"
argument_list|,
operator|new
name|Boolean
argument_list|(
literal|false
argument_list|)
argument_list|)
expr_stmt|;
name|defaults
operator|.
name|put
argument_list|(
literal|"columnNames"
argument_list|,
literal|"author;title;year;journal;bibtexkey"
argument_list|)
expr_stmt|;
name|defaults
operator|.
name|put
argument_list|(
literal|"workingDirectory"
argument_list|,
operator|(
name|String
operator|)
literal|null
argument_list|)
expr_stmt|;
name|defaults
operator|.
name|put
argument_list|(
literal|"autoOpenForm"
argument_list|,
operator|new
name|Boolean
argument_list|(
literal|true
argument_list|)
argument_list|)
expr_stmt|;
name|defaults
operator|.
name|put
argument_list|(
literal|"entryTypeFormHeightFactor"
argument_list|,
operator|new
name|Integer
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|defaults
operator|.
name|put
argument_list|(
literal|"entryTypeFormWidth"
argument_list|,
operator|new
name|Integer
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|defaults
operator|.
name|put
argument_list|(
literal|"backup"
argument_list|,
operator|new
name|Boolean
argument_list|(
literal|true
argument_list|)
argument_list|)
expr_stmt|;
name|defaults
operator|.
name|put
argument_list|(
literal|"openLastEdited"
argument_list|,
operator|new
name|Boolean
argument_list|(
literal|true
argument_list|)
argument_list|)
expr_stmt|;
name|defaults
operator|.
name|put
argument_list|(
literal|"lastEdited"
argument_list|,
operator|(
name|String
operator|)
literal|null
argument_list|)
expr_stmt|;
name|defaults
operator|.
name|put
argument_list|(
literal|"stringsPosX"
argument_list|,
operator|new
name|Integer
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|defaults
operator|.
name|put
argument_list|(
literal|"stringsPosY"
argument_list|,
operator|new
name|Integer
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|defaults
operator|.
name|put
argument_list|(
literal|"stringsSizeX"
argument_list|,
operator|new
name|Integer
argument_list|(
literal|600
argument_list|)
argument_list|)
expr_stmt|;
name|defaults
operator|.
name|put
argument_list|(
literal|"stringsSizeY"
argument_list|,
operator|new
name|Integer
argument_list|(
literal|400
argument_list|)
argument_list|)
expr_stmt|;
name|defaults
operator|.
name|put
argument_list|(
literal|"defaultShowSource"
argument_list|,
operator|new
name|Boolean
argument_list|(
literal|false
argument_list|)
argument_list|)
expr_stmt|;
name|defaults
operator|.
name|put
argument_list|(
literal|"enableSourceEditing"
argument_list|,
operator|new
name|Boolean
argument_list|(
literal|false
argument_list|)
argument_list|)
expr_stmt|;
name|defaults
operator|.
name|put
argument_list|(
literal|"caseSensitiveSearch"
argument_list|,
operator|new
name|Boolean
argument_list|(
literal|false
argument_list|)
argument_list|)
expr_stmt|;
name|defaults
operator|.
name|put
argument_list|(
literal|"searchReq"
argument_list|,
operator|new
name|Boolean
argument_list|(
literal|true
argument_list|)
argument_list|)
expr_stmt|;
name|defaults
operator|.
name|put
argument_list|(
literal|"searchOpt"
argument_list|,
operator|new
name|Boolean
argument_list|(
literal|true
argument_list|)
argument_list|)
expr_stmt|;
name|defaults
operator|.
name|put
argument_list|(
literal|"searchGen"
argument_list|,
operator|new
name|Boolean
argument_list|(
literal|true
argument_list|)
argument_list|)
expr_stmt|;
name|defaults
operator|.
name|put
argument_list|(
literal|"searchAll"
argument_list|,
operator|new
name|Boolean
argument_list|(
literal|false
argument_list|)
argument_list|)
expr_stmt|;
name|defaults
operator|.
name|put
argument_list|(
literal|"regExpSearch"
argument_list|,
operator|new
name|Boolean
argument_list|(
literal|true
argument_list|)
argument_list|)
expr_stmt|;
name|defaults
operator|.
name|put
argument_list|(
literal|"searchPanePosX"
argument_list|,
operator|new
name|Integer
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|defaults
operator|.
name|put
argument_list|(
literal|"searchPanePosY"
argument_list|,
operator|new
name|Integer
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|defaults
operator|.
name|put
argument_list|(
literal|"autoComplete"
argument_list|,
operator|new
name|Boolean
argument_list|(
literal|true
argument_list|)
argument_list|)
expr_stmt|;
name|defaults
operator|.
name|put
argument_list|(
literal|"autoCompFields"
argument_list|,
operator|new
name|byte
index|[]
block|{
literal|0
block|,
literal|1
block|,
literal|28
block|}
argument_list|)
expr_stmt|;
name|defaults
operator|.
name|put
argument_list|(
literal|"groupSelectorVisible"
argument_list|,
operator|new
name|Boolean
argument_list|(
literal|true
argument_list|)
argument_list|)
expr_stmt|;
name|defaults
operator|.
name|put
argument_list|(
literal|"groupsDefaultField"
argument_list|,
literal|"keywords"
argument_list|)
expr_stmt|;
name|restoreKeyBindings
argument_list|()
expr_stmt|;
comment|//defaults.put("oooWarning", new Boolean(true));
block|}
DECL|method|get (String key)
specifier|public
name|String
name|get
parameter_list|(
name|String
name|key
parameter_list|)
block|{
return|return
name|prefs
operator|.
name|get
argument_list|(
name|key
argument_list|,
operator|(
name|String
operator|)
name|defaults
operator|.
name|get
argument_list|(
name|key
argument_list|)
argument_list|)
return|;
block|}
DECL|method|getBoolean (String key)
specifier|public
name|boolean
name|getBoolean
parameter_list|(
name|String
name|key
parameter_list|)
block|{
return|return
name|prefs
operator|.
name|getBoolean
argument_list|(
name|key
argument_list|,
operator|(
operator|(
name|Boolean
operator|)
name|defaults
operator|.
name|get
argument_list|(
name|key
argument_list|)
operator|)
operator|.
name|booleanValue
argument_list|()
argument_list|)
return|;
block|}
DECL|method|getDouble (String key)
specifier|public
name|double
name|getDouble
parameter_list|(
name|String
name|key
parameter_list|)
block|{
return|return
name|prefs
operator|.
name|getDouble
argument_list|(
name|key
argument_list|,
operator|(
operator|(
name|Double
operator|)
name|defaults
operator|.
name|get
argument_list|(
name|key
argument_list|)
operator|)
operator|.
name|doubleValue
argument_list|()
argument_list|)
return|;
block|}
DECL|method|getInt (String key)
specifier|public
name|int
name|getInt
parameter_list|(
name|String
name|key
parameter_list|)
block|{
return|return
name|prefs
operator|.
name|getInt
argument_list|(
name|key
argument_list|,
operator|(
operator|(
name|Integer
operator|)
name|defaults
operator|.
name|get
argument_list|(
name|key
argument_list|)
operator|)
operator|.
name|intValue
argument_list|()
argument_list|)
return|;
block|}
DECL|method|getByteArray (String key)
specifier|public
name|byte
index|[]
name|getByteArray
parameter_list|(
name|String
name|key
parameter_list|)
block|{
return|return
name|prefs
operator|.
name|getByteArray
argument_list|(
name|key
argument_list|,
operator|(
name|byte
index|[]
operator|)
name|defaults
operator|.
name|get
argument_list|(
name|key
argument_list|)
argument_list|)
return|;
block|}
DECL|method|put (String key, String value)
specifier|public
name|void
name|put
parameter_list|(
name|String
name|key
parameter_list|,
name|String
name|value
parameter_list|)
block|{
name|prefs
operator|.
name|put
argument_list|(
name|key
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
DECL|method|putBoolean (String key, boolean value)
specifier|public
name|void
name|putBoolean
parameter_list|(
name|String
name|key
parameter_list|,
name|boolean
name|value
parameter_list|)
block|{
name|prefs
operator|.
name|putBoolean
argument_list|(
name|key
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
DECL|method|putDouble (String key, double value)
specifier|public
name|void
name|putDouble
parameter_list|(
name|String
name|key
parameter_list|,
name|double
name|value
parameter_list|)
block|{
name|prefs
operator|.
name|putDouble
argument_list|(
name|key
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
DECL|method|putInt (String key, int value)
specifier|public
name|void
name|putInt
parameter_list|(
name|String
name|key
parameter_list|,
name|int
name|value
parameter_list|)
block|{
name|prefs
operator|.
name|putInt
argument_list|(
name|key
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
DECL|method|putByteArray (String key, byte[] value)
specifier|public
name|void
name|putByteArray
parameter_list|(
name|String
name|key
parameter_list|,
name|byte
index|[]
name|value
parameter_list|)
block|{
name|prefs
operator|.
name|putByteArray
argument_list|(
name|key
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
DECL|method|remove (String key)
specifier|public
name|void
name|remove
parameter_list|(
name|String
name|key
parameter_list|)
block|{
name|prefs
operator|.
name|remove
argument_list|(
name|key
argument_list|)
expr_stmt|;
block|}
comment|/**      * Puts a string array into the Preferences, by linking its elements      * with ';' into a single string. Escape characters make the process      * transparent even if strings contain ';'.      */
DECL|method|putStringArray (String key, String[] value)
specifier|public
name|void
name|putStringArray
parameter_list|(
name|String
name|key
parameter_list|,
name|String
index|[]
name|value
parameter_list|)
block|{
name|StringBuffer
name|linked
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
if|if
condition|(
name|value
operator|.
name|length
operator|>
literal|0
condition|)
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
name|value
operator|.
name|length
operator|-
literal|1
condition|;
name|i
operator|++
control|)
block|{
name|linked
operator|.
name|append
argument_list|(
name|makeEscape
argument_list|(
name|value
index|[
name|i
index|]
argument_list|)
argument_list|)
expr_stmt|;
name|linked
operator|.
name|append
argument_list|(
literal|";"
argument_list|)
expr_stmt|;
block|}
block|}
name|linked
operator|.
name|append
argument_list|(
name|makeEscape
argument_list|(
name|value
index|[
name|value
operator|.
name|length
operator|-
literal|1
index|]
argument_list|)
argument_list|)
expr_stmt|;
name|put
argument_list|(
name|key
argument_list|,
name|linked
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * Returns a String[] containing the chosen columns.      */
DECL|method|getStringArray (String key)
specifier|public
name|String
index|[]
name|getStringArray
parameter_list|(
name|String
name|key
parameter_list|)
block|{
name|String
name|names
init|=
name|get
argument_list|(
name|key
argument_list|)
decl_stmt|;
if|if
condition|(
name|names
operator|==
literal|null
condition|)
return|return
literal|null
return|;
comment|//Util.pr(key+"\n"+names);
name|StringReader
name|rd
init|=
operator|new
name|StringReader
argument_list|(
name|names
argument_list|)
decl_stmt|;
name|Vector
name|arr
init|=
operator|new
name|Vector
argument_list|()
decl_stmt|;
name|String
name|rs
decl_stmt|;
try|try
block|{
while|while
condition|(
operator|(
name|rs
operator|=
name|getNextUnit
argument_list|(
name|rd
argument_list|)
operator|)
operator|!=
literal|null
condition|)
block|{
name|arr
operator|.
name|add
argument_list|(
name|rs
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{}
name|String
index|[]
name|res
init|=
operator|new
name|String
index|[
name|arr
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
name|res
operator|.
name|length
condition|;
name|i
operator|++
control|)
name|res
index|[
name|i
index|]
operator|=
operator|(
name|String
operator|)
name|arr
operator|.
name|elementAt
argument_list|(
name|i
argument_list|)
expr_stmt|;
return|return
name|res
return|;
block|}
comment|/**      * Returns the KeyStroke for this binding, as defined by the      * defaults, or in the Preferences.      */
DECL|method|getKey (String bindName)
specifier|public
name|KeyStroke
name|getKey
parameter_list|(
name|String
name|bindName
parameter_list|)
block|{
comment|//Util.pr(bindName+" "+(String)keyBinds.get(bindName));
name|String
name|s
init|=
operator|(
name|String
operator|)
name|keyBinds
operator|.
name|get
argument_list|(
name|bindName
argument_list|)
decl_stmt|;
comment|// If the current key bindings don't contain the one asked for,
comment|// we fall back on the default. This should only happen when a
comment|// user has his own set in Preferences, and has upgraded to a
comment|// new version where new bindings have been introduced.
if|if
condition|(
name|s
operator|==
literal|null
condition|)
name|s
operator|=
operator|(
name|String
operator|)
name|defKeyBinds
operator|.
name|get
argument_list|(
name|bindName
argument_list|)
expr_stmt|;
return|return
name|KeyStroke
operator|.
name|getKeyStroke
argument_list|(
name|s
argument_list|)
return|;
block|}
comment|/**      * Returns the HashMap containing all key bindings.      */
DECL|method|getKeyBindings ()
specifier|public
name|HashMap
name|getKeyBindings
parameter_list|()
block|{
return|return
name|keyBinds
return|;
block|}
comment|/**      * Stores new key bindings into Preferences, provided they      * actually differ from the old ones.      */
DECL|method|setNewKeyBindings (HashMap newBindings)
specifier|public
name|void
name|setNewKeyBindings
parameter_list|(
name|HashMap
name|newBindings
parameter_list|)
block|{
if|if
condition|(
operator|!
name|newBindings
operator|.
name|equals
argument_list|(
name|keyBinds
argument_list|)
condition|)
block|{
comment|// This confirms that the bindings have actually changed.
name|String
index|[]
name|bindNames
init|=
operator|new
name|String
index|[
name|newBindings
operator|.
name|size
argument_list|()
index|]
decl_stmt|,
name|bindings
init|=
operator|new
name|String
index|[
name|newBindings
operator|.
name|size
argument_list|()
index|]
decl_stmt|;
name|int
name|index
init|=
literal|0
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|newBindings
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
name|nm
init|=
operator|(
name|String
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|String
name|bnd
init|=
operator|(
name|String
operator|)
name|newBindings
operator|.
name|get
argument_list|(
name|nm
argument_list|)
decl_stmt|;
name|bindNames
index|[
name|index
index|]
operator|=
name|nm
expr_stmt|;
name|bindings
index|[
name|index
index|]
operator|=
name|bnd
expr_stmt|;
name|index
operator|++
expr_stmt|;
block|}
name|putStringArray
argument_list|(
literal|"bindNames"
argument_list|,
name|bindNames
argument_list|)
expr_stmt|;
name|putStringArray
argument_list|(
literal|"bindings"
argument_list|,
name|bindings
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|restoreKeyBindings ()
specifier|private
name|void
name|restoreKeyBindings
parameter_list|()
block|{
comment|// Define default keybindings.
name|defineDefaultKeyBindings
argument_list|()
expr_stmt|;
comment|// First read the bindings, and their names.
name|String
index|[]
name|bindNames
init|=
name|getStringArray
argument_list|(
literal|"bindNames"
argument_list|)
decl_stmt|,
name|bindings
init|=
name|getStringArray
argument_list|(
literal|"bindings"
argument_list|)
decl_stmt|;
comment|// Then set up the key bindings HashMap.
if|if
condition|(
operator|(
name|bindNames
operator|==
literal|null
operator|)
operator|||
operator|(
name|bindings
operator|==
literal|null
operator|)
operator|||
operator|(
name|bindNames
operator|.
name|length
operator|!=
name|bindings
operator|.
name|length
operator|)
condition|)
block|{
comment|// Nothing defined in Preferences, or something is wrong.
name|setDefaultKeyBindings
argument_list|()
expr_stmt|;
return|return;
block|}
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|bindNames
operator|.
name|length
condition|;
name|i
operator|++
control|)
name|keyBinds
operator|.
name|put
argument_list|(
name|bindNames
index|[
name|i
index|]
argument_list|,
name|bindings
index|[
name|i
index|]
argument_list|)
expr_stmt|;
block|}
DECL|method|setDefaultKeyBindings ()
specifier|private
name|void
name|setDefaultKeyBindings
parameter_list|()
block|{
name|keyBinds
operator|=
name|defKeyBinds
expr_stmt|;
block|}
DECL|method|defineDefaultKeyBindings ()
specifier|private
name|void
name|defineDefaultKeyBindings
parameter_list|()
block|{
name|defKeyBinds
operator|.
name|put
argument_list|(
literal|"Open"
argument_list|,
literal|"ctrl O"
argument_list|)
expr_stmt|;
name|defKeyBinds
operator|.
name|put
argument_list|(
literal|"Save"
argument_list|,
literal|"ctrl S"
argument_list|)
expr_stmt|;
name|defKeyBinds
operator|.
name|put
argument_list|(
literal|"New entry"
argument_list|,
literal|"ctrl N"
argument_list|)
expr_stmt|;
name|defKeyBinds
operator|.
name|put
argument_list|(
literal|"Cut"
argument_list|,
literal|"ctrl X"
argument_list|)
expr_stmt|;
name|defKeyBinds
operator|.
name|put
argument_list|(
literal|"Copy"
argument_list|,
literal|"ctrl C"
argument_list|)
expr_stmt|;
name|defKeyBinds
operator|.
name|put
argument_list|(
literal|"Paste"
argument_list|,
literal|"ctrl V"
argument_list|)
expr_stmt|;
name|defKeyBinds
operator|.
name|put
argument_list|(
literal|"Undo"
argument_list|,
literal|"ctrl Z"
argument_list|)
expr_stmt|;
name|defKeyBinds
operator|.
name|put
argument_list|(
literal|"Redo"
argument_list|,
literal|"ctrl Y"
argument_list|)
expr_stmt|;
name|defKeyBinds
operator|.
name|put
argument_list|(
literal|"New article"
argument_list|,
literal|"ctrl shift A"
argument_list|)
expr_stmt|;
name|defKeyBinds
operator|.
name|put
argument_list|(
literal|"New book"
argument_list|,
literal|"ctrl shift B"
argument_list|)
expr_stmt|;
name|defKeyBinds
operator|.
name|put
argument_list|(
literal|"New phdthesis"
argument_list|,
literal|"ctrl shift T"
argument_list|)
expr_stmt|;
name|defKeyBinds
operator|.
name|put
argument_list|(
literal|"New inbook"
argument_list|,
literal|"ctrl shift I"
argument_list|)
expr_stmt|;
name|defKeyBinds
operator|.
name|put
argument_list|(
literal|"New mastersthesis"
argument_list|,
literal|"ctrl shift M"
argument_list|)
expr_stmt|;
name|defKeyBinds
operator|.
name|put
argument_list|(
literal|"New proceedings"
argument_list|,
literal|"ctrl shift P"
argument_list|)
expr_stmt|;
name|defKeyBinds
operator|.
name|put
argument_list|(
literal|"New unpublished"
argument_list|,
literal|"ctrl shift U"
argument_list|)
expr_stmt|;
name|defKeyBinds
operator|.
name|put
argument_list|(
literal|"Edit strings"
argument_list|,
literal|"ctrl shift S"
argument_list|)
expr_stmt|;
name|defKeyBinds
operator|.
name|put
argument_list|(
literal|"Edit preamble"
argument_list|,
literal|"ctrl P"
argument_list|)
expr_stmt|;
name|defKeyBinds
operator|.
name|put
argument_list|(
literal|"Select all"
argument_list|,
literal|"ctrl A"
argument_list|)
expr_stmt|;
name|defKeyBinds
operator|.
name|put
argument_list|(
literal|"Toggle groups"
argument_list|,
literal|"ctrl shift G"
argument_list|)
expr_stmt|;
block|}
DECL|method|getNextUnit (Reader data)
specifier|private
name|String
name|getNextUnit
parameter_list|(
name|Reader
name|data
parameter_list|)
throws|throws
name|IOException
block|{
name|int
name|c
decl_stmt|;
name|boolean
name|escape
init|=
literal|false
decl_stmt|,
name|done
init|=
literal|false
decl_stmt|;
name|StringBuffer
name|res
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
while|while
condition|(
operator|!
name|done
operator|&&
operator|(
operator|(
name|c
operator|=
name|data
operator|.
name|read
argument_list|()
operator|)
operator|!=
operator|-
literal|1
operator|)
condition|)
block|{
if|if
condition|(
name|c
operator|==
literal|'\\'
condition|)
block|{
if|if
condition|(
operator|!
name|escape
condition|)
name|escape
operator|=
literal|true
expr_stmt|;
else|else
block|{
name|escape
operator|=
literal|false
expr_stmt|;
name|res
operator|.
name|append
argument_list|(
literal|'\\'
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
if|if
condition|(
name|c
operator|==
literal|';'
condition|)
block|{
if|if
condition|(
operator|!
name|escape
condition|)
name|done
operator|=
literal|true
expr_stmt|;
else|else
name|res
operator|.
name|append
argument_list|(
literal|';'
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|res
operator|.
name|append
argument_list|(
operator|(
name|char
operator|)
name|c
argument_list|)
expr_stmt|;
block|}
name|escape
operator|=
literal|false
expr_stmt|;
block|}
block|}
if|if
condition|(
name|res
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
return|return
name|res
operator|.
name|toString
argument_list|()
return|;
else|else
return|return
literal|null
return|;
block|}
DECL|method|makeEscape (String s)
specifier|private
name|String
name|makeEscape
parameter_list|(
name|String
name|s
parameter_list|)
block|{
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|int
name|c
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
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|c
operator|=
name|s
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
expr_stmt|;
if|if
condition|(
operator|(
name|c
operator|==
literal|'\\'
operator|)
operator|||
operator|(
name|c
operator|==
literal|';'
operator|)
condition|)
name|sb
operator|.
name|append
argument_list|(
literal|'\\'
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
operator|(
name|char
operator|)
name|c
argument_list|)
expr_stmt|;
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
end_class

end_unit

