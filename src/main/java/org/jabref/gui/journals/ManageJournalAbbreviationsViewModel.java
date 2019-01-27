begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.journals
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|journals
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|FileNotFoundException
import|;
end_import

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
name|ArrayList
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
name|SimpleIntegerProperty
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
name|SimpleListProperty
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
name|ListChangeListener
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
name|FileDialogConfiguration
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
name|journals
operator|.
name|Abbreviation
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
name|journals
operator|.
name|JournalAbbreviationLoader
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
name|journals
operator|.
name|JournalAbbreviationPreferences
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

begin_comment
comment|/**  * This class provides a model for managing journal abbreviation lists.  * It provides all necessary methods to create, modify or delete journal  * abbreviations and files. To visualize the model one can bind the properties  * to ui elements.  */
end_comment

begin_class
DECL|class|ManageJournalAbbreviationsViewModel
specifier|public
class|class
name|ManageJournalAbbreviationsViewModel
extends|extends
name|AbstractViewModel
block|{
DECL|field|logger
specifier|private
specifier|final
name|Logger
name|logger
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|ManageJournalAbbreviationsViewModel
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|journalFiles
specifier|private
specifier|final
name|SimpleListProperty
argument_list|<
name|AbbreviationsFileViewModel
argument_list|>
name|journalFiles
init|=
operator|new
name|SimpleListProperty
argument_list|<>
argument_list|(
name|FXCollections
operator|.
name|observableArrayList
argument_list|()
argument_list|)
decl_stmt|;
DECL|field|abbreviations
specifier|private
specifier|final
name|SimpleListProperty
argument_list|<
name|AbbreviationViewModel
argument_list|>
name|abbreviations
init|=
operator|new
name|SimpleListProperty
argument_list|<>
argument_list|(
name|FXCollections
operator|.
name|observableArrayList
argument_list|()
argument_list|)
decl_stmt|;
DECL|field|abbreviationsCount
specifier|private
specifier|final
name|SimpleIntegerProperty
name|abbreviationsCount
init|=
operator|new
name|SimpleIntegerProperty
argument_list|()
decl_stmt|;
DECL|field|currentFile
specifier|private
specifier|final
name|SimpleObjectProperty
argument_list|<
name|AbbreviationsFileViewModel
argument_list|>
name|currentFile
init|=
operator|new
name|SimpleObjectProperty
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|currentAbbreviation
specifier|private
specifier|final
name|SimpleObjectProperty
argument_list|<
name|AbbreviationViewModel
argument_list|>
name|currentAbbreviation
init|=
operator|new
name|SimpleObjectProperty
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|isFileRemovable
specifier|private
specifier|final
name|SimpleBooleanProperty
name|isFileRemovable
init|=
operator|new
name|SimpleBooleanProperty
argument_list|()
decl_stmt|;
DECL|field|isLoading
specifier|private
specifier|final
name|SimpleBooleanProperty
name|isLoading
init|=
operator|new
name|SimpleBooleanProperty
argument_list|(
literal|false
argument_list|)
decl_stmt|;
DECL|field|isLoadingBuiltIn
specifier|private
specifier|final
name|SimpleBooleanProperty
name|isLoadingBuiltIn
init|=
operator|new
name|SimpleBooleanProperty
argument_list|(
literal|false
argument_list|)
decl_stmt|;
DECL|field|isLoadingIeee
specifier|private
specifier|final
name|SimpleBooleanProperty
name|isLoadingIeee
init|=
operator|new
name|SimpleBooleanProperty
argument_list|(
literal|false
argument_list|)
decl_stmt|;
DECL|field|isAbbreviationEditableAndRemovable
specifier|private
specifier|final
name|SimpleBooleanProperty
name|isAbbreviationEditableAndRemovable
init|=
operator|new
name|SimpleBooleanProperty
argument_list|()
decl_stmt|;
DECL|field|preferences
specifier|private
specifier|final
name|PreferencesService
name|preferences
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
DECL|field|abbreviationsPreferences
specifier|private
specifier|final
name|JournalAbbreviationPreferences
name|abbreviationsPreferences
decl_stmt|;
DECL|field|journalAbbreviationLoader
specifier|private
specifier|final
name|JournalAbbreviationLoader
name|journalAbbreviationLoader
decl_stmt|;
DECL|field|shouldWriteLists
specifier|private
name|boolean
name|shouldWriteLists
init|=
literal|false
decl_stmt|;
DECL|method|ManageJournalAbbreviationsViewModel (PreferencesService preferences, DialogService dialogService, TaskExecutor taskExecutor, JournalAbbreviationLoader journalAbbreviationLoader)
specifier|public
name|ManageJournalAbbreviationsViewModel
parameter_list|(
name|PreferencesService
name|preferences
parameter_list|,
name|DialogService
name|dialogService
parameter_list|,
name|TaskExecutor
name|taskExecutor
parameter_list|,
name|JournalAbbreviationLoader
name|journalAbbreviationLoader
parameter_list|)
block|{
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
name|this
operator|.
name|dialogService
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|dialogService
argument_list|)
expr_stmt|;
name|this
operator|.
name|taskExecutor
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|taskExecutor
argument_list|)
expr_stmt|;
name|this
operator|.
name|journalAbbreviationLoader
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|journalAbbreviationLoader
argument_list|)
expr_stmt|;
name|this
operator|.
name|abbreviationsPreferences
operator|=
name|preferences
operator|.
name|getJournalAbbreviationPreferences
argument_list|()
expr_stmt|;
name|abbreviationsCount
operator|.
name|bind
argument_list|(
name|abbreviations
operator|.
name|sizeProperty
argument_list|()
argument_list|)
expr_stmt|;
name|currentAbbreviation
operator|.
name|addListener
argument_list|(
parameter_list|(
name|observable
parameter_list|,
name|oldvalue
parameter_list|,
name|newvalue
parameter_list|)
lambda|->
block|{
name|boolean
name|isAbbreviation
init|=
operator|(
name|newvalue
operator|!=
literal|null
operator|)
operator|&&
operator|!
name|newvalue
operator|.
name|isPseudoAbbreviation
argument_list|()
decl_stmt|;
name|boolean
name|isEditableFile
init|=
operator|(
name|currentFile
operator|.
name|get
argument_list|()
operator|!=
literal|null
operator|)
operator|&&
operator|!
name|currentFile
operator|.
name|get
argument_list|()
operator|.
name|isBuiltInListProperty
argument_list|()
operator|.
name|get
argument_list|()
decl_stmt|;
name|isAbbreviationEditableAndRemovable
operator|.
name|set
argument_list|(
name|isAbbreviation
operator|&&
name|isEditableFile
argument_list|)
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
name|currentFile
operator|.
name|addListener
argument_list|(
parameter_list|(
name|observable
parameter_list|,
name|oldvalue
parameter_list|,
name|newvalue
parameter_list|)
lambda|->
block|{
if|if
condition|(
name|oldvalue
operator|!=
literal|null
condition|)
block|{
name|abbreviations
operator|.
name|unbindBidirectional
argument_list|(
name|oldvalue
operator|.
name|abbreviationsProperty
argument_list|()
argument_list|)
expr_stmt|;
name|currentAbbreviation
operator|.
name|set
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|newvalue
operator|!=
literal|null
condition|)
block|{
name|isFileRemovable
operator|.
name|set
argument_list|(
operator|!
name|newvalue
operator|.
name|isBuiltInListProperty
argument_list|()
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
name|abbreviations
operator|.
name|bindBidirectional
argument_list|(
name|newvalue
operator|.
name|abbreviationsProperty
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|abbreviations
operator|.
name|size
argument_list|()
operator|>
literal|0
condition|)
block|{
name|currentAbbreviation
operator|.
name|set
argument_list|(
name|abbreviations
operator|.
name|get
argument_list|(
name|abbreviations
operator|.
name|size
argument_list|()
operator|-
literal|1
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|isFileRemovable
operator|.
name|set
argument_list|(
literal|false
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|journalFiles
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|currentFile
operator|.
name|set
argument_list|(
name|journalFiles
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|currentAbbreviation
operator|.
name|set
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|abbreviations
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
block|}
block|}
argument_list|)
expr_stmt|;
name|journalFiles
operator|.
name|addListener
argument_list|(
operator|(
name|ListChangeListener
argument_list|<
name|AbbreviationsFileViewModel
argument_list|>
operator|)
name|c
lambda|->
block|{
if|if
condition|(
name|c
operator|.
name|next
argument_list|()
condition|)
block|{
if|if
condition|(
operator|!
name|c
operator|.
name|wasReplaced
argument_list|()
condition|)
block|{
if|if
condition|(
name|c
operator|.
name|wasAdded
argument_list|()
operator|&&
operator|!
name|c
operator|.
name|getAddedSubList
argument_list|()
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|isBuiltInListProperty
argument_list|()
operator|.
name|get
argument_list|()
condition|)
block|{
name|currentFile
operator|.
name|set
argument_list|(
name|c
operator|.
name|getAddedSubList
argument_list|()
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
argument_list|)
expr_stmt|;
name|isLoading
operator|.
name|bind
argument_list|(
name|isLoadingBuiltIn
operator|.
name|or
argument_list|(
name|isLoadingIeee
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|isLoadingProperty ()
specifier|public
name|SimpleBooleanProperty
name|isLoadingProperty
parameter_list|()
block|{
return|return
name|isLoading
return|;
block|}
DECL|method|isAbbreviationEditableAndRemovable ()
specifier|public
name|boolean
name|isAbbreviationEditableAndRemovable
parameter_list|()
block|{
return|return
name|isAbbreviationEditableAndRemovable
operator|.
name|get
argument_list|()
return|;
block|}
comment|/**      * This will wrap the built in and ieee abbreviations in pseudo abbreviation files      * and add them to the list of journal abbreviation files.      */
DECL|method|addBuiltInLists ()
name|void
name|addBuiltInLists
parameter_list|()
block|{
name|BackgroundTask
operator|.
name|wrap
argument_list|(
name|JournalAbbreviationLoader
operator|::
name|getBuiltInAbbreviations
argument_list|)
operator|.
name|onRunning
argument_list|(
parameter_list|()
lambda|->
name|isLoadingBuiltIn
operator|.
name|setValue
argument_list|(
literal|true
argument_list|)
argument_list|)
operator|.
name|onSuccess
argument_list|(
name|result
lambda|->
block|{
name|isLoadingBuiltIn
operator|.
name|setValue
argument_list|(
literal|false
argument_list|)
argument_list|;
name|addList
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"JabRef built in list"
argument_list|)
argument_list|,
name|result
argument_list|)
argument_list|;
name|selectLastJournalFile
argument_list|()
argument_list|;
block|}
block|)
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
end_class

begin_expr_stmt
name|BackgroundTask
operator|.
name|wrap
argument_list|(
parameter_list|()
lambda|->
block|{
if|if
condition|(
name|abbreviationsPreferences
operator|.
name|useIEEEAbbreviations
argument_list|()
condition|)
block|{
return|return
name|JournalAbbreviationLoader
operator|.
name|getOfficialIEEEAbbreviations
argument_list|()
return|;
block|}
else|else
block|{
return|return
name|JournalAbbreviationLoader
operator|.
name|getStandardIEEEAbbreviations
argument_list|()
return|;
block|}
block|}
argument_list|)
operator|.
name|onRunning
argument_list|(
parameter_list|()
lambda|->
name|isLoadingIeee
operator|.
name|setValue
argument_list|(
literal|true
argument_list|)
argument_list|)
operator|.
name|onSuccess
argument_list|(
name|result
lambda|->
block|{
name|isLoadingIeee
operator|.
name|setValue
argument_list|(
literal|false
argument_list|)
argument_list|;
name|addList
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"IEEE built in list"
argument_list|)
argument_list|,
name|result
argument_list|)
argument_list|;
end_expr_stmt

begin_expr_stmt
unit|})
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
end_expr_stmt

begin_function
unit|}      private
DECL|method|addList (String name, List<Abbreviation> abbreviations)
name|void
name|addList
parameter_list|(
name|String
name|name
parameter_list|,
name|List
argument_list|<
name|Abbreviation
argument_list|>
name|abbreviations
parameter_list|)
block|{
name|List
argument_list|<
name|AbbreviationViewModel
argument_list|>
name|builtInListViewModel
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|abbreviations
operator|.
name|forEach
argument_list|(
name|abbreviation
lambda|->
name|builtInListViewModel
operator|.
name|add
argument_list|(
operator|new
name|AbbreviationViewModel
argument_list|(
name|abbreviation
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|AbbreviationsFileViewModel
name|fileViewModel
init|=
operator|new
name|AbbreviationsFileViewModel
argument_list|(
name|builtInListViewModel
argument_list|,
name|name
argument_list|)
decl_stmt|;
name|journalFiles
operator|.
name|add
argument_list|(
name|fileViewModel
argument_list|)
expr_stmt|;
block|}
end_function

begin_comment
comment|/**      * Read all saved file paths and read their abbreviations      */
end_comment

begin_function
DECL|method|createFileObjects ()
specifier|public
name|void
name|createFileObjects
parameter_list|()
block|{
name|List
argument_list|<
name|String
argument_list|>
name|externalFiles
init|=
name|abbreviationsPreferences
operator|.
name|getExternalJournalLists
argument_list|()
decl_stmt|;
name|externalFiles
operator|.
name|forEach
argument_list|(
name|name
lambda|->
name|openFile
argument_list|(
name|Paths
operator|.
name|get
argument_list|(
name|name
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
end_function

begin_comment
comment|/**      * This method shall be used to add a new journal abbreviation file to the      * set of journal abbreviation files. It basically just calls the      * {@link #openFile(Path)}} method      */
end_comment

begin_function
DECL|method|addNewFile ()
specifier|public
name|void
name|addNewFile
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
name|TXT
argument_list|)
operator|.
name|build
argument_list|()
decl_stmt|;
name|dialogService
operator|.
name|showFileSaveDialog
argument_list|(
name|fileDialogConfiguration
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|this
operator|::
name|openFile
argument_list|)
expr_stmt|;
block|}
end_function

begin_comment
comment|/**      * Checks whether the file exists or if a new file should be opened.      * The file will be added to the set of journal abbreviation files.      * If the file also exists its abbreviations will be read and written      * to the abbreviations property.      *      * @param filePath to the file      */
end_comment

begin_function
DECL|method|openFile (Path filePath)
specifier|private
name|void
name|openFile
parameter_list|(
name|Path
name|filePath
parameter_list|)
block|{
name|AbbreviationsFileViewModel
name|abbreviationsFile
init|=
operator|new
name|AbbreviationsFileViewModel
argument_list|(
name|filePath
argument_list|)
decl_stmt|;
if|if
condition|(
name|journalFiles
operator|.
name|contains
argument_list|(
name|abbreviationsFile
argument_list|)
condition|)
block|{
name|dialogService
operator|.
name|showErrorDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Duplicated Journal File"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Journal file %s already added"
argument_list|,
name|filePath
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
return|return;
block|}
if|if
condition|(
name|abbreviationsFile
operator|.
name|exists
argument_list|()
condition|)
block|{
try|try
block|{
name|abbreviationsFile
operator|.
name|readAbbreviations
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|FileNotFoundException
name|e
parameter_list|)
block|{
name|logger
operator|.
name|debug
argument_list|(
name|e
operator|.
name|getLocalizedMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
name|journalFiles
operator|.
name|add
argument_list|(
name|abbreviationsFile
argument_list|)
expr_stmt|;
block|}
end_function

begin_function
DECL|method|openFile ()
specifier|public
name|void
name|openFile
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
name|TXT
argument_list|)
operator|.
name|build
argument_list|()
decl_stmt|;
name|dialogService
operator|.
name|showFileOpenDialog
argument_list|(
name|fileDialogConfiguration
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|this
operator|::
name|openFile
argument_list|)
expr_stmt|;
block|}
end_function

begin_comment
comment|/**      * This method removes the currently selected file from the set of      * journal abbreviation files. This will not remove existing files from      * the file system. The {@code activeFile} property will always change      * to the previous file in the {@code journalFiles} list property, except      * the first file is selected. If so the next file will be selected except if      * there are no more files than the {@code activeFile} property will be set      * to {@code null}.      */
end_comment

begin_function
DECL|method|removeCurrentFile ()
specifier|public
name|void
name|removeCurrentFile
parameter_list|()
block|{
if|if
condition|(
name|isFileRemovable
operator|.
name|get
argument_list|()
condition|)
block|{
name|journalFiles
operator|.
name|remove
argument_list|(
name|currentFile
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|journalFiles
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|currentFile
operator|.
name|set
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_function

begin_comment
comment|/**      * Method to add a new abbreviation to the abbreviations list property.      * It also sets the currentAbbreviation property to the new abbreviation.      *      * @param name         of the abbreviation object      * @param abbreviation of the abbreviation object      */
end_comment

begin_function
DECL|method|addAbbreviation (String name, String abbreviation)
specifier|public
name|void
name|addAbbreviation
parameter_list|(
name|String
name|name
parameter_list|,
name|String
name|abbreviation
parameter_list|)
block|{
name|Abbreviation
name|abbreviationObject
init|=
operator|new
name|Abbreviation
argument_list|(
name|name
argument_list|,
name|abbreviation
argument_list|)
decl_stmt|;
name|AbbreviationViewModel
name|abbreviationViewModel
init|=
operator|new
name|AbbreviationViewModel
argument_list|(
name|abbreviationObject
argument_list|)
decl_stmt|;
if|if
condition|(
name|abbreviations
operator|.
name|contains
argument_list|(
name|abbreviationViewModel
argument_list|)
condition|)
block|{
name|dialogService
operator|.
name|showErrorDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Duplicated Journal Abbreviation"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Abbreviation %s for journal %s already defined."
argument_list|,
name|abbreviation
argument_list|,
name|name
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|abbreviations
operator|.
name|add
argument_list|(
name|abbreviationViewModel
argument_list|)
expr_stmt|;
name|currentAbbreviation
operator|.
name|set
argument_list|(
name|abbreviationViewModel
argument_list|)
expr_stmt|;
name|shouldWriteLists
operator|=
literal|true
expr_stmt|;
block|}
block|}
end_function

begin_comment
comment|/**      * Method to change the currentAbbrevaition property to a new abbreviation.      *      * @param name         of the abbreviation object      * @param abbreviation of the abbreviation object      */
end_comment

begin_function
DECL|method|editAbbreviation (String name, String abbreviation)
specifier|public
name|void
name|editAbbreviation
parameter_list|(
name|String
name|name
parameter_list|,
name|String
name|abbreviation
parameter_list|)
block|{
if|if
condition|(
name|isAbbreviationEditableAndRemovable
operator|.
name|get
argument_list|()
condition|)
block|{
name|Abbreviation
name|abbreviationObject
init|=
operator|new
name|Abbreviation
argument_list|(
name|name
argument_list|,
name|abbreviation
argument_list|)
decl_stmt|;
name|AbbreviationViewModel
name|abbViewModel
init|=
operator|new
name|AbbreviationViewModel
argument_list|(
name|abbreviationObject
argument_list|)
decl_stmt|;
if|if
condition|(
name|abbreviations
operator|.
name|contains
argument_list|(
name|abbViewModel
argument_list|)
condition|)
block|{
if|if
condition|(
operator|!
name|abbViewModel
operator|.
name|equals
argument_list|(
name|currentAbbreviation
operator|.
name|get
argument_list|()
argument_list|)
condition|)
block|{
name|dialogService
operator|.
name|showErrorDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Duplicated Journal Abbreviation"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Abbreviation %s for journal %s already defined."
argument_list|,
name|abbreviation
argument_list|,
name|name
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|setCurrentAbbreviationNameAndAbbreviationIfValid
argument_list|(
name|name
argument_list|,
name|abbreviation
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|setCurrentAbbreviationNameAndAbbreviationIfValid
argument_list|(
name|name
argument_list|,
name|abbreviation
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_function

begin_comment
comment|/**      * Sets the name and the abbreviation of the {@code currentAbbreviation} property      * to the values of the {@code abbreviationsName} and {@code abbreviationsAbbreviation}      * properties.      */
end_comment

begin_function
DECL|method|setCurrentAbbreviationNameAndAbbreviationIfValid (String name, String abbreviation)
specifier|private
name|void
name|setCurrentAbbreviationNameAndAbbreviationIfValid
parameter_list|(
name|String
name|name
parameter_list|,
name|String
name|abbreviation
parameter_list|)
block|{
if|if
condition|(
name|name
operator|.
name|trim
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|dialogService
operator|.
name|showErrorDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Name cannot be empty"
argument_list|)
argument_list|)
expr_stmt|;
return|return;
block|}
elseif|else
if|if
condition|(
name|abbreviation
operator|.
name|trim
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|dialogService
operator|.
name|showErrorDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Abbreviation cannot be empty"
argument_list|)
argument_list|)
expr_stmt|;
return|return;
block|}
name|currentAbbreviation
operator|.
name|get
argument_list|()
operator|.
name|setName
argument_list|(
name|name
argument_list|)
expr_stmt|;
name|currentAbbreviation
operator|.
name|get
argument_list|()
operator|.
name|setAbbreviation
argument_list|(
name|abbreviation
argument_list|)
expr_stmt|;
name|shouldWriteLists
operator|=
literal|true
expr_stmt|;
block|}
end_function

begin_comment
comment|/**      * Method to delete the abbreviation set in the currentAbbreviation property.      * The currentAbbreviationProperty will be set to the previous or next abbreviation      * in the abbreviations property if applicable. Else it will be set to {@code null}      * if there are no abbreviations left.      */
end_comment

begin_function
DECL|method|deleteAbbreviation ()
specifier|public
name|void
name|deleteAbbreviation
parameter_list|()
block|{
if|if
condition|(
name|isAbbreviationEditableAndRemovable
operator|.
name|get
argument_list|()
condition|)
block|{
if|if
condition|(
operator|(
name|currentAbbreviation
operator|.
name|get
argument_list|()
operator|!=
literal|null
operator|)
operator|&&
operator|!
name|currentAbbreviation
operator|.
name|get
argument_list|()
operator|.
name|isPseudoAbbreviation
argument_list|()
condition|)
block|{
name|int
name|index
init|=
name|abbreviations
operator|.
name|indexOf
argument_list|(
name|currentAbbreviation
operator|.
name|get
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|index
operator|>
literal|1
condition|)
block|{
name|currentAbbreviation
operator|.
name|set
argument_list|(
name|abbreviations
operator|.
name|get
argument_list|(
name|index
operator|-
literal|1
argument_list|)
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
operator|(
name|index
operator|+
literal|1
operator|)
operator|<
name|abbreviationsCount
operator|.
name|get
argument_list|()
condition|)
block|{
name|currentAbbreviation
operator|.
name|set
argument_list|(
name|abbreviations
operator|.
name|get
argument_list|(
name|index
operator|+
literal|1
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|currentAbbreviation
operator|.
name|set
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
name|abbreviations
operator|.
name|remove
argument_list|(
name|index
argument_list|)
expr_stmt|;
name|shouldWriteLists
operator|=
literal|true
expr_stmt|;
block|}
block|}
block|}
end_function

begin_comment
comment|/**      * Calls the {@link AbbreviationsFileViewModel#writeOrCreate()} method for each file      * in the journalFiles property which will overwrite the existing files with      * the content of the abbreviations property of the AbbriviationsFile. Non      * existing files will be created.      */
end_comment

begin_function
DECL|method|saveJournalAbbreviationFiles ()
specifier|public
name|void
name|saveJournalAbbreviationFiles
parameter_list|()
block|{
name|journalFiles
operator|.
name|forEach
argument_list|(
name|file
lambda|->
block|{
try|try
block|{
name|file
operator|.
name|writeOrCreate
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|logger
operator|.
name|debug
argument_list|(
name|e
operator|.
name|getLocalizedMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
end_function

begin_comment
comment|/**      * This method stores all file paths of the files in the journalFiles property      * to the global JabRef preferences. Pseudo abbreviation files will not be stored.      */
end_comment

begin_function
DECL|method|saveExternalFilesList ()
specifier|private
name|void
name|saveExternalFilesList
parameter_list|()
block|{
name|List
argument_list|<
name|String
argument_list|>
name|extFiles
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|journalFiles
operator|.
name|forEach
argument_list|(
name|file
lambda|->
block|{
if|if
condition|(
operator|!
name|file
operator|.
name|isBuiltInListProperty
argument_list|()
operator|.
name|get
argument_list|()
condition|)
block|{
name|file
operator|.
name|getAbsolutePath
argument_list|()
operator|.
name|ifPresent
argument_list|(
name|path
lambda|->
name|extFiles
operator|.
name|add
argument_list|(
name|path
operator|.
name|toAbsolutePath
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|abbreviationsPreferences
operator|.
name|setExternalJournalLists
argument_list|(
name|extFiles
argument_list|)
expr_stmt|;
block|}
end_function

begin_comment
comment|/**      * This will set the {@code currentFile} property to the {@link AbbreviationsFileViewModel} object      * that was added to the {@code journalFiles} list property lastly. If there are no files in the list      * property this methode will do nothing as the {@code currentFile} property is already {@code null}.      */
end_comment

begin_function
DECL|method|selectLastJournalFile ()
specifier|public
name|void
name|selectLastJournalFile
parameter_list|()
block|{
if|if
condition|(
name|journalFiles
operator|.
name|size
argument_list|()
operator|>
literal|0
condition|)
block|{
name|currentFile
operator|.
name|set
argument_list|(
name|journalFilesProperty
argument_list|()
operator|.
name|get
argument_list|(
name|journalFilesProperty
argument_list|()
operator|.
name|size
argument_list|()
operator|-
literal|1
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_function

begin_comment
comment|/**      * This method first saves all external files to its internal list, then writes all abbreviations      * to their files and finally updates the abbreviations auto complete. It basically calls      * {@link #saveExternalFilesList()}, {@link #saveJournalAbbreviationFiles() } and finally      * {@link JournalAbbreviationLoader#update(JournalAbbreviationPreferences)}.      */
end_comment

begin_function
DECL|method|saveEverythingAndUpdateAutoCompleter ()
specifier|public
name|void
name|saveEverythingAndUpdateAutoCompleter
parameter_list|()
block|{
name|BackgroundTask
operator|.
name|wrap
argument_list|(
parameter_list|()
lambda|->
block|{
name|saveExternalFilesList
argument_list|()
expr_stmt|;
if|if
condition|(
name|shouldWriteLists
condition|)
block|{
name|saveJournalAbbreviationFiles
argument_list|()
expr_stmt|;
name|shouldWriteLists
operator|=
literal|false
expr_stmt|;
block|}
comment|// Update journal abbreviation loader
name|journalAbbreviationLoader
operator|.
name|update
argument_list|(
name|abbreviationsPreferences
argument_list|)
expr_stmt|;
name|preferences
operator|.
name|storeJournalAbbreviationPreferences
argument_list|(
name|abbreviationsPreferences
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
block|}
end_function

begin_function
DECL|method|journalFilesProperty ()
specifier|public
name|SimpleListProperty
argument_list|<
name|AbbreviationsFileViewModel
argument_list|>
name|journalFilesProperty
parameter_list|()
block|{
return|return
name|this
operator|.
name|journalFiles
return|;
block|}
end_function

begin_function
DECL|method|abbreviationsProperty ()
specifier|public
name|SimpleListProperty
argument_list|<
name|AbbreviationViewModel
argument_list|>
name|abbreviationsProperty
parameter_list|()
block|{
return|return
name|this
operator|.
name|abbreviations
return|;
block|}
end_function

begin_function
DECL|method|abbreviationsCountProperty ()
specifier|public
name|SimpleIntegerProperty
name|abbreviationsCountProperty
parameter_list|()
block|{
return|return
name|this
operator|.
name|abbreviationsCount
return|;
block|}
end_function

begin_function
DECL|method|currentFileProperty ()
specifier|public
name|SimpleObjectProperty
argument_list|<
name|AbbreviationsFileViewModel
argument_list|>
name|currentFileProperty
parameter_list|()
block|{
return|return
name|this
operator|.
name|currentFile
return|;
block|}
end_function

begin_function
DECL|method|currentAbbreviationProperty ()
specifier|public
name|SimpleObjectProperty
argument_list|<
name|AbbreviationViewModel
argument_list|>
name|currentAbbreviationProperty
parameter_list|()
block|{
return|return
name|this
operator|.
name|currentAbbreviation
return|;
block|}
end_function

begin_function
DECL|method|isAbbreviationEditableAndRemovableProperty ()
specifier|public
name|SimpleBooleanProperty
name|isAbbreviationEditableAndRemovableProperty
parameter_list|()
block|{
return|return
name|this
operator|.
name|isAbbreviationEditableAndRemovable
return|;
block|}
end_function

begin_function
DECL|method|isFileRemovableProperty ()
specifier|public
name|SimpleBooleanProperty
name|isFileRemovableProperty
parameter_list|()
block|{
return|return
name|this
operator|.
name|isFileRemovable
return|;
block|}
end_function

begin_function
DECL|method|addAbbreviation ()
specifier|public
name|void
name|addAbbreviation
parameter_list|()
block|{
name|addAbbreviation
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Name"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Abbreviation"
argument_list|)
argument_list|)
expr_stmt|;
block|}
end_function

begin_function
DECL|method|init ()
specifier|public
name|void
name|init
parameter_list|()
block|{
name|createFileObjects
argument_list|()
expr_stmt|;
name|selectLastJournalFile
argument_list|()
expr_stmt|;
name|addBuiltInLists
argument_list|()
expr_stmt|;
block|}
end_function

unit|}
end_unit

