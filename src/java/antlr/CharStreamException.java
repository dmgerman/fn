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

begin_comment
comment|/**  * Anything that goes wrong while generating a stream of characters  */
end_comment

begin_class
DECL|class|CharStreamException
specifier|public
class|class
name|CharStreamException
extends|extends
name|ANTLRException
block|{
comment|/**      * CharStreamException constructor comment.      * @param s java.lang.String      */
DECL|method|CharStreamException (String s)
specifier|public
name|CharStreamException
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

