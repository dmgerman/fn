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
name|regex
operator|.
name|Pattern
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
name|entry
operator|.
name|field
operator|.
name|Field
import|;
end_import

begin_comment
comment|/**  * Matches entries if the content of a given field is matched by a regular expression.  */
end_comment

begin_class
DECL|class|RegexKeywordGroup
specifier|public
class|class
name|RegexKeywordGroup
extends|extends
name|KeywordGroup
block|{
DECL|field|pattern
specifier|private
name|Pattern
name|pattern
decl_stmt|;
DECL|method|RegexKeywordGroup (String name, GroupHierarchyType context, Field searchField, String searchExpression, boolean caseSensitive)
specifier|public
name|RegexKeywordGroup
parameter_list|(
name|String
name|name
parameter_list|,
name|GroupHierarchyType
name|context
parameter_list|,
name|Field
name|searchField
parameter_list|,
name|String
name|searchExpression
parameter_list|,
name|boolean
name|caseSensitive
parameter_list|)
block|{
name|super
argument_list|(
name|name
argument_list|,
name|context
argument_list|,
name|searchField
argument_list|,
name|searchExpression
argument_list|,
name|caseSensitive
argument_list|)
expr_stmt|;
name|this
operator|.
name|pattern
operator|=
name|compilePattern
argument_list|(
name|searchExpression
argument_list|,
name|caseSensitive
argument_list|)
expr_stmt|;
block|}
DECL|method|compilePattern (String searchExpression, boolean caseSensitive)
specifier|private
specifier|static
name|Pattern
name|compilePattern
parameter_list|(
name|String
name|searchExpression
parameter_list|,
name|boolean
name|caseSensitive
parameter_list|)
block|{
return|return
name|caseSensitive
condition|?
name|Pattern
operator|.
name|compile
argument_list|(
literal|"\\b"
operator|+
name|searchExpression
operator|+
literal|"\\b"
argument_list|)
else|:
name|Pattern
operator|.
name|compile
argument_list|(
literal|"\\b"
operator|+
name|searchExpression
operator|+
literal|"\\b"
argument_list|,
name|Pattern
operator|.
name|CASE_INSENSITIVE
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|contains (BibEntry entry)
specifier|public
name|boolean
name|contains
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
name|Optional
argument_list|<
name|String
argument_list|>
name|content
init|=
name|entry
operator|.
name|getField
argument_list|(
name|searchField
argument_list|)
decl_stmt|;
return|return
name|content
operator|.
name|map
argument_list|(
name|value
lambda|->
name|pattern
operator|.
name|matcher
argument_list|(
name|value
argument_list|)
operator|.
name|find
argument_list|()
argument_list|)
operator|.
name|orElse
argument_list|(
literal|false
argument_list|)
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
operator|!
operator|(
name|o
operator|instanceof
name|RegexKeywordGroup
operator|)
condition|)
block|{
return|return
literal|false
return|;
block|}
name|RegexKeywordGroup
name|other
init|=
operator|(
name|RegexKeywordGroup
operator|)
name|o
decl_stmt|;
return|return
name|getName
argument_list|()
operator|.
name|equals
argument_list|(
name|other
operator|.
name|getName
argument_list|()
argument_list|)
operator|&&
operator|(
name|getHierarchicalContext
argument_list|()
operator|==
name|other
operator|.
name|getHierarchicalContext
argument_list|()
operator|)
operator|&&
name|searchField
operator|.
name|equals
argument_list|(
name|other
operator|.
name|searchField
argument_list|)
operator|&&
name|searchExpression
operator|.
name|equals
argument_list|(
name|other
operator|.
name|searchExpression
argument_list|)
operator|&&
operator|(
name|caseSensitive
operator|==
name|other
operator|.
name|caseSensitive
operator|)
return|;
block|}
annotation|@
name|Override
DECL|method|deepCopy ()
specifier|public
name|AbstractGroup
name|deepCopy
parameter_list|()
block|{
return|return
operator|new
name|RegexKeywordGroup
argument_list|(
name|getName
argument_list|()
argument_list|,
name|getHierarchicalContext
argument_list|()
argument_list|,
name|searchField
argument_list|,
name|searchExpression
argument_list|,
name|caseSensitive
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
name|getName
argument_list|()
argument_list|,
name|getHierarchicalContext
argument_list|()
argument_list|,
name|searchField
argument_list|,
name|searchExpression
argument_list|,
name|caseSensitive
argument_list|)
return|;
block|}
block|}
end_class

end_unit

