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

begin_comment
comment|/**  * Anything that goes wrong while generating a stream of tokens.  */
end_comment

begin_class
DECL|class|TokenStreamException
specifier|public
class|class
name|TokenStreamException
extends|extends
name|ANTLRException
block|{
DECL|method|TokenStreamException ()
specifier|public
name|TokenStreamException
parameter_list|()
block|{ 	}
DECL|method|TokenStreamException (String s)
specifier|public
name|TokenStreamException
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

