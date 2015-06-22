begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

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
name|ItemEvent
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
name|ItemListener
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
name|JFileChooser
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JFrame
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
specifier|final
name|JTextField
name|link
init|=
operator|new
name|JTextField
argument_list|()
decl_stmt|;
DECL|field|description
specifier|final
name|JTextField
name|description
init|=
operator|new
name|JTextField
argument_list|()
decl_stmt|;
DECL|field|ok
specifier|final
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
decl_stmt|;
DECL|field|cancel
specifier|final
name|JButton
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
DECL|field|open
specifier|final
name|JButton
name|open
init|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Open"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|types
specifier|final
name|JComboBox
name|types
decl_stmt|;
DECL|field|prog
specifier|final
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
specifier|final
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
specifier|final
name|MetaData
name|metaData
decl_stmt|;
DECL|field|okPressed
DECL|field|okDisabledExternally
specifier|private
name|boolean
name|okPressed
init|=
literal|false
decl_stmt|,
name|okDisabledExternally
init|=
literal|false
decl_stmt|,
DECL|field|openBrowseWhenShown
DECL|field|dontOpenBrowseUntilDisposed
name|openBrowseWhenShown
init|=
literal|false
decl_stmt|,
name|dontOpenBrowseUntilDisposed
init|=
literal|false
decl_stmt|;
DECL|field|remoteLinkPattern
specifier|public
specifier|static
specifier|final
name|Pattern
name|remoteLinkPattern
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"[a-z]+://.*"
argument_list|)
decl_stmt|;
DECL|method|FileListEntryEditor (JabRefFrame frame, FileListEntry entry, boolean showProgressBar, boolean showOpenButton, MetaData metaData)
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
name|AbstractAction
name|okAction
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
name|e
parameter_list|)
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
return|return;
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
decl_stmt|;
name|types
operator|=
operator|new
name|JComboBox
argument_list|()
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
if|if
condition|(
operator|!
name|okDisabledExternally
condition|)
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
literal|"left:pref, 4dlu, fill:150dlu, 4dlu, fill:pref, 4dlu, fill:pref"
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
specifier|final
name|BrowseListener
name|browse
init|=
operator|new
name|BrowseListener
argument_list|(
name|frame
argument_list|,
name|link
argument_list|)
decl_stmt|;
specifier|final
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
if|if
condition|(
name|showOpenButton
condition|)
name|builder
operator|.
name|append
argument_list|(
name|open
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
argument_list|,
literal|3
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
argument_list|,
literal|3
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
comment|//bb.addButton(open);
comment|//bb.addRelatedGap();
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
name|openFile
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|AbstractAction
name|cancelAction
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
specifier|public
name|void
name|removeUpdate
parameter_list|(
name|DocumentEvent
name|documentEvent
parameter_list|)
block|{             }
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
operator|new
name|Runnable
argument_list|()
block|{
specifier|public
name|void
name|run
parameter_list|()
block|{
name|browse
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
expr_stmt|;
block|}
block|}
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
comment|// Check if this looks like a remote link:
if|if
condition|(
name|remoteLinkPattern
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
name|ExternalFileType
name|type
init|=
name|Globals
operator|.
name|prefs
operator|.
name|getExternalFileTypeByExt
argument_list|(
literal|"html"
argument_list|)
decl_stmt|;
if|if
condition|(
name|type
operator|!=
literal|null
condition|)
block|{
name|types
operator|.
name|setSelectedItem
argument_list|(
name|type
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
name|ExternalFileType
name|type
init|=
name|Globals
operator|.
name|prefs
operator|.
name|getExternalFileTypeForName
argument_list|(
name|theLink
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
DECL|method|openFile ()
specifier|public
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
try|try
block|{
name|Util
operator|.
name|openExternalFileAnyFormat
argument_list|(
name|metaData
argument_list|,
name|link
operator|.
name|getText
argument_list|()
argument_list|,
name|type
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
literal|"allowFileAutoOpenBrowse"
argument_list|)
expr_stmt|;
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
DECL|method|isVisible ()
specifier|public
name|boolean
name|isVisible
parameter_list|()
block|{
return|return
name|diag
operator|!=
literal|null
operator|&&
name|diag
operator|.
name|isVisible
argument_list|()
return|;
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
comment|//if (link.getText().length()> 0)
comment|//    checkExtension();
name|types
operator|.
name|setModel
argument_list|(
operator|new
name|DefaultComboBoxModel
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getExternalFileTypeSelection
argument_list|()
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
name|getType
argument_list|()
operator|!=
literal|null
operator|)
operator|&&
operator|!
operator|(
name|entry
operator|.
name|getType
argument_list|()
operator|instanceof
name|UnknownExternalFileType
operator|)
condition|)
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
comment|// If the entry has a link but not a file type, try to deduce the file type:
elseif|else
if|if
condition|(
operator|(
name|entry
operator|.
name|getLink
argument_list|()
operator|!=
literal|null
operator|)
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
literal|0
operator|)
condition|)
block|{
name|checkExtension
argument_list|()
expr_stmt|;
block|}
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
comment|// See if we should trim the file link to be relative to the file directory:
try|try
block|{
name|String
index|[]
name|dirs
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
if|if
condition|(
name|dirs
operator|.
name|length
operator|==
literal|0
condition|)
block|{
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
name|String
name|relFileName
init|=
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
decl_stmt|;
name|entry
operator|.
name|setLink
argument_list|(
name|relFileName
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
block|}
block|}
catch|catch
parameter_list|(
name|java
operator|.
name|io
operator|.
name|IOException
name|ex
parameter_list|)
block|{
name|ex
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
comment|// Don't think this should happen, but set the file link directly as a fallback:
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
block|}
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
specifier|final
name|JFrame
name|parent
decl_stmt|;
DECL|field|comp
specifier|private
specifier|final
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
name|FileDialogs
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
name|String
index|[]
name|dirsS
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
name|newFile
operator|=
name|Util
operator|.
name|shortenFileName
argument_list|(
name|newFile
argument_list|,
name|dirsS
argument_list|)
expr_stmt|;
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

