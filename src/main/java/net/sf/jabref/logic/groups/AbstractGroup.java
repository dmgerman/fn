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
comment|/**      * Character used for quoting in the string representation.      */
DECL|field|QUOTE_CHAR
specifier|public
specifier|static
specifier|final
name|char
name|QUOTE_CHAR
init|=
literal|'\\'
decl_stmt|;
comment|/**      * For separating units (e.g. name, which every group has) in the string      * representation      */
DECL|field|SEPARATOR
specifier|public
specifier|static
specifier|final
name|String
name|SEPARATOR
init|=
literal|";"
decl_stmt|;
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
name|setHierarchicalContext
argument_list|(
name|context
argument_list|)
expr_stmt|;
block|}
comment|/**      * Re-create a group instance from a textual representation.      *      * @param s The result from the group's toString() method.      * @return New instance of the encoded group.      * @throws ParseException If an error occurred and a group could not be created,      *                        e.g. due to a malformed regular expression.      */
DECL|method|fromString (String s)
specifier|public
specifier|static
name|AbstractGroup
name|fromString
parameter_list|(
name|String
name|s
parameter_list|)
throws|throws
name|ParseException
block|{
if|if
condition|(
name|s
operator|.
name|startsWith
argument_list|(
name|KeywordGroup
operator|.
name|ID
argument_list|)
condition|)
block|{
return|return
name|KeywordGroup
operator|.
name|fromString
argument_list|(
name|s
argument_list|)
return|;
block|}
if|if
condition|(
name|s
operator|.
name|startsWith
argument_list|(
name|AllEntriesGroup
operator|.
name|ID
argument_list|)
condition|)
block|{
return|return
name|AllEntriesGroup
operator|.
name|fromString
argument_list|(
name|s
argument_list|)
return|;
block|}
if|if
condition|(
name|s
operator|.
name|startsWith
argument_list|(
name|SearchGroup
operator|.
name|ID
argument_list|)
condition|)
block|{
return|return
name|SearchGroup
operator|.
name|fromString
argument_list|(
name|s
argument_list|)
return|;
block|}
if|if
condition|(
name|s
operator|.
name|startsWith
argument_list|(
name|ExplicitGroup
operator|.
name|ID
argument_list|)
condition|)
block|{
return|return
name|ExplicitGroup
operator|.
name|fromString
argument_list|(
name|s
argument_list|)
return|;
block|}
return|return
literal|null
return|;
comment|// unknown group
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
comment|/**      * Sets the groups's hierarchical context. If context is not a valid      * value, the call is ignored.      */
DECL|method|setHierarchicalContext (GroupHierarchyType context)
specifier|public
name|void
name|setHierarchicalContext
parameter_list|(
name|GroupHierarchyType
name|context
parameter_list|)
block|{
if|if
condition|(
name|context
operator|==
literal|null
condition|)
block|{
return|return;
block|}
name|this
operator|.
name|context
operator|=
name|context
expr_stmt|;
block|}
comment|/**      * Returns a lengthy textual description of this instance (for      * the groups editor). The text is formatted in HTML.      */
DECL|method|getDescription ()
specifier|public
specifier|abstract
name|String
name|getDescription
parameter_list|()
function_decl|;
comment|/**      * @return A deep copy of this object.      */
DECL|method|deepCopy ()
specifier|public
specifier|abstract
name|AbstractGroup
name|deepCopy
parameter_list|()
function_decl|;
comment|/**      * Returns a short description of the group in HTML (for a tooltip).      */
DECL|method|getShortDescription ()
specifier|public
specifier|abstract
name|String
name|getShortDescription
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

