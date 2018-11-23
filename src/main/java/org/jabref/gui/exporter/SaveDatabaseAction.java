begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.exporter
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|exporter
package|;
end_package

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
name|charset
operator|.
name|Charset
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|charset
operator|.
name|UnsupportedCharsetException
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
name|java
operator|.
name|util
operator|.
name|stream
operator|.
name|Collectors
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|ButtonBar
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|ButtonType
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|DialogPane
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|layout
operator|.
name|VBox
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|text
operator|.
name|Text
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
name|dialogs
operator|.
name|AutosaveUIManager
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
name|FileDialogConfiguration
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
name|autosaveandbackup
operator|.
name|AutosaveManager
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
name|autosaveandbackup
operator|.
name|BackupManager
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
name|exporter
operator|.
name|AtomicFileWriter
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
name|exporter
operator|.
name|BibtexDatabaseWriter
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
name|exporter
operator|.
name|SaveException
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
name|exporter
operator|.
name|SavePreferences
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
name|Encodings
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
name|shared
operator|.
name|prefs
operator|.
name|SharedDatabasePreferences
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
name|StandardFileType
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
name|database
operator|.
name|event
operator|.
name|ChangePropagation
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
name|shared
operator|.
name|DatabaseLocation
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
name|org
operator|.
name|slf4j
operator|.
name|Logger
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|LoggerFactory
import|;
end_import

begin_comment
comment|/**  * Action for the "Save" and "Save as" operations called from BasePanel. This class is also used for save operations  * when closing a database or quitting the applications.  *  * The save operation is loaded off of the GUI thread using {@link BackgroundTask}. Callers can query whether the  * operation was canceled, or whether it was successful.  */
end_comment

