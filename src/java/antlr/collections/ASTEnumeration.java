begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|antlr.collections
package|package
name|antlr
operator|.
name|collections
package|;
end_package

begin_comment
comment|/* ANTLR Translator Generator  * Project led by Terence Parr at http://www.jGuru.com  * Software rights: http://www.antlr.org/license.html  *  * $Id$  */
end_comment

begin_interface
DECL|interface|ASTEnumeration
specifier|public
interface|interface
name|ASTEnumeration
block|{
DECL|method|hasMoreNodes ()
specifier|public
name|boolean
name|hasMoreNodes
parameter_list|()
function_decl|;
DECL|method|nextNode ()
specifier|public
name|AST
name|nextNode
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

