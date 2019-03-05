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
name|javax
operator|.
name|swing
operator|.
name|JFileChooser
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
name|Pane
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
name|ExternalFileTypeEditor
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
name|PushToApplicationSettingsDialog
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
name|PushToApplications
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
name|preferences
operator|.
name|JabRefPreferences
import|;
end_import

begin_class
DECL|class|ExternalTab
class|class
name|ExternalTab
extends|extends
name|Pane
implements|implements
name|PrefsTab
block|{
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
DECL|method|ExternalTab (JabRefFrame frame, PreferencesDialog prefsDiag, JabRefPreferences prefs)
specifier|public
name|ExternalTab
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|PreferencesDialog
name|prefsDiag
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
name|ExternalFileTypeEditor
operator|.
name|getAction
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
name|emailSubject
operator|=
operator|new
name|TextField
argument_list|()
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|emailSubject
argument_list|,
literal|2
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
name|builder
operator|.
name|add
argument_list|(
name|openFoldersOfAttachedFiles
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
name|Label
argument_list|(
literal|""
argument_list|)
argument_list|,
literal|1
argument_list|,
literal|4
argument_list|)
expr_stmt|;
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
literal|5
argument_list|)
expr_stmt|;
name|GridPane
name|butpan
init|=
operator|new
name|GridPane
argument_list|()
decl_stmt|;
name|int
name|index
init|=
literal|0
decl_stmt|;
for|for
control|(
name|PushToApplication
name|pushToApplication
range|:
name|frame
operator|.
name|getPushApplications
argument_list|()
operator|.
name|getApplications
argument_list|()
control|)
block|{
name|addSettingsButton
argument_list|(
name|pushToApplication
argument_list|,
name|butpan
argument_list|,
name|index
argument_list|)
expr_stmt|;
name|index
operator|++
expr_stmt|;
block|}
name|builder
operator|.
name|add
argument_list|(
name|butpan
argument_list|,
literal|1
argument_list|,
literal|6
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
name|builder
operator|.
name|add
argument_list|(
name|citeCommandLabel
argument_list|,
literal|1
argument_list|,
literal|7
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|citeCommand
argument_list|,
literal|2
argument_list|,
literal|7
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|editFileTypes
argument_list|,
literal|1
argument_list|,
literal|8
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
operator|new
name|Label
argument_list|(
literal|""
argument_list|)
argument_list|,
literal|1
argument_list|,
literal|9
argument_list|)
expr_stmt|;
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
literal|10
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
literal|11
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
operator|new
name|Label
argument_list|(
literal|""
argument_list|)
argument_list|,
literal|1
argument_list|,
literal|12
argument_list|)
expr_stmt|;
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
literal|12
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
literal|13
argument_list|)
expr_stmt|;
name|FileDialogConfiguration
name|fileDialogConfiguration
init|=
operator|new
name|FileDialogConfiguration
operator|.
name|Builder
argument_list|()
operator|.
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
DECL|method|addSettingsButton (final PushToApplication application, GridPane panel, int index)
specifier|private
name|void
name|addSettingsButton
parameter_list|(
specifier|final
name|PushToApplication
name|application
parameter_list|,
name|GridPane
name|panel
parameter_list|,
name|int
name|index
parameter_list|)
block|{
name|PushToApplicationSettings
name|settings
init|=
name|PushToApplications
operator|.
name|getSettings
argument_list|(
name|application
argument_list|)
decl_stmt|;
name|Button
name|button
init|=
operator|new
name|Button
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Settings for %0"
argument_list|,
name|application
operator|.
name|getApplicationName
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
name|button
operator|.
name|setPrefSize
argument_list|(
literal|150
argument_list|,
literal|20
argument_list|)
expr_stmt|;
name|button
operator|.
name|setOnAction
argument_list|(
name|e
lambda|->
name|PushToApplicationSettingsDialog
operator|.
name|showSettingsDialog
argument_list|(
literal|null
argument_list|,
name|settings
argument_list|,
name|index
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
operator|(
name|index
operator|%
literal|2
operator|)
operator|==
literal|0
condition|)
block|{
name|panel
operator|.
name|add
argument_list|(
name|button
argument_list|,
literal|1
argument_list|,
operator|(
name|index
operator|/
literal|2
operator|)
operator|+
literal|1
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|panel
operator|.
name|add
argument_list|(
name|button
argument_list|,
literal|2
argument_list|,
operator|(
name|index
operator|/
literal|2
operator|)
operator|+
literal|1
argument_list|)
expr_stmt|;
block|}
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
DECL|method|showConsoleChooser ()
specifier|private
name|void
name|showConsoleChooser
parameter_list|()
block|{
name|JFileChooser
name|consoleChooser
init|=
operator|new
name|JFileChooser
argument_list|()
decl_stmt|;
name|int
name|answer
init|=
name|consoleChooser
operator|.
name|showOpenDialog
argument_list|(
name|ExternalTab
operator|.
name|this
argument_list|)
decl_stmt|;
if|if
condition|(
name|answer
operator|==
name|JFileChooser
operator|.
name|APPROVE_OPTION
condition|)
block|{
name|consoleCommand
operator|.
name|setText
argument_list|(
name|consoleChooser
operator|.
name|getSelectedFile
argument_list|()
operator|.
name|getAbsolutePath
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|showAdobeChooser ()
specifier|private
name|void
name|showAdobeChooser
parameter_list|()
block|{
name|JFileChooser
name|adobeChooser
init|=
operator|new
name|JFileChooser
argument_list|()
decl_stmt|;
name|int
name|answer
init|=
name|adobeChooser
operator|.
name|showOpenDialog
argument_list|(
name|ExternalTab
operator|.
name|this
argument_list|)
decl_stmt|;
if|if
condition|(
name|answer
operator|==
name|JFileChooser
operator|.
name|APPROVE_OPTION
condition|)
block|{
name|adobeAcrobatReaderPath
operator|.
name|setText
argument_list|(
name|adobeChooser
operator|.
name|getSelectedFile
argument_list|()
operator|.
name|getAbsolutePath
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|showSumatraChooser ()
specifier|private
name|void
name|showSumatraChooser
parameter_list|()
block|{
name|JFileChooser
name|adobeChooser
init|=
operator|new
name|JFileChooser
argument_list|()
decl_stmt|;
name|int
name|answer
init|=
name|adobeChooser
operator|.
name|showOpenDialog
argument_list|(
name|ExternalTab
operator|.
name|this
argument_list|)
decl_stmt|;
if|if
condition|(
name|answer
operator|==
name|JFileChooser
operator|.
name|APPROVE_OPTION
condition|)
block|{
name|sumatraReaderPath
operator|.
name|setText
argument_list|(
name|adobeChooser
operator|.
name|getSelectedFile
argument_list|()
operator|.
name|getAbsolutePath
argument_list|()
argument_list|)
expr_stmt|;
block|}
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

