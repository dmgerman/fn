begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
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
name|gui
operator|.
name|util
operator|.
name|component
operator|.
name|JTextFieldWithUnfocusedText
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
name|DocumentEvent
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
name|DocumentListener
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
name|actionsList
decl_stmt|;
DECL|field|keyField
specifier|private
name|JTextFieldWithUnfocusedText
name|keyField
decl_stmt|;
DECL|field|formatters
specifier|private
name|JComboBox
argument_list|<
name|String
argument_list|>
name|formatters
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
literal|"left:pref, 13dlu, left:pref, 4dlu, left:pref, 4dlu, pref:grow"
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
literal|5
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
operator|.
name|size
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|FieldFormatterCleanup
name|action
range|:
name|configuredActions
control|)
block|{
name|actionsToDisplay
operator|.
name|add
argument_list|(
name|action
argument_list|)
expr_stmt|;
block|}
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
operator|new
name|DeleteButtonListener
argument_list|()
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
literal|3
argument_list|,
literal|9
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
literal|5
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
literal|"left:pref, 4dlu, left:pref, 4dlu, pref:grow"
argument_list|,
literal|"pref, 2dlu,"
argument_list|)
argument_list|)
decl_stmt|;
name|keyField
operator|=
operator|new
name|JTextFieldWithUnfocusedText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Enter field name (e.g., title, author)"
argument_list|)
argument_list|)
expr_stmt|;
name|keyField
operator|.
name|setColumns
argument_list|(
literal|25
argument_list|)
expr_stmt|;
name|keyField
operator|.
name|getDocument
argument_list|()
operator|.
name|addDocumentListener
argument_list|(
operator|new
name|DocumentListener
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|insertUpdate
parameter_list|(
name|DocumentEvent
name|e
parameter_list|)
block|{
name|updateDescription
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|removeUpdate
parameter_list|(
name|DocumentEvent
name|e
parameter_list|)
block|{
name|updateDescription
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|changedUpdate
parameter_list|(
name|DocumentEvent
name|e
parameter_list|)
block|{
name|updateDescription
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|keyField
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
name|formatters
operator|=
operator|new
name|JComboBox
argument_list|(
name|formatterNames
operator|.
name|toArray
argument_list|()
argument_list|)
expr_stmt|;
name|formatters
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
operator|-
literal|1
operator|<
name|index
operator|&&
name|index
operator|<
name|formatterDescriptions
operator|.
name|size
argument_list|()
operator|&&
name|value
operator|!=
literal|null
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
name|keyField
operator|.
name|getText
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
name|formatters
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
name|formatters
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
operator|new
name|AddButtonListener
argument_list|()
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
DECL|class|AddButtonListener
class|class
name|AddButtonListener
implements|implements
name|ActionListener
block|{
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
name|keyField
operator|.
name|setText
argument_list|(
literal|""
argument_list|)
expr_stmt|;
block|}
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
name|keyField
operator|.
name|getText
argument_list|()
decl_stmt|;
if|if
condition|(
operator|(
name|fieldKey
operator|==
literal|null
operator|)
operator|||
name|fieldKey
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
literal|null
return|;
block|}
name|FieldFormatterCleanup
name|newAction
init|=
operator|new
name|FieldFormatterCleanup
argument_list|(
name|fieldKey
argument_list|,
name|selectedFormatter
argument_list|)
decl_stmt|;
return|return
name|newAction
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
name|formatters
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
DECL|class|DeleteButtonListener
class|class
name|DeleteButtonListener
implements|implements
name|ActionListener
block|{
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
expr_stmt|;
block|}
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
name|keyField
operator|.
name|setEnabled
argument_list|(
name|status
argument_list|)
expr_stmt|;
name|formatters
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
