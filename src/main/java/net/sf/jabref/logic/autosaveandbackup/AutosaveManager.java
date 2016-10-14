begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.autosaveandbackup
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|autosaveandbackup
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashSet
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Set
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
name|ArrayBlockingQueue
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
name|BlockingQueue
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
name|RejectedExecutionException
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
name|ThreadPoolExecutor
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|model
operator|.
name|database
operator|.
name|BibDatabaseContext
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|model
operator|.
name|database
operator|.
name|event
operator|.
name|AutosaveEvent
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|model
operator|.
name|database
operator|.
name|event
operator|.
name|BibDatabaseContextChangedEvent
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|common
operator|.
name|eventbus
operator|.
name|EventBus
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|common
operator|.
name|eventbus
operator|.
name|Subscribe
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
comment|/**  * Saves the given {@link BibDatabaseContext} on every {@link BibDatabaseContextChangedEvent} by posting a new {@link AutosaveEvent}.  * An intelligent {@link ExecutorService} with a {@link BlockingQueue} prevents a high load while saving and rejects all redundant save tasks.  */
end_comment

begin_class
DECL|class|AutosaveManager
specifier|public
class|class
name|AutosaveManager
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
name|AutosaveManager
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|runningInstances
specifier|private
specifier|static
name|Set
argument_list|<
name|AutosaveManager
argument_list|>
name|runningInstances
init|=
operator|new
name|HashSet
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|bibDatabaseContext
specifier|private
specifier|final
name|BibDatabaseContext
name|bibDatabaseContext
decl_stmt|;
DECL|field|workerQueue
specifier|private
specifier|final
name|BlockingQueue
argument_list|<
name|Runnable
argument_list|>
name|workerQueue
decl_stmt|;
DECL|field|executor
specifier|private
specifier|final
name|ExecutorService
name|executor
decl_stmt|;
DECL|field|eventBus
specifier|private
specifier|final
name|EventBus
name|eventBus
decl_stmt|;
DECL|method|AutosaveManager (BibDatabaseContext bibDatabaseContext)
specifier|private
name|AutosaveManager
parameter_list|(
name|BibDatabaseContext
name|bibDatabaseContext
parameter_list|)
block|{
name|this
operator|.
name|bibDatabaseContext
operator|=
name|bibDatabaseContext
expr_stmt|;
name|this
operator|.
name|workerQueue
operator|=
operator|new
name|ArrayBlockingQueue
argument_list|<>
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|this
operator|.
name|executor
operator|=
operator|new
name|ThreadPoolExecutor
argument_list|(
literal|1
argument_list|,
literal|1
argument_list|,
literal|0
argument_list|,
name|TimeUnit
operator|.
name|SECONDS
argument_list|,
name|workerQueue
argument_list|)
expr_stmt|;
name|this
operator|.
name|eventBus
operator|=
operator|new
name|EventBus
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Subscribe
DECL|method|listen (@uppressWarningsR) BibDatabaseContextChangedEvent event)
specifier|public
specifier|synchronized
name|void
name|listen
parameter_list|(
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unused"
argument_list|)
name|BibDatabaseContextChangedEvent
name|event
parameter_list|)
block|{
try|try
block|{
name|executor
operator|.
name|submit
argument_list|(
parameter_list|()
lambda|->
block|{
name|eventBus
operator|.
name|post
argument_list|(
operator|new
name|AutosaveEvent
argument_list|()
argument_list|)
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|RejectedExecutionException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|debug
argument_list|(
literal|"Rejecting autosave while another save process is already running."
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|shutdown ()
specifier|private
name|void
name|shutdown
parameter_list|()
block|{
name|bibDatabaseContext
operator|.
name|getDatabase
argument_list|()
operator|.
name|unregisterListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|bibDatabaseContext
operator|.
name|getMetaData
argument_list|()
operator|.
name|unregisterListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|executor
operator|.
name|shutdown
argument_list|()
expr_stmt|;
block|}
comment|/**      * Starts the Autosaver which is associated with the given {@link BibDatabaseContext}.      *      * @param bibDatabaseContext Associated {@link BibDatabaseContext}      */
DECL|method|start (BibDatabaseContext bibDatabaseContext)
specifier|public
specifier|static
name|AutosaveManager
name|start
parameter_list|(
name|BibDatabaseContext
name|bibDatabaseContext
parameter_list|)
block|{
name|AutosaveManager
name|autosaver
init|=
operator|new
name|AutosaveManager
argument_list|(
name|bibDatabaseContext
argument_list|)
decl_stmt|;
name|bibDatabaseContext
operator|.
name|getDatabase
argument_list|()
operator|.
name|registerListener
argument_list|(
name|autosaver
argument_list|)
expr_stmt|;
name|bibDatabaseContext
operator|.
name|getMetaData
argument_list|()
operator|.
name|registerListener
argument_list|(
name|autosaver
argument_list|)
expr_stmt|;
name|runningInstances
operator|.
name|add
argument_list|(
name|autosaver
argument_list|)
expr_stmt|;
return|return
name|autosaver
return|;
block|}
comment|/**      * Shuts down the Autosaver which is associated with the given {@link BibDatabaseContext}.      *      * @param bibDatabaseContext Associated {@link BibDatabaseContext}      */
DECL|method|shutdown (BibDatabaseContext bibDatabaseContext)
specifier|public
specifier|static
name|void
name|shutdown
parameter_list|(
name|BibDatabaseContext
name|bibDatabaseContext
parameter_list|)
block|{
name|runningInstances
operator|.
name|stream
argument_list|()
operator|.
name|filter
argument_list|(
name|instance
lambda|->
name|instance
operator|.
name|bibDatabaseContext
operator|==
name|bibDatabaseContext
argument_list|)
operator|.
name|findAny
argument_list|()
operator|.
name|ifPresent
argument_list|(
name|instance
lambda|->
block|{
name|instance
operator|.
name|shutdown
argument_list|()
argument_list|;
name|runningInstances
operator|.
name|remove
argument_list|(
name|instance
argument_list|)
argument_list|;
block|}
block|)
class|;
end_class

begin_function
unit|}      public
DECL|method|registerListener (Object listener)
name|void
name|registerListener
parameter_list|(
name|Object
name|listener
parameter_list|)
block|{
name|eventBus
operator|.
name|register
argument_list|(
name|listener
argument_list|)
expr_stmt|;
block|}
end_function

begin_function
DECL|method|unregisterListener (Object listener)
specifier|public
name|void
name|unregisterListener
parameter_list|(
name|Object
name|listener
parameter_list|)
block|{
name|eventBus
operator|.
name|unregister
argument_list|(
name|listener
argument_list|)
expr_stmt|;
block|}
end_function

unit|}
end_unit

