begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|antlr
package|package
name|antlr
package|;
end_package

begin_comment
comment|/* ANTLR Translator Generator  * Project led by Terence Parr at http://www.jGuru.com  * Software rights: http://www.antlr.org/RIGHTS.html  *  * $Id$  */
end_comment

begin_comment
comment|/** A CommonAST whose initialization copies hidden token  *  information from the Token used to create a node.  */
end_comment

begin_class
DECL|class|CommonASTWithHiddenTokens
specifier|public
class|class
name|CommonASTWithHiddenTokens
extends|extends
name|CommonAST
block|{
DECL|field|hiddenBefore
DECL|field|hiddenAfter
specifier|protected
name|CommonHiddenStreamToken
name|hiddenBefore
decl_stmt|,
name|hiddenAfter
decl_stmt|;
comment|// references to hidden tokens
DECL|method|CommonASTWithHiddenTokens ()
specifier|public
name|CommonASTWithHiddenTokens
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
DECL|method|CommonASTWithHiddenTokens (Token tok)
specifier|public
name|CommonASTWithHiddenTokens
parameter_list|(
name|Token
name|tok
parameter_list|)
block|{
name|super
argument_list|(
name|tok
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
DECL|method|initialize (Token tok)
specifier|public
name|void
name|initialize
parameter_list|(
name|Token
name|tok
parameter_list|)
block|{
name|CommonHiddenStreamToken
name|t
init|=
operator|(
name|CommonHiddenStreamToken
operator|)
name|tok
decl_stmt|;
name|super
operator|.
name|initialize
argument_list|(
name|t
argument_list|)
expr_stmt|;
name|hiddenBefore
operator|=
name|t
operator|.
name|getHiddenBefore
argument_list|()
expr_stmt|;
name|hiddenAfter
operator|=
name|t
operator|.
name|getHiddenAfter
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

