begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.openoffice
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|openoffice
package|;
end_package

begin_comment
comment|/**  * Exception used to indicate that the plugin attempted to set a character format that is  * not defined in the current OpenOffice document.  */
end_comment

begin_class
DECL|class|UndefinedCharacterFormatException
class|class
name|UndefinedCharacterFormatException
extends|extends
name|Exception
block|{
DECL|field|formatName
specifier|private
specifier|final
name|String
name|formatName
decl_stmt|;
DECL|method|UndefinedCharacterFormatException (String formatName)
specifier|public
name|UndefinedCharacterFormatException
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

