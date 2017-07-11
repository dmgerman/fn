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

begin_comment
comment|/**  * For "ONLY_FULL", the auto completer returns the full name, e.g. "Smith, Bob"  * For "ONLY_ABBREVIATED", the auto completer returns the first name abbreviated, e.g. "Smith, B."  * For "BOTH", the auto completer returns both versions.  */
end_comment

begin_enum
DECL|enum|AutoCompleteFirstNameMode
specifier|public
enum|enum
name|AutoCompleteFirstNameMode
block|{
DECL|enumConstant|ONLY_FULL
name|ONLY_FULL
block|,
DECL|enumConstant|ONLY_ABBREVIATED
name|ONLY_ABBREVIATED
block|,
DECL|enumConstant|BOTH
name|BOTH
block|;
DECL|method|parse (String input)
specifier|public
specifier|static
name|AutoCompleteFirstNameMode
name|parse
parameter_list|(
name|String
name|input
parameter_list|)
block|{
try|try
block|{
return|return
name|AutoCompleteFirstNameMode
operator|.
name|valueOf
argument_list|(
name|input
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|IllegalArgumentException
name|ex
parameter_list|)
block|{
comment|// Should only occur when preferences are set directly via preferences.put and not via setFirstnameMode
return|return
name|AutoCompleteFirstNameMode
operator|.
name|BOTH
return|;
block|}
block|}
block|}
end_enum

end_unit

