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

begin_comment
comment|/**  * A class implementing this interface can provided as a receiver for error messages originating  * in a thread that can't return any value or throw any exceptions. E.g. net.sf.jabref.DatabaseSearch.  */
end_comment

begin_interface
DECL|interface|ErrorMessageDisplay
specifier|public
interface|interface
name|ErrorMessageDisplay
block|{
DECL|method|reportError (String errorMessage)
specifier|public
name|void
name|reportError
parameter_list|(
name|String
name|errorMessage
parameter_list|)
function_decl|;
DECL|method|reportError (String errorMessage, Exception exception)
specifier|public
name|void
name|reportError
parameter_list|(
name|String
name|errorMessage
parameter_list|,
name|Exception
name|exception
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

