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
comment|/**A Stream of Token objects fed to the parser from a Tokenizer that can  * be rewound via mark()/rewind() methods.  *<p>  * A dynamic array is used to buffer up all the input tokens.  Normally,  * "k" tokens are stored in the buffer.  More tokens may be stored during  * guess mode (testing syntactic predicate), or when LT(i>k) is referenced.  * Consumption of tokens is deferred.  In other words, reading the next  * token is not done by conume(), but deferred until needed by LA or LT.  *<p>  *  * @see antlr.Token  * @see antlr.Tokenizer  * @see antlr.TokenQueue  */
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

begin_class
DECL|class|TokenBuffer
specifier|public
class|class
name|TokenBuffer
block|{
comment|// Token source
DECL|field|input
specifier|protected
name|TokenStream
name|input
decl_stmt|;
comment|// Number of active markers
DECL|field|nMarkers
name|int
name|nMarkers
init|=
literal|0
decl_stmt|;
comment|// Additional offset used when markers are active
DECL|field|markerOffset
name|int
name|markerOffset
init|=
literal|0
decl_stmt|;
comment|// Number of calls to consume() since last LA() or LT() call
DECL|field|numToConsume
name|int
name|numToConsume
init|=
literal|0
decl_stmt|;
comment|// Circular queue
DECL|field|queue
name|TokenQueue
name|queue
decl_stmt|;
comment|/** Create a token buffer */
DECL|method|TokenBuffer (TokenStream input_)
specifier|public
name|TokenBuffer
parameter_list|(
name|TokenStream
name|input_
parameter_list|)
block|{
name|input
operator|=
name|input_
expr_stmt|;
name|queue
operator|=
operator|new
name|TokenQueue
argument_list|(
literal|1
argument_list|)
expr_stmt|;
block|}
comment|/** Reset the input buffer to empty state */
DECL|method|reset ()
specifier|public
specifier|final
name|void
name|reset
parameter_list|()
block|{
name|nMarkers
operator|=
literal|0
expr_stmt|;
name|markerOffset
operator|=
literal|0
expr_stmt|;
name|numToConsume
operator|=
literal|0
expr_stmt|;
name|queue
operator|.
name|reset
argument_list|()
expr_stmt|;
block|}
comment|/** Mark another token for deferred consumption */
DECL|method|consume ()
specifier|public
specifier|final
name|void
name|consume
parameter_list|()
block|{
name|numToConsume
operator|++
expr_stmt|;
block|}
comment|/** Ensure that the token buffer is sufficiently full */
DECL|method|fill (int amount)
specifier|private
specifier|final
name|void
name|fill
parameter_list|(
name|int
name|amount
parameter_list|)
throws|throws
name|TokenStreamException
block|{
name|syncConsume
argument_list|()
expr_stmt|;
comment|// Fill the buffer sufficiently to hold needed tokens
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
comment|// Append the next token
name|queue
operator|.
name|append
argument_list|(
name|input
operator|.
name|nextToken
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
comment|/** return the Tokenizer (needed by ParseView) */
DECL|method|getInput ()
specifier|public
name|TokenStream
name|getInput
parameter_list|()
block|{
return|return
name|input
return|;
block|}
comment|/** Get a lookahead token value */
DECL|method|LA (int i)
specifier|public
specifier|final
name|int
name|LA
parameter_list|(
name|int
name|i
parameter_list|)
throws|throws
name|TokenStreamException
block|{
name|fill
argument_list|(
name|i
argument_list|)
expr_stmt|;
return|return
name|queue
operator|.
name|elementAt
argument_list|(
name|markerOffset
operator|+
name|i
operator|-
literal|1
argument_list|)
operator|.
name|type
return|;
block|}
comment|/** Get a lookahead token */
DECL|method|LT (int i)
specifier|public
specifier|final
name|Token
name|LT
parameter_list|(
name|int
name|i
parameter_list|)
throws|throws
name|TokenStreamException
block|{
name|fill
argument_list|(
name|i
argument_list|)
expr_stmt|;
return|return
name|queue
operator|.
name|elementAt
argument_list|(
name|markerOffset
operator|+
name|i
operator|-
literal|1
argument_list|)
return|;
block|}
comment|/**Return an integer marker that can be used to rewind the buffer to      * its current state.      */
DECL|method|mark ()
specifier|public
specifier|final
name|int
name|mark
parameter_list|()
block|{
name|syncConsume
argument_list|()
expr_stmt|;
comment|//System.out.println("Marking at " + markerOffset);
comment|//try { for (int i = 1; i<= 2; i++) { System.out.println("LA("+i+")=="+LT(i).getText()); } } catch (ScannerException e) {}
name|nMarkers
operator|++
expr_stmt|;
return|return
name|markerOffset
return|;
block|}
comment|/**Rewind the token buffer to a marker.      * @param mark Marker returned previously from mark()      */
DECL|method|rewind (int mark)
specifier|public
specifier|final
name|void
name|rewind
parameter_list|(
name|int
name|mark
parameter_list|)
block|{
name|syncConsume
argument_list|()
expr_stmt|;
name|markerOffset
operator|=
name|mark
expr_stmt|;
name|nMarkers
operator|--
expr_stmt|;
comment|//System.out.println("Rewinding to " + mark);
comment|//try { for (int i = 1; i<= 2; i++) { System.out.println("LA("+i+")=="+LT(i).getText()); } } catch (ScannerException e) {}
block|}
comment|/** Sync up deferred consumption */
DECL|method|syncConsume ()
specifier|private
specifier|final
name|void
name|syncConsume
parameter_list|()
block|{
while|while
condition|(
name|numToConsume
operator|>
literal|0
condition|)
block|{
if|if
condition|(
name|nMarkers
operator|>
literal|0
condition|)
block|{
comment|// guess mode -- leave leading tokens and bump offset.
name|markerOffset
operator|++
expr_stmt|;
block|}
else|else
block|{
comment|// normal mode -- remove first token
name|queue
operator|.
name|removeFirst
argument_list|()
expr_stmt|;
block|}
name|numToConsume
operator|--
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

