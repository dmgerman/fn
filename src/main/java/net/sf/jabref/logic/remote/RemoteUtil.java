begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.remote
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|remote
package|;
end_package

begin_class
DECL|class|RemoteUtil
specifier|public
class|class
name|RemoteUtil
block|{
DECL|method|isValidPartNumber (int portNumber)
specifier|public
specifier|static
name|boolean
name|isValidPartNumber
parameter_list|(
name|int
name|portNumber
parameter_list|)
block|{
return|return
name|portNumber
operator|>
literal|1024
operator|&&
name|portNumber
operator|<=
literal|65535
return|;
block|}
block|}
end_class

end_unit

