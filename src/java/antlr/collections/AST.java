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

begin_import
import|import
name|antlr
operator|.
name|Token
import|;
end_import

begin_comment
comment|/** Minimal AST node interface used by ANTLR AST generation  * and tree-walker.  */
end_comment

begin_interface
DECL|interface|AST
specifier|public
interface|interface
name|AST
block|{
comment|/** Add a (rightmost) child to this node */
DECL|method|addChild (AST c)
specifier|public
name|void
name|addChild
parameter_list|(
name|AST
name|c
parameter_list|)
function_decl|;
DECL|method|equals (AST t)
specifier|public
name|boolean
name|equals
parameter_list|(
name|AST
name|t
parameter_list|)
function_decl|;
DECL|method|equalsList (AST t)
specifier|public
name|boolean
name|equalsList
parameter_list|(
name|AST
name|t
parameter_list|)
function_decl|;
DECL|method|equalsListPartial (AST t)
specifier|public
name|boolean
name|equalsListPartial
parameter_list|(
name|AST
name|t
parameter_list|)
function_decl|;
DECL|method|equalsTree (AST t)
specifier|public
name|boolean
name|equalsTree
parameter_list|(
name|AST
name|t
parameter_list|)
function_decl|;
DECL|method|equalsTreePartial (AST t)
specifier|public
name|boolean
name|equalsTreePartial
parameter_list|(
name|AST
name|t
parameter_list|)
function_decl|;
DECL|method|findAll (AST tree)
specifier|public
name|ASTEnumeration
name|findAll
parameter_list|(
name|AST
name|tree
parameter_list|)
function_decl|;
DECL|method|findAllPartial (AST subtree)
specifier|public
name|ASTEnumeration
name|findAllPartial
parameter_list|(
name|AST
name|subtree
parameter_list|)
function_decl|;
comment|/** Get the first child of this node; null if no children */
DECL|method|getFirstChild ()
specifier|public
name|AST
name|getFirstChild
parameter_list|()
function_decl|;
comment|/** Get	the next sibling in line after this one */
DECL|method|getNextSibling ()
specifier|public
name|AST
name|getNextSibling
parameter_list|()
function_decl|;
comment|/** Get the token text for this node */
DECL|method|getText ()
specifier|public
name|String
name|getText
parameter_list|()
function_decl|;
comment|/** Get the token type for this node */
DECL|method|getType ()
specifier|public
name|int
name|getType
parameter_list|()
function_decl|;
comment|/** @since 2.7.3 Need for error handling */
DECL|method|getLine ()
specifier|public
name|int
name|getLine
parameter_list|()
function_decl|;
comment|/** @since 2.7.3 Need for error handling */
DECL|method|getColumn ()
specifier|public
name|int
name|getColumn
parameter_list|()
function_decl|;
comment|/** Get number of children of this node; if leaf, returns 0 */
DECL|method|getNumberOfChildren ()
specifier|public
name|int
name|getNumberOfChildren
parameter_list|()
function_decl|;
DECL|method|initialize (int t, String txt)
specifier|public
name|void
name|initialize
parameter_list|(
name|int
name|t
parameter_list|,
name|String
name|txt
parameter_list|)
function_decl|;
DECL|method|initialize (AST t)
specifier|public
name|void
name|initialize
parameter_list|(
name|AST
name|t
parameter_list|)
function_decl|;
DECL|method|initialize (Token t)
specifier|public
name|void
name|initialize
parameter_list|(
name|Token
name|t
parameter_list|)
function_decl|;
comment|/** Set the first child of a node. */
DECL|method|setFirstChild (AST c)
specifier|public
name|void
name|setFirstChild
parameter_list|(
name|AST
name|c
parameter_list|)
function_decl|;
comment|/** Set the next sibling after this one. */
DECL|method|setNextSibling (AST n)
specifier|public
name|void
name|setNextSibling
parameter_list|(
name|AST
name|n
parameter_list|)
function_decl|;
comment|/** Set the token text for this node */
DECL|method|setText (String text)
specifier|public
name|void
name|setText
parameter_list|(
name|String
name|text
parameter_list|)
function_decl|;
comment|/** Set the token type for this node */
DECL|method|setType (int ttype)
specifier|public
name|void
name|setType
parameter_list|(
name|int
name|ttype
parameter_list|)
function_decl|;
DECL|method|toString ()
specifier|public
name|String
name|toString
parameter_list|()
function_decl|;
DECL|method|toStringList ()
specifier|public
name|String
name|toStringList
parameter_list|()
function_decl|;
DECL|method|toStringTree ()
specifier|public
name|String
name|toStringTree
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

