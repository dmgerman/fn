begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|antlr
package|package
name|antlr
package|;
end_package

begin_comment
comment|/* ANTLR Translator Generator  * Project led by Terence Parr at http://www.jGuru.com  * Software rights: http://www.antlr.org/license.html  *  * $Id$  */
end_comment

begin_class
DECL|class|CommonHiddenStreamToken
specifier|public
class|class
name|CommonHiddenStreamToken
extends|extends
name|CommonToken
block|{
DECL|field|hiddenBefore
specifier|protected
name|CommonHiddenStreamToken
name|hiddenBefore
decl_stmt|;
DECL|field|hiddenAfter
specifier|protected
name|CommonHiddenStreamToken
name|hiddenAfter
decl_stmt|;
DECL|method|CommonHiddenStreamToken ()
specifier|public
name|CommonHiddenStreamToken
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
DECL|method|CommonHiddenStreamToken (int t, String txt)
specifier|public
name|CommonHiddenStreamToken
parameter_list|(
name|int
name|t
parameter_list|,
name|String
name|txt
parameter_list|)
block|{
name|super
argument_list|(
name|t
argument_list|,
name|txt
argument_list|)
expr_stmt|;
block|}
DECL|method|CommonHiddenStreamToken (String s)
specifier|public
name|CommonHiddenStreamToken
parameter_list|(
name|String
name|s
parameter_list|)
block|{
name|super
argument_list|(
name|s
argument_list|)
expr_stmt|;
block|}
DECL|method|getHiddenAfter ()
specifier|public
name|CommonHiddenStreamToken
name|getHiddenAfter
parameter_list|()
block|{
return|return
name|hiddenAfter
return|;
block|}
DECL|method|getHiddenBefore ()
specifier|public
name|CommonHiddenStreamToken
name|getHiddenBefore
parameter_list|()
block|{
return|return
name|hiddenBefore
return|;
block|}
DECL|method|setHiddenAfter (CommonHiddenStreamToken t)
specifier|protected
name|void
name|setHiddenAfter
parameter_list|(
name|CommonHiddenStreamToken
name|t
parameter_list|)
block|{
name|hiddenAfter
operator|=
name|t
expr_stmt|;
block|}
DECL|method|setHiddenBefore (CommonHiddenStreamToken t)
specifier|protected
name|void
name|setHiddenBefore
parameter_list|(
name|CommonHiddenStreamToken
name|t
parameter_list|)
block|{
name|hiddenBefore
operator|=
name|t
expr_stmt|;
block|}
block|}
end_class

end_unit

