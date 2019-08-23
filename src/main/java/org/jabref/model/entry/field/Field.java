begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.model.entry.field
package|package
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|field
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

begin_interface
DECL|interface|Field
specifier|public
interface|interface
name|Field
block|{
comment|/**      * properties contains mappings to tell the EntryEditor to add a specific function to this field,      * for instance a dropdown for selecting the month for the month field.      */
DECL|method|getProperties ()
name|Set
argument_list|<
name|FieldProperty
argument_list|>
name|getProperties
parameter_list|()
function_decl|;
comment|/**      * @return A version of the field name more suitable for display      */
DECL|method|getDisplayName ()
specifier|default
name|String
name|getDisplayName
parameter_list|()
block|{
return|return
name|StringUtil
operator|.
name|capitalizeFirst
argument_list|(
name|getName
argument_list|()
argument_list|)
return|;
block|}
DECL|method|getName ()
name|String
name|getName
parameter_list|()
function_decl|;
DECL|method|isStandardField ()
name|boolean
name|isStandardField
parameter_list|()
function_decl|;
DECL|method|isDeprecated ()
specifier|default
name|boolean
name|isDeprecated
parameter_list|()
block|{
return|return
literal|false
return|;
block|}
DECL|method|getAlias ()
specifier|default
name|Optional
argument_list|<
name|Field
argument_list|>
name|getAlias
parameter_list|()
block|{
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
DECL|method|isNumeric ()
specifier|default
name|boolean
name|isNumeric
parameter_list|()
block|{
return|return
name|getProperties
argument_list|()
operator|.
name|contains
argument_list|(
name|FieldProperty
operator|.
name|NUMERIC
argument_list|)
return|;
block|}
block|}
end_interface

end_unit

