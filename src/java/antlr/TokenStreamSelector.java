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

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Hashtable
import|;
end_import

begin_import
import|import
name|antlr
operator|.
name|collections
operator|.
name|impl
operator|.
name|LList
import|;
end_import

begin_import
import|import
name|antlr
operator|.
name|collections
operator|.
name|Stack
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

begin_comment
comment|/** A token stream MUX (multiplexor) knows about n token streams  *  and can multiplex them onto the same channel for use by token  *  stream consumer like a parser.  This is a way to have multiple  *  lexers break up the same input stream for a single parser.  *	Or, you can have multiple instances of the same lexer handle  *  multiple input streams; this works great for includes.  */
end_comment

begin_class
DECL|class|TokenStreamSelector
specifier|public
class|class
name|TokenStreamSelector
implements|implements
name|TokenStream
block|{
comment|/** The set of inputs to the MUX */
DECL|field|inputStreamNames
specifier|protected
name|Hashtable
name|inputStreamNames
decl_stmt|;
comment|/** The currently-selected token stream input */
DECL|field|input
specifier|protected
name|TokenStream
name|input
decl_stmt|;
comment|/** Used to track stack of input streams */
DECL|field|streamStack
specifier|protected
name|Stack
name|streamStack
init|=
operator|new
name|LList
argument_list|()
decl_stmt|;
DECL|method|TokenStreamSelector ()
specifier|public
name|TokenStreamSelector
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
name|inputStreamNames
operator|=
operator|new
name|Hashtable
argument_list|()
expr_stmt|;
block|}
DECL|method|addInputStream (TokenStream stream, String key)
specifier|public
name|void
name|addInputStream
parameter_list|(
name|TokenStream
name|stream
parameter_list|,
name|String
name|key
parameter_list|)
block|{
name|inputStreamNames
operator|.
name|put
argument_list|(
name|key
argument_list|,
name|stream
argument_list|)
expr_stmt|;
block|}
comment|/** Return the stream from tokens are being pulled at      *  the moment.      */
DECL|method|getCurrentStream ()
specifier|public
name|TokenStream
name|getCurrentStream
parameter_list|()
block|{
return|return
name|input
return|;
block|}
DECL|method|getStream (String sname)
specifier|public
name|TokenStream
name|getStream
parameter_list|(
name|String
name|sname
parameter_list|)
block|{
name|TokenStream
name|stream
init|=
operator|(
name|TokenStream
operator|)
name|inputStreamNames
operator|.
name|get
argument_list|(
name|sname
argument_list|)
decl_stmt|;
if|if
condition|(
name|stream
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"TokenStream "
operator|+
name|sname
operator|+
literal|" not found"
argument_list|)
throw|;
block|}
return|return
name|stream
return|;
block|}
DECL|method|nextToken ()
specifier|public
name|Token
name|nextToken
parameter_list|()
throws|throws
name|TokenStreamException
block|{
comment|// return input.nextToken();
comment|// keep looking for a token until you don't
comment|// get a retry exception.
for|for
control|(
init|;
condition|;
control|)
block|{
try|try
block|{
return|return
name|input
operator|.
name|nextToken
argument_list|()
return|;
block|}
catch|catch
parameter_list|(
name|TokenStreamRetryException
name|r
parameter_list|)
block|{
comment|// just retry "forever"
block|}
block|}
block|}
DECL|method|pop ()
specifier|public
name|TokenStream
name|pop
parameter_list|()
block|{
name|TokenStream
name|stream
init|=
operator|(
name|TokenStream
operator|)
name|streamStack
operator|.
name|pop
argument_list|()
decl_stmt|;
name|select
argument_list|(
name|stream
argument_list|)
expr_stmt|;
return|return
name|stream
return|;
block|}
DECL|method|push (TokenStream stream)
specifier|public
name|void
name|push
parameter_list|(
name|TokenStream
name|stream
parameter_list|)
block|{
name|streamStack
operator|.
name|push
argument_list|(
name|input
argument_list|)
expr_stmt|;
comment|// save current stream
name|select
argument_list|(
name|stream
argument_list|)
expr_stmt|;
block|}
DECL|method|push (String sname)
specifier|public
name|void
name|push
parameter_list|(
name|String
name|sname
parameter_list|)
block|{
name|streamStack
operator|.
name|push
argument_list|(
name|input
argument_list|)
expr_stmt|;
name|select
argument_list|(
name|sname
argument_list|)
expr_stmt|;
block|}
comment|/** Abort recognition of current Token and try again.      *  A stream can push a new stream (for include files      *  for example, and then retry(), which will cause      *  the current stream to abort back to this.nextToken().      *  this.nextToken() then asks for a token from the      *  current stream, which is the new "substream."      */
DECL|method|retry ()
specifier|public
name|void
name|retry
parameter_list|()
throws|throws
name|TokenStreamRetryException
block|{
throw|throw
operator|new
name|TokenStreamRetryException
argument_list|()
throw|;
block|}
comment|/** Set the stream without pushing old stream */
DECL|method|select (TokenStream stream)
specifier|public
name|void
name|select
parameter_list|(
name|TokenStream
name|stream
parameter_list|)
block|{
name|input
operator|=
name|stream
expr_stmt|;
block|}
DECL|method|select (String sname)
specifier|public
name|void
name|select
parameter_list|(
name|String
name|sname
parameter_list|)
throws|throws
name|IllegalArgumentException
block|{
name|input
operator|=
name|getStream
argument_list|(
name|sname
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

