begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.preftabs
package|package
name|net
operator|.
name|sf
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
name|JDialog
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
name|JOptionPane
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
name|ListSelectionModel
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|Globals
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|JabRefException
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|FileDialog
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
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
name|net
operator|.
name|sf
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|keyboard
operator|.
name|KeyBinder
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|maintable
operator|.
name|MainTable
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|exporter
operator|.
name|ExportFormat
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|exporter
operator|.
name|ExportFormats
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
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
name|net
operator|.
name|sf
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
name|net
operator|.
name|sf
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|util
operator|.
name|FileExtensions
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
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
name|net
operator|.
name|sf
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
name|net
operator|.
name|sf
operator|.
name|jabref
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
name|com
operator|.
name|jgoodies
operator|.
name|forms
operator|.
name|builder
operator|.
name|ButtonBarBuilder
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|Log
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|LogFactory
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
name|JDialog
block|{
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Log
name|LOGGER
init|=
name|LogFactory
operator|.
name|getLog
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
DECL|field|frame
specifier|private
specifier|final
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|importPreferences
specifier|private
specifier|final
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
DECL|field|exportPreferences
specifier|private
specifier|final
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
DECL|field|showPreferences
specifier|private
specifier|final
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
DECL|field|resetPreferences
specifier|private
specifier|final
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
DECL|method|PreferencesDialog (JabRefFrame parent)
specifier|public
name|PreferencesDialog
parameter_list|(
name|JabRefFrame
name|parent
parameter_list|)
block|{
name|super
argument_list|(
name|parent
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"JabRef preferences"
argument_list|)
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|JabRefPreferences
name|prefs
init|=
name|JabRefPreferences
operator|.
name|getInstance
argument_list|()
decl_stmt|;
name|frame
operator|=
name|parent
expr_stmt|;
name|main
operator|=
operator|new
name|JPanel
argument_list|()
expr_stmt|;
name|JPanel
name|mainPanel
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|JPanel
name|lower
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|getContentPane
argument_list|()
operator|.
name|setLayout
argument_list|(
operator|new
name|BorderLayout
argument_list|()
argument_list|)
expr_stmt|;
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|mainPanel
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|lower
argument_list|,
name|BorderLayout
operator|.
name|SOUTH
argument_list|)
expr_stmt|;
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
name|frame
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
name|FileSortTab
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
name|frame
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
name|AppearancePrefsTab
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
name|parent
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
name|parent
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
name|PreviewPrefsTab
argument_list|()
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
name|AdvancedTab
argument_list|(
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
name|buttons
operator|.
name|add
argument_list|(
name|importPreferences
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|buttons
operator|.
name|add
argument_list|(
name|exportPreferences
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|buttons
operator|.
name|add
argument_list|(
name|showPreferences
argument_list|,
literal|2
argument_list|)
expr_stmt|;
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
name|main
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
name|westPanel
argument_list|,
name|BorderLayout
operator|.
name|WEST
argument_list|)
expr_stmt|;
name|JButton
name|ok
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"OK"
argument_list|)
argument_list|)
decl_stmt|;
name|JButton
name|cancel
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Cancel"
argument_list|)
argument_list|)
decl_stmt|;
name|ok
operator|.
name|addActionListener
argument_list|(
operator|new
name|OkAction
argument_list|()
argument_list|)
expr_stmt|;
name|CancelAction
name|cancelAction
init|=
operator|new
name|CancelAction
argument_list|()
decl_stmt|;
name|cancel
operator|.
name|addActionListener
argument_list|(
name|cancelAction
argument_list|)
expr_stmt|;
name|lower
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createEmptyBorder
argument_list|(
literal|2
argument_list|,
literal|2
argument_list|,
literal|2
argument_list|,
literal|2
argument_list|)
argument_list|)
expr_stmt|;
name|ButtonBarBuilder
name|buttonBarBuilder
init|=
operator|new
name|ButtonBarBuilder
argument_list|(
name|lower
argument_list|)
decl_stmt|;
name|buttonBarBuilder
operator|.
name|addGlue
argument_list|()
expr_stmt|;
name|buttonBarBuilder
operator|.
name|addButton
argument_list|(
name|ok
argument_list|)
expr_stmt|;
name|buttonBarBuilder
operator|.
name|addButton
argument_list|(
name|cancel
argument_list|)
expr_stmt|;
name|buttonBarBuilder
operator|.
name|addGlue
argument_list|()
expr_stmt|;
comment|// Key bindings:
name|KeyBinder
operator|.
name|bindCloseDialogKeyToCancelAction
argument_list|(
name|this
operator|.
name|getRootPane
argument_list|()
argument_list|,
name|cancelAction
argument_list|)
expr_stmt|;
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
name|e
lambda|->
block|{
name|FileDialog
name|dialog
init|=
operator|new
name|FileDialog
argument_list|(
name|frame
argument_list|)
operator|.
name|withExtension
argument_list|(
name|FileExtensions
operator|.
name|XML
argument_list|)
decl_stmt|;
name|dialog
operator|.
name|setDefaultExtension
argument_list|(
name|FileExtensions
operator|.
name|XML
argument_list|)
expr_stmt|;
name|Optional
argument_list|<
name|Path
argument_list|>
name|path
init|=
name|dialog
operator|.
name|saveNewFile
argument_list|()
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
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|PreferencesDialog
operator|.
name|this
argument_list|,
name|ex
operator|.
name|getLocalizedMessage
argument_list|()
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Export preferences"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
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
name|FileDialog
name|dialog
init|=
operator|new
name|FileDialog
argument_list|(
name|frame
argument_list|)
operator|.
name|withExtension
argument_list|(
name|FileExtensions
operator|.
name|XML
argument_list|)
decl_stmt|;
name|dialog
operator|.
name|setDefaultExtension
argument_list|(
name|FileExtensions
operator|.
name|XML
argument_list|)
expr_stmt|;
name|Optional
argument_list|<
name|Path
argument_list|>
name|fileName
init|=
name|dialog
operator|.
name|showDialogAndGetSelectedFile
argument_list|()
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
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|PreferencesDialog
operator|.
name|this
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"You must restart JabRef for this to come into effect."
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Import preferences"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|WARNING_MESSAGE
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
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|PreferencesDialog
operator|.
name|this
argument_list|,
name|ex
operator|.
name|getLocalizedMessage
argument_list|()
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Import preferences"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
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
name|Globals
operator|.
name|prefs
argument_list|)
argument_list|,
name|frame
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
if|if
condition|(
name|JOptionPane
operator|.
name|showConfirmDialog
argument_list|(
name|PreferencesDialog
operator|.
name|this
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
name|JOptionPane
operator|.
name|OK_CANCEL_OPTION
argument_list|)
operator|==
name|JOptionPane
operator|.
name|OK_OPTION
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
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|PreferencesDialog
operator|.
name|this
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"You must restart JabRef for this to come into effect."
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Reset preferences"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|WARNING_MESSAGE
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
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|PreferencesDialog
operator|.
name|this
argument_list|,
name|ex
operator|.
name|getLocalizedMessage
argument_list|()
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Reset preferences"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
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
name|pack
argument_list|()
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
name|Map
argument_list|<
name|String
argument_list|,
name|ExportFormat
argument_list|>
name|customFormats
init|=
name|Globals
operator|.
name|prefs
operator|.
name|customExports
operator|.
name|getCustomExportFormats
argument_list|(
name|Globals
operator|.
name|prefs
argument_list|,
name|Globals
operator|.
name|journalAbbreviationLoader
argument_list|)
decl_stmt|;
name|LayoutFormatterPreferences
name|layoutPreferences
init|=
name|Globals
operator|.
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
name|SavePreferences
operator|.
name|loadForExportFromPreferences
argument_list|(
name|Globals
operator|.
name|prefs
argument_list|)
decl_stmt|;
name|ExportFormats
operator|.
name|initAllExports
argument_list|(
name|customFormats
argument_list|,
name|layoutPreferences
argument_list|,
name|savePreferences
argument_list|)
expr_stmt|;
name|frame
operator|.
name|removeCachedEntryEditors
argument_list|()
expr_stmt|;
name|Globals
operator|.
name|prefs
operator|.
name|updateEntryEditorTabList
argument_list|()
expr_stmt|;
block|}
DECL|class|OkAction
class|class
name|OkAction
extends|extends
name|AbstractAction
block|{
DECL|method|OkAction ()
specifier|public
name|OkAction
parameter_list|()
block|{
name|super
argument_list|(
literal|"OK"
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
comment|// First check that all tabs are ready to close:
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
if|if
condition|(
operator|!
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
name|storeSettings
argument_list|()
expr_stmt|;
block|}
name|Globals
operator|.
name|prefs
operator|.
name|flush
argument_list|()
expr_stmt|;
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|MainTable
operator|.
name|updateRenderers
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
name|getGroupSelector
argument_list|()
operator|.
name|revalidateGroups
argument_list|()
expr_stmt|;
comment|// icons may have changed
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
DECL|class|CancelAction
class|class
name|CancelAction
extends|extends
name|AbstractAction
block|{
DECL|method|CancelAction ()
specifier|public
name|CancelAction
parameter_list|()
block|{
name|super
argument_list|(
literal|"Cancel"
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
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

