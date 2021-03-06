begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.groups
package|package
name|org
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
name|org
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
DECL|class|MoveGroupChange
specifier|public
class|class
name|MoveGroupChange
block|{
DECL|field|oldParent
specifier|private
name|GroupTreeNode
name|oldParent
decl_stmt|;
DECL|field|oldChildIndex
specifier|private
name|int
name|oldChildIndex
decl_stmt|;
DECL|field|newParent
specifier|private
name|GroupTreeNode
name|newParent
decl_stmt|;
DECL|field|newChildIndex
specifier|private
name|int
name|newChildIndex
decl_stmt|;
comment|/**      * @param oldParent      * @param oldChildIndex      * @param newParent The new parent node to which the node will be moved.      * @param newChildIndex The child index at newParent to which the node will be moved.      */
DECL|method|MoveGroupChange (GroupTreeNode oldParent, int oldChildIndex, GroupTreeNode newParent, int newChildIndex)
specifier|public
name|MoveGroupChange
parameter_list|(
name|GroupTreeNode
name|oldParent
parameter_list|,
name|int
name|oldChildIndex
parameter_list|,
name|GroupTreeNode
name|newParent
parameter_list|,
name|int
name|newChildIndex
parameter_list|)
block|{
name|this
operator|.
name|oldParent
operator|=
name|oldParent
expr_stmt|;
name|this
operator|.
name|oldChildIndex
operator|=
name|oldChildIndex
expr_stmt|;
name|this
operator|.
name|newParent
operator|=
name|newParent
expr_stmt|;
name|this
operator|.
name|newChildIndex
operator|=
name|newChildIndex
expr_stmt|;
block|}
DECL|method|getOldParent ()
specifier|public
name|GroupTreeNode
name|getOldParent
parameter_list|()
block|{
return|return
name|oldParent
return|;
block|}
DECL|method|getOldChildIndex ()
specifier|public
name|int
name|getOldChildIndex
parameter_list|()
block|{
return|return
name|oldChildIndex
return|;
block|}
DECL|method|getNewParent ()
specifier|public
name|GroupTreeNode
name|getNewParent
parameter_list|()
block|{
return|return
name|newParent
return|;
block|}
DECL|method|getNewChildIndex ()
specifier|public
name|int
name|getNewChildIndex
parameter_list|()
block|{
return|return
name|newChildIndex
return|;
block|}
block|}
end_class

end_unit

