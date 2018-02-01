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
name|Arrays
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
name|gui
operator|.
name|externalfiles
operator|.
name|DroppedFileHandler
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
name|groups
operator|.
name|EntryTableTransferHandler
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
name|FileHelper
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
DECL|class|FileListEditorTransferHandler
class|class
name|FileListEditorTransferHandler
extends|extends
name|TransferHandler
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
name|FileListEditorTransferHandler
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|URL_FLAVOR
specifier|private
specifier|final
name|DataFlavor
name|URL_FLAVOR
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
comment|/**      * @param textTransferHandler is an instance of javax.swing.plaf.basic.BasicTextUI.TextTransferHandler. That class      *                            is not visible. Therefore, we have to "cheat"      */
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
name|URL_FLAVOR
operator|=
name|getUrlFlavor
argument_list|()
expr_stmt|;
block|}
DECL|method|getUrlFlavor ()
specifier|private
name|DataFlavor
name|getUrlFlavor
parameter_list|()
block|{
name|DataFlavor
name|urlFlavor
decl_stmt|;
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
name|urlFlavor
operator|=
literal|null
expr_stmt|;
block|}
return|return
name|urlFlavor
return|;
block|}
comment|/**      * Overridden to indicate which types of drags are supported (only LINK + COPY). COPY is supported as no support      * disables CTRL+C (copy of text)      */
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
DECL|method|importData (JComponent comp, Transferable transferable)
specifier|public
name|boolean
name|importData
parameter_list|(
name|JComponent
name|comp
parameter_list|,
name|Transferable
name|transferable
parameter_list|)
block|{
comment|// If the drop target is the main table, we want to record which
comment|// row the item was dropped on, to identify the entry if needed:
try|try
block|{
name|List
argument_list|<
name|Path
argument_list|>
name|files
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
comment|// This flavor is used for dragged file links in Windows:
if|if
condition|(
name|transferable
operator|.
name|isDataFlavorSupported
argument_list|(
name|DataFlavor
operator|.
name|javaFileListFlavor
argument_list|)
condition|)
block|{
comment|// javaFileListFlavor returns a list of java.io.File (as the string *File* in File indicates) and not a list of java.nio.file
comment|// There is no DataFlavor.javaPathListFlavor, so we have to deal with java.io.File
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
name|List
argument_list|<
name|File
argument_list|>
name|transferedFiles
init|=
operator|(
name|List
argument_list|<
name|File
argument_list|>
operator|)
name|transferable
operator|.
name|getTransferData
argument_list|(
name|DataFlavor
operator|.
name|javaFileListFlavor
argument_list|)
decl_stmt|;
name|files
operator|.
name|addAll
argument_list|(
name|transferedFiles
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
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|transferable
operator|.
name|isDataFlavorSupported
argument_list|(
name|URL_FLAVOR
argument_list|)
condition|)
block|{
name|URL
name|dropLink
init|=
operator|(
name|URL
operator|)
name|transferable
operator|.
name|getTransferData
argument_list|(
name|URL_FLAVOR
argument_list|)
decl_stmt|;
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Dropped URL, which is currently not implemented "
operator|+
name|dropLink
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|transferable
operator|.
name|isDataFlavorSupported
argument_list|(
name|DataFlavor
operator|.
name|stringFlavor
argument_list|)
condition|)
block|{
comment|// This is used when one or more files are pasted from the file manager
comment|// under Gnome. The data consists of the file paths, one file per line:
name|String
name|dropStr
init|=
operator|(
name|String
operator|)
name|transferable
operator|.
name|getTransferData
argument_list|(
name|DataFlavor
operator|.
name|stringFlavor
argument_list|)
decl_stmt|;
name|files
operator|.
name|addAll
argument_list|(
name|EntryTableTransferHandler
operator|.
name|getFilesFromDraggedFilesString
argument_list|(
name|dropStr
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Dropped something, which we currently cannot handle"
argument_list|)
expr_stmt|;
block|}
name|SwingUtilities
operator|.
name|invokeLater
argument_list|(
parameter_list|()
lambda|->
block|{
for|for
control|(
name|Path
name|file
range|:
name|files
control|)
block|{
comment|// Find the file's extension, if any:
name|String
name|name
init|=
name|file
operator|.
name|toAbsolutePath
argument_list|()
operator|.
name|toString
argument_list|()
decl_stmt|;
name|FileHelper
operator|.
name|getFileExtension
argument_list|(
name|name
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|extension
lambda|->
name|ExternalFileTypes
operator|.
name|getInstance
argument_list|()
operator|.
name|getExternalFileTypeByExt
argument_list|(
name|extension
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|fileType
lambda|->
block|{
block|if (droppedFileHandler == null
argument_list|)
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
argument_list|)
block|)
empty_stmt|;
block|}
block|}
end_class

begin_empty_stmt
unit|)
empty_stmt|;
end_empty_stmt

begin_if
if|if
condition|(
operator|!
name|files
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
comment|// Found some files, return
return|return
literal|true
return|;
block|}
end_if

begin_expr_stmt
unit|} catch
operator|(
name|IOException
name|ioe
operator|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Failed to read dropped data. "
argument_list|,
name|ioe
argument_list|)
block|;         }
end_expr_stmt

begin_catch
catch|catch
parameter_list|(
name|UnsupportedFlavorException
decl||
name|ClassCastException
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
end_catch

begin_comment
comment|// all supported flavors failed
end_comment

begin_comment
comment|// log the flavors to support debugging
end_comment

begin_expr_stmt
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Cannot transfer input: "
operator|+
name|dataFlavorsToString
argument_list|(
name|transferable
operator|.
name|getTransferDataFlavors
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
end_expr_stmt

begin_return
return|return
literal|false
return|;
end_return

begin_function
unit|}      private
DECL|method|dataFlavorsToString (DataFlavor[] transferFlavors)
name|String
name|dataFlavorsToString
parameter_list|(
name|DataFlavor
index|[]
name|transferFlavors
parameter_list|)
block|{
return|return
name|Arrays
operator|.
name|stream
argument_list|(
name|transferFlavors
argument_list|)
operator|.
name|map
argument_list|(
name|dataFlavor
lambda|->
name|dataFlavor
operator|.
name|toString
argument_list|()
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|joining
argument_list|(
literal|" "
argument_list|)
argument_list|)
return|;
block|}
end_function

begin_comment
comment|/**      * This method is called to query whether the transfer can be imported.      *      *  @return<code>true</code> for urls, strings, javaFileLists,<code>false</code> otherwise      */
end_comment

begin_function
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
name|URL_FLAVOR
argument_list|)
operator|||
name|inflav
operator|.
name|match
argument_list|(
name|DataFlavor
operator|.
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
name|LOGGER
operator|.
name|debug
argument_list|(
literal|"Unknown data transfer flavor: "
operator|+
name|dataFlavorsToString
argument_list|(
name|transferFlavors
argument_list|)
argument_list|)
expr_stmt|;
return|return
literal|false
return|;
block|}
end_function

unit|}
end_unit

