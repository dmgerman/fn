begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|antlr.debug
package|package
name|antlr
operator|.
name|debug
package|;
end_package

begin_class
DECL|class|ParserMatchAdapter
specifier|public
class|class
name|ParserMatchAdapter
implements|implements
name|ParserMatchListener
block|{
DECL|method|doneParsing (TraceEvent e)
specifier|public
name|void
name|doneParsing
parameter_list|(
name|TraceEvent
name|e
parameter_list|)
block|{}
DECL|method|parserMatch (ParserMatchEvent e)
specifier|public
name|void
name|parserMatch
parameter_list|(
name|ParserMatchEvent
name|e
parameter_list|)
block|{}
DECL|method|parserMatchNot (ParserMatchEvent e)
specifier|public
name|void
name|parserMatchNot
parameter_list|(
name|ParserMatchEvent
name|e
parameter_list|)
block|{}
DECL|method|parserMismatch (ParserMatchEvent e)
specifier|public
name|void
name|parserMismatch
parameter_list|(
name|ParserMatchEvent
name|e
parameter_list|)
block|{}
DECL|method|parserMismatchNot (ParserMatchEvent e)
specifier|public
name|void
name|parserMismatchNot
parameter_list|(
name|ParserMatchEvent
name|e
parameter_list|)
block|{}
DECL|method|refresh ()
specifier|public
name|void
name|refresh
parameter_list|()
block|{}
block|}
end_class

end_unit

