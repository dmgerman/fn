begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref
package|package
name|org
operator|.
name|jabref
package|;
end_package

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|rules
operator|.
name|ExternalResource
import|;
end_import

begin_comment
comment|/**  * JUnit by default ignores exceptions, which are reported via {@link Thread.UncaughtExceptionHandler}. With this rule  * also these kind of exceptions result in a failure of the test.  */
end_comment

begin_class
DECL|class|CatchExceptionsFromThread
specifier|public
class|class
name|CatchExceptionsFromThread
extends|extends
name|ExternalResource
block|{
annotation|@
name|Override
DECL|method|before ()
specifier|protected
name|void
name|before
parameter_list|()
throws|throws
name|Throwable
block|{
name|Thread
operator|.
name|setDefaultUncaughtExceptionHandler
argument_list|(
parameter_list|(
name|thread
parameter_list|,
name|exception
parameter_list|)
lambda|->
block|{
comment|// We simply rethrow the exception (as a RuntimeException) so that JUnit picks it up
throw|throw
operator|new
name|RuntimeException
argument_list|(
name|exception
argument_list|)
throw|;
block|}
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|after ()
specifier|protected
name|void
name|after
parameter_list|()
block|{
name|Thread
operator|.
name|setDefaultUncaughtExceptionHandler
argument_list|(
operator|new
name|FallbackExceptionHandler
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

