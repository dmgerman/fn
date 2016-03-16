begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

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

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|undo
operator|.
name|UndoableInsertEntry
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
name|UndoableInsertEntry
operator|.
name|class
argument_list|)
decl_stmt|;
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
argument_list|(
operator|new
name|ThreadFactory
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|Thread
name|newThread
parameter_list|(
name|Runnable
name|r
parameter_list|)
block|{
name|Thread
name|thread
init|=
operator|new
name|Thread
argument_list|(
name|r
argument_list|)
decl_stmt|;
name|thread
operator|.
name|setName
argument_list|(
literal|"JabRef CachedThreadPool"
argument_list|)
expr_stmt|;
return|return
name|thread
return|;
block|}
block|}
argument_list|)
decl_stmt|;
DECL|field|startedThreads
specifier|private
specifier|final
name|ConcurrentLinkedQueue
argument_list|<
name|Thread
argument_list|>
name|startedThreads
init|=
operator|new
name|ConcurrentLinkedQueue
argument_list|<>
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
name|ignored
parameter_list|)
block|{
comment|// Ignored
block|}
catch|catch
parameter_list|(
name|ExecutionException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Problem executing command"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
block|}
DECL|class|AutoCleanupRunnable
specifier|private
specifier|static
class|class
name|AutoCleanupRunnable
implements|implements
name|Runnable
block|{
DECL|field|runnable
specifier|private
specifier|final
name|Runnable
name|runnable
decl_stmt|;
DECL|field|startedThreads
specifier|private
specifier|final
name|ConcurrentLinkedQueue
argument_list|<
name|Thread
argument_list|>
name|startedThreads
decl_stmt|;
DECL|field|thread
specifier|public
name|Thread
name|thread
decl_stmt|;
DECL|method|AutoCleanupRunnable (Runnable runnable, ConcurrentLinkedQueue<Thread> startedThreads)
specifier|private
name|AutoCleanupRunnable
parameter_list|(
name|Runnable
name|runnable
parameter_list|,
name|ConcurrentLinkedQueue
argument_list|<
name|Thread
argument_list|>
name|startedThreads
parameter_list|)
block|{
name|this
operator|.
name|runnable
operator|=
name|runnable
expr_stmt|;
name|this
operator|.
name|startedThreads
operator|=
name|startedThreads
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|run ()
specifier|public
name|void
name|run
parameter_list|()
block|{
try|try
block|{
name|runnable
operator|.
name|run
argument_list|()
expr_stmt|;
block|}
finally|finally
block|{
name|startedThreads
operator|.
name|remove
argument_list|(
name|thread
argument_list|)
expr_stmt|;
block|}
block|}
block|}
DECL|method|executeWithLowPriorityInOwnThread (final Runnable runnable, String name)
specifier|public
name|void
name|executeWithLowPriorityInOwnThread
parameter_list|(
specifier|final
name|Runnable
name|runnable
parameter_list|,
name|String
name|name
parameter_list|)
block|{
name|AutoCleanupRunnable
name|target
init|=
operator|new
name|AutoCleanupRunnable
argument_list|(
name|runnable
argument_list|,
name|startedThreads
argument_list|)
decl_stmt|;
specifier|final
name|Thread
name|thread
init|=
operator|new
name|Thread
argument_list|(
name|target
argument_list|)
decl_stmt|;
name|target
operator|.
name|thread
operator|=
name|thread
expr_stmt|;
name|thread
operator|.
name|setName
argument_list|(
literal|"JabRef - "
operator|+
name|name
operator|+
literal|" - low prio"
argument_list|)
expr_stmt|;
name|startedThreads
operator|.
name|add
argument_list|(
name|thread
argument_list|)
expr_stmt|;
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
DECL|method|executeInOwnThread (Thread thread)
specifier|public
name|void
name|executeInOwnThread
parameter_list|(
name|Thread
name|thread
parameter_list|)
block|{
comment|// this is a special case method for Threads that cannot be interrupted so easily
comment|// this method should normally not be used
name|startedThreads
operator|.
name|add
argument_list|(
name|thread
argument_list|)
expr_stmt|;
comment|// TODO memory leak when thread is finished
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
name|setName
argument_list|(
literal|"JabRef low prio"
argument_list|)
expr_stmt|;
name|startedThreads
operator|.
name|add
argument_list|(
name|thread
argument_list|)
expr_stmt|;
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
name|waitForThreadToFinish
argument_list|(
name|thread
argument_list|)
expr_stmt|;
block|}
DECL|method|waitForThreadToFinish (Thread thread)
specifier|private
name|void
name|waitForThreadToFinish
parameter_list|(
name|Thread
name|thread
parameter_list|)
block|{
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
name|startedThreads
operator|.
name|remove
argument_list|(
name|thread
argument_list|)
expr_stmt|;
return|return;
block|}
catch|catch
parameter_list|(
name|InterruptedException
name|ignored
parameter_list|)
block|{
comment|// Ignored
block|}
block|}
block|}
DECL|method|shutdownEverything ()
specifier|public
name|void
name|shutdownEverything
parameter_list|()
block|{
name|this
operator|.
name|executorService
operator|.
name|shutdown
argument_list|()
expr_stmt|;
for|for
control|(
name|Thread
name|thread
range|:
name|startedThreads
control|)
block|{
name|thread
operator|.
name|interrupt
argument_list|()
expr_stmt|;
block|}
name|startedThreads
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

