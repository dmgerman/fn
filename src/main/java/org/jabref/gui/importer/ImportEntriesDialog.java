begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.importer
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|importer
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|EnumSet
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
name|javax
operator|.
name|inject
operator|.
name|Inject
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|undo
operator|.
name|UndoManager
import|;
end_import

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
name|beans
operator|.
name|binding
operator|.
name|BooleanBinding
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
name|Node
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
name|Label
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
name|ToggleButton
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
name|javafx
operator|.
name|scene
operator|.
name|layout
operator|.
name|VBox
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
name|BackgroundTask
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
name|NoSelectionModel
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
name|TextFlowLimited
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
name|ViewModelListCellFactory
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
name|EntryType
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
name|StandardEntryType
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
name|field
operator|.
name|StandardField
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
name|util
operator|.
name|FileUpdateMonitor
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
name|controlsfx
operator|.
name|control
operator|.
name|CheckListView
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
DECL|class|ImportEntriesDialog
specifier|public
class|class
name|ImportEntriesDialog
extends|extends
name|BaseDialog
argument_list|<
name|Void
argument_list|>
block|{
DECL|field|entriesListView
specifier|public
name|CheckListView
argument_list|<
name|BibEntry
argument_list|>
name|entriesListView
decl_stmt|;
DECL|field|importButton
specifier|public
name|ButtonType
name|importButton
decl_stmt|;
DECL|field|task
specifier|private
specifier|final
name|BackgroundTask
argument_list|<
name|List
argument_list|<
name|BibEntry
argument_list|>
argument_list|>
name|task
decl_stmt|;
DECL|field|viewModel
specifier|private
name|ImportEntriesViewModel
name|viewModel
decl_stmt|;
DECL|field|taskExecutor
annotation|@
name|Inject
specifier|private
name|TaskExecutor
name|taskExecutor
decl_stmt|;
DECL|field|dialogService
annotation|@
name|Inject
specifier|private
name|DialogService
name|dialogService
decl_stmt|;
DECL|field|undoManager
annotation|@
name|Inject
specifier|private
name|UndoManager
name|undoManager
decl_stmt|;
DECL|field|preferences
annotation|@
name|Inject
specifier|private
name|PreferencesService
name|preferences
decl_stmt|;
DECL|field|stateManager
annotation|@
name|Inject
specifier|private
name|StateManager
name|stateManager
decl_stmt|;
DECL|field|fileUpdateMonitor
annotation|@
name|Inject
specifier|private
name|FileUpdateMonitor
name|fileUpdateMonitor
decl_stmt|;
DECL|field|database
specifier|private
name|BibDatabaseContext
name|database
decl_stmt|;
DECL|method|ImportEntriesDialog (BibDatabaseContext database, BackgroundTask<List<BibEntry>> task)
specifier|public
name|ImportEntriesDialog
parameter_list|(
name|BibDatabaseContext
name|database
parameter_list|,
name|BackgroundTask
argument_list|<
name|List
argument_list|<
name|BibEntry
argument_list|>
argument_list|>
name|task
parameter_list|)
block|{
name|this
operator|.
name|database
operator|=
name|database
expr_stmt|;
name|this
operator|.
name|task
operator|=
name|task
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
name|BooleanBinding
name|booleanBind
init|=
name|Bindings
operator|.
name|isEmpty
argument_list|(
name|entriesListView
operator|.
name|getCheckModel
argument_list|()
operator|.
name|getCheckedItems
argument_list|()
argument_list|)
decl_stmt|;
name|Button
name|btn
init|=
operator|(
name|Button
operator|)
name|this
operator|.
name|getDialogPane
argument_list|()
operator|.
name|lookupButton
argument_list|(
name|importButton
argument_list|)
decl_stmt|;
name|btn
operator|.
name|disableProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|booleanBind
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
name|importButton
condition|)
block|{
name|viewModel
operator|.
name|importEntries
argument_list|(
name|entriesListView
operator|.
name|getCheckModel
argument_list|()
operator|.
name|getCheckedItems
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|dialogService
operator|.
name|notify
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Import canceled"
argument_list|)
argument_list|)
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
specifier|private
name|void
name|initialize
parameter_list|()
block|{
name|viewModel
operator|=
operator|new
name|ImportEntriesViewModel
argument_list|(
name|task
argument_list|,
name|taskExecutor
argument_list|,
name|database
argument_list|,
name|dialogService
argument_list|,
name|undoManager
argument_list|,
name|preferences
argument_list|,
name|stateManager
argument_list|,
name|fileUpdateMonitor
argument_list|)
expr_stmt|;
name|Label
name|placeholder
init|=
operator|new
name|Label
argument_list|()
decl_stmt|;
name|placeholder
operator|.
name|textProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|viewModel
operator|.
name|messageProperty
argument_list|()
argument_list|)
expr_stmt|;
name|entriesListView
operator|.
name|setPlaceholder
argument_list|(
name|placeholder
argument_list|)
expr_stmt|;
name|entriesListView
operator|.
name|setItems
argument_list|(
name|viewModel
operator|.
name|getEntries
argument_list|()
argument_list|)
expr_stmt|;
name|PseudoClass
name|entrySelected
init|=
name|PseudoClass
operator|.
name|getPseudoClass
argument_list|(
literal|"entry-selected"
argument_list|)
decl_stmt|;
operator|new
name|ViewModelListCellFactory
argument_list|<
name|BibEntry
argument_list|>
argument_list|()
operator|.
name|withGraphic
argument_list|(
name|entry
lambda|->
block|{
name|ToggleButton
name|addToggle
init|=
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|ADD
operator|.
name|asToggleButton
argument_list|()
decl_stmt|;
name|EasyBind
operator|.
name|subscribe
argument_list|(
name|addToggle
operator|.
name|selectedProperty
argument_list|()
argument_list|,
name|selected
lambda|->
block|{
if|if
condition|(
name|selected
condition|)
block|{
name|addToggle
operator|.
name|setGraphic
argument_list|(
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|ADD_FILLED
operator|.
name|withColor
argument_list|(
name|IconTheme
operator|.
name|SELECTED_COLOR
argument_list|)
operator|.
name|getGraphicNode
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|addToggle
operator|.
name|setGraphic
argument_list|(
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|ADD
operator|.
name|getGraphicNode
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|addToggle
operator|.
name|getStyleClass
argument_list|()
operator|.
name|add
argument_list|(
literal|"addEntryButton"
argument_list|)
expr_stmt|;
name|addToggle
operator|.
name|selectedProperty
argument_list|()
operator|.
name|bindBidirectional
argument_list|(
name|entriesListView
operator|.
name|getItemBooleanProperty
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
name|HBox
name|separator
init|=
operator|new
name|HBox
argument_list|()
decl_stmt|;
name|HBox
operator|.
name|setHgrow
argument_list|(
name|separator
argument_list|,
name|Priority
operator|.
name|SOMETIMES
argument_list|)
expr_stmt|;
name|Node
name|entryNode
init|=
name|getEntryNode
argument_list|(
name|entry
argument_list|)
decl_stmt|;
name|HBox
operator|.
name|setHgrow
argument_list|(
name|entryNode
argument_list|,
name|Priority
operator|.
name|ALWAYS
argument_list|)
expr_stmt|;
name|HBox
name|container
init|=
operator|new
name|HBox
argument_list|(
name|entryNode
argument_list|,
name|separator
argument_list|,
name|addToggle
argument_list|)
decl_stmt|;
name|container
operator|.
name|getStyleClass
argument_list|()
operator|.
name|add
argument_list|(
literal|"entry-container"
argument_list|)
expr_stmt|;
name|BindingsHelper
operator|.
name|includePseudoClassWhen
argument_list|(
name|container
argument_list|,
name|entrySelected
argument_list|,
name|addToggle
operator|.
name|selectedProperty
argument_list|()
argument_list|)
expr_stmt|;
name|BackgroundTask
operator|.
name|wrap
argument_list|(
parameter_list|()
lambda|->
name|viewModel
operator|.
name|hasDuplicate
argument_list|(
name|entry
argument_list|)
argument_list|)
operator|.
name|onSuccess
argument_list|(
name|duplicateFound
lambda|->
block|{
lambda|if (duplicateFound
argument_list|)
block|{
name|Button
name|duplicateButton
init|=
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|DUPLICATE
operator|.
name|asButton
argument_list|()
decl_stmt|;
name|duplicateButton
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
literal|"Possible duplicate of existing entry. Click to resolve."
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|duplicateButton
operator|.
name|setOnAction
argument_list|(
name|event
lambda|->
name|viewModel
operator|.
name|resolveDuplicate
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
name|container
operator|.
name|getChildren
argument_list|()
operator|.
name|add
argument_list|(
literal|1
argument_list|,
name|duplicateButton
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
operator|.
name|executeWith
argument_list|(
name|Globals
operator|.
name|TASK_EXECUTOR
argument_list|)
expr_stmt|;
return|return
name|container
return|;
block|}
block|)
operator|.
name|withOnMouseClickedEvent
argument_list|(
parameter_list|(
name|entry
parameter_list|,
name|event
parameter_list|)
lambda|->
name|entriesListView
operator|.
name|getCheckModel
argument_list|()
operator|.
name|toggleCheckState
argument_list|(
name|entry
argument_list|)
argument_list|)
operator|.
name|withPseudoClass
argument_list|(
name|entrySelected
argument_list|,
name|entriesListView
operator|::
name|getItemBooleanProperty
argument_list|)
operator|.
name|install
argument_list|(
name|entriesListView
argument_list|)
expr_stmt|;
end_class

begin_expr_stmt
name|entriesListView
operator|.
name|setSelectionModel
argument_list|(
operator|new
name|NoSelectionModel
argument_list|<>
argument_list|()
argument_list|)
expr_stmt|;
end_expr_stmt

begin_function
unit|}      private
DECL|method|getEntryNode (BibEntry entry)
name|Node
name|getEntryNode
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
name|Node
name|entryType
init|=
name|getIcon
argument_list|(
name|entry
operator|.
name|getType
argument_list|()
argument_list|)
operator|.
name|getGraphicNode
argument_list|()
decl_stmt|;
name|entryType
operator|.
name|getStyleClass
argument_list|()
operator|.
name|add
argument_list|(
literal|"type"
argument_list|)
expr_stmt|;
name|Label
name|authors
init|=
operator|new
name|Label
argument_list|(
name|entry
operator|.
name|getFieldOrAliasLatexFree
argument_list|(
name|StandardField
operator|.
name|AUTHOR
argument_list|)
operator|.
name|orElse
argument_list|(
literal|""
argument_list|)
argument_list|)
decl_stmt|;
name|authors
operator|.
name|getStyleClass
argument_list|()
operator|.
name|add
argument_list|(
literal|"authors"
argument_list|)
expr_stmt|;
name|Label
name|title
init|=
operator|new
name|Label
argument_list|(
name|entry
operator|.
name|getFieldOrAliasLatexFree
argument_list|(
name|StandardField
operator|.
name|TITLE
argument_list|)
operator|.
name|orElse
argument_list|(
literal|""
argument_list|)
argument_list|)
decl_stmt|;
name|title
operator|.
name|getStyleClass
argument_list|()
operator|.
name|add
argument_list|(
literal|"title"
argument_list|)
expr_stmt|;
name|Label
name|year
init|=
operator|new
name|Label
argument_list|(
name|entry
operator|.
name|getFieldOrAliasLatexFree
argument_list|(
name|StandardField
operator|.
name|YEAR
argument_list|)
operator|.
name|orElse
argument_list|(
literal|""
argument_list|)
argument_list|)
decl_stmt|;
name|year
operator|.
name|getStyleClass
argument_list|()
operator|.
name|add
argument_list|(
literal|"year"
argument_list|)
expr_stmt|;
name|Label
name|journal
init|=
operator|new
name|Label
argument_list|(
name|entry
operator|.
name|getFieldOrAliasLatexFree
argument_list|(
name|StandardField
operator|.
name|JOURNAL
argument_list|)
operator|.
name|orElse
argument_list|(
literal|""
argument_list|)
argument_list|)
decl_stmt|;
name|journal
operator|.
name|getStyleClass
argument_list|()
operator|.
name|add
argument_list|(
literal|"journal"
argument_list|)
expr_stmt|;
name|VBox
name|entryContainer
init|=
operator|new
name|VBox
argument_list|(
operator|new
name|HBox
argument_list|(
literal|10
argument_list|,
name|entryType
argument_list|,
name|title
argument_list|)
argument_list|,
operator|new
name|HBox
argument_list|(
literal|5
argument_list|,
name|year
argument_list|,
name|journal
argument_list|)
argument_list|,
name|authors
argument_list|)
decl_stmt|;
name|entry
operator|.
name|getFieldOrAliasLatexFree
argument_list|(
name|StandardField
operator|.
name|ABSTRACT
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|summaryText
lambda|->
block|{
name|TextFlowLimited
name|summary
init|=
operator|new
name|TextFlowLimited
argument_list|(
operator|new
name|Text
argument_list|(
name|summaryText
argument_list|)
argument_list|)
decl_stmt|;
name|summary
operator|.
name|getStyleClass
argument_list|()
operator|.
name|add
argument_list|(
literal|"summary"
argument_list|)
expr_stmt|;
name|entryContainer
operator|.
name|getChildren
argument_list|()
operator|.
name|add
argument_list|(
name|summary
argument_list|)
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
name|entryContainer
operator|.
name|getStyleClass
argument_list|()
operator|.
name|add
argument_list|(
literal|"bibEntry"
argument_list|)
expr_stmt|;
return|return
name|entryContainer
return|;
block|}
end_function

begin_function
DECL|method|getIcon (EntryType type)
specifier|private
name|IconTheme
operator|.
name|JabRefIcons
name|getIcon
parameter_list|(
name|EntryType
name|type
parameter_list|)
block|{
name|EnumSet
argument_list|<
name|StandardEntryType
argument_list|>
name|crossRefTypes
init|=
name|EnumSet
operator|.
name|of
argument_list|(
name|StandardEntryType
operator|.
name|InBook
argument_list|,
name|StandardEntryType
operator|.
name|InProceedings
argument_list|,
name|StandardEntryType
operator|.
name|InCollection
argument_list|)
decl_stmt|;
if|if
condition|(
name|type
operator|==
name|StandardEntryType
operator|.
name|Book
condition|)
block|{
return|return
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|BOOK
return|;
block|}
elseif|else
if|if
condition|(
name|crossRefTypes
operator|.
name|contains
argument_list|(
name|type
argument_list|)
condition|)
block|{
return|return
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|OPEN_LINK
return|;
block|}
return|return
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|ARTICLE
return|;
block|}
end_function

begin_function
DECL|method|unselectAll ()
specifier|public
name|void
name|unselectAll
parameter_list|()
block|{
name|entriesListView
operator|.
name|getCheckModel
argument_list|()
operator|.
name|clearChecks
argument_list|()
expr_stmt|;
block|}
end_function

begin_function
DECL|method|selectAllNewEntries ()
specifier|public
name|void
name|selectAllNewEntries
parameter_list|()
block|{
name|unselectAll
argument_list|()
expr_stmt|;
for|for
control|(
name|BibEntry
name|entry
range|:
name|entriesListView
operator|.
name|getItems
argument_list|()
control|)
block|{
if|if
condition|(
operator|!
name|viewModel
operator|.
name|hasDuplicate
argument_list|(
name|entry
argument_list|)
condition|)
block|{
name|entriesListView
operator|.
name|getCheckModel
argument_list|()
operator|.
name|check
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_function

unit|}
end_unit

