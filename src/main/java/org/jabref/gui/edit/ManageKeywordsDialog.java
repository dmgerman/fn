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
name|java
operator|.
name|util
operator|.
name|List
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
name|TableColumn
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
name|TableView
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
name|control
operator|.
name|cell
operator|.
name|TextFieldTableCell
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
name|icon
operator|.
name|IconTheme
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
name|BindingsHelper
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
name|ValueTableCellFactory
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
DECL|class|ManageKeywordsDialog
specifier|public
class|class
name|ManageKeywordsDialog
extends|extends
name|BaseDialog
argument_list|<
name|Void
argument_list|>
block|{
DECL|field|entries
specifier|private
specifier|final
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
decl_stmt|;
DECL|field|keywordsTableMainColumn
annotation|@
name|FXML
specifier|private
name|TableColumn
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|keywordsTableMainColumn
decl_stmt|;
DECL|field|keywordsTableEditColumn
annotation|@
name|FXML
specifier|private
name|TableColumn
argument_list|<
name|String
argument_list|,
name|Boolean
argument_list|>
name|keywordsTableEditColumn
decl_stmt|;
DECL|field|keywordsTableDeleteColumn
annotation|@
name|FXML
specifier|private
name|TableColumn
argument_list|<
name|String
argument_list|,
name|Boolean
argument_list|>
name|keywordsTableDeleteColumn
decl_stmt|;
DECL|field|keywordsTable
annotation|@
name|FXML
specifier|private
name|TableView
argument_list|<
name|String
argument_list|>
name|keywordsTable
decl_stmt|;
DECL|field|displayType
annotation|@
name|FXML
specifier|private
name|ToggleGroup
name|displayType
decl_stmt|;
DECL|field|preferences
annotation|@
name|Inject
specifier|private
name|PreferencesService
name|preferences
decl_stmt|;
DECL|field|viewModel
specifier|private
name|ManageKeywordsViewModel
name|viewModel
decl_stmt|;
DECL|method|ManageKeywordsDialog (List<BibEntry> entries)
specifier|public
name|ManageKeywordsDialog
parameter_list|(
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
parameter_list|)
block|{
name|this
operator|.
name|entries
operator|=
name|entries
expr_stmt|;
name|this
operator|.
name|setTitle
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Manage keywords"
argument_list|)
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
name|setResultConverter
argument_list|(
name|button
lambda|->
block|{
if|if
condition|(
name|button
operator|==
name|ButtonType
operator|.
name|APPLY
condition|)
block|{
name|viewModel
operator|.
name|saveChanges
argument_list|()
expr_stmt|;
block|}
return|return
literal|null
return|;
block|}
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
name|ManageKeywordsViewModel
argument_list|(
name|preferences
argument_list|,
name|entries
argument_list|)
expr_stmt|;
name|viewModel
operator|.
name|displayTypeProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|EasyBind
operator|.
name|map
argument_list|(
name|displayType
operator|.
name|selectedToggleProperty
argument_list|()
argument_list|,
name|toggle
lambda|->
block|{
if|if
condition|(
name|toggle
operator|!=
literal|null
condition|)
block|{
return|return
operator|(
name|ManageKeywordsDisplayType
operator|)
name|toggle
operator|.
name|getUserData
argument_list|()
return|;
block|}
else|else
block|{
return|return
name|ManageKeywordsDisplayType
operator|.
name|CONTAINED_IN_ALL_ENTRIES
return|;
block|}
block|}
argument_list|)
argument_list|)
expr_stmt|;
name|keywordsTable
operator|.
name|setItems
argument_list|(
name|viewModel
operator|.
name|getKeywords
argument_list|()
argument_list|)
expr_stmt|;
name|keywordsTableMainColumn
operator|.
name|setCellValueFactory
argument_list|(
name|data
lambda|->
name|BindingsHelper
operator|.
name|constantOf
argument_list|(
name|data
operator|.
name|getValue
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|keywordsTableMainColumn
operator|.
name|setOnEditCommit
argument_list|(
name|event
lambda|->
block|{
comment|// Poor mans reverse databinding (necessary because we use a constant value above)
name|viewModel
operator|.
name|getKeywords
argument_list|()
operator|.
name|set
argument_list|(
name|event
operator|.
name|getTablePosition
argument_list|()
operator|.
name|getRow
argument_list|()
argument_list|,
name|event
operator|.
name|getNewValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
name|keywordsTableMainColumn
operator|.
name|setCellFactory
argument_list|(
name|TextFieldTableCell
operator|.
name|forTableColumn
argument_list|()
argument_list|)
expr_stmt|;
name|keywordsTableEditColumn
operator|.
name|setCellValueFactory
argument_list|(
name|data
lambda|->
name|BindingsHelper
operator|.
name|constantOf
argument_list|(
literal|true
argument_list|)
argument_list|)
expr_stmt|;
name|keywordsTableDeleteColumn
operator|.
name|setCellValueFactory
argument_list|(
name|data
lambda|->
name|BindingsHelper
operator|.
name|constantOf
argument_list|(
literal|true
argument_list|)
argument_list|)
expr_stmt|;
operator|new
name|ValueTableCellFactory
argument_list|<
name|String
argument_list|,
name|Boolean
argument_list|>
argument_list|()
operator|.
name|withGraphic
argument_list|(
name|none
lambda|->
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|EDIT
operator|.
name|getGraphicNode
argument_list|()
argument_list|)
operator|.
name|withOnMouseClickedEvent
argument_list|(
name|none
lambda|->
name|event
lambda|->
name|keywordsTable
operator|.
name|edit
argument_list|(
name|keywordsTable
operator|.
name|getFocusModel
argument_list|()
operator|.
name|getFocusedIndex
argument_list|()
argument_list|,
name|keywordsTableMainColumn
argument_list|)
argument_list|)
operator|.
name|install
argument_list|(
name|keywordsTableEditColumn
argument_list|)
expr_stmt|;
operator|new
name|ValueTableCellFactory
argument_list|<
name|String
argument_list|,
name|Boolean
argument_list|>
argument_list|()
operator|.
name|withGraphic
argument_list|(
name|none
lambda|->
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|REMOVE
operator|.
name|getGraphicNode
argument_list|()
argument_list|)
operator|.
name|withOnMouseClickedEvent
argument_list|(
parameter_list|(
name|keyword
parameter_list|,
name|none
parameter_list|)
lambda|->
name|event
lambda|->
name|viewModel
operator|.
name|removeKeyword
argument_list|(
name|keyword
argument_list|)
argument_list|)
operator|.
name|install
argument_list|(
name|keywordsTableDeleteColumn
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

