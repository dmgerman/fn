begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.worker
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|worker
package|;
end_package

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
name|ArrayList
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
name|concurrent
operator|.
name|ExecutionException
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|SwingWorker
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
name|ClipBoardManager
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
name|citationstyle
operator|.
name|CitationStyleGenerator
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
name|CitationStyleOutputFormat
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
name|util
operator|.
name|OS
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
name|PreviewPreferences
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
comment|/**  * Copies the selected entries and formats them with the selected citation style (or preview), then it is copied to the clipboard.  * This worker cannot be reused.  */
end_comment

begin_class
DECL|class|CitationStyleToClipboardWorker
specifier|public
class|class
name|CitationStyleToClipboardWorker
extends|extends
name|SwingWorker
argument_list|<
name|List
argument_list|<
name|String
argument_list|>
argument_list|,
name|Void
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
name|CitationStyleToClipboardWorker
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|basePanel
specifier|private
specifier|final
name|BasePanel
name|basePanel
decl_stmt|;
DECL|field|selectedEntries
specifier|private
specifier|final
name|List
argument_list|<
name|BibEntry
argument_list|>
name|selectedEntries
decl_stmt|;
DECL|field|style
specifier|private
specifier|final
name|String
name|style
decl_stmt|;
DECL|field|previewStyle
specifier|private
specifier|final
name|String
name|previewStyle
decl_stmt|;
DECL|field|outputFormat
specifier|private
specifier|final
name|CitationStyleOutputFormat
name|outputFormat
decl_stmt|;
DECL|method|CitationStyleToClipboardWorker (BasePanel basePanel, CitationStyleOutputFormat outputFormat)
specifier|public
name|CitationStyleToClipboardWorker
parameter_list|(
name|BasePanel
name|basePanel
parameter_list|,
name|CitationStyleOutputFormat
name|outputFormat
parameter_list|)
block|{
name|this
operator|.
name|basePanel
operator|=
name|basePanel
expr_stmt|;
name|this
operator|.
name|selectedEntries
operator|=
name|basePanel
operator|.
name|getSelectedEntries
argument_list|()
expr_stmt|;
name|PreviewPreferences
name|previewPreferences
init|=
name|Globals
operator|.
name|prefs
operator|.
name|getPreviewPreferences
argument_list|()
decl_stmt|;
name|this
operator|.
name|style
operator|=
name|previewPreferences
operator|.
name|getPreviewCycle
argument_list|()
operator|.
name|get
argument_list|(
name|previewPreferences
operator|.
name|getPreviewCyclePosition
argument_list|()
argument_list|)
expr_stmt|;
name|this
operator|.
name|previewStyle
operator|=
name|Globals
operator|.
name|prefs
operator|.
name|getPreviewPreferences
argument_list|()
operator|.
name|getPreviewStyle
argument_list|()
expr_stmt|;
name|this
operator|.
name|outputFormat
operator|=
name|outputFormat
expr_stmt|;
name|basePanel
operator|.
name|frame
argument_list|()
operator|.
name|setStatus
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Copying..."
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|doInBackground ()
specifier|protected
name|List
argument_list|<
name|String
argument_list|>
name|doInBackground
parameter_list|()
throws|throws
name|Exception
block|{
comment|// This worker stored the style as filename. The CSLAdapter and the CitationStyleCache store the source of the
comment|// style. Therefore, we extract the style source from the file.
name|String
name|styleSource
init|=
literal|null
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
name|styleSource
operator|=
name|CitationStyle
operator|.
name|createCitationStyleFromFile
argument_list|(
name|style
argument_list|)
operator|.
name|filter
argument_list|(
name|citationStyleFromFile
lambda|->
operator|!
name|citationStyleFromFile
operator|.
name|getSource
argument_list|()
operator|.
name|isEmpty
argument_list|()
argument_list|)
operator|.
name|map
argument_list|(
name|CitationStyle
operator|::
name|getSource
argument_list|)
operator|.
name|orElse
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|styleSource
operator|!=
literal|null
condition|)
block|{
return|return
name|CitationStyleGenerator
operator|.
name|generateCitations
argument_list|(
name|selectedEntries
argument_list|,
name|styleSource
argument_list|,
name|outputFormat
argument_list|)
return|;
block|}
else|else
block|{
name|StringReader
name|sr
init|=
operator|new
name|StringReader
argument_list|(
name|previewStyle
operator|.
name|replace
argument_list|(
literal|"__NEWLINE__"
argument_list|,
literal|"\n"
argument_list|)
argument_list|)
decl_stmt|;
name|LayoutFormatterPreferences
name|layoutFormatterPreferences
init|=
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
decl_stmt|;
name|Layout
name|layout
init|=
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
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|citations
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|(
name|selectedEntries
operator|.
name|size
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|BibEntry
name|entry
range|:
name|selectedEntries
control|)
block|{
name|citations
operator|.
name|add
argument_list|(
name|layout
operator|.
name|doLayout
argument_list|(
name|entry
argument_list|,
name|basePanel
operator|.
name|getDatabase
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|citations
return|;
block|}
block|}
comment|/**      * Generates a plain text string out of the preview and copies it additionally to the html to the clipboard      * (WYSIWYG Editors use the HTML, plain text editors the text)      */
DECL|method|processPreview (List<String> citations)
specifier|protected
specifier|static
name|String
name|processPreview
parameter_list|(
name|List
argument_list|<
name|String
argument_list|>
name|citations
parameter_list|)
block|{
return|return
name|String
operator|.
name|join
argument_list|(
name|CitationStyleOutputFormat
operator|.
name|HTML
operator|.
name|getLineSeparator
argument_list|()
argument_list|,
name|citations
argument_list|)
return|;
block|}
comment|/**      * Joins every citation with a newline and returns it.      */
DECL|method|processText (List<String> citations)
specifier|protected
specifier|static
name|ClipboardContent
name|processText
parameter_list|(
name|List
argument_list|<
name|String
argument_list|>
name|citations
parameter_list|)
block|{
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
name|String
operator|.
name|join
argument_list|(
name|CitationStyleOutputFormat
operator|.
name|TEXT
operator|.
name|getLineSeparator
argument_list|()
argument_list|,
name|citations
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|content
return|;
block|}
comment|/**      * Converts the citations into the RTF format.      */
DECL|method|processRtf (List<String> citations)
specifier|protected
specifier|static
name|ClipboardContent
name|processRtf
parameter_list|(
name|List
argument_list|<
name|String
argument_list|>
name|citations
parameter_list|)
block|{
name|String
name|result
init|=
literal|"{\\rtf"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
name|String
operator|.
name|join
argument_list|(
name|CitationStyleOutputFormat
operator|.
name|RTF
operator|.
name|getLineSeparator
argument_list|()
argument_list|,
name|citations
argument_list|)
operator|+
literal|"}"
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
name|putRtf
argument_list|(
name|result
argument_list|)
expr_stmt|;
return|return
name|content
return|;
block|}
comment|/**      * Inserts each citation into a XLSFO body and copies it to the clipboard      */
DECL|method|processXslFo (List<String> citations)
specifier|protected
specifier|static
name|ClipboardContent
name|processXslFo
parameter_list|(
name|List
argument_list|<
name|String
argument_list|>
name|citations
parameter_list|)
block|{
name|String
name|result
init|=
literal|"<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<fo:root xmlns:fo=\"http://www.w3.org/1999/XSL/Format\">"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<fo:layout-master-set>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<fo:simple-page-master master-name=\"citations\">"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<fo:region-body/>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"</fo:simple-page-master>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"</fo:layout-master-set>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<fo:page-sequence master-reference=\"citations\">"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<fo:flow flow-name=\"xsl-region-body\">"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
name|OS
operator|.
name|NEWLINE
decl_stmt|;
name|result
operator|+=
name|String
operator|.
name|join
argument_list|(
name|CitationStyleOutputFormat
operator|.
name|XSL_FO
operator|.
name|getLineSeparator
argument_list|()
argument_list|,
name|citations
argument_list|)
expr_stmt|;
name|result
operator|+=
name|OS
operator|.
name|NEWLINE
operator|+
literal|"</fo:flow>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"</fo:page-sequence>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"</fo:root>"
operator|+
name|OS
operator|.
name|NEWLINE
expr_stmt|;
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
name|ClipBoardManager
operator|.
name|XML
argument_list|,
name|result
argument_list|)
expr_stmt|;
return|return
name|content
return|;
block|}
comment|/**      * Inserts each citation into a HTML body and copies it to the clipboard      */
DECL|method|processHtml (List<String> citations)
specifier|protected
specifier|static
name|ClipboardContent
name|processHtml
parameter_list|(
name|List
argument_list|<
name|String
argument_list|>
name|citations
parameter_list|)
block|{
name|String
name|result
init|=
literal|"<!DOCTYPE html>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<html>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<head>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<meta charset=\"utf-8\">"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"</head>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<body>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
name|OS
operator|.
name|NEWLINE
decl_stmt|;
name|result
operator|+=
name|String
operator|.
name|join
argument_list|(
name|CitationStyleOutputFormat
operator|.
name|HTML
operator|.
name|getLineSeparator
argument_list|()
argument_list|,
name|citations
argument_list|)
expr_stmt|;
name|result
operator|+=
name|OS
operator|.
name|NEWLINE
operator|+
literal|"</body>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"</html>"
operator|+
name|OS
operator|.
name|NEWLINE
expr_stmt|;
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
name|result
argument_list|)
expr_stmt|;
return|return
name|content
return|;
block|}
annotation|@
name|Override
DECL|method|done ()
specifier|public
name|void
name|done
parameter_list|()
block|{
try|try
block|{
name|List
argument_list|<
name|String
argument_list|>
name|citations
init|=
name|get
argument_list|()
decl_stmt|;
comment|// if it's not a citation style take care of the preview
if|if
condition|(
operator|!
name|CitationStyle
operator|.
name|isCitationStyleFile
argument_list|(
name|style
argument_list|)
condition|)
block|{
name|Globals
operator|.
name|clipboardManager
operator|.
name|setHtmlContent
argument_list|(
name|processPreview
argument_list|(
name|citations
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// if it's generated by a citation style take care of each output format
name|ClipboardContent
name|content
decl_stmt|;
switch|switch
condition|(
name|outputFormat
condition|)
block|{
case|case
name|HTML
case|:
name|content
operator|=
name|processHtml
argument_list|(
name|citations
argument_list|)
expr_stmt|;
break|break;
case|case
name|RTF
case|:
name|content
operator|=
name|processRtf
argument_list|(
name|citations
argument_list|)
expr_stmt|;
break|break;
case|case
name|XSL_FO
case|:
name|content
operator|=
name|processXslFo
argument_list|(
name|citations
argument_list|)
expr_stmt|;
break|break;
case|case
name|ASCII_DOC
case|:
case|case
name|TEXT
case|:
name|content
operator|=
name|processText
argument_list|(
name|citations
argument_list|)
expr_stmt|;
break|break;
default|default:
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"unknown output format: '"
operator|+
name|outputFormat
operator|+
literal|"', processing it via the default."
argument_list|)
expr_stmt|;
name|content
operator|=
name|processText
argument_list|(
name|citations
argument_list|)
expr_stmt|;
break|break;
block|}
name|Globals
operator|.
name|clipboardManager
operator|.
name|setContent
argument_list|(
name|content
argument_list|)
expr_stmt|;
block|}
name|basePanel
operator|.
name|frame
argument_list|()
operator|.
name|setStatus
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Copied %0 citations."
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|selectedEntries
operator|.
name|size
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|InterruptedException
decl||
name|ExecutionException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Error while copying citations to the clipboard"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

