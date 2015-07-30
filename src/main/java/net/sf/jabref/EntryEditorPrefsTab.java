begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|Insets
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
name|logic
operator|.
name|autocompleter
operator|.
name|AutoCompleterFactory
import|;
end_import

begin_import
import|import
name|org
operator|.
name|xnap
operator|.
name|commons
operator|.
name|gui
operator|.
name|shortcut
operator|.
name|EmacsKeyBindings
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

begin_class
DECL|class|EntryEditorPrefsTab
specifier|public
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
DECL|field|showSource
specifier|private
specifier|final
name|JCheckBox
name|showSource
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
DECL|field|disableOnMultiple
specifier|private
specifier|final
name|JCheckBox
name|disableOnMultiple
decl_stmt|;
DECL|field|autoComplete
specifier|private
specifier|final
name|JCheckBox
name|autoComplete
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
DECL|field|autoCompFirstNameMode_Full
specifier|private
specifier|final
name|JRadioButton
name|autoCompFirstNameMode_Full
decl_stmt|;
DECL|field|autoCompFirstNameMode_Abbr
specifier|private
specifier|final
name|JRadioButton
name|autoCompFirstNameMode_Abbr
decl_stmt|;
DECL|field|autoCompFirstNameMode_Both
specifier|private
specifier|final
name|JRadioButton
name|autoCompFirstNameMode_Both
decl_stmt|;
DECL|field|oldAutoCompFF
specifier|private
name|boolean
name|oldAutoCompFF
decl_stmt|;
DECL|field|oldAutoCompLF
specifier|private
name|boolean
name|oldAutoCompLF
decl_stmt|;
DECL|field|oldAutoCompFModeAbbr
specifier|private
name|boolean
name|oldAutoCompFModeAbbr
decl_stmt|;
DECL|field|oldAutoCompFModeFull
specifier|private
name|boolean
name|oldAutoCompFModeFull
decl_stmt|;
DECL|field|shortestToComplete
specifier|private
specifier|final
name|JSpinner
name|shortestToComplete
decl_stmt|;
DECL|field|autoCompFields
specifier|private
specifier|final
name|JTextField
name|autoCompFields
decl_stmt|;
DECL|field|_prefs
specifier|private
specifier|final
name|JabRefPreferences
name|_prefs
decl_stmt|;
DECL|field|_frame
specifier|private
specifier|final
name|JabRefFrame
name|_frame
decl_stmt|;
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
name|autoCompFirstNameMode_Abbr
operator|.
name|setEnabled
argument_list|(
name|enabled
argument_list|)
expr_stmt|;
name|autoCompFirstNameMode_Full
operator|.
name|setEnabled
argument_list|(
name|enabled
argument_list|)
expr_stmt|;
name|autoCompFirstNameMode_Both
operator|.
name|setEnabled
argument_list|(
name|enabled
argument_list|)
expr_stmt|;
name|shortestToComplete
operator|.
name|setEnabled
argument_list|(
name|enabled
argument_list|)
expr_stmt|;
block|}
DECL|method|EntryEditorPrefsTab (JabRefFrame frame, JabRefPreferences prefs)
specifier|public
name|EntryEditorPrefsTab
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
name|showSource
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Show BibTeX source panel"
argument_list|)
argument_list|)
expr_stmt|;
name|emacsMode
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
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
name|Globals
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
name|Globals
operator|.
name|lang
argument_list|(
literal|"Rebind C-f, too"
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
name|autoComplete
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Enable word/name autocompletion"
argument_list|)
argument_list|)
expr_stmt|;
name|shortestToComplete
operator|=
operator|new
name|JSpinner
argument_list|(
operator|new
name|SpinnerNumberModel
argument_list|(
name|prefs
operator|.
name|getInt
argument_list|(
name|JabRefPreferences
operator|.
name|SHORTEST_TO_COMPLETE
argument_list|)
argument_list|,
literal|1
argument_list|,
literal|5
argument_list|,
literal|1
argument_list|)
argument_list|)
expr_stmt|;
comment|// allowed name formats
name|autoCompFF
operator|=
operator|new
name|JRadioButton
argument_list|(
name|Globals
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
name|Globals
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
name|Globals
operator|.
name|lang
argument_list|(
literal|"Autocomplete names in both formats"
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
name|autoCompLF
argument_list|)
expr_stmt|;
name|bg
operator|.
name|add
argument_list|(
name|autoCompFF
argument_list|)
expr_stmt|;
name|bg
operator|.
name|add
argument_list|(
name|autoCompBoth
argument_list|)
expr_stmt|;
comment|// treatment of first name
name|autoCompFirstNameMode_Full
operator|=
operator|new
name|JRadioButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Use full firstname whenever possible"
argument_list|)
argument_list|)
expr_stmt|;
name|autoCompFirstNameMode_Abbr
operator|=
operator|new
name|JRadioButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Use abbreviated firstname whenever possible"
argument_list|)
argument_list|)
expr_stmt|;
name|autoCompFirstNameMode_Both
operator|=
operator|new
name|JRadioButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Use abbreviated and full firstname"
argument_list|)
argument_list|)
expr_stmt|;
name|ButtonGroup
name|bg_firstNameMode
init|=
operator|new
name|ButtonGroup
argument_list|()
decl_stmt|;
name|bg_firstNameMode
operator|.
name|add
argument_list|(
name|autoCompFirstNameMode_Full
argument_list|)
expr_stmt|;
name|bg_firstNameMode
operator|.
name|add
argument_list|(
name|autoCompFirstNameMode_Abbr
argument_list|)
expr_stmt|;
name|bg_firstNameMode
operator|.
name|add
argument_list|(
name|autoCompFirstNameMode_Both
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
name|defSource
operator|.
name|setMargin
argument_list|(
name|marg
argument_list|)
expr_stmt|;
comment|// We need a listener on showSource to enable and disable the source panel-related choices:
name|showSource
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
name|defSource
operator|.
name|setEnabled
argument_list|(
name|showSource
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
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
block|}
block|}
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
operator|new
name|ChangeListener
argument_list|()
block|{
specifier|public
name|void
name|stateChanged
parameter_list|(
name|ChangeEvent
name|event
parameter_list|)
block|{
name|emacsRebindCtrlF
operator|.
name|setEnabled
argument_list|(
name|emacsMode
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
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
name|setAutoCompleteElementsEnabled
argument_list|(
name|autoComplete
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
comment|// columns
literal|"8dlu, left:pref, 8dlu, fill:150dlu, 4dlu, fill:pref"
argument_list|,
comment|// 4dlu, left:pref, 4dlu",
comment|// rows  1 to 10
literal|"pref, 6dlu, pref, 6dlu, pref, 6dlu, pref, 6dlu, pref, 6dlu, "
operator|+
comment|// rows 11 to 20
literal|"pref, 6dlu, pref, 6dlu, pref, 6dlu, pref, 6dlu, pref, 6dlu, "
operator|+
comment|// rows 21 to 31
literal|"pref, 6dlu, pref, pref, pref, pref, 6dlu, pref, pref, pref, pref"
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
name|Globals
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
name|disableOnMultiple
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
name|showSource
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
name|defSource
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
name|emacsMode
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
name|emacsRebindCtrlA
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
name|emacsRebindCtrlF
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
name|Globals
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
name|Globals
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
name|JLabel
name|label2
init|=
operator|new
name|JLabel
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Autocomplete after following number of characters"
argument_list|)
operator|+
literal|":"
argument_list|)
decl_stmt|;
name|builder3
operator|.
name|append
argument_list|(
name|label2
argument_list|)
expr_stmt|;
name|builder3
operator|.
name|append
argument_list|(
name|shortestToComplete
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
name|Globals
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
name|Globals
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
literal|28
argument_list|,
literal|4
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|autoCompFirstNameMode_Abbr
argument_list|,
name|cc
operator|.
name|xy
argument_list|(
literal|2
argument_list|,
literal|29
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|autoCompFirstNameMode_Full
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
name|autoCompFirstNameMode_Both
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
name|autoOpenForm
operator|.
name|setSelected
argument_list|(
name|_prefs
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
name|_prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|DEFAULT_SHOW_SOURCE
argument_list|)
argument_list|)
expr_stmt|;
name|showSource
operator|.
name|setSelected
argument_list|(
name|_prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|SHOW_SOURCE
argument_list|)
argument_list|)
expr_stmt|;
name|emacsMode
operator|.
name|setSelected
argument_list|(
name|_prefs
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
name|_prefs
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
name|_prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|EDITOR_EMACS_KEYBINDINGS_REBIND_CF
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
name|JabRefPreferences
operator|.
name|DISABLE_ON_MULTIPLE_SELECTION
argument_list|)
argument_list|)
expr_stmt|;
name|autoComplete
operator|.
name|setSelected
argument_list|(
name|_prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|AUTO_COMPLETE
argument_list|)
argument_list|)
expr_stmt|;
name|autoCompFields
operator|.
name|setText
argument_list|(
name|_prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|AUTO_COMPLETE_FIELDS
argument_list|)
argument_list|)
expr_stmt|;
name|shortestToComplete
operator|.
name|setValue
argument_list|(
name|_prefs
operator|.
name|getInt
argument_list|(
name|JabRefPreferences
operator|.
name|SHORTEST_TO_COMPLETE
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
name|AUTO_COMP_FIRST_LAST
argument_list|)
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
name|_prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|AUTO_COMP_LAST_FIRST
argument_list|)
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
name|oldAutoCompFF
operator|=
name|autoCompFF
operator|.
name|isSelected
argument_list|()
expr_stmt|;
name|oldAutoCompLF
operator|=
name|autoCompLF
operator|.
name|isSelected
argument_list|()
expr_stmt|;
if|if
condition|(
name|_prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|AUTOCOMPLETE_FIRSTNAME_MODE
argument_list|)
operator|.
name|equals
argument_list|(
name|JabRefPreferences
operator|.
name|AUTOCOMPLETE_FIRSTNAME_MODE_ONLY_ABBR
argument_list|)
condition|)
block|{
name|autoCompFirstNameMode_Abbr
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
name|_prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|AUTOCOMPLETE_FIRSTNAME_MODE
argument_list|)
operator|.
name|equals
argument_list|(
name|JabRefPreferences
operator|.
name|AUTOCOMPLETE_FIRSTNAME_MODE_ONLY_FULL
argument_list|)
condition|)
block|{
name|autoCompFirstNameMode_Full
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|autoCompFirstNameMode_Both
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
comment|// one field less than the option is enough. If one filed changes, another one also changes.
name|oldAutoCompFModeAbbr
operator|=
name|autoCompFirstNameMode_Abbr
operator|.
name|isSelected
argument_list|()
expr_stmt|;
name|oldAutoCompFModeFull
operator|=
name|autoCompFirstNameMode_Full
operator|.
name|isSelected
argument_list|()
expr_stmt|;
comment|// This choice only makes sense when the source panel is visible:
name|defSource
operator|.
name|setEnabled
argument_list|(
name|showSource
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
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
name|_prefs
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
name|boolean
name|emacsModeChanged
init|=
name|_prefs
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
name|_prefs
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
name|_prefs
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
name|_prefs
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
name|_prefs
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
name|_prefs
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
name|_prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|DISABLE_ON_MULTIPLE_SELECTION
argument_list|,
name|disableOnMultiple
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
comment|// We want to know if the following settings have been modified:
name|boolean
name|oldAutoComplete
init|=
name|_prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|AUTO_COMPLETE
argument_list|)
decl_stmt|;
name|boolean
name|oldShowSource
init|=
name|_prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|SHOW_SOURCE
argument_list|)
decl_stmt|;
name|String
name|oldAutoCompFields
init|=
name|_prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|AUTO_COMPLETE_FIELDS
argument_list|)
decl_stmt|;
name|_prefs
operator|.
name|putInt
argument_list|(
name|JabRefPreferences
operator|.
name|SHORTEST_TO_COMPLETE
argument_list|,
operator|(
name|Integer
operator|)
name|shortestToComplete
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|AUTO_COMPLETE
argument_list|,
name|autoComplete
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
name|AUTO_COMPLETE_FIELDS
argument_list|,
name|autoCompFields
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
name|SHOW_SOURCE
argument_list|,
name|showSource
operator|.
name|isSelected
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
name|_prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|AUTO_COMP_FIRST_LAST
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|AUTO_COMP_LAST_FIRST
argument_list|,
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
name|_prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|AUTO_COMP_FIRST_LAST
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|AUTO_COMP_LAST_FIRST
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|_prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|AUTO_COMP_FIRST_LAST
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|AUTO_COMP_LAST_FIRST
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|autoCompFirstNameMode_Abbr
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
name|AUTOCOMPLETE_FIRSTNAME_MODE
argument_list|,
name|JabRefPreferences
operator|.
name|AUTOCOMPLETE_FIRSTNAME_MODE_ONLY_ABBR
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|autoCompFirstNameMode_Full
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
name|AUTOCOMPLETE_FIRSTNAME_MODE
argument_list|,
name|JabRefPreferences
operator|.
name|AUTOCOMPLETE_FIRSTNAME_MODE_ONLY_FULL
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|_prefs
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|AUTOCOMPLETE_FIRSTNAME_MODE
argument_list|,
name|JabRefPreferences
operator|.
name|AUTOCOMPLETE_FIRSTNAME_MODE_BOTH
argument_list|)
expr_stmt|;
block|}
comment|// We need to remove all entry editors from cache if the source panel setting
comment|// or the autocompletion settings have been changed:
if|if
condition|(
name|oldShowSource
operator|!=
name|showSource
operator|.
name|isSelected
argument_list|()
operator|||
name|oldAutoComplete
operator|!=
name|autoComplete
operator|.
name|isSelected
argument_list|()
operator|||
operator|!
name|oldAutoCompFields
operator|.
name|equals
argument_list|(
name|autoCompFields
operator|.
name|getText
argument_list|()
argument_list|)
operator|||
name|oldAutoCompFF
operator|!=
name|autoCompFF
operator|.
name|isSelected
argument_list|()
operator|||
name|oldAutoCompLF
operator|!=
name|autoCompLF
operator|.
name|isSelected
argument_list|()
operator|||
name|oldAutoCompFModeAbbr
operator|!=
name|autoCompFirstNameMode_Abbr
operator|.
name|isSelected
argument_list|()
operator|||
name|oldAutoCompFModeFull
operator|!=
name|autoCompFirstNameMode_Full
operator|.
name|isSelected
argument_list|()
condition|)
block|{
for|for
control|(
name|int
name|j
init|=
literal|0
init|;
name|j
operator|<
name|_frame
operator|.
name|getTabbedPane
argument_list|()
operator|.
name|getTabCount
argument_list|()
condition|;
name|j
operator|++
control|)
block|{
name|BasePanel
name|bp
init|=
operator|(
name|BasePanel
operator|)
name|_frame
operator|.
name|getTabbedPane
argument_list|()
operator|.
name|getComponentAt
argument_list|(
name|j
argument_list|)
decl_stmt|;
name|bp
operator|.
name|entryEditors
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
block|}
comment|// the autocompleter has to be updated to the new min length to complete
name|AutoCompleterFactory
operator|.
name|SHORTEST_TO_COMPLETE
operator|=
operator|(
name|Integer
operator|)
name|shortestToComplete
operator|.
name|getValue
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
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
literal|"Entry editor"
argument_list|)
return|;
block|}
block|}
end_class

end_unit

