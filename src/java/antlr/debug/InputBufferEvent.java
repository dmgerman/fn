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
DECL|class|InputBufferEvent
specifier|public
class|class
name|InputBufferEvent
extends|extends
name|Event
block|{
DECL|field|c
name|char
name|c
decl_stmt|;
DECL|field|lookaheadAmount
name|int
name|lookaheadAmount
decl_stmt|;
comment|// amount of lookahead
DECL|field|CONSUME
specifier|public
specifier|static
specifier|final
name|int
name|CONSUME
init|=
literal|0
decl_stmt|;
DECL|field|LA
specifier|public
specifier|static
specifier|final
name|int
name|LA
init|=
literal|1
decl_stmt|;
DECL|field|MARK
specifier|public
specifier|static
specifier|final
name|int
name|MARK
init|=
literal|2
decl_stmt|;
DECL|field|REWIND
specifier|public
specifier|static
specifier|final
name|int
name|REWIND
init|=
literal|3
decl_stmt|;
comment|/**  * CharBufferEvent constructor comment.  * @param source java.lang.Object  */
DECL|method|InputBufferEvent (Object source)
specifier|public
name|InputBufferEvent
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
comment|/**  * CharBufferEvent constructor comment.  * @param source java.lang.Object  */
DECL|method|InputBufferEvent (Object source, int type, char c, int lookaheadAmount)
specifier|public
name|InputBufferEvent
parameter_list|(
name|Object
name|source
parameter_list|,
name|int
name|type
parameter_list|,
name|char
name|c
parameter_list|,
name|int
name|lookaheadAmount
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
name|c
argument_list|,
name|lookaheadAmount
argument_list|)
expr_stmt|;
block|}
DECL|method|getChar ()
specifier|public
name|char
name|getChar
parameter_list|()
block|{
return|return
name|c
return|;
block|}
DECL|method|getLookaheadAmount ()
specifier|public
name|int
name|getLookaheadAmount
parameter_list|()
block|{
return|return
name|lookaheadAmount
return|;
block|}
DECL|method|setChar (char c)
name|void
name|setChar
parameter_list|(
name|char
name|c
parameter_list|)
block|{
name|this
operator|.
name|c
operator|=
name|c
expr_stmt|;
block|}
DECL|method|setLookaheadAmount (int la)
name|void
name|setLookaheadAmount
parameter_list|(
name|int
name|la
parameter_list|)
block|{
name|this
operator|.
name|lookaheadAmount
operator|=
name|la
expr_stmt|;
block|}
comment|/** This should NOT be called from anyone other than ParserEventSupport! */
DECL|method|setValues (int type, char c, int la)
name|void
name|setValues
parameter_list|(
name|int
name|type
parameter_list|,
name|char
name|c
parameter_list|,
name|int
name|la
parameter_list|)
block|{
name|super
operator|.
name|setValues
argument_list|(
name|type
argument_list|)
expr_stmt|;
name|setChar
argument_list|(
name|c
argument_list|)
expr_stmt|;
name|setLookaheadAmount
argument_list|(
name|la
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
literal|"CharBufferEvent ["
operator|+
operator|(
name|getType
argument_list|()
operator|==
name|CONSUME
condition|?
literal|"CONSUME, "
else|:
literal|"LA, "
operator|)
operator|+
name|getChar
argument_list|()
operator|+
literal|","
operator|+
name|getLookaheadAmount
argument_list|()
operator|+
literal|"]"
return|;
block|}
block|}
end_class

end_unit

