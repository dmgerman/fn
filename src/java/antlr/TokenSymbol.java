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
DECL|class|TokenSymbol
class|class
name|TokenSymbol
extends|extends
name|GrammarSymbol
block|{
DECL|field|ttype
specifier|protected
name|int
name|ttype
decl_stmt|;
comment|/** describes what token matches in "human terms" */
DECL|field|paraphrase
specifier|protected
name|String
name|paraphrase
init|=
literal|null
decl_stmt|;
comment|/** Set to a value in the tokens {...} section */
DECL|field|ASTNodeType
specifier|protected
name|String
name|ASTNodeType
decl_stmt|;
DECL|method|TokenSymbol (String r)
specifier|public
name|TokenSymbol
parameter_list|(
name|String
name|r
parameter_list|)
block|{
name|super
argument_list|(
name|r
argument_list|)
expr_stmt|;
name|ttype
operator|=
name|Token
operator|.
name|INVALID_TYPE
expr_stmt|;
block|}
DECL|method|getASTNodeType ()
specifier|public
name|String
name|getASTNodeType
parameter_list|()
block|{
return|return
name|ASTNodeType
return|;
block|}
DECL|method|setASTNodeType (String type)
specifier|public
name|void
name|setASTNodeType
parameter_list|(
name|String
name|type
parameter_list|)
block|{
name|ASTNodeType
operator|=
name|type
expr_stmt|;
block|}
DECL|method|getParaphrase ()
specifier|public
name|String
name|getParaphrase
parameter_list|()
block|{
return|return
name|paraphrase
return|;
block|}
DECL|method|getTokenType ()
specifier|public
name|int
name|getTokenType
parameter_list|()
block|{
return|return
name|ttype
return|;
block|}
DECL|method|setParaphrase (String p)
specifier|public
name|void
name|setParaphrase
parameter_list|(
name|String
name|p
parameter_list|)
block|{
name|paraphrase
operator|=
name|p
expr_stmt|;
block|}
DECL|method|setTokenType (int t)
specifier|public
name|void
name|setTokenType
parameter_list|(
name|int
name|t
parameter_list|)
block|{
name|ttype
operator|=
name|t
expr_stmt|;
block|}
block|}
end_class

end_unit

