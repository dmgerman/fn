begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.libraryproperties
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|libraryproperties
package|;
end_package

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
name|Paths
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
name|javax
operator|.
name|inject
operator|.
name|Inject
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|event
operator|.
name|ActionEvent
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|fxml
operator|.
name|FXML
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
name|Button
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
name|CheckBox
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
name|ComboBox
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
name|Label
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
name|TextField
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
name|SaveOrderConfigDisplayView
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
name|cleanup
operator|.
name|FieldFormatterCleanupsPanel
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
name|BaseDialog
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
name|cleanup
operator|.
name|Cleanups
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
name|metadata
operator|.
name|MetaData
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
name|metadata
operator|.
name|SaveOrderConfig
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
name|com
operator|.
name|airhacks
operator|.
name|afterburner
operator|.
name|views
operator|.
name|ViewLoader
import|;
end_import

begin_class
DECL|class|LibraryPropertiesDialogView
specifier|public
class|class
name|LibraryPropertiesDialogView
extends|extends
name|BaseDialog
argument_list|<
name|Void
argument_list|>
block|{
DECL|field|contentVbox
annotation|@
name|FXML
specifier|private
name|VBox
name|contentVbox
decl_stmt|;
DECL|field|encoding
annotation|@
name|FXML
specifier|private
name|ComboBox
argument_list|<
name|Charset
argument_list|>
name|encoding
decl_stmt|;
DECL|field|generalFileDirectory
annotation|@
name|FXML
specifier|private
name|TextField
name|generalFileDirectory
decl_stmt|;
DECL|field|browseGeneralFileDir
annotation|@
name|FXML
specifier|private
name|Button
name|browseGeneralFileDir
decl_stmt|;
DECL|field|userSpecificFileDirectory
annotation|@
name|FXML
specifier|private
name|TextField
name|userSpecificFileDirectory
decl_stmt|;
DECL|field|browseUserSpefiicFileDir
annotation|@
name|FXML
specifier|private
name|Button
name|browseUserSpefiicFileDir
decl_stmt|;
DECL|field|laTexFileDirectory
annotation|@
name|FXML
specifier|private
name|TextField
name|laTexFileDirectory
decl_stmt|;
DECL|field|protect
annotation|@
name|FXML
specifier|private
name|CheckBox
name|protect
decl_stmt|;
DECL|field|preferencesService
annotation|@
name|Inject
specifier|private
name|PreferencesService
name|preferencesService
decl_stmt|;
DECL|field|panel
specifier|private
name|BasePanel
name|panel
decl_stmt|;
DECL|field|viewModel
specifier|private
name|LibraryPropertiesDialogViewModel
name|viewModel
decl_stmt|;
DECL|field|dialogService
specifier|private
specifier|final
name|DialogService
name|dialogService
decl_stmt|;
DECL|field|fieldFormatterCleanupsPanel
specifier|private
name|FieldFormatterCleanupsPanel
name|fieldFormatterCleanupsPanel
decl_stmt|;
DECL|field|saveOrderConfigDisplayView
specifier|private
name|SaveOrderConfigDisplayView
name|saveOrderConfigDisplayView
decl_stmt|;
DECL|field|oldSaveOrderConfig
specifier|private
name|SaveOrderConfig
name|oldSaveOrderConfig
decl_stmt|;
DECL|method|LibraryPropertiesDialogView (BasePanel panel, DialogService dialogService)
specifier|public
name|LibraryPropertiesDialogView
parameter_list|(
name|BasePanel
name|panel
parameter_list|,
name|DialogService
name|dialogService
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
name|panel
operator|=
name|panel
expr_stmt|;
name|ViewLoader
operator|.
name|view
argument_list|(
name|this
argument_list|)
operator|.
name|load
argument_list|()
operator|.
name|setAsDialogPane
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|setResultConverter
argument_list|(
name|btn
lambda|->
block|{
if|if
condition|(
name|btn
operator|==
name|ButtonType
operator|.
name|OK
condition|)
block|{
name|storeSettings
argument_list|()
expr_stmt|;
block|}
return|return
literal|null
return|;
block|}
argument_list|)
expr_stmt|;
name|setTitle
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Library properties"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|FXML
DECL|method|initialize ()
specifier|private
name|void
name|initialize
parameter_list|()
block|{
name|viewModel
operator|=
operator|new
name|LibraryPropertiesDialogViewModel
argument_list|(
name|panel
argument_list|,
name|dialogService
argument_list|,
name|preferencesService
argument_list|)
expr_stmt|;
name|generalFileDirectory
operator|.
name|textProperty
argument_list|()
operator|.
name|bindBidirectional
argument_list|(
name|viewModel
operator|.
name|generalFileDirectoryPropertyProperty
argument_list|()
argument_list|)
expr_stmt|;
name|userSpecificFileDirectory
operator|.
name|textProperty
argument_list|()
operator|.
name|bindBidirectional
argument_list|(
name|viewModel
operator|.
name|userSpecificFileDirectoryProperty
argument_list|()
argument_list|)
expr_stmt|;
name|laTexFileDirectory
operator|.
name|textProperty
argument_list|()
operator|.
name|bindBidirectional
argument_list|(
name|viewModel
operator|.
name|laTexFileDirectoryProperty
argument_list|()
argument_list|)
expr_stmt|;
name|encoding
operator|.
name|itemsProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|viewModel
operator|.
name|encodingsProperty
argument_list|()
argument_list|)
expr_stmt|;
name|encoding
operator|.
name|valueProperty
argument_list|()
operator|.
name|bindBidirectional
argument_list|(
name|viewModel
operator|.
name|selectedEncodingProperty
argument_list|()
argument_list|)
expr_stmt|;
name|encoding
operator|.
name|disableProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|viewModel
operator|.
name|encodingDisableProperty
argument_list|()
argument_list|)
expr_stmt|;
name|protect
operator|.
name|disableProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|viewModel
operator|.
name|protectDisableProperty
argument_list|()
argument_list|)
expr_stmt|;
name|saveOrderConfigDisplayView
operator|=
operator|new
name|SaveOrderConfigDisplayView
argument_list|()
expr_stmt|;
name|Optional
argument_list|<
name|SaveOrderConfig
argument_list|>
name|storedSaveOrderConfig
init|=
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
operator|.
name|getMetaData
argument_list|()
operator|.
name|getSaveOrderConfig
argument_list|()
decl_stmt|;
name|oldSaveOrderConfig
operator|=
name|storedSaveOrderConfig
operator|.
name|orElseGet
argument_list|(
name|preferencesService
operator|::
name|loadExportSaveOrder
argument_list|)
expr_stmt|;
name|saveOrderConfigDisplayView
operator|.
name|changeExportDescriptionToSave
argument_list|()
expr_stmt|;
name|fieldFormatterCleanupsPanel
operator|=
operator|new
name|FieldFormatterCleanupsPanel
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Enable save actions"
argument_list|)
argument_list|,
name|Cleanups
operator|.
name|DEFAULT_SAVE_ACTIONS
argument_list|)
expr_stmt|;
name|Label
name|saveActions
init|=
operator|new
name|Label
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Save actions"
argument_list|)
argument_list|)
decl_stmt|;
name|saveActions
operator|.
name|getStyleClass
argument_list|()
operator|.
name|add
argument_list|(
literal|"sectionHeader"
argument_list|)
expr_stmt|;
name|contentVbox
operator|.
name|getChildren
argument_list|()
operator|.
name|addAll
argument_list|(
name|saveOrderConfigDisplayView
argument_list|,
name|saveActions
argument_list|,
name|fieldFormatterCleanupsPanel
argument_list|)
expr_stmt|;
name|protect
operator|.
name|selectedProperty
argument_list|()
operator|.
name|bindBidirectional
argument_list|(
name|viewModel
operator|.
name|libraryProtectedProperty
argument_list|()
argument_list|)
expr_stmt|;
name|setValues
argument_list|()
expr_stmt|;
block|}
DECL|method|setValues ()
specifier|private
name|void
name|setValues
parameter_list|()
block|{
name|fieldFormatterCleanupsPanel
operator|.
name|setValues
argument_list|(
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
operator|.
name|getMetaData
argument_list|()
argument_list|)
expr_stmt|;
name|saveOrderConfigDisplayView
operator|.
name|setValues
argument_list|(
name|oldSaveOrderConfig
argument_list|)
expr_stmt|;
block|}
annotation|@
name|FXML
DECL|method|browseGeneralFileDirectory (ActionEvent event)
specifier|public
name|void
name|browseGeneralFileDirectory
parameter_list|(
name|ActionEvent
name|event
parameter_list|)
block|{
name|viewModel
operator|.
name|browseGeneralDir
argument_list|()
expr_stmt|;
block|}
annotation|@
name|FXML
DECL|method|browseUserSpecificFileDirectory (ActionEvent event)
specifier|public
name|void
name|browseUserSpecificFileDirectory
parameter_list|(
name|ActionEvent
name|event
parameter_list|)
block|{
name|viewModel
operator|.
name|browseUserDir
argument_list|()
expr_stmt|;
block|}
annotation|@
name|FXML
DECL|method|browseLaTexFileDirectory (ActionEvent event)
name|void
name|browseLaTexFileDirectory
parameter_list|(
name|ActionEvent
name|event
parameter_list|)
block|{
name|viewModel
operator|.
name|browseLaTexDir
argument_list|()
expr_stmt|;
block|}
DECL|method|storeSettings ()
specifier|private
name|void
name|storeSettings
parameter_list|()
block|{
comment|//FIXME: Move to viewModel until fieldFormatterCleanupsPanel is property implemented
name|MetaData
name|metaData
init|=
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
operator|.
name|getMetaData
argument_list|()
decl_stmt|;
name|Charset
name|oldEncoding
init|=
name|metaData
operator|.
name|getEncoding
argument_list|()
operator|.
name|orElse
argument_list|(
name|preferencesService
operator|.
name|getDefaultEncoding
argument_list|()
argument_list|)
decl_stmt|;
name|Charset
name|newEncoding
init|=
name|viewModel
operator|.
name|selectedEncodingProperty
argument_list|()
operator|.
name|getValue
argument_list|()
decl_stmt|;
name|metaData
operator|.
name|setEncoding
argument_list|(
name|newEncoding
argument_list|)
expr_stmt|;
name|String
name|text
init|=
name|viewModel
operator|.
name|generalFileDirectoryPropertyProperty
argument_list|()
operator|.
name|getValue
argument_list|()
operator|.
name|trim
argument_list|()
decl_stmt|;
if|if
condition|(
name|text
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|metaData
operator|.
name|clearDefaultFileDirectory
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|metaData
operator|.
name|setDefaultFileDirectory
argument_list|(
name|text
argument_list|)
expr_stmt|;
block|}
comment|// Repeat for individual file dir - reuse 'text' and 'dir' vars
name|text
operator|=
name|viewModel
operator|.
name|userSpecificFileDirectoryProperty
argument_list|()
operator|.
name|getValue
argument_list|()
expr_stmt|;
if|if
condition|(
name|text
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|metaData
operator|.
name|clearUserFileDirectory
argument_list|(
name|preferencesService
operator|.
name|getUser
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|metaData
operator|.
name|setUserFileDirectory
argument_list|(
name|preferencesService
operator|.
name|getUser
argument_list|()
argument_list|,
name|text
argument_list|)
expr_stmt|;
block|}
name|text
operator|=
name|viewModel
operator|.
name|laTexFileDirectoryProperty
argument_list|()
operator|.
name|getValue
argument_list|()
expr_stmt|;
if|if
condition|(
name|text
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|metaData
operator|.
name|clearLaTexFileDirectory
argument_list|(
name|preferencesService
operator|.
name|getUser
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|metaData
operator|.
name|setLaTexFileDirectory
argument_list|(
name|preferencesService
operator|.
name|getUser
argument_list|()
argument_list|,
name|Paths
operator|.
name|get
argument_list|(
name|text
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|viewModel
operator|.
name|libraryProtectedProperty
argument_list|()
operator|.
name|getValue
argument_list|()
condition|)
block|{
name|metaData
operator|.
name|markAsProtected
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|metaData
operator|.
name|markAsNotProtected
argument_list|()
expr_stmt|;
block|}
name|SaveOrderConfig
name|newSaveOrderConfig
init|=
name|saveOrderConfigDisplayView
operator|.
name|getSaveOrderConfig
argument_list|()
decl_stmt|;
name|boolean
name|saveOrderConfigChanged
init|=
operator|!
name|newSaveOrderConfig
operator|.
name|equals
argument_list|(
name|oldSaveOrderConfig
argument_list|)
decl_stmt|;
comment|// See if any of the values have been modified:
if|if
condition|(
name|saveOrderConfigChanged
condition|)
block|{
if|if
condition|(
name|newSaveOrderConfig
operator|.
name|equals
argument_list|(
name|SaveOrderConfig
operator|.
name|getDefaultSaveOrder
argument_list|()
argument_list|)
condition|)
block|{
name|metaData
operator|.
name|clearSaveOrderConfig
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|metaData
operator|.
name|setSaveOrderConfig
argument_list|(
name|newSaveOrderConfig
argument_list|)
expr_stmt|;
block|}
block|}
name|boolean
name|saveActionsChanged
init|=
name|fieldFormatterCleanupsPanel
operator|.
name|hasChanged
argument_list|()
decl_stmt|;
if|if
condition|(
name|saveActionsChanged
condition|)
block|{
if|if
condition|(
name|fieldFormatterCleanupsPanel
operator|.
name|isDefaultSaveActions
argument_list|()
condition|)
block|{
name|metaData
operator|.
name|clearSaveActions
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|fieldFormatterCleanupsPanel
operator|.
name|storeSettings
argument_list|(
name|metaData
argument_list|)
expr_stmt|;
block|}
block|}
name|boolean
name|encodingChanged
init|=
operator|!
name|newEncoding
operator|.
name|equals
argument_list|(
name|oldEncoding
argument_list|)
decl_stmt|;
name|boolean
name|changed
init|=
name|saveOrderConfigChanged
operator|||
name|encodingChanged
operator|||
name|viewModel
operator|.
name|generalFileDirChanged
argument_list|()
operator|||
name|viewModel
operator|.
name|userFileDirChanged
argument_list|()
operator|||
name|viewModel
operator|.
name|protectedValueChanged
argument_list|()
operator|||
name|saveActionsChanged
operator|||
name|viewModel
operator|.
name|laTexFileDirChanged
argument_list|()
decl_stmt|;
comment|// ... if so, mark base changed. Prevent the Undo button from removing
comment|// change marking:
if|if
condition|(
name|changed
condition|)
block|{
name|panel
operator|.
name|markNonUndoableBaseChanged
argument_list|()
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

