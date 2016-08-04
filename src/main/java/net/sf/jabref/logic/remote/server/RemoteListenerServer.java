begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

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
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

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
name|ServerSocket
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
name|java
operator|.
name|net
operator|.
name|SocketException
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

begin_class
DECL|class|RemoteListenerServer
specifier|public
class|class
name|RemoteListenerServer
implements|implements
name|Runnable
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
name|RemoteListenerServer
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|BACKLOG
specifier|private
specifier|static
specifier|final
name|int
name|BACKLOG
init|=
literal|1
decl_stmt|;
DECL|field|ONE_SECOND_TIMEOUT
specifier|private
specifier|static
specifier|final
name|int
name|ONE_SECOND_TIMEOUT
init|=
literal|1000
decl_stmt|;
DECL|field|messageHandler
specifier|private
specifier|final
name|MessageHandler
name|messageHandler
decl_stmt|;
DECL|field|serverSocket
specifier|private
specifier|final
name|ServerSocket
name|serverSocket
decl_stmt|;
DECL|method|RemoteListenerServer (MessageHandler messageHandler, int port)
specifier|public
name|RemoteListenerServer
parameter_list|(
name|MessageHandler
name|messageHandler
parameter_list|,
name|int
name|port
parameter_list|)
throws|throws
name|IOException
block|{
name|this
operator|.
name|serverSocket
operator|=
operator|new
name|ServerSocket
argument_list|(
name|port
argument_list|,
name|BACKLOG
argument_list|,
name|InetAddress
operator|.
name|getByName
argument_list|(
literal|"localhost"
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|messageHandler
operator|=
name|messageHandler
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|run ()
specifier|public
name|void
name|run
parameter_list|()
block|{
try|try
block|{
while|while
condition|(
operator|!
name|Thread
operator|.
name|interrupted
argument_list|()
condition|)
block|{
try|try
init|(
name|Socket
name|socket
init|=
name|serverSocket
operator|.
name|accept
argument_list|()
init|)
block|{
name|socket
operator|.
name|setSoTimeout
argument_list|(
name|ONE_SECOND_TIMEOUT
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
name|protocol
operator|.
name|sendMessage
argument_list|(
name|Protocol
operator|.
name|IDENTIFIER
argument_list|)
expr_stmt|;
name|String
name|message
init|=
name|protocol
operator|.
name|receiveMessage
argument_list|()
decl_stmt|;
name|protocol
operator|.
name|close
argument_list|()
expr_stmt|;
if|if
condition|(
name|message
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
continue|continue;
block|}
name|messageHandler
operator|.
name|handleMessage
argument_list|(
name|message
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|SocketException
name|ex
parameter_list|)
block|{
return|return;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"RemoteListenerServer crashed"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
block|}
finally|finally
block|{
name|closeServerSocket
argument_list|()
expr_stmt|;
block|}
block|}
DECL|method|closeServerSocket ()
specifier|public
name|void
name|closeServerSocket
parameter_list|()
block|{
try|try
block|{
name|serverSocket
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|ignored
parameter_list|)
block|{
comment|// Ignored
block|}
block|}
block|}
end_class

end_unit

