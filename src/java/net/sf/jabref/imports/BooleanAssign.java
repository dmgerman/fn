begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Package package net.sf.jabref.imports;  * Created on Jul 12, 2004  * Author mspiegel  *  */
end_comment

begin_package
DECL|package|net.sf.jabref.imports
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|imports
package|;
end_package

begin_comment
comment|/**  * @author mspiegel  *  */
end_comment

begin_class
DECL|class|BooleanAssign
specifier|public
class|class
name|BooleanAssign
block|{
DECL|field|value
name|boolean
name|value
decl_stmt|;
comment|/** 	 * @param b 	 */
DECL|method|BooleanAssign (boolean b)
specifier|public
name|BooleanAssign
parameter_list|(
name|boolean
name|b
parameter_list|)
block|{
name|setValue
argument_list|(
name|b
argument_list|)
expr_stmt|;
block|}
DECL|method|setValue (boolean value)
specifier|public
name|void
name|setValue
parameter_list|(
name|boolean
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
DECL|method|getValue ()
specifier|public
name|boolean
name|getValue
parameter_list|()
block|{
return|return
operator|(
name|value
operator|)
return|;
block|}
block|}
end_class

end_unit

