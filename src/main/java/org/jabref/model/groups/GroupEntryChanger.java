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
name|Collection
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
name|entry
operator|.
name|BibEntry
import|;
end_import

begin_comment
comment|/**  * Some groups can change entries so that they match (or no longer match) the group.  * This functionality is encoded in this interface.  */
end_comment

begin_interface
DECL|interface|GroupEntryChanger
specifier|public
interface|interface
name|GroupEntryChanger
block|{
comment|/**      * Adds the specified entries to this group.      *      * @return If this group or one or more entries was/were modified as a      * result of this operation, a list of changes is returned.      */
DECL|method|add (Collection<BibEntry> entriesToAdd)
name|List
argument_list|<
name|FieldChange
argument_list|>
name|add
parameter_list|(
name|Collection
argument_list|<
name|BibEntry
argument_list|>
name|entriesToAdd
parameter_list|)
function_decl|;
DECL|method|add (BibEntry entryToAdd)
specifier|default
name|List
argument_list|<
name|FieldChange
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
comment|/**      * Removes the specified entries from this group.      *      * @return If this group or one or more entries was/were modified as a      * result of this operation, a list of changes is returned.      */
DECL|method|remove (List<BibEntry> entriesToRemove)
name|List
argument_list|<
name|FieldChange
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
DECL|method|remove (BibEntry entryToAdd)
specifier|default
name|List
argument_list|<
name|FieldChange
argument_list|>
name|remove
parameter_list|(
name|BibEntry
name|entryToAdd
parameter_list|)
block|{
return|return
name|remove
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
block|}
end_interface

end_unit

