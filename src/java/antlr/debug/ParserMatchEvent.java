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
DECL|class|ParserMatchEvent
specifier|public
class|class
name|ParserMatchEvent
extends|extends
name|GuessingEvent
block|{
comment|// NOTE: for a mismatch on type STRING, the "text" is used as the lookahead
comment|//       value.  Normally "value" is this
DECL|field|TOKEN
specifier|public
specifier|static
name|int
name|TOKEN
init|=
literal|0
decl_stmt|;
DECL|field|BITSET
specifier|public
specifier|static
name|int
name|BITSET
init|=
literal|1
decl_stmt|;
DECL|field|CHAR
specifier|public
specifier|static
name|int
name|CHAR
init|=
literal|2
decl_stmt|;
DECL|field|CHAR_BITSET
specifier|public
specifier|static
name|int
name|CHAR_BITSET
init|=
literal|3
decl_stmt|;
DECL|field|STRING
specifier|public
specifier|static
name|int
name|STRING
init|=
literal|4
decl_stmt|;
DECL|field|CHAR_RANGE
specifier|public
specifier|static
name|int
name|CHAR_RANGE
init|=
literal|5
decl_stmt|;
DECL|field|inverse
specifier|private
name|boolean
name|inverse
decl_stmt|;
DECL|field|matched
specifier|private
name|boolean
name|matched
decl_stmt|;
DECL|field|target
specifier|private
name|Object
name|target
decl_stmt|;
DECL|field|value
specifier|private
name|int
name|value
decl_stmt|;
DECL|field|text
specifier|private
name|String
name|text
decl_stmt|;
DECL|method|ParserMatchEvent (Object source)
specifier|public
name|ParserMatchEvent
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
DECL|method|ParserMatchEvent (Object source, int type, int value, Object target, String text, int guessing, boolean inverse, boolean matched)
specifier|public
name|ParserMatchEvent
parameter_list|(
name|Object
name|source
parameter_list|,
name|int
name|type
parameter_list|,
name|int
name|value
parameter_list|,
name|Object
name|target
parameter_list|,
name|String
name|text
parameter_list|,
name|int
name|guessing
parameter_list|,
name|boolean
name|inverse
parameter_list|,
name|boolean
name|matched
parameter_list|)
block|{
name|super
argument_list|(
name|source
argument_list|)
expr_stmt|;
name|setValues
argument_list|(
name|type
argument_list|,
name|value
argument_list|,
name|target
argument_list|,
name|text
argument_list|,
name|guessing
argument_list|,
name|inverse
argument_list|,
name|matched
argument_list|)
expr_stmt|;
block|}
DECL|method|getTarget ()
specifier|public
name|Object
name|getTarget
parameter_list|()
block|{
return|return
name|target
return|;
block|}
DECL|method|getText ()
specifier|public
name|String
name|getText
parameter_list|()
block|{
return|return
name|text
return|;
block|}
DECL|method|getValue ()
specifier|public
name|int
name|getValue
parameter_list|()
block|{
return|return
name|value
return|;
block|}
DECL|method|isInverse ()
specifier|public
name|boolean
name|isInverse
parameter_list|()
block|{
return|return
name|inverse
return|;
block|}
DECL|method|isMatched ()
specifier|public
name|boolean
name|isMatched
parameter_list|()
block|{
return|return
name|matched
return|;
block|}
DECL|method|setInverse (boolean inverse)
name|void
name|setInverse
parameter_list|(
name|boolean
name|inverse
parameter_list|)
block|{
name|this
operator|.
name|inverse
operator|=
name|inverse
expr_stmt|;
block|}
DECL|method|setMatched (boolean matched)
name|void
name|setMatched
parameter_list|(
name|boolean
name|matched
parameter_list|)
block|{
name|this
operator|.
name|matched
operator|=
name|matched
expr_stmt|;
block|}
DECL|method|setTarget (Object target)
name|void
name|setTarget
parameter_list|(
name|Object
name|target
parameter_list|)
block|{
name|this
operator|.
name|target
operator|=
name|target
expr_stmt|;
block|}
DECL|method|setText (String text)
name|void
name|setText
parameter_list|(
name|String
name|text
parameter_list|)
block|{
name|this
operator|.
name|text
operator|=
name|text
expr_stmt|;
block|}
DECL|method|setValue (int value)
name|void
name|setValue
parameter_list|(
name|int
name|value
parameter_list|)
block|{
name|this
operator|.
name|value
operator|=
name|value
expr_stmt|;
block|}
comment|/** This should NOT be called from anyone other than ParserEventSupport! */
DECL|method|setValues (int type, int value, Object target, String text, int guessing, boolean inverse, boolean matched)
name|void
name|setValues
parameter_list|(
name|int
name|type
parameter_list|,
name|int
name|value
parameter_list|,
name|Object
name|target
parameter_list|,
name|String
name|text
parameter_list|,
name|int
name|guessing
parameter_list|,
name|boolean
name|inverse
parameter_list|,
name|boolean
name|matched
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
name|setValue
argument_list|(
name|value
argument_list|)
expr_stmt|;
name|setTarget
argument_list|(
name|target
argument_list|)
expr_stmt|;
name|setInverse
argument_list|(
name|inverse
argument_list|)
expr_stmt|;
name|setMatched
argument_list|(
name|matched
argument_list|)
expr_stmt|;
name|setText
argument_list|(
name|text
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
literal|"ParserMatchEvent ["
operator|+
operator|(
name|isMatched
argument_list|()
condition|?
literal|"ok,"
else|:
literal|"bad,"
operator|)
operator|+
operator|(
name|isInverse
argument_list|()
condition|?
literal|"NOT "
else|:
literal|""
operator|)
operator|+
operator|(
name|getType
argument_list|()
operator|==
name|TOKEN
condition|?
literal|"token,"
else|:
literal|"bitset,"
operator|)
operator|+
name|getValue
argument_list|()
operator|+
literal|","
operator|+
name|getTarget
argument_list|()
operator|+
literal|","
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

