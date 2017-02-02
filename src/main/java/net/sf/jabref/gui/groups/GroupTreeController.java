begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.groups
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|groups
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
name|css
operator|.
name|PseudoClass
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
name|control
operator|.
name|ContextMenu
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
name|Control
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
name|TreeTableRow
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
name|javafx
operator|.
name|scene
operator|.
name|layout
operator|.
name|StackPane
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|text
operator|.
name|Text
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|AbstractController
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|StateManager
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
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
name|net
operator|.
name|sf
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
name|net
operator|.
name|sf
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
name|fxmisc
operator|.
name|easybind
operator|.
name|EasyBind
import|;
end_import

begin_class
DECL|class|GroupTreeController
specifier|public
class|class
name|GroupTreeController
extends|extends
name|AbstractController
argument_list|<
name|GroupTreeViewModel
argument_list|>
block|{
DECL|field|groupTree
annotation|@
name|FXML
specifier|private
name|TreeTableView
argument_list|<
name|GroupNodeViewModel
argument_list|>
name|groupTree
decl_stmt|;
DECL|field|mainColumn
annotation|@
name|FXML
specifier|private
name|TreeTableColumn
argument_list|<
name|GroupNodeViewModel
argument_list|,
name|GroupNodeViewModel
argument_list|>
name|mainColumn
decl_stmt|;
DECL|field|numberColumn
annotation|@
name|FXML
specifier|private
name|TreeTableColumn
argument_list|<
name|GroupNodeViewModel
argument_list|,
name|GroupNodeViewModel
argument_list|>
name|numberColumn
decl_stmt|;
DECL|field|disclosureNodeColumn
annotation|@
name|FXML
specifier|private
name|TreeTableColumn
argument_list|<
name|GroupNodeViewModel
argument_list|,
name|GroupNodeViewModel
argument_list|>
name|disclosureNodeColumn
decl_stmt|;
DECL|field|stateManager
annotation|@
name|Inject
specifier|private
name|StateManager
name|stateManager
decl_stmt|;
DECL|field|dialogService
annotation|@
name|Inject
specifier|private
name|DialogService
name|dialogService
decl_stmt|;
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
name|GroupTreeViewModel
argument_list|(
name|stateManager
argument_list|,
name|dialogService
argument_list|)
expr_stmt|;
comment|// Set-up bindings
name|groupTree
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
name|rootGroupProperty
argument_list|()
argument_list|,
name|group
lambda|->
operator|new
name|RecursiveTreeItem
argument_list|<>
argument_list|(
name|group
argument_list|,
name|GroupNodeViewModel
operator|::
name|getChildren
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|viewModel
operator|.
name|selectedGroupProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|EasyBind
operator|.
name|monadic
argument_list|(
name|groupTree
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
comment|// Icon and group name
name|mainColumn
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
name|valueProperty
argument_list|()
argument_list|)
expr_stmt|;
name|mainColumn
operator|.
name|setCellFactory
argument_list|(
operator|new
name|ViewModelTreeTableCellFactory
argument_list|<
name|GroupNodeViewModel
argument_list|,
name|GroupNodeViewModel
argument_list|>
argument_list|()
operator|.
name|withText
argument_list|(
name|GroupNodeViewModel
operator|::
name|getName
argument_list|)
operator|.
name|withIcon
argument_list|(
name|GroupNodeViewModel
operator|::
name|getIconCode
argument_list|)
operator|.
name|withTooltip
argument_list|(
name|GroupNodeViewModel
operator|::
name|getDescription
argument_list|)
argument_list|)
expr_stmt|;
comment|// Number of hits
name|numberColumn
operator|.
name|setCellFactory
argument_list|(
operator|new
name|ViewModelTreeTableCellFactory
argument_list|<
name|GroupNodeViewModel
argument_list|,
name|GroupNodeViewModel
argument_list|>
argument_list|()
operator|.
name|withGraphic
argument_list|(
name|viewModel
lambda|->
block|{
specifier|final
name|StackPane
name|node
init|=
operator|new
name|StackPane
argument_list|()
decl_stmt|;
name|node
operator|.
name|getStyleClass
argument_list|()
operator|.
name|setAll
argument_list|(
literal|"hits"
argument_list|)
expr_stmt|;
name|Text
name|text
init|=
operator|new
name|Text
argument_list|()
decl_stmt|;
name|text
operator|.
name|textProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|viewModel
operator|.
name|getHits
argument_list|()
operator|.
name|asString
argument_list|()
argument_list|)
expr_stmt|;
name|text
operator|.
name|getStyleClass
argument_list|()
operator|.
name|setAll
argument_list|(
literal|"text"
argument_list|)
expr_stmt|;
name|node
operator|.
name|getChildren
argument_list|()
operator|.
name|add
argument_list|(
name|text
argument_list|)
expr_stmt|;
name|node
operator|.
name|setMaxWidth
argument_list|(
name|Control
operator|.
name|USE_PREF_SIZE
argument_list|)
expr_stmt|;
return|return
name|node
return|;
block|}
argument_list|)
argument_list|)
expr_stmt|;
comment|// Arrow indicating expanded status
name|disclosureNodeColumn
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
name|valueProperty
argument_list|()
argument_list|)
expr_stmt|;
name|disclosureNodeColumn
operator|.
name|setCellFactory
argument_list|(
operator|new
name|ViewModelTreeTableCellFactory
argument_list|<
name|GroupNodeViewModel
argument_list|,
name|GroupNodeViewModel
argument_list|>
argument_list|()
operator|.
name|withGraphic
argument_list|(
name|viewModel
lambda|->
block|{
specifier|final
name|StackPane
name|disclosureNode
init|=
operator|new
name|StackPane
argument_list|()
decl_stmt|;
name|disclosureNode
operator|.
name|visibleProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|viewModel
operator|.
name|hasChildrenProperty
argument_list|()
argument_list|)
expr_stmt|;
name|disclosureNode
operator|.
name|getStyleClass
argument_list|()
operator|.
name|setAll
argument_list|(
literal|"tree-disclosure-node"
argument_list|)
expr_stmt|;
specifier|final
name|StackPane
name|disclosureNodeArrow
init|=
operator|new
name|StackPane
argument_list|()
decl_stmt|;
name|disclosureNodeArrow
operator|.
name|getStyleClass
argument_list|()
operator|.
name|setAll
argument_list|(
literal|"arrow"
argument_list|)
expr_stmt|;
name|disclosureNode
operator|.
name|getChildren
argument_list|()
operator|.
name|add
argument_list|(
name|disclosureNodeArrow
argument_list|)
expr_stmt|;
return|return
name|disclosureNode
return|;
block|}
argument_list|)
argument_list|)
expr_stmt|;
comment|// Set pseudo-classes to indicate if row is root or sub-item (> 1 deep)
name|PseudoClass
name|rootPseudoClass
init|=
name|PseudoClass
operator|.
name|getPseudoClass
argument_list|(
literal|"root"
argument_list|)
decl_stmt|;
name|PseudoClass
name|subElementPseudoClass
init|=
name|PseudoClass
operator|.
name|getPseudoClass
argument_list|(
literal|"sub"
argument_list|)
decl_stmt|;
name|groupTree
operator|.
name|setRowFactory
argument_list|(
name|treeTable
lambda|->
block|{
name|TreeTableRow
argument_list|<
name|GroupNodeViewModel
argument_list|>
name|row
init|=
operator|new
name|TreeTableRow
argument_list|<>
argument_list|()
decl_stmt|;
name|row
operator|.
name|treeItemProperty
argument_list|()
operator|.
name|addListener
argument_list|(
parameter_list|(
name|ov
parameter_list|,
name|oldTreeItem
parameter_list|,
name|newTreeItem
parameter_list|)
lambda|->
block|{
name|boolean
name|isRoot
init|=
name|newTreeItem
operator|==
name|treeTable
operator|.
name|getRoot
argument_list|()
decl_stmt|;
name|row
operator|.
name|pseudoClassStateChanged
argument_list|(
name|rootPseudoClass
argument_list|,
name|isRoot
argument_list|)
expr_stmt|;
name|boolean
name|isFirstLevel
init|=
name|newTreeItem
operator|!=
literal|null
operator|&&
name|newTreeItem
operator|.
name|getParent
argument_list|()
operator|==
name|treeTable
operator|.
name|getRoot
argument_list|()
decl_stmt|;
name|row
operator|.
name|pseudoClassStateChanged
argument_list|(
name|subElementPseudoClass
argument_list|,
operator|!
name|isRoot
operator|&&
operator|!
name|isFirstLevel
argument_list|)
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
comment|// Remove disclosure node since we display custom version in separate column
comment|// Simply setting to null is not enough since it would be replaced by the default node on every change
name|row
operator|.
name|setDisclosureNode
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|row
operator|.
name|disclosureNodeProperty
argument_list|()
operator|.
name|addListener
argument_list|(
parameter_list|(
name|observable
parameter_list|,
name|oldValue
parameter_list|,
name|newValue
parameter_list|)
lambda|->
name|row
operator|.
name|setDisclosureNode
argument_list|(
literal|null
argument_list|)
argument_list|)
expr_stmt|;
comment|// Add context menu (only for non-null items)
name|row
operator|.
name|contextMenuProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|EasyBind
operator|.
name|monadic
argument_list|(
name|row
operator|.
name|itemProperty
argument_list|()
argument_list|)
operator|.
name|map
argument_list|(
name|this
operator|::
name|createContextMenuForGroup
argument_list|)
operator|.
name|orElse
argument_list|(
operator|(
name|ContextMenu
operator|)
literal|null
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|row
return|;
block|}
argument_list|)
expr_stmt|;
block|}
DECL|method|createContextMenuForGroup (GroupNodeViewModel group)
specifier|private
name|ContextMenu
name|createContextMenuForGroup
parameter_list|(
name|GroupNodeViewModel
name|group
parameter_list|)
block|{
name|ContextMenu
name|menu
init|=
operator|new
name|ContextMenu
argument_list|()
decl_stmt|;
name|MenuItem
name|addSubgroup
init|=
operator|new
name|MenuItem
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Add subgroup"
argument_list|)
argument_list|)
decl_stmt|;
name|addSubgroup
operator|.
name|setOnAction
argument_list|(
name|event
lambda|->
name|viewModel
operator|.
name|addNewSubgroup
argument_list|(
name|group
argument_list|)
argument_list|)
expr_stmt|;
name|menu
operator|.
name|getItems
argument_list|()
operator|.
name|add
argument_list|(
name|addSubgroup
argument_list|)
expr_stmt|;
return|return
name|menu
return|;
block|}
DECL|method|addNewGroup (ActionEvent actionEvent)
specifier|public
name|void
name|addNewGroup
parameter_list|(
name|ActionEvent
name|actionEvent
parameter_list|)
block|{
name|viewModel
operator|.
name|addNewGroupToRoot
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

