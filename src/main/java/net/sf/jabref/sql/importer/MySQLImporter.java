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
comment|/**  *  * @author ifsteinm.  *  *  Jan 20th	Extends DBImporter to provide features specific for MySQL  *  			Created after a refactory on SQLUtil  *  */
end_comment

begin_class
DECL|class|MySQLImporter
specifier|public
class|class
name|MySQLImporter
extends|extends
name|DBImporter
block|{
DECL|field|instance
specifier|private
specifier|static
name|MySQLImporter
name|instance
decl_stmt|;
DECL|method|MySQLImporter ()
specifier|private
name|MySQLImporter
parameter_list|()
block|{     }
comment|/**      *      * @return The singleton instance of the MySQLImporter      */
DECL|method|getInstance ()
specifier|public
specifier|static
name|MySQLImporter
name|getInstance
parameter_list|()
block|{
if|if
condition|(
name|MySQLImporter
operator|.
name|instance
operator|==
literal|null
condition|)
block|{
name|MySQLImporter
operator|.
name|instance
operator|=
operator|new
name|MySQLImporter
argument_list|()
expr_stmt|;
block|}
return|return
name|MySQLImporter
operator|.
name|instance
return|;
block|}
annotation|@
name|Override
DECL|method|readColumnNames (Connection conn)
specifier|protected
name|ResultSet
name|readColumnNames
parameter_list|(
name|Connection
name|conn
parameter_list|)
throws|throws
name|SQLException
block|{
try|try
init|(
name|Statement
name|statement
init|=
operator|(
name|Statement
operator|)
name|SQLUtil
operator|.
name|processQueryWithResults
argument_list|(
name|conn
argument_list|,
literal|"SHOW columns FROM entries;"
argument_list|)
init|)
block|{
return|return
name|statement
operator|.
name|getResultSet
argument_list|()
return|;
block|}
block|}
annotation|@
name|Override
DECL|method|connectToDB (DBStrings dbstrings)
specifier|protected
name|Connection
name|connectToDB
parameter_list|(
name|DBStrings
name|dbstrings
parameter_list|)
throws|throws
name|Exception
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
return|return
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
return|;
block|}
block|}
end_class

end_unit

