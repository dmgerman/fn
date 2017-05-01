begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.fieldeditors
package|package
name|org
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
name|Component
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
name|java
operator|.
name|nio
operator|.
name|file
operator|.
name|Files
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
name|Paths
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
name|javax
operator|.
name|swing
operator|.
name|table
operator|.
name|TableCellRenderer
import|;
end_import

begin_import
import|import
name|org
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
name|jabref
operator|.
name|gui
operator|.
name|IconTheme
import|;
end_import

begin_import
import|import
name|org
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
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|autocompleter
operator|.
name|AutoCompleteListener
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|desktop
operator|.
name|JabRefDesktop
import|;
end_import

begin_import
import|import
name|org
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
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|externalfiles
operator|.
name|DownloadExternalFile
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|externalfiles
operator|.
name|MoveFileAction
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|externalfiles
operator|.
name|RenameFileAction
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|externalfiletype
operator|.
name|ExternalFileType
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|externalfiletype
operator|.
name|ExternalFileTypes
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|filelist
operator|.
name|FileListEntry
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|filelist
operator|.
name|FileListEntryEditor
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|filelist
operator|.
name|FileListTableModel
import|;
end_import

begin_import
import|import
name|org
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
name|org
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
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|database
operator|.
name|BibDatabaseContext
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
name|FormBuilder
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
DECL|field|editor
specifier|private
name|FileListEntryEditor
name|editor
decl_stmt|;
DECL|method|FileListEditor (JabRefFrame frame, BibDatabaseContext databaseContext, String fieldName, String content, EntryEditor entryEditor)
specifier|public
name|FileListEditor
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|BibDatabaseContext
name|databaseContext
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
name|databaseContext
operator|=
name|databaseContext
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
name|fieldName
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
name|initKeyBindings
argument_list|()
expr_stmt|;
name|JButton
name|remove
init|=
operator|new
name|JButton
argument_list|(
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|REMOVE_NOBOX
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
decl_stmt|;
name|remove
operator|.
name|setToolTipText
argument_list|(
name|Localization
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
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|UP
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
decl_stmt|;
name|JButton
name|down
init|=
operator|new
name|JButton
argument_list|(
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|DOWN
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
decl_stmt|;
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
name|remove
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
name|removeEntries
argument_list|()
argument_list|)
expr_stmt|;
name|up
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
name|moveEntry
argument_list|(
operator|-
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|down
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
name|moveEntry
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|FormBuilder
name|builder
init|=
name|FormBuilder
operator|.
name|create
argument_list|()
operator|.
name|layout
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
name|add
argument_list|(
name|up
argument_list|)
operator|.
name|xy
argument_list|(
literal|1
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|down
argument_list|)
operator|.
name|xy
argument_list|(
literal|1
argument_list|,
literal|2
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|remove
argument_list|)
operator|.
name|xy
argument_list|(
literal|3
argument_list|,
literal|2
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
name|JMenuItem
name|openLink
init|=
operator|new
name|JMenuItem
argument_list|(
name|Localization
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
name|e
lambda|->
name|openSelectedFile
argument_list|()
argument_list|)
expr_stmt|;
name|JMenuItem
name|openFolder
init|=
operator|new
name|JMenuItem
argument_list|(
name|Localization
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
name|e
lambda|->
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
name|Path
name|path
init|=
literal|null
decl_stmt|;
comment|// absolute path
if|if
condition|(
name|Paths
operator|.
name|get
argument_list|(
name|entry
operator|.
name|getLink
argument_list|()
argument_list|)
operator|.
name|isAbsolute
argument_list|()
condition|)
block|{
name|path
operator|=
name|Paths
operator|.
name|get
argument_list|(
name|entry
operator|.
name|getLink
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// relative to file folder
for|for
control|(
name|String
name|folder
range|:
name|databaseContext
operator|.
name|getFileDirectories
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getFileDirectoryPreferences
argument_list|()
argument_list|)
control|)
block|{
name|Path
name|file
init|=
name|Paths
operator|.
name|get
argument_list|(
name|folder
argument_list|,
name|entry
operator|.
name|getLink
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|Files
operator|.
name|exists
argument_list|(
name|file
argument_list|)
condition|)
block|{
name|path
operator|=
name|file
expr_stmt|;
break|break;
block|}
block|}
block|}
if|if
condition|(
name|path
operator|!=
literal|null
condition|)
block|{
name|JabRefDesktop
operator|.
name|openFolderAndSelectFile
argument_list|(
name|path
argument_list|)
expr_stmt|;
block|}
else|else
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
literal|"File not found"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Error"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
block|}
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
argument_list|)
expr_stmt|;
name|JMenuItem
name|rename
init|=
operator|new
name|JMenuItem
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Rename file"
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
name|RenameFileAction
argument_list|(
name|frame
argument_list|,
name|entryEditor
argument_list|,
name|this
argument_list|)
argument_list|)
expr_stmt|;
name|JMenuItem
name|moveToFileDir
init|=
operator|new
name|JMenuItem
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Move file to file directory"
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
argument_list|)
argument_list|)
expr_stmt|;
name|JMenuItem
name|deleteFile
init|=
operator|new
name|JMenuItem
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Permanently delete local file"
argument_list|)
argument_list|)
decl_stmt|;
name|menu
operator|.
name|add
argument_list|(
name|deleteFile
argument_list|)
expr_stmt|;
name|deleteFile
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
block|{
name|int
name|row
init|=
name|getSelectedRow
argument_list|()
decl_stmt|;
comment|// no selection
if|if
condition|(
name|row
operator|==
operator|-
literal|1
condition|)
block|{
return|return;
block|}
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
name|Optional
argument_list|<
name|Path
argument_list|>
name|file
init|=
name|entry
operator|.
name|toParsedFileField
argument_list|()
operator|.
name|findIn
argument_list|(
name|databaseContext
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getFileDirectoryPreferences
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|file
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|String
index|[]
name|options
init|=
block|{
name|Localization
operator|.
name|lang
argument_list|(
literal|"Delete"
argument_list|)
operator|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Cancel"
argument_list|)
block|}
empty_stmt|;
name|int
name|userConfirm
init|=
name|JOptionPane
operator|.
name|showOptionDialog
argument_list|(
name|frame
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Delete '%0'?"
argument_list|,
name|file
operator|.
name|get
argument_list|()
operator|.
name|getFileName
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Delete file"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|YES_NO_OPTION
argument_list|,
name|JOptionPane
operator|.
name|QUESTION_MESSAGE
argument_list|,
literal|null
argument_list|,
name|options
argument_list|,
name|options
index|[
literal|0
index|]
argument_list|)
decl_stmt|;
if|if
condition|(
name|userConfirm
operator|==
name|JOptionPane
operator|.
name|YES_OPTION
condition|)
block|{
try|try
block|{
name|Files
operator|.
name|delete
argument_list|(
name|file
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
name|removeEntries
argument_list|()
expr_stmt|;
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
literal|"File permission error"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Cannot delete file"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"File permission error while deleting: "
operator|+
name|entry
operator|.
name|getLink
argument_list|()
argument_list|,
name|ex
argument_list|)
expr_stmt|;
block|}
block|}
block|}
else|else
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
literal|"File not found"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Cannot delete file"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|adjustColumnWidth
argument_list|()
expr_stmt|;
block|}
DECL|method|initKeyBindings ()
specifier|private
name|void
name|initKeyBindings
parameter_list|()
block|{
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
comment|// Add input/action pair for moving an entry up:
name|getInputMap
argument_list|()
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
name|FILE_LIST_EDITOR_MOVE_ENTRY_UP
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
name|getKeyPrefs
argument_list|()
operator|.
name|getKey
argument_list|(
name|KeyBinding
operator|.
name|FILE_LIST_EDITOR_MOVE_ENTRY_DOWN
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
name|getInputMap
argument_list|()
operator|.
name|put
argument_list|(
name|KeyStroke
operator|.
name|getKeyStroke
argument_list|(
literal|"F4"
argument_list|)
argument_list|,
literal|"open file"
argument_list|)
expr_stmt|;
name|getActionMap
argument_list|()
operator|.
name|put
argument_list|(
literal|"open file"
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
name|openSelectedFile
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
DECL|method|adjustColumnWidth ()
specifier|public
name|void
name|adjustColumnWidth
parameter_list|()
block|{
for|for
control|(
name|int
name|column
init|=
literal|0
init|;
name|column
operator|<
name|this
operator|.
name|getColumnCount
argument_list|()
condition|;
name|column
operator|++
control|)
block|{
name|int
name|width
init|=
literal|0
decl_stmt|;
for|for
control|(
name|int
name|row
init|=
literal|0
init|;
name|row
operator|<
name|this
operator|.
name|getRowCount
argument_list|()
condition|;
name|row
operator|++
control|)
block|{
name|TableCellRenderer
name|renderer
init|=
name|this
operator|.
name|getCellRenderer
argument_list|(
name|row
argument_list|,
name|column
argument_list|)
decl_stmt|;
name|Component
name|comp
init|=
name|this
operator|.
name|prepareRenderer
argument_list|(
name|renderer
argument_list|,
name|row
argument_list|,
name|column
argument_list|)
decl_stmt|;
name|width
operator|=
name|Math
operator|.
name|max
argument_list|(
name|comp
operator|.
name|getPreferredSize
argument_list|()
operator|.
name|width
argument_list|,
name|width
argument_list|)
expr_stmt|;
block|}
name|this
operator|.
name|columnModel
operator|.
name|getColumn
argument_list|(
name|column
argument_list|)
operator|.
name|setPreferredWidth
argument_list|(
name|width
argument_list|)
expr_stmt|;
block|}
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
name|Optional
argument_list|<
name|ExternalFileType
argument_list|>
name|type
init|=
name|ExternalFileTypes
operator|.
name|getInstance
argument_list|()
operator|.
name|getExternalFileTypeByName
argument_list|(
name|entry
operator|.
name|getType
argument_list|()
operator|.
name|get
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
name|JabRefDesktop
operator|.
name|openExternalFileAnyFormat
argument_list|(
name|databaseContext
argument_list|,
name|entry
operator|.
name|getLink
argument_list|()
argument_list|,
name|type
operator|.
name|isPresent
argument_list|()
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
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Cannot open selected file."
argument_list|,
name|e
argument_list|)
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
block|{
comment|// Do nothing
block|}
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
block|{
comment|// Do nothing
block|}
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
name|adjustColumnWidth
argument_list|()
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
name|adjustColumnWidth
argument_list|()
expr_stmt|;
block|}
comment|/**      * Open an editor for this entry.      *      * @param entry      The entry to edit.      * @param openBrowse True to indicate that a Browse dialog should be immediately opened.      * @return true if the edit was accepted, false if it was canceled.      */
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
name|databaseContext
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
name|adjustColumnWidth
argument_list|()
expr_stmt|;
return|return
name|editor
operator|.
name|okPressed
argument_list|()
return|;
block|}
comment|/**      * This is the callback method that the DownloadExternalFile class uses to report the result      * of a download operation. This call may never come, if the user canceled the operation.      *      * @param file The FileListEntry linking to the resulting local file.      */
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
literal|0
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
name|adjustColumnWidth
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|undo ()
specifier|public
name|void
name|undo
parameter_list|()
block|{
comment|// Do nothing
block|}
annotation|@
name|Override
DECL|method|redo ()
specifier|public
name|void
name|redo
parameter_list|()
block|{
comment|// Do nothing
block|}
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
block|{
comment|// Do nothing
block|}
annotation|@
name|Override
DECL|method|clearAutoCompleteSuggestion ()
specifier|public
name|void
name|clearAutoCompleteSuggestion
parameter_list|()
block|{
comment|// Do nothing
block|}
annotation|@
name|Override
DECL|method|setActiveBackgroundColor ()
specifier|public
name|void
name|setActiveBackgroundColor
parameter_list|()
block|{
comment|// Do nothing
block|}
annotation|@
name|Override
DECL|method|setValidBackgroundColor ()
specifier|public
name|void
name|setValidBackgroundColor
parameter_list|()
block|{
comment|// Do nothing
block|}
annotation|@
name|Override
DECL|method|setInvalidBackgroundColor ()
specifier|public
name|void
name|setInvalidBackgroundColor
parameter_list|()
block|{
comment|// Do nothing
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
block|}
end_class

end_unit

