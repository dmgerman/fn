begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

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
name|BorderLayout
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
name|javax
operator|.
name|swing
operator|.
name|event
operator|.
name|ChangeEvent
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|event
operator|.
name|ChangeListener
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
name|external
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
name|external
operator|.
name|push
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
name|actions
operator|.
name|BrowseAction
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
name|gui
operator|.
name|help
operator|.
name|HelpDialog
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
DECL|field|regExpTextField
specifier|private
specifier|final
name|JTextField
name|regExpTextField
decl_stmt|;
DECL|field|fileDir
specifier|private
specifier|final
name|JTextField
name|fileDir
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
DECL|field|bibLocationAsFileDir
specifier|private
specifier|final
name|JCheckBox
name|bibLocationAsFileDir
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
DECL|field|openFoldersOfAttachedFiles
specifier|private
specifier|final
name|JCheckBox
name|openFoldersOfAttachedFiles
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
DECL|method|ExternalTab (JabRefFrame frame, PreferencesDialog prefsDiag, JabRefPreferences prefs, HelpDialog helpDialog)
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
parameter_list|,
name|HelpDialog
name|helpDialog
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
name|fileDir
operator|=
operator|new
name|JTextField
argument_list|(
literal|25
argument_list|)
expr_stmt|;
name|bibLocationAsFileDir
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Allow file links relative to each bib file's location"
argument_list|)
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
literal|"Use the bib file location as primary file directory"
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
literal|"file directory, prefer the bib file location rather than the file directory set above"
argument_list|)
argument_list|)
expr_stmt|;
name|bibLocationAsFileDir
operator|.
name|addChangeListener
argument_list|(
operator|new
name|ChangeListener
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|stateChanged
parameter_list|(
name|ChangeEvent
name|changeEvent
parameter_list|)
block|{
name|bibLocAsPrimaryDir
operator|.
name|setEnabled
argument_list|(
name|bibLocationAsFileDir
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
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
name|citeCommand
operator|=
operator|new
name|JTextField
argument_list|(
literal|25
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
literal|"Use Regular Expression Search"
argument_list|)
argument_list|)
expr_stmt|;
name|ItemListener
name|regExpListener
init|=
operator|new
name|ItemListener
argument_list|()
block|{
annotation|@
name|Override
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
decl_stmt|;
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
name|Localization
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
comment|/**          * Fix for [ 1749613 ] About translation          *          * https://sourceforge.net/tracker/index.php?func=detail&aid=1749613&group_id=92314&atid=600306          *          * Cannot really use %0 to refer to the file type, since this ruins translation.          */
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
literal|"Main file directory"
argument_list|)
operator|+
literal|':'
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
name|BrowseAction
operator|.
name|buildForDir
argument_list|(
name|this
operator|.
name|frame
argument_list|,
name|fileDir
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
name|bibLocationAsFileDir
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
name|builder
operator|.
name|append
argument_list|(
operator|new
name|HelpAction
argument_list|(
name|helpDialog
argument_list|,
name|GUIGlobals
operator|.
name|regularExpressionSearchHelp
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Help on Regular Expression Search"
argument_list|)
argument_list|)
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
name|lab
operator|=
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
name|PushToApplications
operator|.
name|applications
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
operator|new
name|ActionListener
argument_list|()
block|{
annotation|@
name|Override
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
name|frame
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
name|fileDir
operator|.
name|setText
argument_list|(
name|prefs
operator|.
name|get
argument_list|(
name|Globals
operator|.
name|FILE_FIELD
operator|+
name|Globals
operator|.
name|DIR_SUFFIX
argument_list|)
argument_list|)
expr_stmt|;
name|bibLocationAsFileDir
operator|.
name|setSelected
argument_list|(
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|BIB_LOCATION_AS_FILE_DIR
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
name|bibLocAsPrimaryDir
operator|.
name|setEnabled
argument_list|(
name|bibLocationAsFileDir
operator|.
name|isSelected
argument_list|()
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
comment|// We should maybe do some checking on the validity of the contents?
name|prefs
operator|.
name|put
argument_list|(
name|Globals
operator|.
name|FILE_FIELD
operator|+
name|Globals
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
name|BIB_LOCATION_AS_FILE_DIR
argument_list|,
name|bibLocationAsFileDir
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
name|AUTOLINK_EXACT_KEY_ONLY
argument_list|,
name|matchExactKeyOnly
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
block|}
end_class

end_unit

