begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.groups
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
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
name|Cursor
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Point
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Rectangle
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
name|DragGestureEvent
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
name|DragGestureListener
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
name|DragGestureRecognizer
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
name|DragSource
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
name|DragSourceDragEvent
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
name|DragSourceDropEvent
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
name|DragSourceEvent
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
name|DragSourceListener
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
name|DropTarget
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
name|Enumeration
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
name|Objects
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
name|java
operator|.
name|util
operator|.
name|Vector
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JTree
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
name|ToolTipManager
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
name|TreePath
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
name|TreeSelectionModel
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
name|model
operator|.
name|entry
operator|.
name|BibEntry
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
name|model
operator|.
name|groups
operator|.
name|AbstractGroup
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
name|model
operator|.
name|groups
operator|.
name|EntriesGroupChange
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
name|model
operator|.
name|groups
operator|.
name|GroupTreeNode
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
comment|/** distance from component borders from which on autoscrolling starts. */
DECL|field|DRAG_SCROLL_ACTIVATION_MARGIN
specifier|private
specifier|static
specifier|final
name|int
name|DRAG_SCROLL_ACTIVATION_MARGIN
init|=
literal|10
decl_stmt|;
comment|/** number of pixels to scroll each time handler is called. */
DECL|field|DRAG_SCROLL_DISTANCE
specifier|private
specifier|static
specifier|final
name|int
name|DRAG_SCROLL_DISTANCE
init|=
literal|5
decl_stmt|;
comment|/** time of last autoscroll event (for limiting speed). */
DECL|field|lastDragAutoscroll
specifier|private
specifier|static
name|long
name|lastDragAutoscroll
decl_stmt|;
comment|/** minimum interval between two autoscroll events (for limiting speed). */
DECL|field|MIN_AUTOSCROLL_INTERVAL
specifier|private
specifier|static
specifier|final
name|long
name|MIN_AUTOSCROLL_INTERVAL
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
decl_stmt|;
comment|/** max. distance cursor may move in x or y direction while idling. */
DECL|field|IDLE_MARGIN
specifier|private
specifier|static
specifier|final
name|int
name|IDLE_MARGIN
init|=
literal|1
decl_stmt|;
comment|/** idle time after which the node below is expanded. */
DECL|field|IDLE_TIME_TO_EXPAND_NODE
specifier|private
specifier|static
specifier|final
name|long
name|IDLE_TIME_TO_EXPAND_NODE
init|=
literal|1000L
decl_stmt|;
DECL|field|groupSelector
specifier|private
specifier|final
name|GroupSelector
name|groupSelector
decl_stmt|;
DECL|field|dragNode
specifier|private
name|GroupTreeNodeViewModel
name|dragNode
decl_stmt|;
DECL|field|localCellRenderer
specifier|private
specifier|final
name|GroupTreeCellRenderer
name|localCellRenderer
init|=
operator|new
name|GroupTreeCellRenderer
argument_list|()
decl_stmt|;
comment|/**      * @param groupSelector the parent UI component      */
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
if|if
condition|(
name|dgr
operator|!=
literal|null
condition|)
block|{
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
block|}
operator|new
name|DropTarget
argument_list|(
name|this
argument_list|,
name|this
argument_list|)
expr_stmt|;
name|setCellRenderer
argument_list|(
name|localCellRenderer
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
name|this
operator|.
name|setFocusable
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
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
annotation|@
name|Override
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
if|if
condition|(
name|p
operator|!=
literal|null
condition|)
block|{
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
name|GroupTreeNodeViewModel
name|target
init|=
operator|(
name|GroupTreeNodeViewModel
operator|)
name|path
operator|.
name|getLastPathComponent
argument_list|()
decl_stmt|;
if|if
condition|(
operator|(
name|target
operator|==
literal|null
operator|)
operator|||
name|dragNode
operator|.
name|getNode
argument_list|()
operator|.
name|isNodeDescendant
argument_list|(
name|target
operator|.
name|getNode
argument_list|()
argument_list|)
operator|||
operator|(
name|dragNode
operator|.
name|equals
argument_list|(
name|target
argument_list|)
operator|)
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
block|}
annotation|@
name|Override
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
annotation|@
name|Override
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
annotation|@
name|Override
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
block|{
comment|// ignore
block|}
comment|/** This handles dragging of nodes (from myself) or entries (from the table) */
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
block|{
name|idlePoint
operator|=
name|cursor
expr_stmt|;
block|}
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
name|GroupTreeNodeViewModel
name|target
init|=
name|path
operator|==
literal|null
condition|?
literal|null
else|:
operator|(
name|GroupTreeNodeViewModel
operator|)
name|path
operator|.
name|getLastPathComponent
argument_list|()
decl_stmt|;
name|setHighlight1Cell
argument_list|(
name|target
argument_list|)
expr_stmt|;
comment|// accept or reject
if|if
condition|(
name|dtde
operator|.
name|isDataFlavorSupported
argument_list|(
name|GroupTreeNodeViewModel
operator|.
name|FLAVOR
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
name|FLAVOR_INTERNAL
argument_list|)
condition|)
block|{
comment|// check if node accepts explicit assignment
if|if
condition|(
name|target
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
name|getNode
argument_list|()
operator|.
name|supportsAddingEntries
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
operator|(
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
name|GroupsTree
operator|.
name|IDLE_MARGIN
operator|)
operator|&&
operator|(
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
name|GroupsTree
operator|.
name|IDLE_MARGIN
operator|)
condition|)
block|{
if|if
condition|(
operator|(
operator|(
name|currentTime
operator|-
name|idleStartTime
operator|)
operator|>=
name|GroupsTree
operator|.
name|IDLE_TIME_TO_EXPAND_NODE
operator|)
operator|&&
operator|(
name|path
operator|!=
literal|null
operator|)
condition|)
block|{
name|expandPath
argument_list|(
name|path
argument_list|)
expr_stmt|;
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
operator|(
name|currentTime
operator|-
name|GroupsTree
operator|.
name|lastDragAutoscroll
operator|)
operator|<
name|GroupsTree
operator|.
name|MIN_AUTOSCROLL_INTERVAL
condition|)
block|{
return|return;
block|}
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
operator|(
name|cursor
operator|.
name|y
operator|-
name|r
operator|.
name|y
operator|)
operator|<
name|GroupsTree
operator|.
name|DRAG_SCROLL_ACTIVATION_MARGIN
decl_stmt|;
specifier|final
name|boolean
name|scrollDown
init|=
operator|(
operator|(
name|r
operator|.
name|y
operator|+
name|r
operator|.
name|height
operator|)
operator|-
name|cursor
operator|.
name|y
operator|)
operator|<
name|GroupsTree
operator|.
name|DRAG_SCROLL_ACTIVATION_MARGIN
decl_stmt|;
specifier|final
name|boolean
name|scrollLeft
init|=
operator|(
name|cursor
operator|.
name|x
operator|-
name|r
operator|.
name|x
operator|)
operator|<
name|GroupsTree
operator|.
name|DRAG_SCROLL_ACTIVATION_MARGIN
decl_stmt|;
specifier|final
name|boolean
name|scrollRight
init|=
operator|(
operator|(
name|r
operator|.
name|x
operator|+
name|r
operator|.
name|width
operator|)
operator|-
name|cursor
operator|.
name|x
operator|)
operator|<
name|GroupsTree
operator|.
name|DRAG_SCROLL_ACTIVATION_MARGIN
decl_stmt|;
if|if
condition|(
name|scrollUp
condition|)
block|{
name|r
operator|.
name|translate
argument_list|(
literal|0
argument_list|,
operator|-
name|GroupsTree
operator|.
name|DRAG_SCROLL_DISTANCE
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|scrollDown
condition|)
block|{
name|r
operator|.
name|translate
argument_list|(
literal|0
argument_list|,
operator|+
name|GroupsTree
operator|.
name|DRAG_SCROLL_DISTANCE
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|scrollLeft
condition|)
block|{
name|r
operator|.
name|translate
argument_list|(
operator|-
name|GroupsTree
operator|.
name|DRAG_SCROLL_DISTANCE
argument_list|,
literal|0
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|scrollRight
condition|)
block|{
name|r
operator|.
name|translate
argument_list|(
operator|+
name|GroupsTree
operator|.
name|DRAG_SCROLL_DISTANCE
argument_list|,
literal|0
argument_list|)
expr_stmt|;
block|}
name|scrollRectToVisible
argument_list|(
name|r
argument_list|)
expr_stmt|;
name|GroupsTree
operator|.
name|lastDragAutoscroll
operator|=
name|currentTime
expr_stmt|;
block|}
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
block|{
comment|// ignore
block|}
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
name|setHighlight1Cell
argument_list|(
literal|null
argument_list|)
expr_stmt|;
try|try
block|{
comment|// initializations common to all flavors
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
name|GroupTreeNodeViewModel
name|target
init|=
operator|(
name|GroupTreeNodeViewModel
operator|)
name|path
operator|.
name|getLastPathComponent
argument_list|()
decl_stmt|;
comment|// check supported flavors
specifier|final
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
name|transferable
operator|.
name|isDataFlavorSupported
argument_list|(
name|GroupTreeNodeViewModel
operator|.
name|FLAVOR
argument_list|)
condition|)
block|{
name|GroupTreeNodeViewModel
name|source
init|=
operator|(
name|GroupTreeNodeViewModel
operator|)
name|transferable
operator|.
name|getTransferData
argument_list|(
name|GroupTreeNodeViewModel
operator|.
name|FLAVOR
argument_list|)
decl_stmt|;
if|if
condition|(
name|source
operator|.
name|equals
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
comment|// ignore this
return|return;
block|}
if|if
condition|(
name|source
operator|.
name|getNode
argument_list|()
operator|.
name|isNodeDescendant
argument_list|(
name|target
operator|.
name|getNode
argument_list|()
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
argument_list|<
name|TreePath
argument_list|>
name|expandedPaths
init|=
name|groupSelector
operator|.
name|getExpandedPaths
argument_list|()
decl_stmt|;
name|MoveGroupChange
name|undo
init|=
operator|new
name|MoveGroupChange
argument_list|(
operator|(
operator|(
name|GroupTreeNodeViewModel
operator|)
name|source
operator|.
name|getParent
argument_list|()
operator|)
operator|.
name|getNode
argument_list|()
argument_list|,
name|source
operator|.
name|getNode
argument_list|()
operator|.
name|getPositionInParent
argument_list|()
argument_list|,
name|target
operator|.
name|getNode
argument_list|()
argument_list|,
name|target
operator|.
name|getChildCount
argument_list|()
argument_list|)
decl_stmt|;
name|source
operator|.
name|getNode
argument_list|()
operator|.
name|moveTo
argument_list|(
name|target
operator|.
name|getNode
argument_list|()
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
name|source
operator|.
name|getTreePath
argument_list|()
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
name|FLAVOR_INTERNAL
argument_list|)
condition|)
block|{
specifier|final
name|AbstractGroup
name|group
init|=
name|target
operator|.
name|getNode
argument_list|()
operator|.
name|getGroup
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|target
operator|.
name|getNode
argument_list|()
operator|.
name|supportsAddingEntries
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
name|FLAVOR_INTERNAL
argument_list|)
decl_stmt|;
specifier|final
name|List
argument_list|<
name|BibEntry
argument_list|>
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
name|BibEntry
name|entry
range|:
name|entries
control|)
block|{
if|if
condition|(
operator|!
name|target
operator|.
name|getNode
argument_list|()
operator|.
name|getGroup
argument_list|()
operator|.
name|contains
argument_list|(
name|entry
argument_list|)
condition|)
block|{
operator|++
name|assignedEntries
expr_stmt|;
block|}
block|}
comment|// warn if assignment has undesired side effects (modifies a
comment|// field != keywords)
if|if
condition|(
operator|!
name|WarnAssignmentSideEffects
operator|.
name|warnAssignmentSideEffects
argument_list|(
name|group
argument_list|,
name|groupSelector
operator|.
name|frame
argument_list|)
condition|)
block|{
return|return;
comment|// user aborted operation
block|}
comment|// if an editor is showing, its fields must be updated
comment|// after the assignment, and before that, the current
comment|// edit has to be stored:
name|groupSelector
operator|.
name|getActiveBasePanel
argument_list|()
operator|.
name|storeCurrentEdit
argument_list|()
expr_stmt|;
name|Optional
argument_list|<
name|EntriesGroupChange
argument_list|>
name|undo
init|=
name|target
operator|.
name|addEntriesToGroup
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
operator|.
name|isPresent
argument_list|()
condition|)
block|{
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
name|concludeAssignment
argument_list|(
name|UndoableChangeEntriesOfGroup
operator|.
name|getUndoableEdit
argument_list|(
name|target
argument_list|,
name|undo
operator|.
name|get
argument_list|()
argument_list|)
argument_list|,
name|target
operator|.
name|getNode
argument_list|()
argument_list|,
name|assignedEntries
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|dtde
operator|.
name|rejectDrop
argument_list|()
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|IOException
decl||
name|UnsupportedFlavorException
name|ioe
parameter_list|)
block|{
comment|// ignore
block|}
block|}
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
block|{
name|setHighlight1Cell
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|dragGestureRecognized (DragGestureEvent dge)
specifier|public
name|void
name|dragGestureRecognized
parameter_list|(
name|DragGestureEvent
name|dge
parameter_list|)
block|{
name|GroupTreeNodeViewModel
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
block|{
return|return;
comment|// nothing to transfer (select manually?)
block|}
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
specifier|private
name|GroupTreeNodeViewModel
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
operator|==
literal|null
condition|?
literal|null
else|:
operator|(
name|GroupTreeNodeViewModel
operator|)
name|selectionPath
operator|.
name|getLastPathComponent
argument_list|()
return|;
block|}
comment|/**      * Refresh paths that may have become invalid due to node movements within      * the tree. This method creates new paths to the last path components      * (which must still exist) of the specified paths.      *      * @param paths      *            Paths that may have become invalid.      * @return Refreshed paths that are all valid.      */
DECL|method|refreshPaths (Enumeration<TreePath> paths)
specifier|public
name|Enumeration
argument_list|<
name|TreePath
argument_list|>
name|refreshPaths
parameter_list|(
name|Enumeration
argument_list|<
name|TreePath
argument_list|>
name|paths
parameter_list|)
block|{
if|if
condition|(
name|paths
operator|==
literal|null
condition|)
block|{
return|return
operator|new
name|Vector
argument_list|<
name|TreePath
argument_list|>
argument_list|()
operator|.
name|elements
argument_list|()
return|;
block|}
name|Vector
argument_list|<
name|TreePath
argument_list|>
name|freshPaths
init|=
operator|new
name|Vector
argument_list|<>
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
operator|(
operator|(
name|GroupTreeNodeViewModel
operator|)
name|paths
operator|.
name|nextElement
argument_list|()
operator|.
name|getLastPathComponent
argument_list|()
operator|)
operator|.
name|getTreePath
argument_list|()
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
comment|/**      * Refresh paths that may have become invalid due to node movements within      * the tree. This method creates new paths to the last path components      * (which must still exist) of the specified paths.      *      * @param paths      *            Paths that may have become invalid.      * @return Refreshed paths that are all valid.      */
DECL|method|refreshPaths (TreePath[] paths)
specifier|public
name|TreePath
index|[]
name|refreshPaths
parameter_list|(
name|TreePath
index|[]
name|paths
parameter_list|)
block|{
name|TreePath
index|[]
name|freshPaths
init|=
operator|new
name|TreePath
index|[
name|paths
operator|.
name|length
index|]
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
name|paths
operator|.
name|length
condition|;
operator|++
name|i
control|)
block|{
name|freshPaths
index|[
name|i
index|]
operator|=
operator|(
operator|(
name|GroupTreeNodeViewModel
operator|)
name|paths
index|[
name|i
index|]
operator|.
name|getLastPathComponent
argument_list|()
operator|)
operator|.
name|getTreePath
argument_list|()
expr_stmt|;
block|}
return|return
name|freshPaths
return|;
block|}
comment|/** Highlights the specified cell or disables highlight if cell == null */
DECL|method|setHighlight1Cell (Object cell)
specifier|private
name|void
name|setHighlight1Cell
parameter_list|(
name|Object
name|cell
parameter_list|)
block|{
name|localCellRenderer
operator|.
name|setHighlight1Cell
argument_list|(
name|cell
argument_list|)
expr_stmt|;
name|repaint
argument_list|()
expr_stmt|;
block|}
comment|/**      * Highlights the specified groups in red      **/
DECL|method|setOverlappingGroups (List<GroupTreeNode> nodes)
specifier|public
name|void
name|setOverlappingGroups
parameter_list|(
name|List
argument_list|<
name|GroupTreeNode
argument_list|>
name|nodes
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|nodes
argument_list|)
expr_stmt|;
name|localCellRenderer
operator|.
name|setOverlappingGroups
argument_list|(
name|nodes
argument_list|)
expr_stmt|;
name|repaint
argument_list|()
expr_stmt|;
block|}
comment|/**      * Highlights the specified groups by underlining      **/
DECL|method|setMatchingGroups (List<GroupTreeNode> nodes)
specifier|public
name|void
name|setMatchingGroups
parameter_list|(
name|List
argument_list|<
name|GroupTreeNode
argument_list|>
name|nodes
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|nodes
argument_list|)
expr_stmt|;
name|localCellRenderer
operator|.
name|setMatchingGroups
argument_list|(
name|nodes
argument_list|)
expr_stmt|;
name|repaint
argument_list|()
expr_stmt|;
block|}
comment|/** Highlights the specified cell or disables highlight if cell == null */
DECL|method|setHighlightBorderCell (GroupTreeNodeViewModel node)
specifier|public
name|void
name|setHighlightBorderCell
parameter_list|(
name|GroupTreeNodeViewModel
name|node
parameter_list|)
block|{
name|localCellRenderer
operator|.
name|setHighlightBorderCell
argument_list|(
name|node
argument_list|)
expr_stmt|;
name|repaint
argument_list|()
expr_stmt|;
block|}
comment|/** Sort immediate children of the specified node alphabetically. */
DECL|method|sort (GroupTreeNodeViewModel node, boolean recursive)
specifier|public
name|void
name|sort
parameter_list|(
name|GroupTreeNodeViewModel
name|node
parameter_list|,
name|boolean
name|recursive
parameter_list|)
block|{
name|node
operator|.
name|sortChildrenByName
argument_list|(
name|recursive
argument_list|)
expr_stmt|;
block|}
comment|/**      * Returns true if the node specified by path has at least one descendant      * that is currently expanded.      */
DECL|method|hasExpandedDescendant (TreePath path)
specifier|public
name|boolean
name|hasExpandedDescendant
parameter_list|(
name|TreePath
name|path
parameter_list|)
block|{
name|GroupTreeNodeViewModel
name|node
init|=
operator|(
name|GroupTreeNodeViewModel
operator|)
name|path
operator|.
name|getLastPathComponent
argument_list|()
decl_stmt|;
for|for
control|(
name|GroupTreeNodeViewModel
name|child
range|:
name|node
operator|.
name|getChildren
argument_list|()
control|)
block|{
if|if
condition|(
name|child
operator|.
name|isLeaf
argument_list|()
condition|)
block|{
continue|continue;
comment|// don't care about this case
block|}
name|TreePath
name|pathToChild
init|=
name|path
operator|.
name|pathByAddingChild
argument_list|(
name|child
argument_list|)
decl_stmt|;
if|if
condition|(
name|isExpanded
argument_list|(
name|pathToChild
argument_list|)
operator|||
name|hasExpandedDescendant
argument_list|(
name|pathToChild
argument_list|)
condition|)
block|{
return|return
literal|true
return|;
block|}
block|}
return|return
literal|false
return|;
block|}
comment|/**      * Returns true if the node specified by path has at least one descendant      * that is currently collapsed.      */
DECL|method|hasCollapsedDescendant (TreePath path)
specifier|public
name|boolean
name|hasCollapsedDescendant
parameter_list|(
name|TreePath
name|path
parameter_list|)
block|{
name|GroupTreeNodeViewModel
name|node
init|=
operator|(
name|GroupTreeNodeViewModel
operator|)
name|path
operator|.
name|getLastPathComponent
argument_list|()
decl_stmt|;
for|for
control|(
name|GroupTreeNodeViewModel
name|child
range|:
name|node
operator|.
name|getChildren
argument_list|()
control|)
block|{
if|if
condition|(
name|child
operator|.
name|isLeaf
argument_list|()
condition|)
block|{
continue|continue;
comment|// don't care about this case
block|}
name|TreePath
name|pathToChild
init|=
name|path
operator|.
name|pathByAddingChild
argument_list|(
name|child
argument_list|)
decl_stmt|;
if|if
condition|(
name|isCollapsed
argument_list|(
name|pathToChild
argument_list|)
operator|||
name|hasCollapsedDescendant
argument_list|(
name|pathToChild
argument_list|)
condition|)
block|{
return|return
literal|true
return|;
block|}
block|}
return|return
literal|false
return|;
block|}
block|}
end_class

end_unit

