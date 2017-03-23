begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.integrity
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|integrity
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

begin_interface
DECL|interface|ValueChecker
specifier|public
interface|interface
name|ValueChecker
block|{
comment|/**      * Validates the specified value.      * Returns a error massage if the validation failed. Otherwise an empty optional is returned.      */
DECL|method|checkValue (String value)
name|Optional
argument_list|<
name|String
argument_list|>
name|checkValue
parameter_list|(
name|String
name|value
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

