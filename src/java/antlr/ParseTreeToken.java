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

begin_import
import|import
name|antlr
operator|.
name|Token
import|;
end_import

begin_import
import|import
name|antlr
operator|.
name|collections
operator|.
name|AST
import|;
end_import

begin_class
DECL|class|ParseTreeToken
specifier|public
class|class
name|ParseTreeToken
extends|extends
name|ParseTree
block|{
DECL|field|token
specifier|protected
name|Token
name|token
decl_stmt|;
DECL|method|ParseTreeToken (Token token)
specifier|public
name|ParseTreeToken
parameter_list|(
name|Token
name|token
parameter_list|)
block|{
name|this
operator|.
name|token
operator|=
name|token
expr_stmt|;
block|}
DECL|method|getLeftmostDerivation (StringBuffer buf, int step)
specifier|protected
name|int
name|getLeftmostDerivation
parameter_list|(
name|StringBuffer
name|buf
parameter_list|,
name|int
name|step
parameter_list|)
block|{
name|buf
operator|.
name|append
argument_list|(
literal|' '
argument_list|)
expr_stmt|;
name|buf
operator|.
name|append
argument_list|(
name|toString
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|step
return|;
comment|// did on replacements
block|}
DECL|method|toString ()
specifier|public
name|String
name|toString
parameter_list|()
block|{
if|if
condition|(
name|token
operator|!=
literal|null
condition|)
block|{
return|return
name|token
operator|.
name|getText
argument_list|()
return|;
block|}
return|return
literal|"<missing token>"
return|;
block|}
block|}
end_class

end_unit

