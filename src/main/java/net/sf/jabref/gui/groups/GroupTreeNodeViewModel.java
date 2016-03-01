begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.  This program is free software; you can redistribute it and/or modify  it under the terms of the GNU General Public License as published by  the Free Software Foundation; either version 2 of the License, or  (at your option) any later version.   This program is distributed in the hope that it will be useful,  but WITHOUT ANY WARRANTY; without even the implied warranty of  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  GNU General Public License for more details.   You should have received a copy of the GNU General Public License along  with this program; if not, write to the Free Software Foundation, Inc.,  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
end_comment

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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|Globals
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
name|JabRef
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
name|JabRefPreferences
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
name|BasePanel
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
name|IconTheme
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
name|CountingUndoManager
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
name|groups
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
name|logic
operator|.
name|util
operator|.
name|strings
operator|.
name|StringUtil
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
name|undo
operator|.
name|AbstractUndoableEdit
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
name|UndoManager
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
name|ArrayList
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
name|Optional
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|function
operator|.
name|Consumer
import|;
end_import

begin_class
DECL|class|GroupTreeNodeViewModel
specifier|public
class|class
name|GroupTreeNodeViewModel
implements|implements
name|Transferable
implements|,
name|TreeNode
block|{
DECL|field|MAX_DISPLAYED_LETTERS
specifier|private
specifier|static
specifier|final
name|int
name|MAX_DISPLAYED_LETTERS
init|=
literal|35
decl_stmt|;
DECL|field|GROUP_REFINING_ICON
specifier|private
specifier|static
specifier|final
name|Icon
name|GROUP_REFINING_ICON
init|=
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|GROUP_REFINING
operator|.
name|getSmallIcon
argument_list|()
decl_stmt|;
DECL|field|GROUP_INCLUDING_ICON
specifier|private
specifier|static
specifier|final
name|Icon
name|GROUP_INCLUDING_ICON
init|=
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|GROUP_INCLUDING
operator|.
name|getSmallIcon
argument_list|()
decl_stmt|;
DECL|field|GROUP_REGULAR_ICON
specifier|private
specifier|static
specifier|final
name|Icon
name|GROUP_REGULAR_ICON
init|=
literal|null
decl_stmt|;
DECL|field|FLAVOR
specifier|public
specifier|static
specifier|final
name|DataFlavor
name|FLAVOR
decl_stmt|;
DECL|field|FLAVORS
specifier|private
specifier|static
specifier|final
name|DataFlavor
index|[]
name|FLAVORS
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
literal|";class=net.sf.jabref.logic.groups.GroupTreeNode"
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
name|FLAVOR
operator|=
name|df
expr_stmt|;
name|FLAVORS
operator|=
operator|new
name|DataFlavor
index|[]
block|{
name|GroupTreeNodeViewModel
operator|.
name|FLAVOR
block|}
expr_stmt|;
block|}
DECL|field|node
specifier|private
specifier|final
name|GroupTreeNode
name|node
decl_stmt|;
DECL|method|GroupTreeNodeViewModel (GroupTreeNode node)
specifier|public
name|GroupTreeNodeViewModel
parameter_list|(
name|GroupTreeNode
name|node
parameter_list|)
block|{
name|this
operator|.
name|node
operator|=
name|node
expr_stmt|;
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
name|GroupTreeNodeViewModel
operator|.
name|FLAVORS
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
name|GroupTreeNodeViewModel
operator|.
name|FLAVOR
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
annotation|@
name|Override
DECL|method|getChildAt (int childIndex)
specifier|public
name|TreeNode
name|getChildAt
parameter_list|(
name|int
name|childIndex
parameter_list|)
block|{
return|return
name|node
operator|.
name|getChildAt
argument_list|(
name|childIndex
argument_list|)
operator|.
name|map
argument_list|(
name|GroupTreeNodeViewModel
operator|::
operator|new
argument_list|)
operator|.
name|orElse
argument_list|(
literal|null
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|getChildCount ()
specifier|public
name|int
name|getChildCount
parameter_list|()
block|{
return|return
name|node
operator|.
name|getNumberOfChildren
argument_list|()
return|;
block|}
annotation|@
name|Override
DECL|method|getParent ()
specifier|public
name|TreeNode
name|getParent
parameter_list|()
block|{
return|return
name|node
operator|.
name|getParent
argument_list|()
operator|.
name|map
argument_list|(
name|GroupTreeNodeViewModel
operator|::
operator|new
argument_list|)
operator|.
name|orElse
argument_list|(
literal|null
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|getIndex (TreeNode child)
specifier|public
name|int
name|getIndex
parameter_list|(
name|TreeNode
name|child
parameter_list|)
block|{
if|if
condition|(
operator|!
operator|(
name|child
operator|instanceof
name|GroupTreeNodeViewModel
operator|)
condition|)
block|{
return|return
operator|-
literal|1
return|;
block|}
name|GroupTreeNodeViewModel
name|childViewModel
init|=
operator|(
name|GroupTreeNodeViewModel
operator|)
name|child
decl_stmt|;
return|return
name|node
operator|.
name|getIndexOfChild
argument_list|(
name|childViewModel
operator|.
name|getNode
argument_list|()
argument_list|)
operator|.
name|orElse
argument_list|(
operator|-
literal|1
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|getAllowsChildren ()
specifier|public
name|boolean
name|getAllowsChildren
parameter_list|()
block|{
return|return
literal|true
return|;
block|}
annotation|@
name|Override
DECL|method|isLeaf ()
specifier|public
name|boolean
name|isLeaf
parameter_list|()
block|{
return|return
name|node
operator|.
name|isLeaf
argument_list|()
return|;
block|}
annotation|@
name|Override
DECL|method|children ()
specifier|public
name|Enumeration
name|children
parameter_list|()
block|{
name|Iterable
argument_list|<
name|GroupTreeNode
argument_list|>
name|children
init|=
name|node
operator|.
name|getChildren
argument_list|()
decl_stmt|;
return|return
operator|new
name|Enumeration
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|boolean
name|hasMoreElements
parameter_list|()
block|{
return|return
name|children
operator|.
name|iterator
argument_list|()
operator|.
name|hasNext
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|Object
name|nextElement
parameter_list|()
block|{
return|return
operator|new
name|GroupTreeNodeViewModel
argument_list|(
name|children
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
argument_list|)
return|;
block|}
block|}
return|;
block|}
DECL|method|getNode ()
specifier|public
name|GroupTreeNode
name|getNode
parameter_list|()
block|{
return|return
name|node
return|;
block|}
comment|/** Collapse this node and all its children. */
DECL|method|collapseSubtree (JTree tree)
specifier|public
name|void
name|collapseSubtree
parameter_list|(
name|JTree
name|tree
parameter_list|)
block|{
name|tree
operator|.
name|collapsePath
argument_list|(
name|this
operator|.
name|getTreePath
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|GroupTreeNodeViewModel
name|child
range|:
name|getChildren
argument_list|()
control|)
block|{
name|child
operator|.
name|collapseSubtree
argument_list|(
name|tree
argument_list|)
expr_stmt|;
block|}
block|}
comment|/** Expand this node and all its children. */
DECL|method|expandSubtree (JTree tree)
specifier|public
name|void
name|expandSubtree
parameter_list|(
name|JTree
name|tree
parameter_list|)
block|{
name|tree
operator|.
name|expandPath
argument_list|(
name|this
operator|.
name|getTreePath
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|GroupTreeNodeViewModel
name|child
range|:
name|getChildren
argument_list|()
control|)
block|{
name|child
operator|.
name|expandSubtree
argument_list|(
name|tree
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|getChildren ()
specifier|public
name|List
argument_list|<
name|GroupTreeNodeViewModel
argument_list|>
name|getChildren
parameter_list|()
block|{
name|List
argument_list|<
name|GroupTreeNodeViewModel
argument_list|>
name|children
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
name|GroupTreeNode
name|child
range|:
name|node
operator|.
name|getChildren
argument_list|()
control|)
block|{
name|children
operator|.
name|add
argument_list|(
operator|new
name|GroupTreeNodeViewModel
argument_list|(
name|child
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|children
return|;
block|}
DECL|method|printInItalics ()
specifier|protected
name|boolean
name|printInItalics
parameter_list|()
block|{
return|return
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|GROUP_SHOW_DYNAMIC
argument_list|)
operator|&&
name|node
operator|.
name|getGroup
argument_list|()
operator|.
name|isDynamic
argument_list|()
return|;
block|}
DECL|method|getText ()
specifier|public
name|String
name|getText
parameter_list|()
block|{
name|AbstractGroup
name|group
init|=
name|node
operator|.
name|getGroup
argument_list|()
decl_stmt|;
name|String
name|name
init|=
name|StringUtil
operator|.
name|limitStringLength
argument_list|(
name|group
operator|.
name|getName
argument_list|()
argument_list|,
name|MAX_DISPLAYED_LETTERS
argument_list|)
decl_stmt|;
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|(
literal|60
argument_list|)
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|name
argument_list|)
expr_stmt|;
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|GROUP_SHOW_NUMBER_OF_ELEMENTS
argument_list|)
condition|)
block|{
if|if
condition|(
name|group
operator|instanceof
name|ExplicitGroup
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|" ["
argument_list|)
operator|.
name|append
argument_list|(
operator|(
operator|(
name|ExplicitGroup
operator|)
name|group
operator|)
operator|.
name|getNumEntries
argument_list|()
argument_list|)
operator|.
name|append
argument_list|(
literal|']'
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
operator|(
name|group
operator|instanceof
name|KeywordGroup
operator|)
operator|||
operator|(
name|group
operator|instanceof
name|SearchGroup
operator|)
condition|)
block|{
name|int
name|hits
init|=
literal|0
decl_stmt|;
name|BasePanel
name|currentBasePanel
init|=
name|JabRef
operator|.
name|jrf
operator|.
name|getCurrentBasePanel
argument_list|()
decl_stmt|;
if|if
condition|(
name|currentBasePanel
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|BibEntry
name|entry
range|:
name|currentBasePanel
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
control|)
block|{
if|if
condition|(
name|group
operator|.
name|contains
argument_list|(
name|entry
argument_list|)
condition|)
block|{
name|hits
operator|++
expr_stmt|;
block|}
block|}
block|}
name|sb
operator|.
name|append
argument_list|(
literal|" ["
argument_list|)
operator|.
name|append
argument_list|(
name|hits
argument_list|)
operator|.
name|append
argument_list|(
literal|']'
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
DECL|method|getDescription ()
specifier|public
name|String
name|getDescription
parameter_list|()
block|{
return|return
literal|"<html>"
operator|+
name|node
operator|.
name|getGroup
argument_list|()
operator|.
name|getShortDescription
argument_list|()
operator|+
literal|"</html>"
return|;
block|}
DECL|method|getIcon ()
specifier|public
name|Icon
name|getIcon
parameter_list|()
block|{
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|GROUP_SHOW_ICONS
argument_list|)
condition|)
block|{
switch|switch
condition|(
name|node
operator|.
name|getGroup
argument_list|()
operator|.
name|getHierarchicalContext
argument_list|()
condition|)
block|{
case|case
name|REFINING
case|:
return|return
name|GROUP_REFINING_ICON
return|;
case|case
name|INCLUDING
case|:
return|return
name|GROUP_INCLUDING_ICON
return|;
default|default:
return|return
name|GROUP_REGULAR_ICON
return|;
block|}
block|}
else|else
block|{
return|return
literal|null
return|;
block|}
block|}
DECL|method|getTreePath ()
specifier|public
name|TreePath
name|getTreePath
parameter_list|()
block|{
name|List
argument_list|<
name|GroupTreeNode
argument_list|>
name|pathToNode
init|=
name|node
operator|.
name|getPathFromRoot
argument_list|()
decl_stmt|;
return|return
operator|new
name|TreePath
argument_list|(
name|pathToNode
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|GroupTreeNodeViewModel
operator|::
operator|new
argument_list|)
operator|.
name|toArray
argument_list|()
argument_list|)
return|;
block|}
DECL|method|canAddEntries (List<BibEntry> entries)
specifier|public
name|boolean
name|canAddEntries
parameter_list|(
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
parameter_list|)
block|{
return|return
name|getNode
argument_list|()
operator|.
name|getGroup
argument_list|()
operator|.
name|supportsAdd
argument_list|()
operator|&&
operator|!
name|getNode
argument_list|()
operator|.
name|getGroup
argument_list|()
operator|.
name|containsAll
argument_list|(
name|entries
argument_list|)
return|;
block|}
DECL|method|canRemoveEntries (List<BibEntry> entries)
specifier|public
name|boolean
name|canRemoveEntries
parameter_list|(
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
parameter_list|)
block|{
return|return
name|getNode
argument_list|()
operator|.
name|getGroup
argument_list|()
operator|.
name|supportsRemove
argument_list|()
operator|&&
name|getNode
argument_list|()
operator|.
name|getGroup
argument_list|()
operator|.
name|containsAny
argument_list|(
name|entries
argument_list|)
return|;
block|}
DECL|method|sortChildrenByName (boolean recursive)
specifier|public
name|void
name|sortChildrenByName
parameter_list|(
name|boolean
name|recursive
parameter_list|)
block|{
name|getNode
argument_list|()
operator|.
name|sortChildren
argument_list|(
parameter_list|(
name|node1
parameter_list|,
name|node2
parameter_list|)
lambda|->
name|node1
operator|.
name|getGroup
argument_list|()
operator|.
name|getName
argument_list|()
operator|.
name|compareToIgnoreCase
argument_list|(
name|node2
operator|.
name|getGroup
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|,
name|recursive
argument_list|)
expr_stmt|;
block|}
DECL|method|getName ()
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|getNode
argument_list|()
operator|.
name|getGroup
argument_list|()
operator|.
name|getName
argument_list|()
return|;
block|}
DECL|method|canBeEdited ()
specifier|public
name|boolean
name|canBeEdited
parameter_list|()
block|{
return|return
name|getNode
argument_list|()
operator|.
name|getGroup
argument_list|()
operator|instanceof
name|AllEntriesGroup
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
name|getNode
argument_list|()
operator|.
name|getPreviousSibling
argument_list|()
operator|!=
literal|null
operator|)
operator|&&
operator|!
operator|(
name|getNode
argument_list|()
operator|.
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
name|getNode
argument_list|()
operator|.
name|getNextSibling
argument_list|()
operator|!=
literal|null
operator|)
operator|&&
operator|!
operator|(
name|getNode
argument_list|()
operator|.
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
name|getNode
argument_list|()
operator|.
name|getGroup
argument_list|()
operator|instanceof
name|AllEntriesGroup
operator|)
comment|// TODO: Null!
operator|&&
operator|!
operator|(
name|getNode
argument_list|()
operator|.
name|getParent
argument_list|()
operator|.
name|get
argument_list|()
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
name|getNode
argument_list|()
operator|.
name|getPreviousSibling
argument_list|()
operator|!=
literal|null
operator|)
operator|&&
operator|!
operator|(
name|getNode
argument_list|()
operator|.
name|getGroup
argument_list|()
operator|instanceof
name|AllEntriesGroup
operator|)
return|;
block|}
DECL|method|changeEntriesTo (List<BibEntry> entries, UndoManager undoManager)
specifier|public
name|void
name|changeEntriesTo
parameter_list|(
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
parameter_list|,
name|UndoManager
name|undoManager
parameter_list|)
block|{
name|AbstractGroup
name|group
init|=
name|node
operator|.
name|getGroup
argument_list|()
decl_stmt|;
name|Optional
argument_list|<
name|EntriesGroupChange
argument_list|>
name|changesRemove
init|=
name|Optional
operator|.
name|empty
argument_list|()
decl_stmt|;
name|Optional
argument_list|<
name|EntriesGroupChange
argument_list|>
name|changesAdd
init|=
name|Optional
operator|.
name|empty
argument_list|()
decl_stmt|;
comment|// Sort entries into current members and non-members of the group
comment|// Current members will be removed
comment|// Current non-members will be added
name|List
argument_list|<
name|BibEntry
argument_list|>
name|toRemove
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|(
name|entries
operator|.
name|size
argument_list|()
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|BibEntry
argument_list|>
name|toAdd
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|(
name|entries
operator|.
name|size
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|BibEntry
name|entry
range|:
name|entries
control|)
block|{
comment|// Sort according to current state of the entries
if|if
condition|(
name|group
operator|.
name|contains
argument_list|(
name|entry
argument_list|)
condition|)
block|{
name|toRemove
operator|.
name|add
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|toAdd
operator|.
name|add
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
block|}
comment|// If there are entries to remove
if|if
condition|(
operator|!
name|toRemove
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|changesRemove
operator|=
name|removeEntriesFromGroup
argument_list|(
name|toRemove
argument_list|)
expr_stmt|;
block|}
comment|// If there are entries to add
if|if
condition|(
operator|!
name|toAdd
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|changesAdd
operator|=
name|addEntriesToGroup
argument_list|(
name|toAdd
argument_list|)
expr_stmt|;
block|}
comment|// Remember undo information
if|if
condition|(
name|changesRemove
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|AbstractUndoableEdit
name|undoRemove
init|=
name|UndoableChangeEntriesOfGroup
operator|.
name|getUndoableEdit
argument_list|(
name|this
argument_list|,
name|changesRemove
operator|.
name|get
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|changesAdd
operator|.
name|isPresent
argument_list|()
operator|&&
name|undoRemove
operator|!=
literal|null
condition|)
block|{
comment|// we removed and added entries
name|undoRemove
operator|.
name|addEdit
argument_list|(
name|UndoableChangeEntriesOfGroup
operator|.
name|getUndoableEdit
argument_list|(
name|this
argument_list|,
name|changesAdd
operator|.
name|get
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|undoManager
operator|.
name|addEdit
argument_list|(
name|undoRemove
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|changesAdd
operator|!=
literal|null
condition|)
block|{
name|undoManager
operator|.
name|addEdit
argument_list|(
name|UndoableChangeEntriesOfGroup
operator|.
name|getUndoableEdit
argument_list|(
name|this
argument_list|,
name|changesAdd
operator|.
name|get
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|isAllEntriesGroup ()
specifier|public
name|boolean
name|isAllEntriesGroup
parameter_list|()
block|{
return|return
name|getNode
argument_list|()
operator|.
name|getGroup
argument_list|()
operator|instanceof
name|AllEntriesGroup
return|;
block|}
DECL|method|addNewGroup (AbstractGroup newGroup, CountingUndoManager undoManager, GroupSelector groupSelector)
specifier|public
name|void
name|addNewGroup
parameter_list|(
name|AbstractGroup
name|newGroup
parameter_list|,
name|CountingUndoManager
name|undoManager
parameter_list|,
name|GroupSelector
name|groupSelector
parameter_list|)
block|{
name|GroupTreeNode
name|newNode
init|=
operator|new
name|GroupTreeNode
argument_list|(
name|newGroup
argument_list|)
decl_stmt|;
name|this
operator|.
name|getNode
argument_list|()
operator|.
name|addChild
argument_list|(
name|newNode
argument_list|)
expr_stmt|;
name|UndoableAddOrRemoveGroup
name|undo
init|=
operator|new
name|UndoableAddOrRemoveGroup
argument_list|(
name|groupSelector
argument_list|,
name|this
argument_list|,
operator|new
name|GroupTreeNodeViewModel
argument_list|(
name|newNode
argument_list|)
argument_list|,
name|UndoableAddOrRemoveGroup
operator|.
name|ADD_NODE
argument_list|)
decl_stmt|;
name|undoManager
operator|.
name|addEdit
argument_list|(
name|undo
argument_list|)
expr_stmt|;
block|}
DECL|method|moveUp ()
specifier|public
name|Optional
argument_list|<
name|MoveGroupChange
argument_list|>
name|moveUp
parameter_list|()
block|{
specifier|final
name|GroupTreeNode
name|parent
init|=
name|node
operator|.
name|getParent
argument_list|()
operator|.
name|get
argument_list|()
decl_stmt|;
comment|// TODO: Null!
specifier|final
name|int
name|index
init|=
name|parent
operator|.
name|getIndexOfChild
argument_list|(
name|getNode
argument_list|()
argument_list|)
operator|.
name|get
argument_list|()
decl_stmt|;
if|if
condition|(
name|index
operator|>
literal|0
condition|)
block|{
name|getNode
argument_list|()
operator|.
name|moveTo
argument_list|(
name|parent
argument_list|,
name|index
operator|-
literal|1
argument_list|)
expr_stmt|;
return|return
name|Optional
operator|.
name|of
argument_list|(
operator|new
name|MoveGroupChange
argument_list|(
name|parent
argument_list|,
name|index
argument_list|,
name|parent
argument_list|,
name|index
operator|-
literal|1
argument_list|)
argument_list|)
return|;
block|}
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
DECL|method|moveDown ()
specifier|public
name|Optional
argument_list|<
name|MoveGroupChange
argument_list|>
name|moveDown
parameter_list|()
block|{
specifier|final
name|GroupTreeNode
name|parent
init|=
name|node
operator|.
name|getParent
argument_list|()
operator|.
name|get
argument_list|()
decl_stmt|;
comment|// TODO: Null!
specifier|final
name|int
name|index
init|=
name|parent
operator|.
name|getIndexOfChild
argument_list|(
name|node
argument_list|)
operator|.
name|get
argument_list|()
decl_stmt|;
if|if
condition|(
name|index
operator|<
operator|(
name|parent
operator|.
name|getNumberOfChildren
argument_list|()
operator|-
literal|1
operator|)
condition|)
block|{
name|node
operator|.
name|moveTo
argument_list|(
name|parent
argument_list|,
name|index
operator|+
literal|1
argument_list|)
expr_stmt|;
return|return
name|Optional
operator|.
name|of
argument_list|(
operator|new
name|MoveGroupChange
argument_list|(
name|parent
argument_list|,
name|index
argument_list|,
name|parent
argument_list|,
name|index
operator|+
literal|1
argument_list|)
argument_list|)
return|;
block|}
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
DECL|method|moveLeft ()
specifier|public
name|Optional
argument_list|<
name|MoveGroupChange
argument_list|>
name|moveLeft
parameter_list|()
block|{
specifier|final
name|GroupTreeNode
name|parent
init|=
name|node
operator|.
name|getParent
argument_list|()
operator|.
name|get
argument_list|()
decl_stmt|;
comment|// TODO: Null!
specifier|final
name|Optional
argument_list|<
name|GroupTreeNode
argument_list|>
name|grandParent
init|=
name|parent
operator|.
name|getParent
argument_list|()
decl_stmt|;
specifier|final
name|int
name|index
init|=
name|node
operator|.
name|getPositionInParent
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|grandParent
operator|.
name|isPresent
argument_list|()
condition|)
block|{
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
specifier|final
name|int
name|indexOfParent
init|=
name|grandParent
operator|.
name|get
argument_list|()
operator|.
name|getIndexOfChild
argument_list|(
name|parent
argument_list|)
operator|.
name|get
argument_list|()
decl_stmt|;
name|node
operator|.
name|moveTo
argument_list|(
name|grandParent
operator|.
name|get
argument_list|()
argument_list|,
name|indexOfParent
operator|+
literal|1
argument_list|)
expr_stmt|;
return|return
name|Optional
operator|.
name|of
argument_list|(
operator|new
name|MoveGroupChange
argument_list|(
name|parent
argument_list|,
name|index
argument_list|,
name|grandParent
operator|.
name|get
argument_list|()
argument_list|,
name|indexOfParent
operator|+
literal|1
argument_list|)
argument_list|)
return|;
block|}
DECL|method|moveRight ()
specifier|public
name|Optional
argument_list|<
name|MoveGroupChange
argument_list|>
name|moveRight
parameter_list|()
block|{
specifier|final
name|GroupTreeNode
name|previousSibling
init|=
name|node
operator|.
name|getPreviousSibling
argument_list|()
operator|.
name|get
argument_list|()
decl_stmt|;
comment|// TODO: Null
specifier|final
name|GroupTreeNode
name|parent
init|=
name|node
operator|.
name|getParent
argument_list|()
operator|.
name|get
argument_list|()
decl_stmt|;
comment|// TODO: Null!
specifier|final
name|int
name|index
init|=
name|node
operator|.
name|getPositionInParent
argument_list|()
decl_stmt|;
if|if
condition|(
name|previousSibling
operator|==
literal|null
condition|)
block|{
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
name|node
operator|.
name|moveTo
argument_list|(
name|previousSibling
argument_list|)
expr_stmt|;
return|return
name|Optional
operator|.
name|of
argument_list|(
operator|new
name|MoveGroupChange
argument_list|(
name|parent
argument_list|,
name|index
argument_list|,
name|previousSibling
argument_list|,
name|previousSibling
operator|.
name|getNumberOfChildren
argument_list|()
argument_list|)
argument_list|)
return|;
block|}
comment|/**      * Adds the given entries to this node's group.      */
DECL|method|addEntriesToGroup (List<BibEntry> entries)
specifier|public
name|Optional
argument_list|<
name|EntriesGroupChange
argument_list|>
name|addEntriesToGroup
parameter_list|(
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
parameter_list|)
block|{
if|if
condition|(
name|node
operator|.
name|getGroup
argument_list|()
operator|.
name|supportsAdd
argument_list|()
condition|)
block|{
return|return
name|node
operator|.
name|getGroup
argument_list|()
operator|.
name|add
argument_list|(
name|entries
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
block|}
comment|/**      * Removes the given entries from this node's group.      */
DECL|method|removeEntriesFromGroup (List<BibEntry> entries)
specifier|public
name|Optional
argument_list|<
name|EntriesGroupChange
argument_list|>
name|removeEntriesFromGroup
parameter_list|(
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
parameter_list|)
block|{
if|if
condition|(
name|node
operator|.
name|getGroup
argument_list|()
operator|.
name|supportsRemove
argument_list|()
condition|)
block|{
return|return
name|node
operator|.
name|getGroup
argument_list|()
operator|.
name|remove
argument_list|(
name|entries
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
block|}
DECL|method|subscribeToDescendantChanged (Consumer<GroupTreeNodeViewModel> subscriber)
specifier|public
name|void
name|subscribeToDescendantChanged
parameter_list|(
name|Consumer
argument_list|<
name|GroupTreeNodeViewModel
argument_list|>
name|subscriber
parameter_list|)
block|{
name|getNode
argument_list|()
operator|.
name|subscribeToDescendantChanged
argument_list|(
name|node
lambda|->
name|subscriber
operator|.
name|accept
argument_list|(
operator|new
name|GroupTreeNodeViewModel
argument_list|(
name|node
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

