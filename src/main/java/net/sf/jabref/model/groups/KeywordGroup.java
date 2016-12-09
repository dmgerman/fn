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

begin_comment
comment|/**  * Matches entries based on a search phrase relative to the content in a specified field.  */
end_comment

begin_class
DECL|class|KeywordGroup
specifier|public
specifier|abstract
class|class
name|KeywordGroup
extends|extends
name|AbstractGroup
block|{
DECL|field|searchField
specifier|protected
specifier|final
name|String
name|searchField
decl_stmt|;
DECL|field|searchExpression
specifier|protected
specifier|final
name|String
name|searchExpression
decl_stmt|;
DECL|field|caseSensitive
specifier|protected
specifier|final
name|boolean
name|caseSensitive
decl_stmt|;
DECL|method|KeywordGroup (String name, GroupHierarchyType context, String searchField, String searchExpression, boolean caseSensitive)
specifier|public
name|KeywordGroup
parameter_list|(
name|String
name|name
parameter_list|,
name|GroupHierarchyType
name|context
parameter_list|,
name|String
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
argument_list|)
expr_stmt|;
name|this
operator|.
name|caseSensitive
operator|=
name|caseSensitive
expr_stmt|;
name|this
operator|.
name|searchField
operator|=
name|searchField
expr_stmt|;
name|this
operator|.
name|searchExpression
operator|=
name|searchExpression
expr_stmt|;
block|}
DECL|method|isCaseSensitive ()
specifier|public
name|boolean
name|isCaseSensitive
parameter_list|()
block|{
return|return
name|caseSensitive
return|;
block|}
DECL|method|getSearchExpression ()
specifier|public
name|String
name|getSearchExpression
parameter_list|()
block|{
return|return
name|searchExpression
return|;
block|}
DECL|method|getSearchField ()
specifier|public
name|String
name|getSearchField
parameter_list|()
block|{
return|return
name|searchField
return|;
block|}
annotation|@
name|Override
DECL|method|isDynamic ()
specifier|public
name|boolean
name|isDynamic
parameter_list|()
block|{
return|return
literal|true
return|;
block|}
block|}
end_class

end_unit

