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
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Optional
import|;
end_import

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
name|MenuItem
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
name|Tooltip
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
name|DialogService
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
name|TaskExecutor
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
name|FieldName
import|;
end_import

begin_class
DECL|class|IdentifierEditor
specifier|public
class|class
name|IdentifierEditor
extends|extends
name|HBox
implements|implements
name|FieldEditorFX
block|{
DECL|field|viewModel
annotation|@
name|FXML
specifier|private
name|IdentifierEditorViewModel
name|viewModel
decl_stmt|;
DECL|field|textArea
annotation|@
name|FXML
specifier|private
name|EditorTextArea
name|textArea
decl_stmt|;
DECL|field|fetchInformationByIdentifierButton
annotation|@
name|FXML
specifier|private
name|Button
name|fetchInformationByIdentifierButton
decl_stmt|;
DECL|field|lookupIdentifierButton
annotation|@
name|FXML
specifier|private
name|Button
name|lookupIdentifierButton
decl_stmt|;
DECL|field|entry
specifier|private
name|Optional
argument_list|<
name|BibEntry
argument_list|>
name|entry
decl_stmt|;
DECL|method|IdentifierEditor (String fieldName, TaskExecutor taskExecutor, DialogService dialogService, AutoCompleteSuggestionProvider<?> suggestionProvider)
specifier|public
name|IdentifierEditor
parameter_list|(
name|String
name|fieldName
parameter_list|,
name|TaskExecutor
name|taskExecutor
parameter_list|,
name|DialogService
name|dialogService
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
name|IdentifierEditorViewModel
argument_list|(
name|fieldName
argument_list|,
name|suggestionProvider
argument_list|,
name|taskExecutor
argument_list|,
name|dialogService
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
name|fetchInformationByIdentifierButton
operator|.
name|setTooltip
argument_list|(
operator|new
name|Tooltip
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Get BibTeX data from %0"
argument_list|,
name|FieldName
operator|.
name|getDisplayName
argument_list|(
name|fieldName
argument_list|)
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|lookupIdentifierButton
operator|.
name|setTooltip
argument_list|(
operator|new
name|Tooltip
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Look up %0"
argument_list|,
name|FieldName
operator|.
name|getDisplayName
argument_list|(
name|fieldName
argument_list|)
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|MenuItem
argument_list|>
name|menuItems
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
if|if
condition|(
name|fieldName
operator|.
name|equalsIgnoreCase
argument_list|(
name|FieldName
operator|.
name|DOI
argument_list|)
condition|)
block|{
name|menuItems
operator|.
name|addAll
argument_list|(
name|EditorMenus
operator|.
name|getDOIMenu
argument_list|(
name|textArea
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|menuItems
operator|.
name|addAll
argument_list|(
name|EditorMenus
operator|.
name|getDefaultMenu
argument_list|(
name|textArea
argument_list|)
argument_list|)
expr_stmt|;
name|textArea
operator|.
name|addToContextMenu
argument_list|(
name|menuItems
argument_list|)
expr_stmt|;
block|}
DECL|method|getViewModel ()
specifier|public
name|IdentifierEditorViewModel
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
name|this
operator|.
name|entry
operator|=
name|Optional
operator|.
name|of
argument_list|(
name|entry
argument_list|)
expr_stmt|;
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
DECL|method|fetchInformationByIdentifier (ActionEvent event)
specifier|private
name|void
name|fetchInformationByIdentifier
parameter_list|(
name|ActionEvent
name|event
parameter_list|)
block|{
name|entry
operator|.
name|ifPresent
argument_list|(
name|bibEntry
lambda|->
name|viewModel
operator|.
name|fetchInformationByIdentifier
argument_list|(
name|bibEntry
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|FXML
DECL|method|lookupIdentifier (ActionEvent event)
specifier|private
name|void
name|lookupIdentifier
parameter_list|(
name|ActionEvent
name|event
parameter_list|)
block|{
name|entry
operator|.
name|ifPresent
argument_list|(
name|bibEntry
lambda|->
name|viewModel
operator|.
name|lookupIdentifier
argument_list|(
name|bibEntry
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|FXML
DECL|method|openExternalLink (ActionEvent event)
specifier|private
name|void
name|openExternalLink
parameter_list|(
name|ActionEvent
name|event
parameter_list|)
block|{
name|viewModel
operator|.
name|openExternalLink
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

