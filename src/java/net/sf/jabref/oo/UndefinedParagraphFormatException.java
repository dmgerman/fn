begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.oo
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|oo
package|;
end_package

begin_comment
comment|/**  * Exception used to indicate that the plugin attempted to set a paragraph format that is  * not defined in the current OpenOffice document.  */
end_comment

begin_class
DECL|class|UndefinedParagraphFormatException
specifier|public
class|class
name|UndefinedParagraphFormatException
extends|extends
name|Exception
block|{
DECL|field|formatName
name|String
name|formatName
decl_stmt|;
DECL|method|UndefinedParagraphFormatException (String formatName)
specifier|public
name|UndefinedParagraphFormatException
parameter_list|(
name|String
name|formatName
parameter_list|)
block|{
name|super
argument_list|()
expr_stmt|;
name|this
operator|.
name|formatName
operator|=
name|formatName
expr_stmt|;
block|}
DECL|method|getFormatName ()
specifier|public
name|String
name|getFormatName
parameter_list|()
block|{
return|return
name|formatName
return|;
block|}
block|}
end_class

end_unit

