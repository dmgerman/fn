begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2016 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.shared
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|shared
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
name|java
operator|.
name|util
operator|.
name|HashSet
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
comment|/**  * Used to establish connections between JabRef and database systems like MySQL, PostgreSQL and Oracle.  */
end_comment

begin_class
DECL|class|DBMSConnector
specifier|public
class|class
name|DBMSConnector
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
name|DBMSConnector
operator|.
name|class
argument_list|)
decl_stmt|;
comment|/**      * Determines the suitable driver and retrieves a working SQL Connection in normal case.      *      * @param dbmsType Enum entry of {@link DBMSType} which determines the driver      * @param host Hostname, Domain or IP address      * @param port Port number the server is listening on      * @param database An already existent database name.      * @param user Username      * @param password Password      * @return      * @throws ClassNotFoundException Thrown if no suitable drivers were found      * @throws SQLException Thrown if connection has failed      */
DECL|method|getNewConnection (DBMSConnectionProperties properties)
specifier|public
specifier|static
name|Connection
name|getNewConnection
parameter_list|(
name|DBMSConnectionProperties
name|properties
parameter_list|)
throws|throws
name|ClassNotFoundException
throws|,
name|SQLException
block|{
try|try
block|{
name|DriverManager
operator|.
name|setLoginTimeout
argument_list|(
literal|3
argument_list|)
expr_stmt|;
return|return
name|DriverManager
operator|.
name|getConnection
argument_list|(
name|properties
operator|.
name|getType
argument_list|()
operator|.
name|getUrl
argument_list|(
name|properties
operator|.
name|getHost
argument_list|()
argument_list|,
name|properties
operator|.
name|getPort
argument_list|()
argument_list|,
name|properties
operator|.
name|getDatabase
argument_list|()
argument_list|)
argument_list|,
name|properties
operator|.
name|getUser
argument_list|()
argument_list|,
name|properties
operator|.
name|getPassword
argument_list|()
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|SQLException
name|e
parameter_list|)
block|{
comment|// Some systems like PostgreSQL retrieves 0 to every exception.
comment|// Therefore a stable error determination is not possible.
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Could not connect to database: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
operator|+
literal|" - Error code: "
operator|+
name|e
operator|.
name|getErrorCode
argument_list|()
argument_list|)
expr_stmt|;
throw|throw
name|e
throw|;
block|}
block|}
comment|/**      * Returns a Set of {@link DBMSType} which is supported by available drivers.      */
DECL|method|getAvailableDBMSTypes ()
specifier|public
specifier|static
name|Set
argument_list|<
name|DBMSType
argument_list|>
name|getAvailableDBMSTypes
parameter_list|()
block|{
name|Set
argument_list|<
name|DBMSType
argument_list|>
name|dbmsTypes
init|=
operator|new
name|HashSet
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
name|DBMSType
name|dbms
range|:
name|DBMSType
operator|.
name|values
argument_list|()
control|)
block|{
try|try
block|{
name|Class
operator|.
name|forName
argument_list|(
name|dbms
operator|.
name|getDriverClassPath
argument_list|()
argument_list|)
expr_stmt|;
name|dbmsTypes
operator|.
name|add
argument_list|(
name|dbms
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ClassNotFoundException
name|e
parameter_list|)
block|{
comment|// In case that the driver is not available do not perform tests for this system.
name|LOGGER
operator|.
name|info
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"%0 driver not available."
argument_list|,
name|dbms
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|dbmsTypes
return|;
block|}
block|}
end_class

end_unit

