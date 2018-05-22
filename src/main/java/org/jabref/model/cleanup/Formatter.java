begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.model.cleanup
package|package
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|cleanup
package|;
end_package

begin_comment
comment|/**  * The Formatter is used for a Filter design-pattern. Extending classes have to accept a String and returned a  * formatted version of it. Implementations have to reside in the logic package.  *  * Example:  *  * "John von Neumann" => "von Neumann, John"  *  */
end_comment

begin_class
DECL|class|Formatter
specifier|public
specifier|abstract
class|class
name|Formatter
block|{
comment|/**      * Returns a human readable name of the formatter usable for e.g. in the GUI      *      * @return the name of the formatter, always not null      */
DECL|method|getName ()
specifier|public
specifier|abstract
name|String
name|getName
parameter_list|()
function_decl|;
comment|/**      * Returns a unique key for the formatter that can be used for its identification      * @return the key of the formatter, always not null      */
DECL|method|getKey ()
specifier|public
specifier|abstract
name|String
name|getKey
parameter_list|()
function_decl|;
comment|/**      * Formats a field value by with a particular formatter transformation.      *      * Calling this method with a null argument results in a NullPointerException.      *      * @param value the input String      * @return the formatted output String      */
DECL|method|format (String value)
specifier|public
specifier|abstract
name|String
name|format
parameter_list|(
name|String
name|value
parameter_list|)
function_decl|;
comment|/**      * Returns a description of the formatter.      *      * @return the description string, always non empty      */
DECL|method|getDescription ()
specifier|public
specifier|abstract
name|String
name|getDescription
parameter_list|()
function_decl|;
comment|/**      * Returns an example input string of the formatter.      * This example is used as input to the formatter to demonstrate its functionality      *      * @return the example input string, always non empty      */
DECL|method|getExampleInput ()
specifier|public
specifier|abstract
name|String
name|getExampleInput
parameter_list|()
function_decl|;
comment|/**      * Returns a default hashcode of the formatter based on its key.      *      * @return the hash of the key of the formatter      */
annotation|@
name|Override
DECL|method|hashCode ()
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|getKey
argument_list|()
operator|.
name|hashCode
argument_list|()
return|;
block|}
comment|/**      * Indicates whether some other object is the same formatter as this one based on the key.      *      * @param obj the object to compare the formatter to      * @return true if the object is a formatter with the same key      */
annotation|@
name|Override
DECL|method|equals (Object obj)
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|obj
parameter_list|)
block|{
if|if
condition|(
name|obj
operator|instanceof
name|Formatter
condition|)
block|{
return|return
name|getKey
argument_list|()
operator|.
name|equals
argument_list|(
operator|(
operator|(
name|Formatter
operator|)
name|obj
operator|)
operator|.
name|getKey
argument_list|()
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
block|}
end_class

end_unit

