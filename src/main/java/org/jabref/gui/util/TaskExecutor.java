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
name|TimeUnit
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

begin_comment
comment|/**  * An object that executes submitted {@link Task}s. This  * interface provides a way of decoupling task submission from the  * mechanics of how each task will be run, including details of thread  * use, scheduling, thread pooling, etc.  */
end_comment

begin_interface
DECL|interface|TaskExecutor
specifier|public
interface|interface
name|TaskExecutor
block|{
comment|/**      * Runs the given task and returns a Future representing that task.      *      * @param<V>  type of return value of the task      * @param task the task to run      */
DECL|method|execute (BackgroundTask<V> task)
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
function_decl|;
comment|/**      * Runs the given task and returns a Future representing that task. Usually, you want to use the other method {@link      * #execute(BackgroundTask)}.      *      * @param<V>  type of return value of the task      * @param task the task to run      */
DECL|method|execute (Task<V> task)
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
function_decl|;
comment|/**      * Submits a one-shot task that becomes enabled after the given delay.      *      * @param task  the task to execute      * @param delay the time from now to delay execution      * @param unit  the time unit of the delay parameter      * @return a ScheduledFuture representing pending completion of      *         the task and whose {@code get()} method will return      *         {@code null} upon completion      */
DECL|method|schedule (BackgroundTask<V> task, long delay, TimeUnit unit)
parameter_list|<
name|V
parameter_list|>
name|Future
argument_list|<
name|?
argument_list|>
name|schedule
parameter_list|(
name|BackgroundTask
argument_list|<
name|V
argument_list|>
name|task
parameter_list|,
name|long
name|delay
parameter_list|,
name|TimeUnit
name|unit
parameter_list|)
function_decl|;
comment|/**      * Shutdown the task executor.      */
DECL|method|shutdown ()
name|void
name|shutdown
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

