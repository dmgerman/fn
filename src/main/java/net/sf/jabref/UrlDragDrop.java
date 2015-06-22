begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2004 E. Putrycz   All programs in this directory and  subdirectories are published under the GNU General Public License as  described below.   This program is free software; you can redistribute it and/or modify  it under the terms of the GNU General Public License as published by  the Free Software Foundation; either version 2 of the License, or (at  your option) any later version.   This program is distributed in the hope that it will be useful, but  WITHOUT ANY WARRANTY; without even the implied warranty of  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU  General Public License for more details.   You should have received a copy of the GNU General Public License  along with this program; if not, write to the Free Software  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA   Further information about the GNU GPL is available at:  http://www.gnu.org/copyleft/gpl.ja.html   */
end_comment

begin_package
DECL|package|net.sf.jabref
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
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
name|awt
operator|.
name|dnd
operator|.
name|DropTargetDragEvent
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
name|DropTargetDropEvent
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
name|DropTargetEvent
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
name|DropTargetListener
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
name|logging
operator|.
name|Level
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|logging
operator|.
name|Logger
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JOptionPane
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

begin_comment
comment|/**  * @author Erik Putrycz erik.putrycz-at-nrc-cnrc.gc.ca  */
end_comment

begin_class
DECL|class|UrlDragDrop
specifier|public
class|class
name|UrlDragDrop
implements|implements
name|DropTargetListener
block|{
DECL|field|logger
specifier|private
specifier|static
specifier|final
name|Logger
name|logger
init|=
name|Logger
operator|.
name|getLogger
argument_list|(
name|UrlDragDrop
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
DECL|field|feditor
specifier|private
specifier|final
name|FieldEditor
name|feditor
decl_stmt|;
DECL|field|editor
specifier|private
specifier|final
name|EntryEditor
name|editor
decl_stmt|;
DECL|field|frame
specifier|private
specifier|final
name|JabRefFrame
name|frame
decl_stmt|;
DECL|method|UrlDragDrop (EntryEditor _editor, JabRefFrame _frame, FieldEditor _feditor)
specifier|public
name|UrlDragDrop
parameter_list|(
name|EntryEditor
name|_editor
parameter_list|,
name|JabRefFrame
name|_frame
parameter_list|,
name|FieldEditor
name|_feditor
parameter_list|)
block|{
name|editor
operator|=
name|_editor
expr_stmt|;
name|feditor
operator|=
name|_feditor
expr_stmt|;
name|frame
operator|=
name|_frame
expr_stmt|;
block|}
comment|/*      * (non-Javadoc)      *      * @see java.awt.dnd.DropTargetListener#dragEnter(java.awt.dnd.DropTargetDragEvent)      */
DECL|method|dragEnter (DropTargetDragEvent dtde)
specifier|public
name|void
name|dragEnter
parameter_list|(
name|DropTargetDragEvent
name|dtde
parameter_list|)
block|{     }
comment|/*      * (non-Javadoc)      *      * @see java.awt.dnd.DropTargetListener#dragOver(java.awt.dnd.DropTargetDragEvent)      */
DECL|method|dragOver (DropTargetDragEvent dtde)
specifier|public
name|void
name|dragOver
parameter_list|(
name|DropTargetDragEvent
name|dtde
parameter_list|)
block|{     }
comment|/*      * (non-Javadoc)      *      * @see java.awt.dnd.DropTargetListener#dropActionChanged(java.awt.dnd.DropTargetDragEvent)      */
DECL|method|dropActionChanged (DropTargetDragEvent dtde)
specifier|public
name|void
name|dropActionChanged
parameter_list|(
name|DropTargetDragEvent
name|dtde
parameter_list|)
block|{     }
comment|/*      * (non-Javadoc)      *      * @see java.awt.dnd.DropTargetListener#dragExit(java.awt.dnd.DropTargetEvent)      */
DECL|method|dragExit (DropTargetEvent dte)
specifier|public
name|void
name|dragExit
parameter_list|(
name|DropTargetEvent
name|dte
parameter_list|)
block|{     }
DECL|class|JOptionChoice
specifier|private
specifier|static
class|class
name|JOptionChoice
block|{
DECL|field|label
specifier|private
specifier|final
name|String
name|label
decl_stmt|;
DECL|field|id
specifier|private
specifier|final
name|int
name|id
decl_stmt|;
DECL|method|JOptionChoice (String _label, int _id)
specifier|public
name|JOptionChoice
parameter_list|(
name|String
name|_label
parameter_list|,
name|int
name|_id
parameter_list|)
block|{
name|label
operator|=
name|_label
expr_stmt|;
name|id
operator|=
name|_id
expr_stmt|;
block|}
DECL|method|toString ()
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|label
return|;
block|}
DECL|method|getId ()
specifier|public
name|int
name|getId
parameter_list|()
block|{
return|return
name|id
return|;
block|}
block|}
comment|/*      * (non-Javadoc)      *      * @see java.awt.dnd.DropTargetListener#drop(java.awt.dnd.DropTargetDropEvent)      */
DECL|method|drop (DropTargetDropEvent dtde)
specifier|public
name|void
name|drop
parameter_list|(
name|DropTargetDropEvent
name|dtde
parameter_list|)
block|{
name|Transferable
name|tsf
init|=
name|dtde
operator|.
name|getTransferable
argument_list|()
decl_stmt|;
name|dtde
operator|.
name|acceptDrop
argument_list|(
name|DnDConstants
operator|.
name|ACTION_COPY_OR_MOVE
argument_list|)
expr_stmt|;
comment|//try with an URL
name|DataFlavor
name|dtURL
init|=
literal|null
decl_stmt|;
try|try
block|{
name|dtURL
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
name|logger
operator|.
name|log
argument_list|(
name|Level
operator|.
name|WARNING
argument_list|,
literal|"Class not found for DnD... should not happen"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
try|try
block|{
name|URL
name|url
init|=
operator|(
name|URL
operator|)
name|tsf
operator|.
name|getTransferData
argument_list|(
name|dtURL
argument_list|)
decl_stmt|;
name|JOptionChoice
name|res
init|=
operator|(
name|JOptionChoice
operator|)
name|JOptionPane
operator|.
name|showInputDialog
argument_list|(
name|editor
argument_list|,
literal|""
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Select action"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|QUESTION_MESSAGE
argument_list|,
literal|null
argument_list|,
operator|new
name|JOptionChoice
index|[]
block|{
operator|new
name|JOptionChoice
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Insert URL"
argument_list|)
argument_list|,
literal|0
argument_list|)
block|,
operator|new
name|JOptionChoice
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Download file"
argument_list|)
argument_list|,
literal|1
argument_list|)
block|}
argument_list|,
operator|new
name|JOptionChoice
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Insert URL"
argument_list|)
argument_list|,
literal|0
argument_list|)
argument_list|)
decl_stmt|;
switch|switch
condition|(
name|res
operator|.
name|getId
argument_list|()
condition|)
block|{
comment|//insert URL
case|case
literal|0
case|:
name|feditor
operator|.
name|setText
argument_list|(
name|url
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|editor
operator|.
name|updateField
argument_list|(
name|feditor
argument_list|)
expr_stmt|;
break|break;
comment|//download file
case|case
literal|1
case|:
try|try
block|{
comment|//auto file name:
name|File
name|file
init|=
operator|new
name|File
argument_list|(
operator|new
name|File
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
literal|"pdfDirectory"
argument_list|)
argument_list|)
argument_list|,
name|editor
operator|.
name|getEntry
argument_list|()
operator|.
name|getField
argument_list|(
name|BibtexFields
operator|.
name|KEY_FIELD
argument_list|)
operator|+
literal|".pdf"
argument_list|)
decl_stmt|;
name|frame
operator|.
name|output
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Downloading..."
argument_list|)
argument_list|)
expr_stmt|;
name|URLDownload
operator|.
name|buildMonitoredDownload
argument_list|(
name|editor
argument_list|,
name|url
argument_list|)
operator|.
name|downloadToFile
argument_list|(
name|file
argument_list|)
expr_stmt|;
name|frame
operator|.
name|output
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Download completed"
argument_list|)
argument_list|)
expr_stmt|;
name|feditor
operator|.
name|setText
argument_list|(
name|file
operator|.
name|toURI
argument_list|()
operator|.
name|toURL
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|editor
operator|.
name|updateField
argument_list|(
name|feditor
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|ioex
parameter_list|)
block|{
name|logger
operator|.
name|log
argument_list|(
name|Level
operator|.
name|SEVERE
argument_list|,
literal|"Error while downloading file"
argument_list|,
name|ioex
argument_list|)
expr_stmt|;
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|editor
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"File download"
argument_list|)
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Error while downloading file:"
operator|+
name|ioex
operator|.
name|getMessage
argument_list|()
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
block|}
break|break;
block|}
return|return;
block|}
catch|catch
parameter_list|(
name|UnsupportedFlavorException
name|nfe
parameter_list|)
block|{
comment|// not an URL then...
block|}
catch|catch
parameter_list|(
name|IOException
name|ioex
parameter_list|)
block|{
name|logger
operator|.
name|log
argument_list|(
name|Level
operator|.
name|WARNING
argument_list|,
literal|"!should not happen!"
argument_list|,
name|ioex
argument_list|)
expr_stmt|;
block|}
try|try
block|{
comment|//try with a File List
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
name|List
argument_list|<
name|File
argument_list|>
name|filelist
init|=
operator|(
name|List
argument_list|<
name|File
argument_list|>
operator|)
name|tsf
operator|.
name|getTransferData
argument_list|(
name|DataFlavor
operator|.
name|javaFileListFlavor
argument_list|)
decl_stmt|;
if|if
condition|(
name|filelist
operator|.
name|size
argument_list|()
operator|>
literal|1
condition|)
block|{
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|editor
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Only one item is supported"
argument_list|)
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Drag and Drop Error"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
return|return;
block|}
name|File
name|fl
init|=
name|filelist
operator|.
name|get
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|feditor
operator|.
name|setText
argument_list|(
name|fl
operator|.
name|toURI
argument_list|()
operator|.
name|toURL
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|editor
operator|.
name|updateField
argument_list|(
name|feditor
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|UnsupportedFlavorException
name|nfe
parameter_list|)
block|{
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|editor
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Operation not supported"
argument_list|)
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Drag and Drop Error"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
name|logger
operator|.
name|log
argument_list|(
name|Level
operator|.
name|WARNING
argument_list|,
literal|"Transfer exception"
argument_list|,
name|nfe
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|ioex
parameter_list|)
block|{
name|logger
operator|.
name|log
argument_list|(
name|Level
operator|.
name|WARNING
argument_list|,
literal|"Transfer exception"
argument_list|,
name|ioex
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

