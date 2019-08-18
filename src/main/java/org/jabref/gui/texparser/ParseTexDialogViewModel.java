begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.texparser
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|texparser
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
name|file
operator|.
name|FileSystemException
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
name|Map
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|function
operator|.
name|Predicate
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
name|java
operator|.
name|util
operator|.
name|stream
operator|.
name|Stream
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
name|BooleanProperty
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
name|ObjectProperty
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
name|ReadOnlyListWrapper
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
name|SimpleBooleanProperty
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
name|SimpleObjectProperty
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
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|TreeItem
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
name|DirectoryDialogConfiguration
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
name|texparser
operator|.
name|DefaultTexParser
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
name|preferences
operator|.
name|PreferencesService
import|;
end_import

begin_import
import|import
name|de
operator|.
name|saxsys
operator|.
name|mvvmfx
operator|.
name|utils
operator|.
name|validation
operator|.
name|FunctionBasedValidator
import|;
end_import

begin_import
import|import
name|de
operator|.
name|saxsys
operator|.
name|mvvmfx
operator|.
name|utils
operator|.
name|validation
operator|.
name|ValidationMessage
import|;
end_import

begin_import
import|import
name|de
operator|.
name|saxsys
operator|.
name|mvvmfx
operator|.
name|utils
operator|.
name|validation
operator|.
name|ValidationStatus
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

