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
name|Set
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
name|Keyword
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
name|KeywordList
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

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|util
operator|.
name|OptionalUtil
import|;
end_import

begin_class
DECL|class|AutomaticKeywordGroup
specifier|public
class|class
name|AutomaticKeywordGroup
extends|extends
name|AutomaticGroup
block|{
DECL|field|keywordDelimiter
specifier|private
name|Character
name|keywordDelimiter
decl_stmt|;
DECL|field|keywordHierarchicalDelimiter
specifier|private
name|Character
name|keywordHierarchicalDelimiter
decl_stmt|;
DECL|field|field
specifier|private
name|Field
name|field
decl_stmt|;
DECL|method|AutomaticKeywordGroup (String name, GroupHierarchyType context, Field field, Character keywordDelimiter, Character keywordHierarchicalDelimiter)
specifier|public
name|AutomaticKeywordGroup
parameter_list|(
name|String
name|name
parameter_list|,
name|GroupHierarchyType
name|context
parameter_list|,
name|Field
name|field
parameter_list|,
name|Character
name|keywordDelimiter
parameter_list|,
name|Character
name|keywordHierarchicalDelimiter
parameter_list|)
block|{
name|super
argument_list|(
name|name
argument_list|,
name|context
argument_list|)
expr_stmt|;
name|this
operator|.
name|field
operator|=
name|field
expr_stmt|;
name|this
operator|.
name|keywordDelimiter
operator|=
name|keywordDelimiter
expr_stmt|;
name|this
operator|.
name|keywordHierarchicalDelimiter
operator|=
name|keywordHierarchicalDelimiter
expr_stmt|;
block|}
DECL|method|getKeywordHierarchicalDelimiter ()
specifier|public
name|Character
name|getKeywordHierarchicalDelimiter
parameter_list|()
block|{
return|return
name|keywordHierarchicalDelimiter
return|;
block|}
DECL|method|getKeywordDelimiter ()
specifier|public
name|Character
name|getKeywordDelimiter
parameter_list|()
block|{
return|return
name|keywordDelimiter
return|;
block|}
DECL|method|getField ()
specifier|public
name|Field
name|getField
parameter_list|()
block|{
return|return
name|field
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
name|AutomaticKeywordGroup
argument_list|(
name|this
operator|.
name|name
operator|.
name|getValue
argument_list|()
argument_list|,
name|this
operator|.
name|context
argument_list|,
name|field
argument_list|,
name|this
operator|.
name|keywordDelimiter
argument_list|,
name|keywordHierarchicalDelimiter
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
name|o
operator|==
literal|null
operator|||
name|getClass
argument_list|()
operator|!=
name|o
operator|.
name|getClass
argument_list|()
condition|)
block|{
return|return
literal|false
return|;
block|}
name|AutomaticKeywordGroup
name|that
init|=
operator|(
name|AutomaticKeywordGroup
operator|)
name|o
decl_stmt|;
return|return
name|Objects
operator|.
name|equals
argument_list|(
name|keywordDelimiter
argument_list|,
name|that
operator|.
name|keywordDelimiter
argument_list|)
operator|&&
name|Objects
operator|.
name|equals
argument_list|(
name|field
argument_list|,
name|that
operator|.
name|field
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
name|keywordDelimiter
argument_list|,
name|field
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|createSubgroups (BibEntry entry)
specifier|public
name|Set
argument_list|<
name|GroupTreeNode
argument_list|>
name|createSubgroups
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
name|Optional
argument_list|<
name|KeywordList
argument_list|>
name|keywordList
init|=
name|entry
operator|.
name|getLatexFreeField
argument_list|(
name|field
argument_list|)
operator|.
name|map
argument_list|(
name|fieldValue
lambda|->
name|KeywordList
operator|.
name|parse
argument_list|(
name|fieldValue
argument_list|,
name|keywordDelimiter
argument_list|)
argument_list|)
decl_stmt|;
return|return
name|OptionalUtil
operator|.
name|toStream
argument_list|(
name|keywordList
argument_list|)
operator|.
name|flatMap
argument_list|(
name|KeywordList
operator|::
name|stream
argument_list|)
operator|.
name|filter
argument_list|(
name|keyword
lambda|->
name|StringUtil
operator|.
name|isNotBlank
argument_list|(
name|keyword
operator|.
name|get
argument_list|()
argument_list|)
argument_list|)
operator|.
name|map
argument_list|(
name|this
operator|::
name|createGroup
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toSet
argument_list|()
argument_list|)
return|;
block|}
DECL|method|createGroup (Keyword keywordChain)
specifier|private
name|GroupTreeNode
name|createGroup
parameter_list|(
name|Keyword
name|keywordChain
parameter_list|)
block|{
name|WordKeywordGroup
name|rootGroup
init|=
operator|new
name|WordKeywordGroup
argument_list|(
name|keywordChain
operator|.
name|get
argument_list|()
argument_list|,
name|GroupHierarchyType
operator|.
name|INCLUDING
argument_list|,
name|field
argument_list|,
name|keywordChain
operator|.
name|getPathFromRootAsString
argument_list|(
name|keywordHierarchicalDelimiter
argument_list|)
argument_list|,
literal|true
argument_list|,
name|keywordDelimiter
argument_list|,
literal|true
argument_list|)
decl_stmt|;
name|GroupTreeNode
name|root
init|=
operator|new
name|GroupTreeNode
argument_list|(
name|rootGroup
argument_list|)
decl_stmt|;
name|keywordChain
operator|.
name|getChild
argument_list|()
operator|.
name|map
argument_list|(
name|this
operator|::
name|createGroup
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|root
operator|::
name|addChild
argument_list|)
expr_stmt|;
return|return
name|root
return|;
block|}
block|}
end_class

end_unit

