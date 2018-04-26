begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.remote.shared
package|package
name|org
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

begin_enum
DECL|enum|RemoteMessage
specifier|public
enum|enum
name|RemoteMessage
block|{
comment|/**      * Send command line arguments. The message content is of type {@code String[]}.      */
DECL|enumConstant|SEND_COMMAND_LINE_ARGUMENTS
name|SEND_COMMAND_LINE_ARGUMENTS
block|,
comment|/**      * As a response to {@link #PING}. The message content is an identifier of type {@code String}.      */
DECL|enumConstant|PONG
name|PONG
block|,
comment|/**      * Response signaling that the message was received successfully. No message content.      */
DECL|enumConstant|OK
name|OK
block|,
comment|/**      * Request server to identify itself. No message content.      */
DECL|enumConstant|PING
name|PING
block|}
end_enum

end_unit

