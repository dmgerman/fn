begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.shared.exception
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|shared
operator|.
name|exception
package|;
end_package

begin_comment
comment|/**  * This exception is thrown in case that {@link DBMSConnectionProperties} does not provide all data needed for a connection.  */
end_comment

begin_class
DECL|class|InvalidDBMSConnectionPropertiesException
specifier|public
class|class
name|InvalidDBMSConnectionPropertiesException
extends|extends
name|Exception
block|{
DECL|method|InvalidDBMSConnectionPropertiesException ()
specifier|public
name|InvalidDBMSConnectionPropertiesException
parameter_list|()
block|{
name|super
argument_list|(
literal|"Required connection details not present."
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

