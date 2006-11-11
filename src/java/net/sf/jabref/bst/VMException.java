begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.bst
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|bst
package|;
end_package

begin_class
DECL|class|VMException
specifier|public
class|class
name|VMException
extends|extends
name|RuntimeException
block|{
DECL|method|VMException (String string)
specifier|public
name|VMException
parameter_list|(
name|String
name|string
parameter_list|)
block|{
name|super
argument_list|(
name|string
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

