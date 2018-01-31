begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.model.util
package|package
name|org
operator|.
name|jabref
operator|.
name|model
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
name|Path
import|;
end_import

begin_interface
DECL|interface|FileUpdateMonitor
specifier|public
interface|interface
name|FileUpdateMonitor
block|{
comment|/**      * Add a new file to monitor.      *      * @param file The file to monitor.      * @throws IOException if the file does not exist.      */
DECL|method|addListenerForFile (Path file, FileUpdateListener listener)
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
function_decl|;
comment|/**      * Removes a listener from the monitor.      *      * @param path The path to remove.      */
DECL|method|removeListener (Path path, FileUpdateListener listener)
name|void
name|removeListener
parameter_list|(
name|Path
name|path
parameter_list|,
name|FileUpdateListener
name|listener
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

