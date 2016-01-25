begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|Color
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Dimension
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
name|util
operator|.
name|Collection
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
name|keyboard
operator|.
name|KeyBinding
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
name|util
operator|.
name|FocusRequester
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
name|util
operator|.
name|PositionWindow
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
name|database
operator|.
name|BibDatabase
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
name|BibEntry
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
name|com
operator|.
name|jgoodies
operator|.
name|forms
operator|.
name|builder
operator|.
name|ButtonBarBuilder
import|;
end_import

begin_comment
comment|/**  *  * This action goes through all selected entries in the BasePanel, and attempts  * to write the XMP data to the external pdf.  */
end_comment

begin_class
DECL|class|WriteXMPAction
specifier|public
class|class
name|WriteXMPAction
extends|extends
name|AbstractWorker
block|{
DECL|field|panel
specifier|private
specifier|final
name|BasePanel
name|panel
decl_stmt|;
DECL|field|entries
specifier|private
name|BibEntry
index|[]
name|entries
decl_stmt|;
DECL|field|database
specifier|private
name|BibDatabase
name|database
decl_stmt|;
DECL|field|optDiag
specifier|private
name|OptionsDialog
name|optDiag
decl_stmt|;
DECL|field|goOn
specifier|private
name|boolean
name|goOn
init|=
literal|true
decl_stmt|;
DECL|field|skipped
specifier|private
name|int
name|skipped
decl_stmt|;
DECL|field|entriesChanged
specifier|private
name|int
name|entriesChanged
decl_stmt|;
DECL|field|errors
specifier|private
name|int
name|errors
decl_stmt|;
DECL|method|WriteXMPAction (BasePanel panel)
specifier|public
name|WriteXMPAction
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
DECL|method|init ()
specifier|public
name|void
name|init
parameter_list|()
block|{
name|database
operator|=
name|panel
operator|.
name|getDatabase
argument_list|()
expr_stmt|;
comment|// Get entries and check if it makes sense to perform this operation
name|entries
operator|=
name|panel
operator|.
name|getSelectedEntries
argument_list|()
expr_stmt|;
if|if
condition|(
name|entries
operator|.
name|length
operator|==
literal|0
condition|)
block|{
name|Collection
argument_list|<
name|BibEntry
argument_list|>
name|var
init|=
name|database
operator|.
name|getEntries
argument_list|()
decl_stmt|;
name|entries
operator|=
name|var
operator|.
name|toArray
argument_list|(
operator|new
name|BibEntry
index|[
name|var
operator|.
name|size
argument_list|()
index|]
argument_list|)
expr_stmt|;
if|if
condition|(
name|entries
operator|.
name|length
operator|==
literal|0
condition|)
block|{
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|panel
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"This operation requires at least one entry."
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Write XMP-metadata"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
name|goOn
operator|=
literal|false
expr_stmt|;
return|return;
block|}
else|else
block|{
name|int
name|response
init|=
name|JOptionPane
operator|.
name|showConfirmDialog
argument_list|(
name|panel
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Write XMP-metadata for all PDFs in current database?"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Write XMP-metadata"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|YES_NO_CANCEL_OPTION
argument_list|,
name|JOptionPane
operator|.
name|QUESTION_MESSAGE
argument_list|)
decl_stmt|;
if|if
condition|(
name|response
operator|!=
name|JOptionPane
operator|.
name|YES_OPTION
condition|)
block|{
name|goOn
operator|=
literal|false
expr_stmt|;
return|return;
block|}
block|}
block|}
name|errors
operator|=
name|entriesChanged
operator|=
name|skipped
operator|=
literal|0
expr_stmt|;
if|if
condition|(
name|optDiag
operator|==
literal|null
condition|)
block|{
name|optDiag
operator|=
operator|new
name|OptionsDialog
argument_list|(
name|panel
operator|.
name|frame
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|optDiag
operator|.
name|open
argument_list|()
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
operator|!
name|goOn
condition|)
block|{
return|return;
block|}
for|for
control|(
name|BibEntry
name|entry
range|:
name|entries
control|)
block|{
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
if|if
condition|(
name|entry
operator|.
name|hasField
argument_list|(
name|Globals
operator|.
name|FILE_FIELD
argument_list|)
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
name|entry
operator|.
name|getField
argument_list|(
name|Globals
operator|.
name|FILE_FIELD
argument_list|)
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
name|type
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
name|type
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
name|link
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
name|optDiag
operator|.
name|getProgressArea
argument_list|()
operator|.
name|append
argument_list|(
name|entry
operator|.
name|getCiteKey
argument_list|()
operator|+
literal|"\n"
argument_list|)
expr_stmt|;
if|if
condition|(
name|files
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|skipped
operator|++
expr_stmt|;
name|optDiag
operator|.
name|getProgressArea
argument_list|()
operator|.
name|append
argument_list|(
literal|"  "
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"Skipped - No PDF linked"
argument_list|)
operator|+
literal|".\n"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
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
name|skipped
operator|++
expr_stmt|;
name|optDiag
operator|.
name|getProgressArea
argument_list|()
operator|.
name|append
argument_list|(
literal|"  "
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"Skipped - PDF does not exist"
argument_list|)
operator|+
literal|":\n"
argument_list|)
expr_stmt|;
name|optDiag
operator|.
name|getProgressArea
argument_list|()
operator|.
name|append
argument_list|(
literal|"    "
operator|+
name|file
operator|.
name|getPath
argument_list|()
operator|+
literal|"\n"
argument_list|)
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
name|database
argument_list|)
expr_stmt|;
name|optDiag
operator|.
name|getProgressArea
argument_list|()
operator|.
name|append
argument_list|(
literal|"  "
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"OK"
argument_list|)
operator|+
literal|".\n"
argument_list|)
expr_stmt|;
name|entriesChanged
operator|++
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|optDiag
operator|.
name|getProgressArea
argument_list|()
operator|.
name|append
argument_list|(
literal|"  "
operator|+
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
literal|"':\n"
argument_list|)
expr_stmt|;
name|optDiag
operator|.
name|getProgressArea
argument_list|()
operator|.
name|append
argument_list|(
literal|"    "
operator|+
name|e
operator|.
name|getLocalizedMessage
argument_list|()
operator|+
literal|"\n"
argument_list|)
expr_stmt|;
name|errors
operator|++
expr_stmt|;
block|}
block|}
block|}
block|}
if|if
condition|(
name|optDiag
operator|.
name|isCanceled
argument_list|()
condition|)
block|{
name|optDiag
operator|.
name|getProgressArea
argument_list|()
operator|.
name|append
argument_list|(
literal|"\n"
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"Operation canceled."
argument_list|)
operator|+
literal|"\n"
argument_list|)
expr_stmt|;
break|break;
block|}
block|}
name|optDiag
operator|.
name|getProgressArea
argument_list|()
operator|.
name|append
argument_list|(
literal|"\n"
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"Finished writing XMP for %0 file (%1 skipped, %2 errors)."
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|entriesChanged
argument_list|)
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|skipped
argument_list|)
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|errors
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|optDiag
operator|.
name|done
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|update ()
specifier|public
name|void
name|update
parameter_list|()
block|{
if|if
condition|(
operator|!
name|goOn
condition|)
block|{
return|return;
block|}
name|panel
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Finished writing XMP for %0 file (%1 skipped, %2 errors)."
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|entriesChanged
argument_list|)
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|skipped
argument_list|)
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|errors
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|class|OptionsDialog
class|class
name|OptionsDialog
extends|extends
name|JDialog
block|{
DECL|field|okButton
specifier|private
specifier|final
name|JButton
name|okButton
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"OK"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|cancelButton
specifier|private
specifier|final
name|JButton
name|cancelButton
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Cancel"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|canceled
specifier|private
name|boolean
name|canceled
decl_stmt|;
DECL|field|progressArea
specifier|private
specifier|final
name|JTextArea
name|progressArea
decl_stmt|;
DECL|method|OptionsDialog (JFrame parent)
specifier|public
name|OptionsDialog
parameter_list|(
name|JFrame
name|parent
parameter_list|)
block|{
name|super
argument_list|(
name|parent
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Writing XMP metadata for selected entries..."
argument_list|)
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|okButton
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|okButton
operator|.
name|addActionListener
argument_list|(
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
name|e
parameter_list|)
block|{
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|AbstractAction
name|cancel
init|=
operator|new
name|AbstractAction
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|canceled
operator|=
literal|true
expr_stmt|;
block|}
block|}
decl_stmt|;
name|cancelButton
operator|.
name|addActionListener
argument_list|(
name|cancel
argument_list|)
expr_stmt|;
name|InputMap
name|im
init|=
name|cancelButton
operator|.
name|getInputMap
argument_list|(
name|JComponent
operator|.
name|WHEN_IN_FOCUSED_WINDOW
argument_list|)
decl_stmt|;
name|ActionMap
name|am
init|=
name|cancelButton
operator|.
name|getActionMap
argument_list|()
decl_stmt|;
name|im
operator|.
name|put
argument_list|(
name|Globals
operator|.
name|getKeyPrefs
argument_list|()
operator|.
name|getKey
argument_list|(
name|KeyBinding
operator|.
name|CLOSE_DIALOG
argument_list|)
argument_list|,
literal|"close"
argument_list|)
expr_stmt|;
name|am
operator|.
name|put
argument_list|(
literal|"close"
argument_list|,
name|cancel
argument_list|)
expr_stmt|;
name|progressArea
operator|=
operator|new
name|JTextArea
argument_list|(
literal|15
argument_list|,
literal|60
argument_list|)
expr_stmt|;
name|JScrollPane
name|scrollPane
init|=
operator|new
name|JScrollPane
argument_list|(
name|progressArea
argument_list|,
name|JScrollPane
operator|.
name|VERTICAL_SCROLLBAR_ALWAYS
argument_list|,
name|JScrollPane
operator|.
name|HORIZONTAL_SCROLLBAR_NEVER
argument_list|)
decl_stmt|;
name|Dimension
name|d
init|=
name|progressArea
operator|.
name|getPreferredSize
argument_list|()
decl_stmt|;
name|d
operator|.
name|height
operator|+=
name|scrollPane
operator|.
name|getHorizontalScrollBar
argument_list|()
operator|.
name|getHeight
argument_list|()
operator|+
literal|15
expr_stmt|;
name|d
operator|.
name|width
operator|+=
name|scrollPane
operator|.
name|getVerticalScrollBar
argument_list|()
operator|.
name|getWidth
argument_list|()
operator|+
literal|15
expr_stmt|;
name|panel
operator|.
name|setSize
argument_list|(
name|d
argument_list|)
expr_stmt|;
name|progressArea
operator|.
name|setBackground
argument_list|(
name|Color
operator|.
name|WHITE
argument_list|)
expr_stmt|;
name|progressArea
operator|.
name|setEditable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|progressArea
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createEmptyBorder
argument_list|(
literal|3
argument_list|,
literal|3
argument_list|,
literal|3
argument_list|,
literal|3
argument_list|)
argument_list|)
expr_stmt|;
name|progressArea
operator|.
name|setText
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|JPanel
name|tmpPanel
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|tmpPanel
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createEmptyBorder
argument_list|(
literal|3
argument_list|,
literal|2
argument_list|,
literal|3
argument_list|,
literal|2
argument_list|)
argument_list|)
expr_stmt|;
name|tmpPanel
operator|.
name|add
argument_list|(
name|scrollPane
argument_list|)
expr_stmt|;
comment|// progressArea.setPreferredSize(new Dimension(300, 300));
name|ButtonBarBuilder
name|bb
init|=
operator|new
name|ButtonBarBuilder
argument_list|()
decl_stmt|;
name|bb
operator|.
name|addGlue
argument_list|()
expr_stmt|;
name|bb
operator|.
name|addButton
argument_list|(
name|okButton
argument_list|)
expr_stmt|;
name|bb
operator|.
name|addRelatedGap
argument_list|()
expr_stmt|;
name|bb
operator|.
name|addButton
argument_list|(
name|cancelButton
argument_list|)
expr_stmt|;
name|bb
operator|.
name|addGlue
argument_list|()
expr_stmt|;
name|JPanel
name|bbPanel
init|=
name|bb
operator|.
name|getPanel
argument_list|()
decl_stmt|;
name|bbPanel
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createEmptyBorder
argument_list|(
literal|0
argument_list|,
literal|3
argument_list|,
literal|3
argument_list|,
literal|3
argument_list|)
argument_list|)
expr_stmt|;
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|tmpPanel
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|bbPanel
argument_list|,
name|BorderLayout
operator|.
name|SOUTH
argument_list|)
expr_stmt|;
name|pack
argument_list|()
expr_stmt|;
name|this
operator|.
name|setResizable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
DECL|method|done ()
specifier|public
name|void
name|done
parameter_list|()
block|{
name|okButton
operator|.
name|setEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|cancelButton
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
DECL|method|open ()
specifier|public
name|void
name|open
parameter_list|()
block|{
name|progressArea
operator|.
name|setText
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|canceled
operator|=
literal|false
expr_stmt|;
name|PositionWindow
operator|.
name|placeDialog
argument_list|(
name|optDiag
argument_list|,
name|panel
operator|.
name|frame
argument_list|()
argument_list|)
expr_stmt|;
name|okButton
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|cancelButton
operator|.
name|setEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
operator|new
name|FocusRequester
argument_list|(
name|okButton
argument_list|)
expr_stmt|;
name|optDiag
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
DECL|method|isCanceled ()
specifier|public
name|boolean
name|isCanceled
parameter_list|()
block|{
return|return
name|canceled
return|;
block|}
DECL|method|getProgressArea ()
specifier|public
name|JTextArea
name|getProgressArea
parameter_list|()
block|{
return|return
name|progressArea
return|;
block|}
block|}
block|}
end_class

end_unit

