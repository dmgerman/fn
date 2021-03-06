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

begin_import
import|import
name|org
operator|.
name|controlsfx
operator|.
name|control
operator|.
name|textfield
operator|.
name|AutoCompletionBinding
import|;
end_import

begin_class
DECL|class|AutoCompleterUtil
specifier|public
class|class
name|AutoCompleterUtil
block|{
DECL|method|getRequest (String text)
specifier|public
specifier|static
name|AutoCompletionBinding
operator|.
name|ISuggestionRequest
name|getRequest
parameter_list|(
name|String
name|text
parameter_list|)
block|{
return|return
operator|new
name|AutoCompletionBinding
operator|.
name|ISuggestionRequest
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|boolean
name|isCancelled
parameter_list|()
block|{
return|return
literal|false
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getUserText
parameter_list|()
block|{
return|return
name|text
return|;
block|}
block|}
return|;
block|}
block|}
end_class

end_unit

