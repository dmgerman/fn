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

begin_import
import|import
name|antlr
operator|.
name|collections
operator|.
name|AST
import|;
end_import

begin_comment
comment|/** ASTArray is a class that allows ANTLR to  * generate code that can create and initialize an array  * in one expression, like:  *    (new ASTArray(3)).add(x).add(y).add(z)  */
end_comment

begin_class
DECL|class|ASTArray
specifier|public
class|class
name|ASTArray
block|{
DECL|field|size
specifier|public
name|int
name|size
init|=
literal|0
decl_stmt|;
DECL|field|array
specifier|public
name|AST
index|[]
name|array
decl_stmt|;
DECL|method|ASTArray (int capacity)
specifier|public
name|ASTArray
parameter_list|(
name|int
name|capacity
parameter_list|)
block|{
name|array
operator|=
operator|new
name|AST
index|[
name|capacity
index|]
expr_stmt|;
block|}
DECL|method|add (AST node)
specifier|public
name|ASTArray
name|add
parameter_list|(
name|AST
name|node
parameter_list|)
block|{
name|array
index|[
name|size
operator|++
index|]
operator|=
name|node
expr_stmt|;
return|return
name|this
return|;
block|}
block|}
end_class

end_unit

