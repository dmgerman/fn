begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|oracle.jdbc
package|package
name|oracle
operator|.
name|jdbc
package|;
end_package

begin_import
import|import
name|oracle
operator|.
name|jdbc
operator|.
name|dcn
operator|.
name|DatabaseChangeRegistration
import|;
end_import

begin_comment
comment|/**  * A mocking class used as a placeholder for the real Oracle JDBC drivers to prevent build errors.  */
end_comment

begin_class
DECL|class|OracleStatement
specifier|public
class|class
name|OracleStatement
block|{
DECL|method|setDatabaseChangeRegistration (@uppressWarningsR) DatabaseChangeRegistration registration)
specifier|public
name|void
name|setDatabaseChangeRegistration
parameter_list|(
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unused"
argument_list|)
name|DatabaseChangeRegistration
name|registration
parameter_list|)
block|{
comment|// do nothing
block|}
block|}
end_class

end_unit

