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
name|event
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
name|event
operator|.
name|TreeExpansionListener
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

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|undo
operator|.
name|AbstractUndoableEdit
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
name|BibtexEntry
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
implements|,
name|TreeExpansionListener
block|{
comment|/** distance from component borders from which on autoscrolling starts. */
DECL|field|dragScrollActivationMargin
specifier|private
specifier|static
specifier|final
name|int
name|dragScrollActivationMargin
init|=
literal|10
decl_stmt|;
comment|/** number of pixels to scroll each time handler is called. */
DECL|field|dragScrollDistance
specifier|private
specifier|static
specifier|final
name|int
name|dragScrollDistance
init|=
literal|5
decl_stmt|;
comment|/** time of last autoscroll event (for limiting speed). */
DECL|field|lastDragAutoscroll
specifier|private
specifier|static
name|long
name|lastDragAutoscroll
init|=
literal|0L
decl_stmt|;
comment|/** minimum interval between two autoscroll events (for limiting speed). */
DECL|field|minAutoscrollInterval
specifier|private
specifier|static
specifier|final
name|long
name|minAutoscrollInterval
init|=
literal|50L
decl_stmt|;
comment|/**      * the point on which the cursor is currently idling during a drag      * operation.      */
DECL|field|idlePoint
specifier|private
name|Point
name|idlePoint
decl_stmt|;
comment|/** time since which cursor is idling. */
DECL|field|idleStartTime
specifier|private
name|long
name|idleStartTime
init|=
literal|0L
decl_stmt|;
comment|/** max. distance cursor may move in x or y direction while idling. */
DECL|field|idleMargin
specifier|private
specifier|static
specifier|final
name|int
name|idleMargin
init|=
literal|1
decl_stmt|;
comment|/** idle time after which the node below is expanded. */
DECL|field|idleTimeToExpandNode
specifier|private
specifier|static
specifier|final
name|long
name|idleTimeToExpandNode
init|=
literal|1000L
decl_stmt|;
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
DECL|field|cellRenderer
specifier|private
specifier|final
name|GroupTreeCellRenderer
name|cellRenderer
init|=
operator|new
name|GroupTreeCellRenderer
argument_list|()
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
name|setCellRenderer
argument_list|(
name|cellRenderer
argument_list|)
expr_stmt|;
name|setFocusable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|setToggleClickCount
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|ToolTipManager
operator|.
name|sharedInstance
argument_list|()
operator|.
name|registerComponent
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|setShowsRootHandles
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|setVisibleRowCount
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getInt
argument_list|(
literal|"groupsVisibleRows"
argument_list|)
argument_list|)
expr_stmt|;
name|getSelectionModel
argument_list|()
operator|.
name|setSelectionMode
argument_list|(
name|TreeSelectionModel
operator|.
name|DISCONTIGUOUS_TREE_SELECTION
argument_list|)
expr_stmt|;
name|addTreeExpansionListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
DECL|method|setModel (TreeModel newModel)
specifier|public
name|void
name|setModel
parameter_list|(
name|TreeModel
name|newModel
parameter_list|)
block|{
name|super
operator|.
name|setModel
argument_list|(
name|newModel
argument_list|)
expr_stmt|;
comment|// set expansion state
name|Object
name|root
init|=
name|newModel
operator|.
name|getRoot
argument_list|()
decl_stmt|;
if|if
condition|(
name|root
operator|==
literal|null
condition|)
return|return;
name|GroupTreeNode
name|cursor
decl_stmt|;
comment|// might be a dummy (instanceof DefaultMutableTreeNode)
if|if
condition|(
operator|!
operator|(
name|root
operator|instanceof
name|GroupTreeNode
operator|)
condition|)
return|return;
for|for
control|(
name|Enumeration
name|e
init|=
operator|(
operator|(
name|GroupTreeNode
operator|)
name|root
operator|)
operator|.
name|preorderEnumeration
argument_list|()
init|;
name|e
operator|.
name|hasMoreElements
argument_list|()
condition|;
control|)
block|{
name|cursor
operator|=
operator|(
name|GroupTreeNode
operator|)
name|e
operator|.
name|nextElement
argument_list|()
expr_stmt|;
if|if
condition|(
name|cursor
operator|.
name|isExpanded
condition|)
name|expandPath
argument_list|(
operator|new
name|TreePath
argument_list|(
name|cursor
operator|.
name|getPath
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
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
comment|/** This is for moving of nodes within myself */
DECL|method|dragOver (DragSourceDragEvent dsde)
specifier|public
name|void
name|dragOver
parameter_list|(
name|DragSourceDragEvent
name|dsde
parameter_list|)
block|{
specifier|final
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
specifier|final
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
specifier|final
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
name|target
operator|==
literal|null
operator|||
name|dragNode
operator|.
name|isNodeDescendant
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
comment|/** This handles dragging of nodes (from myself) or entries (from the table) */
DECL|method|dragOver (DropTargetDragEvent dtde)
specifier|public
name|void
name|dragOver
parameter_list|(
name|DropTargetDragEvent
name|dtde
parameter_list|)
block|{
specifier|final
name|Point
name|cursor
init|=
name|dtde
operator|.
name|getLocation
argument_list|()
decl_stmt|;
specifier|final
name|long
name|currentTime
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
if|if
condition|(
name|idlePoint
operator|==
literal|null
condition|)
name|idlePoint
operator|=
name|cursor
expr_stmt|;
comment|// determine node over which the user is dragging
specifier|final
name|TreePath
name|path
init|=
name|getPathForLocation
argument_list|(
name|cursor
operator|.
name|x
argument_list|,
name|cursor
operator|.
name|y
argument_list|)
decl_stmt|;
specifier|final
name|GroupTreeNode
name|target
init|=
name|path
operator|==
literal|null
condition|?
literal|null
else|:
operator|(
name|GroupTreeNode
operator|)
name|path
operator|.
name|getLastPathComponent
argument_list|()
decl_stmt|;
name|cellRenderer
operator|.
name|setHighlight1Cell
argument_list|(
name|target
argument_list|)
expr_stmt|;
name|repaint
argument_list|()
expr_stmt|;
comment|// accept or reject
if|if
condition|(
name|dtde
operator|.
name|isDataFlavorSupported
argument_list|(
name|GroupTreeNode
operator|.
name|flavor
argument_list|)
condition|)
block|{
comment|// accept: move nodes within tree
name|dtde
operator|.
name|acceptDrag
argument_list|(
name|DnDConstants
operator|.
name|ACTION_MOVE
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|dtde
operator|.
name|isDataFlavorSupported
argument_list|(
name|TransferableEntrySelection
operator|.
name|flavorInternal
argument_list|)
condition|)
block|{
comment|// check if node accepts explicit assignment
if|if
condition|(
name|path
operator|==
literal|null
condition|)
block|{
name|dtde
operator|.
name|rejectDrag
argument_list|()
expr_stmt|;
block|}
else|else
block|{
comment|// this would be the place to check if the dragging entries
comment|// maybe are in this group already, but I think that's not
comment|// worth the bother (DropTargetDragEvent does not provide
comment|// access to the drag object)...
comment|// it might even be irritating to the user.
if|if
condition|(
name|target
operator|.
name|getGroup
argument_list|()
operator|.
name|supportsAdd
argument_list|()
condition|)
block|{
comment|// accept: assignment from EntryTable
name|dtde
operator|.
name|acceptDrag
argument_list|(
name|DnDConstants
operator|.
name|ACTION_LINK
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|dtde
operator|.
name|rejectDrag
argument_list|()
expr_stmt|;
block|}
block|}
block|}
else|else
block|{
name|dtde
operator|.
name|rejectDrag
argument_list|()
expr_stmt|;
block|}
comment|// auto open
if|if
condition|(
name|Math
operator|.
name|abs
argument_list|(
name|cursor
operator|.
name|x
operator|-
name|idlePoint
operator|.
name|x
argument_list|)
operator|<
name|idleMargin
operator|&&
name|Math
operator|.
name|abs
argument_list|(
name|cursor
operator|.
name|y
operator|-
name|idlePoint
operator|.
name|y
argument_list|)
operator|<
name|idleMargin
condition|)
block|{
if|if
condition|(
name|currentTime
operator|-
name|idleStartTime
operator|>=
name|idleTimeToExpandNode
condition|)
block|{
if|if
condition|(
name|path
operator|!=
literal|null
condition|)
block|{
name|expandPath
argument_list|(
name|path
argument_list|)
expr_stmt|;
block|}
block|}
block|}
else|else
block|{
name|idlePoint
operator|=
name|cursor
expr_stmt|;
name|idleStartTime
operator|=
name|currentTime
expr_stmt|;
block|}
comment|// autoscrolling
if|if
condition|(
name|currentTime
operator|-
name|lastDragAutoscroll
operator|<
name|minAutoscrollInterval
condition|)
return|return;
specifier|final
name|Rectangle
name|r
init|=
name|getVisibleRect
argument_list|()
decl_stmt|;
specifier|final
name|boolean
name|scrollUp
init|=
name|cursor
operator|.
name|y
operator|-
name|r
operator|.
name|y
operator|<
name|dragScrollActivationMargin
decl_stmt|;
specifier|final
name|boolean
name|scrollDown
init|=
name|r
operator|.
name|y
operator|+
name|r
operator|.
name|height
operator|-
name|cursor
operator|.
name|y
operator|<
name|dragScrollActivationMargin
decl_stmt|;
specifier|final
name|boolean
name|scrollLeft
init|=
name|cursor
operator|.
name|x
operator|-
name|r
operator|.
name|x
operator|<
name|dragScrollActivationMargin
decl_stmt|;
specifier|final
name|boolean
name|scrollRight
init|=
name|r
operator|.
name|x
operator|+
name|r
operator|.
name|width
operator|-
name|cursor
operator|.
name|x
operator|<
name|dragScrollActivationMargin
decl_stmt|;
if|if
condition|(
name|scrollUp
condition|)
name|r
operator|.
name|translate
argument_list|(
literal|0
argument_list|,
operator|-
name|dragScrollDistance
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
name|scrollDown
condition|)
name|r
operator|.
name|translate
argument_list|(
literal|0
argument_list|,
operator|+
name|dragScrollDistance
argument_list|)
expr_stmt|;
if|if
condition|(
name|scrollLeft
condition|)
name|r
operator|.
name|translate
argument_list|(
operator|-
name|dragScrollDistance
argument_list|,
literal|0
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
name|scrollRight
condition|)
name|r
operator|.
name|translate
argument_list|(
operator|+
name|dragScrollDistance
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|scrollRectToVisible
argument_list|(
name|r
argument_list|)
expr_stmt|;
name|lastDragAutoscroll
operator|=
name|currentTime
expr_stmt|;
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
name|cellRenderer
operator|.
name|setHighlight1Cell
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|repaint
argument_list|()
expr_stmt|;
try|try
block|{
comment|// initializations common to all flavors
specifier|final
name|Transferable
name|transferable
init|=
name|dtde
operator|.
name|getTransferable
argument_list|()
decl_stmt|;
specifier|final
name|Point
name|p
init|=
name|dtde
operator|.
name|getLocation
argument_list|()
decl_stmt|;
specifier|final
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
specifier|final
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
comment|// check supported flavors
if|if
condition|(
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
name|isNodeDescendant
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
elseif|else
if|if
condition|(
name|transferable
operator|.
name|isDataFlavorSupported
argument_list|(
name|TransferableEntrySelection
operator|.
name|flavorInternal
argument_list|)
condition|)
block|{
specifier|final
name|AbstractGroup
name|group
init|=
name|target
operator|.
name|getGroup
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|group
operator|.
name|supportsAdd
argument_list|()
condition|)
block|{
comment|// this should never happen, because the same condition
comment|// is checked in dragOver already
name|dtde
operator|.
name|rejectDrop
argument_list|()
expr_stmt|;
return|return;
block|}
specifier|final
name|TransferableEntrySelection
name|selection
init|=
operator|(
name|TransferableEntrySelection
operator|)
name|transferable
operator|.
name|getTransferData
argument_list|(
name|TransferableEntrySelection
operator|.
name|flavorInternal
argument_list|)
decl_stmt|;
specifier|final
name|BibtexEntry
index|[]
name|entries
init|=
name|selection
operator|.
name|getSelection
argument_list|()
decl_stmt|;
name|int
name|assignedEntries
init|=
literal|0
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
name|entries
operator|.
name|length
condition|;
operator|++
name|i
control|)
block|{
if|if
condition|(
operator|!
name|target
operator|.
name|getGroup
argument_list|()
operator|.
name|contains
argument_list|(
name|entries
index|[
name|i
index|]
argument_list|)
condition|)
operator|++
name|assignedEntries
expr_stmt|;
block|}
comment|// warn if assignment has undesired side effects (modifies a
comment|// field != keywords)
if|if
condition|(
operator|!
name|Util
operator|.
name|warnAssignmentSideEffects
argument_list|(
name|group
argument_list|,
name|selection
operator|.
name|getSelection
argument_list|()
argument_list|,
name|groupSelector
operator|.
name|getActiveBasePanel
argument_list|()
operator|.
name|getDatabase
argument_list|()
argument_list|,
name|groupSelector
operator|.
name|frame
argument_list|)
condition|)
return|return;
comment|// user aborted operation
name|AbstractUndoableEdit
name|undo
init|=
name|group
operator|.
name|add
argument_list|(
name|selection
operator|.
name|getSelection
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|undo
operator|instanceof
name|UndoableChangeAssignment
condition|)
operator|(
operator|(
name|UndoableChangeAssignment
operator|)
name|undo
operator|)
operator|.
name|setEditedNode
argument_list|(
name|target
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
name|groupSelector
operator|.
name|revalidateGroups
argument_list|()
expr_stmt|;
name|groupSelector
operator|.
name|concludeAssignment
argument_list|(
name|undo
argument_list|,
name|target
argument_list|,
name|assignedEntries
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|dtde
operator|.
name|rejectDrop
argument_list|()
expr_stmt|;
return|return;
block|}
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
name|cellRenderer
operator|.
name|setHighlight1Cell
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|repaint
argument_list|()
expr_stmt|;
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
name|DragSource
operator|.
name|DefaultMoveDrop
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
comment|/**      * Refresh paths that may have become invalid due to node movements within      * the tree. This method creates new paths to the last path components      * (which must still exist) of the specified paths.      *       * @param paths      *            Paths that may have become invalid.      * @return Refreshed paths that are all valid.      */
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
DECL|method|treeCollapsed (TreeExpansionEvent event)
specifier|public
name|void
name|treeCollapsed
parameter_list|(
name|TreeExpansionEvent
name|event
parameter_list|)
block|{
operator|(
operator|(
name|GroupTreeNode
operator|)
name|event
operator|.
name|getPath
argument_list|()
operator|.
name|getLastPathComponent
argument_list|()
operator|)
operator|.
name|isExpanded
operator|=
literal|false
expr_stmt|;
block|}
DECL|method|treeExpanded (TreeExpansionEvent event)
specifier|public
name|void
name|treeExpanded
parameter_list|(
name|TreeExpansionEvent
name|event
parameter_list|)
block|{
operator|(
operator|(
name|GroupTreeNode
operator|)
name|event
operator|.
name|getPath
argument_list|()
operator|.
name|getLastPathComponent
argument_list|()
operator|)
operator|.
name|isExpanded
operator|=
literal|true
expr_stmt|;
block|}
block|}
end_class

end_unit

