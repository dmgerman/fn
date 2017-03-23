begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.undo
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|undo
package|;
end_package

begin_comment
comment|/**  * Event sent when a new undoable action is added to the undo manager  *  */
end_comment

begin_class
DECL|class|AddUndoableActionEvent
specifier|public
class|class
name|AddUndoableActionEvent
extends|extends
name|UndoChangeEvent
block|{
DECL|method|AddUndoableActionEvent (boolean canUndo, String undoDescription, boolean canRedo, String redoDescription)
specifier|public
name|AddUndoableActionEvent
parameter_list|(
name|boolean
name|canUndo
parameter_list|,
name|String
name|undoDescription
parameter_list|,
name|boolean
name|canRedo
parameter_list|,
name|String
name|redoDescription
parameter_list|)
block|{
name|super
argument_list|(
name|canUndo
argument_list|,
name|undoDescription
argument_list|,
name|canRedo
argument_list|,
name|redoDescription
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

