begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.model.groups
package|package
name|org
operator|.
name|jabref
operator|.
name|model
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
name|Collections
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
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|FieldChange
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|TreeNode
import|;
end_import

begin_import
import|import
name|org
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
name|org
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
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|search
operator|.
name|SearchMatcher
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
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
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|search
operator|.
name|matchers
operator|.
name|MatcherSets
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
DECL|field|PATH_DELEMITER
specifier|private
specifier|static
specifier|final
name|String
name|PATH_DELEMITER
init|=
literal|"> "
decl_stmt|;
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
name|this
operator|.
name|group
operator|=
name|Objects
operator|.
name|requireNonNull
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
comment|/**      * Associates the specified group with this node.      *      * @param newGroup the new group (has to be non-null)      * @deprecated use {@link #setGroup(AbstractGroup, boolean, boolean, List)}} instead      */
annotation|@
name|Deprecated
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
comment|/**      * Associates the specified group with this node while also providing the possibility to modify previous matched      * entries so that they are now matched by the new group.      *      * @param newGroup the new group (has to be non-null)      * @param shouldKeepPreviousAssignments specifies whether previous matched entries should be added to the new group      * @param shouldRemovePreviousAssignments specifies whether previous matched entries should be removed from the old group      * @param entriesInDatabase list of entries in the database      */
DECL|method|setGroup (AbstractGroup newGroup, boolean shouldKeepPreviousAssignments, boolean shouldRemovePreviousAssignments, List<BibEntry> entriesInDatabase)
specifier|public
name|List
argument_list|<
name|FieldChange
argument_list|>
name|setGroup
parameter_list|(
name|AbstractGroup
name|newGroup
parameter_list|,
name|boolean
name|shouldKeepPreviousAssignments
parameter_list|,
name|boolean
name|shouldRemovePreviousAssignments
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
name|List
argument_list|<
name|FieldChange
argument_list|>
name|changes
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|boolean
name|shouldRemove
init|=
name|shouldRemovePreviousAssignments
operator|&&
operator|(
name|oldGroup
operator|instanceof
name|GroupEntryChanger
operator|)
decl_stmt|;
name|boolean
name|shouldAdd
init|=
name|shouldKeepPreviousAssignments
operator|&&
operator|(
name|newGroup
operator|instanceof
name|GroupEntryChanger
operator|)
decl_stmt|;
if|if
condition|(
name|shouldAdd
operator|||
name|shouldRemove
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
name|shouldRemove
condition|)
block|{
name|GroupEntryChanger
name|entryChanger
init|=
operator|(
name|GroupEntryChanger
operator|)
name|oldGroup
decl_stmt|;
name|changes
operator|.
name|addAll
argument_list|(
name|entryChanger
operator|.
name|remove
argument_list|(
name|entriesMatchedByOldGroup
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|shouldAdd
condition|)
block|{
name|GroupEntryChanger
name|entryChanger
init|=
operator|(
name|GroupEntryChanger
operator|)
name|newGroup
decl_stmt|;
name|changes
operator|.
name|addAll
argument_list|(
name|entryChanger
operator|.
name|add
argument_list|(
name|entriesMatchedByOldGroup
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|changes
return|;
block|}
comment|/**      * Creates a {@link SearchMatcher} that matches entries of this group and that takes the hierarchical information      * into account. I.e., it finds elements contained in this nodes group,      * or the union of those elements in its own group and its      * children's groups (recursively), or the intersection of the elements in      * its own group and its parent's group (depending on the hierarchical settings stored in the involved groups)      */
DECL|method|getSearchMatcher ()
specifier|public
name|SearchMatcher
name|getSearchMatcher
parameter_list|()
block|{
return|return
name|getSearchMatcher
argument_list|(
name|group
operator|.
name|getHierarchicalContext
argument_list|()
argument_list|)
return|;
block|}
DECL|method|getSearchMatcher (GroupHierarchyType originalContext)
specifier|private
name|SearchMatcher
name|getSearchMatcher
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
name|getSearchMatcher
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
comment|//noinspection OptionalGetWithoutIsPresent
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
name|getSearchMatcher
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
operator|&&
name|Objects
operator|.
name|equals
argument_list|(
name|getChildren
argument_list|()
argument_list|,
name|that
operator|.
name|getChildren
argument_list|()
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
name|getSearchMatcher
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
DECL|method|getEntriesInGroup (List<BibEntry> entries)
specifier|public
name|List
argument_list|<
name|BibEntry
argument_list|>
name|getEntriesInGroup
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
name|BibEntry
argument_list|>
name|result
init|=
operator|new
name|ArrayList
argument_list|<>
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
name|this
operator|.
name|group
operator|.
name|contains
argument_list|(
name|entry
argument_list|)
condition|)
block|{
name|result
operator|.
name|add
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|result
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
DECL|method|addSubgroup (AbstractGroup subgroup)
specifier|public
name|GroupTreeNode
name|addSubgroup
parameter_list|(
name|AbstractGroup
name|subgroup
parameter_list|)
block|{
name|GroupTreeNode
name|child
init|=
name|GroupTreeNode
operator|.
name|fromGroup
argument_list|(
name|subgroup
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
comment|/**      * Determines the number of entries in the specified list which are matched by this group.      * @param entries list of entries to be searched      * @return number of hits      */
DECL|method|calculateNumberOfMatches (List<BibEntry> entries)
specifier|public
name|int
name|calculateNumberOfMatches
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
name|getSearchMatcher
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
comment|/**      * Determines the number of entries in the specified database which are matched by this group.      * @param database database to be searched      * @return number of hits      */
DECL|method|calculateNumberOfMatches (BibDatabase database)
specifier|public
name|int
name|calculateNumberOfMatches
parameter_list|(
name|BibDatabase
name|database
parameter_list|)
block|{
return|return
name|calculateNumberOfMatches
argument_list|(
name|database
operator|.
name|getEntries
argument_list|()
argument_list|)
return|;
block|}
comment|/**      * Returns whether this group matches the specified {@link BibEntry} while taking the hierarchical information      * into account.      */
DECL|method|matches (BibEntry entry)
specifier|public
name|boolean
name|matches
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
return|return
name|getSearchMatcher
argument_list|()
operator|.
name|isMatch
argument_list|(
name|entry
argument_list|)
return|;
block|}
comment|/**      * Get the path from the root of the tree as a string (every group name is separated by {@link #PATH_DELEMITER}.      *      * The name of the root is not included.      */
DECL|method|getPath ()
specifier|public
name|String
name|getPath
parameter_list|()
block|{
return|return
name|getPathFromRoot
argument_list|()
operator|.
name|stream
argument_list|()
operator|.
name|skip
argument_list|(
literal|1
argument_list|)
comment|// Skip root
operator|.
name|map
argument_list|(
name|GroupTreeNode
operator|::
name|getName
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|joining
argument_list|(
name|PATH_DELEMITER
argument_list|)
argument_list|)
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
literal|"GroupTreeNode{"
operator|+
literal|"group="
operator|+
name|group
operator|+
literal|'}'
return|;
block|}
comment|/**      * Finds a children using the given path.      * Each group name should be separated by {@link #PATH_DELEMITER}.      *      * The path should be generated using {@link #getPath()}.      */
DECL|method|getChildByPath (String pathToSource)
specifier|public
name|Optional
argument_list|<
name|GroupTreeNode
argument_list|>
name|getChildByPath
parameter_list|(
name|String
name|pathToSource
parameter_list|)
block|{
name|GroupTreeNode
name|present
init|=
name|this
decl_stmt|;
for|for
control|(
name|String
name|groupName
range|:
name|pathToSource
operator|.
name|split
argument_list|(
name|PATH_DELEMITER
argument_list|)
control|)
block|{
name|Optional
argument_list|<
name|GroupTreeNode
argument_list|>
name|childWithName
init|=
name|present
operator|.
name|getChildren
argument_list|()
operator|.
name|stream
argument_list|()
operator|.
name|filter
argument_list|(
name|group
lambda|->
name|Objects
operator|.
name|equals
argument_list|(
name|group
operator|.
name|getName
argument_list|()
argument_list|,
name|groupName
argument_list|)
argument_list|)
operator|.
name|findFirst
argument_list|()
decl_stmt|;
if|if
condition|(
name|childWithName
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|present
operator|=
name|childWithName
operator|.
name|get
argument_list|()
expr_stmt|;
block|}
else|else
block|{
comment|// No child with that name found -> path seems to be invalid
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
block|}
return|return
name|Optional
operator|.
name|of
argument_list|(
name|present
argument_list|)
return|;
block|}
comment|/**      * Adds the specified entries to this group.      * If the group does not support explicit adding of entries (i.e., does not implement {@link GroupEntryChanger}),      * then no action is performed.      */
DECL|method|addEntriesToGroup (List<BibEntry> entries)
specifier|public
name|List
argument_list|<
name|FieldChange
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
name|getGroup
argument_list|()
operator|instanceof
name|GroupEntryChanger
condition|)
block|{
return|return
operator|(
operator|(
name|GroupEntryChanger
operator|)
name|getGroup
argument_list|()
operator|)
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
name|Collections
operator|.
name|emptyList
argument_list|()
return|;
block|}
block|}
comment|/**      * Removes the given entries from this group. If the group does not support the explicit removal of entries (i.e.,      * does not implement {@link GroupEntryChanger}), then no action is performed.      */
DECL|method|removeEntriesFromGroup (List<BibEntry> entries)
specifier|public
name|List
argument_list|<
name|FieldChange
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
name|getGroup
argument_list|()
operator|instanceof
name|GroupEntryChanger
condition|)
block|{
return|return
operator|(
operator|(
name|GroupEntryChanger
operator|)
name|getGroup
argument_list|()
operator|)
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
name|Collections
operator|.
name|emptyList
argument_list|()
return|;
block|}
block|}
comment|/**      * Returns true if the underlying groups of both {@link GroupTreeNode}s is the same.      */
DECL|method|isSameGroupAs (GroupTreeNode other)
specifier|public
name|boolean
name|isSameGroupAs
parameter_list|(
name|GroupTreeNode
name|other
parameter_list|)
block|{
return|return
name|Objects
operator|.
name|equals
argument_list|(
name|group
argument_list|,
name|other
operator|.
name|group
argument_list|)
return|;
block|}
block|}
end_class

end_unit