begin_class
DECL|class|SaveDatabaseAction
specifier|public
class|class
name|SaveDatabaseAction
block|{
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Logger
name|LOGGER
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|SaveDatabaseAction
operator|.
name|class
argument_list|)
decl_stmt|;
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
DECL|field|dialogService
specifier|private
specifier|final
name|DialogService
name|dialogService
decl_stmt|;
DECL|field|prefs
specifier|private
specifier|final
name|JabRefPreferences
name|prefs
decl_stmt|;
DECL|method|SaveDatabaseAction (BasePanel panel, JabRefPreferences prefs)
specifier|public
name|SaveDatabaseAction
parameter_list|(
name|BasePanel
name|panel
parameter_list|,
name|JabRefPreferences
name|prefs
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
name|dialogService
operator|=
name|frame
operator|.
name|getDialogService
argument_list|()
expr_stmt|;
name|this
operator|.
name|prefs
operator|=
name|prefs
expr_stmt|;
block|}
DECL|method|saveDatabase (Path file, boolean selectedOnly, Charset encoding, SavePreferences.DatabaseSaveType saveType)
specifier|private
name|boolean
name|saveDatabase
parameter_list|(
name|Path
name|file
parameter_list|,
name|boolean
name|selectedOnly
parameter_list|,
name|Charset
name|encoding
parameter_list|,
name|SavePreferences
operator|.
name|DatabaseSaveType
name|saveType
parameter_list|)
throws|throws
name|SaveException
block|{
try|try
block|{
name|SavePreferences
name|preferences
init|=
name|prefs
operator|.
name|loadForSaveFromPreferences
argument_list|()
operator|.
name|withEncoding
argument_list|(
name|encoding
argument_list|)
operator|.
name|withSaveType
argument_list|(
name|saveType
argument_list|)
decl_stmt|;
name|AtomicFileWriter
name|fileWriter
init|=
operator|new
name|AtomicFileWriter
argument_list|(
name|file
argument_list|,
name|preferences
operator|.
name|getEncoding
argument_list|()
argument_list|,
name|preferences
operator|.
name|makeBackup
argument_list|()
argument_list|)
decl_stmt|;
name|BibtexDatabaseWriter
name|databaseWriter
init|=
operator|new
name|BibtexDatabaseWriter
argument_list|(
name|fileWriter
argument_list|,
name|preferences
argument_list|)
decl_stmt|;
if|if
condition|(
name|selectedOnly
condition|)
block|{
name|databaseWriter
operator|.
name|savePartOfDatabase
argument_list|(
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
argument_list|,
name|panel
operator|.
name|getSelectedEntries
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|databaseWriter
operator|.
name|saveDatabase
argument_list|(
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|panel
operator|.
name|registerUndoableChanges
argument_list|(
name|databaseWriter
operator|.
name|getSaveActionsFieldChanges
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|fileWriter
operator|.
name|hasEncodingProblems
argument_list|()
condition|)
block|{
name|saveWithDifferentEncoding
argument_list|(
name|file
argument_list|,
name|selectedOnly
argument_list|,
name|preferences
operator|.
name|getEncoding
argument_list|()
argument_list|,
name|fileWriter
operator|.
name|getEncodingProblems
argument_list|()
argument_list|,
name|saveType
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|UnsupportedCharsetException
name|ex
parameter_list|)
block|{
throw|throw
operator|new
name|SaveException
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Character encoding '%0' is not supported."
argument_list|,
name|encoding
operator|.
name|displayName
argument_list|()
argument_list|)
argument_list|,
name|ex
argument_list|)
throw|;
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{
throw|throw
operator|new
name|SaveException
argument_list|(
name|ex
argument_list|)
throw|;
block|}
return|return
literal|true
return|;
block|}
DECL|method|saveWithDifferentEncoding (Path file, boolean selectedOnly, Charset encoding, Set<Character> encodingProblems, SavePreferences.DatabaseSaveType saveType)
specifier|private
name|void
name|saveWithDifferentEncoding
parameter_list|(
name|Path
name|file
parameter_list|,
name|boolean
name|selectedOnly
parameter_list|,
name|Charset
name|encoding
parameter_list|,
name|Set
argument_list|<
name|Character
argument_list|>
name|encodingProblems
parameter_list|,
name|SavePreferences
operator|.
name|DatabaseSaveType
name|saveType
parameter_list|)
throws|throws
name|SaveException
block|{
name|DialogPane
name|pane
init|=
operator|new
name|DialogPane
argument_list|()
decl_stmt|;
name|VBox
name|vbox
init|=
operator|new
name|VBox
argument_list|()
decl_stmt|;
name|vbox
operator|.
name|getChildren
argument_list|()
operator|.
name|addAll
argument_list|(
operator|new
name|Text
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"The chosen encoding '%0' could not encode the following characters:"
argument_list|,
name|encoding
operator|.
name|displayName
argument_list|()
argument_list|)
argument_list|)
argument_list|,
operator|new
name|Text
argument_list|(
name|encodingProblems
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|Object
operator|::
name|toString
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|joining
argument_list|(
literal|"."
argument_list|)
argument_list|)
argument_list|)
argument_list|,
operator|new
name|Text
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"What do you want to do?"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|pane
operator|.
name|setContent
argument_list|(
name|vbox
argument_list|)
expr_stmt|;
name|ButtonType
name|tryDifferentEncoding
init|=
operator|new
name|ButtonType
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Try different encoding"
argument_list|)
argument_list|,
name|ButtonBar
operator|.
name|ButtonData
operator|.
name|OTHER
argument_list|)
decl_stmt|;
name|ButtonType
name|ignore
init|=
operator|new
name|ButtonType
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Ignore"
argument_list|)
argument_list|,
name|ButtonBar
operator|.
name|ButtonData
operator|.
name|APPLY
argument_list|)
decl_stmt|;
name|boolean
name|saveWithDifferentEncoding
init|=
name|frame
operator|.
name|getDialogService
argument_list|()
operator|.
name|showCustomDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Save library"
argument_list|)
argument_list|,
name|pane
argument_list|,
name|ignore
argument_list|,
name|tryDifferentEncoding
argument_list|)
operator|.
name|filter
argument_list|(
name|buttonType
lambda|->
name|buttonType
operator|.
name|equals
argument_list|(
name|tryDifferentEncoding
argument_list|)
argument_list|)
operator|.
name|isPresent
argument_list|()
decl_stmt|;
if|if
condition|(
name|saveWithDifferentEncoding
condition|)
block|{
name|Optional
argument_list|<
name|Charset
argument_list|>
name|newEncoding
init|=
name|frame
operator|.
name|getDialogService
argument_list|()
operator|.
name|showChoiceDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Save library"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Select new encoding"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Save library"
argument_list|)
argument_list|,
name|encoding
argument_list|,
name|Encodings
operator|.
name|getCharsets
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|newEncoding
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|saveDatabase
argument_list|(
name|file
argument_list|,
name|selectedOnly
argument_list|,
name|newEncoding
operator|.
name|get
argument_list|()
argument_list|,
name|saveType
argument_list|)
expr_stmt|;
comment|// Make sure to remember which encoding we used.
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
operator|.
name|getMetaData
argument_list|()
operator|.
name|setEncoding
argument_list|(
name|newEncoding
operator|.
name|get
argument_list|()
argument_list|,
name|ChangePropagation
operator|.
name|DO_NOT_POST_EVENT
argument_list|)
expr_stmt|;
block|}
block|}
block|}
DECL|method|doSave ()
specifier|private
name|boolean
name|doSave
parameter_list|()
block|{
name|Path
name|targetPath
init|=
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
operator|.
name|getDatabasePath
argument_list|()
operator|.
name|get
argument_list|()
decl_stmt|;
try|try
block|{
comment|// Save the database
name|boolean
name|success
init|=
name|saveDatabase
argument_list|(
name|targetPath
argument_list|,
literal|false
argument_list|,
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
operator|.
name|getMetaData
argument_list|()
operator|.
name|getEncoding
argument_list|()
operator|.
name|orElse
argument_list|(
name|prefs
operator|.
name|getDefaultEncoding
argument_list|()
argument_list|)
argument_list|,
name|SavePreferences
operator|.
name|DatabaseSaveType
operator|.
name|ALL
argument_list|)
decl_stmt|;
if|if
condition|(
name|success
condition|)
block|{
name|panel
operator|.
name|updateTimeStamp
argument_list|()
expr_stmt|;
name|panel
operator|.
name|getUndoManager
argument_list|()
operator|.
name|markUnchanged
argument_list|()
expr_stmt|;
comment|// (Only) after a successful save the following
comment|// statement marks that the base is unchanged
comment|// since last save:
name|panel
operator|.
name|setNonUndoableChange
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|panel
operator|.
name|setBaseChanged
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|panel
operator|.
name|markExternalChangesAsResolved
argument_list|()
expr_stmt|;
name|DefaultTaskExecutor
operator|.
name|runInJavaFXThread
argument_list|(
parameter_list|()
lambda|->
block|{
comment|// Reset title of tab
name|frame
operator|.
name|setTabTitle
argument_list|(
name|panel
argument_list|,
name|panel
operator|.
name|getTabTitle
argument_list|()
argument_list|,
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
operator|.
name|getDatabaseFile
argument_list|()
operator|.
name|get
argument_list|()
operator|.
name|getAbsolutePath
argument_list|()
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
literal|"Saved library"
argument_list|)
operator|+
literal|" '"
operator|+
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
operator|.
name|getDatabaseFile
argument_list|()
operator|.
name|get
argument_list|()
operator|.
name|getPath
argument_list|()
operator|+
literal|"'."
argument_list|)
expr_stmt|;
name|frame
operator|.
name|setWindowTitle
argument_list|()
expr_stmt|;
name|frame
operator|.
name|updateAllTabTitles
argument_list|()
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
block|}
return|return
name|success
return|;
block|}
catch|catch
parameter_list|(
name|SaveException
name|ex
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"A problem occurred when trying to save the file "
operator|+
name|targetPath
argument_list|,
name|ex
argument_list|)
expr_stmt|;
name|frame
operator|.
name|getDialogService
argument_list|()
operator|.
name|showErrorDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Save library"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Could not save file."
argument_list|)
argument_list|,
name|ex
argument_list|)
expr_stmt|;
return|return
literal|false
return|;
block|}
finally|finally
block|{
comment|// release panel from save status
name|panel
operator|.
name|setSaving
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|save ()
specifier|public
name|boolean
name|save
parameter_list|()
block|{
if|if
condition|(
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
operator|.
name|getDatabasePath
argument_list|()
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|panel
operator|.
name|frame
argument_list|()
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Saving library"
argument_list|)
operator|+
literal|"..."
argument_list|)
expr_stmt|;
name|panel
operator|.
name|setSaving
argument_list|(
literal|true
argument_list|)
expr_stmt|;
return|return
name|doSave
argument_list|()
return|;
block|}
else|else
block|{
name|Optional
argument_list|<
name|Path
argument_list|>
name|savePath
init|=
name|getSavePath
argument_list|()
decl_stmt|;
if|if
condition|(
name|savePath
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|saveAs
argument_list|(
name|savePath
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
return|return
literal|true
return|;
block|}
block|}
return|return
literal|false
return|;
block|}
DECL|method|saveAs ()
specifier|public
name|void
name|saveAs
parameter_list|()
block|{
name|getSavePath
argument_list|()
operator|.
name|ifPresent
argument_list|(
name|this
operator|::
name|saveAs
argument_list|)
expr_stmt|;
block|}
DECL|method|getSavePath ()
specifier|private
name|Optional
argument_list|<
name|Path
argument_list|>
name|getSavePath
parameter_list|()
block|{
name|FileDialogConfiguration
name|fileDialogConfiguration
init|=
operator|new
name|FileDialogConfiguration
operator|.
name|Builder
argument_list|()
operator|.
name|addExtensionFilter
argument_list|(
name|StandardFileType
operator|.
name|BIBTEX_DB
argument_list|)
operator|.
name|withDefaultExtension
argument_list|(
name|StandardFileType
operator|.
name|BIBTEX_DB
argument_list|)
operator|.
name|withInitialDirectory
argument_list|(
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|WORKING_DIRECTORY
argument_list|)
argument_list|)
operator|.
name|build
argument_list|()
decl_stmt|;
name|Optional
argument_list|<
name|Path
argument_list|>
name|selectedPath
init|=
name|dialogService
operator|.
name|showFileSaveDialog
argument_list|(
name|fileDialogConfiguration
argument_list|)
decl_stmt|;
name|selectedPath
operator|.
name|ifPresent
argument_list|(
name|path
lambda|->
name|prefs
operator|.
name|setWorkingDir
argument_list|(
name|path
operator|.
name|getParent
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|selectedPath
return|;
block|}
DECL|method|saveAs (Path file)
specifier|public
name|void
name|saveAs
parameter_list|(
name|Path
name|file
parameter_list|)
block|{
name|BibDatabaseContext
name|context
init|=
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
decl_stmt|;
comment|// Close AutosaveManager and BackupManager for original library
name|Optional
argument_list|<
name|Path
argument_list|>
name|databasePath
init|=
name|context
operator|.
name|getDatabasePath
argument_list|()
decl_stmt|;
if|if
condition|(
name|databasePath
operator|.
name|isPresent
argument_list|()
condition|)
block|{
specifier|final
name|Path
name|oldFile
init|=
name|databasePath
operator|.
name|get
argument_list|()
decl_stmt|;
name|context
operator|.
name|setDatabaseFile
argument_list|(
name|oldFile
operator|.
name|toFile
argument_list|()
argument_list|)
expr_stmt|;
name|AutosaveManager
operator|.
name|shutdown
argument_list|(
name|context
argument_list|)
expr_stmt|;
name|BackupManager
operator|.
name|shutdown
argument_list|(
name|context
argument_list|)
expr_stmt|;
block|}
comment|// Set new location
if|if
condition|(
name|context
operator|.
name|getLocation
argument_list|()
operator|==
name|DatabaseLocation
operator|.
name|SHARED
condition|)
block|{
comment|// Save all properties dependent on the ID. This makes it possible to restore them.
operator|new
name|SharedDatabasePreferences
argument_list|(
name|context
operator|.
name|getDatabase
argument_list|()
operator|.
name|generateSharedDatabaseID
argument_list|()
argument_list|)
operator|.
name|putAllDBMSConnectionProperties
argument_list|(
name|context
operator|.
name|getDBMSSynchronizer
argument_list|()
operator|.
name|getConnectionProperties
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|context
operator|.
name|setDatabaseFile
argument_list|(
name|file
argument_list|)
expr_stmt|;
comment|// Save
name|save
argument_list|()
expr_stmt|;
comment|// Reinstall AutosaveManager and BackupManager
name|panel
operator|.
name|resetChangeMonitor
argument_list|()
expr_stmt|;
if|if
condition|(
name|readyForAutosave
argument_list|(
name|context
argument_list|)
condition|)
block|{
name|AutosaveManager
name|autosaver
init|=
name|AutosaveManager
operator|.
name|start
argument_list|(
name|context
argument_list|)
decl_stmt|;
name|autosaver
operator|.
name|registerListener
argument_list|(
operator|new
name|AutosaveUIManager
argument_list|(
name|panel
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|readyForBackup
argument_list|(
name|context
argument_list|)
condition|)
block|{
name|BackupManager
operator|.
name|start
argument_list|(
name|context
argument_list|)
expr_stmt|;
block|}
name|context
operator|.
name|getDatabasePath
argument_list|()
operator|.
name|ifPresent
argument_list|(
name|presentFile
lambda|->
name|frame
operator|.
name|getFileHistory
argument_list|()
operator|.
name|newFile
argument_list|(
name|presentFile
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|readyForAutosave (BibDatabaseContext context)
specifier|private
name|boolean
name|readyForAutosave
parameter_list|(
name|BibDatabaseContext
name|context
parameter_list|)
block|{
return|return
operator|(
operator|(
name|context
operator|.
name|getLocation
argument_list|()
operator|==
name|DatabaseLocation
operator|.
name|SHARED
operator|)
operator|||
operator|(
operator|(
name|context
operator|.
name|getLocation
argument_list|()
operator|==
name|DatabaseLocation
operator|.
name|LOCAL
operator|)
operator|&&
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|LOCAL_AUTO_SAVE
argument_list|)
operator|)
operator|)
operator|&&
name|context
operator|.
name|getDatabasePath
argument_list|()
operator|.
name|isPresent
argument_list|()
return|;
block|}
DECL|method|readyForBackup (BibDatabaseContext context)
specifier|private
name|boolean
name|readyForBackup
parameter_list|(
name|BibDatabaseContext
name|context
parameter_list|)
block|{
return|return
operator|(
name|context
operator|.
name|getLocation
argument_list|()
operator|==
name|DatabaseLocation
operator|.
name|LOCAL
operator|)
operator|&&
name|context
operator|.
name|getDatabasePath
argument_list|()
operator|.
name|isPresent
argument_list|()
return|;
block|}
DECL|method|saveSelectedAsPlain ()
specifier|public
name|void
name|saveSelectedAsPlain
parameter_list|()
block|{
name|getSavePath
argument_list|()
operator|.
name|ifPresent
argument_list|(
name|path
lambda|->
block|{
try|try
block|{
name|saveDatabase
argument_list|(
name|path
argument_list|,
literal|true
argument_list|,
name|prefs
operator|.
name|getDefaultEncoding
argument_list|()
argument_list|,
name|SavePreferences
operator|.
name|DatabaseSaveType
operator|.
name|PLAIN_BIBTEX
argument_list|)
expr_stmt|;
name|frame
operator|.
name|getFileHistory
argument_list|()
operator|.
name|newFile
argument_list|(
name|path
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
literal|"Saved selected to '%0'."
argument_list|,
name|path
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|SaveException
name|ex
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"A problem occurred when trying to save the file"
argument_list|,
name|ex
argument_list|)
expr_stmt|;
name|frame
operator|.
name|getDialogService
argument_list|()
operator|.
name|showErrorDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Save library"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Could not save file."
argument_list|)
argument_list|,
name|ex
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

