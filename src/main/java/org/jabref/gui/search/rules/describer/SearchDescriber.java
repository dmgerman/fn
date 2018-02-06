begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.search.rules.describer
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|search
operator|.
name|rules
operator|.
name|describer
package|;
end_package

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|text
operator|.
name|TextFlow
import|;
end_import

begin_interface
annotation|@
name|FunctionalInterface
DECL|interface|SearchDescriber
specifier|public
interface|interface
name|SearchDescriber
block|{
DECL|method|getDescription ()
name|TextFlow
name|getDescription
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

