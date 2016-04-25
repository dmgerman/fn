begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General public static License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General public static License for more details.      You should have received a copy of the GNU General public static License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
end_comment

begin_package
DECL|package|net.sf.jabref.sql.importer
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|sql
operator|.
name|importer
package|;
end_package

begin_import
import|import
name|java
operator|.
name|sql
operator|.
name|Connection
import|;
end_import

begin_import
import|import
name|java
operator|.
name|sql
operator|.
name|ResultSet
import|;
end_import

begin_import
import|import
name|java
operator|.
name|sql
operator|.
name|SQLException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|sql
operator|.
name|Statement
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
name|Arrays
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
name|LinkedHashMap
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
name|Optional
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|StringJoiner
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
name|MetaData
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
name|AbstractGroup
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
name|AllEntriesGroup
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
name|ExplicitGroup
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
name|GroupHierarchyType
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
name|groups
operator|.
name|KeywordGroup
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
name|SearchGroup
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
name|model
operator|.
name|database
operator|.
name|BibDatabase
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
name|EntryType
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
name|IdGenerator
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
name|sql
operator|.
name|Database
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
name|SQLUtil
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
comment|/**  * @author ifsteinm.  *<p>  *         Jan 20th Abstract Class to provide main features to import entries from a DB. To insert a new DB it is  *         necessary to extend this class and add the DB name the enum available at  *         net.sf.jabref.sql.DBImporterAndExporterFactory (and to the GUI). This class and its subclasses import  *         database, entries and related stuff from a DB to bib. Each exported database is imported as a new JabRef  *         (bib) database, presented on a new tab  */
end_comment

