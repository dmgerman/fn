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
DECL|class|ANTLRException
specifier|public
class|class
name|ANTLRException
extends|extends
name|Exception
block|{
DECL|method|ANTLRException ()
specifier|public
name|ANTLRException
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
DECL|method|ANTLRException (String s)
specifier|public
name|ANTLRException
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
block|}
end_class

end_unit

