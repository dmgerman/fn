begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.maintable
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|maintable
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|File
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|file
operator|.
name|Path
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
name|Objects
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
name|stream
operator|.
name|Collectors
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
name|collections
operator|.
name|ListChangeListener
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
name|ScrollPane
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
name|TableRow
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
name|KeyCode
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
name|KeyEvent
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
name|MouseDragEvent
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
name|MouseEvent
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
name|GUIGlobals
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
name|JabRefFrame
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
name|externalfiles
operator|.
name|ImportHandler
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
name|externalfiletype
operator|.
name|ExternalFileTypes
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
name|keyboard
operator|.
name|KeyBinding
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
name|keyboard
operator|.
name|KeyBindingRepository
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
name|undo
operator|.
name|NamedCompound
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
name|undo
operator|.
name|UndoableInsertEntry
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
name|CustomLocalDragboard
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
name|ViewModelTableRowFactory
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
name|logic
operator|.
name|util
operator|.
name|UpdateField
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
name|preferences
operator|.
name|JabRefPreferences
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|Logger
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|LoggerFactory
import|;
end_import

begin_class
DECL|class|MainTable
specifier|public
class|class
name|MainTable
extends|extends
name|TableView
argument_list|<
name|BibEntryTableViewModel
argument_list|>
block|{
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Logger
name|LOGGER
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|MainTable
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|panel
specifier|private
specifier|final
name|BasePanel
name|panel
decl_stmt|;
DECL|field|pane
specifier|private
specifier|final
name|ScrollPane
name|pane
decl_stmt|;
DECL|field|database
specifier|private
specifier|final
name|BibDatabaseContext
name|database
decl_stmt|;
DECL|field|undoManager
specifier|private
specifier|final
name|UndoManager
name|undoManager
decl_stmt|;
DECL|field|model
specifier|private
specifier|final
name|MainTableDataModel
name|model
decl_stmt|;
DECL|field|importHandler
specifier|private
specifier|final
name|ImportHandler
name|importHandler
decl_stmt|;
DECL|field|localDragboard
specifier|private
specifier|final
name|CustomLocalDragboard
name|localDragboard
init|=
name|GUIGlobals
operator|.
name|localDragboard
decl_stmt|;
DECL|method|MainTable (MainTableDataModel model, JabRefFrame frame, BasePanel panel, BibDatabaseContext database, MainTablePreferences preferences, ExternalFileTypes externalFileTypes, KeyBindingRepository keyBindingRepository)
specifier|public
name|MainTable
parameter_list|(
name|MainTableDataModel
name|model
parameter_list|,
name|JabRefFrame
name|frame
parameter_list|,
name|BasePanel
name|panel
parameter_list|,
name|BibDatabaseContext
name|database
parameter_list|,
name|MainTablePreferences
name|preferences
parameter_list|,
name|ExternalFileTypes
name|externalFileTypes
parameter_list|,
name|KeyBindingRepository
name|keyBindingRepository
parameter_list|)
block|{
name|super
argument_list|()
expr_stmt|;
name|this
operator|.
name|model
operator|=
name|model
expr_stmt|;
name|this
operator|.
name|database
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|database
argument_list|)
expr_stmt|;
name|this
operator|.
name|undoManager
operator|=
name|panel
operator|.
name|getUndoManager
argument_list|()
expr_stmt|;
name|importHandler
operator|=
operator|new
name|ImportHandler
argument_list|(
name|frame
operator|.
name|getDialogService
argument_list|()
argument_list|,
name|database
argument_list|,
name|externalFileTypes
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getFilePreferences
argument_list|()
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getImportFormatPreferences
argument_list|()
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getUpdateFieldPreferences
argument_list|()
argument_list|,
name|Globals
operator|.
name|getFileUpdateMonitor
argument_list|()
argument_list|,
name|undoManager
argument_list|,
name|Globals
operator|.
name|stateManager
argument_list|)
expr_stmt|;
name|this
operator|.
name|getColumns
argument_list|()
operator|.
name|addAll
argument_list|(
operator|new
name|MainTableColumnFactory
argument_list|(
name|database
argument_list|,
name|preferences
operator|.
name|getColumnPreferences
argument_list|()
argument_list|,
name|externalFileTypes
argument_list|,
name|panel
operator|.
name|getUndoManager
argument_list|()
argument_list|,
name|frame
operator|.
name|getDialogService
argument_list|()
argument_list|)
operator|.
name|createColumns
argument_list|()
argument_list|)
expr_stmt|;
operator|new
name|ViewModelTableRowFactory
argument_list|<
name|BibEntryTableViewModel
argument_list|>
argument_list|()
operator|.
name|withOnMouseClickedEvent
argument_list|(
parameter_list|(
name|entry
parameter_list|,
name|event
parameter_list|)
lambda|->
block|{
if|if
condition|(
name|event
operator|.
name|getClickCount
argument_list|()
operator|==
literal|2
condition|)
block|{
name|panel
operator|.
name|showAndEdit
argument_list|(
name|entry
operator|.
name|getEntry
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
operator|.
name|withContextMenu
argument_list|(
name|entry
lambda|->
name|RightClickMenu
operator|.
name|create
argument_list|(
name|entry
argument_list|,
name|keyBindingRepository
argument_list|,
name|panel
argument_list|,
name|frame
operator|.
name|getDialogService
argument_list|()
argument_list|)
argument_list|)
operator|.
name|setOnDragDetected
argument_list|(
name|this
operator|::
name|handleOnDragDetected
argument_list|)
operator|.
name|setOnDragDropped
argument_list|(
name|this
operator|::
name|handleOnDragDropped
argument_list|)
operator|.
name|setOnDragOver
argument_list|(
name|this
operator|::
name|handleOnDragOver
argument_list|)
operator|.
name|setOnDragExited
argument_list|(
name|this
operator|::
name|handleOnDragExited
argument_list|)
operator|.
name|setOnMouseDragEntered
argument_list|(
name|this
operator|::
name|handleOnDragEntered
argument_list|)
operator|.
name|install
argument_list|(
name|this
argument_list|)
expr_stmt|;
comment|/*for (Entry<String, SortType> entries : preferences.getColumnPreferences().getSortTypesForColumns().entrySet()) {             Optional<TableColumn<BibEntryTableViewModel, ?>> column = this.getColumns().stream().filter(col -> entries.getKey().equals(col.getText())).findFirst();             column.ifPresent(col -> {                 col.setSortType(entries.getValue());                 this.getSortOrder().add(col);             });         }*/
if|if
condition|(
name|preferences
operator|.
name|resizeColumnsToFit
argument_list|()
condition|)
block|{
name|this
operator|.
name|setColumnResizePolicy
argument_list|(
operator|new
name|SmartConstrainedResizePolicy
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|this
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
name|this
operator|.
name|setItems
argument_list|(
name|model
operator|.
name|getEntriesFilteredAndSorted
argument_list|()
argument_list|)
expr_stmt|;
comment|// Enable sorting
name|model
operator|.
name|getEntriesFilteredAndSorted
argument_list|()
operator|.
name|comparatorProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|this
operator|.
name|comparatorProperty
argument_list|()
argument_list|)
expr_stmt|;
name|this
operator|.
name|panel
operator|=
name|panel
expr_stmt|;
name|pane
operator|=
operator|new
name|ScrollPane
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|pane
operator|.
name|setFitToHeight
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|pane
operator|.
name|setFitToWidth
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|this
operator|.
name|pane
operator|.
name|getStylesheets
argument_list|()
operator|.
name|add
argument_list|(
name|MainTable
operator|.
name|class
operator|.
name|getResource
argument_list|(
literal|"MainTable.css"
argument_list|)
operator|.
name|toExternalForm
argument_list|()
argument_list|)
expr_stmt|;
comment|// Store visual state
operator|new
name|PersistenceVisualStateTable
argument_list|(
name|this
argument_list|,
name|Globals
operator|.
name|prefs
argument_list|)
expr_stmt|;
comment|// TODO: Float marked entries
comment|//model.updateMarkingState(Globals.prefs.getBoolean(JabRefPreferences.FLOAT_MARKED_ENTRIES));
name|setupKeyBindings
argument_list|(
name|keyBindingRepository
argument_list|)
expr_stmt|;
block|}
DECL|method|clearAndSelect (BibEntry bibEntry)
specifier|public
name|void
name|clearAndSelect
parameter_list|(
name|BibEntry
name|bibEntry
parameter_list|)
block|{
name|findEntry
argument_list|(
name|bibEntry
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|entry
lambda|->
block|{
name|getSelectionModel
argument_list|()
operator|.
name|clearSelection
argument_list|()
expr_stmt|;
name|getSelectionModel
argument_list|()
operator|.
name|select
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|scrollTo
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
block|}
DECL|method|copy ()
specifier|public
name|void
name|copy
parameter_list|()
block|{
name|List
argument_list|<
name|BibEntry
argument_list|>
name|selectedEntries
init|=
name|getSelectedEntries
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|selectedEntries
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
try|try
block|{
name|Globals
operator|.
name|clipboardManager
operator|.
name|setContent
argument_list|(
name|selectedEntries
argument_list|)
expr_stmt|;
name|panel
operator|.
name|output
argument_list|(
name|panel
operator|.
name|formatOutputMessage
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Copied"
argument_list|)
argument_list|,
name|selectedEntries
operator|.
name|size
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Error while copying selected entries to clipboard"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
block|}
DECL|method|cut ()
specifier|public
name|void
name|cut
parameter_list|()
block|{
name|copy
argument_list|()
expr_stmt|;
name|panel
operator|.
name|delete
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
DECL|method|setupKeyBindings (KeyBindingRepository keyBindings)
specifier|private
name|void
name|setupKeyBindings
parameter_list|(
name|KeyBindingRepository
name|keyBindings
parameter_list|)
block|{
name|this
operator|.
name|addEventHandler
argument_list|(
name|KeyEvent
operator|.
name|KEY_PRESSED
argument_list|,
name|event
lambda|->
block|{
if|if
condition|(
name|event
operator|.
name|getCode
argument_list|()
operator|==
name|KeyCode
operator|.
name|ENTER
condition|)
block|{
name|getSelectedEntries
argument_list|()
operator|.
name|stream
argument_list|()
operator|.
name|findFirst
argument_list|()
operator|.
name|ifPresent
argument_list|(
name|panel
operator|::
name|showAndEdit
argument_list|)
expr_stmt|;
name|event
operator|.
name|consume
argument_list|()
expr_stmt|;
return|return;
block|}
name|Optional
argument_list|<
name|KeyBinding
argument_list|>
name|keyBinding
init|=
name|keyBindings
operator|.
name|mapToKeyBinding
argument_list|(
name|event
argument_list|)
decl_stmt|;
if|if
condition|(
name|keyBinding
operator|.
name|isPresent
argument_list|()
condition|)
block|{
switch|switch
condition|(
name|keyBinding
operator|.
name|get
argument_list|()
condition|)
block|{
case|case
name|SELECT_FIRST_ENTRY
case|:
name|clearAndSelectFirst
argument_list|()
expr_stmt|;
name|event
operator|.
name|consume
argument_list|()
expr_stmt|;
break|break;
case|case
name|SELECT_LAST_ENTRY
case|:
name|clearAndSelectLast
argument_list|()
expr_stmt|;
name|event
operator|.
name|consume
argument_list|()
expr_stmt|;
break|break;
case|case
name|PASTE
case|:
name|paste
argument_list|()
expr_stmt|;
name|event
operator|.
name|consume
argument_list|()
expr_stmt|;
break|break;
case|case
name|COPY
case|:
name|copy
argument_list|()
expr_stmt|;
name|event
operator|.
name|consume
argument_list|()
expr_stmt|;
break|break;
case|case
name|CUT
case|:
name|cut
argument_list|()
expr_stmt|;
name|event
operator|.
name|consume
argument_list|()
expr_stmt|;
break|break;
default|default:
comment|// Pass other keys to parent
block|}
block|}
block|}
argument_list|)
expr_stmt|;
block|}
DECL|method|clearAndSelectFirst ()
specifier|private
name|void
name|clearAndSelectFirst
parameter_list|()
block|{
name|getSelectionModel
argument_list|()
operator|.
name|clearSelection
argument_list|()
expr_stmt|;
name|getSelectionModel
argument_list|()
operator|.
name|selectFirst
argument_list|()
expr_stmt|;
name|scrollTo
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
DECL|method|clearAndSelectLast ()
specifier|private
name|void
name|clearAndSelectLast
parameter_list|()
block|{
name|getSelectionModel
argument_list|()
operator|.
name|clearSelection
argument_list|()
expr_stmt|;
name|getSelectionModel
argument_list|()
operator|.
name|selectLast
argument_list|()
expr_stmt|;
name|scrollTo
argument_list|(
name|getItems
argument_list|()
operator|.
name|size
argument_list|()
operator|-
literal|1
argument_list|)
expr_stmt|;
block|}
DECL|method|paste ()
specifier|public
name|void
name|paste
parameter_list|()
block|{
comment|// Find entries in clipboard
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entriesToAdd
init|=
name|Globals
operator|.
name|clipboardManager
operator|.
name|extractData
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|entriesToAdd
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
comment|// Add new entries
name|NamedCompound
name|ce
init|=
operator|new
name|NamedCompound
argument_list|(
operator|(
name|entriesToAdd
operator|.
name|size
argument_list|()
operator|>
literal|1
condition|?
name|Localization
operator|.
name|lang
argument_list|(
literal|"paste entries"
argument_list|)
else|:
name|Localization
operator|.
name|lang
argument_list|(
literal|"paste entry"
argument_list|)
operator|)
argument_list|)
decl_stmt|;
for|for
control|(
name|BibEntry
name|entryToAdd
range|:
name|entriesToAdd
control|)
block|{
name|UpdateField
operator|.
name|setAutomaticFields
argument_list|(
name|entryToAdd
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getUpdateFieldPreferences
argument_list|()
argument_list|)
expr_stmt|;
name|database
operator|.
name|getDatabase
argument_list|()
operator|.
name|insertEntry
argument_list|(
name|entryToAdd
argument_list|)
expr_stmt|;
name|ce
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableInsertEntry
argument_list|(
name|database
operator|.
name|getDatabase
argument_list|()
argument_list|,
name|entryToAdd
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|ce
operator|.
name|end
argument_list|()
expr_stmt|;
name|undoManager
operator|.
name|addEdit
argument_list|(
name|ce
argument_list|)
expr_stmt|;
name|panel
operator|.
name|output
argument_list|(
name|panel
operator|.
name|formatOutputMessage
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Pasted"
argument_list|)
argument_list|,
name|entriesToAdd
operator|.
name|size
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
comment|// Show editor if user want us to do this
name|BibEntry
name|firstNewEntry
init|=
name|entriesToAdd
operator|.
name|get
argument_list|(
literal|0
argument_list|)
decl_stmt|;
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|AUTO_OPEN_FORM
argument_list|)
condition|)
block|{
name|panel
operator|.
name|showAndEdit
argument_list|(
name|firstNewEntry
argument_list|)
expr_stmt|;
block|}
comment|// Select and focus first new entry
name|clearAndSelect
argument_list|(
name|firstNewEntry
argument_list|)
expr_stmt|;
name|this
operator|.
name|requestFocus
argument_list|()
expr_stmt|;
block|}
block|}
DECL|method|handleOnDragOver (TableRow<BibEntryTableViewModel> row, BibEntryTableViewModel item, DragEvent event)
specifier|private
name|void
name|handleOnDragOver
parameter_list|(
name|TableRow
argument_list|<
name|BibEntryTableViewModel
argument_list|>
name|row
parameter_list|,
name|BibEntryTableViewModel
name|item
parameter_list|,
name|DragEvent
name|event
parameter_list|)
block|{
if|if
condition|(
name|event
operator|.
name|getDragboard
argument_list|()
operator|.
name|hasFiles
argument_list|()
condition|)
block|{
name|event
operator|.
name|acceptTransferModes
argument_list|(
name|TransferMode
operator|.
name|ANY
argument_list|)
expr_stmt|;
name|ControlHelper
operator|.
name|setDroppingPseudoClasses
argument_list|(
name|row
argument_list|,
name|event
argument_list|)
expr_stmt|;
block|}
name|event
operator|.
name|consume
argument_list|()
expr_stmt|;
block|}
DECL|method|handleOnDragEntered (TableRow<BibEntryTableViewModel> row, BibEntryTableViewModel entry, MouseDragEvent event)
specifier|private
name|void
name|handleOnDragEntered
parameter_list|(
name|TableRow
argument_list|<
name|BibEntryTableViewModel
argument_list|>
name|row
parameter_list|,
name|BibEntryTableViewModel
name|entry
parameter_list|,
name|MouseDragEvent
name|event
parameter_list|)
block|{
comment|// Support the following gesture to select entries: click on one row -> hold mouse button -> move over other rows
comment|// We need to select all items between the starting row and the row where the user currently hovers the mouse over
comment|// It is not enough to just select the currently hovered row since then sometimes rows are not marked selected if the user moves to fast
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
name|TableRow
argument_list|<
name|BibEntryTableViewModel
argument_list|>
name|sourceRow
init|=
operator|(
name|TableRow
argument_list|<
name|BibEntryTableViewModel
argument_list|>
operator|)
name|event
operator|.
name|getGestureSource
argument_list|()
decl_stmt|;
name|getSelectionModel
argument_list|()
operator|.
name|selectRange
argument_list|(
name|sourceRow
operator|.
name|getIndex
argument_list|()
argument_list|,
name|row
operator|.
name|getIndex
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|handleOnDragExited (TableRow<BibEntryTableViewModel> row, BibEntryTableViewModel entry, DragEvent dragEvent)
specifier|private
name|void
name|handleOnDragExited
parameter_list|(
name|TableRow
argument_list|<
name|BibEntryTableViewModel
argument_list|>
name|row
parameter_list|,
name|BibEntryTableViewModel
name|entry
parameter_list|,
name|DragEvent
name|dragEvent
parameter_list|)
block|{
name|ControlHelper
operator|.
name|removeDroppingPseudoClasses
argument_list|(
name|row
argument_list|)
expr_stmt|;
block|}
DECL|method|handleOnDragDetected (TableRow<BibEntryTableViewModel> row, BibEntryTableViewModel entry, MouseEvent event)
specifier|private
name|void
name|handleOnDragDetected
parameter_list|(
name|TableRow
argument_list|<
name|BibEntryTableViewModel
argument_list|>
name|row
parameter_list|,
name|BibEntryTableViewModel
name|entry
parameter_list|,
name|MouseEvent
name|event
parameter_list|)
block|{
comment|// Start drag'n'drop
name|row
operator|.
name|startFullDrag
argument_list|()
expr_stmt|;
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
init|=
name|getSelectionModel
argument_list|()
operator|.
name|getSelectedItems
argument_list|()
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|BibEntryTableViewModel
operator|::
name|getEntry
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
argument_list|)
decl_stmt|;
comment|//The following is necesary to initiate the drag and drop in javafx, although we don't need the contents
comment|//It doesn't work without
name|ClipboardContent
name|content
init|=
operator|new
name|ClipboardContent
argument_list|()
decl_stmt|;
name|Dragboard
name|dragboard
init|=
name|startDragAndDrop
argument_list|(
name|TransferMode
operator|.
name|MOVE
argument_list|)
decl_stmt|;
name|content
operator|.
name|put
argument_list|(
name|DragAndDropDataFormats
operator|.
name|ENTRIES
argument_list|,
literal|""
argument_list|)
expr_stmt|;
name|dragboard
operator|.
name|setContent
argument_list|(
name|content
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|entries
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|localDragboard
operator|.
name|putBibEntries
argument_list|(
name|entries
argument_list|)
expr_stmt|;
block|}
name|event
operator|.
name|consume
argument_list|()
expr_stmt|;
block|}
DECL|method|handleOnDragDropped (TableRow<BibEntryTableViewModel> row, BibEntryTableViewModel target, DragEvent event)
specifier|private
name|void
name|handleOnDragDropped
parameter_list|(
name|TableRow
argument_list|<
name|BibEntryTableViewModel
argument_list|>
name|row
parameter_list|,
name|BibEntryTableViewModel
name|target
parameter_list|,
name|DragEvent
name|event
parameter_list|)
block|{
name|boolean
name|success
init|=
literal|false
decl_stmt|;
if|if
condition|(
name|event
operator|.
name|getDragboard
argument_list|()
operator|.
name|hasFiles
argument_list|()
condition|)
block|{
name|List
argument_list|<
name|Path
argument_list|>
name|files
init|=
name|event
operator|.
name|getDragboard
argument_list|()
operator|.
name|getFiles
argument_list|()
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|File
operator|::
name|toPath
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
argument_list|)
decl_stmt|;
comment|// Different actions depending on where the user releases the drop in the target row
comment|// Bottom + top -> import entries
comment|// Center -> link files to entry
switch|switch
condition|(
name|ControlHelper
operator|.
name|getDroppingMouseLocation
argument_list|(
name|row
argument_list|,
name|event
argument_list|)
condition|)
block|{
case|case
name|TOP
case|:
case|case
name|BOTTOM
case|:
name|importHandler
operator|.
name|importAsNewEntries
argument_list|(
name|files
argument_list|)
expr_stmt|;
break|break;
case|case
name|CENTER
case|:
comment|// Depending on the pressed modifier, move/copy/link files to drop target
name|BibEntry
name|entry
init|=
name|target
operator|.
name|getEntry
argument_list|()
decl_stmt|;
switch|switch
condition|(
name|event
operator|.
name|getTransferMode
argument_list|()
condition|)
block|{
case|case
name|LINK
case|:
name|LOGGER
operator|.
name|debug
argument_list|(
literal|"Mode LINK"
argument_list|)
expr_stmt|;
comment|//shift on win or no modifier
name|importHandler
operator|.
name|getLinker
argument_list|()
operator|.
name|addFilesToEntry
argument_list|(
name|entry
argument_list|,
name|files
argument_list|)
expr_stmt|;
break|break;
case|case
name|MOVE
case|:
name|LOGGER
operator|.
name|debug
argument_list|(
literal|"Mode MOVE"
argument_list|)
expr_stmt|;
comment|//alt on win
name|importHandler
operator|.
name|getLinker
argument_list|()
operator|.
name|moveFilesToFileDirAndAddToEntry
argument_list|(
name|entry
argument_list|,
name|files
argument_list|)
expr_stmt|;
break|break;
case|case
name|COPY
case|:
name|LOGGER
operator|.
name|debug
argument_list|(
literal|"Mode Copy"
argument_list|)
expr_stmt|;
comment|//ctrl on win
name|importHandler
operator|.
name|getLinker
argument_list|()
operator|.
name|copyFilesToFileDirAndAddToEntry
argument_list|(
name|entry
argument_list|,
name|files
argument_list|)
expr_stmt|;
break|break;
block|}
break|break;
block|}
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
DECL|method|addSelectionListener (ListChangeListener<? super BibEntryTableViewModel> listener)
specifier|public
name|void
name|addSelectionListener
parameter_list|(
name|ListChangeListener
argument_list|<
name|?
super|super
name|BibEntryTableViewModel
argument_list|>
name|listener
parameter_list|)
block|{
name|getSelectionModel
argument_list|()
operator|.
name|getSelectedItems
argument_list|()
operator|.
name|addListener
argument_list|(
name|listener
argument_list|)
expr_stmt|;
block|}
DECL|method|getPane ()
specifier|public
name|ScrollPane
name|getPane
parameter_list|()
block|{
return|return
name|pane
return|;
block|}
DECL|method|getTableModel ()
specifier|public
name|MainTableDataModel
name|getTableModel
parameter_list|()
block|{
return|return
name|model
return|;
block|}
DECL|method|getEntryAt (int row)
specifier|public
name|BibEntry
name|getEntryAt
parameter_list|(
name|int
name|row
parameter_list|)
block|{
return|return
name|model
operator|.
name|getEntriesFilteredAndSorted
argument_list|()
operator|.
name|get
argument_list|(
name|row
argument_list|)
operator|.
name|getEntry
argument_list|()
return|;
block|}
DECL|method|getSelectedEntries ()
specifier|public
name|List
argument_list|<
name|BibEntry
argument_list|>
name|getSelectedEntries
parameter_list|()
block|{
return|return
name|getSelectionModel
argument_list|()
operator|.
name|getSelectedItems
argument_list|()
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|BibEntryTableViewModel
operator|::
name|getEntry
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
argument_list|)
return|;
block|}
DECL|method|findEntry (BibEntry entry)
specifier|private
name|Optional
argument_list|<
name|BibEntryTableViewModel
argument_list|>
name|findEntry
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
return|return
name|model
operator|.
name|getEntriesFilteredAndSorted
argument_list|()
operator|.
name|stream
argument_list|()
operator|.
name|filter
argument_list|(
name|viewModel
lambda|->
name|viewModel
operator|.
name|getEntry
argument_list|()
operator|.
name|equals
argument_list|(
name|entry
argument_list|)
argument_list|)
operator|.
name|findFirst
argument_list|()
return|;
block|}
comment|/**      * Repaints the table with the most recent font configuration      */
DECL|method|updateFont ()
specifier|public
name|void
name|updateFont
parameter_list|()
block|{
comment|// TODO: Font& padding customization
comment|// setFont(GUIGlobals.currentFont);
block|}
block|}
end_class

end_unit

