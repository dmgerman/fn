begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|antlr
package|package
name|antlr
package|;
end_package

begin_comment
comment|/* ANTLR Translator Generator  * Project led by Terence Parr at http://www.jGuru.com  * Software rights: http://www.antlr.org/RIGHTS.html  *  * $Id$  */
end_comment

begin_class
DECL|class|RecognitionException
specifier|public
class|class
name|RecognitionException
extends|extends
name|ANTLRException
block|{
DECL|field|fileName
specifier|public
name|String
name|fileName
decl_stmt|;
comment|// not used by treeparsers
DECL|field|line
specifier|public
name|int
name|line
decl_stmt|;
comment|// not used by treeparsers
DECL|field|column
specifier|public
name|int
name|column
decl_stmt|;
comment|// not used by treeparsers
DECL|method|RecognitionException ()
specifier|public
name|RecognitionException
parameter_list|()
block|{
name|super
argument_list|(
literal|"parsing error"
argument_list|)
expr_stmt|;
block|}
comment|/**      * RecognitionException constructor comment.      * @param s java.lang.String      */
DECL|method|RecognitionException (String s)
specifier|public
name|RecognitionException
parameter_list|(
name|String
name|s
parameter_list|)
block|{
name|super
argument_list|(
name|s
argument_list|)
expr_stmt|;
block|}
comment|/**      * RecognitionException constructor comment.      * @param s java.lang.String      */
DECL|method|RecognitionException (String s, String fileName, int line)
specifier|public
name|RecognitionException
parameter_list|(
name|String
name|s
parameter_list|,
name|String
name|fileName
parameter_list|,
name|int
name|line
parameter_list|)
block|{
name|super
argument_list|(
name|s
argument_list|)
expr_stmt|;
name|this
operator|.
name|fileName
operator|=
name|fileName
expr_stmt|;
name|this
operator|.
name|line
operator|=
name|line
expr_stmt|;
block|}
DECL|method|getColumn ()
specifier|public
name|int
name|getColumn
parameter_list|()
block|{
return|return
name|column
return|;
block|}
comment|/** @deprecated As of ANTLR 2.7.0 */
DECL|method|getErrorMessage ()
specifier|public
name|String
name|getErrorMessage
parameter_list|()
block|{
return|return
name|getMessage
argument_list|()
return|;
block|}
DECL|method|getFilename ()
specifier|public
name|String
name|getFilename
parameter_list|()
block|{
return|return
name|fileName
return|;
block|}
DECL|method|getLine ()
specifier|public
name|int
name|getLine
parameter_list|()
block|{
return|return
name|line
return|;
block|}
DECL|method|toString ()
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|FileLineFormatter
operator|.
name|getFormatter
argument_list|()
operator|.
name|getFormatString
argument_list|(
name|fileName
argument_list|,
name|line
argument_list|)
operator|+
name|getMessage
argument_list|()
return|;
block|}
block|}
end_class

end_unit

