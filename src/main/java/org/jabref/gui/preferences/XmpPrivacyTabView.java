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
name|application
operator|.
name|Platform
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|fxml
operator|.
name|FXML
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
name|Button
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
name|ComboBox
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
name|TableColumn
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
name|TableView
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|input
operator|.
name|KeyCode
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
name|icon
operator|.
name|IconTheme
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
name|util
operator|.
name|BindingsHelper
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
name|util
operator|.
name|FieldsUtil
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
name|util
operator|.
name|IconValidationDecorator
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
name|util
operator|.
name|ValueTableCellFactory
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
name|util
operator|.
name|ViewModelListCellFactory
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
name|model
operator|.
name|entry
operator|.
name|field
operator|.
name|Field
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
name|airhacks
operator|.
name|afterburner
operator|.
name|views
operator|.
name|ViewLoader
import|;
end_import

begin_import
import|import
name|de
operator|.
name|saxsys
operator|.
name|mvvmfx
operator|.
name|utils
operator|.
name|validation
operator|.
name|visualization
operator|.
name|ControlsFxVisualizer
import|;
end_import

begin_class
DECL|class|XmpPrivacyTabView
specifier|public
class|class
name|XmpPrivacyTabView
extends|extends
name|AbstractPreferenceTabView
argument_list|<
name|XmpPrivacyTabViewModel
argument_list|>
implements|implements
name|PreferencesTab
block|{
DECL|field|enableXmpFilter
annotation|@
name|FXML
specifier|private
name|CheckBox
name|enableXmpFilter
decl_stmt|;
DECL|field|filterList
annotation|@
name|FXML
specifier|private
name|TableView
argument_list|<
name|Field
argument_list|>
name|filterList
decl_stmt|;
DECL|field|fieldColumn
annotation|@
name|FXML
specifier|private
name|TableColumn
argument_list|<
name|Field
argument_list|,
name|Field
argument_list|>
name|fieldColumn
decl_stmt|;
DECL|field|actionsColumn
annotation|@
name|FXML
specifier|private
name|TableColumn
argument_list|<
name|Field
argument_list|,
name|Field
argument_list|>
name|actionsColumn
decl_stmt|;
DECL|field|addFieldName
annotation|@
name|FXML
specifier|private
name|ComboBox
argument_list|<
name|Field
argument_list|>
name|addFieldName
decl_stmt|;
DECL|field|addField
annotation|@
name|FXML
specifier|private
name|Button
name|addField
decl_stmt|;
DECL|field|validationVisualizer
specifier|private
name|ControlsFxVisualizer
name|validationVisualizer
init|=
operator|new
name|ControlsFxVisualizer
argument_list|()
decl_stmt|;
DECL|method|XmpPrivacyTabView (JabRefPreferences preferences)
specifier|public
name|XmpPrivacyTabView
parameter_list|(
name|JabRefPreferences
name|preferences
parameter_list|)
block|{
name|this
operator|.
name|preferences
operator|=
name|preferences
expr_stmt|;
name|ViewLoader
operator|.
name|view
argument_list|(
name|this
argument_list|)
operator|.
name|root
argument_list|(
name|this
argument_list|)
operator|.
name|load
argument_list|()
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
literal|"XMP-metadata"
argument_list|)
return|;
block|}
DECL|method|initialize ()
specifier|public
name|void
name|initialize
parameter_list|()
block|{
name|this
operator|.
name|viewModel
operator|=
operator|new
name|XmpPrivacyTabViewModel
argument_list|(
name|dialogService
argument_list|,
name|preferences
argument_list|)
expr_stmt|;
name|enableXmpFilter
operator|.
name|selectedProperty
argument_list|()
operator|.
name|bindBidirectional
argument_list|(
name|viewModel
operator|.
name|xmpFilterEnabledProperty
argument_list|()
argument_list|)
expr_stmt|;
name|filterList
operator|.
name|disableProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|viewModel
operator|.
name|xmpFilterEnabledProperty
argument_list|()
operator|.
name|not
argument_list|()
argument_list|)
expr_stmt|;
name|addFieldName
operator|.
name|disableProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|viewModel
operator|.
name|xmpFilterEnabledProperty
argument_list|()
operator|.
name|not
argument_list|()
argument_list|)
expr_stmt|;
name|addField
operator|.
name|disableProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|viewModel
operator|.
name|xmpFilterEnabledProperty
argument_list|()
operator|.
name|not
argument_list|()
argument_list|)
expr_stmt|;
name|fieldColumn
operator|.
name|setSortable
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|fieldColumn
operator|.
name|setReorderable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|fieldColumn
operator|.
name|setCellValueFactory
argument_list|(
name|cellData
lambda|->
name|BindingsHelper
operator|.
name|constantOf
argument_list|(
name|cellData
operator|.
name|getValue
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
operator|new
name|ValueTableCellFactory
argument_list|<
name|Field
argument_list|,
name|Field
argument_list|>
argument_list|()
operator|.
name|withText
argument_list|(
name|FieldsUtil
operator|::
name|getNameWithType
argument_list|)
operator|.
name|install
argument_list|(
name|fieldColumn
argument_list|)
expr_stmt|;
name|actionsColumn
operator|.
name|setSortable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|actionsColumn
operator|.
name|setReorderable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|actionsColumn
operator|.
name|setCellValueFactory
argument_list|(
name|cellData
lambda|->
name|BindingsHelper
operator|.
name|constantOf
argument_list|(
name|cellData
operator|.
name|getValue
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
operator|new
name|ValueTableCellFactory
argument_list|<
name|Field
argument_list|,
name|Field
argument_list|>
argument_list|()
operator|.
name|withGraphic
argument_list|(
name|item
lambda|->
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|DELETE_ENTRY
operator|.
name|getGraphicNode
argument_list|()
argument_list|)
operator|.
name|withTooltip
argument_list|(
name|item
lambda|->
name|Localization
operator|.
name|lang
argument_list|(
literal|"Remove"
argument_list|)
operator|+
literal|" "
operator|+
name|item
operator|.
name|getName
argument_list|()
argument_list|)
operator|.
name|withOnMouseClickedEvent
argument_list|(
name|item
lambda|->
name|evt
lambda|->
name|viewModel
operator|.
name|removeFilter
argument_list|(
name|filterList
operator|.
name|getFocusModel
argument_list|()
operator|.
name|getFocusedItem
argument_list|()
argument_list|)
argument_list|)
operator|.
name|install
argument_list|(
name|actionsColumn
argument_list|)
expr_stmt|;
name|filterList
operator|.
name|setOnKeyPressed
argument_list|(
name|event
lambda|->
block|{
if|if
condition|(
name|event
operator|.
name|getCode
argument_list|()
operator|==
name|KeyCode
operator|.
name|DELETE
condition|)
block|{
name|viewModel
operator|.
name|removeFilter
argument_list|(
name|filterList
operator|.
name|getSelectionModel
argument_list|()
operator|.
name|getSelectedItem
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|filterList
operator|.
name|itemsProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|viewModel
operator|.
name|filterListProperty
argument_list|()
argument_list|)
expr_stmt|;
name|addFieldName
operator|.
name|setEditable
argument_list|(
literal|true
argument_list|)
expr_stmt|;
operator|new
name|ViewModelListCellFactory
argument_list|<
name|Field
argument_list|>
argument_list|()
operator|.
name|withText
argument_list|(
name|FieldsUtil
operator|::
name|getNameWithType
argument_list|)
operator|.
name|install
argument_list|(
name|addFieldName
argument_list|)
expr_stmt|;
name|addFieldName
operator|.
name|itemsProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|viewModel
operator|.
name|availableFieldsProperty
argument_list|()
argument_list|)
expr_stmt|;
name|addFieldName
operator|.
name|valueProperty
argument_list|()
operator|.
name|bindBidirectional
argument_list|(
name|viewModel
operator|.
name|addFieldNameProperty
argument_list|()
argument_list|)
expr_stmt|;
name|addFieldName
operator|.
name|setConverter
argument_list|(
name|FieldsUtil
operator|.
name|fieldStringConverter
argument_list|)
expr_stmt|;
name|validationVisualizer
operator|.
name|setDecoration
argument_list|(
operator|new
name|IconValidationDecorator
argument_list|()
argument_list|)
expr_stmt|;
name|Platform
operator|.
name|runLater
argument_list|(
parameter_list|()
lambda|->
name|validationVisualizer
operator|.
name|initVisualization
argument_list|(
name|viewModel
operator|.
name|xmpFilterListValidationStatus
argument_list|()
argument_list|,
name|filterList
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|addField ()
specifier|public
name|void
name|addField
parameter_list|()
block|{
name|viewModel
operator|.
name|addField
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

