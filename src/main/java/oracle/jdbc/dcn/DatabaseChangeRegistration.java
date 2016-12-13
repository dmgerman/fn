begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|oracle.jdbc.dcn
package|package
name|oracle
operator|.
name|jdbc
operator|.
name|dcn
package|;
end_package

begin_comment
comment|/**  * A mocking class used as a placeholder for the real Oracle JDBC drivers to prevent build errors.  */
end_comment

begin_class
DECL|class|DatabaseChangeRegistration
specifier|public
class|class
name|DatabaseChangeRegistration
block|{
DECL|method|addListener (@uppressWarningsR) DatabaseChangeListener listener)
specifier|public
name|void
name|addListener
parameter_list|(
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unused"
argument_list|)
name|DatabaseChangeListener
name|listener
parameter_list|)
block|{
comment|// do nothing
block|}
block|}
end_class

end_unit

