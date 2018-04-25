begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.autosaveandbackup
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|autosaveandbackup
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
name|StandardCopyOption
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashSet
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
name|concurrent
operator|.
name|ArrayBlockingQueue
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|concurrent
operator|.
name|BlockingQueue
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|concurrent
operator|.
name|ExecutorService
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|concurrent
operator|.
name|RejectedExecutionException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|concurrent
operator|.
name|ThreadPoolExecutor
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|concurrent
operator|.
name|TimeUnit
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
name|InvalidFieldValueException
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
name|util
operator|.
name|io
operator|.
name|FileUtil
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
name|BibDatabaseContextChangedEvent
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
name|CoarseChangeFilter
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
name|com
operator|.
name|google
operator|.
name|common
operator|.
name|eventbus
operator|.
name|Subscribe
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
comment|/**  * Backups the given bib database file from {@link BibDatabaseContext} on every {@link BibDatabaseContextChangedEvent}.  * An intelligent {@link ExecutorService} with a {@link BlockingQueue} prevents a high load while making backups and  * rejects all redundant backup tasks.  * This class does not manage the .bak file which is created when opening a database.  */
end_comment

begin_class
DECL|class|BackupManager
specifier|public
class|class
name|BackupManager
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
name|BackupManager
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|BACKUP_EXTENSION
specifier|private
specifier|static
specifier|final
name|String
name|BACKUP_EXTENSION
init|=
literal|".sav"
decl_stmt|;
DECL|field|runningInstances
specifier|private
specifier|static
name|Set
argument_list|<
name|BackupManager
argument_list|>
name|runningInstances
init|=
operator|new
name|HashSet
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|bibDatabaseContext
specifier|private
specifier|final
name|BibDatabaseContext
name|bibDatabaseContext
decl_stmt|;
DECL|field|preferences
specifier|private
specifier|final
name|JabRefPreferences
name|preferences
decl_stmt|;
DECL|field|executor
specifier|private
specifier|final
name|ExecutorService
name|executor
decl_stmt|;
DECL|field|backupTask
specifier|private
specifier|final
name|Runnable
name|backupTask
init|=
parameter_list|()
lambda|->
name|determineBackupPath
argument_list|()
operator|.
name|ifPresent
argument_list|(
name|this
operator|::
name|performBackup
argument_list|)
decl_stmt|;
DECL|field|changeFilter
specifier|private
specifier|final
name|CoarseChangeFilter
name|changeFilter
decl_stmt|;
DECL|method|BackupManager (BibDatabaseContext bibDatabaseContext)
specifier|private
name|BackupManager
parameter_list|(
name|BibDatabaseContext
name|bibDatabaseContext
parameter_list|)
block|{
name|this
operator|.
name|bibDatabaseContext
operator|=
name|bibDatabaseContext
expr_stmt|;
name|this
operator|.
name|preferences
operator|=
name|JabRefPreferences
operator|.
name|getInstance
argument_list|()
expr_stmt|;
name|BlockingQueue
argument_list|<
name|Runnable
argument_list|>
name|workerQueue
init|=
operator|new
name|ArrayBlockingQueue
argument_list|<>
argument_list|(
literal|1
argument_list|)
decl_stmt|;
name|this
operator|.
name|executor
operator|=
operator|new
name|ThreadPoolExecutor
argument_list|(
literal|1
argument_list|,
literal|1
argument_list|,
literal|0
argument_list|,
name|TimeUnit
operator|.
name|SECONDS
argument_list|,
name|workerQueue
argument_list|)
expr_stmt|;
name|changeFilter
operator|=
operator|new
name|CoarseChangeFilter
argument_list|(
name|bibDatabaseContext
argument_list|)
expr_stmt|;
name|changeFilter
operator|.
name|registerListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
DECL|method|getBackupPath (Path originalPath)
specifier|static
name|Path
name|getBackupPath
parameter_list|(
name|Path
name|originalPath
parameter_list|)
block|{
return|return
name|FileUtil
operator|.
name|addExtension
argument_list|(
name|originalPath
argument_list|,
name|BACKUP_EXTENSION
argument_list|)
return|;
block|}
comment|/**      * Starts the BackupManager which is associated with the given {@link BibDatabaseContext}.      * As long as no database file is present in {@link BibDatabaseContext}, the {@link BackupManager} will do nothing.      *      * @param bibDatabaseContext Associated {@link BibDatabaseContext}      */
DECL|method|start (BibDatabaseContext bibDatabaseContext)
specifier|public
specifier|static
name|BackupManager
name|start
parameter_list|(
name|BibDatabaseContext
name|bibDatabaseContext
parameter_list|)
block|{
name|BackupManager
name|backupManager
init|=
operator|new
name|BackupManager
argument_list|(
name|bibDatabaseContext
argument_list|)
decl_stmt|;
name|backupManager
operator|.
name|startBackupTask
argument_list|()
expr_stmt|;
name|runningInstances
operator|.
name|add
argument_list|(
name|backupManager
argument_list|)
expr_stmt|;
return|return
name|backupManager
return|;
block|}
comment|/**      * Shuts down the BackupManager which is associated with the given {@link BibDatabaseContext}.      *      * @param bibDatabaseContext Associated {@link BibDatabaseContext}      */
DECL|method|shutdown (BibDatabaseContext bibDatabaseContext)
specifier|public
specifier|static
name|void
name|shutdown
parameter_list|(
name|BibDatabaseContext
name|bibDatabaseContext
parameter_list|)
block|{
name|runningInstances
operator|.
name|stream
argument_list|()
operator|.
name|filter
argument_list|(
name|instance
lambda|->
name|instance
operator|.
name|bibDatabaseContext
operator|==
name|bibDatabaseContext
argument_list|)
operator|.
name|forEach
argument_list|(
name|BackupManager
operator|::
name|shutdown
argument_list|)
expr_stmt|;
name|runningInstances
operator|.
name|removeIf
argument_list|(
name|instance
lambda|->
name|instance
operator|.
name|bibDatabaseContext
operator|==
name|bibDatabaseContext
argument_list|)
expr_stmt|;
block|}
comment|/**      * Checks whether a backup file exists for the given database file.      *      * @param originalPath Path to the file a backup should be checked for.      */
DECL|method|checkForBackupFile (Path originalPath)
specifier|public
specifier|static
name|boolean
name|checkForBackupFile
parameter_list|(
name|Path
name|originalPath
parameter_list|)
block|{
name|Path
name|backupPath
init|=
name|getBackupPath
argument_list|(
name|originalPath
argument_list|)
decl_stmt|;
return|return
name|Files
operator|.
name|exists
argument_list|(
name|backupPath
argument_list|)
operator|&&
operator|!
name|Files
operator|.
name|isDirectory
argument_list|(
name|backupPath
argument_list|)
return|;
block|}
comment|/**      * Restores the backup file by copying and overwriting the original one.      *      * @param originalPath Path to the file which should be equalized to the backup file.      */
DECL|method|restoreBackup (Path originalPath)
specifier|public
specifier|static
name|void
name|restoreBackup
parameter_list|(
name|Path
name|originalPath
parameter_list|)
block|{
name|Path
name|backupPath
init|=
name|getBackupPath
argument_list|(
name|originalPath
argument_list|)
decl_stmt|;
try|try
block|{
name|Files
operator|.
name|copy
argument_list|(
name|backupPath
argument_list|,
name|originalPath
argument_list|,
name|StandardCopyOption
operator|.
name|REPLACE_EXISTING
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Error while restoring the backup file."
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|determineBackupPath ()
specifier|private
name|Optional
argument_list|<
name|Path
argument_list|>
name|determineBackupPath
parameter_list|()
block|{
return|return
name|bibDatabaseContext
operator|.
name|getDatabasePath
argument_list|()
operator|.
name|map
argument_list|(
name|BackupManager
operator|::
name|getBackupPath
argument_list|)
return|;
block|}
DECL|method|performBackup (Path backupPath)
specifier|private
name|void
name|performBackup
parameter_list|(
name|Path
name|backupPath
parameter_list|)
block|{
try|try
block|{
name|Charset
name|charset
init|=
name|bibDatabaseContext
operator|.
name|getMetaData
argument_list|()
operator|.
name|getEncoding
argument_list|()
operator|.
name|orElse
argument_list|(
name|preferences
operator|.
name|getDefaultEncoding
argument_list|()
argument_list|)
decl_stmt|;
name|SavePreferences
name|savePreferences
init|=
name|preferences
operator|.
name|loadForSaveFromPreferences
argument_list|()
operator|.
name|withEncoding
argument_list|(
name|charset
argument_list|)
operator|.
name|withMakeBackup
argument_list|(
literal|false
argument_list|)
decl_stmt|;
operator|new
name|BibtexDatabaseWriter
argument_list|<>
argument_list|(
name|FileSaveSession
operator|::
operator|new
argument_list|)
operator|.
name|saveDatabase
argument_list|(
name|bibDatabaseContext
argument_list|,
name|savePreferences
argument_list|)
operator|.
name|commit
argument_list|(
name|backupPath
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|SaveException
name|e
parameter_list|)
block|{
name|logIfCritical
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|logIfCritical (SaveException e)
specifier|private
name|void
name|logIfCritical
parameter_list|(
name|SaveException
name|e
parameter_list|)
block|{
name|Throwable
name|innermostCause
init|=
name|e
decl_stmt|;
while|while
condition|(
name|innermostCause
operator|.
name|getCause
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|innermostCause
operator|=
name|innermostCause
operator|.
name|getCause
argument_list|()
expr_stmt|;
block|}
name|boolean
name|isErrorInField
init|=
name|innermostCause
operator|instanceof
name|InvalidFieldValueException
decl_stmt|;
comment|// do not print errors in field values into the log during autosave
if|if
condition|(
operator|!
name|isErrorInField
condition|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Error while saving file."
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Subscribe
DECL|method|listen (@uppressWarningsR) BibDatabaseContextChangedEvent event)
specifier|public
specifier|synchronized
name|void
name|listen
parameter_list|(
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unused"
argument_list|)
name|BibDatabaseContextChangedEvent
name|event
parameter_list|)
block|{
name|startBackupTask
argument_list|()
expr_stmt|;
block|}
DECL|method|startBackupTask ()
specifier|private
name|void
name|startBackupTask
parameter_list|()
block|{
try|try
block|{
name|executor
operator|.
name|submit
argument_list|(
name|backupTask
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|RejectedExecutionException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|debug
argument_list|(
literal|"Rejecting while another backup process is already running."
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Unregisters the BackupManager from the eventBus of {@link BibDatabaseContext} and deletes the backup file.      * This method should only be used when closing a database/JabRef legally.      */
DECL|method|shutdown ()
specifier|private
name|void
name|shutdown
parameter_list|()
block|{
name|changeFilter
operator|.
name|unregisterListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|changeFilter
operator|.
name|shutdown
argument_list|()
expr_stmt|;
name|executor
operator|.
name|shutdown
argument_list|()
expr_stmt|;
name|determineBackupPath
argument_list|()
operator|.
name|ifPresent
argument_list|(
name|this
operator|::
name|deleteBackupFile
argument_list|)
expr_stmt|;
block|}
DECL|method|deleteBackupFile (Path backupPath)
specifier|private
name|void
name|deleteBackupFile
parameter_list|(
name|Path
name|backupPath
parameter_list|)
block|{
try|try
block|{
if|if
condition|(
name|Files
operator|.
name|exists
argument_list|(
name|backupPath
argument_list|)
operator|&&
operator|!
name|Files
operator|.
name|isDirectory
argument_list|(
name|backupPath
argument_list|)
condition|)
block|{
name|Files
operator|.
name|delete
argument_list|(
name|backupPath
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Error while deleting the backup file."
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

