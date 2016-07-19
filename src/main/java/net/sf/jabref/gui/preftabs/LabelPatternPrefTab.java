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
name|GridBagConstraints
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
name|BasePanel
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
name|labelpattern
operator|.
name|LabelPatternPanel
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
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|labelpattern
operator|.
name|GlobalLabelPattern
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
name|labelpattern
operator|.
name|LabelPatternUtil
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
comment|/**  * The Preferences panel for key generation.  */
end_comment

begin_class
DECL|class|LabelPatternPrefTab
class|class
name|LabelPatternPrefTab
extends|extends
name|LabelPatternPanel
implements|implements
name|PrefsTab
block|{
DECL|field|prefs
specifier|private
specifier|final
name|JabRefPreferences
name|prefs
decl_stmt|;
DECL|field|dontOverwrite
specifier|private
specifier|final
name|JCheckBox
name|dontOverwrite
init|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Do not overwrite existing keys"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|warnBeforeOverwriting
specifier|private
specifier|final
name|JCheckBox
name|warnBeforeOverwriting
init|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Warn before overwriting existing keys"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|generateOnSave
specifier|private
specifier|final
name|JCheckBox
name|generateOnSave
init|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Generate keys before saving (for entries without a key)"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|autoGenerateOnImport
specifier|private
specifier|final
name|JCheckBox
name|autoGenerateOnImport
init|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Generate keys for imported entries"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|letterStartA
specifier|private
specifier|final
name|JRadioButton
name|letterStartA
init|=
operator|new
name|JRadioButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Ensure unique keys using letters (a, b, ...)"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|letterStartB
specifier|private
specifier|final
name|JRadioButton
name|letterStartB
init|=
operator|new
name|JRadioButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Ensure unique keys using letters (b, c, ...)"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|alwaysAddLetter
specifier|private
specifier|final
name|JRadioButton
name|alwaysAddLetter
init|=
operator|new
name|JRadioButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Always add letter (a, b, ...) to generated keys"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|keyPatternRegex
specifier|private
specifier|final
name|JTextField
name|keyPatternRegex
init|=
operator|new
name|JTextField
argument_list|(
literal|20
argument_list|)
decl_stmt|;
DECL|field|keyPatternReplacement
specifier|private
specifier|final
name|JTextField
name|keyPatternReplacement
init|=
operator|new
name|JTextField
argument_list|(
literal|20
argument_list|)
decl_stmt|;
DECL|method|LabelPatternPrefTab (JabRefPreferences prefs, BasePanel panel)
specifier|public
name|LabelPatternPrefTab
parameter_list|(
name|JabRefPreferences
name|prefs
parameter_list|,
name|BasePanel
name|panel
parameter_list|)
block|{
name|super
argument_list|(
name|panel
argument_list|)
expr_stmt|;
name|this
operator|.
name|prefs
operator|=
name|prefs
expr_stmt|;
name|appendKeyGeneratorSettings
argument_list|()
expr_stmt|;
block|}
comment|/**      * Store changes to table preferences. This method is called when the user clicks Ok.      *      */
annotation|@
name|Override
DECL|method|storeSettings ()
specifier|public
name|void
name|storeSettings
parameter_list|()
block|{
comment|// Set the default value:
name|Globals
operator|.
name|prefs
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|DEFAULT_LABEL_PATTERN
argument_list|,
name|defaultPat
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|WARN_BEFORE_OVERWRITING_KEY
argument_list|,
name|warnBeforeOverwriting
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|AVOID_OVERWRITING_KEY
argument_list|,
name|dontOverwrite
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|prefs
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|KEY_PATTERN_REGEX
argument_list|,
name|keyPatternRegex
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|prefs
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|KEY_PATTERN_REPLACEMENT
argument_list|,
name|keyPatternReplacement
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|GENERATE_KEYS_AFTER_INSPECTION
argument_list|,
name|autoGenerateOnImport
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|GENERATE_KEYS_BEFORE_SAVING
argument_list|,
name|generateOnSave
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|alwaysAddLetter
operator|.
name|isSelected
argument_list|()
condition|)
block|{
name|Globals
operator|.
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|KEY_GEN_ALWAYS_ADD_LETTER
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|letterStartA
operator|.
name|isSelected
argument_list|()
condition|)
block|{
name|Globals
operator|.
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|KEY_GEN_FIRST_LETTER_A
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|KEY_GEN_ALWAYS_ADD_LETTER
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|Globals
operator|.
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|KEY_GEN_FIRST_LETTER_A
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|KEY_GEN_ALWAYS_ADD_LETTER
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
name|LabelPatternUtil
operator|.
name|updateDefaultPattern
argument_list|()
expr_stmt|;
comment|// fetch entries from GUI
name|GlobalLabelPattern
name|keypatterns
init|=
name|getLabelPatternAsGlobalLabelPattern
argument_list|()
decl_stmt|;
comment|// store new patterns globally
name|prefs
operator|.
name|putKeyPattern
argument_list|(
name|keypatterns
argument_list|)
expr_stmt|;
block|}
DECL|method|appendKeyGeneratorSettings ()
specifier|private
name|void
name|appendKeyGeneratorSettings
parameter_list|()
block|{
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
name|letterStartA
argument_list|)
expr_stmt|;
name|bg
operator|.
name|add
argument_list|(
name|letterStartB
argument_list|)
expr_stmt|;
name|bg
operator|.
name|add
argument_list|(
name|alwaysAddLetter
argument_list|)
expr_stmt|;
comment|// Build a panel for checkbox settings:
name|FormLayout
name|layout
init|=
operator|new
name|FormLayout
argument_list|(
literal|"1dlu, 8dlu, left:pref, 8dlu, left:pref"
argument_list|,
literal|""
argument_list|)
decl_stmt|;
name|JPanel
name|pan
init|=
operator|new
name|JPanel
argument_list|()
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
literal|"Key generator settings"
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
name|autoGenerateOnImport
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|letterStartA
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
name|warnBeforeOverwriting
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|letterStartB
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
name|dontOverwrite
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|alwaysAddLetter
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
name|generateOnSave
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
name|Localization
operator|.
name|lang
argument_list|(
literal|"Replace (regular expression)"
argument_list|)
operator|+
literal|':'
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
literal|"by"
argument_list|)
operator|+
literal|':'
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
name|keyPatternRegex
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|keyPatternReplacement
argument_list|)
expr_stmt|;
name|builder
operator|.
name|getPanel
argument_list|()
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
name|con
operator|.
name|gridx
operator|=
literal|1
expr_stmt|;
name|con
operator|.
name|gridy
operator|=
literal|3
expr_stmt|;
name|con
operator|.
name|gridwidth
operator|=
name|GridBagConstraints
operator|.
name|REMAINDER
expr_stmt|;
name|con
operator|.
name|weightx
operator|=
literal|1
expr_stmt|;
name|con
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|BOTH
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|builder
operator|.
name|getPanel
argument_list|()
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|builder
operator|.
name|getPanel
argument_list|()
argument_list|)
expr_stmt|;
name|dontOverwrite
operator|.
name|addChangeListener
argument_list|(
name|e
lambda|->
comment|// Warning before overwriting is only relevant if overwriting can happen:
name|warnBeforeOverwriting
operator|.
name|setEnabled
argument_list|(
operator|!
name|dontOverwrite
operator|.
name|isSelected
argument_list|()
argument_list|)
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
DECL|method|setValues ()
specifier|public
name|void
name|setValues
parameter_list|()
block|{
name|super
operator|.
name|setValues
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getKeyPattern
argument_list|()
argument_list|)
expr_stmt|;
name|defaultPat
operator|.
name|setText
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|DEFAULT_LABEL_PATTERN
argument_list|)
argument_list|)
expr_stmt|;
name|dontOverwrite
operator|.
name|setSelected
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|AVOID_OVERWRITING_KEY
argument_list|)
argument_list|)
expr_stmt|;
name|generateOnSave
operator|.
name|setSelected
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|GENERATE_KEYS_BEFORE_SAVING
argument_list|)
argument_list|)
expr_stmt|;
name|autoGenerateOnImport
operator|.
name|setSelected
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|GENERATE_KEYS_AFTER_INSPECTION
argument_list|)
argument_list|)
expr_stmt|;
name|warnBeforeOverwriting
operator|.
name|setSelected
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|WARN_BEFORE_OVERWRITING_KEY
argument_list|)
argument_list|)
expr_stmt|;
name|boolean
name|prefAlwaysAddLetter
init|=
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|KEY_GEN_ALWAYS_ADD_LETTER
argument_list|)
decl_stmt|;
name|boolean
name|firstLetterA
init|=
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|KEY_GEN_FIRST_LETTER_A
argument_list|)
decl_stmt|;
if|if
condition|(
name|prefAlwaysAddLetter
condition|)
block|{
name|this
operator|.
name|alwaysAddLetter
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
name|firstLetterA
condition|)
block|{
name|this
operator|.
name|letterStartA
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|this
operator|.
name|letterStartB
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
comment|// Warning before overwriting is only relevant if overwriting can happen:
name|warnBeforeOverwriting
operator|.
name|setEnabled
argument_list|(
operator|!
name|dontOverwrite
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|keyPatternRegex
operator|.
name|setText
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|KEY_PATTERN_REGEX
argument_list|)
argument_list|)
expr_stmt|;
name|keyPatternReplacement
operator|.
name|setText
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|KEY_PATTERN_REPLACEMENT
argument_list|)
argument_list|)
expr_stmt|;
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
literal|"BibTeX key generator"
argument_list|)
return|;
block|}
block|}
end_class

end_unit

