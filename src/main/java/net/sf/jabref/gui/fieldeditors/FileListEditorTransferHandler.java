begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2012 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.gui.fieldeditors
package|package
name|net
operator|.
name|sf
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
name|Clipboard
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
name|DataFlavor
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
name|datatransfer
operator|.
name|UnsupportedFlavorException
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
name|net
operator|.
name|URL
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
name|SwingUtilities
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
name|net
operator|.
name|sf
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
name|net
operator|.
name|sf
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|util
operator|.
name|io
operator|.
name|FileUtil
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
name|external
operator|.
name|DroppedFileHandler
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
name|external
operator|.
name|ExternalFileType
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
name|external
operator|.
name|ExternalFileTypes
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
name|groups
operator|.
name|EntryTableTransferHandler
import|;
end_import

begin_class
DECL|class|FileListEditorTransferHandler
class|class
name|FileListEditorTransferHandler
extends|extends
name|TransferHandler
block|{
DECL|field|urlFlavor
specifier|private
name|DataFlavor
name|urlFlavor
decl_stmt|;
DECL|field|stringFlavor
specifier|private
specifier|final
name|DataFlavor
name|stringFlavor
decl_stmt|;
DECL|field|frame
specifier|private
specifier|final
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|entryContainer
specifier|private
specifier|final
name|EntryContainer
name|entryContainer
decl_stmt|;
DECL|field|textTransferHandler
specifier|private
specifier|final
name|TransferHandler
name|textTransferHandler
decl_stmt|;
DECL|field|droppedFileHandler
specifier|private
name|DroppedFileHandler
name|droppedFileHandler
decl_stmt|;
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
name|FileListEditorTransferHandler
operator|.
name|class
argument_list|)
decl_stmt|;
comment|/**      *      * @param frame      * @param entryContainer      * @param textTransferHandler is an instance of javax.swing.plaf.basic.BasicTextUI.TextTransferHandler. That class is not visible. Therefore, we have to "cheat"      */
DECL|method|FileListEditorTransferHandler (JabRefFrame frame, EntryContainer entryContainer, TransferHandler textTransferHandler)
specifier|public
name|FileListEditorTransferHandler
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
name|this
operator|.
name|frame
operator|=
name|frame
expr_stmt|;
name|this
operator|.
name|entryContainer
operator|=
name|entryContainer
expr_stmt|;
name|this
operator|.
name|textTransferHandler
operator|=
name|textTransferHandler
expr_stmt|;
name|stringFlavor
operator|=
name|DataFlavor
operator|.
name|stringFlavor
expr_stmt|;
try|try
block|{
name|urlFlavor
operator|=
operator|new
name|DataFlavor
argument_list|(
literal|"application/x-java-url; class=java.net.URL"
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ClassNotFoundException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Unable to configure drag and drop for file link table"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Overridden to indicate which types of drags are supported (only LINK + COPY).      * COPY is supported as no support disables CTRL+C (copy of text)      */
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
name|ACTION_LINK
operator||
name|DnDConstants
operator|.
name|ACTION_COPY
return|;
block|}
annotation|@
name|Override
DECL|method|exportToClipboard (JComponent comp, Clipboard clip, int action)
specifier|public
name|void
name|exportToClipboard
parameter_list|(
name|JComponent
name|comp
parameter_list|,
name|Clipboard
name|clip
parameter_list|,
name|int
name|action
parameter_list|)
block|{
if|if
condition|(
name|this
operator|.
name|textTransferHandler
operator|!=
literal|null
condition|)
block|{
name|this
operator|.
name|textTransferHandler
operator|.
name|exportToClipboard
argument_list|(
name|comp
argument_list|,
name|clip
argument_list|,
name|action
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
DECL|method|importData (JComponent comp, Transferable t)
specifier|public
name|boolean
name|importData
parameter_list|(
name|JComponent
name|comp
parameter_list|,
name|Transferable
name|t
parameter_list|)
block|{
comment|// If the drop target is the main table, we want to record which
comment|// row the item was dropped on, to identify the entry if needed:
try|try
block|{
name|List
argument_list|<
name|File
argument_list|>
name|files
init|=
literal|null
decl_stmt|;
comment|// This flavor is used for dragged file links in Windows:
if|if
condition|(
name|t
operator|.
name|isDataFlavorSupported
argument_list|(
name|DataFlavor
operator|.
name|javaFileListFlavor
argument_list|)
condition|)
block|{
name|files
operator|=
operator|(
name|List
argument_list|<
name|File
argument_list|>
operator|)
name|t
operator|.
name|getTransferData
argument_list|(
name|DataFlavor
operator|.
name|javaFileListFlavor
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|t
operator|.
name|isDataFlavorSupported
argument_list|(
name|urlFlavor
argument_list|)
condition|)
block|{
name|URL
name|dropLink
init|=
operator|(
name|URL
operator|)
name|t
operator|.
name|getTransferData
argument_list|(
name|urlFlavor
argument_list|)
decl_stmt|;
name|LOGGER
operator|.
name|debug
argument_list|(
literal|"URL: "
operator|+
name|dropLink
argument_list|)
expr_stmt|;
block|}
comment|// This is used when one or more files are pasted from the file manager
comment|// under Gnome. The data consists of the file paths, one file per line:
if|if
condition|(
name|t
operator|.
name|isDataFlavorSupported
argument_list|(
name|stringFlavor
argument_list|)
condition|)
block|{
name|String
name|dropStr
init|=
operator|(
name|String
operator|)
name|t
operator|.
name|getTransferData
argument_list|(
name|stringFlavor
argument_list|)
decl_stmt|;
name|files
operator|=
name|EntryTableTransferHandler
operator|.
name|getFilesFromDraggedFilesString
argument_list|(
name|dropStr
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|files
operator|!=
literal|null
condition|)
block|{
specifier|final
name|List
argument_list|<
name|File
argument_list|>
name|theFiles
init|=
name|files
decl_stmt|;
name|SwingUtilities
operator|.
name|invokeLater
argument_list|(
operator|new
name|Runnable
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|run
parameter_list|()
block|{
for|for
control|(
name|File
name|file
range|:
name|theFiles
control|)
block|{
comment|// Find the file's extension, if any:
name|String
name|name
init|=
name|file
operator|.
name|getAbsolutePath
argument_list|()
decl_stmt|;
name|Optional
argument_list|<
name|String
argument_list|>
name|extension
init|=
name|FileUtil
operator|.
name|getFileExtension
argument_list|(
name|name
argument_list|)
decl_stmt|;
name|ExternalFileType
name|fileType
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|extension
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|fileType
operator|=
name|ExternalFileTypes
operator|.
name|getInstance
argument_list|()
operator|.
name|getExternalFileTypeByExt
argument_list|(
name|extension
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|fileType
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|droppedFileHandler
operator|==
literal|null
condition|)
block|{
name|droppedFileHandler
operator|=
operator|new
name|DroppedFileHandler
argument_list|(
name|frame
argument_list|,
name|frame
operator|.
name|getCurrentBasePanel
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|droppedFileHandler
operator|.
name|handleDroppedfile
argument_list|(
name|name
argument_list|,
name|fileType
argument_list|,
name|entryContainer
operator|.
name|getEntry
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
argument_list|)
expr_stmt|;
return|return
literal|true
return|;
block|}
block|}
catch|catch
parameter_list|(
name|IOException
name|ioe
parameter_list|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Failed to read dropped data. "
argument_list|,
name|ioe
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|UnsupportedFlavorException
name|ufe
parameter_list|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Drop type error. "
argument_list|,
name|ufe
argument_list|)
expr_stmt|;
block|}
comment|// all supported flavors failed
name|StringBuilder
name|logMessage
init|=
operator|new
name|StringBuilder
argument_list|(
literal|"Cannot transfer input:"
argument_list|)
decl_stmt|;
name|DataFlavor
index|[]
name|inflavs
init|=
name|t
operator|.
name|getTransferDataFlavors
argument_list|()
decl_stmt|;
for|for
control|(
name|DataFlavor
name|inflav
range|:
name|inflavs
control|)
block|{
name|logMessage
operator|.
name|append
argument_list|(
literal|' '
argument_list|)
operator|.
name|append
argument_list|(
name|inflav
argument_list|)
expr_stmt|;
block|}
name|LOGGER
operator|.
name|warn
argument_list|(
name|logMessage
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
return|return
literal|false
return|;
block|}
comment|/**      * This method is called to query whether the transfer can be imported.      *      * Will return true for urls, strings, javaFileLists      */
annotation|@
name|Override
DECL|method|canImport (JComponent comp, DataFlavor[] transferFlavors)
specifier|public
name|boolean
name|canImport
parameter_list|(
name|JComponent
name|comp
parameter_list|,
name|DataFlavor
index|[]
name|transferFlavors
parameter_list|)
block|{
comment|// accept this if any input flavor matches any of our supported flavors
for|for
control|(
name|DataFlavor
name|inflav
range|:
name|transferFlavors
control|)
block|{
if|if
condition|(
name|inflav
operator|.
name|match
argument_list|(
name|urlFlavor
argument_list|)
operator|||
name|inflav
operator|.
name|match
argument_list|(
name|stringFlavor
argument_list|)
operator|||
name|inflav
operator|.
name|match
argument_list|(
name|DataFlavor
operator|.
name|javaFileListFlavor
argument_list|)
condition|)
block|{
return|return
literal|true
return|;
block|}
block|}
comment|// nope, never heard of this type
return|return
literal|false
return|;
block|}
block|}
end_class

end_unit

