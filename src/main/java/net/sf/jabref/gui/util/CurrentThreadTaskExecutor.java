begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.util
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|util
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|function
operator|.
name|Consumer
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|Log
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|LogFactory
import|;
end_import

begin_comment
comment|/**  * Implementation of {@link TaskExecutor} that runs every task on the current thread, i.e. in a sequential order.  * This class is not designed to be used in production but should make code involving asynchronous operations  * deterministic and testable.  */
end_comment

begin_class
DECL|class|CurrentThreadTaskExecutor
specifier|public
class|class
name|CurrentThreadTaskExecutor
implements|implements
name|TaskExecutor
block|{
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Log
name|LOGGER
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|CurrentThreadTaskExecutor
operator|.
name|class
argument_list|)
decl_stmt|;
comment|/**      * Executes the task on the current thread.      * The code is essentially taken from {@link javafx.concurrent.Task.TaskCallable#call()},      * but adapted to run sequentially.      */
annotation|@
name|Override
DECL|method|execute (BackgroundTask<V> task)
specifier|public
parameter_list|<
name|V
parameter_list|>
name|void
name|execute
parameter_list|(
name|BackgroundTask
argument_list|<
name|V
argument_list|>
name|task
parameter_list|)
block|{
name|Runnable
name|onRunning
init|=
name|task
operator|.
name|getOnRunning
argument_list|()
decl_stmt|;
if|if
condition|(
name|onRunning
operator|!=
literal|null
condition|)
block|{
name|onRunning
operator|.
name|run
argument_list|()
expr_stmt|;
block|}
try|try
block|{
specifier|final
name|V
name|result
init|=
name|task
operator|.
name|call
argument_list|()
decl_stmt|;
name|Consumer
argument_list|<
name|V
argument_list|>
name|onSuccess
init|=
name|task
operator|.
name|getOnSuccess
argument_list|()
decl_stmt|;
if|if
condition|(
name|onSuccess
operator|!=
literal|null
condition|)
block|{
name|onSuccess
operator|.
name|accept
argument_list|(
name|result
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|exception
parameter_list|)
block|{
name|Consumer
argument_list|<
name|Exception
argument_list|>
name|onException
init|=
name|task
operator|.
name|getOnException
argument_list|()
decl_stmt|;
if|if
condition|(
name|onException
operator|!=
literal|null
condition|)
block|{
name|onException
operator|.
name|accept
argument_list|(
name|exception
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Unhandled exception"
argument_list|,
name|exception
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
end_class

end_unit
