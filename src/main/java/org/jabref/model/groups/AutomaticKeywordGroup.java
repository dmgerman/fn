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
DECL|field|keywordSeperator
specifier|private
name|Character
name|keywordSeperator
decl_stmt|;
DECL|field|field
specifier|private
name|String
name|field
decl_stmt|;
DECL|method|AutomaticKeywordGroup (String name, GroupHierarchyType context, String field, Character keywordSeperator)
specifier|public
name|AutomaticKeywordGroup
parameter_list|(
name|String
name|name
parameter_list|,
name|GroupHierarchyType
name|context
parameter_list|,
name|String
name|field
parameter_list|,
name|Character
name|keywordSeperator
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
name|keywordSeperator
operator|=
name|keywordSeperator
expr_stmt|;
block|}
DECL|method|getKeywordSeperator ()
specifier|public
name|Character
name|getKeywordSeperator
parameter_list|()
block|{
return|return
name|keywordSeperator
return|;
block|}
DECL|method|getField ()
specifier|public
name|String
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
argument_list|,
name|this
operator|.
name|context
argument_list|,
name|field
argument_list|,
name|this
operator|.
name|keywordSeperator
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
name|keywordSeperator
argument_list|)
argument_list|)
decl_stmt|;
return|return
name|OptionalUtil
operator|.
name|flatMap
argument_list|(
name|keywordList
argument_list|,
name|KeywordList
operator|::
name|toStringList
argument_list|)
operator|.
name|map
argument_list|(
name|keyword
lambda|->
operator|new
name|WordKeywordGroup
argument_list|(
name|keyword
argument_list|,
name|GroupHierarchyType
operator|.
name|INDEPENDENT
argument_list|,
name|field
argument_list|,
name|keyword
argument_list|,
literal|true
argument_list|,
name|keywordSeperator
argument_list|,
literal|true
argument_list|)
argument_list|)
operator|.
name|map
argument_list|(
name|GroupTreeNode
operator|::
operator|new
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
block|}
end_class

end_unit

