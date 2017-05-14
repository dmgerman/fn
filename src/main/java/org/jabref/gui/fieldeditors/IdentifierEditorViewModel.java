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
name|util
operator|.
name|Optional
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
name|desktop
operator|.
name|JabRefDesktop
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
name|mergeentries
operator|.
name|FetchAndMergeEntry
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
name|importer
operator|.
name|WebFetchers
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
name|util
operator|.
name|IdentifierParser
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
name|FieldName
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
name|identifier
operator|.
name|Identifier
import|;
end_import

begin_import
import|import
name|org
operator|.
name|fxmisc
operator|.
name|easybind
operator|.
name|EasyBind
import|;
end_import

begin_class
DECL|class|IdentifierEditorViewModel
specifier|public
class|class
name|IdentifierEditorViewModel
extends|extends
name|AbstractEditorViewModel
block|{
DECL|field|fieldName
specifier|private
specifier|final
name|String
name|fieldName
decl_stmt|;
DECL|field|validIdentifierIsNotPresent
specifier|private
name|BooleanProperty
name|validIdentifierIsNotPresent
init|=
operator|new
name|SimpleBooleanProperty
argument_list|(
literal|true
argument_list|)
decl_stmt|;
DECL|field|identifierLookupInProgress
specifier|private
name|BooleanProperty
name|identifierLookupInProgress
init|=
operator|new
name|SimpleBooleanProperty
argument_list|(
literal|false
argument_list|)
decl_stmt|;
DECL|field|idFetcherAvailable
specifier|private
name|BooleanProperty
name|idFetcherAvailable
init|=
operator|new
name|SimpleBooleanProperty
argument_list|(
literal|true
argument_list|)
decl_stmt|;
DECL|field|identifier
specifier|private
name|ObjectProperty
argument_list|<
name|Optional
argument_list|<
name|?
extends|extends
name|Identifier
argument_list|>
argument_list|>
name|identifier
init|=
operator|new
name|SimpleObjectProperty
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|taskExecutor
specifier|private
name|TaskExecutor
name|taskExecutor
decl_stmt|;
DECL|field|dialogService
specifier|private
name|DialogService
name|dialogService
decl_stmt|;
DECL|method|IdentifierEditorViewModel (String fieldName, TaskExecutor taskExecutor, DialogService dialogService)
specifier|public
name|IdentifierEditorViewModel
parameter_list|(
name|String
name|fieldName
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
name|fieldName
operator|=
name|fieldName
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
name|identifier
operator|.
name|bind
argument_list|(
name|EasyBind
operator|.
name|map
argument_list|(
name|text
argument_list|,
name|input
lambda|->
name|IdentifierParser
operator|.
name|parse
argument_list|(
name|fieldName
argument_list|,
name|input
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|validIdentifierIsNotPresent
operator|.
name|bind
argument_list|(
name|EasyBind
operator|.
name|map
argument_list|(
name|identifier
argument_list|,
name|parsedIdentifier
lambda|->
operator|!
name|parsedIdentifier
operator|.
name|isPresent
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|idFetcherAvailable
operator|.
name|setValue
argument_list|(
name|WebFetchers
operator|.
name|getIdFetcherForField
argument_list|(
name|fieldName
argument_list|)
operator|.
name|isPresent
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|isIdFetcherAvailable ()
specifier|public
name|boolean
name|isIdFetcherAvailable
parameter_list|()
block|{
return|return
name|idFetcherAvailable
operator|.
name|get
argument_list|()
return|;
block|}
DECL|method|idFetcherAvailableProperty ()
specifier|public
name|BooleanProperty
name|idFetcherAvailableProperty
parameter_list|()
block|{
return|return
name|idFetcherAvailable
return|;
block|}
DECL|method|getValidIdentifierIsNotPresent ()
specifier|public
name|boolean
name|getValidIdentifierIsNotPresent
parameter_list|()
block|{
return|return
name|validIdentifierIsNotPresent
operator|.
name|get
argument_list|()
return|;
block|}
DECL|method|validIdentifierIsNotPresentProperty ()
specifier|public
name|BooleanProperty
name|validIdentifierIsNotPresentProperty
parameter_list|()
block|{
return|return
name|validIdentifierIsNotPresent
return|;
block|}
DECL|method|openExternalLink ()
specifier|public
name|void
name|openExternalLink
parameter_list|()
block|{
name|identifier
operator|.
name|get
argument_list|()
operator|.
name|flatMap
argument_list|(
name|Identifier
operator|::
name|getExternalURI
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|url
lambda|->
block|{
try|try
block|{
name|JabRefDesktop
operator|.
name|openBrowser
argument_list|(
name|url
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
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
literal|"Unable to open link."
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
DECL|method|getIdentifierLookupInProgress ()
specifier|public
name|boolean
name|getIdentifierLookupInProgress
parameter_list|()
block|{
return|return
name|identifierLookupInProgress
operator|.
name|get
argument_list|()
return|;
block|}
DECL|method|identifierLookupInProgressProperty ()
specifier|public
name|BooleanProperty
name|identifierLookupInProgressProperty
parameter_list|()
block|{
return|return
name|identifierLookupInProgress
return|;
block|}
DECL|method|fetchInformationByIdentifier (BibEntry entry)
specifier|public
name|FetchAndMergeEntry
name|fetchInformationByIdentifier
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
return|return
operator|new
name|FetchAndMergeEntry
argument_list|(
name|entry
argument_list|,
name|fieldName
argument_list|)
return|;
block|}
DECL|method|lookupIdentifier (BibEntry entry)
specifier|public
name|void
name|lookupIdentifier
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
name|WebFetchers
operator|.
name|getIdFetcherForField
argument_list|(
name|fieldName
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|idFetcher
lambda|->
block|{
name|BackgroundTask
operator|.
name|wrap
argument_list|(
parameter_list|()
lambda|->
name|idFetcher
operator|.
name|findIdentifier
argument_list|(
name|entry
argument_list|)
argument_list|)
operator|.
name|onRunning
argument_list|(
parameter_list|()
lambda|->
name|identifierLookupInProgress
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
name|identifierLookupInProgress
operator|.
name|setValue
argument_list|(
literal|false
argument_list|)
argument_list|)
operator|.
name|onSuccess
argument_list|(
name|identifier
lambda|->
block|{
lambda|if (identifier.isPresent(
argument_list|)
argument_list|)
block|{
name|entry
operator|.
name|setField
argument_list|(
name|fieldName
argument_list|,
name|identifier
operator|.
name|get
argument_list|()
operator|.
name|getNormalized
argument_list|()
argument_list|)
block|;                         }
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
literal|"No %0 found"
argument_list|,
name|FieldName
operator|.
name|getDisplayName
argument_list|(
name|fieldName
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
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

begin_empty_stmt
unit|})
empty_stmt|;
end_empty_stmt

unit|} }
end_unit
