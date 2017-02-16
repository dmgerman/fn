begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.search.rules.describer
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|search
operator|.
name|rules
operator|.
name|describer
package|;
end_package

begin_interface
annotation|@
name|FunctionalInterface
DECL|interface|SearchDescriber
specifier|public
interface|interface
name|SearchDescriber
block|{
DECL|method|getDescription ()
name|String
name|getDescription
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

