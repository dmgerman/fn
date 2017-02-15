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
name|GridBagConstraints
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|GridBagLayout
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
name|Insets
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
name|ButtonGroup
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
name|JCheckBox
import|;
end_import

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
name|javax
operator|.
name|swing
operator|.
name|JLabel
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
name|JRadioButton
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JTextField
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
name|externalfiletype
operator|.
name|ExternalFileTypeEditor
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
name|push
operator|.
name|PushToApplication
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
name|push
operator|.
name|PushToApplicationButton
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
name|util
operator|.
name|OS
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
name|com
operator|.
name|jgoodies
operator|.
name|forms
operator|.
name|builder
operator|.
name|DefaultFormBuilder
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
name|layout
operator|.
name|FormLayout
import|;
end_import

begin_class
DECL|class|ExternalTab
class|class
name|ExternalTab
extends|extends
name|JPanel
implements|implements
name|PrefsTab
block|{
DECL|field|prefs
specifier|private
specifier|final
name|JabRefPreferences
name|prefs
decl_stmt|;
DECL|field|frame
specifier|private
specifier|final
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|emailSubject
specifier|private
specifier|final
name|JTextField
name|emailSubject
decl_stmt|;
DECL|field|citeCommand
specifier|private
specifier|final
name|JTextField
name|citeCommand
decl_stmt|;
DECL|field|openFoldersOfAttachedFiles
specifier|private
specifier|final
name|JCheckBox
name|openFoldersOfAttachedFiles
decl_stmt|;
DECL|field|defaultConsole
specifier|private
specifier|final
name|JRadioButton
name|defaultConsole
decl_stmt|;
DECL|field|executeConsole
specifier|private
specifier|final
name|JRadioButton
name|executeConsole
decl_stmt|;
DECL|field|consoleCommand
specifier|private
specifier|final
name|JTextField
name|consoleCommand
decl_stmt|;
DECL|field|browseButton
specifier|private
specifier|final
name|JButton
name|browseButton
decl_stmt|;
DECL|field|adobeAcrobatReader
specifier|private
specifier|final
name|JRadioButton
name|adobeAcrobatReader
decl_stmt|;
DECL|field|sumatraReader
specifier|private
specifier|final
name|JRadioButton
name|sumatraReader
decl_stmt|;
DECL|field|adobeAcrobatReaderPath
specifier|private
specifier|final
name|JTextField
name|adobeAcrobatReaderPath
decl_stmt|;
DECL|field|sumatraReaderPath
specifier|private
specifier|final
name|JTextField
name|sumatraReaderPath
decl_stmt|;
DECL|field|browseAdobeAcrobatReader
specifier|private
specifier|final
name|JButton
name|browseAdobeAcrobatReader
decl_stmt|;
DECL|field|browseSumatraReader
specifier|private
specifier|final
name|JButton
name|browseSumatraReader
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
name|this
operator|.
name|frame
operator|=
name|frame
expr_stmt|;
name|setLayout
argument_list|(
operator|new
name|BorderLayout
argument_list|()
argument_list|)
expr_stmt|;
name|JButton
name|editFileTypes
init|=
operator|new
name|JButton
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
name|JTextField
argument_list|(
literal|25
argument_list|)
expr_stmt|;
name|editFileTypes
operator|.
name|addActionListener
argument_list|(
name|ExternalFileTypeEditor
operator|.
name|getAction
argument_list|(
name|prefsDiag
argument_list|)
argument_list|)
expr_stmt|;
name|defaultConsole
operator|=
operator|new
name|JRadioButton
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
name|JRadioButton
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
name|JTextField
argument_list|()
expr_stmt|;
name|browseButton
operator|=
operator|new
name|JButton
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
name|JRadioButton
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
name|JTextField
argument_list|()
expr_stmt|;
name|browseAdobeAcrobatReader
operator|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Browse"
argument_list|)
argument_list|)
expr_stmt|;
name|sumatraReader
operator|=
operator|new
name|JRadioButton
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
name|JTextField
argument_list|()
expr_stmt|;
name|browseSumatraReader
operator|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Browse"
argument_list|)
argument_list|)
expr_stmt|;
name|JLabel
name|commandDescription
init|=
operator|new
name|JLabel
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Note: Use the placeholder %0 for the location of the opened database file."
argument_list|,
literal|"%DIR"
argument_list|)
argument_list|)
decl_stmt|;
name|ButtonGroup
name|consoleOptions
init|=
operator|new
name|ButtonGroup
argument_list|()
decl_stmt|;
name|consoleOptions
operator|.
name|add
argument_list|(
name|defaultConsole
argument_list|)
expr_stmt|;
name|consoleOptions
operator|.
name|add
argument_list|(
name|executeConsole
argument_list|)
expr_stmt|;
name|ButtonGroup
name|readerOptions
init|=
operator|new
name|ButtonGroup
argument_list|()
decl_stmt|;
name|readerOptions
operator|.
name|add
argument_list|(
name|adobeAcrobatReader
argument_list|)
expr_stmt|;
name|JPanel
name|pdfOptionPanel
init|=
operator|new
name|JPanel
argument_list|(
operator|new
name|GridBagLayout
argument_list|()
argument_list|)
decl_stmt|;
name|GridBagConstraints
name|pdfLayoutConstrains
init|=
operator|new
name|GridBagConstraints
argument_list|()
decl_stmt|;
name|JPanel
name|consoleOptionPanel
init|=
operator|new
name|JPanel
argument_list|(
operator|new
name|GridBagLayout
argument_list|()
argument_list|)
decl_stmt|;
name|GridBagConstraints
name|layoutConstraints
init|=
operator|new
name|GridBagConstraints
argument_list|()
decl_stmt|;
name|defaultConsole
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
name|updateExecuteConsoleButtonAndFieldEnabledState
argument_list|()
argument_list|)
expr_stmt|;
name|executeConsole
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
name|updateExecuteConsoleButtonAndFieldEnabledState
argument_list|()
argument_list|)
expr_stmt|;
name|browseButton
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
name|showConsoleChooser
argument_list|()
argument_list|)
expr_stmt|;
name|browseAdobeAcrobatReader
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
name|showAdobeChooser
argument_list|()
argument_list|)
expr_stmt|;
name|layoutConstraints
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|HORIZONTAL
expr_stmt|;
name|pdfLayoutConstrains
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|HORIZONTAL
expr_stmt|;
name|layoutConstraints
operator|.
name|gridx
operator|=
literal|0
expr_stmt|;
name|layoutConstraints
operator|.
name|gridy
operator|=
literal|0
expr_stmt|;
name|layoutConstraints
operator|.
name|insets
operator|=
operator|new
name|Insets
argument_list|(
literal|0
argument_list|,
literal|0
argument_list|,
literal|6
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|consoleOptionPanel
operator|.
name|add
argument_list|(
name|defaultConsole
argument_list|,
name|layoutConstraints
argument_list|)
expr_stmt|;
name|layoutConstraints
operator|.
name|gridy
operator|=
literal|1
expr_stmt|;
name|consoleOptionPanel
operator|.
name|add
argument_list|(
name|executeConsole
argument_list|,
name|layoutConstraints
argument_list|)
expr_stmt|;
name|layoutConstraints
operator|.
name|gridx
operator|=
literal|1
expr_stmt|;
name|consoleOptionPanel
operator|.
name|add
argument_list|(
name|consoleCommand
argument_list|,
name|layoutConstraints
argument_list|)
expr_stmt|;
name|layoutConstraints
operator|.
name|gridx
operator|=
literal|2
expr_stmt|;
name|layoutConstraints
operator|.
name|insets
operator|=
operator|new
name|Insets
argument_list|(
literal|0
argument_list|,
literal|4
argument_list|,
literal|6
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|consoleOptionPanel
operator|.
name|add
argument_list|(
name|browseButton
argument_list|,
name|layoutConstraints
argument_list|)
expr_stmt|;
name|layoutConstraints
operator|.
name|gridx
operator|=
literal|1
expr_stmt|;
name|layoutConstraints
operator|.
name|gridy
operator|=
literal|2
expr_stmt|;
name|consoleOptionPanel
operator|.
name|add
argument_list|(
name|commandDescription
argument_list|,
name|layoutConstraints
argument_list|)
expr_stmt|;
name|pdfLayoutConstrains
operator|.
name|gridx
operator|=
literal|0
expr_stmt|;
name|pdfLayoutConstrains
operator|.
name|gridy
operator|=
literal|0
expr_stmt|;
name|pdfLayoutConstrains
operator|.
name|insets
operator|=
operator|new
name|Insets
argument_list|(
literal|0
argument_list|,
literal|0
argument_list|,
literal|6
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|pdfOptionPanel
operator|.
name|add
argument_list|(
name|adobeAcrobatReader
argument_list|,
name|pdfLayoutConstrains
argument_list|)
expr_stmt|;
name|pdfLayoutConstrains
operator|.
name|gridx
operator|=
literal|1
expr_stmt|;
name|pdfOptionPanel
operator|.
name|add
argument_list|(
name|adobeAcrobatReaderPath
argument_list|,
name|pdfLayoutConstrains
argument_list|)
expr_stmt|;
name|pdfLayoutConstrains
operator|.
name|gridx
operator|=
literal|2
expr_stmt|;
name|pdfOptionPanel
operator|.
name|add
argument_list|(
name|browseAdobeAcrobatReader
argument_list|,
name|pdfLayoutConstrains
argument_list|)
expr_stmt|;
if|if
condition|(
name|OS
operator|.
name|WINDOWS
condition|)
block|{
name|readerOptions
operator|.
name|add
argument_list|(
name|sumatraReader
argument_list|)
expr_stmt|;
name|browseSumatraReader
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
name|showSumatraChooser
argument_list|()
argument_list|)
expr_stmt|;
name|pdfLayoutConstrains
operator|.
name|gridy
operator|=
literal|1
expr_stmt|;
name|pdfLayoutConstrains
operator|.
name|gridx
operator|=
literal|0
expr_stmt|;
name|pdfOptionPanel
operator|.
name|add
argument_list|(
name|sumatraReader
argument_list|,
name|pdfLayoutConstrains
argument_list|)
expr_stmt|;
name|pdfLayoutConstrains
operator|.
name|gridx
operator|=
literal|1
expr_stmt|;
name|pdfOptionPanel
operator|.
name|add
argument_list|(
name|sumatraReaderPath
argument_list|,
name|pdfLayoutConstrains
argument_list|)
expr_stmt|;
name|pdfLayoutConstrains
operator|.
name|gridx
operator|=
literal|2
expr_stmt|;
name|pdfOptionPanel
operator|.
name|add
argument_list|(
name|browseSumatraReader
argument_list|,
name|pdfLayoutConstrains
argument_list|)
expr_stmt|;
block|}
name|FormLayout
name|layout
init|=
operator|new
name|FormLayout
argument_list|(
literal|"1dlu, 8dlu, left:pref, 4dlu, fill:150dlu, 4dlu, fill:pref"
argument_list|,
literal|""
argument_list|)
decl_stmt|;
name|DefaultFormBuilder
name|builder
init|=
operator|new
name|DefaultFormBuilder
argument_list|(
name|layout
argument_list|)
decl_stmt|;
name|builder
operator|.
name|appendSeparator
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Sending of emails"
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
operator|new
name|JPanel
argument_list|()
argument_list|)
expr_stmt|;
name|JLabel
name|lab
init|=
operator|new
name|JLabel
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
name|append
argument_list|(
name|lab
argument_list|)
expr_stmt|;
name|emailSubject
operator|=
operator|new
name|JTextField
argument_list|(
literal|25
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|emailSubject
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
operator|new
name|JPanel
argument_list|()
argument_list|)
expr_stmt|;
name|openFoldersOfAttachedFiles
operator|=
operator|new
name|JCheckBox
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
name|append
argument_list|(
name|openFoldersOfAttachedFiles
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|builder
operator|.
name|appendSeparator
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"External programs"
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|JPanel
name|butpan
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|butpan
operator|.
name|setLayout
argument_list|(
operator|new
name|GridLayout
argument_list|(
literal|3
argument_list|,
literal|3
argument_list|)
argument_list|)
expr_stmt|;
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
argument_list|)
expr_stmt|;
block|}
name|builder
operator|.
name|append
argument_list|(
operator|new
name|JPanel
argument_list|()
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|butpan
argument_list|,
literal|3
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|lab
operator|=
operator|new
name|JLabel
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
expr_stmt|;
name|JPanel
name|pan
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|pan
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|lab
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|citeCommand
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|pan
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|editFileTypes
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|builder
operator|.
name|appendSeparator
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Open console"
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
operator|new
name|JPanel
argument_list|()
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|consoleOptionPanel
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|builder
operator|.
name|appendSeparator
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Open PDF"
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
operator|new
name|JPanel
argument_list|()
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|pdfOptionPanel
argument_list|)
expr_stmt|;
name|pan
operator|=
name|builder
operator|.
name|getPanel
argument_list|()
expr_stmt|;
name|pan
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createEmptyBorder
argument_list|(
literal|5
argument_list|,
literal|5
argument_list|,
literal|5
argument_list|,
literal|5
argument_list|)
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|pan
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
block|}
DECL|method|addSettingsButton (final PushToApplication pt, JPanel p)
specifier|private
name|void
name|addSettingsButton
parameter_list|(
specifier|final
name|PushToApplication
name|pt
parameter_list|,
name|JPanel
name|p
parameter_list|)
block|{
name|JButton
name|button
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Settings for %0"
argument_list|,
name|pt
operator|.
name|getApplicationName
argument_list|()
argument_list|)
argument_list|,
name|pt
operator|.
name|getIcon
argument_list|()
argument_list|)
decl_stmt|;
name|button
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
name|PushToApplicationButton
operator|.
name|showSettingsDialog
argument_list|(
name|frame
argument_list|,
name|pt
argument_list|,
name|pt
operator|.
name|getSettingsPanel
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|p
operator|.
name|add
argument_list|(
name|button
argument_list|)
expr_stmt|;
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
name|setEnabled
argument_list|(
name|executeConsole
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|consoleCommand
operator|.
name|setEnabled
argument_list|(
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

