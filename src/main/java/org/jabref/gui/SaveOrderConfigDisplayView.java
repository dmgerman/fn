begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
package|;
end_package

begin_import
import|import
name|javax
operator|.
name|inject
operator|.
name|Inject
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
name|ToggleGroup
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
name|model
operator|.
name|metadata
operator|.
name|SaveOrderConfig
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
name|PreferencesService
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

begin_class
DECL|class|SaveOrderConfigDisplayView
specifier|public
class|class
name|SaveOrderConfigDisplayView
extends|extends
name|GridPane
block|{
DECL|field|config
specifier|private
specifier|final
name|SaveOrderConfig
name|config
decl_stmt|;
DECL|field|saveOrderToggleGroup
annotation|@
name|FXML
specifier|private
name|ToggleGroup
name|saveOrderToggleGroup
decl_stmt|;
DECL|field|savePriSort
annotation|@
name|FXML
specifier|private
name|ComboBox
argument_list|<
name|Field
argument_list|>
name|savePriSort
decl_stmt|;
DECL|field|saveSecSort
annotation|@
name|FXML
specifier|private
name|ComboBox
argument_list|<
name|Field
argument_list|>
name|saveSecSort
decl_stmt|;
DECL|field|saveTerSort
annotation|@
name|FXML
specifier|private
name|ComboBox
argument_list|<
name|Field
argument_list|>
name|saveTerSort
decl_stmt|;
DECL|field|exportInSpecifiedOrder
annotation|@
name|FXML
specifier|private
name|RadioButton
name|exportInSpecifiedOrder
decl_stmt|;
DECL|field|exportInTableOrder
annotation|@
name|FXML
specifier|private
name|RadioButton
name|exportInTableOrder
decl_stmt|;
DECL|field|exportInOriginalOrder
annotation|@
name|FXML
specifier|private
name|RadioButton
name|exportInOriginalOrder
decl_stmt|;
DECL|field|savePriDesc
annotation|@
name|FXML
specifier|private
name|CheckBox
name|savePriDesc
decl_stmt|;
DECL|field|saveSecDesc
annotation|@
name|FXML
specifier|private
name|CheckBox
name|saveSecDesc
decl_stmt|;
DECL|field|saveTerDesc
annotation|@
name|FXML
specifier|private
name|CheckBox
name|saveTerDesc
decl_stmt|;
DECL|field|preferencesService
annotation|@
name|Inject
specifier|private
name|PreferencesService
name|preferencesService
decl_stmt|;
DECL|field|viewModel
specifier|private
name|SaveOrderConfigDisplayViewModel
name|viewModel
decl_stmt|;
DECL|method|SaveOrderConfigDisplayView (SaveOrderConfig config)
specifier|public
name|SaveOrderConfigDisplayView
parameter_list|(
name|SaveOrderConfig
name|config
parameter_list|)
block|{
name|this
operator|.
name|config
operator|=
name|config
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
name|FXML
DECL|method|initialize ()
specifier|private
name|void
name|initialize
parameter_list|()
block|{
name|viewModel
operator|=
operator|new
name|SaveOrderConfigDisplayViewModel
argument_list|(
name|config
argument_list|,
name|preferencesService
argument_list|)
expr_stmt|;
name|exportInSpecifiedOrder
operator|.
name|selectedProperty
argument_list|()
operator|.
name|bindBidirectional
argument_list|(
name|viewModel
operator|.
name|saveInSpecifiedOrderProperty
argument_list|()
argument_list|)
expr_stmt|;
name|exportInTableOrder
operator|.
name|selectedProperty
argument_list|()
operator|.
name|bindBidirectional
argument_list|(
name|viewModel
operator|.
name|saveInTableOrderProperty
argument_list|()
argument_list|)
expr_stmt|;
name|exportInOriginalOrder
operator|.
name|selectedProperty
argument_list|()
operator|.
name|bindBidirectional
argument_list|(
name|viewModel
operator|.
name|saveInOriginalProperty
argument_list|()
argument_list|)
expr_stmt|;
name|savePriSort
operator|.
name|itemsProperty
argument_list|()
operator|.
name|bindBidirectional
argument_list|(
name|viewModel
operator|.
name|priSortFieldsProperty
argument_list|()
argument_list|)
expr_stmt|;
name|saveSecSort
operator|.
name|itemsProperty
argument_list|()
operator|.
name|bindBidirectional
argument_list|(
name|viewModel
operator|.
name|secSortFieldsProperty
argument_list|()
argument_list|)
expr_stmt|;
name|saveTerSort
operator|.
name|itemsProperty
argument_list|()
operator|.
name|bindBidirectional
argument_list|(
name|viewModel
operator|.
name|terSortFieldsProperty
argument_list|()
argument_list|)
expr_stmt|;
name|savePriSort
operator|.
name|valueProperty
argument_list|()
operator|.
name|bindBidirectional
argument_list|(
name|viewModel
operator|.
name|savePriSortSelectedValueProperty
argument_list|()
argument_list|)
expr_stmt|;
name|saveSecSort
operator|.
name|valueProperty
argument_list|()
operator|.
name|bindBidirectional
argument_list|(
name|viewModel
operator|.
name|saveSecSortSelectedValueProperty
argument_list|()
argument_list|)
expr_stmt|;
name|saveTerSort
operator|.
name|valueProperty
argument_list|()
operator|.
name|bindBidirectional
argument_list|(
name|viewModel
operator|.
name|saveTerSortSelectedValueProperty
argument_list|()
argument_list|)
expr_stmt|;
name|savePriDesc
operator|.
name|selectedProperty
argument_list|()
operator|.
name|bindBidirectional
argument_list|(
name|viewModel
operator|.
name|savePriDescPropertySelected
argument_list|()
argument_list|)
expr_stmt|;
name|saveSecDesc
operator|.
name|selectedProperty
argument_list|()
operator|.
name|bindBidirectional
argument_list|(
name|viewModel
operator|.
name|saveSecDescPropertySelected
argument_list|()
argument_list|)
expr_stmt|;
name|saveTerDesc
operator|.
name|selectedProperty
argument_list|()
operator|.
name|bindBidirectional
argument_list|(
name|viewModel
operator|.
name|saveTerDescPropertySelected
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|changeExportDescriptionToSave ()
specifier|public
name|void
name|changeExportDescriptionToSave
parameter_list|()
block|{
name|exportInOriginalOrder
operator|.
name|setText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Save entries in their original order"
argument_list|)
argument_list|)
expr_stmt|;
name|exportInSpecifiedOrder
operator|.
name|setText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Save entries ordered as specified"
argument_list|)
argument_list|)
expr_stmt|;
name|exportInTableOrder
operator|.
name|setText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Save in current table sort order"
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|storeConfig ()
specifier|public
name|void
name|storeConfig
parameter_list|()
block|{
name|viewModel
operator|.
name|storeConfigInPrefs
argument_list|()
expr_stmt|;
block|}
DECL|method|getSaveOrderConfig ()
specifier|public
name|SaveOrderConfig
name|getSaveOrderConfig
parameter_list|()
block|{
return|return
name|viewModel
operator|.
name|getSaveOrderConfig
argument_list|()
return|;
block|}
block|}
end_class

end_unit

