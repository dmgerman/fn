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
name|*
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
name|InputEvent
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
name|util
operator|.
name|*
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
name|javax
operator|.
name|swing
operator|.
name|tree
operator|.
name|*
import|;
end_import

begin_class
DECL|class|GroupsTree
specifier|public
class|class
name|GroupsTree
extends|extends
name|JTree
implements|implements
name|DragSourceListener
implements|,
name|DropTargetListener
implements|,
name|DragGestureListener
block|{
DECL|field|groupSelector
specifier|private
name|GroupSelector
name|groupSelector
decl_stmt|;
DECL|field|dragNode
specifier|private
name|GroupTreeNode
name|dragNode
init|=
literal|null
decl_stmt|;
DECL|method|GroupsTree (GroupSelector groupSelector)
specifier|public
name|GroupsTree
parameter_list|(
name|GroupSelector
name|groupSelector
parameter_list|)
block|{
name|this
operator|.
name|groupSelector
operator|=
name|groupSelector
expr_stmt|;
name|DragGestureRecognizer
name|dgr
init|=
name|DragSource
operator|.
name|getDefaultDragSource
argument_list|()
operator|.
name|createDefaultDragGestureRecognizer
argument_list|(
name|this
argument_list|,
name|DnDConstants
operator|.
name|ACTION_MOVE
argument_list|,
name|this
argument_list|)
decl_stmt|;
comment|// Eliminates right mouse clicks as valid actions
name|dgr
operator|.
name|setSourceActions
argument_list|(
name|dgr
operator|.
name|getSourceActions
argument_list|()
operator|&
operator|~
name|InputEvent
operator|.
name|BUTTON3_MASK
argument_list|)
expr_stmt|;
name|DropTarget
name|dropTarget
init|=
operator|new
name|DropTarget
argument_list|(
name|this
argument_list|,
name|this
argument_list|)
decl_stmt|;
block|}
DECL|method|dragEnter (DragSourceDragEvent dsde)
specifier|public
name|void
name|dragEnter
parameter_list|(
name|DragSourceDragEvent
name|dsde
parameter_list|)
block|{
comment|// ignore
block|}
DECL|method|dragOver (DragSourceDragEvent dsde)
specifier|public
name|void
name|dragOver
parameter_list|(
name|DragSourceDragEvent
name|dsde
parameter_list|)
block|{
name|Point
name|p
init|=
name|dsde
operator|.
name|getLocation
argument_list|()
decl_stmt|;
comment|// screen coordinates!
name|SwingUtilities
operator|.
name|convertPointFromScreen
argument_list|(
name|p
argument_list|,
name|this
argument_list|)
expr_stmt|;
name|TreePath
name|path
init|=
name|getPathForLocation
argument_list|(
name|p
operator|.
name|x
argument_list|,
name|p
operator|.
name|y
argument_list|)
decl_stmt|;
if|if
condition|(
name|path
operator|==
literal|null
condition|)
block|{
name|dsde
operator|.
name|getDragSourceContext
argument_list|()
operator|.
name|setCursor
argument_list|(
name|DragSource
operator|.
name|DefaultMoveNoDrop
argument_list|)
expr_stmt|;
return|return;
block|}
name|GroupTreeNode
name|target
init|=
operator|(
name|GroupTreeNode
operator|)
name|path
operator|.
name|getLastPathComponent
argument_list|()
decl_stmt|;
if|if
condition|(
name|dragNode
operator|.
name|isNodeChild
argument_list|(
name|target
argument_list|)
operator|||
name|dragNode
operator|==
name|target
condition|)
block|{
name|dsde
operator|.
name|getDragSourceContext
argument_list|()
operator|.
name|setCursor
argument_list|(
name|DragSource
operator|.
name|DefaultMoveNoDrop
argument_list|)
expr_stmt|;
return|return;
block|}
name|dsde
operator|.
name|getDragSourceContext
argument_list|()
operator|.
name|setCursor
argument_list|(
name|DragSource
operator|.
name|DefaultMoveDrop
argument_list|)
expr_stmt|;
block|}
DECL|method|dropActionChanged (DragSourceDragEvent dsde)
specifier|public
name|void
name|dropActionChanged
parameter_list|(
name|DragSourceDragEvent
name|dsde
parameter_list|)
block|{
comment|// ignore
block|}
DECL|method|dragDropEnd (DragSourceDropEvent dsde)
specifier|public
name|void
name|dragDropEnd
parameter_list|(
name|DragSourceDropEvent
name|dsde
parameter_list|)
block|{
name|dragNode
operator|=
literal|null
expr_stmt|;
block|}
DECL|method|dragExit (DragSourceEvent dse)
specifier|public
name|void
name|dragExit
parameter_list|(
name|DragSourceEvent
name|dse
parameter_list|)
block|{
comment|// ignore
block|}
DECL|method|dragEnter (DropTargetDragEvent dtde)
specifier|public
name|void
name|dragEnter
parameter_list|(
name|DropTargetDragEvent
name|dtde
parameter_list|)
block|{
comment|// ignore
block|}
DECL|method|dragOver (DropTargetDragEvent dtde)
specifier|public
name|void
name|dragOver
parameter_list|(
name|DropTargetDragEvent
name|dtde
parameter_list|)
block|{
comment|// Point p = dtde.getLocation();
comment|// TreePath path = getPathForLocation(p.x, p.y);
comment|// if (path == null) {
comment|// dtde.rejectDrag();
comment|// return;
comment|// }
comment|// GroupTreeNode target = (GroupTreeNode) path.getLastPathComponent();
comment|// if (dragNode.isNodeChild(target)) {
comment|// dtde.rejectDrag();
comment|// return;
comment|// }
comment|// dtde.acceptDrag(DnDConstants.ACTION_COPY_OR_MOVE);
block|}
DECL|method|dropActionChanged (DropTargetDragEvent dtde)
specifier|public
name|void
name|dropActionChanged
parameter_list|(
name|DropTargetDragEvent
name|dtde
parameter_list|)
block|{
comment|// ignore
block|}
DECL|method|drop (DropTargetDropEvent dtde)
specifier|public
name|void
name|drop
parameter_list|(
name|DropTargetDropEvent
name|dtde
parameter_list|)
block|{
try|try
block|{
name|Transferable
name|transferable
init|=
name|dtde
operator|.
name|getTransferable
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|transferable
operator|.
name|isDataFlavorSupported
argument_list|(
name|GroupTreeNode
operator|.
name|flavor
argument_list|)
condition|)
block|{
name|dtde
operator|.
name|rejectDrop
argument_list|()
expr_stmt|;
return|return;
block|}
name|Point
name|p
init|=
name|dtde
operator|.
name|getLocation
argument_list|()
decl_stmt|;
name|TreePath
name|path
init|=
name|getPathForLocation
argument_list|(
name|p
operator|.
name|x
argument_list|,
name|p
operator|.
name|y
argument_list|)
decl_stmt|;
if|if
condition|(
name|path
operator|==
literal|null
condition|)
block|{
name|dtde
operator|.
name|rejectDrop
argument_list|()
expr_stmt|;
return|return;
block|}
name|GroupTreeNode
name|target
init|=
operator|(
name|GroupTreeNode
operator|)
name|path
operator|.
name|getLastPathComponent
argument_list|()
decl_stmt|;
name|GroupTreeNode
name|source
init|=
operator|(
name|GroupTreeNode
operator|)
name|transferable
operator|.
name|getTransferData
argument_list|(
name|GroupTreeNode
operator|.
name|flavor
argument_list|)
decl_stmt|;
if|if
condition|(
name|source
operator|==
name|target
condition|)
block|{
name|dtde
operator|.
name|rejectDrop
argument_list|()
expr_stmt|;
comment|// ignore this
return|return;
block|}
if|if
condition|(
name|source
operator|.
name|isNodeChild
argument_list|(
name|target
argument_list|)
condition|)
block|{
name|dtde
operator|.
name|rejectDrop
argument_list|()
expr_stmt|;
comment|// JZTODO invokeLater: error message; e.g. status line
return|return;
block|}
name|Enumeration
name|expandedPaths
init|=
name|groupSelector
operator|.
name|getExpandedPaths
argument_list|()
decl_stmt|;
name|UndoableMoveGroup
name|undo
init|=
operator|new
name|UndoableMoveGroup
argument_list|(
name|groupSelector
argument_list|,
name|groupSelector
operator|.
name|getGroupTreeRoot
argument_list|()
argument_list|,
name|source
argument_list|,
name|target
argument_list|,
name|target
operator|.
name|getChildCount
argument_list|()
argument_list|)
decl_stmt|;
name|target
operator|.
name|add
argument_list|(
name|source
argument_list|)
expr_stmt|;
name|dtde
operator|.
name|getDropTargetContext
argument_list|()
operator|.
name|dropComplete
argument_list|(
literal|true
argument_list|)
expr_stmt|;
comment|// update selection/expansion state
name|groupSelector
operator|.
name|revalidateGroups
argument_list|(
operator|new
name|TreePath
index|[]
block|{
operator|new
name|TreePath
argument_list|(
name|source
operator|.
name|getPath
argument_list|()
argument_list|)
block|}
argument_list|,
name|refreshPaths
argument_list|(
name|expandedPaths
argument_list|)
argument_list|)
expr_stmt|;
name|groupSelector
operator|.
name|concludeMoveGroup
argument_list|(
name|undo
argument_list|,
name|source
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|ioe
parameter_list|)
block|{
comment|// ignore
block|}
catch|catch
parameter_list|(
name|UnsupportedFlavorException
name|e
parameter_list|)
block|{
comment|// ignore
block|}
block|}
DECL|method|dragExit (DropTargetEvent dte)
specifier|public
name|void
name|dragExit
parameter_list|(
name|DropTargetEvent
name|dte
parameter_list|)
block|{
comment|// ignore
block|}
DECL|method|dragGestureRecognized (DragGestureEvent dge)
specifier|public
name|void
name|dragGestureRecognized
parameter_list|(
name|DragGestureEvent
name|dge
parameter_list|)
block|{
name|GroupTreeNode
name|selectedNode
init|=
name|getSelectedNode
argument_list|()
decl_stmt|;
if|if
condition|(
name|selectedNode
operator|==
literal|null
condition|)
return|return;
comment|// nothing to transfer (select manually?)
name|Cursor
name|cursor
init|=
name|dge
operator|.
name|getDragAction
argument_list|()
operator|==
name|DnDConstants
operator|.
name|ACTION_MOVE
condition|?
name|DragSource
operator|.
name|DefaultMoveDrop
else|:
name|DragSource
operator|.
name|DefaultCopyDrop
decl_stmt|;
name|dragNode
operator|=
name|selectedNode
expr_stmt|;
name|dge
operator|.
name|getDragSource
argument_list|()
operator|.
name|startDrag
argument_list|(
name|dge
argument_list|,
name|cursor
argument_list|,
name|selectedNode
argument_list|,
name|this
argument_list|)
expr_stmt|;
block|}
comment|/** Returns the first selected node, or null if nothing is selected. */
DECL|method|getSelectedNode ()
specifier|public
name|GroupTreeNode
name|getSelectedNode
parameter_list|()
block|{
name|TreePath
name|selectionPath
init|=
name|getSelectionPath
argument_list|()
decl_stmt|;
return|return
name|selectionPath
operator|!=
literal|null
condition|?
operator|(
name|GroupTreeNode
operator|)
name|selectionPath
operator|.
name|getLastPathComponent
argument_list|()
else|:
literal|null
return|;
block|}
comment|/** Refresh paths that may have become invalid due to node movements      * within the tree. This method creates new paths to the last path      * components (which must still exist) of the specified paths.      * @param paths Paths that may have become invalid.      * @return Refreshed paths that are all valid.      */
DECL|method|refreshPaths (Enumeration paths)
specifier|public
name|Enumeration
name|refreshPaths
parameter_list|(
name|Enumeration
name|paths
parameter_list|)
block|{
name|Vector
name|freshPaths
init|=
operator|new
name|Vector
argument_list|()
decl_stmt|;
while|while
condition|(
name|paths
operator|.
name|hasMoreElements
argument_list|()
condition|)
block|{
name|freshPaths
operator|.
name|add
argument_list|(
operator|new
name|TreePath
argument_list|(
operator|(
call|(
name|DefaultMutableTreeNode
call|)
argument_list|(
operator|(
name|TreePath
operator|)
name|paths
operator|.
name|nextElement
argument_list|()
argument_list|)
operator|.
name|getLastPathComponent
argument_list|()
operator|)
operator|.
name|getPath
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|freshPaths
operator|.
name|elements
argument_list|()
return|;
block|}
block|}
end_class

end_unit

