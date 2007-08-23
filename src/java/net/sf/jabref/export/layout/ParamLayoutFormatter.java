begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.export.layout
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|export
operator|.
name|layout
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
name|export
operator|.
name|layout
operator|.
name|LayoutFormatter
import|;
end_import

begin_comment
comment|/**  * This interface extends LayoutFormatter, adding the capability of taking  * and additional parameter. Such a parameter is specified in the layout file  * by the following construct: \format[MyFormatter(argument){\field}  * If and only if MyFormatter is a class that implements ParamLayoutFormatter,  * it will be set up with the argument given in the parenthesis by way of the  * method setArgument(String). If no argument is given, the formatter will be  * invoked without the setArgument() method being called first.  */
end_comment

begin_interface
DECL|interface|ParamLayoutFormatter
specifier|public
interface|interface
name|ParamLayoutFormatter
extends|extends
name|LayoutFormatter
block|{
comment|/**      * Method for setting the argument of this formatter.      * @param arg A String argument.      */
DECL|method|setArgument (String arg)
specifier|public
name|void
name|setArgument
parameter_list|(
name|String
name|arg
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

