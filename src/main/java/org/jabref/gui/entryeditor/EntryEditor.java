begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.entryeditor
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|entryeditor
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
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|LinkedList
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
name|Map
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
name|geometry
operator|.
name|Side
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
name|Tab
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
name|TabPane
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
name|DataFormat
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
name|BorderPane
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
name|actions
operator|.
name|ActionFactory
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
name|actions
operator|.
name|GenerateBibtexKeySingleAction
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
name|actions
operator|.
name|StandardActions
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
name|entryeditor
operator|.
name|fileannotationtab
operator|.
name|FileAnnotationTab
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
name|NewDroppedFileHandler
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
name|help
operator|.
name|HelpAction
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
name|menus
operator|.
name|ChangeEntryTypeMenu
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
name|mergeentries
operator|.
name|FetchAndMergeEntry
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
name|CountingUndoManager
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
name|ColorUtil
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
name|DefaultTaskExecutor
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
name|TypedBibEntry
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
name|help
operator|.
name|HelpFile
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
name|importer
operator|.
name|EntryBasedFetcher
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
name|importer
operator|.
name|WebFetchers
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
name|search
operator|.
name|SearchQueryHighlightListener
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
name|util
operator|.
name|FileUpdateMonitor
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

begin_import
import|import
name|org
operator|.
name|fxmisc
operator|.
name|easybind
operator|.
name|Subscription
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

begin_comment
comment|/**  * GUI component that allows editing of the fields of a BibEntry (i.e. the  * one that shows up, when you double click on an entry in the table)  *<p>  * It hosts the tabs (required, general, optional) and the buttons to the left.  *<p>  * EntryEditor also registers itself to the event bus, receiving  * events whenever a field of the entry changes, enabling the text fields to  * update themselves if the change is made from somewhere else.  */
end_comment