begin_class
DECL|class|DatabaseImporter
specifier|public
class|class
name|DatabaseImporter
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
name|DatabaseImporter
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|COLUMNS_NOT_CONSIDERED_FOR_ENTRIES
specifier|private
specifier|static
specifier|final
name|List
argument_list|<
name|String
argument_list|>
name|COLUMNS_NOT_CONSIDERED_FOR_ENTRIES
init|=
name|Arrays
operator|.
name|asList
argument_list|(
literal|"cite_key"
argument_list|,
literal|"entry_types_id"
argument_list|,
literal|"database_id"
argument_list|,
literal|"jabref_eid"
argument_list|,
literal|"entries_id"
argument_list|)
decl_stmt|;
DECL|field|database
specifier|private
specifier|final
name|Database
name|database
decl_stmt|;
DECL|method|DatabaseImporter (Database database)
specifier|public
name|DatabaseImporter
parameter_list|(
name|Database
name|database
parameter_list|)
block|{
name|this
operator|.
name|database
operator|=
name|database
expr_stmt|;
block|}
comment|/**      * @param conn Connection object to the database      * @return A ResultSet with column name for the entries table      * @throws SQLException      */
DECL|method|readColumnNames (Connection conn)
specifier|private
name|List
argument_list|<
name|String
argument_list|>
name|readColumnNames
parameter_list|(
name|Connection
name|conn
parameter_list|)
throws|throws
name|SQLException
block|{
name|String
name|query
init|=
name|database
operator|.
name|getReadColumnNamesQuery
argument_list|()
decl_stmt|;
try|try
init|(
name|Statement
name|statement
init|=
name|conn
operator|.
name|createStatement
argument_list|()
init|;
name|ResultSet
name|rsColumns
operator|=
name|statement
operator|.
name|executeQuery
argument_list|(
name|query
argument_list|)
init|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|colNames
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
while|while
condition|(
name|rsColumns
operator|.
name|next
argument_list|()
condition|)
block|{
name|colNames
operator|.
name|add
argument_list|(
name|rsColumns
operator|.
name|getString
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|colNames
return|;
block|}
block|}
comment|/**      * Worker method to perform the import from a database      *      * @param dbs  The necessary database connection information      * @param mode      * @return An ArrayList containing pairs of Objects. Each position of the ArrayList stores three Objects: a      * BibDatabase, a MetaData and a String with the bib database name stored in the DBMS      * @throws SQLException      * @throws ClassNotFoundException      * @throws InstantiationException      * @throws IllegalAccessException      * @throws Exception      */
DECL|method|performImport (DBStrings dbs, List<String> listOfDBs, BibDatabaseMode mode)
specifier|public
name|List
argument_list|<
name|DBImporterResult
argument_list|>
name|performImport
parameter_list|(
name|DBStrings
name|dbs
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|listOfDBs
parameter_list|,
name|BibDatabaseMode
name|mode
parameter_list|)
throws|throws
name|IllegalAccessException
throws|,
name|InstantiationException
throws|,
name|ClassNotFoundException
throws|,
name|SQLException
block|{
name|List
argument_list|<
name|DBImporterResult
argument_list|>
name|result
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
try|try
init|(
name|Connection
name|conn
init|=
name|this
operator|.
name|connectToDB
argument_list|(
name|dbs
argument_list|)
init|)
block|{
name|Iterator
argument_list|<
name|String
argument_list|>
name|itLista
init|=
name|listOfDBs
operator|.
name|iterator
argument_list|()
decl_stmt|;
name|StringJoiner
name|stringJoiner
init|=
operator|new
name|StringJoiner
argument_list|(
literal|","
argument_list|,
literal|"("
argument_list|,
literal|")"
argument_list|)
decl_stmt|;
while|while
condition|(
name|itLista
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|stringJoiner
operator|.
name|add
argument_list|(
literal|"'"
operator|+
name|itLista
operator|.
name|next
argument_list|()
operator|+
literal|"'"
argument_list|)
expr_stmt|;
block|}
name|String
name|query
init|=
name|SQLUtil
operator|.
name|queryAllFromTable
argument_list|(
literal|"jabref_database WHERE database_name IN "
operator|+
name|stringJoiner
operator|.
name|toString
argument_list|()
argument_list|)
decl_stmt|;
try|try
init|(
name|Statement
name|statement
init|=
name|conn
operator|.
name|createStatement
argument_list|()
init|;
name|ResultSet
name|rsDatabase
operator|=
name|statement
operator|.
name|executeQuery
argument_list|(
name|query
argument_list|)
init|)
block|{
while|while
condition|(
name|rsDatabase
operator|.
name|next
argument_list|()
condition|)
block|{
name|BibDatabase
name|database
init|=
operator|new
name|BibDatabase
argument_list|()
decl_stmt|;
comment|// Find entry type IDs and their mappings to type names:
name|HashMap
argument_list|<
name|String
argument_list|,
name|EntryType
argument_list|>
name|types
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
try|try
init|(
name|Statement
name|entryTypes
init|=
name|conn
operator|.
name|createStatement
argument_list|()
init|;
name|ResultSet
name|rsEntryType
operator|=
name|entryTypes
operator|.
name|executeQuery
argument_list|(
name|SQLUtil
operator|.
name|queryAllFromTable
argument_list|(
literal|"entry_types"
argument_list|)
argument_list|)
init|)
block|{
while|while
condition|(
name|rsEntryType
operator|.
name|next
argument_list|()
condition|)
block|{
name|Optional
argument_list|<
name|EntryType
argument_list|>
name|entryType
init|=
name|EntryTypes
operator|.
name|getType
argument_list|(
name|rsEntryType
operator|.
name|getString
argument_list|(
literal|"label"
argument_list|)
argument_list|,
name|mode
argument_list|)
decl_stmt|;
if|if
condition|(
name|entryType
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|types
operator|.
name|put
argument_list|(
name|rsEntryType
operator|.
name|getString
argument_list|(
literal|"entry_types_id"
argument_list|)
argument_list|,
name|entryType
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
name|List
argument_list|<
name|String
argument_list|>
name|colNames
init|=
name|this
operator|.
name|readColumnNames
argument_list|(
name|conn
argument_list|)
operator|.
name|stream
argument_list|()
operator|.
name|filter
argument_list|(
name|column
lambda|->
operator|!
name|COLUMNS_NOT_CONSIDERED_FOR_ENTRIES
operator|.
name|contains
argument_list|(
name|column
argument_list|)
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
specifier|final
name|String
name|database_id
init|=
name|rsDatabase
operator|.
name|getString
argument_list|(
literal|"database_id"
argument_list|)
decl_stmt|;
comment|// Read the entries and create BibEntry instances:
name|HashMap
argument_list|<
name|String
argument_list|,
name|BibEntry
argument_list|>
name|entries
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
try|try
init|(
name|Statement
name|entryStatement
init|=
name|conn
operator|.
name|createStatement
argument_list|()
init|;
name|ResultSet
name|rsEntries
operator|=
name|entryStatement
operator|.
name|executeQuery
argument_list|(
name|SQLUtil
operator|.
name|queryAllFromTable
argument_list|(
literal|"entries WHERE database_id= '"
operator|+
name|database_id
operator|+
literal|"';"
argument_list|)
argument_list|)
init|)
block|{
while|while
condition|(
name|rsEntries
operator|.
name|next
argument_list|()
condition|)
block|{
name|String
name|id
init|=
name|rsEntries
operator|.
name|getString
argument_list|(
literal|"entries_id"
argument_list|)
decl_stmt|;
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|(
name|IdGenerator
operator|.
name|next
argument_list|()
argument_list|,
name|types
operator|.
name|get
argument_list|(
name|rsEntries
operator|.
name|getString
argument_list|(
literal|"entry_types_id"
argument_list|)
argument_list|)
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|BibEntry
operator|.
name|KEY_FIELD
argument_list|,
name|rsEntries
operator|.
name|getString
argument_list|(
literal|"cite_key"
argument_list|)
argument_list|)
expr_stmt|;
for|for
control|(
name|String
name|col
range|:
name|colNames
control|)
block|{
name|String
name|value
init|=
name|rsEntries
operator|.
name|getString
argument_list|(
name|col
argument_list|)
decl_stmt|;
if|if
condition|(
name|value
operator|!=
literal|null
condition|)
block|{
name|col
operator|=
name|col
operator|.
name|charAt
argument_list|(
name|col
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
operator|==
literal|'_'
condition|?
name|col
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|col
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
else|:
name|col
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|col
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
block|}
name|entries
operator|.
name|put
argument_list|(
name|id
argument_list|,
name|entry
argument_list|)
expr_stmt|;
name|database
operator|.
name|insertEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
block|}
comment|// Import strings and preamble:
try|try
init|(
name|Statement
name|stringStatement
init|=
name|conn
operator|.
name|createStatement
argument_list|()
init|;
name|ResultSet
name|rsStrings
operator|=
name|stringStatement
operator|.
name|executeQuery
argument_list|(
name|SQLUtil
operator|.
name|queryAllFromTable
argument_list|(
literal|"strings WHERE database_id='"
operator|+
name|database_id
operator|+
literal|'\''
argument_list|)
argument_list|)
init|)
block|{
while|while
condition|(
name|rsStrings
operator|.
name|next
argument_list|()
condition|)
block|{
name|String
name|label
init|=
name|rsStrings
operator|.
name|getString
argument_list|(
literal|"label"
argument_list|)
decl_stmt|;
name|String
name|content
init|=
name|rsStrings
operator|.
name|getString
argument_list|(
literal|"content"
argument_list|)
decl_stmt|;
if|if
condition|(
literal|"@PREAMBLE"
operator|.
name|equals
argument_list|(
name|label
argument_list|)
condition|)
block|{
name|database
operator|.
name|setPreamble
argument_list|(
name|content
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|BibtexString
name|string
init|=
operator|new
name|BibtexString
argument_list|(
name|IdGenerator
operator|.
name|next
argument_list|()
argument_list|,
name|label
argument_list|,
name|content
argument_list|)
decl_stmt|;
name|database
operator|.
name|addString
argument_list|(
name|string
argument_list|)
expr_stmt|;
block|}
block|}
block|}
name|MetaData
name|metaData
init|=
operator|new
name|MetaData
argument_list|()
decl_stmt|;
name|metaData
operator|.
name|initializeNewDatabase
argument_list|()
expr_stmt|;
comment|// Read the groups tree:
name|importGroupsTree
argument_list|(
name|metaData
argument_list|,
name|entries
argument_list|,
name|conn
argument_list|,
name|database_id
argument_list|)
expr_stmt|;
name|result
operator|.
name|add
argument_list|(
operator|new
name|DBImporterResult
argument_list|(
name|database
argument_list|,
name|metaData
argument_list|,
name|rsDatabase
operator|.
name|getString
argument_list|(
literal|"database_name"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
return|return
name|result
return|;
block|}
comment|/**      * Look up the group type name from the type ID in the database.      *      * @param groupId The database's groups id      * @param conn    The database connection      * @return The name (JabRef type id) of the group type.      * @throws SQLException      */
DECL|method|findGroupTypeName (String groupId, Connection conn)
specifier|private
name|String
name|findGroupTypeName
parameter_list|(
name|String
name|groupId
parameter_list|,
name|Connection
name|conn
parameter_list|)
throws|throws
name|SQLException
block|{
return|return
name|SQLUtil
operator|.
name|processQueryWithSingleResult
argument_list|(
name|conn
argument_list|,
literal|"SELECT label FROM group_types WHERE group_types_id='"
operator|+
name|groupId
operator|+
literal|"';"
argument_list|)
return|;
block|}
DECL|method|importGroupsTree (MetaData metaData, Map<String, BibEntry> entries, Connection conn, final String database_id)
specifier|private
name|void
name|importGroupsTree
parameter_list|(
name|MetaData
name|metaData
parameter_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|BibEntry
argument_list|>
name|entries
parameter_list|,
name|Connection
name|conn
parameter_list|,
specifier|final
name|String
name|database_id
parameter_list|)
throws|throws
name|SQLException
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|GroupTreeNode
argument_list|>
name|groups
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
name|LinkedHashMap
argument_list|<
name|GroupTreeNode
argument_list|,
name|String
argument_list|>
name|parentIds
init|=
operator|new
name|LinkedHashMap
argument_list|<>
argument_list|()
decl_stmt|;
name|GroupTreeNode
name|rootNode
init|=
operator|new
name|GroupTreeNode
argument_list|(
operator|new
name|AllEntriesGroup
argument_list|()
argument_list|)
decl_stmt|;
name|String
name|query
init|=
name|SQLUtil
operator|.
name|queryAllFromTable
argument_list|(
literal|"groups WHERE database_id='"
operator|+
name|database_id
operator|+
literal|"' ORDER BY groups_id"
argument_list|)
decl_stmt|;
try|try
init|(
name|Statement
name|statement
init|=
name|conn
operator|.
name|createStatement
argument_list|()
init|;
name|ResultSet
name|rsGroups
operator|=
name|statement
operator|.
name|executeQuery
argument_list|(
name|query
argument_list|)
init|)
block|{
while|while
condition|(
name|rsGroups
operator|.
name|next
argument_list|()
condition|)
block|{
name|AbstractGroup
name|group
init|=
literal|null
decl_stmt|;
name|String
name|typeId
init|=
name|findGroupTypeName
argument_list|(
name|rsGroups
operator|.
name|getString
argument_list|(
literal|"group_types_id"
argument_list|)
argument_list|,
name|conn
argument_list|)
decl_stmt|;
switch|switch
condition|(
name|typeId
condition|)
block|{
case|case
name|AllEntriesGroup
operator|.
name|ID
case|:
comment|// register the id of the root node:
name|groups
operator|.
name|put
argument_list|(
name|rsGroups
operator|.
name|getString
argument_list|(
literal|"groups_id"
argument_list|)
argument_list|,
name|rootNode
argument_list|)
expr_stmt|;
break|break;
case|case
name|ExplicitGroup
operator|.
name|ID
case|:
name|group
operator|=
operator|new
name|ExplicitGroup
argument_list|(
name|rsGroups
operator|.
name|getString
argument_list|(
literal|"label"
argument_list|)
argument_list|,
name|GroupHierarchyType
operator|.
name|getByNumber
argument_list|(
name|rsGroups
operator|.
name|getInt
argument_list|(
literal|"hierarchical_context"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
break|break;
case|case
name|KeywordGroup
operator|.
name|ID
case|:
name|LOGGER
operator|.
name|debug
argument_list|(
literal|"Keyw: "
operator|+
name|rsGroups
operator|.
name|getBoolean
argument_list|(
literal|"case_sensitive"
argument_list|)
argument_list|)
expr_stmt|;
name|group
operator|=
operator|new
name|KeywordGroup
argument_list|(
name|rsGroups
operator|.
name|getString
argument_list|(
literal|"label"
argument_list|)
argument_list|,
name|StringUtil
operator|.
name|unquote
argument_list|(
name|rsGroups
operator|.
name|getString
argument_list|(
literal|"search_field"
argument_list|)
argument_list|,
literal|'\\'
argument_list|)
argument_list|,
name|StringUtil
operator|.
name|unquote
argument_list|(
name|rsGroups
operator|.
name|getString
argument_list|(
literal|"search_expression"
argument_list|)
argument_list|,
literal|'\\'
argument_list|)
argument_list|,
name|rsGroups
operator|.
name|getBoolean
argument_list|(
literal|"case_sensitive"
argument_list|)
argument_list|,
name|rsGroups
operator|.
name|getBoolean
argument_list|(
literal|"reg_exp"
argument_list|)
argument_list|,
name|GroupHierarchyType
operator|.
name|getByNumber
argument_list|(
name|rsGroups
operator|.
name|getInt
argument_list|(
literal|"hierarchical_context"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
break|break;
case|case
name|SearchGroup
operator|.
name|ID
case|:
name|LOGGER
operator|.
name|debug
argument_list|(
literal|"Search: "
operator|+
name|rsGroups
operator|.
name|getBoolean
argument_list|(
literal|"case_sensitive"
argument_list|)
argument_list|)
expr_stmt|;
name|group
operator|=
operator|new
name|SearchGroup
argument_list|(
name|rsGroups
operator|.
name|getString
argument_list|(
literal|"label"
argument_list|)
argument_list|,
name|StringUtil
operator|.
name|unquote
argument_list|(
name|rsGroups
operator|.
name|getString
argument_list|(
literal|"search_expression"
argument_list|)
argument_list|,
literal|'\\'
argument_list|)
argument_list|,
name|rsGroups
operator|.
name|getBoolean
argument_list|(
literal|"case_sensitive"
argument_list|)
argument_list|,
name|rsGroups
operator|.
name|getBoolean
argument_list|(
literal|"reg_exp"
argument_list|)
argument_list|,
name|GroupHierarchyType
operator|.
name|getByNumber
argument_list|(
name|rsGroups
operator|.
name|getInt
argument_list|(
literal|"hierarchical_context"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
break|break;
block|}
if|if
condition|(
name|group
operator|!=
literal|null
condition|)
block|{
name|GroupTreeNode
name|node
init|=
operator|new
name|GroupTreeNode
argument_list|(
name|group
argument_list|)
decl_stmt|;
name|parentIds
operator|.
name|put
argument_list|(
name|node
argument_list|,
name|rsGroups
operator|.
name|getString
argument_list|(
literal|"parent_id"
argument_list|)
argument_list|)
expr_stmt|;
name|groups
operator|.
name|put
argument_list|(
name|rsGroups
operator|.
name|getString
argument_list|(
literal|"groups_id"
argument_list|)
argument_list|,
name|node
argument_list|)
expr_stmt|;
block|}
comment|// Ok, we have collected a map of all groups and their parent IDs,
comment|// and another map of all group IDs and their group nodes.
comment|// Now we need to build the groups tree:
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|GroupTreeNode
argument_list|,
name|String
argument_list|>
name|groupTreeNodeStringEntry
range|:
name|parentIds
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|String
name|parentId
init|=
name|groupTreeNodeStringEntry
operator|.
name|getValue
argument_list|()
decl_stmt|;
name|GroupTreeNode
name|parent
init|=
name|groups
operator|.
name|get
argument_list|(
name|parentId
argument_list|)
decl_stmt|;
if|if
condition|(
name|parent
operator|==
literal|null
condition|)
block|{
comment|// TODO: missing parent
block|}
else|else
block|{
name|groupTreeNodeStringEntry
operator|.
name|getKey
argument_list|()
operator|.
name|moveTo
argument_list|(
name|parent
argument_list|)
expr_stmt|;
block|}
block|}
try|try
init|(
name|Statement
name|entryGroup
init|=
name|conn
operator|.
name|createStatement
argument_list|()
init|;
name|ResultSet
name|rsEntryGroup
operator|=
name|entryGroup
operator|.
name|executeQuery
argument_list|(
name|SQLUtil
operator|.
name|queryAllFromTable
argument_list|(
literal|"entry_group"
argument_list|)
argument_list|)
init|)
block|{
while|while
condition|(
name|rsEntryGroup
operator|.
name|next
argument_list|()
condition|)
block|{
name|String
name|entryId
init|=
name|rsEntryGroup
operator|.
name|getString
argument_list|(
literal|"entries_id"
argument_list|)
decl_stmt|;
name|String
name|groupId
init|=
name|rsEntryGroup
operator|.
name|getString
argument_list|(
literal|"groups_id"
argument_list|)
decl_stmt|;
name|GroupTreeNode
name|node
init|=
name|groups
operator|.
name|get
argument_list|(
name|groupId
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|node
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|node
operator|.
name|getGroup
argument_list|()
operator|instanceof
name|ExplicitGroup
operator|)
condition|)
block|{
name|ExplicitGroup
name|expGroup
init|=
operator|(
name|ExplicitGroup
operator|)
name|node
operator|.
name|getGroup
argument_list|()
decl_stmt|;
name|expGroup
operator|.
name|addEntry
argument_list|(
name|entries
operator|.
name|get
argument_list|(
name|entryId
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
name|metaData
operator|.
name|setGroups
argument_list|(
name|rootNode
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|/**      * Given a DBStrings it connects to the DB and returns the java.sql.Connection object      *      * @param dbstrings The DBStrings to use to make the connection      * @return java.sql.Connection to the DB chosen      * @throws SQLException      * @throws ClassNotFoundException      * @throws InstantiationException      * @throws IllegalAccessException      */
DECL|method|connectToDB (DBStrings dbstrings)
specifier|public
name|Connection
name|connectToDB
parameter_list|(
name|DBStrings
name|dbstrings
parameter_list|)
throws|throws
name|IllegalAccessException
throws|,
name|InstantiationException
throws|,
name|ClassNotFoundException
throws|,
name|SQLException
block|{
name|String
name|url
init|=
name|SQLUtil
operator|.
name|createJDBCurl
argument_list|(
name|dbstrings
argument_list|,
literal|true
argument_list|)
decl_stmt|;
return|return
name|database
operator|.
name|connect
argument_list|(
name|url
argument_list|,
name|dbstrings
operator|.
name|getDbPreferences
argument_list|()
operator|.
name|getUsername
argument_list|()
argument_list|,
name|dbstrings
operator|.
name|getPassword
argument_list|()
argument_list|)
return|;
block|}
block|}
end_class

end_unit

