begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.openoffice
package|package
name|org
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
DECL|class|CreationException
class|class
name|CreationException
extends|extends
name|Exception
block|{
DECL|method|CreationException (String message)
specifier|public
name|CreationException
parameter_list|(
name|String
name|message
parameter_list|)
block|{
name|super
argument_list|(
name|message
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

