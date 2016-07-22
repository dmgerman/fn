begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.logic.groups
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
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
name|ArrayList
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
name|stream
operator|.
name|Collectors
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
name|importer
operator|.
name|fileformat
operator|.
name|ParseException
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
name|search
operator|.
name|SearchMatcher
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
name|search
operator|.
name|matchers
operator|.
name|MatcherSet
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
name|search
operator|.
name|matchers
operator|.
name|MatcherSets
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
name|database
operator|.
name|BibDatabase
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
name|preferences
operator|.
name|JabRefPreferences
import|;
end_import

begin_comment
comment|/**  * A node in the groups tree that holds exactly one AbstractGroup.  */
end_comment

begin_class
DECL|class|GroupTreeNode
specifier|public
class|class
name|GroupTreeNode
extends|extends
name|TreeNode
argument_list|<
name|GroupTreeNode
argument_list|>
block|{
DECL|field|group
specifier|private
name|AbstractGroup
name|group
decl_stmt|;
comment|/**      * Creates this node and associates the specified group with it.      *      * @param group the group underlying this node      */
DECL|method|GroupTreeNode (AbstractGroup group)
specifier|public
name|GroupTreeNode
parameter_list|(
name|AbstractGroup
name|group
parameter_list|)
block|{
name|super
argument_list|(
name|GroupTreeNode
operator|.
name|class
argument_list|)
expr_stmt|;
name|setGroup
argument_list|(
name|group
argument_list|)
expr_stmt|;
block|}
DECL|method|fromGroup (AbstractGroup group)
specifier|public
specifier|static
name|GroupTreeNode
name|fromGroup
parameter_list|(
name|AbstractGroup
name|group
parameter_list|)
block|{
return|return
operator|new
name|GroupTreeNode
argument_list|(
name|group
argument_list|)
return|;
block|}
comment|/**      * Returns the group underlying this node.      *      * @return the group associated with this node      */
DECL|method|getGroup ()
specifier|public
name|AbstractGroup
name|getGroup
parameter_list|()
block|{
return|return
name|group
return|;
block|}
comment|/**      * Associates the specified group with this node.      *      * @param newGroup the new group (has to be non-null)      */
annotation|@
name|Deprecated
comment|// use other overload
DECL|method|setGroup (AbstractGroup newGroup)
specifier|public
name|void
name|setGroup
parameter_list|(
name|AbstractGroup
name|newGroup
parameter_list|)
block|{
name|this
operator|.
name|group
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|newGroup
argument_list|)
expr_stmt|;
block|}
comment|/**      * Associates the specified group with this node while also providing the possibility to modify previous matched      * entries so that they are now matched by the new group.      *      * @param newGroup the new group (has to be non-null)      * @param shouldKeepPreviousAssignments specifies whether previous matched entries should be carried over      * @param entriesInDatabase list of entries in the database      */
DECL|method|setGroup (AbstractGroup newGroup, boolean shouldKeepPreviousAssignments, List<BibEntry> entriesInDatabase)
specifier|public
name|Optional
argument_list|<
name|EntriesGroupChange
argument_list|>
name|setGroup
parameter_list|(
name|AbstractGroup
name|newGroup
parameter_list|,
name|boolean
name|shouldKeepPreviousAssignments
parameter_list|,
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entriesInDatabase
parameter_list|)
block|{
name|AbstractGroup
name|oldGroup
init|=
name|getGroup
argument_list|()
decl_stmt|;
name|setGroup
argument_list|(
name|newGroup
argument_list|)
expr_stmt|;
comment|// Keep assignments from previous group
if|if
condition|(
name|shouldKeepPreviousAssignments
operator|&&
name|newGroup
operator|.
name|supportsAdd
argument_list|()
condition|)
block|{
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entriesMatchedByOldGroup
init|=
name|entriesInDatabase
operator|.
name|stream
argument_list|()
operator|.
name|filter
argument_list|(
name|oldGroup
operator|::
name|isMatch
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|oldGroup
operator|instanceof
name|ExplicitGroup
operator|)
operator|&&
operator|(
name|newGroup
operator|instanceof
name|ExplicitGroup
operator|)
condition|)
block|{
comment|// Rename of explicit group, so remove old group assignment
name|oldGroup
operator|.
name|remove
argument_list|(
name|entriesMatchedByOldGroup
argument_list|)
expr_stmt|;
block|}
return|return
name|newGroup
operator|.
name|add
argument_list|(
name|entriesMatchedByOldGroup
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
comment|/**      * Returns a textual representation of this node and its children. This      * representation contains both the tree structure and the textual      * representations of the group associated with each node.      * Every node is one entry in the list of strings.      *      * @return a representation of the tree based at this node as a list of strings      */
DECL|method|getTreeAsString ()
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getTreeAsString
parameter_list|()
block|{
name|List
argument_list|<
name|String
argument_list|>
name|representation
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
comment|// Append myself
name|representation
operator|.
name|add
argument_list|(
name|this
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
comment|// Append children
for|for
control|(
name|GroupTreeNode
name|child
range|:
name|getChildren
argument_list|()
control|)
block|{
name|representation
operator|.
name|addAll
argument_list|(
name|child
operator|.
name|getTreeAsString
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|representation
return|;
block|}
comment|/**      * Update all groups, if necessary, to handle the situation where the group      * tree is applied to a different BibDatabase than it was created for. This      * is for instance used when updating the group tree due to an external change.      *      * @param db The database to refresh for.      * @deprecated This method shouldn't be necessary anymore once explicit group memberships are saved directly in the entry.      * TODO: Remove this method.      */
annotation|@
name|Deprecated
DECL|method|refreshGroupsForNewDatabase (BibDatabase db)
specifier|public
name|void
name|refreshGroupsForNewDatabase
parameter_list|(
name|BibDatabase
name|db
parameter_list|)
block|{
for|for
control|(
name|GroupTreeNode
name|node
range|:
name|getChildren
argument_list|()
control|)
block|{
name|node
operator|.
name|group
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
comment|/**      * Creates a SearchRule that finds elements contained in this nodes group,      * or the union of those elements in its own group and its      * children's groups (recursively), or the intersection of the elements in      * its own group and its parent's group (depending on the hierarchical settings stored in the involved groups)      *      * @return a SearchRule that finds the desired elements      */
DECL|method|getSearchRule ()
specifier|public
name|SearchMatcher
name|getSearchRule
parameter_list|()
block|{
return|return
name|getSearchRule
argument_list|(
name|group
operator|.
name|getHierarchicalContext
argument_list|()
argument_list|)
return|;
block|}
DECL|method|getSearchRule (GroupHierarchyType originalContext)
specifier|private
name|SearchMatcher
name|getSearchRule
parameter_list|(
name|GroupHierarchyType
name|originalContext
parameter_list|)
block|{
specifier|final
name|GroupHierarchyType
name|context
init|=
name|group
operator|.
name|getHierarchicalContext
argument_list|()
decl_stmt|;
if|if
condition|(
name|context
operator|==
name|GroupHierarchyType
operator|.
name|INDEPENDENT
condition|)
block|{
return|return
name|group
return|;
block|}
name|MatcherSet
name|searchRule
init|=
name|MatcherSets
operator|.
name|build
argument_list|(
name|context
operator|==
name|GroupHierarchyType
operator|.
name|REFINING
condition|?
name|MatcherSets
operator|.
name|MatcherType
operator|.
name|AND
else|:
name|MatcherSets
operator|.
name|MatcherType
operator|.
name|OR
argument_list|)
decl_stmt|;
name|searchRule
operator|.
name|addRule
argument_list|(
name|group
argument_list|)
expr_stmt|;
if|if
condition|(
operator|(
name|context
operator|==
name|GroupHierarchyType
operator|.
name|INCLUDING
operator|)
operator|&&
operator|(
name|originalContext
operator|!=
name|GroupHierarchyType
operator|.
name|REFINING
operator|)
condition|)
block|{
for|for
control|(
name|GroupTreeNode
name|child
range|:
name|getChildren
argument_list|()
control|)
block|{
name|searchRule
operator|.
name|addRule
argument_list|(
name|child
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
name|GroupHierarchyType
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
name|GroupHierarchyType
operator|.
name|INCLUDING
operator|)
condition|)
block|{
name|searchRule
operator|.
name|addRule
argument_list|(
name|getParent
argument_list|()
operator|.
name|get
argument_list|()
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
DECL|method|equals (Object o)
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
if|if
condition|(
name|this
operator|==
name|o
condition|)
block|{
return|return
literal|true
return|;
block|}
if|if
condition|(
operator|(
name|o
operator|==
literal|null
operator|)
operator|||
operator|(
name|getClass
argument_list|()
operator|!=
name|o
operator|.
name|getClass
argument_list|()
operator|)
condition|)
block|{
return|return
literal|false
return|;
block|}
name|GroupTreeNode
name|that
init|=
operator|(
name|GroupTreeNode
operator|)
name|o
decl_stmt|;
return|return
name|Objects
operator|.
name|equals
argument_list|(
name|group
argument_list|,
name|that
operator|.
name|group
argument_list|)
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
name|Objects
operator|.
name|hash
argument_list|(
name|group
argument_list|)
return|;
block|}
DECL|method|getContainingGroups (List<BibEntry> entries, boolean requireAll)
specifier|public
name|List
argument_list|<
name|GroupTreeNode
argument_list|>
name|getContainingGroups
parameter_list|(
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
parameter_list|,
name|boolean
name|requireAll
parameter_list|)
block|{
name|List
argument_list|<
name|GroupTreeNode
argument_list|>
name|groups
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
comment|// Add myself if I contain the entries
if|if
condition|(
name|requireAll
condition|)
block|{
if|if
condition|(
name|this
operator|.
name|group
operator|.
name|containsAll
argument_list|(
name|entries
argument_list|)
condition|)
block|{
name|groups
operator|.
name|add
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
if|if
condition|(
name|this
operator|.
name|group
operator|.
name|containsAny
argument_list|(
name|entries
argument_list|)
condition|)
block|{
name|groups
operator|.
name|add
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
block|}
comment|// Traverse children
for|for
control|(
name|GroupTreeNode
name|child
range|:
name|getChildren
argument_list|()
control|)
block|{
name|groups
operator|.
name|addAll
argument_list|(
name|child
operator|.
name|getContainingGroups
argument_list|(
name|entries
argument_list|,
name|requireAll
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|groups
return|;
block|}
DECL|method|getMatchingGroups (List<BibEntry> entries)
specifier|public
name|List
argument_list|<
name|GroupTreeNode
argument_list|>
name|getMatchingGroups
parameter_list|(
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
parameter_list|)
block|{
name|List
argument_list|<
name|GroupTreeNode
argument_list|>
name|groups
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
comment|// Add myself if I contain the entries
name|SearchMatcher
name|matcher
init|=
name|getSearchRule
argument_list|()
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
name|matcher
operator|.
name|isMatch
argument_list|(
name|entry
argument_list|)
condition|)
block|{
name|groups
operator|.
name|add
argument_list|(
name|this
argument_list|)
expr_stmt|;
break|break;
block|}
block|}
comment|// Traverse children
for|for
control|(
name|GroupTreeNode
name|child
range|:
name|getChildren
argument_list|()
control|)
block|{
name|groups
operator|.
name|addAll
argument_list|(
name|child
operator|.
name|getMatchingGroups
argument_list|(
name|entries
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|groups
return|;
block|}
DECL|method|supportsAddingEntries ()
specifier|public
name|boolean
name|supportsAddingEntries
parameter_list|()
block|{
return|return
name|group
operator|.
name|supportsAdd
argument_list|()
return|;
block|}
DECL|method|getName ()
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|group
operator|.
name|getName
argument_list|()
return|;
block|}
DECL|method|addSubgroup (AbstractGroup group)
specifier|public
name|GroupTreeNode
name|addSubgroup
parameter_list|(
name|AbstractGroup
name|group
parameter_list|)
block|{
name|GroupTreeNode
name|child
init|=
name|GroupTreeNode
operator|.
name|fromGroup
argument_list|(
name|group
argument_list|)
decl_stmt|;
name|addChild
argument_list|(
name|child
argument_list|)
expr_stmt|;
return|return
name|child
return|;
block|}
annotation|@
name|Override
DECL|method|toString ()
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|String
operator|.
name|valueOf
argument_list|(
name|this
operator|.
name|getLevel
argument_list|()
argument_list|)
operator|+
literal|' '
operator|+
name|group
operator|.
name|toString
argument_list|()
return|;
block|}
annotation|@
name|Override
DECL|method|copyNode ()
specifier|public
name|GroupTreeNode
name|copyNode
parameter_list|()
block|{
return|return
name|GroupTreeNode
operator|.
name|fromGroup
argument_list|(
name|group
argument_list|)
return|;
block|}
DECL|method|parse (List<String> orderedData, JabRefPreferences jabRefPreferences)
specifier|public
specifier|static
name|GroupTreeNode
name|parse
parameter_list|(
name|List
argument_list|<
name|String
argument_list|>
name|orderedData
parameter_list|,
name|JabRefPreferences
name|jabRefPreferences
parameter_list|)
throws|throws
name|ParseException
block|{
return|return
name|GroupsParser
operator|.
name|importGroups
argument_list|(
name|orderedData
argument_list|,
name|jabRefPreferences
argument_list|)
return|;
block|}
comment|/**      * Determines the number of entries in the specified list which are matched by this group.      * @param entries list of entries to be searched      * @return number of hits      */
DECL|method|numberOfHits (List<BibEntry> entries)
specifier|public
name|int
name|numberOfHits
parameter_list|(
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
parameter_list|)
block|{
name|int
name|hits
init|=
literal|0
decl_stmt|;
name|SearchMatcher
name|matcher
init|=
name|getSearchRule
argument_list|()
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
name|matcher
operator|.
name|isMatch
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
return|return
name|hits
return|;
block|}
block|}
end_class

end_unit

