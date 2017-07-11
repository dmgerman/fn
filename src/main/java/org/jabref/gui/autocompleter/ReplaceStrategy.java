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

begin_class
DECL|class|ReplaceStrategy
specifier|public
class|class
name|ReplaceStrategy
implements|implements
name|AutoCompletionStrategy
block|{
annotation|@
name|Override
DECL|method|analyze (String input)
specifier|public
name|AutoCompletionInput
name|analyze
parameter_list|(
name|String
name|input
parameter_list|)
block|{
return|return
operator|new
name|AutoCompletionInput
argument_list|(
literal|""
argument_list|,
name|input
argument_list|)
return|;
block|}
block|}
end_class

end_unit

