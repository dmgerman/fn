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
DECL|interface|SyntacticPredicateListener
specifier|public
interface|interface
name|SyntacticPredicateListener
extends|extends
name|ListenerBase
block|{
DECL|method|syntacticPredicateFailed (SyntacticPredicateEvent e)
specifier|public
name|void
name|syntacticPredicateFailed
parameter_list|(
name|SyntacticPredicateEvent
name|e
parameter_list|)
function_decl|;
DECL|method|syntacticPredicateStarted (SyntacticPredicateEvent e)
specifier|public
name|void
name|syntacticPredicateStarted
parameter_list|(
name|SyntacticPredicateEvent
name|e
parameter_list|)
function_decl|;
DECL|method|syntacticPredicateSucceeded (SyntacticPredicateEvent e)
specifier|public
name|void
name|syntacticPredicateSucceeded
parameter_list|(
name|SyntacticPredicateEvent
name|e
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