begin_class
DECL|class|ParseTexDialogViewModel
specifier|public
class|class
name|ParseTexDialogViewModel
extends|extends
name|AbstractViewModel
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
name|ParseTexDialogViewModel
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|TEX_EXT
specifier|private
specifier|static
specifier|final
name|String
name|TEX_EXT
init|=
literal|".tex"
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
DECL|field|preferencesService
specifier|private
specifier|final
name|PreferencesService
name|preferencesService
decl_stmt|;
DECL|field|texDirectory
specifier|private
specifier|final
name|StringProperty
name|texDirectory
decl_stmt|;
DECL|field|texDirectoryValidator
specifier|private
specifier|final
name|FunctionBasedValidator
argument_list|<
name|String
argument_list|>
name|texDirectoryValidator
decl_stmt|;
DECL|field|root
specifier|private
specifier|final
name|ObjectProperty
argument_list|<
name|FileNodeViewModel
argument_list|>
name|root
decl_stmt|;
DECL|field|checkedFileList
specifier|private
specifier|final
name|ObservableList
argument_list|<
name|TreeItem
argument_list|<
name|FileNodeViewModel
argument_list|>
argument_list|>
name|checkedFileList
decl_stmt|;
DECL|field|noFilesFound
specifier|private
specifier|final
name|BooleanProperty
name|noFilesFound
decl_stmt|;
DECL|field|searchInProgress
specifier|private
specifier|final
name|BooleanProperty
name|searchInProgress
decl_stmt|;
DECL|field|successfulSearch
specifier|private
specifier|final
name|BooleanProperty
name|successfulSearch
decl_stmt|;
DECL|method|ParseTexDialogViewModel (BibDatabaseContext databaseContext, DialogService dialogService, TaskExecutor taskExecutor, PreferencesService preferencesService)
specifier|public
name|ParseTexDialogViewModel
parameter_list|(
name|BibDatabaseContext
name|databaseContext
parameter_list|,
name|DialogService
name|dialogService
parameter_list|,
name|TaskExecutor
name|taskExecutor
parameter_list|,
name|PreferencesService
name|preferencesService
parameter_list|)
block|{
name|this
operator|.
name|dialogService
operator|=
name|dialogService
expr_stmt|;
name|this
operator|.
name|taskExecutor
operator|=
name|taskExecutor
expr_stmt|;
name|this
operator|.
name|preferencesService
operator|=
name|preferencesService
expr_stmt|;
name|this
operator|.
name|texDirectory
operator|=
operator|new
name|SimpleStringProperty
argument_list|(
name|databaseContext
operator|.
name|getMetaData
argument_list|()
operator|.
name|getLaTexFileDirectory
argument_list|(
name|preferencesService
operator|.
name|getUser
argument_list|()
argument_list|)
operator|.
name|orElseGet
argument_list|(
name|preferencesService
operator|::
name|getWorkingDir
argument_list|)
operator|.
name|toAbsolutePath
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|this
operator|.
name|root
operator|=
operator|new
name|SimpleObjectProperty
argument_list|<>
argument_list|()
expr_stmt|;
name|this
operator|.
name|checkedFileList
operator|=
name|FXCollections
operator|.
name|observableArrayList
argument_list|()
expr_stmt|;
name|this
operator|.
name|noFilesFound
operator|=
operator|new
name|SimpleBooleanProperty
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|this
operator|.
name|searchInProgress
operator|=
operator|new
name|SimpleBooleanProperty
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|this
operator|.
name|successfulSearch
operator|=
operator|new
name|SimpleBooleanProperty
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|Predicate
argument_list|<
name|String
argument_list|>
name|isDirectory
init|=
name|path
lambda|->
name|Paths
operator|.
name|get
argument_list|(
name|path
argument_list|)
operator|.
name|toFile
argument_list|()
operator|.
name|isDirectory
argument_list|()
decl_stmt|;
name|texDirectoryValidator
operator|=
operator|new
name|FunctionBasedValidator
argument_list|<>
argument_list|(
name|texDirectory
argument_list|,
name|isDirectory
argument_list|,
name|ValidationMessage
operator|.
name|error
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Please enter a valid file path."
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|texDirectoryProperty ()
specifier|public
name|StringProperty
name|texDirectoryProperty
parameter_list|()
block|{
return|return
name|texDirectory
return|;
block|}
DECL|method|texDirectoryValidation ()
specifier|public
name|ValidationStatus
name|texDirectoryValidation
parameter_list|()
block|{
return|return
name|texDirectoryValidator
operator|.
name|getValidationStatus
argument_list|()
return|;
block|}
DECL|method|rootProperty ()
specifier|public
name|ObjectProperty
argument_list|<
name|FileNodeViewModel
argument_list|>
name|rootProperty
parameter_list|()
block|{
return|return
name|root
return|;
block|}
DECL|method|getCheckedFileList ()
specifier|public
name|ObservableList
argument_list|<
name|TreeItem
argument_list|<
name|FileNodeViewModel
argument_list|>
argument_list|>
name|getCheckedFileList
parameter_list|()
block|{
return|return
operator|new
name|ReadOnlyListWrapper
argument_list|<>
argument_list|(
name|checkedFileList
argument_list|)
return|;
block|}
DECL|method|noFilesFoundProperty ()
specifier|public
name|BooleanProperty
name|noFilesFoundProperty
parameter_list|()
block|{
return|return
name|noFilesFound
return|;
block|}
DECL|method|searchInProgressProperty ()
specifier|public
name|BooleanProperty
name|searchInProgressProperty
parameter_list|()
block|{
return|return
name|searchInProgress
return|;
block|}
DECL|method|successfulSearchProperty ()
specifier|public
name|BooleanProperty
name|successfulSearchProperty
parameter_list|()
block|{
return|return
name|successfulSearch
return|;
block|}
DECL|method|browseButtonClicked ()
specifier|public
name|void
name|browseButtonClicked
parameter_list|()
block|{
name|DirectoryDialogConfiguration
name|directoryDialogConfiguration
init|=
operator|new
name|DirectoryDialogConfiguration
operator|.
name|Builder
argument_list|()
operator|.
name|withInitialDirectory
argument_list|(
name|Paths
operator|.
name|get
argument_list|(
name|texDirectory
operator|.
name|get
argument_list|()
argument_list|)
argument_list|)
operator|.
name|build
argument_list|()
decl_stmt|;
name|dialogService
operator|.
name|showDirectorySelectionDialog
argument_list|(
name|directoryDialogConfiguration
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|selectedDirectory
lambda|->
block|{
name|texDirectory
operator|.
name|set
argument_list|(
name|selectedDirectory
operator|.
name|toAbsolutePath
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|preferencesService
operator|.
name|setWorkingDir
argument_list|(
name|selectedDirectory
operator|.
name|toAbsolutePath
argument_list|()
argument_list|)
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
block|}
comment|/**      * Run a recursive search in a background task.      */
DECL|method|searchButtonClicked ()
specifier|public
name|void
name|searchButtonClicked
parameter_list|()
block|{
name|BackgroundTask
operator|.
name|wrap
argument_list|(
parameter_list|()
lambda|->
name|searchDirectory
argument_list|(
name|Paths
operator|.
name|get
argument_list|(
name|texDirectory
operator|.
name|get
argument_list|()
argument_list|)
argument_list|)
argument_list|)
operator|.
name|onRunning
argument_list|(
parameter_list|()
lambda|->
block|{
name|root
operator|.
name|set
argument_list|(
literal|null
argument_list|)
argument_list|;
name|noFilesFound
operator|.
name|set
argument_list|(
literal|true
argument_list|)
argument_list|;
name|searchInProgress
operator|.
name|set
argument_list|(
literal|true
argument_list|)
argument_list|;
name|successfulSearch
operator|.
name|set
argument_list|(
literal|false
argument_list|)
argument_list|;
block|}
block|)
operator|.
name|onFinished
argument_list|(
parameter_list|()
lambda|->
name|searchInProgress
operator|.
name|set
argument_list|(
literal|false
argument_list|)
argument_list|)
operator|.
name|onSuccess
argument_list|(
name|newRoot
lambda|->
block|{
name|root
operator|.
name|set
argument_list|(
name|newRoot
argument_list|)
argument_list|;
name|noFilesFound
operator|.
name|set
argument_list|(
literal|false
argument_list|)
argument_list|;
name|successfulSearch
operator|.
name|set
argument_list|(
literal|true
argument_list|)
argument_list|;
end_class

begin_expr_stmt
unit|})
operator|.
name|onFailure
argument_list|(
name|this
operator|::
name|handleFailure
argument_list|)
operator|.
name|executeWith
argument_list|(
name|taskExecutor
argument_list|)
expr_stmt|;
end_expr_stmt

begin_function
unit|}      private
DECL|method|handleFailure (Exception exception)
name|void
name|handleFailure
parameter_list|(
name|Exception
name|exception
parameter_list|)
block|{
specifier|final
name|boolean
name|permissionProblem
init|=
name|exception
operator|instanceof
name|IOException
operator|&&
name|exception
operator|.
name|getCause
argument_list|()
operator|instanceof
name|FileSystemException
operator|&&
name|exception
operator|.
name|getCause
argument_list|()
operator|.
name|getMessage
argument_list|()
operator|.
name|endsWith
argument_list|(
literal|"Operation not permitted"
argument_list|)
decl_stmt|;
if|if
condition|(
name|permissionProblem
condition|)
block|{
name|dialogService
operator|.
name|showErrorDialogAndWait
argument_list|(
name|String
operator|.
name|format
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"JabRef does not have permission to access %s"
argument_list|)
argument_list|,
name|exception
operator|.
name|getCause
argument_list|()
operator|.
name|getMessage
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|dialogService
operator|.
name|showErrorDialogAndWait
argument_list|(
name|exception
argument_list|)
expr_stmt|;
block|}
block|}
end_function

begin_function
DECL|method|searchDirectory (Path directory)
specifier|private
name|FileNodeViewModel
name|searchDirectory
parameter_list|(
name|Path
name|directory
parameter_list|)
throws|throws
name|IOException
block|{
if|if
condition|(
name|directory
operator|==
literal|null
operator|||
operator|!
name|directory
operator|.
name|toFile
argument_list|()
operator|.
name|isDirectory
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|IOException
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"Invalid directory for searching: %s"
argument_list|,
name|directory
argument_list|)
argument_list|)
throw|;
block|}
name|FileNodeViewModel
name|parent
init|=
operator|new
name|FileNodeViewModel
argument_list|(
name|directory
argument_list|)
decl_stmt|;
name|Map
argument_list|<
name|Boolean
argument_list|,
name|List
argument_list|<
name|Path
argument_list|>
argument_list|>
name|fileListPartition
decl_stmt|;
try|try
init|(
name|Stream
argument_list|<
name|Path
argument_list|>
name|filesStream
init|=
name|Files
operator|.
name|list
argument_list|(
name|directory
argument_list|)
init|)
block|{
name|fileListPartition
operator|=
name|filesStream
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|partitioningBy
argument_list|(
name|path
lambda|->
name|path
operator|.
name|toFile
argument_list|()
operator|.
name|isDirectory
argument_list|()
argument_list|)
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
name|String
operator|.
name|format
argument_list|(
literal|"%s while searching files: %s"
argument_list|,
name|e
operator|.
name|getClass
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|,
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|parent
return|;
block|}
name|List
argument_list|<
name|Path
argument_list|>
name|subDirectories
init|=
name|fileListPartition
operator|.
name|get
argument_list|(
literal|true
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|Path
argument_list|>
name|files
init|=
name|fileListPartition
operator|.
name|get
argument_list|(
literal|false
argument_list|)
operator|.
name|stream
argument_list|()
operator|.
name|filter
argument_list|(
name|path
lambda|->
name|path
operator|.
name|toString
argument_list|()
operator|.
name|endsWith
argument_list|(
name|TEX_EXT
argument_list|)
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
name|int
name|fileCount
init|=
literal|0
decl_stmt|;
for|for
control|(
name|Path
name|subDirectory
range|:
name|subDirectories
control|)
block|{
name|FileNodeViewModel
name|subRoot
init|=
name|searchDirectory
argument_list|(
name|subDirectory
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|subRoot
operator|.
name|getChildren
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|fileCount
operator|+=
name|subRoot
operator|.
name|getFileCount
argument_list|()
expr_stmt|;
name|parent
operator|.
name|getChildren
argument_list|()
operator|.
name|add
argument_list|(
name|subRoot
argument_list|)
expr_stmt|;
block|}
block|}
name|parent
operator|.
name|setFileCount
argument_list|(
name|files
operator|.
name|size
argument_list|()
operator|+
name|fileCount
argument_list|)
expr_stmt|;
name|parent
operator|.
name|getChildren
argument_list|()
operator|.
name|addAll
argument_list|(
name|files
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|FileNodeViewModel
operator|::
operator|new
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|parent
return|;
block|}
end_function

begin_comment
comment|/**      * Parse all checked files in a background task.      */
end_comment

begin_function
DECL|method|parseButtonClicked ()
specifier|public
name|void
name|parseButtonClicked
parameter_list|()
block|{
name|List
argument_list|<
name|Path
argument_list|>
name|fileList
init|=
name|checkedFileList
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|item
lambda|->
name|item
operator|.
name|getValue
argument_list|()
operator|.
name|getPath
argument_list|()
argument_list|)
operator|.
name|filter
argument_list|(
name|path
lambda|->
name|path
operator|.
name|toFile
argument_list|()
operator|.
name|isFile
argument_list|()
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
if|if
condition|(
name|fileList
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"There are no valid files checked"
argument_list|)
expr_stmt|;
return|return;
block|}
name|BackgroundTask
operator|.
name|wrap
argument_list|(
parameter_list|()
lambda|->
operator|new
name|DefaultTexParser
argument_list|()
operator|.
name|parse
argument_list|(
name|fileList
argument_list|)
argument_list|)
operator|.
name|onRunning
argument_list|(
parameter_list|()
lambda|->
name|searchInProgress
operator|.
name|set
argument_list|(
literal|true
argument_list|)
argument_list|)
operator|.
name|onFinished
argument_list|(
parameter_list|()
lambda|->
name|searchInProgress
operator|.
name|set
argument_list|(
literal|false
argument_list|)
argument_list|)
operator|.
name|onSuccess
argument_list|(
name|result
lambda|->
operator|new
name|ParseTexResultView
argument_list|(
name|result
argument_list|,
name|Paths
operator|.
name|get
argument_list|(
name|texDirectory
operator|.
name|get
argument_list|()
argument_list|)
argument_list|)
operator|.
name|showAndWait
argument_list|()
argument_list|)
operator|.
name|onFailure
argument_list|(
name|dialogService
operator|::
name|showErrorDialogAndWait
argument_list|)
operator|.
name|executeWith
argument_list|(
name|taskExecutor
argument_list|)
expr_stmt|;
block|}
end_function

unit|}
end_unit

