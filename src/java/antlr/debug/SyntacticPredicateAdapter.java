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
DECL|class|SyntacticPredicateAdapter
specifier|public
class|class
name|SyntacticPredicateAdapter
implements|implements
name|SyntacticPredicateListener
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
DECL|method|refresh ()
specifier|public
name|void
name|refresh
parameter_list|()
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

