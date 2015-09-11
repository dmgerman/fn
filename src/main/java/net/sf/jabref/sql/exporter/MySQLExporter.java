begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General public static License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General public static License for more details.      You should have received a copy of the GNU General public static License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
end_comment

begin_package
DECL|package|net.sf.jabref.sql.exporter
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|sql
operator|.
name|exporter
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
name|logic
operator|.
name|id
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
name|SQLUtil
import|;
end_import

begin_comment
comment|/**  *   * @author ifsteinm.  *   *         Jan 20th Extends DBExporter to provide features specific for MySQL  *         Created after a refactory on SQLUtil  *   */
end_comment

begin_class
DECL|class|MySQLExporter
specifier|public
class|class
name|MySQLExporter
extends|extends
name|DBExporter
block|{
DECL|field|instance
specifier|private
specifier|static
name|MySQLExporter
name|instance
decl_stmt|;
DECL|method|MySQLExporter ()
specifier|private
name|MySQLExporter
parameter_list|()
block|{     }
comment|/**      *       * @return The singleton instance of the MySQLExporter      */
DECL|method|getInstance ()
specifier|public
specifier|static
name|MySQLExporter
name|getInstance
parameter_list|()
block|{
if|if
condition|(
name|MySQLExporter
operator|.
name|instance
operator|==
literal|null
condition|)
block|{
name|MySQLExporter
operator|.
name|instance
operator|=
operator|new
name|MySQLExporter
argument_list|()
expr_stmt|;
block|}
return|return
name|MySQLExporter
operator|.
name|instance
return|;
block|}
annotation|@
name|Override
DECL|method|connectToDB (DBStrings dbstrings)
specifier|public
name|Connection
name|connectToDB
parameter_list|(
name|DBStrings
name|dbstrings
parameter_list|)
throws|throws
name|Exception
block|{
name|this
operator|.
name|dbStrings
operator|=
name|dbstrings
expr_stmt|;
name|String
name|url
init|=
name|SQLUtil
operator|.
name|createJDBCurl
argument_list|(
name|dbstrings
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|String
name|drv
init|=
literal|"com.mysql.jdbc.Driver"
decl_stmt|;
name|Class
operator|.
name|forName
argument_list|(
name|drv
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
name|dbstrings
operator|.
name|getUsername
argument_list|()
argument_list|,
name|dbstrings
operator|.
name|getPassword
argument_list|()
argument_list|)
decl_stmt|;
name|SQLUtil
operator|.
name|processQuery
argument_list|(
name|conn
argument_list|,
literal|"CREATE DATABASE IF NOT EXISTS `"
operator|+
name|dbStrings
operator|.
name|getDatabase
argument_list|()
operator|+
literal|'`'
argument_list|)
expr_stmt|;
name|SQLUtil
operator|.
name|processQuery
argument_list|(
name|conn
argument_list|,
literal|"USE `"
operator|+
name|dbStrings
operator|.
name|getDatabase
argument_list|()
operator|+
literal|'`'
argument_list|)
expr_stmt|;
return|return
name|conn
return|;
block|}
comment|/**      * Generates SQLnecessary to create all tables in a MySQL database, and      * writes it to appropriate output.      *       * @param out      *            The output (PrintStream or Connection) object to which the DML      *            should be written.      */
annotation|@
name|Override
DECL|method|createTables (Object out)
specifier|protected
name|void
name|createTables
parameter_list|(
name|Object
name|out
parameter_list|)
throws|throws
name|SQLException
block|{
name|SQLUtil
operator|.
name|processQuery
argument_list|(
name|out
argument_list|,
literal|"CREATE TABLE IF NOT EXISTS jabref_database ( \n"
operator|+
literal|"database_id INT UNSIGNED NOT NULL AUTO_INCREMENT, \n"
operator|+
literal|"database_name VARCHAR(64) NOT NULL, \n"
operator|+
literal|"md5_path VARCHAR(32) NOT NULL, \n"
operator|+
literal|"PRIMARY KEY (database_id)\n );"
argument_list|)
expr_stmt|;
name|SQLUtil
operator|.
name|processQuery
argument_list|(
name|out
argument_list|,
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
argument_list|)
expr_stmt|;
name|SQLUtil
operator|.
name|processQuery
argument_list|(
name|out
argument_list|,
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
argument_list|)
expr_stmt|;
name|SQLUtil
operator|.
name|processQuery
argument_list|(
name|out
argument_list|,
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
argument_list|)
expr_stmt|;
name|SQLUtil
operator|.
name|processQuery
argument_list|(
name|out
argument_list|,
literal|"CREATE TABLE IF NOT EXISTS group_types ( \n"
operator|+
literal|"group_types_id  INTEGER     NOT NULL AUTO_INCREMENT, \n"
operator|+
literal|"label   VARCHAR(100)    DEFAULT NULL, \n"
operator|+
literal|"PRIMARY KEY (group_types_id) \n"
operator|+
literal|");"
argument_list|)
expr_stmt|;
name|SQLUtil
operator|.
name|processQuery
argument_list|(
name|out
argument_list|,
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
argument_list|)
expr_stmt|;
name|SQLUtil
operator|.
name|processQuery
argument_list|(
name|out
argument_list|,
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
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

