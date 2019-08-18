begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.entryeditor
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|entryeditor
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
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
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
name|Optional
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
name|Future
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
name|texparser
operator|.
name|Citation
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
name|texparser
operator|.
name|TexParserResult
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
DECL|class|LatexCitationsTabViewModel
specifier|public
class|class
name|LatexCitationsTabViewModel
extends|extends
name|AbstractViewModel
block|{
DECL|enum|Status
enum|enum
name|Status
block|{
DECL|enumConstant|IN_PROGRESS
name|IN_PROGRESS
block|,
DECL|enumConstant|CITATIONS_FOUND
name|CITATIONS_FOUND
block|,
DECL|enumConstant|NO_RESULTS
name|NO_RESULTS
block|,
DECL|enumConstant|ERROR
name|ERROR
block|}
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
name|LatexCitationsTabViewModel
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
DECL|field|databaseContext
specifier|private
specifier|final
name|BibDatabaseContext
name|databaseContext
decl_stmt|;
DECL|field|preferencesService
specifier|private
specifier|final
name|PreferencesService
name|preferencesService
decl_stmt|;
DECL|field|taskExecutor
specifier|private
specifier|final
name|TaskExecutor
name|taskExecutor
decl_stmt|;
DECL|field|dialogService
specifier|private
specifier|final
name|DialogService
name|dialogService
decl_stmt|;
DECL|field|directory
specifier|private
specifier|final
name|ObjectProperty
argument_list|<
name|Path
argument_list|>
name|directory
decl_stmt|;
DECL|field|citationList
specifier|private
specifier|final
name|ObservableList
argument_list|<
name|Citation
argument_list|>
name|citationList
decl_stmt|;
DECL|field|status
specifier|private
specifier|final
name|ObjectProperty
argument_list|<
name|Status
argument_list|>
name|status
decl_stmt|;
DECL|field|searchError
specifier|private
specifier|final
name|StringProperty
name|searchError
decl_stmt|;
DECL|field|searchTask
specifier|private
name|Future
argument_list|<
name|?
argument_list|>
name|searchTask
decl_stmt|;
DECL|field|texParserResult
specifier|private
name|TexParserResult
name|texParserResult
decl_stmt|;
DECL|field|currentEntry
specifier|private
name|BibEntry
name|currentEntry
decl_stmt|;
DECL|method|LatexCitationsTabViewModel (BibDatabaseContext databaseContext, PreferencesService preferencesService, TaskExecutor taskExecutor, DialogService dialogService)
specifier|public
name|LatexCitationsTabViewModel
parameter_list|(
name|BibDatabaseContext
name|databaseContext
parameter_list|,
name|PreferencesService
name|preferencesService
parameter_list|,
name|TaskExecutor
name|taskExecutor
parameter_list|,
name|DialogService
name|dialogService
parameter_list|)
block|{
name|this
operator|.
name|databaseContext
operator|=
name|databaseContext
expr_stmt|;
name|this
operator|.
name|preferencesService
operator|=
name|preferencesService
expr_stmt|;
name|this
operator|.
name|taskExecutor
operator|=
name|taskExecutor
expr_stmt|;
name|this
operator|.
name|dialogService
operator|=
name|dialogService
expr_stmt|;
name|this
operator|.
name|directory
operator|=
operator|new
name|SimpleObjectProperty
argument_list|<>
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
argument_list|)
expr_stmt|;
name|this
operator|.
name|citationList
operator|=
name|FXCollections
operator|.
name|observableArrayList
argument_list|()
expr_stmt|;
name|this
operator|.
name|status
operator|=
operator|new
name|SimpleObjectProperty
argument_list|<>
argument_list|(
name|Status
operator|.
name|IN_PROGRESS
argument_list|)
expr_stmt|;
name|this
operator|.
name|searchError
operator|=
operator|new
name|SimpleStringProperty
argument_list|(
literal|""
argument_list|)
expr_stmt|;
block|}
DECL|method|init (BibEntry entry)
specifier|public
name|void
name|init
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
name|cancelSearch
argument_list|()
expr_stmt|;
name|currentEntry
operator|=
name|entry
expr_stmt|;
name|Optional
argument_list|<
name|String
argument_list|>
name|citeKey
init|=
name|entry
operator|.
name|getCiteKeyOptional
argument_list|()
decl_stmt|;
if|if
condition|(
name|citeKey
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|startSearch
argument_list|(
name|citeKey
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|searchError
operator|.
name|set
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Selected entry does not have an associated BibTeX key."
argument_list|)
argument_list|)
expr_stmt|;
name|status
operator|.
name|set
argument_list|(
name|Status
operator|.
name|ERROR
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|directoryProperty ()
specifier|public
name|ObjectProperty
argument_list|<
name|Path
argument_list|>
name|directoryProperty
parameter_list|()
block|{
return|return
name|directory
return|;
block|}
DECL|method|getCitationList ()
specifier|public
name|ObservableList
argument_list|<
name|Citation
argument_list|>
name|getCitationList
parameter_list|()
block|{
return|return
operator|new
name|ReadOnlyListWrapper
argument_list|<>
argument_list|(
name|citationList
argument_list|)
return|;
block|}
DECL|method|statusProperty ()
specifier|public
name|ObjectProperty
argument_list|<
name|Status
argument_list|>
name|statusProperty
parameter_list|()
block|{
return|return
name|status
return|;
block|}
DECL|method|searchErrorProperty ()
specifier|public
name|StringProperty
name|searchErrorProperty
parameter_list|()
block|{
return|return
name|searchError
return|;
block|}
DECL|method|startSearch (String citeKey)
specifier|private
name|void
name|startSearch
parameter_list|(
name|String
name|citeKey
parameter_list|)
block|{
name|searchTask
operator|=
name|BackgroundTask
operator|.
name|wrap
argument_list|(
parameter_list|()
lambda|->
name|searchAndParse
argument_list|(
name|citeKey
argument_list|)
argument_list|)
operator|.
name|onRunning
argument_list|(
parameter_list|()
lambda|->
name|status
operator|.
name|set
argument_list|(
name|Status
operator|.
name|IN_PROGRESS
argument_list|)
argument_list|)
operator|.
name|onSuccess
argument_list|(
name|result
lambda|->
block|{
name|citationList
operator|.
name|setAll
argument_list|(
name|result
argument_list|)
argument_list|;
name|status
operator|.
name|set
argument_list|(
name|citationList
operator|.
name|isEmpty
argument_list|()
condition|?
name|Status
operator|.
name|NO_RESULTS
else|:
name|Status
operator|.
name|CITATIONS_FOUND
argument_list|)
argument_list|;
block|}
block|)
operator|.
name|onFailure
argument_list|(
name|error
lambda|->
block|{
name|searchError
operator|.
name|set
argument_list|(
name|error
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
name|status
operator|.
name|set
argument_list|(
name|Status
operator|.
name|ERROR
argument_list|)
expr_stmt|;
block|}
argument_list|)
operator|.
name|executeWith
argument_list|(
name|taskExecutor
argument_list|)
expr_stmt|;
end_class

begin_function
unit|}      private
DECL|method|cancelSearch ()
name|void
name|cancelSearch
parameter_list|()
block|{
if|if
condition|(
name|searchTask
operator|==
literal|null
operator|||
name|searchTask
operator|.
name|isCancelled
argument_list|()
operator|||
name|searchTask
operator|.
name|isDone
argument_list|()
condition|)
block|{
return|return;
block|}
name|status
operator|.
name|set
argument_list|(
name|Status
operator|.
name|IN_PROGRESS
argument_list|)
expr_stmt|;
name|searchTask
operator|.
name|cancel
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
end_function

begin_function
DECL|method|searchAndParse (String citeKey)
specifier|private
name|Collection
argument_list|<
name|Citation
argument_list|>
name|searchAndParse
parameter_list|(
name|String
name|citeKey
parameter_list|)
throws|throws
name|IOException
block|{
name|Path
name|newDirectory
init|=
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
decl_stmt|;
if|if
condition|(
name|texParserResult
operator|==
literal|null
operator|||
operator|!
name|newDirectory
operator|.
name|equals
argument_list|(
name|directory
operator|.
name|get
argument_list|()
argument_list|)
condition|)
block|{
name|directory
operator|.
name|set
argument_list|(
name|newDirectory
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|newDirectory
operator|.
name|toFile
argument_list|()
operator|.
name|exists
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
literal|"Current search directory does not exist: %s"
argument_list|,
name|newDirectory
argument_list|)
argument_list|)
throw|;
block|}
name|List
argument_list|<
name|Path
argument_list|>
name|texFiles
init|=
name|searchDirectory
argument_list|(
name|newDirectory
argument_list|,
operator|new
name|ArrayList
argument_list|<>
argument_list|()
argument_list|)
decl_stmt|;
name|texParserResult
operator|=
operator|new
name|DefaultTexParser
argument_list|()
operator|.
name|parse
argument_list|(
name|texFiles
argument_list|)
expr_stmt|;
block|}
return|return
name|texParserResult
operator|.
name|getCitationsByKey
argument_list|(
name|citeKey
argument_list|)
return|;
block|}
end_function

begin_function
DECL|method|searchDirectory (Path directory, List<Path> texFiles)
specifier|private
name|List
argument_list|<
name|Path
argument_list|>
name|searchDirectory
parameter_list|(
name|Path
name|directory
parameter_list|,
name|List
argument_list|<
name|Path
argument_list|>
name|texFiles
parameter_list|)
block|{
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
name|texFiles
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
name|texFiles
operator|.
name|addAll
argument_list|(
name|files
argument_list|)
expr_stmt|;
name|subDirectories
operator|.
name|forEach
argument_list|(
name|subDirectory
lambda|->
name|searchDirectory
argument_list|(
name|subDirectory
argument_list|,
name|texFiles
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|texFiles
return|;
block|}
end_function

begin_function
DECL|method|setLatexDirectory ()
specifier|public
name|void
name|setLatexDirectory
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
name|directory
operator|.
name|get
argument_list|()
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
name|databaseContext
operator|.
name|getMetaData
argument_list|()
operator|.
name|setLaTexFileDirectory
argument_list|(
name|preferencesService
operator|.
name|getUser
argument_list|()
argument_list|,
name|selectedDirectory
operator|.
name|toAbsolutePath
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|init
argument_list|(
name|currentEntry
argument_list|)
expr_stmt|;
block|}
end_function

begin_function
DECL|method|shouldShow ()
specifier|public
name|boolean
name|shouldShow
parameter_list|()
block|{
return|return
name|preferencesService
operator|.
name|getEntryEditorPreferences
argument_list|()
operator|.
name|shouldShowLatexCitationsTab
argument_list|()
return|;
block|}
end_function

unit|}
end_unit
