begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|antlr
package|package
name|antlr
package|;
end_package

begin_comment
comment|/* ANTLR Translator Generator  * Project led by Terence Parr at http://www.jGuru.com  * Software rights: http://www.antlr.org/license.html  */
end_comment

begin_comment
comment|/** This token knows what index 0..n-1 it is from beginning of stream.  *  Designed to work with TokenStreamRewriteEngine.java  */
end_comment

begin_class
DECL|class|TokenWithIndex
specifier|public
class|class
name|TokenWithIndex
extends|extends
name|CommonToken
block|{
comment|/** Index into token array indicating position in input stream */
DECL|field|index
name|int
name|index
decl_stmt|;
DECL|method|TokenWithIndex ()
specifier|public
name|TokenWithIndex
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
DECL|method|TokenWithIndex (int i, String t)
specifier|public
name|TokenWithIndex
parameter_list|(
name|int
name|i
parameter_list|,
name|String
name|t
parameter_list|)
block|{
name|super
argument_list|(
name|i
argument_list|,
name|t
argument_list|)
expr_stmt|;
block|}
DECL|method|setIndex (int i)
specifier|public
name|void
name|setIndex
parameter_list|(
name|int
name|i
parameter_list|)
block|{
name|index
operator|=
name|i
expr_stmt|;
block|}
DECL|method|getIndex ()
specifier|public
name|int
name|getIndex
parameter_list|()
block|{
return|return
name|index
return|;
block|}
DECL|method|toString ()
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
literal|"["
operator|+
name|index
operator|+
literal|":\""
operator|+
name|getText
argument_list|()
operator|+
literal|"\",<"
operator|+
name|getType
argument_list|()
operator|+
literal|">,line="
operator|+
name|line
operator|+
literal|",col="
operator|+
name|col
operator|+
literal|"]\n"
return|;
block|}
block|}
end_class

end_unit

