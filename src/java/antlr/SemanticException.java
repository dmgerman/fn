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
DECL|class|SemanticException
specifier|public
class|class
name|SemanticException
extends|extends
name|RecognitionException
block|{
DECL|method|SemanticException (String s)
specifier|public
name|SemanticException
parameter_list|(
name|String
name|s
parameter_list|)
block|{
name|super
argument_list|(
name|s
argument_list|)
expr_stmt|;
block|}
DECL|method|SemanticException (String s, String fileName, int line)
specifier|public
name|SemanticException
parameter_list|(
name|String
name|s
parameter_list|,
name|String
name|fileName
parameter_list|,
name|int
name|line
parameter_list|)
block|{
name|super
argument_list|(
name|s
argument_list|,
name|fileName
argument_list|,
name|line
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

