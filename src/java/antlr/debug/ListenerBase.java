begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|antlr.debug
package|package
name|antlr
operator|.
name|debug
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|EventListener
import|;
end_import

begin_interface
DECL|interface|ListenerBase
specifier|public
interface|interface
name|ListenerBase
extends|extends
name|EventListener
block|{
DECL|method|doneParsing (TraceEvent e)
specifier|public
name|void
name|doneParsing
parameter_list|(
name|TraceEvent
name|e
parameter_list|)
function_decl|;
DECL|method|refresh ()
specifier|public
name|void
name|refresh
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

