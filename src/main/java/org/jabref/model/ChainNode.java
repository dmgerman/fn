begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.model
package|package
name|org
operator|.
name|jabref
operator|.
name|model
package|;
end_package

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

begin_comment
comment|/**  * Represents a node in a chain.  * We view a chain as a vertical hierarchy and thus refer to the previous node as parent and the next node is a child.  *<p>  * In usual implementations, nodes function as wrappers around a data object. Thus normally they have a value property  * which allows access to the value stored in the node.  * In contrast to this approach, the ChainNode<T> class is designed to be used as a base class which provides the  * tree traversing functionality via inheritance.  *<p>  * Example usage:  * private class BasicChainNode extends ChainNode<BasicChainNode> {  * public BasicChainNode() {  * super(BasicChainNode.class);  * }  * }  *  * @param<T> the type of the class  */
end_comment

begin_class
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
comment|// We use some explicit casts of the form "(T) this". The constructor ensures that this cast is valid.
DECL|class|ChainNode
specifier|public
specifier|abstract
class|class
name|ChainNode
parameter_list|<
name|T
extends|extends
name|ChainNode
parameter_list|<
name|T
parameter_list|>
parameter_list|>
block|{
comment|/**      * This node's parent, or null if this node has no parent      */
DECL|field|parent
specifier|private
name|T
name|parent
decl_stmt|;
comment|/**      * This node's child, or null if this node has no child      */
DECL|field|child
specifier|private
name|T
name|child
decl_stmt|;
comment|/**      * Constructs a chain node without parent and no child.      *      * @param derivingClass class deriving from TreeNode<T>. It should always be "T.class".      *                      We need this parameter since it is hard to get this information by other means.      */
DECL|method|ChainNode (Class<T> derivingClass)
specifier|public
name|ChainNode
parameter_list|(
name|Class
argument_list|<
name|T
argument_list|>
name|derivingClass
parameter_list|)
block|{
name|parent
operator|=
literal|null
expr_stmt|;
name|child
operator|=
literal|null
expr_stmt|;
if|if
condition|(
operator|!
name|derivingClass
operator|.
name|isInstance
argument_list|(
name|this
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|UnsupportedOperationException
argument_list|(
literal|"The class extending ChainNode<T> has to derive from T"
argument_list|)
throw|;
block|}
block|}
comment|/**      * Returns this node's parent or an empty Optional if this node has no parent.      *      * @return this node's parent T, or an empty Optional if this node has no parent      */
DECL|method|getParent ()
specifier|public
name|Optional
argument_list|<
name|T
argument_list|>
name|getParent
parameter_list|()
block|{
return|return
name|Optional
operator|.
name|ofNullable
argument_list|(
name|parent
argument_list|)
return|;
block|}
comment|/**      * Returns this node's child or an empty Optional if this node has no child.      *      * @return this node's child T, or an empty Optional if this node has no child      */
DECL|method|getChild ()
specifier|public
name|Optional
argument_list|<
name|T
argument_list|>
name|getChild
parameter_list|()
block|{
return|return
name|Optional
operator|.
name|ofNullable
argument_list|(
name|child
argument_list|)
return|;
block|}
comment|/**      * Removes this node from its parent and makes it a child of the specified node.      * In this way the whole subchain based at this node is moved to the given node.      *      * @param target      the new parent      * @throws NullPointerException           if target is null      * @throws UnsupportedOperationException  if target is an descendant of this node      */
DECL|method|moveTo (T target)
specifier|public
name|void
name|moveTo
parameter_list|(
name|T
name|target
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|target
argument_list|)
expr_stmt|;
comment|// Check that the target node is not an ancestor of this node, because this would create loops in the tree
if|if
condition|(
name|this
operator|.
name|isAncestorOf
argument_list|(
name|target
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|UnsupportedOperationException
argument_list|(
literal|"the target cannot be a descendant of this node"
argument_list|)
throw|;
block|}
comment|// Remove from previous parent
name|getParent
argument_list|()
operator|.
name|ifPresent
argument_list|(
name|oldParent
lambda|->
name|oldParent
operator|.
name|removeChild
argument_list|()
argument_list|)
expr_stmt|;
comment|// Add as child
name|target
operator|.
name|setChild
argument_list|(
operator|(
name|T
operator|)
name|this
argument_list|)
expr_stmt|;
block|}
comment|/**      * Adds the node as the child. Also sets the parent of the given node to this node.      * The given node is not allowed to already be in a tree (i.e. it has to have no parent).      *      * @param child the node to add as child      * @return the child node      * @throws UnsupportedOperationException if the given node has already a parent      */
DECL|method|setChild (T child)
specifier|public
name|T
name|setChild
parameter_list|(
name|T
name|child
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|child
argument_list|)
expr_stmt|;
if|if
condition|(
name|child
operator|.
name|getParent
argument_list|()
operator|.
name|isPresent
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|UnsupportedOperationException
argument_list|(
literal|"Cannot add a node which already has a parent, use moveTo instead"
argument_list|)
throw|;
block|}
name|child
operator|.
name|setParent
argument_list|(
operator|(
name|T
operator|)
name|this
argument_list|)
expr_stmt|;
name|this
operator|.
name|child
operator|=
name|child
expr_stmt|;
return|return
name|child
return|;
block|}
comment|/**      * Sets the parent node of this node.      *<p>      * This method does not set this node as the child of the new parent nor does it remove this node      * from the old parent. You should probably call {@link #moveTo(ChainNode)} to change the chain.      *      * @param parent the new parent      */
DECL|method|setParent (T parent)
specifier|protected
name|void
name|setParent
parameter_list|(
name|T
name|parent
parameter_list|)
block|{
name|this
operator|.
name|parent
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|parent
argument_list|)
expr_stmt|;
block|}
comment|/**      * Removes the child from this node's child list, giving it an empty parent.      *      */
DECL|method|removeChild ()
specifier|public
name|void
name|removeChild
parameter_list|()
block|{
if|if
condition|(
name|child
operator|!=
literal|null
condition|)
block|{
comment|// NPE if this is ever called
name|child
operator|.
name|setParent
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
name|child
operator|=
literal|null
expr_stmt|;
block|}
comment|/**      * Returns true if this node is an ancestor of the given node.      *<p>      * A node is considered an ancestor of itself.      *      * @param anotherNode node to test      * @return true if anotherNode is a descendant of this node      * @throws NullPointerException if anotherNode is null      */
DECL|method|isAncestorOf (T anotherNode)
specifier|public
name|boolean
name|isAncestorOf
parameter_list|(
name|T
name|anotherNode
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|anotherNode
argument_list|)
expr_stmt|;
if|if
condition|(
name|anotherNode
operator|==
name|this
condition|)
block|{
return|return
literal|true
return|;
block|}
else|else
block|{
return|return
name|child
operator|.
name|isAncestorOf
argument_list|(
name|anotherNode
argument_list|)
return|;
block|}
block|}
block|}
end_class

end_unit

