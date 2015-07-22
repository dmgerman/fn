begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|java
operator|.
name|util
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
name|groups
operator|.
name|GroupTreeNode
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
name|migrations
operator|.
name|VersionHandling
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
name|labelPattern
operator|.
name|LabelPattern
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
name|sql
operator|.
name|DBStrings
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
name|util
operator|.
name|StringUtil
import|;
end_import

begin_class
DECL|class|MetaData
specifier|public
class|class
name|MetaData
implements|implements
name|Iterable
argument_list|<
name|String
argument_list|>
block|{
DECL|field|PREFIX_KEYPATTERN
specifier|private
specifier|static
specifier|final
name|String
name|PREFIX_KEYPATTERN
init|=
literal|"keypattern_"
decl_stmt|;
DECL|field|KEYPATTERNDEFAULT
specifier|private
specifier|static
specifier|final
name|String
name|KEYPATTERNDEFAULT
init|=
literal|"keypatterndefault"
decl_stmt|;
DECL|field|metaData
specifier|private
specifier|final
name|HashMap
argument_list|<
name|String
argument_list|,
name|Vector
argument_list|<
name|String
argument_list|>
argument_list|>
name|metaData
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|Vector
argument_list|<
name|String
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
DECL|field|groupsRoot
specifier|private
name|GroupTreeNode
name|groupsRoot
init|=
literal|null
decl_stmt|;
DECL|field|file
specifier|private
name|File
name|file
init|=
literal|null
decl_stmt|;
comment|// The File where this base gets saved.
DECL|field|groupTreeValid
specifier|private
name|boolean
name|groupTreeValid
init|=
literal|true
decl_stmt|;
DECL|field|labelPattern
specifier|private
name|LabelPattern
name|labelPattern
init|=
literal|null
decl_stmt|;
DECL|field|dbStrings
specifier|private
name|DBStrings
name|dbStrings
init|=
operator|new
name|DBStrings
argument_list|()
decl_stmt|;
comment|/**      * The MetaData object stores all meta data sets in Vectors. To ensure that      * the data is written correctly to string, the user of a meta data Vector      * must simply make sure the appropriate changes are reflected in the Vector      * it has been passed.      */
DECL|method|MetaData (HashMap<String, String> inData, BibtexDatabase db)
specifier|public
name|MetaData
parameter_list|(
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|inData
parameter_list|,
name|BibtexDatabase
name|db
parameter_list|)
block|{
name|boolean
name|groupsTreePresent
init|=
literal|false
decl_stmt|;
name|Vector
argument_list|<
name|String
argument_list|>
name|flatGroupsData
init|=
literal|null
decl_stmt|;
name|Vector
argument_list|<
name|String
argument_list|>
name|treeGroupsData
init|=
literal|null
decl_stmt|;
comment|// The first version (0) lacked a version specification,
comment|// thus this value defaults to 0.
name|int
name|groupsVersionOnDisk
init|=
literal|0
decl_stmt|;
if|if
condition|(
name|inData
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|String
name|key
range|:
name|inData
operator|.
name|keySet
argument_list|()
control|)
block|{
name|StringReader
name|data
init|=
operator|new
name|StringReader
argument_list|(
name|inData
operator|.
name|get
argument_list|(
name|key
argument_list|)
argument_list|)
decl_stmt|;
name|String
name|unit
decl_stmt|;
name|Vector
argument_list|<
name|String
argument_list|>
name|orderedData
init|=
operator|new
name|Vector
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
comment|// We must allow for ; and \ in escape sequences.
try|try
block|{
while|while
condition|(
operator|(
name|unit
operator|=
name|getNextUnit
argument_list|(
name|data
argument_list|)
operator|)
operator|!=
literal|null
condition|)
block|{
name|orderedData
operator|.
name|add
argument_list|(
name|unit
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"Weird error while parsing meta data."
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|key
operator|.
name|equals
argument_list|(
literal|"groupsversion"
argument_list|)
condition|)
block|{
if|if
condition|(
name|orderedData
operator|.
name|size
argument_list|()
operator|>=
literal|1
condition|)
block|{
name|groupsVersionOnDisk
operator|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|orderedData
operator|.
name|firstElement
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|key
operator|.
name|equals
argument_list|(
literal|"groupstree"
argument_list|)
condition|)
block|{
name|groupsTreePresent
operator|=
literal|true
expr_stmt|;
name|treeGroupsData
operator|=
name|orderedData
expr_stmt|;
comment|// save for later user
comment|// actual import operation is handled later because "groupsversion"
comment|// tag might not yet have been read
block|}
elseif|else
if|if
condition|(
name|key
operator|.
name|equals
argument_list|(
literal|"groups"
argument_list|)
condition|)
block|{
name|flatGroupsData
operator|=
name|orderedData
expr_stmt|;
block|}
else|else
block|{
name|putData
argument_list|(
name|key
argument_list|,
name|orderedData
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|// this possibly handles import of a previous groups version
if|if
condition|(
name|groupsTreePresent
condition|)
block|{
name|putGroups
argument_list|(
name|treeGroupsData
argument_list|,
name|db
argument_list|,
name|groupsVersionOnDisk
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|groupsTreePresent
operator|&&
operator|(
name|flatGroupsData
operator|!=
literal|null
operator|)
condition|)
block|{
try|try
block|{
name|groupsRoot
operator|=
name|VersionHandling
operator|.
name|importFlatGroups
argument_list|(
name|flatGroupsData
argument_list|)
expr_stmt|;
name|groupTreeValid
operator|=
literal|true
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IllegalArgumentException
name|ex
parameter_list|)
block|{
name|groupTreeValid
operator|=
literal|true
expr_stmt|;
block|}
block|}
block|}
comment|/**      * The MetaData object can be constructed with no data in it.      */
DECL|method|MetaData ()
specifier|public
name|MetaData
parameter_list|()
block|{      }
comment|/**      * Add default metadata for new database:      */
DECL|method|initializeNewDatabase ()
specifier|public
name|void
name|initializeNewDatabase
parameter_list|()
block|{
name|metaData
operator|.
name|put
argument_list|(
name|Globals
operator|.
name|SELECTOR_META_PREFIX
operator|+
literal|"keywords"
argument_list|,
operator|new
name|Vector
argument_list|<
name|String
argument_list|>
argument_list|()
argument_list|)
expr_stmt|;
name|metaData
operator|.
name|put
argument_list|(
name|Globals
operator|.
name|SELECTOR_META_PREFIX
operator|+
literal|"author"
argument_list|,
operator|new
name|Vector
argument_list|<
name|String
argument_list|>
argument_list|()
argument_list|)
expr_stmt|;
name|metaData
operator|.
name|put
argument_list|(
name|Globals
operator|.
name|SELECTOR_META_PREFIX
operator|+
literal|"journal"
argument_list|,
operator|new
name|Vector
argument_list|<
name|String
argument_list|>
argument_list|()
argument_list|)
expr_stmt|;
name|metaData
operator|.
name|put
argument_list|(
name|Globals
operator|.
name|SELECTOR_META_PREFIX
operator|+
literal|"publisher"
argument_list|,
operator|new
name|Vector
argument_list|<
name|String
argument_list|>
argument_list|()
argument_list|)
expr_stmt|;
name|metaData
operator|.
name|put
argument_list|(
name|Globals
operator|.
name|SELECTOR_META_PREFIX
operator|+
literal|"review"
argument_list|,
operator|new
name|Vector
argument_list|<
name|String
argument_list|>
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * @return Iterator on all keys stored in the metadata      */
annotation|@
name|Override
DECL|method|iterator ()
specifier|public
name|Iterator
argument_list|<
name|String
argument_list|>
name|iterator
parameter_list|()
block|{
return|return
name|metaData
operator|.
name|keySet
argument_list|()
operator|.
name|iterator
argument_list|()
return|;
block|}
comment|/**      * Retrieves the stored meta data.      *       * @param key the key to look up      * @return null if no data is found      */
DECL|method|getData (String key)
specifier|public
name|Vector
argument_list|<
name|String
argument_list|>
name|getData
parameter_list|(
name|String
name|key
parameter_list|)
block|{
return|return
name|metaData
operator|.
name|get
argument_list|(
name|key
argument_list|)
return|;
block|}
comment|/**      * Removes the given key from metadata.      * Nothing is done if key is not found.      *       * @param key the key to remove      */
DECL|method|remove (String key)
specifier|public
name|void
name|remove
parameter_list|(
name|String
name|key
parameter_list|)
block|{
name|metaData
operator|.
name|remove
argument_list|(
name|key
argument_list|)
expr_stmt|;
block|}
comment|/**      * Stores the specified data in this object, using the specified key. For      * certain keys (e.g. "groupstree"), the objects in orderedData are      * reconstructed from their textual (String) representation if they are of      * type String, and stored as an actual instance.      */
DECL|method|putData (String key, Vector<String> orderedData)
specifier|public
name|void
name|putData
parameter_list|(
name|String
name|key
parameter_list|,
name|Vector
argument_list|<
name|String
argument_list|>
name|orderedData
parameter_list|)
block|{
name|metaData
operator|.
name|put
argument_list|(
name|key
argument_list|,
name|orderedData
argument_list|)
expr_stmt|;
block|}
comment|/**      * Look up the directory set up for the given field type for this database.      * If no directory is set up, return that defined in global preferences.      * @param fieldName The field type      * @return The default directory for this field type.      */
DECL|method|getFileDirectory (String fieldName)
specifier|public
name|String
index|[]
name|getFileDirectory
parameter_list|(
name|String
name|fieldName
parameter_list|)
block|{
comment|// There can be up to three directory definitions for these files - the database's
comment|// metadata can specify a general directory and/or a user-specific directory, or
comment|// the preferences can specify one. The settings are prioritized in the following
comment|// order and the first defined setting is used: metadata user-specific directory,
comment|// metadata general directory, preferences directory.
name|String
name|key
init|=
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|USER_FILE_DIR_INDIVIDUAL
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|dirs
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|Vector
argument_list|<
name|String
argument_list|>
name|vec
init|=
name|getData
argument_list|(
name|key
argument_list|)
decl_stmt|;
if|if
condition|(
name|vec
operator|==
literal|null
condition|)
block|{
name|key
operator|=
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|USER_FILE_DIR
argument_list|)
expr_stmt|;
name|vec
operator|=
name|getData
argument_list|(
name|key
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|(
name|vec
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|vec
operator|.
name|size
argument_list|()
operator|>
literal|0
operator|)
condition|)
block|{
name|String
name|dir
decl_stmt|;
name|dir
operator|=
name|vec
operator|.
name|get
argument_list|(
literal|0
argument_list|)
expr_stmt|;
comment|// If this directory is relative, we try to interpret it as relative to
comment|// the file path of this bib file:
if|if
condition|(
operator|!
operator|(
operator|new
name|File
argument_list|(
name|dir
argument_list|)
operator|)
operator|.
name|isAbsolute
argument_list|()
operator|&&
operator|(
name|file
operator|!=
literal|null
operator|)
condition|)
block|{
name|String
name|relDir
decl_stmt|;
if|if
condition|(
name|dir
operator|.
name|equals
argument_list|(
literal|"."
argument_list|)
condition|)
block|{
comment|// if dir is only "current" directory, just use its parent (== real current directory) as path
name|relDir
operator|=
name|file
operator|.
name|getParent
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|relDir
operator|=
name|file
operator|.
name|getParent
argument_list|()
operator|+
name|System
operator|.
name|getProperty
argument_list|(
literal|"file.separator"
argument_list|)
operator|+
name|dir
expr_stmt|;
block|}
comment|// If this directory actually exists, it is very likely that the
comment|// user wants us to use it:
if|if
condition|(
operator|(
operator|new
name|File
argument_list|(
name|relDir
argument_list|)
operator|)
operator|.
name|exists
argument_list|()
condition|)
block|{
name|dir
operator|=
name|relDir
expr_stmt|;
block|}
block|}
name|dirs
operator|.
name|add
argument_list|(
name|dir
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|String
name|dir
init|=
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|fieldName
operator|+
literal|"Directory"
argument_list|)
decl_stmt|;
if|if
condition|(
name|dir
operator|!=
literal|null
condition|)
block|{
name|dirs
operator|.
name|add
argument_list|(
name|dir
argument_list|)
expr_stmt|;
block|}
block|}
comment|// Check if the bib file location should be included, and if so, if it is set:
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|BIB_LOCATION_AS_FILE_DIR
argument_list|)
operator|&&
operator|(
name|getFile
argument_list|()
operator|!=
literal|null
operator|)
condition|)
block|{
comment|// Check if we should add it as primary file dir (first in the list) or not:
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|BIB_LOC_AS_PRIMARY_DIR
argument_list|)
condition|)
block|{
name|dirs
operator|.
name|add
argument_list|(
literal|0
argument_list|,
name|getFile
argument_list|()
operator|.
name|getParent
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|dirs
operator|.
name|add
argument_list|(
name|getFile
argument_list|()
operator|.
name|getParent
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|dirs
operator|.
name|toArray
argument_list|(
operator|new
name|String
index|[
name|dirs
operator|.
name|size
argument_list|()
index|]
argument_list|)
return|;
block|}
comment|/**      * Parse the groups metadata string      * @param orderedData The vector of metadata strings      * @param db The BibtexDatabase this metadata belongs to      * @param version The group tree version      * @return true if parsing was successful, false otherwise      */
DECL|method|putGroups (Vector<String> orderedData, BibtexDatabase db, int version)
specifier|private
name|void
name|putGroups
parameter_list|(
name|Vector
argument_list|<
name|String
argument_list|>
name|orderedData
parameter_list|,
name|BibtexDatabase
name|db
parameter_list|,
name|int
name|version
parameter_list|)
block|{
try|try
block|{
name|groupsRoot
operator|=
name|VersionHandling
operator|.
name|importGroups
argument_list|(
name|orderedData
argument_list|,
name|db
argument_list|,
name|version
argument_list|)
expr_stmt|;
name|groupTreeValid
operator|=
literal|true
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
comment|// we cannot really do anything about this here
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
name|e
argument_list|)
expr_stmt|;
name|groupTreeValid
operator|=
literal|false
expr_stmt|;
block|}
block|}
DECL|method|getGroups ()
specifier|public
name|GroupTreeNode
name|getGroups
parameter_list|()
block|{
return|return
name|groupsRoot
return|;
block|}
comment|/**      * Sets a new group root node.<b>WARNING</b>: This invalidates everything      * returned by getGroups() so far!!!      */
DECL|method|setGroups (GroupTreeNode root)
specifier|public
name|void
name|setGroups
parameter_list|(
name|GroupTreeNode
name|root
parameter_list|)
block|{
name|groupsRoot
operator|=
name|root
expr_stmt|;
name|groupTreeValid
operator|=
literal|true
expr_stmt|;
block|}
comment|/**      * Writes all data to the specified writer, using each object's toString()      * method.      */
DECL|method|writeMetaData (Writer out)
specifier|public
name|void
name|writeMetaData
parameter_list|(
name|Writer
name|out
parameter_list|)
throws|throws
name|IOException
block|{
comment|// write all meta data except groups
name|SortedSet
argument_list|<
name|String
argument_list|>
name|sortedKeys
init|=
operator|new
name|TreeSet
argument_list|<
name|String
argument_list|>
argument_list|(
name|metaData
operator|.
name|keySet
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|key
range|:
name|sortedKeys
control|)
block|{
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|Vector
argument_list|<
name|String
argument_list|>
name|orderedData
init|=
name|metaData
operator|.
name|get
argument_list|(
name|key
argument_list|)
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"@comment{"
argument_list|)
operator|.
name|append
argument_list|(
name|GUIGlobals
operator|.
name|META_FLAG
argument_list|)
operator|.
name|append
argument_list|(
name|key
argument_list|)
operator|.
name|append
argument_list|(
literal|":"
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|j
init|=
literal|0
init|;
name|j
operator|<
name|orderedData
operator|.
name|size
argument_list|()
condition|;
name|j
operator|++
control|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|StringUtil
operator|.
name|quote
argument_list|(
name|orderedData
operator|.
name|elementAt
argument_list|(
name|j
argument_list|)
argument_list|,
literal|";"
argument_list|,
literal|'\\'
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|";"
argument_list|)
expr_stmt|;
block|}
name|sb
operator|.
name|append
argument_list|(
literal|"}"
argument_list|)
expr_stmt|;
name|wrapStringBuffer
argument_list|(
name|sb
argument_list|,
name|Globals
operator|.
name|METADATA_LINE_LENGTH
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|Globals
operator|.
name|NEWLINE
argument_list|)
expr_stmt|;
name|sb
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
name|sb
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|// write groups if present. skip this if only the root node exists
comment|// (which is always the AllEntriesGroup).
if|if
condition|(
operator|(
name|groupsRoot
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|groupsRoot
operator|.
name|getChildCount
argument_list|()
operator|>
literal|0
operator|)
condition|)
block|{
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
comment|// write version first
name|sb
operator|.
name|append
argument_list|(
literal|"@comment{"
argument_list|)
operator|.
name|append
argument_list|(
name|GUIGlobals
operator|.
name|META_FLAG
argument_list|)
operator|.
name|append
argument_list|(
literal|"groupsversion:"
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|""
operator|+
name|VersionHandling
operator|.
name|CURRENT_VERSION
operator|+
literal|";"
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"}"
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|Globals
operator|.
name|NEWLINE
argument_list|)
expr_stmt|;
name|sb
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
name|sb
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
comment|// now write actual groups
name|sb
operator|=
operator|new
name|StringBuffer
argument_list|()
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"@comment{"
argument_list|)
operator|.
name|append
argument_list|(
name|GUIGlobals
operator|.
name|META_FLAG
argument_list|)
operator|.
name|append
argument_list|(
literal|"groupstree:"
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|Globals
operator|.
name|NEWLINE
argument_list|)
expr_stmt|;
comment|// GroupsTreeNode.toString() uses "\n" for separation
name|StringTokenizer
name|tok
init|=
operator|new
name|StringTokenizer
argument_list|(
name|groupsRoot
operator|.
name|getTreeAsString
argument_list|()
argument_list|,
name|Globals
operator|.
name|NEWLINE
argument_list|)
decl_stmt|;
while|while
condition|(
name|tok
operator|.
name|hasMoreTokens
argument_list|()
condition|)
block|{
name|StringBuffer
name|s
init|=
operator|new
name|StringBuffer
argument_list|(
name|StringUtil
operator|.
name|quote
argument_list|(
name|tok
operator|.
name|nextToken
argument_list|()
argument_list|,
literal|";"
argument_list|,
literal|'\\'
argument_list|)
operator|+
literal|";"
argument_list|)
decl_stmt|;
name|wrapStringBuffer
argument_list|(
name|s
argument_list|,
name|Globals
operator|.
name|METADATA_LINE_LENGTH
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|s
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|Globals
operator|.
name|NEWLINE
argument_list|)
expr_stmt|;
block|}
name|sb
operator|.
name|append
argument_list|(
literal|"}"
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|Globals
operator|.
name|NEWLINE
argument_list|)
expr_stmt|;
name|sb
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
name|sb
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|wrapStringBuffer (StringBuffer sb, int lineLength)
specifier|private
name|void
name|wrapStringBuffer
parameter_list|(
name|StringBuffer
name|sb
parameter_list|,
name|int
name|lineLength
parameter_list|)
block|{
for|for
control|(
name|int
name|i
init|=
name|lineLength
init|;
name|i
operator|<
name|sb
operator|.
name|length
argument_list|()
condition|;
name|i
operator|+=
name|lineLength
operator|+
name|Globals
operator|.
name|NEWLINE_LENGTH
control|)
block|{
name|sb
operator|.
name|insert
argument_list|(
name|i
argument_list|,
name|Globals
operator|.
name|NEWLINE
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Reads the next unit. Units are delimited by ';'.       */
DECL|method|getNextUnit (Reader reader)
specifier|private
name|String
name|getNextUnit
parameter_list|(
name|Reader
name|reader
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
decl_stmt|;
name|StringBuilder
name|res
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
while|while
condition|(
operator|(
name|c
operator|=
name|reader
operator|.
name|read
argument_list|()
operator|)
operator|!=
operator|-
literal|1
condition|)
block|{
if|if
condition|(
name|escape
condition|)
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
name|escape
operator|=
literal|false
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|c
operator|==
literal|'\\'
condition|)
block|{
name|escape
operator|=
literal|true
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|c
operator|==
literal|';'
condition|)
block|{
break|break;
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
block|{
return|return
name|res
operator|.
name|toString
argument_list|()
return|;
block|}
return|return
literal|null
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
DECL|method|setFile (File file)
specifier|public
name|void
name|setFile
parameter_list|(
name|File
name|file
parameter_list|)
block|{
name|this
operator|.
name|file
operator|=
name|file
expr_stmt|;
block|}
DECL|method|getDBStrings ()
specifier|public
name|DBStrings
name|getDBStrings
parameter_list|()
block|{
return|return
name|dbStrings
return|;
block|}
DECL|method|setDBStrings (DBStrings dbStrings)
specifier|public
name|void
name|setDBStrings
parameter_list|(
name|DBStrings
name|dbStrings
parameter_list|)
block|{
name|this
operator|.
name|dbStrings
operator|=
name|dbStrings
expr_stmt|;
block|}
DECL|method|isGroupTreeValid ()
specifier|public
name|boolean
name|isGroupTreeValid
parameter_list|()
block|{
return|return
name|groupTreeValid
return|;
block|}
comment|/**      * @return the stored label patterns      */
DECL|method|getLabelPattern ()
specifier|public
name|LabelPattern
name|getLabelPattern
parameter_list|()
block|{
if|if
condition|(
name|labelPattern
operator|!=
literal|null
condition|)
block|{
return|return
name|labelPattern
return|;
block|}
name|labelPattern
operator|=
operator|new
name|LabelPattern
argument_list|()
expr_stmt|;
comment|// the parent label pattern of a BibTeX data base is the global pattern stored in the preferences
name|labelPattern
operator|.
name|setParent
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getKeyPattern
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|String
name|key
range|:
name|this
control|)
block|{
if|if
condition|(
name|key
operator|.
name|startsWith
argument_list|(
name|MetaData
operator|.
name|PREFIX_KEYPATTERN
argument_list|)
condition|)
block|{
name|Vector
argument_list|<
name|String
argument_list|>
name|value
init|=
name|getData
argument_list|(
name|key
argument_list|)
decl_stmt|;
name|String
name|type
init|=
name|key
operator|.
name|substring
argument_list|(
name|MetaData
operator|.
name|PREFIX_KEYPATTERN
operator|.
name|length
argument_list|()
argument_list|)
decl_stmt|;
name|labelPattern
operator|.
name|addLabelPattern
argument_list|(
name|type
argument_list|,
name|value
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
name|Vector
argument_list|<
name|String
argument_list|>
name|defaultPattern
init|=
name|getData
argument_list|(
name|MetaData
operator|.
name|KEYPATTERNDEFAULT
argument_list|)
decl_stmt|;
if|if
condition|(
name|defaultPattern
operator|!=
literal|null
condition|)
block|{
name|labelPattern
operator|.
name|setDefaultValue
argument_list|(
name|defaultPattern
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|labelPattern
return|;
block|}
comment|/**      * Updates the stored key patterns to the given key patterns.      *       * @param labelPattern the key patterns to update to.<br />      * A reference to this object is stored internally and is returned at getLabelPattern();      */
DECL|method|setLabelPattern (LabelPattern labelPattern)
specifier|public
name|void
name|setLabelPattern
parameter_list|(
name|LabelPattern
name|labelPattern
parameter_list|)
block|{
comment|// remove all keypatterns from metadata
name|Iterator
argument_list|<
name|String
argument_list|>
name|iterator
init|=
name|this
operator|.
name|iterator
argument_list|()
decl_stmt|;
while|while
condition|(
name|iterator
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|String
name|key
init|=
name|iterator
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|key
operator|.
name|startsWith
argument_list|(
name|MetaData
operator|.
name|PREFIX_KEYPATTERN
argument_list|)
condition|)
block|{
name|iterator
operator|.
name|remove
argument_list|()
expr_stmt|;
block|}
block|}
comment|// set new value if it is not a default value
for|for
control|(
name|String
name|key
range|:
name|labelPattern
operator|.
name|keySet
argument_list|()
control|)
block|{
name|String
name|metaDataKey
init|=
name|MetaData
operator|.
name|PREFIX_KEYPATTERN
operator|+
name|key
decl_stmt|;
name|ArrayList
argument_list|<
name|String
argument_list|>
name|value
init|=
name|labelPattern
operator|.
name|get
argument_list|(
name|key
argument_list|)
decl_stmt|;
if|if
condition|(
name|value
operator|!=
literal|null
condition|)
block|{
name|Vector
argument_list|<
name|String
argument_list|>
name|data
init|=
operator|new
name|Vector
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|data
operator|.
name|add
argument_list|(
name|value
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|putData
argument_list|(
name|metaDataKey
argument_list|,
name|data
argument_list|)
expr_stmt|;
block|}
block|}
comment|// store default pattern
if|if
condition|(
name|labelPattern
operator|.
name|getDefaultValue
argument_list|()
operator|==
literal|null
condition|)
block|{
name|this
operator|.
name|remove
argument_list|(
name|MetaData
operator|.
name|KEYPATTERNDEFAULT
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|Vector
argument_list|<
name|String
argument_list|>
name|data
init|=
operator|new
name|Vector
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|data
operator|.
name|add
argument_list|(
name|labelPattern
operator|.
name|getDefaultValue
argument_list|()
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|putData
argument_list|(
name|MetaData
operator|.
name|KEYPATTERNDEFAULT
argument_list|,
name|data
argument_list|)
expr_stmt|;
block|}
name|this
operator|.
name|labelPattern
operator|=
name|labelPattern
expr_stmt|;
block|}
block|}
end_class

end_unit

