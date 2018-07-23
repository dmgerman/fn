begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
package|;
end_package

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
name|io
operator|.
name|StringReader
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
name|concurrent
operator|.
name|Future
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Pattern
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|print
operator|.
name|PrinterJob
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
name|SeparatorMenuItem
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
name|web
operator|.
name|WebView
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
name|DefaultTaskExecutor
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
name|citationstyle
operator|.
name|CitationStyle
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
name|exporter
operator|.
name|ExporterFactory
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
name|layout
operator|.
name|Layout
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
name|layout
operator|.
name|LayoutFormatterPreferences
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
name|layout
operator|.
name|LayoutHelper
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
name|entry
operator|.
name|event
operator|.
name|FieldChangedEvent
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
name|PreviewPreferences
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|common
operator|.
name|eventbus
operator|.
name|Subscribe
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
comment|/**  * Displays an BibEntry using the given layout format.  */
end_comment

begin_class
DECL|class|PreviewPanel
specifier|public
class|class
name|PreviewPanel
extends|extends
name|ScrollPane
implements|implements
name|SearchQueryHighlightListener
implements|,
name|EntryContainer
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
name|PreviewPanel
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|clipBoardManager
specifier|private
specifier|final
name|ClipBoardManager
name|clipBoardManager
decl_stmt|;
DECL|field|dialogService
specifier|private
specifier|final
name|DialogService
name|dialogService
decl_stmt|;
DECL|field|keyBindingRepository
specifier|private
specifier|final
name|KeyBindingRepository
name|keyBindingRepository
decl_stmt|;
DECL|field|basePanel
specifier|private
name|Optional
argument_list|<
name|BasePanel
argument_list|>
name|basePanel
init|=
name|Optional
operator|.
name|empty
argument_list|()
decl_stmt|;
DECL|field|fixedLayout
specifier|private
name|boolean
name|fixedLayout
decl_stmt|;
DECL|field|layout
specifier|private
name|Optional
argument_list|<
name|Layout
argument_list|>
name|layout
init|=
name|Optional
operator|.
name|empty
argument_list|()
decl_stmt|;
comment|/**      * The entry currently shown      */
DECL|field|bibEntry
specifier|private
name|Optional
argument_list|<
name|BibEntry
argument_list|>
name|bibEntry
init|=
name|Optional
operator|.
name|empty
argument_list|()
decl_stmt|;
comment|/**      * If a database is set, the preview will attempt to resolve strings in the previewed entry using that database.      */
DECL|field|databaseContext
specifier|private
name|Optional
argument_list|<
name|BibDatabaseContext
argument_list|>
name|databaseContext
init|=
name|Optional
operator|.
name|empty
argument_list|()
decl_stmt|;
DECL|field|previewView
specifier|private
name|WebView
name|previewView
decl_stmt|;
DECL|field|citationStyleFuture
specifier|private
name|Optional
argument_list|<
name|Future
argument_list|<
name|?
argument_list|>
argument_list|>
name|citationStyleFuture
init|=
name|Optional
operator|.
name|empty
argument_list|()
decl_stmt|;
comment|/**      * @param panel           (may be null) Only set this if the preview is associated to the main window.      * @param databaseContext (may be null) Used for resolving pdf directories for links.      */
DECL|method|PreviewPanel (BasePanel panel, BibDatabaseContext databaseContext, KeyBindingRepository keyBindingRepository, PreviewPreferences preferences, DialogService dialogService)
specifier|public
name|PreviewPanel
parameter_list|(
name|BasePanel
name|panel
parameter_list|,
name|BibDatabaseContext
name|databaseContext
parameter_list|,
name|KeyBindingRepository
name|keyBindingRepository
parameter_list|,
name|PreviewPreferences
name|preferences
parameter_list|,
name|DialogService
name|dialogService
parameter_list|)
block|{
name|this
operator|.
name|databaseContext
operator|=
name|Optional
operator|.
name|ofNullable
argument_list|(
name|databaseContext
argument_list|)
expr_stmt|;
name|this
operator|.
name|basePanel
operator|=
name|Optional
operator|.
name|ofNullable
argument_list|(
name|panel
argument_list|)
expr_stmt|;
name|this
operator|.
name|dialogService
operator|=
name|dialogService
expr_stmt|;
name|this
operator|.
name|clipBoardManager
operator|=
name|Globals
operator|.
name|clipboardManager
expr_stmt|;
name|this
operator|.
name|keyBindingRepository
operator|=
name|keyBindingRepository
expr_stmt|;
comment|// Set up scroll pane for preview pane
name|setFitToHeight
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|setFitToWidth
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|previewView
operator|=
operator|new
name|WebView
argument_list|()
expr_stmt|;
name|setContent
argument_list|(
name|previewView
argument_list|)
expr_stmt|;
name|previewView
operator|.
name|setContextMenuEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|setContextMenu
argument_list|(
name|createPopupMenu
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|this
operator|.
name|basePanel
operator|.
name|isPresent
argument_list|()
condition|)
block|{
comment|// Handler for drag content of preview to different window
comment|// only created for main window (not for windows like the search results dialog)
name|setOnDragDetected
argument_list|(
name|event
lambda|->
block|{
name|Dragboard
name|dragboard
init|=
name|startDragAndDrop
argument_list|(
name|TransferMode
operator|.
name|COPY
argument_list|)
decl_stmt|;
name|ClipboardContent
name|content
init|=
operator|new
name|ClipboardContent
argument_list|()
decl_stmt|;
name|content
operator|.
name|putHtml
argument_list|(
operator|(
name|String
operator|)
name|previewView
operator|.
name|getEngine
argument_list|()
operator|.
name|executeScript
argument_list|(
literal|"window.getSelection().toString()"
argument_list|)
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
argument_list|)
expr_stmt|;
block|}
name|createKeyBindings
argument_list|()
expr_stmt|;
name|updateLayout
argument_list|(
name|preferences
argument_list|)
expr_stmt|;
block|}
DECL|method|createKeyBindings ()
specifier|private
name|void
name|createKeyBindings
parameter_list|()
block|{
name|addEventFilter
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
name|keyBindingRepository
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
name|COPY_PREVIEW
case|:
name|copyPreviewToClipBoard
argument_list|()
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
default|default:
block|}
block|}
block|}
argument_list|)
expr_stmt|;
block|}
DECL|method|createPopupMenu ()
specifier|private
name|ContextMenu
name|createPopupMenu
parameter_list|()
block|{
name|MenuItem
name|copyPreview
init|=
operator|new
name|MenuItem
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Copy preview"
argument_list|)
argument_list|,
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|COPY
operator|.
name|getGraphicNode
argument_list|()
argument_list|)
decl_stmt|;
name|copyPreview
operator|.
name|setAccelerator
argument_list|(
name|keyBindingRepository
operator|.
name|getKeyCombination
argument_list|(
name|KeyBinding
operator|.
name|COPY_PREVIEW
argument_list|)
argument_list|)
expr_stmt|;
name|copyPreview
operator|.
name|setOnAction
argument_list|(
name|event
lambda|->
name|copyPreviewToClipBoard
argument_list|()
argument_list|)
expr_stmt|;
name|MenuItem
name|printEntryPreview
init|=
operator|new
name|MenuItem
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Print entry preview"
argument_list|)
argument_list|,
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|PRINTED
operator|.
name|getGraphicNode
argument_list|()
argument_list|)
decl_stmt|;
name|printEntryPreview
operator|.
name|setOnAction
argument_list|(
name|event
lambda|->
name|print
argument_list|()
argument_list|)
expr_stmt|;
name|MenuItem
name|previousPreviewLayout
init|=
operator|new
name|MenuItem
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Previous preview layout"
argument_list|)
argument_list|)
decl_stmt|;
name|previousPreviewLayout
operator|.
name|setAccelerator
argument_list|(
name|keyBindingRepository
operator|.
name|getKeyCombination
argument_list|(
name|KeyBinding
operator|.
name|PREVIOUS_PREVIEW_LAYOUT
argument_list|)
argument_list|)
expr_stmt|;
name|previousPreviewLayout
operator|.
name|setOnAction
argument_list|(
name|event
lambda|->
name|basePanel
operator|.
name|ifPresent
argument_list|(
name|BasePanel
operator|::
name|previousPreviewStyle
argument_list|)
argument_list|)
expr_stmt|;
name|MenuItem
name|nextPreviewLayout
init|=
operator|new
name|MenuItem
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Next preview layout"
argument_list|)
argument_list|)
decl_stmt|;
name|nextPreviewLayout
operator|.
name|setAccelerator
argument_list|(
name|keyBindingRepository
operator|.
name|getKeyCombination
argument_list|(
name|KeyBinding
operator|.
name|NEXT_PREVIEW_LAYOUT
argument_list|)
argument_list|)
expr_stmt|;
name|nextPreviewLayout
operator|.
name|setOnAction
argument_list|(
name|event
lambda|->
name|basePanel
operator|.
name|ifPresent
argument_list|(
name|BasePanel
operator|::
name|nextPreviewStyle
argument_list|)
argument_list|)
expr_stmt|;
name|ContextMenu
name|menu
init|=
operator|new
name|ContextMenu
argument_list|()
decl_stmt|;
name|menu
operator|.
name|getItems
argument_list|()
operator|.
name|add
argument_list|(
name|copyPreview
argument_list|)
expr_stmt|;
name|menu
operator|.
name|getItems
argument_list|()
operator|.
name|add
argument_list|(
name|printEntryPreview
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
name|nextPreviewLayout
argument_list|)
expr_stmt|;
name|menu
operator|.
name|getItems
argument_list|()
operator|.
name|add
argument_list|(
name|previousPreviewLayout
argument_list|)
expr_stmt|;
return|return
name|menu
return|;
block|}
DECL|method|setDatabaseContext (BibDatabaseContext databaseContext)
specifier|public
name|void
name|setDatabaseContext
parameter_list|(
name|BibDatabaseContext
name|databaseContext
parameter_list|)
block|{
name|this
operator|.
name|databaseContext
operator|=
name|Optional
operator|.
name|ofNullable
argument_list|(
name|databaseContext
argument_list|)
expr_stmt|;
block|}
DECL|method|getBasePanel ()
specifier|public
name|Optional
argument_list|<
name|BasePanel
argument_list|>
name|getBasePanel
parameter_list|()
block|{
return|return
name|this
operator|.
name|basePanel
return|;
block|}
DECL|method|setBasePanel (BasePanel basePanel)
specifier|public
name|void
name|setBasePanel
parameter_list|(
name|BasePanel
name|basePanel
parameter_list|)
block|{
name|this
operator|.
name|basePanel
operator|=
name|Optional
operator|.
name|ofNullable
argument_list|(
name|basePanel
argument_list|)
expr_stmt|;
block|}
DECL|method|updateLayout (PreviewPreferences previewPreferences)
specifier|public
name|void
name|updateLayout
parameter_list|(
name|PreviewPreferences
name|previewPreferences
parameter_list|)
block|{
if|if
condition|(
name|fixedLayout
condition|)
block|{
name|LOGGER
operator|.
name|debug
argument_list|(
literal|"cannot change the layout because the layout is fixed"
argument_list|)
expr_stmt|;
return|return;
block|}
name|String
name|style
init|=
name|previewPreferences
operator|.
name|getCurrentPreviewStyle
argument_list|()
decl_stmt|;
if|if
condition|(
name|CitationStyle
operator|.
name|isCitationStyleFile
argument_list|(
name|style
argument_list|)
condition|)
block|{
if|if
condition|(
name|basePanel
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|layout
operator|=
name|Optional
operator|.
name|empty
argument_list|()
expr_stmt|;
name|CitationStyle
operator|.
name|createCitationStyleFromFile
argument_list|(
name|style
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|citationStyle
lambda|->
block|{
name|basePanel
operator|.
name|get
argument_list|()
operator|.
name|getCitationStyleCache
argument_list|()
operator|.
name|setCitationStyle
argument_list|(
name|citationStyle
argument_list|)
expr_stmt|;
name|basePanel
operator|.
name|get
argument_list|()
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Preview style changed to: %0"
argument_list|,
name|citationStyle
operator|.
name|getTitle
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|updatePreviewLayout
argument_list|(
name|previewPreferences
operator|.
name|getPreviewStyle
argument_list|()
argument_list|,
name|previewPreferences
operator|.
name|getLayoutFormatterPreferences
argument_list|()
argument_list|)
expr_stmt|;
name|basePanel
operator|.
name|ifPresent
argument_list|(
name|panel
lambda|->
name|panel
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Preview style changed to: %0"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Preview"
argument_list|)
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|update
argument_list|()
expr_stmt|;
block|}
DECL|method|updatePreviewLayout (String layoutFile, LayoutFormatterPreferences layoutFormatterPreferences)
specifier|private
name|void
name|updatePreviewLayout
parameter_list|(
name|String
name|layoutFile
parameter_list|,
name|LayoutFormatterPreferences
name|layoutFormatterPreferences
parameter_list|)
block|{
name|StringReader
name|sr
init|=
operator|new
name|StringReader
argument_list|(
name|layoutFile
operator|.
name|replace
argument_list|(
literal|"__NEWLINE__"
argument_list|,
literal|"\n"
argument_list|)
argument_list|)
decl_stmt|;
try|try
block|{
name|layout
operator|=
name|Optional
operator|.
name|of
argument_list|(
operator|new
name|LayoutHelper
argument_list|(
name|sr
argument_list|,
name|layoutFormatterPreferences
argument_list|)
operator|.
name|getLayoutFromText
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|layout
operator|=
name|Optional
operator|.
name|empty
argument_list|()
expr_stmt|;
name|LOGGER
operator|.
name|debug
argument_list|(
literal|"no layout could be set"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|setLayout (Layout layout)
specifier|public
name|void
name|setLayout
parameter_list|(
name|Layout
name|layout
parameter_list|)
block|{
name|this
operator|.
name|layout
operator|=
name|Optional
operator|.
name|ofNullable
argument_list|(
name|layout
argument_list|)
expr_stmt|;
name|update
argument_list|()
expr_stmt|;
block|}
DECL|method|setEntry (BibEntry newEntry)
specifier|public
name|void
name|setEntry
parameter_list|(
name|BibEntry
name|newEntry
parameter_list|)
block|{
name|bibEntry
operator|.
name|filter
argument_list|(
name|e
lambda|->
name|e
operator|!=
name|newEntry
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|e
lambda|->
name|e
operator|.
name|unregisterListener
argument_list|(
name|this
argument_list|)
argument_list|)
expr_stmt|;
name|bibEntry
operator|=
name|Optional
operator|.
name|ofNullable
argument_list|(
name|newEntry
argument_list|)
expr_stmt|;
name|newEntry
operator|.
name|registerListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|update
argument_list|()
expr_stmt|;
block|}
comment|/**      * Listener for ChangedFieldEvent.      */
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unused"
argument_list|)
annotation|@
name|Subscribe
DECL|method|listen (FieldChangedEvent fieldChangedEvent)
specifier|public
name|void
name|listen
parameter_list|(
name|FieldChangedEvent
name|fieldChangedEvent
parameter_list|)
block|{
name|update
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|getEntry ()
specifier|public
name|BibEntry
name|getEntry
parameter_list|()
block|{
return|return
name|this
operator|.
name|bibEntry
operator|.
name|orElse
argument_list|(
literal|null
argument_list|)
return|;
block|}
DECL|method|update ()
specifier|public
name|void
name|update
parameter_list|()
block|{
name|ExporterFactory
operator|.
name|entryNumber
operator|=
literal|1
expr_stmt|;
comment|// Set entry number in case that is included in the preview layout.
if|if
condition|(
name|citationStyleFuture
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|citationStyleFuture
operator|.
name|get
argument_list|()
operator|.
name|cancel
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|citationStyleFuture
operator|=
name|Optional
operator|.
name|empty
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|layout
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|bibEntry
operator|.
name|ifPresent
argument_list|(
name|entry
lambda|->
name|sb
operator|.
name|append
argument_list|(
name|layout
operator|.
name|get
argument_list|()
operator|.
name|doLayout
argument_list|(
name|entry
argument_list|,
name|databaseContext
operator|.
name|map
argument_list|(
name|BibDatabaseContext
operator|::
name|getDatabase
argument_list|)
operator|.
name|orElse
argument_list|(
literal|null
argument_list|)
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|setPreviewLabel
argument_list|(
name|sb
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|basePanel
operator|.
name|isPresent
argument_list|()
operator|&&
name|bibEntry
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|Future
argument_list|<
name|?
argument_list|>
name|citationStyleWorker
init|=
name|BackgroundTask
operator|.
name|wrap
argument_list|(
parameter_list|()
lambda|->
name|basePanel
operator|.
name|get
argument_list|()
operator|.
name|getCitationStyleCache
argument_list|()
operator|.
name|getCitationFor
argument_list|(
name|bibEntry
operator|.
name|get
argument_list|()
argument_list|)
argument_list|)
operator|.
name|onRunning
argument_list|(
parameter_list|()
lambda|->
block|{
name|CitationStyle
name|citationStyle
operator|=
name|basePanel
operator|.
name|get
argument_list|()
operator|.
name|getCitationStyleCache
argument_list|()
operator|.
name|getCitationStyle
argument_list|()
argument_list|;
name|setPreviewLabel
argument_list|(
literal|"<i>"
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"Processing %0"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Citation Style"
argument_list|)
argument_list|)
operator|+
literal|": "
operator|+
name|citationStyle
operator|.
name|getTitle
argument_list|()
operator|+
literal|" ..."
operator|+
literal|"</i>"
argument_list|)
expr_stmt|;
block|}
block|)
function|.onSuccess
parameter_list|(
function|this::setPreviewLabel
block|)
operator|.
name|onFailure
argument_list|(
name|exception
lambda|->
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Error while generating citation style"
argument_list|,
name|exception
argument_list|)
expr_stmt|;
name|setPreviewLabel
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Error while generating citation style"
argument_list|)
argument_list|)
expr_stmt|;
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
end_class

begin_expr_stmt
name|this
operator|.
name|citationStyleFuture
operator|=
name|Optional
operator|.
name|of
argument_list|(
name|citationStyleWorker
argument_list|)
expr_stmt|;
end_expr_stmt

begin_function
unit|}     }
DECL|method|setPreviewLabel (String text)
specifier|private
name|void
name|setPreviewLabel
parameter_list|(
name|String
name|text
parameter_list|)
block|{
name|DefaultTaskExecutor
operator|.
name|runInJavaFXThread
argument_list|(
parameter_list|()
lambda|->
block|{
name|previewView
operator|.
name|getEngine
argument_list|()
operator|.
name|loadContent
argument_list|(
name|text
argument_list|)
expr_stmt|;
name|this
operator|.
name|setHvalue
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
block|}
end_function

begin_function
annotation|@
name|Override
DECL|method|highlightPattern (Optional<Pattern> newPattern)
specifier|public
name|void
name|highlightPattern
parameter_list|(
name|Optional
argument_list|<
name|Pattern
argument_list|>
name|newPattern
parameter_list|)
block|{
comment|// TODO: Implement that search phrases are highlighted
name|update
argument_list|()
expr_stmt|;
block|}
end_function

begin_comment
comment|/**      * this fixes the Layout, the user cannot change it anymore. Useful for testing the styles in the settings      *      * @param layout should be either a {@link String} (for the old PreviewStyle) or a {@link CitationStyle}.      */
end_comment

begin_function
DECL|method|setFixedLayout (String layout)
specifier|public
name|void
name|setFixedLayout
parameter_list|(
name|String
name|layout
parameter_list|)
block|{
name|this
operator|.
name|fixedLayout
operator|=
literal|true
expr_stmt|;
name|updatePreviewLayout
argument_list|(
name|layout
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getLayoutFormatterPreferences
argument_list|(
name|Globals
operator|.
name|journalAbbreviationLoader
argument_list|)
argument_list|)
expr_stmt|;
block|}
end_function

begin_function
DECL|method|print ()
specifier|public
name|void
name|print
parameter_list|()
block|{
name|PrinterJob
name|job
init|=
name|PrinterJob
operator|.
name|createPrinterJob
argument_list|()
decl_stmt|;
name|boolean
name|proceed
init|=
name|dialogService
operator|.
name|showPrintDialog
argument_list|(
name|job
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|proceed
condition|)
block|{
return|return;
block|}
name|BackgroundTask
operator|.
name|wrap
argument_list|(
parameter_list|()
lambda|->
block|{
name|job
operator|.
name|getJobSettings
argument_list|()
operator|.
name|setJobName
argument_list|(
name|bibEntry
operator|.
name|flatMap
argument_list|(
name|BibEntry
operator|::
name|getCiteKeyOptional
argument_list|)
operator|.
name|orElse
argument_list|(
literal|"NO ENTRY"
argument_list|)
argument_list|)
expr_stmt|;
name|previewView
operator|.
name|getEngine
argument_list|()
operator|.
name|print
argument_list|(
name|job
argument_list|)
expr_stmt|;
name|job
operator|.
name|endJob
argument_list|()
expr_stmt|;
return|return
literal|null
return|;
block|}
argument_list|)
operator|.
name|onFailure
argument_list|(
name|exception
lambda|->
name|dialogService
operator|.
name|showErrorDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Could not print preview"
argument_list|)
argument_list|,
name|exception
argument_list|)
argument_list|)
operator|.
name|executeWith
argument_list|(
name|Globals
operator|.
name|TASK_EXECUTOR
argument_list|)
expr_stmt|;
block|}
end_function

begin_function
DECL|method|close ()
specifier|public
name|void
name|close
parameter_list|()
block|{
name|basePanel
operator|.
name|ifPresent
argument_list|(
name|BasePanel
operator|::
name|closeBottomPane
argument_list|)
expr_stmt|;
block|}
end_function

begin_function
DECL|method|copyPreviewToClipBoard ()
specifier|private
name|void
name|copyPreviewToClipBoard
parameter_list|()
block|{
name|String
name|previewContent
init|=
operator|(
name|String
operator|)
name|previewView
operator|.
name|getEngine
argument_list|()
operator|.
name|executeScript
argument_list|(
literal|"document.documentElement.outerHTML"
argument_list|)
decl_stmt|;
name|clipBoardManager
operator|.
name|setContent
argument_list|(
name|previewContent
argument_list|)
expr_stmt|;
block|}
end_function

unit|}
end_unit

