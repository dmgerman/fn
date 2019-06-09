begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.preferences
package|package
name|org
operator|.
name|jabref
operator|.
name|preferences
package|;
end_package

begin_enum
DECL|enum|NewLineSeparator
specifier|public
enum|enum
name|NewLineSeparator
block|{
DECL|enumConstant|CR
name|CR
block|,
DECL|enumConstant|LF
name|LF
block|,
DECL|enumConstant|CRLF
name|CRLF
block|;
comment|/**      * An enum which contains the possible NewLineSeperators      * Possible are CR ("\n"), LF ("\r") and the windows standard CR/LF.      */
DECL|method|toString ()
specifier|public
name|String
name|toString
parameter_list|()
block|{
switch|switch
condition|(
name|this
condition|)
block|{
case|case
name|CR
case|:
return|return
literal|"CR ( \\r )"
return|;
case|case
name|LF
case|:
return|return
literal|"LF ( \\n )"
return|;
default|default:
return|return
literal|"CR/LF ( \\r\\n )"
return|;
block|}
block|}
comment|/**      * @return the name of the current mode as String      */
DECL|method|getEscapeChars ()
specifier|public
name|String
name|getEscapeChars
parameter_list|()
block|{
switch|switch
condition|(
name|this
condition|)
block|{
case|case
name|CR
case|:
return|return
literal|"\r"
return|;
case|case
name|LF
case|:
return|return
literal|"\n"
return|;
default|default:
return|return
literal|"\r\n"
return|;
block|}
block|}
comment|/**      * Returns the {@link NewLineSeparator} that equals the given string.      **/
DECL|method|parse (String data)
specifier|public
specifier|static
name|NewLineSeparator
name|parse
parameter_list|(
name|String
name|data
parameter_list|)
block|{
switch|switch
condition|(
name|data
condition|)
block|{
case|case
literal|"\r"
case|:
return|return
name|CR
return|;
case|case
literal|"\n"
case|:
return|return
name|LF
return|;
default|default:
return|return
name|CRLF
return|;
block|}
block|}
block|}
end_enum

end_unit

