begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|antlr.debug
package|package
name|antlr
operator|.
name|debug
package|;
end_package

begin_class
DECL|class|InputBufferReporter
specifier|public
class|class
name|InputBufferReporter
implements|implements
name|InputBufferListener
block|{
comment|/**  * doneParsing method comment.  */
DECL|method|doneParsing (TraceEvent e)
specifier|public
name|void
name|doneParsing
parameter_list|(
name|TraceEvent
name|e
parameter_list|)
block|{ }
DECL|method|inputBufferChanged (InputBufferEvent e)
specifier|public
name|void
name|inputBufferChanged
parameter_list|(
name|InputBufferEvent
name|e
parameter_list|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
comment|/**  * charBufferConsume method comment.  */
DECL|method|inputBufferConsume (InputBufferEvent e)
specifier|public
name|void
name|inputBufferConsume
parameter_list|(
name|InputBufferEvent
name|e
parameter_list|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
comment|/**  * charBufferLA method comment.  */
DECL|method|inputBufferLA (InputBufferEvent e)
specifier|public
name|void
name|inputBufferLA
parameter_list|(
name|InputBufferEvent
name|e
parameter_list|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
DECL|method|inputBufferMark (InputBufferEvent e)
specifier|public
name|void
name|inputBufferMark
parameter_list|(
name|InputBufferEvent
name|e
parameter_list|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
DECL|method|inputBufferRewind (InputBufferEvent e)
specifier|public
name|void
name|inputBufferRewind
parameter_list|(
name|InputBufferEvent
name|e
parameter_list|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
comment|/**  * refresh method comment.  */
DECL|method|refresh ()
specifier|public
name|void
name|refresh
parameter_list|()
block|{ }
block|}
end_class

end_unit

