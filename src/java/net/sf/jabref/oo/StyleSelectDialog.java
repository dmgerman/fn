begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.oo
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|oo
package|;
end_package

begin_import
import|import
name|ca
operator|.
name|odell
operator|.
name|glazedlists
operator|.
name|BasicEventList
import|;
end_import

begin_import
import|import
name|ca
operator|.
name|odell
operator|.
name|glazedlists
operator|.
name|EventList
import|;
end_import

begin_import
import|import
name|ca
operator|.
name|odell
operator|.
name|glazedlists
operator|.
name|SortedList
import|;
end_import

begin_import
import|import
name|ca
operator|.
name|odell
operator|.
name|glazedlists
operator|.
name|event
operator|.
name|ListEvent
import|;
end_import

begin_import
import|import
name|ca
operator|.
name|odell
operator|.
name|glazedlists
operator|.
name|event
operator|.
name|ListEventListener
import|;
end_import

begin_import
import|import
name|ca
operator|.
name|odell
operator|.
name|glazedlists
operator|.
name|gui
operator|.
name|TableFormat
import|;
end_import

begin_import
import|import
name|ca
operator|.
name|odell
operator|.
name|glazedlists
operator|.
name|swing
operator|.
name|EventSelectionModel
import|;
end_import

begin_import
import|import
name|ca
operator|.
name|odell
operator|.
name|glazedlists
operator|.
name|swing
operator|.
name|EventTableModel
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
name|com
operator|.
name|jgoodies
operator|.
name|uif_lite
operator|.
name|component
operator|.
name|UIFSplitPane
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
name|ExternalFileType
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
name|UnknownExternalFileType
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
name|swing
operator|.
name|table
operator|.
name|TableColumnModel
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|*
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

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Set
import|;
end_import

begin_comment
comment|/**  * This class produces a dialog box for choosing a style file.  */
end_comment

