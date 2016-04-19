begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

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
name|MalformedURLException
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
name|util
operator|.
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Optional
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JOptionPane
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|SwingUtilities
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
name|JabRefExecutorService
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
name|JabRefFrame
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
name|net
operator|.
name|MonitoredURLDownload
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
name|logic
operator|.
name|l10n
operator|.
name|Localization
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
name|logic
operator|.
name|net
operator|.
name|URLDownload
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
name|logic
operator|.
name|util
operator|.
name|OS
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
name|logic
operator|.
name|util
operator|.
name|io
operator|.
name|FileUtil
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
comment|/**  * This class handles the download of an external file. Typically called when the user clicks  * the "Download" button in a FileListEditor shown in an EntryEditor.  *<p/>  * The FileListEditor constructs the DownloadExternalFile instance, then calls the download()  * method passing a reference to itself as a callback. The download() method asks for the URL,  * then starts the download. When the download is completed, it calls the downloadCompleted()  * method on the callback FileListEditor, which then needs to take care of linking to the file.  * The local filename is passed as an argument to the downloadCompleted() method.  *<p/>  * If the download is canceled, or failed, the user is informed. The callback is never called.  */
end_comment

begin_class
DECL|class|DownloadExternalFile
specifier|public
class|class
name|DownloadExternalFile
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
name|DownloadExternalFile
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|frame
specifier|private
specifier|final
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|databaseContext
specifier|private
specifier|final
name|BibDatabaseContext
name|databaseContext
decl_stmt|;
DECL|field|bibtexKey
specifier|private
specifier|final
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
decl_stmt|;
DECL|field|dontShowDialog
specifier|private
name|boolean
name|dontShowDialog
decl_stmt|;
DECL|method|DownloadExternalFile (JabRefFrame frame, BibDatabaseContext databaseContext, String bibtexKey)
specifier|public
name|DownloadExternalFile
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|BibDatabaseContext
name|databaseContext
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
name|databaseContext
operator|=
name|databaseContext
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
name|dontShowDialog
operator|=
literal|false
expr_stmt|;
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
name|Localization
operator|.
name|lang
argument_list|(
literal|"Enter URL to download"
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|res
operator|==
literal|null
operator|)
operator|||
name|res
operator|.
name|trim
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return;
block|}
name|URL
name|url
decl_stmt|;
try|try
block|{
name|url
operator|=
operator|new
name|URL
argument_list|(
name|res
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|MalformedURLException
name|ex1
parameter_list|)
block|{
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|frame
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Invalid URL"
argument_list|)
argument_list|,
name|Localization
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
return|return;
block|}
name|download
argument_list|(
name|url
argument_list|,
name|callback
argument_list|)
expr_stmt|;
block|}
comment|/**      * Start a download.      *      * @param callback The object to which the filename should be reported when download      *                 is complete.      */
DECL|method|download (URL url, final DownloadCallback callback)
specifier|public
name|void
name|download
parameter_list|(
name|URL
name|url
parameter_list|,
specifier|final
name|DownloadCallback
name|callback
parameter_list|)
throws|throws
name|IOException
block|{
name|String
name|res
init|=
name|url
operator|.
name|toString
argument_list|()
decl_stmt|;
name|String
name|mimeType
decl_stmt|;
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
name|URLDownload
name|udl
init|=
name|MonitoredURLDownload
operator|.
name|buildMonitoredDownload
argument_list|(
name|frame
argument_list|,
name|url
argument_list|)
decl_stmt|;
try|try
block|{
comment|// TODO: what if this takes long time?
comment|// TODO: stop editor dialog if this results in an error:
name|mimeType
operator|=
name|udl
operator|.
name|determineMimeType
argument_list|()
expr_stmt|;
comment|// Read MIME type
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|frame
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Invalid URL"
argument_list|)
operator|+
literal|": "
operator|+
name|ex
operator|.
name|getMessage
argument_list|()
argument_list|,
name|Localization
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
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Error while downloading "
operator|+
literal|"'"
operator|+
name|res
operator|+
literal|"'"
argument_list|,
name|ex
argument_list|)
expr_stmt|;
return|return;
block|}
specifier|final
name|URL
name|urlF
init|=
name|url
decl_stmt|;
specifier|final
name|URLDownload
name|udlF
init|=
name|udl
decl_stmt|;
name|JabRefExecutorService
operator|.
name|INSTANCE
operator|.
name|execute
argument_list|(
call|(
name|Runnable
call|)
argument_list|()
operator|->
block|{
try|try
block|{
name|udlF
operator|.
name|downloadToFile
argument_list|(
name|tmp
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e2
parameter_list|)
block|{
name|dontShowDialog
operator|=
literal|true
expr_stmt|;
if|if
condition|(
operator|(
name|editor
operator|!=
literal|null
operator|)
operator|&&
name|editor
operator|.
name|isVisible
argument_list|()
condition|)
block|{
name|editor
operator|.
name|setVisible
argument_list|(
literal|false
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|frame
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Invalid URL"
argument_list|)
operator|+
literal|": "
operator|+
name|e2
operator|.
name|getMessage
argument_list|()
argument_list|,
name|Localization
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
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Error while downloading "
operator|+
literal|"'"
operator|+
name|urlF
operator|+
literal|"'"
argument_list|,
name|e2
argument_list|)
expr_stmt|;
return|return;
block|}
comment|// Download finished: call the method that stops the progress bar etc.:
name|SwingUtilities
operator|.
name|invokeLater
argument_list|(
name|DownloadExternalFile
operator|.
name|this
operator|::
name|downloadFinished
argument_list|)
expr_stmt|;
block|}
block|)
class|;
end_class

begin_decl_stmt
name|Optional
argument_list|<
name|ExternalFileType
argument_list|>
name|suggestedType
init|=
name|Optional
operator|.
name|empty
argument_list|()
decl_stmt|;
end_decl_stmt

begin_if
if|if
condition|(
name|mimeType
operator|!=
literal|null
condition|)
block|{
name|LOGGER
operator|.
name|debug
argument_list|(
literal|"MIME Type suggested: "
operator|+
name|mimeType
argument_list|)
expr_stmt|;
name|suggestedType
operator|=
name|ExternalFileTypes
operator|.
name|getInstance
argument_list|()
operator|.
name|getExternalFileTypeByMimeType
argument_list|(
name|mimeType
argument_list|)
expr_stmt|;
block|}
end_if

begin_comment
comment|// Then, while the download is proceeding, let the user choose the details of the file:
end_comment

begin_decl_stmt
name|String
name|suffix
decl_stmt|;
end_decl_stmt

begin_if
if|if
condition|(
name|suggestedType
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|suffix
operator|=
name|suggestedType
operator|.
name|get
argument_list|()
operator|.
name|getExtension
argument_list|()
expr_stmt|;
block|}
else|else
block|{
comment|// If we didn't find a file type from the MIME type, try based on extension:
name|suffix
operator|=
name|getSuffix
argument_list|(
name|res
argument_list|)
expr_stmt|;
if|if
condition|(
name|suffix
operator|==
literal|null
condition|)
block|{
name|suffix
operator|=
literal|""
expr_stmt|;
block|}
name|suggestedType
operator|=
name|ExternalFileTypes
operator|.
name|getInstance
argument_list|()
operator|.
name|getExternalFileTypeByExt
argument_list|(
name|suffix
argument_list|)
expr_stmt|;
block|}
end_if

begin_decl_stmt
name|String
name|suggestedName
init|=
name|getSuggestedFileName
argument_list|(
name|suffix
argument_list|)
decl_stmt|;
end_decl_stmt

begin_decl_stmt
name|List
argument_list|<
name|String
argument_list|>
name|fDirectory
init|=
name|databaseContext
operator|.
name|getFileDirectory
argument_list|()
decl_stmt|;
end_decl_stmt

begin_decl_stmt
name|String
name|directory
decl_stmt|;
end_decl_stmt

begin_if
if|if
condition|(
name|fDirectory
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|directory
operator|=
literal|null
expr_stmt|;
block|}
else|else
block|{
name|directory
operator|=
name|fDirectory
operator|.
name|get
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
end_if

begin_decl_stmt
specifier|final
name|String
name|suggestDir
init|=
name|directory
operator|==
literal|null
condition|?
name|System
operator|.
name|getProperty
argument_list|(
literal|"user.home"
argument_list|)
else|:
name|directory
decl_stmt|;
end_decl_stmt

begin_decl_stmt
name|File
name|file
init|=
operator|new
name|File
argument_list|(
operator|new
name|File
argument_list|(
name|suggestDir
argument_list|)
argument_list|,
name|suggestedName
argument_list|)
decl_stmt|;
end_decl_stmt

begin_decl_stmt
name|FileListEntry
name|entry
init|=
operator|new
name|FileListEntry
argument_list|(
literal|""
argument_list|,
name|file
operator|.
name|getCanonicalPath
argument_list|()
argument_list|,
name|suggestedType
argument_list|)
decl_stmt|;
end_decl_stmt

begin_expr_stmt
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
literal|false
argument_list|,
name|databaseContext
argument_list|)
expr_stmt|;
end_expr_stmt

begin_expr_stmt
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
end_expr_stmt

begin_expr_stmt
name|editor
operator|.
name|setOkEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
end_expr_stmt

begin_expr_stmt
name|editor
operator|.
name|setExternalConfirm
argument_list|(
name|closeEntry
lambda|->
block|{
name|File
name|f
init|=
name|directory
operator|==
literal|null
condition|?
operator|new
name|File
argument_list|(
name|closeEntry
operator|.
name|link
argument_list|)
else|:
name|expandFilename
argument_list|(
name|directory
argument_list|,
name|closeEntry
operator|.
name|link
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
name|Localization
operator|.
name|lang
argument_list|(
literal|"Target file cannot be a directory."
argument_list|)
argument_list|,
name|Localization
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
name|Localization
operator|.
name|lang
argument_list|(
literal|"'%0' exists. Overwrite file?"
argument_list|,
name|f
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|,
name|Localization
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
block|{
return|return
literal|true
return|;
block|}
block|}
argument_list|)
expr_stmt|;
end_expr_stmt

begin_if
if|if
condition|(
name|dontShowDialog
condition|)
block|{
return|return;
block|}
else|else
block|{
name|editor
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
end_if

begin_comment
comment|// Editor closed. Go on:
end_comment

begin_if
if|if
condition|(
name|editor
operator|.
name|okPressed
argument_list|()
condition|)
block|{
name|File
name|toFile
init|=
name|directory
operator|==
literal|null
condition|?
operator|new
name|File
argument_list|(
name|entry
operator|.
name|link
argument_list|)
else|:
name|expandFilename
argument_list|(
name|directory
argument_list|,
name|entry
operator|.
name|link
argument_list|)
decl_stmt|;
name|String
name|dirPrefix
decl_stmt|;
if|if
condition|(
name|directory
operator|==
literal|null
condition|)
block|{
name|dirPrefix
operator|=
literal|null
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|directory
operator|.
name|endsWith
argument_list|(
name|System
operator|.
name|getProperty
argument_list|(
literal|"file.separator"
argument_list|)
argument_list|)
condition|)
block|{
name|dirPrefix
operator|=
name|directory
expr_stmt|;
block|}
else|else
block|{
name|dirPrefix
operator|=
name|directory
operator|+
name|System
operator|.
name|getProperty
argument_list|(
literal|"file.separator"
argument_list|)
expr_stmt|;
block|}
block|}
try|try
block|{
name|boolean
name|success
init|=
name|FileUtil
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
name|LOGGER
operator|.
name|error
argument_list|(
literal|"File already exists! DownloadExternalFile.download()"
argument_list|)
expr_stmt|;
block|}
comment|// If the local file is in or below the main file directory, change the
comment|// path to relative:
if|if
condition|(
operator|(
name|directory
operator|!=
literal|null
operator|)
operator|&&
name|entry
operator|.
name|link
operator|.
name|startsWith
argument_list|(
name|directory
argument_list|)
operator|&&
operator|(
name|entry
operator|.
name|link
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
operator|=
operator|new
name|FileListEntry
argument_list|(
name|entry
operator|.
name|description
argument_list|,
name|entry
operator|.
name|link
operator|.
name|substring
argument_list|(
name|dirPrefix
operator|.
name|length
argument_list|()
argument_list|)
argument_list|,
name|entry
operator|.
name|type
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
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Problem downloading file"
argument_list|,
name|ex
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|tmp
operator|.
name|delete
argument_list|()
condition|)
block|{
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Cannot delete temporary file"
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
comment|// Canceled. Just delete the temp file:
if|if
condition|(
name|downloadFinished
operator|&&
operator|!
name|tmp
operator|.
name|delete
argument_list|()
condition|)
block|{
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Cannot delete temporary file"
argument_list|)
expr_stmt|;
block|}
block|}
end_if

begin_comment
unit|}
comment|/**      * Construct a File object pointing to the file linked, whether the link is      * absolute or relative to the main directory.      *      * @param directory The main directory.      * @param link      The absolute or relative link.      * @return The expanded File.      */
end_comment

begin_function
DECL|method|expandFilename (String directory, String link)
unit|private
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
end_function

begin_comment
comment|/**      * This is called by the download thread when download is completed.      */
end_comment

begin_function
DECL|method|downloadFinished ()
specifier|private
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
end_function

begin_comment
comment|// FIXME: will break download if no bibtexkey is present!
end_comment

begin_function
DECL|method|getSuggestedFileName (String suffix)
specifier|private
name|String
name|getSuggestedFileName
parameter_list|(
name|String
name|suffix
parameter_list|)
block|{
name|String
name|plannedName
init|=
name|bibtexKey
operator|==
literal|null
condition|?
literal|"set-filename"
else|:
name|bibtexKey
decl_stmt|;
if|if
condition|(
operator|!
name|suffix
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|plannedName
operator|+=
literal|"."
operator|+
name|suffix
expr_stmt|;
block|}
comment|/*         * [ 1548875 ] download pdf produces unsupported filename         *         * http://sourceforge.net/tracker/index.php?func=detail&aid=1548875&group_id=92314&atid=600306         * FIXME: rework this! just allow alphanumeric stuff or so?         * https://msdn.microsoft.com/en-us/library/windows/desktop/aa365247(v=vs.85).aspx#naming_conventions         * http://superuser.com/questions/358855/what-characters-are-safe-in-cross-platform-file-names-for-linux-windows-and-os         * https://support.apple.com/en-us/HT202808         */
if|if
condition|(
name|OS
operator|.
name|WINDOWS
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
name|OS
operator|.
name|OS_X
condition|)
block|{
name|plannedName
operator|=
name|plannedName
operator|.
name|replace
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
end_function

begin_comment
comment|/**      * Look for the last '.' in the link, and return the following characters.      * This gives the extension for most reasonably named links.      *      * @param link The link      * @return The suffix, excluding the dot (e.g. "pdf")      */
end_comment

begin_function
DECL|method|getSuffix (final String link)
specifier|private
name|String
name|getSuffix
parameter_list|(
specifier|final
name|String
name|link
parameter_list|)
block|{
name|String
name|strippedLink
init|=
name|link
decl_stmt|;
try|try
block|{
comment|// Try to strip the query string, if any, to get the correct suffix:
name|URL
name|url
init|=
operator|new
name|URL
argument_list|(
name|link
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|url
operator|.
name|getQuery
argument_list|()
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|url
operator|.
name|getQuery
argument_list|()
operator|.
name|length
argument_list|()
operator|<
operator|(
name|link
operator|.
name|length
argument_list|()
operator|-
literal|1
operator|)
operator|)
condition|)
block|{
name|strippedLink
operator|=
name|link
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|link
operator|.
name|length
argument_list|()
operator|-
name|url
operator|.
name|getQuery
argument_list|()
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|MalformedURLException
name|e
parameter_list|)
block|{
comment|// Don't report this error, since this getting the suffix is a non-critical
comment|// operation, and this error will be triggered and reported elsewhere.
block|}
comment|// First see if the stripped link gives a reasonable suffix:
name|String
name|suffix
decl_stmt|;
name|int
name|strippedLinkIndex
init|=
name|strippedLink
operator|.
name|lastIndexOf
argument_list|(
literal|'.'
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|strippedLinkIndex
operator|<=
literal|0
operator|)
operator|||
operator|(
name|strippedLinkIndex
operator|==
operator|(
name|strippedLink
operator|.
name|length
argument_list|()
operator|-
literal|1
operator|)
operator|)
condition|)
block|{
name|suffix
operator|=
literal|null
expr_stmt|;
block|}
else|else
block|{
name|suffix
operator|=
name|strippedLink
operator|.
name|substring
argument_list|(
name|strippedLinkIndex
operator|+
literal|1
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|ExternalFileTypes
operator|.
name|getInstance
argument_list|()
operator|.
name|isExternalFileTypeByExt
argument_list|(
name|suffix
argument_list|)
condition|)
block|{
comment|// If the suffix doesn't seem to give any reasonable file type, try
comment|// with the non-stripped link:
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
operator|(
name|link
operator|.
name|length
argument_list|()
operator|-
literal|1
operator|)
operator|)
condition|)
block|{
comment|// No occurrence, or at the end
comment|// Check if there are path separators in the suffix - if so, it is definitely
comment|// not a proper suffix, so we should give up:
if|if
condition|(
name|strippedLink
operator|.
name|substring
argument_list|(
name|strippedLinkIndex
operator|+
literal|1
argument_list|)
operator|.
name|indexOf
argument_list|(
literal|'/'
argument_list|)
operator|>=
literal|1
condition|)
block|{
return|return
literal|""
return|;
block|}
else|else
block|{
return|return
name|suffix
return|;
comment|// return the first one we found, anyway.
block|}
block|}
else|else
block|{
comment|// Check if there are path separators in the suffix - if so, it is definitely
comment|// not a proper suffix, so we should give up:
if|if
condition|(
name|link
operator|.
name|substring
argument_list|(
name|index
operator|+
literal|1
argument_list|)
operator|.
name|indexOf
argument_list|(
literal|'/'
argument_list|)
operator|>=
literal|1
condition|)
block|{
return|return
literal|""
return|;
block|}
else|else
block|{
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
block|}
block|}
else|else
block|{
return|return
name|suffix
return|;
block|}
block|}
end_function

begin_comment
comment|/**      * Callback interface that users of this class must implement in order to receive      * notification when download is complete.      */
end_comment

begin_interface
annotation|@
name|FunctionalInterface
DECL|interface|DownloadCallback
specifier|public
interface|interface
name|DownloadCallback
block|{
DECL|method|downloadComplete (FileListEntry file)
name|void
name|downloadComplete
parameter_list|(
name|FileListEntry
name|file
parameter_list|)
function_decl|;
block|}
end_interface

unit|}
end_unit

