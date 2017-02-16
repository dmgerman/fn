begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.shared.prefs
package|package
name|org
operator|.
name|jabref
operator|.
name|shared
operator|.
name|prefs
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
name|java
operator|.
name|util
operator|.
name|prefs
operator|.
name|BackingStoreException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|prefs
operator|.
name|Preferences
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|JabRefMain
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|gui
operator|.
name|shared
operator|.
name|ConnectToSharedDatabaseDialog
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|shared
operator|.
name|DBMSConnectionProperties
import|;
end_import

begin_import
import|import
name|org
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
comment|/**  * Stores and reads persistent data for {@link ConnectToSharedDatabaseDialog}.  */
end_comment

begin_class
DECL|class|SharedDatabasePreferences
specifier|public
class|class
name|SharedDatabasePreferences
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
name|SharedDatabasePreferences
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|DEFAULT_NODE
specifier|private
specifier|static
specifier|final
name|String
name|DEFAULT_NODE
init|=
literal|"default"
decl_stmt|;
DECL|field|PARENT_NODE
specifier|private
specifier|static
specifier|final
name|String
name|PARENT_NODE
init|=
literal|"jabref-shared"
decl_stmt|;
DECL|field|SHARED_DATABASE_TYPE
specifier|private
specifier|static
specifier|final
name|String
name|SHARED_DATABASE_TYPE
init|=
literal|"sharedDatabaseType"
decl_stmt|;
DECL|field|SHARED_DATABASE_HOST
specifier|private
specifier|static
specifier|final
name|String
name|SHARED_DATABASE_HOST
init|=
literal|"sharedDatabaseHost"
decl_stmt|;
DECL|field|SHARED_DATABASE_PORT
specifier|private
specifier|static
specifier|final
name|String
name|SHARED_DATABASE_PORT
init|=
literal|"sharedDatabasePort"
decl_stmt|;
DECL|field|SHARED_DATABASE_NAME
specifier|private
specifier|static
specifier|final
name|String
name|SHARED_DATABASE_NAME
init|=
literal|"sharedDatabaseName"
decl_stmt|;
DECL|field|SHARED_DATABASE_USER
specifier|private
specifier|static
specifier|final
name|String
name|SHARED_DATABASE_USER
init|=
literal|"sharedDatabaseUser"
decl_stmt|;
DECL|field|SHARED_DATABASE_PASSWORD
specifier|private
specifier|static
specifier|final
name|String
name|SHARED_DATABASE_PASSWORD
init|=
literal|"sharedDatabasePassword"
decl_stmt|;
DECL|field|SHARED_DATABASE_REMEMBER_PASSWORD
specifier|private
specifier|static
specifier|final
name|String
name|SHARED_DATABASE_REMEMBER_PASSWORD
init|=
literal|"sharedDatabaseRememberPassword"
decl_stmt|;
comment|// This {@link Preferences} is used only for things which should not appear in real JabRefPreferences due to security reasons.
DECL|field|internalPrefs
specifier|private
specifier|final
name|Preferences
name|internalPrefs
decl_stmt|;
DECL|method|SharedDatabasePreferences ()
specifier|public
name|SharedDatabasePreferences
parameter_list|()
block|{
name|this
argument_list|(
name|DEFAULT_NODE
argument_list|)
expr_stmt|;
block|}
DECL|method|SharedDatabasePreferences (String sharedDatabaseID)
specifier|public
name|SharedDatabasePreferences
parameter_list|(
name|String
name|sharedDatabaseID
parameter_list|)
block|{
name|internalPrefs
operator|=
name|Preferences
operator|.
name|userNodeForPackage
argument_list|(
name|JabRefMain
operator|.
name|class
argument_list|)
operator|.
name|parent
argument_list|()
operator|.
name|node
argument_list|(
name|PARENT_NODE
argument_list|)
operator|.
name|node
argument_list|(
name|sharedDatabaseID
argument_list|)
expr_stmt|;
block|}
DECL|method|getType ()
specifier|public
name|Optional
argument_list|<
name|String
argument_list|>
name|getType
parameter_list|()
block|{
return|return
name|getOptionalValue
argument_list|(
name|SHARED_DATABASE_TYPE
argument_list|)
return|;
block|}
DECL|method|getHost ()
specifier|public
name|Optional
argument_list|<
name|String
argument_list|>
name|getHost
parameter_list|()
block|{
return|return
name|getOptionalValue
argument_list|(
name|SHARED_DATABASE_HOST
argument_list|)
return|;
block|}
DECL|method|getPort ()
specifier|public
name|Optional
argument_list|<
name|String
argument_list|>
name|getPort
parameter_list|()
block|{
return|return
name|getOptionalValue
argument_list|(
name|SHARED_DATABASE_PORT
argument_list|)
return|;
block|}
DECL|method|getName ()
specifier|public
name|Optional
argument_list|<
name|String
argument_list|>
name|getName
parameter_list|()
block|{
return|return
name|getOptionalValue
argument_list|(
name|SHARED_DATABASE_NAME
argument_list|)
return|;
block|}
DECL|method|getUser ()
specifier|public
name|Optional
argument_list|<
name|String
argument_list|>
name|getUser
parameter_list|()
block|{
return|return
name|getOptionalValue
argument_list|(
name|SHARED_DATABASE_USER
argument_list|)
return|;
block|}
DECL|method|getPassword ()
specifier|public
name|Optional
argument_list|<
name|String
argument_list|>
name|getPassword
parameter_list|()
block|{
return|return
name|getOptionalValue
argument_list|(
name|SHARED_DATABASE_PASSWORD
argument_list|)
return|;
block|}
DECL|method|getRememberPassword ()
specifier|public
name|boolean
name|getRememberPassword
parameter_list|()
block|{
return|return
name|internalPrefs
operator|.
name|getBoolean
argument_list|(
name|SHARED_DATABASE_REMEMBER_PASSWORD
argument_list|,
literal|false
argument_list|)
return|;
block|}
DECL|method|setType (String type)
specifier|public
name|void
name|setType
parameter_list|(
name|String
name|type
parameter_list|)
block|{
name|internalPrefs
operator|.
name|put
argument_list|(
name|SHARED_DATABASE_TYPE
argument_list|,
name|type
argument_list|)
expr_stmt|;
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
name|internalPrefs
operator|.
name|put
argument_list|(
name|SHARED_DATABASE_HOST
argument_list|,
name|host
argument_list|)
expr_stmt|;
block|}
DECL|method|setPort (String port)
specifier|public
name|void
name|setPort
parameter_list|(
name|String
name|port
parameter_list|)
block|{
name|internalPrefs
operator|.
name|put
argument_list|(
name|SHARED_DATABASE_PORT
argument_list|,
name|port
argument_list|)
expr_stmt|;
block|}
DECL|method|setName (String name)
specifier|public
name|void
name|setName
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|internalPrefs
operator|.
name|put
argument_list|(
name|SHARED_DATABASE_NAME
argument_list|,
name|name
argument_list|)
expr_stmt|;
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
name|internalPrefs
operator|.
name|put
argument_list|(
name|SHARED_DATABASE_USER
argument_list|,
name|user
argument_list|)
expr_stmt|;
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
name|internalPrefs
operator|.
name|put
argument_list|(
name|SHARED_DATABASE_PASSWORD
argument_list|,
name|password
argument_list|)
expr_stmt|;
block|}
DECL|method|setRememberPassword (boolean rememberPassword)
specifier|public
name|void
name|setRememberPassword
parameter_list|(
name|boolean
name|rememberPassword
parameter_list|)
block|{
name|internalPrefs
operator|.
name|putBoolean
argument_list|(
name|SHARED_DATABASE_REMEMBER_PASSWORD
argument_list|,
name|rememberPassword
argument_list|)
expr_stmt|;
block|}
DECL|method|clearPassword ()
specifier|public
name|void
name|clearPassword
parameter_list|()
block|{
name|internalPrefs
operator|.
name|remove
argument_list|(
name|SHARED_DATABASE_PASSWORD
argument_list|)
expr_stmt|;
block|}
DECL|method|clear ()
specifier|public
name|void
name|clear
parameter_list|()
throws|throws
name|BackingStoreException
block|{
name|internalPrefs
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
DECL|method|getOptionalValue (String key)
specifier|private
name|Optional
argument_list|<
name|String
argument_list|>
name|getOptionalValue
parameter_list|(
name|String
name|key
parameter_list|)
block|{
return|return
name|Optional
operator|.
name|ofNullable
argument_list|(
name|internalPrefs
operator|.
name|get
argument_list|(
name|key
argument_list|,
literal|null
argument_list|)
argument_list|)
return|;
block|}
DECL|method|clearAll ()
specifier|public
specifier|static
name|void
name|clearAll
parameter_list|()
throws|throws
name|BackingStoreException
block|{
name|Preferences
operator|.
name|userNodeForPackage
argument_list|(
name|JabRefMain
operator|.
name|class
argument_list|)
operator|.
name|parent
argument_list|()
operator|.
name|node
argument_list|(
name|PARENT_NODE
argument_list|)
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
DECL|method|putAllDBMSConnectionProperties (DBMSConnectionProperties properties)
specifier|public
name|void
name|putAllDBMSConnectionProperties
parameter_list|(
name|DBMSConnectionProperties
name|properties
parameter_list|)
block|{
assert|assert
operator|(
name|properties
operator|.
name|isValid
argument_list|()
operator|)
assert|;
name|setType
argument_list|(
name|properties
operator|.
name|getType
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|setHost
argument_list|(
name|properties
operator|.
name|getHost
argument_list|()
argument_list|)
expr_stmt|;
name|setPort
argument_list|(
name|String
operator|.
name|valueOf
argument_list|(
name|properties
operator|.
name|getPort
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|setName
argument_list|(
name|properties
operator|.
name|getDatabase
argument_list|()
argument_list|)
expr_stmt|;
name|setUser
argument_list|(
name|properties
operator|.
name|getUser
argument_list|()
argument_list|)
expr_stmt|;
try|try
block|{
name|setPassword
argument_list|(
operator|new
name|Password
argument_list|(
name|properties
operator|.
name|getPassword
argument_list|()
operator|.
name|toCharArray
argument_list|()
argument_list|,
name|properties
operator|.
name|getUser
argument_list|()
argument_list|)
operator|.
name|encrypt
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|GeneralSecurityException
decl||
name|UnsupportedEncodingException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Could not store the password due to encryption problems."
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

