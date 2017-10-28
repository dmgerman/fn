begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.actions
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|actions
package|;
end_package

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
name|Objects
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
name|BasePanel
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
name|cleanup
operator|.
name|CleanupPresetPanel
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
name|undo
operator|.
name|NamedCompound
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
name|undo
operator|.
name|UndoableFieldChange
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
name|util
operator|.
name|DefaultTaskExecutor
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
name|util
operator|.
name|component
operator|.
name|CheckBoxMessage
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
name|worker
operator|.
name|AbstractWorker
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
name|cleanup
operator|.
name|CleanupPreset
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
name|cleanup
operator|.
name|CleanupWorker
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
name|FieldChange
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
name|entry
operator|.
name|BibEntry
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

begin_class
DECL|class|CleanupAction
specifier|public
class|class
name|CleanupAction
extends|extends
name|AbstractWorker
block|{
DECL|field|panel
specifier|private
specifier|final
name|BasePanel
name|panel
decl_stmt|;
DECL|field|frame
specifier|private
specifier|final
name|JabRefFrame
name|frame
decl_stmt|;
comment|/**      * Global variable to count unsuccessful renames      */
DECL|field|unsuccessfulRenames
specifier|private
name|int
name|unsuccessfulRenames
decl_stmt|;
DECL|field|canceled
specifier|private
name|boolean
name|canceled
decl_stmt|;
DECL|field|modifiedEntriesCount
specifier|private
name|int
name|modifiedEntriesCount
decl_stmt|;
DECL|field|preferences
specifier|private
specifier|final
name|JabRefPreferences
name|preferences
decl_stmt|;
DECL|method|CleanupAction (BasePanel panel, JabRefPreferences preferences)
specifier|public
name|CleanupAction
parameter_list|(
name|BasePanel
name|panel
parameter_list|,
name|JabRefPreferences
name|preferences
parameter_list|)
block|{
name|this
operator|.
name|panel
operator|=
name|panel
expr_stmt|;
name|this
operator|.
name|frame
operator|=
name|panel
operator|.
name|frame
argument_list|()
expr_stmt|;
name|this
operator|.
name|preferences
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|preferences
argument_list|)
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
name|canceled
operator|=
literal|false
expr_stmt|;
name|modifiedEntriesCount
operator|=
literal|0
expr_stmt|;
if|if
condition|(
name|panel
operator|.
name|getSelectedEntries
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
comment|// None selected. Inform the user to select entries first.
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
literal|"First select entries to clean up."
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Cleanup entry"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|INFORMATION_MESSAGE
argument_list|)
expr_stmt|;
name|canceled
operator|=
literal|true
expr_stmt|;
return|return;
block|}
name|frame
operator|.
name|block
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
literal|"Doing a cleanup for %0 entries..."
argument_list|,
name|Integer
operator|.
name|toString
argument_list|(
name|panel
operator|.
name|getSelectedEntries
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
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
name|canceled
condition|)
block|{
return|return;
block|}
name|CleanupPresetPanel
name|presetPanel
init|=
operator|new
name|CleanupPresetPanel
argument_list|(
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
argument_list|,
name|CleanupPreset
operator|.
name|loadFromPreferences
argument_list|(
name|preferences
argument_list|)
argument_list|)
decl_stmt|;
name|int
name|choice
init|=
name|showDialog
argument_list|(
name|presetPanel
argument_list|)
decl_stmt|;
if|if
condition|(
name|choice
operator|!=
name|JOptionPane
operator|.
name|OK_OPTION
condition|)
block|{
name|canceled
operator|=
literal|true
expr_stmt|;
return|return;
block|}
name|CleanupPreset
name|cleanupPreset
init|=
name|presetPanel
operator|.
name|getCleanupPreset
argument_list|()
decl_stmt|;
name|cleanupPreset
operator|.
name|storeInPreferences
argument_list|(
name|preferences
argument_list|)
expr_stmt|;
if|if
condition|(
name|cleanupPreset
operator|.
name|isRenamePDF
argument_list|()
operator|&&
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|ASK_AUTO_NAMING_PDFS_AGAIN
argument_list|)
condition|)
block|{
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
literal|"Auto-generating PDF-Names does not support undo. Continue?"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Disable this confirmation dialog"
argument_list|)
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|int
name|answer
init|=
name|JOptionPane
operator|.
name|showConfirmDialog
argument_list|(
name|frame
argument_list|,
name|cbm
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Autogenerate PDF Names"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|YES_NO_OPTION
argument_list|)
decl_stmt|;
if|if
condition|(
name|cbm
operator|.
name|isSelected
argument_list|()
condition|)
block|{
name|Globals
operator|.
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|ASK_AUTO_NAMING_PDFS_AGAIN
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|answer
operator|==
name|JOptionPane
operator|.
name|NO_OPTION
condition|)
block|{
name|canceled
operator|=
literal|true
expr_stmt|;
return|return;
block|}
block|}
for|for
control|(
name|BibEntry
name|entry
range|:
name|panel
operator|.
name|getSelectedEntries
argument_list|()
control|)
block|{
comment|// undo granularity is on entry level
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
literal|"Cleanup entry"
argument_list|)
argument_list|)
decl_stmt|;
name|doCleanup
argument_list|(
name|cleanupPreset
argument_list|,
name|entry
argument_list|,
name|ce
argument_list|)
expr_stmt|;
name|ce
operator|.
name|end
argument_list|()
expr_stmt|;
if|if
condition|(
name|ce
operator|.
name|hasEdits
argument_list|()
condition|)
block|{
name|modifiedEntriesCount
operator|++
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
block|}
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
name|canceled
condition|)
block|{
name|frame
operator|.
name|unblock
argument_list|()
expr_stmt|;
return|return;
block|}
if|if
condition|(
name|unsuccessfulRenames
operator|>
literal|0
condition|)
block|{
comment|//Rename failed for at least one entry
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
literal|"File rename failed for %0 entries."
argument_list|,
name|Integer
operator|.
name|toString
argument_list|(
name|unsuccessfulRenames
argument_list|)
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Autogenerate PDF Names"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|INFORMATION_MESSAGE
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|modifiedEntriesCount
operator|>
literal|0
condition|)
block|{
name|panel
operator|.
name|updateEntryEditorIfShowing
argument_list|()
expr_stmt|;
name|panel
operator|.
name|markBaseChanged
argument_list|()
expr_stmt|;
block|}
name|String
name|message
decl_stmt|;
switch|switch
condition|(
name|modifiedEntriesCount
condition|)
block|{
case|case
literal|0
case|:
name|message
operator|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"No entry needed a clean up"
argument_list|)
expr_stmt|;
break|break;
case|case
literal|1
case|:
name|message
operator|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"One entry needed a clean up"
argument_list|)
expr_stmt|;
break|break;
default|default:
name|message
operator|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"%0 entries needed a clean up"
argument_list|,
name|Integer
operator|.
name|toString
argument_list|(
name|modifiedEntriesCount
argument_list|)
argument_list|)
expr_stmt|;
break|break;
block|}
name|panel
operator|.
name|output
argument_list|(
name|message
argument_list|)
expr_stmt|;
name|frame
operator|.
name|unblock
argument_list|()
expr_stmt|;
block|}
DECL|method|showDialog (CleanupPresetPanel presetPanel)
specifier|private
name|int
name|showDialog
parameter_list|(
name|CleanupPresetPanel
name|presetPanel
parameter_list|)
block|{
name|String
name|dialogTitle
init|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"Cleanup entries"
argument_list|)
decl_stmt|;
name|Object
index|[]
name|messages
init|=
block|{
name|Localization
operator|.
name|lang
argument_list|(
literal|"What would you like to clean up?"
argument_list|)
block|,
name|presetPanel
operator|.
name|getScrollPane
argument_list|()
block|}
decl_stmt|;
return|return
name|JOptionPane
operator|.
name|showConfirmDialog
argument_list|(
name|frame
argument_list|,
name|messages
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
comment|/**      * Runs the cleanup on the entry and records the change.      */
DECL|method|doCleanup (CleanupPreset preset, BibEntry entry, NamedCompound ce)
specifier|private
name|void
name|doCleanup
parameter_list|(
name|CleanupPreset
name|preset
parameter_list|,
name|BibEntry
name|entry
parameter_list|,
name|NamedCompound
name|ce
parameter_list|)
block|{
comment|// Create and run cleaner
name|CleanupWorker
name|cleaner
init|=
operator|new
name|CleanupWorker
argument_list|(
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
argument_list|,
name|preferences
operator|.
name|getCleanupPreferences
argument_list|(
name|Globals
operator|.
name|journalAbbreviationLoader
argument_list|)
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|FieldChange
argument_list|>
name|changes
init|=
name|DefaultTaskExecutor
operator|.
name|runInJavaFXThread
argument_list|(
parameter_list|()
lambda|->
name|cleaner
operator|.
name|cleanup
argument_list|(
name|preset
argument_list|,
name|entry
argument_list|)
argument_list|)
decl_stmt|;
name|unsuccessfulRenames
operator|=
name|cleaner
operator|.
name|getUnsuccessfulRenames
argument_list|()
expr_stmt|;
if|if
condition|(
name|changes
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return;
block|}
comment|// Register undo action
for|for
control|(
name|FieldChange
name|change
range|:
name|changes
control|)
block|{
name|ce
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableFieldChange
argument_list|(
name|change
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

