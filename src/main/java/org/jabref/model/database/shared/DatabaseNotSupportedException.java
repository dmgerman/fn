begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.model.database.shared
package|package
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|database
operator|.
name|shared
package|;
end_package

begin_comment
comment|/**  * This exception is thrown in case that the SQL database structure is not compatible with the current shared database support mechanisms.  */
end_comment

begin_class
DECL|class|DatabaseNotSupportedException
specifier|public
class|class
name|DatabaseNotSupportedException
extends|extends
name|Exception
block|{
DECL|method|DatabaseNotSupportedException ()
specifier|public
name|DatabaseNotSupportedException
parameter_list|()
block|{
name|super
argument_list|(
literal|"The structure of the SQL database is not supported."
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

