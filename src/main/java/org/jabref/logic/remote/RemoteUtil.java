begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.remote
package|package
name|org
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
DECL|method|RemoteUtil ()
specifier|private
name|RemoteUtil
parameter_list|()
block|{     }
DECL|method|isUserPort (int portNumber)
specifier|public
specifier|static
name|boolean
name|isUserPort
parameter_list|(
name|int
name|portNumber
parameter_list|)
block|{
return|return
operator|(
name|portNumber
operator|>=
literal|1024
operator|)
operator|&&
operator|(
name|portNumber
operator|<=
literal|65535
operator|)
return|;
block|}
block|}
end_class

end_unit

