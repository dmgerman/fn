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
name|datatransfer
operator|.
name|Transferable
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

begin_comment
comment|/**  * A node in the groups tree that holds exactly one AbstractGroup.  *   * @author jzieren  */
end_comment

begin_class
DECL|class|GroupTreeNode
specifier|public
class|class
name|GroupTreeNode
extends|extends
name|DefaultMutableTreeNode
implements|implements
name|Transferable
block|{
DECL|field|GROUP_UNION_CHILDREN
specifier|public
specifier|static
specifier|final
name|int
name|GROUP_UNION_CHILDREN
init|=
literal|0
decl_stmt|;
DECL|field|GROUP_INTERSECTION_PARENT
specifier|public
specifier|static
specifier|final
name|int
name|GROUP_INTERSECTION_PARENT
init|=
literal|1
decl_stmt|;
DECL|field|GROUP_ITSELF
specifier|public
specifier|static
specifier|final
name|int
name|GROUP_ITSELF
init|=
literal|2
decl_stmt|;
DECL|field|flavor
specifier|public
specifier|static
specifier|final
name|DataFlavor
name|flavor
decl_stmt|;
DECL|field|flavors
specifier|public
specifier|static
specifier|final
name|DataFlavor
index|[]
name|flavors
decl_stmt|;
static|static
block|{
name|DataFlavor
name|df
init|=
literal|null
decl_stmt|;
try|try
block|{
name|df
operator|=
operator|new
name|DataFlavor
argument_list|(
name|DataFlavor
operator|.
name|javaJVMLocalObjectMimeType
operator|+
literal|";class=net.sf.jabref.groups.GroupTreeNode"
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ClassNotFoundException
name|e
parameter_list|)
block|{
comment|// never happens
block|}
name|flavor
operator|=
name|df
expr_stmt|;
name|flavors
operator|=
operator|new
name|DataFlavor
index|[]
block|{
name|flavor
block|}
expr_stmt|;
block|}
comment|/**      * Creates this node and associates the specified group with it.      */
DECL|method|GroupTreeNode (AbstractGroup group)
specifier|public
name|GroupTreeNode
parameter_list|(
name|AbstractGroup
name|group
parameter_list|)
block|{
name|setGroup
argument_list|(
name|group
argument_list|)
expr_stmt|;
block|}
comment|/**      * @return The group associated with this node.      */
DECL|method|getGroup ()
specifier|public
name|AbstractGroup
name|getGroup
parameter_list|()
block|{
return|return
operator|(
name|AbstractGroup
operator|)
name|getUserObject
argument_list|()
return|;
block|}
comment|/**      * Associates the specified group with this node.      */
DECL|method|setGroup (AbstractGroup group)
specifier|public
name|void
name|setGroup
parameter_list|(
name|AbstractGroup
name|group
parameter_list|)
block|{
name|setUserObject
argument_list|(
name|group
argument_list|)
expr_stmt|;
block|}
comment|/**      * Returns a textual representation of this node and its children. This      * representation contains both the tree structure and the textual      * representations of the group associated with each node. It thus allows a      * complete reconstruction of this object and its children.      */
DECL|method|toString ()
specifier|public
name|String
name|toString
parameter_list|()
block|{
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
if|if
condition|(
name|getChildCount
argument_list|()
operator|>
literal|0
condition|)
name|sb
operator|.
name|append
argument_list|(
literal|"("
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|Util
operator|.
name|quote
argument_list|(
name|getGroup
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|,
literal|"(),;"
argument_list|,
literal|'\\'
argument_list|)
argument_list|)
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
name|getChildCount
argument_list|()
condition|;
operator|++
name|i
control|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|","
operator|+
name|getChildAt
argument_list|(
name|i
argument_list|)
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|getChildCount
argument_list|()
operator|>
literal|0
condition|)
name|sb
operator|.
name|append
argument_list|(
literal|")"
argument_list|)
expr_stmt|;
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
comment|/**      * Creates a deep copy of this node and all of its children, including all      * groups.      *       * @return This object's deep copy.      */
DECL|method|deepCopy ()
specifier|public
name|GroupTreeNode
name|deepCopy
parameter_list|()
block|{
name|GroupTreeNode
name|copy
init|=
operator|new
name|GroupTreeNode
argument_list|(
name|getGroup
argument_list|()
argument_list|)
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
name|getChildCount
argument_list|()
condition|;
operator|++
name|i
control|)
name|copy
operator|.
name|add
argument_list|(
operator|(
operator|(
name|GroupTreeNode
operator|)
name|getChildAt
argument_list|(
name|i
argument_list|)
operator|)
operator|.
name|deepCopy
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|copy
return|;
block|}
comment|/**      * @return An indexed path from the root node to this node. The elements in      *         the returned array represent the child index of each node in the      *         path. If this node is the root node, the returned array has zero      *         elements.      */
DECL|method|getIndexedPath ()
specifier|public
name|int
index|[]
name|getIndexedPath
parameter_list|()
block|{
name|TreeNode
index|[]
name|path
init|=
name|getPath
argument_list|()
decl_stmt|;
name|int
index|[]
name|indexedPath
init|=
operator|new
name|int
index|[
name|path
operator|.
name|length
operator|-
literal|1
index|]
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|1
init|;
name|i
operator|<
name|path
operator|.
name|length
condition|;
operator|++
name|i
control|)
name|indexedPath
index|[
name|i
operator|-
literal|1
index|]
operator|=
name|path
index|[
name|i
operator|-
literal|1
index|]
operator|.
name|getIndex
argument_list|(
name|path
index|[
name|i
index|]
argument_list|)
expr_stmt|;
return|return
name|indexedPath
return|;
block|}
comment|/**      * @param indexedPath      *            A sequence of child indices that describe a path from this      *            node to one of its desendants. Be aware that if<b>indexedPath      *</b> was obtained by getIndexedPath(), this node should      *            usually be the root node.      * @return The descendant found by evaluating<b>indexedPath</b>. If the      *         path could not be traversed completely (i.e. one of the child      *         indices did not exist), null will be returned.      */
DECL|method|getDescendant (int[] indexedPath)
specifier|public
name|GroupTreeNode
name|getDescendant
parameter_list|(
name|int
index|[]
name|indexedPath
parameter_list|)
block|{
name|GroupTreeNode
name|cursor
init|=
name|this
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
name|indexedPath
operator|.
name|length
operator|&&
name|cursor
operator|!=
literal|null
condition|;
operator|++
name|i
control|)
name|cursor
operator|=
operator|(
name|GroupTreeNode
operator|)
name|cursor
operator|.
name|getChildAt
argument_list|(
name|indexedPath
index|[
name|i
index|]
argument_list|)
expr_stmt|;
return|return
name|cursor
return|;
block|}
comment|/**      * A GroupTreeNode can create a SearchRule that finds elements contained in      * its own group (GROUP_ITSELF), or the union of those elements in its own      * group and its children's groups (recursively) (GROUP_UNION_CHILDREN), or      * the intersection of the elements in its own group and its parent's group      * (GROUP_INTERSECTION_PARENT).      *       * @return A SearchRule that finds the desired elements.      */
DECL|method|getSearchRule (int searchMode)
specifier|public
name|SearchRule
name|getSearchRule
parameter_list|(
name|int
name|searchMode
parameter_list|)
block|{
if|if
condition|(
name|searchMode
operator|==
name|GROUP_ITSELF
condition|)
return|return
name|getGroup
argument_list|()
operator|.
name|getSearchRule
argument_list|()
return|;
name|AndOrSearchRuleSet
name|searchRule
init|=
operator|new
name|AndOrSearchRuleSet
argument_list|(
name|searchMode
operator|==
name|GROUP_INTERSECTION_PARENT
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|searchRule
operator|.
name|addRule
argument_list|(
name|getGroup
argument_list|()
operator|.
name|getSearchRule
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|searchMode
operator|==
name|GROUP_UNION_CHILDREN
condition|)
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|getChildCount
argument_list|()
condition|;
operator|++
name|i
control|)
name|searchRule
operator|.
name|addRule
argument_list|(
operator|(
operator|(
name|GroupTreeNode
operator|)
name|getChildAt
argument_list|(
name|i
argument_list|)
operator|)
operator|.
name|getSearchRule
argument_list|(
name|searchMode
argument_list|)
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|searchMode
operator|==
name|GROUP_INTERSECTION_PARENT
operator|&&
operator|!
name|isRoot
argument_list|()
condition|)
block|{
name|searchRule
operator|.
name|addRule
argument_list|(
operator|(
operator|(
name|GroupTreeNode
operator|)
name|getParent
argument_list|()
operator|)
operator|.
name|getSearchRule
argument_list|(
name|searchMode
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|searchRule
return|;
block|}
comment|/**      * Scans the subtree rooted at this node.      *       * @return All groups that contain the specified entry.      */
DECL|method|getMatchingGroups (BibtexEntry entry)
specifier|public
name|AbstractGroup
index|[]
name|getMatchingGroups
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|)
block|{
name|Vector
name|matchingGroups
init|=
operator|new
name|Vector
argument_list|()
decl_stmt|;
name|Enumeration
name|e
init|=
name|preorderEnumeration
argument_list|()
decl_stmt|;
name|AbstractGroup
name|group
decl_stmt|;
while|while
condition|(
name|e
operator|.
name|hasMoreElements
argument_list|()
condition|)
block|{
name|group
operator|=
operator|(
operator|(
name|GroupTreeNode
operator|)
name|e
operator|.
name|nextElement
argument_list|()
operator|)
operator|.
name|getGroup
argument_list|()
expr_stmt|;
if|if
condition|(
name|group
operator|.
name|contains
argument_list|(
literal|null
argument_list|,
name|entry
argument_list|)
condition|)
comment|// first argument is never used
name|matchingGroups
operator|.
name|add
argument_list|(
name|group
argument_list|)
expr_stmt|;
block|}
name|AbstractGroup
index|[]
name|matchingGroupsArray
init|=
operator|new
name|AbstractGroup
index|[
name|matchingGroups
operator|.
name|size
argument_list|()
index|]
decl_stmt|;
return|return
operator|(
name|AbstractGroup
index|[]
operator|)
name|matchingGroups
operator|.
name|toArray
argument_list|(
name|matchingGroupsArray
argument_list|)
return|;
block|}
DECL|method|canMoveUp ()
specifier|public
name|boolean
name|canMoveUp
parameter_list|()
block|{
return|return
name|getPreviousSibling
argument_list|()
operator|!=
literal|null
operator|&&
operator|!
operator|(
name|getGroup
argument_list|()
operator|instanceof
name|AllEntriesGroup
operator|)
return|;
block|}
DECL|method|canMoveDown ()
specifier|public
name|boolean
name|canMoveDown
parameter_list|()
block|{
return|return
name|getNextSibling
argument_list|()
operator|!=
literal|null
operator|&&
operator|!
operator|(
name|getGroup
argument_list|()
operator|instanceof
name|AllEntriesGroup
operator|)
return|;
block|}
DECL|method|canMoveLeft ()
specifier|public
name|boolean
name|canMoveLeft
parameter_list|()
block|{
return|return
operator|!
operator|(
name|getGroup
argument_list|()
operator|instanceof
name|AllEntriesGroup
operator|)
operator|&&
operator|!
operator|(
operator|(
operator|(
name|GroupTreeNode
operator|)
name|getParent
argument_list|()
operator|)
operator|.
name|getGroup
argument_list|()
operator|instanceof
name|AllEntriesGroup
operator|)
return|;
block|}
DECL|method|canMoveRight ()
specifier|public
name|boolean
name|canMoveRight
parameter_list|()
block|{
return|return
name|getPreviousSibling
argument_list|()
operator|!=
literal|null
operator|&&
operator|!
operator|(
name|getGroup
argument_list|()
operator|instanceof
name|AllEntriesGroup
operator|)
return|;
block|}
DECL|method|moveUp (GroupSelector groupSelector)
specifier|public
name|AbstractUndoableEdit
name|moveUp
parameter_list|(
name|GroupSelector
name|groupSelector
parameter_list|)
block|{
specifier|final
name|GroupTreeNode
name|myParent
init|=
operator|(
name|GroupTreeNode
operator|)
name|getParent
argument_list|()
decl_stmt|;
specifier|final
name|int
name|index
init|=
name|myParent
operator|.
name|getIndex
argument_list|(
name|this
argument_list|)
decl_stmt|;
if|if
condition|(
name|index
operator|>
literal|0
condition|)
block|{
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
name|this
argument_list|,
name|myParent
argument_list|,
name|index
operator|-
literal|1
argument_list|)
decl_stmt|;
name|myParent
operator|.
name|insert
argument_list|(
name|this
argument_list|,
name|index
operator|-
literal|1
argument_list|)
expr_stmt|;
return|return
name|undo
return|;
block|}
return|return
literal|null
return|;
block|}
DECL|method|moveDown (GroupSelector groupSelector)
specifier|public
name|AbstractUndoableEdit
name|moveDown
parameter_list|(
name|GroupSelector
name|groupSelector
parameter_list|)
block|{
specifier|final
name|GroupTreeNode
name|myParent
init|=
operator|(
name|GroupTreeNode
operator|)
name|getParent
argument_list|()
decl_stmt|;
specifier|final
name|int
name|index
init|=
name|myParent
operator|.
name|getIndex
argument_list|(
name|this
argument_list|)
decl_stmt|;
if|if
condition|(
name|index
operator|<
name|parent
operator|.
name|getChildCount
argument_list|()
operator|-
literal|1
condition|)
block|{
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
name|this
argument_list|,
name|myParent
argument_list|,
name|index
operator|+
literal|1
argument_list|)
decl_stmt|;
name|myParent
operator|.
name|insert
argument_list|(
name|this
argument_list|,
name|index
operator|+
literal|1
argument_list|)
expr_stmt|;
return|return
name|undo
return|;
block|}
return|return
literal|null
return|;
block|}
DECL|method|moveLeft (GroupSelector groupSelector)
specifier|public
name|AbstractUndoableEdit
name|moveLeft
parameter_list|(
name|GroupSelector
name|groupSelector
parameter_list|)
block|{
specifier|final
name|GroupTreeNode
name|myParent
init|=
operator|(
name|GroupTreeNode
operator|)
name|getParent
argument_list|()
decl_stmt|;
specifier|final
name|GroupTreeNode
name|myGrandParent
init|=
operator|(
name|GroupTreeNode
operator|)
name|myParent
operator|.
name|getParent
argument_list|()
decl_stmt|;
comment|// paranoia
if|if
condition|(
name|myGrandParent
operator|==
literal|null
condition|)
return|return
literal|null
return|;
specifier|final
name|int
name|index
init|=
name|myGrandParent
operator|.
name|getIndex
argument_list|(
name|myParent
argument_list|)
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
name|this
argument_list|,
name|myGrandParent
argument_list|,
name|index
operator|+
literal|1
argument_list|)
decl_stmt|;
name|myGrandParent
operator|.
name|insert
argument_list|(
name|this
argument_list|,
name|index
operator|+
literal|1
argument_list|)
expr_stmt|;
return|return
name|undo
return|;
block|}
DECL|method|moveRight (GroupSelector groupSelector)
specifier|public
name|AbstractUndoableEdit
name|moveRight
parameter_list|(
name|GroupSelector
name|groupSelector
parameter_list|)
block|{
specifier|final
name|GroupTreeNode
name|myPreviousSibling
init|=
operator|(
name|GroupTreeNode
operator|)
name|getPreviousSibling
argument_list|()
decl_stmt|;
comment|// paranoia
if|if
condition|(
name|myPreviousSibling
operator|==
literal|null
condition|)
return|return
literal|null
return|;
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
name|this
argument_list|,
name|myPreviousSibling
argument_list|,
name|myPreviousSibling
operator|.
name|getChildCount
argument_list|()
argument_list|)
decl_stmt|;
name|myPreviousSibling
operator|.
name|add
argument_list|(
name|this
argument_list|)
expr_stmt|;
return|return
name|undo
return|;
block|}
comment|/**      * @param path      *            A sequence of child indices that designate a node relative to      *            this node.      * @return The node designated by the specified path, or null if one or more      *         indices in the path could not be resolved.      */
DECL|method|getChildAt (int[] path)
specifier|public
name|GroupTreeNode
name|getChildAt
parameter_list|(
name|int
index|[]
name|path
parameter_list|)
block|{
name|GroupTreeNode
name|cursor
init|=
name|this
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
name|path
operator|.
name|length
operator|&&
name|cursor
operator|!=
literal|null
condition|;
operator|++
name|i
control|)
name|cursor
operator|=
operator|(
name|GroupTreeNode
operator|)
name|cursor
operator|.
name|getChildAt
argument_list|(
name|path
index|[
name|i
index|]
argument_list|)
expr_stmt|;
return|return
name|cursor
return|;
block|}
comment|/** Adds the selected entries to this node's group. */
DECL|method|addToGroup (BibtexEntry[] entries)
specifier|public
name|AbstractUndoableEdit
name|addToGroup
parameter_list|(
name|BibtexEntry
index|[]
name|entries
parameter_list|)
block|{
if|if
condition|(
name|getGroup
argument_list|()
operator|==
literal|null
condition|)
return|return
literal|null
return|;
comment|// paranoia
name|AbstractUndoableEdit
name|undo
init|=
name|getGroup
argument_list|()
operator|.
name|add
argument_list|(
name|entries
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
name|this
argument_list|)
expr_stmt|;
return|return
name|undo
return|;
block|}
comment|/** Removes the selected entries from this node's group. */
DECL|method|removeFromGroup (BibtexEntry[] entries)
specifier|public
name|AbstractUndoableEdit
name|removeFromGroup
parameter_list|(
name|BibtexEntry
index|[]
name|entries
parameter_list|)
block|{
if|if
condition|(
name|getGroup
argument_list|()
operator|==
literal|null
condition|)
return|return
literal|null
return|;
comment|// paranoia
name|AbstractUndoableEdit
name|undo
init|=
name|getGroup
argument_list|()
operator|.
name|remove
argument_list|(
name|entries
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
name|this
argument_list|)
expr_stmt|;
return|return
name|undo
return|;
block|}
DECL|method|getTransferDataFlavors ()
specifier|public
name|DataFlavor
index|[]
name|getTransferDataFlavors
parameter_list|()
block|{
return|return
name|flavors
return|;
block|}
DECL|method|isDataFlavorSupported (DataFlavor someFlavor)
specifier|public
name|boolean
name|isDataFlavorSupported
parameter_list|(
name|DataFlavor
name|someFlavor
parameter_list|)
block|{
return|return
name|someFlavor
operator|.
name|equals
argument_list|(
name|GroupTreeNode
operator|.
name|flavor
argument_list|)
return|;
block|}
DECL|method|getTransferData (DataFlavor someFlavor)
specifier|public
name|Object
name|getTransferData
parameter_list|(
name|DataFlavor
name|someFlavor
parameter_list|)
throws|throws
name|UnsupportedFlavorException
throws|,
name|IOException
block|{
if|if
condition|(
operator|!
name|isDataFlavorSupported
argument_list|(
name|someFlavor
argument_list|)
condition|)
throw|throw
operator|new
name|UnsupportedFlavorException
argument_list|(
name|someFlavor
argument_list|)
throw|;
return|return
name|this
return|;
block|}
block|}
end_class

end_unit

