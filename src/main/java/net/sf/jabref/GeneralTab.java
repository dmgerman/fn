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
name|Insets
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

begin_class
DECL|class|GeneralTab
specifier|public
class|class
name|GeneralTab
extends|extends
name|JPanel
implements|implements
name|PrefsTab
block|{
specifier|private
specifier|final
name|JCheckBox
DECL|field|defSort
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
DECL|field|useImportInspector
specifier|private
specifier|final
name|JCheckBox
name|useImportInspector
decl_stmt|;
DECL|field|useImportInspectorForSingle
specifier|private
specifier|final
name|JCheckBox
name|useImportInspectorForSingle
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
DECL|field|_prefs
specifier|private
specifier|final
name|JabRefPreferences
name|_prefs
decl_stmt|;
DECL|field|language
specifier|private
specifier|final
name|JComboBox
name|language
init|=
operator|new
name|JComboBox
argument_list|(
name|GUIGlobals
operator|.
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
name|GUIGlobals
operator|.
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
name|encodings
init|=
operator|new
name|JComboBox
argument_list|(
name|Globals
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
name|_prefs
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
name|Globals
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
name|Globals
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
name|Globals
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
name|Globals
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
name|Globals
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
name|Globals
operator|.
name|lang
argument_list|(
literal|"Mark new entries with addition date"
argument_list|)
operator|+
literal|". "
operator|+
name|Globals
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
name|Globals
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
name|Globals
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
name|Globals
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
name|Globals
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
name|Globals
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
name|Globals
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
name|Globals
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
name|Globals
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
name|Globals
operator|.
name|lang
argument_list|(
literal|"Show confirmation dialog when deleting entries"
argument_list|)
argument_list|)
expr_stmt|;
name|useImportInspector
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Display imported entries in an inspection window before they are added."
argument_list|)
argument_list|)
expr_stmt|;
name|useImportInspectorForSingle
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Use inspection window also when a single entry is imported."
argument_list|)
argument_list|)
expr_stmt|;
name|markImportedEntries
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
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
name|Globals
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
name|HelpAction
name|ownerHelp
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
decl_stmt|;
name|HelpAction
name|timeStampHelp
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
name|timeStampHelp
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
decl_stmt|;
name|inspectionWarnDupli
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Warn about unresolved duplicates when closing inspection window"
argument_list|)
argument_list|)
expr_stmt|;
name|Insets
name|marg
init|=
operator|new
name|Insets
argument_list|(
literal|0
argument_list|,
literal|12
argument_list|,
literal|3
argument_list|,
literal|0
argument_list|)
decl_stmt|;
name|useImportInspectorForSingle
operator|.
name|setMargin
argument_list|(
name|marg
argument_list|)
expr_stmt|;
name|inspectionWarnDupli
operator|.
name|setMargin
argument_list|(
name|marg
argument_list|)
expr_stmt|;
comment|// We need a listener on useImportInspector to enable and disable the
comment|// import inspector related choices;
name|useImportInspector
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
name|event
parameter_list|)
block|{
name|useImportInspectorForSingle
operator|.
name|setEnabled
argument_list|(
name|useImportInspector
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|inspectionWarnDupli
operator|.
name|setEnabled
argument_list|(
name|useImportInspector
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
name|useImportInspector
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
operator|new
name|JPanel
argument_list|()
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|useImportInspectorForSingle
argument_list|,
literal|11
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
name|inspectionWarnDupli
argument_list|,
literal|11
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
name|hlp
init|=
operator|new
name|JButton
argument_list|(
name|ownerHelp
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
name|builder
operator|.
name|append
argument_list|(
name|hlp
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
name|Globals
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
name|hlp
operator|=
operator|new
name|JButton
argument_list|(
name|timeStampHelp
argument_list|)
expr_stmt|;
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
name|builder
operator|.
name|append
argument_list|(
name|hlp
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
name|Globals
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
name|Globals
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
name|_prefs
operator|.
name|getBoolean
argument_list|(
literal|"allowTableEditing"
argument_list|)
argument_list|)
expr_stmt|;
name|defSort
operator|.
name|setSelected
argument_list|(
name|_prefs
operator|.
name|getBoolean
argument_list|(
literal|"defaultAutoSort"
argument_list|)
argument_list|)
expr_stmt|;
name|ctrlClick
operator|.
name|setSelected
argument_list|(
name|_prefs
operator|.
name|getBoolean
argument_list|(
literal|"ctrlClick"
argument_list|)
argument_list|)
expr_stmt|;
name|useOwner
operator|.
name|setSelected
argument_list|(
name|_prefs
operator|.
name|getBoolean
argument_list|(
literal|"useOwner"
argument_list|)
argument_list|)
expr_stmt|;
name|overwriteOwner
operator|.
name|setSelected
argument_list|(
name|_prefs
operator|.
name|getBoolean
argument_list|(
literal|"overwriteOwner"
argument_list|)
argument_list|)
expr_stmt|;
name|useTimeStamp
operator|.
name|setSelected
argument_list|(
name|_prefs
operator|.
name|getBoolean
argument_list|(
literal|"useTimeStamp"
argument_list|)
argument_list|)
expr_stmt|;
name|overwriteTimeStamp
operator|.
name|setSelected
argument_list|(
name|_prefs
operator|.
name|getBoolean
argument_list|(
literal|"overwriteTimeStamp"
argument_list|)
argument_list|)
expr_stmt|;
name|updateTimeStamp
operator|.
name|setSelected
argument_list|(
name|_prefs
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
name|_prefs
operator|.
name|getBoolean
argument_list|(
literal|"dialogWarningForDuplicateKey"
argument_list|)
argument_list|)
expr_stmt|;
name|keyEmptyWarningDialog
operator|.
name|setSelected
argument_list|(
name|_prefs
operator|.
name|getBoolean
argument_list|(
literal|"dialogWarningForEmptyKey"
argument_list|)
argument_list|)
expr_stmt|;
name|enforceLegalKeys
operator|.
name|setSelected
argument_list|(
name|_prefs
operator|.
name|getBoolean
argument_list|(
literal|"enforceLegalBibtexKey"
argument_list|)
argument_list|)
expr_stmt|;
name|memoryStick
operator|.
name|setSelected
argument_list|(
name|_prefs
operator|.
name|getBoolean
argument_list|(
literal|"memoryStickMode"
argument_list|)
argument_list|)
expr_stmt|;
name|confirmDelete
operator|.
name|setSelected
argument_list|(
name|_prefs
operator|.
name|getBoolean
argument_list|(
literal|"confirmDelete"
argument_list|)
argument_list|)
expr_stmt|;
name|defOwnerField
operator|.
name|setText
argument_list|(
name|_prefs
operator|.
name|get
argument_list|(
literal|"defaultOwner"
argument_list|)
argument_list|)
expr_stmt|;
name|timeStampFormat
operator|.
name|setText
argument_list|(
name|_prefs
operator|.
name|get
argument_list|(
literal|"timeStampFormat"
argument_list|)
argument_list|)
expr_stmt|;
name|timeStampField
operator|.
name|setText
argument_list|(
name|_prefs
operator|.
name|get
argument_list|(
literal|"timeStampField"
argument_list|)
argument_list|)
expr_stmt|;
name|useImportInspector
operator|.
name|setSelected
argument_list|(
name|_prefs
operator|.
name|getBoolean
argument_list|(
literal|"useImportInspectionDialog"
argument_list|)
argument_list|)
expr_stmt|;
name|useImportInspectorForSingle
operator|.
name|setSelected
argument_list|(
name|_prefs
operator|.
name|getBoolean
argument_list|(
literal|"useImportInspectionDialogForSingle"
argument_list|)
argument_list|)
expr_stmt|;
name|inspectionWarnDupli
operator|.
name|setSelected
argument_list|(
name|_prefs
operator|.
name|getBoolean
argument_list|(
literal|"warnAboutDuplicatesInInspection"
argument_list|)
argument_list|)
expr_stmt|;
name|useImportInspectorForSingle
operator|.
name|setEnabled
argument_list|(
name|useImportInspector
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|inspectionWarnDupli
operator|.
name|setEnabled
argument_list|(
name|useImportInspector
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|markImportedEntries
operator|.
name|setSelected
argument_list|(
name|_prefs
operator|.
name|getBoolean
argument_list|(
literal|"markImportedEntries"
argument_list|)
argument_list|)
expr_stmt|;
name|unmarkAllEntriesBeforeImporting
operator|.
name|setSelected
argument_list|(
name|_prefs
operator|.
name|getBoolean
argument_list|(
literal|"unmarkAllEntriesBeforeImporting"
argument_list|)
argument_list|)
expr_stmt|;
name|String
name|enc
init|=
name|_prefs
operator|.
name|get
argument_list|(
literal|"defaultEncoding"
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
name|Globals
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
name|Globals
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
name|_prefs
operator|.
name|get
argument_list|(
literal|"language"
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
name|GUIGlobals
operator|.
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
name|_prefs
operator|.
name|putBoolean
argument_list|(
literal|"useOwner"
argument_list|,
name|useOwner
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|putBoolean
argument_list|(
literal|"overwriteOwner"
argument_list|,
name|overwriteOwner
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|putBoolean
argument_list|(
literal|"useTimeStamp"
argument_list|,
name|useTimeStamp
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|putBoolean
argument_list|(
literal|"overwriteTimeStamp"
argument_list|,
name|overwriteTimeStamp
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
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
name|_prefs
operator|.
name|putBoolean
argument_list|(
literal|"dialogWarningForDuplicateKey"
argument_list|,
name|keyDuplicateWarningDialog
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|putBoolean
argument_list|(
literal|"dialogWarningForEmptyKey"
argument_list|,
name|keyEmptyWarningDialog
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|putBoolean
argument_list|(
literal|"enforceLegalBibtexKey"
argument_list|,
name|enforceLegalKeys
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|_prefs
operator|.
name|getBoolean
argument_list|(
literal|"memoryStickMode"
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
name|Globals
operator|.
name|lang
argument_list|(
literal|"To disable the memory stick mode"
operator|+
literal|" rename or remove the jabref.xml file in the same folder as JabRef."
argument_list|)
argument_list|,
name|Globals
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
name|_prefs
operator|.
name|putBoolean
argument_list|(
literal|"memoryStickMode"
argument_list|,
name|memoryStick
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|putBoolean
argument_list|(
literal|"confirmDelete"
argument_list|,
name|confirmDelete
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|putBoolean
argument_list|(
literal|"allowTableEditing"
argument_list|,
name|allowEditing
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|putBoolean
argument_list|(
literal|"ctrlClick"
argument_list|,
name|ctrlClick
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
comment|//_prefs.putBoolean("preserveFieldFormatting", preserveFormatting.isSelected());
name|_prefs
operator|.
name|putBoolean
argument_list|(
literal|"useImportInspectionDialog"
argument_list|,
name|useImportInspector
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|putBoolean
argument_list|(
literal|"useImportInspectionDialogForSingle"
argument_list|,
name|useImportInspectorForSingle
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|putBoolean
argument_list|(
literal|"warnAboutDuplicatesInInspection"
argument_list|,
name|inspectionWarnDupli
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
comment|//_prefs.putBoolean("defaultAutoSort", defSorrrt.isSelected());
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
name|_prefs
operator|.
name|put
argument_list|(
literal|"defaultOwner"
argument_list|,
name|owner
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|WRAPPED_USERNAME
operator|=
literal|'['
operator|+
name|owner
operator|+
literal|']'
expr_stmt|;
name|_prefs
operator|.
name|put
argument_list|(
literal|"timeStampFormat"
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
name|_prefs
operator|.
name|put
argument_list|(
literal|"timeStampField"
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
name|_prefs
operator|.
name|put
argument_list|(
literal|"defaultEncoding"
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
name|_prefs
operator|.
name|putBoolean
argument_list|(
literal|"markImportedEntries"
argument_list|,
name|markImportedEntries
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|putBoolean
argument_list|(
literal|"unmarkAllEntriesBeforeImporting"
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
name|GUIGlobals
operator|.
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
name|_prefs
operator|.
name|get
argument_list|(
literal|"language"
argument_list|)
argument_list|)
condition|)
block|{
name|_prefs
operator|.
name|put
argument_list|(
literal|"language"
argument_list|,
name|GUIGlobals
operator|.
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
name|Globals
operator|.
name|setLanguage
argument_list|(
name|GUIGlobals
operator|.
name|LANGUAGES
operator|.
name|get
argument_list|(
name|language
operator|.
name|getSelectedItem
argument_list|()
argument_list|)
argument_list|,
literal|""
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
name|Globals
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
name|Globals
operator|.
name|lang
argument_list|(
literal|"You must restart JabRef for this to come into effect."
argument_list|)
argument_list|)
argument_list|,
name|Globals
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
DECL|method|readyToClose ()
specifier|public
name|boolean
name|readyToClose
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
name|Globals
operator|.
name|lang
argument_list|(
literal|"The chosen date format for new entries is not valid"
argument_list|)
argument_list|,
name|Globals
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
name|Globals
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

