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
DECL|class|ParserAdapter
specifier|public
class|class
name|ParserAdapter
implements|implements
name|ParserListener
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
DECL|method|enterRule (TraceEvent e)
specifier|public
name|void
name|enterRule
parameter_list|(
name|TraceEvent
name|e
parameter_list|)
block|{}
DECL|method|exitRule (TraceEvent e)
specifier|public
name|void
name|exitRule
parameter_list|(
name|TraceEvent
name|e
parameter_list|)
block|{}
DECL|method|parserConsume (ParserTokenEvent e)
specifier|public
name|void
name|parserConsume
parameter_list|(
name|ParserTokenEvent
name|e
parameter_list|)
block|{}
DECL|method|parserLA (ParserTokenEvent e)
specifier|public
name|void
name|parserLA
parameter_list|(
name|ParserTokenEvent
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
DECL|method|reportError (MessageEvent e)
specifier|public
name|void
name|reportError
parameter_list|(
name|MessageEvent
name|e
parameter_list|)
block|{}
DECL|method|reportWarning (MessageEvent e)
specifier|public
name|void
name|reportWarning
parameter_list|(
name|MessageEvent
name|e
parameter_list|)
block|{}
DECL|method|semanticPredicateEvaluated (SemanticPredicateEvent e)
specifier|public
name|void
name|semanticPredicateEvaluated
parameter_list|(
name|SemanticPredicateEvent
name|e
parameter_list|)
block|{}
DECL|method|syntacticPredicateFailed (SyntacticPredicateEvent e)
specifier|public
name|void
name|syntacticPredicateFailed
parameter_list|(
name|SyntacticPredicateEvent
name|e
parameter_list|)
block|{}
DECL|method|syntacticPredicateStarted (SyntacticPredicateEvent e)
specifier|public
name|void
name|syntacticPredicateStarted
parameter_list|(
name|SyntacticPredicateEvent
name|e
parameter_list|)
block|{}
DECL|method|syntacticPredicateSucceeded (SyntacticPredicateEvent e)
specifier|public
name|void
name|syntacticPredicateSucceeded
parameter_list|(
name|SyntacticPredicateEvent
name|e
parameter_list|)
block|{}
block|}
end_class

end_unit

