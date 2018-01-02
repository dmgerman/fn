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
comment|/**  * This exception is thrown when a shared database is required, but it actually isn't one.  */
end_comment

begin_class
DECL|class|NotASharedDatabaseException
specifier|public
class|class
name|NotASharedDatabaseException
extends|extends
name|Exception
block|{
DECL|method|NotASharedDatabaseException ()
specifier|public
name|NotASharedDatabaseException
parameter_list|()
block|{
name|super
argument_list|(
literal|"Required database is not shared."
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

