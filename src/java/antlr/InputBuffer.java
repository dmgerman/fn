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
comment|// SAS: Added this class to genericise the input buffers for scanners
end_comment

begin_comment
comment|//      This allows a scanner to use a binary (FileInputStream) or
end_comment

begin_comment
comment|//      text (FileReader) stream of data; the generated scanner
end_comment

begin_comment
comment|//      subclass will define the input stream
end_comment

begin_comment
comment|//      There are two subclasses to this: CharBuffer and ByteBuffer
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
comment|/**A Stream of characters fed to the lexer from a InputStream that can  * be rewound via mark()/rewind() methods.  *<p>  * A dynamic array is used to buffer up all the input characters.  Normally,  * "k" characters are stored in the buffer.  More characters may be stored during  * guess mode (testing syntactic predicate), or when LT(i>k) is referenced.  * Consumption of characters is deferred.  In other words, reading the next  * character is not done by conume(), but deferred until needed by LA or LT.  *<p>  *  * @see antlr.CharQueue  */
end_comment

begin_class
DECL|class|InputBuffer
specifier|public
specifier|abstract
class|class
name|InputBuffer
block|{
comment|// Number of active markers
DECL|field|nMarkers
specifier|protected
name|int
name|nMarkers
init|=
literal|0
decl_stmt|;
comment|// Additional offset used when markers are active
DECL|field|markerOffset
specifier|protected
name|int
name|markerOffset
init|=
literal|0
decl_stmt|;
comment|// Number of calls to consume() since last LA() or LT() call
DECL|field|numToConsume
specifier|protected
name|int
name|numToConsume
init|=
literal|0
decl_stmt|;
comment|// Circular queue
DECL|field|queue
specifier|protected
name|CharQueue
name|queue
decl_stmt|;
comment|/** Create an input buffer */
DECL|method|InputBuffer ()
specifier|public
name|InputBuffer
parameter_list|()
block|{
name|queue
operator|=
operator|new
name|CharQueue
argument_list|(
literal|1
argument_list|)
expr_stmt|;
block|}
comment|/** This method updates the state of the input buffer so that      *  the text matched since the most recent mark() is no longer      *  held by the buffer.  So, you either do a mark/rewind for      *  failed predicate or mark/commit to keep on parsing without      *  rewinding the input.      */
DECL|method|commit ()
specifier|public
name|void
name|commit
parameter_list|()
block|{
name|nMarkers
operator|--
expr_stmt|;
block|}
comment|/** Mark another character for deferred consumption */
DECL|method|consume ()
specifier|public
name|void
name|consume
parameter_list|()
block|{
name|numToConsume
operator|++
expr_stmt|;
block|}
comment|/** Ensure that the input buffer is sufficiently full */
DECL|method|fill (int amount)
specifier|public
specifier|abstract
name|void
name|fill
parameter_list|(
name|int
name|amount
parameter_list|)
throws|throws
name|CharStreamException
function_decl|;
DECL|method|getLAChars ()
specifier|public
name|String
name|getLAChars
parameter_list|()
block|{
name|StringBuffer
name|la
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
name|markerOffset
init|;
name|i
operator|<
name|queue
operator|.
name|nbrEntries
condition|;
name|i
operator|++
control|)
name|la
operator|.
name|append
argument_list|(
name|queue
operator|.
name|elementAt
argument_list|(
name|i
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|la
operator|.
name|toString
argument_list|()
return|;
block|}
DECL|method|getMarkedChars ()
specifier|public
name|String
name|getMarkedChars
parameter_list|()
block|{
name|StringBuffer
name|marked
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|markerOffset
condition|;
name|i
operator|++
control|)
name|marked
operator|.
name|append
argument_list|(
name|queue
operator|.
name|elementAt
argument_list|(
name|i
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|marked
operator|.
name|toString
argument_list|()
return|;
block|}
DECL|method|isMarked ()
specifier|public
name|boolean
name|isMarked
parameter_list|()
block|{
return|return
operator|(
name|nMarkers
operator|!=
literal|0
operator|)
return|;
block|}
comment|/** Get a lookahead character */
DECL|method|LA (int i)
specifier|public
name|char
name|LA
parameter_list|(
name|int
name|i
parameter_list|)
throws|throws
name|CharStreamException
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
name|int
name|mark
parameter_list|()
block|{
name|syncConsume
argument_list|()
expr_stmt|;
name|nMarkers
operator|++
expr_stmt|;
return|return
name|markerOffset
return|;
block|}
comment|/**Rewind the character buffer to a marker.      * @param mark Marker returned previously from mark()      */
DECL|method|rewind (int mark)
specifier|public
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
block|}
comment|/** Reset the input buffer      */
DECL|method|reset ()
specifier|public
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
comment|/** Sync up deferred consumption */
DECL|method|syncConsume ()
specifier|protected
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
comment|// guess mode -- leave leading characters and bump offset.
name|markerOffset
operator|++
expr_stmt|;
block|}
else|else
block|{
comment|// normal mode -- remove first character
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

