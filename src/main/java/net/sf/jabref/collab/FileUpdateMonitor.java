begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

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
name|util
operator|.
name|FileUtil
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
name|Globals
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
comment|/**  * This thread monitors a set of files, each associated with a FileUpdateListener, for changes  * in the file's last modification time stamp. The  */
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
name|Log
name|LOGGER
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|FileUpdateMonitor
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|WAIT
specifier|private
specifier|static
specifier|final
name|int
name|WAIT
init|=
literal|4000
decl_stmt|;
DECL|field|numberOfUpdateListener
specifier|private
name|int
name|numberOfUpdateListener
init|=
literal|0
decl_stmt|;
DECL|field|entries
specifier|private
specifier|final
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
argument_list|<>
argument_list|()
decl_stmt|;
annotation|@
name|Override
DECL|method|run ()
specifier|public
name|void
name|run
parameter_list|()
block|{
comment|// The running variable is used to make the thread stop when needed.
while|while
condition|(
literal|true
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
block|{
name|e
operator|.
name|notifyListener
argument_list|()
expr_stmt|;
block|}
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
name|Thread
operator|.
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
block|{
name|LOGGER
operator|.
name|info
argument_list|(
literal|"FileUpdateMonitor has been interrupted. Terminating..."
argument_list|,
name|ex
argument_list|)
expr_stmt|;
return|return;
block|}
block|}
block|}
comment|/**      * Add a new file to monitor. Returns a handle for accessing the entry.      * @param ul FileUpdateListener The listener to notify when the file changes.      * @param file File The file to monitor.      * @throws IOException if the file does not exist.      */
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
if|if
condition|(
operator|!
name|file
operator|.
name|exists
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|IOException
argument_list|(
literal|"File not found"
argument_list|)
throw|;
block|}
name|numberOfUpdateListener
operator|++
expr_stmt|;
name|String
name|key
init|=
literal|""
operator|+
name|numberOfUpdateListener
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
block|{
return|return
literal|false
return|;
block|}
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
block|{
return|return;
block|}
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
comment|/**      * Removes a listener from the monitor.      * @param handle String The handle for the listener to remove.      */
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
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Entry not found"
argument_list|)
throw|;
block|}
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
comment|/**      * Method for getting the temporary file used for this database. The tempfile      * is used for comparison with the changed on-disk version.      * @param key String The handle for this monitor.      * @throws IllegalArgumentException If the handle doesn't correspond to an entry.      * @return File The temporary file.      */
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
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Entry not found"
argument_list|)
throw|;
block|}
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
comment|/**      * A class containing the File, the FileUpdateListener and the current time stamp for one file.      */
DECL|class|Entry
specifier|static
class|class
name|Entry
block|{
DECL|field|listener
specifier|final
name|FileUpdateListener
name|listener
decl_stmt|;
DECL|field|file
specifier|final
name|File
name|file
decl_stmt|;
DECL|field|tmpFile
specifier|final
name|File
name|tmpFile
decl_stmt|;
DECL|field|timeStamp
name|long
name|timeStamp
decl_stmt|;
DECL|field|fileSize
name|long
name|fileSize
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
name|fileSize
operator|=
name|file
operator|.
name|length
argument_list|()
expr_stmt|;
name|tmpFile
operator|=
name|FileUpdateMonitor
operator|.
name|getTempFile
argument_list|()
expr_stmt|;
name|tmpFile
operator|.
name|deleteOnExit
argument_list|()
expr_stmt|;
name|copy
argument_list|()
expr_stmt|;
block|}
comment|/**          * Check if time stamp or the file size has changed.          * @throws IOException if the file does no longer exist.          * @return boolean true if the file has changed.          */
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
name|long
name|fileSizeNow
init|=
name|file
operator|.
name|length
argument_list|()
decl_stmt|;
if|if
condition|(
name|modified
operator|==
literal|0L
condition|)
block|{
throw|throw
operator|new
name|IOException
argument_list|(
literal|"File deleted"
argument_list|)
throw|;
block|}
return|return
name|timeStamp
operator|!=
name|modified
operator|||
name|fileSize
operator|!=
name|fileSizeNow
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
block|{
name|notifyFileRemoved
argument_list|()
expr_stmt|;
block|}
name|fileSize
operator|=
name|file
operator|.
name|length
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
name|FileUtil
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
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Cannot copy to temporary file '"
operator|+
name|tmpFile
operator|.
name|getPath
argument_list|()
operator|+
literal|'\''
argument_list|,
name|ex
argument_list|)
expr_stmt|;
block|}
comment|//Util.pr("</copy>");
return|return
name|res
return|;
comment|//return true;
block|}
comment|/**          * Call the listener method to signal that the file has changed.          */
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
name|fileSize
operator|=
name|file
operator|.
name|length
argument_list|()
expr_stmt|;
name|listener
operator|.
name|fileUpdated
argument_list|()
expr_stmt|;
block|}
comment|/**          * Call the listener method to signal that the file has been removed.          */
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
comment|/*public void finalize() {           try {             tmpFile.delete();           } catch (Throwable e) {             Globals.logger("Cannot delete temporary file '"+tmpFile.getPath()+"'");           }         }*/
block|}
DECL|method|getTempFile ()
specifier|private
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

