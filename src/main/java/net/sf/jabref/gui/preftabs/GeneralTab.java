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
name|BorderLayout
import|;
end_import

begin_import
import|import
name|java
operator|.
name|text
operator|.
name|SimpleDateFormat
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
name|Encodings
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
import|import static
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
name|Languages
operator|.
name|LANGUAGES
import|;
end_import

begin_class
DECL|class|GeneralTab
class|class
name|GeneralTab
extends|extends
name|JPanel
implements|implements
name|PrefsTab
block|{
DECL|field|defSort
specifier|private
specifier|final
name|JCheckBox
name|defSort
decl_stmt|;
DECL|field|ctrlClick
specifier|private
specifier|final
name|JCheckBox
name|ctrlClick
decl_stmt|;
DECL|field|useOwner
specifier|private
specifier|final
name|JCheckBox
name|useOwner
decl_stmt|;
DECL|field|overwriteOwner
specifier|private
specifier|final
name|JCheckBox
name|overwriteOwner
decl_stmt|;
DECL|field|keyDuplicateWarningDialog
specifier|private
specifier|final
name|JCheckBox
name|keyDuplicateWarningDialog
decl_stmt|;
DECL|field|keyEmptyWarningDialog
specifier|private
specifier|final
name|JCheckBox
name|keyEmptyWarningDialog
decl_stmt|;
DECL|field|enforceLegalKeys
specifier|private
specifier|final
name|JCheckBox
name|enforceLegalKeys
decl_stmt|;
DECL|field|confirmDelete
specifier|private
specifier|final
name|JCheckBox
name|confirmDelete
decl_stmt|;
DECL|field|allowEditing
specifier|private
specifier|final
name|JCheckBox
name|allowEditing
decl_stmt|;
DECL|field|memoryStick
specifier|private
specifier|final
name|JCheckBox
name|memoryStick
decl_stmt|;
DECL|field|inspectionWarnDupli
specifier|private
specifier|final
name|JCheckBox
name|inspectionWarnDupli
decl_stmt|;
DECL|field|useTimeStamp
specifier|private
specifier|final
name|JCheckBox
name|useTimeStamp
decl_stmt|;
DECL|field|updateTimeStamp
specifier|private
specifier|final
name|JCheckBox
name|updateTimeStamp
decl_stmt|;
DECL|field|overwriteTimeStamp
specifier|private
specifier|final
name|JCheckBox
name|overwriteTimeStamp
decl_stmt|;
DECL|field|markImportedEntries
specifier|private
specifier|final
name|JCheckBox
name|markImportedEntries
decl_stmt|;
DECL|field|unmarkAllEntriesBeforeImporting
specifier|private
specifier|final
name|JCheckBox
name|unmarkAllEntriesBeforeImporting
decl_stmt|;
DECL|field|defOwnerField
specifier|private
specifier|final
name|JTextField
name|defOwnerField
decl_stmt|;
DECL|field|timeStampFormat
specifier|private
specifier|final
name|JTextField
name|timeStampFormat
decl_stmt|;
DECL|field|timeStampField
specifier|private
specifier|final
name|JTextField
name|timeStampField
decl_stmt|;
DECL|field|prefs
specifier|private
specifier|final
name|JabRefPreferences
name|prefs
decl_stmt|;
DECL|field|language
specifier|private
specifier|final
name|JComboBox
argument_list|<
name|String
argument_list|>
name|language
init|=
operator|new
name|JComboBox
argument_list|<>
argument_list|(
name|LANGUAGES
operator|.
name|keySet
argument_list|()
operator|.
name|toArray
argument_list|(
operator|new
name|String
index|[
name|LANGUAGES
operator|.
name|keySet
argument_list|()
operator|.
name|size
argument_list|()
index|]
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|encodings
specifier|private
specifier|final
name|JComboBox
argument_list|<
name|String
argument_list|>
name|encodings
init|=
operator|new
name|JComboBox
argument_list|<>
argument_list|(
name|Encodings
operator|.
name|ENCODINGS
argument_list|)
decl_stmt|;
DECL|method|GeneralTab (JabRefFrame frame, JabRefPreferences prefs)
specifier|public
name|GeneralTab
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
name|setLayout
argument_list|(
operator|new
name|BorderLayout
argument_list|()
argument_list|)
expr_stmt|;
name|allowEditing
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Allow editing in table cells"
argument_list|)
argument_list|)
expr_stmt|;
name|memoryStick
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Load and Save preferences from/to jabref.xml on start-up (memory stick mode)"
argument_list|)
argument_list|)
expr_stmt|;
name|defSort
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Sort Automatically"
argument_list|)
argument_list|)
expr_stmt|;
name|ctrlClick
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Open right-click menu with Ctrl+left button"
argument_list|)
argument_list|)
expr_stmt|;
name|useOwner
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Mark new entries with owner name"
argument_list|)
operator|+
literal|':'
argument_list|)
expr_stmt|;
name|useTimeStamp
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Mark new entries with addition date"
argument_list|)
operator|+
literal|". "
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"Date format"
argument_list|)
operator|+
literal|':'
argument_list|)
expr_stmt|;
name|useTimeStamp
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
name|arg0
parameter_list|)
block|{
name|updateTimeStamp
operator|.
name|setEnabled
argument_list|(
name|useTimeStamp
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|updateTimeStamp
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Update timestamp on modification"
argument_list|)
argument_list|)
expr_stmt|;
name|overwriteOwner
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Overwrite"
argument_list|)
argument_list|)
expr_stmt|;
name|overwriteTimeStamp
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Overwrite"
argument_list|)
argument_list|)
expr_stmt|;
name|overwriteOwner
operator|.
name|setToolTipText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"If a pasted or imported entry already has "
operator|+
literal|"the field set, overwrite."
argument_list|)
argument_list|)
expr_stmt|;
name|overwriteTimeStamp
operator|.
name|setToolTipText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"If a pasted or imported entry already has "
operator|+
literal|"the field set, overwrite."
argument_list|)
argument_list|)
expr_stmt|;
name|keyDuplicateWarningDialog
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Show warning dialog when a duplicate BibTeX key is entered"
argument_list|)
argument_list|)
expr_stmt|;
name|keyEmptyWarningDialog
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Show warning dialog when an empty BibTeX key is entered"
argument_list|)
argument_list|)
expr_stmt|;
comment|// JZTODO lyrics
name|enforceLegalKeys
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Enforce legal characters in BibTeX keys"
argument_list|)
argument_list|)
expr_stmt|;
name|confirmDelete
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Show confirmation dialog when deleting entries"
argument_list|)
argument_list|)
expr_stmt|;
name|markImportedEntries
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Mark entries imported into an existing database"
argument_list|)
argument_list|)
expr_stmt|;
name|unmarkAllEntriesBeforeImporting
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Unmark all entries before importing new entries into an existing database"
argument_list|)
argument_list|)
expr_stmt|;
name|defOwnerField
operator|=
operator|new
name|JTextField
argument_list|()
expr_stmt|;
name|timeStampFormat
operator|=
operator|new
name|JTextField
argument_list|()
expr_stmt|;
name|timeStampField
operator|=
operator|new
name|JTextField
argument_list|()
expr_stmt|;
name|inspectionWarnDupli
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Warn about unresolved duplicates when closing inspection window"
argument_list|)
argument_list|)
expr_stmt|;
name|FormLayout
name|layout
init|=
operator|new
name|FormLayout
argument_list|(
literal|"8dlu, 1dlu, left:170dlu, 4dlu, fill:pref, 4dlu, fill:pref, 4dlu, left:pref, 4dlu, left:pref, 4dlu, left:pref"
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
name|inspectionWarnDupli
argument_list|,
literal|13
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
name|ctrlClick
argument_list|,
literal|13
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
name|confirmDelete
argument_list|,
literal|13
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
name|keyDuplicateWarningDialog
argument_list|,
literal|13
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
name|keyEmptyWarningDialog
argument_list|,
literal|13
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
name|enforceLegalKeys
argument_list|,
literal|13
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
name|memoryStick
argument_list|,
literal|13
argument_list|)
expr_stmt|;
comment|// Create a new panel with its own FormLayout for the last items:
name|builder
operator|.
name|append
argument_list|(
name|useOwner
argument_list|,
literal|3
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|defOwnerField
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|overwriteOwner
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
operator|new
name|JPanel
argument_list|()
argument_list|,
literal|3
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
name|ownerHelp
argument_list|)
operator|.
name|getIconButton
argument_list|()
decl_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|help
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
name|useTimeStamp
argument_list|,
literal|3
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|timeStampFormat
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|overwriteTimeStamp
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Field name"
argument_list|)
operator|+
literal|':'
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|timeStampField
argument_list|)
expr_stmt|;
name|help
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
name|timeStampHelp
argument_list|)
operator|.
name|getIconButton
argument_list|()
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|help
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
name|updateTimeStamp
argument_list|,
literal|2
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
name|markImportedEntries
argument_list|,
literal|13
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
name|unmarkAllEntriesBeforeImporting
argument_list|,
literal|13
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|JLabel
name|lab
decl_stmt|;
name|lab
operator|=
operator|new
name|JLabel
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Language"
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
argument_list|,
literal|3
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|language
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
literal|"Default encoding"
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
argument_list|,
literal|3
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|encodings
argument_list|)
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
name|allowEditing
operator|.
name|setSelected
argument_list|(
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|ALLOW_TABLE_EDITING
argument_list|)
argument_list|)
expr_stmt|;
name|defSort
operator|.
name|setSelected
argument_list|(
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|DEFAULT_AUTO_SORT
argument_list|)
argument_list|)
expr_stmt|;
name|ctrlClick
operator|.
name|setSelected
argument_list|(
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|CTRL_CLICK
argument_list|)
argument_list|)
expr_stmt|;
name|useOwner
operator|.
name|setSelected
argument_list|(
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|USE_OWNER
argument_list|)
argument_list|)
expr_stmt|;
name|overwriteOwner
operator|.
name|setSelected
argument_list|(
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|OVERWRITE_OWNER
argument_list|)
argument_list|)
expr_stmt|;
name|useTimeStamp
operator|.
name|setSelected
argument_list|(
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|USE_TIME_STAMP
argument_list|)
argument_list|)
expr_stmt|;
name|overwriteTimeStamp
operator|.
name|setSelected
argument_list|(
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|OVERWRITE_TIME_STAMP
argument_list|)
argument_list|)
expr_stmt|;
name|updateTimeStamp
operator|.
name|setSelected
argument_list|(
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|UPDATE_TIMESTAMP
argument_list|)
argument_list|)
expr_stmt|;
name|updateTimeStamp
operator|.
name|setEnabled
argument_list|(
name|useTimeStamp
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|keyDuplicateWarningDialog
operator|.
name|setSelected
argument_list|(
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|DIALOG_WARNING_FOR_DUPLICATE_KEY
argument_list|)
argument_list|)
expr_stmt|;
name|keyEmptyWarningDialog
operator|.
name|setSelected
argument_list|(
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|DIALOG_WARNING_FOR_EMPTY_KEY
argument_list|)
argument_list|)
expr_stmt|;
name|enforceLegalKeys
operator|.
name|setSelected
argument_list|(
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|ENFORCE_LEGAL_BIBTEX_KEY
argument_list|)
argument_list|)
expr_stmt|;
name|memoryStick
operator|.
name|setSelected
argument_list|(
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|MEMORY_STICK_MODE
argument_list|)
argument_list|)
expr_stmt|;
name|confirmDelete
operator|.
name|setSelected
argument_list|(
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|CONFIRM_DELETE
argument_list|)
argument_list|)
expr_stmt|;
name|defOwnerField
operator|.
name|setText
argument_list|(
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|DEFAULT_OWNER
argument_list|)
argument_list|)
expr_stmt|;
name|timeStampFormat
operator|.
name|setText
argument_list|(
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|TIME_STAMP_FORMAT
argument_list|)
argument_list|)
expr_stmt|;
name|timeStampField
operator|.
name|setText
argument_list|(
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|TIME_STAMP_FIELD
argument_list|)
argument_list|)
expr_stmt|;
name|inspectionWarnDupli
operator|.
name|setSelected
argument_list|(
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|WARN_ABOUT_DUPLICATES_IN_INSPECTION
argument_list|)
argument_list|)
expr_stmt|;
name|markImportedEntries
operator|.
name|setSelected
argument_list|(
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|MARK_IMPORTED_ENTRIES
argument_list|)
argument_list|)
expr_stmt|;
name|unmarkAllEntriesBeforeImporting
operator|.
name|setSelected
argument_list|(
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|UNMARK_ALL_ENTRIES_BEFORE_IMPORTING
argument_list|)
argument_list|)
expr_stmt|;
name|String
name|enc
init|=
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|DEFAULT_ENCODING
argument_list|)
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
name|Encodings
operator|.
name|ENCODINGS
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|Encodings
operator|.
name|ENCODINGS
index|[
name|i
index|]
operator|.
name|equalsIgnoreCase
argument_list|(
name|enc
argument_list|)
condition|)
block|{
name|encodings
operator|.
name|setSelectedIndex
argument_list|(
name|i
argument_list|)
expr_stmt|;
break|break;
block|}
block|}
name|String
name|oldLan
init|=
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|LANGUAGE
argument_list|)
decl_stmt|;
comment|// Language choice
name|int
name|ilk
init|=
literal|0
decl_stmt|;
for|for
control|(
name|String
name|lan
range|:
name|LANGUAGES
operator|.
name|values
argument_list|()
control|)
block|{
if|if
condition|(
name|lan
operator|.
name|equals
argument_list|(
name|oldLan
argument_list|)
condition|)
block|{
name|language
operator|.
name|setSelectedIndex
argument_list|(
name|ilk
argument_list|)
expr_stmt|;
block|}
name|ilk
operator|++
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
name|USE_OWNER
argument_list|,
name|useOwner
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
name|OVERWRITE_OWNER
argument_list|,
name|overwriteOwner
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
name|USE_TIME_STAMP
argument_list|,
name|useTimeStamp
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
name|OVERWRITE_TIME_STAMP
argument_list|,
name|overwriteTimeStamp
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
name|UPDATE_TIMESTAMP
argument_list|,
name|updateTimeStamp
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
name|DIALOG_WARNING_FOR_DUPLICATE_KEY
argument_list|,
name|keyDuplicateWarningDialog
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
name|DIALOG_WARNING_FOR_EMPTY_KEY
argument_list|,
name|keyEmptyWarningDialog
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
name|ENFORCE_LEGAL_BIBTEX_KEY
argument_list|,
name|enforceLegalKeys
operator|.
name|isSelected
argument_list|()
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
name|MEMORY_STICK_MODE
argument_list|)
operator|&&
operator|!
name|memoryStick
operator|.
name|isSelected
argument_list|()
condition|)
block|{
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
literal|null
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"To disable the memory stick mode"
operator|+
literal|" rename or remove the jabref.xml file in the same folder as JabRef."
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Memory Stick Mode"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|INFORMATION_MESSAGE
argument_list|)
expr_stmt|;
block|}
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|MEMORY_STICK_MODE
argument_list|,
name|memoryStick
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
name|CONFIRM_DELETE
argument_list|,
name|confirmDelete
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
name|ALLOW_TABLE_EDITING
argument_list|,
name|allowEditing
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
name|CTRL_CLICK
argument_list|,
name|ctrlClick
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
name|WARN_ABOUT_DUPLICATES_IN_INSPECTION
argument_list|,
name|inspectionWarnDupli
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|String
name|owner
init|=
name|defOwnerField
operator|.
name|getText
argument_list|()
operator|.
name|trim
argument_list|()
decl_stmt|;
name|prefs
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|DEFAULT_OWNER
argument_list|,
name|owner
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|WRAPPED_USERNAME
operator|=
literal|'['
operator|+
name|owner
operator|+
literal|']'
expr_stmt|;
name|prefs
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|TIME_STAMP_FORMAT
argument_list|,
name|timeStampFormat
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
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|TIME_STAMP_FIELD
argument_list|,
name|timeStampField
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
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|DEFAULT_ENCODING
argument_list|,
operator|(
name|String
operator|)
name|encodings
operator|.
name|getSelectedItem
argument_list|()
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|MARK_IMPORTED_ENTRIES
argument_list|,
name|markImportedEntries
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
name|UNMARK_ALL_ENTRIES_BEFORE_IMPORTING
argument_list|,
name|unmarkAllEntriesBeforeImporting
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|LANGUAGES
operator|.
name|get
argument_list|(
name|language
operator|.
name|getSelectedItem
argument_list|()
argument_list|)
operator|.
name|equals
argument_list|(
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|LANGUAGE
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
name|LANGUAGE
argument_list|,
name|LANGUAGES
operator|.
name|get
argument_list|(
name|language
operator|.
name|getSelectedItem
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|Localization
operator|.
name|setLanguage
argument_list|(
name|LANGUAGES
operator|.
name|get
argument_list|(
name|language
operator|.
name|getSelectedItem
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
comment|// Update any defaults that might be language dependent:
name|Globals
operator|.
name|prefs
operator|.
name|setLanguageDependentDefaultValues
argument_list|()
expr_stmt|;
comment|// Warn about restart needed:
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
literal|null
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"You have changed the language setting."
argument_list|)
operator|.
name|concat
argument_list|(
literal|" "
argument_list|)
operator|.
name|concat
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"You must restart JabRef for this to come into effect."
argument_list|)
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Changed language settings"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|WARNING_MESSAGE
argument_list|)
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
try|try
block|{
comment|// Test if date format is legal:
operator|new
name|SimpleDateFormat
argument_list|(
name|timeStampFormat
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IllegalArgumentException
name|ex2
parameter_list|)
block|{
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
literal|null
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"The chosen date format for new entries is not valid"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Invalid date format"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
return|return
literal|false
return|;
block|}
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
literal|"General"
argument_list|)
return|;
block|}
block|}
end_class

end_unit

