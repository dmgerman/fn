begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.model.entry
package|package
name|net
operator|.
name|sf
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|model
operator|.
name|database
operator|.
name|BibtexDatabase
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
name|List
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
comment|/**  * Interface for all EntryTypes.  */
end_comment

begin_interface
DECL|interface|EntryType
specifier|public
interface|interface
name|EntryType
extends|extends
name|Comparable
argument_list|<
name|EntryType
argument_list|>
block|{
comment|/**      * Returns the tag name of the entry type.      *      * @return tag name of the entry type.      */
DECL|method|getName ()
name|String
name|getName
parameter_list|()
function_decl|;
comment|/**      * Returns all supported optional field names.      *      * @return a List of optional field name Strings      */
DECL|method|getOptionalFields ()
name|List
argument_list|<
name|String
argument_list|>
name|getOptionalFields
parameter_list|()
function_decl|;
comment|/**      * Returns all required field names.      * If fields have an OR relationship the name includes both field names divided by /, e.g. author/editor.      * If you need all required fields as sole entities use @see{getRequiredFieldsFlat} .      *      * @return a List of required field name Strings      */
DECL|method|getRequiredFields ()
name|List
argument_list|<
name|String
argument_list|>
name|getRequiredFields
parameter_list|()
function_decl|;
comment|/**      * Returns all required field names.      * No OR relationships are captured here.      *      * @return a List of required field name Strings      */
DECL|method|getRequiredFieldsFlat ()
specifier|default
name|List
argument_list|<
name|String
argument_list|>
name|getRequiredFieldsFlat
parameter_list|()
block|{
name|List
argument_list|<
name|String
argument_list|>
name|requiredFlat
init|=
name|getRequiredFields
argument_list|()
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|field
lambda|->
name|field
operator|.
name|split
argument_list|(
literal|"/"
argument_list|)
argument_list|)
operator|.
name|flatMap
argument_list|(
name|Arrays
operator|::
name|stream
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
argument_list|)
decl_stmt|;
return|return
name|Collections
operator|.
name|unmodifiableList
argument_list|(
name|requiredFlat
argument_list|)
return|;
block|}
comment|/**      * TODO: move inside GUI      */
DECL|method|getPrimaryOptionalFields ()
name|List
argument_list|<
name|String
argument_list|>
name|getPrimaryOptionalFields
parameter_list|()
function_decl|;
comment|/**      * TODO: move inside GUI      */
DECL|method|getSecondaryOptionalFields ()
name|List
argument_list|<
name|String
argument_list|>
name|getSecondaryOptionalFields
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

