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
name|Optional
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
name|DialogService
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
name|CleanupDialog
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
name|BackgroundTask
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
name|TaskExecutor
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
implements|implements
name|BaseAction
block|{
DECL|field|panel
specifier|private
specifier|final
name|BasePanel
name|panel
decl_stmt|;
DECL|field|dialogService
specifier|private
specifier|final
name|DialogService
name|dialogService
decl_stmt|;
DECL|field|taskExecutor
specifier|private
specifier|final
name|TaskExecutor
name|taskExecutor
decl_stmt|;
DECL|field|isCanceled
specifier|private
name|boolean
name|isCanceled
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
DECL|method|CleanupAction (BasePanel panel, JabRefPreferences preferences, TaskExecutor taskExecutor)
specifier|public
name|CleanupAction
parameter_list|(
name|BasePanel
name|panel
parameter_list|,
name|JabRefPreferences
name|preferences
parameter_list|,
name|TaskExecutor
name|taskExecutor
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
name|preferences
operator|=
name|preferences
expr_stmt|;
name|this
operator|.
name|dialogService
operator|=
name|panel
operator|.
name|frame
argument_list|()
operator|.
name|getDialogService
argument_list|()
expr_stmt|;
name|this
operator|.
name|taskExecutor
operator|=
name|taskExecutor
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|action ()
specifier|public
name|void
name|action
parameter_list|()
block|{
name|init
argument_list|()
expr_stmt|;
if|if
condition|(
name|isCanceled
condition|)
block|{
return|return;
block|}
name|CleanupDialog
name|cleanupDialog
init|=
operator|new
name|CleanupDialog
argument_list|(
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
argument_list|,
name|preferences
operator|.
name|getCleanupPreset
argument_list|()
argument_list|,
name|preferences
operator|.
name|getFilePreferences
argument_list|()
argument_list|)
decl_stmt|;
name|Optional
argument_list|<
name|CleanupPreset
argument_list|>
name|chosenPreset
init|=
name|cleanupDialog
operator|.
name|showAndWait
argument_list|()
decl_stmt|;
if|if
condition|(
name|chosenPreset
operator|.
name|isPresent
argument_list|()
condition|)
block|{
if|if
condition|(
name|chosenPreset
operator|.
name|get
argument_list|()
operator|.
name|isRenamePDFActive
argument_list|()
operator|&&
name|preferences
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|ASK_AUTO_NAMING_PDFS_AGAIN
argument_list|)
condition|)
block|{
name|boolean
name|confirmed
init|=
name|dialogService
operator|.
name|showConfirmationDialogWithOptOutAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Autogenerate PDF Names"
argument_list|)
argument_list|,
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
literal|"Autogenerate PDF Names"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Cancel"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Disable this confirmation dialog"
argument_list|)
argument_list|,
name|optOut
lambda|->
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
operator|!
name|optOut
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|confirmed
condition|)
block|{
name|isCanceled
operator|=
literal|true
expr_stmt|;
return|return;
block|}
block|}
name|preferences
operator|.
name|setCleanupPreset
argument_list|(
name|chosenPreset
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
name|BackgroundTask
operator|.
name|wrap
argument_list|(
parameter_list|()
lambda|->
name|cleanup
argument_list|(
name|chosenPreset
operator|.
name|get
argument_list|()
argument_list|)
argument_list|)
operator|.
name|onSuccess
argument_list|(
name|result
lambda|->
name|showResults
argument_list|()
argument_list|)
operator|.
name|executeWith
argument_list|(
name|taskExecutor
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|init ()
specifier|public
name|void
name|init
parameter_list|()
block|{
name|isCanceled
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
name|dialogService
operator|.
name|showInformationDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Cleanup entry"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"First select entries to clean up."
argument_list|)
argument_list|)
expr_stmt|;
name|isCanceled
operator|=
literal|true
expr_stmt|;
return|return;
block|}
name|dialogService
operator|.
name|notify
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
name|cleaner
operator|.
name|cleanup
argument_list|(
name|preset
argument_list|,
name|entry
argument_list|)
decl_stmt|;
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
DECL|method|showResults ()
specifier|private
name|void
name|showResults
parameter_list|()
block|{
if|if
condition|(
name|isCanceled
condition|)
block|{
return|return;
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
name|dialogService
operator|.
name|notify
argument_list|(
name|message
argument_list|)
expr_stmt|;
block|}
DECL|method|cleanup (CleanupPreset cleanupPreset)
specifier|private
name|void
name|cleanup
parameter_list|(
name|CleanupPreset
name|cleanupPreset
parameter_list|)
block|{
name|preferences
operator|.
name|setCleanupPreset
argument_list|(
name|cleanupPreset
argument_list|)
expr_stmt|;
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
block|}
end_class

end_unit

