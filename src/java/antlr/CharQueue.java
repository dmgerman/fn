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

begin_comment
comment|/** A circular buffer object used by CharBuffer */
end_comment

begin_class
DECL|class|CharQueue
specifier|public
class|class
name|CharQueue
block|{
comment|// Physical circular buffer of characters
DECL|field|buffer
specifier|protected
name|char
index|[]
name|buffer
decl_stmt|;
comment|// buffer.length-1 for quick modulous
DECL|field|sizeLessOne
specifier|protected
name|int
name|sizeLessOne
decl_stmt|;
comment|// physical index of front token
DECL|field|offset
specifier|protected
name|int
name|offset
decl_stmt|;
comment|// number of characters in the queue
DECL|field|nbrEntries
specifier|protected
name|int
name|nbrEntries
decl_stmt|;
DECL|method|CharQueue (int minSize)
specifier|public
name|CharQueue
parameter_list|(
name|int
name|minSize
parameter_list|)
block|{
comment|// Find first power of 2>= to requested size
name|int
name|size
decl_stmt|;
for|for
control|(
name|size
operator|=
literal|2
init|;
name|size
operator|<
name|minSize
condition|;
name|size
operator|*=
literal|2
control|)
block|{
empty_stmt|;
block|}
name|init
argument_list|(
name|size
argument_list|)
expr_stmt|;
block|}
comment|/** Add token to end of the queue 	 * @param tok The token to add 	 */
DECL|method|append (char tok)
specifier|public
specifier|final
name|void
name|append
parameter_list|(
name|char
name|tok
parameter_list|)
block|{
if|if
condition|(
name|nbrEntries
operator|==
name|buffer
operator|.
name|length
condition|)
block|{
name|expand
argument_list|()
expr_stmt|;
block|}
name|buffer
index|[
operator|(
name|offset
operator|+
name|nbrEntries
operator|)
operator|&
name|sizeLessOne
index|]
operator|=
name|tok
expr_stmt|;
name|nbrEntries
operator|++
expr_stmt|;
block|}
comment|/** Fetch a token from the queue by index 	 * @param idx The index of the token to fetch, where zero is the token at the front of the queue 	 */
DECL|method|elementAt (int idx)
specifier|public
specifier|final
name|char
name|elementAt
parameter_list|(
name|int
name|idx
parameter_list|)
block|{
return|return
name|buffer
index|[
operator|(
name|offset
operator|+
name|idx
operator|)
operator|&
name|sizeLessOne
index|]
return|;
block|}
comment|/** Expand the token buffer by doubling its capacity */
DECL|method|expand ()
specifier|private
specifier|final
name|void
name|expand
parameter_list|()
block|{
name|char
index|[]
name|newBuffer
init|=
operator|new
name|char
index|[
name|buffer
operator|.
name|length
operator|*
literal|2
index|]
decl_stmt|;
comment|// Copy the contents to the new buffer
comment|// Note that this will store the first logical item in the
comment|// first physical array element.
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|buffer
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|newBuffer
index|[
name|i
index|]
operator|=
name|elementAt
argument_list|(
name|i
argument_list|)
expr_stmt|;
block|}
comment|// Re-initialize with new contents, keep old nbrEntries
name|buffer
operator|=
name|newBuffer
expr_stmt|;
name|sizeLessOne
operator|=
name|buffer
operator|.
name|length
operator|-
literal|1
expr_stmt|;
name|offset
operator|=
literal|0
expr_stmt|;
block|}
comment|/** Initialize the queue. 	 * @param size The initial size of the queue 	 */
DECL|method|init (int size)
specifier|private
specifier|final
name|void
name|init
parameter_list|(
name|int
name|size
parameter_list|)
block|{
comment|// Allocate buffer
name|buffer
operator|=
operator|new
name|char
index|[
name|size
index|]
expr_stmt|;
comment|// Other initialization
name|sizeLessOne
operator|=
name|size
operator|-
literal|1
expr_stmt|;
name|offset
operator|=
literal|0
expr_stmt|;
name|nbrEntries
operator|=
literal|0
expr_stmt|;
block|}
comment|/** Remove char from front of queue */
DECL|method|removeFirst ()
specifier|public
specifier|final
name|void
name|removeFirst
parameter_list|()
block|{
name|offset
operator|=
operator|(
name|offset
operator|+
literal|1
operator|)
operator|&
name|sizeLessOne
expr_stmt|;
name|nbrEntries
operator|--
expr_stmt|;
block|}
block|}
end_class

end_unit

