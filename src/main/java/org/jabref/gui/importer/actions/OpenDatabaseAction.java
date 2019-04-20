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
name|Arrays
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
name|BasePanelPreferences
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
name|actions
operator|.
name|SimpleCommand
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
name|SimpleCommand
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
name|Arrays
operator|.
name|asList
argument_list|(
comment|// Migrations:
comment|// Warning for migrating the Review into the Comment field
operator|new
name|MergeReviewIntoCommentAction
argument_list|()
argument_list|,
comment|// Check for new custom entry types loaded from the BIB file:
operator|new
name|CheckForNewEntryTypesAction
argument_list|()
argument_list|)
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
DECL|method|OpenDatabaseAction (JabRefFrame frame)
specifier|public
name|OpenDatabaseAction
parameter_list|(
name|JabRefFrame
name|frame
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
name|dialogService
operator|=
name|frame
operator|.
name|getDialogService
argument_list|()
expr_stmt|;
block|}
comment|/**      * Go through the list of post open actions, and perform those that need to be performed.      *      * @param panel  The BasePanel where the database is shown.      * @param result The result of the BIB file parse operation.      * @param dialogService      */
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
name|showBasePanel
argument_list|(
name|panel
argument_list|)
expr_stmt|;
block|}
block|}
block|}
annotation|@
name|Override
DECL|method|execute ()
specifier|public
name|void
name|execute
parameter_list|()
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
name|getInitialDirectory
argument_list|()
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
name|dialogService
operator|.
name|showFileOpenDialogAndGetMultipleFiles
argument_list|(
name|fileDialogConfiguration
argument_list|)
decl_stmt|;
name|filesToOpen
operator|.
name|addAll
argument_list|(
name|chosenFiles
argument_list|)
expr_stmt|;
name|openFiles
argument_list|(
name|filesToOpen
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
comment|/**      *      * @return Path of current panel database directory or the working directory      */
DECL|method|getInitialDirectory ()
specifier|private
name|Path
name|getInitialDirectory
parameter_list|()
block|{
if|if
condition|(
name|frame
operator|.
name|getBasePanelCount
argument_list|()
operator|==
literal|0
condition|)
block|{
return|return
name|getWorkingDirectoryPath
argument_list|()
return|;
block|}
else|else
block|{
name|Optional
argument_list|<
name|Path
argument_list|>
name|databasePath
init|=
name|frame
operator|.
name|getCurrentBasePanel
argument_list|()
operator|.
name|getBibDatabaseContext
argument_list|()
operator|.
name|getDatabasePath
argument_list|()
decl_stmt|;
return|return
name|databasePath
operator|.
name|map
argument_list|(
name|p
lambda|->
name|p
operator|.
name|getParent
argument_list|()
argument_list|)
operator|.
name|orElse
argument_list|(
name|getWorkingDirectoryPath
argument_list|()
argument_list|)
return|;
block|}
block|}
DECL|method|getWorkingDirectoryPath ()
specifier|private
name|Path
name|getWorkingDirectoryPath
parameter_list|()
block|{
return|return
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
return|;
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
name|getTabs
argument_list|()
operator|.
name|size
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
name|dialogService
operator|.
name|notify
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
name|getDatabasePath
argument_list|()
operator|.
name|get
argument_list|()
operator|.
name|getFileName
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|frame
operator|.
name|showBasePanel
argument_list|(
name|toRaise
argument_list|)
expr_stmt|;
block|}
name|dialogService
operator|.
name|notify
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
comment|/**      * @param file the file, may be null or not existing      * @return      */
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
name|BackgroundTask
operator|.
name|wrap
argument_list|(
parameter_list|()
lambda|->
name|loadDatabase
argument_list|(
name|file
argument_list|)
argument_list|)
operator|.
name|onSuccess
argument_list|(
name|result
lambda|->
block|{
name|BasePanel
name|panel
operator|=
name|addNewDatabase
argument_list|(
name|result
argument_list|,
name|file
argument_list|,
name|raisePanel
argument_list|)
argument_list|;
name|OpenDatabaseAction
operator|.
name|performPostOpenActions
argument_list|(
name|panel
argument_list|,
name|result
argument_list|)
expr_stmt|;
block|}
block|)
function|.onFailure
parameter_list|(
function|ex -> dialogService.showErrorDialogAndWait
parameter_list|(
function|Localization.lang
parameter_list|(
function|"Connection error"
block|)
operator|,
name|ex
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
end_class

begin_expr_stmt
unit|))
operator|.
name|executeWith
argument_list|(
name|Globals
operator|.
name|TASK_EXECUTOR
argument_list|)
expr_stmt|;
end_expr_stmt

begin_function
unit|}      }
DECL|method|loadDatabase (Path file)
specifier|private
name|ParserResult
name|loadDatabase
parameter_list|(
name|Path
name|file
parameter_list|)
throws|throws
name|Exception
block|{
name|Path
name|fileToLoad
init|=
name|file
operator|.
name|toAbsolutePath
argument_list|()
decl_stmt|;
name|dialogService
operator|.
name|notify
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
name|dialogService
argument_list|,
name|fileToLoad
argument_list|)
expr_stmt|;
block|}
name|ParserResult
name|result
init|=
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
decl_stmt|;
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
throw|throw
name|e
throw|;
block|}
block|}
return|return
name|result
return|;
block|}
end_function

begin_function
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
name|ParserResultWarningDialog
operator|.
name|showParserResultWarningDialog
argument_list|(
name|result
argument_list|,
name|frame
argument_list|)
expr_stmt|;
block|}
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
name|dialogService
operator|.
name|notify
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
name|BasePanel
name|basePanel
init|=
operator|new
name|BasePanel
argument_list|(
name|frame
argument_list|,
name|BasePanelPreferences
operator|.
name|from
argument_list|(
name|Globals
operator|.
name|prefs
argument_list|)
argument_list|,
name|result
operator|.
name|getDatabaseContext
argument_list|()
argument_list|,
name|ExternalFileTypes
operator|.
name|getInstance
argument_list|()
argument_list|)
decl_stmt|;
name|frame
operator|.
name|addTab
argument_list|(
name|basePanel
argument_list|,
name|raisePanel
argument_list|)
expr_stmt|;
return|return
name|basePanel
return|;
block|}
end_function

unit|}
end_unit

