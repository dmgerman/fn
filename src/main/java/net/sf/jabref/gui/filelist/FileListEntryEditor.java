begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.filelist
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|filelist
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
name|awt
operator|.
name|event
operator|.
name|WindowAdapter
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
name|WindowEvent
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
name|Optional
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Pattern
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
name|ActionMap
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|BorderFactory
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|DefaultComboBoxModel
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|InputMap
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
name|JComboBox
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
name|JProgressBar
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JTextField
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|SwingConstants
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
name|javax
operator|.
name|swing
operator|.
name|event
operator|.
name|DocumentEvent
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|event
operator|.
name|DocumentListener
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|FileDialog
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
name|desktop
operator|.
name|JabRefDesktop
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
name|externalfiletype
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
name|gui
operator|.
name|externalfiletype
operator|.
name|ExternalFileTypes
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
name|externalfiletype
operator|.
name|UnknownExternalFileType
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
name|org
operator|.
name|jabref
operator|.
name|preferences
operator|.
name|JabRefPreferences
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

begin_comment
comment|/**  * This class produces a dialog box for editing a single file link from a Bibtex entry.  *  * The information to be edited includes the file description, the link itself and the  * file type. The dialog also includes convenience buttons for quick linking.  *  * For use when downloading files, this class also offers a progress bar and a "Downloading..."  * label that can be hidden when the download is complete.  */
end_comment

