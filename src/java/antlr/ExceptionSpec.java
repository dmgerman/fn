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
name|impl
operator|.
name|Vector
import|;
end_import

begin_class
DECL|class|ExceptionSpec
class|class
name|ExceptionSpec
block|{
comment|// Non-null if this refers to a labeled rule
comment|// Use a token instead of a string to get the line information
DECL|field|label
specifier|protected
name|Token
name|label
decl_stmt|;
comment|// List of ExceptionHandler (catch phrases)
DECL|field|handlers
specifier|protected
name|Vector
name|handlers
decl_stmt|;
DECL|method|ExceptionSpec (Token label_)
specifier|public
name|ExceptionSpec
parameter_list|(
name|Token
name|label_
parameter_list|)
block|{
name|label
operator|=
name|label_
expr_stmt|;
name|handlers
operator|=
operator|new
name|Vector
argument_list|()
expr_stmt|;
block|}
DECL|method|addHandler (ExceptionHandler handler)
specifier|public
name|void
name|addHandler
parameter_list|(
name|ExceptionHandler
name|handler
parameter_list|)
block|{
name|handlers
operator|.
name|appendElement
argument_list|(
name|handler
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

