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
name|javafx
operator|.
name|application
operator|.
name|Platform
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
name|RadioButton
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
name|actions
operator|.
name|ActionFactory
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
name|StandardActions
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
name|help
operator|.
name|HelpAction
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
name|IconValidationDecorator
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
name|help
operator|.
name|HelpFile
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
name|NewLineSeparator
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
name|visualization
operator|.
name|ControlsFxVisualizer
import|;
end_import

begin_class
DECL|class|FileTabView
specifier|public
class|class
name|FileTabView
extends|extends
name|AbstractPreferenceTabView
implements|implements
name|PreferencesTab
block|{
DECL|field|openLastStartup
annotation|@
name|FXML
specifier|private
name|CheckBox
name|openLastStartup
decl_stmt|;
DECL|field|backupOldFile
annotation|@
name|FXML
specifier|private
name|CheckBox
name|backupOldFile
decl_stmt|;
DECL|field|noWrapFiles
annotation|@
name|FXML
specifier|private
name|TextField
name|noWrapFiles
decl_stmt|;
DECL|field|resolveStringsBibTex
annotation|@
name|FXML
specifier|private
name|RadioButton
name|resolveStringsBibTex
decl_stmt|;
DECL|field|resolveStringsAll
annotation|@
name|FXML
specifier|private
name|RadioButton
name|resolveStringsAll
decl_stmt|;
DECL|field|resolveStringsExcept
annotation|@
name|FXML
specifier|private
name|TextField
name|resolveStringsExcept
decl_stmt|;
DECL|field|newLineSeparator
annotation|@
name|FXML
specifier|private
name|ComboBox
argument_list|<
name|NewLineSeparator
argument_list|>
name|newLineSeparator
decl_stmt|;
DECL|field|alwaysReformatBib
annotation|@
name|FXML
specifier|private
name|CheckBox
name|alwaysReformatBib
decl_stmt|;
DECL|field|mainFileDir
annotation|@
name|FXML
specifier|private
name|TextField
name|mainFileDir
decl_stmt|;
DECL|field|useBibLocationAsPrimary
annotation|@
name|FXML
specifier|private
name|CheckBox
name|useBibLocationAsPrimary
decl_stmt|;
DECL|field|autolinkRegexHelp
annotation|@
name|FXML
specifier|private
name|Button
name|autolinkRegexHelp
decl_stmt|;
DECL|field|autolinkFileStartsBibtex
annotation|@
name|FXML
specifier|private
name|RadioButton
name|autolinkFileStartsBibtex
decl_stmt|;
DECL|field|autolinkFileExactBibtex
annotation|@
name|FXML
specifier|private
name|RadioButton
name|autolinkFileExactBibtex
decl_stmt|;
DECL|field|autolinkUseRegex
annotation|@
name|FXML
specifier|private
name|RadioButton
name|autolinkUseRegex
decl_stmt|;
DECL|field|autolinkRegexKey
annotation|@
name|FXML
specifier|private
name|TextField
name|autolinkRegexKey
decl_stmt|;
DECL|field|searchFilesOnOpen
annotation|@
name|FXML
specifier|private
name|CheckBox
name|searchFilesOnOpen
decl_stmt|;
DECL|field|openBrowseOnCreate
annotation|@
name|FXML
specifier|private
name|CheckBox
name|openBrowseOnCreate
decl_stmt|;
DECL|field|autosaveLocalLibraries
annotation|@
name|FXML
specifier|private
name|CheckBox
name|autosaveLocalLibraries
decl_stmt|;
DECL|field|autosaveLocalLibrariesHelp
annotation|@
name|FXML
specifier|private
name|Button
name|autosaveLocalLibrariesHelp
decl_stmt|;
DECL|field|validationVisualizer
specifier|private
name|ControlsFxVisualizer
name|validationVisualizer
init|=
operator|new
name|ControlsFxVisualizer
argument_list|()
decl_stmt|;
DECL|method|FileTabView (JabRefPreferences preferences)
specifier|public
name|FileTabView
parameter_list|(
name|JabRefPreferences
name|preferences
parameter_list|)
block|{
name|this
operator|.
name|preferences
operator|=
name|preferences
expr_stmt|;
name|ViewLoader
operator|.
name|view
argument_list|(
name|this
argument_list|)
operator|.
name|root
argument_list|(
name|this
argument_list|)
operator|.
name|load
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|getTabName ()
specifier|public
name|String
name|getTabName
parameter_list|()
block|{
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"File"
argument_list|)
return|;
block|}
DECL|method|initialize ()
specifier|public
name|void
name|initialize
parameter_list|()
block|{
name|FileTabViewModel
name|fileTabViewModel
init|=
operator|new
name|FileTabViewModel
argument_list|(
name|dialogService
argument_list|,
name|preferences
argument_list|)
decl_stmt|;
name|this
operator|.
name|viewModel
operator|=
name|fileTabViewModel
expr_stmt|;
name|openLastStartup
operator|.
name|selectedProperty
argument_list|()
operator|.
name|bindBidirectional
argument_list|(
name|fileTabViewModel
operator|.
name|openLastStartupProperty
argument_list|()
argument_list|)
expr_stmt|;
name|backupOldFile
operator|.
name|selectedProperty
argument_list|()
operator|.
name|bindBidirectional
argument_list|(
name|fileTabViewModel
operator|.
name|backupOldFileProperty
argument_list|()
argument_list|)
expr_stmt|;
name|noWrapFiles
operator|.
name|textProperty
argument_list|()
operator|.
name|bindBidirectional
argument_list|(
name|fileTabViewModel
operator|.
name|noWrapFilesProperty
argument_list|()
argument_list|)
expr_stmt|;
name|resolveStringsBibTex
operator|.
name|selectedProperty
argument_list|()
operator|.
name|bindBidirectional
argument_list|(
name|fileTabViewModel
operator|.
name|resolveStringsBibTexProperty
argument_list|()
argument_list|)
expr_stmt|;
name|resolveStringsAll
operator|.
name|selectedProperty
argument_list|()
operator|.
name|bindBidirectional
argument_list|(
name|fileTabViewModel
operator|.
name|resolveStringsAllProperty
argument_list|()
argument_list|)
expr_stmt|;
name|resolveStringsExcept
operator|.
name|textProperty
argument_list|()
operator|.
name|bindBidirectional
argument_list|(
name|fileTabViewModel
operator|.
name|resolvStringsExceptProperty
argument_list|()
argument_list|)
expr_stmt|;
name|resolveStringsExcept
operator|.
name|disableProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|resolveStringsAll
operator|.
name|selectedProperty
argument_list|()
operator|.
name|not
argument_list|()
argument_list|)
expr_stmt|;
name|newLineSeparator
operator|.
name|itemsProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|fileTabViewModel
operator|.
name|newLineSeparatorListProperty
argument_list|()
argument_list|)
expr_stmt|;
name|newLineSeparator
operator|.
name|valueProperty
argument_list|()
operator|.
name|bindBidirectional
argument_list|(
name|fileTabViewModel
operator|.
name|selectedNewLineSeparatorProperty
argument_list|()
argument_list|)
expr_stmt|;
name|alwaysReformatBib
operator|.
name|selectedProperty
argument_list|()
operator|.
name|bindBidirectional
argument_list|(
name|fileTabViewModel
operator|.
name|alwaysReformatBibProperty
argument_list|()
argument_list|)
expr_stmt|;
name|mainFileDir
operator|.
name|textProperty
argument_list|()
operator|.
name|bindBidirectional
argument_list|(
name|fileTabViewModel
operator|.
name|mainFileDirProperty
argument_list|()
argument_list|)
expr_stmt|;
name|useBibLocationAsPrimary
operator|.
name|selectedProperty
argument_list|()
operator|.
name|bindBidirectional
argument_list|(
name|fileTabViewModel
operator|.
name|useBibLocationAsPrimaryProperty
argument_list|()
argument_list|)
expr_stmt|;
name|autolinkFileStartsBibtex
operator|.
name|selectedProperty
argument_list|()
operator|.
name|bindBidirectional
argument_list|(
name|fileTabViewModel
operator|.
name|autolinkFileStartsBibtexProperty
argument_list|()
argument_list|)
expr_stmt|;
name|autolinkFileExactBibtex
operator|.
name|selectedProperty
argument_list|()
operator|.
name|bindBidirectional
argument_list|(
name|fileTabViewModel
operator|.
name|autolinkFileExactBibtexProperty
argument_list|()
argument_list|)
expr_stmt|;
name|autolinkUseRegex
operator|.
name|selectedProperty
argument_list|()
operator|.
name|bindBidirectional
argument_list|(
name|fileTabViewModel
operator|.
name|autolinkUseRegexProperty
argument_list|()
argument_list|)
expr_stmt|;
name|autolinkRegexKey
operator|.
name|textProperty
argument_list|()
operator|.
name|bindBidirectional
argument_list|(
name|fileTabViewModel
operator|.
name|autolinkRegexKeyProperty
argument_list|()
argument_list|)
expr_stmt|;
name|autolinkRegexKey
operator|.
name|disableProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|autolinkUseRegex
operator|.
name|selectedProperty
argument_list|()
operator|.
name|not
argument_list|()
argument_list|)
expr_stmt|;
name|searchFilesOnOpen
operator|.
name|selectedProperty
argument_list|()
operator|.
name|bindBidirectional
argument_list|(
name|fileTabViewModel
operator|.
name|searchFilesOnOpenProperty
argument_list|()
argument_list|)
expr_stmt|;
name|openBrowseOnCreate
operator|.
name|selectedProperty
argument_list|()
operator|.
name|bindBidirectional
argument_list|(
name|fileTabViewModel
operator|.
name|openBrowseOnCreateProperty
argument_list|()
argument_list|)
expr_stmt|;
name|autosaveLocalLibraries
operator|.
name|selectedProperty
argument_list|()
operator|.
name|bindBidirectional
argument_list|(
name|fileTabViewModel
operator|.
name|autosaveLocalLibrariesProperty
argument_list|()
argument_list|)
expr_stmt|;
name|ActionFactory
name|actionFactory
init|=
operator|new
name|ActionFactory
argument_list|(
name|Globals
operator|.
name|getKeyPrefs
argument_list|()
argument_list|)
decl_stmt|;
name|actionFactory
operator|.
name|configureIconButton
argument_list|(
name|StandardActions
operator|.
name|HELP_REGEX_SEARCH
argument_list|,
operator|new
name|HelpAction
argument_list|(
name|HelpFile
operator|.
name|REGEX_SEARCH
argument_list|)
argument_list|,
name|autolinkRegexHelp
argument_list|)
expr_stmt|;
name|actionFactory
operator|.
name|configureIconButton
argument_list|(
name|StandardActions
operator|.
name|HELP
argument_list|,
operator|new
name|HelpAction
argument_list|(
name|HelpFile
operator|.
name|AUTOSAVE
argument_list|)
argument_list|,
name|autosaveLocalLibrariesHelp
argument_list|)
expr_stmt|;
name|validationVisualizer
operator|.
name|setDecoration
argument_list|(
operator|new
name|IconValidationDecorator
argument_list|()
argument_list|)
expr_stmt|;
name|Platform
operator|.
name|runLater
argument_list|(
parameter_list|()
lambda|->
name|validationVisualizer
operator|.
name|initVisualization
argument_list|(
name|fileTabViewModel
operator|.
name|mainFileDirValidationStatus
argument_list|()
argument_list|,
name|mainFileDir
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|mainFileDirBrowse ()
specifier|public
name|void
name|mainFileDirBrowse
parameter_list|()
block|{
operator|(
operator|(
name|FileTabViewModel
operator|)
name|viewModel
operator|)
operator|.
name|mainFileDirBrowse
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

