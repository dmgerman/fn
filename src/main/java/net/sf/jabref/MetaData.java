begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|Reader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|StringReader
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
name|Collections
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
name|Iterator
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
name|TreeMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Vector
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
name|exporter
operator|.
name|FieldFormatterCleanups
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
name|importer
operator|.
name|fileformat
operator|.
name|ParseException
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
name|logic
operator|.
name|l10n
operator|.
name|Localization
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
name|labelpattern
operator|.
name|AbstractLabelPattern
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
name|labelpattern
operator|.
name|DatabaseLabelPattern
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
name|util
operator|.
name|strings
operator|.
name|StringUtil
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
name|sql
operator|.
name|DBStrings
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
name|MetaData
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|META_FLAG
specifier|public
specifier|static
specifier|final
name|String
name|META_FLAG
init|=
literal|"jabref-meta: "
decl_stmt|;
DECL|field|SAVE_ORDER_CONFIG
specifier|private
specifier|static
specifier|final
name|String
name|SAVE_ORDER_CONFIG
init|=
literal|"saveOrderConfig"
decl_stmt|;
DECL|field|SAVE_ACTIONS
specifier|private
specifier|static
specifier|final
name|String
name|SAVE_ACTIONS
init|=
literal|"saveActions"
decl_stmt|;
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
DECL|field|DATABASE_TYPE
specifier|private
specifier|static
specifier|final
name|String
name|DATABASE_TYPE
init|=
literal|"databaseType"
decl_stmt|;
DECL|field|GROUPSTREE
specifier|private
specifier|static
specifier|final
name|String
name|GROUPSTREE
init|=
literal|"groupstree"
decl_stmt|;
DECL|field|FILE_DIRECTORY
specifier|private
specifier|static
specifier|final
name|String
name|FILE_DIRECTORY
init|=
name|Globals
operator|.
name|FILE_FIELD
operator|+
name|Globals
operator|.
name|DIR_SUFFIX
decl_stmt|;
DECL|field|SELECTOR_META_PREFIX
specifier|public
specifier|static
specifier|final
name|String
name|SELECTOR_META_PREFIX
init|=
literal|"selector_"
decl_stmt|;
DECL|field|PROTECTED_FLAG_META
specifier|private
specifier|static
specifier|final
name|String
name|PROTECTED_FLAG_META
init|=
literal|"protectedFlag"
decl_stmt|;
DECL|field|metaData
specifier|private
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|String
argument_list|>
argument_list|>
name|metaData
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|groupsRoot
specifier|private
name|GroupTreeNode
name|groupsRoot
decl_stmt|;
DECL|field|labelPattern
specifier|private
name|AbstractLabelPattern
name|labelPattern
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
DECL|field|encoding
specifier|private
name|Charset
name|encoding
init|=
name|Globals
operator|.
name|prefs
operator|.
name|getDefaultEncoding
argument_list|()
decl_stmt|;
comment|/**      * The MetaData object stores all meta data sets in Vectors. To ensure that      * the data is written correctly to string, the user of a meta data Vector      * must simply make sure the appropriate changes are reflected in the Vector      * it has been passed.      */
DECL|method|MetaData (Map<String, String> inData)
specifier|private
name|MetaData
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|inData
parameter_list|)
throws|throws
name|ParseException
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|inData
argument_list|)
expr_stmt|;
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
name|entry
range|:
name|inData
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|StringReader
name|data
init|=
operator|new
name|StringReader
argument_list|(
name|entry
operator|.
name|getValue
argument_list|()
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|orderedData
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
comment|// We must allow for ; and \ in escape sequences.
try|try
block|{
name|Optional
argument_list|<
name|String
argument_list|>
name|unit
decl_stmt|;
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
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|orderedData
operator|.
name|add
argument_list|(
name|unit
operator|.
name|get
argument_list|()
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
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Weird error while parsing meta data."
argument_list|,
name|ex
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|GROUPSTREE
operator|.
name|equals
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|)
condition|)
block|{
name|putGroups
argument_list|(
name|orderedData
argument_list|)
expr_stmt|;
comment|// the keys "groupsversion" and "groups" were used in JabRef versions around 1.3, we will not support them anymore
block|}
elseif|else
if|if
condition|(
name|SAVE_ACTIONS
operator|.
name|equals
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|)
condition|)
block|{
name|setSaveActions
argument_list|(
name|FieldFormatterCleanups
operator|.
name|parse
argument_list|(
name|orderedData
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|putData
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|,
name|orderedData
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|/**      * The MetaData object can be constructed with no data in it.      */
DECL|method|MetaData ()
specifier|public
name|MetaData
parameter_list|()
block|{
comment|// No data
block|}
DECL|method|parse (Map<String, String> data)
specifier|public
specifier|static
name|MetaData
name|parse
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|data
parameter_list|)
throws|throws
name|ParseException
block|{
return|return
operator|new
name|MetaData
argument_list|(
name|data
argument_list|)
return|;
block|}
DECL|method|getSaveOrderConfig ()
specifier|public
name|Optional
argument_list|<
name|SaveOrderConfig
argument_list|>
name|getSaveOrderConfig
parameter_list|()
block|{
name|List
argument_list|<
name|String
argument_list|>
name|storedSaveOrderConfig
init|=
name|getData
argument_list|(
name|SAVE_ORDER_CONFIG
argument_list|)
decl_stmt|;
if|if
condition|(
name|storedSaveOrderConfig
operator|!=
literal|null
condition|)
block|{
return|return
name|Optional
operator|.
name|of
argument_list|(
name|SaveOrderConfig
operator|.
name|parse
argument_list|(
name|storedSaveOrderConfig
argument_list|)
argument_list|)
return|;
block|}
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
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
name|SELECTOR_META_PREFIX
operator|+
literal|"keywords"
argument_list|,
operator|new
name|Vector
argument_list|<>
argument_list|()
argument_list|)
expr_stmt|;
name|metaData
operator|.
name|put
argument_list|(
name|SELECTOR_META_PREFIX
operator|+
literal|"author"
argument_list|,
operator|new
name|Vector
argument_list|<>
argument_list|()
argument_list|)
expr_stmt|;
name|metaData
operator|.
name|put
argument_list|(
name|SELECTOR_META_PREFIX
operator|+
literal|"journal"
argument_list|,
operator|new
name|Vector
argument_list|<>
argument_list|()
argument_list|)
expr_stmt|;
name|metaData
operator|.
name|put
argument_list|(
name|SELECTOR_META_PREFIX
operator|+
literal|"publisher"
argument_list|,
operator|new
name|Vector
argument_list|<>
argument_list|()
argument_list|)
expr_stmt|;
name|metaData
operator|.
name|put
argument_list|(
name|SELECTOR_META_PREFIX
operator|+
literal|"review"
argument_list|,
operator|new
name|Vector
argument_list|<>
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
comment|/**      * Retrieves the stored meta data.      *      * @param key the key to look up      * @return null if no data is found      */
DECL|method|getData (String key)
specifier|public
name|List
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
comment|/**      * Removes the given key from metadata.      * Nothing is done if key is not found.      *      * @param key the key to remove      */
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
DECL|method|putData (String key, List<String> orderedData)
specifier|public
name|void
name|putData
parameter_list|(
name|String
name|key
parameter_list|,
name|List
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
comment|/**      * Parse the groups metadata string      *      * @param orderedData The vector of metadata strings      */
DECL|method|putGroups (List<String> orderedData)
specifier|private
name|void
name|putGroups
parameter_list|(
name|List
argument_list|<
name|String
argument_list|>
name|orderedData
parameter_list|)
throws|throws
name|ParseException
block|{
try|try
block|{
name|groupsRoot
operator|=
name|GroupTreeNode
operator|.
name|parse
argument_list|(
name|orderedData
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ParseException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|ParseException
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Group tree could not be parsed. If you save the BibTeX database, all groups will be lost."
argument_list|)
argument_list|,
name|e
argument_list|)
throw|;
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
block|}
comment|/**      * Reads the next unit. Units are delimited by ';'.      */
DECL|method|getNextUnit (Reader reader)
specifier|private
specifier|static
name|Optional
argument_list|<
name|String
argument_list|>
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
name|Optional
operator|.
name|of
argument_list|(
name|res
operator|.
name|toString
argument_list|()
argument_list|)
return|;
block|}
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
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
comment|/**      * @return the stored label patterns      */
DECL|method|getLabelPattern ()
specifier|public
name|AbstractLabelPattern
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
name|DatabaseLabelPattern
argument_list|()
expr_stmt|;
comment|// read the data from the metadata and store it into the labelPattern
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
name|PREFIX_KEYPATTERN
argument_list|)
condition|)
block|{
name|List
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
name|List
argument_list|<
name|String
argument_list|>
name|defaultPattern
init|=
name|getData
argument_list|(
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
comment|/**      * Updates the stored key patterns to the given key patterns.      *      * @param labelPattern the key patterns to update to.<br />      *                     A reference to this object is stored internally and is returned at getLabelPattern();      */
DECL|method|setLabelPattern (AbstractLabelPattern labelPattern)
specifier|public
name|void
name|setLabelPattern
parameter_list|(
name|AbstractLabelPattern
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
name|Set
argument_list|<
name|String
argument_list|>
name|allKeys
init|=
name|labelPattern
operator|.
name|getAllKeys
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|key
range|:
name|allKeys
control|)
block|{
name|String
name|metaDataKey
init|=
name|PREFIX_KEYPATTERN
operator|+
name|key
decl_stmt|;
if|if
condition|(
operator|!
name|labelPattern
operator|.
name|isDefaultValue
argument_list|(
name|key
argument_list|)
condition|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|data
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|data
operator|.
name|add
argument_list|(
name|labelPattern
operator|.
name|getValue
argument_list|(
name|key
argument_list|)
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
name|KEYPATTERNDEFAULT
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|List
argument_list|<
name|String
argument_list|>
name|data
init|=
operator|new
name|ArrayList
argument_list|<>
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
DECL|method|getSaveActions ()
specifier|public
name|Optional
argument_list|<
name|FieldFormatterCleanups
argument_list|>
name|getSaveActions
parameter_list|()
block|{
if|if
condition|(
name|this
operator|.
name|getData
argument_list|(
name|SAVE_ACTIONS
argument_list|)
operator|==
literal|null
condition|)
block|{
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
else|else
block|{
return|return
name|Optional
operator|.
name|of
argument_list|(
name|FieldFormatterCleanups
operator|.
name|parse
argument_list|(
name|getData
argument_list|(
name|SAVE_ACTIONS
argument_list|)
argument_list|)
argument_list|)
return|;
block|}
block|}
DECL|method|getMode ()
specifier|public
name|Optional
argument_list|<
name|BibDatabaseMode
argument_list|>
name|getMode
parameter_list|()
block|{
name|List
argument_list|<
name|String
argument_list|>
name|data
init|=
name|getData
argument_list|(
name|DATABASE_TYPE
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|data
operator|==
literal|null
operator|)
operator|||
name|data
operator|.
name|isEmpty
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
return|return
name|Optional
operator|.
name|of
argument_list|(
name|BibDatabaseMode
operator|.
name|parse
argument_list|(
name|data
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
argument_list|)
return|;
block|}
DECL|method|isProtected ()
specifier|public
name|boolean
name|isProtected
parameter_list|()
block|{
name|List
argument_list|<
name|String
argument_list|>
name|data
init|=
name|getData
argument_list|(
name|PROTECTED_FLAG_META
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|data
operator|==
literal|null
operator|)
operator|||
name|data
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
literal|false
return|;
block|}
else|else
block|{
return|return
name|Boolean
operator|.
name|parseBoolean
argument_list|(
name|data
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
return|;
block|}
block|}
DECL|method|getContentSelectors (String fieldName)
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getContentSelectors
parameter_list|(
name|String
name|fieldName
parameter_list|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|contentSelectors
init|=
name|getData
argument_list|(
name|SELECTOR_META_PREFIX
operator|+
name|fieldName
argument_list|)
decl_stmt|;
if|if
condition|(
name|contentSelectors
operator|==
literal|null
condition|)
block|{
return|return
name|Collections
operator|.
name|emptyList
argument_list|()
return|;
block|}
else|else
block|{
return|return
name|contentSelectors
return|;
block|}
block|}
DECL|method|getDefaultFileDirectory ()
specifier|public
name|Optional
argument_list|<
name|String
argument_list|>
name|getDefaultFileDirectory
parameter_list|()
block|{
name|List
argument_list|<
name|String
argument_list|>
name|fileDirectory
init|=
name|getData
argument_list|(
name|FILE_DIRECTORY
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|fileDirectory
operator|==
literal|null
operator|)
operator|||
name|fileDirectory
operator|.
name|isEmpty
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
else|else
block|{
return|return
name|Optional
operator|.
name|of
argument_list|(
name|fileDirectory
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|trim
argument_list|()
argument_list|)
return|;
block|}
block|}
DECL|method|getUserFileDirectory (String user)
specifier|public
name|Optional
argument_list|<
name|String
argument_list|>
name|getUserFileDirectory
parameter_list|(
name|String
name|user
parameter_list|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|fileDirectory
init|=
name|getData
argument_list|(
name|FILE_DIRECTORY
operator|+
literal|'-'
operator|+
name|user
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|fileDirectory
operator|==
literal|null
operator|)
operator|||
name|fileDirectory
operator|.
name|isEmpty
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
else|else
block|{
return|return
name|Optional
operator|.
name|of
argument_list|(
name|fileDirectory
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|trim
argument_list|()
argument_list|)
return|;
block|}
block|}
comment|/**      * Writes all data in the format<key, serialized data>.      */
DECL|method|getAsStringMap ()
specifier|public
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|getAsStringMap
parameter_list|()
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|serializedMetaData
init|=
operator|new
name|TreeMap
argument_list|<>
argument_list|()
decl_stmt|;
comment|// first write all meta data except groups
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|String
argument_list|>
argument_list|>
name|metaItem
range|:
name|metaData
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
for|for
control|(
name|String
name|dataItem
range|:
name|metaItem
operator|.
name|getValue
argument_list|()
control|)
block|{
name|stringBuilder
operator|.
name|append
argument_list|(
name|StringUtil
operator|.
name|quote
argument_list|(
name|dataItem
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
comment|//in case of save actions, add an additional newline after the enabled flag
if|if
condition|(
name|metaItem
operator|.
name|getKey
argument_list|()
operator|.
name|equals
argument_list|(
name|SAVE_ACTIONS
argument_list|)
operator|&&
operator|(
literal|"enabled"
operator|.
name|equals
argument_list|(
name|dataItem
argument_list|)
operator|||
literal|"disabled"
operator|.
name|equals
argument_list|(
name|dataItem
argument_list|)
operator|)
condition|)
block|{
name|stringBuilder
operator|.
name|append
argument_list|(
name|Globals
operator|.
name|NEWLINE
argument_list|)
expr_stmt|;
block|}
block|}
name|String
name|serializedItem
init|=
name|stringBuilder
operator|.
name|toString
argument_list|()
decl_stmt|;
comment|// Only add non-empty values
if|if
condition|(
operator|!
name|serializedItem
operator|.
name|isEmpty
argument_list|()
operator|&&
operator|!
literal|";"
operator|.
name|equals
argument_list|(
name|serializedItem
argument_list|)
condition|)
block|{
name|serializedMetaData
operator|.
name|put
argument_list|(
name|metaItem
operator|.
name|getKey
argument_list|()
argument_list|,
name|serializedItem
argument_list|)
expr_stmt|;
block|}
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
name|getNumberOfChildren
argument_list|()
operator|>
literal|0
operator|)
condition|)
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
for|for
control|(
name|String
name|groupNode
range|:
name|groupsRoot
operator|.
name|getTreeAsString
argument_list|()
control|)
block|{
name|stringBuilder
operator|.
name|append
argument_list|(
name|StringUtil
operator|.
name|quote
argument_list|(
name|groupNode
argument_list|,
literal|";"
argument_list|,
literal|'\\'
argument_list|)
argument_list|)
expr_stmt|;
name|stringBuilder
operator|.
name|append
argument_list|(
literal|";"
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
block|}
name|serializedMetaData
operator|.
name|put
argument_list|(
name|GROUPSTREE
argument_list|,
name|stringBuilder
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|serializedMetaData
return|;
block|}
DECL|method|setSaveActions (FieldFormatterCleanups saveActions)
specifier|public
name|void
name|setSaveActions
parameter_list|(
name|FieldFormatterCleanups
name|saveActions
parameter_list|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|actionsSerialized
init|=
name|saveActions
operator|.
name|getAsStringList
argument_list|()
decl_stmt|;
name|putData
argument_list|(
name|SAVE_ACTIONS
argument_list|,
name|actionsSerialized
argument_list|)
expr_stmt|;
block|}
DECL|method|setSaveOrderConfig (SaveOrderConfig saveOrderConfig)
specifier|public
name|void
name|setSaveOrderConfig
parameter_list|(
name|SaveOrderConfig
name|saveOrderConfig
parameter_list|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|serialized
init|=
name|saveOrderConfig
operator|.
name|getAsStringList
argument_list|()
decl_stmt|;
name|putData
argument_list|(
name|SAVE_ORDER_CONFIG
argument_list|,
name|serialized
argument_list|)
expr_stmt|;
block|}
DECL|method|setMode (BibDatabaseMode mode)
specifier|public
name|void
name|setMode
parameter_list|(
name|BibDatabaseMode
name|mode
parameter_list|)
block|{
name|putData
argument_list|(
name|DATABASE_TYPE
argument_list|,
name|Collections
operator|.
name|singletonList
argument_list|(
name|mode
operator|.
name|getAsString
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|markAsProtected ()
specifier|public
name|void
name|markAsProtected
parameter_list|()
block|{
name|putData
argument_list|(
name|PROTECTED_FLAG_META
argument_list|,
name|Collections
operator|.
name|singletonList
argument_list|(
literal|"true"
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|setContentSelectors (String fieldName, List<String> contentSelectors)
specifier|public
name|void
name|setContentSelectors
parameter_list|(
name|String
name|fieldName
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|contentSelectors
parameter_list|)
block|{
name|putData
argument_list|(
name|SELECTOR_META_PREFIX
operator|+
name|fieldName
argument_list|,
name|contentSelectors
argument_list|)
expr_stmt|;
block|}
DECL|method|setDefaultFileDirectory (String path)
specifier|public
name|void
name|setDefaultFileDirectory
parameter_list|(
name|String
name|path
parameter_list|)
block|{
name|putData
argument_list|(
name|FILE_DIRECTORY
argument_list|,
name|Collections
operator|.
name|singletonList
argument_list|(
name|path
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|clearDefaultFileDirectory ()
specifier|public
name|void
name|clearDefaultFileDirectory
parameter_list|()
block|{
name|remove
argument_list|(
name|FILE_DIRECTORY
argument_list|)
expr_stmt|;
block|}
DECL|method|setUserFileDirectory (String user, String path)
specifier|public
name|void
name|setUserFileDirectory
parameter_list|(
name|String
name|user
parameter_list|,
name|String
name|path
parameter_list|)
block|{
name|putData
argument_list|(
name|FILE_DIRECTORY
operator|+
literal|'-'
operator|+
name|user
argument_list|,
name|Collections
operator|.
name|singletonList
argument_list|(
name|path
operator|.
name|trim
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|clearUserFileDirectory (String user)
specifier|public
name|void
name|clearUserFileDirectory
parameter_list|(
name|String
name|user
parameter_list|)
block|{
name|remove
argument_list|(
name|FILE_DIRECTORY
operator|+
literal|'-'
operator|+
name|user
argument_list|)
expr_stmt|;
block|}
DECL|method|clearContentSelectors (String fieldName)
specifier|public
name|void
name|clearContentSelectors
parameter_list|(
name|String
name|fieldName
parameter_list|)
block|{
name|remove
argument_list|(
name|SELECTOR_META_PREFIX
operator|+
name|fieldName
argument_list|)
expr_stmt|;
block|}
DECL|method|markAsNotProtected ()
specifier|public
name|void
name|markAsNotProtected
parameter_list|()
block|{
name|remove
argument_list|(
name|PROTECTED_FLAG_META
argument_list|)
expr_stmt|;
block|}
DECL|method|clearSaveActions ()
specifier|public
name|void
name|clearSaveActions
parameter_list|()
block|{
name|remove
argument_list|(
name|SAVE_ACTIONS
argument_list|)
expr_stmt|;
block|}
DECL|method|clearSaveOrderConfig ()
specifier|public
name|void
name|clearSaveOrderConfig
parameter_list|()
block|{
name|remove
argument_list|(
name|SAVE_ORDER_CONFIG
argument_list|)
expr_stmt|;
block|}
comment|/**      * Returns the encoding used during parsing.      */
DECL|method|getEncoding ()
specifier|public
name|Charset
name|getEncoding
parameter_list|()
block|{
return|return
name|encoding
return|;
block|}
DECL|method|setEncoding (Charset encoding)
specifier|public
name|void
name|setEncoding
parameter_list|(
name|Charset
name|encoding
parameter_list|)
block|{
name|this
operator|.
name|encoding
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|encoding
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

