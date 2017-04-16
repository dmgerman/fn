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
comment|/**      * Runs the given task.      *      * @param task the task to run      * @param<V>  type of return value of the task      */
DECL|method|execute (BackgroundTask<V> task)
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

