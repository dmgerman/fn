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

begin_class
DECL|class|BlockWithImpliedExitPath
specifier|abstract
class|class
name|BlockWithImpliedExitPath
extends|extends
name|AlternativeBlock
block|{
DECL|field|exitLookaheadDepth
specifier|protected
name|int
name|exitLookaheadDepth
decl_stmt|;
comment|// lookahead needed to handle optional path
comment|/** lookahead to bypass block; set 	 * by deterministic().  1..k of Lookahead 	 */
DECL|field|exitCache
specifier|protected
name|Lookahead
index|[]
name|exitCache
init|=
operator|new
name|Lookahead
index|[
name|grammar
operator|.
name|maxk
operator|+
literal|1
index|]
decl_stmt|;
DECL|method|BlockWithImpliedExitPath (Grammar g)
specifier|public
name|BlockWithImpliedExitPath
parameter_list|(
name|Grammar
name|g
parameter_list|)
block|{
name|super
argument_list|(
name|g
argument_list|)
expr_stmt|;
block|}
DECL|method|BlockWithImpliedExitPath (Grammar g, int line)
specifier|public
name|BlockWithImpliedExitPath
parameter_list|(
name|Grammar
name|g
parameter_list|,
name|int
name|line
parameter_list|)
block|{
name|super
argument_list|(
name|g
argument_list|,
name|line
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

