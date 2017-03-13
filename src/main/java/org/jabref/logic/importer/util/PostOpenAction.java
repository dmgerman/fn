begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.importer.util
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|importer
operator|.
name|util
package|;
end_package

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|importer
operator|.
name|ParserResult
import|;
end_import

begin_interface
DECL|interface|PostOpenAction
specifier|public
interface|interface
name|PostOpenAction
block|{
DECL|method|performAction (ParserResult parserResult)
name|void
name|performAction
parameter_list|(
name|ParserResult
name|parserResult
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

