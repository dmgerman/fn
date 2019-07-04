begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.model.entry
package|package
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
package|;
end_package

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
name|LinkedHashSet
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

begin_comment
comment|/**  * Abstract base class for all biblatex entry types.  */
end_comment

begin_class
DECL|class|BiblatexEntryType
specifier|public
specifier|abstract
class|class
name|BiblatexEntryType
implements|implements
name|EntryType
block|{
DECL|field|requiredFields
specifier|private
specifier|final
name|Set
argument_list|<
name|String
argument_list|>
name|requiredFields
decl_stmt|;
DECL|field|optionalFields
specifier|private
specifier|final
name|Set
argument_list|<
name|String
argument_list|>
name|optionalFields
decl_stmt|;
DECL|method|BiblatexEntryType ()
specifier|public
name|BiblatexEntryType
parameter_list|()
block|{
name|requiredFields
operator|=
operator|new
name|LinkedHashSet
argument_list|<>
argument_list|()
expr_stmt|;
name|optionalFields
operator|=
operator|new
name|LinkedHashSet
argument_list|<>
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|getOptionalFields ()
specifier|public
name|Set
argument_list|<
name|String
argument_list|>
name|getOptionalFields
parameter_list|()
block|{
return|return
name|Collections
operator|.
name|unmodifiableSet
argument_list|(
name|optionalFields
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|getRequiredFields ()
specifier|public
name|Set
argument_list|<
name|String
argument_list|>
name|getRequiredFields
parameter_list|()
block|{
return|return
name|Collections
operator|.
name|unmodifiableSet
argument_list|(
name|requiredFields
argument_list|)
return|;
block|}
DECL|method|addAllOptional (String... fieldNames)
name|void
name|addAllOptional
parameter_list|(
name|String
modifier|...
name|fieldNames
parameter_list|)
block|{
name|optionalFields
operator|.
name|addAll
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|fieldNames
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|addAllRequired (String... fieldNames)
name|void
name|addAllRequired
parameter_list|(
name|String
modifier|...
name|fieldNames
parameter_list|)
block|{
name|requiredFields
operator|.
name|addAll
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|fieldNames
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|getPrimaryOptionalFields ()
specifier|public
name|Set
argument_list|<
name|String
argument_list|>
name|getPrimaryOptionalFields
parameter_list|()
block|{
return|return
name|getOptionalFields
argument_list|()
return|;
block|}
annotation|@
name|Override
DECL|method|getSecondaryOptionalFields ()
specifier|public
name|Set
argument_list|<
name|String
argument_list|>
name|getSecondaryOptionalFields
parameter_list|()
block|{
return|return
name|getOptionalFields
argument_list|()
operator|.
name|stream
argument_list|()
operator|.
name|filter
argument_list|(
name|field
lambda|->
operator|!
name|isPrimary
argument_list|(
name|field
argument_list|)
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
DECL|method|isPrimary (String field)
specifier|private
name|boolean
name|isPrimary
parameter_list|(
name|String
name|field
parameter_list|)
block|{
return|return
name|getPrimaryOptionalFields
argument_list|()
operator|.
name|contains
argument_list|(
name|field
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|compareTo (EntryType o)
specifier|public
name|int
name|compareTo
parameter_list|(
name|EntryType
name|o
parameter_list|)
block|{
return|return
name|getName
argument_list|()
operator|.
name|compareTo
argument_list|(
name|o
operator|.
name|getName
argument_list|()
argument_list|)
return|;
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
name|getName
argument_list|()
return|;
block|}
block|}
end_class

end_unit

