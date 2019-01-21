begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.autocompleter
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|autocompleter
package|;
end_package

begin_interface
DECL|interface|AutoCompletionStrategy
specifier|public
interface|interface
name|AutoCompletionStrategy
block|{
DECL|method|analyze (String input)
name|AutoCompletionInput
name|analyze
parameter_list|(
name|String
name|input
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

