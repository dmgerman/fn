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
DECL|class|NewLineEvent
specifier|public
class|class
name|NewLineEvent
extends|extends
name|Event
block|{
DECL|field|line
specifier|private
name|int
name|line
decl_stmt|;
DECL|method|NewLineEvent (Object source)
specifier|public
name|NewLineEvent
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
DECL|method|NewLineEvent (Object source, int line)
specifier|public
name|NewLineEvent
parameter_list|(
name|Object
name|source
parameter_list|,
name|int
name|line
parameter_list|)
block|{
name|super
argument_list|(
name|source
argument_list|)
expr_stmt|;
name|setValues
argument_list|(
name|line
argument_list|)
expr_stmt|;
block|}
DECL|method|getLine ()
specifier|public
name|int
name|getLine
parameter_list|()
block|{
return|return
name|line
return|;
block|}
DECL|method|setLine (int line)
name|void
name|setLine
parameter_list|(
name|int
name|line
parameter_list|)
block|{
name|this
operator|.
name|line
operator|=
name|line
expr_stmt|;
block|}
comment|/** This should NOT be called from anyone other than ParserEventSupport! */
DECL|method|setValues (int line)
name|void
name|setValues
parameter_list|(
name|int
name|line
parameter_list|)
block|{
name|setLine
argument_list|(
name|line
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
literal|"NewLineEvent ["
operator|+
name|line
operator|+
literal|"]"
return|;
block|}
block|}
end_class

end_unit

