begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.importer.actions
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|importer
operator|.
name|actions
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
name|nio
operator|.
name|file
operator|.
name|Paths
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
name|attribute
operator|.
name|FileTime
import|;
end_import

begin_import
import|import
name|java
operator|.
name|sql
operator|.
name|SQLException
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
name|Collections
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
name|java
operator|.
name|util
operator|.
name|Objects
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
name|stream
operator|.
name|Collectors
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
name|JOptionPane
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
name|FXDialogService
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
name|IconTheme
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
name|actions
operator|.
name|MnemonicAwareAction
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
name|autosaveandbackup
operator|.
name|BackupUIManager
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
name|importer
operator|.
name|ParserResultWarningDialog
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
name|keyboard
operator|.
name|KeyBinding
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
name|shared
operator|.
name|SharedDatabaseUIManager
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
name|importer
operator|.
name|OpenDatabase
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
name|importer
operator|.
name|ParserResult
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
name|exception
operator|.
name|InvalidDBMSConnectionPropertiesException
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
name|exception
operator|.
name|NotASharedDatabaseException
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
name|FileType
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
name|migrations
operator|.
name|FileLinksUpgradeWarning
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
name|BibDatabase
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
name|DatabaseNotSupportedException
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
name|strings
operator|.
name|StringUtil
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
comment|// The action concerned with opening an existing database.
end_comment

