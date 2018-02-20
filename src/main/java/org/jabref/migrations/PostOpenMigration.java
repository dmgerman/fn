begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.migrations
package|package
name|org
operator|.
name|jabref
operator|.
name|migrations
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
DECL|interface|PostOpenMigration
specifier|public
interface|interface
name|PostOpenMigration
block|{
DECL|method|performMigration (ParserResult parserResult)
name|void
name|performMigration
parameter_list|(
name|ParserResult
name|parserResult
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

