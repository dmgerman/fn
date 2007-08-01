begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
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
name|GUIGlobals
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
name|MetaData
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
name|Util
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
name|ConfirmCloseFileListEntryEditor
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

begin_comment
comment|/**  * This class produces a dialog box for editing a single file link from a Bibtex entry.  *  * The information to be edited includes the file description, the link itself and the  * file type. The dialog also includes convenience buttons for quick linking.  *  * For use when downloading files, this class also offers a progress bar and a "Downloading..."  * label that can be hidden when the download is complete.  */
end_comment

begin_class
DECL|class|FileListEntryEditor
specifier|public
class|class
name|FileListEntryEditor
block|{
DECL|field|diag
name|JDialog
name|diag
decl_stmt|;
DECL|field|link
DECL|field|description
name|JTextField
name|link
init|=
operator|new
name|JTextField
argument_list|()
decl_stmt|,
name|description
init|=
operator|new
name|JTextField
argument_list|()
decl_stmt|;
DECL|field|ok
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
DECL|field|types
name|JComboBox
name|types
decl_stmt|;
DECL|field|prog
name|JProgressBar
name|prog
init|=
operator|new
name|JProgressBar
argument_list|(
name|JProgressBar
operator|.
name|HORIZONTAL
argument_list|)
decl_stmt|;
DECL|field|downloadLabel
name|JLabel
name|downloadLabel
init|=
operator|new
name|JLabel
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Downloading..."
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|externalConfirm
name|ConfirmCloseFileListEntryEditor
name|externalConfirm
init|=
literal|null
decl_stmt|;
DECL|field|entry
specifier|private
name|FileListEntry
name|entry
decl_stmt|;
DECL|field|metaData
specifier|private
name|MetaData
name|metaData
decl_stmt|;
DECL|field|okPressed
specifier|private
name|boolean
name|okPressed
init|=
literal|false
decl_stmt|;
DECL|method|FileListEntryEditor (JFrame parent, FileListEntry entry, boolean showProgressBar, MetaData metaData)
specifier|public
name|FileListEntryEditor
parameter_list|(
name|JFrame
name|parent
parameter_list|,
name|FileListEntry
name|entry
parameter_list|,
name|boolean
name|showProgressBar
parameter_list|,
name|MetaData
name|metaData
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
name|metaData
operator|=
name|metaData
expr_stmt|;
name|types
operator|=
operator|new
name|JComboBox
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getExternalFileTypeSelection
argument_list|()
argument_list|)
expr_stmt|;
name|types
operator|.
name|addItemListener
argument_list|(
operator|new
name|ItemListener
argument_list|()
block|{
specifier|public
name|void
name|itemStateChanged
parameter_list|(
name|ItemEvent
name|itemEvent
parameter_list|)
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
name|DefaultFormBuilder
name|builder
init|=
operator|new
name|DefaultFormBuilder
argument_list|(
operator|new
name|FormLayout
argument_list|(
literal|"left:pref, 4dlu, fill:150dlu, 4dlu, fill:pref"
argument_list|,
literal|""
argument_list|)
argument_list|)
decl_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Link"
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|link
argument_list|)
expr_stmt|;
name|BrowseListener
name|browse
init|=
operator|new
name|BrowseListener
argument_list|(
name|parent
argument_list|,
name|link
argument_list|)
decl_stmt|;
name|JButton
name|browseBut
init|=
operator|new
name|JButton
argument_list|(
name|Globals
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
name|browse
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|browseBut
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Description"
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|description
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
name|nextLine
argument_list|()
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"File type"
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|types
argument_list|)
expr_stmt|;
if|if
condition|(
name|showProgressBar
condition|)
block|{
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|downloadLabel
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|prog
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
name|e
parameter_list|)
block|{
comment|// If necessary, ask the external confirm object whether we are ready to close.
if|if
condition|(
name|externalConfirm
operator|!=
literal|null
condition|)
block|{
comment|// Construct an updated FileListEntry:
name|FileListEntry
name|testEntry
init|=
operator|new
name|FileListEntry
argument_list|(
literal|""
argument_list|,
literal|""
argument_list|,
literal|null
argument_list|)
decl_stmt|;
name|storeSettings
argument_list|(
name|testEntry
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|externalConfirm
operator|.
name|confirmClose
argument_list|(
name|testEntry
argument_list|)
condition|)
return|return;
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
block|}
argument_list|)
expr_stmt|;
name|cancel
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
argument_list|)
expr_stmt|;
name|link
operator|.
name|addFocusListener
argument_list|(
operator|new
name|FocusListener
argument_list|()
block|{
specifier|public
name|void
name|focusGained
parameter_list|(
name|FocusEvent
name|e
parameter_list|)
block|{             }
specifier|public
name|void
name|focusLost
parameter_list|(
name|FocusEvent
name|e
parameter_list|)
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
name|link
operator|.
name|getText
argument_list|()
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|0
operator|)
condition|)
block|{
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
name|int
name|index
init|=
name|theLink
operator|.
name|indexOf
argument_list|(
literal|'.'
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|index
operator|>=
literal|0
operator|)
operator|&&
operator|(
name|index
operator|<
name|theLink
operator|.
name|length
argument_list|()
operator|-
literal|1
operator|)
condition|)
block|{
name|ExternalFileType
name|type
init|=
name|Globals
operator|.
name|prefs
operator|.
name|getExternalFileTypeByExt
argument_list|(
name|theLink
operator|.
name|substring
argument_list|(
name|index
operator|+
literal|1
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|type
operator|!=
literal|null
condition|)
name|types
operator|.
name|setSelectedItem
argument_list|(
name|type
argument_list|)
expr_stmt|;
block|}
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
name|parent
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Edit file link"
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
name|Util
operator|.
name|placeDialog
argument_list|(
name|diag
argument_list|,
name|parent
argument_list|)
expr_stmt|;
name|setValues
argument_list|(
name|entry
argument_list|)
expr_stmt|;
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
DECL|method|setVisible (boolean visible)
specifier|public
name|void
name|setVisible
parameter_list|(
name|boolean
name|visible
parameter_list|)
block|{
if|if
condition|(
name|visible
condition|)
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
DECL|method|setValues (FileListEntry entry)
specifier|public
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
name|getDescription
argument_list|()
argument_list|)
expr_stmt|;
name|link
operator|.
name|setText
argument_list|(
name|entry
operator|.
name|getLink
argument_list|()
argument_list|)
expr_stmt|;
name|types
operator|.
name|setSelectedItem
argument_list|(
name|entry
operator|.
name|getType
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|storeSettings (FileListEntry entry)
specifier|public
name|void
name|storeSettings
parameter_list|(
name|FileListEntry
name|entry
parameter_list|)
block|{
name|entry
operator|.
name|setDescription
argument_list|(
name|description
operator|.
name|getText
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setLink
argument_list|(
name|link
operator|.
name|getText
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setType
argument_list|(
operator|(
name|ExternalFileType
operator|)
name|types
operator|.
name|getSelectedItem
argument_list|()
argument_list|)
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
DECL|class|BrowseListener
class|class
name|BrowseListener
implements|implements
name|ActionListener
block|{
DECL|field|parent
specifier|private
name|JFrame
name|parent
decl_stmt|;
DECL|field|comp
specifier|private
name|JTextField
name|comp
decl_stmt|;
DECL|method|BrowseListener (JFrame parent, JTextField comp)
specifier|public
name|BrowseListener
parameter_list|(
name|JFrame
name|parent
parameter_list|,
name|JTextField
name|comp
parameter_list|)
block|{
name|this
operator|.
name|parent
operator|=
name|parent
expr_stmt|;
name|this
operator|.
name|comp
operator|=
name|comp
expr_stmt|;
block|}
DECL|method|actionPerformed (ActionEvent e)
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|File
name|initial
init|=
operator|new
name|File
argument_list|(
name|comp
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
name|comp
operator|.
name|getText
argument_list|()
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
block|{
comment|// Nothing in the field. Go to the last file dir used:
name|initial
operator|=
operator|new
name|File
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
literal|"fileWorkingDirectory"
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|String
name|chosen
init|=
name|Globals
operator|.
name|getNewFile
argument_list|(
name|parent
argument_list|,
name|initial
argument_list|,
name|Globals
operator|.
name|NONE
argument_list|,
name|JFileChooser
operator|.
name|OPEN_DIALOG
argument_list|,
literal|false
argument_list|)
decl_stmt|;
if|if
condition|(
name|chosen
operator|!=
literal|null
condition|)
block|{
name|File
name|newFile
init|=
operator|new
name|File
argument_list|(
name|chosen
argument_list|)
decl_stmt|;
comment|// Store the directory for next time:
name|Globals
operator|.
name|prefs
operator|.
name|put
argument_list|(
literal|"fileWorkingDirectory"
argument_list|,
name|newFile
operator|.
name|getParent
argument_list|()
argument_list|)
expr_stmt|;
comment|// If the file is below the file directory, make the path relative:
name|ArrayList
argument_list|<
name|File
argument_list|>
name|dirs
init|=
operator|new
name|ArrayList
argument_list|<
name|File
argument_list|>
argument_list|()
decl_stmt|;
name|dirs
operator|.
name|add
argument_list|(
operator|new
name|File
argument_list|(
name|metaData
operator|.
name|getFileDirectory
argument_list|(
name|GUIGlobals
operator|.
name|FILE_FIELD
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|dirs
operator|.
name|size
argument_list|()
operator|>
literal|0
condition|)
block|{
name|newFile
operator|=
name|FileListEditor
operator|.
name|relativizePath
argument_list|(
name|newFile
argument_list|,
name|dirs
argument_list|)
expr_stmt|;
block|}
name|comp
operator|.
name|setText
argument_list|(
name|newFile
operator|.
name|getPath
argument_list|()
argument_list|)
expr_stmt|;
name|comp
operator|.
name|requestFocus
argument_list|()
expr_stmt|;
block|}
block|}
block|}
block|}
end_class

end_unit

