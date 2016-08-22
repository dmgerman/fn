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
name|JOptionPane
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
name|entryeditor
operator|.
name|EntryEditor
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
name|fieldeditors
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
name|util
operator|.
name|component
operator|.
name|CheckBoxMessage
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
name|layout
operator|.
name|LayoutFormatterPreferences
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
name|preferences
operator|.
name|JabRefPreferences
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
name|MoveFileAction
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|frame
specifier|private
specifier|final
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|eEditor
specifier|private
specifier|final
name|EntryEditor
name|eEditor
decl_stmt|;
DECL|field|editor
specifier|private
specifier|final
name|FileListEditor
name|editor
decl_stmt|;
DECL|field|toFileDir
specifier|private
specifier|final
name|boolean
name|toFileDir
decl_stmt|;
DECL|field|MOVE_RENAME
specifier|private
specifier|static
specifier|final
name|String
name|MOVE_RENAME
init|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"Move/Rename file"
argument_list|)
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
annotation|@
name|Override
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
block|{
return|return;
block|}
name|FileListEntry
name|entry
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
name|entry
operator|.
name|link
decl_stmt|;
name|boolean
name|httpLink
init|=
name|ln
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
comment|// TODO: notify that this operation cannot be done on remote links
return|return;
block|}
comment|// Get an absolute path representation:
name|List
argument_list|<
name|String
argument_list|>
name|dirs
init|=
name|frame
operator|.
name|getCurrentBasePanel
argument_list|()
operator|.
name|getBibDatabaseContext
argument_list|()
operator|.
name|getFileDirectory
argument_list|()
decl_stmt|;
name|int
name|found
init|=
operator|-
literal|1
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
name|dirs
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
operator|new
name|File
argument_list|(
name|dirs
operator|.
name|get
argument_list|(
name|i
argument_list|)
argument_list|)
operator|.
name|exists
argument_list|()
condition|)
block|{
name|found
operator|=
name|i
expr_stmt|;
break|break;
block|}
block|}
if|if
condition|(
name|found
operator|<
literal|0
condition|)
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
literal|"File_directory_is_not_set_or_does_not_exist!"
argument_list|)
argument_list|,
name|MOVE_RENAME
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
return|return;
block|}
name|File
name|file
init|=
operator|new
name|File
argument_list|(
name|ln
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|file
operator|.
name|isAbsolute
argument_list|()
condition|)
block|{
name|file
operator|=
name|FileUtil
operator|.
name|expandFilename
argument_list|(
name|ln
argument_list|,
name|dirs
argument_list|)
operator|.
name|orElse
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
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
comment|// Determine which name to suggest:
name|String
name|suggName
init|=
name|FileUtil
operator|.
name|createFileNameFromPattern
argument_list|(
name|eEditor
operator|.
name|getDatabase
argument_list|()
argument_list|,
name|eEditor
operator|.
name|getEntry
argument_list|()
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|IMPORT_FILENAMEPATTERN
argument_list|)
argument_list|,
name|LayoutFormatterPreferences
operator|.
name|fromPreferences
argument_list|(
name|Globals
operator|.
name|prefs
argument_list|,
name|Globals
operator|.
name|journalAbbreviationLoader
argument_list|)
argument_list|)
operator|.
name|concat
argument_list|(
name|entry
operator|.
name|type
operator|.
name|isPresent
argument_list|()
condition|?
literal|"."
operator|+
name|entry
operator|.
name|type
operator|.
name|get
argument_list|()
operator|.
name|getExtension
argument_list|()
else|:
literal|""
argument_list|)
decl_stmt|;
name|CheckBoxMessage
name|cbm
init|=
operator|new
name|CheckBoxMessage
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Move file to file directory?"
argument_list|)
argument_list|,
name|Localization
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
name|JabRefPreferences
operator|.
name|RENAME_ON_MOVE_FILE_TO_FILE_DIR
argument_list|)
argument_list|)
decl_stmt|;
name|int
name|answer
decl_stmt|;
comment|// Only ask about renaming file if the file doesn't have the proper name already:
if|if
condition|(
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
block|{
name|answer
operator|=
name|JOptionPane
operator|.
name|showConfirmDialog
argument_list|(
name|frame
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Move file to file directory?"
argument_list|)
argument_list|,
name|MOVE_RENAME
argument_list|,
name|JOptionPane
operator|.
name|YES_NO_OPTION
argument_list|)
expr_stmt|;
block|}
else|else
block|{
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
name|MOVE_RENAME
argument_list|,
name|JOptionPane
operator|.
name|YES_NO_OPTION
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|answer
operator|!=
name|JOptionPane
operator|.
name|YES_OPTION
condition|)
block|{
return|return;
block|}
name|Globals
operator|.
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|RENAME_ON_MOVE_FILE_TO_FILE_DIR
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
name|dirs
operator|.
name|get
argument_list|(
name|found
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|dirs
operator|.
name|get
argument_list|(
name|found
argument_list|)
operator|.
name|endsWith
argument_list|(
name|File
operator|.
name|separator
argument_list|)
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|File
operator|.
name|separator
argument_list|)
expr_stmt|;
block|}
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
block|}
else|else
block|{
name|Optional
argument_list|<
name|Path
argument_list|>
name|path
init|=
operator|new
name|FileDialog
argument_list|(
name|frame
argument_list|,
name|file
operator|.
name|getPath
argument_list|()
argument_list|)
operator|.
name|saveNewFile
argument_list|()
decl_stmt|;
if|if
condition|(
name|path
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|chosenFile
operator|=
name|path
operator|.
name|get
argument_list|()
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
else|else
block|{
return|return;
block|}
block|}
name|newFile
operator|=
operator|new
name|File
argument_list|(
name|chosenFile
argument_list|)
expr_stmt|;
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
name|FileUtil
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
name|Files
operator|.
name|deleteIfExists
argument_list|(
name|file
operator|.
name|toPath
argument_list|()
argument_list|)
expr_stmt|;
comment|// Relativise path, if possible.
name|String
name|canPath
init|=
operator|new
name|File
argument_list|(
name|dirs
operator|.
name|get
argument_list|(
name|found
argument_list|)
argument_list|)
operator|.
name|getCanonicalPath
argument_list|()
decl_stmt|;
if|if
condition|(
name|newFile
operator|.
name|getCanonicalPath
argument_list|()
operator|.
name|startsWith
argument_list|(
name|canPath
argument_list|)
condition|)
block|{
if|if
condition|(
operator|(
name|newFile
operator|.
name|getCanonicalPath
argument_list|()
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
name|newFile
operator|.
name|getCanonicalPath
argument_list|()
operator|.
name|charAt
argument_list|(
name|canPath
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
block|{
name|String
name|newLink
init|=
name|newFile
operator|.
name|getCanonicalPath
argument_list|()
operator|.
name|substring
argument_list|(
literal|1
operator|+
name|canPath
operator|.
name|length
argument_list|()
argument_list|)
decl_stmt|;
name|editor
operator|.
name|getTableModel
argument_list|()
operator|.
name|setEntry
argument_list|(
name|selected
argument_list|,
operator|new
name|FileListEntry
argument_list|(
name|entry
operator|.
name|description
argument_list|,
name|newLink
argument_list|,
name|entry
operator|.
name|type
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|String
name|newLink
init|=
name|newFile
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
argument_list|)
decl_stmt|;
name|editor
operator|.
name|getTableModel
argument_list|()
operator|.
name|setEntry
argument_list|(
name|selected
argument_list|,
operator|new
name|FileListEntry
argument_list|(
name|entry
operator|.
name|description
argument_list|,
name|newLink
argument_list|,
name|entry
operator|.
name|type
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|String
name|newLink
init|=
name|newFile
operator|.
name|getCanonicalPath
argument_list|()
decl_stmt|;
name|editor
operator|.
name|getTableModel
argument_list|()
operator|.
name|setEntry
argument_list|(
name|selected
argument_list|,
operator|new
name|FileListEntry
argument_list|(
name|entry
operator|.
name|description
argument_list|,
name|newLink
argument_list|,
name|entry
operator|.
name|type
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|eEditor
operator|.
name|updateField
argument_list|(
name|editor
argument_list|)
expr_stmt|;
name|frame
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"File moved"
argument_list|)
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
literal|"Move file failed"
argument_list|)
argument_list|,
name|MOVE_RENAME
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
decl||
name|IOException
name|ex
parameter_list|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Could not move file"
argument_list|,
name|ex
argument_list|)
expr_stmt|;
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
literal|"Could not move file '%0'."
argument_list|,
name|file
operator|.
name|getAbsolutePath
argument_list|()
argument_list|)
operator|+
name|ex
operator|.
name|getMessage
argument_list|()
argument_list|,
name|MOVE_RENAME
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
name|Localization
operator|.
name|lang
argument_list|(
literal|"Could not find file '%0'."
argument_list|,
name|entry
operator|.
name|link
argument_list|)
argument_list|,
name|Localization
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

