begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.preftabs
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|preftabs
package|;
end_package

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|BorderLayout
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|CardLayout
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Component
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|GridLayout
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|ActionEvent
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
name|prefs
operator|.
name|BackingStoreException
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|AbstractAction
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|BorderFactory
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JButton
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JComponent
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JList
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JPanel
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JScrollPane
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|ListSelectionModel
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
name|layout
operator|.
name|Region
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
name|DefaultTaskExecutor
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
name|shared
operator|.
name|prefs
operator|.
name|SharedDatabasePreferences
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
name|FileType
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
comment|/**  * Preferences dialog. Contains a TabbedPane, and tabs will be defined in  * separate classes. Tabs MUST implement the PrefsTab interface, since this  * dialog will call the storeSettings() method of all tabs when the user presses  * ok.  *  * With this design, it should be very easy to add new tabs later.  *  */
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
DECL|field|main
specifier|private
specifier|final
name|JPanel
name|main
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
name|setPrefSize
argument_list|(
literal|1000
argument_list|,
literal|800
argument_list|)
expr_stmt|;
name|getDialogPane
argument_list|()
operator|.
name|setMinHeight
argument_list|(
name|Region
operator|.
name|USE_PREF_SIZE
argument_list|)
expr_stmt|;
name|setResizable
argument_list|(
literal|true
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
name|ButtonBar
operator|.
name|ButtonData
operator|.
name|APPLY
argument_list|)
decl_stmt|;
name|getDialogPane
argument_list|()
operator|.
name|getButtonTypes
argument_list|()
operator|.
name|setAll
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
name|storeAllSettings
argument_list|()
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
name|main
operator|=
operator|new
name|JPanel
argument_list|()
expr_stmt|;
name|ControlHelper
operator|.
name|setSwingContent
argument_list|(
name|getDialogPane
argument_list|()
argument_list|,
name|constructSwingContent
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|constructSwingContent ()
specifier|private
name|JComponent
name|constructSwingContent
parameter_list|()
block|{
name|JPanel
name|mainPanel
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
specifier|final
name|CardLayout
name|cardLayout
init|=
operator|new
name|CardLayout
argument_list|()
decl_stmt|;
name|main
operator|.
name|setLayout
argument_list|(
name|cardLayout
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|PrefsTab
argument_list|>
name|tabs
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|tabs
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
name|tabs
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
name|tabs
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
name|tabs
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
name|tabs
operator|.
name|add
argument_list|(
operator|new
name|PreviewPrefsTab
argument_list|(
name|dialogService
argument_list|)
argument_list|)
expr_stmt|;
name|tabs
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
name|tabs
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
name|tabs
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
name|tabs
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
name|tabs
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
name|tabs
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
name|tabs
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
name|tabs
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
name|tabs
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
name|tabs
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
name|tabs
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
comment|// add all tabs
name|tabs
operator|.
name|forEach
argument_list|(
name|tab
lambda|->
name|main
operator|.
name|add
argument_list|(
operator|(
name|Component
operator|)
name|tab
argument_list|,
name|tab
operator|.
name|getTabName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|mainPanel
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createEtchedBorder
argument_list|()
argument_list|)
expr_stmt|;
name|String
index|[]
name|tabNames
init|=
name|tabs
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|PrefsTab
operator|::
name|getTabName
argument_list|)
operator|.
name|toArray
argument_list|(
name|String
index|[]
operator|::
operator|new
argument_list|)
decl_stmt|;
name|JList
argument_list|<
name|String
argument_list|>
name|chooser
init|=
operator|new
name|JList
argument_list|<>
argument_list|(
name|tabNames
argument_list|)
decl_stmt|;
name|chooser
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createEtchedBorder
argument_list|()
argument_list|)
expr_stmt|;
comment|// Set a prototype value to control the width of the list:
name|chooser
operator|.
name|setPrototypeCellValue
argument_list|(
literal|"This should be wide enough"
argument_list|)
expr_stmt|;
name|chooser
operator|.
name|setSelectedIndex
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|chooser
operator|.
name|setSelectionMode
argument_list|(
name|ListSelectionModel
operator|.
name|SINGLE_SELECTION
argument_list|)
expr_stmt|;
comment|// Add the selection listener that will show the correct panel when
comment|// selection changes:
name|chooser
operator|.
name|addListSelectionListener
argument_list|(
name|e
lambda|->
block|{
if|if
condition|(
name|e
operator|.
name|getValueIsAdjusting
argument_list|()
condition|)
block|{
return|return;
block|}
name|String
name|o
init|=
name|chooser
operator|.
name|getSelectedValue
argument_list|()
decl_stmt|;
name|cardLayout
operator|.
name|show
argument_list|(
name|main
argument_list|,
name|o
argument_list|)
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
name|JPanel
name|buttons
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|buttons
operator|.
name|setLayout
argument_list|(
operator|new
name|GridLayout
argument_list|(
literal|4
argument_list|,
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|JButton
name|importPreferences
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Import preferences"
argument_list|)
argument_list|)
decl_stmt|;
name|buttons
operator|.
name|add
argument_list|(
name|importPreferences
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|JButton
name|exportPreferences
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Export preferences"
argument_list|)
argument_list|)
decl_stmt|;
name|buttons
operator|.
name|add
argument_list|(
name|exportPreferences
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|JButton
name|showPreferences
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Show preferences"
argument_list|)
argument_list|)
decl_stmt|;
name|buttons
operator|.
name|add
argument_list|(
name|showPreferences
argument_list|,
literal|2
argument_list|)
expr_stmt|;
name|JButton
name|resetPreferences
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Reset preferences"
argument_list|)
argument_list|)
decl_stmt|;
name|buttons
operator|.
name|add
argument_list|(
name|resetPreferences
argument_list|,
literal|3
argument_list|)
expr_stmt|;
name|JPanel
name|westPanel
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|westPanel
operator|.
name|setLayout
argument_list|(
operator|new
name|BorderLayout
argument_list|()
argument_list|)
expr_stmt|;
name|westPanel
operator|.
name|add
argument_list|(
name|chooser
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|westPanel
operator|.
name|add
argument_list|(
name|buttons
argument_list|,
name|BorderLayout
operator|.
name|SOUTH
argument_list|)
expr_stmt|;
name|mainPanel
operator|.
name|setLayout
argument_list|(
operator|new
name|BorderLayout
argument_list|()
argument_list|)
expr_stmt|;
name|mainPanel
operator|.
name|add
argument_list|(
name|putPanelInScrollPane
argument_list|(
name|main
argument_list|)
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|mainPanel
operator|.
name|add
argument_list|(
name|putPanelInScrollPane
argument_list|(
name|westPanel
argument_list|)
argument_list|,
name|BorderLayout
operator|.
name|WEST
argument_list|)
expr_stmt|;
comment|// TODO: Key bindings:
comment|// KeyBinder.bindCloseDialogKeyToCancelAction(this.getRootPane(), cancelAction);
comment|// Import and export actions:
name|exportPreferences
operator|.
name|setToolTipText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Export preferences to file"
argument_list|)
argument_list|)
expr_stmt|;
name|exportPreferences
operator|.
name|addActionListener
argument_list|(
operator|new
name|ExportAction
argument_list|()
argument_list|)
expr_stmt|;
name|importPreferences
operator|.
name|setToolTipText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Import preferences from file"
argument_list|)
argument_list|)
expr_stmt|;
name|importPreferences
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
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
name|FileType
operator|.
name|XML
argument_list|)
operator|.
name|withDefaultExtension
argument_list|(
name|FileType
operator|.
name|XML
argument_list|)
operator|.
name|withInitialDirectory
argument_list|(
name|getPrefsExportPath
argument_list|()
argument_list|)
operator|.
name|build
argument_list|()
decl_stmt|;
name|Optional
argument_list|<
name|Path
argument_list|>
name|fileName
init|=
name|DefaultTaskExecutor
operator|.
name|runInJavaFXThread
argument_list|(
parameter_list|()
lambda|->
name|dialogService
operator|.
name|showFileOpenDialog
argument_list|(
name|fileDialogConfiguration
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|fileName
operator|.
name|isPresent
argument_list|()
condition|)
block|{
try|try
block|{
name|prefs
operator|.
name|importPreferences
argument_list|(
name|fileName
operator|.
name|get
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|updateAfterPreferenceChanges
argument_list|()
expr_stmt|;
name|DefaultTaskExecutor
operator|.
name|runInJavaFXThread
argument_list|(
parameter_list|()
lambda|->
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
name|DefaultTaskExecutor
operator|.
name|runInJavaFXThread
argument_list|(
parameter_list|()
lambda|->
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
argument_list|)
expr_stmt|;
block|}
block|}
block|}
argument_list|)
expr_stmt|;
name|showPreferences
operator|.
name|addActionListener
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
argument_list|,
literal|null
argument_list|)
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
argument_list|)
expr_stmt|;
name|resetPreferences
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
block|{
name|boolean
name|resetPreferencesClicked
init|=
name|DefaultTaskExecutor
operator|.
name|runInJavaFXThread
argument_list|(
parameter_list|()
lambda|->
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
argument_list|)
decl_stmt|;
if|if
condition|(
name|resetPreferencesClicked
condition|)
block|{
try|try
block|{
name|prefs
operator|.
name|clear
argument_list|()
expr_stmt|;
operator|new
name|SharedDatabasePreferences
argument_list|()
operator|.
name|clear
argument_list|()
expr_stmt|;
name|DefaultTaskExecutor
operator|.
name|runInJavaFXThread
argument_list|(
parameter_list|()
lambda|->
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
name|DefaultTaskExecutor
operator|.
name|runInJavaFXThread
argument_list|(
parameter_list|()
lambda|->
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
argument_list|)
expr_stmt|;
block|}
name|updateAfterPreferenceChanges
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|setValues
argument_list|()
expr_stmt|;
return|return
name|mainPanel
return|;
block|}
DECL|method|putPanelInScrollPane (JPanel panel)
specifier|private
name|JScrollPane
name|putPanelInScrollPane
parameter_list|(
name|JPanel
name|panel
parameter_list|)
block|{
name|JScrollPane
name|scrollPane
init|=
operator|new
name|JScrollPane
argument_list|(
name|panel
argument_list|)
decl_stmt|;
name|scrollPane
operator|.
name|setHorizontalScrollBarPolicy
argument_list|(
name|JScrollPane
operator|.
name|HORIZONTAL_SCROLLBAR_AS_NEEDED
argument_list|)
expr_stmt|;
name|scrollPane
operator|.
name|setVerticalScrollBarPolicy
argument_list|(
name|JScrollPane
operator|.
name|VERTICAL_SCROLLBAR_AS_NEEDED
argument_list|)
expr_stmt|;
name|scrollPane
operator|.
name|setBorder
argument_list|(
literal|null
argument_list|)
expr_stmt|;
return|return
name|scrollPane
return|;
block|}
DECL|method|getPrefsExportPath ()
specifier|private
name|String
name|getPrefsExportPath
parameter_list|()
block|{
return|return
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|PREFS_EXPORT_PATH
argument_list|)
return|;
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
name|Globals
operator|.
name|exportFactory
operator|=
name|ExporterFactory
operator|.
name|create
argument_list|(
name|prefs
argument_list|,
name|Globals
operator|.
name|journalAbbreviationLoader
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
name|Component
index|[]
name|preferenceTabs
init|=
name|main
operator|.
name|getComponents
argument_list|()
decl_stmt|;
for|for
control|(
name|Component
name|tab
range|:
name|preferenceTabs
control|)
block|{
if|if
condition|(
operator|!
operator|(
operator|(
name|PrefsTab
operator|)
name|tab
operator|)
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
name|Component
name|tab
range|:
name|preferenceTabs
control|)
block|{
operator|(
operator|(
name|PrefsTab
operator|)
name|tab
operator|)
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
name|int
name|count
init|=
name|main
operator|.
name|getComponentCount
argument_list|()
decl_stmt|;
name|Component
index|[]
name|comps
init|=
name|main
operator|.
name|getComponents
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|count
condition|;
name|i
operator|++
control|)
block|{
operator|(
operator|(
name|PrefsTab
operator|)
name|comps
index|[
name|i
index|]
operator|)
operator|.
name|setValues
argument_list|()
expr_stmt|;
block|}
block|}
DECL|class|ExportAction
class|class
name|ExportAction
extends|extends
name|AbstractAction
block|{
DECL|method|ExportAction ()
specifier|public
name|ExportAction
parameter_list|()
block|{
name|super
argument_list|(
literal|"Export"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|actionPerformed (ActionEvent e)
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
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
name|FileType
operator|.
name|XML
argument_list|)
operator|.
name|withDefaultExtension
argument_list|(
name|FileType
operator|.
name|XML
argument_list|)
operator|.
name|withInitialDirectory
argument_list|(
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|WORKING_DIRECTORY
argument_list|)
argument_list|)
operator|.
name|build
argument_list|()
decl_stmt|;
name|Optional
argument_list|<
name|Path
argument_list|>
name|path
init|=
name|DefaultTaskExecutor
operator|.
name|runInJavaFXThread
argument_list|(
parameter_list|()
lambda|->
name|dialogService
operator|.
name|showFileSaveDialog
argument_list|(
name|fileDialogConfiguration
argument_list|)
argument_list|)
decl_stmt|;
name|path
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
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|PREFS_EXPORT_PATH
argument_list|,
name|exportFile
operator|.
name|toString
argument_list|()
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
block|}
end_class

end_unit

