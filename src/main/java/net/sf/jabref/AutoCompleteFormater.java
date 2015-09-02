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

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|ActionEvent
import|;
end_import

begin_interface
DECL|interface|AutoCompleteFormater
specifier|public
interface|interface
name|AutoCompleteFormater
parameter_list|<
name|E
parameter_list|>
block|{
DECL|method|formatItemToString (E item)
name|String
name|formatItemToString
parameter_list|(
name|E
name|item
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

