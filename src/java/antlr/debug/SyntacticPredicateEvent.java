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
DECL|class|SyntacticPredicateEvent
specifier|public
class|class
name|SyntacticPredicateEvent
extends|extends
name|GuessingEvent
block|{
DECL|method|SyntacticPredicateEvent (Object source)
specifier|public
name|SyntacticPredicateEvent
parameter_list|(
name|Object
name|source
parameter_list|)
block|{
name|super
argument_list|(
name|source
argument_list|)
expr_stmt|;
block|}
DECL|method|SyntacticPredicateEvent (Object source, int type)
specifier|public
name|SyntacticPredicateEvent
parameter_list|(
name|Object
name|source
parameter_list|,
name|int
name|type
parameter_list|)
block|{
name|super
argument_list|(
name|source
argument_list|,
name|type
argument_list|)
expr_stmt|;
block|}
comment|/** This should NOT be called from anyone other than ParserEventSupport! */
DECL|method|setValues (int type, int guessing)
name|void
name|setValues
parameter_list|(
name|int
name|type
parameter_list|,
name|int
name|guessing
parameter_list|)
block|{
name|super
operator|.
name|setValues
argument_list|(
name|type
argument_list|,
name|guessing
argument_list|)
expr_stmt|;
block|}
DECL|method|toString ()
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
literal|"SyntacticPredicateEvent ["
operator|+
name|getGuessing
argument_list|()
operator|+
literal|"]"
return|;
block|}
block|}
end_class

end_unit

