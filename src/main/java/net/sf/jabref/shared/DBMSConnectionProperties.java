begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
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
name|io
operator|.
name|UnsupportedEncodingException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|security
operator|.
name|GeneralSecurityException
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

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|shared
operator|.
name|prefs
operator|.
name|SharedDatabasePreferences
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
name|shared
operator|.
name|security
operator|.
name|Password
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
comment|/**  * Keeps all essential data for establishing a new connection to a DBMS using {@link DBMSConnector}.  */
end_comment

begin_class
DECL|class|DBMSConnectionProperties
specifier|public
class|class
name|DBMSConnectionProperties
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
name|DBMSConnectionProperties
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|type
specifier|private
name|DBMSType
name|type
decl_stmt|;
DECL|field|host
specifier|private
name|String
name|host
decl_stmt|;
DECL|field|port
specifier|private
name|int
name|port
decl_stmt|;
DECL|field|database
specifier|private
name|String
name|database
decl_stmt|;
DECL|field|user
specifier|private
name|String
name|user
decl_stmt|;
DECL|field|password
specifier|private
name|String
name|password
decl_stmt|;
DECL|method|DBMSConnectionProperties ()
specifier|public
name|DBMSConnectionProperties
parameter_list|()
block|{
comment|// no data
block|}
DECL|method|DBMSConnectionProperties (SharedDatabasePreferences prefs)
specifier|public
name|DBMSConnectionProperties
parameter_list|(
name|SharedDatabasePreferences
name|prefs
parameter_list|)
block|{
name|setFromPreferences
argument_list|(
name|prefs
argument_list|)
expr_stmt|;
block|}
DECL|method|DBMSConnectionProperties (DBMSType type, String host, int port, String database, String user, String password)
specifier|public
name|DBMSConnectionProperties
parameter_list|(
name|DBMSType
name|type
parameter_list|,
name|String
name|host
parameter_list|,
name|int
name|port
parameter_list|,
name|String
name|database
parameter_list|,
name|String
name|user
parameter_list|,
name|String
name|password
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
name|host
operator|=
name|host
expr_stmt|;
name|this
operator|.
name|port
operator|=
name|port
expr_stmt|;
name|this
operator|.
name|database
operator|=
name|database
expr_stmt|;
name|this
operator|.
name|user
operator|=
name|user
expr_stmt|;
name|this
operator|.
name|password
operator|=
name|password
expr_stmt|;
block|}
DECL|method|getType ()
specifier|public
name|DBMSType
name|getType
parameter_list|()
block|{
return|return
name|type
return|;
block|}
DECL|method|setType (DBMSType type)
specifier|public
name|void
name|setType
parameter_list|(
name|DBMSType
name|type
parameter_list|)
block|{
name|this
operator|.
name|type
operator|=
name|type
expr_stmt|;
block|}
DECL|method|getHost ()
specifier|public
name|String
name|getHost
parameter_list|()
block|{
return|return
name|host
return|;
block|}
DECL|method|setHost (String host)
specifier|public
name|void
name|setHost
parameter_list|(
name|String
name|host
parameter_list|)
block|{
name|this
operator|.
name|host
operator|=
name|host
expr_stmt|;
block|}
DECL|method|getPort ()
specifier|public
name|int
name|getPort
parameter_list|()
block|{
return|return
name|port
return|;
block|}
DECL|method|setPort (int port)
specifier|public
name|void
name|setPort
parameter_list|(
name|int
name|port
parameter_list|)
block|{
name|this
operator|.
name|port
operator|=
name|port
expr_stmt|;
block|}
DECL|method|getDatabase ()
specifier|public
name|String
name|getDatabase
parameter_list|()
block|{
return|return
name|database
return|;
block|}
DECL|method|setDatabase (String database)
specifier|public
name|void
name|setDatabase
parameter_list|(
name|String
name|database
parameter_list|)
block|{
name|this
operator|.
name|database
operator|=
name|database
expr_stmt|;
block|}
DECL|method|getUser ()
specifier|public
name|String
name|getUser
parameter_list|()
block|{
return|return
name|user
return|;
block|}
DECL|method|setUser (String user)
specifier|public
name|void
name|setUser
parameter_list|(
name|String
name|user
parameter_list|)
block|{
name|this
operator|.
name|user
operator|=
name|user
expr_stmt|;
block|}
DECL|method|getPassword ()
specifier|public
name|String
name|getPassword
parameter_list|()
block|{
return|return
name|password
return|;
block|}
DECL|method|setPassword (String password)
specifier|public
name|void
name|setPassword
parameter_list|(
name|String
name|password
parameter_list|)
block|{
name|this
operator|.
name|password
operator|=
name|password
expr_stmt|;
block|}
comment|/**      * Compares all properties except the password.      */
DECL|method|equals (DBMSConnectionProperties properties)
specifier|public
name|boolean
name|equals
parameter_list|(
name|DBMSConnectionProperties
name|properties
parameter_list|)
block|{
return|return
name|this
operator|.
name|type
operator|.
name|equals
argument_list|(
name|properties
operator|.
name|getType
argument_list|()
argument_list|)
operator|&&
name|this
operator|.
name|host
operator|.
name|equalsIgnoreCase
argument_list|(
name|properties
operator|.
name|getHost
argument_list|()
argument_list|)
operator|&&
name|this
operator|.
name|port
operator|==
name|properties
operator|.
name|getPort
argument_list|()
operator|&&
name|this
operator|.
name|database
operator|.
name|equals
argument_list|(
name|properties
operator|.
name|getDatabase
argument_list|()
argument_list|)
operator|&&
name|this
operator|.
name|user
operator|.
name|equals
argument_list|(
name|properties
operator|.
name|getUser
argument_list|()
argument_list|)
return|;
block|}
comment|/**      *  Gets all required data from {@link SharedDatabasePreferences} and sets them if present.      */
DECL|method|setFromPreferences (SharedDatabasePreferences prefs)
specifier|private
name|void
name|setFromPreferences
parameter_list|(
name|SharedDatabasePreferences
name|prefs
parameter_list|)
block|{
if|if
condition|(
name|prefs
operator|.
name|getType
argument_list|()
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|Optional
argument_list|<
name|DBMSType
argument_list|>
name|dbmsType
init|=
name|DBMSType
operator|.
name|fromString
argument_list|(
name|prefs
operator|.
name|getType
argument_list|()
operator|.
name|get
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|dbmsType
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|this
operator|.
name|type
operator|=
name|dbmsType
operator|.
name|get
argument_list|()
expr_stmt|;
block|}
block|}
name|prefs
operator|.
name|getHost
argument_list|()
operator|.
name|ifPresent
argument_list|(
name|theHost
lambda|->
name|this
operator|.
name|host
operator|=
name|theHost
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|getPort
argument_list|()
operator|.
name|ifPresent
argument_list|(
name|thePort
lambda|->
name|this
operator|.
name|port
operator|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|thePort
argument_list|)
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|getName
argument_list|()
operator|.
name|ifPresent
argument_list|(
name|theDatabase
lambda|->
name|this
operator|.
name|database
operator|=
name|theDatabase
argument_list|)
expr_stmt|;
if|if
condition|(
name|prefs
operator|.
name|getUser
argument_list|()
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|this
operator|.
name|user
operator|=
name|prefs
operator|.
name|getUser
argument_list|()
operator|.
name|get
argument_list|()
expr_stmt|;
if|if
condition|(
name|prefs
operator|.
name|getPassword
argument_list|()
operator|.
name|isPresent
argument_list|()
condition|)
block|{
try|try
block|{
name|this
operator|.
name|password
operator|=
operator|new
name|Password
argument_list|(
name|prefs
operator|.
name|getPassword
argument_list|()
operator|.
name|get
argument_list|()
operator|.
name|toCharArray
argument_list|()
argument_list|,
name|prefs
operator|.
name|getUser
argument_list|()
operator|.
name|get
argument_list|()
argument_list|)
operator|.
name|decrypt
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|UnsupportedEncodingException
decl||
name|GeneralSecurityException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Could not decrypt password"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
operator|!
name|prefs
operator|.
name|getPassword
argument_list|()
operator|.
name|isPresent
argument_list|()
condition|)
block|{
comment|// Some DBMS require a non-null value as a password (in case of using an empty string).
name|this
operator|.
name|password
operator|=
literal|""
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

