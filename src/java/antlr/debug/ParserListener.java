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
DECL|interface|ParserListener
specifier|public
interface|interface
name|ParserListener
extends|extends
name|SemanticPredicateListener
extends|,
name|ParserMatchListener
extends|,
name|MessageListener
extends|,
name|ParserTokenListener
extends|,
name|TraceListener
extends|,
name|SyntacticPredicateListener
block|{ }
end_interface

end_unit

