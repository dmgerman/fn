begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|antlr
package|package
name|antlr
package|;
end_package

begin_comment
comment|/* ANTLR Translator Generator  * Project led by Terence Parr at http://www.jGuru.com  * Software rights: http://www.antlr.org/license.html  *  * $Id$  */
end_comment

begin_comment
comment|/**  * Wraps a RecognitionException in a TokenStreamException so you  * can pass it along.  */
end_comment

begin_class
DECL|class|TokenStreamRecognitionException
specifier|public
class|class
name|TokenStreamRecognitionException
extends|extends
name|TokenStreamException
block|{
DECL|field|recog
specifier|public
name|RecognitionException
name|recog
decl_stmt|;
DECL|method|TokenStreamRecognitionException (RecognitionException re)
specifier|public
name|TokenStreamRecognitionException
parameter_list|(
name|RecognitionException
name|re
parameter_list|)
block|{
name|super
argument_list|(
name|re
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
name|this
operator|.
name|recog
operator|=
name|re
expr_stmt|;
block|}
DECL|method|toString ()
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|recog
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
end_class

end_unit

