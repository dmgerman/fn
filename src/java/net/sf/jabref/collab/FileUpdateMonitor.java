begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.collab
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|collab
package|;
end_package

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|Globals
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
name|Util
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|File
import|;
end_import

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
name|util
operator|.
name|Iterator
import|;
end_import

begin_comment
comment|/**  * This thread monitors a set of files, each associated with a FileUpdateListener, for changes * in the file's last modification time stamp. The  */
end_comment

begin_class
DECL|class|FileUpdateMonitor
specifier|public
class|class
name|FileUpdateMonitor
extends|extends
name|Thread
block|{
DECL|field|WAIT
specifier|final
name|int
name|WAIT
init|=
literal|4000
decl_stmt|;
DECL|field|tmpNum
specifier|static
name|int
name|tmpNum
init|=
literal|0
decl_stmt|;
DECL|field|no
name|int
name|no
init|=
literal|0
decl_stmt|;
DECL|field|entries
name|HashMap
argument_list|<
name|String
argument_list|,
name|Entry
argument_list|>
name|entries
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|Entry
argument_list|>
argument_list|()
decl_stmt|;
DECL|field|running
name|boolean
name|running
decl_stmt|;
DECL|method|FileUpdateMonitor ()
specifier|public
name|FileUpdateMonitor
parameter_list|()
block|{
name|setPriority
argument_list|(
name|MIN_PRIORITY
argument_list|)
expr_stmt|;
block|}
DECL|method|run ()
specifier|public
name|void
name|run
parameter_list|()
block|{
name|running
operator|=
literal|true
expr_stmt|;
comment|// The running variable is used to make the thread stop when needed.
while|while
condition|(
name|running
condition|)
block|{
comment|//System.out.println("Polling...");
name|Iterator
argument_list|<
name|String
argument_list|>
name|i
init|=
name|entries
operator|.
name|keySet
argument_list|()
operator|.
name|iterator
argument_list|()
decl_stmt|;
for|for
control|(
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Entry
name|e
init|=
name|entries
operator|.
name|get
argument_list|(
name|i
operator|.
name|next
argument_list|()
argument_list|)
decl_stmt|;
try|try
block|{
if|if
condition|(
name|e
operator|.
name|hasBeenUpdated
argument_list|()
condition|)
name|e
operator|.
name|notifyListener
argument_list|()
expr_stmt|;
comment|//else
comment|//System.out.println("File '"+e.file.getPath()+"' not modified.");
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{
name|e
operator|.
name|notifyFileRemoved
argument_list|()
expr_stmt|;
block|}
block|}
comment|// Sleep for a while before starting a new polling round.
try|try
block|{
name|sleep
argument_list|(
name|WAIT
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|InterruptedException
name|ex
parameter_list|)
block|{       }
block|}
block|}
comment|/**    * Cause the thread to stop monitoring. It will finish the current round before stopping.    */
DECL|method|stopMonitoring ()
specifier|public
name|void
name|stopMonitoring
parameter_list|()
block|{
name|running
operator|=
literal|false
expr_stmt|;
block|}
comment|/**    * Add a new file to monitor. Returns a handle for accessing the entry.    * @param ul FileUpdateListener The listener to notify when the file changes.    * @param file File The file to monitor.    * @throws IOException if the file does not exist.    */
DECL|method|addUpdateListener (FileUpdateListener ul, File file)
specifier|public
name|String
name|addUpdateListener
parameter_list|(
name|FileUpdateListener
name|ul
parameter_list|,
name|File
name|file
parameter_list|)
throws|throws
name|IOException
block|{
comment|// System.out.println(file.getPath());
if|if
condition|(
operator|!
name|file
operator|.
name|exists
argument_list|()
condition|)
throw|throw
operator|new
name|IOException
argument_list|(
literal|"File not found"
argument_list|)
throw|;
name|no
operator|++
expr_stmt|;
name|String
name|key
init|=
literal|""
operator|+
name|no
decl_stmt|;
name|entries
operator|.
name|put
argument_list|(
name|key
argument_list|,
operator|new
name|Entry
argument_list|(
name|ul
argument_list|,
name|file
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|key
return|;
block|}
comment|/**      * Forces a check on the file, and returns the result. Does not      * force a report to all listeners before the next routine check.      */
DECL|method|hasBeenModified (String handle)
specifier|public
name|boolean
name|hasBeenModified
parameter_list|(
name|String
name|handle
parameter_list|)
throws|throws
name|IllegalArgumentException
block|{
name|Object
name|o
init|=
name|entries
operator|.
name|get
argument_list|(
name|handle
argument_list|)
decl_stmt|;
if|if
condition|(
name|o
operator|==
literal|null
condition|)
return|return
literal|false
return|;
comment|//	    throw new IllegalArgumentException("Entry not found");
try|try
block|{
return|return
operator|(
operator|(
name|Entry
operator|)
name|o
operator|)
operator|.
name|hasBeenUpdated
argument_list|()
return|;
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{
comment|// Thrown if file has been removed. We return false.
return|return
literal|false
return|;
block|}
block|}
comment|/**      * Change the stored timestamp for the given file. If the timestamp equals      * the file's timestamp on disk, after this call the file will appear to      * have been modified. Used if a file has been modified, and the change      * scan fails, in order to ensure successive checks.      * @param handle the handle to the correct file.      */
DECL|method|perturbTimestamp (String handle)
specifier|public
name|void
name|perturbTimestamp
parameter_list|(
name|String
name|handle
parameter_list|)
block|{
name|Object
name|o
init|=
name|entries
operator|.
name|get
argument_list|(
name|handle
argument_list|)
decl_stmt|;
if|if
condition|(
name|o
operator|==
literal|null
condition|)
return|return;
operator|(
operator|(
name|Entry
operator|)
name|o
operator|)
operator|.
name|timeStamp
operator|--
expr_stmt|;
block|}
comment|/**    * Removes a listener from the monitor.    * @param handle String The handle for the listener to remove.    */
DECL|method|removeUpdateListener (String handle)
specifier|public
name|void
name|removeUpdateListener
parameter_list|(
name|String
name|handle
parameter_list|)
block|{
name|entries
operator|.
name|remove
argument_list|(
name|handle
argument_list|)
expr_stmt|;
block|}
DECL|method|updateTimeStamp (String key)
specifier|public
name|void
name|updateTimeStamp
parameter_list|(
name|String
name|key
parameter_list|)
throws|throws
name|IllegalArgumentException
block|{
name|Object
name|o
init|=
name|entries
operator|.
name|get
argument_list|(
name|key
argument_list|)
decl_stmt|;
if|if
condition|(
name|o
operator|==
literal|null
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Entry not found"
argument_list|)
throw|;
name|Entry
name|entry
init|=
operator|(
name|Entry
operator|)
name|o
decl_stmt|;
name|entry
operator|.
name|updateTimeStamp
argument_list|()
expr_stmt|;
block|}
DECL|method|changeFile (String key, File file)
specifier|public
name|void
name|changeFile
parameter_list|(
name|String
name|key
parameter_list|,
name|File
name|file
parameter_list|)
throws|throws
name|IOException
throws|,
name|IllegalArgumentException
block|{
if|if
condition|(
operator|!
name|file
operator|.
name|exists
argument_list|()
condition|)
throw|throw
operator|new
name|IOException
argument_list|(
literal|"File not found"
argument_list|)
throw|;
name|Object
name|o
init|=
name|entries
operator|.
name|get
argument_list|(
name|key
argument_list|)
decl_stmt|;
if|if
condition|(
name|o
operator|==
literal|null
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Entry not found"
argument_list|)
throw|;
operator|(
operator|(
name|Entry
operator|)
name|o
operator|)
operator|.
name|file
operator|=
name|file
expr_stmt|;
block|}
comment|/**    * Method for getting the temporary file used for this database. The tempfile    * is used for comparison with the changed on-disk version.    * @param key String The handle for this monitor.    * @throws IllegalArgumentException If the handle doesn't correspond to an entry.    * @return File The temporary file.    */
DECL|method|getTempFile (String key)
specifier|public
name|File
name|getTempFile
parameter_list|(
name|String
name|key
parameter_list|)
throws|throws
name|IllegalArgumentException
block|{
name|Object
name|o
init|=
name|entries
operator|.
name|get
argument_list|(
name|key
argument_list|)
decl_stmt|;
if|if
condition|(
name|o
operator|==
literal|null
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Entry not found"
argument_list|)
throw|;
return|return
operator|(
operator|(
name|Entry
operator|)
name|o
operator|)
operator|.
name|tmpFile
return|;
block|}
comment|/**    * A class containing the File, the FileUpdateListener and the current time stamp for one file.    */
DECL|class|Entry
class|class
name|Entry
block|{
DECL|field|listener
name|FileUpdateListener
name|listener
decl_stmt|;
DECL|field|file
name|File
name|file
decl_stmt|;
DECL|field|tmpFile
name|File
name|tmpFile
decl_stmt|;
DECL|field|timeStamp
name|long
name|timeStamp
decl_stmt|;
DECL|method|Entry (FileUpdateListener ul, File f)
specifier|public
name|Entry
parameter_list|(
name|FileUpdateListener
name|ul
parameter_list|,
name|File
name|f
parameter_list|)
block|{
name|listener
operator|=
name|ul
expr_stmt|;
name|file
operator|=
name|f
expr_stmt|;
name|timeStamp
operator|=
name|file
operator|.
name|lastModified
argument_list|()
expr_stmt|;
name|tmpFile
operator|=
name|getTempFile
argument_list|()
expr_stmt|;
name|copy
argument_list|()
expr_stmt|;
block|}
comment|/**      * Check if time stamp has changed.      * @throws IOException if the file does no longer exist.      * @return boolean true if the file has changed.      */
DECL|method|hasBeenUpdated ()
specifier|public
name|boolean
name|hasBeenUpdated
parameter_list|()
throws|throws
name|IOException
block|{
name|long
name|modified
init|=
name|file
operator|.
name|lastModified
argument_list|()
decl_stmt|;
if|if
condition|(
name|modified
operator|==
literal|0L
condition|)
throw|throw
operator|new
name|IOException
argument_list|(
literal|"File deleted"
argument_list|)
throw|;
return|return
name|timeStamp
operator|!=
name|modified
return|;
block|}
DECL|method|updateTimeStamp ()
specifier|public
name|void
name|updateTimeStamp
parameter_list|()
block|{
name|timeStamp
operator|=
name|file
operator|.
name|lastModified
argument_list|()
expr_stmt|;
if|if
condition|(
name|timeStamp
operator|==
literal|0L
condition|)
name|notifyFileRemoved
argument_list|()
expr_stmt|;
name|copy
argument_list|()
expr_stmt|;
block|}
DECL|method|copy ()
specifier|public
name|boolean
name|copy
parameter_list|()
block|{
comment|//Util.pr("<copy file=\""+tmpFile.getPath()+"\">");
name|boolean
name|res
init|=
literal|false
decl_stmt|;
try|try
block|{
name|res
operator|=
name|Util
operator|.
name|copyFile
argument_list|(
name|file
argument_list|,
name|tmpFile
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{
name|Globals
operator|.
name|logger
argument_list|(
literal|"Cannot copy to temporary file '"
operator|+
name|tmpFile
operator|.
name|getPath
argument_list|()
operator|+
literal|"'"
argument_list|)
expr_stmt|;
block|}
comment|//Util.pr("</copy>");
return|return
name|res
return|;
comment|//return true;
block|}
comment|/**      * Call the listener method to signal that the file has changed.      */
DECL|method|notifyListener ()
specifier|public
name|void
name|notifyListener
parameter_list|()
block|{
comment|// Update time stamp.
name|timeStamp
operator|=
name|file
operator|.
name|lastModified
argument_list|()
expr_stmt|;
name|listener
operator|.
name|fileUpdated
argument_list|()
expr_stmt|;
block|}
comment|/**      * Call the listener method to signal that the file has been removed.      */
DECL|method|notifyFileRemoved ()
specifier|public
name|void
name|notifyFileRemoved
parameter_list|()
block|{
name|listener
operator|.
name|fileRemoved
argument_list|()
expr_stmt|;
block|}
DECL|method|finalize ()
specifier|public
name|void
name|finalize
parameter_list|()
block|{
try|try
block|{
name|tmpFile
operator|.
name|delete
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Throwable
name|e
parameter_list|)
block|{
name|Globals
operator|.
name|logger
argument_list|(
literal|"Cannot delete temporary file '"
operator|+
name|tmpFile
operator|.
name|getPath
argument_list|()
operator|+
literal|"'"
argument_list|)
expr_stmt|;
block|}
block|}
block|}
DECL|method|getTempFile ()
specifier|static
specifier|synchronized
name|File
name|getTempFile
parameter_list|()
block|{
name|File
name|f
init|=
literal|null
decl_stmt|;
comment|// Globals.prefs.get("tempDir")
comment|//while ((f = File.createTempFile("jabref"+(tmpNum++), null)).exists());
try|try
block|{
name|f
operator|=
name|File
operator|.
name|createTempFile
argument_list|(
literal|"jabref"
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|f
operator|.
name|deleteOnExit
argument_list|()
expr_stmt|;
comment|//System.out.println(f.getPath());
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{
name|ex
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
return|return
name|f
return|;
block|}
block|}
end_class

end_unit

