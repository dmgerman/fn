begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.util
package|package
name|org
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
name|Objects
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|concurrent
operator|.
name|Callable
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|concurrent
operator|.
name|CountDownLatch
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|concurrent
operator|.
name|ExecutionException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|concurrent
operator|.
name|ExecutorService
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|concurrent
operator|.
name|Executors
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|concurrent
operator|.
name|Future
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|concurrent
operator|.
name|FutureTask
import|;
end_import

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
name|javafx
operator|.
name|application
operator|.
name|Platform
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|concurrent
operator|.
name|Task
import|;
end_import

begin_import
import|import
name|org
operator|.
name|fxmisc
operator|.
name|easybind
operator|.
name|EasyBind
import|;
end_import

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
comment|/**  * A very simple implementation of the {@link TaskExecutor} interface.  * Every submitted task is invoked in a separate thread.  */
end_comment

begin_class
DECL|class|DefaultTaskExecutor
specifier|public
class|class
name|DefaultTaskExecutor
implements|implements
name|TaskExecutor
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
name|DefaultTaskExecutor
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|EXECUTOR
specifier|private
specifier|static
specifier|final
name|ExecutorService
name|EXECUTOR
init|=
name|Executors
operator|.
name|newFixedThreadPool
argument_list|(
literal|5
argument_list|)
decl_stmt|;
comment|/**      *      */
DECL|method|runInJavaFXThread (Callable<V> callable)
specifier|public
specifier|static
parameter_list|<
name|V
parameter_list|>
name|V
name|runInJavaFXThread
parameter_list|(
name|Callable
argument_list|<
name|V
argument_list|>
name|callable
parameter_list|)
block|{
if|if
condition|(
name|Platform
operator|.
name|isFxApplicationThread
argument_list|()
condition|)
block|{
try|try
block|{
return|return
name|callable
operator|.
name|call
argument_list|()
return|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Problem executing call"
argument_list|,
name|e
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
name|FutureTask
argument_list|<
name|V
argument_list|>
name|task
init|=
operator|new
name|FutureTask
argument_list|<>
argument_list|(
name|callable
argument_list|)
decl_stmt|;
name|Platform
operator|.
name|runLater
argument_list|(
name|task
argument_list|)
expr_stmt|;
try|try
block|{
return|return
name|task
operator|.
name|get
argument_list|()
return|;
block|}
catch|catch
parameter_list|(
name|InterruptedException
decl||
name|ExecutionException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Problem running in fx thread"
argument_list|,
name|e
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
comment|/**      * Runs the specified {@link Runnable} on the JavaFX application thread and waits for completion.      *      * @param action the {@link Runnable} to run      * @throws NullPointerException if {@code action} is {@code null}      */
DECL|method|runAndWaitInJavaFXThread (Runnable action)
specifier|public
specifier|static
name|void
name|runAndWaitInJavaFXThread
parameter_list|(
name|Runnable
name|action
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|action
argument_list|)
expr_stmt|;
comment|// Run synchronously on JavaFX thread
if|if
condition|(
name|Platform
operator|.
name|isFxApplicationThread
argument_list|()
condition|)
block|{
name|action
operator|.
name|run
argument_list|()
expr_stmt|;
return|return;
block|}
comment|// Queue on JavaFX thread and wait for completion
specifier|final
name|CountDownLatch
name|doneLatch
init|=
operator|new
name|CountDownLatch
argument_list|(
literal|1
argument_list|)
decl_stmt|;
name|Platform
operator|.
name|runLater
argument_list|(
parameter_list|()
lambda|->
block|{
try|try
block|{
name|action
operator|.
name|run
argument_list|()
expr_stmt|;
block|}
finally|finally
block|{
name|doneLatch
operator|.
name|countDown
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
try|try
block|{
name|doneLatch
operator|.
name|await
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|InterruptedException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Problem running action on JavaFX thread"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|runInJavaFXThread (Runnable runnable)
specifier|public
specifier|static
name|void
name|runInJavaFXThread
parameter_list|(
name|Runnable
name|runnable
parameter_list|)
block|{
name|Platform
operator|.
name|runLater
argument_list|(
name|runnable
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|execute (BackgroundTask<V> task)
specifier|public
parameter_list|<
name|V
parameter_list|>
name|Future
argument_list|<
name|V
argument_list|>
name|execute
parameter_list|(
name|BackgroundTask
argument_list|<
name|V
argument_list|>
name|task
parameter_list|)
block|{
return|return
name|execute
argument_list|(
name|getJavaFXTask
argument_list|(
name|task
argument_list|)
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|execute (Task<V> task)
specifier|public
parameter_list|<
name|V
parameter_list|>
name|Future
argument_list|<
name|V
argument_list|>
name|execute
parameter_list|(
name|Task
argument_list|<
name|V
argument_list|>
name|task
parameter_list|)
block|{
name|EXECUTOR
operator|.
name|submit
argument_list|(
name|task
argument_list|)
expr_stmt|;
return|return
name|task
return|;
block|}
annotation|@
name|Override
DECL|method|shutdown ()
specifier|public
name|void
name|shutdown
parameter_list|()
block|{
name|EXECUTOR
operator|.
name|shutdownNow
argument_list|()
expr_stmt|;
block|}
DECL|method|getJavaFXTask (BackgroundTask<V> task)
specifier|private
parameter_list|<
name|V
parameter_list|>
name|Task
argument_list|<
name|V
argument_list|>
name|getJavaFXTask
parameter_list|(
name|BackgroundTask
argument_list|<
name|V
argument_list|>
name|task
parameter_list|)
block|{
name|Task
argument_list|<
name|V
argument_list|>
name|javaTask
init|=
operator|new
name|Task
argument_list|<
name|V
argument_list|>
argument_list|()
block|{
block|{
name|EasyBind
operator|.
name|subscribe
argument_list|(
name|task
operator|.
name|progressProperty
argument_list|()
argument_list|,
name|progress
lambda|->
name|updateProgress
argument_list|(
name|progress
operator|.
name|getWorkDone
argument_list|()
argument_list|,
name|progress
operator|.
name|getMax
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|EasyBind
operator|.
name|subscribe
argument_list|(
name|task
operator|.
name|messageProperty
argument_list|()
argument_list|,
name|this
operator|::
name|updateMessage
argument_list|)
expr_stmt|;
name|EasyBind
operator|.
name|subscribe
argument_list|(
name|task
operator|.
name|isCanceledProperty
argument_list|()
argument_list|,
name|cancelled
lambda|->
block|{
if|if
condition|(
name|cancelled
condition|)
block|{
name|cancel
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|V
name|call
parameter_list|()
throws|throws
name|Exception
block|{
return|return
name|task
operator|.
name|call
argument_list|()
return|;
block|}
block|}
decl_stmt|;
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
name|javaTask
operator|.
name|setOnRunning
argument_list|(
name|event
lambda|->
name|onRunning
operator|.
name|run
argument_list|()
argument_list|)
expr_stmt|;
block|}
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
name|javaTask
operator|.
name|setOnSucceeded
argument_list|(
name|event
lambda|->
name|onSuccess
operator|.
name|accept
argument_list|(
name|javaTask
operator|.
name|getValue
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
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
name|javaTask
operator|.
name|setOnFailed
argument_list|(
name|event
lambda|->
name|onException
operator|.
name|accept
argument_list|(
name|convertToException
argument_list|(
name|javaTask
operator|.
name|getException
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|javaTask
return|;
block|}
DECL|method|convertToException (Throwable throwable)
specifier|private
name|Exception
name|convertToException
parameter_list|(
name|Throwable
name|throwable
parameter_list|)
block|{
if|if
condition|(
name|throwable
operator|instanceof
name|Exception
condition|)
block|{
return|return
operator|(
name|Exception
operator|)
name|throwable
return|;
block|}
else|else
block|{
return|return
operator|new
name|Exception
argument_list|(
name|throwable
argument_list|)
return|;
block|}
block|}
block|}
end_class

end_unit

