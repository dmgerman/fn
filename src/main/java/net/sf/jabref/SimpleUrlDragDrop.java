begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* Copyright (C) 2004 E. Putrycz  All programs in this directory and subdirectories are published under the GNU General Public License as described below.  This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.  You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA  Further information about the GNU GPL is available at: http://www.gnu.org/copyleft/gpl.ja.html  */
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
name|Component
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
name|*
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
name|ActionEvent
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
name|EntryEditor
operator|.
name|StoreFieldAction
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

begin_comment
comment|/**  * @author Erik Putrycz erik.putrycz-at-nrc-cnrc.gc.ca  */
end_comment

begin_class
DECL|class|SimpleUrlDragDrop
class|class
name|SimpleUrlDragDrop
implements|implements
name|DropTargetListener
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
name|SimpleUrlDragDrop
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|editor
specifier|private
specifier|final
name|FieldEditor
name|editor
decl_stmt|;
DECL|field|storeFieldAction
specifier|private
specifier|final
name|StoreFieldAction
name|storeFieldAction
decl_stmt|;
DECL|method|SimpleUrlDragDrop (FieldEditor _editor, StoreFieldAction _storeFieldAction)
specifier|public
name|SimpleUrlDragDrop
parameter_list|(
name|FieldEditor
name|_editor
parameter_list|,
name|StoreFieldAction
name|_storeFieldAction
parameter_list|)
block|{
name|editor
operator|=
name|_editor
expr_stmt|;
name|storeFieldAction
operator|=
name|_storeFieldAction
expr_stmt|;
block|}
comment|/*      * (non-Javadoc)      *       * @see java.awt.dnd.DropTargetListener#dragEnter(java.awt.dnd.DropTargetDragEvent)      */
annotation|@
name|Override
DECL|method|dragEnter (DropTargetDragEvent dtde)
specifier|public
name|void
name|dragEnter
parameter_list|(
name|DropTargetDragEvent
name|dtde
parameter_list|)
block|{     }
comment|/*      * (non-Javadoc)      *       * @see java.awt.dnd.DropTargetListener#dragOver(java.awt.dnd.DropTargetDragEvent)      */
annotation|@
name|Override
DECL|method|dragOver (DropTargetDragEvent dtde)
specifier|public
name|void
name|dragOver
parameter_list|(
name|DropTargetDragEvent
name|dtde
parameter_list|)
block|{     }
comment|/*      * (non-Javadoc)      *       * @see java.awt.dnd.DropTargetListener#dropActionChanged(java.awt.dnd.DropTargetDragEvent)      */
annotation|@
name|Override
DECL|method|dropActionChanged (DropTargetDragEvent dtde)
specifier|public
name|void
name|dropActionChanged
parameter_list|(
name|DropTargetDragEvent
name|dtde
parameter_list|)
block|{     }
comment|/*      * (non-Javadoc)      *       * @see java.awt.dnd.DropTargetListener#dragExit(java.awt.dnd.DropTargetEvent)      */
annotation|@
name|Override
DECL|method|dragExit (DropTargetEvent dte)
specifier|public
name|void
name|dragExit
parameter_list|(
name|DropTargetEvent
name|dte
parameter_list|)
block|{     }
comment|/*      * (non-Javadoc)      *       * @see java.awt.dnd.DropTargetListener#drop(java.awt.dnd.DropTargetDropEvent)      */
annotation|@
name|Override
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
name|LOGGER
operator|.
name|debug
argument_list|(
literal|"Could not find DropTargetDropEvent class"
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
comment|//insert URL
name|editor
operator|.
name|setText
argument_list|(
name|url
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|storeFieldAction
operator|.
name|actionPerformed
argument_list|(
operator|new
name|ActionEvent
argument_list|(
name|editor
argument_list|,
literal|0
argument_list|,
literal|""
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|UnsupportedFlavorException
name|nfe
parameter_list|)
block|{
comment|// if not an URL
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
operator|(
name|Component
operator|)
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
name|LOGGER
operator|.
name|debug
argument_list|(
literal|"Could not perform drage and drop"
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
name|LOGGER
operator|.
name|debug
argument_list|(
literal|"Could not perform drage and drop"
argument_list|,
name|ioex
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

