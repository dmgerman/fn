begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.preview
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|preview
package|;
end_package

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
name|javafx
operator|.
name|beans
operator|.
name|InvalidationListener
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|beans
operator|.
name|Observable
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
name|ScrollPane
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
name|ClipBoardManager
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
name|citationstyle
operator|.
name|PreviewLayout
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

begin_import
import|import
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|Document
import|;
end_import

begin_import
import|import
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|Element
import|;
end_import

begin_import
import|import
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|NodeList
import|;
end_import

begin_comment
comment|/**  * Displays an BibEntry using the given layout format.  */
end_comment

begin_class
DECL|class|PreviewViewer
specifier|public
class|class
name|PreviewViewer
extends|extends
name|ScrollPane
implements|implements
name|InvalidationListener
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
name|PreviewViewer
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
DECL|field|taskExecutor
specifier|private
specifier|final
name|TaskExecutor
name|taskExecutor
init|=
name|Globals
operator|.
name|TASK_EXECUTOR
decl_stmt|;
DECL|field|previewView
specifier|private
specifier|final
name|WebView
name|previewView
decl_stmt|;
DECL|field|layout
specifier|private
name|PreviewLayout
name|layout
decl_stmt|;
comment|/**      * The entry currently shown      */
DECL|field|entry
specifier|private
name|Optional
argument_list|<
name|BibEntry
argument_list|>
name|entry
init|=
name|Optional
operator|.
name|empty
argument_list|()
decl_stmt|;
DECL|field|database
specifier|private
name|BibDatabaseContext
name|database
decl_stmt|;
comment|/**      * @param database Used for resolving strings and pdf directories for links.      */
DECL|method|PreviewViewer (BibDatabaseContext database, DialogService dialogService)
specifier|public
name|PreviewViewer
parameter_list|(
name|BibDatabaseContext
name|database
parameter_list|,
name|DialogService
name|dialogService
parameter_list|)
block|{
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
block|}
DECL|method|setLayout (PreviewLayout newLayout)
specifier|public
name|void
name|setLayout
parameter_list|(
name|PreviewLayout
name|newLayout
parameter_list|)
block|{
name|layout
operator|=
name|newLayout
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
comment|// Remove update listener for old entry
name|entry
operator|.
name|ifPresent
argument_list|(
name|oldEntry
lambda|->
block|{
for|for
control|(
name|Observable
name|observable
range|:
name|oldEntry
operator|.
name|getObservables
argument_list|()
control|)
block|{
name|observable
operator|.
name|removeListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|entry
operator|=
name|Optional
operator|.
name|of
argument_list|(
name|newEntry
argument_list|)
expr_stmt|;
comment|// Register for changes
for|for
control|(
name|Observable
name|observable
range|:
name|newEntry
operator|.
name|getObservables
argument_list|()
control|)
block|{
name|observable
operator|.
name|addListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
name|update
argument_list|()
expr_stmt|;
block|}
DECL|method|update ()
specifier|private
name|void
name|update
parameter_list|()
block|{
if|if
condition|(
operator|!
name|entry
operator|.
name|isPresent
argument_list|()
operator|||
name|layout
operator|==
literal|null
condition|)
block|{
comment|// Nothing to do
return|return;
block|}
name|ExporterFactory
operator|.
name|entryNumber
operator|=
literal|1
expr_stmt|;
comment|// Set entry number in case that is included in the preview layout.
name|BackgroundTask
operator|.
name|wrap
argument_list|(
parameter_list|()
lambda|->
name|layout
operator|.
name|generatePreview
argument_list|(
name|entry
operator|.
name|get
argument_list|()
argument_list|,
name|database
operator|.
name|getDatabase
argument_list|()
argument_list|)
argument_list|)
operator|.
name|onRunning
argument_list|(
parameter_list|()
lambda|->
name|setPreviewText
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
name|layout
operator|.
name|getName
argument_list|()
operator|+
literal|" ..."
operator|+
literal|"</i>"
argument_list|)
argument_list|)
operator|.
name|onSuccess
argument_list|(
name|this
operator|::
name|setPreviewText
argument_list|)
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
argument_list|;
name|setPreviewText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Error while generating citation style"
argument_list|)
argument_list|)
argument_list|;
block|}
block|)
operator|.
name|executeWith
argument_list|(
name|taskExecutor
argument_list|)
expr_stmt|;
end_class

begin_function
unit|}      private
DECL|method|setPreviewText (String text)
name|void
name|setPreviewText
parameter_list|(
name|String
name|text
parameter_list|)
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
name|entry
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
name|taskExecutor
argument_list|)
expr_stmt|;
block|}
end_function

begin_function
DECL|method|copyPreviewToClipBoard ()
specifier|public
name|void
name|copyPreviewToClipBoard
parameter_list|()
block|{
name|StringBuilder
name|previewStringContent
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|Document
name|document
init|=
name|previewView
operator|.
name|getEngine
argument_list|()
operator|.
name|getDocument
argument_list|()
decl_stmt|;
name|NodeList
name|nodeList
init|=
name|document
operator|.
name|getElementsByTagName
argument_list|(
literal|"html"
argument_list|)
decl_stmt|;
comment|//Nodelist does not implement iterable
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|nodeList
operator|.
name|getLength
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|Element
name|element
init|=
operator|(
name|Element
operator|)
name|nodeList
operator|.
name|item
argument_list|(
name|i
argument_list|)
decl_stmt|;
name|previewStringContent
operator|.
name|append
argument_list|(
name|element
operator|.
name|getTextContent
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|ClipboardContent
name|content
init|=
operator|new
name|ClipboardContent
argument_list|()
decl_stmt|;
name|content
operator|.
name|putString
argument_list|(
name|previewStringContent
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
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
literal|"document.documentElement.outerHTML"
argument_list|)
argument_list|)
expr_stmt|;
name|clipBoardManager
operator|.
name|setContent
argument_list|(
name|content
argument_list|)
expr_stmt|;
block|}
end_function

begin_function
annotation|@
name|Override
DECL|method|invalidated (Observable observable)
specifier|public
name|void
name|invalidated
parameter_list|(
name|Observable
name|observable
parameter_list|)
block|{
name|update
argument_list|()
expr_stmt|;
block|}
end_function

begin_function
DECL|method|getSelectionHtmlContent ()
specifier|public
name|String
name|getSelectionHtmlContent
parameter_list|()
block|{
return|return
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
return|;
block|}
end_function

unit|}
end_unit

