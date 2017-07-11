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
name|event
operator|.
name|ActionEvent
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
name|model
operator|.
name|entry
operator|.
name|BibEntry
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

begin_class
DECL|class|OwnerEditor
specifier|public
class|class
name|OwnerEditor
extends|extends
name|HBox
implements|implements
name|FieldEditorFX
block|{
DECL|field|viewModel
annotation|@
name|FXML
specifier|private
name|OwnerEditorViewModel
name|viewModel
decl_stmt|;
DECL|field|textArea
annotation|@
name|FXML
specifier|private
name|EditorTextArea
name|textArea
decl_stmt|;
DECL|method|OwnerEditor (String fieldName, JabRefPreferences preferences, AutoCompleteSuggestionProvider<?> suggestionProvider)
specifier|public
name|OwnerEditor
parameter_list|(
name|String
name|fieldName
parameter_list|,
name|JabRefPreferences
name|preferences
parameter_list|,
name|AutoCompleteSuggestionProvider
argument_list|<
name|?
argument_list|>
name|suggestionProvider
parameter_list|)
block|{
name|this
operator|.
name|viewModel
operator|=
operator|new
name|OwnerEditorViewModel
argument_list|(
name|fieldName
argument_list|,
name|suggestionProvider
argument_list|,
name|preferences
argument_list|)
expr_stmt|;
name|ControlHelper
operator|.
name|loadFXMLForControl
argument_list|(
name|this
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
block|}
DECL|method|getViewModel ()
specifier|public
name|OwnerEditorViewModel
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
annotation|@
name|FXML
DECL|method|setOwner (ActionEvent event)
specifier|private
name|void
name|setOwner
parameter_list|(
name|ActionEvent
name|event
parameter_list|)
block|{
name|viewModel
operator|.
name|setOwner
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

