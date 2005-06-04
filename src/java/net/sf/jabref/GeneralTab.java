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
name|javax
operator|.
name|swing
operator|.
name|*
import|;
end_import

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
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|*
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Iterator
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|File
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
name|*
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
name|factories
operator|.
name|*
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
name|*
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
DECL|field|autoOpenForm
DECL|field|backup
DECL|field|openLast
specifier|private
name|JCheckBox
name|autoOpenForm
decl_stmt|,
name|backup
decl_stmt|,
name|openLast
decl_stmt|,
DECL|field|defSource
DECL|field|editSource
DECL|field|defSort
DECL|field|ctrlClick
DECL|field|disableOnMultiple
name|defSource
decl_stmt|,
name|editSource
decl_stmt|,
name|defSort
decl_stmt|,
name|ctrlClick
decl_stmt|,
name|disableOnMultiple
decl_stmt|,
DECL|field|useOwner
DECL|field|keyDuplicateWarningDialog
DECL|field|keyEmptyWarningDialog
DECL|field|autoDoubleBraces
name|useOwner
decl_stmt|,
name|keyDuplicateWarningDialog
decl_stmt|,
name|keyEmptyWarningDialog
decl_stmt|,
name|autoDoubleBraces
decl_stmt|,
DECL|field|confirmDelete
DECL|field|saveInStandardOrder
DECL|field|allowEditing
DECL|field|preserveFormatting
DECL|field|useImportInspector
name|confirmDelete
decl_stmt|,
name|saveInStandardOrder
decl_stmt|,
name|allowEditing
decl_stmt|,
name|preserveFormatting
decl_stmt|,
name|useImportInspector
decl_stmt|;
DECL|field|groupField
specifier|private
name|JTextField
name|groupField
init|=
operator|new
name|JTextField
argument_list|(
literal|15
argument_list|)
decl_stmt|;
DECL|field|defOwnerField
DECL|field|fontSize
specifier|private
name|JTextField
name|defOwnerField
decl_stmt|,
name|fontSize
decl_stmt|;
DECL|field|_prefs
name|JabRefPreferences
name|_prefs
decl_stmt|;
DECL|field|_frame
name|JabRefFrame
name|_frame
decl_stmt|;
DECL|field|language
specifier|private
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
argument_list|()
argument_list|)
decl_stmt|,
DECL|field|encodings
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
DECL|field|ownerHelp
DECL|field|pdfHelp
specifier|private
name|HelpAction
name|ownerHelp
decl_stmt|,
name|pdfHelp
decl_stmt|;
DECL|field|oldMenuFontSize
specifier|private
name|int
name|oldMenuFontSize
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
name|autoOpenForm
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Open editor when a new entry is created"
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
name|defSource
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Show BibTeX source by default"
argument_list|)
argument_list|)
expr_stmt|;
name|editSource
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Enable source editing"
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
name|disableOnMultiple
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Disable entry editor when multiple entries are selected"
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
literal|":"
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
name|saveInStandardOrder
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Always save database ordered by author name"
argument_list|)
argument_list|)
expr_stmt|;
name|preserveFormatting
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Preserve formatting of non-BibTeX fields"
argument_list|)
argument_list|)
expr_stmt|;
name|autoDoubleBraces
operator|=
operator|new
name|JCheckBox
argument_list|(
literal|"<HTML>"
operator|+
name|Globals
operator|.
name|lang
argument_list|(
literal|"Store fields with double braces, and remove extra braces when loading.<BR>"
operator|+
literal|"Double braces signal that BibTeX should preserve character case."
argument_list|)
operator|+
literal|"</HTML>"
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
name|JPanel
name|general
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|defOwnerField
operator|=
operator|new
name|JTextField
argument_list|()
expr_stmt|;
name|groupField
operator|=
operator|new
name|JTextField
argument_list|(
literal|15
argument_list|)
expr_stmt|;
name|ownerHelp
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
name|ownerHelp
argument_list|,
literal|"Help"
argument_list|,
name|GUIGlobals
operator|.
name|helpSmallIconFile
argument_list|)
expr_stmt|;
comment|// Font sizes:
name|fontSize
operator|=
operator|new
name|JTextField
argument_list|()
expr_stmt|;
name|FormLayout
name|layout
init|=
operator|new
name|FormLayout
argument_list|(
literal|"1dlu, 8dlu, left:pref, 4dlu, fill:60dlu, 4dlu, fill:pref"
argument_list|,
comment|// 4dlu, left:pref, 4dlu",
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
name|JPanel
name|pan
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|builder
operator|.
name|appendSeparator
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"File"
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
name|pan
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|openLast
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
name|backup
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
name|saveInStandardOrder
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
name|preserveFormatting
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
name|autoDoubleBraces
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
comment|//builder.appendSeparator(Globals.lang("Miscellaneous"));
comment|//builder.nextLine();
name|builder
operator|.
name|appendSeparator
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Entry editor"
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
name|pan
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|autoOpenForm
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
name|defSource
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
name|disableOnMultiple
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
literal|"Miscellaneous"
argument_list|)
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
name|useImportInspector
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
name|allowEditing
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
name|ctrlClick
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
name|confirmDelete
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
name|keyDuplicateWarningDialog
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
name|keyEmptyWarningDialog
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
comment|// Create a new panel with its own FormLayout for the last items:
name|FormLayout
name|layout2
init|=
operator|new
name|FormLayout
argument_list|(
literal|"left:pref, 8dlu, fill:60dlu, 4dlu, fill:pref"
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
name|useOwner
argument_list|)
expr_stmt|;
name|builder2
operator|.
name|append
argument_list|(
name|defOwnerField
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
name|builder2
operator|.
name|append
argument_list|(
name|hlp
argument_list|)
expr_stmt|;
name|builder2
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
name|Globals
operator|.
name|lang
argument_list|(
literal|"Default grouping field"
argument_list|)
operator|+
literal|":"
argument_list|)
decl_stmt|;
name|builder2
operator|.
name|append
argument_list|(
name|lab
argument_list|)
expr_stmt|;
name|builder2
operator|.
name|append
argument_list|(
name|groupField
argument_list|)
expr_stmt|;
name|builder2
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
literal|"Language"
argument_list|)
operator|+
literal|":"
argument_list|)
expr_stmt|;
name|builder2
operator|.
name|append
argument_list|(
name|lab
argument_list|)
expr_stmt|;
name|builder2
operator|.
name|append
argument_list|(
name|language
argument_list|)
expr_stmt|;
name|builder2
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
literal|":"
argument_list|)
expr_stmt|;
name|builder2
operator|.
name|append
argument_list|(
name|lab
argument_list|)
expr_stmt|;
name|builder2
operator|.
name|append
argument_list|(
name|encodings
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
literal|"Menu and label font size"
argument_list|)
operator|+
literal|":"
argument_list|)
expr_stmt|;
name|builder2
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|builder2
operator|.
name|append
argument_list|(
name|lab
argument_list|)
expr_stmt|;
name|builder2
operator|.
name|append
argument_list|(
name|fontSize
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
name|builder2
operator|.
name|getPanel
argument_list|()
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
comment|//builder.appendSeparator();
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
DECL|method|setValues ()
specifier|public
name|void
name|setValues
parameter_list|()
block|{
name|autoOpenForm
operator|.
name|setSelected
argument_list|(
name|_prefs
operator|.
name|getBoolean
argument_list|(
literal|"autoOpenForm"
argument_list|)
argument_list|)
expr_stmt|;
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
name|defSource
operator|.
name|setSelected
argument_list|(
name|_prefs
operator|.
name|getBoolean
argument_list|(
literal|"defaultShowSource"
argument_list|)
argument_list|)
expr_stmt|;
name|editSource
operator|.
name|setSelected
argument_list|(
name|_prefs
operator|.
name|getBoolean
argument_list|(
literal|"enableSourceEditing"
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
name|disableOnMultiple
operator|.
name|setSelected
argument_list|(
name|_prefs
operator|.
name|getBoolean
argument_list|(
literal|"disableOnMultipleSelection"
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
name|saveInStandardOrder
operator|.
name|setSelected
argument_list|(
name|_prefs
operator|.
name|getBoolean
argument_list|(
literal|"saveInStandardOrder"
argument_list|)
argument_list|)
expr_stmt|;
name|preserveFormatting
operator|.
name|setSelected
argument_list|(
name|_prefs
operator|.
name|getBoolean
argument_list|(
literal|"preserveFieldFormatting"
argument_list|)
argument_list|)
expr_stmt|;
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
name|groupField
operator|.
name|setText
argument_list|(
name|_prefs
operator|.
name|get
argument_list|(
literal|"groupsDefaultField"
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
name|outer
label|:
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
break|break
name|outer
break|;
block|}
block|}
name|fontSize
operator|.
name|setText
argument_list|(
literal|""
operator|+
name|_prefs
operator|.
name|getInt
argument_list|(
literal|"menuFontSize"
argument_list|)
argument_list|)
expr_stmt|;
name|oldMenuFontSize
operator|=
name|_prefs
operator|.
name|getInt
argument_list|(
literal|"menuFontSize"
argument_list|)
expr_stmt|;
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
name|Iterator
name|i
init|=
name|GUIGlobals
operator|.
name|LANGUAGES
operator|.
name|keySet
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
if|if
condition|(
name|GUIGlobals
operator|.
name|LANGUAGES
operator|.
name|get
argument_list|(
name|i
operator|.
name|next
argument_list|()
argument_list|)
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
literal|"autoOpenForm"
argument_list|,
name|autoOpenForm
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
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
literal|"defaultShowSource"
argument_list|,
name|defSource
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|putBoolean
argument_list|(
literal|"enableSourceEditing"
argument_list|,
name|editSource
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|putBoolean
argument_list|(
literal|"disableOnMultipleSelection"
argument_list|,
name|disableOnMultiple
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
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
literal|"saveInStandardOrder"
argument_list|,
name|saveInStandardOrder
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
name|_prefs
operator|.
name|putBoolean
argument_list|(
literal|"preserveFieldFormatting"
argument_list|,
name|preserveFormatting
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
literal|"useImportInspectionDialog"
argument_list|,
name|useImportInspector
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
comment|//_prefs.putBoolean("defaultAutoSort", defSorrrt.isSelected());
name|_prefs
operator|.
name|put
argument_list|(
literal|"defaultOwner"
argument_list|,
name|defOwnerField
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
literal|"groupsDefaultField"
argument_list|,
name|groupField
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
operator|.
name|toString
argument_list|()
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
operator|.
name|toString
argument_list|()
argument_list|,
literal|""
argument_list|)
expr_stmt|;
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
literal|"You have changed the language setting. "
operator|+
literal|"You must restart JabRef for this to come into effect."
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
try|try
block|{
name|int
name|size
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|fontSize
operator|.
name|getText
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|size
operator|!=
name|oldMenuFontSize
condition|)
block|{
name|_prefs
operator|.
name|putInt
argument_list|(
literal|"menuFontSize"
argument_list|,
name|size
argument_list|)
expr_stmt|;
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
literal|"You have changed the menu and label font size. "
operator|+
literal|"You must restart JabRef for this to come into effect."
argument_list|)
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Changed font settings"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|WARNING_MESSAGE
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|ex
parameter_list|)
block|{
name|ex
operator|.
name|printStackTrace
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
try|try
block|{
name|int
name|size
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|fontSize
operator|.
name|getText
argument_list|()
argument_list|)
decl_stmt|;
return|return
literal|true
return|;
comment|// Ok, the number was legal.
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|ex
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
literal|"You must enter an integer value in the text field for"
argument_list|)
operator|+
literal|" '"
operator|+
name|Globals
operator|.
name|lang
argument_list|(
literal|"Menu and label font size"
argument_list|)
operator|+
literal|"'"
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Changed font settings"
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
block|}
block|}
end_class

end_unit

