begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|antlr.debug
package|package
name|antlr
operator|.
name|debug
package|;
end_package

begin_interface
DECL|interface|InputBufferListener
specifier|public
interface|interface
name|InputBufferListener
extends|extends
name|ListenerBase
block|{
DECL|method|inputBufferConsume (InputBufferEvent e)
specifier|public
name|void
name|inputBufferConsume
parameter_list|(
name|InputBufferEvent
name|e
parameter_list|)
function_decl|;
DECL|method|inputBufferLA (InputBufferEvent e)
specifier|public
name|void
name|inputBufferLA
parameter_list|(
name|InputBufferEvent
name|e
parameter_list|)
function_decl|;
DECL|method|inputBufferMark (InputBufferEvent e)
specifier|public
name|void
name|inputBufferMark
parameter_list|(
name|InputBufferEvent
name|e
parameter_list|)
function_decl|;
DECL|method|inputBufferRewind (InputBufferEvent e)
specifier|public
name|void
name|inputBufferRewind
parameter_list|(
name|InputBufferEvent
name|e
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

