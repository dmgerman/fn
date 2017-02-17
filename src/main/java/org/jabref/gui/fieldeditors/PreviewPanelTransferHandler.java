begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.fieldeditors
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|fieldeditors
package|;
end_package

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|datatransfer
operator|.
name|StringSelection
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|datatransfer
operator|.
name|Transferable
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|dnd
operator|.
name|DnDConstants
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
name|io
operator|.
name|StringWriter
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JComponent
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JEditorPane
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|TransferHandler
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|text
operator|.
name|BadLocationException
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
name|EntryContainer
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

begin_class
DECL|class|PreviewPanelTransferHandler
specifier|public
class|class
name|PreviewPanelTransferHandler
extends|extends
name|FileListEditorTransferHandler
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
name|PreviewPanelTransferHandler
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|method|PreviewPanelTransferHandler (JabRefFrame frame, EntryContainer entryContainer, TransferHandler textTransferHandler)
specifier|public
name|PreviewPanelTransferHandler
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|EntryContainer
name|entryContainer
parameter_list|,
name|TransferHandler
name|textTransferHandler
parameter_list|)
block|{
name|super
argument_list|(
name|frame
argument_list|,
name|entryContainer
argument_list|,
name|textTransferHandler
argument_list|)
expr_stmt|;
block|}
comment|/**      * LINK is unsupported as dropping into into Microsoft Word then leads to a link instead to a copy      */
annotation|@
name|Override
DECL|method|getSourceActions (JComponent component)
specifier|public
name|int
name|getSourceActions
parameter_list|(
name|JComponent
name|component
parameter_list|)
block|{
return|return
name|DnDConstants
operator|.
name|ACTION_COPY
return|;
block|}
annotation|@
name|Override
DECL|method|createTransferable (JComponent component)
specifier|protected
name|Transferable
name|createTransferable
parameter_list|(
name|JComponent
name|component
parameter_list|)
block|{
if|if
condition|(
name|component
operator|instanceof
name|JEditorPane
condition|)
block|{
comment|// this method should be called from the preview panel only
comment|// the default TransferHandler implementation is aware of HTML
comment|// and returns an appropriate Transferable
comment|// as textTransferHandler.createTransferable() is not available and
comment|// I don't know any other method, I do the HTML conversion by hand
comment|// First, get the HTML of the selected text
name|JEditorPane
name|editorPane
init|=
operator|(
name|JEditorPane
operator|)
name|component
decl_stmt|;
name|StringWriter
name|stringWriter
init|=
operator|new
name|StringWriter
argument_list|()
decl_stmt|;
try|try
block|{
name|editorPane
operator|.
name|getEditorKit
argument_list|()
operator|.
name|write
argument_list|(
name|stringWriter
argument_list|,
name|editorPane
operator|.
name|getDocument
argument_list|()
argument_list|,
name|editorPane
operator|.
name|getSelectionStart
argument_list|()
argument_list|,
name|editorPane
operator|.
name|getSelectionEnd
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|BadLocationException
decl||
name|IOException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Cannot write preview"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
comment|// Second, return the HTML (and text as fallback)
return|return
operator|new
name|HtmlTransferable
argument_list|(
name|stringWriter
operator|.
name|toString
argument_list|()
argument_list|,
name|editorPane
operator|.
name|getSelectedText
argument_list|()
argument_list|)
return|;
block|}
else|else
block|{
comment|// if not called from the preview panel, return an error string
return|return
operator|new
name|StringSelection
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Operation not supported"
argument_list|)
argument_list|)
return|;
block|}
block|}
block|}
end_class

end_unit
