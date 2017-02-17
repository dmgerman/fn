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
name|Dimension
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
name|nio
operator|.
name|file
operator|.
name|Files
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
name|nio
operator|.
name|file
operator|.
name|Paths
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
name|JComboBox
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
name|org
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
name|entry
operator|.
name|FieldName
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
name|FileDirectoryPreferences
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

begin_comment
comment|/**  * Preferences tab for file options. These options were moved out from GeneralTab to  * resolve the space issue.  */
end_comment

begin_class
DECL|class|FileTab
class|class
name|FileTab
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
DECL|field|backup
specifier|private
specifier|final
name|JCheckBox
name|backup
decl_stmt|;
DECL|field|localAutoSave
specifier|private
specifier|final
name|JCheckBox
name|localAutoSave
decl_stmt|;
DECL|field|openLast
specifier|private
specifier|final
name|JCheckBox
name|openLast
decl_stmt|;
DECL|field|newlineSeparator
specifier|private
specifier|final
name|JComboBox
argument_list|<
name|String
argument_list|>
name|newlineSeparator
decl_stmt|;
DECL|field|reformatFileOnSaveAndExport
specifier|private
specifier|final
name|JCheckBox
name|reformatFileOnSaveAndExport
decl_stmt|;
DECL|field|resolveStringsStandard
specifier|private
specifier|final
name|JRadioButton
name|resolveStringsStandard
decl_stmt|;
DECL|field|resolveStringsAll
specifier|private
specifier|final
name|JRadioButton
name|resolveStringsAll
decl_stmt|;
DECL|field|nonWrappableFields
specifier|private
specifier|final
name|JTextField
name|nonWrappableFields
decl_stmt|;
DECL|field|doNotResolveStringsFor
specifier|private
specifier|final
name|JTextField
name|doNotResolveStringsFor
decl_stmt|;
DECL|field|fileDir
specifier|private
specifier|final
name|JTextField
name|fileDir
decl_stmt|;
DECL|field|bibLocAsPrimaryDir
specifier|private
specifier|final
name|JCheckBox
name|bibLocAsPrimaryDir
decl_stmt|;
DECL|field|runAutoFileSearch
specifier|private
specifier|final
name|JCheckBox
name|runAutoFileSearch
decl_stmt|;
DECL|field|allowFileAutoOpenBrowse
specifier|private
specifier|final
name|JCheckBox
name|allowFileAutoOpenBrowse
decl_stmt|;
DECL|field|useRegExpComboBox
specifier|private
specifier|final
name|JRadioButton
name|useRegExpComboBox
decl_stmt|;
DECL|field|matchExactKeyOnly
specifier|private
specifier|final
name|JRadioButton
name|matchExactKeyOnly
init|=
operator|new
name|JRadioButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Autolink only files that match the BibTeX key"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|matchStartsWithKey
specifier|private
specifier|final
name|JRadioButton
name|matchStartsWithKey
init|=
operator|new
name|JRadioButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Autolink files with names starting with the BibTeX key"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|regExpTextField
specifier|private
specifier|final
name|JTextField
name|regExpTextField
decl_stmt|;
DECL|method|FileTab (JabRefFrame frame, JabRefPreferences prefs)
specifier|public
name|FileTab
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
name|fileDir
operator|=
operator|new
name|JTextField
argument_list|(
literal|25
argument_list|)
expr_stmt|;
name|bibLocAsPrimaryDir
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Use the BIB file location as primary file directory"
argument_list|)
argument_list|)
expr_stmt|;
name|bibLocAsPrimaryDir
operator|.
name|setToolTipText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"When downloading files, or moving linked files to the "
operator|+
literal|"file directory, prefer the BIB file location rather than the file directory set above"
argument_list|)
argument_list|)
expr_stmt|;
name|runAutoFileSearch
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
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
name|Localization
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
name|Localization
operator|.
name|lang
argument_list|(
literal|"Use regular expression search"
argument_list|)
argument_list|)
expr_stmt|;
name|ItemListener
name|regExpListener
init|=
name|e
lambda|->
name|regExpTextField
operator|.
name|setEditable
argument_list|(
name|useRegExpComboBox
operator|.
name|isSelected
argument_list|()
argument_list|)
decl_stmt|;
name|useRegExpComboBox
operator|.
name|addItemListener
argument_list|(
name|regExpListener
argument_list|)
expr_stmt|;
name|ButtonGroup
name|buttonGroup
init|=
operator|new
name|ButtonGroup
argument_list|()
decl_stmt|;
name|buttonGroup
operator|.
name|add
argument_list|(
name|matchExactKeyOnly
argument_list|)
expr_stmt|;
name|buttonGroup
operator|.
name|add
argument_list|(
name|matchStartsWithKey
argument_list|)
expr_stmt|;
name|buttonGroup
operator|.
name|add
argument_list|(
name|useRegExpComboBox
argument_list|)
expr_stmt|;
name|openLast
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Open last edited libraries at startup"
argument_list|)
argument_list|)
expr_stmt|;
name|backup
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Backup old file when saving"
argument_list|)
argument_list|)
expr_stmt|;
name|localAutoSave
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Autosave local libraries"
argument_list|)
argument_list|)
expr_stmt|;
name|resolveStringsAll
operator|=
operator|new
name|JRadioButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Resolve strings for all fields except"
argument_list|)
operator|+
literal|":"
argument_list|)
expr_stmt|;
name|resolveStringsStandard
operator|=
operator|new
name|JRadioButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Resolve strings for standard BibTeX fields only"
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
name|resolveStringsAll
argument_list|)
expr_stmt|;
name|bg
operator|.
name|add
argument_list|(
name|resolveStringsStandard
argument_list|)
expr_stmt|;
comment|// This is sort of a quick hack
name|newlineSeparator
operator|=
operator|new
name|JComboBox
argument_list|<>
argument_list|(
operator|new
name|String
index|[]
block|{
literal|"CR"
block|,
literal|"CR/LF"
block|,
literal|"LF"
block|}
argument_list|)
expr_stmt|;
name|reformatFileOnSaveAndExport
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Always reformat BIB file on save and export"
argument_list|)
argument_list|)
expr_stmt|;
name|nonWrappableFields
operator|=
operator|new
name|JTextField
argument_list|(
literal|25
argument_list|)
expr_stmt|;
name|doNotResolveStringsFor
operator|=
operator|new
name|JTextField
argument_list|(
literal|30
argument_list|)
expr_stmt|;
name|FormLayout
name|layout
init|=
operator|new
name|FormLayout
argument_list|(
literal|"left:pref, 4dlu, fill:150dlu, 4dlu, fill:pref"
argument_list|,
literal|""
argument_list|)
decl_stmt|;
comment|// left:pref, 4dlu, fill:pref
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
literal|"General"
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
name|openLast
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
name|backup
argument_list|,
literal|3
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|JLabel
name|label
init|=
operator|new
name|JLabel
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Do not wrap the following fields when saving"
argument_list|)
operator|+
literal|":"
argument_list|)
decl_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|label
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|nonWrappableFields
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
name|resolveStringsStandard
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
name|resolveStringsAll
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|doNotResolveStringsFor
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
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
literal|"Newline separator"
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
name|newlineSeparator
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
name|reformatFileOnSaveAndExport
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
name|appendSeparator
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"External file links"
argument_list|)
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
literal|"Main file directory"
argument_list|)
operator|+
literal|':'
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
name|fileDir
argument_list|)
expr_stmt|;
name|JButton
name|browse
init|=
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
decl_stmt|;
name|browse
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
operator|new
name|FileDialog
argument_list|(
name|this
operator|.
name|frame
argument_list|)
operator|.
name|showDialogAndGetSelectedDirectory
argument_list|()
operator|.
name|ifPresent
argument_list|(
name|f
lambda|->
name|fileDir
operator|.
name|setText
argument_list|(
name|f
operator|.
name|toAbsolutePath
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|browse
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
name|bibLocAsPrimaryDir
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
name|builder
operator|.
name|append
argument_list|(
operator|new
name|HelpAction
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Help on regular expression search"
argument_list|)
argument_list|,
name|HelpFile
operator|.
name|REGEX_SEARCH
argument_list|)
operator|.
name|getHelpButton
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
name|Localization
operator|.
name|lang
argument_list|(
literal|"Autosave"
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|localAutoSave
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|JButton
name|help
init|=
operator|new
name|HelpAction
argument_list|(
name|HelpFile
operator|.
name|AUTOSAVE
argument_list|)
operator|.
name|getHelpButton
argument_list|()
decl_stmt|;
name|help
operator|.
name|setPreferredSize
argument_list|(
operator|new
name|Dimension
argument_list|(
literal|24
argument_list|,
literal|24
argument_list|)
argument_list|)
expr_stmt|;
name|JPanel
name|hPan
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|hPan
operator|.
name|setLayout
argument_list|(
operator|new
name|BorderLayout
argument_list|()
argument_list|)
expr_stmt|;
name|hPan
operator|.
name|add
argument_list|(
name|help
argument_list|,
name|BorderLayout
operator|.
name|EAST
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|hPan
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|JPanel
name|pan
init|=
name|builder
operator|.
name|getPanel
argument_list|()
decl_stmt|;
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
name|setLayout
argument_list|(
operator|new
name|BorderLayout
argument_list|()
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
annotation|@
name|Override
DECL|method|setValues ()
specifier|public
name|void
name|setValues
parameter_list|()
block|{
name|fileDir
operator|.
name|setText
argument_list|(
name|prefs
operator|.
name|get
argument_list|(
name|FieldName
operator|.
name|FILE
operator|+
name|FileDirectoryPreferences
operator|.
name|DIR_SUFFIX
argument_list|)
argument_list|)
expr_stmt|;
name|bibLocAsPrimaryDir
operator|.
name|setSelected
argument_list|(
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|BIB_LOC_AS_PRIMARY_DIR
argument_list|)
argument_list|)
expr_stmt|;
name|runAutoFileSearch
operator|.
name|setSelected
argument_list|(
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|RUN_AUTOMATIC_FILE_SEARCH
argument_list|)
argument_list|)
expr_stmt|;
name|allowFileAutoOpenBrowse
operator|.
name|setSelected
argument_list|(
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|ALLOW_FILE_AUTO_OPEN_BROWSE
argument_list|)
argument_list|)
expr_stmt|;
name|regExpTextField
operator|.
name|setText
argument_list|(
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|REG_EXP_SEARCH_EXPRESSION_KEY
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|AUTOLINK_USE_REG_EXP_SEARCH_KEY
argument_list|)
condition|)
block|{
name|useRegExpComboBox
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
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|AUTOLINK_EXACT_KEY_ONLY
argument_list|)
condition|)
block|{
name|matchExactKeyOnly
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|matchStartsWithKey
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
name|openLast
operator|.
name|setSelected
argument_list|(
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|OPEN_LAST_EDITED
argument_list|)
argument_list|)
expr_stmt|;
name|backup
operator|.
name|setSelected
argument_list|(
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|BACKUP
argument_list|)
argument_list|)
expr_stmt|;
name|String
name|newline
init|=
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|NEWLINE
argument_list|)
decl_stmt|;
if|if
condition|(
literal|"\r"
operator|.
name|equals
argument_list|(
name|newline
argument_list|)
condition|)
block|{
name|newlineSeparator
operator|.
name|setSelectedIndex
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"\n"
operator|.
name|equals
argument_list|(
name|newline
argument_list|)
condition|)
block|{
name|newlineSeparator
operator|.
name|setSelectedIndex
argument_list|(
literal|2
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// fallback: windows standard
name|newlineSeparator
operator|.
name|setSelectedIndex
argument_list|(
literal|1
argument_list|)
expr_stmt|;
block|}
name|reformatFileOnSaveAndExport
operator|.
name|setSelected
argument_list|(
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|REFORMAT_FILE_ON_SAVE_AND_EXPORT
argument_list|)
argument_list|)
expr_stmt|;
name|resolveStringsAll
operator|.
name|setSelected
argument_list|(
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|RESOLVE_STRINGS_ALL_FIELDS
argument_list|)
argument_list|)
expr_stmt|;
name|resolveStringsStandard
operator|.
name|setSelected
argument_list|(
operator|!
name|resolveStringsAll
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|doNotResolveStringsFor
operator|.
name|setText
argument_list|(
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|DO_NOT_RESOLVE_STRINGS_FOR
argument_list|)
argument_list|)
expr_stmt|;
name|nonWrappableFields
operator|.
name|setText
argument_list|(
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|NON_WRAPPABLE_FIELDS
argument_list|)
argument_list|)
expr_stmt|;
name|localAutoSave
operator|.
name|setSelected
argument_list|(
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|LOCAL_AUTO_SAVE
argument_list|)
argument_list|)
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
name|FieldName
operator|.
name|FILE
operator|+
name|FileDirectoryPreferences
operator|.
name|DIR_SUFFIX
argument_list|,
name|fileDir
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
name|BIB_LOC_AS_PRIMARY_DIR
argument_list|,
name|bibLocAsPrimaryDir
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|RUN_AUTOMATIC_FILE_SEARCH
argument_list|,
name|runAutoFileSearch
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|ALLOW_FILE_AUTO_OPEN_BROWSE
argument_list|,
name|allowFileAutoOpenBrowse
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|AUTOLINK_USE_REG_EXP_SEARCH_KEY
argument_list|,
name|useRegExpComboBox
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|AUTOLINK_EXACT_KEY_ONLY
argument_list|,
name|matchExactKeyOnly
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
name|prefs
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
name|String
name|newline
decl_stmt|;
switch|switch
condition|(
name|newlineSeparator
operator|.
name|getSelectedIndex
argument_list|()
condition|)
block|{
case|case
literal|0
case|:
name|newline
operator|=
literal|"\r"
expr_stmt|;
break|break;
case|case
literal|2
case|:
name|newline
operator|=
literal|"\n"
expr_stmt|;
break|break;
default|default:
name|newline
operator|=
literal|"\r\n"
expr_stmt|;
break|break;
block|}
name|prefs
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|NEWLINE
argument_list|,
name|newline
argument_list|)
expr_stmt|;
comment|// we also have to change Globals variable as globals is not a getter, but a constant
name|OS
operator|.
name|NEWLINE
operator|=
name|newline
expr_stmt|;
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|BACKUP
argument_list|,
name|backup
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|REFORMAT_FILE_ON_SAVE_AND_EXPORT
argument_list|,
name|reformatFileOnSaveAndExport
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|OPEN_LAST_EDITED
argument_list|,
name|openLast
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|RESOLVE_STRINGS_ALL_FIELDS
argument_list|,
name|resolveStringsAll
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
name|DO_NOT_RESOLVE_STRINGS_FOR
argument_list|,
name|doNotResolveStringsFor
operator|.
name|getText
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
name|doNotResolveStringsFor
operator|.
name|setText
argument_list|(
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|DO_NOT_RESOLVE_STRINGS_FOR
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|nonWrappableFields
operator|.
name|getText
argument_list|()
operator|.
name|trim
argument_list|()
operator|.
name|equals
argument_list|(
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|NON_WRAPPABLE_FIELDS
argument_list|)
argument_list|)
condition|)
block|{
name|prefs
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|NON_WRAPPABLE_FIELDS
argument_list|,
name|nonWrappableFields
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|LOCAL_AUTO_SAVE
argument_list|,
name|localAutoSave
operator|.
name|isSelected
argument_list|()
argument_list|)
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
name|Path
name|path
init|=
name|Paths
operator|.
name|get
argument_list|(
name|fileDir
operator|.
name|getText
argument_list|()
argument_list|)
decl_stmt|;
name|boolean
name|valid
init|=
name|Files
operator|.
name|exists
argument_list|(
name|path
argument_list|)
operator|&&
name|Files
operator|.
name|isDirectory
argument_list|(
name|path
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|valid
condition|)
block|{
name|String
name|content
init|=
name|String
operator|.
name|format
argument_list|(
literal|"%s -> %s %n %n %s: %n %s"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"File"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Main file directory"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Directory not found"
argument_list|)
argument_list|,
name|path
argument_list|)
decl_stmt|;
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|this
operator|.
name|frame
argument_list|,
name|content
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Error"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
block|}
return|return
name|valid
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
literal|"File"
argument_list|)
return|;
block|}
block|}
end_class

end_unit
