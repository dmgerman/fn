begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.net
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|net
package|;
end_package

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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|JabRefPreferences
import|;
end_import

begin_class
DECL|class|ProxyPreferences
specifier|public
class|class
name|ProxyPreferences
block|{
DECL|field|useProxy
specifier|private
specifier|final
name|Boolean
name|useProxy
decl_stmt|;
DECL|field|hostname
specifier|private
specifier|final
name|String
name|hostname
decl_stmt|;
DECL|field|port
specifier|private
specifier|final
name|String
name|port
decl_stmt|;
DECL|field|useAuthentication
specifier|private
specifier|final
name|Boolean
name|useAuthentication
decl_stmt|;
DECL|field|username
specifier|private
specifier|final
name|String
name|username
decl_stmt|;
DECL|field|password
specifier|private
specifier|final
name|String
name|password
decl_stmt|;
DECL|method|ProxyPreferences (Boolean useProxy, String hostname, String port, Boolean useAuthentication, String username, String password)
specifier|public
name|ProxyPreferences
parameter_list|(
name|Boolean
name|useProxy
parameter_list|,
name|String
name|hostname
parameter_list|,
name|String
name|port
parameter_list|,
name|Boolean
name|useAuthentication
parameter_list|,
name|String
name|username
parameter_list|,
name|String
name|password
parameter_list|)
block|{
name|this
operator|.
name|useProxy
operator|=
name|useProxy
expr_stmt|;
name|this
operator|.
name|hostname
operator|=
name|hostname
expr_stmt|;
name|this
operator|.
name|port
operator|=
name|port
expr_stmt|;
name|this
operator|.
name|useAuthentication
operator|=
name|useAuthentication
expr_stmt|;
name|this
operator|.
name|username
operator|=
name|username
expr_stmt|;
name|this
operator|.
name|password
operator|=
name|password
expr_stmt|;
block|}
DECL|method|isUseProxy ()
specifier|public
specifier|final
name|Boolean
name|isUseProxy
parameter_list|()
block|{
return|return
name|useProxy
return|;
block|}
DECL|method|getHostname ()
specifier|public
specifier|final
name|String
name|getHostname
parameter_list|()
block|{
return|return
name|hostname
return|;
block|}
DECL|method|getPort ()
specifier|public
specifier|final
name|String
name|getPort
parameter_list|()
block|{
return|return
name|port
return|;
block|}
DECL|method|isUseAuthentication ()
specifier|public
specifier|final
name|Boolean
name|isUseAuthentication
parameter_list|()
block|{
return|return
name|useAuthentication
return|;
block|}
DECL|method|getUsername ()
specifier|public
specifier|final
name|String
name|getUsername
parameter_list|()
block|{
return|return
name|username
return|;
block|}
DECL|method|getPassword ()
specifier|public
specifier|final
name|String
name|getPassword
parameter_list|()
block|{
return|return
name|password
return|;
block|}
DECL|method|storeInPreferences (JabRefPreferences preferences)
specifier|public
name|void
name|storeInPreferences
parameter_list|(
name|JabRefPreferences
name|preferences
parameter_list|)
block|{
name|preferences
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|PROXY_USE
argument_list|,
name|isUseProxy
argument_list|()
argument_list|)
expr_stmt|;
name|preferences
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|PROXY_HOSTNAME
argument_list|,
name|getHostname
argument_list|()
argument_list|)
expr_stmt|;
name|preferences
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|PROXY_PORT
argument_list|,
name|getPort
argument_list|()
argument_list|)
expr_stmt|;
name|preferences
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|PROXY_USE_AUTHENTICATION
argument_list|,
name|isUseAuthentication
argument_list|()
argument_list|)
expr_stmt|;
name|preferences
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|PROXY_USERNAME
argument_list|,
name|getUsername
argument_list|()
argument_list|)
expr_stmt|;
name|preferences
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|PROXY_PASSWORD
argument_list|,
name|getPassword
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|loadFromPreferences (JabRefPreferences preferences)
specifier|public
specifier|static
name|ProxyPreferences
name|loadFromPreferences
parameter_list|(
name|JabRefPreferences
name|preferences
parameter_list|)
block|{
name|Boolean
name|useProxy
init|=
name|preferences
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|PROXY_USE
argument_list|)
decl_stmt|;
name|String
name|hostname
init|=
name|preferences
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|PROXY_HOSTNAME
argument_list|)
decl_stmt|;
name|String
name|port
init|=
name|preferences
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|PROXY_PORT
argument_list|)
decl_stmt|;
name|Boolean
name|useAuthentication
init|=
name|preferences
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|PROXY_USE_AUTHENTICATION
argument_list|)
decl_stmt|;
name|String
name|username
init|=
name|preferences
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|PROXY_USERNAME
argument_list|)
decl_stmt|;
name|String
name|password
init|=
name|preferences
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|PROXY_PASSWORD
argument_list|)
decl_stmt|;
return|return
operator|new
name|ProxyPreferences
argument_list|(
name|useProxy
argument_list|,
name|hostname
argument_list|,
name|port
argument_list|,
name|useAuthentication
argument_list|,
name|username
argument_list|,
name|password
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
name|hostname
argument_list|,
name|password
argument_list|,
name|port
argument_list|,
name|useAuthentication
argument_list|,
name|useProxy
argument_list|,
name|username
argument_list|)
return|;
block|}
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
name|obj
operator|instanceof
name|ProxyPreferences
condition|)
block|{
name|ProxyPreferences
name|other
init|=
operator|(
name|ProxyPreferences
operator|)
name|obj
decl_stmt|;
if|if
condition|(
name|hostname
operator|==
literal|null
condition|)
block|{
if|if
condition|(
name|other
operator|.
name|hostname
operator|!=
literal|null
condition|)
block|{
return|return
literal|false
return|;
block|}
block|}
elseif|else
if|if
condition|(
operator|!
name|hostname
operator|.
name|equals
argument_list|(
name|other
operator|.
name|hostname
argument_list|)
condition|)
block|{
return|return
literal|false
return|;
block|}
if|if
condition|(
name|password
operator|==
literal|null
condition|)
block|{
if|if
condition|(
name|other
operator|.
name|password
operator|!=
literal|null
condition|)
block|{
return|return
literal|false
return|;
block|}
block|}
elseif|else
if|if
condition|(
operator|!
name|password
operator|.
name|equals
argument_list|(
name|other
operator|.
name|password
argument_list|)
condition|)
block|{
return|return
literal|false
return|;
block|}
if|if
condition|(
name|port
operator|==
literal|null
condition|)
block|{
if|if
condition|(
name|other
operator|.
name|port
operator|!=
literal|null
condition|)
block|{
return|return
literal|false
return|;
block|}
block|}
elseif|else
if|if
condition|(
operator|!
name|port
operator|.
name|equals
argument_list|(
name|other
operator|.
name|port
argument_list|)
condition|)
block|{
return|return
literal|false
return|;
block|}
if|if
condition|(
name|useAuthentication
operator|==
literal|null
condition|)
block|{
if|if
condition|(
name|other
operator|.
name|useAuthentication
operator|!=
literal|null
condition|)
block|{
return|return
literal|false
return|;
block|}
block|}
elseif|else
if|if
condition|(
operator|!
name|useAuthentication
operator|.
name|equals
argument_list|(
name|other
operator|.
name|useAuthentication
argument_list|)
condition|)
block|{
return|return
literal|false
return|;
block|}
if|if
condition|(
name|useProxy
operator|==
literal|null
condition|)
block|{
if|if
condition|(
name|other
operator|.
name|useProxy
operator|!=
literal|null
condition|)
block|{
return|return
literal|false
return|;
block|}
block|}
elseif|else
if|if
condition|(
operator|!
name|useProxy
operator|.
name|equals
argument_list|(
name|other
operator|.
name|useProxy
argument_list|)
condition|)
block|{
return|return
literal|false
return|;
block|}
if|if
condition|(
name|username
operator|==
literal|null
condition|)
block|{
if|if
condition|(
name|other
operator|.
name|username
operator|!=
literal|null
condition|)
block|{
return|return
literal|false
return|;
block|}
block|}
elseif|else
if|if
condition|(
operator|!
name|username
operator|.
name|equals
argument_list|(
name|other
operator|.
name|username
argument_list|)
condition|)
block|{
return|return
literal|false
return|;
block|}
return|return
literal|true
return|;
block|}
return|return
literal|false
return|;
block|}
block|}
end_class

end_unit

