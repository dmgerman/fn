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
DECL|interface|NewLineListener
specifier|public
interface|interface
name|NewLineListener
extends|extends
name|ListenerBase
block|{
DECL|method|hitNewLine (NewLineEvent e)
specifier|public
name|void
name|hitNewLine
parameter_list|(
name|NewLineEvent
name|e
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

