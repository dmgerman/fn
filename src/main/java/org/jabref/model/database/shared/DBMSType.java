begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.model.database.shared
package|package
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|database
operator|.
name|shared
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Locale
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

begin_comment
comment|/**  * Enumerates all supported database systems (DBMS) by JabRef.  */
end_comment

begin_enum
DECL|enum|DBMSType
specifier|public
enum|enum
name|DBMSType
block|{
DECL|enumConstant|MYSQL
name|MYSQL
argument_list|(
literal|"MySQL"
argument_list|,
literal|"org.mariadb.jdbc.Driver"
argument_list|,
literal|"jdbc:mariadb://%s:%d/%s"
argument_list|,
literal|3306
argument_list|)
block|,
DECL|enumConstant|ORACLE
name|ORACLE
argument_list|(
literal|"Oracle"
argument_list|,
literal|"oracle.jdbc.driver.OracleDriver"
argument_list|,
literal|"jdbc:oracle:thin:@%s:%d:%s"
argument_list|,
literal|1521
argument_list|)
block|,
DECL|enumConstant|POSTGRESQL
name|POSTGRESQL
argument_list|(
literal|"PostgreSQL"
argument_list|,
literal|"org.postgresql.Driver"
argument_list|,
literal|"jdbc:postgresql://%s:%d/%s"
argument_list|,
literal|5432
argument_list|)
block|;
DECL|field|type
specifier|private
specifier|final
name|String
name|type
decl_stmt|;
DECL|field|driverPath
specifier|private
specifier|final
name|String
name|driverPath
decl_stmt|;
DECL|field|urlPattern
specifier|private
specifier|final
name|String
name|urlPattern
decl_stmt|;
DECL|field|defaultPort
specifier|private
specifier|final
name|int
name|defaultPort
decl_stmt|;
DECL|method|DBMSType (String type, String driverPath, String urlPattern, int defaultPort)
specifier|private
name|DBMSType
parameter_list|(
name|String
name|type
parameter_list|,
name|String
name|driverPath
parameter_list|,
name|String
name|urlPattern
parameter_list|,
name|int
name|defaultPort
parameter_list|)
block|{
name|this
operator|.
name|type
operator|=
name|type
expr_stmt|;
name|this
operator|.
name|driverPath
operator|=
name|driverPath
expr_stmt|;
name|this
operator|.
name|urlPattern
operator|=
name|urlPattern
expr_stmt|;
name|this
operator|.
name|defaultPort
operator|=
name|defaultPort
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|toString ()
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|this
operator|.
name|type
return|;
block|}
comment|/**      * @return Java Class path for establishing JDBC connection.      */
DECL|method|getDriverClassPath ()
specifier|public
name|String
name|getDriverClassPath
parameter_list|()
throws|throws
name|Error
block|{
return|return
name|this
operator|.
name|driverPath
return|;
block|}
comment|/**      * @return prepared connection URL for appropriate system.      */
DECL|method|getUrl (String host, int port, String database)
specifier|public
name|String
name|getUrl
parameter_list|(
name|String
name|host
parameter_list|,
name|int
name|port
parameter_list|,
name|String
name|database
parameter_list|)
block|{
return|return
name|String
operator|.
name|format
argument_list|(
name|urlPattern
argument_list|,
name|host
argument_list|,
name|port
argument_list|,
name|database
argument_list|)
return|;
block|}
comment|/**      * Retrieves the port number dependent on the type of the database system.      */
DECL|method|getDefaultPort ()
specifier|public
name|int
name|getDefaultPort
parameter_list|()
block|{
return|return
name|this
operator|.
name|defaultPort
return|;
block|}
DECL|method|fromString (String typeName)
specifier|public
specifier|static
name|Optional
argument_list|<
name|DBMSType
argument_list|>
name|fromString
parameter_list|(
name|String
name|typeName
parameter_list|)
block|{
try|try
block|{
return|return
name|Optional
operator|.
name|of
argument_list|(
name|Enum
operator|.
name|valueOf
argument_list|(
name|DBMSType
operator|.
name|class
argument_list|,
name|typeName
operator|.
name|toUpperCase
argument_list|(
name|Locale
operator|.
name|ENGLISH
argument_list|)
argument_list|)
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|IllegalArgumentException
name|exception
parameter_list|)
block|{
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
block|}
block|}
end_enum

end_unit

