begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2012-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.gui
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
package|;
end_package

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Component
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
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Objects
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
name|com
operator|.
name|jgoodies
operator|.
name|forms
operator|.
name|builder
operator|.
name|FormBuilder
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
name|config
operator|.
name|SaveOrderConfig
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

begin_class
DECL|class|SaveOrderConfigDisplay
specifier|public
class|class
name|SaveOrderConfigDisplay
block|{
DECL|field|panel
specifier|private
name|JPanel
name|panel
decl_stmt|;
DECL|field|savePriSort
specifier|private
name|JComboBox
argument_list|<
name|String
argument_list|>
name|savePriSort
decl_stmt|;
DECL|field|saveSecSort
specifier|private
name|JComboBox
argument_list|<
name|String
argument_list|>
name|saveSecSort
decl_stmt|;
DECL|field|saveTerSort
specifier|private
name|JComboBox
argument_list|<
name|String
argument_list|>
name|saveTerSort
decl_stmt|;
DECL|field|savePriField
specifier|private
name|JTextField
name|savePriField
decl_stmt|;
DECL|field|saveSecField
specifier|private
name|JTextField
name|saveSecField
decl_stmt|;
DECL|field|saveTerField
specifier|private
name|JTextField
name|saveTerField
decl_stmt|;
DECL|field|savePriDesc
specifier|private
name|JCheckBox
name|savePriDesc
decl_stmt|;
DECL|field|saveSecDesc
specifier|private
name|JCheckBox
name|saveSecDesc
decl_stmt|;
DECL|field|saveTerDesc
specifier|private
name|JCheckBox
name|saveTerDesc
decl_stmt|;
DECL|method|SaveOrderConfigDisplay ()
specifier|public
name|SaveOrderConfigDisplay
parameter_list|()
block|{
name|init
argument_list|()
expr_stmt|;
block|}
DECL|method|init ()
specifier|private
name|void
name|init
parameter_list|()
block|{
name|List
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
name|savePriSort
operator|=
operator|new
name|JComboBox
argument_list|<>
argument_list|(
name|allPlusKey
argument_list|)
expr_stmt|;
name|saveSecSort
operator|=
operator|new
name|JComboBox
argument_list|<>
argument_list|(
name|allPlusKey
argument_list|)
expr_stmt|;
name|saveTerSort
operator|=
operator|new
name|JComboBox
argument_list|<>
argument_list|(
name|allPlusKey
argument_list|)
expr_stmt|;
name|savePriSort
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
name|saveSecSort
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
name|saveTerSort
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
name|savePriField
operator|=
operator|new
name|JTextField
argument_list|(
literal|10
argument_list|)
expr_stmt|;
name|saveSecField
operator|=
operator|new
name|JTextField
argument_list|(
literal|10
argument_list|)
expr_stmt|;
name|saveTerField
operator|=
operator|new
name|JTextField
argument_list|(
literal|10
argument_list|)
expr_stmt|;
name|savePriSort
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
block|{
if|if
condition|(
name|savePriSort
operator|.
name|getSelectedIndex
argument_list|()
operator|>
literal|0
condition|)
block|{
name|savePriField
operator|.
name|setText
argument_list|(
name|savePriSort
operator|.
name|getSelectedItem
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|savePriSort
operator|.
name|setSelectedIndex
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|saveSecSort
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
block|{
if|if
condition|(
name|saveSecSort
operator|.
name|getSelectedIndex
argument_list|()
operator|>
literal|0
condition|)
block|{
name|saveSecField
operator|.
name|setText
argument_list|(
name|saveSecSort
operator|.
name|getSelectedItem
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|saveSecSort
operator|.
name|setSelectedIndex
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|saveTerSort
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
block|{
if|if
condition|(
name|saveTerSort
operator|.
name|getSelectedIndex
argument_list|()
operator|>
literal|0
condition|)
block|{
name|saveTerField
operator|.
name|setText
argument_list|(
name|saveTerSort
operator|.
name|getSelectedItem
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|saveTerSort
operator|.
name|setSelectedIndex
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|savePriDesc
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
name|saveSecDesc
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
name|saveTerDesc
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
name|FormLayout
name|layout
init|=
operator|new
name|FormLayout
argument_list|(
literal|"right:pref, 8dlu, fill:pref, 4dlu, fill:60dlu, 4dlu, left:pref"
argument_list|,
literal|"pref, 2dlu, pref, 2dlu, pref"
argument_list|)
decl_stmt|;
name|FormBuilder
name|builder
init|=
name|FormBuilder
operator|.
name|create
argument_list|()
operator|.
name|layout
argument_list|(
name|layout
argument_list|)
decl_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Primary sort criterion"
argument_list|)
argument_list|)
operator|.
name|xy
argument_list|(
literal|1
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|savePriSort
argument_list|)
operator|.
name|xy
argument_list|(
literal|3
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|savePriField
argument_list|)
operator|.
name|xy
argument_list|(
literal|5
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|savePriDesc
argument_list|)
operator|.
name|xy
argument_list|(
literal|7
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Secondary sort criterion"
argument_list|)
argument_list|)
operator|.
name|xy
argument_list|(
literal|1
argument_list|,
literal|3
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|saveSecSort
argument_list|)
operator|.
name|xy
argument_list|(
literal|3
argument_list|,
literal|3
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|saveSecField
argument_list|)
operator|.
name|xy
argument_list|(
literal|5
argument_list|,
literal|3
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|saveSecDesc
argument_list|)
operator|.
name|xy
argument_list|(
literal|7
argument_list|,
literal|3
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Tertiary sort criterion"
argument_list|)
argument_list|)
operator|.
name|xy
argument_list|(
literal|1
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|saveTerSort
argument_list|)
operator|.
name|xy
argument_list|(
literal|3
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|saveTerField
argument_list|)
operator|.
name|xy
argument_list|(
literal|5
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|saveTerDesc
argument_list|)
operator|.
name|xy
argument_list|(
literal|7
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|panel
operator|=
name|builder
operator|.
name|build
argument_list|()
expr_stmt|;
block|}
DECL|method|getPanel ()
specifier|public
name|Component
name|getPanel
parameter_list|()
block|{
return|return
name|panel
return|;
block|}
DECL|method|setEnabled (boolean enabled)
specifier|public
name|void
name|setEnabled
parameter_list|(
name|boolean
name|enabled
parameter_list|)
block|{
name|savePriSort
operator|.
name|setEnabled
argument_list|(
name|enabled
argument_list|)
expr_stmt|;
name|savePriField
operator|.
name|setEnabled
argument_list|(
name|enabled
argument_list|)
expr_stmt|;
name|savePriDesc
operator|.
name|setEnabled
argument_list|(
name|enabled
argument_list|)
expr_stmt|;
name|saveSecSort
operator|.
name|setEnabled
argument_list|(
name|enabled
argument_list|)
expr_stmt|;
name|saveSecField
operator|.
name|setEnabled
argument_list|(
name|enabled
argument_list|)
expr_stmt|;
name|saveSecDesc
operator|.
name|setEnabled
argument_list|(
name|enabled
argument_list|)
expr_stmt|;
name|saveTerSort
operator|.
name|setEnabled
argument_list|(
name|enabled
argument_list|)
expr_stmt|;
name|saveTerField
operator|.
name|setEnabled
argument_list|(
name|enabled
argument_list|)
expr_stmt|;
name|saveTerDesc
operator|.
name|setEnabled
argument_list|(
name|enabled
argument_list|)
expr_stmt|;
block|}
DECL|method|setSaveOrderConfig (SaveOrderConfig saveOrderConfig)
specifier|public
name|void
name|setSaveOrderConfig
parameter_list|(
name|SaveOrderConfig
name|saveOrderConfig
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|saveOrderConfig
argument_list|)
expr_stmt|;
name|savePriField
operator|.
name|setText
argument_list|(
name|saveOrderConfig
operator|.
name|sortCriteria
index|[
literal|0
index|]
operator|.
name|field
argument_list|)
expr_stmt|;
name|savePriDesc
operator|.
name|setSelected
argument_list|(
name|saveOrderConfig
operator|.
name|sortCriteria
index|[
literal|0
index|]
operator|.
name|descending
argument_list|)
expr_stmt|;
name|saveSecField
operator|.
name|setText
argument_list|(
name|saveOrderConfig
operator|.
name|sortCriteria
index|[
literal|1
index|]
operator|.
name|field
argument_list|)
expr_stmt|;
name|saveSecDesc
operator|.
name|setSelected
argument_list|(
name|saveOrderConfig
operator|.
name|sortCriteria
index|[
literal|1
index|]
operator|.
name|descending
argument_list|)
expr_stmt|;
name|saveTerField
operator|.
name|setText
argument_list|(
name|saveOrderConfig
operator|.
name|sortCriteria
index|[
literal|2
index|]
operator|.
name|field
argument_list|)
expr_stmt|;
name|saveTerDesc
operator|.
name|setSelected
argument_list|(
name|saveOrderConfig
operator|.
name|sortCriteria
index|[
literal|2
index|]
operator|.
name|descending
argument_list|)
expr_stmt|;
name|savePriSort
operator|.
name|setSelectedIndex
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|saveSecSort
operator|.
name|setSelectedIndex
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|saveTerSort
operator|.
name|setSelectedIndex
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
DECL|method|getSaveOrderConfig ()
specifier|public
name|SaveOrderConfig
name|getSaveOrderConfig
parameter_list|()
block|{
name|SaveOrderConfig
name|saveOrderConfig
init|=
operator|new
name|SaveOrderConfig
argument_list|()
decl_stmt|;
name|saveOrderConfig
operator|.
name|sortCriteria
index|[
literal|0
index|]
operator|.
name|field
operator|=
name|savePriField
operator|.
name|getText
argument_list|()
operator|.
name|toLowerCase
argument_list|()
operator|.
name|trim
argument_list|()
expr_stmt|;
name|saveOrderConfig
operator|.
name|sortCriteria
index|[
literal|0
index|]
operator|.
name|descending
operator|=
name|savePriDesc
operator|.
name|isSelected
argument_list|()
expr_stmt|;
name|saveOrderConfig
operator|.
name|sortCriteria
index|[
literal|1
index|]
operator|.
name|field
operator|=
name|saveSecField
operator|.
name|getText
argument_list|()
operator|.
name|toLowerCase
argument_list|()
operator|.
name|trim
argument_list|()
expr_stmt|;
name|saveOrderConfig
operator|.
name|sortCriteria
index|[
literal|1
index|]
operator|.
name|descending
operator|=
name|saveSecDesc
operator|.
name|isSelected
argument_list|()
expr_stmt|;
name|saveOrderConfig
operator|.
name|sortCriteria
index|[
literal|2
index|]
operator|.
name|field
operator|=
name|saveTerField
operator|.
name|getText
argument_list|()
operator|.
name|toLowerCase
argument_list|()
operator|.
name|trim
argument_list|()
expr_stmt|;
name|saveOrderConfig
operator|.
name|sortCriteria
index|[
literal|2
index|]
operator|.
name|descending
operator|=
name|saveTerDesc
operator|.
name|isSelected
argument_list|()
expr_stmt|;
return|return
name|saveOrderConfig
return|;
block|}
block|}
end_class

end_unit
