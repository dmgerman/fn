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
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|autocompleter
operator|.
name|AutoCompleteFirstNameMode
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
name|autocompleter
operator|.
name|AutoCompletePreferences
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
name|keyboard
operator|.
name|EmacsKeyBindings
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
name|CellConstraints
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
import|import static
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|autocompleter
operator|.
name|AutoCompleteFirstNameMode
operator|.
name|ONLY_ABBREVIATED
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|autocompleter
operator|.
name|AutoCompleteFirstNameMode
operator|.
name|ONLY_FULL
import|;
end_import

begin_class
DECL|class|EntryEditorPrefsTab
class|class
name|EntryEditorPrefsTab
extends|extends
name|JPanel
implements|implements
name|PrefsTab
block|{
DECL|field|autoOpenForm
specifier|private
specifier|final
name|JCheckBox
name|autoOpenForm
decl_stmt|;
DECL|field|defSource
specifier|private
specifier|final
name|JCheckBox
name|defSource
decl_stmt|;
DECL|field|emacsMode
specifier|private
specifier|final
name|JCheckBox
name|emacsMode
decl_stmt|;
DECL|field|emacsRebindCtrlA
specifier|private
specifier|final
name|JCheckBox
name|emacsRebindCtrlA
decl_stmt|;
DECL|field|emacsRebindCtrlF
specifier|private
specifier|final
name|JCheckBox
name|emacsRebindCtrlF
decl_stmt|;
DECL|field|autoComplete
specifier|private
specifier|final
name|JCheckBox
name|autoComplete
decl_stmt|;
DECL|field|recommendations
specifier|private
specifier|final
name|JCheckBox
name|recommendations
decl_stmt|;
DECL|field|validation
specifier|private
specifier|final
name|JCheckBox
name|validation
decl_stmt|;
DECL|field|autoCompBoth
specifier|private
specifier|final
name|JRadioButton
name|autoCompBoth
decl_stmt|;
DECL|field|autoCompFF
specifier|private
specifier|final
name|JRadioButton
name|autoCompFF
decl_stmt|;
DECL|field|autoCompLF
specifier|private
specifier|final
name|JRadioButton
name|autoCompLF
decl_stmt|;
DECL|field|firstNameModeFull
specifier|private
specifier|final
name|JRadioButton
name|firstNameModeFull
decl_stmt|;
DECL|field|firstNameModeAbbr
specifier|private
specifier|final
name|JRadioButton
name|firstNameModeAbbr
decl_stmt|;
DECL|field|firstNameModeBoth
specifier|private
specifier|final
name|JRadioButton
name|firstNameModeBoth
decl_stmt|;
DECL|field|autoCompFields
specifier|private
specifier|final
name|JTextField
name|autoCompFields
decl_stmt|;
DECL|field|prefs
specifier|private
specifier|final
name|JabRefPreferences
name|prefs
decl_stmt|;
DECL|field|autoCompletePreferences
specifier|private
specifier|final
name|AutoCompletePreferences
name|autoCompletePreferences
decl_stmt|;
DECL|method|EntryEditorPrefsTab (JabRefPreferences prefs)
specifier|public
name|EntryEditorPrefsTab
parameter_list|(
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
name|autoCompletePreferences
operator|=
name|prefs
operator|.
name|getAutoCompletePreferences
argument_list|()
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
name|Localization
operator|.
name|lang
argument_list|(
literal|"Open editor when a new entry is created"
argument_list|)
argument_list|)
expr_stmt|;
name|defSource
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Show BibTeX source by default"
argument_list|)
argument_list|)
expr_stmt|;
name|emacsMode
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Use Emacs key bindings"
argument_list|)
argument_list|)
expr_stmt|;
name|emacsRebindCtrlA
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Rebind C-a, too"
argument_list|)
argument_list|)
expr_stmt|;
name|emacsRebindCtrlF
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Rebind C-f, too"
argument_list|)
argument_list|)
expr_stmt|;
name|autoComplete
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Enable word/name autocompletion"
argument_list|)
argument_list|)
expr_stmt|;
name|recommendations
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Show 'Related Articles' tab"
argument_list|)
argument_list|)
expr_stmt|;
name|validation
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Show validation messages"
argument_list|)
argument_list|)
expr_stmt|;
comment|// allowed name formats
name|autoCompFF
operator|=
operator|new
name|JRadioButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Autocomplete names in 'Firstname Lastname' format only"
argument_list|)
argument_list|)
expr_stmt|;
name|autoCompLF
operator|=
operator|new
name|JRadioButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Autocomplete names in 'Lastname, Firstname' format only"
argument_list|)
argument_list|)
expr_stmt|;
name|autoCompBoth
operator|=
operator|new
name|JRadioButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Autocomplete names in both formats"
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
name|autoCompLF
argument_list|)
expr_stmt|;
name|buttonGroup
operator|.
name|add
argument_list|(
name|autoCompFF
argument_list|)
expr_stmt|;
name|buttonGroup
operator|.
name|add
argument_list|(
name|autoCompBoth
argument_list|)
expr_stmt|;
comment|// treatment of first name
name|firstNameModeFull
operator|=
operator|new
name|JRadioButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Use full firstname whenever possible"
argument_list|)
argument_list|)
expr_stmt|;
name|firstNameModeAbbr
operator|=
operator|new
name|JRadioButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Use abbreviated firstname whenever possible"
argument_list|)
argument_list|)
expr_stmt|;
name|firstNameModeBoth
operator|=
operator|new
name|JRadioButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Use abbreviated and full firstname"
argument_list|)
argument_list|)
expr_stmt|;
name|ButtonGroup
name|firstNameModeButtonGroup
init|=
operator|new
name|ButtonGroup
argument_list|()
decl_stmt|;
name|firstNameModeButtonGroup
operator|.
name|add
argument_list|(
name|firstNameModeFull
argument_list|)
expr_stmt|;
name|firstNameModeButtonGroup
operator|.
name|add
argument_list|(
name|firstNameModeAbbr
argument_list|)
expr_stmt|;
name|firstNameModeButtonGroup
operator|.
name|add
argument_list|(
name|firstNameModeBoth
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
literal|20
argument_list|,
literal|3
argument_list|,
literal|0
argument_list|)
decl_stmt|;
name|emacsRebindCtrlA
operator|.
name|setMargin
argument_list|(
name|marg
argument_list|)
expr_stmt|;
comment|// We need a listener on showSource to enable and disable the source panel-related choices:
name|emacsMode
operator|.
name|addChangeListener
argument_list|(
name|event
lambda|->
name|emacsRebindCtrlA
operator|.
name|setEnabled
argument_list|(
name|emacsMode
operator|.
name|isSelected
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|emacsRebindCtrlF
operator|.
name|setMargin
argument_list|(
name|marg
argument_list|)
expr_stmt|;
comment|// We need a listener on showSource to enable and disable the source panel-related choices:
name|emacsMode
operator|.
name|addChangeListener
argument_list|(
name|event
lambda|->
name|emacsRebindCtrlF
operator|.
name|setEnabled
argument_list|(
name|emacsMode
operator|.
name|isSelected
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|autoCompFields
operator|=
operator|new
name|JTextField
argument_list|(
literal|40
argument_list|)
expr_stmt|;
comment|// We need a listener on autoComplete to enable and disable the
comment|// autoCompFields text field:
name|autoComplete
operator|.
name|addChangeListener
argument_list|(
name|event
lambda|->
name|setAutoCompleteElementsEnabled
argument_list|(
name|autoComplete
operator|.
name|isSelected
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|FormLayout
name|layout
init|=
operator|new
name|FormLayout
argument_list|(
comment|// columns
literal|"8dlu, left:pref, 8dlu, fill:150dlu, 4dlu, fill:pref"
argument_list|,
comment|// 4dlu, left:pref, 4dlu",
comment|// rows  1 to 10
literal|"pref, 6dlu, pref, 6dlu, pref, 6dlu, pref, 6dlu, pref, 6dlu, pref, 6dlu, pref, 6dlu,"
operator|+
comment|// rows 11 to 16
literal|"pref, 6dlu, pref, 6dlu, pref, 6dlu, "
operator|+
comment|// rows 17 to 27
literal|"pref, 6dlu, pref, pref, pref, pref, 6dlu, pref, pref, pref, pref, pref"
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
name|CellConstraints
name|cc
init|=
operator|new
name|CellConstraints
argument_list|()
decl_stmt|;
name|builder
operator|.
name|addSeparator
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Editor options"
argument_list|)
argument_list|,
name|cc
operator|.
name|xyw
argument_list|(
literal|1
argument_list|,
literal|1
argument_list|,
literal|5
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|autoOpenForm
argument_list|,
name|cc
operator|.
name|xy
argument_list|(
literal|2
argument_list|,
literal|3
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|defSource
argument_list|,
name|cc
operator|.
name|xy
argument_list|(
literal|2
argument_list|,
literal|5
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|emacsMode
argument_list|,
name|cc
operator|.
name|xy
argument_list|(
literal|2
argument_list|,
literal|7
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|emacsRebindCtrlA
argument_list|,
name|cc
operator|.
name|xy
argument_list|(
literal|2
argument_list|,
literal|9
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|emacsRebindCtrlF
argument_list|,
name|cc
operator|.
name|xy
argument_list|(
literal|2
argument_list|,
literal|11
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|recommendations
argument_list|,
name|cc
operator|.
name|xy
argument_list|(
literal|2
argument_list|,
literal|13
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|validation
argument_list|,
name|cc
operator|.
name|xy
argument_list|(
literal|2
argument_list|,
literal|15
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|addSeparator
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Autocompletion options"
argument_list|)
argument_list|,
name|cc
operator|.
name|xyw
argument_list|(
literal|1
argument_list|,
literal|17
argument_list|,
literal|5
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|autoComplete
argument_list|,
name|cc
operator|.
name|xy
argument_list|(
literal|2
argument_list|,
literal|19
argument_list|)
argument_list|)
expr_stmt|;
name|DefaultFormBuilder
name|builder3
init|=
operator|new
name|DefaultFormBuilder
argument_list|(
operator|new
name|FormLayout
argument_list|(
literal|"left:pref, 4dlu, fill:150dlu"
argument_list|,
literal|""
argument_list|)
argument_list|)
decl_stmt|;
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
literal|"Use autocompletion for the following fields"
argument_list|)
operator|+
literal|":"
argument_list|)
decl_stmt|;
name|builder3
operator|.
name|append
argument_list|(
name|label
argument_list|)
expr_stmt|;
name|builder3
operator|.
name|append
argument_list|(
name|autoCompFields
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|builder3
operator|.
name|getPanel
argument_list|()
argument_list|,
name|cc
operator|.
name|xyw
argument_list|(
literal|2
argument_list|,
literal|21
argument_list|,
literal|3
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|addSeparator
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Name format used for autocompletion"
argument_list|)
argument_list|,
name|cc
operator|.
name|xyw
argument_list|(
literal|2
argument_list|,
literal|23
argument_list|,
literal|4
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|autoCompFF
argument_list|,
name|cc
operator|.
name|xy
argument_list|(
literal|2
argument_list|,
literal|24
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|autoCompLF
argument_list|,
name|cc
operator|.
name|xy
argument_list|(
literal|2
argument_list|,
literal|25
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|autoCompBoth
argument_list|,
name|cc
operator|.
name|xy
argument_list|(
literal|2
argument_list|,
literal|26
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|addSeparator
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Treatment of first names"
argument_list|)
argument_list|,
name|cc
operator|.
name|xyw
argument_list|(
literal|2
argument_list|,
literal|29
argument_list|,
literal|4
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|firstNameModeAbbr
argument_list|,
name|cc
operator|.
name|xy
argument_list|(
literal|2
argument_list|,
literal|30
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|firstNameModeFull
argument_list|,
name|cc
operator|.
name|xy
argument_list|(
literal|2
argument_list|,
literal|31
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|firstNameModeBoth
argument_list|,
name|cc
operator|.
name|xy
argument_list|(
literal|2
argument_list|,
literal|32
argument_list|)
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
DECL|method|setAutoCompleteElementsEnabled (boolean enabled)
specifier|private
name|void
name|setAutoCompleteElementsEnabled
parameter_list|(
name|boolean
name|enabled
parameter_list|)
block|{
name|autoCompFields
operator|.
name|setEnabled
argument_list|(
name|enabled
argument_list|)
expr_stmt|;
name|autoCompLF
operator|.
name|setEnabled
argument_list|(
name|enabled
argument_list|)
expr_stmt|;
name|autoCompFF
operator|.
name|setEnabled
argument_list|(
name|enabled
argument_list|)
expr_stmt|;
name|autoCompBoth
operator|.
name|setEnabled
argument_list|(
name|enabled
argument_list|)
expr_stmt|;
name|firstNameModeAbbr
operator|.
name|setEnabled
argument_list|(
name|enabled
argument_list|)
expr_stmt|;
name|firstNameModeFull
operator|.
name|setEnabled
argument_list|(
name|enabled
argument_list|)
expr_stmt|;
name|firstNameModeBoth
operator|.
name|setEnabled
argument_list|(
name|enabled
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
name|autoOpenForm
operator|.
name|setSelected
argument_list|(
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|AUTO_OPEN_FORM
argument_list|)
argument_list|)
expr_stmt|;
name|defSource
operator|.
name|setSelected
argument_list|(
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|DEFAULT_SHOW_SOURCE
argument_list|)
argument_list|)
expr_stmt|;
name|emacsMode
operator|.
name|setSelected
argument_list|(
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|EDITOR_EMACS_KEYBINDINGS
argument_list|)
argument_list|)
expr_stmt|;
name|emacsRebindCtrlA
operator|.
name|setSelected
argument_list|(
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|EDITOR_EMACS_KEYBINDINGS_REBIND_CA
argument_list|)
argument_list|)
expr_stmt|;
name|emacsRebindCtrlF
operator|.
name|setSelected
argument_list|(
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|EDITOR_EMACS_KEYBINDINGS_REBIND_CF
argument_list|)
argument_list|)
expr_stmt|;
name|recommendations
operator|.
name|setSelected
argument_list|(
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|SHOW_RECOMMENDATIONS
argument_list|)
argument_list|)
expr_stmt|;
name|autoComplete
operator|.
name|setSelected
argument_list|(
name|autoCompletePreferences
operator|.
name|shouldAutoComplete
argument_list|()
argument_list|)
expr_stmt|;
name|autoCompFields
operator|.
name|setText
argument_list|(
name|autoCompletePreferences
operator|.
name|getCompleteNamesAsString
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|autoCompletePreferences
operator|.
name|getOnlyCompleteFirstLast
argument_list|()
condition|)
block|{
name|autoCompFF
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
name|autoCompletePreferences
operator|.
name|getOnlyCompleteLastFirst
argument_list|()
condition|)
block|{
name|autoCompLF
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|autoCompBoth
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
switch|switch
condition|(
name|autoCompletePreferences
operator|.
name|getFirstNameMode
argument_list|()
condition|)
block|{
case|case
name|ONLY_ABBREVIATED
case|:
name|firstNameModeAbbr
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
break|break;
case|case
name|ONLY_FULL
case|:
name|firstNameModeFull
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
break|break;
default|default:
name|firstNameModeBoth
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
break|break;
block|}
comment|// similar for emacs CTRL-a and emacs mode
name|emacsRebindCtrlA
operator|.
name|setEnabled
argument_list|(
name|emacsMode
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
comment|// Autocomplete fields is only enabled when autocompletion is selected
name|setAutoCompleteElementsEnabled
argument_list|(
name|autoComplete
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|validation
operator|.
name|setSelected
argument_list|(
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|VALIDATE_IN_ENTRY_EDITOR
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
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|AUTO_OPEN_FORM
argument_list|,
name|autoOpenForm
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
name|DEFAULT_SHOW_SOURCE
argument_list|,
name|defSource
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
name|SHOW_RECOMMENDATIONS
argument_list|,
name|recommendations
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
name|VALIDATE_IN_ENTRY_EDITOR
argument_list|,
name|validation
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|boolean
name|emacsModeChanged
init|=
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|EDITOR_EMACS_KEYBINDINGS
argument_list|)
operator|!=
name|emacsMode
operator|.
name|isSelected
argument_list|()
decl_stmt|;
name|boolean
name|emacsRebindCtrlAChanged
init|=
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|EDITOR_EMACS_KEYBINDINGS_REBIND_CA
argument_list|)
operator|!=
name|emacsRebindCtrlA
operator|.
name|isSelected
argument_list|()
decl_stmt|;
name|boolean
name|emacsRebindCtrlFChanged
init|=
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|EDITOR_EMACS_KEYBINDINGS_REBIND_CF
argument_list|)
operator|!=
name|emacsRebindCtrlF
operator|.
name|isSelected
argument_list|()
decl_stmt|;
if|if
condition|(
name|emacsModeChanged
operator|||
name|emacsRebindCtrlAChanged
operator|||
name|emacsRebindCtrlFChanged
condition|)
block|{
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|EDITOR_EMACS_KEYBINDINGS
argument_list|,
name|emacsMode
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
name|EDITOR_EMACS_KEYBINDINGS_REBIND_CA
argument_list|,
name|emacsRebindCtrlA
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
name|EDITOR_EMACS_KEYBINDINGS_REBIND_CF
argument_list|,
name|emacsRebindCtrlF
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
comment|// immediately apply the change
if|if
condition|(
name|emacsModeChanged
condition|)
block|{
if|if
condition|(
name|emacsMode
operator|.
name|isSelected
argument_list|()
condition|)
block|{
name|EmacsKeyBindings
operator|.
name|load
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|EmacsKeyBindings
operator|.
name|unload
argument_list|()
expr_stmt|;
block|}
block|}
else|else
block|{
comment|// only rebinding of CTRL+a or CTRL+f changed
assert|assert
name|emacsMode
operator|.
name|isSelected
argument_list|()
assert|;
comment|// we simply reload the emacs mode to activate the CTRL+a/CTRL+f change
name|EmacsKeyBindings
operator|.
name|unload
argument_list|()
expr_stmt|;
name|EmacsKeyBindings
operator|.
name|load
argument_list|()
expr_stmt|;
block|}
block|}
name|autoCompletePreferences
operator|.
name|setShouldAutoComplete
argument_list|(
name|autoComplete
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|autoCompletePreferences
operator|.
name|setCompleteNames
argument_list|(
name|autoCompFields
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|autoCompBoth
operator|.
name|isSelected
argument_list|()
condition|)
block|{
name|autoCompletePreferences
operator|.
name|setOnlyCompleteFirstLast
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|autoCompletePreferences
operator|.
name|setOnlyCompleteLastFirst
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|autoCompFF
operator|.
name|isSelected
argument_list|()
condition|)
block|{
name|autoCompletePreferences
operator|.
name|setOnlyCompleteFirstLast
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|autoCompletePreferences
operator|.
name|setOnlyCompleteLastFirst
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|autoCompletePreferences
operator|.
name|setOnlyCompleteFirstLast
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|autoCompletePreferences
operator|.
name|setOnlyCompleteLastFirst
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|firstNameModeAbbr
operator|.
name|isSelected
argument_list|()
condition|)
block|{
name|autoCompletePreferences
operator|.
name|setFirstNameMode
argument_list|(
name|ONLY_ABBREVIATED
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|firstNameModeFull
operator|.
name|isSelected
argument_list|()
condition|)
block|{
name|autoCompletePreferences
operator|.
name|setFirstNameMode
argument_list|(
name|ONLY_FULL
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|autoCompletePreferences
operator|.
name|setFirstNameMode
argument_list|(
name|AutoCompleteFirstNameMode
operator|.
name|BOTH
argument_list|)
expr_stmt|;
block|}
name|prefs
operator|.
name|storeAutoCompletePreferences
argument_list|(
name|autoCompletePreferences
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
literal|"Entry editor"
argument_list|)
return|;
block|}
block|}
end_class

end_unit

