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
name|*
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
DECL|class|ParseTree
specifier|public
specifier|abstract
class|class
name|ParseTree
extends|extends
name|BaseAST
block|{
comment|/** Walk parse tree and return requested number of derivation steps. 	 *  If steps<= 0, return node text.  If steps == 1, return derivation 	 *  string at step. 	 */
DECL|method|getLeftmostDerivationStep (int step)
specifier|public
name|String
name|getLeftmostDerivationStep
parameter_list|(
name|int
name|step
parameter_list|)
block|{
if|if
condition|(
name|step
operator|<=
literal|0
condition|)
block|{
return|return
name|toString
argument_list|()
return|;
block|}
name|StringBuffer
name|buf
init|=
operator|new
name|StringBuffer
argument_list|(
literal|2000
argument_list|)
decl_stmt|;
name|getLeftmostDerivation
argument_list|(
name|buf
argument_list|,
name|step
argument_list|)
expr_stmt|;
return|return
name|buf
operator|.
name|toString
argument_list|()
return|;
block|}
DECL|method|getLeftmostDerivation (int maxSteps)
specifier|public
name|String
name|getLeftmostDerivation
parameter_list|(
name|int
name|maxSteps
parameter_list|)
block|{
name|StringBuffer
name|buf
init|=
operator|new
name|StringBuffer
argument_list|(
literal|2000
argument_list|)
decl_stmt|;
name|buf
operator|.
name|append
argument_list|(
literal|"    "
operator|+
name|this
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|buf
operator|.
name|append
argument_list|(
literal|"\n"
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|d
init|=
literal|1
init|;
name|d
operator|<
name|maxSteps
condition|;
name|d
operator|++
control|)
block|{
name|buf
operator|.
name|append
argument_list|(
literal|" =>"
argument_list|)
expr_stmt|;
name|buf
operator|.
name|append
argument_list|(
name|getLeftmostDerivationStep
argument_list|(
name|d
argument_list|)
argument_list|)
expr_stmt|;
name|buf
operator|.
name|append
argument_list|(
literal|"\n"
argument_list|)
expr_stmt|;
block|}
return|return
name|buf
operator|.
name|toString
argument_list|()
return|;
block|}
comment|/** Get derivation and return how many you did (less than requested for 	 *  subtree roots. 	 */
DECL|method|getLeftmostDerivation (StringBuffer buf, int step)
specifier|protected
specifier|abstract
name|int
name|getLeftmostDerivation
parameter_list|(
name|StringBuffer
name|buf
parameter_list|,
name|int
name|step
parameter_list|)
function_decl|;
comment|// just satisfy BaseAST interface; unused as we manually create nodes
DECL|method|initialize (int i, String s)
specifier|public
name|void
name|initialize
parameter_list|(
name|int
name|i
parameter_list|,
name|String
name|s
parameter_list|)
block|{ 	}
DECL|method|initialize (AST ast)
specifier|public
name|void
name|initialize
parameter_list|(
name|AST
name|ast
parameter_list|)
block|{ 	}
DECL|method|initialize (Token token)
specifier|public
name|void
name|initialize
parameter_list|(
name|Token
name|token
parameter_list|)
block|{ 	}
block|}
end_class

end_unit

