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
name|net
operator|.
name|sf
operator|.
name|jabref
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
comment|/**  *   * @author ifsteinm.  *   *  Jan 20th	Extends DBExporter to provide features specific for PostgreSQL  *  			Created after a refactory on SQLUtil  *            */
end_comment

begin_class
DECL|class|PostgreSQLExporter
specifier|public
class|class
name|PostgreSQLExporter
extends|extends
name|DBExporter
block|{
DECL|field|instance
specifier|private
specifier|static
name|PostgreSQLExporter
name|instance
decl_stmt|;
DECL|method|PostgreSQLExporter ()
specifier|private
name|PostgreSQLExporter
parameter_list|()
block|{     }
comment|/**      *       * @return The singleton instance of the PostgreSQLExporter      */
DECL|method|getInstance ()
specifier|public
specifier|static
name|PostgreSQLExporter
name|getInstance
parameter_list|()
block|{
if|if
condition|(
name|PostgreSQLExporter
operator|.
name|instance
operator|==
literal|null
condition|)
block|{
name|PostgreSQLExporter
operator|.
name|instance
operator|=
operator|new
name|PostgreSQLExporter
argument_list|()
expr_stmt|;
block|}
return|return
name|PostgreSQLExporter
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
literal|true
argument_list|)
decl_stmt|;
name|String
name|drv
init|=
literal|"org.postgresql.Driver"
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
name|ResultSet
name|rs
init|=
operator|(
operator|(
name|Statement
operator|)
name|SQLUtil
operator|.
name|processQueryWithResults
argument_list|(
name|conn
argument_list|,
literal|"SELECT count(*) AS alreadyThere FROM pg_database WHERE datname='"
operator|+
name|dbStrings
operator|.
name|getDatabase
argument_list|()
operator|+
literal|'\''
argument_list|)
operator|)
operator|.
name|getResultSet
argument_list|()
decl_stmt|;
name|rs
operator|.
name|next
argument_list|()
expr_stmt|;
if|if
condition|(
name|rs
operator|.
name|getInt
argument_list|(
literal|"alreadyThere"
argument_list|)
operator|==
literal|0
condition|)
block|{
name|SQLUtil
operator|.
name|processQuery
argument_list|(
name|conn
argument_list|,
literal|"CREATE DATABASE "
operator|+
name|dbStrings
operator|.
name|getDatabase
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|rs
operator|.
name|getStatement
argument_list|()
operator|.
name|close
argument_list|()
expr_stmt|;
name|conn
operator|.
name|close
argument_list|()
expr_stmt|;
name|conn
operator|=
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
expr_stmt|;
name|createPLPGSQLFunction
argument_list|(
name|conn
argument_list|)
expr_stmt|;
return|return
name|conn
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
comment|/**      * Generates SQL necessary to create all tables in a MySQL database, and      * writes it to appropriate output.      *       * @param out      *            The output (PrintStream or Connection) object to which the DML      *            should be written.      */
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
literal|"SELECT create_table_if_not_exists ('CREATE TABLE jabref_database ( \n"
operator|+
literal|"database_id SERIAL NOT NULL, \n"
operator|+
literal|"database_name VARCHAR(64) NOT NULL, \n"
operator|+
literal|"md5_path VARCHAR(32) NOT NULL, \n"
operator|+
literal|"PRIMARY KEY (database_id)\n );')"
argument_list|)
expr_stmt|;
name|SQLUtil
operator|.
name|processQuery
argument_list|(
name|out
argument_list|,
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
argument_list|)
expr_stmt|;
name|SQLUtil
operator|.
name|processQuery
argument_list|(
name|out
argument_list|,
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
argument_list|)
expr_stmt|;
name|SQLUtil
operator|.
name|processQuery
argument_list|(
name|out
argument_list|,
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
argument_list|)
expr_stmt|;
name|SQLUtil
operator|.
name|processQuery
argument_list|(
name|out
argument_list|,
literal|"SELECT create_table_if_not_exists ('CREATE TABLE group_types ( \n"
operator|+
literal|"group_types_id  SERIAL, \n"
operator|+
literal|"label   VARCHAR(100)    DEFAULT NULL, \n"
operator|+
literal|"PRIMARY KEY (group_types_id) \n"
operator|+
literal|");')"
argument_list|)
expr_stmt|;
name|SQLUtil
operator|.
name|processQuery
argument_list|(
name|out
argument_list|,
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
argument_list|)
expr_stmt|;
name|SQLUtil
operator|.
name|processQuery
argument_list|(
name|out
argument_list|,
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
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

