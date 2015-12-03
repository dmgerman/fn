begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
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
name|gui
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
name|worker
operator|.
name|AbstractWorker
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
name|entryeditor
operator|.
name|EntryEditor
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
name|model
operator|.
name|entry
operator|.
name|BibtexEntry
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|xmp
operator|.
name|XMPUtil
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
name|javax
operator|.
name|xml
operator|.
name|transform
operator|.
name|TransformerException
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
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

begin_comment
comment|/**  * Write XMP action for EntryEditor toolbar.  */
end_comment

begin_class
DECL|class|WriteXMPEntryEditorAction
specifier|public
class|class
name|WriteXMPEntryEditorAction
extends|extends
name|AbstractAction
block|{
DECL|field|panel
specifier|private
specifier|final
name|BasePanel
name|panel
decl_stmt|;
DECL|field|editor
specifier|private
specifier|final
name|EntryEditor
name|editor
decl_stmt|;
DECL|field|message
specifier|private
name|String
name|message
decl_stmt|;
DECL|method|WriteXMPEntryEditorAction (BasePanel panel, EntryEditor editor)
specifier|public
name|WriteXMPEntryEditorAction
parameter_list|(
name|BasePanel
name|panel
parameter_list|,
name|EntryEditor
name|editor
parameter_list|)
block|{
name|this
operator|.
name|panel
operator|=
name|panel
expr_stmt|;
name|this
operator|.
name|editor
operator|=
name|editor
expr_stmt|;
comment|// normally, the next call should be without "Localization.lang". However, the string "Write XMP" is also used in non-menu places and therefore, the translation must be also available at Localization.lang
name|putValue
argument_list|(
name|Action
operator|.
name|NAME
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Write XMP"
argument_list|)
argument_list|)
expr_stmt|;
name|putValue
argument_list|(
name|Action
operator|.
name|SMALL_ICON
argument_list|,
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|WRITE_XMP
operator|.
name|getIcon
argument_list|()
argument_list|)
expr_stmt|;
name|putValue
argument_list|(
name|Action
operator|.
name|SHORT_DESCRIPTION
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Write BibtexEntry as XMP-metadata to PDF."
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|actionPerformed (ActionEvent actionEvent)
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|actionEvent
parameter_list|)
block|{
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|panel
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Writing XMP metadata..."
argument_list|)
argument_list|)
expr_stmt|;
name|panel
operator|.
name|frame
argument_list|()
operator|.
name|setProgressBarIndeterminate
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|panel
operator|.
name|frame
argument_list|()
operator|.
name|setProgressBarVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|BibtexEntry
name|entry
init|=
name|editor
operator|.
name|getEntry
argument_list|()
decl_stmt|;
comment|// Make a list of all PDFs linked from this entry:
name|List
argument_list|<
name|File
argument_list|>
name|files
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
comment|// First check the (legacy) "pdf" field:
name|String
name|pdf
init|=
name|entry
operator|.
name|getField
argument_list|(
literal|"pdf"
argument_list|)
decl_stmt|;
name|String
index|[]
name|dirs
init|=
name|panel
operator|.
name|metaData
argument_list|()
operator|.
name|getFileDirectory
argument_list|(
literal|"pdf"
argument_list|)
decl_stmt|;
name|File
name|f
init|=
name|FileUtil
operator|.
name|expandFilename
argument_list|(
name|pdf
argument_list|,
name|dirs
argument_list|)
decl_stmt|;
if|if
condition|(
name|f
operator|!=
literal|null
condition|)
block|{
name|files
operator|.
name|add
argument_list|(
name|f
argument_list|)
expr_stmt|;
block|}
comment|// Then check the "file" field:
name|dirs
operator|=
name|panel
operator|.
name|metaData
argument_list|()
operator|.
name|getFileDirectory
argument_list|(
name|Globals
operator|.
name|FILE_FIELD
argument_list|)
expr_stmt|;
name|String
name|field
init|=
name|entry
operator|.
name|getField
argument_list|(
name|Globals
operator|.
name|FILE_FIELD
argument_list|)
decl_stmt|;
if|if
condition|(
name|field
operator|!=
literal|null
condition|)
block|{
name|FileListTableModel
name|tm
init|=
operator|new
name|FileListTableModel
argument_list|()
decl_stmt|;
name|tm
operator|.
name|setContent
argument_list|(
name|field
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|j
init|=
literal|0
init|;
name|j
operator|<
name|tm
operator|.
name|getRowCount
argument_list|()
condition|;
name|j
operator|++
control|)
block|{
name|FileListEntry
name|flEntry
init|=
name|tm
operator|.
name|getEntry
argument_list|(
name|j
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|flEntry
operator|.
name|getType
argument_list|()
operator|!=
literal|null
operator|)
operator|&&
literal|"pdf"
operator|.
name|equals
argument_list|(
name|flEntry
operator|.
name|getType
argument_list|()
operator|.
name|getName
argument_list|()
operator|.
name|toLowerCase
argument_list|()
argument_list|)
condition|)
block|{
name|f
operator|=
name|FileUtil
operator|.
name|expandFilename
argument_list|(
name|flEntry
operator|.
name|getLink
argument_list|()
argument_list|,
name|dirs
argument_list|)
expr_stmt|;
if|if
condition|(
name|f
operator|!=
literal|null
condition|)
block|{
name|files
operator|.
name|add
argument_list|(
name|f
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
comment|// We want to offload the actual work to a background thread, so we have a worker
comment|// thread:
name|AbstractWorker
name|worker
init|=
operator|new
name|WriteXMPWorker
argument_list|(
name|files
argument_list|,
name|entry
argument_list|)
decl_stmt|;
comment|// Using Spin, we get a thread that gets synchronously offloaded to a new thread,
comment|// blocking the execution of this method:
name|worker
operator|.
name|getWorker
argument_list|()
operator|.
name|run
argument_list|()
expr_stmt|;
comment|// After the worker thread finishes, we are unblocked and ready to print the
comment|// status message:
name|panel
operator|.
name|output
argument_list|(
name|message
argument_list|)
expr_stmt|;
name|panel
operator|.
name|frame
argument_list|()
operator|.
name|setProgressBarVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|setEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
DECL|class|WriteXMPWorker
class|class
name|WriteXMPWorker
extends|extends
name|AbstractWorker
block|{
DECL|field|files
specifier|private
specifier|final
name|List
argument_list|<
name|File
argument_list|>
name|files
decl_stmt|;
DECL|field|entry
specifier|private
specifier|final
name|BibtexEntry
name|entry
decl_stmt|;
DECL|method|WriteXMPWorker (List<File> files, BibtexEntry entry)
specifier|public
name|WriteXMPWorker
parameter_list|(
name|List
argument_list|<
name|File
argument_list|>
name|files
parameter_list|,
name|BibtexEntry
name|entry
parameter_list|)
block|{
name|this
operator|.
name|files
operator|=
name|files
expr_stmt|;
name|this
operator|.
name|entry
operator|=
name|entry
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
if|if
condition|(
name|files
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|message
operator|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"No PDF linked"
argument_list|)
operator|+
literal|".\n"
expr_stmt|;
block|}
else|else
block|{
name|int
name|written
init|=
literal|0
decl_stmt|;
name|int
name|error
init|=
literal|0
decl_stmt|;
for|for
control|(
name|File
name|file
range|:
name|files
control|)
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
if|if
condition|(
name|files
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
block|{
name|message
operator|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"PDF does not exist"
argument_list|)
expr_stmt|;
block|}
name|error
operator|++
expr_stmt|;
block|}
else|else
block|{
try|try
block|{
name|XMPUtil
operator|.
name|writeXMP
argument_list|(
name|file
argument_list|,
name|entry
argument_list|,
name|panel
operator|.
name|database
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|files
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
block|{
name|message
operator|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"Wrote XMP-metadata"
argument_list|)
expr_stmt|;
block|}
name|written
operator|++
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
decl||
name|TransformerException
name|e
parameter_list|)
block|{
if|if
condition|(
name|files
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
block|{
name|message
operator|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"Error while writing"
argument_list|)
operator|+
literal|" '"
operator|+
name|file
operator|.
name|getPath
argument_list|()
operator|+
literal|"'"
expr_stmt|;
block|}
name|error
operator|++
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
name|files
operator|.
name|size
argument_list|()
operator|>
literal|1
condition|)
block|{
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Finished writing XMP-metadata. Wrote to %0 file(s)."
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|written
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|error
operator|>
literal|0
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|" "
argument_list|)
operator|.
name|append
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Error writing to %0 file(s)."
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|error
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|message
operator|=
name|sb
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
block|}
block|}
block|}
block|}
end_class

end_unit

