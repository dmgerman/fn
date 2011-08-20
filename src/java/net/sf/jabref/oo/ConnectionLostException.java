begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.oo
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|oo
package|;
end_package

begin_comment
comment|/**  * This exception is used to indicate that connection to OpenOffice has been lost.  */
end_comment

begin_class
DECL|class|ConnectionLostException
specifier|public
class|class
name|ConnectionLostException
extends|extends
name|RuntimeException
block|{
DECL|method|ConnectionLostException (String s)
specifier|public
name|ConnectionLostException
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

