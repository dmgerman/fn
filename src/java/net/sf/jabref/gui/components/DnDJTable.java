begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.components
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|components
package|;
end_package

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
name|MouseEvent
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
name|*
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|table
operator|.
name|*
import|;
end_import

begin_class
DECL|class|DnDJTable
specifier|public
specifier|abstract
class|class
name|DnDJTable
extends|extends
name|JTable
implements|implements
name|DragGestureListener
implements|,
name|DragSourceListener
block|{
DECL|field|selectionToDrag
specifier|protected
name|int
index|[]
name|selectionToDrag
init|=
operator|new
name|int
index|[
literal|0
index|]
decl_stmt|;
DECL|method|DnDJTable ()
specifier|public
name|DnDJTable
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
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
expr_stmt|;
block|}
DECL|method|DnDJTable (int numRows, int numColumns)
specifier|public
name|DnDJTable
parameter_list|(
name|int
name|numRows
parameter_list|,
name|int
name|numColumns
parameter_list|)
block|{
name|super
argument_list|(
name|numRows
argument_list|,
name|numColumns
argument_list|)
expr_stmt|;
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
expr_stmt|;
block|}
DECL|method|DnDJTable (TableModel dm)
specifier|public
name|DnDJTable
parameter_list|(
name|TableModel
name|dm
parameter_list|)
block|{
name|super
argument_list|(
name|dm
argument_list|)
expr_stmt|;
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
expr_stmt|;
block|}
DECL|method|DnDJTable (Object[][] rowData, Object[] columnNames)
specifier|public
name|DnDJTable
parameter_list|(
name|Object
index|[]
index|[]
name|rowData
parameter_list|,
name|Object
index|[]
name|columnNames
parameter_list|)
block|{
name|super
argument_list|(
name|rowData
argument_list|,
name|columnNames
argument_list|)
expr_stmt|;
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
expr_stmt|;
block|}
DECL|method|DnDJTable (Vector rowData, Vector columnNames)
specifier|public
name|DnDJTable
parameter_list|(
name|Vector
name|rowData
parameter_list|,
name|Vector
name|columnNames
parameter_list|)
block|{
name|super
argument_list|(
name|rowData
argument_list|,
name|columnNames
argument_list|)
expr_stmt|;
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
expr_stmt|;
block|}
DECL|method|DnDJTable (TableModel dm, TableColumnModel cm)
specifier|public
name|DnDJTable
parameter_list|(
name|TableModel
name|dm
parameter_list|,
name|TableColumnModel
name|cm
parameter_list|)
block|{
name|super
argument_list|(
name|dm
argument_list|,
name|cm
argument_list|)
expr_stmt|;
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
expr_stmt|;
block|}
DECL|method|DnDJTable (TableModel dm, TableColumnModel cm, ListSelectionModel sm)
specifier|public
name|DnDJTable
parameter_list|(
name|TableModel
name|dm
parameter_list|,
name|TableColumnModel
name|cm
parameter_list|,
name|ListSelectionModel
name|sm
parameter_list|)
block|{
name|super
argument_list|(
name|dm
argument_list|,
name|cm
argument_list|,
name|sm
argument_list|)
expr_stmt|;
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
expr_stmt|;
block|}
DECL|method|processMouseMotionEvent (MouseEvent e)
specifier|public
name|void
name|processMouseMotionEvent
parameter_list|(
name|MouseEvent
name|e
parameter_list|)
block|{
name|super
operator|.
name|processMouseMotionEvent
argument_list|(
name|e
argument_list|)
expr_stmt|;
if|if
condition|(
name|e
operator|.
name|getID
argument_list|()
operator|==
name|MouseEvent
operator|.
name|MOUSE_DRAGGED
condition|)
block|{
comment|// prevent selection changes due to movement
name|clearSelection
argument_list|()
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|selectionToDrag
operator|.
name|length
condition|;
operator|++
name|i
control|)
name|addRowSelectionInterval
argument_list|(
name|selectionToDrag
index|[
name|i
index|]
argument_list|,
name|selectionToDrag
index|[
name|i
index|]
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|processMouseEvent (MouseEvent e)
specifier|public
name|void
name|processMouseEvent
parameter_list|(
name|MouseEvent
name|e
parameter_list|)
block|{
comment|// unmodified left click on selected cell has to be handled specially
if|if
condition|(
name|e
operator|.
name|getButton
argument_list|()
operator|==
name|MouseEvent
operator|.
name|BUTTON1
operator|&&
operator|!
name|e
operator|.
name|isAltDown
argument_list|()
operator|&&
operator|!
name|e
operator|.
name|isAltGraphDown
argument_list|()
operator|&&
operator|!
name|e
operator|.
name|isControlDown
argument_list|()
operator|&&
operator|!
name|e
operator|.
name|isMetaDown
argument_list|()
operator|&&
operator|!
name|e
operator|.
name|isShiftDown
argument_list|()
condition|)
block|{
name|Point
name|p
init|=
name|e
operator|.
name|getPoint
argument_list|()
decl_stmt|;
specifier|final
name|int
name|col
init|=
name|columnAtPoint
argument_list|(
name|p
argument_list|)
decl_stmt|;
specifier|final
name|int
name|row
init|=
name|rowAtPoint
argument_list|(
name|p
argument_list|)
decl_stmt|;
if|if
condition|(
name|isCellSelected
argument_list|(
name|row
argument_list|,
name|col
argument_list|)
condition|)
block|{
comment|// on button release, select only this row
if|if
condition|(
name|e
operator|.
name|getID
argument_list|()
operator|==
name|MouseEvent
operator|.
name|MOUSE_RELEASED
condition|)
block|{
name|getSelectionModel
argument_list|()
operator|.
name|setSelectionInterval
argument_list|(
name|row
argument_list|,
name|row
argument_list|)
expr_stmt|;
return|return;
block|}
if|if
condition|(
name|e
operator|.
name|getID
argument_list|()
operator|==
name|MouseEvent
operator|.
name|MOUSE_PRESSED
condition|)
block|{
name|int
index|[]
name|selectedRows
init|=
name|getSelectedRows
argument_list|()
decl_stmt|;
name|super
operator|.
name|processMouseEvent
argument_list|(
name|e
argument_list|)
expr_stmt|;
name|clearSelection
argument_list|()
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|selectedRows
operator|.
name|length
condition|;
operator|++
name|i
control|)
name|addRowSelectionInterval
argument_list|(
name|selectedRows
index|[
name|i
index|]
argument_list|,
name|selectedRows
index|[
name|i
index|]
argument_list|)
expr_stmt|;
return|return;
block|}
block|}
block|}
name|super
operator|.
name|processMouseEvent
argument_list|(
name|e
argument_list|)
expr_stmt|;
comment|// set selection here to ensure it's up to date when dragging starts
name|selectionToDrag
operator|=
name|getSelectedRows
argument_list|()
expr_stmt|;
block|}
DECL|method|dragGestureRecognized (DragGestureEvent dge)
specifier|public
specifier|abstract
name|void
name|dragGestureRecognized
parameter_list|(
name|DragGestureEvent
name|dge
parameter_list|)
function_decl|;
comment|/* public void dragGestureRecognized(DragGestureEvent dge) {         Transferable transferable = new TransferableObject(selectionToDrag);         Cursor cursor = DragSource.DefaultCopyDrop;         dge.getDragSource().startDrag(dge, cursor, transferable, this);     }*/
DECL|method|dragEnter (DragSourceDragEvent dsde)
specifier|public
name|void
name|dragEnter
parameter_list|(
name|DragSourceDragEvent
name|dsde
parameter_list|)
block|{
comment|// to be implemented by subclass if required
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
comment|// to be implemented by subclass if required
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
comment|// to be implemented by subclass if required
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
comment|// to be implemented by subclass if required
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
comment|// to be implemented by subclass if required
block|}
block|}
end_class

end_unit

