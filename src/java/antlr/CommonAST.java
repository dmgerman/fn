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

begin_comment
comment|/** Common AST node implementation */
end_comment

begin_class
DECL|class|CommonAST
specifier|public
class|class
name|CommonAST
extends|extends
name|BaseAST
block|{
DECL|field|ttype
name|int
name|ttype
init|=
name|Token
operator|.
name|INVALID_TYPE
decl_stmt|;
DECL|field|text
name|String
name|text
decl_stmt|;
comment|/** Get the token text for this node */
DECL|method|getText ()
specifier|public
name|String
name|getText
parameter_list|()
block|{
return|return
name|text
return|;
block|}
comment|/** Get the token type for this node */
DECL|method|getType ()
specifier|public
name|int
name|getType
parameter_list|()
block|{
return|return
name|ttype
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
block|{
name|setType
argument_list|(
name|t
argument_list|)
expr_stmt|;
name|setText
argument_list|(
name|txt
argument_list|)
expr_stmt|;
block|}
DECL|method|initialize (AST t)
specifier|public
name|void
name|initialize
parameter_list|(
name|AST
name|t
parameter_list|)
block|{
name|setText
argument_list|(
name|t
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
name|setType
argument_list|(
name|t
operator|.
name|getType
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|CommonAST ()
specifier|public
name|CommonAST
parameter_list|()
block|{     }
DECL|method|CommonAST (Token tok)
specifier|public
name|CommonAST
parameter_list|(
name|Token
name|tok
parameter_list|)
block|{
name|initialize
argument_list|(
name|tok
argument_list|)
expr_stmt|;
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
name|setText
argument_list|(
name|tok
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
name|setType
argument_list|(
name|tok
operator|.
name|getType
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/** Set the token text for this node */
DECL|method|setText (String text_)
specifier|public
name|void
name|setText
parameter_list|(
name|String
name|text_
parameter_list|)
block|{
name|text
operator|=
name|text_
expr_stmt|;
block|}
comment|/** Set the token type for this node */
DECL|method|setType (int ttype_)
specifier|public
name|void
name|setType
parameter_list|(
name|int
name|ttype_
parameter_list|)
block|{
name|ttype
operator|=
name|ttype_
expr_stmt|;
block|}
block|}
end_class

end_unit

