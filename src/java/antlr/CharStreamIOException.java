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
comment|/**  * Wrap an IOException in a CharStreamException  */
end_comment

begin_class
DECL|class|CharStreamIOException
specifier|public
class|class
name|CharStreamIOException
extends|extends
name|CharStreamException
block|{
DECL|field|io
specifier|public
name|IOException
name|io
decl_stmt|;
DECL|method|CharStreamIOException (IOException io)
specifier|public
name|CharStreamIOException
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

