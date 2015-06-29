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
name|awt
operator|.
name|BorderLayout
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|ActionEvent
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|ActionListener
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
name|*
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JDialog
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JProgressBar
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
name|FileListTableModel
import|;
end_import

begin_comment
comment|/**  * This class handles the task of looking up all external files linked for a set  * of entries. This is useful for tasks where the user wants e.g. to send a database  * with external files included.  */
end_comment

begin_class
DECL|class|AccessLinksForEntries
specifier|public
class|class
name|AccessLinksForEntries
block|{
comment|/**      * Look up all external files linked from (at least) one of the entries in a set.      * This method does not verify the links.      *      * @param entries The set of entries.      * @return A list of FileListEntry objects pointing to the external files.      */
DECL|method|getExternalLinksForEntries (List<BibtexEntry> entries)
specifier|private
specifier|static
name|List
argument_list|<
name|FileListEntry
argument_list|>
name|getExternalLinksForEntries
parameter_list|(
name|List
argument_list|<
name|BibtexEntry
argument_list|>
name|entries
parameter_list|)
block|{
name|List
argument_list|<
name|FileListEntry
argument_list|>
name|files
init|=
operator|new
name|ArrayList
argument_list|<
name|FileListEntry
argument_list|>
argument_list|()
decl_stmt|;
name|FileListTableModel
name|model
init|=
operator|new
name|FileListTableModel
argument_list|()
decl_stmt|;
for|for
control|(
name|BibtexEntry
name|entry
range|:
name|entries
control|)
block|{
name|String
name|links
init|=
name|entry
operator|.
name|getField
argument_list|(
name|GUIGlobals
operator|.
name|FILE_FIELD
argument_list|)
decl_stmt|;
if|if
condition|(
name|links
operator|==
literal|null
condition|)
block|{
continue|continue;
block|}
name|model
operator|.
name|setContent
argument_list|(
name|links
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|model
operator|.
name|getRowCount
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|files
operator|.
name|add
argument_list|(
name|model
operator|.
name|getEntry
argument_list|(
name|i
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|files
return|;
block|}
comment|/**      * Take a list of external links and copy the referred files to a given directory.      * This method should be run off the Event Dispatch Thread. A progress bar, if given,      * will be updated on the EDT.      *      * @param files The list of file links.      * @param toDir The directory to copy the files to.      * @param metaData The MetaData for the database containing the external links. This is needed      *  because the database might have its own file directory.      * @param prog A JProgressBar which will be updated to show the progress of the process.      *  This argument can be null if no progress bar is needed.      * @param deleteOriginalFiles if true, the files in their original locations will be deleted      *  after copying, for each file whose source directory is different from the destination      *  directory differs.      * @param callback An ActionListener which should be notified when the process is finished.      *  This parameter can be null if no callback is needed.      */
DECL|method|copyExternalLinksToDirectory (final List<FileListEntry> files, File toDir, MetaData metaData, final JProgressBar prog, boolean deleteOriginalFiles, final ActionListener callback)
specifier|private
specifier|static
name|void
name|copyExternalLinksToDirectory
parameter_list|(
specifier|final
name|List
argument_list|<
name|FileListEntry
argument_list|>
name|files
parameter_list|,
name|File
name|toDir
parameter_list|,
name|MetaData
name|metaData
parameter_list|,
specifier|final
name|JProgressBar
name|prog
parameter_list|,
name|boolean
name|deleteOriginalFiles
parameter_list|,
specifier|final
name|ActionListener
name|callback
parameter_list|)
block|{
if|if
condition|(
name|prog
operator|!=
literal|null
condition|)
block|{
name|SwingUtilities
operator|.
name|invokeLater
argument_list|(
operator|new
name|Runnable
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|run
parameter_list|()
block|{
name|prog
operator|.
name|setMaximum
argument_list|(
name|files
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|prog
operator|.
name|setValue
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|prog
operator|.
name|setIndeterminate
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
name|Set
argument_list|<
name|String
argument_list|>
name|fileNames
init|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|int
name|i
init|=
literal|0
decl_stmt|;
for|for
control|(
name|FileListEntry
name|entry
range|:
name|files
control|)
block|{
name|File
name|file
init|=
operator|new
name|File
argument_list|(
name|entry
operator|.
name|getLink
argument_list|()
argument_list|)
decl_stmt|;
comment|// We try to check the extension for the file:
name|String
name|name
init|=
name|file
operator|.
name|getName
argument_list|()
decl_stmt|;
name|int
name|pos
init|=
name|name
operator|.
name|lastIndexOf
argument_list|(
literal|'.'
argument_list|)
decl_stmt|;
name|String
name|extension
init|=
operator|(
operator|(
name|pos
operator|>=
literal|0
operator|)
operator|&&
operator|(
name|pos
operator|<
operator|(
name|name
operator|.
name|length
argument_list|()
operator|-
literal|1
operator|)
operator|)
operator|)
condition|?
name|name
operator|.
name|substring
argument_list|(
name|pos
operator|+
literal|1
argument_list|)
operator|.
name|trim
argument_list|()
operator|.
name|toLowerCase
argument_list|()
else|:
literal|null
decl_stmt|;
comment|// Find the default directory for this field type, if any:
name|String
index|[]
name|dir
init|=
name|metaData
operator|.
name|getFileDirectory
argument_list|(
name|extension
argument_list|)
decl_stmt|;
comment|// Include the standard "file" directory:
name|String
index|[]
name|fileDir
init|=
name|metaData
operator|.
name|getFileDirectory
argument_list|(
name|GUIGlobals
operator|.
name|FILE_FIELD
argument_list|)
decl_stmt|;
comment|// Include the directory of the bib file:
name|ArrayList
argument_list|<
name|String
argument_list|>
name|al
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|aDir
range|:
name|dir
control|)
block|{
if|if
condition|(
operator|!
name|al
operator|.
name|contains
argument_list|(
name|aDir
argument_list|)
condition|)
block|{
name|al
operator|.
name|add
argument_list|(
name|aDir
argument_list|)
expr_stmt|;
block|}
block|}
for|for
control|(
name|String
name|aFileDir
range|:
name|fileDir
control|)
block|{
if|if
condition|(
operator|!
name|al
operator|.
name|contains
argument_list|(
name|aFileDir
argument_list|)
condition|)
block|{
name|al
operator|.
name|add
argument_list|(
name|aFileDir
argument_list|)
expr_stmt|;
block|}
block|}
name|String
index|[]
name|dirs
init|=
name|al
operator|.
name|toArray
argument_list|(
operator|new
name|String
index|[
name|al
operator|.
name|size
argument_list|()
index|]
argument_list|)
decl_stmt|;
name|File
name|tmp
init|=
name|FileUtil
operator|.
name|expandFilename
argument_list|(
name|entry
operator|.
name|getLink
argument_list|()
argument_list|,
name|dirs
argument_list|)
decl_stmt|;
if|if
condition|(
name|tmp
operator|!=
literal|null
condition|)
block|{
name|file
operator|=
name|tmp
expr_stmt|;
block|}
comment|// Check if we have arrived at an existing file:
if|if
condition|(
name|file
operator|.
name|exists
argument_list|()
condition|)
block|{
if|if
condition|(
name|fileNames
operator|.
name|contains
argument_list|(
name|name
argument_list|)
condition|)
block|{
comment|// Oops, a file of that name already exists....
block|}
else|else
block|{
name|fileNames
operator|.
name|add
argument_list|(
name|name
argument_list|)
expr_stmt|;
name|File
name|destination
init|=
operator|new
name|File
argument_list|(
name|toDir
argument_list|,
name|name
argument_list|)
decl_stmt|;
comment|// Check if the source and destination locations differ:
if|if
condition|(
operator|!
name|destination
operator|.
name|equals
argument_list|(
name|file
argument_list|)
condition|)
block|{
try|try
block|{
comment|// Copy the file:
name|FileUtil
operator|.
name|copyFile
argument_list|(
name|file
argument_list|,
name|destination
argument_list|,
literal|false
argument_list|)
expr_stmt|;
comment|// Delete the original file if requested:
if|if
condition|(
name|deleteOriginalFiles
condition|)
block|{
name|file
operator|.
name|delete
argument_list|()
expr_stmt|;
block|}
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
block|}
else|else
block|{
comment|// Destination and source is the same. Do nothing.
block|}
comment|// Update progress bar:
name|i
operator|++
expr_stmt|;
specifier|final
name|int
name|j
init|=
name|i
decl_stmt|;
if|if
condition|(
name|prog
operator|!=
literal|null
condition|)
block|{
name|SwingUtilities
operator|.
name|invokeLater
argument_list|(
operator|new
name|Runnable
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|run
parameter_list|()
block|{
name|prog
operator|.
name|setValue
argument_list|(
name|j
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
block|}
block|}
else|else
block|{
comment|// The link could not be resolved to an existing file.
block|}
block|}
if|if
condition|(
name|callback
operator|!=
literal|null
condition|)
block|{
name|callback
operator|.
name|actionPerformed
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
block|}
DECL|class|SaveWithLinkedFiles
specifier|public
specifier|static
class|class
name|SaveWithLinkedFiles
implements|implements
name|BaseAction
block|{
DECL|field|panel
specifier|private
specifier|final
name|BasePanel
name|panel
decl_stmt|;
DECL|method|SaveWithLinkedFiles (BasePanel panel)
specifier|public
name|SaveWithLinkedFiles
parameter_list|(
name|BasePanel
name|panel
parameter_list|)
block|{
name|this
operator|.
name|panel
operator|=
name|panel
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|action ()
specifier|public
name|void
name|action
parameter_list|()
throws|throws
name|Throwable
block|{
name|ArrayList
argument_list|<
name|BibtexEntry
argument_list|>
name|entries
init|=
operator|new
name|ArrayList
argument_list|<
name|BibtexEntry
argument_list|>
argument_list|()
decl_stmt|;
name|BibtexEntry
index|[]
name|sel
init|=
name|panel
operator|.
name|getSelectedEntries
argument_list|()
decl_stmt|;
name|Collections
operator|.
name|addAll
argument_list|(
name|entries
argument_list|,
name|sel
argument_list|)
expr_stmt|;
specifier|final
name|List
argument_list|<
name|FileListEntry
argument_list|>
name|links
init|=
name|AccessLinksForEntries
operator|.
name|getExternalLinksForEntries
argument_list|(
name|entries
argument_list|)
decl_stmt|;
for|for
control|(
name|FileListEntry
name|entry
range|:
name|links
control|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Link: "
operator|+
name|entry
operator|.
name|getLink
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|final
name|JProgressBar
name|prog
init|=
operator|new
name|JProgressBar
argument_list|()
decl_stmt|;
name|prog
operator|.
name|setIndeterminate
argument_list|(
literal|true
argument_list|)
expr_stmt|;
specifier|final
name|JDialog
name|diag
init|=
operator|new
name|JDialog
argument_list|(
name|panel
operator|.
name|frame
argument_list|()
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|diag
operator|.
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|prog
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|diag
operator|.
name|pack
argument_list|()
expr_stmt|;
name|diag
operator|.
name|setLocationRelativeTo
argument_list|(
name|panel
operator|.
name|frame
argument_list|()
argument_list|)
expr_stmt|;
name|diag
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|JabRefExecutorService
operator|.
name|INSTANCE
operator|.
name|execute
argument_list|(
operator|new
name|Runnable
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|run
parameter_list|()
block|{
name|AccessLinksForEntries
operator|.
name|copyExternalLinksToDirectory
argument_list|(
name|links
argument_list|,
operator|new
name|File
argument_list|(
literal|"/home/alver/tmp"
argument_list|)
argument_list|,
name|panel
operator|.
name|metaData
argument_list|()
argument_list|,
name|prog
argument_list|,
literal|false
argument_list|,
operator|new
name|ActionListener
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|actionEvent
parameter_list|)
block|{
name|diag
operator|.
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

