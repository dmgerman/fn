begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2016 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
end_comment

begin_package
DECL|package|net.sf.jabref.gui.dbproperties
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|dbproperties
package|;
end_package

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
name|MetaData
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
name|exporter
operator|.
name|FieldFormatterCleanups
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
name|logic
operator|.
name|cleanup
operator|.
name|FieldFormatterCleanup
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
name|formatter
operator|.
name|Formatter
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
name|awt
operator|.
name|event
operator|.
name|MouseEvent
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
name|MouseMotionAdapter
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
name|Objects
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|stream
operator|.
name|Collectors
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

begin_class
DECL|class|FieldFormatterCleanupsPanel
specifier|public
class|class
name|FieldFormatterCleanupsPanel
extends|extends
name|JPanel
block|{
DECL|field|DESCRIPTION
specifier|private
specifier|static
specifier|final
name|String
name|DESCRIPTION
init|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"Description"
argument_list|)
operator|+
literal|": "
decl_stmt|;
DECL|field|enabled
specifier|private
specifier|final
name|JCheckBox
name|enabled
decl_stmt|;
DECL|field|fieldFormatterCleanups
specifier|private
name|FieldFormatterCleanups
name|fieldFormatterCleanups
decl_stmt|;
DECL|field|actionsList
specifier|private
name|JList
argument_list|<
name|?
argument_list|>
name|actionsList
decl_stmt|;
DECL|field|formattersCombobox
specifier|private
name|JComboBox
argument_list|<
name|?
argument_list|>
name|formattersCombobox
decl_stmt|;
DECL|field|selectFieldCombobox
specifier|private
name|JComboBox
argument_list|<
name|String
argument_list|>
name|selectFieldCombobox
decl_stmt|;
DECL|field|addButton
specifier|private
name|JButton
name|addButton
decl_stmt|;
DECL|field|descriptionText
specifier|private
name|JLabel
name|descriptionText
decl_stmt|;
DECL|field|deleteButton
specifier|private
name|JButton
name|deleteButton
decl_stmt|;
DECL|field|resetButton
specifier|private
name|JButton
name|resetButton
decl_stmt|;
DECL|field|defaultFormatters
specifier|private
specifier|final
name|FieldFormatterCleanups
name|defaultFormatters
decl_stmt|;
DECL|method|FieldFormatterCleanupsPanel (String description, FieldFormatterCleanups defaultFormatters)
specifier|public
name|FieldFormatterCleanupsPanel
parameter_list|(
name|String
name|description
parameter_list|,
name|FieldFormatterCleanups
name|defaultFormatters
parameter_list|)
block|{
name|this
operator|.
name|defaultFormatters
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|defaultFormatters
argument_list|)
expr_stmt|;
name|enabled
operator|=
operator|new
name|JCheckBox
argument_list|(
name|description
argument_list|)
expr_stmt|;
block|}
DECL|method|setValues (MetaData metaData)
specifier|public
name|void
name|setValues
parameter_list|(
name|MetaData
name|metaData
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|metaData
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|saveActionsMetaList
init|=
name|metaData
operator|.
name|getData
argument_list|(
name|MetaData
operator|.
name|SAVE_ACTIONS
argument_list|)
decl_stmt|;
name|setValues
argument_list|(
name|FieldFormatterCleanups
operator|.
name|parseFromString
argument_list|(
name|saveActionsMetaList
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|setValues (FieldFormatterCleanups formatterCleanups)
specifier|public
name|void
name|setValues
parameter_list|(
name|FieldFormatterCleanups
name|formatterCleanups
parameter_list|)
block|{
name|fieldFormatterCleanups
operator|=
name|formatterCleanups
expr_stmt|;
comment|// first clear existing content
name|this
operator|.
name|removeAll
argument_list|()
expr_stmt|;
name|List
argument_list|<
name|FieldFormatterCleanup
argument_list|>
name|configuredActions
init|=
name|fieldFormatterCleanups
operator|.
name|getConfiguredActions
argument_list|()
decl_stmt|;
comment|//The copy is necessary becaue the original List is unmodifiable
name|List
argument_list|<
name|FieldFormatterCleanup
argument_list|>
name|actionsToDisplay
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|(
name|configuredActions
argument_list|)
decl_stmt|;
name|buildLayout
argument_list|(
name|actionsToDisplay
argument_list|)
expr_stmt|;
block|}
DECL|method|buildLayout (List<FieldFormatterCleanup> actionsToDisplay)
specifier|private
name|void
name|buildLayout
parameter_list|(
name|List
argument_list|<
name|FieldFormatterCleanup
argument_list|>
name|actionsToDisplay
parameter_list|)
block|{
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
operator|new
name|FormLayout
argument_list|(
literal|"left:pref, 13dlu, left:pref:grow, 4dlu, pref, 4dlu, pref"
argument_list|,
literal|"pref, 2dlu, pref, 2dlu, pref, 4dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref,"
argument_list|)
argument_list|)
decl_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|enabled
argument_list|)
operator|.
name|xyw
argument_list|(
literal|1
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
name|getSelectorPanel
argument_list|()
argument_list|)
operator|.
name|xyw
argument_list|(
literal|3
argument_list|,
literal|3
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|descriptionText
operator|=
operator|new
name|JLabel
argument_list|(
name|DESCRIPTION
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|descriptionText
argument_list|)
operator|.
name|xyw
argument_list|(
literal|3
argument_list|,
literal|5
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|actionsList
operator|=
operator|new
name|JList
argument_list|<>
argument_list|(
operator|new
name|SaveActionsListModel
argument_list|(
name|actionsToDisplay
argument_list|)
argument_list|)
expr_stmt|;
name|actionsList
operator|.
name|setSelectionMode
argument_list|(
name|ListSelectionModel
operator|.
name|SINGLE_SELECTION
argument_list|)
expr_stmt|;
name|actionsList
operator|.
name|addMouseMotionListener
argument_list|(
operator|new
name|MouseMotionAdapter
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|mouseMoved
parameter_list|(
name|MouseEvent
name|e
parameter_list|)
block|{
name|super
operator|.
name|mouseMoved
argument_list|(
name|e
argument_list|)
expr_stmt|;
name|SaveActionsListModel
name|m
init|=
operator|(
name|SaveActionsListModel
operator|)
name|actionsList
operator|.
name|getModel
argument_list|()
decl_stmt|;
name|int
name|index
init|=
name|actionsList
operator|.
name|locationToIndex
argument_list|(
name|e
operator|.
name|getPoint
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|index
operator|>
operator|-
literal|1
condition|)
block|{
name|actionsList
operator|.
name|setToolTipText
argument_list|(
name|m
operator|.
name|getElementAt
argument_list|(
name|index
argument_list|)
operator|.
name|getDescription
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|actionsList
argument_list|)
operator|.
name|xyw
argument_list|(
literal|3
argument_list|,
literal|7
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|resetButton
operator|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Reset"
argument_list|)
argument_list|)
expr_stmt|;
name|resetButton
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
operator|(
operator|(
name|SaveActionsListModel
operator|)
name|actionsList
operator|.
name|getModel
argument_list|()
operator|)
operator|.
name|reset
argument_list|(
name|defaultFormatters
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|resetButton
argument_list|)
operator|.
name|xy
argument_list|(
literal|3
argument_list|,
literal|9
argument_list|)
expr_stmt|;
name|this
operator|.
name|setLayout
argument_list|(
operator|new
name|BorderLayout
argument_list|()
argument_list|)
expr_stmt|;
name|this
operator|.
name|add
argument_list|(
name|builder
operator|.
name|getPanel
argument_list|()
argument_list|,
name|BorderLayout
operator|.
name|WEST
argument_list|)
expr_stmt|;
comment|// make sure the layout is set according to the checkbox
name|enabled
operator|.
name|addActionListener
argument_list|(
operator|new
name|EnablementStatusListener
argument_list|(
name|fieldFormatterCleanups
operator|.
name|isEnabled
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|enabled
operator|.
name|setSelected
argument_list|(
name|fieldFormatterCleanups
operator|.
name|isEnabled
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|updateDescription ()
specifier|private
name|void
name|updateDescription
parameter_list|()
block|{
name|FieldFormatterCleanup
name|formatterCleanup
init|=
name|getFieldFormatterCleanup
argument_list|()
decl_stmt|;
if|if
condition|(
name|formatterCleanup
operator|!=
literal|null
condition|)
block|{
name|descriptionText
operator|.
name|setText
argument_list|(
name|DESCRIPTION
operator|+
name|formatterCleanup
operator|.
name|getDescription
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|Formatter
name|selectedFormatter
init|=
name|getFieldFormatter
argument_list|()
decl_stmt|;
if|if
condition|(
name|selectedFormatter
operator|!=
literal|null
condition|)
block|{
comment|// Create dummy FieldFormatterCleanup just for displaying the description
name|FieldFormatterCleanup
name|displayFormatterCleanup
init|=
operator|new
name|FieldFormatterCleanup
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"the given field"
argument_list|)
argument_list|,
name|selectedFormatter
argument_list|)
decl_stmt|;
name|descriptionText
operator|.
name|setText
argument_list|(
name|DESCRIPTION
operator|+
name|displayFormatterCleanup
operator|.
name|getDescription
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|descriptionText
operator|.
name|setText
argument_list|(
name|DESCRIPTION
argument_list|)
expr_stmt|;
block|}
block|}
block|}
DECL|method|getSelectorPanel ()
specifier|private
name|JPanel
name|getSelectorPanel
parameter_list|()
block|{
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
operator|new
name|FormLayout
argument_list|(
literal|"left:pref, 4dlu, left:pref, 4dlu, left:pref, 4dlu, pref:grow"
argument_list|,
literal|"pref, 2dlu, pref:grow, 2dlu"
argument_list|)
argument_list|)
decl_stmt|;
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
name|selectFieldCombobox
operator|=
operator|new
name|JComboBox
argument_list|<>
argument_list|(
name|allPlusKey
argument_list|)
expr_stmt|;
name|selectFieldCombobox
operator|.
name|setEditable
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|selectFieldCombobox
argument_list|)
operator|.
name|xy
argument_list|(
literal|1
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|formatterNames
init|=
name|fieldFormatterCleanups
operator|.
name|getAvailableFormatters
argument_list|()
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|formatter
lambda|->
name|formatter
operator|.
name|getKey
argument_list|()
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|formatterDescriptions
init|=
name|fieldFormatterCleanups
operator|.
name|getAvailableFormatters
argument_list|()
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|formatter
lambda|->
name|formatter
operator|.
name|getDescription
argument_list|()
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
argument_list|)
decl_stmt|;
name|formattersCombobox
operator|=
operator|new
name|JComboBox
argument_list|<>
argument_list|(
name|formatterNames
operator|.
name|toArray
argument_list|()
argument_list|)
expr_stmt|;
name|formattersCombobox
operator|.
name|setRenderer
argument_list|(
operator|new
name|DefaultListCellRenderer
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|Component
name|getListCellRendererComponent
parameter_list|(
name|JList
argument_list|<
name|?
argument_list|>
name|list
parameter_list|,
name|Object
name|value
parameter_list|,
name|int
name|index
parameter_list|,
name|boolean
name|isSelected
parameter_list|,
name|boolean
name|cellHasFocus
parameter_list|)
block|{
if|if
condition|(
operator|(
operator|-
literal|1
operator|<
name|index
operator|)
operator|&&
operator|(
name|index
operator|<
name|formatterDescriptions
operator|.
name|size
argument_list|()
operator|)
operator|&&
operator|(
name|value
operator|!=
literal|null
operator|)
condition|)
block|{
name|setToolTipText
argument_list|(
name|String
operator|.
name|format
argument_list|(
name|formatterDescriptions
operator|.
name|get
argument_list|(
name|index
argument_list|)
argument_list|,
name|selectFieldCombobox
operator|.
name|getSelectedItem
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|super
operator|.
name|getListCellRendererComponent
argument_list|(
name|list
argument_list|,
name|value
argument_list|,
name|index
argument_list|,
name|isSelected
argument_list|,
name|cellHasFocus
argument_list|)
return|;
block|}
block|}
argument_list|)
expr_stmt|;
name|formattersCombobox
operator|.
name|addItemListener
argument_list|(
name|e
lambda|->
name|updateDescription
argument_list|()
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|formattersCombobox
argument_list|)
operator|.
name|xy
argument_list|(
literal|3
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|addButton
operator|=
operator|new
name|JButton
argument_list|(
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|ADD_NOBOX
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
expr_stmt|;
name|addButton
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
block|{
name|FieldFormatterCleanup
name|newAction
init|=
name|getFieldFormatterCleanup
argument_list|()
decl_stmt|;
if|if
condition|(
name|newAction
operator|==
literal|null
condition|)
block|{
return|return;
block|}
operator|(
operator|(
name|SaveActionsListModel
operator|)
name|actionsList
operator|.
name|getModel
argument_list|()
operator|)
operator|.
name|addSaveAction
argument_list|(
name|newAction
argument_list|)
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|addButton
argument_list|)
operator|.
name|xy
argument_list|(
literal|5
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|deleteButton
operator|=
operator|new
name|JButton
argument_list|(
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|REMOVE_NOBOX
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
expr_stmt|;
name|deleteButton
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
operator|(
operator|(
name|SaveActionsListModel
operator|)
name|actionsList
operator|.
name|getModel
argument_list|()
operator|)
operator|.
name|removeAtIndex
argument_list|(
name|actionsList
operator|.
name|getSelectedIndex
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|deleteButton
argument_list|)
operator|.
name|xy
argument_list|(
literal|7
argument_list|,
literal|1
argument_list|)
expr_stmt|;
return|return
name|builder
operator|.
name|getPanel
argument_list|()
return|;
block|}
DECL|method|storeSettings (MetaData metaData)
specifier|public
name|void
name|storeSettings
parameter_list|(
name|MetaData
name|metaData
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|metaData
argument_list|)
expr_stmt|;
name|FieldFormatterCleanups
name|formatterCleanups
init|=
name|getFormatterCleanups
argument_list|()
decl_stmt|;
comment|// if all actions have been removed, remove the save actions from the MetaData
if|if
condition|(
name|formatterCleanups
operator|.
name|getConfiguredActions
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|metaData
operator|.
name|remove
argument_list|(
name|MetaData
operator|.
name|SAVE_ACTIONS
argument_list|)
expr_stmt|;
return|return;
block|}
name|metaData
operator|.
name|putData
argument_list|(
name|MetaData
operator|.
name|SAVE_ACTIONS
argument_list|,
name|formatterCleanups
operator|.
name|convertToString
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|getFormatterCleanups ()
specifier|public
name|FieldFormatterCleanups
name|getFormatterCleanups
parameter_list|()
block|{
name|List
argument_list|<
name|FieldFormatterCleanup
argument_list|>
name|actions
init|=
operator|(
operator|(
name|SaveActionsListModel
operator|)
name|actionsList
operator|.
name|getModel
argument_list|()
operator|)
operator|.
name|getAllActions
argument_list|()
decl_stmt|;
return|return
operator|new
name|FieldFormatterCleanups
argument_list|(
name|enabled
operator|.
name|isSelected
argument_list|()
argument_list|,
name|actions
argument_list|)
return|;
block|}
DECL|method|hasChanged ()
specifier|public
name|boolean
name|hasChanged
parameter_list|()
block|{
return|return
operator|!
name|fieldFormatterCleanups
operator|.
name|equals
argument_list|(
name|getFormatterCleanups
argument_list|()
argument_list|)
return|;
block|}
DECL|method|isDefaultSaveActions ()
specifier|public
name|boolean
name|isDefaultSaveActions
parameter_list|()
block|{
return|return
name|FieldFormatterCleanups
operator|.
name|DEFAULT_SAVE_ACTIONS
operator|.
name|equals
argument_list|(
name|getFormatterCleanups
argument_list|()
argument_list|)
return|;
block|}
DECL|method|getFieldFormatterCleanup ()
specifier|private
name|FieldFormatterCleanup
name|getFieldFormatterCleanup
parameter_list|()
block|{
name|Formatter
name|selectedFormatter
init|=
name|getFieldFormatter
argument_list|()
decl_stmt|;
name|String
name|fieldKey
init|=
name|selectFieldCombobox
operator|.
name|getSelectedItem
argument_list|()
operator|.
name|toString
argument_list|()
decl_stmt|;
return|return
operator|new
name|FieldFormatterCleanup
argument_list|(
name|fieldKey
argument_list|,
name|selectedFormatter
argument_list|)
return|;
block|}
DECL|method|getFieldFormatter ()
specifier|private
name|Formatter
name|getFieldFormatter
parameter_list|()
block|{
name|Formatter
name|selectedFormatter
init|=
literal|null
decl_stmt|;
name|String
name|selectedFormatterKey
init|=
name|formattersCombobox
operator|.
name|getSelectedItem
argument_list|()
operator|.
name|toString
argument_list|()
decl_stmt|;
for|for
control|(
name|Formatter
name|formatter
range|:
name|fieldFormatterCleanups
operator|.
name|getAvailableFormatters
argument_list|()
control|)
block|{
if|if
condition|(
name|formatter
operator|.
name|getKey
argument_list|()
operator|.
name|equals
argument_list|(
name|selectedFormatterKey
argument_list|)
condition|)
block|{
name|selectedFormatter
operator|=
name|formatter
expr_stmt|;
break|break;
block|}
block|}
return|return
name|selectedFormatter
return|;
block|}
DECL|class|EnablementStatusListener
class|class
name|EnablementStatusListener
implements|implements
name|ActionListener
block|{
DECL|method|EnablementStatusListener (boolean initialStatus)
specifier|public
name|EnablementStatusListener
parameter_list|(
name|boolean
name|initialStatus
parameter_list|)
block|{
name|setStatus
argument_list|(
name|initialStatus
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|actionPerformed (ActionEvent e)
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|boolean
name|enablementStatus
init|=
name|enabled
operator|.
name|isSelected
argument_list|()
decl_stmt|;
name|setStatus
argument_list|(
name|enablementStatus
argument_list|)
expr_stmt|;
block|}
DECL|method|setStatus (boolean status)
specifier|private
name|void
name|setStatus
parameter_list|(
name|boolean
name|status
parameter_list|)
block|{
name|actionsList
operator|.
name|setEnabled
argument_list|(
name|status
argument_list|)
expr_stmt|;
name|selectFieldCombobox
operator|.
name|setEnabled
argument_list|(
name|status
argument_list|)
expr_stmt|;
name|formattersCombobox
operator|.
name|setEnabled
argument_list|(
name|status
argument_list|)
expr_stmt|;
name|addButton
operator|.
name|setEnabled
argument_list|(
name|status
argument_list|)
expr_stmt|;
name|deleteButton
operator|.
name|setEnabled
argument_list|(
name|status
argument_list|)
expr_stmt|;
name|resetButton
operator|.
name|setEnabled
argument_list|(
name|status
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