begin_class
DECL|class|StyleSelectDialog
specifier|public
class|class
name|StyleSelectDialog
block|{
DECL|field|STYLE_FILE_EXTENSION
specifier|public
specifier|static
specifier|final
name|String
name|STYLE_FILE_EXTENSION
init|=
literal|".jstyle"
decl_stmt|;
DECL|field|frame
specifier|private
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|styles
DECL|field|sortedStyles
specifier|private
name|EventList
argument_list|<
name|OOBibStyle
argument_list|>
name|styles
decl_stmt|,
name|sortedStyles
decl_stmt|;
DECL|field|diag
specifier|private
name|JDialog
name|diag
decl_stmt|;
DECL|field|table
specifier|private
name|JTable
name|table
decl_stmt|;
DECL|field|contentPane
specifier|private
name|UIFSplitPane
name|contentPane
init|=
operator|new
name|UIFSplitPane
argument_list|(
name|UIFSplitPane
operator|.
name|VERTICAL_SPLIT
argument_list|)
decl_stmt|;
DECL|field|tableModel
specifier|private
name|EventTableModel
name|tableModel
decl_stmt|;
DECL|field|selectionModel
specifier|private
name|EventSelectionModel
argument_list|<
name|OOBibStyle
argument_list|>
name|selectionModel
decl_stmt|;
DECL|field|popup
specifier|private
name|JPopupMenu
name|popup
init|=
operator|new
name|JPopupMenu
argument_list|()
decl_stmt|;
DECL|field|edit
specifier|private
name|JMenuItem
name|edit
init|=
operator|new
name|JMenuItem
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Edit"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|preview
name|PreviewPanel
name|preview
decl_stmt|;
DECL|field|dirsPanel
name|StyleDirectoriesPanel
name|dirsPanel
decl_stmt|;
DECL|field|toRect
specifier|private
name|Rectangle
name|toRect
init|=
operator|new
name|Rectangle
argument_list|(
literal|0
argument_list|,
literal|0
argument_list|,
literal|1
argument_list|,
literal|1
argument_list|)
decl_stmt|;
DECL|field|ok
specifier|private
name|JButton
name|ok
init|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Ok"
argument_list|)
argument_list|)
decl_stmt|,
DECL|field|cancel
name|cancel
init|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Cancel"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|prevEntry
specifier|private
name|BibtexEntry
name|prevEntry
init|=
operator|new
name|BibtexEntry
argument_list|(
name|Util
operator|.
name|createNeutralId
argument_list|()
argument_list|)
decl_stmt|;
DECL|field|okPressed
specifier|private
name|boolean
name|okPressed
init|=
literal|false
decl_stmt|;
DECL|field|initSelection
specifier|private
name|String
name|initSelection
decl_stmt|;
DECL|method|StyleSelectDialog (JabRefFrame frame, String initSelection)
specifier|public
name|StyleSelectDialog
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|String
name|initSelection
parameter_list|)
block|{
name|this
operator|.
name|frame
operator|=
name|frame
expr_stmt|;
name|setupPrevEntry
argument_list|()
expr_stmt|;
name|init
argument_list|(
name|initSelection
argument_list|)
expr_stmt|;
block|}
DECL|method|init (String initSelection)
specifier|private
name|void
name|init
parameter_list|(
name|String
name|initSelection
parameter_list|)
block|{
name|this
operator|.
name|initSelection
operator|=
name|initSelection
expr_stmt|;
name|popup
operator|.
name|add
argument_list|(
name|edit
argument_list|)
expr_stmt|;
comment|// Add action listener to "Edit" menu item, which is supposed to open the style file in an external editor:
name|edit
operator|.
name|addActionListener
argument_list|(
operator|new
name|ActionListener
argument_list|()
block|{
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|actionEvent
parameter_list|)
block|{
name|int
name|i
init|=
name|table
operator|.
name|getSelectedRow
argument_list|()
decl_stmt|;
if|if
condition|(
name|i
operator|==
operator|-
literal|1
condition|)
return|return;
name|ExternalFileType
name|type
init|=
name|Globals
operator|.
name|prefs
operator|.
name|getExternalFileTypeByExt
argument_list|(
literal|"jstyle"
argument_list|)
decl_stmt|;
name|String
name|link
init|=
operator|(
operator|(
name|OOBibStyle
operator|)
name|tableModel
operator|.
name|getElementAt
argument_list|(
name|i
argument_list|)
operator|)
operator|.
name|getFile
argument_list|()
operator|.
name|getPath
argument_list|()
decl_stmt|;
try|try
block|{
if|if
condition|(
name|type
operator|!=
literal|null
condition|)
name|Util
operator|.
name|openExternalFileAnyFormat
argument_list|(
operator|new
name|MetaData
argument_list|()
argument_list|,
name|link
argument_list|,
name|type
argument_list|)
expr_stmt|;
else|else
name|Util
operator|.
name|openExternalFileUnknown
argument_list|(
name|frame
argument_list|,
literal|null
argument_list|,
operator|new
name|MetaData
argument_list|()
argument_list|,
name|link
argument_list|,
operator|new
name|UnknownExternalFileType
argument_list|(
literal|"jstyle"
argument_list|)
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
argument_list|)
expr_stmt|;
name|diag
operator|=
operator|new
name|JDialog
argument_list|(
name|frame
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Styles"
argument_list|)
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|dirsPanel
operator|=
operator|new
name|StyleDirectoriesPanel
argument_list|(
name|diag
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getStringArray
argument_list|(
literal|"ooStyleFileDirectories"
argument_list|)
argument_list|)
expr_stmt|;
comment|// The dirs panel is used to change the list of files and directories to include.
comment|// We register an ActionListener to update the style table when the list changes:
name|dirsPanel
operator|.
name|addActionListener
argument_list|(
operator|new
name|ActionListener
argument_list|()
block|{
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|event
parameter_list|)
block|{
name|readStyles
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|styles
operator|=
operator|new
name|BasicEventList
argument_list|<
name|OOBibStyle
argument_list|>
argument_list|()
expr_stmt|;
name|sortedStyles
operator|=
operator|new
name|SortedList
argument_list|<
name|OOBibStyle
argument_list|>
argument_list|(
name|styles
argument_list|)
expr_stmt|;
comment|// Create a preview panel for previewing styles:
name|preview
operator|=
operator|new
name|PreviewPanel
argument_list|(
literal|null
argument_list|,
operator|new
name|MetaData
argument_list|()
argument_list|,
literal|""
argument_list|)
expr_stmt|;
comment|// Use the test entry from the Preview settings tab in Preferences:
name|preview
operator|.
name|setEntry
argument_list|(
name|prevEntry
argument_list|)
expr_stmt|;
comment|//PreviewPrefsTab.getTestEntry());
name|tableModel
operator|=
operator|new
name|EventTableModel
argument_list|<
name|OOBibStyle
argument_list|>
argument_list|(
name|sortedStyles
argument_list|,
operator|new
name|StyleTableFormat
argument_list|()
argument_list|)
expr_stmt|;
name|table
operator|=
operator|new
name|JTable
argument_list|(
name|tableModel
argument_list|)
expr_stmt|;
name|TableColumnModel
name|cm
init|=
name|table
operator|.
name|getColumnModel
argument_list|()
decl_stmt|;
name|cm
operator|.
name|getColumn
argument_list|(
literal|0
argument_list|)
operator|.
name|setPreferredWidth
argument_list|(
literal|100
argument_list|)
expr_stmt|;
name|cm
operator|.
name|getColumn
argument_list|(
literal|1
argument_list|)
operator|.
name|setPreferredWidth
argument_list|(
literal|200
argument_list|)
expr_stmt|;
name|cm
operator|.
name|getColumn
argument_list|(
literal|2
argument_list|)
operator|.
name|setPreferredWidth
argument_list|(
literal|80
argument_list|)
expr_stmt|;
name|selectionModel
operator|=
operator|new
name|EventSelectionModel
argument_list|<
name|OOBibStyle
argument_list|>
argument_list|(
name|sortedStyles
argument_list|)
expr_stmt|;
name|table
operator|.
name|setSelectionModel
argument_list|(
name|selectionModel
argument_list|)
expr_stmt|;
name|table
operator|.
name|getSelectionModel
argument_list|()
operator|.
name|setSelectionMode
argument_list|(
name|ListSelectionModel
operator|.
name|SINGLE_SELECTION
argument_list|)
expr_stmt|;
name|table
operator|.
name|addMouseListener
argument_list|(
operator|new
name|MouseAdapter
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|mousePressed
parameter_list|(
name|MouseEvent
name|mouseEvent
parameter_list|)
block|{
if|if
condition|(
name|mouseEvent
operator|.
name|isPopupTrigger
argument_list|()
condition|)
name|tablePopup
argument_list|(
name|mouseEvent
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|mouseReleased
parameter_list|(
name|MouseEvent
name|mouseEvent
parameter_list|)
block|{
if|if
condition|(
name|mouseEvent
operator|.
name|isPopupTrigger
argument_list|()
condition|)
name|tablePopup
argument_list|(
name|mouseEvent
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|selectionModel
operator|.
name|getSelected
argument_list|()
operator|.
name|addListEventListener
argument_list|(
operator|new
name|EntrySelectionListener
argument_list|()
argument_list|)
expr_stmt|;
name|contentPane
operator|.
name|setTopComponent
argument_list|(
operator|new
name|JScrollPane
argument_list|(
name|table
argument_list|)
argument_list|)
expr_stmt|;
name|contentPane
operator|.
name|setBottomComponent
argument_list|(
name|preview
argument_list|)
expr_stmt|;
name|readStyles
argument_list|()
expr_stmt|;
name|DefaultFormBuilder
name|b
init|=
operator|new
name|DefaultFormBuilder
argument_list|(
operator|new
name|FormLayout
argument_list|(
literal|"fill:1dlu:grow"
argument_list|,
comment|//""));
comment|//"fill:1dlu:grow,
literal|"fill:pref, fill:pref, fill:270dlu:grow"
argument_list|)
argument_list|)
decl_stmt|;
name|b
operator|.
name|appendSeparator
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Select style"
argument_list|)
argument_list|)
expr_stmt|;
name|b
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|b
operator|.
name|append
argument_list|(
operator|new
name|JLabel
argument_list|(
literal|"<html>"
operator|+
name|Globals
operator|.
name|lang
argument_list|(
literal|"This is the list of available styles. Select the one you want to use."
argument_list|)
operator|+
literal|"</html>"
argument_list|)
argument_list|)
expr_stmt|;
name|b
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|b
operator|.
name|append
argument_list|(
name|contentPane
argument_list|)
expr_stmt|;
name|b
operator|.
name|getPanel
argument_list|()
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createEmptyBorder
argument_list|(
literal|5
argument_list|,
literal|5
argument_list|,
literal|5
argument_list|,
literal|5
argument_list|)
argument_list|)
expr_stmt|;
name|diag
operator|.
name|add
argument_list|(
name|b
operator|.
name|getPanel
argument_list|()
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|diag
operator|.
name|add
argument_list|(
name|dirsPanel
operator|.
name|getPanel
argument_list|()
argument_list|,
name|BorderLayout
operator|.
name|NORTH
argument_list|)
expr_stmt|;
name|ok
operator|.
name|addActionListener
argument_list|(
operator|new
name|ActionListener
argument_list|()
block|{
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|event
parameter_list|)
block|{
name|okPressed
operator|=
literal|true
expr_stmt|;
name|storeSettings
argument_list|()
expr_stmt|;
name|diag
operator|.
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|Action
name|cancelListener
init|=
operator|new
name|AbstractAction
argument_list|()
block|{
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|event
parameter_list|)
block|{
name|diag
operator|.
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
decl_stmt|;
name|cancel
operator|.
name|addActionListener
argument_list|(
name|cancelListener
argument_list|)
expr_stmt|;
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
name|addGridded
argument_list|(
name|ok
argument_list|)
expr_stmt|;
name|bb
operator|.
name|addGridded
argument_list|(
name|cancel
argument_list|)
expr_stmt|;
name|bb
operator|.
name|addGlue
argument_list|()
expr_stmt|;
name|bb
operator|.
name|getPanel
argument_list|()
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createEmptyBorder
argument_list|(
literal|5
argument_list|,
literal|5
argument_list|,
literal|5
argument_list|,
literal|5
argument_list|)
argument_list|)
expr_stmt|;
name|diag
operator|.
name|add
argument_list|(
name|bb
operator|.
name|getPanel
argument_list|()
argument_list|,
name|BorderLayout
operator|.
name|SOUTH
argument_list|)
expr_stmt|;
name|ActionMap
name|am
init|=
name|bb
operator|.
name|getPanel
argument_list|()
operator|.
name|getActionMap
argument_list|()
decl_stmt|;
name|InputMap
name|im
init|=
name|bb
operator|.
name|getPanel
argument_list|()
operator|.
name|getInputMap
argument_list|(
name|JComponent
operator|.
name|WHEN_IN_FOCUSED_WINDOW
argument_list|)
decl_stmt|;
name|im
operator|.
name|put
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getKey
argument_list|(
literal|"Close dialog"
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
name|cancelListener
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
name|frame
argument_list|)
expr_stmt|;
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
name|contentPane
operator|.
name|setDividerLocation
argument_list|(
name|contentPane
operator|.
name|getSize
argument_list|()
operator|.
name|height
operator|-
literal|150
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
DECL|method|setVisible (boolean visible)
specifier|public
name|void
name|setVisible
parameter_list|(
name|boolean
name|visible
parameter_list|)
block|{
name|okPressed
operator|=
literal|false
expr_stmt|;
name|diag
operator|.
name|setVisible
argument_list|(
name|visible
argument_list|)
expr_stmt|;
block|}
comment|/**      * Read all style files or directories of style files indicated by the current      * settings, and add the styles to the list of styles.      */
DECL|method|readStyles ()
specifier|private
name|void
name|readStyles
parameter_list|()
block|{
name|table
operator|.
name|clearSelection
argument_list|()
expr_stmt|;
name|styles
operator|.
name|getReadWriteLock
argument_list|()
operator|.
name|writeLock
argument_list|()
operator|.
name|lock
argument_list|()
expr_stmt|;
name|styles
operator|.
name|clear
argument_list|()
expr_stmt|;
for|for
control|(
name|StyleDirectoriesPanel
operator|.
name|DirElement
name|elm
range|:
name|dirsPanel
operator|.
name|getDirElements
argument_list|()
control|)
block|{
name|addStyles
argument_list|(
name|elm
operator|.
name|path
argument_list|,
name|elm
operator|.
name|recursive
argument_list|)
expr_stmt|;
block|}
name|styles
operator|.
name|getReadWriteLock
argument_list|()
operator|.
name|writeLock
argument_list|()
operator|.
name|unlock
argument_list|()
expr_stmt|;
name|selectLastUsed
argument_list|()
expr_stmt|;
block|}
comment|/**      * This method scans the current list of styles, and looks for the styles      * that was last used. If found, that style is selected. If not found,      * the first style is selected provided there are>0 styles.      */
DECL|method|selectLastUsed ()
specifier|private
name|void
name|selectLastUsed
parameter_list|()
block|{
comment|// Set the initial selection of the table:
if|if
condition|(
name|initSelection
operator|!=
literal|null
condition|)
block|{
name|boolean
name|found
init|=
literal|false
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|table
operator|.
name|getRowCount
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
operator|(
operator|(
name|OOBibStyle
operator|)
name|tableModel
operator|.
name|getElementAt
argument_list|(
name|i
argument_list|)
operator|)
operator|.
name|getFile
argument_list|()
operator|.
name|getPath
argument_list|()
operator|.
name|equals
argument_list|(
name|initSelection
argument_list|)
condition|)
block|{
name|table
operator|.
name|setRowSelectionInterval
argument_list|(
name|i
argument_list|,
name|i
argument_list|)
expr_stmt|;
name|found
operator|=
literal|true
expr_stmt|;
break|break;
block|}
block|}
if|if
condition|(
operator|!
name|found
operator|&&
operator|(
name|table
operator|.
name|getRowCount
argument_list|()
operator|>
literal|0
operator|)
condition|)
name|table
operator|.
name|setRowSelectionInterval
argument_list|(
literal|0
argument_list|,
literal|0
argument_list|)
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|table
operator|.
name|getRowCount
argument_list|()
operator|>
literal|0
condition|)
name|table
operator|.
name|setRowSelectionInterval
argument_list|(
literal|0
argument_list|,
literal|0
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * If the string dir indicates a file, parse it and add it to the list of styles if      * successful. If the string dir indicates a directory, parse all files looking like      * style files, and add them. The parameter recurse determines whether we should      * recurse into subdirectories.      * @param dir the directory or file to handle.      * @param recurse true indicates that we should recurse into subdirectories.      */
DECL|method|addStyles (String dir, boolean recurse)
specifier|private
name|void
name|addStyles
parameter_list|(
name|String
name|dir
parameter_list|,
name|boolean
name|recurse
parameter_list|)
block|{
name|File
name|dirF
init|=
operator|new
name|File
argument_list|(
name|dir
argument_list|)
decl_stmt|;
if|if
condition|(
name|dirF
operator|.
name|isDirectory
argument_list|()
condition|)
block|{
name|File
index|[]
name|files
init|=
name|dirF
operator|.
name|listFiles
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|files
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|File
name|file
init|=
name|files
index|[
name|i
index|]
decl_stmt|;
comment|// If the file looks like a style file, parse it:
if|if
condition|(
operator|!
name|file
operator|.
name|isDirectory
argument_list|()
operator|&&
operator|(
name|file
operator|.
name|getName
argument_list|()
operator|.
name|endsWith
argument_list|(
name|STYLE_FILE_EXTENSION
argument_list|)
operator|)
condition|)
block|{
name|addSingleFile
argument_list|(
name|file
argument_list|)
expr_stmt|;
block|}
comment|// If the file is a directory, and we should recurse, do:
elseif|else
if|if
condition|(
name|file
operator|.
name|isDirectory
argument_list|()
operator|&&
name|recurse
condition|)
block|{
name|addStyles
argument_list|(
name|file
operator|.
name|getPath
argument_list|()
argument_list|,
name|recurse
argument_list|)
expr_stmt|;
block|}
block|}
block|}
else|else
block|{
comment|// The file wasn't a directory, so we simply parse it:
name|addSingleFile
argument_list|(
name|dirF
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Parse a single file, and add it to the list of styles if parse was successful.      * @param file the file to parse.      */
DECL|method|addSingleFile (File file)
specifier|private
name|void
name|addSingleFile
parameter_list|(
name|File
name|file
parameter_list|)
block|{
try|try
block|{
name|OOBibStyle
name|style
init|=
operator|new
name|OOBibStyle
argument_list|(
name|file
argument_list|)
decl_stmt|;
comment|// Check if the parse was successful before adding it:
if|if
condition|(
name|style
operator|.
name|isValid
argument_list|()
operator|&&
operator|!
name|styles
operator|.
name|contains
argument_list|(
name|style
argument_list|)
condition|)
name|styles
operator|.
name|add
argument_list|(
name|style
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Unable to read style file: '"
operator|+
name|file
operator|.
name|getPath
argument_list|()
operator|+
literal|"'"
argument_list|)
expr_stmt|;
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
DECL|method|storeSettings ()
specifier|public
name|void
name|storeSettings
parameter_list|()
block|{
name|String
index|[]
name|dirs
init|=
name|dirsPanel
operator|.
name|getStringArray
argument_list|()
decl_stmt|;
name|Globals
operator|.
name|prefs
operator|.
name|putStringArray
argument_list|(
literal|"ooStyleFileDirectories"
argument_list|,
name|dirs
argument_list|)
expr_stmt|;
name|OOBibStyle
name|selected
init|=
name|getSelectedStyle
argument_list|()
decl_stmt|;
if|if
condition|(
name|selected
operator|!=
literal|null
condition|)
name|Globals
operator|.
name|prefs
operator|.
name|put
argument_list|(
literal|"ooBibliographyStyleFile"
argument_list|,
name|selected
operator|.
name|getFile
argument_list|()
operator|.
name|getPath
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * Get the currently selected style.      * @return the selected style, or null if no style is selected.      */
DECL|method|getSelectedStyle ()
specifier|public
name|OOBibStyle
name|getSelectedStyle
parameter_list|()
block|{
if|if
condition|(
name|selectionModel
operator|.
name|getSelected
argument_list|()
operator|.
name|size
argument_list|()
operator|>
literal|0
condition|)
return|return
name|selectionModel
operator|.
name|getSelected
argument_list|()
operator|.
name|get
argument_list|(
literal|0
argument_list|)
return|;
else|else
return|return
literal|null
return|;
block|}
DECL|method|setupPrevEntry ()
specifier|private
name|void
name|setupPrevEntry
parameter_list|()
block|{
name|prevEntry
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
literal|"Smith, Bill and Jones, Bob and Williams, Jeff"
argument_list|)
expr_stmt|;
name|prevEntry
operator|.
name|setField
argument_list|(
literal|"editor"
argument_list|,
literal|"Taylor, Phil"
argument_list|)
expr_stmt|;
name|prevEntry
operator|.
name|setField
argument_list|(
literal|"title"
argument_list|,
literal|"Title of the test entry for reference styles"
argument_list|)
expr_stmt|;
name|prevEntry
operator|.
name|setField
argument_list|(
literal|"volume"
argument_list|,
literal|"34"
argument_list|)
expr_stmt|;
name|prevEntry
operator|.
name|setField
argument_list|(
literal|"year"
argument_list|,
literal|"2008"
argument_list|)
expr_stmt|;
name|prevEntry
operator|.
name|setField
argument_list|(
literal|"journal"
argument_list|,
literal|"BibTeX journal"
argument_list|)
expr_stmt|;
name|prevEntry
operator|.
name|setField
argument_list|(
literal|"publisher"
argument_list|,
literal|"JabRef publishing"
argument_list|)
expr_stmt|;
name|prevEntry
operator|.
name|setField
argument_list|(
literal|"address"
argument_list|,
literal|"Trondheim"
argument_list|)
expr_stmt|;
name|prevEntry
operator|.
name|setField
argument_list|(
literal|"www"
argument_list|,
literal|"http://jabref.sf.net"
argument_list|)
expr_stmt|;
block|}
DECL|class|StyleTableFormat
specifier|static
class|class
name|StyleTableFormat
implements|implements
name|TableFormat
argument_list|<
name|OOBibStyle
argument_list|>
block|{
DECL|method|getColumnCount ()
specifier|public
name|int
name|getColumnCount
parameter_list|()
block|{
return|return
literal|3
return|;
block|}
DECL|method|getColumnName (int i)
specifier|public
name|String
name|getColumnName
parameter_list|(
name|int
name|i
parameter_list|)
block|{
switch|switch
condition|(
name|i
condition|)
block|{
case|case
literal|0
case|:
return|return
name|Globals
operator|.
name|lang
argument_list|(
literal|"Name"
argument_list|)
return|;
case|case
literal|1
case|:
return|return
name|Globals
operator|.
name|lang
argument_list|(
literal|"Journals"
argument_list|)
return|;
case|case
literal|2
case|:
return|return
name|Globals
operator|.
name|lang
argument_list|(
literal|"File"
argument_list|)
return|;
default|default:
return|return
literal|""
return|;
block|}
block|}
DECL|method|getColumnValue (OOBibStyle style, int i)
specifier|public
name|Object
name|getColumnValue
parameter_list|(
name|OOBibStyle
name|style
parameter_list|,
name|int
name|i
parameter_list|)
block|{
switch|switch
condition|(
name|i
condition|)
block|{
case|case
literal|0
case|:
return|return
name|style
operator|.
name|getName
argument_list|()
return|;
case|case
literal|1
case|:
return|return
name|formatJournals
argument_list|(
name|style
operator|.
name|getJournals
argument_list|()
argument_list|)
return|;
case|case
literal|2
case|:
return|return
name|style
operator|.
name|getFile
argument_list|()
operator|.
name|getName
argument_list|()
return|;
default|default:
return|return
literal|""
return|;
block|}
block|}
DECL|method|formatJournals (Set<String> journals)
specifier|private
name|String
name|formatJournals
parameter_list|(
name|Set
argument_list|<
name|String
argument_list|>
name|journals
parameter_list|)
block|{
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|(
literal|""
argument_list|)
decl_stmt|;
for|for
control|(
name|Iterator
argument_list|<
name|String
argument_list|>
name|i
init|=
name|journals
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|i
operator|.
name|next
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|i
operator|.
name|hasNext
argument_list|()
condition|)
name|sb
operator|.
name|append
argument_list|(
literal|", "
argument_list|)
expr_stmt|;
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
DECL|method|isOkPressed ()
specifier|public
name|boolean
name|isOkPressed
parameter_list|()
block|{
return|return
name|okPressed
return|;
block|}
DECL|method|tablePopup (MouseEvent e)
specifier|protected
name|void
name|tablePopup
parameter_list|(
name|MouseEvent
name|e
parameter_list|)
block|{
name|popup
operator|.
name|show
argument_list|(
name|e
operator|.
name|getComponent
argument_list|()
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
comment|/**      * The listener for the Glazed list monitoring the current selection.      * When selection changes, we need to update the preview panel.      */
DECL|class|EntrySelectionListener
class|class
name|EntrySelectionListener
implements|implements
name|ListEventListener
argument_list|<
name|OOBibStyle
argument_list|>
block|{
DECL|method|listChanged (ListEvent<OOBibStyle> listEvent)
specifier|public
name|void
name|listChanged
parameter_list|(
name|ListEvent
argument_list|<
name|OOBibStyle
argument_list|>
name|listEvent
parameter_list|)
block|{
if|if
condition|(
name|listEvent
operator|.
name|getSourceList
argument_list|()
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
block|{
name|OOBibStyle
name|style
init|=
name|listEvent
operator|.
name|getSourceList
argument_list|()
operator|.
name|get
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|initSelection
operator|=
name|style
operator|.
name|getFile
argument_list|()
operator|.
name|getPath
argument_list|()
expr_stmt|;
name|preview
operator|.
name|setLayout
argument_list|(
name|style
operator|.
name|getReferenceFormat
argument_list|(
literal|"default"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Update the preview's entry:
name|contentPane
operator|.
name|setDividerLocation
argument_list|(
name|contentPane
operator|.
name|getSize
argument_list|()
operator|.
name|height
operator|-
literal|150
argument_list|)
expr_stmt|;
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
name|preview
operator|.
name|update
argument_list|()
expr_stmt|;
name|preview
operator|.
name|scrollRectToVisible
argument_list|(
name|toRect
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
end_class

end_unit