begin_class
DECL|class|EntryEditor
specifier|public
class|class
name|EntryEditor
extends|extends
name|BorderPane
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
name|EntryEditor
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|databaseContext
specifier|private
specifier|final
name|BibDatabaseContext
name|databaseContext
decl_stmt|;
DECL|field|undoManager
specifier|private
specifier|final
name|CountingUndoManager
name|undoManager
decl_stmt|;
DECL|field|panel
specifier|private
specifier|final
name|BasePanel
name|panel
decl_stmt|;
DECL|field|searchListeners
specifier|private
specifier|final
name|List
argument_list|<
name|SearchQueryHighlightListener
argument_list|>
name|searchListeners
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|typeSubscription
specifier|private
name|Subscription
name|typeSubscription
decl_stmt|;
DECL|field|tabs
specifier|private
specifier|final
name|List
argument_list|<
name|EntryEditorTab
argument_list|>
name|tabs
decl_stmt|;
DECL|field|fileMonitor
specifier|private
specifier|final
name|FileUpdateMonitor
name|fileMonitor
decl_stmt|;
comment|/**      * A reference to the entry this editor works on.      */
DECL|field|entry
specifier|private
name|BibEntry
name|entry
decl_stmt|;
DECL|field|sourceTab
specifier|private
name|SourceTab
name|sourceTab
decl_stmt|;
DECL|field|tabbed
annotation|@
name|FXML
specifier|private
name|TabPane
name|tabbed
decl_stmt|;
DECL|field|typeChangeButton
annotation|@
name|FXML
specifier|private
name|Button
name|typeChangeButton
decl_stmt|;
DECL|field|fetcherButton
annotation|@
name|FXML
specifier|private
name|Button
name|fetcherButton
decl_stmt|;
DECL|field|typeLabel
annotation|@
name|FXML
specifier|private
name|Label
name|typeLabel
decl_stmt|;
DECL|field|generateCiteKeyButton
annotation|@
name|FXML
specifier|private
name|Button
name|generateCiteKeyButton
decl_stmt|;
DECL|field|preferences
specifier|private
specifier|final
name|EntryEditorPreferences
name|preferences
decl_stmt|;
DECL|field|dialogService
specifier|private
specifier|final
name|DialogService
name|dialogService
decl_stmt|;
DECL|field|fileHandler
specifier|private
specifier|final
name|NewDroppedFileHandler
name|fileHandler
decl_stmt|;
DECL|field|taskExecutor
specifier|private
specifier|final
name|TaskExecutor
name|taskExecutor
decl_stmt|;
DECL|method|EntryEditor (BasePanel panel, EntryEditorPreferences preferences, FileUpdateMonitor fileMonitor, DialogService dialogService, ExternalFileTypes externalFileTypes, TaskExecutor taskExecutor)
specifier|public
name|EntryEditor
parameter_list|(
name|BasePanel
name|panel
parameter_list|,
name|EntryEditorPreferences
name|preferences
parameter_list|,
name|FileUpdateMonitor
name|fileMonitor
parameter_list|,
name|DialogService
name|dialogService
parameter_list|,
name|ExternalFileTypes
name|externalFileTypes
parameter_list|,
name|TaskExecutor
name|taskExecutor
parameter_list|)
block|{
name|this
operator|.
name|panel
operator|=
name|panel
expr_stmt|;
name|this
operator|.
name|databaseContext
operator|=
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
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
name|this
operator|.
name|preferences
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|preferences
argument_list|)
expr_stmt|;
name|this
operator|.
name|fileMonitor
operator|=
name|fileMonitor
expr_stmt|;
name|this
operator|.
name|dialogService
operator|=
name|dialogService
expr_stmt|;
name|this
operator|.
name|taskExecutor
operator|=
name|taskExecutor
expr_stmt|;
name|fileHandler
operator|=
operator|new
name|NewDroppedFileHandler
argument_list|(
name|dialogService
argument_list|,
name|databaseContext
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
argument_list|)
expr_stmt|;
name|ViewLoader
operator|.
name|view
argument_list|(
name|this
argument_list|)
operator|.
name|root
argument_list|(
name|this
argument_list|)
operator|.
name|load
argument_list|()
expr_stmt|;
if|if
condition|(
name|GUIGlobals
operator|.
name|currentFont
operator|!=
literal|null
condition|)
block|{
name|setStyle
argument_list|(
literal|"text-area-background: "
operator|+
name|ColorUtil
operator|.
name|toHex
argument_list|(
name|GUIGlobals
operator|.
name|validFieldBackgroundColor
argument_list|)
operator|+
literal|";"
operator|+
literal|"text-area-foreground: "
operator|+
name|ColorUtil
operator|.
name|toHex
argument_list|(
name|GUIGlobals
operator|.
name|editorTextColor
argument_list|)
operator|+
literal|";"
operator|+
literal|"text-area-highlight: "
operator|+
name|ColorUtil
operator|.
name|toHex
argument_list|(
name|GUIGlobals
operator|.
name|activeBackgroundColor
argument_list|)
operator|+
literal|";"
argument_list|)
expr_stmt|;
block|}
name|EasyBind
operator|.
name|subscribe
argument_list|(
name|tabbed
operator|.
name|getSelectionModel
argument_list|()
operator|.
name|selectedItemProperty
argument_list|()
argument_list|,
name|tab
lambda|->
block|{
name|EntryEditorTab
name|activeTab
init|=
operator|(
name|EntryEditorTab
operator|)
name|tab
decl_stmt|;
if|if
condition|(
name|activeTab
operator|!=
literal|null
condition|)
block|{
name|activeTab
operator|.
name|notifyAboutFocus
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|setupKeyBindings
argument_list|()
expr_stmt|;
name|tabs
operator|=
name|createTabs
argument_list|()
expr_stmt|;
name|this
operator|.
name|setOnDragOver
argument_list|(
name|event
lambda|->
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
name|COPY
argument_list|,
name|TransferMode
operator|.
name|MOVE
argument_list|,
name|TransferMode
operator|.
name|LINK
argument_list|)
expr_stmt|;
block|}
name|event
operator|.
name|consume
argument_list|()
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
name|this
operator|.
name|setOnDragDropped
argument_list|(
name|event
lambda|->
block|{
name|BibEntry
name|entry
init|=
name|this
operator|.
name|getEntry
argument_list|()
decl_stmt|;
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
name|hasContent
argument_list|(
name|DataFormat
operator|.
name|FILES
argument_list|)
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
name|FileDragDropPreferenceType
name|dragDropPreferencesType
init|=
name|Globals
operator|.
name|prefs
operator|.
name|getEntryEditorFileLinkPreference
argument_list|()
decl_stmt|;
if|if
condition|(
name|dragDropPreferencesType
operator|==
name|FileDragDropPreferenceType
operator|.
name|MOVE
condition|)
block|{
if|if
condition|(
name|event
operator|.
name|getTransferMode
argument_list|()
operator|==
name|TransferMode
operator|.
name|LINK
condition|)
comment|//alt on win
block|{
name|LOGGER
operator|.
name|debug
argument_list|(
literal|"Mode LINK"
argument_list|)
expr_stmt|;
name|fileHandler
operator|.
name|addToEntry
argument_list|(
name|entry
argument_list|,
name|files
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|event
operator|.
name|getTransferMode
argument_list|()
operator|==
name|TransferMode
operator|.
name|COPY
condition|)
comment|//ctrl on win, no modifier on Xubuntu
block|{
name|LOGGER
operator|.
name|debug
argument_list|(
literal|"Mode COPY"
argument_list|)
expr_stmt|;
name|fileHandler
operator|.
name|copyFilesToFileDirAndAddToEntry
argument_list|(
name|entry
argument_list|,
name|files
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|LOGGER
operator|.
name|debug
argument_list|(
literal|"Mode MOVE"
argument_list|)
expr_stmt|;
comment|//shift on win or no modifier
name|fileHandler
operator|.
name|addToEntryRenameAndMoveToFileDir
argument_list|(
name|entry
argument_list|,
name|files
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|dragDropPreferencesType
operator|==
name|FileDragDropPreferenceType
operator|.
name|COPY
condition|)
block|{
if|if
condition|(
name|event
operator|.
name|getTransferMode
argument_list|()
operator|==
name|TransferMode
operator|.
name|COPY
condition|)
comment|//ctrl on win, no modifier on Xubuntu
block|{
name|LOGGER
operator|.
name|debug
argument_list|(
literal|"Mode MOVE"
argument_list|)
expr_stmt|;
name|fileHandler
operator|.
name|addToEntryRenameAndMoveToFileDir
argument_list|(
name|entry
argument_list|,
name|files
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|event
operator|.
name|getTransferMode
argument_list|()
operator|==
name|TransferMode
operator|.
name|LINK
condition|)
comment|//alt on win
block|{
name|LOGGER
operator|.
name|debug
argument_list|(
literal|"Mode LINK"
argument_list|)
expr_stmt|;
name|fileHandler
operator|.
name|addToEntry
argument_list|(
name|entry
argument_list|,
name|files
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|LOGGER
operator|.
name|debug
argument_list|(
literal|"Mode COPY"
argument_list|)
expr_stmt|;
comment|//shift on win or no modifier
name|fileHandler
operator|.
name|copyFilesToFileDirAndAddToEntry
argument_list|(
name|entry
argument_list|,
name|files
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|dragDropPreferencesType
operator|==
name|FileDragDropPreferenceType
operator|.
name|LINK
condition|)
block|{
if|if
condition|(
name|event
operator|.
name|getTransferMode
argument_list|()
operator|==
name|TransferMode
operator|.
name|COPY
condition|)
comment|//ctrl on win, no modifier on Xubuntu
block|{
name|LOGGER
operator|.
name|debug
argument_list|(
literal|"Mode COPY"
argument_list|)
expr_stmt|;
name|fileHandler
operator|.
name|copyFilesToFileDirAndAddToEntry
argument_list|(
name|entry
argument_list|,
name|files
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|event
operator|.
name|getTransferMode
argument_list|()
operator|==
name|TransferMode
operator|.
name|LINK
condition|)
comment|//alt on win
block|{
name|LOGGER
operator|.
name|debug
argument_list|(
literal|"Mode MOVE"
argument_list|)
expr_stmt|;
name|fileHandler
operator|.
name|addToEntryRenameAndMoveToFileDir
argument_list|(
name|entry
argument_list|,
name|files
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|LOGGER
operator|.
name|debug
argument_list|(
literal|"Mode LINK"
argument_list|)
expr_stmt|;
comment|//shift on win or no modifier
name|fileHandler
operator|.
name|addToEntry
argument_list|(
name|entry
argument_list|,
name|files
argument_list|)
expr_stmt|;
block|}
block|}
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
block|}
comment|/**      * Set-up key bindings specific for the entry editor.      */
DECL|method|setupKeyBindings ()
specifier|private
name|void
name|setupKeyBindings
parameter_list|()
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
name|Optional
argument_list|<
name|KeyBinding
argument_list|>
name|keyBinding
init|=
name|preferences
operator|.
name|getKeyBindings
argument_list|()
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
name|ENTRY_EDITOR_NEXT_PANEL
case|:
case|case
name|ENTRY_EDITOR_NEXT_PANEL_2
case|:
name|tabbed
operator|.
name|getSelectionModel
argument_list|()
operator|.
name|selectNext
argument_list|()
expr_stmt|;
name|event
operator|.
name|consume
argument_list|()
expr_stmt|;
break|break;
case|case
name|ENTRY_EDITOR_PREVIOUS_PANEL
case|:
case|case
name|ENTRY_EDITOR_PREVIOUS_PANEL_2
case|:
name|tabbed
operator|.
name|getSelectionModel
argument_list|()
operator|.
name|selectPrevious
argument_list|()
expr_stmt|;
name|event
operator|.
name|consume
argument_list|()
expr_stmt|;
break|break;
case|case
name|ENTRY_EDITOR_NEXT_ENTRY
case|:
name|panel
operator|.
name|selectNextEntry
argument_list|()
expr_stmt|;
name|event
operator|.
name|consume
argument_list|()
expr_stmt|;
break|break;
case|case
name|ENTRY_EDITOR_PREVIOUS_ENTRY
case|:
name|panel
operator|.
name|selectPreviousEntry
argument_list|()
expr_stmt|;
name|event
operator|.
name|consume
argument_list|()
expr_stmt|;
break|break;
case|case
name|HELP
case|:
name|HelpAction
operator|.
name|openHelpPage
argument_list|(
name|HelpFile
operator|.
name|ENTRY_EDITOR
argument_list|)
expr_stmt|;
name|event
operator|.
name|consume
argument_list|()
expr_stmt|;
break|break;
case|case
name|CLOSE
case|:
name|close
argument_list|()
expr_stmt|;
name|event
operator|.
name|consume
argument_list|()
expr_stmt|;
break|break;
case|case
name|CLOSE_ENTRY
case|:
name|close
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
annotation|@
name|FXML
DECL|method|close ()
specifier|public
name|void
name|close
parameter_list|()
block|{
name|panel
operator|.
name|entryEditorClosing
argument_list|(
name|EntryEditor
operator|.
name|this
argument_list|)
expr_stmt|;
block|}
annotation|@
name|FXML
DECL|method|deleteEntry ()
specifier|private
name|void
name|deleteEntry
parameter_list|()
block|{
name|panel
operator|.
name|delete
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
annotation|@
name|FXML
DECL|method|navigateToPreviousEntry ()
specifier|private
name|void
name|navigateToPreviousEntry
parameter_list|()
block|{
name|panel
operator|.
name|selectPreviousEntry
argument_list|()
expr_stmt|;
block|}
annotation|@
name|FXML
DECL|method|navigateToNextEntry ()
specifier|private
name|void
name|navigateToNextEntry
parameter_list|()
block|{
name|panel
operator|.
name|selectNextEntry
argument_list|()
expr_stmt|;
block|}
DECL|method|createTabs ()
specifier|private
name|List
argument_list|<
name|EntryEditorTab
argument_list|>
name|createTabs
parameter_list|()
block|{
name|List
argument_list|<
name|EntryEditorTab
argument_list|>
name|tabs
init|=
operator|new
name|LinkedList
argument_list|<>
argument_list|()
decl_stmt|;
comment|// Required fields
name|tabs
operator|.
name|add
argument_list|(
operator|new
name|RequiredFieldsTab
argument_list|(
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
argument_list|,
name|panel
operator|.
name|getSuggestionProviders
argument_list|()
argument_list|,
name|undoManager
argument_list|,
name|dialogService
argument_list|)
argument_list|)
expr_stmt|;
comment|// Optional fields
name|tabs
operator|.
name|add
argument_list|(
operator|new
name|OptionalFieldsTab
argument_list|(
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
argument_list|,
name|panel
operator|.
name|getSuggestionProviders
argument_list|()
argument_list|,
name|undoManager
argument_list|,
name|dialogService
argument_list|)
argument_list|)
expr_stmt|;
name|tabs
operator|.
name|add
argument_list|(
operator|new
name|OptionalFields2Tab
argument_list|(
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
argument_list|,
name|panel
operator|.
name|getSuggestionProviders
argument_list|()
argument_list|,
name|undoManager
argument_list|,
name|dialogService
argument_list|)
argument_list|)
expr_stmt|;
name|tabs
operator|.
name|add
argument_list|(
operator|new
name|DeprecatedFieldsTab
argument_list|(
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
argument_list|,
name|panel
operator|.
name|getSuggestionProviders
argument_list|()
argument_list|,
name|undoManager
argument_list|,
name|dialogService
argument_list|)
argument_list|)
expr_stmt|;
comment|// Other fields
name|tabs
operator|.
name|add
argument_list|(
operator|new
name|OtherFieldsTab
argument_list|(
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
argument_list|,
name|panel
operator|.
name|getSuggestionProviders
argument_list|()
argument_list|,
name|undoManager
argument_list|,
name|preferences
operator|.
name|getCustomTabFieldNames
argument_list|()
argument_list|,
name|dialogService
argument_list|)
argument_list|)
expr_stmt|;
comment|// General fields from preferences
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|String
argument_list|>
argument_list|>
name|tab
range|:
name|preferences
operator|.
name|getEntryEditorTabList
argument_list|()
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|tabs
operator|.
name|add
argument_list|(
operator|new
name|UserDefinedFieldsTab
argument_list|(
name|tab
operator|.
name|getKey
argument_list|()
argument_list|,
name|tab
operator|.
name|getValue
argument_list|()
argument_list|,
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
argument_list|,
name|panel
operator|.
name|getSuggestionProviders
argument_list|()
argument_list|,
name|undoManager
argument_list|,
name|dialogService
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|// Special tabs
name|tabs
operator|.
name|add
argument_list|(
operator|new
name|MathSciNetTab
argument_list|()
argument_list|)
expr_stmt|;
name|tabs
operator|.
name|add
argument_list|(
operator|new
name|FileAnnotationTab
argument_list|(
name|panel
operator|.
name|getAnnotationCache
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|tabs
operator|.
name|add
argument_list|(
operator|new
name|RelatedArticlesTab
argument_list|(
name|this
argument_list|,
name|preferences
argument_list|,
name|dialogService
argument_list|)
argument_list|)
expr_stmt|;
comment|// Source tab
name|sourceTab
operator|=
operator|new
name|SourceTab
argument_list|(
name|databaseContext
argument_list|,
name|undoManager
argument_list|,
name|preferences
operator|.
name|getLatexFieldFormatterPreferences
argument_list|()
argument_list|,
name|preferences
operator|.
name|getImportFormatPreferences
argument_list|()
argument_list|,
name|fileMonitor
argument_list|)
expr_stmt|;
name|tabs
operator|.
name|add
argument_list|(
name|sourceTab
argument_list|)
expr_stmt|;
return|return
name|tabs
return|;
block|}
DECL|method|recalculateVisibleTabs ()
specifier|private
name|void
name|recalculateVisibleTabs
parameter_list|()
block|{
name|List
argument_list|<
name|Tab
argument_list|>
name|visibleTabs
init|=
name|tabs
operator|.
name|stream
argument_list|()
operator|.
name|filter
argument_list|(
name|tab
lambda|->
name|tab
operator|.
name|shouldShow
argument_list|(
name|entry
argument_list|)
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
comment|// Start of ugly hack:
comment|// We need to find out, which tabs will be shown and which not and remove and re-add the appropriate tabs
comment|// to the editor. We don't want to simply remove all and re-add the complete list of visible tabs, because
comment|// the tabs give an ugly animation the looks like all tabs are shifting in from the right.
comment|// This hack is required since tabbed.getTabs().setAll(visibleTabs) changes the order of the tabs in the editor
comment|// First, remove tabs that we do not want to show
name|List
argument_list|<
name|EntryEditorTab
argument_list|>
name|toBeRemoved
init|=
name|tabs
operator|.
name|stream
argument_list|()
operator|.
name|filter
argument_list|(
name|tab
lambda|->
operator|!
name|tab
operator|.
name|shouldShow
argument_list|(
name|entry
argument_list|)
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
name|tabbed
operator|.
name|getTabs
argument_list|()
operator|.
name|removeAll
argument_list|(
name|toBeRemoved
argument_list|)
expr_stmt|;
comment|// Next add all the visible tabs (if not already present) at the right position
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|visibleTabs
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|Tab
name|toBeAdded
init|=
name|visibleTabs
operator|.
name|get
argument_list|(
name|i
argument_list|)
decl_stmt|;
name|Tab
name|shown
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|i
operator|<
name|tabbed
operator|.
name|getTabs
argument_list|()
operator|.
name|size
argument_list|()
condition|)
block|{
name|shown
operator|=
name|tabbed
operator|.
name|getTabs
argument_list|()
operator|.
name|get
argument_list|(
name|i
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|toBeAdded
operator|.
name|equals
argument_list|(
name|shown
argument_list|)
condition|)
block|{
name|tabbed
operator|.
name|getTabs
argument_list|()
operator|.
name|add
argument_list|(
name|i
argument_list|,
name|toBeAdded
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|/**      * @return the currently edited entry      */
DECL|method|getEntry ()
specifier|public
name|BibEntry
name|getEntry
parameter_list|()
block|{
return|return
name|entry
return|;
block|}
comment|/**      * Sets the entry to edit.      */
DECL|method|setEntry (BibEntry entry)
specifier|public
name|void
name|setEntry
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|entry
argument_list|)
expr_stmt|;
comment|// Remove subscription for old entry if existing
if|if
condition|(
name|typeSubscription
operator|!=
literal|null
condition|)
block|{
name|typeSubscription
operator|.
name|unsubscribe
argument_list|()
expr_stmt|;
block|}
name|this
operator|.
name|entry
operator|=
name|entry
expr_stmt|;
name|recalculateVisibleTabs
argument_list|()
expr_stmt|;
if|if
condition|(
name|preferences
operator|.
name|showSourceTabByDefault
argument_list|()
condition|)
block|{
name|tabbed
operator|.
name|getSelectionModel
argument_list|()
operator|.
name|select
argument_list|(
name|sourceTab
argument_list|)
expr_stmt|;
block|}
comment|// Notify current tab about new entry
name|getSelectedTab
argument_list|()
operator|.
name|notifyAboutFocus
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|setupToolBar
argument_list|()
expr_stmt|;
comment|// Subscribe to type changes for rebuilding the currently visible tab
name|typeSubscription
operator|=
name|EasyBind
operator|.
name|subscribe
argument_list|(
name|this
operator|.
name|entry
operator|.
name|typeProperty
argument_list|()
argument_list|,
name|type
lambda|->
block|{
name|typeLabel
operator|.
name|setText
argument_list|(
operator|new
name|TypedBibEntry
argument_list|(
name|entry
argument_list|,
name|databaseContext
operator|.
name|getMode
argument_list|()
argument_list|)
operator|.
name|getTypeForDisplay
argument_list|()
argument_list|)
expr_stmt|;
name|recalculateVisibleTabs
argument_list|()
expr_stmt|;
name|getSelectedTab
argument_list|()
operator|.
name|notifyAboutFocus
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
block|}
DECL|method|getSelectedTab ()
specifier|private
name|EntryEditorTab
name|getSelectedTab
parameter_list|()
block|{
return|return
operator|(
name|EntryEditorTab
operator|)
name|tabbed
operator|.
name|getSelectionModel
argument_list|()
operator|.
name|getSelectedItem
argument_list|()
return|;
block|}
DECL|method|setupToolBar ()
specifier|private
name|void
name|setupToolBar
parameter_list|()
block|{
comment|// Update type label
name|TypedBibEntry
name|typedEntry
init|=
operator|new
name|TypedBibEntry
argument_list|(
name|entry
argument_list|,
name|databaseContext
operator|.
name|getMode
argument_list|()
argument_list|)
decl_stmt|;
name|typeLabel
operator|.
name|setText
argument_list|(
name|typedEntry
operator|.
name|getTypeForDisplay
argument_list|()
argument_list|)
expr_stmt|;
comment|// Add type change menu
name|ContextMenu
name|typeMenu
init|=
operator|new
name|ChangeEntryTypeMenu
argument_list|(
name|preferences
operator|.
name|getKeyBindings
argument_list|()
argument_list|)
operator|.
name|getChangeEntryTypePopupMenu
argument_list|(
name|entry
argument_list|,
name|databaseContext
argument_list|,
name|undoManager
argument_list|)
decl_stmt|;
name|typeLabel
operator|.
name|setOnMouseClicked
argument_list|(
name|event
lambda|->
name|typeMenu
operator|.
name|show
argument_list|(
name|typeLabel
argument_list|,
name|Side
operator|.
name|RIGHT
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|typeChangeButton
operator|.
name|setOnMouseClicked
argument_list|(
name|event
lambda|->
name|typeMenu
operator|.
name|show
argument_list|(
name|typeChangeButton
argument_list|,
name|Side
operator|.
name|RIGHT
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|)
argument_list|)
expr_stmt|;
comment|// Add menu for fetching bibliographic information
name|ContextMenu
name|fetcherMenu
init|=
operator|new
name|ContextMenu
argument_list|()
decl_stmt|;
for|for
control|(
name|EntryBasedFetcher
name|fetcher
range|:
name|WebFetchers
operator|.
name|getEntryBasedFetchers
argument_list|(
name|preferences
operator|.
name|getImportFormatPreferences
argument_list|()
argument_list|)
control|)
block|{
name|MenuItem
name|fetcherMenuItem
init|=
operator|new
name|MenuItem
argument_list|(
name|fetcher
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
name|fetcherMenuItem
operator|.
name|setOnAction
argument_list|(
name|event
lambda|->
name|fetchAndMerge
argument_list|(
name|fetcher
argument_list|)
argument_list|)
expr_stmt|;
name|fetcherMenu
operator|.
name|getItems
argument_list|()
operator|.
name|add
argument_list|(
name|fetcherMenuItem
argument_list|)
expr_stmt|;
block|}
name|fetcherButton
operator|.
name|setOnMouseClicked
argument_list|(
name|event
lambda|->
name|fetcherMenu
operator|.
name|show
argument_list|(
name|fetcherButton
argument_list|,
name|Side
operator|.
name|RIGHT
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|)
argument_list|)
expr_stmt|;
comment|// Configure cite key button
operator|new
name|ActionFactory
argument_list|(
name|preferences
operator|.
name|getKeyBindings
argument_list|()
argument_list|)
operator|.
name|configureIconButton
argument_list|(
name|StandardActions
operator|.
name|GENERATE_CITE_KEY
argument_list|,
operator|new
name|GenerateBibtexKeySingleAction
argument_list|(
name|getEntry
argument_list|()
argument_list|,
name|databaseContext
argument_list|,
name|dialogService
argument_list|,
name|preferences
argument_list|,
name|undoManager
argument_list|)
argument_list|,
name|generateCiteKeyButton
argument_list|)
expr_stmt|;
block|}
DECL|method|fetchAndMerge (EntryBasedFetcher fetcher)
specifier|private
name|void
name|fetchAndMerge
parameter_list|(
name|EntryBasedFetcher
name|fetcher
parameter_list|)
block|{
operator|new
name|FetchAndMergeEntry
argument_list|(
name|panel
argument_list|,
name|taskExecutor
argument_list|)
operator|.
name|fetchAndMerge
argument_list|(
name|entry
argument_list|,
name|fetcher
argument_list|)
expr_stmt|;
block|}
DECL|method|addSearchListener (SearchQueryHighlightListener listener)
name|void
name|addSearchListener
parameter_list|(
name|SearchQueryHighlightListener
name|listener
parameter_list|)
block|{
comment|// TODO: Highlight search text in entry editors
name|searchListeners
operator|.
name|add
argument_list|(
name|listener
argument_list|)
expr_stmt|;
name|panel
operator|.
name|frame
argument_list|()
operator|.
name|getGlobalSearchBar
argument_list|()
operator|.
name|getSearchQueryHighlightObservable
argument_list|()
operator|.
name|addSearchListener
argument_list|(
name|listener
argument_list|)
expr_stmt|;
block|}
DECL|method|setFocusToField (String fieldName)
specifier|public
name|void
name|setFocusToField
parameter_list|(
name|String
name|fieldName
parameter_list|)
block|{
name|DefaultTaskExecutor
operator|.
name|runInJavaFXThread
argument_list|(
parameter_list|()
lambda|->
block|{
for|for
control|(
name|Tab
name|tab
range|:
name|tabbed
operator|.
name|getTabs
argument_list|()
control|)
block|{
if|if
condition|(
operator|(
name|tab
operator|instanceof
name|FieldsEditorTab
operator|)
operator|&&
operator|(
operator|(
name|FieldsEditorTab
operator|)
name|tab
operator|)
operator|.
name|getShownFields
argument_list|()
operator|.
name|contains
argument_list|(
name|fieldName
argument_list|)
condition|)
block|{
name|FieldsEditorTab
name|fieldsEditorTab
init|=
operator|(
name|FieldsEditorTab
operator|)
name|tab
decl_stmt|;
name|tabbed
operator|.
name|getSelectionModel
argument_list|()
operator|.
name|select
argument_list|(
name|tab
argument_list|)
expr_stmt|;
name|fieldsEditorTab
operator|.
name|requestFocus
argument_list|(
name|fieldName
argument_list|)
expr_stmt|;
block|}
block|}
block|}
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

