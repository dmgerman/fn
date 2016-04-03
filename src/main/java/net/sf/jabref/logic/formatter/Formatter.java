begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.formatter
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|formatter
package|;
end_package

begin_comment
comment|/**  * The Formatter is used for a Filter design-pattern. Implementing classes have to accept a String and returned a  * formatted version of it.  *  * Example:  *  * "John von Neumann" => "von Neumann, John"  *  */
end_comment

begin_interface
DECL|interface|Formatter
specifier|public
interface|interface
name|Formatter
block|{
comment|/**      * Returns a human readable name of the formatter usable for e.g. in the GUI      *      * @return the name of the formatter, always not null      */
DECL|method|getName ()
name|String
name|getName
parameter_list|()
function_decl|;
comment|/**      * Returns a unique key for the formatter that can be used for its identification      * @return the key of the formatter, always not null      */
DECL|method|getKey ()
name|String
name|getKey
parameter_list|()
function_decl|;
comment|/**      * Formats a field value by with a particular formatter transformation.      *      * Calling this method with a null argument results in a NullPointerException.      *      * @param value the input String      * @return the formatted output String      */
DECL|method|format (String value)
name|String
name|format
parameter_list|(
name|String
name|value
parameter_list|)
function_decl|;
comment|/**      * Returns a description of the formatter.      *      * @return the description string, always non empty      */
DECL|method|getDescription ()
name|String
name|getDescription
parameter_list|()
function_decl|;
comment|/**      * Returns a default hashcode of the formatter based on its key.      *      * @return the hash of the key of the formatter      */
DECL|method|defaultHashCode ()
specifier|default
name|int
name|defaultHashCode
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
DECL|method|defaultEquals (Object obj)
specifier|default
name|boolean
name|defaultEquals
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
end_interface

end_unit

