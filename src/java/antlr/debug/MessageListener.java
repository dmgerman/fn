begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|antlr.debug
package|package
name|antlr
operator|.
name|debug
package|;
end_package

begin_interface
DECL|interface|MessageListener
specifier|public
interface|interface
name|MessageListener
extends|extends
name|ListenerBase
block|{
DECL|method|reportError (MessageEvent e)
specifier|public
name|void
name|reportError
parameter_list|(
name|MessageEvent
name|e
parameter_list|)
function_decl|;
DECL|method|reportWarning (MessageEvent e)
specifier|public
name|void
name|reportWarning
parameter_list|(
name|MessageEvent
name|e
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

