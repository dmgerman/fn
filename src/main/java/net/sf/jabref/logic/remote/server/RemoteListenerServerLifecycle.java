begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.remote.server
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|remote
operator|.
name|server
package|;
end_package

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|JabRefExecutorService
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

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_comment
comment|/**  * Manages the RemoteListenerServerThread through typical life cycle methods.  *<p/>  * open -> start -> stop  * openAndStart -> stop  *<p/>  * Observer: isOpen, isNotStartedBefore  */
end_comment

begin_class
DECL|class|RemoteListenerServerLifecycle
specifier|public
class|class
name|RemoteListenerServerLifecycle
block|{
DECL|field|remoteListenerServerThread
specifier|private
name|RemoteListenerServerThread
name|remoteListenerServerThread
init|=
literal|null
decl_stmt|;
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
name|RemoteListenerServerLifecycle
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|method|stop ()
specifier|public
name|void
name|stop
parameter_list|()
block|{
if|if
condition|(
name|isOpen
argument_list|()
condition|)
block|{
name|remoteListenerServerThread
operator|.
name|interrupt
argument_list|()
expr_stmt|;
name|remoteListenerServerThread
operator|=
literal|null
expr_stmt|;
block|}
block|}
comment|/**      * Acquire any resources needed for the server.      */
DECL|method|open (MessageHandler messageHandler, int port)
specifier|public
name|void
name|open
parameter_list|(
name|MessageHandler
name|messageHandler
parameter_list|,
name|int
name|port
parameter_list|)
block|{
if|if
condition|(
name|isOpen
argument_list|()
condition|)
block|{
return|return;
block|}
name|RemoteListenerServerThread
name|result
decl_stmt|;
try|try
block|{
name|result
operator|=
operator|new
name|RemoteListenerServerThread
argument_list|(
name|messageHandler
argument_list|,
name|port
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
if|if
condition|(
operator|!
name|e
operator|.
name|getMessage
argument_list|()
operator|.
name|startsWith
argument_list|(
literal|"Address already in use"
argument_list|)
condition|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Port is blocked"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
name|result
operator|=
literal|null
expr_stmt|;
block|}
name|remoteListenerServerThread
operator|=
name|result
expr_stmt|;
block|}
DECL|method|isOpen ()
specifier|public
name|boolean
name|isOpen
parameter_list|()
block|{
return|return
name|remoteListenerServerThread
operator|!=
literal|null
return|;
block|}
DECL|method|start ()
specifier|public
name|void
name|start
parameter_list|()
block|{
if|if
condition|(
name|isOpen
argument_list|()
operator|&&
name|isNotStartedBefore
argument_list|()
condition|)
block|{
comment|// threads can only be started when in state NEW
name|JabRefExecutorService
operator|.
name|INSTANCE
operator|.
name|executeInOwnThread
argument_list|(
name|remoteListenerServerThread
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|isNotStartedBefore ()
specifier|private
name|boolean
name|isNotStartedBefore
parameter_list|()
block|{
comment|// threads can only be started when in state NEW
return|return
name|remoteListenerServerThread
operator|.
name|getState
argument_list|()
operator|==
name|Thread
operator|.
name|State
operator|.
name|NEW
return|;
block|}
DECL|method|openAndStart (MessageHandler messageHandler, int port)
specifier|public
name|void
name|openAndStart
parameter_list|(
name|MessageHandler
name|messageHandler
parameter_list|,
name|int
name|port
parameter_list|)
block|{
name|open
argument_list|(
name|messageHandler
argument_list|,
name|port
argument_list|)
expr_stmt|;
name|start
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

