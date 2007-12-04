begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
package|;
end_package

begin_interface
DECL|interface|OutputPrinter
specifier|public
interface|interface
name|OutputPrinter
block|{
DECL|method|setStatus (String s)
name|void
name|setStatus
parameter_list|(
name|String
name|s
parameter_list|)
function_decl|;
DECL|method|showMessage (Object message, String title, int msgType)
name|void
name|showMessage
parameter_list|(
name|Object
name|message
parameter_list|,
name|String
name|title
parameter_list|,
name|int
name|msgType
parameter_list|)
function_decl|;
DECL|method|showMessage (String string)
name|void
name|showMessage
parameter_list|(
name|String
name|string
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

