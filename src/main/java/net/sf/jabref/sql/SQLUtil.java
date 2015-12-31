begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General public static License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General public static License for more details.      You should have received a copy of the GNU General public static License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
end_comment

begin_package
DECL|package|net.sf.jabref.sql
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|sql
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|PrintStream
import|;
end_import

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
name|SQLWarning
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
name|*
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

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|BibtexFields
import|;
end_import

begin_comment
comment|/**  * @author pattonlk  *<p>  *         Reestructured by ifsteinm. Jan 20th Now it is possible to export more than one jabref database. BD creation,  *         insertions and queries where reformulated to accomodate the changes. The changes include a refactory on  *         import/export to SQL module, creating many other classes making them more readable This class just support  *         Exporters and Importers  */
end_comment

begin_class
DECL|class|SQLUtil
specifier|final
specifier|public
class|class
name|SQLUtil
block|{
DECL|field|RESERVED_DB_WORDS
specifier|private
specifier|static
specifier|final
name|List
argument_list|<
name|String
argument_list|>
name|RESERVED_DB_WORDS
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|(
name|Collections
operator|.
name|singletonList
argument_list|(
literal|"key"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|allFields
specifier|private
specifier|static
name|List
argument_list|<
name|String
argument_list|>
name|allFields
decl_stmt|;
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
name|SQLUtil
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|method|SQLUtil ()
specifier|private
name|SQLUtil
parameter_list|()
block|{     }
comment|/**      * loop through entry types to get required, optional, general and utility fields for this type.      */
DECL|method|refreshFields ()
specifier|private
specifier|static
name|void
name|refreshFields
parameter_list|()
block|{
if|if
condition|(
name|SQLUtil
operator|.
name|allFields
operator|==
literal|null
condition|)
block|{
name|SQLUtil
operator|.
name|allFields
operator|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|SQLUtil
operator|.
name|allFields
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
name|SQLUtil
operator|.
name|uniqueListInsert
argument_list|(
name|SQLUtil
operator|.
name|allFields
argument_list|,
name|BibtexFields
operator|.
name|getAllFieldNames
argument_list|()
argument_list|)
expr_stmt|;
name|SQLUtil
operator|.
name|uniqueListInsert
argument_list|(
name|SQLUtil
operator|.
name|allFields
argument_list|,
name|BibtexFields
operator|.
name|getAllPrivateFieldNames
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * @return All existent fields for a bibtex entry      */
DECL|method|getAllFields ()
specifier|public
specifier|static
name|List
argument_list|<
name|String
argument_list|>
name|getAllFields
parameter_list|()
block|{
if|if
condition|(
name|SQLUtil
operator|.
name|allFields
operator|==
literal|null
condition|)
block|{
name|SQLUtil
operator|.
name|refreshFields
argument_list|()
expr_stmt|;
block|}
return|return
name|SQLUtil
operator|.
name|allFields
return|;
block|}
comment|/**      * @return Create a common separated field names      */
DECL|method|getFieldStr ()
specifier|public
specifier|static
name|String
name|getFieldStr
parameter_list|()
block|{
comment|// create comma separated list of field names
name|String
name|field
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|fieldNames
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
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
name|SQLUtil
operator|.
name|getAllFields
argument_list|()
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|field
operator|=
name|SQLUtil
operator|.
name|allFields
operator|.
name|get
argument_list|(
name|i
argument_list|)
expr_stmt|;
if|if
condition|(
name|SQLUtil
operator|.
name|RESERVED_DB_WORDS
operator|.
name|contains
argument_list|(
name|field
argument_list|)
condition|)
block|{
name|field
operator|+=
literal|"_"
expr_stmt|;
block|}
name|fieldNames
operator|.
name|add
argument_list|(
name|field
argument_list|)
expr_stmt|;
block|}
return|return
name|String
operator|.
name|join
argument_list|(
literal|", "
argument_list|,
name|fieldNames
argument_list|)
return|;
block|}
comment|/**      * Inserts the elements of a List into another List making sure not to duplicate entries in the resulting List      *      * @param list1 The List containing unique entries      * @param list2 The second List to be inserted into the first ArrayList      * @return The updated list1 with new unique entries      */
DECL|method|uniqueListInsert (List<String> list1, List<String> list2)
specifier|private
specifier|static
name|List
argument_list|<
name|String
argument_list|>
name|uniqueListInsert
parameter_list|(
name|List
argument_list|<
name|String
argument_list|>
name|list1
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|list2
parameter_list|)
block|{
if|if
condition|(
name|list2
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|String
name|fromList2
range|:
name|list2
control|)
block|{
if|if
condition|(
operator|!
name|list1
operator|.
name|contains
argument_list|(
name|fromList2
argument_list|)
condition|)
block|{
if|if
condition|(
operator|!
literal|"#"
operator|.
name|equals
argument_list|(
name|fromList2
argument_list|)
condition|)
block|{
name|list1
operator|.
name|add
argument_list|(
name|fromList2
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
return|return
name|list1
return|;
block|}
comment|/**      * Generates DML specifying table columns and their datatypes. The output of this routine should be used within a      * CREATE TABLE statement.      *      * @param fields   Contains unique field names      * @param datatype Specifies the SQL data type that the fields should take on.      * @return The SQL code to be included in a CREATE TABLE statement.      */
DECL|method|fieldsAsCols (List<String> fields, String datatype)
specifier|public
specifier|static
name|String
name|fieldsAsCols
parameter_list|(
name|List
argument_list|<
name|String
argument_list|>
name|fields
parameter_list|,
name|String
name|datatype
parameter_list|)
block|{
name|String
name|field
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|newFields
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|field1
range|:
name|fields
control|)
block|{
name|field
operator|=
name|field1
expr_stmt|;
if|if
condition|(
name|SQLUtil
operator|.
name|RESERVED_DB_WORDS
operator|.
name|contains
argument_list|(
name|field
argument_list|)
condition|)
block|{
name|field
operator|=
name|field
operator|+
literal|'_'
expr_stmt|;
block|}
name|newFields
operator|.
name|add
argument_list|(
name|field
operator|+
name|datatype
argument_list|)
expr_stmt|;
block|}
return|return
name|String
operator|.
name|join
argument_list|(
literal|", "
argument_list|,
name|newFields
argument_list|)
return|;
block|}
comment|/**      * @param allFields All existent fields for a given entry type      * @param reqFields list containing required fields for an entry type      * @param optFields list containing optional fields for an entry type      * @param utiFields list containing utility fields for an entry type      * @param origList  original list with the correct size filled with the default values for each field      * @return origList changing the values of the fields that appear on reqFields, optFields, utiFields set to 'req',      * 'opt' and 'uti' respectively      */
DECL|method|setFieldRequirement (List<String> allFields, List<String> reqFields, List<String> optFields, List<String> utiFields, List<String> origList)
specifier|public
specifier|static
name|List
argument_list|<
name|String
argument_list|>
name|setFieldRequirement
parameter_list|(
name|List
argument_list|<
name|String
argument_list|>
name|allFields
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|reqFields
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|optFields
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|utiFields
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|origList
parameter_list|)
block|{
name|String
name|currentField
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
name|allFields
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|currentField
operator|=
name|allFields
operator|.
name|get
argument_list|(
name|i
argument_list|)
expr_stmt|;
if|if
condition|(
name|reqFields
operator|.
name|contains
argument_list|(
name|currentField
argument_list|)
condition|)
block|{
name|origList
operator|.
name|set
argument_list|(
name|i
argument_list|,
literal|"req"
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|optFields
operator|.
name|contains
argument_list|(
name|currentField
argument_list|)
condition|)
block|{
name|origList
operator|.
name|set
argument_list|(
name|i
argument_list|,
literal|"opt"
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|utiFields
operator|.
name|contains
argument_list|(
name|currentField
argument_list|)
condition|)
block|{
name|origList
operator|.
name|set
argument_list|(
name|i
argument_list|,
literal|"uti"
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|origList
return|;
block|}
comment|/**      * Return a message raised from a SQLException      *      * @param ex The SQLException raised      */
DECL|method|getExceptionMessage (Exception ex)
specifier|public
specifier|static
name|String
name|getExceptionMessage
parameter_list|(
name|Exception
name|ex
parameter_list|)
block|{
name|String
name|msg
decl_stmt|;
if|if
condition|(
name|ex
operator|.
name|getMessage
argument_list|()
operator|==
literal|null
condition|)
block|{
name|msg
operator|=
name|ex
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|msg
operator|=
name|ex
operator|.
name|getMessage
argument_list|()
expr_stmt|;
block|}
return|return
name|msg
return|;
block|}
comment|/**      * return a Statement with the result of a "SELECT *" query for a given table      *      * @param conn      Connection to the database      * @param tableName String containing the name of the table you want to get the results.      * @return a ResultSet with the query result returned from the DB      * @throws SQLException      */
DECL|method|queryAllFromTable (Connection conn, String tableName)
specifier|public
specifier|static
name|Statement
name|queryAllFromTable
parameter_list|(
name|Connection
name|conn
parameter_list|,
name|String
name|tableName
parameter_list|)
throws|throws
name|SQLException
block|{
name|String
name|query
init|=
literal|"SELECT * FROM "
operator|+
name|tableName
operator|+
literal|';'
decl_stmt|;
return|return
operator|(
name|Statement
operator|)
name|SQLUtil
operator|.
name|processQueryWithResults
argument_list|(
name|conn
argument_list|,
name|query
argument_list|)
return|;
block|}
comment|/**      * Utility method for processing DML with proper output      *      * @param out The output (PrintStream or Connection) object to which the DML should be sent      * @param dml The DML statements to be processed      */
DECL|method|processQuery (Object out, String dml)
specifier|public
specifier|static
name|void
name|processQuery
parameter_list|(
name|Object
name|out
parameter_list|,
name|String
name|dml
parameter_list|)
throws|throws
name|SQLException
block|{
if|if
condition|(
name|out
operator|instanceof
name|PrintStream
condition|)
block|{
name|PrintStream
name|fout
init|=
operator|(
name|PrintStream
operator|)
name|out
decl_stmt|;
name|fout
operator|.
name|println
argument_list|(
name|dml
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|out
operator|instanceof
name|Connection
condition|)
block|{
name|Connection
name|conn
init|=
operator|(
name|Connection
operator|)
name|out
decl_stmt|;
name|SQLUtil
operator|.
name|executeQuery
argument_list|(
name|conn
argument_list|,
name|dml
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Utility method for processing DML with proper output      *      * @param out   The output (PrintStream or Connection) object to which the DML should be sent      * @param query The DML statements to be processed      * @return the result of the statement      */
DECL|method|processQueryWithResults (Object out, String query)
specifier|public
specifier|static
name|AutoCloseable
name|processQueryWithResults
parameter_list|(
name|Object
name|out
parameter_list|,
name|String
name|query
parameter_list|)
throws|throws
name|SQLException
block|{
if|if
condition|(
name|out
operator|instanceof
name|PrintStream
condition|)
block|{
comment|// TODO: how to handle the PrintStream
comment|// case?
name|PrintStream
name|fout
init|=
operator|(
name|PrintStream
operator|)
name|out
decl_stmt|;
name|fout
operator|.
name|println
argument_list|(
name|query
argument_list|)
expr_stmt|;
return|return
name|fout
return|;
block|}
if|if
condition|(
name|out
operator|instanceof
name|Connection
condition|)
block|{
name|Connection
name|conn
init|=
operator|(
name|Connection
operator|)
name|out
decl_stmt|;
return|return
name|SQLUtil
operator|.
name|executeQueryWithResults
argument_list|(
name|conn
argument_list|,
name|query
argument_list|)
return|;
block|}
return|return
literal|null
return|;
block|}
comment|/**      * This routine returns the JDBC url corresponding to the DBStrings input.      *      * @param dbStrings The DBStrings to use to make the connection      * @return The JDBC url corresponding to the input DBStrings      */
DECL|method|createJDBCurl (DBStrings dbStrings, boolean withDBName)
specifier|public
specifier|static
name|String
name|createJDBCurl
parameter_list|(
name|DBStrings
name|dbStrings
parameter_list|,
name|boolean
name|withDBName
parameter_list|)
block|{
name|String
name|url
decl_stmt|;
name|url
operator|=
literal|"jdbc:"
operator|+
name|dbStrings
operator|.
name|getServerType
argument_list|()
operator|.
name|toLowerCase
argument_list|()
operator|+
literal|"://"
operator|+
name|dbStrings
operator|.
name|getServerHostname
argument_list|()
operator|+
operator|(
name|withDBName
condition|?
literal|'/'
operator|+
name|dbStrings
operator|.
name|getDatabase
argument_list|()
else|:
literal|""
operator|)
expr_stmt|;
return|return
name|url
return|;
block|}
comment|/**      * Process a query and returns only the first result of a result set as a String. To be used when it is certain that      * only one String (single cell) will be returned from the DB      *      * @param conn  The Connection object to which the DML should be sent      * @param query The query statements to be processed      * @return String with the result returned from the database      * @throws SQLException      */
DECL|method|processQueryWithSingleResult (Connection conn, String query)
specifier|public
specifier|static
name|String
name|processQueryWithSingleResult
parameter_list|(
name|Connection
name|conn
parameter_list|,
name|String
name|query
parameter_list|)
throws|throws
name|SQLException
block|{
try|try
init|(
name|Statement
name|sm
init|=
name|SQLUtil
operator|.
name|executeQueryWithResults
argument_list|(
name|conn
argument_list|,
name|query
argument_list|)
init|;
name|ResultSet
name|rs
operator|=
name|sm
operator|.
name|getResultSet
argument_list|()
init|)
block|{
name|rs
operator|.
name|next
argument_list|()
expr_stmt|;
name|String
name|result
init|=
name|rs
operator|.
name|getString
argument_list|(
literal|1
argument_list|)
decl_stmt|;
name|rs
operator|.
name|getStatement
argument_list|()
operator|.
name|close
argument_list|()
expr_stmt|;
return|return
name|result
return|;
block|}
block|}
comment|/**      * Utility method for executing DML      *      * @param conn The DML Connection object that will execute the SQL      * @param qry  The DML statements to be executed      */
DECL|method|executeQuery (Connection conn, String qry)
specifier|private
specifier|static
name|void
name|executeQuery
parameter_list|(
name|Connection
name|conn
parameter_list|,
name|String
name|qry
parameter_list|)
throws|throws
name|SQLException
block|{
try|try
init|(
name|Statement
name|stmnt
init|=
name|conn
operator|.
name|createStatement
argument_list|()
init|)
block|{
name|stmnt
operator|.
name|execute
argument_list|(
name|qry
argument_list|)
expr_stmt|;
name|SQLWarning
name|warn
init|=
name|stmnt
operator|.
name|getWarnings
argument_list|()
decl_stmt|;
if|if
condition|(
name|warn
operator|!=
literal|null
condition|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
name|warn
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|/**      * Utility method for executing DML      *      * @param conn The DML Connection object that will execute the SQL      * @param qry  The DML statements to be executed      */
DECL|method|executeQueryWithResults (Connection conn, String qry)
specifier|private
specifier|static
name|Statement
name|executeQueryWithResults
parameter_list|(
name|Connection
name|conn
parameter_list|,
name|String
name|qry
parameter_list|)
throws|throws
name|SQLException
block|{
name|Statement
name|stmnt
init|=
literal|null
decl_stmt|;
try|try
block|{
name|stmnt
operator|=
name|conn
operator|.
name|createStatement
argument_list|()
expr_stmt|;
name|stmnt
operator|.
name|executeQuery
argument_list|(
name|qry
argument_list|)
expr_stmt|;
name|SQLWarning
name|warn
init|=
name|stmnt
operator|.
name|getWarnings
argument_list|()
decl_stmt|;
if|if
condition|(
name|warn
operator|!=
literal|null
condition|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
name|warn
argument_list|)
expr_stmt|;
block|}
return|return
name|stmnt
return|;
block|}
catch|catch
parameter_list|(
name|SQLException
name|rethrow
parameter_list|)
block|{
comment|// in case of exception, try to close the statement to avoid a resource leak...
if|if
condition|(
name|stmnt
operator|!=
literal|null
condition|)
block|{
name|stmnt
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
comment|//... and rethrow the exception
throw|throw
name|rethrow
throw|;
block|}
block|}
block|}
end_class

end_unit

