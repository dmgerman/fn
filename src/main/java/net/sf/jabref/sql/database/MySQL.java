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
DECL|class|MySQL
specifier|public
class|class
name|MySQL
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
literal|"com.mysql.jdbc.Driver"
decl_stmt|;
DECL|method|loadDriver ()
specifier|private
name|void
name|loadDriver
parameter_list|()
throws|throws
name|InstantiationException
throws|,
name|IllegalAccessException
throws|,
name|ClassNotFoundException
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
literal|"SHOW columns FROM entries;"
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
literal|"CREATE TABLE IF NOT EXISTS jabref_database ( \n"
operator|+
literal|"database_id INT UNSIGNED NOT NULL AUTO_INCREMENT, \n"
operator|+
literal|"database_name VARCHAR(64) NOT NULL, \n"
operator|+
literal|"md5_path VARCHAR(32) NOT NULL, \n"
operator|+
literal|"PRIMARY KEY (database_id)\n );"
return|;
case|case
name|ENTRY_TYPES
case|:
return|return
literal|"CREATE TABLE IF NOT EXISTS entry_types ( \n"
operator|+
literal|"entry_types_id    INT UNSIGNED  NOT NULL AUTO_INCREMENT, \n"
operator|+
literal|"label			 TEXT, \n"
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
literal|");"
return|;
case|case
name|ENTRIES
case|:
return|return
literal|"CREATE TABLE IF NOT EXISTS entries ( \n"
operator|+
literal|"entries_id      INTEGER         NOT NULL AUTO_INCREMENT, \n"
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
literal|"database_id INT UNSIGNED, \n"
operator|+
literal|"entry_types_id  INT UNSIGNED         DEFAULT NULL, \n"
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
literal|"INDEX(entry_types_id), \n"
operator|+
literal|"FOREIGN KEY (entry_types_id) REFERENCES entry_types(entry_types_id), \n"
operator|+
literal|"FOREIGN KEY (database_id) REFERENCES jabref_database(database_id) \n);"
return|;
case|case
name|STRINGS
case|:
return|return
literal|"CREATE TABLE IF NOT EXISTS strings ( \n"
operator|+
literal|"strings_id      INTEGER         NOT NULL AUTO_INCREMENT, \n"
operator|+
literal|"label      VARCHAR(100)  DEFAULT NULL, \n"
operator|+
literal|"content    VARCHAR(200)  DEFAULT NULL, \n"
operator|+
literal|"database_id INT UNSIGNED, \n"
operator|+
literal|"FOREIGN KEY (database_id) REFERENCES jabref_database(database_id), \n"
operator|+
literal|"PRIMARY KEY (strings_id) \n"
operator|+
literal|");"
return|;
case|case
name|GROUP_TYPES
case|:
return|return
literal|"CREATE TABLE IF NOT EXISTS group_types ( \n"
operator|+
literal|"group_types_id  INTEGER     NOT NULL AUTO_INCREMENT, \n"
operator|+
literal|"label   VARCHAR(100)    DEFAULT NULL, \n"
operator|+
literal|"PRIMARY KEY (group_types_id) \n"
operator|+
literal|");"
return|;
case|case
name|GROUPS
case|:
return|return
literal|"CREATE TABLE IF NOT EXISTS groups ( \n"
operator|+
literal|"groups_id       INTEGER         NOT NULL AUTO_INCREMENT, \n"
operator|+
literal|"group_types_id  INTEGER         DEFAULT NULL, \n"
operator|+
literal|"label           VARCHAR(100)    DEFAULT NULL, \n"
operator|+
literal|"database_id INT UNSIGNED, \n"
operator|+
literal|"parent_id       INTEGER         DEFAULT NULL, \n"
operator|+
literal|"search_field       VARCHAR(100)          DEFAULT NULL, \n"
operator|+
literal|"search_expression  VARCHAR(200)          DEFAULT NULL, \n"
operator|+
literal|"case_sensitive  BOOL          DEFAULT NULL, \n"
operator|+
literal|"reg_exp BOOL DEFAULT NULL, \n"
operator|+
literal|"hierarchical_context INTEGER DEFAULT NULL, \n"
operator|+
literal|"FOREIGN KEY (database_id) REFERENCES jabref_database(database_id), \n"
operator|+
literal|"PRIMARY KEY (groups_id) \n"
operator|+
literal|");"
return|;
case|case
name|ENTRY_GROUP
case|:
return|return
literal|"CREATE TABLE IF NOT EXISTS entry_group ( \n"
operator|+
literal|"entries_id       INTEGER        NOT NULL AUTO_INCREMENT, \n"
operator|+
literal|"groups_id        INTEGER        DEFAULT NULL, \n"
operator|+
literal|"INDEX(entries_id), \n"
operator|+
literal|"INDEX(groups_id), \n"
operator|+
literal|"FOREIGN KEY (entries_id) REFERENCES entries(entries_id) ON DELETE CASCADE, \n"
operator|+
literal|"FOREIGN KEY (groups_id)  REFERENCES groups(groups_id), \n"
operator|+
literal|"PRIMARY KEY (groups_id, entries_id) \n"
operator|+
literal|");"
return|;
default|default:
return|return
literal|""
return|;
block|}
block|}
DECL|field|OPT_ALLOW_MULTI_QUERIES
specifier|private
specifier|static
specifier|final
name|String
name|OPT_ALLOW_MULTI_QUERIES
init|=
literal|"?allowMultiQueries=true"
decl_stmt|;
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
name|dbStrings
operator|.
name|setDbParameters
argument_list|(
name|OPT_ALLOW_MULTI_QUERIES
argument_list|)
expr_stmt|;
name|String
name|url
init|=
name|SQLUtil
operator|.
name|createJDBCurl
argument_list|(
name|dbStrings
argument_list|,
literal|false
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
name|String
name|query
init|=
literal|"CREATE DATABASE IF NOT EXISTS `"
operator|+
name|dbStrings
operator|.
name|getDbPreferences
argument_list|()
operator|.
name|getDatabase
argument_list|()
operator|+
literal|'`'
decl_stmt|;
name|SQLUtil
operator|.
name|processQuery
argument_list|(
name|conn
argument_list|,
name|query
argument_list|)
expr_stmt|;
name|conn
operator|.
name|setCatalog
argument_list|(
name|dbStrings
operator|.
name|getDbPreferences
argument_list|()
operator|.
name|getDatabase
argument_list|()
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
name|MYSQL
return|;
block|}
block|}
end_class

end_unit

