begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|concurrent
operator|.
name|*
import|;
end_import

begin_comment
comment|/**  * Responsible for managing of all threads (except Swing threads) in JabRef  */
end_comment

begin_class
DECL|class|JabRefExecutorService
specifier|public
class|class
name|JabRefExecutorService
implements|implements
name|Executor
block|{
DECL|field|INSTANCE
specifier|public
specifier|static
specifier|final
name|JabRefExecutorService
name|INSTANCE
init|=
operator|new
name|JabRefExecutorService
argument_list|()
decl_stmt|;
DECL|field|executorService
specifier|private
specifier|final
name|ExecutorService
name|executorService
init|=
name|Executors
operator|.
name|newCachedThreadPool
argument_list|()
decl_stmt|;
DECL|method|JabRefExecutorService ()
specifier|private
name|JabRefExecutorService
parameter_list|()
block|{}
annotation|@
name|Override
DECL|method|execute (Runnable command)
specifier|public
name|void
name|execute
parameter_list|(
name|Runnable
name|command
parameter_list|)
block|{
if|if
condition|(
name|command
operator|==
literal|null
condition|)
block|{
comment|//TODO logger
return|return;
block|}
name|executorService
operator|.
name|execute
argument_list|(
name|command
argument_list|)
expr_stmt|;
block|}
DECL|method|executeAndWait (Runnable command)
specifier|public
name|void
name|executeAndWait
parameter_list|(
name|Runnable
name|command
parameter_list|)
block|{
if|if
condition|(
name|command
operator|==
literal|null
condition|)
block|{
comment|//TODO logger
return|return;
block|}
name|Future
argument_list|<
name|?
argument_list|>
name|future
init|=
name|executorService
operator|.
name|submit
argument_list|(
name|command
argument_list|)
decl_stmt|;
while|while
condition|(
literal|true
condition|)
block|{
try|try
block|{
name|future
operator|.
name|get
argument_list|()
expr_stmt|;
return|return;
block|}
catch|catch
parameter_list|(
name|InterruptedException
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ExecutionException
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
block|}
DECL|method|executeWithLowPriorityInOwnThread (Runnable runnable)
specifier|public
name|void
name|executeWithLowPriorityInOwnThread
parameter_list|(
name|Runnable
name|runnable
parameter_list|)
block|{
name|Thread
name|thread
init|=
operator|new
name|Thread
argument_list|(
name|runnable
argument_list|)
decl_stmt|;
name|thread
operator|.
name|setPriority
argument_list|(
name|Thread
operator|.
name|MIN_PRIORITY
argument_list|)
expr_stmt|;
name|thread
operator|.
name|start
argument_list|()
expr_stmt|;
block|}
DECL|method|executeInOwnThread (Runnable runnable)
specifier|public
name|void
name|executeInOwnThread
parameter_list|(
name|Runnable
name|runnable
parameter_list|)
block|{
name|Thread
name|thread
init|=
operator|new
name|Thread
argument_list|(
name|runnable
argument_list|)
decl_stmt|;
name|thread
operator|.
name|start
argument_list|()
expr_stmt|;
block|}
DECL|method|executeWithLowPriorityInOwnThreadAndWait (Runnable runnable)
specifier|public
name|void
name|executeWithLowPriorityInOwnThreadAndWait
parameter_list|(
name|Runnable
name|runnable
parameter_list|)
block|{
name|Thread
name|thread
init|=
operator|new
name|Thread
argument_list|(
name|runnable
argument_list|)
decl_stmt|;
name|thread
operator|.
name|setPriority
argument_list|(
name|Thread
operator|.
name|MIN_PRIORITY
argument_list|)
expr_stmt|;
name|thread
operator|.
name|start
argument_list|()
expr_stmt|;
while|while
condition|(
literal|true
condition|)
block|{
try|try
block|{
name|thread
operator|.
name|join
argument_list|()
expr_stmt|;
return|return;
block|}
catch|catch
parameter_list|(
name|InterruptedException
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
block|}
block|}
end_class

end_unit

