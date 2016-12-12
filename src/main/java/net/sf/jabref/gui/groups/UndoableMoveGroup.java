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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|undo
operator|.
name|AbstractUndoableJabRefEdit
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
name|logic
operator|.
name|l10n
operator|.
name|Localization
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

begin_comment
comment|/**  * @author jzieren  *  */
end_comment

begin_class
DECL|class|UndoableMoveGroup
class|class
name|UndoableMoveGroup
extends|extends
name|AbstractUndoableJabRefEdit
block|{
DECL|field|root
specifier|private
specifier|final
name|GroupTreeNodeViewModel
name|root
decl_stmt|;
DECL|field|pathToNewParent
specifier|private
specifier|final
name|List
argument_list|<
name|Integer
argument_list|>
name|pathToNewParent
decl_stmt|;
DECL|field|newChildIndex
specifier|private
specifier|final
name|int
name|newChildIndex
decl_stmt|;
DECL|field|pathToOldParent
specifier|private
specifier|final
name|List
argument_list|<
name|Integer
argument_list|>
name|pathToOldParent
decl_stmt|;
DECL|field|oldChildIndex
specifier|private
specifier|final
name|int
name|oldChildIndex
decl_stmt|;
DECL|method|UndoableMoveGroup (GroupTreeNodeViewModel root, MoveGroupChange moveChange)
specifier|public
name|UndoableMoveGroup
parameter_list|(
name|GroupTreeNodeViewModel
name|root
parameter_list|,
name|MoveGroupChange
name|moveChange
parameter_list|)
block|{
name|this
operator|.
name|root
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|root
argument_list|)
expr_stmt|;
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|moveChange
argument_list|)
expr_stmt|;
name|pathToOldParent
operator|=
name|moveChange
operator|.
name|getOldParent
argument_list|()
operator|.
name|getIndexedPathFromRoot
argument_list|()
expr_stmt|;
name|pathToNewParent
operator|=
name|moveChange
operator|.
name|getNewParent
argument_list|()
operator|.
name|getIndexedPathFromRoot
argument_list|()
expr_stmt|;
name|oldChildIndex
operator|=
name|moveChange
operator|.
name|getOldChildIndex
argument_list|()
expr_stmt|;
name|newChildIndex
operator|=
name|moveChange
operator|.
name|getNewChildIndex
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|getPresentationName ()
specifier|public
name|String
name|getPresentationName
parameter_list|()
block|{
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"move group"
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|undo ()
specifier|public
name|void
name|undo
parameter_list|()
block|{
name|super
operator|.
name|undo
argument_list|()
expr_stmt|;
name|GroupTreeNode
name|newParent
init|=
name|root
operator|.
name|getNode
argument_list|()
operator|.
name|getDescendant
argument_list|(
name|pathToNewParent
argument_list|)
operator|.
name|get
argument_list|()
decl_stmt|;
comment|//TODO: NULL
name|GroupTreeNode
name|node
init|=
name|newParent
operator|.
name|getChildAt
argument_list|(
name|newChildIndex
argument_list|)
operator|.
name|get
argument_list|()
decl_stmt|;
comment|//TODO: Null
comment|//TODO: NULL
name|node
operator|.
name|moveTo
argument_list|(
name|root
operator|.
name|getNode
argument_list|()
operator|.
name|getDescendant
argument_list|(
name|pathToOldParent
argument_list|)
operator|.
name|get
argument_list|()
argument_list|,
name|oldChildIndex
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|redo ()
specifier|public
name|void
name|redo
parameter_list|()
block|{
name|super
operator|.
name|redo
argument_list|()
expr_stmt|;
name|GroupTreeNode
name|oldParent
init|=
name|root
operator|.
name|getNode
argument_list|()
operator|.
name|getDescendant
argument_list|(
name|pathToOldParent
argument_list|)
operator|.
name|get
argument_list|()
decl_stmt|;
comment|//TODO: NULL
name|GroupTreeNode
name|node
init|=
name|oldParent
operator|.
name|getChildAt
argument_list|(
name|oldChildIndex
argument_list|)
operator|.
name|get
argument_list|()
decl_stmt|;
comment|//TODO:Null
comment|//TODO: NULL
name|node
operator|.
name|moveTo
argument_list|(
name|root
operator|.
name|getNode
argument_list|()
operator|.
name|getDescendant
argument_list|(
name|pathToNewParent
argument_list|)
operator|.
name|get
argument_list|()
argument_list|,
name|newChildIndex
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

