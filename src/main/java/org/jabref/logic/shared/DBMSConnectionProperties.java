begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.shared
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
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
name|Objects
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
name|java
operator|.
name|util
operator|.
name|Properties
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
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
name|org
operator|.
name|jabref
operator|.
name|logic
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
name|jabref
operator|.
name|model
operator|.
name|database
operator|.
name|shared
operator|.
name|DBMSType
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|database
operator|.
name|shared
operator|.
name|DatabaseConnectionProperties
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|Logger
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|LoggerFactory
import|;
end_import

begin_comment
comment|/**  * Keeps all essential data for establishing a new connection to a DBMS using {@link DBMSConnection}.  */
end_comment

begin_class
DECL|class|DBMSConnectionProperties
specifier|public
class|class
name|DBMSConnectionProperties
implements|implements
name|DatabaseConnectionProperties
block|{
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Logger
name|LOGGER
init|=
name|LoggerFactory
operator|.
name|getLogger
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
DECL|field|useSSL
specifier|private
name|boolean
name|useSSL
decl_stmt|;
DECL|field|serverTimezone
specifier|private
name|String
name|serverTimezone
decl_stmt|;
comment|//Not needed for connection, but stored for future login
DECL|field|keyStore
specifier|private
name|String
name|keyStore
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
DECL|method|DBMSConnectionProperties (DBMSType type, String host, int port, String database, String user, String password, boolean useSSL, String serverTimezone)
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
parameter_list|,
name|boolean
name|useSSL
parameter_list|,
name|String
name|serverTimezone
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
name|this
operator|.
name|useSSL
operator|=
name|useSSL
expr_stmt|;
name|this
operator|.
name|serverTimezone
operator|=
name|serverTimezone
expr_stmt|;
block|}
annotation|@
name|Override
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
annotation|@
name|Override
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
annotation|@
name|Override
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
annotation|@
name|Override
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
annotation|@
name|Override
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
annotation|@
name|Override
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
annotation|@
name|Override
DECL|method|isUseSSL ()
specifier|public
name|boolean
name|isUseSSL
parameter_list|()
block|{
return|return
name|useSSL
return|;
block|}
DECL|method|setUseSSL (boolean useSSL)
specifier|public
name|void
name|setUseSSL
parameter_list|(
name|boolean
name|useSSL
parameter_list|)
block|{
name|this
operator|.
name|useSSL
operator|=
name|useSSL
expr_stmt|;
block|}
DECL|method|getUrl ()
specifier|public
name|String
name|getUrl
parameter_list|()
block|{
return|return
name|type
operator|.
name|getUrl
argument_list|(
name|host
argument_list|,
name|port
argument_list|,
name|database
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|getServerTimezone ()
specifier|public
name|String
name|getServerTimezone
parameter_list|()
block|{
return|return
name|serverTimezone
return|;
block|}
DECL|method|setServerTimezone (String serverTimezone)
specifier|public
name|void
name|setServerTimezone
parameter_list|(
name|String
name|serverTimezone
parameter_list|)
block|{
name|this
operator|.
name|serverTimezone
operator|=
name|serverTimezone
expr_stmt|;
block|}
comment|/**      * Returns username, password and ssl as Properties Object      * @return Properties with values for user, password and ssl      */
DECL|method|asProperties ()
specifier|public
name|Properties
name|asProperties
parameter_list|()
block|{
name|Properties
name|props
init|=
operator|new
name|Properties
argument_list|()
decl_stmt|;
name|props
operator|.
name|setProperty
argument_list|(
literal|"user"
argument_list|,
name|user
argument_list|)
expr_stmt|;
name|props
operator|.
name|setProperty
argument_list|(
literal|"password"
argument_list|,
name|password
argument_list|)
expr_stmt|;
name|props
operator|.
name|setProperty
argument_list|(
literal|"serverTimezone"
argument_list|,
name|serverTimezone
argument_list|)
expr_stmt|;
if|if
condition|(
name|useSSL
condition|)
block|{
name|props
operator|.
name|setProperty
argument_list|(
literal|"ssl"
argument_list|,
name|Boolean
operator|.
name|toString
argument_list|(
name|useSSL
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|props
return|;
block|}
annotation|@
name|Override
DECL|method|getKeyStore ()
specifier|public
name|String
name|getKeyStore
parameter_list|()
block|{
return|return
name|keyStore
return|;
block|}
DECL|method|setKeyStore (String keyStore)
specifier|public
name|void
name|setKeyStore
parameter_list|(
name|String
name|keyStore
parameter_list|)
block|{
name|this
operator|.
name|keyStore
operator|=
name|keyStore
expr_stmt|;
block|}
comment|/**      * Compares all properties except the password.      */
annotation|@
name|Override
DECL|method|equals (Object obj)
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|obj
parameter_list|)
block|{
if|if
condition|(
name|this
operator|==
name|obj
condition|)
block|{
return|return
literal|true
return|;
block|}
if|if
condition|(
operator|!
operator|(
name|obj
operator|instanceof
name|DBMSConnectionProperties
operator|)
condition|)
block|{
return|return
literal|false
return|;
block|}
name|DBMSConnectionProperties
name|properties
init|=
operator|(
name|DBMSConnectionProperties
operator|)
name|obj
decl_stmt|;
return|return
name|Objects
operator|.
name|equals
argument_list|(
name|type
argument_list|,
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
name|Objects
operator|.
name|equals
argument_list|(
name|port
argument_list|,
name|properties
operator|.
name|getPort
argument_list|()
argument_list|)
operator|&&
name|Objects
operator|.
name|equals
argument_list|(
name|database
argument_list|,
name|properties
operator|.
name|getDatabase
argument_list|()
argument_list|)
operator|&&
name|Objects
operator|.
name|equals
argument_list|(
name|user
argument_list|,
name|properties
operator|.
name|getUser
argument_list|()
argument_list|)
operator|&&
name|Objects
operator|.
name|equals
argument_list|(
name|useSSL
argument_list|,
name|properties
operator|.
name|isUseSSL
argument_list|()
argument_list|)
operator|&&
name|Objects
operator|.
name|equals
argument_list|(
name|serverTimezone
argument_list|,
name|properties
operator|.
name|getServerTimezone
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|hashCode ()
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|Objects
operator|.
name|hash
argument_list|(
name|type
argument_list|,
name|host
argument_list|,
name|port
argument_list|,
name|database
argument_list|,
name|user
argument_list|,
name|useSSL
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
name|prefs
operator|.
name|getKeyStoreFile
argument_list|()
operator|.
name|ifPresent
argument_list|(
name|theKeystore
lambda|->
name|this
operator|.
name|keyStore
operator|=
name|theKeystore
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|getServerTimezone
argument_list|()
operator|.
name|ifPresent
argument_list|(
name|theServerTimezone
lambda|->
name|this
operator|.
name|serverTimezone
operator|=
name|theServerTimezone
argument_list|)
expr_stmt|;
name|this
operator|.
name|setUseSSL
argument_list|(
name|prefs
operator|.
name|isUseSSL
argument_list|()
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
annotation|@
name|Override
DECL|method|isValid ()
specifier|public
name|boolean
name|isValid
parameter_list|()
block|{
return|return
name|Objects
operator|.
name|nonNull
argument_list|(
name|type
argument_list|)
operator|&&
name|Objects
operator|.
name|nonNull
argument_list|(
name|host
argument_list|)
operator|&&
name|Objects
operator|.
name|nonNull
argument_list|(
name|port
argument_list|)
operator|&&
name|Objects
operator|.
name|nonNull
argument_list|(
name|database
argument_list|)
operator|&&
name|Objects
operator|.
name|nonNull
argument_list|(
name|user
argument_list|)
operator|&&
name|Objects
operator|.
name|nonNull
argument_list|(
name|password
argument_list|)
return|;
block|}
block|}
end_class

end_unit

