begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2012 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.gui.fieldeditors
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|fieldeditors
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
name|Insets
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
name|awt
operator|.
name|event
operator|.
name|MouseAdapter
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
name|MouseEvent
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
name|javax
operator|.
name|swing
operator|.
name|AbstractAction
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JButton
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JComponent
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
name|JLabel
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JMenuItem
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
name|JPanel
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JPopupMenu
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JScrollPane
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JTable
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|KeyStroke
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|TransferHandler
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
name|external
operator|.
name|*
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
name|DefaultFormBuilder
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
name|layout
operator|.
name|FormLayout
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
name|AutoCompleteListener
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
name|FileListTableModel
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
name|StringUtil
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
name|util
operator|.
name|Util
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
comment|/**  * Created by Morten O. Alver 2007.02.22  */
end_comment

begin_class
DECL|class|FileListEditor
specifier|public
class|class
name|FileListEditor
extends|extends
name|JTable
implements|implements
name|FieldEditor
implements|,
name|DownloadExternalFile
operator|.
name|DownloadCallback
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
name|FileListEditor
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|label
specifier|private
specifier|final
name|FieldNameLabel
name|label
decl_stmt|;
DECL|field|editor
specifier|private
name|FileListEntryEditor
name|editor
init|=
literal|null
decl_stmt|;
DECL|field|frame
specifier|private
specifier|final
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|metaData
specifier|private
specifier|final
name|MetaData
name|metaData
decl_stmt|;
DECL|field|fieldName
specifier|private
specifier|final
name|String
name|fieldName
decl_stmt|;
DECL|field|entryEditor
specifier|private
specifier|final
name|EntryEditor
name|entryEditor
decl_stmt|;
DECL|field|panel
specifier|private
specifier|final
name|JPanel
name|panel
decl_stmt|;
DECL|field|tableModel
specifier|private
specifier|final
name|FileListTableModel
name|tableModel
decl_stmt|;
DECL|field|auto
specifier|private
specifier|final
name|JButton
name|auto
decl_stmt|;
DECL|field|menu
specifier|private
specifier|final
name|JPopupMenu
name|menu
init|=
operator|new
name|JPopupMenu
argument_list|()
decl_stmt|;
DECL|method|FileListEditor (JabRefFrame frame, MetaData metaData, String fieldName, String content, EntryEditor entryEditor)
specifier|public
name|FileListEditor
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|MetaData
name|metaData
parameter_list|,
name|String
name|fieldName
parameter_list|,
name|String
name|content
parameter_list|,
name|EntryEditor
name|entryEditor
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
name|fieldName
operator|=
name|fieldName
expr_stmt|;
name|this
operator|.
name|entryEditor
operator|=
name|entryEditor
expr_stmt|;
name|label
operator|=
operator|new
name|FieldNameLabel
argument_list|(
literal|" "
operator|+
name|StringUtil
operator|.
name|capitalizeFirst
argument_list|(
name|fieldName
argument_list|)
operator|+
literal|" "
argument_list|)
expr_stmt|;
name|tableModel
operator|=
operator|new
name|FileListTableModel
argument_list|()
expr_stmt|;
name|setText
argument_list|(
name|content
argument_list|)
expr_stmt|;
name|setModel
argument_list|(
name|tableModel
argument_list|)
expr_stmt|;
name|JScrollPane
name|sPane
init|=
operator|new
name|JScrollPane
argument_list|(
name|this
argument_list|)
decl_stmt|;
name|setTableHeader
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|addMouseListener
argument_list|(
operator|new
name|TableClickListener
argument_list|()
argument_list|)
expr_stmt|;
name|JButton
name|add
init|=
operator|new
name|JButton
argument_list|(
name|GUIGlobals
operator|.
name|getImage
argument_list|(
literal|"add"
argument_list|)
argument_list|)
decl_stmt|;
name|add
operator|.
name|setToolTipText
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"New file link (INSERT)"
argument_list|)
argument_list|)
expr_stmt|;
name|JButton
name|remove
init|=
operator|new
name|JButton
argument_list|(
name|GUIGlobals
operator|.
name|getImage
argument_list|(
literal|"remove"
argument_list|)
argument_list|)
decl_stmt|;
name|remove
operator|.
name|setToolTipText
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Remove file link (DELETE)"
argument_list|)
argument_list|)
expr_stmt|;
name|JButton
name|up
init|=
operator|new
name|JButton
argument_list|(
name|GUIGlobals
operator|.
name|getImage
argument_list|(
literal|"up"
argument_list|)
argument_list|)
decl_stmt|;
name|JButton
name|down
init|=
operator|new
name|JButton
argument_list|(
name|GUIGlobals
operator|.
name|getImage
argument_list|(
literal|"down"
argument_list|)
argument_list|)
decl_stmt|;
name|auto
operator|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Auto"
argument_list|)
argument_list|)
expr_stmt|;
name|JButton
name|download
init|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Download"
argument_list|)
argument_list|)
decl_stmt|;
name|add
operator|.
name|setMargin
argument_list|(
operator|new
name|Insets
argument_list|(
literal|0
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|remove
operator|.
name|setMargin
argument_list|(
operator|new
name|Insets
argument_list|(
literal|0
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|up
operator|.
name|setMargin
argument_list|(
operator|new
name|Insets
argument_list|(
literal|0
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|down
operator|.
name|setMargin
argument_list|(
operator|new
name|Insets
argument_list|(
literal|0
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|add
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
name|addEntry
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|remove
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
name|removeEntries
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|up
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
name|moveEntry
argument_list|(
operator|-
literal|1
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|down
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
name|moveEntry
argument_list|(
literal|1
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|auto
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
name|autoSetLinks
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|download
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
name|downloadFile
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|DefaultFormBuilder
name|builder
init|=
operator|new
name|DefaultFormBuilder
argument_list|(
operator|new
name|FormLayout
argument_list|(
literal|"fill:pref,1dlu,fill:pref,1dlu,fill:pref"
argument_list|,
literal|"fill:pref,fill:pref"
argument_list|)
argument_list|)
decl_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|up
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|add
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|auto
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|down
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|remove
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|download
argument_list|)
expr_stmt|;
name|panel
operator|=
operator|new
name|JPanel
argument_list|()
expr_stmt|;
name|panel
operator|.
name|setLayout
argument_list|(
operator|new
name|BorderLayout
argument_list|()
argument_list|)
expr_stmt|;
name|panel
operator|.
name|add
argument_list|(
name|sPane
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|panel
operator|.
name|add
argument_list|(
name|builder
operator|.
name|getPanel
argument_list|()
argument_list|,
name|BorderLayout
operator|.
name|EAST
argument_list|)
expr_stmt|;
name|TransferHandler
name|transferHandler
init|=
operator|new
name|FileListEditorTransferHandler
argument_list|(
name|frame
argument_list|,
name|entryEditor
argument_list|,
literal|null
argument_list|)
decl_stmt|;
name|setTransferHandler
argument_list|(
name|transferHandler
argument_list|)
expr_stmt|;
name|panel
operator|.
name|setTransferHandler
argument_list|(
name|transferHandler
argument_list|)
expr_stmt|;
comment|// Add an input/action pair for deleting entries:
name|getInputMap
argument_list|()
operator|.
name|put
argument_list|(
name|KeyStroke
operator|.
name|getKeyStroke
argument_list|(
literal|"DELETE"
argument_list|)
argument_list|,
literal|"delete"
argument_list|)
expr_stmt|;
name|getActionMap
argument_list|()
operator|.
name|put
argument_list|(
literal|"delete"
argument_list|,
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
name|actionEvent
parameter_list|)
block|{
name|int
name|row
init|=
name|getSelectedRow
argument_list|()
decl_stmt|;
name|removeEntries
argument_list|()
expr_stmt|;
name|row
operator|=
name|Math
operator|.
name|min
argument_list|(
name|row
argument_list|,
name|getRowCount
argument_list|()
operator|-
literal|1
argument_list|)
expr_stmt|;
if|if
condition|(
name|row
operator|>=
literal|0
condition|)
block|{
name|setRowSelectionInterval
argument_list|(
name|row
argument_list|,
name|row
argument_list|)
expr_stmt|;
block|}
block|}
block|}
argument_list|)
expr_stmt|;
comment|// Add an input/action pair for inserting an entry:
name|getInputMap
argument_list|()
operator|.
name|put
argument_list|(
name|KeyStroke
operator|.
name|getKeyStroke
argument_list|(
literal|"INSERT"
argument_list|)
argument_list|,
literal|"insert"
argument_list|)
expr_stmt|;
name|getActionMap
argument_list|()
operator|.
name|put
argument_list|(
literal|"insert"
argument_list|,
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
name|actionEvent
parameter_list|)
block|{
name|addEntry
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
comment|// Add input/action pair for moving an entry up:
name|getInputMap
argument_list|()
operator|.
name|put
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getKey
argument_list|(
literal|"File list editor, move entry up"
argument_list|)
argument_list|,
literal|"move up"
argument_list|)
expr_stmt|;
name|getActionMap
argument_list|()
operator|.
name|put
argument_list|(
literal|"move up"
argument_list|,
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
name|actionEvent
parameter_list|)
block|{
name|moveEntry
argument_list|(
operator|-
literal|1
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
comment|// Add input/action pair for moving an entry down:
name|getInputMap
argument_list|()
operator|.
name|put
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getKey
argument_list|(
literal|"File list editor, move entry down"
argument_list|)
argument_list|,
literal|"move down"
argument_list|)
expr_stmt|;
name|getActionMap
argument_list|()
operator|.
name|put
argument_list|(
literal|"move down"
argument_list|,
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
name|actionEvent
parameter_list|)
block|{
name|moveEntry
argument_list|(
literal|1
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|JMenuItem
name|openLink
init|=
operator|new
name|JMenuItem
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Open"
argument_list|)
argument_list|)
decl_stmt|;
name|menu
operator|.
name|add
argument_list|(
name|openLink
argument_list|)
expr_stmt|;
name|openLink
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
name|actionEvent
parameter_list|)
block|{
name|openSelectedFile
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|JMenuItem
name|openFolder
init|=
operator|new
name|JMenuItem
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Open folder"
argument_list|)
argument_list|)
decl_stmt|;
name|menu
operator|.
name|add
argument_list|(
name|openFolder
argument_list|)
expr_stmt|;
name|openFolder
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
name|int
name|row
init|=
name|getSelectedRow
argument_list|()
decl_stmt|;
if|if
condition|(
name|row
operator|>=
literal|0
condition|)
block|{
name|FileListEntry
name|entry
init|=
name|tableModel
operator|.
name|getEntry
argument_list|(
name|row
argument_list|)
decl_stmt|;
try|try
block|{
name|Util
operator|.
name|openFolderAndSelectFile
argument_list|(
name|entry
operator|.
name|getLink
argument_list|()
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
name|debug
argument_list|(
literal|"Cannot open folder"
argument_list|,
name|ex
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
argument_list|)
expr_stmt|;
name|JMenuItem
name|rename
init|=
operator|new
name|JMenuItem
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Move/Rename file"
argument_list|)
argument_list|)
decl_stmt|;
name|menu
operator|.
name|add
argument_list|(
name|rename
argument_list|)
expr_stmt|;
name|rename
operator|.
name|addActionListener
argument_list|(
operator|new
name|MoveFileAction
argument_list|(
name|frame
argument_list|,
name|entryEditor
argument_list|,
name|this
argument_list|,
literal|false
argument_list|)
argument_list|)
expr_stmt|;
name|JMenuItem
name|moveToFileDir
init|=
operator|new
name|JMenuItem
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Move to file directory"
argument_list|)
argument_list|)
decl_stmt|;
name|menu
operator|.
name|add
argument_list|(
name|moveToFileDir
argument_list|)
expr_stmt|;
name|moveToFileDir
operator|.
name|addActionListener
argument_list|(
operator|new
name|MoveFileAction
argument_list|(
name|frame
argument_list|,
name|entryEditor
argument_list|,
name|this
argument_list|,
literal|true
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|openSelectedFile ()
specifier|private
name|void
name|openSelectedFile
parameter_list|()
block|{
name|int
name|row
init|=
name|getSelectedRow
argument_list|()
decl_stmt|;
if|if
condition|(
name|row
operator|>=
literal|0
condition|)
block|{
name|FileListEntry
name|entry
init|=
name|tableModel
operator|.
name|getEntry
argument_list|(
name|row
argument_list|)
decl_stmt|;
try|try
block|{
name|ExternalFileType
name|type
init|=
name|Globals
operator|.
name|prefs
operator|.
name|getExternalFileTypeByName
argument_list|(
name|entry
operator|.
name|getType
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
name|Util
operator|.
name|openExternalFileAnyFormat
argument_list|(
name|metaData
argument_list|,
name|entry
operator|.
name|getLink
argument_list|()
argument_list|,
name|type
operator|!=
literal|null
condition|?
name|type
else|:
name|entry
operator|.
name|getType
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
block|}
DECL|method|getTableModel ()
specifier|public
name|FileListTableModel
name|getTableModel
parameter_list|()
block|{
return|return
name|tableModel
return|;
block|}
annotation|@
name|Override
DECL|method|getFieldName ()
specifier|public
name|String
name|getFieldName
parameter_list|()
block|{
return|return
name|fieldName
return|;
block|}
comment|/*       * Returns the component to be added to a container. Might be a JScrollPane     * or the component itself.     */
annotation|@
name|Override
DECL|method|getPane ()
specifier|public
name|JComponent
name|getPane
parameter_list|()
block|{
return|return
name|panel
return|;
block|}
comment|/*      * Returns the text component itself.     */
annotation|@
name|Override
DECL|method|getTextComponent ()
specifier|public
name|JComponent
name|getTextComponent
parameter_list|()
block|{
return|return
name|this
return|;
block|}
annotation|@
name|Override
DECL|method|getLabel ()
specifier|public
name|JLabel
name|getLabel
parameter_list|()
block|{
return|return
name|label
return|;
block|}
annotation|@
name|Override
DECL|method|setLabelColor (Color color)
specifier|public
name|void
name|setLabelColor
parameter_list|(
name|Color
name|color
parameter_list|)
block|{
name|label
operator|.
name|setForeground
argument_list|(
name|color
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|getText ()
specifier|public
name|String
name|getText
parameter_list|()
block|{
return|return
name|tableModel
operator|.
name|getStringRepresentation
argument_list|()
return|;
block|}
annotation|@
name|Override
DECL|method|setText (String newText)
specifier|public
name|void
name|setText
parameter_list|(
name|String
name|newText
parameter_list|)
block|{
name|tableModel
operator|.
name|setContent
argument_list|(
name|newText
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|append (String text)
specifier|public
name|void
name|append
parameter_list|(
name|String
name|text
parameter_list|)
block|{      }
annotation|@
name|Override
DECL|method|updateFont ()
specifier|public
name|void
name|updateFont
parameter_list|()
block|{      }
annotation|@
name|Override
DECL|method|paste (String textToInsert)
specifier|public
name|void
name|paste
parameter_list|(
name|String
name|textToInsert
parameter_list|)
block|{      }
annotation|@
name|Override
DECL|method|getSelectedText ()
specifier|public
name|String
name|getSelectedText
parameter_list|()
block|{
return|return
literal|null
return|;
block|}
DECL|method|addEntry (String initialLink)
specifier|private
name|void
name|addEntry
parameter_list|(
name|String
name|initialLink
parameter_list|)
block|{
name|int
name|row
init|=
name|getSelectedRow
argument_list|()
decl_stmt|;
if|if
condition|(
name|row
operator|==
operator|-
literal|1
condition|)
block|{
name|row
operator|=
literal|0
expr_stmt|;
block|}
name|FileListEntry
name|entry
init|=
operator|new
name|FileListEntry
argument_list|(
literal|""
argument_list|,
name|initialLink
argument_list|,
literal|null
argument_list|)
decl_stmt|;
if|if
condition|(
name|editListEntry
argument_list|(
name|entry
argument_list|,
literal|true
argument_list|)
condition|)
block|{
name|tableModel
operator|.
name|addEntry
argument_list|(
name|row
argument_list|,
name|entry
argument_list|)
expr_stmt|;
block|}
name|entryEditor
operator|.
name|updateField
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
DECL|method|addEntry ()
specifier|private
name|void
name|addEntry
parameter_list|()
block|{
name|addEntry
argument_list|(
literal|""
argument_list|)
expr_stmt|;
block|}
DECL|method|removeEntries ()
specifier|private
name|void
name|removeEntries
parameter_list|()
block|{
name|int
index|[]
name|rows
init|=
name|getSelectedRows
argument_list|()
decl_stmt|;
if|if
condition|(
name|rows
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|int
name|i
init|=
name|rows
operator|.
name|length
operator|-
literal|1
init|;
name|i
operator|>=
literal|0
condition|;
name|i
operator|--
control|)
block|{
name|tableModel
operator|.
name|removeEntry
argument_list|(
name|rows
index|[
name|i
index|]
argument_list|)
expr_stmt|;
block|}
block|}
name|entryEditor
operator|.
name|updateField
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
DECL|method|moveEntry (int i)
specifier|private
name|void
name|moveEntry
parameter_list|(
name|int
name|i
parameter_list|)
block|{
name|int
index|[]
name|sel
init|=
name|getSelectedRows
argument_list|()
decl_stmt|;
if|if
condition|(
operator|(
name|sel
operator|.
name|length
operator|!=
literal|1
operator|)
operator|||
operator|(
name|tableModel
operator|.
name|getRowCount
argument_list|()
operator|<
literal|2
operator|)
condition|)
block|{
return|return;
block|}
name|int
name|toIdx
init|=
name|sel
index|[
literal|0
index|]
operator|+
name|i
decl_stmt|;
if|if
condition|(
name|toIdx
operator|>=
name|tableModel
operator|.
name|getRowCount
argument_list|()
condition|)
block|{
name|toIdx
operator|-=
name|tableModel
operator|.
name|getRowCount
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|toIdx
operator|<
literal|0
condition|)
block|{
name|toIdx
operator|+=
name|tableModel
operator|.
name|getRowCount
argument_list|()
expr_stmt|;
block|}
name|FileListEntry
name|entry
init|=
name|tableModel
operator|.
name|getEntry
argument_list|(
name|sel
index|[
literal|0
index|]
argument_list|)
decl_stmt|;
name|tableModel
operator|.
name|removeEntry
argument_list|(
name|sel
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
name|tableModel
operator|.
name|addEntry
argument_list|(
name|toIdx
argument_list|,
name|entry
argument_list|)
expr_stmt|;
name|entryEditor
operator|.
name|updateField
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|setRowSelectionInterval
argument_list|(
name|toIdx
argument_list|,
name|toIdx
argument_list|)
expr_stmt|;
block|}
comment|/**      * Open an editor for this entry.      * @param entry The entry to edit.      * @param openBrowse True to indicate that a Browse dialog should be immediately opened.      * @return true if the edit was accepted, false if it was cancelled.      */
DECL|method|editListEntry (FileListEntry entry, boolean openBrowse)
specifier|private
name|boolean
name|editListEntry
parameter_list|(
name|FileListEntry
name|entry
parameter_list|,
name|boolean
name|openBrowse
parameter_list|)
block|{
if|if
condition|(
name|editor
operator|==
literal|null
condition|)
block|{
name|editor
operator|=
operator|new
name|FileListEntryEditor
argument_list|(
name|frame
argument_list|,
name|entry
argument_list|,
literal|false
argument_list|,
literal|true
argument_list|,
name|metaData
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|editor
operator|.
name|setEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
name|editor
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|,
name|openBrowse
argument_list|)
expr_stmt|;
if|if
condition|(
name|editor
operator|.
name|okPressed
argument_list|()
condition|)
block|{
name|tableModel
operator|.
name|fireTableDataChanged
argument_list|()
expr_stmt|;
block|}
name|entryEditor
operator|.
name|updateField
argument_list|(
name|this
argument_list|)
expr_stmt|;
return|return
name|editor
operator|.
name|okPressed
argument_list|()
return|;
block|}
DECL|method|autoSetLinks ()
specifier|public
name|void
name|autoSetLinks
parameter_list|()
block|{
name|auto
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|BibtexEntry
name|entry
init|=
name|entryEditor
operator|.
name|getEntry
argument_list|()
decl_stmt|;
name|JDialog
name|diag
init|=
operator|new
name|JDialog
argument_list|(
name|frame
argument_list|,
literal|true
argument_list|)
decl_stmt|;
name|JabRefExecutorService
operator|.
name|INSTANCE
operator|.
name|execute
argument_list|(
name|Util
operator|.
name|autoSetLinks
argument_list|(
name|entry
argument_list|,
name|tableModel
argument_list|,
name|metaData
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
name|e
parameter_list|)
block|{
name|auto
operator|.
name|setEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
if|if
condition|(
name|e
operator|.
name|getID
argument_list|()
operator|>
literal|0
condition|)
block|{
name|entryEditor
operator|.
name|updateField
argument_list|(
name|FileListEditor
operator|.
name|this
argument_list|)
expr_stmt|;
name|frame
operator|.
name|output
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Finished autosetting external links."
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|frame
operator|.
name|output
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Finished autosetting external links."
argument_list|)
operator|+
literal|" "
operator|+
name|Globals
operator|.
name|lang
argument_list|(
literal|"No files found."
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
argument_list|,
name|diag
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * Run a file download operation.      */
DECL|method|downloadFile ()
specifier|private
name|void
name|downloadFile
parameter_list|()
block|{
name|String
name|bibtexKey
init|=
name|entryEditor
operator|.
name|getEntry
argument_list|()
operator|.
name|getCiteKey
argument_list|()
decl_stmt|;
if|if
condition|(
name|bibtexKey
operator|==
literal|null
condition|)
block|{
name|int
name|answer
init|=
name|JOptionPane
operator|.
name|showConfirmDialog
argument_list|(
name|frame
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"This entry has no BibTeX key. Generate key now?"
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
argument_list|,
name|JOptionPane
operator|.
name|QUESTION_MESSAGE
argument_list|)
decl_stmt|;
if|if
condition|(
name|answer
operator|==
name|JOptionPane
operator|.
name|OK_OPTION
condition|)
block|{
name|ActionListener
name|l
init|=
name|entryEditor
operator|.
name|generateKeyAction
decl_stmt|;
name|l
operator|.
name|actionPerformed
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|bibtexKey
operator|=
name|entryEditor
operator|.
name|getEntry
argument_list|()
operator|.
name|getCiteKey
argument_list|()
expr_stmt|;
block|}
block|}
name|DownloadExternalFile
name|def
init|=
operator|new
name|DownloadExternalFile
argument_list|(
name|frame
argument_list|,
name|frame
operator|.
name|basePanel
argument_list|()
operator|.
name|metaData
argument_list|()
argument_list|,
name|bibtexKey
argument_list|)
decl_stmt|;
try|try
block|{
name|def
operator|.
name|download
argument_list|(
name|this
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
block|}
comment|/**      * This is the callback method that the DownloadExternalFile class uses to report the result      * of a download operation. This call may never come, if the user cancelled the operation.      * @param file The FileListEntry linking to the resulting local file.      */
annotation|@
name|Override
DECL|method|downloadComplete (FileListEntry file)
specifier|public
name|void
name|downloadComplete
parameter_list|(
name|FileListEntry
name|file
parameter_list|)
block|{
name|tableModel
operator|.
name|addEntry
argument_list|(
name|tableModel
operator|.
name|getRowCount
argument_list|()
argument_list|,
name|file
argument_list|)
expr_stmt|;
name|entryEditor
operator|.
name|updateField
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
DECL|class|TableClickListener
class|class
name|TableClickListener
extends|extends
name|MouseAdapter
block|{
annotation|@
name|Override
DECL|method|mouseClicked (MouseEvent e)
specifier|public
name|void
name|mouseClicked
parameter_list|(
name|MouseEvent
name|e
parameter_list|)
block|{
if|if
condition|(
operator|(
name|e
operator|.
name|getButton
argument_list|()
operator|==
name|MouseEvent
operator|.
name|BUTTON1
operator|)
operator|&&
operator|(
name|e
operator|.
name|getClickCount
argument_list|()
operator|==
literal|2
operator|)
condition|)
block|{
name|int
name|row
init|=
name|rowAtPoint
argument_list|(
name|e
operator|.
name|getPoint
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|row
operator|>=
literal|0
condition|)
block|{
name|FileListEntry
name|entry
init|=
name|tableModel
operator|.
name|getEntry
argument_list|(
name|row
argument_list|)
decl_stmt|;
name|editListEntry
argument_list|(
name|entry
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|e
operator|.
name|isPopupTrigger
argument_list|()
condition|)
block|{
name|processPopupTrigger
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
DECL|method|mousePressed (MouseEvent e)
specifier|public
name|void
name|mousePressed
parameter_list|(
name|MouseEvent
name|e
parameter_list|)
block|{
if|if
condition|(
name|e
operator|.
name|isPopupTrigger
argument_list|()
condition|)
block|{
name|processPopupTrigger
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
DECL|method|mouseReleased (MouseEvent e)
specifier|public
name|void
name|mouseReleased
parameter_list|(
name|MouseEvent
name|e
parameter_list|)
block|{
if|if
condition|(
name|e
operator|.
name|isPopupTrigger
argument_list|()
condition|)
block|{
name|processPopupTrigger
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|processPopupTrigger (MouseEvent e)
specifier|private
name|void
name|processPopupTrigger
parameter_list|(
name|MouseEvent
name|e
parameter_list|)
block|{
name|int
name|row
init|=
name|rowAtPoint
argument_list|(
name|e
operator|.
name|getPoint
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|row
operator|>=
literal|0
condition|)
block|{
name|setRowSelectionInterval
argument_list|(
name|row
argument_list|,
name|row
argument_list|)
expr_stmt|;
name|menu
operator|.
name|show
argument_list|(
name|FileListEditor
operator|.
name|this
argument_list|,
name|e
operator|.
name|getX
argument_list|()
argument_list|,
name|e
operator|.
name|getY
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
annotation|@
name|Override
DECL|method|undo ()
specifier|public
name|void
name|undo
parameter_list|()
block|{     }
annotation|@
name|Override
DECL|method|redo ()
specifier|public
name|void
name|redo
parameter_list|()
block|{     }
annotation|@
name|Override
DECL|method|setAutoCompleteListener (AutoCompleteListener listener)
specifier|public
name|void
name|setAutoCompleteListener
parameter_list|(
name|AutoCompleteListener
name|listener
parameter_list|)
block|{     }
annotation|@
name|Override
DECL|method|clearAutoCompleteSuggestion ()
specifier|public
name|void
name|clearAutoCompleteSuggestion
parameter_list|()
block|{     }
annotation|@
name|Override
DECL|method|setActiveBackgroundColor ()
specifier|public
name|void
name|setActiveBackgroundColor
parameter_list|()
block|{     }
annotation|@
name|Override
DECL|method|setValidBackgroundColor ()
specifier|public
name|void
name|setValidBackgroundColor
parameter_list|()
block|{     }
annotation|@
name|Override
DECL|method|setInvalidBackgroundColor ()
specifier|public
name|void
name|setInvalidBackgroundColor
parameter_list|()
block|{     }
annotation|@
name|Override
DECL|method|updateFontColor ()
specifier|public
name|void
name|updateFontColor
parameter_list|()
block|{     }
block|}
end_class

end_unit

