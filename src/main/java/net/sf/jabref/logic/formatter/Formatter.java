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
comment|/**      * Returns a human readable name of the formatter usable for e.g. in the GUI      *      * @return the name of the formatter      */
DECL|method|getName ()
name|String
name|getName
parameter_list|()
function_decl|;
comment|/**      * Formats a field value by with a particular formatter transformation.      *      * @param value the input String      * @return the formatted output String      */
DECL|method|format (String value)
name|String
name|format
parameter_list|(
name|String
name|value
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

