begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.edit
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|edit
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
name|ButtonType
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
name|TextField
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
name|BasePanel
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
name|BaseDialog
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
name|ControlHelper
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
name|logic
operator|.
name|l10n
operator|.
name|Localization
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
DECL|class|ReplaceStringView
specifier|public
class|class
name|ReplaceStringView
extends|extends
name|BaseDialog
argument_list|<
name|Void
argument_list|>
block|{
DECL|field|allReplace
annotation|@
name|FXML
specifier|private
name|RadioButton
name|allReplace
decl_stmt|;
DECL|field|selectFieldOnly
annotation|@
name|FXML
specifier|private
name|CheckBox
name|selectFieldOnly
decl_stmt|;
DECL|field|replaceButton
annotation|@
name|FXML
specifier|private
name|ButtonType
name|replaceButton
decl_stmt|;
DECL|field|limitFieldInput
annotation|@
name|FXML
specifier|private
name|TextField
name|limitFieldInput
decl_stmt|;
DECL|field|findField
annotation|@
name|FXML
specifier|private
name|TextField
name|findField
decl_stmt|;
DECL|field|replaceField
annotation|@
name|FXML
specifier|private
name|TextField
name|replaceField
decl_stmt|;
DECL|field|viewModel
specifier|private
name|ReplaceStringViewModel
name|viewModel
decl_stmt|;
DECL|field|visualizer
specifier|private
specifier|final
name|ControlsFxVisualizer
name|visualizer
init|=
operator|new
name|ControlsFxVisualizer
argument_list|()
decl_stmt|;
DECL|method|ReplaceStringView (BasePanel basePanel)
specifier|public
name|ReplaceStringView
parameter_list|(
name|BasePanel
name|basePanel
parameter_list|)
block|{
name|this
operator|.
name|setTitle
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Replace String"
argument_list|)
argument_list|)
expr_stmt|;
name|viewModel
operator|=
operator|new
name|ReplaceStringViewModel
argument_list|(
name|basePanel
argument_list|)
expr_stmt|;
name|ViewLoader
operator|.
name|view
argument_list|(
name|this
argument_list|)
operator|.
name|load
argument_list|()
operator|.
name|setAsDialogPane
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|ControlHelper
operator|.
name|setAction
argument_list|(
name|replaceButton
argument_list|,
name|getDialogPane
argument_list|()
argument_list|,
name|event
lambda|->
name|buttonReplace
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|FXML
DECL|method|initialize ()
specifier|public
name|void
name|initialize
parameter_list|()
block|{
name|visualizer
operator|.
name|setDecoration
argument_list|(
operator|new
name|IconValidationDecorator
argument_list|()
argument_list|)
expr_stmt|;
name|viewModel
operator|.
name|findStringProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|findField
operator|.
name|textProperty
argument_list|()
argument_list|)
expr_stmt|;
name|viewModel
operator|.
name|replaceStringProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|replaceField
operator|.
name|textProperty
argument_list|()
argument_list|)
expr_stmt|;
name|viewModel
operator|.
name|fieldStringProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|limitFieldInput
operator|.
name|textProperty
argument_list|()
argument_list|)
expr_stmt|;
name|viewModel
operator|.
name|selectOnlyProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|selectFieldOnly
operator|.
name|selectedProperty
argument_list|()
argument_list|)
expr_stmt|;
name|viewModel
operator|.
name|allFieldReplaceProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|allReplace
operator|.
name|selectedProperty
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|FXML
DECL|method|buttonReplace ()
specifier|private
name|void
name|buttonReplace
parameter_list|()
block|{
name|String
name|findString
init|=
name|findField
operator|.
name|getText
argument_list|()
decl_stmt|;
if|if
condition|(
literal|""
operator|.
name|equals
argument_list|(
name|findString
argument_list|)
condition|)
block|{
name|this
operator|.
name|close
argument_list|()
expr_stmt|;
return|return;
block|}
name|viewModel
operator|.
name|replace
argument_list|()
expr_stmt|;
name|this
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit
