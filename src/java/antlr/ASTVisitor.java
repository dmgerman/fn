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

begin_import
import|import
name|antlr
operator|.
name|collections
operator|.
name|AST
import|;
end_import

begin_interface
DECL|interface|ASTVisitor
specifier|public
interface|interface
name|ASTVisitor
block|{
DECL|method|visit (AST node)
specifier|public
name|void
name|visit
parameter_list|(
name|AST
name|node
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

