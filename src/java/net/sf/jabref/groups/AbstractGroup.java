begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* All programs in this directory and subdirectories are published under the  GNU General Public License as described below.  This program is free software; you can redistribute it and/or modify it  under the terms of the GNU General Public License as published by the Free  Software Foundation; either version 2 of the License, or (at your option)  any later version.  This program is distributed in the hope that it will be useful, but WITHOUT  ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or  FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for  more details.  You should have received a copy of the GNU General Public License along  with this program; if not, write to the Free Software Foundation, Inc., 59  Temple Place, Suite 330, Boston, MA 02111-1307 USA  Further information about the GNU GPL is available at: http://www.gnu.org/copyleft/gpl.ja.html */
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
name|*
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
DECL|field|m_name
specifier|protected
name|String
name|m_name
decl_stmt|;
DECL|method|AbstractGroup (String name)
specifier|public
name|AbstractGroup
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
comment|/**      * For separating units (e.g. name, which every group has) in the string      * representation      */
DECL|field|SEPARATOR
specifier|protected
specifier|static
specifier|final
name|String
name|SEPARATOR
init|=
literal|";"
decl_stmt|;
comment|/**      * @return A search rule that will identify this group's entries.      */
DECL|method|getSearchRule ()
specifier|public
specifier|abstract
name|SearchRule
name|getSearchRule
parameter_list|()
function_decl|;
comment|/**      * Re-create a group instance.      *       * @param s      *            The result from the group's toString() method.      * @return New instance of the encoded group.      * @throws Exception      *             If an error occured and a group could not be created, e.g.      *             due to a malformed regular expression.      */
DECL|method|fromString (String s, BibtexDatabase db)
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
comment|/**      * @return true if this type of group supports the explicit adding of      *         entries.      */
DECL|method|supportsAdd ()
specifier|public
specifier|abstract
name|boolean
name|supportsAdd
parameter_list|()
function_decl|;
comment|/**      * @return true if this type of group supports the explicit removal of      *         entries.      */
DECL|method|supportsRemove ()
specifier|public
specifier|abstract
name|boolean
name|supportsRemove
parameter_list|()
function_decl|;
comment|/**      * Adds the selected entries to this group. This method is to be called      * by GroupTreeNode. The BasePanel instance is required for user feedback.      *       * @return If this group or one or more entries was/were modified as a      *         result of this operation, an object is returned that allows to      *         undo this change. null is returned otherwise.      */
DECL|method|addSelection (BasePanel basePanel)
specifier|abstract
name|AbstractUndoableEdit
name|addSelection
parameter_list|(
name|BasePanel
name|basePanel
parameter_list|)
function_decl|;
comment|/**      * Adds the selected entries to this group.      *        * @return If this group or one or more entries was/were modified as a      *         result of this operation, an object is returned that allows to      *         undo this change. null is returned otherwise.      */
DECL|method|addSelection (BibtexEntry[] entries)
specifier|abstract
name|AbstractUndoableEdit
name|addSelection
parameter_list|(
name|BibtexEntry
index|[]
name|entries
parameter_list|)
function_decl|;
comment|/**      * Removes the selected entries from this group. This method is to be called      * by GroupTreeNode. The BasePanel instance is required for user feedback.      *       * @return If this group or one or more entries was/were modified as a      *         result of this operation, an object is returned that allows to      *         undo this change. null is returned otherwise.      */
DECL|method|removeSelection (BasePanel basePanel)
specifier|abstract
name|AbstractUndoableEdit
name|removeSelection
parameter_list|(
name|BasePanel
name|basePanel
parameter_list|)
function_decl|;
comment|/**      * Removes the selected entries from this group.      *       * @return If this group or one or more entries was/were modified as a      *         result of this operation, an object is returned that allows to      *         undo this change. null is returned otherwise.      */
DECL|method|removeSelection (BibtexEntry[] entries)
specifier|abstract
name|AbstractUndoableEdit
name|removeSelection
parameter_list|(
name|BibtexEntry
index|[]
name|entries
parameter_list|)
function_decl|;
comment|/**      * @param searchOptions      *            The search options to apply.      * @return true if this group contains the specified entry, false otherwise.      */
DECL|method|contains (Map searchOptions, BibtexEntry entry)
specifier|public
specifier|abstract
name|boolean
name|contains
parameter_list|(
name|Map
name|searchOptions
parameter_list|,
name|BibtexEntry
name|entry
parameter_list|)
function_decl|;
comment|/**      * @return true if this group contains the specified entry, false otherwise.      */
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
comment|/**      * @return true if this group contains any of the specified entries, false      *         otherwise.      */
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
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|entries
operator|.
name|length
condition|;
operator|++
name|i
control|)
if|if
condition|(
name|contains
argument_list|(
name|entries
index|[
name|i
index|]
argument_list|)
condition|)
return|return
literal|true
return|;
return|return
literal|false
return|;
block|}
comment|/**      * @return true if this group contains all of the specified entries, false      *         otherwise.      */
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
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|entries
operator|.
name|length
condition|;
operator|++
name|i
control|)
if|if
condition|(
operator|!
name|contains
argument_list|(
name|entries
index|[
name|i
index|]
argument_list|)
condition|)
return|return
literal|false
return|;
return|return
literal|true
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
block|}
end_class

end_unit

