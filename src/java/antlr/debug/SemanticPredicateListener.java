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
DECL|interface|SemanticPredicateListener
specifier|public
interface|interface
name|SemanticPredicateListener
extends|extends
name|ListenerBase
block|{
DECL|method|semanticPredicateEvaluated (SemanticPredicateEvent e)
specifier|public
name|void
name|semanticPredicateEvaluated
parameter_list|(
name|SemanticPredicateEvent
name|e
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

