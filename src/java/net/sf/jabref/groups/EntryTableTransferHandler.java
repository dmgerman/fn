begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  All programs in this directory and subdirectories are published under the   GNU General Public License as described below.   This program is free software; you can redistribute it and/or modify it   under the terms of the GNU General Public License as published by the Free   Software Foundation; either version 2 of the License, or (at your option)   any later version.   This program is distributed in the hope that it will be useful, but WITHOUT   ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or   FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for   more details.   You should have received a copy of the GNU General Public License along   with this program; if not, write to the Free Software Foundation, Inc., 59   Temple Place, Suite 330, Boston, MA 02111-1307 USA   Further information about the GNU GPL is available at:  http://www.gnu.org/copyleft/gpl.ja.html  */
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
name|io
operator|.
name|*
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
name|javax
operator|.
name|swing
operator|.
name|*
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
DECL|method|EntryTableTransferHandler (MainTable entryTable, JabRefFrame frame)
specifier|public
name|EntryTableTransferHandler
parameter_list|(
name|MainTable
name|entryTable
parameter_list|,
name|JabRefFrame
name|frame
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
DECL|method|createTransferable (JComponent c)
specifier|public
name|Transferable
name|createTransferable
parameter_list|(
name|JComponent
name|c
parameter_list|)
block|{
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
comment|// add-ons -----------------------
DECL|method|handleDropTransfer (String dropStr)
specifier|protected
name|boolean
name|handleDropTransfer
parameter_list|(
name|String
name|dropStr
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
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
literal|null
argument_list|,
literal|"Making URL: "
operator|+
name|url
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|handleDropTransfer
argument_list|(
name|url
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
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"importing from "
operator|+
name|tmpfile
operator|.
name|getAbsolutePath
argument_list|()
argument_list|)
expr_stmt|;
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
comment|/**      * Handle a String describing a set of files or URLs dragged into JabRef.      * @param s String describing a set of files or URLs dragged into JabRef      */
DECL|method|handleDraggedFilenames (String s)
specifier|private
name|boolean
name|handleDraggedFilenames
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
name|files
init|=
operator|new
name|ArrayList
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|lines
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|String
name|line
init|=
name|lines
index|[
name|i
index|]
decl_stmt|;
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
name|handleDraggedFiles
argument_list|(
name|files
argument_list|)
return|;
block|}
comment|/**      * Handle a List containing File objects for a set of files to import.      * @param files A List containing File instances pointing to files.      */
DECL|method|handleDraggedFiles (List files)
specifier|private
name|boolean
name|handleDraggedFiles
parameter_list|(
name|List
name|files
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
name|Iterator
name|iterator
init|=
name|files
operator|.
name|iterator
argument_list|()
init|;
name|iterator
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|File
name|file
init|=
operator|(
name|File
operator|)
name|iterator
operator|.
name|next
argument_list|()
decl_stmt|;
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
comment|// Try to load bib files normally, and import the rest into the current database.
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
name|loadOrImportFiles
argument_list|(
name|fileNames
argument_list|)
expr_stmt|;
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
comment|/**      * Take a set of filenames. Those with names indicating bib files are opened as such      * if possible. All other files we will attempt to import into the current database.      * @param fileNames The names of the files to open.      */
DECL|method|loadOrImportFiles (String[] fileNames)
specifier|private
name|void
name|loadOrImportFiles
parameter_list|(
name|String
index|[]
name|fileNames
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
name|notBibFiles
init|=
operator|new
name|ArrayList
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
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|fileNames
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|fileNames
index|[
name|i
index|]
operator|.
name|endsWith
argument_list|(
literal|".bib"
argument_list|)
condition|)
block|{
name|File
name|f
init|=
operator|new
name|File
argument_list|(
name|fileNames
index|[
name|i
index|]
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
name|fileNames
index|[
name|i
index|]
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
literal|false
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
name|fileNames
index|[
name|i
index|]
argument_list|)
expr_stmt|;
comment|// No error message, since we want to try importing the file?
comment|//
comment|//Util.showQuickErrorDialog(frame, Globals.lang("Open database"), e);
block|}
block|}
else|else
name|notBibFiles
operator|.
name|add
argument_list|(
name|fileNames
index|[
name|i
index|]
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
name|toImport
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|handleDropTransfer (URL dropLink)
specifier|protected
name|boolean
name|handleDropTransfer
parameter_list|(
name|URL
name|dropLink
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
comment|//System.out.println("Import url: " + dropLink.toString());
comment|//System.out.println("Temp file: "+tmpfile.getAbsolutePath());
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
comment|// JabRef.importFiletypeUnknown(tmpfile.getAbsolutePath());
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
comment|/**      * Imports the dropped URL or plain text as a new entry in the current database.      * @todo It would be nice to support dropping of pdfs onto the table as a way      *       to link them to the corresponding entries.      */
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
try|try
block|{
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
comment|//JOptionPane.showMessageDialog(null, "Received javaFileListFlavor");
comment|// This flavor is used for dragged file links in Windows:
name|List
name|l
init|=
operator|(
name|List
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
argument_list|)
return|;
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
return|return
name|handleDropTransfer
argument_list|(
name|dropLink
argument_list|)
return|;
block|}
elseif|else
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
comment|//JOptionPane.showMessageDialog(null, "Received stringFlavor: "+dropStr);
return|return
name|handleDropTransfer
argument_list|(
name|dropStr
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
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|inflavs
operator|.
name|length
condition|;
name|i
operator|++
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
name|inflavs
index|[
name|i
index|]
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
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|transferFlavors
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|DataFlavor
name|inflav
init|=
name|transferFlavors
index|[
name|i
index|]
decl_stmt|;
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
comment|//System.out.println("drop type forbidden");
comment|// nope, never heard of this type
return|return
literal|false
return|;
block|}
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
comment|// action is always LINK
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
block|}
end_class

end_unit

