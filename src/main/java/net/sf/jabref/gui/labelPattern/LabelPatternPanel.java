begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.gui.labelPattern
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|labelPattern
package|;
end_package

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Container
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
name|Font
import|;
end_import

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
name|java
operator|.
name|awt
operator|.
name|GridBagLayout
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
name|awt
operator|.
name|event
operator|.
name|ActionEvent
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
name|util
operator|.
name|HashMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
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
name|JScrollPane
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
name|IconTheme
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
name|labelPattern
operator|.
name|AbstractLabelPattern
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
name|labelPattern
operator|.
name|DatabaseLabelPattern
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
name|labelPattern
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|bibtex
operator|.
name|EntryTypes
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
name|model
operator|.
name|entry
operator|.
name|EntryType
import|;
end_import

begin_class
DECL|class|LabelPatternPanel
specifier|public
class|class
name|LabelPatternPanel
extends|extends
name|JPanel
block|{
comment|// used by both LabelPatternPanel and TabLabelPAttern
DECL|field|gbl
specifier|protected
specifier|final
name|GridBagLayout
name|gbl
init|=
operator|new
name|GridBagLayout
argument_list|()
decl_stmt|;
DECL|field|con
specifier|protected
specifier|final
name|GridBagConstraints
name|con
init|=
operator|new
name|GridBagConstraints
argument_list|()
decl_stmt|;
DECL|field|help
specifier|private
specifier|final
name|HelpAction
name|help
decl_stmt|;
comment|// default pattern
DECL|field|defaultPat
specifier|protected
specifier|final
name|JTextField
name|defaultPat
init|=
operator|new
name|JTextField
argument_list|()
decl_stmt|;
comment|// one field for each type
DECL|field|textFields
specifier|private
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|JTextField
argument_list|>
name|textFields
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
DECL|method|LabelPatternPanel (HelpDialog helpDiag)
specifier|public
name|LabelPatternPanel
parameter_list|(
name|HelpDialog
name|helpDiag
parameter_list|)
block|{
name|help
operator|=
operator|new
name|HelpAction
argument_list|(
name|helpDiag
argument_list|,
name|GUIGlobals
operator|.
name|labelPatternHelp
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Help on key patterns"
argument_list|)
argument_list|)
expr_stmt|;
name|buildGUI
argument_list|()
expr_stmt|;
block|}
DECL|method|buildGUI ()
specifier|private
name|void
name|buildGUI
parameter_list|()
block|{
name|JPanel
name|pan
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|JScrollPane
name|sp
init|=
operator|new
name|JScrollPane
argument_list|(
name|pan
argument_list|)
decl_stmt|;
name|sp
operator|.
name|setPreferredSize
argument_list|(
operator|new
name|Dimension
argument_list|(
literal|100
argument_list|,
literal|100
argument_list|)
argument_list|)
expr_stmt|;
name|sp
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createEmptyBorder
argument_list|()
argument_list|)
expr_stmt|;
name|pan
operator|.
name|setLayout
argument_list|(
name|gbl
argument_list|)
expr_stmt|;
name|setLayout
argument_list|(
name|gbl
argument_list|)
expr_stmt|;
comment|// The header - can be removed
name|JLabel
name|lblEntryType
init|=
operator|new
name|JLabel
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Entry type"
argument_list|)
argument_list|)
decl_stmt|;
name|Font
name|f
init|=
operator|new
name|Font
argument_list|(
literal|"plain"
argument_list|,
name|Font
operator|.
name|BOLD
argument_list|,
literal|12
argument_list|)
decl_stmt|;
name|lblEntryType
operator|.
name|setFont
argument_list|(
name|f
argument_list|)
expr_stmt|;
name|con
operator|.
name|gridx
operator|=
literal|0
expr_stmt|;
name|con
operator|.
name|gridy
operator|=
literal|0
expr_stmt|;
name|con
operator|.
name|gridwidth
operator|=
literal|1
expr_stmt|;
name|con
operator|.
name|gridheight
operator|=
literal|1
expr_stmt|;
name|con
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|VERTICAL
expr_stmt|;
name|con
operator|.
name|anchor
operator|=
name|GridBagConstraints
operator|.
name|WEST
expr_stmt|;
name|con
operator|.
name|insets
operator|=
operator|new
name|Insets
argument_list|(
literal|5
argument_list|,
literal|5
argument_list|,
literal|10
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|lblEntryType
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|pan
operator|.
name|add
argument_list|(
name|lblEntryType
argument_list|)
expr_stmt|;
name|JLabel
name|lblKeyPattern
init|=
operator|new
name|JLabel
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Key pattern"
argument_list|)
argument_list|)
decl_stmt|;
name|lblKeyPattern
operator|.
name|setFont
argument_list|(
name|f
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
literal|0
expr_stmt|;
comment|//con.gridwidth = 2;
name|con
operator|.
name|gridheight
operator|=
literal|1
expr_stmt|;
name|con
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|HORIZONTAL
expr_stmt|;
name|con
operator|.
name|anchor
operator|=
name|GridBagConstraints
operator|.
name|WEST
expr_stmt|;
name|con
operator|.
name|insets
operator|=
operator|new
name|Insets
argument_list|(
literal|5
argument_list|,
literal|5
argument_list|,
literal|10
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|lblKeyPattern
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|pan
operator|.
name|add
argument_list|(
name|lblKeyPattern
argument_list|)
expr_stmt|;
name|con
operator|.
name|gridy
operator|=
literal|1
expr_stmt|;
name|con
operator|.
name|gridx
operator|=
literal|0
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
literal|"Default pattern"
argument_list|)
argument_list|)
decl_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|lab
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|pan
operator|.
name|add
argument_list|(
name|lab
argument_list|)
expr_stmt|;
name|con
operator|.
name|gridx
operator|=
literal|1
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|defaultPat
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|pan
operator|.
name|add
argument_list|(
name|defaultPat
argument_list|)
expr_stmt|;
name|con
operator|.
name|insets
operator|=
operator|new
name|Insets
argument_list|(
literal|5
argument_list|,
literal|5
argument_list|,
literal|10
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|JButton
name|btnDefault
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Default"
argument_list|)
argument_list|)
decl_stmt|;
name|btnDefault
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
name|defaultPat
operator|.
name|setText
argument_list|(
operator|(
name|String
operator|)
name|Globals
operator|.
name|prefs
operator|.
name|defaults
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|DEFAULT_LABEL_PATTERN
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|con
operator|.
name|gridx
operator|=
literal|2
expr_stmt|;
name|int
name|y
init|=
literal|2
decl_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|btnDefault
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|pan
operator|.
name|add
argument_list|(
name|btnDefault
argument_list|)
expr_stmt|;
for|for
control|(
name|EntryType
name|type
range|:
name|EntryTypes
operator|.
name|getAllValues
argument_list|()
control|)
block|{
name|textFields
operator|.
name|put
argument_list|(
name|type
operator|.
name|getName
argument_list|()
operator|.
name|toLowerCase
argument_list|()
argument_list|,
name|addEntryType
argument_list|(
name|pan
argument_list|,
name|type
argument_list|,
name|y
argument_list|)
argument_list|)
expr_stmt|;
name|y
operator|++
expr_stmt|;
block|}
name|con
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|BOTH
expr_stmt|;
name|con
operator|.
name|gridx
operator|=
literal|0
expr_stmt|;
name|con
operator|.
name|gridy
operator|=
literal|1
expr_stmt|;
name|con
operator|.
name|gridwidth
operator|=
literal|3
expr_stmt|;
name|con
operator|.
name|weightx
operator|=
literal|1
expr_stmt|;
name|con
operator|.
name|weighty
operator|=
literal|1
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|sp
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|sp
argument_list|)
expr_stmt|;
comment|// A help button
name|con
operator|.
name|gridwidth
operator|=
literal|1
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
literal|2
expr_stmt|;
name|con
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|HORIZONTAL
expr_stmt|;
comment|//
name|con
operator|.
name|weightx
operator|=
literal|0
expr_stmt|;
name|con
operator|.
name|weighty
operator|=
literal|0
expr_stmt|;
name|con
operator|.
name|anchor
operator|=
name|GridBagConstraints
operator|.
name|SOUTHEAST
expr_stmt|;
name|con
operator|.
name|insets
operator|=
operator|new
name|Insets
argument_list|(
literal|0
argument_list|,
literal|5
argument_list|,
literal|0
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|JButton
name|hlb
init|=
operator|new
name|JButton
argument_list|(
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|HELP
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
decl_stmt|;
name|hlb
operator|.
name|setToolTipText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Help on key patterns"
argument_list|)
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|hlb
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|hlb
argument_list|)
expr_stmt|;
name|hlb
operator|.
name|addActionListener
argument_list|(
name|help
argument_list|)
expr_stmt|;
comment|// And finally a button to reset everything
name|JButton
name|btnDefaultAll
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Reset all"
argument_list|)
argument_list|)
decl_stmt|;
name|con
operator|.
name|gridx
operator|=
literal|2
expr_stmt|;
name|con
operator|.
name|gridy
operator|=
literal|2
expr_stmt|;
comment|//con.fill = GridBagConstraints.BOTH;
name|con
operator|.
name|weightx
operator|=
literal|1
expr_stmt|;
name|con
operator|.
name|weighty
operator|=
literal|0
expr_stmt|;
name|con
operator|.
name|anchor
operator|=
name|GridBagConstraints
operator|.
name|SOUTHEAST
expr_stmt|;
name|con
operator|.
name|insets
operator|=
operator|new
name|Insets
argument_list|(
literal|20
argument_list|,
literal|5
argument_list|,
literal|0
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|btnDefaultAll
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|btnDefaultAll
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
name|arg0
parameter_list|)
block|{
comment|// reset all fields
for|for
control|(
name|String
name|s
range|:
name|textFields
operator|.
name|keySet
argument_list|()
control|)
block|{
name|JTextField
name|tf
init|=
name|textFields
operator|.
name|get
argument_list|(
name|s
argument_list|)
decl_stmt|;
name|tf
operator|.
name|setText
argument_list|(
literal|""
argument_list|)
expr_stmt|;
block|}
comment|// also reset the default pattern
name|defaultPat
operator|.
name|setText
argument_list|(
operator|(
name|String
operator|)
name|Globals
operator|.
name|prefs
operator|.
name|defaults
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|DEFAULT_LABEL_PATTERN
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|btnDefaultAll
argument_list|)
expr_stmt|;
block|}
DECL|method|addEntryType (Container c, EntryType type, int y)
specifier|private
name|JTextField
name|addEntryType
parameter_list|(
name|Container
name|c
parameter_list|,
name|EntryType
name|type
parameter_list|,
name|int
name|y
parameter_list|)
block|{
name|JLabel
name|lab
init|=
operator|new
name|JLabel
argument_list|(
name|type
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
name|con
operator|.
name|gridx
operator|=
literal|0
expr_stmt|;
name|con
operator|.
name|gridy
operator|=
name|y
expr_stmt|;
name|con
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|BOTH
expr_stmt|;
name|con
operator|.
name|weightx
operator|=
literal|0
expr_stmt|;
name|con
operator|.
name|weighty
operator|=
literal|0
expr_stmt|;
name|con
operator|.
name|anchor
operator|=
name|GridBagConstraints
operator|.
name|WEST
expr_stmt|;
name|con
operator|.
name|insets
operator|=
operator|new
name|Insets
argument_list|(
literal|0
argument_list|,
literal|5
argument_list|,
literal|0
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|lab
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|c
operator|.
name|add
argument_list|(
name|lab
argument_list|)
expr_stmt|;
name|JTextField
name|tf
init|=
operator|new
name|JTextField
argument_list|()
decl_stmt|;
comment|//_keypatterns.getValue(name).get(0).toString());
name|tf
operator|.
name|setColumns
argument_list|(
literal|15
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
name|fill
operator|=
name|GridBagConstraints
operator|.
name|HORIZONTAL
expr_stmt|;
name|con
operator|.
name|weightx
operator|=
literal|1
expr_stmt|;
name|con
operator|.
name|weighty
operator|=
literal|0
expr_stmt|;
name|con
operator|.
name|anchor
operator|=
name|GridBagConstraints
operator|.
name|CENTER
expr_stmt|;
name|con
operator|.
name|insets
operator|=
operator|new
name|Insets
argument_list|(
literal|0
argument_list|,
literal|5
argument_list|,
literal|0
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|tf
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|c
operator|.
name|add
argument_list|(
name|tf
argument_list|)
expr_stmt|;
name|JButton
name|but
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Default"
argument_list|)
argument_list|)
decl_stmt|;
name|con
operator|.
name|gridx
operator|=
literal|2
expr_stmt|;
name|con
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|BOTH
expr_stmt|;
name|con
operator|.
name|weightx
operator|=
literal|0
expr_stmt|;
name|con
operator|.
name|weighty
operator|=
literal|0
expr_stmt|;
name|con
operator|.
name|anchor
operator|=
name|GridBagConstraints
operator|.
name|CENTER
expr_stmt|;
name|con
operator|.
name|insets
operator|=
operator|new
name|Insets
argument_list|(
literal|0
argument_list|,
literal|5
argument_list|,
literal|0
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|but
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|but
operator|.
name|setActionCommand
argument_list|(
name|type
operator|.
name|getName
argument_list|()
operator|.
name|toLowerCase
argument_list|()
argument_list|)
expr_stmt|;
name|but
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
name|e
parameter_list|)
block|{
name|JTextField
name|tField
init|=
name|textFields
operator|.
name|get
argument_list|(
name|e
operator|.
name|getActionCommand
argument_list|()
argument_list|)
decl_stmt|;
name|tField
operator|.
name|setText
argument_list|(
literal|""
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|c
operator|.
name|add
argument_list|(
name|but
argument_list|)
expr_stmt|;
return|return
name|tf
return|;
block|}
comment|/**      * fill the given LabelPattern by values generated from the text fields      */
DECL|method|fillPatternUsingPanelData (AbstractLabelPattern keypatterns)
specifier|private
name|void
name|fillPatternUsingPanelData
parameter_list|(
name|AbstractLabelPattern
name|keypatterns
parameter_list|)
block|{
comment|// each entry type
for|for
control|(
name|String
name|s1
range|:
name|textFields
operator|.
name|keySet
argument_list|()
control|)
block|{
name|String
name|text
init|=
name|textFields
operator|.
name|get
argument_list|(
name|s1
argument_list|)
operator|.
name|getText
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
literal|""
operator|.
name|equals
argument_list|(
name|text
operator|.
name|trim
argument_list|()
argument_list|)
condition|)
block|{
name|keypatterns
operator|.
name|addLabelPattern
argument_list|(
name|s1
argument_list|,
name|text
argument_list|)
expr_stmt|;
block|}
block|}
comment|// default value
name|String
name|text
init|=
name|defaultPat
operator|.
name|getText
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
literal|""
operator|.
name|equals
argument_list|(
name|text
operator|.
name|trim
argument_list|()
argument_list|)
condition|)
block|{
comment|// we do not trim the value at the assignment to enable users to have spaces at the beginning and at the end of the pattern
name|keypatterns
operator|.
name|setDefaultValue
argument_list|(
name|text
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|getLabelPatternAsGlobalLabelPattern ()
specifier|protected
name|GlobalLabelPattern
name|getLabelPatternAsGlobalLabelPattern
parameter_list|()
block|{
name|GlobalLabelPattern
name|res
init|=
operator|new
name|GlobalLabelPattern
argument_list|()
decl_stmt|;
name|fillPatternUsingPanelData
argument_list|(
name|res
argument_list|)
expr_stmt|;
return|return
name|res
return|;
block|}
DECL|method|getLabelPatternAsDatabaseLabelPattern ()
specifier|public
name|DatabaseLabelPattern
name|getLabelPatternAsDatabaseLabelPattern
parameter_list|()
block|{
name|DatabaseLabelPattern
name|res
init|=
operator|new
name|DatabaseLabelPattern
argument_list|()
decl_stmt|;
name|fillPatternUsingPanelData
argument_list|(
name|res
argument_list|)
expr_stmt|;
return|return
name|res
return|;
block|}
comment|/**      * Fills the current values to the text fields      *      * @param keypatterns the LabelPattern to use as initial value      */
DECL|method|setValues (AbstractLabelPattern keypatterns)
specifier|public
name|void
name|setValues
parameter_list|(
name|AbstractLabelPattern
name|keypatterns
parameter_list|)
block|{
for|for
control|(
name|String
name|name
range|:
name|textFields
operator|.
name|keySet
argument_list|()
control|)
block|{
name|JTextField
name|tf
init|=
name|textFields
operator|.
name|get
argument_list|(
name|name
argument_list|)
decl_stmt|;
name|setValue
argument_list|(
name|tf
argument_list|,
name|name
argument_list|,
name|keypatterns
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|keypatterns
operator|.
name|getDefaultValue
argument_list|()
operator|==
literal|null
condition|)
block|{
name|defaultPat
operator|.
name|setText
argument_list|(
literal|""
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|defaultPat
operator|.
name|setText
argument_list|(
name|keypatterns
operator|.
name|getDefaultValue
argument_list|()
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|setValue (JTextField tf, String fieldName, AbstractLabelPattern keypatterns)
specifier|private
specifier|static
name|void
name|setValue
parameter_list|(
name|JTextField
name|tf
parameter_list|,
name|String
name|fieldName
parameter_list|,
name|AbstractLabelPattern
name|keypatterns
parameter_list|)
block|{
if|if
condition|(
name|keypatterns
operator|.
name|isDefaultValue
argument_list|(
name|fieldName
argument_list|)
condition|)
block|{
name|tf
operator|.
name|setText
argument_list|(
literal|""
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|tf
operator|.
name|setText
argument_list|(
name|keypatterns
operator|.
name|getValue
argument_list|(
name|fieldName
argument_list|)
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

