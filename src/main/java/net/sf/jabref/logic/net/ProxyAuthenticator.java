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
name|net
operator|.
name|Authenticator
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|PasswordAuthentication
import|;
end_import

begin_class
DECL|class|ProxyAuthenticator
specifier|public
class|class
name|ProxyAuthenticator
extends|extends
name|Authenticator
block|{
annotation|@
name|Override
DECL|method|getPasswordAuthentication ()
specifier|protected
name|PasswordAuthentication
name|getPasswordAuthentication
parameter_list|()
block|{
if|if
condition|(
name|getRequestorType
argument_list|()
operator|==
name|RequestorType
operator|.
name|PROXY
condition|)
block|{
name|String
name|prot
init|=
name|getRequestingProtocol
argument_list|()
operator|.
name|toLowerCase
argument_list|()
decl_stmt|;
name|String
name|host
init|=
name|System
operator|.
name|getProperty
argument_list|(
name|prot
operator|+
literal|".proxyHost"
argument_list|,
literal|""
argument_list|)
decl_stmt|;
name|String
name|port
init|=
name|System
operator|.
name|getProperty
argument_list|(
name|prot
operator|+
literal|".proxyPort"
argument_list|,
literal|"80"
argument_list|)
decl_stmt|;
name|String
name|user
init|=
name|System
operator|.
name|getProperty
argument_list|(
name|prot
operator|+
literal|".proxyUser"
argument_list|,
literal|""
argument_list|)
decl_stmt|;
name|String
name|password
init|=
name|System
operator|.
name|getProperty
argument_list|(
name|prot
operator|+
literal|".proxyPassword"
argument_list|,
literal|""
argument_list|)
decl_stmt|;
if|if
condition|(
name|getRequestingHost
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
name|host
argument_list|)
operator|&&
operator|(
name|Integer
operator|.
name|parseInt
argument_list|(
name|port
argument_list|)
operator|==
name|getRequestingPort
argument_list|()
operator|)
condition|)
block|{
return|return
operator|new
name|PasswordAuthentication
argument_list|(
name|user
argument_list|,
name|password
operator|.
name|toCharArray
argument_list|()
argument_list|)
return|;
block|}
block|}
return|return
literal|null
return|;
block|}
block|}
end_class

end_unit

