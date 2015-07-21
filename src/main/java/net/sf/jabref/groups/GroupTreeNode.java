begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|DataFlavor
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
name|Vector
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
name|DefaultMutableTreeNode
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
name|TreeNode
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
name|BibtexDatabase
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

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|search
operator|.
name|rules
operator|.
name|AndOrSearchRuleSet
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
name|search
operator|.
name|SearchRule
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
DECL|field|flavor
specifier|public
specifier|static
specifier|final
name|DataFlavor
name|flavor
decl_stmt|;
DECL|field|flavors
specifier|private
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
name|GroupTreeNode
operator|.
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
DECL|method|getTreeAsString ()
specifier|public
name|String
name|getTreeAsString
parameter_list|()
block|{
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|Enumeration
argument_list|<
name|GroupTreeNode
argument_list|>
name|e
init|=
name|preorderEnumeration
argument_list|()
decl_stmt|;
name|GroupTreeNode
name|cursor
decl_stmt|;
while|while
condition|(
name|e
operator|.
name|hasMoreElements
argument_list|()
condition|)
block|{
name|cursor
operator|=
name|e
operator|.
name|nextElement
argument_list|()
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|cursor
operator|.
name|getLevel
argument_list|()
argument_list|)
operator|.
name|append
argument_list|(
literal|" "
argument_list|)
operator|.
name|append
argument_list|(
name|cursor
operator|.
name|getGroup
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
operator|.
name|append
argument_list|(
literal|"\n"
argument_list|)
expr_stmt|;
block|}
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
block|{
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
block|}
return|return
name|copy
return|;
block|}
comment|/**      * Update all groups, if necessary, to handle the situation where the group      * tree is applied to a different BibtexDatabase than it was created for. This      * is for instance used when updating the group tree due to an external change.      *      * @param db The database to refresh for.      */
DECL|method|refreshGroupsForNewDatabase (BibtexDatabase db)
specifier|public
name|void
name|refreshGroupsForNewDatabase
parameter_list|(
name|BibtexDatabase
name|db
parameter_list|)
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
block|{
name|GroupTreeNode
name|node
init|=
operator|(
name|GroupTreeNode
operator|)
name|getChildAt
argument_list|(
name|i
argument_list|)
decl_stmt|;
name|node
operator|.
name|getGroup
argument_list|()
operator|.
name|refreshForNewDatabase
argument_list|(
name|db
argument_list|)
expr_stmt|;
name|node
operator|.
name|refreshGroupsForNewDatabase
argument_list|(
name|db
argument_list|)
expr_stmt|;
block|}
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
block|{
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
block|}
return|return
name|indexedPath
return|;
block|}
comment|/**      * Returns the node indicated by the specified indexedPath, which contains      * child indices obtained e.g. by getIndexedPath().      */
DECL|method|getNode (int[] indexedPath)
specifier|public
name|GroupTreeNode
name|getNode
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
name|anIndexedPath
range|:
name|indexedPath
control|)
block|{
name|cursor
operator|=
operator|(
name|GroupTreeNode
operator|)
name|cursor
operator|.
name|getChildAt
argument_list|(
name|anIndexedPath
argument_list|)
expr_stmt|;
block|}
return|return
name|cursor
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
operator|(
name|i
operator|<
name|indexedPath
operator|.
name|length
operator|)
operator|&&
operator|(
name|cursor
operator|!=
literal|null
operator|)
condition|;
operator|++
name|i
control|)
block|{
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
block|}
return|return
name|cursor
return|;
block|}
comment|/**      * A GroupTreeNode can create a SearchRule that finds elements contained in      * its own group, or the union of those elements in its own group and its      * children's groups (recursively), or the intersection of the elements in      * its own group and its parent's group. This setting is configured in the      * group contained in this node.      *       * @return A SearchRule that finds the desired elements.      */
DECL|method|getSearchRule ()
specifier|public
name|SearchRule
name|getSearchRule
parameter_list|()
block|{
return|return
name|getSearchRule
argument_list|(
name|getGroup
argument_list|()
operator|.
name|getHierarchicalContext
argument_list|()
argument_list|)
return|;
block|}
DECL|method|getSearchRule (int originalContext)
specifier|private
name|SearchRule
name|getSearchRule
parameter_list|(
name|int
name|originalContext
parameter_list|)
block|{
specifier|final
name|int
name|context
init|=
name|getGroup
argument_list|()
operator|.
name|getHierarchicalContext
argument_list|()
decl_stmt|;
if|if
condition|(
name|context
operator|==
name|AbstractGroup
operator|.
name|INDEPENDENT
condition|)
block|{
return|return
name|getGroup
argument_list|()
operator|.
name|getSearchRule
argument_list|()
return|;
block|}
name|AndOrSearchRuleSet
name|searchRule
init|=
operator|new
name|AndOrSearchRuleSet
argument_list|(
name|context
operator|==
name|AbstractGroup
operator|.
name|REFINING
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
operator|(
name|context
operator|==
name|AbstractGroup
operator|.
name|INCLUDING
operator|)
operator|&&
operator|(
name|originalContext
operator|!=
name|AbstractGroup
operator|.
name|REFINING
operator|)
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
block|{
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
name|originalContext
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
operator|(
name|context
operator|==
name|AbstractGroup
operator|.
name|REFINING
operator|)
operator|&&
operator|!
name|isRoot
argument_list|()
operator|&&
operator|(
name|originalContext
operator|!=
name|AbstractGroup
operator|.
name|INCLUDING
operator|)
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
name|originalContext
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|searchRule
return|;
block|}
annotation|@
name|Override
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
DECL|method|preorderEnumeration ()
specifier|public
name|Enumeration
argument_list|<
name|GroupTreeNode
argument_list|>
name|preorderEnumeration
parameter_list|()
block|{
return|return
name|super
operator|.
name|preorderEnumeration
argument_list|()
return|;
block|}
annotation|@
name|Override
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
DECL|method|depthFirstEnumeration ()
specifier|public
name|Enumeration
argument_list|<
name|GroupTreeNode
argument_list|>
name|depthFirstEnumeration
parameter_list|()
block|{
return|return
name|super
operator|.
name|depthFirstEnumeration
argument_list|()
return|;
block|}
annotation|@
name|Override
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
DECL|method|breadthFirstEnumeration ()
specifier|public
name|Enumeration
argument_list|<
name|GroupTreeNode
argument_list|>
name|breadthFirstEnumeration
parameter_list|()
block|{
return|return
name|super
operator|.
name|breadthFirstEnumeration
argument_list|()
return|;
block|}
annotation|@
name|Override
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
DECL|method|children ()
specifier|public
name|Enumeration
argument_list|<
name|GroupTreeNode
argument_list|>
name|children
parameter_list|()
block|{
return|return
name|super
operator|.
name|children
argument_list|()
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
argument_list|<
name|AbstractGroup
argument_list|>
name|matchingGroups
init|=
operator|new
name|Vector
argument_list|<
name|AbstractGroup
argument_list|>
argument_list|()
decl_stmt|;
name|Enumeration
argument_list|<
name|GroupTreeNode
argument_list|>
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
block|{
name|matchingGroups
operator|.
name|add
argument_list|(
name|group
argument_list|)
expr_stmt|;
block|}
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
operator|(
name|getPreviousSibling
argument_list|()
operator|!=
literal|null
operator|)
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
operator|(
name|getNextSibling
argument_list|()
operator|!=
literal|null
operator|)
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
operator|(
name|getPreviousSibling
argument_list|()
operator|!=
literal|null
operator|)
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
operator|(
name|parent
operator|.
name|getChildCount
argument_list|()
operator|-
literal|1
operator|)
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
block|{
return|return
literal|null
return|;
block|}
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
block|{
return|return
literal|null
return|;
block|}
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
operator|(
name|i
operator|<
name|path
operator|.
name|length
operator|)
operator|&&
operator|(
name|cursor
operator|!=
literal|null
operator|)
condition|;
operator|++
name|i
control|)
block|{
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
block|}
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
block|{
return|return
literal|null
return|;
comment|// paranoia
block|}
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
block|{
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
block|}
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
block|{
return|return
literal|null
return|;
comment|// paranoia
block|}
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
block|{
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
block|}
return|return
name|undo
return|;
block|}
annotation|@
name|Override
DECL|method|getTransferDataFlavors ()
specifier|public
name|DataFlavor
index|[]
name|getTransferDataFlavors
parameter_list|()
block|{
return|return
name|GroupTreeNode
operator|.
name|flavors
return|;
block|}
annotation|@
name|Override
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
annotation|@
name|Override
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
block|{
throw|throw
operator|new
name|UnsupportedFlavorException
argument_list|(
name|someFlavor
argument_list|)
throw|;
block|}
return|return
name|this
return|;
block|}
comment|/**      * Recursively compares this node's group and all subgroups.      */
annotation|@
name|Override
DECL|method|equals (Object other)
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|other
parameter_list|)
block|{
if|if
condition|(
operator|!
operator|(
name|other
operator|instanceof
name|GroupTreeNode
operator|)
condition|)
block|{
return|return
literal|false
return|;
block|}
specifier|final
name|GroupTreeNode
name|otherNode
init|=
operator|(
name|GroupTreeNode
operator|)
name|other
decl_stmt|;
if|if
condition|(
name|getChildCount
argument_list|()
operator|!=
name|otherNode
operator|.
name|getChildCount
argument_list|()
condition|)
block|{
return|return
literal|false
return|;
block|}
name|AbstractGroup
name|g1
init|=
name|getGroup
argument_list|()
decl_stmt|;
name|AbstractGroup
name|g2
init|=
name|otherNode
operator|.
name|getGroup
argument_list|()
decl_stmt|;
if|if
condition|(
operator|(
operator|(
name|g1
operator|==
literal|null
operator|)
operator|&&
operator|(
name|g2
operator|!=
literal|null
operator|)
operator|)
operator|||
operator|(
operator|(
name|g1
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|g2
operator|==
literal|null
operator|)
operator|)
condition|)
block|{
return|return
literal|false
return|;
block|}
if|if
condition|(
operator|(
name|g1
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|g2
operator|!=
literal|null
operator|)
operator|&&
operator|!
name|g1
operator|.
name|equals
argument_list|(
name|g2
argument_list|)
condition|)
block|{
return|return
literal|false
return|;
block|}
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
if|if
condition|(
operator|!
name|getChildAt
argument_list|(
name|i
argument_list|)
operator|.
name|equals
argument_list|(
name|otherNode
operator|.
name|getChildAt
argument_list|(
name|i
argument_list|)
argument_list|)
condition|)
block|{
return|return
literal|false
return|;
block|}
block|}
return|return
literal|true
return|;
block|}
annotation|@
name|Override
DECL|method|hashCode ()
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|getGroup
argument_list|()
operator|.
name|getName
argument_list|()
operator|.
name|hashCode
argument_list|()
return|;
block|}
block|}
end_class

end_unit

