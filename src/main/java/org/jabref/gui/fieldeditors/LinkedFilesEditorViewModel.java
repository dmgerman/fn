begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.fieldeditors
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|fieldeditors
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
name|net
operator|.
name|MalformedURLException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URL
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
name|ListProperty
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
name|SimpleListProperty
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
name|autocompleter
operator|.
name|AutoCompleteSuggestionProvider
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
name|AutoSetFileLinksUtil
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
name|CustomExternalFileType
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
name|ExternalFileType
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
name|externalfiletype
operator|.
name|UnknownExternalFileType
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
name|BindingsHelper
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
name|importer
operator|.
name|FulltextFetchers
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
name|integrity
operator|.
name|FieldCheckers
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
name|entry
operator|.
name|FileFieldParser
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
name|FileFieldWriter
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
name|LinkedFile
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
name|FileHelper
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
DECL|class|LinkedFilesEditorViewModel
specifier|public
class|class
name|LinkedFilesEditorViewModel
extends|extends
name|AbstractEditorViewModel
block|{
DECL|field|files
specifier|private
specifier|final
name|ListProperty
argument_list|<
name|LinkedFileViewModel
argument_list|>
name|files
init|=
operator|new
name|SimpleListProperty
argument_list|<>
argument_list|(
name|FXCollections
operator|.
name|observableArrayList
argument_list|(
name|LinkedFileViewModel
operator|::
name|getObservables
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|fulltextLookupInProgress
specifier|private
specifier|final
name|BooleanProperty
name|fulltextLookupInProgress
init|=
operator|new
name|SimpleBooleanProperty
argument_list|(
literal|false
argument_list|)
decl_stmt|;
DECL|field|dialogService
specifier|private
specifier|final
name|DialogService
name|dialogService
decl_stmt|;
DECL|field|databaseContext
specifier|private
specifier|final
name|BibDatabaseContext
name|databaseContext
decl_stmt|;
DECL|field|taskExecutor
specifier|private
specifier|final
name|TaskExecutor
name|taskExecutor
decl_stmt|;
DECL|field|preferences
specifier|private
specifier|final
name|JabRefPreferences
name|preferences
decl_stmt|;
DECL|field|externalFileTypes
specifier|private
specifier|final
name|ExternalFileTypes
name|externalFileTypes
init|=
name|ExternalFileTypes
operator|.
name|getInstance
argument_list|()
decl_stmt|;
DECL|method|LinkedFilesEditorViewModel (String fieldName, AutoCompleteSuggestionProvider<?> suggestionProvider, DialogService dialogService, BibDatabaseContext databaseContext, TaskExecutor taskExecutor, FieldCheckers fieldCheckers, JabRefPreferences preferences)
specifier|public
name|LinkedFilesEditorViewModel
parameter_list|(
name|String
name|fieldName
parameter_list|,
name|AutoCompleteSuggestionProvider
argument_list|<
name|?
argument_list|>
name|suggestionProvider
parameter_list|,
name|DialogService
name|dialogService
parameter_list|,
name|BibDatabaseContext
name|databaseContext
parameter_list|,
name|TaskExecutor
name|taskExecutor
parameter_list|,
name|FieldCheckers
name|fieldCheckers
parameter_list|,
name|JabRefPreferences
name|preferences
parameter_list|)
block|{
name|super
argument_list|(
name|fieldName
argument_list|,
name|suggestionProvider
argument_list|,
name|fieldCheckers
argument_list|)
expr_stmt|;
name|this
operator|.
name|dialogService
operator|=
name|dialogService
expr_stmt|;
name|this
operator|.
name|databaseContext
operator|=
name|databaseContext
expr_stmt|;
name|this
operator|.
name|taskExecutor
operator|=
name|taskExecutor
expr_stmt|;
name|this
operator|.
name|preferences
operator|=
name|preferences
expr_stmt|;
name|BindingsHelper
operator|.
name|bindContentBidirectional
argument_list|(
name|files
argument_list|,
name|text
argument_list|,
name|LinkedFilesEditorViewModel
operator|::
name|getStringRepresentation
argument_list|,
name|this
operator|::
name|parseToFileViewModel
argument_list|)
expr_stmt|;
block|}
DECL|method|getStringRepresentation (List<LinkedFileViewModel> files)
specifier|private
specifier|static
name|String
name|getStringRepresentation
parameter_list|(
name|List
argument_list|<
name|LinkedFileViewModel
argument_list|>
name|files
parameter_list|)
block|{
comment|// Only serialize linked files, not the ones that are automatically found
name|List
argument_list|<
name|LinkedFile
argument_list|>
name|filesToSerialize
init|=
name|files
operator|.
name|stream
argument_list|()
operator|.
name|filter
argument_list|(
name|file
lambda|->
operator|!
name|file
operator|.
name|isAutomaticallyFound
argument_list|()
argument_list|)
operator|.
name|map
argument_list|(
name|LinkedFileViewModel
operator|::
name|getFile
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
return|return
name|FileFieldWriter
operator|.
name|getStringRepresentation
argument_list|(
name|filesToSerialize
argument_list|)
return|;
block|}
comment|/**      * Creates an instance of {@link LinkedFile} based on the given file.      * We try to guess the file type and relativize the path against the given file directories.      *      * TODO: Move this method to {@link LinkedFile} as soon as {@link CustomExternalFileType} lives in model.      */
DECL|method|fromFile (Path file, List<Path> fileDirectories, ExternalFileTypes externalFileTypesFile)
specifier|public
specifier|static
name|LinkedFile
name|fromFile
parameter_list|(
name|Path
name|file
parameter_list|,
name|List
argument_list|<
name|Path
argument_list|>
name|fileDirectories
parameter_list|,
name|ExternalFileTypes
name|externalFileTypesFile
parameter_list|)
block|{
name|String
name|fileExtension
init|=
name|FileHelper
operator|.
name|getFileExtension
argument_list|(
name|file
argument_list|)
operator|.
name|orElse
argument_list|(
literal|""
argument_list|)
decl_stmt|;
name|ExternalFileType
name|suggestedFileType
init|=
name|externalFileTypesFile
operator|.
name|getExternalFileTypeByExt
argument_list|(
name|fileExtension
argument_list|)
operator|.
name|orElse
argument_list|(
operator|new
name|UnknownExternalFileType
argument_list|(
name|fileExtension
argument_list|)
argument_list|)
decl_stmt|;
name|Path
name|relativePath
init|=
name|FileUtil
operator|.
name|relativize
argument_list|(
name|file
argument_list|,
name|fileDirectories
argument_list|)
decl_stmt|;
return|return
operator|new
name|LinkedFile
argument_list|(
literal|""
argument_list|,
name|relativePath
operator|.
name|toString
argument_list|()
argument_list|,
name|suggestedFileType
operator|.
name|getName
argument_list|()
argument_list|)
return|;
block|}
DECL|method|fromFile (Path file)
specifier|public
name|LinkedFileViewModel
name|fromFile
parameter_list|(
name|Path
name|file
parameter_list|)
block|{
name|List
argument_list|<
name|Path
argument_list|>
name|fileDirectories
init|=
name|databaseContext
operator|.
name|getFileDirectoriesAsPaths
argument_list|(
name|preferences
operator|.
name|getFilePreferences
argument_list|()
argument_list|)
decl_stmt|;
name|LinkedFile
name|linkedFile
init|=
name|fromFile
argument_list|(
name|file
argument_list|,
name|fileDirectories
argument_list|,
name|externalFileTypes
argument_list|)
decl_stmt|;
return|return
operator|new
name|LinkedFileViewModel
argument_list|(
name|linkedFile
argument_list|,
name|entry
argument_list|,
name|databaseContext
argument_list|,
name|taskExecutor
argument_list|,
name|dialogService
argument_list|,
name|preferences
argument_list|,
name|externalFileTypes
argument_list|)
return|;
block|}
DECL|method|isFulltextLookupInProgress ()
specifier|public
name|boolean
name|isFulltextLookupInProgress
parameter_list|()
block|{
return|return
name|fulltextLookupInProgress
operator|.
name|get
argument_list|()
return|;
block|}
DECL|method|fulltextLookupInProgressProperty ()
specifier|public
name|BooleanProperty
name|fulltextLookupInProgressProperty
parameter_list|()
block|{
return|return
name|fulltextLookupInProgress
return|;
block|}
DECL|method|parseToFileViewModel (String stringValue)
specifier|private
name|List
argument_list|<
name|LinkedFileViewModel
argument_list|>
name|parseToFileViewModel
parameter_list|(
name|String
name|stringValue
parameter_list|)
block|{
return|return
name|FileFieldParser
operator|.
name|parse
argument_list|(
name|stringValue
argument_list|)
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|linkedFile
lambda|->
operator|new
name|LinkedFileViewModel
argument_list|(
name|linkedFile
argument_list|,
name|entry
argument_list|,
name|databaseContext
argument_list|,
name|taskExecutor
argument_list|,
name|dialogService
argument_list|,
name|preferences
argument_list|,
name|externalFileTypes
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
return|;
block|}
DECL|method|getFiles ()
specifier|public
name|ObservableList
argument_list|<
name|LinkedFileViewModel
argument_list|>
name|getFiles
parameter_list|()
block|{
return|return
name|files
operator|.
name|get
argument_list|()
return|;
block|}
DECL|method|filesProperty ()
specifier|public
name|ListProperty
argument_list|<
name|LinkedFileViewModel
argument_list|>
name|filesProperty
parameter_list|()
block|{
return|return
name|files
return|;
block|}
DECL|method|addNewFile ()
specifier|public
name|void
name|addNewFile
parameter_list|()
block|{
name|Path
name|workingDirectory
init|=
name|databaseContext
operator|.
name|getFirstExistingFileDir
argument_list|(
name|preferences
operator|.
name|getFilePreferences
argument_list|()
argument_list|)
operator|.
name|orElse
argument_list|(
name|Paths
operator|.
name|get
argument_list|(
name|preferences
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|WORKING_DIRECTORY
argument_list|)
argument_list|)
argument_list|)
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
name|withInitialDirectory
argument_list|(
name|workingDirectory
argument_list|)
operator|.
name|build
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|Path
argument_list|>
name|fileDirectories
init|=
name|databaseContext
operator|.
name|getFileDirectoriesAsPaths
argument_list|(
name|preferences
operator|.
name|getFilePreferences
argument_list|()
argument_list|)
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
name|newFile
lambda|->
block|{
name|LinkedFile
name|newLinkedFile
init|=
name|fromFile
argument_list|(
name|newFile
argument_list|,
name|fileDirectories
argument_list|,
name|externalFileTypes
argument_list|)
decl_stmt|;
name|files
operator|.
name|add
argument_list|(
operator|new
name|LinkedFileViewModel
argument_list|(
name|newLinkedFile
argument_list|,
name|entry
argument_list|,
name|databaseContext
argument_list|,
name|taskExecutor
argument_list|,
name|dialogService
argument_list|,
name|preferences
argument_list|,
name|externalFileTypes
argument_list|)
argument_list|)
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|bindToEntry (BibEntry entry)
specifier|public
name|void
name|bindToEntry
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
name|super
operator|.
name|bindToEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
if|if
condition|(
name|entry
operator|!=
literal|null
condition|)
block|{
name|BackgroundTask
argument_list|<
name|List
argument_list|<
name|LinkedFileViewModel
argument_list|>
argument_list|>
name|findAssociatedNotLinkedFiles
init|=
name|BackgroundTask
operator|.
name|wrap
argument_list|(
parameter_list|()
lambda|->
name|findAssociatedNotLinkedFiles
argument_list|(
name|entry
argument_list|)
argument_list|)
operator|.
name|onSuccess
argument_list|(
name|files
operator|::
name|addAll
argument_list|)
decl_stmt|;
name|taskExecutor
operator|.
name|execute
argument_list|(
name|findAssociatedNotLinkedFiles
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Find files that are probably associated  to the given entry but not yet linked.      */
DECL|method|findAssociatedNotLinkedFiles (BibEntry entry)
specifier|private
name|List
argument_list|<
name|LinkedFileViewModel
argument_list|>
name|findAssociatedNotLinkedFiles
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
name|List
argument_list|<
name|LinkedFileViewModel
argument_list|>
name|result
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|AutoSetFileLinksUtil
name|util
init|=
operator|new
name|AutoSetFileLinksUtil
argument_list|(
name|databaseContext
argument_list|,
name|preferences
operator|.
name|getFilePreferences
argument_list|()
argument_list|,
name|preferences
operator|.
name|getAutoLinkPreferences
argument_list|()
argument_list|,
name|ExternalFileTypes
operator|.
name|getInstance
argument_list|()
argument_list|)
decl_stmt|;
try|try
block|{
name|List
argument_list|<
name|LinkedFile
argument_list|>
name|linkedFiles
init|=
name|util
operator|.
name|findAssociatedNotLinkedFiles
argument_list|(
name|entry
argument_list|)
decl_stmt|;
for|for
control|(
name|LinkedFile
name|linkedFile
range|:
name|linkedFiles
control|)
block|{
name|LinkedFileViewModel
name|newLinkedFile
init|=
operator|new
name|LinkedFileViewModel
argument_list|(
name|linkedFile
argument_list|,
name|entry
argument_list|,
name|databaseContext
argument_list|,
name|taskExecutor
argument_list|,
name|dialogService
argument_list|,
name|preferences
argument_list|,
name|externalFileTypes
argument_list|)
decl_stmt|;
name|newLinkedFile
operator|.
name|markAsAutomaticallyFound
argument_list|()
expr_stmt|;
name|result
operator|.
name|add
argument_list|(
name|newLinkedFile
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
name|dialogService
operator|.
name|showErrorDialogAndWait
argument_list|(
literal|"Error accessing the file system"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
return|return
name|result
return|;
block|}
DECL|method|fetchFulltext ()
specifier|public
name|void
name|fetchFulltext
parameter_list|()
block|{
name|FulltextFetchers
name|fetcher
init|=
operator|new
name|FulltextFetchers
argument_list|(
name|preferences
operator|.
name|getImportFormatPreferences
argument_list|()
argument_list|)
decl_stmt|;
name|BackgroundTask
operator|.
name|wrap
argument_list|(
parameter_list|()
lambda|->
name|fetcher
operator|.
name|findFullTextPDF
argument_list|(
name|entry
argument_list|)
argument_list|)
operator|.
name|onRunning
argument_list|(
parameter_list|()
lambda|->
name|fulltextLookupInProgress
operator|.
name|setValue
argument_list|(
literal|true
argument_list|)
argument_list|)
operator|.
name|onFinished
argument_list|(
parameter_list|()
lambda|->
name|fulltextLookupInProgress
operator|.
name|setValue
argument_list|(
literal|false
argument_list|)
argument_list|)
operator|.
name|onSuccess
argument_list|(
name|url
lambda|->
block|{
lambda|if (url.isPresent(
argument_list|)
block|)
block|{
name|addFromURL
argument_list|(
name|url
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|dialogService
operator|.
name|notify
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"No full text document found"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

begin_expr_stmt
unit|)
operator|.
name|executeWith
argument_list|(
name|taskExecutor
argument_list|)
expr_stmt|;
end_expr_stmt

begin_function
unit|}      public
DECL|method|addFromURL ()
name|void
name|addFromURL
parameter_list|()
block|{
name|Optional
argument_list|<
name|String
argument_list|>
name|urlText
init|=
name|dialogService
operator|.
name|showInputDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Download file"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Enter URL to download"
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|urlText
operator|.
name|isPresent
argument_list|()
condition|)
block|{
try|try
block|{
name|URL
name|url
init|=
operator|new
name|URL
argument_list|(
name|urlText
operator|.
name|get
argument_list|()
argument_list|)
decl_stmt|;
name|addFromURL
argument_list|(
name|url
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|MalformedURLException
name|exception
parameter_list|)
block|{
name|dialogService
operator|.
name|showErrorDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Invalid URL"
argument_list|)
argument_list|,
name|exception
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_function

begin_function
DECL|method|addFromURL (URL url)
specifier|private
name|void
name|addFromURL
parameter_list|(
name|URL
name|url
parameter_list|)
block|{
name|LinkedFileViewModel
name|onlineFile
init|=
operator|new
name|LinkedFileViewModel
argument_list|(
operator|new
name|LinkedFile
argument_list|(
name|url
argument_list|,
literal|""
argument_list|)
argument_list|,
name|entry
argument_list|,
name|databaseContext
argument_list|,
name|taskExecutor
argument_list|,
name|dialogService
argument_list|,
name|preferences
argument_list|,
name|externalFileTypes
argument_list|)
decl_stmt|;
name|files
operator|.
name|add
argument_list|(
name|onlineFile
argument_list|)
expr_stmt|;
name|onlineFile
operator|.
name|download
argument_list|()
expr_stmt|;
block|}
end_function

begin_function
DECL|method|deleteFile (LinkedFileViewModel file)
specifier|public
name|void
name|deleteFile
parameter_list|(
name|LinkedFileViewModel
name|file
parameter_list|)
block|{
if|if
condition|(
name|file
operator|.
name|getFile
argument_list|()
operator|.
name|isOnlineLink
argument_list|()
condition|)
block|{
name|removeFileLink
argument_list|(
name|file
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|boolean
name|deleteSuccessful
init|=
name|file
operator|.
name|delete
argument_list|()
decl_stmt|;
if|if
condition|(
name|deleteSuccessful
condition|)
block|{
name|files
operator|.
name|remove
argument_list|(
name|file
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_function

begin_function
DECL|method|removeFileLink (LinkedFileViewModel file)
specifier|public
name|void
name|removeFileLink
parameter_list|(
name|LinkedFileViewModel
name|file
parameter_list|)
block|{
name|files
operator|.
name|remove
argument_list|(
name|file
argument_list|)
expr_stmt|;
block|}
end_function

unit|}
end_unit

