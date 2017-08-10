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
name|beans
operator|.
name|binding
operator|.
name|Bindings
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
name|gui
operator|.
name|util
operator|.
name|component
operator|.
name|TagBar
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
name|database
operator|.
name|BibDatabaseContext
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
name|model
operator|.
name|entry
operator|.
name|ParsedEntryLink
import|;
end_import

begin_class
DECL|class|LinkedEntriesEditor
specifier|public
class|class
name|LinkedEntriesEditor
extends|extends
name|HBox
implements|implements
name|FieldEditorFX
block|{
DECL|field|viewModel
annotation|@
name|FXML
specifier|private
name|LinkedEntriesEditorViewModel
name|viewModel
decl_stmt|;
DECL|field|linkedEntriesBar
annotation|@
name|FXML
specifier|private
name|TagBar
argument_list|<
name|ParsedEntryLink
argument_list|>
name|linkedEntriesBar
decl_stmt|;
DECL|method|LinkedEntriesEditor (String fieldName, BibDatabaseContext databaseContext, AutoCompleteSuggestionProvider<?> suggestionProvider, FieldCheckers fieldCheckers)
specifier|public
name|LinkedEntriesEditor
parameter_list|(
name|String
name|fieldName
parameter_list|,
name|BibDatabaseContext
name|databaseContext
parameter_list|,
name|AutoCompleteSuggestionProvider
argument_list|<
name|?
argument_list|>
name|suggestionProvider
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
name|LinkedEntriesEditorViewModel
argument_list|(
name|fieldName
argument_list|,
name|suggestionProvider
argument_list|,
name|databaseContext
argument_list|,
name|fieldCheckers
argument_list|)
expr_stmt|;
name|ControlHelper
operator|.
name|loadFXMLForControl
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|linkedEntriesBar
operator|.
name|setStringConverter
argument_list|(
name|viewModel
operator|.
name|getStringConverter
argument_list|()
argument_list|)
expr_stmt|;
name|linkedEntriesBar
operator|.
name|setOnTagClicked
argument_list|(
parameter_list|(
name|parsedEntryLink
parameter_list|,
name|mouseEvent
parameter_list|)
lambda|->
name|viewModel
operator|.
name|jumpToEntry
argument_list|(
name|parsedEntryLink
argument_list|)
argument_list|)
expr_stmt|;
name|Bindings
operator|.
name|bindContentBidirectional
argument_list|(
name|linkedEntriesBar
operator|.
name|tagsProperty
argument_list|()
argument_list|,
name|viewModel
operator|.
name|linkedEntriesProperty
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|getViewModel ()
specifier|public
name|LinkedEntriesEditorViewModel
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

