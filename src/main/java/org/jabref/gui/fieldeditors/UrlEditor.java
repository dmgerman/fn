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

begin_class
DECL|class|UrlEditor
specifier|public
class|class
name|UrlEditor
extends|extends
name|HBox
implements|implements
name|FieldEditorFX
block|{
DECL|field|fieldName
specifier|private
specifier|final
name|String
name|fieldName
decl_stmt|;
DECL|field|viewModel
annotation|@
name|FXML
specifier|private
name|UrlEditorViewModel
name|viewModel
decl_stmt|;
DECL|field|textArea
annotation|@
name|FXML
specifier|private
name|EditorTextArea
name|textArea
decl_stmt|;
DECL|field|entry
specifier|private
name|Optional
argument_list|<
name|BibEntry
argument_list|>
name|entry
decl_stmt|;
DECL|method|UrlEditor (String fieldName, DialogService dialogService)
specifier|public
name|UrlEditor
parameter_list|(
name|String
name|fieldName
parameter_list|,
name|DialogService
name|dialogService
parameter_list|)
block|{
name|this
operator|.
name|fieldName
operator|=
name|fieldName
expr_stmt|;
name|this
operator|.
name|viewModel
operator|=
operator|new
name|UrlEditorViewModel
argument_list|(
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
name|viewModel
operator|.
name|textProperty
argument_list|()
operator|.
name|bindBidirectional
argument_list|(
name|textArea
operator|.
name|textProperty
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|getViewModel ()
specifier|public
name|UrlEditorViewModel
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
name|textArea
operator|.
name|bindToEntry
argument_list|(
name|fieldName
argument_list|,
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

