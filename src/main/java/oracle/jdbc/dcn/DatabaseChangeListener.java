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

begin_interface
DECL|interface|DatabaseChangeListener
specifier|public
interface|interface
name|DatabaseChangeListener
block|{
DECL|method|onDatabaseChangeNotification (DatabaseChangeEvent event)
specifier|public
name|void
name|onDatabaseChangeNotification
parameter_list|(
name|DatabaseChangeEvent
name|event
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

