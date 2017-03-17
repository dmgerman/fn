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

begin_enum
DECL|enum|GroupHierarchyType
specifier|public
enum|enum
name|GroupHierarchyType
block|{
comment|/**      * Group's contents are independent of its hierarchical position.      */
DECL|enumConstant|INDEPENDENT
name|INDEPENDENT
block|,
comment|/**      * Group's content is the intersection of its own content with its supergroup's content.      */
DECL|enumConstant|REFINING
name|REFINING
block|,
comment|// INTERSECTION
comment|/**      * Group's content is the union of its own content with its subgroups' content.      */
DECL|enumConstant|INCLUDING
name|INCLUDING
block|;
comment|// UNION
comment|/**      * Returns the hierarchy type from its position in this enum.      * If the specified position is out of the enums bounds, then {@link #INDEPENDENT} is returned.      */
DECL|method|getByNumberOrDefault (int type)
specifier|public
specifier|static
name|GroupHierarchyType
name|getByNumberOrDefault
parameter_list|(
name|int
name|type
parameter_list|)
block|{
name|GroupHierarchyType
index|[]
name|types
init|=
name|values
argument_list|()
decl_stmt|;
if|if
condition|(
name|type
operator|>=
literal|0
operator|&&
name|type
operator|<
name|types
operator|.
name|length
condition|)
block|{
return|return
name|types
index|[
name|type
index|]
return|;
block|}
else|else
block|{
return|return
name|INDEPENDENT
return|;
block|}
block|}
block|}
end_enum

end_unit

