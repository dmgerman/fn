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
name|Node
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
name|DialogPane
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
name|Separator
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
name|control
operator|.
name|ToggleGroup
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
name|GridPane
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
name|HBox
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
name|externalfiletype
operator|.
name|EditExternalFileTypesAction
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
name|push
operator|.
name|PushToApplication
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
name|push
operator|.
name|PushToApplicationSettings
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
name|push
operator|.
name|PushToApplicationsManager
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
name|OS
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
name|strings
operator|.
name|StringUtil
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
DECL|class|ExternalTab
class|class
name|ExternalTab
implements|implements
name|PrefsTab
block|{
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
DECL|field|emailSubject
specifier|private
specifier|final
name|TextField
name|emailSubject
decl_stmt|;
DECL|field|pushToApplicationComboBox
specifier|private
specifier|final
name|ComboBox
argument_list|<
name|PushToApplication
argument_list|>
name|pushToApplicationComboBox
decl_stmt|;
DECL|field|citeCommand
specifier|private
specifier|final
name|TextField
name|citeCommand
decl_stmt|;
DECL|field|openFoldersOfAttachedFiles
specifier|private
specifier|final
name|CheckBox
name|openFoldersOfAttachedFiles
decl_stmt|;
DECL|field|defaultConsole
specifier|private
specifier|final
name|RadioButton
name|defaultConsole
decl_stmt|;
DECL|field|executeConsole
specifier|private
specifier|final
name|RadioButton
name|executeConsole
decl_stmt|;
DECL|field|consoleCommand
specifier|private
specifier|final
name|TextField
name|consoleCommand
decl_stmt|;
DECL|field|browseButton
specifier|private
specifier|final
name|Button
name|browseButton
decl_stmt|;
DECL|field|adobeAcrobatReader
specifier|private
specifier|final
name|RadioButton
name|adobeAcrobatReader
decl_stmt|;
DECL|field|sumatraReader
specifier|private
specifier|final
name|RadioButton
name|sumatraReader
decl_stmt|;
DECL|field|adobeAcrobatReaderPath
specifier|private
specifier|final
name|TextField
name|adobeAcrobatReaderPath
decl_stmt|;
DECL|field|sumatraReaderPath
specifier|private
specifier|final
name|TextField
name|sumatraReaderPath
decl_stmt|;
DECL|field|defaultFileBrowser
specifier|private
specifier|final
name|RadioButton
name|defaultFileBrowser
decl_stmt|;
DECL|field|executeFileBrowser
specifier|private
specifier|final
name|RadioButton
name|executeFileBrowser
decl_stmt|;
DECL|field|fileBrowserCommand
specifier|private
specifier|final
name|TextField
name|fileBrowserCommand
decl_stmt|;
DECL|field|fileBrowserButton
specifier|private
specifier|final
name|Button
name|fileBrowserButton
decl_stmt|;
DECL|field|builder
specifier|private
specifier|final
name|GridPane
name|builder
init|=
operator|new
name|GridPane
argument_list|()
decl_stmt|;
DECL|field|dialogService
specifier|private
specifier|final
name|DialogService
name|dialogService
decl_stmt|;
DECL|field|fileDialogConfiguration
specifier|private
specifier|final
name|FileDialogConfiguration
name|fileDialogConfiguration
init|=
operator|new
name|FileDialogConfiguration
operator|.
name|Builder
argument_list|()
operator|.
name|build
argument_list|()
decl_stmt|;
DECL|method|ExternalTab (JabRefFrame frame, JabRefPreferences prefs)
specifier|public
name|ExternalTab
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|JabRefPreferences
name|prefs
parameter_list|)
block|{
name|this
operator|.
name|prefs
operator|=
name|prefs
expr_stmt|;
name|this
operator|.
name|frame
operator|=
name|frame
expr_stmt|;
name|dialogService
operator|=
name|frame
operator|.
name|getDialogService
argument_list|()
expr_stmt|;
name|builder
operator|.
name|setVgap
argument_list|(
literal|7
argument_list|)
expr_stmt|;
name|pushToApplicationComboBox
operator|=
operator|new
name|ComboBox
argument_list|<>
argument_list|()
expr_stmt|;
name|Button
name|pushToApplicationSettingsButton
init|=
operator|new
name|Button
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Application settings"
argument_list|)
argument_list|)
decl_stmt|;
name|pushToApplicationSettingsButton
operator|.
name|setOnAction
argument_list|(
name|e
lambda|->
name|showPushToApplicationSettings
argument_list|()
argument_list|)
expr_stmt|;
name|Button
name|editFileTypes
init|=
operator|new
name|Button
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Manage external file types"
argument_list|)
argument_list|)
decl_stmt|;
name|citeCommand
operator|=
operator|new
name|TextField
argument_list|()
expr_stmt|;
name|editFileTypes
operator|.
name|setOnAction
argument_list|(
name|e
lambda|->
operator|new
name|EditExternalFileTypesAction
argument_list|()
operator|.
name|execute
argument_list|()
argument_list|)
expr_stmt|;
name|defaultConsole
operator|=
operator|new
name|RadioButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Use default terminal emulator"
argument_list|)
argument_list|)
expr_stmt|;
name|executeConsole
operator|=
operator|new
name|RadioButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Execute command"
argument_list|)
operator|+
literal|":"
argument_list|)
expr_stmt|;
name|consoleCommand
operator|=
operator|new
name|TextField
argument_list|()
expr_stmt|;
name|browseButton
operator|=
operator|new
name|Button
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Browse"
argument_list|)
argument_list|)
expr_stmt|;
name|adobeAcrobatReader
operator|=
operator|new
name|RadioButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Adobe Acrobat Reader"
argument_list|)
argument_list|)
expr_stmt|;
name|adobeAcrobatReaderPath
operator|=
operator|new
name|TextField
argument_list|()
expr_stmt|;
name|Button
name|browseAdobeAcrobatReader
init|=
operator|new
name|Button
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Browse"
argument_list|)
argument_list|)
decl_stmt|;
name|sumatraReader
operator|=
operator|new
name|RadioButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Sumatra Reader"
argument_list|)
argument_list|)
expr_stmt|;
name|sumatraReaderPath
operator|=
operator|new
name|TextField
argument_list|()
expr_stmt|;
name|Button
name|browseSumatraReader
init|=
operator|new
name|Button
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Browse"
argument_list|)
argument_list|)
decl_stmt|;
name|defaultFileBrowser
operator|=
operator|new
name|RadioButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Use default file browser"
argument_list|)
argument_list|)
expr_stmt|;
name|executeFileBrowser
operator|=
operator|new
name|RadioButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Execute Command"
argument_list|)
argument_list|)
expr_stmt|;
name|fileBrowserCommand
operator|=
operator|new
name|TextField
argument_list|()
expr_stmt|;
name|fileBrowserButton
operator|=
operator|new
name|Button
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Browser"
argument_list|)
argument_list|)
expr_stmt|;
name|Label
name|commandDescription
init|=
operator|new
name|Label
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Note: Use the placeholder %0 for the location of the opened library file."
argument_list|,
literal|"%DIR"
argument_list|)
argument_list|)
decl_stmt|;
name|defaultConsole
operator|.
name|setOnAction
argument_list|(
name|e
lambda|->
name|updateExecuteConsoleButtonAndFieldEnabledState
argument_list|()
argument_list|)
expr_stmt|;
name|executeConsole
operator|.
name|setOnAction
argument_list|(
name|e
lambda|->
name|updateExecuteConsoleButtonAndFieldEnabledState
argument_list|()
argument_list|)
expr_stmt|;
name|browseButton
operator|.
name|setOnAction
argument_list|(
name|e
lambda|->
name|showConsoleChooser
argument_list|()
argument_list|)
expr_stmt|;
name|fileBrowserButton
operator|.
name|disableProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|executeFileBrowser
operator|.
name|selectedProperty
argument_list|()
operator|.
name|not
argument_list|()
argument_list|)
expr_stmt|;
name|fileBrowserCommand
operator|.
name|disableProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|executeFileBrowser
operator|.
name|selectedProperty
argument_list|()
operator|.
name|not
argument_list|()
argument_list|)
expr_stmt|;
name|fileBrowserButton
operator|.
name|setOnAction
argument_list|(
name|e
lambda|->
name|showFileBrowserCommandChooser
argument_list|()
argument_list|)
expr_stmt|;
name|browseAdobeAcrobatReader
operator|.
name|setOnAction
argument_list|(
name|e
lambda|->
name|showAdobeChooser
argument_list|()
argument_list|)
expr_stmt|;
name|GridPane
name|consoleOptionPanel
init|=
operator|new
name|GridPane
argument_list|()
decl_stmt|;
specifier|final
name|ToggleGroup
name|consoleGroup
init|=
operator|new
name|ToggleGroup
argument_list|()
decl_stmt|;
name|defaultConsole
operator|.
name|setToggleGroup
argument_list|(
name|consoleGroup
argument_list|)
expr_stmt|;
name|executeConsole
operator|.
name|setToggleGroup
argument_list|(
name|consoleGroup
argument_list|)
expr_stmt|;
name|consoleOptionPanel
operator|.
name|add
argument_list|(
name|defaultConsole
argument_list|,
literal|1
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|consoleOptionPanel
operator|.
name|add
argument_list|(
name|executeConsole
argument_list|,
literal|1
argument_list|,
literal|2
argument_list|)
expr_stmt|;
name|consoleOptionPanel
operator|.
name|add
argument_list|(
name|consoleCommand
argument_list|,
literal|2
argument_list|,
literal|2
argument_list|)
expr_stmt|;
name|consoleOptionPanel
operator|.
name|add
argument_list|(
name|browseButton
argument_list|,
literal|3
argument_list|,
literal|2
argument_list|)
expr_stmt|;
name|consoleOptionPanel
operator|.
name|add
argument_list|(
name|commandDescription
argument_list|,
literal|2
argument_list|,
literal|3
argument_list|)
expr_stmt|;
name|GridPane
name|pdfOptionPanel
init|=
operator|new
name|GridPane
argument_list|()
decl_stmt|;
specifier|final
name|ToggleGroup
name|pdfReaderGroup
init|=
operator|new
name|ToggleGroup
argument_list|()
decl_stmt|;
name|pdfOptionPanel
operator|.
name|add
argument_list|(
name|adobeAcrobatReader
argument_list|,
literal|1
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|pdfOptionPanel
operator|.
name|add
argument_list|(
name|adobeAcrobatReaderPath
argument_list|,
literal|2
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|adobeAcrobatReader
operator|.
name|setToggleGroup
argument_list|(
name|pdfReaderGroup
argument_list|)
expr_stmt|;
name|pdfOptionPanel
operator|.
name|add
argument_list|(
name|browseAdobeAcrobatReader
argument_list|,
literal|3
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|Label
name|fileBrowserCommandDescription
init|=
operator|new
name|Label
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Note: Use the placeholder %0 for the location of the opened library file."
argument_list|,
literal|"%DIR"
argument_list|)
argument_list|)
decl_stmt|;
name|GridPane
name|fileBrowserOptionPanel
init|=
operator|new
name|GridPane
argument_list|()
decl_stmt|;
specifier|final
name|ToggleGroup
name|fileBrowserGroup
init|=
operator|new
name|ToggleGroup
argument_list|()
decl_stmt|;
name|defaultFileBrowser
operator|.
name|setToggleGroup
argument_list|(
name|fileBrowserGroup
argument_list|)
expr_stmt|;
name|executeFileBrowser
operator|.
name|setToggleGroup
argument_list|(
name|fileBrowserGroup
argument_list|)
expr_stmt|;
name|fileBrowserOptionPanel
operator|.
name|add
argument_list|(
name|defaultFileBrowser
argument_list|,
literal|1
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|fileBrowserOptionPanel
operator|.
name|add
argument_list|(
name|executeFileBrowser
argument_list|,
literal|1
argument_list|,
literal|2
argument_list|)
expr_stmt|;
name|fileBrowserOptionPanel
operator|.
name|add
argument_list|(
name|fileBrowserCommand
argument_list|,
literal|2
argument_list|,
literal|2
argument_list|)
expr_stmt|;
name|fileBrowserOptionPanel
operator|.
name|add
argument_list|(
name|fileBrowserButton
argument_list|,
literal|3
argument_list|,
literal|2
argument_list|)
expr_stmt|;
name|fileBrowserOptionPanel
operator|.
name|add
argument_list|(
name|fileBrowserCommandDescription
argument_list|,
literal|2
argument_list|,
literal|3
argument_list|)
expr_stmt|;
if|if
condition|(
name|OS
operator|.
name|WINDOWS
condition|)
block|{
name|browseSumatraReader
operator|.
name|setOnAction
argument_list|(
name|e
lambda|->
name|showSumatraChooser
argument_list|()
argument_list|)
expr_stmt|;
name|pdfOptionPanel
operator|.
name|add
argument_list|(
name|sumatraReader
argument_list|,
literal|1
argument_list|,
literal|2
argument_list|)
expr_stmt|;
name|sumatraReader
operator|.
name|setToggleGroup
argument_list|(
name|pdfReaderGroup
argument_list|)
expr_stmt|;
name|pdfOptionPanel
operator|.
name|add
argument_list|(
name|sumatraReaderPath
argument_list|,
literal|2
argument_list|,
literal|2
argument_list|)
expr_stmt|;
name|pdfOptionPanel
operator|.
name|add
argument_list|(
name|browseSumatraReader
argument_list|,
literal|3
argument_list|,
literal|2
argument_list|)
expr_stmt|;
block|}
comment|// Sending of emails title
name|Label
name|sendingOfEmails
init|=
operator|new
name|Label
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Sending of emails"
argument_list|)
argument_list|)
decl_stmt|;
name|sendingOfEmails
operator|.
name|getStyleClass
argument_list|()
operator|.
name|add
argument_list|(
literal|"sectionHeader"
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|sendingOfEmails
argument_list|,
literal|1
argument_list|,
literal|1
argument_list|)
expr_stmt|;
comment|// Sending of emails configuration
name|HBox
name|sendRefMailBox
init|=
operator|new
name|HBox
argument_list|()
decl_stmt|;
name|sendRefMailBox
operator|.
name|setSpacing
argument_list|(
literal|8
argument_list|)
expr_stmt|;
name|sendRefMailBox
operator|.
name|setAlignment
argument_list|(
name|Pos
operator|.
name|CENTER_LEFT
argument_list|)
expr_stmt|;
name|Label
name|subject
init|=
operator|new
name|Label
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Subject for sending an email with references"
argument_list|)
operator|.
name|concat
argument_list|(
literal|":"
argument_list|)
argument_list|)
decl_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|subject
argument_list|,
literal|1
argument_list|,
literal|2
argument_list|)
expr_stmt|;
name|openFoldersOfAttachedFiles
operator|=
operator|new
name|CheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Automatically open folders of attached files"
argument_list|)
argument_list|)
expr_stmt|;
name|emailSubject
operator|=
operator|new
name|TextField
argument_list|()
expr_stmt|;
name|sendRefMailBox
operator|.
name|getChildren
argument_list|()
operator|.
name|setAll
argument_list|(
name|openFoldersOfAttachedFiles
argument_list|,
name|emailSubject
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|sendRefMailBox
argument_list|,
literal|1
argument_list|,
literal|3
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
operator|new
name|Separator
argument_list|()
argument_list|,
literal|1
argument_list|,
literal|7
argument_list|)
expr_stmt|;
comment|// External programs title
name|Label
name|externalPrograms
init|=
operator|new
name|Label
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"External programs"
argument_list|)
argument_list|)
decl_stmt|;
name|externalPrograms
operator|.
name|getStyleClass
argument_list|()
operator|.
name|add
argument_list|(
literal|"sectionHeader"
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|externalPrograms
argument_list|,
literal|1
argument_list|,
literal|9
argument_list|)
expr_stmt|;
comment|// PushToApplication configuration
name|HBox
name|pushToApplicationHBox
init|=
operator|new
name|HBox
argument_list|()
decl_stmt|;
name|pushToApplicationHBox
operator|.
name|setAlignment
argument_list|(
name|Pos
operator|.
name|CENTER_LEFT
argument_list|)
expr_stmt|;
name|pushToApplicationHBox
operator|.
name|setSpacing
argument_list|(
literal|10
argument_list|)
expr_stmt|;
name|pushToApplicationHBox
operator|.
name|getChildren
argument_list|()
operator|.
name|add
argument_list|(
operator|new
name|Label
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Application to push entries to:"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
operator|new
name|ViewModelListCellFactory
argument_list|<
name|PushToApplication
argument_list|>
argument_list|()
operator|.
name|withText
argument_list|(
name|application
lambda|->
name|application
operator|.
name|getApplicationName
argument_list|()
argument_list|)
operator|.
name|withIcon
argument_list|(
name|application
lambda|->
name|application
operator|.
name|getIcon
argument_list|()
argument_list|)
operator|.
name|install
argument_list|(
name|pushToApplicationComboBox
argument_list|)
expr_stmt|;
name|pushToApplicationComboBox
operator|.
name|getItems
argument_list|()
operator|.
name|addAll
argument_list|(
name|frame
operator|.
name|getPushToApplicationsManager
argument_list|()
operator|.
name|getApplications
argument_list|()
argument_list|)
expr_stmt|;
name|pushToApplicationHBox
operator|.
name|getChildren
argument_list|()
operator|.
name|add
argument_list|(
name|pushToApplicationComboBox
argument_list|)
expr_stmt|;
name|pushToApplicationHBox
operator|.
name|getChildren
argument_list|()
operator|.
name|add
argument_list|(
name|pushToApplicationSettingsButton
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|pushToApplicationHBox
argument_list|,
literal|1
argument_list|,
literal|10
argument_list|)
expr_stmt|;
comment|// Cite command configuration
name|HBox
name|citeCommandBox
init|=
operator|new
name|HBox
argument_list|()
decl_stmt|;
name|citeCommandBox
operator|.
name|setSpacing
argument_list|(
literal|10
argument_list|)
expr_stmt|;
name|citeCommandBox
operator|.
name|setAlignment
argument_list|(
name|Pos
operator|.
name|CENTER_LEFT
argument_list|)
expr_stmt|;
name|Label
name|citeCommandLabel
init|=
operator|new
name|Label
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Cite command"
argument_list|)
operator|+
literal|':'
argument_list|)
decl_stmt|;
name|citeCommandBox
operator|.
name|getChildren
argument_list|()
operator|.
name|setAll
argument_list|(
name|citeCommandLabel
argument_list|,
name|citeCommand
argument_list|,
name|editFileTypes
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|citeCommandBox
argument_list|,
literal|1
argument_list|,
literal|12
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
operator|new
name|Separator
argument_list|()
argument_list|,
literal|1
argument_list|,
literal|16
argument_list|)
expr_stmt|;
comment|// Open console title
name|Label
name|openConsole
init|=
operator|new
name|Label
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Open console"
argument_list|)
argument_list|)
decl_stmt|;
name|openConsole
operator|.
name|getStyleClass
argument_list|()
operator|.
name|add
argument_list|(
literal|"sectionHeader"
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|openConsole
argument_list|,
literal|1
argument_list|,
literal|18
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|consoleOptionPanel
argument_list|,
literal|1
argument_list|,
literal|21
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
operator|new
name|Separator
argument_list|()
argument_list|,
literal|1
argument_list|,
literal|25
argument_list|)
expr_stmt|;
comment|// Open PDF title
name|Label
name|openPdf
init|=
operator|new
name|Label
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Open PDF"
argument_list|)
argument_list|)
decl_stmt|;
name|openPdf
operator|.
name|getStyleClass
argument_list|()
operator|.
name|add
argument_list|(
literal|"sectionHeader"
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|openPdf
argument_list|,
literal|1
argument_list|,
literal|27
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|pdfOptionPanel
argument_list|,
literal|1
argument_list|,
literal|29
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
operator|new
name|Separator
argument_list|()
argument_list|,
literal|1
argument_list|,
literal|33
argument_list|)
expr_stmt|;
comment|// Open file browser title
name|Label
name|openFileBrowser
init|=
operator|new
name|Label
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Open File Browser"
argument_list|)
argument_list|)
decl_stmt|;
name|openFileBrowser
operator|.
name|getStyleClass
argument_list|()
operator|.
name|add
argument_list|(
literal|"sectionHeader"
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|openFileBrowser
argument_list|,
literal|1
argument_list|,
literal|35
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|fileBrowserOptionPanel
argument_list|,
literal|1
argument_list|,
literal|36
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|getBuilder ()
specifier|public
name|Node
name|getBuilder
parameter_list|()
block|{
return|return
name|builder
return|;
block|}
annotation|@
name|Override
DECL|method|setValues ()
specifier|public
name|void
name|setValues
parameter_list|()
block|{
name|emailSubject
operator|.
name|setText
argument_list|(
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|EMAIL_SUBJECT
argument_list|)
argument_list|)
expr_stmt|;
name|openFoldersOfAttachedFiles
operator|.
name|setSelected
argument_list|(
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|OPEN_FOLDERS_OF_ATTACHED_FILES
argument_list|)
argument_list|)
expr_stmt|;
name|pushToApplicationComboBox
operator|.
name|setValue
argument_list|(
name|prefs
operator|.
name|getActivePushToApplication
argument_list|(
name|frame
operator|.
name|getPushToApplicationsManager
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|citeCommand
operator|.
name|setText
argument_list|(
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|CITE_COMMAND
argument_list|)
argument_list|)
expr_stmt|;
name|defaultConsole
operator|.
name|setSelected
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|USE_DEFAULT_CONSOLE_APPLICATION
argument_list|)
argument_list|)
expr_stmt|;
name|executeConsole
operator|.
name|setSelected
argument_list|(
operator|!
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|USE_DEFAULT_CONSOLE_APPLICATION
argument_list|)
argument_list|)
expr_stmt|;
name|consoleCommand
operator|.
name|setText
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|CONSOLE_COMMAND
argument_list|)
argument_list|)
expr_stmt|;
name|defaultFileBrowser
operator|.
name|setSelected
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|USE_DEFAULT_FILE_BROWSER_APPLICATION
argument_list|)
argument_list|)
expr_stmt|;
name|executeFileBrowser
operator|.
name|setSelected
argument_list|(
operator|!
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|USE_DEFAULT_FILE_BROWSER_APPLICATION
argument_list|)
argument_list|)
expr_stmt|;
name|fileBrowserCommand
operator|.
name|setText
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|FILE_BROWSER_COMMAND
argument_list|)
argument_list|)
expr_stmt|;
name|adobeAcrobatReaderPath
operator|.
name|setText
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|ADOBE_ACROBAT_COMMAND
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|OS
operator|.
name|WINDOWS
condition|)
block|{
name|sumatraReaderPath
operator|.
name|setText
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|SUMATRA_PDF_COMMAND
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|USE_PDF_READER
argument_list|)
operator|.
name|equals
argument_list|(
name|adobeAcrobatReaderPath
operator|.
name|getText
argument_list|()
argument_list|)
condition|)
block|{
name|adobeAcrobatReader
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|USE_PDF_READER
argument_list|)
operator|.
name|equals
argument_list|(
name|sumatraReaderPath
operator|.
name|getText
argument_list|()
argument_list|)
condition|)
block|{
name|sumatraReader
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
name|updateExecuteConsoleButtonAndFieldEnabledState
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|storeSettings ()
specifier|public
name|void
name|storeSettings
parameter_list|()
block|{
name|prefs
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|EMAIL_SUBJECT
argument_list|,
name|emailSubject
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|OPEN_FOLDERS_OF_ATTACHED_FILES
argument_list|,
name|openFoldersOfAttachedFiles
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|setActivePushToApplication
argument_list|(
name|pushToApplicationComboBox
operator|.
name|getValue
argument_list|()
argument_list|,
name|frame
operator|.
name|getPushToApplicationsManager
argument_list|()
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|CITE_COMMAND
argument_list|,
name|citeCommand
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|USE_DEFAULT_CONSOLE_APPLICATION
argument_list|,
name|defaultConsole
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|CONSOLE_COMMAND
argument_list|,
name|consoleCommand
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|ADOBE_ACROBAT_COMMAND
argument_list|,
name|adobeAcrobatReaderPath
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|USE_DEFAULT_FILE_BROWSER_APPLICATION
argument_list|,
name|defaultFileBrowser
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|StringUtil
operator|.
name|isNotBlank
argument_list|(
name|fileBrowserCommand
operator|.
name|getText
argument_list|()
argument_list|)
condition|)
block|{
name|prefs
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|FILE_BROWSER_COMMAND
argument_list|,
name|fileBrowserCommand
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|USE_DEFAULT_FILE_BROWSER_APPLICATION
argument_list|,
literal|true
argument_list|)
expr_stmt|;
comment|//default if no command specified
block|}
if|if
condition|(
name|OS
operator|.
name|WINDOWS
condition|)
block|{
name|prefs
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|SUMATRA_PDF_COMMAND
argument_list|,
name|sumatraReaderPath
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|readerSelected
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|validateSettings ()
specifier|public
name|boolean
name|validateSettings
parameter_list|()
block|{
return|return
literal|true
return|;
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
literal|"External programs"
argument_list|)
return|;
block|}
DECL|method|updateExecuteConsoleButtonAndFieldEnabledState ()
specifier|private
name|void
name|updateExecuteConsoleButtonAndFieldEnabledState
parameter_list|()
block|{
name|browseButton
operator|.
name|setDisable
argument_list|(
operator|!
name|executeConsole
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|consoleCommand
operator|.
name|setDisable
argument_list|(
operator|!
name|executeConsole
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|showPushToApplicationSettings ()
specifier|private
name|void
name|showPushToApplicationSettings
parameter_list|()
block|{
name|PushToApplicationsManager
name|manager
init|=
name|frame
operator|.
name|getPushToApplicationsManager
argument_list|()
decl_stmt|;
name|PushToApplication
name|selectedApplication
init|=
name|pushToApplicationComboBox
operator|.
name|getValue
argument_list|()
decl_stmt|;
name|PushToApplicationSettings
name|settings
init|=
name|manager
operator|.
name|getSettings
argument_list|(
name|selectedApplication
argument_list|)
decl_stmt|;
name|DialogPane
name|dialogPane
init|=
operator|new
name|DialogPane
argument_list|()
decl_stmt|;
name|dialogPane
operator|.
name|setContent
argument_list|(
name|settings
operator|.
name|getSettingsPane
argument_list|()
argument_list|)
expr_stmt|;
name|dialogService
operator|.
name|showCustomDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Application settings"
argument_list|)
argument_list|,
name|dialogPane
argument_list|,
name|ButtonType
operator|.
name|OK
argument_list|,
name|ButtonType
operator|.
name|CANCEL
argument_list|)
operator|.
name|ifPresent
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
name|settings
operator|.
name|storeSettings
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
DECL|method|showConsoleChooser ()
specifier|private
name|void
name|showConsoleChooser
parameter_list|()
block|{
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
name|consoleCommand
operator|.
name|setText
argument_list|(
name|file
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
DECL|method|showAdobeChooser ()
specifier|private
name|void
name|showAdobeChooser
parameter_list|()
block|{
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
name|adobeAcrobatReaderPath
operator|.
name|setText
argument_list|(
name|file
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
DECL|method|showSumatraChooser ()
specifier|private
name|void
name|showSumatraChooser
parameter_list|()
block|{
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
name|sumatraReaderPath
operator|.
name|setText
argument_list|(
name|file
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
DECL|method|showFileBrowserCommandChooser ()
specifier|private
name|void
name|showFileBrowserCommandChooser
parameter_list|()
block|{
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
name|fileBrowserCommand
operator|.
name|setText
argument_list|(
name|file
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
DECL|method|readerSelected ()
specifier|private
name|void
name|readerSelected
parameter_list|()
block|{
if|if
condition|(
name|adobeAcrobatReader
operator|.
name|isSelected
argument_list|()
condition|)
block|{
name|prefs
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|USE_PDF_READER
argument_list|,
name|adobeAcrobatReaderPath
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|sumatraReader
operator|.
name|isSelected
argument_list|()
condition|)
block|{
name|prefs
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|USE_PDF_READER
argument_list|,
name|sumatraReaderPath
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

