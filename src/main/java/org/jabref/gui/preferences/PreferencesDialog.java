begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.preferences
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|preferences
package|;
end_package

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
name|prefs
operator|.
name|BackingStoreException
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
name|geometry
operator|.
name|Pos
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
name|ListView
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
name|ScrollPane
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
name|Tooltip
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
name|BorderPane
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
name|Priority
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
name|Globals
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|JabRefException
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
name|GUIGlobals
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
name|gui
operator|.
name|util
operator|.
name|ControlHelper
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
name|ViewModelListCellFactory
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
name|ExporterFactory
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
name|TemplateExporter
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
name|layout
operator|.
name|LayoutFormatterPreferences
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
name|xmp
operator|.
name|XmpPreferences
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
name|jabref
operator|.
name|preferences
operator|.
name|JabRefPreferencesFilter
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
comment|/**  * Preferences dialog. Contains a TabbedPane, and tabs will be defined in separate classes. Tabs MUST implement the  * PrefsTab interface, since this dialog will call the storeSettings() method of all tabs when the user presses ok.  *  * With this design, it should be very easy to add new tabs later.  */
end_comment

begin_class
DECL|class|PreferencesDialog
specifier|public
class|class
name|PreferencesDialog
extends|extends
name|BaseDialog
argument_list|<
name|Void
argument_list|>
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
name|PreferencesDialog
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|container
specifier|private
specifier|final
name|BorderPane
name|container
decl_stmt|;
DECL|field|dialogService
specifier|private
specifier|final
name|DialogService
name|dialogService
decl_stmt|;
DECL|field|frame
specifier|private
specifier|final
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|prefs
specifier|private
specifier|final
name|JabRefPreferences
name|prefs
decl_stmt|;
DECL|field|preferenceTabs
specifier|private
specifier|final
name|ObservableList
argument_list|<
name|PrefsTab
argument_list|>
name|preferenceTabs
decl_stmt|;
DECL|method|PreferencesDialog (JabRefFrame parent)
specifier|public
name|PreferencesDialog
parameter_list|(
name|JabRefFrame
name|parent
parameter_list|)
block|{
name|setTitle
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"JabRef preferences"
argument_list|)
argument_list|)
expr_stmt|;
name|getDialogPane
argument_list|()
operator|.
name|getScene
argument_list|()
operator|.
name|getStylesheets
argument_list|()
operator|.
name|add
argument_list|(
name|this
operator|.
name|getClass
argument_list|()
operator|.
name|getResource
argument_list|(
literal|"PreferencesDialog.css"
argument_list|)
operator|.
name|toExternalForm
argument_list|()
argument_list|)
expr_stmt|;
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
name|OK_DONE
argument_list|)
decl_stmt|;
name|getDialogPane
argument_list|()
operator|.
name|getButtonTypes
argument_list|()
operator|.
name|addAll
argument_list|(
name|save
argument_list|,
name|ButtonType
operator|.
name|CANCEL
argument_list|)
expr_stmt|;
name|ControlHelper
operator|.
name|setAction
argument_list|(
name|save
argument_list|,
name|getDialogPane
argument_list|()
argument_list|,
name|event
lambda|->
block|{
name|storeAllSettings
argument_list|()
expr_stmt|;
name|close
argument_list|()
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
name|prefs
operator|=
name|JabRefPreferences
operator|.
name|getInstance
argument_list|()
expr_stmt|;
name|frame
operator|=
name|parent
expr_stmt|;
name|dialogService
operator|=
name|frame
operator|.
name|getDialogService
argument_list|()
expr_stmt|;
name|preferenceTabs
operator|=
name|FXCollections
operator|.
name|observableArrayList
argument_list|()
expr_stmt|;
name|preferenceTabs
operator|.
name|add
argument_list|(
operator|new
name|GeneralTab
argument_list|(
name|dialogService
argument_list|,
name|prefs
argument_list|)
argument_list|)
expr_stmt|;
name|preferenceTabs
operator|.
name|add
argument_list|(
operator|new
name|FileTab
argument_list|(
name|dialogService
argument_list|,
name|prefs
argument_list|)
argument_list|)
expr_stmt|;
name|preferenceTabs
operator|.
name|add
argument_list|(
operator|new
name|TablePrefsTab
argument_list|(
name|prefs
argument_list|)
argument_list|)
expr_stmt|;
name|preferenceTabs
operator|.
name|add
argument_list|(
operator|new
name|TableColumnsTab
argument_list|(
name|prefs
argument_list|,
name|frame
argument_list|)
argument_list|)
expr_stmt|;
name|preferenceTabs
operator|.
name|add
argument_list|(
operator|new
name|PreviewPrefsTab
argument_list|(
name|dialogService
argument_list|,
name|ExternalFileTypes
operator|.
name|getInstance
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|preferenceTabs
operator|.
name|add
argument_list|(
operator|new
name|ExternalTab
argument_list|(
name|frame
argument_list|,
name|this
argument_list|,
name|prefs
argument_list|)
argument_list|)
expr_stmt|;
name|preferenceTabs
operator|.
name|add
argument_list|(
operator|new
name|GroupsPrefsTab
argument_list|(
name|prefs
argument_list|)
argument_list|)
expr_stmt|;
name|preferenceTabs
operator|.
name|add
argument_list|(
operator|new
name|EntryEditorPrefsTab
argument_list|(
name|prefs
argument_list|)
argument_list|)
expr_stmt|;
name|preferenceTabs
operator|.
name|add
argument_list|(
operator|new
name|BibtexKeyPatternPrefTab
argument_list|(
name|prefs
argument_list|,
name|frame
operator|.
name|getCurrentBasePanel
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|preferenceTabs
operator|.
name|add
argument_list|(
operator|new
name|ImportSettingsTab
argument_list|(
name|prefs
argument_list|)
argument_list|)
expr_stmt|;
name|preferenceTabs
operator|.
name|add
argument_list|(
operator|new
name|ExportSortingPrefsTab
argument_list|(
name|prefs
argument_list|)
argument_list|)
expr_stmt|;
name|preferenceTabs
operator|.
name|add
argument_list|(
operator|new
name|NameFormatterTab
argument_list|(
name|prefs
argument_list|)
argument_list|)
expr_stmt|;
name|preferenceTabs
operator|.
name|add
argument_list|(
operator|new
name|XmpPrefsTab
argument_list|(
name|prefs
argument_list|)
argument_list|)
expr_stmt|;
name|preferenceTabs
operator|.
name|add
argument_list|(
operator|new
name|NetworkTab
argument_list|(
name|dialogService
argument_list|,
name|prefs
argument_list|)
argument_list|)
expr_stmt|;
name|preferenceTabs
operator|.
name|add
argument_list|(
operator|new
name|AdvancedTab
argument_list|(
name|dialogService
argument_list|,
name|prefs
argument_list|)
argument_list|)
expr_stmt|;
name|preferenceTabs
operator|.
name|add
argument_list|(
operator|new
name|AppearancePrefsTab
argument_list|(
name|dialogService
argument_list|,
name|prefs
argument_list|)
argument_list|)
expr_stmt|;
name|container
operator|=
operator|new
name|BorderPane
argument_list|()
expr_stmt|;
name|getDialogPane
argument_list|()
operator|.
name|setContent
argument_list|(
name|container
argument_list|)
expr_stmt|;
name|construct
argument_list|()
expr_stmt|;
block|}
DECL|method|construct ()
specifier|private
name|void
name|construct
parameter_list|()
block|{
name|VBox
name|vBox
init|=
operator|new
name|VBox
argument_list|()
decl_stmt|;
name|vBox
operator|.
name|setPrefWidth
argument_list|(
literal|160
argument_list|)
expr_stmt|;
name|ListView
argument_list|<
name|PrefsTab
argument_list|>
name|tabsList
init|=
operator|new
name|ListView
argument_list|<>
argument_list|()
decl_stmt|;
name|tabsList
operator|.
name|setId
argument_list|(
literal|"sideMenu"
argument_list|)
expr_stmt|;
name|tabsList
operator|.
name|itemsProperty
argument_list|()
operator|.
name|setValue
argument_list|(
name|preferenceTabs
argument_list|)
expr_stmt|;
name|EasyBind
operator|.
name|subscribe
argument_list|(
name|tabsList
operator|.
name|getSelectionModel
argument_list|()
operator|.
name|selectedItemProperty
argument_list|()
argument_list|,
name|tab
lambda|->
block|{
if|if
condition|(
name|tab
operator|!=
literal|null
condition|)
block|{
name|ScrollPane
name|preferencePaneContainer
init|=
operator|new
name|ScrollPane
argument_list|(
name|tab
operator|.
name|getBuilder
argument_list|()
argument_list|)
decl_stmt|;
name|preferencePaneContainer
operator|.
name|getStyleClass
argument_list|()
operator|.
name|add
argument_list|(
literal|"preferencePaneContainer"
argument_list|)
expr_stmt|;
name|container
operator|.
name|setCenter
argument_list|(
name|preferencePaneContainer
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|tabsList
operator|.
name|getSelectionModel
argument_list|()
operator|.
name|selectFirst
argument_list|()
expr_stmt|;
operator|new
name|ViewModelListCellFactory
argument_list|<
name|PrefsTab
argument_list|>
argument_list|()
operator|.
name|withText
argument_list|(
name|PrefsTab
operator|::
name|getTabName
argument_list|)
operator|.
name|install
argument_list|(
name|tabsList
argument_list|)
expr_stmt|;
name|VBox
name|buttonContainer
init|=
operator|new
name|VBox
argument_list|()
decl_stmt|;
name|buttonContainer
operator|.
name|setAlignment
argument_list|(
name|Pos
operator|.
name|BOTTOM_LEFT
argument_list|)
expr_stmt|;
name|buttonContainer
operator|.
name|setSpacing
argument_list|(
literal|3.0
argument_list|)
expr_stmt|;
name|Button
name|importPreferences
init|=
operator|new
name|Button
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Import preferences"
argument_list|)
argument_list|)
decl_stmt|;
name|importPreferences
operator|.
name|setTooltip
argument_list|(
operator|new
name|Tooltip
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Import preferences from file"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|importPreferences
operator|.
name|setOnAction
argument_list|(
name|e
lambda|->
name|importPreferences
argument_list|()
argument_list|)
expr_stmt|;
name|importPreferences
operator|.
name|setMaxWidth
argument_list|(
name|Double
operator|.
name|MAX_VALUE
argument_list|)
expr_stmt|;
name|Button
name|exportPreferences
init|=
operator|new
name|Button
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Export preferences"
argument_list|)
argument_list|)
decl_stmt|;
name|exportPreferences
operator|.
name|setTooltip
argument_list|(
operator|new
name|Tooltip
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Export preferences to file"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|exportPreferences
operator|.
name|setOnAction
argument_list|(
name|e
lambda|->
name|exportPreferences
argument_list|()
argument_list|)
expr_stmt|;
name|exportPreferences
operator|.
name|setMaxWidth
argument_list|(
name|Double
operator|.
name|MAX_VALUE
argument_list|)
expr_stmt|;
name|Button
name|showPreferences
init|=
operator|new
name|Button
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Show preferences"
argument_list|)
argument_list|)
decl_stmt|;
name|showPreferences
operator|.
name|setOnAction
argument_list|(
name|e
lambda|->
operator|new
name|PreferencesFilterDialog
argument_list|(
operator|new
name|JabRefPreferencesFilter
argument_list|(
name|prefs
argument_list|)
argument_list|)
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
argument_list|)
expr_stmt|;
name|showPreferences
operator|.
name|setMaxWidth
argument_list|(
name|Double
operator|.
name|MAX_VALUE
argument_list|)
expr_stmt|;
name|Button
name|resetPreferences
init|=
operator|new
name|Button
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Reset preferences"
argument_list|)
argument_list|)
decl_stmt|;
name|resetPreferences
operator|.
name|setOnAction
argument_list|(
name|e
lambda|->
name|resetPreferences
argument_list|()
argument_list|)
expr_stmt|;
name|resetPreferences
operator|.
name|setMaxWidth
argument_list|(
name|Double
operator|.
name|MAX_VALUE
argument_list|)
expr_stmt|;
name|buttonContainer
operator|.
name|getChildren
argument_list|()
operator|.
name|addAll
argument_list|(
name|importPreferences
argument_list|,
name|exportPreferences
argument_list|,
name|showPreferences
argument_list|,
name|resetPreferences
argument_list|)
expr_stmt|;
name|VBox
name|spacer
init|=
operator|new
name|VBox
argument_list|()
decl_stmt|;
name|spacer
operator|.
name|setPrefHeight
argument_list|(
literal|10.0
argument_list|)
expr_stmt|;
name|VBox
operator|.
name|setVgrow
argument_list|(
name|tabsList
argument_list|,
name|Priority
operator|.
name|ALWAYS
argument_list|)
expr_stmt|;
name|VBox
operator|.
name|setVgrow
argument_list|(
name|spacer
argument_list|,
name|Priority
operator|.
name|SOMETIMES
argument_list|)
expr_stmt|;
name|vBox
operator|.
name|getChildren
argument_list|()
operator|.
name|addAll
argument_list|(
name|tabsList
argument_list|,
name|spacer
argument_list|,
name|buttonContainer
argument_list|)
expr_stmt|;
name|container
operator|.
name|setLeft
argument_list|(
name|vBox
argument_list|)
expr_stmt|;
name|setValues
argument_list|()
expr_stmt|;
block|}
DECL|method|resetPreferences ()
specifier|private
name|void
name|resetPreferences
parameter_list|()
block|{
name|boolean
name|resetPreferencesConfirmed
init|=
name|dialogService
operator|.
name|showConfirmationDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Reset preferences"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Are you sure you want to reset all settings to default values?"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Reset preferences"
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
name|resetPreferencesConfirmed
condition|)
block|{
try|try
block|{
name|prefs
operator|.
name|clear
argument_list|()
expr_stmt|;
name|dialogService
operator|.
name|showWarningDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Reset preferences"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"You must restart JabRef for this to come into effect."
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|BackingStoreException
name|ex
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Error while resetting preferences"
argument_list|,
name|ex
argument_list|)
expr_stmt|;
name|dialogService
operator|.
name|showErrorDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Reset preferences"
argument_list|)
argument_list|,
name|ex
argument_list|)
expr_stmt|;
block|}
name|updateAfterPreferenceChanges
argument_list|()
expr_stmt|;
block|}
block|}
DECL|method|importPreferences ()
specifier|private
name|void
name|importPreferences
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
name|XML
argument_list|)
operator|.
name|withDefaultExtension
argument_list|(
name|StandardFileType
operator|.
name|XML
argument_list|)
operator|.
name|withInitialDirectory
argument_list|(
name|prefs
operator|.
name|setLastPreferencesExportPath
argument_list|()
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
name|file
lambda|->
block|{
try|try
block|{
name|prefs
operator|.
name|importPreferences
argument_list|(
name|file
argument_list|)
expr_stmt|;
name|updateAfterPreferenceChanges
argument_list|()
expr_stmt|;
name|dialogService
operator|.
name|showWarningDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Import preferences"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"You must restart JabRef for this to come into effect."
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|JabRefException
name|ex
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Error while importing preferences"
argument_list|,
name|ex
argument_list|)
expr_stmt|;
name|dialogService
operator|.
name|showErrorDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Import preferences"
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
DECL|method|updateAfterPreferenceChanges ()
specifier|private
name|void
name|updateAfterPreferenceChanges
parameter_list|()
block|{
name|setValues
argument_list|()
expr_stmt|;
name|List
argument_list|<
name|TemplateExporter
argument_list|>
name|customExporters
init|=
name|prefs
operator|.
name|getCustomExportFormats
argument_list|(
name|Globals
operator|.
name|journalAbbreviationLoader
argument_list|)
decl_stmt|;
name|LayoutFormatterPreferences
name|layoutPreferences
init|=
name|prefs
operator|.
name|getLayoutFormatterPreferences
argument_list|(
name|Globals
operator|.
name|journalAbbreviationLoader
argument_list|)
decl_stmt|;
name|SavePreferences
name|savePreferences
init|=
name|prefs
operator|.
name|loadForExportFromPreferences
argument_list|()
decl_stmt|;
name|XmpPreferences
name|xmpPreferences
init|=
name|prefs
operator|.
name|getXMPPreferences
argument_list|()
decl_stmt|;
name|Globals
operator|.
name|exportFactory
operator|=
name|ExporterFactory
operator|.
name|create
argument_list|(
name|customExporters
argument_list|,
name|layoutPreferences
argument_list|,
name|savePreferences
argument_list|,
name|xmpPreferences
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|updateEntryEditorTabList
argument_list|()
expr_stmt|;
block|}
DECL|method|storeAllSettings ()
specifier|private
name|void
name|storeAllSettings
parameter_list|()
block|{
comment|// First check that all tabs are ready to close:
for|for
control|(
name|PrefsTab
name|tab
range|:
name|preferenceTabs
control|)
block|{
if|if
condition|(
operator|!
name|tab
operator|.
name|validateSettings
argument_list|()
condition|)
block|{
return|return;
comment|// If not, break off.
block|}
block|}
comment|// Then store settings and close:
for|for
control|(
name|PrefsTab
name|tab
range|:
name|preferenceTabs
control|)
block|{
name|tab
operator|.
name|storeSettings
argument_list|()
expr_stmt|;
block|}
name|prefs
operator|.
name|flush
argument_list|()
expr_stmt|;
name|GUIGlobals
operator|.
name|updateEntryEditorColors
argument_list|()
expr_stmt|;
name|frame
operator|.
name|setupAllTables
argument_list|()
expr_stmt|;
name|frame
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Preferences recorded."
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|setValues ()
specifier|public
name|void
name|setValues
parameter_list|()
block|{
comment|// Update all field values in the tabs:
for|for
control|(
name|PrefsTab
name|prefsTab
range|:
name|preferenceTabs
control|)
block|{
name|prefsTab
operator|.
name|setValues
argument_list|()
expr_stmt|;
block|}
block|}
DECL|method|exportPreferences ()
specifier|private
name|void
name|exportPreferences
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
name|XML
argument_list|)
operator|.
name|withDefaultExtension
argument_list|(
name|StandardFileType
operator|.
name|XML
argument_list|)
operator|.
name|withInitialDirectory
argument_list|(
name|prefs
operator|.
name|setLastPreferencesExportPath
argument_list|()
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
name|exportFile
lambda|->
block|{
try|try
block|{
name|storeAllSettings
argument_list|()
expr_stmt|;
name|prefs
operator|.
name|exportPreferences
argument_list|(
name|exportFile
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|setLastPreferencesExportPath
argument_list|(
name|exportFile
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|JabRefException
name|ex
parameter_list|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
name|ex
operator|.
name|getMessage
argument_list|()
argument_list|,
name|ex
argument_list|)
expr_stmt|;
name|dialogService
operator|.
name|showErrorDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Export preferences"
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
block|}
end_class

end_unit

