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

begin_import
import|import
name|antlr
operator|.
name|collections
operator|.
name|ASTEnumeration
import|;
end_import

begin_import
import|import
name|antlr
operator|.
name|Token
import|;
end_import

begin_comment
comment|/** There is only one instance of this class **/
end_comment

begin_class
DECL|class|ASTNULLType
specifier|public
class|class
name|ASTNULLType
implements|implements
name|AST
block|{
DECL|method|addChild (AST c)
specifier|public
name|void
name|addChild
parameter_list|(
name|AST
name|c
parameter_list|)
block|{     }
DECL|method|equals (AST t)
specifier|public
name|boolean
name|equals
parameter_list|(
name|AST
name|t
parameter_list|)
block|{
return|return
literal|false
return|;
block|}
DECL|method|equalsList (AST t)
specifier|public
name|boolean
name|equalsList
parameter_list|(
name|AST
name|t
parameter_list|)
block|{
return|return
literal|false
return|;
block|}
DECL|method|equalsListPartial (AST t)
specifier|public
name|boolean
name|equalsListPartial
parameter_list|(
name|AST
name|t
parameter_list|)
block|{
return|return
literal|false
return|;
block|}
DECL|method|equalsTree (AST t)
specifier|public
name|boolean
name|equalsTree
parameter_list|(
name|AST
name|t
parameter_list|)
block|{
return|return
literal|false
return|;
block|}
DECL|method|equalsTreePartial (AST t)
specifier|public
name|boolean
name|equalsTreePartial
parameter_list|(
name|AST
name|t
parameter_list|)
block|{
return|return
literal|false
return|;
block|}
DECL|method|findAll (AST tree)
specifier|public
name|ASTEnumeration
name|findAll
parameter_list|(
name|AST
name|tree
parameter_list|)
block|{
return|return
literal|null
return|;
block|}
DECL|method|findAllPartial (AST subtree)
specifier|public
name|ASTEnumeration
name|findAllPartial
parameter_list|(
name|AST
name|subtree
parameter_list|)
block|{
return|return
literal|null
return|;
block|}
DECL|method|getFirstChild ()
specifier|public
name|AST
name|getFirstChild
parameter_list|()
block|{
return|return
name|this
return|;
block|}
DECL|method|getNextSibling ()
specifier|public
name|AST
name|getNextSibling
parameter_list|()
block|{
return|return
name|this
return|;
block|}
DECL|method|getText ()
specifier|public
name|String
name|getText
parameter_list|()
block|{
return|return
literal|"<ASTNULL>"
return|;
block|}
DECL|method|getType ()
specifier|public
name|int
name|getType
parameter_list|()
block|{
return|return
name|Token
operator|.
name|NULL_TREE_LOOKAHEAD
return|;
block|}
DECL|method|getLine ()
specifier|public
name|int
name|getLine
parameter_list|()
block|{
return|return
literal|0
return|;
block|}
DECL|method|getColumn ()
specifier|public
name|int
name|getColumn
parameter_list|()
block|{
return|return
literal|0
return|;
block|}
DECL|method|getNumberOfChildren ()
specifier|public
name|int
name|getNumberOfChildren
parameter_list|()
block|{
return|return
literal|0
return|;
block|}
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
block|{     }
DECL|method|initialize (AST t)
specifier|public
name|void
name|initialize
parameter_list|(
name|AST
name|t
parameter_list|)
block|{     }
DECL|method|initialize (Token t)
specifier|public
name|void
name|initialize
parameter_list|(
name|Token
name|t
parameter_list|)
block|{     }
DECL|method|setFirstChild (AST c)
specifier|public
name|void
name|setFirstChild
parameter_list|(
name|AST
name|c
parameter_list|)
block|{     }
DECL|method|setNextSibling (AST n)
specifier|public
name|void
name|setNextSibling
parameter_list|(
name|AST
name|n
parameter_list|)
block|{     }
DECL|method|setText (String text)
specifier|public
name|void
name|setText
parameter_list|(
name|String
name|text
parameter_list|)
block|{     }
DECL|method|setType (int ttype)
specifier|public
name|void
name|setType
parameter_list|(
name|int
name|ttype
parameter_list|)
block|{     }
DECL|method|toString ()
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|getText
argument_list|()
return|;
block|}
DECL|method|toStringList ()
specifier|public
name|String
name|toStringList
parameter_list|()
block|{
return|return
name|getText
argument_list|()
return|;
block|}
DECL|method|toStringTree ()
specifier|public
name|String
name|toStringTree
parameter_list|()
block|{
return|return
name|getText
argument_list|()
return|;
block|}
block|}
end_class

end_unit

