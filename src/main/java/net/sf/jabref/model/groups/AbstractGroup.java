begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.model.groups
package|package
name|net
operator|.
name|sf
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
name|model
operator|.
name|search
operator|.
name|SearchMatcher
import|;
end_import

begin_comment
comment|/**  * A group of BibtexEntries.  */
end_comment

begin_class
DECL|class|AbstractGroup
specifier|public
specifier|abstract
class|class
name|AbstractGroup
implements|implements
name|SearchMatcher
block|{
comment|/**      * The group's name (every type of group has one).      */
DECL|field|name
specifier|private
name|String
name|name
decl_stmt|;
comment|/**      * The hierarchical context of the group (INDEPENDENT, REFINING, or      * INCLUDING). Defaults to INDEPENDENT, which will be used if and      * only if the context specified in the constructor is invalid.      */
DECL|field|context
specifier|private
name|GroupHierarchyType
name|context
init|=
name|GroupHierarchyType
operator|.
name|INDEPENDENT
decl_stmt|;
DECL|method|AbstractGroup (String name, GroupHierarchyType context)
specifier|protected
name|AbstractGroup
parameter_list|(
name|String
name|name
parameter_list|,
name|GroupHierarchyType
name|context
parameter_list|)
block|{
name|this
operator|.
name|name
operator|=
name|name
expr_stmt|;
name|this
operator|.
name|context
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|context
argument_list|)
expr_stmt|;
block|}
DECL|method|getContext ()
specifier|public
name|GroupHierarchyType
name|getContext
parameter_list|()
block|{
return|return
name|context
return|;
block|}
DECL|method|getTypeId ()
specifier|public
specifier|abstract
name|String
name|getTypeId
parameter_list|()
function_decl|;
comment|/**      * Returns this group's name, e.g. for display in a list/tree.      */
DECL|method|getName ()
specifier|public
specifier|final
name|String
name|getName
parameter_list|()
block|{
return|return
name|name
return|;
block|}
comment|/**      * Sets the group's name.      */
DECL|method|setName (String name)
specifier|public
specifier|final
name|void
name|setName
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|this
operator|.
name|name
operator|=
name|name
expr_stmt|;
block|}
comment|/**      * @return true if this type of group supports the explicit adding of      * entries.      */
DECL|method|supportsAdd ()
specifier|public
specifier|abstract
name|boolean
name|supportsAdd
parameter_list|()
function_decl|;
comment|/**      * @return true if this type of group supports the explicit removal of      * entries.      */
DECL|method|supportsRemove ()
specifier|public
specifier|abstract
name|boolean
name|supportsRemove
parameter_list|()
function_decl|;
comment|/**      * Adds the specified entries to this group.      *      * @return If this group or one or more entries was/were modified as a      * result of this operation, an object is returned that allows to      * undo this change. null is returned otherwise.      */
DECL|method|add (List<BibEntry> entriesToAdd)
specifier|public
specifier|abstract
name|Optional
argument_list|<
name|EntriesGroupChange
argument_list|>
name|add
parameter_list|(
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entriesToAdd
parameter_list|)
function_decl|;
DECL|method|add (BibEntry entryToAdd)
specifier|public
name|Optional
argument_list|<
name|EntriesGroupChange
argument_list|>
name|add
parameter_list|(
name|BibEntry
name|entryToAdd
parameter_list|)
block|{
return|return
name|add
argument_list|(
name|Collections
operator|.
name|singletonList
argument_list|(
name|entryToAdd
argument_list|)
argument_list|)
return|;
block|}
comment|/**      * Removes the specified entries from this group.      *      * @return If this group or one or more entries was/were modified as a      * result of this operation, an object is returned that allows to      * undo this change. null is returned otherwise.      */
DECL|method|remove (List<BibEntry> entriesToRemove)
specifier|public
specifier|abstract
name|Optional
argument_list|<
name|EntriesGroupChange
argument_list|>
name|remove
parameter_list|(
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entriesToRemove
parameter_list|)
function_decl|;
comment|/**      * @return true if this group contains the specified entry, false otherwise.      */
DECL|method|contains (BibEntry entry)
specifier|public
specifier|abstract
name|boolean
name|contains
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
function_decl|;
annotation|@
name|Override
DECL|method|isMatch (BibEntry entry)
specifier|public
name|boolean
name|isMatch
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
return|return
name|contains
argument_list|(
name|entry
argument_list|)
return|;
block|}
comment|/**      * @return true if this group contains any of the specified entries, false      * otherwise.      */
DECL|method|containsAny (List<BibEntry> entries)
specifier|public
name|boolean
name|containsAny
parameter_list|(
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
parameter_list|)
block|{
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
name|contains
argument_list|(
name|entry
argument_list|)
condition|)
block|{
return|return
literal|true
return|;
block|}
block|}
return|return
literal|false
return|;
block|}
comment|/**      * @return true if this group contains all of the specified entries, false      * otherwise.      */
DECL|method|containsAll (List<BibEntry> entries)
specifier|public
name|boolean
name|containsAll
parameter_list|(
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
parameter_list|)
block|{
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
operator|!
name|contains
argument_list|(
name|entry
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
comment|/**      * Returns true if this group is dynamic, i.e. uses a search definition or      * equiv. that might match new entries, or false if this group contains a      * fixed set of entries and thus will never match a new entry that was not      * explicitly added to it.      */
DECL|method|isDynamic ()
specifier|public
specifier|abstract
name|boolean
name|isDynamic
parameter_list|()
function_decl|;
comment|/**      * Returns the group's hierarchical context.      */
DECL|method|getHierarchicalContext ()
specifier|public
name|GroupHierarchyType
name|getHierarchicalContext
parameter_list|()
block|{
return|return
name|context
return|;
block|}
comment|/**      * @return A deep copy of this object.      */
DECL|method|deepCopy ()
specifier|public
specifier|abstract
name|AbstractGroup
name|deepCopy
parameter_list|()
function_decl|;
comment|// by general AbstractGroup contract, toString() must return
comment|// something from which this object can be reconstructed
comment|// using fromString(String).
comment|// by general AbstractGroup contract, equals() must be implemented
comment|/**      * Update the group, if necessary, to handle the situation where the group      * is applied to a different BibDatabase than it was created for. This      * is for instance used when updating the group tree due to an external change.      *      * @param db The database to refresh for.      */
DECL|method|refreshForNewDatabase (BibDatabase db)
specifier|public
name|void
name|refreshForNewDatabase
parameter_list|(
name|BibDatabase
name|db
parameter_list|)
block|{
comment|// Default is to do nothing. Group types that are affected by a change
comment|// of database must override this method.
block|}
block|}
end_class

end_unit

