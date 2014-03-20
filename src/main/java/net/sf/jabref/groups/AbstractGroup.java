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
name|util
operator|.
name|Map
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
name|SearchRule
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
block|{
comment|/** The group's name (every type of group has one). */
DECL|field|m_name
specifier|protected
name|String
name|m_name
decl_stmt|;
comment|/** 	 * The hierarchical context of the group (INDEPENDENT, REFINING, or 	 * INCLUDING). Defaults to INDEPENDENT, which will be used if and 	 * only if the context specified in the constructor is invalid. 	 */
DECL|field|m_context
specifier|protected
name|int
name|m_context
init|=
name|INDEPENDENT
decl_stmt|;
DECL|method|getTypeId ()
specifier|public
specifier|abstract
name|String
name|getTypeId
parameter_list|()
function_decl|;
DECL|method|AbstractGroup (String name, int context)
specifier|public
name|AbstractGroup
parameter_list|(
name|String
name|name
parameter_list|,
name|int
name|context
parameter_list|)
block|{
name|m_name
operator|=
name|name
expr_stmt|;
name|setHierarchicalContext
argument_list|(
name|context
argument_list|)
expr_stmt|;
block|}
comment|/** Group's contents are independent of its hierarchical position. */
DECL|field|INDEPENDENT
specifier|public
specifier|static
specifier|final
name|int
name|INDEPENDENT
init|=
literal|0
decl_stmt|;
comment|/** 	 * Group's content is the intersection of its own content with its 	 * supergroup's content. 	 */
DECL|field|REFINING
specifier|public
specifier|static
specifier|final
name|int
name|REFINING
init|=
literal|1
decl_stmt|;
comment|/** 	 * Group's content is the union of its own content with its subgroups' 	 * content. 	 */
DECL|field|INCLUDING
specifier|public
specifier|static
specifier|final
name|int
name|INCLUDING
init|=
literal|2
decl_stmt|;
comment|/** Character used for quoting in the string representation. */
DECL|field|QUOTE_CHAR
specifier|protected
specifier|static
specifier|final
name|char
name|QUOTE_CHAR
init|=
literal|'\\'
decl_stmt|;
comment|/** 	 * For separating units (e.g. name, which every group has) in the string 	 * representation 	 */
DECL|field|SEPARATOR
specifier|protected
specifier|static
specifier|final
name|String
name|SEPARATOR
init|=
literal|";"
decl_stmt|;
comment|/** 	 * @return A search rule that will identify this group's entries. 	 */
DECL|method|getSearchRule ()
specifier|public
specifier|abstract
name|SearchRule
name|getSearchRule
parameter_list|()
function_decl|;
comment|/** 	 * Re-create a group instance from a textual representation. 	 *  	 * @param s 	 *            The result from the group's toString() method. 	 * @return New instance of the encoded group. 	 * @throws Exception 	 *             If an error occured and a group could not be created, e.g. 	 *             due to a malformed regular expression. 	 */
DECL|method|fromString (String s, BibtexDatabase db, int version)
specifier|public
specifier|static
name|AbstractGroup
name|fromString
parameter_list|(
name|String
name|s
parameter_list|,
name|BibtexDatabase
name|db
parameter_list|,
name|int
name|version
parameter_list|)
throws|throws
name|Exception
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
return|return
name|KeywordGroup
operator|.
name|fromString
argument_list|(
name|s
argument_list|,
name|db
argument_list|,
name|version
argument_list|)
return|;
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
return|return
name|AllEntriesGroup
operator|.
name|fromString
argument_list|(
name|s
argument_list|,
name|db
argument_list|,
name|version
argument_list|)
return|;
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
return|return
name|SearchGroup
operator|.
name|fromString
argument_list|(
name|s
argument_list|,
name|db
argument_list|,
name|version
argument_list|)
return|;
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
return|return
name|ExplicitGroup
operator|.
name|fromString
argument_list|(
name|s
argument_list|,
name|db
argument_list|,
name|version
argument_list|)
return|;
return|return
literal|null
return|;
comment|// unknown group
block|}
comment|/** Returns this group's name, e.g. for display in a list/tree. */
DECL|method|getName ()
specifier|public
specifier|final
name|String
name|getName
parameter_list|()
block|{
return|return
name|m_name
return|;
block|}
comment|/** Sets the group's name. */
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
name|m_name
operator|=
name|name
expr_stmt|;
block|}
comment|/** 	 * @return true if this type of group supports the explicit adding of 	 *         entries. 	 */
DECL|method|supportsAdd ()
specifier|public
specifier|abstract
name|boolean
name|supportsAdd
parameter_list|()
function_decl|;
comment|/** 	 * @return true if this type of group supports the explicit removal of 	 *         entries. 	 */
DECL|method|supportsRemove ()
specifier|public
specifier|abstract
name|boolean
name|supportsRemove
parameter_list|()
function_decl|;
comment|/** 	 * Adds the specified entries to this group. 	 *  	 * @return If this group or one or more entries was/were modified as a 	 *         result of this operation, an object is returned that allows to 	 *         undo this change. null is returned otherwise. 	 */
DECL|method|add (BibtexEntry[] entries)
specifier|public
specifier|abstract
name|AbstractUndoableEdit
name|add
parameter_list|(
name|BibtexEntry
index|[]
name|entries
parameter_list|)
function_decl|;
comment|/** 	 * Removes the specified entries from this group. 	 *  	 * @return If this group or one or more entries was/were modified as a 	 *         result of this operation, an object is returned that allows to 	 *         undo this change. null is returned otherwise. 	 */
DECL|method|remove (BibtexEntry[] entries)
specifier|public
specifier|abstract
name|AbstractUndoableEdit
name|remove
parameter_list|(
name|BibtexEntry
index|[]
name|entries
parameter_list|)
function_decl|;
comment|/** 	 * @param searchOptions 	 *            The search options to apply. 	 * @return true if this group contains the specified entry, false otherwise. 	 */
DECL|method|contains (Map<String, String> searchOptions, BibtexEntry entry)
specifier|public
specifier|abstract
name|boolean
name|contains
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|searchOptions
parameter_list|,
name|BibtexEntry
name|entry
parameter_list|)
function_decl|;
comment|/** 	 * @return true if this group contains the specified entry, false otherwise. 	 */
DECL|method|contains (BibtexEntry entry)
specifier|public
specifier|abstract
name|boolean
name|contains
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|)
function_decl|;
comment|/** 	 * @return true if this group contains any of the specified entries, false 	 *         otherwise. 	 */
DECL|method|containsAny (BibtexEntry[] entries)
specifier|public
name|boolean
name|containsAny
parameter_list|(
name|BibtexEntry
index|[]
name|entries
parameter_list|)
block|{
for|for
control|(
name|BibtexEntry
name|entry
range|:
name|entries
control|)
if|if
condition|(
name|contains
argument_list|(
name|entry
argument_list|)
condition|)
return|return
literal|true
return|;
return|return
literal|false
return|;
block|}
comment|/** 	 * @return true if this group contains all of the specified entries, false 	 *         otherwise. 	 */
DECL|method|containsAll (BibtexEntry[] entries)
specifier|public
name|boolean
name|containsAll
parameter_list|(
name|BibtexEntry
index|[]
name|entries
parameter_list|)
block|{
for|for
control|(
name|BibtexEntry
name|entry
range|:
name|entries
control|)
if|if
condition|(
operator|!
name|contains
argument_list|(
name|entry
argument_list|)
condition|)
return|return
literal|false
return|;
return|return
literal|true
return|;
block|}
comment|/** 	 * Returns true if this group is dynamic, i.e. uses a search definition or 	 * equiv. that might match new entries, or false if this group contains a 	 * fixed set of entries and thus will never match a new entry that was not 	 * explicitly added to it. 	 */
DECL|method|isDynamic ()
specifier|public
specifier|abstract
name|boolean
name|isDynamic
parameter_list|()
function_decl|;
comment|/** Sets the groups's hierarchical context. If context is not a valid 	 * value, the call is ignored. */
DECL|method|setHierarchicalContext (int context)
specifier|public
name|void
name|setHierarchicalContext
parameter_list|(
name|int
name|context
parameter_list|)
block|{
if|if
condition|(
name|context
operator|!=
name|INDEPENDENT
operator|&&
name|context
operator|!=
name|REFINING
operator|&&
name|context
operator|!=
name|INCLUDING
condition|)
return|return;
name|m_context
operator|=
name|context
expr_stmt|;
block|}
comment|/** Returns the group's hierarchical context. */
DECL|method|getHierarchicalContext ()
specifier|public
name|int
name|getHierarchicalContext
parameter_list|()
block|{
return|return
name|m_context
return|;
block|}
comment|/** Returns a lengthy textual description of this instance (for       * the groups editor). The text is formatted in HTML. */
DECL|method|getDescription ()
specifier|public
specifier|abstract
name|String
name|getDescription
parameter_list|()
function_decl|;
comment|/** 	 * @return A deep copy of this object. 	 */
DECL|method|deepCopy ()
specifier|public
specifier|abstract
name|AbstractGroup
name|deepCopy
parameter_list|()
function_decl|;
comment|/** Returns a short description of the group in HTML (for a tooltip). */
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
comment|/**          * Update the group, if necessary, to handle the situation where the group          * is applied to a different BibtexDatabase than it was created for. This          * is for instance used when updating the group tree due to an external change.          *          * @param db The database to refresh for.          */
DECL|method|refreshForNewDatabase (BibtexDatabase db)
specifier|public
name|void
name|refreshForNewDatabase
parameter_list|(
name|BibtexDatabase
name|db
parameter_list|)
block|{
comment|// Default is to do nothing. Group types that are affected by a change
comment|// of database must override this method.
block|}
block|}
end_class

end_unit

