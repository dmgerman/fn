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
DECL|class|ExceptionHandler
class|class
name|ExceptionHandler
block|{
comment|// Type of the ANTLR exception class to catch and the variable decl
DECL|field|exceptionTypeAndName
specifier|protected
name|Token
name|exceptionTypeAndName
decl_stmt|;
comment|// The action to be executed when the exception is caught
DECL|field|action
specifier|protected
name|Token
name|action
decl_stmt|;
DECL|method|ExceptionHandler (Token exceptionTypeAndName_, Token action_)
specifier|public
name|ExceptionHandler
parameter_list|(
name|Token
name|exceptionTypeAndName_
parameter_list|,
name|Token
name|action_
parameter_list|)
block|{
name|exceptionTypeAndName
operator|=
name|exceptionTypeAndName_
expr_stmt|;
name|action
operator|=
name|action_
expr_stmt|;
block|}
block|}
end_class

end_unit

