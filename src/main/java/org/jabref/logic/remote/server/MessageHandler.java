begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.remote.server
package|package
name|org
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

begin_interface
annotation|@
name|FunctionalInterface
DECL|interface|MessageHandler
specifier|public
interface|interface
name|MessageHandler
block|{
DECL|method|handleCommandLineArguments (String[] message)
name|void
name|handleCommandLineArguments
parameter_list|(
name|String
index|[]
name|message
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

