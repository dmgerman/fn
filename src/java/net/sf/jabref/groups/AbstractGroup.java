begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
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
specifier|abstract
name|String
name|getName
parameter_list|()
function_decl|;
comment|/**      * Re-create multiple instances (of not necessarily the same type) from the      * specified Vector.      *       * @param vector      *            A vector containing String representations obtained from a      *            group's toString() method.      * @return A vector containing the recreated group instances.      * @throws Exception      *             If an error occured and a group could not be created, e.g.      *             due to a malformed regular expression.      */
DECL|method|fromString (Vector vector)
specifier|public
specifier|static
specifier|final
name|Vector
name|fromString
parameter_list|(
name|Vector
name|vector
parameter_list|)
throws|throws
name|Exception
block|{
name|Vector
name|groups
init|=
operator|new
name|Vector
argument_list|()
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
name|vector
operator|.
name|size
argument_list|()
condition|;
operator|++
name|i
control|)
name|groups
operator|.
name|add
argument_list|(
name|fromString
argument_list|(
name|vector
operator|.
name|elementAt
argument_list|(
name|i
argument_list|)
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|groups
return|;
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
comment|/**      * Adds the selected entries to this group.      */
DECL|method|addSelection (BasePanel basePanel)
specifier|public
specifier|abstract
name|void
name|addSelection
parameter_list|(
name|BasePanel
name|basePanel
parameter_list|)
function_decl|;
comment|/**      * Removes the selected entries from this group.      */
DECL|method|removeSelection (BasePanel basePanel)
specifier|public
specifier|abstract
name|void
name|removeSelection
parameter_list|(
name|BasePanel
name|basePanel
parameter_list|)
function_decl|;
comment|/**      * @return A value>0 if this group contains the specified entry, 0      *         otherwise.      */
DECL|method|contains (Map searchOptions, BibtexEntry entry)
specifier|public
specifier|abstract
name|int
name|contains
parameter_list|(
name|Map
name|searchOptions
parameter_list|,
name|BibtexEntry
name|entry
parameter_list|)
function_decl|;
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

