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
comment|/**A Stream of characters fed to the lexer from a InputStream that can  * be rewound via mark()/rewind() methods.  *<p>  * A dynamic array is used to buffer up all the input characters.  Normally,  * "k" characters are stored in the buffer.  More characters may be stored during  * guess mode (testing syntactic predicate), or when LT(i>k) is referenced.  * Consumption of characters is deferred.  In other words, reading the next  * character is not done by conume(), but deferred until needed by LA or LT.  *<p>  *  * @see antlr.CharQueue  */
end_comment

begin_comment
comment|// SAS: added this class to handle Binary input w/ FileInputStream
end_comment

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|InputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_class
DECL|class|ByteBuffer
specifier|public
class|class
name|ByteBuffer
extends|extends
name|InputBuffer
block|{
comment|// char source
DECL|field|input
specifier|transient
name|InputStream
name|input
decl_stmt|;
comment|/** Create a character buffer */
DECL|method|ByteBuffer (InputStream input_)
specifier|public
name|ByteBuffer
parameter_list|(
name|InputStream
name|input_
parameter_list|)
block|{
name|super
argument_list|()
expr_stmt|;
name|input
operator|=
name|input_
expr_stmt|;
block|}
comment|/** Ensure that the character buffer is sufficiently full */
DECL|method|fill (int amount)
specifier|public
name|void
name|fill
parameter_list|(
name|int
name|amount
parameter_list|)
throws|throws
name|CharStreamException
block|{
try|try
block|{
name|syncConsume
argument_list|()
expr_stmt|;
comment|// Fill the buffer sufficiently to hold needed characters
while|while
condition|(
name|queue
operator|.
name|nbrEntries
operator|<
name|amount
operator|+
name|markerOffset
condition|)
block|{
comment|// Append the next character
name|queue
operator|.
name|append
argument_list|(
operator|(
name|char
operator|)
name|input
operator|.
name|read
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|IOException
name|io
parameter_list|)
block|{
throw|throw
operator|new
name|CharStreamIOException
argument_list|(
name|io
argument_list|)
throw|;
block|}
block|}
block|}
end_class

end_unit

