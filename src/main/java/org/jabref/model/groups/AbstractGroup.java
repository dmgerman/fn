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
name|javafx
operator|.
name|scene
operator|.
name|paint
operator|.
name|Color
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
name|strings
operator|.
name|StringUtil
import|;
end_import

begin_comment
comment|/**  * Base class for all groups.  */
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
comment|/**      * The group's name.      */
DECL|field|name
specifier|protected
specifier|final
name|String
name|name
decl_stmt|;
comment|/**      * The hierarchical context of the group.      */
DECL|field|context
specifier|protected
specifier|final
name|GroupHierarchyType
name|context
decl_stmt|;
DECL|field|color
specifier|protected
name|Optional
argument_list|<
name|Color
argument_list|>
name|color
init|=
name|Optional
operator|.
name|empty
argument_list|()
decl_stmt|;
DECL|field|isExpanded
specifier|protected
name|boolean
name|isExpanded
init|=
literal|true
decl_stmt|;
DECL|field|description
specifier|protected
name|Optional
argument_list|<
name|String
argument_list|>
name|description
init|=
name|Optional
operator|.
name|empty
argument_list|()
decl_stmt|;
DECL|field|iconCode
specifier|protected
name|Optional
argument_list|<
name|String
argument_list|>
name|iconCode
init|=
name|Optional
operator|.
name|empty
argument_list|()
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
annotation|@
name|Override
DECL|method|toString ()
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
literal|"AbstractGroup{"
operator|+
literal|"name='"
operator|+
name|name
operator|+
literal|'\''
operator|+
literal|", context="
operator|+
name|context
operator|+
literal|", color="
operator|+
name|color
operator|+
literal|", isExpanded="
operator|+
name|isExpanded
operator|+
literal|", description="
operator|+
name|description
operator|+
literal|", iconCode="
operator|+
name|iconCode
operator|+
literal|'}'
return|;
block|}
DECL|method|getColor ()
specifier|public
name|Optional
argument_list|<
name|Color
argument_list|>
name|getColor
parameter_list|()
block|{
return|return
name|color
return|;
block|}
DECL|method|setColor (String colorString)
specifier|public
name|void
name|setColor
parameter_list|(
name|String
name|colorString
parameter_list|)
block|{
if|if
condition|(
name|StringUtil
operator|.
name|isBlank
argument_list|(
name|colorString
argument_list|)
condition|)
block|{
name|color
operator|=
name|Optional
operator|.
name|empty
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|setColor
argument_list|(
name|Color
operator|.
name|valueOf
argument_list|(
name|colorString
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|setColor (Color color)
specifier|public
name|void
name|setColor
parameter_list|(
name|Color
name|color
parameter_list|)
block|{
name|this
operator|.
name|color
operator|=
name|Optional
operator|.
name|of
argument_list|(
name|color
argument_list|)
expr_stmt|;
block|}
DECL|method|isExpanded ()
specifier|public
name|boolean
name|isExpanded
parameter_list|()
block|{
return|return
name|isExpanded
return|;
block|}
DECL|method|setExpanded (boolean expanded)
specifier|public
name|void
name|setExpanded
parameter_list|(
name|boolean
name|expanded
parameter_list|)
block|{
name|isExpanded
operator|=
name|expanded
expr_stmt|;
block|}
DECL|method|getDescription ()
specifier|public
name|Optional
argument_list|<
name|String
argument_list|>
name|getDescription
parameter_list|()
block|{
return|return
name|description
return|;
block|}
DECL|method|setDescription (String description)
specifier|public
name|void
name|setDescription
parameter_list|(
name|String
name|description
parameter_list|)
block|{
name|this
operator|.
name|description
operator|=
name|Optional
operator|.
name|of
argument_list|(
name|description
argument_list|)
expr_stmt|;
block|}
DECL|method|getIconCode ()
specifier|public
name|Optional
argument_list|<
name|String
argument_list|>
name|getIconCode
parameter_list|()
block|{
return|return
name|iconCode
return|;
block|}
DECL|method|setIconCode (String iconCode)
specifier|public
name|void
name|setIconCode
parameter_list|(
name|String
name|iconCode
parameter_list|)
block|{
name|this
operator|.
name|iconCode
operator|=
name|Optional
operator|.
name|of
argument_list|(
name|iconCode
argument_list|)
expr_stmt|;
block|}
comment|/**      * Returns the way this group relates to its sub- or supergroup.      */
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
comment|/**      * @return true if this group contains any of the specified entries, false otherwise.      */
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
comment|/**      * @return true if this group contains all of the specified entries, false otherwise.      */
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
comment|/**      * @return A deep copy of this object.      */
DECL|method|deepCopy ()
specifier|public
specifier|abstract
name|AbstractGroup
name|deepCopy
parameter_list|()
function_decl|;
block|}
end_class

end_unit

