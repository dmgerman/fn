begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.contentselector
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|contentselector
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
name|java
operator|.
name|util
operator|.
name|function
operator|.
name|Supplier
import|;
end_import

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
name|beans
operator|.
name|property
operator|.
name|ListProperty
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
name|ListView
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
name|SelectionModel
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
name|org
operator|.
name|fxmisc
operator|.
name|easybind
operator|.
name|EasyBind
import|;
end_import

begin_class
DECL|class|ContentSelectorDialogView
specifier|public
class|class
name|ContentSelectorDialogView
extends|extends
name|BaseDialog
argument_list|<
name|Void
argument_list|>
block|{
annotation|@
name|FXML
DECL|field|addFieldNameButton
specifier|private
name|Button
name|addFieldNameButton
decl_stmt|;
annotation|@
name|FXML
DECL|field|removeFieldNameButton
specifier|private
name|Button
name|removeFieldNameButton
decl_stmt|;
annotation|@
name|FXML
DECL|field|addKeywordButton
specifier|private
name|Button
name|addKeywordButton
decl_stmt|;
annotation|@
name|FXML
DECL|field|removeKeywordButton
specifier|private
name|Button
name|removeKeywordButton
decl_stmt|;
annotation|@
name|FXML
DECL|field|fieldNamesListView
specifier|private
name|ListView
argument_list|<
name|String
argument_list|>
name|fieldNamesListView
decl_stmt|;
annotation|@
name|FXML
DECL|field|keywordsListView
specifier|private
name|ListView
argument_list|<
name|String
argument_list|>
name|keywordsListView
decl_stmt|;
annotation|@
name|FXML
DECL|field|saveButton
specifier|private
name|ButtonType
name|saveButton
decl_stmt|;
annotation|@
name|Inject
DECL|field|dialogService
specifier|private
name|DialogService
name|dialogService
decl_stmt|;
DECL|field|basePanel
specifier|private
specifier|final
name|BasePanel
name|basePanel
decl_stmt|;
DECL|field|viewModel
specifier|private
name|ContentSelectorDialogViewModel
name|viewModel
decl_stmt|;
DECL|method|ContentSelectorDialogView (BasePanel basePanel)
specifier|public
name|ContentSelectorDialogView
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
literal|"Manage content selectors"
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|getDialogPane
argument_list|()
operator|.
name|setPrefSize
argument_list|(
literal|375
argument_list|,
literal|475
argument_list|)
expr_stmt|;
name|this
operator|.
name|basePanel
operator|=
name|basePanel
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
name|saveButton
argument_list|,
name|getDialogPane
argument_list|()
argument_list|,
name|event
lambda|->
name|saveChangesAndClose
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
name|viewModel
operator|=
operator|new
name|ContentSelectorDialogViewModel
argument_list|(
name|basePanel
argument_list|,
name|dialogService
argument_list|)
expr_stmt|;
name|initFieldNameComponents
argument_list|()
expr_stmt|;
name|initKeywordsComponents
argument_list|()
expr_stmt|;
block|}
DECL|method|initFieldNameComponents ()
specifier|private
name|void
name|initFieldNameComponents
parameter_list|()
block|{
name|initListView
argument_list|(
name|fieldNamesListView
argument_list|,
name|viewModel
operator|::
name|getFieldNamesBackingList
argument_list|)
expr_stmt|;
name|viewModel
operator|.
name|selectedFieldNameProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|fieldNamesListView
operator|.
name|getSelectionModel
argument_list|()
operator|.
name|selectedItemProperty
argument_list|()
argument_list|)
expr_stmt|;
name|removeFieldNameButton
operator|.
name|disableProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|viewModel
operator|.
name|isNoFieldNameSelected
argument_list|()
argument_list|)
expr_stmt|;
name|EasyBind
operator|.
name|subscribe
argument_list|(
name|viewModel
operator|.
name|selectedFieldNameProperty
argument_list|()
argument_list|,
name|viewModel
operator|::
name|populateKeywords
argument_list|)
expr_stmt|;
block|}
DECL|method|initKeywordsComponents ()
specifier|private
name|void
name|initKeywordsComponents
parameter_list|()
block|{
name|initListView
argument_list|(
name|keywordsListView
argument_list|,
name|viewModel
operator|::
name|getKeywordsBackingList
argument_list|)
expr_stmt|;
name|viewModel
operator|.
name|selectedKeywordProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|keywordsListView
operator|.
name|getSelectionModel
argument_list|()
operator|.
name|selectedItemProperty
argument_list|()
argument_list|)
expr_stmt|;
name|addKeywordButton
operator|.
name|disableProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|viewModel
operator|.
name|isFieldNameListEmpty
argument_list|()
argument_list|)
expr_stmt|;
name|removeKeywordButton
operator|.
name|disableProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|viewModel
operator|.
name|isNoKeywordSelected
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|FXML
DECL|method|addNewFieldName ()
specifier|private
name|void
name|addNewFieldName
parameter_list|()
block|{
name|viewModel
operator|.
name|showInputFieldNameDialog
argument_list|()
expr_stmt|;
block|}
annotation|@
name|FXML
DECL|method|removeFieldName ()
specifier|private
name|void
name|removeFieldName
parameter_list|()
block|{
name|getSelectedFieldName
argument_list|()
operator|.
name|ifPresent
argument_list|(
name|viewModel
operator|::
name|showRemoveFieldNameConfirmationDialog
argument_list|)
expr_stmt|;
block|}
annotation|@
name|FXML
DECL|method|addNewKeyword ()
specifier|private
name|void
name|addNewKeyword
parameter_list|()
block|{
name|getSelectedFieldName
argument_list|()
operator|.
name|ifPresent
argument_list|(
name|viewModel
operator|::
name|showInputKeywordDialog
argument_list|)
expr_stmt|;
block|}
annotation|@
name|FXML
DECL|method|removeKeyword ()
specifier|private
name|void
name|removeKeyword
parameter_list|()
block|{
name|Optional
argument_list|<
name|String
argument_list|>
name|fieldName
init|=
name|getSelectedFieldName
argument_list|()
decl_stmt|;
name|Optional
argument_list|<
name|String
argument_list|>
name|keywordToRemove
init|=
name|getSelectedKeyword
argument_list|()
decl_stmt|;
if|if
condition|(
name|fieldName
operator|.
name|isPresent
argument_list|()
operator|&&
name|keywordToRemove
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|viewModel
operator|.
name|showRemoveKeywordConfirmationDialog
argument_list|(
name|fieldName
operator|.
name|get
argument_list|()
argument_list|,
name|keywordToRemove
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|initListView (ListView<String> listViewToInit, Supplier<ListProperty<String>> backingList)
specifier|private
name|void
name|initListView
parameter_list|(
name|ListView
argument_list|<
name|String
argument_list|>
name|listViewToInit
parameter_list|,
name|Supplier
argument_list|<
name|ListProperty
argument_list|<
name|String
argument_list|>
argument_list|>
name|backingList
parameter_list|)
block|{
name|listViewToInit
operator|.
name|itemsProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|backingList
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
name|listViewToInit
operator|.
name|getSelectionModel
argument_list|()
operator|.
name|selectFirst
argument_list|()
expr_stmt|;
block|}
DECL|method|getSelectedFieldName ()
specifier|private
name|Optional
argument_list|<
name|String
argument_list|>
name|getSelectedFieldName
parameter_list|()
block|{
return|return
name|Optional
operator|.
name|of
argument_list|(
name|fieldNamesListView
operator|.
name|getSelectionModel
argument_list|()
argument_list|)
operator|.
name|map
argument_list|(
name|SelectionModel
operator|::
name|getSelectedItem
argument_list|)
return|;
block|}
DECL|method|getSelectedKeyword ()
specifier|private
name|Optional
argument_list|<
name|String
argument_list|>
name|getSelectedKeyword
parameter_list|()
block|{
return|return
name|Optional
operator|.
name|of
argument_list|(
name|keywordsListView
operator|.
name|getSelectionModel
argument_list|()
argument_list|)
operator|.
name|map
argument_list|(
name|SelectionModel
operator|::
name|getSelectedItem
argument_list|)
return|;
block|}
DECL|method|saveChangesAndClose ()
specifier|private
name|void
name|saveChangesAndClose
parameter_list|()
block|{
name|viewModel
operator|.
name|saveChanges
argument_list|()
expr_stmt|;
name|close
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit
