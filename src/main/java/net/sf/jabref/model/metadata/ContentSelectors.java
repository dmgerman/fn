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
name|HashMap
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|String
argument_list|>
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
name|HashMap
argument_list|<>
argument_list|()
expr_stmt|;
block|}
DECL|method|addContentSelector (String fieldName, List<String> selectors)
specifier|public
name|void
name|addContentSelector
parameter_list|(
name|String
name|fieldName
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|selectors
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|fieldName
argument_list|)
expr_stmt|;
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|selectors
argument_list|)
expr_stmt|;
name|this
operator|.
name|contentSelectors
operator|.
name|put
argument_list|(
name|fieldName
argument_list|,
name|selectors
argument_list|)
expr_stmt|;
block|}
DECL|method|getSelectorsForField (String fieldName)
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getSelectorsForField
parameter_list|(
name|String
name|fieldName
parameter_list|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|result
init|=
name|contentSelectors
operator|.
name|get
argument_list|(
name|fieldName
argument_list|)
decl_stmt|;
if|if
condition|(
name|result
operator|==
literal|null
condition|)
block|{
name|result
operator|=
name|Collections
operator|.
name|emptyList
argument_list|()
expr_stmt|;
block|}
return|return
name|result
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
DECL|method|parse (List<String> selectors)
specifier|public
specifier|static
name|ContentSelectors
name|parse
parameter_list|(
name|List
argument_list|<
name|String
argument_list|>
name|selectors
parameter_list|)
block|{
comment|//fixme: do the actual parsing
return|return
literal|null
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
name|HashMap
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
return|return
name|contentSelectors
return|;
block|}
block|}
end_class

end_unit

