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
DECL|class|ANTLRError
specifier|public
class|class
name|ANTLRError
extends|extends
name|Error
block|{
comment|/**      * ANTLRError constructor comment.      */
DECL|method|ANTLRError ()
specifier|public
name|ANTLRError
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
comment|/**      * ANTLRError constructor comment.      * @param s java.lang.String      */
DECL|method|ANTLRError (String s)
specifier|public
name|ANTLRError
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

