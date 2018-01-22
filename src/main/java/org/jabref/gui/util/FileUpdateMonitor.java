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
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|file
operator|.
name|FileSystems
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|file
operator|.
name|Path
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|file
operator|.
name|StandardWatchEventKinds
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|file
operator|.
name|WatchEvent
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|file
operator|.
name|WatchKey
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|file
operator|.
name|WatchService
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
name|collect
operator|.
name|ArrayListMultimap
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
name|collect
operator|.
name|Multimap
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
comment|/**  * This class monitors a set of files for changes. Upon detecting a change it notifies the registered {@link  * FileUpdateListener}s.  *  * Implementation based on https://stackoverflow.com/questions/16251273/can-i-watch-for-single-file-change-with-watchservice-not-the-whole-directory  */
end_comment

begin_class
DECL|class|FileUpdateMonitor
specifier|public
class|class
name|FileUpdateMonitor
implements|implements
name|Runnable
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
name|FileUpdateMonitor
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|listeners
specifier|private
specifier|final
name|Multimap
argument_list|<
name|Path
argument_list|,
name|FileUpdateListener
argument_list|>
name|listeners
init|=
name|ArrayListMultimap
operator|.
name|create
argument_list|(
literal|20
argument_list|,
literal|4
argument_list|)
decl_stmt|;
DECL|field|watcher
specifier|private
name|WatchService
name|watcher
decl_stmt|;
annotation|@
name|Override
DECL|method|run ()
specifier|public
name|void
name|run
parameter_list|()
block|{
try|try
init|(
name|WatchService
name|watcher
init|=
name|FileSystems
operator|.
name|getDefault
argument_list|()
operator|.
name|newWatchService
argument_list|()
init|)
block|{
name|this
operator|.
name|watcher
operator|=
name|watcher
expr_stmt|;
while|while
condition|(
literal|true
condition|)
block|{
name|WatchKey
name|key
decl_stmt|;
try|try
block|{
name|key
operator|=
name|watcher
operator|.
name|take
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|InterruptedException
name|e
parameter_list|)
block|{
return|return;
block|}
for|for
control|(
name|WatchEvent
argument_list|<
name|?
argument_list|>
name|event
range|:
name|key
operator|.
name|pollEvents
argument_list|()
control|)
block|{
name|WatchEvent
operator|.
name|Kind
argument_list|<
name|?
argument_list|>
name|kind
init|=
name|event
operator|.
name|kind
argument_list|()
decl_stmt|;
if|if
condition|(
name|kind
operator|==
name|StandardWatchEventKinds
operator|.
name|OVERFLOW
condition|)
block|{
name|Thread
operator|.
name|yield
argument_list|()
expr_stmt|;
continue|continue;
block|}
elseif|else
if|if
condition|(
name|kind
operator|==
name|StandardWatchEventKinds
operator|.
name|ENTRY_MODIFY
condition|)
block|{
comment|// We only handle "ENTRY_MODIFY" here, so the context is always a Path
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
name|WatchEvent
argument_list|<
name|Path
argument_list|>
name|ev
init|=
operator|(
name|WatchEvent
argument_list|<
name|Path
argument_list|>
operator|)
name|event
decl_stmt|;
name|Path
name|path
init|=
operator|(
operator|(
name|Path
operator|)
name|key
operator|.
name|watchable
argument_list|()
operator|)
operator|.
name|resolve
argument_list|(
name|ev
operator|.
name|context
argument_list|()
argument_list|)
decl_stmt|;
name|notifyAboutChange
argument_list|(
name|path
argument_list|)
expr_stmt|;
block|}
name|key
operator|.
name|reset
argument_list|()
expr_stmt|;
block|}
name|Thread
operator|.
name|yield
argument_list|()
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|Throwable
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|debug
argument_list|(
literal|"FileUpdateMonitor has been interrupted. Terminating..."
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|notifyAboutChange (Path path)
specifier|private
name|void
name|notifyAboutChange
parameter_list|(
name|Path
name|path
parameter_list|)
block|{
name|listeners
operator|.
name|get
argument_list|(
name|path
argument_list|)
operator|.
name|forEach
argument_list|(
name|FileUpdateListener
operator|::
name|fileUpdated
argument_list|)
expr_stmt|;
block|}
comment|/**      * Add a new file to monitor.      *      * @param file The file to monitor.      * @throws IOException if the file does not exist.      */
DECL|method|addListenerForFile (Path file, FileUpdateListener listener)
specifier|public
name|void
name|addListenerForFile
parameter_list|(
name|Path
name|file
parameter_list|,
name|FileUpdateListener
name|listener
parameter_list|)
throws|throws
name|IOException
block|{
comment|// We can't watch files directly, so monitor their parent directory for updates
name|Path
name|directory
init|=
name|file
operator|.
name|toAbsolutePath
argument_list|()
operator|.
name|getParent
argument_list|()
decl_stmt|;
name|directory
operator|.
name|register
argument_list|(
name|watcher
argument_list|,
name|StandardWatchEventKinds
operator|.
name|ENTRY_MODIFY
argument_list|)
expr_stmt|;
name|listeners
operator|.
name|put
argument_list|(
name|file
argument_list|,
name|listener
argument_list|)
expr_stmt|;
block|}
comment|/**      * Removes a listener from the monitor.      *      * @param path The path to remove.      */
DECL|method|removeListener (Path path, FileUpdateListener listener)
specifier|public
name|void
name|removeListener
parameter_list|(
name|Path
name|path
parameter_list|,
name|FileUpdateListener
name|listener
parameter_list|)
block|{
name|listeners
operator|.
name|remove
argument_list|(
name|path
argument_list|,
name|listener
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

