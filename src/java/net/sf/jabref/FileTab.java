begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

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
name|*
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
name|net
operator|.
name|sf
operator|.
name|jabref
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

begin_comment
comment|/**  * Preferences tab for file options. These options were moved out from GeneralTab to  * resolve the space issue.  */
end_comment

begin_class
DECL|class|FileTab
specifier|public
class|class
name|FileTab
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
DECL|field|backup
DECL|field|openLast
DECL|field|autoDoubleBraces
DECL|field|autoSave
specifier|private
name|JCheckBox
name|backup
decl_stmt|,
name|openLast
decl_stmt|,
name|autoDoubleBraces
decl_stmt|,
name|autoSave
decl_stmt|,
DECL|field|promptBeforeUsingAutoSave
DECL|field|includeEmptyFields
name|promptBeforeUsingAutoSave
decl_stmt|,
name|includeEmptyFields
decl_stmt|;
DECL|field|valueDelimiter
specifier|private
name|JComboBox
argument_list|<
name|String
argument_list|>
name|valueDelimiter
decl_stmt|;
specifier|private
name|JRadioButton
DECL|field|saveOriginalOrder
DECL|field|saveAuthorOrder
DECL|field|saveTableOrder
DECL|field|saveTitleOrder
name|saveOriginalOrder
decl_stmt|,
name|saveAuthorOrder
decl_stmt|,
name|saveTableOrder
decl_stmt|,
name|saveTitleOrder
decl_stmt|,
DECL|field|exportOriginalOrder
DECL|field|exportAuthorOrder
DECL|field|exportTableOrder
DECL|field|exportTitleOrder
name|exportOriginalOrder
decl_stmt|,
name|exportAuthorOrder
decl_stmt|,
name|exportTableOrder
decl_stmt|,
name|exportTitleOrder
decl_stmt|,
DECL|field|resolveStringsStandard
DECL|field|resolveStringsAll
name|resolveStringsStandard
decl_stmt|,
name|resolveStringsAll
decl_stmt|;
DECL|field|bracesAroundCapitalsFields
DECL|field|nonWrappableFields
specifier|private
name|JTextField
name|bracesAroundCapitalsFields
decl_stmt|,
name|nonWrappableFields
decl_stmt|,
DECL|field|doNotResolveStringsFor
name|doNotResolveStringsFor
decl_stmt|;
DECL|field|autoSaveInterval
specifier|private
name|JSpinner
name|autoSaveInterval
decl_stmt|;
DECL|field|origAutoSaveSetting
specifier|private
name|boolean
name|origAutoSaveSetting
init|=
literal|false
decl_stmt|;
DECL|field|autosaveHelp
specifier|private
name|HelpAction
name|autosaveHelp
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
name|_prefs
operator|=
name|prefs
expr_stmt|;
name|_frame
operator|=
name|frame
expr_stmt|;
name|autosaveHelp
operator|=
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
argument_list|,
literal|"Help"
argument_list|,
name|GUIGlobals
operator|.
name|getIconUrl
argument_list|(
literal|"helpSmall"
argument_list|)
argument_list|)
expr_stmt|;
name|openLast
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
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
name|Globals
operator|.
name|lang
argument_list|(
literal|"Backup old file when saving"
argument_list|)
argument_list|)
expr_stmt|;
name|saveAuthorOrder
operator|=
operator|new
name|JRadioButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Save ordered by author/editor/year"
argument_list|)
argument_list|)
expr_stmt|;
name|exportAuthorOrder
operator|=
operator|new
name|JRadioButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Export ordered by author/editor/year"
argument_list|)
argument_list|)
expr_stmt|;
name|saveOriginalOrder
operator|=
operator|new
name|JRadioButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Save entries in their original order"
argument_list|)
argument_list|)
expr_stmt|;
name|exportOriginalOrder
operator|=
operator|new
name|JRadioButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Export entries in their original order"
argument_list|)
argument_list|)
expr_stmt|;
name|saveTableOrder
operator|=
operator|new
name|JRadioButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Save in current table sort order"
argument_list|)
argument_list|)
expr_stmt|;
name|exportTableOrder
operator|=
operator|new
name|JRadioButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Export in current table sort order"
argument_list|)
argument_list|)
expr_stmt|;
name|saveTitleOrder
operator|=
operator|new
name|JRadioButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Save entries ordered by title"
argument_list|)
argument_list|)
expr_stmt|;
name|exportTitleOrder
operator|=
operator|new
name|JRadioButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Export entries ordered by title"
argument_list|)
argument_list|)
expr_stmt|;
name|autoSave
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
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
name|Globals
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
argument_list|<
name|String
argument_list|>
argument_list|(
operator|new
name|String
index|[]
block|{
name|Globals
operator|.
name|lang
argument_list|(
literal|"Quotes"
argument_list|)
operator|+
literal|": \", \""
block|,
name|Globals
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
name|includeEmptyFields
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Include empty fields"
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
name|saveAuthorOrder
argument_list|)
expr_stmt|;
name|bg
operator|.
name|add
argument_list|(
name|saveOriginalOrder
argument_list|)
expr_stmt|;
name|bg
operator|.
name|add
argument_list|(
name|saveTableOrder
argument_list|)
expr_stmt|;
name|bg
operator|.
name|add
argument_list|(
name|saveTitleOrder
argument_list|)
expr_stmt|;
name|bg
operator|=
operator|new
name|ButtonGroup
argument_list|()
expr_stmt|;
name|bg
operator|.
name|add
argument_list|(
name|exportAuthorOrder
argument_list|)
expr_stmt|;
name|bg
operator|.
name|add
argument_list|(
name|exportOriginalOrder
argument_list|)
expr_stmt|;
name|bg
operator|.
name|add
argument_list|(
name|exportTableOrder
argument_list|)
expr_stmt|;
name|bg
operator|.
name|add
argument_list|(
name|exportTitleOrder
argument_list|)
expr_stmt|;
name|resolveStringsAll
operator|=
operator|new
name|JRadioButton
argument_list|(
name|Globals
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
name|Globals
operator|.
name|lang
argument_list|(
literal|"Resolve strings for standard BibTeX fields only"
argument_list|)
argument_list|)
expr_stmt|;
name|bg
operator|=
operator|new
name|ButtonGroup
argument_list|()
expr_stmt|;
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
name|autoDoubleBraces
operator|=
operator|new
name|JCheckBox
argument_list|(
comment|//+ Globals.lang("Store fields with double braces, and remove extra braces when loading.<BR>"
comment|//+ "Double braces signal that BibTeX should preserve character case.") + "</HTML>");
name|Globals
operator|.
name|lang
argument_list|(
literal|"Remove double braces around BibTeX fields when loading."
argument_list|)
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
name|Globals
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
name|builder
operator|.
name|append
argument_list|(
name|autoDoubleBraces
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
name|Globals
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
name|Globals
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
name|builder
operator|.
name|appendSeparator
argument_list|(
name|Globals
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
name|hlp
init|=
operator|new
name|JButton
argument_list|(
name|autosaveHelp
argument_list|)
decl_stmt|;
name|hlp
operator|.
name|setText
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|hlp
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
name|hlp
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
name|Globals
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
name|builder
operator|.
name|appendSeparator
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Sort order"
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|saveAuthorOrder
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|exportAuthorOrder
argument_list|,
literal|1
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
name|saveTableOrder
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|exportTableOrder
argument_list|,
literal|1
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
name|saveOriginalOrder
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|exportOriginalOrder
argument_list|,
literal|1
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
name|saveTitleOrder
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|exportTitleOrder
argument_list|,
literal|1
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
literal|"Field saving options"
argument_list|)
argument_list|)
expr_stmt|;
name|FormLayout
name|layout2
init|=
operator|new
name|FormLayout
argument_list|(
literal|"left:pref, 8dlu, fill:pref"
argument_list|,
literal|""
argument_list|)
decl_stmt|;
name|DefaultFormBuilder
name|builder2
init|=
operator|new
name|DefaultFormBuilder
argument_list|(
name|layout2
argument_list|)
decl_stmt|;
name|builder2
operator|.
name|append
argument_list|(
operator|new
name|JLabel
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Field value delimiter. E.g., \"author={x}\" or \"author='x'\""
argument_list|)
operator|+
literal|":"
argument_list|)
argument_list|)
expr_stmt|;
name|builder2
operator|.
name|append
argument_list|(
name|valueDelimiter
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
name|builder2
operator|.
name|getPanel
argument_list|()
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
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|includeEmptyFields
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
name|_prefs
operator|.
name|getBoolean
argument_list|(
literal|"openLastEdited"
argument_list|)
argument_list|)
expr_stmt|;
name|backup
operator|.
name|setSelected
argument_list|(
name|_prefs
operator|.
name|getBoolean
argument_list|(
literal|"backup"
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|_prefs
operator|.
name|getBoolean
argument_list|(
literal|"saveInStandardOrder"
argument_list|)
condition|)
name|saveAuthorOrder
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
literal|"saveInOriginalOrder"
argument_list|)
condition|)
name|saveOriginalOrder
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
literal|"saveInTitleOrder"
argument_list|)
condition|)
name|saveTitleOrder
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
else|else
name|saveTableOrder
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
if|if
condition|(
name|_prefs
operator|.
name|getBoolean
argument_list|(
literal|"exportInStandardOrder"
argument_list|)
condition|)
name|exportAuthorOrder
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
literal|"exportInOriginalOrder"
argument_list|)
condition|)
name|exportOriginalOrder
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
literal|"exportInTitleOrder"
argument_list|)
condition|)
name|exportTitleOrder
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
else|else
name|exportTableOrder
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
comment|//preserveFormatting.setSelected(_prefs.getBoolean("preserveFieldFormatting"));
name|autoDoubleBraces
operator|.
name|setSelected
argument_list|(
name|_prefs
operator|.
name|getBoolean
argument_list|(
literal|"autoDoubleBraces"
argument_list|)
argument_list|)
expr_stmt|;
name|resolveStringsAll
operator|.
name|setSelected
argument_list|(
name|_prefs
operator|.
name|getBoolean
argument_list|(
literal|"resolveStringsAllFields"
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
name|_prefs
operator|.
name|get
argument_list|(
literal|"doNotResolveStringsFor"
argument_list|)
argument_list|)
expr_stmt|;
name|bracesAroundCapitalsFields
operator|.
name|setText
argument_list|(
name|_prefs
operator|.
name|get
argument_list|(
literal|"putBracesAroundCapitals"
argument_list|)
argument_list|)
expr_stmt|;
name|nonWrappableFields
operator|.
name|setText
argument_list|(
name|_prefs
operator|.
name|get
argument_list|(
literal|"nonWrappableFields"
argument_list|)
argument_list|)
expr_stmt|;
name|autoSave
operator|.
name|setSelected
argument_list|(
name|_prefs
operator|.
name|getBoolean
argument_list|(
literal|"autoSave"
argument_list|)
argument_list|)
expr_stmt|;
name|promptBeforeUsingAutoSave
operator|.
name|setSelected
argument_list|(
name|_prefs
operator|.
name|getBoolean
argument_list|(
literal|"promptBeforeUsingAutosave"
argument_list|)
argument_list|)
expr_stmt|;
name|autoSaveInterval
operator|.
name|setValue
argument_list|(
name|_prefs
operator|.
name|getInt
argument_list|(
literal|"autoSaveInterval"
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
name|_prefs
operator|.
name|getInt
argument_list|(
literal|"valueDelimiters"
argument_list|)
argument_list|)
expr_stmt|;
name|includeEmptyFields
operator|.
name|setSelected
argument_list|(
name|_prefs
operator|.
name|getBoolean
argument_list|(
literal|"includeEmptyFields"
argument_list|)
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
literal|"backup"
argument_list|,
name|backup
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|putBoolean
argument_list|(
literal|"openLastEdited"
argument_list|,
name|openLast
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|putBoolean
argument_list|(
literal|"saveInStandardOrder"
argument_list|,
name|saveAuthorOrder
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|putBoolean
argument_list|(
literal|"saveInOriginalOrder"
argument_list|,
name|saveOriginalOrder
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|putBoolean
argument_list|(
literal|"saveInTitleOrder"
argument_list|,
name|saveTitleOrder
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|putBoolean
argument_list|(
literal|"exportInStandardOrder"
argument_list|,
name|exportAuthorOrder
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|putBoolean
argument_list|(
literal|"exportInOriginalOrder"
argument_list|,
name|exportOriginalOrder
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|putBoolean
argument_list|(
literal|"exportInTitleOrder"
argument_list|,
name|exportTitleOrder
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|putBoolean
argument_list|(
literal|"autoDoubleBraces"
argument_list|,
name|autoDoubleBraces
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|putBoolean
argument_list|(
literal|"resolveStringsAllFields"
argument_list|,
name|resolveStringsAll
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|put
argument_list|(
literal|"doNotResolveStringsFor"
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
name|_prefs
operator|.
name|putBoolean
argument_list|(
literal|"autoSave"
argument_list|,
name|autoSave
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|putBoolean
argument_list|(
literal|"promptBeforeUsingAutosave"
argument_list|,
name|promptBeforeUsingAutoSave
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|putInt
argument_list|(
literal|"autoSaveInterval"
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
name|_prefs
operator|.
name|putInt
argument_list|(
literal|"valueDelimiters"
argument_list|,
name|valueDelimiter
operator|.
name|getSelectedIndex
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|putBoolean
argument_list|(
literal|"includeEmptyFields"
argument_list|,
name|includeEmptyFields
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|doNotResolveStringsFor
operator|.
name|setText
argument_list|(
name|_prefs
operator|.
name|get
argument_list|(
literal|"doNotResolveStringsFor"
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
name|_prefs
operator|.
name|get
argument_list|(
literal|"putBracesAroundCapitals"
argument_list|)
argument_list|)
condition|)
block|{
name|_prefs
operator|.
name|put
argument_list|(
literal|"putBracesAroundCapitals"
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
name|_prefs
operator|.
name|get
argument_list|(
literal|"nonWrappableFields"
argument_list|)
argument_list|)
condition|)
block|{
name|_prefs
operator|.
name|put
argument_list|(
literal|"nonWrappableFields"
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
name|_prefs
operator|.
name|updateSpecialFieldHandling
argument_list|()
expr_stmt|;
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
name|_frame
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
literal|"File"
argument_list|)
return|;
block|}
block|}
end_class

end_unit

