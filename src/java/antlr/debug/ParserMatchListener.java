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
DECL|interface|ParserMatchListener
specifier|public
interface|interface
name|ParserMatchListener
extends|extends
name|ListenerBase
block|{
DECL|method|parserMatch (ParserMatchEvent e)
specifier|public
name|void
name|parserMatch
parameter_list|(
name|ParserMatchEvent
name|e
parameter_list|)
function_decl|;
DECL|method|parserMatchNot (ParserMatchEvent e)
specifier|public
name|void
name|parserMatchNot
parameter_list|(
name|ParserMatchEvent
name|e
parameter_list|)
function_decl|;
DECL|method|parserMismatch (ParserMatchEvent e)
specifier|public
name|void
name|parserMismatch
parameter_list|(
name|ParserMatchEvent
name|e
parameter_list|)
function_decl|;
DECL|method|parserMismatchNot (ParserMatchEvent e)
specifier|public
name|void
name|parserMismatchNot
parameter_list|(
name|ParserMatchEvent
name|e
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

