begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.groups
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|groups
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
name|*
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
name|awt
operator|.
name|event
operator|.
name|InputEvent
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|MouseEvent
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
name|FileWriter
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
name|MalformedURLException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URISyntaxException
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
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Iterator
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
name|JTable
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|BasePanel
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
name|TransferableFileLinkSelection
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
name|MainTable
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
name|MainTableFormat
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
name|imports
operator|.
name|ImportMenuItem
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
name|imports
operator|.
name|OpenDatabaseAction
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
name|imports
operator|.
name|ParserResult
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
name|net
operator|.
name|URLDownload
import|;
end_import

begin_import
import|import
name|spl
operator|.
name|PdfImporter
import|;
end_import

begin_import
import|import
name|spl
operator|.
name|PdfImporter
operator|.
name|ImportPdfFilesResult
import|;
end_import

begin_import
import|import
name|spl
operator|.
name|Tools
import|;
end_import

begin_class
DECL|class|EntryTableTransferHandler
specifier|public
class|class
name|EntryTableTransferHandler
extends|extends
name|TransferHandler
block|{
DECL|field|entryTable
specifier|protected
specifier|final
name|MainTable
name|entryTable
decl_stmt|;
DECL|field|frame
specifier|protected
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|panel
specifier|private
name|BasePanel
name|panel
decl_stmt|;
DECL|field|urlFlavor
specifier|protected
name|DataFlavor
name|urlFlavor
decl_stmt|;
DECL|field|stringFlavor
specifier|protected
name|DataFlavor
name|stringFlavor
decl_stmt|;
DECL|field|DROP_ALLOWED
specifier|protected
specifier|static
name|boolean
name|DROP_ALLOWED
init|=
literal|true
decl_stmt|;
comment|/** 	 * Construct the transfer handler. 	 *  	 * @param entryTable 	 *            The table this transfer handler should operate on. This 	 *            argument is allowed to equal 	 * @null, in which case the transfer handler can assume that it works for a 	 *        JabRef instance with no databases open, attached to the empty 	 *        tabbed pane. 	 * @param frame 	 *            The JabRefFrame instance. 	 * @param panel 	 *            The BasePanel this transferhandler works for. 	 */
DECL|method|EntryTableTransferHandler (MainTable entryTable, JabRefFrame frame, BasePanel panel)
specifier|public
name|EntryTableTransferHandler
parameter_list|(
name|MainTable
name|entryTable
parameter_list|,
name|JabRefFrame
name|frame
parameter_list|,
name|BasePanel
name|panel
parameter_list|)
block|{
name|this
operator|.
name|entryTable
operator|=
name|entryTable
expr_stmt|;
name|this
operator|.
name|frame
operator|=
name|frame
expr_stmt|;
name|this
operator|.
name|panel
operator|=
name|panel
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
name|Globals
operator|.
name|logger
argument_list|(
literal|"Unable to configure drag and drop for main table"
argument_list|)
expr_stmt|;
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
comment|/** 	 * Overriden to indicate which types of drags are supported (only LINK). 	 *  	 * @override 	 */
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
return|;
block|}
comment|/** 	 * This method is called when dragging stuff *from* the table. 	 */
DECL|method|createTransferable (JComponent c)
specifier|public
name|Transferable
name|createTransferable
parameter_list|(
name|JComponent
name|c
parameter_list|)
block|{
if|if
condition|(
operator|!
name|draggingFile
condition|)
block|{
comment|/* so we can assume it will never be called if entryTable==null: */
return|return
operator|new
name|TransferableEntrySelection
argument_list|(
name|entryTable
operator|.
name|getSelectedEntries
argument_list|()
argument_list|)
return|;
block|}
else|else
block|{
name|draggingFile
operator|=
literal|false
expr_stmt|;
return|return
operator|(
operator|new
name|TransferableFileLinkSelection
argument_list|(
name|panel
argument_list|,
name|entryTable
operator|.
name|getSelectedEntries
argument_list|()
argument_list|)
operator|)
return|;
comment|//.getTransferable();
block|}
block|}
comment|/** 	 * This method is called when stuff is drag to the component. 	 *  	 * Imports the dropped URL or plain text as a new entry in the current 	 * database. 	 *  	 */
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
name|int
name|dropRow
init|=
operator|-
literal|1
decl_stmt|;
if|if
condition|(
name|comp
operator|instanceof
name|JTable
condition|)
block|{
name|dropRow
operator|=
operator|(
operator|(
name|JTable
operator|)
name|comp
operator|)
operator|.
name|getSelectedRow
argument_list|()
expr_stmt|;
block|}
try|try
block|{
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
comment|// JOptionPane.showMessageDialog(null, "Received
comment|// javaFileListFlavor");
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
name|List
argument_list|<
name|File
argument_list|>
name|l
init|=
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
decl_stmt|;
return|return
name|handleDraggedFiles
argument_list|(
name|l
argument_list|,
name|dropRow
argument_list|)
return|;
block|}
comment|// Done by MrDlib
comment|/*if(t.isDataFlavorSupported(MindMapNodesSelection.mindMapNodesFlavor)){                 String xml = (String)t.getTransferData(MindMapNodesSelection.mindMapNodesFlavor);                 URL mindmapURL = null;                 if(t.isDataFlavorSupported(MindMapNodesSelection.mindmapUrlFlavor)){                     mindmapURL = (URL)t.getTransferData(MindMapNodesSelection.mindmapUrlFlavor);                 }                 List<File> files = new ArrayList<File>();                 String[] xmlNodes = xml.split("<nodeseparator>");                 for(String xmlNode : xmlNodes){                     XMLElement element = new XMLElement();                     element.parseString(xmlNode);                     String link = element.getStringAttribute("Link");                     String absoluteLink = Tools.getLink(link, mindmapURL);                     if(absoluteLink == null) continue;                     File file = new File(absoluteLink);                     if(file.exists()){                         files.add(file);                     }                     else{                         try {                             URL url = new URL(absoluteLink);                             file = new File(url.toURI());                             if(file.exists()){                                 files.add(file);                             }                         } catch (URISyntaxException e) {                             // Todo logging                         } catch(IllegalArgumentException e){                             // Todo logging                         } catch(MalformedURLException e){                             // Todo logging                         }                     }                 }                 if(files.size()> 0){                     return handleDraggedFiles(files, dropRow);                 }                 else{                     return false;                 }             }*/
comment|// Done by MrDlib
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
return|return
name|handleDropTransfer
argument_list|(
name|dropLink
argument_list|,
name|dropRow
argument_list|)
return|;
block|}
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
comment|// JOptionPane.showMessageDialog(null, "Received stringFlavor:
comment|// "+dropStr);
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
return|return
name|handleDropTransfer
argument_list|(
name|dropStr
argument_list|,
name|dropRow
argument_list|)
return|;
block|}
block|}
catch|catch
parameter_list|(
name|IOException
name|ioe
parameter_list|)
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"failed to read dropped data: "
operator|+
name|ioe
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|UnsupportedFlavorException
name|ufe
parameter_list|)
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"drop type error: "
operator|+
name|ufe
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|// all supported flavors failed
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"can't transfer input: "
argument_list|)
expr_stmt|;
name|DataFlavor
name|inflavs
index|[]
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
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"  "
operator|+
name|inflav
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
literal|false
return|;
block|}
comment|/** 	 * This method is called to query whether the transfer can be imported. 	 *  	 * Will return true for urls, strings, javaFileLists 	 *  	 * @override 	 */
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
if|if
condition|(
operator|!
name|DROP_ALLOWED
condition|)
return|return
literal|false
return|;
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
return|return
literal|true
return|;
block|}
comment|// System.out.println("drop type forbidden");
comment|// nope, never heard of this type
return|return
literal|false
return|;
block|}
DECL|field|draggingFile
name|boolean
name|draggingFile
init|=
literal|false
decl_stmt|;
DECL|method|exportAsDrag (JComponent comp, InputEvent e, int action)
specifier|public
name|void
name|exportAsDrag
parameter_list|(
name|JComponent
name|comp
parameter_list|,
name|InputEvent
name|e
parameter_list|,
name|int
name|action
parameter_list|)
block|{
comment|/* TODO: add support for dragging file link from table icon into other apps */
if|if
condition|(
name|e
operator|instanceof
name|MouseEvent
condition|)
block|{
name|MouseEvent
name|me
init|=
operator|(
name|MouseEvent
operator|)
name|e
decl_stmt|;
name|int
name|col
init|=
name|entryTable
operator|.
name|columnAtPoint
argument_list|(
name|me
operator|.
name|getPoint
argument_list|()
argument_list|)
decl_stmt|;
name|String
index|[]
name|res
init|=
name|entryTable
operator|.
name|getIconTypeForColumn
argument_list|(
name|col
argument_list|)
decl_stmt|;
if|if
condition|(
name|res
operator|==
literal|null
condition|)
block|{
name|super
operator|.
name|exportAsDrag
argument_list|(
name|comp
argument_list|,
name|e
argument_list|,
name|DnDConstants
operator|.
name|ACTION_LINK
argument_list|)
expr_stmt|;
return|return;
block|}
comment|// We have an icon column:
if|if
condition|(
name|res
operator|==
name|MainTableFormat
operator|.
name|FILE
condition|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"dragging file"
argument_list|)
expr_stmt|;
name|draggingFile
operator|=
literal|true
expr_stmt|;
block|}
block|}
name|super
operator|.
name|exportAsDrag
argument_list|(
name|comp
argument_list|,
name|e
argument_list|,
name|DnDConstants
operator|.
name|ACTION_LINK
argument_list|)
expr_stmt|;
block|}
DECL|method|exportDone (JComponent source, Transferable data, int action)
specifier|protected
name|void
name|exportDone
parameter_list|(
name|JComponent
name|source
parameter_list|,
name|Transferable
name|data
parameter_list|,
name|int
name|action
parameter_list|)
block|{
comment|// default implementation is OK
name|super
operator|.
name|exportDone
argument_list|(
name|source
argument_list|,
name|data
argument_list|,
name|action
argument_list|)
expr_stmt|;
block|}
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
comment|// default implementation is OK
name|super
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
comment|// add-ons -----------------------
DECL|method|handleDropTransfer (String dropStr, final int dropRow)
specifier|protected
name|boolean
name|handleDropTransfer
parameter_list|(
name|String
name|dropStr
parameter_list|,
specifier|final
name|int
name|dropRow
parameter_list|)
throws|throws
name|IOException
block|{
if|if
condition|(
name|dropStr
operator|.
name|startsWith
argument_list|(
literal|"file:"
argument_list|)
condition|)
block|{
comment|// This appears to be a dragged file link and not a reference
comment|// format. Check if we can map this to a set of files:
if|if
condition|(
name|handleDraggedFilenames
argument_list|(
name|dropStr
argument_list|,
name|dropRow
argument_list|)
condition|)
return|return
literal|true
return|;
comment|// If not, handle it in the normal way...
block|}
elseif|else
if|if
condition|(
name|dropStr
operator|.
name|startsWith
argument_list|(
literal|"http:"
argument_list|)
condition|)
block|{
comment|// This is the way URL links are received on OS X and KDE (Gnome?):
name|URL
name|url
init|=
operator|new
name|URL
argument_list|(
name|dropStr
argument_list|)
decl_stmt|;
comment|// JOptionPane.showMessageDialog(null, "Making URL:
comment|// "+url.toString());
return|return
name|handleDropTransfer
argument_list|(
name|url
argument_list|,
name|dropRow
argument_list|)
return|;
block|}
name|File
name|tmpfile
init|=
name|java
operator|.
name|io
operator|.
name|File
operator|.
name|createTempFile
argument_list|(
literal|"jabrefimport"
argument_list|,
literal|""
argument_list|)
decl_stmt|;
name|tmpfile
operator|.
name|deleteOnExit
argument_list|()
expr_stmt|;
name|FileWriter
name|fw
init|=
operator|new
name|FileWriter
argument_list|(
name|tmpfile
argument_list|)
decl_stmt|;
name|fw
operator|.
name|write
argument_list|(
name|dropStr
argument_list|)
expr_stmt|;
name|fw
operator|.
name|close
argument_list|()
expr_stmt|;
comment|// System.out.println("importing from " + tmpfile.getAbsolutePath());
name|ImportMenuItem
name|importer
init|=
operator|new
name|ImportMenuItem
argument_list|(
name|frame
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|importer
operator|.
name|automatedImport
argument_list|(
operator|new
name|String
index|[]
block|{
name|tmpfile
operator|.
name|getAbsolutePath
argument_list|()
block|}
argument_list|)
expr_stmt|;
return|return
literal|true
return|;
block|}
comment|/** 	 * Translate a String describing a set of files or URLs dragged into JabRef      * into a List of File objects, taking care of URL special characters. 	 * 	 * @param s 	 *            String describing a set of files or URLs dragged into JabRef      * @return a List<File> containing the individual file objects.      * 	 */
DECL|method|getFilesFromDraggedFilesString (String s)
specifier|public
specifier|static
name|List
argument_list|<
name|File
argument_list|>
name|getFilesFromDraggedFilesString
parameter_list|(
name|String
name|s
parameter_list|)
block|{
comment|// Split into lines:
name|String
index|[]
name|lines
init|=
name|s
operator|.
name|replaceAll
argument_list|(
literal|"\r"
argument_list|,
literal|""
argument_list|)
operator|.
name|split
argument_list|(
literal|"\n"
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|File
argument_list|>
name|files
init|=
operator|new
name|ArrayList
argument_list|<
name|File
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|line1
range|:
name|lines
control|)
block|{
name|String
name|line
init|=
name|line1
decl_stmt|;
comment|// Try to use url.toURI() to translate URL specific sequences like %20 into
comment|// standard characters:
name|File
name|fl
init|=
literal|null
decl_stmt|;
try|try
block|{
name|URL
name|url
init|=
operator|new
name|URL
argument_list|(
name|line
argument_list|)
decl_stmt|;
name|fl
operator|=
operator|new
name|File
argument_list|(
name|url
operator|.
name|toURI
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|URISyntaxException
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|MalformedURLException
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
comment|// Unless an exception was thrown, we should have the sanitized path:
if|if
condition|(
name|fl
operator|!=
literal|null
condition|)
name|line
operator|=
name|fl
operator|.
name|getPath
argument_list|()
expr_stmt|;
elseif|else
if|if
condition|(
name|line
operator|.
name|startsWith
argument_list|(
literal|"file:"
argument_list|)
condition|)
name|line
operator|=
name|line
operator|.
name|substring
argument_list|(
literal|5
argument_list|)
expr_stmt|;
else|else
continue|continue;
comment|// Under Gnome, the link is given as file:///...., so we
comment|// need to strip the extra slashes:
if|if
condition|(
name|line
operator|.
name|startsWith
argument_list|(
literal|"//"
argument_list|)
condition|)
name|line
operator|=
name|line
operator|.
name|substring
argument_list|(
literal|2
argument_list|)
expr_stmt|;
name|File
name|f
init|=
operator|new
name|File
argument_list|(
name|line
argument_list|)
decl_stmt|;
if|if
condition|(
name|f
operator|.
name|exists
argument_list|()
condition|)
block|{
name|files
operator|.
name|add
argument_list|(
name|f
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|files
return|;
block|}
comment|/** 	 * Handle a String describing a set of files or URLs dragged into JabRef. 	 *  	 * @param s 	 *            String describing a set of files or URLs dragged into JabRef      * @param dropRow The row in the table where the files were dragged.      * @return success status for the operation      * 	 */
DECL|method|handleDraggedFilenames (String s, final int dropRow)
specifier|private
name|boolean
name|handleDraggedFilenames
parameter_list|(
name|String
name|s
parameter_list|,
specifier|final
name|int
name|dropRow
parameter_list|)
block|{
return|return
name|handleDraggedFiles
argument_list|(
name|getFilesFromDraggedFilesString
argument_list|(
name|s
argument_list|)
argument_list|,
name|dropRow
argument_list|)
return|;
block|}
comment|/** 	 * Handle a List containing File objects for a set of files to import. 	 *  	 * @param files 	 *            A List containing File instances pointing to files. 	 * @param dropRow @param dropRow The row in the table where the files were dragged.      * @return success status for the operation 	 */
DECL|method|handleDraggedFiles (List<File> files, final int dropRow)
specifier|private
name|boolean
name|handleDraggedFiles
parameter_list|(
name|List
argument_list|<
name|File
argument_list|>
name|files
parameter_list|,
specifier|final
name|int
name|dropRow
parameter_list|)
block|{
specifier|final
name|String
index|[]
name|fileNames
init|=
operator|new
name|String
index|[
name|files
operator|.
name|size
argument_list|()
index|]
decl_stmt|;
name|int
name|i
init|=
literal|0
decl_stmt|;
for|for
control|(
name|File
name|file
range|:
name|files
control|)
block|{
name|fileNames
index|[
name|i
index|]
operator|=
name|file
operator|.
name|getAbsolutePath
argument_list|()
expr_stmt|;
name|i
operator|++
expr_stmt|;
block|}
comment|// Try to load bib files normally, and import the rest into the current
comment|// database.
comment|// This process must be spun off into a background thread:
operator|new
name|Thread
argument_list|(
operator|new
name|Runnable
argument_list|()
block|{
specifier|public
name|void
name|run
parameter_list|()
block|{
comment|// Done by MrDlib
specifier|final
name|ImportPdfFilesResult
name|importRes
init|=
operator|new
name|PdfImporter
argument_list|(
name|frame
argument_list|,
name|panel
argument_list|,
name|entryTable
argument_list|,
name|dropRow
argument_list|)
operator|.
name|importPdfFiles
argument_list|(
name|fileNames
argument_list|,
name|frame
argument_list|)
decl_stmt|;
if|if
condition|(
name|importRes
operator|.
name|noPdfFiles
operator|.
name|length
operator|>
literal|0
condition|)
block|{
name|loadOrImportFiles
argument_list|(
name|importRes
operator|.
name|noPdfFiles
argument_list|,
name|dropRow
argument_list|)
expr_stmt|;
block|}
comment|//loadOrImportFiles(fileNames, dropRow);
comment|// Done by MrDlib
block|}
block|}
argument_list|)
operator|.
name|start
argument_list|()
expr_stmt|;
return|return
literal|true
return|;
block|}
comment|/** 	 * Take a set of filenames. Those with names indicating bib files are opened 	 * as such if possible. All other files we will attempt to import into the 	 * current database. 	 *  	 * @param fileNames 	 *            The names of the files to open. 	 * @param dropRow success status for the operation 	 */
DECL|method|loadOrImportFiles (String[] fileNames, int dropRow)
specifier|private
name|void
name|loadOrImportFiles
parameter_list|(
name|String
index|[]
name|fileNames
parameter_list|,
name|int
name|dropRow
parameter_list|)
block|{
name|OpenDatabaseAction
name|openAction
init|=
operator|new
name|OpenDatabaseAction
argument_list|(
name|frame
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|ArrayList
argument_list|<
name|String
argument_list|>
name|notBibFiles
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|String
name|encoding
init|=
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
literal|"defaultEncoding"
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|fileName
range|:
name|fileNames
control|)
block|{
comment|// Find the file's extension, if any:
name|String
name|extension
init|=
literal|""
decl_stmt|;
name|ExternalFileType
name|fileType
init|=
literal|null
decl_stmt|;
name|int
name|index
init|=
name|fileName
operator|.
name|lastIndexOf
argument_list|(
literal|'.'
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|index
operator|>=
literal|0
operator|)
operator|&&
operator|(
name|index
operator|<
name|fileName
operator|.
name|length
argument_list|()
operator|)
condition|)
block|{
name|extension
operator|=
name|fileName
operator|.
name|substring
argument_list|(
name|index
operator|+
literal|1
argument_list|)
operator|.
name|toLowerCase
argument_list|()
expr_stmt|;
name|fileType
operator|=
name|Globals
operator|.
name|prefs
operator|.
name|getExternalFileTypeByExt
argument_list|(
name|extension
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|extension
operator|.
name|equals
argument_list|(
literal|"bib"
argument_list|)
condition|)
block|{
name|File
name|f
init|=
operator|new
name|File
argument_list|(
name|fileName
argument_list|)
decl_stmt|;
try|try
block|{
name|ParserResult
name|pr
init|=
name|OpenDatabaseAction
operator|.
name|loadDatabase
argument_list|(
name|f
argument_list|,
name|encoding
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|pr
operator|==
literal|null
operator|)
operator|||
operator|(
name|pr
operator|==
name|ParserResult
operator|.
name|INVALID_FORMAT
operator|)
condition|)
block|{
name|notBibFiles
operator|.
name|add
argument_list|(
name|fileName
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|openAction
operator|.
name|addNewDatabase
argument_list|(
name|pr
argument_list|,
name|f
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|frame
operator|.
name|getFileHistory
argument_list|()
operator|.
name|newFile
argument_list|(
name|fileName
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|notBibFiles
operator|.
name|add
argument_list|(
name|fileName
argument_list|)
expr_stmt|;
comment|// No error message, since we want to try importing the
comment|// file?
comment|//
comment|// Util.showQuickErrorDialog(frame, Globals.lang("Open database"), e);
block|}
continue|continue;
block|}
comment|/*              * This is a linkable file. If the user dropped it on an entry, we 			 * should offer options for autolinking to this files: 			 * 			 * TODO we should offer an option to highlight the row the user is on too. 			 */
if|if
condition|(
name|fileType
operator|!=
literal|null
operator|&&
name|dropRow
operator|>=
literal|0
condition|)
block|{
comment|/* 				 * TODO: need to signal if this is a local or autodownloaded 				 * file 				 */
name|boolean
name|local
init|=
literal|true
decl_stmt|;
comment|/* 				 * TODO: make this an instance variable? 				 */
name|DroppedFileHandler
name|dfh
init|=
operator|new
name|DroppedFileHandler
argument_list|(
name|frame
argument_list|,
name|panel
argument_list|)
decl_stmt|;
name|dfh
operator|.
name|handleDroppedfile
argument_list|(
name|fileName
argument_list|,
name|fileType
argument_list|,
name|local
argument_list|,
name|entryTable
argument_list|,
name|dropRow
argument_list|)
expr_stmt|;
continue|continue;
block|}
comment|/* 			if (extension.equals("pdf")) { 				Collection c; 				try { 					c = XMPUtil.readXMP(fileNames[i]); 				} catch (IOException e1) { 					c = null; 					frame.output(Globals.lang("No XMP metadata found in " + fileNames[i])); 				}  				if (c != null&& c.size()> 0) { 					Iterator it = c.iterator();  					BasePanel panel = frame.basePanel();  					if (panel == null) { 						// // Create a new, empty, database. 						BibtexDatabase database = new BibtexDatabase(); 						frame.addTab(database, null, null, Globals.prefs.get("defaultEncoding"), 							true); 						frame.output(Globals.lang("New database created.")); 						panel = frame.basePanel(); 					}  					BibtexDatabase database = frame.basePanel().database();  					NamedCompound ce = new NamedCompound(Glbals.lang("Drop PDF"));  					while (it.hasNext()) { 						BibtexEntry e = (BibtexEntry) it.next();  						try { 							e.setId(Util.createNeutralId()); 							database.insertEntry(e); 							ce.addEdit(new UndoableInsertEntry(database, e, panel)); 						} catch (Exception e2) { 							// Should not happen? 						} 					}  					ce.end(); 					panel.undoManager.addEdit(ce); 					panel.markBaseChanged(); 					continue; 				} 			} 			*/
name|notBibFiles
operator|.
name|add
argument_list|(
name|fileName
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|notBibFiles
operator|.
name|size
argument_list|()
operator|>
literal|0
condition|)
block|{
name|String
index|[]
name|toImport
init|=
operator|new
name|String
index|[
name|notBibFiles
operator|.
name|size
argument_list|()
index|]
decl_stmt|;
name|notBibFiles
operator|.
name|toArray
argument_list|(
name|toImport
argument_list|)
expr_stmt|;
comment|// Import into new if entryTable==null, otherwise into current
comment|// database:
name|ImportMenuItem
name|importer
init|=
operator|new
name|ImportMenuItem
argument_list|(
name|frame
argument_list|,
operator|(
name|entryTable
operator|==
literal|null
operator|)
argument_list|)
decl_stmt|;
name|importer
operator|.
name|automatedImport
argument_list|(
name|toImport
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|handleDropTransfer (URL dropLink, int dropRow)
specifier|protected
name|boolean
name|handleDropTransfer
parameter_list|(
name|URL
name|dropLink
parameter_list|,
name|int
name|dropRow
parameter_list|)
throws|throws
name|IOException
block|{
name|File
name|tmpfile
init|=
name|java
operator|.
name|io
operator|.
name|File
operator|.
name|createTempFile
argument_list|(
literal|"jabrefimport"
argument_list|,
literal|""
argument_list|)
decl_stmt|;
name|tmpfile
operator|.
name|deleteOnExit
argument_list|()
expr_stmt|;
comment|// System.out.println("Import url: " + dropLink.toString());
comment|// System.out.println("Temp file: "+tmpfile.getAbsolutePath());
operator|new
name|URLDownload
argument_list|(
name|entryTable
argument_list|,
name|dropLink
argument_list|,
name|tmpfile
argument_list|)
operator|.
name|download
argument_list|()
expr_stmt|;
comment|// Import into new if entryTable==null, otherwise into current database:
name|ImportMenuItem
name|importer
init|=
operator|new
name|ImportMenuItem
argument_list|(
name|frame
argument_list|,
operator|(
name|entryTable
operator|==
literal|null
operator|)
argument_list|)
decl_stmt|;
name|importer
operator|.
name|automatedImport
argument_list|(
operator|new
name|String
index|[]
block|{
name|tmpfile
operator|.
name|getAbsolutePath
argument_list|()
block|}
argument_list|)
expr_stmt|;
return|return
literal|true
return|;
block|}
block|}
end_class

end_unit

