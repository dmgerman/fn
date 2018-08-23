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
name|File
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
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|Alert
operator|.
name|AlertType
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
operator|.
name|ButtonData
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
name|control
operator|.
name|TextArea
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
name|Globals
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|JabRefExecutorService
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
name|SidePaneType
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
name|actions
operator|.
name|BaseAction
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
name|collab
operator|.
name|ChangeScanner
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
name|FileSaveSession
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
name|exporter
operator|.
name|SaveSession
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
name|logic
operator|.
name|util
operator|.
name|io
operator|.
name|FileBasedLock
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
comment|/**  * Action for the "Save" and "Save as" operations called from BasePanel. This class is also used for  * save operations when closing a database or quitting the applications.  *  * The save operation is loaded off of the GUI thread using {@link BackgroundTask}.  * Callers can query whether the operation was canceled, or whether it was successful.  */
end_comment

begin_class
DECL|class|SaveDatabaseAction
specifier|public
class|class
name|SaveDatabaseAction
implements|implements
name|BaseAction
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
DECL|field|success
specifier|private
name|boolean
name|success
decl_stmt|;
DECL|field|canceled
specifier|private
name|boolean
name|canceled
decl_stmt|;
DECL|field|fileLockedError
specifier|private
name|boolean
name|fileLockedError
decl_stmt|;
DECL|field|filePath
specifier|private
name|Optional
argument_list|<
name|Path
argument_list|>
name|filePath
decl_stmt|;
DECL|method|SaveDatabaseAction (BasePanel panel)
specifier|public
name|SaveDatabaseAction
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
name|filePath
operator|=
name|Optional
operator|.
name|empty
argument_list|()
expr_stmt|;
block|}
comment|/**      * @param panel BasePanel which contains the database to be saved      * @param filePath Path to the file the database should be saved to      */
DECL|method|SaveDatabaseAction (BasePanel panel, Path filePath)
specifier|public
name|SaveDatabaseAction
parameter_list|(
name|BasePanel
name|panel
parameter_list|,
name|Path
name|filePath
parameter_list|)
block|{
name|this
argument_list|(
name|panel
argument_list|)
expr_stmt|;
name|this
operator|.
name|filePath
operator|=
name|Optional
operator|.
name|ofNullable
argument_list|(
name|filePath
argument_list|)
expr_stmt|;
block|}
DECL|method|init ()
specifier|public
name|void
name|init
parameter_list|()
throws|throws
name|Exception
block|{
name|success
operator|=
literal|false
expr_stmt|;
name|canceled
operator|=
literal|false
expr_stmt|;
name|fileLockedError
operator|=
literal|false
expr_stmt|;
if|if
condition|(
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
operator|.
name|getDatabaseFile
argument_list|()
operator|.
name|isPresent
argument_list|()
condition|)
block|{
comment|// Check for external modifications: if true, save not performed so do not tell the user a save is underway but return instead.
if|if
condition|(
name|checkExternalModification
argument_list|()
condition|)
block|{
return|return;
block|}
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
block|}
elseif|else
if|if
condition|(
name|filePath
operator|.
name|isPresent
argument_list|()
condition|)
block|{
comment|// save as directly if the target file location is known
name|saveAs
argument_list|(
name|filePath
operator|.
name|get
argument_list|()
operator|.
name|toFile
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|saveAs
argument_list|()
expr_stmt|;
block|}
block|}
DECL|method|doSave ()
specifier|private
name|void
name|doSave
parameter_list|()
block|{
if|if
condition|(
name|canceled
operator|||
operator|!
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
operator|.
name|getDatabaseFile
argument_list|()
operator|.
name|isPresent
argument_list|()
condition|)
block|{
return|return;
block|}
try|try
block|{
comment|// If set in preferences, generate missing BibTeX keys
name|panel
operator|.
name|autoGenerateKeysBeforeSaving
argument_list|()
expr_stmt|;
if|if
condition|(
name|FileBasedLock
operator|.
name|waitForFileLock
argument_list|(
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
name|toPath
argument_list|()
argument_list|)
condition|)
block|{
comment|// Check for external modifications to alleviate multiuser concurrency issue when near
comment|// simultaneous saves occur to a shared database file: if true, do not perform the save
comment|// rather return instead.
if|if
condition|(
name|checkExternalModification
argument_list|()
condition|)
block|{
return|return;
block|}
comment|// Save the database
name|success
operator|=
name|saveDatabase
argument_list|(
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
name|Globals
operator|.
name|prefs
operator|.
name|getDefaultEncoding
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|panel
operator|.
name|updateTimeStamp
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|success
operator|=
literal|false
expr_stmt|;
name|fileLockedError
operator|=
literal|true
expr_stmt|;
block|}
comment|// release panel from save status
name|panel
operator|.
name|setSaving
argument_list|(
literal|false
argument_list|)
expr_stmt|;
if|if
condition|(
name|success
condition|)
block|{
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
block|}
block|}
catch|catch
parameter_list|(
name|SaveException
name|ex
parameter_list|)
block|{
if|if
condition|(
name|ex
operator|==
name|SaveException
operator|.
name|FILE_LOCKED
condition|)
block|{
name|success
operator|=
literal|false
expr_stmt|;
name|fileLockedError
operator|=
literal|true
expr_stmt|;
return|return;
block|}
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Problem saving file"
argument_list|,
name|ex
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|success
condition|)
block|{
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
elseif|else
if|if
condition|(
operator|!
name|canceled
condition|)
block|{
if|if
condition|(
name|fileLockedError
condition|)
block|{
comment|// TODO: user should have the option to override the lock file.
name|frame
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Could not save, file locked by another JabRef instance."
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|frame
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Save failed"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
DECL|method|saveDatabase (File file, boolean selectedOnly, Charset encoding)
specifier|private
name|boolean
name|saveDatabase
parameter_list|(
name|File
name|file
parameter_list|,
name|boolean
name|selectedOnly
parameter_list|,
name|Charset
name|encoding
parameter_list|)
throws|throws
name|SaveException
block|{
name|SaveSession
name|session
decl_stmt|;
try|try
block|{
name|SavePreferences
name|prefs
init|=
name|Globals
operator|.
name|prefs
operator|.
name|loadForSaveFromPreferences
argument_list|()
operator|.
name|withEncoding
argument_list|(
name|encoding
argument_list|)
decl_stmt|;
name|BibtexDatabaseWriter
argument_list|<
name|SaveSession
argument_list|>
name|databaseWriter
init|=
operator|new
name|BibtexDatabaseWriter
argument_list|<>
argument_list|(
name|FileSaveSession
operator|::
operator|new
argument_list|)
decl_stmt|;
if|if
condition|(
name|selectedOnly
condition|)
block|{
name|session
operator|=
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
argument_list|,
name|prefs
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|session
operator|=
name|databaseWriter
operator|.
name|saveDatabase
argument_list|(
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
argument_list|,
name|prefs
argument_list|)
expr_stmt|;
block|}
name|panel
operator|.
name|registerUndoableChanges
argument_list|(
name|session
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|UnsupportedCharsetException
name|ex
parameter_list|)
block|{
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
operator|+
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
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|SaveException
argument_list|(
name|ex
operator|.
name|getMessage
argument_list|()
argument_list|,
name|ex
argument_list|)
throw|;
block|}
catch|catch
parameter_list|(
name|SaveException
name|ex
parameter_list|)
block|{
if|if
condition|(
name|ex
operator|==
name|SaveException
operator|.
name|FILE_LOCKED
condition|)
block|{
throw|throw
name|ex
throw|;
block|}
if|if
condition|(
name|ex
operator|.
name|specificEntry
argument_list|()
condition|)
block|{
name|BibEntry
name|entry
init|=
name|ex
operator|.
name|getEntry
argument_list|()
decl_stmt|;
comment|// Error occured during processing of an entry. Highlight it!
name|panel
operator|.
name|clearAndSelect
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"A problem occured when trying to save the file"
argument_list|,
name|ex
argument_list|)
expr_stmt|;
block|}
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
throw|throw
name|ex
throw|;
block|}
comment|// handle encoding problems
name|boolean
name|success
init|=
literal|true
decl_stmt|;
if|if
condition|(
operator|!
name|session
operator|.
name|getWriter
argument_list|()
operator|.
name|couldEncodeAll
argument_list|()
condition|)
block|{
name|DialogPane
name|pane
init|=
operator|new
name|DialogPane
argument_list|()
decl_stmt|;
name|TextArea
name|area
init|=
operator|new
name|TextArea
argument_list|(
name|session
operator|.
name|getWriter
argument_list|()
operator|.
name|getProblemCharacters
argument_list|()
argument_list|)
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
name|session
operator|.
name|getEncoding
argument_list|()
operator|.
name|displayName
argument_list|()
argument_list|)
argument_list|)
argument_list|,
name|area
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
name|tryDiff
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
name|ButtonData
operator|.
name|OTHER
argument_list|)
decl_stmt|;
name|ButtonType
name|save
init|=
operator|new
name|ButtonType
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Save"
argument_list|)
argument_list|,
name|ButtonData
operator|.
name|APPLY
argument_list|)
decl_stmt|;
name|Optional
argument_list|<
name|ButtonType
argument_list|>
name|clickedBtn
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
name|save
argument_list|,
name|tryDiff
argument_list|,
name|ButtonType
operator|.
name|CANCEL
argument_list|)
decl_stmt|;
if|if
condition|(
name|clickedBtn
operator|.
name|isPresent
argument_list|()
operator|&&
name|clickedBtn
operator|.
name|get
argument_list|()
operator|.
name|equals
argument_list|(
name|tryDiff
argument_list|)
condition|)
block|{
name|Optional
argument_list|<
name|Charset
argument_list|>
name|selectedCharSet
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
literal|"Select encoding"
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
name|selectedCharSet
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|Charset
name|newEncoding
init|=
name|selectedCharSet
operator|.
name|get
argument_list|()
decl_stmt|;
return|return
name|saveDatabase
argument_list|(
name|file
argument_list|,
name|selectedOnly
argument_list|,
name|newEncoding
argument_list|)
return|;
block|}
else|else
block|{
name|success
operator|=
literal|false
expr_stmt|;
block|}
block|}
block|}
comment|// backup file?
try|try
block|{
if|if
condition|(
name|success
condition|)
block|{
name|session
operator|.
name|commit
argument_list|(
name|file
operator|.
name|toPath
argument_list|()
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
name|encoding
argument_list|,
name|ChangePropagation
operator|.
name|DO_NOT_POST_EVENT
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|session
operator|.
name|cancel
argument_list|()
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|SaveException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|debug
argument_list|(
literal|"Problems saving during backup creationg"
argument_list|,
name|e
argument_list|)
expr_stmt|;
name|boolean
name|saveWithoutBackupClicked
init|=
name|frame
operator|.
name|getDialogService
argument_list|()
operator|.
name|showConfirmationDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Unable to create backup"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Save failed during backup creation"
argument_list|)
operator|+
literal|". "
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"Save without backup?"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Save without backup"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Cancel"
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|saveWithoutBackupClicked
condition|)
block|{
name|session
operator|.
name|setUseBackup
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|session
operator|.
name|commit
argument_list|(
name|file
operator|.
name|toPath
argument_list|()
argument_list|)
expr_stmt|;
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
name|encoding
argument_list|,
name|ChangePropagation
operator|.
name|DO_NOT_POST_EVENT
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|success
operator|=
literal|false
expr_stmt|;
block|}
block|}
return|return
name|success
return|;
block|}
comment|/**      * Run the "Save" operation. This method offloads the actual save operation to a background thread.      */
DECL|method|runCommand ()
specifier|public
name|void
name|runCommand
parameter_list|()
throws|throws
name|Exception
block|{
name|action
argument_list|()
expr_stmt|;
block|}
DECL|method|save ()
specifier|public
name|void
name|save
parameter_list|()
throws|throws
name|Exception
block|{
name|runCommand
argument_list|()
expr_stmt|;
block|}
DECL|method|saveAs ()
specifier|public
name|void
name|saveAs
parameter_list|()
throws|throws
name|Exception
block|{
comment|// configure file dialog
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
name|Globals
operator|.
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
name|DialogService
name|dialogService
init|=
name|frame
operator|.
name|getDialogService
argument_list|()
decl_stmt|;
name|Optional
argument_list|<
name|Path
argument_list|>
name|path
init|=
name|dialogService
operator|.
name|showFileSaveDialog
argument_list|(
name|fileDialogConfiguration
argument_list|)
decl_stmt|;
if|if
condition|(
name|path
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|saveAs
argument_list|(
name|path
operator|.
name|get
argument_list|()
operator|.
name|toFile
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|canceled
operator|=
literal|true
expr_stmt|;
return|return;
block|}
block|}
comment|/**      * Run the "Save as" operation. This method offloads the actual save operation to a background thread.      */
DECL|method|saveAs (File file)
specifier|public
name|void
name|saveAs
parameter_list|(
name|File
name|file
parameter_list|)
throws|throws
name|Exception
block|{
name|BibDatabaseContext
name|context
init|=
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
decl_stmt|;
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
if|if
condition|(
name|file
operator|.
name|getParent
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|Globals
operator|.
name|prefs
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|WORKING_DIRECTORY
argument_list|,
name|file
operator|.
name|getParent
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|runCommand
argument_list|()
expr_stmt|;
comment|// If the operation failed, revert the file field and return:
if|if
condition|(
operator|!
name|success
condition|)
block|{
return|return;
block|}
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
comment|//closing AutosaveManager and BackupManager for original library
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
else|else
block|{
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Old file not found, just creating a new file"
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
name|getDatabaseFile
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
operator|.
name|getPath
argument_list|()
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
name|Globals
operator|.
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
name|getDatabaseFile
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
name|getDatabaseFile
argument_list|()
operator|.
name|isPresent
argument_list|()
return|;
block|}
comment|/**      * Query whether the last operation was successful.      *      * @return true if the last Save/SaveAs operation completed successfully, false otherwise.      */
DECL|method|isSuccess ()
specifier|public
name|boolean
name|isSuccess
parameter_list|()
block|{
return|return
name|success
return|;
block|}
comment|/**      * Query whether the last operation was canceled.      *      * @return true if the last Save/SaveAs operation was canceled from the file dialog or from another      * query dialog, false otherwise.      */
DECL|method|isCanceled ()
specifier|public
name|boolean
name|isCanceled
parameter_list|()
block|{
return|return
name|canceled
return|;
block|}
comment|/**      * Check whether or not the external database has been modified. If so need to alert the user to accept external updates prior to      * saving the database. This is necessary to avoid overwriting other users work when using a multiuser database file.      *      * @return true if the external database file has been modified and the user must choose to accept the changes and false if no modifications      * were found or there is no requested protection for the database file.      */
DECL|method|checkExternalModification ()
specifier|private
name|boolean
name|checkExternalModification
parameter_list|()
block|{
comment|// Check for external modifications:
if|if
condition|(
name|panel
operator|.
name|isUpdatedExternally
argument_list|()
condition|)
block|{
name|ButtonType
name|save
init|=
operator|new
name|ButtonType
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Save"
argument_list|)
argument_list|)
decl_stmt|;
name|ButtonType
name|reviewChanges
init|=
operator|new
name|ButtonType
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Review changes"
argument_list|)
argument_list|)
decl_stmt|;
name|Optional
argument_list|<
name|ButtonType
argument_list|>
name|buttonPressed
init|=
name|DefaultTaskExecutor
operator|.
name|runInJavaFXThread
argument_list|(
parameter_list|()
lambda|->
name|frame
operator|.
name|getDialogService
argument_list|()
operator|.
name|showCustomButtonDialogAndWait
argument_list|(
name|AlertType
operator|.
name|CONFIRMATION
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"File updated externally"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"File has been updated externally. "
operator|+
literal|"What do you want to do?"
argument_list|)
argument_list|,
name|reviewChanges
argument_list|,
name|save
argument_list|,
name|ButtonType
operator|.
name|CANCEL
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|buttonPressed
operator|.
name|isPresent
argument_list|()
condition|)
block|{
if|if
condition|(
name|buttonPressed
operator|.
name|get
argument_list|()
operator|==
name|ButtonType
operator|.
name|CANCEL
condition|)
block|{
name|canceled
operator|=
literal|true
expr_stmt|;
return|return
literal|true
return|;
block|}
if|if
condition|(
name|buttonPressed
operator|.
name|get
argument_list|()
operator|.
name|equals
argument_list|(
name|reviewChanges
argument_list|)
condition|)
block|{
name|canceled
operator|=
literal|true
expr_stmt|;
name|JabRefExecutorService
operator|.
name|INSTANCE
operator|.
name|execute
argument_list|(
parameter_list|()
lambda|->
block|{
if|if
condition|(
operator|!
name|FileBasedLock
operator|.
name|waitForFileLock
argument_list|(
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
argument_list|)
condition|)
block|{
comment|// TODO: GUI handling of the situation when the externally modified file keeps being locked.
name|LOGGER
operator|.
name|error
argument_list|(
literal|"File locked, this will be trouble."
argument_list|)
expr_stmt|;
block|}
name|ChangeScanner
name|scanner
init|=
operator|new
name|ChangeScanner
argument_list|(
name|panel
operator|.
name|frame
argument_list|()
argument_list|,
name|panel
argument_list|,
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
operator|.
name|getDatabasePath
argument_list|()
operator|.
name|orElse
argument_list|(
literal|null
argument_list|)
argument_list|,
name|panel
operator|.
name|getTempFile
argument_list|()
argument_list|)
decl_stmt|;
name|JabRefExecutorService
operator|.
name|INSTANCE
operator|.
name|executeInterruptableTaskAndWait
argument_list|(
name|scanner
argument_list|)
expr_stmt|;
if|if
condition|(
name|scanner
operator|.
name|changesFound
argument_list|()
condition|)
block|{
name|scanner
operator|.
name|displayResult
argument_list|(
name|resolved
lambda|->
block|{
if|if
condition|(
name|resolved
condition|)
block|{
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
name|panel
operator|.
name|getSidePaneManager
argument_list|()
operator|.
name|hide
argument_list|(
name|SidePaneType
operator|.
name|FILE_UPDATE_NOTIFICATION
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|canceled
operator|=
literal|true
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
return|return
literal|true
return|;
block|}
else|else
block|{
comment|// User indicated to store anyway.
if|if
condition|(
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
operator|.
name|getMetaData
argument_list|()
operator|.
name|isProtected
argument_list|()
condition|)
block|{
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
literal|"Protected library"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Library is protected. Cannot save until external changes have been reviewed."
argument_list|)
argument_list|)
expr_stmt|;
name|canceled
operator|=
literal|true
expr_stmt|;
block|}
else|else
block|{
name|panel
operator|.
name|markExternalChangesAsResolved
argument_list|()
expr_stmt|;
name|panel
operator|.
name|getSidePaneManager
argument_list|()
operator|.
name|hide
argument_list|(
name|SidePaneType
operator|.
name|FILE_UPDATE_NOTIFICATION
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
comment|// Return false as either no external database file modifications have been found or overwrite is requested any way
return|return
literal|false
return|;
block|}
annotation|@
name|Override
DECL|method|action ()
specifier|public
name|void
name|action
parameter_list|()
throws|throws
name|Exception
block|{
name|init
argument_list|()
expr_stmt|;
name|BackgroundTask
operator|.
name|wrap
argument_list|(
name|this
operator|::
name|doSave
argument_list|)
operator|.
name|executeWith
argument_list|(
name|Globals
operator|.
name|TASK_EXECUTOR
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

