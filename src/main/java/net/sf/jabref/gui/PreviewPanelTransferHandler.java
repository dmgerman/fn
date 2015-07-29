begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* Copyright (C) 2012 JabRef contributors.    This program is free software: you can redistribute it and/or modify   it under the terms of the GNU General Public License as published by   the Free Software Foundation, either version 3 of the License, or   (at your option) any later version.    This program is distributed in the hope that it will be useful,   but WITHOUT ANY WARRANTY; without even the implied warranty of   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the   GNU General Public License for more details.    You should have received a copy of the GNU General Public License   along with this program.  If not, see<http://www.gnu.org/licenses/>.  */
end_comment

begin_package
DECL|package|net.sf.jabref.gui
package|package
name|net
operator|.
name|sf
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|EntryContainer
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
name|Globals
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
name|JabRefFrame
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
DECL|method|getSourceActions (JComponent c)
specifier|public
name|int
name|getSourceActions
parameter_list|(
name|JComponent
name|c
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
DECL|method|createTransferable (JComponent c)
specifier|protected
name|Transferable
name|createTransferable
parameter_list|(
name|JComponent
name|c
parameter_list|)
block|{
if|if
condition|(
name|c
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
name|e
init|=
operator|(
name|JEditorPane
operator|)
name|c
decl_stmt|;
name|StringWriter
name|sw
init|=
operator|new
name|StringWriter
argument_list|()
decl_stmt|;
try|try
block|{
name|e
operator|.
name|getEditorKit
argument_list|()
operator|.
name|write
argument_list|(
name|sw
argument_list|,
name|e
operator|.
name|getDocument
argument_list|()
argument_list|,
name|e
operator|.
name|getSelectionStart
argument_list|()
argument_list|,
name|e
operator|.
name|getSelectionEnd
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
decl||
name|BadLocationException
name|e1
parameter_list|)
block|{
name|e1
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
comment|// Second, return the HTML (and text as fallback)
return|return
operator|new
name|HtmlTransferable
argument_list|(
name|sw
operator|.
name|toString
argument_list|()
argument_list|,
name|e
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
name|Globals
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

