begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.model.metadata
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|model
operator|.
name|metadata
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Arrays
import|;
end_import

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
name|HashMap
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
name|Map
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

begin_class
DECL|class|ContentSelectors
specifier|public
class|class
name|ContentSelectors
block|{
DECL|field|contentSelectors
specifier|private
specifier|final
name|List
argument_list|<
name|ContentSelector
argument_list|>
name|contentSelectors
decl_stmt|;
DECL|method|ContentSelectors ()
specifier|public
name|ContentSelectors
parameter_list|()
block|{
name|contentSelectors
operator|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
expr_stmt|;
block|}
DECL|method|addContentSelector (ContentSelector contentSelector)
specifier|public
name|void
name|addContentSelector
parameter_list|(
name|ContentSelector
name|contentSelector
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|contentSelector
argument_list|)
expr_stmt|;
name|this
operator|.
name|contentSelectors
operator|.
name|add
argument_list|(
name|contentSelector
argument_list|)
expr_stmt|;
block|}
DECL|method|getSelectorValuesForField (String fieldName)
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getSelectorValuesForField
parameter_list|(
name|String
name|fieldName
parameter_list|)
block|{
for|for
control|(
name|ContentSelector
name|selector
range|:
name|contentSelectors
control|)
block|{
if|if
condition|(
name|selector
operator|.
name|getFieldName
argument_list|()
operator|.
name|equals
argument_list|(
name|fieldName
argument_list|)
condition|)
block|{
return|return
name|selector
operator|.
name|getValues
argument_list|()
return|;
block|}
block|}
return|return
name|Collections
operator|.
name|emptyList
argument_list|()
return|;
block|}
DECL|method|removeSelector (String fieldName)
specifier|public
name|void
name|removeSelector
parameter_list|(
name|String
name|fieldName
parameter_list|)
block|{
name|contentSelectors
operator|.
name|remove
argument_list|(
name|fieldName
argument_list|)
expr_stmt|;
block|}
DECL|method|parse (String key, String values)
specifier|public
specifier|static
name|ContentSelector
name|parse
parameter_list|(
name|String
name|key
parameter_list|,
name|String
name|values
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|key
argument_list|)
expr_stmt|;
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|values
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|valueList
init|=
name|Arrays
operator|.
name|asList
argument_list|(
name|values
operator|.
name|split
argument_list|(
literal|";"
argument_list|)
argument_list|)
decl_stmt|;
return|return
operator|new
name|ContentSelector
argument_list|(
name|key
argument_list|,
name|valueList
argument_list|)
return|;
block|}
DECL|method|getAsStringList ()
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getAsStringList
parameter_list|()
block|{
comment|// fixme: do the actual serialization
return|return
literal|null
return|;
block|}
DECL|method|getSelectorData ()
specifier|public
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|String
argument_list|>
argument_list|>
name|getSelectorData
parameter_list|()
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|String
argument_list|>
argument_list|>
name|result
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
name|ContentSelector
name|selector
range|:
name|contentSelectors
control|)
block|{
name|result
operator|.
name|put
argument_list|(
name|selector
operator|.
name|getFieldName
argument_list|()
argument_list|,
name|selector
operator|.
name|getValues
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|result
return|;
block|}
block|}
end_class

end_unit

