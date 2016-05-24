begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.sql.database
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|sql
operator|.
name|database
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
name|DriverManager
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
name|DatabaseType
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

begin_class
DECL|class|PostgreSQL
specifier|public
class|class
name|PostgreSQL
implements|implements
name|Database
block|{
DECL|field|DRIVER
specifier|public
specifier|static
specifier|final
name|String
name|DRIVER
init|=
literal|"org.postgresql.Driver"
decl_stmt|;
DECL|method|loadDriver ()
specifier|private
name|void
name|loadDriver
parameter_list|()
throws|throws
name|ClassNotFoundException
throws|,
name|IllegalAccessException
throws|,
name|InstantiationException
block|{
name|Class
operator|.
name|forName
argument_list|(
name|DRIVER
argument_list|)
operator|.
name|newInstance
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|connect (String url, String username, String password)
specifier|public
name|Connection
name|connect
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
name|IllegalAccessException
throws|,
name|InstantiationException
throws|,
name|ClassNotFoundException
throws|,
name|SQLException
block|{
name|loadDriver
argument_list|()
expr_stmt|;
return|return
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
return|;
block|}
annotation|@
name|Override
DECL|method|getReadColumnNamesQuery ()
specifier|public
name|String
name|getReadColumnNamesQuery
parameter_list|()
block|{
return|return
literal|"SELECT column_name FROM information_schema.columns WHERE table_name ='entries';"
return|;
block|}
annotation|@
name|Override
DECL|method|getCreateTableSQL (Table table)
specifier|public
name|String
name|getCreateTableSQL
parameter_list|(
name|Table
name|table
parameter_list|)
block|{
switch|switch
condition|(
name|table
condition|)
block|{
case|case
name|JABREF_DATABASE
case|:
return|return
literal|"SELECT create_table_if_not_exists ('CREATE TABLE jabref_database ( \n"
operator|+
literal|"database_id SERIAL NOT NULL, \n"
operator|+
literal|"database_name VARCHAR(64) NOT NULL, \n"
operator|+
literal|"md5_path VARCHAR(32) NOT NULL, \n"
operator|+
literal|"PRIMARY KEY (database_id)\n );')"
return|;
case|case
name|ENTRY_TYPES
case|:
return|return
literal|"SELECT create_table_if_not_exists ('CREATE TABLE entry_types ( \n"
operator|+
literal|"entry_types_id    SERIAL, \n"
operator|+
literal|"label TEXT, \n"
operator|+
name|SQLUtil
operator|.
name|fieldsAsCols
argument_list|(
name|SQLUtil
operator|.
name|getAllFields
argument_list|()
argument_list|,
literal|" VARCHAR(3) DEFAULT NULL"
argument_list|)
operator|+
literal|", \n"
operator|+
literal|"PRIMARY KEY (entry_types_id) \n"
operator|+
literal|");')"
return|;
case|case
name|ENTRIES
case|:
return|return
literal|"SELECT create_table_if_not_exists ('CREATE TABLE entries ( \n"
operator|+
literal|"entries_id      SERIAL, \n"
operator|+
literal|"jabref_eid      VARCHAR("
operator|+
name|IdGenerator
operator|.
name|getMinimumIntegerDigits
argument_list|()
operator|+
literal|")   DEFAULT NULL, \n"
operator|+
literal|"database_id INTEGER, \n"
operator|+
literal|"entry_types_id  INTEGER DEFAULT NULL, \n"
operator|+
literal|"cite_key        VARCHAR(100)     DEFAULT NULL, \n"
operator|+
name|SQLUtil
operator|.
name|fieldsAsCols
argument_list|(
name|SQLUtil
operator|.
name|getAllFields
argument_list|()
argument_list|,
literal|" TEXT DEFAULT NULL"
argument_list|)
operator|+
literal|",\n"
operator|+
literal|"PRIMARY KEY (entries_id), \n"
operator|+
literal|"FOREIGN KEY (entry_types_id) REFERENCES entry_types (entry_types_id), \n"
operator|+
literal|"FOREIGN KEY (database_id) REFERENCES jabref_database(database_id) \n"
operator|+
literal|");')"
return|;
case|case
name|STRINGS
case|:
return|return
literal|"SELECT create_table_if_not_exists ('CREATE TABLE strings ( \n"
operator|+
literal|"strings_id      SERIAL, \n"
operator|+
literal|"label      VARCHAR(100)  DEFAULT NULL, \n"
operator|+
literal|"content    VARCHAR(200)  DEFAULT NULL, \n"
operator|+
literal|"database_id INTEGER, \n"
operator|+
literal|"FOREIGN KEY (database_id) REFERENCES jabref_database(database_id), \n"
operator|+
literal|"PRIMARY KEY (strings_id) \n"
operator|+
literal|");')"
return|;
case|case
name|GROUP_TYPES
case|:
return|return
literal|"SELECT create_table_if_not_exists ('CREATE TABLE group_types ( \n"
operator|+
literal|"group_types_id  SERIAL, \n"
operator|+
literal|"label   VARCHAR(100)    DEFAULT NULL, \n"
operator|+
literal|"PRIMARY KEY (group_types_id) \n"
operator|+
literal|");')"
return|;
case|case
name|GROUPS
case|:
return|return
literal|"SELECT create_table_if_not_exists ('CREATE TABLE groups ( \n"
operator|+
literal|"groups_id       SERIAL, \n"
operator|+
literal|"group_types_id  INTEGER         DEFAULT NULL, \n"
operator|+
literal|"label           VARCHAR(100)    DEFAULT NULL, \n"
operator|+
literal|"database_id INTEGER, \n"
operator|+
literal|"parent_id       INTEGER         DEFAULT NULL, \n"
operator|+
literal|"search_field       VARCHAR(100)          DEFAULT NULL, \n"
operator|+
literal|"search_expression  VARCHAR(200)          DEFAULT NULL, \n"
operator|+
literal|"case_sensitive  BOOLEAN       DEFAULT NULL, \n"
operator|+
literal|"reg_exp BOOLEAN DEFAULT NULL, \n"
operator|+
literal|"hierarchical_context INTEGER DEFAULT NULL, \n"
operator|+
literal|"FOREIGN KEY (database_id) REFERENCES jabref_database(database_id), \n"
operator|+
literal|"PRIMARY KEY (groups_id) \n"
operator|+
literal|");')"
return|;
case|case
name|ENTRY_GROUP
case|:
return|return
literal|"SELECT create_table_if_not_exists ('CREATE TABLE entry_group ( \n"
operator|+
literal|"entries_id       SERIAL, \n"
operator|+
literal|"groups_id        INTEGER        DEFAULT NULL, \n"
operator|+
literal|"FOREIGN KEY (entries_id) REFERENCES entries (entries_id) ON DELETE CASCADE, \n"
operator|+
literal|"FOREIGN KEY (groups_id)  REFERENCES groups (groups_id), \n"
operator|+
literal|"PRIMARY KEY (groups_id, entries_id) \n"
operator|+
literal|");')"
return|;
default|default:
return|return
literal|""
return|;
block|}
block|}
annotation|@
name|Override
DECL|method|connectAndEnsureDatabaseExists (DBStrings dbStrings)
specifier|public
name|Connection
name|connectAndEnsureDatabaseExists
parameter_list|(
name|DBStrings
name|dbStrings
parameter_list|)
throws|throws
name|SQLException
throws|,
name|IllegalAccessException
throws|,
name|ClassNotFoundException
throws|,
name|InstantiationException
block|{
comment|// requires that the database is already there
name|String
name|url
init|=
name|SQLUtil
operator|.
name|createJDBCurl
argument_list|(
name|dbStrings
argument_list|,
literal|true
argument_list|)
decl_stmt|;
name|Connection
name|conn
init|=
name|connect
argument_list|(
name|url
argument_list|,
name|dbStrings
operator|.
name|getDbPreferences
argument_list|()
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
name|createPLPGSQLFunction
argument_list|(
name|conn
argument_list|)
expr_stmt|;
return|return
name|conn
return|;
block|}
annotation|@
name|Override
DECL|method|getType ()
specifier|public
name|DatabaseType
name|getType
parameter_list|()
block|{
return|return
name|DatabaseType
operator|.
name|POSTGRESQL
return|;
block|}
DECL|method|createPLPGSQLFunction (Connection conn)
specifier|private
name|void
name|createPLPGSQLFunction
parameter_list|(
name|Connection
name|conn
parameter_list|)
throws|throws
name|SQLException
block|{
name|SQLUtil
operator|.
name|processQuery
argument_list|(
name|conn
argument_list|,
literal|"create or replace function create_table_if_not_exists (create_sql text) returns bool as $$"
operator|+
literal|"BEGIN"
operator|+
literal|"\tBEGIN"
operator|+
literal|"\t\tEXECUTE create_sql;"
operator|+
literal|"\t\tException when duplicate_table THEN"
operator|+
literal|"\t\tRETURN false;"
operator|+
literal|"\tEND;"
operator|+
literal|"\tRETURN true;"
operator|+
literal|"END;"
operator|+
literal|"$$"
operator|+
literal|"Language plpgsql;"
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

