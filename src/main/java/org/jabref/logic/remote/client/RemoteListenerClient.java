begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.remote.client
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|remote
operator|.
name|client
package|;
end_package

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|InetAddress
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|Socket
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
name|l10n
operator|.
name|Localization
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
name|remote
operator|.
name|shared
operator|.
name|Protocol
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

begin_class
DECL|class|RemoteListenerClient
specifier|public
class|class
name|RemoteListenerClient
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
name|RemoteListenerClient
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|TIMEOUT
specifier|private
specifier|static
specifier|final
name|int
name|TIMEOUT
init|=
literal|2000
decl_stmt|;
DECL|method|RemoteListenerClient ()
specifier|private
name|RemoteListenerClient
parameter_list|()
block|{     }
comment|/**      * Attempt to send command line arguments to already running JabRef instance.      *      * @param args Command line arguments.      * @return true if successful, false otherwise.      */
DECL|method|sendToActiveJabRefInstance (String[] args, int remoteServerPort)
specifier|public
specifier|static
name|boolean
name|sendToActiveJabRefInstance
parameter_list|(
name|String
index|[]
name|args
parameter_list|,
name|int
name|remoteServerPort
parameter_list|)
block|{
try|try
init|(
name|Socket
name|socket
init|=
operator|new
name|Socket
argument_list|(
name|InetAddress
operator|.
name|getByName
argument_list|(
literal|"localhost"
argument_list|)
argument_list|,
name|remoteServerPort
argument_list|)
init|)
block|{
name|socket
operator|.
name|setSoTimeout
argument_list|(
name|TIMEOUT
argument_list|)
expr_stmt|;
name|Protocol
name|protocol
init|=
operator|new
name|Protocol
argument_list|(
name|socket
argument_list|)
decl_stmt|;
try|try
block|{
name|String
name|identifier
init|=
name|protocol
operator|.
name|receiveMessage
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|Protocol
operator|.
name|IDENTIFIER
operator|.
name|equals
argument_list|(
name|identifier
argument_list|)
condition|)
block|{
name|String
name|port
init|=
name|String
operator|.
name|valueOf
argument_list|(
name|remoteServerPort
argument_list|)
decl_stmt|;
name|String
name|errorMessage
init|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"Cannot use port %0 for remote operation; another application may be using it. Try specifying another port."
argument_list|,
name|port
argument_list|)
decl_stmt|;
name|LOGGER
operator|.
name|error
argument_list|(
name|errorMessage
argument_list|)
expr_stmt|;
return|return
literal|false
return|;
block|}
name|protocol
operator|.
name|sendMessage
argument_list|(
name|String
operator|.
name|join
argument_list|(
literal|"\n"
argument_list|,
name|args
argument_list|)
argument_list|)
expr_stmt|;
return|return
literal|true
return|;
block|}
finally|finally
block|{
name|protocol
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|debug
argument_list|(
literal|"Could not send args "
operator|+
name|String
operator|.
name|join
argument_list|(
literal|", "
argument_list|,
name|args
argument_list|)
operator|+
literal|" to the server at port "
operator|+
name|remoteServerPort
argument_list|,
name|e
argument_list|)
expr_stmt|;
return|return
literal|false
return|;
block|}
block|}
block|}
end_class

end_unit

