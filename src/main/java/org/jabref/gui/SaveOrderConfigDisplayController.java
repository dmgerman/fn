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

begin_class
DECL|class|SaveOrderConfigDisplayController
specifier|public
class|class
name|SaveOrderConfigDisplayController
block|{
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
name|String
argument_list|>
name|savePriSort
decl_stmt|;
DECL|field|saveSecSort
annotation|@
name|FXML
specifier|private
name|ComboBox
argument_list|<
name|String
argument_list|>
name|saveSecSort
decl_stmt|;
DECL|field|saveTerSort
annotation|@
name|FXML
specifier|private
name|ComboBox
argument_list|<
name|String
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
DECL|method|init ()
specifier|public
name|void
name|init
parameter_list|()
block|{      }
block|}
end_class

end_unit

