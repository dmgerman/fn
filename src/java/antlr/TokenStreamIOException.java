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

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_comment
comment|/**  * Wraps an IOException in a TokenStreamException  */
end_comment

begin_class
DECL|class|TokenStreamIOException
specifier|public
class|class
name|TokenStreamIOException
extends|extends
name|TokenStreamException
block|{
DECL|field|io
specifier|public
name|IOException
name|io
decl_stmt|;
comment|/**  * TokenStreamIOException constructor comment.  * @param s java.lang.String  */
DECL|method|TokenStreamIOException (IOException io)
specifier|public
name|TokenStreamIOException
parameter_list|(
name|IOException
name|io
parameter_list|)
block|{
name|super
argument_list|(
name|io
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
name|this
operator|.
name|io
operator|=
name|io
expr_stmt|;
block|}
block|}
end_class

end_unit

