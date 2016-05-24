begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.remote.shared
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
name|shared
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
name|io
operator|.
name|InputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|OutputStream
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
name|SocketTimeoutException
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
comment|/**  * Every message is terminated with '\0'.  */
end_comment

begin_class
DECL|class|Protocol
specifier|public
class|class
name|Protocol
block|{
DECL|field|IDENTIFIER
specifier|public
specifier|static
specifier|final
name|String
name|IDENTIFIER
init|=
literal|"jabref"
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
name|Protocol
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|socket
specifier|private
specifier|final
name|Socket
name|socket
decl_stmt|;
DECL|field|out
specifier|private
specifier|final
name|OutputStream
name|out
decl_stmt|;
DECL|field|in
specifier|private
specifier|final
name|InputStream
name|in
decl_stmt|;
DECL|method|Protocol (Socket socket)
specifier|public
name|Protocol
parameter_list|(
name|Socket
name|socket
parameter_list|)
throws|throws
name|IOException
block|{
name|this
operator|.
name|socket
operator|=
name|socket
expr_stmt|;
name|this
operator|.
name|out
operator|=
name|socket
operator|.
name|getOutputStream
argument_list|()
expr_stmt|;
name|this
operator|.
name|in
operator|=
name|socket
operator|.
name|getInputStream
argument_list|()
expr_stmt|;
block|}
DECL|method|sendMessage (String message)
specifier|public
name|void
name|sendMessage
parameter_list|(
name|String
name|message
parameter_list|)
throws|throws
name|IOException
block|{
name|out
operator|.
name|write
argument_list|(
name|message
operator|.
name|getBytes
argument_list|()
argument_list|)
expr_stmt|;
name|out
operator|.
name|write
argument_list|(
literal|'\0'
argument_list|)
expr_stmt|;
name|out
operator|.
name|flush
argument_list|()
expr_stmt|;
block|}
DECL|method|receiveMessage ()
specifier|public
name|String
name|receiveMessage
parameter_list|()
throws|throws
name|IOException
block|{
name|int
name|c
decl_stmt|;
name|StringBuilder
name|result
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
try|try
block|{
while|while
condition|(
operator|(
operator|(
name|c
operator|=
name|in
operator|.
name|read
argument_list|()
operator|)
operator|!=
literal|'\0'
operator|)
operator|&&
operator|(
name|c
operator|>=
literal|0
operator|)
condition|)
block|{
name|result
operator|.
name|append
argument_list|(
operator|(
name|char
operator|)
name|c
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|SocketTimeoutException
name|ex
parameter_list|)
block|{
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Connection timed out."
argument_list|,
name|ex
argument_list|)
expr_stmt|;
block|}
return|return
name|result
operator|.
name|toString
argument_list|()
return|;
block|}
DECL|method|close ()
specifier|public
name|void
name|close
parameter_list|()
block|{
try|try
block|{
name|in
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
try|try
block|{
name|out
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
try|try
block|{
name|socket
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

