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
DECL|class|NoViableAltException
specifier|public
class|class
name|NoViableAltException
extends|extends
name|RecognitionException
block|{
DECL|field|token
specifier|public
name|Token
name|token
decl_stmt|;
DECL|field|node
specifier|public
name|AST
name|node
decl_stmt|;
comment|// handles parsing and treeparsing
DECL|method|NoViableAltException (AST t)
specifier|public
name|NoViableAltException
parameter_list|(
name|AST
name|t
parameter_list|)
block|{
name|super
argument_list|(
literal|"NoViableAlt"
argument_list|)
expr_stmt|;
name|node
operator|=
name|t
expr_stmt|;
name|fileName
operator|=
literal|"<AST>"
expr_stmt|;
block|}
DECL|method|NoViableAltException (Token t, String fileName)
specifier|public
name|NoViableAltException
parameter_list|(
name|Token
name|t
parameter_list|,
name|String
name|fileName
parameter_list|)
block|{
name|super
argument_list|(
literal|"NoViableAlt"
argument_list|)
expr_stmt|;
name|token
operator|=
name|t
expr_stmt|;
name|line
operator|=
name|t
operator|.
name|getLine
argument_list|()
expr_stmt|;
name|column
operator|=
name|t
operator|.
name|getColumn
argument_list|()
expr_stmt|;
name|this
operator|.
name|fileName
operator|=
name|fileName
expr_stmt|;
block|}
comment|/** 	 * @deprecated As of ANTLR 2.7.0 	 */
DECL|method|getErrorMessage ()
specifier|public
name|String
name|getErrorMessage
parameter_list|()
block|{
return|return
name|getMessage
argument_list|()
return|;
block|}
comment|/** 	 * Returns a clean error message (no line number/column information) 	 */
DECL|method|getMessage ()
specifier|public
name|String
name|getMessage
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
literal|"unexpected token: "
operator|+
name|token
operator|.
name|getText
argument_list|()
return|;
block|}
comment|// must a tree parser error if token==null
if|if
condition|(
name|node
operator|==
name|TreeParser
operator|.
name|ASTNULL
condition|)
block|{
return|return
literal|"unexpected end of subtree"
return|;
block|}
return|return
literal|"unexpected AST node: "
operator|+
name|node
operator|.
name|toString
argument_list|()
return|;
block|}
comment|/**      * Returns a string representation of this exception.      */
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
comment|// AST or Token?
return|return
name|FileLineFormatter
operator|.
name|getFormatter
argument_list|()
operator|.
name|getFormatString
argument_list|(
name|fileName
argument_list|,
name|line
argument_list|)
operator|+
name|getMessage
argument_list|()
return|;
block|}
return|return
name|getMessage
argument_list|()
return|;
block|}
block|}
end_class

end_unit

