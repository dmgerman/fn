begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|antlr.debug
package|package
name|antlr
operator|.
name|debug
package|;
end_package

begin_comment
comment|/** A dummy implementation of a CharBufferListener -- this class is not   * meant to be used by itself -- it's meant to be subclassed */
end_comment

begin_class
DECL|class|InputBufferAdapter
specifier|public
specifier|abstract
class|class
name|InputBufferAdapter
implements|implements
name|InputBufferListener
block|{
DECL|method|doneParsing (TraceEvent e)
specifier|public
name|void
name|doneParsing
parameter_list|(
name|TraceEvent
name|e
parameter_list|)
block|{ 	}
comment|/**  * charConsumed method comment.  */
DECL|method|inputBufferConsume (InputBufferEvent e)
specifier|public
name|void
name|inputBufferConsume
parameter_list|(
name|InputBufferEvent
name|e
parameter_list|)
block|{ }
comment|/**  * charLA method comment.  */
DECL|method|inputBufferLA (InputBufferEvent e)
specifier|public
name|void
name|inputBufferLA
parameter_list|(
name|InputBufferEvent
name|e
parameter_list|)
block|{ }
DECL|method|inputBufferMark (InputBufferEvent e)
specifier|public
name|void
name|inputBufferMark
parameter_list|(
name|InputBufferEvent
name|e
parameter_list|)
block|{}
DECL|method|inputBufferRewind (InputBufferEvent e)
specifier|public
name|void
name|inputBufferRewind
parameter_list|(
name|InputBufferEvent
name|e
parameter_list|)
block|{}
DECL|method|refresh ()
specifier|public
name|void
name|refresh
parameter_list|()
block|{ 	}
block|}
end_class

end_unit

