begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.importer
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|importer
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
name|javax
operator|.
name|swing
operator|.
name|undo
operator|.
name|UndoManager
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|beans
operator|.
name|property
operator|.
name|SimpleStringProperty
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|beans
operator|.
name|property
operator|.
name|StringProperty
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|collections
operator|.
name|FXCollections
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|collections
operator|.
name|ObservableList
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
name|AbstractViewModel
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
name|StateManager
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
name|duplicationFinder
operator|.
name|DuplicateResolverDialog
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
name|externalfiles
operator|.
name|ImportHandler
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
name|externalfiletype
operator|.
name|ExternalFileTypes
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
name|bibtex
operator|.
name|DuplicateCheck
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
name|model
operator|.
name|util
operator|.
name|FileUpdateMonitor
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
name|PreferencesService
import|;
end_import

begin_class
DECL|class|ImportEntriesViewModel
specifier|public
class|class
name|ImportEntriesViewModel
extends|extends
name|AbstractViewModel
block|{
DECL|field|task
specifier|private
specifier|final
name|BackgroundTask
argument_list|<
name|List
argument_list|<
name|BibEntry
argument_list|>
argument_list|>
name|task
decl_stmt|;
DECL|field|message
specifier|private
specifier|final
name|StringProperty
name|message
decl_stmt|;
DECL|field|database
specifier|private
specifier|final
name|BibDatabaseContext
name|database
decl_stmt|;
DECL|field|dialogService
specifier|private
specifier|final
name|DialogService
name|dialogService
decl_stmt|;
DECL|field|undoManager
specifier|private
specifier|final
name|UndoManager
name|undoManager
decl_stmt|;
DECL|field|stateManager
specifier|private
specifier|final
name|StateManager
name|stateManager
decl_stmt|;
DECL|field|fileUpdateMonitor
specifier|private
specifier|final
name|FileUpdateMonitor
name|fileUpdateMonitor
decl_stmt|;
DECL|field|entries
specifier|private
name|ObservableList
argument_list|<
name|BibEntry
argument_list|>
name|entries
decl_stmt|;
DECL|field|preferences
specifier|private
name|PreferencesService
name|preferences
decl_stmt|;
DECL|method|ImportEntriesViewModel (BackgroundTask<List<BibEntry>> task, TaskExecutor taskExecutor, BibDatabaseContext database, DialogService dialogService, UndoManager undoManager, PreferencesService preferences, StateManager stateManager, FileUpdateMonitor fileUpdateMonitor)
specifier|public
name|ImportEntriesViewModel
parameter_list|(
name|BackgroundTask
argument_list|<
name|List
argument_list|<
name|BibEntry
argument_list|>
argument_list|>
name|task
parameter_list|,
name|TaskExecutor
name|taskExecutor
parameter_list|,
name|BibDatabaseContext
name|database
parameter_list|,
name|DialogService
name|dialogService
parameter_list|,
name|UndoManager
name|undoManager
parameter_list|,
name|PreferencesService
name|preferences
parameter_list|,
name|StateManager
name|stateManager
parameter_list|,
name|FileUpdateMonitor
name|fileUpdateMonitor
parameter_list|)
block|{
name|this
operator|.
name|task
operator|=
name|task
expr_stmt|;
name|this
operator|.
name|database
operator|=
name|database
expr_stmt|;
name|this
operator|.
name|dialogService
operator|=
name|dialogService
expr_stmt|;
name|this
operator|.
name|undoManager
operator|=
name|undoManager
expr_stmt|;
name|this
operator|.
name|preferences
operator|=
name|preferences
expr_stmt|;
name|this
operator|.
name|stateManager
operator|=
name|stateManager
expr_stmt|;
name|this
operator|.
name|fileUpdateMonitor
operator|=
name|fileUpdateMonitor
expr_stmt|;
name|this
operator|.
name|entries
operator|=
name|FXCollections
operator|.
name|observableArrayList
argument_list|()
expr_stmt|;
name|this
operator|.
name|message
operator|=
operator|new
name|SimpleStringProperty
argument_list|()
expr_stmt|;
name|this
operator|.
name|message
operator|.
name|bind
argument_list|(
name|task
operator|.
name|messageProperty
argument_list|()
argument_list|)
expr_stmt|;
name|task
operator|.
name|onSuccess
argument_list|(
name|entriesToImport
lambda|->
name|entries
operator|.
name|addAll
argument_list|(
name|entriesToImport
argument_list|)
argument_list|)
operator|.
name|executeWith
argument_list|(
name|taskExecutor
argument_list|)
expr_stmt|;
block|}
DECL|method|getMessage ()
specifier|public
name|String
name|getMessage
parameter_list|()
block|{
return|return
name|message
operator|.
name|get
argument_list|()
return|;
block|}
DECL|method|messageProperty ()
specifier|public
name|StringProperty
name|messageProperty
parameter_list|()
block|{
return|return
name|message
return|;
block|}
DECL|method|getEntries ()
specifier|public
name|ObservableList
argument_list|<
name|BibEntry
argument_list|>
name|getEntries
parameter_list|()
block|{
return|return
name|entries
return|;
block|}
DECL|method|hasDuplicate (BibEntry entry)
specifier|public
name|boolean
name|hasDuplicate
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
return|return
name|findInternalDuplicate
argument_list|(
name|entry
argument_list|)
operator|.
name|isPresent
argument_list|()
operator|||
name|DuplicateCheck
operator|.
name|containsDuplicate
argument_list|(
name|database
operator|.
name|getDatabase
argument_list|()
argument_list|,
name|entry
argument_list|,
name|database
operator|.
name|getMode
argument_list|()
argument_list|)
operator|.
name|isPresent
argument_list|()
return|;
block|}
DECL|method|importEntries (List<BibEntry> entriesToImport)
specifier|public
name|void
name|importEntries
parameter_list|(
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entriesToImport
parameter_list|)
block|{
comment|// Check if we are supposed to warn about duplicates.
comment|// If so, then see if there are duplicates, and warn if yes.
if|if
condition|(
name|preferences
operator|.
name|shouldWarnAboutDuplicatesForImport
argument_list|()
condition|)
block|{
name|BackgroundTask
operator|.
name|wrap
argument_list|(
parameter_list|()
lambda|->
name|entriesToImport
operator|.
name|stream
argument_list|()
operator|.
name|anyMatch
argument_list|(
name|this
operator|::
name|hasDuplicate
argument_list|)
argument_list|)
operator|.
name|onSuccess
argument_list|(
name|e
lambda|->
block|{
lambda|if (e
argument_list|)
block|{
name|boolean
name|continueImport
operator|=
name|dialogService
operator|.
name|showConfirmationDialogWithOptOutAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Duplicates found"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"There are possible duplicates (marked with an icon) that haven't been resolved. Continue?"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Continue with import"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Cancel import"
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
name|preferences
operator|.
name|setShouldWarnAboutDuplicatesForImport
argument_list|(
operator|!
name|optOut
argument_list|)
argument_list|)
block|;
if|if
condition|(
operator|!
name|continueImport
condition|)
block|{
name|dialogService
operator|.
name|notify
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Import canceled"
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|ImportHandler
name|importHandler
init|=
operator|new
name|ImportHandler
argument_list|(
name|dialogService
argument_list|,
name|database
argument_list|,
name|ExternalFileTypes
operator|.
name|getInstance
argument_list|()
argument_list|,
name|preferences
operator|.
name|getFilePreferences
argument_list|()
argument_list|,
name|preferences
operator|.
name|getImportFormatPreferences
argument_list|()
argument_list|,
name|preferences
operator|.
name|getUpdateFieldPreferences
argument_list|()
argument_list|,
name|fileUpdateMonitor
argument_list|,
name|undoManager
argument_list|,
name|stateManager
argument_list|)
decl_stmt|;
name|importHandler
operator|.
name|importEntries
argument_list|(
name|entriesToImport
argument_list|)
expr_stmt|;
name|dialogService
operator|.
name|notify
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Number of entries successfully imported"
argument_list|)
operator|+
literal|": "
operator|+
name|entriesToImport
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|)
operator|.
name|executeWith
argument_list|(
name|Globals
operator|.
name|TASK_EXECUTOR
argument_list|)
expr_stmt|;
end_class

begin_comment
unit|}      }
comment|/**      * Checks if there are duplicates to the given entry in the list of entries to be imported.      *      * @param entry The entry to search for duplicates of.      * @return A possible duplicate, if any, or null if none were found.      */
end_comment

begin_function
DECL|method|findInternalDuplicate (BibEntry entry)
specifier|private
name|Optional
argument_list|<
name|BibEntry
argument_list|>
name|findInternalDuplicate
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
for|for
control|(
name|BibEntry
name|othEntry
range|:
name|entries
control|)
block|{
if|if
condition|(
name|othEntry
operator|.
name|equals
argument_list|(
name|entry
argument_list|)
condition|)
block|{
continue|continue;
comment|// Don't compare the entry to itself
block|}
if|if
condition|(
name|DuplicateCheck
operator|.
name|isDuplicate
argument_list|(
name|entry
argument_list|,
name|othEntry
argument_list|,
name|database
operator|.
name|getMode
argument_list|()
argument_list|)
condition|)
block|{
return|return
name|Optional
operator|.
name|of
argument_list|(
name|othEntry
argument_list|)
return|;
block|}
block|}
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
end_function

begin_function
DECL|method|resolveDuplicate (BibEntry entry)
specifier|public
name|void
name|resolveDuplicate
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
comment|// First, try to find duplicate in the existing library
name|Optional
argument_list|<
name|BibEntry
argument_list|>
name|other
init|=
name|DuplicateCheck
operator|.
name|containsDuplicate
argument_list|(
name|database
operator|.
name|getDatabase
argument_list|()
argument_list|,
name|entry
argument_list|,
name|database
operator|.
name|getMode
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|other
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|DuplicateResolverDialog
name|dialog
init|=
operator|new
name|DuplicateResolverDialog
argument_list|(
name|other
operator|.
name|get
argument_list|()
argument_list|,
name|entry
argument_list|,
name|DuplicateResolverDialog
operator|.
name|DuplicateResolverType
operator|.
name|INSPECTION
argument_list|,
name|database
argument_list|)
decl_stmt|;
name|DuplicateResolverDialog
operator|.
name|DuplicateResolverResult
name|result
init|=
name|dialog
operator|.
name|showAndWait
argument_list|()
operator|.
name|orElse
argument_list|(
name|DuplicateResolverDialog
operator|.
name|DuplicateResolverResult
operator|.
name|BREAK
argument_list|)
decl_stmt|;
if|if
condition|(
name|result
operator|==
name|DuplicateResolverDialog
operator|.
name|DuplicateResolverResult
operator|.
name|KEEP_LEFT
condition|)
block|{
comment|// TODO: Remove old entry. Or... add it to a list of entries
comment|// to be deleted. We only delete
comment|// it after Ok is clicked.
comment|// entriesToDelete.add(other.get());
block|}
elseif|else
if|if
condition|(
name|result
operator|==
name|DuplicateResolverDialog
operator|.
name|DuplicateResolverResult
operator|.
name|KEEP_RIGHT
condition|)
block|{
comment|// Remove the entry from the import inspection dialog.
name|entries
operator|.
name|remove
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|result
operator|==
name|DuplicateResolverDialog
operator|.
name|DuplicateResolverResult
operator|.
name|KEEP_BOTH
condition|)
block|{
comment|// Do nothing.
block|}
elseif|else
if|if
condition|(
name|result
operator|==
name|DuplicateResolverDialog
operator|.
name|DuplicateResolverResult
operator|.
name|KEEP_MERGE
condition|)
block|{
comment|// TODO: Remove old entry. Or... add it to a list of entries
comment|// to be deleted. We only delete
comment|// it after Ok is clicked.
comment|//entriesToDelete.add(other.get());
comment|// Replace entry by merged entry
name|entries
operator|.
name|add
argument_list|(
name|dialog
operator|.
name|getMergedEntry
argument_list|()
argument_list|)
expr_stmt|;
name|entries
operator|.
name|remove
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
return|return;
block|}
comment|// Second, check if the duplicate is of another entry in the import:
name|other
operator|=
name|findInternalDuplicate
argument_list|(
name|entry
argument_list|)
expr_stmt|;
if|if
condition|(
name|other
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|DuplicateResolverDialog
name|diag
init|=
operator|new
name|DuplicateResolverDialog
argument_list|(
name|entry
argument_list|,
name|other
operator|.
name|get
argument_list|()
argument_list|,
name|DuplicateResolverDialog
operator|.
name|DuplicateResolverType
operator|.
name|DUPLICATE_SEARCH
argument_list|,
name|database
argument_list|)
decl_stmt|;
name|DuplicateResolverDialog
operator|.
name|DuplicateResolverResult
name|answer
init|=
name|diag
operator|.
name|showAndWait
argument_list|()
operator|.
name|orElse
argument_list|(
name|DuplicateResolverDialog
operator|.
name|DuplicateResolverResult
operator|.
name|BREAK
argument_list|)
decl_stmt|;
if|if
condition|(
name|answer
operator|==
name|DuplicateResolverDialog
operator|.
name|DuplicateResolverResult
operator|.
name|KEEP_LEFT
condition|)
block|{
comment|// Remove other entry
name|entries
operator|.
name|remove
argument_list|(
name|other
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|answer
operator|==
name|DuplicateResolverDialog
operator|.
name|DuplicateResolverResult
operator|.
name|KEEP_RIGHT
condition|)
block|{
comment|// Remove entry
name|entries
operator|.
name|remove
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|answer
operator|==
name|DuplicateResolverDialog
operator|.
name|DuplicateResolverResult
operator|.
name|KEEP_BOTH
condition|)
block|{
comment|// Do nothing
block|}
elseif|else
if|if
condition|(
name|answer
operator|==
name|DuplicateResolverDialog
operator|.
name|DuplicateResolverResult
operator|.
name|KEEP_MERGE
condition|)
block|{
comment|// Replace both entries by merged entry
name|entries
operator|.
name|add
argument_list|(
name|diag
operator|.
name|getMergedEntry
argument_list|()
argument_list|)
expr_stmt|;
name|entries
operator|.
name|remove
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|entries
operator|.
name|remove
argument_list|(
name|other
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_function

unit|}
end_unit

