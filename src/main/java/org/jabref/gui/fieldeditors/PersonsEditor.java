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
name|layout
operator|.
name|HBox
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
name|Priority
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
name|autocompleter
operator|.
name|AutoCompletePreferences
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
name|autocompleter
operator|.
name|AutoCompleteSuggestionProvider
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
name|autocompleter
operator|.
name|AutoCompletionTextInputBinding
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
name|fieldeditors
operator|.
name|contextmenu
operator|.
name|EditorMenus
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
name|integrity
operator|.
name|FieldCheckers
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
DECL|class|PersonsEditor
specifier|public
class|class
name|PersonsEditor
extends|extends
name|HBox
implements|implements
name|FieldEditorFX
block|{
DECL|field|viewModel
annotation|@
name|FXML
specifier|private
specifier|final
name|PersonsEditorViewModel
name|viewModel
decl_stmt|;
DECL|method|PersonsEditor (String fieldName, AutoCompleteSuggestionProvider<?> suggestionProvider, AutoCompletePreferences autoCompletePreferences, FieldCheckers fieldCheckers)
specifier|public
name|PersonsEditor
parameter_list|(
name|String
name|fieldName
parameter_list|,
name|AutoCompleteSuggestionProvider
argument_list|<
name|?
argument_list|>
name|suggestionProvider
parameter_list|,
name|AutoCompletePreferences
name|autoCompletePreferences
parameter_list|,
name|FieldCheckers
name|fieldCheckers
parameter_list|)
block|{
name|this
operator|.
name|viewModel
operator|=
operator|new
name|PersonsEditorViewModel
argument_list|(
name|fieldName
argument_list|,
name|suggestionProvider
argument_list|,
name|autoCompletePreferences
argument_list|,
name|fieldCheckers
argument_list|)
expr_stmt|;
name|EditorTextArea
name|textArea
init|=
operator|new
name|EditorTextArea
argument_list|()
decl_stmt|;
name|HBox
operator|.
name|setHgrow
argument_list|(
name|textArea
argument_list|,
name|Priority
operator|.
name|ALWAYS
argument_list|)
expr_stmt|;
name|textArea
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
name|textArea
operator|.
name|addToContextMenu
argument_list|(
name|EditorMenus
operator|.
name|getNameMenu
argument_list|(
name|textArea
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|getChildren
argument_list|()
operator|.
name|add
argument_list|(
name|textArea
argument_list|)
expr_stmt|;
name|AutoCompletionTextInputBinding
operator|.
name|autoComplete
argument_list|(
name|textArea
argument_list|,
name|viewModel
operator|::
name|complete
argument_list|,
name|viewModel
operator|.
name|getAutoCompletionConverter
argument_list|()
argument_list|,
name|viewModel
operator|.
name|getAutoCompletionStrategy
argument_list|()
argument_list|)
expr_stmt|;
name|ControlsFxVisualizer
name|validationVisualizer
init|=
operator|new
name|ControlsFxVisualizer
argument_list|()
decl_stmt|;
name|validationVisualizer
operator|.
name|initVisualization
argument_list|(
name|viewModel
operator|.
name|getFieldValidator
argument_list|()
operator|.
name|getValidationStatus
argument_list|()
argument_list|,
name|textArea
argument_list|)
expr_stmt|;
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

