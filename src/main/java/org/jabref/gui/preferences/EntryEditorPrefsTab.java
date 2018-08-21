begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.preferences
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|preferences
package|;
end_package

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|Node
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|CheckBox
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|Label
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|RadioButton
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|Separator
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|TextField
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|layout
operator|.
name|GridPane
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|layout
operator|.
name|Pane
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
name|Pane
implements|implements
name|PrefsTab
block|{
DECL|field|autoOpenForm
specifier|private
specifier|final
name|CheckBox
name|autoOpenForm
decl_stmt|;
DECL|field|defSource
specifier|private
specifier|final
name|CheckBox
name|defSource
decl_stmt|;
DECL|field|emacsMode
specifier|private
specifier|final
name|CheckBox
name|emacsMode
decl_stmt|;
DECL|field|emacsRebindCtrlA
specifier|private
specifier|final
name|CheckBox
name|emacsRebindCtrlA
decl_stmt|;
DECL|field|emacsRebindCtrlF
specifier|private
specifier|final
name|CheckBox
name|emacsRebindCtrlF
decl_stmt|;
DECL|field|autoComplete
specifier|private
specifier|final
name|CheckBox
name|autoComplete
decl_stmt|;
DECL|field|recommendations
specifier|private
specifier|final
name|CheckBox
name|recommendations
decl_stmt|;
DECL|field|validation
specifier|private
specifier|final
name|CheckBox
name|validation
decl_stmt|;
DECL|field|autoCompBoth
specifier|private
specifier|final
name|RadioButton
name|autoCompBoth
decl_stmt|;
DECL|field|autoCompFF
specifier|private
specifier|final
name|RadioButton
name|autoCompFF
decl_stmt|;
DECL|field|autoCompLF
specifier|private
specifier|final
name|RadioButton
name|autoCompLF
decl_stmt|;
DECL|field|firstNameModeFull
specifier|private
specifier|final
name|RadioButton
name|firstNameModeFull
decl_stmt|;
DECL|field|firstNameModeAbbr
specifier|private
specifier|final
name|RadioButton
name|firstNameModeAbbr
decl_stmt|;
DECL|field|firstNameModeBoth
specifier|private
specifier|final
name|RadioButton
name|firstNameModeBoth
decl_stmt|;
DECL|field|builder
specifier|private
specifier|final
name|GridPane
name|builder
init|=
operator|new
name|GridPane
argument_list|()
decl_stmt|;
DECL|field|autoCompFields
specifier|private
specifier|final
name|TextField
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
name|autoOpenForm
operator|=
operator|new
name|CheckBox
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
name|CheckBox
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
name|CheckBox
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
name|CheckBox
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
name|CheckBox
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
name|CheckBox
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
name|CheckBox
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
name|CheckBox
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
name|RadioButton
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
name|RadioButton
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
name|RadioButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Autocomplete names in both formats"
argument_list|)
argument_list|)
expr_stmt|;
comment|// treatment of first name
name|firstNameModeFull
operator|=
operator|new
name|RadioButton
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
name|RadioButton
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
name|RadioButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Use abbreviated and full firstname"
argument_list|)
argument_list|)
expr_stmt|;
comment|// We need a listener on showSource to enable and disable the source panel-related choices:
name|emacsMode
operator|.
name|setOnAction
argument_list|(
name|event
lambda|->
name|emacsRebindCtrlA
operator|.
name|setDisable
argument_list|(
operator|!
name|emacsMode
operator|.
name|isSelected
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
comment|// We need a listener on showSource to enable and disable the source panel-related choices:
name|emacsMode
operator|.
name|setOnAction
argument_list|(
name|event
lambda|->
name|emacsRebindCtrlF
operator|.
name|setDisable
argument_list|(
operator|!
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
name|TextField
argument_list|()
expr_stmt|;
comment|// We need a listener on autoComplete to enable and disable the
comment|// autoCompFields text field:
name|autoComplete
operator|.
name|setOnAction
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
name|Label
name|editorOptions
init|=
operator|new
name|Label
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Editor options"
argument_list|)
argument_list|)
decl_stmt|;
name|editorOptions
operator|.
name|getStyleClass
argument_list|()
operator|.
name|add
argument_list|(
literal|"sectionHeader"
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|editorOptions
argument_list|,
literal|1
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
operator|new
name|Separator
argument_list|()
argument_list|,
literal|2
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|autoOpenForm
argument_list|,
literal|1
argument_list|,
literal|2
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|defSource
argument_list|,
literal|1
argument_list|,
literal|3
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|emacsMode
argument_list|,
literal|1
argument_list|,
literal|4
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|emacsRebindCtrlA
argument_list|,
literal|1
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|emacsRebindCtrlF
argument_list|,
literal|1
argument_list|,
literal|6
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|recommendations
argument_list|,
literal|1
argument_list|,
literal|7
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|validation
argument_list|,
literal|1
argument_list|,
literal|8
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
operator|new
name|Label
argument_list|(
literal|""
argument_list|)
argument_list|,
literal|1
argument_list|,
literal|9
argument_list|)
expr_stmt|;
name|Label
name|autocompletionOptions
init|=
operator|new
name|Label
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Autocompletion options"
argument_list|)
argument_list|)
decl_stmt|;
name|autocompletionOptions
operator|.
name|getStyleClass
argument_list|()
operator|.
name|add
argument_list|(
literal|"sectionHeader"
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|autocompletionOptions
argument_list|,
literal|1
argument_list|,
literal|10
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|autoComplete
argument_list|,
literal|1
argument_list|,
literal|11
argument_list|)
expr_stmt|;
name|Label
name|useFields
init|=
operator|new
name|Label
argument_list|(
literal|"       "
operator|+
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
name|builder
operator|.
name|add
argument_list|(
name|useFields
argument_list|,
literal|1
argument_list|,
literal|12
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|autoCompFields
argument_list|,
literal|2
argument_list|,
literal|12
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
operator|new
name|Label
argument_list|(
literal|""
argument_list|)
argument_list|,
literal|1
argument_list|,
literal|13
argument_list|)
expr_stmt|;
name|Label
name|nameFormat
init|=
operator|new
name|Label
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Name format used for autocompletion"
argument_list|)
argument_list|)
decl_stmt|;
name|nameFormat
operator|.
name|getStyleClass
argument_list|()
operator|.
name|add
argument_list|(
literal|"sectionHeader"
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|nameFormat
argument_list|,
literal|1
argument_list|,
literal|14
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|autoCompFF
argument_list|,
literal|1
argument_list|,
literal|15
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|autoCompLF
argument_list|,
literal|1
argument_list|,
literal|16
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|autoCompBoth
argument_list|,
literal|1
argument_list|,
literal|17
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
operator|new
name|Label
argument_list|(
literal|""
argument_list|)
argument_list|,
literal|1
argument_list|,
literal|18
argument_list|)
expr_stmt|;
name|Label
name|treatment
init|=
operator|new
name|Label
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Treatment of first names"
argument_list|)
argument_list|)
decl_stmt|;
name|treatment
operator|.
name|getStyleClass
argument_list|()
operator|.
name|add
argument_list|(
literal|"sectionHeader"
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|treatment
argument_list|,
literal|1
argument_list|,
literal|19
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|firstNameModeAbbr
argument_list|,
literal|1
argument_list|,
literal|20
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|firstNameModeFull
argument_list|,
literal|1
argument_list|,
literal|21
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|firstNameModeBoth
argument_list|,
literal|1
argument_list|,
literal|22
argument_list|)
expr_stmt|;
block|}
DECL|method|getBuilder ()
specifier|public
name|Node
name|getBuilder
parameter_list|()
block|{
return|return
name|builder
return|;
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
name|setDisable
argument_list|(
operator|!
name|enabled
argument_list|)
expr_stmt|;
name|autoCompLF
operator|.
name|setDisable
argument_list|(
operator|!
name|enabled
argument_list|)
expr_stmt|;
name|autoCompFF
operator|.
name|setDisable
argument_list|(
operator|!
name|enabled
argument_list|)
expr_stmt|;
name|autoCompBoth
operator|.
name|setDisable
argument_list|(
operator|!
name|enabled
argument_list|)
expr_stmt|;
name|firstNameModeAbbr
operator|.
name|setDisable
argument_list|(
operator|!
name|enabled
argument_list|)
expr_stmt|;
name|firstNameModeFull
operator|.
name|setDisable
argument_list|(
operator|!
name|enabled
argument_list|)
expr_stmt|;
name|firstNameModeBoth
operator|.
name|setDisable
argument_list|(
operator|!
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
name|setDisable
argument_list|(
operator|!
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

