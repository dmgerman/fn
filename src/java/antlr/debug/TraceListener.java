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
DECL|interface|TraceListener
specifier|public
interface|interface
name|TraceListener
extends|extends
name|ListenerBase
block|{
DECL|method|enterRule (TraceEvent e)
specifier|public
name|void
name|enterRule
parameter_list|(
name|TraceEvent
name|e
parameter_list|)
function_decl|;
DECL|method|exitRule (TraceEvent e)
specifier|public
name|void
name|exitRule
parameter_list|(
name|TraceEvent
name|e
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

