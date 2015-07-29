begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General public static License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General public static License for more details.      You should have received a copy of the GNU General public static License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
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
name|structure
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
name|sql
operator|.
name|DBImporterExporter
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
name|SQLUtil
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

begin_comment
comment|/**  *   * @author ifsteinm.  *   *         Jan 20th Abstract Class to provide main features to import entries  *         from a DB. To insert a new DB it is necessary to extend this class  *         and add the DB name the enum available at  *         net.sf.jabref.sql.DBImporterAndExporterFactory (and to the GUI). This  *         class and its subclasses import database, entries and related stuff  *         from a DB to bib. Each exported database is imported as a new JabRef  *         (bib) database, presented on a new tab  *   */
end_comment

begin_class
DECL|class|DBImporter
specifier|public
specifier|abstract
class|class
name|DBImporter
extends|extends
name|DBImporterExporter
block|{
DECL|field|columnsNotConsideredForEntries
specifier|private
specifier|final
name|ArrayList
argument_list|<
name|String
argument_list|>
name|columnsNotConsideredForEntries
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|(
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
argument_list|)
decl_stmt|;
comment|/**      * Given a DBStrings it connects to the DB and returns the      * java.sql.Connection object      *       * @param dbstrings      *            The DBStrings to use to make the connection      * @return java.sql.Connection to the DB chosen      * @throws Exception      */
DECL|method|connectToDB (DBStrings dbstrings)
specifier|protected
specifier|abstract
name|Connection
name|connectToDB
parameter_list|(
name|DBStrings
name|dbstrings
parameter_list|)
throws|throws
name|Exception
function_decl|;
comment|/**      *       * @param conn      *            Connection object to the database      * @return A ResultSet with column name for the entries table      * @throws SQLException      */
DECL|method|readColumnNames (Connection conn)
specifier|protected
specifier|abstract
name|ResultSet
name|readColumnNames
parameter_list|(
name|Connection
name|conn
parameter_list|)
throws|throws
name|SQLException
function_decl|;
comment|/**      * Worker method to perform the import from a database      *       * @param keySet      *            The set of IDs of the entries to export.      * @param dbs      *            The necessary database connection information      * @return An ArrayList containing pairs of Objects. Each position of the      *         ArrayList stores three Objects: a BibtexDatabase, a MetaData and      *         a String with the bib database name stored in the DBMS      * @throws Exception      */
DECL|method|performImport (Set<String> keySet, DBStrings dbs, List<String> listOfDBs)
specifier|public
name|ArrayList
argument_list|<
name|Object
index|[]
argument_list|>
name|performImport
parameter_list|(
name|Set
argument_list|<
name|String
argument_list|>
name|keySet
parameter_list|,
name|DBStrings
name|dbs
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|listOfDBs
parameter_list|)
throws|throws
name|Exception
block|{
name|ArrayList
argument_list|<
name|Object
index|[]
argument_list|>
name|result
init|=
operator|new
name|ArrayList
argument_list|<
name|Object
index|[]
argument_list|>
argument_list|()
decl_stmt|;
name|Connection
name|conn
init|=
name|this
operator|.
name|connectToDB
argument_list|(
name|dbs
argument_list|)
decl_stmt|;
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
name|String
name|jabrefDBs
init|=
literal|"("
decl_stmt|;
while|while
condition|(
name|itLista
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|jabrefDBs
operator|+=
literal|'\''
operator|+
name|itLista
operator|.
name|next
argument_list|()
operator|+
literal|"',"
expr_stmt|;
block|}
name|jabrefDBs
operator|=
name|jabrefDBs
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|jabrefDBs
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
operator|+
literal|')'
expr_stmt|;
name|ResultSet
name|rsDatabase
init|=
name|SQLUtil
operator|.
name|queryAllFromTable
argument_list|(
name|conn
argument_list|,
literal|"jabref_database WHERE database_name IN "
operator|+
name|jabrefDBs
argument_list|)
decl_stmt|;
while|while
condition|(
name|rsDatabase
operator|.
name|next
argument_list|()
condition|)
block|{
name|BibtexDatabase
name|database
init|=
operator|new
name|BibtexDatabase
argument_list|()
decl_stmt|;
comment|// Find entry type IDs and their mappings to type names:
name|HashMap
argument_list|<
name|String
argument_list|,
name|BibtexEntryType
argument_list|>
name|types
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|BibtexEntryType
argument_list|>
argument_list|()
decl_stmt|;
name|ResultSet
name|rsEntryType
init|=
name|SQLUtil
operator|.
name|queryAllFromTable
argument_list|(
name|conn
argument_list|,
literal|"entry_types"
argument_list|)
decl_stmt|;
while|while
condition|(
name|rsEntryType
operator|.
name|next
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
name|BibtexEntryType
operator|.
name|getType
argument_list|(
name|rsEntryType
operator|.
name|getString
argument_list|(
literal|"label"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|rsEntryType
operator|.
name|getStatement
argument_list|()
operator|.
name|close
argument_list|()
expr_stmt|;
name|ResultSet
name|rsColumns
init|=
name|this
operator|.
name|readColumnNames
argument_list|(
name|conn
argument_list|)
decl_stmt|;
name|ArrayList
argument_list|<
name|String
argument_list|>
name|colNames
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
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
if|if
condition|(
operator|!
name|columnsNotConsideredForEntries
operator|.
name|contains
argument_list|(
name|rsColumns
operator|.
name|getString
argument_list|(
literal|1
argument_list|)
argument_list|)
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
block|}
name|rsColumns
operator|.
name|getStatement
argument_list|()
operator|.
name|close
argument_list|()
expr_stmt|;
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
comment|// Read the entries and create BibtexEntry instances:
name|HashMap
argument_list|<
name|String
argument_list|,
name|BibtexEntry
argument_list|>
name|entries
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|BibtexEntry
argument_list|>
argument_list|()
decl_stmt|;
name|ResultSet
name|rsEntries
init|=
name|SQLUtil
operator|.
name|queryAllFromTable
argument_list|(
name|conn
argument_list|,
literal|"entries WHERE database_id= '"
operator|+
name|database_id
operator|+
literal|"';"
argument_list|)
decl_stmt|;
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
name|BibtexEntry
name|entry
init|=
operator|new
name|BibtexEntry
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
argument_list|)
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|BibtexFields
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
name|rsEntries
operator|.
name|getStatement
argument_list|()
operator|.
name|close
argument_list|()
expr_stmt|;
comment|// Import strings and preamble:
name|ResultSet
name|rsStrings
init|=
name|SQLUtil
operator|.
name|queryAllFromTable
argument_list|(
name|conn
argument_list|,
literal|"strings WHERE database_id='"
operator|+
name|database_id
operator|+
literal|'\''
argument_list|)
decl_stmt|;
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
name|label
operator|.
name|equals
argument_list|(
literal|"@PREAMBLE"
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
name|rsStrings
operator|.
name|getStatement
argument_list|()
operator|.
name|close
argument_list|()
expr_stmt|;
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
name|Object
index|[]
block|{
name|database
block|,
name|metaData
block|,
name|rsDatabase
operator|.
name|getString
argument_list|(
literal|"database_name"
argument_list|)
block|}
argument_list|)
expr_stmt|;
block|}
return|return
name|result
return|;
block|}
comment|/**      * Look up the group type name from the type ID in the database.      *       * @param groupId      *            The database's groups id      * @param conn      *            The database connection      *       * @return The name (JabRef type id) of the group type.      * @throws SQLException      */
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
DECL|method|importGroupsTree (MetaData metaData, HashMap<String, BibtexEntry> entries, Connection conn, String database_id)
specifier|private
name|void
name|importGroupsTree
parameter_list|(
name|MetaData
name|metaData
parameter_list|,
name|HashMap
argument_list|<
name|String
argument_list|,
name|BibtexEntry
argument_list|>
name|entries
parameter_list|,
name|Connection
name|conn
parameter_list|,
name|String
name|database_id
parameter_list|)
throws|throws
name|SQLException
block|{
name|HashMap
argument_list|<
name|String
argument_list|,
name|GroupTreeNode
argument_list|>
name|groups
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|GroupTreeNode
argument_list|>
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
argument_list|<
name|GroupTreeNode
argument_list|,
name|String
argument_list|>
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
name|ResultSet
name|rsGroups
init|=
name|SQLUtil
operator|.
name|queryAllFromTable
argument_list|(
name|conn
argument_list|,
literal|"groups WHERE database_id='"
operator|+
name|database_id
operator|+
literal|"' ORDER BY groups_id"
argument_list|)
decl_stmt|;
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
if|if
condition|(
name|typeId
operator|.
name|equals
argument_list|(
name|AllEntriesGroup
operator|.
name|ID
argument_list|)
condition|)
block|{
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
block|}
elseif|else
if|if
condition|(
name|typeId
operator|.
name|equals
argument_list|(
name|ExplicitGroup
operator|.
name|ID
argument_list|)
condition|)
block|{
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
block|}
elseif|else
if|if
condition|(
name|typeId
operator|.
name|equals
argument_list|(
name|KeywordGroup
operator|.
name|ID
argument_list|)
condition|)
block|{
name|System
operator|.
name|out
operator|.
name|println
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
block|}
elseif|else
if|if
condition|(
name|typeId
operator|.
name|equals
argument_list|(
name|SearchGroup
operator|.
name|ID
argument_list|)
condition|)
block|{
name|System
operator|.
name|out
operator|.
name|println
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
name|parent
operator|.
name|add
argument_list|(
name|groupTreeNodeStringEntry
operator|.
name|getKey
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
name|ResultSet
name|rsEntryGroup
init|=
name|SQLUtil
operator|.
name|queryAllFromTable
argument_list|(
name|conn
argument_list|,
literal|"entry_group"
argument_list|)
decl_stmt|;
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
name|rsEntryGroup
operator|.
name|getStatement
argument_list|()
operator|.
name|close
argument_list|()
expr_stmt|;
name|metaData
operator|.
name|setGroups
argument_list|(
name|rootNode
argument_list|)
expr_stmt|;
block|}
name|rsGroups
operator|.
name|getStatement
argument_list|()
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

