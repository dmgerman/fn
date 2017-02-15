begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.externalfiles
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|externalfiles
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
name|Collections
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashSet
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
name|Locale
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
name|Set
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
name|Action
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
name|ButtonGroup
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
name|JCheckBox
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
name|JRadioButton
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
name|BasePanel
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
name|IconTheme
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
name|ExternalFileTypeEntryEditor
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
name|filelist
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
name|filelist
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
name|filelist
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
name|undo
operator|.
name|NamedCompound
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
name|undo
operator|.
name|UndoableFieldChange
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
name|model
operator|.
name|database
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
name|model
operator|.
name|entry
operator|.
name|FieldName
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

begin_comment
comment|/**  * This action goes through all selected entries in the BasePanel, and attempts to autoset the  * given external file (pdf, ps, ...) based on the same algorithm used for the "Auto" button in  * EntryEditor.  */
end_comment

begin_class
DECL|class|SynchronizeFileField
specifier|public
class|class
name|SynchronizeFileField
extends|extends
name|AbstractWorker
block|{
DECL|field|panel
specifier|private
specifier|final
name|BasePanel
name|panel
decl_stmt|;
DECL|field|sel
specifier|private
name|List
argument_list|<
name|BibEntry
argument_list|>
name|sel
decl_stmt|;
DECL|field|optDiag
specifier|private
name|SynchronizeFileField
operator|.
name|OptionsDialog
name|optDiag
decl_stmt|;
DECL|field|entriesChangedCount
specifier|private
name|int
name|entriesChangedCount
decl_stmt|;
DECL|field|brokenLinkOptions
specifier|private
specifier|final
name|Object
index|[]
name|brokenLinkOptions
init|=
block|{
name|Localization
operator|.
name|lang
argument_list|(
literal|"Ignore"
argument_list|)
block|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Assign new file"
argument_list|)
block|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Remove link"
argument_list|)
block|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Remove all broken links"
argument_list|)
block|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Quit synchronization"
argument_list|)
block|}
decl_stmt|;
DECL|field|goOn
specifier|private
name|boolean
name|goOn
init|=
literal|true
decl_stmt|;
DECL|field|autoSet
specifier|private
name|boolean
name|autoSet
init|=
literal|true
decl_stmt|;
DECL|field|checkExisting
specifier|private
name|boolean
name|checkExisting
init|=
literal|true
decl_stmt|;
DECL|method|SynchronizeFileField (BasePanel panel)
specifier|public
name|SynchronizeFileField
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
name|Collection
argument_list|<
name|BibEntry
argument_list|>
name|col
init|=
name|panel
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
decl_stmt|;
name|goOn
operator|=
literal|true
expr_stmt|;
name|sel
operator|=
operator|new
name|ArrayList
argument_list|<>
argument_list|(
name|col
argument_list|)
expr_stmt|;
comment|// Ask about rules for the operation:
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
name|SynchronizeFileField
operator|.
name|OptionsDialog
argument_list|(
name|panel
operator|.
name|frame
argument_list|()
argument_list|,
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|optDiag
operator|.
name|setLocationRelativeTo
argument_list|(
name|panel
operator|.
name|frame
argument_list|()
argument_list|)
expr_stmt|;
name|optDiag
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
if|if
condition|(
name|optDiag
operator|.
name|canceled
argument_list|()
condition|)
block|{
name|goOn
operator|=
literal|false
expr_stmt|;
return|return;
block|}
name|autoSet
operator|=
operator|!
name|optDiag
operator|.
name|isAutoSetNone
argument_list|()
expr_stmt|;
name|checkExisting
operator|=
name|optDiag
operator|.
name|isCheckLinks
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
literal|"Synchronizing file links..."
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
name|panel
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"This operation requires one or more entries to be selected."
argument_list|)
argument_list|)
expr_stmt|;
return|return;
block|}
name|entriesChangedCount
operator|=
literal|0
expr_stmt|;
name|panel
operator|.
name|frame
argument_list|()
operator|.
name|setProgressBarValue
argument_list|(
literal|0
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
name|int
name|weightAutoSet
init|=
literal|10
decl_stmt|;
comment|// autoSet takes 10 (?) times longer than checkExisting
name|int
name|progressBarMax
init|=
operator|(
name|autoSet
condition|?
name|weightAutoSet
operator|*
name|sel
operator|.
name|size
argument_list|()
else|:
literal|0
operator|)
operator|+
operator|(
name|checkExisting
condition|?
name|sel
operator|.
name|size
argument_list|()
else|:
literal|0
operator|)
decl_stmt|;
name|panel
operator|.
name|frame
argument_list|()
operator|.
name|setProgressBarMaximum
argument_list|(
name|progressBarMax
argument_list|)
expr_stmt|;
name|int
name|progress
init|=
literal|0
decl_stmt|;
specifier|final
name|NamedCompound
name|ce
init|=
operator|new
name|NamedCompound
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Automatically set file links"
argument_list|)
argument_list|)
decl_stmt|;
name|Set
argument_list|<
name|BibEntry
argument_list|>
name|changedEntries
init|=
operator|new
name|HashSet
argument_list|<>
argument_list|()
decl_stmt|;
comment|// First we try to autoset fields
if|if
condition|(
name|autoSet
condition|)
block|{
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|(
name|sel
argument_list|)
decl_stmt|;
comment|// Start the automatically setting process:
name|Runnable
name|r
init|=
name|AutoSetLinks
operator|.
name|autoSetLinks
argument_list|(
name|entries
argument_list|,
name|ce
argument_list|,
name|changedEntries
argument_list|,
literal|null
argument_list|,
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|)
decl_stmt|;
name|JabRefExecutorService
operator|.
name|INSTANCE
operator|.
name|executeAndWait
argument_list|(
name|r
argument_list|)
expr_stmt|;
block|}
name|progress
operator|+=
name|sel
operator|.
name|size
argument_list|()
operator|*
name|weightAutoSet
expr_stmt|;
name|panel
operator|.
name|frame
argument_list|()
operator|.
name|setProgressBarValue
argument_list|(
name|progress
argument_list|)
expr_stmt|;
comment|// The following loop checks all external links that are already set.
if|if
condition|(
name|checkExisting
condition|)
block|{
name|boolean
name|removeAllBroken
init|=
literal|false
decl_stmt|;
name|mainLoop
label|:
for|for
control|(
name|BibEntry
name|aSel
range|:
name|sel
control|)
block|{
name|panel
operator|.
name|frame
argument_list|()
operator|.
name|setProgressBarValue
argument_list|(
name|progress
operator|++
argument_list|)
expr_stmt|;
specifier|final
name|Optional
argument_list|<
name|String
argument_list|>
name|old
init|=
name|aSel
operator|.
name|getField
argument_list|(
name|FieldName
operator|.
name|FILE
argument_list|)
decl_stmt|;
comment|// Check if a extension is set:
if|if
condition|(
name|old
operator|.
name|isPresent
argument_list|()
operator|&&
operator|!
operator|(
name|old
operator|.
name|get
argument_list|()
operator|.
name|isEmpty
argument_list|()
operator|)
condition|)
block|{
name|FileListTableModel
name|tableModel
init|=
operator|new
name|FileListTableModel
argument_list|()
decl_stmt|;
name|tableModel
operator|.
name|setContentDontGuessTypes
argument_list|(
name|old
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
comment|// We need to specify which directories to search in for Util.expandFilename:
name|List
argument_list|<
name|String
argument_list|>
name|dirsS
init|=
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
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
name|List
argument_list|<
name|File
argument_list|>
name|dirs
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|dirs1
range|:
name|dirsS
control|)
block|{
name|dirs
operator|.
name|add
argument_list|(
operator|new
name|File
argument_list|(
name|dirs1
argument_list|)
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|int
name|j
init|=
literal|0
init|;
name|j
operator|<
name|tableModel
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
name|tableModel
operator|.
name|getEntry
argument_list|(
name|j
argument_list|)
decl_stmt|;
comment|// See if the link looks like an URL:
name|boolean
name|httpLink
init|=
name|flEntry
operator|.
name|link
operator|.
name|toLowerCase
argument_list|(
name|Locale
operator|.
name|ENGLISH
argument_list|)
operator|.
name|startsWith
argument_list|(
literal|"http"
argument_list|)
decl_stmt|;
if|if
condition|(
name|httpLink
condition|)
block|{
continue|continue;
comment|// Don't check the remote file.
comment|// TODO: should there be an option to check remote links?
block|}
comment|// A variable to keep track of whether this link gets deleted:
name|boolean
name|deleted
init|=
literal|false
decl_stmt|;
comment|// Get an absolute path representation:
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
name|flEntry
operator|.
name|link
argument_list|,
name|dirsS
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
operator|!
name|file
operator|.
name|isPresent
argument_list|()
operator|)
operator|||
operator|!
name|file
operator|.
name|get
argument_list|()
operator|.
name|exists
argument_list|()
condition|)
block|{
name|int
name|answer
decl_stmt|;
if|if
condition|(
name|removeAllBroken
condition|)
block|{
name|answer
operator|=
literal|2
expr_stmt|;
comment|// We should delete this link.
block|}
else|else
block|{
name|answer
operator|=
name|JOptionPane
operator|.
name|showOptionDialog
argument_list|(
name|panel
operator|.
name|frame
argument_list|()
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"<HTML>Could not find file '%0'<BR>linked from entry '%1'</HTML>"
argument_list|,
name|flEntry
operator|.
name|link
argument_list|,
name|aSel
operator|.
name|getCiteKeyOptional
argument_list|()
operator|.
name|orElse
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"undefined"
argument_list|)
argument_list|)
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Broken link"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|YES_NO_CANCEL_OPTION
argument_list|,
name|JOptionPane
operator|.
name|QUESTION_MESSAGE
argument_list|,
literal|null
argument_list|,
name|brokenLinkOptions
argument_list|,
name|brokenLinkOptions
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
block|}
switch|switch
condition|(
name|answer
condition|)
block|{
case|case
literal|1
case|:
comment|// Assign new file.
name|FileListEntryEditor
name|flEditor
init|=
operator|new
name|FileListEntryEditor
argument_list|(
name|panel
operator|.
name|frame
argument_list|()
argument_list|,
name|flEntry
argument_list|,
literal|false
argument_list|,
literal|true
argument_list|,
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
argument_list|)
decl_stmt|;
name|flEditor
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|,
literal|true
argument_list|)
expr_stmt|;
break|break;
case|case
literal|2
case|:
comment|// Clear field:
name|tableModel
operator|.
name|removeEntry
argument_list|(
name|j
argument_list|)
expr_stmt|;
name|deleted
operator|=
literal|true
expr_stmt|;
comment|// Make sure we don't investigate this link further.
name|j
operator|--
expr_stmt|;
comment|// Step back in the iteration, because we removed an entry.
break|break;
case|case
literal|3
case|:
comment|// Clear field:
name|tableModel
operator|.
name|removeEntry
argument_list|(
name|j
argument_list|)
expr_stmt|;
name|deleted
operator|=
literal|true
expr_stmt|;
comment|// Make sure we don't investigate this link further.
name|j
operator|--
expr_stmt|;
comment|// Step back in the iteration, because we removed an entry.
name|removeAllBroken
operator|=
literal|true
expr_stmt|;
comment|// Notify for further cases.
break|break;
default|default:
comment|// Cancel
break|break
name|mainLoop
break|;
block|}
block|}
comment|// Unless we deleted this link, see if its file type is recognized:
if|if
condition|(
operator|!
name|deleted
operator|&&
name|flEntry
operator|.
name|type
operator|.
name|isPresent
argument_list|()
operator|&&
operator|(
name|flEntry
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
name|String
index|[]
name|options
init|=
operator|new
name|String
index|[]
block|{
name|Localization
operator|.
name|lang
argument_list|(
literal|"Define '%0'"
argument_list|,
name|flEntry
operator|.
name|type
operator|.
name|get
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
block|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Change file type"
argument_list|)
block|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Cancel"
argument_list|)
block|}
decl_stmt|;
name|String
name|defOption
init|=
name|options
index|[
literal|0
index|]
decl_stmt|;
name|int
name|answer
init|=
name|JOptionPane
operator|.
name|showOptionDialog
argument_list|(
name|panel
operator|.
name|frame
argument_list|()
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"One or more file links are of the type '%0', which is undefined. What do you want to do?"
argument_list|,
name|flEntry
operator|.
name|type
operator|.
name|get
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Undefined file type"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|YES_NO_CANCEL_OPTION
argument_list|,
name|JOptionPane
operator|.
name|QUESTION_MESSAGE
argument_list|,
literal|null
argument_list|,
name|options
argument_list|,
name|defOption
argument_list|)
decl_stmt|;
if|if
condition|(
name|answer
operator|==
name|JOptionPane
operator|.
name|CANCEL_OPTION
condition|)
block|{
comment|// User doesn't want to handle this unknown link type.
block|}
elseif|else
if|if
condition|(
name|answer
operator|==
name|JOptionPane
operator|.
name|YES_OPTION
condition|)
block|{
comment|// User wants to define the new file type. Show the dialog:
name|ExternalFileType
name|newType
init|=
operator|new
name|ExternalFileType
argument_list|(
name|flEntry
operator|.
name|type
operator|.
name|get
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|,
literal|""
argument_list|,
literal|""
argument_list|,
literal|""
argument_list|,
literal|"new"
argument_list|,
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|FILE
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
decl_stmt|;
name|ExternalFileTypeEntryEditor
name|editor
init|=
operator|new
name|ExternalFileTypeEntryEditor
argument_list|(
name|panel
operator|.
name|frame
argument_list|()
argument_list|,
name|newType
argument_list|)
decl_stmt|;
name|editor
operator|.
name|setVisible
argument_list|(
literal|true
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
comment|// Get the old list of types, add this one, and update the list in prefs:
name|List
argument_list|<
name|ExternalFileType
argument_list|>
name|fileTypes
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|(
name|ExternalFileTypes
operator|.
name|getInstance
argument_list|()
operator|.
name|getExternalFileTypeSelection
argument_list|()
argument_list|)
decl_stmt|;
name|fileTypes
operator|.
name|add
argument_list|(
name|newType
argument_list|)
expr_stmt|;
name|Collections
operator|.
name|sort
argument_list|(
name|fileTypes
argument_list|)
expr_stmt|;
name|ExternalFileTypes
operator|.
name|getInstance
argument_list|()
operator|.
name|setExternalFileTypes
argument_list|(
name|fileTypes
argument_list|)
expr_stmt|;
name|panel
operator|.
name|getMainTable
argument_list|()
operator|.
name|repaint
argument_list|()
expr_stmt|;
block|}
block|}
else|else
block|{
comment|// User wants to change the type of this link.
comment|// First get a model of all file links for this entry:
name|FileListEntryEditor
name|editor
init|=
operator|new
name|FileListEntryEditor
argument_list|(
name|panel
operator|.
name|frame
argument_list|()
argument_list|,
name|flEntry
argument_list|,
literal|false
argument_list|,
literal|true
argument_list|,
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
argument_list|)
decl_stmt|;
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
block|}
block|}
if|if
condition|(
operator|!
name|tableModel
operator|.
name|getStringRepresentation
argument_list|()
operator|.
name|equals
argument_list|(
name|old
operator|.
name|orElse
argument_list|(
literal|null
argument_list|)
argument_list|)
condition|)
block|{
comment|// The table has been modified. Store the change:
name|String
name|toSet
init|=
name|tableModel
operator|.
name|getStringRepresentation
argument_list|()
decl_stmt|;
if|if
condition|(
name|toSet
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|ce
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableFieldChange
argument_list|(
name|aSel
argument_list|,
name|FieldName
operator|.
name|FILE
argument_list|,
name|old
operator|.
name|orElse
argument_list|(
literal|null
argument_list|)
argument_list|,
literal|null
argument_list|)
argument_list|)
expr_stmt|;
name|aSel
operator|.
name|clearField
argument_list|(
name|FieldName
operator|.
name|FILE
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|ce
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableFieldChange
argument_list|(
name|aSel
argument_list|,
name|FieldName
operator|.
name|FILE
argument_list|,
name|old
operator|.
name|orElse
argument_list|(
literal|null
argument_list|)
argument_list|,
name|toSet
argument_list|)
argument_list|)
expr_stmt|;
name|aSel
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|FILE
argument_list|,
name|toSet
argument_list|)
expr_stmt|;
block|}
name|changedEntries
operator|.
name|add
argument_list|(
name|aSel
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
if|if
condition|(
operator|!
name|changedEntries
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
comment|// Add the undo edit:
name|ce
operator|.
name|end
argument_list|()
expr_stmt|;
name|panel
operator|.
name|getUndoManager
argument_list|()
operator|.
name|addEdit
argument_list|(
name|ce
argument_list|)
expr_stmt|;
name|panel
operator|.
name|markBaseChanged
argument_list|()
expr_stmt|;
name|entriesChangedCount
operator|=
name|changedEntries
operator|.
name|size
argument_list|()
expr_stmt|;
block|}
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
literal|"Finished synchronizing file links. Entries changed: %0."
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|entriesChangedCount
argument_list|)
argument_list|)
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
if|if
condition|(
name|entriesChangedCount
operator|>
literal|0
condition|)
block|{
name|panel
operator|.
name|markBaseChanged
argument_list|()
expr_stmt|;
block|}
block|}
DECL|class|OptionsDialog
specifier|static
class|class
name|OptionsDialog
extends|extends
name|JDialog
block|{
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
DECL|field|cancel
specifier|private
specifier|final
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
DECL|field|canceled
specifier|private
name|boolean
name|canceled
init|=
literal|true
decl_stmt|;
DECL|field|databaseContext
specifier|private
specifier|final
name|BibDatabaseContext
name|databaseContext
decl_stmt|;
DECL|field|autoSetUnset
specifier|private
specifier|final
name|JRadioButton
name|autoSetUnset
init|=
operator|new
name|JRadioButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Automatically set file links"
argument_list|)
operator|+
literal|". "
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"Do not overwrite existing links."
argument_list|)
argument_list|,
literal|true
argument_list|)
decl_stmt|;
DECL|field|autoSetAll
specifier|private
specifier|final
name|JRadioButton
name|autoSetAll
init|=
operator|new
name|JRadioButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Automatically set file links"
argument_list|)
operator|+
literal|". "
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"Allow overwriting existing links."
argument_list|)
argument_list|,
literal|false
argument_list|)
decl_stmt|;
DECL|field|autoSetNone
specifier|private
specifier|final
name|JRadioButton
name|autoSetNone
init|=
operator|new
name|JRadioButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Do not automatically set"
argument_list|)
argument_list|,
literal|false
argument_list|)
decl_stmt|;
DECL|field|checkLinks
specifier|private
specifier|final
name|JCheckBox
name|checkLinks
init|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Check existing file links"
argument_list|)
argument_list|,
literal|true
argument_list|)
decl_stmt|;
DECL|method|OptionsDialog (JFrame parent, BibDatabaseContext databaseContext)
specifier|public
name|OptionsDialog
parameter_list|(
name|JFrame
name|parent
parameter_list|,
name|BibDatabaseContext
name|databaseContext
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
literal|"Synchronize file links"
argument_list|)
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|this
operator|.
name|databaseContext
operator|=
name|databaseContext
expr_stmt|;
name|ok
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
block|{
name|canceled
operator|=
literal|false
expr_stmt|;
name|dispose
argument_list|()
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
name|Action
name|closeAction
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
name|closeAction
argument_list|)
expr_stmt|;
name|InputMap
name|im
init|=
name|cancel
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
name|cancel
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
name|closeAction
argument_list|)
expr_stmt|;
name|ButtonGroup
name|bg
init|=
operator|new
name|ButtonGroup
argument_list|()
decl_stmt|;
name|bg
operator|.
name|add
argument_list|(
name|autoSetUnset
argument_list|)
expr_stmt|;
name|bg
operator|.
name|add
argument_list|(
name|autoSetNone
argument_list|)
expr_stmt|;
name|bg
operator|.
name|add
argument_list|(
name|autoSetAll
argument_list|)
expr_stmt|;
name|FormLayout
name|layout
init|=
operator|new
name|FormLayout
argument_list|(
literal|"fill:pref"
argument_list|,
literal|"pref, 2dlu, pref, 2dlu, pref, pref, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref"
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
name|layout
argument_list|)
decl_stmt|;
name|JLabel
name|description
init|=
operator|new
name|JLabel
argument_list|(
literal|"<HTML>"
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"Attempt to automatically set file links for your entries. Automatically setting works if "
operator|+
literal|"a file in your file directory<BR>or a subdirectory is named identically to an entry's BibTeX key, plus extension."
argument_list|)
operator|+
literal|"</HTML>"
argument_list|)
decl_stmt|;
name|builder
operator|.
name|addSeparator
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Automatically set file links"
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
name|description
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
name|autoSetUnset
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
name|autoSetAll
argument_list|)
operator|.
name|xy
argument_list|(
literal|1
argument_list|,
literal|6
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|autoSetNone
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
name|addSeparator
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Check links"
argument_list|)
argument_list|)
operator|.
name|xy
argument_list|(
literal|1
argument_list|,
literal|9
argument_list|)
expr_stmt|;
name|description
operator|=
operator|new
name|JLabel
argument_list|(
literal|"<HTML>"
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"This makes JabRef look up each file link and check if the file exists. If not, you will be given options<BR>to resolve the problem."
argument_list|)
operator|+
literal|"</HTML>"
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|description
argument_list|)
operator|.
name|xy
argument_list|(
literal|1
argument_list|,
literal|11
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|checkLinks
argument_list|)
operator|.
name|xy
argument_list|(
literal|1
argument_list|,
literal|13
argument_list|)
expr_stmt|;
name|builder
operator|.
name|addSeparator
argument_list|(
literal|""
argument_list|)
operator|.
name|xy
argument_list|(
literal|1
argument_list|,
literal|15
argument_list|)
expr_stmt|;
name|JPanel
name|main
init|=
name|builder
operator|.
name|getPanel
argument_list|()
decl_stmt|;
name|main
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
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|main
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
name|pack
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
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
block|{
name|canceled
operator|=
literal|true
expr_stmt|;
block|}
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
name|autoSetNone
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|autoSetNone
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|autoSetAll
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|autoSetUnset
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|autoSetNone
operator|.
name|setEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|autoSetAll
operator|.
name|setEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|autoSetUnset
operator|.
name|setEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
name|ok
operator|.
name|requestFocus
argument_list|()
expr_stmt|;
name|super
operator|.
name|setVisible
argument_list|(
name|visible
argument_list|)
expr_stmt|;
block|}
DECL|method|isAutoSetNone ()
specifier|public
name|boolean
name|isAutoSetNone
parameter_list|()
block|{
return|return
name|autoSetNone
operator|.
name|isSelected
argument_list|()
return|;
block|}
DECL|method|isCheckLinks ()
specifier|public
name|boolean
name|isCheckLinks
parameter_list|()
block|{
return|return
name|checkLinks
operator|.
name|isSelected
argument_list|()
return|;
block|}
DECL|method|canceled ()
specifier|public
name|boolean
name|canceled
parameter_list|()
block|{
return|return
name|canceled
return|;
block|}
block|}
block|}
end_class

end_unit
