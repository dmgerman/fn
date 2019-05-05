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
name|Collection
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
name|java
operator|.
name|util
operator|.
name|stream
operator|.
name|Stream
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
comment|/**  * This class is used to represent customized entry types.  */
end_comment

begin_class
DECL|class|CustomEntryType
specifier|public
class|class
name|CustomEntryType
implements|implements
name|EntryType
block|{
DECL|field|ENTRYTYPE_FLAG
specifier|public
specifier|static
specifier|final
name|String
name|ENTRYTYPE_FLAG
init|=
literal|"jabref-entrytype: "
decl_stmt|;
DECL|field|name
specifier|private
specifier|final
name|String
name|name
decl_stmt|;
DECL|field|required
specifier|private
specifier|final
name|Set
argument_list|<
name|String
argument_list|>
name|required
decl_stmt|;
DECL|field|optional
specifier|private
specifier|final
name|Set
argument_list|<
name|String
argument_list|>
name|optional
decl_stmt|;
DECL|field|primaryOptional
specifier|private
specifier|final
name|Set
argument_list|<
name|String
argument_list|>
name|primaryOptional
decl_stmt|;
DECL|method|CustomEntryType (String name, Collection<String> required, Collection<String> primaryOptional, Collection<String> secondaryOptional)
specifier|public
name|CustomEntryType
parameter_list|(
name|String
name|name
parameter_list|,
name|Collection
argument_list|<
name|String
argument_list|>
name|required
parameter_list|,
name|Collection
argument_list|<
name|String
argument_list|>
name|primaryOptional
parameter_list|,
name|Collection
argument_list|<
name|String
argument_list|>
name|secondaryOptional
parameter_list|)
block|{
name|this
operator|.
name|name
operator|=
name|StringUtil
operator|.
name|capitalizeFirst
argument_list|(
name|name
argument_list|)
expr_stmt|;
name|this
operator|.
name|primaryOptional
operator|=
operator|new
name|LinkedHashSet
argument_list|<>
argument_list|(
name|primaryOptional
argument_list|)
expr_stmt|;
name|this
operator|.
name|required
operator|=
operator|new
name|LinkedHashSet
argument_list|<>
argument_list|(
name|required
argument_list|)
expr_stmt|;
name|this
operator|.
name|optional
operator|=
name|Stream
operator|.
name|concat
argument_list|(
name|primaryOptional
operator|.
name|stream
argument_list|()
argument_list|,
name|secondaryOptional
operator|.
name|stream
argument_list|()
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toSet
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|CustomEntryType (String name, Collection<String> required, Collection<String> optional)
specifier|public
name|CustomEntryType
parameter_list|(
name|String
name|name
parameter_list|,
name|Collection
argument_list|<
name|String
argument_list|>
name|required
parameter_list|,
name|Collection
argument_list|<
name|String
argument_list|>
name|optional
parameter_list|)
block|{
name|this
operator|.
name|name
operator|=
name|StringUtil
operator|.
name|capitalizeFirst
argument_list|(
name|name
argument_list|)
expr_stmt|;
name|this
operator|.
name|required
operator|=
operator|new
name|LinkedHashSet
argument_list|<>
argument_list|(
name|required
argument_list|)
expr_stmt|;
name|this
operator|.
name|optional
operator|=
operator|new
name|LinkedHashSet
argument_list|<>
argument_list|(
name|optional
argument_list|)
expr_stmt|;
name|this
operator|.
name|primaryOptional
operator|=
operator|new
name|LinkedHashSet
argument_list|<>
argument_list|(
name|optional
argument_list|)
expr_stmt|;
block|}
DECL|method|CustomEntryType (String name, String required, String optional)
specifier|public
name|CustomEntryType
parameter_list|(
name|String
name|name
parameter_list|,
name|String
name|required
parameter_list|,
name|String
name|optional
parameter_list|)
block|{
name|this
argument_list|(
name|name
argument_list|,
name|Arrays
operator|.
name|asList
argument_list|(
name|required
operator|.
name|split
argument_list|(
literal|";"
argument_list|)
argument_list|)
argument_list|,
name|Arrays
operator|.
name|asList
argument_list|(
name|optional
operator|.
name|split
argument_list|(
literal|";"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|parse (String comment)
specifier|public
specifier|static
name|Optional
argument_list|<
name|CustomEntryType
argument_list|>
name|parse
parameter_list|(
name|String
name|comment
parameter_list|)
block|{
name|String
name|rest
init|=
name|comment
operator|.
name|substring
argument_list|(
name|ENTRYTYPE_FLAG
operator|.
name|length
argument_list|()
argument_list|)
decl_stmt|;
name|int
name|indexEndOfName
init|=
name|rest
operator|.
name|indexOf
argument_list|(
literal|':'
argument_list|)
decl_stmt|;
if|if
condition|(
name|indexEndOfName
operator|<
literal|0
condition|)
block|{
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
name|String
name|fieldsDescription
init|=
name|rest
operator|.
name|substring
argument_list|(
name|indexEndOfName
operator|+
literal|2
argument_list|)
decl_stmt|;
name|int
name|indexEndOfRequiredFields
init|=
name|fieldsDescription
operator|.
name|indexOf
argument_list|(
literal|']'
argument_list|)
decl_stmt|;
name|int
name|indexEndOfOptionalFields
init|=
name|fieldsDescription
operator|.
name|indexOf
argument_list|(
literal|']'
argument_list|,
name|indexEndOfRequiredFields
operator|+
literal|1
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|indexEndOfRequiredFields
operator|<
literal|4
operator|)
operator|||
operator|(
name|indexEndOfOptionalFields
operator|<
operator|(
name|indexEndOfRequiredFields
operator|+
literal|6
operator|)
operator|)
condition|)
block|{
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
name|String
name|name
init|=
name|rest
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|indexEndOfName
argument_list|)
decl_stmt|;
name|String
name|reqFields
init|=
name|fieldsDescription
operator|.
name|substring
argument_list|(
literal|4
argument_list|,
name|indexEndOfRequiredFields
argument_list|)
decl_stmt|;
name|String
name|optFields
init|=
name|fieldsDescription
operator|.
name|substring
argument_list|(
name|indexEndOfRequiredFields
operator|+
literal|6
argument_list|,
name|indexEndOfOptionalFields
argument_list|)
decl_stmt|;
return|return
name|Optional
operator|.
name|of
argument_list|(
operator|new
name|CustomEntryType
argument_list|(
name|name
argument_list|,
name|reqFields
argument_list|,
name|optFields
argument_list|)
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
operator|instanceof
name|CustomEntryType
condition|)
block|{
return|return
name|Objects
operator|.
name|equals
argument_list|(
name|name
argument_list|,
operator|(
operator|(
name|CustomEntryType
operator|)
name|o
operator|)
operator|.
name|name
argument_list|)
return|;
block|}
else|else
block|{
return|return
literal|false
return|;
block|}
block|}
annotation|@
name|Override
DECL|method|getName ()
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|name
return|;
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
name|optional
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
name|required
argument_list|)
return|;
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
name|Collections
operator|.
name|unmodifiableSet
argument_list|(
name|primaryOptional
argument_list|)
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
name|Set
argument_list|<
name|String
argument_list|>
name|result
init|=
operator|new
name|LinkedHashSet
argument_list|<>
argument_list|(
name|optional
argument_list|)
decl_stmt|;
name|result
operator|.
name|removeAll
argument_list|(
name|primaryOptional
argument_list|)
expr_stmt|;
return|return
name|Collections
operator|.
name|unmodifiableSet
argument_list|(
name|result
argument_list|)
return|;
block|}
comment|/**      * Get a String describing the required field set for this entry type.      *      * @return Description of required field set for storage in preferences or BIB file.      */
DECL|method|getRequiredFieldsString ()
specifier|public
name|String
name|getRequiredFieldsString
parameter_list|()
block|{
return|return
name|String
operator|.
name|join
argument_list|(
literal|";"
argument_list|,
name|required
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
name|super
operator|.
name|hashCode
argument_list|()
return|;
block|}
DECL|method|getAsString ()
specifier|public
name|String
name|getAsString
parameter_list|()
block|{
name|StringBuilder
name|builder
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|ENTRYTYPE_FLAG
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
literal|": req["
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|getRequiredFieldsString
argument_list|()
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
literal|"] opt["
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|String
operator|.
name|join
argument_list|(
literal|";"
argument_list|,
name|getOptionalFields
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
literal|"]"
argument_list|)
expr_stmt|;
return|return
name|builder
operator|.
name|toString
argument_list|()
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