begin_class
DECL|class|OpenDatabaseAction
specifier|public
class|class
name|OpenDatabaseAction
extends|extends
name|MnemonicAwareAction
block|{
DECL|field|LOGGER
specifier|public
specifier|static
specifier|final
name|Logger
name|LOGGER
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|OpenDatabaseAction
operator|.
name|class
argument_list|)
decl_stmt|;
comment|// List of actions that may need to be called after opening the file. Such as
comment|// upgrade actions etc. that may depend on the JabRef version that wrote the file:
DECL|field|POST_OPEN_ACTIONS
specifier|private
specifier|static
specifier|final
name|List
argument_list|<
name|GUIPostOpenAction
argument_list|>
name|POST_OPEN_ACTIONS
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
static|static
block|{
comment|// Add the action for checking for new custom entry types loaded from the BIB file:
name|POST_OPEN_ACTIONS
operator|.
name|add
argument_list|(
operator|new
name|CheckForNewEntryTypesAction
argument_list|()
argument_list|)
expr_stmt|;
comment|// Add the action for the new external file handling system in version 2.3:
name|POST_OPEN_ACTIONS
operator|.
name|add
argument_list|(
operator|new
name|FileLinksUpgradeWarning
argument_list|()
argument_list|)
expr_stmt|;
comment|// Add the action for warning about and handling duplicate BibTeX keys:
name|POST_OPEN_ACTIONS
operator|.
name|add
argument_list|(
operator|new
name|HandleDuplicateWarnings
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|field|showDialog
specifier|private
specifier|final
name|boolean
name|showDialog
decl_stmt|;
DECL|field|frame
specifier|private
specifier|final
name|JabRefFrame
name|frame
decl_stmt|;
DECL|method|OpenDatabaseAction (JabRefFrame frame, boolean showDialog)
specifier|public
name|OpenDatabaseAction
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|boolean
name|showDialog
parameter_list|)
block|{
name|super
argument_list|(
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|OPEN
operator|.
name|getIcon
argument_list|()
argument_list|)
expr_stmt|;
name|this
operator|.
name|frame
operator|=
name|frame
expr_stmt|;
name|this
operator|.
name|showDialog
operator|=
name|showDialog
expr_stmt|;
name|putValue
argument_list|(
name|Action
operator|.
name|NAME
argument_list|,
name|Localization
operator|.
name|menuTitle
argument_list|(
literal|"Open library"
argument_list|)
argument_list|)
expr_stmt|;
name|putValue
argument_list|(
name|Action
operator|.
name|ACCELERATOR_KEY
argument_list|,
name|Globals
operator|.
name|getKeyPrefs
argument_list|()
operator|.
name|getKey
argument_list|(
name|KeyBinding
operator|.
name|OPEN_DATABASE
argument_list|)
argument_list|)
expr_stmt|;
name|putValue
argument_list|(
name|Action
operator|.
name|SHORT_DESCRIPTION
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Open BibTeX library"
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * Go through the list of post open actions, and perform those that need to be performed.      *      * @param panel  The BasePanel where the database is shown.      * @param result The result of the BIB file parse operation.      */
DECL|method|performPostOpenActions (BasePanel panel, ParserResult result)
specifier|public
specifier|static
name|void
name|performPostOpenActions
parameter_list|(
name|BasePanel
name|panel
parameter_list|,
name|ParserResult
name|result
parameter_list|)
block|{
for|for
control|(
name|GUIPostOpenAction
name|action
range|:
name|OpenDatabaseAction
operator|.
name|POST_OPEN_ACTIONS
control|)
block|{
if|if
condition|(
name|action
operator|.
name|isActionNecessary
argument_list|(
name|result
argument_list|)
condition|)
block|{
name|action
operator|.
name|performAction
argument_list|(
name|panel
argument_list|,
name|result
argument_list|)
expr_stmt|;
name|panel
operator|.
name|frame
argument_list|()
operator|.
name|getTabbedPane
argument_list|()
operator|.
name|setSelectedComponent
argument_list|(
name|panel
argument_list|)
expr_stmt|;
block|}
block|}
block|}
annotation|@
name|Override
DECL|method|actionPerformed (ActionEvent e)
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|List
argument_list|<
name|Path
argument_list|>
name|filesToOpen
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
if|if
condition|(
name|showDialog
condition|)
block|{
name|DialogService
name|ds
init|=
operator|new
name|FXDialogService
argument_list|()
decl_stmt|;
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
name|FileType
operator|.
name|BIBTEX_DB
argument_list|)
operator|.
name|withDefaultExtension
argument_list|(
name|FileType
operator|.
name|BIBTEX_DB
argument_list|)
operator|.
name|withInitialDirectory
argument_list|(
name|Paths
operator|.
name|get
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
argument_list|)
operator|.
name|build
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|Path
argument_list|>
name|chosenFiles
init|=
name|DefaultTaskExecutor
operator|.
name|runInJavaFXThread
argument_list|(
parameter_list|()
lambda|->
name|ds
operator|.
name|showFileOpenDialogAndGetMultipleFiles
argument_list|(
name|fileDialogConfiguration
argument_list|)
argument_list|)
decl_stmt|;
name|filesToOpen
operator|.
name|addAll
argument_list|(
name|chosenFiles
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|LOGGER
operator|.
name|info
argument_list|(
name|Action
operator|.
name|NAME
operator|+
literal|" "
operator|+
name|e
operator|.
name|getActionCommand
argument_list|()
argument_list|)
expr_stmt|;
name|filesToOpen
operator|.
name|add
argument_list|(
name|Paths
operator|.
name|get
argument_list|(
name|StringUtil
operator|.
name|getCorrectFileName
argument_list|(
name|e
operator|.
name|getActionCommand
argument_list|()
argument_list|,
literal|"bib"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|openFiles
argument_list|(
name|filesToOpen
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
comment|/**      * Opens the given file. If null or 404, nothing happens      *      * @param file the file, may be null or not existing      */
DECL|method|openFile (Path file, boolean raisePanel)
specifier|public
name|void
name|openFile
parameter_list|(
name|Path
name|file
parameter_list|,
name|boolean
name|raisePanel
parameter_list|)
block|{
name|List
argument_list|<
name|Path
argument_list|>
name|filesToOpen
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|filesToOpen
operator|.
name|add
argument_list|(
name|file
argument_list|)
expr_stmt|;
name|openFiles
argument_list|(
name|filesToOpen
argument_list|,
name|raisePanel
argument_list|)
expr_stmt|;
block|}
DECL|method|openFilesAsStringList (List<String> fileNamesToOpen, boolean raisePanel)
specifier|public
name|void
name|openFilesAsStringList
parameter_list|(
name|List
argument_list|<
name|String
argument_list|>
name|fileNamesToOpen
parameter_list|,
name|boolean
name|raisePanel
parameter_list|)
block|{
name|List
argument_list|<
name|Path
argument_list|>
name|filesToOpen
init|=
name|fileNamesToOpen
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|Paths
operator|::
name|get
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
argument_list|)
decl_stmt|;
name|openFiles
argument_list|(
name|filesToOpen
argument_list|,
name|raisePanel
argument_list|)
expr_stmt|;
block|}
comment|/**      * Opens the given files. If one of it is null or 404, nothing happens      *      * @param filesToOpen the filesToOpen, may be null or not existing      */
DECL|method|openFiles (List<Path> filesToOpen, boolean raisePanel)
specifier|public
name|void
name|openFiles
parameter_list|(
name|List
argument_list|<
name|Path
argument_list|>
name|filesToOpen
parameter_list|,
name|boolean
name|raisePanel
parameter_list|)
block|{
name|BasePanel
name|toRaise
init|=
literal|null
decl_stmt|;
name|int
name|initialCount
init|=
name|filesToOpen
operator|.
name|size
argument_list|()
decl_stmt|;
name|int
name|removed
init|=
literal|0
decl_stmt|;
comment|// Check if any of the files are already open:
for|for
control|(
name|Iterator
argument_list|<
name|Path
argument_list|>
name|iterator
init|=
name|filesToOpen
operator|.
name|iterator
argument_list|()
init|;
name|iterator
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Path
name|file
init|=
name|iterator
operator|.
name|next
argument_list|()
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
name|frame
operator|.
name|getTabbedPane
argument_list|()
operator|.
name|getTabCount
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|BasePanel
name|basePanel
init|=
name|frame
operator|.
name|getBasePanelAt
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|basePanel
operator|.
name|getBibDatabaseContext
argument_list|()
operator|.
name|getDatabasePath
argument_list|()
operator|.
name|isPresent
argument_list|()
operator|)
operator|&&
name|basePanel
operator|.
name|getBibDatabaseContext
argument_list|()
operator|.
name|getDatabasePath
argument_list|()
operator|.
name|get
argument_list|()
operator|.
name|equals
argument_list|(
name|file
argument_list|)
condition|)
block|{
name|iterator
operator|.
name|remove
argument_list|()
expr_stmt|;
name|removed
operator|++
expr_stmt|;
comment|// See if we removed the final one. If so, we must perhaps
comment|// raise the BasePanel in question:
if|if
condition|(
name|removed
operator|==
name|initialCount
condition|)
block|{
name|toRaise
operator|=
name|basePanel
expr_stmt|;
block|}
comment|// no more bps to check, we found a matching one
break|break;
block|}
block|}
block|}
comment|// Run the actual open in a thread to prevent the program
comment|// locking until the file is loaded.
if|if
condition|(
operator|!
name|filesToOpen
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
specifier|final
name|List
argument_list|<
name|Path
argument_list|>
name|theFiles
init|=
name|Collections
operator|.
name|unmodifiableList
argument_list|(
name|filesToOpen
argument_list|)
decl_stmt|;
name|JabRefExecutorService
operator|.
name|INSTANCE
operator|.
name|execute
argument_list|(
parameter_list|()
lambda|->
block|{
for|for
control|(
name|Path
name|theFile
range|:
name|theFiles
control|)
block|{
name|openTheFile
argument_list|(
name|theFile
argument_list|,
name|raisePanel
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
for|for
control|(
name|Path
name|theFile
range|:
name|theFiles
control|)
block|{
name|frame
operator|.
name|getFileHistory
argument_list|()
operator|.
name|newFile
argument_list|(
name|theFile
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
comment|// If no files are remaining to open, this could mean that a file was
comment|// already open. If so, we may have to raise the correct tab:
elseif|else
if|if
condition|(
name|toRaise
operator|!=
literal|null
condition|)
block|{
name|frame
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"File '%0' is already open."
argument_list|,
name|toRaise
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
argument_list|)
argument_list|)
expr_stmt|;
name|frame
operator|.
name|getTabbedPane
argument_list|()
operator|.
name|setSelectedComponent
argument_list|(
name|toRaise
argument_list|)
expr_stmt|;
block|}
name|frame
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Files opened"
argument_list|)
operator|+
literal|": "
operator|+
operator|(
name|filesToOpen
operator|.
name|size
argument_list|()
operator|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * @param file the file, may be null or not existing      */
DECL|method|openTheFile (Path file, boolean raisePanel)
specifier|private
name|void
name|openTheFile
parameter_list|(
name|Path
name|file
parameter_list|,
name|boolean
name|raisePanel
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|file
argument_list|)
expr_stmt|;
if|if
condition|(
name|Files
operator|.
name|exists
argument_list|(
name|file
argument_list|)
condition|)
block|{
name|Path
name|fileToLoad
init|=
name|file
operator|.
name|toAbsolutePath
argument_list|()
decl_stmt|;
name|frame
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Opening"
argument_list|)
operator|+
literal|": '"
operator|+
name|file
operator|+
literal|"'"
argument_list|)
expr_stmt|;
name|String
name|fileName
init|=
name|file
operator|.
name|getFileName
argument_list|()
operator|.
name|toString
argument_list|()
decl_stmt|;
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
name|fileToLoad
operator|.
name|getParent
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|FileBasedLock
operator|.
name|hasLockFile
argument_list|(
name|file
argument_list|)
condition|)
block|{
name|Optional
argument_list|<
name|FileTime
argument_list|>
name|modificationTime
init|=
name|FileBasedLock
operator|.
name|getLockFileTimeStamp
argument_list|(
name|file
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|modificationTime
operator|.
name|isPresent
argument_list|()
operator|)
operator|&&
operator|(
operator|(
name|System
operator|.
name|currentTimeMillis
argument_list|()
operator|-
name|modificationTime
operator|.
name|get
argument_list|()
operator|.
name|toMillis
argument_list|()
operator|)
operator|>
name|FileBasedLock
operator|.
name|LOCKFILE_CRITICAL_AGE
operator|)
condition|)
block|{
comment|// The lock file is fairly old, so we can offer to "steal" the file:
name|int
name|answer
init|=
name|JOptionPane
operator|.
name|showConfirmDialog
argument_list|(
literal|null
argument_list|,
literal|"<html>"
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"Error opening file"
argument_list|)
operator|+
literal|" '"
operator|+
name|fileName
operator|+
literal|"'. "
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"File is locked by another JabRef instance."
argument_list|)
operator|+
literal|"<p>"
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"Do you want to override the file lock?"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"File locked"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|YES_NO_OPTION
argument_list|)
decl_stmt|;
if|if
condition|(
name|answer
operator|==
name|JOptionPane
operator|.
name|YES_OPTION
condition|)
block|{
name|FileBasedLock
operator|.
name|deleteLockFile
argument_list|(
name|file
argument_list|)
expr_stmt|;
block|}
else|else
block|{
return|return;
block|}
block|}
elseif|else
if|if
condition|(
operator|!
name|FileBasedLock
operator|.
name|waitForFileLock
argument_list|(
name|file
argument_list|)
condition|)
block|{
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
literal|null
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Error opening file"
argument_list|)
operator|+
literal|" '"
operator|+
name|fileName
operator|+
literal|"'. "
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"File is locked by another JabRef instance."
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Error"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
return|return;
block|}
block|}
if|if
condition|(
name|BackupManager
operator|.
name|checkForBackupFile
argument_list|(
name|fileToLoad
argument_list|)
condition|)
block|{
name|BackupUIManager
operator|.
name|showRestoreBackupDialog
argument_list|(
name|frame
argument_list|,
name|fileToLoad
argument_list|)
expr_stmt|;
block|}
name|ParserResult
name|result
decl_stmt|;
name|result
operator|=
name|OpenDatabase
operator|.
name|loadDatabase
argument_list|(
name|fileToLoad
operator|.
name|toString
argument_list|()
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getImportFormatPreferences
argument_list|()
argument_list|,
name|Globals
operator|.
name|getFileUpdateMonitor
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|result
operator|.
name|getDatabase
argument_list|()
operator|.
name|isShared
argument_list|()
condition|)
block|{
try|try
block|{
operator|new
name|SharedDatabaseUIManager
argument_list|(
name|frame
argument_list|)
operator|.
name|openSharedDatabaseFromParserResult
argument_list|(
name|result
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|SQLException
decl||
name|DatabaseNotSupportedException
decl||
name|InvalidDBMSConnectionPropertiesException
decl||
name|NotASharedDatabaseException
name|e
parameter_list|)
block|{
name|result
operator|.
name|getDatabaseContext
argument_list|()
operator|.
name|clearDatabaseFile
argument_list|()
expr_stmt|;
comment|// do not open the original file
name|result
operator|.
name|getDatabase
argument_list|()
operator|.
name|clearSharedDatabaseID
argument_list|()
expr_stmt|;
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Connection error"
argument_list|,
name|e
argument_list|)
expr_stmt|;
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|frame
argument_list|,
name|e
operator|.
name|getMessage
argument_list|()
operator|+
literal|"\n\n"
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"A local copy will be opened."
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Connection error"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|WARNING_MESSAGE
argument_list|)
expr_stmt|;
block|}
block|}
name|BasePanel
name|panel
init|=
name|addNewDatabase
argument_list|(
name|result
argument_list|,
name|file
argument_list|,
name|raisePanel
argument_list|)
decl_stmt|;
comment|// After adding the database, go through our list and see if
comment|// any post open actions need to be done. For instance, checking
comment|// if we found new entry types that can be imported, or checking
comment|// if the database contents should be modified due to new features
comment|// in this version of JabRef:
specifier|final
name|ParserResult
name|finalReferenceToResult
init|=
name|result
decl_stmt|;
name|SwingUtilities
operator|.
name|invokeLater
argument_list|(
parameter_list|()
lambda|->
name|OpenDatabaseAction
operator|.
name|performPostOpenActions
argument_list|(
name|panel
argument_list|,
name|finalReferenceToResult
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|addNewDatabase (ParserResult result, final Path file, boolean raisePanel)
specifier|private
name|BasePanel
name|addNewDatabase
parameter_list|(
name|ParserResult
name|result
parameter_list|,
specifier|final
name|Path
name|file
parameter_list|,
name|boolean
name|raisePanel
parameter_list|)
block|{
name|BibDatabase
name|database
init|=
name|result
operator|.
name|getDatabase
argument_list|()
decl_stmt|;
if|if
condition|(
name|result
operator|.
name|hasWarnings
argument_list|()
condition|)
block|{
name|JabRefExecutorService
operator|.
name|INSTANCE
operator|.
name|execute
argument_list|(
parameter_list|()
lambda|->
name|ParserResultWarningDialog
operator|.
name|showParserResultWarningDialog
argument_list|(
name|result
argument_list|,
name|frame
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|BasePanel
name|basePanel
init|=
operator|new
name|BasePanel
argument_list|(
name|frame
argument_list|,
name|result
operator|.
name|getDatabaseContext
argument_list|()
argument_list|)
decl_stmt|;
comment|// file is set to null inside the EventDispatcherThread
name|SwingUtilities
operator|.
name|invokeLater
argument_list|(
parameter_list|()
lambda|->
name|frame
operator|.
name|addTab
argument_list|(
name|basePanel
argument_list|,
name|raisePanel
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|Objects
operator|.
name|nonNull
argument_list|(
name|file
argument_list|)
condition|)
block|{
name|frame
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Opened library"
argument_list|)
operator|+
literal|" '"
operator|+
name|file
operator|.
name|toString
argument_list|()
operator|+
literal|"' "
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"with"
argument_list|)
operator|+
literal|" "
operator|+
name|database
operator|.
name|getEntryCount
argument_list|()
operator|+
literal|" "
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"entries"
argument_list|)
operator|+
literal|"."
argument_list|)
expr_stmt|;
block|}
return|return
name|basePanel
return|;
block|}
block|}
end_class

end_unit

