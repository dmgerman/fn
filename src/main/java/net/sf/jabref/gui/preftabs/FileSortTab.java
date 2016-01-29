begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2013-2015 JabRef contributors.  This program is free software; you can redistribute it and/or modify  it under the terms of the GNU General Public License as published by  the Free Software Foundation; either version 2 of the License, or  (at your option) any later version.   This program is distributed in the hope that it will be useful,  but WITHOUT ANY WARRANTY; without even the implied warranty of  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  GNU General Public License for more details.   You should have received a copy of the GNU General Public License along  with this program; if not, write to the Free Software Foundation, Inc.,  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
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
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collections
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
name|JTextField
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
name|gui
operator|.
name|InternalBibtexFields
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
name|model
operator|.
name|entry
operator|.
name|BibEntry
import|;
end_import

begin_comment
comment|/**  * Preference tab for file sorting options.  */
end_comment

begin_class
DECL|class|FileSortTab
class|class
name|FileSortTab
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
DECL|field|exportInOriginalOrder
specifier|private
specifier|final
name|JRadioButton
name|exportInOriginalOrder
decl_stmt|;
DECL|field|exportInTableOrder
specifier|private
specifier|final
name|JRadioButton
name|exportInTableOrder
decl_stmt|;
DECL|field|exportInSpecifiedOrder
specifier|private
specifier|final
name|JRadioButton
name|exportInSpecifiedOrder
decl_stmt|;
DECL|field|exportPriDesc
specifier|private
specifier|final
name|JCheckBox
name|exportPriDesc
decl_stmt|;
DECL|field|exportSecDesc
specifier|private
specifier|final
name|JCheckBox
name|exportSecDesc
decl_stmt|;
DECL|field|exportTerDesc
specifier|private
specifier|final
name|JCheckBox
name|exportTerDesc
decl_stmt|;
DECL|field|exportPriField
specifier|private
specifier|final
name|JTextField
name|exportPriField
decl_stmt|;
DECL|field|exportSecField
specifier|private
specifier|final
name|JTextField
name|exportSecField
decl_stmt|;
DECL|field|exportTerField
specifier|private
specifier|final
name|JTextField
name|exportTerField
decl_stmt|;
DECL|field|exportPriSort
specifier|private
specifier|final
name|JComboBox
argument_list|<
name|String
argument_list|>
name|exportPriSort
decl_stmt|;
DECL|field|exportSecSort
specifier|private
specifier|final
name|JComboBox
argument_list|<
name|String
argument_list|>
name|exportSecSort
decl_stmt|;
DECL|field|exportTerSort
specifier|private
specifier|final
name|JComboBox
argument_list|<
name|String
argument_list|>
name|exportTerSort
decl_stmt|;
DECL|method|FileSortTab (JabRefPreferences prefs)
specifier|public
name|FileSortTab
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
name|FormLayout
name|layout
init|=
operator|new
name|FormLayout
argument_list|(
literal|"4dlu, left:pref, 4dlu, fill:pref"
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
name|leadingColumnOffset
argument_list|(
literal|1
argument_list|)
expr_stmt|;
comment|// EXPORT SORT ORDER
comment|// create Components
name|exportInOriginalOrder
operator|=
operator|new
name|JRadioButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Export entries in their original order"
argument_list|)
argument_list|)
expr_stmt|;
name|exportInTableOrder
operator|=
operator|new
name|JRadioButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Export in current table sort order"
argument_list|)
argument_list|)
expr_stmt|;
name|exportInSpecifiedOrder
operator|=
operator|new
name|JRadioButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Export entries ordered as specified"
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
name|exportInOriginalOrder
argument_list|)
expr_stmt|;
name|buttonGroup
operator|.
name|add
argument_list|(
name|exportInTableOrder
argument_list|)
expr_stmt|;
name|buttonGroup
operator|.
name|add
argument_list|(
name|exportInSpecifiedOrder
argument_list|)
expr_stmt|;
name|ActionListener
name|listener
init|=
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
name|boolean
name|selected
init|=
name|e
operator|.
name|getSource
argument_list|()
operator|==
name|exportInSpecifiedOrder
decl_stmt|;
name|exportPriSort
operator|.
name|setEnabled
argument_list|(
name|selected
argument_list|)
expr_stmt|;
name|exportPriField
operator|.
name|setEnabled
argument_list|(
name|selected
argument_list|)
expr_stmt|;
name|exportPriDesc
operator|.
name|setEnabled
argument_list|(
name|selected
argument_list|)
expr_stmt|;
name|exportSecSort
operator|.
name|setEnabled
argument_list|(
name|selected
argument_list|)
expr_stmt|;
name|exportSecField
operator|.
name|setEnabled
argument_list|(
name|selected
argument_list|)
expr_stmt|;
name|exportSecDesc
operator|.
name|setEnabled
argument_list|(
name|selected
argument_list|)
expr_stmt|;
name|exportTerSort
operator|.
name|setEnabled
argument_list|(
name|selected
argument_list|)
expr_stmt|;
name|exportTerField
operator|.
name|setEnabled
argument_list|(
name|selected
argument_list|)
expr_stmt|;
name|exportTerDesc
operator|.
name|setEnabled
argument_list|(
name|selected
argument_list|)
expr_stmt|;
block|}
block|}
decl_stmt|;
name|exportInOriginalOrder
operator|.
name|addActionListener
argument_list|(
name|listener
argument_list|)
expr_stmt|;
name|exportInTableOrder
operator|.
name|addActionListener
argument_list|(
name|listener
argument_list|)
expr_stmt|;
name|exportInSpecifiedOrder
operator|.
name|addActionListener
argument_list|(
name|listener
argument_list|)
expr_stmt|;
name|ArrayList
argument_list|<
name|String
argument_list|>
name|fieldNames
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|(
name|InternalBibtexFields
operator|.
name|getAllFieldNames
argument_list|()
argument_list|)
decl_stmt|;
name|fieldNames
operator|.
name|add
argument_list|(
name|BibEntry
operator|.
name|KEY_FIELD
argument_list|)
expr_stmt|;
name|Collections
operator|.
name|sort
argument_list|(
name|fieldNames
argument_list|)
expr_stmt|;
name|String
index|[]
name|allPlusKey
init|=
name|fieldNames
operator|.
name|toArray
argument_list|(
operator|new
name|String
index|[
name|fieldNames
operator|.
name|size
argument_list|()
index|]
argument_list|)
decl_stmt|;
name|exportPriSort
operator|=
operator|new
name|JComboBox
argument_list|<>
argument_list|(
name|allPlusKey
argument_list|)
expr_stmt|;
name|exportSecSort
operator|=
operator|new
name|JComboBox
argument_list|<>
argument_list|(
name|allPlusKey
argument_list|)
expr_stmt|;
name|exportTerSort
operator|=
operator|new
name|JComboBox
argument_list|<>
argument_list|(
name|allPlusKey
argument_list|)
expr_stmt|;
name|exportPriField
operator|=
operator|new
name|JTextField
argument_list|(
literal|10
argument_list|)
expr_stmt|;
name|exportSecField
operator|=
operator|new
name|JTextField
argument_list|(
literal|10
argument_list|)
expr_stmt|;
name|exportTerField
operator|=
operator|new
name|JTextField
argument_list|(
literal|10
argument_list|)
expr_stmt|;
name|exportPriSort
operator|.
name|insertItemAt
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"<select>"
argument_list|)
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|exportSecSort
operator|.
name|insertItemAt
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"<select>"
argument_list|)
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|exportTerSort
operator|.
name|insertItemAt
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"<select>"
argument_list|)
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|exportPriSort
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
if|if
condition|(
name|exportPriSort
operator|.
name|getSelectedIndex
argument_list|()
operator|>
literal|0
condition|)
block|{
name|exportPriField
operator|.
name|setText
argument_list|(
name|exportPriSort
operator|.
name|getSelectedItem
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|exportPriSort
operator|.
name|setSelectedIndex
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
block|}
block|}
argument_list|)
expr_stmt|;
name|exportSecSort
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
if|if
condition|(
name|exportSecSort
operator|.
name|getSelectedIndex
argument_list|()
operator|>
literal|0
condition|)
block|{
name|exportSecField
operator|.
name|setText
argument_list|(
name|exportSecSort
operator|.
name|getSelectedItem
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|exportSecSort
operator|.
name|setSelectedIndex
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
block|}
block|}
argument_list|)
expr_stmt|;
name|exportTerSort
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
if|if
condition|(
name|exportTerSort
operator|.
name|getSelectedIndex
argument_list|()
operator|>
literal|0
condition|)
block|{
name|exportTerField
operator|.
name|setText
argument_list|(
name|exportTerSort
operator|.
name|getSelectedItem
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|exportTerSort
operator|.
name|setSelectedIndex
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
block|}
block|}
argument_list|)
expr_stmt|;
name|exportPriDesc
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Descending"
argument_list|)
argument_list|)
expr_stmt|;
name|exportSecDesc
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Descending"
argument_list|)
argument_list|)
expr_stmt|;
name|exportTerDesc
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Descending"
argument_list|)
argument_list|)
expr_stmt|;
comment|// create GUI
name|JLabel
name|lab
decl_stmt|;
name|builder
operator|.
name|appendSeparator
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Export sort order"
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|exportInOriginalOrder
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
name|exportInTableOrder
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
name|exportInSpecifiedOrder
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
comment|// Create a new panel with its own FormLayout for these items:
name|FormLayout
name|layout2
init|=
operator|new
name|FormLayout
argument_list|(
literal|"right:pref, 8dlu, fill:pref, 4dlu, fill:60dlu, 4dlu, left:pref"
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
name|lab
operator|=
operator|new
name|JLabel
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Primary sort criterion"
argument_list|)
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
name|exportPriSort
argument_list|)
expr_stmt|;
name|builder2
operator|.
name|append
argument_list|(
name|exportPriField
argument_list|)
expr_stmt|;
name|builder2
operator|.
name|append
argument_list|(
name|exportPriDesc
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
name|Localization
operator|.
name|lang
argument_list|(
literal|"Secondary sort criterion"
argument_list|)
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
name|exportSecSort
argument_list|)
expr_stmt|;
name|builder2
operator|.
name|append
argument_list|(
name|exportSecField
argument_list|)
expr_stmt|;
name|builder2
operator|.
name|append
argument_list|(
name|exportSecDesc
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
name|Localization
operator|.
name|lang
argument_list|(
literal|"Tertiary sort criterion"
argument_list|)
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
name|exportTerSort
argument_list|)
expr_stmt|;
name|builder2
operator|.
name|append
argument_list|(
name|exportTerField
argument_list|)
expr_stmt|;
name|builder2
operator|.
name|append
argument_list|(
name|exportTerDesc
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
comment|// COMBINE EVERYTHING
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
if|if
condition|(
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|EXPORT_IN_ORIGINAL_ORDER
argument_list|)
condition|)
block|{
name|exportInOriginalOrder
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
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|EXPORT_IN_SPECIFIED_ORDER
argument_list|)
condition|)
block|{
name|exportInSpecifiedOrder
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|exportInTableOrder
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
name|boolean
name|selected
init|=
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|EXPORT_IN_SPECIFIED_ORDER
argument_list|)
decl_stmt|;
name|exportPriSort
operator|.
name|setEnabled
argument_list|(
name|selected
argument_list|)
expr_stmt|;
name|exportPriField
operator|.
name|setEnabled
argument_list|(
name|selected
argument_list|)
expr_stmt|;
name|exportPriDesc
operator|.
name|setEnabled
argument_list|(
name|selected
argument_list|)
expr_stmt|;
name|exportSecSort
operator|.
name|setEnabled
argument_list|(
name|selected
argument_list|)
expr_stmt|;
name|exportSecField
operator|.
name|setEnabled
argument_list|(
name|selected
argument_list|)
expr_stmt|;
name|exportSecDesc
operator|.
name|setEnabled
argument_list|(
name|selected
argument_list|)
expr_stmt|;
name|exportTerSort
operator|.
name|setEnabled
argument_list|(
name|selected
argument_list|)
expr_stmt|;
name|exportTerField
operator|.
name|setEnabled
argument_list|(
name|selected
argument_list|)
expr_stmt|;
name|exportTerDesc
operator|.
name|setEnabled
argument_list|(
name|selected
argument_list|)
expr_stmt|;
name|exportPriField
operator|.
name|setText
argument_list|(
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|EXPORT_PRIMARY_SORT_FIELD
argument_list|)
argument_list|)
expr_stmt|;
name|exportSecField
operator|.
name|setText
argument_list|(
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|EXPORT_SECONDARY_SORT_FIELD
argument_list|)
argument_list|)
expr_stmt|;
name|exportTerField
operator|.
name|setText
argument_list|(
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|EXPORT_TERTIARY_SORT_FIELD
argument_list|)
argument_list|)
expr_stmt|;
name|exportPriSort
operator|.
name|setSelectedIndex
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|exportSecSort
operator|.
name|setSelectedIndex
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|exportTerSort
operator|.
name|setSelectedIndex
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|exportPriDesc
operator|.
name|setSelected
argument_list|(
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|EXPORT_PRIMARY_SORT_DESCENDING
argument_list|)
argument_list|)
expr_stmt|;
name|exportSecDesc
operator|.
name|setSelected
argument_list|(
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|EXPORT_SECONDARY_SORT_DESCENDING
argument_list|)
argument_list|)
expr_stmt|;
name|exportTerDesc
operator|.
name|setSelected
argument_list|(
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|EXPORT_TERTIARY_SORT_DESCENDING
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
name|EXPORT_IN_ORIGINAL_ORDER
argument_list|,
name|exportInOriginalOrder
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
name|EXPORT_IN_SPECIFIED_ORDER
argument_list|,
name|exportInSpecifiedOrder
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
name|EXPORT_PRIMARY_SORT_DESCENDING
argument_list|,
name|exportPriDesc
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
name|EXPORT_SECONDARY_SORT_DESCENDING
argument_list|,
name|exportSecDesc
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
name|EXPORT_TERTIARY_SORT_DESCENDING
argument_list|,
name|exportTerDesc
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
name|EXPORT_PRIMARY_SORT_FIELD
argument_list|,
name|exportPriField
operator|.
name|getText
argument_list|()
operator|.
name|toLowerCase
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
name|EXPORT_SECONDARY_SORT_FIELD
argument_list|,
name|exportSecField
operator|.
name|getText
argument_list|()
operator|.
name|toLowerCase
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
name|EXPORT_TERTIARY_SORT_FIELD
argument_list|,
name|exportTerField
operator|.
name|getText
argument_list|()
operator|.
name|toLowerCase
argument_list|()
operator|.
name|trim
argument_list|()
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
literal|"Export Sorting"
argument_list|)
return|;
block|}
block|}
end_class

end_unit

