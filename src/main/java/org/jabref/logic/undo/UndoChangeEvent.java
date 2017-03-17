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
comment|/**  * Event sent when something is undone or redone  *  */
end_comment

begin_class
DECL|class|UndoChangeEvent
specifier|public
class|class
name|UndoChangeEvent
block|{
DECL|field|canUndo
specifier|private
specifier|final
name|boolean
name|canUndo
decl_stmt|;
DECL|field|undoDescription
specifier|private
specifier|final
name|String
name|undoDescription
decl_stmt|;
DECL|field|canRedo
specifier|private
specifier|final
name|boolean
name|canRedo
decl_stmt|;
DECL|field|redoDescription
specifier|private
specifier|final
name|String
name|redoDescription
decl_stmt|;
DECL|method|UndoChangeEvent (boolean canUndo, String undoDescription, boolean canRedo, String redoDescription)
specifier|public
name|UndoChangeEvent
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
name|this
operator|.
name|canUndo
operator|=
name|canUndo
expr_stmt|;
name|this
operator|.
name|undoDescription
operator|=
name|undoDescription
expr_stmt|;
name|this
operator|.
name|canRedo
operator|=
name|canRedo
expr_stmt|;
name|this
operator|.
name|redoDescription
operator|=
name|redoDescription
expr_stmt|;
block|}
comment|/**      *      * @return true if there is an action that can be undone      */
DECL|method|isCanUndo ()
specifier|public
name|boolean
name|isCanUndo
parameter_list|()
block|{
return|return
name|canUndo
return|;
block|}
comment|/**      *      * @return A description of the action to be undone      */
DECL|method|getUndoDescription ()
specifier|public
name|String
name|getUndoDescription
parameter_list|()
block|{
return|return
name|undoDescription
return|;
block|}
comment|/**      *      * @return true if there is an action that can be redone      */
DECL|method|isCanRedo ()
specifier|public
name|boolean
name|isCanRedo
parameter_list|()
block|{
return|return
name|canRedo
return|;
block|}
comment|/**     *     * @return A description of the action to be redone     */
DECL|method|getRedoDescription ()
specifier|public
name|String
name|getRedoDescription
parameter_list|()
block|{
return|return
name|redoDescription
return|;
block|}
block|}
end_class

end_unit

