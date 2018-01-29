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
name|slf4j
operator|.
name|Logger
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|LoggerFactory
import|;
end_import

begin_comment
comment|/**  * Catch and log any unhandled exceptions.  */
end_comment

begin_class
DECL|class|FallbackExceptionHandler
specifier|public
class|class
name|FallbackExceptionHandler
implements|implements
name|Thread
operator|.
name|UncaughtExceptionHandler
block|{
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Logger
name|LOGGER
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|FallbackExceptionHandler
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|method|installExceptionHandler ()
specifier|public
specifier|static
name|void
name|installExceptionHandler
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
annotation|@
name|Override
DECL|method|uncaughtException (Thread thread, Throwable exception)
specifier|public
name|void
name|uncaughtException
parameter_list|(
name|Thread
name|thread
parameter_list|,
name|Throwable
name|exception
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Uncaught exception occurred in "
operator|+
name|thread
argument_list|,
name|exception
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

