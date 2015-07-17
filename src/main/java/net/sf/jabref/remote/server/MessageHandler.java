begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.remote.server
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|remote
operator|.
name|server
package|;
end_package

begin_interface
DECL|interface|MessageHandler
specifier|public
interface|interface
name|MessageHandler
block|{
DECL|method|handleMessage (String message)
name|void
name|handleMessage
parameter_list|(
name|String
name|message
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

