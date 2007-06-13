begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.external
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|external
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
name|*
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
name|FileListEditor
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
name|FileListEntryEditor
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
name|FileListEntry
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
name|net
operator|.
name|URLDownload
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|*
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
name|net
operator|.
name|URL
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|MalformedURLException
import|;
end_import

begin_comment
comment|/**  * This class handles the download of an external file. Typically called when the user clicks  * the "Download" button in a FileListEditor shown in an EntryEditor.  *<p/>  * The FileListEditor constructs the DownloadExternalFile instance, then calls the download()  * method passing a reference to itself as a callback. The download() method asks for the URL,  * then starts the download. When the download is completed, it calls the downloadCompleted()  * method on the callback FileListEditor, which then needs to take care of linking to the file.  * The local filename is passed as an argument to the downloadCompleted() method.  *<p/>  * If the download is cancelled, or failed, the user is informed. The callback is never called.  */
end_comment

begin_class
DECL|class|DownloadExternalFile
specifier|public
class|class
name|DownloadExternalFile
block|{
DECL|field|frame
specifier|private
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|dialog
specifier|private
name|JDialog
name|dialog
decl_stmt|;
DECL|field|metaData
specifier|private
name|MetaData
name|metaData
decl_stmt|;
DECL|field|bibtexKey
specifier|private
name|String
name|bibtexKey
decl_stmt|;
DECL|field|editor
specifier|private
name|FileListEntryEditor
name|editor
decl_stmt|;
DECL|field|downloadFinished
specifier|private
name|boolean
name|downloadFinished
init|=
literal|false
decl_stmt|;
DECL|method|DownloadExternalFile (JabRefFrame frame, MetaData metaData, String bibtexKey)
specifier|public
name|DownloadExternalFile
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|MetaData
name|metaData
parameter_list|,
name|String
name|bibtexKey
parameter_list|)
block|{
name|this
operator|.
name|frame
operator|=
name|frame
expr_stmt|;
name|this
operator|.
name|metaData
operator|=
name|metaData
expr_stmt|;
name|this
operator|.
name|bibtexKey
operator|=
name|bibtexKey
expr_stmt|;
block|}
comment|/**      * Start a download.      *      * @param callback The object to which the filename should be reported when download      *                 is complete.      */
DECL|method|download (final DownloadCallback callback)
specifier|public
name|void
name|download
parameter_list|(
specifier|final
name|DownloadCallback
name|callback
parameter_list|)
throws|throws
name|IOException
block|{
specifier|final
name|String
name|res
init|=
name|JOptionPane
operator|.
name|showInputDialog
argument_list|(
name|frame
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Enter URL to download"
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|res
operator|==
literal|null
operator|||
name|res
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
return|return;
comment|// First of all, start the download itself in the background to a temporary file:
specifier|final
name|File
name|tmp
init|=
name|File
operator|.
name|createTempFile
argument_list|(
literal|"jabref_download"
argument_list|,
literal|"tmp"
argument_list|)
decl_stmt|;
name|tmp
operator|.
name|deleteOnExit
argument_list|()
expr_stmt|;
operator|(
operator|new
name|Thread
argument_list|()
block|{
specifier|public
name|void
name|run
parameter_list|()
block|{
try|try
block|{
name|URL
name|url
init|=
operator|new
name|URL
argument_list|(
name|res
argument_list|)
decl_stmt|;
name|URLDownload
name|udl
init|=
operator|new
name|URLDownload
argument_list|(
name|frame
argument_list|,
name|url
argument_list|,
name|tmp
argument_list|)
decl_stmt|;
try|try
block|{
name|udl
operator|.
name|download
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e2
parameter_list|)
block|{
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|frame
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Invalid URL: "
operator|+
name|e2
operator|.
name|getMessage
argument_list|()
argument_list|)
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Download file"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|logger
argument_list|(
literal|"Error while downloading "
operator|+
name|url
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
return|return;
block|}
comment|// Download finished: call the method that stops the progress bar etc.:
name|SwingUtilities
operator|.
name|invokeLater
argument_list|(
operator|new
name|Runnable
argument_list|()
block|{
specifier|public
name|void
name|run
parameter_list|()
block|{
name|downloadFinished
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|MalformedURLException
name|e1
parameter_list|)
block|{
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|frame
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Invalid URL"
argument_list|)
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Download file"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
block|}
block|}
block|}
operator|)
operator|.
name|start
argument_list|()
expr_stmt|;
comment|// Then, while the download is proceeding, let the user choose the details of the file:
name|String
name|suffix
init|=
name|getSuffix
argument_list|(
name|res
argument_list|)
decl_stmt|;
name|String
name|suggestedName
init|=
name|bibtexKey
operator|!=
literal|null
condition|?
name|getSuggestedFileName
argument_list|(
name|res
argument_list|,
name|suffix
argument_list|)
else|:
literal|""
decl_stmt|;
specifier|final
name|String
name|directory
init|=
name|getFileDirectory
argument_list|(
name|res
argument_list|)
decl_stmt|;
name|File
name|file
init|=
operator|new
name|File
argument_list|(
operator|new
name|File
argument_list|(
name|directory
argument_list|)
argument_list|,
name|suggestedName
argument_list|)
decl_stmt|;
name|FileListEntry
name|entry
init|=
operator|new
name|FileListEntry
argument_list|(
literal|""
argument_list|,
name|bibtexKey
operator|!=
literal|null
condition|?
name|file
operator|.
name|getPath
argument_list|()
else|:
literal|""
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getExternalFileTypeByExt
argument_list|(
name|suffix
argument_list|)
argument_list|)
decl_stmt|;
name|editor
operator|=
operator|new
name|FileListEntryEditor
argument_list|(
name|frame
argument_list|,
name|entry
argument_list|,
literal|true
argument_list|,
name|metaData
argument_list|)
expr_stmt|;
name|editor
operator|.
name|getProgressBar
argument_list|()
operator|.
name|setIndeterminate
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|editor
operator|.
name|setOkEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|editor
operator|.
name|setExternalConfirm
argument_list|(
operator|new
name|ConfirmCloseFileListEntryEditor
argument_list|()
block|{
specifier|public
name|boolean
name|confirmClose
parameter_list|(
name|FileListEntry
name|entry
parameter_list|)
block|{
name|File
name|f
init|=
name|expandFilename
argument_list|(
name|directory
argument_list|,
name|entry
operator|.
name|getLink
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|f
operator|.
name|isDirectory
argument_list|()
condition|)
block|{
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|frame
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Target file cannot be a directory."
argument_list|)
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Download file"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
return|return
literal|false
return|;
block|}
if|if
condition|(
name|f
operator|.
name|exists
argument_list|()
condition|)
block|{
return|return
name|JOptionPane
operator|.
name|showConfirmDialog
argument_list|(
name|frame
argument_list|,
literal|"'"
operator|+
name|f
operator|.
name|getName
argument_list|()
operator|+
literal|"' "
operator|+
name|Globals
operator|.
name|lang
argument_list|(
literal|"exists. Overwrite file?"
argument_list|)
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Download file"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|OK_CANCEL_OPTION
argument_list|)
operator|==
name|JOptionPane
operator|.
name|OK_OPTION
return|;
block|}
else|else
return|return
literal|true
return|;
block|}
block|}
argument_list|)
expr_stmt|;
name|editor
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
comment|// Editor closed. Go on:
if|if
condition|(
name|editor
operator|.
name|okPressed
argument_list|()
condition|)
block|{
name|String
name|dirPrefix
init|=
name|directory
operator|+
name|System
operator|.
name|getProperty
argument_list|(
literal|"file.separator"
argument_list|)
decl_stmt|;
name|File
name|toFile
init|=
name|expandFilename
argument_list|(
name|directory
argument_list|,
name|entry
operator|.
name|getLink
argument_list|()
argument_list|)
decl_stmt|;
try|try
block|{
name|boolean
name|success
init|=
name|Util
operator|.
name|copyFile
argument_list|(
name|tmp
argument_list|,
name|toFile
argument_list|,
literal|true
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|success
condition|)
block|{
comment|// OOps, the file exists!
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"File already exists! DownloadExternalFile.download()"
argument_list|)
expr_stmt|;
block|}
comment|// If the local file is in or below the main file directory, change the
comment|// path to relative:
if|if
condition|(
name|entry
operator|.
name|getLink
argument_list|()
operator|.
name|startsWith
argument_list|(
name|directory
argument_list|)
operator|&&
operator|(
name|entry
operator|.
name|getLink
argument_list|()
operator|.
name|length
argument_list|()
operator|>
name|dirPrefix
operator|.
name|length
argument_list|()
operator|)
condition|)
block|{
name|entry
operator|.
name|setLink
argument_list|(
name|entry
operator|.
name|getLink
argument_list|()
operator|.
name|substring
argument_list|(
name|dirPrefix
operator|.
name|length
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|callback
operator|.
name|downloadComplete
argument_list|(
name|entry
argument_list|)
expr_stmt|;
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
name|tmp
operator|.
name|delete
argument_list|()
expr_stmt|;
block|}
else|else
block|{
comment|// Cancelled. Just delete the temp file:
if|if
condition|(
name|downloadFinished
condition|)
name|tmp
operator|.
name|delete
argument_list|()
expr_stmt|;
block|}
block|}
comment|/**      * Construct a File object pointing to the file linked, whether the link is      * absolute or relative to the main directory.      * @param directory The main directory.      * @param link The absolute or relative link.      * @return The expanded File.      */
DECL|method|expandFilename (String directory, String link)
specifier|private
name|File
name|expandFilename
parameter_list|(
name|String
name|directory
parameter_list|,
name|String
name|link
parameter_list|)
block|{
name|File
name|toFile
init|=
operator|new
name|File
argument_list|(
name|link
argument_list|)
decl_stmt|;
comment|// If this is a relative link, we should perhaps append the directory:
name|String
name|dirPrefix
init|=
name|directory
operator|+
name|System
operator|.
name|getProperty
argument_list|(
literal|"file.separator"
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|toFile
operator|.
name|isAbsolute
argument_list|()
condition|)
block|{
name|toFile
operator|=
operator|new
name|File
argument_list|(
name|dirPrefix
operator|+
name|link
argument_list|)
expr_stmt|;
block|}
return|return
name|toFile
return|;
block|}
comment|/**      * This is called by the download thread when download is completed.      */
DECL|method|downloadFinished ()
specifier|public
name|void
name|downloadFinished
parameter_list|()
block|{
name|downloadFinished
operator|=
literal|true
expr_stmt|;
name|editor
operator|.
name|getProgressBar
argument_list|()
operator|.
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|editor
operator|.
name|getProgressBarLabel
argument_list|()
operator|.
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|editor
operator|.
name|setOkEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|editor
operator|.
name|getProgressBar
argument_list|()
operator|.
name|setValue
argument_list|(
name|editor
operator|.
name|getProgressBar
argument_list|()
operator|.
name|getMaximum
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|getSuggestedFileName (String res, String suffix)
specifier|public
name|String
name|getSuggestedFileName
parameter_list|(
name|String
name|res
parameter_list|,
name|String
name|suffix
parameter_list|)
block|{
if|if
condition|(
name|suffix
operator|==
literal|null
condition|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Link has no obvious extension (DownloadExternalFile.download()"
argument_list|)
expr_stmt|;
block|}
name|String
name|plannedName
init|=
name|bibtexKey
operator|+
literal|"."
operator|+
name|suffix
decl_stmt|;
comment|/*         * [ 1548875 ] download pdf produces unsupported filename         *         * http://sourceforge.net/tracker/index.php?func=detail&aid=1548875&group_id=92314&atid=600306         *         */
if|if
condition|(
name|Globals
operator|.
name|ON_WIN
condition|)
block|{
name|plannedName
operator|=
name|plannedName
operator|.
name|replaceAll
argument_list|(
literal|"\\?|\\*|\\<|\\>|\\||\\\"|\\:|\\.$|\\[|\\]"
argument_list|,
literal|""
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|Globals
operator|.
name|ON_MAC
condition|)
block|{
name|plannedName
operator|=
name|plannedName
operator|.
name|replaceAll
argument_list|(
literal|":"
argument_list|,
literal|""
argument_list|)
expr_stmt|;
block|}
return|return
name|plannedName
return|;
block|}
comment|/**      * Look for the last '.' in the link, and returnthe following characters.      * This gives the extension for most reasonably named links.      *      * @param link The link      * @return The suffix, excluding the dot (e.g. ".pdf")      */
DECL|method|getSuffix (String link)
specifier|public
name|String
name|getSuffix
parameter_list|(
name|String
name|link
parameter_list|)
block|{
name|int
name|index
init|=
name|link
operator|.
name|lastIndexOf
argument_list|(
literal|'.'
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|index
operator|<=
literal|0
operator|)
operator|||
operator|(
name|index
operator|==
name|link
operator|.
name|length
argument_list|()
operator|-
literal|1
operator|)
condition|)
comment|// No occurence, or at the end
return|return
literal|null
return|;
return|return
name|link
operator|.
name|substring
argument_list|(
name|index
operator|+
literal|1
argument_list|)
return|;
block|}
DECL|method|getFileDirectory (String link)
specifier|public
name|String
name|getFileDirectory
parameter_list|(
name|String
name|link
parameter_list|)
block|{
comment|// TODO: getFileDirectory()
return|return
name|metaData
operator|.
name|getFileDirectory
argument_list|(
name|GUIGlobals
operator|.
name|FILE_FIELD
argument_list|)
return|;
block|}
comment|/**      * Callback interface that users of this class must implement in order to receive      * notification when download is complete.      */
DECL|interface|DownloadCallback
specifier|public
interface|interface
name|DownloadCallback
block|{
DECL|method|downloadComplete (FileListEntry file)
specifier|public
name|void
name|downloadComplete
parameter_list|(
name|FileListEntry
name|file
parameter_list|)
function_decl|;
block|}
block|}
end_class

end_unit

