begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|antlr.collections.impl
package|package
name|antlr
operator|.
name|collections
operator|.
name|impl
package|;
end_package

begin_comment
comment|/* ANTLR Translator Generator  * Project led by Terence Parr at http://www.jGuru.com  * Software rights: http://www.antlr.org/license.html  *  * $Id$  */
end_comment

begin_class
DECL|class|IntRange
specifier|public
class|class
name|IntRange
block|{
DECL|field|begin
DECL|field|end
name|int
name|begin
decl_stmt|,
name|end
decl_stmt|;
DECL|method|IntRange (int begin, int end)
specifier|public
name|IntRange
parameter_list|(
name|int
name|begin
parameter_list|,
name|int
name|end
parameter_list|)
block|{
name|this
operator|.
name|begin
operator|=
name|begin
expr_stmt|;
name|this
operator|.
name|end
operator|=
name|end
expr_stmt|;
block|}
DECL|method|toString ()
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|begin
operator|+
literal|".."
operator|+
name|end
return|;
block|}
block|}
end_class

end_unit

