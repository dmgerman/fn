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
name|FileListEditor
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
name|javax
operator|.
name|swing
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

begin_comment
comment|/**  * Action for moving or renaming a file that is linked to from an entry in JabRef.  */
end_comment

begin_class
DECL|class|MoveFileAction
specifier|public
class|class
name|MoveFileAction
extends|extends
name|AbstractAction
block|{
DECL|field|frame
specifier|private
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|eEditor
specifier|private
name|EntryEditor
name|eEditor
decl_stmt|;
DECL|field|editor
specifier|private
name|FileListEditor
name|editor
decl_stmt|;
DECL|field|toFileDir
specifier|private
name|boolean
name|toFileDir
decl_stmt|;
DECL|method|MoveFileAction (JabRefFrame frame, EntryEditor eEditor, FileListEditor editor, boolean toFileDir)
specifier|public
name|MoveFileAction
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|EntryEditor
name|eEditor
parameter_list|,
name|FileListEditor
name|editor
parameter_list|,
name|boolean
name|toFileDir
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
name|eEditor
operator|=
name|eEditor
expr_stmt|;
name|this
operator|.
name|editor
operator|=
name|editor
expr_stmt|;
name|this
operator|.
name|toFileDir
operator|=
name|toFileDir
expr_stmt|;
block|}
DECL|method|actionPerformed (ActionEvent event)
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|event
parameter_list|)
block|{
name|int
name|selected
init|=
name|editor
operator|.
name|getSelectedRow
argument_list|()
decl_stmt|;
if|if
condition|(
name|selected
operator|==
operator|-
literal|1
condition|)
return|return;
name|FileListEntry
name|flEntry
init|=
name|editor
operator|.
name|getTableModel
argument_list|()
operator|.
name|getEntry
argument_list|(
name|selected
argument_list|)
decl_stmt|;
comment|// Check if the current file exists:
name|String
name|ln
init|=
name|flEntry
operator|.
name|getLink
argument_list|()
decl_stmt|;
name|boolean
name|httpLink
init|=
name|ln
operator|.
name|toLowerCase
argument_list|()
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
comment|// TODO: notify that this operation cannot be done on remote links
block|}
comment|// Get an absolute path representation:
name|String
name|dir
init|=
name|frame
operator|.
name|basePanel
argument_list|()
operator|.
name|metaData
argument_list|()
operator|.
name|getFileDirectory
argument_list|(
name|GUIGlobals
operator|.
name|FILE_FIELD
argument_list|)
decl_stmt|;
name|File
name|file
init|=
name|Util
operator|.
name|expandFilename
argument_list|(
name|ln
argument_list|,
operator|new
name|String
index|[]
block|{
name|dir
block|}
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|file
operator|!=
literal|null
operator|)
operator|&&
name|file
operator|.
name|exists
argument_list|()
condition|)
block|{
comment|// Ok, we found the file. Now get a new name:
name|String
name|extension
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|flEntry
operator|.
name|getType
argument_list|()
operator|!=
literal|null
condition|)
name|extension
operator|=
literal|"."
operator|+
name|flEntry
operator|.
name|getType
argument_list|()
operator|.
name|getExtension
argument_list|()
expr_stmt|;
name|File
name|newFile
init|=
literal|null
decl_stmt|;
name|boolean
name|repeat
init|=
literal|true
decl_stmt|;
while|while
condition|(
name|repeat
condition|)
block|{
name|repeat
operator|=
literal|false
expr_stmt|;
name|String
name|chosenFile
decl_stmt|;
if|if
condition|(
name|toFileDir
condition|)
block|{
name|String
name|suggName
init|=
name|eEditor
operator|.
name|getEntry
argument_list|()
operator|.
name|getCiteKey
argument_list|()
operator|+
name|extension
decl_stmt|;
name|CheckBoxMessage
name|cbm
init|=
operator|new
name|CheckBoxMessage
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Move file to file directory?"
argument_list|)
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Rename to '%0'"
argument_list|,
name|suggName
argument_list|)
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"renameOnMoveFileToFileDir"
argument_list|)
argument_list|)
decl_stmt|;
name|int
name|answer
decl_stmt|;
comment|// Only ask about renaming file if the file doesn't have the proper name already:
if|if
condition|(
operator|!
name|suggName
operator|.
name|equals
argument_list|(
name|file
operator|.
name|getName
argument_list|()
argument_list|)
condition|)
name|answer
operator|=
name|JOptionPane
operator|.
name|showConfirmDialog
argument_list|(
name|frame
argument_list|,
name|cbm
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Move/Rename file"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|YES_NO_OPTION
argument_list|)
expr_stmt|;
else|else
name|answer
operator|=
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
literal|"Move file to file directory?"
argument_list|)
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Move/Rename file"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|YES_NO_OPTION
argument_list|)
expr_stmt|;
if|if
condition|(
name|answer
operator|!=
name|JOptionPane
operator|.
name|YES_OPTION
condition|)
return|return;
name|Globals
operator|.
name|prefs
operator|.
name|putBoolean
argument_list|(
literal|"renameOnMoveFileToFileDir"
argument_list|,
name|cbm
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|(
name|dir
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|dir
operator|.
name|endsWith
argument_list|(
name|File
operator|.
name|separator
argument_list|)
condition|)
name|sb
operator|.
name|append
argument_list|(
name|File
operator|.
name|separator
argument_list|)
expr_stmt|;
if|if
condition|(
name|cbm
operator|.
name|isSelected
argument_list|()
condition|)
block|{
comment|// Rename:
name|sb
operator|.
name|append
argument_list|(
name|suggName
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// Do not rename:
name|sb
operator|.
name|append
argument_list|(
name|file
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|chosenFile
operator|=
name|sb
operator|.
name|toString
argument_list|()
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|chosenFile
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|chosenFile
operator|=
name|Globals
operator|.
name|getNewFile
argument_list|(
name|frame
argument_list|,
name|file
argument_list|,
name|extension
argument_list|,
name|JFileChooser
operator|.
name|SAVE_DIALOG
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|chosenFile
operator|==
literal|null
condition|)
block|{
return|return;
comment|// cancelled
block|}
name|newFile
operator|=
operator|new
name|File
argument_list|(
name|chosenFile
argument_list|)
expr_stmt|;
comment|// Check if the file already exists:
if|if
condition|(
name|newFile
operator|.
name|exists
argument_list|()
operator|&&
operator|(
name|JOptionPane
operator|.
name|showConfirmDialog
argument_list|(
name|frame
argument_list|,
literal|"'"
operator|+
name|newFile
operator|.
name|getName
argument_list|()
operator|+
literal|"' "
operator|+
name|Globals
operator|.
name|lang
argument_list|(
literal|"exists. Overwrite file?"
argument_list|)
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Move/Rename file"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|OK_CANCEL_OPTION
argument_list|)
operator|!=
name|JOptionPane
operator|.
name|OK_OPTION
operator|)
condition|)
block|{
if|if
condition|(
operator|!
name|toFileDir
condition|)
name|repeat
operator|=
literal|true
expr_stmt|;
else|else
return|return;
block|}
block|}
if|if
condition|(
operator|!
name|newFile
operator|.
name|equals
argument_list|(
name|file
argument_list|)
condition|)
block|{
try|try
block|{
name|boolean
name|success
init|=
name|file
operator|.
name|renameTo
argument_list|(
name|newFile
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|success
condition|)
block|{
name|success
operator|=
name|Util
operator|.
name|copyFile
argument_list|(
name|file
argument_list|,
name|newFile
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|success
condition|)
block|{
comment|// Remove the original file:
name|file
operator|.
name|delete
argument_list|()
expr_stmt|;
comment|// Relativise path, if possible.
if|if
condition|(
name|newFile
operator|.
name|getPath
argument_list|()
operator|.
name|startsWith
argument_list|(
name|dir
argument_list|)
condition|)
block|{
if|if
condition|(
operator|(
name|newFile
operator|.
name|getPath
argument_list|()
operator|.
name|length
argument_list|()
operator|>
name|dir
operator|.
name|length
argument_list|()
operator|)
operator|&&
operator|(
name|newFile
operator|.
name|getPath
argument_list|()
operator|.
name|charAt
argument_list|(
name|dir
operator|.
name|length
argument_list|()
argument_list|)
operator|==
name|File
operator|.
name|separatorChar
operator|)
condition|)
name|flEntry
operator|.
name|setLink
argument_list|(
name|newFile
operator|.
name|getPath
argument_list|()
operator|.
name|substring
argument_list|(
literal|1
operator|+
name|dir
operator|.
name|length
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
else|else
name|flEntry
operator|.
name|setLink
argument_list|(
name|newFile
operator|.
name|getPath
argument_list|()
operator|.
name|substring
argument_list|(
name|dir
operator|.
name|length
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
name|flEntry
operator|.
name|setLink
argument_list|(
name|newFile
operator|.
name|getCanonicalPath
argument_list|()
argument_list|)
expr_stmt|;
name|eEditor
operator|.
name|updateField
argument_list|(
name|editor
argument_list|)
expr_stmt|;
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|frame
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"File moved"
argument_list|)
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Move/Rename file"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|INFORMATION_MESSAGE
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
name|Globals
operator|.
name|lang
argument_list|(
literal|"Move file failed"
argument_list|)
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Move/Rename file"
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
name|SecurityException
name|ex
parameter_list|)
block|{
name|ex
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|frame
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Could not move file"
argument_list|)
operator|+
literal|": "
operator|+
name|ex
operator|.
name|getMessage
argument_list|()
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Move/Rename file"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
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
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|frame
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Could not move file"
argument_list|)
operator|+
literal|": "
operator|+
name|ex
operator|.
name|getMessage
argument_list|()
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Move/Rename file"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
block|}
block|}
block|}
else|else
block|{
comment|// File doesn't exist, so we can't move it.
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|frame
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Could not find file '%0'."
argument_list|,
name|flEntry
operator|.
name|getLink
argument_list|()
argument_list|)
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"File not found"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

