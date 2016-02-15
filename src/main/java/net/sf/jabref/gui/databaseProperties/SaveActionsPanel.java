begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.databaseProperties
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|databaseProperties
package|;
end_package

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
name|SaveAction
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
name|SaveActions
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
DECL|class|SaveActionsPanel
specifier|public
class|class
name|SaveActionsPanel
extends|extends
name|JPanel
block|{
DECL|field|enabled
specifier|private
name|JCheckBox
name|enabled
decl_stmt|;
DECL|field|saveActions
specifier|private
name|SaveActions
name|saveActions
decl_stmt|;
DECL|field|actionsList
specifier|private
name|JList
name|actionsList
decl_stmt|;
DECL|field|keyField
specifier|private
name|JTextField
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
DECL|method|SaveActionsPanel ()
specifier|public
name|SaveActionsPanel
parameter_list|()
block|{
name|enabled
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Enable save actions"
argument_list|)
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
comment|// first clear existing content
name|this
operator|.
name|removeAll
argument_list|()
expr_stmt|;
name|saveActions
operator|=
operator|new
name|SaveActions
argument_list|(
name|metaData
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|SaveAction
argument_list|>
name|configuredActions
init|=
name|saveActions
operator|.
name|getConfiguredActions
argument_list|()
decl_stmt|;
name|enabled
operator|.
name|setSelected
argument_list|(
name|saveActions
operator|.
name|isEnabled
argument_list|()
argument_list|)
expr_stmt|;
name|this
operator|.
name|setLayout
argument_list|(
operator|new
name|GridLayout
argument_list|(
literal|2
operator|+
name|configuredActions
operator|.
name|size
argument_list|()
argument_list|,
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|add
argument_list|(
name|enabled
argument_list|)
expr_stmt|;
name|this
operator|.
name|add
argument_list|(
name|getSelectorPanel
argument_list|()
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|SaveAction
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
name|SaveAction
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
name|actionsList
operator|=
operator|new
name|JList
argument_list|(
operator|new
name|SaveActionsListModel
argument_list|<>
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
name|this
operator|.
name|add
argument_list|(
name|actionsList
argument_list|)
expr_stmt|;
name|JButton
name|deleteButton
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Delete"
argument_list|)
argument_list|)
decl_stmt|;
name|deleteButton
operator|.
name|addActionListener
argument_list|(
operator|new
name|DeleteButtonListener
argument_list|()
argument_list|)
expr_stmt|;
name|this
operator|.
name|add
argument_list|(
name|deleteButton
argument_list|)
expr_stmt|;
block|}
DECL|method|getSelectorPanel ()
specifier|private
name|JPanel
name|getSelectorPanel
parameter_list|()
block|{
name|JPanel
name|panel
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|panel
operator|.
name|setLayout
argument_list|(
operator|new
name|GridLayout
argument_list|(
literal|1
argument_list|,
literal|3
argument_list|)
argument_list|)
expr_stmt|;
name|keyField
operator|=
operator|new
name|JTextField
argument_list|(
literal|20
argument_list|)
expr_stmt|;
name|panel
operator|.
name|add
argument_list|(
name|keyField
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|formatterNames
init|=
name|saveActions
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
name|panel
operator|.
name|add
argument_list|(
name|formatters
argument_list|)
expr_stmt|;
name|JButton
name|addButton
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Add"
argument_list|)
argument_list|)
decl_stmt|;
name|addButton
operator|.
name|addActionListener
argument_list|(
operator|new
name|AddButtonListener
argument_list|()
argument_list|)
expr_stmt|;
name|panel
operator|.
name|add
argument_list|(
name|addButton
argument_list|)
expr_stmt|;
return|return
name|panel
return|;
block|}
DECL|method|storeSettings (MetaData metaData)
specifier|public
name|boolean
name|storeSettings
parameter_list|(
name|MetaData
name|metaData
parameter_list|)
block|{
name|java
operator|.
name|util
operator|.
name|List
argument_list|<
name|String
argument_list|>
name|actions
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
if|if
condition|(
name|enabled
operator|.
name|isSelected
argument_list|()
condition|)
block|{
name|actions
operator|.
name|add
argument_list|(
literal|"enabled"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|actions
operator|.
name|add
argument_list|(
literal|"disabled"
argument_list|)
expr_stmt|;
block|}
name|List
argument_list|<
name|SaveAction
argument_list|>
name|newActions
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
for|for
control|(
name|SaveAction
name|action
range|:
name|newActions
control|)
block|{
name|actions
operator|.
name|add
argument_list|(
name|action
operator|.
name|getFieldName
argument_list|()
argument_list|)
expr_stmt|;
name|actions
operator|.
name|add
argument_list|(
name|action
operator|.
name|getFormatter
argument_list|()
operator|.
name|getKey
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|metaData
operator|.
name|putData
argument_list|(
name|SaveActions
operator|.
name|META_KEY
argument_list|,
name|actions
argument_list|)
expr_stmt|;
name|boolean
name|hasChanged
init|=
name|saveActions
operator|.
name|equals
argument_list|(
operator|new
name|SaveActions
argument_list|(
operator|(
name|metaData
operator|)
argument_list|)
argument_list|)
decl_stmt|;
return|return
name|hasChanged
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
name|saveActions
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
name|fieldKey
operator|==
literal|null
operator|||
name|fieldKey
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
block|{
return|return;
block|}
name|SaveAction
name|newAction
init|=
operator|new
name|SaveAction
argument_list|(
name|fieldKey
argument_list|,
name|selectedFormatter
argument_list|)
decl_stmt|;
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
block|}
end_class

end_unit