begin_class
DECL|class|FileListEntryEditor
specifier|public
class|class
name|FileListEntryEditor
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
name|FileListEntryEditor
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|diag
specifier|private
name|JDialog
name|diag
decl_stmt|;
DECL|field|link
specifier|private
specifier|final
name|JTextField
name|link
init|=
operator|new
name|JTextField
argument_list|()
decl_stmt|;
DECL|field|description
specifier|private
specifier|final
name|JTextField
name|description
init|=
operator|new
name|JTextField
argument_list|()
decl_stmt|;
DECL|field|ok
specifier|private
specifier|final
name|JButton
name|ok
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
DECL|field|types
specifier|private
specifier|final
name|JComboBox
argument_list|<
name|ExternalFileType
argument_list|>
name|types
decl_stmt|;
DECL|field|prog
specifier|private
specifier|final
name|JProgressBar
name|prog
init|=
operator|new
name|JProgressBar
argument_list|(
name|SwingConstants
operator|.
name|HORIZONTAL
argument_list|)
decl_stmt|;
DECL|field|downloadLabel
specifier|private
specifier|final
name|JLabel
name|downloadLabel
init|=
operator|new
name|JLabel
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Downloading..."
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|externalConfirm
specifier|private
name|ConfirmCloseFileListEntryEditor
name|externalConfirm
decl_stmt|;
DECL|field|entry
specifier|private
name|FileListEntry
name|entry
decl_stmt|;
comment|//Do not make this variable final, as then the lambda action listener will fail on compiÃ¶e
DECL|field|databaseContext
specifier|private
name|BibDatabaseContext
name|databaseContext
decl_stmt|;
DECL|field|okPressed
specifier|private
name|boolean
name|okPressed
decl_stmt|;
DECL|field|okDisabledExternally
specifier|private
name|boolean
name|okDisabledExternally
decl_stmt|;
DECL|field|openBrowseWhenShown
specifier|private
name|boolean
name|openBrowseWhenShown
decl_stmt|;
DECL|field|dontOpenBrowseUntilDisposed
specifier|private
name|boolean
name|dontOpenBrowseUntilDisposed
decl_stmt|;
comment|//Do not make this variable final, as then the lambda action listener will fail on compile
DECL|field|frame
specifier|private
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|REMOTE_LINK_PATTERN
specifier|private
specifier|static
specifier|final
name|Pattern
name|REMOTE_LINK_PATTERN
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"[a-z]+://.*"
argument_list|)
decl_stmt|;
DECL|method|FileListEntryEditor (JabRefFrame frame, FileListEntry entry, boolean showProgressBar, boolean showOpenButton, BibDatabaseContext databaseContext)
specifier|public
name|FileListEntryEditor
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|FileListEntry
name|entry
parameter_list|,
name|boolean
name|showProgressBar
parameter_list|,
name|boolean
name|showOpenButton
parameter_list|,
name|BibDatabaseContext
name|databaseContext
parameter_list|)
block|{
name|this
operator|.
name|entry
operator|=
name|entry
expr_stmt|;
name|this
operator|.
name|databaseContext
operator|=
name|databaseContext
expr_stmt|;
name|this
operator|.
name|frame
operator|=
name|frame
expr_stmt|;
name|ActionListener
name|okAction
init|=
name|e
lambda|->
block|{
comment|// If OK button is disabled, ignore this event:
if|if
condition|(
operator|!
name|ok
operator|.
name|isEnabled
argument_list|()
condition|)
block|{
return|return;
block|}
comment|// If necessary, ask the external confirm object whether we are ready to close.
if|if
condition|(
name|externalConfirm
operator|!=
literal|null
condition|)
block|{
comment|// Construct an updated FileListEntry:
name|storeSettings
argument_list|(
name|entry
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|externalConfirm
operator|.
name|confirmClose
argument_list|(
name|entry
argument_list|)
condition|)
block|{
return|return;
block|}
block|}
name|diag
operator|.
name|dispose
argument_list|()
expr_stmt|;
name|storeSettings
argument_list|(
name|FileListEntryEditor
operator|.
name|this
operator|.
name|entry
argument_list|)
expr_stmt|;
name|okPressed
operator|=
literal|true
expr_stmt|;
block|}
decl_stmt|;
name|types
operator|=
operator|new
name|JComboBox
argument_list|<>
argument_list|()
expr_stmt|;
name|types
operator|.
name|addItemListener
argument_list|(
name|itemEvent
lambda|->
block|{
if|if
condition|(
operator|!
name|okDisabledExternally
condition|)
block|{
name|ok
operator|.
name|setEnabled
argument_list|(
name|types
operator|.
name|getSelectedItem
argument_list|()
operator|!=
literal|null
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|FormLayout
name|fileDialog
init|=
operator|new
name|FormLayout
argument_list|(
literal|"left:pref, 4dlu, fill:400dlu, 4dlu, fill:pref, 4dlu, fill:pref"
argument_list|,
literal|"p, 8dlu, p, 8dlu, p"
argument_list|)
decl_stmt|;
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
name|fileDialog
argument_list|)
decl_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Link"
argument_list|)
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
name|link
argument_list|)
operator|.
name|xy
argument_list|(
literal|3
argument_list|,
literal|1
argument_list|)
expr_stmt|;
specifier|final
name|JButton
name|browseBut
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Browse"
argument_list|)
argument_list|)
decl_stmt|;
name|browseBut
operator|.
name|addActionListener
argument_list|(
name|browsePressed
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|browseBut
argument_list|)
operator|.
name|xy
argument_list|(
literal|5
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|JButton
name|open
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Open"
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|showOpenButton
condition|)
block|{
name|builder
operator|.
name|add
argument_list|(
name|open
argument_list|)
operator|.
name|xy
argument_list|(
literal|7
argument_list|,
literal|1
argument_list|)
expr_stmt|;
block|}
name|builder
operator|.
name|add
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Description"
argument_list|)
argument_list|)
operator|.
name|xy
argument_list|(
literal|1
argument_list|,
literal|3
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|description
argument_list|)
operator|.
name|xyw
argument_list|(
literal|3
argument_list|,
literal|3
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|builder
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
name|builder
operator|.
name|add
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"File type"
argument_list|)
argument_list|)
operator|.
name|xy
argument_list|(
literal|1
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|types
argument_list|)
operator|.
name|xyw
argument_list|(
literal|3
argument_list|,
literal|5
argument_list|,
literal|5
argument_list|)
expr_stmt|;
if|if
condition|(
name|showProgressBar
condition|)
block|{
name|builder
operator|.
name|appendRows
argument_list|(
literal|"2dlu, p"
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|downloadLabel
argument_list|)
operator|.
name|xy
argument_list|(
literal|1
argument_list|,
literal|7
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|prog
argument_list|)
operator|.
name|xyw
argument_list|(
literal|3
argument_list|,
literal|7
argument_list|,
literal|3
argument_list|)
expr_stmt|;
block|}
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
name|addRelatedGap
argument_list|()
expr_stmt|;
name|bb
operator|.
name|addButton
argument_list|(
name|ok
argument_list|)
expr_stmt|;
name|JButton
name|cancel
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
name|bb
operator|.
name|addButton
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
name|ok
operator|.
name|addActionListener
argument_list|(
name|okAction
argument_list|)
expr_stmt|;
comment|// Add OK action to the two text fields to simplify entering:
name|link
operator|.
name|addActionListener
argument_list|(
name|okAction
argument_list|)
expr_stmt|;
name|description
operator|.
name|addActionListener
argument_list|(
name|okAction
argument_list|)
expr_stmt|;
name|open
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
name|openFile
argument_list|()
argument_list|)
expr_stmt|;
name|AbstractAction
name|cancelAction
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
name|cancelAction
argument_list|)
expr_stmt|;
comment|// Key bindings:
name|ActionMap
name|am
init|=
name|builder
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
name|builder
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
name|cancelAction
argument_list|)
expr_stmt|;
name|link
operator|.
name|getDocument
argument_list|()
operator|.
name|addDocumentListener
argument_list|(
operator|new
name|DocumentListener
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|insertUpdate
parameter_list|(
name|DocumentEvent
name|documentEvent
parameter_list|)
block|{
name|checkExtension
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|removeUpdate
parameter_list|(
name|DocumentEvent
name|documentEvent
parameter_list|)
block|{
comment|// Do nothing
block|}
annotation|@
name|Override
specifier|public
name|void
name|changedUpdate
parameter_list|(
name|DocumentEvent
name|documentEvent
parameter_list|)
block|{
name|checkExtension
argument_list|()
expr_stmt|;
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
name|Localization
operator|.
name|lang
argument_list|(
literal|"Save file"
argument_list|)
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|diag
operator|.
name|getContentPane
argument_list|()
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
name|CENTER
argument_list|)
expr_stmt|;
name|diag
operator|.
name|getContentPane
argument_list|()
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
name|diag
operator|.
name|addWindowListener
argument_list|(
operator|new
name|WindowAdapter
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|windowActivated
parameter_list|(
name|WindowEvent
name|event
parameter_list|)
block|{
if|if
condition|(
name|openBrowseWhenShown
operator|&&
operator|!
name|dontOpenBrowseUntilDisposed
condition|)
block|{
name|dontOpenBrowseUntilDisposed
operator|=
literal|true
expr_stmt|;
name|SwingUtilities
operator|.
name|invokeLater
argument_list|(
parameter_list|()
lambda|->
name|browsePressed
operator|.
name|actionPerformed
argument_list|(
operator|new
name|ActionEvent
argument_list|(
name|browseBut
argument_list|,
literal|0
argument_list|,
literal|""
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|windowClosed
parameter_list|(
name|WindowEvent
name|event
parameter_list|)
block|{
name|dontOpenBrowseUntilDisposed
operator|=
literal|false
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|setValues
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
DECL|method|checkExtension ()
specifier|private
name|void
name|checkExtension
parameter_list|()
block|{
if|if
condition|(
operator|(
name|types
operator|.
name|getSelectedIndex
argument_list|()
operator|==
operator|-
literal|1
operator|)
operator|&&
operator|(
operator|!
name|link
operator|.
name|getText
argument_list|()
operator|.
name|trim
argument_list|()
operator|.
name|isEmpty
argument_list|()
operator|)
condition|)
block|{
comment|// Check if this looks like a remote link:
if|if
condition|(
name|FileListEntryEditor
operator|.
name|REMOTE_LINK_PATTERN
operator|.
name|matcher
argument_list|(
name|link
operator|.
name|getText
argument_list|()
argument_list|)
operator|.
name|matches
argument_list|()
condition|)
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
name|getExternalFileTypeByExt
argument_list|(
literal|"html"
argument_list|)
decl_stmt|;
if|if
condition|(
name|type
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|types
operator|.
name|setSelectedItem
argument_list|(
name|type
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
return|return;
block|}
block|}
comment|// Try to guess the file type:
name|String
name|theLink
init|=
name|link
operator|.
name|getText
argument_list|()
operator|.
name|trim
argument_list|()
decl_stmt|;
name|ExternalFileTypes
operator|.
name|getInstance
argument_list|()
operator|.
name|getExternalFileTypeForName
argument_list|(
name|theLink
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|types
operator|::
name|setSelectedItem
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|openFile ()
specifier|private
name|void
name|openFile
parameter_list|()
block|{
name|ExternalFileType
name|type
init|=
operator|(
name|ExternalFileType
operator|)
name|types
operator|.
name|getSelectedItem
argument_list|()
decl_stmt|;
if|if
condition|(
name|type
operator|!=
literal|null
condition|)
block|{
try|try
block|{
name|JabRefDesktop
operator|.
name|openExternalFileAnyFormat
argument_list|(
name|databaseContext
argument_list|,
name|link
operator|.
name|getText
argument_list|()
argument_list|,
name|Optional
operator|.
name|of
argument_list|(
name|type
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
name|LOGGER
operator|.
name|error
argument_list|(
literal|"File could not be opened"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
block|}
DECL|method|setExternalConfirm (ConfirmCloseFileListEntryEditor eC)
specifier|public
name|void
name|setExternalConfirm
parameter_list|(
name|ConfirmCloseFileListEntryEditor
name|eC
parameter_list|)
block|{
name|this
operator|.
name|externalConfirm
operator|=
name|eC
expr_stmt|;
block|}
DECL|method|setOkEnabled (boolean enabled)
specifier|public
name|void
name|setOkEnabled
parameter_list|(
name|boolean
name|enabled
parameter_list|)
block|{
name|okDisabledExternally
operator|=
operator|!
name|enabled
expr_stmt|;
name|ok
operator|.
name|setEnabled
argument_list|(
name|enabled
argument_list|)
expr_stmt|;
block|}
DECL|method|getProgressBar ()
specifier|public
name|JProgressBar
name|getProgressBar
parameter_list|()
block|{
return|return
name|prog
return|;
block|}
DECL|method|getProgressBarLabel ()
specifier|public
name|JLabel
name|getProgressBarLabel
parameter_list|()
block|{
return|return
name|downloadLabel
return|;
block|}
DECL|method|setEntry (FileListEntry entry)
specifier|public
name|void
name|setEntry
parameter_list|(
name|FileListEntry
name|entry
parameter_list|)
block|{
name|this
operator|.
name|entry
operator|=
name|entry
expr_stmt|;
name|setValues
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
DECL|method|setVisible (boolean visible, boolean openBrowse)
specifier|public
name|void
name|setVisible
parameter_list|(
name|boolean
name|visible
parameter_list|,
name|boolean
name|openBrowse
parameter_list|)
block|{
name|openBrowseWhenShown
operator|=
name|openBrowse
operator|&&
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|ALLOW_FILE_AUTO_OPEN_BROWSE
argument_list|)
expr_stmt|;
if|if
condition|(
name|visible
condition|)
block|{
name|okPressed
operator|=
literal|false
expr_stmt|;
block|}
name|diag
operator|.
name|setVisible
argument_list|(
name|visible
argument_list|)
expr_stmt|;
block|}
DECL|method|isVisible ()
specifier|public
name|boolean
name|isVisible
parameter_list|()
block|{
return|return
operator|(
name|diag
operator|!=
literal|null
operator|)
operator|&&
name|diag
operator|.
name|isVisible
argument_list|()
return|;
block|}
DECL|method|setValues (FileListEntry entry)
specifier|private
name|void
name|setValues
parameter_list|(
name|FileListEntry
name|entry
parameter_list|)
block|{
name|description
operator|.
name|setText
argument_list|(
name|entry
operator|.
name|description
argument_list|)
expr_stmt|;
name|link
operator|.
name|setText
argument_list|(
name|entry
operator|.
name|link
argument_list|)
expr_stmt|;
name|Collection
argument_list|<
name|ExternalFileType
argument_list|>
name|list
init|=
name|ExternalFileTypes
operator|.
name|getInstance
argument_list|()
operator|.
name|getExternalFileTypeSelection
argument_list|()
decl_stmt|;
name|types
operator|.
name|setModel
argument_list|(
operator|new
name|DefaultComboBoxModel
argument_list|<>
argument_list|(
name|list
operator|.
name|toArray
argument_list|(
operator|new
name|ExternalFileType
index|[
name|list
operator|.
name|size
argument_list|()
index|]
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|types
operator|.
name|setSelectedIndex
argument_list|(
operator|-
literal|1
argument_list|)
expr_stmt|;
comment|// See what is a reasonable selection for the type combobox:
if|if
condition|(
operator|(
name|entry
operator|.
name|type
operator|.
name|isPresent
argument_list|()
operator|)
operator|&&
operator|!
operator|(
name|entry
operator|.
name|type
operator|.
name|get
argument_list|()
operator|instanceof
name|UnknownExternalFileType
operator|)
condition|)
block|{
name|types
operator|.
name|setSelectedItem
argument_list|(
name|entry
operator|.
name|type
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
operator|(
name|entry
operator|.
name|link
operator|!=
literal|null
operator|)
operator|&&
operator|(
operator|!
name|entry
operator|.
name|link
operator|.
name|isEmpty
argument_list|()
operator|)
condition|)
block|{
name|checkExtension
argument_list|()
expr_stmt|;
block|}
block|}
DECL|method|storeSettings (FileListEntry listEntry)
specifier|private
name|void
name|storeSettings
parameter_list|(
name|FileListEntry
name|listEntry
parameter_list|)
block|{
name|String
name|descriptionText
init|=
name|this
operator|.
name|description
operator|.
name|getText
argument_list|()
operator|.
name|trim
argument_list|()
decl_stmt|;
name|String
name|fileLink
init|=
literal|""
decl_stmt|;
comment|// See if we should trim the file link to be relative to the file directory:
try|try
block|{
name|List
argument_list|<
name|String
argument_list|>
name|dirs
init|=
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
decl_stmt|;
if|if
condition|(
name|dirs
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|fileLink
operator|=
name|this
operator|.
name|link
operator|.
name|getText
argument_list|()
operator|.
name|trim
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|boolean
name|found
init|=
literal|false
decl_stmt|;
for|for
control|(
name|String
name|dir
range|:
name|dirs
control|)
block|{
name|String
name|canPath
init|=
operator|(
operator|new
name|File
argument_list|(
name|dir
argument_list|)
operator|)
operator|.
name|getCanonicalPath
argument_list|()
decl_stmt|;
name|File
name|fl
init|=
operator|new
name|File
argument_list|(
name|this
operator|.
name|link
operator|.
name|getText
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|fl
operator|.
name|isAbsolute
argument_list|()
condition|)
block|{
name|String
name|flPath
init|=
name|fl
operator|.
name|getCanonicalPath
argument_list|()
decl_stmt|;
if|if
condition|(
operator|(
name|flPath
operator|.
name|length
argument_list|()
operator|>
name|canPath
operator|.
name|length
argument_list|()
operator|)
operator|&&
operator|(
name|flPath
operator|.
name|startsWith
argument_list|(
name|canPath
argument_list|)
operator|)
condition|)
block|{
name|fileLink
operator|=
name|fl
operator|.
name|getCanonicalPath
argument_list|()
operator|.
name|substring
argument_list|(
name|canPath
operator|.
name|length
argument_list|()
operator|+
literal|1
argument_list|)
expr_stmt|;
name|found
operator|=
literal|true
expr_stmt|;
break|break;
block|}
block|}
block|}
if|if
condition|(
operator|!
name|found
condition|)
block|{
name|fileLink
operator|=
name|this
operator|.
name|link
operator|.
name|getText
argument_list|()
operator|.
name|trim
argument_list|()
expr_stmt|;
block|}
block|}
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{
comment|// Don't think this should happen, but set the file link directly as a fallback:
name|fileLink
operator|=
name|this
operator|.
name|link
operator|.
name|getText
argument_list|()
operator|.
name|trim
argument_list|()
expr_stmt|;
block|}
name|ExternalFileType
name|type
init|=
operator|(
name|ExternalFileType
operator|)
name|types
operator|.
name|getSelectedItem
argument_list|()
decl_stmt|;
name|listEntry
operator|.
name|description
operator|=
name|descriptionText
expr_stmt|;
name|listEntry
operator|.
name|type
operator|=
name|Optional
operator|.
name|ofNullable
argument_list|(
name|type
argument_list|)
expr_stmt|;
name|listEntry
operator|.
name|link
operator|=
name|fileLink
expr_stmt|;
block|}
DECL|method|okPressed ()
specifier|public
name|boolean
name|okPressed
parameter_list|()
block|{
return|return
name|okPressed
return|;
block|}
DECL|field|browsePressed
specifier|private
specifier|final
name|ActionListener
name|browsePressed
init|=
name|e
lambda|->
block|{
name|String
name|filePath
init|=
name|link
operator|.
name|getText
argument_list|()
operator|.
name|trim
argument_list|()
decl_stmt|;
name|Optional
argument_list|<
name|File
argument_list|>
name|file
init|=
name|FileUtil
operator|.
name|expandFilename
argument_list|(
name|this
operator|.
name|databaseContext
argument_list|,
name|filePath
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getFileDirectoryPreferences
argument_list|()
argument_list|)
decl_stmt|;
name|String
name|workingDir
decl_stmt|;
comment|// no file set yet or found
if|if
condition|(
name|file
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|workingDir
operator|=
name|file
operator|.
name|get
argument_list|()
operator|.
name|getPath
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|workingDir
operator|=
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|WORKING_DIRECTORY
argument_list|)
expr_stmt|;
block|}
name|Optional
argument_list|<
name|Path
argument_list|>
name|path
init|=
operator|new
name|FileDialog
argument_list|(
name|this
operator|.
name|frame
argument_list|,
name|workingDir
argument_list|)
operator|.
name|showDialogAndGetSelectedFile
argument_list|()
decl_stmt|;
name|path
operator|.
name|ifPresent
argument_list|(
name|selection
lambda|->
block|{
name|File
name|newFile
init|=
name|selection
operator|.
name|toFile
argument_list|()
decl_stmt|;
comment|// Store the directory for next time:
name|Globals
operator|.
name|prefs
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|WORKING_DIRECTORY
argument_list|,
name|newFile
operator|.
name|getPath
argument_list|()
argument_list|)
expr_stmt|;
comment|// If the file is below the file directory, make the path relative:
name|List
argument_list|<
name|String
argument_list|>
name|fileDirs
init|=
name|this
operator|.
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
decl_stmt|;
name|newFile
operator|=
name|FileUtil
operator|.
name|shortenFileName
argument_list|(
name|newFile
argument_list|,
name|fileDirs
argument_list|)
expr_stmt|;
name|link
operator|.
name|setText
argument_list|(
name|newFile
operator|.
name|getPath
argument_list|()
argument_list|)
expr_stmt|;
name|link
operator|.
name|requestFocus
argument_list|()
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
block|}
decl_stmt|;
block|}
end_class

end_unit

