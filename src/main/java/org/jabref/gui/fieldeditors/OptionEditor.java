begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.fieldeditors
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|fieldeditors
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
name|Parent
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
name|layout
operator|.
name|HBox
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
name|model
operator|.
name|entry
operator|.
name|BibEntry
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

begin_comment
comment|/**  * Field editor that provides various pre-defined options as a drop-down combobox.  */
end_comment

begin_class
DECL|class|OptionEditor
specifier|public
class|class
name|OptionEditor
parameter_list|<
name|T
parameter_list|>
extends|extends
name|HBox
implements|implements
name|FieldEditorFX
block|{
DECL|field|viewModel
annotation|@
name|FXML
specifier|private
name|OptionEditorViewModel
argument_list|<
name|T
argument_list|>
name|viewModel
decl_stmt|;
DECL|field|comboBox
annotation|@
name|FXML
specifier|private
name|ComboBox
argument_list|<
name|T
argument_list|>
name|comboBox
decl_stmt|;
DECL|method|OptionEditor (OptionEditorViewModel<T> viewModel)
specifier|public
name|OptionEditor
parameter_list|(
name|OptionEditorViewModel
argument_list|<
name|T
argument_list|>
name|viewModel
parameter_list|)
block|{
name|this
operator|.
name|viewModel
operator|=
name|viewModel
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
name|comboBox
operator|.
name|setConverter
argument_list|(
name|viewModel
operator|.
name|getStringConverter
argument_list|()
argument_list|)
expr_stmt|;
name|comboBox
operator|.
name|setCellFactory
argument_list|(
operator|new
name|ViewModelListCellFactory
argument_list|<
name|T
argument_list|>
argument_list|()
operator|.
name|withText
argument_list|(
name|viewModel
operator|::
name|convertToDisplayText
argument_list|)
argument_list|)
expr_stmt|;
name|comboBox
operator|.
name|getItems
argument_list|()
operator|.
name|setAll
argument_list|(
name|viewModel
operator|.
name|getItems
argument_list|()
argument_list|)
expr_stmt|;
name|comboBox
operator|.
name|getEditor
argument_list|()
operator|.
name|textProperty
argument_list|()
operator|.
name|bindBidirectional
argument_list|(
name|viewModel
operator|.
name|textProperty
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|getViewModel ()
specifier|public
name|OptionEditorViewModel
argument_list|<
name|T
argument_list|>
name|getViewModel
parameter_list|()
block|{
return|return
name|viewModel
return|;
block|}
annotation|@
name|Override
DECL|method|bindToEntry (BibEntry entry)
specifier|public
name|void
name|bindToEntry
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
name|viewModel
operator|.
name|bindToEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|getNode ()
specifier|public
name|Parent
name|getNode
parameter_list|()
block|{
return|return
name|this
return|;
block|}
block|}
end_class

end_unit

