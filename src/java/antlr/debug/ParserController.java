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
DECL|interface|ParserController
specifier|public
interface|interface
name|ParserController
extends|extends
name|ParserListener
block|{
DECL|method|checkBreak ()
specifier|public
name|void
name|checkBreak
parameter_list|()
function_decl|;
DECL|method|setParserEventSupport (ParserEventSupport p)
specifier|public
name|void
name|setParserEventSupport
parameter_list|(
name|ParserEventSupport
name|p
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

