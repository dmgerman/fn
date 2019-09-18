begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.keyboard
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|keyboard
package|;
end_package

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
name|SelectionMode
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
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|TreeItem
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
name|TreeTableColumn
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
name|TreeTableView
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
name|icon
operator|.
name|JabRefIcon
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
name|RecursiveTreeItem
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
name|ViewModelTreeTableCellFactory
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
DECL|class|KeyBindingsDialogView
specifier|public
class|class
name|KeyBindingsDialogView
extends|extends
name|BaseDialog
argument_list|<
name|Void
argument_list|>
block|{
DECL|field|resetButton
annotation|@
name|FXML
specifier|private
name|ButtonType
name|resetButton
decl_stmt|;
DECL|field|saveButton
annotation|@
name|FXML
specifier|private
name|ButtonType
name|saveButton
decl_stmt|;
DECL|field|keyBindingsTable
annotation|@
name|FXML
specifier|private
name|TreeTableView
argument_list|<
name|KeyBindingViewModel
argument_list|>
name|keyBindingsTable
decl_stmt|;
DECL|field|actionColumn
annotation|@
name|FXML
specifier|private
name|TreeTableColumn
argument_list|<
name|KeyBindingViewModel
argument_list|,
name|String
argument_list|>
name|actionColumn
decl_stmt|;
DECL|field|shortcutColumn
annotation|@
name|FXML
specifier|private
name|TreeTableColumn
argument_list|<
name|KeyBindingViewModel
argument_list|,
name|String
argument_list|>
name|shortcutColumn
decl_stmt|;
DECL|field|resetColumn
annotation|@
name|FXML
specifier|private
name|TreeTableColumn
argument_list|<
name|KeyBindingViewModel
argument_list|,
name|KeyBindingViewModel
argument_list|>
name|resetColumn
decl_stmt|;
DECL|field|keyBindingRepository
annotation|@
name|Inject
specifier|private
name|KeyBindingRepository
name|keyBindingRepository
decl_stmt|;
DECL|field|dialogService
annotation|@
name|Inject
specifier|private
name|DialogService
name|dialogService
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
name|KeyBindingsDialogViewModel
name|viewModel
decl_stmt|;
DECL|method|KeyBindingsDialogView ()
specifier|public
name|KeyBindingsDialogView
parameter_list|()
block|{
name|this
operator|.
name|setTitle
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Key bindings"
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
name|resetButton
argument_list|,
name|getDialogPane
argument_list|()
argument_list|,
name|event
lambda|->
name|setDefaultBindings
argument_list|()
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
name|saveKeyBindingsAndCloseDialog
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|FXML
DECL|method|initialize ()
specifier|private
name|void
name|initialize
parameter_list|()
block|{
name|viewModel
operator|=
operator|new
name|KeyBindingsDialogViewModel
argument_list|(
name|keyBindingRepository
argument_list|,
name|dialogService
argument_list|,
name|preferences
argument_list|)
expr_stmt|;
name|keyBindingsTable
operator|.
name|getSelectionModel
argument_list|()
operator|.
name|setSelectionMode
argument_list|(
name|SelectionMode
operator|.
name|SINGLE
argument_list|)
expr_stmt|;
name|viewModel
operator|.
name|selectedKeyBindingProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|EasyBind
operator|.
name|monadic
argument_list|(
name|keyBindingsTable
operator|.
name|selectionModelProperty
argument_list|()
argument_list|)
operator|.
name|flatMap
argument_list|(
name|SelectionModel
operator|::
name|selectedItemProperty
argument_list|)
operator|.
name|selectProperty
argument_list|(
name|TreeItem
operator|::
name|valueProperty
argument_list|)
argument_list|)
expr_stmt|;
name|keyBindingsTable
operator|.
name|setOnKeyPressed
argument_list|(
name|evt
lambda|->
name|viewModel
operator|.
name|setNewBindingForCurrent
argument_list|(
name|evt
argument_list|)
argument_list|)
expr_stmt|;
name|keyBindingsTable
operator|.
name|rootProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|EasyBind
operator|.
name|map
argument_list|(
name|viewModel
operator|.
name|rootKeyBindingProperty
argument_list|()
argument_list|,
name|keybinding
lambda|->
operator|new
name|RecursiveTreeItem
argument_list|<>
argument_list|(
name|keybinding
argument_list|,
name|KeyBindingViewModel
operator|::
name|getChildren
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|actionColumn
operator|.
name|setCellValueFactory
argument_list|(
name|cellData
lambda|->
name|cellData
operator|.
name|getValue
argument_list|()
operator|.
name|getValue
argument_list|()
operator|.
name|nameProperty
argument_list|()
argument_list|)
expr_stmt|;
name|shortcutColumn
operator|.
name|setCellValueFactory
argument_list|(
name|cellData
lambda|->
name|cellData
operator|.
name|getValue
argument_list|()
operator|.
name|getValue
argument_list|()
operator|.
name|shownBindingProperty
argument_list|()
argument_list|)
expr_stmt|;
operator|new
name|ViewModelTreeTableCellFactory
argument_list|<
name|KeyBindingViewModel
argument_list|>
argument_list|()
operator|.
name|withGraphic
argument_list|(
name|keyBinding
lambda|->
name|keyBinding
operator|.
name|getIcon
argument_list|()
operator|.
name|map
argument_list|(
name|JabRefIcon
operator|::
name|getGraphicNode
argument_list|)
operator|.
name|orElse
argument_list|(
literal|null
argument_list|)
argument_list|)
operator|.
name|withOnMouseClickedEvent
argument_list|(
name|keyBinding
lambda|->
name|evt
lambda|->
name|keyBinding
operator|.
name|resetToDefault
argument_list|()
argument_list|)
operator|.
name|install
argument_list|(
name|resetColumn
argument_list|)
expr_stmt|;
block|}
annotation|@
name|FXML
DECL|method|closeDialog ()
specifier|private
name|void
name|closeDialog
parameter_list|()
block|{
name|close
argument_list|()
expr_stmt|;
block|}
annotation|@
name|FXML
DECL|method|saveKeyBindingsAndCloseDialog ()
specifier|private
name|void
name|saveKeyBindingsAndCloseDialog
parameter_list|()
block|{
name|viewModel
operator|.
name|saveKeyBindings
argument_list|()
expr_stmt|;
name|closeDialog
argument_list|()
expr_stmt|;
block|}
annotation|@
name|FXML
DECL|method|setDefaultBindings ()
specifier|private
name|void
name|setDefaultBindings
parameter_list|()
block|{
name|viewModel
operator|.
name|resetToDefault
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

