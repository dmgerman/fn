begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * SQLutil.java  *  * Created on October 4, 2007, 5:28 PM  *  * To change this template, choose Tools | Template Manager  * and open the template in the editor.  */
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
name|File
import|;
end_import

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
name|PrintStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|lang
operator|.
name|Exception
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
name|Enumeration
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

begin_comment
comment|/* import java.util.List; import java.util.ListIterator; import java.util.Iterator;  */
end_comment

begin_import
import|import
name|java
operator|.
name|sql
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
name|ArrayList
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
name|ListIterator
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Enumeration
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
name|lang
operator|.
name|System
import|;
end_import

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
name|lang
operator|.
name|Exception
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
name|Util
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
name|BibtexDatabase
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
name|BibtexEntryType
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
name|BibtexEntry
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
name|export
operator|.
name|FileActions
import|;
end_import

begin_comment
comment|/**  *  * @author pattonlk  */
end_comment

begin_class
DECL|class|SQLutil
specifier|public
class|class
name|SQLutil
block|{
DECL|enum|DBTYPE
specifier|public
enum|enum
name|DBTYPE
block|{
DECL|enumConstant|MYSQL
name|MYSQL
block|}
DECL|field|fields
specifier|private
specifier|static
name|ArrayList
argument_list|<
name|String
argument_list|>
name|fields
init|=
literal|null
decl_stmt|;
DECL|field|fieldstr
specifier|private
specifier|static
name|String
name|fieldstr
init|=
literal|null
decl_stmt|;
DECL|method|connect_mysql (String url, String username, String password)
specifier|public
specifier|static
name|Connection
name|connect_mysql
parameter_list|(
name|String
name|url
parameter_list|,
name|String
name|username
parameter_list|,
name|String
name|password
parameter_list|)
throws|throws
name|Exception
block|{
comment|/**      * This routine accepts the location of a MySQL database specified as a url as       * well as the username and password for the MySQL user with appropriate access      * to this database.  The routine returns a valid Connection object if the MySQL       * database is successfully opened. It returns a null object otherwise.      */
name|Class
operator|.
name|forName
argument_list|(
literal|"com.mysql.jdbc.Driver"
argument_list|)
operator|.
name|newInstance
argument_list|()
expr_stmt|;
name|Connection
name|conn
init|=
name|DriverManager
operator|.
name|getConnection
argument_list|(
name|url
argument_list|,
name|username
argument_list|,
name|password
argument_list|)
decl_stmt|;
return|return
name|conn
return|;
block|}
comment|/**      * Utility method for processing DML with proper output      *      * @param out      *          The output (PrintStream or Connection) object to which the DML should be sent      * @param dml      *          The DML statements to be processed      */
DECL|method|processDML ( Object out, String dml)
specifier|private
specifier|static
name|void
name|processDML
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
name|execDML
argument_list|(
name|conn
argument_list|,
name|dml
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Utility method for executing DML      *      * @param conn      *          The DML Connection object that will execute the SQL      * @param sql      *          The DML statements to be executed      */
DECL|method|execDML (Connection conn, String dml)
specifier|public
specifier|static
name|void
name|execDML
parameter_list|(
name|Connection
name|conn
parameter_list|,
name|String
name|dml
parameter_list|)
throws|throws
name|SQLException
block|{
name|Statement
name|stmnt
init|=
name|conn
operator|.
name|createStatement
argument_list|()
decl_stmt|;
name|stmnt
operator|.
name|execute
argument_list|(
name|dml
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
comment|//TODO handle SQL warnings
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|warn
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"("
operator|+
name|dml
operator|+
literal|")"
argument_list|)
expr_stmt|;
block|}
name|stmnt
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
DECL|method|getFields ()
specifier|public
specifier|static
name|ArrayList
argument_list|<
name|String
argument_list|>
name|getFields
parameter_list|()
block|{
if|if
condition|(
name|fields
operator|==
literal|null
condition|)
block|{
name|refreshFields
argument_list|()
expr_stmt|;
block|}
return|return
name|fields
return|;
block|}
comment|/**      * loop through entry types to get required, optional, general and utility       * fields for this type.      */
DECL|method|refreshFields ()
specifier|public
specifier|static
name|void
name|refreshFields
parameter_list|()
block|{
if|if
condition|(
name|fields
operator|==
literal|null
condition|)
block|{
name|fields
operator|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|fields
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
for|for
control|(
name|BibtexEntryType
name|val
range|:
name|BibtexEntryType
operator|.
name|ALL_TYPES
operator|.
name|values
argument_list|()
control|)
block|{
name|fields
operator|=
name|uniqueInsert
argument_list|(
name|fields
argument_list|,
name|val
operator|.
name|getRequiredFields
argument_list|()
argument_list|)
expr_stmt|;
name|fields
operator|=
name|uniqueInsert
argument_list|(
name|fields
argument_list|,
name|val
operator|.
name|getOptionalFields
argument_list|()
argument_list|)
expr_stmt|;
name|fields
operator|=
name|uniqueInsert
argument_list|(
name|fields
argument_list|,
name|val
operator|.
name|getGeneralFields
argument_list|()
argument_list|)
expr_stmt|;
name|fields
operator|=
name|uniqueInsert
argument_list|(
name|fields
argument_list|,
name|val
operator|.
name|getUtilityFields
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|// create comma separated list of field names
name|fieldstr
operator|=
literal|""
expr_stmt|;
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
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|i
operator|>
literal|0
condition|)
name|fieldstr
operator|=
name|fieldstr
operator|+
literal|", "
expr_stmt|;
name|fieldstr
operator|=
name|fieldstr
operator|+
name|fields
operator|.
name|get
argument_list|(
name|i
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Inserts the elements of a String array into an ArrayList making sure not      * to duplicate entries in the ArrayList      *       * @param list      *            The ArrayList containing unique entries      * @param array      *            The String array to be inserted into the ArrayList      * @return The updated ArrayList with new unique entries      */
DECL|method|uniqueInsert (ArrayList<String> list, String[] array)
specifier|private
specifier|static
name|ArrayList
argument_list|<
name|String
argument_list|>
name|uniqueInsert
parameter_list|(
name|ArrayList
argument_list|<
name|String
argument_list|>
name|list
parameter_list|,
name|String
index|[]
name|array
parameter_list|)
block|{
if|if
condition|(
name|array
operator|!=
literal|null
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
name|array
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
name|list
operator|.
name|contains
argument_list|(
name|array
index|[
name|i
index|]
argument_list|)
condition|)
name|list
operator|.
name|add
argument_list|(
name|array
index|[
name|i
index|]
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|list
return|;
block|}
comment|/**      * Accepts the BibtexDatabase and MetaData, generates the DML required to      * create and populate SQL database tables, and writes this DML to the       * specified output file.      *      * @param database      *          The BibtexDatabase to export      * @param metaData      *          The MetaData object containing the groups information      * @param keySet      *          The set of IDs of the entries to export.      * @param file      *          The name of the file to which the DML should be written      */
DECL|method|exportDatabase (final BibtexDatabase database, final MetaData metaData, Set<String> keySet, String file )
specifier|public
specifier|static
name|void
name|exportDatabase
parameter_list|(
specifier|final
name|BibtexDatabase
name|database
parameter_list|,
specifier|final
name|MetaData
name|metaData
parameter_list|,
name|Set
argument_list|<
name|String
argument_list|>
name|keySet
parameter_list|,
name|String
name|file
parameter_list|)
throws|throws
name|Exception
block|{
comment|// open output file
name|File
name|outfile
init|=
operator|new
name|File
argument_list|(
name|file
argument_list|)
decl_stmt|;
if|if
condition|(
name|outfile
operator|.
name|exists
argument_list|()
condition|)
name|outfile
operator|.
name|delete
argument_list|()
expr_stmt|;
name|PrintStream
name|fout
init|=
literal|null
decl_stmt|;
name|fout
operator|=
operator|new
name|PrintStream
argument_list|(
name|outfile
argument_list|)
expr_stmt|;
name|exportDatabase_worker
argument_list|(
name|database
argument_list|,
name|metaData
argument_list|,
name|keySet
argument_list|,
name|fout
argument_list|)
expr_stmt|;
name|fout
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
comment|/**      * Accepts the BibtexDatabase and MetaData, generates the DML required to      * create and populate SQL database tables, and writes this DML to the       * specified SQL database.      *      * @param database      *          The BibtexDatabase to export      * @param metaData      *          The MetaData object containing the groups information      * @param keySet      *          The set of IDs of the entries to export.      * @param dbStrings      *          The necessary database connection information      */
DECL|method|exportDatabase (final BibtexDatabase database, final MetaData metaData, Set<String> keySet, DBStrings dbStrings )
specifier|public
specifier|static
name|void
name|exportDatabase
parameter_list|(
specifier|final
name|BibtexDatabase
name|database
parameter_list|,
specifier|final
name|MetaData
name|metaData
parameter_list|,
name|Set
argument_list|<
name|String
argument_list|>
name|keySet
parameter_list|,
name|DBStrings
name|dbStrings
parameter_list|)
throws|throws
name|Exception
block|{
name|Connection
name|conn
init|=
name|SQLutil
operator|.
name|connect_mysql
argument_list|(
name|dbStrings
operator|.
name|getJdbcUrl
argument_list|()
argument_list|,
name|dbStrings
operator|.
name|getUsername
argument_list|()
argument_list|,
name|dbStrings
operator|.
name|getPassword
argument_list|()
argument_list|)
decl_stmt|;
name|exportDatabase_worker
argument_list|(
name|database
argument_list|,
name|metaData
argument_list|,
name|keySet
argument_list|,
name|conn
argument_list|)
expr_stmt|;
name|conn
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
comment|/**      * Worker method for the exportDatabase methods.      *      * @param database      *          The BibtexDatabase to export      * @param metaData      *          The MetaData object containing the groups information      * @param keySet      *            The set of IDs of the entries to export.      * @param out      *          The output (PrintStream or Connection) object to which the DML should be written.      */
DECL|method|exportDatabase_worker (final BibtexDatabase database, final MetaData metaData, Set<String> keySet, Object out)
specifier|private
specifier|static
name|void
name|exportDatabase_worker
parameter_list|(
specifier|final
name|BibtexDatabase
name|database
parameter_list|,
specifier|final
name|MetaData
name|metaData
parameter_list|,
name|Set
argument_list|<
name|String
argument_list|>
name|keySet
parameter_list|,
name|Object
name|out
parameter_list|)
throws|throws
name|Exception
block|{
name|List
argument_list|<
name|BibtexEntry
argument_list|>
name|entries
init|=
name|FileActions
operator|.
name|getSortedEntries
argument_list|(
name|database
argument_list|,
name|keySet
argument_list|,
literal|false
argument_list|)
decl_stmt|;
comment|// create MySQL tables
name|dmlCreateTables
argument_list|(
name|SQLutil
operator|.
name|DBTYPE
operator|.
name|MYSQL
argument_list|,
name|out
argument_list|)
expr_stmt|;
comment|// populate entry_type table
name|dmlPopTab_ET
argument_list|(
name|out
argument_list|)
expr_stmt|;
comment|// populate entries table
name|dmlPopTab_FD
argument_list|(
name|entries
argument_list|,
name|out
argument_list|)
expr_stmt|;
name|GroupTreeNode
name|gtn
init|=
name|metaData
operator|.
name|getGroups
argument_list|()
decl_stmt|;
comment|// populate groups table
name|dmlPopTab_GP
argument_list|(
name|gtn
argument_list|,
name|out
argument_list|)
expr_stmt|;
comment|// populate entry_group table
name|dmlPopTab_EG
argument_list|(
name|gtn
argument_list|,
name|out
argument_list|)
expr_stmt|;
block|}
empty_stmt|;
comment|/**      * Writes the table creation DML to the specififed file.      *       * @param dbtype      *          Indicates the type of database to be written to       * @param fout      *          The output (PrintStream or Connection) object to which the DML should be written      */
DECL|method|dmlCreateTables (DBTYPE dbtype, Object out)
specifier|private
specifier|static
name|void
name|dmlCreateTables
parameter_list|(
name|DBTYPE
name|dbtype
parameter_list|,
name|Object
name|out
parameter_list|)
throws|throws
name|SQLException
block|{
comment|// make sure fields are initialized
if|if
condition|(
name|fields
operator|==
literal|null
condition|)
block|{
name|refreshFields
argument_list|()
expr_stmt|;
block|}
comment|// generate DML that specifies DB columns corresponding to fields
name|String
name|dml1
init|=
name|SQLutil
operator|.
name|fieldsAsCols
argument_list|(
name|fields
argument_list|,
literal|" VARCHAR(3)  DEFAULT NULL"
argument_list|)
decl_stmt|;
name|String
name|dml2
init|=
name|SQLutil
operator|.
name|fieldsAsCols
argument_list|(
name|fields
argument_list|,
literal|" TEXT DEFAULT NULL"
argument_list|)
decl_stmt|;
comment|// build the DML tables specification
name|String
name|dml
init|=
literal|""
decl_stmt|;
switch|switch
condition|(
name|dbtype
condition|)
block|{
case|case
name|MYSQL
case|:
name|dmlTable_mysql
argument_list|(
name|dml1
argument_list|,
name|dml2
argument_list|,
name|out
argument_list|)
expr_stmt|;
break|break;
default|default:
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"Error: Do not recognize database enumeration."
argument_list|)
expr_stmt|;
name|System
operator|.
name|exit
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
return|return;
block|}
comment|/**      * Generates DML specifying table columns and their datatypes. The output of      * this routine should be used within a CREATE TABLE statement.      *       * @param fields      *            Contains unique field names      * @param datatype      *            Specifies the SQL data type that the fields should take on.      * @return The DML code to be included in a CREATE TABLE statement.      */
DECL|method|fieldsAsCols (ArrayList<String> fields, String datatype)
specifier|private
specifier|static
name|String
name|fieldsAsCols
parameter_list|(
name|ArrayList
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
name|str
init|=
literal|""
decl_stmt|;
name|ListIterator
argument_list|<
name|String
argument_list|>
name|li
init|=
name|fields
operator|.
name|listIterator
argument_list|()
decl_stmt|;
while|while
condition|(
name|li
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|str
operator|=
name|str
operator|+
name|li
operator|.
name|next
argument_list|()
operator|+
literal|" "
operator|+
name|datatype
expr_stmt|;
if|if
condition|(
name|li
operator|.
name|hasNext
argument_list|()
condition|)
name|str
operator|=
name|str
operator|+
literal|",\n"
expr_stmt|;
block|}
return|return
name|str
return|;
block|}
comment|/**      * Generates DML code necessary to create all tables in a MySQL database,       * and writes it to appropriate output.      *      * @param dml1      *            Column specifications for fields in entry_type table.      * @param dml2      *            Column specifications for fields in entries table.      * @param out      *            The output (PrintStream or Connection) object to which the DML should be written.      * @return DML to create all MySQL tables.      */
DECL|method|dmlTable_mysql (String dml1, String dml2, Object out)
specifier|private
specifier|static
name|void
name|dmlTable_mysql
parameter_list|(
name|String
name|dml1
parameter_list|,
name|String
name|dml2
parameter_list|,
name|Object
name|out
parameter_list|)
throws|throws
name|SQLException
block|{
name|processDML
argument_list|(
name|out
argument_list|,
literal|"DROP TABLE IF EXISTS entry_types;"
argument_list|)
expr_stmt|;
name|processDML
argument_list|(
name|out
argument_list|,
literal|"DROP TABLE IF EXISTS entries;"
argument_list|)
expr_stmt|;
name|processDML
argument_list|(
name|out
argument_list|,
literal|"DROP TABLE IF EXISTS groups;"
argument_list|)
expr_stmt|;
name|processDML
argument_list|(
name|out
argument_list|,
literal|"DROP TABLE IF EXISTS entry_group;"
argument_list|)
expr_stmt|;
name|processDML
argument_list|(
name|out
argument_list|,
literal|"CREATE TABLE entry_types ( \n"
operator|+
literal|"entry_types_id    INT UNSIGNED  NOT NULL AUTO_INCREMENT, \n"
operator|+
literal|"label			 TEXT, \n"
operator|+
name|dml1
operator|+
literal|", \n"
operator|+
literal|"PRIMARY KEY (entry_types_id) \n"
operator|+
literal|");"
argument_list|)
expr_stmt|;
name|processDML
argument_list|(
name|out
argument_list|,
literal|"CREATE TABLE entries ( \n"
operator|+
literal|"entries_id      INTEGER         NOT NULL AUTO_INCREMENT, \n"
operator|+
literal|"jabref_eid      VARCHAR("
operator|+
name|Util
operator|.
name|getMinimumIntegerDigits
argument_list|()
operator|+
literal|")   DEFAULT NULL, \n"
operator|+
literal|"entry_types_id  INTEGER         DEFAULT NULL, \n"
operator|+
literal|"cite_key        VARCHAR(30)     DEFAULT NULL, \n"
operator|+
name|dml2
operator|+
literal|",\n"
operator|+
literal|"PRIMARY KEY (entries_id), \n"
operator|+
literal|"FOREIGN KEY (entry_types_id) REFERENCES entry_type(entry_types_id) \n"
operator|+
literal|");"
argument_list|)
expr_stmt|;
name|processDML
argument_list|(
name|out
argument_list|,
literal|"CREATE TABLE groups ( \n"
operator|+
literal|"groups_id       INTEGER         NOT NULL AUTO_INCREMENT, \n"
operator|+
literal|"label           VARCHAR(100)     DEFAULT NULL, \n"
operator|+
literal|"parent_id       INTEGER          DEFAULT NULL, \n"
operator|+
literal|"PRIMARY KEY (groups_id) \n"
operator|+
literal|");"
argument_list|)
expr_stmt|;
name|processDML
argument_list|(
name|out
argument_list|,
literal|"CREATE TABLE entry_group ( \n"
operator|+
literal|"entries_id       INTEGER        NOT NULL AUTO_INCREMENT, \n"
operator|+
literal|"groups_id        INTEGER        DEFAULT NULL, \n"
operator|+
literal|"FOREIGN KEY (entries_id) REFERENCES entry_fields(entries_id), \n"
operator|+
literal|"FOREIGN KEY (groups_id)  REFERENCES groups(groups_id) \n"
operator|+
literal|");"
argument_list|)
expr_stmt|;
return|return;
block|}
comment|/**      * Generates the DML required to populate the entry_types table with jabref      * data.      *       * @param out      *          The output (PrintSream or Connection) object to which the DML should be written.      */
DECL|method|dmlPopTab_ET ( Object out)
specifier|private
specifier|static
name|void
name|dmlPopTab_ET
parameter_list|(
name|Object
name|out
parameter_list|)
throws|throws
name|SQLException
block|{
name|String
name|dml
init|=
literal|""
decl_stmt|;
name|String
name|insert
init|=
literal|"INSERT INTO entry_types (label, "
operator|+
name|fieldstr
operator|+
literal|") VALUES ("
decl_stmt|;
name|ArrayList
argument_list|<
name|String
argument_list|>
name|fieldID
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
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
name|fields
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
name|fieldID
operator|.
name|add
argument_list|(
literal|null
argument_list|)
expr_stmt|;
comment|// loop through entry types
for|for
control|(
name|BibtexEntryType
name|val
range|:
name|BibtexEntryType
operator|.
name|ALL_TYPES
operator|.
name|values
argument_list|()
control|)
block|{
comment|// set ID for each field corresponding to its relationship to the
comment|// entry type
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|fieldID
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|fieldID
operator|.
name|set
argument_list|(
name|i
argument_list|,
literal|""
argument_list|)
expr_stmt|;
block|}
name|fieldID
operator|=
name|setFieldID
argument_list|(
name|fields
argument_list|,
name|fieldID
argument_list|,
name|val
operator|.
name|getRequiredFields
argument_list|()
argument_list|,
literal|"req"
argument_list|)
expr_stmt|;
name|fieldID
operator|=
name|setFieldID
argument_list|(
name|fields
argument_list|,
name|fieldID
argument_list|,
name|val
operator|.
name|getOptionalFields
argument_list|()
argument_list|,
literal|"opt"
argument_list|)
expr_stmt|;
name|fieldID
operator|=
name|setFieldID
argument_list|(
name|fields
argument_list|,
name|fieldID
argument_list|,
name|val
operator|.
name|getGeneralFields
argument_list|()
argument_list|,
literal|"gen"
argument_list|)
expr_stmt|;
name|fieldID
operator|=
name|setFieldID
argument_list|(
name|fields
argument_list|,
name|fieldID
argument_list|,
name|val
operator|.
name|getUtilityFields
argument_list|()
argument_list|,
literal|"uti"
argument_list|)
expr_stmt|;
comment|// build DML insert statement
name|dml
operator|=
name|insert
operator|+
literal|"\""
operator|+
name|val
operator|.
name|getName
argument_list|()
operator|.
name|toLowerCase
argument_list|()
operator|+
literal|"\""
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|fieldID
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|dml
operator|=
name|dml
operator|+
literal|", "
expr_stmt|;
if|if
condition|(
name|fieldID
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|!=
literal|""
condition|)
block|{
name|dml
operator|=
name|dml
operator|+
literal|"\""
operator|+
name|fieldID
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|+
literal|"\""
expr_stmt|;
block|}
else|else
block|{
name|dml
operator|=
name|dml
operator|+
literal|"NULL"
expr_stmt|;
block|}
block|}
name|dml
operator|=
name|dml
operator|+
literal|");"
expr_stmt|;
comment|// handle DML according to output type
name|processDML
argument_list|(
name|out
argument_list|,
name|dml
argument_list|)
expr_stmt|;
block|}
return|return;
block|}
comment|/**      * A utility function for facilitating the assignment of a code to each      * field name that represents the relationship of that field to a specific      * entry type.      *       * @param fields      *            A list of all fields.      * @param fieldID      *            A list for holding the codes.      * @param fieldstr      *            A String array containing the fields to be coded.      * @param ID      *            The code that should be assigned to the specified fields.      * @return The updated code list.      */
DECL|method|setFieldID (ArrayList<String> fields, ArrayList<String> fieldID, String[] fieldstr, String ID)
specifier|private
specifier|static
name|ArrayList
argument_list|<
name|String
argument_list|>
name|setFieldID
parameter_list|(
name|ArrayList
argument_list|<
name|String
argument_list|>
name|fields
parameter_list|,
name|ArrayList
argument_list|<
name|String
argument_list|>
name|fieldID
parameter_list|,
name|String
index|[]
name|fieldstr
parameter_list|,
name|String
name|ID
parameter_list|)
block|{
if|if
condition|(
name|fieldstr
operator|!=
literal|null
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
name|fieldstr
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|fieldID
operator|.
name|set
argument_list|(
name|fields
operator|.
name|indexOf
argument_list|(
name|fieldstr
index|[
name|i
index|]
argument_list|)
argument_list|,
name|ID
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|fieldID
return|;
block|}
comment|/**      * Generates the DML required to populate the entries table with jabref      * data and writes it to the output PrintStream.      *       * @param entries      *          The BibtexEntries to export           * @param out      *          The output (PrintStream or Connection) object to which the DML should be written.      */
DECL|method|dmlPopTab_FD (List<BibtexEntry> entries, Object out)
specifier|private
specifier|static
name|void
name|dmlPopTab_FD
parameter_list|(
name|List
argument_list|<
name|BibtexEntry
argument_list|>
name|entries
parameter_list|,
name|Object
name|out
parameter_list|)
throws|throws
name|SQLException
block|{
name|String
name|dml
init|=
literal|""
decl_stmt|;
name|String
name|val
init|=
literal|""
decl_stmt|;
name|String
name|insert
init|=
literal|"INSERT INTO entries (jabref_eid, entry_types_id, cite_key, "
operator|+
name|fieldstr
operator|+
literal|") VALUES ("
decl_stmt|;
comment|// loop throught the entries that are to be exported
for|for
control|(
name|BibtexEntry
name|entry
range|:
name|entries
control|)
block|{
comment|// build DML insert statement
name|dml
operator|=
name|insert
operator|+
literal|"\""
operator|+
name|entry
operator|.
name|getId
argument_list|()
operator|+
literal|"\""
operator|+
literal|", (SELECT entry_types_id FROM entry_types WHERE label=\""
operator|+
name|entry
operator|.
name|getType
argument_list|()
operator|.
name|getName
argument_list|()
operator|.
name|toLowerCase
argument_list|()
operator|+
literal|"\"), \""
operator|+
name|entry
operator|.
name|getCiteKey
argument_list|()
operator|+
literal|"\""
expr_stmt|;
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
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|dml
operator|=
name|dml
operator|+
literal|", "
expr_stmt|;
name|val
operator|=
name|entry
operator|.
name|getField
argument_list|(
name|fields
operator|.
name|get
argument_list|(
name|i
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|val
operator|!=
literal|null
condition|)
block|{
name|dml
operator|=
name|dml
operator|+
literal|"\""
operator|+
name|val
operator|.
name|replaceAll
argument_list|(
literal|"\""
argument_list|,
literal|"\\\\\""
argument_list|)
operator|+
literal|"\""
expr_stmt|;
block|}
else|else
block|{
name|dml
operator|=
name|dml
operator|+
literal|"NULL"
expr_stmt|;
block|}
block|}
name|dml
operator|=
name|dml
operator|+
literal|");"
expr_stmt|;
comment|// handle DML according to output type
name|processDML
argument_list|(
name|out
argument_list|,
name|dml
argument_list|)
expr_stmt|;
block|}
return|return;
block|}
comment|/**      * Generates the DML required to populate the groups table with jabref      * data, and writes this DML to the output file.      *       * @param cursor      *            The current GroupTreeNode in the GroupsTree      * @param out      *            The output (PrintStream or Connection) object to which the DML should be written.      */
DECL|method|dmlPopTab_GP (GroupTreeNode cursor, Object out)
specifier|private
specifier|static
name|int
name|dmlPopTab_GP
parameter_list|(
name|GroupTreeNode
name|cursor
parameter_list|,
name|Object
name|out
parameter_list|)
throws|throws
name|Exception
block|{
name|int
name|cnt
init|=
name|dmlPopTab_GP_worker
argument_list|(
name|cursor
argument_list|,
literal|1
argument_list|,
literal|1
argument_list|,
name|out
argument_list|)
decl_stmt|;
return|return
name|cnt
return|;
block|}
comment|/**      * Recursive worker method for the dmlPopTab_GP methods.      *      * @param cursor      *            The current GroupTreeNode in the GroupsTree      * @param parentID      *            The integer ID associated with the cursors's parent node      * @param ID      *            The integer value to associate with the cursor      * @param out      *            The output (PrintStream or Connection) object to which the DML should be written.      */
DECL|method|dmlPopTab_GP_worker (GroupTreeNode cursor, int parentID, int ID, Object out)
specifier|private
specifier|static
name|int
name|dmlPopTab_GP_worker
parameter_list|(
name|GroupTreeNode
name|cursor
parameter_list|,
name|int
name|parentID
parameter_list|,
name|int
name|ID
parameter_list|,
name|Object
name|out
parameter_list|)
throws|throws
name|SQLException
block|{
comment|// handle DML according to output type
name|processDML
argument_list|(
name|out
argument_list|,
literal|"INSERT INTO groups (groups_id, label, parent_id) "
operator|+
literal|"VALUES ("
operator|+
name|ID
operator|+
literal|", \""
operator|+
name|cursor
operator|.
name|getGroup
argument_list|()
operator|.
name|getName
argument_list|()
operator|+
literal|"\", "
operator|+
name|parentID
operator|+
literal|");"
argument_list|)
expr_stmt|;
comment|// recurse on child nodes (depth-first traversal)
name|int
name|myID
init|=
name|ID
decl_stmt|;
for|for
control|(
name|Enumeration
argument_list|<
name|GroupTreeNode
argument_list|>
name|e
init|=
name|cursor
operator|.
name|children
argument_list|()
init|;
name|e
operator|.
name|hasMoreElements
argument_list|()
condition|;
control|)
name|ID
operator|=
name|dmlPopTab_GP_worker
argument_list|(
name|e
operator|.
name|nextElement
argument_list|()
argument_list|,
name|myID
argument_list|,
operator|++
name|ID
argument_list|,
name|out
argument_list|)
expr_stmt|;
return|return
name|ID
return|;
block|}
comment|/**      * Generates the DML required to populate the entry_group table with jabref      * data, and writes the DML to the PrintStream.      *       * @param cursor      *            The current GroupTreeNode in the GroupsTree      * @param out      *            The output (PrintStream or Connection) object to which the DML should be written.      */
DECL|method|dmlPopTab_EG (GroupTreeNode cursor, Object fout)
specifier|private
specifier|static
name|int
name|dmlPopTab_EG
parameter_list|(
name|GroupTreeNode
name|cursor
parameter_list|,
name|Object
name|fout
parameter_list|)
throws|throws
name|SQLException
block|{
name|int
name|cnt
init|=
name|dmlPopTab_EG_worker
argument_list|(
name|cursor
argument_list|,
literal|1
argument_list|,
literal|1
argument_list|,
name|fout
argument_list|)
decl_stmt|;
return|return
name|cnt
return|;
block|}
comment|/**      * Recursive worker method for the dmlPopTab_EG methods.      *       * @param cursor      *            The current GroupTreeNode in the GroupsTree      * @param parentID      *            The integer ID associated with the cursors's parent node      * @param ID      *            The integer value to associate with the cursor      * @param out      *            The output (PrintStream or Connection) object to which the DML should be written.      */
DECL|method|dmlPopTab_EG_worker (GroupTreeNode cursor, int parentID, int ID, Object out)
specifier|private
specifier|static
name|int
name|dmlPopTab_EG_worker
parameter_list|(
name|GroupTreeNode
name|cursor
parameter_list|,
name|int
name|parentID
parameter_list|,
name|int
name|ID
parameter_list|,
name|Object
name|out
parameter_list|)
throws|throws
name|SQLException
block|{
comment|// if this group contains entries...
if|if
condition|(
name|cursor
operator|.
name|getGroup
argument_list|()
operator|instanceof
name|ExplicitGroup
condition|)
block|{
comment|// build INSERT statement for each entry belonging to this group
name|ExplicitGroup
name|grp
init|=
operator|(
name|ExplicitGroup
operator|)
name|cursor
operator|.
name|getGroup
argument_list|()
decl_stmt|;
name|Iterator
name|it
init|=
name|grp
operator|.
name|getEntries
argument_list|()
operator|.
name|iterator
argument_list|()
decl_stmt|;
while|while
condition|(
name|it
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|BibtexEntry
name|be
init|=
operator|(
name|BibtexEntry
operator|)
name|it
operator|.
name|next
argument_list|()
decl_stmt|;
comment|// handle DML according to output type
name|processDML
argument_list|(
name|out
argument_list|,
literal|"INSERT INTO entry_group (entries_id, groups_id) "
operator|+
literal|"VALUES ("
operator|+
literal|"(SELECT entries_id FROM entries WHERE jabref_eid="
operator|+
literal|"\""
operator|+
name|be
operator|.
name|getId
argument_list|()
operator|+
literal|"\""
operator|+
literal|"), "
operator|+
literal|"(SELECT groups_id FROM groups WHERE groups_id="
operator|+
literal|"\""
operator|+
name|ID
operator|+
literal|"\")"
operator|+
literal|");"
argument_list|)
expr_stmt|;
block|}
block|}
comment|// recurse on child nodes (depth-first traversal)
name|int
name|myID
init|=
name|ID
decl_stmt|;
for|for
control|(
name|Enumeration
argument_list|<
name|GroupTreeNode
argument_list|>
name|e
init|=
name|cursor
operator|.
name|children
argument_list|()
init|;
name|e
operator|.
name|hasMoreElements
argument_list|()
condition|;
control|)
name|ID
operator|=
name|dmlPopTab_EG_worker
argument_list|(
name|e
operator|.
name|nextElement
argument_list|()
argument_list|,
name|myID
argument_list|,
operator|++
name|ID
argument_list|,
name|out
argument_list|)
expr_stmt|;
return|return
name|ID
return|;
block|}
block|}
end_class

end_unit

