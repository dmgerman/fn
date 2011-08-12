begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
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
name|event
operator|.
name|ItemEvent
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
name|ItemListener
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
name|ActionListener
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
name|javax
operator|.
name|swing
operator|.
name|*
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
name|external
operator|.
name|*
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
name|plugin
operator|.
name|core
operator|.
name|JabRefPlugin
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
specifier|public
class|class
name|ExternalTab
extends|extends
name|JPanel
implements|implements
name|PrefsTab
block|{
DECL|field|_prefs
name|JabRefPreferences
name|_prefs
decl_stmt|;
DECL|field|_frame
name|JabRefFrame
name|_frame
decl_stmt|;
DECL|field|pdfDir
DECL|field|regExpTextField
DECL|field|fileDir
DECL|field|psDir
DECL|field|emailSubject
name|JTextField
name|pdfDir
decl_stmt|,
name|regExpTextField
decl_stmt|,
name|fileDir
decl_stmt|,
name|psDir
decl_stmt|,
name|emailSubject
decl_stmt|;
DECL|field|runAutoFileSearch
DECL|field|allowFileAutoOpenBrowse
DECL|field|openFoldersOfAttachedFiles
name|JCheckBox
name|runAutoFileSearch
decl_stmt|,
name|allowFileAutoOpenBrowse
decl_stmt|,
name|openFoldersOfAttachedFiles
decl_stmt|;
DECL|field|editFileTypes
name|JButton
name|editFileTypes
decl_stmt|;
DECL|field|regExpListener
name|ItemListener
name|regExpListener
decl_stmt|;
DECL|field|useRegExpComboBox
name|JRadioButton
name|useRegExpComboBox
decl_stmt|;
DECL|field|matchExactKeyOnly
name|JRadioButton
name|matchExactKeyOnly
init|=
operator|new
name|JRadioButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Autolink only files that match the BibTeX key"
argument_list|)
argument_list|)
decl_stmt|,
DECL|field|matchStartsWithKey
name|matchStartsWithKey
init|=
operator|new
name|JRadioButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Autolink files with names starting with the BibTeX key"
argument_list|)
argument_list|)
decl_stmt|;
DECL|method|ExternalTab (JabRefFrame frame, PrefsDialog3 prefsDiag, JabRefPreferences prefs, HelpDialog helpDialog)
specifier|public
name|ExternalTab
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|PrefsDialog3
name|prefsDiag
parameter_list|,
name|JabRefPreferences
name|prefs
parameter_list|,
name|HelpDialog
name|helpDialog
parameter_list|)
block|{
name|_prefs
operator|=
name|prefs
expr_stmt|;
name|_frame
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
name|psDir
operator|=
operator|new
name|JTextField
argument_list|(
literal|25
argument_list|)
expr_stmt|;
name|pdfDir
operator|=
operator|new
name|JTextField
argument_list|(
literal|25
argument_list|)
expr_stmt|;
name|fileDir
operator|=
operator|new
name|JTextField
argument_list|(
literal|25
argument_list|)
expr_stmt|;
name|editFileTypes
operator|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Manage external file types"
argument_list|)
argument_list|)
expr_stmt|;
name|runAutoFileSearch
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"When opening file link, search for matching file if no link is defined"
argument_list|)
argument_list|)
expr_stmt|;
name|allowFileAutoOpenBrowse
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Automatically open browse dialog when creating new file link"
argument_list|)
argument_list|)
expr_stmt|;
name|regExpTextField
operator|=
operator|new
name|JTextField
argument_list|(
literal|25
argument_list|)
expr_stmt|;
name|useRegExpComboBox
operator|=
operator|new
name|JRadioButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Use Regular Expression Search"
argument_list|)
argument_list|)
expr_stmt|;
name|regExpListener
operator|=
operator|new
name|ItemListener
argument_list|()
block|{
specifier|public
name|void
name|itemStateChanged
parameter_list|(
name|ItemEvent
name|e
parameter_list|)
block|{
name|regExpTextField
operator|.
name|setEditable
argument_list|(
name|useRegExpComboBox
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
expr_stmt|;
name|useRegExpComboBox
operator|.
name|addItemListener
argument_list|(
name|regExpListener
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
name|ButtonGroup
name|bg
init|=
operator|new
name|ButtonGroup
argument_list|()
decl_stmt|;
name|bg
operator|.
name|add
argument_list|(
name|matchExactKeyOnly
argument_list|)
expr_stmt|;
name|bg
operator|.
name|add
argument_list|(
name|matchStartsWithKey
argument_list|)
expr_stmt|;
name|bg
operator|.
name|add
argument_list|(
name|useRegExpComboBox
argument_list|)
expr_stmt|;
name|BrowseAction
name|browse
decl_stmt|;
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
name|Globals
operator|.
name|lang
argument_list|(
literal|"External file links"
argument_list|)
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
comment|/** 		 * Fix for [ 1749613 ] About translation 		 *  		 * https://sourceforge.net/tracker/index.php?func=detail&aid=1749613&group_id=92314&atid=600306 		 *  		 * Cannot really use %0 to refer to the file type, since this ruins translation. 		 */
name|JLabel
name|lab
init|=
operator|new
name|JLabel
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Main file directory"
argument_list|)
operator|+
literal|":"
argument_list|)
decl_stmt|;
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
name|fileDir
argument_list|)
expr_stmt|;
name|browse
operator|=
operator|new
name|BrowseAction
argument_list|(
name|_frame
argument_list|,
name|fileDir
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
operator|new
name|JButton
argument_list|(
name|browse
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
name|matchStartsWithKey
argument_list|,
literal|3
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
name|matchExactKeyOnly
argument_list|,
literal|3
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
name|useRegExpComboBox
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|regExpTextField
argument_list|)
expr_stmt|;
name|HelpAction
name|helpAction
init|=
operator|new
name|HelpAction
argument_list|(
name|helpDialog
argument_list|,
name|GUIGlobals
operator|.
name|regularExpressionSearchHelp
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Help on Regular Expression Search"
argument_list|)
argument_list|,
name|GUIGlobals
operator|.
name|getIconUrl
argument_list|(
literal|"helpSmall"
argument_list|)
argument_list|)
decl_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|helpAction
operator|.
name|getIconButton
argument_list|()
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
name|runAutoFileSearch
argument_list|,
literal|3
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
name|allowFileAutoOpenBrowse
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
name|Globals
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
name|lab
operator|=
operator|new
name|JLabel
argument_list|(
name|Globals
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
expr_stmt|;
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
name|Globals
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
name|Globals
operator|.
name|lang
argument_list|(
literal|"Legacy file fields"
argument_list|)
argument_list|)
expr_stmt|;
name|pan
operator|=
operator|new
name|JPanel
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
operator|new
name|JLabel
argument_list|(
literal|"<html>"
operator|+
name|Globals
operator|.
name|lang
argument_list|(
literal|"Note that these settings are used for the legacy "
operator|+
literal|"<b>pdf</b> and<b>ps</b> fields only.<br>For most users, setting the<b>Main file directory</b> "
operator|+
literal|"above should be sufficient."
argument_list|)
operator|+
literal|"</html>"
argument_list|)
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|pan
operator|=
operator|new
name|JPanel
argument_list|()
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|pan
argument_list|)
expr_stmt|;
name|lab
operator|=
operator|new
name|JLabel
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Main PDF directory"
argument_list|)
operator|+
literal|":"
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
name|pdfDir
argument_list|)
expr_stmt|;
name|browse
operator|=
operator|new
name|BrowseAction
argument_list|(
name|_frame
argument_list|,
name|pdfDir
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
operator|new
name|JButton
argument_list|(
name|browse
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|pan
operator|=
operator|new
name|JPanel
argument_list|()
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|pan
argument_list|)
expr_stmt|;
name|lab
operator|=
operator|new
name|JLabel
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Main PS directory"
argument_list|)
operator|+
literal|":"
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
name|psDir
argument_list|)
expr_stmt|;
name|browse
operator|=
operator|new
name|BrowseAction
argument_list|(
name|_frame
argument_list|,
name|psDir
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
operator|new
name|JButton
argument_list|(
name|browse
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
name|appendSeparator
argument_list|(
name|Globals
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
name|addSettingsButton
argument_list|(
operator|new
name|PushToLyx
argument_list|()
argument_list|,
name|builder
argument_list|)
expr_stmt|;
name|addSettingsButton
argument_list|(
operator|new
name|PushToEmacs
argument_list|()
argument_list|,
name|builder
argument_list|)
expr_stmt|;
name|addSettingsButton
argument_list|(
operator|new
name|PushToWinEdt
argument_list|()
argument_list|,
name|builder
argument_list|)
expr_stmt|;
name|addSettingsButton
argument_list|(
operator|new
name|PushToVim
argument_list|()
argument_list|,
name|builder
argument_list|)
expr_stmt|;
name|addSettingsButton
argument_list|(
operator|new
name|PushToLatexEditor
argument_list|()
argument_list|,
name|builder
argument_list|)
expr_stmt|;
comment|//builder.nextLine();
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
DECL|method|addSettingsButton (final PushToApplication pt, DefaultFormBuilder b)
specifier|private
name|void
name|addSettingsButton
parameter_list|(
specifier|final
name|PushToApplication
name|pt
parameter_list|,
name|DefaultFormBuilder
name|b
parameter_list|)
block|{
name|b
operator|.
name|append
argument_list|(
operator|new
name|JPanel
argument_list|()
argument_list|)
expr_stmt|;
name|b
operator|.
name|append
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Settings for %0"
argument_list|,
name|pt
operator|.
name|getName
argument_list|()
argument_list|)
operator|+
literal|":"
argument_list|)
expr_stmt|;
name|JButton
name|button
init|=
operator|new
name|JButton
argument_list|(
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
operator|new
name|ActionListener
argument_list|()
block|{
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|event
parameter_list|)
block|{
name|PushToApplicationButton
operator|.
name|showSettingsDialog
argument_list|(
name|_frame
argument_list|,
name|pt
argument_list|,
name|pt
operator|.
name|getSettingsPanel
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|b
operator|.
name|append
argument_list|(
name|button
argument_list|)
expr_stmt|;
name|b
operator|.
name|nextLine
argument_list|()
expr_stmt|;
block|}
DECL|method|setValues ()
specifier|public
name|void
name|setValues
parameter_list|()
block|{
name|pdfDir
operator|.
name|setText
argument_list|(
name|_prefs
operator|.
name|get
argument_list|(
literal|"pdfDirectory"
argument_list|)
argument_list|)
expr_stmt|;
name|psDir
operator|.
name|setText
argument_list|(
name|_prefs
operator|.
name|get
argument_list|(
literal|"psDirectory"
argument_list|)
argument_list|)
expr_stmt|;
name|fileDir
operator|.
name|setText
argument_list|(
name|_prefs
operator|.
name|get
argument_list|(
name|GUIGlobals
operator|.
name|FILE_FIELD
operator|+
literal|"Directory"
argument_list|)
argument_list|)
expr_stmt|;
name|runAutoFileSearch
operator|.
name|setSelected
argument_list|(
name|_prefs
operator|.
name|getBoolean
argument_list|(
literal|"runAutomaticFileSearch"
argument_list|)
argument_list|)
expr_stmt|;
name|regExpTextField
operator|.
name|setText
argument_list|(
name|_prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|REG_EXP_SEARCH_EXPRESSION_KEY
argument_list|)
argument_list|)
expr_stmt|;
name|allowFileAutoOpenBrowse
operator|.
name|setSelected
argument_list|(
name|_prefs
operator|.
name|getBoolean
argument_list|(
literal|"allowFileAutoOpenBrowse"
argument_list|)
argument_list|)
expr_stmt|;
name|emailSubject
operator|.
name|setText
argument_list|(
name|_prefs
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
name|_prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|OPEN_FOLDERS_OF_ATTACHED_FILES
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|_prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|USE_REG_EXP_SEARCH_KEY
argument_list|)
condition|)
name|useRegExpComboBox
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
name|_prefs
operator|.
name|getBoolean
argument_list|(
literal|"autolinkExactKeyOnly"
argument_list|)
condition|)
name|matchExactKeyOnly
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
else|else
name|matchStartsWithKey
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
DECL|method|storeSettings ()
specifier|public
name|void
name|storeSettings
parameter_list|()
block|{
name|_prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|USE_REG_EXP_SEARCH_KEY
argument_list|,
name|useRegExpComboBox
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|useRegExpComboBox
operator|.
name|isSelected
argument_list|()
condition|)
block|{
name|_prefs
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|REG_EXP_SEARCH_EXPRESSION_KEY
argument_list|,
name|regExpTextField
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|// We should maybe do some checking on the validity of the contents?
name|_prefs
operator|.
name|put
argument_list|(
literal|"pdfDirectory"
argument_list|,
name|pdfDir
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|put
argument_list|(
literal|"psDirectory"
argument_list|,
name|psDir
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|put
argument_list|(
name|GUIGlobals
operator|.
name|FILE_FIELD
operator|+
literal|"Directory"
argument_list|,
name|fileDir
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|putBoolean
argument_list|(
literal|"autolinkExactKeyOnly"
argument_list|,
name|matchExactKeyOnly
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|putBoolean
argument_list|(
literal|"runAutomaticFileSearch"
argument_list|,
name|runAutoFileSearch
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|putBoolean
argument_list|(
literal|"allowFileAutoOpenBrowse"
argument_list|,
name|allowFileAutoOpenBrowse
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
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
name|_prefs
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
block|}
DECL|method|readyToClose ()
specifier|public
name|boolean
name|readyToClose
parameter_list|()
block|{
return|return
literal|true
return|;
block|}
DECL|method|getTabName ()
specifier|public
name|String
name|getTabName
parameter_list|()
block|{
return|return
name|Globals
operator|.
name|lang
argument_list|(
literal|"External programs"
argument_list|)
return|;
block|}
block|}
end_class

end_unit

