begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.groups
package|package
name|org
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
name|java
operator|.
name|lang
operator|.
name|reflect
operator|.
name|InvocationTargetException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|lang
operator|.
name|reflect
operator|.
name|Method
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
name|java
operator|.
name|util
operator|.
name|function
operator|.
name|Consumer
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|stream
operator|.
name|Collectors
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
name|ObjectProperty
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|collections
operator|.
name|ObservableList
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
name|SeparatorMenuItem
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
name|TextField
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
name|input
operator|.
name|ClipboardContent
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|input
operator|.
name|DragEvent
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|input
operator|.
name|Dragboard
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|input
operator|.
name|TransferMode
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
name|org
operator|.
name|jabref
operator|.
name|Globals
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
name|AbstractController
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
name|DragAndDropDataFormats
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
name|StateManager
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
name|TaskExecutor
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
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|Log
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|LogFactory
import|;
end_import

begin_import
import|import
name|org
operator|.
name|controlsfx
operator|.
name|control
operator|.
name|textfield
operator|.
name|CustomTextField
import|;
end_import

begin_import
import|import
name|org
operator|.
name|controlsfx
operator|.
name|control
operator|.
name|textfield
operator|.
name|TextFields
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
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Log
name|LOGGER
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|GroupTreeController
operator|.
name|class
argument_list|)
decl_stmt|;
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
DECL|field|searchField
annotation|@
name|FXML
specifier|private
name|CustomTextField
name|searchField
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
DECL|field|taskExecutor
annotation|@
name|Inject
specifier|private
name|TaskExecutor
name|taskExecutor
decl_stmt|;
DECL|method|removePseudoClasses (TreeTableRow<GroupNodeViewModel> row, PseudoClass... pseudoClasses)
specifier|private
specifier|static
name|void
name|removePseudoClasses
parameter_list|(
name|TreeTableRow
argument_list|<
name|GroupNodeViewModel
argument_list|>
name|row
parameter_list|,
name|PseudoClass
modifier|...
name|pseudoClasses
parameter_list|)
block|{
for|for
control|(
name|PseudoClass
name|pseudoClass
range|:
name|pseudoClasses
control|)
block|{
name|row
operator|.
name|pseudoClassStateChanged
argument_list|(
name|pseudoClass
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
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
name|GroupTreeViewModel
argument_list|(
name|stateManager
argument_list|,
name|dialogService
argument_list|,
name|taskExecutor
argument_list|)
expr_stmt|;
comment|// Set-up groups tree
name|groupTree
operator|.
name|setStyle
argument_list|(
literal|"-fx-font-size: "
operator|+
name|Globals
operator|.
name|prefs
operator|.
name|getFontSizeFX
argument_list|()
operator|+
literal|"pt;"
argument_list|)
expr_stmt|;
name|groupTree
operator|.
name|getSelectionModel
argument_list|()
operator|.
name|setSelectionMode
argument_list|(
name|SelectionMode
operator|.
name|MULTIPLE
argument_list|)
expr_stmt|;
comment|// Set-up bindings
name|Consumer
argument_list|<
name|ObservableList
argument_list|<
name|GroupNodeViewModel
argument_list|>
argument_list|>
name|updateSelectedGroups
init|=
parameter_list|(
name|newSelectedGroups
parameter_list|)
lambda|->
name|newSelectedGroups
operator|.
name|forEach
argument_list|(
name|this
operator|::
name|selectNode
argument_list|)
decl_stmt|;
name|Consumer
argument_list|<
name|List
argument_list|<
name|TreeItem
argument_list|<
name|GroupNodeViewModel
argument_list|>
argument_list|>
argument_list|>
name|updateViewModel
init|=
parameter_list|(
name|newSelectedGroups
parameter_list|)
lambda|->
block|{
try|try
block|{
name|viewModel
operator|.
name|selectedGroupsProperty
argument_list|()
operator|.
name|setAll
argument_list|(
name|newSelectedGroups
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|TreeItem
operator|::
name|getValue
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NullPointerException
name|e
parameter_list|)
block|{
name|viewModel
operator|.
name|selectedGroupsProperty
argument_list|()
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
block|}
decl_stmt|;
name|BindingsHelper
operator|.
name|bindContentBidirectional
argument_list|(
name|groupTree
operator|.
name|getSelectionModel
argument_list|()
operator|.
name|getSelectedItems
argument_list|()
argument_list|,
name|viewModel
operator|.
name|selectedGroupsProperty
argument_list|()
argument_list|,
name|updateSelectedGroups
argument_list|,
name|updateViewModel
argument_list|)
expr_stmt|;
name|viewModel
operator|.
name|filterTextProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|searchField
operator|.
name|textProperty
argument_list|()
argument_list|)
expr_stmt|;
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
argument_list|,
name|GroupNodeViewModel
operator|::
name|expandedProperty
argument_list|,
name|viewModel
operator|.
name|filterPredicateProperty
argument_list|()
argument_list|)
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
name|getDisplayName
argument_list|)
operator|.
name|withIcon
argument_list|(
name|GroupNodeViewModel
operator|::
name|getIcon
argument_list|,
name|GroupNodeViewModel
operator|::
name|getColor
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
name|PseudoClass
name|anySelected
init|=
name|PseudoClass
operator|.
name|getPseudoClass
argument_list|(
literal|"any-selected"
argument_list|)
decl_stmt|;
name|PseudoClass
name|allSelected
init|=
name|PseudoClass
operator|.
name|getPseudoClass
argument_list|(
literal|"all-selected"
argument_list|)
decl_stmt|;
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
name|group
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
if|if
condition|(
operator|!
name|group
operator|.
name|isRoot
argument_list|()
condition|)
block|{
name|BindingsHelper
operator|.
name|includePseudoClassWhen
argument_list|(
name|node
argument_list|,
name|anySelected
argument_list|,
name|group
operator|.
name|anySelectedEntriesMatchedProperty
argument_list|()
argument_list|)
expr_stmt|;
name|BindingsHelper
operator|.
name|includePseudoClassWhen
argument_list|(
name|node
argument_list|,
name|allSelected
argument_list|,
name|group
operator|.
name|allSelectedEntriesMatchedProperty
argument_list|()
argument_list|)
expr_stmt|;
block|}
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
name|group
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
operator|.
name|withOnMouseClickedEvent
argument_list|(
name|group
lambda|->
name|event
lambda|->
name|group
operator|.
name|toggleExpansion
argument_list|()
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
comment|// Pseudo-classes for drag and drop
name|PseudoClass
name|dragOverBottom
init|=
name|PseudoClass
operator|.
name|getPseudoClass
argument_list|(
literal|"dragOver-bottom"
argument_list|)
decl_stmt|;
name|PseudoClass
name|dragOverCenter
init|=
name|PseudoClass
operator|.
name|getPseudoClass
argument_list|(
literal|"dragOver-center"
argument_list|)
decl_stmt|;
name|PseudoClass
name|dragOverTop
init|=
name|PseudoClass
operator|.
name|getPseudoClass
argument_list|(
literal|"dragOver-top"
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
operator|(
name|newTreeItem
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|newTreeItem
operator|.
name|getParent
argument_list|()
operator|==
name|treeTable
operator|.
name|getRoot
argument_list|()
operator|)
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
comment|// Drag and drop support
name|row
operator|.
name|setOnDragDetected
argument_list|(
name|event
lambda|->
block|{
name|TreeItem
argument_list|<
name|GroupNodeViewModel
argument_list|>
name|selectedItem
init|=
name|treeTable
operator|.
name|getSelectionModel
argument_list|()
operator|.
name|getSelectedItem
argument_list|()
decl_stmt|;
if|if
condition|(
operator|(
name|selectedItem
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|selectedItem
operator|.
name|getValue
argument_list|()
operator|!=
literal|null
operator|)
condition|)
block|{
name|Dragboard
name|dragboard
init|=
name|treeTable
operator|.
name|startDragAndDrop
argument_list|(
name|TransferMode
operator|.
name|MOVE
argument_list|)
decl_stmt|;
comment|// Display the group when dragging
name|dragboard
operator|.
name|setDragView
argument_list|(
name|row
operator|.
name|snapshot
argument_list|(
literal|null
argument_list|,
literal|null
argument_list|)
argument_list|)
expr_stmt|;
comment|// Put the group node as content
name|ClipboardContent
name|content
init|=
operator|new
name|ClipboardContent
argument_list|()
decl_stmt|;
name|content
operator|.
name|put
argument_list|(
name|DragAndDropDataFormats
operator|.
name|GROUP
argument_list|,
name|selectedItem
operator|.
name|getValue
argument_list|()
operator|.
name|getPath
argument_list|()
argument_list|)
expr_stmt|;
name|dragboard
operator|.
name|setContent
argument_list|(
name|content
argument_list|)
expr_stmt|;
name|event
operator|.
name|consume
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|row
operator|.
name|setOnDragOver
argument_list|(
name|event
lambda|->
block|{
name|Dragboard
name|dragboard
init|=
name|event
operator|.
name|getDragboard
argument_list|()
decl_stmt|;
if|if
condition|(
operator|(
name|event
operator|.
name|getGestureSource
argument_list|()
operator|!=
name|row
operator|)
operator|&&
name|row
operator|.
name|getItem
argument_list|()
operator|.
name|acceptableDrop
argument_list|(
name|dragboard
argument_list|)
condition|)
block|{
name|event
operator|.
name|acceptTransferModes
argument_list|(
name|TransferMode
operator|.
name|MOVE
argument_list|,
name|TransferMode
operator|.
name|LINK
argument_list|)
expr_stmt|;
name|removePseudoClasses
argument_list|(
name|row
argument_list|,
name|dragOverBottom
argument_list|,
name|dragOverCenter
argument_list|,
name|dragOverTop
argument_list|)
expr_stmt|;
switch|switch
condition|(
name|getDroppingMouseLocation
argument_list|(
name|row
argument_list|,
name|event
argument_list|)
condition|)
block|{
case|case
name|BOTTOM
case|:
name|row
operator|.
name|pseudoClassStateChanged
argument_list|(
name|dragOverBottom
argument_list|,
literal|true
argument_list|)
expr_stmt|;
break|break;
case|case
name|CENTER
case|:
name|row
operator|.
name|pseudoClassStateChanged
argument_list|(
name|dragOverCenter
argument_list|,
literal|true
argument_list|)
expr_stmt|;
break|break;
case|case
name|TOP
case|:
name|row
operator|.
name|pseudoClassStateChanged
argument_list|(
name|dragOverTop
argument_list|,
literal|true
argument_list|)
expr_stmt|;
break|break;
block|}
block|}
name|event
operator|.
name|consume
argument_list|()
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
name|row
operator|.
name|setOnDragExited
argument_list|(
name|event
lambda|->
block|{
name|removePseudoClasses
argument_list|(
name|row
argument_list|,
name|dragOverBottom
argument_list|,
name|dragOverCenter
argument_list|,
name|dragOverTop
argument_list|)
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
name|row
operator|.
name|setOnDragDropped
argument_list|(
name|event
lambda|->
block|{
name|Dragboard
name|dragboard
init|=
name|event
operator|.
name|getDragboard
argument_list|()
decl_stmt|;
name|boolean
name|success
init|=
literal|false
decl_stmt|;
if|if
condition|(
name|dragboard
operator|.
name|hasContent
argument_list|(
name|DragAndDropDataFormats
operator|.
name|GROUP
argument_list|)
condition|)
block|{
name|String
name|pathToSource
init|=
operator|(
name|String
operator|)
name|dragboard
operator|.
name|getContent
argument_list|(
name|DragAndDropDataFormats
operator|.
name|GROUP
argument_list|)
decl_stmt|;
name|Optional
argument_list|<
name|GroupNodeViewModel
argument_list|>
name|source
init|=
name|viewModel
operator|.
name|rootGroupProperty
argument_list|()
operator|.
name|get
argument_list|()
operator|.
name|getChildByPath
argument_list|(
name|pathToSource
argument_list|)
decl_stmt|;
if|if
condition|(
name|source
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|source
operator|.
name|get
argument_list|()
operator|.
name|draggedOn
argument_list|(
name|row
operator|.
name|getItem
argument_list|()
argument_list|,
name|getDroppingMouseLocation
argument_list|(
name|row
argument_list|,
name|event
argument_list|)
argument_list|)
expr_stmt|;
name|success
operator|=
literal|true
expr_stmt|;
block|}
block|}
if|if
condition|(
name|dragboard
operator|.
name|hasContent
argument_list|(
name|DragAndDropDataFormats
operator|.
name|ENTRIES
argument_list|)
condition|)
block|{
name|TransferableEntrySelection
name|entrySelection
init|=
operator|(
name|TransferableEntrySelection
operator|)
name|dragboard
operator|.
name|getContent
argument_list|(
name|DragAndDropDataFormats
operator|.
name|ENTRIES
argument_list|)
decl_stmt|;
name|row
operator|.
name|getItem
argument_list|()
operator|.
name|addEntriesToGroup
argument_list|(
name|entrySelection
operator|.
name|getSelection
argument_list|()
argument_list|)
expr_stmt|;
name|success
operator|=
literal|true
expr_stmt|;
block|}
name|event
operator|.
name|setDropCompleted
argument_list|(
name|success
argument_list|)
expr_stmt|;
name|event
operator|.
name|consume
argument_list|()
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
return|return
name|row
return|;
block|}
argument_list|)
expr_stmt|;
comment|// Filter text field
name|setupClearButtonField
argument_list|(
name|searchField
argument_list|)
expr_stmt|;
block|}
DECL|method|selectNode (GroupNodeViewModel value)
specifier|private
name|void
name|selectNode
parameter_list|(
name|GroupNodeViewModel
name|value
parameter_list|)
block|{
name|getTreeItemByValue
argument_list|(
name|value
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|treeItem
lambda|->
name|groupTree
operator|.
name|getSelectionModel
argument_list|()
operator|.
name|select
argument_list|(
name|treeItem
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|getTreeItemByValue (GroupNodeViewModel value)
specifier|private
name|Optional
argument_list|<
name|TreeItem
argument_list|<
name|GroupNodeViewModel
argument_list|>
argument_list|>
name|getTreeItemByValue
parameter_list|(
name|GroupNodeViewModel
name|value
parameter_list|)
block|{
return|return
name|getTreeItemByValue
argument_list|(
name|groupTree
operator|.
name|getRoot
argument_list|()
argument_list|,
name|value
argument_list|)
return|;
block|}
DECL|method|getTreeItemByValue (TreeItem<GroupNodeViewModel> root, GroupNodeViewModel value)
specifier|private
name|Optional
argument_list|<
name|TreeItem
argument_list|<
name|GroupNodeViewModel
argument_list|>
argument_list|>
name|getTreeItemByValue
parameter_list|(
name|TreeItem
argument_list|<
name|GroupNodeViewModel
argument_list|>
name|root
parameter_list|,
name|GroupNodeViewModel
name|value
parameter_list|)
block|{
if|if
condition|(
name|root
operator|.
name|getValue
argument_list|()
operator|.
name|equals
argument_list|(
name|value
argument_list|)
condition|)
block|{
return|return
name|Optional
operator|.
name|of
argument_list|(
name|root
argument_list|)
return|;
block|}
for|for
control|(
name|TreeItem
argument_list|<
name|GroupNodeViewModel
argument_list|>
name|child
range|:
name|root
operator|.
name|getChildren
argument_list|()
control|)
block|{
name|Optional
argument_list|<
name|TreeItem
argument_list|<
name|GroupNodeViewModel
argument_list|>
argument_list|>
name|treeItemByValue
init|=
name|getTreeItemByValue
argument_list|(
name|child
argument_list|,
name|value
argument_list|)
decl_stmt|;
if|if
condition|(
name|treeItemByValue
operator|.
name|isPresent
argument_list|()
condition|)
block|{
return|return
name|treeItemByValue
return|;
block|}
block|}
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
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
name|editGroup
init|=
operator|new
name|MenuItem
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Edit group"
argument_list|)
argument_list|)
decl_stmt|;
name|editGroup
operator|.
name|setOnAction
argument_list|(
name|event
lambda|->
block|{
name|menu
operator|.
name|hide
argument_list|()
expr_stmt|;
name|viewModel
operator|.
name|editGroup
argument_list|(
name|group
argument_list|)
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
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
block|{
name|menu
operator|.
name|hide
argument_list|()
expr_stmt|;
name|viewModel
operator|.
name|addNewSubgroup
argument_list|(
name|group
argument_list|)
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
name|MenuItem
name|removeGroupAndSubgroups
init|=
operator|new
name|MenuItem
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Remove group and subgroups"
argument_list|)
argument_list|)
decl_stmt|;
name|removeGroupAndSubgroups
operator|.
name|setOnAction
argument_list|(
name|event
lambda|->
name|viewModel
operator|.
name|removeGroupAndSubgroups
argument_list|(
name|group
argument_list|)
argument_list|)
expr_stmt|;
name|MenuItem
name|removeGroupKeepSubgroups
init|=
operator|new
name|MenuItem
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Remove group, keep subgroups"
argument_list|)
argument_list|)
decl_stmt|;
name|removeGroupKeepSubgroups
operator|.
name|setOnAction
argument_list|(
name|event
lambda|->
name|viewModel
operator|.
name|removeGroupKeepSubgroups
argument_list|(
name|group
argument_list|)
argument_list|)
expr_stmt|;
name|MenuItem
name|removeSubgroups
init|=
operator|new
name|MenuItem
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Remove subgroups"
argument_list|)
argument_list|)
decl_stmt|;
name|removeSubgroups
operator|.
name|setOnAction
argument_list|(
name|event
lambda|->
name|viewModel
operator|.
name|removeSubgroups
argument_list|(
name|group
argument_list|)
argument_list|)
expr_stmt|;
name|MenuItem
name|addEntries
init|=
operator|new
name|MenuItem
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Add selected entries to this group"
argument_list|)
argument_list|)
decl_stmt|;
name|addEntries
operator|.
name|setOnAction
argument_list|(
name|event
lambda|->
name|viewModel
operator|.
name|addSelectedEntries
argument_list|(
name|group
argument_list|)
argument_list|)
expr_stmt|;
name|MenuItem
name|removeEntries
init|=
operator|new
name|MenuItem
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Remove selected entries from this group"
argument_list|)
argument_list|)
decl_stmt|;
name|removeEntries
operator|.
name|setOnAction
argument_list|(
name|event
lambda|->
name|viewModel
operator|.
name|removeSelectedEntries
argument_list|(
name|group
argument_list|)
argument_list|)
expr_stmt|;
name|MenuItem
name|sortAlphabetically
init|=
operator|new
name|MenuItem
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Sort all subgroups (recursively)"
argument_list|)
argument_list|)
decl_stmt|;
name|sortAlphabetically
operator|.
name|setOnAction
argument_list|(
name|event
lambda|->
name|viewModel
operator|.
name|sortAlphabeticallyRecursive
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
name|editGroup
argument_list|)
expr_stmt|;
name|menu
operator|.
name|getItems
argument_list|()
operator|.
name|add
argument_list|(
operator|new
name|SeparatorMenuItem
argument_list|()
argument_list|)
expr_stmt|;
name|menu
operator|.
name|getItems
argument_list|()
operator|.
name|addAll
argument_list|(
name|addSubgroup
argument_list|,
name|removeSubgroups
argument_list|,
name|removeGroupAndSubgroups
argument_list|,
name|removeGroupKeepSubgroups
argument_list|)
expr_stmt|;
name|menu
operator|.
name|getItems
argument_list|()
operator|.
name|add
argument_list|(
operator|new
name|SeparatorMenuItem
argument_list|()
argument_list|)
expr_stmt|;
name|menu
operator|.
name|getItems
argument_list|()
operator|.
name|addAll
argument_list|(
name|addEntries
argument_list|,
name|removeEntries
argument_list|)
expr_stmt|;
name|menu
operator|.
name|getItems
argument_list|()
operator|.
name|add
argument_list|(
operator|new
name|SeparatorMenuItem
argument_list|()
argument_list|)
expr_stmt|;
name|menu
operator|.
name|getItems
argument_list|()
operator|.
name|add
argument_list|(
name|sortAlphabetically
argument_list|)
expr_stmt|;
comment|// TODO: Disable some actions under certain conditions
comment|//if (group.canBeEdited()) {
comment|//editGroupPopupAction.setEnabled(false);
comment|//addGroupPopupAction.setEnabled(false);
comment|//removeGroupAndSubgroupsPopupAction.setEnabled(false);
comment|//removeGroupKeepSubgroupsPopupAction.setEnabled(false);
comment|//} else {
comment|//editGroupPopupAction.setEnabled(true);
comment|//addGroupPopupAction.setEnabled(true);
comment|//addGroupPopupAction.setNode(node);
comment|//removeGroupAndSubgroupsPopupAction.setEnabled(true);
comment|//removeGroupKeepSubgroupsPopupAction.setEnabled(true);
comment|//}
comment|//sortSubmenu.setEnabled(!node.isLeaf());
comment|//removeSubgroupsPopupAction.setEnabled(!node.isLeaf());
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
comment|/**      * Workaround taken from https://bitbucket.org/controlsfx/controlsfx/issues/330/making-textfieldssetupclearbuttonfield      */
DECL|method|setupClearButtonField (CustomTextField customTextField)
specifier|private
name|void
name|setupClearButtonField
parameter_list|(
name|CustomTextField
name|customTextField
parameter_list|)
block|{
try|try
block|{
name|Method
name|m
init|=
name|TextFields
operator|.
name|class
operator|.
name|getDeclaredMethod
argument_list|(
literal|"setupClearButtonField"
argument_list|,
name|TextField
operator|.
name|class
argument_list|,
name|ObjectProperty
operator|.
name|class
argument_list|)
decl_stmt|;
name|m
operator|.
name|setAccessible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|m
operator|.
name|invoke
argument_list|(
literal|null
argument_list|,
name|customTextField
argument_list|,
name|customTextField
operator|.
name|rightProperty
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NoSuchMethodException
decl||
name|IllegalAccessException
decl||
name|InvocationTargetException
name|ex
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Failed to decorate text field with clear button"
argument_list|,
name|ex
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Determines where the mouse is in the given row.      */
DECL|method|getDroppingMouseLocation (TreeTableRow<GroupNodeViewModel> row, DragEvent event)
specifier|private
name|DroppingMouseLocation
name|getDroppingMouseLocation
parameter_list|(
name|TreeTableRow
argument_list|<
name|GroupNodeViewModel
argument_list|>
name|row
parameter_list|,
name|DragEvent
name|event
parameter_list|)
block|{
if|if
condition|(
operator|(
name|row
operator|.
name|getHeight
argument_list|()
operator|*
literal|0.25
operator|)
operator|>
name|event
operator|.
name|getY
argument_list|()
condition|)
block|{
return|return
name|DroppingMouseLocation
operator|.
name|TOP
return|;
block|}
elseif|else
if|if
condition|(
operator|(
name|row
operator|.
name|getHeight
argument_list|()
operator|*
literal|0.75
operator|)
operator|<
name|event
operator|.
name|getY
argument_list|()
condition|)
block|{
return|return
name|DroppingMouseLocation
operator|.
name|BOTTOM
return|;
block|}
else|else
block|{
return|return
name|DroppingMouseLocation
operator|.
name|CENTER
return|;
block|}
block|}
block|}
end_class

end_unit

