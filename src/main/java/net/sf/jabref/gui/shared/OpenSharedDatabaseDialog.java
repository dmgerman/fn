begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.shared
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|shared
package|;
end_package

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|FlowLayout
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
name|Insets
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
name|awt
operator|.
name|event
operator|.
name|KeyEvent
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|UnsupportedEncodingException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|security
operator|.
name|GeneralSecurityException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|sql
operator|.
name|SQLException
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
name|Set
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
name|Action
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
name|DefaultComboBoxModel
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
name|JComboBox
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
name|JDialog
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
name|JPasswordField
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
name|javax
operator|.
name|swing
operator|.
name|KeyStroke
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
name|JabRefGUI
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
name|BasePanel
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
name|help
operator|.
name|HelpAction
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
name|help
operator|.
name|HelpFile
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
name|model
operator|.
name|database
operator|.
name|BibDatabaseContext
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
name|model
operator|.
name|database
operator|.
name|DatabaseLocation
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
name|DBMSConnection
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
name|DBMSConnectionProperties
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
name|DBMSType
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
name|exception
operator|.
name|DatabaseNotSupportedException
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|shared
operator|.
name|security
operator|.
name|Password
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

begin_class
DECL|class|OpenSharedDatabaseDialog
specifier|public
class|class
name|OpenSharedDatabaseDialog
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
name|OpenSharedDatabaseDialog
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|frame
specifier|private
specifier|final
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|gridBagLayout
specifier|private
specifier|final
name|GridBagLayout
name|gridBagLayout
init|=
operator|new
name|GridBagLayout
argument_list|()
decl_stmt|;
DECL|field|gridBagConstraints
specifier|private
specifier|final
name|GridBagConstraints
name|gridBagConstraints
init|=
operator|new
name|GridBagConstraints
argument_list|()
decl_stmt|;
DECL|field|connectionPanel
specifier|private
specifier|final
name|JPanel
name|connectionPanel
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
DECL|field|buttonPanel
specifier|private
specifier|final
name|JPanel
name|buttonPanel
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
DECL|field|databaseTypeLabel
specifier|private
specifier|final
name|JLabel
name|databaseTypeLabel
init|=
operator|new
name|JLabel
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Database type"
argument_list|)
operator|+
literal|":"
argument_list|)
decl_stmt|;
DECL|field|hostPortLabel
specifier|private
specifier|final
name|JLabel
name|hostPortLabel
init|=
operator|new
name|JLabel
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Host"
argument_list|)
operator|+
literal|"/"
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"Port"
argument_list|)
operator|+
literal|":"
argument_list|)
decl_stmt|;
DECL|field|databaseLabel
specifier|private
specifier|final
name|JLabel
name|databaseLabel
init|=
operator|new
name|JLabel
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Database"
argument_list|)
operator|+
literal|":"
argument_list|)
decl_stmt|;
DECL|field|userLabel
specifier|private
specifier|final
name|JLabel
name|userLabel
init|=
operator|new
name|JLabel
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"User"
argument_list|)
operator|+
literal|":"
argument_list|)
decl_stmt|;
DECL|field|passwordLabel
specifier|private
specifier|final
name|JLabel
name|passwordLabel
init|=
operator|new
name|JLabel
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Password"
argument_list|)
operator|+
literal|":"
argument_list|)
decl_stmt|;
DECL|field|hostField
specifier|private
specifier|final
name|JTextField
name|hostField
init|=
operator|new
name|JTextField
argument_list|(
literal|12
argument_list|)
decl_stmt|;
DECL|field|portField
specifier|private
specifier|final
name|JTextField
name|portField
init|=
operator|new
name|JTextField
argument_list|(
literal|4
argument_list|)
decl_stmt|;
DECL|field|userField
specifier|private
specifier|final
name|JTextField
name|userField
init|=
operator|new
name|JTextField
argument_list|(
literal|14
argument_list|)
decl_stmt|;
DECL|field|databaseField
specifier|private
specifier|final
name|JTextField
name|databaseField
init|=
operator|new
name|JTextField
argument_list|(
literal|14
argument_list|)
decl_stmt|;
DECL|field|passwordField
specifier|private
specifier|final
name|JPasswordField
name|passwordField
init|=
operator|new
name|JPasswordField
argument_list|(
literal|14
argument_list|)
decl_stmt|;
DECL|field|dbmsTypeDropDown
specifier|private
specifier|final
name|JComboBox
argument_list|<
name|DBMSType
argument_list|>
name|dbmsTypeDropDown
init|=
operator|new
name|JComboBox
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|connectButton
specifier|private
specifier|final
name|JButton
name|connectButton
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Connect"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|cancelButton
specifier|private
specifier|final
name|JButton
name|cancelButton
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
DECL|field|helpButton
specifier|private
specifier|final
name|JButton
name|helpButton
init|=
operator|new
name|HelpAction
argument_list|(
name|HelpFile
operator|.
name|SQL_DATABASE
argument_list|)
operator|.
name|getHelpButton
argument_list|()
decl_stmt|;
DECL|field|rememberPassword
specifier|private
specifier|final
name|JCheckBox
name|rememberPassword
init|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Remember password?"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|prefs
specifier|private
specifier|final
name|SharedDatabasePreferences
name|prefs
init|=
operator|new
name|SharedDatabasePreferences
argument_list|()
decl_stmt|;
DECL|field|connectionProperties
specifier|private
name|DBMSConnectionProperties
name|connectionProperties
decl_stmt|;
comment|/**      * @param frame the JabRef Frame      */
DECL|method|OpenSharedDatabaseDialog (JabRefFrame frame)
specifier|public
name|OpenSharedDatabaseDialog
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|)
block|{
name|super
argument_list|(
name|frame
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Open shared database"
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|frame
operator|=
name|frame
expr_stmt|;
name|initLayout
argument_list|()
expr_stmt|;
name|applyPreferences
argument_list|()
expr_stmt|;
name|setupActions
argument_list|()
expr_stmt|;
name|pack
argument_list|()
expr_stmt|;
name|setLocationRelativeTo
argument_list|(
name|frame
argument_list|)
expr_stmt|;
block|}
DECL|method|openSharedDatabase ()
specifier|public
name|void
name|openSharedDatabase
parameter_list|()
block|{
if|if
condition|(
name|isSharedDatabaseAlreadyPresent
argument_list|()
condition|)
block|{
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|OpenSharedDatabaseDialog
operator|.
name|this
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"You are already connected to a database using entered connection details."
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Warning"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|WARNING_MESSAGE
argument_list|)
expr_stmt|;
return|return;
block|}
name|setLoadingConnectButtonText
argument_list|(
literal|true
argument_list|)
expr_stmt|;
try|try
block|{
operator|new
name|SharedDatabaseUIManager
argument_list|(
name|frame
argument_list|)
operator|.
name|openNewSharedDatabaseTab
argument_list|(
name|connectionProperties
argument_list|)
expr_stmt|;
name|setPreferences
argument_list|()
expr_stmt|;
name|dispose
argument_list|()
expr_stmt|;
return|return;
comment|// setLoadingConnectButtonText(false) should not be reached regularly.
block|}
catch|catch
parameter_list|(
name|SQLException
name|exception
parameter_list|)
block|{
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|OpenSharedDatabaseDialog
operator|.
name|this
argument_list|,
name|exception
operator|.
name|getMessage
argument_list|()
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Connection error"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|DatabaseNotSupportedException
name|exception
parameter_list|)
block|{
operator|new
name|MigrationHelpDialog
argument_list|(
name|this
argument_list|)
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
name|setLoadingConnectButtonText
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
comment|/**      * Defines and sets the different actions up.      */
DECL|method|setupActions ()
specifier|private
name|void
name|setupActions
parameter_list|()
block|{
name|Action
name|openAction
init|=
operator|new
name|AbstractAction
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
try|try
block|{
name|checkFields
argument_list|()
expr_stmt|;
name|connectionProperties
operator|=
operator|new
name|DBMSConnectionProperties
argument_list|()
expr_stmt|;
name|connectionProperties
operator|.
name|setType
argument_list|(
operator|(
name|DBMSType
operator|)
name|dbmsTypeDropDown
operator|.
name|getSelectedItem
argument_list|()
argument_list|)
expr_stmt|;
name|connectionProperties
operator|.
name|setHost
argument_list|(
name|hostField
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
name|connectionProperties
operator|.
name|setPort
argument_list|(
name|Integer
operator|.
name|parseInt
argument_list|(
name|portField
operator|.
name|getText
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|connectionProperties
operator|.
name|setDatabase
argument_list|(
name|databaseField
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
name|connectionProperties
operator|.
name|setUser
argument_list|(
name|userField
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
name|connectionProperties
operator|.
name|setPassword
argument_list|(
operator|new
name|String
argument_list|(
name|passwordField
operator|.
name|getPassword
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
comment|//JPasswordField.getPassword() does not return a String, but a char array.
name|openSharedDatabase
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|JabRefException
name|exception
parameter_list|)
block|{
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|OpenSharedDatabaseDialog
operator|.
name|this
argument_list|,
name|exception
operator|.
name|getMessage
argument_list|()
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Warning"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|WARNING_MESSAGE
argument_list|)
expr_stmt|;
block|}
block|}
block|}
decl_stmt|;
name|connectButton
operator|.
name|addActionListener
argument_list|(
name|openAction
argument_list|)
expr_stmt|;
name|cancelButton
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
name|dispose
argument_list|()
argument_list|)
expr_stmt|;
comment|/**          * Set up a listener which updates the default port number once the selection in dbmsTypeDropDown has changed.          */
name|Action
name|dbmsTypeDropDownAction
init|=
operator|new
name|AbstractAction
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|portField
operator|.
name|setText
argument_list|(
name|Integer
operator|.
name|toString
argument_list|(
operator|(
operator|(
name|DBMSType
operator|)
name|dbmsTypeDropDown
operator|.
name|getSelectedItem
argument_list|()
operator|)
operator|.
name|getDefaultPort
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
decl_stmt|;
name|dbmsTypeDropDown
operator|.
name|addActionListener
argument_list|(
name|dbmsTypeDropDownAction
argument_list|)
expr_stmt|;
comment|// Add enter button action listener
name|connectButton
operator|.
name|getInputMap
argument_list|(
name|JComponent
operator|.
name|WHEN_IN_FOCUSED_WINDOW
argument_list|)
operator|.
name|put
argument_list|(
name|KeyStroke
operator|.
name|getKeyStroke
argument_list|(
name|KeyEvent
operator|.
name|VK_ENTER
argument_list|,
literal|0
argument_list|)
argument_list|,
literal|"Enter_pressed"
argument_list|)
expr_stmt|;
name|connectButton
operator|.
name|getActionMap
argument_list|()
operator|.
name|put
argument_list|(
literal|"Enter_pressed"
argument_list|,
name|openAction
argument_list|)
expr_stmt|;
block|}
comment|/**      * Fetches possibly saved data and configures the control elements respectively.      */
DECL|method|applyPreferences ()
specifier|private
name|void
name|applyPreferences
parameter_list|()
block|{
name|Optional
argument_list|<
name|String
argument_list|>
name|sharedDatabaseType
init|=
name|prefs
operator|.
name|getType
argument_list|()
decl_stmt|;
name|Optional
argument_list|<
name|String
argument_list|>
name|sharedDatabaseHost
init|=
name|prefs
operator|.
name|getHost
argument_list|()
decl_stmt|;
name|Optional
argument_list|<
name|String
argument_list|>
name|sharedDatabasePort
init|=
name|prefs
operator|.
name|getPort
argument_list|()
decl_stmt|;
name|Optional
argument_list|<
name|String
argument_list|>
name|sharedDatabaseName
init|=
name|prefs
operator|.
name|getName
argument_list|()
decl_stmt|;
name|Optional
argument_list|<
name|String
argument_list|>
name|sharedDatabaseUser
init|=
name|prefs
operator|.
name|getUser
argument_list|()
decl_stmt|;
name|Optional
argument_list|<
name|String
argument_list|>
name|sharedDatabasePassword
init|=
name|prefs
operator|.
name|getPassword
argument_list|()
decl_stmt|;
name|boolean
name|sharedDatabaseRememberPassword
init|=
name|prefs
operator|.
name|getRememberPassword
argument_list|()
decl_stmt|;
if|if
condition|(
name|sharedDatabaseType
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|Optional
argument_list|<
name|DBMSType
argument_list|>
name|dbmsType
init|=
name|DBMSType
operator|.
name|fromString
argument_list|(
name|sharedDatabaseType
operator|.
name|get
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|dbmsType
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|dbmsTypeDropDown
operator|.
name|setSelectedItem
argument_list|(
name|dbmsType
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|sharedDatabaseHost
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|hostField
operator|.
name|setText
argument_list|(
name|sharedDatabaseHost
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|sharedDatabasePort
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|portField
operator|.
name|setText
argument_list|(
name|sharedDatabasePort
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|portField
operator|.
name|setText
argument_list|(
name|Integer
operator|.
name|toString
argument_list|(
operator|(
operator|(
name|DBMSType
operator|)
name|dbmsTypeDropDown
operator|.
name|getSelectedItem
argument_list|()
operator|)
operator|.
name|getDefaultPort
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|sharedDatabaseName
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|databaseField
operator|.
name|setText
argument_list|(
name|sharedDatabaseName
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|sharedDatabaseUser
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|userField
operator|.
name|setText
argument_list|(
name|sharedDatabaseUser
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|sharedDatabasePassword
operator|.
name|isPresent
argument_list|()
operator|&&
name|sharedDatabaseUser
operator|.
name|isPresent
argument_list|()
condition|)
block|{
try|try
block|{
name|passwordField
operator|.
name|setText
argument_list|(
operator|new
name|Password
argument_list|(
name|sharedDatabasePassword
operator|.
name|get
argument_list|()
operator|.
name|toCharArray
argument_list|()
argument_list|,
name|sharedDatabaseUser
operator|.
name|get
argument_list|()
argument_list|)
operator|.
name|decrypt
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|GeneralSecurityException
decl||
name|UnsupportedEncodingException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Could not read the password due to decryption problems."
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
name|rememberPassword
operator|.
name|setSelected
argument_list|(
name|sharedDatabaseRememberPassword
argument_list|)
expr_stmt|;
block|}
comment|/**      * Set up the layout and position the control units in their right place.      */
DECL|method|initLayout ()
specifier|private
name|void
name|initLayout
parameter_list|()
block|{
name|setResizable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|Insets
name|defautInsets
init|=
operator|new
name|Insets
argument_list|(
literal|4
argument_list|,
literal|15
argument_list|,
literal|4
argument_list|,
literal|4
argument_list|)
decl_stmt|;
name|connectionPanel
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createTitledBorder
argument_list|(
name|BorderFactory
operator|.
name|createEtchedBorder
argument_list|()
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Connection"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|connectionPanel
operator|.
name|setLayout
argument_list|(
name|gridBagLayout
argument_list|)
expr_stmt|;
name|Set
argument_list|<
name|DBMSType
argument_list|>
name|availableDBMSTypes
init|=
name|DBMSConnection
operator|.
name|getAvailableDBMSTypes
argument_list|()
decl_stmt|;
name|DefaultComboBoxModel
argument_list|<
name|DBMSType
argument_list|>
name|comboBoxModel
init|=
operator|new
name|DefaultComboBoxModel
argument_list|<>
argument_list|(
name|availableDBMSTypes
operator|.
name|toArray
argument_list|(
operator|new
name|DBMSType
index|[
name|availableDBMSTypes
operator|.
name|size
argument_list|()
index|]
argument_list|)
argument_list|)
decl_stmt|;
name|dbmsTypeDropDown
operator|.
name|setModel
argument_list|(
name|comboBoxModel
argument_list|)
expr_stmt|;
name|gridBagConstraints
operator|.
name|insets
operator|=
name|defautInsets
expr_stmt|;
name|gridBagConstraints
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|BOTH
expr_stmt|;
name|gridBagLayout
operator|.
name|setConstraints
argument_list|(
name|connectionPanel
argument_list|,
name|gridBagConstraints
argument_list|)
expr_stmt|;
comment|//1. column
name|gridBagConstraints
operator|.
name|gridx
operator|=
literal|0
expr_stmt|;
name|gridBagConstraints
operator|.
name|gridy
operator|=
literal|0
expr_stmt|;
name|connectionPanel
operator|.
name|add
argument_list|(
name|databaseTypeLabel
argument_list|,
name|gridBagConstraints
argument_list|)
expr_stmt|;
name|gridBagConstraints
operator|.
name|gridy
operator|=
literal|1
expr_stmt|;
name|connectionPanel
operator|.
name|add
argument_list|(
name|hostPortLabel
argument_list|,
name|gridBagConstraints
argument_list|)
expr_stmt|;
name|gridBagConstraints
operator|.
name|gridy
operator|=
literal|2
expr_stmt|;
name|connectionPanel
operator|.
name|add
argument_list|(
name|databaseLabel
argument_list|,
name|gridBagConstraints
argument_list|)
expr_stmt|;
name|gridBagConstraints
operator|.
name|gridy
operator|=
literal|3
expr_stmt|;
name|connectionPanel
operator|.
name|add
argument_list|(
name|userLabel
argument_list|,
name|gridBagConstraints
argument_list|)
expr_stmt|;
name|gridBagConstraints
operator|.
name|gridy
operator|=
literal|4
expr_stmt|;
name|connectionPanel
operator|.
name|add
argument_list|(
name|passwordLabel
argument_list|,
name|gridBagConstraints
argument_list|)
expr_stmt|;
comment|// 2. column
name|gridBagConstraints
operator|.
name|gridwidth
operator|=
literal|2
expr_stmt|;
name|gridBagConstraints
operator|.
name|gridx
operator|=
literal|1
expr_stmt|;
name|gridBagConstraints
operator|.
name|gridy
operator|=
literal|0
expr_stmt|;
name|connectionPanel
operator|.
name|add
argument_list|(
name|dbmsTypeDropDown
argument_list|,
name|gridBagConstraints
argument_list|)
expr_stmt|;
name|gridBagConstraints
operator|.
name|gridy
operator|=
literal|1
expr_stmt|;
name|gridBagConstraints
operator|.
name|gridwidth
operator|=
literal|1
expr_stmt|;
comment|// the hostField is smaller than the others.
name|gridBagConstraints
operator|.
name|insets
operator|=
operator|new
name|Insets
argument_list|(
literal|4
argument_list|,
literal|15
argument_list|,
literal|4
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|connectionPanel
operator|.
name|add
argument_list|(
name|hostField
argument_list|,
name|gridBagConstraints
argument_list|)
expr_stmt|;
name|gridBagConstraints
operator|.
name|gridy
operator|=
literal|2
expr_stmt|;
name|gridBagConstraints
operator|.
name|gridwidth
operator|=
literal|2
expr_stmt|;
name|gridBagConstraints
operator|.
name|insets
operator|=
name|defautInsets
expr_stmt|;
name|connectionPanel
operator|.
name|add
argument_list|(
name|databaseField
argument_list|,
name|gridBagConstraints
argument_list|)
expr_stmt|;
name|gridBagConstraints
operator|.
name|gridy
operator|=
literal|3
expr_stmt|;
name|connectionPanel
operator|.
name|add
argument_list|(
name|userField
argument_list|,
name|gridBagConstraints
argument_list|)
expr_stmt|;
name|gridBagConstraints
operator|.
name|gridy
operator|=
literal|4
expr_stmt|;
name|connectionPanel
operator|.
name|add
argument_list|(
name|passwordField
argument_list|,
name|gridBagConstraints
argument_list|)
expr_stmt|;
name|gridBagConstraints
operator|.
name|gridy
operator|=
literal|5
expr_stmt|;
name|connectionPanel
operator|.
name|add
argument_list|(
name|rememberPassword
argument_list|,
name|gridBagConstraints
argument_list|)
expr_stmt|;
comment|// 3. column
name|gridBagConstraints
operator|.
name|gridx
operator|=
literal|2
expr_stmt|;
name|gridBagConstraints
operator|.
name|gridy
operator|=
literal|1
expr_stmt|;
name|gridBagConstraints
operator|.
name|gridwidth
operator|=
literal|1
expr_stmt|;
name|gridBagConstraints
operator|.
name|insets
operator|=
operator|new
name|Insets
argument_list|(
literal|4
argument_list|,
literal|0
argument_list|,
literal|4
argument_list|,
literal|4
argument_list|)
expr_stmt|;
name|connectionPanel
operator|.
name|add
argument_list|(
name|portField
argument_list|,
name|gridBagConstraints
argument_list|)
expr_stmt|;
comment|// help button
name|gridBagConstraints
operator|.
name|gridx
operator|=
literal|0
expr_stmt|;
name|gridBagConstraints
operator|.
name|gridy
operator|=
literal|6
expr_stmt|;
name|gridBagConstraints
operator|.
name|insets
operator|=
operator|new
name|Insets
argument_list|(
literal|10
argument_list|,
literal|10
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|JPanel
name|helpPanel
init|=
operator|new
name|JPanel
argument_list|(
operator|new
name|FlowLayout
argument_list|(
name|FlowLayout
operator|.
name|LEFT
argument_list|)
argument_list|)
decl_stmt|;
name|helpPanel
operator|.
name|add
argument_list|(
name|helpButton
argument_list|)
expr_stmt|;
name|connectionPanel
operator|.
name|add
argument_list|(
name|helpPanel
argument_list|,
name|gridBagConstraints
argument_list|)
expr_stmt|;
comment|// control buttons
name|gridBagConstraints
operator|.
name|gridx
operator|=
literal|1
expr_stmt|;
name|gridBagConstraints
operator|.
name|gridwidth
operator|=
literal|2
expr_stmt|;
name|buttonPanel
operator|.
name|setLayout
argument_list|(
operator|new
name|FlowLayout
argument_list|(
name|FlowLayout
operator|.
name|LEFT
argument_list|)
argument_list|)
expr_stmt|;
name|buttonPanel
operator|.
name|add
argument_list|(
name|connectButton
argument_list|)
expr_stmt|;
name|buttonPanel
operator|.
name|add
argument_list|(
name|cancelButton
argument_list|)
expr_stmt|;
name|connectionPanel
operator|.
name|add
argument_list|(
name|buttonPanel
argument_list|,
name|gridBagConstraints
argument_list|)
expr_stmt|;
comment|// add panel
name|getContentPane
argument_list|()
operator|.
name|setLayout
argument_list|(
name|gridBagLayout
argument_list|)
expr_stmt|;
name|gridBagConstraints
operator|.
name|gridx
operator|=
literal|0
expr_stmt|;
name|gridBagConstraints
operator|.
name|gridy
operator|=
literal|0
expr_stmt|;
name|gridBagConstraints
operator|.
name|gridwidth
operator|=
literal|1
expr_stmt|;
name|gridBagConstraints
operator|.
name|insets
operator|=
operator|new
name|Insets
argument_list|(
literal|5
argument_list|,
literal|5
argument_list|,
literal|5
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|gridBagLayout
operator|.
name|setConstraints
argument_list|(
name|connectionPanel
argument_list|,
name|gridBagConstraints
argument_list|)
expr_stmt|;
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|connectionPanel
argument_list|)
expr_stmt|;
name|setModal
argument_list|(
literal|true
argument_list|)
expr_stmt|;
comment|// Owner window should be disabled while this dialog is opened.
block|}
comment|/**      * Saves the data from this dialog persistently to facilitate the usage.      */
DECL|method|setPreferences ()
specifier|private
name|void
name|setPreferences
parameter_list|()
block|{
name|prefs
operator|.
name|setType
argument_list|(
name|dbmsTypeDropDown
operator|.
name|getSelectedItem
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|setHost
argument_list|(
name|hostField
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|setPort
argument_list|(
name|portField
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|setName
argument_list|(
name|databaseField
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|setUser
argument_list|(
name|userField
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|rememberPassword
operator|.
name|isSelected
argument_list|()
condition|)
block|{
try|try
block|{
name|prefs
operator|.
name|setPassword
argument_list|(
operator|new
name|Password
argument_list|(
name|passwordField
operator|.
name|getPassword
argument_list|()
argument_list|,
name|userField
operator|.
name|getText
argument_list|()
argument_list|)
operator|.
name|encrypt
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|GeneralSecurityException
decl||
name|UnsupportedEncodingException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Could not store the password due to encryption problems."
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|prefs
operator|.
name|clearPassword
argument_list|()
expr_stmt|;
comment|// for the case that the password is already set
block|}
name|prefs
operator|.
name|setRememberPassword
argument_list|(
name|rememberPassword
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|isEmptyField (JTextField field)
specifier|private
name|boolean
name|isEmptyField
parameter_list|(
name|JTextField
name|field
parameter_list|)
block|{
return|return
name|field
operator|.
name|getText
argument_list|()
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
return|;
block|}
comment|/**      * Checks every required text field for emptiness.      */
DECL|method|checkFields ()
specifier|private
name|void
name|checkFields
parameter_list|()
throws|throws
name|JabRefException
block|{
if|if
condition|(
name|isEmptyField
argument_list|(
name|hostField
argument_list|)
condition|)
block|{
name|hostField
operator|.
name|requestFocus
argument_list|()
expr_stmt|;
throw|throw
operator|new
name|JabRefException
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Required_field_\"%0\"_is_empty."
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Host"
argument_list|)
argument_list|)
argument_list|)
throw|;
block|}
if|if
condition|(
name|isEmptyField
argument_list|(
name|portField
argument_list|)
condition|)
block|{
name|portField
operator|.
name|requestFocus
argument_list|()
expr_stmt|;
throw|throw
operator|new
name|JabRefException
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Required_field_\"%0\"_is_empty."
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Port"
argument_list|)
argument_list|)
argument_list|)
throw|;
block|}
if|if
condition|(
name|isEmptyField
argument_list|(
name|databaseField
argument_list|)
condition|)
block|{
name|databaseField
operator|.
name|requestFocus
argument_list|()
expr_stmt|;
throw|throw
operator|new
name|JabRefException
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Required_field_\"%0\"_is_empty."
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Database"
argument_list|)
argument_list|)
argument_list|)
throw|;
block|}
if|if
condition|(
name|isEmptyField
argument_list|(
name|userField
argument_list|)
condition|)
block|{
name|userField
operator|.
name|requestFocus
argument_list|()
expr_stmt|;
throw|throw
operator|new
name|JabRefException
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Required_field_\"%0\"_is_empty."
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"User"
argument_list|)
argument_list|)
argument_list|)
throw|;
block|}
block|}
comment|/**      * Sets the connectButton according to the current connection state.      */
DECL|method|setLoadingConnectButtonText (boolean isLoading)
specifier|private
name|void
name|setLoadingConnectButtonText
parameter_list|(
name|boolean
name|isLoading
parameter_list|)
block|{
name|connectButton
operator|.
name|setEnabled
argument_list|(
operator|!
name|isLoading
argument_list|)
expr_stmt|;
if|if
condition|(
name|isLoading
condition|)
block|{
name|connectButton
operator|.
name|setText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Connecting..."
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|connectButton
operator|.
name|setText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Connect"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Checks whether a database with the given @link {@link DBMSConnectionProperties} is already opened.      */
DECL|method|isSharedDatabaseAlreadyPresent ()
specifier|private
name|boolean
name|isSharedDatabaseAlreadyPresent
parameter_list|()
block|{
name|List
argument_list|<
name|BasePanel
argument_list|>
name|panels
init|=
name|JabRefGUI
operator|.
name|getMainFrame
argument_list|()
operator|.
name|getBasePanelList
argument_list|()
decl_stmt|;
return|return
name|panels
operator|.
name|parallelStream
argument_list|()
operator|.
name|anyMatch
argument_list|(
name|panel
lambda|->
block|{
name|BibDatabaseContext
name|context
init|=
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
decl_stmt|;
return|return
operator|(
operator|(
name|context
operator|.
name|getLocation
argument_list|()
operator|==
name|DatabaseLocation
operator|.
name|SHARED
operator|)
operator|&&
name|this
operator|.
name|connectionProperties
operator|.
name|equals
argument_list|(
name|context
operator|.
name|getDBMSSynchronizer
argument_list|()
operator|.
name|getDBProcessor
argument_list|()
operator|.
name|getDBMSConnectionProperties
argument_list|()
argument_list|)
operator|)
return|;
block|}
argument_list|)
return|;
block|}
block|}
end_class

end_unit

