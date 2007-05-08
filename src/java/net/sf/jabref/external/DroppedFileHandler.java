begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
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
name|List
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
name|JCheckBox
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
name|javax
operator|.
name|swing
operator|.
name|event
operator|.
name|ChangeEvent
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
name|ChangeListener
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
name|KeyCollisionException
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
name|gui
operator|.
name|MainTable
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
name|util
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
comment|/**  * This class holds the functionality of autolinking to a file that's dropped  * onto an entry.  *   * Options for handling the files are:  *   * 1) Link to the file in its current position (disabled if the file is remote)  *   * 2) Copy the file to ??? directory, rename after bibtex key, and extension  *   * 3) Move the file to ??? directory, rename after bibtex key, and extension  */
end_comment

begin_class
DECL|class|DroppedFileHandler
specifier|public
class|class
name|DroppedFileHandler
block|{
DECL|field|frame
specifier|private
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|panel
specifier|private
name|BasePanel
name|panel
decl_stmt|;
DECL|field|linkInPlace
DECL|field|copyRadioButton
specifier|private
name|JRadioButton
name|linkInPlace
init|=
operator|new
name|JRadioButton
argument_list|()
decl_stmt|,
name|copyRadioButton
init|=
operator|new
name|JRadioButton
argument_list|()
decl_stmt|,
DECL|field|moveRadioButton
name|moveRadioButton
init|=
operator|new
name|JRadioButton
argument_list|()
decl_stmt|;
DECL|field|destDirLabel
specifier|private
name|JLabel
name|destDirLabel
init|=
operator|new
name|JLabel
argument_list|()
decl_stmt|;
DECL|field|renameCheckBox
specifier|private
name|JCheckBox
name|renameCheckBox
init|=
operator|new
name|JCheckBox
argument_list|()
decl_stmt|;
DECL|field|optionsPanel
specifier|private
name|JPanel
name|optionsPanel
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
DECL|method|DroppedFileHandler (JabRefFrame frame, BasePanel panel)
specifier|public
name|DroppedFileHandler
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|BasePanel
name|panel
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
name|panel
operator|=
name|panel
expr_stmt|;
name|ButtonGroup
name|grp
init|=
operator|new
name|ButtonGroup
argument_list|()
decl_stmt|;
name|grp
operator|.
name|add
argument_list|(
name|linkInPlace
argument_list|)
expr_stmt|;
name|grp
operator|.
name|add
argument_list|(
name|copyRadioButton
argument_list|)
expr_stmt|;
name|grp
operator|.
name|add
argument_list|(
name|moveRadioButton
argument_list|)
expr_stmt|;
name|copyRadioButton
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|DefaultFormBuilder
name|builder
init|=
operator|new
name|DefaultFormBuilder
argument_list|(
name|optionsPanel
argument_list|,
operator|new
name|FormLayout
argument_list|(
literal|"left:pref"
argument_list|,
literal|""
argument_list|)
argument_list|)
decl_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|linkInPlace
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|destDirLabel
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|copyRadioButton
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|moveRadioButton
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|renameCheckBox
argument_list|)
expr_stmt|;
block|}
comment|/**      * Offer copy/move/linking options for a dragged external file. Perform the      * chosen operation, if any.      *       * @param fileName      *            The name of the dragged file.      * @param fileType      *            The FileType associated with the file.      * @param localFile      *            Indicate whether this is a local file, or a remote file copied      *            to a local temporary file.      * @param mainTable      *            The MainTable the file was dragged to.      * @param dropRow      *            The row where the file was dropped.      */
DECL|method|handleDroppedfile (String fileName, ExternalFileType fileType, boolean localFile, MainTable mainTable, int dropRow)
specifier|public
name|void
name|handleDroppedfile
parameter_list|(
name|String
name|fileName
parameter_list|,
name|ExternalFileType
name|fileType
parameter_list|,
name|boolean
name|localFile
parameter_list|,
name|MainTable
name|mainTable
parameter_list|,
name|int
name|dropRow
parameter_list|)
block|{
name|NamedCompound
name|edits
init|=
operator|new
name|NamedCompound
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Drop %0"
argument_list|,
name|fileType
operator|.
name|extension
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|tryXmpImport
argument_list|(
name|fileName
argument_list|,
name|fileType
argument_list|,
name|localFile
argument_list|,
name|mainTable
argument_list|,
name|edits
argument_list|)
condition|)
block|{
name|panel
operator|.
name|undoManager
operator|.
name|addEdit
argument_list|(
name|edits
argument_list|)
expr_stmt|;
return|return;
block|}
name|BibtexEntry
name|entry
init|=
name|mainTable
operator|.
name|getEntryAt
argument_list|(
name|dropRow
argument_list|)
decl_stmt|;
comment|// Show dialog
name|boolean
name|newEntry
init|=
literal|false
decl_stmt|;
name|boolean
name|rename
init|=
name|entry
operator|.
name|getCiteKey
argument_list|()
operator|!=
literal|null
operator|&&
name|entry
operator|.
name|getCiteKey
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|0
decl_stmt|;
name|String
name|citeKeyOrReason
init|=
operator|(
name|rename
condition|?
name|entry
operator|.
name|getCiteKey
argument_list|()
else|:
name|Globals
operator|.
name|lang
argument_list|(
literal|"Entry has no citekey"
argument_list|)
operator|)
decl_stmt|;
name|int
name|reply
init|=
name|showLinkMoveCopyRenameDialog
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Link to file %0"
argument_list|,
name|fileName
argument_list|)
argument_list|,
name|fileType
argument_list|,
name|rename
argument_list|,
name|citeKeyOrReason
argument_list|,
name|newEntry
argument_list|,
literal|false
argument_list|)
decl_stmt|;
if|if
condition|(
name|reply
operator|!=
name|JOptionPane
operator|.
name|OK_OPTION
condition|)
return|return;
comment|/*          * Ok, we're ready to go. See first if we need to do a file copy before          * linking:          */
name|boolean
name|success
init|=
literal|true
decl_stmt|;
name|String
name|destFilename
decl_stmt|;
if|if
condition|(
name|linkInPlace
operator|.
name|isSelected
argument_list|()
condition|)
block|{
name|destFilename
operator|=
name|fileName
expr_stmt|;
block|}
else|else
block|{
name|destFilename
operator|=
operator|(
name|renameCheckBox
operator|.
name|isSelected
argument_list|()
condition|?
name|entry
operator|.
name|getCiteKey
argument_list|()
operator|+
literal|"."
operator|+
name|fileType
operator|.
name|extension
else|:
name|fileName
operator|)
expr_stmt|;
if|if
condition|(
name|copyRadioButton
operator|.
name|isSelected
argument_list|()
condition|)
block|{
name|success
operator|=
name|doCopy
argument_list|(
name|fileName
argument_list|,
name|fileType
argument_list|,
name|destFilename
argument_list|,
name|edits
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|moveRadioButton
operator|.
name|isSelected
argument_list|()
condition|)
block|{
name|success
operator|=
name|doRename
argument_list|(
name|fileName
argument_list|,
name|fileType
argument_list|,
name|destFilename
argument_list|,
name|edits
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|success
condition|)
block|{
name|doLink
argument_list|(
name|entry
argument_list|,
name|fileType
argument_list|,
name|destFilename
argument_list|,
name|edits
argument_list|)
expr_stmt|;
name|panel
operator|.
name|markBaseChanged
argument_list|()
expr_stmt|;
block|}
name|panel
operator|.
name|undoManager
operator|.
name|addEdit
argument_list|(
name|edits
argument_list|)
expr_stmt|;
block|}
DECL|method|tryXmpImport (String fileName, ExternalFileType fileType, boolean localFile, MainTable mainTable, NamedCompound edits)
specifier|private
name|boolean
name|tryXmpImport
parameter_list|(
name|String
name|fileName
parameter_list|,
name|ExternalFileType
name|fileType
parameter_list|,
name|boolean
name|localFile
parameter_list|,
name|MainTable
name|mainTable
parameter_list|,
name|NamedCompound
name|edits
parameter_list|)
block|{
if|if
condition|(
operator|!
name|fileType
operator|.
name|extension
operator|.
name|equals
argument_list|(
literal|"pdf"
argument_list|)
condition|)
block|{
return|return
literal|false
return|;
block|}
name|List
name|xmpEntriesInFile
init|=
literal|null
decl_stmt|;
try|try
block|{
name|xmpEntriesInFile
operator|=
name|XMPUtil
operator|.
name|readXMP
argument_list|(
name|fileName
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
return|return
literal|false
return|;
block|}
if|if
condition|(
operator|(
name|xmpEntriesInFile
operator|==
literal|null
operator|)
operator|||
operator|(
name|xmpEntriesInFile
operator|.
name|size
argument_list|()
operator|==
literal|0
operator|)
condition|)
block|{
return|return
literal|false
return|;
block|}
name|JLabel
name|confirmationMessage
init|=
operator|new
name|JLabel
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"The PDF contains one or several bibtex-records.\nDo you want to import these as new entries into the current database?"
argument_list|)
argument_list|)
decl_stmt|;
name|int
name|reply
init|=
name|JOptionPane
operator|.
name|showConfirmDialog
argument_list|(
name|frame
argument_list|,
name|confirmationMessage
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"XMP metadata found in PDF: %0"
argument_list|,
name|fileName
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
name|reply
operator|==
name|JOptionPane
operator|.
name|CANCEL_OPTION
condition|)
block|{
return|return
literal|true
return|;
comment|// The user canceled thus that we are done.
block|}
if|if
condition|(
name|reply
operator|==
name|JOptionPane
operator|.
name|NO_OPTION
condition|)
block|{
return|return
literal|false
return|;
block|}
comment|// reply == JOptionPane.YES_OPTION)
comment|/*          * TODO Extract Import functionality from ImportMenuItem then we could          * do:          *           * ImportMenuItem importer = new ImportMenuItem(frame, (mainTable ==          * null), new PdfXmpImporter());          *           * importer.automatedImport(new String[] { fileName });          */
name|boolean
name|isSingle
init|=
name|xmpEntriesInFile
operator|.
name|size
argument_list|()
operator|==
literal|1
decl_stmt|;
name|BibtexEntry
name|single
init|=
operator|(
name|isSingle
condition|?
operator|(
name|BibtexEntry
operator|)
name|xmpEntriesInFile
operator|.
name|get
argument_list|(
literal|0
argument_list|)
else|:
literal|null
operator|)
decl_stmt|;
name|reply
operator|=
name|showLinkMoveCopyRenameDialog
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Link to PDF %0"
argument_list|,
name|fileName
argument_list|)
argument_list|,
name|fileType
argument_list|,
name|isSingle
argument_list|,
operator|(
name|isSingle
condition|?
name|single
operator|.
name|getCiteKey
argument_list|()
else|:
name|Globals
operator|.
name|lang
argument_list|(
literal|"Cannot rename for several entries."
argument_list|)
operator|)
argument_list|,
literal|false
argument_list|,
operator|!
name|isSingle
argument_list|)
expr_stmt|;
name|boolean
name|success
init|=
literal|true
decl_stmt|;
name|String
name|destFilename
decl_stmt|;
if|if
condition|(
name|linkInPlace
operator|.
name|isSelected
argument_list|()
condition|)
block|{
name|destFilename
operator|=
name|fileName
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|renameCheckBox
operator|.
name|isSelected
argument_list|()
condition|)
block|{
name|destFilename
operator|=
name|fileName
expr_stmt|;
block|}
else|else
block|{
name|destFilename
operator|=
name|single
operator|.
name|getCiteKey
argument_list|()
operator|+
literal|"."
operator|+
name|fileType
operator|.
name|extension
expr_stmt|;
block|}
if|if
condition|(
name|copyRadioButton
operator|.
name|isSelected
argument_list|()
condition|)
block|{
name|success
operator|=
name|doCopy
argument_list|(
name|fileName
argument_list|,
name|fileType
argument_list|,
name|destFilename
argument_list|,
name|edits
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|moveRadioButton
operator|.
name|isSelected
argument_list|()
condition|)
block|{
name|success
operator|=
name|doRename
argument_list|(
name|fileName
argument_list|,
name|fileType
argument_list|,
name|destFilename
argument_list|,
name|edits
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|success
condition|)
block|{
name|Iterator
name|it
init|=
name|xmpEntriesInFile
operator|.
name|iterator
argument_list|()
decl_stmt|;
while|while
condition|(
name|it
operator|.
name|hasNext
argument_list|()
condition|)
block|{
try|try
block|{
name|BibtexEntry
name|entry
init|=
operator|(
name|BibtexEntry
operator|)
name|it
operator|.
name|next
argument_list|()
decl_stmt|;
name|entry
operator|.
name|setId
argument_list|(
name|Util
operator|.
name|createNeutralId
argument_list|()
argument_list|)
expr_stmt|;
name|panel
operator|.
name|getDatabase
argument_list|()
operator|.
name|insertEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|doLink
argument_list|(
name|entry
argument_list|,
name|fileType
argument_list|,
name|destFilename
argument_list|,
name|edits
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|KeyCollisionException
name|ex
parameter_list|)
block|{                  }
block|}
name|panel
operator|.
name|markBaseChanged
argument_list|()
expr_stmt|;
name|panel
operator|.
name|updateEntryEditorIfShowing
argument_list|()
expr_stmt|;
block|}
return|return
literal|true
return|;
block|}
DECL|method|showLinkMoveCopyRenameDialog (String dialogTitle, ExternalFileType fileType, final boolean allowRename, String citekeyOrReason, boolean newEntry, final boolean multipleEntries)
specifier|public
name|int
name|showLinkMoveCopyRenameDialog
parameter_list|(
name|String
name|dialogTitle
parameter_list|,
name|ExternalFileType
name|fileType
parameter_list|,
specifier|final
name|boolean
name|allowRename
parameter_list|,
name|String
name|citekeyOrReason
parameter_list|,
name|boolean
name|newEntry
parameter_list|,
specifier|final
name|boolean
name|multipleEntries
parameter_list|)
block|{
name|String
name|dir
init|=
name|panel
operator|.
name|metaData
argument_list|()
operator|.
name|getFileDirectory
argument_list|(
name|fileType
operator|.
name|getFieldName
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|dir
operator|==
literal|null
operator|)
operator|||
operator|!
operator|(
operator|new
name|File
argument_list|(
name|dir
argument_list|)
operator|)
operator|.
name|exists
argument_list|()
condition|)
block|{
name|destDirLabel
operator|.
name|setText
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"%0 directory is not set or does not exist!"
argument_list|,
name|fileType
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|copyRadioButton
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|moveRadioButton
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|linkInPlace
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|destDirLabel
operator|.
name|setText
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"%0 directory is '%1':"
argument_list|,
name|fileType
operator|.
name|getName
argument_list|()
argument_list|,
name|dir
argument_list|)
argument_list|)
expr_stmt|;
name|copyRadioButton
operator|.
name|setEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|moveRadioButton
operator|.
name|setEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
name|ChangeListener
name|cl
init|=
operator|new
name|ChangeListener
argument_list|()
block|{
specifier|public
name|void
name|stateChanged
parameter_list|(
name|ChangeEvent
name|arg0
parameter_list|)
block|{
name|renameCheckBox
operator|.
name|setEnabled
argument_list|(
operator|!
name|linkInPlace
operator|.
name|isSelected
argument_list|()
operator|&&
name|allowRename
operator|&&
operator|(
operator|!
name|multipleEntries
operator|)
argument_list|)
expr_stmt|;
block|}
block|}
decl_stmt|;
if|if
condition|(
name|multipleEntries
condition|)
block|{
name|linkInPlace
operator|.
name|setText
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Leave files in their current directory."
argument_list|)
argument_list|)
expr_stmt|;
name|copyRadioButton
operator|.
name|setText
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Copy files to %0."
argument_list|,
name|fileType
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|moveRadioButton
operator|.
name|setText
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Move files to %0."
argument_list|,
name|fileType
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|linkInPlace
operator|.
name|setText
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Leave file in its current directory."
argument_list|)
argument_list|)
expr_stmt|;
name|copyRadioButton
operator|.
name|setText
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Copy file to %0."
argument_list|,
name|fileType
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|moveRadioButton
operator|.
name|setText
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Move file to %0."
argument_list|,
name|fileType
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|renameCheckBox
operator|.
name|setText
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Rename to match citekey"
argument_list|)
operator|+
literal|": "
operator|+
name|citekeyOrReason
argument_list|)
expr_stmt|;
name|linkInPlace
operator|.
name|addChangeListener
argument_list|(
name|cl
argument_list|)
expr_stmt|;
name|cl
operator|.
name|stateChanged
argument_list|(
operator|new
name|ChangeEvent
argument_list|(
name|linkInPlace
argument_list|)
argument_list|)
expr_stmt|;
try|try
block|{
return|return
name|JOptionPane
operator|.
name|showConfirmDialog
argument_list|(
name|frame
argument_list|,
name|optionsPanel
argument_list|,
name|dialogTitle
argument_list|,
name|JOptionPane
operator|.
name|OK_CANCEL_OPTION
argument_list|,
name|JOptionPane
operator|.
name|QUESTION_MESSAGE
argument_list|)
return|;
block|}
finally|finally
block|{
name|linkInPlace
operator|.
name|removeChangeListener
argument_list|(
name|cl
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Make a extension to the file.      *       * @param entry      *            The entry to extension from.      * @param fileType      *            The FileType associated with the file.      * @param filename      *            The path to the file.      * @param edits      *            An NamedCompound action this action is to be added to. If none      *            is given, the edit is added to the panel's undoManager.      */
DECL|method|doLink (BibtexEntry entry, ExternalFileType fileType, String filename, NamedCompound edits)
specifier|private
name|void
name|doLink
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|,
name|ExternalFileType
name|fileType
parameter_list|,
name|String
name|filename
parameter_list|,
name|NamedCompound
name|edits
parameter_list|)
block|{
name|UndoableFieldChange
name|edit
init|=
operator|new
name|UndoableFieldChange
argument_list|(
name|entry
argument_list|,
name|fileType
operator|.
name|getFieldName
argument_list|()
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
name|fileType
operator|.
name|getFieldName
argument_list|()
argument_list|)
argument_list|,
name|filename
argument_list|)
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|fileType
operator|.
name|getFieldName
argument_list|()
argument_list|,
name|filename
argument_list|)
expr_stmt|;
if|if
condition|(
name|edits
operator|==
literal|null
condition|)
block|{
name|panel
operator|.
name|undoManager
operator|.
name|addEdit
argument_list|(
name|edit
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|edits
operator|.
name|addEdit
argument_list|(
name|edit
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Move the given file to the base directory for its file type, and rename      * it to the given filename.      *       * @param fileName      *            The name of the source file.      * @param fileType      *            The FileType associated with the file.      * @param destFilename      *            The destination filename.      * @param edits      *            TODO we should be able to undo this action      * @return true if the operation succeeded.      */
DECL|method|doRename (String fileName, ExternalFileType fileType, String destFilename, NamedCompound edits)
specifier|private
name|boolean
name|doRename
parameter_list|(
name|String
name|fileName
parameter_list|,
name|ExternalFileType
name|fileType
parameter_list|,
name|String
name|destFilename
parameter_list|,
name|NamedCompound
name|edits
parameter_list|)
block|{
name|String
name|dir
init|=
name|panel
operator|.
name|metaData
argument_list|()
operator|.
name|getFileDirectory
argument_list|(
name|fileType
operator|.
name|getFieldName
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|dir
operator|==
literal|null
operator|)
operator|||
operator|!
operator|(
operator|new
name|File
argument_list|(
name|dir
argument_list|)
operator|)
operator|.
name|exists
argument_list|()
condition|)
block|{
comment|// OOps, we don't know which directory to put it in, or the given
comment|// dir doesn't exist....
comment|// This should not happen!!
return|return
literal|false
return|;
block|}
name|destFilename
operator|=
operator|new
name|File
argument_list|(
name|destFilename
argument_list|)
operator|.
name|getName
argument_list|()
expr_stmt|;
name|File
name|f
init|=
operator|new
name|File
argument_list|(
name|fileName
argument_list|)
decl_stmt|;
name|File
name|destFile
init|=
operator|new
name|File
argument_list|(
operator|new
name|StringBuffer
argument_list|(
name|dir
argument_list|)
operator|.
name|append
argument_list|(
name|System
operator|.
name|getProperty
argument_list|(
literal|"file.separator"
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
name|destFilename
argument_list|)
operator|.
name|toString
argument_list|()
argument_list|)
decl_stmt|;
name|f
operator|.
name|renameTo
argument_list|(
name|destFile
argument_list|)
expr_stmt|;
return|return
literal|true
return|;
block|}
comment|/**      * Copy the given file to the base directory for its file type, and give it      * the given name.      *       * @param fileName      *            The name of the source file.      * @param fileType      *            The FileType associated with the file.      * @param toFile      *            The destination filename. An existing path-component will be removed.      * @param edits      *            TODO we should be able to undo this!      * @return      */
DECL|method|doCopy (String fileName, ExternalFileType fileType, String toFile, NamedCompound edits)
specifier|private
name|boolean
name|doCopy
parameter_list|(
name|String
name|fileName
parameter_list|,
name|ExternalFileType
name|fileType
parameter_list|,
name|String
name|toFile
parameter_list|,
name|NamedCompound
name|edits
parameter_list|)
block|{
name|String
name|dir
init|=
name|panel
operator|.
name|metaData
argument_list|()
operator|.
name|getFileDirectory
argument_list|(
name|fileType
operator|.
name|getFieldName
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|dir
operator|==
literal|null
operator|)
operator|||
operator|!
operator|(
operator|new
name|File
argument_list|(
name|dir
argument_list|)
operator|)
operator|.
name|exists
argument_list|()
condition|)
block|{
comment|// OOps, we don't know which directory to put it in, or the given
comment|// dir doesn't exist....
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"dir: "
operator|+
name|dir
operator|+
literal|"\t ext: "
operator|+
name|fileType
operator|.
name|getExtension
argument_list|()
argument_list|)
expr_stmt|;
return|return
literal|false
return|;
block|}
name|toFile
operator|=
operator|new
name|File
argument_list|(
name|toFile
argument_list|)
operator|.
name|getName
argument_list|()
expr_stmt|;
name|File
name|destFile
init|=
operator|new
name|File
argument_list|(
operator|new
name|StringBuffer
argument_list|(
name|dir
argument_list|)
operator|.
name|append
argument_list|(
name|System
operator|.
name|getProperty
argument_list|(
literal|"file.separator"
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
name|toFile
argument_list|)
operator|.
name|toString
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|destFile
operator|.
name|equals
argument_list|(
operator|new
name|File
argument_list|(
name|fileName
argument_list|)
argument_list|)
condition|)
block|{
comment|// File is already in the correct position. Don't override!
return|return
literal|true
return|;
block|}
if|if
condition|(
name|destFile
operator|.
name|exists
argument_list|()
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
literal|"'"
operator|+
name|destFile
operator|.
name|getPath
argument_list|()
operator|+
literal|"' "
operator|+
name|Globals
operator|.
name|lang
argument_list|(
literal|"exists.Overwrite?"
argument_list|)
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"File exists"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|YES_NO_OPTION
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
name|NO_OPTION
condition|)
return|return
literal|false
return|;
block|}
try|try
block|{
name|Util
operator|.
name|copyFile
argument_list|(
operator|new
name|File
argument_list|(
name|fileName
argument_list|)
argument_list|,
name|destFile
argument_list|,
literal|true
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
return|return
literal|false
return|;
block|}
return|return
literal|true
return|;
block|}
block|}
end_class

end_unit

