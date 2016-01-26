begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|JSpinner
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
name|SpinnerNumberModel
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
name|gui
operator|.
name|help
operator|.
name|HelpAction
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
DECL|field|openLast
specifier|private
specifier|final
name|JCheckBox
name|openLast
decl_stmt|;
DECL|field|autoSave
specifier|private
specifier|final
name|JCheckBox
name|autoSave
decl_stmt|;
DECL|field|promptBeforeUsingAutoSave
specifier|private
specifier|final
name|JCheckBox
name|promptBeforeUsingAutoSave
decl_stmt|;
DECL|field|valueDelimiter
specifier|private
specifier|final
name|JComboBox
argument_list|<
name|String
argument_list|>
name|valueDelimiter
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
DECL|field|bracesAroundCapitalsFields
specifier|private
specifier|final
name|JTextField
name|bracesAroundCapitalsFields
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
DECL|field|autoSaveInterval
specifier|private
specifier|final
name|JSpinner
name|autoSaveInterval
decl_stmt|;
DECL|field|origAutoSaveSetting
specifier|private
name|boolean
name|origAutoSaveSetting
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
name|openLast
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Open last edited databases at startup"
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
name|autoSave
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Autosave"
argument_list|)
argument_list|)
expr_stmt|;
name|promptBeforeUsingAutoSave
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Prompt before recovering a database from an autosave file"
argument_list|)
argument_list|)
expr_stmt|;
name|autoSaveInterval
operator|=
operator|new
name|JSpinner
argument_list|(
operator|new
name|SpinnerNumberModel
argument_list|(
literal|1
argument_list|,
literal|1
argument_list|,
literal|60
argument_list|,
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|valueDelimiter
operator|=
operator|new
name|JComboBox
argument_list|<>
argument_list|(
operator|new
name|String
index|[]
block|{
name|Localization
operator|.
name|lang
argument_list|(
literal|"Quotes"
argument_list|)
operator|+
literal|": \", \""
block|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Curly Brackets"
argument_list|)
operator|+
literal|": {, }"
block|}
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
name|bracesAroundCapitalsFields
operator|=
operator|new
name|JTextField
argument_list|(
literal|25
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
name|autoSave
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
name|autoSaveInterval
operator|.
name|setEnabled
argument_list|(
name|autoSave
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|promptBeforeUsingAutoSave
operator|.
name|setEnabled
argument_list|(
name|autoSave
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|FormLayout
name|layout
init|=
operator|new
name|FormLayout
argument_list|(
literal|"left:pref, 4dlu, fill:pref"
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
literal|"Store the following fields with braces around capital letters"
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
name|bracesAroundCapitalsFields
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|label
operator|=
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
expr_stmt|;
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
name|autoSave
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
name|frame
operator|.
name|helpDiag
argument_list|,
name|GUIGlobals
operator|.
name|autosaveHelp
argument_list|)
operator|.
name|getIconButton
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
name|builder
operator|.
name|append
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Autosave interval (minutes)"
argument_list|)
operator|+
literal|":"
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|autoSaveInterval
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
name|promptBeforeUsingAutoSave
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
name|bracesAroundCapitalsFields
operator|.
name|setText
argument_list|(
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|PUT_BRACES_AROUND_CAPITALS
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
name|autoSave
operator|.
name|setSelected
argument_list|(
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|AUTO_SAVE
argument_list|)
argument_list|)
expr_stmt|;
name|promptBeforeUsingAutoSave
operator|.
name|setSelected
argument_list|(
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|PROMPT_BEFORE_USING_AUTOSAVE
argument_list|)
argument_list|)
expr_stmt|;
name|autoSaveInterval
operator|.
name|setValue
argument_list|(
name|prefs
operator|.
name|getInt
argument_list|(
name|JabRefPreferences
operator|.
name|AUTO_SAVE_INTERVAL
argument_list|)
argument_list|)
expr_stmt|;
name|origAutoSaveSetting
operator|=
name|autoSave
operator|.
name|isSelected
argument_list|()
expr_stmt|;
name|valueDelimiter
operator|.
name|setSelectedIndex
argument_list|(
name|prefs
operator|.
name|getInt
argument_list|(
name|JabRefPreferences
operator|.
name|VALUE_DELIMITERS2
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
name|Globals
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
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|AUTO_SAVE
argument_list|,
name|autoSave
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
name|PROMPT_BEFORE_USING_AUTOSAVE
argument_list|,
name|promptBeforeUsingAutoSave
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|putInt
argument_list|(
name|JabRefPreferences
operator|.
name|AUTO_SAVE_INTERVAL
argument_list|,
operator|(
name|Integer
operator|)
name|autoSaveInterval
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|putInt
argument_list|(
name|JabRefPreferences
operator|.
name|VALUE_DELIMITERS2
argument_list|,
name|valueDelimiter
operator|.
name|getSelectedIndex
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
name|boolean
name|updateSpecialFields
init|=
literal|false
decl_stmt|;
if|if
condition|(
operator|!
name|bracesAroundCapitalsFields
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
name|PUT_BRACES_AROUND_CAPITALS
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
name|PUT_BRACES_AROUND_CAPITALS
argument_list|,
name|bracesAroundCapitalsFields
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
name|updateSpecialFields
operator|=
literal|true
expr_stmt|;
block|}
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
name|updateSpecialFields
operator|=
literal|true
expr_stmt|;
block|}
comment|// If either of the two last entries were changed, run the update for special field handling:
if|if
condition|(
name|updateSpecialFields
condition|)
block|{
name|prefs
operator|.
name|updateSpecialFieldHandling
argument_list|()
expr_stmt|;
block|}
comment|// See if we should start or stop the auto save manager:
if|if
condition|(
operator|!
name|origAutoSaveSetting
operator|&&
name|autoSave
operator|.
name|isSelected
argument_list|()
condition|)
block|{
name|Globals
operator|.
name|startAutoSaveManager
argument_list|(
name|frame
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|origAutoSaveSetting
operator|&&
operator|!
name|autoSave
operator|.
name|isSelected
argument_list|()
condition|)
block|{
name|Globals
operator|.
name|stopAutoSaveManager
argument_list|()
expr_stmt|;
block|}
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
literal|"File"
argument_list|)
return|;
block|}
block|}
end_class

end_unit

