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
DECL|interface|ParserTokenListener
specifier|public
interface|interface
name|ParserTokenListener
extends|extends
name|ListenerBase
block|{
DECL|method|parserConsume (ParserTokenEvent e)
specifier|public
name|void
name|parserConsume
parameter_list|(
name|ParserTokenEvent
name|e
parameter_list|)
function_decl|;
DECL|method|parserLA (ParserTokenEvent e)
specifier|public
name|void
name|parserLA
parameter_list|(
name|ParserTokenEvent
name|e
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

