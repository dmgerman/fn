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
DECL|class|MessageAdapter
specifier|public
class|class
name|MessageAdapter
implements|implements
name|MessageListener
block|{
DECL|method|doneParsing (TraceEvent e)
specifier|public
name|void
name|doneParsing
parameter_list|(
name|TraceEvent
name|e
parameter_list|)
block|{}
DECL|method|refresh ()
specifier|public
name|void
name|refresh
parameter_list|()
block|{}
DECL|method|reportError (MessageEvent e)
specifier|public
name|void
name|reportError
parameter_list|(
name|MessageEvent
name|e
parameter_list|)
block|{}
DECL|method|reportWarning (MessageEvent e)
specifier|public
name|void
name|reportWarning
parameter_list|(
name|MessageEvent
name|e
parameter_list|)
block|{}
block|}
end_class

end_unit

