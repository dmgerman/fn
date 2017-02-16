begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.layout
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|layout
package|;
end_package

begin_comment
comment|/**  * The LayoutFormatter is used for a Filter design-pattern.  *  * Implementing classes have to accept a String and returned a formatted version of it.  *  * Example:  *  *   "John von Neumann" => "von Neumann, John"  *  * @version 1.2 - Documentation CO  */
end_comment

begin_interface
annotation|@
name|FunctionalInterface
DECL|interface|LayoutFormatter
specifier|public
interface|interface
name|LayoutFormatter
block|{
comment|/**      * Failure Mode:      *<p>      * Formatters should be robust in the sense that they always return some      * relevant string.      *<p>      * If the formatter can detect an invalid input it should return the      * original string otherwise it may simply return a wrong output.      *      * @param fieldText      *            The text to layout.      * @return The layouted text.      */
DECL|method|format (String fieldText)
name|String
name|format
parameter_list|(
name|String
name|fieldText
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

